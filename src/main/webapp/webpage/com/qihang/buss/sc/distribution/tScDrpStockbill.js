$(function(){
	setDefaultDate();//设置默认时间
	addButton();//添加选单按钮
	$('#dealerName').keypress(function (e) {//经销商
		if (e.keyCode == 13) {
			selectitemNameDialog();
		}
	});
	$("#deptName").keypress(function (e) {//部门
		if (e.keyCode == 13) {
			selectDeptDialog();
		}
	});
	$("#empName").keypress(function(e){//经办人
		if (e.keyCode == 13) {
			selectEmpDialog();
		}
	});
	$("#rStockName").keypress(function (e) {//仓库
		if (e.keyCode == 13) {
			selectStockDialog();
		}
	});
	$("#accountName").keypress(function (e) {//仓库
		if (e.keyCode == 13) {
			selectAccountDialog();
		}
	});
	//优惠金额
	$('#rebateAmount').change(function () {
		sumAllAmount();
	});
	//订单金额
	$("#amount").change(function () {
		sumAllAmount();
	});
	var CFG_OTHER = $("#CFG_OTHER").val();
	var CFG_MONEY = $("#CFG_MONEY").val();
	var weight = $("#weight").val() || 0;
	var allAmount = $("#allAmount").val() || 0;
	var rebateAmount = $("#rebateAmount").val() || 0;
	var freight = $("#freight").val() || 0;
	var curPayAmount = $("#curPayAmount").val() || 0;
	$("#weight").val(parseFloat(weight).toFixed(CFG_OTHER));
	$("#allAmount").val(parseFloat(allAmount).toFixed(CFG_MONEY));
	$("#rebateAmount").val(parseFloat(rebateAmount).toFixed(CFG_MONEY));
	$("#freight").val(parseFloat(freight).toFixed(CFG_MONEY));
	$("#curPayAmount").val(parseFloat(curPayAmount).toFixed(CFG_MONEY));

	var delearType = $("#delearType").val();
	if(delearType == 'CDealer'){
		$("#dealerName").removeAttr("datatype");
		$("#dealerName").next().text("");
		$("#dealerName").unbind();
	}
})

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

//统计应收金额
function sumAllAmount() {
	var rebateAmount = 0;
	var amount = 0;
	var sum = 0;
	if ($('#amount').val() != "") {
		amount = parseFloat($('#amount').val());
	}
	if ($('#rebateAmount').val() != "") {
		rebateAmount = parseFloat($('#rebateAmount').val());
	}
	if(amount==0){
		$('#allAmount').val(0);
		return;
	}
	if ($('#rebateAmount').val() != "" && $('#amount').val() != "") {
		if (amount < rebateAmount) {
			$('#allAmount').val(0);
			return;
		}
	}
	//统计应收金额 =订单金额-优惠金额
	sum = amount - rebateAmount;
	$('#allAmount').val(sum);
}

//数量发生改变时，取报价单单价
function changePrice(index){
	var customerId = $("#dealerID").val();
	var qty = $("input[name='tScDrpStockbillentryList["+index+"].qty']").val();
	var itemId = $("input[name='tScDrpStockbillentryList["+index+"].itemID']").val();//商品
	var unitId = $("#tScDrpStockbillentryList\\["+index+"\\]\\.unitID").combobox("getValue");
	var itemNo = $("input[name='tScDrpStockbillentryList["+index+"].number']").val();//商品编号
	var freegifts = $("select[name='tScDrpStockbillentryList[" + index + "].freeGifts_']").val();
	var tranType = $("#tranType").val();
	if(itemId && qty && customerId && unitId && freegifts){
		$.ajax({
			type: 'POST',
			url: 'tScDrpOrderController.do?getPrice',
			async: false,
			cache: false,
			data: {'tranType':tranType,'customerId': customerId,'qty':qty,'itemId':itemId,'unitId':unitId,'itemNo':itemNo},
			dataType: 'json',
			success: function (data) {
				var resultData = data.attributes;
				var price = resultData.price;//报价单价
				if (data.success == true) {
					var salesID = resultData.salesID;//促销类型
					var salesType = resultData.salesType;//促销类型分类 0：调价 1：买一赠一
					var salesName = "调价政策";
					if("1" == salesType){
						salesName = "买一赠一"
					}
					$("input[name='tScDrpStockbillentryList["+index+"].salesID']").val(salesID);
					$("input[name='tScDrpStockbillentryList["+index+"].salesName']").val(salesName);
					if("1" == salesType){
						var isFreeGift =  $("input[name='tScDrpStockbillentryList["+index+"].isFreeGift']").val();
						if(isFreeGift == "1"){
							var rowLength = rowInfo[index];
							for(var i = 0 ; i < rowLength ; i++){
								clickDelTScOrderentryBtn((index + 1));
							}
						}
						$("input[name='tScDrpStockbillentryList["+index+"].isFreeGift']").val(1);
						var freeGiftInfo = resultData.freeGiftInfo;
						rowInfo[index] = freeGiftInfo.length;
						for(var j = 0; j < freeGiftInfo.length ; j++) {
							clickAddTScOrderentryBtn((index+j));
							var itemId = freeGiftInfo[j].itemId;
							var itemNo = freeGiftInfo[j].itemNo;
							var itemName = freeGiftInfo[j].itemName;
							var model = freeGiftInfo[j].model;
							var barCode = freeGiftInfo[j].barCode;
							var unitId = freeGiftInfo[j].unitId;
							var qty = freeGiftInfo[j].qty;
							var basicQty = freeGiftInfo[j].basicQty;
							var coefficient = freeGiftInfo[j].coefficient;
							var freeIndex = index+j + 1;
							//赠品信息
							//商品
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemId']").val(itemId);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemNumber']").val(itemNo);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemNumber']").attr("disabled", "disabled");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemNumber']").css("background-color", "rgb(226, 226, 226)");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemName']").val(itemName);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemModel']").val(model);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemBarcode']").val(barCode);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].salesName']").val(salesName);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].coefficient']").val(coefficient);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].basicQty']").val(basicQty);
							setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemNumber']"),itemId,itemNo);
							//数量
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").val(qty);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").css("background-color", "rgb(226, 226, 226)");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").trigger("change");
							//单价
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").val(0);
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").trigger("change");
							//金额
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxAmountEx']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
							//折扣率
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].discountRate']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
							//折后金额
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].inTaxAmount']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
							//税率
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxRate']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
							//赠品标记
							$("select[name='tScDrpStockbillentryList[" + freeIndex + "].freeGifts_']").val(1);
							$("select[name='tScDrpStockbillentryList[" + freeIndex + "].freeGifts_']").attr("disabled", "disabled");
							$("select[name='tScDrpStockbillentryList[" + freeIndex + "].freeGifts_']").trigger("change");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].freeGifts']").val(1);
							//交货日期
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].fetchDate']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].fetchDate']").removeAttr("onclick");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].fetchDate']").css("background-color", "rgb(226, 226, 226)");
							//仓库
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].stockName']").attr("disabled", "disabled");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].stockName']").css("background-color", "rgb(226, 226, 226)");
							//单位
							$('#tScDrpStockbillentryList\\[' + freeIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$('#tScDrpStockbillentryList\\[' + freeIndex + '\\]\\.unitID').combobox("setValue",unitId);
							$('#tScDrpStockbillentryList\\[' + freeIndex + '\\]\\.unitID').combobox("disable");
							$("input[name='tScDrpStockbillentryList[" + freeIndex + "].isFreeGift']").val(1);
						}
					}else{
						var isFreeGift =  $("input[name='tScDrpStockbillentryList["+index+"].isFreeGift']").val();
						if(isFreeGift == "1"){
							var rowLength = rowInfo[index];
							for(var i = 0 ; i < rowLength ; i++){
								clickDelTScOrderentryBtn((index + 1));
							}
						}
						$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").val(price);
						$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").trigger("change");
						$("input[name='tScDrpStockbillentryList["+index+"].isFreeGift']").val(0);
					}
					//$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").val(price);
					//$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").trigger("change");
				}else{
					$("input[name='tScDrpStockbillentryList["+index+"].isFreeGift']").val(2);
					$("input[name='tScDrpStockbillentryList["+index+"].freeGifts_']").val(2);
					$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").val(price);
					$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").trigger("change");
				}
			}
		});
	}
}

