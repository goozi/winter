<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScAccountStageList" checkbox="true" fitColumns="false" title="账套期别" actionUrl="tScAccountStageController.do?datagrid" idField="id" fit="true" queryMode="group" onDblClick="dbClickView" tableName="tableName">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="期别"  field="date" formatter="yyyy-MM-dd"  query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="state"  hidden="false"  replace="未结账_0,已结账_1" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="账套ID"  field="accountId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%--<t:dgDelOpt title="删除" url="tScAccountStageController.do?doDel&id={id}" />--%>
   <%--<t:dgToolBar title="新增" icon="icon-add" url="tScAccountStageController.do?goAdd" funname="add"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="结账" icon="icon-edit" url="tScAccountStageController.do?goUpdate" funname="update"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScAccountStageController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="icon-search" url="tScAccountStageController.do?goUpdate" funname="detail"></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="icon-print"
                   funname="CreateFormPage('账套期别', '#tScAccountStageList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/sys/tScAccountStageList.js"></script>		
 <script type="text/javascript">
   //双击行事件
   function dbClickView(rowIndex, rowData) {
     url = 'tScAccountStageController.do?goUpdate&load=detail&tranType=52&id=' + rowData.id;
     createdetailwindow('查看', url, null, null, 'tab');
   }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tScAccountStageListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScAccountStageListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScAccountStageListtb").find("input[name='date']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScAccountStageController.do?upload', "tScAccountStageList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScAccountStageController.do?exportXls","tScAccountStageList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScAccountStageController.do?exportXlsByT","tScAccountStageList");
}
 </script>