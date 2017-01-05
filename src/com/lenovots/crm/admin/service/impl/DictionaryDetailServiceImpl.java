package com.lenovots.crm.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lenovots.crm.admin.entity.DictionaryDetail;
import com.lenovots.crm.admin.service.IDictionaryDetailService;
import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;


@Service("dictionaryDetailService")
public class DictionaryDetailServiceImpl extends CrmBaseServiceImpl<DictionaryDetail> implements IDictionaryDetailService{

	//根据编号获取字典详情
	public List<DictionaryDetail> getDetailByDictionaryNumber(String number) {
		return findByHql("FROM DictionaryDetail dd WHERE dd.parent.prefix=? ORDER BY dd.sort", new Object[]{number});
	}
	
	//根据编号和名称获取字典详情
	public DictionaryDetail getDetailByDictionaryNumberAndName(String number,String name) {
		List<DictionaryDetail> list = findByHql("FROM DictionaryDetail dd WHERE dd.parent.prefix=? AND dd.name=? ORDER BY dd.sort", new Object[]{number,name});
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
