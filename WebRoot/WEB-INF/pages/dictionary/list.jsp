<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>数据字典</title>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type">
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>数据字典</td>
	  </tr>
	</table>
	
	<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
			<td>
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="dictionary_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="dictionary_delete.action" onClick="return operation('delete','listForm','dictionary_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table>
	
		<FORM ACTION="${basePath }dictionary_list.action" id="listForm" METHOD="POST" NAME="dataform">
			<input type="hidden" name="tmp" value="1">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }">
		<table class="ui-widget ui-widget-content" width="100%">
		<thead>
			<tr class="ui-widget-header" height="30px" id="listTHead">
										<TD ALIGN="CENTER" class="TableTdBgStyle" width="4%"></TD>
										<TD ALIGN="CENTER"  width="10%">
											<b>名称</b>
										</TD>
										<TD ALIGN="CENTER"  width="10%">
											<b>编号</b>
										</TD>
										<TD ALIGN="CENTER"  width="10%">
											<b>描述</b>
										</TD>
										<TD ALIGN="CENTER"  width="18%">
											<b>修改</b>
										</TD>
									</TR>
									</thead>
									<tbody id="listTBody">
									<c:if test="${empty pageBean.recordList}">
										<tr>
											<td class="bg" colspan="6" align="center">
												<font style="color: red;font-weight:bold;font-size:16px">暂无记录 </font>
											</td>
										</tr>
									</c:if>
									<c:forEach items="${pageBean.recordList}" var="dictionary">
										<TR onmouseout="this.style.backgroundColor='#FFFFFF'"
											onmouseover="this.style.backgroundColor='#BFDFFF'">
											<TD width="2%" ALIGN="CENTER">
												<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${dictionary.id }"
													onClick="checkThis('ids','controlCheckbox');">
											</TD>
											<TD width="20%" ALIGN="center">
												<s:url value="dictionaryDetail_list.action" id="view">
													<s:param name="parentId">${dictionary.id}</s:param>
												</s:url>
												<s:a href="%{view}">${dictionary.title}(${fn:length(dictionary.details) })</s:a>
											</TD>
											<TD width="20%" ALIGN="center">
												${dictionary.prefix}
											</TD>
											<TD width="16%" ALIGN="center">
												${dictionary.description }&nbsp;
											</TD>
											<TD ALIGN="CENTER" width="8%">
											    <s:url value="dictionary_updateUI.action" id="update">
													<s:param name="id">${dictionary.id}</s:param>
												</s:url>
												
											    <s:url value="dictionary_delete.action" id="delete">
													<s:param name="ids">${dictionary.id}</s:param>
												</s:url>
												<table>
												   <tr>
												      <td><span class="ui-icon ui-icon-pencil"></span></td>
												      <td><s:a href="%{update}">修改</s:a></td>
												      <td><span class="ui-icon ui-icon-newwin"></span></td>
												      <td><s:a href="%{view}">详情</s:a></td>
												      <td><span class="ui-icon ui-icon-trash"></span></td>
												      <td><s:a href="%{delete}" onclick="return window.confirm('确认要删除该记录吗?');">删除</s:a></td>
												   </tr>
												</table>
											</TD>
										</TR>
									</c:forEach>
									</tbody>
								</TABLE>
								<jsp:include page="/WEB-INF/pages/public/pageBar.jsp"></jsp:include>
								<div id="InputDetailBar">
									<s:a action="dictionary_addUI.action" cssClass="ButtonStyle"
										onClick="return operation('add','listForm','${basePath }dictionary_addUI.action','');">
									增 加
									</s:a>
									<s:a action="dictionary_delete.action" cssClass="ButtonStyle"
										onClick="return operation('delete','listForm','${basePath}dictionary_delete.action','ids')">
									删 除
									</s:a>
								</div>
		</FORM>
	</body>
</html>
