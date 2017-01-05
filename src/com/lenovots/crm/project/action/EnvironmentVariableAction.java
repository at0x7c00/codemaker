package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.EnvironmentVariable;
import com.lenovots.crm.project.service.IEnvironmentVariableService;

/**
 * @author 胡桥 2013-09-06 13:48:45
 */
@Controller("environmentVariableAction")
@Scope(value = "prototype")
public class EnvironmentVariableAction extends BaseAction<EnvironmentVariable> {

	private static Logger logger = Logger
			.getLogger(EnvironmentVariableAction.class);

	private EnvironmentVariable environmentVariable;
	@Resource
	private IEnvironmentVariableService environmentVariableService;
	private List<EnvironmentVariable> environmentVariableList;
	private List<EnvironmentVariable> projectList;


	@Override
	protected void prepareModel() throws Exception {
		if (id != null) {
			environmentVariable = environmentVariableService.findById(id);
		} else {
			environmentVariable = new EnvironmentVariable();
		}
	}

	@Override
	public String addUI() throws Exception {
		projectList = environmentVariableService.findAll();
		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		projectList = environmentVariableService.findAll();
		return SUCCESS;
	}

	@Override
	public EnvironmentVariable getModel() {
		return environmentVariable;
	}

	@Override
	public String add() throws Exception {
		environmentVariableService.add(environmentVariable);
		return RELOAD;
	}

	@Override
	public String update() throws Exception {
		environmentVariableService.update(environmentVariable);
		return RELOAD;
	}

	@Override
	public String list() throws Exception {
		if (pageNum == null || pageNum < 0) {
			pageNum = 0;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 15;
		}
		// 复杂关联关系数据准备
		projectList = environmentVariableService.findAll();

		createPageBean(false);

		return SUCCESS;
	}

	private void createPageBean(boolean all) {
		StringBuffer hql = new StringBuffer(
				"FROM EnvironmentVariable environmentVariable WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();

		if (environmentVariable.getVarName() != null
				&& !environmentVariable.getVarName().trim().equals("")) {
			hql.append(" AND environmentVariable.varName like ? ");
			params.add("%" + environmentVariable.getVarName() + "%");
		}
		if (environmentVariable.getValue() != null
				&& !environmentVariable.getValue().trim().equals("")) {
			hql.append(" AND environmentVariable.value like ? ");
			params.add("%" + environmentVariable.getValue() + "%");
		}
		if (environmentVariable.getDataType() != null) {
			hql.append(" AND environmentVariable.dataType=? ");
			params.add(environmentVariable.getDataType());
		}
		if (all) {
			list = environmentVariableService.findByHql(hql.toString(),
					params.toArray());
		} else {// 分页
			pageBean = environmentVariableService.findPageBeanOfHql(
					hql.toString(), pageNum, pageSize, params.toArray());
		}
	}

	public EnvironmentVariable getEnvironmentVariable() {
		return environmentVariable;
	}

	public void setEnvironmentVariable(EnvironmentVariable environmentVariable) {
		this.environmentVariable = environmentVariable;
	}

	public List<EnvironmentVariable> getEnvironmentVariableList() {
		return environmentVariableList;
	}

	public void setEnvironmentVariableList(
			List<EnvironmentVariable> environmentVariableList) {
		this.environmentVariableList = environmentVariableList;
	}

	// 复杂关联关系List的getter和setter
	public List<EnvironmentVariable> getProjectList() {
		return this.projectList;
	}

	public void setProjectList(List<EnvironmentVariable> projectList) {
		this.projectList = projectList;
	}
}
