<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
${msg }
<script type="text/javascript">
<!--
  var check=1;
 
  function changeStatus(){
       var pw1=document.getElementById("pw1");
      if(check==1){
      	 check=0;
      	 $("#pw1").show();
      }else{ 
      	check=1;
      	$("#pw1").hide();
      }
  }
  
  function checkThisForm(){
   if(!$('initP').checked && $('changePwd').checked){
      if(isEmpty($('oldPwd').value)){
	      alert('请填写旧密码');
	      $('oldPwd').focus();
	      return false;
      }
      if(isEmpty($('password1').value)){
	      alert('新密码不能为空');
	      $('password1').focus();
	      return false;
      }
      if($('password2').value.length<5||$('password2').value.length>12){
          alert('密码必须是5-12位');
          $('password2').focus();
          return false;     
      }
      if($('password1').value!=$('password2').value){
          alert('两次密码输入不一致');
	      $('password2').focus();
	      return false;
      }
   }
   return $("#formID").validationEngine('validate');
 }
 function doChangePwd(ckbValue){
 	if(ckbValue){
 		$('#pw1').show();
 		$('#pwdTr').hide();
 	}else{
 		$('#pw1').hide(); 
 		$('#pwdTr').show();
 	}
 }
//-->
</script>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户信息修改</title>
	</head>

	<body class="ui-widget-content">
		<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>菜单列表</td>
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
	
	
		<form id="formID" name="form" method="post"
			action="${basePath}operator_update.action"
			onsubmit="return checkThisForm();">
			<input type="hidden" name="pageNum" value="0" />
			<input type="hidden" name="id" value="${operator.id }" />
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table class="mainForm" style="width: 50%; margin-top: 0px;"
						align="center">
						<tr>
							<td width="25%" align="right">
								所属部门:
							</td>
							<td width="75%" align="left">
								<select name="deptId" class="InputStyle" id="deptSel" vl="validate[required]">
									<option value="">
										---请选择---
									</option>
									<c:forEach items="${deptList}" var="dept">
										<option value="${dept.id }"
											<c:if test="${operator.dept.id==dept.id }">selected</c:if>>
											${dept.name}
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								职位:
							</td>
							<td width="75%" align="left">
								<select name="jobId" id="jobSel" class="InputStyle"  vl="validate[required]">
									<option value="">
										---请选择---
									</option>
									<c:forEach items="${jobList}" var="job">
										<option value="${job.id}"
											<c:if test="${operator.job.id==job.id }">selected</c:if>>
											${job.name }
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								角色:
							</td>
							<td width="75%" align="left">
								<select name="roleId" id="roleSel" class="InputStyle"  vl="validate[required]">
									<option value="">
										---请选择---
									</option>
									<c:forEach items="${roleList}" var="role">
										<option value="${role.id}"
											<c:if test="${operator.role.id==role.id }">selected</c:if>>
											${role.name }
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>

						<tr>
							<td width="25%" align="right">
								姓名：
							</td>
							<td width="75%" align="left">
								<input type="text" name="operator.name" id="operator.name"
									value="${operator.name }" class="InputStyle"  vl="validate[required]"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								登录账号：
							</td>
							<td width="75%" align="left">
								<input type="text" name="operator.loginName" class="InputStyle"
									value="${operator.loginName }" id="operator.loginName"  vl="validate[required]"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								绑定工号：
							</td>
							<td width="75%" align="left">
								<input type="text" name="operator.workNum" class="InputStyle"
									id="lname" value="${operator.workNum }"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								电话系统登录密码：
							</td>
							<td width="75%" align="left">
								<input type="password" name="operator.workPwd"
									class="InputStyle" value="${operator.workPwd }" />
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								性别：
							</td>
							<td width="75%" align="left">
								<input type="radio" name="operator.sex" value="1"
									<c:if test="${operator.sex==1}">checked="checked"</c:if> />
								男
								<input type="radio" name="operator.sex" value="0"
									<c:if test="${operator.sex==0}">checked="checked"</c:if> />
								女
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								电话1：
							</td>
							<td width="75%" align="left">
								<input type="text" name="operator.phoneNumber1" id="tel1"  vl="validate[custom[phone]]"
									class="InputStyle" value="${operator.phoneNumber1}" />
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								电话2：
							</td>
							<td width="75%" align="left">
								<input type="text" name="operator.phoneNumber2" id="tel2"  vl="validate[custom[phone]]"
									class="InputStyle" value="${operator.phoneNumber2}" />
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								使用默认初始密码:
							</td>
							<td width="75%">
								<input name="initPwd" value="12345" type="checkbox" id="initP"
									<c:if test="${operator.password=='12345'}">
										  checked="checked"
										</c:if>
									onclick="poup2('pw1',!this.checked)" />
								12345&nbsp;&nbsp;
								修改密码<input name="changePwd" value="1" onclick="doChangePwd(this.checked)" type="checkbox" id="changePwd"/>
							</td>
						</tr>
						<tr id="pwdTr">
							<td width="25%" align="right">
								密码：
							</td>
							<td width="75%">
								<input type="password"
									class="InputStyle" readonly="readonly" value="12345"/>
							</td>
						</tr>
						<tbody id="pw1" style="display:none">
						<tr>
							<td width="25%" align="right">
								旧密码：
							</td>
							<td width="75%">
								<input type="password" name="oldPwd"
									class="InputStyle" id="oldPwd" vl="validate[required]"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								新密码：
							</td>
							<td width="75%">
								<input type="password" name="password1"
									class="InputStyle" id="password1"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								新密码确认：
							</td>
							<td width="75%">
								<input type="password" name="password2" id="password2"
									class="InputStyle" vl="validate[required,equals[password1]]"/>
							</td>
						</tr>
						
						<!-- 
						<tr>
							<td width="25%" align="right">
								工单可见范围
							</td>
							<td width="75%">
								<select name="visibility"  class="SelectStyle">
									<option value="self" ${operator.visibility=="self"?"selected":"" }>仅自己</option>
									<option value="private" ${operator.visibility=="private"?"selected":""}>所在部门</option>
									<option value="public" ${operator.visibility=="public"?"selected":""}>所有部门</option>
								</select>
							</td>
						</tr>
						 -->
						 </tbody>
					</table>
				</div>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="submit" value="保 存" class="ButtonStyle" />
				<input type="reset" value="重 置" class="ButtonStyle" />
				<input type="button" value="取 消" onclick="javascript:history.go(-1)"
					class="ButtonStyle" />
			</div>
		</form>
	</body>
</html>

