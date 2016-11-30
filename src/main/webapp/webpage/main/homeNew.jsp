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
    <div style="width: 58%;float: left">
        <div class="easyui-panel" title="最新公告<img style='float:right;cursor:pointer;' src='plug-in/accordion/images/more.png'
	onclick='addTabHome(&quot;公告查看&quot;,&quot;tApBulletinController.do?tApBulletin&show=true&userId=${sessionScope.sonId eq null || sessionScope.sonId eq ""?sessionScope.sonIdSup:sessionScope.sonId}&quot;)'>
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
    <div style="width: 40%;float: right">
        <div class="easyui-panel" title="政策法规<img style='float:right;cursor:pointer;' src='plug-in/accordion/images/more.png'
	onclick='addTabHome(&quot;政策法规查看&quot;,&quot;tApPolicyController.do?tApPolicy&show=true&quot;)'>"
             style="height:220px"
             data-options="iconCls:'Laws'">
            <table id="pol"></table>
        </div>
        <br/>
        <div class="easyui-panel" title="文件下载<img style='float:right;cursor:pointer;' src='plug-in/accordion/images/more.png'
	onclick='addTabHome(&quot;文件查看&quot;,&quot;tApFileDownloadController.do?tApFileDownload&show=true&quot;)'>"
             style="height:300px"
             data-options="iconCls:'download'">
            <table id="file"></table>
        </div>
    </div>
</div>
<script src="/webpage/main/homeNew.js"></script>
</body>
</html>