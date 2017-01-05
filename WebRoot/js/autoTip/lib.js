var path;
var key;
var oldKey;
var hidden=true;
var focusRowNum=-1;
var $params;
var $msg_table;
var $outputTag;
var $inputTag;
var doQuery;
var bindIdRecord=[0,0,0,0,0,0,0,0,0,0];//每个页面最多支持10个自动补齐输入框
function isBind(tagId){
   for(var i=0;i<bindIdRecord.length;i++){
      if(tagId==bindIdRecord[i]){
         return true;
      }
   }
   return false;
}
function recordBindId(tagId){
   for(var i=0;i<bindIdRecord.length;i++){
      if(bindIdRecord[i]==0){
         bindIdRecord[i]=tagId;
         break;
      }
   }
}

function bind(params){
    $params=params;
    init();
    if(isBind($params.inputTagId)){
       return;
    }
    recordBindId($params.inputTagId);
	$("#"+$params.inputTagId).keyup(function(arg){//事件处理函数中传递过来的是事件对象。
	     
	   if(arg.keyCode==13){
	      //Enter确认
	      doEnter();
	      return;
	   }
	   //处理方向键
	   if(arg.keyCode==38||arg.keyCode==40){
	     doSelect(arg.keyCode,arg.srcElement );
	     return;
	   }
	   //获取关键字
	   key=$inputTag.val();
	   key=trim(key);
	   if(key==""){
	     hiddenMsgTable();
	     oldKey=key;
	     return;
	   }
	   //关键字没有改变时，返回
	   if(key==oldKey){
	     return;
	   }
	   oldKey=key;
	   if(doQuery){
       		window.clearTimeout(doQuery);
	   }
	   doQuery=window.setTimeout(function(){//延迟400毫秒后访问
	               
	   			   //直到400毫秒后发现关键字还没变，表示用户在等待结果了,发送查询请求
			       $.ajax({
				       type: "POST",//为解决中文乱码问题，必须采用POST方式发送请求
					   url:path+"?t="+new Date().getTime(),
					   data: "key="+key,
					   dataType: "application/x-www-form-urlencoded",//POST方式发送参数key的必添参数
					   async:true,
					   complete:function(xhr, textStatus){
					      if(textStatus=="success"){
					         var xmlDoc=xhr.responseXML;
					         //清除原有信息
					         removeAll();
					         //如果补全信息为空，则隐藏div
					         if($(xmlDoc).find("msg").length==0){//find方法相当有用！！！ 等价于：$("#msg_table tr").length
					             hidden=true;
					             hiddenMsgTable();
					         	 return;
					         }
					         //遍历返回的xml数据，将信息添加到table中
					         //步骤：1、$(xmlDoc)-->将DOM对象转换成JQuery对象，
					         //     2、再查找符合"msg"的所有子元素，
					         //     3、然后使用each方法遍历
					         $($(xmlDoc).find("msg")).each(function(index,domEle){
					              addMsg($(domEle));
					         });
					         //显示补全信息div
					         showMsgTable();
					      }
					   }
			     });
			    $("#"+$params.inputTagId).focus(function(){
			       if($msg_table!=null){
			           showMsgTable();
			       }
			    });
			    $("#"+$params.inputTagId).blur(function(){
			       if($msg_table!=null){
			           hiddenMsgTable();
			        }
			    });
		},1000);
	});
}


function trim(str){  //删除左右两端的空格    
 return str.replace(/(^\s*)|(\s*$)/g, "");    
} 
function addMsg(msg){
   var $tr=$("<tr/>");
   var $td=$("<td/>");
   
   $tr.mouseover(function(){//鼠标经过
	    resetFocusRowNum();//用户鼠标处于补全信息上方时，默认显示高亮行号清空(置-1)
	    focusRowNum=$("#msg_table tr").index(this);//在table中查找次行的行号（最小行号：0） 
	   	this.style.background=$params.msgFocusColor;
	   	this.style.cursor='pointer';
	   	this.style.color='#ffffff';
	   	hidden=false;
	   	//修改key输入框的值：
	   	$("#"+$params.inputTagId).val($(this).text());
	   	//送到自定义鼠标经过函数中的值为json值，用户比较关心这些值
	   	$params.onMouseover(msg.find("json").text());
    });
   
   $tr.mouseout(function(){
   		this.style.background='#ffffff';
   		this.style.color='#000000';
   		hidden=true;
   		$params.onMouseout(msg.find("json").text());
   });
   $tr.click(function(){
       $("#"+$params.inputTagId).val($(this).text());
       hidden=true;
       hiddenMsgTable();
       $params.onClick(msg.find("json").text());
   });
   
   //表格提示为display中的内容
   $td.append(msg.find("display").text());
   
   //将json数据放到td的自定义属性中，以备后用
   $td.attr("jsonValue",msg.find("json").text());
   
   $td.css("height","22px");
   $td.css("font-size","14px");
   $tr.append($td);
   $msg_table.append($tr);
}

