$(function(){
	$('#itemIDName').keypress(function(e){
		if (e.keyCode == 13) {
			selectitemNameDialog();
		}
	});
	$("#itemIDName").blur(function () {
		checkInput($("#itemID"), $("#itemIDName"));
	});
	$('#checkerName').keypress(function(e){
		if (e.keyCode == 13) {
			selectcheckerNameDialog();
		}
	});
	$("#checkerName").blur(function () {
		checkInput($("#checkerID"), $("#checkerName"));
	});
	$('#billerName').keypress(function(e){
		if (e.keyCode == 13) {
			selectbillerNameDialog();
		}
	});
	$("#billerName").blur(function () {
		checkInput($("#billerID"), $("#billerName"));
	});
});

function checkBillDate(){
	var checkDate = $("#checkDate").val();
	checkDate = new Date(checkDate).getTime();
	var billDate = $("#date").val();
	billDate = new Date(billDate).getTime();
	if(parseInt(billDate) <= parseInt(checkDate)){
		tip("单据日期不可小于期间日期");
		return false;
	}
}


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

function selectitemNameDialog() {
	debugger;
	var tempName = $("#itemIDName").val();
	var url = 'tScAptitudeController.do?goSelectDialog&name=' + tempName;
	var width = 1000;
	var height = 500;
	var title = "经销商";
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
				$("input[name='distributor']").val(item.name);
				$("input[name='itemID']").val(item.id);
				setValOldIdAnOldName($("#itemIDName"), item.id, item.name);

				var contact = item.contact; //联系人
				var mobilePhone = item.mobilephone;//手机号码
				var fax = item.fax; //传真
				var phone = item.phone;//电话
				var address = item.address;//地址
				var corperate = item.corperate;//法人代表

				$('#contact').val(contact);
				$('#mobilePhone').val(mobilePhone);
				$('#phone').val(phone);
				$('#fax').val(fax);
				$('#address').val(address);
				$('#corperate').val(corperate);
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

function selectcheckerNameDialog() {
	var tempName = $("#checkerName").val();
	var url = 'tScEmpController.do?goselectdeptIdNameDialog&name=' + tempName;
	var width = 1000;
	var height = 500;
	var title = "审核人";
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
				$("input[name='checkerName']").val(item.name);
				$("input[name='checkerID']").val(item.id);
				setValOldIdAnOldName($("#checkerName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

function selectbillerNameDialog() {
	var tempName = $("#checkerName").val();
	var url = 'tScEmpController.do?goselectdeptIdNameDialog&name=' + tempName;
	var width = 1000;
	var height = 500;
	var title = "制单人";
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
				$("input[name='billerName']").val(item.name);
				$("input[name='billerID']").val(item.id);
				setValOldIdAnOldName($("#billerName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

function checkRegCapital(obj){
	var CFG_NUMBER = $("#CFG_NUMBER").val();
	var val = $(obj).val();
	var amount = parseFloat(val);
	amount = amount.toFixed(CFG_NUMBER);
	if(!isNaN(amount)){
		$(obj).val(amount);
	}else{
		$(obj).val(val);
	}
}