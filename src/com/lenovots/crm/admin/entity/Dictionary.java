package com.lenovots.crm.admin.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * 数据字典
 * @author 胡桥
 * Jun 8, 2011  1:58:21 PM
 */
@SuppressWarnings("serial")
public class Dictionary implements Serializable{

	private Integer id;
	
	private String prefix;
	
	private String description;
	
	private String title;
	
	private Set<DictionaryDetail> details=new HashSet<DictionaryDetail>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<DictionaryDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<DictionaryDetail> details) {
		this.details = details;
	}
	
	
	
	
}
