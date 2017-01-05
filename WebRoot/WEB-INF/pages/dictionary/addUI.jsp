<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加数据字典</title>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加数据字典</td>
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
	
		<form METHOD="POST" NAME="fmOperator" id="formID" style="margin-top: 15; margin-bottom: 0" action="${basePath}dictionary_add.action">
			<div class="ItemBlockBorder">
						<table width="100%" class="mainForm">
							<tr>
								<td width="16%" class="TextStyle" align="right" 
									 valign="middle">
									名称：
								</td>
								<td width="84%" class="TextStyle" align="left" 
									valign="middle">
									<input type="text" name="dic.title" maxlength="100" class="InputStyle"
										 size="50" value="" id="areaName" vl="validate[required]">
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
							<td width="16%" class="TextStyle" align="right" 
									 valign="middle">
									编号：
								</td>
								<td width="84%" class="TextStyle" align="left" 
									valign="middle">
									<input type="text" name="dic.prefix" class="InputStyle" vl="validate[required]" value="" id="number"/>
									<font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td width="16%" class="TextStyle" align="right" 
									 valign="middle">
									描述：
								</td>
								<td width="84%" class="TextStyle" align="left" 
									valign="middle">
									<textarea rows="4" cols="10" name="dic.description" class="TextareaStyle"></textarea>
								</td>
							</tr>	
						</table>
						</div>
						<div id="InputDetailBar">	
							
			<input type="submit" name="btnSave" value="添加" class="ButtonStyle" onClick="save()" />
			<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle"/>
			<input type="button" name="btnBack" value="返 回" class="ButtonStyle" onClick="goBack();"/>  
			</div>
			
			<input type="hidden" name="tmp" value="1">
			<input type="hidden" name="page" value="1">
			<input type="hidden" name="SelID" value="2">
			
		</form>
		
	</body>
</html>

