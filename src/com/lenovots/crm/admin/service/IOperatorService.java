package com.lenovots.crm.admin.service;

import java.util.List;
import java.util.Set;

import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.admin.entity.Role;
import com.lenovots.crm.common.service.IBaseService;




public interface IOperatorService extends IBaseService<Operator>{
	
	/**
	 * 获取指定角色的系统用户
	 * @param roles
	 * @return
	 */
	public List<Operator> getOperatorList(Set<Role> roles);
	
}
