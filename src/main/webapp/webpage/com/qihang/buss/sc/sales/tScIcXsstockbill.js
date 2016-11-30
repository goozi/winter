var h = ($(window).height() - 295)/2;
var rowIndex = 0;
//初始化下标
function resetTrNum(tableId,index,indexName,isAdd) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input,span, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			var comboboName = $this.attr("comboname");
			var change_ = $this.attr("onchange");
			if(null != comboboName){
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

					var stockId = $("input[name='"+indexName+"["+i+"].stockId']").val();
					var stockName = $("input[name='"+indexName+"["+i+"].stockName']").val();
					setValOldIdAnOldName($("input[name='"+indexName+"["+i+"].stockName']"),stockId,stockName);
					$this.attr("onblur","orderListStockBlur("+i+",'stockId','stockName','"+indexName+"')");
				}else if(name.indexOf("itemNo") > -1){
					$this.attr("onblur","orderListStockBlur("+i+",'itemId','itemNo','"+indexName+"')");
				}else if(name.indexOf("inTaxAmount") > -1){
					$this.attr("onchange","setAllAmount()");
				}else if(name.indexOf("kfDateType") > -1 || name.indexOf("kfDate") > -1 || name.indexOf("kfPeriod") > -1){
					$this.attr("onchange","setPeriodDate("+i+",'"+indexName+"')");
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
					if(name.indexOf("kfDate") < 0) {
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
		checkAllowAddLine(i);
		$("input[name='"+indexName+"["+i+"].findex']").val(i+1);
		$(this).find('div[name=\'xh\']').html(i+1);
	});

	$tbody.find('>tr').each(function(i){
		if(i == (parseInt(index)+1) && isAdd) {
			debugger;
			var num = indexName.replace(/[^0-9]/ig,"");
			$("#unitId"+num+"\\[" + i + "\\]").combobox({
				width:54,
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
	$("#itemName").keypress(function (e) {
		if (e.keyCode == 13) {
			selectItemNameDialog();
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


	$("#deptName").blur(function(){
		checkInput($("#deptId"), $("#deptName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empId"), $("#empName"));
	})
	$("#accountName").blur(function(){
		checkInput($("#accountID"), $("#accountName"));
	})
	var checkState = $("#checkState").val();
	if(checkState == 2){
		$("#date").attr("readonly","readonly");
		$("#itemName").attr("readonly","readonly");
		$("#empName").attr("readonly","readonly");
		$("#deptName").attr("readonly","readonly");
		$("#contact").attr("readonly","readonly");
		$("#mobilePhone").attr("readonly","readonly");
		$("#phone").attr("readonly","readonly");
		$("#address").attr("readonly","readonly");
		$("#rebateAmount").attr("readonly","readonly");
		$("#explanation").attr("readonly","readonly");
	}
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
		option1.value="103";
		option1.innerText="销售出库单";
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
	var date = $("input[name='date']").val();
	date = new Date(date);
	var year = date.getFullYear();
	var month = (date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
	var dateS = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var id = $("#id").val();
	if(!id){
		$("input[name='tScIcXsstockbillentry1List[#index#].kfDate']").val(year+"-"+month+"-"+dateS);
		$("input[name='tScIcXsstockbillentry2List[#index#].kfDate']").val(year+"-"+month+"-"+dateS);
	}else{
		var dateValue = $("input[name='date']").val();
		dateValue = new Date(dateValue);
		var year = dateValue.getFullYear();
		var month = (dateValue.getMonth()+1) < 10 ? "0"+(dateValue.getMonth()+1) : (dateValue.getMonth()+1);
		var date = dateValue.getDate() < 10 ? "0"+dateValue.getDate() : dateValue.getDate();
		dateValue = year+"-"+month+"-"+date;
		$("input[name='tScIcXsstockbillentry1List[#index#].kfDate']").val(dateValue);
		$("input[name='tScIcXsstockbillentry2List[#index#].kfDate']").val(dateValue);
	}
}

//赠品设置单价为0；
function setPrice(index){
	var selected = document.getElementsByName("tScIcXsstockbillentry1List["+index+"].freeGifts_");
	var i = selected[0].selectedIndex;
	var value = selected[0].options[i].value;
	$("input[name='tScIcXsstockbillentry1List["+index+"].freeGifts']").val(value);
	if(value == 1){
		$("input[name='tScIcXsstockbillentry1List["+index+"].taxPriceEx']").val(0);//单价
		onPriceChange(index);
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
	var url = 'tScOrganizationController.do?goSelectDialog&name=' + itemName;
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
				$("#itemId").val(item.id);
				setValOldIdAnOldName($("#itemName"), item.id, item.name);
				var iscreditmgr = item.iscreditmgr;//是否启用信用
				var creditamount = item.creditamount;//信用额度
				var countAmount = item.countAmount;//已执行金额
				var contact = item.contact;
				var mobilePhone = item.mobilephone;
				var phone = item.phone;
				var address = item.address;
				var fax = item.fax;
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
function selectStockDialog(index,entryName) {
	var itemName = $("input[name='"+entryName+"List["+index+"].stockName']").val();
	if(!itemName){
		itemName="";
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
					$tbody = $("#add_"+entryName+"_table");
					var length = $tbody[0].rows.length;
					$("input[name='"+entryName+"List[#index#].stockId']").val(item.id);
					$("input[name='"+entryName+"List[#index#].stockName']").val(item.name);
					for (var i = 0; i < length; i++) {
						$("input[name='"+entryName+"List[" + i + "].stockId']").val(item.id);
						$("input[name='"+entryName+"List[" + i + "].stockName']").val(item.name);
						setValOldIdAnOldName($("input[name='"+entryName+"List[" + i + "].stockName']"), item.id, item.name);
					}
					setValOldIdAnOldName($("#stockName"), item.id, item.name);
				}else{
					$("input[name='"+entryName+"List[" + index + "].stockId']").val(item.id);
					$("input[name='"+entryName+"List[" + index + "].stockName']").val(item.name);
					setValOldIdAnOldName($("input[name='"+entryName+"List[" + index + "].stockName']"), item.id, item.name);
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
	var priceEx = $("input[name='tScIcXsstockbillentry1List["+index+"].taxPriceEx']").val();//单价
	if(priceEx > 0){
		$("select[name='tScIcXsstockbillentry1List["+index+"].freeGifts_']").val(0);
		$("inout[name='tScIcXsstockbillentry1List["+index+"].freeGifts']").val(0);
	}
	var qty = $("input[name='tScIcXsstockbillentry1List["+index+"].qty']").val();//数量
	var discountRate = $("input[name='tScIcXsstockbillentry1List["+index+"].discountRate']").val();//折扣率
	var taxRate = $("input[name='tScIcXsstockbillentry1List["+index+"].taxRate']").val();//税率
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
	$("input[name='tScIcXsstockbillentry1List["+index+"].price']").val(price.toFixed(2));//不含税单价
	$("input[name='tScIcXsstockbillentry1List["+index+"].amount']").val(amount.toFixed(2));//不含税金额
	$("input[name='tScIcXsstockbillentry1List["+index+"].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
	$("input[name='tScIcXsstockbillentry1List["+index+"].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
	$("input[name='tScIcXsstockbillentry1List["+index+"].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
	$("input[name='tScIcXsstockbillentry1List["+index+"].taxPrice']").val(disprice.toFixed(2));//折后单价
	$("input[name='tScIcXsstockbillentry1List["+index+"].taxAmount']").val(taxAmount.toFixed(2));//税额
	$("input[name='tScIcXsstockbillentry1List["+index+"].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
	setAllAmount();
}


function selectInventoryInfo(index,entryName){
	var itemId = $("input[name='"+entryName+"["+index+"].itemId']").val();//商品id
	var stockId = $("input[name='"+entryName+"["+index+"].stockId']").val();//仓库id
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
					$("input[name='"+entryName+"[" + index + "].kfDate']").val(kfDate);
					$("input[name='"+entryName+"[" + index + "].kfDate']").trigger("change");
				}
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


function selectIcitemDialog(index,entryName){
	var itemNo = $("input[name='"+entryName+"["+index+"].itemNo']").val();
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
				var nextTrLength =  $('input[name="'+entryName+'['+index+'].findex"]').parent().nextAll().length;//判断后面的行数
				var newLength = itemLength-nextTrLength-1
				for(var j=0 ; j<newLength ; j++){
					if(entryName.indexOf("1") > -1) {
						clickAddTScIcXsstockbillentry1Btn(index);
					}else{
						clickAddTScIcXsstockbillentry2Btn(index);
					}
				}
				var sonId = $("#sonId").val();
				$.each(itemList,function(i,item){
					//var length = $("#add_tScPoStockbillentry_table").find(">tr").length;
					//if(i > 0 ){
					//	clickAddEntryBtn(index);
					//	index ++;
					//}
					//var item = itemList[i];
					var rowindex = parseInt(index)+i;
					var id = item.id;//id
					var number = item.number;//编码
					var name = item.name;//名称
					var model = item.model;//规格
					var kfPeriod = item.kFPeriod;//保质期
					var kfDateType = item.kFDateType;//保质期类型
					var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
					var batchManager = item.batchManager;//是否批号管理
					var taxRate = item.taxRateOut;//销项税率
					var stockSonId = item.stockSonId;//商品默认仓库分支机构id
					var url = "tScIcitemController.do?getItemInfo&id=" + id+"&rowIndex="+rowindex+"&tranType=103";
					$.ajax({
						url: url,
						dataType: 'json',
						cache: false,
						success: function (data) {
							var attr = data.attributes;
							var xsLimitPrice = attr.xsLimitPrice;
							var basicCoefficient = attr.basicCoefficient;
							var barCode = attr.barCode;
							var unitId = attr.unitId;
							var cofficient = attr.cofficient;
							var secUnitId = attr.secUnitId;
							var secCoefficient = attr.secCoefficient;
							var basicUnitId = attr.basicUnitId;
							var itemId = attr.id;
							var rowIndex = attr.rowIndex;
							var stockId = attr.stockId;//默认仓库id
							var stockName = attr.stockName;//默认仓库名称
							var chkId = $("input[name='"+entryName+"[" + rowIndex + "].stockId']").val();
							if(!chkId) {
								if(sonId == stockSonId) {
									$("input[name='" + entryName + "[" + rowIndex + "].stockId']").val(stockId);
									$("input[name='" + entryName + "[" + rowIndex + "].stockName']").val(stockName);
								}
							}
							setValOldIdAnOldName($("input[name='" + entryName + "[" + rowIndex + "].stockName']"),stockId,stockName);
							$("input[name='"+entryName+"[" + rowIndex + "].taxRate']").val(taxRate);//基本换算率
							$("input[name='"+entryName+"[" + rowIndex + "].basicCoefficient']").val(basicCoefficient);//基本换算率
							$("input[name='"+entryName+"[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);//销售限价
							$("input[name='"+entryName+"[" + rowIndex + "].itemNo']").val(number);
							setValOldIdAnOldName($("input[name='"+entryName+"[" + rowIndex + "].itemNo']"),id,number);
							$("input[name='"+entryName+"[" + rowIndex + "].itemId']").val(id);
							$("input[name='"+entryName+"[" + rowIndex + "].model']").val(model);
							$("input[name='"+entryName+"[" + rowIndex + "].itemName']").val(name);
							$("input[name='"+entryName+"[" + rowIndex + "].barCode']").val(barCode);
							$("input[name='"+entryName+"[" + rowIndex + "].coefficient']").val(cofficient);
							$("input[name='"+entryName+"[" + rowIndex + "].coefficient']").trigger("blur");
							$("input[name='"+entryName+"[" + rowIndex + "].secUnitId']").val(secUnitId);
							$("input[name='"+entryName+"[" + rowIndex + "].secCoefficient']").val(secCoefficient);
							$("input[name='"+entryName+"[" + rowIndex + "].secCoefficient']").trigger("change");
							$("input[name='"+entryName+"[" + rowIndex + "].basicUnitId']").val(basicUnitId);
							if("Y"==iSKFPeriod) {
								$("input[name='"+entryName+"[" + rowIndex + "].kfPeriod']").val(kfPeriod);
								$("select[name='" + entryName + "[" + rowIndex + "].kfDateType_']").val(kfDateType);
								$("input[name='" + entryName + "[" + rowIndex + "].kfDateType']").val(kfDateType);
								var kfDate = $("input[name='"+entryName+"[" + rowIndex + "].kfDate']").val();
								var interval = "";
								if (kfDateType == "0001") {
									interval = "y";
								} else if (kfDateType == "0002") {
									interval = "m";
								} else if (kfDateType == "0003") {
									interval = "d";
								}
								var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
								$("input[name='"+entryName+"[" + rowIndex + "].periodDate']").val(periodDate);
							}else{
								$("input[name='"+entryName+"[" + rowIndex + "].kfDate']").attr("readonly","readonly");
								$("input[name='"+entryName+"[" + rowIndex + "].kfDate']").attr("onClick","");
								$("input[name='"+entryName+"[" + rowIndex + "].kfDate']").val("");
								$("input[name='"+entryName+"[" + rowIndex + "].kfDate']").css("background-color","rgb(226,226,226)");
								$("input[name='"+entryName+"[" + rowIndex + "].kfPeriod']").val("");
								$("input[name='"+entryName+"[" + rowIndex + "].kfPeriod']").attr("readonly","readonly");
								$("input[name='"+entryName+"[" + rowIndex + "].kfPeriod']").css("background-color","rgb(226,226,226)");
								$("select[name='"+entryName+"[" + rowIndex + "].kfDateType']").val("");
								$("select[name='"+entryName+"[" + rowIndex + "].kfDateType']").attr("disabled","disabled");
								$("select[name='"+entryName+"[" + rowIndex + "].kfDateType']").css("background-color","rgb(226,226,226)");
								$("select[name='"+entryName+"[" + rowIndex + "].kfDateType_']").val("");
								$("select[name='"+entryName+"[" + rowIndex + "].kfDateType_']").attr("disabled","disabled");
								$("select[name='"+entryName+"[" + rowIndex + "].kfDateType_']").css("background-color","rgb(226,226,226)");
								$("input[name='"+entryName+"[" + rowIndex + "].periodDate']").val("");
							}
							if("N" == batchManager){
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").val("");
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").attr("readonly","readonly");
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").css("background-color","rgb(226,226,226)");
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").removeAttr("datatype");
							}else{
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").removeAttr("readonly");
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").attr("dataType","*");
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").css("background-color","rgb(255,255,255)");
								var batchName = "batchNo";
								if(entryName.indexOf("2") > -1){
									batchName = "batchNo2";
								}
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").attr("onkeypress","keyDownInfoI('"+rowIndex+"','"+batchName+"')");
								$("input[name='"+entryName+"[" + rowIndex + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+rowIndex+"','"+entryName+"')");
							}
							//var className = $("input[name='tScIcXsstockbillentry1List["+index+"].unitId']")[0].className;
							//if(className == "combo-value"){
							//	$("input[name='tScIcXsstockbillentry1List["+index+"].unitId']").combobox("reload");
							//}

							/*var aa = $("input[name='tScIcXsstockbillentry1List\\[" + index + "\\]\\.unitId']:first").combobox({
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
							 //				$("input[name='tScIcXsstockbillentry1List[" + index + "].barCode']").val(barCode);
							 //				$("input[name='tScIcXsstockbillentry1List[" + index + "].coefficient']").val(cofficient);
							 //			}
							 //		});
							 //	}
							 //}
							 });*/
							var num = entryName.replace(/[^0-9]/ig,"");
							$("#unitId"+num+"\\[" + rowIndex+"\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
							$("#unitId"+num+"\\[" + rowIndex+"\\]").combobox({
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
												var cgLimitPrice = attributes.xsLimitPrice;
												$("input[name='"+entryName+"[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);//采购限价
												$("input[name='"+entryName+"[" + rowIndex + "].barCode']").val(barCode);
												$("input[name='"+entryName+"[" + rowIndex + "].coefficient']").val(cofficient);
												$("input[name='"+entryName+"[" + rowIndex + "].coefficient']").trigger("change");
											}
										});
									}
								}
							})
							$("#unitId"+num+"\\[" + rowIndex+"\\]").combobox("setValue", unitId);
							//$("input[name='tScIcXsstockbillentry1List["+index+"].unitId']").combobox("setValue",unitId);
							setValOldIdAnOldName($("input[name='"+entryName+"[" + rowIndex + "].itemNo']"), itemId, number);
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

function setDateTypeInfo(index){
	var value = $("select[name='tScIcXsstockbillentry1List["+index+"].kfDateType_']").val();
	$("input[name='tScIcXsstockbillentry1List["+index+"].kfDateType']").val(value);
}

function editQtyChange(index){
	var oldValue = $("input[name='tScIcXsstockbillentry1List["+index+"].qty']").attr("oldvalue");
	var newValue = $("input[name='tScIcXsstockbillentry1List["+index+"].qty']").val();
	if(oldValue > newValue){
		tip("数量不可小于当前数量");
		$("input[name='tScIcXsstockbillentry1List["+index+"].qty']").val(oldValue);
		return;
	}
	setAllQty(index);
}

//汇总折扣金额
function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody1 = $("#tScIcXsstockbillentry1_table");
	var length = $tbody1[0].rows.length;
	//退货明细汇总数据
	var allAmount = 0;
	var allAmountTax = 0;
	var allQty = 0;

	//换货明细汇总数据
	var allAmount2 =0;
	var allAmountTax2 = 0;
	var allQty2 = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScIcXsstockbillentry1List[" + i + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScIcXsstockbillentry1List[" + i + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScIcXsstockbillentry1List[" + i + "].qty']").val();//数量
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

	$tbody2 = $("#tScIcXsstockbillentry2_table");
	length = $tbody2[0].rows.length;

	for(var j=0 ; j<length ; j++){
		var amount = $("input[name='tScIcXsstockbillentry2List[" + j + "].inTaxAmount']").val();//折后金额
		var amount2 = $("input[name='tScIcXsstockbillentry2List[" + j + "].taxAmountEx']").val();//金额
		var qty = $("input[name='tScIcXsstockbillentry2List[" + j + "].qty']").val();//数量
		if(!amount){
			amount = 0;
		}
		if(!amount2){
			amount2 = 0;
		}
		if(!qty){
			qty = 0;
		}
		allAmount2 += parseFloat(amount);
		allAmountTax2 += parseFloat(amount2);
		allQty2 += parseFloat(qty);
	}

	var diffAmount = allAmount2 - allAmount;

	var rebateAmount = $("#rebateAmount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
	diffAmount = diffAmount.toFixed(CFG_MONEY);
	$("#diffAmount").val(diffAmount);
	$("#amount").val(diffAmount);
	//if(allAmount > 0) {
	//$("#sumQty")[0].innerText = allQty;
	//$("#sumAmount")[0].innerText = allAmountTax;
	//$("#sumTaxAmount")[0].innerText=allAmount;
	diffAmount = diffAmount - rebateAmount;
	diffAmount = diffAmount.toFixed(CFG_MONEY);
	$("#allAmount").val(diffAmount);
	//}
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
				$("#accountID").val(item.id);
				$("#accountName").val(item.name);
				setValOldIdAnOldName($("#accountName"), item.id, item.name);
				checkcurPayAmount();
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
	var accountId = $("#accountID").val();
	var isCheckNegative = $("#isCheckNegative").val();
	if(accountId){
		var curPayAmount = $("#curPayAmount").val();
		if(curPayAmount <= 0){
			tip("本次收款不可小于0，请确认");
			return false;
		}
	}
	var $tbody = $("#tScIcXsstockbillentry1_table");
	var length = $tbody[0].rows.length;
	var checkItemName = "";
	var checkItemName2 = "";
	var checkValue = "";
	var $tbody2 = $("#tScIcXsstockbillentry2_table");
	var length2 = $tbody2[0].rows.length;

	for(var checkIndex=0 ; checkIndex<length ; checkIndex++){
		var price = $("input[name='tScIcXsstockbillentry1List["+checkIndex+"].taxPriceEx']").val();
		var itemName = $("input[name='tScIcXsstockbillentry1List["+checkIndex+"].itemName']").val();
		var xsLimitPrice = $("input[name='tScIcXsstockbillentry1List["+checkIndex+"].xsLimitPrice']").val();
		if(!xsLimitPrice){
			continue;
		}else{
			if(parseFloat(price) < parseFloat(xsLimitPrice)){
				checkItemName += itemName+",";
			}
		}
	}
	for(var checkIndex2=0 ; checkIndex2<length2 ; checkIndex2++){
		var price = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].taxPriceEx']").val();
		var itemName = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].itemName']").val();
		var xsLimitPrice = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].xsLimitPrice']").val();
		var itemId = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].itemId']").val();
		var stockId = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].stockId']").val();
		var qty = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].basicQty']").val();
		var batchNo = $("input[name='tScIcXsstockbillentry2List["+checkIndex2+"].batchNo']").val();
		if(isCheckNegative){
			checkValue += itemId+"#"+itemName+"#"+stockId+"#"+qty+"#"+batchNo+",";
		}
		if(!xsLimitPrice){
			continue;
		}else{
			if(parseFloat(price) < parseFloat(xsLimitPrice)){
				checkItemName2 += itemName+",";
			}
		}
	}
	if(isCheck || isCheck2) {
		if (checkItemName.length > 0 || checkItemName2.length > 0 || checkValue.length > 0) {
			if((checkItemName.length > 0 || checkItemName2.length > 0) && isCheck ) {
				var itemName = "";
				if (checkItemName.length > 0) {
					itemName += "退货商品[" + checkItemName + "] ";
				}
				if (checkItemName2.length > 0) {
					itemName += "换货商品[" + checkItemName2 + "] ";
				}
				var confirmStr = itemName + "的单价低于销售限价，是否继续保存？";
				$.dialog.confirm(confirmStr, function () {
						isCheck = false;
						$("#formobj").submit();
					}
					, function () {
						//
					})
			}else if(checkValue.length > 0){
				//出库时，校验数量是否超出库存
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
			}
			return false;
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
	//					var price = $("input[name='tScIcXsstockbillentry1List["+i+"].taxPriceEx']").val();
	//					if(!price || price == 0){
	//						itemName += $("input[name='tScIcXsstockbillentry1List["+i+"].itemName']").val()+",";
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
function setPeriodDate(index,entryName) {
	var kfDate = $("input[name='" + entryName + "[" + index + "].kfDate']").val();//生产日期
	var kfPeriod = $("input[name='" + entryName + "[" + index + "].kfPeriod']").val();//保质期
	var kfDateType = ""//保质期类型
	kfDateType = $("select[name='" + entryName + "[" + index + "].kfDateType_']").val();
	$("input[name='" + entryName + "[" + index + "].kfDateType']").val(kfDateType);
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

//选单
function selectBillDialog(){
	var itemId = $("#itemId").val();
	if(!itemId){
		tip("请选择客户后再进行选单操作");
		$("#chooseType").val("");
		return;
	}
	var sonId = $("#sonId").val();
	var tranType = $("#tranType").val();
	var url = "tScSlStockbillController.do?goSelectDialog&itemid="+itemId+"&trantype="+tranType+"&sonId="+sonId;
	var width = 800;
	var height = 500;
	var title = "销售出库单";
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
				var ids ="";
				for(var i=0 ; i< itemList.length ; i++){
					var item = itemList[i];
					ids += item.id+",";
				}
				var item = itemList[itemList.length-1];
				$("#classIDSrc").val(item.tranType);
				$("#idSrc").val(item.id);
				$("#billNoSrc").val(item.billNo);
				//$("#itemId").val(item.itemId);
				//$("#itemName").val(item.itemName);
				$("#itemName").attr("readonly","readonly");
				//setValOldIdAnOldName($("#itemName"), item.itemId, item.itemName);
				$("#deptId").val(item.deptid);
				$("#deptName").val(item.deptName);
				setValOldIdAnOldName($("#deptName"), item.deptid, item.deptName);
				$("#empId").val(item.empid);
				$("#empName").val(item.empName);
				setValOldIdAnOldName($("#empName"), item.empid, item.empName);
				$("#contact").val(item.contact);
				//$("#mobilePhone").val(item.mobilePhone);
				//$("#phone").val(item.phone);
				//$("#fax").val(item.fax);
				//$("#address").val(item.address);
				//var entryUrl = "tScPoStockbillController.do?loadEntryList&id="+ids+"&tranType="+tranType;
				//$.ajax({
				//	url: entryUrl,
				//	dataType: 'json',
				//	cache: false,
				//	async: false,
				//	success: function (data) {
				//		if(data.length > 0){
				var length = $("#add_tScIcXsstockbillentry1_table").find(">tr").length;
				length--;
				var id = $("input[name='tScIcXsstockbillentry1List["+length+"].itemId']").val();
				var dataLength = itemList.length;
				if(!id){
					dataLength--;
				}
				for(var k=0 ; k < dataLength ; k++){
					clickAddTScIcXsstockbillentry1Btn(length,"tScIcXsstockbillentry1")
				}
				if(id){
					length++;
				}
				for(var i=0 ; i<itemList.length ;i++) {
					var row = itemList[i];
					for(var k in row){
						if(k=="unitid"){
							$("#unitId1\\["+length+"\\]").combobox("reload","tScIcitemController.do?loadItemUnit&id=" + row.entryItemId);
							$("#unitId1\\[" + length+"\\]").combobox("setValue", row[k]);
							$("#unitId1\\[" + length+"\\]").combobox("disable");
						}else if( k=="kfdateTypeInfo"){
							if("N"==row.isKfperiod && k=="kfdateTypeInfo") {
								$("select[name='tScIcXsstockbillentry1List[" + length + "].kfDateType_']").val("");
								$("select[name='tScIcXsstockbillentry1List[" + length + "].kfDateType_']").attr("disabled","disabled");
								$("select[name='tScIcXsstockbillentry1List[" + length + "].kfDateType_']").css("background-color","rgb(226,226,226)");
							}else{
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDateType']").val(row[k]);
								$("select[name='tScIcXsstockbillentry1List[" + length + "].kfDateType_']").val(row[k]);
								$("select[name='tScIcXsstockbillentry1List[" + length + "].kfDateType_']").attr("disabled","disabled");
								$("select[name='tScIcXsstockbillentry1List[" + length + "].kfDateType_']").css("background-color","rgb(226,226,226)");

							}
						}else if(k=="kfperiod"){
							if("N"==row.isKfperiod) {
								//if(k.indexOf("Date") > -1){
								//	$("input[name='tScIcXsstockbillentry1List[" + length + "]." + k + "']").attr("readonly","readonly");
								//	$("input[name='tScIcXsstockbillentry1List[" + length + "]." + k + "']").css("background-color","rgb(226,226,226)");
								//	$("input[name='tScIcXsstockbillentry1List[" + length + "]." + k + "']").attr("onClick","");
								//	$("input[name='tScIcXsstockbillentry1List[" + length + "]." + k + "']").val("");
								//}else{
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfPeriod']").attr("readonly","readonly");
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfPeriod']").css("background-color","rgb(226,226,226)");
								//}
							}else{
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfPeriod']").val(row[k]);
							}
						}else if(k.indexOf("kfdate") > -1){
							if("N"== row.isKfperiod){
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").attr("readonly","readonly");
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").css("background-color","rgb(226,226,226)");
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").attr("onClick","");
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").val("");
							}else if("Y"==row.isKfperiod){
								var date = new Date(row[k]);
								var year = date.getFullYear();
								var mongth = ((date.getMonth()+1) < 10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1));
								var dates = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
								var newDate = year+"-"+mongth+"-"+dates;
								$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").val(newDate);
							}
						}else if(k=="qty"){
							var stockQty = row.commitQty;//退货数量
							var qty = row[k];
							var basicQty = row["basicQty"];
							var coe = row["coefficient"];
							qty = ((parseFloat(basicQty)-parseFloat(stockQty))/coe).toFixed(CFG_NUMBER);
							$("input[name='tScIcXsstockbillentry1List[" +length + "]." + k + "']").val(qty);
							$("input[name='tScIcXsstockbillentry1List[" +length + "].billQty']").val(row[k]);
						}
						else {
							$("input[name='tScIcXsstockbillentry1List[" +length + "]." + k + "']").val(row[k]);
						}
						//if(k == "entryItemNo"){
						//	$("input[name='tScIcXsstockbillentry1List[" + length + "].itemNo']").attr("readonly","readonly");
						//	$("input[name='tScIcXsstockbillentry1List[" + length + "].itemNo']").css("background-color","rgb(226,226,226)");
						//}
					}
					if("Y"==row.isKfperiod) {
						setPeriodDate(length,"tScIcXsstockbillentry1List");
					}
					if("N"==row.batchManager){
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").attr("readonly","readonly");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").css("background-color","rgb(226,226,226)");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").removeAttr("datatype");
					}else{
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").val(row.batchNo);
						//$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").attr("readonly","readonly");
						//$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").css("background-color","rgb(226,226,226)");
						//$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").removeAttr("datatype");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").removeAttr("readonly");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").css("background-color", "white");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").attr("datatype","*");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + length + "','batchNo')");
						$("input[name='tScIcXsstockbillentry1List[" + length + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+length+"','tScIcXsstockbillentry1List')");
					}

					//单价
					$("input[name='tScIcXsstockbillentry1List[" + length + "].taxPriceEx']").val(row.taxPriceEx);

					$("input[name='tScIcXsstockbillentry1List[" + length + "].kfPeriod']").attr("readonly","readonly");
					$("input[name='tScIcXsstockbillentry1List[" + length + "].kfPeriod']").css("background-color","rgb(226,226,226)");

					$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").attr("readonly","readonly");
					$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").removeAttr("onclick");
					$("input[name='tScIcXsstockbillentry1List[" + length + "].kfDate']").css("background-color","rgb(226,226,226)");

					$("input[name='tScIcXsstockbillentry1List["+length+"].itemId']").val(row.entryItemId);
					$("input[name='tScIcXsstockbillentry1List["+length+"].itemNo']").val(row.entryItemNo);
					$("input[name='tScIcXsstockbillentry1List["+length+"].itemName']").val(row.entryItemName);
					setValOldIdAnOldName($("input[name='tScIcXsstockbillentry1List["+length+"].itemNo']"),row.entryItemId,row.entryItemNo);

					$("input[name='tScIcXsstockbillentry1List["+length+"].stockId']").val(row.entryStockId);
					$("input[name='tScIcXsstockbillentry1List["+length+"].stockName']").val(row.entryStockName);
					setValOldIdAnOldName($("input[name='tScIcXsstockbillentry1List["+length+"].stockName']"),row.entryStockId,row.entryStockName);
					$("input[name='tScIcXsstockbillentry1List["+length+"].stockName']").attr("readonly","readonly");
					$("input[name='tScIcXsstockbillentry1List["+length+"].stockName']").css("background-color","rgb(226,226,226)");

					//var basicCoefficient = $("input[name='tScIcXsstockbillentry1List[" + length + "].basicCoefficient']").val();
					//var discountPrice = $("input[name='tScIcXsstockbillentry1List[" + length + "].discountPrice']").val();
					//var costPrice = parseFloat(discountPrice)/parseFloat(basicCoefficient)
					//var qty = $("input[name='tScIcXsstockbillentry1List[" + length + "].qty']").val();
					//$("input[name='tScIcXsstockbillentry1List[" + length + "].costPrice']").val(costPrice.toFixed(2));
					//$("input[name='tScIcXsstockbillentry1List[" + length + "].costAmount']").val((costPrice.toFixed(2)*qty).toFixed(2))
					//$("input[name='tScIcXsstockbillentry1List[" + i + "].idSrc']").val(item.id);
					//$("input[name='tScIcXsstockbillentry1List[" + i + "].billNoSrc']").val(item.billNo);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].idSrc']").val(row.id);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].billNoSrc']").val(row.billNo);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].classIDSrc']").val(row.tranType);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].entryIdSrc']").val(row.entryId);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].billNoOrder']").val(row.billNoOrder);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].idOrder']").val(row.idOrder);
					$("input[name='tScIcXsstockbillentry1List[" + length + "].entryIdOrder']").val(row.entryIdOrder);
					setFunctionInfo(length,"tScIcXsstockbillentry1List");
					$("input[name='tScIcXsstockbillentry1List[" + length + "].qty']").trigger("change");
					$("input[name='tScIcXsstockbillentry1List[" + length + "].taxPriceEx']").trigger("change");
					$("input[name='tScIcXsstockbillentry1List[" + length + "].taxAmountEx']").trigger("change");
					var tranTypeName = row.tranTypeName;
					$("input[name='tScIcXsstockbillentry1List[" + length + "].classIDName']").val(tranTypeName);
					$("#classIDName").val(tranTypeName);
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
	$("#chooseType").val("");
}

//更改优惠金额时执行操作
function changeAllAmount(){
	var rebateAmount = $("#rebateAmount").val();
	if(!rebateAmount){
		rebateAmount = 0;
	}
	var allAmount = $("#diffAmount").val();
	if(allAmount){
		allAmount = parseFloat(allAmount) - parseFloat(rebateAmount);
		$("#allAmount").val(allAmount);
	}
}

//表格校验事件
function orderListStockBlur(rowIndex,id,name,entryName){
	checkInput( $('input[name="'+entryName+'['+rowIndex+'].'+id+'"]'),$('input[name="'+entryName+'['+rowIndex+'].'+name+'"]'));
}

//判断是否允许增行操作
function checkAllowAddLine(index){
	var isAddLine = true;
	if(!isAddLine){
		$("#addTScOrderentryBtn\\["+index+"\\]").css("display","none");
		$("input[name='tScIcXsstockbillentry1List["+index+"].itemNo']").attr("readonly","readonly");
	}
}


function afterAudit(){
	var id = $("#id").val();
	var url = "tScIcXsstockbillController.do?afterAudit&audit=1&id=" + id;
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		success: function (d) {
			//
		}
	});
}

function afterUnAudit(){
	var id = $("#id").val();
	var url = "tScIcXsstockbillController.do?afterAudit&audit=0&id=" + id;
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		success: function (d) {
			//
		}
	});
}

//审核前校验
function checkAudit(){
	var isCheckNegative = $("#isCheckNegative").val();
	if(isCheckNegative == "true") {
		var tranType = $("#tranType").val();
		var isContinue = true;
		var itemName = "";
		var billId = $("#id").val();
		/**
		 *参数说明
		 * id 单据id
		 * tranTyoe 单据类型
		 * tableName 明细表名
		 * parentId 关联id
		 */
		$.ajax({
			async: false,
			url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=t_sc_ic_Xsstockbillentry1&parentId=fid",
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
}

//审核前校验
function checkUnAudit(){
	var isCheckNegative = $("#isCheckNegative").val();
	if(isCheckNegative == "true") {
		var tranType = $("#tranType").val();
		var isContinue = true;
		var itemName = "";
		var billId = $("#id").val();
		/**
		 *参数说明
		 * id 单据id
		 * tranTyoe 单据类型
		 * tableName 明细表名
		 * parentId 关联id
		 */
		$.ajax({
			async: false,
			url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=t_sc_ic_Xsstockbillentry2&parentId=fid",
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
			return "该单据反审核后,商品【"+itemName+"】将出现负库存，不可反审核，请确认";
		}
	}else{
		return false;
	}
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
				var freight = json.freight;
				var curPayAmount = json.curPayAmount;
				$("#freight").val(freight);
				var CFG_MONEY = $("#CFG_MONEY").val();
				var allAmount = $("#allAmount").val() || 0;
				var rebateamount = $("#rebateAmount").val() || 0;
				allAmount = (parseFloat(allAmount) + parseFloat(freight)).toFixed(CFG_MONEY);
				$("#diffAmount").val(0);
				allAmount = (parseFloat(allAmount) - parseFloat(rebateamount)).toFixed(CFG_MONEY);
				if(curPayAmount){
					allAmount = (parseFloat(allAmount) - parseFloat(curPayAmount)).toFixed(CFG_MONEY);
				}
				$("#allAmount").val(allAmount);
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
		$("#curPayAmount").removeAttr("readonly");
		$("#curPayAmount").css("background-color","white");
		$("#curPayAmount").attr("datatype","vInt");
	}else{
		$("#curPayAmount").attr("readonly","readonly");
		$("#curPayAmount").css("background-color","rgb(226,226,226)");
		$("#curPayAmount").val("");
		$("#curPayAmount").removeAttr("datatype");
	}
}

//草稿恢复后操作事项
function doTempRecoveryExt() {
	$tbody = $("#add_tScIcXsstockbillentry1_table");
	var length1 = $tbody[0].rows.length;
	for (var i = 0; i < length1; i++) {
		var isKf = $("input[name='tScIcXsstockbillentry1List[" + i + "].isKFPeriod']").val();
		var isBatch = $("input[name='tScIcXsstockbillentry1List[" + i + "].batchManager']").val();
		if ("N" == isKf || "" == isKf) {
			$("input[name='tScIcXsstockbillentry1List[" + i + "].kfDate']").val("");
			$("input[name='tScIcXsstockbillentry1List[" + i + "].kfDate']").removeAttr("onclick");
			$("input[name='tScIcXsstockbillentry1List[" + i + "].kfDate']").attr("readonly", "readonly");
			$("input[name='tScIcXsstockbillentry1List[" + i + "].kfDate']").css("background-color", "rgb(226,226,226)");
		}
		if ("N" == isBatch || "" == isBatch) {
			$("input[name='tScIcXsstockbillentry1List[" + i + "].batchNo']").val("");
			$("input[name='tScIcXsstockbillentry1List[" + i + "].batchNo']").attr("readonly", "readonly");
			$("input[name='tScIcXsstockbillentry1List[" + i + "].batchNo']").css("background-color", "rgb(226,226,226)");
		} else {
			$("input[name='tScIcXsstockbillentry1List[" + i + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + i + "','batchNo')");
		}
		var kfDateType = $("input[name='tScIcXsstockbillentry1List[" + i + "].kfDateType']").val();
		$("select[name='tScIcXsstockbillentry1List[" + i + "].kfDateType_']").val(kfDateType);


		//$("input[name='tScIcJhstockbillentry1List[" + i + "].classIDSrc']").val("");
		//$("input[name='tScIcJhstockbillentry1List[" + i + "].idSrc']").val("");
		//$("input[name='tScIcJhstockbillentry1List[" + i + "].entryIdSrc']").val("");
		//$("input[name='tScIcJhstockbillentry1List[" + i + "].idOrder']").val("");
		//$("input[name='tScIcJhstockbillentry1List[" + i + "].entryIdOrder']").val("");
		//$("input[name='tScIcJhstockbillentry1List[" + i + "].billNoSrc']").val("");
		//$("input[name='tScIcJhstockbillentry1List[" + i + "].billNoOrder']").val("");
	}

	$tbody = $("#add_tScIcXsstockbillentry2_table");
	var length2 = $tbody[0].rows.length;
	for (var i = 0; i < length2; i++) {
		var isKf = $("input[name='tScIcXsstockbillentry2List[" + i + "].isKFPeriod']").val();
		var isBatch = $("input[name='tScIcXsstockbillentry2List[" + i + "].batchManager']").val();
		if ("N" == isKf || "" == isKf) {
			$("input[name='tScIcXsstockbillentry2List[" + i + "].kfDate']").val("");
			$("input[name='tScIcXsstockbillentry2List[" + i + "].kfDate']").removeAttr("onclick");
			$("input[name='tScIcXsstockbillentry2List[" + i + "].kfDate']").attr("readonly", "readonly");
			$("input[name='tScIcXsstockbillentry2List[" + i + "].kfDate']").css("background-color", "rgb(226,226,226)");
		}
		if ("N" == isBatch || "" == isBatch) {
			$("input[name='tScIcXsstockbillentry2List[" + i + "].batchNo']").val("");
			$("input[name='tScIcXsstockbillentry2List[" + i + "].batchNo']").attr("readonly", "readonly");
			$("input[name='tScIcXsstockbillentry2List[" + i + "].batchNo']").css("background-color", "rgb(226,226,226)");
		} else {
			$("input[name='tScIcXsstockbillentry2List[" + i + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + i + "','batchNo')");
		}
		var kfDateType = $("input[name='tScIcXsstockbillentry2List[" + i + "].kfDateType']").val();
		$("select[name='tScIcXsstockbillentry2List[" + i + "].kfDateType_']").val(kfDateType);
	}
}



//复制后操作
function clearCopyInfo() {
	$tbody = $("#add_tScIcXsstockbillentry1_table");
	var length1 = $tbody[0].rows.length;
	for (var i = 0; i < length1; i++) {
		$("input[name='tScIcXsstockbillentry1List[" + i + "].classIDSrc']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].idSrc']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].entryIdSrc']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].idOrder']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].entryIdOrder']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].classIDName']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].billNoSrc']").val("");
		$("input[name='tScIcXsstockbillentry1List[" + i + "].billNoOrder']").val("");
	}
}

function doClearExt() {
	setTimeout("clearCopyInfo()", 500);
}