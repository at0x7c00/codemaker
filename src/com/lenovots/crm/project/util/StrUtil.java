package com.lenovots.crm.project.util;

public class StrUtil {

	/**
	 * 大写首字母
	 * @param str
	 * @return
	 */
	public static String upperCaseFirstChar(String str){
		if(str.length()<=1){
			return str.toUpperCase();
		}
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
	
	
	/**
	 * 小写首字母
	 * @param str
	 * @return
	 */
	public static String lowerCaseFistChar(String str){
		if(str.length()<=1){
			return str.toLowerCase();
		}
		return str.substring(0, 1).toLowerCase()+str.substring(1);
	}
}
