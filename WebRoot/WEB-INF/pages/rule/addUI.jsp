<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加映射规则</title>
		<script type="text/javascript"
			src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body class="ui-widget-content">

		<table width="100%">
			<tr style="border: 1px solid red">
				<td class="ui-icon ui-icon-circle-triangle-e" width="16px">
					&nbsp;
				</td>
				<td>
					添加映射规则
				</td>
			</tr>
		</table>
		<form METHOD="POST" NAME="fmOperator"
			style="margin-top: 15; margin-bottom: 0" id="formID"
			action="${basePath }rule_add.action">
			<input type="hidden" name="pageSize" value="${pageSize}">
			<input type="hidden" name="pageNum" value="${pageNum}">
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table align="center" class="mainForm" style="width: 100%;">
						<tr>
							<td width="10%" align="right">
								输入模板：
							</td>
							<td>
								<select name="templateId" id="templateId" class="SelectStyle"
									vl="validate[required]" />
								<option value="">
									请选择
								</option>
								<c:forEach items="${templateList}" var="vari">
									<option value="${vari.id}">
										${vari.name}
									</option>
								</c:forEach>
								</select>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="10%" align="right">
								输出位置：
							</td>
							<td >
								<input name="rule.outputDir" id="rule.outputDir" type="text"
									size="15" class="InputStyle"
									vl="validate[required,maxSize[255]]" style="width:60%"/>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="10%" align="right">
								追加：
							</td>
							<td >
								<select name="append">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td width="10%" align="right">
								输出文件名称：
							</td>
							<td >
								<input name="rule.outputFileName" id="rule.outputFileName" type="text"
									size="15" class="InputStyle"
									vl="validate[required,maxSize[255]]" style="width:60%"/>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="10%" align="right">
								查询条件：
							</td>
							<td >
								<input name="rule.cause" id="rule.cause" type="text"
									size="15" class="InputStyle"
									vl="validate[optional,maxSize[255]]" style="width:60%"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="保 存" class="ButtonStyle" />
				<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle">
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					onClick="history.back();">
			</div>
		</form>
	</body>
</html>

