
var rowIndex = 0;
//初始化下标
function resetTrNum(tableId,index,isAdd) {
    var len = 0;
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		var unitId = "";
		len = i;
		$(':input,span, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			var comboboName = $this.attr("comboname");
			var change_ = $this.attr("onchange");
			if(null != comboboName){
				var s = comboboName.indexOf("[");
				var e = comboboName.indexOf("]");
				var new_name = comboboName.substring(s+1,e);
				$this.attr("comboname",comboboName.replace(new_name,i));
			}
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
				if(name.indexOf("stockName") > -1){
					var stockid = $("input[name='tScSlStockbillentryList["+i+"].stockid']").val();
					var stockName = $("input[name='tScSlStockbillentryList["+i+"].stockName']").val();
					setValOldIdAnOldName($("input[name='tScSlStockbillentryList["+i+"].stockName']"),stockid,stockName);
				}else if(name.indexOf("freegifts_") > -1){
					$this.attr("onchange","setPrice("+i+")");
				}else if(name.indexOf("inTaxAmount") > -1){
					$this.attr("onchange","setAllAmount()");
				}else if(name.indexOf("kfdatetype") > -1){
					$this.attr("onchange","setPeriodDate("+i+")");
				}else if(name.indexOf("itemweight") > -1){
					$this.attr("onchange","setAllAmount()");
				}else if(name.indexOf(".qty") > -1){
					$this.attr("onblur","setAllWeight("+i+")");
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
			if (onclick_str != null) {
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {
					if(name.indexOf("kfdate") < 0) {
						var s = onclick_str.indexOf("(");
						var e = onclick_str.indexOf(")");
						var new_onclick = onclick_str.substring(s + 1, e);
						$this.attr("onclick", onclick_str.replace(new_onclick, i));
					}
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
					if(name.indexOf("qty") < 0) {
						var s = blur_str.indexOf("(");
						var e = blur_str.indexOf(",");
						var new_blur = blur_str.substring(s + 1, e);
						$this.attr("onblur", blur_str.replace(new_blur, i));
					}
				}
			}
			if(null != change_){
				if (change_.indexOf("#index#") >= 0) {
					$this.attr("onchange", change_.replace(/#index#/g, i));
				} else {
					if(name.indexOf("inTaxAmount") < 0 && name.indexOf("itemweight") < 0) {
						var s = change_.indexOf("(");
						var e = change_.indexOf(")");
						var new_change = change_.substring(s + 1, e);
						$this.attr("onchange", change_.replace(new_change, i));
					}
				}
			}
		});
		setFunctionInfo(i);
		checkAllowAddLine(i);
		$("input[name='tScSlStockbillentryList["+i+"].findex']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});

	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1) && isAdd) {
			$("#unitId\\[" + i + "\\]").combobox({
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			})
		}
	});
	setAllAmount();
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
	setDefaultDate();//设置默认时间
	setDefaultOldInfo();//设置旧数据
	addButton();//添加选单按钮
	checkcurPayAmount();
	$("#chooseBill").bind("click",function(){
		selectBillDialog();
	})
	$("#itemName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectItemNameDialog();
		}
	});

	$("#stockName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectStockDialog();
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

	$("#accountName").keypress(function(e){
		if (e.keyCode == 13) {
			selectAccountDialog();
		}
	})


	$("#itemName").blur(function(){
		checkInput($("#itemId"), $("#itemName"));
	})

	$("#stockName").blur(function(){
		checkInput($("#stockid"), $("#stockName"));
	})
	$("#deptName").blur(function(){
		checkInput($("#deptId"), $("#deptName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empId"), $("#empName"));
	})
	$("#accountName").blur(function(){
		checkInput($("#accountId"), $("#accountName"));
	})
	//var checkState = $("#checkState").val();
	//if(checkState == 2){
	//	$("#date").attr("disabled","disabled");
	//	$("#itemName").attr("readonly","readonly");
	//	$("#empName").attr("readonly","readonly");
	//	$("#deptName").attr("readonly","readonly");
	//	$("#stockName").attr("readonly","readonly");
	//	$("#contact").attr("readonly","readonly");
	//	$("#mobilePhone").attr("readonly","readonly");
	//	$("#phone").attr("readonly","readonly");
	//	$("#address").attr("readonly","readonly");
	//	$("#rebateAmount").attr("readonly","readonly");
	//	$("#explanation").attr("readonly","readonly");
	//}
})

//添加选单按钮
function addButton(){
	var button = document.createElement("input");
	button.id="chooseBill";
	button.type="button";
	button.class="button";
	button.value="选单"
	var buttonDiv = document.getElementsByClassName("ui_buttons");
	var load = $("#load").val()
	var tranType = $("#trantype").val();
	if("detail" != load) {
		//buttonDiv[0].appendChild(button);
		var select = document.createElement("select");
		select.id="chooseType";
		select.setAttribute("onchange","selectBillDialog()");
		select.setAttribute("style","margin-left:10px");
		var option1 = document.createElement("option");
		option1.value="";
		option1.innerText="选单";
		option1.select = "select";
		if("103" == tranType) {
			var option2 = document.createElement("option");
			option2.value = "102";
			option2.innerText = "销售订单";
			//var option3 = document.createElement("option");
			//option3.value = "107";
			//option3.innerText = "收货通知单";
			//var option4 = document.createElement("option");
			//option4.value = "109";
			//option4.innerText = "商品借出单";
			select.appendChild(option1);
			select.appendChild(option2);
			//select.appendChild(option3);
			//select.appendChild(option4);
		}else if("104" == tranType){
			var option2 = document.createElement("option");
			option2.value = "103";
			option2.innerText = "销售出库单";
			select.appendChild(option1);
			select.appendChild(option2);
		}
		buttonDiv[0].appendChild(select);
	}

	var button2 = document.createElement("a");
	button2.setAttribute("class","easyui-linkbutton");
	button2.id = "logi";
	button2.setAttribute("style","margin-left:10px");
	//button2.setAttribute("onclick","goLogistical()");
	buttonDiv[0].appendChild(button2);
	$("#logi").linkbutton({
		iconCls:"new-icon-add",
		plain : true,
		text:"物流信息"
	})
	$("#logi").bind("click",function(){
		goLogistical();
	});
}

//设置旧数据
function setDefaultOldInfo(){
	var itemId = $("#itemId").val();
	var itemName = $("#itemName").val();
	setValOldIdAnOldName($("#itemName"),itemId,itemName);

	var empId = $("#empId").val();
	var empName = $("#empName").val();
	setValOldIdAnOldName($("#empName"),empId,empName);

	var stockid = $("#stockid").val();
	var stockName = $("#stockName").val();
	setValOldIdAnOldName($("#stockName"),stockid,stockName);

	var deptId = $("#deptId").val();
	var deptName = $("#deptName").val();
	setValOldIdAnOldName($("#deptName"),deptId,deptName);

}

