var rowIndex = 0;
//初始化下标
function resetTrNum(tableId, index, isAdd) {

    $tbody = $("#" + tableId + "");
    $tbody.find('>tr').each(function (i) {
        var unitId = "";
        $(':input,span, select,button,a', this).each(function () {
            var $this = $(this), name = $this.attr('name'), id = $this.attr('id'), onclick_str = $this.attr('onclick'), str_press = $this.attr('onkeypress'), val = $this.val();
            var blur_str = $this.attr('onblur');
            var comboboName = $this.attr("comboname");
            var change_ = $this.attr("onchange");
            if (null != comboboName) {
                var s = comboboName.indexOf("[");
                var e = comboboName.indexOf("]");
                var new_name = comboboName.substring(s + 1, e);
                $this.attr("comboname", comboboName.replace(new_name, i));
            }
            if (name != null) {
                if (name.indexOf("#index#") >= 0) {
                    $this.attr("name", name.replace('#index#', i));
                } else {
                    var s = name.indexOf("[");
                    var e = name.indexOf("]");
                    var new_name = name.substring(s + 1, e);
                    $this.attr("name", name.replace(new_name, i));
                }
                if (name.indexOf("stockName") > -1) {
                    var stockId = $("input[name='tPoOrderentryList[" + i + "].stockId']").val();
                    var stockName = $("input[name='tPoOrderentryList[" + i + "].stockName']").val();
                    setValOldIdAnOldName($("input[name='tPoOrderentryList[" + i + "].stockName']"), stockId, stockName);
                } else if (name.indexOf("freeGifts_") > -1) {
                    $this.attr("onchange", "setPrice(" + i + ")");
                } else if (name.indexOf("inTaxAmount") > -1) {
                    $this.attr("onchange", "setAllAmount()");
                } else if (name.indexOf("kfDateType") > -1) {
                    $this.attr("onchange", "setPeriodDate(" + i + ")");
                }
            }
            if (id != null) {
                if (id.indexOf("#index#") >= 0) {
                    $this.attr("id", id.replace('#index#', i));
                } else {
                    var s = id.indexOf("[");
                    var e = id.indexOf("]");
                    var new_id = id.substring(s + 1, e);
                    $this.attr("id", id.replace(new_id, i));
                }
            }
            if (onclick_str != null) {
                if (onclick_str.indexOf("#index#") >= 0) {
                    $this.attr("onclick", onclick_str.replace(/#index#/g, i));
                } else {
                    if (name.indexOf("kfDate") < 0) {
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
                } else {
                    var s = blur_str.indexOf("(");
                    var e = blur_str.indexOf(",");
                    var new_blur = blur_str.substring(s + 1, e);
                    $this.attr("onblur", blur_str.replace(new_blur, i));
                }
            }
            if (null != change_) {
                if (change_.indexOf("#index#") >= 0) {
                    $this.attr("onchange", change_.replace(/#index#/g, i));
                } else {
                    if (name.indexOf("inTaxAmount") < 0) {
                        var s = change_.indexOf("(");
                        var e = change_.indexOf(")");
                        var new_change = change_.substring(s + 1, e);
                        $this.attr("onchange", change_.replace(new_change, i));
                    }
                }
            }
        });
        setFunctionInfo(i);
        checkAllowAddLine(i);
        $("input[name='tScPoStockbillentryList[" + i + "].findex']").val(i + 1);
        $(this).find('div[name=\'xh\']').html(i + 1);
    });

    $tbody.find('>tr').each(function (i) {
        if (i == (parseInt(index) + 1) && isAdd) {
            $("#unitId\\[" + i + "\\]").combobox({
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

    $("#stockName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectStockDialog();
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

    $("#stockName").blur(function () {
        checkInput($("#stockId"), $("#stockName"));
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
    var checkState = $("#checkState").val();
    var load = $("#load").val();
    if (checkState == 2 && load != "fcopy") {
        $("#date").attr("readonly", "readonly");
        $("#itemName").attr("readonly", "readonly");
        $("#empName").attr("readonly", "readonly");
        $("#deptName").attr("readonly", "readonly");
        $("#stockName").attr("readonly", "readonly");
        $("#contact").attr("readonly", "readonly");
        $("#mobilePhone").attr("readonly", "readonly");
        $("#phone").attr("readonly", "readonly");
        $("#address").attr("readonly", "readonly");
        $("#rebateAmount").attr("readonly", "readonly");
        $("#explanation").attr("readonly", "readonly");
    }
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
    var tranType = $("#tranType").val();
    if ("detail" != load) {
        //buttonDiv[0].appendChild(button);
        var select = document.createElement("select");
        select.id = "chooseType";
        select.setAttribute("onchange", "selectBillDialog()");
        select.setAttribute("style", "margin-left:10px;");
        var option1 = document.createElement("option");
        option1.value = "";
        option1.innerText = "选单";
        option1.select = "select";
        if ("52" == tranType) {
            var option2 = document.createElement("option");
            option2.value = "51";
            option2.innerText = "采购订单";
            //var option3 = document.createElement("option");
            //option3.value = "99";
            //option3.innerText = "收货通知单";
            //var option4 = document.createElement("option");
            //option4.value = "100";
            //option4.innerText = "商品借进单";
            select.appendChild(option1);
            select.appendChild(option2);
            //select.appendChild(option3);
            //select.appendChild(option4);
        } else if ("53" == tranType) {
            var option2 = document.createElement("option");
            option2.value = "52";
            option2.innerText = "采购入库单";
            select.appendChild(option1);
            select.appendChild(option2);
        }
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
        $("input[name='tScPoStockbillentryList[#index#].kfDate']").val(year + "-" + month + "-" + date);
    } else {
        var dateValue = $("input[name='date']").val();
        dateValue = new Date(dateValue);
        var year = dateValue.getFullYear();
        var month = (dateValue.getMonth() + 1) < 10 ? "0" + (dateValue.getMonth() + 1) : (dateValue.getMonth() + 1);
        var date = dateValue.getDate() < 10 ? "0" + dateValue.getDate() : dateValue.getDate();
        dateValue = year + "-" + month + "-" + date;
        $("input[name='tScPoStockbillentryList[#index#].kfDate']").val(dateValue);
    }
}

//赠品设置单价为0；
function setPrice(index) {
    var selected = document.getElementsByName("tScPoStockbillentryList[" + index + "].freeGifts_");
    var i = selected[0].selectedIndex;
    var value = selected[0].options[i].value;
    $("input[name='tScPoStockbillentryList[" + index + "].freeGifts']").val(value);
    if (value == 1) {
        $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").val(0);//单价
        $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").trigger("change");
        //单价只读
        $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").attr("readonly", "readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").css("background-color", "rgb(226,226,226)");
        //金额只读
        $("input[name='tScPoStockbillentryList[" + index + "].taxAmountEx']").attr("readonly", "readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].taxAmountEx']").css("background-color", "rgb(226,226,226)");
        //折扣率只读
        $("input[name='tScPoStockbillentryList[" + index + "].discountRate']").attr("readonly", "readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].discountRate']").css("background-color", "rgb(226,226,226)");
        //折后金额
        $("input[name='tScPoStockbillentryList[" + index + "].inTaxAmount']").attr("readonly", "readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].inTaxAmount']").css("background-color", "rgb(226,226,226)");
        //税率
        $("input[name='tScPoStockbillentryList[" + index + "].taxRate']").attr("readonly", "readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].taxRate']").css("background-color", "rgb(226,226,226)");
        //onPriceChange(index);
    } else {
        //单价只读
        $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").removeAttr("readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").css("background-color", "white");
        //金额只读
        $("input[name='tScPoStockbillentryList[" + index + "].taxAmountEx']").removeAttr("readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].taxAmountEx']").css("background-color", "white");
        //折扣率只读
        $("input[name='tScPoStockbillentryList[" + index + "].discountRate']").removeAttr("readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].discountRate']").css("background-color", "white");
        //折后金额
        $("input[name='tScPoStockbillentryList[" + index + "].inTaxAmount']").removeAttr("readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].inTaxAmount']").css("background-color", "white");
        //税率
        $("input[name='tScPoStockbillentryList[" + index + "].taxRate']").removeAttr("readonly");
        $("input[name='tScPoStockbillentryList[" + index + "].taxRate']").css("background-color", "white");
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
function selectStockDialog(index) {
    var itemName = $("#stockName").val();
    if (index || 0 == index) {
        itemName = $("input[name='tScPoStockbillentryList[" + index + "].stockName']").val();
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
                if (!index && index != 0) {
                    $("#stockName").val(item.name);
                    $("#stockId").val(item.id);
                    $tbody = $("#add_tScPoStockbillentry_table");
                    var length = $tbody[0].rows.length;
                    $("input[name='tScPoStockbillentryList[#index#].stockId']").val(item.id);
                    $("input[name='tScPoStockbillentryList[#index#].stockName']").val(item.name);
                    for (var i = 0; i < length; i++) {
                        $("input[name='tScPoStockbillentryList[" + i + "].stockId']").val(item.id);
                        $("input[name='tScPoStockbillentryList[" + i + "].stockName']").val(item.name);
                        setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + i + "].stockName']"), item.id, item.name);
                    }
                    setValOldIdAnOldName($("#stockName"), item.id, item.name);
                } else {
                    $("input[name='tScPoStockbillentryList[" + index + "].stockId']").val(item.id);
                    $("input[name='tScPoStockbillentryList[" + index + "].stockName']").val(item.name);
                    setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + index + "].stockName']"), item.id, item.name);
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
    var priceEx = $("input[name='tScPoStockbillentryList[" + index + "].taxPriceEx']").val();//单价
    if (priceEx > 0) {
        $("select[name='tScPoStockbillentryList[" + index + "].freeGifts_']").val(0);
        $("inout[name='tScPoStockbillentryList[" + index + "].freeGifts']").val(0);
    }
    var qty = $("input[name='tScPoStockbillentryList[" + index + "].qty']").val();//数量
    var discountRate = $("input[name='tScPoStockbillentryList[" + index + "].discountRate']").val();//折扣率
    var taxRate = $("input[name='tScPoStockbillentryList[" + index + "].taxRate']").val();//税率
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
    $("input[name='tScPoStockbillentryList[" + index + "].price']").val(price.toFixed(2));//不含税单价
    $("input[name='tScPoStockbillentryList[" + index + "].amount']").val(amount.toFixed(2));//不含税金额
    $("input[name='tScPoStockbillentryList[" + index + "].discountPrice']").val(discountPrice.toFixed(2));//不含税折后单价
    $("input[name='tScPoStockbillentryList[" + index + "].discountAmount']").val(discountAmount.toFixed(2));//不含税折后金额
    $("input[name='tScPoStockbillentryList[" + index + "].inTaxAmount']").val(disAmount.toFixed(2));//折后金额
    $("input[name='tScPoStockbillentryList[" + index + "].taxPrice']").val(disprice.toFixed(2));//折后单价
    $("input[name='tScPoStockbillentryList[" + index + "].taxAmount']").val(taxAmount.toFixed(2));//税额
    $("input[name='tScPoStockbillentryList[" + index + "].taxAmountEx']").val(taxAmountEx.toFixed(2));//金额
    setAllAmount();
}


function selectIcitemDialog(index) {
    var itemNo = $("input[name='tScPoStockbillentryList[" + index + "].itemNo']").val();
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
                var nextTrLength = $('input[name="tScPoStockbillentryList[' + index + '].findex"]').parent().nextAll().length;//判断后面的行数
                var newLength = itemLength - nextTrLength - 1
                for (var j = 0; j < newLength; j++) {
                    clickAddTScPoStockbillentryBtn(index);
                }
                var sonId = $("#sonId").val();
                $.each(itemList, function (i, item) {
                    var rowindex = parseInt(index) + i;
                    var id = item.id;//id
                    var number = item.number;//编码
                    var name = item.name;//名称
                    var model = item.model;//规格
                    var kfPeriod = item.kFPeriod;//保质期
                    var kfDateType = item.kFDateType;//保质期类型
                    var iSKFPeriod = item.iSKFPeriod;//是否按保质期管理
                    var batchManager = item.batchManager;//是否批号管理
                    var taxRate = item.taxRateIn;//进项税率
                    var stockSonId = item.stockSonId;//商品默认仓库分支机构id
                    var url = "tScIcitemController.do?getItemInfo&id=" + id + "&rowIndex=" + rowindex + "&tranType=51";
                    $.ajax({
                        url: url,
                        dataType: 'json',
                        cache: false,
                        success: function (data) {
                            var attr = data.attributes;
                            var cgLimitPrice = attr.cgLimitPrice;
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
                            var checkId = $("input[name='tScPoStockbillentryList[" + rowIndex + "].stockId']").val();
                            if (!checkId) {
                                if (sonId == stockSonId) {
                                    $("input[name='tScPoStockbillentryList[" + rowIndex + "].stockId']").val(stockId);
                                    $("input[name='tScPoStockbillentryList[" + rowIndex + "].stockName']").val(stockName);
                                    setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + rowIndex + "].stockName']"), stockId, stockName)
                                }
                            }
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].taxRate']").val(taxRate);//采购限价
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);//采购限价
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].itemNo']").val(number);
                            setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + rowIndex + "].itemNo']"), id, number);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].itemId']").val(id);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].model']").val(model);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].itemName']").val(name);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].coefficient']").trigger("blur");
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].secUnitId']").val(secUnitId);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].secCoefficient']").trigger("change");
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].basicUnitId']").val(basicUnitId);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].isKFPeriod']").val(iSKFPeriod);
                            $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchManager']").val(batchManager);
                            if ("Y" == iSKFPeriod) {
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfPeriod']").val(kfPeriod);
                                $("select[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType_']").val(kfDateType);
                                $("select[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType_']").attr("readonly", "readonly");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType']").val(kfDateType);
                                var kfDate = $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDate']").val();
                                var interval = "";
                                if (kfDateType == "0001") {
                                    interval = "y";
                                } else if (kfDateType == "0002") {
                                    interval = "m";
                                } else if (kfDateType == "0003") {
                                    interval = "d";
                                }
                                var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].periodDate']").val(periodDate);
                            } else {
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDate']").attr("readonly", "readonly");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDate']").attr("onClick", "");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDate']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfPeriod']").attr("readonly", "readonly");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfPeriod']").css("background-color", "rgb(226,226,226)");
                                $("select[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType_']").attr("readonly", "readonly");
                                $("select[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDate']").val("");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfPeriod']").val("");
                                $("select[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType_']").val("");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].kfDateType']").val("");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].periodDate']").val("");
                            }
                            if ("N" == batchManager) {
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").attr("readonly", "readonly");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").removeAttr("datatype");
                            } else {
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").removeAttr("readonly");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(255,255,255)");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").attr("datatype", "*");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").attr("onkeypress", "keyDownInfo('" + rowIndex + "','batchNo')");
                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+rowIndex+"')");
                            }
                            $("#unitId\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
                            $("#unitId\\[" + rowIndex + "\\]").combobox({
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
                                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);//采购限价
                                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
                                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
                                                $("input[name='tScPoStockbillentryList[" + rowIndex + "].coefficient']").trigger("change");
                                            }
                                        });
                                    }
                                }
                            })
                            $("#unitId\\[" + rowIndex + "\\]").combobox("setValue", unitId);
                            setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + rowIndex + "].itemNo']"), itemId, number);
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
    var oldValue = $("input[name='tScPoStockbillentryList[" + index + "].qty']").attr("oldvalue");
    var newValue = $("input[name='tScPoStockbillentryList[" + index + "].qty']").val();
    if (oldValue > newValue) {
        tip("数量不可小于当前数量");
        $("input[name='tScPoStockbillentryList[" + index + "].qty']").val(oldValue);
        return;
    }
    setAllQty(index);
}

