<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
   <input type="hidden" id="checkDate" value="${checkDate}">
   <input type="hidden" id="flag" value="${flag}">
  <t:datagrid name="tScDrpStockbillList" checkbox="true" beforeAccount="true" fitColumns="false" title="发货管理"  tranType="1506"
              actionUrl="tScDrpStockbillController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" queryMode="group" tableName="t_sc_drp_stockbill">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single" mergeCells="true"  width="95"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd" mergeCells="true" query="true" queryMode="group"  width="75"></t:dgCol>
  <t:dgCol title="下游经销商"  field="dealerId"  hidden="true"  queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="下游经销商"  field="dealerName"  query="true" mergeCells="true" queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="经办人"  field="empId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="经办人"  field="empName" query="true" mergeCells="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="部门"  field="deptId"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="部门"  field="deptName" query="true" mergeCells="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="收款金额"  field="allAmount"  extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);},"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="优惠金额"  field="rebateAmount"  extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="本次收款"  field="curPayAmount"  extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="结算账户"  field="accountId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="结算账户"  field="accountName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="物流费用"  field="freight"  extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);},"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="重量"  field="weight" extendParams="formatter:function(v,r,i){var m = $('#CFG_OTHER').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);},"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="丢失金额"  field="amountLoss"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="确认人"  field="affirmId"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="确认人"  field="affirmName"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="确认时间"  field="affirmDate" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>

   <t:dgCol title="分录号"  field="indexNumber" queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品编号"  field="number" query="true"   queryMode="single"  width="108"></t:dgCol>
   <t:dgCol title="商品名称"  field="name"  query="true"  queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条形码"  field="barCode"    queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="仓库id"  field="stockId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="仓库"  field="stockName" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="批号"  field="batchNo"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="单位id"  field="unitId" hidden="true" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="单位"  field="unitName" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="数量"  field="qty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <%--<t:dgCol title="基本单位"  field="basicUnitId"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitName" hidden="true" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="基本数量"  field="basicQty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助单位"  field="secUnitId" hidden="true" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助单位"  field="secUnitName" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助换算率"  field="secCoefficient" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助数量"  field="secQty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="单价"  field="taxPriceEx" queryMode="single"  width="70"></t:dgCol>--%>
   <t:dgCol title="金额"  field="taxAmountEx" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣率(%)"  field="discountRate" extendParams="formatter:function(v,r,i){var m = $('#CFG_DISCOUNT_RATE').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣单价"  field="taxPrice" extendParams="formatter:function(v,r,i){var m = $('#CFG_UNITP_RICE').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣金额"  field="inTaxAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="重量"  field="itemWeight" extendParams="formatter:function(v,r,i){var m = $('#OTHER').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="税率(%)"  field="taxRate" extendParams="formatter:function(v,r,i){var m = $('#CFG_RATES').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <%--<t:dgCol title="不含税单价"  field="price" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税金额"  field="itemAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税折后单价"  field="discountPrice" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税折后金额"  field="discountAmount" queryMode="single"  width="70"></t:dgCol>--%>
   <t:dgCol title="税额"  field="taxAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="生产日期"  field="kfDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="保质期"  hidden="true" field="kfperiod" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="保质期"  field="kfdateType" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="有效期至"  field="periodDate" queryMode="single" formatter="yyyy-MM-dd"  width="80"></t:dgCol>
   <t:dgCol title="成本单价"  field="costPrice" extendParams="formatter:function(v,r,i){var m = $('#CFG_UNITP_RICE').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="成本金额"  field="costAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="促销类型id"  field="salesId" hidden="true" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="促销类型"  field="salesName" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="赠品标记"  field="freeGifts" dictionary="sf_01" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="退货数量"  field="commitQty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="确认收货数量"  field="stockQty" extendParams="formatter:function(v,r,i){var m = $('#CFG_NUMBER').val(); if(!v){v=0;}; return parseFloat(v).toFixed(m);}," queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="源单类型"  field="entryClassIdSrc" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="订单编号"  field="billNoOrder" query="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="备注"  field="note" queryMode="single"  width="180"></t:dgCol>

   <t:dgCol title="摘要"  field="explanation" mergeCells="true"  queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="联系地址"  field="address"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="制单人id"  field="billerId"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="制单人"  field="billerName" mergeCells="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核人"  field="auditorName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate" formatter="yyyy-MM-dd"   queryMode="group"  width="128"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState" mergeCells="true" query="true"  dictionary="SC_AUDIT_STATUS" queryMode="single"  width="65"></t:dgCol>
   <%--<t:dgCol title="作废标记"  field="cancellation" query="true" mergeCells="true" replace="未作废_0,已作废_1" queryMode="single"  width="65"></t:dgCol>--%>
   <t:dgCol title="分支机构id"  field="sonId"  hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonName"  mergeCells="true"   queryMode="single"  width="80"></t:dgCol>

   <%--<t:dgCol title="传真"  field="fax"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据金额"  field="amount"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收款金额"  field="checkAmount"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="确认状态"  field="affirmStatus"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单主键"  field="interIDSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  hidden="true"   queryMode="single"  width="120"></t:dgCol>--%>

   <t:dgCol title="操作" field="opt" width="100"  mergeCells="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScDrpStockbillController.do?doDel&id={id}" exp="checkState#eq#0" />
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScDrpStockbillController.do?goAdd&tranType=1505" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScDrpStockbillController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScDrpStockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="变更" icon="new-icon-edit" url="tScDrpStockbillController.do?goUpdate" funname="goChange"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="关闭" icon="new-icon-close"  funname="closeBill"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="反关闭" icon="new-icon-unclose"  funname="openBill"></t:dgToolBar>--%>
  <%-- <t:dgToolBar title="作废" icon="new-icon-cancellation" exp="cancellation#eq#0" funname="cancellBillInfo"></t:dgToolBar>
   <t:dgToolBar title="反作废" icon="new-icon-uncancellation" exp="cancellation#ne#0"  funname="unCancellBillInfo"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScDrpStockbillController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('发货管理', '#tScDrpStockbillList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/distribution/tScDrpStockbillList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScDrpStockbillList").datagrid({
         onDblClickRow:function(rowIndex,rowData){
             url ='tScDrpStockbillController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null,"tab");

         }
     });
 		//给时间控件加上样式
       $("#tScDrpStockbillListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpStockbillListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpStockbillListtb").find("input[name='affirmDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpStockbillListtb").find("input[name='affirmDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpStockbillListtb").find("input[name='checkDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpStockbillListtb").find("input[name='checkDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
      //kfDate
       $("#tScDrpStockbillListtb").find("input[name='kfDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpStockbillListtb").find("input[name='kfDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScDrpStockbillController.do?upload', "tScDrpStockbillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScDrpStockbillController.do?exportXls","tScDrpStockbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScDrpStockbillController.do?exportXlsByT","tScDrpStockbillList");
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

 //变更页面跳转
 function goChange(){
  var selected = $("#tScDrpStockbillList").datagrid("getSelected");
  if(null != selected) {
   var checkState = selected.checkState;
   if (checkState == 1) {
    tip("该单据正在审核中，不可变更");
    return;
   }

//   var closed = selected.closed;
//   if(closed == 1){
//    tip("该单据已关闭，不可变更");
//    return;
//   }
   var cancellation = selected.cancellation;
   if(cancellation == 1){
    tip("该单据已作废，不可变更");
    return;
   }
  }
  var url = "tScDrpStockbillController.do?goUpdate&checkState="+checkState;
  update("变更", url, "tScDrpStockbillList", null, null, "tab");
 }
 </script>