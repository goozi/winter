<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>公告通知</title>
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
             action="tScNoticeController.do?doUpdate">
    <input id="id" name="id" type="hidden" value="${tScNoticePage.id }">
    <input id="createName" name="createName" type="hidden" value="${tScNoticePage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tScNoticePage.createBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tScNoticePage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tScNoticePage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tScNoticePage.updateDate }">
    <input id="createDate" name="createDate" type="hidden" value="${tScNoticePage.createDate }">
    <input id="issuance" name="issuance" type="hidden" value="${tScNoticePage.issuance }">
    <input id="issuanceDate" name="issuanceDate" type="hidden" value="${tScNoticePage.issuanceDate }">
    <input id="version" name="version" type="hidden" value="${tScNoticePage.version}">
    <table cellpadding="0" cellspacing="1" class="formtable">
        <div hidden="true" id="userSelect" >${userIdList}</div>
        <tr>
            <td align="right" width="80px">
                <label class="Validform_label">标题:</label>
            </td>
            <td class="value">
                <input id="title" name="title" type="text" style="width: 50%" class="inputxt"
                       value='${tScNoticePage.title}' datatype="*1-200" maxlength="200">
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;">标题</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">公告类型:</label>
            </td>
            <td class="value">
                <t:dictSelect field="type" type="list"
                              typeGroupCode="noteType" extendJson="{'datatype':'*'}"
                              defaultVal="${tScNoticePage.type}" hasLabel="false"
                              title="类型"></t:dictSelect>
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;">公告类型</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">通知人员:</label>
            </td>
            <td class="value">
                    <%--<t:dictSelect field="userId" type="list" typeGroupCode="" defaultVal="${tOaNoticePage.userId}"
                                  hasLabel="false"  title="通知人员"></t:dictSelect>--%>
                        <select class="easyui-combotree" data-options="width:155,url:'userController.do?userSel', multiple:true,onlyLeafCheck:false, cascadeCheck:true"
                                id="userId" name="userId" datatype="select1">
                        </select>
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;">通知人员</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">公告内容:</label>
            </td>
            <td class="value">
                    <%--<input id="content" name="content" type="text" style="width: 150px" class="inputxt" value='${tOaNoticePage.content}'>--%>
                <t:ckeditor name="content" isfinder="false" type="width:document.body.availWidth,height:250,toolbar:[
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    ['Link','Unlink','Anchor'],
    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
    ['Styles','Format','Font','FontSize'],
    ['TextColor','BGColor'],
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]"  value="${tScNoticePage.content}"></t:ckeditor>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">公告内容</label>
            </td>
        </tr>
    </table>
    <div style="width: auto;height: 200px;">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:800px;height:1px;"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScNoticeController.do?tScNoticeFileList&id=${tScNoticePage.id}" icon="icon-search"
                   title="公告通知附件表" id="tScNoticeFile"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScNoticeFile_table_template">
    <tr>
        <td align="center">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center"><div style="width: 80px;margin-top: -5px"><a name="addTScNoticeFileBtn[#index#]" id="addTScNoticeFileBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScNoticeFileBtn('#index#');"></a><a name="delTScNoticeFileBtn[#index#]" id="delTScNoticeFileBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScNoticeFileBtn('#index#');"></a></div></td>

        <%--<td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>--%>
        <td align="left">
            <input type="hidden" id="tScNoticeFileList[#index#].fileName" name="tScNoticeFileList[#index#].fileName"/>
            <a target="_blank" id="tScNoticeFileList[#index#].fileName_href">未上传</a>
            <br>
            <input class="ui-button" type="button" value="上传附件"
                   onclick="commonUpload(commonUploadDefaultCallBack,'tScNoticeFileList\\[#index#\\]\\.fileName')"/>
            <label class="Validform_label" style="display: none;">附件名称</label>
        </td>
    </tr>
    </tbody>
</table>
</body>
<script src="webpage/com/qihang/buss/sc/oa/tScNotice.js"></script>