//汇总折扣金额
function setAllAmount() {
    var CFG_MONEY = $("#CFG_MONEY").val();
    $tbody = $("#tScPoStockbillentry_table");
    var length = $tbody[0].rows.length;
    var allAmount = 0;
    var allAmountTax = 0;
    var allQty = 0;
    for (var i = 0; i < length; i++) {
        var amount = $("input[name='tScPoStockbillentryList[" + i + "].inTaxAmount']").val();//折后金额
        var amount2 = $("input[name='tScPoStockbillentryList[" + i + "].taxAmountEx']").val();//金额
        var qty = $("input[name='tScPoStockbillentryList[" + i + "].qty']").val();//数量
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
    var rebateAmount = $("#rebateAmount").val();
    if (!rebateAmount) {
        rebateAmount = 0;
    }
    $("#amount").val(allAmount.toFixed(CFG_MONEY));
    //if(allAmount > 0) {
    //$("#sumQty")[0].innerText = allQty;
    //$("#sumAmount")[0].innerText = allAmountTax;
    //$("#sumTaxAmount")[0].innerText=allAmount;
    allAmount = allAmount - rebateAmount;
    allAmount = allAmount.toFixed(CFG_MONEY);
    $("#allAmount").val(allAmount);
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
                $("#accountName").trigger("blur");
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
    var isCheckNegative = $("#isCheckNegative").val();
    var accountId = $("#accountId").val();
    if (accountId) {
        var curPayAmount = $("#curPayAmount").val();
        if (curPayAmount <= 0) {
            tip("本次付款不可小于0，请确认");
            return false;
        }
    }
    var $tbody = $("#tScPoStockbillentry_table");
    var length = $tbody[0].rows.length;
    var checkItemName = "";
    var checkValue = "";
    var tranType = $("#tranType").val();//单据类型 52：；入库单 53：退货单
    for (var checkIndex = 0; checkIndex < length; checkIndex++) {
        var price = $("input[name='tScPoStockbillentryList[" + checkIndex + "].taxPriceEx']").val();
        var itemName = $("input[name='tScPoStockbillentryList[" + checkIndex + "].itemName']").val();
        var cgLimitPrice = $("input[name='tScPoStockbillentryList[" + checkIndex + "].cgLimitPrice']").val();
        if(!cgLimitPrice){
            cgLimitPrice = 0;
        }
        var qty = $("input[name='tScPoStockbillentryList[" + checkIndex + "].basicQty']").val();
        var bigQty = $("input[name='tScPoStockbillentryList[" + checkIndex + "].qty']").val();
        var billQty = $("input[name='tScPoStockbillentryList[" + checkIndex + "].billQty']").val();
        var stockQty = $("input[name='tScPoStockbillentryList[" + checkIndex + "].stockQty']").val();
        var itemId = $("input[name='tScPoStockbillentryList[" + checkIndex + "].itemId']").val();
        var stockId = $("input[name='tScPoStockbillentryList[" + checkIndex + "].stockId']").val();
        var batchNo = $("input[name='tScPoStockbillentryList[" + checkIndex + "].batchNo']").val();
        //TODO 系统设置入库数量允超数量
        var allowPassQty = 0;
        var CFG_CANNOTINREPERTORYNYT_PURCHASEN = $("#CFG_CANNOTINREPERTORYNYT_PURCHASEN").val();
        if ("1" == CFG_CANNOTINREPERTORYNYT_PURCHASEN) {
            if (parseFloat(billQty) > 0 || stockQty) {
                if ("52" == tranType) {
                    if(stockQty) {
                        if ((parseFloat(bigQty) - (parseFloat(billQty) - parseFloat(stockQty))) > allowPassQty) {
                            var cs = parseFloat(bigQty) - (parseFloat(billQty) - parseFloat(stockQty)) - allowPassQty;
                            cs = parseFloat(cs).toFixed(CFG_NUMBER);
                            alertTip("商品【" + itemName + "】入库数量超出订单数量:" + cs + ",请确认");
                            return false;
                        }
                    } else {
                        if (parseFloat(bigQty) > parseFloat(billQty)) {
                            var cs = parseFloat(bigQty) - parseFloat(billQty);
                            cs = parseFloat(cs).toFixed(CFG_NUMBER);
                            alertTip("商品【" + itemName + "】入库数量超出订单数量:" + cs.toFixed(2) + ",请确认");
                            return false;
                        }
                    }
                } else if ("53" == tranType) {
                    if (parseFloat(bigQty) > parseFloat(billQty)) {
                        var cs = parseFloat(bigQty) - parseFloat(billQty);
                        cs = parseFloat(cs).toFixed(CFG_NUMBER);
                        alertTip("商品【" + itemName + "】退货数量超出订单数量:" + cs + ",请确认");
                        return false;
                    }
                }
            }
        }
        if (isCheckNegative) {
            checkValue += itemId + "#" + itemName + "#" + stockId + "#" + qty + "#" + batchNo + ",";
        }
        if (cgLimitPrice && parseFloat(cgLimitPrice) > 0){
            if (parseFloat(price) > parseFloat(cgLimitPrice)) {
                checkItemName += itemName + ",";
            }
        }

    }
    if (isCheck || isCheck2) {
        if (checkItemName.length > 0 && isCheck) {
            parent.$.dialog.confirm("商品[" + itemName + "]的单价高于采购限价，是否继续保存？", function () {
                    isCheck = false;
                    $("#formobj").submit();
                }
                , function () {
                    //
                })
            return false;
        } else {
            if (tranType == "53") {
                //销售出库单时，校验数量是否超出库存
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
                            $.dialog.confirm("商品[" + data.msg + "]的数量超出库存数量，是否继续保存？", function () {
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
            } else {
                return true;
            }
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
    //					var price = $("input[name='tScPoStockbillentryList["+i+"].taxPriceEx']").val();
    //					if(!price || price == 0){
    //						itemName += $("input[name='tScPoStockbillentryList["+i+"].itemName']").val()+",";
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
function setPeriodDate(index) {
    var kfDate = $("input[name='tScPoStockbillentryList[" + index + "].kfDate']").val();//生产日期
    var kfPeriod = $("input[name='tScPoStockbillentryList[" + index + "].kfPeriod']").val();//保质期
    var kfDateType = $("select[name='tScPoStockbillentryList[" + index + "].kfDateType_']").val();//保质期类型
    var interval = "";
    if (kfDateType == "0001") {
        interval = "y";
    } else if (kfDateType == "0002") {
        interval = "m";
    } else if (kfDateType == "0003") {
        interval = "d";
    }
    var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
    $("input[name='tScPoStockbillentryList[" + index + "].periodDate']").val(periodDate);
}

//选单
var CFG_NUMBER = $("#CFG_NUMBER").val();
var CFG_MONEY = $("#CFG_MONEY").val();
function selectBillDialog() {
    var itemId = $("#itemId").val();
    if (!itemId) {
        tip("请选择供应商后再进行选单操作");
        $("#chooseType").val("");
        return;
    }
    var tranType = $("#chooseType").val();
    var sonId = $("#sonId").val();
    var title = "采购订单";
    var url = "";
    if ("51" == tranType) {
        url = "tPoOrderController.do?goSelectDialog&itemId=" + itemId + "&tranType=" + tranType +"&sonId="+sonId;
        url = encodeURI(url);
    } else if ("52" == tranType) {
        title = "采购入库单"
        url = "tScPoStockbillController.do?goSelectDialog&itemId=" + itemId + "&tranType=" + tranType +"&sonId="+sonId;
    } else if ("99" == tranType) {
        tip(tranType);
        return;
    } else if ("100" == tranType) {
        tip(tranType);
        return;
    } else {
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
                var itemList = iframe.getSelectionData();
                var ids = "";
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
                $("#stockId").val(item.stockId);
                $("#stockName").val(item.stockName);
                setValOldIdAnOldName($("#stockName"), item.stockId, item.stockName);
                $("#contact").val(item.contact);
                $("#mobilePhone").val(item.mobilePhone);
                $("#phone").val(item.phone);
                $("#fax").val(item.fax);
                $("#address").val(item.address);
                var length = $("#add_tScPoStockbillentry_table").find(">tr").length;
                length--;
                var id = $("input[name='tScPoStockbillentryList[" + length + "].itemId']").val();
                var dataLength = itemList.length;
                if (!id) {
                    dataLength--;
                }
                for (var k = 0; k < dataLength; k++) {
                    clickAddTScPoStockbillentryBtn(length)
                }
                if (id) {
                    length++;
                }
                for (var i = 0; i < itemList.length; i++) {
                    var row = itemList[i];
                    for (var k in row) {
                        if (k == "unitId") {
                            $("#unitId\\[" + length + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + row.entryItemId);
                            $("#unitId\\[" + length + "\\]").combobox("setValue", row[k]);
                            $("#unitId\\[" + length + "\\]").combobox("disable");
                        } else if (k == "freeGifts" || k == "kfDateType" || k == "kfDateTypeInfo") {
                            if ("N" == row.isKFPeriod && (k == "kfDateType" || k == "kfDateTypeInfo")) {
                                $("select[name='tScPoStockbillentryList[" + length + "].kfDateType_']").attr("readonly", "readonly");
                                $("select[name='tScPoStockbillentryList[" + length + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
                            } else if (k == "freeGifts") {
                                $("select[name='tScPoStockbillentryList[" + length + "].freeGifts_']").val(row[k]);
                                $("select[name='tScPoStockbillentryList[" + length + "].freeGifts_']").attr("readonly", "readonly");
                                $("select[name='tScPoStockbillentryList[" + length + "].freeGifts_']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScPoStockbillentryList[" + length + "].freeGifts']").val(row[k]);
                                if("1" == row[k]){
                                    $("input[name='tScPoStockbillentryList[" + length + "].taxPriceEx']").attr("readonly","readonly");
                                    $("input[name='tScPoStockbillentryList[" + length + "].taxPriceEx']").css("background-color","rgb(226,226,226)");
                                    $("input[name='tScPoStockbillentryList[" + length + "].taxAmountEx']").attr("readonly","readonly");
                                    $("input[name='tScPoStockbillentryList[" + length + "].taxAmountEx']").css("background-color","rgb(226,226,226)");
                                    $("input[name='tScPoStockbillentryList[" + length + "].discountRate']").attr("readonly","readonly");
                                    $("input[name='tScPoStockbillentryList[" + length + "].discountRate']").css("background-color","rgb(226,226,226)");
                                    $("input[name='tScPoStockbillentryList[" + length + "].inTaxAmount']").attr("readonly","readonly");
                                    $("input[name='tScPoStockbillentryList[" + length + "].inTaxAmount']").css("background-color","rgb(226,226,226)");
                                    $("input[name='tScPoStockbillentryList[" + length + "].taxRate']").attr("readonly","readonly");
                                    $("input[name='tScPoStockbillentryList[" + length + "].taxRate']").css("background-color","rgb(226,226,226)");
                                }
                            } else {
                                $("select[name='tScPoStockbillentryList[" + length + "].kfDateType_']").attr("readonly", "readonly");
                                $("select[name='tScPoStockbillentryList[" + length + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
                                $("select[name='tScPoStockbillentryList[" + length + "].kfDateType_']").val(row[k]);
                                $("input[name='tScPoStockbillentryList[" + length + "].kfDateType']").val(row[k]);
                            }
                        } else if (k == "kfPeriod") {
                            if ("N" == row.isKFPeriod) {
                                $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").attr("readonly", "readonly");
                                $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").css("background-color", "rgb(226,226,226)");
                            } else if ("Y" == row.isKFPeriod){
                                $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").attr("readonly", "readonly");
                                $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").val(parseFloat(row[k]));
                            }
                        } else if (k == "qty") {
                            var stockQty;
                            if ("51" == tranType) {
                                stockQty = row.stockQty;//已执行数量
                            } else if ("52" == tranType) {
                                stockQty = row.commitQty;//退货数量
                            }
                            var qty = row[k];
                            var basicQty = row["qty"];
                            var coe = row["coefficient"];
                            qty = ((parseFloat(basicQty) - parseFloat(stockQty))).toFixed(CFG_NUMBER);
                            $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").val(qty);
                            $("input[name='tScPoStockbillentryList[" + length + "].billQty']").val(row[k]);
                        }
                        else {
                            $("input[name='tScPoStockbillentryList[" + length + "]." + k + "']").val(row[k]);
                        }
                        if (k == "entryItemNo") {
                            $("input[name='tScPoStockbillentryList[" + length + "].itemNo']").attr("readonly", "readonly");
                        }
                        if (k == "kfDate" && "Y" == row.isKFPeriod) {
                            var kfDate = row[k];
                            kfDate = dateFormatter(kfDate);
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").val(kfDate);
                        }
                    }
                    if ("Y" == row.isKFPeriod) {
                        setPeriodDate(length);
                        if("52" == tranType) {
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").attr("readonly", "readonly");
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").css("background-color", "rgb(226,226,226)");
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").attr("onClick", "");
                        }
                    } else {
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").attr("readonly", "readonly");
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").css("background-color", "rgb(226,226,226)");
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").attr("onClick", "");
                            $("input[name='tScPoStockbillentryList[" + length + "].kfDate']").val("");
                    }
                    if ("Y" == row.batchManager) {
                        if ("52" == tranType) {
                            $("input[name='tScPoStockbillentryList[" + length + "].batchNo']").attr("readonly", "readonly");
                            $("input[name='tScPoStockbillentryList[" + length + "].batchNo']").css("background-color", "rgb(226,226,226)");
                            $("input[name='tScPoStockbillentryList[" + length + "].batchNo']").removeAttr("datatype");
                            //$("input[name='tScPoStockbillentryList[" + length + "].stockName']").attr("readonly","readonly");
                            //$("input[name='tScPoStockbillentryList[" + length + "].stockName']").css("background-color","rgb(226,226,226)");
                        }
                    } else {
                        $("input[name='tScPoStockbillentryList[" + length + "].batchNo']").attr("readonly", "readonly");
                        $("input[name='tScPoStockbillentryList[" + length + "].batchNo']").css("background-color", "rgb(226,226,226)");
                        $("input[name='tScPoStockbillentryList[" + length + "].batchNo']").removeAttr("datatype");
                    }
                    $("input[name='tScPoStockbillentryList[" + length + "].itemId']").val(row.entryItemId);
                    $("input[name='tScPoStockbillentryList[" + length + "].itemNo']").val(row.entryItemNo);
                    $("input[name='tScPoStockbillentryList[" + length + "].itemName']").val(row.entryItemName);

                    $("input[name='tScPoStockbillentryList[" + length + "].stockId']").val(row.entryStockId);
                    $("input[name='tScPoStockbillentryList[" + length + "].stockName']").val(row.entryStockName);
                    setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + length + "].stockName']"), row.entryStockId, row.entryStockName);

                    var basicCoefficient = $("input[name='tScPoStockbillentryList[" + length + "].basicCoefficient']").val();
                    var discountPrice = $("input[name='tScPoStockbillentryList[" + length + "].discountPrice']").val();
                    var costPrice = parseFloat(discountPrice) / parseFloat(basicCoefficient)
                    var qty = $("input[name='tScPoStockbillentryList[" + length + "].qty']").val();
                    $("input[name='tScPoStockbillentryList[" + length + "].costPrice']").val(costPrice.toFixed(2));
                    $("input[name='tScPoStockbillentryList[" + length + "].costAmount']").val((costPrice.toFixed(2) * qty).toFixed(2))
                    if ("51" == tranType) {
                        $("input[name='tScPoStockbillentryList[" + length + "].idSrc']").val(row.id);
                        $("input[name='tScPoStockbillentryList[" + length + "].billNoSrc']").val(row.billNo);
                        $("input[name='tScPoStockbillentryList[" + length + "].classIDSrc']").val(row.tranType);
                        $("input[name='tScPoStockbillentryList[" + length + "].entryIdSrc']").val(row.entryId);
                        $("input[name='tScPoStockbillentryList[" + length + "].billNoOrder']").val(row.billNo);
                        $("input[name='tScPoStockbillentryList[" + length + "].idOrder']").val(row.id);
                        $("input[name='tScPoStockbillentryList[" + length + "].entryIdOrder']").val(row.entryId);
                    } else if ("52" == tranType) {
                        $("input[name='tScPoStockbillentryList[" + length + "].idSrc']").val(row.id);
                        $("input[name='tScPoStockbillentryList[" + length + "].billNoSrc']").val(row.billNo);
                        $("input[name='tScPoStockbillentryList[" + length + "].classIDSrc']").val(row.tranType);
                        $("input[name='tScPoStockbillentryList[" + length + "].entryIdSrc']").val(row.entryId);
                        $("input[name='tScPoStockbillentryList[" + length + "].billNoOrder']").val(row.billNoOrder);
                        $("input[name='tScPoStockbillentryList[" + length + "].idOrder']").val(row.idOrder);
                        $("input[name='tScPoStockbillentryList[" + length + "].entryIdOrder']").val(row.entryIdOrder);
                    }
                    setFunctionInfo(length);
                    $("input[name='tScPoStockbillentryList[" + length + "].qty']").trigger("change");
                    $("input[name='tScPoStockbillentryList[" + length + "].taxPriceEx']").trigger("change");
                    $("input[name='tScPoStockbillentryList[" + length + "].taxAmountEx']").trigger("change");

                    var itemNo = $("input[name='tScPoStockbillentryList[" + length + "].itemNo']").val();
                    var itemId = $("input[name='tScPoStockbillentryList[" + length + "].itemId']").val();
                    setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + length + "].itemNo']"), itemId, itemNo);

                    var stockName = $("input[name='tScPoStockbillentryList[" + length + "].stockName']").val();
                    var stockId = $("input[name='tScPoStockbillentryList[" + length + "].stockId']").val();
                    if (stockId) {
                        setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + length + "].stockName']"), stockId, stockName);
                    }
                    var tranTypeName = row.tranTypeName;
                    $("input[name='tScPoStockbillentryList[" + length + "].classIDName']").val(tranTypeName);
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
                //重置选单类型
                $("#chooseType").val("");
            }
        }]
    }).zindex();
    setDefault();
}

