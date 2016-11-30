<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScRpAdjustbillList" checkbox="true" fitColumns="false" title="资金调账"
              beforeAccount="true" tranType="${tranType}" tableName="t_sc_rp_adjustbill"
              actionUrl="tScRpBankbillFinController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" queryMode="group" onDblClick="dbClickView">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single" mergeCells="true"   width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true" mergeCells="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true" mergeCells="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单据编号"  field="billNo"  query="true" queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group" mergeCells="true"   width="120"></t:dgCol>
   <t:dgCol title="经办人"  field="empName"    queryMode="single"  width="120" mergeCells="true"  query="true"></t:dgCol>
   <t:dgCol title="部门"  field="deptName"    queryMode="single"  width="120" mergeCells="true"  query="true"></t:dgCol>
   <t:dgCol title="结算账户"  field="accountName"    queryMode="single" mergeCells="true"   width="120"></t:dgCol>
   <t:dgCol title="总金额"  field="allamount"  mergeCells="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="分录号"  field="findex"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收支项目"  field="feeName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收支金额"  field="amount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="explanation"    queryMode="single" mergeCells="true"   width="120"></t:dgCol>
   <t:dgCol title="制单人"  field="billUserName"    queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="checkUserName"    queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate"    queryMode="single" mergeCells="true"   width="120"  ></t:dgCol>
   <t:dgCol title="审核状态"  field="checkstate"    queryMode="single" mergeCells="true"  width="120" replace="未审核_0,审核中_1,已审核_2"></t:dgCol>
   <t:dgCol title="分支机构"  field="departName"    queryMode="single" mergeCells="true"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" mergeCells="true" ></t:dgCol>
   <t:dgDelOpt title="删除" url="tScRpAdjustbillController.do?doDel&id={id}" exp="checkstate#eq#0" />
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScRpBankbillFinController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" onclick="updateEdit()"></t:dgToolBar>
   <t:dgToolBar title="复制" icon="new-icon-copy" url="tScRpBankbillFinController.do?goUpdate" funname="fcopy"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScRpBankbillFinController.do?goUpdate" funname="detail" windowType="tab"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="打印" icon="new-icon-print" funname="CreateFormPage('应收调账', '#tScRpAdjustbillList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/financing/tScRpAdjustbillList.js"></script>		
 <script type="text/javascript">
  $(document).ready(function(){
    //添加datagrid 事件 onDblClickRow
    $("#tScRpAdjustbillList").datagrid({
     onDblClickRow:function(rowIndex,rowData){
      url ='tScRpBankbillFinController.do?goUpdate'+ '&load=detail&tranType=${tranType}&id=' + rowData.id;
      createdetailwindow('查看', url, null, null,"tab");

     }
//     onLoadSuccess : function(data){
//      if(data.rows.length > 0){
//        mergeCellsByField("tScRpAdjustbillList", "ck,billNo,date,empName,deptName,accountName,allamount,explanation,billUserName,checkUserName,checkDate,checkstate,cancellation,departName,opt","id");
//      }
//     }
    });
     $("#tScRpAdjustbillListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
     });
      $("#tScRpAdjustbillListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
       WdatePicker({dateFmt: 'yyyy-MM-dd'});
      });
   });
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScRpAdjustbillController.do?upload', "tScRpAdjustbillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScRpBankbillFinController.do?exportXls","tScRpAdjustbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScRpAdjustbillController.do?exportXlsByT","tScRpAdjustbillList");
}
  //合并行操作
  function mergeCellsByField(tableID, colList,id) {
   var ColArray = colList.split(",");
   var tTable = $("#" + tableID);
   var TableRowCnts = tTable.datagrid("getRows").length;
   var tmpA;
   var PerTxt = "";
   var CurTxt = "";
   var PerId = "";
   var CurId = "";
   for (var j = ColArray.length - 1; j >= 0; j--) {
    PerTxt = "";
    PerId = "";
    tmpA = 1;
    for (var i = 0; i <= TableRowCnts; i++) {
     if (i == TableRowCnts) {
      CurTxt = "";
      CurId = "";
     }
     else {
      CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
      CurId = tTable.datagrid("getRows")[i][id];

     }
     if ((PerTxt == CurTxt)&&(PerId==CurId)) {
      tmpA += 1;
     }
     else {
      var index =  i - tmpA;
      tTable.datagrid("mergeCells", {
       index: index,
       field: ColArray[j],　　//合并字段
       rowspan: tmpA,
       colspan: null
      });
      tmpA = 1;
     }
     PerTxt = CurTxt;
     PerId = CurId;
    }
   }
  }
 </script>