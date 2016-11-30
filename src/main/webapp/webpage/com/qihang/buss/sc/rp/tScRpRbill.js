
var h = $(window).height() - 260;
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'),str_press = $this.attr('onkeypress'), val = $this.val();
			var blur_str=$this.attr('onblur');
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace("["+new_name+"]","["+i+"]"));
				}
				if(name.indexOf("amount") > -1 && name.indexOf("1L") > -1){
					$this.attr("onChange","setAllAmount()");
				}else if(name.indexOf("amount") > -1 && name.indexOf("2L") > -1){
					$this.attr("onChange","checkAmount("+i+")");
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
				if (onclick_str.indexOf("#index#") >= 0) {
					$this.attr("onclick", onclick_str.replace(/#index#/g, i));
				} else {
					var s = onclick_str.indexOf("(");
					var e = onclick_str.indexOf(")");
					var new_onclick = onclick_str.substring(s + 1, e);
					$this.attr("onclick", onclick_str.replace("("+new_onclick+")", "("+i+")"));
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
		$(this).find('div[name=\'xh\']').html(i+1);
		if(tableId.indexOf("1") > -1) {
			$("input[name='tScRpRbillentry1List[" + i + "].findex']").val(i + 1);
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
	addButton();//配置选单按钮
	setDefaultOldInfo();
	var id = $("#id").val();
	if(id) {
		var billType = $("select[name='billType_']").val();
		if (billType == "1") {
			$("#chooseType").attr("disabled", "disabled");
			$("#chooseType2").attr("disabled", "disabled");
		}
	}else{
		$("#chooseType").attr("disabled", "disabled");
		$("#chooseType2").attr("disabled", "disabled");
	}
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

	$("#itemName").blur(function(){
		checkInput($("#itemId"), $("#itemName"));
	})
	$("#deptName").blur(function(){
		checkInput($("#deptId"), $("#deptName"));
	})
	$("#empName").blur(function(){
		checkInput($("#empId"), $("#empName"));
	})

	document.getElementsByName("billType_")[0].options.remove(0);
})
function setDefaultOldInfo(){
	var itemId = $("#itemId").val();
	var itemName = $("#itemName").val();
	setValOldIdAnOldName($("#itemName"),itemId,itemName);

	var empId = $("#empId").val();
	var empName = $("#empName").val();
	setValOldIdAnOldName($("#empName"),empId,empName);

	var deptId = $("#deptId").val();
	var deptName = $("#deptName").val();
	setValOldIdAnOldName($("#deptName"),deptId,deptName);
}
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
	inputid.attr("oldid", oldId);
	inputid.attr("oldname", oldName);
}

//表格校验事件
function orderListStockBlur(rowIndex,id,name){
	checkInput( $('input[name="tScRpRbillentry1List['+rowIndex+'].'+id+'"]'),$('input[name="tScRpRbillentry1List['+rowIndex+'].'+name+'"]'));
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
				$("#itemId").val(item.id);
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


//结算账户回车事件
var ids3 ="";
function selectAccountDialog(index){
	var accountName = $("input[name='tScRpRbillentry1List["+index+"].accountName']").val();
	var url = 'tScSettleacctController.do?goSelectDialog&name=' + accountName +"&isMore=1"+"&ids="+ids3;
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
				var itemList = iframe.getMoreSelectionData();
				var itemLength = itemList.length;
				var nextTrLength = $('input[name="tScRpRbillentry1List[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
				var newLength = itemLength - nextTrLength - 1
				for (var j = 0; j < newLength; j++) {
					clickAddTScRpRbillentry1Btn(index);
				}
				$.each(itemList, function (i, item) {
					var rowindex = parseInt(index) + i;
					var accountId = item.id;
					ids3 += accountId+",";
					var accountName = item.name;
					$("input[name='tScRpRbillentry1List["+rowindex+"].accountId']").val(accountId);
					$("input[name='tScRpRbillentry1List["+rowindex+"].accountName']").val(accountName);
					setValOldIdAnOldName($("input[name='tScRpRbillentry1List["+rowindex+"].accountName']"),accountId,accountName);
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

//添加选单按钮
function addButton(){
	//选单
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
		select.setAttribute("style","margin-left:10px;");
		var option1 = document.createElement("option");
		option1.value="";
		option1.innerText="选单";
		option1.select = "select";
		if("105" == tranType) {
			var option2 = document.createElement("option");
			option2.value = "102";
			option2.innerText = "销售订单";
			var option3 = document.createElement("option");
			option3.value = "999";
			option3.innerText = "应收源单";
			//var option4 = document.createElement("option");
			//option4.value = "100";
			//option4.innerText = "商品借进单";
			select.appendChild(option1);
			select.appendChild(option2);
			select.appendChild(option3);
			//select.appendChild(option4);
		}
		buttonDiv[0].appendChild(select);

		//分摊
		var select2 = document.createElement("select");
		select2.id="chooseType2";
		select2.setAttribute("onchange","fentang()");
		select2.setAttribute("style","margin-left:10px;");
		var option1 = document.createElement("option");
		option1.value="";
		option1.innerText="分摊";
		option1.select = "select";
		var option2 = document.createElement("option");
		option2.value = "0001";
		option2.innerText = "顺序分摊";
		var option3 = document.createElement("option");
		option3.value = "0002";
		option3.innerText = "金额配比分摊";
		select2.appendChild(option1);
		select2.appendChild(option2);
		select2.appendChild(option3);
		buttonDiv[0].appendChild(select2);
	}
	//分摊

}


//选单
var CFG_NUMBER = $("#CFG_NUMBER").val();
var CFG_MONEY = $("#CFG_MONEY").val();
var ids = "";
var ids2 = "";
function selectBillDialog(){
	var itemId = $("#itemId").val();
	if(!itemId){
		tip("请选择客户后再进行选单操作");
		$("#chooseType").val("");
		return;
	}
	var tranType = $("#chooseType").val();
	var title = "销售订单";
	var url = "";
	var sonId = $("#sonId").val();
	if("102" ==  tranType) {
		url = "tScOrderController.do?goSelectMainDialog&itemID=" + itemId + "&tranType=" + tranType +"&ids="+ids+"&ids2="+ids2+"&sonId="+sonId;
		url = encodeURI(url);
	} else if ("999" == tranType){
		title = "应收单"
		url = "tScSlStockbillController.do?goSelectMainDialog&itemid=" + itemId +"&ids="+ids+"&ids2="+ids2+"&sonId="+sonId;
		url = encodeURI(url);
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
				var length = $("#add_tScRpRbillentry2_table").find(">tr").length;
				length--;
				var id = $("input[name='tScRpRbillentry2List["+length+"].classIdSRC']").val();
				var dataLength = itemList.length;
				if(!id){
					dataLength--;
				}
				for(var k=0 ; k < dataLength ; k++){
					clickAddTScRpRbillentry2Btn(length)
				}
				if(id){
					length++;
				}
				$("#itemName").attr("readonly","readonly");
				$("#itemName").css("background-color","rgb(226,226,226)");
				$("select[name='billType_']").attr("disabled","disabled");
				var allUnCheckAmount = 0;
				if("102" == tranType) {
					for (var i = 0; i < itemList.length; i++) {
						var rowIndex = length+i;
						var row = itemList[i];
						var idSrc = row.id;
						ids += idSrc + ",";
						var classIdSRC = row.tranType;
						var billNoSrc = row.billNo;
						var dateSrc = row.date.substring(0, 10);
						var billAmount = parseFloat(row.allAmount).toFixed(CFG_MONEY);
						var billCheckAmount = parseFloat(row.checkAmount).toFixed(CFG_MONEY);
						var billUnCheckAmount = (billAmount - billCheckAmount).toFixed(CFG_MONEY);
						var empId = row.empID;
						var empName = row.empName;
						var deptId = row.deptID;
						var deptName = row.deptName;
						$("#empId").val(empId);
						$("#empName").val(empName);
						$("#deptId").val(deptId);
						$("#deptName").val(deptName);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].findex']").val(i + 1);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].idSrc']").val(idSrc);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].classIdSRC']").val(classIdSRC);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billNoSrc']").val(billNoSrc);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].dateSrc']").val(dateSrc);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billAmount']").val(billAmount);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billAmount']").trigger("change");
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billCheckAmount']").val(billCheckAmount);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billCheckAmount']").trigger("change");
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billUnCheckAmount']").val(billUnCheckAmount);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billUnCheckAmount']").trigger("change");
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].classIdName']").val("销售订单");
						allUnCheckAmount = parseFloat(allUnCheckAmount) + parseFloat(billUnCheckAmount);
					}
				}else{
					for (var i = 0; i < itemList.length; i++) {
						var rowIndex = length + i;
						var row = itemList[i];
						var idSrc = row.id;
						ids2 += idSrc+",";
						var classIdSRC = row.tranType;
						var billNoSrc = row.billNo;
						var classIdName = row.typeName;
						var dateSrc = row.date.substring(0, 10);
						var billCheckAmount = parseFloat(row.checkAmount).toFixed(CFG_MONEY);
						if(classIdSRC == "104"){
							billCheckAmount = 0-billCheckAmount;
						}
						var billAmount = parseFloat(row.allAmount).toFixed(CFG_MONEY);
						var billUnCheckAmount = 0;
						billUnCheckAmount = (parseFloat(billAmount) - parseFloat(billCheckAmount)).toFixed(CFG_MONEY);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].findex']").val(i + 1);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].idSrc']").val(idSrc);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].classIdSRC']").val(classIdSRC);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billNoSrc']").val(billNoSrc);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].dateSrc']").val(dateSrc);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billAmount']").val(billAmount);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billAmount']").trigger("change")
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billCheckAmount']").val(billCheckAmount);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billCheckAmount']").trigger("change");
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billUnCheckAmount']").val(billUnCheckAmount);
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].billUnCheckAmount']").trigger("change");
						$("input[name='tScRpRbillentry2List[" + rowIndex + "].classIdName']").val(classIdName);
						if("104" != classIdSRC) {
							allUnCheckAmount = parseFloat(allUnCheckAmount) + parseFloat(billUnCheckAmount);
						}
					}
				}
				$("#allUnCheckAmount").val(allUnCheckAmount);
				//if(tranType == "102"){
				//	document.getElementById("chooseType").options[2].style("display",none);
				//}
				$("#chooseType").val("");
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

