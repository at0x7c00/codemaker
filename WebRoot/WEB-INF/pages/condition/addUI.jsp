<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加条件</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加条件</td>
	  </tr>
	</table>
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 15; margin-bottom: 0" id="formID"
		 action="${basePath }condition_add.action">
		<input type="hidden" name="pageSize" value="${pageSize}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">字段：</td><td width="40%"><select name="propertyId" id="propertyId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${propertyList}" var="vari"><option value="${vari.id}" >${vari.name}</option></c:forEach></select></td><td width="10%" align="right">参数值：</td><td width="40%"><input name="condition.paramValue" id="condition.paramValue" type="text" size="15" class="InputStyle" vl="validate[optional,maxSize[255]]"/></td></tr><tr><td width="10%" align="right">比较方式：</td><td width="40%"><select name="condition.compareType" id="compareType" class="SelectStyle"vl="validate[optional]"/><option value="0" ${condition.compareType==0?'selected':''}>全部</option><option value="1" ${condition.compareType==1?'selected':''}>等于</option><option value="2" ${condition.compareType==2?'selected':''}>不等于</option><option value="3" ${condition.compareType==3?'selected':''}>大于且小于</option><option value="4" ${condition.compareType==4?'selected':''}>取某些值</option><option value="5" ${condition.compareType==5?'selected':''}>大于</option><option value="6" ${condition.compareType==6?'selected':''}>大于等于</option><option value="7" ${condition.compareType==7?'selected':''}>小于</option><option value="8" ${condition.compareType==8?'selected':''}>小于等于</option><option value="9" ${condition.compareType==9?'selected':''}>为空</option><option value="10" ${condition.compareType==10?'selected':''}>不为空</option></select></td><td width="10%" align="right">连接词：</td><td width="40%"><select name="condition.logicValue" id="logicValue" class="SelectStyle"vl="validate[optional]"/><option value="0" ${condition.logicValue==0?'selected':''}>并且</option><option value="1" ${condition.logicValue==1?'selected':''}>或者</option></select></td></tr><tr><td width="10%" align="right">所属连线：</td><td width="40%"><select name="transitionId" id="transitionId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${transitionList}" var="vari"><option value="${vari.id}" >${vari.name}</option></c:forEach></select></td></tr>
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

