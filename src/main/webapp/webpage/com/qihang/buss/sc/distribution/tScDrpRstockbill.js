$(function(){
	setDefaultDate();//设置默认时间
	$('#itemName').keypress(function (e) {//经销商
		if (e.keyCode == 13) {
			selectitemNameDialog();
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
	});
	$("#rStockName").keypress(function (e) {//仓库
		if (e.keyCode == 13) {
			selectStockNameDialog();
		}
	});
	//添加选单下拉框
	addButton();

	var CFG_OTHER = $("#CFG_OTHER").val();
	var CFG_MONEY = $("#CFG_MONEY").val();
	var weight = $("#weight").val() || 0;
	var allAmount = $("#allAmount").val() || 0;
	var freight = $("#freight").val() || 0;
	$("#weight").val(parseFloat(weight).toFixed(CFG_OTHER));
	$("#allAmount").val(parseFloat(allAmount).toFixed(CFG_MONEY));
	$("#freight").val(parseFloat(freight).toFixed(CFG_MONEY));

	var delearType = $("#delearType").val();
	if(delearType == 'ADealer' || delearType == ''){
		$("#itemName").removeAttr("datatype");
		$("#itemName").next().text("");
		$("#itemName").unbind();
	}
});

//初始化下标
function resetTrNum(tableId,index,indexName) {
	$tbody = $("#" + tableId + "");
	$tbody.find('>tr').each(function (i) {
		$(':input,span, select,button,a', this).each(function () {
			var $this = $(this), name = $this.attr('name'), id = $this.attr('id'), onclick_str = $this.attr('onclick'), str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str = $this.attr('onblur');
			var comboboName = $this.attr("comboname");
			var change_ = $this.attr("onchange");
			if (null != comboboName) {
				console.log(comboboName);
				var s = comboboName.indexOf("[");
				var e = comboboName.indexOf("]");
				var new_name = comboboName.substring(s + 1, e);
				new_name = "[" + new_name + "]"
				$this.attr("comboname", comboboName.replace(new_name, "[" + i + "]"));
			}
			if (name != null) {
				if (name.indexOf("#index#") >= 0) {
					$this.attr("name", name.replace('#index#', i));
				} else {
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s + 1, e);
					new_name = "[" + new_name + "]"
					$this.attr("name", name.replace(new_name, "[" + i + "]"));
				}
				if (name.indexOf("newPrice") > -1) {
					$this.attr("onchange", "changePrice("+i+")");//onchange="changePrice(0)"
				}
				if (name.indexOf("qty") > -1) {
					$this.attr("onblur", "changePrice("+i+")");//onchange="changePrice(0)"
				}
				/*if (name.indexOf("taxPriceEx") > -1) {
					$this.attr("onblur", "setAmountBytaxAmountEx("+i+",'tScDrpRstockbillentryList',3)");//onchange="changePrice(0)"
				}
				if (name.indexOf("taxAmountEx") > -1) {
					$this.attr("onblur", "setAmountBytaxAmountEx("+i+",'tScDrpRstockbillentryList',3)");//onchange="changePrice(0)"
				}*/
			}
			if (id != null) {
				if (id.indexOf("#index#") >= 0) {
					$this.attr("id", id.replace('#index#', i));
				} else {
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s + 1, e);
					new_id = "[" + new_id + "]"
					$this.attr("id", id.replace(new_id, "[" + i + "]"));
				}
			}
			if (onclick_str != null && onclick_str != "WdatePicker()") {
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {
					if (name.indexOf("begDate") < 0) {
						var s = onclick_str.indexOf("(");
						var e = onclick_str.indexOf(")");
						var new_onclick = onclick_str.substring(s + 1, e);
						$this.attr("onclick", onclick_str.replace(new_onclick, i));
					}
					if (name.indexOf("endDate") < 0) {
						var s = onclick_str.indexOf("(");
						var e = onclick_str.indexOf(")");
						var new_onclick = onclick_str.substring(s + 1, e);
						$this.attr("onclick", onclick_str.replace(new_onclick, i));
					}
				}
			}
			if (str_press != null) {
				if (str_press.indexOf("#index#") >= 0) {
					$this.attr("onkeypress", str_press.replace(/#index#/g, i));
				} else {
					var s = str_press.indexOf("(");
					var e = str_press.indexOf(",");
					var new_key = str_press.substring(s + 1, e);
					$this.attr("onkeypress", str_press.replace(new_key, i));
				}
			}
		});
		setFunctionInfo(i,indexName);
		$("input[name='" + indexName + "[" + i + "].indexNumber']").val(i + 1);
		$(this).find('div[name=\'xh\']').html(i + 1);
	});
	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1)) {
			$('#tScDrpRstockbillentryList\\[' + i + '\\]\\.unitId').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			});
			/*$('#tScDrpRstockbillentryList\\[' + i + '\\]\\.basicUnitId').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false,
				disabled:true
			});
			$('#tScDrpRstockbillentryList\\[' + i + '\\]\\.secUnitId').combobox({
				width:'64',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				disabled:true
			});*/
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

