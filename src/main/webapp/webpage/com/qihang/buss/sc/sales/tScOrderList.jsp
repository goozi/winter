<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScOrderList" checkbox="true" fitColumns="false" title="销售订单"
                    actionUrl="tScOrderController.do?datagridMain&cancellation=0&checkState=2&itemID=${itemId}&isStock=0&tranType=102&ids=${ids}&ids2=${ids2}&sonID=${sonId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" query="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="客户Id" field="itemID" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="客户" field="itemName" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="经办人Id" field="empID" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="经办人" field="empName" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="部门Id" field="deptID" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptName" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="预收金额" field="preAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();var newV = parseFloat(v||0).toFixed(m); return newV;}," queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="优惠金额" field="rebateAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();var newV = parseFloat(v||0).toFixed(m); return newV;}," queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="物流费用" field="freight" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();var newV = parseFloat(v||0).toFixed(m); return newV;}," queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="订单金额" field="amount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();var newV = parseFloat(v||0).toFixed(m); return newV;}," hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="应收金额" field="allAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();var newV = parseFloat(v||0).toFixed(m); return newV;}," queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="执行金额" field="checkAmount" extendParams="formatter:function(v,r,i){var m = $('#CFG_MONEY').val();var newV = parseFloat(v||0).toFixed(m); return newV;}," queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="摘要" field="explanation" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="分支机构" field="sonName" queryMode="single" width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sales/tScOrderList.js"></script>
<script type="text/javascript">
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
                        if (val.id == rowData.id) {
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
                    if (value.id == rowData.id) {
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
                        if (row.id != popJon[j].id) {
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
                        if (row.id == popJon[j].id) {
                            popJon.remove(j);
                            break;
                        }
                    }
                }
            },
            onLoadSuccess: function (data) {
                for(var i = 0; i<data.rows.length ;i++){
                    var row = data.rows[i];
                    var id = row.id;
                    for(var j=0 ; j<popJon.length ; j++){
                        var inf = popJon[j];
                        if(id==inf.id){
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
        $("#tScOrderListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
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
                            if(value.id == value.id) {
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
                        if(rowInfo.id == popJon[k].id){
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