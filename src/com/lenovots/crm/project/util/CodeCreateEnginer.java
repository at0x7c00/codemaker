package com.lenovots.crm.project.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Transient;

import org.apache.log4j.Logger;

import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Property;
import com.sun.star.uno.RuntimeException;


/**
 * 文件操作工具类
 * @author 胡桥
 * Jul 13, 2012  4:35:31 PM
 */
public class CodeCreateEnginer {

	private static Logger logger = Logger.getLogger(CodeCreateEnginer.class);
	private static StringBuffer importCtn = null;
	private static StringBuffer property = null;
	private static StringBuffer method = null;
	private static StringBuffer classBody = null;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final String INTERFACE_PREFIX="I";
	public static final String SERVICE_INTERFACE_ENDFIX="Service";
	
	/**
	 * 文件(夹)复制
	 * @param from 源文件(夹)
	 * @param to 目标
	 * @throws IOException
	 */
	public static void copyFile(File from,File to) throws IOException{
		if(from==null){
			return;
		}
		if(!from.exists()){
			logger.info("文件 "+from.getAbsolutePath() +"不存在");
		}
		if(from.isDirectory()){
			for(File file :from.listFiles()){
				copyFile(file,new File(to,from.getName()));
			}
		}else{
			if(!to.exists()){
				to.mkdirs();
			}
			if(to.isDirectory()){
				to = new File(to,from.getName());
			}
			FileInputStream fis = new FileInputStream(from);
			FileOutputStream fos = new FileOutputStream(to);
			byte[] buffer = new byte[1024]; 
			int len ;
			while((len=fis.read(buffer))!=-1){
				fos.write(buffer, 0, len);
			}
			logger.info("复制文件：从"+from.getAbsolutePath()+"到"+to.getAbsolutePath());
			fos.close();
			fis.close();
		}
	}
	
	/**
	 * 通过包名称创建文件夹
	 * @param root 根路径
	 * @param packagee 包名称 类似于 com.foo.crm
	 */
	public static void makeDirs(String root,String packagee){
		packagee="src."+packagee;
		String[] tmp = packagee.split("\\.");
		packagee = "";
		for(String str:tmp){
			packagee+=str+"\\";
		}
		File file = new File(new File(root),packagee);
		
		if(!file.exists()){
			file.mkdirs();
		}
		logger.info("创建文件夹："+packagee);
	}
	
	/**
	 * 报名转化成文件名
	 * @param packagee
	 * @return
	 */
	public static String packageToDir(String packagee){
		String[] tmp = packagee.split("\\.");
		packagee = "";
		for(String str:tmp){
			packagee+=str+"\\";
		}
		return packagee;
	}
	
	/**
	 * 获取UTF8的文件写入流
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private static OutputStreamWriter getWriter(File file) throws UnsupportedEncodingException, FileNotFoundException{
		return new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
	}
	private static OutputStreamWriter getWriter(File file,boolean append) throws UnsupportedEncodingException, FileNotFoundException{
		return new OutputStreamWriter(new FileOutputStream(file,append),"UTF-8");
	}
	/**
	 * 获取UTF8的文件输入流
	 * @param file
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	private static InputStreamReader getReader(File file) throws UnsupportedEncodingException, FileNotFoundException{
		return new InputStreamReader(new FileInputStream(file),"UTF-8");
	}
	
	/**
	 * 生成java源码文件
	 * @param entity
	 */
	public static void makeJavaSource(String root,Entity entity)throws Exception{
		if(!root.endsWith("\\")){
			root+="\\";
		}
		File file = new File(new File(root+packageToDir(entity.getFullPackageName())),entity.getClassName()+".java");
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStreamWriter out = getWriter(file); 
		importCtn = new StringBuffer();
		property = new StringBuffer();
		method = new StringBuffer();
		classBody = new StringBuffer();
		
		importCtn.append("package ").append(entity.getFullPackageName()).append(";").append("\r\n");
		classBody.append(createClassComment(entity)).append("\r\n");
		classBody.append("@Entity\r\n");
		classBody.append("@Table(name=\"").append((entity.getTableName()!=null && !entity.getTableName().trim().equals(""))?entity.getTableName():"s_"+entity.getLowerClassName()).append("\")\r\n");
		classBody.append("public class ").append(entity.getClassName()).append("{").append("\r\n");
		if(Entity.ID_GENERATE_TYPE_AUTO_INCREMENT==entity.getIdGenerateType()){//自增长主键
			property.append("\tprivate Integer id;\r\n\t");
			method.append("\tpublic void setId(Integer id){\r\n\t\tthis.id=id;\r\n\t}\r\n");
			method.append("@Id\r\n\t");
			method.append("@GeneratedValue(strategy=GenerationType.AUTO)\r\n\t");
			method.append("@Column(columnDefinition=\"integer\")\r\n\t");
			method.append("\tpublic Integer getId(){\r\n\t\treturn this.id;\r\n\t}\r\n");
		}
		
		for(Property prop:entity.getProperties()){
			property.append(propDefinition(prop));
			method.append(getSetDefinition(prop));
		}
		classBody.append(property.toString());
		classBody.append(method.toString());
		importCtn.append(classBody.toString());
		importCtn.append("\r\n}\r\n");
		out.write(importCtn.toString());
		out.close();
	}
	
