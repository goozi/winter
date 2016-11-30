$(document).ready(function(){
    var flag = $("#flag").val();
    if(flag == "true"){
        $("[icon=new-icon-edit]").linkbutton("disable");
        $("[ icon=new-icon-unAudit]").linkbutton("disable");
    }
});
//关闭功能
function closeBill(){
    var selected = $("#tScDrpOrderList").datagrid("getSelections");
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
    var url = "tScDrpOrderController.do?closeBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("关闭成功");
                $("#tScDrpOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}
//反关闭功能
function openBill(){
    var selected = $("#tScDrpOrderList").datagrid("getSelections");
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
    var url = "tScDrpOrderController.do?openBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反关闭成功");
                $("#tScDrpOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//作废功能
function cancelBill(){
    var selected = $("#tScDrpOrderList").datagrid("getSelections");
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
    var url = "tScDrpOrderController.do?cancelBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tScDrpOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反作废功能
function enableBill(){
    var selected = $("#tScDrpOrderList").datagrid("getSelections");
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
    var url = "tScDrpOrderController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#tScDrpOrderList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//审核前
function checkAudit(){
    //选择记录s
    var selected = $("#tScDrpOrderList").datagrid("getSelected");
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
    if(closed == 1){
        tip("单据["+selected.billNo+"]已关闭，不可审核");
        return;
    }
}

/*//反审核前
function checkUnAudit(){
    //选择记录
    var selected = $("#tScDrpOrderList").datagrid("getSelected");
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
            tip("单据["+selected.billNo+"]不在本期间内，不可反审核");
            return;
        }
    }

    if(closed == 1){
     tip("单据["+selected.billNo+"]已关闭，不可反审核");
     return;
     }
}*/

//编辑页面跳转
function goUpdate() {
    var selected = $("#tScDrpOrderList").datagrid("getSelected");
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
    var url = "tScDrpOrderController.do?goUpdate";
    update("编辑", url, "tScDrpOrderList", null, null, "tab");
}

/**
 * 批量删除控制
 */
function deleteALLSelect(){
    var rows = $("#tScDrpOrderList").datagrid('getSelections');
    var ids="";
    if(rows.length > 0){
        for(var i in rows){
            var status = parseInt(rows[i].checkState);
            if(status == 1){
                tip("单据["+rows[0].billNo+"]已在审核中，不删除");
                return;
            }else if(status == 2){
                tip("单据["+rows[0].billNo+"]已审核，不可删除");
                return;
            }
            ids += rows[i].id+",";
        }
        if(ids.endsWith(",")){
            ids = ids.substring(ids.lastIndexOf(","),0);
        }
        $.dialog.confirm("确定删除所选记录？", function () {
            $.ajax({
                type: 'POST',
                url: 'tScDrpOrderController.do?doBatchDel',
                async: false,
                cache: false,
                data: {'ids':ids},
                dataType: 'json',
                success: function (data) {
                    if (data.success == true) {
                        $("#tScDrpOrderList").datagrid("reload");
                    }
                    tip(data.msg);
                }
            });
        });
    }else{
        tip("请选择需要删除的数据");
        return;
    }
}

//变更页面跳转
function goChange(){
    var selected = $("#tScDrpOrderList").datagrid("getSelected");
    if(null != selected) {
        var checkState = selected.checkState;
        if (checkState == 1) {
            tip("该单据正在审核中，不可变更");
            return;
        }
        if (checkState == 0) {
            tip("该单据未审核，不可变更");
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
    var url = "tScDrpOrderController.do?goUpdate&checkState="+checkState;
    update("变更", url, "tScDrpOrderList", null, null, "tab");
}

function checkUnAudit(){
    var rowsData = $('#tScDrpOrderList').datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
        tip('请选择要反审核项目');
        return;
    }
    if (rowsData.length > 1) {
        tip('请选择一条记录再反审核');
        return;
    }
    var sconId = rowsData[0].sonID;
    var sonId = $("#sonCompanyId").val();
    if(sconId != sonId){
        return "下级分销商订货单不允许被反审核";
    }
    //$.post("tScDrpOrderController.do?checkUnAduit",{sconId:sconId},function(data){
    //    if(data.success){
    //        goUnAudit('tScDrpOrderList');
    //    }else{
    //        tip(data.msg);
    //    }
    //})
}