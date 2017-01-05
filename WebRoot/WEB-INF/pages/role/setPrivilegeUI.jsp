<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>角色权限设置</title>
    <script language="javascript" src="${basePath}js/jquery_treeview/jquery.treeview.js"></script><!-- 实现树状菜单伸缩功能 -->
	<link type="text/css" rel="stylesheet" href="${basePath}js/jquery_treeview/jquery.treeview.css" />


  <!-- 处理复选框选择问题 -->
  <script type="text/javascript">
 		// 选择所有
    	function selectAll(checkedValue){
    		$("[name=permissionIds]").attr("checked", checkedValue);
    	}
    	$(function(){
    		// 绑定click事件
    		$("[name=permissionIds]").click(function(){
    		
    			// 当前的选中状态
    			var checkedValue = this.checked;
    			
    			// 1，选中一个权限时：
    			if(checkedValue){
				     // a，应该选中他的所有直系上级
				     $(this).parents("li").children("[name=permissionIds]").attr("checked", checkedValue);
				     
				     // b，应该选中他的所有直系下级
				     $(this).siblings("ul").find("[name=permissionIds]").attr("checked", checkedValue);
				}
				// 2，取消选择一个权限时：
				else{
				     // a，应该取消选择 他的所有直系下级
				     $(this).siblings("ul").find("[name=permissionIds]").attr("checked", checkedValue);
				     
				     // b，如果同级的权限都是未选择状态，就应该取消选中他的直接上级，并递归向上做这个操作
				     if( $(this).parent("li").siblings("li").children("[name=permissionIds]:checked").size() == 0 ){
				     	$(this).parent("li").parent("ul").siblings("[name=permissionIds]").attr("checked", checkedValue);
				     	
				     	// 上级的上级也要进行同样的操作（仅当前为第三级时才可以）
				     	if( $(this).parent("li").parent("ul").parent("li").siblings("li").children("[name=permissionIds]:checked").size() == 0 ){
				     		$(this).parent("li").parent("ul").parent("li").parent("ul").siblings("[name=permissionIds]").attr("checked", checkedValue);
				     	}
				     }
				}
    		});
    		$("#tree").treeview();
    	});
    </script>
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
		
    <form action="${basePath}role_setPrivilege.action" method="post">
    	<input type="hidden" name="id"  value="${entity.id }"/>
    	<input type="hidden" name="pageNum"  value="${pageNum}"/>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
               <table class="ui-widget ui-widget-content" width="100%">
				<thead>
					<tr class="ui-widget-header" height="30px" id="listTHead">
							<td width="300px" style="padding-left: 7px;">
								<!-- 如果把全选元素的id指定为selectAll，并且有函数selectAll()，就会有错。因为有一种用法：可以直接用id引用元素 -->
								<input type="checkbox" id="cbSelectAll" onClick="selectAll(this.checked)"/>
								<label for="cbSelectAll">全选</label>
							</td>
						</tr>
					</thead>
                   
			   		<!--显示数据列表-->
					<tbody id="TableData">
						<tr class="TableDetail1">
							<!-- 显示权限树 -->
							<td>
							<!-- 显示顶级 -->
							<ul id="tree"><c:forEach items="${topPermissionList}" var="top">
								<li>
									<input type="checkbox" name="permissionIds" value="${top.id}" id="cb_${top.id}"
									   <c:forEach items="${entity.permissions}" var="ihave">
									       <c:if test="${ihave.id==top.id }">checked</c:if>
									   </c:forEach>
									/>
									<label for="cb_${top.id}" style="cursor: hand;"><span class="ui-icon ui-icon-folder-collapsed" style="display:inline;margin-right:5px;"></span><SPAN style="font-size:15px;" id='${top.id}'>${top.name}</SPAN></label>
									<ul><%-- 显示第二级 --%>
									<c:forEach items="${top.children}" var="level2">
										<li>
											<input type="checkbox" name="permissionIds" value="${level2.id}" id="cb_${level2.id}"
											    <c:forEach items="${entity.permissions}" var="ihave">
									       			<c:if test="${ihave.id==level2.id }">checked</c:if>
									            </c:forEach>
											/>
											<label for="cb_${level2.id}" style="cursor: hand;"><span class="ui-icon ui-icon-folder-collapsed" style="display:inline;margin-right:5px;"></span> <SPAN style="font-size:15px;"  id='${level2.id}'>${level2.name}</SPAN></label><br>
											<ul><%-- 显示第三级 --%>
												<c:forEach items="${level2.children}" var="level3">
													<li>
														<input type="checkbox" name="permissionIds" value="${level3.id}" id="cb_${level3.id}"
														<c:forEach items="${entity.permissions}" var="ihave">
									       					<c:if test="${ihave.id==level3.id }">checked</c:if>
									   					</c:forEach>
														/>
														<label for="cb_${level3.id}" style="cursor: hand;"><span class="ui-icon ui-icon-folder-collapsed" style="display:inline;margin-right:5px;"></span> <SPAN style="font-size:15px;"  id='${level3.id}'>${level3.name}</SPAN></label><br>
													</li>
												</c:forEach>
											</ul>
										</li>
									</c:forEach>
									</ul>
								</li></c:forEach>
							</ul>
							</td>
						</tr>
					</tbody>
                </table>
        </div>
        <!-- 表单操作 -->
        <div id="InputDetailBar">
	        <input type="submit" value="提 交" class="ButtonStyle"/>
		    <input type="reset" value="重 置" class="ButtonStyle"/>
		    <input type="reset" onclick="javascript:history.go(-1);" value="返 回" class="ButtonStyle"/>
	    </div>
	</form>
  </body>
</html>
