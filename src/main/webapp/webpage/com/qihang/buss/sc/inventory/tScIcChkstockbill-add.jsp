<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>盘点单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
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
    </style>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" title="盘点单" usePlugin="password" layout="table" tiptype="1" saveTemp="true"
             action="tScIcChkstockbillController.do?doAdd">
    <input id="id" name="id" type="hidden" value="${tScIcChkstockbillPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tScIcChkstockbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tScIcChkstockbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tScIcChkstockbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tScIcChkstockbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tScIcChkstockbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tScIcChkstockbillPage.updateDate }">
    <input id="tranType" name="tranType" type="hidden" value="160">
    <input id="pdDate" name="pdDate" type="hidden" value="${tScIcChkstockbillPage.pdDate }">
    <input id="checkState" name="checkState" type="hidden" value="0">
    <input id="cancellation" name="cancellation" type="hidden" value="0">
    <input id="sonId" name="sonId" type="hidden" value="${sonId}">
    <input id="billerId" name="billerId" type="hidden" value="${sessionScope.user.id }">
    <input id="billNo" name="billNo" type="hidden" value="${tScIcChkstockbillPage.billNo }">
    <input name="date" type="hidden" value="${date}">
    <input id="stockId" name="stockId" type="hidden">
    <input id="empId" name="empId" type="hidden">
    <input id="deptId" name="deptId" type="hidden">
    <input id="chkType" name="chkType" value="0" type="hidden">
    <input id="version" name="version"  type="hidden">
    <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>

            <td align="right">
                <label class="Validform_label">仓库:</label>
            </td>
            <td class="value">
                <input id="stockName" name="stockName"  type="text" style="width: 150px" class="inputxt popup-select" datatype="*">
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="right">
                <label class="Validform_label">经办人:</label>
            </td>
            <td class="value">
                <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*">
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;">经办人</label>
            </td>
            <td align="right">
                <label class="Validform_label">部门:</label>
            </td>
            <td class="value">
                <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*">
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;">部门</label>
            </td>

        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">盘点类型:</label>
            </td>
            <td class="value">
                <t:dictSelect field="chkType_" width="150" type="list" showDefaultOption="true" extendJson="{onChange:setChkType(),disabled:disabled}"
                              typeGroupCode="SC_ChkType" defaultVal="0" hasLabel="false" title="盘点类型"></t:dictSelect>
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">盘点类型</label>
            </td>
            <td align="right">
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="3">
                <input id="explanation" name="explanation" type="text" style="width: 69%" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
    </table>
    <div style="width: auto;height: 200px;">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:800px;height:1px;"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScIcChkstockbillController.do?tScIcChkstockbillentryList&id=${tScIcChkstockbillPage.id}"
                   icon="icon-search" title="盘点单明细" id="tScIcChkstockbillentry"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScIcChkstockbillentry_table_template">
    <tr>
        <td align="center" bgcolor="white">
            <div style="width: 30px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addTScIcChkstockbillentryBtn[#index#]" id="addTScIcChkstockbillentryBtn[#index#]" href="#"
                   onclick="clickAddTScIcChkstockbillentryBtn('#index#');"></a>
                <a name="delTScIcChkstockbillentryBtn[#index#]" id="delTScIcChkstockbillentryBtn[#index#]" href="#"
                   onclick="clickDelTScIcChkstockbillentryBtn('#index#');"></a>
            </div>
        </td>
        <input name="tScIcChkstockbillentryList[#index#].id" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].createName" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].createBy" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].createDate" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].updateName" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].updateBy" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].updateDate" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].secUnitId" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].basicUnitId" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].secQty" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].fid" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].findex" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].itemId" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].stockId" type="hidden"/>
        <input name="tScIcChkstockbillentryList[#index#].idSrc" type="hidden"/>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].itemNo" maxlength="32"
                   onkeypress="keyDownInfo('#index#','item',event)" onblur="orderListStockBlur('#index#','itemId','itemNo');"
                   type="text" class="inputxt popup-select" style="width:105px;"/>
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].itemName" maxlength="32" readonly="readonly"
                   type="text" class="inputxt" style="width:180px;"/>
            <label class="Validform_label" style="display: none;">商品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].model" maxlength="32" readonly="readonly"
                   type="text" class="inputxt" style="width:85px;"/>
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].barCode" maxlength="32" readonly="readonly"
                   type="text" class="inputxt" style="width:65px;"/>
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <%--<td align="left" bgcolor="white">--%>
            <%--<input name="tScIcChkstockbillentryList[#index#].stockName" maxlength="32"--%>
                   <%--type="text" class="inputxt" style="width:65px;"/>--%>
            <%--<label class="Validform_label" style="display: none;">仓库</label>--%>
        <%--</td>--%>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].batchNo" maxlength="100" readonly="readonly"
                   type="text" class="inputxt" style="width:80px;"/>
            <label class="Validform_label" style="display: none;">批号</label>
        </td>
        <td align="left" bgcolor="white">
            <input id="unitId[#index#]" name="tScIcChkstockbillentryList[#index#].unitId" maxlength="32"
                   type="text" class="inputxt" style="width:50px;"/>
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].qty" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;"/>
            <label class="Validform_label" style="display: none;">账面箱数</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].smallQty" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">账面散数</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].basicQty" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">账面数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].coefficient" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:65px;" />
            <label class="Validform_label" style="display: none;">换算率</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].chkQty" maxlength="20"
                   type="text" class="inputxt" style="width:65px;" onchange="changeQty('#index#')" datatype="vFloat"/>
            <label class="Validform_label" style="display: none;">箱数</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].chkSmallQty" maxlength="20"
                   type="text" class="inputxt" style="width:65px;" onchange="changeQty('#index#')" datatype="vFloat"/>
            <label class="Validform_label" style="display: none;">散数</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].chkBasicQty" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" readonly="readonly" />
            <label class="Validform_label" style="display: none;">盘点数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].diffQty" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">差异数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].costPrice" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">成本单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].amount" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">账面金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].chkAmount" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">盘点金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].diffAmount" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:70px;" />
            <label class="Validform_label" style="display: none;">差异金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcChkstockbillentryList[#index#].note" maxlength="80"
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
<script src="webpage/com/qihang/buss/sc/inventory/tScIcChkstockbill.js"></script>