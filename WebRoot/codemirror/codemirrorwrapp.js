var timeoutfn;
var keyword='';
$(function(){
	$("#myEditor").on('keyup',function(e){
		showTip(this);
	});
	randTextarea();
});
function showTip() {
	window.clearTimeout(timeoutfn);
	timeoutfn = window.setTimeout(function(){
		if(keyword==''){
			return;
		}
		$.get(basePath+"varLabel_findLabelByName.action?keyWord="+keyword,function(vars){
			var labels = eval("("+vars+")");
			var tip = $("#tip");
			tip.html("");
			for(var i = 0 ; i<labels.length;i++){
				addTip(labels[i].name,(labels[i].category==0?'[内置]':'[自定义]'),labels[i].remark);
			}
		});
	}, 500);
	
}
var editor = null;
function randTextarea(){
        CodeMirror.commands.autocomplete = function(cm) {
            CodeMirror.showHint(cm, CodeMirror.hint.javascript);
        }
	    editor = CodeMirror.fromTextArea(document.getElementById("myEditor"), {
        lineNumbers: true,
        mode: $("#mode").val() || "application/x-ejs",
        indentUnit: 4,
        indentWithTabs: true,
        lineWrapping: false,
        enterMode: "keep",
        styleActiveLine: true,
        tabMode: "shift",
        matchTags: true,
        autoCloseTags: true,
        theme : $("#theme").val() || "eclipse",
        extraKeys:{
	        "F11": function(cm) {
	          cm.setOption("fullScreen", !cm.getOption("fullScreen"));
	        },
	        "Esc": function(cm) {
	          if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
	        },
	        "Ctrl-J": "toMatchingTag",
	        "Alt-/": "autocomplete"
        }
     });
     editor.getWrapperElement().style["font-size"] = $("#selectFontSize").val();
	 editor.getWrapperElement().style["font-family"] = "Courier New";
	 
	 editor.on("change",function(from,to){
		//log(from["text"]+"<br/>");
	    var text =  filter(to["text"]); 
	    if(text==''){
			keyword = '';    	    
	    }else{
    	 	keyword += text;
	    }
	    showTip();
	 });
}

function refresh(){
	if(editor){
		editor.setOption("theme",$("#theme").val() || "eclipse");
		editor.setOption("mode",$("#mode").val() || "application/x-ejs");
		editor.setOption("lineWrapping",$("#rendLine").attr("checked"));
		editor.getWrapperElement().style["font-size"] = $("#selectFontSize").val();
		editor.getWrapperElement().style["font-family"] = "Courier New";
		editor.refresh();
	}
}
	    
function addTip(name,cate,remark){
	var tip = $("<div>");
	tip.attr("class","tip");
	var namespan = $("<span>");
	var catespan = $("<span>");
	var remarkspan = $("<span>");
	namespan.attr("class","tip_name");
	catespan.attr("class","tip_cate");
	remarkspan.attr("class","tip_remark");
	namespan.html(name);
	catespan.html(cate);
	remarkspan.html(remark);
	tip.append(namespan).append(catespan).append(remarkspan).appendTo($("#tip"));
	
	$(tip).mouseover(function(){
		$(this).css("background","#f0f0f0");
	}).mouseout(function(){
		$(this).css("background","#ffffff");
	});
}
function filter(to){
	var strs = [' ','(',')','[',']','{','}','\'','"','<','>','\t','@','#','$','%','&','*','-','+',';','?','\\','|','/'];
	for(var i = 0;i<strs.length;i++){
		if(to==strs[i]){
			return ''
		}
	}
	return to;
}
function log(info){
	$("#logInfo").html(info);
}