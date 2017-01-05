package com.lenovots.crm.project.entity;

import java.util.HashMap;
import java.util.Map;

public class Property
{
  public static final int DATATYPE_STRING = 1;
  public static final int DATATYPE_INTEGER = 2;
  public static final int DATATYPE_FLOAT = 3;
  public static final int DATATYPE_DATE = 4;
  public static final int DATATYPE_DICTIONARY = 5;
  public static final int DATATYPE_COMPLEX = 6;
  public static final int DATATYPE_ENUM = 7;
  public static final int RELATIONTYPE_ONE_TO_ONE = 1;
  public static final int RELATIONTYPE_MANY_TO_MANY = 2;
  public static final int RELATIONTYPE_ONE_TO_MANY = 3;
  public static final int RELATIONTYPE_MANY_TO_ONE = 4;
  public static final int VALIATETYPE_NONE = 0;
  public static final int VALIATETYPE_EMAIL = 1;
  public static final int VALIATETYPE_NUMBER = 2;
  public static final String EN_YEAR_MONTH_DAY_HOUR_MIN_SEC = "yyyy-MM-dd HH:mm:ss";
  public static final String EN_YEAR_MONTH_DAY_HOUR_MIN = "yyyy-MM-dd HH:mm";
  public static final String EN_YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";
  public static final String EN_YEAR_MONTH_DAY = "yyyy-MM-dd";
  public static final String EN_YEAR_MONTH = "yyyy-MM";
  public static final String EN_YEAR = "yyyy";
  public static final String EN_HOUR_MIN_SEC = "HH:mm:ss";
  public static final String EN_HOUR_MIN = "HH:mm";
  public static final String CN_YEAR_MONTH_DAY_HOUR_MIN_SEC = "yyyy年MM月dd日 HH时mm分ss秒";
  public static final String CN_YEAR_MONTH_DAY_HOUR_MIN = "yyyy年MM月dd日 HH时mm分";
  public static final String CN_YEAR_MONTH_DAY_HOUR = "yyyy年MM月dd日 HH时";
  public static final String CN_YEAR_MONTH_DAY = "yyyy年MM月dd日";
  public static final String CN_YEAR_MONTH = "yyyy年MM月";
  public static final String CN_YEAR = "yyyy年";
  public static final String CN_HOUR_MIN_SEC = "HH时mm分ss秒";
  public static final String CN_HOUR_MIN = "HH时mm分";
  public static final Map<String, String> timeFormatConstantsMap = new HashMap();
  private Integer id;
  private Entity entity;
  private String name;
  private String propName;
  private String columnName;
  private String description;
  private Integer length;
  
  static
  {
    timeFormatConstantsMap.put("yyyy-MM-dd HH:mm:ss", "EN_YEAR_MONTH_DAY_HOUR_MIN_SEC");
    timeFormatConstantsMap.put("yyyy-MM-dd HH:mm", "EN_YEAR_MONTH_DAY_HOUR_MIN");
    timeFormatConstantsMap.put("yyyy-MM-dd HH", "EN_YEAR_MONTH_DAY_HOUR");
    timeFormatConstantsMap.put("yyyy-MM-dd", "EN_YEAR_MONTH_DAY");
    timeFormatConstantsMap.put("yyyy-MM", "EN_YEAR_MONTH");
    timeFormatConstantsMap.put("yyyy", "EN_YEAR");
    timeFormatConstantsMap.put("HH:mm:ss", "EN_HOUR_MIN_SEC");
    timeFormatConstantsMap.put("HH:mm", "EN_HOUR_MIN");
    timeFormatConstantsMap.put("yyyy年MM月dd日 HH时mm分ss秒", "CN_YEAR_MONTH_DAY_HOUR_MIN_SEC");
    timeFormatConstantsMap.put("yyyy年MM月dd日 HH时mm分", "CN_YEAR_MONTH_DAY_HOUR_MIN");
    timeFormatConstantsMap.put("yyyy年MM月dd日 HH时", "CN_YEAR_MONTH_DAY_HOUR");
    timeFormatConstantsMap.put("yyyy年MM月dd日", "CN_YEAR_MONTH_DAY");
    timeFormatConstantsMap.put("yyyy年MM月", "CN_YEAR_MONTH");
    timeFormatConstantsMap.put("yyyy年", "CN_YEAR");
    timeFormatConstantsMap.put("HH时mm分ss秒", "CN_HOUR_MIN_SEC");
    timeFormatConstantsMap.put("HH时mm分", "CN_HOUR_MIN");
  }
  
