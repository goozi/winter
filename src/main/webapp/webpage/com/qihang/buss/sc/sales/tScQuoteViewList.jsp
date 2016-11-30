<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScQuoteList"  beforeAccount="true" checkbox="true" tranType="101" tableName="t_sc_Quote" fitColumns="false" isSon="true" rowStyler="dgRowStyler"
                    title="销售报价单" actionUrl="tScQuoteController.do?datagrid&isWarm=${isWarm}" idField="entryId" fit="true" onDblClick="goDetail" scrollview="true"
                    queryMode="group">
            <t:dgCol title="自动增长主键" field="entryId" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" mergeCells="true" queryMode="single" query="true" width="125"></t:dgCol>
            <t:dgCol title="单据日期" field="date" query="true" formatter="yyyy-MM-dd" queryMode="group"
                   mergeCells="true"  width="75"></t:dgCol>
            <t:dgCol title="经办人id" field="empid" hidden="true" queryMode="single" mergeCells="true" width="120"></t:dgCol>
            <t:dgCol title="经办人" field="empName" autocomplete="true" query="true" queryMode="single" mergeCells="true" width="65"></t:dgCol>
            <t:dgCol title="部门id" field="deptid" hidden="true" queryMode="single" mergeCells="true" width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptName" autocomplete="true" query="true" queryMode="single" mergeCells="true" width="65"></t:dgCol>
            <t:dgCol title="生效时间" field="inureDate" formatter="yyyy-MM-dd" queryMode="single" mergeCells="true" width="90"></t:dgCol>

            <t:dgCol title="分录号" field="indexNumber" queryMode="single"  width="50"></t:dgCol>
            <t:dgCol title="商品编号" field="icitemNumber" queryMode="single"  width="105"></t:dgCol>
            <t:dgCol title="商品名称" field="icitemName" autocomplete="true" query="true" queryMode="single"  width="180"></t:dgCol>
            <t:dgCol title="规格" field="icitemModel" queryMode="single"  width="85"></t:dgCol>
            <t:dgCol title="条形码" field="icitemBarCode" queryMode="single"  width="65"></t:dgCol>
            <t:dgCol title="单位" field="icitemUnitId" dictionary="T_SC_MeasureUnit,id,name" queryMode="single"
                      width="50"></t:dgCol>
            <t:dgCol title="数量段(从)" field="begQty" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="数量段(至)" field="endQty" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="单价" field="price" queryMode="single"  width="70"></t:dgCol>
            <t:dgCol title="成本单价" field="costPrice" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="毛利" field="grossAmount" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="毛利率" field="grossper" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="备注" field="note" queryMode="single"  width="180"></t:dgCol>

            <t:dgCol title="摘要" field="explanation" queryMode="single" mergeCells="true" width="180"></t:dgCol>
            <t:dgCol title="制单人" field="billerName" queryMode="single" mergeCells="true" width="90"></t:dgCol>
            <t:dgCol title="审核人" field="auditorName" queryMode="single" mergeCells="true" width="90"></t:dgCol>
            <t:dgCol title="审核日期" field="auditDate" formatter="yyyy-MM-dd" queryMode="single" mergeCells="true" width="80"></t:dgCol>
            <t:dgCol title="审核状态" field="checkState" query="true" dictionary="SC_AUDIT_STATUS" queryMode="single"
                     mergeCells="true" width="65"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName" queryMode="single" mergeCells="true" width="80"></t:dgCol>
            <t:dgCol title="操作" field="opt" mergeCells="true" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="checkState#eq#0" url="tScQuoteController.do?doDel&id={id}"/>
            <%--<t:dgFunOpt title="反审核" exp="checkState#ne#0" funname="goUnAudit(id,billNo,date)"/>--%>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScQuoteController.do?goAdd" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScQuoteController.do?goUpdate"
                         funname="goUpdate"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScQuoteController.do?goUpdate"
                         funname="fcopy"></t:dgToolBar>

            <t:dgToolBar title="变更" icon="new-icon-edit" url="tScQuoteController.do?goUpdate"
                         funname="goChange"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScQuoteController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScQuoteController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>

            <%--<t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('销售报价单', '#tScQuoteList')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sales/tScQuoteList.js"></script>
<script type="text/javascript">
    function goDetail(rowIndex, rowData){
        url = 'tScQuoteController.do?goUpdate' + '&load=detail&id=' + rowData.id;
        createdetailwindow('查看', url, null, null, "tab");
    }
    $(document).ready(function () {
//        //添加datagrid 事件 onDblClickRow
//        $("#tScQuoteList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//
//
//            },
//            onLoadSuccess: function (data) {
//                if (data.rows.length > 0) {
//                    mergeCellsByField("tScQuoteList", "billNo,date,empid,empName,deptid,deptName,inureDate,auditorName,auditDate,checkState,sonName,billerName,opt", "id");
//                }
//            }
//
//        });
        //给时间控件加上样式
        $("#tScQuoteListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScQuoteListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScQuoteListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScQuoteListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScQuoteListtb").find("input[name='inureDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScQuoteListtb").find("input[name='checkDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScQuoteListtb").find("input[name='date']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

//    function mergeCellsByField(tableID, colList, id) {
//        var ColArray = colList.split(",");
//        var tTable = $("#" + tableID);
//        var TableRowCnts = tTable.datagrid("getRows").length;
//        var tmpA;
//        var PerTxt = "";
//        var CurTxt = "";
//        var PerId = "";
//        var CurId = "";
//        for (var j = ColArray.length - 1; j >= 0; j--) {
//            PerTxt = "";
//            PerId = "";
//            tmpA = 1;
//            for (var i = 0; i <= TableRowCnts; i++) {
//                if (i == TableRowCnts) {
//                    CurTxt = "";
//                    CurId = "";
//                }
//                else {
//                    CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
//                    CurId = tTable.datagrid("getRows")[i][id];
//
//                }
//                if ((PerTxt == CurTxt) && (PerId == CurId)) {
//                    tmpA += 1;
//                }
//                else {
//                    var index = i - tmpA;
//                    tTable.datagrid("mergeCells", {
//                        index: index,
//                        field: ColArray[j],　　//合并字段
//                        rowspan: tmpA,
//                        colspan: null
//                    });
//                    tmpA = 1;
//                }
//                PerTxt = CurTxt;
//                PerId = CurId;
//            }
//        }
//    }
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScQuoteController.do?upload', "tScQuoteList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScQuoteController.do?exportXls&tranType=101", "tScQuoteList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScQuoteController.do?exportXlsByT", "tScQuoteList");
    }
</script>