//设置单据日期默认为当前日期
function setDefaultDate(){
	var date = new Date($("input[name='date']").val());
	var year = date.getFullYear();
	var month = (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
	var date = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var id = $("#id").val();
	if(!id){
		//$("input[name='date']").val(year+"-"+month+"-"+date);
		$("input[name='tScSlStockbillentryList[#index#].kfdate']").val(year+"-"+month+"-"+date);
	}else{
		var dateValue = $("input[name='date']").val();
		dateValue = new Date(dateValue);
		var year = dateValue.getFullYear();
		var month = (dateValue.getMonth()+1) < 10 ? "0"+(dateValue.getMonth()+1) : (dateValue.getMonth()+1);
		var date = dateValue.getDate() < 10 ? "0"+dateValue.getDate() : dateValue.getDate();
		dateValue = year+"-"+month+"-"+date;
		$("input[name='tScSlStockbillentryList[#index#].kfdate']").val(dateValue);
	}
}

//赠品设置单价为0；
function setPrice(index){
	var selected = document.getElementsByName("tScSlStockbillentryList["+index+"].freegifts_");
	var i = selected[0].selectedIndex;
	var value = selected[0].options[i].value;
	$("input[name='tScSlStockbillentryList["+index+"].freegifts']").val(value);
	if(value == 1){
		$("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").val(0);//单价
		$("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").trigger("change");
		//单价只读
		$("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").attr("readonly","readonly");
		$("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
		//金额只读
		$("input[name='tScSlStockbillentryList["+index+"].taxAmountEx']").attr("readonly","readonly");
		$("input[name='tScSlStockbillentryList["+index+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
		//折扣率只读
		$("input[name='tScSlStockbillentryList["+index+"].discountRate']").attr("readonly","readonly");
		$("input[name='tScSlStockbillentryList["+index+"].discountRate']").css("background-color","rgb(226,226,226)");
		//折后金额
		$("input[name='tScSlStockbillentryList["+index+"].inTaxAmount']").attr("readonly","readonly");
		$("input[name='tScSlStockbillentryList["+index+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
		//税率
		$("input[name='tScSlStockbillentryList["+index+"].taxRate']").attr("readonly","readonly");
		$("input[name='tScSlStockbillentryList["+index+"].taxRate']").css("background-color","rgb(226,226,226)");
		//onPriceChange(index);
	}else{
		//单价只读
		$("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").removeAttr("readonly");
		$("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").css("background-color","white");
		//金额只读
		$("input[name='tScSlStockbillentryList["+index+"].taxAmountEx']").removeAttr("readonly");
		$("input[name='tScSlStockbillentryList["+index+"].taxAmountEx']").css("background-color","white");
		//折扣率只读
		$("input[name='tScSlStockbillentryList["+index+"].discountRate']").removeAttr("readonly");
		$("input[name='tScSlStockbillentryList["+index+"].discountRate']").css("background-color","white");
		//折后金额
		$("input[name='tScSlStockbillentryList["+index+"].inTaxAmount']").removeAttr("readonly");
		$("input[name='tScSlStockbillentryList["+index+"].inTaxAmount']").css("background-color","white");
		//税率
		$("input[name='tScSlStockbillentryList["+index+"].taxRate']").removeAttr("readonly");
		$("input[name='tScSlStockbillentryList["+index+"].taxRate']").css("background-color","white");
	}
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

//选择客户
function selectItemNameDialog() {
	var itemName = $("#itemName").val();
	var url = encodeURI('tScOrganizationController.do?goSelectDialog&name=' + itemName);
	var width = 800;
	var height = 500;
	var title = "客户";
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
				$("#itemid").val(item.id);
				setValOldIdAnOldName($("#itemName"), item.id, item.name);
				var contact = item.contact;
				var mobilePhone = item.mobilephone;
				var phone = item.phone;
				var address = item.address;
				var fax = item.fax;
				var iscreditmgr = item.iscreditmgr;//是否启用信用
				var creditamount = item.creditamount;//信用额度
				var countAmount = item.countAmount;//已执行金额
				$("#contact").val(contact);
				$("#mobilephone").val(mobilePhone);
				$("#phone").val(phone);
				$("#address").val(address);
				$("#fax").val(fax);
				$("#iscreditmgr").val(iscreditmgr);
				$("#creditamount").val(creditamount);
				$("#countAmount").val(countAmount);

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
				$("#deptid").val(item.id);
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
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
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
				$("#empid").val(item.id);
				setValOldIdAnOldName($("#empName"), item.id, item.name);

				$("#deptName").val(item.deptIdName);
				$("#deptid").val(item.deptID);
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
//选择仓库
function selectStockDialog(index) {
	var itemName = $("#stockName").val();
	if(index || 0 == index){
		itemName = $("input[name='tScSlStockbillentryList["+index+"].stockName']").val();
	}
	var url = 'tBiStockController.do?goSelectDialog&name=' + itemName;
	var width = 800;
	var height = 500;
	var title = "仓库";
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
				if(!index && index!=0) {
					$("#stockName").val(item.name);
					$("#stockid").val(item.id);
					$tbody = $("#add_tScSlStockbillentry_table");
					var length = $tbody[0].rows.length;
					$("input[name='tScSlStockbillentryList[#index#].stockid']").val(item.id);
					$("input[name='tScSlStockbillentryList[#index#].stockName']").val(item.name);
					//表头仓库发生变化，表体仓库也跟着发生变化
					for (var i = 0; i < length; i++) {
						$("input[name='tScSlStockbillentryList[" + i + "].stockid']").val(item.id);
						$("input[name='tScSlStockbillentryList[" + i + "].stockName']").val(item.name);
						setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + i + "].stockName']"), item.id, item.name);
					}
					setValOldIdAnOldName($("#stockName"), item.id, item.name);
				}else{
					$("input[name='tScSlStockbillentryList[" + index + "].stockid']").val(item.id);
					$("input[name='tScSlStockbillentryList[" + index + "].stockName']").val(item.name);
					setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + index + "].stockName']"), item.id, item.name);
				}
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}


//单价改变时执行操作
function onPriceChange(index){
	var priceEx = $("input[name='tScSlStockbillentryList["+index+"].taxPriceEx']").val();//单价
	if(priceEx > 0){
		$("select[name='tScSlStockbillentryList["+index+"].freeGifts_']").val(0);
		$("inout[name='tScSlStockbillentryList["+index+"].freeGifts']").val(0);
	}
	var qty = $("input[name='tScSlStockbillentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tScSlStockbillentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tScSlStockbillentryList["+index+"].taxRate']").val();//税率
	if(!priceEx){
		priceEx = 0;
	}
	if(!qty){
		qty = 0;
	}
	if(!discountRate){
		discountRate = 100;
	}
	if(!taxRate){
		taxRate = 0;
	}
	var price = priceEx / (1 + taxRate / 100);
	var amount = price * qty;
	var discountPrice = price * discountRate / 100;
	var discountAmount = discountPrice * qty;
	var taxAmountEx = priceEx*qty;
	var disprice = priceEx*discountRate/100;
	var disAmount = disprice*qty;
	var taxAmount = disAmount - (disAmount /(1+(taxRate/100)));
	$("input[name='tScSlStockbillentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tScSlStockbillentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tScSlStockbillentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tScSlStockbillentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tScSlStockbillentryList["+index+"].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
	$("input[name='tScSlStockbillentryList["+index+"].taxPrice']").val(disprice.toFixed(2));//折后单价
	$("input[name='tScSlStockbillentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
	$("input[name='tScSlStockbillentryList["+index+"].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
	setAllAmount();
}


function selectIcitemDialog(index){
	var tranType = $("#trantype").val();
	var itemNo = $("input[name='tScSlStockbillentryList["+index+"].itemNo']").val();
	var url = 'tScIcitemController.do?goInventorySelectDialog&number=' + itemNo+'&isZero=0';
	var stockId = $("#stockid").val();
	if(stockId){
		url = url+"&stockID="+stockId;
	}
	var width = 800;
	var height = 500;
	var title = "商品";
    //销售出库单
	if(tranType == "103") {
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
					var itemList = iframe.getMoreSelectionData();
					var itemLength = itemList.length;
					var nextTrLength = $('input[name="tScSlStockbillentryList[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
					var newLength = itemLength - nextTrLength - 1
					for (var j = 0; j < newLength; j++) {
						clickAddTScSlStockbillentryBtn(index);
					}
					$.each(itemList, function (i, item) {
						var rowindex = parseInt(index) + i;
						var id = item.itemId;//id
						var number = item.itemNo;//编码
						var name = item.itemName;//名称
						var model = item.model;//规格
						var kfPeriod = item.kfPeriod;//保质期
						var kfdateType = item.kfDateType;//保质期类型
						kfdateType = changeTypeValue(kfdateType);
						var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
						var batchManager = item.batchManager;//是否批号管理
						var weight = item.weight;//重量
						var batchNo = item.batchNo;//批号
						var invQty = item.basicQty;//库存基本数量
						var stockId = item.stockId;//库存仓库
						var stockName = item.stockName;//库存仓库名称
						var price = item.costPrice;//存货单价
						var taxRate = item.taxRateOut;//销项税率；
						var kfDate = item.kfDate;//生产日期；
						var chooseQty = item.chooseQty;//选择数量；
						if(kfDate){
							kfDate = kfDate.substring(0,10);
						}
						var url = "tScIcitemController.do?getItemInfo&id=" + id + "&rowIndex=" + rowindex + "&tranType=103";
						$.ajax({
							url: url,
							dataType: 'json',
							cache: false,
							success: function (data) {
								var attr = data.attributes;
								var xsLimitPrice = attr.xsLimitPrice;
								var barCode = attr.barCode;
								var unitId = attr.unitId;
								var cofficient = attr.cofficient;
								var secUnitId = attr.secUnitId;
								var secCoefficient = attr.secCoefficient;
								var basicUnitId = attr.basicUnitId;
								var itemId = attr.id;
								var rowIndex = attr.rowIndex;
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].qty").val(chooseQty);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].qty").trigger("change");
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].qty").trigger("input");
								var chkId = $("input[name='tScSlStockbillentryList[" + rowIndex + "].stockid']").val();
								if(!chkId) {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockid']").val(stockId);
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']").val(stockName);
									setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']"),stockId,stockName);
								}
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].taxRate']").val(taxRate);//税率
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].invQty']").val(invQty);//库存数量
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").val(batchNo);//库存数量
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);//销售限价
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemNo']").val(number);
								setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + rowIndex + "].itemNo']"), id, number);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemid']").val(id);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].model']").val(model);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemName']").val(name);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").trigger("change");
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].secunitid']").val(secUnitId);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].secCoefficient']").trigger("change");
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].basicunitid']").val(basicUnitId);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemweight']").val(weight);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemweight']").trigger("change");
								//$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockid']").val(stockId);
								//$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']").val(stockName);
								//$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']").attr("readonly", "readonly");
								//$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']").css("background-color", "rgb(226,226,226)");
								setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']"), stockId, stockName);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].isKfperiod']").val(iSKFPeriod);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchManager']").val(batchManager);
								if(kfDate) {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").val(kfDate);
								}
								if ("Y" == iSKFPeriod) {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").val(kfPeriod);
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").val(kfdateType);
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").attr("disabled","disabled");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype']").val(kfdateType);
									var kfdate = $("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").val();
									var interval = "";
									if (kfdateType == "0001") {
										interval = "y";
									} else if (kfdateType == "0002") {
										interval = "m";
									} else if (kfdateType == "0003") {
										interval = "d";
									}
									var periodDate = dateAdd(interval, kfPeriod, new Date(kfdate));
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].perioddate']").val(periodDate);
								} else {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").attr("readonly", "readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").attr("onClick", "");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").css("background-color", "rgb(226,226,226)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").attr("readonly", "readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").css("background-color", "rgb(226,226,226)");
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").attr("disabled", "disabled");
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").css("background-color", "rgb(226,226,226)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").val("");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").val("");
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").val("");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype']").val("");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].perioddate']").val("");
								}
								if ("N" == batchManager) {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("readonly", "readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").css("background-color", "rgb(226,226,226)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").removeAttr("datatype");
								} else {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").removeAttr("readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").css("background-color","rgb(255,255,255)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("datatype","*");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("onkeypress","keyDownInfo('"+rowIndex+"','batchNo')");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("ondblclick","selectInventoryInfo('"+rowIndex+"')");
								}
								$("#unitId\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$("#unitId\\[" + rowIndex + "\\]").combobox({
									onChange: function (newV, oldV) {
										if (oldV != newV) {
											var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV;
											$.ajax({
												url: changeUrl,
												dataType: 'json',
												cache: false,
												success: function (data) {
													var attributes = data.attributes;
													cofficient = attributes.coefficient;
													barCode = attributes.barCode;
													var xsLimitPrice = attributes.xsLimitPrice;
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);//采购限价
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").trigger("change");
												}
											});
										}
									}
								})
								$("#unitId\\[" + rowIndex + "\\]").combobox("setValue", unitId);
								//$("input[name='tScSlStockbillentryList["+index+"].unitId']").combobox("setValue",unitId);
								setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + rowIndex + "].itemNo']"), itemId, number);
							}
						});
						//}
					})
				},
				focus: true
			}, {
				name: '取消',
				callback: function () {
				}
			}]
		}).zindex();
	}else{
		url = 'tScIcitemController.do?goSelectDialog&number=' + itemNo;
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
					var itemList = iframe.getMoreSelectionData();
					//for(var i=0 ; i<itemList.length ; i++) {
					var itemLength = itemList.length;
					var nextTrLength =  $('input[name="tScSlStockbillentryList['+index+'].findex"]').parent().nextAll().length;//判断后面的行数
					var newLength = itemLength-nextTrLength-1
					for(var j=0 ; j<newLength ; j++){
						clickAddTScSlStockbillentryBtn(index);
					}
					var sonId = $("#sonid").val();
					$.each(itemList,function(i,item){
						//var length = $("#add_tScPoStockbillentry_table").find(">tr").length;
						//if(i > 0 ){
						//	clickAddTScSlStockbillentryBtn(index);
						//	index ++;
						//}
						//var item = itemList[i];
						var rowindex = parseInt(index)+i;
						var id = item.id;//id
						var number = item.number;//编码
						var name = item.name;//名称
						var model = item.model;//规格
						var kfPeriod = item.kFPeriod;//保质期
						var kfdateType = item.kFDateType;//保质期类型
						var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
						var batchManager = item.batchManager;//是否批号管理
						var weight = item.weight;//重量
						var taxRate = item.taxRateOut;//销项税率
						var stockSonId = item.stockSonId;
						var stockId = $("input[name='tScSlStockbillentryList[" + i + "].stockid']").val();//仓库id
						var batchNo = $("input[name='tScSlStockbillentryList[" + i + "].batchno']").val();//批号
						var url = "tScIcitemController.do?getItemInfo&id=" + id+"&rowIndex="+rowindex+"&tranType=52&stockId="+stockId+"&batchNo="+batchNo;
						$.ajax({
							url: url,
							dataType: 'json',
							cache: false,
							success: function (data) {
								var attr = data.attributes;
								var xsLimitPrice = attr.xsLimitPrice;
								var barCode = attr.barCode;
								var unitId = attr.unitId;
								var cofficient = attr.cofficient;
								var secUnitId = attr.secUnitId;
								var secCoefficient = attr.secCoefficient;
								var basicUnitId = attr.basicUnitId;
								var itemId = attr.id;
								var rowIndex = attr.rowIndex;
								var invQty = attr.invQty;//库存数量
								var stockId = attr.stockId;//默认仓库id
								var stockName = attr.stockName;//默认仓库名称
								var chkId = $("input[name='tScSlStockbillentryList[" + rowIndex + "].stockid']").val();
								if(!chkId) {
									if(sonId == stockSonId) {
										$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockid']").val(stockId);
										$("input[name='tScSlStockbillentryList[" + rowIndex + "].stockName']").val(stockName);
									}
								}
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].taxRate']").val(taxRate);//税率
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].invQty']").val(invQty);//库存数量
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);//销售限价
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemNo']").val(number);
								setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + rowIndex + "].itemNo']"),id,number);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemid']").val(id);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].model']").val(model);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemName']").val(name);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").trigger("blur");
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].secunitid']").val(secUnitId);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].secCoefficient']").trigger("change");
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].basicunitid']").val(basicUnitId);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemweight']").val(weight);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].itemweight']").trigger("change");
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].isKfperiod']").val(iSKFPeriod);
								$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchManager']").val(batchManager);
								if("Y"==iSKFPeriod) {
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").val(kfPeriod);
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").val(kfdateType);
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype_']").attr("disabled","disabled");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype']").val(kfdateType);
									var kfdate = $("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").val();
									var interval = "";
									if (kfdateType == "0001") {
										interval = "y";
									} else if (kfdateType == "0002") {
										interval = "m";
									} else if (kfdateType == "0003") {
										interval = "d";
									}
									var periodDate = dateAdd(interval, kfPeriod, new Date(kfdate));
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].perioddate']").val(periodDate);
								}else{
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").attr("readonly","readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").attr("onClick","");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").css("background-color","rgb(226,226,226)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").attr("readonly","readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").css("background-color","rgb(226,226,226)");
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype']").attr("disabled","disabled");
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype']").css("background-color","rgb(226,226,226)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfdate']").val("");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].kfperiod']").val("");
									$("select[name='tScSlStockbillentryList[" + rowIndex + "].kfdatetype']").val("");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].perioddate']").val("");
								}
								if("N" == batchManager){
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("readonly","readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").css("background-color","rgb(226,226,226)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").removeAttr("datatype");
								}else{
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").removeAttr("readonly");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").css("background-color","rgb(255,255,255)");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("datatype","*");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("onkeypress","keyDownInfo('"+rowIndex+"','batchNo')");
									$("input[name='tScSlStockbillentryList[" + rowIndex + "].batchno']").attr("ondblclick","selectInventoryInfo('"+rowIndex+"')");
								}
								$("#unitId\\[" + rowIndex+"\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$("#unitId\\[" + rowIndex+"\\]").combobox({
									onChange: function (newV, oldV) {
										if (oldV != newV) {
											var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV;
											$.ajax({
												url: changeUrl,
												dataType: 'json',
												cache: false,
												success: function (data) {
													var attributes = data.attributes;
													cofficient = attributes.coefficient;
													barCode = attributes.barCode;
													var cgLimitPrice = attributes.cgLimitPrice;
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);//采购限价
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
													$("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").trigger("change");
												}
											});
										}
									}
								})
								$("#unitId\\[" + rowIndex+"\\]").combobox("setValue", unitId);
								//$("input[name='tScSlStockbillentryList["+index+"].unitId']").combobox("setValue",unitId);
								setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + rowIndex + "].itemNo']"), itemId, number);
							}
						});
						//}
					})
				},
				focus: true
			}, {
				name: '取消',
				callback: function () {
				}
			}]
		}).zindex();
	}
}

