/*$(document).ready(function(){
    var flag = $("#flag").val();
    if(flag == "true"){
        $("[icon=new-icon-edit]").linkbutton("disable");
        $("[ icon=new-icon-unAudit]").linkbutton("disable");
    }
});*/
//关闭功能
function closeBill(){
    var selected = $("#tScDrpStockbillList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.checkState;
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
    var url = "tScDrpStockbillController.do?closeBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("关闭成功");
                $("#tScDrpStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}
//反关闭功能
function openBill(){
    var selected = $("#tScDrpStockbillList").datagrid("getSelections");
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
    var url = "tScDrpStockbillController.do?openBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反关闭成功");
                $("#tScDrpStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

/*//作废功能
function cancelBill(){
    var selected = $("#tScDrpStockbillList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.checkState;
        //var closed = row.closed;
        if(state == 1){
            tip("单据["+row.billNo+"]已在审核中，不可作废");
            return;
        }else if(state == 2){
            tip("单据["+row.billNo+"]已审核，不可作废");
            return;
        }
        /!*if(closed == 1){
            tip("单据["+row.billNo+"]已关闭，不可作废");
            return;
        }*!/
        if(cancellation == 1){
            tip("单据["+row.billNo+"]已作废，不可再作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tScDrpStockbillController.do?cancelBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tScDrpStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反作废功能
function enableBill(){
    var selected = $("#tScDrpStockbillList").datagrid("getSelections");
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
    var url = "tScDrpStockbillController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#tScDrpStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}*/

//审核前
function checkAudit(){
    //选择记录
    var selected = $("#tScDrpStockbillList").datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
    var cancel = selected.cancellation;
    var state = selected.checkState;
    var closed = selected.closed;
    var date = new Date(selected.date).getTime();
    var checkDate = $("#checkDate").val();
    if(checkDate){
        checkDate = new Date(checkDate).getTime();
        if(date < checkDate){
            tip("单据["+selected.billNo+"]不在本期间内，不可审核");
            return;
        }
    }
    if(state == 2){
        tip("单据["+selected.billNo+"]已审核完毕，不可重复审核");
        return;
    }
    if(cancel == 1){
        tip("单据["+selected.billNo+"]已作废，不可审核");
        return;
    }
    /*if(closed == 1){
     tip("单据["+selected.billNo+"]已关闭，不可审核");
     return;
     }*/
}

//审核后执行
function afterAudit(billId){
    var select = $("#tScDrpStockbillList").datagrid("getSelected");
    var id = billId;
    var url = "tScDrpStockbillController.do?afterAudit&audit=1&id=" + id;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}

//反审核后执行
function afterUnAudit(id){
    //var select = $("#tScDrpStockbillList").datagrid("getSelected");
    // var id = select.id;
    var url = "tScDrpStockbillController.do?afterAudit&audit=0&id=" + id;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}

//编辑页面跳转
function goUpdate() {
    var selected = $("#tScDrpStockbillList").datagrid("getSelected");
    if (null != selected) {
        var isEdit = $("#isEdit").val();
        var checkState = selected.checkState;

        if (checkState == 1 && !isEdit) {
            tip("该单据正在审核中，不可编辑");
            return;
        }
        if (checkState == 2 && !isEdit) {
            tip("该单据已审核，不可编辑");
            return;
        }
    }
    var url = "tScDrpStockbillController.do?goUpdate";
    update("编辑", url, "tScDrpStockbillList", null, null, "tab");
}