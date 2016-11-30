<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSAuditList" checkbox="true" fitColumns="false" title="多级审核" actionUrl="tSAuditController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="菜单id"  field="funId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="进行多级审核控制"  field="isAudit"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="级次"  field="leaveNum"    queryMode="single" dictionary="SCM_A_L" width="120"></t:dgCol>
   <t:dgCol title="审核时必须逐级审核"  field="isSort"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="使用工作流消息"  field="isSendMessage"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="启用审核拒绝功能"  field="isUnaudit"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="按金额确定多级审核"  field="isMoney"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="最低限额"  field="minMoney"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="按金额确定审核级次"  field="isMoneyLeave"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSAuditController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="tSAuditController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSAuditController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSAuditController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSAuditController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
      <t:dgToolBar title="打印" icon="icon-print"
                   funname="CreateFormPage('多级审核', '#tSAuditList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sysaudit/tSAuditList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tSAuditList").datagrid({

         onDblClickRow:function(rowIndex,rowData){
             url ='tSAuditController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null);

         }

     });
 		//给时间控件加上样式
 			$("#tSAuditListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tSAuditListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tSAuditController.do?upload', "tSAuditList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tSAuditController.do?exportXls","tSAuditList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tSAuditController.do?exportXlsByT","tSAuditList");
}
 </script>