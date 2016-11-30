<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input id="isAccountOpen" name="isAccountOpen" value="${isAccountOpen}" type="hidden">

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScBegdataList" checkbox="true" fitColumns="false" title="收支初始化"
                    actionUrl="tScBegdataController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true" tableName="t_sc_begdata" beforeAccount="init"
                    queryMode="group" tranType="${tranType}">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true" queryMode="single" dictionary="SC_TRAN_TYPE"
                     width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" query="true" queryMode="single" width="180"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd" query="true" queryMode="group" width="120"
                     mergeCells="true"></t:dgCol>
            <t:dgCol title="结算账户" field="itemID"  query="true" queryMode="single" dictionary="T_SC_SETTLEACCT,id,name"
                     width="180"></t:dgCol>
            <%--<t:dgCol title="应收日期" field="rpDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>--%>
            <t:dgCol title="经办人" field="empID" query="true" queryMode="single" dictionary="T_SC_EMP,id,name" width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptID" query="true"  queryMode="single" dictionary="T_SC_Department,id,Name"
                     width="120"></t:dgCol>
            <t:dgCol title="总金额" field="allAmount" queryMode="single" width="120"></t:dgCol>
            <%--<t:dgCol title="已付金额" field="checkAmount" hidden="true" queryMode="single" width="120"></t:dgCol>--%>
            <%--<t:dgCol title="未付金额" field="unCheckAmount" hidden="true" queryMode="single" width="120"></t:dgCol>--%>
            <t:dgCol title="摘要" field="explanation" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="制单人" field="billerID" queryMode="single" dictionary="t_s_base_user,id,realname"
                     width="120"></t:dgCol>
            <t:dgCol title="审核人" field="checkerID" queryMode="single" dictionary="t_s_base_user,id,realname"
                     width="120"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState" query="true" queryMode="single" dictionary="SC_AUDIT_STATUS"
                     width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonID" queryMode="single" dictionary="t_s_depart,id,departname"
                     width="180"></t:dgCol>
            <t:dgCol title="版本号" field="version" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScBegdataController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScBegdataController.do?goAdd&tranType=${tranType}"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScBegdataController.do?goUpdate&tranType=${tranType}"
                         funname="goUpdate"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScBegdataController.do?goUpdate" funname="fcopy"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScBegdataController.do?doBatchDel"--%>
            <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScBegdataController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('应收初始化', '#tScBegdataList')"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/init/tScBegdataincomepayList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScBegdataList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScBegdataController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,'tab');

            }

        });
        //给时间控件加上样式
        $("#tScBegdataListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScBegdataListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
//            $("#tScBegdataListtb").find("input[name='date']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
//                WdatePicker({dateFmt: 'yyyy-MM-dd'});
//            });
        $("#tScBegdataListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScBegdataListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });


        $("#tScBegdataListtb").find("input[name='rpDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScBegdataListtb").find("input[name='checkDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

        //判断是否已经开帐，如果是则工具栏不允许相关操作
        var isAccountOpen = $("#isAccountOpen").val();
        if (isAccountOpen == "true") {
            $("[icon=icon-add]").linkbutton("disable");
            $("[icon=icon-edit]").linkbutton("disable");
            $("[icon=icon-remove]").linkbutton("disable");
            $("[icon=icon-put]").linkbutton("disable");
        }
    });


    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScBegdataController.do?upload', "tScBegdataList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScBegdataController.do?exportXls&tranType=${tranType}", "tScBegdataList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScBegdataController.do?exportXlsByT", "tScBegdataList");
    }
</script>