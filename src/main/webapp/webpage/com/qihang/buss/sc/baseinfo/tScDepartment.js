$(function(){
	$('#managerName').keypress(function(e){
		if (e.keyCode == 13) {
			selectempNameDialog();
		}
	});
	$("#managerName").blur(function () {
		checkInput($("#manager"), $("#managerName"));
	})
});

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
//保存前校验
var isDoCheck = false;
var isDoSub = false;
function checkValue(){
	var amout = $("#creditAmount").val();
	var creditValue = $('select[name="isCreditMgr"]').val();
	if(creditValue == 1){
		if(amout <= 0 ){
			tip("信用额度必须大于0");
			return false;
		}
	}
	return checkName();
}
function checkName(){
	var name = $("#name").val();
	var id = $("#id").val();
	if(!isDoCheck) {
		url = 'tScDepartmentController.do?checkName&name=' + name+'&id='+id;
		$.ajax({
			url: url,
			async: false,
			dataType: 'json',
			type: 'POST',
			cache: false,
			success: function (data) {
				if (data.success) {
					parent.$.dialog.confirm(name + "已存在，是否继续保存？", function () {
								isDoCheck = true;
								isDoSub = true;
								$("#formobj").submit();
							}
							, function () {
							}
					)
				} else {
					isDoCheck = true;
					isDoSub = true;
				}
			}
		});
	}
	return isDoSub;
}

function selectempNameDialog() {
	var tempName = $("#managerName").val();
	var url = 'tScEmpController.do?goselectdeptIdNameDialog&name=' + tempName;
	var width = 700;
	var height = 500;
	var title = "部门主管";
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
				$("input[name='managerName']").val(item.name);
				$("input[name='manager']").val(item.id);
				setValOldIdAnOldName($("#managerName"), item.id, item.name);
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