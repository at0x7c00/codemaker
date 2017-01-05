<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>系统基本信息</title>
	</head>
		<body class="ui-widget-content">
	
	<table width="100%" border="0">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>基本设置</td>
	  </tr>
	</table>
	
		<DIV  class="ui-widget-content" id="tipMsg">
		   <table width="100%">
		     <tr>
		       <td width="15%" align="right"><b>站点名称:</b></td>
		       <td>
		       		<c:choose>
					  <c:when test="${not empty systemConfig.systemTitle}">${systemConfig.systemTitle }</c:when>
					  <c:when test="${empty systemConfig.systemTitle}">未设置</c:when>
					</c:choose>
		       </td>
		     </tr>
		     <tr>
		       <td width="15%" align="right"><b>副名称:</b></td>
		       <td>
		       		<c:choose>
					  <c:when test="${not empty systemConfig.subTitle}">${systemConfig.subTitle }</c:when>
					  <c:when test="${empty systemConfig.subTitle}">未设置</c:when>
					</c:choose>
		       </td>
		     </tr>
		     <tr >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						系统根路径:
					</TD> 
					<TD >
					 <c:choose>
					  <c:when test="${empty systemConfig.projectRootPath}"><font color="red">未设置</font></c:when>
					  <c:otherwise>
					     <c:out value="${systemConfig.projectRootPath}"></c:out>
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
		     <TR>
		     <tr >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						版本备注:
					</TD>
					<TD >
					 <c:choose>
					  <c:when test="${empty systemConfig.versionDesc}"><font color="red">未设置</font></c:when>
					  <c:otherwise>
					     <c:out value="${systemConfig.versionDesc}"></c:out>
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
		     <TR>
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						启用登录验证码:
					</TD>
					<TD >
					   <c:if test="${systemConfig.useCheckCode==1}">启用</c:if>
					   <c:if test="${systemConfig.useCheckCode==0}">停用</c:if>
					</TD>
				   </TR>
		   </table>
		   
		</DIV>
		
	
			<%--
		<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>电话控件</td>
	  </tr>
	</table>
	
		<FORM ACTION="${basePath }area_list.action" id="listForm"
			METHOD="POST" NAME="dataform"
			style="margin-top: 0px; margin-bottom: 0">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }">
			<DIV  class="ui-widget-content" id="tipMsg">
			<table width="100%">
		            <tr  >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						电话控件显示方式:
					</TD>
					<TD >
					<c:choose>
					  <c:when test="${systemConfig.phoneDisplayType==0}">直接显示控件</c:when>
					  <c:when test="${systemConfig.phoneDisplayType==1}">通过JS调用控件</c:when>
					</c:choose>
					</TD>
				   </TR>
		            <tr>
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						ocx源码:
					</TD>
					<TD>
					<c:choose>
					  <c:when test="${empty systemConfig.ocxObjSource}"><font color="red">未设置</font></c:when>
					  <c:otherwise>
					     <c:out value="${systemConfig.ocxObjSource}"></c:out>
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
		            <tr  >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						电话控件object ID:
					</TD>
					<TD >
					<c:choose>
					  <c:when test="${empty systemConfig.objId}"><font color="red">未设置</font></c:when>
					  <c:otherwise>
					     <c:out value="${systemConfig.objId}"></c:out>
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
		            <tr  >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						电话服务器IP地址:
					</TD>
					<TD >
					<c:choose>
					  <c:when test="${empty systemConfig.phoneServerIp}"><font color="red">未设置</font></c:when>
					  <c:otherwise>
					     <c:out value="${systemConfig.phoneServerIp}"></c:out>
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
		            <tr  >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						录音文件存放位置:
					</TD>
					<TD >
					 <c:choose>
					  <c:when test="${empty systemConfig.recorderUrl}"><font color="red">未设置</font></c:when>
					  <c:otherwise>
					     <c:out value="${systemConfig.recorderUrl}"></c:out>
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
		            <tr  >
					<TD  width="15%" style="text-align:right;font-weight:bold;">
						录音文件播放:
					</TD>
					<TD >
					 <c:choose>
					  <c:when test="${systemConfig.selfPlayer==1}">自带播放页面</c:when>
					  <c:otherwise>
					     后台VOIP播放页面
					  </c:otherwise>
					</c:choose>
					</TD>
				   </TR>
				</thead>
			</TABLE>
			</DIV>
				    --%>
			<div id="InputDetailBar">
				<s:a action="systemConfig_updateUI.action" cssClass="ButtonStyle"
					onClick="return operation('update','listForm','${basePath }systemConfig_updateUI.action','');">
					修 改 
				</s:a>
			</div>
		</FORM>
		
	</body>
</html>
