<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改教师</title>
	</head>
	<body>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改教师</td>
	  </tr>
	</table>
		<form id="formID" METHOD="POST" NAME="fmOperator" style="margin-top: 5; margin-bottom: 0" action="${basePath }teacher_update.action">
		   <input type="hidden" name="id" value="${teacher.id}"> 
		   	<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">姓名：</td><td width="40%"><input name="teacher.name" id="teacher.name" type="text" size="15" class="InputStyle"  value="${teacher.name}" vl="validate[optional,maxSize[32]]"/></td><td width="10%" align="right">年龄：</td><td width="40%"><input name="teacher.age" id="teacher.age" type="text" size="15" class="InputStyle"  value="${teacher.age}" vl="validate[optional,custom[integer]]"/></td></tr><tr><td width="10%" align="right">性别：</td><td width="40%"><select name="sexId" id="sexId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${sexList}" var="vari"><option value="${vari.id}" ${teacher.sex.id==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td></tr><tr><td width="10%" align="right">备注：</td><td width="40%" colspan="3" ><textarea name="teacher.remark" id="teacher.remark" col="5" row="5" class="TextareaStyle" vl="validate[optional,maxSize[255]]">${teacher.remark}</textarea></td></tr><tr>
			 </table>
			 </div>
			 </div>
					<div id="InputDetailBar">
					<input type="submit" name="btnSave" value="保 存" class="ButtonStyle"
						 onClick="save()" />
					<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle"
						>
					<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
						 onClick="goBack()">
				</div>
		</form>
	</body>
</html>