function editQtyChange(index){
	var oldValue = $("input[name='tScSlStockbillentryList["+index+"].qty']").attr("oldvalue");
	var newValue = $("input[name='tScSlStockbillentryList["+index+"].qty']").val();
	if(oldValue > newValue){
		tip("数量不可小于当前数量");
		$("input[name='tScSlStockbillentryList["+index+"].qty']").val(oldValue);
		return;
	}
	setAllQty(index);
}

//汇总折扣金额
function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody = $("#tScSlStockbillentryList_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScSlStockbillentryList[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScSlStockbillentryList[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScSlStockbillentryList[" + i + "].qty']").val();//数量
		if(!amount){
			amount = 0;
		}
		if(!amount2){
			amount2 = 0;
		}
		if(!qty){
			qty = 0;
		}
		allAmount += parseFloat(amount);
		allAmountTax += parseFloat(amount2);
		allQty += parseFloat(qty);
	}
	var rebateAmount = $("#rebateamount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
	allAmount = allAmount.toFixed(CFG_MONEY);
	$("#amount").val(allAmount);
	//if(allAmount > 0) {
	//$("#sumQty")[0].innerText = allQty;
	//$("#sumAmount")[0].innerText = allAmountTax;
	//$("#sumTaxAmount")[0].innerText=allAmount;
	allAmount = allAmount - rebateAmount;
	allAmount = allAmount.toFixed(CFG_MONEY);
	$("#allamount").val(allAmount);
	//}

	$tbody = $("#tScSlStockbillentryList_table");
	var length = $tbody[0].rows.length;
	var allWeight = 0;
	for(var i=0 ; i<length ; i++){
		var weight = $("input[name='tScSlStockbillentryList[" + i + "].itemweight']").val();//商品重量
		var qty = $("input[name='tScSlStockbillentryList[" + i + "].qty").val();
		if(!qty){
			qty = 0;
		}
		if(!weight){
			weight = 0;
		}
		weight = (parseFloat(weight)*parseFloat(qty));
		$("input[name='tScSlStockbillentryList[" + i + "].weight_").val(weight);
		allWeight += weight;
	}
}

