<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>采购换货单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript" src="plug-in/scm/js/computingTools.js"></script>
    <script type="text/javascript">
        console.log(${tScIcJhstockbillPage.cancellation });
        var centerBodyHigh;
        var southBodyHight;
        $(document).ready(function () {
            $('#tt').tabs({
                onSelect: function (title) {
                    $('#tt .panel-body').css('width', 'auto');
                }
            });
            $(".tabs-wrap").css('width', '100%');

            // 退货单
            $.ajax({
                type: "get",
                url: "tScIcJhstockbillController.do?tScIcJhstockbillentry1List&id=${tScIcJhstockbillPage.id}&load=${load}",
                success:function(data){
                    $("#centerBody").html(data);
                    centerBodyHigh = $("#centerBody").height();
                }
            });

            // 换货单
            $.ajax({
                type: "get",
                url: "tScIcJhstockbillController.do?tScIcJhstockbillentry2List&id=${tScIcJhstockbillPage.id}&load=${load}",
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
                    $("#tScIcJhstockbillentry2_tablescrolldiv").height(southBodyHigh-50);
                } else {
                    $("#centerBody").slideUp();
                    $("#southBody").height(centerBodyHigh +southBodyHigh );
                    $("#tScIcJhstockbillentry2_tablescrolldiv").height(centerBodyHigh +southBodyHigh-50);
                }
            }

            function toggleSouth(){
                if($("#southBody").is(":hidden")){
                    $("#southBody").slideDown();
                    $("#centerBody").height(southBodyHigh );
                    $("#tScIcJhstockbillentry1_tablescrolldiv").height(southBodyHigh-50);
                } else {
                    $("#southBody").slideUp();
                    $("#centerBody").height(centerBodyHigh +southBodyHigh  );
                    $("#tScIcJhstockbillentry1_tablescrolldiv").height(centerBodyHigh +southBodyHigh-50);
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
        #centerBody{
            overflow: hidden !important;
        }
        #southBody{
            overflow: hidden !important;
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
<input id="load" type="hidden" value="${load}">
<input id="isCheckNegative" type="hidden" value="${isCheckNegative}">
<input id="clearExt" type="hidden" value="doClearExt">
<t:formvalid formid="formobj" styleClass="fullForm" title="采购换货单" tranType="61" tableName="t_sc_ic_jhstockbill" dialog="true"
             usePlugin="password" layout="table" tiptype="1" saveTemp="true"
             action="tScIcJhstockbillController.do?doUpdate" beforeSubmit="checkPriceIsZero">
<div  class="panel-body layout-body" style="height: 20%">
    <input id="id" name="id" type="hidden"
           value="${tScIcJhstockbillPage.id }">
    <input id="createName" name="createName" type="hidden"
           value="${tScIcJhstockbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden"
           value="${tScIcJhstockbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden"
           value="${tScIcJhstockbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden"
           value="${tScIcJhstockbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden"
           value="${tScIcJhstockbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden"
           value="${tScIcJhstockbillPage.updateDate }">
    <input id="tranType" name="tranType" type="hidden"
           value="${tScIcJhstockbillPage.tranType }">
    <input id="idSrc" name="idSrc" type="hidden"
           value="${tScIcJhstockbillPage.idSrc }">
    <input id="billNoSrc" name="billNoSrc" type="hidden"
           value="${tScIcJhstockbillPage.billNoSrc }">
    <input id="checkState" name="checkState" type="hidden"
           value="${tScIcJhstockbillPage.checkState }">
    <input id="cancellation" name="cancellation" type="hidden"
           value="${tScIcJhstockbillPage.cancellation }">
    <input id="checkAmount" name="checkAmount" type="hidden"
           value="${tScIcJhstockbillPage.checkAmount }">
    <input id="sonId" name="sonId" type="hidden"
           value="${tScIcJhstockbillPage.sonId }">
    <input id="classIDSrc" name="classIDSrc" value="${tScIcJhstockbillPage.classIDSrc }" type="hidden">
    <input id="itemId" name="itemId" value="${tScIcJhstockbillPage.itemId }" type="hidden">
    <input id="empId" name="empId" value="${tScIcJhstockbillPage.empId }" type="hidden">
    <input id="deptId" name="deptId" value="${tScIcJhstockbillPage.deptId }" type="hidden">
    <input id="diffAmount" name="diffAmount" value="${tScIcJhstockbillPage.diffAmount }" type="hidden">
    <input id="accountId" name="accountId" value="${tScIcJhstockbillPage.accountId }" type="hidden">
    <input id="version" name="version" value="${tScIcJhstockbillPage.version }" type="hidden">
    <input id="billerId" name="billerId" value="${tScIcJhstockbillPage.billerId }" type="hidden">
    <input id="billNo" name="billNo" value="${tScIcJhstockbillPage.billNo }" type="hidden">
    <input name="date" value="${tScIcJhstockbillPage.date }" type="hidden">
    <table cellpadding="0" cellspacing="1" class="formtable" style="z-index: 50;position: absolute">
        <tr>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">单据编号:</label>--%>
            <%--</td>--%>
            <%--<td class="value">--%>
                <%--<input id="billNo" name="billNo" type="text" style="width: 150px" class="inputxt" readonly="readonly"--%>
                       <%--value="${tScIcJhstockbillPage.billNo }"--%>
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
                       <%--class="Wdate" onClick="WdatePicker()"--%>
                       <%--value='<fmt:formatDate value='${tScIcJhstockbillPage.date}' type="date" pattern="yyyy-MM-dd"/>'--%>
                       <%--datatype="*"--%>
                        <%-->--%>
      <%--<span class="Validform_checktip">--%>
          <%--*--%>
      <%--</span>--%>
                <%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
            <%--</td>--%>
            <td align="right">
                <label class="Validform_label">供应商:</label>
            </td>
            <td class="value">
                <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select"
                       value="${tScIcJhstockbillPage.itemName }"
                       datatype="*">
      <span class="Validform_checktip">
          *
      </span>
                <label class="Validform_label" style="display: none;">供应商</label>
            </td>
                <td align="right">
                    <label class="Validform_label">经办人:</label>
                </td>
                <td class="value">
                    <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select"
                           value="${tScIcJhstockbillPage.empName }"
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
                           value="${tScIcJhstockbillPage.deptName }"
                           datatype="*">
      <span class="Validform_checktip">
          *
      </span>
                    <label class="Validform_label" style="display: none;">部门</label>
                </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">优惠金额:</label>
            </td>
            <td class="value">
                <input id="rebateAmount" name="rebateAmount" type="text" style="width: 150px" class="inputxt" datatype="float" ignore="ignore"
                       onblur="changeAllAmount()" value="${tScIcJhstockbillPage.rebateAmount }"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">优惠金额</label>
            </td>
            <td align="right">
                <label class="Validform_label">应付金额:</label>
            </td>
            <td class="value">
                <input id="allAmount" name="allAmount" type="text" style="width: 150px" class="inputxt"
                       readonly="readonly"
                       value="${tScIcJhstockbillPage.allAmount }"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">应付金额</label>
            </td>
            <td align="right">
                <label class="Validform_label">本次付款:</label>
            </td>
            <td class="value">
                <input id="curPayAmount" name="curPayAmount" type="text" style="width: 150px" class="inputxt"
                       value="${tScIcJhstockbillPage.curPayAmount }"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">本次付款</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">结算账户:</label>
            </td>
            <td class="value">
                <input id="accountName" type="text" style="width: 150px" class="inputxt popup-select" onblur="checkcurPayAmount()"
                       value="${tScIcJhstockbillPage.accountName }"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">结算账户</label>
            </td>
            <td align="right">
                <label class="Validform_label">分支机构:</label>
            </td>
            <td class="value">
                <input id="sonName" name="sonName" type="text" style="width: 150px" class="inputxt"
                       readonly="readonly"
                       value="${tScIcJhstockbillPage.sonName }"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">分支机构</label>
            </td>
            <td></td>
            <td><span class="spanbtn-expand" id="btnExpand"></span></td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="5">
                <input id="explanation" name="explanation" type="text" style="width: 715px" class="inputxt"
                       value="${tScIcJhstockbillPage.explanation }"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
    </table>
    </div>
    <div class="panel-header" id="custHeader" style="margin-top: 100px;" >
        <div class="panel-title">退货单</div>
        <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up" ></a></div>
    </div>
    <div  class="panel-body layout-body" id="centerBody" style="height: 30%">

    </div>
    <div class="panel-header" id="goodsHeader" >
        <div class="panel-title">换货单</div>
        <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-down" ></a></div>
    </div>
    <div  class="panel-body layout-body" id="southBody" style="height: 30%">

    </div>
    <%--<div style="width: auto;height: 200px;margin-top: 100px">--%>
            <%--&lt;%&ndash; 增加一个div，用于调节页面大小，否则默认太小 &ndash;%&gt;--%>
        <%--<div style="width:800px;height:1px;"></div>--%>
        <%--<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">--%>
            <%--<t:tab href="tScIcJhstockbillController.do?tScIcJhstockbillentry1List&id=${tScIcJhstockbillPage.id}"--%>
                   <%--icon="icon-search" title="退货信息表" id="tScIcJhstockbillentry1"></t:tab>--%>
            <%--<t:tab href="tScIcJhstockbillController.do?tScIcJhstockbillentry2List&id=${tScIcJhstockbillPage.id}"--%>
                   <%--icon="icon-search" title="换货信息表" id="tScIcJhstockbillentry2"></t:tab>--%>
        <%--</t:tabs>--%>
    <%--</div>--%>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScIcJhstockbillentry1_table_template">
    <tr>
        <td align="center" bgcolor="white" >
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white" >
            <div style="width: 80px;"><a name="addTScOrderentryBtn[#index#]"
                                                         id="addTScOrderentryBtn[#index#]" href="#"
                                                         class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                                                         plain="true"
                                                         onclick="clickAddTScIcJhstockbillentry1Btn('#index#','tScIcJhstockbillentry1');"></a><a
                    name="delTScOrderentryBtn[#index#]" id="delTScOrderentryBtn[#index#]" href="#"
                    class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                    onclick="clickDelTScOrderentryBtn('#index#');"></a></div>
        </td>
        <input name="tScIcJhstockbillentry1List[#index#].id" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].createName" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].createBy" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].createDate" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].updateName" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].updateBy" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].updateDate" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].fid" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].findex" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].secUnitId" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].basicUnitId" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].basicQty" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].secCoefficient" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].secQty" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].price" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].amount" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].discountPrice" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].discountAmount" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].costPrice" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].costAmount" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].idSrc" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].entryIdSrc" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].idOrder" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].entryIdOrder" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].itemId" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].stockId" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].basicCoefficient" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].batchManager" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].isKFPeriod" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].xsLimitPrice" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].coefficient" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].classIDSrc" type="hidden"/>
        <input name="tScIcJhstockbillentry1List[#index#].kfDateType" type="hidden"/>
        <td align="left" bgcolor="white" >
            <input name="tScIcJhstockbillentry1List[#index#].itemNo" maxlength="32"
                   type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                   onkeypress="keyDownInfoI('#index#','item',event)"
                    >
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].itemName" maxlength="32"
                   type="text" class="inputxt" style="width:180px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">商品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].model" maxlength="32"
                   type="text" class="inputxt" style="width:85px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].barCode" maxlength="32"
                   type="text" class="inputxt" style="width:100px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].stockName" maxlength="32"
                   type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                   onkeypress="keyDownInfoI('#index#','stock',event)"
                    >
            <label class="Validform_label" style="display: none;">仓库</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].batchNo" maxlength="100"
                   type="text" class="inputxt" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">批号</label>
        </td>
        <td align="left" bgcolor="white">
            <input id="unitId1[#index#]" name="tScIcJhstockbillentry1List[#index#].unitId" maxlength="32"
                   type="text" class="inputxt" style="width:50px;"

                    >
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].qty" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="vInt"

                    >
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <%--<td align="left" bgcolor="white">--%>
            <%--<input name="tScIcJhstockbillentry1List[#index#].coefficient" maxlength="20"--%>
                   <%--type="text" class="inputxt" style="width:70px;" readonly="readonly"--%>

                    <%-->--%>
            <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
        <%--</td>--%>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].taxPriceEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="num"

                    >
            <label class="Validform_label" style="display: none;">单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].taxAmountEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"

                    >
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].discountRate" maxlength="20"
                   type="text" class="inputxt" style="width:80px;" value="100"

                    >
            <label class="Validform_label" style="display: none;">折扣率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].taxPrice" maxlength="20"
                   type="text" class="inputxt" style="width:80px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">折后单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].inTaxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:80px;"
                   onchange="setAllAmount()"
                    >
            <label class="Validform_label" style="display: none;">折后金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].taxRate" maxlength="20"
                   type="text" class="inputxt" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">税率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].taxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">税额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].kfDate" maxlength="20"
                   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                    >
            <label class="Validform_label" style="display: none;">生产日期</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].kfPeriod" maxlength="10"
                   type="text" class="inputxt" style="width:50px;background-color: rgb(226,226,226);" readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">保质期</label>
        </td>
        <td align="left" bgcolor="white">
            <t:dictSelect width="70" field="tScIcJhstockbillentry1List[#index#].kfDateType_"
                          type="list" extendJson="{disabled:disabled}"
                          typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
                          title="保质期类型"></t:dictSelect>
            <label class="Validform_label" style="display: none;">保质期类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].periodDate" maxlength="20"
                   type="text" class="Wdate" readonly="readonly" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">有效期至</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].classIDName" maxlength="10"
                   type="text" class="inputxt" style="width:90px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">源单类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].billNoSrc" maxlength="100"
                   type="text" class="inputxt" style="width:90px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">源单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].billNoOrder" maxlength="100"
                   type="text" class="inputxt" style="width:90px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">订单编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry1List[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:180px;"

                    >
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
    </tbody>
    <tbody id="add_tScIcJhstockbillentry2_table_template">
    <tr>
        <td align="center" bgcolor="white">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;"><a name="addTScOrderentryBtn2[#index#]"
                                                         id="addTScOrderentryBtn2[#index#]" href="#"
                                                         class="easyui-linkbutton" data-options="iconCls:'icon-add'"
                                                         plain="true"
                                                         onclick="clickAddTScIcJhstockbillentry2Btn('#index#','tScIcJhstockbillentry2');"></a><a
                    name="delTScOrderentryBtn2[#index#]" id="delTScOrderentryBtn2[#index#]" href="#"
                    class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                    onclick="clickDelTScOrderentryBtnI('#index#','tScIcJhstockbillentry2');"></a></div>
        </td>
        <input name="tScIcJhstockbillentry2List[#index#].id" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].createName" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].createBy" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].createDate" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].updateName" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].updateBy" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].updateDate" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].basicUnitId" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].basicQty" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].secUnitId" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].secCoefficient" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].secQty" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].price" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].amount" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].discountPrice" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].discountAmount" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].fid" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].findex" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].itemId" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].stockId" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].basicCoefficient" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].batchManager" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].isKFPeriod" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].costPrice" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].costAmount" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].xsLimitPrice" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].coefficient" type="hidden"/>
        <input name="tScIcJhstockbillentry2List[#index#].kfDateType" type="hidden"/>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].itemNo" maxlength="32"
                   type="text" class="inputxt popup-select" style="width:105px;" datatype="*"
                   onkeypress="keyDownInfo('#index#','item',event)"
                    >
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].itemName" maxlength="32"
                   type="text" class="inputxt" style="width:180px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">商品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].model" maxlength="32"
                   type="text" class="inputxt" style="width:85px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].barCode" maxlength="32"
                   type="text" class="inputxt" style="width:100px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].stockName" maxlength="32"
                   type="text" class="inputxt popup-select" style="width:65px;" datatype="*"
                   onkeypress="keyDownInfo('#index#','stock',event)"
                    >
            <label class="Validform_label" style="display: none;">仓库</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].batchNo" maxlength="100"
                   type="text" class="inputxt" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">批号</label>
        </td>
        <td align="left" bgcolor="white">
            <input id="unitId2[#index#]" name="tScIcJhstockbillentry2List[#index#].unitId" maxlength="32"
                   type="text" class="inputxt" style="width:50px;"

                    >
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].qty" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="vInt"

                    >
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <%--<td align="left" bgcolor="white">--%>
            <%--<input name="tScIcJhstockbillentry2List[#index#].coefficient" maxlength="20"--%>
                   <%--type="text" class="inputxt" style="width:70px;" readonly="readonly"--%>

                    <%-->--%>
            <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
        <%--</td>--%>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].taxPriceEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" datatype="num"

                    >
            <label class="Validform_label" style="display: none;">单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].taxAmountEx" maxlength="20"
                   type="text" class="inputxt" style="width:70px;"

                    >
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].discountRate" maxlength="20"
                   type="text" class="inputxt" style="width:80px;" value="100"

                    >
            <label class="Validform_label" style="display: none;">折扣率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].taxPrice" maxlength="20"
                   type="text" class="inputxt" style="width:80px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">折后单价</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].inTaxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">折后金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].taxRate" maxlength="20"
                   type="text" class="inputxt" style="width:80px;"

                    >
            <label class="Validform_label" style="display: none;">税率（%）</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].taxAmount" maxlength="20"
                   type="text" class="inputxt" style="width:70px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">税额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].kfDate" maxlength="20"
                   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
                    >
            <label class="Validform_label" style="display: none;">生产日期</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].kfPeriod" maxlength="10"
                   type="text" class="inputxt" style="width:50px;background-color: rgb(226,226,226)" readonly="readonly"
                    >
            <label class="Validform_label" style="display: none;">保质期</label>
        </td>
        <td align="left" bgcolor="white">
            <t:dictSelect width="70" field="tScIcJhstockbillentry2List[#index#].kfDateType_"
                          type="list" extendJson="{disabled:disabled}"
                          typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
                          title="保质期类型"></t:dictSelect>
            <label class="Validform_label" style="display: none;">保质期类型</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].periodDate" maxlength="20"
                   type="text" class="Wdate" style="width:80px;" readonly="readonly"

                    >
            <label class="Validform_label" style="display: none;">有效期至</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScIcJhstockbillentry2List[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:180px;"

                    >
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
    </tbody>
</table>
<!-- 页脚显示 -->
<div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
    <div style="width: 33%;display:inline;float: left" align="left">
        <label class="Validform_label footlabel">制单人: </label>
        <span class="inputxt footspan"> ${tScIcJhstockbillPage.billerName}</span>
    </div>
    <div style="width: 33%;display: inline;float: left" align="left">
        <label class="Validform_label footlabel">审核人: </label>
        <span class="inputxt footspan">${auditor} </span>
    </div>
    <div style="width: 33%;display: inline;float: right" align="left">
        <label class="Validform_label footlabel">审核日期: </label>
        <span class="inputxt footspan">${auditDate} </span>
    </div>


</div>
</body>
<script src="webpage/com/qihang/buss/sc/sales/tScIcJhstockbill.js"></script>