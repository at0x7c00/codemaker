<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<html>
<title>添加单位</title>
<body class="ui-widget-content">

   <!-- 标题及工具按钮 -->
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>部门详情</td>
	  </tr>
	</table>
	
 <table border="0" style="position:absolute;top:0;right:5;">
	    <tr>
			<td class="ui-icon ui-icon-arrowreturnthick-1-w"></td>
			<td>
				<span><a href="javascript:void(0);" onclick="history.back();">返回<a></span> 
			</td> 
		</tr>
	</table>
			
				<div class="ItemBlockBorder">
				<table width="100%">
				<TBODY>
				<tr>
					<td width="105" class="TextStyle" align="right" nowrap height="16" valign="middle" width="10%">
					单位名称：</td>
					<td width="388" class="TextStyle" align="left" valign="middle" width="40%">
						<b>${entity.name }</b>
					</td>
					<td width="190" class="TextStyle" align="right"  valign="middle" width="10%">
					所属单位：</td>
					<td width="516" class="TextStyle" align="left"  valign="middle" width="40%">
	            		 ${empty entity.mainDept?"无":entity.mainDept.name}
		             </td>
				</tr>
				<tr>
					<td width="105" class="TextStyle" align="right" nowrap  valign="middle">
					单位电话1：</td>
					<td width="388" class="TextStyle" align="left"  valign="middle">
						${entity.phoneNumber1 }
           		  </td>
					<td width="190" class="TextStyle" align="right"  valign="middle" nowrap>
					单位电话2：</td>
					<td width="516" class="TextStyle" align="left"  valign="middle">
				  		${entity.phoneNumber2 }
				  </td>
				</tr>
				<tr>
					<td width="105" class="TextStyle" align="right" nowrap  valign="middle">
					单位地址：</td>
					<td width="388" class="TextStyle" align="left"  valign="middle">
						${entity.address }
           		  </td>
					<td width="190" class="TextStyle" align="right"  valign="middle" nowrap>
					负 责 人：</td>
					<td width="516" class="TextStyle" align="left"  valign="middle">
				  		${empty entity.leader ? "无":entity.leader.name}
					</td>
				</tr>
				<tr>
					<td width="105" class="TextStyle" align="right"  valign="middle">
					描述信息：</td>
					<td width="388" colspan='3' class="TextStyle" align="left"  valign="middle">
						${entity.description }
				</tr>
				<tr>
					<td width="105" class="TextStyle" align="right"  valign="middle">
					子 部 门：</td>
					<td width="388" colspan='3' class="TextStyle" align="left" valign="middle">
						<c:forEach items="${entity.subDeptList}" var="tempDept" varStatus="stat">
							${tempDept.name } &nbsp; &nbsp;${stat.index%5==4?"<br/>":"" }
						</c:forEach>
				</tr>
				<tr>
					<td width="105" class="TextStyle" align="right" valign="middle">
					部门人员：</td>
					<td width="388" colspan='3' class="TextStyle" align="left" valign="middle">
						<c:forEach items="${entity.staffs}" var="staff" varStatus="stat">
							${staff.name } &nbsp; &nbsp;${stat.index%5==4?"<br/>":"" }
						</c:forEach>
				</tr>
				</TBODY>
			</table>
			</div>
			
			<div id="InputDetailBar">
				<input type="button" name="btnBack" value="返 回" class="ButtonStyle" onClick="goBack();">
			</div>
</body>
</html>

