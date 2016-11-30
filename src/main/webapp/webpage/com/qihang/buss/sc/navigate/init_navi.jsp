<%@ taglib prefix="t" uri="/easyui-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
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
    margin-top:6%;
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
    background: url('plug-in/buss/scm/home/u14.png') no-repeat;
  }
  a.seven:hover{
    background: url('plug-in/buss/scm/home/u14_mouseOver.png');
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
      var title = "存货初始化";
      var url = "tScIcInitialController.do?goAdd";//"vScIcInitialController.do?vScIcInitial";
      //debugger;
      //var tab = $('#maintabs',parent);
      window.parent.addTab(title, url);
    });
    $(".two").bind("click", function(){
      var title = "应收初始化";
      var url = "tScBegdataController.do?goAdd&tranType=1030";
      window.parent.addTab(title, url);
    });
    $(".four").bind("click", function(){
      var title = "应付初始化";
      var url = "tScBegdataController.do?goAdd&tranType=1031";
      window.parent.addTab(title, url);
    });
    $(".six").bind("click", function(){
      var title = "收支初始化";
      var url = "tScBegdataController.do?goAdd&tranType=1032";
      window.parent.addTab(title, url);
    });
  });
//
//  function addTab(subtitle, url, icon) {
//
//    var progress = $("div.messager-progress");
//    if (progress.length) {
//      return;
//    }
//    rowid = "";
//    $.messager.progress({
//      text: loading,
//      interval: 200
//    });
//    if (!$('#maintabs').tabs('exists', subtitle)) {
//      //判断是否进行href方式打开tab，默认为iframe方式
//      if (url.indexOf('isHref') != -1) {
//        $('#maintabs').tabs('add', {
//          title: subtitle,
//          href: url,
//          closable: true,
//          icon: icon
//        });
//      } else {
//
//        $('#maintabs').tabs('add', {
//          title: subtitle,
//          content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:' + ($('#maintabs').height() - 35) + 'px"></iframe>',
//          closable: true,
//          icon: icon
//        });
//
//      }
//
//    } else {
//      $('#maintabs').tabs('select', subtitle);
//      $.messager.progress('close');
//    }
//
//    // $('#maintabs').tabs('select',subtitle);
//    tabClose();
//  }
</script>


<div class="row" style="margin-top: 8%;">
  <input type="hidden" id="beforeAccount" name="beforeAccount" value="init" exception="seven">
  <div class="navigate" ><a class="one"><img src="plug-in/buss/scm/init_navi/u76.png"></a><span >存货初始化</span></div>

  <div class="navigate" ><a class="two"><img src="plug-in/buss/scm/init_navi/u78.png"></a><span >应收初始化</span></div>

  <%--<div class="navigate" ><a class="three"><img src="plug-in/buss/scm/init_navi/u80.png" style="padding-top: 5px"></a><span >预收初始化</span></div>--%>

  <div class="navigate" ><a class="four"><img src="plug-in/buss/scm/init_navi/u82.png"></a><span >应付初始化</span></div>

  <div class="navigate" ><a class="six"> <img src="plug-in/buss/scm/init_navi/u86.png"></a><span >收支初始化</span></div>

</div>

<div class="row">
  <%--<div class="navigate" ><a class="five"><img src="plug-in/buss/scm/init_navi/u84.png"></a><span >预付初始化</span></div>--%>

  <%--<div class="navigate" ><a class="six"> <img src="plug-in/buss/scm/init_navi/u86.png"></a><span >收支初始化</span></div>--%>

  <div class="navigate" ><a class="seven" onclick="openAccount();"><img src="plug-in/buss/scm/init_navi/u88.png"></a><span >结束初始化</span></div>


</div>

<script type="text/javascript">
  /**
   * 结束初始化
   * */
  function openAccount(){
    var url = 'tScAccountConfigController.do?goOpenAccount';
    var width = 400;
    var height = 300;
    var title = "结束初始化";
    createwindow(title, url, width, height);
  }
  function afterOpenAccount(data){
    if(data.success) {
      //parent.tip(data.msg);
      parent.$('#mm-tabcloseall').trigger("click");
    }else{
      parent.tip(data.msg);
    }
  }
</script>