//收款类型发生改变时功能操作
function changeBillType(){
	var billType = $("select[name='billType_']").val();
	if("1" == billType){
		$("#chooseType").attr("disabled","disabled");
		$("#chooseType2").attr("disabled","disabled");
		var length = $("#add_tScRpRbillentry2_table").find(">tr").length;
		for(var i=0 ; i<length ; i++){
			clickDelTScRpRbillentry2Btn(i);
		}
		$("input[name='tScRpRbillentry2List[0].amount']").attr("readonly","readonly");
		$("input[name='tScRpRbillentry2List[0].amount']").css("background-color","rgb(226,226,226)");
		$("input[name='tScRpRbillentry2List[0].amount']").removeAttr("datatype");
		$("input[name='tScRpRbillentry2List[0].note']").attr("readonly","readonly");
		$("input[name='tScRpRbillentry2List[0].note']").css("background-color","rgb(226,226,226)");
		$("input[name='tScRpRbillentry2List[0].billNoSrc']").removeAttr("datatype");
	}else if("2" == billType){
		$("#chooseType").removeAttr("disabled");
		$("#chooseType2").removeAttr("disabled");
		$("input[name='tScRpRbillentry2List[0].amount']").removeAttr("readonly");
		$("input[name='tScRpRbillentry2List[0].amount']").css("background-color","white");
		$("input[name='tScRpRbillentry2List[0].amount']").attr("datatype","float");
		$("input[name='tScRpRbillentry2List[0].note']").removeAttr("readonly");
		$("input[name='tScRpRbillentry2List[0].note']").css("background-color","white");
		$("input[name='tScRpRbillentry2List[0].billNoSrc']").attr("datatype","*");

	}
	$("#billType").val(billType);
}

