<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScIcitemList" checkbox="true" fitColumns="false" title="商品主表" colConfig="false"
              actionUrl="tScIcitemController.do?datagridMain&disabled=0" isSon="false" onLoadSuccess="loadsuccess" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称 "  field="name"  query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编号"  field="number" query="true"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分类ID"  field="parentID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="简称"  field="shortName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="助记码"  field="shortNumber"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="规格"  field="model"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="存货类型"  field="itemType"  hidden="true"  queryMode="single" dictionary="SC_STOCK_TYPE" width="120"></t:dgCol>
   <t:dgCol title="计价方法"  field="track"  hidden="true"  queryMode="single" dictionary="SC_PRICE_METHOD" width="120"></t:dgCol>
   <t:dgCol title="指导价"  field="costPrice"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="默认仓库"  field="stockID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="主供应商"  field="supplierID" hidden="true" dictionary="T_SC_SUPPLIER,id,name"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="按保质期管理"  field="iSKFPeriod" hidden="true"  dictionary="sf_yn"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="保质期"  field="kFPeriod"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="保质期类型"  field="kFDateType"    queryMode="single" dictionary="SC_DURA_DATE_TYPE" width="120"></t:dgCol>
   <t:dgCol title="启用批次管理"  field="batchManager" dictionary="sf_yn"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="可组装/拆卸"  field="iSAssembly" dictionary="sf_yn"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="产家"  field="factory"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="产地"  field="producingPlace"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="批准文号"  field="cultureNo"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="品牌"  field="brandID"  hidden="true"  queryMode="single" dictionary="SC_BRAND_TYPE" width="120"></t:dgCol>
   <t:dgCol title="重量"  field="weight"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="进项税"  field="taxRateIn"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="销项税"  field="taxRateOut"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="级次"  field="level"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="禁用标记"  field="disabled"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="库存数量"  field="invQty"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分支机构"  field="stockSonId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <%--<script src = "webpage/com/qihang/buss/sc/baseinfo/tScIcitemList.js"></script>--%>
<%--<script src="webpage/com/qihang/buss/sc/baseinfo/tScItemTypeTree.js"></script>--%>
 <script type="text/javascript">
     Array.prototype.remove=function(dx)
     {
         if(isNaN(dx)||dx>this.length){return false;}
         for(var i=0,n=0;i<this.length;i++)
         {
             if(this[i]!=this[dx])
             {
                 this[n++]=this[i]
             }
         }
         this.length-=1
     }
     var flag = true;
     var isNew = false;
     function loadsuccess(data){
         if(flag) {
             $("input[name='name']").val('${number}_');
             $('.icon-query').click();
             $("input[name='name']").val('');
             flag = false;
         }
         debugger;
         for(var i = 0; i<data.rows.length ;i++){
             var row = data.rows[i];
             var id = row.id;
             for(var j=0 ; j<popJon.length ; j++){
                 var inf = popJon[j];
                 if(id==inf.id){
                     $("#tScIcitemList").datagrid("selectRow",i);
                     //popJon.remove(j);
                     break;
                 }
             }
             isNew = true;
         }
     }
     var popJon = new Array();
     var orgId;
     var isPop = false;
     function getSelectionData() {
         var rows = $('#tScIcitemList').datagrid('getSelections');
         if (rows.length > 0) {
             isPop =true ;
             return rows[0];

         } else {
             parent.$.messager.show({
                 title: '提示消息',
                 msg: '请选择商品',
                 timeout: 2000,
                 showType: 'slide'
             });
             return '';
         }
     }

     function getMoreSelectionData(){
         var rows = popJon;
         if (rows.length > 0) {
             isPop =true ;
             return rows;

         } else {
             parent.$.messager.show({
                 title: '提示消息',
                 msg: '请选择商品',
                 timeout: 2000,
                 showType: 'slide'
             });
             return '';
         }
     }
 var flag = true;
 $(document).ready(function(){
     //添加datagrid 事件 onDblClickRow
     $("#tScIcitemList").datagrid({

         onSelect:function(rowIndex,rowData){
             if(popJon.length) {
                 var isPush = true;
                 for (var i = 0; i < popJon.length; i++) {
                     var val = popJon[i];
                     if(val.id == rowData.id) {
                         isPush = false;
                     }
                 }
                 if(isPush) {
                     popJon.push(rowData);
                 }
             }else{
                 popJon.push(rowData);
             }
             isNew=false;
         },
         onUnselect:function(rowIndex,rowData){
             for(var i=0 ; i<popJon.length ; i++){
                 var value = popJon[i];
                 if(value.id == rowData.id){
                     popJon.remove(i);
                     break;
                 }
             }
             isNew=false;
         },
         onSelectAll:function(rows){
             for(var i=0 ; i<rows.length ; i++){
                 var row = rows[i];
                 var checkNum = 0;
                 for(var j = 0 ; j<popJon.length ; j++){
                     if(row.id != popJon[j].id){
                         checkNum++;
                     }
                 }
                 if(checkNum == popJon.length){
                     popJon.push(row);
                 }
             }
             isNew=false;
         },
         onUnselectAll:function(rows){
             if(!isNew) {
                 for (var i = 0; i < rows.length; i++) {
                     var row = rows[i];
                     for (var j = 0; j < popJon.length; j++) {
                         if (row.id == popJon[j].id) {
                             popJon.remove(j);
                             break;
                         }
                     }
                 }
             }
         }

     });
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