<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
${msg }
<html>
	<title>添加单位</title>
</script>
	<body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>添加部门</td>
	  </tr>
	</table>
		
		<form METHOD="POST" NAME="fmDepartment"
			style="margin-top: 15; margin-bottom: 0" id="formID"
			action="${basePath}department_add.action"
			onsubmit="return checkThisForm();">
			<div class="ItemBlockBorder">
			<table width="100%">
				<tr>
					<td  class="TextStyle" align="right"  height="16"
						valign="middle" width="10%">
						单位名称：
					</td>
					<td  class="TextStyle" align="left" height="16"
						valign="middle" width="40%">
						<input type="text" name="dept.name" maxlength="20"
							class="InputStyle" size="35" id="deptName" vl="validate[required]">
						<font color="red">*</font>
					</td>
					<td  class="TextStyle" align="right" height="16"
						valign="middle" width="10%">
						所属单位：
					</td>
					<td  class="TextStyle" align="left" height="16"
						valign="middle" width="40%">
						<select name="mainDeptId" id="mainDeptId" class="SelectStyle">
							<option value="-1">
								--无--
							</option>
							<c:forEach items="${deptList}" var="dept">
								<OPTION VALUE="${dept.id}">
									${dept.name}
								</OPTION>
							</c:forEach>
						</select>

					</td>
				</tr>
				<tr>
					<td class="TextStyle" align="right"  height="16"
						valign="middle">
						单位电话1：
					</td>
					<td  class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="dept.phoneNumber1" maxlength="16" id="dept.p1"
							class="InputStyle" id="tel1" size="22" value="" vl="validate[custom[phone]]">
					</td>
					<td  class="TextStyle" align="right" height="16"
						valign="middle" >
						单位电话2：
					</td>
					<td  class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="dept.phoneNumber2" id="dpet.p2" vl="validate[custom[phone]]"  class="InputStyle" maxlength="16"
							id="tel2" size="21" value="">
					</td>
				</tr>
				<tr>
					<td  class="TextStyle" align="right"  height="16"
						valign="middle">
						单位地址：
					</td>
					<td  class="TextStyle" align="left" height="16"
						valign="middle">
						<input type="text" name="dept.address" maxlength="64" style="width:400px"
							class="InputStyle" id="tel1" value="">
						&nbsp;

					</td>
				</tr>
				<tr>
					<td class="TextStyle" align="right" 
						style='height: 80px;' valign="middle">
						描述信息：
					</td>
					<td colspan='3' class="TextStyle" align="left"
						style='height: 80px;' valign="middle">
						<textarea name="dept.description" class="InputStyle"
							style='width:80%;' cols="20" rows="3"></textarea>
				</tr>
			</table>
			</div>
			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="添 加" class="ButtonStyle"
					 />
				<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle"
					>
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					 onClick="goBack();">
			</div>
		</form>
	</body>
</html>

