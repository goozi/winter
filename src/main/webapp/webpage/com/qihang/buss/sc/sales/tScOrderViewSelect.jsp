<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <div id="mm1" class="easyui-menu" style="width:120px;">
            <div onclick="selectBill()" data-options="iconCls:'icon-add'"><font color="black" >选择本单</font></div>
            <div onclick="unselect()" data-options="iconCls:'icon-edit'"><font color="black">取消选择</font></div>
        </div>
        <t:datagrid name="tScOrderList" checkbox="true" fitColumns="false" title="销售订单"
                    actionUrl="tScOrderController.do?datagridSelect&closed=0&cancellation=0&checkState=2&aitemid=${itemId}&isStock=0&tranType=102&sonId=${sonId}" idField="entryId"
                    fit="true" queryMode="group">
            <t:dgCol title="从表主键" field="entryId" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="主表主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranTypeName"  hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" query="true" formatter="yyyy-MM-dd" queryMode="single"
                     width="80"></t:dgCol>
            <t:dgCol title="客户" field="aitemid" hidden="true" queryMode="single"  width="180"></t:dgCol>
            <t:dgCol title="客户" field="aitemName"  queryMode="single"  width="180"></t:dgCol>
            <t:dgCol title="经办人" field="empid" hidden="true"  queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="经办人" field="empName"  queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="部门" field="deptid" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="部门" field="deptName"  queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="仓库" field="astockId" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="仓库" field="astockName" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="收款方式" field="fetchStyle" dictionary="SC_PAY_TYPE" query="true" queryMode="single"
                     width="70"></t:dgCol>
            <t:dgCol title="应收金额" field="allamount" extendParams="formatter:function(v,r,i){return formatterMoney(v);}," queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="预收金额" field="preamount" extendParams="formatter:function(v,r,i){return formatterMoney(v);}," queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="优惠金额" field="rebateamount" extendParams="formatter:function(v,r,i){return formatterMoney(v);}," queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="物流费用" field="freight" extendParams="formatter:function(v,r,i){return formatterMoney(v);}," queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="重量" field="aweight"  extendParams="formatter:function(v,r,i){var m = $('#CFG_OTHER').val();var newV = parseFloat(v).toFixed(m); return newV;}," queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="分录号" field="indexNumber" queryMode="single" width="40"></t:dgCol>
            <t:dgCol title="商品id" field="itemid" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="itemNumber" queryMode="single" width="105"></t:dgCol>
            <t:dgCol title="商品名称" field="itemName" queryMode="single" width="180"></t:dgCol>
            <t:dgCol title="规格" field="model" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="条形码" field="barCode" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="仓库" hidden="true" field="stockid" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="仓库" field="stockName" queryMode="single" hidden="true" width="70"></t:dgCol>
            <t:dgCol title="单位" field="unitid" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单位" field="unitName"  queryMode="single" width="50"></t:dgCol>
            <t:dgCol title="数量" field="qty" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="换算率" field="coefficient" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="单价" field="taxPriceEx" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="金额" field="taxAmountEx" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="折扣率%" field="discountRate" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="折后单价" field="taxPrice" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="折后金额" field="inTaxAmount" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="商城单价" field="mallPrice" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="商城金额" field="mallAmount" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="重量" field="weight" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="商品重量" field="itemweight" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="税率" field="taxRate" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="交货日期" field="fetchDate" formatter="yyyy-MM-dd" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="促销类型Id"  field="salesID" hidden="true" queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="促销类型"  field="salesName"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="促销类型" hidden="true" field="isFreeGift"  queryMode="single"  width="80"></t:dgCol>
            <t:dgCol title="赠品标记" dictionary="sf_01" field="freegifts" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="发货数量" field="stockQty" queryMode="single" width="70"></t:dgCol>

            <t:dgCol title="基本单位id" field="basicUnitId" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="基本数量" field="basicQty" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="保质期" field="kfperiod" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="保质期类型" field="kfdatetype" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="库存数量" field="invQty" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="源单编号" field="billnoSrc" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="源单类型" field="classidSrc" dictionary="t_s_bill_info,bill_id,bill_name" hidden="true" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="备注" field="note" queryMode="single" width="180"></t:dgCol>

            <t:dgCol title="订单来源" field="mall" replace="系统新增_1,商城订单_2" queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="商城单号" field="mallOrderID" extendParams="formatter:function(v,r,i){if(v == 0){v='';} return v;}," queryMode="single" width="90"></t:dgCol>
            <t:dgCol title="摘要" field="explanation" hidden="true" queryMode="single" width="180"></t:dgCol>
            <t:dgCol title="联系人" field="contact" hidden="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilephone" hidden="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="电话" field="phone" hidden="true" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="联系地址" field="address" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="执行金额" field="checkamount" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="是否保质期控制" field="isKfperiod" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="是否批号控制" field="batchManager" hidden="true" queryMode="single" width="70"></t:dgCol>
            <t:dgCol title="制单人" field="billerid" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核人" field="auditorname" hidden="true" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="审核日期" field="auditdate" hidden="true" formatter="yyyy-MM-dd" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="审核状态" field="status" hidden="true" dictionary="SC_AUDIT_STATUS" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="关闭标记" field="closed" hidden="true" replace="未关闭_0,已关闭_1" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="自动关闭标记" field="autoFlag" replace="否_0,是_1" hidden="true" queryMode="single"
                     width="120"></t:dgCol>
            <t:dgCol title="作废标记" field="cancellation" hidden="true" replace="未作废_0,已作废_1" queryMode="single" width="65"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName" hidden="true" queryMode="single" width="100"></t:dgCol>

            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
            <%--<t:dgDelOpt title="删除"  exp="cancellation#eq#0&&status#eq#0"  url="tScOrderController.do?doDel&id={id}" />--%>
            <%--<t:dgFunOpt title="反审核" exp="status#ne#0" funname="goUnAudit(id,billNo,date)"/>--%>
            <%--<t:dgToolBar title="录入" icon="icon-add" url="tScOrderController.do?goAdd" funname="add"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScOrderController.do?goUpdate" funname="updateOrder"></t:dgToolBar>--%>
            <%--&lt;%&ndash;<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScOrderController.do?doBatchDel" funname="deleteALLSelectOrder"></t:dgToolBar>&ndash;%&gt;--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" windowType="tab" url="tScOrderController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="变更" icon="icon-edit" url="tScOrderController.do?goUpdate" funname="goChange"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="关闭" icon="icon-remove"  funname="closeOrderBill"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="反关闭" icon="icon-remove"  funname="oppositeCloseOrderBill"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="作废" icon="icon-remove"  funname="cancelBill"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="反作废" icon="icon-remove"  funname="enableBill"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
            <%--&lt;%&ndash;<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>&ndash;%&gt;--%>
            <%--<t:dgToolBar title="打印" icon="icon-print"--%>
            <%--funname="CreateFormPage('销售订单', '#tScOrderList')"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sales/tScOrderList.js"></script>
