<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>销售出库单</title>
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
        /** 页脚样式  **/
        body {
            position: absolute;
            width: 100%;
            height: 100%;
        }

        .footlabel {
            float: left;
            margin-left: 35px;
        }

        .footspan {
            float: left;
            margin-left: 5px;
            margin-right: 5px;
            font-weight: bold;
            color: grey;
            margin-bottom: 5px;
            text-decoration: underline;
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
<input id="configName" value="tScSlStockbillentryList" type="hidden">
<input type="hidden" id="defaultTaxRate" value="${taxRate}">
<input id="isCheckNegative" type="hidden" value="${isCheckNegative}">
<input id="countAmount" type="hidden" >
<input id="creditamount" type="hidden">
<input id="iscreditmgr" type="hidden">
<input id="method" type="hidden"  value="${method}">
<input id="timePoint" type="hidden"  value="${timePoint}">
<input id="tempRecoveryExt" type="hidden" value="doTempRecoveryExt">
<t:formvalid formid="formobj" dialog="true" title="${title}" usePlugin="password" beforeSubmit="checkPriceIsZero" layout="table" tiptype="1" saveTemp="true"
             action="tScSlStockbillController.do?doAdd">
    <input id="id" name="id" type="hidden"
           value="${tScSlStockbillPage.id }">
    <input id="createName" name="createName" type="hidden"
           value="${tScSlStockbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden"
           value="${tScSlStockbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden"
           value="${tScSlStockbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden"
           value="${tScSlStockbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden"
           value="${tScSlStockbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden"
           value="${tScSlStockbillPage.updateDate }">
    <input id="trantype" name="trantype" type="hidden"
           value="${tranType}">
    <input id="amount" name="amount" type="hidden"
           value="${tScSlStockbillPage.amount }">
    <input id="checkamount" name="checkamount" type="hidden"
           value="0">
    <input id="idSrc" name="idSrc" type="hidden"
           value="">
    <input id="billnoSrc" name="billnoSrc" type="hidden"
           value="">
    <input id="checkState" name="checkstate" type="hidden"
           value="0">
    <input id="cancellation" name="cancellation" type="hidden"
           value="0">
    <input id="itemid" name="itemid" type="hidden"/>
    <input id="empid" name="empid" type="hidden"/>
    <input id="deptid" name="deptid" type="hidden"/>
    <input id="sonid" name="sonid" value="${sonId}" type="hidden"/>
    <input id="accountid" name="accountid" type="hidden"/>
    <input id="billerid" name="billerid" value="${sessionScope.user.id}" type="hidden"/>
    <input id="stockid" name="stockid" type="hidden"/>
    <input id="billNo" name="billNo" value="${billNo}" type="hidden"/>
    <input name="date" value="${date}" type="hidden"/>
    <input id="json" name="json" type="hidden"/>
    <table cellpadding="0" cellspacing="1" class="formtable" style="z-index: 50;position: absolute">
        <tr>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">单据编号:</label>--%>
                <%--</td>--%>
                <%--<td class="value">--%>
                <%--<input id="billno" name="billno" value="${billNo}" readonly="readonly" type="text" style="width: 150px"--%>
                <%--class="inputxt"--%>
                <%--datatype="*">--%>
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
                <%--datatype="*" class="Wdate" onClick="WdatePicker()">--%>
                <%--<span class="Validform_checktip">--%>
                <%--*--%>
                <%--</span>--%>
                <%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
                <%--</td>--%>
            <td align="right">
                <label class="Validform_label">客户:</label>
            </td>
            <td class="value">
                <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select"
                       datatype="*">
      <span class="Validform_checktip">
          *
      </span>
                <label class="Validform_label" style="display: none;">客户</label>
            </td>
                <td align="right">
                    <label class="Validform_label">经办人:</label>
                </td>
                <td class="value">
                    <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select"
                           datatype="*">
      <span class="Validform_checktip">
          *
      </span>
                    <label class="Validform_label" style="display: none;">经办人</label>
                </td>
                <td align="right">
                    <label class="Validform_label">部门:</label>
                </td>
                <td class="value">
                    <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select"
                           datatype="*">
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
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="right">
                <label class="Validform_label">${amountStr}金额:</label>
            </td>
            <td class="value">
                <input id="allamount" name="allamount" type="text" style="width: 150px" readonly="readonly"
                       class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">${amountStr}金额</label>
            </td>
            <td align="right">
                <label class="Validform_label">优惠金额:</label>
            </td>
            <td class="value">
                <input id="rebateamount" name="rebateamount" type="text" style="width: 150px" class="inputxt" datatype="num" ignore="ignore"
                       onblur="changeAllAmount()"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">优惠金额</label>
            </td>
        </tr>
        <tr>

            <td align="right">
                <label class="Validform_label">本次${payType}:</label>
            </td>
            <td class="value">
                <input id="curpayamount" name="curpayamount" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">本次${payType}</label>
            </td>
            <td align="right">
                <label class="Validform_label">结算账户:</label>
            </td>
            <td class="value">
                <input id="accountName" type="text" style="width: 150px" class="inputxt popup-select" onblur="checkcurPayAmount()"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">结算账户</label>
            </td>
            <td align="right">
                <label class="Validform_label">重量:</label>
            </td>
            <td class="value">
                <input id="weight" name="weight" type="text" style="width: 150px" class="inputxt" readonly="readonly"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">重量</label>
                <span class="spanbtn-expand" id="btnExpand"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">联系人:</label>
            </td>
            <td class="value">
                <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">联系人</label>
            </td>
            <td align="right">
                <label class="Validform_label">手机:</label>
            </td>
            <td class="value">
                <input id="mobilephone" name="mobilephone" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">手机</label>
            </td>
            <td align="right">
                <label class="Validform_label">电话:</label>
            </td>
            <td class="value">
                <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">电话</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">传真:</label>
            </td>
            <td class="value">
                <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">传真</label>
            </td>
            <td align="right">
                <label class="Validform_label">物流费用:</label>
            </td>
            <td class="value">
                <input id="freight" name="freight"  readonly="readonly" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">物流费用</label>
            </td>
            <td align="right">
                <label class="Validform_label">分支机构:</label>
            </td>
            <td class="value">
                <input id="sonName" name="sonName" value="${sonName}" readonly="readonly" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">分支机构</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">联系地址:</label>
            </td>
            <td class="value" colspan="5">
                <input id="address" name="address" type="text" style="width: 725px" class="inputxt"
                        >
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
                        >
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
            <t:tab href="tScSlStockbillController.do?tScSlStockbillentryList&id=${tScSlStockbillPage.id}&trantype=${tranType}"
                   icon="icon-search" title="销售出库单明细" id="tScSlStockbillentry"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScSlStockbillentry_table_template">
    <tr>
        <td align="center" bgcolor="white">
            <div style="width: 30px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addtScSlStockbillentryListBtn[#index#]" id="addtScSlStockbillentryListBtn[#index#]" href="#"
                   onclick="clickAddTScSlStockbillentryListBtn('#index#');"></a>
                <a name="deltScSlStockbillentryListBtn[#index#]" id="deltScSlStockbillentryListBtn[#index#]" href="#"
                   onclick="clickDeltScSlStockbillentryListBtn('#index#');"></a>
            </div>
        </td>
        <input name="tScSlStockbillentryList[#index#].id" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].createName" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].createBy" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].updateName" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].updateBy" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].createDate" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].updateDate" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].fid" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].basicunitid" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].coefficient" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].basicQty" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].secunitid" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].secCoefficient" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].secQty" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].price" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].amount" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].discountPrice" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].discountAmount" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].costPrice" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].costAmount" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].idSrc" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].entryidSrc" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].idOrder" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].entryidOrder" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].findex" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].itemid" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].stockid" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].freegifts" value="0" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].xsLimitPrice" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].billQty" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].itemweight" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].stockQty" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].invQty" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].classidSrc" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].isFreeGift" value="2" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].salesid" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].kfdatetype" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].batchManager" type="hidden"/>
        <input name="tScSlStockbillentryList[#index#].isKfperiod" type="hidden"/>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].itemNo" maxlength="32"
                   onkeypress="keyDownInfo('#index#','item',event)" onblur="orderListStockBlur('#index#','itemid','itemNo');"
                   type="text" class="inputxt popup-select" datatype="*" style="width:105px;"/>
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].itemName" maxlength="32"
                   type="text" class="inputxt" readonly="readonly" style="width:180px;"/>
            <label class="Validform_label" style="display: none;">商品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].model" maxlength="32"
                   type="text" class="inputxt" readonly="readonly" style="width:85px;"/>
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].barCode" maxlength="32"
                   type="text" class="inputxt" readonly="readonly" style="width:65px;"/>
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].stockName" maxlength="32"
                   onkeypress="keyDownInfo('#index#','stock',event)" datatype="*"
                   onblur="orderListStockBlur('#index#','stockid','stockName');"
                   type="text" class="inputxt popup-select" style="width:65px;"/>
            <label class="Validform_label" style="display: none;">仓库</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].batchno" maxlength="50"
                   type="text" class="inputxt" datatype="*" style="width:80px;"/>
            <label class="Validform_label" style="display: none;">批号</label>
        </td>
        <td align="left" bgcolor="white">
            <input id="unitId[#index#]" name="tScSlStockbillentryList[#index#].unitid" maxlength="32"
                   type="text" class="inputxt" style="width:54px;"/>
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].qty" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="vInt"/>
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].taxPriceEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="num"/>
            <label class="Validform_label" style="display: none;">单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].taxAmountEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].discountRate" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" value="100" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">折扣率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].taxPrice" maxlength="20"
                   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
            <label class="Validform_label" style="display: none;">折后单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].inTaxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">折后金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].weight_" maxlength="20"
                   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
            <label class="Validform_label" style="display: none;">重量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].taxRate" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">税率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].taxAmount" maxlength="20"
                   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
            <label class="Validform_label" style="display: none;">税额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].kfdate" maxlength="0" onchange="setPeriodDate('#index#')"
                   type="text" class="Wdate" <c:if test="${tranType ne '103'}">onClick="WdatePicker()" style="width:80px;" </c:if> <c:if test="${tranType eq '103'}"> readonly="readonly" style="width:80px;background-color: rgb(226,226,226);"</c:if> />
            <label class="Validform_label" style="display: none;">生产日期</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].kfperiod" maxlength="10" readonly="readonly"
                   type="text" class="inputxt" style="width:50px;" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">保质期</label>
        </td>
        <td align="left" bgcolor="white">
            <%--<input name="tScSlStockbillentryList[#index#].kfdatetype" maxlength="10"--%>
            <%--type="text" class="inputxt" style="width:70px;"/>--%>
            <t:dictSelect field="tScSlStockbillentryList[#index#].kfdatetype_" width="70" type="list"
                          showDefaultOption="true" extendJson="{disabled:disabled}"
                          typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false" title="保质期类型"></t:dictSelect>
            <label class="Validform_label" style="display: none;">保质期类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].perioddate" maxlength="0"
                   type="text" class="Wdate" readonly="readonly" style="width:80px;"/>
            <label class="Validform_label" style="display: none;">有效期至</label>
        </td>
        <c:if test="${tranType eq '103'}">
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].salesName" maxlength="10"
                   type="text" class="inputxt" readonly="readonly" style="width:65px;"/>
            <label class="Validform_label" style="display: none;">促销类型</label>
        </td>
        </c:if>
        <td align="left" bgcolor="white">
            <t:dictSelect field="tScSlStockbillentryList[#index#].freegifts_"
                          type="list" width="70px" showDefaultOption="false"
                          typeGroupCode="sf_01" defaultVal="0" hasLabel="false"
                          title="赠品标记"></t:dictSelect>
            <label class="Validform_label" style="display: none;">赠品标记</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].commitqty" maxlength="20"
                   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
            <label class="Validform_label" style="display: none;">退货数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].classidName" maxlength="10"
                   type="text" class="inputxt" readonly="readonly" style="width:90px;"/>
            <label class="Validform_label" style="display: none;">源单类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].billnoSrc" maxlength="50"
                   type="text" class="inputxt" readonly="readonly" style="width:90px;"/>
            <label class="Validform_label" style="display: none;">源单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].billnoOrder" maxlength="50"
                   type="text" class="inputxt" readonly="readonly" style="width:90px;"/>
            <label class="Validform_label" style="display: none;">订单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScSlStockbillentryList[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:180px;"/>
            <label class="Validform_label" style="display: none;">备注</label>
        </td>


    </tr>
    </tbody>
</table>
<!-- 页脚显示 -->
<div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
    <div style="width: 33%;display:inline;float: left" align="left">
        <label class="Validform_label footlabel">制单人: </label>
        <span class="inputxt footspan"> ${sessionScope.user.realName}</span>
    </div>
    <div style="width: 33%;display: inline;float: left" align="left">
        <label class="Validform_label footlabel">审核人: </label>
        <span class="inputxt footspan"> </span>
    </div>
    <div style="width: 33%;display: inline;float: right" align="left">
        <label class="Validform_label footlabel">审核日期: </label>
        <span class="inputxt footspan"> </span>
    </div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/sales/tScSlStockbill.js"></script>