<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="mm1" class="easyui-menu" style="width:120px;">
    <div onclick="addTree('09')" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>
    <div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>
    <div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>
</div>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'收支项目分类',split:true" style="width:200px;">
        <ul id="tree" class="easyui-tree" data-options="url:'tScItemTypeController.do?getItemTypeTree&itemClassId=09'"></ul>
    </div>
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScFeeList"  fitColumns="false" title="收支项目" isSon="false" colConfig="false"
                    actionUrl="tScFeeController.do?datagrid" idField="id" fit="true"
                    queryMode="group" onDblClick="dbClick" tableName="T_SC_Fee">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="简称" field="shortName"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled"  replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="count#eq#0" url="tScFeeController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScFeeController.do?goAdd"
                         funname="add" onclick="goAdd()"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScFeeController.do?goUpdate"
                         funname="update" onclick="goUpdate()"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScFeeController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScFeeController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScFeeController.do?goUpdate"
                         funname="fcopy" windowType="tab"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('收支项目', '#tScFeeList')"></t:dgToolBar>
            <t:dgToolBar title="禁用" icon="icon-lock" funname="Disabled "   ></t:dgToolBar>
            <t:dgToolBar title="启用" icon="icon-active" funname="Enable "></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScFeeList.js"></script>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScItemTypeTree.js"></script>
<script type="text/javascript">
    function dbClick(rowIndex, rowData) {
        url = 'tScFeeController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, null, null,"tab");
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tScFeeList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScFeeController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, 1050, 270,'tab');
//
//            }
//
//        });
        //给时间控件加上样式
            $("#tScFeeListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScFeeListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScFeeController.do?upload', "tScFeeList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScFeeController.do?exportXls", "tScFeeList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScFeeController.do?exportXlsByT", "tScFeeList");
    }

    //禁用
    //getSelected获取你所选行，返回你选中的行的数据，
    //取它的id，用ajax请求把id传到后台，然后差出这条数据，设置它禁用，更新保存那条数据
    function Disabled(){


        var rowsData = $('#tScFeeList').datagrid('getSelections');
        if (!rowsData || rowsData.length==0) {
            tip('请选择禁用项目');
            return;
        }
        if (rowsData.length>1) {
            tip('请选择一条记录再禁用');
            return;
        }


        var  option=$("#tScFeeList").datagrid('getSelected');
        $.dialog.confirm('你确定要禁用吗?',function(r) {
            if (r) {

                $.ajax({
                    url: "tScFeeController.do?doDisable&id=" + option.id,
                    type: "post",
                    success: function (data) {

                        var result = data;
                        if (result.success == true) {
                            $("#tScFeeList").datagrid('reload');
                            $.messager.show({
                                title: '提示消息',
                                msg: '禁用成功！',
                                timeout: 2000,
                                showType: 'slide'
                            });

                        } else {
                            $.messager.show({
                                title: '提示消息',
                                msg: '禁用失败！',
                                timeout: 2000,
                                showType: 'slide'
                            });
                        }
                    }
                });
            }
            return option;
        });
    }
    //启用
    function Enable() {

        var rowsData = $('#tScFeeList').datagrid('getSelections');
        if (!rowsData || rowsData.length == 0) {
            tip('请选择启用项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再启用');
            return;
        }


        var option = $("#tScFeeList").datagrid('getSelected');
        $.dialog.confirm('你确定要启用吗?',function(r){
            if(r){

        $.ajax({
            url: "tScFeeController.do?doEnable&id=" + option.id,
            type: "post",
            success: function (data) {

                var result = data;
                if (result.success == true) {
                    $("#tScFeeList").datagrid('reload');

                    $.messager.show({
                        title: '提示消息',
                        msg: '启用成功！',
                        timeout: 2000,
                        showType: 'slide'
                    });

                } else {
                    $.messager.show({
                        title: '提示消息',
                        msg: '启用失败！',
                        timeout: 2000,
                        showType: 'slide'
                    });
                }
            }
        });
    }
        return option;
    });
    }
</script>