<script type="text/javascript">
    function formatterMoney(v){
        if(v){
            var CFG_MONEY = $("#CFG_MONEY").val();
            return parseFloat(v).toFixed(CFG_MONEY);
        }else{
            return "";
        }
    }
    $(document).ready(function () {
        //添加datagrid 事件 onDblClickRow
        $("#tScOrderList").datagrid({

//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScOrderController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null,"tab");
//         },
            onSelect: function (rowIndex, rowData) {
                if (popJon.length) {
                    var isPush = true;
                    for (var i = 0; i < popJon.length; i++) {
                        var val = popJon[i];
                        if (val.entryId == rowData.entryId) {
                            isPush = false;
                        }
                    }
                    if (isPush) {
                        popJon.push(rowData);
                    }
                } else {
                    popJon.push(rowData);
                }
            },
            onUnselect: function (rowIndex, rowData) {
                for (var i = 0; i < popJon.length; i++) {
                    var value = popJon[i];
                    if (value.entryId == rowData.entryId) {
                        popJon.remove(i);
                        break;
                    }
                }
            },
            onSelectAll: function (rows) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var checkNum = 0;
                    for (var j = 0; j < popJon.length; j++) {
                        if (row.entryId != popJon[j].entryId) {
                            checkNum++;
                        }
                    }
                    if (checkNum == popJon.length) {
                        popJon.push(row);
                    }
                }
            },
            onUnselectAll: function (rows) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    for (var j = 0; j < popJon.length; j++) {
                        if (row.entryId == popJon[j].entryId) {
                            popJon.remove(j);
                            break;
                        }
                    }
                }
            },
            onLoadSuccess: function (data) {
                if (data.rows.length > 0) {
                    mergeCellsByField("tScOrderList", "billNo,date,aitemid,aitemName,empid,empName,deptid," +
                            "deptName,fetchStyle,allamount,preamount,rebateamount,freight,aweight,mall," +
                            "mallOrderID,auditorname,auditdate,status,closed,cancellation,opt", "id");
                }
                for(var i = 0; i<data.rows.length ;i++){
                    var row = data.rows[i];
                    var id = row.entryId;
                    for(var j=0 ; j<popJon.length ; j++){
                        var inf = popJon[j];
                        if(id==inf.entryId){
                            $("#tScOrderList").datagrid("selectRow",i);
                            //popJon.remove(j);
                            break;
                        }
                    }
                }
            },
            onRowContextMenu:function(e, rowIndex, rowData){
                return showRightMenu(e,rowData);
            }

        });
        //给时间控件加上样式
        $("#tScOrderListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='date']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='checkDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });
    //右键菜单配置
    var selectRow;
    function showRightMenu(e,rowData){
        selectRow = rowData;
        e.preventDefault();
        //显示快捷菜单
        $('#mm1').menu({
            onShow:function(){
                selectRow = rowData;
            }
        });
        $('#mm1').menu('show',{
            left: e.pageX,
            top: e.pageY
        });
    }

    //单据全选
    function selectBill(){
        var id = selectRow.id;
        var entryUrl = "tScOrderController.do?loadEntryViewList&id="+id;
        $.ajax({
            url: entryUrl,
            dataType: 'json',
            cache: false,
            async: false,
            success: function (data) {
                var rows = data;
                if(popJon.length > 0) {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        for(var j = 0 ; j < popJon.length ; j++) {
                            var value = popJon[j];
                            if(value.entryId == value.entryId) {
                                popJon.remove(j);
                                break;
                            }
                        }
                    }
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        popJon.push(row);
                    }
                }else{
                    popJon = rows;
                }
                var rows = $("#tScOrderList").datagrid("getRows");
                for(var index = 0 ; index < rows.length ; index++){
                    var rowInfo = rows[index];
                    for(var k = 0 ; k<popJon.length ; k++){
                        if(rowInfo.entryId == popJon[k].entryId){
                            $("#tScOrderList").datagrid("selectRow",index);
                            break;
                        }
                    }
                }
            }
        });
    }
    //取消选择
    function unselect(){
        var id = selectRow.id;
        var rows = $("#tScOrderList").datagrid("getRows");
        for(var index = 0 ; index < rows.length ; index++){
            var rowInfo = rows[index];
            for(var k = 0 ; k<popJon.length ; k++){
                if(rowInfo.id == id && rowInfo.id == popJon[k].id){
                    $("#tScOrderList").datagrid("unselectRow",index);
                    break;
                }
            }
        }
    }

    function mergeCellsByField(tableID, colList, id) {
        var ColArray = colList.split(",");
        var tTable = $("#" + tableID);
        var TableRowCnts = tTable.datagrid("getRows").length;
        var tmpA;
        var PerTxt = "";
        var CurTxt = "";
        var PerId = "";
        var CurId = "";
        for (var j = ColArray.length - 1; j >= 0; j--) {
            PerTxt = "";
            PerId = "";
            tmpA = 1;
            for (var i = 0; i <= TableRowCnts; i++) {
                if (i == TableRowCnts) {
                    CurTxt = "";
                    CurId = "";
                }
                else {
                    CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                    CurId = tTable.datagrid("getRows")[i][id];

                }
                if ((PerTxt == CurTxt) && (PerId == CurId)) {
                    tmpA += 1;
                }
                else {
                    var index = i - tmpA;
                    tTable.datagrid("mergeCells", {
                        index: index,
                        field: ColArray[j],　　//合并字段
                        rowspan: tmpA,
                        colspan: null
                    });
                    tmpA = 1;
                }
                PerTxt = CurTxt;
                PerId = CurId;
            }
        }
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScOrderController.do?upload', "tScOrderList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScOrderController.do?exportXls", "tScOrderList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScOrderController.do?exportXlsByT", "tScOrderList");
    }

    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i]
            }
        }
        this.length -= 1
    }
    var popJon = new Array();
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = popJon;
        if (rows.length > 0) {
            isPop = true;
            return rows;

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择销售订单',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
</script>