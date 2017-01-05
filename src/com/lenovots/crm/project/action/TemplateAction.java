package com.lenovots.crm.project.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Template;
import com.lenovots.crm.project.entity.TemplateGroup;
import com.lenovots.crm.project.entity.VarLabel;
import com.lenovots.crm.project.service.ITemplateGroupService;
import com.lenovots.crm.project.service.ITemplateService;
import com.lenovots.crm.project.service.IVarLabelService;
import com.lenovots.crm.util.Constants;
import com.lenovots.crm.util.URLUtil;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
/**
 * @author 胡桥
 * 2013-07-31 11:50:05
 */
@Controller("templateAction") 
@Scope(value="prototype")
public class TemplateAction extends BaseAction<Template> {
	
	private static Logger logger = Logger.getLogger(TemplateAction.class);
	
	private Template template;
	
	@Resource
	private ITemplateService templateService;
	@Resource
	private ITemplateGroupService groupService;
	@Resource
	private IVarLabelService labelService;
	
	private List<Template> templateList;
	private List<TemplateGroup> groupList;
	private Integer groupId;
	

	//复杂关联关系的list
	
	//复杂关联关系的Id
	
	//复杂关联关系的Service
	
	//日期类型定义
	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			template = templateService.findById(id);
		}else{
			template = new Template();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		//复杂关联关系数据准备
		groupList = groupService.findAll();
		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		//复杂关联关系数据准备
		Template oldTemplate = template;
		template = new Template();
		template.setContent(oldTemplate.getContent());
		template.setGroup(oldTemplate.getGroup());
		template.setId(oldTemplate.getId());
		template.setName(oldTemplate.getName());
		template.setType(oldTemplate.getType());
		
		if(template.getType()==Template.TEMPLATE_TYPE_JSP){
			template.setContent(template.getContent().replaceAll("\\$\\{","\\@\\{"));
			template.setContent(template.getContent().replaceAll(Constants.LT_R,"<"));
			template.setContent(template.getContent().replaceAll(Constants.GT_R,">"));
			template.setContent(template.getContent().replaceAll(Constants.DOLLAR_R,"\\$"));
		}
		
		groupList = groupService.findAll();
		return SUCCESS;
	}

	@Override
	public Template getModel() {
		return template;
	}

	@Override
	public String add() throws Exception {
		template.setGroup(groupService.findById(groupId));
		if(template.getType()==Template.TEMPLATE_TYPE_JSP){
			template.setContent(template.getContent().replaceAll("\\<", Constants.LT));
			template.setContent(template.getContent().replaceAll("\\>", Constants.GT));
			template.setContent(template.getContent().replaceAll("\\$", Constants.DOLLAR));
			template.setContent(template.getContent().replaceAll("\\@\\{", "\\$\\{"));
		}
		
		Object id = templateService.add(template);
		template.setId((Integer)id);
		createJspFile(template);
		return RELOAD;
	}
	
	/**
	 * 创建jsp页面
	 * @param template
	 */
	private void createJspFile(Template template){
		String entityPageDir = ServletActionContext.getServletContext().getRealPath("/WEB-INF/pages/entity")+File.separator;
		File file = new File(entityPageDir+"codeView_"+template.getId()+".jsp");
		StringBuffer content = new StringBuffer("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\" %><%@ taglib prefix=\"fn\" uri=\"http://java.sun.com/jsp/jstl/functions\"%><%@taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\" %><%@page pageEncoding=\"UTF-8\" %>");
		content.append(replaceCustomLabel(template.getContent()));
		URLUtil.saveContentToFile(content.toString(), file,false);
	}
	
	private String replaceCustomLabel(String content){
		List<VarLabel> customLabels= labelService.findByHql("FROM VarLabel label WHERE label.category = ?", new Object[]{VarLabel.CATEGORY_CUSTOM});
		Map<String,String> labelMap = new HashMap<String,String>();
		for(VarLabel label : customLabels){
			labelMap.put(label.getName(), label.getValue());
		}
		Pattern pattern = Pattern.compile("\\#\\{[0-9_a-zA-Z\\.]*\\}");
		Matcher matcher = pattern.matcher(content);
		StringBuffer res = new StringBuffer();
		String labelValue = null;;
		String group = null;
		while(matcher.find()){
			group = matcher.group();
			if(group.length()<3){
				matcher.appendReplacement(res,"");
			}else{
				labelValue = labelMap.get(group.substring(2,group.length()-1));
				try {
					labelValue = labelValue==null?"":labelValue.replaceAll("\\$", "\\\\\\$");
					matcher.appendReplacement(res,labelValue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		matcher.appendTail(res);
		return res.toString();
	}
	
	private void deleteJspFile(Integer id){
		String entityPageDir = ServletActionContext.getServletContext().getRealPath("/WEB-INF/pages/entity")+File.separator;
		File file = new File(entityPageDir+"codeView_"+id+".jsp");
		if(file.exists()){
			file.delete();
		}
	}

	@Override
	public String update() throws Exception {
		template.setGroup(groupService.findById(groupId));
		if(template.getType()==Template.TEMPLATE_TYPE_JSP){
			template.setContent(template.getContent().replaceAll("\\<", Constants.LT));
			template.setContent(template.getContent().replaceAll("\\>", Constants.GT));
			template.setContent(template.getContent().replaceAll("\\$", Constants.DOLLAR));
			template.setContent(template.getContent().replaceAll("\\@\\{", "\\$\\{"));
		}
		createJspFile(template);
		templateService.update(template);
		return RELOAD;
	}

	/**
	 * 将模板内容更新至jsp文件中
	 * @return
	 */
	public String refreshToFile(){
		if(id!=null){
			template = templateService.findById(id);
			createJspFile(template);
		}else{
			List<Template> templates = templateService.findAll();
			for(Template t : templates){
				createJspFile(t);
			}
		}
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
		groupList = groupService.findAll();
		createPageBean(false);
		return SUCCESS;
	}
	
	private void createPageBean(boolean all){
		StringBuffer hql = new StringBuffer("FROM Template template WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if (template.getName() != null && !template.getName().trim().equals("")) {
			hql.append(" AND template.name like ? ");
			params.add("%" + template.getName() + "%");
		}
		if (template.getContent() != null
				&& !template.getContent().trim().equals("")) {
			hql.append(" AND template.content like ? ");
			params.add("%" + template.getContent() + "%");
		}
		
		if(groupId!=null && groupId!=-1){
			hql.append(" AND template.group.id=? ");
			params.add(groupId);
		}
		if(all){
			list = templateService.findByHql(hql.toString(), params.toArray());
		}else{//分页
			pageBean = templateService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, params.toArray());
		}
	}
	
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public List<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}

	public List<TemplateGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<TemplateGroup> groupList) {
		this.groupList = groupList;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Override
	public String delete() throws Exception {
		if(ids==null){
			return RELOAD;
		}
		for(Integer id:ids){
			deleteJspFile(id);
		    baseService.deleteById(id,this.clazz);	
		}
		
		return RELOAD;
	}
	
	
	public String copy(){
		if(ids==null){
			return RELOAD;
		}
		for(Integer id:ids){
		    Template template = (Template)baseService.findById(id,this.clazz);	
		    Template copyOfTemplate = new Template();
		    copyOfTemplate.setContent(template.getContent());
		    copyOfTemplate.setGroup(template.getGroup());
		    copyOfTemplate.setName("Copy of " + template.getName());
		    copyOfTemplate.setNeedRefresh(template.isNeedRefresh());
		    copyOfTemplate.setType(template.getType());
		    templateService.add(copyOfTemplate);
		}
		return RELOAD;
	}
}
