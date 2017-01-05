<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加节点</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加节点</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 15; margin-bottom: 0" id="formID"
		 action="${basePath }node_add.action">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">名称：</td><td width="40%"><input name="node.name" id="node.name" type="text" size="15" class="InputStyle" vl="validate[optional,maxSize[64]]"/></td><td width="10%" align="right">处理页面：</td><td width="40%"><select name="pageId" id="pageId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${pageList}" var="vari"><option value="${vari.id}" >${vari.title}</option></c:forEach></select></td></tr>
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

