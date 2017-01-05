package com.lenovots.crm.admin.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.admin.entity.Role;
import com.lenovots.crm.admin.service.IOperatorService;
import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;




@Service("operatorService")
public class OperatorServiceImpl extends CrmBaseServiceImpl<Operator> implements IOperatorService {

	@Override
	public List<Operator> getOperatorList(Set<Role> roles) {
		if(roles==null || roles.size()==0){
			return Collections.EMPTY_LIST;
		}
		StringBuffer hql = new StringBuffer("FROM Operator p WHERE p.role.id IN(");
		List<Object> paramValues = new ArrayList<Object>();
		for(Role role: roles){
			hql.append("?,");
			paramValues.add(role.getId());
		}
		hql.delete(hql.length()-1, hql.length());
		hql.append(")");
		return findByHql(hql.toString(), paramValues.toArray());
	}
	
}
