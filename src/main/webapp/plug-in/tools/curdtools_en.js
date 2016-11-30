//﻿var jq = jQuery.noConflict();
/**
 * 增删改工具栏
 */
/*window.onerror = function() {
	return true;
};*/
var iframe;// iframe操作对象
var win;//窗口对象
var gridname="";//操作datagrid对象名称
var windowapi = frameElement.api, W = windowapi.opener;//内容页中调用窗口实例对象接口
function upload(curform) {
	upload();
}
/**
 *  Add event open window
 * @param title
 * @param addurl
 */
function add(title,addurl,gname,width,height) {
	gridname=gname;
	createwindow(title, addurl,width,height);
}
/**
 * 树列表添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function addTreeNode(title,addurl,gname) {
	if (rowid != '') {
		addurl += '&id='+rowid;
	}
	gridname=gname;
	createwindow(title, addurl);
}
/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function update(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('Please select edit item');
		return;
	}
	if (rowsData.length>1) {
		tip('Please one item to edit');
		return;
	}

	url += '&load=edit';//告诉编辑表单页面此次是编辑操作
	url += '&id='+rowsData[0].id;
	createwindow(title,url,width,height);
}

/**
 * 如果页面是详细查看页面，无效化所有表单元素，只能进行查看
 */
$(function(){
	if(location.href.indexOf("load=detail")!=-1){
		$(":input").attr("disabled","true");
		setTimeout('$("a").attr("onclick","")', 1000);
		//20151207 zerrion 查看页面时去除所有必填标志*号
		$(".Validform_checktip").html('');
		//hjhadd20160809如果当前账套未开账或已经结账，不允许单据管理操作
		billbeforeaccount();
	}
	//Zerrion 15-11-15--25 页面载入时把所有只读输入框背景设为灰色
	$(':input[readonly="readonly"]').css('background-color','#e2e2e2');
	//单据复制时，重置或清空部分初始数据
	var url = location.href;
	var urliffcopy = url.indexOf("load=fcopy");
	if (urliffcopy>=0){
		billcopy();
	}
	//调用控制左边导航快捷桌面的按钮是否可点击
	navigatebeforeaccount();
});

/**
 * 根据导航页面的beforeAccount隐藏域值来控制左边导航快捷桌面的按钮是否可点击
 *   值为true表示按表示如果当前账套已开账未结账才允许单据操作，
 *   值为init表示当前账套未开账才允许单据操作（用于初始化的几种单据）
 *   未设置值（默认为空字符串)即为不需要当前账套检查
 *   隐藏域属性 exception表示页面内例外不做控制的链接A的class(如初始化导航中结束初始化不做控制)
 * @author hjh 20160908
 */
function navigatebeforeaccount(){
	var objname = "beforeAccount";
	if ($("#" + objname).length>0){
		var beforeAccount = $("#" + objname).val();
		var exception = $("#" + objname).attr("exception");
		if (beforeAccount!=null && beforeAccount!=""){
			var url = "tScAccountConfigController.do?navigatebeforeaccount";
			var formData = new Object();
			formData['beforeAccount'] = beforeAccount;
			$.ajax({
				url: url,
				async: false,
				cache: false,
				type: 'POST',
				data: formData,
				error: function () {// 请求失败处理函数
				},
				success: function (data) {
					if (data.success == false) {//不允许操作
						var unbindstr = "";
						$(".navigate").find("a").each(function (i){
							//$(this).attr("disabled","disabled");
							if (exception!=null && exception!="" && $(this).attr('class')==exception){
								//例外不做控制
							}else {
								$(this).removeAttr("onclick");//如果有点击属性配置则为空
								$(this).attr("msg", data.msg);
								unbindstr += '$(".' + $(this).attr('class') + '").unbind();';
								$(this).attr("onclick", "tip('" + data.msg + "');");//如果有点击属性配置则为空
							}
						});
						if (unbindstr!="") {
							//解绑事件
							setTimeout(unbindstr, 500);
						}
					}
				}
			});
		}
	}
}

/**
 * 如果当前账套未开账或已经结账，不允许单据管理操作
 * @author hjh 20160908
 */
