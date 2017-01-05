package com.lenovots.crm.admin.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.SystemConfig;
import com.lenovots.crm.common.action.BaseAction;



@Controller("systemConfigAction")
@Scope(value="prototype")
public class SystemConfigAction extends BaseAction<SystemConfig> {
	private static final long serialVersionUID = 8856961694147206209L;
	
	private Integer useCheck;

	@Override
	public String update() throws Exception {
		SystemConfig systemConfig = entity;
		systemConfig.setUseCheckCode(useCheck==null?0:1);
		ServletActionContext.getRequest().getSession().setAttribute("systemConfig", systemConfig);
		baseService.update(systemConfig);
		return RELOAD;
	}

	public Integer getUseCheck() {
		return useCheck;
	}

	public void setUseCheck(Integer useCheck) {
		this.useCheck = useCheck;
	}
}
