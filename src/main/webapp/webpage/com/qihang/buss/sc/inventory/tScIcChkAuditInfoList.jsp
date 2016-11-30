<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScIcChkstockbillList" tableName="t_sc_ic_chkstockbill" checkbox="false" checkOnSelect="true" fitColumns="false" title="未审核单据" actionUrl="tScIcChkstockbillController.do?auditInfoDatagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="单据类别"  field="billName"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"   queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="tranType"  field="tranType" hidden="true" queryMode="single"  width="65"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" exp="cancellation#eq#0&&checkState#eq#0" url="tScIcChkstockbillController.do?doDel&id={id}" />--%>
   <%--<t:dgToolBar title="新增" icon="icon-add" url="tScIcChkstockbillController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScIcChkstockbillController.do?goUpdate" funname="goUpdate"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="自动盘点" icon="icon-add" url="tScIcChkstockbillController.do?goAdd" funname="AutoChk"></t:dgToolBar>--%>
   <%--&lt;%&ndash;<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScIcChkstockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>&ndash;%&gt;--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="tScIcChkstockbillController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--&lt;%&ndash;<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>&ndash;%&gt;--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--&lt;%&ndash;<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>&ndash;%&gt;--%>
      <%--<t:dgToolBar title="打印" icon="icon-print"--%>
                   <%--funname="CreateFormPage('盘点单', '#tScIcChkstockbillList')"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <%--<script src = "webpage/com/qihang/buss/sc/inventory/tScIcChkstockbillList.js"></script>		--%>
 <script type="text/javascript">
     Array.prototype.remove = function (dx) {
         if (isNaN(dx) || dx > this.length) {
             return false;
         }
         for (var i = 0, n = 0; i < this.length; i++) {
             if (this[i] != this[dx]) {
                 this[n++] = this[i]
             }
         }
         this.length -= 1
     }
     var popJon = new Array();
     var orgId;
     var isPop = false;
     function getSelectionData() {
         var rows = popJon;
         if (rows.length > 0) {
             isPop = true;
             return rows;

         } else {
             parent.$.messager.show({
                 title: '提示消息',
                 msg: '请选择单据',
                 timeout: 2000,
                 showType: 'slide'
             });
             return '';
         }
     }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScIcChkstockbillList").datagrid({

//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScSlStockbillController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null,"tab");
//
//         },
         onSelect: function (rowIndex, rowData) {
             if (popJon.length) {
                 var isPush = true;
                 for (var i = 0; i < popJon.length; i++) {
                     var val = popJon[i];
                     if (val.id == rowData.id) {
                         isPush = false;
                     }
                 }
                 if (isPush) {
                     popJon.push(rowData);
                 }
             } else {
                 popJon.push(rowData);
             }
         },
         onUnselect: function (rowIndex, rowData) {
             for (var i = 0; i < popJon.length; i++) {
                 var value = popJon[i];
                 if (value.id == rowData.id) {
                     popJon.remove(i);
                     break;
                 }
             }
         },
         onSelectAll: function (rows) {
             for (var i = 0; i < rows.length; i++) {
                 var row = rows[i];
                 var checkNum = 0;
                 for (var j = 0; j < popJon.length; j++) {
                     if (row.id != popJon[j].id) {
                         checkNum++;
                     }
                 }
                 if (checkNum == popJon.length) {
                     popJon.push(row);
                 }
             }
         },
         onUnselectAll: function (rows) {
             for (var i = 0; i < rows.length; i++) {
                 var row = rows[i];
                 for (var j = 0; j < popJon.length; j++) {
                     if (row.id == popJon[j].id) {
                         popJon.remove(j);
                         break;
                     }
                 }
             }
         },
         onLoadSuccess : function(data){
             for (var i = 0; i < data.rows.length; i++) {
                 var row = data.rows[i];
                 var id = row.id;
                 for (var j = 0; j < popJon.length; j++) {
                     var inf = popJon[j];
                     if (id == inf.id) {
                         $("#tScIcChkstockbillList").datagrid("selectRow", i);
                         //popJon.remove(j);
                         break;
                     }
                 }
             }
         }
     });
 });
 </script>