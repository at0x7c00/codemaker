<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>软件授权失败</title>
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
  
  <div id="dialog-modal" title="软件授权失败" style="display:none;width:90%"  style="margin:10px;">
	  <table class="mainForm" width="100%" align="center" border="0" >
			<tr>
				<td align="left">
				<span class="ui-icon ui-icon-alert"></span>
					公钥或授权号无效,验证授权号失败，如果您是未注册用户，请先<a href="${basePath}registUI.jsp">点击注册</a>，或者重启服务器继续试用。如果您已注册，可能是由于<a href="${basePath}mac.jsp" target="blank">服务物理地址</a>变动造成认证失败。
				</td>
			</tr>
		</table>
  </div>
  
  </body>
</html>
