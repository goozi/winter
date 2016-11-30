<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>账套期别</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <%--<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>--%>
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

        // 本期未审核单据信息
        <%--$.ajax({--%>
            <%--type: "get",--%>
            <%--async: false,--%>
            <%--url: "tScAccountStageController.do?vScCheckstageList&date=<fmt:formatDate value='${tScAccountStagePage.date}' type="date" pattern="yyyy-MM-dd"/>",//"tScIcJhstockbillController.do?tScIcJhstockbillentry1List&id=${tScIcJhstockbillPage.id}",--%>
            <%--success:function(data){--%>
                <%--$("#centerBody").html(data);--%>
                <%--centerBodyHigh = $("#centerBody").height();--%>
            <%--}--%>
        <%--});--%>

        <%--// 本期负库存商品信息--%>
        <%--$.ajax({--%>
            <%--type: "get",--%>
            <%--async: false,--%>
            <%--url: "tScAccountStageController.do?vScCheckspeedbalList&date=<fmt:formatDate value='${tScAccountStagePage.date}' type="date" pattern="yyyy-MM-dd"/>",//"tScIcJhstockbillController.do?tScIcJhstockbillentry2List&id=${tScIcJhstockbillPage.id}",--%>
            <%--success:function(data){--%>
                <%--$("#southBody").html(data);--%>
                <%--southBodyHigh= $("#southBody").height();--%>
            <%--}--%>
        <%--});--%>
        <%--//本期未审核单据信息 展开和收缩点击事件--%>
        <%--//本期负库存商品信息 展开和收缩点击事件--%>
        <%--$(".layout-button-up").click(toggleCenter);--%>
        <%--$(".layout-button-down").click(toggleSouth);--%>
        <%--$("#custHeader").dblclick(toggleCenter);--%>
        <%--$("#goodsHeader").dblclick(toggleSouth);--%>
        var isStage = $("#isStage").val();
        if (isStage=="true"){
            $("#openType1").removeAttr("disabled");
        }
        var isUnStage = $("#isUnStage").val();
        if (isUnStage=="true"){
            $("#openType2").removeAttr("disabled");
        }
    });

    //本期未审核单据信息 展开和收缩点击事件
    function toggleCenter(){
        //debugger;
        if($("#centerBody").is(":hidden")){//已经隐藏，即要展开
            $("#centerBody").slideDown();
            //$("#southBody").height(southBodyHigh );
            //$("#tScIcJhstockbillentry2_tablescrolldiv").height(southBodyHigh-25);
            //$("#add_vScCheckspeedbal_table").height(southBodyHigh-25);//vScCheckstage_tablescrolldiv
        } else {//已经展开，即要隐藏
            $("#centerBody").slideUp();
            $("#southBody").height(centerBodyHigh +southBodyHigh );
            //$("#tScIcJhstockbillentry2_tablescrolldiv").height(centerBodyHigh +southBodyHigh-25);
            //$("#add_vScCheckspeedbal_table").height(centerBodyHigh +southBodyHigh-25);
        }
    }

    //本期负库存商品信息 展开和收缩点击事件
    function toggleSouth(){
        //debugger;
        if($("#southBody").is(":hidden")){//已经隐藏，即要展开
            $("#southBody").slideDown();
            //$("#centerBody").height(southBodyHigh );
            $("#centerBody").height(centerBodyHigh );
            //$("#tScIcJhstockbillentry1_tablescrolldiv").height(southBodyHigh-25);
            //$("#add_vScCheckstage_table").height(southBodyHigh-25);//vScCheckspeedbal_tablescrolldiv //add_vScCheckstage_table
        } else {//已经展开，即要隐藏
            $("#southBody").slideUp();
            $("#centerBody").height(centerBodyHigh +southBodyHigh  );
            //$("#tScIcJhstockbillentry1_tablescrolldiv").height(centerBodyHigh +southBodyHigh-25);
            //$("#add_vScCheckstage_table").height(centerBodyHigh +southBodyHigh-25);
        }
    }

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
    </style>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" beforeSubmit="doBeforeSubmit"
             action="tScAccountStageController.do?doAdd">
      <input id="id" name="id" type="hidden"
             value="${tScAccountStagePage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScAccountStagePage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScAccountStagePage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScAccountStagePage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScAccountStagePage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScAccountStagePage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScAccountStagePage.updateDate }">
      <input id="accountId" name="accountId" type="hidden"
             value="${tScAccountStagePage.accountId }">
    <input id="isMinusInventoryAccount" name="isMinusInventoryAccount" type="hidden"
           value="${isMinusInventoryAccount }">
    <input id="isAccountOpen" name="isAccountOpen" type="hidden"
           value="${isAccountOpen }">
    <input id="isStage" name="isStage" type="hidden"
           value="${isStage}">
    <input id="isUnStage" name="isUnStage" type="hidden"
           value="${isUnStage}">
  <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
          <td colspan="2">
              &nbsp;&nbsp;<font color="red">*</font>业务期末结账是对当前期间业务工作的一个总结和确认，业务期末结账后将不能再录入已经结账之前期间的业务单据，也不能对已经结账之前期间的业务单据进行编辑操作。<br>
              &nbsp;&nbsp;业务期末结账后，如果发现以前期间的业务单据有错误，要进行修改或调整则必须反结账该业务单据对应的业务期间方可进行编辑。<br>
              &nbsp;&nbsp;<font color="red">*</font>结账前必须对当前期间业务数据进行一系列的检查：<br>
              <%--0、未开账的账套不能结账--%>
              &nbsp;&nbsp;1、必须检查当前期间所有的业务单据都已经审核成功。<br>
              &nbsp;&nbsp;2、必须检查当前期间是否有未计算存货成本的货品数据。<br>
              &nbsp;&nbsp;3、如果系统设置为不允许负库存结账，则必须得检查当前期间是否有货品的存货期末结存为负数，如果有则不能结账。
          </td>
      </tr>
      <tr>
    <td align="right" width="15%">
      <label class="Validform_label">当前期间:</label>
    </td>
    <td class="value">
          <input id="date" name="date" type="text" style="width: 150px;display: none"
                 class="Wdate" onClick="WdatePicker()" value="<fmt:formatDate value='${tScAccountStagePage.date}' type="date" pattern="yyyy-MM"/>">
          <c:if test="${isAccountOpen==false}">
              <font color="red">未开账</font>
          </c:if>
        <c:if test="${isAccountOpen}">
            <fmt:formatDate value='${tScAccountStagePage.date}' type="date" pattern="yyyy-MM"/>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">期别</label>
        </c:if>
    </td>
            </tr>
      <tr>
          <td align="right" width="15%">
              操作选项：
          </td>
          <td class="value">
              <input name="openType" id="openType1" type="radio" value="${stage}" disabled="disabled"><label class="Validform_label" title="${checkstage}">结账</label>
              <input name="openType" id="openType2" type="radio" value="${unstage}" disabled='disabled' ><label class="Validform_label" title="${checkunstage}">反结账</label>
          </td>
      </tr>
  </table>
    <%--<div class="panel-header" id="custHeader" style="margin-top: 5px;" >--%>
        <%--<div class="panel-title">未审核单据</div>--%>
        <%--<div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up" ></a></div>--%>
    <%--</div>--%>
    <%--<div  class="panel-body layout-body" id="centerBody" style="height: 26%">--%>

    <%--</div>--%>
    <%--<div class="panel-header" id="goodsHeader" >--%>
        <%--<div class="panel-title">负库存情况</div>--%>
        <%--<div class="panel-tool"><a href="javascript:void(0)" class="layout-button-down" ></a></div>--%>
    <%--</div>--%>
    <%--<div  class="panel-body layout-body" id="southBody" style="height: 26%">--%>

    <%--</div>--%>
  <div style="width: auto;height: 200px;">
    <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
    <div style="width:800px;height:1px;"></div>
    <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
      <t:tab href="tScAccountStageController.do?vScCheckstageList&date=${tScAccountStagePage.date}"
             icon="icon-search" title="未审核单据" id="vScCheckstage"></t:tab>
      <t:tab href="tScAccountStageController.do?vScCheckspeedbalList&date=${tScAccountStagePage.date}"
             icon="icon-search" title="负库存情况" id="vScCheckspeedbal"></t:tab>
      <%--<t:tab href="tScAccountStageController.do?tScIcBalList&id=${tScAccountStagePage.id}"--%>
             <%--icon="icon-search" title="存货结账" id="tScIcBal"></t:tab>--%>
      <%--<t:tab href="tScAccountStageController.do?tScRpContactbalList&id=${tScAccountStagePage.id}"--%>
             <%--icon="icon-search" title="应收应付结账" id="tScRpContactbal"></t:tab>--%>
      <%--<t:tab href="tScAccountStageController.do?tScRpExpbalList&id=${tScAccountStagePage.id}"--%>
             <%--icon="icon-search" title="收支结账" id="tScRpExpbal"></t:tab>--%>
    </t:tabs>
  </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
  <tbody id="add_vScCheckstage_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addVScCheckstageBtn[#index#]" id="addVScCheckstageBtn[#index#]" href="#" onclick="clickAddVScCheckstageBtn('#index#');"></a>
        <a name="delVScCheckstageBtn[#index#]" id="delVScCheckstageBtn[#index#]" href="#"  onclick="clickDelVScCheckstageBtn('#index#');"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckstageList[#index#].billName" maxlength="100"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">单据类型</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckstageList[#index#].billno" maxlength="50"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">单据编号</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckstageList[#index#].date" maxlength="32"
                         type="text" class="Wdate" onClick="WdatePicker()" style="width:120px;" />
              <label class="Validform_label" style="display: none;">单据日期</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckstageList[#index#].departmentname" maxlength="80"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">分支机构</label>
            </td>
  </tr>
  </tbody>
  <tbody id="add_vScCheckspeedbal_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addVScCheckspeedbalBtn[#index#]" id="addVScCheckspeedbalBtn[#index#]" href="#" onclick="clickAddVScCheckspeedbalBtn('#index#');"></a>
        <a name="delVScCheckspeedbalBtn[#index#]" id="delVScCheckspeedbalBtn[#index#]" href="#"  onclick="clickDelVScCheckspeedbalBtn('#index#');"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].sonname" maxlength="80"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">仓库</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].itemnumber" maxlength="80"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">商品编号</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].itemname" maxlength="100"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">商品名称</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].batchno" maxlength="100"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].unitname" maxlength="80"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">单位</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].bigqty" maxlength="19"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">箱数</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].smallqty" maxlength="23"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">散数</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].qty" maxlength="23"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">基本数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].secqty" maxlength="23"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">辅助数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="vScCheckspeedbalList[#index#].departmentname" maxlength="100"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">分支机构</label>
            </td>
  </tr>
  </tbody>
  <tbody id="add_tScIcBal_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addTScIcBalBtn[#index#]" id="addTScIcBalBtn[#index#]" href="#" onclick="clickAddTScIcBalBtn('#index#');"></a>
        <a name="delTScIcBalBtn[#index#]" id="delTScIcBalBtn[#index#]" href="#"  onclick="clickDelTScIcBalBtn('#index#');"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].itemID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">商品主键</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].stockID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">仓库主键</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].batchNo" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].begQty" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期初数量(上一期期末)</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].secBegQty" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期初辅助数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].receive" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期收入数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].send" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期发出数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].ytdReceive" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计收入数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].ytdSend" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计发出数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].secYtdReceive" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计收入辅助数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].secYtdSend" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计发出辅助数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].endQty" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期结存数量=期初金额+本期收入-本期发出</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].secEndQty" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期结存辅助数量</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].begBal" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期初金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].debit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期收入金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].credit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期发出金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].ytdDebit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计收入金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].ytdCredit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计发出金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScIcBalList[#index#].endBal" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期末结存金额=期初金额+本期收入-本期发出</label>
            </td>
  </tr>
  </tbody>
  <tbody id="add_tScRpContactbal_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addTScRpContactbalBtn[#index#]" id="addTScRpContactbalBtn[#index#]" href="#" onclick="clickAddTScRpContactbalBtn('#index#');"></a>
        <a name="delTScRpContactbalBtn[#index#]" id="delTScRpContactbalBtn[#index#]" href="#"  onclick="clickDelTScRpContactbalBtn('#index#');"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].rp" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">业务类型(0：应付；1：应收)</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].itemID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">核算项目(客户、供应商主键)</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].deptID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">部门主键</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].empID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">职员主键</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].begBal" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期初金额=上一期期末</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].debit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期收入金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].credit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期发出金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].ytdDebit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计收入金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].ytdCredit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计发出金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpContactbalList[#index#].endBal" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期末结存金额=期初金额+本期收入-本期发出</label>
            </td>
  </tr>
  </tbody>
  <tbody id="add_tScRpExpbal_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addTScRpExpbalBtn[#index#]" id="addTScRpExpbalBtn[#index#]" href="#" onclick="clickAddTScRpExpbalBtn('#index#');"></a>
        <a name="delTScRpExpbalBtn[#index#]" id="delTScRpExpbalBtn[#index#]" href="#"  onclick="clickDelTScRpExpbalBtn('#index#');"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].accountID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">结算账户</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].deptID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">部门主键</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].empID" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">职员主键</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].begBal" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期初金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].debit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期收入金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].credit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本期发出金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].ytdDebit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计收入金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].ytdCredit" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本年累计发出金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpExpbalList[#index#].endBal" maxlength="32"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">期末结存金额</label>
            </td>
  </tr>
  </tbody>
</table>
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 10px;left:10px;" id="footdiv">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScAccountStage.js"></script>