//校验本次收款绝对值是否大于源单未执行金额
function checkAmount(index){
	var amount = $("input[name='tScRpRbillentry2List["+index+"].amount']").val();
	var tranType = $("input[name='tScRpRbillentry2List["+index+"].classIdSRC']").val();//单据类型
	if(parseFloat(amount) < 0){
		amount = -1 * parseFloat(amount);
	}
	var billUnCheckAmount = $("input[name='tScRpRbillentry2List["+index+"].billUnCheckAmount']").val();
	if(!billUnCheckAmount){
		billUnCheckAmount = 0;
	}
	if(parseFloat(billUnCheckAmount) < 0){
		billUnCheckAmount = -1 * billUnCheckAmount;
	}
	if(parseFloat(amount) > parseFloat(billUnCheckAmount)){
		tip("本次付款不得大于源单未执行金额");
		if("104" == tranType){
			billUnCheckAmount = -billUnCheckAmount;
		}
		$("input[name='tScRpRbillentry2List["+index+"].amount']").val(billUnCheckAmount)
		setAllAmount2();
		return;
	}
	if("104" == tranType){
		amount = -amount;
	}
	//$("input[name='tScRpRbillentry2List["+index+"].amount']").val(amount)
	setAllAmount2();
}

//汇总表体1金额
function setAllAmount(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody = $("#tScRpRbillentry1_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	for(var i=0 ; i<length ; i++){
		var amount = $("input[name='tScRpRbillentry1List[" + i + "].amount']").val();//金额
		if(!amount){
			amount = 0;
		}

		allAmount += parseFloat(amount);
		$("input[name='tScRpRbillentry1List[" + i + "].amount']").val(parseFloat(amount).toFixed(CFG_MONEY));//金额
	}
	allAmount = allAmount.toFixed(CFG_MONEY);
	$("#allAmount").val(allAmount);
}

