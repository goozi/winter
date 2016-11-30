<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScDrpRstockbillList" checkbox="true" beforeAccount="true" fitColumns="false" title="退货申请" actionUrl="tScDrpRstockbillController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" queryMode="group" onDblClick="dbClickView" tableName="tableName">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据日期"  field="date"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="上游经销商或总部"  field="itemId"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="手机号码"  field="mobilePhone"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="phone"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="传真"  field="fax"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="联系地址 "  field="address"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经办人"  field="empId"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="部门 "  field="deptId"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="仓库 "  field="stockId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="物流费用"  field="freight"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据金额"  field="amount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="应收金额"  field="allAmount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="重量"  field="weight"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单类型"  field="classIdSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单主键"  field="interIdSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="checkerId"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billerId "    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkState "    queryMode="single" dictionary="SC_AUDIT_STATUS" width="120"></t:dgCol>
   <t:dgCol title="作废标记"  field="cancellation"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="explanation"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分支机构"  field="sonId"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="版本号"  field="version"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否禁用"  field="disabled"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="是否删除"  field="deleted"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScDrpRstockbillController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScDrpRstockbillController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScDrpRstockbillController.do?goUpdate" funname="update"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScDrpRstockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScDrpRstockbillController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('退货申请', '#tScDrpRstockbillList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/distribution/tScDrpRstockbillList.js"></script>		
 <script type="text/javascript">
   //双击行事件
   function dbClickView(rowIndex, rowData) {
     url = 'tScDrpRstockbillController.do?goUpdate&load=detail&tranType=52&id=' + rowData.id;
     createdetailwindow('查看', url, null, null, 'tab');
   }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tScDrpRstockbillListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScDrpRstockbillListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScDrpRstockbillController.do?upload', "tScDrpRstockbillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScDrpRstockbillController.do?exportXls","tScDrpRstockbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScDrpRstockbillController.do?exportXlsByT","tScDrpRstockbillList");
}
 </script>