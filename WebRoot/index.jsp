<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.lenovots.crm.filter.PrivilegeValidateFilter"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
	    <title><%=PrivilegeValidateFilter.hasRegist?"":"[未注册]" %>${systemConfig.systemTitle }</title>
	    <style type="text/css">
	    	.Msg{
	    		font-size:12px;
	    		margin-top:2px;
	    	} 
	    	.Msg a{
	    		padding:3px;
	    	}
	    	.SiteTitle{
	    		font-size:20px;FILTER: Shadow(color=#cccccc,direction=135); HEIGHT: 19pt;font-family:黑体; 
	    	}
	    </style>
	    <script type="text/javascript">
	    $(function(){
	    	$(document).keydown(function(){   
			  if(window.event.keyCode==8 || window.event.keyCode== 9||window.event.keyCode==37 || window.event.keyCode == 38|| window.event.keyCode == 39 || window.event.keyCode == 40){   
			      window.event.keyCode=0;   
			  }
			});
	    });
	    
	    </script>
	</head>
    <link rel="stylesheet" href="${basePath}style/forIndex.css" type="text/css"></link>
    <script type="text/javascript" src="${basePath}js/common/forIndex.js"></script>
    <script type="text/javascript">
        var basePath= '${basePath}';
        var receiverId = ${OPERATOR.id};
        var checkRate=${empty OPERATOR.checkRate?60:OPERATOR.checkRate}*1000;
        var isTaskTip=${empty OPERATOR.isTaskTip?0:OPERATOR.isTaskTip};
        var interval;
        var isShow=false;
    	$(function(){
    		 /*try{
	         	${systemConfig.objId}.weblogin('${OPERATOR.workNum}', '${OPERATOR.workPwd}','${systemConfig.phoneServerIp}', '5060');
	         }catch(e){
	         	if(browserCheck().ie){
		         	alertMsg("<span class=\'ui-icon ui-icon-info\' style=\'float: left; margin-right: .3em;\'></span>您的浏览器没有安装电话控件,或者阻止了控件加载");
		         	$("#ocxMsg").html("<span class=\'ui-icon ui-icon-info\' style=\'float: left; margin-right: .3em;\'></span>您的浏览器没有安装电话控件,或者阻止了控件加载。<a href=\"${basePath}ocx.rar\" target=\"_blank\">点击这里</a>下载OCX控件");
	         	}else{
		         	alertMsg("<span class=\'ui-icon ui-icon-info\' style=\'float: left; margin-right: .3em;\'></span>您的浏览器不支持该电话控件");
		         	$("#ocxSource").html("<span class=\'ui-icon ui-icon-info\' style=\'float: left; margin-right: .3em;\'></span>您的浏览器不支持该电话控件");
	         	}
	         } */
	         $( "#phoneMenu" ).toggle();  
	         
    	});
    	function doCall(telNumber){
    		//$( "#phoneMenu" ).show( "fold", {}, 500, null );
			$("#${systemConfig.objId}").get(0).WEBcall(telNumber);
    	}
    	function saveTipSet(){
    		$.get("${basePath}ajax/saveTipSet.action",{isTaskTip:$("#isTaskTip").val(),checkRate:$("#checkRate").val()},function(data,textStatus){
    			//nothing
    		});
    	}
    	function openClientCall(Number){
    	    jQuery('#tag').attr('url',"${basePath}callTask_clientCall.action?telNumber="+Number);
    		jQuery('#tag').get(0).focus();
    		$( "#phoneMenu" ).toggle( "fold", {}, 500, null );
    	}
    </script>
    <!-- 外呼电话录音记录ID -->
	<SCRIPT FOR='${systemConfig.objId }' EVENT=RecordIDExt(Number)>
		$.get("${basePath }ajax/recordFileId.action",{callRecordFileId:Number},function(date,textStatus){
		//nothing 
		});
	</SCRIPT>
	<!-- 呼入事件 retCallID：录音ID,retCaler：主叫号码-->
	<SCRIPT type="text/javascript" FOR="${systemConfig.objId }" EVENT="OnCallIn(retCallID,retCaller)" >
		$.get("${basePath }ajax/recordFileId.action",{callRecordFileId:retCallID},function(date,textStatus){
		//nothing 
		});
	</SCRIPT>
	<!--  来电事件 -->  
	<SCRIPT FOR='${systemConfig.objId }' EVENT=EventInvite(Number,UserID,Worknum)>   
		openClientCall(Number);
	</SCRIPT>
	<!-- 登录返回事件 -->
	<SCRIPT FOR='${systemConfig.objId }' EVENT=EventLogin(Result)>
	    if(Result!=0){
		    alertMsg("<center>电话控件登录失败!</center>");
	     }
	</SCRIPT>
</head>
<body class="ui-widget-content" scroll="no">
	<!-- 头部 开始-->
		<a href="#" id="tag"></a>
	<div class="LogoHead">
	  <table width="100%" border="0">
	      <tr>
	         <td width="50%" rowspan="2" valign="middle" >
	          <a href="${basePath}loginSuccess.action"><img src="${basePath}images/logo.png" title="Powered by NOVOTS"/></a>
	         </td>
	         <td align="right">
	          	<table border="0" style="display:inline;">
	                <tr style="padding:5px;">
	                   <td class="ui-icon ui-icon-person"></td>
	                   <td><a href="#" id="userInfoMenu">用户信息</a></td>
	                   <td>&nbsp;</td>
	                   <td class="ui-icon ui-icon-power"></td>
	                   <td><a href="#" id="exitMenu">退出系统</a></td>
	                   <td>&nbsp;</td>
	                </tr>
	             </table>
	         </td>
	      </tr>
	      <tr>
	         <td align="right">
	             <table border="0" style="display:inline;">
	                 <tr>
	                	 <td>${OPERATOR.name}(</td>
	                	 <td>${OPERATOR.role.name},</td>
	                	 <td>${OPERATOR.dept.name})</td>
	                     <td align="right">
	                     &nbsp;<select onchange="selectStyle('${basePath}',this.value)" id="styleSelect">
								<option value="base" ${OPERATOR.styleName=='base'?'selected':'' }>base</option>
							  	<option value="dark-hive" ${OPERATOR.styleName=='dark-hive'?'selected':''}>dark-hive</option>
							  	<option value="le-frog" ${OPERATOR.styleName=='le-frog'?'selected':''}>le-frog</option>
							  	<option value="ui-darkness" ${OPERATOR.styleName=='ui-darkness'?'selected':''}>ui-darkness</option>
							  	<option value="ui-lightness" ${OPERATOR.styleName=='ui-lightness'?'selected':''}>ui-lightness</option>
							  	<option value="trontastic" ${OPERATOR.styleName=='trontastic'?'selected':''}>trontastic</option>
							  	<option value="blitzer" ${OPERATOR.styleName=='blitzer'?'selected':''}>blitzer</option>
							  	<option value="start" ${OPERATOR.styleName=='start'?'selected':''}>start</option>
							  	<option value="hot-sneaks" ${OPERATOR.styleName=='hot-sneaks'?'selected':''}>hot-sneaks</option>
							  	<option value="swanky-purse" ${OPERATOR.styleName=='swanky-purse'?'selected':''}>swanky-purse</option>
							  	<option value="ubs-theme" ${OPERATOR.styleName=='ubs-theme'?'selected':''}>ubs-theme</option>
							  	<option value="dot-luv" ${OPERATOR.styleName=='dot-luv'?'selected':''}>dot-luv</option>
						    </select>
				  		 </td>
				  	 </tr>
	           	   </table>
	         </td>
	      </tr>
	  </table>
	</div>
	<!-- 头部 结束-->
	
	<!-- 中间DIV 开始 ui-icon ui-icon-carat-1-e -->
	<table width="100%">
	<tr>
	<td class="LeftMenu"  valign="top" id="leftTd" > <!-- float:left;clear:left; -->
	
			<button id="showMenu" style="margin:0px;display:none" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
				<span class="ui-icon ui-icon-carat-1-e" title="显示菜单栏"></span>
			</button>
			
				<div id="accordionResizer" class="ui-widget-content" style="width:95%">
				
					<div class="ui-widget-header ui-corner-all" id="hideMenu"  title="隐藏菜单栏">
							<button  style="margin:0px" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button" aria-disabled="false">
								<span class="ui-icon ui-icon-carat-1-w"></span>
							</button>
					</div>
					
					<div id="accordion">
						<c:forEach items="${permissionList}" var="top" varStatus="status">
								<h3><a href="#">${top.name}</a></h3>
								<div>
									<c:forEach  items="${top.children}" var="second" varStatus="status">
									        <table border="0">
									           <tr>
									              <td><img src="${basePath}images/func/${second.icon}" width="32" height="32"></td>
									              <td><a href="${basePath}${second.url}" class="menuA" target="_blank" title="${second.name}">${second.name}</a></td>
									           </tr>
									        </table>
									</c:forEach>
								</div>
						</c:forEach>
					</div>
				</div>
			</td>
			<!-- 右侧内容 -->
			<td class="RightContent"   valign="top">
					<div id="tabs">
						<ul>
							<li><a style="border:0px solid #ffffff;" href="#tabs-1">首页</a></li>
						</ul>
						<div id="tabs-1" style="padding:0px;">
							<iframe src="${basePath}desk.jsp" width="100%" id="adminFrame" frameborder="0"></iframe>
						</div>
					</div>
			</td>
		</tr>
	</table>
	<!-- 中间DIV 结束 -->


	<!-- 尾部版权 开始-->
	<div class="CopyRight">
	 
	</div>
	<!-- 尾部版权 结束-->
 
	<!-- 消息盒子 开始 -->
	<div id="msgBox" class="ui-widget-content">
		<h3 class="ui-widget-header ui-corner-all" id="msgBoxBar" title="点击关闭">消息盒子</h3>
		<p id="msgContent">
			
		</p>
		<div style="position:absolute;bottom:0px;font-size:10px;color:gray;">
		    消息提醒
		    <select id="isTaskTip" name="isTaskTip" onchange="saveTipSet();">
		    	<option value="1" ${OPERATOR.isTaskTip==1?'selected':''}>开启</option>
		    	<option value="0" ${OPERATOR.isTaskTip==0?'selected':''}>关闭</option>
		    </select>
		    检测频率
		    <select id="checkRate" name="checkRate" onchange="saveTipSet();">
		    	<option value="10" ${OPERATOR.checkRate==10?'selected':''}>10秒</option>
		    	<option value="20" ${OPERATOR.checkRate==20?'selected':''}>20秒</option>
		    	<option value="30" ${OPERATOR.checkRate==30?'selected':''}>30秒</option>
		    	<option value="60" ${OPERATOR.checkRate==60?'selected':''}>1分钟</option>
		    	<option value="300" ${OPERATOR.checkRate==300?'selected':''}>5分钟</option>
		    	<option value="1200" ${OPERATOR.checkRate==1200?'selected':''}>20分钟</option>
		    </select>
		</div>
	</div>
	<!-- 消息盒子 结束 -->

	<!-- 电话控件  开始 -->
	<div id="phoneMenu" class="ui-widget-content" style="display:block;position:absolute;">
		<h3 class="ui-widget-header ui-corner-all" id="phoneMenuBar" title="点击关闭">电话控件</h3>
		<div id="ocxSource">${systemConfig.ocxObjSource}</div>
		<div id="ocxMsg"></div>
	</div>
	<!-- 电话控件  结束 -->

	<!-- 退出提示对话框 -->
	<div id="dialog-confirm" title="提示" style="display:none;">
		<p><span class="ui-icon ui-icon-alert" style="float:left;margin:0 7px 20px 0;"></span>你确认要退出系统吗?</p>
	</div>
	
	<div id="dialog-modal" style="display:none;">
	</div>
</body>
</html>
