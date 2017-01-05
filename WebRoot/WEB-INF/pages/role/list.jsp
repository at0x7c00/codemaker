<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>角色管理</title>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
	</head>
	
	
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>角色列表</td>
	  </tr>
	</table>
	
	<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
			<td>
			  <a href="javascript:void(0);" onClick="selectAll('ids');"> 全选</a> 
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="role_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="role_delete.action" onClick="return operation('delete','listForm','role_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table>
	
		<FORM ACTION="${basePath}role_list.action" METHOD="POST" id="listForm"
			NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
			<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<TD ALIGN="CENTER" class="TableTdBgStyle" nowrap width="4%"></TD>
					<TD ALIGN="CENTER" class="TableTdBgStyle" nowrap width="6%">
						编号
					</TD>
					<TD ALIGN="CENTER" class="TableTdBgStyle" nowrap width="50%">
						名称
					</TD>
					<TD ALIGN="CENTER" class="TableTdBgStyle" nowrap width="20%">
						操作
					</TD>
				</TR>
                </thead>
                <tbody id="listTBody">
				<c:if test="${empty pageBean.recordList}">
					<tr>
						<td colspan="10">
							<center>
								<font style="color: red;font-weight:bold;font-size:16px">暂无记录 </font>
							</center>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${pageBean.recordList}" var="role">
					<TR id="listTable">
						<TD width="4%" ALIGN="CENTER">
							<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${role.id }"
								onClick="checkThis('ids','controlCheckbox');">
						</TD>
						<TD width="6%" ALIGN="CENTER">
							<font COLOR="#336699">${role.id }</font>
						</TD>
						<TD ALIGN="left" nowrap>
							${role.name}
						</TD>
						<TD width="30%" ALIGN="center" nowrap>
							<s:url value="role_updateUI.action" id="update">
								<s:param name="id">${role.id}</s:param>
							</s:url>
							<s:url value="role_setPrivilegeUI.action" id="setPrivilege">
								<s:param name="id">${role.id}</s:param>
							</s:url>
							<s:url value="role_setModularUI.action" id="setModular">
								<s:param name="id">${role.id}</s:param>
							</s:url>
							<s:url value="role_delete.action" id="delete">
								<s:param name="ids">${role.id}</s:param>
							</s:url>
						    <table>
						       <tr>
						          <td class="ui-icon ui-icon-pencil"></td>
						          <td><s:a value="%{update}">修改</s:a></td>
						          <td>&nbsp;</td>
						          <td class="ui-icon ui-icon-trash"></td>
						          <td><s:a value="%{delete}" onclick="return window.confirm('确认要删除该条记录吗?');">删除</s:a></td>
						          <td>&nbsp;</td>
						          <td class="ui-icon ui-icon-key"></td>
						          <td><s:a value="%{setPrivilege}">设置权限</s:a></td>
						          <td>&nbsp;</td>
						          <td class="ui-icon ui-icon-key"></td>
						          <td><s:a value="%{setModular}">设置模块</s:a></td>
						       </tr>
						    </table>
						</TD>
					</TR>
				</c:forEach>
				</tbody>  
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div  id="InputDetailBar">

				<s:a action="role_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath }role_addUI.action','');">
				增 加
			</s:a>
				<s:a action="role_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}role_delete.action','ids')">
				删 除
			</s:a>
		   </div>
		</FORM>
	</body>
</html>
