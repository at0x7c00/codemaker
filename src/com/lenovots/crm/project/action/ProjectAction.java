package com.lenovots.crm.project.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.SystemConfig;
import com.lenovots.crm.admin.service.ISystemConfigService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.EnvironmentVariable;
import com.lenovots.crm.project.entity.MyFile;
import com.lenovots.crm.project.entity.Packagee;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.entity.Rule;
import com.lenovots.crm.project.service.IEntityService;
import com.lenovots.crm.project.service.IEnvironmentVariableService;
import com.lenovots.crm.project.service.IPackageService;
import com.lenovots.crm.project.service.IProjectService;
import com.lenovots.crm.project.service.IRuleService;
import com.lenovots.crm.util.CommonUtil;
import com.lenovots.crm.util.Constants;
import com.lenovots.crm.util.ELUtil;
import com.lenovots.crm.util.URLUtil;
import com.lenovots.crm.util.ZipCompressor;

@Controller("projectAction")
@Scope("prototype")
public class ProjectAction extends BaseAction<Project> {
	static Logger log = Logger.getLogger(ProjectAction.class);
	@Resource
	private IProjectService projectService;
	@Resource
	private IEntityService entityService;
	@Resource
	private IRuleService ruleService;
	@Resource
	private IEnvironmentVariableService environmentVariableService;
	@Resource
	private ISystemConfigService systemConfigService;
	@Resource
	private IPackageService packageService;
	private Project project;
	private List<Entity> entityList;
	private Integer entityId;
	private Integer[] entityIds;
	private Integer packageId;
	private Integer complexId;
	private Entity entityy;
	private Integer[] ruleIds;
	private Integer[] envirVarIds;
	private List<Rule> ruleList;
	private List<EnvironmentVariable> envirVars;
	private List<Packagee> parentPacks;
	private String dir;
	private Integer ruleId;
	private boolean checkError;
	private static final long serialVersionUID = 8856961694147206209L;

	public void prepareModel() {
		if (this.id != null) {
			this.project = ((Project) this.projectService.findById(this.id));
		} else {
			this.project = new Project();
		}
	}

	public String list() {
		initPage();
		this.pageBean = this.projectService.findPageBeanOfHql("FROM Project", this.pageNum.intValue(), 200, null);
		return "success";
	}

	public String addUI() {
		this.ruleList = this.ruleService.findAll();
		this.envirVars = this.environmentVariableService.findAll();
		return "success";
	}

	public String add() {
		this.projectService.add(this.project);
		if (this.ruleIds != null) {
			for (int i = 0; i < this.ruleIds.length; i++) {
				this.project.getRules().add((Rule) this.ruleService.findById(this.ruleIds[i]));
			}
		}
		if (this.envirVarIds != null) {
			for (int i = 0; i < this.envirVarIds.length; i++) {
				this.project.getVariables().add((EnvironmentVariable) this.environmentVariableService.findById(this.envirVarIds[i]));
			}
		}
		this.projectService.update(this.project);
		return "reload";
	}

	public void prepareBuild() {
		prepareModel();
	}

