package com.lenovots.crm.admin.service;

import com.lenovots.crm.admin.entity.DeskModular;
import com.lenovots.crm.common.service.IBaseService;



public interface IDeskModularService extends IBaseService<DeskModular>{
	
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
	
	public Integer getMaxSortValue();
	
}
