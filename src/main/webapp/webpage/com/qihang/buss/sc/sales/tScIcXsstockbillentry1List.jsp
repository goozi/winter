<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addTScIcXsstockbillentry1Btn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="delTScIcXsstockbillentry1Btn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScIcXsstockbillentry1Btn(rowIndex) {
        var tr = $("#add_tScIcXsstockbillentry1_table_template tr").clone();
        $("#add_tScIcXsstockbillentry1_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScIcXsstockbillentry1_table',rowIndex,'tScIcXsstockbillentry1List',1);
    }

    function clickDelTScIcXsstockbillentry1Btn(rowIndex) {
        $("#add_tScIcXsstockbillentry1_table").find(">tr").eq(rowIndex).remove();
        var length = $("#add_tScIcXsstockbillentry1_table").find(">tr").length;
        if(length == 0){
            //clickAddEntryBtn(0)
            $("#itemName").removeAttr("disabled");
            var tr =  $("#add_tScIcXsstockbillentry1_table_template tr").clone();
            $("#add_tScIcXsstockbillentry1_table").append(tr);
            resetTrNum('add_tScIcXsstockbillentry1_table',-1,"tScIcXsstockbillentry1List");
            $("#unitId1\\[" + 0 + "\\]").combobox({
                width:54,
                valueField: "id",
                textField: "text",
                panelHeight: "auto",
                editable: false
            })
        }else {
            resetTrNum('add_tScIcXsstockbillentry1_table',rowIndex,"tScIcXsstockbillentry1List");
        }
    }
    function keyDownInfoI(index,name,evt){
        var evt = (evt)?evt:((window.event)?window.event:"");
        var code =evt.keyCode?evt.keyCode:evt.which;
        if(code == 13){
            if(name=="stock") {
                selectStockDialog(index,"tScIcXsstockbillentry1");
            }else if(name == "item"){
                selectIcitemDialog(index,"tScIcXsstockbillentry1List");
            }else if(name == "unit"){
                selectUnitDialog(index);
            }else if(name == "batchNo"){
                selectInventoryInfo(index,"tScIcXsstockbillentry1List");
            }else if(name == "batchNo2"){
                selectInventoryInfo(index,"tScIcXsstockbillentry2List");
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
        $("#tScIcXsstockbillentry1_table").createhftable({
            height: (h-68)+'px',
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

        if(${fn:length(tScIcXsstockbillentry1List)  <= 0 }){
            checkAllowAddLine(0);//判断是否允许增行操作
            var billDate = $("#date").val();
            var taxRate = $("#defaultTaxRate").val();
            $("input[name='tScIcXsstockbillentry1List[0].kfDate']").val(billDate);
            $("input[name='tScIcXsstockbillentry1List[0].taxRate']").val(taxRate);
            $("select[name='tScIcXsstockbillentry1List[0].kfDateType']").attr("onChange","setPeriodDate(0,'tScIcXsstockbillentry1List')");
            $("input[name='tScIcXsstockbillentry1List[0].stockName']").keypress(function (e) {
                if (e.keyCode == 13) {
                    selectStockDialog(0,"tScIcXsstockbillentry1");
                }
            });
            setFunctionInfo(0,"tScIcXsstockbillentry1List");
            $("#unitId1\\[0\\]").combobox({
                width:54,
                valueField: 'id',
                textField: 'text',
                panelHeight: 'auto',
                editable: false,
                onChange:function(newV,oldV){
                    if(oldV != newV) {
                        var index = $(this)[0].id.replace(/[^0-9]/ig,"");
                        index = index.substring(1,index.length);
                        var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV+"&rowIndex="+index;
                        $.ajax({
                            url: changeUrl,
                            dataType: 'json',
                            cache: false,
                            success: function (data) {
                                var attributes = data.attributes;
                                var cofficient = attributes.coefficient;
                                var barCode = attributes.barCode;
                                var rowIndex = attributes.rowIndex;
                                $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].barCode']").val(barCode);
                                $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].coefficient']").val(cofficient);
                                $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].coefficient']").trigger("propertychange");
                                $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].secCoefficient']").trigger("propertychange");
                            }
                        });
                    }
                }
            })
//			$("input[name='tScIcXsstockbillentry1List[0].unitName']").keypress(function(e){
//				if(e.keyCode == 13){
//					selectUnitDialog(0);
//				}
//			})
        }else{
            var checkState = $("#checkState").val();
            var $tbody = $("#tScIcXsstockbillentry1_table");
            var length = $tbody[0].rows.length;
            rowIndex = length-1;
            //变更功能配置
            for(var j=0 ; j<length ; j++){
                //编辑行金额函数配置
                if("detail" != "${load}") {
                    setFunctionInfo(j, "tScIcXsstockbillentry1List");
                }
                $("select[name='tScIcXsstockbillentry1List["+j+"].kfDateType']").attr("onChange","setPeriodDate("+j+",'tScIcXsstockbillentry1List')");
                checkAllowAddLine(j);//判断是否允许增行操作
                var unitId = $("#unitId1\\["+j+"\\]").val();
                var itemId = $("input[name='tScIcXsstockbillentry1List["+j+"].itemId']").val();
                var itemNo = $("input[name='tScIcXsstockbillentry1List["+j+"].itemNo']").val();
                setValOldIdAnOldName($("input[name='tScIcXsstockbillentry1List["+j+"].itemNo']"),itemId,itemNo);
                var idSrc = $("input[name='tScIcXsstockbillentry1List["+j+"].idSrc']").val();
                var stockId = $("input[name='tScIcXsstockbillentry1List["+j+"].stockId']").val();
                if(stockId){
                    var stockName = $("input[name='tScIcXsstockbillentry1List["+j+"].stockName']").val();
                    setValOldIdAnOldName($("input[name='tScIcXsstockbillentry1List["+j+"].stockName']"),stockId,stockName);
                }
//				var entryOrderId = $("input[name='tScIcXsstockbillentry1List["+j+"].entryIdOrder']").val();
//				if(entryOrderId){
//					$("select[name='tScIcXsstockbillentry1List["+j+"].freeGifts_']").attr("disabled","disabled");
//					$("select[name='tScIcXsstockbillentry1List["+j+"].freeGifts_']").css("background-color","rgb(226,226,226)");
//				}
                var batchManager = $("input[name='tScIcXsstockbillentry1List["+j+"].batchManager']").val();
                if("N"==batchManager){
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].batchNo']").attr("readonly","readonly");
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].batchNo']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].batchNo']").removeAttr("datatype");
                } else {
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].batchNo']").attr("onkeypress","keyDownInfoI('"+j+"','batchNo')")
                }
                var isKfPeriod = $("input[name='tScIcXsstockbillentry1List["+j+"].isKFPeriod']").val();
                if("N"==isKfPeriod) {
                    //生产日期
                    $("input[ame='tScIcXsstockbillentry1List[" + j + "].kfDate']").attr("readonly","readonly");
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].kfDate']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].kfDate']").attr("onClick","");
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].kfDate']").val("");
                    //保质期
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].kfPeriod']").attr("readonly","readonly");
                    $("input[name='tScIcXsstockbillentry1List[" + j + "].kfPeriod']").css("background-color","rgb(226,226,226)");
                    //保质期类型
                    $("select[name='tScIcXsstockbillentry1List[" + j + "].kfDateType_']").attr("disabled", "disabled");
                    $("select[name='tScIcXsstockbillentry1List[" + j + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
                }
                $("#unitId1\\["+j+"\\]").combobox({
                    onChange:function(newV,oldV){
                        if(oldV != newV) {
                            var index = $(this)[0].id.replace(/[^0-9]/ig,"");
                            index = index.substring(1,index.length);
                            var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV+"&rowIndex="+index;
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
                                    $("input[name='tScIcXsstockbillentry1List[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);
                                    $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].barCode']").val(barCode);
                                    $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].coefficient']").val(cofficient);
                                    $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].coefficient']").trigger("propertychange");
                                    $("input[name='tScIcXsstockbillentry1List["+rowIndex+"].secCoefficient']").trigger("propertychange");
                                }
                            });
                        }
                    }
                })
                if(idSrc){
                    $("input[name='tScIcXsstockbillentry1List["+j+"].itemNo']").attr("disabled","disabled");
                    $("input[name='tScIcXsstockbillentry1List["+j+"].itemNo']").css("background-color","rgb(226,226,226)");
                    $("input[name='tScIcXsstockbillentry1List["+j+"].stockName']").attr("disabled","disabled");
                    $("input[name='tScIcXsstockbillentry1List["+j+"].stockName']").css("background-color","rgb(226,226,226)");
                    $("#unitId1\\["+j+"\\]").combobox({
                        disabled:true
                    })
                    $("#itemName").attr("disabled","disabled");
                    //$("#itemName").css("background-color","rgb(226,226,226)");
                }
            }
            //	}
        }
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScIcXsstockbillentry1_table" totalRow="true" style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
        <td align="center" bgcolor="#476f9a">序号</td>
        <c:if test="${load ne 'detail'}">
        <td align="center" bgcolor="#476f9a">操作</td>
        </c:if>
        <td align="center" bgcolor="#476f9a">
            商品编号<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            商品名称<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            规格
        </td>
        <td align="center" bgcolor="#476f9a">
            条形码
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
            折扣率
        </td>
        <td align="center" bgcolor="#476f9a">
            折后单价
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            折后金额
        </td>
        <td align="center" bgcolor="#476f9a">
            税率
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
    <tbody id="add_tScIcXsstockbillentry1_table">
    <c:if test="${fn:length(tScIcXsstockbillentry1List)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 25px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                    <a name="addTScIcXsstockbillentry1Btn[0]" id="addTScIcXsstockbillentry1Btn[0]" href="#"
                       onclick="clickAddTScIcXsstockbillentry1Btn(0);"></a>
                    <a name="delTScIcXsstockbillentry1Btn[0]"
                       id="delTScIcXsstockbillentry1Btn[0]" href="#"
                       onclick="clickDelTScIcXsstockbillentry1Btn(0);"></a>
                </div>
            </td>
            <input name="tScIcXsstockbillentry1List[0].id" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].createName" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].createBy" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].createDate" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].updateName" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].updateBy" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].updateDate" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].fid" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].findex" value="1" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].basicUnitId" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].coefficient" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].basicQty" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].secUnitId" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].secCoefficient" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].secQty" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].price" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].amount" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].discountPrice" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].discountAmount" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].idSrc" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].entryIdSrc" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].idOrder" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].entryIdOrder" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].costPrice" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].costAmount" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].itemId" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].stockId" type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].basicCoefficient"  type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].batchManager"  type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].isKFPeriod"  type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].xsLimitPrice"  type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].kfDateType"  type="hidden"/>
            <input name="tScIcXsstockbillentry1List[0].classIDSrc"  type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].itemNo" maxlength="32"
                       type="text" class="inputxt popup-select" datatype="*"
                       onkeypress="keyDownInfoI(0,'item',event)" onblur="orderListStockBlur('0','itemId','itemNo','tScIcXsstockbillentry1List');"
                       style="width:105px;">
                <label class="Validform_label" style="display: none;">商品编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].itemName" maxlength="32"
                       type="text" class="inputxt" readonly="readonly" style="width:180px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">商品名称</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].model" maxlength="32"
                       type="text" class="inputxt" readonly="readonly"  style="width:85px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">规格</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].barCode" maxlength="32"
                       type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">条形码</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].stockName" maxlength="32" datatype="*"
                       onblur="orderListStockBlur('0','stockId','stockName','tScIcXsstockbillentry1List');"
                       type="text" class="inputxt popup-select" style="width:65px;">
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].batchNo" maxlength="100"
                       type="text" class="inputxt" style="width:80px;" datatype="*">
                <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                <input id="unitId1[0]" name="tScIcXsstockbillentry1List[0].unitId" maxlength="32"
                       type="text" class="easyui-combobox"
                       data-options="valueField: 'id',width:54,textField: 'text',panelHeight: 'auto',editable: false"
                       style="width:50px;">
                <label class="Validform_label" style="display: none;">单位</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].qty" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num">
                <label class="Validform_label" style="display: none;">数量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].taxPriceEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num">
                <label class="Validform_label" style="display: none;">单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].taxAmountEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num">
                <label class="Validform_label" style="display: none;">金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].discountRate" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" value="100" datatype="num">
                <label class="Validform_label" style="display: none;">折扣率</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].taxPrice" maxlength="20"
                       type="text" class="inputxt" readonly="readonly" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">折后单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].inTaxAmount" maxlength="20"
                       onchange="setAllAmount()"
                       type="text" class="inputxt" style="width:70px;" datatype="num">
                <label class="Validform_label" style="display: none;">折后金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].taxRate" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num">
                <label class="Validform_label" style="display: none;">税率</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].taxAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">税额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].kfDate" maxlength="20" onchange="setPeriodDate(0,'tScIcXsstockbillentry1List')"
                       type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                        >
                <label class="Validform_label" style="display: none;">生产日期</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].kfPeriod" maxlength="10"
                       onchange="setPeriodDate(0,'tScIcXsstockbillentry1List')" readonly="readonly"
                       type="text" datatype="num" ignore="ignore" class="inputxt" style="width:50px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">保质期</label>
            </td>
            <td align="left" bgcolor="white">
                <t:dictSelect field="tScIcXsstockbillentry1List[0].kfDateType_" type="list" width="70" extendJson="{disabled:disabled}"
                              typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="0" hasLabel="false"  title="保质期类型"></t:dictSelect>
                <label class="Validform_label" style="display: none;">保质期类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].periodDate" maxlength="20"
                       type="text" class="Wdate" readonly="readonly" style="width:80px;background-color: rgb(226,226,226)"
                        >
                <label class="Validform_label" style="display: none;">有效期至</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].classIDName" maxlength="32"
                       type="text" class="inputxt" readonly="readonly" style="width:90px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">源单类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].billNoSrc" maxlength="50"
                       type="text" class="inputxt" readonly="readonly" style="width:90px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">源单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].billNoOrder" maxlength="50"
                       type="text" class="inputxt" readonly="readonly" style="width:90px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">订单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcXsstockbillentry1List[0].note" maxlength="255"
                       type="text" class="inputxt" style="width:180px;">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScIcXsstockbillentry1List)  > 0 }">
        <c:forEach items="${tScIcXsstockbillentry1List}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 80px;background-color: white;">
                        <a name="addTScIcXsstockbillentry1Btn[${stuts.index}]"
                           id="addTScIcXsstockbillentry1Btn[${stuts.index}]" href="#"
                           onclick="clickAddTScIcXsstockbillentry1Btn(${stuts.index});"></a>

                        <a name="delTScIcXsstockbillentry1Btn[${stuts.index}]"
                           id="delTScIcXsstockbillentry1Btn[${stuts.index}]" href="#"
                           onclick="clickDelTScIcXsstockbillentry1Btn(${stuts.index});"></a>
                    </div>
                </td>
                </c:if>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].createBy" type="hidden"
                       value="${poVal.createBy }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].updateBy" type="hidden"
                       value="${poVal.updateBy }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].findex" type="hidden"
                       value="${poVal.findex }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].basicUnitId" type="hidden"
                       value="${poVal.basicUnitId }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].coefficient" type="hidden"
                       value="${poVal.coefficient }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].basicQty" type="hidden"
                       value="${poVal.basicQty }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].secUnitId" type="hidden"
                       value="${poVal.secUnitId }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].secCoefficient" type="hidden"
                       value="${poVal.secCoefficient }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].secQty" type="hidden"
                       value="${poVal.secQty }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].price" type="hidden" value="${poVal.price }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].amount" type="hidden"
                       value="${poVal.amount }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].discountPrice" type="hidden"
                       value="${poVal.discountPrice }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].discountAmount" type="hidden"
                       value="${poVal.discountAmount }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].idSrc" type="hidden" value="${poVal.idSrc }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].entryIdSrc" type="hidden"
                       value="${poVal.entryIdSrc }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].idOrder" type="hidden"
                       value="${poVal.idOrder }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].entryIdOrder" type="hidden"
                       value="${poVal.entryIdOrder }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].costPrice" type="hidden"
                       value="${poVal.costPrice }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].costAmount" type="hidden"
                       value="${poVal.costAmount }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].itemId" type="hidden"
                       value="${poVal.itemId }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].stockId" type="hidden"
                       value="${poVal.stockId }"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].basicCoefficient" value="${poVal.basicCoefficient}" type="hidden"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].batchManager" value="${poVal.batchManager}" type="hidden"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].isKFPeriod" value="${poVal.isKFPeriod}" type="hidden"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].xsLimitPrice" value="${poVal.xsLimitPrice}" type="hidden"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].kfDateType" value="${poVal.kfDateType}" type="hidden"/>
                <input name="tScIcXsstockbillentry1List[${stuts.index }].classIDSrc" value="${poVal.classIDSrc}"  type="hidden"/>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].itemNo" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                           onkeypress="keyDownInfoI(${stuts.index },'item',event)" onblur="orderListStockBlur('${stuts.index }','itemId','itemNo','tScIcXsstockbillentry1List');"
                           value="${poVal.itemNo }">
                    <label class="Validform_label" style="display: none;">商品编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].itemName" maxlength="32"
                           type="text" class="inputxt" readonly="readonly"  style="width:180px;background-color: rgb(226,226,226)"
                           value="${poVal.itemName }">
                    <label class="Validform_label" style="display: none;">商品名称</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].model" maxlength="32"
                           type="text" class="inputxt" readonly="readonly"  style="width:85px;background-color: rgb(226,226,226)"
                           value="${poVal.model }">
                    <label class="Validform_label" style="display: none;">规格</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].barCode" maxlength="32"
                           type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color: rgb(226,226,226)"
                           value="${poVal.barCode }">
                    <label class="Validform_label" style="display: none;">条形码</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].stockName" maxlength="32" datatype="*"
                           onblur="orderListStockBlur('${stuts.index }','stockId','stockName','tScIcXsstockbillentry1List');"
                           type="text" class="inputxt popup-select" style="width:65px;"
                           value="${poVal.stockName }">
                    <label class="Validform_label" style="display: none;">仓库</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].batchNo" maxlength="100"
                           type="text" class="inputxt" style="width:80px;"
                           datatype="*" value="${poVal.batchNo }">
                    <label class="Validform_label" style="display: none;">批号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input id="unitId1[${stuts.index }]" name="tScIcXsstockbillentry1List[${stuts.index }].unitId" maxlength="32"
                           type="text"
                           class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"
                           style="width:50px;"
                           value="${poVal.unitId }">
                    <label class="Validform_label" style="display: none;">单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].qty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" value="${poVal.qty }">
                    <label class="Validform_label" style="display: none;">数量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].taxPriceEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" value="${poVal.taxPriceEx }">
                    <label class="Validform_label" style="display: none;">单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].taxAmountEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" value="${poVal.taxAmountEx }">
                    <label class="Validform_label" style="display: none;">金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].discountRate" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" value="${poVal.discountRate }">
                    <label class="Validform_label" style="display: none;">折扣率</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].taxPrice" maxlength="20"
                           type="text" class="inputxt" readonly="readonly" style="width:70px;background-color: rgb(226,226,226)"
                           value="${poVal.taxPrice }">
                    <label class="Validform_label" style="display: none;">折后单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].inTaxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           onchange="setAllAmount()"
                           datatype="num" value="${poVal.inTaxAmount }">
                    <label class="Validform_label" style="display: none;">折后金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].taxRate" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"
                           datatype="num" value="${poVal.taxRate }">
                    <label class="Validform_label" style="display: none;">税率</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].taxAmount" maxlength="20"
                           type="text" class="inputxt" readonly="readonly" style="width:70px;background-color: rgb(226,226,226)"
                           value="${poVal.taxAmount }">
                    <label class="Validform_label" style="display: none;">税额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].kfDate" maxlength="20"
                           type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"

                           value="<fmt:formatDate value='${poVal.kfDate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">生产日期</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].kfPeriod" maxlength="10" readonly="readonly"
                           type="text" class="inputxt" style="width:50px;background-color: rgb(226,226,226)" datatype="num" ignore="ignore"
                           onchange="setPeriodDate(${stuts.index },'tScIcXsstockbillentry1List')"
                           value="${poVal.kfPeriod }">
                    <label class="Validform_label" style="display: none;">保质期</label>
                </td>
                <td align="left" bgcolor="white">
                    <t:dictSelect width="70" field="tScIcXsstockbillentry1List[${stuts.index }].kfDateType_" type="list" extendJson="{disabled:disabled}"
                                  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType }" hasLabel="false"  title="保质期类型"></t:dictSelect>
                    <label class="Validform_label" style="display: none;">保质期类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].periodDate" maxlength="20"
                           type="text" class="Wdate" readonly="readonly" style="width:80px;background-color: rgb(226,226,226)"

                           value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">有效期至</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].classIDName" maxlength="32"
                           type="text" class="inputxt" readonly="readonly" style="width:90px;background-color: rgb(226,226,226)"
                           value="${poVal.classIDName }">
                    <label class="Validform_label" style="display: none;">源单类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].billNoSrc" maxlength="50"
                           type="text" class="inputxt" readonly="readonly" style="width:90px;background-color: rgb(226,226,226)"
                           value="${poVal.billNoSrc }">
                    <label class="Validform_label" style="display: none;">源单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].billNoOrder" maxlength="50"
                           type="text" class="inputxt" readonly="readonly" style="width:90px;background-color: rgb(226,226,226)"
                           value="${poVal.billNoOrder }">
                    <label class="Validform_label" style="display: none;">订单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcXsstockbillentry1List[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:180px;"
                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
