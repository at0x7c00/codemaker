<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@taglib prefix="xooxle" uri="/WEB-INF/telJudgment.tld"%>
<html>
	<head>
		<title>标签列表</title>
		<script type="text/javascript"
			src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
		<script type="text/javascript">
	function exportExcle() {
		loaded1 = true;
		jQuery("#listForm").attr("action", "${basePath}varLabel_export.action");
		jQuery("#listForm").attr("target", "_blank");
		document.getElementById("listForm").submit();
		jQuery("#listForm").attr("action", "${basePath}varLabel_list.action");
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
					标签列表
				</td>
			</tr>
		</table>

		<table border="0" style="position: absolute; top: 0; right: 0;">
			<tr>
				<td class="ui-icon ui-icon-carat-2-n-s"></td>
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
					<span><s:a action="varLabel_addUI.action">添加</s:a>
					</span>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-trash"></td>
				<td>
					<s:a href="varLabel_delete.action"
						onClick="return operation('delete','listForm','varLabel_delete.action','ids')">删除</s:a>
				</td>


			</tr>
		</table>

		<FORM ACTION="${basePath}varLabel_list.action" METHOD="POST"
			id="listForm" NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
			<table width="100%" border="0" id="causeTab" >
				<tr>
					<td width="10%" align="right">
						标签名称：
					</td>
					<td width="40%" >
						<input name="varLabel.name" id="varLabel.name" type="text"
							size="15" class="InputStyle" value="${varLabel.name}" />
					</td>
					<td width="10%" align="right">
						说明：
					</td>
					<td width="40%">
						<input name="varLabel.remark" id="varLabel.remark" class="InputStyle" value="${varLabel.remark}"/></td>
				</tr>
				<tr>
					<td width="10%" align="right">
						类型：
					</td>
					<td width="40%">
						<select name="varLabel.category" id="category" class="SelectStyle" />
							<option value="">-全部-</option>
							<option value="0" ${varLabel.category==0?'selected':''}>内置</option>
							<option value="1" ${varLabel.category==1?'selected':''}>自定义</option>
						</select>
					</td>
					<td width="10%" align="right">
						常量值：
					</td>
					<td width="40%" >
						<input name="varLabel.value" id="varLabel.value" type="text"
							size="15" class="InputStyle" value="${varLabel.value}" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						所属项目：
					</td>
					<td width="40%">
						<select name="projectId" id="projectId" class="SelectStyle" />
						<option value="">
							请选择
						</option>
						<c:forEach items="${projectList}" var="vari">
							<option value="${vari.id}" ${projectId==vari.id?'selected':''}>
								${vari.name}
							</option>
						</c:forEach>
						</select>
					</td>
					<td align="center" colspan="2">
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
							标签名称
						</td>
						<td width="10%">
							操作
						</td>

					</TR>
				</thead>
				<tbody id="listTBody">
					<c:if test="${empty pageBean.recordList}">
						<tr>
							<td colspan="7">
								<center>
									<font style="color: red; font-weight: bold; font-size: 16px">暂无记录
									</font>
								</center>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${pageBean.recordList}" var="tempRecord">
						<TR>
							<TD width="4%" ALIGN="CENTER">
								<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${tempRecord.id }"
									onClick="checkThis('ids','controlCheckbox');">
							</TD>
							<td>
								<c:choose>
									<c:when test="${tempRecord.category==0}"><img src="images/inner.jpg" title="内置"/></c:when>
									<c:when test="${tempRecord.category==1}"><img src="images/custom.jpg" title="自定义"/></c:when>
								</c:choose>
								<b>${tempRecord.name}</b>
								<div style="border:1px dashed #ccc;padding:5px;margin:2px;">
								${tempRecord.remark}
								<span style="color:green;">${tempRecord.project.name}</span>
								</div>
							</td>
							<td width="10%">
								<a
									href="varLabel_updateUI.action?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}">修改</a>&nbsp;|&nbsp;
								<a
									href="varLabel_delete.action?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}"
									onclick="return window.confirm('确认要删除该条记录吗?');">删除</a>
							</td>
						</TR>
					</c:forEach>
				</tbody>
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div id="InputDetailBar">
				<s:a action="varLabel_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}varLabel_addUI.action','');">
				增 加
			</s:a>
				<s:a action="varLabel_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}varLabel_delete.action','ids')">
				删 除
			</s:a>
			</div>
		</FORM>
	</body>
</html>
