<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScNoticeList" checkbox="true" fitColumns="false" title="公告通知管理" sortName="createDate" sortOrder="desc"
              actionUrl="tScNoticeController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="公告内容"  field="content"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="公告类型"  field="type"   query="true" queryMode="single" dictionary="noteType" width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="通知人员"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布标记"  field="issuance"   query="true" queryMode="single" dictionary="sf_01" width="120"></t:dgCol>
   <t:dgCol title="发布时间"  field="issuanceDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScNoticeController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScNoticeController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScNoticeController.do?goUpdate" funname="updateIssuance"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="new-icon-remove" url="tScNoticeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScNoticeController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="发布" icon="icon-putout"  funname="release" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScNoticeList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScNoticeList").datagrid({

         onDblClickRow:function(rowIndex,rowData){
             url ='tScNoticeController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
             createdetailwindow('查看', url, null, null,'tab');

         }

     });
 		//给时间控件加上样式
 			$("#tScNoticeListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScNoticeListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tScNoticeListtb").find("input[name='issuanceDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tScNoticeListtb").find("input[name='issuanceDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 //编辑
 function updateIssuance(title, addurl, gname, width, height){
     var rows = $('#tScNoticeList').datagrid('getSelections');
     if (rows.length == 0) {
         $.messager.show({
             title: '提示消息',
             msg: '请选择编辑项目',
             timeout: 2000,
             showType: 'slide'
         });
         return;
     }else if(rows.length > 1){
         $.messager.show({
             title: '提示消息',
             msg: '请选择要一条公告消息',
             timeout: 2000,
             showType: 'slide'
         });
         return;
     }
     var issuance = rows[0].issuance;
     if(issuance == 1){
         $.messager.show({
             title:'提示消息',
             msg:'公告消息已发布,不能进行修改',
             timeout:2000,
             showType:'slide'
         });
         return;
     }else{
         update(title, addurl, gname, width, height,"tab");
     }
 }
 </script>