<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加项目</title>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red;">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加项目</td>
	  </tr> 
	</table>
		<form METHOD="POST" NAME="fmOperator" id="formID" style="margin-top: 15; margin-bottom: 0" action="${basePath}project_add.action" >
			<input type="hidden" value="${id }" name="id"/>
			 <div class="ItemBlockBorder">
				<div class="ItemBlock">
				<table width="100%">
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						项目名称：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="project.name" maxlength="100" class="InputStyle"
							vl="validate[required]" id="project.name" value="${project.name}"> <font color="red">*</font>
					</td>
				</tr>
<%--
				<tr>
					<td class="TextStyle" align="right">
						支持国际化：
					</td>
					<td>
						<select name="project.useI18n">
							<option value="">-请选择-</option>
							<option value="1" ${project.useI18n eq 1 ? 'selected':''}>是</option>
							<option value="0" ${project.useI18n eq 0 ? 'selected':''}>否</option>
						</select>
					</td>
				</tr>--%>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						备注：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<textarea rows="5" cols="50" class="TextareaStyle" name="project.remark">${project.remark}</textarea>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						映射规则：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						<c:forEach items="${ruleList}" var="rule">
							<input type="checkbox" id="chk_${rule.id}" value="${rule.id}" name="ruleIds"/><label for="chk_${rule.id }">${rule.template.name}(${rule.template.group.name})<span style="color:blue;font-weight:bold;">---&gt;</span>${rule.outputDir }</label><br/>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td width="15%" class="TextStyle" align="right" height="16"	valign="middle">
						环境变量：
					</td>
					<td width="85%" class="TextStyle" align="left" height="16"
						valign="middle">
						
						<c:forEach items="${envirVars}" var="ev">
							<input type="checkbox" id="chk_ev_${ev.id}" value="${ev.id}" name="envirVarIds"/><label for="chk_ev_${ev.id }">${ev.varName}(<font style="color:green">
							<c:choose>
								<c:when test="${ev.dataType==1}">字符串</c:when>
								<c:when test="${ev.dataType==2}">整数</c:when>
								<c:when test="${ev.dataType==3}">小数</c:when>
								<c:when test="${ev.dataType==4}">日期</c:when>
							</c:choose>
							</font>
							${ev.value}
							)</label><br/>
						</c:forEach>
						
					</td>
				</tr>
			</table>
			</div>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="保存" class="ButtonStyle" />
				<input type="button" value="取 消" onclick="javascript:window.history.back()" class="ButtonStyle"/>
			</div>
		</form>
		&nbsp;${msg}
	</body>
</html>

