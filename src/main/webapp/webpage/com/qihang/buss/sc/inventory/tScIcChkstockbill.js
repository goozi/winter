

//初始化下标
function resetTrNum(tableId,index) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),onchange_str=$this.attr('onchange'),str_press = $this.attr('onkeypress'), val = $this.val();
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
			if(onchange_str != null){
				if (onchange_str.indexOf("#index#") >= 0) {
					$this.attr("onchange", onchange_str.replace(/#index#/g, i));
				} else {
					var s = onchange_str.indexOf("(");
					var e = onchange_str.indexOf(")");
					var new_char = onchange_str.substring(s + 1, e);
					$this.attr("onchange", onchange_str.replace(new_char, i));
				}
			}
		});
		//setFunctionInfo(i);
		//if(i==rowIndex) {
		//	$("#unitId\\[" + i + "\\]").combobox({
		//		valueField: "id",
		//		textField: "text",
		//		panelHeight: "auto",
		//		editable: false
		//	})
		//}
		$("input[name='tScIcChkstockbillentryList["+i+"].findex']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});

	$tbody.find('>tr').each(function(i){
		debugger;
		if(i == (parseInt(index)+1)) {
			$("#unitId\\[" + i + "\\]").combobox({
				width:54,
				disabled:true,
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			})
		}

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
	setDefaultOldInfo();
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

	$("#stockName").blur(function(){
		checkInput($("#stockId"), $("#stockName"));
	})
	$("#deptName").blur(function(){
		checkInput($("#deptId"), $("#deptName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empId"), $("#empName"));
	})

	var isAuto = $("#isAuto").val();
	if("1" == isAuto){
		$("#stockName").attr("readonly","readonly");
		$("#stockName").css("background-color","rgb(226,226,226)");
		$("#deptName").attr("readonly","readonly");
		$("#deptName").css("background-color","rgb(226,226,226)");
		$("#empName").attr("readonly","readonly");
		$("#empName").css("background-color","rgb(226,226,226)");
	}
})
//设置旧数据
function setDefaultOldInfo(){

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



//赠品设置单价为0；
function setPrice(index){
	var selected = document.getElementsByName("tScIcChkstockbillentryList["+index+"].freeGifts");
	var i = selected[0].selectedIndex;
	var value = selected[0].options[i].value;
	if(value == 1){
		$("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").val(0);//单价
		$("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").trigger("change");
		//onPriceChange(index);
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
				$("#stockName").val(item.name);
				$("#stockId").val(item.id);
				$tbody = $("#tScIcChkstockbillentry_table");
				var length = $tbody[0].rows.length;
				$("input[name='tScIcChkstockbillentryList[#index#].stockId']").val(item.id);
				for (var i = 0; i < length; i++) {
					$("input[name='tScIcChkstockbillentryList[" + i + "].stockId']").val(item.id);
				}
				setValOldIdAnOldName($("#stockName"), item.id, item.name);
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

var CFG_NUMBER = $("#CFG_NUMBER").val();//数量精确度
var CFG_MONEY = $("#CFG_MONEY").val();//金额精确度
/**
 * 更改盘点箱数
 */
function changeCheckQty(index){
	var basicQty = $("input[name='tScIcChkstockbillentryList["+index+"].chkBasicQty']").val();//盘点数量
	if(!basicQty){
		basicQty = 0;
	}
	basicQty = parseFloat(basicQty).toFixed(CFG_NUMBER);
	$("input[name='tScIcChkstockbillentryList["+index+"].chkBasicQty']").val(basicQty);
	var price = $("input[name='tScIcChkstockbillentryList["+index+"].costPrice']").val();//成本单价
	if(!price){
		price = 0 ;
	}
	var coefficient = $("input[name='tScIcChkstockbillentryList["+index+"].coefficient']").val();//转换率
	if(!coefficient){
		coefficient = 1;
	}
	var qty = Math.floor(parseFloat(basicQty)/parseFloat(coefficient)).toFixed(CFG_NUMBER);//盘点箱数
	var smallQty = ((parseFloat(basicQty)-qty)/parseFloat(coefficient)).toFixed(CFG_NUMBER);//盘点散数
	var amount = (parseFloat(price)*parseFloat(basicQty)).toFixed(CFG_MONEY);//盘点金额
	$("input[name='tScIcChkstockbillentryList["+index+"].chkQty").val(qty);
	$("input[name='tScIcChkstockbillentryList["+index+"].chkQty").trigger("input");
	$("input[name='tScIcChkstockbillentryList["+index+"].chkSmallQty").val(smallQty);
	$("input[name='tScIcChkstockbillentryList["+index+"].chkSmallQty").trigger("input");
	$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").val(amount);
	$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").trigger("input");

	var oldQty = $("input[name='tScIcChkstockbillentryList["+index+"].basicQty']").val();//账面数量
	var oldAmount = $("input[name='tScIcChkstockbillentryList["+index+"].amount']").val();//账面金额
	if(!oldQty){
		oldQty = 0;
	}
	if(!oldAmount){
		oldAmount = 0;
	}
	var diffQty = (parseFloat(basicQty) - parseFloat(oldQty)).toFixed(CFG_NUMBER);
	var diffAmount = (parseFloat(diffQty) * parseFloat(price)).toFixed(CFG_MONEY);
	$("input[name='tScIcChkstockbillentryList["+index+"].diffQty").val(diffQty);//差异数量
	$("input[name='tScIcChkstockbillentryList["+index+"].diffQty").trigger("input");
	$("input[name='tScIcChkstockbillentryList["+index+"].diffAmount").val(diffAmount);//差异金额
	$("input[name='tScIcChkstockbillentryList["+index+"].diffAmount").trigger("input");
	if(diffQty == 0){
		$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").val(oldAmount);//盘点金额
		$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").trigger("input");
	}
}

function changeQty(index){
	var price = $("input[name='tScIcChkstockbillentryList["+index+"].costPrice']").val();//成本单价
	if(!price){
		price = 0;
	}
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].chkQty']").val();//盘点箱数
	if(!qty){
		qty = 0;
	}
	//盘点箱数小数位控制
	$("input[name='tScIcChkstockbillentryList["+index+"].chkQty']").val(parseFloat(qty).toFixed(CFG_NUMBER));
	var smallQty = $("input[name='tScIcChkstockbillentryList["+index+"].chkSmallQty']").val();//盘点散数
	if(!smallQty){
		smallQty = 0;
	}
	//盘点散数小数位控制
	$("input[name='tScIcChkstockbillentryList["+index+"].chkSmallQty']").val(parseFloat(smallQty).toFixed(CFG_NUMBER));
	var coefficient = $("input[name='tScIcChkstockbillentryList["+index+"].coefficient']").val();//转换率
	if(!coefficient){
		coefficient = 1;
	}
	var basicQty = ((parseFloat(qty) * parseFloat(coefficient)) + parseFloat(smallQty)).toFixed(CFG_NUMBER);
	var amount = (parseFloat(price)*parseFloat(basicQty)).toFixed(CFG_MONEY);//盘点金额

	$("input[name='tScIcChkstockbillentryList["+index+"].chkBasicQty']").val(basicQty);//盘点数量
	$("input[name='tScIcChkstockbillentryList["+index+"].chkBasicQty']").trigger("input");
	$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").val(amount);//盘点金额
	$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount']").trigger("input");

	var oldQty = $("input[name='tScIcChkstockbillentryList["+index+"].basicQty']").val();//账面数量
	var oldAmount = $("input[name='tScIcChkstockbillentryList["+index+"].amount']").val();//账面金额
	var diffQty = (parseFloat(basicQty)-parseFloat(oldQty)).toFixed(CFG_NUMBER);
	var diffAmount = (parseFloat(diffQty) * parseFloat(price)).toFixed(CFG_MONEY);
	$("input[name='tScIcChkstockbillentryList["+index+"].diffQty").val(diffQty);//差异数量
	$("input[name='tScIcChkstockbillentryList["+index+"].diffQty").trigger("input");
	$("input[name='tScIcChkstockbillentryList["+index+"].diffAmount").val(diffAmount);//差异金额
	$("input[name='tScIcChkstockbillentryList["+index+"].diffAmount").trigger("input");
	if(diffQty == 0){
		$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").val(oldAmount);//盘点金额
		$("input[name='tScIcChkstockbillentryList["+index+"].chkAmount").trigger("input");
	}
}


//设置基本数量
function setBasicQty(index){
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();//数量
	var coefficient = $("input[name='tScIcChkstockbillentryList["+index+"].coefficient']").val();//换算率
	if(!qty){
		qty = 0;
	}
	if(!coefficient){
		coefficient = 0;
	}
	var basicQty = qty*coefficient;
	$("input[name='tScIcChkstockbillentryList["+index+"].basicQty']").val(basicQty.toFixed(2));//基础数量
}

//税率发生改变时执行操作
function onRateChange(index){
	var priceEx = $("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").val();//单价
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tScIcChkstockbillentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tScIcChkstockbillentryList["+index+"].taxRate']").val();//税率
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
	$("input[name='tScIcChkstockbillentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tScIcChkstockbillentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tScIcChkstockbillentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tScIcChkstockbillentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tScIcChkstockbillentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
}

//折扣率改变执行操作
function ondisRateChange(index){
	var priceEx = $("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").val();//单价
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tScIcChkstockbillentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tScIcChkstockbillentryList["+index+"].taxRate']").val();//税率
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
	$("input[name='tScIcChkstockbillentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tScIcChkstockbillentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tScIcChkstockbillentryList["+index+"].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
	$("input[name='tScIcChkstockbillentryList["+index+"].taxPrice']").val(disprice.toFixed(2));//折后单价
	$("input[name='tScIcChkstockbillentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额

	$("input[name='tScIcChkstockbillentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tScIcChkstockbillentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	setAllAmount();
}

//单价改变时执行操作
function onPriceChange(index){
	var priceEx = $("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").val();//单价
	if(priceEx > 0){
		$("select[name='tScIcChkstockbillentryList["+index+"].freeGifts']").val(0);
	}
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tScIcChkstockbillentryList["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tScIcChkstockbillentryList["+index+"].taxRate']").val();//税率
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
	$("input[name='tScIcChkstockbillentryList["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tScIcChkstockbillentryList["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tScIcChkstockbillentryList["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tScIcChkstockbillentryList["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tScIcChkstockbillentryList["+index+"].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
	$("input[name='tScIcChkstockbillentryList["+index+"].taxPrice']").val(disprice.toFixed(2));//折后单价
	$("input[name='tScIcChkstockbillentryList["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
	$("input[name='tScIcChkstockbillentryList["+index+"].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
	setAllAmount();
}

//金额改变时执行操作
function onAmountChange(index){
	var amount = $("input[name='tScIcChkstockbillentryList["+index+"].taxAmountEx']").val();//金额
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();//数量
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
	$("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").val(priceEx.toFixed(2));//单价
	onPriceChange(index);
}

//折后金额改变时进行操作
function onTaxAmountChange(index){
	var taxAmount = $("input[name='tScIcChkstockbillentryList["+index+"].inTaxAmount']").val();//折后金额
	var qty = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();//数量
	var priceEx = $("input[name='tScIcChkstockbillentryList["+index+"].taxPriceEx']").val();//单价
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

	$("input[name='tScIcChkstockbillentryList["+index+"].discountRate']").val(discountRate.toFixed(2));//折扣率
	ondisRateChange(index);
}

//汇总折扣金额
function setAllAmount(){
	$tbody = $("#tPoOrderentry_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScIcChkstockbillentryList[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScIcChkstockbillentryList[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScIcChkstockbillentryList[" + i + "].qty']").val();//数量
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
	$("#amount").val(allAmount);
	//if(allAmount > 0) {
	//$("#sumQty")[0].innerText = allQty;
	//$("#sumAmount")[0].innerText = allAmountTax;
	//$("#sumTaxAmount")[0].innerText=allAmount;
	allAmount = allAmount - rebateAmount;
	$("#allAmount").val(allAmount);
	//}
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
		// if(!isAllowPriceZero){
		var $tbody = $("#tPoOrderentry_table");
		var length = $tbody[0].rows.length;
		var itemName = "";
		var cgName = "";
		for(var i=0 ; i<length ; i++){
			var price = $("input[name='tScIcChkstockbillentryList["+i+"].taxPriceEx']").val();
			var cgLimitPrice  = $("input[name='tScIcChkstockbillentryList["+i+"].cgLimitPrice']").val();
			if(!price || price == 0){
				itemName += $("input[name='tScIcChkstockbillentryList["+i+"].itemName']").val()+",";
			}
			if(cgLimitPrice) {
				if (parseFloat(price) < parseFloat(cgLimitPrice)){
					cgName += $("input[name='tScIcChkstockbillentryList["+i+"].itemName']").val()+",";
				}
			}
		}
		if (itemName.length > 0 || cgName.length > 0) {
			if(cgName.length > 0 && isCheck2){
				$.dialog.confirm("商品[" + cgName + "]的单价低于采购限价，是否继续保存？", function () {
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
	checkInput( $('input[name="tScIcChkstockbillentryList['+rowIndex+'].'+id+'"]'),$('input[name="tScIcChkstockbillentryList['+rowIndex+'].'+name+'"]'));
}

//单位选择框
function selectUnitDialog(index){
	var unitName = $("input[name='tScIcChkstockbillentryList["+index+"].unitName']").val();
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
				$("input[name='tScIcChkstockbillentryList["+index+"].unitName']").val(item.name);
				$("input[name='tScIcChkstockbillentryList["+index+"].unitId']").val(item.id);
				setValOldIdAnOldName($("input[name='tScIcChkstockbillentryList["+index+"].unitName']"), item.id, item.name);
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
	var itemNo = $("input[name='tScIcChkstockbillentryList["+index+"].itemNo']").val();
	var stockId = $("#stockId").val();
	var chkType = $("#chkType").val();
	if(!stockId){
		tip("请选择仓库信息");
		return;
	}
	if(!chkType){
		tip("请选择盘点类型");
		return;
	}
	var url = 'tScIcitemController.do?goInventorySelectDialog&number=' + itemNo+"&stockID="+stockId;
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
				var nextTrLength = $('input[name="tScIcChkstockbillentryList[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
				var newLength = itemLength - nextTrLength - 1
				for (var j = 0; j < newLength; j++) {
					clickAddTScIcChkstockbillentryBtn(index);
				}
				$("#stockName").attr("readonly","readonly");
				$.each(itemList, function (i, item) {
					//var length = $("#add_tScPoStockbillentry_table").find(">tr").length;
					//if(i > 0 ){
					//	clickAddtScIcChkstockbillentryListBtn(index);
					//	index ++;
					//}
					//var item = itemList[i];
					var rowindex = parseInt(index) + i;
					var id = item.itemId;//id
					var idSrc = item.id;//库存id
					var number = item.itemNo;//编码
					var name = item.itemName;//名称
					var model = item.model;//规格
					var batchNo = item.batchNo;//批号
					var basicQty = item.basicQty;//库存基本数量
					basicQty = parseFloat(basicQty).toFixed(CFG_NUMBER);
					var qty = item.qty;//账面箱数
					qty = parseFloat(qty).toFixed(CFG_NUMBER);
					var smallQty = item.smallQty;//账面散数
					smallQty = parseFloat(smallQty).toFixed(CFG_NUMBER);
					var price = item.costPrice;//成本单价
					var amount = item.costAmount;//账面金额
					amount = parseFloat(amount).toFixed(CFG_MONEY);
					var url = "tScIcitemController.do?getItemInfo&id=" + id + "&rowIndex=" + rowindex + "&tranType=53";
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
							//var invQty = attr.invQty;//库存数量
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].qty']").val(qty);//账面箱数
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].qty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkQty']").val(qty);//盘点箱数
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkQty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].smallQty']").val(smallQty);//账面散数
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].smallQty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkSmallQty']").val(smallQty);//盘点散数
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkSmallQty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].basicQty']").val(basicQty);//账面数量
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].basicQty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkBasicQty']").val(basicQty);//盘点数量
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkBasicQty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].amount']").val(amount);//账面金额
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].amount']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkAmount']").val(amount);//盘点金额
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].chkAmount']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].diffQty']").val(0.0);//差异数量
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].diffQty']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].diffAmount']").val(0.0);//差异金额
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].diffAmount']").trigger("input");
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);//换算率
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].costPrice']").val(price);//成本单价
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].batchNo']").val(batchNo);//批号
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].itemNo']").val(number);//商品编号
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].itemId']").val(itemId);
							setValOldIdAnOldName($("input[name='tScIcChkstockbillentryList[" + rowIndex + "].itemNo']"), id, number);
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].model']").val(model);//规格
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].itemName']").val(name);//商品名称
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].barCode']").val(barCode);//条形码
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].secunitId']").val(secUnitId);
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].basicUnitId']").val(basicUnitId);
							$("input[name='tScIcChkstockbillentryList[" + rowIndex + "].idSrc']").val(idSrc);

							$("#unitId\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$("#unitId\\[" + rowIndex + "\\]").combobox("setValue", unitId);
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
	var oldValue = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").attr("oldvalue");
	var newValue = $("input[name='tScIcChkstockbillentryList["+index+"].qty']").val();
	if(parseFloat(oldValue) > parseFloat(newValue)){
		tip("数量不可小于当前数量");
		$("input[name='tScIcChkstockbillentryList["+index+"].qty']").val(oldValue);
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

function setChkType(){
	var chkType = $("select[name='chkType_']").val();
	$("#chkType").val(chkType);
}

//审核后方法
function afterAudit(billId){
	var tranType = $("#tranType").val();
	var id = $("#id").val();
	var url = "tScIcChkstockbillController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		success: function (d) {
			if(!d.success){
				tip(d.msg);
			}
		}
	});
}