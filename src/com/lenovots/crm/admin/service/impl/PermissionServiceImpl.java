package com.lenovots.crm.admin.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lenovots.crm.admin.entity.Permission;
import com.lenovots.crm.admin.service.IPermissionService;
import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;


@Service("permissionService")
public class PermissionServiceImpl extends CrmBaseServiceImpl<Permission> implements IPermissionService{

	/**
	 * 遍历所有菜单信息，生成树状信息List
	 * @param topDepartments
	 * @param prefixCode
	 * @param result
	 */
	public void tree(Collection<Permission> topDepartments, String prefixCode,List<Permission> result,Integer filterId){
		if(topDepartments==null)
			return;
		int count=0;
		for(Permission subDept:topDepartments){
			count++;
			Permission temp_dep=new Permission();
			temp_dep.setId(subDept.getId());  
			temp_dep.setIcon(subDept.getIcon());
			temp_dep.setLevel(subDept.getLevel());
			temp_dep.setParent(subDept.getParent());
			temp_dep.setUrl(subDept.getUrl());
			temp_dep.setSortValue(subDept.getSortValue());
			String prefix=parsePrefix(prefixCode);
			if(count==topDepartments.size()){
				if(filterId==null||subDept.getId()!=filterId){
				    temp_dep.setName(prefix+"┗━"+subDept.getName());
				    result.add(temp_dep);
				    tree(subDept.getChildren(),prefixCode+"0",result,filterId);
				}
			}
			else{
				if(filterId==null||subDept.getId()!=filterId){
					temp_dep.setName(prefix+"┣━"+subDept.getName());
					result.add(temp_dep);
					tree(subDept.getChildren(),prefixCode+"1",result,filterId);
				}
			}
		}
	}
	
	/**
	 * 解析前缀，生成复制Department的名称的前缀
	 * @param prefixCode
	 * @return
	 */
	private static String parsePrefix(String prefixCode) {
		String str=" ";
		char[] array=prefixCode.toCharArray();
		for(int i=0;i<array.length;i++){
			char c=array[i];
			if(c=='1'){
				str+="┃&nbsp;&nbsp;";
			}else{
				str+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		}
		return str;
	}

	@Override
	public void down(Integer id) {
		if(id==null){
			return;
		}
		Permission p=findById(id);
		if(p==null){
			return;
		}
		changeSortValue(p,getNearByBrother(p,1));
	}

	@Override
	public void up(Integer id) {
		if(id==null){
			return;
		}
		Permission p=findById(id);
		if(p==null){
			return;
		}
		changeSortValue(p,getNearByBrother(p,0));
	}
	
	/**
	 * 交换排序号
	 * @param p1
	 * @param p2
	 */
	private void changeSortValue(Permission p1,Permission p2){
		if(p1==null||p2==null){
			return;
		}
		if(p1.getSortValue()==null){
			p1.setSortValue(p1.getId());
		}
		if(p2.getSortValue()==null){
			p2.setSortValue(p2.getId());
		}
		int sortValue=p1.getSortValue();
		p1.setSortValue(p2.getSortValue());
		p2.setSortValue(sortValue);
		update(p1);
		update(p2);
	}
	    
	/** 
	 * 获取临近的兄弟
	 * @param p
	 * @param type  0:表示比自己小的，1表示比自己大的
	 * @return 如果没找到返回null
	 */
	private Permission getNearByBrother(Permission p,int type){
		Permission parent=p.getParent();
		//找到第一个sortValue比自己小的兄弟
		List<Permission> brothers=null;
		if(type==0){
			if(parent==null){
				brothers=findByHql("FROM Permission p WHERE p.parent is null AND p.sortValue<? ORDER BY p.sortValue DESC", new Object[]{p.getSortValue()});
			}else{
				brothers=findByHql("FROM Permission p WHERE p.parent=? AND p.sortValue<? ORDER BY p.sortValue DESC", new Object[]{parent,p.getSortValue()});
			}
		}else if(type==1){
			if(parent==null){
				brothers=findByHql("FROM Permission p WHERE p.parent is null AND p.sortValue>? ORDER BY p.sortValue", new Object[]{p.getSortValue()});
			}else{
				brothers=findByHql("FROM Permission p WHERE p.parent=? AND p.sortValue>? ORDER BY p.sortValue", new Object[]{parent,p.getSortValue()});
			}
		}
		
		if(brothers!=null&&brothers.size()>0){
			return brothers.get(0);
		}
		return null;
	}

	@Override
	public Integer getMaxSortValue(Integer parentId) {
		List<Permission> brothers=null;
		if(parentId==null || parentId==-1){
			brothers=findByHql("FROM Permission p WHERE p.parent is NULL ORDER BY p.sortValue DESC",null);
		}else{
			brothers=findByHql("FROM Permission p WHERE p.parent.id=? ORDER BY p.sortValue DESC", new Object[]{parentId});
		}
		if(brothers!=null&&brothers.size()>0){
			return brothers.get(0).getSortValue();
		}else{
			return 0;
		}
	}
	
}