//结算账户回车事件
function selectAccountDialog(){
	var accountName = $("#accountName").val();
	var url = 'tScSettleacctController.do?goSelectDialog&name=' + accountName;
	var width = 1000;
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
				$("#accountID").val(item.id);
				$("#accountName").val(item.name);
				setValOldIdAnOldName($("#accountName"), item.id, item.name);
				$("#curPayAmount").attr("datatype","num");
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//初始化下标
function resetTrNum(tableId,index,indexName) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input,span, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			var comboboName = $this.attr("comboname");
			var change_ = $this.attr("onchange");
			if(null != comboboName){
				console.log(comboboName);
				var s = comboboName.indexOf("[");
				var e = comboboName.indexOf("]");
				var new_name = comboboName.substring(s+1,e);
				new_name = "["+new_name+"]"
				$this.attr("comboname",comboboName.replace(new_name,"["+i+"]"));
			}
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					new_name = "["+new_name+"]"
					$this.attr("name",name.replace(new_name,"["+i+"]"));
				}
				if(name.indexOf("stockName") > -1){
					var stockId = $("input[name='"+indexName+"["+i+"].stockID']").val();
					var stockName = $("input[name='"+indexName+"["+i+"].stockName']").val();
					setValOldIdAnOldName($("input[name='"+indexName+"["+i+"].stockName']"),stockId,stockName);
					$this.attr("onblur","orderListStockBlur("+i+",'stockId','stockName','"+indexName+"')");
				}else if(name.indexOf("number") > -1){
					$this.attr("onblur","orderListStockBlur("+i+",'itemID','number','"+indexName+"')");
				}else if(name.indexOf("inTaxAmount") > -1){
					$this.attr("onchange","setAllAmount()");
				}else if(name.indexOf("kfDateType") > -1){
					$this.attr("onchange","setPeriodDate("+i+",'"+indexName+"')");
				}else if(name.indexOf("indexNumber") > -1){
					$this.val(i+1);
				}else if(name.indexOf("freeGifts_") > -1){
					$this.attr("onchange","setPrice("+i+")");
				}
				if (name.indexOf("qty") > -1) {
					$this.attr("onblur", "changePrice("+i+")");//onchange="changePrice(0)"
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					new_id = "["+new_id+"]"
					$this.attr("id",id.replace(new_id,"["+i+"]"));
				}
			}
			if (onclick_str != null && onclick_str !="WdatePicker()" ) {
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {
					if(name.indexOf("date") < 0) {
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
			/*if (blur_str != null) {
				if (blur_str.indexOf("#index#") >= 0) {
					$this.attr("onblur", blur_str.replace(/#index#/g, i));
				} /!*else {
					var s = blur_str.indexOf("(");
					var e = blur_str.indexOf(",");
					var new_blur = blur_str.substring(s + 1, e);
					$this.attr("onblur", blur_str.replace(new_blur, i));
				}*!/
			}*/
			//if(null != change_){
			//	if (change_.indexOf("#index#") >= 0) {
			//		$this.attr("onchange", change_.replace(/#index#/g, i));
			//	} else {
			//		var s = change_.indexOf("(");
			//		var e = change_.indexOf(")");
			//		var new_change = change_.substring(s + 1, e);
			//		$this.attr("onchange", change_.replace(new_change, i));
			//	}
			//}
		});
		setFunctionInfo(i,indexName);
		$("input[name='"+indexName+"["+i+"].indexNumber']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});

	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1)) {
			$('#tScDrpStockbillentryList\\[' + i + '\\]\\.unitID').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			});
			/*$('#tScDrpStockbillentryList\\[' + i + '\\]\\.basicUnitID').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false,
				disabled:true
			});
			$('#tScDrpStockbillentryList\\[' + i + '\\]\\.secUnitID').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false,
				disabled:true
			});*/
		}
	});
	setAllAmount();
}
//赠品标记发生改变时触发事件
function setPrice(index){
	var value = $("select[name='tScDrpStockbillentryList["+index+"].freeGifts_']").val();
	//$("input[name='tScDrpStockbillentryList["+index+"].freeGifts']").val(value);
	$("input[name='tScDrpStockbillentryList["+index+"].freeGifts']").val(value);
	if(value == 1){
		$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").val(0);//单价
		$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").trigger("change");
		//单价只读
		$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").attr("readonly","readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
		//金额只读
		$("input[name='tScDrpStockbillentryList["+index+"].taxAmountEx']").attr("readonly","readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
		//折扣率只读
		$("input[name='tScDrpStockbillentryList["+index+"].discountRate']").attr("readonly","readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].discountRate']").css("background-color","rgb(226,226,226)");
		//折后金额
		$("input[name='tScDrpStockbillentryList["+index+"].inTaxAmount']").attr("readonly","readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
		//税率
		$("input[name='tScDrpStockbillentryList["+index+"].taxRate']").attr("readonly","readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].taxRate']").css("background-color","rgb(226,226,226)");
		//onPriceChange(index);
	}else{
		//单价只读
		$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").removeAttr("readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].taxPriceEx']").css("background-color","white");
		//金额只读
		$("input[name='tScDrpStockbillentryList["+index+"].taxAmountEx']").removeAttr("readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].taxAmountEx']").css("background-color","white");
		//折扣率只读
		$("input[name='tScDrpStockbillentryList["+index+"].discountRate']").removeAttr("readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].discountRate']").css("background-color","white");
		//折后金额
		$("input[name='tScDrpStockbillentryList["+index+"].inTaxAmount']").removeAttr("readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].inTaxAmount']").css("background-color","white");
		//税率
		$("input[name='tScDrpStockbillentryList["+index+"].taxRate']").removeAttr("readonly");
		$("input[name='tScDrpStockbillentryList["+index+"].taxRate']").css("background-color","white");
	}
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
function setDefaultDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
	var date = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	//var billDate = $("#date").val();
	var id = $("#id").val();
	if(!id){
		$("#date").val(year+"-"+month+"-"+date);
		$("input[name='tScDrpStockbillentryList[#index#].date']").val(year+"-"+month+"-"+date);
	}else{
		$("input[name='tScDrpStockbillentryList[#index#].date']").val(year+"-"+month+"-"+date);
	}
}
function selectitemNameDialog() {
	var tempName = $("#dealerName").val();
	var url = 'tScDrpStockbillController.do?goSelectToStockbillDialog&name=' + tempName;
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
				var iscreditmgr = item.iscreditmgr;//是否启用信用
				var creditamount = item.creditamount;//信用额度
				var countAmount = item.countAmount;//已执行金额
				$("input[name='dealerName']").val(item.name);
				$("input[name='dealerID']").val(item.dealer);
				$("#iscreditmgr").val(iscreditmgr);
				$("#creditamount").val(creditamount);
				$("#countAmount").val(countAmount);
				setValOldIdAnOldName($("#dealerName"), item.id, item.name);
				var contact = item.contact; //联系人
				var mobilePhone = item.mobilephone;//手机号码
				var fax = item.fax; //传真
				var phone = item.phone;//电话
				var address = item.address;//地址

				$('#contact').val(contact);
				$('#mobilePhone').val(mobilePhone);
				$('#phone').val(phone);
				$('#fax').val(fax);
				$('#address').val(address);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
function selectEmpDialog(){
	var empName = $("#empName").val();
	var url = "tScEmpController.do?goselectdeptIdNameDialog&name="+empName;
	var width = 885;
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
				$("#empID").val(item.id);
				if(item.deptIdName){
					$("#deptName").val(item.deptIdName);
					$("#deptID").val(item.deptID);
					setValOldIdAnOldName($("#deptName"), item.deptID, item.deptIdName);
				}
				setValOldIdAnOldName($("#empName"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
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
function selectStockNameDialog(index) {
	var tempName = $("input[name='tScDrpStockbillentryList["+index+"].stockName']").val();
	var url = encodeURI('tBiStockController.do?goSelectDialog&name=' + tempName);
	var width = 1000;
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
				$("input[name='tScDrpStockbillentryList["+index+"].stockName']").val(item.name);//设置模板从表头取值
				$("input[name='tScDrpStockbillentryList["+index+"].stockID']").val(item.id);
				setValOldIdAnOldName($('#stockName'), item.id, item.name)
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

//选择仓库
function selectStockDialog(index) {
	var itemName = $('#rStockName').val();
	if(!itemName){
		itemName = $("input[name='tScDrpStockbillentryList[" + index + "].stockName']").val();
	}
	if(!itemName){
		itemName = "";
	}
	var url = 'tBiStockController.do?goSelectDialog&name=' + itemName;
	var width = 1000;
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
					$('#rStockName').val(item.name);
					$('#rStockID').val(item.id);
					$tbody = $("#add_tScDrpStockbillentry_table");
					var length = $tbody[0].rows.length;
					$("input[name='tScDrpStockbillentryList[#index#].stockID']").val(item.id);
					$("input[name='tScDrpStockbillentryList[#index#].stockName']").val(item.name);
					//表头仓库发生变化，表体仓库也跟着发生变化
					for (var i = 0; i < length; i++) {
						$("input[name='tScDrpStockbillentryList[" + i + "].stockID']").val(item.id);
						$("input[name='tScDrpStockbillentryList[" + i + "].stockName']").val(item.name);
						setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + i + "].stockName']"), item.id, item.name);
					}
					setValOldIdAnOldName($("#rStockName"), item.id, item.name);
				}else{
					$("input[name='tScDrpStockbillentryList[" + index + "].stockID']").val(item.id);
					$("input[name='tScDrpStockbillentryList[" + index + "].stockName']").val(item.name);
					setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + index + "].stockName']"), item.id, item.name);
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


//选择商品
function selectIcitemDialog(index){
	var number = $("input[name='tScDrpStockbillentryList["+index+"].number']").val();
	var url = 'tScIcitemController.do?goInventorySelectDialog&number=' + number+'&isZero=0';
	var stockId = $("#rStockId").val();
	if(stockId){
		url = url+"&stockID="+stockId;
	}
	var width = 800;
	var height = 500;
	var title = "库存商品";
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
				var items = iframe.getMoreSelectionData();
				if (items != "") {
					var nextTrLength =  $('input[name="tScDrpStockbillentryList['+index+'].indexNumber"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					var newLength = itemsLength-nextTrLength-1;
					for (var j = 0; j < newLength; j++) {
						clickAddTScDrpStockbillentryBtn(index);
					}
					var sonId = $("#sonID").val();//当前分支机构
					$.each(items,function(i,item){
						var rowindex = parseInt(index) + i;
						var id = item.itemId;//id
						var number = item.itemNo;//编码
						var name = item.itemName;//名称
						/*var barCode = item.barCode;*/
						var model = item.model;//规格
						var kfPeriod = item.kfPeriod;//保质期
						var kfdateType = item.kfDateType;//保质期类型
						var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
						var batchManager = item.batchManager;//是否批号管理;
						var stockSonId = item.stockSonId;//商品默认仓库分支机构id；
						var weight = item.weight==null?0:item.weight;//item.weight|0;//重量
						var basicQty = item.basicQty;//库存基本数量
						var taxRateOut = item.taxRateOut;//商品销项税
						var stockId = item.stockId;
						var batchNo = item.batchNo;//批号
						var stockName = item.stockName;
						var url = "tScIcitemController.do?getItemInfo&id=" + item.itemId+"&rowIndex="+rowindex+"&tranType=1506&stockId="+stockId+"&batchNo="+batchNo;
						$.ajax({
							type: 'POST',
							url: url,
							dataType: 'json',
							cache: false,
							success: function (data) {
								var attr = data.attributes;
								var barCode = attr.barCode;
								var unitId = attr.unitId;
								var basicUnitID  = attr.basicUnitId;
								var secUnitId = attr.secUnitId;
								var coefficient = attr.cofficient;
								var secCoefficient = attr.secCoefficient;
								var rowIndex = attr.rowIndex;
								var itemId = attr.id;

								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].number']").val(number);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].itemID']").val(id);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].model']").val(model);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].name']").val(name);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].weight']").val(weight);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].itemWeight']").val(weight);
								//$('input[name="tScDrpOrderentryList[' + rowIndex + '].itemWeight"]:first').trigger("change");
								$('input[name="tScDrpStockbillentryList[' + rowIndex + '].taxRate"]').val(taxRateOut);
								$('input[name="tScDrpStockbillentryList[' + rowIndex + '].taxRate"]').trigger("change");
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].coefficient']").val(coefficient);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].basicUnitID']").val(basicUnitID);
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].secUnitID']").val(secUnitId);

								var checkId = $("input[name='tScDrpStockbillentryList[" + rowIndex + "].stockID']").val();
								if(!checkId) {
									if(stockSonId == sonId) {
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].stockID']").val(stockId);
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].stockName']").val(stockName);
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].stockName']").attr("readonly", "readonly");
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].stockName']").css("background-color", "rgb(226,226,226)");
									}
								}
								setValOldIdAnOldName($('input[name="tScDrpStockbillentryList[' + rowIndex + '].stockName"]'),stockId,stockName);

								$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.unitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.unitID').combobox({
									onChange: function (newV, oldV) {
										if (oldV != newV) {
											var changeUrl = "tScIcitemController.do?getChangeInfo&unitId="+newV+"&rowIndex="+index;
											$.ajax({
												type: 'POST',
												url: changeUrl,
												dataType: 'json',
												cache: false,
												success: function (data) {
													if(data.success == true){
														var attributes = data.attributes;
														var barCode = attributes.barCode;
														$("input[name='tScDrpStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
														$('input[name="tScDrpStockbillentryList['+rowIndex+'].coefficient"]').val(coefficient);//换算率
														$('input[name="tScDrpStockbillentryList['+rowIndex+'].coefficient"]').trigger("change");
													}
												}
											});
										}
									}
								});
								debugger;
								$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.unitID').combobox("setValue", unitId);
								setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + rowIndex + "].number']"), item.id,item.itemNo);

								/*$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.basicUnitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);

								$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.basicUnitID').combobox("setValue", basicUnitID);
								setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.secUnitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);

								$('#tScDrpStockbillentryList\\[' + rowIndex + '\\]\\.secUnitID').combobox("setValue", secUnitId);
								setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + rowIndex + "].number']"), item.id,item.number);*/
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(226,226,226)");
								if ("Y" == batchManager) {
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").val(batchNo);
								}
								if("N" == batchManager){
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").attr("disabled","disabled");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").removeAttr("datatype");
								}else{
									if(batchNo){
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").val(batchNo);
									}else{
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").removeAttr("disabled");
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").css("background-color","rgb(255,255,255)");
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").attr("datatype","*");
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").attr("onkeypress","keyDownInfo('"+rowIndex+"','batchNo')");
										$("input[name='tScDrpStockbillentryList[" + rowIndex + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+rowIndex+"')");
									}
								}
								if("Y"== iSKFPeriod) {
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].kfperiod']").val(kfPeriod);
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].kfperiod']").attr("disabled","disabled");
									$("select[name='tScDrpStockbillentryList[" + rowIndex + "].kfDateType_']").val(kfdateType);
									$("select[name='tScDrpStockbillentryList[" + rowIndex + "].kfDateType_']").attr("disabled","disabled");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].kfDateType']").val(kfdateType);

									var kfdate = $("input[name='tScDrpStockbillentryList[" + rowIndex + "].date']").val();
									var interval = "";
									if (kfdateType == "0001") {
										interval = "y";
									} else if (kfdateType == "0002") {
										interval = "m";
									} else if (kfdateType == "0003") {
										interval = "d";
									}
									var periodDate = dateAdd(interval, kfPeriod, new Date(kfdate));
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].periodDate']").val(periodDate);
								}else{
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].date']").attr("readonly","readonly");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].date']").unbind("onClick");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].date']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].kfperiod']").attr("readonly","readonly");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].kfperiod']").css("background-color","rgb(226,226,226)");
									$("select[name='tScDrpStockbillentryList[" + rowIndex + "].kfDateType']").attr("disabled","disabled");
									$("select[name='tScDrpStockbillentryList[" + rowIndex + "].kfDateType']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].date']").val("");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].kfperiod']").val("");
									$("select[name='tScDrpStockbillentryList[" + rowIndex + "].kfDateType']").val("");
									$("input[name='tScDrpStockbillentryList[" + rowIndex + "].periodDate']").val("");
								}
							}
						});
					})
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