function billbeforeaccount(){
	if (location.href.indexOf("accountIsAllowBillManage=1") != -1) {
		$("[data-options*=icon-add]").linkbutton("disable"); //旧版录入
		$("[data-options*=icon-edit]").linkbutton("disable"); //旧版编辑
		$("[data-options*=new-icon-add]").linkbutton("disable"); //新版录入
		$("[data-options*=new-icon-edit]").linkbutton("disable"); //新版编辑
		$("[data-options*=new-icon-cancellation]").linkbutton("disable"); //作废
		$("[data-options*=new-icon-uncancellation]").linkbutton("disable"); //反作废
		$("[data-options*=new-icon-audit]").linkbutton("disable"); //审核
		$("[data-options*=new-icon-unaudit]").linkbutton("disable"); //反审核
		$("[data-options*=icon-put]").linkbutton("disable"); //旧版导入
		$("[data-options*=new-icon-put]").linkbutton("disable"); //新版导入
		$("[data-options*=new-icon-copy]").linkbutton("disable"); //复制
		$("[data-options*=new-icon-chkstockbill]").linkbutton("disable"); //自动盘点
		$("[data-options*=new-icon-close]").linkbutton("disable"); //关闭
		$("[data-options*=new-icon-unclose]").linkbutton("disable"); //反关闭
		$("[data-options*=new-icon-copy]").linkbutton("disable"); //复制
		//解绑事件
		setTimeout('$("#unAuditBtn").unbind();' +
				'$("#auditBtn").unbind();' +
				'$("#edit").unbind();' +
				'$("#closedBtn").unbind();' +
				'$("#cancelBtn").unbind();' +
				'$("#unCancelBtn").unbind();'+
				'$("#copyBtn").unbind();' +
				'$("#add").unbind();'+
				'$("#aduitBtn").unbind();'+
				'$("#unAuditBtn").unbind();', 500);
	}
}

//单据复制
function billcopy() {
	var tranType = $("#tranType").val();//编辑页面必须要有tranType的表单域，且值为复制源表单的tranType
	if ($("#tranType").length==0){
		tranType = $("#trantype").val();
	}
	var billDateType = "";//新单据的单据日期的类型，默认是当天，init表示初始化时是用扎帐日期减1天
	var number = "";//源基础资料的编号
	var billNo = "";//源单据的单据编号
	if ($("#billDateType").length > 0) {
		billDateType = $("#billDateType").val();
	}
	if ($("#number").length > 0) {
		number = $("#number").val();
	}
	if ($("#billNo").length > 0) {
		billNo = $("#billNo").val();
	}
	var url = "tScBillController.do?goBillCopy&tranType=" + tranType + "&billDateType=" + billDateType;
	url += "&billNo=" + billNo + "&number=" + number;
	$.ajax({
		url: url,
		type: 'post',
		cache: false,
		success: function (data) {
			//var d = $.parseJSON(data);
			var d = data;
			if (d.success) {
				var msg = d.msg;
				if (d.success) {//单据复制成功取到要清除或重置的值
					//通过js遍历obj对象的所有属性，并给其赋值，即后台把要清空或重置的数据按规则处理好，前台用规则来处理就好了(分为主表和子表，其中子表带小数点开头)。
					var formobj = $("#formobj");
					//$(formobj).form("load", d.obj);
					var jobj = d.obj;
					for (var fieldname in jobj) {
						var fieldvalue = jobj[fieldname];
						if (fieldname=="date"){
							$("[name='date']").val(fieldvalue);
						}else if(fieldname=="billNo"){
							$("[name='billNo']").val(fieldvalue);
						}
						if (fieldname.indexOf(":") >= 0) {//footdiv+冒号+要清除内容标题 表示页脚设置的重置字段
							var fieldnameArray = fieldname.split(":");
							if (fieldnameArray.length>1){
								$("#" + fieldnameArray[0]).find(".footlabel").each(function(i){
									if ($(this).text().indexOf(fieldnameArray[1])>=0){
										$(this).parent().find("span").text(fieldvalue);
									}
								});
							}else{
								$(".footlabel").each(function(i){
									if ($(this).text().indexOf(fieldnameArray[1])>=0){
										$(this).parent().find("span").text(fieldvalue);
									}
								});
							}
						}else if (fieldname.indexOf(".") >= 0) {//点号开头表示子表的重置字段
							//清空子表相关数据：（附表明细）的主表ID、明细表ID，来源单据相关（idSrc、entryIdSrc、idOrder、entryIdOrder、classIdSrc、billNoOrder）
							//$("input[name$="+fieldname+"]").val(fieldvalue);
							var iobj = $("input[name$='" + fieldname + "']");
							if ($(iobj).length > 0) {
								$(iobj).val(fieldvalue);
							}
						} else {
							//重置为新数据：新单据编号、单据日期、制单人姓名、制单人ID、分支机构ID、分支机构名称、未审核状态、未作废状态，创建人、创建人ID、创建时间、更新人、更新人ID、更新时间
							//清空主表相关数据：表头主表ID，来源单据相关（classIDSrc、idSrc、billNoSrc）
							//$("#"+fieldname).val(fieldvalue);
							var iobj = $("#" + fieldname);
							if ($(iobj).length > 0) {
								$(iobj).val(fieldvalue);
							}
						}
					}
					//清除单据页面自定义的扩展清除属性
					var clearExt = "";//单据复制清除扩展属性串：多个用逗号间隔，其中主表为属性名，子表为小数点+属性名。
					if ($("#clearExt").length > 0) {
						clearExt = $("#clearExt").val();
						//var clearExtArray = clearExt.split(",");
						//for (var i = 0; i < clearExtArray.length; i++) {
						//	var fieldname = clearExtArray[i];
						//	var fieldvalue = "";
						//	if (fieldname != "") {
						//		if (fieldname.indexOf(".") >= 0) {
						//			//清空子表相关数据
						//			//$("input[name$="+fieldname+"]").val(fieldvalue);
						//			var iobj = $("input[name$='" + fieldname + "']");
						//			if ($(iobj).length > 0) {
						//				$(iobj).val(fieldvalue);
						//			}
						//		} else {
						//			//清空主表相关数据
						//			//$("#"+fieldname).val(fieldvalue);
						//			var iobj = $("#" + fieldname);
						//			if ($(iobj).length > 0) {
						//				$(iobj).val(fieldvalue);
						//			}
						//		}
						//	}
						//}
						//更正声明：单据复制已经默认部分字段的设置，原约定在编辑页面增加id为clearExt的隐藏域为要扩展清除属性，现更正为当前单据需要调用扩展的重置函数。如应收初始化单据需要在单据复制后实现“应收日期要重置为当天的日期”，则在此表单域的值为其自定义的函数名，并在编辑页面内编写实现其功能的自定义函数。
						if (clearExt!=""){
							eval(clearExt+"()");//调用单据复制的扩展数据重置函数
						}
					}
					//根据单据日期更新单据日期选择框值
					if ($("[name='date']").length > 0) {
						var billDate = $("[name='date']").val();
						billDate = billDate.replace(/-/g, "/");
						var billDateArray = billDate.split(" ");
						if (billDateArray.length > 1) {//去掉日期后面的时间
							billDate = billDateArray[0];
						}
						var newdate = new Date(billDate);
						var year = newdate.getFullYear();
						var month = (newdate.getMonth() + 1) < 10 ? "0" + (newdate.getMonth() + 1) : (newdate.getMonth() + 1);
						var date = newdate.getDate() < 10 ? "0" + newdate.getDate() : newdate.getDate();
						$("#date").val(year + "-" + month + "-" + date);
					}
					//重置form url为新增doAdd
					var url = $(formobj).attr("action");
					url = url.replace("?doUpdate", "?doAdd");
					$(formobj).attr("action", url);
				}
				//tip(msg);//20190831婷婷:去掉单据复制提示
				//reloadTable();
				//$("#" + gname).datagrid('unselectAll');
				ids = '';
			}
		}
	});
}

