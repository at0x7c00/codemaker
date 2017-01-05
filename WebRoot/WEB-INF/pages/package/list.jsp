<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<html>
	<head>
		<title>包列表</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			//jQuery.noConflict();
			window.onload = function(){
				jQuery('input[class="Wdate"]').addClass("InputStyle");
				//jQuery('input[type="text"]').attr("disabled",true);
			};
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>包管理</td>
	  </tr>
	</table>
	<table border="0" style="position:absolute;top:0;right:0;">	
	    <tr>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="package_addUI.action" onClick="return operation('add','listForm','${basePath }package_addUI.action','');">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="package_delete.action" onClick="return operation('delete','listForm','package_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table> 
		<FORM ACTION="${basePath }package_list.action" id="listForm"
			METHOD="POST" NAME="dataform" style="margin-top: 0px; margin-bottom: 0">
			<input type="hidden" name="id" value="${id }" />
		   	所属项目：
		     	<select name="projectId" class="SelectStyle" id="projectId" onchange="$('#listForm').submit();">
					<option value="">-全部-</option>
					<c:forEach items="${projectList}" var="project">
						<option value="${project.id}" ${projectId==project.id?'selected':''}>${project.name }</option>
					</c:forEach>
				</select>
			<table class="ui-widget ui-widget-content" width="100%" style="text-align: center;">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<TD width="1%"><INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON"></TD>
					<TD align="left" width="20%">
						所属项目
					</TD>
					<TD align="left">
						名称
					</TD>
					<TD  width="10%">
						操作
					</TD>
				</TR>
				</thead>
				<tbody id="listTBody">
				<c:if test="${empty list}">
					<tr height="25px">
						<td class="bg" colspan="6" align="center">
							<font style="color: gray; font-weight: bold;">暂无记录
							</font>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${list }" var="tmpRecord">
					<tr height="25px" ALIGN="center" >
						<TD width="2%"  ALIGN="CENTER">
							<INPUT TYPE="CHECKBOX" NAME="ids" id="cb${tmpRecord.id }" value="${tmpRecord.id }"
								onClick="checkThis('ids','controlCheckbox');"/>
						</TD>
						<TD align="left">
							${empty tmpRecord.parent?tmpRecord.project.name:'' }
						</TD>
						<TD align="left">
							${tmpRecord.name }
						</TD>
						<TD>
							<table>
						      <tr>
						         <td class="ui-icon ui-icon-pencil"></td>
						         <td>
						         	<s:url value="package_updateUI.action" escapeAmp="false" id="update">
										<s:param name="id">${tmpRecord.id}</s:param>
									</s:url>
									<s:a value="%{update}">修改</s:a>
						         </td>
						         <td>&nbsp;</td>
						         
						         <td class="ui-icon ui-icon-trash"></td>
						         <td>
						         	 <s:url value="package_delete.action" id="delete" escapeAmp="false">
										<s:param name="ids">${tmpRecord.id}</s:param>
									</s:url>
									<s:a value="%{delete}" onclick="window.confirm('确认要删除该记录吗?');">删除</s:a>
						         </td>
						      </tr>
						    </table>
						</TD>
					</TR>
				</c:forEach>
				</tbody>
			</TABLE>
			<div id="InputDetailBar">
				<s:a action="package_addUI.action" 
					onClick="return operation('add','listForm','${basePath }package_addUI.action','');">
					增 加
				</s:a>
				<s:a action="package_delete.action" 
					onClick="return operation('delete','listForm','${basePath}package_delete.action','ids')">
					删 除
				</s:a>
			</div>
		</FORM>
	</body>
</html>
