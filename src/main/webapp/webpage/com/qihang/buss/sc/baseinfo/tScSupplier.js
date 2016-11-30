$(function(){
	//$("#settleCompanyName").on("dblclick", function () {
	//	selectDialog();
	//});
		setDefaultOldInfo();
		$("#settleCompanyName").keypress(function (e) {
			//如果==13就是按下回车
			if (e.keyCode == 13) {
				//如果doCompanyName这个方法里面判断count是否大于0 false 则给出下面的else提示 否则执行selectDialog()
				if(doCompanyName()) {
					selectDialog();
				}else{
					$.messager.show({
						title: '提示消息',
						msg: '该结算单位已被引用，不能修改',
						timeout: 2000,
						showType: 'slide'
					});
				}
			}
		});
})

var isDoCheck = false;
var isDoSub = false;
//保存前校验
function checkName(){
	var name = $("#name").val();
	var id = $("#id").val();
	if(!isDoCheck) {
		url = 'tScSupplierController.do?checkName&name=' + name+'&id='+id;
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
//设置旧数据
function setDefaultOldInfo(){

	var settleCompany = $("#settleCompany").val();
	var settleCompanyName = $("#settleCompanyName").val();
	setValOldIdAnOldName($("#settleCompanyName"),settleCompany,settleCompanyName);
}
$("#settleCompanyName").blur(function(){
	checkInput($("#settleCompany"), $("#settleCompanyName"));
})

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


function doCompanyName(){
	var  count=$("#count").val();
	//当引用大于0的时候设为只读
	if(count > 0){
		$("#settleCompanyName").attr("readonly","readonly");
		return false;
	}
	return true;
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

function selectDialog(){
	var settleCompanyName = $("#settleCompanyName").val();
	var url ='tScSupplierController.do?goSelectDialog&name=' + settleCompanyName ;
	var width =850;
	var height = 500;
	var title = "结算单位选择";
	url = encodeURI(url);
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
				var CompanyName =iframe.getSelectionData();
				$("input[name='settleCompanyName']").val(CompanyName.name);
				$("input[name='settleCompany']").val(CompanyName.id);
				setValOldIdAnOldName($("#settleCompanyName"), CompanyName.id, CompanyName.name);

			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}