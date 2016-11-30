<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tTestUserList" checkbox="true" fitColumns="false" title="用户表"
                    actionUrl="tTestUserController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="用户名" field="username"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="年龄" field="age"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="性别" field="sex"    queryMode="single"  dictionary="GD_SEX" width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tTestUserController.do?doDel&id={id}"/>
            <t:dgToolBar title="录入" icon="icon-add" url="tTestUserController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tTestUserController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tTestUserController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tTestUserController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('用户表', '#tTestUserList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/tTestUserList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tTestUserList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tTestUserController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
            $("#tTestUserListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tTestUserListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tTestUserController.do?upload', "tTestUserList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tTestUserController.do?exportXls", "tTestUserList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tTestUserController.do?exportXlsByT", "tTestUserList");
    }
</script>