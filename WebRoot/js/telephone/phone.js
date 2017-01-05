/*
 * 呼出
 */
function CallOut(telNumber){
	window.parent.doCall(telNumber);
	jQuery("#dialog-modal").dialog("close");
}
function CallOutWithQuHao(quhaoDiv,telNumber){
	CallOut(jQuery("#"+quhaoDiv).val()+telNumber);
}
function CallDialog(telNumbers){
	jQuery("#dialog-modal").html("");
	var tels = telNumbers.split(";");
	for(var i = 0 ;i<tels.length;i++){
		jQuery("<div>")
		.css("font-size",25)
		.css("font-weight",'bold')
		.html("<img src='images/call.png'/>"+tels[i]+"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='CallOut(\""+tels[i]+"\")'>"+"直接呼叫"+"</a>&nbsp;&nbsp;&nbsp;"+"<a href='javascript:void(0);' onclick='CallOut(\"0"+tels[i]+"\")'>"+"加0呼叫"+"</a>&nbsp;&nbsp;<input id='quhao_"+i+"' type='text' style='width:100px'/><a href='javascript:void(0);' onclick=\"CallOutWithQuHao('quhao_"+i+"','"+tels[i]+"')\">加区号</a>").attr("class","PhoneLine").appendTo(jQuery("#dialog-modal"));
	}
	jQuery("#dialog-modal").dialog({
	   title:'选择呼叫方式',
	   modal: true,
	   width:750,
	   height:500
    });
	//window.parent.doCall(telNumber);
}