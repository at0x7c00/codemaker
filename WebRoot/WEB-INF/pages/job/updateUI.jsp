<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>职位添加</title>
	</head>
</script>
    <body class="ui-widget-content">
	
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>修改职位</td>
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
	
		<form METHOD="POST" NAME="fmOperator" id="formID"
			style="margin-top: 5; margin-bottom: 0"
			action="${basePath}job_update.action"
			onsubmit="return checkThisForm();">
			<input type="hidden" name="id" value="${entity.id }" />
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table class="mainForm" style="width: 90%;" align="center">
						<tr> 
							<td width="16%" class="TextStyle" align="right" height="16"
								valign="middle">
								职位名称：
							</td>
							<td width="84%" class="TextStyle" align="left" height="16"
								valign="middle">
								<input type="text" name="entity.name" maxlength="100" vl="validate[required]"
									class="InputStyle" size="50" id="jobName" value="${entity.name }"
									onchange="checkField('msg1','Job','name',this.value,${entity.id })">
								<font color="red">*&nbsp;<span id="msg1"></span></font>
							</td>
						</tr>
						<tr>
							<td width="16%" class="TextStyle" align="right" height="16"
								valign="middle">
								描 &nbsp; &nbsp;述：
							</td>
							<td width="84%" class="TextStyle" align="left" height="16"
								valign="middle">
								<textarea rows="3" cols="50" name="entity.description"
									id="description" class="InputStyle">${entity.description }</textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<div id="InputDetailBar">
				<input type="submit" name="btnSave" value="保 存" class="ButtonStyle" />
				<input type="reset" name="btnCancel" value="重 置" class="ButtonStyle" />
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					onClick="goBack();" />
			</div>
		</form>
	</body>
</html>

