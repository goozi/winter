var h = ($(window).height() - 200)/2;
$(function () {
	$.ajax({
		cache:false
	});

	$("#empName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectEmpNameDialog();
		}
	});
	$("#empName").blur(function () {
		checkInput($("#empID"), $("#empName"));
	})

	$("#deptName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectDeptNameDialog();
		}
	});
	$("#deptName").blur(function () {
		checkInput($("#deptID"), $("#deptName"));
	});


});

function countGrossAmount(rowIndex){
	var priceVal = $('input[name="tScQuoteitemsList[' + rowIndex + '].price"]').val();
	if(parseFloat(priceVal) <= 0){
		$('input[name="tScQuoteitemsList[' + rowIndex + '].price"]').val(1);
		priceVal = 1;
	}
	var costPriceVal = $('input[name="tScQuoteitemsList[' + rowIndex + '].costPrice"]').val();
	if(priceVal == ""){
		priceVal = parseFloat(0);
	}
	if(costPriceVal == ""){
		costPriceVal = parseFloat(0);
	}
	//单价格保留精度小数位数
	var CFG_UNITP_RICE = $("#CFG_UNITP_RICE").val();
	priceVal = parseFloat(priceVal).toFixed(CFG_UNITP_RICE);
	$('input[name="tScQuoteitemsList[' + rowIndex + '].price"]').val(priceVal);

	var sumVal = priceVal -  costPriceVal;
	var CFG_OTHER = $("#CFG_OTHER").val();//其他精度
	if(sumVal <= 0){
		$('input[name="tScQuoteitemsList[' + rowIndex + '].grossAmount"]').val(0);
		$('input[name="tScQuoteitemsList[' + rowIndex + '].grossper"]').val(0);
	}else{
		$('input[name="tScQuoteitemsList[' + rowIndex + '].grossAmount"]').val(sumVal);
		$('input[name="tScQuoteitemsList[' + rowIndex + '].grossper"]').val((parseFloat(sumVal)/parseFloat(priceVal)).toFixed(CFG_OTHER));
	}


}
function tScQuoteitemsListItemIdListBlur(rowIndex){
	checkInput($('input[name="tScQuoteitemsList[' + rowIndex + '].itemID"]'), $('input[name="tScQuoteitemsList[' + rowIndex + '].itemNumber"]'));

}
function tScQuoteitemsListItemIdListKeyPress(rowIndex, evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
	var key = evt.keyCode ? evt.keyCode : evt.which;
	if (key == 13) {
		selecttScQuoteitemsListItemIdListKeyPressDialog(rowIndex);
	}
}

