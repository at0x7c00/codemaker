package com.lenovots.crm.project.action;

import com.lenovots.crm.admin.service.IDictionaryDetailService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.common.service.IBaseService;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Packagee;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.entity.Property;
import com.lenovots.crm.project.service.IEntityService;
import com.lenovots.crm.project.service.IPackageService;
import com.lenovots.crm.project.service.IProjectService;
import com.lenovots.crm.project.service.IPropertyService;
import com.lenovots.crm.util.CommonUtil;
import com.lenovots.crm.util.PropertityUtil;
import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
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

@Controller("propertyAction")
@Scope("prototype")
public class PropertyAction
  extends BaseAction<Property>
{
  private static Logger logger = Logger.getLogger(PropertyAction.class);
  @Resource
  private IPropertyService propertyService;
  @Resource
  private IEntityService entityService;
  @Resource
  private IProjectService projectService;
  @Resource
  private IPackageService packageService;
  private Property property;
  private List<Entity> entityList;
  private List<Packagee> parentPacks;
  private List<Project> projectList;
  private Integer entityId;
  private Integer packageId;
  private Integer projectId;
  private Integer complexId;
  private Entity entityy;
  private Integer dataType;
  private static final long serialVersionUID = 8856961694147206209L;
  private Integer checkRepeat = Integer.valueOf(0);
  private String formTimeId;
  private StringBuffer resultInfo;
  private List<String> validatePropNames;
  private Integer[] optionalIds;
  private Map<String, Property> existedPropertyMap = new HashMap();
  private String existedPropertyMapKey;
  private File excelFile;
  @Resource
  private IDictionaryDetailService dictionaryDetailService;
  
  public String importExcelUI()
  {
    this.formTimeId = UUID.randomUUID().toString();
    ServletActionContext.getRequest().getSession().setAttribute("formTimeId", this.formTimeId);
    return "success";
  }
  
  public String importExcel()
    throws Exception
  {
    try
    {
      String formTimeIdInSession = (String)ServletActionContext.getRequest().getSession().getAttribute("formTimeId");
      if ((formTimeIdInSession != null) && (formTimeIdInSession.equals(this.formTimeId)))
      {
        long start = System.currentTimeMillis();
        if (this.checkRepeat.intValue() == 1) {
          initValidateProps();
        }
        int success = importPropertyExcel(this.excelFile);
        long end = System.currentTimeMillis();
        ServletActionContext.getRequest().setAttribute("duration", Long.valueOf((end - start) / 1000L));
        ServletActionContext.getRequest().setAttribute("success", Integer.valueOf(success));
        ServletActionContext.getRequest().setAttribute("total", ServletActionContext.getRequest().getSession().getAttribute("total"));
        ServletActionContext.getRequest().getSession().setAttribute("formTimeId", null);
        ServletActionContext.getRequest().getSession().setAttribute("total", null);
        ServletActionContext.getRequest().getSession().setAttribute("rowCount", null);
      }
      else
      {
        this.msg = "表单重复提交";
      }
    }
    catch (BiffException e)
    {
      e.printStackTrace();
      this.msg = "文件格式错误！";
    }
    this.anyAction = "property";
    this.op = "importReport";
    return "anyforward";
  }
  
  public int importPropertyExcel(File file)
    throws Exception
  {
    Property property = null;
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
      try
      {
        ServletActionContext.getRequest().getSession().setAttribute("rowCount", Integer.valueOf(i));
        appendMsg("<div class='Record'>");
        appendMsg("<span class='RowTitle'>第" + i + "行:</span>");
        property = new Property();
        

        property.setEntity((Entity)cellContentsToComplexObject(sheet.getCell(0, i).getContents(), this.entityService, "Entity", "name"));
        property.setName((String)cellContentsToObject(sheet.getCell(1, i).getContents(), 1, null, null));
        property.setPropName((String)cellContentsToObject(sheet.getCell(2, i).getContents(), 1, null, null));
        property.setColumnName((String)cellContentsToObject(sheet.getCell(3, i).getContents(), 1, null, null));
        property.setDataType((Integer)cellContentsToObject(sheet.getCell(4, i).getContents(), 2, null, null));
        property.setIsTextArea((Integer)cellContentsToObject(sheet.getCell(5, i).getContents(), 2, null, null));
        property.setLength((Integer)cellContentsToObject(sheet.getCell(6, i).getContents(), 2, null, null));
        property.setCanNull((Integer)cellContentsToObject(sheet.getCell(7, i).getContents(), 2, null, null));
        property.setRelationType((Integer)cellContentsToObject(sheet.getCell(8, i).getContents(), 2, null, null));
        
        property.setComplexEntity((Entity)cellContentsToComplexObject(sheet.getCell(9, i).getContents(), this.entityService, "Entity", "className"));
        if ((property.getComplexEntity() == null) && (6 == property.getDataType().intValue())) {
          logger.error("complex data type property:" + property.getName() + " does not find a complex entity use entity name : " + sheet.getCell(9, i).getContents());
        }
        property.setValuePath((String)cellContentsToObject(sheet.getCell(10, i).getContents(), 1, null, null));
        property.setSetKeyCoumn((String)cellContentsToObject(sheet.getCell(11, i).getContents(), 1, null, null));
        property.setForQuery((Integer)cellContentsToObject(sheet.getCell(12, i).getContents(), 2, null, null));
        property.setDisplay((Integer)cellContentsToObject(sheet.getCell(13, i).getContents(), 2, null, null));
        
        property.setBriefLength((Integer)cellContentsToObject(sheet.getCell(14, i).getContents(), 2, null, null));
        property.setTimeFormat((String)cellContentsToObject(sheet.getCell(15, i).getContents(), 1, null, null));
        property.setIsTotalRow((Integer)cellContentsToObject(sheet.getCell(16, i).getContents(), 2, null, null));
        property.setRow((String)cellContentsToObject(sheet.getCell(17, i).getContents(), 1, null, null));
        property.setCol((String)cellContentsToObject(sheet.getCell(18, i).getContents(), 1, null, null));
        property.setSortValue((Integer)cellContentsToObject(sheet.getCell(19, i).getContents(), 2, null, null));
        property.setOnlyRelationship((Integer)cellContentsToObject(sheet.getCell(20, i).getContents(), 2, null, null));
        property.setIsId((Integer)cellContentsToObject(sheet.getCell(21, i).getContents(), 2, null, null));
        property.setMiddletable((String)cellContentsToObject(sheet.getCell(22, i).getContents(), 1, null, null));
        property.setIsTextStringType((Integer)cellContentsToObject(sheet.getCell(23, i).getContents(), 2, null, null));
        if ((this.checkRepeat != null) && (this.checkRepeat.intValue() != 0))
        {
          property = getExistPropertyInfo(property);
          if (!this.existedPropertyMapKey.trim().equals(""))
          {
            if (this.existedPropertyMap.get(this.existedPropertyMapKey) != null)
            {
              appendMsg("第" + (i + 1) + "行记录与之前的记录重复，已忽略<br/>");
              appendMsg("</div>");
              logger.info("第" + (i + 1) + "行记录与之前的记录重复，已忽略");
              continue;
            }
            this.existedPropertyMap.put(this.existedPropertyMapKey, property);
          }
        }
        if (property.getId() != null)
        {
          appendMsg("<font color='red'>导入失败:发现重复的字段信息，忽略该条记录。</font>");
          appendMsg("</div>");
        }
        else
        {
          appendMsg("<font color='red'>导入成功</font>");
          appendMsg("</div>");
          
          list.add(property);
          success++;
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
        appendMsg("遇到异常：" + e.getMessage());
        appendMsg("</div>");
        appendMsg("</div>");
      }
    }
    ServletActionContext.getRequest().getSession().setAttribute("rowCount", Integer.valueOf(0));
    ServletActionContext.getRequest().getSession().setAttribute("total", Integer.valueOf(success));
    ServletActionContext.getRequest().getSession().setAttribute("opera", "正在添加数据...");
    if (list.size() > 0)
    {
      List tmpList = new ArrayList();
      int size = 100;
      int count = 0;
      for (int j = 0; j < list.size(); j++)
      {
        tmpList.add(list.get(j));
        if ((tmpList.size() % size == 0) || (j == list.size() - 1))
        {
          ServletActionContext.getRequest().getSession().setAttribute("rowCount", Integer.valueOf(count));
          this.propertyService.batchAdd(tmpList);
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
  
  private Property getExistPropertyInfo(Property property)
  {
    StringBuffer hql = new StringBuffer("FROM Property c WHERE 1=1 ");
    List<Object> paramValues = new ArrayList();
    
    this.existedPropertyMapKey = "";
    for (String propName : this.validatePropNames)
    {
      Object propValue = PropertityUtil.getProp(property, propName);
      if ((propValue != null) && (!"".equals(propValue.toString().trim())))
      {
        hql.append(" AND c.").append(propName).append("=? ");
        paramValues.add(propValue);
      }
      this.existedPropertyMapKey += (propValue == null ? "" : propValue.toString());
    }
    List<Property> tmpList = this.propertyService.findByHql(hql.toString(), paramValues.toArray());
    if ((tmpList != null) && (tmpList.size() > 0))
    {
      appendMsg("<font color='red'>使用已存在的字段信息。</font>");
      return (Property)tmpList.get(0);
    }
    return property;
  }
  
  private void initValidateProps()
  {
    if (this.optionalIds != null)
    {
      this.validatePropNames = new ArrayList();
      for (Integer id : this.optionalIds) {
        this.validatePropNames.add(getPropName(id.intValue()));
      }
    }
  }
  
  private String getPropName(int optionalId)
  {
    switch (optionalId)
    {
    case 1: 
      return "entity";
    case 2: 
      return "isTextArea";
    case 3: 
      return "name";
    case 4: 
      return "propName";
    case 5: 
      return "length";
    case 6: 
      return "canNull";
    case 7: 
      return "dataType";
    case 8: 
      return "dictFix";
    case 9: 
      return "valuePath";
    case 10: 
      return "timeFormat";
    case 11: 
      return "sortValue";
    case 12: 
      return "display";
    case 13: 
      return "forQuery";
    case 14: 
      return "relationType";
    case 15: 
      return "complexEntity";
    case 16: 
      return "isTotalRow";
    case 17: 
      return "setKeyCoumn";
    case 18: 
      return "briefLength";
    case 19: 
      return "row";
    case 20: 
      return "col";
    case 21: 
      return "onlyRelationship";
    }
    return null;
  }
  
  private Object cellContentsToObject(String strValue, int dataType, String timeFormat, String dicFix)
  {
    if ((strValue == null) || (strValue.trim().equals("")))
    {
      switch (dataType)
      {
      case 1: 
        return strValue;
      case 2: 
        return new Integer(0);
      case 3: 
        return new Float(0.0F);
      }
      return null;
    }
    try
    {
      switch (dataType)
      {
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
    }
    catch (Exception e)
    {
      return null;
    }
    return null;
  }
  
  private Object cellContentsToComplexObject(String strValue, IBaseService service, String className, String valuePath)
  {
    if (strValue == null) {
      return null;
    }
    strValue = strValue.trim();
    String propValue = strValue.replaceAll("\\[id=\\d{0,}\\]", "");
    String idValue = strValue.replaceAll(propValue, "");
    idValue = idValue.replaceAll("[^\\d]{0,}", "");
    try
    {
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
    }
    catch (Exception e)
    {
      return null;
    }
    return null;
  }
  
  private PropertyAction appendMsg(Object obj)
  {
    this.resultInfo.append(obj);
    return this;
  }
  
  public String importProgress()
  {
    Integer total = (Integer)(ServletActionContext.getRequest().getSession().getAttribute("total") == null ? Integer.valueOf(0) : ServletActionContext.getRequest().getSession().getAttribute("total"));
    Integer rowCount = (Integer)(ServletActionContext.getRequest().getSession().getAttribute("rowCount") == null ? Integer.valueOf(0) : ServletActionContext.getRequest().getSession().getAttribute("rowCount"));
    String msg = (String)ServletActionContext.getRequest().getSession().getAttribute("resultInfo");
    String opera = (String)ServletActionContext.getRequest().getSession().getAttribute("opera");
    msg = msg == null ? "" : msg;
    StringBuffer res = new StringBuffer();
    if (ServletActionContext.getRequest().getParameter("first") != null)
    {
      res.append("[{\"total\":\"").append(total).append("\",\"rowCount\":\"").append(rowCount).append("\",");
      res.append("\"complate\":\"").append(total.equals(rowCount)).append("\",");
      res.append("\"opera\":\"").append(opera == null ? "" : opera).append("\"}]");
      write(res.toString());
      return null;
    }
    return "success";
  }
  
  private void write(String content)
  {
    try
    {
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.write(content);
      out.flush();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public String exportEmptyExcel()
    throws Exception
  {
    String cols = "所属实体;显示名称;字段名称;列名称;数据类型;文本域显示;字段长度;允许为空;复杂类型关联关系;复杂类型关联实体;valuePath;mappedby;可查询;列表显示;简略显示长度;日期格式;整行显示;row;col;排序值;关系字段;主键;text";
    List<String> columnNames = new ArrayList();
    for (String col : cols.split(";")) {
      columnNames.add(col);
    }
    createEmptyExcel(columnNames, "字段导入模板");
    return null;
  }
  
  public void createEmptyExcel(List<String> columnNames, String fileName)
    throws Exception
  {
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
    for (int i = 0; i < columnNames.size(); i++)
    {
      sheet.setColumnView(i, 20);
      sheet.addCell(new Label(i, 0, (String)columnNames.get(i), wcf));
    }
    book.write();
    book.close();
    this.propertyService.downLoad(result.getAbsolutePath(), ServletActionContext.getResponse(), false);
  }
  
  public void prepareModel()
  {
    if (this.id != null) {
      this.property = ((Property)this.propertyService.findById(this.id));
    } else {
      this.property = new Property();
    }
  }
  
  public String addUI()
  {
    Project project = (Project)ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
    if (project != null) {
      this.entityList = this.entityService.findByHql("FROM Entity e WHERE e.packagee.project.id=? ", new Object[] { project.getId() });
    } else {
      this.entityList = this.entityService.findByHql("FROM Entity", null);
    }
    this.entityy = ((Entity)this.entityService.findById(this.entityId));
    return "success";
  }
  
  public String add()
  {
    this.entityy = ((Entity)this.entityService.findById(this.entityId));
    if (this.entityy == null) {
      return "reload";
    }
    if ((this.property.getDataType().equals(Integer.valueOf(6))) || (this.property.getDataType().equals(Integer.valueOf(7)))) {
      this.property.setComplexEntity((Entity)this.entityService.findById(this.complexId));
    }
    if (this.property.getDataType().equals(Integer.valueOf(5))) {
      this.property.setRelationType(Integer.valueOf(4));
    }
    if (this.property.getRelationType() == null) {
      this.property.setRelationType(Integer.valueOf(0));
    }
    this.entityId = this.entityy.getId();
    this.packageId = this.entityy.getPackagee().getId();
    this.projectId = this.entityy.getPackagee().getProject().getId();
    
    this.property.setEntity(this.entityy);
    this.propertyService.add(this.property);
    this.entityy.getProperties().add(this.property);
    this.entityService.update(this.entityy);
    setAttribute("entityId", this.entityId);
    return "reload";
  }
  
  public String list()
    throws Exception
  {
    initPage();
    Project project = (Project)ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
    if ((project != null) && (this.projectId == null)) {
      this.projectId = project.getId();
    }
    if (this.entityId != null)
    {
      this.entityy = ((Entity)this.entityService.findById(this.entityId));
      this.packageId = this.entityy.getPackagee().getId();
      this.projectId = this.entityy.getPackagee().getProject().getId();
    }
    this.projectList = this.projectService.findAll();
    if (this.packageId != null) {
      this.entityList = this.entityService.findByHql("FROM Entity entity WHERE entity.packagee.id=?", new Object[] { this.packageId });
    } else {
      this.entityList = this.entityService.findAll();
    }
    if (this.projectId != null)
    {
      this.parentPacks = new ArrayList();
      CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.project.id=? AND p.parent is null", new Object[] { this.projectId }), this.parentPacks, "", null);
    }
    String tmpStr = (String)getParameter("entityId");
    if ((tmpStr != null) && (!tmpStr.trim().equals(""))) {
      this.entityId = Integer.valueOf(Integer.parseInt(tmpStr));
    }
    StringBuffer hql = new StringBuffer("FROM Property p WHERE 1=1 ");
    List<Object> params = new ArrayList();
    if (this.entityId != null)
    {
      hql.append(" AND p.entity.id=?");
      params.add(this.entityId);
    }
    else if (this.packageId != null)
    {
      this.entityList = this.entityService.findByHql("FROM Entity e WHERE e.packagee.id=?", new Object[] { this.packageId });
      hql.append(" AND p.entity.packagee.id=?");
      params.add(this.packageId);
    }
    else if (this.projectId != null)
    {
      hql.append(" AND p.entity.packagee.project.id=?");
      params.add(this.projectId);
    }
    if (this.dataType != null)
    {
      hql.append(" AND p.dataType=?");
      params.add(this.dataType);
    }
    this.pageBean = this.propertyService.findPageBeanOfHql(hql.toString(), this.pageNum.intValue(), this.pageSize.intValue(), params.toArray());
    return "success";
  }
  
  public String updateUI()
  {
    Project project = (Project)ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
    if (project != null) {
      this.entityList = this.entityService.findByHql("FROM Entity e WHERE e.packagee.project.id=? ", new Object[] { project.getId() });
    } else {
      this.entityList = this.entityService.findByHql("FROM Entity", null);
    }
    return "success";
  }
  
  public String update()
  {
    this.entityy = ((Entity)this.entityService.findById(this.entityId));
    if (this.entityy == null) {
      return "reload";
    }
    if ((this.property.getDataType().equals(Integer.valueOf(6))) || (this.property.getDataType().equals(Integer.valueOf(7)))) {
      this.property.setComplexEntity((Entity)this.entityService.findById(this.complexId));
    }
    if (this.property.getDataType().equals(Integer.valueOf(5))) {
      this.property.setRelationType(Integer.valueOf(4));
    }
    if (this.property.getRelationType() == null) {
      this.property.setRelationType(Integer.valueOf(0));
    }
    this.property.setEntity(this.entityy);
    if (this.property.getRelationType() == null) {
      this.property.setRelationType(Integer.valueOf(0));
    }
    this.entityy.getProperties().add(this.property);
    this.entityService.update(this.entityy);
    this.entityId = this.entityy.getId();
    this.packageId = this.entityy.getPackagee().getId();
    this.projectId = this.entityy.getPackagee().getProject().getId();
    return "reload";
  }
  
  public String delete()
  {
    if (this.ids != null) {
      this.propertyService.batchDelete(this.ids);
    }
    return "reload";
  }
  
  public List<Entity> getEntityList()
  {
    return this.entityList;
  }
  
  public void setEntityList(List<Entity> entityList)
  {
    this.entityList = entityList;
  }
  
  public Property getProperty()
  {
    return this.property;
  }
  
  public void setProperty(Property property)
  {
    this.property = property;
  }
  
  public Entity getEntityy()
  {
    return this.entityy;
  }
  
  public void setEntityy(Entity entityy)
  {
    this.entityy = entityy;
  }
  
  public Integer getEntityId()
  {
    return this.entityId;
  }
  
  public void setEntityId(Integer entityId)
  {
    this.entityId = entityId;
  }
  
  public Integer getComplexId()
  {
    return this.complexId;
  }
  
  public void setComplexId(Integer complexId)
  {
    this.complexId = complexId;
  }
  
  public List<Project> getProjectList()
  {
    return this.projectList;
  }
  
  public void setProjectList(List<Project> projectList)
  {
    this.projectList = projectList;
  }
  
  public Integer getPackageId()
  {
    return this.packageId;
  }
  
  public void setPackageId(Integer packageId)
  {
    this.packageId = packageId;
  }
  
  public Integer getProjectId()
  {
    return this.projectId;
  }
  
  public void setProjectId(Integer projectId)
  {
    this.projectId = projectId;
  }
  
  public List<Packagee> getParentPacks()
  {
    return this.parentPacks;
  }
  
  public void setParentPacks(List<Packagee> parentPacks)
  {
    this.parentPacks = parentPacks;
  }
  
  public String getFormTimeId()
  {
    return this.formTimeId;
  }
  
  public void setFormTimeId(String formTimeId)
  {
    this.formTimeId = formTimeId;
  }
  
  public Integer getCheckRepeat()
  {
    return this.checkRepeat;
  }
  
  public void setCheckRepeat(Integer checkRepeat)
  {
    this.checkRepeat = checkRepeat;
  }
  
  public StringBuffer getResultInfo()
  {
    return this.resultInfo;
  }
  
  public void setOptionalIds(Integer[] optionalIds)
  {
    this.optionalIds = optionalIds;
  }
  
  public void setExcelFile(File excelFile)
  {
    this.excelFile = excelFile;
  }
  
  public Integer getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(Integer dataType)
  {
    this.dataType = dataType;
  }
}
