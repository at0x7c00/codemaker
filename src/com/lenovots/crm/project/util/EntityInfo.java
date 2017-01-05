package com.lenovots.crm.project.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Property;


/**
 * Java 文件参数
 * @author 胡桥
 * Jul 30, 2012  9:42:04 AM
 */
public class EntityInfo {
	
	Logger logger = Logger.getLogger(EntityInfo.class);
	public static final int PACKAGE_TYPE_SERVICE = 1;
	public static final int PACKAGE_TYPE_SERVICE_IMPL = 2;
	public static final int PACKAGE_TYPE_CONTROLLER = 3;
	public static final int PACKAGE_TYPE_DAO = 4;
	public static final int PACKAGE_TYPE_DAO_IMPL = 5;
	
	/*
	 *  包：PACKAGE_VALUE
		对应实体全称:BEAN_FULLCLASSNAME
		对应接口类全称：INTERFACE_FULLCLASSNAME
		简写小写实体名：LOWER_CLASSNAME
		简写正常类名称：BEAN_CLASSNAME
		SERVICE_NAME
	 */

	private Entity entity;
	private String packageValue;
	private String beanFullClassName;
	private String interfaceFullClassName;
	private String lowerClassName;
	private String beanClassName;
	private String serviceName;
	private String serviceImplName;
	private String daoName;
	private String daoImplName;
	
	/*
	 * 	//复杂关联关系的list
		COMPLEX_LISTS_DEFINE
		//复杂关联关系的Id
		COMPLEX_IDS_DEFINE
		//复杂关联关系的Service
		COMPLEX_SERVICES_DEFINE
		
		//复杂关联关系数据准备
		COMPLEX_LIST_PREPARE
		
		//复杂关联关系保存 
		COMPLEX_RELATIONSHIP_SAVE
		
		//复杂关联关系List的getter和setter
		COMPLEX_LIST_GETTERANDSETTER
		
		//复杂关联关系Id的getter和setter
		COMPLEX_ID_GETTERANDSETTER
		
		//设置列
		EXP_SETCOLUMNVIEW
		
		//设置表头
		EXP_ADD_HEAD
		
		//添加excel的内容记录
		EXP_ADD_RECORD_ROWS
		
		//注册属性编辑器
		INIT_PROP_EDITOR
		
	 */
	private String complexListsDefine;//复杂关联关系的list
	private String complexIdsDefine;//复杂关联关系的Id
	private String complexServicesDefine;//复杂关联关系的Service
	private String dateTypePropDefine;//日期类型数据定义
	
	private String dateTypePropGetterAndSetter;//日期类型的getter和setter
	
	private String complexListPrepare;//复杂关联关系数据准备
	private String complexRelationShipSave;//复杂关联关系保存
	
	private String dateTypeDefaultValueSave;//默认系统时间字段保存
	
	private String sexComplexNullValue;
	
	private String complexListGetterAndSetter;//复杂关联关系List的getter和setter
	private String complexIdGetterAndSetter;//复杂关联关系Id的getter和setter
	
	private String haveOrNotHaveCauseForHql;//根据有或者没有的查询添加拼接hql语句
	
	private String expSetColumnView;
	private String expAddHead;
	private String expAddRecordRows;
	private String expExportInAction;
	
	private String impPropertyRealyNames;
	
	private String impPropertyMappingSwitchCase;
	private String impEntityAssignment;
	private String impAutoSysDate;
	
	private String idPropertyName;//主键字段名称
	private String idPropertyDataTypeName;//主键字段类型名称
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public EntityInfo(Entity entity){
		this.entity = entity;
	}
	
	public Entity getEntity(){
		return entity;
	}
	
	
	public String getIdPropertyName(){
		if(idPropertyName!=null){
			return idPropertyName;
		}
		Property prop = entity.getIdColumnProp();
		if(prop==null){
			idPropertyName="id";
			return idPropertyName;
		}
		idPropertyName = prop.getPropName();
		return idPropertyName;
	}
	
	public String getIdPropertyDataTypeName(){
		if(idPropertyDataTypeName!=null){
			return idPropertyDataTypeName;
		}
		Property prop = entity.getIdColumnProp();
		if(prop==null){
			idPropertyDataTypeName = "Integer";
			return idPropertyDataTypeName;
		}
		idPropertyDataTypeName = prop.getDataTypeName();
		return idPropertyDataTypeName;
	}
	
