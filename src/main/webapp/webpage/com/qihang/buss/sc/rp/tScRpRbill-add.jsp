<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>收款单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        var centerBodyHigh;
        var southBodyHight;
        $(document).ready(function () {
            $('#tt').tabs({
                onSelect: function (title) {
                    $('#tt .panel-body').css('width', 'auto');
                }
            });
            $(".tabs-wrap").css('width', '100%');
            // 结算表
            $.ajax({
                type: "get",
                url: "tScRpRbillController.do?tScRpRbillentry1List&id=${tScRpRbillPage.id}",
                success:function(data){
                    $("#centerBody").html(data);
                    centerBodyHigh = $("#centerBody").height();
                }
            });

            // 应收表
            $.ajax({
                type: "get",
                url: "tScRpRbillController.do?tScRpRbillentry2List&id=${tScRpRbillPage.id}",
                success:function(data){
                    $("#southBody").html(data);
                    southBodyHigh= $("#southBody").height();
                }
            });
            $(".layout-button-up").click(toggleCenter);
            $(".layout-button-down").click(toggleSouth);
            $("#custHeader").dblclick(toggleCenter);
            $("#goodsHeader").dblclick(toggleSouth);

            function toggleCenter(){
                if($("#centerBody").is(":hidden")){
                    $("#centerBody").slideDown();
                    $("#southBody").height(southBodyHigh );
                    $("#tScRpRbillentry2_tablescrolldiv").height(southBodyHigh-50);
                } else {
                    $("#centerBody").slideUp();
                    $("#southBody").height(centerBodyHigh +southBodyHigh );
                    $("#tScRpRbillentry2_tablescrolldiv").height(centerBodyHigh +southBodyHigh-50);
                }
            }

            function toggleSouth(){
                if($("#southBody").is(":hidden")){
                    $("#southBody").slideDown();
                    $("#centerBody").height(centerBodyHigh);
                    $("#tScRpRbillentry1_tablescrolldiv").height(southBodyHigh-50);
                } else {
                    $("#southBody").slideUp();
                    $("#centerBody").height(centerBodyHigh +southBodyHigh  );
                    $("#tScRpRbillentry1_tablescrolldiv").height(centerBodyHigh +southBodyHigh-50);
                }
            }
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
        #centerBody{
            overflow: hidden !important;
        }
        #southBody{
            overflow: hidden !important;
        }
    </style>
</head>
<body style="overflow-x: hidden;">
<input id="entry2Amount" type="hidden">
<t:formvalid formid="formobj" dialog="true" title="收款单" usePlugin="password" layout="table" tiptype="1" beforeSubmit="checkAllAmount"
             action="tScRpRbillController.do?doAdd" saveTemp="true">
<div  class="panel-body layout-body" style="height: 20%">
    <input id="id" name="id" type="hidden"
           value="${tScRpRbillPage.id }">
    <input id="createName" name="createName" type="hidden"
           value="${tScRpRbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden"
           value="${tScRpRbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden"
           value="${tScRpRbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden"
           value="${tScRpRbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden"
           value="${tScRpRbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden"
           value="${tScRpRbillPage.updateDate }">
    <input id="tranType" name="tranType" type="hidden"
           value="105">
    <input id="date" name="date" type="hidden"
           value="${date }">
    <input id="billNo" name="billNo" type="hidden"
           value="${tScRpRbillPage.billNo }">
    <input id="billerId" name="billerId" type="hidden"
           value="${sessionScope.user.id}">
    <input id="checkerId" name="checkerId" type="hidden">
    <input id="checkDate" name="checkDate" type="hidden">
    <input id="checkState" name="checkState" type="hidden"
           value="0">
    <input id="cancellation" name="cancellation" type="hidden"
           value="0">
    <input id="itemId" name="itemId" type="hidden">
    <input id="empId" name="empId" type="hidden">
    <input id="deptId" name="deptId" type="hidden">
    <input id="sonId" name="sonId" value="${sonId}" type="hidden">
    <input id="billType" name="billType" value="1" type="hidden">
    <input id="version" name="version" type="hidden">
    <table cellpadding="0" cellspacing="1" class="formtable" style="z-index: 50;position: absolute">
        <tr>
            <td align="right">
                <label class="Validform_label">客户:</label>
            </td>
            <td class="value">
                <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
                        >
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
                <label class="Validform_label">收款类型:</label>
            </td>
            <td class="value">
                <t:dictSelect field="billType_" type="list" showDefaultOption="true"
                              typeGroupCode="SC_BillType"
                              defaultVal="1"
                              hasLabel="false"
                              extendJson="{datatype:select1,onChange:changeBillType()}"
                              title="收款类型" ></t:dictSelect>
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">收款类型</label>
            </td>
            <td align="right">
                <label class="Validform_label">收款金额:</label>
            </td>
            <td class="value">
                <input id="allAmount" name="allAmount" readonly="readonly" type="text" style="width: 150px" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">收款金额</label>
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
        </tr>
        <tr>

            <td align="right">
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="5">
                <input id="explanation" name="explanation" type="text" style="width: 47%" class="inputxt"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
    </table>
