<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加角色</title>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加角色</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 30; margin-bottom: 0" id="formID"
		 action="${basePath }role_add.action">
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:50%;">
							<tr>
								<td width="16%" class="TextStyle" align="right" 
									height="16" valign="middle">
									角色名称：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
										<input type="text" id="roleName" name="entity.name" maxlength="100" 
										class="InputStyle" size="50" value="${role.name }" vl="validate[required]">
										 <font color="red">*&nbsp;<span id="msg1"></span></font>
								</td>
							</tr>
			 </table>
			 </div>
			 </div>
					<div id="InputDetailBar">
					<input type="submit" name="btnSave" value="保 存" class="ButtonStyle" />
					<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle">
					<input type="button" name="btnBack" value="返 回" class="ButtonStyle" 	onClick="history.back();">
				</div>
		</form>
	</body>
</html>