//表格校验事件
function orderListStockBlur(rowIndex,id,name){
	//checkInput( $('input[name="tScDrpStockbillentryList['+rowIndex+'].'+id+'"]'),$('input[name="tScDrpStockbillentryList['+rowIndex+'].'+name+'"]'));
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

//添加选单按钮
function addButton(){
	var button = document.createElement("input");
	button.id="chooseBill";
	button.type="button";
	button.class="button";
	button.value="选单"
	var buttonDiv = document.getElementsByClassName("ui_buttons");
	var load = $("#load").val()
	var tranType = $("#tranType").val();
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
		var option2 = document.createElement("option");
		option2.value = "1504"
		option2.innerText = "分销订单";
		select.appendChild(option1);
		select.appendChild(option2);
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

//选单
var CFG_NUMBER = $("#CFG_NUMBER").val();
var CFG_MONEY = $("#CFG_MONEY").val();
function selectBillDialog(){
	var itemId = $("#dealerID").val();
	if(!itemId){
		tip("请选择下游经销商后再进行选单操作");
		$("#chooseType").val("");
		return;
	}
	var tranType = "1505";
	var title = "";
	var url = "";
	var sonId = $("#sonID").val();
	if("1505" == tranType){
		title = "分销订单";
		url = "tScDrpOrderController.do?goSelectDialog&itemID="+itemId+"&tranType="+tranType+"&sonId="+sonId;
		url = encodeURI(url);
	}else{
		tip("请选择选单类型");
		return;
	}
	var width = 1000;
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
				var itemList = iframe.getSelectRows();//getSelectionData();
				//var ids ="";
				//for(var i=0 ; i< itemList.length ; i++){
				//	var item = itemList[i];
				//	ids += item.id+",";
				//}
				var item = itemList[itemList.length-1];
				/*给主表表单赋值*/
				$("#classIDSrc").val(item.tranType); //源单类型
				$("#fidSrc").val(item.id);//源单主键
				$("#billNoSrc").val(item.billNo);//源单编号
				/*$("#itemId").val(item.itemID);
				$("#itemName").val(item.itemName);*/
				$("#dealerName").attr("readonly","readonly");
				/*setValOldIdAnOldName($("#itemName"), item.itemID, item.itemName);*/
				$("#deptID").val(item.deptID);
				$("#deptName").val(item.deptName);
				setValOldIdAnOldName($("#deptName"), item.deptID, item.deptName);
				$("#empID").val(item.empID);
				$("#empName").val(item.empName);
				setValOldIdAnOldName($("#empName"), item.empID, item.empName);
				$("#stockID").val(item.stockID);
				$("#stockName").val(item.stockName);
				setValOldIdAnOldName($("#stockName"), item.stockID, item.stockName);
				/*$("#contact").val(item.contact);
				$("#mobilePhone").val(item.mobilePhone);
				$("#phone").val(item.phone);
				$("#fax").val(item.fax);
				$("#address").val(item.address);*/

				//var entryUrl = "tPoOrderController.do?loadEntryList&id="+ids+"&tranType="+tranType;
				//$.ajax({
				//	url: entryUrl,
				//	dataType: 'json',
				//	cache: false,
				//	async: false,
				//	success: function (data) {
				//		if(data.length > 0){、
				var length = $("#add_tScDrpStockbillentry_table").find(">tr").length;
				length--;
				var id = $("input[name='tScDrpStockbillentryList["+length+"].dealerID']").val();
				var dataLength = itemList.length;
				if(!id){
					dataLength--;
				}
				for(var k=0 ; k < dataLength ; k++){
					clickAddTScDrpStockbillentryBtn(length);
				}
				if(id){
					length++;
				}
				for(var i=0 ; i<itemList.length ;i++) {
					var row = itemList[i];
					for (var k in row) {
						if (k == "unitID") {
							var itemId = row.goodsItemID;
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.unitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.unitID').combobox("setValue", row[k]);
							//$('#tScDrpStockbillentryList\\[' + length + '\\]\\.unitID').combobox("disable");
						} else if (k == "basicUnitID") {
							var itemId = row.goodsItemID;
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.basicUnitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.basicUnitID').combobox("setValue", row[k]);
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.basicUnitID').combobox("disable");
						} else if (k == "secUnitID") {
							var itemId = row.goodsItemID;
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.secUnitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.secUnitID').combobox("setValue", row[k]);
							$('#tScDrpStockbillentryList\\[' + length + '\\]\\.secUnitID').combobox("disable");
						}else if (k == "qty") {
								var stockQty = 0;
								var qty = row[k];
								var basicQty = row["basicQty"]==""?0:row["basicQty"];
								var coe = row["coefficient"];
								qty = ((parseFloat(basicQty)-parseFloat(stockQty))/coe).toFixed(CFG_NUMBER);
								$("input[name='tScDrpStockbillentryList[" + length + "]." + k + "']").val(qty);
								$("input[name='tScDrpStockbillentryList[" +length + "].billQty']").val(row[k]);
							}else if(k == "batchNo"){
								$("input[name='tScDrpStockbillentryList[" +length + "].batchNo']").val(row[k]);
							}else {
								$("input[name='tScDrpStockbillentryList[" +length + "]." + k + "']").val(row[k]);
							}
							if(k == "goodsNo"){
								$("input[name='tScDrpStockbillentryList[" + length + "].number']").attr("disabled","disabled");
							}
							if ("Y" == row.isKfPeriod) {
								$("input[name='tScDrpStockbillentryList[" + length + "].kfperiod']").val(row.KfPeriod);//
								$("select[name='tScDrpStockbillentryList[" + length + "].kfDateType_']").val(row.kfDateType);//
								$("input[name='tScDrpStockbillentryList[" + length + "].kfDateType']").val(row.kfDateType);//
								var kfDate = $("input[name='tScDrpStockbillentryList[" + length + "].date']").val();
								var interval = "";
								if (row.kfDateType == "0001") {
									interval = "y";
								} else if (row.kfDateType == "0002") {
									interval = "m";
								} else if (row.kfDateType == "0003") {
									interval = "d";
								}
								var periodDate = dateAdd(interval, row.KfPeriod, new Date(kfDate));
								$("input[name='tScDrpStockbillentryList[" + length + "].periodDate']").val(periodDate);
							} else {
								$("input[sname='tScDrpStockbillentryList[" + length + "].date']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + length + "].date']").css("background-color", "rgb(226,226,226)");
								$("input[name='tScDrpStockbillentryList[" + length + "].date']").attr("onClick", "");
								$("input[name='tScDrpStockbillentryList[" + length + "].date']").val("");

								$("select[name='tScDrpStockbillentryList[" + length + "].kfDateType_']").attr("disabled", "disabled");
								$("select[name='tScDrpStockbillentryList[" + length + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
								$("select[name='tScDrpStockbillentryList[" + length + "].kfDateType_']").val("");

								$("input[name='tScDrpStockbillentryList[" + length + "].kfperiod']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + length + "].kfperiod']").css("background-color", "rgb(226,226,226)");
								$("input[name='tScDrpStockbillentryList[" + length + "].kfperiod']").val("");

								$("select[name='tScDrpStockbillentryList[" + length + "].periodDate']").attr("disabled", "disabled");
								$("select[name='tScDrpStockbillentryList[" + length + "].periodDate']").css("background-color", "rgb(226,226,226)");
								$("input[name='tScDrpStockbillentryList[" + length + "].periodDate']").val("");

								/*$("select[name='tScDrpStockbillentryList[" + length + "].freegifts_']").val(row[k]);
								$("select[name='tScDrpStockbillentryList[" + length + "].freegifts_']").attr("disabled","disabled");
								$("select[name='tScDrpStockbillentryList[" + length + "].freegifts_']").css("background-color","rgb(226,226,226)");
								$("input[name='tScDrpStockbillentryList[" + length + "].freegifts']").val(row[k]);*/
							}
						}
						if("Y"==row.isKfPeriod) {
							setPeriodDate(length,"tScDrpStockbillentryList");
						}
						if ("N" == row.batchManager) {
							$("input[name='tScDrpStockbillentryList[" + length + "].batchNo']").attr("readonly", "readonly");
							$("input[name='tScDrpStockbillentryList[" + length + "].batchNo']").css("background-color", "rgb(226,226,226)");
							$("input[name='tScDrpStockbillentryList[" + length + "].batchNo']").removeAttr("datatype");
						}else{
							$("input[name='tScDrpStockbillentryList[" + length + "].batchNo']").attr("readonly","readonly");
							$("input[name='tScDrpStockbillentryList[" + length + "].batchNo']").css("background-color","rgb(226,226,226)");
							$("input[name='tScDrpStockbillentryList[" + length + "].stockName']").attr("disabled","disabled");
							$("input[name='tScDrpStockbillentryList[" + length + "].stockName']").css("background-color","rgb(226,226,226)");
						}
						var itemid = row.goodsItemID; //商品ID
						var itemName = row.goodsName; //商品名称

						var itemNo = row.goodsNo;//商品编号
						var model = row.model;//规格
						var barCode = row.barCode;//条形码

						$("input[name='tScDrpStockbillentryList[" + length + "].itemID']").val(itemid);
						$("input[name='tScDrpStockbillentryList[" + length + "].number']").val(itemNo);
						$("input[name='tScDrpStockbillentryList[" + length + "].name']").val(itemName);
						$("input[name='tScDrpStockbillentryList[" + length + "].model']").val(model);
						$("input[name='tScDrpStockbillentryList[" + length + "].barCode']").val(barCode);

						$("input[name='tScDrpStockbillentryList[" + length + "].coefficient']").val(row.coefficient);
						$("input[name='tScDrpStockbillentryList[" + length + "].secCoefficient']").val(row.secCoefficient);

						//var discountPrice = $("input[name='tScDrpStockbillentryList[" + length + "].discountPrice']").val();
						//var costPrice = parseFloat(discountPrice)/parseFloat(basicCoefficient)
						//var qty = $("input[name='tScDrpStockbillentryList[" + length + "].qty']").val();
						//$("input[name='tScDrpStockbillentryList[" + length + "].costPrice']").val(costPrice.toFixed(2));
						//$("input[name='tScDrpStockbillentryList[" + length + "].costAmount']").val((costPrice.toFixed(2)*qty).toFixed(2))

						setFunctionInfo(length,"tScDrpStockbillentryList");
						$("input[name='tScDrpStockbillentryList[" + length + "].qty']").trigger("change");
						$("input[name='tScDrpStockbillentryList[" + length + "].taxPriceEx']").trigger("change");
						$("input[name='tScDrpStockbillentryList[" + length + "].taxAmountEx']").trigger("change");

						var itemNo = $("input[name='tScDrpStockbillentryList[" + length + "].number']").val();
						var itemId = $("input[name='tScDrpStockbillentryList[" + length + "].itemID']").val();
						setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + length + "].number']"), itemId, itemNo);
						if(row.stockID==""){
							$("input[name='tScDrpStockbillentryList[" + length + "].stockName']").attr("disabled","disabled");
							$("input[name='tScDrpStockbillentryList[" + length + "].stockName']").removeAttr("datatype");
							$("input[name='tScDrpStockbillentryList[" + length + "].stockName']").css("background-color","rgb(226,226,226)");
						}else{
							$("input[name='tScDrpStockbillentryList[" + length + "].stockID']").val(row.stockID);
							$("input[name='tScDrpStockbillentryList[" + length + "].stockName']").val(row.stockName);
							setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + length + "].stockName']"), row.stockID, row.stockName);
						}
						$("input[name='tScDrpStockbillentryList[" + length + "].fidSrc']").val(row.id);
						$("input[name='tScDrpStockbillentryList[" + length + "].billNoSrc']").val(row.billNo);
						$("input[name='tScDrpStockbillentryList[" + length + "].classIDSrc']").val(row.tranType);
						$("input[name='tScDrpStockbillentryList[" + length + "].interIDSrc']").val(row.interID);
						$("input[name='tScDrpStockbillentryList[" + length + "].billNoOrder']").val(row.billNo);
						$("input[name='tScDrpStockbillentryList[" + length + "].fidOrder']").val(row.rOrderId);
						$("input[name='tScDrpStockbillentryList[" + length + "].interIDOrder']").val(row.interID);

						var tranTypeName = row.tranTypeName;
						$("input[name='tScDrpStockbillentryList[" + length + "].classIDName']").val(tranTypeName);
					   length++;
					}
				setAllAmount();
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
}

