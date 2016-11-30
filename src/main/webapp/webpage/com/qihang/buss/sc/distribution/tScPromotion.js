var h = ($(window).height() - 285)/2;
$(function () {
	setDefaultDate();//设置默认时间
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

function checkBillDate(){
	var checkDate = $("#checkDate").val();
	if(checkDate) {
		checkDate = new Date(checkDate).getTime();
		var billDate = $("#date").val();
		billDate = new Date(billDate).getTime();
		if (parseInt(billDate) <= parseInt(checkDate)) {
			tip("单据日期不可小于期间日期");
			return false;
		}
	}
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
		$("#begDate").val(year+"-"+month+"-"+date);
		$("#endDate").val(year+"-"+month+"-"+date);
	}else{
		/*$("#begDate").val(year+"-"+month+"-"+date);
		$("#endDate").val(year+"-"+month+"-"+date);*/
	}
}
function selectDeptNameDialog() {
	var tempName = $("#deptName").val();
	var url = encodeURI('tScDepartmentController.do?goSelectDepartDialog&name=' + tempName);
	var width = 1000;
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
	var width = 1000;
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
				if(item.deptIdName){
					$("#deptName").val(item.deptIdName);
					$("#deptID").val(item.deptID);
					setValOldIdAnOldName($("#deptName"), item.deptID, item.deptIdName);
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
function tScPromotionGoodsEntryListItemIdListKeyPress(rowIndex, evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
	var key = evt.keyCode ? evt.keyCode : evt.which;
	if (key == 13) {
		selecttScPromotionGoodsEntryListItemIdListKeyPressDialog(rowIndex);
	}
}

function tScPromotionGiftsEntryListItemIdListKeyPress(rowIndex, evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
	var key = evt.keyCode ? evt.keyCode : evt.which;
	if (key == 13) {
		selecttScPromotionGiftEntryListItemIdListKeyPressDialog(rowIndex);
	}
}

function selecttScPromotionGiftEntryListItemIdListKeyPressDialog(rowIndex){
	var giftNumber = $('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftNumber"]').val();
	var url = encodeURI('tScIcitemController.do?goSelectDialog&number=' + giftNumber);
	var width = 1000;
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
					var nextTrLength = $('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftNumber"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						var appendLength = itemsLength - nextTrLength;
						for (var j = 0; j < appendLength - 1; j++) {
							clickAddTScPromotiongiftentryBtn(rowIndex);//相当于在之后增加一行
						}
					}
					$.each(items, function (i, item) {
						var id = item.id;//商品id
						var unitId = ""; //默认销售单位
						var barCode = "";//条形码

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
								}
							}
						});
						if (i > 0) {
							var countIndex = parseInt(rowIndex) + parseInt(i);
							$('input[name="tScPromotiongiftsentryList[' + countIndex + '].itemID"]').val(item.id);//商品id
							$('input[name="tScPromotiongiftsentryList[' + countIndex + '].giftNumber"]').val(item.number);//商品编号
							$('input[name="tScPromotiongiftsentryList[' + countIndex + '].giftName"]').val(item.name);//商品名称
							$('input[name="tScPromotiongiftsentryList[' + countIndex + '].giftModel"]').val(item.model);//规格
							$('input[name="tScPromotiongiftsentryList[' + countIndex + '].giftBarCode"]').val(barCode);//条形码
							$('input[name="tScPromotiongiftsentryList[' + countIndex + '].qty"]').val(1);//数量

							//$('#tScPromotiongiftsentryList\\[' + countIndex + '\\]\\.unitID')
							//$("#giftsUnitId"+"\\[" + countIndex + "\\]")
							$('#tScPromotiongiftsentryList\\[' + countIndex + '\\]\\.unitID').combobox('reload','tScIcitemController.do?loadItemUnit&id=' + id);
							$('#tScPromotiongiftsentryList\\[' + countIndex + '\\]\\.unitID').combobox({
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
													$('input[name="tScPromotiongiftsentryList[' + countIndex + '].giftBarCode"]').val(barCode);//条形码
												}
											}
										});
									}
								}
							});
							//$('#tScPromotiongiftsentryList\\[' + countIndex + '\\]\\.unitID')
							$('#tScPromotiongiftsentryList\\[' + countIndex + '\\]\\.unitID').combobox('setValue', unitId);
							setValOldIdAnOldName($('input[name="tScPromotiongiftsentryList[' + countIndex + '].giftNumber"]'), item.id, item.number);
						} else {
							$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].itemID"]').val(item.id);//商品id
							$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftNumber"]').val(item.number);//商品编号
							$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftName"]').val(item.name);//商品名称
							$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftModel"]').val(item.model);//规格
							$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftBarCode"]').val(barCode);//条形码
							$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].qty"]').val(1);//数量

							$('#tScPromotiongiftsentryList\\[' + rowIndex + '\\]\\.unitID').combobox('reload',"tScIcitemController.do?loadItemUnit&id=" + id);//, "tScIcitemController.do?loadItemUnit&id=" + id
							$('#tScPromotiongiftsentryList\\[' + rowIndex + '\\]\\.unitID').combobox({
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
													$('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftBarCode"]').val(barCode);//条形码

												}
											}
										});
									}
								}
							});
							$('#tScPromotiongiftsentryList\\[' + rowIndex + '\\]\\.unitID').combobox('setValue', unitId);
							setValOldIdAnOldName($('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftNumber"]'), item.id, item.number);
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

