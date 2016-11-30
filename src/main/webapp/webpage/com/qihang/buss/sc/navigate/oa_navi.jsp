<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    margin-top:4%;
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



<div class="row">

  <%--<div class="navigate" ><a class="one"><img src="plug-in/buss/scm/oa_navi/u76.png" style="padding-top: 5px"></a><span >公告通知</span></div>--%>
  <div class="navigate" ><a class="one" onclick="window.parent.addTab('公告通知','tScNoticeController.do?goAdd')"><img src="plug-in/buss/scm/oa_navi/u76.png" style="padding-top: 5px"></a><span >公告通知</span></div>


  <%--<div class="navigate" ><a class="two"><img src="plug-in/buss/scm/oa_navi/u78.png"></a><span >短信助手</span></div>--%>

  <%--<div class="navigate" ><a class="three"><img src="plug-in/buss/scm/oa_navi/u80.png" style="padding-top: 5px"></a><span >工作计划</span></div>--%>
  <div class="navigate" ><a class="three" onclick="window.parent.addTab('工作计划','tScPlanController.do?goAdd')"><img src="plug-in/buss/scm/oa_navi/u80.png" style="padding-top: 5px"></a><span >工作计划</span></div>

  <%--<div class="navigate" ><a class="four"><img src="plug-in/buss/scm/oa_navi/u82.png"></a><span >工作日志</span></div>--%>
  <div class="navigate" ><a class="four" onclick="window.parent.addTab('工作日志','tScDailyController.do?goAdd')"><img src="plug-in/buss/scm/oa_navi/u82.png"></a><span >工作日志</span></div>

  <div class="navigate" ><a class="five" onclick="window.parent.addTab('日程安排','tScScheduleController.do?tScSchedule')"><img src="plug-in/buss/scm/oa_navi/u84.png"></a><span >日程安排</span></div>

</div>

<div class="row">
  <%--<div class="navigate" ><a class="five"><img src="plug-in/buss/scm/oa_navi/u84.png"></a><span >日程安排</span></div>--%>
    <%--<div class="navigate" ><a class="five" onclick="window.parent.addTab('日程安排','tScScheduleController.do?tScSchedule')"><img src="plug-in/buss/scm/oa_navi/u84.png"></a><span >日程安排</span></div>--%>

  <%--<div class="navigate" ><a class="six"> <img src="plug-in/buss/scm/oa_navi/u86.png"></a><span >备忘录</span></div>--%>

  <%--<div class="navigate" ><a class="seven"><img src="plug-in/buss/scm/oa_navi/u88.png"></a><span >通讯录</span></div>--%>
  <div class="navigate" ><a class="seven" onclick="window.parent.addTab('通讯录','tScContactController.do?tScContactAll')"><img src="plug-in/buss/scm/oa_navi/u88.png"></a><span >通讯录</span></div>

  <%--<div class="navigate" ><a class="eight"><img src="plug-in/buss/scm/oa_navi/u106.png"></a><span >文件柜</span></div>--%>
  <div class="navigate" ><a class="eight" onclick="window.parent.addTab('文件柜','tScFileController.do?tScFile')"><img src="plug-in/buss/scm/oa_navi/u106.png"></a><span >文件柜</span></div>

  <div class="navigate" ><a class="nine" onclick="window.parent.addTab('电子邮件','tScEmailController.do?view')"><img src="plug-in/buss/scm/oa_navi/u112.png" style="padding-top: 10px"></a><span >电子邮件</span></div>

</div>

<div class="row">
  <%--<div class="navigate" ><a class="nine"><img src="plug-in/buss/scm/oa_navi/u112.png" style="padding-top: 10px"></a><span >电子邮件</span></div>--%>
  <%--<div class="navigate" ><a class="nine" onclick="window.parent.addTab('电子邮件','tScEmailController.do?view')"><img src="plug-in/buss/scm/oa_navi/u112.png" style="padding-top: 10px"></a><span >电子邮件</span></div>--%>


</div>
