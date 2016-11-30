var h = ($(window).height() - 285) / 2;
var rowIndex = 0;
//初始化下标
function resetTrNum(tableId, index, indexName, isAdd) {

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
                if (name.indexOf("stockName") > -1) {

                    var stockId = $("input[name='" + indexName + "[" + i + "].stockId']").val();
                    var stockName = $("input[name='" + indexName + "[" + i + "].stockName']").val();
                    setValOldIdAnOldName($("input[name='" + indexName + "[" + i + "].stockName']"), stockId, stockName);
                    $this.attr("onblur", "orderListStockBlur(" + i + ",'stockId','stockName','" + indexName + "')");
                } else if (name.indexOf("itemNo") > -1) {
                    $this.attr("onblur", "orderListStockBlur(" + i + ",'itemId','itemNo','" + indexName + "')");
                } else if (name.indexOf("inTaxAmount") > -1) {
                    $this.attr("onchange", "setAllAmount()");
                } else if (name.indexOf("kfDateType") > -1 || name.indexOf("kfDate") > -1 || name.indexOf("kfPeriod") > -1) {
                    $this.attr("onchange", "setPeriodDate(" + i + ",'" + indexName + "')");
                } else if (name.indexOf("taxRate") > -1) {
                    var defaultRate = $("#CFG_TAX_RATE").val();
                    $this.val(defaultRate);
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
                    if (name.indexOf("kfDate") < 0) {
                        var s = onclick_str.indexOf("(");
                        var e = onclick_str.indexOf(",");
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
        setFunctionInfo(i, indexName);
        checkAllowAddLine(i);
        $("input[name='" + indexName + "[" + i + "].findex']").val(i + 1);
        $(this).find('div[name=\'xh\']').html(i + 1);
    });

    $tbody.find('>tr').each(function (i) {
        if (i == (parseInt(index) + 1) && isAdd) {
            var num = indexName.replace(/[^0-9]/ig, "");
            $("#unitId" + num + "\\[" + i + "\\]").combobox({
                width: 54,
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
function commonUpload(callback, inputId) {
    $.dialog({
        content: "url:systemController.do?commonUpload",
        lock: true,
        title: "文件上传",
        zIndex: 2100,
        width: 700,
        height: 200,
        parent: windowapi,
        cache: false,
        ok: function () {
            var iframe = this.iframe.contentWindow;
            iframe.uploadCallback(callback, inputId);
            return true;
        },
        cancelVal: '关闭',
        cancel: function () {
        }
    });
}
//通用弹出式文件上传-回调
function commonUploadDefaultCallBack(url, name, inputId) {
    $("#" + inputId + "_href").attr('href', url).html('下载');
    $("#" + inputId).val(url);
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
    var finder = new CKFinder();
    finder.selectActionFunction = function (fileUrl, data) {//设置文件被选中时的函数
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
    finder.selectActionFunction = function (fileUrl, data) {//设置文件被选中时的函数
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


$(function () {
    setDefaultDate();//设置默认时间
    setDefaultOldInfo();//设置旧数据
    addButton();//添加选单按钮
    checkcurPayAmount();
    $("#chooseBill").bind("click", function () {
        selectBillDialog();
    })
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
    $("#empName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectEmpDialog();
        }
    })

    $("#accountName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectAccountDialog();
        }
    })


    $("#itemName").blur(function () {
        checkInput($("#itemId"), $("#itemName"));
    })


    $("#deptName").blur(function () {
        checkInput($("#deptId"), $("#deptName"));
    })
    $("#empName").blur(function () {
        checkInput($("#empId"), $("#empName"));
    })
    $("#accountName").blur(function () {
        checkInput($("#accountId"), $("#accountName"));
    })

})
//添加选单按钮
function addButton() {
    var button = document.createElement("input");
    button.id = "chooseBill";
    button.type = "button";
    button.class = "button";
    button.value = "选单"
    var buttonDiv = document.getElementsByClassName("ui_buttons");
    var load = $("#load").val()
    if ("detail" != load) {
        //buttonDiv[0].appendChild(button);
        var select = document.createElement("select");
        select.id = "chooseType";
        select.setAttribute("onchange", "selectBillDialog()");
        select.setAttribute("style", "margin-left:10px;");
        var option2 = document.createElement("option");
        option2.value = "";
        option2.innerText = "选单";
        option2.select = "select";
        select.appendChild(option2);

        var option1 = document.createElement("option");
        option1.value = "52";
        option1.innerText = "采购入库单";
        select.appendChild(option1);
        buttonDiv[0].appendChild(select);
    }
}

//设置旧数据
function setDefaultOldInfo() {
    var itemId = $("#itemId").val();
    var itemName = $("#itemName").val();
    setValOldIdAnOldName($("#itemName"), itemId, itemName);

    var empId = $("#empId").val();
    var empName = $("#empName").val();
    setValOldIdAnOldName($("#empName"), empId, empName);

    var stockId = $("#stockId").val();
    var stockName = $("#stockName").val();
    setValOldIdAnOldName($("#stockName"), stockId, stockName);

    var deptId = $("#deptId").val();
    var deptName = $("#deptName").val();
    setValOldIdAnOldName($("#deptName"), deptId, deptName);

}

//设置单据日期默认为当前日期
function setDefaultDate() {
    var date = new Date($("input[name='date']").val());
    var year = date.getFullYear();
    var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
    var date = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var billDate = $("#date").val();
    var id = $("#id").val();
    if (!id) {
        //$("input[name='date']").val(year+"-"+month+"-"+date);
        $("input[name='tScIcJhstockbillentry1List[#index#].kfDate']").val(year + "-" + month + "-" + date);
        $("input[name='tScIcJhstockbillentry2List[#index#].kfDate']").val(year + "-" + month + "-" + date);
    } else {
        var dateValue = $("input[name='date']").val();
        dateValue = new Date(dateValue);
        var year = dateValue.getFullYear();
        var month = (dateValue.getMonth() + 1) < 10 ? "0" + (dateValue.getMonth() + 1) : (dateValue.getMonth() + 1);
        var date = dateValue.getDate() < 10 ? "0" + dateValue.getDate() : dateValue.getDate();
        dateValue = year + "-" + month + "-" + date;
        $("input[name='tScIcJhstockbillentry1List[#index#].kfDate']").val(dateValue);
        $("input[name='tScIcJhstockbillentry2List[#index#].kfDate']").val(dateValue);
    }
}

//赠品设置单价为0；
function setPrice(index) {
    var selected = document.getElementsByName("tScIcJhstockbillentry1List[" + index + "].freeGifts_");
    var i = selected[0].selectedIndex;
    var value = selected[0].options[i].value;
    $("input[name='tScIcJhstockbillentry1List[" + index + "].freeGifts']").val(value);
    if (value == 1) {
        $("input[name='tScIcJhstockbillentry1List[" + index + "].taxPriceEx']").val(0);//单价
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

//选择供应商
function selectItemNameDialog() {
    var itemName = $("#itemName").val();
    var url = 'tScSupplierController.do?goSelectDialog&name=' + itemName;
    var width = 800;
    var height = 500;
    var title = "供应商";
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
                $("#contact").val(item.contact);
                $("#mobilePhone").val(item.mobilePhone);
                $("#phone").val(item.phone);
                $("#fax").val(item.fax);
                $("#address").val(item.address);
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
function selectDeptDialog() {
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
function selectEmpDialog() {
    var empName = $("#empName").val();
    var url = "tScEmpController.do?goselectdeptIdNameDialog&name=" + empName;
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
function selectStockDialog(index, entryName) {
    var itemName = $("input[name='" + entryName + "List[" + index + "].stockName']").val();
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
                if (!index && index != 0) {
                    $("#stockName").val(item.name);
                    $("#stockId").val(item.id);
                    $tbody = $("#add_" + entryName + "_table");
                    var length = $tbody[0].rows.length;
                    $("input[name='" + entryName + "List[#index#].stockId']").val(item.id);
                    $("input[name='" + entryName + "List[#index#].stockName']").val(item.name);
                    for (var i = 0; i < length; i++) {
                        $("input[name='" + entryName + "List[" + i + "].stockId']").val(item.id);
                        $("input[name='" + entryName + "List[" + i + "].stockName']").val(item.name);
                        setValOldIdAnOldName($("input[name='" + entryName + "List[" + i + "].stockName']"), item.id, item.name);
                    }
                    setValOldIdAnOldName($("#stockName"), item.id, item.name);
                } else {
                    $("input[name='" + entryName + "List[" + index + "].stockId']").val(item.id);
                    $("input[name='" + entryName + "List[" + index + "].stockName']").val(item.name);
                    setValOldIdAnOldName($("input[name='" + entryName + "List[" + index + "].stockName']"), item.id, item.name);
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
function onPriceChange(index) {
    var priceEx = $("input[name='tScIcJhstockbillentry1List[" + index + "].taxPriceEx']").val();//单价
    if (priceEx > 0) {
        $("select[name='tScIcJhstockbillentry1List[" + index + "].freeGifts_']").val(0);
        $("inout[name='tScIcJhstockbillentry1List[" + index + "].freeGifts']").val(0);
    }
    var qty = $("input[name='tScIcJhstockbillentry1List[" + index + "].qty']").val();//数量
    var discountRate = $("input[name='tScIcJhstockbillentry1List[" + index + "].discountRate']").val();//折扣率
    var taxRate = $("input[name='tScIcJhstockbillentry1List[" + index + "].taxRate']").val();//税率
    if (!priceEx) {
        priceEx = 0;
    }
    if (!qty) {
        qty = 0;
    }
    if (!discountRate) {
        discountRate = 100;
    }
    if (!taxRate) {
        taxRate = 0;
    }
    var price = priceEx / (1 + taxRate / 100);
    var amount = price * qty;
    var discountPrice = price * discountRate / 100;
    var discountAmount = discountPrice * qty;
    var taxAmountEx = priceEx * qty;
    var disprice = priceEx * discountRate / 100;
    var disAmount = disprice * qty;
    var taxAmount = disAmount - (disAmount / (1 + (taxRate / 100)));
    $("input[name='tScIcJhstockbillentry1List[" + index + "].price']").val(price.toFixed(2));//不含税单价
    $("input[name='tScIcJhstockbillentry1List[" + index + "].amount']").val(amount.toFixed(2));//不含税金额
    $("input[name='tScIcJhstockbillentry1List[" + index + "].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
    $("input[name='tScIcJhstockbillentry1List[" + index + "].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
    $("input[name='tScIcJhstockbillentry1List[" + index + "].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
    $("input[name='tScIcJhstockbillentry1List[" + index + "].taxPrice']").val(disprice.toFixed(2));//折后单价
    $("input[name='tScIcJhstockbillentry1List[" + index + "].taxAmount']").val(taxAmount.toFixed(2));//税额
    $("input[name='tScIcJhstockbillentry1List[" + index + "].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
    setAllAmount();
}


function selectIcitemDialog(index, entryName) {
    var itemNo = $("input[name='" + entryName + "[" + index + "].itemNo']").val();
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
                var nextTrLength = $('input[name="' + entryName + '[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
                var newLength = itemLength - nextTrLength - 1
                for (var j = 0; j < newLength; j++) {
                    var name = entryName.substring(0, entryName.length - 4);
                    if (name.indexOf("1") > -1) {
                        clickAddTScIcJhstockbillentry1Btn(index, name);
                    } else {
                        clickAddTScIcJhstockbillentry2Btn(index, name);
                    }
                }
                var sonId = $("#sonId").val();
                $.each(itemList, function (i, item) {
                    //var length = $("#add_tScPoStockbillentry_table").find(">tr").length;
                    //if(i > 0 ){
                    //	clickAddTScIcJhstockbillentry1Btn(index);
                    //	index ++;
                    //}
                    //var item = itemList[i];
                    var rowindex = parseInt(index) + i;
                    var id = item.id;//id
                    var number = item.number;//编码
                    var name = item.name;//名称
                    var model = item.model;//规格
                    var kfPeriod = item.kFPeriod;//保质期
                    var kfDateType = item.kFDateType;//保质期类型
                    var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
                    var batchManager = item.batchManager;//是否批号管理
                    var taxRate = item.taxRateIn;//进项税
                    var stockSonId = item.stockSonId;//商品默认仓库分支机构id

                    var url = "tScIcitemController.do?getItemInfo&id=" + id + "&rowIndex=" + rowindex + "&tranType=52";
                    $.ajax({
                        url: url,
                        dataType: 'json',
                        cache: false,
                        success: function (data) {
                            var attr = data.attributes;
                            var xsLimitPrice = attr.cgLimitPrice;
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
                            var chkId = $("input[name='" + entryName + "[" + rowIndex + "].stockId']").val();
                            if (!chkId) {
                                if (stockSonId == sonId) {
                                    $("input[name='" + entryName + "[" + rowIndex + "].stockId']").val(stockId);
                                    $("input[name='" + entryName + "[" + rowIndex + "].stockName']").val(stockName);
                                }
                            }
                            setValOldIdAnOldName($("input[name='" + entryName + "[" + rowIndex + "].stockName']"), stockId, stockName);

                            var num = entryName.replace(/[^0-9]/ig, "");
                            $("#unitId" + num + "\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
                            $("#unitId" + num + "\\[" + rowIndex + "\\]").combobox({
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
                                                var cgLimitPrice = attributes.cgLimitPrice;
                                                $("input[name='" + entryName + "[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);//采购限价
                                                $("input[name='" + entryName + "[" + rowIndex + "].barCode']").val(barCode);
                                                $("input[name='" + entryName + "[" + rowIndex + "].coefficient']").val(cofficient);
                                                $("input[name='" + entryName + "[" + rowIndex + "].coefficient']").trigger("change");
                                            }
                                        });
                                    }
                                }
                            })
                            $("#unitId" + num + "\\[" + rowIndex + "\\]").combobox("setValue", unitId);

                            $("input[name='" + entryName + "[" + rowIndex + "].taxRate']").val(taxRate);//税率
                            $("input[name='" + entryName + "[" + rowIndex + "].basicCoefficient']").val(basicCoefficient);//基本换算率
                            $("input[name='" + entryName + "[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);//销售限价
                            $("input[name='" + entryName + "[" + rowIndex + "].itemNo']").val(number);
                            setValOldIdAnOldName($("input[name='" + entryName + "[" + rowIndex + "].itemNo']"), id, number);
                            $("input[name='" + entryName + "[" + rowIndex + "].itemId']").val(id);
                            $("input[name='" + entryName + "[" + rowIndex + "].model']").val(model);
                            $("input[name='" + entryName + "[" + rowIndex + "].itemName']").val(name);
                            $("input[name='" + entryName + "[" + rowIndex + "].barCode']").val(barCode);
                            $("input[name='" + entryName + "[" + rowIndex + "].coefficient']").val(cofficient);
                            $("input[name='" + entryName + "[" + rowIndex + "].coefficient']").trigger("blur");
                            $("input[name='" + entryName + "[" + rowIndex + "].secUnitId']").val(secUnitId);
                            $("input[name='" + entryName + "[" + rowIndex + "].secCoefficient']").val(secCoefficient);
                            $("input[name='" + entryName + "[" + rowIndex + "].secCoefficient']").trigger("change");
                            $("input[name='" + entryName + "[" + rowIndex + "].basicUnitId']").val(basicUnitId);
                            $("input[name='" + entryName + "[" + rowIndex + "].isKFPeriod']").val(iSKFPeriod);
                            $("input[name='" + entryName + "[" + rowIndex + "].batchManager']").val(batchManager);
                            if ("Y" == iSKFPeriod) {
                                $("input[name='" + entryName + "[" + rowIndex + "].kfPeriod']").val(kfPeriod);
                                $("input[name='" + entryName + "[" + rowIndex + "].kfDateType']").val(kfDateType);
                                $("select[name='" + entryName + "[" + rowIndex + "].kfDateType_']").val(kfDateType);
                                var kfDate = $("input[name='" + entryName + "[" + rowIndex + "].kfDate']").val();
                                var interval = "";
                                if (kfDateType == "0001") {
                                    interval = "y";
                                } else if (kfDateType == "0002") {
                                    interval = "m";
                                } else if (kfDateType == "0003") {
                                    interval = "d";
                                }
                                var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
                                $("input[name='" + entryName + "[" + rowIndex + "].periodDate']").val(periodDate);
                            } else {
                                $("input[name='" + entryName + "[" + rowIndex + "].kfDate']").attr("readonly", "readonly");
                                $("input[name='" + entryName + "[" + rowIndex + "].kfDate']").attr("onClick", "");
                                $("input[name='" + entryName + "[" + rowIndex + "].kfDate']").val("");
                                $("input[name='" + entryName + "[" + rowIndex + "].kfDate']").css("background-color", "rgb(226,226,226)");
                                $("input[name='" + entryName + "[" + rowIndex + "].kfPeriod']").val("");
                                $("input[name='" + entryName + "[" + rowIndex + "].kfPeriod']").attr("readonly", "readonly");
                                $("input[name='" + entryName + "[" + rowIndex + "].kfPeriod']").css("background-color", "rgb(226,226,226)");
                                $("select[name='" + entryName + "[" + rowIndex + "].kfDateType']").val("");
                                $("select[name='" + entryName + "[" + rowIndex + "].kfDateType']").attr("disabled", "disabled");
                                $("select[name='" + entryName + "[" + rowIndex + "].kfDateType']").css("background-color", "rgb(226,226,226)");
                                $("input[name='" + entryName + "[" + rowIndex + "].periodDate']").val("");
                            }
                            if ("N" == batchManager) {
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").val("");
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").attr("readonly", "readonly");
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").css("background-color", "rgb(226,226,226)");
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").removeAttr("datatype");
                            } else {
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").removeAttr("readonly");
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").attr("dataType", "*");
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").css("background-color", "rgb(255,255,255)");

                                var batchName = "batchNo";
                                if (entryName.indexOf("2") > -1) {
                                    batchName = "batchNo2";
                                }
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + rowIndex + "','" + batchName + "')");
                                $("input[name='" + entryName + "[" + rowIndex + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+rowIndex+"','"+entryName+"')");
                            }
                            //var className = $("input[name='tScIcJhstockbillentry1List["+index+"].unitId']")[0].className;
                            //if(className == "combo-value"){
                            //	$("input[name='tScIcJhstockbillentry1List["+index+"].unitId']").combobox("reload");
                            //}

                            /*var aa = $("input[name='tScIcJhstockbillentry1List\\[" + index + "\\]\\.unitId']:first").combobox({
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
                             //				$("input[name='tScIcJhstockbillentry1List[" + index + "].barCode']").val(barCode);
                             //				$("input[name='tScIcJhstockbillentry1List[" + index + "].coefficient']").val(cofficient);
                             //			}
                             //		});
                             //	}
                             //}
                             });*/
                            //$("input[name='tScIcJhstockbillentry1List["+index+"].unitId']").combobox("setValue",unitId);
                            setValOldIdAnOldName($("input[name='" + entryName + "[" + rowIndex + "].itemNo']"), itemId, number);
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

function editQtyChange(index) {
    var oldValue = $("input[name='tScIcJhstockbillentry1List[" + index + "].qty']").attr("oldvalue");
    var newValue = $("input[name='tScIcJhstockbillentry1List[" + index + "].qty']").val();
    if (oldValue > newValue) {
        tip("数量不可小于当前数量");
        $("input[name='tScIcJhstockbillentry1List[" + index + "].qty']").val(oldValue);
        return;
    }
    setAllQty(index);
}

//汇总折扣金额
function setAllAmount() {
    var CFG_MONEY = $("#CFG_MONEY").val();
    $tbody1 = $("#tScIcJhstockbillentry1_table");
    var length = $tbody1[0].rows.length;
    //退货明细汇总数据
    var allAmount = 0;
    var allAmountTax = 0;
    var allQty = 0;

    //换货明细汇总数据
    var allAmount2 = 0;
    var allAmountTax2 = 0;
    var allQty2 = 0;
    for (var i = 0; i < length; i++) {
        var amount = $("input[name='tScIcJhstockbillentry1List[" + i + "].inTaxAmount']").val();//折后金额
        var amount2 = $("input[name='tScIcJhstockbillentry1List[" + i + "].taxAmountEx']").val();//金额
        var qty = $("input[name='tScIcJhstockbillentry1List[" + i + "].qty']").val();//数量
        if (!amount) {
            amount = 0;
        }
        if (!amount2) {
            amount2 = 0;
        }
        if (!qty) {
            qty = 0;
        }
        allAmount += parseFloat(amount);
        allAmountTax += parseFloat(amount2);
        allQty += parseFloat(qty);
    }

    $tbody2 = $("#tScIcJhstockbillentry2_table");
    length = $tbody2[0].rows.length;

    for (var j = 0; j < length; j++) {
        var amount = $("input[name='tScIcJhstockbillentry2List[" + j + "].inTaxAmount']").val();//折后金额
        var amount2 = $("input[name='tScIcJhstockbillentry2List[" + j + "].taxAmountEx']").val();//金额
        var qty = $("input[name='tScIcJhstockbillentry2List[" + j + "].qty']").val();//数量
        if (!amount) {
            amount = 0;
        }
        if (!amount2) {
            amount2 = 0;
        }
        if (!qty) {
            qty = 0;
        }
        allAmount2 += parseFloat(amount);
        allAmountTax2 += parseFloat(amount2);
        allQty2 += parseFloat(qty);
    }

    var diffAmount = allAmount2 - allAmount;

    var rebateAmount = $("#rebateAmount").val();
    if (!rebateAmount) {
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
function selectAccountDialog() {
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
                $("#accountId").val(item.id);
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
function checkPriceIsZero(item) {
    var accountId = $("#accountId").val();
    var isCheckNegative = $("#isCheckNegative").val();
    if (accountId) {
        var curPayAmount = $("#curPayAmount").val();
        if (curPayAmount <= 0) {
            tip("本次付款不可小于0，请确认");
            return false;
        }
    }
    var $tbody = $("#tScIcJhstockbillentry1_table");
    var length = $tbody[0].rows.length;
    var checkItemName = "";
    var checkItemName2 = "";
    var checkValue = "";
    var $tbody2 = $("#tScIcJhstockbillentry2_table");
    var length2 = $tbody2[0].rows.length;
    //TODO xsLimitPrice 为采购限价
    for (var checkIndex = 0; checkIndex < length; checkIndex++) {
        var price = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].taxPriceEx']").val();
        var itemName = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].itemName']").val();
        var xsLimitPrice = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].xsLimitPrice']").val();
        var itemId = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].itemId']").val();
        var stockId = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].stockId']").val();
        var qty = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].basicQty']").val();
        var batchNo = $("input[name='tScIcJhstockbillentry1List[" + checkIndex + "].batchNo']").val();
        if (isCheckNegative) {
            checkValue += itemId + "#" + itemName + "#" + stockId + "#" + qty + "#" + batchNo + ",";
        }
        if (!xsLimitPrice) {
            continue;
        } else {
            if (parseFloat(price) > parseFloat(xsLimitPrice) && parseFloat(xsLimitPrice) > 0) {
                checkItemName += itemName + ",";
            }
        }
    }
    for (var checkIndex2 = 0; checkIndex2 < length2; checkIndex2++) {
        var price = $("input[name='tScIcJhstockbillentry2List[" + checkIndex2 + "].taxPriceEx']").val();
        var itemName = $("input[name='tScIcJhstockbillentry2List[" + checkIndex2 + "].itemName']").val();
        var xsLimitPrice = $("input[name='tScIcJhstockbillentry2List[" + checkIndex2 + "].xsLimitPrice']").val();
        if (!xsLimitPrice) {
            continue;
        } else {
            if (parseFloat(price) > parseFloat(xsLimitPrice) && parseFloat(xsLimitPrice) > 0) {
                checkItemName2 += itemName + ",";
            }
        }
    }
    if (isCheck || isCheck2) {
        if (checkItemName.length > 0 || checkItemName2.length > 0 || checkValue.length > 0) {
            if ((checkItemName.length > 0 || checkItemName2.length > 0) && isCheck) {
                var itemName = "";
                if (checkItemName.length > 0) {
                    itemName += "退货商品[" + checkItemName + "] ";
                }
                if (checkItemName2.length > 0) {
                    itemName += "换货商品[" + checkItemName2 + "] ";
                }
                var confirmStr = itemName + "的单价高于采购限价，是否继续保存？";
                $.dialog.confirm(confirmStr, function () {
                        isCheck = false;
                        $("#formobj").submit();
                    }
                    , function () {
                        //
                    })
            } else if (checkValue.length > 0) {
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
        } else {
            return true;
        }
    } else {
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
    //					var price = $("input[name='tScIcJhstockbillentry1List["+i+"].taxPriceEx']").val();
    //					if(!price || price == 0){
    //						itemName += $("input[name='tScIcJhstockbillentry1List["+i+"].itemName']").val()+",";
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


function dateAdd(interval, number, date) {
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
    switch (interval) {
        case   "y"   :
        {
            date.setFullYear(date.getFullYear() + number);
            break;
        }
        case   "q"   :
        {
            date.setMonth(date.getMonth() + number * 3);
            break;
        }
        case   "m"   :
        {
            date.setMonth(date.getMonth() + number);
            break;
        }
        case   "w"   :
        {
            date.setDate(date.getDate() + number * 7);
            break;
        }
        case   "d"   :
        {
            date.setDate(date.getDate() + number);
            break;
        }
        case   "h"   :
        {
            date.setHours(date.getHours() + number);
            break;
        }
        case   "mi"   :
        {
            date.setMinutes(date.getMinutes() + number);
            break;
        }
        case   "s"   :
        {
            date.setSeconds(date.getSeconds() + number);
            break;
        }
        default   :
        {
            date.setDate(d.getDate() + number);
            break;
        }
    }
    var dateStr = date.getFullYear() + "-" + (((date.getMonth() + 1) < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) +
        "-" + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
    return dateStr
}


//设置有效期至
function setPeriodDate(index, entryName) {
    var kfDate = $("input[name='" + entryName + "[" + index + "].kfDate']").val();//生产日期
    var kfPeriod = $("input[name='" + entryName + "[" + index + "].kfPeriod']").val();//保质期
    var kfDateType = $("select[name='" + entryName + "[" + index + "].kfDateType_']").val();//保质期类型
    var interval = "";
    if (kfDateType == "0001") {
        interval = "y";
    } else if (kfDateType == "0002") {
        interval = "m";
    } else if (kfDateType == "0003") {
        interval = "d";
    }
    var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
    $("input[name='" + entryName + "[" + index + "].periodDate']").val(periodDate);
}

//选单
function selectBillDialog() {
    var itemId = $("#itemId").val();
    if (!itemId) {
        tip("请选择供应商后再进行选单操作");
        $("#chooseType").val("");
        return;
    }
    var sonId = $("#sonId").val();
    var tranType = $("#tranType").val();
    var url = "tScPoStockbillController.do?goSelectDialog&itemId=" + itemId + "&tranType=" + tranType+"&sonId="+sonId;
    var width = 800;
    var height = 500;
    var title = "采购入库单";
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
                var ids = "";
                for (var i = 0; i < itemList.length; i++) {
                    var item = itemList[i];
                    ids += item.id + ",";
                }
                var item = itemList[itemList.length - 1];
                $("#classIDSrc").val(item.tranType);
                $("#idSrc").val(item.id);
                $("#billNoSrc").val(item.billNo);
                $("#itemName").attr("readonly", "readonly");
                $("#deptId").val(item.deptId);
                $("#deptName").val(item.deptName);
                setValOldIdAnOldName($("#deptName"), item.deptId, item.deptName);
                $("#empId").val(item.empId);
                $("#empName").val(item.empName);
                setValOldIdAnOldName($("#empName"), item.empId, item.empName);
                $("#contact").val(item.contact);
                var length = $("#add_tScIcJhstockbillentry1_table").find(">tr").length;
                length--;
                var id = $("input[name='tScIcJhstockbillentry1List[" + length + "].itemId']").val();
                var dataLength = itemList.length;
                if (!id) {
                    dataLength--;
                }
                for (var k = 0; k < dataLength; k++) {
                    clickAddTScIcJhstockbillentry1Btn(length, "tScIcJhstockbillentry1")
                }
                if (id) {
                    length++;
                }
                for (var i = 0; i < itemList.length; i++) {
                    var row = itemList[i];
                    for (var k in row) {
                        if (k == "unitId") {
                            $("#unitId1\\[" + length + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + row.entryItemId);
                            $("#unitId1\\[" + length + "\\]").combobox("setValue", row[k]);
                            $("#unitId1\\[" + length + "\\]").combobox("disable");
                        } else if (k == "kfDateTypeInfo") {
                            if ("N" == row.isKFPeriod) {

                            } else {
                                $("select[name='tScIcJhstockbillentry1List[" + length + "].kfDateType_']").val(row[k]);
                                $("input[name='tScIcJhstockbillentry1List[" + length + "].kfDateType']").val(row[k]);
                            }
                        } else if (k == "kfPeriod") {
                            if ("N" == row.isKFPeriod) {
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").attr("readonly", "readonly");
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").css("background-color", "rgb(226,226,226)");
                            } else {
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").val(row[k]);
                            }
                        } else if (k.indexOf("Date") > -1) {
                            if ("N" == row.isKFPeriod) {
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").attr("readonly", "readonly");
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").attr("onClick", "");
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").val("");
                            } else if ((k == "kfDate" || k == "periodDate") && "Y" == row.isKFPeriod) {
                                var date = new Date(row[k]);
                                var year = date.getFullYear();
                                var mongth = ((date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1));
                                var dates = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                                var newDate = year + "-" + mongth + "-" + dates;
                                $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").val(newDate);
                            }
                        } else if (k == "qty") {
                            var stockQty = row.commitQty;//退货数量
                            var qty = row[k];
                            var basicQty = row["basicQty"];
                            var coe = row["coefficient"];
                            qty = ((parseFloat(basicQty) - parseFloat(stockQty)) / coe).toFixed(CFG_NUMBER);
                            $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").val(qty);
                            $("input[name='tScIcJhstockbillentry1List[" + length + "].billQty']").val(row[k]);
                        }
                        else {
                            $("input[name='tScIcJhstockbillentry1List[" + length + "]." + k + "']").val(row[k]);
                        }
                        if (k == "entryItemNo") {
                            //$("input[name='tScIcJhstockbillentry1List[" + length + "].itemNo']").attr("readonly","readonly");
                            //$("input[name='tScIcJhstockbillentry1List[" + length + "].itemNo']").attr("onclick","this.blur()");
                        }
                    }
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].isKFPeriod']").val(row.isKFPeriod);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].batchManager']").val(row.batchManager);
                    if ("Y" == row.isKFPeriod) {
                        setPeriodDate(length, "tScIcJhstockbillentry1List");
                    }
                    //不修改生产日期
                    $("input[name='tScIcJhstockbillentry1List["+length+"].kfDate']").attr("readonly","readonly");
                    $("input[name='tScIcJhstockbillentry1List["+length+"].kfDate']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScIcJhstockbillentry1List["+length+"].kfDate']").removeAttr("onclick");
                    //if ("N" == row.batchManager) {
                        $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").val(row.batchNo);
                        $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").attr("readonly", "readonly");
                        $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").css("background-color", "rgb(226,226,226)");
                        $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").removeAttr("datatype");
                    //} else {
                   //     $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").removeAttr("readonly");
                   //     $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").css("background-color", "white");
                  //      $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").attr("datatype","*");
                  //      $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + length + "','batchNo')");
                  //      $("input[name='tScIcJhstockbillentry1List[" + length + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+length+"','tScIcJhstockbillentry1List')");
                  //  }
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].itemId']").val(row.entryItemId);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].itemNo']").val(row.entryItemNo);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].itemName']").val(row.entryItemName);

                    $("input[name='tScIcJhstockbillentry1List[" + length + "].stockId']").val(row.entryStockId);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].stockName']").val(row.entryStockName);
                    setValOldIdAnOldName($("input[name='tScIcJhstockbillentry1List[" + length + "].stockName']"), row.entryStockId, row.entryStockName);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].stockName']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].stockName']").css("background-color", "rgb(226,226,226)");


                    var basicCoefficient = $("input[name='tScIcJhstockbillentry1List[" + length + "].basicCoefficient']").val();
                    var discountPrice = $("input[name='tScIcJhstockbillentry1List[" + length + "].discountPrice']").val();
                    var costPrice = parseFloat(discountPrice) / parseFloat(basicCoefficient)
                    var qty = $("input[name='tScIcJhstockbillentry1List[" + length + "].qty']").val();
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].costPrice']").val(costPrice.toFixed(2));
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].costAmount']").val((costPrice.toFixed(2) * qty).toFixed(2))
                    //$("input[name='tScIcJhstockbillentry1List[" + i + "].idSrc']").val(item.id);
                    //$("input[name='tScIcJhstockbillentry1List[" + i + "].billNoSrc']").val(item.billNo);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].idSrc']").val(row.id);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].billNoSrc']").val(row.billNo);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].classIDSrc']").val(row.tranType);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].entryIdSrc']").val(row.entryId);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].billNoOrder']").val(row.billNoOrder);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].idOrder']").val(row.idOrder);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].entryIdOrder']").val(row.entryIdOrder);
                    setFunctionInfo(length, "tScIcJhstockbillentry1List");
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].qty']").trigger("change");
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].taxPriceEx']").trigger("change");
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].taxAmountEx']").trigger("change");

                    $("input[name='tScIcJhstockbillentry1List[" + length + "].xsLimitPrice']").val(row.cgLimitPrice);

                    var itemNo = $("input[name='tScIcJhstockbillentry1List[" + length + "].itemNo']").val();
                    var itemId = $("input[name='tScIcJhstockbillentry1List[" + length + "].itemId']").val();
                    setValOldIdAnOldName($("input[name='tScIcJhstockbillentry1List[" + length + "].itemNo']"), itemId, itemNo);
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].itemNo']").attr("readonly", "readonly");
                    var tranTypeName = row.tranTypeName;
                    $("input[name='tScIcJhstockbillentry1List[" + length + "].classIDName']").val(tranTypeName);

                    length++;

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
function changeAllAmount() {
    var rebateAmount = $("#rebateAmount").val();
    if (!rebateAmount) {
        rebateAmount = 0;
    }
    var allAmount = $("#diffAmount").val();
    if (allAmount) {
        allAmount = parseFloat(allAmount) - parseFloat(rebateAmount);
        $("#allAmount").val(allAmount);
    }
}

//表格校验事件
function orderListStockBlur(rowIndex, id, name, entryName) {
    checkInput($('input[name="' + entryName + '[' + rowIndex + '].' + id + '"]'), $('input[name="' + entryName + '[' + rowIndex + '].' + name + '"]'));
}

//判断是否允许增行操作
function checkAllowAddLine(index) {
    var isAddLine = true;
    if (!isAddLine) {
        $("#addTScOrderentryBtn\\[" + index + "\\]").css("display", "none");
        $("input[name='tScIcJhstockbillentry1List[" + index + "].itemNo']").attr("readonly", "readonly");
    }
}


function afterAudit() {
    var id = $("#id").val();
    var url = "tScIcJhstockbillController.do?afterAudit&audit=1&id=" + id;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}

function afterUnAudit() {
    var id = $("#id").val();
    var url = "tScIcJhstockbillController.do?afterAudit&audit=0&id=" + id;
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
function checkAudit() {
    var isCheckNegative = $("#isCheckNegative").val();
    if (isCheckNegative == "true") {
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
            url: "tSAuditController.do?checkIsNegative&id=" + billId + "&tranType=" + tranType + "&tableName=t_sc_ic_jhstockbillentry1&parentId=fid",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (!data.success) {
                    isContinue = false;
                    itemName = data.msg;
                }
            }
        })
        if (isContinue) {
            return false;
        } else {
            itemName = itemName.substring(0, itemName.length - 1);
            return "该单据审核后,商品【" + itemName + "】将出现负库存，不可审核，请确认";
        }
    } else {
        return false;
    }
}

//审核前校验
function checkUnAudit() {
    var isCheckNegative = $("#isCheckNegative").val();
    if (isCheckNegative == "true") {
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
            url: "tSAuditController.do?checkIsNegative&id=" + billId + "&tranType=" + tranType + "&tableName=t_sc_ic_jhstockbillentry2&parentId=fid",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (!data.success) {
                    isContinue = false;
                    itemName = data.msg;
                }
            }
        })
        if (isContinue) {
            return false;
        } else {
            itemName = itemName.substring(0, itemName.length - 1);
            return "该单据反审核后,商品【" + itemName + "】将出现负库存，不可反审核，请确认";
        }
    } else {
        return false;
    }
}

//校验结算账户
function checkcurPayAmount() {
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
//批号回车事件
function selectInventoryInfo(index, entryName) {
    var itemId = $("input[name='" + entryName + "[" + index + "].itemId']").val();//商品id
    var stockId = $("input[name='" + entryName + "[" + index + "].stockId']").val();//仓库id
    var url = 'tScIcitemController.do?goInventorySelectDialog&id=' + itemId;
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
                    setPeriodDate(index, entryName);
                    //$("input[name='"+entryName+"[" + index + "].kfDate']").trigger("change");
                }
                $("input[name='" + entryName + "[" + index + "].batchNo']").val(item.batchNo);
                $("input[name='" + entryName + "[" + index + "].stockId']").val(item.stockId);
                $("input[name='" + entryName + "[" + index + "].stockName']").val(item.stockName);
                setValOldIdAnOldName($("input[name='" + entryName + "[" + index + "].stockName']"), item.stockId, item.stockName);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}

//草稿恢复后操作事项
function doTempRecoveryExt() {
    $tbody = $("#add_tScIcJhstockbillentry1_table");
    var length1 = $tbody[0].rows.length;
    for (var i = 0; i < length1; i++) {
        var isKf = $("input[name='tScIcJhstockbillentry1List[" + i + "].isKFPeriod']").val();
        var isBatch = $("input[name='tScIcJhstockbillentry1List[" + i + "].batchManager']").val();
        var idSrc = $("input[name='tScIcJhstockbillentry1List[" + i + "].idSrc']").val();
        if ("N" == isKf || "" == isKf) {
            $("input[name='tScIcJhstockbillentry1List[" + i + "].kfDate']").val("");
            $("input[name='tScIcJhstockbillentry1List[" + i + "].kfDate']").removeAttr("onclick");
            $("input[name='tScIcJhstockbillentry1List[" + i + "].kfDate']").attr("readonly", "readonly");
            $("input[name='tScIcJhstockbillentry1List[" + i + "].kfDate']").css("background-color", "rgb(226,226,226)");
        }
        if ("N" == isBatch || "" == isBatch) {
            $("input[name='tScIcJhstockbillentry1List[" + i + "].batchNo']").val("");
            $("input[name='tScIcJhstockbillentry1List[" + i + "].batchNo']").attr("readonly", "readonly");
            $("input[name='tScIcJhstockbillentry1List[" + i + "].batchNo']").css("background-color", "rgb(226,226,226)");
        } else {
            if(!idSrc) {
                $("input[name='tScIcJhstockbillentry1List[" + i + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + i + "','batchNo')");
            } else {
                $("input[name='tScIcJhstockbillentry1List[" + i + "].batchNo']").attr("readonly", "readonly");
                $("input[name='tScIcJhstockbillentry1List[" + i + "].batchNo']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScIcJhstockbillentry1List[" + i + "].stockName']").attr("readonly", "readonly");
                $("input[name='tScIcJhstockbillentry1List[" + i + "].stockName']").css("background-color", "rgb(226,226,226)");
                $("#unitId1\\[" + i + "\\]").combobox("disable");
            }
        }
        var kfDateType = $("input[name='tScIcJhstockbillentry1List[" + i + "].kfDateType']").val();
        $("select[name='tScIcJhstockbillentry1List[" + i + "].kfDateType_']").val(kfDateType);


        //$("input[name='tScIcJhstockbillentry1List[" + i + "].classIDSrc']").val("");
        //$("input[name='tScIcJhstockbillentry1List[" + i + "].idSrc']").val("");
        //$("input[name='tScIcJhstockbillentry1List[" + i + "].entryIdSrc']").val("");
        //$("input[name='tScIcJhstockbillentry1List[" + i + "].idOrder']").val("");
        //$("input[name='tScIcJhstockbillentry1List[" + i + "].entryIdOrder']").val("");
        //$("input[name='tScIcJhstockbillentry1List[" + i + "].billNoSrc']").val("");
        //$("input[name='tScIcJhstockbillentry1List[" + i + "].billNoOrder']").val("");
    }

    $tbody = $("#add_tScIcJhstockbillentry2_table");
    var length2 = $tbody[0].rows.length;
    for (var i = 0; i < length2; i++) {
        var isKf = $("input[name='tScIcJhstockbillentry2List[" + i + "].isKFPeriod']").val();
        var isBatch = $("input[name='tScIcJhstockbillentry2List[" + i + "].batchManager']").val();
        if ("N" == isKf) {
            $("input[name='tScIcJhstockbillentry2List[" + i + "].kfDate']").val("");
            $("input[name='tScIcJhstockbillentry2List[" + i + "].kfDate']").removeAttr("onclick");
            $("input[name='tScIcJhstockbillentry2List[" + i + "].kfDate']").attr("readonly", "readonly");
            $("input[name='tScIcJhstockbillentry2List[" + i + "].kfDate']").css("background-color", "rgb(226,226,226)");
        }
        if ("N" == isBatch || "" == isBatch) {
            $("input[name='tScIcJhstockbillentry2List[" + i + "].batchNo']").val("");
            $("input[name='tScIcJhstockbillentry2List[" + i + "].batchNo']").attr("readonly", "readonly");
            $("input[name='tScIcJhstockbillentry2List[" + i + "].batchNo']").css("background-color", "rgb(226,226,226)");
        } else {
            $("input[name='tScIcJhstockbillentry2List[" + i + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + i + "','batchNo')");
        }
        var kfDateType = $("input[name='tScIcJhstockbillentry2List[" + i + "].kfDateType']").val();
        $("select[name='tScIcJhstockbillentry2List[" + i + "].kfDateType_']").val(kfDateType);
    }
}

//复制后操作
function clearCopyInfo() {
    $tbody = $("#add_tScIcJhstockbillentry1_table");
    var length1 = $tbody[0].rows.length;
    for (var i = 0; i < length1; i++) {
        $("input[name='tScIcJhstockbillentry1List[" + i + "].classIDSrc']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].idSrc']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].entryIdSrc']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].idOrder']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].entryIdOrder']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].classIDName']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].billNoSrc']").val("");
        $("input[name='tScIcJhstockbillentry1List[" + i + "].billNoOrder']").val("");
    }
}

function doClearExt() {
    setTimeout("clearCopyInfo()", 500);
}