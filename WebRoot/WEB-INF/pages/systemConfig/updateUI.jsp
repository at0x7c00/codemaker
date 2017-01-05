<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>修改系统设置</title>
	</head>
	<script type="text/javascript">
	function getFileName(fileSel){
	   fileSel.nextSibling.value=fileSel.value;
	}
	</script>
		<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改系统设置</td>
	  </tr>
	</table>
	
		
		<form METHOD="POST" NAME="fmOperator" style="margin-top: 5; margin-bottom: 0" action="${basePath }systemConfig_update.action" enctype="multipart/form-data">
						
		<div class="ItemBlockBorder">
		<div class="ItemBlock">
		
						<table width="100%" class="mainForm">
							 <input type="hidden" name="id" value="${systemConfig.id }"/>
							 <tr>
							   <td width="16%" class="TextStyle" align="right" 
									height="16" valign="middle">
									站点名称：
								</td>
							    <td>
							      <input type="text" class="InputStyle" name="entity.systemTitle" value="${systemConfig.systemTitle}" style="width:500px"/> 
							    </td>
							 </tr>
							 <tr>
							   <td width="16%" class="TextStyle" align="right" 
									height="16" valign="middle">
									副标题：
								</td>
							    <td>
							      <input type="text" class="InputStyle" name="entity.subTitle" value="${systemConfig.subTitle}"  style="width:500px"/> 
							    </td>
							 </tr>
							 <%--
							 <tr>
							   <td width="16%" class="TextStyle" align="right" 
									height="16" valign="middle">
									电话控件显示方式：
								</td>
							    <td>
							       <input type="radio" name="entity.phoneDisplayType" id="showObject" value="0"  <c:if test="${systemConfig.phoneDisplayType==0}">checked</c:if> /><label for="showObject" style="cursor:pointer">直接显示电话控件</label>
							       <input type="radio" name="entity.phoneDisplayType" id="hiddenObject" value="1"  <c:if test="${systemConfig.phoneDisplayType==1}">checked</c:if> /><label for="hiddenObject"  style="cursor:pointer">通过JS调用电话控件</label>
							    </td>
							 </tr>
							 <tr>
								<td width="16%" class="TextStyle" align="right" 
									height="16" valign="middle">
										OCX源码：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<textarea rows="10" cols="50"  class="TextareaStyle" style="width:90%;height:70px" name="entity.ocxObjSource">${systemConfig.ocxObjSource}</textarea>
								</td>
							</tr>
							 <tr>
								<td width="16%" class="TextStyle" align="right" 
									height="16" valign="middle">
										object Id：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<input type="text" name="entity.objId" value="${systemConfig.objId}" class="InputStyle"/>
								</td>
							</tr>
							
							<tr>
								<td width="16%" class="TextStyle" align="right"height="16" valign="middle">
									电话服务器IP地址：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<input type="text" name="entity.phoneServerIp" 
										class="InputStyle" size="50" value="${systemConfig.phoneServerIp}">
								</td>
							</tr>
							<tr>
								<td width="16%" class="TextStyle" align="right"height="16" valign="middle">
									录音文件存放位置：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<input type="text" name="entity.recorderUrl" 
										class="InputStyle" size="50" value="${empty systemConfig.recorderUrl ? 'http://':systemConfig.recorderUrl}">
								</td>
							</tr>
							<tr>
								<td width="16%" class="TextStyle" align="right"height="16" valign="middle">
									录音播放页面：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<select name="entity.selfPlayer">
										<option value="1" ${systemConfig.selfPlayer==1?'selected':'' }>自带播放页面</option>
										<option value="0" ${systemConfig.selfPlayer==0?'selected':'' }>后台VOIP播放页面</option>
									</select>
								</td>
							</tr>
							 --%>
							<tr>
								<td width="16%" class="TextStyle" align="right"height="16" valign="middle">
									系统根路径：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<input type="text" name="entity.projectRootPath"  style="width:500px"
										class="InputStyle" size="50" value="${systemConfig.projectRootPath}">
								</td>
							</tr>
							<tr>
								<td width="16%" class="TextStyle" align="right"height="16" valign="middle">
									版本备注：
								</td>
								<td width="84%" class="TextStyle" align="left" height="16"
									valign="middle">
									<input type="text" name="entity.versionDesc"  style="width:500px"
										class="InputStyle" size="50" value="${systemConfig.versionDesc}">
								</td>
							</tr>
							<tr>
							   <td width="16%" align="right" 
									height="16" valign="middle">
								  启用登录验证码：
								</td>
							    <td>
							      <input type="checkbox" name="useCheck" value="1" ${systemConfig.useCheckCode==1 ? 'checked':''}/>
							    </td>
							 </tr>
						</table>
						</div>
						</div>
						
					<div id="InputDetailBar">
					<input type="submit" name="btnSave" value="保 存" class="ButtonStyle" />
					<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle"
						/>
					<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
						 onClick="goBack()"/>
				    </div>
		</form>
	</body>
</html>
