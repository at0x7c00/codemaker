package com.lenovots.crm.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.lenovots.crm.admin.entity.Dept;
import com.lenovots.crm.project.entity.Packagee;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 通用工具类
 * @author 胡桥
 * Oct 27, 2011  7:52:06 PM
 */
public class CommonUtil {

	
	/**
	 * 获取对象或对象的属性的字符串值<br/>
	 * @param obj eg student
	 * @param props eg "Teacher","Age" 获取学生的教师的年龄的字符串表单形式
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @return
	 */
	public static  String mildToString(Object obj,String...props) throws Exception{
		if(obj==null){
			return "";
		}else if(obj instanceof String){
			return obj.toString();
		}else if(props.length>0){//复杂类型获取指定字段
			Class clazz=obj.getClass();
			Method m=clazz.getDeclaredMethod("get"+props[0]);
			Object result= m.invoke(obj);
			if(props.length>1){
				String[] newProps=new String[props.length-1]; 
				for(int i=1;i<props.length;i++){
					newProps[i-1]=props[i];
					return mildToString(result,newProps);
				}
			}else{
				return mildToString(result);
			}
		}
		
		return obj.toString();
	}
	
	
	
	
	
	/**
	 * 检查参数是否为空<br/>
	 * 用户Action中的参数检查，在列表页选择查询时，表单的部门约束条件可能为空，字符串为空和Id值为-1均认为是不存在
	 * @param param
	 * @return
	 */
	public static  boolean isExists(Object param){
		if(param==null){
			return false;
		}
		if(param instanceof String){
			return !param.equals("");
		}else if(param instanceof Integer ){
			return !param.equals(-1);
		}
		return true;
	}
	
