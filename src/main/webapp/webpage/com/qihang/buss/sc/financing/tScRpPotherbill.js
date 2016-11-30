

//初始化下标
function resetTrNum(tableId) {

	$tbody = $("#"+tableId+"");
	$tbody.find('tr').each(function(i){
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
					var new_onclick_str = onclick_str.substring(s+1,e);
					$this.attr("onclick",onclick_str.replace(new_onclick_str,i));
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
		$("input[name='tScRpPotherbillentryList["+i+"].findex']").val(i+1);
	});
	//dieEvent();
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

//核算项目
function pressItemName(){
	var tr = $("#add_tScRpPotherbillentry_table").find("input[name $='.billNoSrc'][value!='']");
	if(tr.length > 0){
		tip("请删除所选单【费用开支明细】在操作");
		return false;
	}
	var v = $("#itemClassId").val();
	selectItemDialog(v);
}
//核算类别选择
$("#itemClassId").bind("change",function(){
	$("#itemId").val("");
	$("#itemName").val("");
});
//核算项目
function selectItemDialog(no){
	var itemName = $("#itemName").val();
	var url ="";
	var title = "";
	if(no == 1){//调用职员
		title = "职员";
		url = "tScEmpController.do?goselectdeptIdNameDialog&name="+itemName;
	}
	if(no == 2){//调用部门
		title = "部门";
		url = 'tScDepartmentController.do?goSelectDialog&name=' + itemName;
	}
	var width = 800;
	var height = 500;
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
};
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}
//经办人
$("#empName").keypress(function(e){
	if (e.keyCode == 13) {
		selectEmpDialog();
	}
})
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
				$("#deptId").val(item.deptID);
				$("#deptName").val(item.deptIdName);
				setValOldIdAnOldName($("#empName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
};
//部门
$("#deptName").keypress(function(e){
	if (e.keyCode == 13) {
		selectDeptDialog();
	}
})
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
};

