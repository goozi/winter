<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>采购订单</title>
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
<input id="configName" value="tPoOrderentryList" type="hidden">
<input type="hidden" id="checkDate" value="${checkDate}">
<t:formvalid formid="formobj" dialog="true" styleClass="fullForm" title="采购订单" usePlugin="password" layout="table"
             tiptype="1"
             beforeSubmit="checkPriceIsZero" saveTemp="true"
             action="tPoOrderController.do?doAdd">
  <input id="id" name="id" type="hidden"
         value="${tPoOrderPage.id }">
  <input id="createName" name="createName" type="hidden"
         value="${tPoOrderPage.createName }">
  <input id="createBy" name="createBy" type="hidden"
         value="${tPoOrderPage.createBy }">
  <input id="createDate" name="createDate" type="hidden"
         value="${tPoOrderPage.createDate }">
  <input id="updateName" name="updateName" type="hidden"
         value="${tPoOrderPage.updateName }">
  <input id="updateBy" name="updateBy" type="hidden"
         value="${tPoOrderPage.updateBy }">
  <input id="updateDate" name="updateDate" type="hidden"
         value="${tPoOrderPage.updateDate }">
  <input id="tranType" name="tranType" type="hidden"
         value="51">
  <input id="idSrc" name="idSrc" type="hidden"
         value="${tPoOrderPage.idSrc }">
  <input id="checkerId" name="checkerId" type="hidden"
         value="">
  <input id="checkDate" name="checkDate" type="hidden"
         value="">
  <input id="checkState" name="checkState" type="hidden"
         value="0">
  <input id="closed" name="closed" type="hidden"
         value="0">
  <input id="autoFlag" name="autoFlag" type="hidden"
         value="0">
  <input id="cancellation" name="cancellation" type="hidden"
         value="0">
  <input id="sonId" name="sonId" type="hidden"
         value="${sonId }">
  <input id="billerId" name="billerId" type="hidden"
         value="${sessionScope.user.id}">
  <input id="isUse" name="isUse" value="0" type="hidden">
  <input id="itemId" name="itemId" type="hidden">
  <input id="stockId" name="stockId" type="hidden">
  <input id="deptId" name="deptId" type="hidden">
  <input id="empId" name="empId" type="hidden">
  <input id="amount" name="amount" type="hidden">
  <input id="billNo" name="billNo" value="${billNo}" type="hidden"/>
  <input name="date" value="${date}" type="hidden"/>
  <table cellpadding="0" cellspacing="1" class="formtable" style="z-index: 50;position: absolute">
    <tr>
        <%--<td align="right">--%>
        <%--<label class="Validform_label">单据日期:</label>--%>
        <%--</td>--%>
        <%--<td class="value">--%>
        <%--<input id="date" name="date" type="text" style="width: 150px"--%>
        <%--class="Wdate" onClick="WdatePicker()" datatype="*"--%>
        <%--onchange="checkBillDate()"--%>
        <%-->--%>
        <%--<span class="Validform_checktip">*--%>
        <%--</span>--%>
        <%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
        <%--</td>--%>
        <%--<td align="right">--%>
        <%--<label class="Validform_label">单据编号:</label>--%>
        <%--</td>--%>
        <%--<td class="value">--%>
        <%--<input id="billNo" name="billNo" readonly="readonly" value="${billNo}" type="text" style="width: 150px"--%>
        <%--class="inputxt"--%>
        <%-->--%>
        <%--<span class="Validform_checktip">*--%>
        <%--</span>--%>
        <%--<label class="Validform_label" style="display: none;">单据编号</label>--%>
        <%--</td>--%>
      <td align="right">
        <label class="Validform_label">供应商:</label>
      </td>
      <td class="value">
        <input id="itemName" name="itemName" type="text" datatype="*" style="width: 150px;" class="inputxt popup-select"
        >
        <span class="Validform_checktip">*
            </span>
        <label class="Validform_label" style="display: none;">供应商</label>
      </td>
      <td align="right">
        <label class="Validform_label">经办人:</label>
      </td>
      <td class="value">
        <input id="empName" name="empName" type="text" datatype="*" style="width: 150px" class="inputxt popup-select"
        >
        <span class="Validform_checktip">*
                </span>
        <label class="Validform_label" style="display: none;">经办人</label>
      </td>
      <td align="right">
        <label class="Validform_label">部门:</label>
      </td>
      <td class="value">
        <input id="deptName" name="deptName" type="text" datatype="*" style="width: 150px" class="inputxt popup-select"
        >
        <span class="Validform_checktip">*
                </span>
        <label class="Validform_label" style="display: none;">部门</label>
      </td>
    </tr>
    <tr>
      <td align="right">
        <label class="Validform_label">仓库:</label>
      </td>
      <td class="value">
        <input id="stockName" name="stockName" type="text" style="width: 150px" class="inputxt popup-select">
        <span class="Validform_checktip">
                </span>
        <label class="Validform_label" style="display: none;">仓库</label>
      </td>
      <td align="right">
        <label class="Validform_label">优惠金额:</label>
      </td>
      <td class="value">
        <input id="rebateAmount" name="rebateAmount" type="text" datatype="num" ignore="ignore" style="width: 150px"
               class="inputxt"
               onblur="changeAllAmount()"
        >
        <span class="Validform_checktip">
                </span>
        <label class="Validform_label" style="display: none;">优惠金额</label>
      </td>
      <td align="right">
        <label class="Validform_label">应付金额:</label>
      </td>
      <td class="value" colspan="3">
        <input id="allAmount" name="allAmount" readonly="readonly" type="text" style="width: 150px"
               class="inputxt"
        >
        <span class="Validform_checktip">
                </span>
        <label class="Validform_label" style="display: none;">应付金额</label>
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
        <label class="Validform_label">手机号码:</label>
      </td>
      <td class="value">
        <input id="mobilePhone" name="mobilePhone" type="text" style="width: 150px" class="inputxt"
        >
        <span class="Validform_checktip">
                </span>
        <label class="Validform_label" style="display: none;">手机号码</label>
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
        <span class="spanbtn-expand" id="btnExpand"></span>
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
        <label class="Validform_label">分支机构:</label>
      </td>
      <td class="value">
        <input id="sonName" name="sonName" type="text" readonly="readonly"
               value="${sonName}" style="width: 150px" class="inputxt"
        >
        <span class="Validform_checktip">
                </span>
        <label class="Validform_label" style="display: none;">分支机构</label>
      </td>
      <td></td>
      <td></td>
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

        <%--<td align="right">--%>
        <%--<label class="Validform_label">源单类型:</label>--%>
        <%--</td>--%>
        <%--<td class="value">--%>
        <%--<input id="classIdSrc" name="classIdSrc" readonly="readonly" type="text" style="width: 150px" class="inputxt"--%>
        <%-->--%>
        <%--<span class="Validform_checktip">--%>
        <%--</span>--%>
        <%--<label class="Validform_label" style="display: none;">源单类型</label>--%>
        <%--</td>--%>
    </tr>
      <%--<tr>--%>
      <%--<td align="right">--%>
      <%--<label class="Validform_label">订单金额:</label>--%>
      <%--</td>--%>
      <%--<td class="value">--%>
      <%--<input id="amount" name="amount" type="text" style="width: 150px" class="inputxt"--%>
      <%-->--%>
      <%--<span class="Validform_checktip">--%>
      <%--</span>--%>
      <%--<label class="Validform_label" style="display: none;">订单金额</label>--%>
      <%--</td>--%>

      <%--</tr>--%>
      <%--<tr>--%>
      <%--<td align="right">--%>
      <%--<label class="Validform_label">执行金额:</label>--%>
      <%--</td>--%>
      <%--<td class="value">--%>
      <%--<input id="checkAmount" name="checkAmount" type="text" style="width: 150px" class="inputxt"--%>
      <%-->--%>
      <%--<span class="Validform_checktip">--%>
      <%--</span>--%>
      <%--<label class="Validform_label" style="display: none;">执行金额</label>--%>
      <%--</td>--%>

      <%--</tr>--%>
    <tr>
        <%--<td align="right">--%>
        <%--<label class="Validform_label">源单编号:</label>--%>
        <%--</td>--%>
        <%--<td class="value">--%>
        <%--<input id="billNoSrc" name="billNoSrc" readonly="readonly" type="text" style="width: 150px" class="inputxt"--%>
        <%-->--%>
        <%--<span class="Validform_checktip">--%>
        <%--</span>--%>
        <%--<label class="Validform_label"  style="display: none;">源单编号</label>--%>
        <%--</td>--%>
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
  <input type="hidden" id="defaultTaxRate" value="${taxRate}">

  <div style="width: auto;height: 200px;margin-top: 100px">
      <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
    <div style="width:800px;height:1px;"></div>
    <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
      <t:tab href="tPoOrderController.do?tPoOrderentryList&id=${tPoOrderPage.id}"
             icon="icon-search" title="采购订单从表" id="tPoOrderentry"></t:tab>
    </t:tabs>
  </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
  <tbody id="add_tPoOrderentry_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 25px;" name="xh" bgcolor="white"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;"><a name="addTScOrderentryBtn[#index#]"
                                   id="addTScOrderentryBtn[#index#]" href="#"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                                   plain="true"
                                   onclick="clickAddTPoOrderentryBtn('#index#');"></a><a
              name="delTScOrderentryBtn[#index#]" id="delTScOrderentryBtn[#index#]" href="#"
              class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
              onclick="clickDelTScOrderentryBtn('#index#');"></a></div>
    </td>
    <input name="tPoOrderentryList[#index#].id" type="hidden"/>
    <input name="tPoOrderentryList[#index#].createName" type="hidden"/>
    <input name="tPoOrderentryList[#index#].createBy" type="hidden"/>
    <input name="tPoOrderentryList[#index#].createDate" type="hidden"/>
    <input name="tPoOrderentryList[#index#].updateName" type="hidden"/>
    <input name="tPoOrderentryList[#index#].updateBy" type="hidden"/>
    <input name="tPoOrderentryList[#index#].updateDate" type="hidden"/>
    <input name="tPoOrderentryList[#index#].fid" type="hidden"/>
    <input name="tPoOrderentryList[#index#].fidSrc" type="hidden"/>
    <input name="tPoOrderentryList[#index#].idSrc" type="hidden"/>
    <input name="tPoOrderentryList[#index#].findex" type="hidden">
    <input name="tPoOrderentryList[#index#].secUnitId" type="hidden">
    <input name="tPoOrderentryList[#index#].basicUnitId" type="hidden">
    <input name="tPoOrderentryList[#index#].secCoefficient" type="hidden">
    <input name="tPoOrderentryList[#index#].secQty" type="hidden">
    <input name="tPoOrderentryList[#index#].stockId" type="hidden">
    <input name="tPoOrderentryList[#index#].itemId" type="hidden">
    <%--<input name="tPoOrderentryList[#index#].unitId" type="hidden">--%>
    <input name="tPoOrderentryList[#index#].amount" type="hidden">
    <input name="tPoOrderentryList[#index#].discountPrice" type="hidden">
    <input name="tPoOrderentryList[#index#].discountAmount" type="hidden">
    <input name="tPoOrderentryList[#index#].price" type="hidden">
    <input name="tPoOrderentryList[#index#].version" type="hidden">
    <input name="tPoOrderentryList[#index#].coefficient" type="hidden">
    <input name="tPoOrderentryList[#index#].basicQty" type="hidden">
    <input name="tPoOrderentryList[#index#].cgLimitPrice" type="hidden"/>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].findex" maxlength="10"--%>
    <%--type="text" class="inputxt" hidden="hidden" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">分录号</label>--%>
    <%--</td>--%>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].itemNo" maxlength="32"
             type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
             onkeypress="keyDownInfo('#index#','item',event)"
             onblur="orderListStockBlur('#index#','itemId','itemNo');"
      >
      <label class="Validform_label" style="display: none;">商品编号</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].itemName" maxlength="32"
             type="text" class="inputxt" style="width:180px;" readonly="readonly"

      >
      <label class="Validform_label" style="display: none;">商品名称</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].model" maxlength="32"
             type="text" class="inputxt" style="width:85px;" readonly="readonly"

      >
      <label class="Validform_label" style="display: none;">规格</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].barCode" maxlength="32"
             type="text" class="inputxt" style="width:65px;" readonly="readonly"

      >
      <label class="Validform_label" style="display: none;">条码</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].stockName" maxlength="32"
             type="text" class="inputxt popup-select" style="width:65px;"
             onblur="orderListStockBlur('#index#','stockId','stockName');"
             onkeypress="orderListStockKeyPress('#index#',event);"

      >
      <label class="Validform_label" style="display: none;">仓库</label>
    </td>
    <td align="left" bgcolor="white">
      <input id="unitId[#index#]" name="tPoOrderentryList[#index#].unitId" maxlength="32"
             type="text" class="inputxt" style="width:80px;"
      >
      <label class="Validform_label" style="display: none;">单位</label>
    </td>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].secUnitId" maxlength="32"--%>
    <%--type="text" class="inputxt" readonly="readonly" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">辅助单位</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].basicUnitId" maxlength="32"--%>
    <%--type="text" class="inputxt" hidden="hidden" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">基本单位</label>--%>
    <%--</td>--%>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].qty" maxlength="20" id="qty#index#"
             type="text" class="inputxt" style="width:65px;" datatype="vInt"

      >
      <label class="Validform_label" style="display: none;">数量</label>
    </td>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].secCoefficient" maxlength="20"--%>
    <%--type="text" class="inputxt" hidden="hidden" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">辅助换算率</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].secQty" maxlength="20"--%>
    <%--type="text" class="inputxt" hidden="hidden" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">辅助数量</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].coefficient" maxlength="20" id="coefficient#index#"--%>
    <%--type="text" class="inputxt" readonly="readonly" style="width:65px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].basicQty" maxlength="20"--%>
    <%--type="text" class="inputxt" readonly="readonly" style="width:65px;"--%>
    <%--id="basicQty#index#"--%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">基本数量</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].price" maxlength="20"--%>
    <%--type="text" class="inputxt" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">单价</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].amount" maxlength="20"--%>
    <%--type="text" class="inputxt" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">不含税金额</label>--%>
    <%--</td>--%>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].taxPriceEx" maxlength="20"
             type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore"
             id="taxPriceEx#index#"
      >
      <label class="Validform_label" style="display: none;">单价</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].taxAmountEx" maxlength="20"
             type="text" class="inputxt" style="width:70px;" datatype="num" ignore="ignore"
             id="taxAmountEx#index#"
      >
      <label class="Validform_label" style="display: none;">金额</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].discountRate" maxlength="20"
             type="text" class="inputxt" style="width:80px;" value="100"
             id="discountRate#index#" datatype="num" ignore="ignore"
      >
      <label class="Validform_label" style="display: none;">折扣率（%）</label>
    </td>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].discountPrice" maxlength="20"--%>
    <%--type="text" class="inputxt" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">不含税折后单价</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].discountAmount" maxlength="20"--%>
    <%--type="text" class="inputxt" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">不含税折后金额</label>--%>
    <%--</td>--%>

    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].taxPrice" maxlength="20"
             type="text" class="inputxt" readonly="readonly" style="width:70px;"
             id="taxPrice#index#"
      >
      <label class="Validform_label" style="display: none;">折后单价</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].inTaxAmount" maxlength="20"
             type="text" class="inputxt" style="width:70px;"
             id="inTaxAmount#index#" datatype="num" ignore="ignore"
      >
      <label class="Validform_label" style="display: none;">折后金额</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].taxRate" maxlength="20"
             type="text" class="inputxt" style="width:70px;" value="${taxRate}"
             id="taxRate#index#" datatype="num" ignore="ignore"
      >
      <label class="Validform_label" style="display: none;">税率（%）</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].taxAmount" maxlength="20"
             type="text" class="inputxt" readonly="readonly" style="width:70px;"
             id="taxAmount#index#"
      >
      <label class="Validform_label" style="display: none;">税额</label>
    </td>
    <td align="left" bgcolor="white">
      <%--<input name="tPoOrderentryList[#index#].jhDate" maxlength="20"--%>
      <%--type="text" class="easyui-datebox" style="width:120px;"--%>
      <%----%>
      <%-->--%>
      <input name="tPoOrderentryList[#index#].jhDate" type="text" style="width: 100px"
             class="Wdate" onClick="WdatePicker()">
      <label class="Validform_label" style="display: none;">收货日期</label>
    </td>
    <td align="left" bgcolor="white">
      <%--<input name="tPoOrderentryList[#index#].freeGifts" maxlength="1"--%>
      <%--type="text" class="inputxt" style="width:120px;"--%>
      <%----%>
      <%-->--%>
      <t:dictSelect field="tPoOrderentryList[#index#].freeGifts" type="list" showDefaultOption="false"
                    typeGroupCode="sf_01"
                    defaultVal="0"
                    width="70px"
                    hasLabel="false"
                    extendJson="{datatype:select1}"
                    title="赠品标记"></t:dictSelect>
      <label class="Validform_label" style="display: none;">赠品标记</label>
    </td>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].stockQty" maxlength="20"
             type="text" class="inputxt" readonly="readonly" style="width:70px;"
             value="0"
      >
      <label class="Validform_label" style="display: none;">收货数量</label>
    </td>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].classIDSrc" maxlength="10"--%>
    <%--type="text" class="inputxt" readonly="readonly" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">源单类型</label>--%>
    <%--</td>--%>
    <%--<td align="left">--%>
    <%--<input name="tPoOrderentryList[#index#].billNoSrc" maxlength="50"--%>
    <%--type="text" class="inputxt" readonly="readonly" style="width:120px;"--%>
    <%----%>
    <%-->--%>
    <%--<label class="Validform_label" style="display: none;">源单编号</label>--%>
    <%--</td>--%>
    <td align="left" bgcolor="white">
      <input name="tPoOrderentryList[#index#].note" maxlength="32"
             type="text" class="inputxt" style="width:180px;"

      >
      <label class="Validform_label" style="display: none;">备注</label>
    </td>
  </tr>
  </tbody>
</table>
<!-- 页脚显示 -->
<%--<div style="width:100%;position: absolute;bottom: 40px;left:10px;" id="footdiv">--%>
<%--<div style="width: 10%;display: inline;float: left">--%>
<%--<label  class="Validform_label footlabel">金额合计: </label>--%>
<%--<span  class="inputxt footspan" id="sumAmount"></span>--%>
<%--</div>--%>
<%--<div style="width: 10%;display: inline;float: left">--%>
<%--<label  class="Validform_label footlabel">折后金额合计: </label>--%>
<%--<span  class="inputxt footspan" id="sumTaxAmount"></span>--%>
<%--</div>--%>
<%--<div style="width: 10%;display: inline;float: left">--%>
<%--<label  class="Validform_label footlabel">数量合计: </label>--%>
<%--<span  class="inputxt footspan" id="sumQty"></span>--%>
<%--</div>--%>
<%--</div>--%>
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
<script src="webpage/com/qihang/buss/sc/sales/tScPoOrder.js"></script>