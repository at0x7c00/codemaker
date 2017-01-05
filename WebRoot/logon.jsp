<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<HTML>  
<HEAD>
<TITLE>登录成功</TITLE>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
%>

<%@include file="/WEB-INF/pages/public/jqueryLab.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryUI.jsp"%>
<script type="text/javascript">
  $(function() {
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		$( "#dialog-modal" ).dialog({
			height: 140,
			modal: true 
		});
		 //window.open('${basePath}loginSuccess.action','_blank','height='+(screen.availHeight-screen.availHeight*0.051)+',width='+(screen.availWidth-screen.availWidth*0.00732)+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no,status=yes');
       	 window.location="${basePath}loginSuccess.action";
       	 //window.opener=null;
       	 //window.close();
	});
</script>
</HEAD>
<BODY>
<div id="dialog-modal" title="提示">
	<p>登录成功，正在跳转...</p>
</div>
</BODY>
</HTML>