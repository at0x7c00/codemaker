package com.lenovots.crm.project.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Alignment;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.service.IDictionaryDetailService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.common.service.IBaseService;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.EnvironmentVariable;
import com.lenovots.crm.project.entity.Packagee;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.entity.Rule;
import com.lenovots.crm.project.service.IEntityService;
import com.lenovots.crm.project.service.IPackageService;
import com.lenovots.crm.project.service.IProjectService;
import com.lenovots.crm.project.service.IRuleService;
import com.lenovots.crm.util.CommonUtil;
import com.lenovots.crm.util.DateUtil;
import com.lenovots.crm.util.ELUtil;
import com.lenovots.crm.util.PropertityUtil;

@Controller("entityAction")
@Scope("prototype")
public class EntityAction extends BaseAction<Entity> {
	private static final long serialVersionUID = 8856961694147206209L;
	private static Logger logger = Logger.getLogger(EntityAction.class);
	@Resource
	private IEntityService entityService;
	@Resource
	private IRuleService ruleService;
	@Resource
	private IPackageService packageService;
	@Resource
	private IProjectService projectService;
	private List<Packagee> parentPacks;
	private List<Project> projectList;
	private List<Entity> entityList;
	private Integer parentId;
	private Entity entity;
	private Integer packageId;
	private Integer projectId;
	private Integer ruleId;
	private Integer checkRepeat = Integer.valueOf(0);
	private String formTimeId;
	private StringBuffer resultInfo;
	private List<String> validatePropNames;
	private Integer[] optionalIds;
	private Map<String, Entity> existedEntityMap = new HashMap<String, Entity>();
	private String existedEntityMapKey;
	private File excelFile;
	@Resource
	private IDictionaryDetailService dictionaryDetailService;

	public String importExcelUI() {
		this.formTimeId = UUID.randomUUID().toString();
		ServletActionContext.getRequest().getSession().setAttribute("formTimeId", this.formTimeId);
		return "success";
	}

	public String importExcel() throws Exception {
		try {
			String formTimeIdInSession = (String) ServletActionContext.getRequest().getSession().getAttribute("formTimeId");
			if ((formTimeIdInSession != null) && (formTimeIdInSession.equals(this.formTimeId))) {
				long start = System.currentTimeMillis();
				if (this.checkRepeat.intValue() == 1) {
					initValidateProps();
				}
				int success = importEntityExcel(this.excelFile);
				long end = System.currentTimeMillis();
				ServletActionContext.getRequest().setAttribute("duration", Long.valueOf((end - start) / 1000L));
				ServletActionContext.getRequest().setAttribute("success", Integer.valueOf(success));
				ServletActionContext.getRequest().setAttribute("total", ServletActionContext.getRequest().getSession().getAttribute("total"));
				ServletActionContext.getRequest().getSession().setAttribute("formTimeId", null);
				ServletActionContext.getRequest().getSession().setAttribute("total", null);
				ServletActionContext.getRequest().getSession().setAttribute("rowCount", null);
			} else {
				this.msg = "表单重复提交";
			}
		} catch (BiffException e) {
			e.printStackTrace();
			this.msg = "文件格式错误！";
		}
		this.anyAction = "entity";
		this.op = "importReport";
		return "anyforward";
	}

