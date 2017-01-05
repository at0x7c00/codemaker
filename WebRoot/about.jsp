<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<META http-equiv=content-type content="text/html; charset=UTF-8">
		<TITLE>关于系统</TITLE>
		<script language=javascript> 
		
</SCRIPT>
</HEAD>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>关于系统</td>
	  </tr>
	</table>
		<DIV class="ui-widget-content" style="margin:20px;height:150px;PADDING-RIGHT: 13px; PADDING-LEFT: 13px; FONT-SIZE: 12px; PADDING-BOTTOM: 13px; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 18px;" id="tipMsg">
		  	<table>
		     	<tr>
		     	   <td class="ui-icon ui-icon-carat-1-e"></td>
		     	   <td>${systemConfig.systemTitle } v2.0</td>
		     	</tr>
		     	<tr>
		     	   <td class="ui-icon ui-icon-carat-1-e"></td>
		     	   <td>电话控件版本：v2.0 Zhou.</td>
		     	</tr>
		     	<tr>
		     	   <td class="ui-icon ui-icon-carat-1-e"></td>
		     	   <td>Client：${systemConfig.systemTitle }</td>
		     	</tr>
		     	<tr>
		     	   <td class="ui-icon ui-icon-carat-1-e"></td>
		     	   <td>版本状态：Stable</td>
		     	</tr>
		     	<tr>
		     	   <td class="ui-icon ui-icon-carat-1-e"></td>
		     	   <td>版权所有&nbsp;<a href='http://www.topvee.com/' target='_blank'>北京护航科技有限公司</a></td>
		     	</tr>
		     </table>
		</DIV>
		
		
	<table width="100%">
	  <tr style="border-bottom:1px solid #ff0000;">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>帮助和支持</td>
	  </tr>
	</table>
		<DIV class="ui-widget-content" style="margin:20px;height:150px;PADDING-RIGHT: 13px; PADDING-LEFT: 13px; FONT-SIZE: 12px; PADDING-BOTTOM: 13px; WIDTH: 100%; COLOR: #1f336b; PADDING-TOP: 18px;" id="tipMsg">
		     <table>
		     	<tr>
		     	   <td class="ui-icon ui-icon-document"></td>
		     	   <td><a href="${basePath}ocx.rar">OCX控件【请右键选择另存为rar格式,或使用迅雷等下载工具下载】</a></td>
		     	</tr>
		     	<tr> 
		     	   <td class="ui-icon ui-icon-document"></td>
		     	   <td><a href="${basePath}help.doc">帮助文档</a></td>
		     	</tr>
		     </table>
		</DIV>
							

</body>
</HTML>