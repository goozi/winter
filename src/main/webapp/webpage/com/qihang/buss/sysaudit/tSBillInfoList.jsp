<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <table width="100%" id="T_S_Bill_InfoList" toolbar="#T_S_Bill_InfoListtb"></table>
        <div id="T_S_Bill_InfoListtb" style="padding:3px; height: auto">
            <div name="searchColums"></div>
            <div style="height:30px;" class="datagrid-toolbar">
                <span style="float:left;">

                    <a id="add" href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="T_S_Bill_Infoadd()">新增</a>
                    <a id="update" href="#" class="easyui-linkbutton" plain="true" icon="icon-edit"
                       onclick="T_S_Bill_Infoupdate()">编辑</a>
                    <%--<a id="batchDelete" href="#" class="easyui-linkbutton" plain="true" icon="icon-remove" onclick="T_S_Bill_InfodelBatch()">批量删除</a>--%>
                    <a id="detail" href="#" class="easyui-linkbutton" plain="true" icon="icon-search"
                       onclick="T_S_Bill_Infoview()">查看</a>
                    <a id="import" href="#" class="easyui-linkbutton" plain="true" icon="icon-put"
                       onclick="ImportXls()">导入</a>
                    <a id="excel" href="#" class="easyui-linkbutton" plain="true" onclick="ExportXls()"
                       icon="icon-putout">导出</a>
                    <%--<a id="print" href="#" class="easyui-linkbutton" plain="true" onclick="CreateFormPage('','#T_S_Bill_InfoList')"--%>
                        <%--icon="icon-print">打印</a>--%>
	            </span>
            </div>
        </div>
        <%--<t:datagrid name="tSBillInfoList" checkbox="false" fitColumns="false" title="单据类型设置"
                    actionUrl="tSBillInfoController.do?datagrid" idField="id" fit="true" treegrid="true" pagination="false"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true" treefield="id" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据名称" field="billName" treefield="text"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="billId"  treefield="billId"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="父id" field="pid"  hidden="true" treefield="pid"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="前缀" field="prefix"    queryMode="single"  treefield="prefix"  width="120"></t:dgCol>
            <t:dgCol title="日期格式" field="dateFormatter"    queryMode="single" treefield="dateFormatter"  dictionary="SC_DF" width="120"></t:dgCol>
            <t:dgCol title="流水号长度" field="billNoLen"    queryMode="single" treefield="billNoLen"   width="120"></t:dgCol>
            <t:dgCol title="允许手工编辑单据号" field="isEdit"  dictionary="sf_01" treefield="isEdit"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="允许断号" field="isOffOn"    queryMode="single"  dictionary="sf_01" treefield="isOffOn"  width="120"></t:dgCol>
            <t:dgCol title="流水号归零" field="backZero"    queryMode="single" dictionary="SC_BackZero" treefield="backZero"  width="120"></t:dgCol>
            <t:dgCol title="单据数量" field="billNo"  hidden="true"  queryMode="single" treefield="billNo"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tSBillInfoController.do?doDel&id={id}"/>
            <t:dgToolBar title="录入" icon="icon-add" url="tSBillInfoController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tSBillInfoController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tSBillInfoController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tSBillInfoController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('单据类型设置', '#tSBillInfoList')"></t:dgToolBar>
        </t:datagrid> --%>
    </div>
</div>
<script src="webpage/com/qihang/buss/sysaudit/tSBillInfoList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tSBillInfoList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tSBillInfoController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null);
//
//            }
//
//        });
        //给时间控件加上样式
            $("#tSBillInfoListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tSBillInfoListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tSBillInfoController.do?upload', "tSBillInfoList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tSBillInfoController.do?exportXls", "tSBillInfoList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tSBillInfoController.do?exportXlsByT", "tSBillInfoList");
    }
</script>