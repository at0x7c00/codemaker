<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<html>
	<head>
		<title>Project列表</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			//jQuery.noConflict();
			window.onload = function(){
				jQuery('input[class="Wdate"]').addClass("InputStyle");
				//jQuery('input[type="text"]').attr("disabled",true);
			};
			var msg='${msg}';
			$(function(){
				if(msg=='ok'){
					showMsg('代码生成成功！');				
				}else if(msg=='error'){
					showMsg('代码生成时出现异常！');				
				}
			});
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>Project Manage</td>
	  </tr>
	</table>
	<table border="0" style="position:absolute;top:0;right:0;">	
	    <tr>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><s:a action="project_addUI.action" onClick="return operation('add','listForm','${basePath }project_addUI.action','');">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="project_delete.action" onClick="return operation('delete','listForm','project_delete.action','ids')">删除</s:a>
			</td>
		</tr>
	</table> 
		<FORM ACTION="${basePath }project_list.action" id="listForm"
			METHOD="POST" NAME="dataform" style="margin-top: 0px; margin-bottom: 0">
			<input type="hidden" name="id" value="${id }" />
			<input type="hidden" name="pageNum" value="${pageNum }" />
			<s:fielderror />
			<table class="ui-widget ui-widget-content" width="100%" style="text-align: center;">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<TD width="1%"><INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON"></TD>
					<TD align="left">
						名称
					</TD>
					<TD  width="35%">
						操作
					</TD>
				</TR>
				</thead>
				<tbody id="listTBody">
				<c:if test="${empty pageBean.recordList}">
					<tr height="25px">
						<td class="bg" colspan="6" align="center">
							<font style="color: gray; font-weight: bold;">暂无记录
							</font>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${pageBean.recordList }" var="tmpRecord">
					<tr height="25px" ALIGN="center" >
						<TD width="2%"  ALIGN="CENTER">
							<INPUT TYPE="CHECKBOX" NAME="ids" id="cb${tmpRecord.id }" value="${tmpRecord.id }"
								onClick="checkThis('ids','controlCheckbox');"/>
						</TD>
						<TD align="left">
							<b style="font-size:14px;">${tmpRecord.name }</b>
						</TD>
						<TD>
							<table border="0">
						      <tr>
						         <td class="ui-icon ui-icon-script"></td>
						         <td>
						         	<a href="${basePath}project_detail.action?id=${tmpRecord.id}">代码预览</a>
						         </td>
						         <td class="ui-icon ui-icon-play"></td>
						         <td>
						         	<a href="${basePath}project_viewFile.action?id=${tmpRecord.id}">生成代码</a>
						         </td>
						         <td class="ui-icon ui-icon-pin-s"></td>
						         <td>
						            <c:choose>
						            	<c:when test="${PROJECT.id==tmpRecord.id}">
						            		<b style="color:red;">已选定</b>
						         		</c:when>
						         		<c:otherwise>
								         	<a href="${basePath}project_select.action?id=${tmpRecord.id}" title="点击选择工作项目">选定</a>
						         		</c:otherwise>
						            </c:choose>
						         </td>
						         <td class="ui-icon ui-icon-gear"></td>
						         <td>
						         	<s:url value="project_updateUI.action" escapeAmp="false" id="update">
										<s:param name="id">${tmpRecord.id}</s:param>
									</s:url>
									<s:a value="%{update}">设置</s:a>
						         </td>
						         <td>&nbsp;</td>
						         
						         <td class="ui-icon ui-icon-trash"></td>
						         <td>
						         	 <s:url value="project_delete.action" id="delete" escapeAmp="false">
										<s:param name="ids">${tmpRecord.id}</s:param>
									</s:url>
									<s:a value="%{delete}" onclick="return window.confirm('确认要删除该记录吗?');">删除</s:a>
						         </td>
						      </tr>
						    </table>
						</TD>
					</TR>
				</c:forEach>
				</tbody>
			</TABLE>
			<jsp:include page="/WEB-INF/pages/public/pageBar.jsp" />
			<div id="InputDetailBar">
				<s:a action="project_addUI.action" 
					onClick="return operation('add','listForm','${basePath }project_addUI.action','');">
					增 加
				</s:a>
				<s:a action="project_delete.action" 
					onClick="return operation('delete','listForm','${basePath}project_delete.action','ids')">
					删 除
				</s:a>
			</div>
		</FORM>
		<div id="dialog-modal"></div>
	</body>
</html>
