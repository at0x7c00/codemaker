package com.lenovots.crm.project.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lenovots.crm.common.service.IBaseService;
import com.lenovots.crm.project.entity.Property;


public interface IPropertyService extends  IBaseService<Property>{
	
	public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine);
}
