<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%--<div id="mm1" class="easyui-menu" style="width:120px;">--%>
    <%--<div onclick="addTree('03')" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>--%>
    <%--<div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>--%>
    <%--<div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>--%>
<%--</div>--%>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScLogisticalList" checkbox="false" checkOnSelect="true" onLoadSuccess="loadsuccess" fitColumns="false" title="物流公司"
                    actionUrl="tScLogisticalController.do?datagrid&disabled=0" idField="id" fit="true" isSon="false" colConfig="false"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="简称" field="shortName"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="联系人" field="contact"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilePhone"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电话" field="phone"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="传真" field="fax"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="联系地址" field="address"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="区域" field="regionID"    queryMode="single"  dictionary="SC_REGION" width="120"></t:dgCol>
            <t:dgCol title="邮政编码" field="postalCode"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电子邮件" field="email"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="开户银行" field="bank"    queryMode="single"  dictionary="SC_BANK" width="120"></t:dgCol>
            <t:dgCol title="银行账号" field="bankNumber"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="换货天数" field="jhDay"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="换货比例" field="jhRate"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"    queryMode="single"   width="200"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}');
            $('.icon-query').click();
            flag = false;
        }
    }

    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tScLogisticalList").datagrid({
//
//
//            onDblClickRow: function (rowIndex, rowData) {
//        url = 'tScLogisticalController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//        createdetailwindow('查看', url, 1000, 500);
//
//    }

//    });
        //给时间控件加上样式
            $("#tScLogisticalListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScLogisticalListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScLogisticalController.do?upload', "tScLogisticalList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScLogisticalController.do?exportXls", "tScLogisticalList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScLogisticalController.do?exportXlsByT", "tScLogisticalList");
    }

    var popJon;
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tScLogisticalList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择物流公司',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
</script>