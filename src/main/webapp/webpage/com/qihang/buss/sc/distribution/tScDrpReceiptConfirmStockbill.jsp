<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>发货管理</title>
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
            $.ajax({
                type: "get",
                url: "tScDrpReceiptConfirmlController.do?tScDrpReceiptConfirmStockbillentryList&id=${tScDrpStockbillPage.id}",
                success: function (data) {
                    $("#centerBody").html(data);
                    centerBodyHigh = $("#centerBody").height();
                }
            });
        });
    </script>

    <style >
        /** 页脚样式  **/
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
        .spanBtn{
            background-color:#CCE1F3;
            display:-moz-inline-box;
            display:inline-block;
            width:20px;
            height: 20px;
            text-align: center;
        }
    </style>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="myform" dialog="true"  usePlugin="password" layout="table" tiptype="1" windowType="dialog"  action="tScDrpReceiptConfirmlController.do?doConfirm">
    <input type="hidden" id="checkDate" value="${checkDate}">
    <input id="id" name="id" type="hidden" value="${tScDrpStockbillPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tScDrpStockbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tScDrpStockbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tScDrpStockbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tScDrpStockbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tScDrpStockbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tScDrpStockbillPage.updateDate }">
    <input id="tranType" name="tranType" type="hidden" value="1505">
    <input id="amount" name="amount" type="hidden" value="${tScDrpStockbillPage.amount }">
    <input id="checkAmount" name="checkAmount" type="hidden" value="${tScDrpStockbillPage.checkAmount }">
    <input id="affirmStatus" name="affirmStatus" type="hidden" value="${tScDrpStockbillPage.affirmStatus }">
    <input id="affirmID" name="affirmID" type="hidden" value="${tScDrpStockbillPage.affirmID }">
    <input id="interIDSrc" name="interIDSrc" type="hidden" value="${tScDrpStockbillPage.interIDSrc }">
    <input id="checkerID" name="checkerID" type="hidden" value="${tScDrpStockbillPage.checkerID }">
    <input id="billerID" name="billerID" type="hidden" value="${tScDrpStockbillPage.billerID }">
    <input id="accountID" name="accountID" type="hidden" value="${tScDrpStockbillPage.accountID }">
    <input id="checkDate1" name="checkDate" type="hidden" value="${tScDrpStockbillPage.checkDate }">
    <input id="checkState" name="checkState" type="hidden" value="${tScDrpStockbillPage.checkState }">
    <input id="cancellation" name="cancellation" type="hidden" value="${tScDrpStockbillPage.cancellation }">
    <input id="dealerID" name="dealerID" type="hidden" value="${tScDrpStockbillPage.dealerID }">
    <input id="empID" name="empID" type="hidden" value="${tScDrpStockbillPage.empID }">
    <input id="deptID" name="deptID" type="hidden" value="${tScDrpStockbillPage.deptID }">
    <input id="rStockId" name="rStockId" type="hidden" value="${tScDrpStockbillPage.rStockId }">
    <input id="sonID" name="sonID" type="hidden" value="${sessionScope.user.currentDepart.id}">
    <input id="billNo" name="billNo" type="hidden" value="${tScDrpStockbillPage.billNo}">
    <input id="date" name="date" type="hidden" value="${tScDrpStockbillPage.date}">
    <input id="json" name="json" type="hidden"/>
    <input id="iscreditmgr" type="hidden"/>
    <input id="countAmount" type="hidden"/>
    <input id="creditamount" type="hidden"/>
    <div style="width: auto;height: 210px;">
        <div class="panel-body layout-body" id="centerBody" style="height: 70%"></div>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 10px;left:10px;width: 100%;">
    <div style="width: 16%;display:inline;float: left" align="left">
        <label  class="Validform_label footlabel">制单人: </label>
        <span  class="inputxt footspan">${sessionScope.user.realName}</span>
    </div>
   <%-- <div style="width: 16%;display:inline;float: left" align="left">
        <label  class="Validform_label footlabel">审核人: </label>
        <span  class="inputxt footspan"></span>
    </div>
    <div style="width: 16%;display:inline;float: left" align="left">
        <label  class="Validform_label footlabel">审核日期: </label>
        <span  class="inputxt footspan"></span>
    </div>--%>
    <div style="width: 16%;display:inline;float: left" align="left">
        <label  class="Validform_label footlabel">确认人: </label>
        <span  class="inputxt footspan"></span>
    </div>
    <div style="width: 16%;display:inline;float: left" align="left">
        <label  class="Validform_label footlabel">确认时间: </label>
        <span  class="inputxt footspan"></span>
    </div>
   <%-- <div style="width: 16%;display:inline;float: left" align="left">
        <label  class="Validform_label footlabel">丢失金额: </label>
        <span  class="inputxt footspan"></span>
    </div>--%>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpReceiptConfirmStockbillList.js"></script>
