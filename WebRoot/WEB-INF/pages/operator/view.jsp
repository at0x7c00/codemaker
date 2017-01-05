<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>菜单列表</td>
	  </tr>
	</table>		
			<div class="ItemBlockBorder">
					<table cellpadding="2"  width="950">
						<tr>
							<td width="100%">
								<table class="mainForm" width="100%" cellpadding="5" cellspacing="0" border=0>
										<tr>
											<td height="30" align="right" width="10%">
												姓名：
											</td>
											<td align="left" width="40%">
												${entity.name }
											</td>

											<td height="30" align="right" width="20%">
												帐号：
											</td>
											<td align="left"  width="30%">
												${entity.loginName }
											</td>
										</tr>
										<tr>
											<td height="30" align="right">
												性别：
											</td>
											<td align="left">
												<c:if test="${entity.sex ==1}">男</c:if>
												<c:if test="${entity.sex ==0}">女</c:if>
											</td>

											<td height="30" align="right">
												联系电话：
											</td>
											<td align="left">
												${entity.mobileNumber1}.${entity.mobileNumber2}.
												${entity.phoneNumber1}.${entity.phoneNumber2}.
											</td>
										</tr>
										<tr>
											<td height="30" align="right">
												部门：
											</td>
											<td align="left">
												${entity.dept.name }
											</td>

											<td height="20" align="right">
												是否中心座席：
											</td>
											<td align="left">
												<c:if test="${entity.isCenter==1}">是</c:if>
												<c:if test="${entity.isCenter!=1}">否</c:if>
											</td>
										</tr>
										<tr>
											<td height="30" align="right">
												角色：
											</td> 
											<td width="240" align="left">
												${entity.role.name }
											</td>
											<td align="right">
												职位：
											</td>
											<td align="left">
												${entity.job.name }
											</td>
										</tr>
										<tr>
											<td height="30" align="right">
												工号：
											</td>
											<td width="240" align="left">
												${entity.workNum }
											</td>
											<td width="200">
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
										</tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					<div id="InputDetailBar">
					  <input type="button" name="btnBack" value="返 回" 	class="ButtonStyle" onClick="goBack()">
					</div>
				</center>
			</div>
	</body>
</html>
