<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="mm1" class="easyui-menu" style="width:120px;">
    <div onclick="addTree('04')" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>
    <div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>
    <div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>
</div>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'商品分类',split:true" style="width:200px;">
        <ul id="tree" class="easyui-tree" data-options="url:'tScItemTypeController.do?getItemTypeTree&itemClassId=04'"></ul>
    </div>
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScIcitemList" checkbox="false" fitColumns="false" title="商品主表" checkOnSelect="true" colConfig="false"
              actionUrl="tScIcitemController.do?datagrid" idField="id" fit="true" queryMode="group" onDblClick="dbClickView">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称 "  field="name"  query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编号"  field="number"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分类ID"  field="parentID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="简称"  field="shortName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="助记码"  field="shortNumber"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="存货类型"  field="itemType"    queryMode="single" dictionary="SC_STOCK_TYPE" width="120"></t:dgCol>
   <t:dgCol title="计价方法"  field="track"    queryMode="single" dictionary="SC_PRICE_METHOD" width="120"></t:dgCol>
   <t:dgCol title="指导价"  field="costPrice"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="默认仓库"  field="stockID"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="主供应商"  field="supplierID" dictionary="T_SC_SUPPLIER,id,name"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="按保质期管理"  field="iSKFPeriod" dictionary="sf_yn"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="保质期"  field="kFPeriod"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="保质期类型"  field="kFDateType"    queryMode="single" dictionary="SC_DURA_DATE_TYPE" width="120"></t:dgCol>
   <t:dgCol title="启用批次管理"  field="batchManager" dictionary="sf_yn"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="可组装/拆卸"  field="iSAssembly" dictionary="sf_yn"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="产家"  field="factory"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="产地"  field="producingPlace"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批准文号"  field="cultureNo"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandID"    queryMode="single" dictionary="SC_BRAND_TYPE" width="120"></t:dgCol>
   <t:dgCol title="重量"  field="weight"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="进项税"  field="taxRateIn"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="销项税"  field="taxRateOut"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="级次"  field="level"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="禁用标记"  field="disabled"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgCol title="count" field="count"  width="120" hidden="true"></t:dgCol>
   <t:dgDelOpt title="删除" exp="count#eq#0" url="tScIcitemController.do?doDel&id={id}" />
   <t:dgToolBar title="新增" icon="icon-add" url="tScIcitemController.do?goAdd" funname="goAdd"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tScIcitemController.do?goUpdate" funname="update"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScIcitemController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="icon-search" url="tScIcitemController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
      <t:dgToolBar title="打印" icon="icon-print"
                   funname="CreateFormPage('商品主表', '#tScIcitemList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/baseinfo/tScIcitemList.js"></script>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScItemTypeTree.js"></script>
 <script type="text/javascript">
     function dbClickView(rowIndex, rowData) {
         url ='tScIcitemController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
         createdetailwindow('查看', url, null, null,"tab");
     }
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
//     $("#tScIcitemList").datagrid({
//
//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScIcitemController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null);
//
//         }
//
//     });
 		//给时间控件加上样式
 			$("#tScIcitemListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScIcitemListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScIcitemController.do?upload', "tScIcitemList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScIcitemController.do?exportXls","tScIcitemList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScIcitemController.do?exportXlsByT","tScIcitemList");
}
 </script>