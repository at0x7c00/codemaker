package com.lenovots.crm.admin.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 处理部门
 * 
 * @author 邹霖 2010-05-06
 * 
 */
@Entity(name="部门")
@Table(name="t_dept")
public class Dept {
	
	public static final String CITY_STATION = "市局";
	public static final String SUBSTATION = "分局";
	public static final String ABTEILUNG = "科室";
	public static final String OTHERS = "其他";
	
	private Integer id;// 单位ID
	private String name;// 单位名称
	private String phoneNumber1;
	private String phoneNumber2;
	private String address;
	
	private Dept mainDept;// 父级单位
	private Set<Dept> subDeptList;// 子级单位
	private Set<Operator> staffs;// 单位职员
	private Operator leader;// 部门领导
	private String description;// 单位描述信息
	private String category;//类别，分为市局、分局、科室和其他三种

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public String getFullName(){
		if(getMainDept() != null){
			return getMainDept().getFullName() + "—" + getName();
		}else{
			return getName();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dept getMainDept() {
		return mainDept;
	}

	public void setMainDept(Dept mainDept) {
		this.mainDept = mainDept;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Dept> getSubDeptList() {
		return subDeptList;
	}

	public void setSubDeptList(Set<Dept> subDeptList) {
		this.subDeptList = subDeptList;
	}

	public Operator getLeader() {
		return leader;
	}

	public void setLeader(Operator leader) {
		this.leader = leader;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Operator> getStaffs() {
		return staffs;
	}

	public void setStaffs(Set<Operator> staffs) {
		this.staffs = staffs;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
