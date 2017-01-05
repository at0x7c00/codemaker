package com.lenovots.crm.admin.entity;

import java.sql.Timestamp;

/**
 * 公告实体类
 */
public class Notice {

	private Integer id;
	private String title;// 公告标题
	private String content;// 公告内容
	private Operator publisher;// 发布者
	private Timestamp startTime;// 开始时间
	private Timestamp endTime;// 结束时间
	private String viewRange;//发布范围Id
	private String viewDeptNames;

	public String getViewRange() {
		return viewRange;
	}

	public void setViewRange(String viewRange) {
		this.viewRange = viewRange;
	}

	public String getViewDeptNames() {
		return viewDeptNames;
	}

	public void setViewDeptNames(String viewDeptNames) {
		this.viewDeptNames = viewDeptNames;
	}

	public Notice() {
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Operator getPublisher() {
		return publisher;
	}

	public void setPublisher(Operator publisher) {
		this.publisher = publisher;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

}