	public String getPackageValue(Integer packageType) {
		packageValue = entity.getFullPackageName();
		if(packageValue.indexOf(".")>0){
			packageValue = packageValue.substring(0,packageValue.lastIndexOf("."));
		}
		if(packageType==PACKAGE_TYPE_SERVICE){
			packageValue +=".service";
		}else if(packageType == PACKAGE_TYPE_SERVICE_IMPL){
			packageValue +=".service.impl";
		}else if(packageType == PACKAGE_TYPE_CONTROLLER){
			packageValue +=".controller";
		}else if(packageType == PACKAGE_TYPE_DAO){
			packageValue +=".dao";
		}else if(packageType == PACKAGE_TYPE_DAO_IMPL){
			packageValue +=".dao.impl";
		}
		return packageValue;
	}

	public String getBeanFullClassName() {
		if(beanFullClassName ==null ){
			beanFullClassName = entity.getFullClassName();
		}
		return beanFullClassName;
	}

	public String getInterfaceFullClassName() {
		if(interfaceFullClassName == null){
			interfaceFullClassName = getPackageValue(PACKAGE_TYPE_SERVICE)+".I"+entity.getClassName()+"Service";
		}
		return interfaceFullClassName;
	}

	public String getLowerClassName() {
		if(lowerClassName == null){
			lowerClassName = entity.getClassName().substring(0,1).toLowerCase()+entity.getClassName().substring(1);
		}
		return lowerClassName;
	}
	public String getBeanClassName(){
		if(beanClassName == null){
			beanClassName = entity.getClassName();
		}
		beanClassName = ((beanClassName.subSequence(0, 1)+"").toUpperCase())+beanClassName.substring(1);
		return beanClassName;
	}
	
	public String getServiceName(){
		if(serviceName == null){
			serviceName = entity.getName()+"Service接口";
		}
		return serviceName;
	}
	
	public String getServiceImplName(){ 
		if(serviceImplName == null){
			serviceImplName = entity.getName()+"Service实现";
		}
		return serviceImplName;
	}
	public String getDaoName(){
		if(daoName == null){
			daoName = entity.getName()+"Dao接口";
		}
		return daoName;
	}
	
	public String getDaoImplName(){ 
		if(daoImplName == null){
			daoImplName = entity.getName()+"Dao实现";
		}
		return daoImplName;
	}
	
	public String getCreateDate(){
		return sdf.format(new Date());
	}
	
