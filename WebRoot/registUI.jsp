<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
		<title>系统注册</title>
		
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
    var hasCheckCode=${systemConfig.useCheckCode==1};
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
				"提交": function() {
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

<!-- style="background:url('${basePath}images/logonUIBg.png');background-color:#000000;" -->
<body >
	<div id="dialog-modal" title="系统注册" style="display:none;width:90%">
	<form  action="${basePath }regist.action" id="formID" method="post">
		<table width="100%">
		    <tr>
		       <td><label for="name">请输入注册码</label></td>
		    </tr>
		    <tr>
		     <td>
		       <textarea  row="50"  col="100" name="registCode" id="name" value="admin" vl="validate[required]"  style="height:100px;width:100%">
		       </textarea>
		       </td>
		    </tr>
		</table>
	        
		</form>
	</div>
</body>
</html>