//汇总折扣金额
function setAllAmount(index){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody = $("#tScDrpStockbillentry_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScDrpStockbillentryList[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScDrpStockbillentryList[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScDrpStockbillentryList[" + i + "].qty']").val();//数量
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

	$tbody = $("#tScDrpStockbillentry_table");
	var length = $tbody[0].rows.length;
	var allWeight = 0;
	for(var i=0 ; i<length ; i++){
		var weight = $("input[name='tScDrpStockbillentryList[" + i + "].weight']").val();//商品重量
		var qty = $("input[name='tScDrpStockbillentryList[" + i + "].qty").val();
		if(!qty){
			qty = 0;
		}
		if(!weight){
			weight = 0;
		}
		weight = (parseFloat(weight)*parseFloat(qty));
		$("input[name='tScDrpStockbillentryList[" + i + "].weight_").val(weight);
		allWeight += weight;
	}
}

//汇总重量
var rowInfo = {};
function setAllWeight(index){
	$tbody = $("#tScDrpStockbillentry_table");
	var length = $tbody[0].rows.length;
	var allWeight = 0;
	for(var i=0 ; i<length ; i++){
		var weight = $("input[name='tScDrpStockbillentryList[" + i + "].weight']").val();//商品重量
		var qty = $("input[name='tScDrpStockbillentryList[" + i + "].qty").val();
		if(!qty){
			qty = 0;
		}
		if(!weight){
			weight = 0;
		}
		weight = (parseFloat(weight)*parseFloat(qty));
		$("input[name='tScDrpStockbillentryList[" + i + "].weight_").val(weight);
		allWeight += weight;
	}
	//if(allAmount > 0) {
	//$("#sumQty")[0].innerText = allQty;
	//$("#sumAmount")[0].innerText = allAmountTax;
	//$("#sumTaxAmount")[0].innerText=allAmount;
	//}

	var tranType = $("#tranType").val();
	if(tranType == "1505") {
		var itemId = $("#dealerID").val();
		var qty = $("input[name='tScDrpStockbillentryList[" + index + "].qty']").val();
		var entryItemId = $("input[name='tScDrpStockbillentryList[" + index + "].itemID']").val();//商品
		var unitId = $('#tScDrpStockbillentryList\\[' + index + '\\]\\.unitID').combobox("getValue");
		var itemNo = $("input[name='tScDrpStockbillentryList\\[" + index + "\\]\\.number']").val();//商品编号
		if (itemId && qty && unitId) {
			$.ajax({
				type: 'POST',
				url: 'tScPromotionController.do?getPrice',
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
						$("input[name='tScDrpStockbillentryList[" + index + "].salesID']").val(salesID);
						$("input[name='tScDrpStockbillentryList[" + index + "].salesName']").val(salesName);
						if ("1" == salesType) {
							var isFreeGift = $("input[name='tScDrpStockbillentryList[" + index + "].isFreeGift']").val();
							if (isFreeGift == "1") {
								var rowLength = rowInfo[index];
								for (var i = 0; i < rowLength; i++) {
									clickDelTScDrpStockbillentryBtn((index + 1));
								}
							}
							$("input[name='tScDrpStockbillentryList[" + index + "].isFreeGift']").val(1);
							var freeGiftInfo = resultData.freeGiftInfo;
							rowInfo[index] = freeGiftInfo.length;
							for (var j = 0; j < freeGiftInfo.length; j++) {
								clickAddTScDrpStockbillentryBtn((index + j));
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
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].itemID']").val(itemId);
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].number']").val(itemNo);
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].number']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].number']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].name']").val(itemName);
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].model']").val(model);
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].barCode']").val(barCode);
								setValOldIdAnOldName($("input[name='tScDrpStockbillentryList[" + freeIndex + "].number']"), itemId, itemNo);
								//批号
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].batchNo']").attr("readonly","readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].batchNo']").css("background-color", "rgb(226, 226, 226)");
								//数量
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").val(qty);
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].qty']").trigger("change");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].basicQty']").val(qty);
								//单价
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").val(0);
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxPriceEx']").trigger("change");
								//金额
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxAmountEx']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
								//折扣率
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].discountRate']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
								//折后金额
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].inTaxAmount']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
								//税率
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxRate']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
								//赠品标记
								$("select[name='tScDrpStockbillentryList[" + freeIndex + "].freegifts_']").val(1);
								$("select[name='tScDrpStockbillentryList[" + freeIndex + "].freegifts_']").attr("disabled", "disabled");
								$("select[name='tScDrpStockbillentryList[" + freeIndex + "].freegifts_']").trigger("change");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].freegifts']").val(1);
								//仓库
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].stockName']").removeAttr("disabled");
								//单位
								$('#unitId\\[' + freeIndex + '\\]').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$('#unitId\\[' + freeIndex + '\\]').combobox("setValue", unitId);
								$('#unitId\\[' + freeIndex + '\\]').combobox("disable");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].isFreeGift']").val(1);
								//生产日期
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].date']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].date']").css("background-color", "rgb(226, 226, 226)");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].date']").removeAttr("onclick");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].date']").val("");
								//保质期
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].kfperiod']").attr("readonly", "readonly");
								$("input[name='tScDrpStockbillentryList[" + freeIndex + "].kfperiod']").css("background-color", "rgb(226, 226, 226)");
								//保质期类型
								$("select[name='tScDrpStockbillentryList[" + freeIndex + "].kfDateType_']").attr("disabled", "disabled");
							}
						} else {
							var isFreeGift = $("input[name='tScDrpStockbillentryList[" + index + "].isFreeGift']").val();
							if (isFreeGift == "1") {
								var rowLength = rowInfo[index];
								for (var i = 0; i < rowLength; i++) {
									clickDelTScDrpStockbillentryBtn((index + 1));
								}
							}
							$("input[name='tScDrpStockbillentryList[" + index + "].isFreeGift']").val(0);
							$("input[name='tScDrpStockbillentryList[" + index + "].taxPriceEx']").val(price);
							$("input[name='tScDrpStockbillentryList[" + index + "].taxPriceEx']").trigger("change");
						}
					}else{
						$("input[name='tScDrpStockbillentryList[" + index + "].isFreeGift']").val(2);
						$("input[name='tScDrpStockbillentryList[" + index + "].taxPriceEx']").val(price);
						$("input[name='tScDrpStockbillentryList[" + index + "].taxPriceEx']").trigger("change");
					}
				}
			});
		}
	}
}

