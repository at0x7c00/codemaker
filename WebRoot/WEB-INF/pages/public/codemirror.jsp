<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<style type="text/css">
.tip{
	border:1px solid #f0f0f0;
	padding:2px;
	margin:5px;
}
.tip_name{
    color:blue;
	font-size:16px;
	font-weight:bold;
}
.tip_cate{
	color:green;
}
.tip_remark{
	color:gray;
}
.CodeMirror-matchingtag { background: rgba(255, 150, 0, .3); }
</style>

<script src="${basePath}codemirror/lib/codemirror.js"></script>
<link rel="stylesheet" href="${basePath}codemirror/lib/codemirror.css">

<link rel="stylesheet" href="${basePath}codemirror/theme/3024-day.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/3024-night.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/ambiance-mobile.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/ambiance.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/base16-dark.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/base16-light.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/blackboard.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/cobalt.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/eclipse.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/elegant.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/erlang-dark.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/lesser-dark.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/midnight.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/monokai.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/neat.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/night.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/paraiso-dark.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/paraiso-light.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/rubyblue.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/solarized.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/the-matrix.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/tomorrow-night-eighties.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/twilight.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/vibrant-ink.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/xq-dark.css">
<link rel="stylesheet" href="${basePath}codemirror/theme/xq-light.css">

<link rel="stylesheet" href="${basePath}codemirror/addon/display/fullscreen.css">
<link rel="stylesheet" href="${basePath}codemirror/addon/dialog/dialog.css">
<link rel="stylesheet" href="${basePath}codemirror/addon/hint/show-hint.css">