//汇总重量
var rowInfo = {};
function setAllWeight(index){
	$tbody = $("#tScSlStockbillentryList_table");
	var length = $tbody[0].rows.length;
	var allWeight = 0;
	for(var i=0 ; i<length ; i++){
		var weight = $("input[name='tScSlStockbillentryList[" + i + "].itemweight']").val();//商品重量
		var qty = $("input[name='tScSlStockbillentryList[" + i + "].qty").val();
		if(!qty){
			qty = 0;
		}
		if(!weight){
			weight = 0;
		}
		weight = (parseFloat(weight)*parseFloat(qty));
		$("input[name='tScSlStockbillentryList[" + i + "].weight_").val(weight);
		allWeight += weight;
	}
	//if(allAmount > 0) {
	//$("#sumQty")[0].innerText = allQty;
	//$("#sumAmount")[0].innerText = allAmountTax;
	//$("#sumTaxAmount")[0].innerText=allAmount;
	$("#weight").val(allWeight);
	//}

	var tranType = $("#trantype").val();
	if(tranType == "103") {
		var itemId = $("#itemid").val();
		var qty = $("input[name='tScSlStockbillentryList[" + index + "].qty']").val();
		if(isNaN(qty)){
			$("input[name='tScSlStockbillentryList["+index+"].qty']").val("");
			return;
		}
		var entryItemId = $("input[name='tScSlStockbillentryList[" + index + "].itemid']").val();//商品
		var unitId = $("#unitId\\[" + index + "\\]").combobox("getValue");
		var itemNo = $("input[name='tScSlStockbillentryList[" + index + "].itemNo']").val();//商品编号
		var freegifts = $("select[name='tScSlStockbillentryList[" + index + "].freegifts_']").val();
		var price = $("input[name='tScSlStockbillentryList[" + index + "].taxPriceEx']").val();//商品单价
		if (itemId && qty && entryItemId && unitId && freegifts=="0" && !price) {
			$.ajax({
				type: 'POST',
				url: 'tScQuoteController.do?getPrice',
				async: false,
				cache: false,
				data: {'tranType':tranType,'itemId': itemId, 'qty': qty, 'entryItemId': entryItemId, 'unitId': unitId, 'itemNo': itemNo},
				dataType: 'json',
				success: function (data) {
					var price = data.attributes.price;//报价单价
					if (data.success == true) {
						var resultData = data.attributes;
						var salesID = resultData.salesID;//促销类型
						var salesType = resultData.salesType;//促销类型分类 0：调价 1：买一赠一
						var salesName = "调价政策";
						if ("1" == salesType) {
							salesName = "买一赠一"
						}
						$("input[name='tScSlStockbillentryList[" + index + "].salesid']").val(salesID);
						$("input[name='tScSlStockbillentryList[" + index + "].salesName']").val(salesName);
						if ("1" == salesType) {
							var isFreeGift = $("input[name='tScSlStockbillentryList[" + index + "].isFreeGift']").val();
							if (isFreeGift == "1") {
								var rowLength = rowInfo[index];
								for (var i = 0; i < rowLength; i++) {
									clickDeltScSlStockbillentryListBtn((index + 1));
								}
							}
							$("input[name='tScSlStockbillentryList[" + index + "].isFreeGift']").val(1);
							var freeGiftInfo = resultData.freeGiftInfo;
							rowInfo[index] = freeGiftInfo.length;
							for (var j = 0; j < freeGiftInfo.length; j++) {
								clickAddTScSlStockbillentryBtn((index + j));
								var itemId = freeGiftInfo[j].itemId;
								var itemNo = freeGiftInfo[j].itemNo;
								var itemName = freeGiftInfo[j].itemName;
								var model = freeGiftInfo[j].model;
								var barCode = freeGiftInfo[j].barCode;
								var unitId = freeGiftInfo[j].unitId;
								var qty = freeGiftInfo[j].qty;
								var freeIndex = index + j + 1;
								//赠品信息
								//商品
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].itemid']").val(itemId);
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].itemNo']").val(itemNo);
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].itemNo']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].itemNo']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].itemName']").val(itemName);
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].model']").val(model);
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].barCode']").val(barCode);
								setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + freeIndex + "].itemNo']"), itemId, itemNo);
								//批号
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].batchno']").attr("readonly","readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].batchno']").css("background-color", "rgb(226, 226, 226)");
								//数量
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].qty']").val(qty);
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].qty']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].qty']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].qty']").trigger("change");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].basicQty']").val(qty);
								//单价
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxPriceEx']").val(0);
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxPriceEx']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxPriceEx']").trigger("change");
								//金额
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxAmountEx']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
								//折扣率
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].discountRate']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
								//折后金额
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].inTaxAmount']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
								//税率
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxRate']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
								//赠品标记
								$("select[name='tScSlStockbillentryList[" + freeIndex + "].freegifts_']").val(1);
								$("select[name='tScSlStockbillentryList[" + freeIndex + "].freegifts_']").attr("disabled", "disabled");
								$("select[name='tScSlStockbillentryList[" + freeIndex + "].freegifts_']").trigger("change");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].freegifts']").val(1);
								//仓库
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].stockName']").removeAttr("readonly");
								//单位
								$('#unitId\\[' + freeIndex + '\\]').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$('#unitId\\[' + freeIndex + '\\]').combobox("setValue", unitId);
								$('#unitId\\[' + freeIndex + '\\]').combobox("disable");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].isFreeGift']").val(1);
								//生产日期
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].kfdate']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].kfdate']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].kfdate']").removeAttr("onclick");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].kfdate']").val("");
								//保质期
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].kfperiod']").attr("readonly", "readonly");
								$("input[name='tScSlStockbillentryList[" + freeIndex + "].kfperiod']").css("background-color", "rgb(226, 226, 226)");
								//保质期类型
								$("select[name='tScSlStockbillentryList[" + freeIndex + "].kfdatetype_']").attr("disabled", "disabled");
							}
						} else {
							var isFreeGift = $("input[name='tScSlStockbillentryList[" + index + "].isFreeGift']").val();
							if (isFreeGift == "1") {
								var rowLength = rowInfo[index];
								for (var i = 0; i < rowLength; i++) {
									clickDeltScSlStockbillentryListBtn((index + 1));
								}
							}
							$("input[name='tScSlStockbillentryList[" + index + "].isFreeGift']").val(0);
							$("input[name='tScSlStockbillentryList[" + index + "].taxPriceEx']").val(price);
							$("input[name='tScSlStockbillentryList[" + index + "].taxPriceEx']").trigger("change");
						}
					}else{
						$("input[name='tScSlStockbillentryList[" + index + "].isFreeGift']").val(2);
						$("input[name='tScSlStockbillentryList[" + index + "].taxPriceEx']").val(price);
						$("input[name='tScSlStockbillentryList[" + index + "].taxPriceEx']").trigger("change");
					}
				}
			});
		}
	}
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
				$("#accountName").trigger("blur");
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//保存前校验单价是否为0
var isCheck = true;
var isCheck2 = true;
function checkPriceIsZero(item){
	var isCheckNegative = $("#isCheckNegative").val();
	var accountId = $("#accountid").val();
	if(accountId){
		var curPayAmount = $("#curpayamount").val();
		if(curPayAmount <= 0){
			tip("本次收款不可小于0，请确认");
			return false;
		}
	}
	var iscreditmgr = $("#iscreditmgr").val();
	if("1" == iscreditmgr){
		var creditamount = $("#creditamount").val();//客户信用额度
		var countAmount = $("#countAmount").val();//客户已执行金额
		var allamount = $("#allamount").val();//应付金额
		var timePoint = $("#timePoint").val();
		if("0" == timePoint){
			if(parseFloat(allamount) + parseFloat(countAmount)  > parseFloat(creditamount)) {
				var method = $("#method").val();
				if("1" == method){
					tip("金额已超出客户的信用额度，不可保存");
					return false;
				}
			}
		}
	}
	var $tbody = $("#tScSlStockbillentryList_table");
	var length = $tbody[0].rows.length;
	var checkItemName = "";//价格低于销售限价商品名称
	var checkValue="";
	var tranType = $("#trantype").val();//单据类型 103：；出库单 104：退货单
	for(var checkIndex=0 ; checkIndex<length ; checkIndex++){
		var price = $("input[name='tScSlStockbillentryList["+checkIndex+"].taxPriceEx']").val();//单价
		var itemName = $("input[name='tScSlStockbillentryList["+checkIndex+"].itemName']").val();//商品名称
		var xsLimitPrice = $("input[name='tScSlStockbillentryList["+checkIndex+"].xsLimitPrice']").val();//销售限价
		if(!xsLimitPrice){
			xsLimitPrice = 0;
		}
		var qty = $("input[name='tScSlStockbillentryList["+checkIndex+"].basicQty']").val();//基本数量
		var xqty = $("input[name='tScSlStockbillentryList["+checkIndex+"].qty']").val();//数量
		var billQty = $("input[name='tScSlStockbillentryList["+checkIndex+"].billQty']").val();//订单数量
		var stockQty = $("input[name='tScSlStockbillentryList["+checkIndex+"].stockQty']").val();//订单执行数量
		var itemId = $("input[name='tScSlStockbillentryList["+checkIndex+"].itemid']").val();
		var batchNo = $("input[name='tScSlStockbillentryList["+checkIndex+"].batchno']").val();
		var stockId = $("input[name='tScSlStockbillentryList["+checkIndex+"].stockid']").val();
		//TODO 系统设置入库数量允超数量
		var allowPassQty = 0;
		var CFG_CANNOTINREPERTORYNYT_PURCHASEN = $("#CFG_CANNOTOUTREPERTORYNGT_SALE").val();
		if("1" == CFG_CANNOTINREPERTORYNYT_PURCHASEN) {
			if (parseInt(allowPassQty) >= 0) {
				if (parseFloat(billQty) > 0 || stockQty) {
					if ("103" == tranType) {
						if (!stockQty) {
							stockQty = 0;
						}
						if ((parseFloat(xqty) - (parseFloat(billQty) - parseFloat(stockQty))) > allowPassQty) {
							var cs = parseFloat(xqty) - (parseFloat(billQty) - parseFloat(stockQty)) - allowPassQty;
							alertTip("商品【" + itemName + "】出库数量超出订单数量:" + cs + ",请确认");
							return false;
						}
					} else if ("104" == tranType) {
						if (parseFloat(xqty) > parseFloat(billQty)) {
							var cs = parseFloat(xqty) - parseFloat(billQty);
							alertTip("商品【" + itemName + "】退货数量超出订单数量:" + cs + ",请确认");
							return false;
						}
					}
				}
			}
		}
		if(isCheckNegative){
			checkValue += itemId+"#"+itemName+"#"+stockId+"#"+qty+"#"+batchNo+",";
		}
		if(parseFloat(xsLimitPrice) > 0 && parseFloat(price) < parseFloat(xsLimitPrice)){
			checkItemName += itemName+",";
		}
	}
	if(isCheck || isCheck2) {
		if (checkItemName.length > 0 || checkValue.length > 0) {
			if(checkItemName && isCheck) {
				itemName = itemName.substring(0, itemName.length - 1);
				$.dialog.confirm("商品[" + itemName + "]的单价低于销售限价，是否继续保存？", function () {
						isCheck = false;
						$("#formobj").submit();
					}
					, function () {
						//
					}
				)
				return false;
			}else{
				if(tranType == "103") {
					//销售出库单时，校验数量是否超出库存
					var url = "tScIcInventoryController.do?checkIsOverInv";
					$.ajax({
						url: url,
						async: false,
						dataType: 'json',
						type: 'POST',
						data: {checkValue: checkValue},
						cache: false,
						success: function (data) {
							if (data.success) {
								parent.$.dialog.confirm("商品[" + data.msg + "]的数量超出库存数量，是否继续保存？", function () {
										isCheck = false;
										isCheck2 = false;
										$("#formobj").submit();
									}
									, function () {
										//
									}
								)
							} else {
								isCheck = false;
								isCheck2 = false;
								$("#formobj").submit();
							}
						}
					});
					return false;
				}else{
					return true;
				}
				//parent.$.dialog.confirm("商品[" + overItemName + "]的数量超出库存数量，是否继续保存？", function () {
				//		isCheck = false;
				//		isCheck2 = false;
				//		$("#formobj").submit();
				//	}
				//	, function () {
				//		//
				//	}
				//)
			}
		}else {
			return true;
		}
	}else{
		return true;
	}

	//}else{
	//	continue
	//}
	//if(isCheck) {
	//	$.ajax({
	//		url: url,
	//		dataType: 'json',
	//		cache: false,
	//		async: false,
	//		success: function(data) {
	//			if(data.success){
	//				var $tbody = $("#tScPoStockbillentry_table");
	//				var length = $tbody[0].rows.length;
	//				var itemName = "";
	//				for(var i=0 ; i<length ; i++){
	//					var price = $("input[name='tScSlStockbillentryList["+i+"].taxPriceEx']").val();
	//					if(!price || price == 0){
	//						itemName += $("input[name='tScSlStockbillentryList["+i+"].itemName']").val()+",";
	//					}
	//				}
	//				if (itemName.length > 0) {
	//					itemName = itemName.substring(0,itemName.length-1);
	//					$.dialog.confirm("商品[" + itemName + "]的单价为0，是否继续保存？", function () {
	//						isCheck = false;
	//						$("#formobj").submit();
	//					}, function () {
	//						//
	//					});
	//				} else {
	//					isCheck = false;
	//					$("#formobj").submit();
	//				}
	//			}else{
	//				isCheck = false;
	//				$("#formobj").submit();
	//			}
	//		}
	//	});
	//	return false;
	//}else{
	//	return true;
	//}

}


