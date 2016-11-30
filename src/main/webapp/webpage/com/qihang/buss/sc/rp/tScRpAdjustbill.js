

//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
				if(name.indexOf("amount") > -1){
					$this.attr("onchange","setAllAmount()");
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
			if(onclick_str!=null){
				if (onclick_str.indexOf("#index#") >= 0){
					$this.attr("onclick",onclick_str.replace(/#index#/g,i));
				}else{
					var s = onclick_str.indexOf("(");
					var e = onclick_str.indexOf(")");
					var new_onclick = onclick_str.substring(s + 1, e);
					$this.attr("onclick", onclick_str.replace("("+new_onclick+")", "("+i+")"));
				}
			}
			if(str_press != null){
				if (str_press.indexOf("#index#") >= 0) {
					$this.attr("onkeypress", str_press.replace(/#index#/g, i));
				} else {
					var s = str_press.indexOf("(");
					var e = str_press.indexOf(",");
					var new_key= str_press.substring(s + 1, e);
					$this.attr("onkeypress", str_press.replace(new_key, i));
				}
			}
			if (blur_str != null) {
				if (blur_str.indexOf("#index#") >= 0) {
					$this.attr("onblur", blur_str.replace(/#index#/g, i));
				} else {
					var s = blur_str.indexOf("(");
					var e = blur_str.indexOf(",");
					var new_blur = blur_str.substring(s + 1, e);
					$this.attr("onblur", blur_str.replace(new_blur, i));
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i+1);
		$("input[name='tScRpAdjustbillentryList["+i+"].findex']").val(i+1);
	});
}
//通用弹出式文件上传
function commonUpload(callback,inputId){
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
               iframe.uploadCallback(callback,inputId);
               return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
//通用弹出式文件上传-回调
function commonUploadDefaultCallBack(url,name,inputId){
	$("#"+inputId+"_href").attr('href',url).html('下载');
	$("#"+inputId).val(url);
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
	$("#itemName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectItemNameDialog();
		}
	});
	$("#deptName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectDeptDialog();
		}
	});
	$("#empName").keypress(function(e){
		if (e.keyCode == 13) {
			selectEmpDialog();
		}
	})

	$("#itemName").blur(function(){
		checkInput($("#itemId"), $("#itemName"));
	})
	$("#deptName").blur(function(){
		checkInput($("#deptId"), $("#deptName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empId"), $("#empName"));
	})

	var CFG_MONEY = $("#CFG_MONEY").val();//
	var allAmount = $("#allAmount").val();
	if(allAmount){
		allAmount = parseFloat(allAmount).toFixed(CFG_MONEY);
		$("#allAmount").val(allAmount);
	}
})


//选择客户
function selectItemNameDialog() {
	var itemName = $("#itemName").val();
	var url = encodeURI('tScOrganizationController.do?goSelectDialog&name=' + itemName);
	var width = 800;
	var height = 500;
	var tranType = $("#tranType").val();
	var title = "客户";
	if("202" == tranType){
		title = "供应商";
		url = encodeURI('tScSupplierController.do?goSelectDialog&name=' + itemName);
	}
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
				$("#itemId").val(item.id);
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
				$("#deptId").val(item.id);
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

//选择收支项目
function selectFeeDialog(index){
	var expName = $("input[name='tScRpAdjustbillentryList["+index+"].expName']").val();
	var url = 'tScFeeController.do?goSelectEmpDialog&name=' + expName;
	var width = 800;
	var height = 500;
	var title = "收支项目";
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
				var expList = iframe.getMoreSelectionData();
				//for(var i=0 ; i<itemList.length ; i++) {
				var itemLength = expList.length;
				var nextTrLength = $('input[name="tScRpAdjustbillentryList[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
				var newLength = itemLength - nextTrLength - 1
				for (var j = 0; j < newLength; j++) {
					clickAddTScRpAdjustbillentryBtn(index);
				}
				$.each(expList, function (i, item) {
					var rowindex = parseInt(index) + i;
					var expId = item.id;
					var expName = item.name;
					$("input[name='tScRpAdjustbillentryList["+rowindex+"].expId']").val(expId);
					$("input[name='tScRpAdjustbillentryList["+rowindex+"].expName']").val(expName);
					setValOldIdAnOldName($("input[name='tScRpAdjustbillentryList["+rowindex+"].expName']"),expId,expName);
				});
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
//选择职员
function selectEmpDialog(){
	var empName = $("#empName").val();
	var url = "tScEmpController.do?goselectdeptIdNameDialog&name="+empName;
	var width = 800;
	var height = 500;
	var title = "经办人";
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
				$("#empId").val(item.id);
				setValOldIdAnOldName($("#empName"), item.id, item.name);

				$("#deptName").val(item.deptIdName);
				$("#deptId").val(item.deptID);
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
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

//表格校验事件
function orderListStockBlur(rowIndex,id,name){
	checkInput( $('input[name="tScRpAdjustbillentryList['+rowIndex+'].'+id+'"]'),$('input[name="tScRpAdjustbillentryList['+rowIndex+'].'+name+'"]'));
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
	}
}

function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody = $("#tScRpAdjustbillentry_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScRpAdjustbillentryList[" + i + "].amount']").val();//金额
		if(!amount){
			amount = 0;
		}

		allAmount += parseFloat(amount);
		if(amount && amount != 0) {
			$("input[name='tScRpAdjustbillentryList[" + i + "].amount']").val(parseFloat(amount).toFixed(CFG_MONEY));
			$("input[name='tScRpAdjustbillentryList[" + i + "].amount']").trigger("input");
		} else if(amount){
			$("input[name='tScRpAdjustbillentryList[" + i + "].amount']").val(parseFloat(1).toFixed(CFG_MONEY));
			$("input[name='tScRpAdjustbillentryList[" + i + "].amount']").trigger("input");
		}
	}
	allAmount = allAmount.toFixed(CFG_MONEY);
	$("#allAmount").val(allAmount);
}