<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
		var pageCount=${pageBean.pageCount} <c:if test="${empty pageBean}">0</c:if>;
		function gotoPageNum( pageNum ){
		    if(pageNum==null){
		       return;
		    }
		    if(pageNum<1)
		       pageNum=1;
		    if(pageNum>pageCount)
		       pageNum=pageCount;
		       var pageNumObj= document.getElementsByName('pageNum')[0];
		    if(pageNumObj!=null){
		     	pageNumObj .value=pageNum-1;
		    }
			document.getElementById("listForm").pageNum.value = pageNum-1;
			document.getElementById("listForm").submit();
			return false;
		}
		function go(){
		    gotoPageNum(jQuery("#goPage").val());
		}
</script>
<script type="text/javascript" src="${basePath}js/common/forList.js"></script>
