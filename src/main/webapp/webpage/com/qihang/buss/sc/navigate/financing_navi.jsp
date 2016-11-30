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
    margin-top:10%;
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
  <div class="navigate" ><a class="one" onclick="window.parent.addTab('银行存取款','tScRpBankbillController.do?goAdd')"><img src="plug-in/buss/scm/financing_navi/u78.png"></a><span >银行存取款</span></div>

  <div class="navigate" ><a class="one" onclick="window.parent.addTab('资金调账','tScRpBankbillFinController.do?goAdd')"><img src="plug-in/buss/scm/financing_navi/u70.png"></a><span >资金调账</span></div>

  <div class="navigate" ><a class="two" onclick="window.parent.addTab('其他收入单','tScRpRotherbillController.do?goAdd')"><img src="plug-in/buss/scm/financing_navi/u72.png"></a><span >其他收入单</span></div>

  <div class="navigate" ><a class="three"  onclick="window.parent.addTab('费用申报单','tScRpExpensesapplyController.do?goAdd')"><img src="plug-in/buss/scm/financing_navi/u74.png" style="padding-top: 5px"></a><span >费用申报单</span></div>

  <div class="navigate" ><a class="four" onclick="window.parent.addTab('费用开支单','tScRpPotherbillController.do?goAdd')"><img src="plug-in/buss/scm/financing_navi/u76.png"></a><span >费用开支单</span></div>

</div>




</div>