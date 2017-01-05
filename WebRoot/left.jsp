<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>菜单</title>
    
    <%@include file="/WEB-INF/pages/public/common-all.jsp" %>

<script type="text/javascript">
		$(function() {
		$( "#accordion" ).accordion({
			fillSpace: true
		});
		});
		$(function() {
			$( "#accordionResizer" ).resizable({
				minHeight: 140,
				resize: function() {
					$( "#accordion" ).accordion( "resize" );
				}
			});
		});
</script>
  </head>
  <body>
	<div class="demo">
		<h3 class="docs">Resize the outer container:</h3>
			<div id="accordionResizer" style="padding:10px; width:350px; height:220px;" class="ui-widget-content">
					<div id="accordion">
						<c:forEach items="${permissionList}" var="top" varStatus="status">
								<h3><a href="#">${top.name}</a></h3>
								<div>
									<c:forEach  items="${top.children}" var="second" varStatus="status">
											<p><img src="${basePath}images/func/${second.icon}" width="32" height="32"><a href="${basePath}${second.url}" ${second.url=='callNotes_monthlyReport.action'? 'target=_blank':'target=frmright'}>${second.name}</a></p>
									</c:forEach>
								</div>
						</c:forEach>
					</div>
				<span class="ui-icon ui-icon-grip-dotted-horizontal" style="margin:2px auto;"></span>
			</div>
		<div style="margin-top:7px; padding:10px; width:350px; height:50px;" class="ui-widget-content">I'm another panel</div>
	</div>
	
	
  </body>
</html>