//选择经销商
function selectitemNameDialog() {
	var tempName = $("#itemName").val();
	var url = 'tScDrpOrderController.do?goSelectToOrderDialog&name=' + tempName;
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
				$("input[name='itemName']").val(item.name);
				$("input[name='itemId']").val(item.id);
				$("#iscreditmgr").val(iscreditmgr);
				$("#creditamount").val(creditamount);
				$("#countAmount").val(countAmount);
				setValOldIdAnOldName($("#itemName"), item.id, item.name);
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

//选择经办人
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
				$("#empId").val(item.id);
				if(item.deptIdName){
					$("#deptName").val(item.deptIdName);
					$("#deptId").val(item.deptID);
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

//选择仓库
function selectStockNameDialog() {
	var tempName = $("#rStockName").val();
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
				$("input[name='rStockName']").val(item.name);
				$("input[name='rStockId']").val(item.id);
				//$("input[name='tScOrderentryList[#index#].stockID']").val(item.id);//设置模板从表头取值
				//$("input[name='tScOrderentryList[#index#].stockName']").val(item.name);
				//setValOldIdAnOldName($("input[name='tScOrderentryList[#index#].stockName']"), item.id, item.name)
				//$('#add_tScOrderentry_table').find("input[name$=stockName]").val(item.name);//设置从表的值
				//$('#add_tScOrderentry_table').find("input[name$=stockID]").val(item.id);
				//setValOldIdAnOldName($('#add_tScOrderentry_table').find("input[name$=stockName]"), item.id, item.name)
				setValOldIdAnOldName($('#rStockName'), item.id, item.name)
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
	var number = $("input[name='tScDrpRstockbillentryList["+index+"].number']").val();
	var url = 'tScIcitemController.do?goInventorySelectDialog&number=' + number+'&isZero=0';
	var stockId = $("#rStockId").val();
	if(stockId){
		url = url+"&stockID="+stockId;
	}
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
				var items = iframe.getMoreSelectionData();
				if (items != "") {
					var nextTrLength =  $('input[name="tScDrpRstockbillentryList['+index+'].indexNumber"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					var newLength = itemsLength-nextTrLength-1;
					for (var j = 0; j < newLength; j++) {
						clickAddTScDrpRstockbillentryBtn(index);
					}
					var sonId = $("#sonId").val();//当前分支机构
					$.each(items,function(i,item){
						var rowindex = parseInt(index) + i;
						var id = item.itemId;//id
						var number = item.itemNo;//编码
						var name = item.itemName;//名称
						/*var barCode = item.barCode;*/
						var taxRateOut = item.taxRateOut;//商品销项税
						var stockSonId = item.stockSonId;//商品默认仓库分支机构id；
						var model = item.model;//规格
						var kfPeriod = item.kfPeriod;//保质期
						var kfdateType = item.kfDateType1;//保质期类型
						var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
						var batchManager = item.batchManager;//是否批号管理;
						var weight = item.weight|0;//重量
						var basicQty = item.basicQty;//库存基本数量
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
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']").val(number);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].itemId']").val(id);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].model']").val(model);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].name']").val(name);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].barCode']").val(barCode);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].coefficient']").val(coefficient);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
								$('input[name="tScDrpRstockbillentryList[' + rowIndex + '].itemWeight"]').val(item.weight);//商品重量
								$('input[name="tScDrpRstockbillentryList[' + rowIndex + '].itemWeight"]:first').trigger("change");
								$('input[name="tScDrpRstockbillentryList[' + rowIndex + '].taxRate"]').val(taxRateOut);
								$('input[name="tScDrpRstockbillentryList[' + rowIndex + '].taxRate"]').trigger("change");

								var checkId = $("input[name='tScDrpRstockbillentryList[" + rowIndex + "].stockId']").val();
								if(!checkId) {
									if(stockSonId == sonId) {
										$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].stockId']").val(stockId);
										$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].stockName']").val(stockName);
										$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].stockName']").attr("readonly", "readonly");
										$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].stockName']").css("background-color", "rgb(226,226,226)");
									}
								}
								setValOldIdAnOldName($('input[name="tScDrpRstockbillentryList[' + rowIndex + '].stockName"]'),stockId,stockName);

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.unitId').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.unitId').combobox({
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
														$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].barCode']").val(barCode);
														$('input[name="tScDrpRstockbillentryList['+rowIndex+'].coefficient"]').val(coefficient);//换算率
														$('input[name="tScDrpRstockbillentryList['+rowIndex+'].coefficient"]').trigger("change");
													}
												}
											});
										}
									}
								});

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.unitId').combobox("setValue", unitId);
								setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.basicUnitId').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.basicUnitId').combobox("setValue", basicUnitID);
								setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.secUnitId').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.secUnitId').combobox("setValue", secUnitId);
								setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								if ("N" == batchManager) {
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").attr("readonly", "readonly");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(226,226,226)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").removeAttr("datatype");
								}else{
									/*$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").removeAttr("disabled");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").css("background-color","rgb(255,255,255)");*/
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").val(batchNo);
									/*$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").attr("datatype","*");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").attr("onkeypress","keyDownInfo('"+rowIndex+"','batchNo')");*/
								}

								if("Y"== iSKFPeriod) {
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").val(kfPeriod);
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").val(kfdateType);
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").attr("disabled","disabled");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType']").val(kfdateType);
									var kfdate = $("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").val();
									var interval = "";
									if (kfdateType == "0001") {
										interval = "y";
									} else if (kfdateType == "0002") {
										interval = "m";
									} else if (kfdateType == "0003") {
										interval = "d";
									}
									var periodDate = dateAdd(interval, kfPeriod, new Date(kfdate));
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].periodDate']").val(periodDate);
								}else{
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").attr("readonly","readonly");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").unbind("onClick");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").attr("readonly","readonly");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").css("background-color","rgb(226,226,226)");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").attr("disabled","disabled");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").val("");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").val("");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType']").val("");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").val("");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].periodDate']").val("");
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

