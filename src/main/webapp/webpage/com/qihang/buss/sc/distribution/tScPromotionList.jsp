<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScPromotionList" checkbox="true" fitColumns="false" title="买一送一促销单"
                    actionUrl="tScPromotionController.do?datagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="单据类型 " field="tranType" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd" query="true" queryMode="group"
                     width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="经办人ID" field="empID" hidden="true" dictionary="t_sc_emp,id,name" queryMode="single"
                     width="160"></t:dgCol>
            <t:dgCol title="经办人" field="empName" query="true" queryMode="single" width="160"></t:dgCol>
            <t:dgCol title="部门" field="deptID" dictionary="t_sc_department,id,name" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="起始日期	" field="begDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="结束日期	" field="endDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="制单人" field="billerName" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="审核人 " field="checkerID" dictionary="t_sc_emp,id,name" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="作废标记" field="cancellation" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="摘要 " field="explanation" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName" queryMode="single" width="130"></t:dgCol>
            <t:dgCol title="版本号" field="version" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="是否禁用" field="disabled" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="是否删除" field="deleted" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScPromotionController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="icon-add"
                         url="tScPromotionController.do?goAdd" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit"
                         url="tScPromotionController.do?goUpdate" funname="update"></t:dgToolBar>
            <t:dgToolBar title="批量删除" icon="icon-remove" url="tScPromotionController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search"
                         url="tScPromotionController.do?goUpdate" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="icon-print"
                         funname="CreateFormPage('买一送一促销单', '#tScPromotionList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/distribution/tScPromotionList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //添加datagrid 事件 onDblClickRow
        $("#tScPromotionList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScPromotionController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null);

            }

        });
        //给时间控件加上样式
        $("#tScPromotionListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionListtb").find("input[name='begDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionListtb").find("input[name='endDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScPromotionListtb").find("input[name='checkDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScPromotionController.do?upload', "tScPromotionList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScPromotionController.do?exportXls", "tScPromotionList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScPromotionController.do?exportXlsByT", "tScPromotionList");
    }
</script>