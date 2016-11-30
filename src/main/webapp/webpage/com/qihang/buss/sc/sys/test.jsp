<%--
  Created by IntelliJ IDEA.
  User: LenovoM4550
  Date: 2016-06-21
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
  <title>Title</title>
  <t:base type="jquery,easyui,tools"/>
  <script type="text/javascript" src="plug-in/scm/js/computingTools.js"></script>
  <style>
    .readonly{
      background-color: gray;
    }
    .defaulthide{
      background-color: yellow !important;
    }
  </style>
</head>
<body>
<t:formvalid formid="testForm" layout="table" dialog="true">
  <table style="width: 400px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
      <td align="right">
        <label for="qty" class="Validform_label">数量：</label>
      </td>
      <td class="value">
        <input id="qty" type="text" class="inputxt"/>*
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="basicQty" class="Validform_label">基本数量：</label>
      </td>
      <td class="value">
        <input id="basicQty" type="text" class="inputxt readonly" readonly="readonly"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="secQty" class="Validform_label">辅助数量：</label>
      </td>
      <td class="value">
        <input id="secQty" type="text" class="inputxt defaulthide" readonly="readonly"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="coefficient" class="Validform_label">换算率：</label>
      </td>
      <td class="value">
        <input id="coefficient" type="text" class="inputxt readonly" readonly="readonly" value="10"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="secCoefficient" class="Validform_label">辅助换算率：</label>
      </td>
      <td class="value">
        <input id="secCoefficient" type="text" class="inputxt defaulthide" readonly="readonly" value="20"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="price" class="Validform_label">不含税单价：</label>
      </td>
      <td class="value">
        <input id="price" type="text" class="inputxt defaulthide"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="amount" class="Validform_label">不含税金额：</label>
      </td>
      <td class="value">
        <input id="amount" type="text" class="inputxt defaulthide"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="discountRate" class="Validform_label">折扣率：</label>
      </td>
      <td class="value">
        <input id="discountRate" type="text" class="inputxt" value="90"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="discountPrice" class="Validform_label">不含税折后单价：</label>
      </td>
      <td class="value">
        <input id="discountPrice" type="text" class="inputxt defaulthide"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="discountAmount" class="Validform_label">不含税折后金额：</label>
      </td>
      <td class="value">
        <input id="discountAmount" type="text" class="inputxt defaulthide"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="taxRate" class="Validform_label">税率：</label>
      </td>
      <td class="value">
        <input id="taxRate" type="text" class="inputxt" value="17"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="taxPriceEx" class="Validform_label">单价：</label>
      </td>
      <td class="value">
        <input id="taxPriceEx" type="text" class="inputxt"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="amountEx" class="Validform_label">金额：</label>
      </td>
      <td class="value">
        <input id="amountEx" type="text" class="inputxt"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="taxPrice" class="Validform_label">折后单价：</label>
      </td>
      <td class="value">
        <input id="taxPrice" type="text" class="inputxt readonly" readonly="readonly"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="inTaxAmount" class="Validform_label">折后金额：</label>
      </td>
      <td class="value">
        <input id="inTaxAmount" type="text" class="inputxt"/>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label for="taxAmount" class="Validform_label">税额：</label>
      </td>
      <td class="value">
        <input id="taxAmount" type="text" class="inputxt readonly" readonly="readonly"/>
      </td>
    </tr>
  </table>
</t:formvalid>
</body>
</html>
