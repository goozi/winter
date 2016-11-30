//销售订单同步
function sysOrder(){
    $.messager.progress({
        text: '正在同步销售订单数据,请耐心等待....',
        interval: 100
    });
    $.ajax({
        url: "orderInterController.do?synOrder",
        dataType: 'json',
        cache: false,
        success: function (data) {
            if (data.success) {
                tip(data.msg);
                $("#tScOrderList").datagrid("reload");
                $.messager.progress('close');
            } else {
                tip(data.msg);
                $.messager.progress('close');
            }
        }
    });

}
/***
 * 关闭
 */
function closeOrderBill(){
    var rows = $("#tScOrderList").datagrid('getSelections');
    if(rows.length > 0){
        var autoFlag = rows[0].autoFlag;
        var status = rows[0].status;
        var cancellation = rows[0].cancellation;
        var closed = rows[0].closed;
        if(autoFlag == 1){
            tip("单据["+rows[0].billNo+"]已自动关闭");
            return;
        }
        if(closed==1){
            tip("单据["+rows[0].billNo+"]已关闭");
            return;
        }
        if(cancellation == 1){
            tip("单据["+rows[0].billNo+"]已作废，不可关闭");
            return;
        }
        if(status < 2){
            tip("单据["+rows[0].billNo+"]未审核完毕，不可关闭");
            return;
        }
        $.ajax({
            type: 'POST',
            url: 'tScOrderController.do?closeOrderBill',
            async: false,
            cache: false,
            data: {'id': rows[0].id,'type':'1'},
            dataType: 'json',
            success: function (data) {
                if (data.success == true) {
                    $("#tScOrderList").datagrid("reload");
                }
                tip(data.msg);
            }
        });

    }else{
        tip("请选择需要关闭的数据");
    }
}

/***
 * 反关闭
 */
function oppositeCloseOrderBill(){
    var rows = $("#tScOrderList").datagrid('getSelections');
    if(rows.length > 0){
        var autoFlag = rows[0].autoFlag;
        var cancellation = rows[0].cancellation;
        var closed = rows[0].closed;
        if(autoFlag == 1){
            tip("单据["+rows[0].billNo+"]已自动关闭，不可反关闭");
            return;
        }
        if(cancellation == 1){
            tip("单据["+rows[0].billNo+"]已作废，不可反关闭");
            return;
        }
        if(closed == 0){
            tip("单据["+rows[0].billNo+"]未关闭，不可反关闭");
            return;
        }
        $.ajax({
            type: 'POST',
            url: 'tScOrderController.do?closeOrderBill',
            async: false,
            cache: false,
            data: {'id': rows[0].id,'type':'2'},
            dataType: 'json',
            success: function (data) {
                if (data.success == true) {
                    $("#tScOrderList").datagrid("reload");
                }
                tip(data.msg);
            }
        });

    }else{
        tip("请选择需要反关闭的数据");
    }
}
/***
 * 作废
 */
function cancelBill(){
    var rows = $("#tScOrderList").datagrid('getSelections');
    if(rows.length > 0){
        var autoFlag = rows[0].autoFlag;
        var cancellation = rows[0].cancellation;
        var closed = rows[0].closed;
        var status = rows[0].status;
        if(status == 1){
            tip("单据["+rows[0].billNo+"]已在审核中，不可作废");
            return;
        }else if(status == 2){
            tip("单据["+rows[0].billNo+"]已审核，不可作废");
            return;
        }
        if(closed == 1 ||autoFlag == 1){
            tip("单据["+rows[0].billNo+"]已关闭，不可作废");
            return;
        }
        if(cancellation == 1){
            tip("单据["+rows[0].billNo+"]已作废");
            return;
        }
        $.ajax({
            type: 'POST',
            url: 'tScOrderController.do?cancelBill',
            async: false,
            cache: false,
            data: {'id': rows[0].id,'type':'1'},
            dataType: 'json',
            success: function (data) {
                if (data.success == true) {
                    $("#tScOrderList").datagrid("reload");
                }
                tip(data.msg);
            }
        });

    }else{
        tip("请选择需要作废的数据");
    }
}

/**
 *反作废
 */
function enableBill(){
    var rows = $("#tScOrderList").datagrid('getSelections');
    if(rows.length > 0){
        var cancellation = rows[0].cancellation;
        if(cancellation == 0){
            tip("单据["+rows[0].billNo+"]未作废，不可反作废");
            return;
        }
        $.ajax({
            type: 'POST',
            url: 'tScOrderController.do?cancelBill',
            async: false,
            cache: false,
            data: {'id': rows[0].id,'type':'2'},
            dataType: 'json',
            success: function (data) {
                if (data.success == true) {
                    $("#tScOrderList").datagrid("reload");
                }
                tip(data.msg);
            }
        });
    }else{
        tip("请选择需要反作废的数据");
    }
}

/***
 * 修改
 */
function updateOrder(title, url, id, width, height, windowType){
    gridname = id;
    var rowsData = $('#' + id).datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length > 1) {
        tip('请选择一条记录再编辑');
        return;
    }
    if(rowsData[0].cancellation == 1){
        tip("单据["+rowsData[0].billNo+"]已作废,不可修改");
        return;
    }
    if(rowsData[0].checkState > 0){
        tip("只能是未审核的单据才能修改");
        return;
    }

    url += '&id=' + rowsData[0].id;
    if (windowType == 'tab') {
        var tranType = $("#tranType").val();
        addOneTabForBiz(title, url);
    } else {
        createwindow(title, url, width, height);
    }
}

function goChange(title, url, id, width, height, windowType){
    var selected = $("#tScOrderList").datagrid("getSelected");
    if(null != selected) {
        var checkStatus = selected.checkState;
        if (checkStatus < 2) {
            tip("该单据未审核完成，不可变更");
            return;
        }
        var closed = selected.closed;
        if(closed == 1){
            tip("该单据已关闭，不可变更");
            return;
        }
        var cancellation = selected.cancellation;
        if(cancellation == 1){
            tip("该单据已作废，不可变更");
            return;
        }
    }
     url = url+"&pageType="+1;//1是变更
    update(title, url, id, width, height, windowType);

}

function checkAudit(){
    var checked = $("#tScOrderList").datagrid("getSelections");
    if(checked.length == 0){
        tip("请选择一条数据");
        return;
    }else if(checked.length > 1){
        tip("请选择唯一数据");
        return;
    }
    var method = $("#CFG_CONTROL_METHOD").val();
    var timePoint = $("#CFG_CONTROL_TIMEPOINT").val();
    var isAllowAudit = checked[0].isAllowAudit;
    if("N" == isAllowAudit){
        if("1" == timePoint){
            if("1" == method){
                return "该单据金额已超出该客户的信用额度，不可审核，请确认";
            }
        }
    }
}

function afterAudit(billId){
    var gridName = $("#entityName").val();
    $("#"+gridName).datagrid("reload");
}

//反审核前校验
function checkUnAudit(){
    var selected = $("#tScOrderList").datagrid("getSelected");
    if(selected){
        var rows = $("#tScOrderList").datagrid("getRows");
        for(var i=0 ; i<rows.length ; i++) {
            var row = rows[i];
            if(selected.id == row.id && parseFloat(row.stockQty) > 0){
                return "该单据已被其他单据引用，不可反审核";
            }
        }
    }
    return false;
}