<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加页面字段</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加页面字段</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 15; margin-bottom: 0" id="formID"
		 action="${basePath }pageProperty_add.action">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">所属页面：</td><td width="40%"><select name="pageId" id="pageId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${pageList}" var="vari"><option value="${vari.id}" >${vari.title}</option></c:forEach></select></td><td width="10%" align="right">显示：</td><td width="40%"><select name="pageProperty.display" id="display" class="SelectStyle"vl="validate[optional]"/><option value="1" ${pageProperty.display==1?'selected':''}>是</option><option value="0" ${pageProperty.display==0?'selected':''}>否</option></select></td></tr><tr><td width="10%" align="right">可编辑：</td><td width="40%"><select name="pageProperty.editable" id="editable" class="SelectStyle"vl="validate[optional]"/><option value="1" ${pageProperty.editable==1?'selected':''}>是</option><option value="0" ${pageProperty.editable==0?'selected':''}>否</option></select></td><td width="10%" align="right">排序号：</td><td width="40%"><input name="pageProperty.sortValue" id="pageProperty.sortValue" type="text" size="15" class="InputStyle" vl="validate[optional,custom[integer]]"/></td></tr><tr><td width="10%" align="right">字段：</td><td width="40%"><select name="propertyId" id="propertyId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${propertyList}" var="vari"><option value="${vari.id}" >${vari.name}</option></c:forEach></select></td></tr>
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

