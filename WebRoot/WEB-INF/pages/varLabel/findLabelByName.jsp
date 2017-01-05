<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
[<c:forEach items="${varLabels}" var="label" varStatus="s">{
name:"${label.name}",category:${label.category},remark:"${label.remark}"
}${s.last ? '':','}
</c:forEach>]