//单据保存草稿
function doTemp(formid, tranType) {
	var formData = $("#" + formid).serializeArray();//自动将form表单封装成json
	var url = "tScBillTempController.do?doAddJson";
	var d = {};
	var d_form = {};
	var d_combobox = {};
	var d_combobox_itemid = {};
	var d_wdate = {};
	var d_ckeditor = {};
	var d_tt_tab = {};
	$.each(formData, function () {
		d_form[this.name] = this.value;
	});
	//手动下拉框、日期框、复文本框要单独取值，并放到json串里
	$("input.easyui-combobox,input.combobox-f").each(function (i) {
		var fieldname = this.name ? this.name : this.id;//$(this).attr("comboname")?$(this).attr("comboname"):this.id;
		d_combobox[fieldname] = $(this).combobox('getValues');
		var itemIdobj = $(this).attr("comboname");//fieldname;//this.name ? this.name : this.id;
		itemIdobj = itemIdobj.replace(".unitiD",".unitId").replace(".unitid",".unitId").replace(".unitID",".unitId");//兼容单位id(支持unitId,unitID,unitid,unitiD)
		itemIdobj = itemIdobj.replace(".unitId",".itemId");
		var itemId = "";
		if ($("input[name='" + itemIdobj + "']").length>0){
			itemId = $("input[name='" + itemIdobj + "']").val();
		}else{
			itemIdobj = itemIdobj.replace(".itemId", ".itemID");
			if ($("input[name='" + itemIdobj + "']").length>0){
				itemId = $("input[name='" + itemIdobj + "']").val();
			}else{
				itemIdobj = itemIdobj.replace(".itemID", ".itemiD");
				if ($("input[name='" + itemIdobj + "']").length>0){
					itemId = $("input[name='" + itemIdobj + "']").val();
				}else{
					itemIdobj = itemIdobj.replace(".itemiD", ".itemid");
					itemId = $("input[name='" + itemIdobj + "']").val();
				}
			}
		}
		//d_combobox_itemid[this.name ? this.name : this.id] = itemId;
		d_combobox_itemid[fieldname] = itemId;
	});
	$("input.Wdate").each(function (i) {
		d_wdate[this.name ? this.name : this.id] = this.value;//日期以id为key
	});
	$("textarea").each(function (i) {
		if ($("#cke_" + this.id).length > 0) {
			d_ckeditor[this.name] = CHEDITOR.instance[this.name].getData();
		}
	});
	//todo:手动获得有多少个附表，每个附表有多少行，目前只考虑 tab模式的附表
	//$("[id=tt]").find("tbody[id$='_table']").each(function(i){
	$("tbody[id$='_table']").each(function(i){
		d_tt_tab[this.id] = $(this).find("tr").length;
	});
	//存放到大json对象d中
	d["d_form"] = d_form;
	d["d_combobox"] = d_combobox;
	d["d_wdate"] = d_wdate;
	d["d_ckeditor"] = d_ckeditor;
	d["d_tt_tab"] = d_tt_tab;
	d["d_combobox_itemid"] = d_combobox_itemid;
	$.ajax({
		type: "post",
		contentType: "application/json",
		url: url,
		data: "str=" + JSON.stringify(d),//{json: JSON.stringify(formData)},
		//data:"str="+json.stringify(formdata),
		dataType: 'json',
		success: function (result) {//回调函数
			alertTip("Draft document saved successfully.");//单据保存草稿成功
			var preTitle = window.top.$('.tabs-selected:first').text();
			setTimeout("closetab('"+preTitle+"');",1000);
		}
	});
}
//选择恢复单据草稿
function doTempChooseDialog(formid, tranType) {
	//var itemName = $("#stockName").val();
	//debugger;
	var tranType = $("#tranType").val();
	if ($("#tranType").length==0){
		tranType = $("#trantype").val();
	}
	var url = 'vScBillTempController.do?goSelectDialog&tranType=' + tranType;
	var width = 1000;
	var height = 500;
	var title = "单据草稿";
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
				var content = eval("(" + item.content + ")");
				//todo:判断每个附表的行数是否达到保存草稿里的行号要求，不够的先加行号，再来给表单域赋值
				var content_tt_tab = content["d_tt_tab"];
				for (fieldname in content_tt_tab) {
					var fieldvalue = content_tt_tab[fieldname];
					var nextTrLength = $("#"+fieldname).find("tr").length;
					var itemLength = parseInt(fieldvalue);
					var newLength = itemLength - nextTrLength;// - 1;
					//先加不够的空行
					for (var j = 0; j < newLength; j++) {
						var newfunname = fieldname.replace("add_","").replace("_table","");
						newfunname = "clickAdd"+newfunname.substring(0,1).toUpperCase()+newfunname.substring(1)+"Btn("+j+")";//Add后跟的附表ID首字母大写
						var f0 = eval("(" + newfunname + ")");//clickAddTScIcInitialentryBtn(j);
					}
				}
				//将草稿数据通过form的load方法快速赋值
				var content_form = content["d_form"];
				$("#"+formid).form("load", content_form);
				//将隐藏域的单据编号赋值到显示的单据编号中
				$("#billNoStr").val($("#billNo").val());
				//手动从json中获取下拉框、日期框、复文本框取值并赋值
				var content_combobox = content["d_combobox"];
				var d_combobox_itemid = content["d_combobox_itemid"];
				for (fieldname in content_combobox) {
					var fieldvalue = content_combobox[fieldname];
					fieldname = "'" + fieldname + "'";
					if ($("input[id=" + fieldname + "]").length > 0) {
						var comobj = fieldname.replace("'", "").replace("'", "").replace("[", "\\[").replace("]", "\\]");//$("input[id=" + fieldname + "]").attr("id");
						var itemId = d_combobox_itemid[fieldname.replace("'", "").replace("'", "")];
						//$("#" + comobj).combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
						$("input[id=" + fieldname + "]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
						$("input[id=" + fieldname + "]").combobox("setValue", fieldvalue);
					} else {
						//debugger;
						var comobj = $("input[name=" + fieldname + "]").attr("id");
						var itemId = d_combobox_itemid[fieldname];
						$("#" + comobj).combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
						//$("#unitId\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId + "&timestamp=" + new Date().getTime());
						$("input[name=" + fieldname + "]").combobox("setValue", fieldvalue);
					}
				}
				var strobjnames = '#qty, #basicQty, #secQty, #coefficient, #secCoefficient, #price, #amount, #discountRate, #discountPrice, #discountAmount, #taxRate, #taxPriceEx, #taxAmountEx, #taxPrice, #inTaxAmount, #taxAmount, #itemWeight';
				if ($(strobjnames).length>0) {
					//如果存在数量、基本数量、单价、金额等表单域时，在模拟输入框值改变事件前初始主表计算js金额相关工具类
					initComputingTools();
				}
				//重新对每个子表进行计算js金额初始化动作
				var content_tt_tab = content["d_tt_tab"];
				for (fieldname in content_tt_tab) {
					var tableobj = $("#" + fieldname);//内容表格
					var nextTrLength = $(tableobj).find("tr").length;
					var configName =fieldname.replace("add_", "").replace("_table", "") + "List"; //如 ，tPoOrderentryList
					var isExists = false;
					var arrobjnames = strobjnames.split(",");
					for(var i=0;i<arrobjnames.length;i++){
						if (arrobjnames[i]!=""){
							if ($("[name='" + configName+"[0]." + arrobjnames[i].replace("#","") + "']").length>0){
								isExists = true;
							}
						}
					}
					if (isExists){
						for (var i=0;i<nextTrLength;i++){
							//子表函数计算
							try{//如买一赠一有数量，但没有计算需求，就会报错，这里抛弃异常
								setFunctionInfo(i, configName);
							}catch(e){

							}
						}
					}
				}
				//判断每个附表的的标题列是否有total属性且值为true，如果有的话，对其列下所有单元格模拟change事件
				var content_tt_tab = content["d_tt_tab"];
				for (fieldname in content_tt_tab) {
					var tableobj = $("#" + fieldname);//内容表格
					var tablehead = $("#" + fieldname.replace("add_", "") + "scrolldivHead");//标题表格
					var nextTrLength = $(tableobj).find("tr").length;
					$(tablehead).find("td").each(function (i){
						if ($(this).attr("total")!=null && $(this).attr("total").toLowerCase()=="true") {
							//debugger;
							//对其列下所有单元格模拟input事件
							for (var j = 0; j < nextTrLength; j++) {
								var inputobj = $(tableobj).find("tr").eq(j).children("td:eq(" + i + ")").find("input");
								if (inputobj.length>0){
									$(inputobj).trigger('input');//数量乘以单价算金额等
									$(inputobj).trigger('change');//数量乘以单价算金额等
									//$(inputobj).trigger('propertychange input change');//合计行
								}
							}
						}
					});
				}
				var content_wdate = content["d_wdate"];
				for (fieldname in content_wdate) {
					var fieldvalue = content_wdate[fieldname];
					var dateValue = "";
					fieldname = "'" + fieldname + "'";
					if (fieldvalue!="") {
						var dateValue = new Date(fieldvalue);
						var year = dateValue.getFullYear();
						var month = (dateValue.getMonth() + 1) < 10 ? "0" + (dateValue.getMonth() + 1) : (dateValue.getMonth() + 1);
						var date = dateValue.getDate() < 10 ? "0" + dateValue.getDate() : dateValue.getDate();
						dateValue = year + "-" + month + "-" + date;
					}
					if ($("input[name=" + fieldname + "]").length > 0) {
						$("input[name=" + fieldname + "]").val(dateValue);
					}
					if ($("input[id=" + fieldname + "]").length > 0) {
						$("input[id=" + fieldname + "]").val(dateValue);
					}
				}
				var content_ckeditor = content["d_ckeditor"];
				for (fieldname in content_ckeditor) {
					var fieldvalue = content_ckeditor[fieldname];
					CHEDITOR.instance[this.name].setData(fieldvalue);
				}
				//发送ajax到controller,删除被选中的单据草稿
				var url = "tScBillTempController.do?doBatchDel";
				$.ajax({
					url: url,
					type: 'post',
					data: {
						ids: item.id//单据草稿ID
					},
					cache: false,
					success: function (data) {
						//var d = $.parseJSON(data);
						var d = data;
						if (d.success) {
							var msg = d.msg;
							//tip(msg);
							//reloadTable();
							//$("#" + gname).datagrid('unselectAll');
							var tempRecoveryExt = "";//恢复草稿后留一个自定义函数来供各单据恢复后自定义脚本
							if ($("#tempRecoveryExt").length > 0) {
								tempRecoveryExt = $("#tempRecoveryExt").val();
								if (tempRecoveryExt != "") {
									eval(tempRecoveryExt + "()");//调用单据恢复后的扩展函数
								}
							}
							tip("Draft document recovery successfully.");//单据恢复草稿成功
							ids = '';
						}
					}
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
 * 查看详细事件打开窗口
 * @param title 查看框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function detail(title,url, id,width,height) {
	var rowsData = $('#'+id).datagrid('getSelections');
	
	if (!rowsData || rowsData.length == 0) {
		tip('Please select view item');
		return;
	}
	if (rowsData.length > 1) {
		tip('Please select an item to view');
		return;
	}
    url += '&load=detail&id='+rowsData[0].id;
	createdetailwindow(title,url,width,height);
}

/**
 * 单据复制事件打开窗口
 * @param title 查看框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function fcopy(title,url, id,width,height) {
	var rowsData = $('#'+id).datagrid('getSelections');

	if (!rowsData || rowsData.length == 0) {
		tip('Please select view item');
		return;
	}
	if (rowsData.length > 1) {
		tip('Please select an item to view');
		return;
	}
	url += '&load=fcopy&id='+rowsData[0].id;
	createdetailwindow(title,url,width,height);
}

/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelect(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.confirm('Do you want to delete this data for ever?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("Please select delete data");
	}
}

/**
 * 查看时的弹出窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createdetailwindow(title, addurl,width,height) {
	//查看页面url多传递（从左datagrid隐藏域）是否账套未开账或已结账，来控制查看页面的按钮是否显示
	addurl += '&accountIsAllowBillManage=' + $("#accountIsAllowBillManage").val();
	//debugger;
	var fcopyobj = $("[onclick^='fcopy(']");
	if (fcopyobj!=null && fcopyobj.length>0){//判断列表页面是否有复制按钮
		addurl += '&iscopy=true';
	}
	title = title.replace("复制","新增");
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: 'Close',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: 'Close',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
	
}
/**
 * 全屏编辑
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function editfs(title,url) {
	var name=gridname;
	 if (rowid == '') {
		tip('Please select edit item');
		return;
	}
	url += '&id='+rowid;
	openwindow(title,url,name,800,500);
}
// 删除调用函数
function delObj(url,name) {
	url = encodeURI(url);
	gridname=name;
	createdialog('Delete Confirm ', 'Delete this record, Confirm ?', url,name);
}
// 删除调用函数
function confuploadify(url, id) {
	$.dialog.confirm('Delete confirm', function(){
		deluploadify(url, id);
	}, function(){
	}).zindex();
}
/**
 * 执行删除附件
 * 
 * @param url
 * @param index
 */
function deluploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				$("#" + id).remove();// 移除SPAN
				m.remove(id);// 移除MAP对象内字符串
			}

		}
	});
}
// 普通询问操作调用函数
function confirm(url, content,name) {
	createdialog('Tip Message ', content, url,name);
}
/**
 * Tip Message
 */
