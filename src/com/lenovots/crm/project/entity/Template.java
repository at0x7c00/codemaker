package com.lenovots.crm.project.entity;
/**
 * 模板
 * @author 胡桥
 * 2013-07-31 11:50:05
 **/
public class Template{
	
	public final static int TEMPLATE_TYPE_JSP = 1;
	public final static int TEMPLATE_TYPE_JAVA = 2;
	
	private Integer id;
	private String name;//名称
	private String content;//内容
	private TemplateGroup group;
	private Integer type;//1:jsp 2:java
	
	private boolean needRefresh;
	
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
	public void setContent(String content){
		this.content=content;
	}
	public String getContent(){
		 return this.content;
	}
	public TemplateGroup getGroup() {
		return group;
	}
	public void setGroup(TemplateGroup group) {
		this.group = group;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public boolean isNeedRefresh() {
		return needRefresh;
	}
	public void setNeedRefresh(boolean needRefresh) {
		this.needRefresh = needRefresh;
	}
}
