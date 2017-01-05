<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户注册</title>
	</head>
    <script type="text/javascript">
    	function pwdToggle(checked){
    		if(checked){
    			$("#pw1").hide();
    			$("#pw2").hide();
    		}else{
    			$("#pw1").show();
    			$("#pw2").show();
    		}
    	}
    </script>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加用户</td>
	  </tr>
	</table>
			<form id="formID" name="form" method="post" style="margin-top:0px;"
						action="${basePath}operator_add.action" >
						<input type="hidden" name="pageNum" value="0" />
						<div class="ItemBlockBorder">
						<table width="100%" style="width:50%;" align="center">
							<tr>
								<td width="25%" align="right">
									所属单位：
								</td>
								<td width="75%" align="left">
									<select name="deptId" class="InputStyle" id="deptSel" vl="validate[required]">
										<option value="">
											---请选择---
										</option>
										<c:forEach items="${deptList}" var="dept">
											<option value="${dept.id }"
												<c:if test="${deptId==dept.id }">selected</c:if>>
												${dept.name}
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									职位：
								</td>
								<td width="75%" align="left">
									<select name="jobId" id="jobId" class="InputStyle" vl="validate[required]">
										<option value="">
											---请选择---
										</option>
										<c:forEach items="${jobList}" var="job">
											<option value="${job.id}"
												<c:if test="${jobId==job.id }">selected</c:if>>
												${job.name }
											</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									角色：
								</td>
								<td width="75%" align="left">
									<select name="roleId" id="roleSel" class="InputStyle" vl="validate[required]">
										<option value="">
											---请选择---
										</option>
										<c:forEach items="${roleList}" var="role">
											<option value="${role.id}"
												<c:if test="${roleId==role.id }">selected</c:if>>
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
									<input type="text" name="addOperator.name" id="name" class="InputStyle"
										value="${addOperator.name }" vl="validate[required]"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									登录账号：
								</td>
								<td width="75%" align="left">
									<input type="text" name="addOperator.loginName" vl="validate[required]" class="InputStyle"
										id="lname" value="${addOperator.loginName }" />
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									绑定工号：
								</td>
								<td width="75%" align="left">
									<input type="text" name="addOperator.workNum" class="InputStyle"
										id="wname" value="${addOperator.workNum }" />
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									电话系统登录密码：
								</td>
								<td width="75%" align="left">
									<input type="text" name="addOperator.workPwd" class="InputStyle"
										value="${addOperator.workPwd }" />
								</td>
							</tr>

							<tr>
								<td width="25%" align="right">
									性别：
								</td>
								<td width="75%" align="left">
									<input type="radio" name="addOperator.sex" value="1" checked="checked" />
									男
									<input type="radio" name="addOperator.sex" value="0"/>
									女
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									电话1：
								</td>
								<td width="75%" align="left">
									<input type="text" name="addOperator.phoneNumber1" id="tel1"
										class="InputStyle" value=" " vl="validate[custom[phone]]"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									电话2：
								</td>
								<td width="75%" align="left">
								    
									<input type="text" name="addOperator.phoneNumber2" id="tel2"
										class="InputStyle" value="${addOperator.phoneNumber2 }" vl="validate[custom[phone]]"/>
								</td>
							</tr>
							<tr>
								<td width="25%" align="right">
									使用默认初始密码:
								</td>
								<td width="75%" align="left">
									<input name="initPwd" value="12345" type="checkbox" id="initP"
										  checked="checked"
										onclick="pwdToggle(this.checked);" />
									12345
								</td>
							</tr>
							<tr id="pw1" style="display:none;">
									<td width="25%" align="right">
													密码：
										</td>
										<td width="75%" align="left">
										<input type="password" name="addOperator.password"
												class="InputStyle" id="pwd" />
										</td>
							</tr>
							<tr id="pw2" style="display:none;">
												<td width="25%" align="right">
													密码确认：
												</td>
												<td width="75%" align="left">
													<input type="password" name="password2" id="pwd2"
														class="InputStyle"  vl="validate[equals[password]"/>
								</td>
							</tr>
						</table>
						</div>
						 <div id="InputDetailBar">  
						  <input type="submit" name="submit" value="提 交"
										class="ButtonStyle" id="submit" />
									<input type="reset" value="重 置" class="ButtonStyle" />
									<input type="button" value="取 消"
										onclick="javascript:history.go(-1)" class="ButtonStyle" />
						</div>
					</form>
	</body>
</html>