//商品批号信息
function selectInventoryInfo(index,entryName){
	var itemId = $("input[name='"+entryName+"["+index+"].itemId']").val();//商品id
	var url = 'tScIcitemController.do?goInventorySelectDialog&id='+itemId;
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
				$("input[name='"+entryName+"[" + index + "].batchNo']").val(item.batchNo);
				$("input[name='"+entryName+"[" + index + "].stockID']").val(item.stockId);
				$("input[name='"+entryName+"[" + index + "].stockName']").val(item.stockName);
				setValOldIdAnOldName($("input[name='"+entryName+"[" + index + "].stockName']"), item.stockId, item.stockName);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

//物流信息
var logisticalValue = "";
function goLogistical(){
	var id = $("#id").val();
	var tranType = $("#tranType").val();
	var checkState = $("#checkState").val();
	var load = $("#load").val();
	var url = 'tScDrpStockbillController.do?goLogisticalUpdate&fid=' + id+"&tranType="+tranType+"&logisticalValue="+logisticalValue;
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
				var freight = json.freight;
				$("#freight").val(freight);
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

//提交前校验
function checkForm(){
	var iscreditmgr = $("#iscreditmgr").val();
	if ("1" == iscreditmgr) {
		var creditamount = $("#creditamount").val();//客户信用额度
		var countAmount = $("#countAmount").val();//客户已执行金额
		var allamount = $("#allAmount").val();//应付金额
		var timePoint = $("#CFG_CONTROL_TIMEPOINT").val();
		if ("0" == timePoint) {
			if (parseFloat(allamount) + parseFloat(countAmount) > parseFloat(creditamount)) {
				var method = $("#CFG_CONTROL_METHOD").val();
				if ("1" == method) {
					tip( "金额已超出客户的信用额度，不可保存");
					return false;
				}
			}
		}
	}
}

//审核前校验
function checkAudit() {
	//信用控制
	var iscreditmgr = $("#iscreditmgr").val();
	if ("1" == iscreditmgr) {
		var creditamount = $("#creditamount").val();//客户信用额度
		var countAmount = $("#countAmount").val();//客户已执行金额
		var allamount = $("#allAmount").val();//应付金额
		var timePoint = $("#CFG_CONTROL_TIMEPOINT").val();
		if ("1" == timePoint) {
			if (parseFloat(allamount) + parseFloat(countAmount) > parseFloat(creditamount)) {
				var method = $("#CFG_CONTROL_METHOD").val();
				if ("1" == method) {
					return "金额已超出客户的信用额度，不可审核";
				}
			}
		}
	}
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
			date.setDate(date.getDate()+number);
			break;
		}
	}
	var dateStr = date.getFullYear()+"-"+(((date.getMonth()+1)<10)?"0"+(date.getMonth()+1):(date.getMonth()+1))+
		"-"+(date.getDate()<10 ? "0"+date.getDate() : date.getDate());
	return dateStr
}

