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

  .arrow{
    margin-top:6%;
    margin-right: 10px;
  }
  .row{
    margin-top:3%;
    margin-left:20%;
    text-align: center;
    float: left;
    width: 80%
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
    background: url('plug-in/buss/scm/home/u121.png') no-repeat;
  }
  a.one:hover{
    background: url('plug-in/buss/scm/home/u121_mouseOver.png');
  }
  a.two{
    background: url('plug-in/buss/scm/home/u131.png') no-repeat;
  }
  a.two:hover{
    background: url('plug-in/buss/scm/home/u131_mouseOver.png');
  }
  a.three{
    background: url('plug-in/buss/scm/home/u119.png') no-repeat;
  }
  a.three:hover{
    background: url('plug-in/buss/scm/home/u119_mouseOver.png');
  }
  a.four{
    background: url('plug-in/buss/scm/home/u123.png') no-repeat;
  }
  a.four:hover{
    background: url('plug-in/buss/scm/home/u123_mouseOver.png');
  }
  a.five{
    background: url('plug-in/buss/scm/home/u119.png') no-repeat;
  }
  a.five:hover{
    background: url('plug-in/buss/scm/home/u119_mouseOver.png');
  }
  a.six{
    background: url('plug-in/buss/scm/home/u123.png') no-repeat;
  }
  a.six:hover{
    background: url('plug-in/buss/scm/home/u123_mouseOver.png');
  }
  a.seven{
    background: url('plug-in/buss/scm/home/u121.png') no-repeat;
  }
  a.seven:hover{
    background: url('plug-in/buss/scm/home/u121_mouseOver.png');
  }
  a.eight{
    background: url('plug-in/buss/scm/home/u123.png') no-repeat;
  }
  a.eight:hover{
    background: url('plug-in/buss/scm/home/u123_mouseOver.png');
  }

  a.nine{
    background: url('plug-in/buss/scm/home/u131.png') no-repeat;
  }
  a.nine:hover{
    background: url('plug-in/buss/scm/home/u131_mouseOver.png');
  }

  .navigate img{
    margin-top:19%;



  }
  .navigate span {
    margin-top:13px;
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
      var title = "采购订单";
      var url = "tPoOrderController.do?goAdd&tranType=51";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
    $(".two").bind("click", function(){
      var title = "采购入库";
      var url = "tScPoStockbillController.do?goAdd&tranType=52";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
    $(".three").bind("click", function(){
      var title = "采购退货";
      var url = "tScPoStockbillController.do?goAdd&tranType=53";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
    $(".four").bind("click", function(){
      var title = "采购换货";
      var url = "tScIcJhstockbillController.do?goAdd&tranType=61";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
  });
</script>

<div class="row"style="margin-top: 5%">
  <input type="hidden" id="beforeAccount" name="beforeAccount" value="true">
  <div class="navigate" ><a class="one" ><img src="plug-in/buss/scm/sale_navi/u90.png"></a><span >采购订单</span></div>

  <div class="navigate" ><a class="two"><img src="plug-in/buss/scm/sale_navi/u91.png"></a><span >采购入库</span></div>

  <div class="navigate" ><a class="three"><img src="plug-in/buss/scm/sale_navi/u83.png"></a><span >采购退货</span></div>

  <div class="navigate" ><a class="four"><img src="plug-in/buss/scm/sale_navi/u84.png"></a><span >采购换货</span></div>

</div>

<%--<div class="row">--%>
  <%--<div class="navigate" ><a class="five"><img src="plug-in/buss/scm/shop_navi/u149.png"></a><span >订单物流跟踪</span></div>--%>

  <%--<div class="navigate" ><a class="six"> <img src="plug-in/buss/scm/shop_navi/u147.png"></a><span >结算查看</span></div>--%>

  <%--<div class="navigate" ><a class="seven"><img src="plug-in/buss/scm/shop_navi/u145.png"></a><span >新品推介</span></div>--%>
  <%--<div class="navigate" ><a class="eight"><img src="plug-in/buss/scm/shop_navi/u202.png"></a><span >宣传推介</span></div>--%>

<%--</div>--%>

<%--<div class="row">--%>
  <%--<div class="navigate" ><a class="nine"><img src="plug-in/buss/scm/shop_navi/u200.png"></a><span >其它</span></div>--%>


<%--</div>--%>
