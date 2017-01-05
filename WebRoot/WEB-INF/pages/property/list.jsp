<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript">
		function deleteProp(){
			if(confirm('确定操作此的记录吗?')){
			   $( "#dialog-modal" ).dialog({
					height: 140,
					modal: true 
				});
			   return true;
			}else{
			   return false; 
			}
		}
		
		function deleteProps(){
			
		}
		
		function updateCanNull(selectorId,chk){
			$.get("${basePath}ajax/updateCanNull.action?propertyId="+selectorId+"&value="+(chk.checked?1:0),function(){
				//alert("修改成功");
			});
		}
		function updateDisplay(selectorId,chk){
			$.get("${basePath}ajax/updateListDisplay.action?propertyId="+selectorId+"&value="+(chk.checked?1:0),function(){
				//alert("修改成功");
			});
		}
		function updateSelect2(selectorId,chk){
			$.get("${basePath}ajax/updateSelect2.action?propertyId="+selectorId+"&value="+(chk.checked?1:0),function(){
				//alert("修改成功");
			});
		}
		
		function startEdit(blankSpanEle,inputSpanId,inputId){
			blankSpanEle.style.display="none";
			document.getElementById(inputSpanId).style.display="block";
			document.getElementById(inputId).style.display="block";
			document.getElementById(inputId).focus();
		}
		
		function endEdit(propId,blankId,inputSpanEle,inputId,hiddenId){
			var inputEle = document.getElementById(inputId);	
			
			inputSpanEle.style.display="none";
			document.getElementById(blankId).style.display="block";
			
			updateValuePath(blankId,propId,inputEle.value,document.getElementById(hiddenId).value);
			
			document.getElementById(hiddenId).value = inputEle.value;
			document.getElementById(blankId).innerHTML = inputEle.value+" ";
				
		}
		function getPackages(projectId){
			$.get("${basePath}ajax/getPackagesByProjectId.action",{id:projectId,timeMark:new Date().getTime()},function(a){
				var packs = eval(a);
				$("#packageId").html("<option value=''>-全部-</option>");
				for(var i = 0;i<packs.length;i++){
					$("#packageId").append($("<option value='"+packs[i].id+"' title='"+packs[i].title+"'>"+packs[i].name+"</option>"));
				}
			});
		}
		function getEntities(packageId){
			$.get("${basePath}ajax/getEntityByPackageId.action",{id:packageId,timeMark:new Date().getTime()},function(a){
				var packs = eval(a);
				$("#entityId").html("<option value=''>-全部-</option>");
				for(var i = 0;i<packs.length;i++){
					$("#entityId").append($("<option value='"+packs[i].id+"' title='"+packs[i].name+"'>"+packs[i].name+"</option>"));
				}
			});
		}
		</script>
		<title>字段自定义</title>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>字段自定义</td>
	  </tr>
	</table>
		<table border="0" style="position:absolute;top:0;right:0;">
	    <tr>
			<td>
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-circle-plus"></td>
			<td>
				<span><a href="property_addUI.action?entityId=${entityId}&pageNum=${pageBean.pageNum}&dataType=${dataType}">添加</a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="property_delete.action" onClick="return operation('delete','listForm','property_delete.action','ids')">删除</s:a>
			</td>
			<td class="ui-icon ui-icon-arrowstop-1-n"></td>
			<td>
				<a href="property_importExcelUI.action">导入Excel </a>
			</td>
			</tr>
	</table>
	
		<form action="${basePath}property_list.action" style="margin-top:0px;"
			id="listForm" method="post">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }"/>
			所属项目：
		     	<select name="projectId" class="SelectStyle" id="projectId" onchange="getPackages(this.value);">
					<option value="">-全部-</option>
					<c:forEach items="${projectList}" var="project">
						<option value="${project.id}" ${projectId==project.id?'selected':''}>${project.name }</option>
					</c:forEach>
				</select>
			所属包：
		     	<select name="packageId" class="SelectStyle" id="packageId" onchange="getEntities(this.value);">
					<option value="">-全部-</option>
					<c:forEach items="${parentPacks}" var="parent">
						<option value="${parent.id}" ${parent.id==packageId?'selected':'' } title="${parent.fullPackageName}">${parent.name }</option>
					</c:forEach>
				</select>
				
			所属实体：
			<select name="entityId" id="entityId" class="SelectStyle">
				<option value="">-全部-</option>
				<c:forEach items="${entityList}" var="entity">
					<option value="${entity.id}" ${entityId==entity.id?'selected':'' } title="${entity.fullClassName}[${entity.name}]">${entity.fullClassName}[${entity.name}]</option>
				</c:forEach>
			</select>
			数据类型：
			<select name="dataType" onchange="selType(this)" id="typeSel" class="InputStyle" vl="validate[required]">
					<option value="">-全部-</option>
					<option value="1" ${dataType==1?'selected':''}>字符串</option>
					<option value="2" ${dataType==2?'selected':''}>整数</option>
					<option value="3" ${dataType==3?'selected':''}>小数</option>
					<option value="4" ${dataType==4?'selected':''}>日期</option>
					<!-- 
					<option value="5" ${dataType==5?'selected':''}>数据字典</option>
					 -->
					<option value="6" ${dataType==6?'selected':''}>复杂类型</option>
					<option value="7" ${dataType==7?'selected':''}>枚举类型</option>
			</select>
			<input type="submit" value="查询"/>
			<table class="ui-widget ui-widget-content" width="100%" border="0">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
								<td width="5%" align="center">
									<input type="checkbox" name="controlCheckbox" onclick="selectAll('ids');" value="ON" />
								</td>
								<td  width="8%">
									&nbsp;显示名称
								</td>
								<td >
									&nbsp;字段名称
								</td>
								<td width="8%">
									&nbsp;字段类型
								</td>
								<td  width="8%">
									&nbsp;允许为空
								</td>
								<td width="8%">
								  &nbsp;验证规则
								</td> 
								<td  width="7%">
									&nbsp;列表显示
								</td>
								<td>
									使用Select2
								</td>
								<td width="5%" align="center">
								   排序
								</td>
								<td  width="10%">
									&nbsp;操作
								</td>
							</tr>
							</thead>
							<tbody id="listTBody">
							<c:if test="${empty pageBean.recordList}">
								<tr height="25px">
									<td  colspan="10" align="center">
										<font style="color: gray"><b>暂无数据</b>
										</font>
									</td>
								</tr>
							</c:if>
							<c:forEach items="${pageBean.recordList }" var="property">
								<tr height="25px">
									<TD width="2%"  ALIGN="CENTER">
										<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${property.id }"
											onClick="checkThis('ids','controlCheckbox');"/>
									</TD>
									<td>
										<c:out value="${property.name }"/>
									</td>
									<td>
										<font color="gray">[ ${property.entity.fullClassName } ]</font>${property.propName}
									</td>
									<td>
										<c:choose>
										  <c:when test="${property.dataType==1}">
										  字符串
										  <c:choose>
										  	<c:when test="${property.isTextStringType==1}">(Text)</c:when>
										  	<c:otherwise>
										  (${property.length })
										  	</c:otherwise>
										  </c:choose>
										  </c:when>
										  <c:when test="${property.dataType==2}">
										  整数
										  </c:when>
										  <c:when test="${property.dataType==3}">
										  小数
										  </c:when>
										  <c:when test="${property.dataType==4}">
										  <span title="格式:${property.timeFormat }">日期</span>
										  </c:when>
										  <c:when test="${property.dataType==5}">
										  数据字典
										  </c:when>
										  <c:when test="${property.dataType==6}">
										  <span title="${property.relationType==1?'一对一':property.relationType==2?'多对多':property.relationType==3?'一对多':property.relationType==4?'多对一':'未知'}--&gt;${property.complexEntity.fullClassName}">复杂类型</span>
										  </c:when>
										  <c:when test="${property.dataType==7}">
										  枚举类型
										  </c:when>
										</c:choose> 
									</td>
									<td align="center">
										 <input type="checkbox" ${property.canNull==1?'checked=checked':''} onclick="updateCanNull(${property.id},this);"/>
									</td>
									<td align="center">
									    ${property.validateType==0?'无':property.validateType==1?'数字':property.validateType==2?'邮箱地址':'无' }
									</td>
									<td align="center">
										 <input type="checkbox"  ${property.display==1?'checked=checked':''} onclick="updateDisplay(${property.id},this);"/>
									</td>
									<td align="center">
										<input type="checkbox" ${property.select2==1?'checked=checked':''} onclick="updateSelect2(${property.id},this);"/>
									</td>
									<td align="center">
									  ${property.sortValue}
									</td>
									<td>
										&nbsp;<span class="ui-icon ui-icon-pencil" style="display:inline"></span>
										<a href="${basePath}property_updateUI.action?id=${property.id}&pageNum=${pageBean.pageNum}&dataType=${property.dataType}">修改</a>
										 |
									    <span class="ui-icon ui-icon-trash" style="display:inline"></span>
										<a href="property_delete.action?ids=${property.id}&pageNum=${pageNum}&dataType=${property.dataType}" onclick="return deleteProp()">删除</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
			</table>  
			<jsp:include page="/WEB-INF/pages/public/pageBar.jsp" />
	</form>
	<div id="dialog-modal" title="提示" style="display:none;width:90%">
			数据库更新可能需要一段时间，请稍后...
	</div>
	</body>
</html>