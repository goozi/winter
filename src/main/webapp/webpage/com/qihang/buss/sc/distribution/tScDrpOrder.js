$(function(){
	setDefaultDate();
	$("#empName").keypress(function(e){//经办人
		if (e.keyCode == 13) {
			selectEmpDialog();
		}
	});
	$("#deptName").keypress(function (e) {//部门
		if (e.keyCode == 13) {
			selectDeptDialog();
		}
	});
	$("#stockName").keypress(function (e) {//仓库
		if (e.keyCode == 13) {
			selectStockNameDialog();
		}
	});
	$('#itemName').keypress(function (e) {//经销商
		if (e.keyCode == 13) {
			selectitemNameDialog();
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
	var delearType = $("#delearType").val();
	if(delearType == 'ADealer' || delearType == ''){
		$("#itemName").removeAttr("datatype");
		$("#itemName").next().text("");
		$("#itemName").unbind();
	}
	var CFG_OTHER = $("#CFG_OTHER").val();
	var CFG_MONEY = $("#CFG_MONEY").val();
	var CFG_NUMBER = $("#CFG_NUMBER").val();
	var CFG_UNITP_RICE = $("#CFG_UNITP_RICE").val();
	var CFG_DISCOUNT_RATE = $("#CFG_DISCOUNT_RATE").val();

	var allAmount = $("#allAmount").val() || 0;
	var rebateAmount = $("#rebateAmount").val() || 0;
	$("#allAmount").val(parseFloat(allAmount).toFixed(CFG_MONEY));
	$("#rebateAmount").val(parseFloat(rebateAmount).toFixed(CFG_MONEY));
	var id = $("#id").val();
	if(id){

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

//汇总金额
function setAllAmount(){
	$tbody = $("#add_tScDrpOrderentry_table");
	var length1 = $tbody[0].rows.length;
	var sumAmount = 0;
	for(var i=0 ; i<length1 ; i++){
		var amount = $("input[name='tScDrpOrderentryList["+i+"].inTaxAmount']").val();
		sumAmount += parseFloat(amount);
	}
	var CFG_MONEY = $("#CFG_MONEY").val();
	$("#amount").val(sumAmount.toFixed(CFG_MONEY));
	var rebateAmount = $("#rebateAmount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
	var allAmount = (sumAmount - parseFloat(rebateAmount)).toFixed(CFG_MONEY);
	$("#allAmount").val(allAmount);
}

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
				if (name.indexOf("qty") > -1) {
					$this.attr("onblur", "changePrice("+i+")");//onchange="changePrice(0)"
				}
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
			if (blur_str != null) {
				if (blur_str.indexOf("#index#") >= 0) {
					$this.attr("onblur", blur_str.replace(/#index#/g, i));
				}
			}
		});
		setFunctionInfo(i,indexName);
		$("input[name='" + indexName + "[" + i + "].indexNumber']").val(i + 1);
		$(this).find('div[name=\'xh\']').html(i + 1);
	});
	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1)) {
			$('#tScDrpOrderentryList\\[' + i + '\\]\\.unitID').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			});
			/*$('#tScDrpOrderentryList\\[' + i + '\\]\\.basicUnitID').combobox({
				width:'64',
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false,
				disabled:true
			});
			$('#tScDrpOrderentryList\\[' + i + '\\]\\.secUnitID').combobox({
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false,
				disabled:true
			});*/
		}
	});
}

