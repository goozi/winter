<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScRpAdjustbillList" tranType="${tranType}" tableName="t_sc_rp_adjustbill" checkbox="true" fitColumns="false" title="${amountStr}调账" scrollview="true" rowStyler="dgRowStyler"
                    actionUrl="tScRpAdjustbillController.do?datagrid&isWarm=${isWarm}" beforeAccount="true" idField="id" fit="true" queryMode="group" onDblClick="goDetail" isSon="true">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo"  mergeCells="true" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" mergeCells="true" formatter="yyyy-MM-dd"  query="true" queryMode="group"
                     width="75"></t:dgCol>
            <t:dgCol title="${itemTitle}Id" field="itemId" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="${itemTitle}" field="itemName" mergeCells="true" query="true" queryMode="single" width="160"></t:dgCol>
            <t:dgCol title="经办人Id" field="empId" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="经办人" field="empName" mergeCells="true" query="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="部门Id" field="deptId" hidden="true"  queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptName" mergeCells="true" query="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="${amountStr}金额" field="allAmount" extendParams="formatter:function(v,r,i){return formatterMoney(v);},"  mergeCells="true" queryMode="single" width="70"></t:dgCol>

            <t:dgCol title="明细id" field="entryId" hidden="true"  queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="分录号" field="findex"  queryMode="single" width="45"></t:dgCol>
            <t:dgCol title="收支项目" field="expName"  queryMode="single" width="150"></t:dgCol>
            <t:dgCol title="金额" field="amount" extendParams="formatter:function(v,r,i){return formatterMoney(v);},"  queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="备注" field="note"  queryMode="single" width="180"></t:dgCol>

            <t:dgCol title="摘要" field="explanation" mergeCells="true" queryMode="single" width="180"></t:dgCol>
            <t:dgCol title="执行金额" field="checkAmount" mergeCells="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="制单人id" field="billerId" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="制单人" field="billerName" mergeCells="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核人id" field="checkerId" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核人" field="checkerName" mergeCells="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" mergeCells="true" formatter="yyyy-MM-dd" queryMode="single" width="128"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState" mergeCells="true" query="true" replace="未审核_0,审核中_1,已审核_2" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="作废标记" field="cancellation" mergeCells="true" query="false" queryMode="single" replace="未作废_0,作废_1" width="65"></t:dgCol>
            <t:dgCol title="分支机构id" field="sonId" hidden="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName" mergeCells="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="操作" mergeCells="true" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="cancellation#eq#0&&checkState#eq#0" url="tScRpAdjustbillController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScRpAdjustbillController.do?goAdd&tranType=${tranType}" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScRpAdjustbillController.do?goUpdate" funname="goUpdate"></t:dgToolBar><!--update-->
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScRpAdjustbillController.do?goUpdate" funname="fcopy"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScRpAdjustbillController.do?goUpdate" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="作废" icon="new-icon-cancellation" funname="cancellBillInfo"></t:dgToolBar>
            <t:dgToolBar title="反作废" icon="new-icon-uncancellation" funname="unCancellBillInfo"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScRpAdjustbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print" funname="CreateFormPage('应收调账', '#tScRpAdjustbillList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/rp/tScRpAdjustbillList.js"></script>
<script type="text/javascript">
    function goDetail(rowIndex, rowData){
        url = 'tScRpAdjustbillController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, null, null,"tab");
    }
    $(document).ready(function () {
        //添加datagrid 事件 onDblClickRow
//        $("#tScRpAdjustbillList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScRpAdjustbillController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null,"tab");
//
//            }
//
//        });
        //给时间控件加上样式
        $("#tScRpAdjustbillListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScRpAdjustbillListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScRpAdjustbillListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScRpAdjustbillListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    //编辑页面跳转
    function goUpdate(){
        var selected = $("#tScRpAdjustbillList").datagrid("getSelected");
        if(null != selected) {
            var isEdit = $("#isEdit").val();
            var checkState = selected.checkState;
            if (checkState == 1 && !isEdit) {
                tip("该单据正在审核中，不可编辑");
                return;
            }else if(checkState == 2 && !isEdit){
                tip("该单据已审核，不可编辑");
                return;
            }

            var closed = selected.closed;
            if(closed == 1){
                tip("该单据已关闭，不可编辑");
                return;
            }
            var cancellation = selected.cancellation;
            if(cancellation == 1){
                tip("该单据已作废，不可编辑");
                return;
            }
        }
        var url = "tScRpAdjustbillController.do?goUpdate";
        update("编辑", url, "tScRpAdjustbillList", null, null, "tab");
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScRpAdjustbillController.do?upload', "tScRpAdjustbillList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScRpAdjustbillController.do?exportXls&tranType=${tranType}", "tScRpAdjustbillList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScRpAdjustbillController.do?exportXlsByT", "tScRpAdjustbillList");
    }

    function formatterMoney(v){
        var m = $('#CFG_MONEY').val();
        var newV = parseFloat(v).toFixed(m);
        return newV;
    }
</script>