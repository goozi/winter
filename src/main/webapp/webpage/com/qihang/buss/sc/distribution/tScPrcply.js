var h = ($(window).height() - 285)/2;
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

	//添加选单下拉框
	addButton();
});

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

function addButton(){
	var button = document.createElement("input");
	button.id="orderQuote";
	button.type="button";
	button.class="button";
	button.value="选单";
	var load = $("#load").val();
	if("detail" != load) {
		var buttonDiv = document.getElementsByClassName("ui_buttons");
		//buttonDiv[0].appendChild(button);
		var selectbutton = document.createElement("select");
		selectbutton.id = "selectOrderbutton";
		selectbutton.setAttribute("onchange","selectBillDialog(0)");
		selectbutton.style="margin-left:10px";

		var option1 = document.createElement("option");
		option1.value="";
		option1.innerText="选单";
		option1.select = "select";
		selectbutton.appendChild(option1);
		var option = document.createElement("option");
		option.value="1";
		option.text="销售报价单";
		selectbutton.appendChild(option);
		var buttonDiv = document.getElementsByClassName("ui_buttons");
		buttonDiv[0].appendChild(selectbutton);
	}
}

function selectBillDialog(index){
	var sonId = $("#sonID").val();
	var url = encodeURI('tScQuoteController.do?getQuoteDialog&checkState=2&customerId='+$("#itemID").val()+"&sonId="+sonId);
	var width = 800;
	var height = 500;
	var title = "销售报价单";
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

				if (items.length > 0) {
					debugger;
					var item = items[items.length-1];
					var rowIndex = $("#add_tScPrcplyentry2_table tr").length - 1;
					var id = $("input[name='tScPrcplyentry2List["+rowIndex+"].itemID']").val();
					var itemsLength = items.length;
					if(!id){
						itemsLength--;
					}
					for (var j = 0; j < itemsLength; j++) {
						clickAddEntryBtns(index);//相当于在之后增加一行
						clickAddEntryBtn(index);
					}
					if(id){
						rowIndex++;
					}

					$.each(items, function (i, item) {
						var rowindex = parseInt(index) + i;
						var id = item.itemID;//商品id
						var unitId = item.unitID;//默认仓存单位的从表主键id（放从表的id）
						var barCode = item.icitemBarCode;//默认仓存单位的商品的从表的条形码
						var coefficient = item.icitemCoefficient;//单位换算率
						var xsLimitPrice = item.icitemxsLimitPrice;//销售限价
						var secUnitID = "";//辅助单位（放从表的id为辅助单位）
						var basicUnitId = ""; //基本单位（从表的id）
						var secCoefficient = "";//辅助换算率
						var number = item.icitemNumber;//商品编号
						var price = item.price;//单价
						var costPrice = item.costPrice;//成本单价
						var itemid = "";//客户id
						var itemName = ""; //客户
						var contact = "";//联系人
						var mobilePhone = "";//手机号码
						var phone = "";//电话
						var fax = "";//传真
						var address = "";//地址
						//通过客户id 获得客户信息
						$.ajax({
							type: 'POST',
							url: 'tScPrcplyController.do?getCustInfo',
							async: false,
							cache: false,
							data: {'custId': item.customerId},
							success: function (data) {
								debugger;
								var resultData = data.attributes.custInfo;
								itemid = resultData.id;
								itemName = resultData.name;
								contact = resultData.contact;
								mobilePhone = resultData.mobilePhone;
								phone = resultData.phone;
								fax = resultData.fax;
								address = resultData.address;
							}
						});

						var cfg_price = $("#CFG_UNITP_RICE").val();
						costPrice = parseFloat(costPrice).toFixed(cfg_price);
						price = parseFloat(price).toFixed(cfg_price);

						//通过id获取子表里面的信息
						$.ajax({
							type: 'POST',
							url: 'tScIcitemController.do?getIcItemPriceInfoByIcitemId&rowIndex='+rowindex,
							async: false,
							cache: false,
							data: {'id': id},
							dataType: 'json',
							success: function (data) {
								if (data.success == true) {
									var cfg_number = $("#CFG_NUMBER").val();//数量精确位数；
									var resultData = data.attributes;
									var begQty = resultData.begQty;
									var endQty = resultData.endQty;
									secCoefficient = resultData.secCoefficient;//辅助单位的辅助单位换算率
									var rowIndex = resultData.rowIndex;

									$("input[name='tScPrcplyentry1List["+rowIndex+"].itemName']").val(itemName);
									$("input[name='tScPrcplyentry1List["+rowIndex+"].itemID']").val(itemid);
									setValOldIdAnOldName($("input[name='tScPrcplyentry1List["+rowIndex+"].itemName']"),itemid,itemName);

									$("input[name='tScPrcplyentry1List["+rowIndex+"].contact']").val(contact);
									$("input[name='tScPrcplyentry1List["+rowIndex+"].mobilePhone']").val(mobilePhone);
									$("input[name='tScPrcplyentry1List["+rowIndex+"].phone']").val(phone);
									$("input[name='tScPrcplyentry1List["+rowIndex+"].fax']").val(fax);
									$("input[name='tScPrcplyentry1List["+rowIndex+"].address']").val(address);

									$('input[name="tScPrcplyentry2List[' + rowIndex + '].itemNo"]').attr("readonly","readonly");//商品编号只读
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].itemID"]').val(id);//商品id

									$('input[name="tScPrcplyentry2List[' + rowIndex + '].itemNo"]').val(item.icitemNumber);//商品编号
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].itemName"]').val(item.icitemName);//商品名称
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].model"]').val(item.icitemModel);//规格
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].barCode"]').val(barCode);//条形码
									var begQty = item.begQty;
									var endQty = item.endQty;
									begQty = parseFloat(begQty).toFixed(cfg_number);
									endQty = parseFloat(endQty).toFixed(cfg_number);
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].begQty"]').val(begQty);
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].endQty"]').val(endQty);
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].price"]').val(price);//原价
									$('input[name="tScPrcplyentry2List[' + rowIndex + '].costPrice"]').val(costPrice);//成本单价

									$('input[name="tScPrcplyentry2List[' + rowIndex + '].newPrice"]').trigger("change");

									$('#tScPrcplyentry2List\\[' + rowIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
									$('#tScPrcplyentry2List\\[' + rowIndex + '\\]\\.unitID').combobox({
										disabled:true,
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
															$('input[name="tScPrcplyentry2List[' + rowIndex + '].barCode"]').val(barCode);//条形码
														}
													}
												});
											}
										}
									});
									$('#tScPrcplyentry2List\\[' + rowIndex + '\\]\\.unitID').combobox('setValue', unitId);
									setValOldIdAnOldName($('input[name="tScPrcplyentry2List[' + rowIndex + '].itemNo"]'), id, item.icitemNumber);
								}
							}
						});
					});
				}
				$("#selectOrderbutton").val("");
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
				$("#selectOrderbutton").val("");
			}
		}]
	}).zindex();
}

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
					$this.attr("onchange", "changePrice("+i+")");
				}
				if (name.indexOf("price") > -1) {
					$this.attr("onchange", "changePrice("+i+")");
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
				}else{
					if(onclick_str.indexOf(",")>0){
						var s = onclick_str.indexOf("(");
						var e = onclick_str.indexOf(",");
						var new_onclick_str = onclick_str.substring(s + 1, e);
						$this.attr("onclick",onclick_str.replace(new_onclick_str, i));
					}else{
						var s = onclick_str.indexOf("(");
						var e = onclick_str.indexOf(")");
						var new_onclick_str = onclick_str.substring(s+1,e);
						$this.attr("onclick",onclick_str.replace(new_onclick_str,i));
					}
				}
				//} else{
				//	var s = onclick_str.indexOf("(");
				//	var e = onclick_str.indexOf(")");
				//	var new_onclick_str = onclick_str.substring(s+1,e);
				//	$this.attr("onclick",onclick_str.replace(new_onclick_str,i));
				//}
				/*else {
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
				}*/
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
					var e = blur_str.indexOf(",");
					var new_blur = blur_str.substring(s + 1, e);
					$this.attr("onblur", blur_str.replace(new_blur, i));
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i + 1);
		$("input[name='" + indexName + "[" + i + "].indexNumber']").val(i + 1);

	});
	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1)) {
			$('#tScPrcplyentry2List\\[' + i + '\\]\\.unitID').combobox({
				width:'84',
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
		$("input[name='tScPrcplyentry2List[#index#].begDate']").val(year+"-"+month+"-"+date);
		$("input[name='tScPrcplyentry2List[#index#].endDate']").val(year+"-"+month+"-"+date);
	}else{
		$("input[name='tScPrcplyentry2List[#index#].begDate']").val(year+"-"+month+"-"+date);
		$("input[name='tScPrcplyentry2List[#index#].endDate']").val(year+"-"+month+"-"+date);
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

//商品选择框
var rowIndex = 0;
function keyDownInfo(index,name,evt){
	var evt = (evt)?evt:((window.event)?window.event:"");
	var code =evt.keyCode?evt.keyCode:evt.which;
	if(code == 13){
		if(name == "item"){
			selectIcitemDialog(index);
		}else if(name == "unit"){
			selectUnitDialog(index);
		}else if(name == "custom"){
			selectCustomDialog(index);
		}
	}
}

function selectIcitemDialog(index){
	var itemNo = $("input[name='tScPrcplyentry2List["+index+"].itemNo']").val();
	var url = 'tScIcitemController.do?goSelectDialog&number=' + itemNo;
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
				debugger;
				iframe = this.iframe.contentWindow;
				var items = iframe.getMoreSelectionData();
				if (items != "") {
					var nextTrLength =  $('input[name="tScPrcplyentry2List['+index+'].itemNo"]').parent().parent().nextAll().length;//判断后面的行数
					var itemsLength = items.length;
					if (itemsLength > nextTrLength) {//增行
						var appendLength = itemsLength - nextTrLength;
						for (var j = 0; j < appendLength - 1; j++) {
							clickAddEntryBtns(index);//相当于在之后增加一行
						}
					}

					$.each(items,function(i,item){
						var rowindex = parseInt(index) + i;
						var id = item.id;//id
						var number = item.number;//编码
						var name = item.name;//名称
						var model = item.model;//规格
						var price = 0;//原价
						var costPrice = item.costPrice;//成本单价
						var cfg_price = $("#CFG_UNITP_RICE").val();
						costPrice = parseFloat(costPrice).toFixed(cfg_price);
						var stockId = "";
						var batchNo = "";
						var url = "tScIcitemController.do?getIcItemForQuoteItems";
						var rowIndex = 0;
						$.ajax({
							type: 'POST',
							url: url,
							dataType: 'json',
							data: {'id': id,'rowIndex':rowindex},
							cache: false,
							success: function (data) {
								var resultData = data.attributes;
								var unitId = resultData.unitId; //调用商品单位的默认销售单位
								var barCode = resultData.barCode;//条码
								rowIndex = resultData.rowIndex;

								$("input[name='tScPrcplyentry2List[" + rowIndex + "].itemNo']").val(number);
								$("input[name='tScPrcplyentry2List[" + rowIndex + "].itemID']").val(id);
								$("input[name='tScPrcplyentry2List[" + rowIndex + "].model']").val(model);
								$("input[name='tScPrcplyentry2List[" + rowIndex + "].itemName']").val(name);
								$("input[name='tScPrcplyentry2List[" + rowIndex + "].barCode']").val(barCode);
								$("input[name='tScPrcplyentry2List[" + rowIndex + "].price']").val(0);
								$("input[name='tScPrcplyentry2List[" + rowIndex + "].costPrice']").val(costPrice);

								$('#tScPrcplyentry2List\\[' + rowIndex + '\\]\\.unitID').combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + item.id);
								$('#tScPrcplyentry2List\\[' + rowIndex + '\\]\\.unitID').combobox({
									onChange: function (newV, oldV) {
										if (oldV != newV) {
											var changeUrl = "tScIcitemController.do?getChangeInfo";
											$.ajax({
												type: 'POST',
												url: changeUrl,
												dataType: 'json',
												data: {'unitId': newV},
												cache: false,
												success: function (data) {
													if(data.success == true){
														var attributes = data.attributes;
														var barCode = attributes.barCode;
														$("input[name='tScPrcplyentry2List[" + rowIndex + "].barCode']").val(barCode);
													}
												}
											});
										}
									}
								});
								$('#tScPrcplyentry2List\\[' + rowIndex + '\\]\\.unitID').combobox("setValue", unitId);
								setValOldIdAnOldName($("input[name='tScPrcplyentry2List[" + rowIndex + "].itemNo']"), item.id,item.number);
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

//数量发生改变时，取报价单单价
var rowInfo = {};
function getPriceByQty(index){
	var customerId = $("#itemID").val();
	var begQty = $("input[name='tScPrcplyentry2List["+index+"].begQty']").val();
	var endQty = $("input[name='tScPrcplyentry2List["+index+"].endQty']").val();


	if(begQty < 0 || endQty < 0){
		tip("数量不能为负数！");
		return false;
	}
	var qty = endQty - begQty;
	if(endQty == 0){
		qty = 0;
	}
	var itemId = $("input[name='tScPrcplyentry2List["+index+"].itemID']").val();//商品
	var unitId = $("#tScPrcplyentry2List\\["+index+"\\]\\.unitID").combobox("getValue");
	//var itemNo = $("input[name='tScPrcplyentry2List["+index+"].itemNo']").val();//商品编号
	var tranType = $("#tranType").val();
	if(itemId && qty && customerId && unitId){
		/*$.ajax({
			type: 'POST',
			url: 'tScPrcplyController.do?getPrice',
			async: false,
			cache: false,
			data: {'tranType':tranType,'customerId': customerId,'qty':qty,'itemId':itemId,'unitId':unitId},
			dataType: 'json',
			success: function (data) {
				var resultData = data.attributes;
				var price = resultData.price;
				if (data.success == true) {
					$("input[name='tScPrcplyentry2List["+index+"].price']").val(price);
					$("input[name='tScPrcplyentry2List["+index+"].price']").trigger("change");
				}else{
					$("input[name='tScPrcplyentry2List["+index+"].price']").val(price);
					$("input[name='tScPrcplyentry2List["+index+"].price']").trigger("change");
				}
			}
		});*/
	}
}

//单位选择框
function selectUnitDialog(index){
	var unitName = $("input[name='tScPrcplyentry2List["+index+"].unitName']").val();
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
				$("input[name='tScPrcplyentry2List["+index+"].unitName']").val(item.name);
				$("input[name='tScPrcplyentry2List["+index+"].unitID']").val(item.id);
				setValOldIdAnOldName($("input[name='tScPrcplyentry2List["+index+"].unitName']"), item.id, item.name);
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
	checkInput( $('input[name="tScPrcplyentry2List['+rowIndex+'].'+id+'"]'),$('input[name="tScPrcplyentry2List['+rowIndex+'].'+name+'"]'));
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

//
function changePrice(index){
	var newPrice = parseFloat($("input[name='tScPrcplyentry2List["+index+"].newPrice']").val());//新价
	var price =  parseFloat($("input[name='tScPrcplyentry2List["+index+"].price']").val());//原价
	var costPrice =  parseFloat($("input[name='tScPrcplyentry2List["+index+"].costPrice']").val());//成本单价

	var disCountRate = 0;
	var grossper = 0;
	var newGrossper = 0;
	//if(newPrice && price && costPrice){
		if((price!=0 && !isNaN(price))){
			disCountRate = (newPrice/price*100);
			grossper = ((price-costPrice)/price*100);
		}else{
			price = 0;
		}
		if(newPrice!=0 && !isNaN(newPrice)){
			newGrossper = ((newPrice-costPrice)/newPrice*100);
		}else{
			newPrice = 0;
		}
		var cfg_price = $("#CFG_UNITP_RICE").val();
		var cfg_disCountRate = $("#CFG_DISCOUNT_RATE").val();
		var cfg_other = $("#CFG_OTHER").val();
		$("input[name='tScPrcplyentry2List["+index+"].differPrice']").val((price-newPrice).toFixed(cfg_price));
		$("input[name='tScPrcplyentry2List["+index+"].differPrice']").trigger("input");
		$("input[name='tScPrcplyentry2List["+index+"].disCountRate']").val(disCountRate.toFixed(cfg_disCountRate));
		$("input[name='tScPrcplyentry2List["+index+"].disCountRate']").trigger("input");
		$("input[name='tScPrcplyentry2List["+index+"].grossper']").val(grossper.toFixed(cfg_other));
		$("input[name='tScPrcplyentry2List["+index+"].grossper']").trigger("input");
		$("input[name='tScPrcplyentry2List["+index+"].newGrossper']").val(newGrossper.toFixed(cfg_other));
		$("input[name='tScPrcplyentry2List["+index+"].newGrossper']").trigger("input");
	//}
}

function selectCustomDialog(index){
	var customName = $("input[name='tScPrcplyentry1List["+index+"].itemName']").val();
	var url = "tScEmpController.do?goselectdeptIdNameDialog&name="+customName;
	var width = 885;
	var height = 500;
	var title = "职员";
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
				$("input[name='tScPrcplyentry1List["+index+"].itemName']").val(item.name);
				$("input[name='tScPrcplyentry1List["+index+"].itemID']").val(item.id);
				setValOldIdAnOldName($("input[name='tScPrcplyentry1List["+index+"].itemName']"), item.id, item.name);
			},
			focus: true
		}, {
			name: '取消',
			callback: function () {
			}
		}]
	}).zindex();
}
//选择客户
function selectItemNameDialog(index) {
	var itemName = $('input[name="tScPrcplyentry1List[' + index + '].itemName"]').val();
	var url = 'tScPrcplyController.do?goSelectDialog&name=' + itemName;
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
				debugger;
				iframe = this.iframe.contentWindow;
				var items = iframe.getMoreSelectionData();
				if (items != "") {
					var nextTrLength =  $('input[name="tScPrcplyentry1List['+index+'].itemName"]').parent().parent().nextAll().length;//判断后面的行数
					var itemLength = items.length;
					if (itemLength > nextTrLength) {//增行
						var appendLength = itemLength - nextTrLength;
						for (var j = 0; j < appendLength - 1; j++) {
							clickAddEntryBtn(index,"tScPrcplyentry1List");//相当于在之后增加一行
						}
					}
				}
				$.each(items,function(i,item){
					var rowindex = parseInt(index) + i;
					var contact = item.contact; //联系人
					var mobilePhone = item.mobilephone;//手机号码
					var fax = item.fax; //传真
					var phone = item.phone;//电话
					var address = item.address;//地址

					$('input[name="tScPrcplyentry1List[' + rowindex + '].itemName"]').val(item.name);
					$('input[name="tScPrcplyentry1List[' + rowindex + '].itemID"]').val(item.id);
					setValOldIdAnOldName($('input[name="tScPrcplyentry1List[' + rowindex + '].itemName"]'), item.id, item.name);

					$('input[name="tScPrcplyentry1List[' + rowindex + '].contact"]').val(contact);
					$('input[name="tScPrcplyentry1List[' + rowindex + '].mobilePhone"]').val(mobilePhone);
					$('input[name="tScPrcplyentry1List[' + rowindex + '].phone"]').val(phone);
					$('input[name="tScPrcplyentry1List[' + rowindex + '].fax"]').val(fax);
					$('input[name="tScPrcplyentry1List[' + rowindex + '].address"]').val(address);
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

/**
 * 精确数量小数位
 * @param rowIndex 行下标
 * @param type 切换类型
 * @param field 字段名
 */
function checkNum(rowIndex,type,field){
	var num = $("#CFG_NUMBER").val();
	if("price" == type){
		num = $("#CFG_UNITP_RICE").val();
	}
	var value = $("input[name='tScPrcplyentry2List["+rowIndex+"]."+field+"']").val();
	if(!isNaN(value)){
		value = parseFloat(value).toFixed(num);
		$("input[name='tScPrcplyentry2List["+rowIndex+"]."+field+"']").val(value);
		$("input[name='tScPrcplyentry2List["+rowIndex+"]."+field+"']").trigger("change");
	} else {
		$("input[name='tScPrcplyentry2List["+rowIndex+"]."+field+"']").val(0);
	}
}