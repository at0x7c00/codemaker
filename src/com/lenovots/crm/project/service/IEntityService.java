package com.lenovots.crm.project.service;

import javax.servlet.http.HttpServletResponse;

import com.lenovots.crm.common.service.IBaseService;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Project;


public interface IEntityService extends  IBaseService<Entity>{
	
	public void makeMVC(Project project,Entity entity,int sqlId) throws Exception;
	
	public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine);

}
