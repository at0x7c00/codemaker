<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@taglib prefix="xooxle" uri="/WEB-INF/telJudgment.tld" %>
<html>
	<head>
		<title>连线列表</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
		<script type="text/javascript">
		   function exportExcle(){
		   		loaded1 =true;
		   		jQuery("#listForm").attr("action","${basePath}transition_export.action");
		   		jQuery("#listForm").attr("target","_blank");
		        document.getElementById("listForm").submit();
		   		jQuery("#listForm").attr("action","${basePath}transition_list.action");
		   		jQuery("#listForm").attr("target","_self");
		   }
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>连线列表</td>
	  </tr>
	</table>
	
	<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
<td class="ui-icon ui-icon-carat-2-n-s"></td><td><span><a href="javascript:void(0);" onclick="jQuery('#causeTab').toggle();">查询条件</a> </span></td> <td>&nbsp;</td>
			<td class="ui-icon ui-icon-check"></td>
			<td>
			  <a href="javascript:void(0);" onClick="selectAll('ids');"> 全选</a> 
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="transition_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="transition_delete.action" onClick="return operation('delete','listForm','transition_delete.action','ids')">删除</s:a>
			</td>


		</tr>
	</table>
	
		<FORM ACTION="${basePath}transition_list.action" METHOD="POST" id="listForm"
			NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
<table width="100%" border="0" id="causeTab" style="display:none;"><tr><td width="10%" align="right">名称：</td><td width="40%"><input name="transition.name" id="transition.name" type="text" size="15" class="InputStyle"  value="${transition.name}" /></td><td width="10%" align="right">起始节点：</td><td width="40%"><select name="fromNodeId" id="fromNodeId" class="SelectStyle"/><option value="">请选择</option><c:forEach items="${fromNodeList}" var="vari"><option value="${vari.id}" ${fromNodeId==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td></tr><tr><td width="10%" align="right">目标节点：</td><td width="40%"><select name="toNodeId" id="toNodeId" class="SelectStyle"/><option value="">请选择</option><c:forEach items="${toNodeList}" var="vari"><option value="${vari.id}" ${toNodeId==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td><td width="10%" align="right">判断顺序号：</td><td width="40%"><input name="transition.sortValue" id="transition.sortValue" type="text" size="15" class="InputStyle"  value="${transition.sortValue}" /></td></tr><tr></tr><tr><td colspan="4" align="center"><input type="submit" name="button" id="button" value="查 询"class="ButtonStyle"/><input type="reset" name="button2" id="button2" value="重 置" class="ButtonStyle" /></td></tr></table>
			<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
<td align="center"><INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON"></td><td>名称</td>
<td>条件</td>
<td>目标节点</td>
<td>判断顺序号</td>
<td>操作</td>

				</TR>
                </thead>
                <tbody id="listTBody">
				<c:if test="${empty pageBean.recordList}">
					<tr>
						<td colspan="7">
							<center>
								<font style="color: red;font-weight:bold;font-size:16px">暂无记录 </font>
							</center>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${pageBean.recordList}" var="tempRecord">
					<TR id="listTable">
						<TD width="4%" ALIGN="CENTER">
							<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${tempRecord.id }"
								onClick="checkThis('ids','controlCheckbox');">
						</TD>
<td>${tempRecord.name}</td>
<td>${tempRecord.conditions}</td>
<td>${tempRecord.toNode.name}</td>
<td>${tempRecord.sortValue}</td>
<td  width="10%"><a href="transition_updateUI.action?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}">修改</a>&nbsp;|&nbsp;<a href="transition_delete.action?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}" onclick="return window.confirm('确认要删除该条记录吗?');">删除</a></td>

					</TR>
				</c:forEach>
				</tbody>  
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div  id="InputDetailBar">
				<s:a action="transition_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}transition_addUI.action','');">
				增 加
			</s:a>
				<s:a action="transition_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}transition_delete.action','ids')">
				删 除
			</s:a>
		   </div>
		</FORM>
	</body>
</html>
