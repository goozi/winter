<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('#addTScIcJhstockbillentry1Btn').linkbutton({
        iconCls: 'icon-add'
    });
    $('#delTScIcJhstockbillentry1Btn').linkbutton({
        iconCls: 'icon-remove'
    });
    //	$('#addTScIcJhstockbillentry1Btn').bind('click', function(){
    // 		 var tr =  $("#add_tScIcJhstockbillentry1_table_template tr").clone();
    //	 	 $("#add_tScIcJhstockbillentry1_table").append(tr);
    //	 	 resetTrNum('add_tScIcJhstockbillentry1_table');
    //	 	 return false;
    //    });
    //	$('#delTScIcJhstockbillentry1Btn').bind('click', function(){
    //      	$("#add_tScIcJhstockbillentry1_table").find("input:checked").parent().parent().remove();
    //        resetTrNum('add_tScIcJhstockbillentry1_table');
    //        return false;
    //    });
    function clickAddTScIcJhstockbillentry1Btn(index, entryName) {
        var tr = $("#add_tScIcJhstockbillentry1_table_template tr").clone();
        $("#add_tScIcJhstockbillentry1_table tr").eq(index).after(tr);
        rowIndex++;
        resetTrNum('add_tScIcJhstockbillentry1_table', index, "tScIcJhstockbillentry1List",1);
    }
    function clickDelTScOrderentryBtn(index) {
//		var stockQty = $("input[name='tScIcJhstockbillentry1List["+index+"].stockQty']").val();
//		if(stockQty > 0){
//			tip("该商品已被执行，不可删除");
//			return;
//		}
        $("#add_tScIcJhstockbillentry1_table").find(">tr").eq(index).remove();
        var length = $("#add_tScIcJhstockbillentry1_table").find(">tr").length;
        if (length == 0) {
            //clickAddTScIcJhstockbillentry1Btn(0)
            $("#itemName").removeAttr("disabled");
            var tr = $("#add_tScIcJhstockbillentry1_table_template tr").clone();
            $("#add_tScIcJhstockbillentry1_table").append(tr);
            resetTrNum('add_tScIcJhstockbillentry1_table', -1, "tScIcJhstockbillentry1List");
            $("#unitId1\\[" + 0 + "\\]").combobox({
                width:54,
                valueField: "id",
                textField: "text",
                panelHeight: "auto",
                editable: false
            })
        } else {
            resetTrNum('add_tScIcJhstockbillentry1_table', index, "tScIcJhstockbillentry1List");
        }
        rowIndex--;
    }
    function keyDownInfoI(index, name, evt) {
        debugger;
        var evt = (evt) ? evt : ((window.event) ? window.event : "");
        var code = evt.keyCode ? evt.keyCode : evt.which;
        if (code == 13) {
            if (name == "stock") {
                selectStockDialog(index, "tScIcJhstockbillentry1");
            } else if (name == "item") {
                selectIcitemDialog(index, "tScIcJhstockbillentry1List");
            } else if (name == "unit") {
                selectUnitDialog(index);
            }else if(name == "batchNo"){
                selectInventoryInfo(index,"tScIcJhstockbillentry1List");
            }else if(name == "batchNo2"){
                selectInventoryInfo(index,"tScIcJhstockbillentry2List");
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
        $("#tScIcJhstockbillentry1_table").createhftable({
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

        $("#add_tScIcJhstockbillentry1_table tr").each(function (i, data) {
            $('#addTScOrderentryBtn\\[' + i + '\\]').linkbutton({
                iconCls: 'icon-add',
                plain: 'true'
            });
            $('#delTScOrderentryBtn\\[' + i + '\\]').linkbutton({
                iconCls: 'icon-remove',
                plain: 'true'
            });
        });

        if (${fn:length(tScIcJhstockbillentry1List)  <= 0 }) {
            checkAllowAddLine(0);//判断是否允许增行操作
            var billDate = $("#date").val();
            var taxRate = $("#CFG_TAX_RATE").val();
            $("input[name='tScIcJhstockbillentry1List[0].kfDate']").val(billDate);
            $("input[name='tScIcJhstockbillentry1List[0].taxRate']").val(taxRate);
            $("input[name='tScIcJhstockbillentry1List[#index#].taxRate']").val(taxRate);
            $("select[name='tScIcJhstockbillentry1List[0].kfDateType']").attr("onChange", "setPeriodDate(0,'tScIcJhstockbillentry1List')");
            $("input[name='tScIcJhstockbillentry1List[0].stockName']").keypress(function (e) {
                if (e.keyCode == 13) {
                    selectStockDialog(0, "tScIcJhstockbillentry1");
                }
            });
            setFunctionInfo(0, "tScIcJhstockbillentry1List");
            $("#unitId1\\[0\\]").combobox({
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
                                $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].barCode']").val(barCode);
                                $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].coefficient']").val(cofficient);
                                $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].coefficient']").trigger("propertychange");
                                $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].secCoefficient']").trigger("propertychange");
                                $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);
                            }
                        });
                    }
                }
            })
