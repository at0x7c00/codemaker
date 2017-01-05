<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>修改字段</title> 
		<script type="text/javascript">
			function selType(selObj){
				var selOp = selObj.options[selObj.selectedIndex];
				if(selObj.value ==1){
					$('#lengthTr').show();
					$('#dicTr').hide();
					$('#complexObject').hide();
					$('#dateFormat').hide();
				}else if(selObj.value ==5){
					$('#lengthTr').hide();
					$('#dicTr').show();
					$('#complexObject').hide();
					$('#dateFormat').hide();
				}else if(selObj.value==4){
					$('#lengthTr').hide();
					$('#dicTr').hide();
					$('#complexObject').hide();
					$('#dateFormat').show();
				}else if(selObj.value==6){
					$('#lengthTr').hide();
					$('#dicTr').hide();
					$('#complexObject').show();
					$('#dateFormat').hide();
				}else if(selObj.value==7){
					$('#lengthTr').hide();
					$('#dicTr').hide();
					$('#dateFormat').hide();
					$('#complexObject').show();
					$('#enumValue').hide();
				}else if(selObj.value==3){
					$('#lengthTr').hide();
					$('#dicTr').hide();
					$('#complexObject').hide();
					$('#dateFormat').hide();
					$('#numberType').show();
				}else{
					$('#lengthTr').hide();
					$('#dicTr').hide();
					$('#complexObject').hide();
					$('#dateFormat').hide();
				}
			}
		</script>
		<style type="text/css">
		.InputStyle{
			width:350px;
			height:24px;
			font-size:13px;
		}
		.SelectStyle{
			width:350px;
		}
		</style>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改字段
		  <c:if test="${not empty entityy}">
		  ${entityy.fullClassName}
		  </c:if>
		  -${PROJECT.name}
		  </td>
	  </tr>
	</table>
    <table border="0" style="position:absolute;top:0;right:5;">
	    <tr>
			<td class="ui-icon ui-icon-arrowreturnthick-1-w"></td>
			<td>
				<span><a href="javascript:void(0);" onclick="history.back();">返回<a></span> 
			</td> 
		</tr>
	</table>
		<form action="${basePath }property_update.action" method="post" id="formID">
			<div class="ItemBlockBorder">
			<input type="hidden" name="id" value="${property.id }"/>
			<input type="hidden" name="pageNum" value="${pageNum}"/>
			<input type="hidden" name="dataType" value="${dataType}"/>
			<table width="100%" border="0">
				<tr>
					<td width="20%" align="right">
						所属实体:
					</td>
					<td>
						<select name="entityId" id="typeSel" title="该该字段所属的实体" vl="validate[required]">
								<option value="">-请选择-</option>
								<c:forEach items="${entityList}" var="entity">
									<option value="${entity.id }" ${property.entity.id==entity.id?'selected':''}>${entity.fullClassName} - [${entity.name}]</option>
								</c:forEach>
						</select> <font color="red">*</font>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						字段名称:
					</td>
					<td>
						<input type="text" name="property.propName" class="InputStyle" id="fieldName" vl="validate[required,maxSize[50]]"
							value="<c:out value="${property.propName }"/>" placeholder="Java类字段名称"/><font color="red"> * ${msg }</font>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						列名称:
					</td>
					<td>
						<input type="text" name="property.columnName" class="InputStyle" id="fieldName" 
							value="<c:out value="${property.columnName }"/>" title="数据库表列名称" placeholder="数据库表列名称"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						显示名称:
					</td>
					<td>
						<input type="text" name="property.name" class="InputStyle" id="fieldNameCN" vl="validate[required]"
							value="${property.name }" title="页面中显示的名称" placeholder="页面中显示的名称"/><font color="red"> *</font>
					</td>
				</tr>
				<tr>
					<td align="right">
						主键:
					</td>
					<td>
						<select id="property.isId" name="property.isId" class="SelectStyle" >
							<option value="0" ${property.isId==0?'selected':''}>否</option>
							<option value="1" ${property.isId==1?'selected':''}>是</option>
						</select><font color="red"> *</font>
					</td>
				</tr>
				<tr>
					<td align="right">
						添加修改页显示方式:
					</td>
					<td>
					    <select class="SelectStyle" name="property.isTotalRow">
				   			       		<option value="0" ${property.isTotalRow==0?'selected':''}>半行</option>
				   			       		<option value="1" ${property.isTotalRow==1?'selected':''}>整行</option>
				   			       </select>
					</td>
				</tr>
				<tr>
					<td align="right">
						数据类型:
					</td>
					<td>
						<select name="property.dataType" onchange="selType(this)" title="字段在页面中的显示方式"  style="font-size:16px;" id="typeSel" class="InputStyle" vl="validate[required]">
								<option value="">-请选择-</option>
								<option value="1" ${property.dataType==1?'selected':''}>字符串</option>
								<option value="2" ${property.dataType==2?'selected':''}>整数</option>
								<option value="3" ${property.dataType==3?'selected':''}>小数</option>
								<option value="4" ${property.dataType==4?'selected':''}>日期</option>
								<!-- 
								<option value="5" ${property.dataType==5?'selected':''}>数据字典</option>
								 -->
								<option value="6" ${property.dataType==6?'selected':''}>复杂类型</option>
								<option value="7" ${property.dataType==7?'selected':''}>枚举类型</option>
						</select> <font color="red">*</font>
					</td>
				</tr>
				<!-- 
				<tr id="enumValue" <c:if test="${property.dataType!=7}">style="display: none;"</c:if>>
					<td align="right">
						枚举值:
					</td>
					<td>
						<input name="property.enumValue" id="property.enumValue" value="${property.enumValue}" class="InputStyle" vl="validate[required]"/>
						&nbsp;<font style="color:red">*</font>
						&nbsp;<font style="color:gray">例如：1=投诉;2=咨询;3=报修</font>
					</td>
				</tr>
				 -->
				<tr id="lengthTr">
				    <td colspan="2">
				    	<table width="100%">
				    	<tr>
				   				<td width="20%" align="right">
									Text类型：
								</td>
								<td>
									<select name="property.isTextStringType" class="SelectStyle" title="对于字符串类型而言，设定是否为Text类型">
										<option value="0" ${property.isTextStringType==0 ?'selected':''}>否</option>
										<option value="1" ${property.isTextStringType==1 ?'selected':''}>是</option>
									</select>
								</td>
				   			</tr>
				    	<tr>
				    	   <td width="20%" align="right">
								字符串字段长度:
							</td>
							<td>
								<input type="text" name="property.length" id="fieldLength" class="InputStyle" value="${property.length }" vl="validate[required,custom[number]]" /><font color="red"> *</font>
							</td>
				    	   <tr>
				   			    <td align="right">
				   			       显示方式：
				   			    </td>
				   			    <td>
				   			       <select class="SelectStyle" name="property.isTextArea">
				   			       		<option value="0" ${property.isTextArea==0?'selected':''}>文本框</option>
				   			       		<option value="1" ${property.isTextArea==1?'selected':''}>文本域</option>
				   			       </select>
				   			    </td>
				   			</tr>
				   			<tr>
				   			   <td align="right">行：</td>
				   			   <td>
				   			     <input class="InputStyle" name="property.row" value="${property.row }"/>
				   			   </td>
				   			</tr>
				   			<tr>
				   			   <td align="right">列：</td>
				   			   <td>
				   			     <input class="InputStyle" name="property.col" value="${property.col }"/>
				   			   </td>
				   			</tr>
				    	</table>
				    </td>
				</tr>
				<%--
				<tr id="dicTr" <c:if test="${property.dataType==5}"></c:if> <c:if test="${property.dataType!=5}">style="display: none;"</c:if>>
					<td width="20%" align="right">
						字典编号：
					</td>
					<td>
						<input type="text" class="InputStyle" name="property.dictFix" value="${property.dictFix}" vl="validate[required]" id="property.dictFix"/>&nbsp;<font color="red">*</font>
					</td>
				</tr>
				 --%>
				<tr id="dateFormat" <c:if test="${property.dataType!=4}">style="display: none;"</c:if>>
				    <td width="100%" colspan="2">
				    	<table width="100%" border="0">
				   			<tr>
				   				<td width="20%" align="right">
									日期格式:
								</td>
								<td>
								<input type="text" name="property.timeFormat" id="timeFormat" class="InputStyle" value="${property.timeFormat }" vl="validate[required]" /><font color="red"> *</font>
								</td>
				   			</tr>
				   			<tr>
				   				<td width="20%" align="right">
									数据库类型:
								</td>
								<td>
								<select  class="SelectStyle" name="property.dateType">
									<option value="datetime" ${property.dateType eq 'datetime' ?'selected':''}>DATETIME</option>
									<option value="date" ${property.dateType eq 'date' ?'selected':''}>DATE</option>
								</select>
								<font color="red"> *</font>
								</td>
				   			</tr>
				   			<tr>
				   				<td width="20%" align="right">
									添加时默认系统时间：
								</td>
								<td>
									<select class="SelectStyle" name="property.defaultSysTime">
										<option value="0" ${property.defaultSysTime==0?'selected':''}>否</option>
										<option value="1" ${property.defaultSysTime==1?'selected':''}>是</option>
									</select>
								</td>
				   			</tr>
				   		</table>
				    </td>
				</tr>
				<tr id="numberType" <c:if test="${property.dataType!=3}">style="display:none;"</c:if>>
					<td align="right" width="20%">
					精度：
					</td>
					<td>
						<select class="SelectStyle" name="property.precision">
							<option value="double"  ${property.precision eq 'double' ? 'selected':''}>Double</option>
							<option value="float" ${property.precision eq 'float' ? 'selected':''}>Float</option>
						</select>
					</td>
				</tr>
				<tr id="complexObject" <c:if test="${property.dataType==6}"></c:if> <c:if test="${property.dataType!=6}">style="display: none;"</c:if>>
					<td colspan="2">
					   <table width="100%" border="0">
					      <tr>
					           <td width="20%" align="right">
									关联实体:
								</td>
								<td>
									<select name="complexId" id="typeSel" class="InputStyle" vl="validate[required]">
											<option value="">-请选择-</option>
											<c:forEach items="${entityList}" var="entity">
												<option value="${entity.id }" ${property.complexEntity.id==entity.id?'selected':''}>${entity.fullClassName}</option>
											</c:forEach>
									</select> <font color="red">*</font>
								</td>
					      </tr>
					      <tr>
					           <td width="20%" align="right">
									关系类型:
								</td>
								<td>
									<select name="property.relationType" id="typeSel" class="InputStyle" vl="validate[required]" >
											<option value="">-请选择-</option>
											<option value="1" ${property.relationType==1?'selected':''}>一对一</option>
											<option value="2" ${property.relationType==2?'selected':''}>多对多</option>
											<option value="3" ${property.relationType==3?'selected':''}>一对多</option>
											<option value="4" ${property.relationType==4?'selected':''}>多对一</option>
									</select> <font color="red">*</font>
								</td>
					      </tr>
					      
					       <tr>
					           <td width="20%" align="right">
									维护一对多/多对多关联关系:
								</td>
								<td>
									<select name="property.maintainOneToManyRelation" id="maintainOneToManyRelation" class="InputStyle">
											<option value="1" ${property.maintainOneToManyRelation==1?'selected':''}>是</option>
											<option value="0" ${property.maintainOneToManyRelation==0?'selected':''}>否</option>
									</select>
									<img src="${basePath}images/new.gif" alt="新功能" title="最近更新"/>
								</td>
					      </tr>
					      
					       <tr>
					           <td width="20%" align="right">
									中间表：
								</td>
								<td>
									<input type="text" class="InputStyle" name="property.middletable" value="${property.middletable}"/>
								</td>
					      </tr>
					      <tr>
					         <td align="right">关联关系对方关联字段：</td>
					         <td><input type="text" class="InputStyle" name="property.setKeyCoumn" value="${property.setKeyCoumn}"/></td>
					      </tr>
					      <tr>
					         <td align="right">关系字段：</td>
					         <td>
					         	<select name="property.onlyRelationship" class="SelectStyle"  title="一对多时选择是" placeholder="关系对方关联自己的Java类字段名称" >
					         		<option value="0" ${property.onlyRelationship==0?'selected':''}>否</option>
					         		<option value="1" ${property.onlyRelationship==1?'selected':''}>是</option>
					         	</select>
					         </td>
					      </tr>
					      <tr>
					         <td align="right">value path：</td>
					         <td><input type="text" class="InputStyle" name="property.valuePath" value="${property.valuePath}" placeholder="管理列表中显示复杂类型字段的哪个字段"/></td>
					      </tr>
					      <tr>
					      	<td align="right">使用Select2</td>
					      	<td>
					      		<select name="property.select2">
					      			<option value="0" ${property.select2==0?'selected':'' }>否</option>
					      			<option value="1" ${property.select2==1?'selected':'' }>是</option>
					      		</select>
					      	</td>
					      </tr>
					   </table>
					</td>
				</tr>
				<tr>
					<td align="right">
						允许为空:
					</td>
					<td>
						<input type="checkbox" ${property.canNull==1?'checked':''}   onclick="canNullChange(this);"/>
						<input type="hidden" name="property.canNull" id="canNull" value="${property.canNull}"/> 
						<script type="text/javascript">
						function canNullChange(_this){
							$("#canNull").val($(_this).attr("checked")?1:0);
						}
						</script>
					</td>
				</tr>
				<tr>
					<td align="right">
						不持久化:
					</td>
					<td>
						<input type="checkbox"  ${property.noPersistence==1 ? 'checked':''} onclick="noPersistenceChange(this);" title="表示该字段只是业务显示，不做持久化存储。例如根据“身份证号”生成的“年龄”字段。具体内容需要生成以后自己自己手动补上。"/> 
						<input type="hidden" value="${property.noPersistence}" id="noPersistence"/>
						<script type="text/javascript">
							function noPersistenceChange(_this){
								$("#noPersistence").val($(_this).attr("checked")?1:0);
							}
						</script>
					</td>
				</tr>
				<tr>
					<td align="right">
						文件附件：
					</td>
					<td>
						<input type="checkbox" title="表示该 字段是否是需要上传文件" onclick="changeAttmentTrDisplay(this);" ${property.isAttachement eq 1 ? 'checked':''}/>
						<input type="hidden" name="property.isAttachement" value="${property.isAttachement}" id="isAttachement"/>
						<script type="text/javascript">
							function changeAttmentTrDisplay(_this){
								if($(_this).attr("checked")){
									$("#attachement").show();
									$("#isAttachement").val(1);
								}else{
									$("#attachement").hide();
									$("#isAttachement").val(0);
								}
							}
						</script> 
					</td>
				</tr>
				<tr id="attachement" <c:if test="${empty property.isAttachement or property.isAttachement==0}">style="display:none;"</c:if>>
					<td colspan="2">
						<table width="100%">
							<tr>
								<td align="right">
									文件格式：
								</td>
								<td>
									<select name="property.isPicture" class="SelectStyle">
										<option value="0" ${property.isPicture==0?'selected':''}>普通文件</option>
										<option value="1" ${property.isPicture==1?'selected':''}>图片文件</option>
									</select>
								</td>
							</tr>
							<tr id="attachement">
								<td align="right">
									附件显示方式：
								</td>
								<td>
									<select name="property.attachementDisplayType" class="SelectStyle">
										<option value="fileurl"  ${property.attachementDisplayType eq 'fileurl'?'selected':''}>下载链接</option>
										<option value="img" ${property.attachementDisplayType eq 'img'?'selected':''}>&lt;img&gt;显示图片</option>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
			           <td width="20%" align="right">
							验证规则：
						</td>
						<td>
							<select name="property.validateType" id="typeSel" class="InputStyle" vl="validate[required]">
									<option value="0">-无-</option>
									<option value="1">数字</option>
									<option value="2">邮箱地址</option>
							</select> 
						</td>
			    </tr>
				<tr>
			           <td width="20%" align="right">
							列表显示：
						</td>
						<td>
							<select name="property.display" id="typeSel" class="InputStyle" vl="validate[required]">
									<option value="1" ${property.display==1 ? 'selected':''}>是</option>
									<option value="0" ${property.display==0 ? 'selected':''}>否</option>
							</select> 
						</td>
			    </tr>
			    <tr>
			           <td width="20%" align="right">
							列表查询：
						</td>
						<td>
							<select name="property.forQuery" id="typeSel" class="InputStyle" vl="validate[required]">
									<option value="1" ${property.forQuery==1?'selected':''}>是</option>
									<option value="0" ${property.forQuery==0?'selected':''}>否</option>
							</select>
						</td>
			    </tr>
			    <tr>
			           <td width="20%" align="right">
							列表简略显示长度：
						</td>
						<td>
						  <input type="text" class="InputStyle" name="property.briefLength" value="${property.briefLength}"/>&nbsp;0表示完整显示
						</td>
			    </tr>
				<tr>
			           <td width="20%" align="right">
							排序：
						</td>
						<td>
							<input type="text" name="property.sortValue" id="property.sortValue" class="InputStyle" value="${property.sortValue}" vl="validate[required,custom[number]]" /><font color="red"> *</font>
						</td>
			    </tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" name="submit" value="提 交" class="ButtonStyle"/>
						<input type="reset" name="submit" value="重 置" class="ButtonStyle"/>
						<input type="button" value="取 消" onclick="javascript:history.go(-1)" class="ButtonStyle"/>
					</td>
				</tr>
			</table>
			</div>
		</form>
		<font style="color: red"><s:fielderror/>
		</font>
		
	</body>
</html>
