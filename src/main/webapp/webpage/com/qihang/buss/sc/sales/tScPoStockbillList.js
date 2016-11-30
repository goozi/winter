//作废功能
function cancelBill(){
    var selected = $("#tScPoStockbillList").datagrid("getSelections");
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
        var date = new Date(row.date).getTime();
        var checkDate = $("#checkDate").val();
        checkDate = new Date(checkDate).getTime();
        if(date < checkDate){
            tip("单据["+row.billNo+"]不在本期间内，不可作废");
            return;
        }
        if(state == 1){
            tip("单据["+row.billNo+"]已在审核中，不可作废");
            return;
        }else if(state == 2){
            tip("单据["+row.billNo+"]已审核，不可作废");
            return;
        }
        if(cancellation == 1){
            tip("单据["+row.billNo+"]已作废，不可再作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tScPoStockbillController.do?cancelBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tScPoStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//删除
function doDel(id,billNo,date){
    date = new Date(date).getTime();
    var checkDate = $("#checkDate").val();
    checkDate = new Date(checkDate).getTime();
    if(date < checkDate){
        tip("单据["+billNo+"]不在本期间内，不可删除");
        return;
    }
    var url="tScPoStockbillController.do?doDel&id="+id
    $.dialog.confirm("确定删除该单据？",
        function () {
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success) {
                        tip("删除成功");
                        $("#tScPoStockbillList").datagrid("reload");
                    } else {
                        tip(data.msg);
                    }
                }
            });
        },function(){
            //
        }
    );
}

//反作废功能
function enableBill(){
    var selected = $("#tScPoStockbillList").datagrid("getSelections");
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
        var date = new Date(row.date).getTime();
        var checkDate = $("#checkDate").val();
        checkDate = new Date(checkDate).getTime();
        if(date < checkDate){
            tip("单据["+row.billNo+"]不在本期间内，不可反作废");
            return;
        }
        if(cancellation == 0){
            tip("单据["+row.billNo+"]未作废，不可反作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tScPoStockbillController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#tScPoStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//审核后执行
function afterAudit(billId){
    var tranType = $("#tranType").val();
    var select = $("#tScPoStockbillList").datagrid("getSelected");
    var id = billId;
    var url = "tScPoStockbillController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
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
function afterUnAudit(billId){
    var tranType = $("#tranType").val();
    var url = "tScPoStockbillController.do?afterAudit&audit=0&id=" + billId+"&tranType="+tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}