	public String build() {
		this.project = ((Project) this.projectService.findById(this.id));
		List<Entity> entities = null;
		if ((this.entityIds != null) && (this.entityIds.length > 0)) {
			entities = this.entityService.findByHql("FROM Entity e WHERE e.packagee.project.id=?", new Object[] { this.project.getId() });
		} else if (this.packageId != null) {
			entities = this.entityService.findByHql("FROM Entity e WHERE e.packagee.project.id=? AND e.packagee.id=?", new Object[] { this.project.getId(), this.packageId });
		} else {
			entities = this.entityService.findByHql("FROM Entity e WHERE e.packagee.project.id=? ", new Object[] { this.project.getId() });
		}
		String codeSaveRoot = ServletActionContext.getServletContext().getRealPath(Constants.CODE_SAVE_ROOT) + File.separator + this.project.getName() + File.separator;
		Rule rule = null;
		ELUtil elUtil = ELUtil.getInstance();
		String outputDir = null;
		String outputFileName = null;
		File outputFile = null;
		int i = 0;

		List<Integer> entityIdsList = new ArrayList();
		if(this.entityIds!=null){
			for (Integer entityId : this.entityIds) {
				entityIdsList.add(entityId);
			}
		}
		for (Entity entity : entities) {
			if (entity.getVisiablity().intValue() != 0) {
				if ((entityIdsList.size() == 0) || (entityIdsList.contains(entity.getId()))) {
					i++;
					elUtil.setAttribute("entity", entity);
					rule = (Rule) this.ruleService.findById(this.ruleId);
					try {
						if (!elUtil.causeValidate(entity, rule.getCause())) {
							continue;
						}
						try {
							outputDir = elUtil.parse(rule.getOutputDir());
							outputDir = elUtil.calcuteRelativePath(outputDir);
							outputDir = outputDir.replaceAll("\\.", "\\" + File.separator);

							outputFileName = elUtil.parse(rule.getOutputFileName());
							if (outputDir.startsWith(File.separator)) {
								outputDir = outputDir.substring(1);
							}
							if (!outputDir.endsWith(File.separator)) {
								outputDir = outputDir + File.separator;
							}
							outputFile = new File(codeSaveRoot + outputDir + outputFileName);
						} catch (Exception e) {
							log.error("映射规则输出位置配置错误:" + e.getMessage());
							e.printStackTrace();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
						log.info("查询条件错误:" + rule.getTemplate().getName() + "," + rule.getCause());
					}
					if (outputFile != null) {
						SystemConfig config = (SystemConfig) this.systemConfigService.findById(Integer.valueOf(1));

						String projectRootPath = config.getProjectRootPath();
						if ((projectRootPath == null) || (projectRootPath.trim().equals(""))) {
							write("{status:'ERROR',info:'未配置项目根路径，请到系统设置-->基本设置下进行配置'}");
							return null;
						}
						if (!projectRootPath.endsWith("/")) {
							projectRootPath = projectRootPath + "/";
						}
						String url = projectRootPath + "entity_codeView.action?id=" + entity.getId() + "&ruleId=" + this.ruleId + "&entityIteratorIndex=" + i;
						String content = URLUtil.getURLContent(url);
						if (rule.getTemplate().getType().intValue() == 1) {
							content = content.replaceAll(Constants.LT_R, "<");
							content = content.replaceAll(Constants.GT_R, ">");
							content = content.replaceAll(Constants.DOLLAR_R, "\\$");
						}
						StringBuffer temp = new StringBuffer();
						for (String str : content.split("\r")) {
							if (!str.trim().equals("")) {
								temp.append(str);
							}
						}
						content = temp.toString();
						if ((this.checkError) && (hasException(content))) {
							write("{status:'EXCEPTION',info:'生成代码时遇到异常',entity:" + entity.getId() + ",entityName:'" + entity.getName() + "',rule:" + this.ruleId + ",template:" + rule.getTemplate().getId() + ",index:" + i + "}");
							return null;
						}
						URLUtil.saveContentToFile(content, outputFile, rule.isAppend());
						log.info("url=" + url);
						log.info("生成文件:" + outputFile.getAbsolutePath());
					}
				}
			}
		}
		write("{status:'OK',info:'生成成功!'}");
		return null;
	}

	private boolean hasException(String content) {
		return (content.contains("org.apache.jasper.JasperException")) && (content.contains("stacktraces"));
	}

	private void write(String content) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(content);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String detail() {
		this.ruleList = this.ruleService.findAll();
		this.project = ((Project) this.projectService.findById(this.id));
		return "success";
	}

	public String updateUI() {
		this.ruleList = this.ruleService.findAll();
		this.envirVars = this.environmentVariableService.findAll();
		return "success";
	}

	public String update() {
		Set<Rule> temp = new HashSet();
		Iterator<Rule> iter = this.project.getRules().iterator();
		while (iter.hasNext()) {
			temp.add((Rule) iter.next());
		}
		this.project.getRules().removeAll(temp);
		if (this.ruleIds != null) {
			for (int i = 0; i < this.ruleIds.length; i++) {
				this.project.getRules().add((Rule) this.ruleService.findById(this.ruleIds[i]));
			}
		}
		this.project.getVariables().clear();
		if (this.envirVarIds != null) {
			for (int i = 0; i < this.envirVarIds.length; i++) {
				this.project.getVariables().add((EnvironmentVariable) this.environmentVariableService.findById(this.envirVarIds[i]));
			}
		}
		this.projectService.update(this.project);
		return "reload";
	}

	public String delete() {
		if (this.ids != null) {
			for (Integer id : this.ids) {
				this.project = ((Project) this.projectService.findById(id));
				this.project.getRules().clear();
			}
			this.projectService.batchDelete(this.ids);
		}
		return "reload";
	}

	public String select() {
		this.project = ((Project) this.projectService.findById(this.id));
		ServletActionContext.getRequest().getSession().setAttribute("PROJECT", this.project);
		return "reload";
	}

	public String viewFile() {
		this.project = ((Project) this.projectService.findById(this.id));

		this.parentPacks = new ArrayList();
		CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.parent is NULL AND p.project.id=?", new Object[] { this.project.getId() }), this.parentPacks, "", null);

		String projectRootDir = ServletActionContext.getServletContext().getRealPath(Constants.CODE_SAVE_ROOT + this.project.getName());
		File file = new File(projectRootDir);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (this.dir != null) {
			projectRootDir = projectRootDir + File.separator + this.dir;
			file = new File(projectRootDir);
		}
		if (!file.isFile()) {
			List<MyFile> parents = new ArrayList();
			File parent = new File(file.getAbsolutePath());
			while (!parent.getName().equals(this.project.getName())) {
				parents.add(0, new MyFile(parent, this.project.getName()));
				String fileName = parent.getAbsolutePath();
				fileName = fileName.substring(0, fileName.lastIndexOf(File.separator));
				parent = new File(fileName);
			}
			setAttribute("parents", parents);
			List<MyFile> myfiles = new ArrayList();
			for (File f : file.listFiles()) {
				myfiles.add(new MyFile(f, this.project.getName()));
			}
			setAttribute("files", myfiles);
		}
		return "success";
	}

	public String downloadFile() throws Exception {
		this.project = ((Project) this.projectService.findById(this.id));

		String projectRootDir = ServletActionContext.getServletContext().getRealPath(Constants.CODE_SAVE_ROOT + this.project.getName());
		File zipFile = new File("D:\\temp\\" + new Date().getTime() + ".zip");
		if (!zipFile.exists()) {
			zipFile.createNewFile();
		}
		ZipCompressor zc = new ZipCompressor();
		zc.compress(projectRootDir, zipFile);

		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream out = response.getOutputStream();
		String strFileName = URLEncoder.encode(this.project.getName(), "UTF-8");
		response.setContentType("application/msexcel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + strFileName + ".zip");

		FileInputStream fis = new FileInputStream(zipFile);
		int i = 0;
		byte[] buffer = new byte[1024];
		while ((i = fis.read(buffer)) != -1) {
			out.write(buffer, 0, i);
		}
		fis.close();
		out.close();
		zipFile.delete();
		return null;
	}

	public String checkCode() {
		this.project = ((Project) this.projectService.findById(this.id));
		String fileName = ServletActionContext.getRequest().getParameter("fileName");
		if (fileName != null) {
			String codeSaveRoot = ServletActionContext.getServletContext().getRealPath(Constants.CODE_SAVE_ROOT + this.project.getName());
			File fileToCheck = new File(codeSaveRoot + File.separator + fileName);
			String content = null;
			try {
				content = URLUtil.readFile(fileToCheck, "UTF8");
			} catch (Exception e) {
				content = "读取文件时出现异常：" + e.getMessage();
				e.printStackTrace();
			}
			setAttribute("fileContent", content);
			setAttribute("fileName", fileName);
		}
		this.op = "viewFile";
		return "success";
	}

	public String deleteCode() {
		this.project = ((Project) this.projectService.findById(this.id));
		String deleteDir = ServletActionContext.getRequest().getParameter("deleteDir");
		if (deleteDir != null) {
			String codeSaveRoot = ServletActionContext.getServletContext().getRealPath(Constants.CODE_SAVE_ROOT + this.project.getName());
			File fileToDelete = new File(codeSaveRoot + File.separator + deleteDir);
			cascadeFileDelete(fileToDelete);
		}
		this.op = "viewFile";
		return "redirect";
	}

	private void cascadeFileDelete(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				cascadeFileDelete(f);
			}
		}
		file.delete();
	}

