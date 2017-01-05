package com.lenovots.crm.admin.service.impl;

import org.springframework.stereotype.Service;

import com.lenovots.crm.admin.entity.Dictionary;
import com.lenovots.crm.admin.service.IDictionaryService;
import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;


@Service("dictionaryService")
public class DictionaryServiceImpl extends CrmBaseServiceImpl<Dictionary> implements IDictionaryService{
}
