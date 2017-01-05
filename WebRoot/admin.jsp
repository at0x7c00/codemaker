<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <script type="text/javascript">
    	jQuery(function(){
	    	jQuery.get("${basePath}ajax/getOperatorProcessStatus.action",{operatorId:${OPERATOR.id}},function(data,textStatus){
				if(!textStatus){
					alert("ajax请求时无响应。");
				}else if(data!=""){
					var ops = eval(data);
					var val = Math.round((ops[0].comp*100/(ops[0].dnumber*1+ops[0].cnumber*1)));
				    if(isNaN(val)){
				       val = 0;
				    }
					jQuery("#dnumber").html(ops[0].dnumber);
					jQuery("#cnumber").html(ops[0].cnumber);
					jQuery("#comp").html(ops[0].comp);
					jQuery("#pvals").html(val+"%");
					jQuery("#progressbar").progressbar({
		   		    	value:val
		   		    }); 
					jQuery("#processStatus").show();
					jQuery("#processImg").hide();
				}
			});
			//今日已完成量
	    	jQuery.get("${basePath}ajax/getDispatchedProcessedCountForToday.action",{operatorId:${OPERATOR.id}},function(data,textStatus){
	    		$("#ap").html(data);
			});
			//未完成量
	    	jQuery.get("${basePath}ajax/getDispatchedUnProcessedCount.action",{operatorId:${OPERATOR.id}},function(data,textStatus){
	    		$("#up").html(data);
			});
			//我已处理量
	    	jQuery.get("${basePath}ajax/getOperatorProcessedCountForToday.action",null,function(data,textStatus){
	    		$("#processCount").html(data);
			});
    	});
    </script>
  </head>
  <body class="ui-widget-content" style="font-size:25px;">
   		<table width="100%" border="0" style="margin:5px;">
		 <tr>
	       <td colspan="2" align="left"><h5 style="padding:0px;margin:0px;">全局状态</h5><hr/></td>
	    </tr>
		<tr> 
		   <td width="20%" ><b>今日处理情况：</b></td>
		   <td>
		   		未处理量：<span id="up"></span>&nbsp;&nbsp;&nbsp;&nbsp;今日完成量：<span id="ap"></span>
		   </td> 
		</tr>
		<tr>
	       <td colspan="2" align="left"><br><br><br><h5 style="padding:0px;margin:0px;">我的状态</h5><hr/></td>
	    </tr>
		<tr id="processStatus" height="40px">
		   <td width="20%"><b>我的处理状态：</b></td>
		   <td>
		   		<div id="progressbar" style="width:99%" style="margin-bottom:5px;"></div>
		   		已分配:<span id="dnumber" class="num"></span>&nbsp;&nbsp;累积:<span id="cnumber"  class="num"></span>&nbsp;&nbsp;已完成:<span id="comp" class="num"></span>&nbsp;&nbsp;完成率:<span id="pvals" class="num"></span>
		   </td> 
		</tr>
		<tr>
		   <td width="20%" ><b>今日已经处理：</b></td>
		   <td>
		   	<span id="processCount"></span>
		   </td> 
		</tr>
		<tr>
		   <td colspan="2" align="center">
		   		<input type="button" onclick="window.location.reload();" value="刷新" title="点击刷新查看最新结果"/>
		   </td>
		</tr>
		</table>
  </body>
</html>
