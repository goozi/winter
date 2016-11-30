<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="mm1" class="easyui-menu" style="width:120px;">
    <div onclick="addTree('07')" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>
    <div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>
    <div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>
</div>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'仓库分类',split:true" style="width:200px;">
        <ul id="tree" class="easyui-tree" data-options="url:'tScItemTypeController.do?getItemTypeTree&itemClassId=07'"></ul>
    </div>
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBiStockList"  fitColumns="false" title="仓库" isSon="false" tableName="t_sc_stock"
                    actionUrl="tBiStockController.do?datagrid" idField="id" fit="true" colConfig="false"
                    queryMode="group" onDblClick="dbClick">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="80"></t:dgCol>
            <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="简称" field="shortName"    queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber"   query="true" queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="仓管" field="empID"    queryMode="single" dictionary="t_sc_emp,id,name"  width="70"></t:dgCol>
            <t:dgCol title="仓库地址" field="address"    queryMode="single"   width="150"></t:dgCol>
            <t:dgCol title="电话" field="phone"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="仓库类型" field="typeID"    queryMode="single" dictionary="SC_REPERTORY_TYPE"   width="65"></t:dgCol>
            <%--<t:dgCol title="禁用标记" field="deleted" replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>--%>
            <t:dgCol title="禁用标记" field="disabled" replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="机构" field="sonID"   queryMode="single" hidden="true"  width="80"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <%--<t:dgFunOpt title="启用" funname="changeSta({0},id)" ></t:dgFunOpt>
            <t:dgFunOpt title="禁用" funname="changeSta({1},id)"></t:dgFunOpt>--%>
            <t:dgDelOpt title="删除" exp="count#eq#0&&disabled#eq#0" url="tBiStockController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tBiStockController.do?goAdd"
                         onclick="goAdd()"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" onclick="uploadEdit()"  ></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tBiStockController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tBiStockController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tBiStockController.do?goUpdate"
                         funname="fcopy" windowType="tab"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('仓库', '#tBiStockList')"></t:dgToolBar>
            <t:dgToolBar title="禁用" icon="icon-lock" funname="changeSta(1)"></t:dgToolBar>
            <t:dgToolBar title="启用" icon="icon-active" onclick="changeSta(0)"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tBiStockList.js"></script>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScItemTypeTree.js"></script>
<script type="text/javascript">
    function dbClick(rowIndex, rowData) {
        url = 'tBiStockController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, null, null,"tab");
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tBiStockList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tBiStockController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null,'tab');
//
//            }
//
//        });
        //给时间控件加上样式
            $("#tBiStockListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tBiStockListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBiStockController.do?upload', "tBiStockList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBiStockController.do?exportXls", "tBiStockList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBiStockController.do?exportXlsByT", "tBiStockList");
    }
</script>