package com.lenovots.crm.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InnerLabel{
	
	/**
	 * 标签名称
	 * @return
	 */
	String name();
	
	/**
	 * 描述
	 * @return
	 */
	String desc();

}
