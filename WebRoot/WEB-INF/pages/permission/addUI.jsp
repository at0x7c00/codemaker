<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>菜单管理</title>
		<script type="text/javascript">
		    function changePicStatus(xx){
		      var picBox=document.getElementById('pics');
		      if(picBox.style.display=="none"){
		          picBox.style.display="block";
		          xx.innerHTML="隐藏";
		      }else{ 
		      	  picBox.style.display="none";
		          xx.innerHTML="显示图片";
		      }
		    }
		</script>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加菜单</td>
	  </tr>
	</table>
	
		<form id="formID" method="post" action="${basePath}permission_add.action">
		<input type="hidden" name="pageNum" value="${pageNum}"/>
		<div class="ItemBlockBorder">
			<table class="mainForm" align="center" style="width:76%;text-align: left;">
							<tr>
								<td width="10%" align="right">
									菜单名称： 
								</td>
								<td>
								   <input type="text" name="permission.name" value="${permission.name }"  class="InputStyle" id="permission.name" vl="validate[required]"/>
							</tr>
							<tr>
								<td align="right">
									URL：
								</td>
								<td>
									<input type="text" name="permission.url" value="${permission.url}"  class="InputStyle" style="width:300px"/>
							</tr>
							<tr>
								<td align="right">
									页面显示：
								</td>
								<td>
									<input type="radio" name="permission.display" value="1" id="yes" checked="checked"/><label for="yes">是</label>
									<input type="radio" name="permission.display" value="0" id="no"/><label for="no">否</label>
							</tr>
							<tr>
								<td align="right">
									上级菜单：
								</td>
								<td>
									<select name="parentId"  style="width:250px" class="SelectStyle">
										<option value="-1">
											---请选择---
										</option>
										<s:iterator value="list" var="dept">
											<option value="<s:property value='#dept.id'/>">
												<s:property value="#dept.name" escape="false"/>
											</option>
										</s:iterator>
									</select>
									<span onclick="changePicStatus(this);" style="cursor:hand">显示图片</span>
								</td>
							</tr>
								<tr>
								<td colspan="2">
								<div style="display:none" id="pics">
								   <table width="100%">
								      <tr>
								         <td  align="right">
												菜单图片：
											</td>
											<td>
													<c:forEach items="${icons}" var="icon" varStatus="status">
												    <span style="border:1px dashed #cccccc;padding:1px;width:64px;height:36px;vertical-align: middle;" onmouseover="this.style.background='#cccccc'"  onmouseout="this.style.background='#ffffff'">
														    <img src="${basePath }images/func/${icon}" style="vertical-align:middle;line-height:60px;"/>
														    <input type="radio" name="permission.icon" value="${icon}">
													</span>&nbsp;
													<c:if test="${status.index%10==9}"><br/><br/></c:if>
													</c:forEach>
											</td>
								      </tr>
								   </table>
								   </div>
								</td>
								</tr>
								
								<tr> 
						<td align="right"><input name="checkRepeat" id="checkRepeat" value="1" type="checkbox" onclick="$('#ci').toggle();"/></td>
						<td><label for="checkRepeat">同时添加子菜单</label></td>
					</tr>
					<tr id="ci" style="display:none;">
					    <td align="right">
					       选择子菜单项：
					    </td>
					    <td>
					       <div id="cichk">
								<input type="checkbox" name="optionalIds" value="0" id="chk_0"/><label for="chk_0">详情</label>
								<input type="checkbox" name="optionalIds" value="1" id="chk_1"/><label for="chk_1">添加</label>
								<input type="checkbox" name="optionalIds" value="2" id="chk_2"/><label for="chk_2">修改</label>
								<input type="checkbox" name="optionalIds" value="3" id="chk_3"/><label for="chk_3">删除</label>
								<input type="checkbox" name="optionalIds" value="4" id="chk_4"/><label for="chk_4">导入</label>
								<input type="checkbox" name="optionalIds" value="5" id="chk_5"/><label for="chk_5">导出</label>
					       </div>
					    </td>
					</tr>
						</table>
						</div>
								<div id="InputDetailBar">
									<input type="submit" name="submit" value="提 交" class="ButtonStyle"/>
									<input type="reset" name="submit" value="重 置" class="ButtonStyle"/>
									<input type="button" value="取 消" onclick="javascript:window.history.back()" class="ButtonStyle"/>
								</div>
						<font style="color:red"><s:fielderror/></font>
					</form>
	</body>
</html>
