package com.lenovots.crm.admin.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Dictionary;
import com.lenovots.crm.admin.entity.DictionaryDetail;
import com.lenovots.crm.admin.service.IDictionaryDetailService;
import com.lenovots.crm.admin.service.IDictionaryService;
import com.lenovots.crm.common.action.BaseAction;


/**
 * @author 胡桥 2011-06-08
 */
@Controller("dictionaryDetailAction")
@Scope(value="prototype")
public class DictionaryDetailAction extends BaseAction<DictionaryDetail> {


	private static final long serialVersionUID = 1L;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IDictionaryDetailService dictionaryDetailService;
	
	private DictionaryDetail dicDetail;
	
	private Dictionary dic;
	
	private Integer parentId;
	
	@Override
	public String add() throws Exception {
		dicDetail.setParent(dictionaryService.findById(parentId));
		System.out.println("parentId="+parentId);
		dictionaryDetailService.add(dicDetail);
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
				DictionaryDetail detail=dictionaryDetailService.findById(id);
				detail.getParent().getDetails().remove(detail);
				dictionaryDetailService.deleteById(id);
			}
		}
		return RELOAD;
	}

	@Override
	public DictionaryDetail getModel() {
		return dicDetail;
	}

	@Override
	public String list() throws Exception {
		if(parentId!=null){
			dic=dictionaryService.findById(parentId);
		}
		if(pageNum==null||pageNum<0){
			pageNum=0;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		pageBean= dictionaryDetailService.findPageBeanOfHql("FROM DictionaryDetail dd WHERE dd.parent.id=? ORDER BY dd.sort", pageNum, pageSize, new Object[]{parentId});
		return SUCCESS;
	}

    public String up(){
    	dicDetail=dictionaryDetailService.findById(id);
    	if(dicDetail!=null){
    		if(dicDetail.getSort()==null){
    			dicDetail.setSort(1);
    		}else{
    			dicDetail.setSort(dicDetail.getSort().intValue()-1);
    		}
    	}
    	dictionaryDetailService.update(dicDetail);
    	return RELOAD;
    }
    
    public String down(){
    	dicDetail=dictionaryDetailService.findById(id);
    	if(dicDetail!=null){
    		if(dicDetail.getSort()==null){
    			dicDetail.setSort(1);
    		}else{
    			dicDetail.setSort(dicDetail.getSort().intValue()+1);
    		}
    	}
    	dictionaryDetailService.update(dicDetail);
    	return RELOAD;
    }
	@Override
	protected void prepareModel() throws Exception {
		if(id==null){
			dicDetail=new DictionaryDetail();
		}else{
			dicDetail=dictionaryDetailService.findById(id);
		}
	}

	@Override
	public String update() throws Exception {
		
		dictionaryDetailService.update(dicDetail);
		
		return RELOAD;
	}

	@Override
	public String updateUI() throws Exception {
		dicDetail=dictionaryDetailService.findById(id);
		return SUCCESS;
	}

	public DictionaryDetail getDicDetail() {
		return dicDetail;
	}

	public void setDicDetail(DictionaryDetail dicDetail) {
		this.dicDetail = dicDetail;
	}

	public Dictionary getDic() {
		return dic;
	}

	public void setDic(Dictionary dic) {
		this.dic = dic;
		
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
