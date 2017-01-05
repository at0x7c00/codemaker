<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>实体批量导入</title>
		<script src="${basePath}js/jquery/ui/jquery.ui.progressbar.js"></script>
		<style type="text/css">
		.RowTitle{
			font-size:14px;
			color:red;
			font-weight:bold;
			display:block;
		}
		.Pos{
			color:blue;
		} 
		div{
			margin:3px;
		}
		</style>
		<script type="text/javascript">
			var timer;
			$(function(){
				timer = window.setInterval(function() { 
					try{
					    $.get("${basePath}entity_importProgress.action",{'first':'no','time':new Date().getTime()},function(data,textStatus){
						    var res =eval('('+data+')');
						    var val = Math.round((res[0].rowCount/res[0].total)*100);
						    if(isNaN(val)){
						       val = 0;
						    }
						    $("#f").html(val+'%');
						    $("#opera").html(res[0].opera);
						    $("#t").html(res[0].total);
						    $("#r").html(res[0].rowCount);
						    $("#i").html(res[0].msg); 
							$("#progressbar").progressbar({
				   		    	value:val
				   		    });  
						    if(res[0].complate=="true"){
						       window.location.reload();
						    }
						});
					}catch(e){
					   alert(e.description);
					}	
				},1000);
			});
		</script>
	</head>
</script>

	<body class="ui-widget-content">
	
		<div style="margin:5px;padding:15px;">
		<div id="progressbar" style="width:100%">
		</div> 
			<div id="f" style="font-size:20px;font-weight:bold;"></div>
			<span id="opera"></span>
			共<span id="t"></span>条记录，已处理<span id="r"></span>条<br/>
		</div>
		
	</body>
	
</html>

