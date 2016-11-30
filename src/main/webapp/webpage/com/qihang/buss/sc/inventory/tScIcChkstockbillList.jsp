<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScIcChkstockbillList" tranType="160" tableName="t_sc_ic_chkstockbill" beforeAccount="true" scrollview="true"
              checkbox="false" fitColumns="false" title="盘点单" actionUrl="tScIcChkstockbillController.do?datagrid&isWarm=${isWarm}"
              idField="entryId" fit="true" queryMode="group" onDblClick="goDetail" rowStyler="dgRowStyler">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"  query="true"  queryMode="single"  mergeCells="true" width="95"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" query="true" formatter="yyyy-MM-dd"   queryMode="group"  mergeCells="true" width="75"></t:dgCol>
   <t:dgCol title="仓库id"  field="stockId" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="仓库"  field="stockName"  query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="盘点日期"  field="pdDate" query="true" formatter="yyyy-MM-dd"  queryMode="group"  mergeCells="true" width="75"></t:dgCol>
   <t:dgCol title="盘点类型"  field="chkType" replace="即时库存_0,截止日期_1" query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>

      <t:dgCol title="明细id"  field="entryId" hidden="true"   queryMode="single"  width="45"></t:dgCol>
      <t:dgCol title="分录号"  field="findex"    queryMode="single"  width="45"></t:dgCol>
      <t:dgCol title="商品编号"  field="itemNo"    queryMode="single"  width="105"></t:dgCol>
      <t:dgCol title="商品名称"  field="itemName"    queryMode="single"  width="180"></t:dgCol>
      <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
      <t:dgCol title="条形码"  field="barCode"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="批号"  field="batchNo"    queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="单位"  field="unitName"    queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="账面箱数"  field="qty"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="账面散数"  field="smallQty"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="账面数量"  field="basicQty"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="箱数"  field="chkQty"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="散数"  field="chkSmallQty"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="换算率"  field="coefficient"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="盘点数量"  field="chkBasicQty"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="差异数量"  field="diffQty"  extendParams="styler:function(v,r,i){return showColor(v,r,i);},"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="成本单价"  field="costPrice"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="账面金额"  field="amount"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="盘点金额"  field="chkAmount"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="差异金额"  field="diffAmount"  extendParams="styler:function(v,r,i){return showColor(v,r,i);},"    queryMode="single"  width="70"></t:dgCol>

   <t:dgCol title="是否自动盘点"  field="isAuto" dictionary="sf_01"  queryMode="single"  mergeCells="true" width="90"></t:dgCol>
   <t:dgCol title="经办人id"  field="empId" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="经办人"  field="empName" query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="部门id"  field="deptId" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="部门"  field="deptName" query="true" queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="摘要"  field="explanation"    queryMode="single"  mergeCells="true" width="180"></t:dgCol>
   <t:dgCol title="制单人id"  field="billerId"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billerName"    queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="审核人id"  field="checkerId"  hidden="true"   queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="checkerName"    queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate" formatter="yyyy-MM-dd"   queryMode="single"  mergeCells="true" width="128"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState" query="true" replace="未审核_0,审核中_1,已审核_2"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="作废标记"  field="cancellation" query="true" replace="未作废_0,已作废_1"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="分支机构id"  field="sonId" hidden="true" queryMode="single"  mergeCells="true" width="80"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonName" queryMode="single"  mergeCells="true" width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" mergeCells="true" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" exp="cancellation#eq#0&&checkState#eq#0" url="tScIcChkstockbillController.do?doDel&id={id}" />
    <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0&&cancellation#eq#0" funname="goUnAudit(id,billNo,date,cancellation,{0},{0})"/>--%>
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScIcChkstockbillController.do?goAdd&tranType=160" funname="goAdd"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScIcChkstockbillController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
   <t:dgToolBar title="自动盘点" icon="new-icon-chkstockbill" url="tScIcChkstockbillController.do?goAdd" funname="AutoChk"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScIcChkstockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScIcChkstockbillController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="作废" icon="new-icon-cancellation"  funname="cancellBillInfo"></t:dgToolBar>
      <t:dgToolBar title="反作废" icon="new-icon-uncancellation"  funname="unCancellBillInfo"></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('盘点单', '#tScIcChkstockbillList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/inventory/tScIcChkstockbillList.js"></script>		
 <script type="text/javascript">
     function goDetail(rowIndex,rowData){
         url ='tScIcChkstockbillController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
         createdetailwindow('查看', url, null, null,"tab");
     }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
//     $("#tScIcChkstockbillList").datagrid({
//
//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScIcChkstockbillController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null,"tab");
//
//         },
//         onLoadSuccess : function(data){
//             if(data.rows.length > 0){
//                 mergeCellsByField("tScIcChkstockbillList", "ck,trantype,pdDate,date,chkType,billNo,empName,deptName,stockName,billerName,cancellation,explanation,checkerName,checkDate,checkState,sonId,opt","id");
//             }
//         }
//
//     });
 		//给时间控件加上样式
 			$("#tScIcChkstockbillListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScIcChkstockbillListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScIcChkstockbillListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
            $("#tScIcChkstockbillListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
            $("#tScIcChkstockbillListtb").find("input[name='pdDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
            $("#tScIcChkstockbillListtb").find("input[name='pdDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScIcChkstockbillListtb").find("input[name='checkDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScIcChkstockbillController.do?upload', "tScIcChkstockbillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScIcChkstockbillController.do?exportXls&tranType=160","tScIcChkstockbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScIcChkstockbillController.do?exportXlsByT","tScIcChkstockbillList");
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
     var selected = $("#tScIcChkstockbillList").datagrid("getSelected");
     if(null != selected) {
         var isEdit = $("#isEdit").val();
         var checkState = selected.checkState;
         //var date = new Date(selected.date).getTime();
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
     var url = "tScIcChkstockbillController.do?goUpdate";
     update("编辑", url, "tScIcChkstockbillList", null, null, "tab");
 }

 </script>