<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link rel="stylesheet" href="plug-in/accordion/css/icons.css"/>
    <style type="text/css">
        html body{
            padding: 15px;
            margin: 0;
        }
    </style>
</head>
<body>
<div>
    <input id="comId" value="${sessionScope.sonId}" type="hidden">
    <input id="sonId" value="${sessionScope.sonIdSup}" type="hidden">
    <input id="userId" value="${sessionScope.user.id}" type="hidden">
    <div style="width: 49%;float: left">
        <div class="easyui-panel" title="最新公告<img style='float:right;cursor:pointer;' src='plug-in/accordion/images/more.png'
	onclick='addOneTabForBiz(&quot;公告通知&quot;,&quot;tScNoticeController.do?tScNotice&quot;)'>
" style="height:220px;"
             data-options="iconCls:'news'">
            <table id="dg"></table>
        </div>
        <br/>
        <div class="easyui-panel" title="预警提醒"
             data-options="iconCls:'warning1'" style="height:300px;">
            <ul id="trees"></ul>
        </div>
    </div>
    <div style="width: 49%;float: right">
        <div class="easyui-panel" title="未读消息<img style='float:right;cursor:pointer;' src='plug-in/accordion/images/more.png'
	onclick='addOneTabForBiz(&quot;消息管理&quot;,&quot;tScMessageController.do?tScMessage&quot;)'>"
             style="height:220px"
             data-options="iconCls:'Laws'">
            <table id="pol"></table>
        </div>
        <br/>
        <div class="easyui-panel" title="日程安排<img style='float:right;cursor:pointer;' src='plug-in/accordion/images/more.png'
	onclick='addOneTabForBiz(&quot;日程安排&quot;,&quot;tScScheduleController.do?tScSchedule&quot;)'>"
             style="height:300px"
             data-options="iconCls:'download'">
            <table id="schedule"></table>
        </div>
    </div>
</div>
<script src="webpage/main/homeNew_scm.js"></script>
</body>
</html>