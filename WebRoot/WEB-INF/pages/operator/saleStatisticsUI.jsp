<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>客服销量统计</title>
		<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
			<script type="text/javascript">
			var x=1;
			var hidden=1;
			function add(){
			   x++;
			   var selObj=document.getElementById('userIdSel');
			   var causeObj=document.getElementById('causeTab');
			   var spanObj=document.createElement("span");
               if(x%7==0){
				   spanObj.innerHTML="<select name='userIds'>"+selObj.innerHTML+"</select><a href='javascript:void(0);' onclick='del(this);'>[删除]</a><br/>";
			   }else{
				   spanObj.innerHTML="<select name='userIds'>"+selObj.innerHTML+"</select><a href='javascript:void(0);' onclick='del(this);'>[删除]</a>";
			   }
			   causeObj.rows[0].cells[1].appendChild(spanObj);
			}
		   	function del(selObj){
			   selObj.parentNode.parentNode.removeChild(selObj.parentNode);
			   x--;
			}
			function test(range){
			    maskAll();
			    if(range==1){//本周
			         document.getElementById('no').style.display="inline";
			         document.getElementById('day').style.display="inline";
			    }else if(range==2){//本月
			         document.getElementById('no').style.display="inline";
			         document.getElementById('day').style.display="inline";
			    }else if(range==3){//本季度
			    	document.getElementById('no').style.display="inline";
			    	document.getElementById('month').style.display="inline";
			    }else if(range==4){//本年
			        document.getElementById('no').style.display="inline";
			        document.getElementById('month').style.display="inline";
			        document.getElementById('season').style.display="inline";
			    }else{//自定义
			       showAll();
			    }
			}
			function maskAll(){
			   document.getElementById('timeCause').style.display="none";
			   document.getElementById('no').style.display="none";
			   document.getElementById('day').style.display="none";
			   document.getElementById('month').style.display="none";
			   document.getElementById('season').style.display="none";
			   document.getElementById('year').style.display="none";
			}
			function showAll(){
			   document.getElementById('timeCause').style.display="inline";
			   document.getElementById('no').style.display="inline";
			   document.getElementById('day').style.display="inline";
			   document.getElementById('month').style.display="inline";
			   document.getElementById('season').style.display="inline";
			   document.getElementById('year').style.display="inline";
			}
			function saleStatisticsList(){
			    document.getElementById("listForm").action="${basePath}operator_saleStatisticsList.action";
				openWaitDialog('listForm','正在统计,请稍后');
			}
			</script>
	</head>
	<body>
	<div id="Title_bar" >
	<table width="100%" style="border:0px solid #000000" >
	<tr align="right">
	<td>  
			<div id="Title_bar_Head">
				<div id="Title_Head"></div>
				<div id="Title">
					<!--页面标题-->
					<img border="0" width="13" height="13"
						src="${basePath}images/title_arrow.gif" />
					    客服销售统计
				</div>
				<div id="Title_End"></div>
			</div>
			</td>
			   </tr>
			</table>
		</div>
		<div><font style="color:red"><s:fielderror></s:fielderror></font></div>
		<form action="${basePath}operator_saleStatisticsPic.action" id="listForm" method="post">
		<div class="ItemBlockBorder">
			<div class="ItemBlock">
			<table class="mianForm" width="100%">
				<tr>
					<td width="15%"  align="right" height="30px">
						&nbsp;客服姓名：
						</td>
						<td colspan="2">
						     <span id="users"></span><input type="button" value="选择" class="ButtonStyle" onclick="openMask('选择客服','${basePath }ajax/getAllOperators.action','users');"/>&nbsp;&nbsp;[ 列表方式下，默认为全选 ]
						</td>
				</tr>
				<tr height="35px"> 
				 <td  align="right">
						&nbsp;统计方式：
						</td>
						<td>
						<input type="radio" name="statType" value="0"  checked="checked" id="001"/><label for="001" style="cursor:pointer">联合统计</label>
						<input type="radio" name="statType" value="1" id="002"/><label for="002" style="cursor:pointer">对比统计</label>
					</td>
				</tr>
				<tr height="35px"> 
				 <td  align="right">
						&nbsp;统计类型：
						</td>
						<td>
						<input type="radio" name="type" value="1"  checked="checked" id="003"/><label for="003" style="cursor:pointer">销售额</label>
						<input type="radio" name="type" value="2" id="004"/><label for="004" style="cursor:pointer">销售量</label>
					</td>
				</tr>
				<tr height="35px">
				 <td  align="right">
						&nbsp;时间范围：
						</td>
						<td>
						<input type="radio" name="timeRange" value="1"  onclick="test(1);" checked="checked" id="005"/><label for="005" style="cursor:pointer">本周</label>
						<input type="radio" name="timeRange" value="2"  onclick="test(2);" id="006"/><label for="006" style="cursor:pointer">本月</label>
						<input type="radio" name="timeRange" value="3"  onclick="test(3);" id="007"/><label for="007" style="cursor:pointer">本季度</label>
						<input type="radio" name="timeRange" value="4"  onclick="test(4);" id="008"/><label for="008" style="cursor:pointer">本年</label>
						<input type="radio" name="timeRange" value="0"  onclick="test(0);" id="009"/><label for="009" style="cursor:pointer">自定义时间 </label>
						<span id="timeCause" style="display:none">&nbsp;从<input readonly='true' class="Wdate" type="text" name="timeBegin" class="Wdate" onClick="WdatePicker()" />&nbsp;到<input readonly='true' class="Wdate" type="text" name="timeEnd" class="Wdate" onClick="WdatePicker()""/></span>
					</td>
				</tr>
				<tr height="35px">
				 <td  align="right">
						&nbsp;时间间隔：
						</td>
						<td>
						<span id="no" ><input type="radio" name="timeType" value="0" checked="checked" id="010"/><label for="010" style="cursor:pointer">无间隔</label></span>
						<span id="day" ><input type="radio" name="timeType" value="1" id="011"/><label for="011" style="cursor:pointer">每天</label></span>
						<span id="month" style="display:none"><input type="radio" name="timeType" value="2" id="012"/><label for="012" style="cursor:pointer">每月</label></span>
						<span id="season" style="display:none"><input type="radio" name="timeType" value="3" id="013"/><label for="013" style="cursor:pointer">每季度</label></span>
						<span id="year" style="display:none"><input type="radio" name="timeType" value="4" id="014"/><label for="014" style="cursor:pointer">每年</label></span>
					</td>
				</tr>
			</table>
			</div>
			</div>
			<div id="InputDetailBar">
			  <label>
							<input type="button" name="button" id="button" value="统  计" class="ButtonStyle" onclick="openWaitDialog('listForm','正在统计,请稍后');" />
							<input type="button" name="button" id="button" value="列  表" class="ButtonStyle" onclick="saleStatisticsList();"/>
						</label>
						<label>
							<input type="reset" name="button2" id="button2" value="&nbsp;重&nbsp;&nbsp;置&nbsp;" class="ButtonStyle" />
						</label>
			</div>
			</form>
	</body>
</html>