function selecttScPromotionGoodsEntryListItemIdListKeyPressDialog(rowIndex){
	var goodsNumber = $('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsNumber"]').val();
	var url = encodeURI('tScPromotionController.do?goSelectDialog&number=' + goodsNumber);
	var width = 1000;
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
					/*var nextTrLength = $('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsNumber"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						var appendLength = itemsLength - nextTrLength;
						/!*for (var j = 0; j < appendLength - 1; j++) {
							clickAddTScPromotiongoodsentryBtn(rowIndex);//相当于在之后增加一行
						}*!/
					}*/
					$.each(items, function (i, item) {
						var id = item.id;//商品id
						var unitId = ""; //默认销售单位
						var barCode = "";//条形码

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
								}
							}
						});
						if (i > 0) {
							var countIndex = parseInt(rowIndex) + parseInt(i);
							$('input[name="tScPromotiongoodsentryList[' + countIndex + '].itemID"]').val(item.id);//商品id
							$('input[name="tScPromotiongoodsentryList[' + countIndex + '].goodsNumber"]').val(item.number);//商品编号
							$('input[name="tScPromotiongoodsentryList[' + countIndex + '].goodsName"]').val(item.name);//商品名称
							$('input[name="tScPromotiongoodsentryList[' + countIndex + '].goodsModel"]').val(item.model);//规格
							$('input[name="tScPromotiongoodsentryList[' + countIndex + '].goodsBarCode"]').val(barCode);//条形码
							$('input[name="tScPromotiongoodsentryList[' + countIndex + '].qty"]').val(1);//数量

							//$('#tScPromotiongoodsentryList\\[' + countIndex + '\\]\\.unitID')
							//$("#goodsUnitId"+"\\[" + countIndex + "\\]")
							$('#tScPromotiongoodsentryList\\[' + countIndex + '\\]\\.unitID').combobox('reload','tScIcitemController.do?loadItemUnit&id=' + id);
							$('#tScPromotiongoodsentryList\\[' + countIndex + '\\]\\.unitID').combobox({
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
													$('input[name="tScPromotiongoodsentryList[' + countIndex + '].goodsBarCode"]').val(barCode);//条形码
												}
											}
										});
									}
								}
							});
							$('#tScPromotiongoodsentryList\\[' + countIndex + '\\]\\.unitID').combobox('setValue', unitId);
							setValOldIdAnOldName($('input[name="tScPromotiongoodsentryList[' + countIndex + '].goodsNumber"]'), item.id, item.number);
						} else {
							$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].itemID"]').val(item.id);//商品id
							$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsNumber"]').val(item.number);//商品编号
							$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsName"]').val(item.name);//商品名称
							$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsModel"]').val(item.model);//规格
							$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsBarCode"]').val(barCode);//条形码
							$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].qty"]').val(1);//数量

							$('#tScPromotiongoodsentryList\\[' + rowIndex + '\\]\\.unitID').combobox('reload',"tScIcitemController.do?loadItemUnit&id=" + id);//, "tScIcitemController.do?loadItemUnit&id=" + id
							$('#tScPromotiongoodsentryList\\[' + rowIndex + '\\]\\.unitID').combobox({
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
													$('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsBarCode"]').val(barCode);//条形码

												}
											}
										});
									}
								}
							});
							$('#tScPromotiongoodsentryList\\[' + rowIndex + '\\]\\.unitID').combobox('setValue', unitId);
							setValOldIdAnOldName($('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsNumber"]'), item.id, item.number);
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

