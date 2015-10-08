JEECG 微云快速开发平台
===============

当前最新版本： 3.5.2（发布日期：20150607）

前言：
-----------------------------------
随着 WEB UI 框架 ( EasyUI/Jquery UI/Ext/DWZ) 等的逐渐成熟,系统界面逐渐实现统一化，代码生成器也可以生成统一规范的界面！
代码生成+手工MERGE半智能开发将是新的趋势，单表数据模型和一对多数据模型的增删改查功能直接生成使用,可节省60%工作量，快速提高开发效率！！！


简介
-----------------------------------
JEECG（J2EE Code Generation）是一款基于代码生成器的智能开发平台。引领新的开发模式(Online Coding模式->代码生成器模式->手工MERGE智能开发)，
可以帮助解决Java项目60%的重复工作，让开发更多关注业务逻辑。既能快速提高开发效率，帮助公司节省人力成本，同时又不失灵活性。

JEECG宗旨是: 简单功能由代Online Coding配置出功能;复杂功能由代码生成器生成进行手工Merge; 复杂流程业务采用表单自定义，业务流程使用工作流来实现、扩展出任务接口，供开发编写业务逻辑。实现了流程任务节点和任务接口的灵活配置，既保证了公司流程的保密行，又减少了开发人员的工作量。


为什么选择JEECG?
-----------------------------------
* 1.采用主流框架，容易上手; 代码生成器依赖性低,很方便的扩展能力，可完全实现二次开发;
* 2.开发效率很高,采用代码生成器，单表数据模型和一对多(父子表)数据模型，增删改查功能自动生成，菜单配置直接使用；
* 3.页面校验自动生成(必须输入、数字校验、金额校验、时间空间等);
* 4.封装完善的用户基础权限、强大的数据权限、和数据字典等基础功能，直接使用无需修改
* 5.常用共通封装，各种工具类(定时任务,短信接口,邮件发送,Excel导出等),基本满足80%项目需求
* 6.集成简易报表工具，图像报表和数据导出非常方便，可极其方便的生成pdf、excel、word等报表；
* 7.集成工作流activiti，并实现了只需在页面配置流程转向，可极大的简化jbpm工作流的开发；用jbpm的流程设计器画出了流程走向，一个工作流基本就完成了，只需写很少量的java代码；
* 8.UI快速开发库，针对WEB UI进行标准式封装，页面统一采用自定义标签实现功能：列表数据展现、页面校验等,标签使用简单清晰且便于维护
* 9.在线流程设计，采用开源Activiti流程引擎，实现在线画流程,自定义表单,表单挂靠,业务流转
* 10.查询过滤器：查询功能自动生成，后台动态拼SQL追加查询条件；支持多种匹配方式（全匹配/模糊查询/包含查询/不匹配查询）；
* 11.多数据源：及其简易的使用方式，在线配置数据源配置，便捷的从其他数据抓取数据；
* 12.国际化：支持多语言，开发国际化项目非常方便；
* 13.数据权限（精细化数据权限控制，控制到行级，列表级，表单字段级，实现不同人看不同数据，不同人对同一个页面操作不同字段
* 14.多种首页风格切换,支持自定义首页风格。（经典风格、Shortcut风格、ACE bootstrap风格、云桌面风格）
* 15.在线配置报表（无需编码，通过在线配置方式，实现曲线图，柱状图，数据等报表）
* 16.简易Excel导入导出，支持单表导出和一对多表模式导出，生成的代码自带导入导出功能

JEECG 适用范围
-----------------------------------
JEECG智能开发平台，可以应用在任何J2EE项目的开发中，尤其适合企业信息管理系统（MIS）、内部办公系统（OA）、企业资源计划系统（ERP）
、客户关系管理系统（CRM）等，其半智能手工Merge的开发方式，可以显著提高开发效率60%以上，极大降低开发成本。


JEECG 功能特点
-----------------------------------
* 	SpringMvc + Hibernate + UI标签库 + Minidao + Easyui + Easypoi + Highcharts + Ehcache 等基础架构</br>
* 	采用面向声明的开发模式， 基于泛型编写极少代码即可实现复杂的数据展示、数据编辑、
表单处理等功能，再配合Online Coding在线开发与代码生成器的使用,将J2EE的开发效率提高6倍以上，可以将代码减少80%以上。</br>

* 	JEECG V3.5.2 版本技术点总结:

* 	<b>技术点一：</b>Online Coding在线开发(通过在线配置实现一个表模型的增删改查功能，无需一行代码，支持用户自定义表单布局) </br>
* 	<b>技术点二：</b>代码生成器，支持多种数据模型,根据表生成对应的Entity,Service,Dao,Action,JSP等,增删改查功能生成直接使用</br>
* 	<b>技术点三：</b>UI快速开发库，针对WEB UI进行标准封装，页面统一采用UI标签实现功能：数据datagrid,表单校验,Popup,Tab等，实现JSP页面零JS，开发维护非常高效</br>
* 	<b>技术点四：</b>在线流程定义，采用开源Activiti流程引擎，实现在线画流程,自定义表单,表单挂接,业务流转，流程监控，流程跟踪，流程委托等</br>
* 	<b>技术点五：</b>系统日志记录 (详细记录操作日志)</br>
* 	<b>技术点六：</b>查询过滤器：查询功能自动生成，后台动态拼SQL追加查询条件；支持多种匹配方式（全匹配/模糊查询/包含查询/不匹配查询）</br>
* 	<b>技术点七：</b>移动平台支持，对Bootstrap(兼容Html5)进行标准封装 </br>
* 	<b>技术点八：</b>动态报表功能（用户输入一个sql，系统自动解析生成报表）</br>
*   <b>技术点九：</b>数据权限（精细化数据权限控制，控制到行级，列表级，表单字段级，实现不同人看不同数据，不同人对同一个页面操作不同字段）</br>
*   <b>技术点十：</b>国际化（支持多语言，国际化的封装为多语言做了便捷支持）</br>
*   <b>技术点十一：</b>多数据源（在线配置数据源，数据源工作类封装）</br>
*   <b>技术点十二：</b>多种首页风格切换,支持自定义首页风格。（经典风格、Shortcut风格、ACE bootstrap风格、云桌面风格）</br>
*   <b>技术点十三：</b>在线配置报表（无需编码，通过在线配置方式，实现曲线图，柱状图，数据等报表）</br>
*   <b>技术点十四：</b>简易Excel导入导出，支持单表导出和一对多表模式导出，生成的代码自带导入导出功能</br>
	