//设置有效期至
function setPeriodDate(index,entryName) {
	var kfDate = $("input[name='" + entryName + "[" + index + "].date']").val();//生产日期
	var kfPeriod = $("input[name='" + entryName + "[" + index + "].kfperiod']").val();//保质期
	var kfDateType = ""//保质期类型
	if (entryName.indexOf("1") > -1) {
		kfDateType = $("select[name='" + entryName + "[" + index + "].kfDateType_']").val();
		$("input[name='" + entryName + "[" + index + "].kfDateType']").val(kfDateType);
	}else{
		kfDateType = $("select[name='" + entryName + "[" + index + "].kfDateType']").val();
	}
	var interval = "";
	if(kfDateType=="0001"){
		interval = "y";
	}else if(kfDateType == "0002"){
		interval = "m";
	}else if(kfDateType == "0003"){
		interval = "d";
	}
	var periodDate = dateAdd(interval,kfPeriod,new Date(kfDate));
	$("input[name='"+entryName+"[" + index + "].periodDate']").val(periodDate);
}
//判断是否允许增行操作
function checkAllowAddLine(index){
	var isAddLine = true;
	if(!isAddLine){
		$("#addTScOrderentryBtn\\["+index+"\\]").css("display","none");
		$("input[name='tScDrpStockbillentryList["+index+"].number']").attr("readonly","readonly");
	}
}
/*//获得基本数量和辅助数量
function getBasicQtyOrSecQtyByQtyAndCoefficient(index,entryName){
	var qty = $("input[name='"+entryName+"[" + index + "].qty']").val(); //获得输入数量
	var coefficient = $("input[name='"+entryName+"[" + index + "].coefficient']").val(); //获得换算率
	if(coefficient == ""){
		coefficient = 0;
	}
	var basicQty = parseFloat(qty*coefficient);
	var secQty = parseFloat(qty*coefficient);
	$("input[name='"+entryName+"[" + index + "].basicQty']").val(basicQty);
	$("input[name='"+entryName+"[" + index + "].secQty']").val(secQty);
}*/

