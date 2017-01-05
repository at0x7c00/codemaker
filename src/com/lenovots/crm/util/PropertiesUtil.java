package com.lenovots.crm.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropertiesUtil {

	// 根据key读取value
	public static String readValue(String fileName, String key) {
		Properties props = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
					.getResourceAsStream(fileName);
			props.load(in);
			return props.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 读取properties的全部信息
	public String readProperties(String fileName) {
		Properties props = new Properties();
		StringBuffer res = new StringBuffer();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
			.getResourceAsStream(fileName);
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				res.append(key +"="+ Property).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	// 写入properties信息
	public static void writeProperties(String fileName,String propFilePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream in = PropertiesUtil.class.getClassLoader()
			.getResourceAsStream(fileName);
			
			// 从输入流中读取属性列表（键和元素对）
			prop.load(in);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(propFilePath+fileName);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
			
		} catch (IOException e) {
			System.err.println("Visit " + fileName + " for updating "
					+ parameterName + " value error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		writeProperties("setting.properties","C:\\apache-tomcat-6.0.24\\webapps\\ROOT\\WEB-INF\\classes\\setting.properties", "license", "xxxxx");
	}

}