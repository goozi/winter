<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScSettleacctList"  fitColumns="false" title="结算账户"
                    actionUrl="tScSettleacctController.do?datagrid" idField="id" fit="true" isSon="false" colConfig="false"
                    queryMode="group" tableName="T_SC_SettleAcct">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled"  replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="count#eq#0&&disabled#eq#0" url="tScSettleacctController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScSettleacctController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" onclick="uploadEdit()"  ></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScSettleacctController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScSettleacctController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScSettleacctController.do?goCopy"
                        onclick="copyAdd()" ></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('结算账户', '#tScSettleacctList')"></t:dgToolBar>
            <t:dgToolBar title="禁用" icon="icon-lock" onclick="changeSta(1)"></t:dgToolBar>
            <t:dgToolBar title="启用" icon="icon-active" onclick="changeSta(0)"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScSettleacctList.js"></script>
<script type="text/javascript">
    function copyAdd(){
        var rowsData = $('#tScSettleacctList').datagrid('getSelections');
        if (!rowsData || rowsData.length == 0) {
            tip('请选择要复制项');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录在复制');
            return;
        }
        update('新增','tScSettleacctController.do?goCopy','tScSettleacctList',null,null,'tab')
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScSettleacctList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScSettleacctController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,'tab');

            }

        });
        //给时间控件加上样式
            $("#tScSettleacctListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScSettleacctListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScSettleacctController.do?upload', "tScSettleacctList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScSettleacctController.do?exportXls", "tScSettleacctList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScSettleacctController.do?exportXlsByT", "tScSettleacctList");
    }
</script>