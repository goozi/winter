<%--
  Created by IntelliJ IDEA.
  User: LenovoM4550
  Date: 2016-05-19
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<html>
<head>
  <title>新建流程模型</title>
  <t:base type="jquery,easyui,jqueryui-sortable,tools"></t:base>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="modelController.do?create"
             windowType="tab" callback="@Override callback">
<table cellpadding="0" cellspacing="1" class="formtable">
  <tr>
    <td align="right"><label class="Validform_label"> Key: </label></td>
    <td class="value">
      <input class="inputxt" id="key" name="key" value="" style="width: 250px;"
             prefixName="" datatype="*6-80" validType="act_re_model,key_,id_">
      <span class="Validform_checktip">*</span>
    </td>
  </tr>
  <tr>
    <td align="right"><label class="Validform_label"> 名称: </label></td>
    <td class="value">
      <input class="inputxt" id="name" name="name" value="" style="width: 250px;"
             prefixName="" datatype="*6-80">
      <span class="Validform_checktip">*</span>
    </td>
  </tr>
  <tr>
    <td align="right"><label class="Validform_label"> 描述: </label></td>
    <td class="value">
            <textarea class="inputxt" id="description" name="description" style="width: 450px;" rows="6"
                      prefixName="" datatype="*0-1000"></textarea>
      <span class="Validform_checktip"></span>
    </td>
  </tr>
  </t:formvalid>
</body>
<script>
  function callback(data) {
    if (data.success == true) {
      var pathName = window.document.location.pathname;
      var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1) + '/';
      location.href = projectName + 'modeler.html?modelId=' + data.obj;
    } else {
      tip(data.msg);
    }
  }

</script>
</html>
