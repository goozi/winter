<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <div id="mm1" class="easyui-menu" style="width:120px;">
          <div onclick="selectBill()" data-options="iconCls:'icon-add'"><font color="black" >选择本单</font></div>
          <div onclick="unselect()" data-options="iconCls:'icon-edit'"><font color="black">取消选择</font></div>
      </div>
  <t:datagrid name="tPoOrderList" checkbox="true" fitColumns="false" title="采购订单" actionUrl="tPoOrderController.do?datagrid&itemId=${itemId}&closed=0&cancellation=0&checkState=2&isStock=0&autoFlag=0&sonId=${sonId}" idField="id" fit="true" queryMode="group" sortName="billNo" sortOrder="desc" >
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="单据类型"  field="tranTypeName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="单据编号"  field="billNo" query="true"   queryMode="single"  width="95"></t:dgCol>
     <t:dgCol title="单据日期"  field="date" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="75"></t:dgCol>
     <t:dgCol title="供应商"  field="itemName" queryMode="single"  width="160"></t:dgCol>
     <t:dgCol title="供应商"  field="itemId" hidden="true" queryMode="single"  width="160"></t:dgCol>
     <t:dgCol title="经办人"  field="empName" queryMode="single"  width="65"></t:dgCol>
     <t:dgCol title="经办人"  field="empId" hidden="true" queryMode="single"  width="65"></t:dgCol>
     <t:dgCol title="部门"  field="deptName" queryMode="single"  width="65"></t:dgCol>
     <t:dgCol title="部门"  field="deptId" hidden="true"  queryMode="single"  width="65"></t:dgCol>
     <t:dgCol title="仓库"  field="stockName" hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="仓库"  field="stockId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="优惠金额"  field="rebateAmount"    queryMode="single"  width="70"></t:dgCol>
     <t:dgCol title="订单金额"  field="amount"  hidden="true"  queryMode="single"  width="70"></t:dgCol>
     <t:dgCol title="应付金额"  field="allAmount"    queryMode="single"  width="70"></t:dgCol>
     <t:dgCol title="执行金额"  field="checkAmount"  hidden="true"  queryMode="single"  width="70"></t:dgCol>

   <t:dgCol title="明细id"  field="entryId" hidden="true"   queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="分录号"  field="index"    queryMode="single"  width="45"></t:dgCol>
   <t:dgCol title="商品id"  field="entryItemId" hidden="true"   queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品编号"  field="entryItemNo" query="true"   queryMode="single"  width="105"></t:dgCol>
   <t:dgCol title="商品名称"  field="entryItemName"    queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
   <t:dgCol title="条码"  field="barCode" query="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="entryStockName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="仓库"  field="entryStockId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="单位id"  field="unitId" hidden="true"    queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="单位"  field="unitName"    queryMode="single"  width="50"></t:dgCol>
   <t:dgCol title="数量"  field="qty"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="基本单位"  field="basicUnitId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="基本单位"  field="basicCoefficient" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="换算率"  field="coefficient"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="基本数量"  field="basicQty"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="辅助单位" hidden="true"  field="secUnitName"    queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="辅助单位Id" hidden="true"  field="secUnitId"    queryMode="single"  width="65"></t:dgCol>
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
   <t:dgCol title="是否赠品"  field="freeGifts"  dictionary="sf_01"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="收货数量"  field="stockQty"  queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="是否保质期"  field="isKFPeriod" hidden="true"  queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="是否批号"  field="batchManager" hidden="true" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="保质期类型"  field="kfDateType" hidden="true" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="保质期"  field="kfPeriod" hidden="true" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="采购限价"  field="cgLimitPrice" hidden="true" queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="备注"  field="note"  queryMode="single"  width="150"></t:dgCol>

   <t:dgCol title="摘要"  field="explanation" hidden="true"   queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="联系人"  field="contact" hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone" hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="电话"  field="phone" hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="传真"  field="fax"  hidden="true" queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="联系地址" hidden="true" field="address"  queryMode="single"  width="70"></t:dgCol>
   <t:dgCol title="源单类型"  field="classIdSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单主键"  field="idSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billerId" hidden="true"   queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核人"  field="auditorName" hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核状态"  field="state" hidden="true" dictionary="SC_AUDIT_STATUS"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="审核日期"  field="auditDate" hidden="true"  queryMode="single"  width="128"></t:dgCol>
   <t:dgCol title="关闭标记"  field="closed"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="自动关闭标记"  field="autoFlag"  hidden="true"  queryMode="single"  width="65"></t:dgCol>
   <t:dgCol title="作废状态" hidden="true" field="cancellation"  replace="未作废_0,已作废_1"  queryMode="single"  width="65"></t:dgCol>

   <t:dgCol title="分支机构"  field="sonName"  hidden="true"  queryMode="single"  width="80"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" exp="state#eq#0&&stockQty#eq#0.0&&cancellation#eq#0&&closed#eq#0" url="tPoOrderController.do?doDel&id={id}" />--%>
    <%--<t:dgFunOpt title="反审核" exp="state#ne#0&&cancellation#eq#0" funname="goUnAudit(id,billNo,date)"/>--%>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="tPoOrderController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tPoOrderController.do?goUpdate&tranType=51" funname="goUpdate"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="关闭" icon="icon-remove"  funname="closeBill"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="反关闭" icon="icon-remove"  funname="openBill"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="作废" icon="icon-remove"  funname="cancelBill"></t:dgToolBar>--%>
   <%--&lt;%&ndash;<t:dgToolBar title="批量删除"  icon="icon-remove" url="tPoOrderController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>&ndash;%&gt;--%>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="tPoOrderController.do?goUpdate" funname="detail" windowType="tab"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--&lt;%&ndash;<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>&ndash;%&gt;--%>
      <%--<t:dgToolBar title="打印" icon="icon-print"--%>
                   <%--funname="CreateFormPage('采购订单主表', '#tPoOrderList')"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <%--<script src = "webpage/com/qihang/buss/sc/sales/tScPoOrderList.js"></script>--%>
 <script type="text/javascript">
     Array.prototype.remove=function(dx)
     {
         if(isNaN(dx)||dx>this.length){return false;}
         for(var i=0,n=0;i<this.length;i++)
         {
             if(this[i]!=this[dx])
             {
                 this[n++]=this[i]
             }
         }
         this.length-=1
     }
     var popJon = new Array();
     var orgId;
     var isPop = false;
     function getSelectionData() {
         var rows = popJon;
         if (rows.length > 0) {
             isPop =true ;
             return rows;

         } else {
             parent.$.messager.show({
                 title: '提示消息',
                 msg: '请选择采购订单',
                 timeout: 2000,
                 showType: 'slide'
             });
             return '';
         }
     }


 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tPoOrderList").datagrid({
         onSelect:function(rowIndex,rowData){
             if(popJon.length) {
                 var isPush = true;
                 for (var i = 0; i < popJon.length; i++) {
                     var val = popJon[i];
                     if(val.entryId == rowData.entryId) {
                         isPush = false;
                     }
                 }
                 if(isPush) {
                     popJon.push(rowData);
                 }
             }else{
                 popJon.push(rowData);
             }
         },
         onUnselect:function(rowIndex,rowData){
             for(var i=0 ; i<popJon.length ; i++){
                 var value = popJon[i];
                 if(value.entryId == rowData.entryId){
                     popJon.remove(i);
                     break;
                 }
             }
         },
         onSelectAll:function(rows){
           for(var i=0 ; i<rows.length ; i++){
               var row = rows[i];
               var checkNum = 0;
               for(var j = 0 ; j<popJon.length ; j++){
                   if(row.entryId != popJon[j].entryId){
                       checkNum++;
                   }
               }
               if(checkNum == popJon.length){
                   popJon.push(row);
               }
           }
         },
         onUnselectAll:function(rows){
             for(var i=0 ; i<rows.length ; i++){
                 var row = rows[i];
                 for(var j = 0 ; j<popJon.length ; j++){
                     if(row.entryId == popJon[j].entryId){
                         popJon.remove(j);
                         break;
                     }
                 }
             }
         },
         onLoadSuccess:function(data){
             mergeCellsByField("tPoOrderList", "trantype,date,billNo,state,auditorName,auditDate,itemId,itemName," +
                     "contact,mobilePhone,phone,fax,address,empId,empName,deptId,deptName,stockId,stockName,rebateAmount,allAmount," +
                     "billerId,cancellation,explanation,sonId,opt","id");
             for(var i = 0; i<data.rows.length ;i++){
                 var row = data.rows[i];
                 var id = row.entryId;
                 for(var j=0 ; j<popJon.length ; j++){
                     var inf = popJon[j];
                     if(id==inf.entryId){
                         $("#tPoOrderList").datagrid("selectRow",i);
                         //popJon.remove(j);
                         break;
                     }
                 }
             }
         },
         onRowContextMenu:function(e, rowIndex, rowData){
             return showRightMenu(e,rowData);
         }

     });
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
	JeecgExcelExport("tPoOrderController.do?exportXls","tPoOrderList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPoOrderController.do?exportXlsByT","tPoOrderList");
}

