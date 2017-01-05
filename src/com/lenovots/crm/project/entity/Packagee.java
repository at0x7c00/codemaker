package com.lenovots.crm.project.entity;

import java.util.Set;

/**
 * 包
 * @author 胡桥
 * Jul 9, 2012  1:30:15 PM
 */
public class Packagee {
	
	private Integer id;
	private Project project;
	private String name;
	private Packagee parent;
	private Set<Packagee>children;
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Packagee getParent() {
		return parent;
	}
	public void setParent(Packagee parent) {
		this.parent = parent;
	}
	public Set<Packagee> getChildren() {
		return children;
	}
	public void setChildren(Set<Packagee> children) {
		this.children = children;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFullPackageName(){
		StringBuffer res = new StringBuffer();
		Packagee parent = this;
		while(parent!=null){
			res.insert(0, parent.getName()+".");
			parent = parent.getParent();
		}
		String str = res.toString();
		str = str.replaceAll(" ", "");
		str = str.replaceAll("┗", "");
		str = str.replaceAll("━", "");
		str = str.replaceAll("━", "");
		str = str.replaceAll("┣", "");
		str = str.replaceAll("━", "");
		str = str.replaceAll("┃", "");
		str = str.replaceAll("&nbsp;", "");
		return str;
	}
}
