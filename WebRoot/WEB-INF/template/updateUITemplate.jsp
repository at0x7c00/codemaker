<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/jsp/common/taglib.jsp"%>
<div class="pageContent">
	<form:form method="post" action="BEAN_NAME/update.do" cssClass="pageForm required-validate" commandName="BEAN_NAME" onsubmit="return validateCallback(this, navTabAjaxDone);">
	    <input type="hidden" value="${rel}" name="rel"/>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
				</li>
			</ul>
		</div>
		<div class="pageFormContent" layoutH="56">
			<form:input type="hidden" path="ENTITY_ID_PROP_NAME" value="${BEAN_NAME.BEAN_ID_NAME}"/>
			UPDATE_INPUT_TRS
		</div>
	</form:form>
</div>
