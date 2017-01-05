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
      	 pw1.style.display="block";
      }else{
      	check=1;
      	pw1.style.display="none";
      }
  }
  
  function checkThisForm(){
    
   if($('changePwd').checked){
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
   if($('changeSignPwd').checked){
   	  if(isEmpty($('oldSignPwd').value)){
	      alert('请填写旧签章密码');
	      $('oldSignPwd').focus();
	      return false;
      }
   	  if(isEmpty($('signPwd').value)){
	      alert('新签章密码不能为空');
	      $('signPwd').focus();
	      return false;
      }
      if($('signPwd2').value.length<5||$('signPwd2').value.length>12){
          alert('签章密码必须是5-12位');
          $('signPwd2').focus();
          return false;     
      }
      if($('signPwd').value!=$('signPwd2').value){
          alert('两次签章密码输入不一致');
	      $('signPwd2').focus();
	      return false;
      }
   }
   return true;
}
 function getFileName(fileSel){
   fileSel.nextSibling.value=fileSel.value;
}

function doChangePwd(ckbValue){
 	if(ckbValue){
 		$('#pw1').show();
 		$('#pwdTr').hide();
 		$('#changePwd2').attr('checked',ckbValue);
 	}else{
 		$('#pw1').hide();
 		$('#pwdTr').show();
 		$('#changePwd').attr('checked',ckbValue);
 	}
 }
//-->
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人信息修改</title>
	</head>

		<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>个人信息设置</td>
	  </tr>
	</table>
					<form id="formID" name="form" method="post" action="${basePath}operator_infoSet.action" onsubmit="return checkThisForm();" enctype="multipart/form-data">
						<input type="hidden" name="pageNum" value="0" />
						<input type="hidden" name="id" value="${operator.id }" />
						<div class="ItemBlockBorder">
						<table class="mainForm" style="width:50%;" align="center" width="90%">
							<tr>
								<td width="25%" align="right">
									所属单位：
								</td>
								<td width="75%" align="left" >
									${operator.dept.name}
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									角色：
								</td>
								<td width="75%" align="left" >
									<input type="hidden" name="roleId" value="${operator.role.id }" >
									 ${operator.role.name}
								</td>
							</tr>
                            <tr>
								<td width="25%" align="right">
									职务：
								</td>
								<td width="75%" align="left" >
									<input type="hidden" name="jobId" value="${operator.job.id }" >
									 ${operator.job.name}
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									姓名：
								</td>
								<td width="75%" align="left">
									<input type="text" name="operator.name" id="name" value="${operator.name }" class="InputStyle" />
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									登录账号：
								</td>
								<td width="75%" align="left">
									<input type="text" name="operator.loginName" readonly="readonly" class="InputStyle" id="lname" value="${operator.loginName }"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									绑定工号：
								</td>
								<td width="75%" align="left">
									<input type="text" name="operator.workNum" class="InputStyle" id="lname" value="${operator.workNum}"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									电话系统登录密码：
								</td>
								<td width="75%" align="left">
									<input type="password" name="operator.workPwd" class="InputStyle" value="${operator.workPwd }"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									性别：
								</td>
								<td width="75%" align="left">
									<input type="radio" name="operator.sex" value="1"  <c:if test="${operator.sex==1}">checked="checked"</c:if>/>男
									<input type="radio" name="operator.sex" value="0" <c:if test="${operator.sex==0}">checked="checked"</c:if>/>女
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									电话1：
								</td>
								<td width="75%" align="left">
								   <input type="text" name="operator.phoneNumber1" id="tel1" class="InputStyle"  vl="validate[custom[phone]]" value="${operator.phoneNumber1}"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									电话2：
								</td>
								<td width="75%" align="left">
								   <input type="text" name="operator.phoneNumber2" id="tel2" class="InputStyle" vl="validate[custom[phone]]" value="${operator.phoneNumber2}"/>
								</td>
							</tr>
						<tr id="pwdTr">
							<td width="25%" align="right">
								密码：
							</td>
							<td width="75%">
								<input type="password"
									class="InputStyle" readonly="readonly" value="12345"/>
									修改<input name="changePwd" value="1" onclick="doChangePwd(this.checked)" type="checkbox" id="changePwd"/>
							</td>
						</tr>
						<tbody id="pw1" style="display:none">
						<tr>
							<td width="25%" align="right">
								旧密码：
							</td>
							<td width="75%">
								<input type="password" name="oldPwd"
									class="InputStyle" id="oldPwd" />
									修改<input value="1" onclick="doChangePwd(this.checked)" type="checkbox" id="changePwd2"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								新密码：
							</td>
							<td width="75%">
								<input type="password" name="password1"
									class="InputStyle" id="password1" vl="validate[required]"/>
							</td>
						</tr>
						<tr>
							<td width="25%" align="right">
								新密码确认：
							</td>
							<td width="75%">
								<input type="password" name="password2" id="password2" vl="validate[required,equals[password1]]"
									class="InputStyle" />
							</td>
						</tr>
						</tbody>
						</table>
						</div>
						<div id="InputDetailBar">
						    <input type="submit" name="submit" value="保 存" class="ButtonStyle"  />
									<input type="reset" value="重 置" class="ButtonStyle"  />
						</div>
					</form>
	</body>
</html>