	public List<Entity> getEntityList() {
		return this.entityList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Entity getEntityy() {
		return this.entityy;
	}

	public void setEntityy(Entity entityy) {
		this.entityy = entityy;
	}

	public Integer getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public Integer getComplexId() {
		return this.complexId;
	}

	public void setComplexId(Integer complexId) {
		this.complexId = complexId;
	}

	public Integer[] getRuleIds() {
		return this.ruleIds;
	}

	public void setRuleIds(Integer[] ruleIds) {
		this.ruleIds = ruleIds;
	}

	public List<Rule> getRuleList() {
		return this.ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}

	public String getDir() {
		return this.dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer[] getEnvirVarIds() {
		return this.envirVarIds;
	}

	public void setEnvirVarIds(Integer[] envirVarIds) {
		this.envirVarIds = envirVarIds;
	}

	public List<EnvironmentVariable> getEnvirVars() {
		return this.envirVars;
	}

	public boolean isCheckError() {
		return this.checkError;
	}

	public void setCheckError(boolean checkError) {
		this.checkError = checkError;
	}

	public List<Packagee> getParentPacks() {
		return this.parentPacks;
	}

	public void setParentPacks(List<Packagee> parentPacks) {
		this.parentPacks = parentPacks;
	}

	public Integer getPackageId() {
		return this.packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public Integer[] getEntityIds() {
		return this.entityIds;
	}

	public void setEntityIds(Integer[] entityIds) {
		this.entityIds = entityIds;
	}
}
