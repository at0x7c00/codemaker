package com.lenovots.crm.project.entity;

import com.lenovots.crm.doc.annotation.InnerLabel;
import com.lenovots.crm.project.util.StrUtil;
import com.sun.star.uno.RuntimeException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@InnerLabel(name="entity", desc="实体、或者模型(Domain),又指模型驱动中的model,比如“学生”、“教师”等")
public class Entity
{
  public static final int ID_GENERATE_TYPE_AUTO_INCREMENT = 0;
  public static final int ID_GENERATE_TYPE_ASSIGNED = 1;
  public static final int ENTITY_TYPE_CLASS = 0;
  public static final int ENTITY_TYPE_ENUM = 1;
  private Integer id;
  private String name;
  private String tableName;
  private String className;
  private Packagee packagee;
  private Set<Property> properties;
  private Set<TaskNode> taskNodes;
  private Integer queryAble = Integer.valueOf(1);
  private Integer exportable = Integer.valueOf(0);
  private Integer importable = Integer.valueOf(0);
  private Integer visiablity = Integer.valueOf(1);
  private Integer idGenerateType = Integer.valueOf(0);
  private Integer viewable;
  private Integer type = Integer.valueOf(0);
  private String enumValue;
  private String currentFileFullPackage;
  private String currentFileName;
  private Integer manageKeySupport;
  private Entity parent;
  private Set<Entity> children;
  private Integer historyable = Integer.valueOf(0);
  
  @InnerLabel(name="id", desc="实体id号，自增长int类型")
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  @InnerLabel(name="name", desc="实体意义名称")
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  @InnerLabel(name="tableName", desc="实体映射表名称")
  public String getTableName()
  {
    return this.tableName;
  }
  
  public void setTableName(String tableName)
  {
    this.tableName = tableName;
  }
  
  @InnerLabel(name="className", desc="实体对应类名称")
  public String getClassName()
  {
    return this.className;
  }
  
  public String getEntityBeanName()
  {
    return getLowerClassName();
  }
  
  public void setClassName(String className)
  {
    this.className = className;
  }
  
  public Set<Property> getProperties()
  {
    if (this.properties == null) {
      this.properties = new HashSet();
    }
    return this.properties;
  }
  
  public void setProperties(Set<Property> properties)
  {
    this.properties = properties;
  }
  
  public Packagee getPackagee()
  {
    return this.packagee;
  }
  
  public void setPackagee(Packagee packagee)
  {
    this.packagee = packagee;
  }
  
  public String getFullPackageName()
  {
    String fullClassName = getFullClassName();
    if (fullClassName.indexOf(".") < 0) {
      return null;
    }
    return fullClassName.substring(0, fullClassName.lastIndexOf("."));
  }
  
  public String getLowerClassName()
  {
    return getClassName().substring(0, 1).toLowerCase() + getClassName().substring(1);
  }
  
  public String getFullClassName()
  {
    StringBuffer res = new StringBuffer();
    res.append(getClassName());
    Packagee parent = getPackagee();
    while (parent != null)
    {
      res.insert(0, parent.getName() + ".");
      parent = parent.getParent();
    }
    return res.toString();
  }
  
  public String getIdColumnNameForForeignUse()
  {
    Property idProp = getIdColumnProp();
    if (idProp == null) {
      return this.className.toLowerCase() + "_id";
    }
    return idProp.getPropName();
  }
  
  public String getIdColumnName()
  {
    Property idProp = getIdColumnProp();
    if (idProp == null) {
      return "id";
    }
    return idProp.getPropName();
  }
  
  public Property getIdColumnProp()
  {
    if (this.idGenerateType.intValue() == 0) {
      return null;
    }
    for (Property prop : getProperties()) {
      if (1 == prop.getIsId().intValue()) {
        return prop;
      }
    }
    throw new RuntimeException("entity " + getClassName() + "'s id column is not exists!");
  }
  
  public String getManageIdDataName()
  {
    String manageId = getManageId();
    if (manageId.equals("manageKey")) {
      return "String";
    }
    return getIdColumnProp().getDataTypeName();
  }
  
  public String getManageId()
  {
    if (manageKeySupport()) {
      return "manageKey";
    }
    return getIdColumnName();
  }
  
  private boolean manageKeySupport()
  {
    return (getManageKeySupport() != null) && (getManageKeySupport().intValue() == 1);
  }
  
  public String getIdCoumunGetterName()
  {
    Property idProp = getIdColumnProp();
    if (idProp == null) {
      return "getId";
    }
    return "get" + StrUtil.upperCaseFirstChar(idProp.getPropName());
  }
  
  public String getPropGetterName(Property prop)
  {
    return "get" + StrUtil.upperCaseFirstChar(prop.getPropName());
  }
  
  public String getPropAccessGetterName(Property prop)
  {
    return getLowerClassName() + ".get" + StrUtil.upperCaseFirstChar(prop.getPropName());
  }
  
  public String getEnumDefinitionSQL()
  {
    StringBuffer res = new StringBuffer();
    if ((getEnumValue() == null) || (getEnumValue().trim().equals(""))) {
      return res.toString();
    }
    for (String eqpart : getEnumValue().split(";")) {
      res.append("'").append(eqpart.split("=")[0]).append("',");
    }
    if (res.length() > 0) {
      res.delete(res.length() - 1, res.length());
    }
    return res.toString();
  }
  
  public Integer getQueryAble()
  {
    return this.queryAble;
  }
  
  public void setQueryAble(Integer queryAble)
  {
    this.queryAble = queryAble;
  }
  
  public Integer getExportable()
  {
    return this.exportable;
  }
  
  public void setExportable(Integer exportable)
  {
    this.exportable = exportable;
  }
  
  public Integer getImportable()
  {
    return this.importable;
  }
  
