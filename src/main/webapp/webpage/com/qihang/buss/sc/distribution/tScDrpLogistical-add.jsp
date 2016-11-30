<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>物流信息表</title>
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
             action="tScDrpLogisticalController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScDrpLogisticalPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScDrpLogisticalPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScDrpLogisticalPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScDrpLogisticalPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScDrpLogisticalPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScDrpLogisticalPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScDrpLogisticalPage.updateDate }">
      <input id="fid" name="fid" type="hidden"
             value="${tScDrpLogisticalPage.fid }">
      <input id="tranType" name="tranType" type="hidden"
             value="${tScDrpLogisticalPage.tranType }">
      <input id="accountID" name="accountID" type="hidden"
             value="${tScDrpLogisticalPage.accountID }">
      <input id="checkAmount" name="checkAmount" type="hidden"
             value="${tScDrpLogisticalPage.checkAmount }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">
      承担方:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="bears" type="list"
                        typeGroupCode="SC_BEAR"
                        defaultVal="${tScDrpLogisticalPage.bears}" hasLabel="false"
                        title="承担方"></t:dictSelect>
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">承担方</label>
    </td>
      <tr>
    <td align="right">
      <label class="Validform_label">
      买家承担方式:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="buyType" type="list"
                        typeGroupCode="SC_BUYER_TYPE"
                        defaultVal="${tScDrpLogisticalPage.buyType}" hasLabel="false"
                        title="买家承担方式"></t:dictSelect>
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">买家承担方式</label>
    </td>
          </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      物流费用:
      </label>
    </td>
    <td class="value">
          <input id="freight" name="freight" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">物流费用</label>
    </td>
      <tr>
    <td align="right">
      <label class="Validform_label">
      本次付款:
      </label>
    </td>
    <td class="value">
          <input id="curPayAmount" name="curPayAmount" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">本次付款</label>
    </td>
          </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      物流公司:
      </label>
    </td>
    <td class="value">
          <input id="logisticalID" name="logisticalID" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">物流公司</label>
    </td>
      <tr>
    <td align="right">
      <label class="Validform_label">
      物流单号:
      </label>
    </td>
    <td class="value">
          <input id="logisticalNo" name="logisticalNo" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">物流单号</label>
    </td>
          </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpLogistical.js"></script>