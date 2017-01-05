 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改数据字典详情</td>
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
	
		<form METHOD="POST" NAME="fmOperator" id="formID" style="margin-top: 15; margin-bottom: 0" action="${basePath}dictionaryDetail_update.action" onsubmit="return checkThisForm();">
		<input type="hidden" name="parentId" value="${parentId}"/>
		<input type="hidden" name="pageNum" value="${pageNum}"/>
		<input type="hidden" name="id" value="${id}"/>
			<div class="ItemBlockBorder">
						<table width="100%" class="mainForm">
							<tr>
								<td width="16%" class="TextStyle" align="right" 
									 valign="middle">
									名称：
								</td>
								<td width="84%" class="TextStyle" align="left" 
									valign="middle">
									<input type="text" name="dicDetail.name" maxlength="100" class="InputStyle"
										 size="50" value="${dicDetail.name}" id="name" vl="validate[required]">
										 <font color="red">*</font>
								</td>
							</tr>
							<tr>
							<td width="16%" class="TextStyle" align="right" 
									 valign="middle">
									值：
								</td>
								<td width="84%" class="TextStyle" align="left" 
									valign="middle">
									<input type="text" name="dicDetail.value" class="InputStyle" value="${dicDetail.value}" id="number"/>
								</td>
							</tr>
							<td width="16%" class="TextStyle" align="right" 
									 valign="middle">
									排序：
								</td>
								<td width="84%" class="TextStyle" align="left" 
									valign="middle">
									<input type="text" name="dicDetail.sort" class="InputStyle" vl="validate[custom[number]]" value="${dicDetail.sort}" id="sort"/>
								</td>
							</tr>
						</table>
						</div>
						<div id="InputDetailBar">	
						<input type="submit" name="btnSave" value="修 改" class="ButtonStyle"/>
						<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle"/>
						<input type="button" name="btnBack" value="返 回" class="ButtonStyle" onClick="goBack();"/>  
						</div>
		</form>
		
	</body>
</html>

