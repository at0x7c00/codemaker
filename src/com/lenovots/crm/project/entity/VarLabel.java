package com.lenovots.crm.project.entity;
/**
 * 标签
 * @author 胡桥
 * 2013-05-10 18:37:50
 **/
public class VarLabel{
	
	public static final Integer CATEGORY_INNER = 0;//内置
	public static final Integer CATEGORY_CUSTOM = 1;//自定义
	
	private Integer id;
	private String name;//标签名称
	private String remark;//说明
	private Integer category;//类型
	private String value;//常量值
	private Project project;//所属项目
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
	public void setRemark(String remark){
		this.remark=remark;
	}
	public String getRemark(){
		 return this.remark;
	}
	public void setCategory(Integer category){
		this.category=category;
	}
	public Integer getCategory(){
		 return this.category;
	}
	public void setValue(String value){
		this.value=value;
	}
	public String getValue(){
		 return this.value;
	}
	public void setProject(Project project){
		this.project=project;
	}
	public Project getProject(){
		 return this.project;
	}
}
