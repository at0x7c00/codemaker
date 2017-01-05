package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.entity.VarLabel;
import com.lenovots.crm.project.service.IProjectService;
import com.lenovots.crm.project.service.IVarLabelService;
/**
 * @author 胡桥
 * 2013-05-10 18:37:50
 */
@Controller("varLabelAction") 
@Scope(value="prototype")
public class VarLabelAction extends BaseAction<VarLabel> {
	
	private static Logger logger = Logger.getLogger(VarLabelAction.class);
	
	private VarLabel varLabel;
	
	@Resource
	private IVarLabelService varLabelService;
	
	private List<VarLabel> varLabelList;
	
	
		

	//复杂关联关系的list
	private List<Project> projectList;

	//复杂关联关系的Id
	private Integer projectId;

	//复杂关联关系的Service
	@Resource private IProjectService projectService;

	//日期类型定义
	
	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			varLabel = varLabelService.findById(id);
		}else{
			varLabel = new VarLabel();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		//复杂关联关系数据准备
		projectList = projectService.findAll();

		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		//复杂关联关系数据准备
		projectList = projectService.findAll();

		return SUCCESS;
	}

	@Override
	public VarLabel getModel() {
		return varLabel;
	}

	@Override
	public String add() throws Exception {
		//复杂关联关系保存
		if(projectId==null){	varLabel.setProject(null);}else{varLabel.setProject(projectService.findById(projectId));}
		//默认系统时间类型保存
		
		varLabelService.add(varLabel);
		
		return RELOAD;
	}

	@Override
	public String update() throws Exception {
		//复杂关联关系保存
		if(projectId==null){	varLabel.setProject(null);}else{varLabel.setProject(projectService.findById(projectId));}
		varLabelService.update(varLabel);
		return RELOAD;
	}

	@Override
	public String list() throws Exception {
		if(pageNum==null || pageNum<0){
			pageNum=0;
		}
		if(pageSize==null || pageSize<=0){
			pageSize=50;
		}
		//复杂关联关系数据准备
		projectList = projectService.findAll();

		createPageBean(false);
		
		return SUCCESS;
	}
	
	/**
	 * 按照标签名称查询
	 * @return
	 */
	public String findLabelByName(){
		String keyWord = ServletActionContext.getRequest().getParameter("keyWord");
		if(keyWord==null || keyWord.trim().equals("")){
			return SUCCESS;
		}
		String hql = "FROM VarLabel label WHERE label.name like ?";
		List<VarLabel> list = varLabelService.findByHql(hql, new Object[]{keyWord+"%"});
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		List<VarLabel> labels = new ArrayList<VarLabel>();
		for(VarLabel l : list){
			VarLabel label = new VarLabel();
			label.setCategory(l.getCategory());
			label.setName(l.getName());
			label.setProject(l.getProject());
			label.setRemark(l.getRemark());
			label.setValue(l.getValue());
			
			label.setName(label.getName().replaceAll("\"", "\\\\\\\""));
			label.setName(label.getName().replaceAll("\r", ""));
			label.setName(label.getName().replaceAll("\n", ""));
			
			label.setRemark(label.getRemark().replaceAll("\"", "\\\\\\\""));
			label.setRemark(label.getRemark().replaceAll("\r", ""));
			label.setRemark(label.getRemark().replaceAll("\n", ""));
			
			label.setValue(label.getValue().replaceAll("\"", "\\\\\\\""));
			label.setValue(label.getValue().replaceAll("\r", ""));
			label.setValue(label.getValue().replaceAll("\n", ""));
			
			labels.add(label);
		}
		setAttribute("varLabels",labels);
		return SUCCESS;
	}
	
	private void createPageBean(boolean all){
		StringBuffer hql = new StringBuffer("FROM VarLabel varLabel WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if(varLabel.getName()!=null && ! varLabel.getName().trim().equals("")){hql.append(" AND varLabel.name like ? ");
params.add("%"+varLabel.getName()+"%");}if(varLabel.getRemark()!=null && ! varLabel.getRemark().trim().equals("")){hql.append(" AND varLabel.remark like ? ");
params.add("%"+varLabel.getRemark()+"%");}if(varLabel.getCategory()!=null){hql.append(" AND varLabel.category=? ");
params.add(varLabel.getCategory());}if(varLabel.getValue()!=null && ! varLabel.getValue().trim().equals("")){hql.append(" AND varLabel.value like ? ");
params.add("%"+varLabel.getValue()+"%");}if(projectId!=null && projectId!=-1){	hql.append(" AND varLabel.project.id=? ");	params.add(projectId);}
		
		if(all){
			list = varLabelService.findByHql(hql.toString(), params.toArray());
		}else{//分页
			pageBean = varLabelService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, params.toArray());
		}
	}
	
	public VarLabel getVarLabel() {
		return varLabel;
	}

	public void setVarLabel(VarLabel varLabel) {
		this.varLabel = varLabel;
	}

	public List<VarLabel> getVarLabelList() {
		return varLabelList;
	}

	public void setVarLabelList(List<VarLabel> varLabelList) {
		this.varLabelList = varLabelList;
	}
	
	//导出
	
	
	
	//复杂关联关系List的getter和setter
	public List<Project> getProjectList(){return this.projectList;}
public void setProjectList(List<Project> projectList){this.projectList=projectList;}

	//复杂关联关系Id的getter和setter
	public Integer getProjectId(){return this.projectId;}
public void setProjectId(Integer projectId){this.projectId=projectId;}

	//用于查询的日期类型的开始结束的getter和setter
	
		

}