	public String codeView() {
		this.entity = ((Entity) this.entityService.findById(this.id));
		Rule rule = (Rule) this.ruleService.findById(this.ruleId);
		setAttribute("rule", rule);

		ELUtil elUtil = ELUtil.getInstance();
		elUtil.setAttribute("entity", this.entity);
		try {
			String currentFileFullPackage = elUtil.parse(rule.getOutputDir());
			String currentFileName = elUtil.parse(rule.getOutputFileName());
			currentFileFullPackage = elUtil.calcuteRelativePath(currentFileFullPackage);
			currentFileFullPackage = currentFileFullPackage.replaceAll("/", ".");
			currentFileFullPackage = currentFileFullPackage.replaceAll("\\\\", ".");
			if (currentFileFullPackage.endsWith(".")) {
				currentFileFullPackage = currentFileFullPackage.substring(0, currentFileFullPackage.length() - 1);
			}
			if (currentFileFullPackage.contains(".")) {
				currentFileFullPackage = currentFileFullPackage.substring(currentFileFullPackage.indexOf(".") + 1);
			}
			this.entity.setCurrentFileFullPackage(currentFileFullPackage);
			this.entity.setCurrentFileName(currentFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttribute("entity", this.entity);

		prepareEnvironmentVariables();
		this.op = rule.getTemplate().getId() + "";
		return "codeView";
	}

	private void prepareEnvironmentVariables() {
		setAttribute("now", new Date());
		setAttribute("entityIteratorIndex", Integer.valueOf(Integer.parseInt((String) getParameter("entityIteratorIndex"))));

		Object value = null;
		for (EnvironmentVariable var : this.entity.getPackagee().getProject().getVariables()) {
			try {
				switch (var.getDataType().intValue()) {
				case 1:
					value = var.getValue();
					break;
				case 2:
					value = Integer.valueOf(Integer.parseInt(var.getValue()));
					break;
				case 3:
					value = Float.valueOf(Float.parseFloat(var.getValue()));
					break;
				case 4:
					value = DateUtil.tryStr2Date(var.getValue());
				}
				setAttribute(var.getVarName(), value);
			} catch (Exception localException) {
			}
		}
	}

	public int importEntityExcel(File file) throws Exception {
		Entity entity = null;
		Workbook book = Workbook.getWorkbook(file);

		Sheet sheet = book.getSheet(0);

		int i = 1;
		int success = 0;
		this.resultInfo = new StringBuffer();
		ServletActionContext.getRequest().getSession().setAttribute("total", Integer.valueOf(sheet.getRows() - 1));
		ServletActionContext.getRequest().getSession().setAttribute("opera", "正在分析Excel...");
		List list = new ArrayList();
		ServletActionContext.getRequest().getSession().setAttribute("total", Integer.valueOf(sheet.getRows() - 1));
		for (; i < sheet.getRows(); i++) {
			try {
				ServletActionContext.getRequest().getSession().setAttribute("rowCount", Integer.valueOf(i));
				appendMsg("<div class='Record'>");
				appendMsg("<span class='RowTitle'>第" + i + "行:</span>");
				entity = new Entity();

				entity.setName((String) cellContentsToObject(sheet.getCell(0, i).getContents(), 1, null, null));
				entity.setTableName((String) cellContentsToObject(sheet.getCell(1, i).getContents(), 1, null, null));
				entity.setClassName((String) cellContentsToObject(sheet.getCell(2, i).getContents(), 1, null, null));
				String packagename = sheet.getCell(3, i).getContents();
				StringBuffer hql = new StringBuffer("FROM Packagee p where 1=1 ");
				List<Object> params = new ArrayList();
				int x = 0;
				for (String pName : packagename.split("\\.")) {
					String temp = " and p";
					if (x != 0) {
						for (int a = 0; a < x; a++) {
							temp = temp + ".parent";
						}
					}
					temp = temp + ".name=?";
					hql.append(temp);
					params.add(0, pName);
					x++;
				}
				List<Packagee> pList = this.packageService.findByHql(hql.toString(), params.toArray());
				if (pList.size() > 0) {
					entity.setPackagee((Packagee) pList.get(0));
				}
				entity.setType((Integer) cellContentsToObject(sheet.getCell(4, i).getContents(), 2, null, null));
				entity.setEnumValue((String) cellContentsToObject(sheet.getCell(5, i).getContents(), 1, null, null));
				entity.setIdGenerateType((Integer) cellContentsToObject(sheet.getCell(6, i).getContents(), 2, null, null));
				entity.setQueryAble((Integer) cellContentsToObject(sheet.getCell(7, i).getContents(), 2, null, null));
				entity.setExportable((Integer) cellContentsToObject(sheet.getCell(8, i).getContents(), 2, null, null));
				entity.setImportable((Integer) cellContentsToObject(sheet.getCell(9, i).getContents(), 2, null, null));
				entity.setVisiablity((Integer) cellContentsToObject(sheet.getCell(10, i).getContents(), 2, null, null));
				if ((sheet.getCell(10, i).getContents() == null) || (sheet.getCell(10, i).getContents().trim().equals(""))) {
					entity.setVisiablity(Integer.valueOf(1));
				}
				if ((this.checkRepeat != null) && (this.checkRepeat.intValue() != 0)) {
					entity = getExistEntityInfo(entity);
					if (!this.existedEntityMapKey.trim().equals("")) {
						if (this.existedEntityMap.get(this.existedEntityMapKey) != null) {
							appendMsg("第" + (i + 1) + "行记录与之前的记录重复，已忽略<br/>");
							appendMsg("</div>");
							logger.info("第" + (i + 1) + "行记录与之前的记录重复，已忽略");
							continue;
						}
						this.existedEntityMap.put(this.existedEntityMapKey, entity);
					}
				}
				if (entity.getId() != null) {
					appendMsg("<font color='red'>导入失败:发现重复的实体信息，忽略该条记录。</font>");
					appendMsg("</div>");
				} else {
					appendMsg("<font color='red'>导入成功</font>");
					appendMsg("</div>");

					list.add(entity);
					success++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				appendMsg("遇到异常：" + e.getMessage());
				appendMsg("</div>");
				appendMsg("</div>");
			}
		}
		ServletActionContext.getRequest().getSession().setAttribute("rowCount", Integer.valueOf(0));
		ServletActionContext.getRequest().getSession().setAttribute("total", Integer.valueOf(success));
		ServletActionContext.getRequest().getSession().setAttribute("opera", "正在添加答卷...");
		if (list.size() > 0) {
			List tmpList = new ArrayList();
			int size = 100;
			int count = 0;
			for (int j = 0; j < list.size(); j++) {
				tmpList.add(list.get(j));
				if ((tmpList.size() % size == 0) || (j == list.size() - 1)) {
					ServletActionContext.getRequest().getSession().setAttribute("rowCount", Integer.valueOf(count));
					this.entityService.batchAdd(tmpList);
					tmpList = new ArrayList();
				}
			}
		}
		this.msg = this.resultInfo.toString();
		ServletActionContext.getRequest().getSession().setAttribute("total", Integer.valueOf(sheet.getRows() - 1));
		ServletActionContext.getRequest().getSession().setAttribute("opera", "完成!");
		book.close();
		return success;
	}

	private Entity getExistEntityInfo(Entity entity) {
		StringBuffer hql = new StringBuffer("FROM Entity c WHERE 1=1 ");
		List<Object> paramValues = new ArrayList();

		this.existedEntityMapKey = "";
		for (String propName : this.validatePropNames) {
			Object propValue = PropertityUtil.getProp(entity, propName);
			if ((propValue != null) && (!"".equals(propValue.toString().trim()))) {
				hql.append(" AND c.").append(propName).append("=? ");
				paramValues.add(propValue);
			}
			this.existedEntityMapKey += (propValue == null ? "" : propValue.toString());
		}
		List<Entity> tmpList = this.entityService.findByHql(hql.toString(), paramValues.toArray());
		if ((tmpList != null) && (tmpList.size() > 0)) {
			appendMsg("<font color='red'>使用已存在的实体信息。</font>");
			return (Entity) tmpList.get(0);
		}
		return entity;
	}

	private void initValidateProps() {
		if (this.optionalIds != null) {
			this.validatePropNames = new ArrayList();
			for (Integer id : this.optionalIds) {
				this.validatePropNames.add(getPropName(id.intValue()));
			}
		}
	}

	private String getPropName(int optionalId) {
		switch (optionalId) {
		case 1:
			return "name";
		case 2:
			return "tableName";
		case 3:
			return "className";
		case 4:
			return "packagee";
		case 5:
			return "queryAble";
		case 6:
			return "exportable";
		case 7:
			return "importable";
		case 8:
			return "visiablity";
		}
		return null;
	}

	private Object cellContentsToObject(String strValue, int dataType, String timeFormat, String dicFix) {
		if ((strValue == null) || (strValue.trim().equals(""))) {
			switch (dataType) {
			case 1:
				return strValue;
			case 2:
				return new Integer(0);
			case 3:
				return new Float(0.0F);
			}
			return null;
		}
		try {
			switch (dataType) {
			case 1:
				return strValue;
			case 2:
				return Integer.valueOf(Integer.parseInt(strValue));
			case 3:
				return Float.valueOf(Float.parseFloat(strValue));
			case 4:
				return new SimpleDateFormat(timeFormat).parse(strValue);
			case 5:
				return this.dictionaryDetailService.getDetailByDictionaryNumberAndName(dicFix, strValue);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private Object cellContentsToComplexObject(String strValue, IBaseService service, String className, String valuePath) {
		if (strValue == null) {
			return null;
		}
		strValue = strValue.trim();
		String propValue = strValue.replaceAll("\\[id=\\d{0,}\\]", "");
		String idValue = strValue.replaceAll(propValue, "");
		idValue = idValue.replaceAll("[^\\d]{0,}", "");
		try {
			if ((idValue != null) && (!idValue.trim().equals(""))) {
				return service.findById(Integer.valueOf(Integer.parseInt(idValue)));
			}
			StringBuffer hql = new StringBuffer();
			hql.append("FROM ").append(className).append(" alias WHERE alias.").append(valuePath);
			hql.append("=? ");
			List list = service.findByHql(hql.toString(), new Object[] { propValue });
			if ((list != null) && (list.size() > 0)) {
				return list.get(0);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private EntityAction appendMsg(Object obj) {
		this.resultInfo.append(obj);
		return this;
	}

	public String importProgress() {
		Integer total = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("total") == null ? Integer.valueOf(0) : ServletActionContext.getRequest().getSession().getAttribute("total"));
		Integer rowCount = (Integer) (ServletActionContext.getRequest().getSession().getAttribute("rowCount") == null ? Integer.valueOf(0) : ServletActionContext.getRequest().getSession().getAttribute("rowCount"));
		String msg = (String) ServletActionContext.getRequest().getSession().getAttribute("resultInfo");
		String opera = (String) ServletActionContext.getRequest().getSession().getAttribute("opera");
		msg = msg == null ? "" : msg;
		StringBuffer res = new StringBuffer();
		if (ServletActionContext.getRequest().getParameter("first") != null) {
			res.append("[{\"total\":\"").append(total).append("\",\"rowCount\":\"").append(rowCount).append("\",");
			res.append("\"complate\":\"").append(total.equals(rowCount)).append("\",");
			res.append("\"opera\":\"").append(opera == null ? "" : opera).append("\"}]");
			write(res.toString());
			return null;
		}
		return "success";
	}

	private void write(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(content);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String exportEmptyExcel() throws Exception {
		String cols = "名称;表名称;类名称;所属包;类型(0:普通类,1:枚举);枚举值(示例:Male=男；Female=女);主键生成方式(0:自增长主键,1:指定型主键);是否可查询;是否可导出;是否可导入;对生成器可见";
		List<String> columnNames = new ArrayList();
		for (String col : cols.split(";")) {
			columnNames.add(col);
		}
		createEmptyExcel(columnNames, "实体导入模板");
		return null;
	}

	public void createEmptyExcel(List<String> columnNames, String fileName) throws Exception {
		File fdir = new File("fileUpload");
		if (!fdir.exists()) {
			fdir.mkdir();
		}
		File result = null;
		result = new File(fdir.getAbsoluteFile() + "/" + fileName + ".xls");
		if (!result.exists()) {
			result.createNewFile();
		}
		WritableWorkbook book = Workbook.createWorkbook(result);
		WritableSheet sheet = book.createSheet(fileName, 1);

		WritableFont wf = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false);
		wf.setColour(Colour.WHITE);
		WritableCellFormat wcf = new WritableCellFormat(wf);
		wcf.setAlignment(Alignment.CENTRE);
		wcf.setBackground(Colour.GREEN);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		for (int i = 0; i < columnNames.size(); i++) {
			sheet.setColumnView(i, 20);
			sheet.addCell(new Label(i, 0, (String) columnNames.get(i), wcf));
		}
		book.write();
		book.close();
		this.entityService.downLoad(result.getAbsolutePath(), ServletActionContext.getResponse(), false);
	}

	public String addPageCode() {
		return "success";
	}

	public String updatePageCode() {
		return "success";
	}

	public String listPageCode() {
		return "success";
	}

	public String controllerCode() {
		return "success";
	}

	public String serviceCode() {
		return "success";
	}

	public String serviceImplCode() {
		return "success";
	}

	public String daoCode() {
		return "success";
	}

	public String entity_daoImplCode() {
		return "success";
	}

	public void prepareModel() {
		if (this.id != null) {
			this.entity = ((Entity) this.entityService.findById(this.id));
		} else {
			this.entity = new Entity();
		}
	}

	public String list() {
		initPage();
		Project project = (Project) ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
		if ((project != null) && (this.projectId == null)) {
			this.projectId = project.getId();
		}
		this.projectList = this.projectService.findAll();
		if (this.projectId != null) {
			this.parentPacks = new ArrayList();
			CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.parent is NULL AND p.project.id=?", new Object[] { this.projectId }), this.parentPacks, "", null);
		}
		StringBuffer hql = new StringBuffer("FROM Entity e WHERE 1=1");
		List<Object> params = new ArrayList();
		if (this.packageId != null) {
			hql.append(" AND e.packagee.id=? ");
			params.add(this.packageId);
		} else if (this.projectId != null) {
			hql.append(" AND e.packagee.project.id=? ");
			params.add(this.projectId);
		}
		this.pageBean = this.entityService.findPageBeanOfHql(hql.toString(), this.pageNum.intValue(), this.pageSize.intValue(), params.toArray());
		return "success";
	}

	public String addUI() {
		this.parentPacks = new ArrayList();
		Project project = (Project) ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
		if (project != null) {
			CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.parent is NULL AND p.project.id=?", new Object[] { project.getId() }), this.parentPacks, "", null);
		} else {
			CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.parent is NULL", null), this.parentPacks, "", null);
		}
		StringBuffer hql = new StringBuffer("From Entity e where 1=1 ");
		List<Object> params = new ArrayList();
		if (this.packageId != null) {
			hql.append(" AND e.packagee.id=? ");
			params.add(this.packageId);
		} else if (this.projectId != null) {
			hql.append(" AND e.packagee.project.id=? ");
			params.add(this.projectId);
		}
		this.entityList = this.entityService.findByHql(hql.toString(), params.toArray());

		return "success";
	}

	public String add() {
		if (this.packageId != null) {
			this.entity.setPackagee((Packagee) this.packageService.findById(this.packageId));
		} else {
			this.entity.setPackagee(null);
		}
		if (this.parentId != null) {
			this.entity.setParent((Entity) this.entityService.findById(this.parentId));
		} else {
			this.entity.setParent(null);
		}
		this.entityService.add(this.entity);
		return "reload";
	}

	public String updateUI() {
		this.parentPacks = new ArrayList();
		Project project = (Project) ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
		if (project != null) {
			CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.parent is NULL AND p.project.id=?", new Object[] { project.getId() }), this.parentPacks, "", null);
		} else {
			CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.parent is NULL", null), this.parentPacks, "", null);
		}
		StringBuffer hql = new StringBuffer("From Entity e where 1=1 ");
		List<Object> params = new ArrayList();
		if (this.packageId != null) {
			hql.append(" AND e.packagee.id=? ");
			params.add(this.packageId);
		} else if (this.projectId != null) {
			hql.append(" AND e.packagee.project.id=? ");
			params.add(this.projectId);
		}
		this.entityList = this.entityService.findByHql(hql.toString(), params.toArray());

		return "success";
	}

	public String update() {
		if (this.packageId != null) {
			this.entity.setPackagee((Packagee) this.packageService.findById(this.packageId));
		} else {
			this.entity.setPackagee(null);
		}
		if (this.parentId != null) {
			this.entity.setParent((Entity) this.entityService.findById(this.parentId));
		} else {
			this.entity.setParent(null);
		}
		this.entityService.update(this.entity);
		return "reload";
	}

	public String delete() {
		if (this.ids != null) {
			this.entityService.batchDelete(this.ids);
		}
		return "reload";
	}

	public Integer getPackageId() {
		return this.packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public List<Packagee> getParentPacks() {
		return this.parentPacks;
	}

	public void setParentPacks(List<Packagee> parentPacks) {
		this.parentPacks = parentPacks;
	}

	public List<Project> getProjectList() {
		return this.projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getFormTimeId() {
		return this.formTimeId;
	}

	public void setFormTimeId(String formTimeId) {
		this.formTimeId = formTimeId;
	}

	public Integer getCheckRepeat() {
		return this.checkRepeat;
	}

	public void setCheckRepeat(Integer checkRepeat) {
		this.checkRepeat = checkRepeat;
	}

	public StringBuffer getResultInfo() {
		return this.resultInfo;
	}

	public void setResultInfo(StringBuffer resultInfo) {
		this.resultInfo = resultInfo;
	}

	public Integer[] getOptionalIds() {
		return this.optionalIds;
	}

	public void setOptionalIds(Integer[] optionalIds) {
		this.optionalIds = optionalIds;
	}

	public File getExcelFile() {
		return this.excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public List<Entity> getEntityList() {
		return this.entityList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
