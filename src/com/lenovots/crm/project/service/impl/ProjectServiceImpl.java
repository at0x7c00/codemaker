package com.lenovots.crm.project.service.impl;

import org.springframework.stereotype.Service;

import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.service.IProjectService;


@Service("projectService")
public class ProjectServiceImpl extends CrmBaseServiceImpl<Project> implements IProjectService{
}