	//INSERT INTO `s_function_point` (`is_display`, `name`, `order_num`, `url`, `parent`) VALUES ('1', 'fdsafd管理', '1', '11111', '1')
	public static void createPermissionSQL(String root,Entity entity,int incrementId)throws Exception{
		if(!root.endsWith("\\")){
			root+="\\";
		}
		File file = new File(new File(root),"function_point.sql");
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStreamWriter out = getWriter(file,true); 
		StringBuffer content = new StringBuffer();
		content.append(createEntityManageSql(entity,0,incrementId,1)).append(";\r\n");
		content.append(createEntityManageSql(entity,1,incrementId+1,1)).append(";\r\n");
		content.append(createEntityManageSql(entity,2,incrementId+2,1)).append(";\r\n");
		content.append(createEntityManageSql(entity,3,incrementId+3,1)).append(";\r\n");
		content.append(createEntityManageSql(entity,4,incrementId+4,1)).append(";\r\n").append("\r\n");
		out.write(content.toString());
		out.close();
	}
	
	private static String createEntityManageSql(Entity entity,int type,int incrementId,int parentId){
		StringBuffer res = new StringBuffer();
		String name = null;
		String display = null;
		String orderNum = null;
		String url = null;
		String parent = null;
		switch(type){
		case 0://管理
			name=entity.getName()+"管理";
			display = "1";
			orderNum = ""+incrementId;
			url = entity.getLowerClassName()+"/list.do";
			parent = parentId+"";
			break;
		case 1://添加
			name="添加"+entity.getName();
			display = "0";
			orderNum = "1";
			url = entity.getLowerClassName()+"/add.do";
			parent = (incrementId-1)+"";
			break;
		case 2://修改
			name="修改"+entity.getName();
			display = "0";
			orderNum = "2";
			url = entity.getLowerClassName()+"/update.do";
			parent = (incrementId-2)+"";
			break;
		case 3://删除
			name="删除"+entity.getName();
			display = "0";
			orderNum = "3";
			url = entity.getLowerClassName()+"/delete.do";
			parent = (incrementId-3)+"";
			break;
		case 4://查看
			name="查看"+entity.getName();
			display = "0";
			orderNum = "4";
			url = entity.getLowerClassName()+"/detail.do";
			parent = (incrementId-4)+"";
			break;
		}
		res.append("INSERT INTO `sys_function_point` (`id`,`is_display`, `name`, `order_num`, `url`, `parent`) VALUES");
		res.append(" ('")
		.append(incrementId).append("','")
		.append(display).append("', '")
		.append(name).append("', '")
		.append(orderNum).append("', '")
		.append(url).append("', '")
		.append(parent).append("')");
		return res.toString();
	}
	
