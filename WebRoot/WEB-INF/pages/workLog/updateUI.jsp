<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改工作日志</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改工作日志</td>
	  </tr>
	</table>
		<form id="formID" METHOD="POST" NAME="fmOperator" style="margin-top: 15; margin-bottom: 0" action="${basePath }workLog_update.action">
		   <input type="hidden" name="id" value="${workLog.id}"> 
		   <input type="hidden" name="pageSize" value="${pageSize}">
			<input type="hidden" name="pageNum" value="${pageNum}">
		   	<div class="ItemBlockBorder">
		<div class="ItemBlock">
			<table align="center" class="mainForm" style="width:100%;">
<tr><td width="10%" align="right">记录时间：</td><td width="40%"><input name="workLog.addTime" id="workLog.addTime" type="text" size="15" class="WDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:180px;"   readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${workLog.addTime}"/>" vl="validate[optional]"/></td></tr><tr><td width="10%" align="right">内容：</td><td width="40%" colspan="3" ><input name="workLog.content" id="workLog.content" type="text" size="15" class="InputStyle"  value="${workLog.content}" vl="validate[optional,maxSize[255]]"/></td></tr><tr><td width="10%" align="right">所属类型：</td><td width="40%"><select name="logTypeId" id="logTypeId" class="SelectStyle"vl="validate[optional]"/><option value="">请选择</option><c:forEach items="${logTypeList}" var="vari"><option value="${vari.id}" ${workLog.logType.id==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td><td width="10%" align="right">状态：</td><td width="40%"><select name="workLog.status" id="status" class="SelectStyle"vl="validate[optional]"/><option value="0" ${workLog.status==0?'selected':''}>待处理</option><option value="1" ${workLog.status==1?'selected':''}>处理中</option><option value="2" ${workLog.status==2?'selected':''}>已处理</option></select></td></tr><tr><td width="10%" align="right">开始处理时间：</td><td width="40%"><input name="workLog.processStartDate" id="workLog.processStartDate" type="text" size="15" class="WDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:180px;"   readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${workLog.processStartDate}"/>" vl="validate[optional]"/></td><td width="10%" align="right">完成处理时间：</td><td width="40%"><input name="workLog.processEndDate" id="workLog.processEndDate" type="text" size="15" class="WDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:180px;"   readonly="readonly" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${workLog.processEndDate}"/>" vl="validate[optional]"/></td></tr><tr><td width="10%" align="right">处理备注：</td><td width="40%" colspan="3" ><input name="workLog.remark" id="workLog.remark" type="text" size="15" class="InputStyle"  value="${workLog.remark}" vl="validate[optional,maxSize[255]]"/></td></tr><tr></tr>
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
