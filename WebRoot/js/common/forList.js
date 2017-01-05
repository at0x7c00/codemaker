 /**
 * 列表页JS库
 */
 
 /**
  * 为List页面的表格行添加鼠标悬停高亮效果
  */
 jQuery(function(){
	jQuery("#listTBody tr").mouseover(function(){
	    jQuery(this).css("background-color",jQuery("#listTHead").css("background-color"));
	    jQuery(this).css("color",jQuery("#listTHead").css("color"));
	    jQuery(this).find("a").css("color",jQuery("#listTHead").css("color"));
	}).mouseout(function(){
	    jQuery(this).css("background","")
	    jQuery(this).css("color","")
	    jQuery(this).find("a").css("color","");
	});
	
	jQuery("#listTHead").css("font-size","12px");
	jQuery(".sortable").css("cursor","pointer")
	.click(function(){
		jQuery("#orderBy").val(jQuery(this).attr("sort"));
		if(jQuery(this).attr("direction")=='asc'){
			jQuery("#direction").val("desc");
		}else{
			jQuery("#direction").val("asc");
		}
		jQuery("#listForm").get(0).submit();
	});
 });
 
 /**
 * 添加/删除操作
 * @param {}
 *            type
 * @param {}
 *            formId
 * @param {}
 *            url
 * @param {}
 *            checkBoxName
 */
function operation(type, formId, url, checkBoxName) {
	var formAction = document.getElementById(formId);
	// 获取所有复选框
	var checkBoxs = document.getElementsByName(checkBoxName);
	formAction.action = url;
	if (type == "delete") {// 删除
		// 检查是否选择了记录
		var selected = false;
		for (var x = 0; x < checkBoxs.length; x++) {
			if (checkBoxs[x].checked) {
				selected = true;
				break;
			}
		}
		if (!selected) {
			alert("\u8bf7\u9009\u62e9\u8981\u5220\u9664\u7684\u8bb0\u5f55");
		} else {
			// 确认对话框
			if (window.confirm("\u771f\u7684\u8981\u5220\u9664\u5417?")) {
				if(document.getElementById("dialog-modal")!=null){
					alertUnCloseableMsg("正在删除...");
				}
				formAction.submit();
			}
		}
	}else if(type=="unDispatch"){
		// 检查是否选择了记录
		var selected = false;
		for (var x = 0; x < checkBoxs.length; x++) {
			if (checkBoxs[x].checked) {
				selected = true;
				break;
			}
		}
		if (!selected) {
			alert("请选择要撤销的记录！");
		} else {
			// 确认对话框
			if (window.confirm("确实要撤销所选答卷的分配吗?")) {
				formAction.submit();
			}
		}
	}else if(type=="updateArea"){
		// 检查是否选择了记录
		var selected = false;
		for (var x = 0; x < checkBoxs.length; x++) {
			if (checkBoxs[x].checked) {
				selected = true;
				break;
			}
		}
		if (!selected) {
			alert("请选择要修改的记录！");
		} else {
			jQuery("#areaSelect-dialog").dialog({
				height:300,
				width:650,
				modal: true ,
				autoOpen: true,
				buttons:[
				{
					text:'提交',
					click:function(){
						if(!jQuery("#saleAreaId").val()){
							alert("请选择一个大区");
							return;
						}
						if(!jQuery("#saleTeamId").val()){
							alert("请选择一个团队");
							return;
						}
						jQuery("#areaSelect-dialog").dialog("close");
						alertUnCloseableMsg("正在提交...");
						formAction.submit();
					}
				},{
					text:'取消',
					click:function(){
						jQuery("#areaSelect-dialog").dialog("close");
					}
				}
				]
			});
		}
	}else {
		formAction.submit();
	}
	return false;
}
/**
 * 复选框全选
 */
var checkBoxSelected = false;//是否全被选中
function selectAll(checkBoxName) {
	// 获取所有复选框
	var checkBoxs = document.getElementsByName(checkBoxName);
	var controlCheckboxs = document.getElementsByName("controlCheckbox");
	if (checkBoxSelected) {
		for (var x = 0; x < checkBoxs.length; x++) {
			checkBoxs[x].checked = false;
		}
		checkBoxSelected = false;
		if(controlCheckboxs[0]){
		   controlCheckboxs[0].checked = false;
	    }
		if(controlCheckboxs[1]){
			controlCheckboxs[1].checked = false;
	    }
	} else {
		for (var x = 0; x < checkBoxs.length; x++) {
			checkBoxs[x].checked = true;
		}
		checkBoxSelected = true;
		if(controlCheckboxs[0]){
			controlCheckboxs[0].checked = true;
		}
		if(controlCheckboxs[1]){
			controlCheckboxs[1].checked = true;
		}
	}
}
/**
 * 单选复选框，影响到全选复选框
 * 
 * @param {}
 *            checkBoxName 单选框们的名字
 * @param {}
 *            selectAllName 全选复选框的名字
 */
function checkThis(checkBoxName, selectAllName) {
	var allSelected = true;
	var checkBoxs = document.getElementsByName(checkBoxName);
	var selectAllObj = document.getElementById(selectAllName);
	var controlCheckboxs = document.getElementsByName("controlCheckbox");
	for (var x = 0; x < checkBoxs.length; x++) {
		if (checkBoxs[x].checked == false) {
			allSelected = false;
			break;
		}
	}
	if(selectAllObj){
		if(allSelected){
			 selectAllObj.checked=true;
		}else{
			 selectAllObj.checked=false;
		}
	}
}