  private Integer canNull = Integer.valueOf(0);
  private Integer dataType;
  private String dictFix;
  private String valuePath;
  private String timeFormat;
  private String dateType;
  private Integer sortValue;
  private Integer display = Integer.valueOf(1);
  private Integer forQuery = Integer.valueOf(0);
  private Integer relationType = Integer.valueOf(0);
  private Entity complexEntity;
  private Integer validateType;
  private Integer maintainOneToManyRelation;
  private Integer isTotalRow = Integer.valueOf(0);
  private Integer isTextArea = Integer.valueOf(0);
  private String setKeyCoumn;
  private Integer briefLength = Integer.valueOf(0);
  private String row = "3";
  private String col = "20";
  private Integer onlyRelationship = Integer.valueOf(0);
  private Integer defaultSysTime = Integer.valueOf(0);
  private String enumValue;
  private String middletable;
  private Integer isId = Integer.valueOf(0);
  private Integer isTextStringType;
  private Integer noPersistence;
  private String precision;
  private Integer isAttachement;
  private Integer isPicture;
  private String attachementDisplayType;
  private Integer select2 = Integer.valueOf(0);
  
  public Integer getId()
  {
    return this.id;
  }
  
  public void setId(Integer id)
  {
    this.id = id;
  }
  
  public Entity getEntity()
  {
    return this.entity;
  }
  