//统计应付金额
function sumAllAmount() {
	debugger;
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
	var customerId = $("#itemID").val();
	var qty = $("input[name='tScDrpOrderentryList["+index+"].qty']").val();
	if(isNaN(qty)){
		$("input[name='tScDrpOrderentryList["+index+"].qty']").val("");
		return;
	}
	var itemId = $("input[name='tScDrpOrderentryList["+index+"].itemID']").val();//商品
	var unitId = $("#tScDrpOrderentryList\\["+index+"\\]\\.unitID").combobox("getValue");
	var itemNo = $("input[name='tScDrpOrderentryList["+index+"].goodsNo']").val();//商品编号
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
				$("input[name='tScDrpOrderentryList["+index+"].taxPriceEx']").val(price);
				$("input[name='tScDrpOrderentryList["+index+"].taxPriceEx']").trigger("change");
			}
		});
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
//设置单据日期默认为当前日期
function setDefaultDate(){
	var date = new Date();
	var year = date.getFullYear();
	var month = (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
	var date = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	$("#date").val(year+"-"+month+"-"+date);
	var id = $("#id").val();
	if(!id){
		$("#date").val(year+"-"+month+"-"+date);
		$("input[name='tScDrpOrderentryList[#index#].jhDate']").val(year+"-"+month+"-"+date);
	}else{
		$("input[name='tScDrpOrderentryList[#index#].jhDate']").val(year+"-"+month+"-"+date);
	}
}
//选择职员
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
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
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
//选择仓库
function selectStockNameDialog() {
	var tempName = $("#stockName").val();
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
				$("input[name='stockName']").val(item.name);
				$("input[name='stockID']").val(item.id);
				$("#add_tScDrpOrderentry_table").find("input[name$='.stockID']").each(function(){
					$(this).val(item.id);
				});
				$("#add_tScDrpOrderentry_table").find("input[name$='.stockName']").each(function(){
					$(this).val(item.name);
				});

				//$("input[name='tScOrderentryList[#index#].stockID']").val(item.id);//设置模板从表头取值
				//$("input[name='tScOrderentryList[#index#].stockName']").val(item.name);
				//setValOldIdAnOldName($("input[name='tScOrderentryList[#index#].stockName']"), item.id, item.name)
				//$('#add_tScOrderentry_table').find("input[name$=stockName]").val(item.name);//设置从表的值
				//$('#add_tScOrderentry_table').find("input[name$=stockID]").val(item.id);
				//setValOldIdAnOldName($('#add_tScOrderentry_table').find("input[name$=stockName]"), item.id, item.name)
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
				$("input[name='itemName']").val(item.name);
				$("input[name='itemID']").val(item.id);
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
//商品选择框
function selectIcitemDialog(index){
	var goodsNo = $("input[name='tScDrpOrderentryList["+index+"].goodsNo']").val();
	var url = 'tScIcitemController.do?goSelectDialog&number=' + goodsNo;
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
					var nextTrLength =  $('input[name="tScDrpOrderentryList['+index+'].goodsNo"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						var appendLength = itemsLength - nextTrLength;
						for (var j = 0; j < appendLength - 1; j++) {
							clickAddTScDrpOrderentryBtn(index);//相当于在之后增加一行
						}
					}
					var sonId = $("#sonID").val();//当前分支机构
					$.each(items,function(i,item){
						debugger;
						var rowindex = parseInt(index) + i;
						var id = item.id;//id
						var taxRateOut = item.taxRateOut;//商品销项税
						var number = item.number;//编码
						var name = item.name;//名称
						var model = item.model;//规格
						var stockSonId = item.stockSonId;//商品默认仓库分支机构id；
						var unitId = "";//默认仓存单位的从表主键id（放从表的id）
						var barCode = "";//默认仓存单位的商品的从表的条形码
						var secUnitID = "";//辅助单位（放从表的id为辅助单位）
						var basicUnitId = ""; //基本单位（从表的id）
						var secCoefficient = "";//辅助换算率
						var coefficient = "";//单位换算率
						var price = item.price|0;//单价
						var stockId = "";
						var stockName = "";
						//通过id获取子表里面的信息
						$.ajax({
							type: 'POST',
							url: 'tScIcitemController.do?getIcItemPriceInfoByIcitemId&rowIndex='+rowindex,
							async: false,
							cache: false,
							data: {'id': id},
							dataType: 'json',
							success: function (data) {
								debugger;
								if (data.success == true) {
									var resultData = data.attributes;
									unitId = resultData.unitId; //仓存单位的id的默认仓存
									barCode = resultData.barCode;//条码
									secUnitID = resultData.secUnitId;//辅助单位的id
									basicUnitId = resultData.basicUnitId;//基本单位的id
									secCoefficient = resultData.secCoefficient;//辅助单位的辅助单位换算率
									coefficient = resultData.cofficient;//单位换算率
									stockId = resultData.stockId;//仓库id
									stockName = resultData.stockName;//仓库名称
									var rowIndex = resultData.rowIndex;
									//var checkId = $("input[name='tScDrpOrderentryList[" + rowIndex + "].stockID']").val();
									if(stockId != "" && stockId != "undefined") {
										//if(stockSonId == sonId) {
											$("input[name='tScDrpOrderentryList[" + rowIndex + "].stockID']").val(stockId);
											$("input[name='tScDrpOrderentryList[" + rowIndex + "].stockName']").val(stockName);
											//$("input[name='tScDrpOrderentryList[" + rowIndex + "].stockName']").attr("readonly", "readonly");
											//$("input[name='tScDrpOrderentryList[" + rowIndex + "].stockName']").css("background-color", "rgb(226,226,226)");
										//}
									}else{
										$("input[name='tScDrpOrderentryList[" + rowIndex + "].stockID']").val($("input[name='stockID']").val());
										$("input[name='tScDrpOrderentryList[" + rowIndex + "].stockName']").val($("input[name='stockName']").val());
									}
									setValOldIdAnOldName($('input[name="tScDrpOrderentryList[' + rowIndex + '].stockName"]'),stockId,stockName);

									$('input[name="tScDrpOrderentryList[' + rowIndex + '].taxRate"]').val(taxRateOut);
									$('input[name="tScDrpOrderentryList[' + rowIndex + '].taxRate"]').trigger("change");
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].goodsNo']").val(number);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].itemID']").val(id);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].model']").val(model);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].goodsName']").val(name);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].barCode']").val(barCode);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].coefficient']").val(coefficient);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].basicUnitID']").val(basicUnitId);
									$("input[name='tScDrpOrderentryList[" + rowIndex + "].secUnitID']").val(secUnitID);

									$('input[name="tScDrpOrderentryList[' + rowIndex + '].qty"]').val(0);//数量
									$('input[name="tScDrpOrderentryList[' + rowIndex + '].qty"]').trigger("change");
									$('input[name="tScDrpOrderentryList[' + rowIndex + '].taxPriceEx"]').val(price);//单价
									$('input[name="tScDrpOrderentryList[' + rowIndex + '].taxPriceEx"]').trigger("change");


									//$("input[name='stockName']").val(item.name);
									//$("input[name='stockID']").val(item.id);
									//$("#add_tScDrpOrderentry_table").find("input[name$='.stockId']").each(function(){
									//	$(this).val(item.id);
									//});
									//$("#add_tScDrpOrderentry_table").find("input[name$='.stockName']").each(function(){
									//	$(this).val(item.name);
									//});
									/*$('input[name="tScDrpOrderentryList[' + rowIndex + '].itemWeight"]').val(item.weight);//商品重量
									$('input[name="tScDrpOrderentryList[' + rowIndex + '].itemWeight"]:first').trigger("change");*/
									$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
									$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox({
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
															$("input[name='tScDrpOrderentryList[" + rowIndex + "].barCode']").val(barCode);
															$('input[name="tScDrpOrderentryList['+rowIndex+'].coefficient"]').val(coefficient);//换算率
															$('input[name="tScDrpOrderentryList['+rowIndex+'].coefficient"]').trigger("change");
														}
													}
												});
											}
										}
									});
									$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox("setValue", unitId);
									setValOldIdAnOldName($("input[name='tScDrpOrderentryList[" + rowIndex + "].goodsNo']"), item.id,item.number);

									/*$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.basicUnitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
									$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.basicUnitID').combobox("setValue", basicUnitId);
									setValOldIdAnOldName($("input[name='tScDrpOrderentryList[" + rowIndex + "].goodsNo']"), item.id,item.number);


									$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.secUnitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
									$('#tScDrpOrderentryList\\[' + rowIndex + '\\]\\.secUnitID').combobox("setValue", secUnitID);
									setValOldIdAnOldName($("input[name='tScDrpOrderentryList[" + rowIndex + "].goodsNo']"), item.id,item.number);*/
								}
							}
						});
					});
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

