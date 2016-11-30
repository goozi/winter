//编写自定义JS代码
$(function(){
	//$("#settlecompanyName").on("dblclick", function () {
	//	selectDialog();
	//});
	$('#defaultoperatorName').keypress(function(e){
		if (e.keyCode == 13) {
			selectempNameDialog();
		}
	});
	$("#defaultoperatorName").blur(function () {
		checkInput($("#defaultoperator"), $("#defaultoperatorName"));
	});
	//结算单位
	$('#settlecompanyName').keypress(function(e){
		if (e.keyCode == 13) {
			selectSettleDialog();
		}
	});
	//上级经销商
	$('#dealerName').keypress(function(e){
		if (e.keyCode == 13) {
			selectDealerDialog();
		}
	});

	//$("#settlecompanyName").blur(function () {
	//	checkInput($("#settlecompany"), $("#settlecompanyName"));
	//});
	if($('select[name="isDealer"]').val()==1){
		$("#dealerName").removeAttr("readonly");
		$("#dealerName").css("background", "white");
	}else{
		$('select[name="typeid"]').attr("disabled", true);
	};
	$("#number").css("width", "50%");

	//var count = $('#count').val();
	//if(count > 0){
	//	$('select[name="isDealer"]').attr("disabled",true);
	//	$('select[name="typeid"]').attr("disabled", true);
	//	$('#dealerName').attr("disabled", true);
    //
	//	$('select[name="isDealer"]').css("background","#ddd");
	//	$('select[name="typeid"]').css("background","#ddd");
	//	$('#dealerName').css("background","#ddd");
	//}

	var isDealer = $('select[name="isDealer"]').val();
	isdealer(isDealer);

	var typeid = $('select[name="typeid"]').val();
	clientSort(typeid);

	var iscreditmgr = $('select[name="iscreditmgr"]').val();
	iscreditmgrf(iscreditmgr);

})

//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:900,
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

function selectDialog(){
	var url ='tScOrganizationController.do?goSelectDialog';
	var width =700;
	var height = 500;
	var title = "结算单位选择";

	$.dialog({
		content: 'url:' + url,
		title: title,
		lock: true,//定义是否可以关闭窗口
		zIndex:600,//窗口Z坐標
		height: height,
		cache: false,
		width: width,
		opacity: 0.3,
		button: [{
			name:'确定',
			callback:function(){
				iframe = this.iframe.contentWindow;
				var item =iframe.getSelectionData();
				$("input[name='settlecompanyName']").val(item.name);
				$("input[name='settlecompany']").val(item.id);

			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

var isDoCheck = true;
var isDoSub = false;
function checkName(){

	var old_name = $('#old_name').val();
	var load = $('#load').val();
	var name = $('#name').val();
	var url = "tScOrganizationController.do?checkName&name="+ name +"&load="+ load +"&old_name="+ old_name;
	if(isDoCheck){
		$.ajax({
			url: url,
			async: false,
			dataType: 'json',
			//type: 'POST',
			cache: false,
			success: function (data) {
				if (data.success) {
					parent.$.dialog.confirm(name+"已存在，是否继续保存？", function () {
								isDoCheck = false;
								isDoSub = true;
								$("#formobj").submit();
							}
							, function () {
								//
							}
					)
				} else {
					isDoCheck = false;
					isDoSub = true;
				}
			}
		})
	}
	return isDoSub;
}

function selectempNameDialog() {
	var tempName = $("#defaultoperatorName").val();
	var url = 'tScEmpController.do?goselectdeptIdNameDialog&name=' + tempName;
	var width = 1000;
	var height = 500;
	var title = "默认业务员";
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
				$("input[name='defaultoperatorName']").val(item.name);
				$("input[name='defaultoperator']").val(item.id);
				setValOldIdAnOldName($("#defaultoperatorName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

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
	}
}

function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

function selectSettleDialog() {
	var settleName = $("#settlecompanyName").val();
	var url = 'tScOrganizationController.do?goSelectDialog&name=' + settleName;
	var width = 1000;
	var height = 500;
	var title = "结算单位";
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
				$("input[name='settlecompanyName']").val(item.name);
				$("input[name='settlecompany']").val(item.id);
				setValOldIdAnOldName($("#settlecompanyName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
function isdealer(n){
	if(n == 1){
		//是经销商
		$('select[name="typeid"]').attr("disabled", false);
		//$("#dealerName").removeAttr("readonly");
		$("#dealerName").attr("disabled",true);
		$("#dealerName").removeAttr("datatype");
		$("#dealerName").css("background", "#ddd");
		$('select[name="typeid"]').attr("datatype","*");
	}else{
		//经销商为否
		$("#dealerName").attr("disabled",true);
		$("#dealerName").removeAttr("datatype");
		$("#dealerName").val("");
		$("#dealerName").css("background","#ddd");
		$("#dealerId").val("");
		$('select[name="typeid"]').attr("disabled", true);
		$('select[name="typeid"]').removeAttr("datatype");
		$('select[name="typeid"]').val("");
		$('select[name="typeid"]').css("background","white");
	}
}
function clientSort(n){
	if(n == "" || n == "ADealer"){
		$("#dealerName").attr("disabled",true);
		$("#dealerName").css("background","#ddd");
		$("#dealerName").removeAttr("datatype");
		$("#dealer").val("");
		$("#dealerName").val("");
	} else {
		if($("#load").val() != "detail") {
			$("#dealerName").removeAttr("disabled");
		}
		$("#dealerName").css("background", "white");
		$("#dealerName").attr("datatype","*");
	}
	//
//	$("#dealerName").val("");
}


//上级经销商选择
function selectDealerDialog(){
	var typeid =$("select[name='typeid']").val();
	if(typeid == "BDealer"){
		typeid = "ADealer";
	}
	if(typeid == "CDealer"){
		typeid = "BDealer";
	}
	var name = $.trim($("#dealerName").val());
	var url = 'tScOrganizationController.do?goSelectDialog&isDealer=1'+'&typeid='+typeid+"&name="+name;
	var width = 1000;
	var height = 500;
	var title = "上级经销商";
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
				$("input[name='dealerName']").val(item.name);
				$("input[name='dealer']").val(item.id);
				setValOldIdAnOldName($("#dealerName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//信用额度控制
function iscreditmgrf(v){
	if(v == "0"){
		$('#creditamount').attr("disabled",true);
		$('#creditamount').val("");
		$('#creditamount').css("background","#ddd");
	}	else {
		$('#creditamount').attr("disabled", false);
		$('#creditamount').css("background","white");
	}
}
var CFG_MONEY = $("#CFG_MONEY").val();//取金额小数位
function checkcreditamount(){
	var creditamount = $('#creditamount').val();
	var intPart = creditamount.split(".")[0].length;
	var maxIntPart = 20-CFG_MONEY;//20是数据库中金额最大可存位数，CFG_MONEY是系统设置金额小数位数
  if(intPart > maxIntPart){
		$.messager.show({
			title: '提示消息',
			msg: '整数位数超出范围,范围是1-'+maxIntPart+'位整数',
			timeout: 2000,
			showType: 'slide'
		});
		return ;
	}
	$('#creditamount').val((Number)(creditamount).toFixed(CFG_MONEY));
}