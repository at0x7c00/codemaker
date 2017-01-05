<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改学生</title>
	</head>
	<body>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改学生</td>
	  </tr>
	</table>
		<form id="formID" METHOD="POST" NAME="fmOperator" style="margin-top: 5; margin-bottom: 0" action="${basePath }student_update.action">
		   <input type="hidden" name="id" value="${student.id}"> 
		   	<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">姓名：</td><td width="40%"><input name="student.name" id="student.name" type="text" size="15" class="InputStyle"  value="${student.name}" vl="validate[optional,maxSize[255]]"/></td><td width="10%" align="right">所属教师：</td><td width="40%"><select name="teacherId" id="teacherId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${teacherList}" var="vari"><option value="${vari.id}" ${student.teacher.id==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td>
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
