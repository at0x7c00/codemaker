package com.lenovots.crm.admin.entity;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 角色
 * @author 胡桥
 */
public class Role {

	private Integer id;
	private String name;
	
	private Set<Permission> permissions=new TreeSet<Permission>(new Comparator<Permission>(){

		public int compare(Permission p1, Permission p2) {
			return p1.getSortValue()-p2.getSortValue();
		}
		
	});//拥有权限
	
	private Set<DeskModular> modulars=new TreeSet<DeskModular>(new Comparator<DeskModular>(){
		
		public int compare(DeskModular p1, DeskModular p2) {
			return p1.getSortValue()-p2.getSortValue();
		}
		
	});//拥有模块
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public Role addPermission(Permission permission){
		this.permissions.add(permission);
		return this;
	}
	public Role addModular(DeskModular modular){
		this.modulars.add(modular);
		return this;
	}
	public Set<DeskModular> getModulars() {
		return modulars;
	}
	public void setModulars(Set<DeskModular> modulars) {
		this.modulars = modulars;
	}
}
