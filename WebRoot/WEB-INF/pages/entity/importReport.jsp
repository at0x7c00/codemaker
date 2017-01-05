<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>实体批量导入结果报告</title>
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
		.Record{
			border:1xp dashed gray;
			padding : 10px;
		}
		</style>
		<script type="text/javascript">
			function goToList(){
				$("#formID").get(0).submit();
			}
		</script>
	</head>
</script>
	<body class="ui-widget-content" >
	 <table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>实体批量导入结果报告</td>
	  </tr>
	</table> 
    <table border="0" style="position:absolute;top:0;right:5;">
	    <tr>
			<td class="ui-icon ui-icon-arrowreturnthick-1-w"></td>
			<td>
				<span><a href="javascript:void(0);" onclick="goToList();">返回<a></span> 
			</td> 
		</tr>
	</table>
		<form METHOD="POST" id="formID" action="${basePath}entity_list.action">
		<input type="hidden" name="all" value="${all}"/>
		    <center>
			<div class="ItemBlockBorder">
			    <table width="80%">
			       <tr>
			          <td style="font-size:16px;font-weight:bold;">
			          共<b>${total}</b>条记录,成功导入<b>${success}</b>条记录。耗时<b>${duration}</b>秒。
			          </td>
			          <td align="right">
			              <a href="javascript:void(0);" onclick="$('#msg').toggle();">详细信息</a>
			          </td>
			       </tr>
			    </table>
				<table width="80%" border="0" id="msg" style="display:none;"> 
				<tbody>
					<tr>
					 	<td>
					 	${msg}
					 	</td>
					</tr>
				</tbody>
				</table>
				<div id="InputDetailBar">
					<input type="submit" name="btnSave" value="确 认" class="ButtonStyle" onclick=""/>
				</div>
			</div>
			</center>
		</form>
	</div>
	<div id="dialog-modal" style="display:none;">
	</div>
	</body>
</html>

