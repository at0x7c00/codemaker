package com.lenovots.crm.project.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.VersionRecord;
import com.lenovots.crm.project.service.IVersionRecordService;

/**
 * @author 胡桥
 * 2012-12-18 09:29:56
 */
@Controller("versionRecordAction") 
@Scope(value="prototype")
public class VersionRecordAction extends BaseAction<VersionRecord> {
	
	private static Logger logger = Logger.getLogger(VersionRecordAction.class);
	
	private VersionRecord versionRecord;
	
	@Resource
	private IVersionRecordService versionRecordService;
	
	private List<VersionRecord> versionRecordList;
	
	
		

	//复杂关联关系的list
	
	//复杂关联关系的Id
	
	//复杂关联关系的Service
	
	//日期类型定义
	private Date addTimeStart;//日期
private Date addTimeEnd;//日期

	

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			versionRecord = versionRecordService.findById(id);
		}else{
			versionRecord = new VersionRecord();
		}
	}
	
	@Override
	public String addUI() throws Exception {
		//复杂关联关系数据准备
		
		return SUCCESS;
	}

	@Override
	public String updateUI() throws Exception {
		//复杂关联关系数据准备
		
		return SUCCESS;
	}

	@Override
	public VersionRecord getModel() {
		return versionRecord;
	}

	@Override
	public String add() throws Exception {
		//复杂关联关系保存
		
		//默认系统时间类型保存
		versionRecord.setAddTime(new Date());
		versionRecordService.add(versionRecord);
		
		return RELOAD;
	}

	@Override
	public String update() throws Exception {
		//复杂关联关系保存
		
		versionRecordService.update(versionRecord);
		return RELOAD;
	}
	
	public void prepareList(){
		try {
			prepareModel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String list() throws Exception {
		if(pageNum==null || pageNum<0){
			pageNum=0;
		}
		if(pageSize==null || pageSize<=0){
			pageSize=15;
		}
		//复杂关联关系数据准备
		createPageBean(false);
		return SUCCESS;
	}
	
	private void createPageBean(boolean all){
		StringBuffer hql = new StringBuffer("FROM VersionRecord versionRecord WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		if(versionRecord.getName()!=null && ! versionRecord.getName().trim().equals("")){hql.append(" AND versionRecord.name like ? ");
		params.add("%"+versionRecord.getName()+"%");}if(addTimeStart!=null){	hql.append(" AND versionRecord.addTime>= ? ");	params.add(addTimeStart);}if(addTimeEnd!=null){	hql.append(" AND versionRecord.addTime<= ? ");	params.add(addTimeEnd);}if(versionRecord.getRemark()!=null && ! versionRecord.getRemark().trim().equals("")){hql.append(" AND versionRecord.remark like ? ");
		params.add("%"+versionRecord.getRemark()+"%");}
		hql.append(" ORDER BY versionRecord.addTime DESC");
		if(all){
			list = versionRecordService.findByHql(hql.toString(), params.toArray());
		}else{//分页
			pageBean = versionRecordService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, params.toArray());
		}
	}
	
	public VersionRecord getVersionRecord() {
		return versionRecord;
	}

	public void setVersionRecord(VersionRecord versionRecord) {
		this.versionRecord = versionRecord;
	}

	public List<VersionRecord> getVersionRecordList() {
		return versionRecordList;
	}

	public void setVersionRecordList(List<VersionRecord> versionRecordList) {
		this.versionRecordList = versionRecordList;
	}
	
	//导出
	
	//复杂关联关系List的getter和setter
	
	//复杂关联关系Id的getter和setter
	
	//用于查询的日期类型的开始结束的getter和setter
	public Date getAddTimeStart(){return this.addTimeStart;}
public void setAddTimeStart(Date addTimeStart){this.addTimeStart=addTimeStart;}
public Date getAddTimeEnd(){return this.addTimeEnd;}
public void setAddTimeEnd(Date addTimeEnd){this.addTimeEnd=addTimeEnd;}

		

}
