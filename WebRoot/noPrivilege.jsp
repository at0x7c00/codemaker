<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出错了</title>
    <script type="text/javascript"> 
    var dialogHeight=browserCheck().ie?200:200;
    $(function() { 
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		$( "#dialog-modal" ).dialog({
			height: dialogHeight,
			width:400,
			modal: true ,
			autoOpen: true,
			buttons: {
				"确认": function() {
				   window.history.back();
				}
			},
			close: function() {
				window.history.back();
			}
		});
	});
    </script>
  </head>
  
	<body class="ui-widget-content">
  
  <div id="dialog-modal" title="错误" style="display:none;width:90%"  style="margin:10px;">
	  <table class="mainForm" width="50%" align="center" border="0" >
			<tr>
				<td class="ui-icon ui-icon-alert">
				</td>
				<td align="left">
					您没有权限访问该页面
				</td>
			</tr>
		</table>
  </div>
  
  </body>
</html>
