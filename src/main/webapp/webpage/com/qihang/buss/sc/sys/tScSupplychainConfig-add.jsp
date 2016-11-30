<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>供应链设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>

    <style >
        /**20160629添加页脚样式 **/
        body{
            position: absolute;
            width: 100%;
            height: 100%;
        }
        .footlabel{
            float: left;
            margin-left: 15px;
        }

        .footspan{
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
             action="tScSupplychainConfigController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScSupplychainConfigPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScSupplychainConfigPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScSupplychainConfigPage.createBy }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScSupplychainConfigPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScSupplychainConfigPage.updateBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScSupplychainConfigPage.createDate }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScSupplychainConfigPage.updateDate }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
    <td align="right">
      <label class="Validform_label">
      允许负库存结账:
      </label>
    </td>
    <td class="value">
          <input id="minusInventoryAccount" name="minusInventoryAccount" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">允许负库存结账</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      允许负库存出库:
      </label>
    </td>
    <td class="value">
          <input id="minusInventorySl" name="minusInventorySl" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">允许负库存出库</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      允许盘点有未审核存货单据的数据:
      </label>
    </td>
    <td class="value">
          <input id="stocktakingNotAuditedStockBill" name="stocktakingNotAuditedStockBill" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">允许盘点有未审核存货单据的数据</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      单据保存时系统自动审核:
      </label>
    </td>
    <td class="value">
          <input id="billSaveSystemWithExamine" name="billSaveSystemWithExamine" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据保存时系统自动审核</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      单据审核时系统自带后续业务单据:
      </label>
    </td>
    <td class="value">
          <input id="billExamineSystemWithFollow" name="billExamineSystemWithFollow" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据审核时系统自带后续业务单据</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      不允许手工开入库单:
      </label>
    </td>
    <td class="value">
          <input id="cannotManualOpenInRepertory" name="cannotManualOpenInRepertory" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">不允许手工开入库单</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      不允许入库单数量大于采购订单数量:
      </label>
    </td>
    <td class="value">
          <input id="cannotInRepertoryngtPurchasen" name="cannotInRepertoryngtPurchasen" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">不允许入库单数量大于采购订单数量</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      采购模块启用价格调用顺序:
      </label>
    </td>
    <td class="value">
          <input id="purchaseStartPriceCallOrder" name="purchaseStartPriceCallOrder" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">采购模块启用价格调用顺序</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      采购设置下拉框一:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="purchaseselectOne" type="list"
                        typeGroupCode=""
                        defaultVal="${tScSupplychainConfigPage.purchaseselectOne}" hasLabel="false"
                        title="采购设置下拉框一"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">采购设置下拉框一</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      采购设置下拉框二:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="purchaseselectTwo" type="list"
                        typeGroupCode=""
                        defaultVal="${tScSupplychainConfigPage.purchaseselectTwo}" hasLabel="false"
                        title="采购设置下拉框二"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">采购设置下拉框二</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      不允许手工开出库单:
      </label>
    </td>
    <td class="value">
          <input id="cannotManualOpenOutRepertory" name="cannotManualOpenOutRepertory" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">不允许手工开出库单</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      不允许出库单数量大于销售订单数量:
      </label>
    </td>
    <td class="value">
          <input id="cannotOutRepertoryngtSale" name="cannotOutRepertoryngtSale" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">不允许出库单数量大于销售订单数量</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      销售模块启用价格调用顺序:
      </label>
    </td>
    <td class="value">
          <input id="saleStartPriceCallOrder" name="saleStartPriceCallOrder" type="checkbox" style="width: 150px" class="inputxt"
                 value="1"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">销售模块启用价格调用顺序</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      销售设置下拉框一:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="saleSelectOne" type="list"
                        typeGroupCode=""
                        defaultVal="${tScSupplychainConfigPage.saleSelectOne}" hasLabel="false"
                        title="销售设置下拉框一"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">销售设置下拉框一</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      销售设置下拉框二:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="saleSelectTwo" type="list"
                        typeGroupCode=""
                        defaultVal="${tScSupplychainConfigPage.saleSelectTwo}" hasLabel="false"
                        title="销售设置下拉框二"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">销售设置下拉框二</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      销售设置下拉框三:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="saleSelectOneThree" type="list"
                        typeGroupCode=""
                        defaultVal="${tScSupplychainConfigPage.saleSelectOneThree}" hasLabel="false"
                        title="销售设置下拉框三"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">销售设置下拉框三</label>
    </td>
          </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScSupplychainConfig.js"></script>