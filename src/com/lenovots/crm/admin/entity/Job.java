package com.lenovots.crm.admin.entity;

/**
 * 职位
 * @author 胡桥 2011-04-19
 */
public class Job {

	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Integer status = 1;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
