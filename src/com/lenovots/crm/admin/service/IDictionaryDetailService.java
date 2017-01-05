package com.lenovots.crm.admin.service;

import java.util.List;

import com.lenovots.crm.admin.entity.DictionaryDetail;
import com.lenovots.crm.common.service.IBaseService;



public interface IDictionaryDetailService extends IBaseService<DictionaryDetail>{
	
	public List<DictionaryDetail> getDetailByDictionaryNumber(String number);
	
	public DictionaryDetail getDetailByDictionaryNumberAndName(String number,String name);
	
}
