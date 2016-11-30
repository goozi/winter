<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScBalList" checkbox="true" fitColumns="false" title="应收应付结账表"
                    actionUrl="tScBalController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="年份" field="year"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="月份" field="period"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品主键" field="itemID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库主键" field="stockID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="批号" field="batchNo"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="期初数量" field="begQty" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="期初辅助数量" field="secBegQty" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本期收入数量" field="receive" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本期发出数量" field="send" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本年累计收入数量" field="ytdReceive"  extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本年累计发出数量" field="ytdSend"  extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本年累计收入辅助数量" field="secYtdReceive"  extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本年累计发出辅助数量" field="secYtdSend" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本期结存数量" field="endQty" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本期结存辅助数量" field="secEndQty" extendParams="formatter:function(v,r,i){var CFG_QTY = $('#CFG_NUMBER').val();return parseFloat(v).toFixed(CFG_QTY);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="期初金额" field="begBal" extendParams="formatter:function(v,r,i){var CFG_AMOUNT = $('#CFG_MONEY').val();return parseFloat(v).toFixed(CFG_AMOUNT);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本期收入金额" field="debit"  extendParams="formatter:function(v,r,i){var CFG_AMOUNT = $('#CFG_MONEY').val();return parseFloat(v).toFixed(CFG_AMOUNT);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本期发出金额" field="credit"  extendParams="formatter:function(v,r,i){var CFG_AMOUNT = $('#CFG_MONEY').val();return parseFloat(v).toFixed(CFG_AMOUNT);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本年累计收入金额" field="ytdDebit"  extendParams="formatter:function(v,r,i){var CFG_AMOUNT = $('#CFG_MONEY').val();return parseFloat(v).toFixed(CFG_AMOUNT);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本年累计发出金额" field="ytdCredit"  extendParams="formatter:function(v,r,i){var CFG_AMOUNT = $('#CFG_MONEY').val();return parseFloat(v).toFixed(CFG_AMOUNT);},"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="期末结存金额" field="endBal" extendParams="formatter:function(v,r,i){var CFG_AMOUNT = $('#CFG_MONEY').val();return parseFloat(v).toFixed(CFG_AMOUNT);},"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="机构ID" field="sonID"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScBalController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="icon-add" url="tScBalController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tScBalController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tScBalController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tScBalController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('应收应付结账表', '#tScBalList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/tScBalList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScBalList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScBalController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
            $("#tScBalListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScBalListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScBalController.do?upload', "tScBalList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScBalController.do?exportXls", "tScBalList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScBalController.do?exportXlsByT", "tScBalList");
    }
</script>