//右键菜单配置
var selectRow;
 function showRightMenu(e,rowData){
     selectRow = rowData;
     e.preventDefault();
     //显示快捷菜单
     $('#mm1').menu({
         onShow:function(){
             selectRow = rowData;
         }
     });
     $('#mm1').menu('show',{
         left: e.pageX,
         top: e.pageY
     });
 }

 //单据全选
 function selectBill(){
     var id = selectRow.id;
     var entryUrl = "tPoOrderController.do?loadEntryViewList&id="+id;
     $.ajax({
         url: entryUrl,
         dataType: 'json',
         cache: false,
         async: false,
         success: function (data) {
             var rows = data;
             if(popJon.length > 0) {
                 for (var i = 0; i < rows.length; i++) {
                     var row = rows[i];
                     for(var j = 0 ; j < popJon.length ; j++) {
                         var value = popJon[j];
                         if(value.entryId == value.entryId) {
                             popJon.remove(j);
                             break;
                         }
                     }
                 }
                 for (var i = 0; i < rows.length; i++) {
                     var row = rows[i];
                     popJon.push(row);
                 }
             }else{
                 popJon = rows;
             }
             var rows = $("#tPoOrderList").datagrid("getRows");
             for(var index = 0 ; index < rows.length ; index++){
                 var rowInfo = rows[index];
                 for(var k = 0 ; k<popJon.length ; k++){
                     if(rowInfo.entryId == popJon[k].entryId){
                         $("#tPoOrderList").datagrid("selectRow",index);
                         break;
                     }
                 }
             }
         }
     });
 }
 //取消选择
  function unselect(){
      var id = selectRow.id;
      var rows = $("#tPoOrderList").datagrid("getRows");
      for(var index = 0 ; index < rows.length ; index++){
          var rowInfo = rows[index];
          for(var k = 0 ; k<popJon.length ; k++){
              if(rowInfo.id == id && rowInfo.id == popJon[k].id){
                  $("#tPoOrderList").datagrid("unselectRow",index);
                  break;
              }
          }
      }
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