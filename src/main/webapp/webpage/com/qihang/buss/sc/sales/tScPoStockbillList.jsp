<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <input id="checkDate" type="hidden" value="${checkDate}">
        <input id="isEdit" type="hidden" value="${isEdit}">
        <input id="isCheckNegative" type="hidden" value="${isCheckNegative}">
        <t:datagrid name="tScPoStockbillList" checkbox="true" pageSize="${pageNum}" tableName="t_sc_po_stockbill"
                    beforeAccount="true" isSon="true" entityName="TScPoStockBillViewEntity" rowStyler="dgRowStyler"
                    tranType="52" fitColumns="false" title="采购入库单" actionUrl="tScPoStockbillController.do?datagrid&isWarm=${isWarm}"
                    scrollview="true"
                    onDblClick="dbClickView"
                    idField="entryId" fit="true" queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" query="true" mergeCells="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd" mergeCells="true" query="true" queryMode="group"
                     width="75"></t:dgCol>
            <t:dgCol title="供应商id" field="itemId" hidden="true" mergeCells="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="供应商" field="itemName" autocomplete="true" query="true" mergeCells="true" queryMode="single"
                     width="160"></t:dgCol>
            <t:dgCol title="经办人id" field="empId" hidden="true" mergeCells="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="经办人" field="empName" autocomplete="true" query="true" mergeCells="true" queryMode="single"
                     width="65"></t:dgCol>
            <t:dgCol title="部门id" field="deptId" hidden="true" mergeCells="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptName" autocomplete="true" query="true" mergeCells="true" queryMode="single"
                     width="65"></t:dgCol>
            <%--<t:dgCol title="仓库" field="stockId" hidden="true" mergeCells="true" queryMode="single"--%>
                     <%--width="120"></t:dgCol>--%>
            <%--<t:dgCol title="仓库" field="stockName" hidden="true" mergeCells="true" queryMode="single"--%>
                     <%--width="120"></t:dgCol>--%>
            <t:dgCol title="应付金额" field="allAmount" mergeCells="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="优惠金额" field="rebateAmount" mergeCells="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="本次付款" field="curPayAmount" mergeCells="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="结算账户" field="accountId" mergeCells="true" dictionary="t_sc_settleacct,id,name"
                     queryMode="single" width="65"></t:dgCol>

            <t:dgCol title="明细id" field="entryId" hidden="true" queryMode="single" width="50"></t:dgCol>
            <t:dgCol title="分录号" field="index" queryMode="single" width="45"></t:dgCol>
            <t:dgCol title="商品编号" field="entryItemNo" queryMode="single" width="105"></t:dgCol>
            <t:dgCol title="商品名称" field="entryItemName" autocomplete="true" query="true" queryMode="single"
                     width="180"></t:dgCol>
            <t:dgCol title="规格" field="model" queryMode="single" width="85"></t:dgCol>
            <t:dgCol title="条码" field="barCode" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="仓库" field="entryStockName" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="批号" field="batchNo" queryMode="single" width="95"></t:dgCol>
            <t:dgCol title="单位" field="unitName" queryMode="single" width="50"></t:dgCol>
            <t:dgCol title="数量" field="qty" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="基本单位" field="basicUnitName" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="换算率" field="coefficient" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="基本数量" field="basicQty" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="辅助单位" hidden="true" field="secUnitName" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="辅助换算率" hidden="true" field="secCoefficient" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="辅助数量" hidden="true" field="secQty" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="单价" field="taxPriceEx" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="金额" field="taxAmountEx" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="折扣率（%）" field="discountRate" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="折后单价" field="taxPrice" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="折后金额" field="inTaxAmount" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="税率（%）" field="taxRate" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="不含税单价" field="entryPrice" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="不含税金额" field="entryAmount" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="不含税折扣单价" field="discountPrice" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="不含税折扣金额" field="discountAmount" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="税额" field="taxAmount" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="生产日期" field="kfDate" formatter="yyyy-MM-dd" queryMode="single" width="80"></t:dgCol>
            <%--<t:dgCol title="保质期"  field="kfPeriod"  queryMode="single"  width="80"></t:dgCol>--%>
            <t:dgCol title="保质期" field="kfDateType" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="有效期至" field="periodDate" formatter="yyyy-MM-dd" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="是否赠品" field="freeGifts" query="true" dictionary="sf_01" queryMode="single"
                     width="65"></t:dgCol>
            <t:dgCol title="退货数量" field="commitQty" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="源单类型" field="entryClassIdSrc" dictionary="t_s_bill_info,bill_id,bill_name"
                     queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="源单编号" field="entryBillNoSrc" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="订单编号" field="billNoOrder" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="备注" field="note" queryMode="single" width="180"></t:dgCol>

            <t:dgCol title="摘要" field="explanation" mergeCells="true" queryMode="single" width="180"></t:dgCol>
            <t:dgCol title="联系人" field="contact" mergeCells="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="手机" field="mobilePhone" mergeCells="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="电话" field="phone" mergeCells="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="传真" field="fax" mergeCells="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="联系地址" field="address" mergeCells="true" queryMode="single" width="150"></t:dgCol>
            <t:dgCol title="单据金额" field="amount" mergeCells="true" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="付款金额" field="checkAmount" mergeCells="true" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="源单类型" field="classIdName" mergeCells="true" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="源单主键" field="idSrc" mergeCells="true" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="源单编号" field="billNoSrc" mergeCells="true" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="制单人" field="billerID" mergeCells="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核人id" field="checkerId" hidden="true" mergeCells="true" queryMode="single"
                     width="65"></t:dgCol>
            <t:dgCol title="审核人" field="checkerName" mergeCells="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" mergeCells="true" formatter="yyyy-MM-dd" queryMode="single"
                     width="128"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState" mergeCells="true" query="true" replace="未审核_0,审核中_1,已审核_2"
                     queryMode="single"
                     width="65"></t:dgCol>
            <t:dgCol title="作废标记" field="cancellation" mergeCells="true" query="true" replace="未作废_0,已作废_1"
                     queryMode="single"
                     width="65"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName" mergeCells="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100" mergeCells="true"></t:dgCol>
            <t:dgFunOpt title="删除" exp="checkState#eq#0&&cancellation#eq#0" funname="doDel(id,billNo,date)"/>
            <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0&&cancellation#eq#0" funname="goUnAudit(id,billNo,date,cancellation,{0},{0})"/>--%>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScPoStockbillController.do?goAdd&tranType=52"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScPoStockbillController.do?goUpdate"
                         funname="goUpdate"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScPoStockbillController.do?goUpdate"
                         funname="fcopy"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScPoStockbillController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="作废" icon="new-icon-cancellation" funname="cancellBillInfo"></t:dgToolBar>
            <t:dgToolBar title="反作废" icon="new-icon-uncancellation" funname="unCancellBillInfo"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScPoStockbillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>

            <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('采购入库单', '#tScPoStockbillList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sales/tScPoStockbillList.js"></script>
<script type="text/javascript">
    function dbClickView(rowIndex, rowData) {
        url = 'tScPoStockbillController.do?goUpdate' + '&load=detail&tranType=52&id=' + rowData.id;
        createdetailwindow('查看', url, null, null, 'tab');
    }
    //  function loadSuccess(data) {
    //    if (data.rows.length > 0) {
    //      mergeCellsByField("tScPoStockbillList", "ck,trantype,date,billNo,checkState,auditorName,auditDate,itemName,contact,mobilePhone,phone,fax,address,empName,deptName,stockName,rebateAmount,allAmount,curPayAmount,accountId,billerID,cancellation,explanation,sonId,opt", "id");
    //    }
    //  }
    $(document).ready(function () {
        //给时间控件加上样式
        $("#tScPoStockbillListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPoStockbillListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPoStockbillListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPoStockbillListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScPoStockbillController.do?upload', "tScPoStockbillList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScPoStockbillController.do?exportXls&tranType=52", "tScPoStockbillList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScPoStockbillController.do?exportXlsByT", "tScPoStockbillList");
    }

    //  //合并行操作
    //  function mergeCellsByField(tableID, colList, id) {
    //    var ColArray = colList.split(",");
    //    var tTable = $("#" + tableID);
    //    var TableRowCnts = tTable.datagrid("getRows").length;
    //    var tmpA;
    //    var PerTxt = "";
    //    var CurTxt = "";
    //    var PerId = "";
    //    var CurId = "";
    //    for (var j = ColArray.length - 1; j >= 0; j--) {
    //      PerTxt = "";
    //      PerId = "";
    //      tmpA = 1;
    //      for (var i = 0; i <= TableRowCnts; i++) {
    //        if (i == TableRowCnts) {
    //          CurTxt = "";
    //          CurId = "";
    //        }
    //        else {
    //          CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
    //          CurId = tTable.datagrid("getRows")[i][id];
    //
    //        }
    //        if ((PerTxt == CurTxt) && (PerId == CurId)) {
    //          tmpA += 1;
    //        }
    //        else {
    //          var index = i - tmpA;
    //          tTable.datagrid("mergeCells", {
    //            index: index,
    //            field: ColArray[j],　　//合并字段
    //            rowspan: tmpA,
    //            colspan: null
    //          });
    //          tmpA = 1;
    //        }
    //        PerTxt = CurTxt;
    //        PerId = CurId;
    //      }
    //    }
    //  }
    //编辑页面跳转
    function goUpdate() {
        var selected = $("#tScPoStockbillList").datagrid("getSelected");
        if (null != selected) {
            var isEdit = $("#isEdit").val();
            var checkState = selected.checkState;
            var date = new Date(selected.date).getTime();
            var checkDate = new Date('${checkDate}').getTime();
            if (date < checkDate) {
                tip("该单据未在本期间内，不可编辑");
                return;
            }

            if (checkState == 1 && !isEdit) {
                tip("该单据正在审核中，不可编辑");
                return;
            }
            if (checkState == 2 && !isEdit) {
                tip("该单据已审核，不可编辑");
                return;
            }
//         var closed = selected.closed;
//         if(closed == 1){
//             tip("该单据已关闭，不可编辑");
//             return;
//         }
            var cancellation = selected.cancellation;
            if (cancellation == 1) {
                tip("该单据已作废，不可编辑");
                return;
            }
        }
        var url = "tScPoStockbillController.do?goUpdate";
        update("编辑", url, "tScPoStockbillList", null, null, "tab");
    }
    //反审核前校验
    function checkUnAudit(billId) {
        var isCheckNegative = $("#isCheckNegative").val();
        if (isCheckNegative == "true") {
            var tranType = $("#tranType").val();
            var isContinue = true;
            var itemName = "";
            /**
             *参数说明
             * id 单据id
             * tranTyoe 单据类型
             * tableName 明细表名
             * parentId 关联id
             */
            $.ajax({
                async: false,
                url: "tSAuditController.do?checkIsNegative&id=" + billId + "&tranType=" + tranType + "&tableName=T_SC_Po_StockBillEntry&parentId=fid",
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (!data.success) {
                        isContinue = false;
                        itemName = data.msg;
                    }
                }
            })
            if (isContinue) {
                return false;
            } else {
                itemName = itemName.substring(0, itemName.length - 1);
                return "该单据反审核后,商品【" + itemName + "】将出现负库存，不可反审核，请确认";
            }
        } else {
            return false;
        }
    }
</script>