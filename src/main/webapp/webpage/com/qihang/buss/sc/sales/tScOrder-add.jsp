<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>销售订单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
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
//            $("#btnshow").css("cursor","pointer").click(function(){
//                $(".formtable tr").each(function(i,data){
//                    if(i > 2){
//                        $(this).toggle();
//                    }
//                });
//            });
//            $("#btnshow").toggle(
//                    function () {
//                        $(this).html("&nbsp;&nbsp;-&nbsp;&nbsp;");
//                    },
//                    function () {
//                        $(this).html("&nbsp;&nbsp;+&nbsp;&nbsp;");
//                    }
//            );
//            $(".formtable tr").each(function(i,data){
//                if(i > 2){
//                    $(this).toggle();
//                }
//            });
        });
    </script>
    <style type="text/css" >
        body{
            position: absolute;
            width: 100%;
            height: 100%;
        }
        .footlabel{
            float: left;
            margin-left: 35px;
        }
        .footspan{
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
<input id="tempRecoveryExt" type="hidden" value="doTempRecoveryExt">
<t:formvalid formid="formobj" dialog="true" title="销售订单" usePlugin="password" layout="table" tiptype="1" beforeSubmit="checkForm" saveTemp="true"
             action="tScOrderController.do?doAdd" >
    <input id="id" name="id" type="hidden"
           value="${tScOrderPage.id }">
    <input id="createName" name="createName" type="hidden"
           value="${tScOrderPage.createName }">
    <input id="createBy" name="createBy" type="hidden"
           value="${tScOrderPage.createBy }">
    <input id="createDate" name="createDate" type="hidden"
           value="${tScOrderPage.createDate }">
    <input id="updateName" name="updateName" type="hidden"
           value="${tScOrderPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden"
           value="${tScOrderPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden"
           value="${tScOrderPage.updateDate }">
    <input id="version" name="version" type="hidden"
           value="${tScOrderPage.version }">
    <input id="tranType" name="tranType" value="102"  type="hidden">
    <input id="itemID" name="itemID" type="hidden"/>
    <input id="empID" name="empID" type="hidden"/>
    <input id="deptID" name="deptID" type="hidden"/>
    <input id="stockID" name="stockID" type="hidden" />
    <input id="amount" name="amount" type="hidden"/>
    <input id="checkAmount" name="checkAmount" type="hidden"/>
    <input id="interIDSrc" name="interIDSrc" type="hidden"/>
    <input id="billNoSrc" name="billNoSrc" type="hidden"/>
    <input id="checkerID" name="checkerID" type="hidden"/>
    <input id="billerID" name="billerID" type="hidden"/>
    <input id="checkDate" name="checkDate" type="hidden"/>
    <input id="checkState" name="checkState" type="hidden"/>
    <input id="closed" name="closed" type="hidden"/>
    <input id="autoFlag" name="autoFlag"  type="hidden"/>
    <input id="cancellation" name="cancellation" type="hidden"/>
    <input id="sonID" name="sonID" value="${sonId}" type="hidden"/>
    <input name="mall" value="1" type="hidden"/>
    <input id="iscreditmgr" type="hidden">
    <input id="countAmount" type="hidden">
    <input id="creditamount" type="hidden">
    <input id="configName" name="configName" value="tScOrderentryList" type="hidden"/>
    <input id="sumAmount" name="sumAmount"  type="hidden" value='[{"itemName":"taxAmountEx","mainItemId":"amount"},{"itemName":"weight","mainItemId":"weight"}]'/>
    <input id="billNo" name="billNo" value="${tScOrderPage.billNo}" type="hidden"/>
    <input name="date" value="${date}" type="hidden"/>
    <table cellpadding="0" cellspacing="1" class="formtable" style="position: absolute;z-index: 50;width: 100% " >
        <tr>
            <%--<td align="right" width="10%">--%>
                <%--<label class="Validform_label">单据编号:</label>--%>
            <%--</td>--%>
            <%--<td class="value" width="23%">--%>
                <%--<input id="billNo" name="billNo"  type="text" style="width: 150px" class="inputxt" readonly="true" value="${tScOrderPage.billNo}"--%>
                        <%-->--%>
      <%--<span class="Validform_checktip"> *--%>
                <%--</span>--%>
                <%--<label class="Validform_label" style="display: none;">单据编号</label>--%>
            <%--</td>--%>
            <%--<td align="right"  width="10%">--%>
                <%--<label class="Validform_label">单据日期:</label>--%>
            <%--</td>--%>
            <%--<td class="value"  width="23%">--%>
                <%--<input id="date" name="date" type="text" style="width: 150px"--%>
                       <%--class="Wdate" onClick="WdatePicker()" datatype="*"--%>
                       <%--value='<fmt:formatDate value='${tScOrderPage.date}' type="date" pattern="yyyy-MM-dd"/>'--%>
                        <%-->--%>
      <%--<span class="Validform_checktip"> *--%>
                <%--</span>--%>
                <%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
            <%--</td>--%>
            <td align="right" width="10%">
                <label class="Validform_label">客户:</label>
            </td>
            <td class="value"  width="24%">
                <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select"   datatype="*"
                        />
      <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">客户</label>
            </td>
                <td align="right">
                    <label class="Validform_label">经办人:</label>
                </td>
                <td class="value">
                    <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
                            >
      <span class="Validform_checktip">*
                </span>
                    <label class="Validform_label" style="display: none;">经办人</label>
                </td>
                <td align="right">
                    <label class="Validform_label">部门:</label>
                </td>
                <td class="value">
                    <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
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
                <label class="Validform_label">联系人:</label>
            </td>
            <td class="value">
                <input id="contact" name="contact" type="text" style="width: 150px"
                       class="inputxt" datatype="*1-25" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">联系人</label>
            </td>
            <td align="right">
                <label class="Validform_label">手机号码:</label>
            </td>
            <td class="value">
                <input id="mobilePhone" name="mobilePhone" type="text" style="width:
150px" class="inputxt" datatype="m" ignore="ignore"
                        >
               <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">手机号码</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">电话:</label>
            </td>
            <td class="value">
                <input id="phone" name="phone" type="text" style="width: 150px"
                       class="inputxt" datatype="po"  ignore="ignore"
                        >
                 <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">电话</label>
            </td>
            <td align="right">
                <label class="Validform_label">传真:</label>
            </td>
            <td class="value">
                <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt" datatype="f" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">传真</label>
            </td>
            <td align="right">
                <label class="Validform_label">分支机构:</label>
            </td>
            <td class="value">
                <input id="sonName" name="sonName" type="text" readonly="readonly" value="${sonName}" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">分支机构</label>
                <span class="spanbtn-expand" id="btnExpand"></span>
            </td>
        </tr>
        <tr>

            <td align="right">
                <label class="Validform_label">联系地址:</label>
            </td>
            <td class="value" colspan="5">
                <input id="address" name="address" type="text" style="width:80%"
                       class="inputxt" datatype="*1-126" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">联系地址</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">收款方式:</label>
            </td>
            <td class="value">
                <t:dictSelect field="fetchStyle" type="list" showDefaultOption="true"
                              typeGroupCode="SC_PAY_TYPE"
                              hasLabel="false"
                              title="收款方式" ></t:dictSelect>
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">收款方式</label>
            </td>
            <td align="right">
                <label class="Validform_label">预收金额:</label>
            </td>
            <td class="value">
                <input id="preAmount" name="preAmount" type="text" readonly="true" style="width: 150px" class="inputxt" datatype="num" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">预收金额</label>
            </td>
            <td align="right">
                <label class="Validform_label">优惠金额:</label>
            </td>
            <td class="value">
                <input id="rebateAmount" name="rebateAmount" type="text" style="width: 150px" class="inputxt" datatype="num" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">优惠金额</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">物流费用:</label>
            </td>
            <td class="value">
                <input id="freight" name="freight" type="text" style="width: 150px" class="inputxt"  datatype="num" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">物流费用</label>
            </td>
            <td align="right">
                <label class="Validform_label">重量:</label>
            </td>
            <td class="value">
                <input id="weight" name="weight" type="text" style="width: 150px" class="inputxt" readonly="true"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">重量</label>
            </td>
            <td align="right">
                <label class="Validform_label">应收金额:</label>
            </td>
            <td class="value">
                <input id="allAmount" name="allAmount" type="text" style="width: 150px" class="inputxt" readonly="true"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">应收金额</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">订单来源:</label>
            </td>
            <td class="value">
                <input id="mall" value="系统新增" type="text" style="width: 150px" class="inputxt" readonly="true"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">订单来源</label>
            </td>
            <td align="right">
                <label class="Validform_label">商城单号:</label>
            </td>
            <td class="value">
                <input id="mallOrderID" name="mallOrderID" type="text" style="width: 150px" class="inputxt"  readonly="true"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">商城单号</label>
            </td>
            <td align="right">
                <label class="Validform_label">源单类型:</label>
            </td>
            <td class="value">
                <input id="classIDSrc" name="classIDSrc" type="text" style="width: 150px" class="inputxt" readonly="true"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">源单类型</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="5">
                <input id="explanation" name="explanation"   style="width: 87.5%" class="inputxt"  datatype="*1-126" ignore="ignore" />
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
    </table>

    <div style="width: auto;height: 200px;margin-top: 100px;">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:800px;height:1px"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScOrderController.do?tScOrderentryList&id=${tScOrderPage.id}"
                   icon="icon-search" title="销售订单从表" id="tScOrderentry" ></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScOrderentry_table_template">
    <tr>
        <td align="center" bgcolor="white">
            <input name="tScOrderentryList[#index#].itemID"  type="hidden"/>
            <input name="tScOrderentryList[#index#].stockID"  type="hidden"/>
            <input name="tScOrderentryList[#index#].indexNumber" type="hidden"  value="1"/>
            <input name="tScOrderentryList[#index#].basicUnitID" type="hidden"/>
            <input name="tScOrderentryList[#index#].secUnitID" type="hidden"/>
            <input name="tScOrderentryList[#index#].secCoefficient" type="hidden"/>
            <input name="tScOrderentryList[#index#].secQty" type="hidden"/>
            <input name="tScOrderentryList[#index#].price" type="hidden"/>
            <input name="tScOrderentryList[#index#].amount" type="hidden"/>
            <input name="tScOrderentryList[#index#].discountPrice" type="hidden"/>
            <input name="tScOrderentryList[#index#].discountAmount" type="hidden"/>
            <input name="tScOrderentryList[#index#].itemWeight" type="hidden"/>
            <input name="tScOrderentryList[#index#].xsLimitPrice" type="hidden"/>
            <input name="tScOrderentryList[#index#].salesID" type="hidden"/>
            <input name="tScOrderentryList[#index#].freeGifts" value="0" type="hidden"/>
            <input name="tScOrderentryList[#index#].isFreeGift" value="0" type="hidden"/>
            <input name="tScOrderentryList[#index#].coefficient"  type="hidden"/>
            <input name="tScOrderentryList[#index#].basicQty"  type="hidden"/>
            <input name="tScOrderentryList[#index#].classIDSrc"  type="hidden"/>
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center"  bgcolor="white"><div style="width: 80px;"><a name="addTScOrderentryBtn[#index#]" id="addTScOrderentryBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScOrderentryBtn('#index#');"></a><a name="delTScOrderentryBtn[#index#]" id="delTScOrderentryBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn('#index#');"></a></div></td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].itemNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*" onkeypress="orderListIcitemKeyPress('#index#',event);" onblur="orderListIcItemBlur('#index#');">
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].itemName"  type="text" class="inputxt"  style="width:180px; background-color: rgb(226, 226, 226);" readonly="true"/>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].itemModel"  type="text" class="inputxt"  style="width:85px; background-color: rgb(226, 226, 226);" readonly="true"/>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].itemBarcode"  type="text" class="inputxt"  style="width:65px; background-color: rgb(226, 226, 226);" readonly="true"/>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].stockName"  type="text" class="inputxt popup-select"  style="width:65px;" onblur="orderListStockBlur('#index#');" onkeypress="orderListStockKeyPress('#index#',event);"/>
        </td>
        <td align="left" bgcolor="white">
            <input  id="tScOrderentryList[#index#].unitID"  name="tScOrderentryList[#index#].unitID" class="inputxt"   style="width:50px;">
                <%--<select id="tScOrderentryList[#index#].unitID" name="tScOrderentryList[#index#].unitID" class="inputxt"  style="width:80px;" datatype="*"></select>--%>
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].qty" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="vInt">
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <%--<td align="left" bgcolor="white">--%>
            <%--<input name="tScOrderentryList[#index#].coefficient"  style="width:70px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
            <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
        <%--</td>--%>
        <%--<td align="left" bgcolor="white">--%>
            <%--<input name="tScOrderentryList[#index#].basicQty"  style="width:70px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
            <%--<label class="Validform_label" style="display: none;">基本数量</label>--%>
        <%--</td>--%>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].discountRate" maxlength="32" type="text" class="inputxt"  style="width:70px;"  datatype="num" ignore="ignore" value="100">
            <label class="Validform_label" style="display: none;">折扣率(%)</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].taxPrice" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true"/>
            <label class="Validform_label" style="display: none;">折后单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore">
            <label class="Validform_label" style="display: none;">折后金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].mallPrice" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">商城单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].mallAmount" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">商城金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].weight" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">重量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].taxRate" maxlength="32" type="text" class="inputxt"  style="width:70px;"  datatype="num" ignore="ignore" value="0">
            <label class="Validform_label" style="display: none;">税率(%)</label>
        </td>
        <%--<td align="left" a="bumingbai">--%>
        <%--<input name="tScOrderentryList[0].price" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" ignore="ignore" readonly="true">--%>
        <%--<label class="Validform_label" style="display: none;">不含税单价</label>--%>
        <%--</td>--%>
        <%--<td align="left" a="bumingbai">--%>
        <%--<input name="tScOrderentryList[0].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"  readonly="true">--%>
        <%--<label class="Validform_label" style="display: none;">不含税金额</label>--%>
        <%--</td>--%>
        <%--<td align="left"  a="bumingbai">--%>
        <%--<input name="tScOrderentryList[0].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;">--%>
        <%--<label class="Validform_label" style="display: none;">不含税折后单价</label>--%>
        <%--</td>--%>
        <%--<td align="left"  a="bumingbai">--%>
        <%--<input name="tScOrderentryList[0].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;">--%>
        <%--<label class="Validform_label" style="display: none;">不含税折后金额</label>--%>
        <%--</td>--%>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">税额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].fetchDate" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;">
            <label class="Validform_label" style="display: none;">交货日期</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].salesName" maxlength="32" type="text" class="inputxt"  style="width:80px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">促销类型</label>
        </td>
        <td align="left" bgcolor="white">
            <t:dictSelect field="tScOrderentryList[#index#].freeGifts_" type="list" typeGroupCode="sf_01" width="80px" defaultVal="0" hasLabel="false" showDefaultOption="false" title="赠品标记"></t:dictSelect>
            <label class="Validform_label" style="display: none;">赠品标记</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].stockQty" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);"  readonly="true">
            <label class="Validform_label" style="display: none;">发货数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].billNoSrc" maxlength="50" type="text" class="inputxt"  style="width:90px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">源单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScOrderentryList[#index#].classIDName" maxlength="32" type="text" class="inputxt"  style="width:90px; background-color: rgb(226, 226, 226);" readonly="true">
            <label class="Validform_label" style="display: none;">源单类型</label>
        </td>
        <td align="left" bgcolor="white"><input name="tScOrderentryList[#index#].note" maxlength="255" type="text" class="inputxt"  style="width:180px;" datatype="*1-125" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">备注 </label>
        </td>
    </tr>
    </tbody>
</table>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;width:100%">
    <div style="width: 33%;float: left">
        <label  class="Validform_label footlabel">制单人: </label>
        <span  class="inputxt footspan">${sessionScope.user.realName}</span>
    </div>
    <div style="width: 33%;float: left">
    <label  class="Validform_label footlabel">审核人: </label>
    <span  class="inputxt footspan"></span>
    </div>
    <div style="width: 34%;float: left"></div>
    <label  class="Validform_label footlabel">审核时间: </label>
    <span  class="inputxt footspan"></span>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/sales/tScOrder.js"></script>