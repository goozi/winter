<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBiStockList" checkbox="false" fitColumns="false" title="仓库" checkOnSelect="true" onDblClick="dbButtonOk" colConfig="false"
                    actionUrl="tBiStockController.do?datagrid&disabled=0" idField="id" fit="true" onLoadSuccess="loadsuccess" isSon="false"
                    queryMode="group" >
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="简称" field="shortName"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓管" field="empID"  hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库地址" field="address"  hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电话" field="phone"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库类型" field="typeID"  hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <%--<t:dgCol title="禁用标记" field="deleted" replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>--%>
            <t:dgCol title="禁用标记" field="disabled" hidden="true" replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="机构ID" field="sonID" hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            &lt;%&ndash;<t:dgFunOpt title="启用" funname="changeSta({0},id)" ></t:dgFunOpt>
            <t:dgFunOpt title="禁用" funname="changeSta({1},id)"></t:dgFunOpt>&ndash;%&gt;
            <t:dgDelOpt title="删除" url="tBiStockController.do?doDel&id={id}"/>
            <t:dgToolBar title="录入" icon="icon-add" url="tBiStockController.do?goAdd"
                         onclick="goAdd()"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tBiStockController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tBiStockController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tBiStockController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('仓库', '#tBiStockList')"></t:dgToolBar>
            <t:dgToolBar title="禁用" icon="icon-lock" funname="changeSta(1)"></t:dgToolBar>
            <t:dgToolBar title="启用" icon="icon-active" onclick="changeSta(0)"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tBiStockList.js"></script>
<script type="text/javascript">
    function dbButtonOk(){
        $(".ui_buttons .ui_state_highlight",parent.document).trigger('click');
    }
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}_');
            $('.icon-query').click();
            $("input[name='name']").val('');
            flag = false;
        }
    }

    function chooseInfo(rowIndex,rowData){
    }
    <%--$(document).ready(function () {--%>
        <%--//  //添加datagrid 事件 onDblClickRow--%>
        <%--$("#tBiStockList").datagrid({--%>

            <%--onBeforeLoad:function(param){--%>
                <%--if(flag){--%>
                    <%--flag = false;--%>
                    <%--param.name='${name}';--%>
                    <%--param.selInfo = 1;--%>
                <%--}--%>
            <%--}--%>

        <%--});--%>
        <%--//给时间控件加上样式--%>
        <%--$("#tBiStockListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {--%>
            <%--WdatePicker({dateFmt: 'yyyy-MM-dd'});--%>
        <%--});--%>
        <%--$("#tBiStockListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {--%>
            <%--WdatePicker({dateFmt: 'yyyy-MM-dd'});--%>
        <%--});--%>
    <%--});--%>

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
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tBiStockList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择信息',
                timeout: 2000,
                showType: 'slide'
            });
            return;
        }
    }
</script>