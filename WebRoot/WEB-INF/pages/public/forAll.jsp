<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--JSTL和Struts标签库--%>
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
<%--jquery库--%>
<%@include file="/WEB-INF/pages/public/jqueryLab.jsp"%>
<%--jqueryUI库和css--%>
<%@include file="/WEB-INF/pages/public/jqueryUI.jsp"%>
<%-- 通用js补充 --%>
<script language="javascript" type="text/javascript" src="${basePath}js/common/forAll.js"></script>
<%-- 通用css补充 --%>
<link rel="stylesheet" href="${basePath}style/forAll.css" type="text/css"></link>
