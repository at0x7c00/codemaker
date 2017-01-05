<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
		<title>用户登录-${systemConfig.systemTitle }</title>
		
		<script type="text/javascript">
		
        function changeBorder(inpObj){
           inpObj.style.border="2px solid #eda804";
        }
        
        function changeBorder2(inpObj){
           inpObj.style.border="0px"; 
        }
        
        function changeCode(){
           document.getElementById('code').src="${basePath}ajax/getCheckCode.action?time="+new Date().getTime();
        }
        
        window.onload=function(){
             if(window.top!=window){
			    window.top.location.href=window.location.href;
			}
        }
        
    </script>
    <script>
    var hasCheckCode='${systemConfig.useCheckCode==1}';
    var dialogHeight=browserCheck().ie?400:250;
    $(function() {
        if(hasCheckCode){
      	   dialogHeight=browserCheck().ie?550:330;
        }
        $( "#dialog-modal" ).bind("keydown",function(e){
        	if(e.which == 13){
        		$("#formID").submit();
           	}
        });
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		$( "#dialog-modal" ).dialog({
			height: dialogHeight,
			width:600,
			modal: true ,
			autoOpen: true,
			buttons: {
				"登录": function() {
				   if($('#formID').validationEngine('validate')){
						document.getElementById("formID").submit();
				    }
				},
				'重置': function() {
					document.getElementById("formID").reset();
				}
			},
			close: function() {
				window.location.reload();
			}
		});
	});
	</script>
    <style type="text/css">
		body { font-size: 62.5%; }
		label {font-size:16px}
		input.text {height:32px;width:95%; padding: .4em;line-height:16px;font-size:16px}
		fieldset { padding:0; border:0; margin-top:25px; }
		.ui-dialog .ui-state-error { padding: .3em; }
	</style>
</head>

<body style="background:url('${basePath}images/loginUIBG.png');background-color:#ffffff;" >
	<div id="dialog-modal" title="CodeMaker-用户登录" style="display:none;width:90%">
	<form  action="${basePath }login.action" id="formID" method="post">
		<table width="100%">
		    <tr>
		       <td width="10%"><label for="name">账号</label></td>
		       <td><input type="text" name="operator.loginName" id="name" value="admin" vl="validate[required]" class="text ui-widget-content ui-corner-all"/></td>
		    </tr>
		    <tr>
		       <td width="10%"><label for="password">密码</label></td>
		       <td> <input type="password" name="operator.password" id="password" value="a" vl="validate[required]"  value="" class="text ui-widget-content ui-corner-all" /></td>
		    </tr>
			<c:if test="${systemConfig.useCheckCode==1}">
			    <tr>
			       <td width="10%"><label for="checkCode">验证码</label></td>
			       <td><input type="text" name="checkCode" id="checkCode"  vl="validate[required]"  class="text ui-widget-content ui-corner-all" ></td>
			    </tr>
			    <tr> 
			        <td>&nbsp;</td>
			    	<td><img id="code" onclick="changeCode();"  src="${basePath}ajax/getCheckCode.action?time=12345678" alt="验证码" style="cursor:hand" title="点击换一张" width="100" height="30">&nbsp;<a href="javascript:void(0);" onclick="changeCode();">换一张</a></td>
			    </tr>
	        </c:if>
		    <tr>
		      <td colspan="2">
		       <s:fielderror cssClass="ui-state-error ui-corner-all" cssStyle="width:90%;margin:15px;"/>
		        <%if(session.getAttribute("LOGIN_MSG") != null){
		            	session.removeAttribute("LOGIN_MSG");
		            	out.print("<span class='ui-state-error ui-corner-all' cssStyle='width:90%;margin:15px;'>您的账号在其他地方登录</span>");
		         }%>
		      </td>
		    </tr>
		</table>
		</form>
	</div>
</body>
</html>
