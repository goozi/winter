<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <%--<div id="mm1" class="easyui-menu" style="width:120px;">
          <div onclick="selectBill()" data-options="iconCls:'icon-add'"><font color="black" >选择本单</font></div>
          <div onclick="unselect()" data-options="iconCls:'icon-edit'"><font color="black">取消选择</font></div>
      </div>--%>
  <%--&closed=0&cancellation=0&checkState=2&tranType=${tranType}&itemId=${itemId}--%>
  <t:datagrid name="tScDrpOrderList" checkbox="true" fitColumns="false" title="订货管理"
              actionUrl="tScDrpOrderController.do?datagridSelect&closed=0&cancellation=0&checkState=2&tranType=${tranType}&itemId=${itemId}&sonID=${sonId}" idField="entryId" fit="true" queryMode="group"
              tableName="t_sc_drp_order">
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
      <t:dgCol title="interID" field="interID" hidden="true" width="80"/>
      <t:dgCol title="tranTypeName" field="tranTypeName" hidden="true" width="80"/>
      <t:dgCol title="rOrderId" field="rOrderId" hidden="true" width="80"/>
   <t:dgCol title="单据编号" mergeCells="true" field="billNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据日期" mergeCells="true" field="date" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="上游经销商" mergeCells="true" field="itemName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经办人" mergeCells="true" field="empName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门" mergeCells="true" field="deptName"   queryMode="single"  width="120"></t:dgCol>
   <%-- <t:dgCol title="收款方式"   field="test"    queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="应付金额"  field="allAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="优惠金额"  field="rebateAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分录号"  field="indexNumber"    queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品编号"  field="goodsItemID" hidden="true"   queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品编号"  field="goodsNo"    queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品名称"  field="goodsName"    queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条形码"  field="barCode"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="stockID" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="stockName"  dictionary="T_SC_STOCK,id,name"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="单位" field="unitName" width="80"/>
   <t:dgCol title="数量"  field="qty"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitID"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient" queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="基本数量"  field="basicQty"   queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="辅助单位"  field="secUnitID"    queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="辅助换算率"  field="secCoefficient"    queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="辅助数量"  field="secQty"   queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="单价"  field="taxPriceEx"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="金额"  field="taxAmountEx"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="折扣率(%)"  field="discountRate"  queryMode="single"  width="65"></t:dgCol>
  <t:dgCol title="折后单价"  field="taxPrice"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="折后金额"  field="inTaxAmount"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="税率(%)"  field="taxRate"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税单价"  field="price"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税金额"  field="amount"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税折后单价"  field="discountPrice"  queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="不含税折后金额"  field="discountAmount" queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="税额"  field="taxAmount" queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="收货确认数量"  field="affirmQty" queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="收货日期"  field="jhDate" formatter="yyyy-MM-dd" queryMode="group"   width="80"></t:dgCol>
  <t:dgCol title="发货数量"  field="outStockQty" queryMode="single"  width="70"></t:dgCol>
  <t:dgCol title="发货日期"  field="outDate"  formatter="yyyy-MM-dd" queryMode="group"  width="80"></t:dgCol>
  <t:dgCol title="备注"  field="note" queryMode="single"  width="180"></t:dgCol>

      <t:dgCol title="摘要" mergeCells="true"  field="explanation"    queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"    queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"    queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="联系地址"  field="address"    queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="执行金额"  field="checkAmount"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="制单人" mergeCells="true" field="billerName"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="审核人" mergeCells="true" field="auditorName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核日期" mergeCells="true"  field="checkDate"   queryMode="single"  width="128"></t:dgCol>
   <t:dgCol title="审核状态" mergeCells="true" replace="未审核_0,审核中_1,已审核_2" field="checkState"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="关闭标记"  mergeCells="true" field="closed"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="自动关闭标记" mergeCells="true"  field="autoFlag"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <%--<t:dgCol title="作废标记"  replace="未作废_0,已作废_1" field="cancellation" queryMode="single"  width="65"></t:dgCol>--%>
   <t:dgCol title="分支机构"  field="sonID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="分支机构"  field="sonName"    queryMode="single"  width="120"></t:dgCol>
      <%--<t:dgCol title="操作" field="opt" mergeCells="true" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScDrpOrderController.do?doDel&id={id}" exp="checkState#eq#0"/>
   <t:dgToolBar title="新增" icon="icon-add" url="tScDrpOrderController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tScDrpOrderController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
  <t:dgToolBar title="批量删除"  icon="icon-remove" url="tScDrpOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  <t:dgToolBar title="变更" icon="icon-edit" url="tScDrpOrderController.do?goUpdate" funname="goChange"></t:dgToolBar>
  <t:dgToolBar title="关闭" icon="icon-remove"  funname="closeBill"></t:dgToolBar>
  <t:dgToolBar title="反关闭" icon="icon-remove"  funname="openBill"></t:dgToolBar>
  <t:dgToolBar title="作废" icon="icon-remove" exp="cancellation#eq#0" funname="cancellBillInfo"></t:dgToolBar>
  <t:dgToolBar title="反作废" icon="icon-remove" exp="cancellation#ne#0" funname="unCancellBillInfo"></t:dgToolBar>

   <t:dgToolBar title="查看" icon="icon-search" url="tScDrpOrderController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   &lt;%&ndash;<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>&ndash;%&gt;
      <t:dgToolBar title="打印" icon="icon-print"
                   funname="CreateFormPage('订货管理', '#tScDrpOrderList')"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/distribution/tScDrpOrderList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
    /* $("#tScDrpOrderList").datagrid({
         onDblClickRow:function(rowIndex,rowData){
             url ='tScDrpOrderController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null);
         }
     });*/
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
 //变更页面跳转
 function goChange(){
     var selected = $("#tScDrpOrderList").datagrid("getSelected");
     if(null != selected) {
         var checkState = selected.checkState;
         if (checkState == 1) {
             tip("该单据正在审核中，不可变更");
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
     var url = "tScDrpOrderController.do?goUpdate&checkState="+checkState;
     update("变更", url, "tScDrpOrderList", null, null, "tab");
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