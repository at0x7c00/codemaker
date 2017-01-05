package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.TemplateGroup;
import com.lenovots.crm.project.service.ITemplateGroupService;
/**
 * @author 胡桥
 * 2013-08-16 10:49:49
 */
@Controller("templateGroupAction") 
@Scope(value="prototype")
public class TemplateGroupAction extends BaseAction<TemplateGroup> {
	
	private static Logger logger = Logger.getLogger(TemplateGroupAction.class);
	
	private TemplateGroup templateGroup;
	
	@Resource
	private ITemplateGroupService templateGroupService;
	
	private List<TemplateGroup> templateGroupList;
	
	
		

	//复杂关联关系的list
	
	//复杂关联关系的Id
	
	//复杂关联关系的Service
	
	//日期类型定义
	
	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			templateGroup = templateGroupService.findById(id);
		}else{
			templateGroup = new TemplateGroup();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		//复杂关联关系数据准备
		
		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		//复杂关联关系数据准备
		
		return SUCCESS;
	}

	@Override
	public TemplateGroup getModel() {
		return templateGroup;
	}

	@Override
	public String add() throws Exception {
		//复杂关联关系保存
		
		//默认系统时间类型保存
		
		templateGroupService.add(templateGroup);
		
		return RELOAD;
	}

	@Override
	public String update() throws Exception {
		//复杂关联关系保存
		
		templateGroupService.update(templateGroup);
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
		
		createPageBean(false);
		
		return SUCCESS;
	}
	
	private void createPageBean(boolean all){
		StringBuffer hql = new StringBuffer("FROM TemplateGroup templateGroup WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if(templateGroup.getName()!=null && ! templateGroup.getName().trim().equals("")){hql.append(" AND templateGroup.name like ? ");
params.add("%"+templateGroup.getName()+"%");}
		
		if(all){
			list = templateGroupService.findByHql(hql.toString(), params.toArray());
		}else{//分页
			pageBean = templateGroupService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, params.toArray());
		}
	}
	
	public TemplateGroup getTemplateGroup() {
		return templateGroup;
	}

	public void setTemplateGroup(TemplateGroup templateGroup) {
		this.templateGroup = templateGroup;
	}

	public List<TemplateGroup> getTemplateGroupList() {
		return templateGroupList;
	}

	public void setTemplateGroupList(List<TemplateGroup> templateGroupList) {
		this.templateGroupList = templateGroupList;
	}
	
	//导出
	
	
	
	//复杂关联关系List的getter和setter
	
	//复杂关联关系Id的getter和setter
	
	//用于查询的日期类型的开始结束的getter和setter
	
		

}
