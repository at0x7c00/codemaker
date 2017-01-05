package com.lenovots.crm.admin.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Dictionary;
import com.lenovots.crm.admin.service.IDictionaryService;
import com.lenovots.crm.common.action.BaseAction;


/**
 * @author 胡桥 2011-04-11
 */
@Controller("dictionaryAction")
@Scope(value="prototype")
public class DictionaryAction extends BaseAction<Dictionary> {

	private static final long serialVersionUID = 1L;

	@Resource
	private IDictionaryService dictionaryService;
	
	private Dictionary dic;
	
	@Override
	public String add() throws Exception {
		dictionaryService.add(dic);
		return RELOAD;
	}

	@Override
	public String addUI() throws Exception {
		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		if(ids!=null){
			for(Integer id:ids){
				dictionaryService.deleteById(id);
			}
		}
		return RELOAD;
	}

	@Override
	public Dictionary getModel() {
		return dic;
	}

	@Override
	public String list() throws Exception {
		if(pageNum==null||pageNum<0){
			pageNum=0;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		pageBean= dictionaryService.findPageBeanOfHql("FROM Dictionary d ", pageNum, pageSize, null);
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id==null){
			dic=new Dictionary();
		}else{
			dic=dictionaryService.findById(id);
		}
	}

	@Override
	public String update() throws Exception {
		dictionaryService.update(dic);
		return RELOAD;
	}

	@Override
	public String updateUI() throws Exception {
		dic=dictionaryService.findById(id);
		return SUCCESS;
	}


	public Dictionary getDic() {
		return dic;
	}

	public void setDic(Dictionary dic) {
		this.dic = dic;
	}
	
}
