<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>学生列表</title>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>学生列表</td>
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
				<span><s:a action="student_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="student_delete.action" onClick="return operation('delete','listForm','student_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table>
	
		<FORM ACTION="${basePath}student_list.action" METHOD="POST" id="listForm"
			NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
			<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
<td><INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON"></td><td>姓名</td>
<td>所属教师</td>
<td>操作</td>

				</TR>
                </thead>
                <tbody id="listTBody">
				<c:if test="${empty pageBean.recordList}">
					<tr>
						<td colspan="4">
							<center>
								<font style="color: red;font-weight:bold;font-size:16px">暂无记录 </font>
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
<td>${tempRecord.name}</td>
<td>${tempRecord.teacher}</td>
<td><a href="student_updateUI.action?id=${tempRecord.id}">修改</a>&nbsp;|&nbsp;<a href="student_delete.action?ids=${tempRecord.id}">删除</a></td>

					</TR>
				</c:forEach>
				</tbody>  
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div  id="InputDetailBar">
				<s:a action="role_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}student_addUI.action','');">
				增 加
			</s:a>
				<s:a action="role_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}student_delete.action$,'ids')">
				删 除
			</s:a>
		   </div>
		</FORM>
	</body>
</html>
