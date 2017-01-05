package com.lenovots.crm.admin.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.DeskModular;
import com.lenovots.crm.admin.service.IDeskModularService;
import com.lenovots.crm.common.action.BaseAction;


/**
 * @author 胡桥 2011-04-11
 */
@Controller("deskModularAction")
@Scope(value="prototype")
public class DeskModularAction extends BaseAction<DeskModular> {

	@Resource
	private IDeskModularService deskModularService;
	
	private static final long serialVersionUID = 1L;
	
	private DeskModular deskModular;
	
	private List<String> icons;
	
	@Override
	public String add() throws Exception {
		deskModular.setSortValue(deskModularService.getMaxSortValue() + 1);
		deskModularService.add(deskModular);
		return RELOAD;
	}

	@Override
	public String addUI() throws Exception {
		return SUCCESS;
	}

	@Override
	public String delete() throws Exception {
		deskModularService.deleteById(id);
		return RELOAD;
	}
	
	public String up() throws Exception {
		deskModularService.up(id);
		return RELOAD;
	}
	
	public String down() throws Exception {
		deskModularService.down(id);
		return RELOAD;
	}

	@Override
	public DeskModular getModel() {
		return deskModular;
	}

	@Override
	public String list() throws Exception {
		if(pageNum==null||pageNum<0){
			pageNum=0;
		}
		if(pageSize==null||pageSize<=0){
			pageSize=30;
		}
		list = deskModularService.findByHql("FROM DeskModular m ORDER BY m.sortValue", null);
		
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
        if(id==null){
        	deskModular=new DeskModular();
        }else{
        	deskModular=deskModularService.findById(id);
        }
	}

	@Override
	public String update() throws Exception {
		deskModularService.update(deskModular);
		return RELOAD;
	}

	@Override
	public String updateUI() throws Exception {
		deskModular = deskModularService.findById(id);
		return SUCCESS;
	}

	public DeskModular getDeskModular() {
		return deskModular;
	}

	public void setDeskModular(DeskModular deskModular) {
		this.deskModular = deskModular;
	}

	public List<String> getIcons() {
		return icons;
	}

	public void setIcons(List<String> icons) {
		this.icons = icons;
	}
	
}
