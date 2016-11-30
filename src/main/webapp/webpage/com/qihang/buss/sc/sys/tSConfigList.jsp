<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tSConfigList" checkbox="true" fitColumns="false" title="业务设参数置"
                    actionUrl="tSConfigController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="id" field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="code" field="code"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="content" field="content"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="name" field="name"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="note" field="note"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="userid" field="userid"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tSConfigController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="icon-add" url="tSConfigController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tSConfigController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tSConfigController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tSConfigController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('t_s_config', '#tSConfigList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/tSConfigList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tSConfigList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tSConfigController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tSConfigController.do?upload', "tSConfigList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tSConfigController.do?exportXls", "tSConfigList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tSConfigController.do?exportXlsByT", "tSConfigList");
    }
</script>