//选择仓库
function selectStockDialog(index) {
	var itemName = $("input[name='tScDrpOrderentryList[" + index + "].stockName']").val();
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
				$("input[name='tScDrpOrderentryList[" + index + "].stockID']").val(item.id);
				$("input[name='tScDrpOrderentryList[" + index + "].stockName']").val(item.name);
				/*if(!index && index!=0) {
					$("#stockName").val(item.name);
					$("#stockId").val(item.id);
					$tbody = $("#tPoOrderentry_table");
					var length = $tbody[0].rows.length;
					$("input[name='tScDrpOrderentryList[#index#].stockId']").val(item.id);
					$("input[name='tScDrpOrderentryList[#index#].stockName']").val(item.name);
					for (var i = 0; i < length; i++) {
						$("input[name='tScDrpOrderentryList[" + i + "].stockId']").val(item.id);
						$("input[name='tScDrpOrderentryList[" + i + "].stockName']").val(item.name);
						setValOldIdAnOldName($("input[name='tScDrpOrderentryList[" + i + "].stockName']"), item.id, item.name);
					}
					setValOldIdAnOldName($("#stockName"), item.id, item.name);
				}else{
					$("input[name='tScDrpOrderentryList[" + index + "].stockId']").val(item.id);
					$("input[name='tScDrpOrderentryList[" + index + "].stockName']").val(item.name);
					setValOldIdAnOldName($("input[name='tPoOrderentryList[" + index + "].stockName']"), item.id, item.name);
				}*/
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}

/*
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
function setAmountBytaxPriceEx(index,entryName,number){
	//计算金额
	var taxPriceEx = $("input[name='"+entryName+"[" + index + "].taxPriceEx']").val();//获得输入的单价
	var qty = $("input[name='"+entryName+"[" + index + "].qty']").val(); //获得输入数量
	var amount = parseFloat(taxPriceEx*qty).toFixed(number);
	$("input[name='"+entryName+"[" + index + "].taxAmountEx']").val(amount);

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
}*/
