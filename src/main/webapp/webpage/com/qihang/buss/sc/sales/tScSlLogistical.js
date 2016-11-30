

//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

$(function(){

	$("#logisticalName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectLogisticalDialog();
		}
	});
	$("#accountName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectAccountDialog();
		}
	});

	$("#logisticalName").blur(function(){
		checkInput($("#logisticalId"), $("#logisticalName"));
	})

	$("#accountName").blur(function(){
		checkInput($("#accounId"), $("#accounName"));
	})

	var buyType = $("select[name='buyType']").val();
	if(buyType){
		var load = $("#load").val();
		if(!load) {
			$("select[name='buyType']").removeAttr("disabled");
		}
		if (0 == buyType) {
			$(".hideField").removeAttr("style");
		}
	}
	checkcurPayAmount();
})

//选择物流公司
function selectLogisticalDialog(){
	var logisticalName = $("#logisticalName").val();
	var url = 'tScLogisticalController.do?goSelectDialog&name=' + logisticalName;
	var width = 800;
	var height = 500;
	var title = "物流公司";
	$.dialog({
		content: 'url:' + url,
		title: title,
		lock: true,
		zIndex: 700,
		height: height,
		cache: false,
		width: width,
		opacity: 0.3,
		button: [{
			name: '确定',
			callback: function () {
				iframe = this.iframe.contentWindow;
				var item = iframe.getSelectionData();
				$("#logisticalName").val(item.name);
				$("#logisticalId").val(item.id);
				setValOldIdAnOldName($("#logisticalName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//结算账户回车事件
function selectAccountDialog(){
	var accountName = $("#accountName").val();
	var url = 'tScSettleacctController.do?goSelectDialog&name=' + accountName;
	var width = 800;
	var height = 500;
	var title = "结算账户";
	$.dialog({
		content: 'url:' + url,
		title: title,
		lock: true,
		zIndex: 600,
		height: height,
		cache: false,
		width: width,
		opacity: 0.3,
		button: [{
			name: '确定',
			callback: function () {
				iframe = this.iframe.contentWindow;
				var item = iframe.getSelectionData();
				$("#accountId").val(item.id);
				$("#accountName").val(item.name);
				setValOldIdAnOldName($("#accountName"), item.id, item.name);
				$("#curPayAmount").attr("datatype","num");
				checkcurPayAmount();
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}


function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

//弹出界面数据赋值
function checkInput(checkId, checkNameId) {
	if (checkNameId.val() != undefined && checkNameId.val() != "") {
		if (checkNameId.attr("oldid") != undefined && checkNameId.attr("oldid") != "") {
			checkNameId.val(checkNameId.attr("oldname"));
			checkId.val(checkNameId.attr("oldid"));
		} else {
			checkNameId.val("");
			checkId.val("");
		}
	} else {
		checkNameId.attr("oldid", "");
		checkNameId.attr("oldname", "");
		checkId.val("");
		if("accountName" == checkNameId.id){
			$("#curPayAmount").removeAttr("datatype");
		}
	}
}

//当承担方为买家时，开启承担方式
function openBuyType(){
	var bear = $("select[name='bears']").val();
	if(1 == bear){
		$("select[name='buyType']").removeAttr("disabled");
	}else{
		$("select[name='buyType']").attr("disabled","disabled");
		$("select[name='buyType']").val("");
		$("#freight").val("");
		$("#freight").removeAttr("datatype");
		$("#accountId").val("");
		$("#accountName").val("");
		$("#curPayAmount").removeAttr("datatype");
		$("#curPayAmount").val("");
		$(".hideField").css("display","none");
	}
}
//当买家承担方式为卖家垫付时，显示
function showInfo(){
	var buyType = $("select[name='buyType']").val();
	if(0 == buyType){
		$(".hideField").removeAttr("style");
		$("#freight").attr("datatype","vInt");
		checkcurPayAmount();
	}else{
		$("#freight").val("");
		$("#freight").removeAttr("datatype");
		$("#accountId").val("");
		$("#accountName").val("");
		$("#curPayAmount").removeAttr("datatype");
		$("#curPayAmount").val("");
		$(".hideField").css("display","none");
	}
}

function checkSaveValue(){
	var accountId = $("#accountId").val();
	if(accountId){
		var curPayAmount = $("#curPayAmount").val();
		if(curPayAmount <= 0){
			tip("付款金额必须大于0，请确认");
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}

//校验结算账户
function checkcurPayAmount(){
	var accountName = $("#accountName").val();
	if(accountName){
		$("#curPayAmount").removeAttr("readonly");
		$("#curPayAmount").css("background-color","white");
		$("#curPayAmount").attr("datatype","vInt");
	}else{
		$("#curPayAmount").attr("readonly","readonly");
		$("#curPayAmount").css("background-color","rgb(226,226,226)");
		$("#curPayAmount").val("");
		$("#curPayAmount").removeAttr("datatype");
	}
}