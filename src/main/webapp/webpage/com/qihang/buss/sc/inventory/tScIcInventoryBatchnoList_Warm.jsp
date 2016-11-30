<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScIcInventoryBatchnoList" checkbox="true" fitColumns="false" title="商品批号明细" tableName="t_sc_ic_inventory_batchno"
                    actionUrl="tScIcInventoryController.do?datagridBatchNo&isWarm=1" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品id" field="itemId" hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库id" field="stockId" hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="itemNo" query="false"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="商品名称" field="itemName" queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="条形码" field="barCode" queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="仓库" field="stockName" queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="批号" field="batchNo"  query="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="生产日期" field="kfDate" formatter="yyyy-MM-dd"   queryMode="single"   width="100"></t:dgCol>
            <%--<t:dgCol title="保质期" field="kfPeriod"    queryMode="single"   width="100"></t:dgCol>--%>
            <t:dgCol title="保质期" field="kfDateType1"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="有效期至" field="periodDate" formatter="yyyy-MM-dd"   queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="箱数" field="qty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="散数" field="smallQty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="基本数量" field="basicQty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="辅助数量" field="secQty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="存货单价" field="costPrice"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="存货金额" field="costAmount"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="单据数量" field="count"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
            <%--<t:dgDelOpt title="删除" url="tScIcInventoryBatchnoController.do?doDel&id={id}"/>--%>
            <%--<t:dgToolBar title="录入" icon="icon-add" url="tScIcInventoryBatchnoController.do?goAdd"--%>
                         <%--funname="add"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScIcInventoryBatchnoController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScIcInventoryBatchnoController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="tScIcInventoryBatchnoController.do?goUpdate"--%>
                         <%--funname="detail"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('商品批号明细', '#tScIcInventoryBatchnoList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/inventory/tScIcInventoryBatchnoList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tScIcInventoryBatchnoList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScIcInventoryBatchnoController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null);
//
//            }
//
//        });
        //给时间控件加上样式
            $("#tScIcInventoryBatchnoListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcInventoryBatchnoListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcInventoryBatchnoListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScIcInventoryBatchnoController.do?upload', "tScIcInventoryBatchnoList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScIcInventoryBatchnoController.do?exportXls", "tScIcInventoryBatchnoList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScIcInventoryBatchnoController.do?exportXlsByT", "tScIcInventoryBatchnoList");
    }
</script>