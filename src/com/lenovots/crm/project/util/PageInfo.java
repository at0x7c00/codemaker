package com.lenovots.crm.project.util;

import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Property;
import com.lenovots.crm.util.DateUtil;

/**
 * 页面生成参数
 * @author 胡桥
 * Jul 26, 2012  10:44:53 PM
 */
public class PageInfo {
	
	public static final int PAGETYPE_LIST=0;
	public static final int PAGETYPE_ADD=1;
	public static final int PAGETYPE_UPDATE=2;
	public static final int PAGETYPE_IMPORT = 3;
	public static final int PAGETYPE_DETAIL = 4;
	
	private Integer pageType;
	
	private Entity entity;
	private String pageTitle;
	private String tabTitle;
	private String addUrl;
	private String updateUrl;
	private String updateUIUrl;
	private String addUIUrl;
	private String deleteUrl;
	private String beanName;
	private String beanIDName;
	private String headTds;
	private String bodyTds;
	private String addInputTrs;
	private String updateInputTrs;
	private String detailInputTrs;
	private String emptyColspan;
	
	private String queryTable;
	
	//IMP_CHECK_REPEAT_CHKS
	private String impCheckRepeatChks;
	
	public PageInfo(Entity entity,Integer pageType){
		this.entity = entity;
		this.pageType = pageType;
	}
	
	public Entity getEntity() {
		return entity;
	}
	public String getPageTitle(){
		if(pageTitle==null){
			if(pageType==PAGETYPE_ADD){
				pageTitle="添加";
				pageTitle=pageTitle+entity.getName();
			}else if(pageType==PAGETYPE_UPDATE){
				pageTitle="修改";
				pageTitle=pageTitle+entity.getName();
			}else if(pageType==PAGETYPE_LIST){
				pageTitle="列表";
				pageTitle=entity.getName() + pageTitle;
			}else{
				pageTitle="导入";
				pageTitle = entity.getName() + pageTitle;
			}
		}
		return pageTitle;
	}
	public String getTabTitle() {
		if(tabTitle==null){
			if(pageType==PAGETYPE_ADD){
				tabTitle="添加";
				tabTitle=tabTitle+entity.getName();
			}else if(pageType==PAGETYPE_UPDATE){
				tabTitle="修改";
				tabTitle=tabTitle+entity.getName();
			}else if(pageType==PAGETYPE_LIST){
				tabTitle="列表";
				tabTitle=entity.getName() + tabTitle;
			}else{
				tabTitle="导入";
				tabTitle = entity.getName() + tabTitle;
			}
		}
		return tabTitle;
	}
	public String getAddUrl() {
		if(addUrl==null){
			addUrl = entity.getLowerClassName()+"_add.action";
		}
		return addUrl;
	}
	public String getUpdateUrl() {
		if(updateUrl==null){
			updateUrl = entity.getLowerClassName()+"_update.action";
		}
		return updateUrl;
	}
	public String getUpdateUIUrl() {
		if(updateUIUrl==null){
			updateUIUrl =entity.getLowerClassName()+"_updateUI.action";
		}
		return updateUIUrl;
	}
	public String getAddUIUrl() {
		if(addUIUrl==null){
			addUIUrl = entity.getLowerClassName()+"_addUI.action";
		}
		return addUIUrl;
	}
	public String getDeleteUrl() {
		if(deleteUrl==null){
			deleteUrl = entity.getLowerClassName()+"_delete.action";
		}
		return deleteUrl;
	}
	public String getBeanName() {
		if(beanName==null){
			beanName = entity.getLowerClassName();
		}
		return beanName;
	}
	public String getBeanIDName(){
		if(beanIDName==null){
			beanIDName = entity.getIdColumnName();
		}
		return beanIDName;
	}
	@Deprecated
	public String getHeadTds() {
		if(headTds!=null){
			return headTds;
		}
		StringBuffer res = new StringBuffer();
		res.append("<td align=\"center\"><INPUT TYPE=\"CHECKBOX\" id=\"controlCheckbox\" onClick=\"selectAll('ids');\" value=\"ON\"></td>");
		for(Property prop:entity.getProperties()){
			if(prop.getDisplay()==0){
				continue;
			}
			res.append("<td>").append(prop.getName()).append("</td>").append("\r\n");
		}
		res.append("<td>").append("操作").append("</td>").append("\r\n");
		headTds=res.toString();
		return headTds;
	}
	
