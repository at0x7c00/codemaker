<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加版本记录</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加版本记录</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 15; margin-bottom: 0" id="formID"
		 action="${basePath }versionRecord_add.action">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">版本号：</td><td width="40%"><input name="versionRecord.name" id="versionRecord.name" type="text" size="15" class="InputStyle" vl="validate[required,maxSize[30]]"/>&nbsp;<font color="red">*</font></td><td width="10%" align="right">日期：</td><td width="40%"><input name="versionRecord.addTime" id="versionRecord.addTime" type="text" size="15" class="WDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:180px;"   readonly="readonly"vl="validate[required]"/>&nbsp;<font color="red">*</font></td></tr><tr><td width="10%" align="right">版本备注：</td><td width="40%" colspan="3" ><textarea name="versionRecord.remark" id="versionRecord.remark" cols="100" rows="20" class="TextareaStyle" vl="validate[required,maxSize[255]]"></textarea>&nbsp;<font color="red">*</font></td></tr><tr></tr>
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

