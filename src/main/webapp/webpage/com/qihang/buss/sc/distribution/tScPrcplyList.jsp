<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <input id="isEdit" type="hidden" value="${isEdit}">
      <input id="isCheckNegative" type="hidden" value="true">
      <input type="hidden" id="checkDate" value="${checkDate}">
  <t:datagrid name="tScPrcplyList" checkbox="true" fitColumns="false" checkOnSelect="true" beforeAccount="true" title="调价政策单" actionUrl="tScPrcplyController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true"  queryMode="group"
              tranType="353" isSon="true" tableName="t_sc_prcply" entityName="TScPrcplyViewEntityEntity">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol><t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单据类型"  field="tranType"  hidden="true" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single" mergeCells="true" width="170"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  query="true" mergeCells="true" queryMode="group"  width="120" ></t:dgCol>
      <t:dgCol title="经办人"  field="empName"  query="true" queryMode="single" mergeCells="true" width="65"></t:dgCol>
   <t:dgCol title="经办人ID"  field="empID" hidden="true" dictionary="t_sc_emp,id,name"  queryMode="single"  width="160" ></t:dgCol>
      <t:dgCol title="部门"  field="deptName" query="true" mergeCells="true" queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="分录号"  field="indexNumber"  mergeCells="true"  queryMode="single"  width="45"></t:dgCol>
      <t:dgCol title="商品编号"  field="number"   queryMode="single"  width="105"></t:dgCol>
      <t:dgCol title="商品名称"  field="name" query="true"  queryMode="single"  width="180"></t:dgCol>
      <t:dgCol title="规格"  field="model"    queryMode="single"  width="85"></t:dgCol>
      <t:dgCol title="条形码"  field="barCode"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="单位"  field="unitName"    queryMode="single"  width="50"></t:dgCol>
      <t:dgCol title="数量（从）"  field="begQty"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="数量（至）"  field="endQty"    queryMode="single"  width="65"></t:dgCol>
      <t:dgCol title="原价"  field="price"    queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="新价"  field="newPrice"    queryMode="single"  width="71"></t:dgCol>
      <t:dgCol title="差价"  field="differPrice"    queryMode="single"  width="72"></t:dgCol>
      <t:dgCol title="折扣率"  field="disCountRate"    queryMode="single"  width="73"></t:dgCol>
      <t:dgCol title="起始时间"  field="begDate" query="true"  formatter="yyyy-MM-dd"  queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="结束时间"  field="endDate" query="true" formatter="yyyy-MM-dd"  queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="成本单价"  field="costPrice"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="原毛利率"  field="grossper"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="新毛利率"  field="newGrossper"   queryMode="single"  width="70"></t:dgCol>
      <t:dgCol title="备注"  field="note"  queryMode="single"  width="150"></t:dgCol>


   <t:dgCol title="摘要"  field="explanation" queryMode="single" mergeCells="true" width="180" ></t:dgCol>
   <t:dgCol title="制单人"  field="billerName"  queryMode="single" mergeCells="true" width="65" ></t:dgCol>
   <t:dgCol title="审核人"  field="auditorName"   queryMode="single" mergeCells="true" width="65" ></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate" mergeCells="true" query="true" formatter="yyyy-MM-dd" queryMode="group"  width="128"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState" dictionary="SC_AUDIT_STATUS" queryMode="single" mergeCells="true" width="65" ></t:dgCol>
   <%--<t:dgCol title="作废标记"  field="cancellation" replace="未作废_0,已作废_1"  queryMode="single"  width="65" mergeCells="true"></t:dgCol><t:dgCol title="分支机构" field="sonName"    queryMode="single"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="分支机构"  field="sonID" hidden="true" dictionary="t_s_depart,id,departname"  queryMode="single"  width="130"></t:dgCol>
   <t:dgCol title="是否禁用"  field="disabled"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否删除"  field="deleted"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="version"  hidden="true"  queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="操作" field="opt" width="100"  mergeCells="true"></t:dgCol>

   <t:dgDelOpt title="删除" url="tScPrcplyController.do?doDel&id={id}" exp="checkState#eq#0"/>
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScPrcplyController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScPrcplyController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScPrcplyController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="复制" icon="new-icon-copy" url="tScPrcplyController.do?goUpdate" funname="fcopy"></t:dgToolBar>
  <%-- <t:dgToolBar title="作废" icon="new-icon-cancellation" exp="cancellation#eq#0" funname="cancellBillInfo"></t:dgToolBar>
   <t:dgToolBar title="反作废" icon="new-icon-uncancellation" exp="cancellation#ne#0" funname="unCancellBillInfo"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScPrcplyController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('促销管理', '#tScPrcplyList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/distribution/tScPrcplyList.js"></script>		
 <script type="text/javascript">
     //添加datagrid 事件 onDblClickRow
     /*function dbClickView(rowIndex,rowData){
         url ='tScPrcplyController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
         createdetailwindow('查看', url, null, null,"tab");
     }*/
     /*function onLoadSuccess(data){
         if(data.rows.length > 0){
             mergeCellsByField("tScPrcplyList", "ck,tranType,billNo,date,empName,empID,deptID,deptName,explanation,billerID,billerName,auditorName,auditDate,,checkState,cancellation,sonID,opt","id");
         }
     }*/
 $(document).ready(function(){
     $("#tScPrcplyList").datagrid({
         onDblClickRow: function (rowIndex, rowData) {
             url ='tScPrcplyController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null,"tab");

         }
     });
    //给时间控件加上样式
    $("#tScPrcplyListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='checkDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='checkDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    //begDate
    $("#tScPrcplyListtb").find("input[name='begDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='begDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    //endDate
    $("#tScPrcplyListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tScPrcplyListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScPrcplyController.do?upload', "tScPrcplyList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScPrcplyController.do?exportXls&tranType=353","tScPrcplyList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScPrcplyController.do?exportXlsByT","tScPrcplyList");
}
 </script>