	/**
	 * 生成enum源码文件
	 * @param entity
	 */
	public static void makeEnumSource(String root,Entity entity)throws Exception{
		if(!root.endsWith("\\")){
			root+="\\";
		}
		File file = new File(new File(root+packageToDir(entity.getFullPackageName())),entity.getClassName()+".java");
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStreamWriter out = getWriter(file); 
		StringBuffer content = new StringBuffer();
		content.append("package ").append(entity.getFullPackageName()).append(";\r\n");
		content.append("import java.util.LinkedHashMap;\r\n");
		content.append("import java.util.Map;\r\n");
		content.append("/**\r\n");
		content.append(" * ").append(entity.getName()).append("\r\n");
		content.append(" * @author auto created by pc").append("\r\n");
		content.append(" * ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())).append("\r\n");
		content.append(" */\r\n");
		content.append("public enum ").append(entity.getClassName()).append(" {").append("\r\n")
		.append(stringToMenuMember(entity.getEnumValue())).append("\r\n")
		.append("private String description;")
		.append("private ").append(entity.getClassName()).append("(String description){this.description = description;}").append("\r\n")
		.append("public String getDescription(){return description;}")
		.append("public final static Map<").append(entity.getClassName()).append(",String> ").append(entity.getLowerClassName()).append("Map = new LinkedHashMap<").append(entity.getClassName()).append(", String>();").append("\r\n")
		.append("static{for(").append(entity.getClassName()).append(" s : ").append(entity.getClassName()).append(".values()){").append(entity.getLowerClassName()).append("Map.put(s, s.getDescription());}}").append("\r\n")
		.append("}");
		out.write(content.toString());
		out.close();
	}
	
	// Male=男;Female=女
	@Deprecated
	private static String stringToMenuMember(String str){
		StringBuffer result = new StringBuffer();
		for(String eqpart : str.split(";")){
			String[] enumpart = eqpart.split("=");
			result.append(enumpart[0]).append("(\"").append(enumpart[1]).append("\")").append(",");
		}
		if(result.length()>0){
			result.delete(result.length()-1, result.length());
		}
		result.append(";");
		return result.toString();
	}
	
	/**
	 * 生成复杂类对应的属性编辑器
	 * @param entity
	 */
	public static void makePropertyEditorJavaSource(String root,Entity entity)throws Exception{
		if(!root.endsWith("\\")){
			root+="\\";
		}
		File folder = new File(root+packageToDir(entity.getFullPackageName()+".propertyeditor"));
		if(!folder.exists()){
			folder.mkdirs();
		}
		File file = new File(folder,entity.getClassName()+"Editor.java");
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStreamWriter out = getWriter(file); 
		StringBuffer content = new StringBuffer();
		String className = entity.getClassName();
		String lowerClassName = entity.getLowerClassName();
		content.append("package ").append(entity.getFullPackageName()).append(".propertyeditor").append(";\r\n");
		content.append("import java.beans.PropertyEditorSupport;");
		content.append("public class ").append(className).append("Editor extends PropertyEditorSupport{");
		content.append("private I").append(className).append("Service ").append(lowerClassName).append("Service;");
		content.append("public ").append(className).append("Editor(I").append(className).append("Service ").append(lowerClassName).append("Service){this.").append(lowerClassName).append("Service = ").append(lowerClassName).append("Service;}");
		content.append("public String getAsText() {");
		content.append(className).append(" ").append(lowerClassName).append(" = (").append(className).append(") getValue();");
		content.append("if(").append(lowerClassName).append(" == null) return \"\";");
		content.append("return String.valueOf(").append(lowerClassName).append(".").append(entity.getIdCoumunGetterName()).append("());");
		content.append("}");
		
		content.append(" public void setAsText(String id) throws IllegalArgumentException {");
		Property idprop = entity.getIdColumnProp();
		//只支持字符串和整型的主键类型
		content.append(className).append(" ").append(lowerClassName).append(" = new ").append(className).append("();");
		if(idprop == null || idprop.getDataType() == Property.DATATYPE_INTEGER){//自增长型主键
			content.append("Integer integerId = null;");
			content.append("try {integerId = Integer.parseInt(id);} catch (Exception e) {}");
			content.append(lowerClassName).append(" = ").append(lowerClassName).append("Service.getById(").append(className).append(".class,integerId);");
		}else{
			content.append(lowerClassName).append(" = ").append(lowerClassName).append("Service.getById(").append(className).append(".class,id);");
		}
		content.append("setValue(").append(lowerClassName).append(");");
		content.append("}");
		content.append("}");
		out.write(content.toString());
		out.close();
	}
	
	/**
	 * 生成Service层代码
	 * @param root
	 * @param entity
	 * @return
	 */
	public static void makeServiceSrc(String templateDir,String outputDir,Entity entity)throws Exception{
		outputDir = outputDir+"\\src\\"+packageToDir(entity.getFullPackageName());
		
		if(outputDir.endsWith("\\")){
			outputDir = outputDir.substring(0,outputDir.length()-1);
		}
		outputDir = outputDir.substring(0,outputDir.lastIndexOf("\\"));
		outputDir +="\\service";
		if(!outputDir.endsWith("\\")){
			outputDir+="\\";
		}
		EntityInfo param = new EntityInfo(entity);
		createJavaSourceFile(templateDir,outputDir,entity,param,"serviceTemplate.java","I"+param.getBeanClassName()+"Service.java",EntityInfo.PACKAGE_TYPE_SERVICE);
	}
	/**
	 * 生成ServiceImpl层代码
	 * @param root
	 * @param entity
	 * @return
	 */
	public static void makeServiceImplSrc(String templateDir,String outputDir,Entity entity)throws Exception{
		outputDir = outputDir+"\\src\\"+packageToDir(entity.getFullPackageName());
		
		if(outputDir.endsWith("\\")){
			outputDir = outputDir.substring(0,outputDir.length()-1);
		}
		outputDir = outputDir.substring(0,outputDir.lastIndexOf("\\"));
		outputDir +="\\service";
		if(!outputDir.endsWith("\\")){
			outputDir+="\\impl\\";
		}
		EntityInfo param = new EntityInfo(entity);
		createJavaSourceFile(templateDir,outputDir,entity,param,"serviceImplTemplate.java",param.getBeanClassName()+"ServiceImpl.java",EntityInfo.PACKAGE_TYPE_SERVICE_IMPL);
	}
	
	/**
	 * 生成Dao层接口代码
	 * @param root
	 * @param entity
	 * @return
	 */
	public static void makeDaoSrc(String templateDir,String outputDir,Entity entity)throws Exception{
		outputDir = outputDir+"\\src\\"+packageToDir(entity.getFullPackageName());
		
		if(outputDir.endsWith("\\")){
			outputDir = outputDir.substring(0,outputDir.length()-1);
		}
		outputDir = outputDir.substring(0,outputDir.lastIndexOf("\\"));
		outputDir +="\\dao";
		if(!outputDir.endsWith("\\")){
			outputDir+="\\";
		}
		EntityInfo param = new EntityInfo(entity);
		createJavaSourceFile(templateDir,outputDir,entity,param,"daoTemplate.java","I"+param.getBeanClassName()+"Dao.java",EntityInfo.PACKAGE_TYPE_DAO);
	}
	
	/**
	 * 生Dao实现层代码
	 * @param templateDir
	 * @param outputDir
	 * @param entity
	 * @throws Exception
	 */
	public static void makeDaoImplSrc(String templateDir,String outputDir,Entity entity)throws Exception{
		outputDir = outputDir+"\\src\\"+packageToDir(entity.getFullPackageName());
		
		if(outputDir.endsWith("\\")){
			outputDir = outputDir.substring(0,outputDir.length()-1);
		}
		outputDir = outputDir.substring(0,outputDir.lastIndexOf("\\"));
		outputDir +="\\dao";
		if(!outputDir.endsWith("\\")){
			outputDir+="\\impl\\";
		}
		EntityInfo param = new EntityInfo(entity);
		createJavaSourceFile(templateDir,outputDir,entity,param,"daoImplTemplate.java",param.getBeanClassName()+"DaoImpl.java",EntityInfo.PACKAGE_TYPE_DAO_IMPL);
	}
	
	/**
	 * 生成Controller层代码
	 * @param root
	 * @param entity
	 * @return
	 */
	public static void makeControllerSrc(String templateDir,String outputDir,Entity entity)throws Exception{
		outputDir = outputDir+"\\src\\"+packageToDir(entity.getFullPackageName());
		logger.info("outputDir="+outputDir);
		if(outputDir.endsWith("\\")){
			outputDir = outputDir.substring(0,outputDir.length()-1);
		}
		outputDir = outputDir.substring(0,outputDir.lastIndexOf("\\"));
		outputDir +="\\controller";
		logger.info("outputDir="+outputDir);
		createJavaSourceFile(templateDir,outputDir,entity,new EntityInfo(entity),"controllerTemplate.java",entity.getClassName()+"Controller.java",EntityInfo.PACKAGE_TYPE_CONTROLLER);
	}
	
	
	
	/**
	 * 生成增删改列表JSP页面
	 * @param root
	 * @param entity
	 * @throws IOException 
	 */
	public static void makeCrudJsp(String templateDir,String root,Entity entity) throws IOException{
		createAddUIJsp(templateDir,root,entity,new PageInfo(entity,PageInfo.PAGETYPE_ADD));
		createListJsp(templateDir,root,entity,new PageInfo(entity,PageInfo.PAGETYPE_LIST));
		createUpdateUIJsp(templateDir,root,entity,new PageInfo(entity,PageInfo.PAGETYPE_UPDATE));
		createDetailJsp(templateDir,root,entity,new PageInfo(entity,PageInfo.PAGETYPE_DETAIL));
		
		
		
		if(entity.getImportable()==1){
			createImportUIJsp(templateDir,root,entity,new PageInfo(entity,PageInfo.PAGETYPE_IMPORT));
		}
	}
	/**
	 * @param templateDir
	 * @param outputFile
	 * @param entity
	 * @throws IOException 
	 */
	private static void createAddUIJsp(String templateDir,String outputDir,Entity entity,PageInfo param) throws IOException{
		createJspPage(templateDir,outputDir,entity,param,"addUITemplate.jsp","add.jsp");
	}
	private static void createListJsp(String templateDir,String outputDir,Entity entity,PageInfo param) throws IOException{
		createJspPage(templateDir,outputDir,entity,param,"listTemplate.jsp","list.jsp");
	}
	private static void createUpdateUIJsp(String templateDir,String outputDir,Entity entity,PageInfo param) throws IOException{
		createJspPage(templateDir,outputDir,entity,param,"updateUITemplate.jsp","update.jsp");
	}
	private static void createDetailJsp(String templateDir,String outputDir,Entity entity,PageInfo param) throws IOException{
		createJspPage(templateDir,outputDir,entity,param,"detailTemplate.jsp","detail.jsp");
	}
	private static void createImportUIJsp(String templateDir,String outputDir,Entity entity,PageInfo param) throws IOException{
		createJspPage(templateDir,outputDir,entity,param,"importUITemplate.jsp","importExcelUI.jsp");
		createJspPage(templateDir,outputDir,entity,param,"importProgressTemplate.jsp","importProgress.jsp");
		createJspPage(templateDir,outputDir,entity,param,"importReportTemplate.jsp","importReport.jsp");
	}
	
	/**
	 * 创建Java源文件<br/>
	 * 该方法主要完成Java源文件的实际路径的生成
	 * @param templateDir 模版位置
	 * @param outputDir 输出目录
	 * @param entity 实体定义
	 * @param param class参数
	 * @param templateFileName 模版文件名
	 * @param javaSourceFileName 生成的java源文件名称
	 */
	private static void createJavaSourceFile(String templateDir,String outputDir,Entity entity,EntityInfo param,String templateFileName,String javaSourceFileName,Integer packageType)throws Exception{
		File javaTemplate = new File(templateDir,templateFileName);
		if(!javaTemplate.exists()){
			logger.error("模版类文件不存在:"+templateFileName);
			return;
		}
		File parent  = new File(outputDir);
		if(!parent.exists()){
			parent.mkdirs();
		}
		File addUIJsp = new File(parent,javaSourceFileName);
		if(!addUIJsp.exists()){
			addUIJsp.createNewFile();
		}
		OutputStreamWriter os = getWriter(addUIJsp);
		InputStreamReader is = getReader(javaTemplate);
		BufferedReader br = new BufferedReader(is);
		String line;
		boolean isBetweenOnlyForImport = false;
		boolean isBetweenOnlyForExport = false;
		while((line=br.readLine())!=null){
			//导入专用区判断
			if(line.indexOf("ONLY_FOR_IMPORT_START")>0){//开始忽略
				isBetweenOnlyForImport = true;
			}else if(line.indexOf("ONLY_FOR_IMPORT_END")>0){//结束忽略
				isBetweenOnlyForImport = false;
				continue;//忽略当前行
			}
			if(isBetweenOnlyForImport && param.getEntity().getImportable()==0){//不能导入并且处于导入专用区域，pass
				continue;
			}
			//导出专用区
			if(line.indexOf("ONLY_FOR_EXPORT_START")>0){//开始忽略
				isBetweenOnlyForExport = true;
			}else if(line.indexOf("ONLY_FOR_EXPORT_END")>0){//结束忽略
				isBetweenOnlyForExport = false;
				continue;//忽略当前行
			}
			if(isBetweenOnlyForExport && param.getEntity().getExportable()==0){//不能导出并且处于导出专用区域，pass
				continue;
			}
			line = replaceClassMark(line,param,packageType);
			os.write(line);
			os.write("\r\n");
		}
		try{
			is.close();
		}catch(Exception e){}
		try{
			os.close();
		}catch(Exception e){}
	}
	
	/**
	 * @param templateDir
	 * @param outputFile
	 * @param entity
	 * @throws IOException 
	 */
	@SuppressWarnings("unused")
	private static void createJspPage(String templateDir,String outputDir,Entity entity,PageInfo param,String templateFileName,String pageName) throws IOException{
		File addUIJspTemplate = new File(templateDir,templateFileName);
		if(!addUIJspTemplate.exists()){
			logger.error("模版页面不存在:"+templateFileName);
			return;
		}
		File parent  = new File(outputDir+"\\"+entity.getLowerClassName());
		if(!parent.exists()){
			parent.mkdirs();
		}
		File addUIJsp = new File(parent,pageName);
		if(!addUIJsp.exists()){
			System.out.println(addUIJsp.getAbsolutePath());
			addUIJsp.createNewFile();
		}
		OutputStreamWriter os = getWriter(addUIJsp);
		InputStreamReader is = getReader(addUIJspTemplate);
		BufferedReader br = new BufferedReader(is);
		String line;
		while((line=br.readLine())!=null){
			line = replaceJspMark(line,param);
			os.write(line);
			os.write("\r\n");
		}
		try{
			is.close();
		}catch(Exception e){}
		try{
			os.close();
		}catch(Exception e){}
	}
	
	/**
	 * 替换Class文件中的标签
	 * @param source
	 * @param param
	 * @return
	 */
	private static String replaceClassMark(String source,EntityInfo param,Integer packageType){
		/*
		 * 包：PACKAGE_VALUE
			对应实体全称:BEAN_FULLCLASSNAME
			对应接口类全称：INTERFACE_FULLCLASSNAME
			简写小写实体名：LOWER_CLASSNAME
			简写正常类名称：BEAN_CLASSNAME
			
		//复杂关联关系的list
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
		
		//list页面的查询table显示、隐藏切换按钮
		QUERY_MENU
		
		//list页面查询table
		QUERY_TABLE
		
		//根据有或者没有的查询条件拼接hql语句
		HAVE_OR_NOTHAVE_CAUSE_FOR_HQL
		
		//默认系统时间的字段的值设置
		DATATYPE_DEFAULT_VALUE_SAVE
		//实体实际意义名称
		ENTITY_REALY_NAME
		
		//主键字段名称
		ID_PROPERTY_NAME
		
		//主键字段类型
		ID_PROPERTY_DATATYPENAME
		
		SET_COMPLEX_NULL_WHEN_NULL_ID
		
		 */
		
		source = source.replaceAll("PACKAGE_VALUE", param.getPackageValue(packageType));
		source = source.replaceAll("BEAN_FULLCLASSNAME",param.getBeanFullClassName());
		source = source.replaceAll("INTERFACE_FULLCLASSNAME",param.getInterfaceFullClassName());
		source = source.replaceAll("LOWER_CLASSNAME",param.getLowerClassName());
		source = source.replaceAll("BEAN_CLASSNAME",param.getBeanClassName());
		source = source.replaceAll("SERVICE_NAME",param.getServiceName());
		source = source.replaceAll("SERVICE_IMPL_NAME",param.getServiceImplName());
		
		source = source.replaceAll("DAO_NAME",param.getDaoName());
		source = source.replaceAll("DAO_IMPL_NAME",param.getDaoImplName());
		
		source = source.replaceAll("CREATE_DATE",param.getCreateDate());
		
		source = source.replaceAll("COMPLEX_LISTS_DEFINE",param.getComplexListsDefine());
		source = source.replaceAll("COMPLEX_IDS_DEFINE",param.getComplexIdsDefine());
		source = source.replaceAll("COMPLEX_SERVICES_DEFINE",param.getComplexServicesDefine());
		source = source.replaceAll("COMPLEX_LIST_PREPARE",param.getComplexListPrepare2());
		source = source.replaceAll("COMPLEX_RELATIONSHIP_SAVE",param.getComplexRelationShipSave());
		source = source.replaceAll("DATATYPE_DEFAULT_VALUE_SAVE",param.getDateTypeDefaultValueSave());
		
		source = source.replaceAll("COMPLEX_LIST_GETTERANDSETTER",param.getComplexListGetterAndSetter());
		source = source.replaceAll("COMPLEX_ID_GETTERANDSETTER",param.getComplexIdGetterAndSetter());
		
		source = source.replaceAll("DATETYPE_DEFINE",param.getDateTypePropDefine());
		source = source.replaceAll("DATETYPE_GETTERNADSETTER",param.getDateTypePropGetterAndSetter());
		
		source = source.replaceAll("HAVE_OR_NOTHAVE_CAUSE_FOR_HQL", param.getHaveOrNotHaveCauseForHql2());
		source = source.replaceAll("ENTITY_REALY_NAME",param.getEntityRealyName());
		
		source = source.replaceAll("EXP_ADD_RECORD_ROWS", param.getExpAddRecordRows());
		source = source.replaceAll("EXP_SETCOLUMNVIEW", param.getExpSetColumnView());
		source = source.replaceAll("EXP_ADD_HEAD", param.getExpAddHead());
		
		source = source.replaceAll("EXPORT_INTERFACE_DEFINE", param.getExportInterfaceDefine());
		source = source.replaceAll("EXP_EXPORT_IN_ACTION", param.getExportInAction());
		
		source = source.replaceAll("IMP_PROPERTY_REALY_NAMES", param.getImpPropertyRealyNames());
		source = source.replaceAll("IMP_PROPERTY_MAPING_SWITCH_CASE", param.getImpPropertyMappingSwitchCase());
		source = source.replaceAll("IMP_ENTITY_ASSIGNMENT", param.getImpEntityAssignment());
		source = source.replaceAll("IMP_AUTO_SYS_DATE", param.getImpAutoSysDate());
		
		source = source.replaceAll("ID_PROPERTY_NAME", param.getIdPropertyName());
		source = source.replaceAll("ID_PROPERTY_DATATYPENAME", param.getIdPropertyDataTypeName());
		
		source = source.replaceAll("SET_COMPLEX_NULL_WHEN_NULL_ID", "");//param.getSetComplexNullValue()
		
		source = source.replaceAll("INIT_PROP_EDITOR", param.getInitPropEditor());
		
		
		return source;
	}
	
	/**
	 * 替换模版页面中的标签
	 * @param source
	 * @param param
	 * @return
	 */
	private static String replaceJspMark(String source,PageInfo param){
		/*
		页面标题：$PAGE_TITLE$
		表格标题：$TAB_TITLE$
		添加链接：$ADD_URL$
		修改链接：$UPDATE_URL$
		修改页面链接：$UPDATEUI_URL$
		添加页面链接：$ADDUI_URL$
		删除链接：$DELETE_URL$
		实体名称：$BEAN_NAME$
		列表表头：$HEAD_TDS$   <----------------
		列表内容：$BODY_TDS$   <----------------
		暂无记录行跨行值：$EMPTY_COLSPAN$   <----------------
		添加输入项：$ADD_INPUT_TRS$   <----------------
		添加输入项：$UPDATE_INPUT_TRS$   <----------------
		实体主键名称:BEAN_ID_NAME
		*/
		source = source.replaceAll("PAGE_TITLE", param.getPageTitle());
		source = source.replaceAll("TAB_TITLE", param.getTabTitle());
		source = source.replaceAll("ADD_URL", param.getAddUrl());
		source = source.replaceAll("UPDATE_URL", param.getUpdateUrl());
		source = source.replaceAll("UPDATEUI_URL", param.getUpdateUIUrl());
		source = source.replaceAll("ADDUI_URL", param.getAddUIUrl());
		source = source.replaceAll("DELETE_URL", param.getDeleteUrl());
		source = source.replaceAll("BEAN_NAME", param.getBeanName());
		source = source.replaceAll("BEAN_ID_NAME", param.getBeanIDName());
		source = source.replace("BEAN_REALY_NAME", param.getBeanRealyName());
		source = source.replace("ENTITY_ID_PROP_NAME", param.getEntity().getIdColumnName());
		
		
		
		
		//source = source.replaceAll("HEAD_TDS", param.getHeadTds());
		//source = source.replaceAll("BODY_TDS", param.getBodyTds());
		
		source = source.replaceAll("EMPTY_COLSPAN", param.getEmptyColspan());
		if(source.trim().equals("BODY_TDS")){
			source = param.getBodyTds2();
		}else if(source.trim().equals("HEAD_TDS")){
			source = param.getHeadTds2();
		}else if(source.trim().equals("ADD_INPUT_TRS")){
			source = param.getAddInputTrs2();
		}else if(source.trim().equals("UPDATE_INPUT_TRS")){
			source = param.getUpdateInputTrs2();
		}else if(source.trim().equals("DETAIL_INPUT_TRS")){
			source = param.getDetailInputTrs();
		}else if(source.trim().equals("QUERY_MENU")){
			source = param.getQueryMenu();
		}else if(source.trim().equals("QUERY_TABLE")){
			source = param.getQueryTable2();
		}else if(source.trim().equals("EXPORT_MENU")){
			source = param.getExportMenu();
		}else if(source.trim().equals("IMPORT_MENU")){
			source = param.getImportMenu();
		}else if(source.trim().equals("IMP_CHECK_REPEAT_CHKS")){
			source = param.getImpCheckRepeatChks();
		}
		return source;
	}
	
	@Deprecated
	private static String createClassComment(Entity entity){
		StringBuffer res = new StringBuffer();
		res.append("/**\r\n");
		res.append(" * ").append(entity.getName()).append("\r\n");
		res.append(" *").append(" @author pc").append("\r\n");
		res.append(" * ").append(sdf.format(new Date())).append("\r\n");
		res.append(" **/");
		return res.toString();
	}
	
	/**
	 * 创建hibernate映射文件
	 */
	public static void makeORMapping(String root,Entity entity)throws Exception{
		if(!root.endsWith("\\")){
			root+="\\";
		}
		File file = new File(new File(root+packageToDir(entity.getFullPackageName())),entity.getClassName()+".hbm.xml");
		if(!file.exists()){
			file.createNewFile();
		}
		OutputStreamWriter out = getWriter(file);
		StringBuffer prefix =new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">");
		prefix.append("<hibernate-mapping package=\"")
		.append(entity.getFullPackageName()).append("\">")
		.append("    <class name=\"").append(entity.getClassName()).append("\" table=\"t_").append(entity.getTableName().toLowerCase()).append("\">")
		.append("<id name=\"id\" type=\"java.lang.Integer\"><column name=\"id\" /><generator class=\"native\" /></id>");
		for(Property prop:entity.getProperties()){
			if(prop.getDataType()==Property.DATATYPE_COMPLEX){
				//复杂类型
				if(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_ONE){
					//多对一
					prefix.append("<many-to-one name=\"").append(prop.getPropName()).append("\" cascade=\"none\" class=\"").append(prop.getComplexEntity().getFullClassName()).append("\" not-found=\"ignore\" column=\"").append(prop.getPropName()).append("Id").append("\"></many-to-one>").append("\r\n");
				}else if(prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY){
					//一对多(set)
					prefix.append("<set name=\"").append(prop.getPropName()).append("\" lazy=\"false\" cascade=\"none\"").append(">\r\n");
					prefix.append("<key column=\"").append(prop.getSetKeyCoumn()).append("\"></key>").append("\r\n");
					prefix.append("<one-to-many class=\"").append(prop.getComplexEntity().getFullClassName()).append("\" not-found=\"ignore\"/>").append("\r\n");
					prefix.append("</set>").append("\r\n");
				}else{
					//多对多
					prefix.append("<set name=\"").append(prop.getPropName()).append("\" table=\"t_").append(prop.getEntity().getLowerClassName()).append("_").append(prop.getComplexEntity().getLowerClassName()).append(prop.getPropName()).append("\" lazy=\"false\" cascade=\"all\" fetch=\"select\"> \").append(\"\r\n");
					prefix.append("   <key column=\"").append(prop.getEntity().getLowerClassName()).append("\"></key>").append("\r\n");
					prefix.append("   <many-to-many class=\"").append(prop.getComplexEntity().getFullClassName()).append("\" column=\"").append(prop.getComplexEntity().getLowerClassName()).append("_Id\" not-found=\"ignore\"></many-to-many>").append("\r\n");
					prefix.append("</set> ").append("\r\n");
				}
			}else if(prop.getDataType()==Property.DATATYPE_DICTIONARY){
				prefix.append("<many-to-one name=\"").append(prop.getPropName()).append("\" cascade=\"none\" class=\"").append("cn.chinacti.crm.admin.entity.DictionaryDetail").append("\" not-found=\"ignore\" column=\"").append(prop.getPropName()).append("Id").append("\"></many-to-one>").append("\r\n");
			}else{
				//简单类型
				prefix.append("<property name=\"").append(prop.getPropName()).append("\" ");
				if(prop.getColumnName()!=null && !prop.getColumnName().trim().equals("")){
					prefix.append(" column=\"").append(prop.getColumnName()).append("\"");
				}
				prefix.append("></property>").append("\r\n");
			}
		}
		prefix.append("    </class>")
		.append("</hibernate-mapping>");
		out.write(prefix.toString());
		out.close();
	}
	
