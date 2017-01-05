<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>角色权限设置</title>
    <script language="javascript" src="${basePath}js/jquery_treeview/jquery.treeview.js"></script><!-- 实现树状菜单伸缩功能 -->
	<link type="text/css" rel="stylesheet" href="${basePath}js/jquery_treeview/jquery.treeview.css" />


	<script type="text/javascript">
		jQuery(function(){
			jQuery(".modular").button();
		});
	</script>
	<style type="text/css">
		.modular{
			padding:10px;
			width: 90%
		}
	</style>
 <body class="ui-widget-content" >
 
 	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td><font style="color:red">${entity.name}</font>角色权限设置</td>
	  </tr>
	</table>     
      
    <table border="0" style="position:absolute;top:0;right:5;">
	    <tr>
			<td class="ui-icon ui-icon-arrowreturnthick-1-w"></td>
			<td>
				<span><a href="javascript:void(0);" onclick="history.back();">返回<a></span> 
			</td> 
		</tr>
	</table>
		
    <form action="${basePath}role_setModular.action" method="post">
    	<input type="hidden" name="id"  value="${entity.id }"/>
    	<input type="hidden" name="pageNum"  value="${pageNum}"/>
        
        <table class="ui-widget ui-widget-content" width="100%"  border="0">
			<tbody id="listTBody">
				<tr height="25px">
					<c:forEach items="${modularList}" var="deskModular" varStatus="status">
					     <td>
					       <div class="modular" width="20%">
					       	 <b>${deskModular.title }</b>
					       	 <input value="${deskModular.id }" name="modularId" type="checkbox"/>
					       </div>
					     </td>
					 <c:if test="${not status.last and not status.last and status.count%5==0}">
					     </tr>
					     <tr>
					 </c:if>
					</c:forEach>
				</tr>
			</tbody>
		</table>
		
        <!-- 表单操作 -->
        <div id="InputDetailBar">
	        <input type="submit" value="提 交" class="ButtonStyle"/>
		    <input type="reset" value="重 置" class="ButtonStyle"/>
		    <input type="reset" onclick="javascript:history.go(-1);" value="返 回" class="ButtonStyle"/>
	    </div>
	</form>
  </body>
</html>
