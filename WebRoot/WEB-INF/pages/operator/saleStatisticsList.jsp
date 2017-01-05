<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>客服销量统计</title>
		<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
	</head>

	<body>
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
						客服销量统计						
				</div>
				<div id="Title_End"></div>
			</div>
			</td>
			<td>
			   <input type="button" value="返回" class="ButtonStyle" onclick="javascript:window.history.go(-1);"/>
			</td>
			   </tr>
			</table>
		</div>
		
			<center><b>${title}</b></center>
			<table cellspacing="0" cellpadding="0" class="TableStyle">
			        <!-- 表头-->
			        <thead>
			            <tr align=center valign=middle id=TableTitle>
								<td  class="bg">
									<b>客服姓名</b>
								</td>
								<td  class="bg">
									所属部门
								</td>
								<td  class="bg">
									订单数量
								</td>
								<td  class="bg">
									交易金额
								</td>
								<td  class="bg">
									退货金额
								</td>
								<td  class="bg">
									成功交易金额
								</td>
							</tr>
							</thead>
							<tbody id="TableData" class="dataContainer">
							<c:if test="${empty salesRecordList}">
							    <tr>
							        <td colspan="7" bgcolor="#FFFFFF" align="center">
							            <font style="color: red"><b>暂不存在任何客服销量信息</b>
										</font>
								    </td>
							    </tr>
							</c:if>
							<c:forEach items="${salesRecordList}" var="salesRecord">
								<tr bgcolor="#FFFFFF" align="center"
									onmouseout="this.style.backgroundColor='#FFFFFF'"
									onmouseover="this.style.backgroundColor='#BFDFFF'">
									<td>
										${salesRecord.operator.name}
									</td>
									<td>
										${salesRecord.operator.dept.name }
										<c:if test="${empty salesRecord.operator.dept}">
										   <font style="color:red">未知</font>
										</c:if>
									</td>
									<td>
										<c:out value="${salesRecord.orderCount}"/>
									</td>
									<td>
										<c:out value="${salesRecord.totalPrice}"/>
									</td>
									<td>
										<c:out value="${salesRecord.beCanclePrice}"/>
									</td>
									<td>
										<c:out value="${salesRecord.successPrice}"/>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			<br/>
			<li>【注】列表情况下，不符合条件和没有销售记录的客服不予显示。</li>
	</body>
</html>