	/**
	 * 字段定义语句生成器
	 * @param prop
	 * @return
	 */
	public static String propDefinition(Property prop){
		StringBuffer res = new StringBuffer();
		res.append(createValidAnnotation(prop));
		res.append("private ")
		.append(getDataTypeName(prop)).append(" ")
		.append(prop.getPropName()).append(";//").append(prop.getName()).append("\r\n");
		
		if(prop.getForQuery()==1 &&
			(prop.getDataType()==Property.DATATYPE_DATE 
			|| prop.getDataType()==Property.DATATYPE_INTEGER
			|| prop.getDataType()==Property.DATATYPE_FLOAT)){
			res.append("private ").append(getDataTypeName(prop)).append(" ").append(prop.getPropName()).append("Start;\r\n");
			res.append("private ").append(getDataTypeName(prop)).append(" ").append(prop.getPropName()).append("End;\r\n");
		}
		
		return res.toString();
	}
	
	/**
	 * 根据字段获取字段类型名称
	 * @param prop
	 * @return
	 */
	public static String getDataTypeName(Property prop){
		switch(prop.getDataType().intValue()){
			case Property.DATATYPE_STRING:
				return "String";
			case Property.DATATYPE_INTEGER:
				return "Integer";
			case Property.DATATYPE_ENUM:
				return prop.getComplexEntity().getClassName();
			case Property.DATATYPE_FLOAT:
				return "Float";
			case Property.DATATYPE_DATE:
				return "Date";
			case Property.DATATYPE_DICTIONARY:
				if(!importCtn.toString().contains("import cn.chinacti.crm.admin.entity.DictionaryDetail;")){
					importCtn.append("import cn.chinacti.crm.admin.entity.DictionaryDetail;\r\n");
				}
				return "DictionaryDetail";
			case Property.DATATYPE_COMPLEX:
				if(prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY || prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY){
					//set
					if(!importCtn.toString().contains("import java.util.Set;")){
						importCtn.append("import java.util.Set;\r\n");
					}
					return "Set<"+prop.getComplexEntity().getClassName()+">";
				}
				return prop.getComplexEntity().getClassName();
			default:
				throw new RuntimeException("property:"+prop.getPropName()+" in "+prop.getEntity().getName()+"  does not appoint a datatype.");
		}
	}
	
