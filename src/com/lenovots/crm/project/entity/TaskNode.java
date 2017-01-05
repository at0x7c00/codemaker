package com.lenovots.crm.project.entity;
import java.util.HashSet;
import java.util.Set;
/**
 * 任务节点
 * @author 胡桥
 * 2013-09-05 15:21:19
 **/
public class TaskNode{
	private Integer id;
	private String name;//任务名称
	private String listUrl;//列表URL
	private String processUrl;//处理URL
	private String submitUrl;//处理提交URL
	private Entity contextEntity;//所属实体
	private String filterCause;//过滤条件
	private Set<PageProperty> properties = new HashSet<PageProperty>();//涉及字段
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
	public void setListUrl(String listUrl){
		this.listUrl=listUrl;
	}
	public String getListUrl(){
		 return this.listUrl;
	}
	public void setProcessUrl(String processUrl){
		this.processUrl=processUrl;
	}
	public String getProcessUrl(){
		 return this.processUrl;
	}
	public void setSubmitUrl(String submitUrl){
		this.submitUrl=submitUrl;
	}
	public String getSubmitUrl(){
		 return this.submitUrl;
	}
	public void setContextEntity(Entity contextEntity){
		this.contextEntity=contextEntity;
	}
	public Entity getContextEntity(){
		 return this.contextEntity;
	}
	public void setFilterCause(String filterCause){
		this.filterCause=filterCause;
	}
	public String getFilterCause(){
		 return this.filterCause;
	}
	public void setProperties(Set<PageProperty> properties){
		this.properties=properties;
	}
	public Set<PageProperty> getProperties(){
		 return this.properties;
	}

}
