<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>项目内容查看</title>
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red;">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>项目内容查看</td>
	  </tr> 
	</table>
		<table border="0" style="position:absolute;top:0;right:0;">	
	    <tr>
			<td class="ui-icon ui-icon-play"></td>
			<td>
				<span><a href="javascript:void(0);" onclick="sleectRuleDialog()">生成代码</a></span> 
			</td>
			<td>&nbsp;</td>
			<td class="ui-icon ui-icon-home"></td>
			<td>
				<span><s:a action="project_list.action">返回项目列表</s:a></span> 
			</td> 
		</tr>
	</table>
			<input type="hidden" value="${id }" name="id"/>
			 &nbsp;&nbsp;
			 <a href="${basePath}project_viewFile.action?id=${project.id}"><b>${project.name}</b></a>
			 <c:forEach items="${parents}" var="p">
			 	&gt;&nbsp;
			 	 <a href="${basePath}project_viewFile.action?id=${project.id}&dir=${p.fullPath}">${p.name}</a>
				 <c:set var="lastParent" value="${p}"></c:set>
			 </c:forEach>
				<table class="ui-widget ui-widget-content" width="100%" style="text-align: center;">
				<thead>
					<tr class="ui-widget-header" height="30px" id="listTHead" align="left">
					<TD width="16px"  style="width:16px"></TD>
					<td>
						<b>文件名</b>
					</td>
					<td style="width:10%">
						<b>大小</b>
					</td>
					<td  style="width:20%">
						<b>修改日期</b>
					</td>
					<td  style="width:8%">
						<b>操作</b>
					</td>
					</tr>
				</thead>
				<tbody id="listTBody">
				<c:if test="${empty files}">
					<tr height="15px">
						<td class="bg" colspan="5" align="center">
							<font style="color: gray; font-weight: bold;">文件夹是空的
							</font>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${files}" var="file">
					<tr align="left" height="20px">
						<TD width="16px" class="ui-icon ui-icon-${file.isDirectory?'folder-collapsed':'document'}" style="width:16px"></TD>
						<td >
							<c:choose>
								<c:when test="${file.isDirectory}">
								   <a href="${basePath}project_viewFile.action?id=${project.id}&dir=${file.fullPath}">${file.name}</a>
								</c:when>
								<c:otherwise>
									<a href="${basePath}project_checkCode.action?id=${project.id}&fileName=${file.fullPath}" target="_blank" style="text-decoration: none;">${file.name}</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td style="width:10%">
							<c:choose>
								<c:when test="${file.isDirectory}">
								--
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${file.size/1024<1000}">
											<fmt:formatNumber value="${file.size/1024}" pattern="0.00"></fmt:formatNumber>KB
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${file.size/(1024*1024)}" pattern="0.00"></fmt:formatNumber>M
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</td>
						<td  style="width:10%">
							<fmt:formatDate value="${file.lastModifyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<a href="${basePath}project_deleteCode.action?id=${project.id}&deleteDir=${file.fullPath}&dir=${lastParent.fullPath}" >删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div id="InputDetailBar">
				<c:if test="${not empty files}">
				<a href="project_downloadFile.action?id=${project.id}"  class="button">
					下载代码
				</a>
				</c:if>
			</div>
			<div id="dialog-message" title="代码生成" style="display:none;">
			<p>
			    正在生成代码....[<span id="progressInfo"></span>]<span id="progressValue"></span>
			 </p>
			 <p id="msgContent"></p>
			</div>
			
			<div id="select-rule" title="选择映射规则" style="display:none;">
			<p>
			<table width="100%">
				<tr>
					<td style="background: gray;color:#fff;font-weight:bold;">
					<input type="checkbox" id="selectAll"/><label for="selectAll">全选</label>
					&nbsp;&nbsp;&nbsp;&nbsp;
					检查错误<select id="checkError">
						<option value="true">是</option>
						<option value="false">否</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					包范围
					<select name="packageId" class="SelectStyle" id="packageId" >
						<option value="">-全部-</option>
						<c:forEach items="${parentPacks}" var="parent">
							<option value="${parent.id}" ${parent.id==packageId?'selected':'' }>${parent.name }</option>
						</c:forEach>
					</select>
					
					</td>
				</tr>
				<tr>
					<td>
						<table id="entityTable" style="color:white;background-color: gray;" width="100%">
			   	  			<tbody>
			   	  			
			   	  			</tbody>
			   	  		</table>
					</td>
				</tr>
			   <c:forEach items="${project.rules}" var="r" varStatus="s">
			   <tr class="rule_item">
			   	<td>
			   	<input type="checkbox" name="ruleIds" value="${r.id}" id="item_${r.id}"/><label for="item_${r.id}">${r.template.name}-->${r.outputDir}${r.outputFileName}</label>
			   	</td>
			   	</tr>
				</c:forEach>
			</table>
			 </p>
			</div>
	</body>
	<script type="text/javascript">
		/*var rules = [
			<c:forEach items="${project.rules}" var="r" varStatus="s">
			{id:${r.id},name:'${r.template.name}',outputdir:'${r.outputDir}',outputfilename:'${r.outputFileName}'}<c:if test="${not s.last}">,</c:if>
			</c:forEach>
		];*/
		var rules = [];
		var hasError = false;
		
		function sleectRuleDialog(){
			$("#select-rule").dialog({modal:true,
				width:900,
				height:500,
				buttons:{
					确认:function(){
						$("#select-rule").dialog("close");
						buildProject();
					},
					取消:function(){
						$("#select-rule").dialog("close");
					}
				}
			});
			
		}
		
		function buildProject(){
			var a = $("#msgContent");
			a.html("");
			$('#dialog-message').dialog({modal:true,
				buttons:{
				},
				width:900,
				height:500
			});
			hasError = false;
			rules = [];
			$("input[name=ruleIds]:checked").each(function(i){
				rules[i] = {id:$(this).val(),info:$(this).parent().find("label").first().html()};
			});
			buildCode(0);
		}
		function buildCode(i){
			if(i>=rules.length){
				appendToMsgContent("<p>生成完毕!</p>");
				if(rules.length==0){
					appendToMsgContent("<p>啊！一个都没选，你真有意思~</p>");
				}
				window.setTimeout(function(){
					if(!hasError){
						window.location.reload();
					}
				},1000);
				return;
			}
			//appendToMsgContent((i+1)+"、"+rules[i].name+"--->"+rules[i].outputdir+rules[i].outputfilename);
			appendToMsgContent((i+1)+"、"+rules[i].info);
			$.get("${basePath}project_build.action?id=${project.id}&ruleId="+rules[i].id+"&checkError="+$("#checkError").val()+"&packageId=" + $("#packageId").val(),function(data){
				var d = eval("("+data+")");
				if(d.status=='ERROR'){
					appendToMsgContent("<b style='color:red;'>["+d.status+"]</b>");
					appendToMsgContent("："+d.info);
					hasError = true;
				}else if(d.status=='EXCEPTION'){
					appendToMsgContent("<b style='color:red;'>["+d.status+"]</b>");
					appendToMsgContent("：<a href='${basePath}entity_codeView.action?id="+d.entity+"&ruleId="+d.rule+"&entityIteratorIndex="+d.index+"' target='_blank'>"+d.info+"</a>");
					appendToMsgContent("，<a href='${basePath}property_list.action?entityId=" + d.entity+"' target='_blank'>"+d.entityName+"</a>，<a href='${basePath}template_updateUI.action?id="+d.template+"' target='_blank'>相关模板</a>");
					hasError = true;
				}else{
					appendToMsgContent("<b style='color:green;'>["+d.status+"]：</b>"+d.info);
				}
				$("#msgContent").html($("#msgContent").html()+"<br/>");
				
				$("#progressValue").html((i+1)+"/"+rules.length);
				var tempStr = "";
				var processRage = ((i+1)*100.0)/rules.length;
				processRage/=2;
				for(var k = 0;k<processRage;k++){
					tempStr += "=";
				}
				for(;processRage<50;processRage++){
					tempStr += ">";
				}
				$("#progressInfo").html(tempStr);
				buildCode(i+1);
			});
		}
		function appendToMsgContent(msg){
			$("#msgContent").html($("#msgContent").html()+msg);
		}
		$(function(){
			$( ".button" ).button({
		      icons: {
		        primary: "ui-icon-arrowthickstop-1-s"
		      },
		      text: true
		    });
			
			$(".rule_item").mouseover(function(){
				$(".rule_item").css("color","black");
				$(this).css("color","red");
			}).mouseout(function(){
				$(this).css("color","black");
			});
			
			var tempStr = "";
			for(var k = 0;k<50;k++){
				tempStr += ">";
			}
			$("#progressInfo").html(tempStr);
			
			$("#selectAll").click(function(){
				if(!$(this).attr("checked")){
					$("input[name=ruleIds]").each(function(i){
						$(this).removeAttr("checked");
					});
				}else{
					$("input[name=ruleIds]").each(function(i){
						$(this).attr("checked","checked");
					});
				}
			});
			
			$("#packageId").change(function(){
				var packageId = $(this).val();
				if(!packageId  || packageId==''){
					return;
				}
				$.get("${basePath}ajax/getEntityByPackageId.action?id=" + packageId,function(d){
					var entities = eval("("+d+")");
					var tbody = $("#entityTable").find("tbody").first();
					for(var i = 0 ;i<entities.length; i++){
						var tr = $("<tr/>");
						var td = $("<td/>");
						td.append("<input type='checkbox' name='entityIds' value='" + entities[i].id+"' id='entity_"+entities[i].id+"'/><label for='entity_"+entities[i].id+"'>"+entities[i].name+"</label>");
						tr.append(td).appendTo(tbody);
					}
				});
				
			});
			
		});
	</script>
</html>

