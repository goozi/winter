<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScDrpRecepitConfimStockbillList" beforeAccount="true" checkbox="true" fitColumns="false" title="发货管理"
              actionUrl="tScDrpReceiptConfirmlController.do?datagrid&affirmStatus=0&checkState=2" idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"    queryMode="single"  width="95"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"   queryMode="group"  width="75"></t:dgCol>
  <t:dgCol title="下游经销商"  field="dealerId"  hidden="true"  queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="下游经销商"  field="dealerName"    queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="经办人"  field="empId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="经办人"  field="empName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="部门"  field="deptId"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="部门"  field="deptName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="收款金额"  field="allAmount"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="优惠金额"  field="rebateAmount"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="本次收款"  field="curPayAmount"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="结算账户"  field="accountId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="结算账户"  field="accountName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="物流费用"  field="freight"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="重量"  field="weight"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="丢失金额"  field="amountLoss"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="确认人"  field="affirmId"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="确认人"  field="affirmName"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="确认时间"  field="affirmDate"  formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>

   <t:dgCol title="分录号"  field="indexNumber" queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品编号"  field="number"    queryMode="single"  width="108"></t:dgCol>
   <t:dgCol title="商品名称"  field="name"    queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条形码"  field="barCode"    queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="仓库"  field="stockId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="仓库"  field="stockName" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="批号"  field="batchNo"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="单位"  field="unitId" hidden="true" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="单位"  field="unitName" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="数量"  field="qty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitId" hidden="true" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitName" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="基本数量"  field="basicQty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助单位"  field="secUnitId" hidden="true" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助单位"  field="secUnitName" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助换算率"  field="secCoefficient" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助数量"  field="secQty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="单价"  field="taxPriceEx" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="金额"  field="taxAmountEx" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣率(%)"  field="discountRate" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣单价"  field="taxPrice" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣金额"  field="inTaxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="重量"  field="weight" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="税率(%)"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税单价"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税金额"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税折后单价"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税折后金额"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="税额"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="生产日期"  field="kfDate" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="保质期"  field="kfperiod" queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="保质期类型"  field="kfdateType" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="有效期至"  field="periodDate" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="成本单价"  field="costPrice" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="成本金额"  field="costAmount" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="促销类型"  field="salesId" hidden="true" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="促销类型"  field="salesName" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="赠品标记"  field="freeGifts" dictionary="sf_01" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="退货数量"  field="commitQty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="确认收货数量"  field="stockQty" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="源单类型"  field="entryClassIdSrc"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="订单编号"  field="billNoOrder"  queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="备注"  field="note" queryMode="single"  width="180"></t:dgCol>

   <t:dgCol title="摘要"  field="explanation"  queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"     queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="联系地址"  field="address"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="制单人"  field="billerId"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="制单人"  field="billerName"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核人"  field="checkerId"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate" formatter="yyyy-MM-dd"    queryMode="group"  width="128"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState"   dictionary="SC_AUDIT_STATUS" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="作废标记"  field="cancellation"   replace="未作废_0,已作废_1" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonId"  hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonName"    queryMode="single"  width="80"></t:dgCol>

   <t:dgCol title="传真"  field="fax"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据金额"  field="amount"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收款金额"  field="checkAmount"   hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="确认状态"  field="affirmStatus"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单主键"  field="interIDSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  hidden="true"   queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgToolBar title="确认" icon="new-icon-uncancellation" url="tScDrpReceiptConfirmlController.do?goConfirm" funname="goConfirm"></t:dgToolBar>
   <t:dgToolBar title="反确认" icon="new-icon-unclose" url="tScDrpReceiptConfirmlController.do?doUnConfirm" funname="doUnConfirm"></t:dgToolBar>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="tScDrpReceiptConfirmlController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="打印" icon="icon-print"
                   funname="CreateFormPage('发货管理', '#tScDrpRecepitConfimStockbillList')"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/distribution/tScDrpReceiptConfirmStockbillList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScDrpRecepitConfimStockbillList").datagrid({
         onDblClickRow:function(rowIndex,rowData){
             url ='tScDrpReceiptConfirmlController.do?goConfirm'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null,"tab");
         }
     });
 		//给时间控件加上样式
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='affirmDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='affirmDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='checkDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='checkDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
      //kfDate
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='kfDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
       $("#tScDrpRecepitConfimStockbillListtb").find("input[name='kfDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScDrpReceiptConfirmlController.do?upload', "tScDrpRecepitConfimStockbillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScDrpStockbillController.do?exportXls","tScDrpRecepitConfimStockbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScDrpStockbillController.do?exportXlsByT","tScDrpRecepitConfimStockbillList");
}

 //变更页面跳转
/* function goChange(){
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
 }*/
 </script>