<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScAccountConfigList" checkbox="true" fitColumns="false" title="账套设置"
                    actionUrl="tScAccountConfigController.do?datagrid" idField="id" fit="true"
                    queryMode="group" onDblClick="dbClickView">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="账套名称" field="dbKey" query="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="账套启用年月" field="accountStartDate" query="true"  queryMode="group"   width="80" formatter="yyyy-MM"></t:dgCol>
            <t:dgCol title="当前期间" field="stageStartDate" query="true"  queryMode="group"   width="80" formatter="yyyy-MM"></t:dgCol>
            <%--<t:dgCol title="账套启用月份" field="accountStartMonth"  queryMode="single"   width="80"></t:dgCol>--%>
            <%--<t:dgCol title="允许负库存结账" field="minusInventoryAccount"    queryMode="single"   width="100"></t:dgCol>--%>
            <%--<t:dgCol title="允许负库存出库" field="minusInventorySl"    queryMode="single"   width="100"></t:dgCol>--%>
            <t:dgCol title="公司名称" field="companyName"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="税号" field="taxNumber"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="银行账号" field="bankAccount"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="公司地址" field="companyAddress"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="联系人" field="contact"    queryMode="single"   width="80"></t:dgCol>
            <t:dgCol title="电话" field="phone"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="传真" field="fax"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="E_Mail" field="email"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="公司网址" field="companyUrl"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="注册号" field="registrationNumber"    queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="程序版本" field="systemVersion"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="开启状态" field="openState"  replace="未开启_0,已开启_1"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="开启时间" field="openDate" formatter="yyyy-MM-dd"   queryMode="single"   width="100"></t:dgCol>
            <%--<t:dgCol title="结账状态" field="closeState"  replace="未结账_0,已结账_1"   queryMode="single"   width="100"></t:dgCol>--%>
            <%--<t:dgCol title="结账时间" field="closeDate"    queryMode="single"   width="100"></t:dgCol>--%>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="openState#eq#0" url="tScAccountConfigController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScAccountConfigController.do?goAddInit"
                         funname="add"></t:dgToolBar>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScAccountConfigController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScAccountConfigController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScAccountConfigController.do?goView"
                         funname="detail"></t:dgToolBar>
<%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('账套设置', '#tScAccountConfigList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sys/tScAccountConfigList.js"></script>
<script type="text/javascript">
    function dbClickView(rowIndex, rowData) {
        url = 'tScAccountConfigController.do?goView' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, 1060, 700,"tab");
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        //给时间控件加上样式
            $("#tScAccountConfigListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScAccountConfigListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScAccountConfigController.do?upload', "tScAccountConfigList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScAccountConfigController.do?exportXls", "tScAccountConfigList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScAccountConfigController.do?exportXlsByT", "tScAccountConfigList");
    }
</script>