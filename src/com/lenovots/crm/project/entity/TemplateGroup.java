package com.lenovots.crm.project.entity;
/**
 * 模板组
 * @author 胡桥
 * 2013-08-16 10:49:49
 **/
public class TemplateGroup{
	private Integer id;
	private String name;//名称
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		 return this.name;
	}

}
