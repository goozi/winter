<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScIcSpeedbalList" checkbox="true" tableName="t_sc_ic_speedbal" fitColumns="false" title="存货日结表"
                    actionUrl="tScIcSpeedbalController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd" query="true"   queryMode="group"   width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" query="true" replace="采购入库_采购入库,采购退货_采购退货,采购换货_采购换货,销售出库_销售出库,销售退货_销售退货,销售换货_销售换货"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据主键" field="billId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分录主键" field="billEntryId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据创建日期" field="billCreateTime" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品id" field="itemId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="itemNo"  queryMode="single"   width="105"></t:dgCol>
            <t:dgCol title="商品名称" field="itemName" query="true"  queryMode="single"   width="180"></t:dgCol>
            <t:dgCol title="规格" field="model"  queryMode="single"   width="85"></t:dgCol>
            <t:dgCol title="条形码" field="barCode"  queryMode="single"   width="80"></t:dgCol>
            <t:dgCol title="仓库id" field="stockId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库" field="stockName"  query="true"  queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="单位" field="unitName" hidden="true"   queryMode="single"   width="50"></t:dgCol>
            <t:dgCol title="批号" field="batchNo"  query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="数量" field="qty"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="辅助数量" field="secQty"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="成本单价" field="price"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="成本金额" field="amount"    queryMode="single"   width="120"></t:dgCol>
            <%--<t:dgCol title="结余单价" field="ePrice"    queryMode="single"   width="120"></t:dgCol>--%>
            <%--<t:dgCol title="结余金额" field="eAmount"    queryMode="single"   width="120"></t:dgCol>--%>
            <%--<t:dgCol title="差异金额" field="diffAmount"    queryMode="single"   width="120"></t:dgCol>--%>
            <t:dgCol title="源单类型" field="blidSrc"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="出入库标记" field="flag"   replace="出库类单据_-1,入库类单据_1"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="计算状态" field="status" hidden="true" replace="存货计价已计算完成_0,存货计价未计算完成_1"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="负结余处理状态" field="negFlag" hidden="true" replace="未处理_0,已处理_1"   queryMode="single"   width="120"></t:dgCol>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
            <%--<t:dgDelOpt title="删除" url="tScIcSpeedbalController.do?doDel&id={id}"/>--%>
            <%--<t:dgToolBar title="录入" icon="icon-add" url="tScIcSpeedbalController.do?goAdd"--%>
                         <%--funname="add"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScIcSpeedbalController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScIcSpeedbalController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="tScIcSpeedbalController.do?goUpdate"--%>
                         <%--funname="detail"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('存货日结表', '#tScIcSpeedbalList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/tScIcSpeedbalList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScIcSpeedbalList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScIcSpeedbalController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
            $("#tScIcSpeedbalListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcSpeedbalListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcSpeedbalListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcSpeedbalListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcSpeedbalListtb").find("input[name='billCreateTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScIcSpeedbalController.do?upload', "tScIcSpeedbalList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScIcSpeedbalController.do?exportXls", "tScIcSpeedbalList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScIcSpeedbalController.do?exportXlsByT", "tScIcSpeedbalList");
    }
</script>