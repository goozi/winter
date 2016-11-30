<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('#addTScIcJhstockbillentry2Btn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delTScIcJhstockbillentry2Btn').linkbutton({
        iconCls: 'icon-remove'
    });

    function clickAddTScIcJhstockbillentry2Btn(index, entryName) {
        var tr = $("#add_tScIcJhstockbillentry2_table_template tr").clone();
        $("#add_tScIcJhstockbillentry2_table tr").eq(index).after(tr);
        rowIndex++;
        resetTrNum('add_tScIcJhstockbillentry2_table', index, "tScIcJhstockbillentry2List",1);
    }
    function clickDelTScOrderentryBtnI(index) {
//		var stockQty = $("input[name='tScIcJhstockbillentry2List["+index+"].stockQty']").val();
//		if(stockQty > 0){
//			tip("该商品已被执行，不可删除");
//			return;
//		}
        $("#add_tScIcJhstockbillentry2_table").find(">tr").eq(index).remove();
        var length = $("#add_tScIcJhstockbillentry2_table").find(">tr").length;
        if (length == 0) {
            //clickAddEntryBtn(0)
            var tr = $("#add_tScIcJhstockbillentry2_table_template tr").clone();
            $("#add_tScIcJhstockbillentry2_table").append(tr);
            resetTrNum('add_tScIcJhstockbillentry2_table', -1, "tScIcJhstockbillentry2List");
            $("#unitId2\\[" + 0 + "\\]").combobox({
                width:54,
                valueField: "id",
                textField: "text",
                panelHeight: "auto",
                editable: false
            })
        } else {
            resetTrNum('add_tScIcJhstockbillentry2_table', index, "tScIcJhstockbillentry2List");
        }
        rowIndex--;
    }
    function keyDownInfo(index, name, evt) {
        var evt = (evt) ? evt : ((window.event) ? window.event : "");
        var code = evt.keyCode ? evt.keyCode : evt.which;
        if (code == 13) {
            if (name == "stock") {
                selectStockDialog(index, "tScIcJhstockbillentry2");
            } else if (name == "item") {
                selectIcitemDialog(index, "tScIcJhstockbillentry2List");
            } else if (name == "unit") {
                selectUnitDialog(index);
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
        $("#tScIcJhstockbillentry2_table").createhftable({
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

        $("#add_tScIcJhstockbillentry2_table tr").each(function (i, data) {
            $('#addTScOrderentryBtn2\\[' + i + '\\]').linkbutton({
                iconCls: 'icon-add',
                plain: 'true'
            });
            $('#delTScOrderentryBtn2\\[' + i + '\\]').linkbutton({
                iconCls: 'icon-remove',
                plain: 'true'
            });
        });

        if (${fn:length(tScIcJhstockbillentry2List)  <= 0 }) {
            checkAllowAddLine(0);//判断是否允许增行操作
            var billDate = $("#date").val();
            var taxRate = $("#CFG_TAX_RATE").val();//默认税率
            $("input[name='tScIcJhstockbillentry2List[0].taxRate']").val(taxRate);
            $("input[name='tScIcJhstockbillentry2List[#incex#].taxRate']").val(taxRate);
            $("input[name='tScIcJhstockbillentry2List[0].kfDate']").val(billDate);
            $("select[name='tScIcJhstockbillentry2List[0].kfDateType']").attr("onChange", "setPeriodDate(0,'tScIcJhstockbillentry2List')");
            $("input[name='tScIcJhstockbillentry2List[0].stockName']").keypress(function (e) {
                if (e.keyCode == 13) {
                    selectStockDialog(0, "tScIcJhstockbillentry2");
                }
            });
            setFunctionInfo(0, "tScIcJhstockbillentry2List");
            $("#unitId2\\[0\\]").combobox({
                width: 54,
                valueField: 'id',
                textField: 'text',
                panelHeight: 'auto',
                editable: false,
                onChange: function (newV, oldV) {
                    if (oldV != newV) {
                        var index = $(this)[0].id.replace(/[^0-9]/ig, "");
                        index = index.substring(1, index.length);
                        var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV + "&rowIndex=" + index;
                        $.ajax({
                            url: changeUrl,
                            dataType: 'json',
                            cache: false,
                            success: function (data) {
                                var attributes = data.attributes;
                                var cofficient = attributes.coefficient;
                                var barCode = attributes.barCode;
                                var rowIndex = attributes.rowIndex;
                                var cgLimitPrice = attributes.cgLimitPrice;
                                $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);
                                $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].barCode']").val(barCode);
                                $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].coefficient']").val(cofficient);
                                $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].coefficient']").trigger("propertychange");
                                $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].secCoefficient']").trigger("propertychange");
                            }
                        });
                    }
                }
            })
//			$("input[name='tScIcJhstockbillentry2List[0].unitName']").keypress(function(e){
//				if(e.keyCode == 13){
//					selectUnitDialog(0);
//				}
//			})
        } else {
            var checkState = $("#checkState").val();
            var taxRate = $("#CFG_TAX_RATE").val();//默认税率
            $("input[name='tScIcJhstockbillentry2List[#incex#].taxRate']").val(taxRate);
            var $tbody = $("#tScIcJhstockbillentry2_table");
            var length = $tbody[0].rows.length;
            rowIndex = length - 1;
            //变更功能配置
            for (var j = 0; j < length; j++) {
                //编辑行金额函数配置
                if("detail" != "${load}") {
                    setFunctionInfo(j, "tScIcJhstockbillentry2List");
                }
                $("select[name='tScIcJhstockbillentry2List[" + j + "].kfDateType']").attr("onChange", "setPeriodDate(0,'tScIcJhstockbillentry2List')");
                checkAllowAddLine(j);//判断是否允许增行操作
                var unitId = $("#unitId2\\[" + j + "\\]").val();
                var itemId = $("input[name='tScIcJhstockbillentry2List[" + j + "].itemId']").val();
                var itemNo = $("input[name='tScIcJhstockbillentry2List[" + j + "].itemNo']").val();
                setValOldIdAnOldName($("input[name='tScIcJhstockbillentry2List[" + j + "].itemNo']"), itemId, itemNo);
                var stockId = $("input[name='tScIcJhstockbillentry2List[" + j + "].stockId']").val();
                if (stockId) {
                    var stockName = $("input[name='tScIcJhstockbillentry2List[" + j + "].stockName']").val();
                    setValOldIdAnOldName($("input[name='tScIcJhstockbillentry2List[" + j + "].stockName']"), stockId, stockName);
                }
                var entryOrderId = $("input[name='tScIcJhstockbillentry2List[" + j + "].entryIdOrder']").val();
                if (entryOrderId) {
                    $("select[name='tScIcJhstockbillentry2List[" + j + "].freeGifts_']").attr("disabled", "disabled");
                    $("select[name='tScIcJhstockbillentry2List[" + j + "].freeGifts_']").css("background-color", "rgb(226,226,226)");
                }
                var batchManager = $("input[name='tScIcJhstockbillentry2List[" + j + "].batchManager']").val();
                if ("N" == batchManager) {
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].batchNo']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].batchNo']").css("background-color", "rgb(226,226,226)");
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].batchNo']").removeAttr("datatype");
                }else{
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].batchNo']").attr("onkeypress","keyDownInfoI('"+j+"','batchNo2')");
                }
                var isKfPeriod = $("input[name='tScIcJhstockbillentry2List[" + j + "].isKFPeriod']").val();
                debugger;
                if ("N" == isKfPeriod) {
                    //生产日期
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].kfDate']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].kfDate']").css("background-color", "rgb(226,226,226)");
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].kfDate']").attr("onClick", "");
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].kfDate']").val("");
                    //保质期
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].kfPeriod']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry2List[" + j + "].kfPeriod']").css("background-color", "rgb(226,226,226)");
                    //保质期类型
                    $("select[name='tScIcJhstockbillentry2List[" + j + "].kfDateType']").attr("disabled", "disabled");
                    $("select[name='tScIcJhstockbillentry2List[" + j + "].kfDateType']").css("background-color", "rgb(226,226,226)");
                }
                $("#unitId2\\[" + j + "\\]").combobox({
                    onChange: function (newV, oldV) {
                        if (oldV != newV) {
                            var index = $(this)[0].id.replace(/[^0-9]/ig, "");
                            var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV + "&rowIndex=" + index;
                            $.ajax({
                                url: changeUrl,
                                dataType: 'json',
                                cache: false,
                                success: function (data) {
                                    var attributes = data.attributes;
                                    var cofficient = attributes.coefficient;
                                    var barCode = attributes.barCode;
                                    var rowIndex = attributes.rowIndex;
                                    var cgLimitPrice = attributes.cgLimitPrice;
                                    $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);
                                    $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].barCode']").val(barCode);
                                    $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].coefficient']").val(cofficient);
                                    $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].coefficient']").trigger("propertychange");
                                    $("input[name='tScIcJhstockbillentry2List[" + rowIndex + "].secCoefficient']").trigger("propertychange");
                                }
                            });
                        }
                    }
                })
            }
            //	}
        }
    });
