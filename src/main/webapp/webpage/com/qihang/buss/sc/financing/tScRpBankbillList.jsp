<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScRpBankbillList" checkbox="true" fitColumns="false" title="银行存取款"
                    actionUrl="tScRpBankbillController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" beforeAccount="true"
                    tranType="${tranType}" tableName="t_sc_rp_bankbill" queryMode="group" rowStyler="dgRowStyler">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本信息" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo"   query="true" queryMode="single"   width="60"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"   width="120"></t:dgCol>
            <t:dgCol title="经办人" field="tScEmpEntity_name"    queryMode="single" query="true"   width="60"></t:dgCol>
            <t:dgCol title="部门" field="tScDepartmentEntity_name"    queryMode="single" query="true"   width="60"></t:dgCol>
            <t:dgCol title="转出机构" field="dcsoncompanyEntity_departname"   queryMode="single" query="true"  width="120"></t:dgCol>
            <t:dgCol title="转出账户" field="pasettleacctEntity_name"   queryMode="single" query="true"   width="120"></t:dgCol>
            <t:dgCol title="转入机构" field="scsoncompanyEntity_departname"   queryMode="single"  query="true"  width="120"></t:dgCol>
            <t:dgCol title="转入账户" field="rasettleacctEntity_name"   queryMode="single"  query="true"  width="120"></t:dgCol>
            <t:dgCol title="金额" field="allAmount"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="审核人" field="checkerUser_realName"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="制单人" field="billerUser_realName"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState"   queryMode="single"   width="120" replace="未审核_0,审核中_1,已审核_2"></t:dgCol>
            <t:dgCol title="摘要" field="explanation"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="tsDepart_departname"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScRpBankbillController.do?doDel&id={id}" exp="checkState#eq#0"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScRpBankbillController.do?goAdd" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit"  onclick="updateEdit()"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScRpBankbillController.do?goUpdate" funname="fcopy"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScRpBankbillController.do?goUpdate" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="new-icon-print" funname="CreateFormPage('银行存取款', '#tScRpBankbillList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/financing/tScRpBankbillList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScRpBankbillList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScRpBankbillController.do?goUpdate' + '&load=detail&id=' + rowData.id;
               // createdetailwindow('查看', url, null, null);
                createdetailwindow('查看', url, null, null,"tab");
            }

        });
        //给时间控件加上样式
            $("#tScRpBankbillListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScRpBankbillListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
                $("#tScRpBankbillListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScRpBankbillListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScRpBankbillListtb").find("input[name='checkDate _begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#tScRpBankbillListtb").find("input[name='checkDate _end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScRpBankbillController.do?upload', "tScRpBankbillList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScRpBankbillController.do?exportXls", "tScRpBankbillList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScRpBankbillController.do?exportXlsByT", "tScRpBankbillList");
    }
</script>