<script src="${basePath}codemirror/mode/apl/apl.js"></script>
<script src="${basePath}codemirror/mode/asterisk/asterisk.js"></script>
<script src="${basePath}codemirror/mode/clike/clike.js"></script>
<script src="${basePath}codemirror/mode/clojure/clojure.js"></script>
<script src="${basePath}codemirror/mode/cobol/cobol.js"></script>
<script src="${basePath}codemirror/mode/coffeescript/coffeescript.js"></script>
<script src="${basePath}codemirror/mode/commonlisp/commonlisp.js"></script>
<script src="${basePath}codemirror/mode/css/css.js"></script>
<script src="${basePath}codemirror/mode/d/d.js"></script>
<script src="${basePath}codemirror/mode/diff/diff.js"></script>
<script src="${basePath}codemirror/mode/ecl/ecl.js"></script>
<script src="${basePath}codemirror/mode/erlang/erlang.js"></script>
<script src="${basePath}codemirror/mode/gas/gas.js"></script>
<script src="${basePath}codemirror/mode/gfm/gfm.js"></script>
<script src="${basePath}codemirror/mode/go/go.js"></script>
<script src="${basePath}codemirror/mode/groovy/groovy.js"></script>
<script src="${basePath}codemirror/mode/haml/haml.js"></script>
<script src="${basePath}codemirror/mode/haskell/haskell.js"></script>
<script src="${basePath}codemirror/mode/haxe/haxe.js"></script>
<script src="${basePath}codemirror/mode/htmlembedded/htmlembedded.js"></script>
<script src="${basePath}codemirror/mode/htmlmixed/htmlmixed.js"></script>
<script src="${basePath}codemirror/mode/http/http.js"></script>
<script src="${basePath}codemirror/mode/jade/jade.js"></script>
<script src="${basePath}codemirror/mode/javascript/javascript.js"></script>
<script src="${basePath}codemirror/mode/jinja2/jinja2.js"></script>
<script src="${basePath}codemirror/mode/less/less.js"></script>
<script src="${basePath}codemirror/mode/livescript/livescript.js"></script>
<script src="${basePath}codemirror/mode/lua/lua.js"></script>
<script src="${basePath}codemirror/mode/markdown/markdown.js"></script>
<script src="${basePath}codemirror/mode/mirc/mirc.js"></script>
<script src="${basePath}codemirror/mode/nginx/nginx.js"></script>
<script src="${basePath}codemirror/mode/ntriples/ntriples.js"></script>
<script src="${basePath}codemirror/mode/ocaml/ocaml.js"></script>
<script src="${basePath}codemirror/mode/pascal/pascal.js"></script>
<script src="${basePath}codemirror/mode/perl/perl.js"></script>
<script src="${basePath}codemirror/mode/php/php.js"></script>
<script src="${basePath}codemirror/mode/pig/pig.js"></script>
<script src="${basePath}codemirror/mode/properties/properties.js"></script>
<script src="${basePath}codemirror/mode/python/python.js"></script>
<script src="${basePath}codemirror/mode/q/q.js"></script>
<script src="${basePath}codemirror/mode/r/r.js"></script>
<script src="${basePath}codemirror/mode/rpm/spec/spec.js"></script>
<script src="${basePath}codemirror/mode/rst/rst.js"></script>
<script src="${basePath}codemirror/mode/ruby/ruby.js"></script>
<script src="${basePath}codemirror/mode/rust/rust.js"></script>
<script src="${basePath}codemirror/mode/sass/sass.js"></script>
<script src="${basePath}codemirror/mode/scheme/scheme.js"></script>
<script src="${basePath}codemirror/mode/shell/shell.js"></script>
<script src="${basePath}codemirror/mode/sieve/sieve.js"></script>
<script src="${basePath}codemirror/mode/smalltalk/smalltalk.js"></script>
<script src="${basePath}codemirror/mode/smarty/smarty.js"></script>
<script src="${basePath}codemirror/mode/smartymixed/smartymixed.js"></script>
<script src="${basePath}codemirror/mode/sparql/sparql.js"></script>
<script src="${basePath}codemirror/mode/sql/sql.js"></script>
<script src="${basePath}codemirror/mode/stex/stex.js"></script>
<script src="${basePath}codemirror/mode/tcl/tcl.js"></script>
<script src="${basePath}codemirror/mode/tiddlywiki/tiddlywiki.js"></script>
<script src="${basePath}codemirror/mode/tiki/tiki.js"></script>
<script src="${basePath}codemirror/mode/turtle/turtle.js"></script>
<script src="${basePath}codemirror/mode/vb/vb.js"></script>
<script src="${basePath}codemirror/mode/vbscript/vbscript.js"></script>
<script src="${basePath}codemirror/mode/velocity/velocity.js"></script>
<script src="${basePath}codemirror/mode/verilog/verilog.js"></script>
<script src="${basePath}codemirror/mode/xml/xml.js"></script>
<script src="${basePath}codemirror/mode/xquery/xquery.js"></script>
<script src="${basePath}codemirror/mode/yaml/yaml.js"></script>
<script src="${basePath}codemirror/mode/z80/z80.js"></script>

<script src="${basePath}codemirror/addon/hint/anyword-hint.js"></script>
<script src="${basePath}codemirror/addon/hint/html-hint.js"></script>
<script src="${basePath}codemirror/addon/hint/javascript-hint.js"></script>
<script src="${basePath}codemirror/addon/hint/pig-hint.js"></script>
<script src="${basePath}codemirror/addon/hint/python-hint.js"></script>
<script src="${basePath}codemirror/addon/hint/show-hint.js"></script>
<script src="${basePath}codemirror/addon/hint/xml-hint.js"></script>

<script src="${basePath}codemirror/addon/fold/xml-fold.js"></script>
<script src="${basePath}codemirror/addon/selection/active-line.js"></script>
<script src="${basePath}codemirror/addon/edit/closetag.js"></script>
<script src="${basePath}codemirror/addon/edit/matchtags.js"></script>
<script src="${basePath}codemirror/addon/display/fullscreen.js"></script>
<script src="${basePath}codemirror/addon/display/placeholder.js"></script>
<script src="${basePath}codemirror/addon/dialog/dialog.js"></script>
<script src="${basePath}codemirror/addon/search/searchcursor.js"></script>
<script src="${basePath}codemirror/addon/search/search.js"></script>
<script src="${basePath}codemirror/addon/runmode/runmode.js"></script>