<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>消息管理</title>
    <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#tt').tabs({
                onSelect: function (title) {
                    $('#tt .panel-body').css('width', 'auto');
                }
            });
            $(".tabs-wrap").css('width', '100%');
        });
    </script>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1"
             action="tScMessageController.do?doAdd">
    <input id="id" name="id" type="hidden"
           value="${tScMessagePage.id }">
    <input id="createName" name="createName" type="hidden"
           value="${tScMessagePage.createName }">
    <input id="createBy" name="createBy" type="hidden"
           value="${tScMessagePage.createBy }">
    <input id="createDate" name="createDate" type="hidden"
           value="${tScMessagePage.createDate }">
    <input id="updateName" name="updateName" type="hidden"
           value="${tScMessagePage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden"
           value="${tScMessagePage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden"
           value="${tScMessagePage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">标题:</label>
            </td>
            <td class="value">
                <input id="title" name="title" datatype="*" type="text" style="width: 150px" class="inputxt"
                >
      <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">标题</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">接收人:</label>
            </td>
            <td class="value">
                    <%--<input id="userId" name="userId" type="text" style="width: 150px" class="inputxt">--%>
                <select class="easyui-combotree" id="userId" style="width: 155px;" datatype="select1"
                        data-optons="onlyLeafCheck:true,cascadeCheck:true,multiple:true" name="userId"></select>
      <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">接收人</label>
            </td>
        </tr>
        <tr>
            <td align="right" style="width:60px;">
                <label class="Validform_label">消息内容:</label>
            </td>
            <td class="value">
                    <%--<input id="content" name="content" type="text" style="width: 150px" class="inputxt">--%>
                <t:ckeditor name="content" isfinder="false" type="height:150,toolbar:[
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    ['Link','Unlink','Anchor'],
    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
    ['Styles','Format','Font','FontSize'],
    ['TextColor','BGColor'],
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value=""></t:ckeditor>
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">消息内容</label>
            </td>
        </tr>
    </table>
    <div style="width: auto;height: 200px;">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:800px;height:1px;"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScMessageController.do?tScMessageFileList&id=${tScMessagePage.id}"
                   icon="icon-search" title="消息管理附件表" id="tScMessageFile"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScMessageFile_table_template">
    <tr>
        <td align="center">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
        <td align="left">
            <input type="hidden" id="tScMessageFileList[#index#].messageFile"
                   name="tScMessageFileList[#index#].messageFile"/>
            <a target="_blank" id="tScMessageFileList[#index#].messageFile_href">未上传</a>
            <br>
            <input class="ui-button" type="button" value="上传附件"
                   onclick="commonUpload(commonUploadDefaultCallBack,'tScMessageFileList\\[#index#\\]\\.messageFile')"/>
            <label class="Validform_label" style="display: none;">附件</label>
        </td>
    </tr>
    </tbody>
</table>
</body>
<script src="webpage/com/qihang/buss/sc/oa/tScMessage.js"></script>