//保存前校验
function checkAllAmount(mate){
	var billType = $("select[name='billType_']").val();
	if(billType == 2){
		var allAmount = $("#allAmount").val();//表1金额合计
		var entry2Amount = $("#entry2Amount").val();//表2金额合计
		if(parseFloat(allAmount) != parseFloat(entry2Amount)){
			tip("结算表体收款合计必须等于应收表体收款合计");
			return false;
		}
	}else{
		return true;
	}
}
$(function(){
	var allAmount = $.trim($("#allAmount").val());
	if(allAmount != "" && allAmount != "undefined" && allAmount != null){
		var CFG_MONEY = $("#CFG_MONEY").val();
		$("#allAmount").val(parseFloat(allAmount).toFixed(CFG_MONEY));
	}
});
//汇总表二金额
function setAllAmount2(){
	var CFG_MONEY = $("#CFG_MONEY").val();
	$tbody = $("#tScRpRbillentry2_table");
	var length = $tbody[0].rows.length;
	var allAmount = 0;
	var allAmount_ = 0;
	for(var i=0 ; i<length ; i++){
		var tranType = $("input[name='tScRpRbillentry2List["+i+"].classIdSRC']").val();//源单类型
		var amount = $("input[name='tScRpRbillentry2List[" + i + "].amount']").val();//金额
		if(!amount){
			amount = 0;
		}
		if("104" != tranType){
			var unCheckAmount = $("input[name='tScRpRbillentry2List["+i+"].billUnCheckAmount']").val();//未执行金额
			allAmount_ = allAmount_ + parseFloat(unCheckAmount);
		}else{
			//if(parseFloat(amount) > 0){
			//	amount = -amount;
			//}
		}
		//	allAmount = parseFloat(allAmount) - parseFloat(amount);
		//} else {
			allAmount = parseFloat(allAmount) + parseFloat(amount);
		//}
	}
	allAmount = allAmount.toFixed(CFG_MONEY);
	$("#entry2Amount").val(allAmount);
	$("#allUnCheckAmount").val(allAmount_);
}

