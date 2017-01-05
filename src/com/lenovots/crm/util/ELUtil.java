package com.lenovots.crm.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * el表达式解析工具
 * @author j
 */
public class ELUtil {
	
	public static void main(String[] args) {
		String strWithEl = "I${entity.className}Dao${entity.className}BBC.java";
		Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z.]*\\}");
		Matcher matcher = pattern.matcher(strWithEl);
		StringBuffer sb = new StringBuffer();
		String group = null;
		boolean find = false;
		while(matcher.find()){
			group = matcher.group();
			matcher.appendReplacement(sb, "[_V_]");
			find = true;
		}
		if(!strWithEl.endsWith("}") && group!=null){
			sb.append(strWithEl.substring(strWithEl.lastIndexOf(group)+1));
		}
		if(find){
			System.out.println("input:"+strWithEl+"\r\noutput:"+sb.toString());
		}else{
			System.out.println("input:"+strWithEl+"\r\noutput:"+strWithEl);
		}
	}
	
	private Map<String,Object> context = new HashMap<String,Object>();
	
	public static ELUtil getInstance(){
		return new ELUtil();
	}
	
	/**
	 * 验证查询添加
	 * @param obj
	 * @param strWithEl 如${entity.type}=1
	 * @return
	 * @throws Exception 
	 */
	public boolean causeValidate(Object target,String elCause) throws Exception{
		if(elCause.trim().equals("")){
			return true;
		}
		String[] causes = elCause.split(";");
		boolean result = true;
		for(String cause : causes){
			String[] causeArray = cause.split("=");
			String value = readPropByEl(target, causeArray[0]);
			result = result && value.equals(causeArray[1]);
		}
		return result;
	}
	
	/**
	 * 往作用域中添加变量
	 * @param name
	 * @param value
	 */
	public ELUtil setAttribute(String name,Object value){
		context.put(name, value);
		return this;
	}
	
	public String parse(String strWithEl)throws Exception{
		Pattern pattern = Pattern.compile("\\$\\{[a-zA-Z.]*\\}");
		Matcher matcher = pattern.matcher(strWithEl);
		StringBuffer sb = new StringBuffer();
		String group = null;
		boolean find = false;
		while(matcher.find()){
			group = matcher.group();
			matcher.appendReplacement(sb, readPropByEl(context.get(getRootTargetName(group)), group));
			find = true;
		}
		if(!strWithEl.endsWith("}") && group!=null){
			sb.append(strWithEl.substring(strWithEl.lastIndexOf(group)+group.length()));
		}
		if(find){
			return sb.toString();
		}else{
			return strWithEl;
		}
	}
	
	private String getRootTargetName(String el){
		el = el.trim().substring(el.indexOf("{")+1,el.indexOf("."));
		return el;
	}
	
	/**
	 * 通过el表达式读取对象属性值
	 * @param target 任何复杂类型
	 * @param el el表达式，如${entity.lowerClassName}
	 * @return
	 * @throws Exception
	 */
	public static String readPropByEl(Object target,String el)throws Exception{
		el = el.trim();
		el = el.substring(el.indexOf("{")+1,el.length()-1);
		el = el.substring(el.indexOf(".")+1);
		return readPropByPath(target,el);
	}
	
	/**
	 * 通过el表达式读取对象属性值
	 * @param target 任何复杂类型
	 * @param path 属性路径，如entity.lowerClassName
	 * @return
	 * @throws Exception 
	 */
	public static String readPropByPath(Object target,String path)throws Exception{
		path = path.trim();
		if(path.contains(".")){
			target = readProp(target,path.substring(0,path.indexOf(".")));
			return readPropByPath(target,path.substring(path.indexOf(".")+1));
		}
		return readProp(target,path).toString();
	}
	
	/**
	 * 读取目标对象的属性值
	 * @param target
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	public static Object readProp(Object target,String prop)throws Exception{
		if(target==null){
			return "";
		}
		prop = prop.substring(0, 1).toUpperCase()+prop.substring(1);
		prop = "get"+prop;
		Class clazz = target.getClass();
		Method method = clazz.getDeclaredMethod(prop);
		return method.invoke(target);
	}
	
	/**
	 * 计算相对路劲
	 * @param path
	 * @return
	 */
	public String calcuteRelativePath(String path){
		int pos = path.indexOf("../");
		while(pos>0){
			String pre= path.substring(0,pos);
			if(pre.endsWith(".")){
				pre = pre.substring(0,pre.length()-1);
			}
			int a = pre.lastIndexOf(".");
			int b = pre.lastIndexOf("/");
			pre = pre.substring(0,(a>b? a : b)+1);
			String suf = path.substring(pos+3);
			path = pre+suf;
			pos = path.indexOf("../");
		}
		return path;
	}
	
	
}