//数量发生改变时，取报价单单价
function changePrice(index){
	var customerId = $("#itemId").val();
	var qty = $("input[name='tScDrpRstockbillentryList["+index+"].qty']").val();
	var itemId = $("input[name='tScDrpRstockbillentryList["+index+"].itemId']").val();//商品
	var unitId = $("#tScDrpRstockbillentryList\\["+index+"\\]\\.unitId").combobox("getValue");
	var itemNo = $("input[name='tScDrpRstockbillentryList["+index+"].number']").val();//商品编号
	var tranType = $("#tranType").val();
	if(itemId && qty && customerId && unitId){
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
					$("input[name='tScDrpRstockbillentryList["+index+"].salesID']").val(salesID);
					$("input[name='tScDrpRstockbillentryList["+index+"].salesName']").val(salesName);
					if("1" == salesType){
						var isFreeGift =  $("input[name='tScDrpRstockbillentryList["+index+"].isFreeGift']").val();
						if(isFreeGift == "1"){
							var rowLength = rowInfo[index];
							for(var i = 0 ; i < rowLength ; i++){
								clickDelTScOrderentryBtn((index + 1));
							}
						}
						$("input[name='tScDrpRstockbillentryList["+index+"].isFreeGift']").val(1);
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
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemId']").val(itemId);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemNumber']").val(itemNo);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemNumber']").attr("disabled", "disabled");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemNumber']").css("background-color", "rgb(226, 226, 226)");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemName']").val(itemName);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemModel']").val(model);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemBarcode']").val(barCode);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].salesName']").val(salesName);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].coefficient']").val(coefficient);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].basicQty']").val(basicQty);
							setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + freeIndex + "].itemNumber']"),itemId,itemNo);
							//数量
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].qty']").val(qty);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].qty']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].qty']").css("background-color", "rgb(226, 226, 226)");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].qty']").trigger("change");
							//单价
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxPriceEx']").val(0);
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxPriceEx']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxPriceEx']").trigger("change");
							//金额
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxAmountEx']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
							//折扣率
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].discountRate']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
							//折后金额
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].inTaxAmount']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
							//税率
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxRate']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
							//赠品标记
							$("select[name='tScDrpRstockbillentryList[" + freeIndex + "].freeGifts_']").val(1);
							$("select[name='tScDrpRstockbillentryList[" + freeIndex + "].freeGifts_']").attr("disabled", "disabled");
							$("select[name='tScDrpRstockbillentryList[" + freeIndex + "].freeGifts_']").trigger("change");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].freeGifts']").val(1);
							//交货日期
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].fetchDate']").attr("readonly", "readonly");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].fetchDate']").removeAttr("onclick");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].fetchDate']").css("background-color", "rgb(226, 226, 226)");
							//仓库
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].stockName']").attr("disabled", "disabled");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].stockName']").css("background-color", "rgb(226, 226, 226)");
							//单位
							$('#tScDrpRstockbillentryList\\[' + freeIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$('#tScDrpRstockbillentryList\\[' + freeIndex + '\\]\\.unitID').combobox("setValue",unitId);
							$('#tScDrpRstockbillentryList\\[' + freeIndex + '\\]\\.unitID').combobox("disable");
							$("input[name='tScDrpRstockbillentryList[" + freeIndex + "].isFreeGift']").val(1);
						}
					}else{
						var isFreeGift =  $("input[name='tScDrpRstockbillentryList["+index+"].isFreeGift']").val();
						if(isFreeGift == "1"){
							var rowLength = rowInfo[index];
							for(var i = 0 ; i < rowLength ; i++){
								clickDelTScOrderentryBtn((index + 1));
							}
						}
						$("input[name='tScDrpRstockbillentryList["+index+"].taxPriceEx']").val(price);
						$("input[name='tScDrpRstockbillentryList["+index+"].taxPriceEx']").trigger("change");
						$("input[name='tScDrpRstockbillentryList["+index+"].isFreeGift']").val(0);
					}
					//$("input[name='tScDrpRstockbillentryList["+index+"].taxPriceEx']").val(price);
					//$("input[name='tScDrpRstockbillentryList["+index+"].taxPriceEx']").trigger("change");
				}else{
					$("input[name='tScDrpRstockbillentryList["+index+"].isFreeGift']").val(2);
					$("input[name='tScDrpRstockbillentryList["+index+"].freeGifts_']").val(2);
					$("input[name='tScDrpRstockbillentryList["+index+"].taxPriceEx']").val(price);
					$("input[name='tScDrpRstockbillentryList["+index+"].taxPriceEx']").trigger("change");
				}
			}
		});
	}
}
/*//选择商品
function selectIcitemDialog(index){
	var number = $("input[name='tScDrpRstockbillentryList["+index+"].number']").val();
	var url = 'tScIcitemController.do?goSelectDialog&number=' + number;
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
				var items = iframe.getMoreSelectionData();
				if (items != "") {
					var nextTrLength =  $('input[name="tScDrpRstockbillentryList['+index+'].number"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						for (var j = 0; j < itemsLength - 1; j++) {
							clickAddTScDrpRstockbillentryBtn(index);
						}
					}
					$.each(items,function(i,item){
						var rowindex = parseInt(index) + i;
						var id = item.id;//id
						var number = item.number;//编码
						var name = item.name;//名称
						var barCode = item.barCode;
						var model = item.model;//规格
						var kfPeriod = item.kFPeriod;//保质期
						var kfdateType = item.kFDateType;//保质期类型
						var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
						var batchManager = item.batchManager;//是否批号管理;
						var stockId = $("input[name='tScDrpRstockbillentryList[" + i + "].stockId']").val();//仓库id
						var batchNo = $("input[name='tScDrpRstockbillentryList[" + i + "].batchNo']").val();//批号

						var url = "tScIcitemController.do?getItemInfo&id=" + item.id+"&rowIndex="+rowindex+"&tranType=1504&stockId="+stockId+"&batchNo="+batchNo;
						$.ajax({
							type: 'POST',
							url: url,
							dataType: 'json',
							cache: false,
							success: function (data) {
								var attr = data.attributes;
								var barCode = attr.barCode;
								var unitId = attr.unitID;
								var basicUnitID  = attr.basicUnitID;
								var secUnitId = attr.secUnitId;
								var coefficient = attr.coefficient;
								var rowIndex = attr.rowIndex;

								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']").val(number);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].itemId']").val(id);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].model']").val(model);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].name']").val(name);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].barCode']").val(barCode);
								/!*$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].unitId']").val(basicUnitID);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].basicUnitId']").val(basicUnitID);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].secUnitId']").val(secUnitId);*!/
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].coefficient']").val(coefficient);
								$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].secCoefficient']").val(coefficient);

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.unitId').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.unitId').combobox({
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
														$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].barCode']").val(barCode);
													}
												}
											});
										}
									}
								});
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.unitId').combobox("setValue", unitId);
								setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.basicUnitId').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.basicUnitId').combobox({
									onChange: function (newV, oldV) {
										if (oldV != newV) {
											var changeUrl = "tScIcitemController.do?getChangeInfo&id="+item.id+"&unitId="+newV;
											$.ajax({
												type: 'POST',
												url: changeUrl,
												dataType: 'json',
												cache: false,
												success: function (data) {
													if(data.success == true){
														var attributes = data.attributes;
														var barCode = attributes.barCode;
														$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].barCode']").val(barCode);
													}
												}
											});
										}
									},
									disabled:true
								});
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.basicUnitId').combobox("setValue", unitId);
								setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.secUnitId').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.secUnitId').combobox({
									onChange: function (newV, oldV) {
										if (oldV != newV) {
											var changeUrl = "tScIcitemController.do?getChangeInfo&id="+item.id+"&unitId="+newV;
											$.ajax({
												type: 'POST',
												url: changeUrl,
												dataType: 'json',
												cache: false,
												success: function (data) {
													if(data.success == true){
														var attributes = data.attributes;
														var barCode = attributes.barCode;
														$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].barCode']").val(barCode);
													}
												}
											});
										}
									},disabled:true
								});
								$('#tScDrpRstockbillentryList\\[' + rowIndex + '\\]\\.secUnitId').combobox("setValue", unitId);
								setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + rowIndex + "].number']"), item.id,item.number);

								if ("N" == batchManager) {
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").attr("readonly", "readonly");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(226,226,226)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").removeAttr("datatype");
								}else{
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").removeAttr("disabled");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").css("background-color","rgb(255,255,255)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").attr("datatype","*");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].batchNo']").attr("onkeypress","keyDownInfo('"+rowIndex+"','batchNo')");
								}

								if("Y"== iSKFPeriod) {
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").val(kfPeriod);
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").val(kfdateType);
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType_']").attr("disabled","disabled");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType']").val(kfdateType);

									var kfdate = $("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").val();
									var interval = "";
									if (kfdateType == "0001") {
										interval = "y";
									} else if (kfdateType == "0002") {
										interval = "m";
									} else if (kfdateType == "0003") {
										interval = "d";
									}
									var periodDate = dateAdd(interval, kfPeriod, new Date(kfdate));
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].periodDate']").val(periodDate);
								}else{
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").attr("readonly","readonly");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").unbind("onClick");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").attr("readonly","readonly");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").css("background-color","rgb(226,226,226)");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType']").attr("disabled","disabled");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType']").css("background-color","rgb(226,226,226)");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDate']").val("");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].kfPeriod']").val("");
									$("select[name='tScDrpRstockbillentryList[" + rowIndex + "].kfDateType']").val("");
									$("input[name='tScDrpRstockbillentryList[" + rowIndex + "].periodDate']").val("");
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
}*/

