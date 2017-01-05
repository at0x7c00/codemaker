package com.lenovots.crm.admin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Dept;
import com.lenovots.crm.admin.service.IDepartmentService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.util.CommonUtil;



@Controller("departmentAction")
@Scope(value="prototype")
public class DepartmentAction extends BaseAction<Dept> {
	private static final long serialVersionUID = 8856961694147206209L;
	

	private Dept dept;
	
	private Integer mainDeptId;
	
	@Resource
	private IDepartmentService deptService;
	
	private List<Dept> deptList;
	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			dept = deptService.findById(id);
		}else{
			dept = new Dept();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		List<Dept> tempList = deptService.findByHql("from Dept d where d.mainDept is null", null);
		deptList = new ArrayList<Dept>();   
		CommonUtil.tree(tempList, "", deptList, null);
		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		List<Dept> tempList = deptService.findByHql("from Dept d where d.mainDept is null", null);
		deptList = new ArrayList<Dept>();
		CommonUtil.tree(tempList, "", deptList, null);
		return SUCCESS;
	}

	@Override
	public Dept getModel() {
		return dept;
	}

	@Override
	public String add() throws Exception {
		if(mainDeptId!=null){
			dept.setMainDept(deptService.findById(mainDeptId));
		}
		deptService.add(dept);
		return RELOAD;
	}

	@Override
	public String update() throws Exception {
		if(mainDeptId!=null){
			dept.setMainDept(deptService.findById(mainDeptId));
		}
		deptService.update(dept);
		return RELOAD;
	}

	@Override
	public String list() throws Exception {
		List<Dept> tempList = deptService.findByHql("FROM Dept d WHERE d.mainDept is NULL", null);
		deptList = new ArrayList<Dept>();
		CommonUtil.tree(tempList, "", deptList, null);
		return SUCCESS;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Integer getMainDeptId() {
		return mainDeptId;
	}

	public void setMainDeptId(Integer mainDeptId) {
		this.mainDeptId = mainDeptId;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	
}