	/**
	 * getter 和 setter方法生成器
	 * @param prop
	 * @return
	 */
	public static String getSetDefinition(Property prop){
		StringBuffer res = new StringBuffer();
		res.append("\t");
		//set方法
		res.append("public void set")
		.append(prop.getPropName().substring(0,1).toUpperCase())
		.append(prop.getPropName().substring(1))
		.append("(")
		.append(getDataTypeName(prop)).append(" ").append(prop.getPropName()).append("){\r\n")
		.append("\t\t")
		.append("this.").append(prop.getPropName()).append("=").append(prop.getPropName()).append(";\r\n")
		.append("\t}")
		.append("\r\n");
		
		//get方法
		res.append("\t").append(createJPAAnnotation(prop)).append("public ").append(getDataTypeName(prop)).append(" get")
		.append(prop.getPropName().substring(0,1).toUpperCase())
		.append(prop.getPropName().substring(1))
		.append("(){")
		.append("\r\n")
		.append("\t\t return this.").append(prop.getPropName()).append(";").append("\r\n")
		.append("\t}\r\n");
		
		
		if(prop.getForQuery()==1 &&
				(prop.getDataType()==Property.DATATYPE_DATE 
				|| prop.getDataType()==Property.DATATYPE_INTEGER
				|| prop.getDataType()==Property.DATATYPE_FLOAT)){
				
				res.append("\t@Transient ").append("public ").append(getDataTypeName(prop)).append(" get")
				.append(prop.getPropName().substring(0,1).toUpperCase())
				.append(prop.getPropName().substring(1))
				.append("Start(){")
				.append("\r\n")
				.append("\t\t return this.").append(prop.getPropName()).append("Start;").append("\r\n")
				.append("\t}\r\n");
				
				res.append("\t@Transient ").append("public ").append(getDataTypeName(prop)).append(" get")
				.append(prop.getPropName().substring(0,1).toUpperCase())
				.append(prop.getPropName().substring(1))
				.append("End(){")
				.append("\r\n")
				.append("\t\t return this.").append(prop.getPropName()).append("End;").append("\r\n")
				.append("\t}\r\n");
				
				res.append("public void set")
				.append(prop.getPropName().substring(0,1).toUpperCase())
				.append(prop.getPropName().substring(1))
				.append("Start(")
				.append(getDataTypeName(prop)).append(" ").append(prop.getPropName()).append("Start").append("){\r\n")
				.append("\t\t")
				.append("this.").append(prop.getPropName()).append("Start=").append(prop.getPropName()).append("Start;\r\n")
				.append("\t}")
				.append("\r\n");
				
				res.append("public void set")
				.append(prop.getPropName().substring(0,1).toUpperCase())
				.append(prop.getPropName().substring(1))
				.append("End(")
				.append(getDataTypeName(prop)).append(" ").append(prop.getPropName()).append("End").append("){\r\n")
				.append("\t\t")
				.append("this.").append(prop.getPropName()).append("End=").append(prop.getPropName()).append("End;\r\n")
				.append("\t}")
				.append("\r\n");
		}
		
		
		return res.toString();
	}
	
