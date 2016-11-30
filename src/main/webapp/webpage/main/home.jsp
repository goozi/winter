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
    margin-left: 5px;
  }

  .row{
    margin-top:5%;
    margin-left:20%;
    text-align: center;
    float: left;
    width: 80%
  }

  .arrow{
    margin-top:30px;
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


  .navigate img{
    margin-top:19%;



  }
  .navigate span {
    margin-top:18px;
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



<div class="row" style="margin-top:8%;">

  <div class="navigate" ><a class="one"><img src="plug-in/buss/scm/fresh_navi/u137.png"></a><span >期初建账</span></div>
  <div class="navigate arrow" ><img src="plug-in/buss/scm/fresh_navi/u151.png" ></div>
  <div class="navigate" ><a class="two"><img src="plug-in/buss/scm/fresh_navi/u139.png"></a><span >期初数据完成</span></div>
  <div class="navigate arrow"><img src="plug-in/buss/scm/fresh_navi/u151.png"></div>
  <div class="navigate" ><a class="three"><img src="plug-in/buss/scm/fresh_navi/u141.png" style="padding-top: 5px"></a><span >进货业务</span></div>
  <div class="navigate arrow"><img src="plug-in/buss/scm/fresh_navi/u151.png"></div>
  <div class="navigate" ><a class="four"><img src="plug-in/buss/scm/fresh_navi/u143.png"></a><span >销售业务</span></div>

</div>

<div class="row">
  <div class="navigate" ><a class="five"><img src="plug-in/buss/scm/fresh_navi/u149.png"></a><span >月结存</span></div>
  <div class="navigate arrow" ><img src="plug-in/buss/scm/fresh_navi/u161.png"></div>
  <div class="navigate" ><a class="six"> <img src="plug-in/buss/scm/fresh_navi/u147.png"></a><span >财务管理</span></div>
  <div class="navigate arrow" ><img src="plug-in/buss/scm/fresh_navi/u161.png"></div>
  <div class="navigate" ><a class="seven"><img src="plug-in/buss/scm/fresh_navi/u145.png"></a><span >库存管理</span></div>
  <div class="navigate arrow" ><img src="plug-in/buss/scm/fresh_navi/u161.png"></div>
</div>


<!--
<div style="margin-top: 14px;">
  <h3>简介</h3>

  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;WINTER FRAMEWORK 是一款基于代码生成器的快速在线开发平台，引领新开发模式(智能开发\在线开发\插件开发)，
    可以帮助解决Java项目60%的重复工作，让开发更多关注业务逻辑。既能快速提高开发效率，帮助公司节省人力成本，同时又不失灵活性。 <br>
    &nbsp;&nbsp;&nbsp;&nbsp;WINTER快速开发宗旨是：简单功能由代码生成器生成使用; 复杂业务采用表单自定义，业务流程使用工作流来实现、扩展出任务接口，供开发编写业务逻辑。
    实现了流程任务节点和任务接口的灵活配置，既保证了公司流程的保密性，又减少了开发人员的工作量</p>

  <h3>架构说明</h3>
  <ul>
    <li>WINTER 开发平台采用SpringMVC+WinterDao+UI快速开发库+ OnlineCoding开发组件 的基础架构,采用面向声明的开发模式， 基于泛型编写极少代码即可实现复杂的数据展示、数据编辑、<br>
      表单处理等功能，再配合代码生成器的使用,将J2EE的开发效率提高6倍以上，可以将代码减少60%以上。
    </li>
    <li>WINTER FRAMEWORK六大技术点: <b>1.代码生成器</b> <b>2.OnlineCoding在线开发</b> <b>3.在线流程设计</b> <b>4.插件支持</b> <b>5.Web GIS支持</b>
      <b>6.UI快速开发库</b></li>
    <li>技术点一：代码生成器，支持多种数据模型,根据表生成对应的Entity,Service,Dao,Action,JSP等,增删改查功能生成直接使用</li>
    <li>技术点二：OnlineCoding在线开发,对于部分单表及父子表可实现零代码或少量JS代码直接在线开发</li>
    <li>技术点三：在线流程定义，采用开源 Activiti流程引擎，实现在线画流程,自定义表单,表单挂接,业务流转，流程监控，流程跟踪，流程委托等</li>
    <li>技术点四：Web GIS支持，采用采用OpenLayers技术 </li>
    <li>技术点五：UI快速开发库，针对WEB UI进行标准封装，页面统一采用UI标签实现功能：数据datagrid,表单校验,Popup,Tab等，实现JSP页面零JS，开发维护非常高效</li>
    <li>WINTER 开发平台,经过压力测试,性能测试，保证后台数据的准确性和页面访问速度</li>
    <li>支持多种浏览器: IE, 火狐, Google 等浏览器访问速度都很快</li>
    <li>支持数据库: Mysql,Oracle10g等</li>
    <li>基础权限: 用户，角色，菜单权限，按钮权限，数据权限</li>
    <li>智能报表集成: 简易的图像报表工具和Excel导入导出</li>
    <li>Web容器测试通过的有Jetty和Tomcat6</li>
    <li>待推出功能：分布式计算，云计算，规则引擎</li>
    <li>要求JDK1.6+</li>
  </ul>
</div>
<div style="margin-top: 20px;">
  <h3>技术交流</h3>

  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本系统由典通科技提供，WINTER FRAMEWORK 快速开发平台，后期将开源，为大家提供最好的<b>企业二次开发平台</b></p>
  <ul>
    <li>开发 : Zerrion</li>
    <li>邮箱 ：eric.goozi@gmail.com</li>
    <li>网址 ：<a href="http://www.qihang.com">www.qihang.com</a></li>
  </ul>
</div>
-->