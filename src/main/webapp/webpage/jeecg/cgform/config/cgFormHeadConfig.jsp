<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016-07-30
  Time: 17:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript" src="plug-in/easyui-1.3.6/jquery.min.js"></script>
<script type="text/javascript" src="plug-in/easyui-1.3.6/jquery.easyui.min.js"></script>
<script type="text/javascript" src="plug-in/easyui-1.3.6/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="plug-in/easyui-1.3.6/datagrid-dnd.js"></script>
<script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
<link rel="stylesheet" type="text/css" href="plug-in/easyui-1.3.6/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="plug-in/easyui-1.3.6/themes/icon.css">

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <t:datagrid name="cgFormHeadConfigList" checkbox="false" checkOnSelect="true" fitColumns="false" title="列配置" pagination="false"
                actionUrl="" autoLoadData="false" idField="id" fit="true" queryMode="group" >
      <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="字段" field="field" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="列名" field="title" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="显示名" field="displayTitle" queryMode="single" width="120" editor="text"></t:dgCol>
      <t:dgCol title="隐藏" field="hidden" queryMode="single" width="60" replace="是_true,否_false"
               editor="{type:'checkbox',options:{on:true,off:false}}"></t:dgCol>
      <t:dgCol title="宽度" field="width" queryMode="single"
               editor="{type:'numberbox',options:{min:0,max:999,precision:0}}"></t:dgCol>
    </t:datagrid>
    <input type="hidden" id="headData">
  </div>
</div>
<script>
  $(function () {
//    debugger;
    $('#cgFormHeadConfigList').datagrid({
      data: W.colsConfigData,
      onBeforeDrop: function (targetRow, sourceRow, point) {
        var temp;
        var sourceIndex = $('#cgFormHeadConfigList').datagrid('getRowIndex', sourceRow);
        var targetIndex = $('#cgFormHeadConfigList').datagrid('getRowIndex', targetRow);
        if (point == 'top') {
          temp = targetIndex - 1;
        } else {
          temp = targetIndex;
        }
        W.switchCol(sourceIndex, temp, point);
      },
      onBeforeDrag: function (e) {
        var editCell = $(".datagrid-editable-input");
        if (editCell.val() != undefined) {
          return false;
        }
      }
    });
    $('#cgFormHeadConfigList').datagrid('enableDnd');
  });
</script>
