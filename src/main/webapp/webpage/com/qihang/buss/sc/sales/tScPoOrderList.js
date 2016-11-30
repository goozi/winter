//关闭功能
function closeBill(){
    var selected = $("#tPoOrderList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.state;
        var closed = row.closed;
        if(closed == 1){
            tip("单据["+row.billNo+"]已关闭，不可重复关闭");
            return;
        }
        if(state < 2){
            tip("单据["+row.billNo+"]未审核完毕，不可关闭");
            return;
        }
        if(cancellation == 1){
            tip("单据["+row.billNo+"]已作废，不可关闭");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tPoOrderController.do?closeBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("关闭成功");
                $("#tPoOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}
//反关闭功能
function openBill(){
    var selected = $("#tPoOrderList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var autoFlag = row.autoFlag;
        if(autoFlag == 1){
            tip("单据["+row.billNo+"]已自动关闭，不可反关闭");
            return;
        }
        if(cancellation == 1){
            tip("单据["+row.billNo+"]已作废，不可反关闭");
            return;
        }
        var closed = row.closed;
        if(closed == 0){
            tip("单据["+row.billNo+"]未关闭，不可反关闭");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tPoOrderController.do?openBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反关闭成功");
                $("#tPoOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//作废功能
function cancelBill(){
    var selected = $("#tPoOrderList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.state;
        var closed = row.closed;
        if(state == 1){
            tip("单据["+row.billNo+"]已在审核中，不可作废");
            return;
        }else if(state == 2){
            tip("单据["+row.billNo+"]已审核，不可作废");
            return;
        }
        if(closed == 1){
            tip("单据["+row.billNo+"]已关闭，不可作废");
            return;
        }
        if(cancellation == 1){
            tip("单据["+row.billNo+"]已作废，不可再作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tPoOrderController.do?cancelBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tPoOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反作废功能
function enableBill(){
    var selected = $("#tPoOrderList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.state;
        var closed = row.closed;

        if(cancellation == 0){
            tip("单据["+row.billNo+"]未作废，不可反作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tPoOrderController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tPoOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反审核前
function checkUnAudit(billId){
    var selected = $("#tPoOrderList").datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据进行反审核");
        return;
    }
    var rows = $("#tPoOrderList").datagrid("getRows");
    var id = selected.id;
    for(var i = 0 ; i<rows.length ; i++){
        var row = rows[i];
        var rowId = row.id;
        if(id == rowId){
            var stockQty = row.stockQty;
            if(parseFloat(stockQty) > 0){
                return "该单已被引用，不可反审核";
            }
        }
    }
    return false;
}