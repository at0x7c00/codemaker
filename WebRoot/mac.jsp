<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.lenovots.crm.util.CommonUtil"%> 
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
      <title>服务器物理地址</title>
    <script type="text/javascript">  
    var dialogHeight=browserCheck().ie?300:300;
    $(function() {  
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		$( "#dialog-modal" ).dialog({
			height: dialogHeight,
			width:500,
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
  <div id="dialog-modal" title="服务器物理地址" style="display:none;width:90%"  style="margin:10px;">
	  <table class="mainForm" width="100%" align="center" border="0" >
			<tr>
				<td align="left" style="font-size:16px;">
					<%=CommonUtil.getPhysicalAddress() %>
				</td>
			</tr>
		</table>
  </div>
  </body>
</html>

