<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>职位管理</title>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
	</head>
	<body class="ui-widget-content">
	
	<!-- 标题及工具按钮 -->
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>职位列表</td>
	  </tr>
	</table>
	
	<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
			<td>
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="job_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="job_delete.action" onClick="return operation('delete','listForm','job_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table>
	
		<FORM ACTION="${basePath }job_list.action" id="listForm" METHOD="POST"
			NAME="dataform">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }">
			<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<TD ALIGN="CENTER"  width="2%">
					<INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON">
					</TD>
					<TD ALIGN="CENTER"  width="10%">
						ID号
					</TD>
					<TD ALIGN="CENTER"  width="10%">
						职位名称
					</TD>
					<TD ALIGN="CENTER"  width="10%">
						职位描述
					</TD>
					<TD ALIGN="CENTER"  width="18%">
						修改
					</TD>
				</TR>
				</thead>
				<tbody id="listTBody">
				<c:if test="${empty pageBean.recordList}">
					<tr>
						<td colspan="5">
							<center>
								<font style="color: red;font-weight:bold;font-size:16px">暂无记录 </font>
							</center>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${pageBean.recordList}" var="job">
					<tr height="25px">
							<TD width="2%" ALIGN="CENTER">
								<c:if test="${job.status==1}">
								<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${job.id }"
									onClick="checkThis('id','controlCheckbox');">
								</c:if>
							</TD>
							<TD align="center">
								${job.id}
							</TD>
							<TD width="20%" ALIGN="center">
								${job.name }
							</TD>
							<TD width="20%" ALIGN="center">
								${job.description }
							</TD>
							<TD ALIGN="CENTER" width="8%">
								<c:if test="${job.status==1}">
									<s:url value="job_updateUI.action" id="update">
										<s:param name="id">${job.id}</s:param>
									</s:url>
									<s:a value="%{update}">修改</s:a>
								</c:if>
							&nbsp;
							</TD>
					</TR>
				</c:forEach>
				</tbody>
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div id="InputDetailBar">

				<s:a action="job_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath }job_addUI.action','');">
				增 加
			</s:a>
				<s:a action="job_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}job_delete.action','ids')">
				删 除
			</s:a>

		</FORM>
	</body>
</html>
