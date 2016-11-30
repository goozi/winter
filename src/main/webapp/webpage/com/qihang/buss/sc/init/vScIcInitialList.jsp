<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input type="hidden" id="isAccountOpen" name="isAccountOpen" value="${isAccountOpen}">
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="vScIcInitialList" checkbox="true" fitColumns="false" title="存货初始化单"
                    actionUrl="vScIcInitialController.do?datagrid&isWarm=${isWarm}" onDblClick="dbClickView" idField="id" fit="true" tableName="t_sc_ic_initial" queryMode="group" tranType="${tranType}" beforeAccount="init" isSon="true" rowStyler="dgRowStyler">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="trantype"  hidden="true"  queryMode="group" dictionary="SC_TRAN_TYPE" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billno"   query="true" queryMode="single"   width="95" mergeCells="true"></t:dgCol>
            <t:dgCol title="单据日期" field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="75" mergeCells="true"></t:dgCol>
            <t:dgCol title="仓库ID" field="stockid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="仓库" field="stockname"   query="true" queryMode="single"   width="65"  mergeCells="true"></t:dgCol>
            <t:dgCol title="分录号" field="indexnumber"    queryMode="group"  width="45"></t:dgCol>
            <t:dgCol title="商品编号" field="entryitemno"    queryMode="group"  width="105"></t:dgCol>
            <t:dgCol title="商品名称" field="entryitemname"   query="true" queryMode="single"   width="180"></t:dgCol>
            <t:dgCol title="规格" field="model"    queryMode="group"  width="85"></t:dgCol>
            <t:dgCol title="条形码" field="barcode"    queryMode="group"  width="65"></t:dgCol>
            <t:dgCol title="单位" field="unitname"    queryMode="group"  width="50"></t:dgCol>
            <t:dgCol title="箱数" field="qtystr"    queryMode="group"  width="70"></t:dgCol>
            <t:dgCol title="散数" field="smallqtystr"    queryMode="group"  width="70"></t:dgCol>
            <t:dgCol title="基本单位ID" field="basicunitid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="基本单位" field="basicunitname"   hidden="true"  queryMode="group"  width="65"></t:dgCol>
            <t:dgCol title="换算率" field="coefficientstr"    queryMode="group"  width="65"></t:dgCol>
            <t:dgCol title="数量" field="basicqtystr"    queryMode="group"  width="65"></t:dgCol>
            <t:dgCol title="辅助单位ID" field="secunitid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="辅助单位" field="secunitname"   hidden="true"  queryMode="group"  width="65"></t:dgCol>
            <t:dgCol title="辅助换算率(%)" field="secCoefficientstr"   hidden="true"  queryMode="group"  width="70"></t:dgCol>
            <t:dgCol title="辅助数量" field="secqtyStr"   hidden="true"  queryMode="group"  width="65" ></t:dgCol>
            <t:dgCol title="成本单价" field="costpricestr"    queryMode="group"  width="70"></t:dgCol>
            <t:dgCol title="成本金额" field="costamountstr"    queryMode="group"  width="70"></t:dgCol>
            <t:dgCol title="批号" field="batchno"    queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="单位ID" field="unitid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="生产日期" field="kfdate" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="保质期" field="kfperiod"  hidden="true"  queryMode="group"  width="50"></t:dgCol>
            <t:dgCol title="保质期类型" field="kfdatetypeinfo"  hidden="true"  queryMode="group"  width="70" dictionary="SC_DURA_DATE_TYPE"></t:dgCol>
            <t:dgCol title="保质期" field="kfdatetype"  queryMode="group"  width="50"></t:dgCol>
            <t:dgCol title="有效期至" field="perioddate" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="备注" field="entryexplanation"    queryMode="group"  width="150"></t:dgCol>
            <t:dgCol title="摘要" field="explanation"    queryMode="group"  width="180" mergeCells="true"></t:dgCol>
            <t:dgCol title="制单人" field="billerid"    queryMode="group"  width="65" mergeCells="true"></t:dgCol>
            <t:dgCol title="审核人" field="checkerid"    queryMode="group"  width="65" mergeCells="true" dictionary="t_s_base_user,id,realname"></t:dgCol>
            <t:dgCol title="审核日期" field="checkdate" formatter="yyyy-MM-dd"   queryMode="group"  width="128" mergeCells="true"></t:dgCol>
            <t:dgCol title="审核状态" field="checkstate"   query="true" queryMode="single"  dictionary="SC_AUDIT_STATUS" width="65" mergeCells="true"></t:dgCol>
            <t:dgCol title="作废标记" field="cancellation" hidden="false" replace="未作废_0,已作废_1" queryMode="single"   width="65" mergeCells="true"></t:dgCol>
            <t:dgCol title="分支机构ID" field="sonid"  hidden="true"  queryMode="group"  width="80" mergeCells="true"  dictionary="t_s_depart,id,departname"></t:dgCol>
            <t:dgCol title="分支机构" field="sonname" width="80" mergeCells="true"></t:dgCol>
            <t:dgCol title="版本" field="version"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="附表主键" field="entryid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="商品ID" field="entryitemid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="附表仓库ID" field="entrystockid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="附表仓库名称" field="entrystockname"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100" mergeCells="true"></t:dgCol>
            <t:dgDelOpt title="删除" url="tScIcInitialController.do?doDel&id={id}" exp="checkstate#eq#0&&cancellation#eq#0"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScIcInitialController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScIcInitialController.do?goUpdate"
                         funname="goUpdate"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScIcInitialController.do?goUpdate"
                         funname="fcopy" windowType="tab"></t:dgToolBar>

            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScIcInitialController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScIcInitialController.do?goUpdate"
                         funname="detail" windowType="tab"></t:dgToolBar>
            <%--<t:dgToolBar title="作废" icon="new-icon-cancellation"  funname="cancelBill"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="反作废" icon="new-icon-uncancellation"  funname="enableBill"></t:dgToolBar>--%>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('存货初始化单', '#vScIcInitialList')"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/init/vScIcInitialList.js"></script>
<script type="text/javascript">
    function dbClickView(rowIndex, rowData) {
        url = 'tScIcInitialController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, 1060, 700,"tab");
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        //给时间控件加上样式
                $("#vScIcInitialListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='kfdate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vIcInitialListtb").find("input[name='kfdate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='perioddate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='perioddate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='checkdate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
                $("#vScIcInitialListtb").find("input[name='checkdate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                    WdatePicker({dateFmt: 'yyyy-MM-dd'});
                });
        //判断是否已经开帐，如果是则不允许相关操作
//        var isAccountOpen = $("#isAccountOpen").val();
//        if (isAccountOpen=="true"){
//            $("[icon=icon-add]").linkbutton("disable");
//            $("[icon=icon-edit]").linkbutton("disable");
//            $("[icon=new-icon-cancellation]").linkbutton("disable");
//            $("[icon=new-icon-uncancellation]").linkbutton("disable");
//            $("[icon=new-icon-audit]").linkbutton("disable");
//            $("[icon=icon-put]").linkbutton("disable");
//        }
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'vScIcInitialController.do?upload', "vScIcInitialList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("vScIcInitialController.do?exportXls", "vScIcInitialList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("vScIcInitialController.do?exportXlsByT", "vScIcInitialList");
    }


</script>