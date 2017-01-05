package com.lenovots.crm.admin.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Permission;
import com.lenovots.crm.admin.service.IPermissionService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.util.PageBean;


/**
 * @author 胡桥 2011-04-11
 */
@Controller("permissionAction")
@Scope(value="prototype")
public class PermissionAction extends BaseAction<Permission> {

	@Resource
	private IPermissionService permissionService;
	
	private static final long serialVersionUID = 1L;
	
	private Integer[] optionalIds;
	
	private Permission permission;
	
	private Integer parentId;
	
	private List<String> icons;
	
	@Override
	public String add() throws Exception {
		if(parentId!=null){
			permission.setParent(permissionService.findById(parentId));
		}
		List<Integer> tmpOptionalIds = new ArrayList<Integer>();
		if(optionalIds!=null){
			for(Integer i: optionalIds){
				tmpOptionalIds.add(i);
			}
		}
		permission.setSortValue(permissionService.getMaxSortValue(parentId) + 1);
		permissionService.add(permission);
		Permission tmpP = null;
		if(tmpOptionalIds.contains(0)){//详情
			tmpP = getSubPermission(permission, 0);
			savePermission(tmpP);
		}
		if(tmpOptionalIds.contains(1)){//添加
			tmpP = getSubPermission(permission, 1);
			savePermission(tmpP);
		}
		if(tmpOptionalIds.contains(2)){//修改
			tmpP = getSubPermission(permission, 2);
			savePermission(tmpP);
		}
		if(tmpOptionalIds.contains(3)){//删除
			tmpP = getSubPermission(permission, 3);
			savePermission(tmpP);
		}
		if(tmpOptionalIds.contains(4)){//导入
			tmpP = getSubPermission(permission, 4);
			savePermission(tmpP);
			tmpP = getSubPermission(permission, 6);
			savePermission(tmpP);
			tmpP = getSubPermission(permission, 7);
			savePermission(tmpP);
			tmpP = getSubPermission(permission, 8);
			savePermission(tmpP);
		}
		if(tmpOptionalIds.contains(5)){//导出  
			tmpP = getSubPermission(permission, 5);
			savePermission(tmpP);
			tmpP = getSubPermission(permission, 8);
			savePermission(tmpP);
		}
		return RELOAD;
	}
	
	private void savePermission(Permission p){
		if(p!=null){
			permissionService.add(p);
		}
	}
	
	/**
	 * 获取子级菜单
	 * @param parent 父级菜单 
	 * @param type 0 for detail, 1:for add, 2 for update, 3 for delete,4 for importExcel ,5 for export, 6 for importProgress, 7 for importReport, 8 for exportEmptyExcel
	 * @return
	 */
	private Permission getSubPermission(Permission parent,int type ){
		Permission p = null;
		if( type<1 || type>8 ){
			return p;
		}
		if(parent.getUrl()==null || parent.getUrl().trim().equals("")){
			return p;
		}
		p = new Permission();
		String url = parent.getUrl();
		url = url.substring(0, url.indexOf("_")+1);
		String pName = parent.getName().replaceAll("管理", "").replaceAll("设置", "");
		p.setParent(parent);
		p.setDisplay(1);
		p.setSortValue(type);
		switch(type){
		case 0:
			url += "detail";
			pName = pName + "详情查看";
			break;
		case 1:
			url += "add";
			pName = "添加"+pName;
			break;
		case 2:
			url +="update";
			pName = "修改"+pName;
			break;
		case 3:
			url +="delete";
			pName = "删除"+pName;
			break;
		case 4:
			url +="importExcel";
			pName = "导入"+pName;
			break;
		case 5:
			url +="export";
			pName = "导出"+pName;
			break;
		case 6:
			url +="importProgress";
			pName += "导入进度";
			break;
		case 7:
			url +="importReport";
			pName += "导入结果";
			break;
		case 8:
			url +="exportEmptyExcel";
			pName += "导入模版下载";
			break;
		}
		url +=".action";
		p.setUrl(url);
		p.setName(pName);
		return p;
	}

	@Override
	public String addUI() throws Exception {
		String path=ServletActionContext.getServletContext().getRealPath("/images/func/");
		File file=new File(path);
		icons=new ArrayList<String>();
		for(File f:file.listFiles()){
		    icons.add(f.getName());
		}
		list=new ArrayList<Permission>();
		permissionService.tree(permissionService.findByHql("FROM Permission p WHERE p.parent IS NULL ORDER BY p.sortValue", null), "", list, null);
		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		permission=permissionService.findById(id);
		Permission parent=permission.getParent();
		if(parent!=null){
			parent.getChildren().remove(permission);
			permission.setParent(null);
			permissionService.update(parent);
		}
		permissionService.deleteById(id);
		return RELOAD;
	}
	
	public String up() throws Exception {
		permissionService.up(id);
		return RELOAD;
	}
	
	public String down() throws Exception {
		permissionService.down(id);
		return RELOAD;
	}

	@Override
	public Permission getModel() {
		return permission;
	}

	@Override
	public String list() throws Exception {
		if(pageNum==null||pageNum<0){
			pageNum=0;
		}
		if(pageSize==null||pageSize<=0){
			pageSize=30;
		}
		list=new ArrayList<Permission>();
		permissionService.tree(permissionService.findByHql("FROM Permission p WHERE p.parent IS NULL ORDER BY sortValue ", null), "", list, null);
		pageBean=new PageBean<Permission>(pageNum,pageSize,list.size(),list);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
        if(id==null){
        	permission=new Permission();
        }else{
        	permission=permissionService.findById(id);
        }
	}

	@Override
	public String update() throws Exception {
		if(!"1".equals(getParameter("onlySortValue"))){
			if(parentId!=null){
				permission.setParent(permissionService.findById(parentId));
			}
		}
		permissionService.update(permission);
		return RELOAD;
	}

	@Override
	public String updateUI() throws Exception {
		String path=ServletActionContext.getServletContext().getRealPath("/images/func/");
		File file=new File(path);
		icons=new ArrayList<String>();
		for(File f:file.listFiles()){
		    icons.add(f.getName());
		}
		list=new ArrayList<Permission>();
		permissionService.tree(permissionService.findByHql("FROM Permission p WHERE p.parent IS NULL", null), "", list, null);
		return SUCCESS;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<String> getIcons() {
		return icons;
	}

	public void setIcons(List<String> icons) {
		this.icons = icons;
	}

	public Integer[] getOptionalIds() {
		return optionalIds;
	}

	public void setOptionalIds(Integer[] optionalIds) {
		this.optionalIds = optionalIds;
	}
	
}