function tip_old(msg) {
	$.dialog.setting.zIndex = 1980;
	$.dialog.tips(msg, 1);
}
/**
 * Tip Message
 */
function tip(msg) {
	try{
		$.dialog.setting.zIndex = 1980;
		$.messager.show({
			title : 'Tip Message',
			msg : msg,
			timeout : 1000 * 6
		});
	}catch(e){
		alertTipTop(msg,'10%');
	}
}
/**
 * Tip Message像alert一样 定位顶部的位置
 */
function alertTipTop(msg,top,title) {
	$.dialog.setting.zIndex = 1980;
	title = title?title:"Tip Message";
	$.dialog({
			title:title,
			icon:'tips.gif',
			top:top,
			content: msg
		}).zindex();
}

/**
 * Tip Message像alert一样
 */
function alertTip(msg,title) {
	$.dialog.setting.zIndex = 1980;
	title = title?title:"Tip Message";
	$.dialog({
			title:title,
			icon:'tips.gif',
			content: msg
		}).zindex();
}
/**
 * 创建添加或编辑窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
    //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
			okVal: 'Submit',
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: 'Close',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: 'Close',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
    //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	
}
/**
 * 创建上传页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url,name,width, height) {
	gridname=name;
	$.dialog({
	    content: 'url:'+url,
	    cache:false,
	    button: [
	        {
	            name: "Begin Upload",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.upload();
					return false;
	            },
	            focus: true
	        },
	        {
	            name: "Cancel Upload",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.cancel();
	            }
	        }
	    ]
	}).zindex();
}
/**
 * 创建查询页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function opensearchdwin(title, url, width, height) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		height : height,
		cache:false,
		width : width,
		opacity : 0.3,
		button : [ {
			name :'Query',
			callback : function() {
				iframe = this.iframe.contentWindow;
				iframe.searchs();
			},
			focus : true
		}, {
			name : 'Cancel',
			callback : function() {

			}
		} ]
	}).zindex();
}
/**
 * 创建不带按钮的窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openwindow(title, url,name, width, height) {
	gridname=name;
	if (typeof (width) == 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true,
				width: 'auto',
			    height: height
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				parent:windowapi,
				lock : true,
				width: 'auto',
			    height: height
			}).zindex();
		}
	}
	if (typeof (height) == 'undefined'&&typeof (width) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: 'auto'
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: 'auto'
			}).zindex();
		}
	}
	if (typeof (width) == 'undefined'&&typeof (height) == 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: 'auto',
				cache:false,
			    height: 'auto'
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: 'auto',
				cache:false,
			    height: 'auto'
			}).zindex();
		}
	}
	
	if (typeof (width) != 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true
			}).zindex();
		}else{
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				parent:windowapi,
				title : title,
				cache:false,
				lock : true
			}).zindex();
		}
	}
}

/**
 * 创建询问窗口
 * 
 * @param title
 * @param content
 * @param url
 */
