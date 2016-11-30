<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScDrpLogisticalList" checkbox="true" fitColumns="false" title="物流信息表"
                    actionUrl="tScDrpLogisticalController.do?datagrid" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="主表主键" field="fid"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="承担方" field="bears"    queryMode="single"  dictionary="SC_BEAR" width="120"></t:dgCol>
            <t:dgCol title="买家承担方式" field="buyType"    queryMode="single"  dictionary="SC_BUYER_TYPE" width="120"></t:dgCol>
            <t:dgCol title="物流费用" field="freight"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="本次付款" field="curPayAmount"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="物流公司" field="logisticalID"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="物流单号" field="logisticalNo"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="结算账号" field="accountID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="执行金额" field="checkAmount"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScDrpLogisticalController.do?doDel&id={id}" exp="checkState#eq#0"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScDrpLogisticalController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScDrpLogisticalController.do?goUpdate"
                         funname="update"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScDrpLogisticalController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScDrpLogisticalController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('物流信息表', '#tScDrpLogisticalList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpLogisticalList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScDrpLogisticalList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScDrpLogisticalController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
            $("#tScDrpLogisticalListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScDrpLogisticalListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScDrpLogisticalController.do?upload', "tScDrpLogisticalList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScDrpLogisticalController.do?exportXls", "tScDrpLogisticalList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScDrpLogisticalController.do?exportXlsByT", "tScDrpLogisticalList");
    }
</script>