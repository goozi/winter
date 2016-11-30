<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <div id="mm1" class="easyui-menu" style="width:120px;">
          <div onclick="selectBill()" data-options="iconCls:'icon-add'"><font color="black">选择本单</font></div>
          <div onclick="unselect()" data-options="iconCls:'icon-edit'"><font color="black">取消选择</font></div>
      </div>
  <t:datagrid name="tScSlStockbillList" checkbox="true"  fitColumns="false"
              title="销售出库单" actionUrl="tScSlStockbillController.do?datagrid&checkState=2&cancellation=0&itemid=${itemId}&isstock=0&tranType=103&sonId=${sonId}"
              idField="id" fit="true" queryMode="group" onLoadSuccess="loadsuccess">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单据类型"  field="tranTypeName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single"  width="95"></t:dgCol>
      <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="75"></t:dgCol>
      <t:dgCol title="客户id"  field="itemId" hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="客户"  field="itemName" queryMode="single"  width="160"></t:dgCol>
      <t:dgCol title="经办人id"  field="empid" hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="经办人"  field="empName" query="true"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="部门id"  field="deptid" hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="部门"  field="deptName" query="true"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="仓库id"  field="stockid" hidden="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="仓库"  field="stockName" hidden="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="应付金额"  field="allAmount"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="优惠金额"  field="rebateAmount"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="结算账户"  field="accountid"   queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="本次付款"  field="curPayAmount"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="重量"  field="weight"   queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="物流费用"  field="freight"   queryMode="single"  width="65"></t:dgCol>

      <t:dgCol title="明细id"  field="entryId" hidden="true"   queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="分录号"  field="findex"    queryMode="single"  width="45"></t:dgCol>
      <t:dgCol title="商品id"  field="entryItemId" hidden="true" queryMode="single"  width="105"></t:dgCol>
      <t:dgCol title="商品编号"  field="entryItemNo"  queryMode="single"  width="105"></t:dgCol>
      <t:dgCol title="商品名称"  field="entryItemName"  query="true"   queryMode="single"  width="180"></t:dgCol>
      <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
      <t:dgCol title="条码"  field="barCode"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="仓库"  field="entryStockName"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="仓库"  field="entryStockId"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="批号"  field="batchNo"    queryMode="single"  width="95"></t:dgCol>
      <t:dgCol title="单位"  field="unitName"    queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="单位id"  field="unitid"  hidden="true"   queryMode="single"  width="50"></t:dgCol>
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
      <t:dgCol title="生产日期"  field="kfdate"  formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="保质期"  field="kfperiod" hidden="true"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="保质期"  field="kfdateType"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="有效期至"  field="perioddate"  formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="促销类型"  field="salesID"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="是否赠品"  field="freegifts"   dictionary="sf_01"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="退货数量"  field="commitQty"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="源单类型"  field="entryClassIdSrc"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="源单编号"  field="entryBillNoSrc"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="订单编号"  field="billNoOrder"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="订单id"  field="idOrder" hidden="true" queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="订单分类id"  field="entryIdOrder" hidden="true" queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="商品重量"  field="itemweight" hidden="true"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="是否批号管理"  field="batchManager" hidden="true"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="是否保质期"  field="isKfperiod" hidden="true"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="保质期类型"  field="kfdateTypeInfo" hidden="true"  queryMode="single"  width="90"></t:dgCol>
      <t:dgCol title="备注"  field="note"  queryMode="single"  width="180"></t:dgCol>

      <t:dgCol title="摘要"  field="explanation"    queryMode="single"  width="180"></t:dgCol>
      <t:dgCol title="联系人"  field="contact"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="手机"  field="mobilePhone"    queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="电话"  field="phone"    queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="传真"  field="fax"    queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="联系地址"  field="address"    queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="单据金额"  field="amount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="付款金额"  field="checkAmount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="源单类型"  field="classIDSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="源单主键"  field="idSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="源单编号"  field="billNoSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="制单人"  field="billerID"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="审核人"  field="checkerName" hidden="true"   queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="审核日期"  field="checkDate" hidden="true"  formatter="yyyy-MM-dd"  queryMode="single"  width="128"></t:dgCol>
      <t:dgCol title="审核状态"  field="checkState" hidden="true" replace="未审核_0,审核中_1,已审核_2"   queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="作废标记"  field="cancellation" hidden="true" replace="未作废_0,已作废_1"   queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="分支机构"  field="sonName"    queryMode="single"  width="80"></t:dgCol>
      <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
      <%--<t:dgFunOpt title="删除" exp="checkState#eq#0&&cancellation#eq#0" funname="doDel(id,billNo,date)" />--%>
      <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0&&cancellation#eq#0" funname="goUnAudit(id,billNo,date,cancellation,{0},{0})"/>--%>
      <%--<t:dgToolBar title="新增" icon="new-icon-add" url="tScSlStockbillController.do?goAdd&tranType=103" funname="add"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="编辑" icon="new-icon-edit" url="tScSlStockbillController.do?goUpdate" funname="goUpdate"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="作废" icon="new-icon-cancellation"  funname="cancelBill"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="反作废" icon="new-icon-uncancellation"  funname="enableBill"></t:dgToolBar>--%>
      <%--&lt;%&ndash;<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScPoStockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>&ndash;%&gt;--%>
      <%--<t:dgToolBar title="查看" icon="new-icon-view" url="tScSlStockbillController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>--%>
      <%--&lt;%&ndash;<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>&ndash;%&gt;--%>
      <%--<t:dgToolBar title="打印" icon="new-icon-print"--%>
                   <%--funname="CreateFormPage('销售出库单', '#tScSlStockbillList')"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/sales/tScSlStockbillList.js"></script>		
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
                 msg: '请选择销售出库',
                 timeout: 2000,
                 showType: 'slide'
             });
             return '';
         }
     }
     function loadsuccess(data){
         if(data.rows.length > 0){
             mergeCellsByField("tScSlStockbillList", "trantype,date,billNo,checkState,checkerName,checkDate," +
                     "itemName,contact,mobilePhone,phone,fax,address,empName,deptName,stockName,rebateAmount," +
                     "allAmount,curPayAmount,accountid,billerID,cancellation,explanation,sonId,opt,weight,freight","id");
         }
         for (var i = 0; i < data.rows.length; i++) {
             var row = data.rows[i];
             var id = row.entryId;
             for (var j = 0; j < selectInfo.length; j++) {
                 var inf = selectInfo[j];
                 if (id == inf.entryId) {
                     $("#tScSlStockbillList").datagrid("selectRow", i);
                     //popJon.remove(j);
                     break;
                 }
             }
         }
     }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScSlStockbillList").datagrid({

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
                     if (val.entryId == rowData.entryId) {
                         isPush = false;
                     }
                 }
                 if (isPush) {
                     popJon.push(rowData);
                     selectInfo.push(rowData);
                 }
             } else {
                 popJon.push(rowData);
                 selectInfo.push(rowData);
             }
         },
         onUnselect: function (rowIndex, rowData) {
             for (var i = 0; i < popJon.length; i++) {
                 var value = popJon[i];
                 if (value.entryId == rowData.entryId) {
                     popJon.remove(i);
                     selectInfo.remove(i);
                     break;
                 }
             }
         },
         onSelectAll: function (rows) {
             for (var i = 0; i < rows.length; i++) {
                 var row = rows[i];
                 var checkNum = 0;
                 for (var j = 0; j < popJon.length; j++) {
                     if (row.entryId != popJon[j].entryId) {
                         checkNum++;
                     }
                 }
                 if (checkNum == popJon.length) {
                     popJon.push(row);
                     selectInfo.push(row);
                 }
             }
         },
         onUnselectAll: function (rows) {
             for (var i = 0; i < rows.length; i++) {
                 var row = rows[i];
                 for (var j = 0; j < popJon.length; j++) {
                     if (row.entryId == popJon[j].entryId) {
                         popJon.remove(j);
                         selectInfo.remove(j);
                         break;
                     }
                 }
             }
         },
         onRowContextMenu: function (e, rowIndex, rowData) {
             return showRightMenu(e, rowData);
         }

     });
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
	JeecgExcelExport("tScSlStockbillController.do?exportXls","tScSlStockbillList&tranType="+tranType);
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScSlStockbillController.do?exportXlsByT","tScSlStockbillList");
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
     //右键菜单配置
     var selectRow;
     function showRightMenu(e, rowData) {
         selectRow = rowData;
         e.preventDefault();
         //显示快捷菜单
         $('#mm1').menu({
             onShow: function () {
                 selectRow = rowData;
             }
         });
         $('#mm1').menu('show', {
             left: e.pageX,
             top: e.pageY
         });
     }

     var selectInfo = new Array();
     //单据全选
     function selectBill() {
         var id = selectRow.id;
         var entryUrl = "tScSlStockbillController.do?loadEntryViewList&id=" + id;
         $.ajax({
             url: entryUrl,
             dataType: 'json',
             cache: false,
             async: false,
             success: function (data) {
                 var chooseRows = data;
                 for(var i = 0 ; i<data.length ;i++) {
                     var r = data[i];
                     selectInfo.push(r);
                 }
//                 if (popJon.length > 0) {
//                     for (var i = 0; i < rows.length; i++) {
//                         var row = rows[i];
//                         for (var j = 0; j < popJon.length; j++) {
//                             var value = popJon[j];
//                             if (value.entryId == value.entryId) {
//                                 popJon.remove(j);
//                                 break;
//                             }
//                         }
//                     }
//                     for (var i = 0; i < rows.length; i++) {
//                         var row = rows[i];
//                         popJon.push(row);
//                     }
//                 } else {
//                     popJon = rows;
//                 }
                 var rows = $("#tScSlStockbillList").datagrid("getRows");
                 for (var index = 0; index < rows.length; index++) {
                     var rowInfo = rows[index];
                     for (var k = 0; k < chooseRows.length; k++) {
                         if (rowInfo.entryId == chooseRows[k].entryId) {
                             $("#tScSlStockbillList").datagrid("selectRow", index);
                             break;
                         }
                     }
                 }
             }
         });
     }
     //取消选择
     function unselect() {
         var id = selectRow.id;
         var rows = $("#tScSlStockbillList").datagrid("getRows");
         for (var index = 0; index < rows.length; index++) {
             var rowInfo = rows[index];
             for (var k = 0; k < popJon.length; k++) {
                 if (rowInfo.id == id && rowInfo.id == popJon[k].id) {
                     $("#tScSlStockbillList").datagrid("unselectRow", index);
                     break;
                 }
             }
         }
     }
 </script>