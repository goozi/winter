<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="vScBillTempList" checkbox="true" fitColumns="false" title="单据草稿"
                    actionUrl="vScBillTempController.do?datagrid&tranType=${tranType}" idField="id" fit="true"
                    queryMode="group" tableName="v_sc_bill_temp" onDblClick="dbClickView">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType"  hidden="true"  query="false" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billno"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="仓库ID" field="stockid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="仓库" field="stockname"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="部门ID" field="deptid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="部门" field="departname"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="供货商客户ID" field="itemid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="供货商/客户" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="经办人ID" field="empid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="经办人" field="empname"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="数据JSON串" field="content"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScBillTempController.do?doDel&id={id}"/>
            <%--<t:dgToolBar title="录入" icon="icon-add" url="vScBillTempController.do?goAdd"--%>
                         <%--funname="add"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="vScBillTempController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="vScBillTempController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="vScBillTempController.do?goUpdate"--%>
                         <%--funname="detail"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="打印" icon="icon-print"--%>
                         <%--funname="CreateFormPage('单据草稿', '#vScBillTempList')"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/vScBillTempList.js"></script>
<script type="text/javascript">
    function dbClickView(rowIndex, rowData) {
        url = 'vScBillTempController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, 1060, 700,"tab");
    }
    $(document).ready(function () {
        //给单据类型赋默认值，并只读
        //$("[name='tranType']").val("${tranType}");
        //$("[name='tranType']").attr("readonly","readonly");
        //  //添加datagrid 事件 onDblClickRow
        //给时间控件加上样式
                $("#vScBillTempListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScBillTempListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScBillTempListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScBillTempListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScBillTempListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScBillTempListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'vScBillTempController.do?upload', "vScBillTempList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("vScBillTempController.do?exportXls", "vScBillTempList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("vScBillTempController.do?exportXlsByT", "vScBillTempList");
    }

    var popJon;
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#vScBillTempList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择单据草稿',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
</script>