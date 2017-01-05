<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/public/forAll.jsp"%>
<%@include file="/WEB-INF/pages/public/jqueryValidate.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>添加模板</title>
		<%@include file="/WEB-INF/pages/public/codemirror.jsp" %>
	</head>
	<body class="ui-widget-content">

		<table width="100%">
			<tr style="border: 1px solid red">
				<td class="ui-icon ui-icon-circle-triangle-e" width="16px">
					&nbsp;
				</td>
				<td>
					添加模板
				</td>
			</tr>
		</table>
		<form METHOD="POST" NAME="fmOperator"
			style="margin-top: 15; margin-bottom: 0" id="formID"
			action="${basePath }template_add.action">
			<input type="hidden" name="pageSize" value="${pageSize}">
			<input type="hidden" name="pageNum" value="${pageNum}">
			名称：<input name="template.name" id="template.name" type="text"
									size="15" class="InputStyle" style="width:20%"
									vl="validate[optional,maxSize[50]]" />
			所属组：<select name="groupId"  style="width:80px">
									<option value="-1">-全部-</option>
									<c:forEach items="${groupList}" var="group">
										<option  value="${group.id}" ${template.group.id==group.id?'selected':'' }> ${group.name}</option>
									</c:forEach>
								</select>
			类型：<select name="template.type"  style="width:80px">
					<option value="1" ${template.type==1?'selected':''}>JSP</option>
					<option value="2" ${template.type==2?'selected':''}>Java</option>
				  </select>
		     样式：<select style="width:80px" id="theme" onchange="refresh()">
				 	<option value="3024-day">3024-day</option>
					<option value="3024-night">3024-night</option>
					<option value="ambiance-mobile">ambiance-mobile</option>
					<option value="ambiance">ambiance</option>
					<option value="base16-dark">base16-dark</option>
					<option value="base16-light">base16-light</option>
					<option value="blackboard">blackboard</option>
					<option value="cobalt">cobalt</option>
					<option value="eclipse" selected="selected">eclipse</option>
					<option value="elegant">elegant</option>
					<option value="erlang-dark">erlang-dark</option>
					<option value="lesser-dark">lesser-dark</option>
					<option value="midnight">midnight</option>
					<option value="monokai">monokai</option>
					<option value="neat">neat</option>
					<option value="night">night</option>
					<option value="paraiso-dark">paraiso-dark</option>
					<option value="paraiso-light">paraiso-light</option>
					<option value="rubyblue">rubyblue</option>
					<option value="solarized">solarized</option>
					<option value="the-matrix">the-matrix</option>
					<option value="tomorrow-night-eighties">tomorrow-night-eighties</option>
					<option value="twilight">twilight</option>
					<option value="vibrant-ink">vibrant-ink</option>
					<option value="xq-dark">xq-dark</option>
					<option value="xq-light">xq-light</option>
			 	</select>
			模式：<select style="width:80px" id="mode" onchange="refresh()">
					<option value="text/apl">apl</option>
					<option value="text/asterisk">asterisk</option>
					<option value="text/x-csrc">clike-C</option>
					<option value="text/x-c++src">clike-C++</option>
					<option value="text/x-java">clike-Java</option>
					<option value="text/x-clojure">clojure</option>
					<option value="text/x-cobol">cobol</option>
					<option value="text/x-coffeescript">coffeescript</option>
					<option value="text/x-common-lisp">commonlisp</option>
					<option value="text/css">css</option>
					<option value="text/x-d">d</option>
					<option value="text/x-diff">diff</option>
					<option value="text/x-ecl">ecl</option>
					<option value="text/x-erlang">erlang</option>
					<option value="text/x-gas">gas</option>
					<option value="text/x-gfm">gfm</option>
					<option value="text/x-go">go</option>
					<option value="text/x-groovy">groovy</option>
					<option value="text/x-haml">haml</option>
					<option value="text/x-haskell">haskell</option>
					<option value="text/x-haxe">haxe</option>
					<option value="application/x-ejs" selected="selected">htmlembedded</option>
					<option value="text/html">htmlmixed</option>
					<option value="message/http">http</option>
					<option value="text/x-jade">jade</option>
					<option value="text/javascript">javascript</option>
					<option value="jinja2">jinja2</option>
					<option value="text/x-less">less</option>
					<option value="text/x-livescript">livescript</option>
					<option value="text/x-lua">lua</option>
					<option value="text/x-markdown">markdown</option>
					<option value="text/mirc">mirc</option>
					<option value="text/nginx">nginx</option>
					<option value="text/n-ntriples">ntriples</option>
					<option value="text/x-ocaml">ocaml</option>
					<option value="text/x-pascal">pascal</option>
					<option value="text/x-perl">perl</option>
					<option value="application/x-httpd-php">php</option>
					<option value="text/x-pig">pig</option>
					<option value="text/x-properties">properties</option>
					<option value="text/x-python">python</option>
					<option value="text/x-q">q</option>
					<option value="text/x-rsrc">r</option>
					<option value="text/x-rpm-spec">rpm</option>
					<option value="text/x-rst">rst</option>
					<option value="text/x-ruby">ruby</option>
					<option value="text/x-rustsrc">rust</option>
					<option value="text/x-sass">sass</option>
					<option value="text/x-scheme">scheme</option>
					<option value="text/x-sh">shell</option>
					<option value="application/sieve">sieve</option>
					<option value="text/x-stsrc">smalltalk</option>
					<option value="text/x-smarty">smarty</option>
					<option value="text/x-smarty">smartymixed</option>
					<option value="application/x-sparql-query">sparql</option>
					<option value="text/x-mariadb">sql</option>
					<option value="text/x-stex">stex</option>
					<option value="text/x-tcl">tcl</option>
					<option value="text/x-tiddlywiki">tiddlywiki</option>
					<option value="tiki">tiki</option>
					<option value="text/turtle">turtle</option>
					<option value="text/x-vb">vb</option>
					<option value="text/vbscript">vbscript</option>
					<option value="text/velocity">velocity</option>
					<option value="text/x-verilog">verilog</option>
					<option value="application/xml">xml</option>
					<option value="application/xquery">xquery</option>
					<option value="text/x-yaml">yaml</option>
					<option value="text/x-z80">z80</option>
				</select>
			字体：
			<select onchange="refresh()" id="selectFontSize" style="width:80px">
		          <option value="13px">13px</option>
		          <option value="14px">14px</option>
		          <option value="16px">16px</option>
		          <option value="18px" selected="selected">18px</option>
		          <option value="20px">20px</option>
		          <option value="24px">24px</option>
		          <option value="26px">26px</option>
		          <option value="28px">28px</option>
		          <option value="30px">30px</option>
		          <option value="32px">32px</option>
		          <option value="34px">34px</option>
		          <option value="36px">36px</option>
		        </select>
		    自动换行<input type="checkbox" onclick="refresh();" id="rendLine">
			<input type="submit" name="btnSave" value="保 存" class="ButtonStyle" />
			<input type="button" name="btnBack" value="返 回" class="ButtonStyle"
					onClick="history.back();">
			<div class="ItemBlockBorder">
				<div class="ItemBlock">
					<table align="center" class="mainForm" style="width: 100%;">
						<tr>
							<td>
								<textarea name="template.content" id="myEditor"
									cols="500" rows="20" class="TextareaStyle" onmouseup="showTip(this)" placeholder="输入模板值..."
									vl="validate[optional]" style="width:100%;"><c:out value="${template.content}"/></textarea>
							</td>
						</tr>
						<tr></tr>
					</table>
                </div>
            </div>
		</form>
		<div id="logInfo"></div>
		<div class="ui-widget" style="width:98%" id="tip">
			<div  id="tipcontent">
			
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var basePath = '${basePath}';
	</script>
	<script type="text/javascript" src="${basePath}codemirror/codemirrorwrapp.js"></script>
</html>

