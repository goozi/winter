<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScAptitudeList" checkbox="true" beforeAccount="true" fitColumns="false" title="资格审查"  actionUrl="tScAptitudeController.do?datagrid&isWarm=${isWarm}" idField="id" fit="true"  queryMode="group" tranType="77" isSon="true" tableName="t_sc_aptitude" rowStyler="dgRowStyler">

            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true"  queryMode="single"  dictionary="SC_TRAN_TYPE" width="120"></t:dgCol>

            <t:dgCol title="单据编号" field="billNo"   query="true" queryMode="single"   width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="单据日期" field="date"  formatter="yyyy-MM-dd" query="true" queryMode="group"  width="120" mergeCells="true"></t:dgCol>
            <t:dgCol title="经销商" field="itemID" query="true"  dictionary="t_sc_organization,id,name"  queryMode="single" mergeCells="true"  width="120"></t:dgCol>
            <t:dgCol title="法人代表" field="corperate" query="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="联系人" field="contact"  query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilePhone"  query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电话" field="phone" query="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="传真" field="fax" query="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="联系地址" field="address"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="注册资金" field="regCapital"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="企业性质" field="economyKind"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="渠道" field="trench"  query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="代理产品" field="itemName"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="预计销量（年）" field="planQty"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售渠道|A类" field="trenchA"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售渠道|B类" field="trenchB"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售渠道|C类" field="trenchC"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="销售渠道|其他" field="trenchO"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="是否合格经销商" field="eligibility"    queryMode="single"  dictionary="sf_01" width="120"></t:dgCol>
            <t:dgCol title="审核人" field="checkerID"  mergeCells="true"  dictionary="t_s_base_user,id,realname"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="制单人" field="billerID" mergeCells="true" dictionary="t_s_base_user,id,realname" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="审核日期" field="checkDate" mergeCells="true" formatter="yyyy-MM-dd"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState"   mergeCells="true" queryMode="single"  dictionary="SC_AUDIT_STATUS" width="120"></t:dgCol>
            <%--<t:dgCol title="作废标记" field="cancellation" mergeCells="true" replace="未作废_0,已作废_1"   queryMode="single"  dictionary="sf_01" width="120"></t:dgCol>--%>
            <t:dgCol title="摘要" field="explanation"  hidden="true" mergeCells="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonID" dictionary="t_s_depart,id,departname"  mergeCells="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="逻辑删除标记" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled" dictionary="SC_DISABLED_CODE"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="checkState#eq#0" url="tScAptitudeController.do?doDel&id={id}"/>
            <t:dgFunOpt title="禁用" exp="checkState#eq#2&&eligibility#eq#1" funname="doDisabled(id)"/>
            <t:dgFunOpt title="启用" exp="checkState#eq#2&&eligibility#eq#0" funname="doEnabled(id)"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScAptitudeController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScAptitudeController.do?goUpdate"
                         funname="goUpdate"></t:dgToolBar>
   <%--         <t:dgToolBar title="批量删除" icon="new-icon-remove" url="tScAptitudeController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScAptitudeController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <%--<t:dgToolBar title="复制" icon="new-icon-copy" url="tScAptitudeController.do?goUpdate" funname="fcopy"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="new-icon-download" funname="ExportXlsByT"></t:dgToolBar>
          <%--  <t:dgToolBar title="作废" icon="new-icon-cancellation" exp="cancellation#eq#0" funname="cancellBillInfo"></t:dgToolBar>
            <t:dgToolBar title="反作废" icon="new-icon-uncancellation" exp="cancellation#ne#0" funname="unCancellBillInfo"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print" funname="CreateFormPage('资格审查', '#tScAptitudeList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/distribution/tScAptitudeList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#tScAptitudeList").datagrid({
            onDblClickRow: function (rowIndex, rowData) {
                url ='tScAptitudeController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,"tab");
            }
        });
        //给时间控件加上样式
        $("#tScAptitudeListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScAptitudeListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

        $("#tScAptitudeListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScAptitudeListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScAptitudeController.do?upload', "tScAptitudeList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScAptitudeController.do?exportXls", "tScAptitudeList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScAptitudeController.do?exportXlsByT", "tScAptitudeList");
    }
</script>