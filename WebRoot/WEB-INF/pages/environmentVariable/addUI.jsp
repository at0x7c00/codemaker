<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加环境变量</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加环境变量</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 15; margin-bottom: 0" id="formID"
		 action="${basePath }environmentVariable_add.action">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">变量名称：</td><td width="40%" colspan="3" ><input name="environmentVariable.varName" id="environmentVariable.varName" type="text" size="15" class="InputStyle" vl="validate[required,maxSize[255]]"/>&nbsp;<font color="red">*</font></td></tr><tr><td width="10%" align="right">变量值：</td><td width="40%" colspan="3" ><input name="environmentVariable.value" id="environmentVariable.value" type="text" size="15" class="InputStyle" vl="validate[required,maxSize[255]]"/>&nbsp;<font color="red">*</font></td></tr><tr><td width="10%" align="right">类型：</td><td width="40%"><select name="environmentVariable.dataType" id="dataType" class="SelectStyle"vl="validate[required]"/><option value="1" ${environmentVariable.dataType==1?'selected':''}>字符串</option><option value="2" ${environmentVariable.dataType==2?'selected':''}>整数</option><option value="3" ${environmentVariable.dataType==3?'selected':''}>小数</option><option value="4" ${environmentVariable.dataType==4?'selected':''}>日期</option></select>&nbsp;<font color="red">*</font></td></tr>
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

