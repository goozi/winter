<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>收信箱</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tScMessageReceiveController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScMessageReceivePage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScMessageReceivePage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScMessageReceivePage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScMessageReceivePage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScMessageReceivePage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScMessageReceivePage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScMessageReceivePage.updateDate }">
      <input id="readStatus" name="readStatus" type="hidden"
             value="${tScMessageReceivePage.readStatus }">
      <input id="readDate" name="readDate" type="hidden"
             value="${tScMessageReceivePage.readDate }">
      <input id="userId" name="userId" type="hidden"
             value="${tScMessageReceivePage.userId }">
      <input id="messageId" name="messageId" type="hidden"
             value="${tScMessageReceivePage.messageId }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">
      标题:
      </label>
    </td>
    <td class="value">
          <input id="title" name="title" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">标题</label>
    </td>
      <tr>
    <td align="right">
      <label class="Validform_label">
      发送人:
      </label>
    </td>
    <td class="value">
          <input id="sender" name="sender" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">发送人</label>
    </td>
          </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      消息内容:
      </label>
    </td>
    <td class="value">
          <input id="content" name="content" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">消息内容</label>
    </td>
            <td align="right">
              <label class="Validform_label">
              </label>
            </td>
            <td class="value">
            </td>
          </tr>
  </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/oa/tScMessageReceive.js"></script>