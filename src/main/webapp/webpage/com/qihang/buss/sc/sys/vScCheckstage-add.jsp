<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>结账前检查当期所有未审核未作废单据列表</title>
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
             action="vScCheckstageController.do?doAdd" tiptype="1">
      <input id="trantype" name="trantype" type="hidden"
             value="${vScCheckstagePage.trantype }">
      <input id="sonid" name="sonid" type="hidden"
             value="${vScCheckstagePage.sonid }">
      <input id="id" name="id" type="hidden"
             value="${vScCheckstagePage.id }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">
      单据日期:
      </label>
    </td>
    <td class="value">
          <input id="date" name="date" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据日期</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      审核状态:
      </label>
    </td>
    <td class="value">
          <input id="checkstate" name="checkstate" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">审核状态</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      单据编号:
      </label>
    </td>
    <td class="value">
          <input id="billno" name="billno" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据编号</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      分支机构:
      </label>
    </td>
    <td class="value">
          <input id="departmentname" name="departmentname" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">分支机构</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      单据类型:
      </label>
    </td>
    <td class="value">
          <input id="billName" name="billName" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据类型</label>
    </td>
        </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/vScCheckstage.js"></script>