<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>单位信息列表</title>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
	</head>
	<body class="ui-widget-content">
		<FORM ACTION="${basePath }department_list.action" METHOD="POST"
			NAME="dataform" id="listForm" style="margin-top: 5; margin-bottom: 0">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />

    <!-- 标题及工具按钮 -->
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>部门列表</td>
	  </tr>
	</table>
	
	<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
			<td>
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="department_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="department_delete.action" onClick="return operation('delete','listForm','department_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table>
	
	
		<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<td width="3%" nowrap>
						  <INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON">
					</td>
					<td width="8%" ALIGN="CENTER">
						单位编号
					</td>
					<td  ALIGN="CENTER">
						单位名称
					</td>
					<td width="13%" ALIGN="CENTER">
						部门电话1
					</td>
					<td width="11%" ALIGN="CENTER">
						部门电话2
					</td>
					<TD width="15%" ALIGN="CENTER" >
						操作
					</TD>
				</tr>
				</thead>
					<tbody id="listTBody">
				<c:if test="${empty deptList}">
					<tr><td colspan="8" align="center"><font color="red">暂无数据</font></td></tr>
				</c:if>
				<c:forEach items="${deptList}" var="dept">
						<tr height="25px">
						<TD ALIGN="CENTER">
							<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${dept.id}"
								onClick="checkThis('ids','controlCheckbox');">
						</TD>
						<TD ALIGN="CENTER">
							${dept.id}
						</TD>
						<TD ALIGN="left" nowrap>
							<a href='${basePath }department_view.action?id=${dept.id }' style="text-decoration:none;">${dept.name}</a>&nbsp;
						</TD>
						<TD ALIGN="left">
							&nbsp;${dept.phoneNumber1}
						</TD>
						<TD ALIGN="left">
							&nbsp;${dept.phoneNumber2}
						</TD>
						<TD ALIGN="CENTER">
						
							<s:url value="department_updateUI.action" id="update">
								<s:param name="id">${dept.id}</s:param>
							</s:url>
							<s:url value="department_delete.action" id="delete">
								<s:param name="ids">${dept.id}</s:param>
							</s:url>
							 <table>
						       <tr>
						          <td class="ui-icon ui-icon-pencil"></td>
						          <td><s:a value="%{update}">修改</s:a></td>
						          <td>&nbsp;</td>
						          <td class="ui-icon ui-icon-trash"></td>
						          <td><s:a value="%{delete}" onclick="return window.confirm('确认要删除该条记录吗?');">删除</s:a></td>
						       </tr>
						    </table>
						</TD>
					</TR>
				</c:forEach>
				</tbody>
			</TABLE>
			<div id="InputDetailBar">
			<s:a action="department_addUI.action" cssClass="ButtonStyle"  onClick="return operation('add','listForm','${basePath }department_addUI.action','');">
				增 加
			</s:a>
			<s:a action="department_delete.action" cssClass="ButtonStyle" onClick="return operation('delete','listForm','${basePath}department_delete.action','ids')">
				删 除
			</s:a>
		</form>
	</body>
</html>