function createdialog(title, content, url,name) {
	$.dialog.confirm(content, function(){
		doSubmit(url,name);
		rowid = '';
	}, function(){
	}).zindex();
}
/**
 * 执行保存
 * 
 * @param url
 * @param gridname
 */
function saveObj() {
	//保存前，如果有单据日期对象，则要验证当前单据日期的值是否超过当期月份即表单域对象的stageDate属性；如果验证失败则弹出提示并不进行表单提交
	if ($("#date").length>0){
		var stageDate = $("#date").attr("stageDate");
		var date = $("#date").val();
		var tranType = $("#tranType").val();
		if (tranType=="1032" || tranType=="1031" || tranType=="1030" || tranType=="1020"){//初始化单据必须早于开账月份
			if (date>stageDate){
				tip("单据日期必须早于开账年月[" + stageDate + "]");
				return false;
			}
		}else{//普通单据必须晚于当前期间月份
			if (date<stageDate){
				tip("单据日期必须晚于当前期间年月[" + stageDate + "]");
				return false;
			}
		}
	}
	$('#btn_sub', iframe.document).click();
}

/**
 * 执行AJAX提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxSubForm(url) {
	$('#myform', iframe.document).form('submit', {
		url : url,
		onSubmit : function() {
			iframe.editor.sync();
		},
		success : function(r) {
			tip('Cancel');
			reloadTable();
		}
	});
}
/**
 * 执行查询
 * 
 * @param url
 * @param gridname
 */