//			$("input[name='tScIcJhstockbillentry1List[0].unitName']").keypress(function(e){
//				if(e.keyCode == 13){
//					selectUnitDialog(0);
//				}
//			})
        } else {
            var checkState = $("#checkState").val();
            var taxRate = $("#CFG_TAX_RATE").val();
            $("input[name='tScIcJhstockbillentry1List[#index#].taxRate']").val(taxRate);
            var $tbody = $("#tScIcJhstockbillentry1_table");
            var length = $tbody[0].rows.length;
            rowIndex = length - 1;
            //变更功能配置
            for (var j = 0; j < length; j++) {
                //编辑行金额函数配置
                if("detail" != "${load}") {
                    setFunctionInfo(j, "tScIcJhstockbillentry1List");
                }
                //$("select[name='tScIcJhstockbillentry1List[" + j + "].kfDateType']").attr("onChange", "setPeriodDate(" + j + ",'tScIcJhstockbillentry1List')");
                //checkAllowAddLine(j);//判断是否允许增行操作
                var unitId = $("#unitId1\\[" + j + "\\]").val();
                var itemId = $("input[name='tScIcJhstockbillentry1List[" + j + "].itemId']").val();
                var itemNo = $("input[name='tScIcJhstockbillentry1List[" + j + "].itemNo']").val();
                setValOldIdAnOldName($("input[name='tScIcJhstockbillentry1List[" + j + "].itemNo']"), itemId, itemNo);
                var idSrc = $("input[name='tScIcJhstockbillentry1List[" + j + "].idSrc']").val();
                var stockId = $("input[name='tScIcJhstockbillentry1List[" + j + "].stockId']").val();
                if (stockId) {
                    var stockName = $("input[name='tScIcJhstockbillentry1List[" + j + "].stockName']").val();
                    setValOldIdAnOldName($("input[name='tScIcJhstockbillentry1List[" + j + "].stockName']"), stockId, stockName);
                }
//				var entryOrderId = $("input[name='tScIcJhstockbillentry1List["+j+"].entryIdOrder']").val();
//				if(entryOrderId){
//					$("select[name='tScIcJhstockbillentry1List["+j+"].freeGifts_']").attr("disabled","disabled");
//					$("select[name='tScIcJhstockbillentry1List["+j+"].freeGifts_']").css("background-color","rgb(226,226,226)");
//				}
                var batchManager = $("input[name='tScIcJhstockbillentry1List[" + j + "].batchManager']").val();
                if ("N" == batchManager) {
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].batchNo']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].batchNo']").css("background-color", "rgb(226,226,226)");
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].batchNo']").removeAttr("datatype");
                }else{
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].batchNo']").attr("onkeypress","keyDownInfoI('"+j+"','batchNo')");
                }
                var isKfPeriod = $("input[name='tScIcJhstockbillentry1List[" + j + "].isKFPeriod']").val();
                if ("N" == isKfPeriod) {
                    //生产日期
                    $("input[ame='tScIcJhstockbillentry1List[" + j + "].kfDate']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].kfDate']").css("background-color", "rgb(226,226,226)");
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].kfDate']").attr("onClick", "");
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].kfDate']").val("");
                    //保质期
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].kfPeriod']").attr("readonly", "readonly");
                    $("input[name='tScIcJhstockbillentry1List[" + j + "].kfPeriod']").css("background-color", "rgb(226,226,226)");
                    //保质期类型
                    $("select[name='tScIcJhstockbillentry1List[" + j + "].kfDateType']").attr("disabled", "disabled");
                    $("select[name='tScIcJhstockbillentry1List[" + j + "].kfDateType']").css("background-color", "rgb(226,226,226)");
                }
                $("#unitId1\\[" + j + "\\]").combobox({
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
                                    $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].barCode']").val(barCode);
                                    $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].coefficient']").val(cofficient);
                                    $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].coefficient']").trigger("propertychange");
                                    $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].secCoefficient']").trigger("propertychange");
                                    $("input[name='tScIcJhstockbillentry1List[" + rowIndex + "].xsLimitPrice']").val(cgLimitPrice);
                                }
                            });
                        }
                    }
                })
                if (idSrc) {
                    //$("input[name='tScIcJhstockbillentry1List[" + j + "].stockName']").attr("disabled","disabled");
                    //$("input[name='tScIcJhstockbillentry1List[" + j + "].stockName']").css("background-color","rgb(226,226,226)");
                    $("#unitId1\\[" + j + "\\]").combobox({
                        disabled: true
                    })
                    if("fcopy" != "${load}") {
                        $("input[name='tScIcJhstockbillentry1List[" + j + "].itemNo']").attr("disabled", "disabled");
                        $("input[name='tScIcJhstockbillentry1List[" + j + "].itemNo']").css("background-color", "rgb(226,226,226)");
                        $("#itemName").attr("disabled", "disabled");
                        $("#itemName").css("background-color", "rgb(226,226,226)");
                    }
                }
            }
            //	}
        }
    });