	/**
	 * 通过年龄获取生日日期
	 * @param age
	 * @return
	 */
	public static Date getBirthday(Integer age){
		if(age==null){
			age=0;
		}
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.YEAR, -age);
		return cal.getTime();
	}
	
	/**
	 * 通过日期获取年龄
	 * @param birthday
	 * @return
	 */
	public static Integer getAge(Date birthday){
		if(birthday==null){
			return 0;
		}
		Calendar cal1=Calendar.getInstance();//开始
		cal1.set(Calendar.HOUR, 0);
		cal1.set(Calendar.MINUTE, 0);
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.YEAR, 1900+birthday.getYear());
		
		Calendar cal2=Calendar.getInstance();//结束
		cal2.set(Calendar.HOUR, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		
		int age=0;
		while(cal1.get(Calendar.YEAR)<cal2.get(Calendar.YEAR)){
			cal1.add(Calendar.YEAR,1);
			age++;
		}
		return age;
	}
	
	public static void main(String[] args) {
		System.out.println(getBirthday(24));
		System.out.println(getAge(new Date("Wed Oct 28 10:24:34 CST 1987")));
	}
	
	/**
	 * 获取随机颜色字符串
	 * @return
	 */
	public static String getRandomColor(){
		String[] colors=new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<6;i++){
			sb.append(colors[new Double(Math.random()*15).intValue()]);
		}
		return sb.toString();
	}
	  
	

	/**
	 * 获取操作人员上传文件的目录<br/>
	 * 如当前时间为:2011-04-17  操作人员名称为admin则返回结果为<br/>
	 * 2011/04/14/admin
	 * @param userName
	 */
	public synchronized static String getMyDir(String userName){
		Calendar cal=Calendar.getInstance();
		return File.separator+cal.get(Calendar.YEAR)+File.separator+(cal.get(Calendar.MONTH)+1)+File.separator+cal.get(Calendar.DATE)+File.separator+userName;
	}
	
	public synchronized static String[] getFileNames(String attNames,List<File> attsFile){
		String[] fileNames=null;  
		if(attNames==null){
			return null;
    	}
		fileNames=attNames.split(";");
		
    	List<File> temps=new ArrayList<File>();
    	for(String name:fileNames){
    		temps.add(new File(name));
    		attsFile.add(new File(name));
    	}
    	  
    	String[] temp= new String[fileNames.length];
    	int index=0;
    	for(String name:fileNames){
    		try {
				temp[index]=URLEncoder.encode(name, "utf-8");//URL编码
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    		index++;
    	}
    	fileNames=temp;
		return temp;
	}
	/**
	 * 获取md5码
	 * @param str
	 * @return
	 */
	public static String  getMd5Code(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return  buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 遍历所有部门信息，生成树状信息List
	 * @param topDepartments
	 * @param prefixCode
	 * @param result
	 */
	public static void tree(Collection<Dept> topDepartments, String prefixCode,List<Dept> result,Integer filterId){
		if(topDepartments==null)
			return;
		int count=0;
		for(Dept subDept:topDepartments){
			count++;
			Dept temp_dep=new Dept();
			temp_dep.setId(subDept.getId());
			temp_dep.setCategory(subDept.getCategory());
			temp_dep.setPhoneNumber1(subDept.getPhoneNumber1());
			temp_dep.setPhoneNumber2(subDept.getPhoneNumber2());
			String prefix=parsePrefix(prefixCode);
			if(count==topDepartments.size()){
				if(filterId==null||subDept.getId()!=filterId){
				    temp_dep.setName(prefix+"┗━"+subDept.getName());
				    result.add(temp_dep);
				    tree(subDept.getSubDeptList(),prefixCode+"0",result,filterId);
				}
			}
			else{
				if(filterId==null||subDept.getId()!=filterId){
					temp_dep.setName(prefix+"┣━"+subDept.getName());
					result.add(temp_dep);
					tree(subDept.getSubDeptList(),prefixCode+"1",result,filterId);
				}
			}
		}
	}
	
	/**
	 * 解析前缀，生成复制Department的名称的前缀
	 * @param prefixCode
	 * @return
	 */
	public static String parsePrefix(String prefixCode) {
		String str=" ";
		char[] array=prefixCode.toCharArray();
		for(int i=0;i<array.length;i++){
			char c=array[i];
			if(c=='1'){
				str+="┃&nbsp;&nbsp;";
			}else{
				str+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		}
		return str;
	}
	
	
	/**
	 * 遍历所有部门信息，生成树状信息List
	 * @param topDepartments
	 * @param prefixCode
	 * @param result
	 */
	public static void tree(Collection<Packagee> topDepartments, List<Packagee> result,String prefixCode,Integer filterId){
		if(topDepartments==null)
			return;
		int count=0;
		for(Packagee subPackagee:topDepartments){
			count++;
			Packagee temp_dep=new Packagee();
			temp_dep.setId(subPackagee.getId());
			temp_dep.setProject(subPackagee.getProject());
			temp_dep.setParent(subPackagee.getParent());
			String prefix=parsePrefix(prefixCode);
			if(count==topDepartments.size()){
				if(filterId==null||subPackagee.getId()!=filterId){
					temp_dep.setName(prefix+"┗━"+subPackagee.getName());
					result.add(temp_dep);
					tree(subPackagee.getChildren(),result,prefixCode+"0",filterId);
				}
			}
			else{
				if(filterId==null||subPackagee.getId()!=filterId){
					temp_dep.setName(prefix+"┣━"+subPackagee.getName());
					result.add(temp_dep);
					tree(subPackagee.getChildren(),result,prefixCode+"1",filterId);
				}
			}
		}
	}

	
	
	
	
	public static Object getComponent(String beanId){
		return WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext()).getBean(beanId);
	}
	
	public static String convertNull(String str){
		if(str == null)
			return "";
		return str.trim();
	}
	
	/**
	 * html过滤器
	 * @param source
	 * @return
	 */
	public static String htmlFilter(String source){
		
		if(source==null||source.length()==0||source.trim().equals("")){
			return "";
		}
		source=source.replaceAll("<[a-zA-Z].*?>|<[/][a-zA-Z].*?>", "");
	    return source;
	}
	
	/**
	 * 根据包名获取包下所有类的名称和类路径
	 * @param packageName 包名
	 * @return
	 */
	public static Map<String, String> getClasses(String packageName) {
		Map<String, String> res = new HashMap<String, String>();
		String name = new String(packageName);
		if (!name.startsWith("/")) {
			name = "/" + name;
		}
		name = name.replace('.', '/');
		URL url = CommonUtil.class.getResource(name);
		if (url == null) {
			return res;
		}
		File directory = new File(url.getFile());
		if (directory.exists()) {
			String[] files = directory.list();
			for (int i = 0; i < files.length; i++) {
				if (files[i].endsWith(".class")) {
					String className = files[i].substring(0, files[i].length() - 6);
					try {
						Class<?> clazz = Class.forName(packageName + "." + className);
						Entity e = clazz.getAnnotation(Entity.class);
						if(e != null)
							res.put(e.name(), clazz.getName());
					} catch (ClassNotFoundException cnfex) {
						cnfex.printStackTrace();
					}
				}
			}
		}
		return res;
	}
	/**
	 * 获取系统配置信息
	 * @param propName
	 * @return
	 */
	public static String getConfig(String propName){
		if(propName.equals("fileUpload.dir")){
			if(System.getProperty( "os.name").toLowerCase().contains("windows")){
				propName="fileUpload.dir.windows";
			}else{
				propName="fileUpload.dir.linux";
			}
		}
		return PropertiesUtil.readValue("setting.properties", propName);
	}
	/**
	 * 根据公钥解密加密字节sign验证是否与plainText相同
	 * @param pubKeyText 公钥
	 * @param plainText 要验证的
	 * @param signText 加密后的内容
	 * @return
	 */
	public static boolean verify(byte[] pubKeyText, String plainText, byte[] signText) {
		try {
			// 解密由base64编码的公钥,并构造X509EncodedKeySpec对象
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64.decode(pubKeyText));
			// RSA对称加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			// 取公钥匙对象 
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			// 解密由base64编码的数字签名
			byte[] signed = Base64.decode(signText);
			Signature signatureChecker = Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			// 验证签名是否正常
			if (signatureChecker.verify(signed))
				return true;
			else
				return false;
		} catch (Throwable e) {
			System.out.println("校验签名失败");
			e.printStackTrace();
			return false;
		}
	}
	
	public static String getMac(){
		String sMAC = null;
		try{
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			ni.getInetAddresses().nextElement().getAddress();
			byte[] mac = ni.getHardwareAddress();
			Formatter formatter = new Formatter();
			for (int i = 0; i < mac.length; i++) {
				sMAC = formatter.format(Locale.getDefault(), "%02X%s", mac[i], (i < mac.length - 1) ? "-" : "").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sMAC;
	}
	
	/**
	 * 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等.
	 */
	public static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}

	/**
	 * 获取Unix网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("hwaddr");
				// 找到了
				if (index != -1) {
					// 取出mac地址并去除2边空格
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取Linux网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getLinuxMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ifconfig eth1");
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			String key="hwaddr";
			while ((line = bufferedReader.readLine()) != null) {
				
				index = line.toLowerCase().indexOf(key);
				if (index != -1) {
					// 取出mac地址并去除2边空格
					mac = line.substring(index+key.length()).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	/**
	 * 获取当前系统的网卡物理地址,支持Windows，Linux，Unix
	 * 
	 * @return
	 */
	public static String getPhysicalAddress() {
		String os = getOSName();
		if (os.startsWith("windows")) {
			return getWindowsMACAddress();
		} else if (os.startsWith("linux")) {
			return getLinuxMACAddress();
		} else {
			return getUnixMACAddress();
		}
	}

	/**
	 * 获取widnows网卡的mac地址.
	 * 
	 * @return mac地址
	 */
	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// windows下的命令，显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ipconfig /all");
			bufferedReader = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[physical address]
				index = line.toLowerCase().indexOf("physical address");
				if (index != -1) {
					index = line.indexOf(":");
					if (index != -1) {
						// 取出mac地址并去除2边空格
						mac = line.substring(index + 1).trim();
					}
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}
	
}
