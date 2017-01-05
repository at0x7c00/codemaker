<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@taglib prefix="xooxle" uri="/WEB-INF/telJudgment.tld"%>
<html>
	<head>
		<title>映射规则列表</title>
		<script type="text/javascript"
			src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
		<script type="text/javascript">
	function exportExcle() {
		loaded1 = true;
		jQuery("#listForm").attr("action", "${basePath}rule_export.action");
		jQuery("#listForm").attr("target", "_blank");
		document.getElementById("listForm").submit();
		jQuery("#listForm").attr("action", "${basePath}rule_list.action");
		jQuery("#listForm").attr("target", "_self");
	}
</script>
	</head>
	<body class="ui-widget-content">

		<table width="100%">
			<tr style="border: 1px solid red">
				<td class="ui-icon ui-icon-circle-triangle-e" width="16px">
					&nbsp;
				</td>
				<td>
					映射规则列表
				</td>
			</tr>
		</table>

		<table border="0" style="position: absolute; top: 0; right: 0;">
			<tr>
				<td class="ui-icon ui-icon-check"></td>
				<td>
					<a href="javascript:void(0);" onClick="selectAll('ids');"> 全选</a>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-circle-plus"></td>
				<td>
					<span><s:a action="rule_addUI.action">添加</s:a>
					</span>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-trash"></td>
				<td>
					<s:a href="rule_delete.action"
						onClick="return operation('delete','listForm','rule_delete.action','ids')">删除</s:a>
				</td>


			</tr>
		</table>

		<FORM ACTION="${basePath}rule_list.action" METHOD="POST" id="listForm"
			NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
			<table width="100%" border="0" id="causeTab">
				<tr>
					<td align="left">
						输入模板：
						<select name="templateId" id="templateId" class="SelectStyle" />
						<option value="">
							请选择
						</option>
						<c:forEach items="${templateList}" var="vari">
							<option value="${vari.id}" ${templateId==vari.id?'selected':''}>
								${vari.name}
							</option>
						</c:forEach>
						</select>
						输出位置：
						<input name="rule.outputDir" id="rule.outputDir" type="text"
							size="15" class="InputStyle" value="${rule.outputDir}" />
						<input type="submit" name="button" id="button" value="查 询"
							class="ButtonStyle" />
					</td>
				</tr>
			</table>
			<table class="ui-widget ui-widget-content" width="100%">
				<thead>
					<tr class="ui-widget-header" height="30px" id="listTHead">
						<td align="center" width="2%" >
							<INPUT TYPE="CHECKBOX" id="controlCheckbox"
								onClick="selectAll('ids');" value="ON">
						</td>
						<td>
						编号
						</td>
						<td>
							输入模板
						</td>
						<td>
							输出位置
						</td>
						<td width="25%">
							输出文件名
						</td>
						<td width="10%" >
							操作
						</td>

					</TR>
				</thead>
				<tbody id="listTBody">
					<c:if test="${empty pageBean.recordList}">
						<tr>
							<td colspan="4">
								<center>
									<font style="color: red; font-weight: bold; font-size: 16px">暂无记录
									</font>
								</center>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${pageBean.recordList}" var="tempRecord">
						<TR id="listTable" >
							<TD width="2%" ALIGN="CENTER">
								<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${tempRecord.id }"
									onClick="checkThis('ids','controlCheckbox');">
							</TD>
							<td>
								${tempRecord.id }
							</td>
							<td title="${tempRecord.cause}">
								<span style="color:gray;">[${tempRecord.template.group.name}]</span>
								${tempRecord.template.name}
							</td>
							<td>
								${tempRecord.outputDir}
							</td>
							<td>
								${tempRecord.outputFileName}
							</td>
							<td width="10%">
								<a
									href="rule_updateUI.action?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}">修改</a>&nbsp;|&nbsp;
								<a
									href="rule_delete.action?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}"
									onclick="return window.confirm('确认要删除该条记录吗?');">删除</a>
							</td>

						</TR>
					</c:forEach>
				</tbody>
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div id="InputDetailBar">
				<s:a action="rule_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}rule_addUI.action','');">
				增 加
			</s:a>
				<s:a action="rule_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}rule_delete.action','ids')">
				删 除
			</s:a>
			<s:a action="rule_copy.action" cssClass="ButtonStyle"
					onClick="return operation('copy','listForm','${basePath}rule_copy.action','ids')">
					复制
				</s:a>
			</div>
		</FORM>
	</body>
</html>