function setDefault() {
    $("#chooseType").val("");
}
//更改优惠金额时执行操作
function changeAllAmount() {
    var rebateAmount = $("#rebateAmount").val();
    if (!rebateAmount) {
        rebateAmount = 0;
    }
    var allAmount = $("#amount").val();
    if (allAmount) {
        allAmount = parseFloat(allAmount) - parseFloat(rebateAmount);
        allAmount = parseFloat(allAmount).toFixed(CFG_MONEY);
        $("#allAmount").val(allAmount);
    }
}

//表格校验事件
function orderListStockBlur(rowIndex, id, name) {
    checkInput($('input[name="tScPoStockbillentryList[' + rowIndex + '].' + id + '"]'), $('input[name="tScPoStockbillentryList[' + rowIndex + '].' + name + '"]'));
}

//判断是否允许增行操作
function checkAllowAddLine(index) {
    var isAddLine = true;
    if (!isAddLine) {
        $("#addTScOrderentryBtn\\[" + index + "\\]").css("display", "none");
        $("input[name='tScPoStockbillentryList[" + index + "].itemNo']").attr("readonly", "readonly");
    }
}

//日期格式化
function dateFormatter(dateV) {
    if(dateV) {
        //var dateStr = dateV.replace(/-/g, "/");
        //var date = new Date(dateStr);
        //console.log(date);
        //if (date) {
        //    var year = date.getFullYear();
        //    var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
        //    var date = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        //    return year + "-" + month + "-" + date;
        //} else {
            return dateV.substring(0, 10);
        //}
    }
}