function   dateAdd(interval,number,date)
{
	/*
	 *   功能:实现VBScript的DateAdd功能.
	 *   参数:interval,字符串表达式，表示要添加的时间间隔.
	 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
	 *   参数:date,时间对象.
	 *   返回:新的时间对象.
	 *   var   now   =   new   Date();
	 *   var   newDate   =   DateAdd( "d ",5,now);
	 *---------------   DateAdd(interval,number,date)   -----------------
	 */
	number = parseInt(number);
	switch(interval)
	{
		case   "y"   :   {
			date.setFullYear(date.getFullYear()+number);
			break;
		}
		case   "q"   :   {
			date.setMonth(date.getMonth()+number*3);
			break;
		}
		case   "m"   :   {
			date.setMonth(date.getMonth()+number);
			break;
		}
		case   "w"   :   {
			date.setDate(date.getDate()+number*7);
			break;
		}
		case   "d"   :   {
			date.setDate(date.getDate()+number);
			break;
		}
		case   "h"   :   {
			date.setHours(date.getHours()+number);
			break;
		}
		case   "mi"   :   {
			date.setMinutes(date.getMinutes()+number);
			break;
		}
		case   "s"   :   {
			date.setSeconds(date.getSeconds()+number);
			break;
		}
		default   :   {
			date.setDate(d.getDate()+number);
			break;
		}
	}
	var dateStr = date.getFullYear()+"-"+(((date.getMonth()+1)<10)?"0"+(date.getMonth()+1):(date.getMonth()+1))+
		"-"+(date.getDate()<10 ? "0"+date.getDate() : date.getDate());
	return dateStr
}


//设置有效期至
function setPeriodDate(index){
	var kfdate = $("input[name='tScSlStockbillentryList["+index+"].kfdate']").val();//生产日期
	var kfPeriod = $("input[name='tScSlStockbillentryList["+index+"].kfperiod']").val();//保质期
	var kfdateType = $("select[name='tScSlStockbillentryList["+index+"].kfdatetype_']").val();//保质期类型
	var interval = "";
	if(kfdateType=="0001"){
		interval = "y";
	}else if(kfdateType == "0002"){
		interval = "m";
	}else if(kfdateType == "0003"){
		interval = "d";
	}
	var periodDate = dateAdd(interval,kfPeriod,new Date(kfdate));
	$("input[name='tScSlStockbillentryList[" + index + "].perioddate']").val(periodDate);
}

