package com.lenovots.crm.util;

import java.lang.reflect.Field;

/**
 * 对象属性操作工具
 * @author 胡桥
 * Mar 30, 2012  9:00:09 AM
 */
public class PropertityUtil {
	
	
	/**
	 * 通过反射获取对象上的一个属性值
	 * @param clazz
	 * @param target
	 * @param property
	 * @return
	 */
	public static Object getProp(Object target,String property){
		if(target==null){
			return null;
		}
		Class<?> clazz=target.getClass();
		try {
			Field field=clazz.getDeclaredField(property);
			field.setAccessible(true);
			return field.get(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 通过反射为对象上的一个属性赋值
	 * @param clazz
	 * @param target
	 * @param property
	 * @param feildValue 
	 * @return
	 */
	public static void setProp(Object target,String property,Object feildValue){
		Class<?> clazz=target.getClass();
		try {
			Field field=clazz.getDeclaredField(property);
			field.setAccessible(true);
			field.set(target,feildValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
