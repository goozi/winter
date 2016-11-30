<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScDepartmentList" checkbox="false" fitColumns="false" title="部门" checkOnSelect="true"
                    actionUrl="tScDepartmentController.do?datagrid&disabled=0" idField="id" fit="true" colConfig="false"
                    queryMode="group" onLoadSuccess="loadsuccess">
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
            <t:dgCol title="助记码" field="shortNumber"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="简称" field="shortName"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="部门主管" field="manager"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电话" field="phone"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="传真" field="fax"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="启用信控" field="isCreditMgr"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="信用额度" field="creditAmount"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled" replace="启用_0,禁用_1"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScDepartmentList.js"></script>
<script type="text/javascript">
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}');
            $('.icon-query').click();
            flag = false;
        }
    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScDepartmentController.do?upload', "tScDepartmentList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScDepartmentController.do?exportXls", "tScDepartmentList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScDepartmentController.do?exportXlsByT", "tScDepartmentList");
    }
    var popJon;
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tScDepartmentList').datagrid('getSelections');
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
            return '';
        }
    }
</script>