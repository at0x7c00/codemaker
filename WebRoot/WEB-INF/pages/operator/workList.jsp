<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
	<head>
		<title>操作员业务统计</title>
		<script language="javascript">
		      function printScreen(block)
		      {
		        var value = $(block).innerHTML;
		        var printDetail = window.open("","printDetail","");
		        printDetail.document.open();
		        printDetail.document.write("<HTML><head><style media=print>.dataTable tr{font-size:15px;} .dataTable{width:98%;border-bottom:1px solid #000000;border-right:1px solid #000000;border-collapse:collapse;margin:0 10px;font-size:12px;color:#000000;background:#fff;} .noPrint{display:none;}.PageNext{page-break-after: always;}</style></head><BODY>"); 
		        printDetail.document.write("<PRE>");
		        printDetail.document.write(value);
		        printDetail.document.write("</PRE>");
		        printDetail.document.write("<center class='noPrint'><OBJECT id='WebBrowser' classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height=0 width=0></OBJECT>");
				printDetail.document.write("<input type='button' value='打印' class='ButtonStyle' onclick=\"document.getElementById('WebBrowser').ExecWB(6,1)\">&nbsp;"); 
				printDetail.document.write("<input type='button' value='直接打印' class='ButtonStyle' onclick=\"document.getElementById('WebBrowser').ExecWB(6,6)\">&nbsp;");
				printDetail.document.write("<input type='button' value='页面设置' class='ButtonStyle' onclick=\"document.getElementById('WebBrowser').ExecWB(8,1)\">&nbsp;");
				printDetail.document.write("<input type='button' value='打印预览' class='ButtonStyle' onclick=\"document.getElementById('WebBrowser').ExecWB(7,1)\"><br/>");
		 		printDetail.document.write("<hr align='center' width='90%' size='1' noshade></center>");
		        printDetail.document.close("</BODY></HTML>");
		      }
		      
		      function showSo(){
		      	$('so').innerText = $('stationSel').options[$('stationSel').selectedIndex].text +' '+ $('operatorSel').options[$('operatorSel').selectedIndex].text;
		      }
		      
		      function showOperator(deptId){
		      	if(deptId == '-1'){
		      		$('operatorSel').length = 0;
		      		$('operatorSel').options.add(new Option('全部人员','-1'));
		      	}else{
		      		var xmlHttp1 = GetXmlHttpObject();
		      		ajax(xmlHttp1,'${basePath}ajax/getOperatorsByDeptId.action?time='+new Date().getTime()+'&deptId=' + deptId,function(){
		    			if (xmlHttp1.readyState==4){
		    			   if(xmlHttp1.responseText!=''){
		    			   	   var result=eval(xmlHttp1.responseText);
			    			   $('operatorSel').length = 0;
		      				   $('operatorSel').options.add(new Option('全部人员','-1'));
		      				   for(var i = 0;i < result.length;i++){
		      				   	  $('operatorSel').options.add(new Option(result[i].name,result[i].id));
		      				   }
		    			   }else{
		    			   	  $('operatorSel').length = 0;
		      				  $('operatorSel').options.add(new Option('全部人员','-1'));
		    			   }
						}
	    			});
		      	}
		      }
		      /**
		 * 进行ajax请求，异步请求完毕后调用指定的方法
		 * @param {} url
		 * @param {} fun
		 */
		function ajax(xmlHttp,url,fun){
			    
				if (xmlHttp==null){
					  alert ("Your browser does not support AJAX!");
					  return;
				}
				if(url.indexOf('?')!=-1){
					url=url+"&time="+new Date().getTime();
				}else{
					url=url+"?time="+new Date().getTime();
				}
				xmlHttp.onreadystatechange=fun;
				xmlHttp.open("GET",url,true);
				xmlHttp.send(null);
		}
		</script>
		<style type="text/css">
		body{
		  padding:0px 5px;
		}
		</style>
	</head>
	<body onload="showSo();">
	<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0> 
	</OBJECT> 
	<div id="Title_bar" >
	<table width="100%" style="border:0px solid #000000">
	<tr align="right">
	<td>
			<div id="Title_bar_Head">
				<div id="Title_Head"></div>
				<div id="Title">
					<!--页面标题-->
					<img border="0" width="13" height="13"
						src="${basePath}images/title_arrow.gif" />
					处理人员业务统计
				</div>
				<div id="Title_End"></div>
			</div>
			</td>
			   </tr>
			</table>
		</div>
	<FORM ACTION="${basePath}operator_workList.action?type=1" id="listForm" METHOD="POST"
		NAME="QueryForm" style="margin-top: 5">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="acceptTypeId" value="${acceptType.id}" />
		
            <div id="TableTail">
       	    <div id="TableTail_inside">
			<table width="100%">
                <tr>
                  <td height="29" align="right" class="TextStyle" nowrap>起始日期：</td>
                  <td align="left" class="TextStyle" nowrap><input type="text" id="startTime" name="startTime" value="${startTime }" size="18" tabindex="1" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" >
                  </td>
                  <td align="right" class="TextStyle" nowrap>截止日期：</td>
                  <td align="left" class="TextStyle" nowrap><input type="text" id="endTime" name="endTime" value="${endTime }" size="19" tabindex="2" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
                  </td>
                  <td width="7%" align="left" class="TextStyle"><div align="right">处理单位：</div></td>
					<td width="13%" class="TextStyle">
						<select name="deptId" id="stationSel" style="width:100%" onchange="showOperator(this.value)">
							<option value="-1">全部单位</option>
						<c:forEach items="${deptList }" var="dept">
								<option value="${dept.id }" ${dept.id==deptId?'selected':'' }>${dept.name }</option>
						</c:forEach>
						</select>
										  
		          </td>
		          <td width="7%" align="right" class="TextStyle">处理人员：</td>
					<td width="13%" class="TextStyle" align="left">
						<select name="id" id="operatorSel" style="width:100%">
							<option value="-1">全部人员</option>
							<c:forEach var="operator" items="${operatorList}">
								<option value="${operator.id }" ${operator.id==id?"selected":"" }>${operator.name}</option>
							</c:forEach>
						</select>
										  
		          </td>
                  <td class="TextStyle">
                    <input TYPE="submit" value="信息统计" class="ButtonStyle">
                  </td>
                </tr>
              </table>
              </div>
              </div>
	</FORM>
	<div id="printTable">
	<FORM ACTION="${basePath}operator_workList.action" METHOD="POST"
		NAME="dataform" style=" margin-bottom: 0">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="acceptTypeId" value="${acceptType.id}" />
		       <center>
		       <B style="color:#000000;font-size:16px;">${startTime }至${endTime } <span id="so"></span> 业务统计列表（共 <FONT COLOR="RED">${fn:length(searchOperator) }</FONT> 条信息）</B>
				<TABLE class="dataTable" style="width:95%" border="1" bordercolor="#000000" bordercolordark="#ffffff" cellpadding="0" cellspacing="0">
								<TR class="TableTDBgStyle" style="background:#ABD1ED;">
									<TD ALIGN="CENTER" nowrap >
										<b>处理人员</b>
									</TD>
									<c:forEach items="${acceptTypeList}" var="acceptType">
									<TD ALIGN="CENTER" nowrap >
										<b>${acceptType.name }</b>
									</TD>
									</c:forEach>
									<TD ALIGN="CENTER" nowrap >
										<b>总计</b>
									</TD>
								</TR>

                                <c:if test="${empty searchOperator}">
									<tr>
									    <td colspan="8"><center><font style="color:red"><b>暂无记录</b></font></center></td>
									</tr>
								</c:if>
								
								<c:forEach items="${searchOperator }" var="oper">
								<TR onmouseout="this.style.backgroundColor='#FFFFFF'"
											onmouseover="this.style.backgroundColor='#BFDFFF'">
									<TD ALIGN="CENTER">
										${oper.name }
									</TD>
									<c:set var="totalCount" value="0"/>
									<c:set var="record" value="${results[oper.id] }"/>
									<c:forEach items="${acceptTypeList}" var="acceptType">
										<TD ALIGN="CENTER">
											<c:if test="${not empty record}">
												${empty record[acceptType.name] ? 0 : record[acceptType.name]}<c:set var="totalCount" value="${totalCount + (empty record[acceptType.name] ? 0 : record[acceptType.name])}"/>
											</c:if>
											<c:if test="${empty record}">
												0
											</c:if>
										</TD>
									</c:forEach>
									<TD ALIGN="center">
										${totalCount}
									</TD>
								</TR>
								</c:forEach>
								<c:if test="${not empty searchOperator}">
								<TR onmouseout="this.style.backgroundColor='#FFFFFF'"
											onmouseover="this.style.backgroundColor='#BFDFFF'">
									<TD ALIGN="CENTER">
										总计
									</TD>
									<c:set value="0" var="totalCount"/>
									<c:forEach items="${acceptTypeList}" var="acceptType">
										<TD ALIGN="CENTER">
											${empty total[acceptType.name] ? 0 : total[acceptType.name] }<c:set var="totalCount" value="${totalCount + (empty total[acceptType.name] ? 0 : total[acceptType.name]) }"/>
										</TD>
									</c:forEach>
									<TD ALIGN="center">
										${totalCount}
									</TD>
								    </TR>
								</c:if>
								<%-- <TR ALIGN="center" VALIGN="top">
									<TD COLSPAN="10" HEIGHT="18">
										<TABLE ALIGN="center" CELLPADDING="0" CELLSPACING="0"
											WIDTH="100%">
											<TR>
												<TD ALIGN="CENTER">
													<jsp:include page="/WEB-INF/pages/public/pageView002.jsp"></jsp:include>
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>--%>
				</TABLE>
				</center>
	</FORM>
	</div>
	<TABLE ALIGN="CENTER" WIDTH="100%">
		<TR>
			<TD ALIGN="CENTER">
				<input type='button' value='打 印' class="ButtonStyle" onclick="printScreen('printTable')">
				<input type='button' value='报 表' class="ButtonStyle" onclick="window.open('${basePath}operator_workListPic.action?type=1','_self');">	 
			</td>
		</tr>
	</TABLE>
	</body>
</html>

