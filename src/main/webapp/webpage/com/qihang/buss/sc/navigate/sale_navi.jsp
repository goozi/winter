<%@ taglib prefix="t" uri="/easyui-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<t:base type="jquery,easyui,tools"></t:base>
<style>
  * {
    font-size: 12px;
    font-family: Tahoma, Verdana, 新宋体
  }
</style>

<style>
  * {
    font-size: 12px;
    font-family: Tahoma, Verdana, 新宋体
  }

  .navigate{
    float:left;
    margin-left: 40px;
  }

  .row{
    margin-top:5%;
    margin-left:20%;
    text-align: center;
    float: left;
    width: 80%
  }
  .arrow{
    margin-top:6%;
    margin-right: 10px;
  }

  .navigate a{
    vertical-align: middle;
    display: block;
    line-height: 82px;
    padding-right: 10px;
    padding-bottom: 10px;
    width: 82px;
    height: 82px;

  }
  a.one{
    background: url('plug-in/buss/scm/home/u14.png') no-repeat;
  }
  a.one:hover{
    background: url('plug-in/buss/scm/home/u14_mouseOver.png');
  }
  a.two{
    background: url('plug-in/buss/scm/home/u16.png') no-repeat;
  }
  a.two:hover{
    background: url('plug-in/buss/scm/home/u16_mouseOver.png');
  }
  a.three{
    background: url('plug-in/buss/scm/home/u12.png') no-repeat;
  }
  a.three:hover{
    background: url('plug-in/buss/scm/home/u12_mouseOver.png');
  }
  a.four{
    background: url('plug-in/buss/scm/home/u16.png') no-repeat;
  }
  a.four:hover{
    background: url('plug-in/buss/scm/home/u16_mouseOver.png');
  }
  a.five{
    background: url('plug-in/buss/scm/home/u12.png') no-repeat;
  }
  a.five:hover{
    background: url('plug-in/buss/scm/home/u12_mouseOver.png');
  }
  a.six{
    background: url('plug-in/buss/scm/home/u16.png') no-repeat;
  }
  a.six:hover{
    background: url('plug-in/buss/scm/home/u16_mouseOver.png');
  }
  a.seven{
    background: url('plug-in/buss/scm/home/u16.png') no-repeat;
  }
  a.seven:hover{
    background: url('plug-in/buss/scm/home/u16_mouseOver.png');
  }
  a.eight{
    background: url('plug-in/buss/scm/home/u20.png') no-repeat;
  }
  a.eight:hover{
    background: url('plug-in/buss/scm/home/u20_mouseOver.png');
  }

  a.nine{
    background: url('plug-in/buss/scm/home/u20.png') no-repeat;
  }
  a.nine:hover{
    background: url('plug-in/buss/scm/home/u20_mouseOver.png');
  }

  a.ten{
    background: url('plug-in/buss/scm/home/u20.png') no-repeat;
  }
  a.ten:hover{
    background: url('plug-in/buss/scm/home/u20_mouseOver.png');
  }

  a.eleven{
    background: url('plug-in/buss/scm/home/u12.png') no-repeat;
  }
  a.eleven:hover{
    background: url('plug-in/buss/scm/home/u12_mouseOver.png');
  }

  a.twelve{
    background: url('plug-in/buss/scm/home/u20.png') no-repeat;
  }
  a.twelve:hover{
    background: url('plug-in/buss/scm/home/u20_mouseOver.png');
  }

  a.thirteen{
    background: url('plug-in/buss/scm/home/u16.png') no-repeat;
  }
  a.thirteen:hover{
    background: url('plug-in/buss/scm/home/u16_mouseOver.png');
  }

  .navigate img{
    margin-top:19%;



  }
  .navigate span {
    margin-top:8px;
    display: block;
    text-align: center;
    font-family:'Arial Normal', 'Arial';
    font-weight:400;
    font-style:normal;
    font-size:13px;
    color:#333333;
    line-height:normal;
    padding-right: 10px;
  }
</style>


<div style="float: left;overflow: auto;width: 100%" >
  <input type="hidden" id="beforeAccount" name="beforeAccount" value="true">
  <div class="row">
    <div class="navigate" ><a class="one" onclick="window.parent.addTab('资格审查', 'tScAptitudeController.do?goAdd&tranType=77')"><img src="plug-in/buss/scm/sale_navi/u70.png"></a><span >资格审查</span></div>
    <div class="navigate" ><a class="two" onclick="window.parent.addTab('合同管理', 'tScContractController.do?goAdd')"><img src="plug-in/buss/scm/sale_navi/u71.png"></a><span >合同管理</span></div>
    <div class="navigate" ><a class="three" onclick="window.parent.addTab('调价政策单', 'tScPrcplyController.do?goAdd&tranType=353')"><img src="plug-in/buss/scm/sale_navi/u72.png"></a><span >调价政策单</span></div>
    <div class="navigate" ><a class="four" onclick="window.parent.addTab('买一赠一促销', 'tScPromotionController.do?goAdd&tranType=356')"><img src="plug-in/buss/scm/sale_navi/u78.png" style="padding-top: 5px"></a><span >买一赠一促销</span></div>
  </div>
  <div class="row">
    <div class="navigate" ><a class="five" onclick="window.parent.addTab('订货管理', 'tScDrpOrderController.do?goAdd&tranType=1505')"><img src="plug-in/buss/scm/sale_navi/u73.png"></a><span >订货管理</span></div>
    <div class="navigate" ><a class="six" onclick="window.parent.addTab('发货管理', 'tScDrpStockbillController.do?goAdd&tranType=1506')"><img src="plug-in/buss/scm/sale_navi/u75.png"></a><span >发货管理</span></div>
    <div class="navigate" ><a class="seven" onclick="window.parent.addTab('收货确认', 'tScDrpReceiptConfirmlController.do?tScDrpReceiptConfirmStockbill')" href=""><img src="plug-in/buss/scm/sale_navi/u77.png"></a><span >收货确认</span></div>
    <div class="navigate" ><a class="eight" onclick="window.parent.addTab('退货申请', 'tScDrpRstockbillController.do?goAdd&tranType=1507')"><img src="plug-in/buss/scm/sale_navi/u79.png"></a><span >退货申请</span></div>
  </div>
  <div class="row">
    <div class="navigate" ><a class="one" onclick="window.parent.addTab('付款单', 'tScRpPbillController.do?goAdd&tranType=54')"><img src="plug-in/buss/scm/sale_navi/u81.png"></a><span >付款单</span></div>
    <div class="navigate" ><a class="one" onclick="window.parent.addTab('收款单', 'tScRpRbillController.do?goAdd&tranType=105')"><img src="plug-in/buss/scm/sale_navi/u82.png"></a><span >收款单</span></div>
  </div>

  <%--<div class="navigate" ><a class="two" href="tScPrcplyController.do?tScPrcply"><img src="plug-in/buss/scm/sale_navi/u74.png"></a><span >满100加1促销单</span></div>--%>


  <%--<div class="navigate" ><a class="four" href="tScPrcplyController.do?tScPrcply"><img src="plug-in/buss/scm/sale_navi/u78.png"></a><span >限时限量促销单</span></div>--%>

</div>

<div class="row">
 <%--<div class="navigate" ><a class="five"><img src="plug-in/buss/scm/sale_navi/sales.png"></a><span >零售单</span></div>--%>



</div>


</div>