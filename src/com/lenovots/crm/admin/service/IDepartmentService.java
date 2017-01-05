package com.lenovots.crm.admin.service;

import java.util.List;

import com.lenovots.crm.admin.entity.Dept;
import com.lenovots.crm.common.service.IBaseService;



public interface IDepartmentService extends IBaseService<Dept>{
	
	/**
	 * 获取指定部门直属的分局或市局
	 * @param dept
	 * @return
	 */
	public Dept getAncestryDept(Dept dept);
	
	
	/**
	 * 获取指定部门的所有子部门及子孙部门
	 * @param dept
	 * @return
	 */
	public void  getSubDepts(Dept dept,List<Dept> results);
	

	/**
	 *  查询所有部门信息的树状结构
	 * @return
	 */
	public List<Dept> getAllDepartmentTreeView();

	/**
	 * 查询过滤掉指定ID部门及其子孙部门的树状结构
	 */
	public List<Dept> getProductCategoryTreeViewByFilter(Integer id);
	
	
}
