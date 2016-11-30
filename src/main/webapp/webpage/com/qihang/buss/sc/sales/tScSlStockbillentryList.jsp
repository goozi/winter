<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addtScSlStockbillentryListBtn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="deltScSlStockbillentryListBtn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScSlStockbillentryBtn(rowIndex) {
        var tr = $("#add_tScSlStockbillentry_table_template tr").clone();
        $("#add_tScSlStockbillentry_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScSlStockbillentry_table', rowIndex,1);
    }

    function clickDeltScSlStockbillentryListBtn(rowIndex) {
        var len = $("#add_tScSlStockbillentry_table").find(">tr").length;
        $("#add_tScSlStockbillentry_table").find(">tr").eq(rowIndex).remove();
        if (rowIndex == 0 && len == 1) {//如果只有一行且删除这一行则删除后补一空行
            $("#itemName").removeAttr("disabled");
            var tr = $("#add_tScSlStockbillentry_table_template tr").clone();
            $("#add_tScSlStockbillentry_table").append(tr);
        }
        resetTrNum('add_tScSlStockbillentry_table', rowIndex);
        if(rowIndex == 0 && len == 1){
            $("#unitId\\[" + 0 + "\\]").combobox({
                valueField: "id",
                textField: "text",
                panelHeight: "auto",
                editable: false
            })
        }
    }

    function keyDownInfo(index, name, evt) {
        var evt = (evt) ? evt : ((window.event) ? window.event : "");
        var code = evt.keyCode ? evt.keyCode : evt.which;
        if (code == 13) {
            if (name == "stock") {
                selectStockDialog(index);
            } else if (name == "item") {
                selectIcitemDialog(index);
            } else if (name == "batchNo") {
                selectInventoryInfo(index);
            }
        }
    }

    $(document).ready(function () {
        $(".datagrid-toolbar").parent().css("width", "auto");
        if (location.href.indexOf("load=detail") != -1) {
            $(":input").attr("disabled", "true");
            $(".datagrid-toolbar").hide();
        }
        //将表格的表头固定
        $("#tScSlStockbillentryList_table").createhftable({
            height: (h-40)+"px",
            width: 'auto',
            fixFooter: true
        });
        //解决一对多页面中列表页输入框tip属性失效问题
        $('table').find("[tip]").each(function () {
            var defaultvalue = $(this).attr("tip");
            var altercss = $(this).attr("altercss");
            $(this).focus(function () {
                if ($(this).val() == defaultvalue) {
                    $(this).val('');
                    if (altercss) {
                        $(this).removeClass(altercss);
                    }
                }
            }).blur(function () {
                if ($.trim($(this).val()) === '') {
                    $(this).val(defaultvalue);
                    if (altercss) {
                        $(this).addClass(altercss);
                    }
                }
            });
        });
        var stockid = $("#stockid").val();
        if (stockid) {
            var stockName = $("#stockName").val();
            $("input[name='tScSlStockbillentryList[#index#].stockid']").val(stockid);
            $("input[name='tScSlStockbillentryList[#index#].stockName']").val(stockName);
        }
        if (${fn:length(tScSlStockbillentryList)  <= 0 }) {

            checkAllowAddLine(0);//判断是否允许增行操作
            var billDate = $("#date").val();
            var taxrate = $("#defaultTaxRate").val();
            $("input[name='tScSlStockbillentryList[0].taxRate']").val(taxrate);
            $("input[name='tScSlStockbillentryList[#index#].taxRate']").val(taxrate);
            $("input[name='tScSlStockbillentryList[0].kfdate']").val(billDate);
            $("input[name='tScSlStockbillentryList[0].stockName']").keypress(function (e) {
                if (e.keyCode == 13) {
                    selectStockDialog(0);
                }
            });
            setFunctionInfo(0);
//			$("input[name='tScSlStockbillentryList[0].unitName']").keypress(function(e){
//				if(e.keyCode == 13){
//					selectUnitDialog(0);
//				}
//			})
        } else {
            var checkState = $("#checkState").val();
            var $tbody = $("#tScSlStockbillentryList_table");
            var length = $tbody[0].rows.length;
            rowIndex = length - 1;
            var index = 0;
            var rowLength = 0;
            for (var j = 0; j < length; j++) {
                //编辑行金额函数配置
                if("detail" != "${load}") {
                    setFunctionInfo(j);
                }
                checkAllowAddLine(j);//判断是否允许增行操作
                //var unitId = $("#unitId\\["+j+"\\]").val();
                var itemid = $("input[name='tScSlStockbillentryList[" + j + "].itemid']").val();
                var itemNo = $("input[name='tScSlStockbillentryList[" + j + "].itemNo']").val();
                setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + j + "].itemNo']"), itemid, itemNo);
                var stockid = $("input[name='tScSlStockbillentryList[" + j + "].stockid']").val();
                if (stockid) {
                    var stockName = $("input[name='tScSlStockbillentryList[" + j + "].stockName']").val();
                    setValOldIdAnOldName($("input[name='tScSlStockbillentryList[" + j + "].stockName']"), stockid, stockName);
                }
                var entryOrderId = $("input[name='tScSlStockbillentryList[" + j + "].entryidOrder']").val();
                if (entryOrderId) {
                    if("fcopy" != '${load}') {
                        $("select[name='tScSlStockbillentryList[" + j + "].freegifts_']").attr("disabled", "disabled");
                        $("select[name='tScSlStockbillentryList[" + j + "].freegifts_']").css("background-color", "rgb(226,226,226)");
                    }
                }
                var batchManager = $("input[name='tScSlStockbillentryList[" + j + "].batchManager']").val();
                if ("N" == batchManager) {
                    $("input[name='tScSlStockbillentryList[" + j + "].batchno']").attr("readonly", "readonly");
                    $("input[name='tScSlStockbillentryList[" + j + "].batchno']").css("background-color", "rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList[" + j + "].batchno']").removeAttr("datatype");
                }else{
                    $("input[name='tScSlStockbillentryList[" + j + "].batchno']").attr("onkeypress","keyDownInfo('"+j+"','batchNo')");
                }
                var iskfperiod = $("input[name='tScSlStockbillentryList[" + j + "].isKfperiod']").val();
                if ("N" == iskfperiod) {
                    //生产日期
                    $("input[name='tScSlStockbillentryList[" + j + "].kfdate']").attr("readonly", "readonly");
                    $("input[name='tScSlStockbillentryList[" + j + "].kfdate']").css("background-color", "rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList[" + j + "].kfdate']").attr("onClick", "");
                    $("input[name='tScSlStockbillentryList[" + j + "].kfdate']").val("");
                    //保质期
                    $("input[name='tScSlStockbillentryList[" + j + "].kfperiod']").attr("readonly", "readonly");
                    $("input[name='tScSlStockbillentryList[" + j + "].kfperiod']").css("background-color", "rgb(226,226,226)");
                    //保质期类型
                    $("select[name='tScSlStockbillentryList[" + j + "].kfdatetype']").attr("disabled", "disabled");
                    $("select[name='tScSlStockbillentryList[" + j + "].kfdatetype']").css("background-color", "rgb(226,226,226)");
                }
                $("#unitId\\[" + j + "\\]").combobox({
                    onChange: function (newV, oldV) {
                        if (oldV != newV) {
                            var index = $(this)[0].id.replace(/[^0-9]/ig, "");
                            var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemid + "&unitId=" + newV + "&rowIndex=" + index;
                            $.ajax({
                                url: changeUrl,
                                dataType: 'json',
                                cache: false,
                                success: function (data) {
                                    var attributes = data.attributes;
                                    var cofficient = attributes.coefficient;
                                    var barCode = attributes.barCode;
                                    var rowIndex = attributes.rowIndex;
                                    var xsLimitPrice = attributes.xsLimitPrice;
                                    $("input[name='tScSlStockbillentryList[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);//采购限价
                                    $("input[name='tScSlStockbillentryList[" + rowIndex + "].barCode']").val(barCode);
                                    $("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").val(cofficient);
                                    $("input[name='tScSlStockbillentryList[" + rowIndex + "].coefficient']").trigger("change");
                                    $("input[name='tScSlStockbillentryList[" + rowIndex + "].seccoefficient']").trigger("change");
                                }
                            });
                        }
                    }
                })

                var idSrc = $("input[name='tScSlStockbillentryList[" + j + "].idSrc']").val();
                if (idSrc) {
                    if('fcopy' != '${load}') {
                        $("input[name='tScSlStockbillentryList[" + j + "].itemNo']").attr("disabled", "disabled");
                        $("#itemName").attr("disabled", "disabled");
                    }
                    $("#unitId\\[" + j + "\\]").combobox({
                        disabled: true
                    });
                }
                var freeGifts = $("input[name='tScSlStockbillentryList["+j+"].freegifts']").val();
                var isFreeGift = $("input[name='tScSlStockbillentryList["+j+"].isFreeGift']").val();
                debugger;
                if(freeGifts == "1" ){
                    $("input[name='tScSlStockbillentryList["+j+"].qty']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].qty']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList["+j+"].itemNo']").attr("disabled","disabled");
                    $("input[name='tScSlStockbillentryList["+j+"].itemNo']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList["+j+"].stockName']").removeAttr("disabled");
                    $("#unitId\\[" + j + "\\]").combobox({disabled:true});
                    $("input[name='tScSlStockbillentryList["+j+"].taxPriceEx']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList["+j+"].taxAmountEx']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList["+j+"].discountRate']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].discountRate']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList["+j+"].inTaxAmount']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScSlStockbillentryList["+j+"].taxRate']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].taxRate']").css("background-color","rgb(226,226,226)");
                    if("fcopy" != '${load}') {
                        $("select[name='tScSlStockbillentryList[" + j + "].freegifts_'']").attr("disabled", "disabled");
                    }
                    $("input[name='tScSlStockbillentryList["+j+"].note']").attr("readonly","readonly");
                    $("input[name='tScSlStockbillentryList["+j+"].note']").css("background-color","rgb(226,226,226)");
                    rowInfo[index]=rowLength+1;
                }else{
                    index = j;
                    rowLength = 0;
                }
            }
            //	}
        }
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScSlStockbillentryList_table" totalRow="true" style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
        <td align="center" bgcolor="#476f9a">序号</td>
        <c:if test="${load ne 'detail'}">
        <td align="center" bgcolor="#476f9a">操作</td>
        </c:if>
        <td align="center" bgcolor="#476f9a">
            商品编号<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            商品名称
        </td>
        <td align="center" bgcolor="#476f9a">
            规格
        </td>
        <td align="center" bgcolor="#476f9a">
            条码
        </td>
        <td align="center" bgcolor="#476f9a">
            仓库<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            批号
        </td>
        <td align="center" bgcolor="#476f9a">
            单位
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            数量<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            单价<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            金额
        </td>
        <td align="center" bgcolor="#476f9a">
            折扣率（%）
        </td>
        <td align="center" bgcolor="#476f9a">
            折后单价
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            折后金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            重量
        </td>
        <td align="center" bgcolor="#476f9a">
            税率（%）
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            税额
        </td>
        <td align="center" bgcolor="#476f9a">
            生产日期
        </td>
        <td align="center" bgcolor="#476f9a">
            保质期
        </td>
        <td align="center" bgcolor="#476f9a">
            保质期类型
        </td>
        <td align="center" bgcolor="#476f9a">
            有效期至
        </td>
        <c:if test="${tranType eq '103'}">
        <td align="center" bgcolor="#476f9a">
            促销类型
        </td>
        </c:if>
        <td align="center" bgcolor="#476f9a">
            赠品标记
        </td>
        <td align="center" bgcolor="#476f9a">
            退货数量
        </td>
        <td align="center" bgcolor="#476f9a">
            源单类型
        </td>
        <td align="center" bgcolor="#476f9a">
            源单编号
        </td>
        <td align="center" bgcolor="#476f9a">
            订单编号
        </td>
        <td align="center" bgcolor="#476f9a">
            备注
        </td>


    </tr>
    <tbody id="add_tScSlStockbillentry_table">
    <c:if test="${fn:length(tScSlStockbillentryList)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 30px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                    <a name="addtScSlStockbillentryListBtn[0]" id="addtScSlStockbillentryListBtn[0]" href="#"
                       onclick="clickAddTScSlStockbillentryBtn(0);"></a>
                    <a name="deltScSlStockbillentryListBtn[0]"
                       id="deltScSlStockbillentryListBtn[0]" href="#"
                       onclick="clickDeltScSlStockbillentryListBtn(0);"></a>
                </div>
            </td>
            <input name="tScSlStockbillentryList[0].id" type="hidden"/>
            <input name="tScSlStockbillentryList[0].createName" type="hidden"/>
            <input name="tScSlStockbillentryList[0].createBy" type="hidden"/>
            <input name="tScSlStockbillentryList[0].updateName" type="hidden"/>
            <input name="tScSlStockbillentryList[0].updateBy" type="hidden"/>
            <input name="tScSlStockbillentryList[0].createDate" type="hidden"/>
            <input name="tScSlStockbillentryList[0].updateDate" type="hidden"/>
            <input name="tScSlStockbillentryList[0].fid" type="hidden"/>
            <input name="tScSlStockbillentryList[0].basicunitid" type="hidden"/>
            <input name="tScSlStockbillentryList[0].coefficient" type="hidden"/>
            <input name="tScSlStockbillentryList[0].basicQty" type="hidden"/>
            <input name="tScSlStockbillentryList[0].secunitid" type="hidden"/>
            <input name="tScSlStockbillentryList[0].secCoefficient" type="hidden"/>
            <input name="tScSlStockbillentryList[0].secQty" type="hidden"/>
            <input name="tScSlStockbillentryList[0].price" type="hidden"/>
            <input name="tScSlStockbillentryList[0].amount" type="hidden"/>
            <input name="tScSlStockbillentryList[0].discountPrice" type="hidden"/>
            <input name="tScSlStockbillentryList[0].discountAmount" type="hidden"/>
            <input name="tScSlStockbillentryList[0].costPrice" type="hidden"/>
            <input name="tScSlStockbillentryList[0].costAmount" type="hidden"/>
            <input name="tScSlStockbillentryList[0].idSrc" type="hidden"/>
            <input name="tScSlStockbillentryList[0].entryidSrc" type="hidden"/>
            <input name="tScSlStockbillentryList[0].idOrder" type="hidden"/>
            <input name="tScSlStockbillentryList[0].entryidOrder" type="hidden"/>
            <input name="tScSlStockbillentryList[0].findex" value="1" type="hidden"/>
            <input name="tScSlStockbillentryList[0].itemid" type="hidden"/>
            <input name="tScSlStockbillentryList[0].stockid" type="hidden"/>
            <input name="tScSlStockbillentryList[0].freegifts" value="0" type="hidden"/>
            <input name="tScSlStockbillentryList[0].xsLimitPrice" type="hidden"/>
            <input name="tScSlStockbillentryList[0].billQty" type="hidden"/>
            <input name="tScSlStockbillentryList[0].itemweight" onchange="setAllWeight(0)" type="hidden"/>
            <input name="tScSlStockbillentryList[0].stockQty" type="hidden"/>
            <input name="tScSlStockbillentryList[0].invQty" type="hidden"/>
            <input name="tScSlStockbillentryList[0].classidSrc" type="hidden"/>
            <input name="tScSlStockbillentryList[0].isFreeGift" value="2" type="hidden"/>
            <input name="tScSlStockbillentryList[0].salesid" type="hidden"/>
            <input name="tScSlStockbillentryList[0].kfdatetype" type="hidden"/>
            <input name="tScSlStockbillentryList[0].batchManager" type="hidden"/>
            <input name="tScSlStockbillentryList[0].isKfperiod" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].itemNo" maxlength="32"
                       datatype="*" onkeypress="keyDownInfo(0,'item',event)"
                       onblur="orderListStockBlur('0','itemid','itemNo');" datatype="*"
                       type="text" class="inputxt popup-select"  style="width:105px;">
                <label class="Validform_label" style="display: none;">商品编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].itemName"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:180px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">商品名称</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].model"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:85px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">规格</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].barCode"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:65px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">条形码</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].stockName"  maxlength="32" onblur="orderListStockBlur('0','stockid','stockName');"
                       type="text" class="inputxt popup-select" datatype="*" style="width:65px;">
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].batchno" maxlength="50"
                       type="text" class="inputxt" datatype="*" style="width:80px;">
                <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                <input id="unitId[0]" name="tScSlStockbillentryList[0].unitid"
                       class="easyui-combobox"
                       data-options="valueField: 'id',width:54,textField: 'text',panelHeight: 'auto',editable: false"
                       type="text" style="width:50px;padding-left:2px;padding-right: 2px">
                <label class="Validform_label" style="display: none;">单位</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].qty" maxlength="20" onblur="setAllWeight(0)"
                       type="text" class="inputxt" style="width:70px;" datatype="vInt" >
                <label class="Validform_label" style="display: none;">数量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].taxPriceEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num" >
                <label class="Validform_label" style="display: none;">单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].taxAmountEx" maxlength="70"
                       type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore">
                <label class="Validform_label" style="display: none;">金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].discountRate" maxlength="70"
                       type="text" class="inputxt" value="100" style="width:70px;" datatype="num" ignore="ignore">
                <label class="Validform_label" style="display: none;">折扣率（%）</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].taxPrice" maxlength="70"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:70px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">折后单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].inTaxAmount" maxlength="70" onchange="setAllAmount()"
                       type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore">
                <label class="Validform_label" style="display: none;">折后金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].weight_" maxlength="70"
                       type="text" class="inputxt" readonly="readonly" style="width:70px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">重量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].taxRate" maxlength="70"
                       type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore">
                <label class="Validform_label" style="display: none;">税率（%）</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].taxAmount" maxlength="70"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:70px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">税额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].kfdate" maxlength="0" onchange="setPeriodDate(0)"
                       type="text" class="Wdate" <c:if test="${tranType ne '103'}">onClick="WdatePicker()" style="width:80px;" </c:if> <c:if test="${tranType eq '103'}"> readonly="readonly" style="width:80px;background-color: rgb(226,226,226);" </c:if>
                        >
                <label class="Validform_label" style="display: none;">生产日期</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].kfperiod" maxlength="10" onchange="setPeriodDate(0)"
                       type="text" class="inputxt" style="width:50px;background-color: rgb(226,226,226)" readonly="readonly" datatype="num" ignore="ignore">
                <label class="Validform_label" style="display: none;">保质期</label>
            </td>
            <td align="left" bgcolor="white">
                <t:dictSelect field="tScSlStockbillentryList[0].kfdatetype_" width="100px" type="list"
                              showDefaultOption="true" extendJson="{disabled:disabled}"
                              typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
                              title="保质期类型"></t:dictSelect>
                    <%--<input name="tScSlStockbillentryList[0].kfdatetype" maxlength="10"--%>
                    <%--type="text" class="inputxt" style="width:70px;">--%>
                <label class="Validform_label" style="display: none;">保质期类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].perioddate" maxlength="0"
                       type="text" class="Wdate" readonly="readonly"
                       style="width:80px;background-color:rgb(226,226,226)"
                        >
                <label class="Validform_label" style="display: none;">有效期至</label>
            </td>
            <c:if test="${tranType eq '103'}">
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].salesName" maxlength="10"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:65px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">促销类型</label>
            </td>
            </c:if>
            <td align="left" bgcolor="white">
                <t:dictSelect field="tScSlStockbillentryList[0].freegifts_" width="70px" type="list"
                              extendJson="{datatype:select1,onChange:setPrice(${0})}" showDefaultOption="false"
                              typeGroupCode="sf_01" defaultVal="0" hasLabel="false" title="赠品标记"></t:dictSelect>
                <label class="Validform_label" style="display: none;">赠品标记</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].commitqty" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:70px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">退货数量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].classidName" maxlength="10"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:90px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">源单类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].billnoSrc" maxlength="50"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:90px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">源单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].billnoOrder" maxlength="50"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:90px;background-color:rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">订单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScSlStockbillentryList[0].note" maxlength="255"
                       type="text" class="inputxt" style="width:180px;">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>

        </tr>
    </c:if>
    <c:if test="${fn:length(tScSlStockbillentryList)  > 0 }">
        <c:forEach items="${tScSlStockbillentryList}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 90px;background-color: white;">
                        <a name="addtScSlStockbillentryListBtn[${stuts.index}]"
                           id="addtScSlStockbillentryListBtn[${stuts.index}]" href="#"
                           onclick="clickAddTScSlStockbillentryBtn(${stuts.index});"></a>

                        <a name="deltScSlStockbillentryListBtn[${stuts.index}]"
                           id="deltScSlStockbillentryListBtn[${stuts.index}]" href="#"
                           onclick="clickDeltScSlStockbillentryListBtn(${stuts.index});"></a>
                    </div>
                </td>
                </c:if>
                <input name="tScSlStockbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].createBy" type="hidden"
                       value="${poVal.createBy }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].updateBy" type="hidden"
                       value="${poVal.updateBy }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].basicunitid" type="hidden"
                       value="${poVal.basicunitid }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].coefficient" type="hidden"
                       value="${poVal.coefficient }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].basicQty" type="hidden"
                       value="${poVal.basicQty }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].secunitid" type="hidden"
                       value="${poVal.secunitid }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].secCoefficient" type="hidden"
                       value="${poVal.secCoefficient }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].secQty" type="hidden" value="${poVal.secQty }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].price" type="hidden" value="${poVal.price }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].amount" type="hidden" value="${poVal.amount }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].discountPrice" type="hidden"
                       value="${poVal.discountPrice }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].discountAmount" type="hidden"
                       value="${poVal.discountAmount }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].costPrice" type="hidden"
                       value="${poVal.costPrice }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].costAmount" type="hidden"
                       value="${poVal.costAmount }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].idSrc" type="hidden" <c:if test="${load ne 'fcopy'}"> value="${poVal.idSrc }" </c:if> />
                <input name="tScSlStockbillentryList[${stuts.index }].entryidSrc" type="hidden"
                        <c:if test="${load ne 'fcopy'}"> value="${poVal.entryidSrc }" </c:if> />
                <input name="tScSlStockbillentryList[${stuts.index }].idOrder" type="hidden" <c:if test="${load ne 'fcopy'}"> value="${poVal.idOrder }" </c:if> />
                <input name="tScSlStockbillentryList[${stuts.index }].entryidOrder" type="hidden"
                       <c:if test="${load ne 'fcopy'}"> value="${poVal.entryidOrder }" </c:if> />
                <input name="tScSlStockbillentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].itemid" type="hidden" value="${poVal.itemid }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].stockid" type="hidden" value="${poVal.stockid }"/>
                <input name="tScSlStockbillentryList[${stuts.index }].xsLimitPrice" type="hidden"
                       value="${poVal.xsLimitPrice}"/>
                <input name="tScSlStockbillentryList[${stuts.index }].billQty" value="${poVal.billQty}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].itemweight" value="${poVal.itemweight}" onchange="setAllWeight(${stuts.index })" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].batchManager" value="${poVal.batchManager}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].isKfperiod" value="${poVal.isKfperiod}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].freegifts" value="${poVal.freegifts}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].invQty" value="${poVal.invQty}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].classidSrc" value="${poVal.classidSrc}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].isFreeGift" value="${poVal.isFreeGift}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].salesid" value="${poVal.salesid}" type="hidden"/>
                <input name="tScSlStockbillentryList[${stuts.index }].kfdatetype" value="${poVal.kfdatetype}" type="hidden"/>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].itemNo" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:105px;"
                           onkeypress="keyDownInfo('${stuts.index}','item',event)" onblur="orderListStockBlur('${stuts.index}','itemid','itemNo');"
                           value="${poVal.itemNo }">
                    <label class="Validform_label" style="display: none;">商品编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].itemName" maxlength="32"
                           type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226);" readonly="readonly"
                           value="${poVal.itemName }">
                    <label class="Validform_label" style="display: none;">商品名称</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].model" maxlength="32"
                           type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226);" readonly="readonly"
                           value="${poVal.model }">
                    <label class="Validform_label" style="display: none;">规格</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].barCode" maxlength="32"
                           type="text" class="inputxt" style="width:65px;background-color:rgb(226,226,226);" readonly="readonly"
                           value="${poVal.barCode }">
                    <label class="Validform_label" style="display: none;">条形码</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].stockName" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                           onkeypress="keyDownInfo('${stuts.index}','stock',event)" onblur="orderListStockBlur('${stuts.index}','stockid','stockName');"
                           value="${poVal.stockName }">
                    <label class="Validform_label" style="display: none;">仓库</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].batchno" maxlength="50"
                           type="text" class="inputxt" style="width:80px;" <c:if test="${poVal.batchManager eq 'Y'}"> datatype="*" </c:if>
                           value="${poVal.batchno }">
                    <label class="Validform_label" style="display: none;">批号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input id="unitId[${stuts.index }]" name="tScSlStockbillentryList[${stuts.index }].unitid"
                           maxlength="32"
                           type="text"
                           class="easyui-combobox" data-options="valueField: 'id',disabled:true,textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemid}'"
                           style="width: 50px"
                           value="${poVal.unitid }">
                    <label class="Validform_label" style="display: none;">单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].qty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;" onblur="setAllWeight(${stuts.index })"
                           datatype="vInt"  value="${poVal.qty }">
                    <label class="Validform_label" style="display: none;">数量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].taxPriceEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="float" value="${poVal.taxPriceEx }">
                    <label class="Validform_label" style="display: none;">单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].taxAmountEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" ignore="ignore" value="${poVal.taxAmountEx }">
                    <label class="Validform_label" style="display: none;">金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].discountRate" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" ignore="ignore" value="${poVal.discountRate }">
                    <label class="Validform_label" style="display: none;">折扣率（%）</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].taxPrice" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226);" readonly="readonly"
                           value="${poVal.taxPrice }">
                    <label class="Validform_label" style="display: none;">折后单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].inTaxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;" onchange="setAllAmount()"
                           datatype="num" ignore="ignore" value="${poVal.inTaxAmount }">
                    <label class="Validform_label" style="display: none;">折后金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].weight_" maxlength="20"
                           type="text" class="inputxt" readonly="readonly" style="width:70px;background-color:rgb(226,226,226)"
                           value="${poVal.weight }">
                    <label class="Validform_label" style="display: none;">重量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].taxRate" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" ignore="ignore" value="${poVal.taxRate }">
                    <label class="Validform_label" style="display: none;">税率（%）</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].taxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226);" readonly="readonly"
                           value="${poVal.taxAmount }">
                    <label class="Validform_label" style="display: none;">税额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].kfdate" maxlength="0"
                           type="text" class="Wdate" <c:if test="${tranType ne '103'}">onClick="WdatePicker()" style="width:80px;" </c:if> <c:if test="${tranType eq '103'}"> readonly="readonly" style="width:80px;background-color: rgb(226,226,226);"</c:if>

                           value="<fmt:formatDate value='${poVal.kfdate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">生产日期</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].kfperiod" maxlength="10" readonly="readonly"
                           type="text" class="inputxt" style="width:50px;background-color: rgb(226,226,226)"
                           datatype="num" ignore="ignore" value="${poVal.kfperiod }">
                    <label class="Validform_label" style="display: none;">保质期</label>
                </td>
                <td align="left" bgcolor="white">
                    <t:dictSelect field="tScSlStockbillentryList[${stuts.index }].kfdatetype_" width="100px" type="list"
                                  showDefaultOption="true" extendJson="{disabled:disabled}"
                                  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfdatetype }" hasLabel="false"
                                  title="保质期类型"></t:dictSelect>
                        <%--<input name="tScSlStockbillentryList[${stuts.index }].kfdatetype" maxlength="10"--%>
                        <%--type="text" class="inputxt" style="width:70px;"--%>
                        <%--value="${poVal.kfdatetype }">--%>
                    <label class="Validform_label" style="display: none;">保质期类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].perioddate" maxlength="0"
                           type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)" readonly="readonly"

                           value="<fmt:formatDate value='${poVal.perioddate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">有效期至</label>
                </td>
                <c:if test="${tranType eq '103'}">
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].salesName" maxlength="10"
                           type="text" class="inputxt" style="width:65px;background-color:rgb(226,226,226)" readonly="readonly"
                           value="${poVal.salesName }">
                    <label class="Validform_label" style="display: none;">促销类型</label>
                </td>
                </c:if>
                <td align="left" bgcolor="white">
                    <t:dictSelect field="tScSlStockbillentryList[${stuts.index }].freegifts_" width="70px" type="list"
                                  extendJson="{datatype:select1,onChange:setPrice(${stuts.index })}" showDefaultOption="false"
                                  typeGroupCode="sf_01" defaultVal="${poVal.freegifts }" hasLabel="false"
                                  title="赠品标记"></t:dictSelect>
                    <label class="Validform_label" style="display: none;">赠品标记</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].commitqty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
                        <c:if test="${load ne 'fcopy'}"> value="${poVal.commitqty }" </c:if> >
                    <label class="Validform_label" style="display: none;">退货数量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].classidName" maxlength="10"
                           type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
                        <c:if test="${load ne 'fcopy'}"> value="${poVal.classidName }" </c:if> >
                    <label class="Validform_label" style="display: none;">源单类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].billnoSrc" maxlength="50"
                           type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
                        <c:if test="${load ne 'fcopy'}"> value="${poVal.billnoSrc }" </c:if> >
                    <label class="Validform_label" style="display: none;">源单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].billnoOrder" maxlength="50"
                           type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
                        <c:if test="${load ne 'fcopy'}">   value="${poVal.billnoOrder }" </c:if> >
                    <label class="Validform_label" style="display: none;">订单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScSlStockbillentryList[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:180px;"
                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
