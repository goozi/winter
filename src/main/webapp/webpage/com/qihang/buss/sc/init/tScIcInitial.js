var rowIndex = 0;
//初始化下标
function resetTrNum(tableId, index) {
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
                    var stockId = $("input[name='tScIcInitialentryList[" + i + "].stockId']").val();
                    var stockName = $("input[name='tScIcInitialentryList[" + i + "].stockName']").val();
                    setValOldIdAnOldName($("input[name='tScIcInitialentryList[" + i + "].stockName']"), stockId, stockName);
                    //}else if(name.indexOf("freeGifts_") > -1){
                    //	$this.attr("onchange","setPrice("+i+")");
                    //}else if(name.indexOf("inTaxAmount") > -1){
                    //	$this.attr("onchange","setAllAmount()");
                } else if (name.indexOf(".qty") > -1) {
                    $this.attr("onchange", "onPriceChange(" + i + ")");
                } else if (name.indexOf(".smallQty") > -1) {
                    $this.attr("onchange", "onPriceChange(" + i + ")");
                } else if (name.indexOf(".costPrice") > -1) {
                    $this.attr("onchange", "onPriceChange(" + i + ")");
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
                    var s = change_.indexOf("(");
                    var e = change_.indexOf(")");
                    var new_change = change_.substring(s + 1, e);
                    $this.attr("onchange", change_.replace(new_change, i));
                }
            }
        });
        setFunctionInfo(i);
        checkAllowAddLine(i);
        $("input[name='tScIcInitialentryList[" + i + "].indexnumber']").val(i + 1);
        $(this).find('div[name=\'xh\']').html(i + 1);
    });

    $tbody.find('>tr').each(function (i) {
        if (i == (parseInt(index) + 1)) {
            $("#unitId\\[" + i + "\\]").combobox({
                //$("#unitId\\[" + i + "\\]").combobox({
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
    finder.selectActionData = inputId; //接收地址的input Id
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
    finder.selectActionData = inputId; //接收地址的input Id
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

    /*$("#itemName").keypress(function (e) {
     if (e.keyCode == 13) {
     selectItemNameDialog();
     }
     });*/

    $("#stockName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectStockDialog();
        }
    });


    //$("#itemName").blur(function(){
    //	checkInput($("#itemId"), $("#itemName"));
    //});

    $("#stockName").blur(function () {
        checkInput($("#stockId"), $("#stockName"));
    });
    var checkState = $("#checkState").val();
    if (checkState == 2) {
        $("#date").attr("disabled", "disabled");
        //$("#itemName").attr("readonly","readonly");
        $("#stockName").attr("readonly", "readonly");
        $("#explanation").attr("readonly", "readonly");
    }
    //单据复制时，重置或清空部分初始数据，是否应该移到
    var url = location.href;
    var urliffcopy = url.indexOf("load=fcopy");
    if (urliffcopy >= 0) {
        billcopy();
    }
})

//设置旧数据
function setDefaultOldInfo() {
    var stockId = $("#stockId").val();
    var stockName = $("#stockName").val();
    setValOldIdAnOldName($("#stockName"), stockId, stockName);

}
//设置单据日期默认为当前日期
function setDefaultDate() {
    var date = $("#date").val();
    date = date.replace(/-/g, "/");
    var dateArray = date.split(" ");
    if (dateArray.length > 1) {//去掉日期后面的时间
        date = dateArray[0];
    }
    var date = new Date(date);
    var year = date.getFullYear();
    var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
    var date = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    //var date = $("#date").val();
    $("#date").val(year + "-" + month + "-" + date);
    $("#date").attr("readonly", "readonly");
    $("#date").attr("onClick", "");
    var id = $("#id").val();
    if (!id) {
        $("input[name='date']").val(year + "-" + month + "-" + date);
        $("input[name='tScIcInitialentryList[#index#].kfDate']").val(year + "-" + month + "-" + date);
    } else {
        //var dateValue = $("input[name='date']").val();
        //dateValue = new Date(dateValue);
        var dateValue = new Date(date);
        var year = dateValue.getFullYear();
        var month = (dateValue.getMonth() + 1) < 10 ? "0" + (dateValue.getMonth() + 1) : (dateValue.getMonth() + 1);
        var date = dateValue.getDate() < 10 ? "0" + dateValue.getDate() : dateValue.getDate();
        dateValue = year + "-" + month + "-" + date;
        $("input[name='tScIcInitialentryList[#index#].kfDate']").val(dateValue);
    }
}
//赠品设置单价为0；
function setPrice(index) {
    onPriceChange(index);//改变时计算价格
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
/**
 * 存放旧值
 * 弹出框回传设置值
 */
function setValOldIdAnOldName(inputid, oldId, oldName) {
    inputid.attr("oldid", oldId);
    inputid.attr("oldname", oldName);
}
//选择仓库
function selectStockDialog(index) {
    var itemName = $("#stockName").val();
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
                    $tbody = $("#add_tScIcInitialentry_table");
                    var length = $tbody[0].rows.length;
                    $("input[name='tScIcInitialentryList[#index#].stockId']").val(item.id);
                    //$("input[name='tScIcInitialentryList[#index#].stockName']").val(item.name);
                    for (var i = 0; i < length; i++) {
                        $("input[name='tScIcInitialentryList[" + i + "].stockId']").val(item.id);
                        //$("input[name='tScIcInitialentryList[" + i + "].stockName']").val(item.name);
                        //setValOldIdAnOldName($("input[name='tScIcInitialentryList[" + i + "].stockName']"), item.id, item.name);
                    }
                    setValOldIdAnOldName($("#stockName"), item.id, item.name);
                } else {
                    $("input[name='tScIcInitialentryList[" + index + "].stockId']").val(item.id);
                    //$("input[name='tScIcInitialentryList[" + index + "].stockName']").val(item.name);
                    //setValOldIdAnOldName($("input[name='tScIcInitialentryList[" + index + "].stockName']"), item.id, item.name);
                }
                //$("input[name='stockId']").val(item.id);
                //$("input[name='stockName']").val(item.name);
                //setValOldIdAnOldName($("#stockName"), item.id, item.name);
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
    amountCfg($("input[name='tScIcInitialentryList[" + index + "].costPrice']"));
    numberCfg($("input[name='tScIcInitialentryList[" + index + "].qty']"));
    numberCfg($("input[name='tScIcInitialentryList[" + index + "].smallQty']"));
    numberCfg($("input[name='tScIcInitialentryList[" + index + "].basicQty']"));

    var costPrice = $("input[name='tScIcInitialentryList[" + index + "].costPrice']").val();//单价
    var qty = $("input[name='tScIcInitialentryList[" + index + "].qty']").val();//箱数
    var smallQty = $("input[name='tScIcInitialentryList[" + index + "].smallQty']").val();//散数
    var basicQty = $("input[name='tScIcInitialentryList[" + index + "].basicQty']").val();//数量
    var secCoefficient = $("input[name='tScIcInitialentryList[" + index + "].secCoefficient']").val();//辅助换算率
    var coefficient = $("input[name='tScIcInitialentryList[" + index + "].coefficient']").val();//换算率
    if (!costPrice) {
        costPrice = 0;
    }
    if (!qty) {
        qty = 0;
    }
    if (!smallQty) {
        smallQty = 0;
    }
    if (!basicQty) {
        basicQty = 0;
    }
    if (!secCoefficient) {
        secCoefficient = 0;
    }
    if (!coefficient) {
        coefficient = 0;
    }
    //debugger;
    basicQty = parseFloat(qty) * parseFloat(coefficient) + parseFloat(smallQty);//数量=箱数*换算率+散数
    var secQty = 0;
    if (secCoefficient != 0) {
        secQty = basicQty * parseFloat(coefficient) / parseFloat(secCoefficient);
    }//辅助数量=数量*换算率/辅助换算率
    var costAmount = parseFloat(basicQty) * parseFloat(costPrice);//成本金额=数量*成本单价
    $("input[name='tScIcInitialentryList[" + index + "].costAmount']").val(costAmount.toFixed(2));//不含税金额
    $("input[name='tScIcInitialentryList[" + index + "].basicQty']").val(basicQty.toFixed(2));//数量
    $("input[name='tScIcInitialentryList[" + index + "].secQty']").val(secQty.toFixed(2));//辅助数量
    $("input[name='tScIcInitialentryList[" + index + "].costAmount']").trigger('change');//数量乘以单价算金额等
    $("input[name='tScIcInitialentryList[" + index + "].basicQty']").trigger('change');//数量乘以单价算金额等
    //setAllAmount();
}

//选择商品
function selectIcitemDialog(index) {
    var itemNo = $("input[name='tScIcInitialentryList[" + index + "].itemNo']").val();
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
                var itemLength = itemList.length;
                var nextTrLength = $('input[name="tScIcInitialentryList[' + index + '].indexnumber"]').parent().nextAll().length;//判断后面的行数
                var newLength = itemLength - nextTrLength - 1;
                //先加不够的空行
                for (var j = 0; j < newLength; j++) {
                    clickAddTScIcInitialentryBtn(index);
                }
                //给各行赋值
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
                    var url = "tScIcitemController.do?getItemInfo&id=" + id + "&rowIndex=" + rowindex + "&tranType=52";
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
                            //$("input[name='tScIcInitialentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);//采购限价
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].itemNo']").val(number);
                            setValOldIdAnOldName($("input[name='tScIcInitialentryList[" + rowIndex + "].itemNo']"), id, number);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].itemId']").val(id);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].model']").val(model);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].itemName']").val(name);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].barCode']").val(barCode);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].coefficient']").val(cofficient);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].coefficient']").trigger("blur");
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].secUnitId']").val(secUnitId);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].secCoefficient']").val(secCoefficient);
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].secCoefficient']").trigger("change");
                            $("input[name='tScIcInitialentryList[" + rowIndex + "].basicUnitId']").val(basicUnitId);
                            if ("Y" == iSKFPeriod) {
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfPeriod']").val(kfPeriod);
                                //$("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType']").val(kfDateType);
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDateType']").val(kfDateType);
                                var kfDate = $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDate']").val();
                                var interval = "";
                                if (kfDateType == "0001") {
                                    interval = "y";
                                } else if (kfDateType == "0002") {
                                    interval = "m";
                                } else if (kfDateType == "0003") {
                                    interval = "d";
                                }
                                var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].periodDate']").val(periodDate);

                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfPeriod']").attr("readonly", "readonly");//保质期和保质期类型为只读
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfPeriod']").css("background-color", "rgb(226,226,226)");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType_']").val(kfDateType);
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType_']").attr("disabled", "disabled");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
                            } else {
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDate']").attr("readonly", "readonly");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDate']").attr("onClick", "");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDate']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfPeriod']").attr("readonly", "readonly");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfPeriod']").css("background-color", "rgb(226,226,226)");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType']").attr("disabled", "disabled");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType']").css("background-color", "rgb(226,226,226)");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType_']").val("");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType_']").attr("disabled", "disabled");
                                $("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDate']").val("");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfPeriod']").val("");
                                //$("select[name='tScIcInitialentryList[" + rowIndex + "].kfDateType']").val("");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].kfDateType']").val("");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].periodDate']").val("");
                            }
                            if ("N" == batchManager) {
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].batchNo']").attr("readonly", "readonly");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(226,226,226)");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].batchNo']").removeAttr("datatype");
                            } else {
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].batchNo']").removeAttr("readonly");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].batchNo']").css("background-color", "rgb(255,255,255)");
                                $("input[name='tScIcInitialentryList[" + rowIndex + "].batchNo']").attr("datatype", "*");
                            }
                            //debugger;
                            $("#unitId\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId + "&timestamp=" + new Date().getTime());
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
                                                //$("input[name='tScIcInitialentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);//采购限价
                                                $("input[name='tScIcInitialentryList[" + rowIndex + "].barCode']").val(barCode);
                                                $("input[name='tScIcInitialentryList[" + rowIndex + "].coefficient']").val(cofficient);
                                                $("input[name='tScIcInitialentryList[" + rowIndex + "].coefficient']").trigger("change");
                                            }
                                        });
                                    }
                                }
                            })
                            $("#unitId\\[" + rowIndex + "\\]").combobox("setValue", unitId);
                            setValOldIdAnOldName($("input[name='tScIcInitialentryList[" + rowIndex + "].itemNo']"), itemId, number);
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
    var oldValue = $("input[name='tScIcInitialentryList[" + index + "].qty']").attr("oldvalue");
    var newValue = $("input[name='tScIcInitialentryList[" + index + "].qty']").val();
    if (oldValue > newValue) {
        tip("数量不可小于当前数量");
        $("input[name='tScIcInitialentryList[" + index + "].qty']").val(oldValue);
        return;
    }
    setAllQty(index);
}

