package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Rule;
import com.lenovots.crm.project.entity.Template;
import com.lenovots.crm.project.service.IRuleService;
import com.lenovots.crm.project.service.ITemplateService;
/**
 * @author 胡桥
 * 2013-08-19 11:02:34
 */
@Controller("ruleAction") 
@Scope(value="prototype")
public class RuleAction extends BaseAction<Rule> {
	
	private static Logger logger = Logger.getLogger(RuleAction.class);
	
	private Rule rule;
	
	@Resource
	private IRuleService ruleService;
	
	private List<Rule> ruleList;

	//复杂关联关系的list
	private List<Template> templateList;

	//复杂关联关系的Id
	private Integer templateId;

	//复杂关联关系的Service
	@Resource private ITemplateService templateService;

	//日期类型定义
	private Integer append;
	
	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			rule = ruleService.findById(id);
		}else{
			rule = new Rule();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		//复杂关联关系数据准备
		templateList = templateService.findAll();

		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		//复杂关联关系数据准备
		templateList = templateService.findAll();

		return SUCCESS;
	}

	@Override
	public Rule getModel() {
		return rule;
	}

	@Override
	public String add() throws Exception {
		//复杂关联关系保存
		if(templateId==null){	rule.setTemplate(null);}else{rule.setTemplate(templateService.findById(templateId));}
		//默认系统时间类型保存
		rule.setAppend(1==append);
		ruleService.add(rule);
		
		return RELOAD;
	}

	@Override
	public String update() throws Exception {
		//复杂关联关系保存
		if(templateId==null){	rule.setTemplate(null);}else{rule.setTemplate(templateService.findById(templateId));}
		rule.setAppend(1==append);
		ruleService.update(rule);
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
		templateList = templateService.findAll();

		createPageBean(false);
		
		return SUCCESS;
	}
	
	private void createPageBean(boolean all){
		StringBuffer hql = new StringBuffer("FROM Rule rule WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if(templateId!=null && templateId!=-1){	hql.append(" AND rule.template.id=? ");	params.add(templateId);}if(rule.getOutputDir()!=null && ! rule.getOutputDir().trim().equals("")){hql.append(" AND rule.outputDir like ? ");
params.add("%"+rule.getOutputDir()+"%");}
		
		if(all){
			list = ruleService.findByHql(hql.toString(), params.toArray());
		}else{//分页
			pageBean = ruleService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, params.toArray());
		}
	}
	
	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	public List<Rule> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<Rule> ruleList) {
		this.ruleList = ruleList;
	}
	
	
	//复杂关联关系List的getter和setter
	public List<Template> getTemplateList(){return this.templateList;}
public void setTemplateList(List<Template> templateList){this.templateList=templateList;}

	//复杂关联关系Id的getter和setter
	public Integer getTemplateId(){return this.templateId;}
public void setTemplateId(Integer templateId){this.templateId=templateId;}

public void setAppend(Integer append) {
	this.append = append;
}

	//用于查询的日期类型的开始结束的getter和setter
		

}
