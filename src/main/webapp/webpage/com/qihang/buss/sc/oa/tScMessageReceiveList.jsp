<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScMessageReceiveList" checkbox="true" fitColumns="false" title="收信箱" sortName="createDate" sortOrder="desc"
              actionUrl="tScMessageReceiveController.do?datagrid&readStatus=${readStatus}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发送人"  field="sender"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="接收时间"  field="createDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="读取状态"  field="readStatus"  query="false"  queryMode="single" dictionary="sf_01" width="120"></t:dgCol>
   <t:dgCol title="读取时间"  field="readDate"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="用户ID"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="消息内容"  field="content"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收信箱Id"  field="messageId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScMessageReceiveController.do?doDel&id={id}" />
      <t:dgToolBar title="查看" icon="new-icon-view" url="tScMessageReceiveController.do?goUpdate" funname="detailDiy"></t:dgToolBar>
      <t:dgToolBar title="批量删除"  icon="icon-remove" url="tScMessageReceiveController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
      <%--<t:dgToolBar title="录入" icon="icon-add" url="tScMessageReceiveController.do?goAdd" funname="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tScMessageReceiveController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScMessageReceiveList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     //  //添加datagrid 事件 onDblClickRow
         $("#tScMessageReceiveList").datagrid({

             onDblClickRow:function(rowIndex,rowData){
                 url ='tScMessageReceiveController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
                 createdetailwindow('查看', url, 700, 630);

                 setTimeout(function(){
                     $("#tScMessageReceiveList").datagrid("reload");
                 },2000);


             }

         });
 		//给时间控件加上样式
 			$("#tScMessageReceiveListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScMessageReceiveListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScMessageReceiveListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScMessageReceiveController.do?upload', "tScMessageReceiveList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScMessageReceiveController.do?exportXls","tScMessageReceiveList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScMessageReceiveController.do?exportXlsByT","tScMessageReceiveList");
}

     function detailDiy(title, addurl, gname, width, height){
     detail(title, addurl, gname, width, height);
         setTimeout(function(){
             $("#tScMessageReceiveList").datagrid("reload");
         },2000);

     }
 </script>