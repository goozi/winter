<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <input type="hidden" id="checkDate" value="${checkDate}">
        <t:datagrid name="tScDrpRstockbillViewList" beforeAccount="true" checkbox="true" fitColumns="false" title="退货申请"
                    actionUrl="tScDrpRstockbillController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" tranType="1507"
                    queryMode="single" isSon="true" tableName="t_sc_drp_rstockbill">
            <t:dgCol title="id" field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="rbillId" field="rbillId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" mergeCells="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" mergeCells="true" query="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="上游经销商或总部" field="itemId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="上游经销商" field="itemName" query="true" mergeCells="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="经办人ID" field="empId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="经办人" field="empName"   mergeCells="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptName" mergeCells="true"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="应收金额" field="allAmount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="物流费用" field="freight"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="重量" field="weight"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="分录号" field="indexNumber"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="number"  query="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="商品名称" field="goodsName"  query="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="规格" field="model"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="条形码" field="barCode"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="仓库" field="stockId"   hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="仓库" field="stockName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="批号" field="batchNo"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单位" field="unitId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单位" field="unitName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="数量" field="qty"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="基本单位" field="basicUnitId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="基本单位" field="basicUnitName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="换算率" field="coefficient"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="基本数量" field="basicQty"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="辅助单位" field="secUnitId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="辅助单位" field="secUnitName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="辅助换算率" field="secCoefficient"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="辅助数量" field="secQty"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单价" field="taxPriceEx"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="金额" field="taxAmountEx"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="折扣率(%)" field="discountRate"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="折后单价" field="taxPrice"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="折后金额" field="inTaxAmount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="重量" field="itemWeight"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="税率(%)" field="taxRate"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="不含税单价" field="price"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="不含税金额" field="rbAmount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="不含税折后单价" field="discountprice"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="不含税折后金额" field="discountamount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="税额" field="taxAmount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="生产日期" field="kfDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="保质期" field="kfPeriod"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="保质期类型" field="kfDateType"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="有效期至" field="periodDate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="源单编号" field="billNoSrc"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="订单编号" field="billNoOrder"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="摘要" field="explanation"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="联系人" field="contact" query="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilePhone"  query="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="联系电话" field="phone"  query="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="联系地址" field="address"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="制单人" field="billerName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="审核人" field="auditorName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState" dictionary="SC_AUDIT_STATUS"   queryMode="single"  width="120"></t:dgCol>
            <%--<t:dgCol title="作废标记" field="cancellation" replace="未作废_0,已作废_1"   queryMode="single"  width="120"></t:dgCol>--%>
            <t:dgCol title="分支机构" field="sonId" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName"    queryMode="single"  width="120"></t:dgCol>
           <%-- <t:dgCol title="传真" field="fax" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据金额" field="amount"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="源单类型" field="rClassidSrc"   hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="源单主键" field="rInteridSrc" hidden="true"   queryMode="single"  width="120"></t:dgCol>

            <t:dgCol title="商品" field="goodsitemid" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="源单主键" field="interidSrc"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="源单分录主键" field="idSrc"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="订单主键" field="interidOrder"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="订单分录主键" field="idOrder"    queryMode="single"  width="120"></t:dgCol>--%>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScDrpRstockbillController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScDrpRstockbillController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScDrpRstockbillController.do?goUpdate"
                         funname="update"></t:dgToolBar>
           <%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="tScDrpRstockbillController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScDrpRstockbillController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScDrpRstockbillController.do?goUpdate" funname="fcopy"></t:dgToolBar>
           <%-- <t:dgToolBar title="作废" icon="new-icon-cancellation" exp="cancellation#eq#0" funname="cancellBillInfo"></t:dgToolBar>
            <t:dgToolBar title="反作废" icon="new-icon-uncancellation" exp="cancellation#ne#0" funname="unCancellBillInfo"></t:dgToolBar>--%>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('v_sc_drp_rstockbill', '#tScDrpRstockbillViewList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpRstockbillViewList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScDrpRstockbillViewList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScDrpRstockbillController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,"tab");

            }

        });
        //给时间控件加上样式
                $("#tScDrpRstockbillViewListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='checkDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='checkDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='kfDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='kfDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='periodDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScDrpRstockbillViewListtb").find("input[name='periodDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScDrpRstockbillController.do?upload', "tScDrpRstockbillViewList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScDrpRstockbillController.do?exportXls", "tScDrpRstockbillViewList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScDrpRstockbillController.do?exportXlsByT", "tScDrpRstockbillViewList");
    }
</script>