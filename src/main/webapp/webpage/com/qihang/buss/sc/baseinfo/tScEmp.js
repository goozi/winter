$(function(){
	setDefaultOldInfo();
	$("#deptIdName").keypress(function (e) {
		//如果==13就是按下回车
		if (e.keyCode == 13) {
				selectdeptIdNameDialog();
		}

	})
	debugger;
	var isCreditMgr = $('select[name="isCreditMgr"]').val();
	iscreditmgrf(isCreditMgr);


})

//设置旧数据
function setDefaultOldInfo(){

	var deptID = $("#deptID").val();
	var deptIdName = $("#deptIdName").val();
	setValOldIdAnOldName($("#deptIdName"),deptID,deptIdName);
}

$("#deptIdName").blur(function(){
	checkInput($("#deptID"), $("#deptIdName"));
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

function selectdeptIdNameDialog() {
	var deptIdName = $("#deptIdName").val();
	var url = 'tScDepartmentController.do?goSelectDialog&name=' + deptIdName;
	var width = 550;
	var height = 400;
	var title = "部门选择";
	url = encodeURI(url);
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
				var deptIdName = iframe.getSelectionData();
				$("input[name='deptIdName']").val(deptIdName.name);
				$("input[name='deptID']").val(deptIdName.id);
				setValOldIdAnOldName($("#deptIdName"), deptIdName.id, deptIdName.name);
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

//自带检查
function doName() {
	var getName=$("#name").val()
	var pinyin = getPinYin(getName);

	$("#shortName").val(pinyin);
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

//重复性校验
var isDoCheck = true;
var isDoSub = false;
function checkName(){
	var old_name = $('#old_name').val();
	var load = $('#load').val();
	var name = $('#name').val();
	var url = "tScEmpController.do?checkName&name="+ name +"&load="+ load +"&old_name="+ old_name;
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
//信用额度控制
function iscreditmgrf(v){
	if(v == "0"){
		$('#creditAmount').attr("disabled","true");
		$('#creditAmount').val("");
		$('#creditAmount').css("background","#ddd");
		$('#creditAmount').removeAttr("datatype");
	}	else {
		$('#creditAmount').removeAttr("disabled");
		$('#creditAmount').css("background","white");
		$('#creditAmount').attr("datatype","d");
	}
}
function checkcreditamount(obj){
	var CFG_MONEY = $("#CFG_MONEY").val();
	var val = $(obj).val();
	var allAmount = parseFloat(val).toFixed(CFG_MONEY);
	if( allAmount+"" == "NaN")
		$(obj).val(val);
	else
		$(obj).val(allAmount);
}