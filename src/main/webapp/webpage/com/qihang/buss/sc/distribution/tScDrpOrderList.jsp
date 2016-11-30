<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <input type="hidden" id="checkDate" value="${checkDate}">
      <input type="hidden" id="flag" value="${flag}">
  <t:datagrid name="tScDrpOrderList" checkbox="true" fitColumns="false" title="订货管理" beforeAccount="true"
              actionUrl="tScDrpOrderController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" queryMode="group"
              tranType="1505" isSon="true" tableName="t_sc_drp_order">

   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="上游经销商ID" mergeCells="true" field="itemID" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经办人ID" mergeCells="true" hidden="true" field="empID" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门ID" mergeCells="true" field="deptID" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单位ID" field="unitID" hidden="true" width="80"/>
      <t:dgCol title="isAssembly" field="isAssembly" hidden="true" width="80"/>
      <t:dgCol title="isKfPeriod" field="isKfPeriod" hidden="true" width="80"/>
      <t:dgCol title="batchManager" field="batchManager" hidden="true" width="80"/>
      <t:dgCol title="kfDateType" field="kfDateType" hidden="true" width="80"/>
      <t:dgCol title="KfPeriod" field="KfPeriod" hidden="true" width="80"/>
   <t:dgCol title="单据编号" mergeCells="true" field="billNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据日期" mergeCells="true" field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="上游经销商" mergeCells="true" field="itemName" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经办人" mergeCells="true" field="empName" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门" mergeCells="true" field="deptName" query="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="收款方式"   field="fetchStyle"    queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="应付金额"  field="allAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," mergeCells="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="优惠金额"  field="rebateAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分录号"  field="indexNumber"    queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品编号"  field="goodsItemID" hidden="true"   queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品编号"  field="goodsNo"    queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName" query="true"   queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条形码"  field="barCode"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="stockID" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="stockName"  dictionary="T_SC_STOCK,id,name"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="单位" field="unitName" width="80"/>
   <t:dgCol title="数量"  field="qty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitID" hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient" hidden="true" queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="基本数量"  field="basicQty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," hidden="true"  queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="辅助单位"  field="secUnitID"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="辅助换算率"  field="secCoefficient"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="辅助数量"  field="secQty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val();if(!v){v=0;};return parseFloat(v).toFixed(m);},"  hidden="true" queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="单价"  field="taxPriceEx" extendParams="formatter:function(v,r,i){var m = $('#CFG_UNITP_RICE').val();if(!v){v=0;};return parseFloat(v).toFixed(m);},"   queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="金额"  field="taxAmountEx" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="折扣率(%)"  field="discountRate" extendParams="formatter:function(v,r,i){var m = $('#CFG_DISCOUNT_RATE').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="折后单价"  field="taxPrice" extendParams="formatter:function(v,r,i){var m = $('#CFG_UNITP_RICE').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="折后金额"  field="inTaxAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);},"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="税率(%)"  field="taxRate" extendParams="formatter:function(v,r,i){var m = $('#CFG_RATES').val();if(!v){v=0;};return parseFloat(v).toFixed(m);},"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税单价"  field="price" extendParams="formatter:function(v,r,i){var m = $('#CFG_UNITP_RICE').val();if(!v){v=0;};return parseFloat(v).toFixed(m);},"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税金额"  field="amount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税折后单价"  field="discountPrice" extendParams="formatter:function(v,r,i){var m = $('#CFG_UNITP_RICE').val();if(!v){v=0;};return parseFloat(v).toFixed(m);},"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税折后金额"  field="discountAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="税额"  field="taxAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="收货确认数量"  field="affirmQty" queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="收货日期"  field="jhDate" formatter="yyyy-MM-dd" queryMode="group" query="true"  width="80"></t:dgCol>
  <t:dgCol title="发货数量"  field="outStockQty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val();if(!v){v=0;};return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="发货日期"  field="outDate" query="true" formatter="yyyy-MM-dd" queryMode="group"  width="80"></t:dgCol>
  <t:dgCol title="备注"  field="note" queryMode="single"  width="180"></t:dgCol>

      <t:dgCol title="摘要" mergeCells="true"  field="explanation"    queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"  query="true" mergeCells="true" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"  query="true" mergeCells="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"  query="true"  queryMode="single" mergeCells="true" width="80"></t:dgCol>
      <t:dgCol title="联系地址"  field="address"    queryMode="single" mergeCells="true" width="150"></t:dgCol>
      <t:dgCol title="执行金额"  field="checkAmount"    queryMode="single" mergeCells="true" width="70"></t:dgCol>
      <t:dgCol title="制单人" mergeCells="true" field="billerName"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="审核人" mergeCells="true" field="auditorName"  query="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核日期" mergeCells="true" query="true" field="checkDate"   queryMode="single"  width="128"></t:dgCol>
   <t:dgCol title="审核状态" mergeCells="true" replace="未审核_0,审核中_1,已审核_2" field="checkState"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="关闭标记"  mergeCells="true" field="closed" replace="已关闭_1,未关闭_0" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="自动关闭标记" mergeCells="true"  field="autoFlag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="作废标记"  mergeCells="true" replace="未作废_0,已作废_1" field="cancellation" queryMode="single"  width="65"></t:dgCol>--%>
   <t:dgCol title="分支机构"  field="sonID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="分支机构"  field="sonName" mergeCells="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" mergeCells="true" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScDrpOrderController.do?doDel&id={id}" exp="checkState#eq#0"/>
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScDrpOrderController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑"  icon="new-icon-edit"  url="tScDrpOrderController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
  <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScDrpOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
  <t:dgToolBar title="变更" icon="new-icon-edit" url="tScDrpOrderController.do?goUpdate" funname="goChange"></t:dgToolBar>
  <t:dgToolBar title="关闭" icon="new-icon-close"  funname="closeBill"></t:dgToolBar>
  <t:dgToolBar title="反关闭" icon="new-icon-unclose"  funname="openBill"></t:dgToolBar>
  <%--<t:dgToolBar title="作废" icon="new-icon-cancellation" exp="cancellation#eq#0" funname="cancellBillInfo"></t:dgToolBar>
  <t:dgToolBar title="反作废" icon="new-icon-uncancellation" exp="cancellation#ne#0" funname="unCancellBillInfo"></t:dgToolBar>--%>

   <t:dgToolBar title="查看" icon="new-icon-view" url="tScDrpOrderController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('订货管理', '#tScDrpOrderList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/distribution/tScDrpOrderList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     //$("a[onClick^='goUnAudit']").attr("onClick","checkUnAuit()");
     //添加datagrid 事件 onDblClickRow
     $("#tScDrpOrderList").datagrid({
         onDblClickRow:function(rowIndex,rowData){
             url ='tScDrpOrderController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null,"tab");
         }
     });
 		//给时间控件加上样式
        $("#tScDrpOrderListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScDrpOrderListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

        $("#tScDrpOrderListtb").find("input[name='jhDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScDrpOrderListtb").find("input[name='jhDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

        $("#tScDrpOrderListtb").find("input[name='outDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScDrpOrderListtb").find("input[name='outDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

        $("#tScDrpOrderListtb").find("input[name='checkDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScDrpOrderListtb").find("input[name='checkDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScDrpOrderController.do?upload', "tScDrpOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScDrpOrderController.do?exportXls","tScDrpOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScDrpOrderController.do?exportXlsByT","tScDrpOrderList");
}
 //合并行操作
 function mergeCellsByField(tableID, colList,id) {
     var ColArray = colList.split(",");
     var tTable = $("#" + tableID);
     var TableRowCnts = tTable.datagrid("getRows").length;
     var tmpA;
     var PerTxt = "";
     var CurTxt = "";
     var PerId = "";
     var CurId = "";
     for (var j = ColArray.length - 1; j >= 0; j--) {
         PerTxt = "";
         PerId = "";
         tmpA = 1;
         for (var i = 0; i <= TableRowCnts; i++) {
             if (i == TableRowCnts) {
                 CurTxt = "";
                 CurId = "";
             }
             else {
                 CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                 CurId = tTable.datagrid("getRows")[i][id];

             }
             if ((PerTxt == CurTxt)&&(PerId==CurId)) {
                 tmpA += 1;
             }
             else {
                 var index =  i - tmpA;
                 tTable.datagrid("mergeCells", {
                     index: index,
                     field: ColArray[j],　　//合并字段
                     rowspan: tmpA,
                     colspan: null
                 });
                 tmpA = 1;
             }
             PerTxt = CurTxt;
             PerId = CurId;
         }
     }
 }
 </script>