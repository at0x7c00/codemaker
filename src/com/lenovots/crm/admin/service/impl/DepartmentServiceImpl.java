package com.lenovots.crm.admin.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lenovots.crm.admin.entity.Dept;
import com.lenovots.crm.admin.service.IDepartmentService;
import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;



@Service("departmentService")
public class DepartmentServiceImpl extends CrmBaseServiceImpl<Dept> implements IDepartmentService {

	//上溯
	public Dept getAncestryDept(Dept dept) {
		while(!dept.getCategory().equals(Dept.CITY_STATION)&&!dept.getCategory().equals(Dept.SUBSTATION)&&dept.getMainDept()!=null){
			dept=dept.getMainDept();
		}
		return dept;
	}

	//下溯
	public void getSubDepts(Dept dept,List<Dept> results) {
		results.add(dept);
		if(dept.getSubDeptList()!=null){
			for(Dept d:dept.getSubDeptList()){
				//添加科室或其他类型的子部门到结果集中,即，市局查询所有可见子部门时，看不到分局
				if(d.getCategory().equals(Dept.ABTEILUNG)||d.getCategory().equals(Dept.OTHERS)){
					getSubDepts(d, results);
				}
			}
		}
	}
	
	/**
	 * 树状形式显示所有部门信息
	 * @return
	 */
	public List<Dept> getAllDepartmentTreeView() {
		// 查询出持久态的Department集合
		List<Dept> topDepartments=findByHql("from Dept where mainDept is null",null);
		List<Dept> result = new ArrayList<Dept>();
		
		tree(topDepartments,"",result,null);
		return result;
	}

	public List<Dept> getProductCategoryTreeViewByFilter(Integer id){
		// 查询出持久态的Department集合
		List<Dept> topDepartments=findByHql("from Dept where  mainDept is null",null);
		List<Dept> result = new ArrayList<Dept>();
		
		tree(topDepartments,"",result,id);
		return result;
	}
	/**
	 * 遍历所有部门信息，生成树状信息List
	 * @param topDepartments
	 * @param prefixCode
	 * @param result
	 */
	private void tree(Collection<Dept> topDepartments, String prefixCode,List<Dept> result,Integer filterId){
		if(topDepartments==null)
			return;
		int count=0;
		for(Dept productCategory:topDepartments){
			count++;
			Dept temp_dep=new Dept();
			temp_dep.setId(productCategory.getId());
			String prefix=parsePrefix(prefixCode);
			if(count==topDepartments.size()){
				if(filterId==null||productCategory.getId()!=filterId){
				    temp_dep.setName(prefix+"┗━"+productCategory.getName());
				    result.add(temp_dep);
				    tree(productCategory.getSubDeptList(),prefixCode+"0",result,filterId);
				}
			}
			else{
				if(filterId==null||productCategory.getId()!=filterId){
					temp_dep.setName(prefix+"┣━"+productCategory.getName());
					result.add(temp_dep);
					tree(productCategory.getSubDeptList(),prefixCode+"1",result,filterId);
				}
			}
		}
	}
	/**
	 * 解析前缀，生成复制Department的名称的前缀
	 * @param prefixCode
	 * @return
	 */
	private String parsePrefix(String prefixCode) {
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

	
}
