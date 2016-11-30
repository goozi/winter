<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>采购出入库单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript" src="plug-in/scm/js/computingTools.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#tt').tabs({
                onSelect: function (title) {
                    $('#tt .panel-body').css('width', 'auto');
                }
            });
            $(".tabs-wrap").css('width', '100%');
            $(".tabs-wrap").css('display', 'none');
        });
    </script>

    <style>
        /**20160629  页脚样式 **/
        body {
            position: absolute;
            width: 100%;
            height: 100%;
        }

        .footlabel {
            float: left;
            margin-left: 15px;
        }

        .footspan {
            float: left;
            margin-left: 5px;
            margin-right: 5px;
            font-weight: bold;
            color: grey;
            margin-bottom: 5px;
        }

        .fullForm {
            width: 100%;;
        }

        .spanBtn {
            background-color: #CCE1F3;
            display: -moz-inline-box;
            display: inline-block;
            width: 20px;
            height: 20px;
            text-align: center;
        }
    </style>
</head>
<body style="overflow-x: hidden;">
<input id="configName" value="tScPoStockbillentryList" type="hidden">
<input id="load" value="${load}" type="hidden">
<input id="isCheckNegative" type="hidden" value="${isCheckNegative}">
<input type="hidden" id="checkDate" value="${checkDate}">
<input id="clearExt" type="hidden" value="doClearExt">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" tableName="t_sc_po_stockbill" title="${title}" saveTemp="true"
             tranType="${tScPoStockbillPage.tranType }" styleClass="fullForm" layout="table" tiptype="1"
             beforeSubmit="checkPriceIsZero" action="tScPoStockbillController.do?doUpdate">
    <input id="id" name="id" type="hidden" value="${tScPoStockbillPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tScPoStockbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tScPoStockbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tScPoStockbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tScPoStockbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tScPoStockbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tScPoStockbillPage.updateDate }">
    <input id="tranType" name="tranType" type="hidden" value="${tScPoStockbillPage.tranType }">
    <input id="amount" name="amount" type="hidden" value="${tScPoStockbillPage.amount }">
    <input id="checkAmount" name="checkAmount" type="hidden" value="${tScPoStockbillPage.checkAmount }">
    <input id="classIDSrc" name="classIDSrc" type="hidden" value="${tScPoStockbillPage.classIDSrc }">
    <input id="idSrc" name="idSrc" type="hidden" value="${tScPoStockbillPage.idSrc }">
    <input id="billNoSrc" name="billNoSrc" type="hidden" value="${tScPoStockbillPage.billNoSrc }">
    <input id="itemId" name="itemId" type="hidden" value="${tScPoStockbillPage.itemId }">
    <input id="empId" name="empId" type="hidden" value="${tScPoStockbillPage.empId }">
    <input id="deptId" name="deptId" type="hidden" value="${tScPoStockbillPage.deptId }">
    <input id="stockId" name="stockId" type="hidden" value="${tScPoStockbillPage.stockId }">
    <input id="cancellation" name="cancellation" type="hidden" value='${tScPoStockbillPage.cancellation}'>
    <input id="sonId" name="sonId" type="hidden" value='${tScPoStockbillPage.sonId}'>
    <input id="version" name="version" type="hidden" value='${tScPoStockbillPage.version}'>
    <input id="billerID" name="billerID" type="hidden" value='${tScPoStockbillPage.billerID}'>
    <input id="checkState" name="checkState" type="hidden" value='${tScPoStockbillPage.checkState}'>
    <input id="accountId" name="accountId" type="hidden" value='${tScPoStockbillPage.accountId}'>
    <input id="billNo" name="billNo" type="hidden" value='${tScPoStockbillPage.billNo}'>
    <input name="date" type="hidden" value='${tScPoStockbillPage.date}'>
    <table cellpadding="0" cellspacing="1" class="formtable" style="z-index: 50;position: absolute">
        <tr>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">单据编号:</label>--%>
            <%--</td>--%>
            <%--<td class="value">--%>
                <%--<input id="billNo" name="billNo" readonly="readonly" type="text" style="width: 150px" class="inputxt"--%>
                       <%--datatype="*" value='${tScPoStockbillPage.billNo}'>--%>
				<%--<span class="Validform_checktip">--%>
                      <%--*--%>
				<%--</span>--%>
                <%--<label class="Validform_label" style="display: none;">单据编号</label>--%>
            <%--</td>--%>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">单据日期:</label>--%>
            <%--</td>--%>
            <%--<td class="value">--%>
                <%--<input id="date" name="date" type="text" style="width: 150px"--%>
                       <%--datatype="*" class="Wdate" onClick="WdatePicker()"--%>
                       <%--value='<fmt:formatDate value='${tScPoStockbillPage.date}' type="date" pattern="yyyy-MM-dd"/>'>--%>
				<%--<span class="Validform_checktip">--%>
                      <%--*--%>
				<%--</span>--%>
                <%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
            <%--</td>--%>
            <td align="right">
                <label class="Validform_label">供应商:</label>
            </td>
            <td class="value">
                <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
                       value='${tScPoStockbillPage.itemName}'>
				<span class="Validform_checktip">
                      *
				</span>
                <label class="Validform_label" style="display: none;">供应商</label>
            </td>
                <td align="right">
                    <label class="Validform_label">经办人:</label>
                </td>
                <td class="value">
                    <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
                           value='${tScPoStockbillPage.empName}'>
				<span class="Validform_checktip">
                      *
				</span>
                    <label class="Validform_label" style="display: none;">经办人</label>
                </td>
                <td align="right">
                    <label class="Validform_label">部门:</label>
                </td>
                <td class="value">
                    <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
                           value='${tScPoStockbillPage.deptName}'>
				<span class="Validform_checktip">
                      *
				</span>
                    <label class="Validform_label" style="display: none;">部门</label>
                </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">仓库:</label>
            </td>
            <td class="value">
                <input id="stockName" name="stockName" type="text" style="width: 150px" class="inputxt popup-select"
                       value='${tScPoStockbillPage.stockName}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="right">
                <label class="Validform_label">应付金额:</label>
            </td>
            <td class="value">
                <input id="allAmount" name="allAmount" readonly="readonly" type="text" style="width: 150px"
                       class="inputxt" value='${tScPoStockbillPage.allAmount}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">应付金额</label>
            </td>
            <td align="right">
                <label class="Validform_label">优惠金额:</label>
            </td>
            <td class="value">
                <input id="rebateAmount" name="rebateAmount" type="text" style="width: 150px" class="inputxt" datatype="num" ignore="ignore"
                       onblur="changeAllAmount()" value='${tScPoStockbillPage.rebateAmount}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">优惠金额</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">结算账户:</label>
            </td>
            <td class="value">
                <input id="accountName" name="accountName" type="text" style="width: 150px" class="inputxt popup-select" onblur="checkcurPayAmount()"
                       value='${tScPoStockbillPage.accountName}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">结算账户</label>
            </td>
            <td align="right">
                <label class="Validform_label">本次付款:</label>
            </td>
            <td class="value">
                <input id="curPayAmount" name="curPayAmount" readonly="readonly" type="text" style="width: 150px" class="inputxt"
                       value='${tScPoStockbillPage.curPayAmount}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">本次付款</label>
            </td>
            <td align="right">
                <label class="Validform_label">联系人:</label>
            </td>
            <td class="value">
                <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"
                       value='${tScPoStockbillPage.contact}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">联系人</label>
                <span class="spanbtn-expand" id="btnExpand"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">手机:</label>
            </td>
            <td class="value">
                <input id="mobilePhone" name="mobilePhone" type="text" style="width: 150px" class="inputxt"
                       value='${tScPoStockbillPage.mobilePhone}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">手机</label>
            </td>
            <td align="right">
                <label class="Validform_label">电话:</label>
            </td>
            <td class="value">
                <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                       value='${tScPoStockbillPage.phone}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">电话</label>
            </td>
            <td align="right">
                <label class="Validform_label">传真:</label>
            </td>
            <td class="value">
                <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
                       value='${tScPoStockbillPage.fax}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">传真</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">分支机构:</label>
            </td>
            <td class="value">
                <input id="sonName" name="sonName" type="text" readonly="readonly" style="width: 150px" class="inputxt"
                       value='${tScPoStockbillPage.sonName}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">分支机构</label>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">联系地址:</label>
            </td>
            <td class="value" colspan="5">
                <input id="address" name="address" type="text" style="width: 725px" class="inputxt"
                       value='${tScPoStockbillPage.address}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">联系地址</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="5">
                <input id="explanation" name="explanation" type="text" style="width: 725px" class="inputxt"
                       value='${tScPoStockbillPage.explanation}'>
				<span class="Validform_checktip">
				</span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
    </table>
    <div style="width: auto;height: 200px;margin-top: 100px">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:800px;height:1px;"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScPoStockbillController.do?tScPoStockbillentryList&id=${tScPoStockbillPage.id}&load=${load}&tranType=${tranType}"
                   icon="icon-search" title="采购入库单明细表" id="tScPoStockbillentry"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScPoStockbillentry_table_template">
    <tr>
        <td align="center" bgcolor="white">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;"><a name="addTScOrderentryBtn[#index#]"
                                                         id="addTScOrderentryBtn[#index#]" href="#"
                                                         class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                                                         plain="true"
                                                         onclick="clickAddTScPoStockbillentryBtn('#index#');"></a><a
                    name="delTScOrderentryBtn[#index#]" id="delTScOrderentryBtn[#index#]" href="#"
                    class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                    onclick="clickDelTScOrderentryBtn('#index#');"></a>
            </div>
        </td>
        <input name="tScPoStockbillentryList[#index#].basicUnitId" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].basicCoefficient" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].coefficient" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].basicQty" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].secUnitId" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].secCoefficient" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].secQty" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].price" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].amount" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].discountPrice" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].discountAmount" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].idSrc" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].entryIdSrc" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].idOrder" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].entryIdOrder" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].findex" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].stockId" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].itemId" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].costPrice" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].costAmount" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].freeGifts" value="0" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].cgLimitPrice" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].commitQty" value="0.0" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].classIDSrc" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].kfDateType" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].isKFPeriod" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].batchManager" type="hidden"/>
        <input name="tScPoStockbillentryList[#index#].billQty" type="hidden"/>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].itemNo" maxlength="32"
                   type="text" class="inputxt popup-select" style="width:105px;"
                   datatype="*" onkeypress="keyDownInfo('#index#','item',event)"
                   onblur="orderListStockBlur('#index#','itemId','itemNo');"
                    >
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].itemName" readonly="readonly" maxlength="32"
                   type="text" class="inputxt" style="width:180px;"
                   datatype="*"
                    >
            <label class="Validform_label" style="display: none;">商品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].model" readonly="readonly" maxlength="32"
                   type="text" class="inputxt" style="width:85px;"
                    >
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].barCode" readonly="readonly" maxlength="32"
                   type="text" class="inputxt" style="width:65px;"
                    >
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].stockName" maxlength="32"
                   type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                   onkeypress="keyDownInfo('#index#','stock',event)"
                   onblur="orderListStockBlur('#index#','stockId','stockName');"
                    >
            <label class="Validform_label" style="display: none;">仓库</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].batchNo" maxlength="50"
                   type="text" class="inputxt" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">批号</label>
        </td>
        <td align="left" bgcolor="white">
            <input id="unitId[#index#]" name="tScPoStockbillentryList[#index#].unitId" maxlength="32"
                   type="text" class="inputxt" style="width:50px;"
                    >
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].qty" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"
                   datatype="vInt"
                    >
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].taxPriceEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"
                   datatype="num" ignore="ignore"
                    >
            <label class="Validform_label" style="display: none;">单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].taxAmountEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"
                   datatype="num" ignore="ignore"
                    >
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].discountRate" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" value="100"
                   datatype="num" ignore="ignore"
                    >
            <label class="Validform_label" style="display: none;">折扣率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].taxPrice" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">折后单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].inTaxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"
                   datatype="num" ignore="ignore"
                    >
            <label class="Validform_label" style="display: none;">折后金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].taxRate" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"
                   datatype="num" ignore="ignore" value="${taxRate}"
                    >
            <label class="Validform_label" style="display: none;">税率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].taxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">税额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].kfDate" maxlength="20"
                   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                   onchange="setPeriodDate('#index#')"
                    >
            <label class="Validform_label" style="display: none;">生产日期</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].kfPeriod" maxlength="10"
                   type="text" class="inputxt" style="width:50px;" readonly="readonly"
                   onchange="setPeriodDate('#index#')"
                    >
            <label class="Validform_label" style="display: none;">保质期</label>
        </td>
        <td align="left" bgcolor="white">
            <t:dictSelect field="tScPoStockbillentryList[#index#].kfDateType_" width="70px" type="list"
                          showDefaultOption="true" extendJson="{disabled:disabled}"
                          typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false" title="保质期类型"></t:dictSelect>
            <label class="Validform_label" style="display: none;">保质期类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].periodDate" maxlength="20"
                   type="text" class="Wdate" style="width:80px;"
                   readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">有效期至</label>
        </td>
        <%--<td align="left" bgcolor="white">--%>
        <%--<input name="tScPoStockbillentryList[#index#].costPrice" maxlength="20"--%>
        <%--type="text" class="inputxt" style="width:120px;"--%>
        <%--readonly="readonly"--%>
        <%-->--%>
        <%--<label class="Validform_label" style="display: none;">成本单价</label>--%>
        <%--</td>--%>
        <%--<td align="left" bgcolor="white">--%>
        <%--<input name="tScPoStockbillentryList[#index#].costAmount" maxlength="20"--%>
        <%--type="text" class="inputxt" style="width:120px;"--%>
        <%--readonly="readonly"--%>
        <%-->--%>
        <%--<label class="Validform_label" style="display: none;">成本金额</label>--%>
        <%--</td>--%>
        <td align="left" bgcolor="white">
            <t:dictSelect field="tScPoStockbillentryList[#index#].freeGifts_" width="70px"
                          type="list" showDefaultOption="false"
                          typeGroupCode="sf_01" defaultVal="0" hasLabel="false"
                          title="赠品标记"></t:dictSelect>
            <label class="Validform_label" style="display: none;">赠品标记</label>
        </td>
        <c:if test="${tranType eq '52'}">
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].commitQty" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"
                   readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">退货数量</label>
        </td>
        </c:if>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].classIDName" maxlength="10"
                   type="text" class="inputxt" style="width:90px;"
                   readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">源单类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].billNoSrc" maxlength="50"
                   type="text" class="inputxt" style="width:90px;"
                   readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">源单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].billNoOrder" maxlength="50"
                   type="text" class="inputxt" style="width:90px;"
                   readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">订单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPoStockbillentryList[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:180px;"

                    >
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
    </tbody>
</table>
<!--20160629  页脚显示 -->
<div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
    <div style="width: 33%;display:inline;float: left" align="left">
        <label class="Validform_label footlabel">制单人: </label>
        <span class="inputxt footspan"> ${tScPoStockbillPage.billerName}</span>
    </div>
    <div style="width: 33%;display: inline;float: left" align="left">
        <label class="Validform_label footlabel">审核人: </label>
        <span class="inputxt footspan">${auditor} </span>
    </div>
    <div style="width: 33%;display: inline;float: right" align="left">
        <label class="Validform_label footlabel">审核日期: </label>
        <span class="inputxt footspan">${auditDate} </span>
    </div>


</div>

</body>
<script src="webpage/com/qihang/buss/sc/sales/tScPoStockbill.js"></script>