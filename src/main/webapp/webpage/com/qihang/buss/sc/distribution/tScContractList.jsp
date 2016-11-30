<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScContractList" beforeAccount="true" isSon="true" checkbox="true" fitColumns="false" title="合同管理"
                    actionUrl="tScContractController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="trantype" hidden="true"    queryMode="single"  dictionary="SC_TRAN_TYPE" width="120"></t:dgCol>
            <t:dgCol title="合同日期" field="date"  formatter="yyyy-MM-dd"   queryMode="group"   width="120"></t:dgCol>
            <t:dgCol title="合同号" field="billNo"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="经销商" field="tScOrganizationEntity_name"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="合同名" field="contractName"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="合同内容" field="content"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonID"  dictionary="t_s_depart,id,departname"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScContractController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScContractController.do?goAdd" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScContractController.do?goUpdate" funname="update"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScContractController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScContractController.do?goUpdate" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScContractController.do?goUpdate" funname="fcopy"></t:dgToolBar>
            <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print" funname="CreateFormPage('合同管理', '#tScContractList')"></t:dgToolBar>
        </t:datagrid>
        <div id="tScContractListtb" style="padding: 8px; height: 40px">
            <div align="left">
                合同日期:
                <input id="date_begin" name="date_begin" type="text" style="width: 150px" class="Wdate"
                       onClick="WdatePicker({maxDate:'#F{$dp.$D(\'date_end\')}'})" datatype="*"/>
                ~
                <input id="date_end" name="date_end" type="text" style="width: 150px" class="Wdate"
                       onClick="WdatePicker({minDate:'#F{$dp.$D(\'date_begin\')}'})" datatype="*"/>
                合同号:
                <input type="text" name="billNo" id="billNo" class="inputxt" style="width: 196px;height: 20px;">
                合同名:
                <input type="text" name="contractName" id="contractName" class="inputxt" style="width: 196px;height: 20px;">
                经销商:
                <select id="itemName" name="itemName" style="width: 196px;height:23px;border: 1px solid #a5aeb6" placeholder="可填写/输入">
                    <option value=""></option>
                    <c:forEach items="${organList}" var="o">
                        <option value="${o.name}">${o.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div align="right" style="margin-bottom: 20px;">
                <a href="#" class="qh-button icon-query" onclick="tScContractListsearch()"></a>
                <a href="#" class="qh-button icon-reset" onclick="tScContractListsearchReset('tScContractList')"></a>
            </div>
        </div>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/distribution/tScContractList.js"></script>
<link rel="stylesheet" href="plug-in/editableSelect/dist/jquery-editable-select.min.css" />
<script charset="utf-8" src="plug-in/editableSelect/dist/jquery-editable-select.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#itemName").editableSelect({
            effects:'slide'
        });
        //  //添加datagrid 事件 onDblClickRow
        $("#tScContractList").datagrid({
            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScContractController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,"tab");
            }
        });
        //给时间控件加上样式
        $("#tScContractListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScContractListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScContractListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScContractListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScContractController.do?upload', "tScContractList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScContractController.do?exportXls", "tScContractList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScContractController.do?exportXlsByT", "tScContractList");
    }
</script>