$(function(){
	var  html ='<select name="checkExpenaa" onchange="checkExpen(this)">'+
					'<option value="0">选单</option>'+
					'<option value="1">费用申报单</option>'+
					'</select>';
	$("#formobj").find(".ui_buttons").append(html);
});
//表格校验事件
function orderListStockBlur(rowIndex,id,name){
	checkInput( $('input[name="tScRpRotherbillentryList['+rowIndex+'].'+id+'"]'),$('input[name="tScRpRotherbillentryList['+rowIndex+'].'+name+'"]'));
}
//选择收支项目
function selectFeeDialog(index){
	var expName = $("input[name='tScRpPotherbillentryList["+index+"].expName']").val();
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
				//var nextTrLength = $('input[name="tScRpPotherbillentryList[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
				//var newLength = itemLength - nextTrLength - 1
				for (var j = 0; j < itemLength-1; j++) {
					clickAddTScRpPotherbillentryBtn(index);
				}
				$.each(expList, function (i, item) {
					var rowindex = parseInt(index) + i;
					var expId = item.id;
					var expName = item.name;
					$("input[name='tScRpPotherbillentryList["+rowindex+"].expId']").val(expId);
					$("input[name='tScRpPotherbillentryList["+rowindex+"].expName']").val(expName);
					setValOldIdAnOldName($("input[name='tScRpPotherbillentryList["+rowindex+"].expName']"),expId,expName);
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


function checkExpen(obj){
	$(obj).find("option:first-child").attr("selected","selected");
	var itemId =  $("#itemId").val();
	var itemName =   $("#itemName").val();
	if(itemId == "" || itemName == ""){
		tip("请选择核算项目");
		return;
	}
	//var n = $(obj).val();
	//if(n == 1){
		selectExpensesDialog();
	//}
}
//费用申报单
function selectExpensesDialog(){
	var tr = $("#add_tScRpPotherbillentry_table").find("input[name$='.fidSrc']");
	var ids = "";
	tr.each(function(){
		if($(this).val() != "" && $(this).val() != "undefined")
			ids = ids + $(this).val()+",";
	});
	var itemName = $("#itemName").val();
	var sonId = $("#sonId").val();
	var url = "tScRpPotherbillController.do?tScRpPotherbillSelect&itemName="+itemName+"&ids="+ids+"&sonId="+sonId;
	var width = 800;
	var height = 500;
	var title = "费用申报单";
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
				var itemList = iframe.getSelectionData();
				var bool = true;
				if($("#add_tScRpPotherbillentry_table").find("tr").length == 1 ){
					var expId = $("#add_tScRpPotherbillentry_table").find("tr").eq(0).find("input[name$='.expId']").val();
					if( expId == "" || expId == "undefined")
						$("#add_tScRpPotherbillentry_table").empty();
				}
				if(itemList.length > 0){
					//$("#itemName").attr("disabled","disabled");
					$("#itemName").attr("readonly","readonly");
					//$("#itemName").removeAttr("onkeypress");
				}
				for(var i =0 ;i<itemList.length;i++){
					if(bool){
						$("#classIdSrc").val(itemList[i].tranType);
						$("#classIdSrcName").val(itemList[i].billName);
						bool =false;
					}
					$("#add_tScRpPotherbillentry_table").append(trHtml(itemList[i]));
					resetTrNum('add_tScRpPotherbillentry_table');
					setAllAmount(null);
					lastTotalAmount();
					//alert(itemList[i].billName);
					//setValOldIdAnOldName($("input[name='classIdSrc']"), itemList[i].id, itemList[i].billName);
				}
				setTipReset();
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();

};
//恢复草稿时有这个方法名自己 根据方法名实现自己的业务
function removeEventDraw(){
	dieEvent();
}
//选择单据时移除所有元素的事件
function dieEvent(){
	$("#add_tScRpPotherbillentry_table").find("input[name $='.billNoSrc']").each(function(){
		if($(this).val() != "undefined" && $(this).val() != ""){
			$(this).parent().siblings("td").find("input[name$='.expName']").removeAttr("onkeypress").removeAttr("onblur").css("background-color","rgb(226, 226, 226)").attr("readonly","readonly");
		}
	});
}
function lastTotalAmount(){
	var haveAmountSrc = $("#tScRpPotherbillentry_table").find("input[name$='.haveAmountSrc']").trigger("change");
	var notAmountSrc = $("#tScRpPotherbillentry_table").find("input[name$='.notAmountSrc']").trigger("change");;
	var amount = $("#tScRpPotherbillentry_table").find("input[name$='.amount']").trigger("change");;
}
//选单组装明细tr
function trHtml(obj){
			var html='<tr class="cc">'+
					'<input name="tScRpPotherbillentryList[#index#].id" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].createName" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].createBy" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].createDate" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].updateName" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].updateBy" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].updateDate" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].fid" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].findex" type="hidden"/>'+
					'<input name="tScRpPotherbillentryList[#index#].expId" type="hidden" datatype="*" value="'+obj.expId+'"/>'+
					'<td align="center" bgcolor="white">'+
					'<div style="width: 30px;" name="xh"></div>'+
					'</td>'+
					'<td align="center" bgcolor="white">'+
					'<div style="width: 80px;">'+
					'<a name="addTScRpPotherbillentryBtn[#index#]" id="addTScRpPotherbillentryBtn[#index#]" href="#" onclick="clickAddTScRpPotherbillentryBtn(\'#index#\');" class="l-btn l-btn-plain">' +
					'<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty icon-add">&nbsp;</span></span></span>' +
					'</a>'+
					'<a name="delTScRpPotherbillentryBtn[#index#]" id="delTScRpPotherbillentryBtn[#index#]" href="#"  onclick="clickDelTScRpPotherbillentryBtn(\'#index#\',this);" class="l-btn l-btn-plain">' +
					'<span class="l-btn-left"><span class="l-btn-text"><span class="l-btn-empty icon-remove">&nbsp;</span></span></span>' +
					'</a>'+
					'</div>'+
					'</td>'+
					'<td align="left" bgcolor="white">'+
					'<input name="tScRpPotherbillentryList[#index#].expName" value="'+obj.feeName+'" maxlength="32" datatype="*"  type="text" class="inputxt popup-select"   style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly" />'+
					'<label class="Validform_label" style="display: none;">收支项目</label>'+
					'</td>'+
					'<td align="left" bgcolor="white">'+
					'<input name="tScRpPotherbillentryList[#index#].billNoSrc" maxlength="32" value="'+obj.billNo+'" type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);" readonly="readonly"/>'+
					'<label class="Validform_label" style="display: none;">源单编号</label>'+
					'</td>'+
					'<td align="left" bgcolor="white">'+
					'<input name="tScRpPotherbillentryList[#index#].haveAmountSrc" maxlength="32" type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);" value="'+obj.haveAmount+'" readonly="readonly"/>'+
					'<label class="Validform_label" style="display: none;">源单已报销金额</label>'+
					'</td>'+
					'<td align="left" bgcolor="white">'+
					'<input name="tScRpPotherbillentryList[#index#].notAmountSrc" maxlength="32" type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);" value="'+obj.notAmount+'" readonly="readonly"/>'+
					'<label class="Validform_label" style="display: none;">源单未报销金额</label>'+
					'</td>'+
					'<td align="left" bgcolor="white">'+
					'<input name="tScRpPotherbillentryList[#index#].amount"  onchange="setAllAmount(this)" maxlength="32" type="text" class="inputxt" style="width:120px;" value="'+obj.notAmount+'"/>'+
					'<label class="Validform_label" style="display: none;">本次支出</label>'+
					'</td>'+
					'<td align="left" bgcolor="white">'+
					'<input name="tScRpPotherbillentryList[#index#].note" maxlength="255" type="text" class="inputxt" style="width:120px;" value="'+obj.note+'"/>'+
					'<label class="Validform_label" style="display: none;">备注</label>'+
					'</td>'+
					'<input name="tScRpPotherbillentryList[#index#].classIdSrc" maxlength="32" type="hidden"  value="'+obj.tranType+'" />'+
					'<input name="tScRpPotherbillentryList[#index#].interIdSrc" maxlength="32" type="hidden" value="'+obj.id+'" />'+
					'<input name="tScRpPotherbillentryList[#index#].fidSrc" maxlength="32" type="hidden" value="'+obj.entryId+'"/>'+
					'</tr>';
	return html;
}
function setTipReset(){
	$('#add_tScRpPotherbillentry_table .cc input.popup-select').each(function (index, obj) {
		// debugger;
		var w = $(obj).width();
		//$(obj).width(w - 20);
		$(obj).css('float', 'left');
		//$(obj).css('margin-right', '20px');
		var popupDiv = $('<div></div>');
		popupDiv.addClass('popupSelectDiv');
		if ($(obj).parents('div[id$="scrolldiv"]').length > 0) {
			popupDiv.height(16).width(18);
			popupDiv.css('background', 'url("plug-in/easyui/themes/icons/popup.png") 1px 6px no-repeat');
		} else {
			popupDiv.height(18).width(18);
			popupDiv.css('background', 'url("plug-in/easyui/themes/icons/popup.png") 1px 7px no-repeat');
		}
		popupDiv.css('background-size', '16px 4px');
		popupDiv.css('float', 'left');
		popupDiv.css('position', 'relative');
		popupDiv.css('border', '1px solid #a5aeb6');
		popupDiv.css('left', '-1px');
		popupDiv.css('margin-left', '-20px');

		$(obj).after(popupDiv);
		// debugger;
	});
}
function setAllAmount(obj){
	var CFG_MONEY = $("#CFG_MONEY").val();

	if(obj != null && obj != "undefined"){
		if($.trim($(obj).val()) == "" || $.trim($(obj).val()) == "undefined"){
			var thisAmount = 0;
			$(obj).val(thisAmount.toFixed(CFG_MONEY));
			var fidSrc = $(obj).parent().siblings("input[name$='.fidSrc']").val();
			if(fidSrc == null || fidSrc == undefined || fidSrc == ""){
				$(obj).parent().siblings("td").find("input[name$='.notAmountSrc']").val(parseFloat($(obj).val()).toFixed(CFG_MONEY));
			}
		}else{
			$(obj).val( parseFloat($(obj).val()).toFixed(CFG_MONEY));
			var fidSrc = $(obj).parent().siblings("input[name$='.fidSrc']").val();
			if(fidSrc == null || fidSrc == undefined || fidSrc == ""){
				$(obj).parent().siblings("td").find("input[name$='.notAmountSrc']").val(parseFloat($(obj).val()).toFixed(CFG_MONEY));
			}
		}
	}
	$(obj).parent().parent("tr").find("input[name$='notAmountSrc']").trigger("change");
	var amount =  $("#tScRpPotherbillentry_table").find("input[name$='.amount']");
	var allAmount = 0;
	amount.each(function(){
		if($.trim($(this).val()) != "" && $.trim($(this).val()) != "undefined"){
			allAmount = allAmount + parseFloat($(this).val());
		}
	});
	allAmount = allAmount.toFixed(CFG_MONEY);
	$("#allAmount").val(allAmount);
}
/**
 * 提交之前检查一下金额
 * @returns {boolean}
 */
function checkPartten(){
	var amount =  $("#tScRpPotherbillentry_table").find("input[name$='.amount']");
	var allAmount = 0;
	var num = 0;
	amount.each(function(){
		if($.trim($(this).val()) == 0 || $.trim($(this).val()) == "" || $.trim($(this).val()) == "undefined"){
			num = num + 1;
		}
	});
	if(num > 0){
		tip("资金其他收入明细金额项不能为0或空");
		return false;
	}
	return true;
}
