package com.lenovots.crm.project.entity;

import java.util.Date;

/**
 * 版本记录
 * @author 胡桥
 * 2012-12-18 09:29:56
 **/
public class VersionRecord{
	private Integer id;
	private String name;//版本号
	private Date addTime;//日期
	private String remark;//版本备注
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
	public void setAddTime(Date addTime){
		this.addTime=addTime;
	}
	public Date getAddTime(){
		 return this.addTime;
	}
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		 return this.remark;
	}

}
