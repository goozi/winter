<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <input id="isCheckNegative" type="hidden" value="${isCheckNegative}">
      <input id="method" type="hidden"  value="${method}">
      <input id="timePoint" type="hidden"  value="${timePoint}">
  <t:datagrid name="tScSlStockbillList" checkbox="true" tranType="104" tableName="t_sc_sl_stockbill" beforeAccount="true" isSon="true" rowStyler="dgRowStyler"
              fitColumns="false" title="销售退货单" actionUrl="tScSlStockbillController.do?datagrid&isWarm=${isWarm}" scrollview="true"
              idField="id" fit="true" queryMode="group" onDblClick="goDetail">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single" mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  mergeCells="true" width="75"></t:dgCol>
      <t:dgCol title="客户id"  field="itemId" hidden="true" queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="客户"  field="itemName"  query="true" queryMode="single"  mergeCells="true" width="160"></t:dgCol>
      <t:dgCol title="经办人id"  field="empId" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="经办人"  field="empName" query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="部门id"  field="deptId" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="部门"  field="deptName" query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="仓库id"  field="stockId" hidden="true"   queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="仓库"  field="stockName" hidden="true"   queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="应付金额"  field="allAmount"    queryMode="single"  mergeCells="true" width="70"></t:dgCol>
      <t:dgCol title="优惠金额"  field="rebateAmount"    queryMode="single"  mergeCells="true" width="70"></t:dgCol>
      <t:dgCol title="结算账户"  field="accountId"   queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="本次付款"  field="curPayAmount"    queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="重量"  field="weight"   queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="物流费用"  field="freight"   queryMode="single"  mergeCells="true" width="65"></t:dgCol>

      <t:dgCol title="明细id"  field="entryId" hidden="true"   queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="分录号"  field="findex"    queryMode="single"  width="45"></t:dgCol>
      <t:dgCol title="商品编号"  field="entryItemNo"  queryMode="single"  width="105"></t:dgCol>
      <t:dgCol title="商品名称"  field="entryItemName"  query="true"   queryMode="single"  width="180"></t:dgCol>
      <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
      <t:dgCol title="条码"  field="barCode"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="仓库"  field="entryStockName"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="批号"  field="batchNo"    queryMode="single"  width="95"></t:dgCol>
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
      <t:dgCol title="重量"  field="entryWeight"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="税率（%）"  field="taxRate"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="不含税单价"  field="entryPrice"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="不含税金额"  field="entryAmount"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="不含税折扣单价"  field="discountPrice"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="不含税折扣金额"  field="discountAmount" hidden="true"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="税额"  field="taxAmount"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="生产日期"  field="kfDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
      <%--<t:dgCol title="保质期"  field="kfPeriod"  queryMode="single"  width="80"></t:dgCol>--%>
      <t:dgCol title="保质期"  field="kfDateType"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="有效期至"  field="periodDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
      <%--<t:dgCol title="促销类型"  field="salesID"  queryMode="single"  width="65"></t:dgCol>--%>
      <t:dgCol title="是否赠品"  field="freegifts" query="true"  dictionary="sf_01"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="退货数量"  field="commitQty"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="源单类型"  field="entryClassIdSrc"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="源单编号"  field="entryBillNoSrc"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="订单编号"  field="billNoOrder"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="备注"  field="note"  queryMode="single"  width="180"></t:dgCol>

      <t:dgCol title="摘要"  field="explanation"    queryMode="single"  mergeCells="true" width="180"></t:dgCol>
      <t:dgCol title="联系人"  field="contact"    queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="手机"  field="mobilePhone"    queryMode="single"  mergeCells="true" width="80"></t:dgCol>
      <t:dgCol title="电话"  field="phone"    queryMode="single"  mergeCells="true" width="80"></t:dgCol>
      <t:dgCol title="传真"  field="fax"    queryMode="single"  mergeCells="true" width="80"></t:dgCol>
      <t:dgCol title="联系地址"  field="address"    queryMode="single"  mergeCells="true" width="150"></t:dgCol>
      <t:dgCol title="单据金额"  field="amount"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="付款金额"  field="checkAmount"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
      <%--<t:dgCol title="源单类型"  field="classIDSrc"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>--%>
      <%--<t:dgCol title="源单主键"  field="idSrc"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>--%>
      <%--<t:dgCol title="源单编号"  field="billNoSrc"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>--%>
      <t:dgCol title="制单人"  field="billerID"    queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="审核人"  field="checkerName"    queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="审核日期"  field="checkDate"  formatter="yyyy-MM-dd"  queryMode="single"  mergeCells="true" width="128"></t:dgCol>
      <t:dgCol title="审核状态"  field="checkState" query="true" replace="未审核_0,审核中_1,已审核_2"   queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="作废标记"  field="cancellation" query="true" replace="未作废_0,已作废_1"   queryMode="single"  mergeCells="true" width="65"></t:dgCol>
      <t:dgCol title="同步更新状态"  field="sysState" mergeCells="true" replace="未同步_0,已同步_1" queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="分支机构"  field="sonName"    queryMode="single"  mergeCells="true" width="80"></t:dgCol>
      <t:dgCol title="操作" field="opt" mergeCells="true" width="100"></t:dgCol>
      <t:dgFunOpt title="删除" exp="checkState#eq#0&&cancellation#eq#0" funname="doDel(id,billNo,date)" />
      <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0&&cancellation#eq#0" funname="goUnAudit(id,billNo,date,cancellation,{0},{0})"/>--%>
      <t:dgToolBar title="新增" icon="new-icon-add" url="tScSlStockbillController.do?goAdd&tranType=104" funname="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScSlStockbillController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
      <t:dgToolBar title="复制" icon="new-icon-copy" url="tScSlStockbillController.do?goUpdate" funname="fcopy"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="new-icon-view" url="tScSlStockbillController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="物流信息" icon="new-icon-add"  funname="goLogistical"></t:dgToolBar>
      <t:dgToolBar title="作废" icon="new-icon-cancellation"  funname="cancellBillInfo"></t:dgToolBar>
      <t:dgToolBar title="反作废" icon="new-icon-uncancellation"  funname="unCancellBillInfo"></t:dgToolBar>
      <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScPoStockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>

      <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
      <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
      <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('销售退货单', '#tScSlStockbillList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/sales/tScSlStockbillList.js"></script>		
 <script type="text/javascript">
     function goDetail(rowIndex,rowData){
         url ='tScSlStockbillController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
         createdetailwindow('查看', url, null, null,"tab");
     }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
//     $("#tScSlStockbillList").datagrid({
//
//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScSlStockbillController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null,"tab");
//
//         },
//         onLoadSuccess : function(data){
//             if(data.rows.length > 0){
//                 mergeCellsByField("tScSlStockbillList", "ck,trantype,date,billNo,checkState,checkerName,checkDate,itemName,contact,mobilePhone,phone,fax,address,empName,deptName,stockName,rebateAmount,allAmount,curPayAmount,accountId,billerID,cancellation,explanation,sonId,opt,weight,freight","id");
//             }
//         }
//
//     });
 		//给时间控件加上样式
 			$("#tScSlStockbillListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='checkdate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScSlStockbillListtb").find("input[name='checkdate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScSlStockbillController.do?upload', "tScSlStockbillList");
}

//导出
function ExportXls() {
    var tranType = $("#tranType").val();
	JeecgExcelExport("tScSlStockbillController.do?exportXls&tranType="+tranType,"tScSlStockbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScSlStockbillController.do?exportXlsByT","tScSlStockbillList");
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
     var selected = $("#tScSlStockbillList").datagrid("getSelected");
     if(null != selected) {
         var isEdit = $("#isEdit").val();
         var checkState = selected.checkState;
         var date = new Date(selected.date).getTime();
         <%--var checkDate = new Date('${checkDate}').getTime();--%>
         <%--if(date<checkDate){--%>
             <%--tip("该单据未在本期间内，不可编辑");--%>
             <%--return;--%>
         <%--}--%>

         if (checkState == 1 && !isEdit) {
             tip("该单据正在审核中，不可编辑");
             return;
         }
         if (checkState == 2 && !isEdit) {
             tip("该单据已审核，不可编辑");
             return;
         }
//         var closed = selected.closed;
//         if(closed == 1){
//             tip("该单据已关闭，不可编辑");
//             return;
//         }
         var cancellation = selected.cancellation;
         if(cancellation == 1){
             tip("该单据已作废，不可编辑");
             return;
         }
     }
     var url = "tScSlStockbillController.do?goUpdate";
     update("编辑", url, "tScSlStockbillList", null, null, "tab");
 }
 //反审核前校验
 function checUnkAudit(billId){
//     var selects = $("#tScSlStockbillList").datagrid("getSelections");
//     if(selects.length = 0){
//         tip("请选择一条数据");
//         return;
//     }else if(selects.length > 1){
//         tip("请选择唯一数据");
//         return;
//     }
//     billId = selects[0].id;
     var isCheckNegative = $("#isCheckNegative").val();
     if(isCheckNegative) {
         var tranType = $("#tranType").val();
         var isContinue = true;
         var itemName = "";
         /**
          *参数说明
          * id 单据id
          * tranTyoe 单据类型
          * tableName 明细表名
          * parentId 关联id
          */
         $.ajax({
             async: false,
             url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=T_SC_Sl_StockBillEntry&parentId=fid",
             type: "POST",
             dataType: "json",
             success: function (data) {
                 if(!data.success){
                     isContinue = false;
                     //itemName = data.msg;
                 }
             }
         })
         if (isContinue) {
             return false;
         } else {
             //itemName = itemName.substring(0, itemName.length - 1);
             return "该单据反审核后将出现负库存，不可反审核，请确认";
         }
     }else{
         return false;
     }
 }
 </script>