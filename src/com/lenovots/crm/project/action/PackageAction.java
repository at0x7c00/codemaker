package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Packagee;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.service.IPackageService;
import com.lenovots.crm.project.service.IProjectService;
import com.lenovots.crm.util.CommonUtil;



@Controller("packageAction")
@Scope(value="prototype")
public class PackageAction extends BaseAction<Packagee> {
	@Resource
	private IPackageService packageService;
	@Resource
	private IProjectService projectService;
	
	private com.lenovots.crm.project.entity.Packagee pack;
	
	private List<Packagee> parentPacks;
	private List<Project> projectList;
	
	private Integer parentId;
	private Integer projectId;
	
	private static final long serialVersionUID = 8856961694147206209L;
	
	
	public void prepareModel(){
		if(id!=null){
			pack = packageService.findById(id);
		}else{
			pack = new Packagee();
		}
	}
	
	public String list(){
		initPage();
		Project project = (Project) ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
		if(project!=null && projectId==null){
			projectId = project.getId();
		}
		projectList = projectService.findAll();
		StringBuffer hql = new StringBuffer("FROM Packagee p WHERE p.parent is NULL");
		List<Object> params = new ArrayList<Object>();
		if(projectId!=null){
			hql.append(" AND p.project.id=?");
			params.add(projectId);
		}
		list = new ArrayList<Packagee>();
		CommonUtil.tree(packageService.findByHql(hql.toString(),params.toArray()), list, "", null);
		return SUCCESS;
	}
	
	public String addUI(){
		projectList = projectService.findAll();
		parentPacks = new ArrayList<Packagee>();
		Project project = (Project) ServletActionContext.getRequest().getSession().getAttribute("PROJECT");
		if(project!=null && projectId==null){
			projectId = project.getId();
		}
		if(projectId!=null){
			CommonUtil.tree(packageService.findByHql("FROM Packagee p WHERE p.parent is NULL AND p.project.id=?", new Object[]{projectId}), parentPacks, "", null);
		}else{
			CommonUtil.tree(packageService.findByHql("FROM Packagee p WHERE p.parent is NULL", null), parentPacks, "", null);
		}
		
		return SUCCESS;
	}
	
	public String add(){
		setAttribute("projectId", projectId);
		if(parentId!=null){
			pack.setParent(packageService.findById(parentId));
		}else{
			pack.setParent(null);
		}
		pack.setProject(projectService.findById(projectId));
		packageService.add(pack);
		return RELOAD;
	}
	
	public String updateUI(){
		projectList = projectService.findAll();
		parentPacks = new ArrayList<Packagee>();
		CommonUtil.tree(packageService.findByHql("FROM Packagee p WHERE p.parent is NULL", null), parentPacks, "", null);
		return SUCCESS;
	}
	
	public String update(){
		setAttribute("projectId", projectId);
		if(parentId!=null){
			pack.setParent(packageService.findById(parentId));
		}else{
			pack.setParent(null);
		}
		Project project = projectService.findById(projectId);
		cascadeUpdate(pack,project);
		packageService.update(pack);
		return RELOAD;
	}
	
	private void cascadeUpdate(Packagee pack,Project project){
		if(pack.getChildren()!=null){
			for(Packagee p : pack.getChildren()){
				cascadeUpdate(p,project);
			}
		}
		pack.setProject(project);
	}
	
	public String delete(){
		if(ids!=null){
			for(Integer id : ids){
				pack = packageService.findById(id);
				if(pack.getParent()!=null){
					pack.getParent().getChildren().remove(pack);
				}
				pack.setParent(null);
				packageService.deleteById(id);
			}
		}
		return RELOAD;
	}

	public IPackageService getPackageService() {
		return packageService;
	}

	public void setPackageService(IPackageService packageService) {
		this.packageService = packageService;
	}

	public com.lenovots.crm.project.entity.Packagee getPack() {
		return pack;
	}

	public void setPack(com.lenovots.crm.project.entity.Packagee pack) {
		this.pack = pack;
	}

	public List<Packagee> getParentPacks() {
		return parentPacks;
	}

	public void setParentPacks(List<Packagee> parentPacks) {
		this.parentPacks = parentPacks;
	}

	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
}