//汇总折扣金额
function setAllAmount() {
    var tbody = $("#add_tScIcInitialentry_table");//$("#tScIcInitialentry_table");
    var length = $(tbody).children("tr").length;//$tbody[0].rows.length;
    var allCostAmount = 0;
    var allCostPrice = 0;
    var allQty = 0;
    var allBasicQty = 0;
    for (var i = 0; i < length; i++) {
        var costAmount = $("input[name='tScIcInitialentryList[" + i + "].costAmount']").val();//成本金额
        var qty = $("input[name='tScIcInitialentryList[" + i + "].qty']").val();//箱数
        var costPrice = $("input[name='tScIcInitialentryList[" + i + "].costPrice']").val();//单价
        var basicQty = $("input[name='tScIcInitialentryList[" + i + "].basicQty']").val();//箱数
        if (!costAmount) {
            costAmount = 0;
        }
        if (!costPrice) {
            costPrice = 0;
        }
        if (!basicQty) {
            basicQty = 0;
        }
        if (!qty) {
            qty = 0;
        }
        allCostAmount += parseFloat(costAmount);
        allCostPrice += parseFloat(costPrice);
        allQty += parseFloat(qty);
        allBasicQty += parseFloat(basicQty);
    }
    $("#allCostAmount").val(allCostAmount);
    $("#allQty").val(allQty);
    $("#allBasicQty").val(allBasicQty);
}
//保存前校验单价是否为0
var isCheck = true;
var isCheck2 = true;
function checkPriceIsZero(item) {
    //var isCheckNegative = $("#isCheckNegative").val();
    var stockName = $("#stockName").val();
    if (stockName == "") {
        tip("请填写仓库");
        return false;
    }
    //var dateobj = $("#date");
    //var date = $(dateobj).val();//.datebox("getValue");
    //$("#date").val(date);

    /*var accountId = $("#accountId").val();
     if(accountId){
     var curPayAmount = $("#curPayAmount").val();
     if(curPayAmount <= 0){
     tip("本次付款不可小于0，请确认");
     return false;
     }
     }*/
    //验证每个明细表的基本数量必须大于0
    var tbody = $("#add_tScIcInitialentry_table");//$("#tScIcInitialentry_table");
     var length =  $(tbody).children("tr").length;//$tbody[0].rows.length;
     var checkItemName = "";
     var checkValue = "";
     var tranType = $("#tranType").val();//单据类型 52：；入库单 53：退货单
     for(var checkIndex=0 ; checkIndex<length ; checkIndex++){
         //var costPrice = $("input[name='tScIcInitialentryList["+checkIndex+"].costPrice']").val();
         var itemName = $("input[name='tScIcInitialentryList["+checkIndex+"].itemName']").val();
         //var qty = $("input[name='tScIcInitialentryList["+checkIndex+"].qty']").val();//箱数
         //var smallQty = $("input[name='tScIcInitialentryList["+checkIndex+"].smallQty']").val();//散数
         //var basicQty = $("input[name='tScIcInitialentryList["+checkIndex+"].basicQty']").val();//数量
         //var itemId = $("input[name='tScIcInitialentryList["+checkIndex+"].itemId']").val();
         //var stockId = $("input[name='tScIcInitialentryList["+checkIndex+"].stockId']").val();
         //var batchNo = $("input[name='tScIcInitialentryList["+checkIndex+"].batchNo']").val();
         var basicQty = $("input[name='tScIcInitialentryList["+checkIndex+"].basicQty']").val();
         if(parseFloat(basicQty) <=0){
             tip("商品【" + itemName + "】箱数和散数不能同时为零,请确认");
             return false;
         }
        //系统设置入库数量允超数量  存货初始化不做入库数量
         /*var allowPassQty = 0;
         if(parseFloat(billQty) > 0 || stockQty) {
             if("52"==tranType) {
                 if ((parseFloat(qty) - (parseFloat(billQty) - parseFloat(stockQty))) > allowPassQty) {
                     var cs = parseFloat(qty) - (parseFloat(billQty) - parseFloat(stockQty)) - allowPassQty;
                     tip("商品【" + itemName + "】入库数量超出允超数量:" + cs + ",请确认");
                     return false;
                 }
             }else if("53" == tranType){
                 if(parseFloat(qty) > parseFloat(billQty)){
                     var cs = parseFloat(qty)-parseFloat(billQty);
                     tip("商品【" + itemName + "】退货数量超出订单数量:" + cs + ",请确认");
                     return false;
                 }
             }
         }
         if(isCheckNegative){
         checkValue += itemId+"#"+itemName+"#"+stockId+"#"+qty+"#"+batchNo+",";
         }
         if(!cgLimitPrice){
            continue;
         }else{
            if(parseFloat(price) < parseFloat(cgLimitPrice)){
                checkItemName += itemName+",";
             }
         }*/

    }
    /*c、	存货初始化不做是否超出限价的判断
     if(isCheck || isCheck2) {
     if (checkItemName.length > 0 && isCheck) {
     parent.$.dialog.confirm("商品[" + itemName + "]的单价低于采购限价，是否继续保存？", function () {
     isCheck = false;
     $("#formobj").submit();
     }
     , function () {
     //
     })
     return false;
     }else {
     if(tranType == "53"){
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
     }else {
     return true;
     }
     }
     }else{
     return true;
     }*/
    return true;
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
    var kfDate = $("input[name='tScIcInitialentryList[" + index + "].kfDate']").val();//生产日期
    var kfPeriod = $("input[name='tScIcInitialentryList[" + index + "].kfPeriod']").val();//保质期
    var kfDateType = $("select[name='tScIcInitialentryList[" + index + "].kfDateType']").val();//保质期类型
    var interval = "";
    if (kfDateType == "0001") {
        interval = "y";
    } else if (kfDateType == "0002") {
        interval = "m";
    } else if (kfDateType == "0003") {
        interval = "d";
    }
    var periodDate = dateAdd(interval, kfPeriod, new Date(kfDate));
    $("input[name='tScIcInitialentryList[" + index + "].periodDate']").val(periodDate);
}
//更改优惠金额时执行操作
function changeAllAmount() {
    //库存初始化无优惠金额
    /*var rebateAmount = $("#rebateAmount").val();
     if(!rebateAmount){
     rebateAmount = 0;
     }
     var allAmount = $("#amount").val();
     if(allAmount){
     allAmount = parseFloat(allAmount) - parseFloat(rebateAmount);
     $("#allAmount").val(allAmount);
     }*/
}

