<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input id="isEdit" type="hidden" value="${isEdit}">
  <t:datagrid name="tPoOrderList" pageSize="${pageNum}" checkbox="true" tableName="t_sc_po_order" beforeAccount="true" scrollview="true"
              tranType="51" fitColumns="false" title="采购订单" actionUrl="tPoOrderController.do?datagrid&isWarm=${isWarm}" isSon="true" entityName="TScPoOrderViewEntity"
              idField="entryId" fit="true" queryMode="group" rowStyler="dgRowStyler"  onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo" query="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" query="true" mergeCells="true" formatter="yyyy-MM-dd"   queryMode="group"  width="75"></t:dgCol>

   <t:dgCol title="供应商id"  field="itemId" hidden="true"  queryMode="single"  width="160"></t:dgCol>
      <t:dgCol title="供应商"  field="itemName" autocomplete="true" query="true" mergeCells="true"  queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="经办人id"  field="empId" hidden="true"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="经办人"  field="empName" autocomplete="true"  query="true" mergeCells="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="部门id"  field="deptId" hidden="true"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="部门"  field="deptName" autocomplete="true" query="true" mergeCells="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库id"  field="stockId" hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="仓库"  field="stockName" hidden="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="优惠金额"  field="rebateAmount" mergeCells="true"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="订单金额"  field="amount" mergeCells="true" hidden="true"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="应付金额"  field="allAmount"  mergeCells="true"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="执行金额"  field="checkAmount"  hidden="true" mergeCells="true" queryMode="single"  width="70"></t:dgCol>

   <t:dgCol title="明细id"  field="entryId" hidden="true"   queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="分录号"  field="index"    queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品编号"  field="entryItemNo"  queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品名称"  field="entryItemName" autocomplete="true"  query="true"  queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条形码"  field="barCode"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="entryStockName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="单位"  field="unitName"    queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="数量"  field="qty"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitName" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="基本数量"  field="basicQty"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="辅助单位" hidden="true"  field="secUnitName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="辅助换算率" hidden="true" field="secCoefficient"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="辅助数量" hidden="true" field="secQty"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="单价"  field="taxPriceEx"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="金额"  field="taxAmountEx"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣率（%）"  field="discountRate"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="折后单价"  field="taxPrice"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折后金额"  field="inTaxAmount"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="税率（%）"  field="taxRate"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税单价"  field="entryPrice"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税金额"  field="entryAmount"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税折扣单价"  field="discountPrice"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="不含税折扣金额"  field="discountAmount" hidden="true"   queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="税额"  field="taxAmount"    queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="收货日期"  field="jhDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="是否赠品"  field="freeGifts" query="true"  dictionary="sf_01"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="收货数量"  field="stockQty"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="备注"  field="note"  queryMode="single"  width="150"></t:dgCol>

   <t:dgCol title="摘要"  field="explanation"    queryMode="single" mergeCells="true"  width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"  queryMode="single" mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"  queryMode="single" mergeCells="true"  width="80"></t:dgCol>
   <t:dgCol title="电话"  field="phone"   queryMode="single" mergeCells="true"  width="80"></t:dgCol>
   <t:dgCol title="传真"  field="fax"   queryMode="single" mergeCells="true"  width="150"></t:dgCol>
   <t:dgCol title="联系地址"  field="address"  queryMode="single" mergeCells="true"  width="70"></t:dgCol>
   <t:dgCol title="源单类型"  field="classIdSrc"  hidden="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单主键"  field="idSrc"  hidden="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  hidden="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否被引用"  field="isStock"  hidden="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billerId" mergeCells="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核人id" field="checkerId" hidden="true" mergeCells="true" queryMode="single" width="65"></t:dgCol>
   <t:dgCol title="审核人" field="checkerName" mergeCells="true" queryMode="single" width="65"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState" mergeCells="true" replace="未审核_0,审核中_1,已审核_2" query="true" queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate" mergeCells="true" formatter="yyyy-MM-dd"  queryMode="single"  width="128"></t:dgCol>
   <t:dgCol title="关闭标记"  field="closed" mergeCells="true"  hidden="true" replace="未关闭_0,已关闭_1" query="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="自动关闭标记"  field="autoFlag" mergeCells="true"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="作废状态"  field="cancellation" mergeCells="true"  query="true" replace="未作废_0,已作废_1"  queryMode="single"  width="65"></t:dgCol>

   <t:dgCol title="分支机构"  field="sonName"  mergeCells="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" mergeCells="true"  width="100"></t:dgCol>
   <t:dgDelOpt title="删除" exp="checkState#eq#0&&stockQty#eq#0.0&&cancellation#eq#0&&closed#eq#0" url="tPoOrderController.do?doDel&id={id}" />
    <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0&&cancellation#eq#0&&isStock#eq#0" funname="goUnAudit(id,billNo,date,cancellation,isStock,closed)"/>--%>
   <t:dgToolBar title="新增" icon="new-icon-add" url="tPoOrderController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tPoOrderController.do?goUpdate&tranType=51" funname="goUpdate"></t:dgToolBar>
   <t:dgToolBar title="复制" icon="new-icon-copy" url="tPoOrderController.do?goUpdate" funname="fcopy"></t:dgToolBar>
   <t:dgToolBar title="变更" icon="new-icon-edit" url="tPoOrderController.do?goUpdate" funname="goChange"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tPoOrderController.do?goUpdate" funname="detail" windowType="tab"></t:dgToolBar>
   <t:dgToolBar title="关闭" icon="new-icon-close"  funname="closeBillInfo"></t:dgToolBar>
   <t:dgToolBar title="反关闭" icon="new-icon-unclose"  funname="unCloseBillInfo"></t:dgToolBar>
   <t:dgToolBar title="作废" icon="new-icon-cancellation"  funname="cancellBillInfo"></t:dgToolBar>
   <t:dgToolBar title="反作废" icon="new-icon-uncancellation"  funname="unCancellBillInfo"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tPoOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('采购订单', '#tPoOrderList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/sales/tScPoOrderList.js"></script>
 <script type="text/javascript">
//     function loadsuccess(data){
//         if(data.rows.length > 0){
//             mergeCellsByField("tPoOrderList", "ck,trantype,date,billNo,checkState,auditorName,auditDate,itemName,contact,mobilePhone,phone,fax,address,empName,deptName,stockName,rebateAmount,allAmount,billerId,cancellation,explanation,sonName,opt","id");
//         }
//     }
     function goDetail(rowIndex,rowData){
         url ='tPoOrderController.do?goUpdate'+ '&load=detail&tranType=51&id=' + rowData.id;
         createdetailwindow('查看', url, null, null,"tab");
     }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
//     $("#tPoOrderList").datagrid({
//         rowStyler: function(index,row){
//             var returnInfo = "";
//             if(row && row.checkState && row.checkState == 2){
//                 returnInfo = 'color:green;'
//             }
//             if(row && row.closed && row.closed == 1){
//                 returnInfo = 'color:red;'
//             }
//             if(row && row.cancellation && row.cancellation == 1){
//                 returnInfo = 'color:rgb(226,226,226);'
//             }
//             return returnInfo;
//         }
//     });
 		//给时间控件加上样式
 			$("#tPoOrderListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPoOrderListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPoOrderListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
            $("#tPoOrderListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPoOrderController.do?upload', "tPoOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPoOrderController.do?exportXls&tranType=51","tPoOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPoOrderController.do?exportXlsByT","tPoOrderList");
}

 //合并行操作
// function mergeCellsByField(tableID, colList,id) {
//     var ColArray = colList.split(",");
//     var tTable = $("#" + tableID);
//     var TableRowCnts = tTable.datagrid("getRows").length;
//     var tmpA;
//     var PerTxt = "";
//     var CurTxt = "";
//     var PerId = "";
//     var CurId = "";
//     for (var j = ColArray.length - 1; j >= 0; j--) {
//         PerTxt = "";
//         PerId = "";
//         tmpA = 1;
//         for (var i = 0; i <= TableRowCnts; i++) {
//             if (i == TableRowCnts) {
//                 CurTxt = "";
//                 CurId = "";
//             }
//             else {
//                 CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
//                 CurId = tTable.datagrid("getRows")[i][id];
//
//             }
//             if ((PerTxt == CurTxt)&&(PerId==CurId)) {
//                 tmpA += 1;
//             }
//             else {
//                 var index =  i - tmpA;
//                 tTable.datagrid("mergeCells", {
//                     index: index,
//                     field: ColArray[j],　　//合并字段
//                     rowspan: tmpA,
//                     colspan: null
//                 });
//                 tmpA = 1;
//             }
//             PerTxt = CurTxt;
//             PerId = CurId;
//         }
//     }
// }
//编辑页面跳转
 function goUpdate(){
     var selected = $("#tPoOrderList").datagrid("getSelected");
     if(null != selected) {
         var isEdit = $("#isEdit").val();
         var checkState = selected.checkState;
         if (checkState == 1 && !isEdit) {
             tip("该单据正在审核中，不可编辑");
             return;
         }else if(checkState == 2 && !isEdit){
             tip("该单据已审核，不可编辑");
             return;
         }

         var closed = selected.closed;
         if(closed == 1){
             tip("该单据已关闭，不可编辑");
             return;
         }
         var cancellation = selected.cancellation;
         if(cancellation == 1){
             tip("该单据已作废，不可编辑");
             return;
         }
     }
     var url = "tPoOrderController.do?goUpdate";
     update("编辑", url, "tPoOrderList", null, null, "tab");
 }
 //变更页面跳转
 function goChange(){
     var selected = $("#tPoOrderList").datagrid("getSelected");
     if(null != selected) {
         var checkState = selected.checkState;
         if (checkState == 1) {
             tip("该单据正在审核中，不可变更");
             return;
         }
         if(checkState == 0){
             tip("该单据未审核，不可变更");
             return;
         }

         var closed = selected.closed;
         if(closed == 1){
             tip("该单据已关闭，不可变更");
             return;
         }
         var cancellation = selected.cancellation;
         if(cancellation == 1){
             tip("该单据已作废，不可变更");
             return;
         }
     }
     var url = "tPoOrderController.do?goUpdate";
     update("变更", url, "tPoOrderList", null, null, "tab");
 }
 </script>