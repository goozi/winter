<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>买一送一促销单</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        var centerBodyHigh;
        var southBodyHight;
        $(document).ready(function () {
            // 商品信息获取
            $.ajax({
                type: "get",
                url: "tScPromotionController.do?tScPromotiongoodsentryList&id=${tScPromotionPage.id}",
                success: function (data) {
                    $("#centerBody").html(data);
                    centerBodyHigh = $("#centerBody").height();
                }
            });
            //赠品商品信息获取
            $.ajax({
                type: "get",
                url: "tScPromotionController.do?tScPromotiongiftsentryList&id=${tScPromotionPage.id}",
                success: function (data) {
                    $("#southBody").html(data);
                    southBodyHigh = $("#southBody").height();
                }
            });
            $(".layout-button-up").click(toggleCenter);
            $(".layout-button-down").click(toggleSouth);
            $("#goodsHeader").dblclick(toggleCenter);
            $("#giftsGoodsHeader").dblclick(toggleSouth);
            $("input[name='date']").val(setmorendate());
        });
        function toggleCenter() {
            if ($("#centerBody").is(":hidden")) {
                $("#centerBody").slideDown();
                $("#southBody").height(southBodyHigh);
            } else {
                $("#centerBody").slideUp();
                $("#southBody").height(centerBodyHigh + southBodyHigh);
            }
        }
        function toggleSouth() {
            if ($("#southBody").is(":hidden")) {
                $("#southBody").slideDown();
                $("#centerBody").height(southBodyHigh);
            } else {
                $("#southBody").slideUp();
                $("#centerBody").height(centerBodyHigh + southBodyHigh);
            }
        }
    </script>

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
<body style="overflow: hidden;">
<input type="hidden" id="checkDate" value="${checkDate}">
<t:formvalid formid="formobj" dialog="true" title="买一赠一" usePlugin="password" layout="table" tiptype="1" action="tScPromotionController.do?doAdd" beforeSubmit="checkBillDate" saveTemp="true">
    <div class="panel-body layout-body" style="height: 95px">
        <input id="id" name="id" type="hidden" value="${tScPromotionPage.id }"/>
        <input id="createName" name="createName" type="hidden" value="${tScPromotionPage.createName }"/>
        <input id="createBy" name="createBy" type="hidden" value="${tScPromotionPage.createBy }"/>
        <input id="createDate" name="createDate" type="hidden" value="${tScPromotionPage.createDate }"/>
        <input id="updateName" name="updateName" type="hidden" value="${tScPromotionPage.updateName }"/>
        <input id="updateBy" name="updateBy" type="hidden" value="${tScPromotionPage.updateBy }"/>
        <input id="updateDate" name="updateDate" type="hidden" value="${tScPromotionPage.updateDate }"/>
        <input id="tranType" name="tranType" type="hidden" value="356"/>
        <input id="checkDate1" name="checkDate" type="hidden" value="${tScPromotionPage.checkDate }"/>
        <input id="checkState" name="checkState" type="hidden" value="${tScPromotionPage.checkState }"/>
        <input id="cancellation" name="cancellation" type="hidden" value="${tScPromotionPage.cancellation }"/>
        <input id="version" name="version" type="hidden" value="${tScPromotionPage.version }"/>
        <input id="disabled" name="disabled" type="hidden" value="${tScPromotionPage.disabled }"/>
        <input id="deleted" name="deleted" type="hidden" value="${tScPromotionPage.deleted }"/>
        <input id="sonID" name="sonID" type="hidden" value="${tScPromotionPage.sonID}"/>
        <input id="billerID" name="billerID" type="hidden" value="${tScPromotionPage.billerID }"/>
        <input id="checkerID" name="checkerID" type="hidden" value="${tScPromotionPage.checkerID }"/>
        <input id="empID" name="empID" type="hidden"/>
        <input id="deptID" name="deptID" type="hidden"/>
        <input name="billNo" id="billNo" value="${tScPromotionPage.billNo}" type="hidden"/>
        <input name="date" value="${date}" type="hidden"/>
        <table cellpadding="0" cellspacing="1" class="formtable" style="position: absolute;z-index: 50;width: 100% ">
            <tr>
                <td align="right" style="width:6%">
                    <label class="Validform_label">经办人:</label>
                </td>
                <td class="value" style="width:24%">
                    <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"/>
                    <span class="Validform_checktip">*</span>
                    <label class="Validform_label" style="display: none;">经办人</label>
                </td>
                <td align="right">
                    <label class="Validform_label">部门 :</label>
                </td>
                <td class="value">
                    <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"/>
                    <span class="Validform_checktip">*</span>
                    <label class="Validform_label" style="display: none;">部门 </label>
                </td>
                <td align="right">
                    <label class="Validform_label">起始日期 :</label>
                </td>
                <td class="value">
                    <input id="begDate" name="begDate" type="text" style="width: 150px" class="Wdate"
                           onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" datatype="*"/>
                    <span class="Validform_checktip">*</span>
                    <label class="Validform_label" style="display: none;">起始日期 </label>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">结束日期 :</label>
                </td>
                <td class="value">
                    <input id="endDate" name="endDate" type="text" style="width: 150px" class="Wdate"
                           onClick="WdatePicker({minDate:'#F{$dp.$D(\'begDate\')}'})" datatype="*"/>
                    <span class="Validform_checktip">* </span>
                    <label class="Validform_label" style="display: none;">结束日期 </label>
                </td>
                <td align="right">
                    <label class="Validform_label">分支机构:</label>
                </td>
                <td class="value">
                    <input type="text" style="width: 150px" class="inputxt" value="${sessionScope.user.currentDepart.departname}" readonly/>
                    <input id="sonName" name="sonName" type="hidden" value="${sonName}" readonly/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">分支机构</label>
                </td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">摘要 :</label>
                </td>
                <td class="value" colspan="6">
                    <input id="explanation" style="width: 72.5%" class="inputxt" name="explanation" datatype="*1-126"
                           ignore="ignore"/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">摘要 </label>
                </td>
            </tr>
        </table>
    </div>
    <div class="panel-header" id="goodsHeader">
        <div class="panel-title">购买货品</div>
        <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up"></a></div>
    </div>
    <div class="panel-body layout-body" id="centerBody" style="height: 26%"></div>
    <div class="panel-header" id="giftsGoodsHeader">
        <div class="panel-title">赠品</div>
        <div class="panel-tool"><a href="javascript:void(0)" class="layou-button-down"></a></div>
    </div>
    <div class="panel-body layout-body" id="southBody" style="height: 26%"></div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScPromotiongoodsentry_table_template">
        <tr>
        <td align="center" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].id" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].createName" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].createBy" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].createDate" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].updateName" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].updateBy" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].updateDate" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].interID" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].indexNumber" value="1" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].version" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].itemID" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].goodsItemID" type="hidden"/>
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addTScPromotiongoodsentryBtn[#index#]" id="addTScPromotiongoodsentryBtn[#index#]" href="#" onclick="clickAddTScPromotiongoodsentryBtn('#index#');"></a>
                <a name="delTScPromotiongoodsentryBtn[#index#]" id="delTScPromotiongoodsentryBtn[#index#]" href="#" onclick="clickDelTScPromotiongoodsentryBtn('#index#');"></a>
            </div>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongoodsentryList[#index#].goodsNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;"  datatype="*"
                   onkeypress="tScPromotionGoodsEntryListItemIdListKeyPress('#index#',event)"
                   onblur="tScPromotionGoodsEntryListItemIdListBlur('#index#')"/>
            <label class="Validform_label" style="display: none;">商品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongoodsentryList[#index#].goodsName" maxlength="32" datatype="*" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
            <label class="Validform_label" style="display: none;">商品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongoodsentryList[#index#].goodsModel" maxlength="32" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongoodsentryList[#index#].goodsBarCode" maxlength="32" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
            <input id="tScPromotiongoodsentryList[#index#].unitID" name="tScPromotiongoodsentryList[#index#].unitID" style="width:80px;" />
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongoodsentryList[#index#].qty" maxlength="32" type="text" class="inputxt" onchange="checkQty(this)" style="width:120px;"  datatype="d" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongoodsentryList[#index#].note" maxlength="255" type="text" class="inputxt"  style="width:120px;"/>
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
</tbody>
    <tbody id="add_tScPromotiongiftentry_table_template">
    <tr>
        <td align="center" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].id" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].createName" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].createBy" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].createDate" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].updateName" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].updateBy" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].updateDate" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].interID" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].indexNumber" value="1" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].version" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].itemID" type="hidden"/>
            <input name="tScPromotiongiftsentryList[#index#].goodsItemID" type="hidden"/>
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addTScPromotiongiftentryBtn[#index#]" id="addTScPromotiongiftentryBtn[#index#]" href="#" onclick="clickAddTScPromotiongiftentryBtn('#index#');"></a>
                <a name="delTScPromotiongiftentryBtn[#index#]" id="delTScPromotiongiftentryBtn[#index#]" href="#" onclick="clickdelTScPromotiongiftentryBtn('#index#');"></a>
            </div>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].giftNumber" datatype="*" maxlength="36" type="text" class="inputxt popup-select"  style="width:120px;"
                   onkeypress="tScPromotionGiftsEntryListItemIdListKeyPress('#index#',event)"
                   onblur="tScPromotionGiftsEntryListItemIdListBlur('#index#')"
                    />
            <label class="Validform_label" style="display: none;">赠品编号</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].giftName" maxlength="36" datatype="*" type="text" class="inputxt" style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"  />
            <label class="Validform_label" style="display: none;">赠品名称</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].giftModel" maxlength="36" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" />
            <label class="Validform_label" style="display: none;">规格</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].giftBarCode" maxlength="36" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" />
            <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].unitID" id="tScPromotiongiftsentryList[#index#].unitID"  style="width:80px;" />
            <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].qty" maxlength="32" onchange="checkQty(this)" type="text" class="inputxt"  style="width:120px;"  datatype="d" ignore="ignore"/>
            <label class="Validform_label" style="display: none;">数量</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScPromotiongiftsentryList[#index#].note" maxlength="255" type="text" class="inputxt"  style="width:120px;"/>
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
    </tbody>
</table>
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 0px;left:60px;width:100%">
    <div style="width: 33%;float: left">
        <label class="Validform_label footlabel">制单人: </label>
        <span class="inputxt footspan">${sessionScope.user.realName}</span>
    </div>
    <div style="width: 33%;float: left">
        <label class="Validform_label footlabel">审核人 : </label>
        <span class="inputxt footspan"></span>
    </div>
    <div style="width: 34%;float: left">
        <label class="Validform_label footlabel">审核日期: </label>
        <span class="inputxt footspan"></span>
    </div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScPromotion.js"></script>