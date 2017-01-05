<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@taglib prefix="xooxle" uri="/WEB-INF/telJudgment.tld"%>
<html>
	<head>
		<title>任务节点列表</title>
		<script type="text/javascript"
			src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
		<script type="text/javascript">
	function exportExcle() {
		loaded1 = true;
		jQuery("#listForm").attr("action", "${basePath}taskNode_export.action");
		jQuery("#listForm").attr("target", "_blank");
		document.getElementById("listForm").submit();
		jQuery("#listForm").attr("action", "${basePath}taskNode_list.action");
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
					任务节点列表
				</td>
			</tr>
		</table>

		<table border="0" style="position: absolute; top: 0; right: 0;">
			<tr>
				<td class="ui-icon ui-icon-search"></td>
				<td>
					<span><a href="javascript:void(0);"
						onclick="jQuery('#causeTab').toggle();">查询条件</a> </span>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-check"></td>
				<td>
					<a href="javascript:void(0);" onClick="selectAll('ids');"> 全选</a>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-circle-plus"></td>
				<td>
					<span><s:a action="taskNode_addUI.action">添加</s:a>
					</span>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-trash"></td>
				<td>
					<s:a href="taskNode_delete.action"
						onClick="return operation('delete','listForm','taskNode_delete.action','ids')">删除</s:a>
				</td>


			</tr>
		</table>

		<FORM ACTION="${basePath}taskNode_list.action" METHOD="POST"
			id="listForm" NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
			<table width="100%" border="0" id="causeTab" style="display: none;">
				<tr>
					<td width="10%" align="right">
						任务名称：
					</td>
					<td width="40%">
						<input name="taskNode.name" id="taskNode.name" type="text"
							size="15" class="InputStyle" value="${taskNode.name}" />
					</td>
					<td width="10%" align="right">
						列表URL：
					</td>
					<td width="40%">
						<input name="taskNode.listUrl" id="taskNode.listUrl" type="text"
							size="15" class="InputStyle" value="${taskNode.listUrl}" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						处理URL：
					</td>
					<td width="40%">
						<input name="taskNode.processUrl" id="taskNode.processUrl"
							type="text" size="15" class="InputStyle"
							value="${taskNode.processUrl}" />
					</td>
					<td width="10%" align="right">
						处理提交URL：
					</td>
					<td width="40%">
						<input name="taskNode.submitUrl" id="taskNode.submitUrl"
							type="text" size="15" class="InputStyle"
							value="${taskNode.submitUrl}" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						所属实体：
					</td>
					<td width="40%">
						<select name="contextEntityId" id="contextEntityId"
							class="SelectStyle" />
						<option value="">
							请选择
						</option>
						<c:forEach items="${contextEntityList}" var="vari">
							<option value="${vari.id}" ${contextEntityId==vari.id?'selected':''}>
								${vari.name}
							</option>
						</c:forEach>
						</select>
					</td>
					<td width="10%" align="right">
						过滤条件：
					</td>
					<td width="40%">
						<input name="taskNode.filterCause" id="taskNode.filterCause"
							type="text" size="15" class="InputStyle"
							value="${taskNode.filterCause}" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input type="submit" name="button" id="button" value="查 询"
							class="ButtonStyle" />
						<input type="reset" name="button2" id="button2" value="重 置"
							class="ButtonStyle" />
					</td>
				</tr>
			</table>
			<table class="ui-widget ui-widget-content" width="100%">
				<thead>
					<tr class="ui-widget-header" height="30px" id="listTHead">
						<td align="center">
							<INPUT TYPE="CHECKBOX" id="controlCheckbox"
								onClick="selectAll('ids');" value="ON">
						</td>
						<td>
							任务名称
						</td>
						<td>
							列表URL
						</td>
						<td>
							处理URL
						</td>
						<td>
							处理提交URL
						</td>
						<td>
							所属实体
						</td>
						<td>
							过滤条件
						</td>
						<td>
							操作
						</td>

					</TR>
				</thead>
				<tbody id="listTBody">
					<c:if test="${empty pageBean.recordList}">
						<tr>
							<td colspan="9">
								<center>
									<font style="color: red; font-weight: bold; font-size: 16px">暂无记录
									</font>
								</center>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${pageBean.recordList}" var="tempRecord">
						<TR id="listTable">
							<TD width="4%" ALIGN="CENTER">
								<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${tempRecord.id }"
									onClick="checkThis('ids','controlCheckbox');">
							</TD>
							<td>
								${tempRecord.name}
							</td>
							<td>
								<xooxle:contentShower length="15"
									content="${tempRecord.listUrl}"></xooxle:contentShower>
							</td>
							<td>
								<xooxle:contentShower length="15"
									content="${tempRecord.processUrl}"></xooxle:contentShower>
							</td>
							<td>
								<xooxle:contentShower length="15"
									content="${tempRecord.submitUrl}"></xooxle:contentShower>
							</td>
							<td>
								${tempRecord.contextEntity.name}
							</td>
							<td>
								${tempRecord.filterCause}
							</td>
							<td width="10%">
								<a
									href="taskNode_updateUI.action?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}">修改</a>&nbsp;|&nbsp;
								<a
									href="taskNode_delete.action?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}"
									onclick="return window.confirm('确认要删除该条记录吗?');">删除</a>
							</td>

						</TR>
					</c:forEach>
				</tbody>
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div id="InputDetailBar">
				<s:a action="taskNode_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}taskNode_addUI.action','');">
				增 加
			</s:a>
				<s:a action="taskNode_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}taskNode_delete.action','ids')">
				删 除
			</s:a>
			</div>
		</FORM>
	</body>
</html>
