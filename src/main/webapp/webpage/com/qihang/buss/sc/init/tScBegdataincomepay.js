

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
	$("#date").removeAttr("onClick");
	//$("#date").datebox({editable:false})
	//setDefaultDate();//设置默认时间
	setDefaultOldInfo();//设置旧数据
	//allAmountPrecision();//金额控制
	$("#itemName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectItemNameDialog();
		}
	});
	$("#empName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectEmpDialog();
		}
	});
	$("#deptName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectDeptDialog();
		}
	});

	$("#itemName").blur(function(){
		checkInput($("#itemID"), $("#itemName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empID"), $("#empName"));
	})
	$("#deptName").blur(function(){
		checkInput($("#deptID"), $("#deptName"));
	})
})

//设置旧数据
function setDefaultOldInfo(){
	//供应商
	var itemID = $("#itemID").val();
	var itemName = $("#itemName").val();
	setValOldIdAnOldName($("#itemName"),itemID,itemName);
	//经办人
	var empID = $("#empID").val();
	var empName = $("#empName").val();
	setValOldIdAnOldName($("#empName"),empID,empName);
	//部门
	var deptID = $("#deptID").val();
	var deptName = $("#deptName").val();
	setValOldIdAnOldName($("#deptName"),deptID,deptName);

}
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

//选择结算账户
function selectItemNameDialog() {
	var itemName = $("#itemName").val();
	var url = 'tScSettleacctController.do?goSelectDialog&name=' + itemName;
	var width = 400;
	var height = 300;
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
				$("#itemName").val(item.name);
				$("#itemID").val(item.id);
				setValOldIdAnOldName($("#itemName"), item.id, item.name);

			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//选择经办人
function selectEmpDialog(){
	var empName = $("#empName").val();
	var url = "tScEmpController.do?goselectdeptIdNameDialog&name="+empName;
	var width = 800;
	var height = 500;
	var title = "职员";
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
				$("#empName").val(item.name);
				$("#empID").val(item.id);
				$("#deptName").val(item.deptIdName);
				$("#deptID").val(item.deptID);
				setValOldIdAnOldName($("#empName"), item.id, item.name);
				setValOldIdAnOldName($("#deptName"), item.deptID, item.deptIdName);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//选择部门
function selectDeptDialog(){
	var deptName = $("#deptName").val();
	var url = 'tScDepartmentController.do?goSelectDialog&name=' + deptName;
	var width = 800;
	var height = 500;
	var title = "部门";
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
				$("#deptName").val(item.name);
				$("#deptID").val(item.id);
				setValOldIdAnOldName($("#deptName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}


function setDefaultDate(){
	var date1 = new Date();
	var year = date1.getFullYear();
	var month = (date1.getMonth()+1) < 10 ? "0"+(date1.getMonth()+1) : (date1.getMonth()+1);
	var date2 = date1.getDate() < 10 ? "0"+date1.getDate() : date1.getDate();
	var date = $("#date").val();
	var id = $("#id").val();
	if(!id){
		$("input[name='date']").val(year+"-"+month+"-"+date2);
	}

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

//金额精度控制
function allAmountPrecision(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	var allAmount = parseFloat($("#allAmount").val());
	if(isNaN(allAmount)){
		$("#allAmount").val("");
	}else {
		$("#allAmount").val(allAmount.toFixed(CFG_MONEY));
	}
}