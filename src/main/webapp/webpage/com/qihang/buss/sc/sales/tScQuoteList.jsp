<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScQuoteList" checkbox="true" tranType="101" beforeAccount="true" tableName="t_sc_quote"  fitColumns="false" title="销售报价单" actionUrl="tScQuoteController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核标记" field="status" dictionary="SC_AUDIT_STATUS" queryMode="single"   width="100"></t:dgCol>
   <t:dgCol title="审核人" field="auditorName" queryMode="single"   width="100"></t:dgCol>
   <t:dgCol title="审核日期" field="auditDate" queryMode="single"   width="100"></t:dgCol>
   <t:dgCol title="经办人"  field="empID"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门"  field="deptID"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="生效日期"  field="inureDate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="checkerID"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billerID"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="作废标记"  field="cancellation"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="explanation"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonID"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="逻辑删除"  field="deleted"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="version"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScQuoteController.do?doDel&id={id}" />
   <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0" funname="goUnAudit(id,billNo,date)"/>--%>
   <t:dgToolBar title="新增" icon="icon-add" url="tScQuoteController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tScQuoteController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tScQuoteController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tScQuoteController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
      <t:dgToolBar title="打印" icon="icon-print"
                   funname="CreateFormPage('销售报价单', '#tScQuoteList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/sales/tScQuoteList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScQuoteList").datagrid({

         onDblClickRow:function(rowIndex,rowData){
             url ='tScQuoteController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null,"tab");

         }

     });
 		//给时间控件加上样式
 			$("#tScQuoteListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScQuoteListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScQuoteListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScQuoteListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScQuoteListtb").find("input[name='inureDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScQuoteListtb").find("input[name='checkDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScQuoteController.do?upload', "tScQuoteList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScQuoteController.do?exportXls","tScQuoteList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScQuoteController.do?exportXlsByT","tScQuoteList");
}
 </script>