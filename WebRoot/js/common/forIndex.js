/**
 * 首页JS库
 */
 
       
 		$(function(){
			var urlContent;
			var urlTitle;
			var tab_counter = 2;
			// tabs init with a custom tab template and an "add" callback filling in the content
			var $tabs = $("#tabs").tabs({
				tabTemplate: "<li><a href='\#{href}'_id='\#{href}'>\#{label}</a> <span class='ui-icon ui-icon-close'>&nbsp;</span></li>",
				add: function( event, ui ) {
				    if(urlContent.indexOf("?")<0){
				    	urlContent+="?";
				    }else{
				    	urlContent+="&"; 
				    }
				    urlContent+="styleName="+$("#styleSelect").attr("value");
					$( ui.panel ).append( "<iframe  src='"+urlContent+"' width='100%' height='"+(getHeightAndWidth().height*0.75)+"' frameborder='0'></iframe>");
					$(ui.panel).css("margin","0px");
					$(ui.panel).css("padding","0px");
					$tabs.tabs('select', '#' + ui.panel.id);
				},
				load:function(event,ui){
					alert();
				}
		});
		
		// actual addTab function: adds new tab using the title input from the form above
		function addTab(title,url) {
			urlContent=url;
			urlTitle=title;
			var isOpened=false;
			var tabs=$("#tabs");
			var tabsTabs=$("#tabs").find("a[href]");
			var i = 1;
		    for(;i<=tabsTabs.length;i++){
		    	if($(tabsTabs[i-1]).html()===urlTitle){
		    		$tabs.tabs('select', tabsTabs[i-1].getAttribute('_id'));
		    		var iframe=$("#tabs>div[id="+tabsTabs[i-1].getAttribute('_id').replace("#","")+"]>iframe");
		    		var src=iframe.attr("src");
		    		if(src.indexOf("?")>=0){
		    			iframe.get(0).src=src+"&time="+new Date().getTime();
		    		}else{
		    			iframe.get(0).src=src+"?time="+new Date().getTime();
		    		}
		    		isOpened=true;
		    		break;
		    	}
		    }
		    if(!isOpened || title=='客户来电'){
				$tabs.tabs( "add", "#tabs-" + tab_counter, title );
				tab_counter++;
		    }
		    return null;
		};
		
		$(".menuA").click(function(){
			try{
				addTab($(this).html(),$(this).attr("href"));
			}catch(e){
				alert(e.message);
			}
			return false;
		});
		$("#tag").focus(function(){
			addTab("客户来电",$(this).attr("url"));
			return true;
		});
			
		// close icon: removing the tab on click
		// note: closable tabs gonna be an option in the future - see http://dev.jqueryui.com/ticket/3924
		$( "#tabs span.ui-icon-close" ).live( "click", function() {
			var index = $( "li", $tabs ).index( $( this ).parent() );
			$tabs.tabs( "remove", index );
		});
				
		$("#userInfoMenu").click(function(){
			addTab("个人设置",'operator_infoSet.action');
		});
		
		$("#tabs-1").css("height",getHeightAndWidth().height*0.7);
		
		if(isTaskTip){
			interval =window.setInterval((checkNewMsg),checkRate);
		}
		function checkNewMsg(){
			$.get(basePath+"message_chatContent.action",{receiverId:receiverId,noPage:true,msgCount:1,onlyReceive:1},function(data,textStatus){
				if(data!=""){ 
					var msgs = eval(data); 
					$("#msgContent").html("");
					for(var i=0;i<msgs.length;i++){
						var cont = msgs[i].content;
						cont = cont.replace("<img.*?>","");
						var $msgA=$("<a/>");
						$msgA.html(msgs[i].sender.name+"：");
						$msgA.attr("点击查看");
						$msgA.attr("href","javascript:void(0);");
						$msgA.attr("receiverId",msgs[i].sender.id*1==-1?"":msgs[i].sender.id);
						$msgA.click(function(){
							addTab("站内信",basePath+"message_index.action?receiverId="+$(this).attr("receiverId"));
							$( "#msgBox" ).hide();
							interval = window.setInterval((checkNewMsg),checkRate);
							isShow = false;
						});
						var $msgDiv=$("<div/>").attr("class","Msg");
						$msgA.appendTo($msgDiv);   
						$msgDiv.append(cont);  
						$msgDiv.appendTo($("#msgContent"));
					} 
					if(msgs.length>0){
						$( "#msgBox" ).show( "fold", {}, 500, null );
						window.clearInterval(interval);
						isShow=true;
					}
				}
			});
	   };
		$( "#accordion" ).accordion({
			autoHeight:false,
			collapsible:true,
			animated: false,
			icons: {
                header: "ui-icon-circle-arrow-e",
                headerSelected: "ui-icon-circle-arrow-s"
            },
            event: "click"
		});
		$( "#accordionResizer" ).resizable({
			minWidth:200
		});
	   
	   $("#infoMenu").click(function(){
			if(isShow){
				$( "#msgBox" ).hide( "fold", {}, 500, null );
				if(isTaskTip){
					interval = window.setInterval((checkNewMsg),checkRate);
				}
				isShow = false;
			}else{
				$( "#msgBox" ).show( "fold", {}, 500, null );
				isShow = true;
			}
		});
		$("#msgBoxBar").click(function(){
			$( "#msgBox" ).hide( "fold", {}, 500, null );
			if(isTaskTip){
				interval = window.setInterval((checkNewMsg),checkRate);
			}
		});
		$("#ocxMenu").click(function(){
			$( "#phoneMenu" ).toggle( "fold", {}, 500, null );
		});
		$("#phoneMenuBar").click(function(){
			$( "#phoneMenu" ).toggle( "fold", {}, 500, null );
		});
		
		//$("#phoneMenu" ).draggable();
		$("#showMenu").click(toggleShowMenu);
		$("#hideMenu").click(toggleHideMenu);
		
		function toggleShowMenu(){
			$("#showMenu").toggle("slide",{},300,function(){
				$("#leftTd").css("width","17%");
				$("#accordionResizer").toggle("drop", {}, 300, null);
				$("#tabs").css("width","100%");
			});
			$(".LogoHead").show("clip",{},300,null);
		};
		 
		function toggleHideMenu(){
			$("#leftTd").css("width","1%");
			$( "#accordionResizer" ).toggle("drop", {}, 300, function(){
				$("#showMenu").toggle("slide",{},300,null);
				$("#tabs").css("width","100%");
				$(".LogoHead").hide("clip",{},300,null);
			});
		};
		
		$("#exitMenu").click(function(){
			$( "#dialog-confirm" ).dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: {
					"确认": function() {
						window.location="loginOut.action";
					},
					"取消": function() {
						$( this ).dialog( "close" );
					}
				}
			});			 
		});
		
		var frameHeight = parseInt(getHeightAndWidth().height*0.75);
		$("#adminFrame").attr("height",frameHeight);
		$("#tabs-1").attr("height",frameHeight).css("height",frameHeight);
		
	});
	
	/**
	 * 选择皮肤
	 * 通过ajax记录用户选择的皮肤属性，记录到数据库
	 * @param {} basePath 根路径
	 * @param {} value 选择框的值
	 */
	function selectStyle(basePath,value){
		$('#styleLink').attr("href","");
		$('#styleLink').attr("href",basePath+"style/themes/"+value+"/jquery.ui.all.css");
		$.get(basePath+"ajax/recordStyleName.action",{styleName:value},function(data,textStatus){
			if(!textStatus){
				alert("ajax请求时无响应。");
			}else{
				window.location.reload();
			}
		});
	}
	