<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>系统用户管理</title>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
	</head>
		<body class="ui-widget-content">
	
	<table width="100%">  
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>系统用户列表</td>
	  </tr>
	</table>
	
		<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
			<td>
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="operator_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="operator_delete.action" onClick="return operation('delete','listForm','operator_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table>
	
		<FORM ACTION="${basePath}operator_list.action" METHOD="POST"
			id="listForm" NAME="dataform" style="margin-top: 5; margin-bottom: 0">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum}" />
			<input type="hidden" name="id" value="${operator.id }" />
			<!-- 标题及工具按钮 -->
			<s:fielderror />
			<div id="TableTail">
       	    <div id="TableTail_inside">
			<table width="100%" border="0">
				<tr>
					<td align="right" width="10%">
						姓&nbsp; &nbsp; 名：
					</td>
					<td width="20%">
						<input name="inputName" type="text" size="8" class="InputStyle"
							style="width: 100%" value="${inputName }">
					</td>
					<td align="right" width="10%">
						账&nbsp; &nbsp; 号：
					</td>
					<td width="20%">
						<input name="inputLogin" type="text" size="17" class="InputStyle"
							style="width: 100%" value="${inputLogin }">
					<td align="right" width="10%">
						联系电话：
					</td>
					<td>
						<input name="inputPhone" type="text" size="10" class="InputStyle"
							style="width: 100%" value="${inputPhone }">
				</tr>
				<tr>
					<td height="25" align="right">
						单&nbsp; &nbsp; 位：
					</td>
					<td>
						<select name="deptId" id="deptSel" class="InputStyle"
							style="width: 100%">
							<option value="-1">
								---请选择---
							</option>
							<c:forEach items="${deptList}" var="dept">
								<option value="${dept.id }"
									<c:if test="${deptId==dept.id }">selected</c:if>>
									${dept.name}
								</option>
							</c:forEach>
						</select>
					<td align="right">
						职&nbsp; &nbsp; 位：
					<td>
						<select name="jobId" id="jobId" class="SelectStyle"
							style="width: 100%">
							<option value="-1">
								---请选择---
							</option>
							<c:forEach items="${jobList}" var="job">
								<option value="${job.id}"
									<c:if test="${jobId==job.id }">selected</c:if>>
									${job.name }
								</option>
							</c:forEach>
						</select>
					<td align="right">
						角&nbsp; &nbsp; 色：
					<td>
						<select name="roleId" id="roleSel" class="select" style="width: 100%">
							<option value="-1">
								---请选择---
							</option>
							<c:forEach items="${roleList}" var="role">
								<option value="${role.id}"
									<c:if test="${roleId==role.id }">selected</c:if>>
									${role.name }
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="12">
						<input type="submit" name="button" class="ButtonStyle" id="button"
							value="查 询" />
						<input type="reset" name="button2" class="ButtonStyle"
							id="button2" value="重 置" />
					</td>
				</tr>
			</table>
			</div>
			</div>
		<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
				<td width="5%" align="center">
				<INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON">
				</td>
				<td width="10%" ALIGN="CENTER">
					编号
				</td>
				<td width="10%" ALIGN="CENTER">
					姓名
				</td>
				<td width="10%" ALIGN="CENTER">
					帐号
				</td>
				<td width="10%" ALIGN="CENTER">
					工号
				</td>
				<td width="10%" ALIGN="CENTER">
					单位
				</td>
				<td width="5%" ALIGN="CENTER">
					职位
				</td>
				<td width="5%" ALIGN="CENTER">
					所属角色
				</td>
				<TD ALIGN="CENTER"  width="8%">
					操作
				</TD>
			</tr>
			</thead>
			<tbody id="listTBody">
			<c:if test="${empty pageBean.recordList}">
				<tr><td colspan="11" style="text-align: center;color:red;font-weight:bold;">暂无数据</td></tr>
			</c:if>
			<c:forEach items="${pageBean.recordList}" var="operator">
				<tr height="25px">
					<TD width="2%" ALIGN="CENTER">
						<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${operator.id}"
							onClick="checkThis('ids','controlCheckbox');">
					</TD>
					<TD width="3%" ALIGN="CENTER">
						${operator.id }
					</TD>
					<TD width="5%" ALIGN="center" nowrap>
						<A href="${basePath }operator_view.action?id=${operator.id }">${operator.name}</A>
					</TD>
					<TD width="5%" ALIGN="center">
						${operator.loginName}
					</TD>
					<TD width="5%" ALIGN="center">
						${operator.workNum}
					</TD>
					<TD width="5%" ALIGN="center">
						${operator.dept.name}
					</TD>
					<TD width="3%" ALIGN="center">
						${operator.job.name}
					</TD>
					<TD width="5%" ALIGN="center">
						${operator.role.name}
					</TD>
					<TD ALIGN="CENTER" nowrap width="8%">
						<s:url value="operator_updateUI.action" id="update">
							<s:param name="id">${operator.id}</s:param>
						</s:url>
						<s:url value="operator_delete.action" id="delete">
							<s:param name="ids">${operator.id}</s:param>
						</s:url>
						<table>
							<td class="ui-icon ui-icon-pencil"></td>
							<td><s:a href="%{update}">修改</s:a></td>
							<td>&nbsp;</td>
							<td class="ui-icon ui-icon-trash"></td>
							<td><s:a href="%{delete}" onclick="return window.confirm('确认要删除该条记录吗?');">删除</s:a></td>
						</table>
					</TD>
			</tr>
			</c:forEach>
			</tbody>
		</TABLE>
		<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
		</FORM>
		<div id="InputDetailBar">
			<s:a action="operator_addUI.action" cssClass="ButtonStyle"
				onClick="return operation('add','listForm','${basePath }operator_addUI.action','');">
				增 加
			</s:a>
			<s:a action="operator_delete.action" cssClass="ButtonStyle"
				onClick="return operation('delete','listForm','${basePath}operator_delete.action','ids')">
				删 除
			</s:a>
			<%--<input type="button" name="btnAdd" value="增加" class="ButtonStyle"
				style="WIDTH: 60pt"
				onClick="operation('add','listForm','${basePath }operator_addUI.action','');">
			<input type="button" name="btnDel" value="删除" class="ButtonStyle"
				style="WIDTH: 60pt"
				onClick="operation('delete','listForm','${basePath}operator_delete.action','ids')">
		--%></div>
	</body>
</html>
