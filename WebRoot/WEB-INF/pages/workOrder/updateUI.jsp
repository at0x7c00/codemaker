<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改工单</title>
	</head>
	<body>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改工单</td>
	  </tr>
	</table>
		<form id="formID" METHOD="POST" NAME="fmOperator" style="margin-top: 5; margin-bottom: 0" action="${basePath }workOrder_update.action">
		   <input type="hidden" name="id" value="${workOrder.id}"> 
		   	<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr>
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
