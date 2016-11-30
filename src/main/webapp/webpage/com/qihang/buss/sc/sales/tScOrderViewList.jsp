<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScOrderList" checkbox="true"  tableName="t_sc_Order" tranType="102" scrollview="true" rowStyler="dgRowStyler"
              fitColumns="false" title="销售订单" actionUrl="tScOrderController.do?datagrid&isWarm=${isWarm}" beforeAccount="true" isSon="true"
              idField="entryId" fit="true" queryMode="group" onDblClick="goDetail">
   <t:dgCol title="从表主键"  field="entryId"  hidden="true"  queryMode="single" mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single" mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="主表主键"  field="id"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"  query="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="单据日期"  field="date"  query="true" formatter="yyyy-MM-dd"   queryMode="group"  mergeCells="true" width="75"></t:dgCol>
   <t:dgCol title="客户id"  field="aitemID" hidden="true" queryMode="single"  mergeCells="true" width="160"></t:dgCol>
   <t:dgCol title="客户"  field="aitemName" autocomplete="true" query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="经办人id"  field="empID" hidden="true" queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="经办人"  field="empName" autocomplete="true" query="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="部门id"  field="deptID" hidden="true" queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="部门"  field="deptName" autocomplete="true" query="true" queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="收款方式"  field="fetchStyle" dictionary="SC_PAY_TYPE"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="应收金额"  field="allAmount" extendParams="formatter:function(v,r,i){return formatterMoney(v);},"  queryMode="single"  mergeCells="true" width="70"></t:dgCol>
   <t:dgCol title="预收金额"  field="preAmount"  extendParams="formatter:function(v,r,i){return formatterMoney(v);}," queryMode="single"  mergeCells="true" width="70"></t:dgCol>
   <t:dgCol title="优惠金额"  field="rebateAmount"  extendParams="formatter:function(v,r,i){return formatterMoney(v);}," queryMode="single"  mergeCells="true" width="70"></t:dgCol>
   <t:dgCol title="物流费用"  field="freight" extendParams="formatter:function(v,r,i){return formatterMoney(v);},"  queryMode="single"  mergeCells="true" width="70"></t:dgCol>
   <t:dgCol title="重量"  field="aweight" extendParams="formatter:function(v,r,i){var m = $('#CFG_OTHER').val();var newV = parseFloat(v).toFixed(m); return newV;},"  queryMode="single"  mergeCells="true" width="70"></t:dgCol>
   <t:dgCol title="订单来源"  field="mall" replace="系统新增_1,商城订单_2"  queryMode="single"  mergeCells="true" width="80"></t:dgCol>
   <t:dgCol title="商城单号"  field="mallOrderID"  extendParams="formatter:function(v,r,i){if(v == 0){v='';} return v;}," queryMode="single"  mergeCells="true" width="80"></t:dgCol>

   <t:dgCol title="分录号"  field="indexNumber"   queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品编号"  field="itemNumber"   queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品名称"  field="itemName" autocomplete="true" query="true"   queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"   queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条形码"  field="barCode"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库id"  field="stockID" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="仓库"  field="stockName"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="单位"  field="unitName"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="数量"  field="qty"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="单价"  field="taxPriceEx"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="金额"  field="taxAmountEx"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折扣率%"  field="discountRate"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折后单价"  field="taxPrice"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="折后金额"  field="inTaxAmount"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="商城单价"  field="mallPrice"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="商城金额"  field="mallAmount"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="重量"  field="bweight"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="税率"  field="taxRate"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="交货日期"  field="fetchDate"  formatter="yyyy-MM-dd"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="促销类型Id"  field="salesID" hidden="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="促销类型"  field="salesName"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="促销类型" hidden="true" field="isFreeGift"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="赠品标记"  dictionary="sf_01" field="freeGifts" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="发货数量"  field="stockQty" queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="源单类型"  field="classIDSrc" hidden="true" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="源单类型"  field="classIDName" queryMode="single"  width="90"></t:dgCol>
   <t:dgCol title="备注"  field="note" queryMode="single"  width="180"></t:dgCol>

   <t:dgCol title="摘要"  field="explanation"  queryMode="single"  mergeCells="true" width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"   hidden="true"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"   hidden="true"  queryMode="single"  mergeCells="true" width="80"></t:dgCol>
   <t:dgCol title="电话"  field="phone"   hidden="true"  queryMode="single"  mergeCells="true" width="80"></t:dgCol>
   <t:dgCol title="联系地址"  field="address"   hidden="true"  queryMode="single"  mergeCells="true" width="150"></t:dgCol>
   <t:dgCol title="执行金额"  field="checkAmount"   hidden="true"  queryMode="single"  mergeCells="true" width="70"></t:dgCol>
   <t:dgCol title="制单人id"  field="billerID"   hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billerName"   queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="审核人id" field="checkerId" hidden="true" mergeCells="true" queryMode="single" width="65"></t:dgCol>
   <t:dgCol title="审核人" field="checkerName" mergeCells="true" queryMode="single" width="65"></t:dgCol>
   <t:dgCol title="审核日期" field="checkDate" mergeCells="true" formatter="yyyy-MM-dd" queryMode="single" width="128"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState" query="true" dictionary="SC_AUDIT_STATUS"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="关闭标记"  field="closed" query="true"  replace="未关闭_0,已关闭_1"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="自动关闭标记"  field="autoFlag"  replace="否_0,是_1" hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="作废标记"  field="cancellation" query="true"  replace="未作废_0,已作废_1"  queryMode="single"  mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonName"   hidden="true"  queryMode="single"  mergeCells="true" width="80"></t:dgCol>
   <t:dgCol title="是否被引用"  field="isStock"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="信用额度是否满足"  field="isAllowAudit"  hidden="true"  queryMode="single"  mergeCells="true" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" mergeCells="true" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" exp="checkState#eq#0&&cancellation#eq#0&&closed#eq#0" url="tScOrderController.do?doDel&id={id}" />
   <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0&&cancellation#eq#0&&closed#eq#0" funname="goUnAudit(id,billNo,date,cancellation,isStock,closed)"/>--%>
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScOrderController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScOrderController.do?goUpdate" funname="updateOrder"></t:dgToolBar>
   <t:dgToolBar title="复制" icon="new-icon-copy" url="tScOrderController.do?goUpdate" funname="fcopy"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScOrderController.do?doBatchDel" funname="deleteALLSelectOrder"></t:dgToolBar>--%>
   <t:dgToolBar title="变更" icon="new-icon-edit" url="tScOrderController.do?goUpdate" funname="goChange"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="new-icon-view" windowType="tab" url="tScOrderController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="关闭" exp="closed#eq#0" icon="new-icon-close"  funname="closeBillInfo"></t:dgToolBar>
   <t:dgToolBar title="反关闭" exp="closed#ne#0" icon="new-icon-unclose"  funname="unCloseBillInfo"></t:dgToolBar>
   <t:dgToolBar title="作废" exp="cancellation#eq#0" icon="new-icon-cancellation"  funname="cancellBillInfo"></t:dgToolBar>
   <t:dgToolBar title="反作废" exp="cancellation#ne#0" icon="new-icon-uncancellation"  funname="unCancellBillInfo"></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('销售订单', '#tScOrderList')"></t:dgToolBar>
   <t:dgToolBar title="同步订单" icon="new-icon-syn"
                onclick="sysOrder()"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/sales/tScOrderList.js"></script>		
 <script type="text/javascript">
  function goDetail(rowIndex,rowData){
    url ='tScOrderController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
    createdetailwindow('查看', url, null, null,"tab");
  }
  function formatterMoney(v){
     if(v){
      var CFG_MONEY = $("#CFG_MONEY").val();
      return parseFloat(v).toFixed(CFG_MONEY);
     }else{
      return "";
     }
  }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
