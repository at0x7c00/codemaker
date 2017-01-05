package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.PageProperty;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.entity.Property;
import com.lenovots.crm.project.entity.TaskNode;
import com.lenovots.crm.project.service.IEntityService;
import com.lenovots.crm.project.service.IPagePropertyService;
import com.lenovots.crm.project.service.IPropertyService;
import com.lenovots.crm.project.service.ITaskNodeService;
/**
 * @author 胡桥
 * 2013-09-05 15:21:19
 */
@Controller("taskNodeAction") 
@Scope(value="prototype")
public class TaskNodeAction extends BaseAction<TaskNode> {
	
	private static Logger logger = Logger.getLogger(TaskNodeAction.class);
	
	private TaskNode taskNode;
	
	@Resource
	private ITaskNodeService taskNodeService;
	
	private List<TaskNode> taskNodeList;
	
	//复杂关联关系的list
	private List<Entity> contextEntityList;

	//复杂关联关系的Id
	private Integer contextEntityId;
	
	private Integer[] propIds;

	//复杂关联关系的Service
	@Resource private IEntityService entityService;
	@Resource private IPagePropertyService pagePropertyService;
	@Resource private IPropertyService propertyService;
	//日期类型定义
	
	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			taskNode = taskNodeService.findById(id);
		}else{
			taskNode = new TaskNode();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		//复杂关联关系数据准备
		Project project = (Project) ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
		Integer projectId = null;
		StringBuffer hql = new StringBuffer("FROM Entity e WHERE 1=1");
		List<Object> params = new ArrayList<Object>();
		if(project!=null && projectId==null){
			projectId = project.getId();
		}
		if(projectId!=null){
			hql.append(" AND e.packagee.project.id=? ");
			params.add(projectId);
		}
		contextEntityList = entityService.findByHql(hql.toString(), params.toArray());
		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		//复杂关联关系数据准备
		contextEntityList = entityService.findAll();
		return SUCCESS;
	}

	@Override
	public TaskNode getModel() {
		return taskNode;
	}

	@Override
	public String add() throws Exception {
		//复杂关联关系保存
		if (contextEntityId == null) {
			taskNode.setContextEntity(null);
		} else {
			taskNode.setContextEntity(entityService.findById(contextEntityId));
		}
		if(propIds!=null){
			Property prop = null;
			Integer editable = null;
			Integer autoValue = null;
			Integer autoValueType = null;
			String valueOrExp = null;
			PageProperty pageProp = null;
			for(Integer propId : propIds){
				pageProp = new PageProperty();
				editable = null;
				autoValue = null;
				autoValueType = null;
				valueOrExp = null;
				prop = propertyService.findById(propId);
				editable = param2Integer("editable_"+propId);
				if(editable==1){
					autoValue =  param2Integer("autoValue_"+propId);
					if(autoValue==1){
						autoValueType = param2Integer("autoValueType_"+propId);
						if(autoValueType!=0){
							valueOrExp = (String)getParameter("valueOrExp_"+propId);
						}
					}
				}
				pageProp.setEditable(editable);
				pageProp.setAutoValue(autoValue);
				pageProp.setAutoValueType(autoValueType);
				pageProp.setValueOrExp(valueOrExp);
				pageProp.setProperty(prop);
				pageProp.setTaskNode(taskNode);
				taskNode.getProperties().add(pageProp);
			}
		}
		taskNodeService.add(taskNode);
		
		return RELOAD;
	}
	
	private Integer param2Integer(String paramName){
		try {
			return Integer.parseInt((String) getParameter(paramName));
		} catch (Exception e) {
		}
		return 0;
	}

	@Override
	public String update() throws Exception {
		if (contextEntityId == null) {
			taskNode.setContextEntity(null);
		} else {
			taskNode.setContextEntity(entityService.findById(contextEntityId));
		}
		List<PageProperty> tempList = new ArrayList<PageProperty>();
		for(PageProperty p : taskNode.getProperties()){
			p.setTaskNode(null);
			tempList.add(p);
		}
		taskNode.getProperties().clear();
		for(PageProperty p : tempList){
			pagePropertyService.deleteById(p.getId());
		}
		if(propIds!=null){
			Property prop = null;
			Integer editable = null;
			Integer autoValue = null;
			Integer autoValueType = null;
			String valueOrExp = null;
			PageProperty pageProp = null;
			for(Integer propId : propIds){
				pageProp = new PageProperty();
				editable = null;
				autoValue = null;
				autoValueType = null;
				valueOrExp = null;
				prop = propertyService.findById(propId);
				editable = param2Integer("editable_"+propId);
				if(editable==1){
					autoValue =  param2Integer("autoValue_"+propId);
					if(autoValue==1){
						autoValueType = param2Integer("autoValueType_"+propId);
						if(autoValueType!=0){
							valueOrExp = (String)getParameter("valueOrExp_"+propId);
						}
					}
				}
				pageProp.setEditable(editable);
				pageProp.setAutoValue(autoValue);
				pageProp.setAutoValueType(autoValueType);
				pageProp.setValueOrExp(valueOrExp);
				pageProp.setProperty(prop);
				pageProp.setTaskNode(taskNode);
				taskNode.getProperties().add(pageProp);
			}
		}
		taskNodeService.update(taskNode);
		return RELOAD;
	}

	@Override
	public String list() throws Exception {
		if(pageNum==null || pageNum<0){
			pageNum=0;
		}
		if(pageSize==null || pageSize<=0){
			pageSize=15;
		}
		//复杂关联关系数据准备
		contextEntityList = entityService.findAll();

		createPageBean(false);
		
		return SUCCESS;
	}
	
	private void createPageBean(boolean all){
		StringBuffer hql = new StringBuffer("FROM TaskNode taskNode WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if(taskNode.getName()!=null && ! taskNode.getName().trim().equals("")){hql.append(" AND taskNode.name like ? ");
params.add("%"+taskNode.getName()+"%");}if(taskNode.getListUrl()!=null && ! taskNode.getListUrl().trim().equals("")){hql.append(" AND taskNode.listUrl like ? ");
params.add("%"+taskNode.getListUrl()+"%");}if(taskNode.getProcessUrl()!=null && ! taskNode.getProcessUrl().trim().equals("")){hql.append(" AND taskNode.processUrl like ? ");
params.add("%"+taskNode.getProcessUrl()+"%");}if(taskNode.getSubmitUrl()!=null && ! taskNode.getSubmitUrl().trim().equals("")){hql.append(" AND taskNode.submitUrl like ? ");
params.add("%"+taskNode.getSubmitUrl()+"%");}if(contextEntityId!=null && contextEntityId!=-1){	hql.append(" AND taskNode.contextEntity.id=? ");	params.add(contextEntityId);}if(taskNode.getFilterCause()!=null && ! taskNode.getFilterCause().trim().equals("")){hql.append(" AND taskNode.filterCause like ? ");
params.add("%"+taskNode.getFilterCause()+"%");}
		
		if(all){
			list = taskNodeService.findByHql(hql.toString(), params.toArray());
		}else{//分页
			pageBean = taskNodeService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, params.toArray());
		}
	}
	
	public TaskNode getTaskNode() {
		return taskNode;
	}

	public void setTaskNode(TaskNode taskNode) {
		this.taskNode = taskNode;
	}

	public List<TaskNode> getTaskNodeList() {
		return taskNodeList;
	}

	public void setTaskNodeList(List<TaskNode> taskNodeList) {
		this.taskNodeList = taskNodeList;
	}
	
	//导出
	
	
	
	//复杂关联关系List的getter和setter
	public List<Entity> getContextEntityList(){return this.contextEntityList;}
public void setContextEntityList(List<Entity> contextEntityList){this.contextEntityList=contextEntityList;}

	//复杂关联关系Id的getter和setter
	public Integer getContextEntityId(){return this.contextEntityId;}
public void setContextEntityId(Integer contextEntityId){this.contextEntityId=contextEntityId;}

public Integer[] getPropIds() {
	return propIds;
}

public void setPropIds(Integer[] propIds) {
	this.propIds = propIds;
}

}
