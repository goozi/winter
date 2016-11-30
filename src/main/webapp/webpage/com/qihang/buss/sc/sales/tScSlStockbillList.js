//销售出库同步商城状态为已发货
function sysStockState(){
    var selected = $("#tScSlStockbillList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids="";
    var mallorders="";
    var bool = false;
    for(var i=0 ; i<selLen ; i++) {
        var row = selected[i];
        if(row.checkState == 0){
            boool=true;
        }
        if(row.mallorderid != "" || row.mallorderid != "undefined"){
            ids = ids + row.id;
            mallorders = mallorders + row.mallorderid;
        }
    }
    if(bool){
        tip("请选择审核通过在同步");
        return;
    }
    if(mallorders == ""){
        tip("请选择要同步的商城订单");
        return;
    }
    $.ajax({
        url: "stockbillInterController.do?synStockstate",
        data:{ids:ids,mallorders:mallorders},
        dataType: 'json',
        cache: false,
        success: function (data) {
            if (data.success) {
                tip(data.msg);
                $("#tScSlStockbillList").datagrid("reload");
            } else {
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
    if(checkDate && date < checkDate){
        tip("单据["+billNo+"]不在本期间内，不可删除");
        return;
    }
    var url="tScSlStockbillController.do?doDel&id="+id
    $.dialog.confirm("确定删除该单据？",
        function () {
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success) {
                        tip("删除成功");
                        $("#tScSlStockbillList").datagrid("reload");
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

//作废功能
function cancelBill(){
    var selected = $("#tScSlStockbillList").datagrid("getSelections");
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
    var url = "tScSlStockbillController.do?cancelBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tScSlStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反作废功能
function enableBill(){
    var selected = $("#tScSlStockbillList").datagrid("getSelections");
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
    var url = "tScSlStockbillController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#tScSlStockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//审核后执行
function afterAudit(billId){
    var tranType = $("#tranType").val();
    var select = $("#tScSlStockbillList").datagrid("getChecked");
    var id = billId;
    var url = "tScSlStockbillController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
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
    var url = "tScSlStockbillController.do?afterAudit&audit=0&id=" + billId+"&tranType="+tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}
//物流信息
function goLogistical(){
    var selected  = $("#tScSlStockbillList").datagrid("getChecked");
    if(selected.length == 0){
        tip("请选择一条数据");
        return;
    }
    if(selected.length > 1){
        tip("请选择一条数据");
        return;
    }
    var id = selected[0].id;
    var tranType = selected[0].tranType;
    var checkState = selected[0].checkState;
    var url = 'tScSlStockbillController.do?goLogisticalUpdate&fid=' + id+"&tranType="+tranType+"&tableName=t_sc_sl_stockbill&load=detail";
    if(checkState > 0){
        url += "&load=detail";
    }
    var width = 600;
    var height = 250;
    var title = "物流信息";
    $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        zIndex: 500,
        height: height,
        cache: false,
        width: width,
        opacity: 0.3,
        button: [{
            name: '确定',
            callback: function () {
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}