<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScNewsList" checkbox="false" checkOnSelect="true" fitColumns="false" title="新闻管理" sortName="createDate" sortOrder="desc"
              actionUrl="tScNewsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="类型"  field="type"  dictionary="newsType"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="查看人员"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="content"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布标记"  field="issuance"   query="true" queryMode="single" dictionary="sf_01"  width="120"></t:dgCol>
   <t:dgCol title="发布时间"  field="issuanceDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="允许评论"  field="comment"  dictionary="sf_01"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" exp="issuance#eq#0" url="tScNewsController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScNewsController.do?goAdd" funname="add" width="700" height="450"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScNewsController.do?goUpdate" funname="updateIssuance" width="700" height="450"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScNewsController.do?doBatchDel" onclick="doBatchDel();" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScNewsController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="发布" icon="icon-putout"  funname="release" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScNewsList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     //  //添加datagrid 事件 onDblClickRow
         $("#tScNewsList").datagrid({

             onDblClickRow:function(rowIndex,rowData){
                 url ='tScNewsController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
                 createdetailwindow('查看', url, 1050, 460);

             }

         });
 		//给时间控件加上样式
 			$("#tScNewsListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScNewsListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScNewsListtb").find("input[name='issuanceDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScNewsListtb").find("input[name='issuanceDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>