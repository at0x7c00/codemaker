<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//response.sendRedirect(basePath+"loginUI.action");
%>
	<script type="text/javascript">
		window.onload=function(){
		 	window.location.href="${basePath}loginUI.action";
			//window.open('${basePath}loginUI.action','_blank','height='+(screen.availHeight-screen.availHeight*0.051)+',width='+(screen.availWidth-screen.availWidth*0.00732)+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=yes,location=no,status=yes');
			//window.opener=null; 
			//window.open("",'_self',""); //新加了一句
			//window.close();
		} 
	</script>  
