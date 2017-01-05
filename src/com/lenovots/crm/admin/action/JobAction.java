package com.lenovots.crm.admin.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Job;
import com.lenovots.crm.common.action.BaseAction;



@Controller("jobAction")
@Scope(value="prototype")
public class JobAction extends BaseAction<Job> {
	private static final long serialVersionUID = 8856961694147206209L;
	
}