</script>
<%--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">--%>
<%--<a id="addTScIcJhstockbillentry1Btn" href="#">添加</a> <a id="delTScIcJhstockbillentry1Btn" href="#">删除</a> --%>
<%--</div>--%>
<table border="0" cellpadding="2" cellspacing="1" id="tScIcJhstockbillentry1_table" totalRow="true" style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; ">
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
    <tbody id="add_tScIcJhstockbillentry1_table">
    <c:if test="${fn:length(tScIcJhstockbillentry1List)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 25px;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;"><a name="addTScOrderentryBtn[0]" id="addTScOrderentryBtn[0]" href="#"
                                             class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                                             onclick="clickAddTScIcJhstockbillentry1Btn(0,'tScIcJhstockbillentry1');"></a><a
                        name="delTScOrderentryBtn[0]" id="delTScOrderentryBtn[0]" href="#" class="easyui-linkbutton"
                        data-options="iconCls:'icon-remove'" plain="true"
                        onclick="clickDelTScOrderentryBtn(0,'tScIcJhstockbillentry1');"></a></div>
            </td>
            <input name="tScIcJhstockbillentry1List[0].id" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].createName" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].createBy" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].createDate" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].updateName" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].updateBy" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].updateDate" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].fid" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].findex" value="1" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].secUnitId" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].basicUnitId" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].basicQty" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].secCoefficient" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].secQty" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].price" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].amount" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].discountPrice" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].discountAmount" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].costPrice" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].costAmount" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].idSrc" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].entryIdSrc" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].idOrder" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].entryIdOrder" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].itemId" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].stockId" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].basicCoefficient" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].batchManager" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].isKFPeriod" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].xsLimitPrice" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].coefficient" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].classIDSrc" type="hidden"/>
            <input name="tScIcJhstockbillentry1List[0].kfDateType" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].itemNo" maxlength="32"
                       type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                       onkeypress="keyDownInfoI(0,'item',event)"
                       onblur="orderListStockBlur('0','itemId','itemNo','tScIcJhstockbillentry1List');"
                        >
                <label class="Validform_label" style="display: none;">商品编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].itemName" maxlength="32"
                       type="text" class="inputxt" style="width:180px;background-color: rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">商品名称</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].model" maxlength="32"
                       type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">规格</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].barCode" maxlength="32"
                       type="text" class="inputxt" style="width:100px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">条码</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].stockName" maxlength="32"
                       type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                       onblur="orderListStockBlur('0','stockId','stockName','tScIcJhstockbillentry1List');"
                        >
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].batchNo" maxlength="100"
                       type="text" class="inputxt" style="width:80px;"

                        >
                <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                <input id="unitId1[0]" name="tScIcJhstockbillentry1List[0].unitId" maxlength="32"
                       type="text" class="easyui-combobox"
                       data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false"
                       style="width:50px;"

                        >
                <label class="Validform_label" style="display: none;">单位</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].qty" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="vInt"
                        >
                <label class="Validform_label" style="display: none;">数量</label>
            </td>
                <%--<td align="left" bgcolor="white">--%>
                <%--<input name="tScIcJhstockbillentry1List[0].coefficient" maxlength="20" --%>
                <%--type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"--%>
                <%----%>
                <%-->--%>
                <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
                <%--</td>--%>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].taxPriceEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;" datatype="num"

                        >
                <label class="Validform_label" style="display: none;">单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].taxAmountEx" maxlength="20"
                       type="text" class="inputxt" style="width:70px;"

                        >
                <label class="Validform_label" style="display: none;">金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].discountRate" maxlength="20"
                       type="text" class="inputxt" style="width:80px;" value="100"

                        >
                <label class="Validform_label" style="display: none;">折扣率（%）</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].taxPrice" maxlength="20"
                       type="text" class="inputxt" style="width:80px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">折后单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].inTaxAmount" maxlength="20"
                       type="text" class="inputxt" style="width:80px;"
                       onchange="setAllAmount()"
                        >
                <label class="Validform_label" style="display: none;">折后金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].taxRate" maxlength="20"
                       type="text" class="inputxt" style="width:80px;"
                        >
                <label class="Validform_label" style="display: none;">税率（%）</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].taxAmount" maxlength="20"
                       type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">税额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].kfDate" maxlength="20"
                       type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                       onchange="setPeriodDate(0,'tScIcJhstockbillentry1List')"

                        >
                <label class="Validform_label" style="display: none;">生产日期</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].kfPeriod" maxlength="10"
                       type="text" class="inputxt" style="width:50px;background-color:rgb(226,226,226)" readonly="readonly"
                       onchange="setPeriodDate(0,'tScIcJhstockbillentry1List')"
                        >
                <label class="Validform_label" style="display: none;">保质期</label>
            </td>
            <td align="left" bgcolor="white">
                <t:dictSelect field="tScIcJhstockbillentry1List[0].kfDateType_" type="list" width="70"
                              typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" extendJson="{disabled:disabled}" hasLabel="false"
                              title="保质期类型"></t:dictSelect>
                <label class="Validform_label" style="display: none;">保质期类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].periodDate" maxlength="20"
                       type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">有效期至</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].classIDName" maxlength="10"
                       type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">源单类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].billNoSrc" maxlength="100"
                       type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">源单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].billNoOrder" maxlength="100"
                       type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)"
                       readonly="readonly"

                        >
                <label class="Validform_label" style="display: none;">订单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcJhstockbillentry1List[0].note" maxlength="255"
                       type="text" class="inputxt" style="width:180px;"

                        >
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScIcJhstockbillentry1List)  > 0 }">
        <c:forEach items="${tScIcJhstockbillentry1List}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 80px;"><a name="addTScOrderentryBtn[${stuts.index }]"
                                                 id="addTScOrderentryBtn[${stuts.index }]" href="#"
                                                 class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                                                 plain="true"
                                                 onclick="clickAddTScIcJhstockbillentry1Btn(${stuts.index },'tScIcJhstockbillentry1');"></a><a
                            name="delTScOrderentryBtn[${stuts.index }]" id="delTScOrderentryBtn[${stuts.index }]"
                            href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                            onclick="clickDelTScOrderentryBtn(${stuts.index },'tScIcJhstockbillentry1');"></a></div>
                </td>
                </c:if>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].createBy" type="hidden"
                       value="${poVal.createBy }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].updateBy" type="hidden"
                       value="${poVal.updateBy }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].findex" type="hidden"
                       value="${poVal.findex }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].secUnitId" type="hidden"
                       value="${poVal.secUnitId }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].basicUnitId" type="hidden"
                       value="${poVal.basicUnitId }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].basicQty" type="hidden"
                       value="${poVal.basicQty }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].secCoefficient" type="hidden"
                       value="${poVal.secCoefficient }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].secQty" type="hidden"
                       value="${poVal.secQty }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].price" type="hidden" value="${poVal.price }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].amount" type="hidden"
                       value="${poVal.amount }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].discountPrice" type="hidden"
                       value="${poVal.discountPrice }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].discountAmount" type="hidden"
                       value="${poVal.discountAmount }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].costPrice" type="hidden"
                       value="${poVal.costPrice }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].costAmount" type="hidden"
                       value="${poVal.costAmount }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].idSrc" type="hidden" value="${poVal.idSrc }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].entryIdSrc" type="hidden"
                       value="${poVal.entryIdSrc }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].idOrder" type="hidden"
                       value="${poVal.idOrder }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].entryIdOrder" type="hidden"
                       value="${poVal.entryIdOrder }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].itemId" type="hidden"
                       value="${poVal.itemId }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].stockId" type="hidden"
                       value="${poVal.stockId }"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].basicCoefficient"
                       value="${poVal.basicCoefficient}" type="hidden"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].batchManager" value="${poVal.batchManager}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].isKFPeriod" value="${poVal.isKFPeriod}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].xsLimitPrice" value="${poVal.xsLimitPrice}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].coefficient" value="${poVal.coefficient}"
                       type="hidden"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].classIDSrc" type="hidden" value="${poVal.classIDSrc}"/>
                <input name="tScIcJhstockbillentry1List[${stuts.index }].kfDateType" type="hidden" value="${poVal.kfDateType}"/>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].itemNo" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                           onkeypress="keyDownInfoI(${stuts.index },'item',event)"
                           onblur="orderListStockBlur('${stuts.index }','itemId','itemNo','tScIcJhstockbillentry1List');"
                           value="${poVal.itemNo }">
                    <label class="Validform_label" style="display: none;">商品编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].itemName" maxlength="32"
                           type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.itemName }">
                    <label class="Validform_label" style="display: none;">商品名称</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].model" maxlength="32"
                           type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.model }">
                    <label class="Validform_label" style="display: none;">规格</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].barCode" maxlength="32"
                           type="text" class="inputxt" style="width:100px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.barCode }">
                    <label class="Validform_label" style="display: none;">条形码</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].stockName" maxlength="32"
                           type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                           onkeypress="keyDownInfoI(${stuts.index },'stock',event)"
                           onblur="orderListStockBlur('${stuts.index }','stockId','stockName','tScIcJhstockbillentry1List');"
                           value="${poVal.stockName }">
                    <label class="Validform_label" style="display: none;">仓库</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].batchNo" maxlength="100"
                           type="text" class="inputxt" style="width:80px;"

                           value="${poVal.batchNo }">
                    <label class="Validform_label" style="display: none;">批号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input id="unitId1[${stuts.index }]" name="tScIcJhstockbillentry1List[${stuts.index }].unitId"
                           maxlength="32"
                           type="text" style="width:50px;"
                           class="easyui-combobox"
                           data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',width:54,editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"
                           value="${poVal.unitId }">
                    <label class="Validform_label" style="display: none;">单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].qty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;" datatype="vInt"

                           value="${poVal.qty }">
                    <label class="Validform_label" style="display: none;">数量</label>
                </td>
                    <%--<td align="left" bgcolor="white">--%>
                    <%--<input name="tScIcJhstockbillentry1List[${stuts.index }].coefficient" maxlength="20" --%>
                    <%--type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"--%>
                    <%----%>
                    <%--value="${poVal.coefficient }">--%>
                    <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
                    <%--</td>--%>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].taxPriceEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;" datatype="num"

                           value="${poVal.taxPriceEx }">
                    <label class="Validform_label" style="display: none;">单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].taxAmountEx" maxlength="20"
                           type="text" class="inputxt" style="width:70px;"

                           value="${poVal.taxAmountEx }">
                    <label class="Validform_label" style="display: none;">金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].discountRate" maxlength="20"
                           type="text" class="inputxt" style="width:80px;"

                           value="${poVal.discountRate }">
                    <label class="Validform_label" style="display: none;">折扣率（%）</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].taxPrice" maxlength="20"
                           type="text" class="inputxt" style="width:80px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.taxPrice }">
                    <label class="Validform_label" style="display: none;">折后单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].inTaxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:80px;"
                           onchange="setAllAmount()"
                           value="${poVal.inTaxAmount }">
                    <label class="Validform_label" style="display: none;">折后金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].taxRate" maxlength="20"
                           type="text" class="inputxt" style="width:80px;"

                           value="${poVal.taxRate }">
                    <label class="Validform_label" style="display: none;">税率（%）</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].taxAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.taxAmount }">
                    <label class="Validform_label" style="display: none;">税额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].kfDate" maxlength="20"
                           type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                           onchange="setPeriodDate(${stuts.index },'tScIcJhstockbillentry1List')"
                           value="<fmt:formatDate value='${poVal.kfDate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">生产日期</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].kfPeriod" maxlength="10"
                           type="text" class="inputxt" style="width:65px;background-color: rgb(226,226,226)" readonly="readonly"
                           onchange="setPeriodDate(${stuts.index },'tScIcJhstockbillentry1List')"
                           value="${poVal.kfPeriod }">
                    <label class="Validform_label" style="display: none;">保质期</label>
                </td>
                <td align="left" bgcolor="white">
                    <t:dictSelect width="70" field="tScIcJhstockbillentry1List[${stuts.index }].kfDateType_" type="list"
                                  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType }" extendJson="{disabled:disabled}" hasLabel="false"
                                  title="保质期类型"></t:dictSelect>
                    <label class="Validform_label" style="display: none;">保质期类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].periodDate" maxlength="20"
                           type="text" class="Wdate" readonly="readonly"
                           style="width:80px;background-color:rgb(226,226,226)"

                           value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">有效期至</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].classIDName" maxlength="10"
                           type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.classIDName }">
                    <label class="Validform_label" style="display: none;">源单类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].billNoSrc" maxlength="100"
                           type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.billNoSrc }">
                    <label class="Validform_label" style="display: none;">源单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].billNoOrder" maxlength="100"
                           type="text" class="inputxt" style="width:90px;background-color:rgb(226,226,226)"
                           readonly="readonly"

                           value="${poVal.billNoOrder }">
                    <label class="Validform_label" style="display: none;">订单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcJhstockbillentry1List[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:180px;"

                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
