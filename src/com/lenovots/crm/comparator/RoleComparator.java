package com.lenovots.crm.comparator;

import java.util.Comparator;

import com.lenovots.crm.admin.entity.Role;


public class RoleComparator implements Comparator<Role> {

	@Override
	public int compare(Role r1, Role r2) {
		return r1.getId()-r2.getId();
	}
}
