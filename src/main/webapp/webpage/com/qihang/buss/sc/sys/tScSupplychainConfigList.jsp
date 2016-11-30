<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScSupplychainConfigList" checkbox="true" fitColumns="false" title="供应链设置"
                    actionUrl="tScSupplychainConfigController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="允许负库存结账" field="minusInventoryAccount"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="允许负库存出库" field="minusInventorySl"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="允许盘点有未审核存货单据的数据" field="stocktakingNotAuditedStockBill"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据保存时系统自动审核" field="billSaveSystemWithExamine"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据审核时系统自带后续业务单据" field="billExamineSystemWithFollow"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="不允许手工开入库单" field="cannotManualOpenInRepertory"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="不允许入库单数量大于采购订单数量" field="cannotInRepertoryngtPurchasen"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="采购模块启用价格调用顺序" field="purchaseStartPriceCallOrder"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="采购设置下拉框一" field="purchaseselectOne"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="采购设置下拉框二" field="purchaseselectTwo"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="不允许手工开出库单" field="cannotManualOpenOutRepertory"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="不允许出库单数量大于销售订单数量" field="cannotOutRepertoryngtSale"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售模块启用价格调用顺序" field="saleStartPriceCallOrder"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售设置下拉框一" field="saleSelectOne"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售设置下拉框二" field="saleSelectTwo"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售设置下拉框三" field="saleSelectOneThree"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScSupplychainConfigController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="icon-add" url="tScSupplychainConfigController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tScSupplychainConfigController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tScSupplychainConfigController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tScSupplychainConfigController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('供应链设置', '#tScSupplychainConfigList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/tScSupplychainConfigList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScSupplychainConfigList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScSupplychainConfigController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
            $("#tScSupplychainConfigListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScSupplychainConfigListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScSupplychainConfigController.do?upload', "tScSupplychainConfigList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScSupplychainConfigController.do?exportXls", "tScSupplychainConfigList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScSupplychainConfigController.do?exportXlsByT", "tScSupplychainConfigList");
    }
</script>