function search() {

	$('#btn_sub', iframe.document).click();
	iframe.search();
}

/**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doSubmit(url,name,data) {
	gridname=name;
	//--author：Zerrion ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	var paramsData = data;
	if(!paramsData){
		paramsData = new Object();
		if (url.indexOf("&") != -1) {
			var str = url.substr(url.indexOf("&")+1);
			url = url.substr(0,url.indexOf("&"));
			var strs = str.split("&");
			for(var i = 0; i < strs.length; i ++) {
				paramsData[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
			}
		}      
	}
	//--author：Zerrion ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : paramsData,
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				tip(msg);
				reloadTable();
			}
		}
	});
	
	
}
/**
 * 退出确认框
 * 
 * @param url
 * @param content
 * @param index
 */
function exit(url, content) {
	$.dialog.confirm(content, function(){
		window.location = url;
	}, function(){
	}).zindex();
}
/**
 * 模板页面ajax提交
 * 
 * @param url
 * @param gridname
 */
function ajaxdoSub(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
			editor.sync();
		},
		success : function(r) {
			tip('Cancel');
		}
	});
}
/**
 * ajax提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxdoForm(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
		},
		success : function(r) {
			tip('Cancel');
		}
	});
}

function opensubwin(title, url, saveurl, okbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	}).zindex();
}

function openauditwin(title, url, saveurl, okbutton, backbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = $.dialog.open.origin;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : backbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#formobj', iframe.document).form('submit', {
					url : saveurl + "&code=exit",
					onSubmit : function() {
						$('#code').val('exit');
					},
					success : function(r) {
						$.dialog.tips('Cancel', 2);
						win.location.reload();
					}
				});

			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	}).zindex();
}
/*获取Cookie值*/
function getCookie(c_name)
{
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
// 添加标签
function addOneTab(subtitle, url, icon) {
	var indexStyle = getCookie("JEECGINDEXSTYLE");
	if(indexStyle=='sliding'||indexStyle=='bootstrap'||indexStyle=='ace'){
		//shortcut和bootstrap风格的tab跳转改为直接跳转
		window.location.href=url;
	}else{
		if (icon == '') {
			icon = 'icon folder';
		}
		window.top.$.messager.progress({
			text : 'Page loading...',
			interval : 300
		});
		window.top.$('#maintabs').tabs({
			onClose : function(subtitle, index) {
				window.top.$.messager.progress('close');
			}
		});
		if (window.top.$('#maintabs').tabs('exists', subtitle)) {
			window.top.$('#maintabs').tabs('select', subtitle);
			if (url.indexOf('isHref') != -1) {
				window.top.$('#maintabs').tabs('update', {
					tab : window.top.$('#maintabs').tabs('getSelected'),
					options : {
						title : subtitle,
						href:url,
						//content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						closable : true,
						icon : icon
					}
				});
			}else {
				window.top.$('#maintabs').tabs('update', {
					tab : window.top.$('#maintabs').tabs('getSelected'),
					options : {
						title : subtitle,
						content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						//content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						closable : true,
						icon : icon
					}
				});
			}
		} else {
			if (url.indexOf('isHref') != -1) {
				window.top.$('#maintabs').tabs('add', {
					title : subtitle,
					href:url,
					closable : true,
					icon : icon
				});
			}else {
				window.top.$('#maintabs').tabs('add', {
					title : subtitle,
					content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					closable : true,
					icon : icon
				});
			}
		}
	}
}
// 关闭自身TAB刷新父TABgrid
function closetab(title) {
	//暂时先不刷新
	//window.top.document.getElementById('tabiframe').contentWindow.reloadTable();
	//window.top.document.getElementById('maintabs').contentWindow.reloadTable();
	window.top.$('#maintabs').tabs('close', title);
	//tip("添加成功");
}

//popup  
//object: this  name:需要选择的列表的字段  code:动态报表的code
function inputClick(obj,name,code) {
	 $.dialog.setting.zIndex = 2000;
	 if(name==""||code==""){
		 alert("Popup parameter not prepare");
		 return;
	 }
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:cgReportController.do?popup&id="+code,
				lock : true,
				title:"Select",
				width:800,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("Please select");
			    		return false;
				    }else {
					    var str = "";
				    	$.each( selected, function(i, n){
					    	if (i==0)
					    	str+= n[name];
					    	else
				    		str+= ","+n[name];
				    	});
				    	$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);
				    	return true;
				    }
					
			    },
			    cancelVal: 'Close',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: "url:cgReportController.do?popup&id="+code,
				lock : true,
				title:"Select",
				width:800,
				height: 400,
				parent:windowapi,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("Please select");
			    		return false;
				    }else {
					    var str = "";
				    	$.each( selected, function(i, n){
					    	if (i==0)
					    	str+= n[name];
					    	else
				    		str+= ","+n[name];
				    	});
				    	$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);
				    	return true;
				    }
					
			    },
			    cancelVal: 'Close',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}