function doSelect(keyCode,aa){//方向键移动
         if(keyCode!=38&&keyCode!=40){
		   return;
		 }
	     if(keyCode==38){//上移
	        focusRowNum--;
	        if(focusRowNum<0)
	           focusRowNum=0;
	     }else{//下移
	         focusRowNum++;
	         if(focusRowNum>=$msg_table.find("tr").length){//最大行号拦截，
	             focusRowNum=$msg_table.find("tr").length-1;
	         }
	      }
          clearnColor();
	      if(focusRowNum >=0){
		      var $tr=$($msg_table.find("tr")[focusRowNum]);
		      $tr.css("background",$params.msgFocusColor);
		      $tr.css("color","#ffffff");
		      $("#"+$params.inputTagId).val($tr.text());
	      }
	       
	      $params.doSelect($msg_table.find("tr")[focusRowNum].firstChild.getAttribute("jsonValue"));
}

function doEnter(){
   try{
   	$params.onEnter();
   }catch(e){}
}
function showMsgTable(){
   resetFocusRowNum();//每一次重新显示div之后，默认显示高亮行号清空(置-1)
   if($msg_table.find("tr").length>0){
       $outputTag.css("display","block");
	   $outputTag.css("width",$inputTag.css("width"));
	   $outputTag.css("border","1px solid #817F82");
   }
}

function hiddenMsgTable(){
   if(hidden){
	   $outputTag.css("display","none");
	   $outputTag.css("border","0");
	   resetFocusRowNum();//每一次隐藏div之后，默认显示高亮行号清空(置-1)
   }
}
function removeAll(){
      $outputTag.find("table").empty();
}

function resetFocusRowNum(){
    focurRowNum=-1;
    clearnColor();
}
//将补全信息的表格中的所有高亮颜色换成统一的白色
function clearnColor(){
    $msg_table.find("tr").each(function(index,domEle){
    	$(this).css("background","#ffffff");
    	$(this).css("color","#000000");
    });
}
function createTable(){
	   if($msg_table){
	      $outputTag.find("table").empty();
	   }
	   $msg_table=$("<table/>");
	   $msg_table.css("width",$params.outputTagCss.width);
	   $msg_table.css("color",$params.outputTagCss.color);
	   $msg_table.css("margin",$params.outputTagCss.margin);
	   $msg_table.css("padding",$params.outputTagCss.padding);
	   $msg_table.css("font-size",$params.outputTagCss.fontSize);
	   $msg_table.css("background","#ffffff");
	   $outputTag.append($msg_table);
}
function init(){
       //初始化关键字输入标签
	   $inputTag=$("#"+$params.inputTagId);
	   //初始化请求地址
	   if($params.isDynaPath){
	      path=$("#"+$params.dynaPathTagId).val();
	   }else{
	      path=$params.path;
	   }
	   //初始化补全信息显示标签
	   if($params.outputTagId==""){//如果没有直指定补全信息显示位置，则创建一个div来显示
	       //设置显示位置
	       $outputTag=$("<div/>");
	       $outputTag.css("top",$params.outputTagCss.top);
	       $outputTag.css("left",$params.outputTagCss.left);
	       $outputTag.css("width",$params.outputTagCss.width);
	       $outputTag.css("display","block");
	       $outputTag.css("position",$params.outputTagCss.position);
	       $("body").append($outputTag);
	   }else{
	       $outputTag=$("#"+$params.outputTagId);
	   }
	   //初始化table
	   createTable();
}