  public void setImportable(Integer importable)
  {
    this.importable = importable;
  }
  
  public Integer getVisiablity()
  {
    return this.visiablity;
  }
  
  public void setVisiablity(Integer visiablity)
  {
    this.visiablity = visiablity;
  }
  
  public Integer getIdGenerateType()
  {
    return this.idGenerateType;
  }
  
  public void setIdGenerateType(Integer idGenerateType)
  {
    this.idGenerateType = idGenerateType;
  }
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
  
  public String getEnumValue()
  {
    return this.enumValue;
  }
  
  public void setEnumValue(String enumValue)
  {
    this.enumValue = enumValue;
  }
  
  public String getStr2Enum()
  {
    StringBuffer result = new StringBuffer();
    for (String eqpart : getEnumValue().split(";")) {
      if (!eqpart.trim().equals(""))
      {
        String[] enumpart = eqpart.split("=");
        try
        {
          result.append(enumpart[0]).append("(\"").append(enumpart[1]).append("\")").append(",");
        }
        catch (Exception e)
        {
          System.out.println(eqpart);
        }
      }
    }
    if (result.length() > 0) {
      result.delete(result.length() - 1, result.length());
    }
    result.append(";");
    return result.toString();
  }
  
  public String getClassComment()
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    StringBuffer res = new StringBuffer();
    res.append("/**\r\n");
    res.append(" * ").append(getName()).append("\r\n");
    res.append(" *").append(" @author pc").append("\r\n");
    res.append(" * ").append(sdf.format(new Date())).append("\r\n");
    res.append(" **/");
    return res.toString();
  }
  
  public Set<TaskNode> getTaskNodes()
  {
    return this.taskNodes;
  }
  
  public void setTaskNodes(Set<TaskNode> taskNodes)
  {
    this.taskNodes = taskNodes;
  }
  
  public String getCurrentFileFullPackage()
  {
    return this.currentFileFullPackage;
  }
  
  public void setCurrentFileFullPackage(String currentFileFullPackage)
  {
    this.currentFileFullPackage = currentFileFullPackage;
  }
  
  public String getCurrentFileName()
  {
    return this.currentFileName;
  }
  
  public void setCurrentFileName(String currentFileName)
  {
    this.currentFileName = currentFileName;
  }
  
  public Integer getViewable()
  {
    return this.viewable;
  }
  
  public void setViewable(Integer viewable)
  {
    this.viewable = viewable;
  }
  
  public Integer getManageKeySupport()
  {
    return this.manageKeySupport;
  }
  
  public void setManageKeySupport(Integer manageKeySupport)
  {
    this.manageKeySupport = manageKeySupport;
  }
  
  public Entity getParent()
  {
    return this.parent;
  }
  
  public void setParent(Entity parent)
  {
    this.parent = parent;
  }
  
  public Set<Entity> getChildren()
  {
    return this.children;
  }
  
  public void setChildren(Set<Entity> children)
  {
    this.children = children;
  }
  
  public List<Property> getFullProperties()
  {
    List<Property> result = new ArrayList();
    addRecursionly(result, this);
    return result;
  }
  
  private void addRecursionly(List<Property> props, Entity entity)
  {
    if (entity.getParent() != null) {
      addRecursionly(props, entity.getParent());
    }
    for (Property p : entity.getProperties()) {
      props.add(p);
    }
  }
  
  public List<Property> getOneToManyTypeProperties()
  {
    List<Property> allProperties = getFullProperties();
    List<Property> result = new ArrayList();
    for (Property prop : allProperties) {
      if ((prop.getDataType().intValue() == 6) && (prop.getRelationType().intValue() == 3)) {
        result.add(prop);
      }
    }
    return result;
  }
  
  public List<Property> getManyToManyTypeProperties()
  {
    List<Property> allProperties = getFullProperties();
    List<Property> result = new ArrayList();
    for (Property prop : allProperties) {
      if ((prop.getDataType().intValue() == 6) && (prop.getRelationType().intValue() == 2)) {
        result.add(prop);
      }
    }
    return result;
  }
  
  public List<Property> getSimpleTypeProperties()
  {
    List<Property> allProperties = getFullProperties();
    List<Property> result = new ArrayList();
    for (Property prop : allProperties) {
      if ((prop.getDataType().intValue() != 6) || ((prop.getRelationType().intValue() != 3) && (prop.getRelationType().intValue() != 2))) {
        result.add(prop);
      }
    }
    return result;
  }
  
  public List<Entity> getParents()
  {
    List<Entity> result = new ArrayList();
    Entity parent = getParent();
    while (parent != null)
    {
      result.add(parent);
      parent = parent.getParent();
    }
    return result;
  }
  
  public Integer getHistoryable()
  {
    return this.historyable;
  }
  
  public void setHistoryable(Integer historyable)
  {
    this.historyable = historyable;
  }
  
  public boolean isHasSortProp()
  {
    return searchProp("sort") != null;
  }
  
  public boolean isHasStatusProp()
  {
    return searchProp("status") != null;
  }
  
  public boolean isHasUsernameProp()
  {
    return searchProp("username") != null;
  }
  
  public boolean isHasNameProp()
  {
    return searchProp("name") != null;
  }
  
  public String getQueryProp()
  {
    if (isHasUsernameProp()) {
      return "username";
    }
    if (isHasNameProp()) {
      return "name";
    }
    if (isHasEqNoProp()) {
      return "eqNo";
    }
    return getManageId();
  }
  
  private boolean isHasEqNoProp()
  {
    return searchProp("eqNo") != null;
  }
  
  public Property searchProp(String propName)
  {
    for (Property prop : getProperties()) {
      if (prop.getPropName().equals(propName)) {
        return prop;
      }
    }
    return null;
  }
}
