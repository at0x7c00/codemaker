<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@taglib prefix="xooxle" uri="/WEB-INF/telJudgment.tld" %>
<html>
	<head>
		<title>条件列表</title>
		<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<%@include file="/WEB-INF/pages/public/forList.jsp"%>
		<script type="text/javascript">
		   function exportExcle(){
		   		loaded1 =true;
		   		jQuery("#listForm").attr("action","${basePath}condition_export.action");
		   		jQuery("#listForm").attr("target","_blank");
		        document.getElementById("listForm").submit();
		   		jQuery("#listForm").attr("action","${basePath}condition_list.action");
		   		jQuery("#listForm").attr("target","_self");
		   }
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>条件列表</td>
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
				<span><s:a action="condition_addUI.action">添加</s:a></span> 
			</td> 
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-trash"></td>
			<td>
			 <s:a href="condition_delete.action" onClick="return operation('delete','listForm','condition_delete.action','ids')">删除</s:a>
			</td>


		</tr>
	</table>
	
		<FORM ACTION="${basePath}condition_list.action" METHOD="POST" id="listForm"
			NAME="QueryForm">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }" />
<table width="100%" border="0" id="causeTab" style="display:none;"><tr><td width="10%" align="right">字段：</td><td width="40%"><select name="propertyId" id="propertyId" class="SelectStyle"/><option value="">请选择</option><c:forEach items="${propertyList}" var="vari"><option value="${vari.id}" ${propertyId==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td><td width="10%" align="right">参数值：</td><td width="40%"><input name="condition.paramValue" id="condition.paramValue" type="text" size="15" class="InputStyle"  value="${condition.paramValue}" /></td></tr><tr><td width="10%" align="right">比较方式：</td><td width="40%"><select name="condition.compareType" id="compareType" class="SelectStyle"/><option value="0" ${condition.compareType==0?'selected':''}>全部</option><option value="1" ${condition.compareType==1?'selected':''}>等于</option><option value="2" ${condition.compareType==2?'selected':''}>不等于</option><option value="3" ${condition.compareType==3?'selected':''}>大于且小于</option><option value="4" ${condition.compareType==4?'selected':''}>取某些值</option><option value="5" ${condition.compareType==5?'selected':''}>大于</option><option value="6" ${condition.compareType==6?'selected':''}>大于等于</option><option value="7" ${condition.compareType==7?'selected':''}>小于</option><option value="8" ${condition.compareType==8?'selected':''}>小于等于</option><option value="9" ${condition.compareType==9?'selected':''}>为空</option><option value="10" ${condition.compareType==10?'selected':''}>不为空</option></select></td><td width="10%" align="right">连接词：</td><td width="40%"><select name="condition.logicValue" id="logicValue" class="SelectStyle"/><option value="0" ${condition.logicValue==0?'selected':''}>并且</option><option value="1" ${condition.logicValue==1?'selected':''}>或者</option></select></td></tr><tr><td width="10%" align="right">所属连线：</td><td width="40%"><select name="transitionId" id="transitionId" class="SelectStyle"/><option value="">请选择</option><c:forEach items="${transitionList}" var="vari"><option value="${vari.id}" ${transitionId==vari.id?'selected':''}>${vari.name}</option></c:forEach></select></td></tr><tr><td colspan="4" align="center"><input type="submit" name="button" id="button" value="查 询"class="ButtonStyle"/><input type="reset" name="button2" id="button2" value="重 置" class="ButtonStyle" /></td></tr></table>
			<table class="ui-widget ui-widget-content" width="100%">
			<thead>
				<tr class="ui-widget-header" height="30px" id="listTHead">
<td align="center"><INPUT TYPE="CHECKBOX" id="controlCheckbox" onClick="selectAll('ids');" value="ON"></td><td>字段</td>
<td>参数值</td>
<td>比较方式</td>
<td>连接词</td>
<td>所属连线</td>
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
<td>${tempRecord.property.name}</td>
<td>${tempRecord.paramValue}</td>
<td><c:choose><c:when test="${tempRecord.compareType==0}">全部</c:when><c:when test="${tempRecord.compareType==1}">等于</c:when><c:when test="${tempRecord.compareType==2}">不等于</c:when><c:when test="${tempRecord.compareType==3}">大于且小于</c:when><c:when test="${tempRecord.compareType==4}">取某些值</c:when><c:when test="${tempRecord.compareType==5}">大于</c:when><c:when test="${tempRecord.compareType==6}">大于等于</c:when><c:when test="${tempRecord.compareType==7}">小于</c:when><c:when test="${tempRecord.compareType==8}">小于等于</c:when><c:when test="${tempRecord.compareType==9}">为空</c:when><c:when test="${tempRecord.compareType==10}">不为空</c:when></c:choose></td>
<td><c:choose><c:when test="${tempRecord.logicValue==0}">并且</c:when><c:when test="${tempRecord.logicValue==1}">或者</c:when></c:choose></td>
<td>${tempRecord.transition.name}</td>
<td  width="10%"><a href="condition_updateUI.action?id=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}">修改</a>&nbsp;|&nbsp;<a href="condition_delete.action?ids=${tempRecord.id}&pageSize=${pageSize}&pageNum=${pageNum}" onclick="return window.confirm('确认要删除该条记录吗?');">删除</a></td>

					</TR>
				</c:forEach>
				</tbody>  
			</TABLE>
			<%@include file="/WEB-INF/pages/public/pageBar.jsp"%>
			<div  id="InputDetailBar">
				<s:a action="condition_addUI.action" cssClass="ButtonStyle"
					onClick="return operation('add','listForm','${basePath}condition_addUI.action','');">
				增 加
			</s:a>
				<s:a action="condition_delete.action" cssClass="ButtonStyle"
					onClick="return operation('delete','listForm','${basePath}condition_delete.action','ids')">
				删 除
			</s:a>
		   </div>
		</FORM>
	</body>
</html>
