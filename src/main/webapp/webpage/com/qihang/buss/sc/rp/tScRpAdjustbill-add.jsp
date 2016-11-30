<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>应收调账</title>
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
<t:formvalid formid="formobj" dialog="true" title="${title}" usePlugin="password" layout="table" tiptype="1"
             action="tScRpAdjustbillController.do?doAdd" saveTemp="true">
    <input id="id" name="id" type="hidden" value="${tScRpAdjustbillPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tScRpAdjustbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tScRpAdjustbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tScRpAdjustbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tScRpAdjustbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tScRpAdjustbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tScRpAdjustbillPage.updateDate }">
    <input id="tranType" name="tranType" type="hidden" value="${tScRpAdjustbillPage.tranType }">
    <input name="date" type="hidden" value="${date}">
    <input name="billNo" id="billNo" type="hidden" value="${tScRpAdjustbillPage.billNo }">
    <input id="checkAmount" name="checkAmount" type="hidden" value="0">
    <input id="idSrc" name="idSrc" type="hidden">
    <input id="billerId" name="billerId" type="hidden"value="${sessionScope.user.id}">
    <input id="checkerId" name="checkerId" type="hidden">
    <input id="checkDate" name="checkDate" type="hidden">
    <input id="checkState" name="checkState" type="hidden" value="0">
    <input id="cancellation" name="cancellation" type="hidden" value="0">
    <input id="sonId" name="sonId" type="hidden" value="${sonId}">
    <input id="itemId" name="itemId" type="hidden">
    <input id="empId" name="empId" type="hidden">
    <input id="deptId" name="deptId" type="hidden">
    <input id="version" name="version" type="hidden" value="${tScRpAdjustbillPage.version }">
    <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">${itemTitle}:</label>
            </td>
            <td class="value">
                <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
                        >
      <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">${itemTitle}</label>
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
                <label class="Validform_label">${amountStr}金额:</label>
            </td>
            <td class="value">
                <input id="allAmount" name="allAmount" readonly="readonly" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">${amountStr}金额</label>
            </td>
            <td align="right">
                <label class="Validform_label">分支机构:</label>
            </td>
            <td class="value">
                <input id="sonName" name="sonName" readonly="readonly" value="${sonName}" type="text" style="width: 150px" class="inputxt"
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
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="5">
                <input id="explanation" name="explanation" type="text" style="width: 47%;" class="inputxt"
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
            <t:tab href="tScRpAdjustbillController.do?tScRpAdjustbillentryList&id=${tScRpAdjustbillPage.id}"
                   icon="icon-search" title="应收调账从表" id="tScRpAdjustbillentry"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScRpAdjustbillentry_table_template">
    <tr>
        <input name="tScRpAdjustbillentryList[#index#].id" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].createName" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].createBy" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].createDate" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].updateName" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].updateBy" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].updateDate" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].fid" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].findex" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].expId" type="hidden"/>
        <td align="center" bgcolor="white">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addTScRpAdjustbillentryBtn[#index#]" id="addTScRpAdjustbillentryBtn[#index#]" href="#"
                   onclick="clickAddTScRpAdjustbillentryBtn('#index#');"></a>
                <a name="delTScRpAdjustbillentryBtn[#index#]" id="delTScRpAdjustbillentryBtn[#index#]" href="#"
                   onclick="clickDelTScRpAdjustbillentryBtn('#index#');"></a>
            </div>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpAdjustbillentryList[#index#].expName" maxlength="32"
                   onkeypress="keyDownInfo('#index#',event)" onblur="orderListStockBlur('#index#','expId','expName');"
                   type="text" class="inputxt popup-select" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">收支项目</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpAdjustbillentryList[#index#].amount" maxlength="20" datatype="fInt"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpAdjustbillentryList[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:120px;"/>
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
<script src="webpage/com/qihang/buss/sc/rp/tScRpAdjustbill.js"></script>