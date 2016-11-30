<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>结算账户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>

    <style >
        /**20160629添加页脚样式 **/
        body{
            position: absolute;
            width: 100%;
            height: 100%;
        }
        .footlabel{
            float: left;
            margin-left: 15px;
        }

        .footspan{
            float: left;
            margin-left: 5px;
            margin-right: 5px;
            font-weight: bold;
            color: grey;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tScSettleacctController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScSettleacctPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScSettleacctPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScSettleacctPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScSettleacctPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScSettleacctPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScSettleacctPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScSettleacctPage.updateDate }">
      <input id="disabled" name="disabled" type="hidden"
             value="${tScSettleacctPage.disabled }">
      <input id="deleted" name="deleted" type="hidden"
             value="${tScSettleacctPage.deleted }">
      <input id="version" name="version" type="hidden"
             value="${tScSettleacctPage.version }">
      <input id="count" name="count" type="hidden"
             value="0">
  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">
      名称:
      </label>
    </td>
    <td class="value">
          <input id="name" name="name" type="text" style="width: 150px" class="inputxt" value="${tScSettleacctPage.name}"
                 datatype="*" maxlength="25"
              >
      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">名称</label>
    </td>
            <td align="right">
              <label class="Validform_label">
              </label>
            </td>
              <%--<td class="value">
              </td>--%>
          </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScSettleacct.js"></script>