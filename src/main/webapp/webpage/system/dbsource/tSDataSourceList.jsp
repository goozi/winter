<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tSDataSourceList" checkbox="true" fitColumns="false" title="数据源"
                    actionUrl="dynamicDataSourceController.do?datagrid" idField="id" fit="true"
                    queryMode="group" onDblClick="dbClickView">
            <t:dgCol title="id" field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="dbKey" field="dbKey"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="数据源描述" field="description"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="驱动类" field="driverClass"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="url" field="url"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="数据库用户" field="dbUser"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="数据库密码" field="dbPassword"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="数据库类型" field="dbType"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <%--<t:dgDelOpt title="删除" url="dynamicDataSourceController.do?doDel&id={id}"/>--%>
            <%--<t:dgToolBar title="录入" icon="icon-add" url="dynamicDataSourceController.do?goAdd"--%>
                         <%--funname="add"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="dynamicDataSourceController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="dynamicDataSourceControllerurceController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="icon-search" url="dynamicDataSourceController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('数据源', '#tSDataSourceList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<%--<script src="webpage/com/qihang/buss/sc/sys/tSDataSourceList.js"></script>--%>
<script src="webpage/system/dbsource/tSDataSourceList.js"></script>
<script type="text/javascript">
    function dbClickView(rowIndex, rowData) {
        url = 'dynamicDataSourceController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, 1060, 700,"tab");
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        //给时间控件加上样式
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'dynamicDataSourceController.do?upload', "tSDataSourceList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("dynamicDataSourceController.do?exportXls", "tSDataSourceList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("dynamicDataSourceController.do?exportXlsByT", "tSDataSourceList");
    }
</script>