//选单
var CFG_NUMBER = $("#CFG_NUMBER").val();
var CFG_MONEY = $("#CFG_MONEY").val();
function selectBillDialog(){
	var itemId = $("#itemid").val();
	if(!itemId){
		tip("请选择客户后再进行选单操作");
		$("#chooseType").val("");
		return;
	}
	var tranType = $("#chooseType").val();
	var title = "销售订单";
	var url = "";
	var sonId = $("#sonid").val();
	if("102" ==  tranType) {
		url = "tScOrderController.do?goSelectDialog&itemID=" + itemId + "&tranType=" + tranType+"&sonId="+sonId;
		url = encodeURI(url);
	}else if("103" == tranType){
		title = "销售出库单"
		url = "tScSlStockbillController.do?goSelectDialog&itemid="+itemId+"&trantype="+tranType+"&sonId="+sonId;
		url = encodeURI(url);
	}else if("107" == tranType){
		tip(tranType);
		return;
	}else if("109" == tranType){
		tip(tranType);
		return;
	}else{
		tip("请选择选单类型");
		return;
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
				var itemList = iframe.getSelectionData();
				//var ids ="";
				//for(var i=0 ; i< itemList.length ; i++){
				//	var item = itemList[i];
				//	ids += item.id+",";
				//}
				var item = itemList[itemList.length-1];
				$("#classidSrc").val(item.tranType);
				$("#idSrc").val(item.id);
				$("#billnoSrc").val(item.billno);
				//$("#itemId").val(item.itemId);
				//$("#itemName").val(item.itemName);
				$("#itemName").attr("readonly","readonly");
				//setValOldIdAnOldName($("#itemName"), item.itemId, item.itemName);
				$("#deptid").val(item.deptid);
				$("#deptName").val(item.deptName);
				setValOldIdAnOldName($("#deptName"), item.deptid, item.deptName);
				$("#empid").val(item.empid);
				$("#empName").val(item.empName);
				setValOldIdAnOldName($("#empName"), item.empid, item.empName);
				$("#stockid").val(item.astockId);
				$("#stockName").val(item.astockName);
				setValOldIdAnOldName($("#stockName"), item.astockId, item.astockName);
				$("#contact").val(item.contact);
				$("#mobilePhone").val(item.mobilePhone);
				$("#phone").val(item.phone);
				$("#fax").val(item.fax);
				$("#address").val(item.address);
				//var entryUrl = "tPoOrderController.do?loadEntryList&id="+ids+"&tranType="+tranType;
				//$.ajax({
				//	url: entryUrl,
				//	dataType: 'json',
				//	cache: false,
				//	async: false,
				//	success: function (data) {
				//		if(data.length > 0){
				var length = $("#add_tScSlStockbillentry_table").find(">tr").length;
				length--;
				var id = $("input[name='tScSlStockbillentryList["+length+"].itemid']").val();
				var dataLength = itemList.length;
				if(!id){
					dataLength--;
				}
				for(var k=0 ; k < dataLength ; k++){
					clickAddTScSlStockbillentryBtn(length)
				}
				if(id){
					length++;
				}
				for(var i=0 ; i<itemList.length ;i++) {
					var row = itemList[i];
					for(var k in row){
						if(k=="unitid"){
							var itemId = row.itemid;
							if(tranType == "103"){
								itemId = row.entryItemId;
							}
							$("#unitId\\["+length+"\\]").combobox("reload","tScIcitemController.do?loadItemUnit&id=" + itemId);
							$("#unitId\\[" + length+"\\]").combobox("setValue", row[k]);
							$("#unitId\\[" + length+"\\]").combobox("disable");
						}else if(k=="freegifts" || k=="kfdatetype" || k == "kfdateTypeInfo"){
							$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").attr("disabled","disabled");
							$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").css("background","rgb(226,226,226)");
							if("Y" == row.isKfperiod && k=="kfdatetype"){
								$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").val(row[k]);
								$("input[name='tScSlStockbillentryList[" + length + "].kfdatetype']").val(row[k]);
							}
							else if("Y" == row.isKfperiod && k=="kfdateTypeInfo"){
								$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").val(row[k]);
								$("input[name='tScSlStockbillentryList[" + length + "].kfdatetype']").val(row[k]);
							}
							else if(k=="freegifts"){
								$("select[name='tScSlStockbillentryList[" + length + "].freegifts_']").val(row[k]);
								$("select[name='tScSlStockbillentryList[" + length + "].freegifts_']").attr("disabled","disabled");
								$("select[name='tScSlStockbillentryList[" + length + "].freegifts_']").css("background-color","rgb(226,226,226)");
								$("input[name='tScSlStockbillentryList[" + length + "].freegifts']").val(row[k]);
							}
						}else if(k=="qty"){
							var stockQty;
							if("102" == tranType) {
								stockQty = row.stockQty == "" ? 0 : row.stockQty;//发货数量
							}else if("103" == tranType){
								stockQty = row.commitQty == "" ? 0 : row.commitQty;//退货数量
							}
							var qty = row[k];
							var basicQty = row["qty"];
							var coe = row["coefficient"];
							qty = ((parseFloat(basicQty)-parseFloat(stockQty))).toFixed(CFG_NUMBER);
							$("input[name='tScSlStockbillentryList[" +length + "]." + k + "']").val(qty);
							$("input[name='tScSlStockbillentryList[" +length + "].billQty']").val(row[k]);
						}else if(k == "batchNo"){
							$("input[name='tScSlStockbillentryList[" +length + "].batchno']").val(row[k]);
						}
						else {
							$("input[name='tScSlStockbillentryList[" +length + "]." + k + "']").val(row[k]);
						}
						if(k == "itemNumber" || k == "entryItemNo"){
							$("input[name='tScSlStockbillentryList[" + length + "].itemNo']").attr("readonly","readonly");
						}
						if(k=="kfdate" && "Y"==row.isKfperiod){
							var kfdate = row[k];
							kfdate = dateFormatter(kfdate);
							$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").val(kfdate);
						}
					}
					$("input[name='tScSlStockbillentryList[" + length + "].basicunitid']").val(row.basicUnitId);
					$("input[name='tScSlStockbillentryList[" + length + "].basicunitid']").val(row.basicUnitId);
					$("input[name='tScSlStockbillentryList[" + length + "].kfperiod']").attr("readonly","readonly");
					$("input[name='tScSlStockbillentryList[" + length + "].kfperiod']").css("background-color","rgb(226,226,226)");
					if("Y"==row.isKfperiod) {
						setPeriodDate(length);
					}else{
						$("input[sname='tScSlStockbillentryList[" + length + "].kfdate']").attr("readonly","readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").css("background-color","rgb(226,226,226)");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").attr("onClick","");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").val("");
						$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").attr("disabled","disabled");
						$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").css("background-color","rgb(226,226,226)");
						$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype_']").val("");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdatetype']").val("");
						$("input[name='tScSlStockbillentryList[" + length + "].kfperiod']").val("");
						$("input[name='tScSlStockbillentryList[" + length + "].perioddate']").val("");
					}
					if("N"==row.batchManager){
						$("input[name='tScSlStockbillentryList[" + length + "].batchno']").attr("readonly","readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].batchno']").css("background-color","rgb(226,226,226)");
						$("input[name='tScSlStockbillentryList[" + length + "].batchno']").removeAttr("datatype");
					} else {
						//销售出库单中取数据，不可编辑批号、仓库
						if(tranType == "103"){
							$("input[name='tScSlStockbillentryList[" + length + "].batchno']").attr("readonly","readonly");
							$("input[name='tScSlStockbillentryList[" + length + "].batchno']").css("background-color","rgb(226,226,226)");
							$("input[name='tScSlStockbillentryList[" + length + "].batchno']").removeAttr("datatype");

							$("input[name='tScSlStockbillentryList[" + length + "].stockName']").attr("readonly","readonly");
							$("input[name='tScSlStockbillentryList[" + length + "].stockName']").css("background-color","rgb(226,226,226)");
						} else {
							$("input[name='tScSlStockbillentryList[" + length + "].batchno']").attr("onkeypress","keyDownInfo('"+length+"','batchNo')");
							document.getElementsByName("tScSlStockbillentryList\[" + length + "\]\.batchno")[0].className = 'inputxt popup-select';
						}
					}
					var itemid = row.itemid ;
					var itemName = row.itemName;
					var itemNo = row.itemNumber;
					if(tranType == "103"){
						itemName = row.entryItemName;
						itemid = row.entryItemId;
						itemNo = row.entryItemNo;
					}

					$("input[name='tScSlStockbillentryList["+length+"].itemid']").val(itemid);
					$("input[name='tScSlStockbillentryList["+length+"].itemNo']").val(itemNo);
					$("input[name='tScSlStockbillentryList["+length+"].itemName']").val(itemName);
					if(tranType == "103") {
						$("input[name='tScSlStockbillentryList[" + length + "].stockid']").val(row.entryStockId);
						$("input[name='tScSlStockbillentryList[" + length + "].stockName']").val(row.entryStockName);
						setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + length + "].stockName']"), row.entryStockId, row.entryStockName);
					}
					//var basicCoefficient = $("input[name='tScSlStockbillentryList[" + length + "].basicCoefficient']").val();
					//var discountPrice = $("input[name='tScSlStockbillentryList[" + length + "].discountPrice']").val();
					//var costPrice = parseFloat(discountPrice)/parseFloat(basicCoefficient)
					//var qty = $("input[name='tScSlStockbillentryList[" + length + "].qty']").val();
					//$("input[name='tScSlStockbillentryList[" + length + "].costPrice']").val(costPrice.toFixed(2));
					//$("input[name='tScSlStockbillentryList[" + length + "].costAmount']").val((costPrice.toFixed(2)*qty).toFixed(2))
					if("102" == tranType) {
						$("input[name='tScSlStockbillentryList[" + length + "].idSrc']").val(row.id);
						$("input[name='tScSlStockbillentryList[" + length + "].billnoSrc']").val(row.billNo);
						$("input[name='tScSlStockbillentryList[" + length + "].classidSrc']").val(row.tranType);
						$("input[name='tScSlStockbillentryList[" + length + "].entryidSrc']").val(row.entryId);
						$("input[name='tScSlStockbillentryList[" + length + "].billnoOrder']").val(row.billNo);
						$("input[name='tScSlStockbillentryList[" + length + "].idOrder']").val(row.id);
						$("input[name='tScSlStockbillentryList[" + length + "].entryidOrder']").val(row.entryId);
					}else if("103" == tranType){
						$("input[name='tScSlStockbillentryList[" + length + "].idSrc']").val(row.id);
						$("input[name='tScSlStockbillentryList[" + length + "].billnoSrc']").val(row.billNo);
						$("input[name='tScSlStockbillentryList[" + length + "].classidSrc']").val(row.tranType);
						$("input[name='tScSlStockbillentryList[" + length + "].entryidSrc']").val(row.entryId);
						$("input[name='tScSlStockbillentryList[" + length + "].billnoOrder']").val(row.billnoorder);
						$("input[name='tScSlStockbillentryList[" + length + "].idOrder']").val(row.idorder);
						$("input[name='tScSlStockbillentryList[" + length + "].entryidOrder']").val(row.entryidorder);

						//单价
						$("input[name='tScSlStockbillentryList[" + length + "].taxPriceEx']").val(row.taxPriceEx);
					}
					setFunctionInfo(length);
					$("input[name='tScSlStockbillentryList[" + length + "].qty']").trigger("change");
					$("input[name='tScSlStockbillentryList[" + length + "].taxPriceEx']").trigger("change");
					$("input[name='tScSlStockbillentryList[" + length + "].taxAmountEx']").trigger("change");

					var itemNo = $("input[name='tScSlStockbillentryList[" + length + "].itemNo']").val();
					var itemId = $("input[name='tScSlStockbillentryList[" + length + "].itemid']").val();
					setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + length + "].itemNo']"),itemId,itemNo);

					var stockName = $("input[name='tScSlStockbillentryList[" + length + "].stockName']").val();
					var stockid = $("input[name='tScSlStockbillentryList[" + length + "].stockid']").val();
					if(stockid) {
						setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + length + "].stockName']"), stockid, stockName);
					}
					var tranTypeName = row.tranTypeName;
					$("input[name='tScSlStockbillentryList[" + length + "].classidName']").val(tranTypeName);
					var salesId = row.salesID;
					$("input[name='tScSlStockbillentryList[" + length + "].salesid']").val(salesId);
					var isFreeGift = row.isFreeGift;
					var freeGifts = row.freegifts;
					if(freeGifts == "1"){
						//批号
						$("input[name='tScSlStockbillentryList[" + length + "].batchno']").attr("readonly","readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].batchno']").css("background-color", "rgb(226, 226, 226)");
						$("input[name='tScSlStockbillentryList[" + length + "].batchno']").removeAttr("datatype");
						//数量
						$("input[name='tScSlStockbillentryList[" + length + "].qty']").val(qty);
						$("input[name='tScSlStockbillentryList[" + length + "].qty']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].qty']").css("background-color", "rgb(226, 226, 226)");
						$("input[name='tScSlStockbillentryList[" + length + "].qty']").trigger("change");
						//单价
						$("input[name='tScSlStockbillentryList[" + length + "].taxPriceEx']").val(0);
						$("input[name='tScSlStockbillentryList[" + length + "].taxPriceEx']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
						$("input[name='tScSlStockbillentryList[" + length + "].taxPriceEx']").trigger("change");
						//金额
						$("input[name='tScSlStockbillentryList[" + length + "].taxAmountEx']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
						//折扣率
						$("input[name='tScSlStockbillentryList[" + length + "].discountRate']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
						//折后金额
						$("input[name='tScSlStockbillentryList[" + length + "].inTaxAmount']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
						//税率
						$("input[name='tScSlStockbillentryList[" + length + "].taxRate']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
						//赠品标记
						$("select[name='tScSlStockbillentryList[" + length + "].freegifts_']").val(1);
						$("select[name='tScSlStockbillentryList[" + length + "].freegifts_']").attr("disabled", "disabled");
						$("select[name='tScSlStockbillentryList[" + length + "].freegifts_']").trigger("change");
						$("input[name='tScSlStockbillentryList[" + length + "].freegifts']").val(1);
						//仓库
						$("input[name='tScSlStockbillentryList[" + length + "].stockName']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].stockName']").css("background-color", "rgb(226, 226, 226)");
						//生产日期
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").css("background-color", "rgb(226, 226, 226)");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").removeAttr("onclick");
						$("input[name='tScSlStockbillentryList[" + length + "].kfdate']").val("");
						//保质期
						$("input[name='tScSlStockbillentryList[" + length + "].kfperiod']").attr("readonly", "readonly");
						$("input[name='tScSlStockbillentryList[" + length + "].kfperiod']").css("background-color", "rgb(226, 226, 226)");
						//保质期类型
						$("select[name='tScSlStockbillentryList[" + length + "].kfdatetype']").attr("disabled", "disabled");

					}
					length ++;

				}
				setAllAmount();
				//setAllWeight();
				$("#chooseType").val("");
				//}
				//}
				//});
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
				//重置选单类型
				$("#chooseType").val("");
			}
		}]
	}).zindex();
	$("#chooseType").val("");
}