	public String getHeadTds2() {
		if(headTds!=null){
			return headTds;
		}
		StringBuffer res = new StringBuffer();
		res.append("<!--<th width=\"30\" align=\"center\"><input type=\"checkbox\" group=\"").append(entity.getIdColumnName()).append("s\" class=\"checkboxCtrl\"></th>-->");
		if(entity.getIdGenerateType() == Entity.ID_GENERATE_TYPE_AUTO_INCREMENT){
			res.append("<th orderField=\"id\" <c:if test=\"${pageBean.orderField=='id'}\">class=\"${pageBean.orderDirection}\"</c:if>>编号</th>").append("\r\n");
		}
		for(Property prop:entity.getProperties()){
			if(prop.getDisplay()==0){
				continue;
			}
			res.append("<th orderField=\"").append(prop.getPropName()).append("\"  <c:if test=\"${pageBean.orderField=='").append(prop.getPropName()).append("'}\">class=\"${pageBean.orderDirection}\"</c:if>>").append(prop.getName()).append("</th>").append("\r\n");
		}
		headTds=res.toString();
		return headTds;
	}
	@Deprecated
	public String getBodyTds() {
		if(bodyTds!=null){
			return bodyTds;
		}
		StringBuffer res = new StringBuffer();
		//res.append("<td><INPUT TYPE=\"CHECKBOX\" id=\"controlCheckbox\" onClick=\"selectAll('ids');\" value=\"ON\"></td>");
		for(Property prop:entity.getProperties()){
			if(prop.getDisplay()==0){
				continue;
			}
			if(prop.getDataType()==Property.DATATYPE_DICTIONARY){
				res.append("<td>").append("${tempRecord.").append(prop.getPropName()).append(".name}").append("</td>").append("\r\n");
			}else if(prop.getDataType()==Property.DATATYPE_COMPLEX){ 
				res.append("<td>").append("${tempRecord.").append(prop.getPropName());
				if(prop.getRelationType() != Property.RELATIONTYPE_MANY_TO_MANY && prop.getRelationType() != Property.RELATIONTYPE_ONE_TO_MANY){
					if(prop.getValuePath()!=null && !prop.getValuePath().trim().equals("")){
						res.append(".").append(prop.getValuePath());
					}
				}
				res.append("}").append("</td>").append("\r\n");
			}else if(prop.getDataType()==Property.DATATYPE_DATE){
				res.append("<td>").append("<fmt:formatDate value=\"").append("${tempRecord.").append(prop.getPropName());
				res.append("}\" pattern=\"").append(prop.getTimeFormat()).append("\"/>").append("</td>").append("\r\n");
			}else{
				res.append("<td>");
				if(prop.getDataType()==Property.DATATYPE_STRING && prop.getBriefLength() != 0){
					res.append("<xooxle:contentShower length=\"").append(prop.getBriefLength()).append("\" content=\"${tempRecord.").append(prop.getPropName()).append("}\"></xooxle:contentShower>");
				}else if(prop.getDataType()==Property.DATATYPE_ENUM){
					res.append("<c:choose>");
					String value;
					String display;
					for(String enumValues : prop.getEnumValue().split(";")){
						value = enumValues.split("=")[0];
						display = enumValues.split("=")[1];
						res.append("<c:when test=\"").append("${tempRecord.").append(prop.getPropName()).append("==").append(value).append("}\">");
							res.append(display);
						res.append("</c:when>");
					}
					res.append("</c:choose>");
				}else{
					res.append("${tempRecord.").append(prop.getPropName()).append("}");
				}
				res.append("</td>").append("\r\n");
			}
		}
		res.append("<td  width=\"10%\">").append("<a href=\"").append(this.getUpdateUIUrl()).append("?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}").append("\">修改</a>&nbsp;|&nbsp;").append("<a href=\"").append(this.getDeleteUrl()).append("?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}").append("\" onclick=\"return window.confirm('确认要删除该条记录吗?');\">删除</a>").append("</td>").append("\r\n");
		bodyTds=res.toString();
		return bodyTds;
	}
	public String getBodyTds2() {
		if(bodyTds!=null){
			return bodyTds;
		}
		StringBuffer res = new StringBuffer();
		res.append("<!--<td align=\"center\"><input name=\"").append(entity.getIdColumnName()).append("s\" type=\"checkbox\" value=\"${").append(entity.getLowerClassName()).append(".").append(entity.getIdColumnName()).append("}\"/></td>-->");
		String tempBeanName = "temp"+entity.getLowerClassName();
		if(entity.getIdGenerateType() == Entity.ID_GENERATE_TYPE_AUTO_INCREMENT){
			res.append("<td>${").append(tempBeanName).append(".id}</td>").append("\r\n");
		}
		for(Property prop:entity.getProperties()){
			if(prop.getDisplay()==0){
				continue;
			}
			if(prop.getDataType()==Property.DATATYPE_DICTIONARY){
				res.append("<td>").append("${").append(tempBeanName).append(".").append(prop.getPropName()).append(".name}").append("</td>").append("\r\n");
			}else if(prop.getDataType()==Property.DATATYPE_COMPLEX){
				res.append("<td>").append("${").append(tempBeanName).append(".").append(prop.getPropName());
				if(prop.getRelationType() != Property.RELATIONTYPE_MANY_TO_MANY && prop.getRelationType() != Property.RELATIONTYPE_ONE_TO_MANY){
					if(prop.getValuePath()!=null && !prop.getValuePath().trim().equals("")){
						res.append(".").append(prop.getValuePath());
					}
				}
				res.append("}").append("</td>").append("\r\n");
			}else if(prop.getDataType()==Property.DATATYPE_DATE){
				res.append("<td>").append("<fmt:formatDate value=\"").append("${").append(tempBeanName).append(".").append(prop.getPropName());
				res.append("}\" pattern=\"").append(prop.getTimeFormat()).append("\"/>").append("</td>").append("\r\n");
			}else{
				res.append("<td>");
				if(prop.getDataType()==Property.DATATYPE_STRING && prop.getBriefLength() != 0){
					res.append("<xooxle:contentShower length=\"").append(prop.getBriefLength()).append("\" content=\"${").append(tempBeanName).append(".").append(prop.getPropName()).append("}\"></xooxle:contentShower>");
				}else if(prop.getDataType()==Property.DATATYPE_ENUM){
					res.append("${").append(prop.getComplexEntity().getLowerClassName()).append("Map[").append(tempBeanName).append(".").append(prop.getPropName()).append("]}");
				}else{
					res.append("${").append(tempBeanName).append(".").append(prop.getPropName()).append("}");
				}
				res.append("</td>").append("\r\n");
			}
		}
		//res.append("<td  width=\"10%\">").append("<a href=\"").append(this.getUpdateUIUrl()).append("?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}").append("\">修改</a>&nbsp;|&nbsp;").append("<a href=\"").append(this.getDeleteUrl()).append("?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}").append("\" onclick=\"return window.confirm('确认要删除该条记录吗?');\">删除</a>").append("</td>").append("\r\n");
		bodyTds=res.toString();
		return bodyTds;
	}
	@Deprecated
	public String getAddInputTrs() {
		if(addInputTrs!=null){
			return addInputTrs;
		}
		StringBuffer res = new StringBuffer();
		int count = 0;
		boolean newLine=true;
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(!newLine && (prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1)){//没有从新行开始，但又要求从新的一行开始
				res.append("</tr><tr>");//开始新的一行
			}
			res.append("<td width=\"10%\" align=\"right\">");
			res.append(prop.getName()).append("：");
			res.append("</td>");
			res.append("<td width=\"40%\"");
			
			if(prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1){//占整行
				res.append(" colspan=\"3\" ");
			}
			res.append(">");
			
			res.append(makePropAddInput(prop));
			res.append("</td>");
			count++;
			if((count%2==0 && count<entity.getProperties().size()) ||((prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1))){
				res.append("</tr><tr>");
				newLine = true; 
			}else{
				newLine = false;
			}
		}
		res.append("</tr>");
		addInputTrs = res.toString();
		return addInputTrs;
	}
	public String getAddInputTrs2() {
		if(addInputTrs!=null){
			return addInputTrs;
		}
		StringBuffer res = new StringBuffer();
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1){//占整行
				res.append("<dl class=\"nowrap\"><dt>");
				res.append(prop.getName()).append("：</dt><dd>");
				res.append(makePropAddInput(prop));
				res.append("</dd></dl>");
			}else{
				res.append("<p><label>");
				res.append(prop.getName()).append("：</label>");
				res.append(makePropAddInput(prop));
				res.append("</p>");
			}
		}
		addInputTrs = res.toString();
		return addInputTrs;
	}
	
	@Deprecated
	public String getUpdateInputTrs() {
		if(updateInputTrs!=null){
			return updateInputTrs;
		}
		StringBuffer res = new StringBuffer();
		res.append("<tr>");
		int count = 0;
		boolean newLine=true;
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(!newLine && (prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1)){//没有从新行开始，但又要求从新的一行开始
				res.append("</tr><tr>");//开始新的一行
			}
			res.append("<td width=\"10%\" align=\"right\">");
			res.append(prop.getName()).append("：");
			res.append("</td>");
			res.append("<td width=\"40%\"");
			if(prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1){//占整行
				res.append(" colspan=\"3\" ");
			}
			res.append(">");
			res.append(makePropUpdateInput(prop));
			res.append("</td>");
			count++;
			if((count%2==0 && count<entity.getProperties().size()) ||((prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1))){
				res.append("</tr><tr>");
				newLine = true; 
			}else{
				newLine = false;
			}
		}
		res.append("</tr>");
		updateInputTrs = res.toString();
		return updateInputTrs;
	}
	public String getUpdateInputTrs2() {
		if(updateInputTrs!=null){
			return updateInputTrs;
		}
		StringBuffer res = new StringBuffer();
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1){//占整行
				res.append("<dl class=\"nowrap\"><dt>");
				res.append(prop.getName()).append("：</dt><dd>");
				res.append(makePropUpdateInput(prop));
				res.append("</dd></dl>");
			}else{
				res.append("<p><label>");
				res.append(prop.getName()).append("：</label>");
				res.append(makePropUpdateInput(prop));
				res.append("</p>");
			}
		}
		res.append("</tr>");
		updateInputTrs = res.toString();
		return updateInputTrs;
	}
	
	public String getDetailInputTrs() {
		if(detailInputTrs!=null){
			return detailInputTrs;
		}
		StringBuffer res = new StringBuffer();
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1){//占整行
				res.append("<dl class=\"nowrap\"><dt>");
				res.append(prop.getName()).append("：</dt><dd>");
				res.append("<c:out value=\"${").append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append("}\"/>");
				res.append("</dd></dl>");
			}else{
				res.append("<p><label>");
				res.append(prop.getName()).append("：</label>");
				res.append("<c:out value=\"${").append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append("}\"/>");
				res.append("</p>");
			}
		}
		res.append("</tr>");
		detailInputTrs = res.toString();
		return detailInputTrs;
	}
	
	
	
	@Deprecated
	public String getListInputTrs() {
		if(updateInputTrs!=null){
			return updateInputTrs;
		}
		StringBuffer res = new StringBuffer();
		res.append("<tr>");
		int count = 0;
		boolean newLine=true;
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(!newLine && (prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1)){//没有从新行开始，但又要求从新的一行开始
				res.append("</tr><tr>");//开始新的一行
			}
			res.append("<td width=\"10%\" align=\"right\">");
			res.append(prop.getName()).append("：");
			res.append("</td>");
			res.append("<td width=\"40%\"");
			if(prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1){//占整行
				res.append(" colspan=\"3\" ");
			}
			res.append(">");
			res.append(makePropListInput(prop));
			res.append("</td>");
			count++;
			if((count%2==0 && count<entity.getProperties().size()) ||((prop.getIsTotalRow()!=null && prop.getIsTotalRow()==1))){
				res.append("</tr><tr>");
				newLine = true; 
			}else{
				newLine = false;
			}
		}
		updateInputTrs = res.toString();
		return updateInputTrs;
	}
	public String getListInputTrs2() {
		if(updateInputTrs!=null){
			return updateInputTrs;
		}
		StringBuffer res = new StringBuffer();
		for(Property prop : entity.getProperties()){
			if(prop.getOnlyRelationship()!=null && prop.getOnlyRelationship()==1){
				continue;
			}
			if(prop.getForQuery()!=null && prop.getForQuery()==0){
				continue;
			}
			res.append("<li><label>");
			res.append(prop.getName()).append("：");
			res.append("</label>");
			res.append(makePropListInput(prop));
			res.append("</li>");
			
		}
		updateInputTrs = res.toString();
		return updateInputTrs;
	}
	
	private String makePropListInput(Property prop){
		return makePropInput2(prop,2);
	}
	
	private String makePropUpdateInput(Property prop){
		return makePropInput2(prop,1);
	}
	
	private String makePropAddInput(Property prop){
		return makePropInput2(prop,0);
	}
	
	
	/**
	 * @param prop
	 * @param type 0 for add, 1 for update, 2 for list
	 * @return
	 */
	@Deprecated
	private String makePropInput(Property prop,int type){
		if(type==2 && prop.getForQuery()!=null && prop.getForQuery()==0){
			return "";
		}
		StringBuffer res = new StringBuffer();
		switch(prop.getDataType().intValue()){
		case Property.DATATYPE_INTEGER:
			//整数
		case Property.DATATYPE_FLOAT:
			//小数
			res.append("<input name=\"")
			.append(this.getBeanName()).append(".").append(prop.getPropName())
			.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
			.append("\" type=\"text\" size=\"15\" class=\"InputStyle\" ");
			if(type==1 || type==2){//修改
				res.append(" value=\"${")
				.append(this.getBeanName()).append(".").append(prop.getPropName()).append("}\" ");
			}
			if(type!=2){
				if(prop.getCanNull()==null){//不能为空
					res.append("vl=\"validate[required");
				}else{
					res.append("vl=\"validate[optional");
				}
				if(prop.getDataType()==Property.DATATYPE_INTEGER){
					res.append(",custom[integer]]\"/>");
				}else{
					res.append(",custom[number]]\"/>");
				}
			}else{
				res.append("/>");
			}
			
			break;
		case Property.DATATYPE_STRING:
			//字符串
			if(prop.getIsTextArea()!=null && prop.getIsTextArea()==1){//文本域
				res.append("<textarea name=\"")
				.append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" cols=\"").append(prop.getCol()).append("\" rows=\"").append(prop.getRow()).append("\" class=\"TextareaStyle\" ");
				
				if(type!=2){
					if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
						res.append("vl=\"validate[required,maxSize[");
					}else{
						res.append("vl=\"validate[optional,maxSize[");
					}
					res.append(prop.getLength()).append("]]\">");
				}else{
					res.append("/>");
				}
				if(type==1 || type==2){
					res.append("${")
					.append(this.getBeanName()).append(".").append(prop.getPropName()).append("}");
				}
				res.append("</textarea>");
			}else{//文本框
				res.append("<input name=\"")
				.append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" type=\"text\" size=\"15\" class=\"InputStyle\" ");
				if(type==1 || type==2){
					res.append(" value=\"${")
					.append(this.getBeanName()).append(".").append(prop.getPropName()).append("}\" ");
				}
				if(type!=2){
					if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
						res.append("vl=\"validate[required,maxSize[");
					}else{
						res.append("vl=\"validate[optional,maxSize[");
					}
					res.append(prop.getLength()).append("]]\"/>");
				}else{
					res.append("/>");
				}
			}
			break;
		case Property.DATATYPE_DATE:
			//日期
			if(type==2){//列表页面
				//开始
				res.append("<input name=\"")
				.append(prop.getPropName()).append("Start")
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" type=\"text\" size=\"15\" class=\"WDate\" onclick=\"WdatePicker({dateFmt:'").append(prop.getTimeFormat()).append("'})\" style=\"width:180px;\"   readonly=\"readonly\"")
				.append(" value=\"<fmt:formatDate pattern=\"").append(prop.getTimeFormat()).append("\" value=\"${")
				.append(prop.getPropName()).append("Start").append("}\"/>\" />");
				//截止
				res.append(" 至 ");
				res.append("<input name=\"")
				.append(prop.getPropName()).append("End")
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" type=\"text\" size=\"15\" class=\"WDate\" onclick=\"WdatePicker({dateFmt:'").append(prop.getTimeFormat()).append("'})\" style=\"width:180px;\"   readonly=\"readonly\"")
				.append(" value=\"<fmt:formatDate pattern=\"").append(prop.getTimeFormat()).append("\" value=\"${")
				.append(prop.getPropName()).append("End").append("}\"/>\" />");
				
			}else{//添加或修改
				res.append("<input name=\"")
				.append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" type=\"text\" size=\"15\" class=\"WDate\" onclick=\"WdatePicker({dateFmt:'").append(prop.getTimeFormat()).append("'})\" style=\"width:180px;\"   readonly=\"readonly\"");
				
				if(type==1){//修改
					res.append(" value=\"<fmt:formatDate pattern=\"").append(prop.getTimeFormat()).append("\" value=\"${")
					.append(this.getBeanName()).append(".").append(prop.getPropName()).append("}\"/>\" ");
				}
				
				if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
					res.append("vl=\"validate[required]\"/>");
				}else{
					res.append("vl=\"validate[optional]\"/>");
				}
			}
			break;
		case Property.DATATYPE_DICTIONARY:
			//数据字典
		case Property.DATATYPE_COMPLEX:
			//复杂类型
			res.append("<select name=\"").append(prop.getPropName()).append("Id\" id=\"").append(prop.getPropName()).append("Id\" class=\"SelectStyle\"");
			if(type!=2){
				if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
					res.append("vl=\"validate[required]\"/>");
				}else{
					res.append("vl=\"validate[optional]\"/>");
				}
			}else{
				res.append("/>");
			}
			res.append("<option value=\"\">请选择</option>");
			res.append("<c:forEach items=\"${").append(prop.getPropName()).append("List}\" var=\"vari\"><option value=\"${vari.id}\" ");
			if(type==1){
				res.append("${").append(prop.getEntity().getEntityBeanName()).append(".").append(prop.getPropName()).append(".id==vari.id?'selected':''}");
			}else if(type==2){
				res.append("${").append(prop.getPropName()).append("Id==vari.id?'selected':''}");
			}
			if(prop.getDataType()==Property.DATATYPE_COMPLEX){//复杂类型
				res.append(">${vari");
				if(prop.getValuePath()!=null){
					res.append(".").append(prop.getValuePath());
				}
				res.append("}</option></c:forEach>");
			}else{
				res.append(">${vari.name}</option></c:forEach>");
			}
			res.append("</select>");
			break;
		case Property.DATATYPE_ENUM:
			//枚举类型
			res.append("<select name=\"").append(this.getBeanName()).append(".").append(prop.getPropName()).append("\" id=\"").append(prop.getPropName()).append("\" class=\"SelectStyle\"");
			if(type!=2){
				if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
					res.append("vl=\"validate[required]\"/>");
				}else{
					res.append("vl=\"validate[optional]\"/>");
				}
			}else{
				res.append("/>");
			}
			res.append(makeEnumSelectOption(prop.getPropName(),prop.getEnumValue()));	
			res.append("</select>");
		}
		if(type!=2 && (prop.getCanNull()==null || prop.getCanNull()==0)){
			res.append("&nbsp;<font color=\"red\">*</font>");
		}
		return res.toString();
	}
	/**
	 * @param prop
	 * @param type 0 for add, 1 for update, 2 for list
	 * @return
	 */
	private String makePropInput2(Property prop,int type){
		if(type==2 && prop.getForQuery()!=null && prop.getForQuery()==0){
			return "";
		}
		StringBuffer res = new StringBuffer();
		switch(prop.getDataType().intValue()){
		case Property.DATATYPE_INTEGER:
			//整数
		case Property.DATATYPE_FLOAT:
			//小数
			res.append("<form:input path=\"")
			.append(prop.getPropName())
			.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
			.append("\" type=\"text\" class=\"textInput ");
			if(type!=2){
				if(prop.getCanNull()==null || prop.getCanNull()==0 ){//不能为空
					res.append("required ");
				}
				if(prop.getDataType()==Property.DATATYPE_INTEGER){
					res.append("digits\"/>");
				}else{
					res.append("number\"/>");
				}
			}else{
				res.append("\" size=\"12\"/>");
			}
			break;
		case Property.DATATYPE_STRING:
			//字符串
			if(prop.getIsTextArea()!=null && prop.getIsTextArea()==1){//文本域
				res.append("<form:textarea path=\"")
				.append(prop.getPropName())
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" cols=\"").append(prop.getCol()).append("\" rows=\"").append(prop.getRow()).append("\" ");
				if(type!=2){
					if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
						res.append("cssClass=\"required\" ");
					}
					if(0==prop.getIsTextStringType()){
						res.append("maxlength=\"").append(prop.getLength()).append("\"");
					}
					res.append("/>");
				}else{
					res.append(" size=\"12\"/>");
				}
			}else{//文本框
				res.append("<form:input path=\"")
				.append(prop.getPropName())
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" class=\"textInput ");
				if(type!=2){
					if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
						res.append("required\" ");
					}else{
						res.delete(res.length()-1, res.length());
						res.append("\" ");
					}
					res.append("maxlength=\"").append(prop.getLength()).append("\"/>");
				}else{
					res.delete(res.length()-1, res.length());
					res.append("\" size=\"12\" />");
				}
			}
			break;
		case Property.DATATYPE_DATE:
			/*
			 * <input name="createTime" id="contact.createTime"
					onclick="WdatePicker({dateFmt='${applicationScope.CN_HOUR_MIN}'});"
					value="<fmt:formatDate pattern="${applicationScope.CN_HOUR_MIN}" value="${contact.createTime}"/>"
					class="WDate textInput valid required" />
			 */
			//日期
			if(type==2){//列表页面
				//开始
				res.append("<input name=\"")
				.append(prop.getPropName()).append("Start")
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName()).append("Start")
				.append("\" onclick=\"WdatePicker({dateFmt:'${applicationScope.").append(DateUtil.patterns.get(prop.getTimeFormat())).append("}'});\" ")
				.append(" value=\"<fmt:formatDate pattern=\"${applicationScope.").append(DateUtil.patterns.get(prop.getTimeFormat())).append("}\" value=\"${")
				.append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append("Start").append("}\"/>\" class=\"date_not_required textInput valid\" size=\"12\"/>");
				
				//截止
				res.append("至 ");
				res.append("<input name=\"")
				.append(prop.getPropName()).append("End")
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName()).append("End")
				.append("\" onclick=\"WdatePicker({dateFmt:'${applicationScope.").append(DateUtil.patterns.get(prop.getTimeFormat())).append("}'});\" ")
				.append(" value=\"<fmt:formatDate pattern=\"${applicationScope.").append(DateUtil.patterns.get(prop.getTimeFormat())).append("}\" value=\"${")
				.append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append("End").append("}\"/>\" class=\"date_not_required textInput valid\" size=\"12\"/>");
			}else{//添加或修改
				res.append("<input name=\"")
				.append(prop.getPropName())
				.append("\" id=\"").append(this.getBeanName()).append(".").append(prop.getPropName())
				.append("\" onclick=\"WdatePicker({dateFmt:'${applicationScope.").append(DateUtil.patterns.get(prop.getTimeFormat())).append("}'});\" ")
				.append(" value=\"<fmt:formatDate pattern=\"${applicationScope.").append(DateUtil.patterns.get(prop.getTimeFormat())).append("}\" value=\"${")
				.append(prop.getEntity().getLowerClassName()).append(".").append(prop.getPropName()).append("}\"/>\" class=\"date_");
				if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
					res.append("required textInput valid");
					res.append(" required");
				}else{
					res.append("not_required textInput valid");
				}
				res.append("\"/>");
			}
			break;
		case Property.DATATYPE_DICTIONARY:
			//数据字典
		case Property.DATATYPE_COMPLEX:
			//复杂类型
			//.append(".").append(prop.getComplexEntity().getIdColumnName())
			res.append("<form:select id=\"").append(prop.getPropName()).append("\" path=\"")
			.append(prop.getPropName())//.append(".").append(prop.getComplexEntity().getIdColumnName())
			.append("\" cssClass=\"comboxed ");
			if(type!=2){
				if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
					res.append("required ");
				}else{
					res.delete(res.length()-1, res.length());
				}
			}
			res.append("\">");
			if(type==2){
				res.append("<option value=\"\">-全部-</option>");
			}else{
				res.append("<option value=\"\">请选择</option>");
			}
			res.append("<form:options  items=\"${").append(prop.getComplexEntity().getLowerClassName()).append("List}\" itemValue=\"").append(prop.getComplexEntity().getIdColumnName())
			.append("\" itemLabel=\"").append(prop.getValuePath()).append("\" cssClass=\"comboxed\"/>");
			res.append("</form:select>");
			break;
		case Property.DATATYPE_ENUM://枚举类型
			if(type==2){
				res.append("<form:select id=\"").append(prop.getPropName()).append(".").append(prop.getComplexEntity().getIdColumnName()).append("\" path=\"")
				.append(prop.getPropName())
				.append("\">");
				if(type==2){
					res.append("<option value=\"\">-全部-</option>");
				}else{
					res.append("<option value=\"\">请选择</option>");
				}
				res.append("<form:options  items=\"${").append(prop.getComplexEntity().getLowerClassName()).append("Map}\"/>");
				res.append("</form:select>");
			}else{
				res.append("<form:select path=\"").append(prop.getPropName())
				.append("\" cssClass=\"");
				if(prop.getCanNull()==null || prop.getCanNull()==0){//不能为空
					res.append("required ");
				}
				res.append("comboxed\" items=\"${")
				.append(prop.getComplexEntity().getLowerClassName()).append("Map}\"></form:select>");
			}
			break;
			default:
				throw new RuntimeException("property:"+prop.getName()+" in "+prop.getEntity().getName()+" has a unsupport datatype:"+prop.getDataType());
		}
		if(type!=2 && (prop.getCanNull()==null || prop.getCanNull()==0) && (prop.getDataType()==Property.DATATYPE_COMPLEX || prop.getDataType()==Property.DATATYPE_ENUM)){
			res.append("&nbsp;<font color=\"red\">*</font>");
		}
		return res.toString();
	}
	
	private String makeEnumSelectOption(String propName,String enumValue){
		StringBuffer res = new StringBuffer();
		String key;
		String value;
		for(String singlEnumValue : enumValue.split(";")){
			key = singlEnumValue.split("=")[0];
			value = singlEnumValue.split("=")[1];
			res.append("<option value=\"").append(key).append("\" ${")
			.append(this.getBeanName()).append(".").append(propName)
			.append("==").append(key).append("?'selected':''}>").append(value).append("</option>");
		}
		return res.toString();
	}
	
	public String getEmptyColspan(){
		if(emptyColspan==null){
			emptyColspan = (this.entity.getProperties().size()+2)+"";
		}
		return this.emptyColspan;
	}
	
	/**
	 * 查询的显示/隐藏切换按钮
	 * @return
	 */
	public String getQueryMenu(){
		if(this.entity.getQueryAble()==0){
			return "";
		}
		return "<td class=\"ui-icon ui-icon-carat-2-n-s\"></td><td><span><a href=\"javascript:void(0);\" onclick=\"jQuery('#causeTab').toggle();\">查询条件</a> </span></td> <td>&nbsp;</td>";
	}
	
	/**
	 * 批量导出菜单
	 * @return
	 */
	public String getExportMenu(){
		if(this.entity.getExportable()==0){
			return "";
		}
		return "<td>&nbsp;</td><td class=\"ui-icon ui-icon-arrowstop-1-s\"></td><td><span style=\"cursor: hand\" onclick=\"exportExcle();\">导出Excel</span></td>";
	}
	
	/**
	 * 批量导入菜单
	 * @return
	 */
	public String getImportMenu(){
		if(this.entity.getImportable()==0){
			return "";
		}
		return "<td>&nbsp;</td><td class=\"ui-icon ui-icon-arrowstop-1-n\"></td><td><a href=\""+this.getBeanName()+"_importExcelUI.action\">导入Excel </a></td>";
	}
	
	/**
	 * 获取列表中的查询table
	 * @return
	 */
	@Deprecated
	public String getQueryTable(){
		if(this.entity.getQueryAble()==0){
			return "";
		}
		if(queryTable == null){
			StringBuffer res = new StringBuffer();
			res.append("<table width=\"100%\" border=\"0\" id=\"causeTab\" style=\"display:none;\">");
			res.append(this.getListInputTrs());
			res.append("</tr>");
			res.append("<tr>");
			res.append("<td colspan=\"4\" align=\"center\">");
			res.append("<input type=\"submit\" name=\"button\" id=\"button\" value=\"查 询\"class=\"ButtonStyle\"/><input type=\"reset\" name=\"button2\" id=\"button2\" value=\"重 置\" class=\"ButtonStyle\" />");
			res.append("</td>");
			res.append("</tr>");
			res.append("</table>");
			queryTable = res.toString();
		}
		return queryTable;
	}
	/**
	 * 获取列表中的查询table
	 * @return
	 */
	public String getQueryTable2(){
		if(this.entity.getQueryAble()==0){
			return "";
		}
		if(queryTable == null){
			StringBuffer res = new StringBuffer();
			res.append(this.getListInputTrs2());
			queryTable = res.toString();
		}
		return queryTable;
	}
	/**
	 * 导入时页面中选择的重复验证的字段的checkbox<br/>
	 * &lt;input type="checkbox" name="optionalIds" value="1" id="chk_1"/&gt;&lt;label for="chk_1"&gt;站台名称&lt;/label&gt;<br/>
	   &lt;input type="checkbox" name="optionalIds" value="2" id="chk_2"/&gt;&lt;label for="chk_2"&gt;地址&lt;/label&gt;<br/>
	   &lt;input type="checkbox" name="optionalIds" value="3" id="chk_3"/&gt;&lt;label for="chk_3"&gt;邮编&lt;/label&gt;<br/>
	   &lt;input type="checkbox" name="optionalIds" value="4" id="chk_4"/&gt;&lt;label for="chk_4"&gt;发票抬头&lt;/label&gt;<br/>
	 * @return
	 */
	public String getImpCheckRepeatChks(){
		if(impCheckRepeatChks==null){
			StringBuffer res = new StringBuffer();
			int i = 1;
			for(Property prop : entity.getProperties()){
				res.append("<input type=\"checkbox\" name=\"optionalIds\" value=\"")
				.append(i).append("\" id=\"chk_")
				.append(i).append("\"/><label for=\"chk_")
				.append(i).append("\">")
				.append(prop.getName())
				.append("</label>\r\n");
				i++;
			}
			impCheckRepeatChks = res.toString();
		}
		return impCheckRepeatChks;
	}
	
	/**
	 * 获取实体对应的实际意义名称
	 * @return
	 */
	public String getBeanRealyName(){
		
		return entity.getName();
	}
}