</div>
    <div class="panel-header" id="custHeader" style="margin-top: 100px;" >
        <div class="panel-title">结算表</div>
        <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up" ></a></div>
    </div>
    <div  class="panel-body layout-body" id="centerBody" style="height: 26%">

    </div>
    <div class="panel-header" id="goodsHeader" >
        <div class="panel-title">应收表</div>
        <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-down" ></a></div>
    </div>
    <div  class="panel-body layout-body" id="southBody" style="height: 26%">

    </div>
    <%--<div style="width: auto;height: 200px;">--%>
            <%--&lt;%&ndash; 增加一个div，用于调节页面大小，否则默认太小 &ndash;%&gt;--%>
        <%--<div style="width:800px;height:1px;"></div>--%>
        <%--<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">--%>
            <%--<t:tab href="tScRpRbillController.do?tScRpRbillentry1List&id=${tScRpRbillPage.id}"--%>
                   <%--icon="icon-search" title="结算表" id="tScRpRbillentry1"></t:tab>--%>
            <%--<t:tab href="tScRpRbillController.do?tScRpRbillentry2List&id=${tScRpRbillPage.id}"--%>
                   <%--icon="icon-search" title="应收表" id="tScRpRbillentry2"></t:tab>--%>
        <%--</t:tabs>--%>
    <%--</div>--%>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScRpRbillentry1_table_template">
    <tr>
        <input name="tScRpRbillentry1List[#index#].id" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].createName" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].createBy" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].createDate" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].updateName" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].updateBy" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].updateDate" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].fid" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].findex" value="1" type="hidden"/>
        <input name="tScRpRbillentry1List[#index#].accountId" type="hidden"/>
        <td align="center" bgcolor="white">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addTScRpRbillentry1Btn[#index#]" id="addTScRpRbillentry1Btn[#index#]" href="#"
                   onclick="clickAddTScRpRbillentry1Btn('#index#');"></a>
                <a name="delTScRpRbillentry1Btn[#index#]" id="delTScRpRbillentry1Btn[#index#]" href="#"
                   onclick="clickDelTScRpRbillentry1Btn('#index#');"></a>
            </div>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry1List[#index#].accountName" maxlength="32"
                   onkeypress="keyDownInfo('#index#',event)"
                   onblur="orderListStockBlur('#index#','accountId','accountName');"
                   type="text" class="inputxt popup-select" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">结算账户</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry1List[#index#].amount" maxlength="20"
                   type="text" class="inputxt" style="width:120px;" datatype="vInt"/>
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry1List[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
    </tbody>
    <tbody id="add_tScRpRbillentry2_table_template">
    <tr>
        <input name="tScRpRbillentry2List[#index#].id" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].createName" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].createBy" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].createDate" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].updateName" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].updateBy" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].updateDate" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].fid" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].findex" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].idSrc" type="hidden"/>
        <input name="tScRpRbillentry2List[#index#].classIdSRC" type="hidden"/>
        <td align="center" bgcolor="white">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <%--<a name="addTScRpRbillentry2Btn[#index#]" id="addTScRpRbillentry2Btn[#index#]" href="#"--%>
                   <%--onclick="clickAddTScRpRbillentry2Btn('#index#');"></a>--%>
                <a name="delTScRpRbillentry2Btn[#index#]" id="delTScRpRbillentry2Btn[#index#]" href="#"
                   onclick="clickDelTScRpRbillentry2Btn('#index#');"></a>
            </div>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].classIdName" maxlength="11"
                   type="text" class="inputxt" readonly="readonly" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">源单类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].billNoSrc" readonly="readonly" maxlength="50"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">源单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].dateSrc" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">源单日期</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].billAmount" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">源单金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].billCheckAmount" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">源单已执行金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].billUnCheckAmount" maxlength="20" readonly="readonly"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">源单未执行金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].amount" maxlength="20"
                   type="text" class="inputxt" style="width:120px;" datatype="float"/>
            <label class="Validform_label" style="display: none;">本次收款</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpRbillentry2List[#index#].note" maxlength="255"
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
<script src="webpage/com/qihang/buss/sc/rp/tScRpRbill.js"></script>