/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

function tScPromotionGoodsEntryListItemIdListBlur(rowIndex){
	checkInput($('input[name="tScPromotiongoodsentryList[' + rowIndex + '].itemID"]'), $('input[name="tScPromotiongoodsentryList[' + rowIndex + '].goodsNumber"]'));
}

function tScPromotionGiftsEntryListItemIdListBlur(rowIndex){
	checkInput($('input[name="tScPromotiongiftsentryList[' + rowIndex + '].itemID"]'), $('input[name="tScPromotiongiftsentryList[' + rowIndex + '].giftNumber"]'));
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
function resetPromotionGoodsEntryListTrNum(tableId,index,indexName) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input,span, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			var comboboName = $this.attr("comboname");
			var change_ = $this.attr("onchange");
			if(null != comboboName){
				//console.log(comboboName);
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
			if (onclick_str != null) {
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {

					var s = onclick_str.indexOf("(");
					var e = onclick_str.indexOf(")");
					var new_onclick = onclick_str.substring(s + 1, e);
					$this.attr("onclick", onclick_str.replace(new_onclick, i));

				}
			}
		});

		$("input[name='"+indexName+"["+i+"].indexNumber']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});

	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1)) {
			$("#tScPromotiongoodsentryList"+"\\[" + i + "\\]").combobox({
				width:"auto",
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			});
		}
	});
}

//初始化下标
function resetPromotionGiftsEntryListTrNum(tableId, index,indexName,str) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input,span, select,button,a', this).each(function(){

			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			var comboboName = $this.attr("comboname");
			var change_ = $this.attr("onchange");
			if(null != comboboName){
				//console.log(comboboName);
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
			if (onclick_str != null) {
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {
					var s = onclick_str.indexOf("(");
					var e = onclick_str.indexOf(")");
					var new_onclick = onclick_str.substring(s + 1, e);
					$this.attr("onclick", onclick_str.replace(new_onclick, i));
				}
			}
		});
		$("input[name='"+indexName+"["+i+"].indexNumber']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});
	if(str != "del") {
		$tbody.find('>tr').each(function (i) {
			if (i == (parseInt(index) + 1)) {
				$('#tScPromotiongiftsentryList\\[' + i + '\\]\\.unitID').combobox({
					width: "auto",
					valueField: "id",
					textField: "text",
					panelHeight: "auto",
					editable: false
				})
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
function checkQty(obj){
		var CFG_NUMBER = $("#CFG_NUMBER").val();
		var val = $(obj).val();
		var amount = parseFloat(val);
		amount = amount.toFixed(CFG_NUMBER);
		if(!isNaN(amount)){
			$(obj).val(amount);
		}else{
			$(obj).val(val);
		}
}


