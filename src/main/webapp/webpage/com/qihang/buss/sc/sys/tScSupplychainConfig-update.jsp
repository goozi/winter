<%@ taglib prefix="e" uri="/jodd" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>供应链设置</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <%--<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>--%>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        //编写自定义JS代码
    </script>
    <!--20160629页脚样式 -->
    <style>
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
    </style>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tSConfigController.do?doSupplyUpdate" tiptype="1">
    <%--<input id="id" name="id" type="hidden" value="${tScSupplychainConfigEntityPage.id }">--%>
    <%--<input id="createName" name="createName" type="hidden" value="${tScSupplychainConfigEntityPage.createName }">--%>
    <%--<input id="createBy" name="createBy" type="hidden" value="${tScSupplychainConfigEntityPage.createBy }">--%>
    <%--<input id="updateName" name="updateName" type="hidden" value="${tScSupplychainConfigEntityPage.updateName }">--%>
    <%--<input id="updateBy" name="updateBy" type="hidden" value="${tScSupplychainConfigEntityPage.updateBy }">--%>
    <%--<input id="createDate" name="createDate" type="hidden" value="${tScSupplychainConfigEntityPage.createDate }">--%>
    <%--<input id="updateDate" name="updateDate" type="hidden" value="${tScSupplychainConfigEntityPage.updateDate }">--%>
    <table style="width: 50%;" align="center" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <th colspan="2" height="25">基本设置</th>
        </tr>
        <tr>

            <td class="value" >
                <input id="minusInventoryAccount" name="minusInventoryAccount" type="checkbox" style="width: 30px"
                       class="inputxt"
                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.minusInventoryAccount=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">允许负库存结账</label>

                <label class="Validform_label">
                    允许负库存结账
                </label>
            </td>


            <td class="value" >
                <input id="minusInventorySl" name="minusInventorySl" type="checkbox" style="width: 30px"
                       class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.minusInventorySl=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">允许负库存出库</label>

                <label class="Validform_label">
                    允许负库存出库
                </label>
            </td>
        </tr>
        <tr>
            <td class="value" >
                <input id="stocktakingNotAuditedStockBill" name="stocktakingNotAuditedStockBill" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.stocktakingNotAuditedStockBill=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">允许盘点有未审核存货单据的数据</label>

                <label class="Validform_label">
                    允许盘点有未审核存货单据的数据
                </label>
            </td>

            <td class="value">
                <input id="billSaveSystemWithExamine" name="billSaveSystemWithExamine" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.billSaveSystemWithExamine=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">单据保存时系统自动审核</label>

                <label class="Validform_label">
                    单据保存时系统自动审核
                </label>
            </td>

        </tr>
        <tr>
            <td class="value" colspan="2">
                <input id="billExamineSystemWithFollow" name="billExamineSystemWithFollow" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.billExamineSystemWithFollow=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">单据审核时系统自带后续业务单据</label>

                <label class="Validform_label">
                    单据审核时系统自带后续业务单据
                </label>
            </td>


        </tr>
        <tr>
            <th colspan="2" height="25">采购设置</th>
        </tr>

        <tr>
            <td class="value">
                <input id="cannotManualOpenInRepertory" name="cannotManualOpenInRepertory" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.cannotManualOpenInRepertory=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">不允许手工开入库单</label>

                <label class="Validform_label">
                    不允许手工开入库单
                </label>
            </td>

            <td class="value">
                <input id="cannotInRepertoryngtPurchasen" name="cannotInRepertoryngtPurchasen" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.cannotInRepertoryngtPurchasen=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">不允许入库单数量大于采购订单数量</label>

                <label class="Validform_label">
                    不允许入库单数量大于采购订单数量
                </label>
            </td>

        </tr>
        <tr>
            <td align="left" colspan="2">智能取价</td>
        </tr>
        <tr>
            <td class="value" colspan="2">
                <input id="purchaseStartPriceCallOrder" name="purchaseStartPriceCallOrder" type="checkbox" onchange="purchaseStartPriceCallOrderChange()"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.purchaseStartPriceCallOrder=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">采购模块启用价格调用顺序</label>

                <label class="Validform_label">
                    采购模块启用价格调用顺序
                </label>
            </td>

        </tr>
        <tr>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">--%>
                    <%--采购设置下拉框一:--%>
                <%--</label>--%>
            <%--</td>--%>
            <td class="value"  align="center" colspan="2">
                <t:dictSelect field="purchaseselectOne" type="list" extendJson="{class:'purchaseselect'}"
                              typeGroupCode="SC_PURCHASE_PRICE_ORDER_TYPE" defaultVal="${tScSupplychainConfigEntityPage.purchaseselectOne}"
                              hasLabel="false" title="采购设置下拉框一"></t:dictSelect>
                <t:dictSelect field="purchaseselectTwo" type="list" extendJson="{class:'purchaseselect'}"
                              typeGroupCode="SC_PURCHASE_PRICE_ORDER_TYPE" defaultVal="${tScSupplychainConfigEntityPage.purchaseselectTwo}"
                              hasLabel="false" title="采购设置下拉框二"></t:dictSelect>
            </td>
        </tr>
        <tr>
            <th colspan="2" height="25">销售设置</th>
        </tr>

        <tr>
            <td class="value">
                <input id="cannotManualOpenOutRepertory" name="cannotManualOpenOutRepertory" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.cannotManualOpenOutRepertory=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">不允许手工开出库单</label>

                <label class="Validform_label">
                    不允许手工开出库单
                </label>
            </td>

            <td class="value">
                <input id="cannotOutRepertoryngtSale" name="cannotOutRepertoryngtSale" type="checkbox"
                       style="width: 30px" class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.cannotOutRepertoryngtSale=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">不允许出库单数量大于销售订单数量</label>

                <label class="Validform_label">
                    不允许出库单数量大于销售订单数量
                </label>
            </td>

        </tr>
        <tr>
            <td align="left" colspan="2">智能取价</td>
        </tr>
        <tr>
            <td class="value" colspan="2">
                <input id="saleStartPriceCallOrder" name="saleStartPriceCallOrder" type="checkbox" style="width: 30px" onchange="saleStartPriceCallOrderChange()"
                       class="inputxt"

                       value="1"
                       <c:if test="${tScSupplychainConfigEntityPage.saleStartPriceCallOrder=='1'}">checked</c:if>>

							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">销售模块启用价格调用顺序</label>

                <label class="Validform_label">
                    销售模块启用价格调用顺序
                </label>
            </td>


        </tr>
        <tr>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">--%>
                    <%--销售设置下拉框一:--%>
                <%--</label>--%>
            <%--</td>--%>
            <td class="value" colspan="2" align="center">
                <t:dictSelect field="saleSelectOne" type="list" extendJson="{class:'saleSelect'}"
                              typeGroupCode="SC_SALES_PRICE_ORDER_TYPE" defaultVal="${tScSupplychainConfigEntityPage.saleSelectOne}" hasLabel="false"
                              title="销售设置下拉框一"></t:dictSelect>
                <t:dictSelect field="saleSelectTwo" type="list" extendJson="{class:'saleSelect'}"
                              typeGroupCode="SC_SALES_PRICE_ORDER_TYPE" defaultVal="${tScSupplychainConfigEntityPage.saleSelectTwo}" hasLabel="false"
                              title="销售设置下拉框二"></t:dictSelect>
                <t:dictSelect field="saleSelectThree" type="list" extendJson="{class:'saleSelect'}"
                              typeGroupCode="SC_SALES_PRICE_ORDER_TYPE" defaultVal="${tScSupplychainConfigEntityPage.saleSelectThree}"
                              hasLabel="false" title="销售设置下拉框三"></t:dictSelect>
            </td>
        </tr>
    </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScSupplychainConfig.js"></script>