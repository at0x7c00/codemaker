<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp" %>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>测试</title>
    
        
        <script type="text/javascript">
        jQuery(document).ready(function(){
                // binds form submission and fields to the validation engine
                jQuery("#formID").validationEngine();
            });
        </script>
    <style type="text/css">
       #msgBox{
       		border:1px solid red;
       		width:300px;
       		height:200px;
			position: absolute;
			display: block;
			right: 0;
			bottom: 0;
			background-color: #bcd5e6;
			text-align: center;
       }
       #parent{
       		width: 60%;
			margin: 10px auto;
			padding: 5px;
			border: 1px solid #777;
			background-color: #fbca93;
			text-align: center;
       }
    </style>
  </head>
  
  <body>
    <div id="parent">
	    <div id="msgBox">
	       消息盒子
	    </div>
    </div>
    
    <form action="aa" id="formID">
    		<input name="aaa" id="111" vl="validate[required]"/>
    </form>
  </body>
</html>
