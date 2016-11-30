<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>审核</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    $(document).ready(function () {
      var no = $("#no").val();
      $("#billNo").val(no);
      $('#tt').tabs({
        onSelect: function (title) {
          $('#tt .panel-body').css('width', 'auto');
        }
      });
      $(".tabs-wrap").css('width', '100%');
        $("#auditInfoList").datagrid({
            url:'tSAuditController.do?auditInfoList&billId=${billId}&tranType=${tranType}',
            width:'600px',
            cache:false,
            fit:true,
            fitColumns:true,
            singleSelect:true,
            columns:[[
                {field:'status',title:'级次',width:100,formatter:showStatus},
                {field:'auditorName',title:'审核人',width:120},
                {field:'auditDate',title:'审核日期',width:100,formatter:showDate},
                {field:'auditInfo',title:'审核意见',width:180},
                {field:'deleted',title:'审核类型',width:100,formatter:showType}
            ]]

        })
        var isSub = '${isSub}';
        var isSort = '${isSort}';
        $("#status").combobox({
            textField:'text',
            valueField:'id',
            panelHeight:'auto',
            disabled:isSort == "0" ? false : true,
            url:'tSAuditController.do?loadStatus&billId=${billId}&tranType=${tranType}&isSub='+isSub
        })
    });
    function showDate(v,r,i){
        if(v) {
            var date = new Date(v);
            var year = date.getFullYear();
            var month = (date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
            var date = date.getDate() >= 10 ? date.getDate() : "0" + date.getDate();
            return year + "-" + month + "-" + date;
        }
    }

    function showStatus(v,r,i){
        switch (v){
            case 1 : return "第一级";break;
            case 2 : return "第二级";break;
            case 3 : return "第三级";break;
            case 4 : return "第四级";break;
            case 5 : return "第五级";break;
            case 6 : return "第六级";break;
        }
    }
    function showType(v,r,i){
        if(v==0){
            return "审核通过";
        }else if(v==1){
            return "退回审核";
        }else{
            return "未执行";
        }
    }
    function checkValue(temp){
        var status = $("#status").combobox("getValue");
        if(!status){
            tip("请选择审核级次");
            return false;
        }else{
            return true;
        }
    }

  </script>
</head>
<body style="overflow-x: hidden;">
<input id="no" value="${billNo}" type="hidden">
<t:formvalid formid="myform" dialog="true" beforeSubmit="checkValue" usePlugin="password" layout="table" tiptype="1"
             action="${url}" windowType="dialog">
      <input id="id" name="id" type="hidden"
             value="${auditInfo.id }">
      <input id="billId" name="billId" type="hidden"
               value="${auditInfo.billId}">
        <input id="tranType" name="tranType" type="hidden"
               value="${auditInfo.tranType}">
  <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">单据日期：</label>
    </td>
    <td class="value">
        <input id="date" name="date" type="text" style="width: 150px" readonly="readonly"
               value='<fmt:formatDate value='${billDate}'  type="date" pattern="yyyy-MM-dd"/>'>
      <span class="Validform_checktip">
                </span>
      <label class="Validform_label" style="display: none;">单据日期</label>
    </td>
    </tr>
    <tr>
        <td align="right">
         <label class="Validform_label">单据编号：</label>
        </td>
        <td class="value">
            <input id="billNo" readonly="readonly" type="text" style="width: 150px" class="inputxt">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">单据编号</label>
        </td>
    </tr>
    <tr>
    <td align="right">
      <label class="Validform_label">审核级次:</label>
    </td>
    <td class="value">
      <input id="status" name="status"/>
      <span class="Validform_checktip">*</span>
      <label class="Validform_label" style="display: none;">审核级次</label>
    </td>
    </tr>
     <tr>
    <td align="right">
      <label class="Validform_label">意见:</label>
    </td>
    <td class="value">
          <textarea id="auditInfo" name="auditInfo" rows="5" style="width: 300px"></textarea>
      <span class="Validform_checktip"></span>
      <label class="Validform_label" style="display: none;">意见</label>
    </td>
      </tr>
  </table>
    <div style="width:800px;height:1px;"></div>
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