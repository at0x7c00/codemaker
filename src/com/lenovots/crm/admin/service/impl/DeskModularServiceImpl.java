package com.lenovots.crm.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lenovots.crm.admin.entity.DeskModular;
import com.lenovots.crm.admin.service.IDeskModularService;
import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;


@Service("deskModularService")
public class DeskModularServiceImpl extends CrmBaseServiceImpl<DeskModular> implements IDeskModularService{

	
	@Override
	public void down(Integer id) {
		if(id==null){
			return;
		}
		DeskModular p=findById(id);
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
		DeskModular p=findById(id);
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
	private void changeSortValue(DeskModular p1,DeskModular p2){
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
	private DeskModular getNearByBrother(DeskModular p,int type){
		//找到第一个sortValue比自己小的兄弟
		List<DeskModular> brothers=null;
		if(type==0){
			brothers=findByHql("FROM DeskModular p WHERE  p.sortValue<? ORDER BY p.sortValue DESC", new Object[]{p.getSortValue()});
		}else if(type==1){
			brothers=findByHql("FROM DeskModular p WHERE  p.sortValue>? ORDER BY p.sortValue", new Object[]{p.getSortValue()});
		}
		
		if(brothers!=null&&brothers.size()>0){
			return brothers.get(0);
		}
		return null;
	}

	@Override
	public Integer getMaxSortValue() {
		List<DeskModular> brothers=null;
		brothers=findByHql("FROM DeskModular p ORDER BY p.sortValue DESC", null);
		if(brothers!=null&&brothers.size()>0){
			return brothers.get(0).getSortValue();
		}else{
			return 0;
		}
	}
	
}
