<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
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
		  <td>修改菜单</td>
	  </tr>
	</table>
		
					<form id="formID" name="form1" method="post" style="margin-top:0px;"
						action="${basePath}permission_update.action">
						<input type="hidden" name="id" value="${permission.id }" />
						<input type="hidden" name="pageNum" value="${pageNum}" />
						<div class="ItemBlockBorder">
						<table class="mainForm" align="center" style="width:76%;" border="0">
							<tr>
								<td width="10%" align="right">
									菜单名称：
								</td>
								<td>
									<input type="text" name="permission.name" value="<c:out value="${permission.name }"/>" class="InputStyle" style="width:250px" id="permission.name" vl="validate[required]"/>
								</td>
							</tr>
							<tr>
								<td align="right">
									URL：
								</td>
								<td>
									<input type="text" name="permission.url" value="<c:out value="${permission.url}"/>" class="InputStyle"  style="width:300px"/>
								</td>
							</tr>
							<tr>
							<td align="right">
									页面显示：
								</td>
								<td>
									<input type="radio" name="permission.display" value="1" id="yes" ${permission.display==1?'checked':''}/><label for="yes">是</label>
									<input type="radio" name="permission.display" value="0" id="no" ${permission.display==0?'checked':''}/><label for="no">否</label>
								</td>
							</tr>
							<tr>
								<td align="right">
									上级菜单：
								</td>
								<td>
									<select name="parentId"  style="width:250px">
										<option value="-1">
											---请选择---
										</option>
										<c:forEach items="${list}" var="p">
											<option value="${p.id }"
											  <c:if test="${p.id==permission.parent.id}">selected</c:if>
											>
												${p.name }
											</option>
										</c:forEach>
									</select>
									<span onclick="changePicStatus(this);" style="cursor:hand">显示图片</span>
								</td>
							</tr>
								<tr>
								<td colspan="2">
								<div style="display:none" id="pics">
								   <table width="100%">
								      <tr>
										<td align="right">
											菜单图片：
										</td>
										<td>
												<c:forEach items="${icons}" var="icon" varStatus="status">
											    <span style="border:1px dashed #cccccc;padding:1px;width:64px;height:36px;vertical-align: middle;" onmouseover="this.style.background='#cccccc'"  onmouseout="this.style.background='#ffffff'">
													    <img src="${basePath }images/func/${icon}" style="vertical-align:middle;line-height:60px;"/>
													    <input type="radio" name="permission.icon" value="${icon}" <c:if test="${permission.icon==icon}">checked</c:if>>
												</span>&nbsp;
												<c:if test="${status.index%10==9}"><br/><br/></c:if>
												</c:forEach>
										</td>
										</tr>
									</table>
									</div>
									</td>
							</tr>
							</table>
							</div>
								<div id="InputDetailBar">
									<input type="submit" name="submit" value="保 存" class="ButtonStyle"/>
									<input type="reset" name="submit" value="重 置" class="ButtonStyle"/>
									<input type="button" value="取 消" onclick="javascript:history.go(-1)" class="ButtonStyle"/>
								</div>
						<font style="color:red"><s:fielderror/></font>
					</form>
	</body>
</html>
