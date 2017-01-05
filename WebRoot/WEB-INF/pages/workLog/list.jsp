<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@taglib prefix="xooxle" uri="/WEB-INF/telJudgment.tld"%>
<html>
	<head>
		<title>工作日志列表</title>
		<script type="text/javascript"
			src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
		<script type="text/javascript">
		   function exportExcle(){
		   		loaded1 =true;
		   		jQuery("#listForm").attr("action","${basePath}workLog_export.action");
		   		jQuery("#listForm").attr("target","_blank");
		        document.getElementById("listForm").submit();
		   		jQuery("#listForm").attr("action","${basePath}workLog_list.action");
		   		jQuery("#listForm").attr("target","_self");
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
					工作日志列表
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
					<span><s:a action="workLog_addUI.action">添加</s:a>
					</span>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-trash"></td>
				<td>
					<s:a href="workLog_delete.action"
						onClick="return operation('delete','listForm','workLog_delete.action','ids')">删除</s:a>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-arrowstop-1-s"></td>
				<td>
					<span style="cursor: hand" onclick="exportExcle();">导出Excel</span>
				</td>
			</tr>
		</table>

		<FORM ACTION="${basePath}workLog_list.action" METHOD="POST"
			id="listForm" NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
			<table width="100%" border="0" id="causeTab" style="display: none;">
				<tr>
					<td width="10%" align="right">
						记录时间：
					</td>
					<td width="40%">
						<input name="addTimeStart" id="workLog.addTime" type="text"
							size="15" class="WDate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 180px;" readonly="readonly"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${addTimeStart}"/>" />
						至
						<input name="addTimeEnd" id="workLog.addTime" type="text"
							size="15" class="WDate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 180px;" readonly="readonly"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${addTimeEnd}"/>" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						内容：
					</td>
					<td width="40%" colspan="3">
						<input name="workLog.content" id="workLog.content" type="text"
							size="15" class="InputStyle" value="${workLog.content}" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						所属类型：
					</td>
					<td width="40%">
						<select name="logTypeId" id="logTypeId" class="SelectStyle" />
							<option value="">
								请选择
							</option>
							<c:forEach items="${logTypeList}" var="vari">
								<option value="${vari.id}" ${logTypeId==vari.id?'selected':''}>
									${vari.name}
								</option>
							</c:forEach>
						</select>
					</td>
					<td width="10%" align="right">
						状态：
					</td>
					<td width="40%">
						<select name="workLog.status" id="status" class="SelectStyle" />
							<option value="0" ${workLog.status==0?'selected':''}>
								待处理
							</option>
							<option value="1" ${workLog.status==1?'selected':''}>
								处理中
							</option>
							<option value="2" ${workLog.status==2?'selected':''}>
								已处理
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						开始处理时间：
					</td>
					<td width="40%">
						<input name="processStartDateStart" id="workLog.processStartDate"
							type="text" size="15" class="WDate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 180px;" readonly="readonly"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${processStartDateStart}"/>" />
						至
						<input name="processStartDateEnd" id="workLog.processStartDate"
							type="text" size="15" class="WDate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 180px;" readonly="readonly"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${processStartDateEnd}"/>" />
					</td>
					<td width="10%" align="right">
						完成处理时间：
					</td>
					<td width="40%">
						<input name="processEndDateStart" id="workLog.processEndDate"
							type="text" size="15" class="WDate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 180px;" readonly="readonly"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${processEndDateStart}"/>" />
						至
						<input name="processEndDateEnd" id="workLog.processEndDate"
							type="text" size="15" class="WDate"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 180px;" readonly="readonly"
							value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${processEndDateEnd}"/>" />
					</td>
				</tr>
				<tr>
					<td width="10%" align="right">
						处理备注：
					</td>
					<td width="40%" colspan="3">
						<input name="workLog.remark" id="workLog.remark" type="text"
							size="15" class="InputStyle" value="${workLog.remark}" />
					</td>
				</tr>
				<tr></tr>
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
							记录时间
						</td>
						<td>
							所属类型
						</td>
						<td>
							状态
						</td>
						<td>
							开始处理时间
						</td>
						<td>
							完成处理时间
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
								<fmt:formatDate value="${tempRecord.addTime}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								${tempRecord.logType.name}
							</td>
							<td>
								<c:choose>
									<c:when test="${tempRecord.status==0}">待处理</c:when>
									<c:when test="${tempRecord.status==1}">处理中</c:when>
									<c:when test="${tempRecord.status==2}">已处理</c:when>
								</c:choose>
							</td>
							<td>
								<fmt:formatDate value="${tempRecord.processStartDate}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<fmt:formatDate value="${tempRecord.processEndDate}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="10%">
								<a
									href="workLog_updateUI.action?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}">修改</a>&nbsp;|&nbsp;
								<a
									href="workLog_delete.action?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}"
									onclick="return window.confirm('确认要删除该条记录吗?');">删除</a>
							</td>
						</TR>
					</c:forEach>
				</tbody>
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div id="InputDetailBar">
				<s:a action="workLog_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}workLog_addUI.action','');">
				增 加
			</s:a>
				<s:a action="workLog_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}workLog_delete.action','ids')">
				删 除
			</s:a>
			</div>
		</FORM>
	</body>
</html>
