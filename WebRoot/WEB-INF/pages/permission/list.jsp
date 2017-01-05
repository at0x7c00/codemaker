<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>菜单管理</title>
		<style type="text/css">
			.SortInput{
				border:1px solid gray;
				width:80px;
				display:none;
			}
		</style>
		<script language="javascript" src="${basePath}js/common/forList.js"></script>
		<script language="javascript">
			jQuery(function(){
				jQuery(".SortInput").focus(function(){
					jQuery(this).css("border","1px solid red");
				}).blur(function(){
					var tmp = jQuery(this);
					tmp.css("border","1px solid gray");
					if(tmp.attr("oval")==tmp.val()){
						return;
					}
					$.get("${basePath}permission_update.action",{"onlySortValue":1,"id":jQuery(this).attr("att"),"permission.sortValue":jQuery(this).val()},null);
				});
			})
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>菜单列表</td>
	  </tr>
	</table>
	<table border="0" style="position:absolute;top:0;right:0;">
	    <tr style="height:12px">
			  <td class="ui-icon ui-icon-carat-2-n-s"></td>
			  <td>
				<span><a href="javascript:void(0);" onclick="jQuery('.SortInput').toggle();">修改排序</a></span> 
			  </td> 
			  <td>&nbsp;</td>
			  <td class="ui-icon ui-icon-circle-plus"></td>
			  <td>
			  <s:url value="permission_addUI.action" id="add">
		       <s:param name="pageNum">${pageBean.pageNum}</s:param>
		      </s:url>
			  <s:a action="%{add}">添加新菜单</s:a>
			</td>
		</tr>
	</table>
	
		<form action="${basePath}permission_list.action" id="listForm" style="width:100%">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum}" />
			<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<th>ID</th>
					<th>菜单名称</th>
					<th>URL</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="listTBody">
				<c:forEach items="${pageBean.recordList}" var="permission">
				<tr height="25px">
					<td> ${permission.id }</td>
					<td>${permission.name }
					  <input type="text" value="${permission.sortValue}" att="${permission.id}" oval="${permission.sortValue}" class="SortInput">
					 </td>
					<td>${permission.url}</td>
					<td>
							<table>
								<td class="ui-icon ui-icon-pencil"></td>
								<td><a href="${basePath}permission_updateUI.action?id=${permission.id}&pageNum=${pageBean.pageNum}">修改</a></td>
								<td>&nbsp;</td>
								
								<td class="ui-icon ui-icon-trash"></td>
								<td><a href="${basePath}permission_delete.action?id=${permission.id}&pageNum=${pageBean.pageNum}"
								onclick="return window.confirm('删除后将不能恢复，你要继续吗？')">删除</a></td>
								<td>&nbsp;</td>
								
								<td class="ui-icon ui-icon-arrowthick-1-n"></td>
								<td><a href="${basePath}permission_up.action?id=${permission.id}&pageNum=${pageBean.pageNum}"title="调整菜单显示位置上移">上移</a></td>
								<td>&nbsp;</td>
								
								<td class="ui-icon ui-icon-arrowthick-1-s"></td>
								<td><a href="${basePath}permission_down.action?id=${permission.id}&pageNum=${pageBean.pageNum}" title="调整菜单显示位置下移">下移</a></td>
								
							</table>
							
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	</body>
</html>