//选择仓库
function selectStockDialog(index) {
	var tempName = $("input[name='tScDrpRstockbillentryList[" + index + "].stockName']").val();
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
				//$("input[name='stockName']").val(item.name);
				$("input[name='tScDrpRstockbillentryList[" + index + "].stockName']").val(item.name);
				//$("input[name='stockId']").val(item.id);
				$("input[name='tScDrpRstockbillentryList[" + index + "].stockId']").val(item.id);
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
				$("input[name='"+entryName+"[" + index + "].stockId']").val(item.stockId);
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

/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

//设置单据日期默认为当前日期
function setDefaultDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
	var date = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	//var billDate = $("#date").val();
	var id = $("#id").val();
	if(!id){
		$("#date").val(year+"-"+month+"-"+date);
		$("input[name='tScDrpRstockbillentryList[#index#].kfDate']").val(year+"-"+month+"-"+date);
		$("input[name='tScDrpRstockbillentryList[#index#].kfDate']").val(year+"-"+month+"-"+date);
	}else{
		$("input[name='tScDrpRstockbillentryList[#index#].kfDate']").val(year+"-"+month+"-"+date);
		$("input[name='tScDrpRstockbillentryList[#index#].kfDate']").val(year+"-"+month+"-"+date);
	}
}

function   dateAdd(interval,number,date) {
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
	switch(interval){
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

//获得基本数量和辅助数量
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
}

//获得金额
function setAmount(index){
	//计算金额
	var taxPriceEx = $("input[name='tScDrpRstockbillentryList[" + index + "].taxPriceEx']").val();//获得输入的单价
	var qty = $("input[name='tScDrpRstockbillentryList[" + index + "].qty']").val(); //获得输入数量
	var amount = parseFloat(taxPriceEx*qty);
	$("input[name='tScDrpRstockbillentryList[" + index + "].taxAmountEx']").val(amount);

	//计算折后单价
	var discountRate = $("input[name='tScDrpRstockbillentryList[" + index + "].discountRate']").val(); //获得折扣率
	var taxPrice = parseFloat(taxPriceEx*discountRate/100);
	$("input[name='tScDrpRstockbillentryList[" + index + "].taxPrice']").val(taxPrice);

	//计算折后金额
	var inTaxAmount = parseFloat(taxPriceEx*discountRate/100*qty);
	$("input[name='tScDrpRstockbillentryList[" + index + "].inTaxAmount']").val(inTaxAmount);

	//计算不含税折后单价
	var taxRate = $("input[name='tScDrpRstockbillentryList[" + index + "].taxRate']").val(); //获得税率
	var discountPrice = parseFloat(taxPriceEx/(1+taxRate/100)*discountRate/100);
	$("input[name='tScDrpRstockbillentryList[" + index + "].discountPrice']").val(discountPrice);

	//计算不含税折后金额
	var discountAmount = parseFloat(taxPriceEx/(1+taxRate/100)*discountRate/100*qty);
	$("input[name='tScDrpRstockbillentryList[" + index + "].discountAmount']").val(discountAmount);

	//计算不含税单价
	var price = parseFloat(taxPriceEx/(1+taxRate/100));
	$("input[name='tScDrpRstockbillentryList[" + index + "].price']").val(price);

	//计算不含税金额
	var amount = parseFloat(taxPriceEx/(1+taxRate/100));
	$("input[name='tScDrpRstockbillentryList[" + index + "].amount']").val(amount);

	//计算税额
	var taxAmount = parseFloat((taxPrice-discountPrice)*qty);
	$("input[name='tScDrpRstockbillentryList[" + index + "].taxAmount']").val(taxAmount);
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
//添加按钮
function addButton(){
	var button = document.createElement("input");
	button.id="chooseBill";
	button.type="button";
	button.class="button";
	button.value="选单"
	var buttonDiv = document.getElementsByClassName("ui_buttons");
	var load = $("#load").val()
	if("detail" != load) {
		//buttonDiv[0].appendChild(button);
		var select = document.createElement("select");
		select.id="chooseType";
		select.setAttribute("onchange","selectBillDialog()");
		select.setAttribute("style","margin-left:10px;");
		var option2 = document.createElement("option");
		option2.value="";
		option2.innerText="选单";
		option2.select = "select";
		select.appendChild(option2);

		var option1 = document.createElement("option");
		option1.value="52";
		option1.innerText="分销发货单";
		select.appendChild(option1);
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
function selectBillDialog(){
	var itemId = $("#itemId").val();
	if(!itemId){
		tip("请选择经销商后再进行选单操作");
		$("#chooseType").val("");
		return;
	}
	var sonId = $("#sonId").val();
	var tranType = 1505;
	var url = "tScDrpStockbillController.do?goSelectDialog&dealerID="+itemId+"&tranType="+tranType+"&sonId="+sonId;
	var width = 800;
	var height = 500;
	var title = "分销发货单";
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
				var itemList = iframe.getSelectRows()//.getSelectionData();
				var item = itemList[itemList.length-1];
				/*给主表表单赋值*/
				$("#classIdSrc").val(item.tranType);//源单类型
				$("#interIdSrc").val(item.id);//源单主键
				$("#billNoSrc").val(item.billNo);//源单编号

				$("#itemName").attr("disabled","disabled") //当选择经销商后 禁用输入框

				$("#deptId").val(item.deptID);
				$("#deptName").val(item.deptName);
				setValOldIdAnOldName($("#deptName"), item.deptID, item.deptName);
				$("#empId").val(item.empID);
				$("#empName").val(item.empName);
				setValOldIdAnOldName($("#empName"), item.empID, item.empName);
				/*$("#contact").val(item.contact);
				$("#mobilePhone").val(item.mobilePhone);
				$("#phone").val(item.phone);
				$("#fax").val(item.fax);
				$("#address").val(item.address);*/

				var length = $("#add_tScDrpRstockbillentry_table").find(">tr").length;
				length--;
				var id = $("input[name='tScDrpRstockbillentryList["+length+"].itemId']").val();
				var dataLength = itemList.length;
				if(!id){
					dataLength--;
				}
				for(var k=0 ; k < dataLength ; k++){
					clickAddTScDrpRstockbillentryBtn(length);
				}
				if(id){
					length++;
				}
				for(var i=0 ; i<itemList.length ;i++) {
					var row = itemList[i];
					for(var k in row){
						if(k=="unitId"){
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.unitId').combobox("reload","tScIcitemController.do?loadItemUnit&id=" + row.itemId);
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.unitId').combobox("setValue", row[k]);
							//$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.unitId').combobox("disable");
						}else if(k=="basicUnitId"){
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.basicUnitId').combobox("reload","tScIcitemController.do?loadItemUnit&id=" + row.itemId);
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.basicUnitId').combobox("setValue", row[k]);
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.basicUnitId').combobox("disable");
						}else if(k=="secUnitId"){
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.secUnitId').combobox("reload","tScIcitemController.do?loadItemUnit&id=" + row.itemId);
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.secUnitId').combobox("setValue", row[k]);
							$('#tScDrpRstockbillentryList\\[' + length+ '\\]\\.secUnitId').combobox("disable");
						} else if( k=="kfDateType"){
							if("N"==row.isKfPeriod) {
								$("select[name='tScDrpRstockbillentryList[" + length + "].kfDateType_']").attr("disabled","disabled");
								$("select[name='tScDrpRstockbillentryList[" + length + "].kfDateType_']").css("background-color","rgb(226,226,226)");
							}else{
								$("select[name='tScDrpRstockbillentryList[" + length + "].kfDateType_']").val(row[k]);
								$("select[name='tScDrpRstockbillentryList[" + length + "].kfDateType']").val(row[k]);
							}
						}else if(k=="kfPeriod"){
							if("N"==row.isKfPeriod) {
								//if(k.indexOf("Date") > -1){
								//	$("input[name='tScDrpRstockbillentryList[" + length + "]." + k + "']").attr("readonly","readonly");
								//	$("input[name='tScDrpRstockbillentryList[" + length + "]." + k + "']").css("background-color","rgb(226,226,226)");
								//	$("input[name='tScDrpRstockbillentryList[" + length + "]." + k + "']").attr("onClick","");
								//	$("input[name='tScDrpRstockbillentryList[" + length + "]." + k + "']").val("");
								//}else{
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfPeriod']").attr("readonly","readonly");
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfPeriod']").css("background-color","rgb(226,226,226)");
								//}
							}else{
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfPeriod']").val(row[k]);
							}
						}else if(k == 'kfDate' ){
							if("N"== row.isKfPeriod ){
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfDate']").attr("readonly","readonly");
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfDate']").css("background-color","rgb(226,226,226)");
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfDate']").attr("onClick","");
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfDate']").val("");

								$("input[name='tScDrpRstockbillentryList[" + length + "].periodDate']").attr("readonly","readonly");
								$("input[name='tScDrpRstockbillentryList[" + length + "].periodDate']").css("background-color","rgb(226,226,226)");
								$("input[name='tScDrpRstockbillentryList[" + length + "].periodDate']").attr("onClick","");
								$("input[name='tScDrpRstockbillentryList[" + length + "].periodDate']").val("");
							}else {
								var date = new Date(row['kfDate']);
								var year = date.getFullYear();
								var mongth = ((date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1));
								var dates = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
								var newDate = year+"-"+mongth+"-"+dates;
								$("input[name='tScDrpRstockbillentryList[" + length + "].kfDate']").val(newDate);
							}
						}else if(k=="qty"){
							var stockQty = row.commitQty;//退货数量
							var qty = row[k];
							var basicQty = row["basicQty"];
							var coe = row["coefficient"];
							qty = ((parseFloat(basicQty)-parseFloat(stockQty))/coe).toFixed(CFG_NUMBER);
							$("input[name='tScDrpRstockbillentryList[" +length + "]." + k + "']").val(qty);
							//$("input[name='tScDrpRstockbillentryList[" +length + "].billQty']").val(row[k]);
						}else {
							$("input[name='tScDrpRstockbillentryList[" +length + "]." + k + "']").val(row[k]);
						}
						/*if(k == "entryItemNo"){
							//$("input[name='tScDrpRstockbillentryList[" + length + "].itemNo']").attr("readonly","readonly");
							//$("input[name='tScDrpRstockbillentryList[" + length + "].itemNo']").attr("onclick","this.blur()");
						}*/
					}
					if("Y"==row.isKfPeriod) {
						setPeriodDate(length,"tScDrpRstockbillentryList");
					}
					if("N"==row.batchManager){
						$("input[name='tScDrpRstockbillentryList[" + length + "].batchNo']").attr("readonly","readonly");
						$("input[name='tScDrpRstockbillentryList[" + length + "].batchNo']").css("background-color","rgb(226,226,226)");
						$("input[name='tScDrpRstockbillentryList[" + length + "].batchNo']").removeAttr("datatype");
					}else{
						$("input[name='tScDrpRstockbillentryList[" + length + "].batchNo']").attr("readonly","readonly");
						$("input[name='tScDrpRstockbillentryList[" + length + "].batchNo']").css("background-color","rgb(226,226,226)");

						$("input[name='tScDrpRstockbillentryList[" + length + "].stockName']").attr("disabled","disabled");
						$("input[name='tScDrpRstockbillentryList[" + length + "].stockName']").css("background-color","rgb(226,226,226)");
					}


					$("input[name='tScDrpRstockbillentryList["+length+"].itemId']").val(row.itemId);
					$("input[name='tScDrpRstockbillentryList["+length+"].number']").val(row.number);
					$("input[name='tScDrpRstockbillentryList["+length+"].name']").val(row.name);

					$("input[name='tScDrpRstockbillentryList["+length+"].stockId']").val(row.stockId);
					$("input[name='tScDrpRstockbillentryList["+length+"].stockName']").val(row.stockName);
					setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList["+length+"].stockName']"),row.stockId,row.stockName);


					var basicCoefficient = $("input[name='tScDrpRstockbillentryList[" + length + "].basicCoefficient']").val();
					var discountPrice = $("input[name='tScDrpRstockbillentryList[" + length + "].discountPrice']").val();
					var costPrice = parseFloat(discountPrice)/parseFloat(basicCoefficient)
					var qty = $("input[name='tScDrpRstockbillentryList[" + length + "].qty']").val();
					$("input[name='tScDrpRstockbillentryList[" + length + "].costPrice']").val(costPrice.toFixed(2));
					$("input[name='tScDrpRstockbillentryList[" + length + "].costAmount']").val((costPrice.toFixed(2)*qty).toFixed(2))
					//$("input[name='tScDrpRstockbillentryList[" + i + "].idSrc']").val(item.id);
					//$("input[name='tScDrpRstockbillentryList[" + i + "].billNoSrc']").val(item.billNo);
					$("input[name='tScDrpRstockbillentryList[" + length + "].idSrc']").val(row.fidSrc);
					$("input[name='tScDrpRstockbillentryList[" + length + "].billNoSrc']").val(row.billNo);
					$("input[name='tScDrpRstockbillentryList[" + length + "].classIDSrc']").val(row.tranType);
					$("input[name='tScDrpRstockbillentryList[" + length + "].interIdSrc']").val(row.interIdSrc);
					$("input[name='tScDrpRstockbillentryList[" + length + "].billNoOrder']").val(row.billNoOrder);
					$("input[name='tScDrpRstockbillentryList[" + length + "].idOrder']").val(row.fidOrder);
					$("input[name='tScDrpRstockbillentryList[" + length + "].interIdOrder']").val(row.interIdOrder);
					setFunctionInfo(length,"tScDrpRstockbillentryList");
					$("input[name='tScDrpRstockbillentryList[" + length + "].qty']").trigger("change");
					$("input[name='tScDrpRstockbillentryList[" + length + "].taxPriceEx']").trigger("change");
					$("input[name='tScDrpRstockbillentryList[" + length + "].taxAmountEx']").trigger("change");

					var itemNo = $("input[name='tScDrpRstockbillentryList[" + length + "].number']").val();
					var itemId = $("input[name='tScDrpRstockbillentryList[" + length + "].itemId']").val();
					setValOldIdAnOldName($("input[name='tScDrpRstockbillentryList[" + length + "].number']"),itemId,itemNo);
					$("input[name='tScDrpRstockbillentryList[" + length + "].number']").attr("disabled","disabled");
					/*var tranTypeName = row.tranTypeName;
					$("input[name='tScDrpRstockbillentryList[" + length + "].classIDName']").val(tranTypeName);
*/
					length ++;

				}
				setAllAmount();
				$("#chooseType").val("");
				//}
				//}
				//});
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
				$("#chooseType").val("");
			}
		}]
	}).zindex();
}

//设置有效期至
function setPeriodDate(index,entryName) {
	var kfDate = $("input[name='" + entryName + "[" + index + "].kfDate']").val();//生产日期
	var kfPeriod = $("input[name='" + entryName + "[" + index + "].kfPeriod']").val();//保质期
	var kfDateType = ""//保质期类型
	if (entryName.indexOf("r") > -1) {
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

//汇总折扣金额
function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody1 = $("#tScDrpRstockbillentry_table");
	var length = $tbody1[0].rows.length;
	//退货明细汇总数据
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScDrpRstockbillentryList[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScDrpRstockbillentryList[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScDrpRstockbillentryList[" + i + "].qty']").val();//数量
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

//物流信息
var logisticalValue = "";
function goLogistical(){
	var id = $("#id").val();
	var tranType = $("#tranType").val();
	var checkState = $("#checkState").val();
	var load = $("#load").val();
	var url = 'tScDrpRstockbillController.do?goLogisticalUpdate&fid=' + id+"&tranType="+tranType+"&logisticalValue="+logisticalValue;
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