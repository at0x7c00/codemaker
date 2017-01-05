package com.lenovots.crm.admin.entity;

import java.io.Serializable;

/**
 * 桌面模块
 * @author 胡桥
 * May 9, 2012  2:54:29 PM
 */
public class DeskModular implements Comparable<Permission>,Serializable {
	
	/**
	 * 系统内置
	 */
	public final static int TYPE_INNER=1;
	
	/**
	 * 用户自定义
	 */
	public final static int TYPE_CUSTOMER=2;

	private Integer id;
	
	private String title;
	
	private Integer outSite=0;//外部资源
	
	private String url;
	
	private Integer sortValue;
	
	private Integer type;
	
	private Integer height;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getOutSite() {
		return outSite;
	}

	public void setOutSite(Integer outSite) {
		this.outSite = outSite;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSortValue() {
		return sortValue;
	}

	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public int compareTo(Permission o) {
		return 0;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	
}
