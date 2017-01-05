package com.lenovots.crm.project.entity;
/**
 * 页面字段
 * @author 胡桥
 * 2013-09-05 15:21:19
 **/
public class PageProperty{
	private Integer id;
	private TaskNode taskNode;//所属任务节点
	private Property property;//字段
	private Integer editable;//是否可写
	private Integer autoValue;//自动赋值
	private Integer autoValueType;//自动赋值方式
	private String valueOrExp;//给定值或表达式
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setTaskNode(TaskNode taskNode){
		this.taskNode=taskNode;
	}
	public TaskNode getTaskNode(){
		 return this.taskNode;
	}
	public void setProperty(Property property){
		this.property=property;
	}
	public Property getProperty(){
		 return this.property;
	}
	public void setEditable(Integer editable){
		this.editable=editable;
	}
	public Integer getEditable(){
		 return this.editable;
	}
	public void setAutoValue(Integer autoValue){
		this.autoValue=autoValue;
	}
	public Integer getAutoValue(){
		 return this.autoValue;
	}
	public void setAutoValueType(Integer autoValueType){
		this.autoValueType=autoValueType;
	}
	public Integer getAutoValueType(){
		 return this.autoValueType;
	}
	public void setValueOrExp(String valueOrExp){
		this.valueOrExp=valueOrExp;
	}
	public String getValueOrExp(){
		 return this.valueOrExp;
	}

}
