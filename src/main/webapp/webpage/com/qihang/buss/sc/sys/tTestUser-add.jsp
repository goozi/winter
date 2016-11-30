<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>用户表</title>
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
             action="tTestUserController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tTestUserPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tTestUserPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tTestUserPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tTestUserPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tTestUserPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tTestUserPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tTestUserPage.updateDate }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">
      用户名:
      </label>
    </td>
    <td class="value">
          <input id="username" name="username" type="text" style="width: 150px" class="inputxt"
                 datatype="*"
>
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">用户名</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      年龄:
      </label>
    </td>
    <td class="value">
          <input id="age" name="age" type="text" style="width: 150px" class="inputxt"
                 datatype="n"
>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">年龄</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      性别:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="sex" type="list"
                        typeGroupCode="GD_SEX"
                        defaultVal="${tTestUserPage.sex}" hasLabel="false"
                        title="性别"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">性别</label>
    </td>
        </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tTestUser.js"></script>