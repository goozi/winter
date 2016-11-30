<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">


    <div region="center" style="padding:1px;">
        <t:datagrid name="tScOrganizationList" checkbox="false" fitColumns="false" title="客户"
                    actionUrl="tScAptitudeController.do?datagridSelect&disable=0&deleted=0&isDealer=1" checkOnSelect="true" idField="id" fit="true" onLoadSuccess="loadsuccess"
                    queryMode="group" isSon="false">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="编号" field="number" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="名称" field="name" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="营业执照号" field="licence" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="结算单位" field="settlecompany" hidden="true" dictionary="t_sc_organization,id,name" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="开户银行" field="bank" queryMode="single" dictionary="SC_BANK" width="120"></t:dgCol>
            <t:dgCol title="银行账号" field="banknumber" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="客户分类" field="typeid" queryMode="single" dictionary="SC_TYPE_ID" width="120"></t:dgCol>
            <t:dgCol title="上级经销商" field="dealer" queryMode="single" dictionary="t_sc_organization,id,name" width="120"></t:dgCol>
            <t:dgCol title="默认业务员" field="defaultoperator" dictionary="t_sc_emp,id,name" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="送货方式" field="delivertype" queryMode="single" dictionary="SC_DELIVER_TYPE"
                     width="120"></t:dgCol>
            <t:dgCol title="分类id" field="parentid" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="简称" field="shortname" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortnumber" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="联系人" field="contact" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilephone" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="电话" field="phone" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="传真" field="fax" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="邮政编码" field="postalcode" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="区域" field="regionid" queryMode="single" dictionary="SC_REGION" width="120"></t:dgCol>
            <t:dgCol title="城市" field="city"  queryMode="single" dictionary="SC_CITY" width="120"></t:dgCol>
            <t:dgCol title="qq号" field="ciqnumber" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="电子邮件" field="email" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="公司主页" field="homepage" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="法人代表" field="corperate" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="行业" field="trade" queryMode="single" dictionary="SC_TRADE" width="120"></t:dgCol>
            <t:dgCol title="启用信控" field="iscreditmgr" queryMode="single" dictionary="sf_01" width="120"></t:dgCol>
            <t:dgCol title="信用额度" field="creditamount" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="级次" field="level" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="联系地址" field="address" queryMode="single" width="200"></t:dgCol>
            <t:dgCol title="备注" field="note" queryMode="single" width="200"></t:dgCol>
            <t:dgCol title="禁用标记" field="disable" queryMode="single" replace="启用_0,禁用_1" width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="逻辑删除" field="deleted" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="已执行金额" field="countAmount" hidden="true" queryMode="single" width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">
    <%--window.onload=function(){--%>
    <%--$("input[name='name']").val(${name});--%>
    <%--$(".icon-query").click();--%>
    <%--}--%>
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}_');
            $('.icon-query').click();
            $("input[name='name']").val('');
            flag = false;
        }
    }
    $(document).ready(function () {
//        $("#tCrProductList").datagrid({
//            singleSelect:true,
//            method:'get'
//        });




        //给时间控件加上样式
        $("#tScOrganizationListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrganizationListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    var popJon;
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tScOrganizationList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择结算单位',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
    function getMoreSelectionData() {
        var rows = $('#tScOrganizationList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows;

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择结算单位',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
</script>