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
    margin-top:3%;
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
    background: url('plug-in/buss/scm/home/u20.png') no-repeat;
  }
  a.two:hover{
    background: url('plug-in/buss/scm/home/u20_mouseOver.png');
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
<script type="text/javascript">
  $(document).ready(function(){
    $(".one").bind("click", function(){
      var title = "盘点单";
      var url = "tScIcChkstockbillController.do?tScIcChkstockbill";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
    $(".two").bind("click", function(){
      var title = "即时库存";
      var url = "tScIcInventoryController.do?tScIcInventory";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
    $(".three").bind("click", function(){
      var title = "期未结账";
      var url = "tScAccountStageController.do?goAdd";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
  });
</script>

<div class="row" style="margin-top: 8%">
  <input type="hidden" id="beforeAccount" name="beforeAccount" value="true">
  <%--<div class="navigate" ><a class="one"><img src="plug-in/buss/scm/inventory_navi/u76.png"></a><span >其它入库单</span></div>--%>

    <div class="navigate" ><a class="one"><img src="plug-in/buss/scm/inventory_navi/u106.png"></a><span >盘点单</span></div>
    <div class="navigate" ><a class="two"><img src="plug-in/buss/scm/inventory_navi/u107.png" ></a><span >即时库存</span></div>
    <div class="navigate" ><a class="three"><img src="plug-in/buss/scm/inventory_navi/accountstage.png" ></a><span >期未结账</span></div>

  <%--<div class="navigate" ><a class="two"><img src="plug-in/buss/scm/inventory_navi/u78.png"></a><span >其它出库单</span></div>--%>

  <div class="navigate" ></div>

  <div class="navigate" ></div>

</div>

<%--<div class="row">--%>
  <%--<div class="navigate" ><a class="five"><img src="plug-in/buss/scm/inventory_navi/u84.png"></a><span >拆卸单</span></div>--%>

  <%--<div class="navigate" ><a class="six"> <img src="plug-in/buss/scm/inventory_navi/u86.png"></a><span >库存调价单</span></div>--%>

  <%--<div class="navigate" ><a class="seven"><img src="plug-in/buss/scm/inventory_navi/u88.png"></a><span >库存期末调整单</span></div>--%>
  <%--<div class="navigate" ><a class="eight"><img src="plug-in/buss/scm/inventory_navi/u106.png"></a><span >盘点单</span></div>--%>

<%--</div>--%>

  <%--<div class="row">--%>
    <%--<div class="navigate" ><a class="nine"><img src="plug-in/buss/scm/inventory_navi/u107.png"></a><span >即时库存</span></div>--%>
  <%--</div>--%>

