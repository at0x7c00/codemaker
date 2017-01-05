<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
       <link rel="stylesheet" href="${basePath}js/jquery/validate/css/validationEngine.jquery.css" type="text/css"/>
        <link rel="stylesheet" href="${basePath}js/jquery/validate/css/template.css" type="text/css"/>
        <script src="${basePath}js/jquery/validate/languages/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="utf-8"></script>
        <script src="${basePath}js/jquery/validate/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
<!--
           jQuery(document).ready( function() {
          	 try{
                	jQuery("#formID").validationEngine();
                }catch(e){
                    alert(e.description);
                }
            });
//-->
</script>
	<div id="wait_to_submit_dialog" title="提交中，请稍后..." style="display:none;">
	<center>
		<p>
		<img src="${basePath}images/wait.gif"/><br/>
		</p>
	</center>
	</div>
