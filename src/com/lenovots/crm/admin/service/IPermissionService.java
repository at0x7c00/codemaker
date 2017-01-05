package com.lenovots.crm.admin.service;

import java.util.Collection;
import java.util.List;

import com.lenovots.crm.admin.entity.Permission;
import com.lenovots.crm.common.service.IBaseService;



public interface IPermissionService extends IBaseService<Permission>{
	
	public void tree(Collection<Permission> topDepartments, String prefixCode,List<Permission> result,Integer filterId);
	
	/**
	 * 位置上移
	 * @param id
	 */
	public void up(Integer id);
	   
	/**
	 * 位置下移
	 * @param id
	 */
	public void down(Integer id);
	
	/**
	 * 获取指定父级权限下最大的排序值
	 * @param parentId
	 * @return
	 */
	public Integer getMaxSortValue(Integer parentId);
	
}
