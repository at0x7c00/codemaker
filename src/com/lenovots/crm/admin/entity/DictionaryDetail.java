package com.lenovots.crm.admin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * 数据字典
 * @author 胡桥
 * Jun 8, 2011  1:58:21 PM
 */
@Entity(name="数据字典")
@Table(name="t_dictionaryDetail")
public class DictionaryDetail {

	private Integer id;
	
	private Dictionary parent;//所属字典
	
    private String value;
    
    private String name;
    
    private Integer sort;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Dictionary getParent() {
		return parent;
	}
	public void setParent(Dictionary parent) {
		this.parent = parent;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
