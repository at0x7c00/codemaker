<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<script>
	$(function(){
		$( "#dialog-confirm" ).dialog({
				resizable: false,
				height:160,
				width:320,
				modal: true,
				buttons: {
					"确认": function() {
						$( this ).dialog( "close" );
						history.back();
					},
					'取消': function() {
						$( this ).dialog( "close" );
						history.back();
					}
				}
			});
	});
</script>
<!-- 退出提示对话框 -->
	<div id="dialog-confirm" title="提示" style="display:none;">
		<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>删除失败，您删除的记录中有数据正在被使用！</p>
	</div>