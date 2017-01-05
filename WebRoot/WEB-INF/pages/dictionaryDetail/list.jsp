<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/forList.jsp"%>
<html>
	<head>
		<title>数据字典详情--${dic.title}</title>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type">
	</head>
	<body class="ui-widget-content">
	<table width="100%">
	  <tr style="border:1px solid red">
		  <td class="ui-icon ui-icon-circle-triangle-e" width="16px">&nbsp;</td>
		  <td>数据字典详情--${dic.title}</td>
	  </tr>
	</table>
	
	 <table border="0" style="position:absolute;top:0;right:5;">
	    <tr>
			<td class="ui-icon ui-icon-arrowreturnthick-1-w"></td>
			<td>
				<s:a action="dictionary_list.action"
					onclick="return operation('add','listForm','dictionary_list.action','')">
					返 回
				</s:a>  
			</td> 
		</tr>
	</table>
				
		<FORM ACTION="" id="listForm" METHOD="POST" NAME="dataform">
			<input type="hidden" name="parentId" value="${parentId }">
			<input type="hidden" name="pageNum" value="${pageBean.pageNum }">
				<table class="ui-widget ui-widget-content" width="100%">
						<thead>
							<tr class="ui-widget-header" height="30px" id="listTHead">
								<TD ALIGN="CENTER" width="2%"></TD>
								<TD ALIGN="CENTER" width="10%">
									<b>名称</b>
								</TD>
								<TD ALIGN="CENTER" width="10%">
									<b>值</b>
								</TD>
								<TD ALIGN="CENTER" width="10%">
									<b>排序号</b>
								</TD>
								<TD ALIGN="CENTER" width="18%">
									<b>修改</b>
								</TD>
							</TR>
							</thead>
							<tbody id="listTBody">
							<c:if test="${empty pageBean.recordList}">
								<tr>
									<td class="bg" colspan="6" align="center">
										<font style="color: gray; font-size: 12px">暂无记录
										</font>
									</td>
								</tr>
							</c:if>
							<c:forEach items="${pageBean.recordList}" var="dictionaryDetail">
								<TR onmouseout="this.style.backgroundColor='#FFFFFF'"
									onmouseover="this.style.backgroundColor='#BFDFFF'">
									<TD width="2%" ALIGN="CENTER">
										<INPUT TYPE="CHECKBOX" NAME="ids" VALUE="${dictionaryDetail.id }"
											onClick="checkThis('ids','controlCheckbox');">
									</TD>
									<TD width="20%" ALIGN="center">
										${dictionaryDetail.name}
									</TD>
									<TD width="20%" ALIGN="center">
										${dictionaryDetail.value}
									</TD>
									<TD width="20%" ALIGN="center">
										${dictionaryDetail.sort}&nbsp;
									</TD>
									<TD ALIGN="CENTER" width="8%">
										<s:url value="dictionaryDetail_updateUI.action" id="update">
											<s:param name="id">${dictionaryDetail.id}</s:param>
											<s:param name="parentId">${dictionaryDetail.parent.id}</s:param>
										</s:url>
										<s:url value="dictionaryDetail_up.action" id="up">
											<s:param name="id">${dictionaryDetail.id}</s:param>
											<s:param name="parentId">${dictionaryDetail.parent.id}</s:param>
											<s:param name="pageNum">${dictionaryDetail.id}</s:param>
										</s:url>
										<s:url value="dictionaryDetail_down.action" id="down">
											<s:param name="id">${dictionaryDetail.id}</s:param>
											<s:param name="parentId">${dictionaryDetail.parent.id}</s:param>
											<s:param name="pageNum">${dictionaryDetail.id}</s:param>
										</s:url>
										<table>
											<tr>
												<td class="ui-icon ui-icon-pencil">&nbsp;</td>
												<td><s:a href="%{update}">修改</s:a></td>
												<td class="ui-icon ui-icon-arrowthick-1-n">&nbsp;</td>
												<td><s:a href="%{up}">上移</s:a></td>
												<td class="ui-icon ui-icon-arrowthick-1-s">&nbsp;</td>
												<td><s:a href="%{down}">下移</s:a></td>
											</tr>
										</table>
									</TD>
								</TR>
							</c:forEach>
							</tbody>
						</TABLE>
						<jsp:include page="/WEB-INF/pages/public/pageBar.jsp"></jsp:include>
				</center>
			</div>
			<TABLE id="InputDetailBar" WIDTH="100%">
				<TR>
					<TD ALIGN="CENTER">
						<s:a action="dictionaryDetail_addUI.action" cssClass="ButtonStyle"
							onClick="return operation('add','listForm','${basePath }dictionaryDetail_addUI.action','');">
					增 加
				</s:a>
						<s:a action="dictionaryDetail_delete.action"
							cssClass="ButtonStyle"
							onClick="return operation('delete','listForm','${basePath}dictionaryDetail_delete.action','ids')">
					删 除
				</s:a>
					</TD>
				</TR>
			</TABLE>
		</FORM>
	</body>
</html>