function selecttScQuoteitemsListItemIdListKeyPressDialog(rowIndex){
	var itemNumber = $('input[name="tScQuoteitemsList[' + rowIndex + '].itemNumber"]').val();
	var url = encodeURI('tScIcitemController.do?goSelectDialog&number=' + itemNumber);
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
					var nextTrLength = $('input[name="tScQuoteitemsList[' + rowIndex + '].itemNumber"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						var appendLength = itemsLength - nextTrLength;
						for (var j = 0; j < appendLength - 1; j++) {
							clickAddTScQuoteitemsBtn(parseInt(rowIndex)+j);//相当于在之后增加一行
						}
					}
					$.each(items, function (i, item) {
						var id = item.id;//商品id
						var unitId = ""; //默认销售单位
						var barCode = "";//条形码
						var cgLatestPrice = "";//最近进货价(最近采购价)
						$.ajax({
							type: 'POST',
							url: 'tScIcitemController.do?getIcItemForQuoteItems',
							async: false,
							cache: false,
							data: {'id': id},
							dataType: 'json',
							success: function (data) {
								if (data.success == true) {
									var resultData = data.attributes;
									unitId = resultData.unitId; //调用商品单位的默认销售单位
									barCode = resultData.barCode;//条码
									cgLatestPrice = resultData.cgLatestPrice;//最近采购价
								}
							}
						});
						if (i > 0) {
							var countIndex = parseInt(rowIndex) + parseInt(i);
							$('input[name="tScQuoteitemsList[' + countIndex + '].itemID"]').val(item.id);//商品id
							$('input[name="tScQuoteitemsList[' + countIndex + '].itemNumber"]').val(item.number);//商品编号
							$('input[name="tScQuoteitemsList[' + countIndex + '].itemName"]').val(item.name);//商品名称
							$('input[name="tScQuoteitemsList[' + countIndex + '].itemModel"]').val(item.model);//规格
							$('input[name="tScQuoteitemsList[' + countIndex + '].itemBarcode"]').val(barCode);//条形码
							$('input[name="tScQuoteitemsList[' + countIndex + '].price"]').val(0);//单价
							$('input[name="tScQuoteitemsList[' + countIndex + '].price"]').trigger('change');
							if(cgLatestPrice=="" || cgLatestPrice==null){
								$('input[name="tScQuoteitemsList[' + countIndex + '].costPrice"]').val(0);//最近采购价
							}else{
								$('input[name="tScQuoteitemsList[' + countIndex + '].costPrice"]').val(cgLatestPrice);//最近采购价
							}
							$('#tScQuoteitemsList\\[' + countIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
							$('#tScQuoteitemsList\\[' + countIndex + '\\]\\.unitID').combobox({
								onChange: function (newValue, oldValue) {
									if (oldValue != newValue) {
										$.ajax({
											type: 'POST',
											url: 'tScIcitemController.do?getChangeInfo',
											async: false,
											cache: false,
											data: {'unitId': newValue},
											dataType: 'json',
											success: function (data) {
												if (data.success == true) {
													var resultData = data.attributes;
													barCode = resultData.barCode;
													var cgLatestPrice = resultData.cgLatestPrice;
													$('input[name="tScQuoteitemsList[' + countIndex + '].itemBarcode"]').val(barCode);//条形码
													$('input[name="tScQuoteitemsList[' + countIndex + '].costPrice"]').val(cgLatestPrice);
													$('input[name="tScQuoteitemsList[' + countIndex + '].price"]').trigger('change');
												}
											}
										});
									}
								}
							});
							$('#tScQuoteitemsList\\[' + countIndex + '\\]\\.unitID').combobox('setValue', unitId);
							setValOldIdAnOldName($('input[name="tScQuoteitemsList[' + countIndex + '].itemNumber"]'), item.id, item.number);

						} else {
							$('input[name="tScQuoteitemsList[' + rowIndex + '].itemID"]').val(item.id);//商品id
							$('input[name="tScQuoteitemsList[' + rowIndex + '].itemNumber"]').val(item.number);//商品编号
							$('input[name="tScQuoteitemsList[' + rowIndex + '].itemName"]').val(item.name);//商品名称
							$('input[name="tScQuoteitemsList[' + rowIndex + '].itemModel"]').val(item.model);//规格
							$('input[name="tScQuoteitemsList[' + rowIndex + '].itemBarcode"]').val(barCode);//条形码
							$('input[name="tScQuoteitemsList[' + rowIndex + '].price"]').val(0);//单价
							$('input[name="tScQuoteitemsList[' + rowIndex + '].price"]').trigger('change');
							if(cgLatestPrice=="" || cgLatestPrice==null){
								$('input[name="tScQuoteitemsList[' + rowIndex + '].costPrice"]').val(0);
							}else{
								$('input[name="tScQuoteitemsList[' + rowIndex + '].costPrice"]').val(cgLatestPrice);
							}
							$('#tScQuoteitemsList\\[' + rowIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
							$('#tScQuoteitemsList\\[' + rowIndex + '\\]\\.unitID').combobox({
								onChange: function (newValue, oldValue) {
									if (oldValue != newValue) {
										$.ajax({
											type: 'POST',
											url: 'tScIcitemController.do?getChangeInfo',
											async: false,
											cache: false,
											data: {'unitId': newValue},
											dataType: 'json',
											success: function (data) {
												if (data.success == true) {
													var resultData = data.attributes;
													barCode = resultData.barCode;
													var cgLatestPrice =resultData.cgLatestPrice;
													$('input[name="tScQuoteitemsList[' + rowIndex + '].itemBarcode"]').val(barCode);//条形码
													$('input[name="tScQuoteitemsList[' + rowIndex + '].costPrice"]').val(cgLatestPrice);
													$('input[name="tScQuoteitemsList[' + rowIndex + '].price"]').trigger('change');
												}
											}
										});
									}
								}
							});
							$('#tScQuoteitemsList\\[' + rowIndex + '\\]\\.unitID').combobox('setValue', unitId);
							setValOldIdAnOldName($('input[name="tScQuoteitemsList[' + rowIndex + '].itemNumber"]'), item.id, item.number);
						}
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

function tScQuotecustomerItemIdListKeyPress(rowIndex, evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
	var key = evt.keyCode ? evt.keyCode : evt.which;
	if (key == 13) {
		selecttScQuotecustomerItemIdListKeyPressDialog(rowIndex);
	}
}

var itemIds = "";
function selecttScQuotecustomerItemIdListKeyPressDialog(rowIndex){
	var itemName = $('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]').val();
	var url = encodeURI('tScOrganizationController.do?goSelectDialog&name=' + itemName);
	var width = 1000;
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
				var items = iframe.getMoreSelectionData();
				if (items != "") {
					var nextTrLength = $('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						var appendLength = itemsLength - nextTrLength;
						for (var j = 0; j < appendLength - 1; j++) {
							clickAddTScQuotecustomerBtn(rowIndex);//相当于在之后增加一行
						}
					}
					$.each(items, function (i, item) {
						if(itemIds.indexOf(item.id+i) > -1){
							$.dialog.confirm("列表中已存在该客户，是否继续选择？", function () {
									if (i > 0) {
										var countIndex = parseInt(rowIndex) + parseInt(i);
										$('input[name="tScQuotecustomerList[' + countIndex + '].itemID"]').val(item.id);//客户id
										$('input[name="tScQuotecustomerList[' + countIndex + '].itemName"]').val(item.name);//客户名称
										$('input[name="tScQuotecustomerList[' + countIndex + '].contact"]').val(item.contact);//联系人
										$('input[name="tScQuotecustomerList[' + countIndex + '].mobilePhone"]').val(item.mobilephone);//手机号码
										$('input[name="tScQuotecustomerList[' + countIndex + '].phone"]').val(item.phone);//电话
										$('input[name="tScQuotecustomerList[' + countIndex + '].fax"]').val(item.fax);//传真
										$('input[name="tScQuotecustomerList[' + countIndex + '].address"]').val(item.address);//地址
										//$('input[name="tScQuotecustomerList[' + countIndex + '].note"]').val(item.note);//备注
										setValOldIdAnOldName($('input[name="tScQuotecustomerList[' + countIndex + '].itemName"]'), item.id, item.name);
									} else {
										$('input[name="tScQuotecustomerList[' + rowIndex + '].itemID"]').val(item.id);//客户id
										$('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]').val(item.name);//客户名称
										$('input[name="tScQuotecustomerList[' + rowIndex + '].contact"]').val(item.contact);//联系人
										$('input[name="tScQuotecustomerList[' + rowIndex + '].mobilePhone"]').val(item.mobilephone);//手机号码
										$('input[name="tScQuotecustomerList[' + rowIndex + '].phone"]').val(item.phone);//电话
										$('input[name="tScQuotecustomerList[' + rowIndex + '].fax"]').val(item.fax);//传真
										$('input[name="tScQuotecustomerList[' + rowIndex + '].address"]').val(item.address);//地址
										//$('input[name="tScQuotecustomerList[' + rowIndex + '].note"]').val(item.note);//备注
										setValOldIdAnOldName($('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]'), item.id, item.name);
									}
								}
								, function () {
									//
								}
							)
						}else {
							itemIds += item.id+i+",";
							if (i > 0) {
								var countIndex = parseInt(rowIndex) + parseInt(i);
								$('input[name="tScQuotecustomerList[' + countIndex + '].itemID"]').val(item.id);//客户id
								$('input[name="tScQuotecustomerList[' + countIndex + '].itemName"]').val(item.name);//客户名称
								$('input[name="tScQuotecustomerList[' + countIndex + '].contact"]').val(item.contact);//联系人
								$('input[name="tScQuotecustomerList[' + countIndex + '].mobilePhone"]').val(item.mobilephone);//手机号码
								$('input[name="tScQuotecustomerList[' + countIndex + '].phone"]').val(item.phone);//电话
								$('input[name="tScQuotecustomerList[' + countIndex + '].fax"]').val(item.fax);//传真
								$('input[name="tScQuotecustomerList[' + countIndex + '].address"]').val(item.address);//地址
								//$('input[name="tScQuotecustomerList[' + countIndex + '].note"]').val(item.note);//备注
								setValOldIdAnOldName($('input[name="tScQuotecustomerList[' + countIndex + '].itemName"]'), item.id, item.name);
							} else {
								$('input[name="tScQuotecustomerList[' + rowIndex + '].itemID"]').val(item.id);//客户id
								$('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]').val(item.name);//客户名称
								$('input[name="tScQuotecustomerList[' + rowIndex + '].contact"]').val(item.contact);//联系人
								$('input[name="tScQuotecustomerList[' + rowIndex + '].mobilePhone"]').val(item.mobilephone);//手机号码
								$('input[name="tScQuotecustomerList[' + rowIndex + '].phone"]').val(item.phone);//电话
								$('input[name="tScQuotecustomerList[' + rowIndex + '].fax"]').val(item.fax);//传真
								$('input[name="tScQuotecustomerList[' + rowIndex + '].address"]').val(item.address);//地址
								//$('input[name="tScQuotecustomerList[' + rowIndex + '].note"]').val(item.note);//备注
								setValOldIdAnOldName($('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]'), item.id, item.name);
							}
						}
					});

				}
			/*	$("input[name='itemName']").val(item.name);
				$("input[name='itemID']").val(item.id);
				setValOldIdAnOldName($("#itemName"), item.id, item.name);*/
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
function tScQuotecustomerItemIdListBlur(rowIndex){
	checkInput($('input[name="tScQuotecustomerList[' + rowIndex + '].itemID"]'), $('input[name="tScQuotecustomerList[' + rowIndex + '].itemName"]'));
}
/**
 *
 * checkId 文本框里面的id
 * checkNameId 另外用名称代替的id
 *
 */
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

/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

function selectDeptNameDialog() {
	var tempName = $("#deptName").val();
	var url = encodeURI('tScDepartmentController.do?goSelectDepartDialog&name=' + tempName);
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
				$("input[name='deptName']").val(item.name);
				$("input[name='deptID']").val(item.id);
				setValOldIdAnOldName($('#deptName'), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
function selectEmpNameDialog() {
	var tempName = $("#empName").val();
	var url = encodeURI('tScEmpController.do?goSelectEmpDialog&name=' + tempName);
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
				$("input[name='empName']").val(item.name);
				$("input[name='empID']").val(item.id);
				setValOldIdAnOldName($("#empName"), item.id, item.name);
				//通过部门的id获取部门名称
				$("#deptName").val(item.deptIdName);
				$("#deptID").val(item.deptID);
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
function setmorendate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}

//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'),comboname_str=$this.attr('comboname'), blur_str = $this.attr('onblur'), val = $this.val(), onchange_str = $this.attr("onchange");
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
			if(comboname_str!=null){
				var s = comboname_str.indexOf("[");
				var e = comboname_str.indexOf("]");
				var new_comboname_str = comboname_str.substring(s + 1, e);
				$this.attr("comboname", comboname_str.replace(new_comboname_str, i));
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
					$this.attr("onclick", onclick_str.replace(new_onclick, i));

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
				} else {
					var s = blur_str.indexOf("(");
					var e = blur_str.indexOf(")");
					var new_blur = blur_str.substring(s + 1, e);
					$this.attr("onblur", blur_str.replace(new_blur, i));
				}
			}
			if (onchange_str != null) {
				if (onchange_str.indexOf("#index#") >= 0) {
					$this.attr("onchange", onchange_str.replace('#index#', i));
				} else {
					var s = onchange_str.indexOf("(");
					var e = onchange_str.indexOf(")");
					var new_onchange_str = onchange_str.substring(s + 1, e);
					$this.attr("onchange", onchange_str.replace(new_onchange_str, i));
				}
			}
		});
		$("input[name='tScQuotecustomerList[" + i + "].indexNumber']").val(i + 1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});
}

//初始化下标
function resetQuoteItemTrNum(tableId,rowIndex) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), blur_str = $this.attr('onblur'), val = $this.val(),onchange_str = $this.attr("onchange");
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
					var new_onclick = onclick_str.substring(s + 1, e);
					$this.attr("onclick", onclick_str.replace(new_onclick, i));

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
				} else {
					var s = blur_str.indexOf("(");
					var e = blur_str.indexOf(")");
					var new_blur = blur_str.substring(s + 1, e);
					$this.attr("onblur", blur_str.replace(new_blur, i));
				}
			}
			if (onchange_str != null) {
				if (onchange_str.indexOf("#index#") >= 0) {
					$this.attr("onchange", onchange_str.replace('#index#', i));
				} else {
					var s = onchange_str.indexOf("(");
					var e = onchange_str.indexOf(")");
					var new_onchange_str = onchange_str.substring(s + 1, e);
					$this.attr("onchange", onchange_str.replace(new_onchange_str, i));
				}
			}
		});
		$("input[name='tScQuoteitemsList[" + i + "].indexNumber']").val(i + 1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});
	$tbody.find('>tr').each(function (i) {
		var a = parseInt(rowIndex) + 1;
		if (a == i) {
			$("#tScQuoteitemsList\\[" + a + "\\]\\.unitID").combobox({
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false,
				width:84
			});
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

//草稿恢复后操作事项
function doTempRecoveryExt(){
	var newDate = setmorendate();
	$("#inureDate").val(newDate);
}