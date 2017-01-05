package com.lenovots.crm.project.entity;
/**
 * 环境变量
 * @author 胡桥
 * 2013-09-06 13:48:45
 **/
public class EnvironmentVariable{
	private Integer id;
	private String varName;//变量名称
	private String value;//变量值
	private Integer dataType;//类型
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setVarName(String varName){
		this.varName=varName;
	}
	public String getVarName(){
		 return this.varName;
	}
	public void setValue(String value){
		this.value=value;
	}
	public String getValue(){
		 return this.value;
	}
	public void setDataType(Integer dataType){
		this.dataType=dataType;
	}
	public Integer getDataType(){
		 return this.dataType;
	}
}