//表格校验事件
function orderListStockBlur(rowIndex, id, name) {
    checkInput($('input[name="tScIcInitialentryList[' + rowIndex + '].' + id + '"]'), $('input[name="tScIcInitialentryList[' + rowIndex + '].' + name + '"]'));
}

//判断是否允许增行操作
function checkAllowAddLine(index) {
    var isAddLine = true;
    if (!isAddLine) {
        $("#addTScOrderentryBtn\\[" + index + "\\]").css("display", "none");
        $("input[name='tScIcInitialentryList[" + index + "].itemNo']").attr("readonly", "readonly");
    }
}

//日期格式化
function dateFormatter(date) {
    date = new Date(date);
    var year = date.getFullYear();
    var month = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
    var date = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return year + "-" + month + "-" + date;
}

//审核前校验
function checkAudit() {
    //存货初始化不验证审核后出现负库存
    /*var isCheckNegative = $("#isCheckNegative").val();
     if(isCheckNegative) {
     var tranType = $("#tranType").val();
     if("53" == tranType){
     var isContinue = true;
     var itemName = "";
     var billId = $("#id").val();
     $.ajax({
     async: false,
     url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=T_SC_IC_InitiallEntry&parentId=fid",
     type: "POST",
     dataType: "json",
     success: function (data) {
     if(!data.success){
     isContinue = false;
     //itemName = data.msg;
     }
     }
     })
     if (isContinue) {
     return false;
     } else {
     //itemName = itemName.substring(0, itemName.length - 1);
     return "该单据审核后将出现负库存，不可审核，请确认";
     }
     }else{
     return false;
     }
     }else{
     return false;
     }*/
    //需要判断作废单单据或已审核单据不允许审核；还需要判断当前主体单位是否已经系统开帐，如果开帐了就不允许进行审核，页面上已经公共功能进行按钮控制。
    return false;
}

