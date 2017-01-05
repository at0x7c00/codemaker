<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加任务节点</title>
		<script type="text/javascript"
			src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		var props ={
		<c:forEach items="${contextEntityList}" var="e" varStatus="s1">
		entity_${e.id}:[<c:forEach items="${e.properties}" var="p" varStatus="s2">{id:'${p.id}',name : '${p.name}',propName:'${p.propName}'}${(not s2.last) ? ',' : ''}</c:forEach>]${(not s1.last) ? ',' : ''}</c:forEach>
		};
		var contextEntityIdValue = '';
		$(function(){
			$("#contextEntityId").change(function(){
				if(contextEntityIdValue!='' && !window.confirm("相关字段操作将丢失，还要继续吗？")){
					$(this).val(contextEntityIdValue);
					return false;
				}
				contextEntityIdValue = $(this).val();
				var ps = props["entity_"+$(this).val()];
				var res = "";
				$("#pTable").find("tr:not(:first-child)").remove();
				for(var i = 0 ;i < ps.length;i++){
					appendPropTr(ps[i],i);
				}
				addChangeEvent();
			});
			
			function appendPropTr(p,index){
				var tr = $("<tr/>");
				if(index%2==0){
					tr.css("background","#f0f0f0")
				}
				tr.css("height","30px");
				var nameTd = $("<td>");
				var td0 =  $("<td>");
				td0.html("<input type='checkbox' class='propSelector' value='"+p.id+"' name='propIds'/>");
				
				var td1 = $("<td>");
				td1.html(getEditableSelectHtml(p));
				
				var td2 = $("<td>");
				td2.html(getAutoValueSelectHtml(p));
				
				var td3 = $("<td>");
				td3.html(getAutoValueTypeSelectHtml(p));
				
				var td4 = $("<td>");
				td4.html(getValueOfExpHtml(p));
				
				nameTd.html(p.name+"("+p.propName+")");
				tr.append(td0).append(nameTd).append(td1).append(td2).append(td3).append(td4).appendTo($("#pTable"));
			}
			
			function getEditableSelectHtml(p){
				return "<select name='editable_"+p.id+"' class='editSelect' style='display:none;'><option value='0'>否</option><option value='1'>是</option></select>";
			}
			function getAutoValueSelectHtml(p){
				return "<select name='autoValue_"+p.id+"' class='autoValueSelect' style='display:none;'><option value='0'>否</option><option value='1'>是</option></select>";
			}
			function getAutoValueTypeSelectHtml(p){
				return "<select name='autoValueType_"+p.id+"' class='autoValueTypeSelect' style='display:none;'>"+
				"<option value='0'>系统时间</option>"+
				"<option value='1'>给定值</option>"+
				"<option value='3'>表达式</option>"+
				"</select>";
			}
			
			function getValueOfExpHtml(p){
				return "<input type='text' name='valueOrExp_"+p.id+"' class='valueOrExp' style='width:90%;display:none;' />";
			}
			
			$("#selectAll").click(function(){
				if($(this).attr("checked")){
					$(".propSelector").attr("checked","true");
					$("#pTable").find(".editSelect").show();
				}else{
					$(".propSelector").removeAttr("checked");
					$("#pTable").find(".editSelect,.autoValueSelect,.autoValueTypeSelect,.valueOrExp").hide();
					$("#pTable").find(".editSelect,.autoValueSelect,.autoValueTypeSelect").val("0");
					$("#pTable").find(".editSelect").hide();
				}
			});
			function addChangeEvent(){
				$(".propSelector").click(function(){
					if($(this).attr("checked")){
						$(this).parent().parent().find(".editSelect").show();
					}else{
						$(this).parent().parent().find("select,input:not(:first-child),.valueOrExp").hide();
						$(this).parent().parent().find(".editSelect,.autoValueSelect,.autoValueTypeSelect").val(0);
					}
				});
				$(".editSelect").change(function(){
				    var _this = $(this);
					if(_this.val()==1){
						$(this).parent().parent().find(".autoValueSelect").show();
					}else{
						$(this).parent().parent().find(".autoValueSelect,.autoValueTypeSelect,.valueOrExp").hide();
						$(this).parent().parent().find(".autoValueSelect,.autoValueTypeSelect").val(0);
					}
				});
				$(".autoValueSelect").change(function(){
				    var _this = $(this);
					if(_this.val()==1){
						$(this).parent().parent().find(".autoValueTypeSelect").show();
					}else{
						$(this).parent().parent().find(".autoValueTypeSelect,.valueOrExp").hide();
						$(this).parent().parent().find(".autoValueTypeSelect").val(0);
					}
				});
				$(".autoValueTypeSelect").change(function(){
				    var _this = $(this);
					if(_this.val()!=0){
						$(this).parent().parent().find(".valueOrExp").show();
					}else{
						$(this).parent().parent().find(".valueOrExp").hide();
					}
				});
			}
		});
		</script>
	</head>
	<body class="ui-widget-content">

		<table width="100%">
			<tr style="border: 1px solid red">
				<td class="ui-icon ui-icon-circle-triangle-e" width="16px">
					&nbsp;
				</td>
				<td>
					添加任务节点
				</td>
			</tr>
		</table>
		<form METHOD="POST" NAME="fmOperator"
			style="margin-top: 15; margin-bottom: 0" id="formID"
			action="${basePath }taskNode_add.action">
			<input type="hidden" name="pageSize" value="${pageSize}">
			<input type="hidden" name="pageNum" value="${pageNum}">
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table align="center" class="mainForm" style="width: 100%;">
						<tr>
							<td width="9%" align="right">
								任务名称：
							</td>
							<td colspan="3">
								<input name="taskNode.name" id="taskNode.name" type="text"
									size="15" class="InputStyle"
									vl="validate[required,maxSize[255]]" style="width:90%"/>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="9%" align="right">
								列表URL：
							</td>
							<td colspan="3">
								<input name="taskNode.listUrl" id="taskNode.listUrl" type="text"
									size="15" class="InputStyle"
									vl="validate[required,maxSize[255]]"  style="width:90%"/>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="9%" align="right">
								处理URL：
							</td>
							<td colspan="3">
								<input name="taskNode.processUrl" id="taskNode.processUrl"
									type="text" size="15" class="InputStyle"
									vl="validate[required,maxSize[255]]"  style="width:90%"/>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="9%" align="right">
								处理提交URL：
							</td>
							<td colspan="3">
								<input name="taskNode.submitUrl" id="taskNode.submitUrl"
									type="text" size="15" class="InputStyle"
									vl="validate[required,maxSize[255]]"  style="width:90%"/>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="9%" align="right">
								操作实体：
							</td>
							<td colspan="3">
								<select name="contextEntityId" id="contextEntityId"
									class="SelectStyle" vl="validate[required]" onchange=""  style="width:90%"/>
								<option value="">
									请选择
								</option>
								<c:forEach items="${contextEntityList}" var="vari">
									<option value="${vari.id}">
										${vari.name}-${vari.fullClassName}
									</option>
								</c:forEach>
								</select>
								&nbsp;
								<font color="red">*</font>
							</td>
						</tr>
						<tr>
							<td width="9%" align="right">
								过滤条件：
							</td>
							<td colspan="3">
								<input name="taskNode.filterCause" id="taskNode.filterCause"
									type="text" size="15" class="InputStyle"
									vl="validate[optional,maxSize[255]]"  style="width:90%"/>
							</td>
						</tr>
						<tr>
							<td width="9%" align="right">
							字段操作：
							</td>
							<td colspan="3">
								<table width="100%" border="0" id="pTable">
									<tr style="font-weight:bold;">
									  <td width="5%">选择</td>
									  <td width="15%">字段</td>
									  <td width="10%">是否可编辑</td>
									  <td width="10%">自动赋值</td>
									  <td width="10%">赋值方式</td>
									  <td >给定值或表达式</td>
									</tr>
								</table>
								&nbsp;<input type="checkbox" id="selectAll"> <label for="selectAll">全选</label>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="保 存" class="ButtonStyle" />
				<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle">
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					onClick="history.back();">
			</div>
		</form>
	</body>
</html>