* 	JEECG V3.5.2, 经过了专业压力测试,性能测试，保证后台数据的准确性和页面访问速度</br>
* 	支持多种浏览器: IE, 火狐, Google 等</br>
* 	支持数据库: Mysql,Oracle10g,Postgre,SqlServer等</br>
* 	基础权限: 用户，角色，菜单权限，按钮权限，数据权限</br>
* 	智能报表集成: 简易的图像报表工具和Excel导入导出</br>
* 	Web容器测试通过的有Jetty和Tomcat6,Weblogic</br>
* 	即将推出功能：分布式部署，云平台，移动平台开发，规则引擎</br>
* 	要求JDK1.6+</br>



系统演示
-----------------------------------
平台支持四种首页风格切换： 经典风格、Shortcut风格、ACE bootstrap风格、云桌面风格
![github](http://img.blog.csdn.net/20150607214324659?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdkYWlzY290dA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center "jeecg")
![github](http://img.blog.csdn.net/20150607214353113?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdkYWlzY290dA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center "jeecg")
![github](http://img.blog.csdn.net/20150607222027195?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdkYWlzY290dA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center "jeecg")
![github](http://img.blog.csdn.net/20150607214724128?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdkYWlzY290dA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center "jeecg")
![github](http://img.blog.csdn.net/20150607221941932?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdkYWlzY290dA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center "jeecg")
![github](http://img.blog.csdn.net/20150607214807402?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvemhhbmdkYWlzY290dA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center "jeecg")

![github](http://www.qihang.com/data/attachment/forum/201303/02/123311mf9fa22tv69b228f.jpg "jeecg")
![github](http://www.qihang.com/data/attachment/forum/201303/02/123412x003euegeg7nb68z.jpg "jeecg")
![github](http://www.qihang.com/data/attachment/forum/201303/02/124748gyhrgvr45vshyc82.jpg "jeecg")
![github](http://www.qihang.com/data/attachment/forum/201303/02/123428ubcjjnuwjbkjrnrw.jpg "jeecg")
![github](http://www.qihang.com/data/attachment/forum/201303/02/124749up2j5id7gj9kppp8.jpg "jeecg")



代码示例
-----------------------------------
    这是一个有多行的文本框  
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@include file="/context/mytags.jsp"%>
    <div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
    <t:dategrid name="jeecgDemoList" title="开发DEMO列表" actionUrl="jeecgDemoController.do?datagrid" idField="id" fit="true">
    <t:dgCol title="编号" field="id" hidden="false"></t:dgCol>
    <t:dgCol title="用户名" field="userName" query="true"></t:dgCol>
    <t:dgCol title="电话号码" sortable="false" field="mobilePhone" width="20" query="true"></t:dgCol>
    <t:dgCol title="办公电话" field="officePhone"></t:dgCol>
    <t:dgCol title="邮箱" field="email"></t:dgCol>
    <t:dgCol title="年龄" sortable="true" field="age"></t:dgCol>
    <t:dgCol title="工资"  field="sex"></t:dgCol>
    <t:dgCol title="性别"  field="salary"></t:dgCol>
    <t:dgCol title="生日" field="birthday" formatter="yyyy/MM/dd"></t:dgCol>
    <t:dgCol title="创建日期" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
    <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
    <t:dgFunOpt funname="szqm(id)" title="审核" />
    <t:dgDelOpt title="删除" url="jeecgDemoController.do?del&id={id}" />
    <t:dgToolBar title="录入" icon="icon-add"></t:dgToolBar>
    <t:dgToolBar title="编辑" icon="icon-edit"></t:dgToolBar>
    </t:dategrid>
    </div>
    </div>
    

技术文档
-----------------------------------
* [JEECG 开发入门搭建文档（Maven版）](http://www.qihang.com/forum.php?mod=viewthread&tid=2537&extra=page%3D1)
* [JEECG 技术论坛](http://www.qihang.com)
* [JEECG 开发入门视频](http://www.qihang.com/forum.php?mod=viewthread&tid=197&extra=page%3D1)
* [JEECG 在线演示](http://demo.jeecg.org)


- 福利贴 -
-----------------------------------
* 资源1： 受Maven折磨的同学来福利了，Eclipse非maven版本下载地址
* JEECG 开发平台(V3系列-NEW)
* JEEWX微信管家系统（V2系列-NEW）
* [下载地址](http://www.qihang.com/forum.php?mod=viewthread&tid=1229&extra=page%3D1)

    
技术交流
-----------------------------------
* 	作者：[张代浩](http://www.qihang.com/forum.php?mod=viewthread&tid=1047)</br>
* 	邮箱：jeecg@sina.com</br>
* 	QQ交流群： 106838471（二群）、106259349（一群）</br>
