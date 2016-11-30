<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScContactList" checkbox="true" fitColumns="false" title="通讯录" sortOrder="desc" sortName="createDate"
              actionUrl="tScContactController.do?datagrid&type=${type}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分类"  field="type"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="用户ID"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="name"   query="true" queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="性别"  field="sex"    queryMode="single" dictionary="oa_sex" width="120"></t:dgCol>
   <t:dgCol title="生日"  field="birthday" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="职位"  field="position"    queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="单位"  field="unit"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单位地址"  field="unitAddress"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="单位邮编"  field="unitCode"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单位电话"  field="unitPhone"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单位传真"  field="unitFax"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="家庭住址"  field="address"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="联系电话"  field="phone"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="QQ"  field="qq"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScContactController.do?doDel&id={id}" />
   <t:dgToolBar windowType="dialog" title="新增" icon="new-icon-add" url="tScContactController.do?goAdd&type=${type}" funname="add"></t:dgToolBar>
   <t:dgToolBar windowType="dialog" title="编辑" icon="new-icon-edit" url="tScContactController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tScContactController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar windowType="dialog" title="查看" icon="new-icon-view" url="tScContactController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScContactList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     //  //添加datagrid 事件 onDblClickRow
         $("#tScContactList").datagrid({

             onDblClickRow:function(rowIndex,rowData){
                 url ='tScContactController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
                 createdetailwindow('查看', url, null, null);

             }

         });
 		//给时间控件加上样式
 			$("#tScContactListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScContactListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScContactListtb").find("input[name='birthday']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScContactController.do?upload', "tScContactList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScContactController.do?exportXls&type=${type}","tScContactList");
}

//模板下载
function ExportXlsByT() {
	//JeecgExcelExport("tScContactController.do?exportXlsByT","tScContactList");
  window.location.href="export/template/oa/${type eq 1?"个人":"公共"}通讯簿.xls";
}
 </script>