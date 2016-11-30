<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <input id="isEdit" type="hidden" value="${isEdit}">
        <input id="isCheckNegative" type="hidden" value="true">
        <input type="hidden" id="checkDate" value="${checkDate}">
        <t:datagrid name="tScPromotionViewList" isSon="true" checkbox="true" beforeAccount="true" fitColumns="false" title="买一赠一促销" tableName="t_sc_promotion" tranType="356" actionUrl="tScPromotionController.do?datagrid&isWarm=${isWarm}" idField="entryId" fit="true" queryMode="group">
            <t:dgCol title="entryId" field="entryId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="id" field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo"  query="true"  queryMode="single"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="单据日期" field="date" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="经办人ID" field="empId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="经办人"  field="empName"  query="true" queryMode="single"  width="65" mergeCells="true"></t:dgCol>
            <t:dgCol title="部门ID" field="deptId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="部门"  field="deptName" query="true"   queryMode="single"  width="65" mergeCells="true"></t:dgCol>
            <t:dgCol title="起始日期" field="begDate" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="结束日期" field="endDate" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="goodsNumber"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="商品名称" field="goodsName"  query="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="规格" field="goodsModel"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="条形码" field="goodsBarCode"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单位" field="goodsUnitName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="数量" field="goodsQty"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="备注" field="goodsNote"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="商品" field="goodsItemId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="分录号" field="indexNumber"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="赠品编号" field="giftNumber"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="赠品名称" field="giftName" query="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="规格" field="giftModel"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="条形码" field="giftBarCode"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单位" field="giftUnitName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="赠品数量" field="giftQty"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="备注" field="giftNote"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="摘要" field="explanation"    queryMode="single"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="制单人" field="billerName"    queryMode="single"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="审核人" field="auditorName"    queryMode="single"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState"  query="true" dictionary="SC_AUDIT_STATUS"  queryMode="single"  width="120" mergeCells="true"></t:dgCol>
            <%--<t:dgCol title="作废标记" field="cancellation"  replace="未作废_0,已作废_1"   queryMode="single"  width="120" mergeCells="true"></t:dgCol>--%>
            <t:dgCol title="分支机构" field="sonName"    queryMode="single"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100" mergeCells="true"></t:dgCol>

            <t:dgDelOpt title="删除" url="tScPromotionController.do?doDel&id={id}" exp="checkState#eq#0" funname="doDel"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScPromotionController.do?goAdd" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScPromotionController.do?goUpdate" funname="goUpdate"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScPromotionController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScPromotionController.do?goUpdate" funname="fcopy"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScPromotionController.do?goUpdate" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="new-icon-print" funname="CreateFormPage('买一赠一促销单',v_sc_promotion')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/distribution/tScPromotionList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScPromotionViewList").datagrid({
            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScPromotionController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,"tab");
            }
        });

        //给时间控件加上样式
        $("#tScPromotionViewListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionViewListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionViewListtb").find("input[name='checkDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionViewListtb").find("input[name='checkDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        //begDate
        $("#tScPromotionViewListtb").find("input[name='begDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionViewListtb").find("input[name='begDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        //endDate
        $("#tScPromotionViewListtb").find("input[name='endDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionViewListtb").find("input[name='endDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

        //auditDate
        $("#tScPromotionViewListtb").find("input[name='auditDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionViewListtb").find("input[name='auditDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScPromotionController.do?upload', "tScPromotionViewList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScPromotionController.do?exportXls", "tScPromotionViewList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScPromotionController.do?exportXlsByT", "tScPromotionViewList");
    }
</script>