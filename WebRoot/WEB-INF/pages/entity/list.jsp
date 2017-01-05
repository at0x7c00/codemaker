<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<html>
	<head>
		<title>实体列表</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
			//jQuery.noConflict();
			window.onload = function(){
				jQuery('input[class="Wdate"]').addClass("InputStyle");
				//jQuery('input[type="text"]').attr("disabled",true);
			};
			function getPackages(projectId){
				$.get("${basePath}ajax/getPackagesByProjectId.action",{id:projectId,timeMark:new Date().getTime()},function(a){
					var packs = eval(a);
					$("#packageId").html("<option value=''>-全部-</option>");
					for(var i = 0;i<packs.length;i++){
						$("#packageId").append($("<option value='"+packs[i].id+"'>"+packs[i].name+"</option>"));
					}
				});
			}
			function getEntitys(packageId){
				$.get("${basePath}ajax/getEntityByPackageId.action",{id:packageId,timeMark:new Date().getTime()},function(a){
					var entities = eval(a);
					$("#packageId").html("<option value=''>-全部-</option>");
					for(var i = 0;i<entities.length;i++){
						$("#packageId").append($("<option value='"+entities[i].id+"'>"+packs[i].name+"</option>"));
					}
				});
			}
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>实体管理</td>
	  </tr>
	</table>
	<table border="0" style="position:absolute;top:0;right:0;">	
	    <tr>
				<td>&nbsp;</td>
				<td class="ui-icon ui-icon-circle-plus"></td>
				<td>
					<span><s:a action="entity_addUI.action" onClick="return operation('add','listForm','${basePath }entity_addUI.action','');">添加</s:a></span> 
				</td> 
				<td>&nbsp;</td>
				<td class="ui-icon ui-icon-trash"></td>
				<td>
				 <s:a href="entity_delete.action" onClick="return operation('delete','listForm','entity_delete.action','ids')">删除</s:a>
				</td>
				<td>
					&nbsp;
				</td>
				<td class="ui-icon ui-icon-arrowstop-1-n"></td>
				<td>
					<a href="entity_importExcelUI.action">导入Excel </a>
				</td>
			</tr>
	</table> 
		<FORM ACTION="${basePath }entity_list.action" id="listForm"
			METHOD="POST" NAME="dataform" style="margin-top: 0px; margin-bottom: 0">
			<input type="hidden" name="id" value="${id }" />
			<input type="hidden" name="pageNum" value="${pageNum }" />
			所属项目：
		     	<select name="projectId" class="SelectStyle" id="projectId" onchange="getPackages(this.value);">
					<option value="">-全部-</option>
					<c:forEach items="${projectList}" var="project">
						<option value="${project.id}" ${projectId==project.id?'selected':''}>${project.name }</option>
					</c:forEach>
				</select>
			所属包：
		     	<select name="packageId" class="SelectStyle" id="packageId" >
					<option value="">-请选择-</option>
					<c:forEach items="${parentPacks}" var="parent">
						<option value="${parent.id}" ${parent.id==packageId?'selected':'' }>${parent.name }</option>
					</c:forEach>
				</select>
				<input type="submit" value="查询"/>
			<table class="ui-widget ui-widget-content" width="100%" style="text-align: center;">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
					<TD width="1%"><INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON"></TD>
					<TD align="left" width="20%">
						名称
					</TD>
					<TD align="left">
						类全称
					</TD>
					<TD  width="10%">
						生成历史记录
					</TD>
					<TD  width="10%">
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
							${tmpRecord.name}
						</TD>
						<TD align="left">
							<font style="font-weight:bold;">[&nbsp;${tmpRecord.fullClassName }&nbsp;]</font>
						</TD>
						<TD align="center">
							<input type="checkbox" class="historyablechks" entity-id="${tmpRecord.id }" ${tmpRecord.historyable==1 ? 'checked' : '' }/>
						</TD>
						<TD>
							<table>
						      <tr>
						         <td class="ui-icon ui-icon-pencil"></td>
						         <td>
						         	<s:url value="entity_updateUI.action" escapeAmp="false" id="update">
										<s:param name="id">${tmpRecord.id}</s:param>
									</s:url>
									<s:a value="%{update}">修改</s:a>
						         </td>
						         <td>&nbsp;</td>
						         
						         <td class="ui-icon ui-icon-trash"></td>
						         <td>
						         	 <s:url value="entity_delete.action" id="delete" escapeAmp="false">
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
				<s:a action="entity_addUI.action" 
					onClick="return operation('add','listForm','${basePath }entity_addUI.action','');">
					增 加
				</s:a>
				<s:a action="entity_delete.action" 
					onClick="return operation('delete','listForm','${basePath}entity_delete.action','ids')">
					删 除
				</s:a>
			</div>
		</FORM>
	</body>
	<script type="text/javascript">
		$(function(){
			$(".historyablechks").click(function(){
				var val = $(this).get(0).checked ? 1 : 0;
				$.get('${basePath}ajax/updateHistoryable.action?id=' + $(this).attr("entity-id") + "&value=" + val,function(d){
				});
				
				
			});
			
		});
	</script>
</html>
