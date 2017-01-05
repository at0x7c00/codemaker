<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加包</title>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red;">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加包</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" id="formID" style="margin-top: 15; margin-bottom: 0" action="${basePath}package_add.action" >
			<input type="hidden" value="${id }" name="id"/>
			 <div class="ItemBlockBorder">
				<div class="ItemBlock">
				<table width="100%">
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						所属项目：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="projectId" class="SelectStyle" id="projectId" vl="validate[required]">
							<option value="">-请选择-</option>
							<c:forEach items="${projectList}" var="project">
								<option value="${project.id}" ${PROJECT.id==project.id?'selected':''}>${project.name }</option>
							</c:forEach>
						</select>
						&nbsp;
						<font style="color:red">*</font>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						名称：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="pack.name" maxlength="100" class="InputStyle"
							vl="validate[required]" id="title"> <font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						上级包：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<select name="parentId" class="SelectStyle">
							<option value="">-请选择-</option>
							<c:forEach items="${parentPacks}" var="parent">
								<option value="${parent.id}">${parent.name }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="TextStyle" align="right" height="16"
						valign="middle">
						备&nbsp; &nbsp; 注：
					</td>
					<td class="text" align="left" height="16"
						valign="middle">
						<textarea rows="5" cols="80"  class="TextareaStyle" style="width:70%" name="pack.remark"></textarea>
					</td>
				</tr>
			</table>
			</div>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="添 加" class="ButtonStyle" />
				<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle" />
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					onClick="goBack();" />
			</div>
		</form>
	</body>
</html>

