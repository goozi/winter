<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'',split:true" style="width:200px;">
        <ul id="tree" class="easyui-tree" data-options="url:'tBiStockController.do?getStockTree'"></ul>
    </div>
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScIcInventoryList" checkbox="true" fitColumns="false" title="即时库存基本表" tableName="t_sc_ic_inventory"
                    actionUrl="tScIcInventoryController.do?datagrid" idField="id" fit="true" scrollview="true" isSon="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate"  formatter="yyyy-MM-dd" hidden="true"  queryMode="group"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品id" field="itemId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库id" field="stockId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="itemNo" query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品名称" field="itemName" query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="规格" field="model"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="条形码" field="barCode" query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库" field="stockName" query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="箱数" field="qty"  extendParams="formatter:function (v,r,i){var CFG_NUMBER = $('#CFG_NUMBER').val(); if(!v){v = 0}; return parseFloat(v).toFixed(CFG_NUMBER)},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="散数" field="smallQty" extendParams="formatter:function (v,r,i){var CFG_NUMBER = $('#CFG_NUMBER').val(); if(!v){v = 0}; return parseFloat(v).toFixed(CFG_NUMBER)},"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="基本数量" field="basicQty" extendParams="formatter:function (v,r,i){var CFG_NUMBER = $('#CFG_NUMBER').val(); if(!v){v = 0}; return parseFloat(v).toFixed(CFG_NUMBER)},"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="辅助数量" field="secQty"  extendParams="formatter:function (v,r,i){var CFG_NUMBER = $('#CFG_NUMBER').val(); if(!v){v = 0}; return parseFloat(v).toFixed(CFG_NUMBER)},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="存货单价" field="costPrice" extendParams="formatter:function (v,r,i){var CFG_NUMBER = $('#CFG_UNITP_RICE').val(); if(!v){v = 0}; return parseFloat(v).toFixed(CFG_NUMBER)},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="存货金额" field="costAmount" extendParams="formatter:function (v,r,i){var CFG_NUMBER = $('#CFG_MONEY').val(); if(!v){v = 0}; return parseFloat(v).toFixed(CFG_NUMBER)},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName"  queryMode="single"   width="150"></t:dgCol>
            <t:dgCol title="分支机构" field="sonId" hidden="true"   queryMode="single"   width="150"></t:dgCol>
            <t:dgCol title="单据使用量" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="是否显示零库存" field="isZero"  hidden="true" query="true" replace="否_0,是_1"  queryMode="single"   width="120"></t:dgCol>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
            <%--<t:dgDelOpt title="删除" url="tScIcInventoryController.do?doDel&id={id}"/>--%>
            <t:dgToolBar title="仓库" icon="icon-search"  funname="changeTreeInfo('stock')"></t:dgToolBar>
            <t:dgToolBar title="商品" icon="icon-search"  funname="changeTreeInfo('icitem')"></t:dgToolBar>
            <t:dgToolBar title="清理" icon="new-icon-remove"  funname="deleteInfo()"></t:dgToolBar>
            <t:dgToolBar title="批号" icon="new-icon-view"  funname="showBatchNoInfo()"></t:dgToolBar>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScIcInventoryController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScIcInventoryController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="tScIcInventoryController.do?goUpdate"--%>
                         <%--funname="detail"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('即时库存基本表', '#tScIcInventoryList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/inventory/tScIcInventoryList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScIcInventoryList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                showBatchNoInfo(rowData.itemId,rowData.stockId,rowData.itemName,rowData.model);
            }

        });
        //给时间控件加上样式
            $("#tScIcInventoryListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcInventoryListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcInventoryListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScIcInventoryController.do?upload', "tScIcInventoryList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScIcInventoryController.do?exportXls", "tScIcInventoryList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScIcInventoryController.do?exportXlsByT", "tScIcInventoryList");
    }
</script>