//更改优惠金额时执行操作
function changeAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	var rebateAmount = $("#rebateamount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
	rebateAmount = parseFloat(rebateAmount).toFixed(CFG_MONEY);
	$("#rebateamount").val(rebateAmount);
	var allAmount = $("#amount").val();
	if(allAmount){
		allAmount = (parseFloat(allAmount) - parseFloat(rebateAmount)).toFixed(CFG_MONEY);
		$("#allamount").val(allAmount);
	}
}

//表格校验事件
function orderListStockBlur(rowIndex,id,name){
	checkInput( $('input[name="tScSlStockbillentryList['+rowIndex+'].'+id+'"]'),$('input[name="tScSlStockbillentryList['+rowIndex+'].'+name+'"]'));
}

//判断是否允许增行操作
function checkAllowAddLine(index){
	var isAddLine = true;
	if(!isAddLine){
		$("#addTScSlStockbillentryBtn\\["+index+"\\]").css("display","none");
		$("input[name='tScSlStockbillentryList["+index+"].itemNo']").attr("readonly","readonly");
	}
}

//日期格式化
function dateFormatter(date){
	date = new Date(date);
	var year = date.getFullYear();
	var month = (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
	var date = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	return year+"-"+month+"-"+date;
}

//审核前校验
function checkAudit(){
	//信用控制
	var iscreditmgr = $("#iscreditmgr").val();
	if("1" == iscreditmgr){
		var creditamount = $("#creditamount").val();//客户信用额度
		var countAmount = $("#countAmount").val();//客户已执行金额
		var allamount = $("#allamount").val();//应付金额
		var timePoint = $("#timePoint").val();
		if("1" == timePoint){
			if(parseFloat(allamount) + parseFloat(countAmount)  > parseFloat(creditamount)) {
				var method = $("#method").val();
				if("1" == method){
					return "金额已超出客户的信用额度，不可审核";
				}
			}
		}
	}
	var isCheckNegative = $("#isCheckNegative").val();
	if(isCheckNegative == "true") {
		var tranType = $("#tranType").val();
		if("53" == tranType){
			var isContinue = true;
			var itemName = "";
			var billId = $("#id").val();
			$.ajax({
				async: false,
				url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=T_SC_Po_StockBillEntry&parentId=fid",
				type: "POST",
				dataType: "json",
				success: function (data) {
					if(!data.success){
						isContinue = false;
						itemName = data.msg;
					}
				}
			})
			if (isContinue) {
				return false;
			} else {
				itemName = itemName.substring(0, itemName.length - 1);
				return "该单据审核后,商品【"+itemName+"】将出现负库存，不可审核，请确认";
			}
		}else{
			return false;
		}
	}else{
		return false;
	}
}

//反审核前校验
function checkUnAudit(){
	var isCheckNegative = $("#isCheckNegative").val();
	if(isCheckNegative == "true") {
		var tranType = $("#tranType").val();
		if("52"==tranType) {
			var isContinue = true;
			var itemName = "";
			var billId = $("#id").val();
			$.ajax({
				async: false,
				url: "tSAuditController.do?checkIsNegative&id=" + billId + "&tranType=" + tranType + "&tableName=T_SC_Po_StockBillEntry&parentId=fid",
				type: "POST",
				dataType: "json",
				success: function (data) {
					if (!data.success) {
						isContinue = false;
						itemName = data.msg;
					}
				}
			})
			if (isContinue) {
				return false;
			} else {
				itemName = itemName.substring(0, itemName.length - 1);
				return "该单据反审核后,商品【"+itemName+"】将出现负库存，不可反审核，请确认";
			}
		}
	}else{
		return false;
	}
}

//审核后执行
function afterAudit(){
	var tranType = $("#tranType").val();
	var id = $("#id").val();
	var url = "tScSlStockbillController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		success: function (d) {
			//
		}
	});
}

