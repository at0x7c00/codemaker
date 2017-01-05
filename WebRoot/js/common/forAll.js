/**
 * 全局公用JS库
 */

/**
 * 返回
 */
function goBack() {
	window.history.go(-1);
	//window.location.back();
}


function browserCheck(){
       var Sys = {};
       var ua = navigator.userAgent.toLowerCase();
       try{  
	       if (window.ActiveXObject)
	           Sys.ie = ua.match(/msie ([\d.]+)/)[1]
	       else if (ua.indexOf('firefox')>=0)
	           Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]
	       else if (window.MessageEvent && !document.getBoxObjectFor){
	           Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
	       }else if (window.opera)
	           Sys.opera = ua.match(/opera.([\d.]+)/)[1]
	       else if (window.openDatabase)
	           Sys.safari = ua.match(/version\/([\d.]+)/)[1];
       }catch(e){
           alert('exception when checkbrowser:'+e.message);
       }
       return Sys;
}

function isEmpty(value) {
	if (value == "" || trim(value) == "") {
		return true;
	}
	return false;
}

jQuery(function(){
	jQuery( "#InputDetailBar a, input[type='button'],input[type='submit'],input[type='reset']").button();
	
});

function getHeightAndWidth(){
	var heightValue;
	var widthValue;
	if(jQuery.browser.msie){ 
		heightValue = document.compatMode == "CSS1Compat"? document.documentElement.clientHeight : document.body.clientHeight; 
	} 
	else { 
		heightValue =  self.innerHeight; 
	} 
	if(jQuery.browser.msie){ 
		widthValue = document.compatMode == "CSS1Compat"? document.documentElement.clientWidth : document.body.clientWidth; 
	} 
	else{ 
		widthValue = self.innerWidth; 
	} 
	return {width:widthValue,height:heightValue};
}

/**
 * 展示问题
 * @param title
 * @param answers
 * @return
 */
function getQuestionHtml(title,answersStr){
	var htmlStr = title || '';
	//answers = eval(answersStr);
	var answers = eval(answersStr);
    if(jQuery.trim(answersStr) != '' && answers && answers.length > 0){
    	htmlStr += '<br/>';
        for(var i = 0;i < answers.length;i++){
        	htmlStr += answers[i].id + '. ' + answers[i].content;
        	if(answers[i].type == 1)
        		htmlStr += '<input type="text" class="Others" disabled/>';
        	htmlStr += '<br/>';
        }
    }
    return htmlStr;
}
//弹出提示信息
function alertMsg(msg){
    jQuery('#dialog-modal').html( msg );
    jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
    jQuery( '#dialog-modal' ).dialog({
        title:'提示',
		height: 180,
		width:280,
		modal: true ,
		autoOpen: true,
		buttons: {
			"确定": function() {
    			jQuery( this ).dialog( 'close' );
			}
		}
	});
}
function alertUnCloseableMsg(msg){
    jQuery('#dialog-modal').html( msg );
    jQuery( "#dialog:ui-dialog" ).dialog( "destroy" );
    jQuery( '#dialog-modal' ).dialog({
        title:'提示',
		height: 180,
		width:280,
		modal: true ,
		autoOpen: true,
		buttons: {
			
		}
	});
}
//显示提示信息
function showMsg(msg){
    jQuery('#dialog-modal').html( msg );
    jQuery( '#dialog-modal' ).dialog({
    	title:'提示',
		height: 100,
		width:160,
		modal: false ,
		autoOpen: true
	});
    setTimeout(closeDialog,2000);
}
function closeDialog(){
	jQuery('#dialog-modal').fadeOut(500,function(){jQuery('#dialog-modal').dialog('close');});
}
function openNewWin(url){
	var a = getHeightAndWidth();
	window.showModalDialog(url,"","dialogWidth:"+parseInt(a.width*0.8)+"px;dialogHeight:"+a.height+"px;scroll:auto;status:no;resizable:yes;"); 
	return false;
}