</script>
<%--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">--%>
<%--<a id="addTScIcJhstockbillentry2Btn" href="#">添加</a> <a id="delTScIcJhstockbillentry2Btn" href="#">删除</a> --%>
<%--</div>--%>
<table border="0" cellpadding="2" cellspacing="1" id="tScIcJhstockbillentry2_table" totalRow="true" style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; ">
        <td align="center" bgcolor="#476f9a">序号</td>
        <c:if test="${load ne 'detail'}">
        <td align="center" bgcolor="#476f9a">操作</td>
        </c:if>
        <td align="left" bgcolor="#476f9a">
            商品编号<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            商品名称
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
        <%--<td align="center" bgcolor="#476f9a">--%>
            <%--换算率--%>
        <%--</td>--%>
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
        <td align="center" bgcolor="#476f9a">
            备注
        </td>
    </tr>
    <tbody id="add_tScIcJhstockbillentry2_table">
    <c:if test="${fn:length(tScIcJhstockbillentry2List)  <= 0 }">
        <tr>
            <td align="center" bgcolor="white">
                <div style="width: 25px;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;"><a name="addTScOrderentryBtn2[0]" id="addTScOrderentryBtn2[0]" href="#"
                                             class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                                             onclick="clickAddTScIcJhstockbillentry2Btn(0,'tScIcJhstockbillentry2');"></a><a
                        name="delTScOrderentryBtn2[0]" id="delTScOrderentryBtn2[0]" href="#" class="easyui-linkbutton"
                        data-options="iconCls:'icon-remove'" plain="true"
                        onclick="clickDelTScOrderentryBtnI(0,'tScIcJhstockbillentry2');"></a></div>
            </td>
            <input name="tScIcJhstockbillentry2List[0].id" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].createName" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].createBy" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].createDate" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].updateName" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].updateBy" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].updateDate" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].basicUnitId" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].basicQty" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].secUnitId" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].secCoefficient" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].secQty" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].price" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].amount" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].discountPrice" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].discountAmount" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].fid" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].findex" value="1" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].costPrice" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].costAmount" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].itemId" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].stockId" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].basicCoefficient" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].batchManager" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].isKFPeriod" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].xsLimitPrice" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].coefficient" type="hidden"/>
            <input name="tScIcJhstockbillentry2List[0].kfDateType" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].itemNo" maxlength="32"
                       type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                       onkeypress="keyDownInfo(0,'item',event)"
                       onblur="orderListStockBlur('0','itemId','itemNo','tScIcJhstockbillentry2List');"
                        >
                <label class="Validform_label" style="display: none;">商品编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].itemName" maxlength="32"
                       type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">商品名称</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].model" maxlength="32"
                       type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">规格</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].barCode" maxlength="32"
                       type="text" class="inputxt" style="width:100px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">条形码</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].stockName" maxlength="32" datatype="*"
                       type="text" class="inputxt popup-select" style="width:65px;"
                       onblur="orderListStockBlur('0','stockId','stockName','tScIcJhstockbillentry2List');"
                        >
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].batchNo" maxlength="100"
                       type="text" class="inputxt" style="width:80px;"

                        >
                <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                <input id="unitId2[0]" name="tScIcJhstockbillentry2List[0].unitId" maxlength="32"
                       type="text" class="easyui-combobox"
                       data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false"
                       style="width:50px;"

                        >
                <label class="Validform_label" style="display: none;">单位</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].qty" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="vInt"

                        >
                <label class="Validform_label" style="display: none;">数量</label>
            </td>
                <%--<td align="left" bgcolor="white">--%>
                <%--<input name="tScIcJhstockbillentry2List[0].coefficient" maxlength="20" --%>
                <%--type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"--%>
                <%----%>
                <%-->--%>
                <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
                <%--</td>--%>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].taxPriceEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num"

                        >
                <label class="Validform_label" style="display: none;">单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].taxAmountEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;"

                        >
                <label class="Validform_label" style="display: none;">金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].discountRate" maxlength="20"
                       type="text" class="inputxt" style="width:80px;" value="100"

                        >
                <label class="Validform_label" style="display: none;">折扣率（%）</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].taxPrice" maxlength="20"
                       type="text" class="inputxt" style="width:80px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">折后单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].inTaxAmount" maxlength="20"
                       type="text" class="inputxt" style="width:80px;"
                       onchange="setAllAmount()"
                        >
                <label class="Validform_label" style="display: none;">折后金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].taxRate" maxlength="20"
                       type="text" class="inputxt" style="width:80px;"

                        >
                <label class="Validform_label" style="display: none;">税率（%）</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].taxAmount" maxlength="20"
                       type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">税额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].kfDate" maxlength="20"
                       type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                       onchange="setPeriodDate(0,'tScIcJhstockbillentry2List')"
                        >
                <label class="Validform_label" style="display: none;">生产日期</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].kfPeriod" maxlength="10"
                       type="text" class="inputxt" style="width:50px;background-color:rgb(226,226,226)" readonly="readonly"
                       onchange="setPeriodDate(0,'tScIcJhstockbillentry2List')"
                        >
                <label class="Validform_label" style="display: none;">保质期</label>
            </td>
            <td align="left" bgcolor="white">
                <t:dictSelect width="70" field="tScIcJhstockbillentry2List[0].kfDateType_" type="list" extendJson="{disabled:disabled}"
                              typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${tScIcJhstockbillentry2Page.kfDateType}"
                              hasLabel="false" title="保质期类型"></t:dictSelect>
                <label class="Validform_label" style="display: none;">保质期类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].periodDate" maxlength="20"
                       type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">有效期至</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry2List[0].note" maxlength="255"
                       type="text" class="inputxt" style="width:180px;"

                        >
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScIcJhstockbillentry2List)  > 0 }">
        <c:forEach items="${tScIcJhstockbillentry2List}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 80px;"><a name="addTScOrderentryBtn2[${stuts.index }]"
                                                 id="addTScOrderentryBtn2[${stuts.index }]" href="#"
                                                 class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                                                 plain="true"
                                                 onclick="clickAddTScIcJhstockbillentry2Btn(${stuts.index },'tScIcJhstockbillentry2');"></a><a
                            name="delTScOrderentryBtn2[${stuts.index }]" id="delTScOrderentryBtn2[${stuts.index }]"
                            href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                            onclick="clickDelTScOrderentryBtnI(${stuts.index });"></a></div>
                </td>
                </c:if>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].createBy" type="hidden"
                       value="${poVal.createBy }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].updateBy" type="hidden"
                       value="${poVal.updateBy }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].basicUnitId" type="hidden"
                       value="${poVal.basicUnitId }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].basicQty" type="hidden"
                       value="${poVal.basicQty }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].secUnitId" type="hidden"
                       value="${poVal.secUnitId }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].secCoefficient" type="hidden"
                       value="${poVal.secCoefficient }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].secQty" type="hidden"
                       value="${poVal.secQty }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].price" type="hidden" value="${poVal.price }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].amount" type="hidden"
                       value="${poVal.amount }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].discountPrice" type="hidden"
                       value="${poVal.discountPrice }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].discountAmount" type="hidden"
                       value="${poVal.discountAmount }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].findex" type="hidden"
                       value="${poVal.findex }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].costPrice" value="${poVal.costPrice}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].costAmount" value="${poVal.costAmount}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].itemId" type="hidden"
                       value="${poVal.itemId }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].stockId" type="hidden"
                       value="${poVal.stockId }"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].basicCoefficient"
                       value="${poVal.basicCoefficient}" type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].batchManager" value="${poVal.batchManager}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].isKFPeriod" value="${poVal.isKFPeriod}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].xsLimitPrice" value="${poVal.xsLimitPrice}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].coefficient" value="${poVal.coefficient}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry2List[${stuts.index }].kfDateType" value="${poVal.kfDateType}"
                       type="hidden"/>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].itemNo" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                           onkeypress="keyDownInfo(${stuts.index },'item',event)"
                           onblur="orderListStockBlur('${stuts.index }','itemId','itemNo','tScIcJhstockbillentry2List');"
                           value="${poVal.itemNo }">
                    <label class="Validform_label" style="display: none;">商品编码</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].itemName" maxlength="32"
                           type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.itemName }">
                    <label class="Validform_label" style="display: none;">商品名称</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].model" maxlength="32"
                           type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.model }">
                    <label class="Validform_label" style="display: none;">规格</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].barCode" maxlength="32"
                           type="text" class="inputxt" style="width:100px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.barCode }">
                    <label class="Validform_label" style="display: none;">条形码</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].stockName" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                           onkeypress="keyDownInfo(${stuts.index },'stock',event)"
                           onblur="orderListStockBlur('${stuts.index }','stockId','stockName','tScIcJhstockbillentry2List');"
                           value="${poVal.stockName }">
                    <label class="Validform_label" style="display: none;">仓库</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].batchNo" maxlength="100"
                           type="text" class="inputxt" style="width:80px;"

                           value="${poVal.batchNo }">
                    <label class="Validform_label" style="display: none;">批号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input id="unitId2[${stuts.index }]" name="tScIcJhstockbillentry2List[${stuts.index }].unitId"
                           maxlength="32"
                           type="text" style="width:50px;"
                           class="easyui-combobox"
                           data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',width:54,editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"
                           value="${poVal.unitId }">
                    <label class="Validform_label" style="display: none;">单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].qty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;" datatype="vInt"

                           value="${poVal.qty }">
                    <label class="Validform_label" style="display: none;">数量</label>
                </td>
                <%--<td align="left" bgcolor="white">--%>
                    <%--<input name="tScIcJhstockbillentry2List[${stuts.index }].coefficient" maxlength="20"--%>
                           <%--type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)"--%>
                           <%--readonly="readonly"--%>

                           <%--value="${poVal.coefficient }">--%>
                    <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
                <%--</td>--%>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].taxPriceEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;" datatype="num"

                           value="${poVal.taxPriceEx }">
                    <label class="Validform_label" style="display: none;">单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].taxAmountEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"

                           value="${poVal.taxAmountEx }">
                    <label class="Validform_label" style="display: none;">金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].discountRate" maxlength="20"
                           type="text" class="inputxt" style="width:80px;"

                           value="${poVal.discountRate }">
                    <label class="Validform_label" style="display: none;">折扣率（%）</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].taxPrice" maxlength="20"
                           type="text" class="inputxt" style="width:80px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.taxPrice }">
                    <label class="Validform_label" style="display: none;">折后单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].inTaxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:80px;"
                           onchange="setAllAmount()"
                           value="${poVal.taxAmount }">
                    <label class="Validform_label" style="display: none;">折后金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].taxRate" maxlength="20"
                           type="text" class="inputxt" style="width:80px;"

                           value="${poVal.taxRate }">
                    <label class="Validform_label" style="display: none;">税率（%）</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].taxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.taxAmount }">
                    <label class="Validform_label" style="display: none;">税额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].kfDate" maxlength="20"
                           type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"

                           value="<fmt:formatDate value='${poVal.kfDate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">生产日期</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].kfPeriod" maxlength="10"
                           type="text" class="inputxt" style="width:50px;background-color:rgb(226,226,226)" readonly="readonly"

                           value="${poVal.kfPeriod }">
                    <label class="Validform_label" style="display: none;">保质期</label>
                </td>
                <td align="left" bgcolor="white">
                    <t:dictSelect width="70" field="tScIcJhstockbillentry2List[${stuts.index }].kfDateType_" extendJson="{disabled:disabled}" type="list"
                                  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType }" hasLabel="false"
                                  title="保质期类型"></t:dictSelect>
                    <label class="Validform_label" style="display: none;">保质期类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].periodDate" maxlength="20"
                           type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">有效期至</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry2List[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:180px;"

                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
