package com.lenovots.crm.admin.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.DeskModular;
import com.lenovots.crm.admin.entity.Permission;
import com.lenovots.crm.admin.entity.Role;
import com.lenovots.crm.admin.service.IPermissionService;
import com.lenovots.crm.admin.service.IRoleService;
import com.lenovots.crm.common.action.BaseAction;



@Controller("roleAction")
@Scope(value="prototype")
public class RoleAction extends BaseAction<Role> {
	@Resource
	private IRoleService roleService;
	@Resource
	private IPermissionService permissionService;
	
	private static final long serialVersionUID = 8856961694147206209L;
	
	private Integer[] modularId;
	private Integer[] permissionIds;
	
	private List<Permission> topPermissionList;
	
	public void prepareSetModular(){
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepareSetPrivilege(){
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String setPrivilegeUI() {
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.topPermissionList = permissionService.findByHql("FROM Permission p WHERE p.parent is null",null);
		return SUCCESS;
	}
	
	public String setPrivilege() {
		Role role = entity;
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<Permission> temp = new HashSet<Permission>();
		Iterator<Permission> iter = role.getPermissions().iterator();
		while (iter.hasNext()) {
			temp.add(iter.next());
		}
		role.getPermissions().removeAll(temp);
		if (permissionIds != null) {
			for (int i = 0; i < permissionIds.length; i++) {
				role.addPermission(permissionService.findById(permissionIds[i]));
			}
		}
		roleService.update(role);
		return RELOAD;
	}
	
	public String setModularUI() {
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("modularList", baseService.findByHql(DeskModular.class,"FROM DeskModular p ",null));
		return SUCCESS;
	}
	public String setModular() {
		Role role = entity;
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Set<DeskModular> temp = new HashSet<DeskModular>();
		Iterator<DeskModular> iter = role.getModulars().iterator();
		while (iter.hasNext()) {
			temp.add(iter.next());
		}     
		role.getModulars().removeAll(temp);
		if (modularId != null) {
			for (int i = 0; i < modularId.length; i++) {
				role.addModular((DeskModular)baseService.findById(modularId[i],DeskModular.class));
			}
		}  
		roleService.update(role);
		return RELOAD;
	} 

	public Integer[] getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}

	public List<Permission> getTopPermissionList() {
		return topPermissionList;
	}

	public void setTopPermissionList(List<Permission> topPermissionList) {
		this.topPermissionList = topPermissionList;
	}

	public Integer[] getModularId() {
		return modularId;
	}

	public void setModularId(Integer[] modularId) {
		this.modularId = modularId;
	}
	
}
