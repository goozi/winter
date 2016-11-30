var isSetAllowPriceZero = false;//销售订单系统设置为0提示
var isCheck1 =true ;
var isCheck2 = true;
$(function () {
    $.ajax({
        cache:false
    });
    //$("#itemName").on("dblclick", function () {
    //    selectItemNameDialog();
    //});
    var pageType = $("#pageType").val();
    if(pageType!=1){
        $(".formtable  select[name$='fetchStyle']").on('change', function () {
            var fetchStyleVal = $(".formtable  select[name$='fetchStyle']").val();
            if (fetchStyleVal != "" && fetchStyleVal == "0002") {
                $(".formtable #preAmount").removeAttr("readonly");
                $(".formtable #preAmount").css("background-color", "#ffffff");
            } else {
                $(".formtable #preAmount").val("");
                $(".formtable #preAmount").attr("readonly", "true");
                $(".formtable #preAmount").css("background-color", "rgb(226, 226, 226)");

            }
        });
        }
    $("#itemName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectItemNameDialog();
        }
    });
    $("#itemName").blur(function () {
        checkInput($("#itemID"), $("#itemName"));
    });
    /*    $("#empName").on("dblclick", function () {
     selectEmpNameDialog();
     });*/
    $("#empName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectEmpNameDialog();
        }
    });
    $("#empName").blur(function () {
        checkInput($("#empID"), $("#empName"));
    })

    /*  $("#deptName").on("dblclick", function () {
     selectDeptNameDialog();
     });*/
    $("#deptName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectDeptNameDialog();

        }
    });
    $("#deptName").blur(function () {
        checkInput($("#deptID"), $("#deptName"));
    });
    /*  $("#stockName").on("dblclick", function () {
     selectStockNameDialog();
     });*/
    $("#stockName").keypress(function (e) {
        if (e.keyCode == 13) {
            selectStockNameDialog();
        }
    });
    $("#stockName").blur(function () {
        checkInput($("#stockID"), $("#stockName"));
    });
    /*    $(":input").click(function () {
     //alert(itemNameIsPop);
     var itemNameVal = $("#itemName").val();
     if (itemNameVal != "" && itemNameVal != undefined){
     if (itemNameIsPopTemp) {
     $("#itemName").val(itemNameTemp);
     } else {
     $("#itemName").val("");
     itemNameIsPopTemp =false;
     }
     }else{
     itemNameIsPopTemp =false;
     }
     });*/
    //优惠金额
    $('#rebateAmount').change(function () {
        sumAllAmount();
    });
    //订单金额
    $("#amount").change(function () {
        sumAllAmount();

    });
    //如果是变更
     var pageType = $("#pageType").val();
    if(pageType == 1){
        $("#date").removeAttr('onclick');
        $("#date").attr("readonly","readonly");
        $("#itemName").attr("readonly","readonly");
        $("#empName").attr("readonly","readonly");
        $("#deptName").attr("readonly","readonly");
        $("#stockName").attr("readonly","readonly");
        $("#contact").attr("readonly","readonly");
        $("#mobilePhone").attr("readonly","readonly");
        $("#phone").attr("readonly","readonly");
        $("#fax").attr("readonly","readonly");
        $("#address").attr("readonly","readonly");
        $(".formtable  select[name$='fetchStyle']").attr("onfocus","this.defaultIndex=this.selectedIndex");
        $(".formtable  select[name$='fetchStyle']").attr("onchange","this.selectedIndex=this.defaultIndex");
        $("#rebateAmount").attr("readonly","readonly");
        $("#freight").attr("readonly","readonly");
        $("#explanation").attr("readonly","readonly");
    }
    //选单选择销售报价单
    chooseOrderQuoteButton();

    var CFG_OTHER = $("#CFG_OTHER").val();
    var CFG_MONEY = $("#CFG_MONEY").val();
    debugger;
    var weight = $("#weight").val() || 0;
    var allAmount = $("#allAmount").val() || 0;
    var preAmount = $("#preAmount").val() || 0;
    var rebateAmount = $("#rebateAmount").val() || 0;
    var freight = $("#freight").val() || 0;
    $("#weight").val(parseFloat(weight).toFixed(CFG_OTHER));
    $("#allAmount").val(parseFloat(allAmount).toFixed(CFG_MONEY));
    $("#preAmount").val(parseFloat(preAmount).toFixed(CFG_MONEY));
    $("#rebateAmount").val(parseFloat(rebateAmount).toFixed(CFG_MONEY));
    $("#freight").val(parseFloat(freight).toFixed(CFG_MONEY));



});
function chooseOrderQuoteButton(){
    var button = document.createElement("input");
    button.id="chooseOrderQuoteButton";
    button.type="button";
    button.class="button";
    button.value="选单";
    var load = $("#load").val();
    if("detail" != load) {
        var buttonDiv = document.getElementsByClassName("ui_buttons");
        //buttonDiv[0].appendChild(button);
        var selectbutton = document.createElement("select");
        selectbutton.id = "selectOrderbutton";
        selectbutton.setAttribute("onchange","selectBillDialog()");
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


function selectBillDialog(){
    if($("#itemName").val()==""){
        tip("请您先填写客户,在进行选单操作");
        $("#selectOrderbutton").val("");
        return;
    }
    var sonId = $("#sonID").val();
    var url = encodeURI('tScQuoteController.do?getQuoteDialog&checkState=2&customerId='+$("#itemID").val()+'&sonId='+sonId);
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
                    var item = items[items.length-1];
                    $("#empID").val(item.empid);
                    $("#empName").val(item.empName);
                    setValOldIdAnOldName($("#empName"),item.empid,item.empName);
                    $("#deptId").val(item.deptid);
                    $("#deptName").val(item.deptName);
                    setValOldIdAnOldName($("#deptName"),item.deptid,item.deptName);

                    $("#itemName").attr("readonly","readonly");

                    var rowIndex = $("#add_tScOrderentry_table tr").length - 1;
                    var id = $("input[name='tScOrderentryList["+rowIndex+"].itemID']").val();
                    var itemsLength = items.length;
                    if(!id){
                        itemsLength--;
                    }
                    for (var j = 0; j < itemsLength; j++) {
                        clickAddTScOrderentryBtn(rowIndex);//相当于在之后增加一行
                    }
                    if(id){
                        rowIndex++;
                    }
                    //rowIndex = rowIndex +1;
                    $.each(items, function (i, item) {
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
                        var idSrc = item.idSrc;//源单id
                        var entryIdSrc = item.entryId;//源单分录id
                        var billNoSrc = item.billNo;//源单编号
                        var classIdSrc = item.tranType;//源单类型
                        var classIdName = "销售报价单";
                        //通过id获取子表里面的信息
                        $.ajax({
                            type: 'POST',
                            url: 'tScIcitemController.do?getIcItemPriceInfoByIcitemId',
                            async: false,
                            cache: false,
                            data: {'id': id},
                            dataType: 'json',
                            success: function (data) {
                                if (data.success == true) {
                                    var resultData = data.attributes;
                                    secUnitID = resultData.secUnitId;//辅助单位的id
                                    secCoefficient = resultData.secCoefficient;//辅助单位的辅助单位换算率
                                    basicUnitId = resultData.basicUnitId;//基本单位的id
                                }
                            }
                        });
                        if (i > 0) {
                            var countIndex = parseInt(rowIndex) + parseInt(i);
                            $('input[name="tScOrderentryList[' + countIndex + '].itemNumber"]').attr("readonly","readonly");//商品编号只读
                            $('input[name="tScOrderentryList[' + countIndex + '].qty"]').val(0);//数量
                            $('input[name="tScOrderentryList[' + countIndex + '].qty"]').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].itemID"]').val(id);//商品id
                            $('input[name="tScOrderentryList[' + countIndex + '].itemNumber"]').val(item.icitemNumber);//商品编号
                            $('input[name="tScOrderentryList[' + countIndex + '].itemName"]').val(item.icitemName);//商品名称
                            $('input[name="tScOrderentryList[' + countIndex + '].itemModel"]').val(item.icitemModel);//规格
                            $('input[name="tScOrderentryList[' + countIndex + '].itemWeight"]').val(item.icitemWeight);//商品重量
                            //$('input[name="tScOrderentryList['+countIndex+'].itemWeight"]').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].itemWeight"]:first').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].itemBarcode"]').val(barCode);//条形码
                            $('input[name="tScOrderentryList[' + countIndex + '].secUnitID"]').val(secUnitID);//辅助单位
                            $('input[name="tScOrderentryList[' + countIndex + '].basicUnitID"]').val(basicUnitId);//基本单位
                            $('input[name="tScOrderentryList[' + countIndex + '].secCoefficient"]').val(secCoefficient);//辅助换算率
                            $('input[name="tScOrderentryList[' + countIndex + '].coefficient"]').val(coefficient);//单位换算率
                            $('input[name="tScOrderentryList[' + countIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价
                            $('input[name="tScOrderentryList[' + countIndex + '].taxPriceEx"]').val(price);//单价
                            $('input[name="tScOrderentryList[' + countIndex + '].taxPriceEx"]').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].interIDSrc"]').val(idSrc);
                            $('input[name="tScOrderentryList[' + countIndex + '].idSrc"]').val(entryIdSrc);
                            $('input[name="tScOrderentryList[' + countIndex + '].billNoSrc"]').val(billNoSrc);
                            $('input[name="tScOrderentryList[' + countIndex + '].classIDSrc"]').val(classIdSrc);
                            $('input[name="tScOrderentryList[' + countIndex + '].classIDName"]').val(classIdName);
                            $('#tScOrderentryList\\[' + countIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
                            $('#tScOrderentryList\\[' + countIndex + '\\]\\.unitID').combobox({
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
                                                    coefficient = resultData.coefficient;
                                                    barCode = resultData.barCode;
                                                    xsLimitPrice = resultData.xsLimitPrice
                                                    $('input[name="tScOrderentryList[' + countIndex + '].coefficient"]').val(coefficient);//换算率
                                                    $('input[name="tScOrderentryList[' + countIndex + '].coefficient"]').trigger("change");
                                                    $('input[name="tScOrderentryList[' + countIndex + '].itemBarcode"]').val(barCode);//条形码
                                                    $('input[name="tScOrderentryList[' + countIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            $('#tScOrderentryList\\[' + countIndex + '\\]\\.unitID').combobox('setValue', unitId);
                            setValOldIdAnOldName($('input[name="tScOrderentryList[' + countIndex + '].itemNumber"]'), id, item.icitemNumber);

                        } else {
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]').attr("readonly","readonly");
                            $('input[name="tScOrderentryList[' + rowIndex + '].qty"]').val(0);//数量
                            $('input[name="tScOrderentryList[' + rowIndex + '].qty"]').trigger("change");
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemID"]').val(id);//商品id
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]').val(number);//商品编号
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemName"]').val(item.icitemName);//商品名称
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemModel"]').val(item.icitemModel);//规格
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemWeight"]').val(item.icitemWeight);//商品重量
                            //$('input[name="tScOrderentryList['+rowIndex+'].itemWeight"]').trigger("change");
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemWeight"]:first').trigger("change");
                            //$('#tScOrderentryList\\['+rowIndex+'\\]\\.itemWeight').trigger("change");
                            //$('#tScOrderentryList\\['+rowIndex+'\\]\\.itemWeight').change();
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemBarcode"]').val(barCode);//条形码
                            $('input[name="tScOrderentryList[' + rowIndex + '].secUnitID"]').val(secUnitID);//辅助单位
                            $('input[name="tScOrderentryList[' + rowIndex + '].basicUnitID"]').val(basicUnitId);//基本单位
                            $('input[name="tScOrderentryList[' + rowIndex + '].secCoefficient"]').val(secCoefficient);//辅助换算率
                            $('input[name="tScOrderentryList[' + rowIndex + '].coefficient"]').val(coefficient);//单位换算率
                            $('input[name="tScOrderentryList[' + rowIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价
                            $('input[name="tScOrderentryList[' + rowIndex + '].taxPriceEx"]').val(price);//单价
                            $('input[name="tScOrderentryList[' + rowIndex + '].taxPriceEx"]').trigger("change");
                            $('input[name="tScOrderentryList[' + rowIndex + '].interIDSrc"]').val(idSrc);
                            $('input[name="tScOrderentryList[' + rowIndex + '].idSrc"]').val(entryIdSrc);
                            $('input[name="tScOrderentryList[' + rowIndex + '].billNoSrc"]').val(billNoSrc);
                            $('input[name="tScOrderentryList[' + rowIndex + '].classIDSrc"]').val(classIdSrc);
                            $('input[name="tScOrderentryList[' + rowIndex + '].classIDName"]').val(classIdName);
                            $('#tScOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
                            $('#tScOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox({
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
                                                    coefficient = resultData.coefficient;
                                                    barCode = resultData.barCode;
                                                    xsLimitPrice = resultData.xsLimitPrice
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].coefficient"]').val(coefficient);//换算率
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].coefficient"]').trigger("change");
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].itemBarcode"]').val(barCode);//条形码
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价

                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            $('#tScOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox('setValue', unitId);
                            setValOldIdAnOldName($('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]'), id, item.icitemNumber);
                        }
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
    $("#selectOrderbutton").val("");
}
//统计应收金额
function sumAllAmount() {
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
function checkForm() {
    //变更操作
/*
    if($('#pageType').val()!=""&&$('#pageType').val()==1){
        $("#add_tScOrderentry_table > tr").each(function (i, data) {
            var stockTemp;
            if($("input[name='tScOrderentryList["+i+"].stockQty']").val()==""){
                stockTemp = 0;
            }else{
                stockTemp = $("input[name='tScOrderentryList["+i+"].stockQty']").val();
            }
             $("input[name='tScOrderentryList["+i+"].stockQty']").val();
             $("input[name='tScOrderentryList["+i+"].qty']").val()
        });
    }
*/

    //信用控制
    var iscreditmgr = $("#iscreditmgr").val();
    if ("1" == iscreditmgr) {
        var creditamount = $("#creditamount").val();//客户信用额度
        var countAmount = $("#countAmount").val();//客户已执行金额
        var allamount = $("#allAmount").val();//应付金额
        var timePoint = $("#CFG_CONTROL_TIMEPOINT").val();
        if ("0" == timePoint) {
            if (parseFloat(allamount) + parseFloat(countAmount) > parseFloat(creditamount)) {
                var method = $("#CFG_CONTROL_METHOD").val();
                if ("1" == method) {
                    tip( "金额已超出客户的信用额度，不可保存");
                    return false;
                }
            }
        }
    }
    //付款方式
    var fetchStyleVal = $(".formtable  select[name$='fetchStyle']").val();
    var preAmountVal = $(".formtable #preAmount").val();
    var allAmountVal = $(".formtable #allAmount").val();
    if (fetchStyleVal != "" && fetchStyleVal == "0002") {
        if (preAmountVal == "" || parseFloat(preAmountVal) == 0) {
            tip("当付款方式为【款到发货时】，【预收金额】不能小于或者等于0");
            return false;
        }
        if (allAmountVal == "" || allAmountVal == 0) {
            allAmountVal = parseFloat(0);
        }
        if (parseFloat(preAmountVal) > parseFloat(allAmountVal)) {
            tip("当付款方式为【款到发货时】，【预收金额】不能大于应收金额");
            return false;
        }
    }
    //客户
    checkInput($("#itemID"), $("#itemName"));
    checkInput($("#empID"), $("#empName"));
    checkInput($("#deptID"), $("#deptName"));
    checkInput($("#stockID"), $("#stockName"));
    $("#add_tScOrderentry_table > tr").each(function (i, data) {
        checkInput($('input[name="tScOrderentryList[' + i + '].itemID"]'), $('input[name="tScOrderentryList[' + i + '].itemNumber"]'));
        checkInput($('input[name="tScOrderentryList[' + i + '].stockID"]'), $('input[name="tScOrderentryList[' + i + '].stockName"]'));
    });
    var isFreeGifts = false;
    var xsLimitPriceMsg = "";
    var isSetAllowPriceZeroMSg="";
    $("#add_tScOrderentry_table > tr").each(function (i, data) {
        var taxPriceEx = $('input[name="tScOrderentryList[' + i + '].taxPriceEx"]').val();
        var itemNumber = $('input[name="tScOrderentryList[' + i + '].itemNumber"]').val();
        var freeGifts = $('input[name="tScOrderentryList[' + i + '].freeGifts"]').val();
        var taxPriceEx = $('input[name="tScOrderentryList[' + i + '].taxPriceEx"]').val();
        var xsLimitPrice = $('input[name="tScOrderentryList[' + i + '].xsLimitPrice"]').val();
        if(!xsLimitPrice){
            xsLimitPrice = 0;
        }
        if (freeGifts == "1") {
            if (taxPriceEx != "") {
                taxPriceEx = parseFloat(taxPriceEx);
            } else {
                taxPriceEx = parseFloat(0);
            }
            if (taxPriceEx != 0) {
                tip("商品编号为：" + itemNumber + "是赠品标记时,单价必须为0");
                isFreeGifts = true;
                return false;
            }
        }
        if (taxPriceEx != "") {
            taxPriceEx = parseFloat(taxPriceEx);
        } else {
            taxPriceEx = parseFloat(0);
        }
        if(taxPriceEx == 0){
            isSetAllowPriceZeroMSg+=itemNumber+",";
        }
        if (xsLimitPrice) {
            xsLimitPrice = parseFloat(xsLimitPrice);
        } else {
            xsLimitPrice = parseFloat(0);
        }
        if (xsLimitPrice > 0 && taxPriceEx < xsLimitPrice) {//表体分录的单价低于商品对应单位的销售限价时给出询问提示
            xsLimitPriceMsg += itemNumber + ",";
        }
    });
    if (isFreeGifts == true) {
        return false;
    }
    //供应链系统设置设置了销售订单单价为0时提示，则有分录的单价为0时给出询问提示
    //$.ajax({
    //    type: 'POST',
    //    url: 'tSConfigController.do?getConfigByCode',
    //    async: false,
    //    cache: false,
    //    data: {'code': 'CFG_PRICE_ZERO'},
    //    dataType: 'json',
    //    success: function (data) {
    var CFG_PRICE_ZERO = $("#CFG_PRICE_ZERO").val();
    if(CFG_PRICE_ZERO == "1"){
        isSetAllowPriceZero = true;
    }
    //    }
    //});
    if(isCheck1 || isCheck2){
        if (xsLimitPriceMsg.length > 0 || isSetAllowPriceZeroMSg.length > 0){
            if(xsLimitPriceMsg.length >0 && isCheck1){
                var msg1 = xsLimitPriceMsg.substring(0,xsLimitPriceMsg.length-1);
                $.dialog.confirm("商品编号：[" + msg1 + "]的单价低于商品对应单位的销售限价，是否继续保存？", function () {
                    isCheck1 = false;
                    $("#formobj").submit();
                }, function () {
                    isCheck1 = true;
                });
            }else{
                if(isSetAllowPriceZero && isCheck2 && isSetAllowPriceZeroMSg.length > 0) {
                    var msg2 = isSetAllowPriceZeroMSg.substring(0,isSetAllowPriceZeroMSg.length-1);
                    parent.$.dialog.confirm("商品[" + msg2 + "]的单价为0，是否继续保存？", function () {
                        isCheck2 = false;
                        $("#formobj").submit();
                    }, function () {
                        isCheck2 = true;
                    });
                }else{
                    isSetAllowPriceZero=false;
                    xsLimitPriceMsg="";
                    isSetAllowPriceZeroMSg="";
                    isCheck1 = true;
                    isCheck2 = true;
                    return true;
                }
            }
        }else{
            isSetAllowPriceZero=false;
            xsLimitPriceMsg="";
            isSetAllowPriceZeroMSg="";
            isCheck1 = true;
            isCheck2 = true;
            return true;
        }
        return false;
    }else{
        isSetAllowPriceZero=false;
        xsLimitPriceMsg="";
        isSetAllowPriceZeroMSg="";
        isCheck1 = true;
        isCheck2 = true;
        return true;
    }
}
function selectStockNameDialog() {
    var tempName = $("#stockName").val();
    var url = encodeURI('tBiStockController.do?goSelectDialog&name=' + tempName);
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
                $("input[name='stockName']").val(item.name);
                $("input[name='stockID']").val(item.id);
                $("input[name='tScOrderentryList[#index#].stockID']").val(item.id);//设置模板从表头取值
                $("input[name='tScOrderentryList[#index#].stockName']").val(item.name);
                setValOldIdAnOldName($("input[name='tScOrderentryList[#index#].stockName']"), item.id, item.name)
                $('#add_tScOrderentry_table').find("input[name$=stockName]").val(item.name);//设置从表的值
                $('#add_tScOrderentry_table').find("input[name$=stockID]").val(item.id);
                setValOldIdAnOldName($('#add_tScOrderentry_table').find("input[name$=stockName]"), item.id, item.name)
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
                var iscreditmgr = item.iscreditmgr;//是否启用信用
                var creditamount = item.creditamount;//信用额度
                var countAmount = item.countAmount;//已执行金额
                debugger;
                var phone = item.phone;
                var mobilePhone = item.mobilephone;
                var contact = item.contact;
                var address = item.address;
                var fax = item.fax;
                $("#phone").val(phone);
                $("#mobilePhone").val(mobilePhone);
                $("#contact").val(contact);
                $("#address").val(address);
                $("#fax").val(fax);
                $("input[name='itemName']").val(item.name);
                $("input[name='itemID']").val(item.id);
                $("#iscreditmgr").val(iscreditmgr);
                $("#creditamount").val(creditamount);
                $("#countAmount").val(countAmount);
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
function resetTrNum(tableId, rowIndex,isAdd) {
    $tbody = $("#" + tableId + "");
    $tbody.find('>tr').each(function (i) {
        $(':input, select,button,a', this).each(function () {
            var $this = $(this), name = $this.attr('name'), id = $this.attr('id'), onclick_str = $this.attr('onclick'), val = $this.val(),
                str_press = $this.attr('onkeypress'), blur_str = $this.attr('onblur'), comboname_str=$this.attr('comboname'), onchange_str = $this.attr("onchange");//comboname是单位里面的
            if (name != null) {
                if (name.indexOf("#index#") >= 0) {
                    $this.attr("name", name.replace('#index#', i));
                } else {
                    var s = name.indexOf("[");
                    var e = name.indexOf("]");
                    var new_name = name.substring(s + 1, e);
                    $this.attr("name", name.replace(new_name, i));
                }
                if(name.indexOf("qty") > -1){
                    $this.attr("onblur","changePrice("+i+")");
                }else if(name.indexOf("freeGifts_") > -1){
                    $this.attr("onchange","setPrice("+i+")");
                }
            }
            if(comboname_str!=null){
                var s = comboname_str.indexOf("[");
                var e = comboname_str.indexOf("]");
                var new_comboname_str = comboname_str.substring(s + 1, e);
                $this.attr("comboname", comboname_str.replace(new_comboname_str, i));
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
                if (onclick_str == "WdatePicker()") {
                    if ($this.val() == "") {
                        $this.val(setmorendate());
                    }
                } else {
                    if (onclick_str.indexOf("#index#") >= 0) {
                        $this.attr("onclick", onclick_str.replace(/#index#/g, i));
                    } else {
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
                    var e = blur_str.indexOf(")");
                    var new_blur = blur_str.substring(s + 1, e);
                    $this.attr("onblur", blur_str.replace(new_blur, i));
                }
            }
        });
        setFunctionInfo(i);
        $("input[name='tScOrderentryList[" + i + "].indexNumber']").val(i + 1);
        $(this).find('div[name=\'xh\']').html(i + 1);
    });
    $tbody.find('>tr').each(function (i) {
        var a = parseInt(rowIndex) + 1;
        if (a == i && isAdd) {
            $("#tScOrderentryList\\[" + a + "\\]\\.unitID").combobox({
                width:54,
                valueField: "id",
                textField: "text",
                panelHeight: "auto",
                editable: false
            });
        }
    });
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

/***
 * 子表里面的商品编号操作
 */
function orderListIcitemKeyPress(rowIndex, evt) {
    evt = (evt) ? evt : ((window.event) ? window.event : "");
    var key = evt.keyCode ? evt.keyCode : evt.which;
    if (key == 13) {
        selectOrderLisIcitemKeyPressDialog(rowIndex);
    }
}
function selectOrderLisIcitemKeyPressDialog(rowIndex) {
    var itemNumber = $('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]').val();
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
                    var nextTrLength = $('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]').parent().parent().nextAll().length;//判断后面的行数
                    var itemsLength = items.length;
                    if (itemsLength > nextTrLength) {//增行
                        var appendLength = itemsLength - nextTrLength;
                        for (var j = 0; j < appendLength - 1; j++) {
                            clickAddTScOrderentryBtn(rowIndex);//相当于在之后增加一行
                        }
                    }
                    var sonId = $("#sonID").val();//当前分支机构
                    $.each(items, function (i, item) {
                        //var url = "tScIcitemController.do?getItemInfo&id=" + id+"&rowIndex="+rowIndex;
                        //$.ajax({
                        //    type: 'POST',
                        //    url: 'tScDepartmentController.do?goDepartName',
                        //    async: false,
                        //    cache:false,
                        //    data: {'id':deptId},
                        //    dataType: 'json',
                        //    success:function(data){
                        //        if(data.success == true){
                        //            var departName = data.obj;
                        //            $("input[name='deptName']").val(departName);
                        //            $("input[name='deptID']").val(deptId);
                        //            setValOldIdAnOldName($("#deptName"), deptId, departName);
                        //        }
                        //    }
                        //});
                        var id = item.id;//商品id
                        var taxRateOut = item.taxRateOut;//商品销项税
                        var stockSonId = item.stockSonId;//商品默认仓库分支机构id；
                        var unitId = "";//默认仓存单位的从表主键id（放从表的id）
                        var barCode = "";//默认仓存单位的商品的从表的条形码
                        var secUnitID = "";//辅助单位（放从表的id为辅助单位）
                        var basicUnitId = ""; //基本单位（从表的id）
                        var secCoefficient = "";//辅助换算率
                        var coefficient = "";//单位换算率
                        var xsLimitPrice = "";//销售限价
                        var stockId = "";//默认仓库id
                        var stockName = "";//默认仓库名称
                        //通过id获取子表里面的信息
                        $.ajax({
                            type: 'POST',
                            url: 'tScIcitemController.do?getIcItemPriceInfoByIcitemId',
                            async: false,
                            cache: false,
                            data: {'id': id},
                            dataType: 'json',
                            success: function (data) {
                                if (data.success == true) {
                                    var resultData = data.attributes;
                                    unitId = resultData.unitId; //仓存单位的id的默认仓存
                                    barCode = resultData.barCode;//条码
                                    secUnitID = resultData.secUnitId;//辅助单位的id
                                    basicUnitId = resultData.basicUnitId;//基本单位的id
                                    secCoefficient = resultData.secCoefficient;//辅助单位的辅助单位换算率
                                    coefficient = resultData.cofficient;//单位换算率
                                    xsLimitPrice = resultData.xsLimitPrice//销售限价
                                    stockId = resultData.stockId;//仓库id
                                    stockName = resultData.stockName;//仓库名称
                                }
                            }
                        });

                        if (i > 0) {
                            var countIndex = parseInt(rowIndex) + parseInt(i);
                            var checkId = $("input[name='tScOrderentryList[" + countIndex + "].stockId']").val();
                            if(!checkId) {
                                if(stockSonId == sonId) {
                                    $("input[name='tScOrderentryList[" + countIndex + "].stockId']").val(stockId);
                                    $("input[name='tScOrderentryList[" + countIndex + "].stockName']").val(stockName);
                                }
                            }
                            setValOldIdAnOldName($('input[name="tScOrderentryList[' + countIndex + '].stockName"]'),stockId,stockName);
                            $('input[name="tScOrderentryList[' + countIndex + '].taxRate"]').val(taxRateOut);
                            $('input[name="tScOrderentryList[' + countIndex + '].taxRate"]').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].itemID"]').val(item.id);//商品id
                            $('input[name="tScOrderentryList[' + countIndex + '].itemNumber"]').val(item.number);//商品编号
                            $('input[name="tScOrderentryList[' + countIndex + '].itemName"]').val(item.name);//商品名称
                            $('input[name="tScOrderentryList[' + countIndex + '].itemModel"]').val(item.model);//规格
                            $('input[name="tScOrderentryList[' + countIndex + '].itemWeight"]').val(item.weight);//商品重量
                            //$('input[name="tScOrderentryList['+countIndex+'].itemWeight"]').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].itemWeight"]:first').trigger("change");
                            $('input[name="tScOrderentryList[' + countIndex + '].itemBarcode"]').val(barCode);//条形码
                            $('input[name="tScOrderentryList[' + countIndex + '].secUnitID"]').val(secUnitID);//辅助单位
                            $('input[name="tScOrderentryList[' + countIndex + '].basicUnitID"]').val(basicUnitId);//基本单位
                            $('input[name="tScOrderentryList[' + countIndex + '].secCoefficient"]').val(secCoefficient);//辅助换算率
                            $('input[name="tScOrderentryList[' + countIndex + '].coefficient"]').val(coefficient);//单位换算率
                            $('input[name="tScOrderentryList[' + countIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价
                            $('#tScOrderentryList\\[' + countIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
                            $('#tScOrderentryList\\[' + countIndex + '\\]\\.unitID').combobox({
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
                                                    coefficient = resultData.coefficient;
                                                    barCode = resultData.barCode;
                                                    xsLimitPrice = resultData.xsLimitPrice
                                                    $('input[name="tScOrderentryList[' + countIndex + '].coefficient"]').val(coefficient);//换算率
                                                    $('input[name="tScOrderentryList[' + countIndex + '].coefficient"]').trigger("change");
                                                    $('input[name="tScOrderentryList[' + countIndex + '].itemBarcode"]').val(barCode);//条形码
                                                    $('input[name="tScOrderentryList[' + countIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            $('#tScOrderentryList\\[' + countIndex + '\\]\\.unitID').combobox('setValue', unitId);
                            /*   $('#tScOrderentryList\\['+countIndex+'\\]\\.unitID').empty();
                             /!***
                             * 单位的获取
                             *!/
                             $.ajax({
                             type: 'POST',
                             url: 'tScIcitemController.do?loadItemUnit',
                             async: false,
                             cache:false,
                             data: {'id':id},
                             dataType: 'json',
                             success:function(data){
                             var jsonArray = data;
                             $.each(jsonArray,function(i,json){
                             var option=$("<option>").val(json.id).text(json.text);
                             $('#tScOrderentryList\\['+countIndex+'\\]\\.unitID').append(option);
                             });
                             }
                             });
                             $('#tScOrderentryList\\['+countIndex+'\\]\\.unitID').val(unitId);
                             $('#tScOrderentryList\\['+countIndex+'\\]\\.unitID').attr("onchange","changeUnit("+countIndex+")");*/
                            setValOldIdAnOldName($('input[name="tScOrderentryList[' + countIndex + '].itemNumber"]'), item.id, item.number);

                        } else {
                            var checkId = $("input[name='tScOrderentryList[" + rowIndex + "].stockId']").val();
                            if(!checkId) {
                                if(stockSonId == sonId) {
                                    $("input[name='tScOrderentryList[" + rowIndex + "].stockId']").val(stockId);
                                    $("input[name='tScOrderentryList[" + rowIndex + "].stockName']").val(stockName);
                                }
                            }
                            setValOldIdAnOldName($('input[name="tScOrderentryList[' + rowIndex + '].stockName"]'),stockId,stockName);
                            $('input[name="tScOrderentryList[' + rowIndex + '].taxRate"]').val(taxRateOut);
                            $('input[name="tScOrderentryList[' + rowIndex + '].taxRate"]').trigger("change");
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemID"]').val(item.id);//商品id
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]').val(item.number);//商品编号
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemName"]').val(item.name);//商品名称
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemModel"]').val(item.model);//规格
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemWeight"]').val(item.weight);//商品重量
                            //$('input[name="tScOrderentryList['+rowIndex+'].itemWeight"]').trigger("change");
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemWeight"]:first').trigger("change");
                            //$('#tScOrderentryList\\['+rowIndex+'\\]\\.itemWeight').trigger("change");
                            //$('#tScOrderentryList\\['+rowIndex+'\\]\\.itemWeight').change();
                            $('input[name="tScOrderentryList[' + rowIndex + '].itemBarcode"]').val(barCode);//条形码
                            $('input[name="tScOrderentryList[' + rowIndex + '].secUnitID"]').val(secUnitID);//辅助单位
                            $('input[name="tScOrderentryList[' + rowIndex + '].basicUnitID"]').val(basicUnitId);//基本单位
                            $('input[name="tScOrderentryList[' + rowIndex + '].secCoefficient"]').val(secCoefficient);//辅助换算率
                            $('input[name="tScOrderentryList[' + rowIndex + '].coefficient"]').val(coefficient);//单位换算率
                            $('input[name="tScOrderentryList[' + rowIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价
                            $('#tScOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + id);
                            $('#tScOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox({
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
                                                    coefficient = resultData.coefficient;
                                                    barCode = resultData.barCode;
                                                    xsLimitPrice = resultData.xsLimitPrice
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].coefficient"]').val(coefficient);//换算率
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].coefficient"]').trigger("change");
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].itemBarcode"]').val(barCode);//条形码
                                                    $('input[name="tScOrderentryList[' + rowIndex + '].xsLimitPrice"]').val(xsLimitPrice);//销售限价

                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            $('#tScOrderentryList\\[' + rowIndex + '\\]\\.unitID').combobox('setValue', unitId);
                            /*   $('#tScOrderentryList\\['+rowIndex+'\\]\\.unitID').empty();
                             /!***
                             * 单位的获取
                             *!/
                             $.ajax({
                             type: 'POST',
                             url: 'tScIcitemController.do?loadItemUnit',
                             async: false,
                             cache:false,
                             data: {'id':id},
                             dataType: 'json',
                             success:function(data){
                             var jsonArray = data;
                             //$('#tScOrderentryList\\['+rowIndex+'\\]\\.unitID')
                             $.each(jsonArray,function(i,json){
                             var option=$("<option>").val(json.id).text(json.text);
                             $('#tScOrderentryList\\['+rowIndex+'\\]\\.unitID').append(option);
                             });
                             //if(data.success == true){
                             //    debugger;
                             //    var resultData = data.attributes;
                             //    ////coefficient = resultData.coefficient;
                             //    //barCode = resultData.barCode;
                             //    //$('input[name="tScOrderentryList['+countIndex+'].itemBarcode"]').val(barCode);//条形码
                             //
                             //}
                             }
                             });
                             $('#tScOrderentryList\\['+rowIndex+'\\]\\.unitID').val(unitId);
                             $('#tScOrderentryList\\['+rowIndex+'\\]\\.unitID').attr("onchange","changeUnit("+rowIndex+")");*/
                            setValOldIdAnOldName($('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]'), item.id, item.number);
                        }

                    });
                }

                //$('input[name="tScOrderentryList['+rowIndex+'].stockID"]').val(item.id);
                //$('input[name="tScOrderentryList['+rowIndex+'].stockName"]').val(item.name);
                //setValOldIdAnOldName($('input[name="tScOrderentryList['+rowIndex+'].stockName"]'), item.id, item.name);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();

}
function orderListIcItemBlur(rowIndex) {
    checkInput($('input[name="tScOrderentryList[' + rowIndex + '].itemID"]'), $('input[name="tScOrderentryList[' + rowIndex + '].itemNumber"]'));
}
/**
 * 单位改变
 */
function changeUnit(index) {
    var id = $('#tScOrderentryList\\[' + index + '\\]\\.unitID').val();
    $.ajax({
        type: 'POST',
        url: 'tScIcitemController.do?getChangeInfo',
        async: false,
        cache: false,
        data: {'unitId': id},
        dataType: 'json',
        success: function (data) {
            if (data.success == true) {
                var resultData = data.attributes;
                //coefficient = resultData.coefficient;
                var barCode = resultData.barCode;
                $('input[name="tScOrderentryList[' + index + '].itemBarcode"]').val(barCode);//条形码

            }
        }
    });
}
/**
 * 子表键盘按键 仓库
 * @param rowIndex
 * @param event
 */
function orderListStockKeyPress(rowIndex, evt) {
    //var number = getCharCode(event);
    /*    alert("1:"+event.keyCode);
     alert("2:"+event.which);*/
    //var lkeycode=(navigator.appName=="Netscape")?event.keyCode:event.which;
    //var lkeycode=window.event? e.keyCode: e.which;
    evt = (evt) ? evt : ((window.event) ? window.event : "");
    var key = evt.keyCode ? evt.keyCode : evt.which;
    if (key == 13) {
        selectorderListStockKeyPressDialog(rowIndex);
    }
}
function selectorderListStockKeyPressDialog(rowIndex) {
    var itemName = $('input[name="tScOrderentryList[' + rowIndex + '].stockName"]').val();
    var url = encodeURI('tBiStockController.do?goSelectDialog&name=' + itemName);
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
                $('input[name="tScOrderentryList[' + rowIndex + '].stockID"]').val(item.id);
                $('input[name="tScOrderentryList[' + rowIndex + '].stockName"]').val(item.name);
                setValOldIdAnOldName($('input[name="tScOrderentryList[' + rowIndex + '].stockName"]'), item.id, item.name);
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}

function orderListStockBlur(rowIndex) {
    checkInput($('input[name="tScOrderentryList[' + rowIndex + '].stockID"]'), $('input[name="tScOrderentryList[' + rowIndex + '].stockName"]'));
}

function editQtyChange(index){
    var oldval= $("input[name='tScOrderentryList["+index+"].qty']").attr('oldvalue');
    var stockQty = $("input[name='tScOrderentryList["+index+"].stockQty']").val();
    if(stockQty==""){
        stockQty = 0;
    }
    var qty = $("input[name='tScOrderentryList["+index+"].qty']").val();
    if(qty==""){
        qty = 0;
    }
    if(parseFloat(stockQty) >=0){
         if(parseFloat(qty)< parseFloat(stockQty)){
             tip("修改的数量必须大于执行数量");
             $("input[name='tScOrderentryList["+index+"].qty']").val(oldval);
             return;
         }
    }else{
        tip("执行数量小于0");
        $("input[name='tScOrderentryList["+index+"].qty']").val(oldval);
        return ;
    }
}
//数量发生改变时，取报价单单价
var rowInfo = {};
function changePrice(index){
    var itemId = $("#itemID").val();
    var qty = $("input[name='tScOrderentryList["+index+"].qty']").val();
    if(isNaN(qty)){
        $("input[name='tScOrderentryList["+index+"].qty']").val("");
        return;
    }
    var entryItemId = $("input[name='tScOrderentryList["+index+"].itemID']").val();//商品
    var unitId = $("#tScOrderentryList\\["+index+"\\]\\.unitID").combobox("getValue");
    var itemNo = $("input[name='tScOrderentryList["+index+"].itemNumber']").val();//商品编号
    var freegifts = $("select[name='tScOrderentryList[" + index + "].freeGifts_']").val();
    var tranType = $("#tranType").val();
    var classIDName = $("input[name='tScOrderentryList["+index+"].classIDName']").val();//源单类型
    if(itemId && qty && entryItemId && unitId && freegifts == "0" && !classIDName){
        $.ajax({
            type: 'POST',
            url: 'tScQuoteController.do?getPrice',
            async: false,
            cache: false,
            data: {'tranType':tranType,'itemId': itemId,'qty':qty,'entryItemId':entryItemId,'unitId':unitId,'itemNo':itemNo},
            dataType: 'json',
            success: function (data) {
                var resultData = data.attributes;
                var price = resultData.price;//报价单价
                if (data.success == true) {
                    var salesID = resultData.salesID;//促销类型
                    var salesType = resultData.salesType;//促销类型分类 0：调价 1：买一赠一
                    var salesName = "调价政策";
                    if("1" == salesType){
                        salesName = "买一赠一"
                    }
                    $("input[name='tScOrderentryList["+index+"].salesID']").val(salesID);
                    $("input[name='tScOrderentryList["+index+"].salesName']").val(salesName);
                    if("1" == salesType){
                        var isFreeGift =  $("input[name='tScOrderentryList["+index+"].isFreeGift']").val();
                        if(isFreeGift == "1"){
                            var rowLength = rowInfo[index];
                            for(var i = 0 ; i < rowLength ; i++){
                                clickDelTScOrderentryBtn((index + 1));
                            }
                        }
                        $("input[name='tScOrderentryList["+index+"].isFreeGift']").val(1);
                        var freeGiftInfo = resultData.freeGiftInfo;
                        rowInfo[index] = freeGiftInfo.length;
                        for(var j = 0; j < freeGiftInfo.length ; j++) {
                            clickAddTScOrderentryBtn((index+j));
                            var itemId = freeGiftInfo[j].itemId;
                            var itemNo = freeGiftInfo[j].itemNo;
                            var itemName = freeGiftInfo[j].itemName;
                            var model = freeGiftInfo[j].model;
                            var barCode = freeGiftInfo[j].barCode;
                            var unitId = freeGiftInfo[j].unitId;
                            var qty = freeGiftInfo[j].qty;
                            var basicQty = freeGiftInfo[j].basicQty;
                            var coefficient = freeGiftInfo[j].coefficient;
                            var freeIndex = index+j + 1;
                            //赠品信息
                            //商品
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemId']").val(itemId);
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemNumber']").val(itemNo);
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemNumber']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemNumber']").css("background-color", "rgb(226, 226, 226)");
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemName']").val(itemName);
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemModel']").val(model);
                            $("input[name='tScOrderentryList[" + freeIndex + "].itemBarcode']").val(barCode);
                            $("input[name='tScOrderentryList[" + freeIndex + "].salesName']").val(salesName);
                            $("input[name='tScOrderentryList[" + freeIndex + "].coefficient']").val(coefficient);
                            $("input[name='tScOrderentryList[" + freeIndex + "].basicQty']").val(basicQty);
                            setValOldIdAnOldName($("input[name='tScOrderentryList[" + freeIndex + "].itemNumber']"),itemId,itemNo);
                            //数量
                            $("input[name='tScOrderentryList[" + freeIndex + "].qty']").val(qty);
                            $("input[name='tScOrderentryList[" + freeIndex + "].qty']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].qty']").css("background-color", "rgb(226, 226, 226)");
                            $("input[name='tScOrderentryList[" + freeIndex + "].qty']").trigger("change");
                            //单价
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxPriceEx']").val(0);
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxPriceEx']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxPriceEx']").css("background-color", "rgb(226, 226, 226)");
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxPriceEx']").trigger("change");
                            //金额
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxAmountEx']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxAmountEx']").css("background-color", "rgb(226, 226, 226)");
                            //折扣率
                            $("input[name='tScOrderentryList[" + freeIndex + "].discountRate']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].discountRate']").css("background-color", "rgb(226, 226, 226)");
                            //折后金额
                            $("input[name='tScOrderentryList[" + freeIndex + "].inTaxAmount']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].inTaxAmount']").css("background-color", "rgb(226, 226, 226)");
                            //税率
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxRate']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].taxRate']").css("background-color", "rgb(226, 226, 226)");
                            //赠品标记
                            $("select[name='tScOrderentryList[" + freeIndex + "].freeGifts_']").val(1);
                            $("select[name='tScOrderentryList[" + freeIndex + "].freeGifts_']").attr("readonly", "readonly");
                            $("select[name='tScOrderentryList[" + freeIndex + "].freeGifts_']").trigger("change");
                            $("input[name='tScOrderentryList[" + freeIndex + "].freeGifts']").val(1);
                            //交货日期
                            $("input[name='tScOrderentryList[" + freeIndex + "].fetchDate']").attr("readonly", "readonly");
                            $("input[name='tScOrderentryList[" + freeIndex + "].fetchDate']").removeAttr("onclick");
                            $("input[name='tScOrderentryList[" + freeIndex + "].fetchDate']").css("background-color", "rgb(226, 226, 226)");
                            //仓库
                            //$("input[name='tScOrderentryList[" + freeIndex + "].stockName']").attr("readonly", "readonly");
                            //$("input[name='tScOrderentryList[" + freeIndex + "].stockName']").css("background-color", "rgb(226, 226, 226)");
                            //单位
                            $('#tScOrderentryList\\[' + freeIndex + '\\]\\.unitID').combobox('reload', "tScIcitemController.do?loadItemUnit&id=" + itemId);
                            $('#tScOrderentryList\\[' + freeIndex + '\\]\\.unitID').combobox("setValue",unitId);
                            $('#tScOrderentryList\\[' + freeIndex + '\\]\\.unitID').combobox("disable");
                            $("input[name='tScOrderentryList[" + freeIndex + "].isFreeGift']").val(1);
                        }
                    }else{
                        var isFreeGift =  $("input[name='tScOrderentryList["+index+"].isFreeGift']").val();
                        if(isFreeGift == "1"){
                            var rowLength = rowInfo[index];
                            for(var i = 0 ; i < rowLength ; i++){
                                clickDelTScOrderentryBtn((index + 1));
                            }
                        }
                        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").val(price);
                        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").trigger("change");
                        $("input[name='tScOrderentryList["+index+"].isFreeGift']").val(0);
                    }
                    //$("input[name='tScOrderentryList["+index+"].taxPriceEx']").val(price);
                    //$("input[name='tScOrderentryList["+index+"].taxPriceEx']").trigger("change");
                }else{
                    $("input[name='tScOrderentryList["+index+"].salesID']").val("");
                    $("input[name='tScOrderentryList["+index+"].salesName']").val("");
                    var isFreeGift =  $("input[name='tScOrderentryList["+index+"].isFreeGift']").val();
                    if(isFreeGift == "1"){
                        var rowLength = rowInfo[index];
                        for(var i = 0 ; i < rowLength ; i++){
                            clickDelTScOrderentryBtn((index + 1));
                        }
                    }
                    $("input[name='tScOrderentryList["+index+"].taxPriceEx']").val(price);
                    $("input[name='tScOrderentryList["+index+"].taxPriceEx']").trigger("change");
                    $("input[name='tScOrderentryList["+index+"].isFreeGift']").val(2);
                }
            }
        });
    }
}
//赠品标记发生改变时触发事件
function setPrice(index){
    var value = $("select[name='tScOrderentryList["+index+"].freeGifts_']").val();
    //$("input[name='tScOrderentryList["+index+"].freeGifts']").val(value);
    $("input[name='tScOrderentryList["+index+"].freeGifts']").val(value);
    if(value == 1){
        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").val(0);//单价
        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").trigger("change");
        //单价只读
        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").attr("readonly","readonly");
        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
        //金额只读
        $("input[name='tScOrderentryList["+index+"].taxAmountEx']").attr("readonly","readonly");
        $("input[name='tScOrderentryList["+index+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
        //折扣率只读
        $("input[name='tScOrderentryList["+index+"].discountRate']").attr("readonly","readonly");
        $("input[name='tScOrderentryList["+index+"].discountRate']").css("background-color","rgb(226,226,226)");
        //折后金额
        $("input[name='tScOrderentryList["+index+"].inTaxAmount']").attr("readonly","readonly");
        $("input[name='tScOrderentryList["+index+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
        //税率
        $("input[name='tScOrderentryList["+index+"].taxRate']").attr("readonly","readonly");
        $("input[name='tScOrderentryList["+index+"].taxRate']").css("background-color","rgb(226,226,226)");
        //onPriceChange(index);
    }else{
        //单价只读
        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").removeAttr("readonly");
        $("input[name='tScOrderentryList["+index+"].taxPriceEx']").css("background-color","white");
        //金额只读
        $("input[name='tScOrderentryList["+index+"].taxAmountEx']").removeAttr("readonly");
        $("input[name='tScOrderentryList["+index+"].taxAmountEx']").css("background-color","white");
        //折扣率只读
        $("input[name='tScOrderentryList["+index+"].discountRate']").removeAttr("readonly");
        $("input[name='tScOrderentryList["+index+"].discountRate']").css("background-color","white");
        //折后金额
        $("input[name='tScOrderentryList["+index+"].inTaxAmount']").removeAttr("readonly");
        $("input[name='tScOrderentryList["+index+"].inTaxAmount']").css("background-color","white");
        //税率
        $("input[name='tScOrderentryList["+index+"].taxRate']").removeAttr("readonly");
        $("input[name='tScOrderentryList["+index+"].taxRate']").css("background-color","white");
    }
}


//审核前校验
function checkAudit() {
    //信用控制
    var iscreditmgr = $("#iscreditmgr").val();
    if ("1" == iscreditmgr) {
        var creditamount = $("#creditamount").val();//客户信用额度
        var countAmount = $("#countAmount").val();//客户已执行金额
        var allamount = $("#allAmount").val();//应付金额
        var timePoint = $("#CFG_CONTROL_TIMEPOINT").val();
        if ("1" == timePoint) {
            if (parseFloat(allamount) + parseFloat(countAmount) > parseFloat(creditamount)) {
                var method = $("#CFG_CONTROL_METHOD").val();
                if ("1" == method) {
                    return "金额已超出客户的信用额度，不可审核";
                }
            }
        }
    }
}

//复制后方法
function doTempRecoveryExt(){
    $tbody = $("#add_tScOrderentry_table");
    var length1 = $tbody[0].rows.length;
    for(var i=0 ; i<length1 ; i++){
        //$("input[name='tScOrderentryList["+i+"].idSrc']").val("");
        //$("input[name='tScOrderentryList["+i+"].classIDSrc']").val("");
        //$("input[name='tScOrderentryList["+i+"].billNoSrc']").val("");
        //$("input[name='tScOrderentryList["+i+"].classIDName']").val("");
        //$("input[name='tScOrderentryList["+i+"].stockQty']").val(0);
        //$("input[name='tScOrderentryList["+i+"].salesName']").val("");
        //$("input[name='tScOrderentryList["+i+"].salesID']").val("");
        var isFreeGifts = $("select[name='tScOrderentryList["+i+"].freeGifts_']").val();
        if("1" == isFreeGifts){
            $("input[name='tScOrderentryList["+i+"].taxPriceEx']").val(0);//单价
            $("input[name='tScOrderentryList["+i+"].taxPriceEx']").trigger("change");
            //单价只读
            $("input[name='tScOrderentryList["+i+"].taxPriceEx']").attr("readonly","readonly");
            $("input[name='tScOrderentryList["+i+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
            //金额只读
            $("input[name='tScOrderentryList["+i+"].taxAmountEx']").attr("readonly","readonly");
            $("input[name='tScOrderentryList["+i+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
            //折扣率只读
            $("input[name='tScOrderentryList["+i+"].discountRate']").attr("readonly","readonly");
            $("input[name='tScOrderentryList["+i+"].discountRate']").css("background-color","rgb(226,226,226)");
            //折后金额
            $("input[name='tScOrderentryList["+i+"].inTaxAmount']").attr("readonly","readonly");
            $("input[name='tScOrderentryList["+i+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
            //税率
            $("input[name='tScOrderentryList["+i+"].taxRate']").attr("readonly","readonly");
            $("input[name='tScOrderentryList["+i+"].taxRate']").css("background-color","rgb(226,226,226)");
        }
    }
}


//反审核前校验
function checkUnAudit(){
    $tbody = $("#add_tScOrderentry_table");
    var length1 = $tbody[0].rows.length;
    for(var i=0 ; i<length1 ; i++){
        var stockQty = $("input[name='tScOrderentryList["+i+"].stockQty']").val();
        if(parseFloat(stockQty) > 0){
            return "该单据已被其他单据引用，不可反审核";
        }
    }
    return false;
}

//汇总金额
function setAllAmount(){
    $tbody = $("#add_tScOrderentry_table");
    var length1 = $tbody[0].rows.length;
    var sumAmount = 0;
    for(var i=0 ; i<length1 ; i++){
        var amount = $("input[name='tScOrderentryList["+i+"].inTaxAmount']").val();
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