//反审核前校验
function checkUnAudit() {
    /*var isCheckNegative = $("#isCheckNegative").val();
     if(isCheckNegative) {
     var tranType = $("#tranType").val();
     if("52"==tranType) {
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
     //itemName = data.msg;
     }
     }
     })
     if (isContinue) {
     return false;
     } else {
     //itemName = itemName.substring(0, itemName.length - 1);
     return "该单据反审核后将出现负库存，不可反审核，请确认";
     }
     }
     }else{
     return false;
     }*/
    //需要判断作废单单据或未审核单据不允许反审核；还需要判断当前主体单位是否已经系统开帐，如果开帐了就不允许进行反审核，页面上已经公共功能进行按钮控制。
    return false;
}

//审核后执行
function afterAudit() {
    //var tranType = $("#tranType").val();
    //var id = $("#id").val();
    //var url = "tScIcInitialController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
    //$.ajax({
    //	url: url,
    //	type: 'post',
    //	cache: false,
    //	success: function (d) {
    //		//
    //	}
    //});
    return false;
}

//反审核后执行
function afterUnAudit(billId) {
    //var tranType = $("#tranType").val();
    //var url = "tScIcInitialController.do?afterAudit&audit=0&id=" + billId+"&tranType="+tranType;
    //$.ajax({
    //	url: url,
    //	type: 'post',
    //	cache: false,
    //	success: function (d) {
    //		//
    //	}
    //});
    return false;
}
//金额精度控制
function amountCfg(obj){
    var CFG_MONEY = $("#CFG_MONEY").val();
    var amount = parseFloat($(obj).val());
    if(isNaN(amount)){
        $(obj).val("");
    }else {
        $(obj).val(amount.toFixed(CFG_MONEY));
    }
}
//数量精度控制
function numberCfg(obj){
    var CFG_NUMBER = $("#CFG_NUMBER").val();
    var number = parseFloat($(obj).val());
    if(isNaN(number)){
        $(obj).val("");
    }else {
        $(obj).val(number.toFixed(CFG_NUMBER));
    }
}

