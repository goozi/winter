<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScDepartmentList" checkbox="false" fitColumns="false" title="部门" checkOnSelect="true" isSon="false"
                    actionUrl="tScDepartmentController.do?datagrid&disabled=0" idField="id" fit="true" onLoadSuccess="loadsuccess" colConfig="false"
                    queryMode="group" onDblClick="dbButtonOk">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="简称" field="shortName"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="部门主管" field="manager"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电话" field="phone"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="传真" field="fax"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="启用信控" field="isCreditMgr"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="信用额度" field="creditAmount"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled" replace="启用_0,禁用_1"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function dbButtonOk(){
        $(".ui_buttons .ui_state_highlight",parent.document).trigger('click');
    }
    $(document).ready(function () {

    });
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}_');
            $('.icon-query').click();
            $("input[name='name']").val('');
            flag = false;
        }
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
                msg: '请选择部门',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
</script>