/*
	自定义url的弹出
	obj:要填充的控件,可以为多个，以逗号分隔
	name:列表中对应的字段,可以为多个，以逗号分隔（与obj要对应）
	url：弹出页面的Url
*/
function popClick(obj,name,url) {
	 $.dialog.setting.zIndex = 2001;
	var names = name.split(",");
	var objs = obj.split(",");
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"Select",
				width:700,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("Please select");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
							if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("input[name='"+objs[i1]+"']").val("");
								$("input[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					 
			    },
			    cancelVal: 'Close',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"Select",
				width:700,
				height: 400,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("Please select");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
					    	if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("[name='"+objs[i1]+"']").val("");
								$("[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					
			    },
			    cancelVal: 'Close',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}
/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function JeecgExcelExport(url,datagridId){
	var queryParams = $('#'+datagridId).datagrid('options').queryParams;
	$('#'+datagridId+'tb').find('*').each(function() {
	    queryParams[$(this).attr('name')] = $(this).val();
	});
	var params = '&';
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); 
	var fields = '&field=';
	$.each($('#'+ datagridId).datagrid('options').columns[0], function(i, val){
		if(val.field != 'opt'){
			fields+=val.field+',';
		}
	}); 
	window.location.href = url+ encodeURI(fields+params);
}
/**
 * 自动完成的解析函数
 * @param data
 * @returns {Array}
 */
function jeecgAutoParse(data){
	var parsed = [];
    	$.each(data.rows,function(index,row){
    		parsed.push({data:row,result:row,value:row.id});
    	});
			return parsed;
}