	/**
	 * 复杂关联关系List定义
	 * @return
	 */
	public String getComplexListsDefine() {
		if(complexListsDefine==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
				|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType()==Property.DATATYPE_COMPLEX ||prop.getDataType()==Property.DATATYPE_DICTIONARY){
					res.append("private List<").append(CodeCreateEnginer.getDataTypeName(prop)).append("> ").append(this.getPropListName(prop)).append(";\r\n");
				}
			}
			complexListsDefine = res.toString();
		}
		return complexListsDefine;
	}
	
	/**
	 * 日期类型开始结束定义
	 * @return
	 */
	public String getDateTypePropDefine() {
		if(dateTypePropDefine==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_DATE){//日期类型
					res.append("private Date ").append(prop.getPropName()).append("Start;//").append(prop.getName()).append("\r\n");
					res.append("private Date ").append(prop.getPropName()).append("End;//").append(prop.getName()).append("\r\n");
				}
			}
			dateTypePropDefine = res.toString();
		}
		return dateTypePropDefine;
	}
	
	
	/**
	 * 复杂关联关系ID定义
	 * @return
	 */
	public String getComplexIdsDefine() {
		if(complexIdsDefine==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_COMPLEX ||prop.getDataType()==Property.DATATYPE_DICTIONARY){
					if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
					|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
						continue;
					}
					res.append("private Integer ").append(this.getPropIdName(prop)).append(";\r\n");
				}
			}
			complexIdsDefine = res.toString();
		}
		return complexIdsDefine;
	}
	
	/**
	 * 复杂类型对应的Service定义
	 * @return
	 */
	public String getComplexServicesDefine() {
		if(complexServicesDefine==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
				|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					//continue;
				}
				if(prop.getDataType()==Property.DATATYPE_COMPLEX){
					if(prop.getComplexEntity().getClassName().equals(prop.getEntity().getClassName())){
						//与自身关联的关联关系，不再需要service的定义
						continue;
					}
					res.append("@Resource private ").append(this.getPropServiceName(prop)).append(" ").append(getPropServiceBeanName(prop)).append(";\r\n");
				}else if(prop.getDataType()==Property.DATATYPE_DICTIONARY){
					res.append("@Resource private IDictionaryDetailService dictionaryDetailService").append(";\r\n");
				}
			}
			complexServicesDefine = res.toString();
		}
		return complexServicesDefine;
	}
	
	/**
	 * 为复杂类型的添加或修改页面准备List数据
	 * @return
	 */
	@Deprecated
	public String getComplexListPrepare() {
		if(complexListPrepare==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
				|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType()==Property.DATATYPE_DICTIONARY){//数据字典
					res.append(this.getPropListName(prop)).append(" = ");
					res.append("dictionaryDetailService.getDetailByDictionaryNumber(\"").append(prop.getDictFix()).append("\");\r\n");
				}else if(prop.getDataType()==Property.DATATYPE_COMPLEX){//复杂类型
					res.append(this.getPropListName(prop)).append(" = ");
					res.append(this.getPropServiceBeanName(prop)).append(".findAll();\r\n");
				}
			}
			complexListPrepare = res.toString();
		}
		return complexListPrepare;
	}
	/**
	 * 为复杂类型的添加或修改页面准备List数据
	 * @return
	 */
	public String getComplexListPrepare2() {
		if(complexListPrepare==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
						|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType()==Property.DATATYPE_DICTIONARY){//数据字典
					res.append("//this version does not support dictionary datatype.\r\n");
				}else if(prop.getDataType()==Property.DATATYPE_COMPLEX){//复杂类型
					res.append("List<").append(prop.getDataTypeName()).append("> ").append(prop.getComplexEntity().getLowerClassName()).append("List = ").append(prop.getComplexEntity().getLowerClassName()).append("Service.findAll(").append(prop.getComplexEntity().getClassName()).append(".class);\r\n");
					res.append("request.setAttribute(\"").append(prop.getComplexEntity().getLowerClassName()).append("List\", ").append(prop.getComplexEntity().getLowerClassName()).append("List);\r\n");
				}else if(prop.getDataType()==Property.DATATYPE_ENUM){//枚举类型
					res.append("request.setAttribute(\"").append(prop.getComplexEntity().getLowerClassName()).append("Map\", ").append(prop.getComplexEntity().getClassName()).append(".").append(prop.getComplexEntity().getLowerClassName()).append("Map);");
				}
			}
			complexListPrepare = res.toString();
		}
		return complexListPrepare;
	}
	
	/**
	 * 根据添加或修改时的复杂类型ID保存关联关系
	 * @return
	 */
	public String getComplexRelationShipSave() {
		if(complexRelationShipSave==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
				|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType()==Property.DATATYPE_COMPLEX){// 复杂类型
					res.append("if(").append(this.getPropIdName(prop)).append("==null){");
					res.append("\t").append(prop.getEntity().getEntityBeanName()).append(".").append(this.getPropSetter(prop)).append("(null);");
					res.append("}else{").append(prop.getEntity().getEntityBeanName()).append(".").append(this.getPropSetter(prop))
					.append("(").append(this.getPropServiceBeanName(prop)).append(".findById(").append(this.getPropIdName(prop)).append("));}");
				}else if(prop.getDataType()==Property.DATATYPE_DICTIONARY ){//数据字典 
					res.append("if(").append(this.getPropIdName(prop)).append("==null){");
					res.append("\t").append(prop.getEntity().getEntityBeanName()).append(".").append(this.getPropSetter(prop)).append("(null);");
					res.append("}else{").append(prop.getEntity().getEntityBeanName()).append(".").append(this.getPropSetter(prop))
					.append("(dictionaryDetailService.findById(").append(this.getPropIdName(prop)).append("));}");
				}
			}
			complexRelationShipSave = res.toString();
		}
		return complexRelationShipSave;
	}
	
	public String getDateTypeDefaultValueSave() {
		if(dateTypeDefaultValueSave==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_DATE && 1==prop.getDefaultSysTime()){
					res.append(prop.getEntity().getEntityBeanName()).append(".").append(this.getPropSetter(prop))
					.append("(new Date());");
				}
			}
			dateTypeDefaultValueSave = res.toString();
		}
		return dateTypeDefaultValueSave;
	}
	
	/**
	 * 如果复杂关联关系的对方id为空，设置为null
	 * @return
	 */
	public String getSetComplexNullValue(){
		if(sexComplexNullValue==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_COMPLEX && 4==prop.getRelationType()){
					res.append("if(").append(prop.getEntity().getLowerClassName()).append(".").append(this.getPropGetter(prop)).append("().").append(prop.getComplexEntity().getIdCoumunGetterName()).append("()==null){");
					res.append(prop.getEntity().getEntityBeanName()).append(".").append(this.getPropSetter(prop))
					.append("(null);");
					res.append("}");
				}
			}
			sexComplexNullValue = res.toString();
		}
		return sexComplexNullValue;
	}
	
	
	/**
	 * 复杂类型关联关系对应的List的getter和setter
	 * @return
	 */
	public String getComplexListGetterAndSetter() {
		if(complexListGetterAndSetter==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_DICTIONARY || prop.getDataType()==Property.DATATYPE_COMPLEX){//数据字典 或者 复杂类型
					if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
					|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
						continue;
					}
					res.append(makeGetterAndSetterDefinition("List<"+CodeCreateEnginer.getDataTypeName(prop)+">", prop.getPropName()+"List"));
				}
			}
			complexListGetterAndSetter = res.toString();
		}
		return complexListGetterAndSetter;
	}
	
	/**
	 * 复杂类型关联关系对应的ID的getter和setter
	 * @return
	 */
	public String getComplexIdGetterAndSetter(){
		if(complexIdGetterAndSetter==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_DICTIONARY || prop.getDataType()==Property.DATATYPE_COMPLEX){//数据字典 或者 复杂类型
					if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
					|| prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
						continue;
					}
					res.append(makeGetterAndSetterDefinition("Integer", prop.getPropName()+"Id"));
				}
			}
			complexIdGetterAndSetter = res.toString();
		}
		return complexIdGetterAndSetter;
	} 
	/**
	 * 日期类型的getter和setter
	 * @return
	 */
	public String getDateTypePropGetterAndSetter(){
		if(dateTypePropGetterAndSetter==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()==Property.DATATYPE_DATE){//数据字典 或者 复杂类型
					res.append(makeGetterAndSetterDefinition("Date", prop.getPropName()+"Start"));
					res.append(makeGetterAndSetterDefinition("Date", prop.getPropName()+"End"));
				}
			}
			dateTypePropGetterAndSetter = res.toString();
		}
		return dateTypePropGetterAndSetter;
	}
	
	/**
	 * 复杂类型的字段对应Service接口类名称
	 * @param prop
	 * @return
	 */
	public String getPropServiceName(Property prop){
		return "I"+prop.getComplexEntity().getClassName()+"Service";
	}
	
	/**
	 * 复杂类型的字段对应Service接口bean名称
	 * @return
	 */
	public String getPropServiceBeanName(Property prop){
		return prop.getComplexEntity().getLowerClassName()+"Service";
	}
	
	/**
	 * 复杂类型的字段对应List名称
	 * @param prop
	 * @return
	 */
	public String getPropListName(Property prop){
		return prop.getPropName()+"List";
	}
	
	/**
	 * 复杂类型的字段对应ID名称
	 * @param prop
	 * @return
	 */
	public String getPropIdName(Property prop){
		return prop.getPropName()+"Id";
	}
	
	/**
	 * 字段对应set方法名称
	 * @param prop
	 * @return
	 */
	public String getPropSetter(Property prop){
		return "set"+prop.getPropName().substring(0,1).toUpperCase()+prop.getPropName().substring(1);
	} 
	/**
	 * 字段对应get方法名称
	 * @param prop
	 * @return
	 */
	public String getPropGetter(Property prop){
		return "get"+prop.getPropName().substring(0,1).toUpperCase()+prop.getPropName().substring(1);
	} 
	
	/**
	 * 生成字段getter和setter
	 * @param dataType
	 * @param propName
	 * @return
	 */
	public String makeGetterAndSetterDefinition(String dataType,String propName){
		StringBuffer res = new StringBuffer();
		String upPropName = propName.substring(0,1).toUpperCase()+propName.substring(1);
		//getter
		res.append("public ").append(dataType).append(" get").append(upPropName).append("(){return this.").append(propName).append(";}\r\n");
		//setter
		res.append("public void set").append(upPropName).append("(").append(dataType).append(" ").append(propName).append("){this.").append(propName).append("=").append(propName).append(";}\r\n");
		return res.toString();
	}
	
	
	/**
	 * 根据有或者没有的查询添加拼接hql语句
	 * @return
	 */
	public String getHaveOrNotHaveCauseForHql(){
		if(haveOrNotHaveCauseForHql==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : this.entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
				 ||prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType()==Property.DATATYPE_COMPLEX ||prop.getDataType()==Property.DATATYPE_DICTIONARY){//复杂类型
					res.append("if(").append(prop.getPropName()).append("Id!=null && ").append(prop.getPropName()).append("Id!=-1){");
					res.append("	hql.append(\" AND ").append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append(".id=? \");");
					res.append("	params.add(").append(prop.getPropName()).append("Id").append(");}");
				}else if(prop.getDataType()==Property.DATATYPE_DATE){//日期
					res.append("if(").append(prop.getPropName()).append("Start!=null){");
					res.append("	hql.append(\" AND ").append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append(">= ? \");");
					res.append("	params.add(").append(prop.getPropName()).append("Start);");
					res.append("}");
					res.append("if(").append(prop.getPropName()).append("End!=null){");
					res.append("	hql.append(\" AND ").append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append("<= ? \");");
					res.append("	params.add(").append(prop.getPropName()).append("End);");
					res.append("}");
				}else{//其他
					res.append("if(").append(prop.getEntity().getLowerClassName())
					.append(".").append(this.getPropGetter(prop))
					.append("()!=null");
					if(prop.getDataType()==Property.DATATYPE_STRING){
						res.append(" && ! ").append(prop.getEntity().getLowerClassName());
						res.append(".").append(this.getPropGetter(prop)).append("().trim().equals(\"\")");
					}
					res.append("){")
					.append("hql.append(\" AND ")
					.append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName());
					if(prop.getDataType()==Property.DATATYPE_STRING){//字符串的模糊查询
						res.append(" like ? ");
					}else{
						res.append("=? ");
					}
					res.append("\");\r\n");
					res.append("params.add(");
					if(prop.getDataType()==Property.DATATYPE_STRING){//字符串的模糊查询
						res.append("\"%\"+").append(prop.getEntity().getLowerClassName()).append(".").append(getPropGetter(prop)).append("()+\"%\");}");
					}else{
						res.append(prop.getEntity().getLowerClassName()).append(".").append(getPropGetter(prop)).append("());}");
					}
				}
			}
			haveOrNotHaveCauseForHql = res.toString();
		}
		return haveOrNotHaveCauseForHql;
	}
	/**
	 * 根据有或者没有的查询添加拼接hql语句
	 * @return
	 */
	public String getHaveOrNotHaveCauseForHql2(){
		if(haveOrNotHaveCauseForHql==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : this.entity.getProperties()){
				if(prop.getForQuery()==0 || prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
						||prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType()==Property.DATATYPE_COMPLEX ||prop.getDataType()==Property.DATATYPE_DICTIONARY){//复杂类型
					res.append("if(").append(entity.getPropAccessGetterName(prop)).append("()!=null && ").append(entity.getPropAccessGetterName(prop)).append("().").append(prop.getComplexEntity().getIdCoumunGetterName()).append("() != null){");
					res.append("    criteria.add(Restrictions.eq(\"").append(prop.getPropName()).append(".").append(prop.getComplexEntity().getIdColumnName()).append("\", ").append(entity.getPropAccessGetterName(prop)).append("().").append(prop.getComplexEntity().getIdCoumunGetterName()).append("()));");
					res.append("}");
				}else if(prop.getDataType()==Property.DATATYPE_DATE||prop.getDataType()==Property.DATATYPE_FLOAT || prop.getDataType()==Property.DATATYPE_INTEGER){//日期
					
					res.append("if(").append(entity.getPropAccessGetterName(prop)).append("Start()!=null){");
					res.append("    criteria.add(Restrictions.ge(\"").append(prop.getPropName()).append("\", ").append(entity.getPropAccessGetterName(prop)).append("Start()));");
					res.append("}");
					
					res.append("if(").append(entity.getPropAccessGetterName(prop)).append("End()!=null){");
					res.append("    criteria.add(Restrictions.le(\"").append(prop.getPropName()).append("\", ").append(entity.getPropAccessGetterName(prop)).append("End()));");
					res.append("}");
					
					
				}else{//其他
					
					res.append("if(").append(prop.getEntity().getLowerClassName())
					.append(".").append(this.getPropGetter(prop))
					.append("()!=null");
					if(prop.getDataType()==Property.DATATYPE_STRING){
						res.append(" && ! ").append(prop.getEntity().getLowerClassName());
						res.append(".").append(this.getPropGetter(prop)).append("().trim().equals(\"\")");
					}
					res.append("){")
					
					.append("criteria.add(Restrictions.");
					
					if(prop.getDataType()==Property.DATATYPE_STRING){//字符串的模糊查询
						res.append("like");
						res.append("(\"").append(prop.getPropName()).append("\", ").append(entity.getPropAccessGetterName(prop)).append("(),MatchMode.ANYWHERE));");
					}else{
						res.append("eq");
						res.append("(\"").append(prop.getPropName()).append("\", ").append(entity.getPropAccessGetterName(prop)).append("()));");
					}
					res.append("}");
				}
			}
			haveOrNotHaveCauseForHql = res.toString();
		}
		return haveOrNotHaveCauseForHql;
	}

	/**
	 * 实体实际意义名称
	 * @return
	 */
	public String getEntityRealyName() {
		return entity.getName();
	}
	
	/**
	 * 导出接口定义
	 * @return
	 */
	public String getExportInterfaceDefine(){
		StringBuffer res = null;
		if(entity.getExportable()==0){
			 res =new StringBuffer("");
		}else{
			res =new StringBuffer("public void export(List<"+getBeanClassName()+"> "+getLowerClassName()+"List,HttpServletResponse response) throws Exception;\r\n");
		}
		if(entity.getImportable()==1){
			res.append("public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine);");
		}
		return res.toString();
	}

	/**
	 * 导出时excel的列设置<br/>
	 *  sheet.setColumnView(0, 10);<br/>
		sheet.setColumnView(1, 10);<br/>
		sheet.setColumnView(2, 20);<br/>
	 * @return
	 */
	public String getExpSetColumnView() {
		if(expSetColumnView==null){
			StringBuffer res = new StringBuffer();
			int i=0;
			res.append("sheet.setColumnView(").append(i).append(", 20);\r\n");
			i++;
			for(Property prop : entity.getProperties()){
				res.append("sheet.setColumnView(").append(i).append(", 20);\r\n");
				i++;
			}
			expSetColumnView = res.toString();
		}
		return expSetColumnView;
	}

	/**
	 * 导出时excel的表头设置<br/>
	 * Label label0 = new Label(0,0,"编号",wcf);<br/>
		Label label1 = new Label(1,0,"站台名称",wcf);	<br/>
		Label label2 = new Label(2,0,"所属省份",wcf);<br/>
		...<br/>
		sheet.addCell(label0);<br/>
		sheet.addCell(label1);<br/>
		sheet.addCell(label2);<br/>
		...
	 * @return
	 */
	public String getExpAddHead() {
		if(expAddHead==null){
			StringBuffer res = new StringBuffer();
			int i=0;
			res.append("Label label").append(i).append(" = new Label(").append(i).append(",0,\"数据库编号\",wcf);\r\nsheet.addCell(label").append(i).append(");\r\n\r\n");
			i++;
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==prop.RELATIONTYPE_MANY_TO_MANY || prop.getRelationType()==prop.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				res.append("Label label").append(i).append(" = new Label(").append(i).append(",0,\"").append(prop.getName()).append("\",wcf);\r\nsheet.addCell(label").append(i).append(");\r\n\r\n");
				i++;
			}
			expAddHead = res.toString();
		}
		return expAddHead;
	}

	/**
	 * 添加excel的内容记录<br/>
	 * sheet.addCell(new Label(0,i,CommonUtil.mildToString(LOWER_CLASSNAME.getId())));<br/>
	   sheet.addCell(new Label(1,i,CommonUtil.mildToString(LOWER_CLASSNAME.getName())));<br/>
	   sheet.addCell(new Label(2,i,CommonUtil.mildToString(LOWER_CLASSNAME.getContactor())));<br/>
	 * @return
	 */
	public String getExpAddRecordRows() {
		if(expAddRecordRows==null){
			StringBuffer res = new StringBuffer();
			int i=0;
			res.append("sheet.addCell(new Label(").append(i).append(",i,CommonUtil.mildToString(").append(getLowerClassName()).append(".getId())));\r\n");
			i++;
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==prop.RELATIONTYPE_MANY_TO_MANY || prop.getRelationType()==prop.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				res.append("sheet.addCell(new Label(").append(i).append(",i,CommonUtil.mildToString(");
				
				if(prop.getDataType()==Property.DATATYPE_COMPLEX){//复杂类型
					res.append(getLowerClassName()).append(".").append(getPropGetter(prop)).append("()");
					if(prop.getValuePath()!=null){//如果valuepath不为空，需要往mildToString中继续添加路径参数
						res.append(",");
						for(String str : prop.getValuePath().split("\\.")){
							res.append("\"").append(uppercaseFirstLetter(str)).append("\",");
						}
						res.delete(res.length()-1, res.length());//删除最后的逗号
					}
					res.append(")+\"[id=\"+").append("CommonUtil.mildToString(").append(getLowerClassName()).append(".").append(getPropGetter(prop)).append("()").append(",\"Id\")+").append("\"]\"");
				}else if(prop.getDataType()==Property.DATATYPE_DICTIONARY){//数据字典
					res.append(getLowerClassName()).append(".").append(getPropGetter(prop)).append("()");
					res.append(",\"Name\")");
				}else if(prop.getDataType()==Property.DATATYPE_DATE){
					res.append("DateUtil.format(").append(getLowerClassName()).append(".").append(getPropGetter(prop)).append("(),\"").append(prop.getTimeFormat()).append("\")");
					res.append(")");
				}else if(prop.getDataType()==Property.DATATYPE_ENUM){
					res.append(enumValueExport(prop));
					res.append(")");
				}else{//基本类型
					res.append(getLowerClassName()).append(".").append(getPropGetter(prop)).append("()");
					res.append(")");
				}
				res.append("));\r\n");
				i++;
			}
			expAddRecordRows = res.toString();
		}
		return expAddRecordRows;
	}
	
	private String enumValueExport(Property prop){
		//order.getType()==1?'投诉':order.getType()==2?'举报':'[未知]'
		StringBuffer res = new StringBuffer();
		String key;
		String value;
		if(prop.getEnumValue()!=null){
			for(String singlEnumValue : prop.getEnumValue().split(";")){
				key = singlEnumValue.split("=")[0];
				value = singlEnumValue.split("=")[1];
				res.append(getLowerClassName()).append(".").append(getPropGetter(prop)).append("()==").append(key).append("?");
				res.append("\"").append(value).append("\"").append(":");
			}
		}
		res.append("\"[未知]\"");
		return res.toString();
	}
	
	private String uppercaseFirstLetter(String str){
		if(str==null || str.trim().equals("")){
			return str;
		}
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	/**
	 * action中的export方法体
	 * @return
	 */
	public String getExportInAction(){
		if(entity.getExportable()==0){
			return "";
		}
		if(expExportInAction==null){
			StringBuffer res = new StringBuffer();
			res.append("public String export() throws Exception{");
			res.append("createPageBean(true);");
			res.append(getLowerClassName()).append("Service.export(list, ServletActionContext.getResponse());");
			res.append("return null;");
			res.append("}");
			expExportInAction = res.toString();
		}
		return expExportInAction;
	}
	
	/**
	 * 由;号分隔的实体字段名称字符串<br/>
	 * IMP_PROPERTY_REALY_NAMES
	 * @return
	 */
	public String getImpPropertyRealyNames(){
		if(impPropertyRealyNames==null){
			StringBuffer res = new StringBuffer();
			res.append("数据库编号(可以为空);");
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY || prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				res.append(prop.getName()).append(";");
			}
			res.delete(res.length()-1, res.length());
			impPropertyRealyNames = res.toString();
		}
		return impPropertyRealyNames;
	}
	
	/**
	 * 导入重复验证时从传递的字段id找到对应的字段名称<br/>
	 * IMP_PROPERTY_MAPING_SWITCH_CASE<br/>
	 * eg:<br/>
	 * 	   case 1://公司名称<br/>
			   return "name";<br/>
    	   case 2://地址<br/>
    		   return "address";<br/>
    	   case 3://邮编<br/>
    		   return "postNum";<br/>
    	   case 4://发票抬头<br/>
    		   return "invoiceName";<br/>
	 * @return
	 */
	public String getImpPropertyMappingSwitchCase(){
		if(impPropertyMappingSwitchCase==null){
			StringBuffer res = new StringBuffer();
			int i = 1;//Attention start from 1
			for(Property prop : entity.getProperties()){
				res.append("case ").append(i).append(" : return \"").append(prop.getPropName()).append("\";\r\n");
				i++;
			}
			impPropertyMappingSwitchCase = res.toString();
		}
		return impPropertyMappingSwitchCase;
	}
	/**
	 * 导入时给实体赋值<br/>
	 * IMP_ENTITY_ASSIGNMENT<br/>
	 * eg:<br/>
	 * client.setName(     sheet.getCell(0, i).getContents());
	 * @return
	 */
	public String getImpEntityAssignment(){
		if(impEntityAssignment == null){
			StringBuffer res = new StringBuffer();
			int i = 1;
			for(Property prop : entity.getProperties()){
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY || prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					continue;
				}
				if(prop.getDataType() == Property.DATATYPE_COMPLEX){//复杂类型
					res.append(getLowerClassName()).append(".")
					.append(getPropSetter(prop)).append("((").append(prop.getComplexEntity().getClassName())
					.append(")cellContentsToComplexObject(sheet.getCell(").append(i).append(", i).getContents(),")
					.append(prop.getComplexEntity().getLowerClassName()).append("Service,")
					.append("\"").append(prop.getComplexEntity().getClassName()).append("\",")
					.append("\"").append(prop.getValuePath()).append("\"")
					//.append("\"")
					.append("));");//prop.getValuePath()
				}else{
					if(prop.getDataType()==Property.DATATYPE_ENUM){
						res.append("tempEnumValue=sheet.getCell(").append(i).append(", i).getContents();");
					}
					res.append(getLowerClassName()).append(".")
					.append(getPropSetter(prop)).append("((").append(getDataTypeName(prop.getDataType())).append(")");
					if(prop.getDataType()==Property.DATATYPE_ENUM){
						res.append("cellContentsToObject(").append(enumValueImport(prop)).append(",").append(Property.DATATYPE_INTEGER);
					}else{
						res.append("cellContentsToObject(sheet.getCell(").append(i).append(", i).getContents(),").append(prop.getDataType());
					}
					switch(prop.getDataType()){
						case Property.DATATYPE_INTEGER:
						case Property.DATATYPE_STRING:
						case Property.DATATYPE_ENUM:
						case Property.DATATYPE_FLOAT:
							res.append(",null,null));");
							break;
						case Property.DATATYPE_DATE:
							res.append(",\"").append(prop.getTimeFormat()).append("\",null));");
							break;
						case Property.DATATYPE_DICTIONARY:
							res.append(",null,\"").append(prop.getDictFix()).append("\"));");
							break;
					}
				}
				i++;
				res.append("\r\n");
			}
			impEntityAssignment = res.toString();
		}
		return impEntityAssignment;
	}
	
	
	private String enumValueImport(Property prop){
		//tempEnumValue.equals("投诉")?1:tempEnumValue.equals("举报")?2:null
		StringBuffer res = new StringBuffer();
		String key;
		String value;
		if(prop.getEnumValue()!=null){
			for(String singlEnumValue : prop.getEnumValue().split(";")){
				key = singlEnumValue.split("=")[0];
				value = singlEnumValue.split("=")[1];
				res.append("tempEnumValue.equals(\"").append(value).append("\")?").append(key).append(":");
			}
		}
		res.append("null");
		return res.toString();
	}
	
	/**
	 * 根据数据类型代码获取数据类型的字符串名次
	 * @param dataType
	 * @return
	 */
	private String getDataTypeName(int dataType){
		switch(dataType){
		case Property.DATATYPE_INTEGER:
		case Property.DATATYPE_ENUM:
			return "Integer";
			//整数
		case Property.DATATYPE_FLOAT:
			//小数
			return "Float";
		case Property.DATATYPE_STRING:
			//字符串
			return "String";
		case Property.DATATYPE_DATE:
			//日期
			return "Date";
		case Property.DATATYPE_DICTIONARY:
			//数据字典
			return "DictionaryDetail";
		case Property.DATATYPE_COMPLEX:
			//复杂类型
			return "[Complex]";
		}
		return "unsupport";
	}
	
	/**
	 * 导入时给自动为系统日期的字段赋值<br/>
	 * IMP_AUTO_SYS_DATE<br/>
	 * eg:client.setAddTime(new Date());
	 * @return
	 */
	public String getImpAutoSysDate(){
		if(impAutoSysDate==null){
			StringBuffer res = new StringBuffer();
			for(Property prop : entity.getProperties()){
				if(prop.getDataType()!= Property.DATATYPE_DATE ||(prop.getDefaultSysTime()==null ||prop.getDefaultSysTime()==0)){
					continue;
				}
				res.append(getLowerClassName()).append(".").append(getPropSetter(prop)).append("(new Date());");
			}
			impAutoSysDate = res.toString();
		}
		return impAutoSysDate;
	}

	
	/**
	 * 注册属性编辑器
	 * binder.registerCustomEditor(FunctionPoint.class,new FunctionPointEditor(functionPointService));
	 * @return
	 */
	public String getInitPropEditor() {
		StringBuffer res = new StringBuffer();
		for(Property prop : entity.getProperties()){
			if(prop.getDataType() == Property.DATATYPE_COMPLEX && prop.getRelationType() == Property.RELATIONTYPE_MANY_TO_ONE){
				res.append("binder.registerCustomEditor(").append(prop.getComplexEntity().getClassName()).append(".class,new ").append(prop.getComplexEntity().getClassName()).append("Editor(").append(prop.getComplexEntity().getLowerClassName()).append("Service));");
			}
		}
		return res.toString();
	}
	
}
