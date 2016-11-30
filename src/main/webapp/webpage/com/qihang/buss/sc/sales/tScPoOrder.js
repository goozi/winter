

//初始化下标
function resetTrNum(tableId,index,isAdd) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			var comboboName = $this.attr("comboname");
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
				/*if(name.indexOf(".qty") > -1){
					$this.attr("onchange","setAllQty("+i+")");
				}else if(name.indexOf("discountRate") > -1){
					$this.attr("onchange","ondisRateChange("+i+")");
				}else if(name.indexOf("coefficient") > -1){
					$this.attr("onchange","setBasicQty("+i+")");
					$this.css("background-color","white");
				}else if(name.indexOf("taxRate") > -1){
					$this.attr("onchange","onRateChange("+i+")");
				}else if(name.indexOf("taxPriceEx") > -1){
					$this.attr("onchange","onPriceChange("+i+")");
				}else if(name.indexOf("taxAmountEx") > -1){
					$this.attr("onchange","onAmountChange("+i+")");
				}else if(name.indexOf("inTaxAmount") > -1){
					$this.attr("onchange","onTaxAmountChange("+i+")");
				}else if(name.indexOf("basicQty") > -1){
					$this.css("background-color","white");
				}else if(name.indexOf("taxPrice") > -1 || name.indexOf("taxAmount") > -1 || name.indexOf("stockQty") > -1 ||
				name.indexOf("classIDSrc") > -1 || name.indexOf("billNoSrc") > -1 || name.indexOf("itemName") > -1 ||
				name.indexOf("model") > -1 || name.indexOf("barCode") > -1){
					$this.css("background-color","white");
				}else*/
				if(name.indexOf("stockName") > -1){
					var stockId = $("input[name='tPoOrderentryList["+i+"].stockId']").val();
					var stockName = $("input[name='tPoOrderentryList["+i+"].stockName']").val();
					setValOldIdAnOldName($("input[name='tPoOrderentryList["+i+"].stockName']"),stockId,stockName);
				}else if(name.indexOf("freeGifts") > -1){
					$this.attr("onchange","setPrice("+i+")");
				}else if(name.indexOf("inTaxAmount") > -1){
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
			if (onclick_str != null) {
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {
					if(name.indexOf("jhDate") < 0) {
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
					var s = blur_str.indexOf("(");
					var e = blur_str.indexOf(",");
					var new_blur = blur_str.substring(s + 1, e);
					$this.attr("onblur", blur_str.replace(new_blur, i));
				}
			}
		});
		setFunctionInfo(i);
		//if(i==rowIndex) {
		//	$("#unitId\\[" + i + "\\]").combobox({
		//		valueField: "id",
		//		textField: "text",
		//		panelHeight: "auto",
		//		editable: false
		//	})
		//}
		$("input[name='tPoOrderentryList["+i+"].findex']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});

	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1) && isAdd) {
			$("#unitId\\[" + i + "\\]").combobox({
				width:84,
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
	setDefaultDate();
	setDefaultOldInfo();
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

	$("#itemName").blur(function(){
		checkInput($("#itemId"), $("#itemName"));
	})

	$("#stockName").blur(function(){
		checkInput($("#stockId"), $("#stockName"));
	})
	$("#deptName").blur(function(){
		checkInput($("#deptId"), $("#deptName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empId"), $("#empName"));
	})
	var checkState = $("#checkState").val();
	var load = $("#load").val();
	if(checkState == 2 && "fcopy" != load){
		$("#date").attr("readonly","readonly");
		$("#date").attr("onclick","");
		$("#itemName").attr("readonly","readonly");
		$("#empName").attr("readonly","readonly");
		$("#deptName").attr("readonly","readonly");
		$("#stockName").attr("readonly","readonly");
		$("#contact").attr("readonly","readonly");
		$("#mobilePhone").attr("readonly","readonly");
		$("#phone").attr("readonly","readonly");
		$("#fax").attr("readonly","readonly");
		$("#address").attr("readonly","readonly");
		$("#rebateAmount").attr("readonly","readonly");
		$("#explanation").attr("readonly","readonly");
	}

	//var parent = /^([0-9]\.\d*[1-9]\d*)$/;
	//var a = 0.0001;
	//if(parent.exec(a)){
	//	alert("aaaa");
	//}else {
	//	alert("bbb");
	//}
})
//设置旧数据
function setDefaultOldInfo(){
	var itemId = $("#itemId").val();
	var itemName = $("#itemName").val();
	setValOldIdAnOldName($("#itemName"),itemId,itemName);

	var empId = $("#empId").val();
	var empName = $("#empName").val();
	setValOldIdAnOldName($("#empName"),empId,empName);

	var stockId = $("#stockId").val();
	var stockName = $("#stockName").val();
	setValOldIdAnOldName($("#stockName"),stockId,stockName);

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
	var billDate = $("#date").val();
	var id = $("#id").val();
	if(!id){
		$("input[name='date']").val(year+"-"+month+"-"+date);
		$("input[name='tPoOrderentryList[#index#].jhDate']").val(year+"-"+month+"-"+date);
	}else{
		var dateValue = $("input[name='date']").val();
		dateValue = new Date(dateValue);
		var year = dateValue.getFullYear();
		var month = (dateValue.getMonth()+1) < 10 ? "0"+(dateValue.getMonth()+1) : (dateValue.getMonth()+1);
		var date = dateValue.getDate() < 10 ? "0"+dateValue.getDate() : dateValue.getDate();
		dateValue = year+"-"+month+"-"+date;
		$("input[name='tPoOrderentryList[#index#].jhDate']").val(dateValue);
	}
}

//赠品设置单价为0；
function setPrice(index){
	var selected = document.getElementsByName("tPoOrderentryList["+index+"].freeGifts");
	var i = selected[0].selectedIndex;
	var value = selected[0].options[i].value;
	if(value == 1){
		$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val(0);//单价
		$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").trigger("change");
		//单价只读
		$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").attr("readonly","readonly");
		$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
		//金额只读
		$("input[name='tPoOrderentryList["+index+"].taxAmountEx']").attr("readonly","readonly");
		$("input[name='tPoOrderentryList["+index+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
		//折扣率只读
		$("input[name='tPoOrderentryList["+index+"].discountRate']").attr("readonly","readonly");
		$("input[name='tPoOrderentryList["+index+"].discountRate']").css("background-color","rgb(226,226,226)");
		//折后金额
		$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").attr("readonly","readonly");
		$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
		//税率
		$("input[name='tPoOrderentryList["+index+"].taxRate']").attr("readonly","readonly");
		$("input[name='tPoOrderentryList["+index+"].taxRate']").css("background-color","rgb(226,226,226)");
		//onPriceChange(index);
	}else{
		//单价只读
		$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").removeAttr("readonly");
		$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").css("background-color","white");
		//金额只读
		$("input[name='tPoOrderentryList["+index+"].taxAmountEx']").removeAttr("readonly");
		$("input[name='tPoOrderentryList["+index+"].taxAmountEx']").css("background-color","white");
		//折扣率只读
		$("input[name='tPoOrderentryList["+index+"].discountRate']").removeAttr("readonly");
		$("input[name='tPoOrderentryList["+index+"].discountRate']").css("background-color","white");
		//折后金额
		$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").removeAttr("readonly");
		$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").css("background-color","white");
		//税率
		$("input[name='tPoOrderentryList["+index+"].taxRate']").removeAttr("readonly");
		$("input[name='tPoOrderentryList["+index+"].taxRate']").css("background-color","white");
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

//选择供应商
function selectItemNameDialog() {
	var itemName = $("#itemName").val();
	var url = 'tScSupplierController.do?goSelectDialog&name=' + itemName;
	var width = 800;
	var height = 500;
	var title = "供应商";
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
				$("#contact").val(item.contact);
				$("#mobilePhone").val(item.mobilePhone);
				$("#phone").val(item.phone);
				$("#fax").val(item.fax);
				$("#address").val(item.address);
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
//选择仓库
function selectStockDialog(index) {
	var itemName = $("#stockName").val();
	if(index || 0 == index){
		itemName = $("input[name='tPoOrderentryList["+index+"].stockName']").val();
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
					$("#stockId").val(item.id);
					$tbody = $("#tPoOrderentry_table");
					var length = $tbody[0].rows.length;
					$("input[name='tPoOrderentryList[#index#].stockId']").val(item.id);
					$("input[name='tPoOrderentryList[#index#].stockName']").val(item.name);
					for (var i = 0; i < length; i++) {
						$("input[name='tPoOrderentryList[" + i + "].stockId']").val(item.id);
						$("input[name='tPoOrderentryList[" + i + "].stockName']").val(item.name);
						setValOldIdAnOldName($("input[name='tPoOrderentryList[" + i + "].stockName']"), item.id, item.name);
					}
					setValOldIdAnOldName($("#stockName"), item.id, item.name);
				}else{
					$("input[name='tPoOrderentryList[" + index + "].stockId']").val(item.id);
					$("input[name='tPoOrderentryList[" + index + "].stockName']").val(item.name);
					setValOldIdAnOldName($("input[name='tPoOrderentryList[" + index + "].stockName']"), item.id, item.name);
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


/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

/**
 * 数量改变时执行操作
 */
function setAllQty(index){
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	var price = $("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val();//单价
	var coefficient = $("input[name='tPoOrderentryList["+index+"].coefficient']").val();//转换率
	var discountRate = $("input[name='tPoOrderentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tPoOrderentryList["+index+"].taxRate']").val();//税率
	var discountPrice = $("input[name='tPoOrderentryList["+index+"].discountPrice']").val();//不含税折后单价
	var disPrice = $("input[name='tPoOrderentryList["+index+"].price']").val();//不含税单价
	var secCoefficient = $("input[name='tPoOrderentryList["+index+"].secCoefficient']").val();
	if(!qty){
		qty = 0;
	}
	if(!discountRate){
		discountRate = 100;
	}
	if(!taxRate){
		taxRate = 0;
	}
	if(!coefficient){
		coefficient = 0;
	}
	if(!price){
		price = 0;
	}
	if(!discountPrice){
		discountPrice = 0;
	}
	if(!disPrice){
		disPrice = 0;
	}
	if(!secCoefficient){
		secCoefficient = 0;
	}
	var basicQty = qty*coefficient;
	var intaxAmount = price*discountRate/100*qty;
	var taxAmountEx = price*qty;
	var taxAmount = intaxAmount - (intaxAmount/(1+(taxRate/100)));
	var amount = disPrice * qty;
	var discountAmount = discountPrice * qty;
	var secQty = 0;
	if(secCoefficient > 0) {
		secQty = (qty * coefficient) / secCoefficient;
	}
	var taxPrice = 0;
	if(qty>0){
		taxPrice = intaxAmount/qty;
	}
	$("input[name='tPoOrderentryList["+index+"].basicQty']").val(basicQty.toFixed(2));//基础数量
	$("input[name='tPoOrderentryList["+index+"].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
	$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").val(intaxAmount.toFixed(2));//折后金额
	$("input[name='tPoOrderentryList["+index+"].taxPrice']").val(taxPrice.toFixed(2));//折后单价
	$("input[name='tPoOrderentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
	$("input[name='tPoOrderentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tPoOrderentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tPoOrderentryList["+index+"].secQty']").val(secQty.toFixed(2));//不含税金额
	setAllAmount();
}

//设置基本数量
function setBasicQty(index){
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	var coefficient = $("input[name='tPoOrderentryList["+index+"].coefficient']").val();//换算率
	if(!qty){
		qty = 0;
	}
	if(!coefficient){
		coefficient = 0;
	}
	var basicQty = qty*coefficient;
	$("input[name='tPoOrderentryList["+index+"].basicQty']").val(basicQty.toFixed(2));//基础数量
}

//税率发生改变时执行操作
function onRateChange(index){
	var priceEx = $("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val();//单价
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tPoOrderentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tPoOrderentryList["+index+"].taxRate']").val();//税率
	if(!priceEx){
		priceEx = 0;
	}
	if(!qty){
		qty = 0;
	}
	if(!taxRate){
		taxRate = 0;
	}
	if(!discountRate){
		discountRate = 100;
	}
	var price = priceEx / (1 + taxRate / 100);
	var amount = price * qty;
	var discountPrice = price * discountRate / 100;
	var discountAmount = discountPrice * qty;
	var intaxAmount = priceEx * discountRate / 100 * qty;
	var taxAmount = intaxAmount - (intaxAmount/(1+(taxRate/100)));
	$("input[name='tPoOrderentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tPoOrderentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tPoOrderentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tPoOrderentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tPoOrderentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
}

//折扣率改变执行操作
function ondisRateChange(index){
	var priceEx = $("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val();//单价
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tPoOrderentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tPoOrderentryList["+index+"].taxRate']").val();//税率
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
	var disprice = priceEx*discountRate/100;
	var disAmount = disprice*qty;
	var taxAmount = disAmount - (disAmount /(1+(taxRate/100)));

	var discountPrice = price * discountRate / 100;
	var discountAmount = discountPrice * qty;
	$("input[name='tPoOrderentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tPoOrderentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
	$("input[name='tPoOrderentryList["+index+"].taxPrice']").val(disprice.toFixed(2));//折后单价
	$("input[name='tPoOrderentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额

	$("input[name='tPoOrderentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tPoOrderentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	setAllAmount();
}

//单价改变时执行操作
function onPriceChange(index){
	var priceEx = $("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val();//单价
	if(priceEx > 0){
		$("select[name='tPoOrderentryList["+index+"].freeGifts']").val(0);
	}
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tPoOrderentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tPoOrderentryList["+index+"].taxRate']").val();//税率
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
	$("input[name='tPoOrderentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tPoOrderentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tPoOrderentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tPoOrderentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tPoOrderentryList["+index+"].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
	$("input[name='tPoOrderentryList["+index+"].taxPrice']").val(disprice.toFixed(2));//折后单价
	$("input[name='tPoOrderentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
	$("input[name='tPoOrderentryList["+index+"].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
	setAllAmount();
}

//金额改变时执行操作
function onAmountChange(index){
	var amount = $("input[name='tPoOrderentryList["+index+"].taxAmountEx']").val();//金额
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	if(!qty){
		qty = 0;
	}
	if(!amount){
		amount = 0;
	}
	var priceEx = 0;
	if(qty > 0) {
		priceEx = amount / qty;
	}
	$("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val(priceEx.toFixed(2));//单价
	onPriceChange(index);
}

//折后金额改变时进行操作
function onTaxAmountChange(index){
	var taxAmount = $("input[name='tPoOrderentryList["+index+"].inTaxAmount']").val();//折后金额
	var qty = $("input[name='tPoOrderentryList["+index+"].qty']").val();//数量
	var priceEx = $("input[name='tPoOrderentryList["+index+"].taxPriceEx']").val();//单价
	if(!taxAmount){
		taxAmount = 0;
	}
	if(!qty){
		qty = 0;
	}
	if(!priceEx){
		priceEx = 0;
	}

	var discountRate = 0;
	if(priceEx > 0 && qty > 0) {
		discountRate = (taxAmount / qty) / priceEx * 100;
	}

	$("input[name='tPoOrderentryList["+index+"].discountRate']").val(discountRate.toFixed(2));//折扣率
	ondisRateChange(index);
}

//汇总折扣金额
function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody = $("#tPoOrderentry_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tPoOrderentryList[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tPoOrderentryList[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tPoOrderentryList[" + i + "].qty']").val();//数量
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
	var rebateAmount = $("#rebateAmount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
		allAmount = allAmount.toFixed(CFG_MONEY);
		$("#amount").val(allAmount);
		allAmount = allAmount - rebateAmount;
		allAmount = allAmount.toFixed(CFG_MONEY);
		$("#allAmount").val(allAmount);
}

//更改优惠金额时执行操作
function changeAllAmount(){
	var rebateAmount = $("#rebateAmount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
	var allAmount = $("#amount").val();
	if(allAmount){
		allAmount = parseFloat(allAmount) - parseFloat(rebateAmount);
		$("#allAmount").val(allAmount);
	}
}


//保存前校验单价是否为0
var isCheck = true;
var isCheck2 = true;
function checkPriceIsZero(item){
	var url = "tPoOrderController.do?checkIsPriceZero";
	if(isCheck || isCheck2) {
	   var isAllowPriceZero = false;
		var CFG_PRICE_ZERO = $("#CFG_PRICE_ZERO").val();
		if("1" == CFG_PRICE_ZERO){
			isAllowPriceZero = true;
		}
	  // if(!isAllowPriceZero){
			var $tbody = $("#tPoOrderentry_table");
			var length = $tbody[0].rows.length;
			var itemName = "";
		    var cgName = "";
			for(var i=0 ; i<length ; i++){
				var price = $("input[name='tPoOrderentryList["+i+"].taxPriceEx']").val();
				var cgLimitPrice  = $("input[name='tPoOrderentryList["+i+"].cgLimitPrice']").val();
				if(!cgLimitPrice){
					cgLimitPrice = 0;
				}
				if(!price || price == 0){
					itemName += $("input[name='tPoOrderentryList["+i+"].itemName']").val()+",";
				}
				if(cgLimitPrice && parseFloat(cgLimitPrice) > 0 ) {
					if (parseFloat(price) > parseFloat(cgLimitPrice)){
						cgName += $("input[name='tPoOrderentryList["+i+"].itemName']").val()+",";
					}
				}
			}
			if (itemName.length > 0 || cgName.length > 0) {
				if(cgName.length > 0 && isCheck2){
					$.dialog.confirm("商品[" + cgName + "]的单价高于采购限价，是否继续保存？", function () {
						isCheck2 = false;
						$("#formobj").submit();
					}, function () {
						//
					});
				}else {
					if(!isAllowPriceZero) {
						parent.$.dialog.confirm("商品[" + itemName + "]的单价为0，是否继续保存？", function () {
							isCheck = false;
							isCheck2 = false;
							$("#formobj").submit();
						}, function () {
							isCheck2 = true;
						});
					}else{
						isCheck = false;
						isCheck2 = false;
						$("#formobj").submit();
					}
				}
			}else {
				isCheck = false;
				isCheck2 = false;
				$("#formobj").submit();
			}
		//}else{
		//	isCheck = false;
		//	$("#formobj").submit();
		//}
		return false;
	}else{
		return true;
	}

}

//表格回车事件
function orderListStockKeyPress(rowIndex,evt) {
	evt = (evt)?evt:((window.event)?window.event:"");
	var key =evt.keyCode?evt.keyCode:evt.which;
	if (key == 13) {
		selectStockDialog(rowIndex);
	}
}

//表格校验事件
function orderListStockBlur(rowIndex,id,name){
	checkInput( $('input[name="tPoOrderentryList['+rowIndex+'].'+id+'"]'),$('input[name="tPoOrderentryList['+rowIndex+'].'+name+'"]'));
}

//单位选择框
function selectUnitDialog(index){
	var unitName = $("input[name='tPoOrderentryList["+index+"].unitName']").val();
	var url = 'tScMeasureunitController.do?goSelectDialog&name=' + unitName;
	var width = 800;
	var height = 500;
	var title = "单位";
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
				$("input[name='tPoOrderentryList["+index+"].unitName']").val(item.name);
				$("input[name='tPoOrderentryList["+index+"].unitId']").val(item.id);
				setValOldIdAnOldName($("input[name='tPoOrderentryList["+index+"].unitName']"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}


//商品选择框
var rowIndex = 0;
function selectIcitemDialog(index){
	var itemNo = $("input[name='tPoOrderentryList["+index+"].itemNo']").val();
	var url = 'tScIcitemController.do?goSelectDialog&number=' + itemNo;
	var width = 800;
	var height = 500;
	var title = "商品";
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
				var nextTrLength =  $('input[name="tPoOrderentryList['+index+'].findex"]').parent().nextAll().length;//判断后面的行数
				var newLength = itemLength-nextTrLength-1
				var ind = index;
				for(var j=0 ; j<newLength ; j++){
					clickAddTPoOrderentryBtn(ind);
					ind++;
				}
				var sonId = $("#sonId").val();
				$.each(itemList,function(i,item){

					//var item = itemList[i];
					var rowindex = parseInt(index)+i;
					var id = item.id;//id
					var number = item.number;//编码
					var name = item.name;//名称
					var model = item.model;//规格
					var taxRate = item.taxRateIn;//进行税率
					var stockSonId = item.stockSonId;//商品默认仓库分支机构
					var url = "tScIcitemController.do?getItemInfo&id=" + id+"&rowIndex="+rowindex+"&tranType=51";
					$.ajax({
						url: url,
						dataType: 'json',
						cache: false,
						success: function (data) {
							var attr = data.attributes;
							var barCode = attr.barCode;
							var unitId = attr.unitId;
							var cofficient = attr.cofficient;
							var secUnitId = attr.secUnitId;
							var secCoefficient = attr.secCoefficient;
							var basicUnitId = attr.basicUnitId;
							var itemId = attr.id;
							var rowIndex = attr.rowIndex;
							var cgLimitPrice = attr.cgLimitPrice;
							var stockId = attr.stockId;//默认仓库id
							var stockName = attr.stockName;//默认仓库名称
							var checkId = $("input[name='tPoOrderentryList[" + rowIndex + "].stockId']").val();
							if(!checkId) {
								if(sonId == stockSonId) {
									$("input[name='tPoOrderentryList[" + rowIndex + "].stockId']").val(stockId);
									$("input[name='tPoOrderentryList[" + rowIndex + "].stockName']").val(stockName);
								}
							}
							setValOldIdAnOldName($("input[name='tPoOrderentryList[" + rowIndex + "].stockName']"),stockId,stockName)
							$("input[name='tPoOrderentryList[" + rowIndex + "].taxRate']").val(taxRate);
							$("input[name='tPoOrderentryList[" + rowIndex + "].taxRate']").trigger("change");
							$("input[name='tPoOrderentryList[" + rowIndex + "].itemNo']").val(number);
							setValOldIdAnOldName($("input[name='tPoOrderentryList[" + rowIndex + "].itemNo']"),id,number);
							$("input[name='tPoOrderentryList[" + rowIndex + "].itemId']").val(id);
							$("input[name='tPoOrderentryList[" + rowIndex + "].model']").val(model);
							$("input[name='tPoOrderentryList[" + rowIndex + "].itemName']").val(name);
							$("input[name='tPoOrderentryList[" + rowIndex + "].barCode']").val(barCode);
							$("input[name='tPoOrderentryList[" + rowIndex + "].coefficient']").val(cofficient);
							$("input[name='tPoOrderentryList[" + rowIndex + "].coefficient']").trigger("blur");
							$("input[name='tPoOrderentryList[" + rowIndex + "].secUnitId']").val(secUnitId);
							$("input[name='tPoOrderentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
							$("input[name='tPoOrderentryList[" + rowIndex + "].secCoefficient']").trigger("change");
							$("input[name='tPoOrderentryList[" + rowIndex + "].basicUnitId']").val(basicUnitId);
							$("input[name='tPoOrderentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);
							//var className = $("input[name='tPoOrderentryList["+index+"].unitId']")[0].className;
							//if(className == "combo-value"){
							//	$("input[name='tPoOrderentryList["+index+"].unitId']").combobox("reload");
							//}

							/*var aa = $("input[name='tPoOrderentryList\\[" + index + "\\]\\.unitId']:first").combobox({
							 valueField: "id",
							 textField: "text",
							 panelHeight: "auto",
							 editable: false,
							 //value: unitId,
							 url: "tScIcitemController.do?loadItemUnit&id=" + id,
							 //onChange: function (newV, oldV) {
							 //	if (oldV != newV) {
							 //		var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + id + "&unitId=" + newV;
							 //		$.ajax({
							 //			url: changeUrl,
							 //			dataType: 'json',
							 //			cache: false,
							 //			success: function (data) {
							 //				var attributes = data.attributes;
							 //				cofficient = attributes.coefficient;
							 //				barCode = attributes.barCode;
							 //				$("input[name='tPoOrderentryList[" + index + "].barCode']").val(barCode);
							 //				$("input[name='tPoOrderentryList[" + index + "].coefficient']").val(cofficient);
							 //			}
							 //		});
							 //	}
							 //}
							 });*/
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
												var cofficient = attributes.coefficient;
												var barCode = attributes.barCode;
												var cgLimitPrice = attributes.cgLimitPrice;
												$("input[name='tPoOrderentryList[" + rowIndex + "].barCode']").val(barCode);
												$("input[name='tPoOrderentryList[" + rowIndex + "].coefficient']").val(cofficient);
												$("input[name='tPoOrderentryList[" + rowIndex + "].coefficient']").trigger("change");
												$("input[name='tPoOrderentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);
											}
										});
									}
								}
							})
							$("#unitId\\[" + rowIndex+"\\]").combobox("setValue", unitId);
							//$("input[name='tPoOrderentryList["+index+"].unitId']").combobox("setValue",unitId);
							setValOldIdAnOldName($("input[name='tPoOrderentryList[" + rowIndex + "].itemNo']"), itemId, number);
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

function editQtyChange(index){
	var oldValue = $("input[name='tPoOrderentryList["+index+"].qty']").attr("oldvalue");
	var newValue = $("input[name='tPoOrderentryList["+index+"].qty']").val();
	if(parseFloat(oldValue) > parseFloat(newValue)){
		tip("数量不可小于当前数量");
		$("input[name='tPoOrderentryList["+index+"].qty']").val(oldValue);
		return;
	}
	setAllQty(index);
}

function checkBillDate(){
	var checkDate = $("#checkDate").val();
	checkDate = new Date(checkDate).getTime();
	var billDate = $("#date").val();
	billDate = new Date(billDate).getTime();
	if(parseInt(billDate) <= parseInt(checkDate)){
		tip("单据日期不可小于期间日期");
		setDefaultDate();
	}
}


//审核前
function checkAudit(){
	return false;
}

//复制后执行方法
function doClearExt(){
	setTimeout("clearTableInfo();",200);
}

//清楚子表数据
function clearTableInfo(){
	$tbody = $("#add_tPoOrderentry_table");
	var length1 = $tbody[0].rows.length;
	for(var i=0 ; i<length1 ; i++){
		$("input[name='tPoOrderentryList["+i+"].stockQty']").val("");
	}
}


function checkUnAudit(){
	var checkDate = $("#checkDate").val();
	var date = $("#date").val();
	var checkTime = new Date(checkDate).getTime();
	var dateTime = new Date(date).getTime();
	if(dateTime < checkTime){
		return "该数据不在账套期间内，不可反审核";
	} else {
		return false;
	}
}