/*
//获得金额
function setAmount(index){
	//计算金额
	var taxPriceEx = $("input[name='tScDrpStockbillentryList[" + index + "].taxPriceEx']").val();//获得输入的单价
	var qty = $("input[name='tScDrpStockbillentryList[" + index + "].qty']").val(); //获得输入数量
	var amount = parseFloat(taxPriceEx*qty);
	$("input[name='tScDrpStockbillentryList[" + index + "].taxAmountEx']").val(amount);

	//计算折后单价
	var discountRate = $("input[name='tScDrpStockbillentryList[" + index + "].discountRate']").val(); //获得折扣率
	var taxPrice = parseFloat(taxPriceEx*discountRate/100);
	$("input[name='tScDrpStockbillentryList[" + index + "].taxPrice']").val(taxPrice);

	//计算折后金额
	var inTaxAmount = parseFloat(taxPriceEx*discountRate/100*qty);
	$("input[name='tScDrpStockbillentryList[" + index + "].inTaxAmount']").val(inTaxAmount);

	//计算不含税折后单价
	var taxRate = $("input[name='tScDrpStockbillentryList[" + index + "].taxRate']").val(); //获得税率
	var discountPrice = parseFloat(taxPriceEx/(1+taxRate/100)*discountRate/100);
	$("input[name='tScDrpStockbillentryList[" + index + "].discountPrice']").val(discountPrice);

	//计算不含税折后金额
	var discountAmount = parseFloat(taxPriceEx/(1+taxRate/100)*discountRate/100*qty);
	$("input[name='tScDrpStockbillentryList[" + index + "].discountAmount']").val(discountAmount);

	//计算不含税单价
	var price = parseFloat(taxPriceEx/(1+taxRate/100));
	$("input[name='tScDrpStockbillentryList[" + index + "].price']").val(price);

	//计算不含税金额
	var amount = parseFloat(taxPriceEx/(1+taxRate/100));
	$("input[name='tScDrpStockbillentryList[" + index + "].amount']").val(amount);

	//计算税额
	var taxAmount = parseFloat((taxPrice-discountPrice)*qty);
	$("input[name='tScDrpStockbillentryList[" + index + "].taxAmount']").val(taxAmount);
}

function setAmountBytaxAmountEx(index,entryName,number){
	var taxAmountEx = $("input[name='"+entryName+"[" + index + "].taxAmountEx']").val();//获得输入的金额
	var taxPriceEx = $("input[name='"+entryName+"[" + index + "].taxPriceEx']").val();//获得输入的单价
	var qty = $("input[name='"+entryName+"[" + index + "].qty']").val(); //获得输入数量
	//计算单价
	if(taxAmountEx!="" && taxAmountEx!=0){
		//计算单价
		var taxPriceEx = parseFloat(taxAmountEx/qty).toFixed(number);
		$("input[name='"+entryName+"[" + index + "].taxPriceEx']").val(taxPriceEx);
	}else{
		var amount = parseFloat(taxPriceEx*qty).toFixed(number);
		$("input[name='"+entryName+"[" + index + "].taxAmountEx']").val(amount);
	}

	//计算折后单价
	var discountRate = $("input[name='"+entryName+"[" + index + "].discountRate']").val(); //获得折扣率
	var taxPrice = parseFloat(taxPriceEx*discountRate/100).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].taxPrice']").val(taxPrice);

	//计算折后金额
	var inTaxAmount = parseFloat(taxPriceEx*discountRate/100*qty).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].inTaxAmount']").val(inTaxAmount);

	//计算不含税折后单价
	var taxRate = $("input[name='"+entryName+"[" + index + "].taxRate']").val(); //获得税率
	var discountPrice = parseFloat(taxPriceEx/(1+taxRate/100)*discountRate/100).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].discountPrice']").val(discountPrice);

	//计算不含税折后金额
	var discountAmount = parseFloat(taxPriceEx/(1+taxRate/100)*discountRate/100*qty).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].discountAmount']").val(discountAmount);

	//计算不含税单价
	var price = parseFloat(taxPriceEx/(1+taxRate/100)).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].price']").val(price);

	//计算不含税金额
	var amount = parseFloat(taxPriceEx/(1+taxRate/100)).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].amount']").val(amount);

	//计算税额
	var taxAmount = parseFloat((taxPrice-discountPrice)*qty).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].taxAmount']").val(taxAmount);
}
*/

