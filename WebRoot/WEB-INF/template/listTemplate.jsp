<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/common/taglib.jsp"%>
<form:form onsubmit="return navTabSearch(this);" action="BEAN_NAME/list.do" method="post" commandName="BEAN_NAME" id="pagerForm">
<input type="hidden" name="pageNum" value="${pageBean.pageNum}" />
<input type="hidden" name="orderField" value="${pageBean.orderField}" />
<input type="hidden" name="orderDirection" value="${pageBean.orderDirection}" />
<div class="pageHeader">
	<div class="searchBar">
		<ul class="searchContent">
			QUERY_TABLE
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
			</ul>
		</div>
	</div>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="BEAN_NAME/add.do?rel=${rel}" target="navTab" rel="${rel}_add"><span>添加</span></a></li>
			<li><a class="edit" href="BEAN_NAME/update.do?ENTITY_ID_PROP_NAME={sid_BEAN_NAME}&rel=${rel}" target="navTab" rel="${rel}_update"><span>修改</span></a></li>
			<li><a class="edit" href="BEAN_NAME/detail.do?ENTITY_ID_PROP_NAME={sid_BEAN_NAME}&rel=${rel}" target="navTab" rel="${rel}_detail"><span>查看</span></a></li>
			<li><a class="delete" href="BEAN_NAME/delete.do?ENTITY_ID_PROP_NAME={sid_BEAN_NAME}&rel=${rel}" target="ajaxTodo" title="确定要删除吗?" method="GET"><span>删除</span></a></li>
			<%--
			<li class="line">line</li>
			<li><a title="确实要删除这些记录吗?" target="selectedTodo" postType="string" href="BEAN_NAME/delete.do" class="delete" method="POST"><span>批量删除</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
			--%>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				HEAD_TDS
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty pageBean.list}">
				<tr>
					<td colspan="EMPTY_COLSPAN">
						<center>
							<font style="color: gray;">暂无记录 </font>
						</center>
					</td>
				</tr>
			</c:if>
			<c:forEach var="tempBEAN_NAME" items ="${pageBean.list}" varStatus="stauts">
				<tr target="sid_BEAN_NAME" rel="${tempBEAN_NAME.BEAN_ID_NAME}">
					BODY_TDS
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@include file="/WEB-INF/jsp/common/pageBar.jsp" %>
</div>
</form:form>
