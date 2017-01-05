<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>我的桌面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

 		<style>
		.column { width: 49%; float: left; padding-bottom: 10px; border:0xp solid red; }
		.portlet { margin: 0 1em 1em 0;}
		.portlet-header { margin: 0.3em; padding-bottom: 4px; padding-left: 0.2em; }
		.portlet-header .ui-icon { float: right; }
		.portlet-content { padding: 0.4em; }
		.ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 50px !important; }
		.ui-sortable-placeholder * { visibility: hidden; }
		</style>
		
		<script>
		$(function() {
		
			$( ".column" ).sortable({
				connectWith: ".column"
			});
			
			
			$( ".portlet" ).addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
				.find( ".portlet-header" )
				.addClass( "ui-widget-header ui-corner-all" )
				.prepend( "<span class='ui-icon ui-icon-refresh' title='刷新' style='cursor:pointer;'></span>")
				.prepend( "<span class='ui-icon ui-icon-minusthick'  style='cursor:pointer;'></span>")
				.end()
				.find( ".portlet-content" );
	
			$( ".portlet-header .ui-icon-minusthick" ).click(function() {
				$( this ).toggleClass( "ui-icon-minusthick" ).toggleClass( "ui-icon-plusthick" );
				$( this ).parents( ".portlet:first" ).find( ".portlet-content" ).toggle();
			});
			$( ".portlet-header .ui-icon-refresh" ).click(function() {
				$("#"+$(this).parents( ".portlet:first" ).attr("frameid")).attr("src","${basePath}"+$(this).parents( ".portlet:first" ).attr("src"));
			});
			
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
			
			$( ".column" ).disableSelection();
	
		});
		function getData(){ 
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
		}
		</script>
		
  </head>
  
 <body class="ui-widget-content">
	<div class="demo" style="margin-top:5px;">
	<div class="column"  style="width:100%">
		<div class="portlet" frameid="frame_${modular.id}" src="${modular.url}">
				<div class="portlet-header">变更记录</div>
				<div class="portlet-content">
				<p>
					<h1>Plan</h1>
					<ul>
						<li style="color:green;">管理基础架构为模板</li>
						<li style="color:green;">支持manageKey</li>
					</ul>
				</p>
				<hr/>
				<p>
					<h1>Doing</h1>
					<ul>
					</ul>
				</p>
				<hr/>
				<h1>Done</h1>
				<ul>
				
				<li>2014-07-08：支持查看页面生成</li><br/>
				
				<li>2014-07-03：导出</li>
				<li>2014-07-03：文件上传</li>
				<li>2014-07-03：代码生成时遇错报告机制</li>
				<li>2014-07-03：增加防重复提交功能</li>
				<li>2014-07-03：字段列表中，字符串类型时，如果是Text类型就显示Text，而不是字符串长度</li>
				<li>2014-07-03：对于小数类型而言，增加单精度也双精度的区别</li>
				<li>2014-07-03：解决问题：修改字段后页面跳转走，页码也丢失</li>
				<li>2014-07-03：去掉实体中非空验证注解<font style="color:red">[√]</font></li>
				<li>2014-07-03：支持“非持久化字段”<font style="color:red">[√]</font></li><br/>
				
				<li>2014-07-02：支持date和datetime类型区别</li><br/>
				
				<li>2014-07-01：修正实体管理页面中无法选择所属包的问题</li>
				<li>2014-07-01：修正IE中字段管理页面中无法显示操作列按钮问题</li>
				</ul>
				</div>
		</div>
		<c:forEach items="${OPERATOR.role.modulars}" var="modular">
			<div class="portlet" frameid="frame_${modular.id}" src="${modular.url}">
				<div class="portlet-header">${modular.title}</div>
				<div class="portlet-content">
				    <c:choose>
				    	<c:when test="${modular.title=='我的状态'}">
				    	    <table width="100%">
				    	         <tr>
				    	            <td>
				    					<div id="progressbar" style="width:99%" style="margin-bottom:5px;"></div>
		   								已分配:<span id="dnumber" class="num"></span>&nbsp;&nbsp;已完成:<span id="comp" class="num"></span>&nbsp;&nbsp;完成率:<span id="pvals" class="num"></span>
		   					        </td>
		   						</tr>
			   					<tr>
								   <td><b>今日已经处理：</b>	<span id="processCount"></span></td>
								</tr>
			   					<tr>
								   <td><input type="button" value="刷新" onclick="getData();"></td>
								</tr>
		   					</table>
				    	</c:when>
				    	<c:otherwise>
				    		<c:if test="${modular.outSite==1}">
								<iframe src="${modular.url}" frameborder="0" id="frame_${modular.id}" style="height:${modular.height }px;width:100%"></iframe>
							</c:if>
							<c:if test="${modular.outSite==0}">
								<iframe src="${basePath}${modular.url}" frameborder="0" id="frame_${modular.id}" style="height:${modular.height }px;width:100%"></iframe>
							</c:if>
				    	</c:otherwise>
				    </c:choose>
				</div>
			</div>
		</c:forEach>
	</div>
	</div>
	<!-- End demo -->
  </body>
</html>
