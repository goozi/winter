<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>账套设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tScAccountConfigController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScAccountConfigPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScAccountConfigPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScAccountConfigPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScAccountConfigPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScAccountConfigPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScAccountConfigPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScAccountConfigPage.updateDate }">
  <table style="width: 43%;margin-left: 25%" cellpadding="0" cellspacing="1" class="formtable">
      <th colspan="4" height="25">企业信息 </th>
      <tr>
    <td align="right"  style="width: 100%">
      <label class="Validform_label">
      公司名称:
      </label>
    </td>
    <td class="value">
          <input id="companyName" name="companyName" type="text" style="width: 400px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">公司名称</label>
    </td>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      税号:
      </label>
    </td>
    <td class="value">
          <input id="taxNumber" name="taxNumber" type="text" style="width: 400px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">税号</label>
    </td>
          </tr>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      银行账号:
      </label>
    </td>
    <td class="value">
          <input id="bankAccount" name="bankAccount" type="text" style="width: 400px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">银行账号</label>
    </td>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      公司地址:
      </label>
    </td>
    <td class="value">
          <input id="companyAddress" name="companyAddress" type="text" style="width: 400px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">公司地址</label>
    </td>
          </tr>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      电话:
      </label>
    </td>
    <td class="value">
          <input id="phone" name="phone" type="text" style="width: 400px" class="inputxt"
                 datatype="*,po"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">电话</label>
    </td>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      传真:
      </label>
    </td>
    <td class="value">
          <input id="fax" name="fax" type="text" style="width: 400px" class="inputxt"
                 datatype="*,f"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">传真</label>
    </td>
          </tr>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      E_Mail:
      </label>
    </td>
    <td class="value">
          <input id="email" name="email" type="text" style="width: 400px" class="inputxt"
                 datatype="*,e"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">E_Mail</label>
    </td>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      公司网址:
      </label>
    </td>
    <td class="value">
          <input id="companyUrl" name="companyUrl" type="text" style="width: 400px" class="inputxt"
                 datatype="*,url"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">公司网址</label>
    </td>
          </tr>
      <tr>
    <td align="right"style="width: 100%">
      <label class="Validform_label">
      注册号:
      </label>
    </td>
    <td class="value">
          <input id="registrationNumber" name="registrationNumber" type="text" style="width: 400px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">注册号</label>
    </td>

          </tr>
  </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScAccountConfig.js"></script>