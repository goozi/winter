<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>收支结账表</title>
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
             action="tScExpbalController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScExpbalPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScExpbalPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScExpbalPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScExpbalPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScExpbalPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScExpbalPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScExpbalPage.updateDate }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
    <td align="right">
      <label class="Validform_label">
      年份:
      </label>
    </td>
    <td class="value">
          <input id="year" name="year" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">年份</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      月份:
      </label>
    </td>
    <td class="value">
          <input id="period" name="period" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">月份</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      结算账户:
      </label>
    </td>
    <td class="value">
          <input id="accountID" name="accountID" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">结算账户</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      部门主键:
      </label>
    </td>
    <td class="value">
          <input id="deptID" name="deptID" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">部门主键</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      职员主键:
      </label>
    </td>
    <td class="value">
          <input id="empID" name="empID" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">职员主键</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      期初金额:
      </label>
    </td>
    <td class="value">
          <input id="begBal" name="begBal" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">期初金额</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      本期收入金额:
      </label>
    </td>
    <td class="value">
          <input id="debit" name="debit" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">本期收入金额</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      本期发出金额:
      </label>
    </td>
    <td class="value">
          <input id="credit" name="credit" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">本期发出金额</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      本年累计收入金额:
      </label>
    </td>
    <td class="value">
          <input id="ytdDebit" name="ytdDebit" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">本年累计收入金额</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      本年累计发出金额:
      </label>
    </td>
    <td class="value">
          <input id="ytdCredit" name="ytdCredit" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">本年累计发出金额</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      期末结存金额:
      </label>
    </td>
    <td class="value">
          <input id="endBal" name="endBal" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">期末结存金额</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      机构ID:
      </label>
    </td>
    <td class="value">
          <input id="sonID" name="sonID" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">机构ID</label>
    </td>
          </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScExpbal.js"></script>