//     $("#tScOrderList").datagrid({
//
//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScOrderController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null,"tab");
//         },
//
//        onLoadSuccess : function(data){
//            if(data.rows.length > 0){
//               mergeCellsByField("tScOrderList", "billNo,date,aitemID,aitemName,empID,empName,deptID,deptName,fetchStyle,allAmount,preAmount,rebateAmount,freight,aweight,mall,mallOrderID,auditorName,auditDate,checkState,closed,cancellation,billerName,opt","id");
//            }
//        }
//
//     });
 		//给时间控件加上样式
 			$("#tScOrderListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScOrderListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScOrderListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
            $("#tScOrderListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

            $("#tScOrderListtb").find("input[name='checkDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 function deleteALLSelectOrder(title, url, gname) {
  gridname = gname;
  var ids = [];
  var rows = $("#" + gname).datagrid('getSelections');
  if (rows.length > 0) {
   $.dialog.confirm('你确定永久删除该数据吗?', function (r) {
    if (r) {
     for (var i = 0; i < rows.length; i++) {
      ids.push(rows[i].zid);
     }
     $.ajax({
      url: url,
      type: 'post',
      data: {
       ids: ids.join(',')
      },
      cache: false,
      success: function (data) {
       //var d = $.parseJSON(data);
       var d = data;
       if (d.success) {
        var msg = d.msg;
        tip(msg);
        reloadTable();
        $("#" + gname).datagrid('unselectAll');
        ids = '';
       }
      }
     });
    }
   });
  } else {
   tip("请选择需要删除的数据");
  }
 }

// function mergeCellsByField(tableID, colList,id) {
//  var ColArray = colList.split(",");
//  var tTable = $("#" + tableID);
//  var TableRowCnts = tTable.datagrid("getRows").length;
//  var tmpA;
//  var PerTxt = "";
//  var CurTxt = "";
//  var PerId = "";
//  var CurId = "";
//  for (var j = ColArray.length - 1; j >= 0; j--) {
//   PerTxt = "";
//   PerId = "";
//   tmpA = 1;
//   for (var i = 0; i <= TableRowCnts; i++) {
//    if (i == TableRowCnts) {
//     CurTxt = "";
//     CurId = "";
//    }
//    else {
//     CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
//     CurId = tTable.datagrid("getRows")[i][id];
//
//    }
//    if ((PerTxt == CurTxt)&&(PerId==CurId)) {
//     tmpA += 1;
//    }
//    else {
//     var index =  i - tmpA;
//     tTable.datagrid("mergeCells", {
//      index: index,
//      field: ColArray[j],　　//合并字段
//      rowspan: tmpA,
//      colspan: null
//     });
//     tmpA = 1;
//    }
//    PerTxt = CurTxt;
//    PerId = CurId;
//   }
//  }
// }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScOrderController.do?upload', "tScOrderList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScOrderController.do?exportXls","tScOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScOrderController.do?exportXlsByT","tScOrderList");
}
 </script>