//草稿恢复后操作事项
function doTempRecoveryExt() {
    debugger;
    var $tbody = $("#tScIcInitialentry_table");
    var length1 = $tbody[0].rows.length;
    //根据商品是否启用保质期管理、批号管理，来控制是否要求输入保持期、批号
    for (var i = 0; i < length1; i++) {
        var batchManager = $("input[name='tScIcInitialentryList["+i+"].batchManager']").val();
        if("N"==batchManager){
            $("input[name='tScIcInitialentryList[" + i + "].batchNo']").attr("readonly","readonly");
            $("input[name='tScIcInitialentryList[" + i + "].batchNo']").css("background-color","rgb(226,226,226)");
            $("input[name='tScIcInitialentryList[" + i + "].batchNo']").removeAttr("datatype");
        }else{
            $("input[name='tScIcInitialentryList[" + i + "].batchNo']").attr("onkeypress","keyDownInfo('"+i+"','batchNo')");
        }
        var isKfPeriod = $("input[name='tScIcInitialentryList["+i+"].isKfPeriod']").val();
        //if("N"==isKfPeriod) {
        if("Y" != isKfPeriod) {
            //生产日期
            $("input[name='tScIcInitialentryList[" + i + "].kfDate']").attr("readonly","readonly");
            $("input[name='tScIcInitialentryList[" + i + "].kfDate']").css("background-color","rgb(226,226,226)");
            $("input[name='tScIcInitialentryList[" + i + "].kfDate']").attr("onClick","");
            $("input[name='tScIcInitialentryList[" + i + "].kfDate']").val("");
            //保质期
            $("input[name='tScIcInitialentryList[" + i + "].kfPeriod']").attr("readonly","readonly");
            $("input[name='tScIcInitialentryList[" + i + "].kfPeriod']").css("background-color","rgb(226,226,226)");
            //保质期类型
            $("select[name='tScIcInitialentryList[" + i + "].kfDateType']").attr("disabled", "disabled");
            $("select[name='tScIcInitialentryList[" + i + "].kfDateType']").css("background-color", "rgb(226,226,226)");
            $("select[name='tScIcInitialentryList[" + i + "].kfDateType_']").attr("disabled", "disabled");
            $("select[name='tScIcInitialentryList[" + i + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
        }
    }
}