  public void setEntity(Entity entity)
  {
    this.entity = entity;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getPropName()
  {
    return this.propName;
  }
  
  public String getUpperCasePropName()
  {
    return toFirstLetterUpperCase(getPropName());
  }
  
  public String getUpperCaseValuePath()
  {
    if (!getValuePath().contains(".")) {
      return toFirstLetterUpperCase(getValuePath());
    }
    String str = getValuePath();
    StringBuffer result = new StringBuffer();
    String[] strs = str.split("\\.");
    int i = 0;
    for (String s : strs)
    {
      if (i != 0) {
        result.append(",\"");
      }
      result.append(toFirstLetterUpperCase(s));
      i++;
    }
    return result.toString();
  }
  
  private String toFirstLetterUpperCase(String str)
  {
    if (str.length() <= 1) {
      return str.toUpperCase();
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
  }
  
  public void setPropName(String propName)
  {
    this.propName = propName;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public Integer getLength()
  {
    return this.length;
  }
  
  public void setLength(Integer length)
  {
    this.length = length;
  }
  
  public Integer getCanNull()
  {
    return this.canNull;
  }
  
  public void setCanNull(Integer canNull)
  {
    this.canNull = canNull;
  }
  
  public Integer getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(Integer dataType)
  {
    this.dataType = dataType;
  }
  
  public String getDictFix()
  {
    return this.dictFix;
  }
  
  public void setDictFix(String dictFix)
  {
    this.dictFix = dictFix;
  }
  
  public String getValuePath()
  {
    return this.valuePath;
  }
  
  public void setValuePath(String valuePath)
  {
    this.valuePath = valuePath;
  }
  
  public String getTimeFormat()
  {
    return this.timeFormat;
  }
  
  public void setTimeFormat(String timeFormat)
  {
    this.timeFormat = timeFormat;
  }
  
  public Integer getSortValue()
  {
    return this.sortValue;
  }
  
  public void setSortValue(Integer sortValue)
  {
    this.sortValue = sortValue;
  }
  
  public Integer getDisplay()
  {
    return this.display;
  }
  
  public void setDisplay(Integer display)
  {
    this.display = display;
  }
  
  public Integer getRelationType()
  {
    return this.relationType;
  }
  
  public void setRelationType(Integer relationType)
  {
    this.relationType = relationType;
  }
  
  public Entity getComplexEntity()
  {
    return this.complexEntity;
  }
  
  public void setComplexEntity(Entity complexEntity)
  {
    this.complexEntity = complexEntity;
  }
  
  public Integer getValidateType()
  {
    return this.validateType;
  }
  
  public void setValidateType(Integer validateType)
  {
    this.validateType = validateType;
  }
  
  public Integer getIsTotalRow()
  {
    return this.isTotalRow;
  }
  
  public void setIsTotalRow(Integer isTotalRow)
  {
    this.isTotalRow = isTotalRow;
  }
  
  public Integer getIsTextArea()
  {
    return this.isTextArea;
  }
  
  public void setIsTextArea(Integer isTextArea)
  {
    this.isTextArea = isTextArea;
  }
  
  public String getSetKeyCoumn()
  {
    return this.setKeyCoumn;
  }
  
  public void setSetKeyCoumn(String setKeyCoumn)
  {
    this.setKeyCoumn = setKeyCoumn;
  }
  
  public String getRow()
  {
    return this.row;
  }
  
  public void setRow(String row)
  {
    this.row = row;
  }
  
  public String getCol()
  {
    return this.col;
  }
  
  public void setCol(String col)
  {
    this.col = col;
  }
  
  public Integer getBriefLength()
  {
    return this.briefLength;
  }
  
  public void setBriefLength(Integer briefLength)
  {
    this.briefLength = briefLength;
  }
  
  public Integer getOnlyRelationship()
  {
    return this.onlyRelationship;
  }
  
  public void setOnlyRelationship(Integer onlyRelationship)
  {
    this.onlyRelationship = onlyRelationship;
  }
  
  public Integer getForQuery()
  {
    return this.forQuery;
  }
  
  public void setForQuery(Integer forQuery)
  {
    this.forQuery = forQuery;
  }
  
  public Integer getDefaultSysTime()
  {
    return this.defaultSysTime;
  }
  
  public void setDefaultSysTime(Integer defaultSysTime)
  {
    this.defaultSysTime = defaultSysTime;
  }
  
  public String getColumnName()
  {
    return this.columnName;
  }
  
  public void setColumnName(String columnName)
  {
    this.columnName = columnName;
  }
  
  public String getEnumValue()
  {
    return this.enumValue;
  }
  
  public void setEnumValue(String enumValue)
  {
    this.enumValue = enumValue;
  }
  
  public Integer getIsId()
  {
    return this.isId;
  }
  
  public void setIsId(Integer isId)
  {
    this.isId = isId;
  }
  
  public String getMiddletable()
  {
    return this.middletable;
  }
  
  public void setMiddletable(String middletable)
  {
    this.middletable = middletable;
  }
  
  public String getColumnOrPropName()
  {
    return (getColumnName() != null) && (!getColumnName().trim().equals("")) ? getColumnName() : getPropName().toLowerCase();
  }
  
  public String getDataTypeName()
  {
    switch (getDataType().intValue())
    {
    case 1: 
      return "String";
    case 2: 
      return "Integer";
    case 7: 
      return getComplexEntity().getClassName();
    case 3: 
      return "float".equals(getPrecision()) ? "Float" : "Double";
    case 4: 
      return "Date";
    case 5: 
      return "DictionaryDetail";
    case 6: 
      if ((getRelationType().intValue() == 3) || (getRelationType().intValue() == 2)) {
        return "Set<" + getComplexEntity().getClassName() + ">";
      }
      return getComplexEntity().getClassName();
    }
    return null;
  }
  
  public Integer getIsTextStringType()
  {
    return this.isTextStringType;
  }
  
  public void setIsTextStringType(Integer isTextStringType)
  {
    this.isTextStringType = isTextStringType;
  }
  
  public String getSetterName()
  {
    return "set" + getPropName().substring(0, 1).toUpperCase() + getPropName().substring(1);
  }
  
  public String getGetterName()
  {
    return "get" + getPropName().substring(0, 1).toUpperCase() + getPropName().substring(1);
  }
  
  public String getDateType()
  {
    return this.dateType;
  }
  
  public void setDateType(String dateType)
  {
    this.dateType = dateType;
  }
  
  public Integer getNoPersistence()
  {
    return this.noPersistence;
  }
  
  public void setNoPersistence(Integer noPersistence)
  {
    this.noPersistence = noPersistence;
  }
  
  public String getPrecision()
  {
    return this.precision;
  }
  
  public void setPrecision(String precision)
  {
    this.precision = precision;
  }
  
  public Integer getIsAttachement()
  {
    return this.isAttachement;
  }
  
  public void setIsAttachement(Integer isAttachement)
  {
    this.isAttachement = isAttachement;
  }
  
  public Integer getIsPicture()
  {
    return this.isPicture;
  }
  
  public void setIsPicture(Integer isPicture)
  {
    this.isPicture = isPicture;
  }
  
  public String getAttachementDisplayType()
  {
    return this.attachementDisplayType;
  }
  
  public void setAttachementDisplayType(String attachementDisplayType)
  {
    this.attachementDisplayType = attachementDisplayType;
  }
  
  public Integer getMaintainOneToManyRelation()
  {
    return this.maintainOneToManyRelation;
  }
  
  public void setMaintainOneToManyRelation(Integer maintainOneToManyRelation)
  {
    this.maintainOneToManyRelation = maintainOneToManyRelation;
  }
  
  public String getMappedBySetter()
  {
    return "set" + this.setKeyCoumn.substring(0, 1).toUpperCase() + this.setKeyCoumn.substring(1);
  }
  
  public String getMappedByGetter()
  {
    return "get" + this.setKeyCoumn.substring(0, 1).toUpperCase() + this.setKeyCoumn.substring(1);
  }
  
  public String getFmtPattern()
  {
    if (getDateType().equals("date")) {
      return "EN_YEAR_MONTH_DAY";
    }
    return "EN_YEAR_MONTH_DAY_HOUR_MIN_SEC";
  }
  
  public Integer getSelect2()
  {
    return this.select2;
  }
  
  public void setSelect2(Integer select2)
  {
    this.select2 = select2;
  }
}
