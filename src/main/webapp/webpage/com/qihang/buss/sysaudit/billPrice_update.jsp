<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>单价设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  </script>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="myform" dialog="true"  usePlugin="password" layout="table" tiptype="1"
             action="tSBillInfoController.do?doUpdatePrice" callback="refreshTable" windowType="dialog">
      <input id="id" name="id" type="hidden"
             value="${id }">
      <input id="billId" name="billId" type="hidden"
               value="${billId}">
  <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">单价类型：</label>
    </td>
    <td class="value">
        <t:dictSelect field="priceField"  type="list" typeGroupCode="SC_BillPriceType" defaultVal="${priceField}"  width="150px"  hasLabel="false" showDefaultOption="true" title="单价类型"></t:dictSelect>
        <label class="Validform_label" style="display: none;">单价类型</label>
      <span class="Validform_checktip">
                </span>
      <label class="Validform_label" style="display: none;">单价类型</label>
    </td>
    </tr>

  </table>
  <%--<div style="width: auto;height: 200px;">--%>
    <%--&lt;%&ndash; 增加一个div，用于调节页面大小，否则默认太小 &ndash;%&gt;--%>
    <%--<div style="width:800px;height:1px;"></div>--%>
    <%--&lt;%&ndash;<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">&ndash;%&gt;--%>
      <%--&lt;%&ndash;<t:tab href="tSAuditController.do?auditInfoList&billId=${billId}&tranType=${tranType}"&ndash;%&gt;--%>
             <%--&lt;%&ndash;icon="icon-search" title="审核内容" id="auditInfo"></t:tab>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</t:tabs>&ndash;%&gt;--%>
  <%--</div>--%>
</t:formvalid>
<div id="auditInfoList" style="width: 500px"></div>
</body>