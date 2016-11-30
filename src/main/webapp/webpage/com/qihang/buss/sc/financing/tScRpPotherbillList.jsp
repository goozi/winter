<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
 <div region="center" style="padding:1px;">
  <t:datagrid name="tScRpPotherbillList" checkbox="true" fitColumns="false" title="费用开支单"
              actionUrl="tScRpPotherbillController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" queryMode="group"
              beforeAccount="true"
              onDblClick="dbClickView" tableName="t_sc_rp_potherbill" tranType="${tranType}">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"  mergeCells="true"></t:dgCol>

   <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="核算类别"  field="itemClassId"   query="true" queryMode="single"  width="120" replace="职员_1,部门_2"  mergeCells="true"></t:dgCol>
   <t:dgCol title="核算项目"  field="itemName"    queryMode="single"  width="120" query="true"  mergeCells="true"></t:dgCol>
   <t:dgCol title="经办人"  field="empName"    queryMode="single"  width="120" query="true"  mergeCells="true"></t:dgCol>
   <t:dgCol title="部门"  field="deptName"    queryMode="single"  width="120" query="true"  mergeCells="true"></t:dgCol>
   <t:dgCol title="结算账户"  field="accountName"    queryMode="single"  width="120" query="true"  mergeCells="true"></t:dgCol>
   <t:dgCol title="总金额"  field="allamount"    queryMode="single"  width="120"  mergeCells="true"></t:dgCol>

   <t:dgCol title="分录号"  field="findex"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收支项目"  field="feeName"    queryMode="single"  width="120" query="true" ></t:dgCol>
   <t:dgCol title="源单已报销金额"  field="haveAmountSrc"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单未报销金额"  field="notAmountSrc"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="本次报销金额"  field="amount"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单编号"  field="billNoSrc"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="源单类型"  field="billName"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="摘要"  field="explanation"    queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="制单人"  field="billUserName"    queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="审核人"  field="checkUserName"    queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="审核日期"  field="checkDate"    queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="审核状态"  field="checkstate"    queryMode="single"  width="120"  mergeCells="true" replace="未审核_0,审核中_1,已审核_2"></t:dgCol>
   <t:dgCol title="分支机构"  field="departName"    queryMode="single"  width="120"  mergeCells="true"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" mergeCells="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tScRpPotherbillController.do?doDel&id={id}" exp="checkstate#eq#0" />
   <t:dgToolBar title="新增" icon="new-icon-add" url="tScRpPotherbillController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" onclick="updateEdit()"></t:dgToolBar>
   <t:dgToolBar title="复制" icon="new-icon-copy" url="tScRpPotherbillController.do?goUpdate" funname="fcopy"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="new-icon-view" url="tScRpPotherbillController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('费用开支单', '#tScRpPotherbillList')"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/financing/tScRpPotherbillList.js"></script>
 <script type="text/javascript">
   //双击行事件
   function dbClickView(rowIndex, rowData) {
     url = 'tScRpPotherbillController.do?goUpdate&load=detail&tranType=52&id=' + rowData.id;
     createdetailwindow('查看', url, null, null, 'tab');
   }
 $(document).ready(function(){
          //添加datagrid 事件 onDblClickRow
//          $("#tScRpPotherbillList").datagrid({
//           onLoadSuccess : function(data){
//            if(data.rows.length > 0){
//             mergeCellsByField("tScRpPotherbillList", "ck,billNo,date,itemClassId,itemName,empName,deptName,accountName,allamount," +
//                     "explanation,billUserName,checkUserName,checkDate,checkstate,cancellation,departName,opt","id");
//            }
//           }
//          });
          //给时间控件加上样式
 			$("#tScRpPotherbillListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScRpPotherbillListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScRpPotherbillListtb").find("input[name='date_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScRpPotherbillListtb").find("input[name='date_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScRpPotherbillController.do?upload', "tScRpPotherbillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScRpPotherbillController.do?exportXls","tScRpPotherbillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScRpPotherbillController.do?exportXlsByT","tScRpPotherbillList");
}
 </script>