	/**
	 * 生成验证注解
	 * @param prop
	 * @return
	 */
	private static String createValidAnnotation(Property prop){
		StringBuffer result = new StringBuffer();
		if(prop.getDataType()==Property.DATATYPE_DATE || prop.getDataType()==Property.DATATYPE_ENUM || (prop.getDataType()==Property.DATATYPE_COMPLEX && 
				(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY
				||prop.getRelationType()==Property.RELATIONTYPE_ONE_TO_MANY))){
			return result.toString();
		}
		if(prop.getCanNull()==0){
			if(prop.getDataType()==Property.DATATYPE_STRING){//字符串
				result.append("\t@NotBlank(message=\"").append(prop.getName()).append("不能为空\")").append("\r\n\t");
			}else if(prop.getDataType()==Property.DATATYPE_COMPLEX && 
					(prop.getRelationType()==Property.RELATIONTYPE_MANY_TO_MANY 
							|| prop.getRelationType() == Property.RELATIONTYPE_ONE_TO_MANY)){//集合
				result.append("\t@NotEmpty(message=\"").append(prop.getName()).append("不能为空\")").append("\r\n\t");
			}else{
				result.append("\t@NotNull(message=\"").append(prop.getName()).append("不能为空\")").append("\r\n\t");
			}
		}
		if(Property.DATATYPE_STRING == prop.getDataType() && prop.getLength()>0 && 0==prop.getIsTextStringType()){//字符串长度验证
			result.append("@Length(min=0,max=").append(prop.getLength()).append(",").append("message=\"").append(prop.getName()).append("长度不能超过").append(prop.getLength()).append("\")").append("\r\n\t");
		}
		return result.toString();
	}
	