//汇总折扣金额
function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody1 = $("#tScDrpStockbillentry_table");
	var length = $tbody1[0].rows.length;
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScDrpStockbillentryList[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScDrpStockbillentryList[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScDrpStockbillentryList[" + i + "].qty']").val();//数量
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
}
//赠品标记
function setPrice(obj){
	var v = $(obj).val();
	if(v == 1){//为是的话处理
		$(obj).parent().parent().find("input[name$='.taxPriceEx']").attr("readonly","readonly").css("background-color","rgb(226, 226, 226)").val(0);//单价清0
		$(obj).parent().parent().find("input[name$='.taxAmountEx']").attr("readonly","readonly").css("background-color","rgb(226, 226, 226)").val(0);//金额清0
		$(obj).parent().parent().find("input[name$='.taxPrice']").val(0);//折后单价
		$(obj).parent().parent().find("input[name$='.inTaxAmount']").attr("readonly","readonly").css("background-color","rgb(226, 226, 226)").val(0);//折后金额
		$(obj).parent().parent().find("input[name$='.taxAmount']").val(0);//税额
	}else{//为否的处理
		$(obj).parent().parent().find("input[name$='.taxPriceEx']").removeAttr("readonly").css("background-color","");
		$(obj).parent().parent().find("input[name$='.taxAmountEx']").removeAttr("readonly").css("background-color","");
		$(obj).parent().parent().find("input[name$='.inTaxAmount']").removeAttr("readonly").css("background-color","");
	}
}