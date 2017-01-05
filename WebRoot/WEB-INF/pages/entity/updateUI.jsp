<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改实体</title>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red;">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改实体-${PROJECT.name}</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" id="formID" style="margin-top: 15; margin-bottom: 0" action="${basePath}entity_update.action" >
			<input type="hidden" value="${entity.id}" name="id"/>
			 <div class="ItemBlockBorder">
				<div class="ItemBlock">
				<table width="100%">
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						名称：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="entity.name" maxlength="100" class="InputStyle"
							vl="validate[required]" id="entity.name" value="${entity.name}"> <font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						表名称：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="entity.tableName" maxlength="100" class="InputStyle"
							vl="validate[required]" id="entity.tableName" value="${entity.tableName }"> <font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						类名称：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="entity.className" maxlength="100" class="InputStyle"
							vl="validate[required]" id="entity.className" value="${entity.className }"> <font color="red">*</font>
					</td>
				</tr>
				
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						继承于：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
							<select name="parentId">
								<option value="">-空-</option>
								<c:forEach items="${entityList}" var="_entity">
									<option value="${_entity.id}" ${entity.parent.id eq _entity.id?'selected':'xxx' }>${_entity.fullClassName}</option>
								</c:forEach>
							</select>
							<img src="${basePath}images/new.gif" alt="新功能" title="最近更新"/>
					</td>
					<td>
				</tr>
				
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						所属包：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="packageId" class="SelectStyle" id="parentId" vl="validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${parentPacks}" var="parent">
								<option value="${parent.id}" ${entity.packagee.id==parent.id?'selected':'' }>${parent.name }</option>
							</c:forEach>
						</select> <font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						类型：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.type" class="SelectStyle" id="entity.type" vl="validate[required]">
							<option value="0" ${entity.type==0?'selected':''}>普通类</option>
							<option value="1" ${entity.type==1?'selected':''}>枚举</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						枚举值：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<textarea name="entity.enumValue" rows="5" cols="50" class="TextareaStyle">${entity.enumValue }</textarea>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						是否可查询：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.queryAble" class="SelectStyle" id="entity.queryAble" vl="validate[required]">
							<option value="1" ${entity.queryAble==1?'selected':''}>是</option>
							<option value="0" ${entity.queryAble==0?'selected':''}>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						ManageKey支持：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.manageKeySupport" class="SelectStyle" id="entity.manageKeySupport" vl="validate[required]">
							<option value="1" ${entity.manageKeySupport==1?'selected':''}>是</option>
							<option value="0" ${entity.manageKeySupport==0?'selected':''}>否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						是否可导出：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.exportable" class="SelectStyle" id="entity.exportable" vl="validate[required]">
							<option value="0" ${entity.exportable==0?'selected':''}>否</option>
							<option value="1" ${entity.exportable==1?'selected':''}>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						是否可导入：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.importable" class="SelectStyle" id="entity.importable" vl="validate[required]">
							<option value="0" ${entity.importable==0?'selected':''}>否</option>
							<option value="1" ${entity.importable==1?'selected':''}>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						是否可查看：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.viewable" class="SelectStyle" id="entity.viewable" vl="validate[required]">
							<option value="0" ${entity.viewable==0?'selected':''}>否</option>
							<option value="1" ${entity.viewable==1?'selected':''}>是</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right">
						主键生成策略:
					</td>
					<td>
						<select id="entity.idGenerateType" name="entity.idGenerateType" class="SelectStyle">
							<option value="0" ${entity.idGenerateType==0?'selected':''}>自增长</option>
							<option value="1" ${entity.idGenerateType==1?'selected':''}>指定值</option>
						</select><font color="red"> *</font>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						对代码生成器不可见：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.visiablity" class="SelectStyle" id="entity.importable" vl="validate[required]">
							<option value="1" ${entity.visiablity==1?'selected':''}>否</option> 
							<option value="0" ${entity.visiablity==0?'selected':''}>是</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						生成历史记录：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="entity.historyable" class="SelectStyle" id=""entity.historyable"" vl="validate[required]">
							<option value="0" ${entity.historyable==0?'selected':''}>否</option>
							<option value="1" ${entity.historyable==1?'selected':''}>是</option>
						</select>
					</td>
				</tr>
			</table>
			</div>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="修 改" class="ButtonStyle" />
				<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle" />
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					onClick="goBack();" />
			</div>
		</form>
	</body>
</html>