//反审核后执行
function afterUnAudit(billId){
	var tranType = $("#tranType").val();
	var url = "tScSlStockbillController.do?afterAudit&audit=0&id=" + billId+"&tranType="+tranType;
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		success: function (d) {
			//
		}
	});
}

//物流信息
var logisticalValue = "";
function goLogistical(){
	var id = $("#id").val();
	var tranType = $("#trantype").val();
	var checkState = $("#checkState").val();
	var load = $("#load").val();
	var url = 'tScSlStockbillController.do?goLogisticalUpdate&fid=' + id+"&tranType="+tranType+"&logisticalValue="+logisticalValue;
	if(checkState == 2 || "detail" == load){
		url += "&load=detail";
	}
	var width = 600;
	var height = 250;
	var title = "物流信息";
	$.dialog({
		content: 'url:' + url,
		title: title,
		lock: true,
		zIndex: 500,
		height: height,
		cache: false,
		width: width,
		opacity: 0.3,
		button: [{
			name: '确定',
			callback: function () {
				iframe = this.iframe.contentWindow;
				var json = iframe.getJsonInfo();
				logisticalValue = json;
				$("#json").val(json);
				json = eval('('+json+')');
				var freight = json.freight || 0;
				$("#freight").val(freight);
				var CFG_MONEY = $("#CFG_MONEY").val();
				var allAmount = $("#allamount").val() || 0;
				var rebateamount = $("#rebateamount").val() || 0;
				var curPayAmount = json.curPayAmount || 0;
				allAmount = (parseFloat(allAmount) + parseFloat(freight)).toFixed(CFG_MONEY);
				$("#amount").val(0);
				allAmount = (parseFloat(allAmount) - parseFloat(rebateamount)).toFixed(CFG_MONEY);
				if(curPayAmount){
					allAmount = (parseFloat(allAmount) - parseFloat(curPayAmount)).toFixed(CFG_MONEY);
				}
				$("#allamount").val(allAmount);
				return true;
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//校验结算账户
function checkcurPayAmount(){
	var accountName = $("#accountName").val();
	if(accountName){
		$("#curpayamount").removeAttr("readonly");
		$("#curpayamount").css("background-color","white");
		$("#curpayamount").attr("datatype","vInt");
	}else{
		$("#curpayamount").attr("readonly","readonly");
		$("#curpayamount").css("background-color","rgb(226,226,226)");
		$("#curpayamount").val("");
		$("#curpayamount").removeAttr("datatype");
	}
}

//批号回车事件
function selectInventoryInfo(index){
	var itemId = $("input[name='tScSlStockbillentryList["+index+"].itemid']").val();//商品id
	var stockId = $("input[name='tScSlStockbillentryList["+index+"].stockid']").val();//仓库id
	var url = 'tScIcitemController.do?goInventorySelectDialog&id='+itemId;
	if(stockId){
		url += "&stockID="+stockId;
	}
	var width = 800;
	var height = 500;
	var title = "商品批号信息";
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
				var kfDate = item.kfDate;
				if(kfDate){
					kfDate = kfDate.substring(0,10);
					$("input[name='tScSlStockbillentryList[" + index + "].kfdate']").val(kfDate);
					$("input[name='tScSlStockbillentryList[" + index + "].kfdate']").trigger("change");
				}
				$("input[name='tScSlStockbillentryList[" + index + "].batchno']").val(item.batchNo);
				$("input[name='tScSlStockbillentryList[" + index + "].stockid']").val(item.stockId);
				$("input[name='tScSlStockbillentryList[" + index + "].stockName']").val(item.stockName);
				setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + index + "].stockName']"), item.stockId, item.stockName);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}


//草稿恢复后操作
function doTempRecoveryExt(){
	cc();
}

function cc(){
	$tbody = $("#add_tScSlStockbillentry_table");
	var length1 = $tbody[0].rows.length;
	for (var i = 0; i < length1; i++) {
		var isKf = $("input[name='tScSlStockbillentryList[" + i + "].isKfperiod']").val();
		var isBatch = $("input[name='tScSlStockbillentryList[" + i + "].batchManager']").val();
		var isGift = $("select[name='tScSlStockbillentryList[" + i + "].freegifts_']").val();
		if ("N" == isKf || "" == isKf) {
			$("input[name='tScSlStockbillentryList[" + i + "].kfdate']").val("");
			$("input[name='tScSlStockbillentryList[" + i + "].kfdate']").removeAttr("onclick");
			$("input[name='tScSlStockbillentryList[" + i + "].kfdate']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].kfdate']").css("background-color", "rgb(226,226,226)");
		}
		if ("N" == isBatch || "" == isBatch) {
			$("input[name='tScSlStockbillentryList[" + i + "].batchno']").val("");
			$("input[name='tScSlStockbillentryList[" + i + "].batchno']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].batchno']").css("background-color", "rgb(226,226,226)");
			$("input[name='tScSlStockbillentryList[" + i + "].batchno']").removeAttr("datatype");
		} else {
			$("input[name='tScSlStockbillentryList[" + i + "].batchno']").attr("onkeypress", "keyDownInfo('" + i + "','batchNo')");
		}
		debugger;
		if(isGift == "1"){
			//单价
			$("input[name='tScSlStockbillentryList[" + i + "].taxPriceEx']").val(0);
			$("input[name='tScSlStockbillentryList[" + i + "].taxPriceEx']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
			$("input[name='tScSlStockbillentryList[" + i + "].taxPriceEx']").trigger("change");
			//金额
			$("input[name='tScSlStockbillentryList[" + i + "].taxAmountEx']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
			//折扣率
			$("input[name='tScSlStockbillentryList[" + i + "].discountRate']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
			//折后金额
			$("input[name='tScSlStockbillentryList[" + i + "].inTaxAmount']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
			//税率
			$("input[name='tScSlStockbillentryList[" + i + "].taxRate']").attr("readonly", "readonly");
			$("input[name='tScSlStockbillentryList[" + i + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
		}
		var kfDateType = $("input[name='tScSlStockbillentryList[" + i + "].kfdatetype']").val();
		$("select[name='tScSlStockbillentryList[" + i + "].kfdatetype_']").val(kfDateType);
		//$("input[name='tScSlStockbillentryList[" + i + "].classIDSrc']").val("");
		//$("input[name='tScSlStockbillentryList[" + i + "].idSrc']").val("");
		//$("input[name='tScSlStockbillentryList[" + i + "].entryIdSrc']").val("");
		//$("input[name='tScSlStockbillentryList[" + i + "].idOrder']").val("");
		//$("input[name='tScSlStockbillentryList[" + i + "].entryIdOrder']").val("");
		//$("input[name='tScSlStockbillentryList[" + i + "].billNoSrc']").val("");
		//$("input[name='tScSlStockbillentryList[" + i + "].billNoOrder']").val("");
	}
}


function changeTypeValue(kfDateType){
	if(kfDateType) {
		if (kfDateType.indexOf("天")) {
			kfDateType = "0003";
		} else if (kfDateType.indexOf("月")){
			kfDateType = "0002";
		} else if (kfDateType.indexOf("年")){
			kfDateType = "0001";
		}
	}
	return kfDateType;
}