//分摊
function fentang(){
	var chooseType =  $("#chooseType2").val();
	if(!chooseType){
		tip("请选择分摊类型");
		return;
	}
	var allAmount = $("#allAmount").val();
	if(!allAmount){
		tip("请填写结算表数据");
		$("#chooseType2").val("");
		return ;
	}
	$tbody = $("#tScRpRbillentry2_table");
	var length = $tbody[0].rows.length;
	var id = $("input[name='tScRpRbillentry2List["+(length-1)+"].classIdSRC']").val();
	if(!id){
		tip("请对应收表进行选单操作");
		$("#chooseType2").val("");
		return ;
	}
	//顺序分摊
	if("0001" == chooseType){
		debugger;
		for(var j=0 ; j< length ; j++){
			var tranType = $("input[name='tScRpRbillentry2List[" + j + "].classIdSRC']").val();
			var unCheckAmount = $("input[name='tScRpRbillentry2List[" + j + "].billUnCheckAmount']").val();
			if ("104" == tranType) {
				allAmount = parseFloat(allAmount) - parseFloat(unCheckAmount);
				$("input[name='tScRpRbillentry2List[" + j + "].amount']").val(unCheckAmount);
				//$("input[name='tScRpRbillentry2List[" + i + "].amount']").trigger("change");
			}
		}
		for(var i=0 ; i<length ; i++){
			var tranType = $("input[name='tScRpRbillentry2List["+i+"].classIdSRC']").val();
			var unCheckAmount = $("input[name='tScRpRbillentry2List["+i+"].billUnCheckAmount']").val();
			if("104" != tranType){
				if (parseFloat(allAmount) > parseFloat(unCheckAmount)) {
					$("input[name='tScRpRbillentry2List[" + i + "].amount']").val(unCheckAmount);
					$("input[name='tScRpRbillentry2List[" + i + "].amount']").trigger("change");
					allAmount = (parseFloat(allAmount) - parseFloat(unCheckAmount)).toFixed(2);
				} else {
					$("input[name='tScRpRbillentry2List[" + i + "].amount']").val(allAmount);
					$("input[name='tScRpRbillentry2List[" + i + "].amount']").trigger("change");
					allAmount = (parseFloat(allAmount) - parseFloat(allAmount)).toFixed(2);
				}
			}else{
				$("input[name='tScRpRbillentry2List[" + i + "].amount']").trigger("change");
			}
		}
	}else{
		//金额配比分摊
		for(var j=0 ; j< length ; j++){
			var tranType = $("input[name='tScRpRbillentry2List[" + j + "].classIdSRC']").val();
			var unCheckAmount = $("input[name='tScRpRbillentry2List[" + j + "].billUnCheckAmount']").val();
			if ("104" == tranType) {
				allAmount = parseFloat(allAmount) - parseFloat(unCheckAmount);
				$("input[name='tScRpRbillentry2List[" + j + "].amount']").val(unCheckAmount);
			}
		}
		for(var i=0 ; i<length ; i++) {
			var tranType = $("input[name='tScRpRbillentry2List[" + i + "].classIdSRC']").val();
			var unCheckAmount = $("input[name='tScRpRbillentry2List[" + i + "].billUnCheckAmount']").val();
			var allUnCheckAmount = $("#allUnCheckAmount").val();
			if("104" != tranType) {
				//if (parseFloat(allAmount) > parseFloat(unCheckAmount)) {
						var amount = (parseFloat(unCheckAmount) / parseFloat(allUnCheckAmount) * parseFloat(allAmount)).toFixed(2);
						$("input[name='tScRpRbillentry2List[" + i + "].amount']").val(amount);
						$("input[name='tScRpRbillentry2List[" + i + "].amount']").trigger("change");
						//allAmount = parseFloat(allAmount) - parseFloat(amount);
				//} else {
				//	$("input[name='tScRpRbillentry2List[" + i + "].amount']").val(allAmount);
				//	//allAmount = parseFloat(allAmount) - parseFloat(allAmount);
				//}
			}else{
				$("input[name='tScRpRbillentry2List[" + i + "].amount']").trigger("change");
			}
		}
	}
	setAllAmount2();
	$("#chooseType2").val("");
}

//复制单据后操作方法
function clearTabInfo(){
	setTimeout("delTableRow()",500);
}

function delTableRow(){
	//debugger;
	var billType = $("#billType").val();
	if("2" == billType) {//应收款
	//if("1" == billType) {//收款
		var length = $("#add_tScRpRbillentry2_table").find(">tr").length;
		//for (var i = 0; i < length; i++) {
		for (var i = length-1; i >=0 ; i--) {
			clickDelTScRpRbillentry2Btn(i);
		}
	}
}