//审核前校验
function checkAudit() {
    var isCheckNegative = $("#isCheckNegative").val();
    if (isCheckNegative == "true") {
        var tranType = $("#tranType").val();
        if ("53" == tranType) {
            var isContinue = true;
            var itemName = "";
            var billId = $("#id").val();
            $.ajax({
                async: false,
                url: "tSAuditController.do?checkIsNegative&id=" + billId + "&tranType=" + tranType + "&tableName=T_SC_Po_StockBillEntry&parentId=fid",
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
    } else {
        return false;
    }
}

//反审核前校验
function checkUnAudit() {
    var isCheckNegative = $("#isCheckNegative").val();
    if (isCheckNegative == "true") {
        var tranType = $("#tranType").val();
        if ("52" == tranType) {
            var isContinue = true;
            var itemName = "";
            var billId = $("#id").val();
            $.ajax({
                async: false,
                url: "tSAuditController.do?checkIsNegative&id=" + billId + "&tranType=" + tranType + "&tableName=T_SC_Po_StockBillEntry&parentId=fid",
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
        }
    } else {
        return false;
    }
}

//审核后执行
function afterAudit() {
    var tranType = $("#tranType").val();
    var id = $("#id").val();
    var url = "tScPoStockbillController.do?afterAudit&audit=1&id=" + id + "&tranType=" + tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
            if(!d.success) {
                tip(d.msg);
            }
        }
    });
}

//反审核后执行
function afterUnAudit(billId) {
    var tranType = $("#tranType").val();
    var url = "tScPoStockbillController.do?afterAudit&audit=0&id=" + billId + "&tranType=" + tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
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
function selectInventoryInfo(index) {
    var itemId = $("input[name='tScPoStockbillentryList[" + index + "].itemId']").val();//商品id
    var stockId = $("input[name='tScPoStockbillentryList[" + index + "].stockId']").val();//仓库id
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
                    $("input[name='tScPoStockbillentryList[" + index + "].kfDate']").val(kfDate);
                    $("input[name='tScPoStockbillentryList[" + index + "].kfDate']").trigger("change");
                }
                $("input[name='tScPoStockbillentryList[" + index + "].batchNo']").val(item.batchNo);
                $("input[name='tScPoStockbillentryList[" + index + "].stockId']").val(item.stockId);
                $("input[name='tScPoStockbillentryList[" + index + "].stockName']").val(item.stockName);
                setValOldIdAnOldName($("input[name='tScPoStockbillentryList[" + index + "].stockName']"), item.stockId, item.stockName);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}


//复制后执行
function doClearExt(){
    setTimeout("clearSourceInfo();",500);
}

//清楚源单数据
function clearSourceInfo(){
    $tbody = $("#add_tScPoStockbillentry_table");
    var length1 = $tbody[0].rows.length;
    for (var i = 0; i < length1; i++) {
        $("input[name='tScPoStockbillentryList[" + i + "].classIDSrc']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].idSrc']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].entryIdSrc']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].idOrder']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].entryIdOrder']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].classIDName']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].billNoSrc']").val("");
        $("input[name='tScPoStockbillentryList[" + i + "].billNoOrder']").val("");
        var batchManager = $("input[name='tScPoStockbillentryList[" + i + "].batchManager']").val();
        if("Y" == batchManager){
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").removeAttr("readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").css("background","white");
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").attr("onkeypress", "keyDownInfo('" + i + "','batchNo')");
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").attr("ondblclick","selectInventoryInfo('"+i+"')");
        }
    }
}


//草稿恢复后操作事项
function doTempRecoveryExt() {
    checkCopyInfo();
}


function checkCopyInfo(){
    $tbody = $("#add_tScPoStockbillentry_table");
    var length1 = $tbody[0].rows.length;
    for (var i = 0; i < length1; i++) {
        var isKf = $("input[name='tScPoStockbillentryList[" + i + "].isKFPeriod']").val();
        var isBatch = $("input[name='tScPoStockbillentryList[" + i + "].batchManager']").val();
        if ("N" == isKf || "" == isKf) {
            $("input[name='tScPoStockbillentryList[" + i + "].kfDate']").val("");
            $("input[name='tScPoStockbillentryList[" + i + "].kfDate']").removeAttr("onclick");
            $("input[name='tScPoStockbillentryList[" + i + "].kfDate']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].kfDate']").css("background-color", "rgb(226,226,226)");
        }
        if ("N" == isBatch || "" == isBatch) {
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").val("");
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").css("background-color", "rgb(226,226,226)");
        } else {
            $("input[name='tScPoStockbillentryList[" + i + "].batchNo']").attr("onkeypress", "keyDownInfoI('" + i + "','batchNo')");
        }
        var kfDateType = $("input[name='tScPoStockbillentryList[" + i + "].kfDateType']").val();
        $("select[name='tScPoStockbillentryList[" + i + "].kfDateType_']").val(kfDateType);
        var isFreeGift = $("select[name='tScPoStockbillentryList[" + i + "].freeGifts_']").val();
        if(isFreeGift == "1"){
            $("input[name='tScPoStockbillentryList[" + i + "].taxPriceEx']").val(0);//单价
            $("input[name='tScPoStockbillentryList[" + i + "].taxPriceEx']").trigger("change");
            //单价只读
            $("input[name='tScPoStockbillentryList[" + i + "].taxPriceEx']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].taxPriceEx']").css("background-color", "rgb(226,226,226)");
            //金额只读
            $("input[name='tScPoStockbillentryList[" + i + "].taxAmountEx']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].taxAmountEx']").css("background-color", "rgb(226,226,226)");
            //折扣率只读
            $("input[name='tScPoStockbillentryList[" + i + "].discountRate']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].discountRate']").css("background-color", "rgb(226,226,226)");
            //折后金额
            $("input[name='tScPoStockbillentryList[" + i + "].inTaxAmount']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].inTaxAmount']").css("background-color", "rgb(226,226,226)");
            //税率
            $("input[name='tScPoStockbillentryList[" + i + "].taxRate']").attr("readonly", "readonly");
            $("input[name='tScPoStockbillentryList[" + i + "].taxRate']").css("background-color", "rgb(226,226,226)");
        }

        //$("input[name='tScPoStockbillentryList[" + i + "].classIDSrc']").val("");
        //$("input[name='tScPoStockbillentryList[" + i + "].idSrc']").val("");
        //$("input[name='tScPoStockbillentryList[" + i + "].entryIdSrc']").val("");
        //$("input[name='tScPoStockbillentryList[" + i + "].idOrder']").val("");
        //$("input[name='tScPoStockbillentryList[" + i + "].entryIdOrder']").val("");
        //$("input[name='tScPoStockbillentryList[" + i + "].billNoSrc']").val("");
        //$("input[name='tScPoStockbillentryList[" + i + "].billNoOrder']").val("");
    }
}