	/**
	 * 生成JPA注解
	 * @param prop
	 * @return
	 */
	private static String createJPAAnnotation(Property prop){
		StringBuffer result = new StringBuffer();
		//主键
		if(Entity.ENTITY_TYPE_CLASS==prop.getEntity().getType() && Entity.ID_GENERATE_TYPE_ASSIGNED==prop.getEntity().getIdGenerateType()
				&&prop.getIsId()==1){
		    result.append("@Id\r\n\t");
		    result.append("@GeneratedValue(generator=\"").append(prop.getPropName()).append("_generator\")\r\n\t");
		    result.append("@GenericGenerator(name=\"").append(prop.getPropName()).append("_generator\",strategy=\"assigned\")\r\n\t");
		}
		
		if(Property.DATATYPE_COMPLEX != prop.getDataType()){//非复杂类型才有@Column注解
			result.append("@Column(name=\"").append(prop.getColumnOrPropName()).append("\"");
			if(Property.DATATYPE_STRING==prop.getDataType()){
				if(prop.getLength()>0){
					result.append(",length="+prop.getLength());
				}
				if(1==prop.getIsTextStringType()){
					result.append(",columnDefinition=\"text\"");
				}
			}
			if(prop.getCanNull()==0){
				result.append(",nullable="+(prop.getCanNull()==1));
			}
			if(Property.DATATYPE_ENUM == prop.getDataType()){//枚举类型
				result.append(",columnDefinition=\"enum(").append(prop.getComplexEntity().getEnumDefinitionSQL()).append(")\"");
			}
			result.append(")\r\n\t");
			if(Property.DATATYPE_ENUM == prop.getDataType()){//枚举类型
				result.append("@Enumerated(EnumType.STRING)\r\n\t");
			}
		}else if(Property.DATATYPE_COMPLEX == prop.getDataType()){//复杂类型
			switch(prop.getRelationType()){
			case Property.RELATIONTYPE_ONE_TO_MANY:
				result.append("@OneToMany(targetEntity=").append(prop.getComplexEntity().getFullClassName()).append(".class,mappedBy=\"").append(prop.getSetKeyCoumn()).append("\",fetch=FetchType.LAZY)");
				break;
			case Property.RELATIONTYPE_MANY_TO_ONE:
				result.append("@ManyToOne(targetEntity=").append(prop.getComplexEntity().getFullClassName()).append(".class,fetch=FetchType.LAZY)");
				result.append("@JoinColumn(name=\"").append(prop.getColumnOrPropName()).append("\"");
				if(prop.getCanNull()==0){
					result.append(",nullable=false");
				}else{
					result.append(",nullable=true");
				}
				result.append(")\r\n\t");
				result.append("@Fetch(FetchMode.SELECT)\r\n\t");
				break;
			case Property.RELATIONTYPE_ONE_TO_ONE:
				//暂不支持
				break;
			case Property.RELATIONTYPE_MANY_TO_MANY:
				result.append("@ManyToMany(targetEntity=").append(prop.getComplexEntity().getFullClassName()).append(".class)");
				result.append("@JoinTable(name=\"").append(prop.getMiddletable()).append("\",joinColumns={@JoinColumn(name=\"").append(prop.getEntity().getIdColumnNameForForeignUse()).append("\")},inverseJoinColumns={@JoinColumn(name=\"").append(prop.getComplexEntity().getIdColumnNameForForeignUse()).append("\")})");
				break;
			}
		}
		return result.toString();
	}
	
	/**
	 * 判断包路径是否存在
	 * @param packagee
	 * @return
	 */
	public static  boolean packageIsExists(String packagee){
		String[] tmp = packagee.split("\\.");
		packagee = "";
		for(String str:tmp){
			packagee+=str+"\\";
		}
		File file = new File(packagee);
		return file.exists();
	}
}
