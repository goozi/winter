//作废功能
function cancelBill(){
    var selected = $("#tScIcXsstockbillList").datagrid("getSelections");
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
    var url = "tScIcXsstockbillController.do?cancelBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#tScIcXsstockbillList").datagrid("reload");
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
    var url="tScIcXsstockbillController.do?doDel&id="+id
    $.dialog.confirm("确定删除该单据？",
        function () {
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success) {
                        tip("删除成功");
                        $("#tScIcXsstockbillList").datagrid("reload");
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
    var selected = $("#tScIcXsstockbillList").datagrid("getSelections");
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
    var url = "tScIcXsstockbillController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#tScIcXsstockbillList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}


//审核后执行
function afterAudit(billId){
    var select = $("#tScIcXsstockbillList").datagrid("getSelected");
    var id = billId;
    var url = "tScIcXsstockbillController.do?afterAudit&audit=1&id=" + id;
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
    //var select = $("#tScIcXsstockbillList").datagrid("getSelected");
    // var id = select.id;
    var url = "tScIcXsstockbillController.do?afterAudit&audit=0&id=" + id;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}

//审核前校验
function checkAudit(billId){
    var isCheckNegative = $("#isCheckNegative").val();
    if(isCheckNegative == "true") {
        var tranType = $("#tranType").val();
        var isContinue = true;
        var itemName = "";
        /**
         *参数说明
         * id 单据id
         * tranTyoe 单据类型
         * tableName 明细表名
         * parentId 关联id
         */
        $.ajax({
            async: false,
            url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=t_sc_ic_xsstockbillentry2&parentId=fid",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if(!data.success){
                    isContinue = false;
                    itemName = data.msg;
                }
            }
        })
        if (isContinue) {
            return false;
        } else {
            itemName = itemName.substring(0, itemName.length - 1);
            return "该单据审核后,商品【"+itemName+"】将出现负库存，不可审核，请确认";
        }
    }else{
        return false;
    }
}

//审核前校验
function checkUnAudit(billId){
    var isCheckNegative = $("#isCheckNegative").val();
    if(isCheckNegative == "true") {
        var tranType = $("#tranType").val();
        var isContinue = true;
        var itemName = "";
        /**
         *参数说明
         * id 单据id
         * tranTyoe 单据类型
         * tableName 明细表名
         * parentId 关联id
         */
        $.ajax({
            async: false,
            url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=t_sc_ic_xsstockbillentry1&parentId=fid",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if(!data.success){
                    isContinue = false;
                    itemName = data.msg;
                }
            }
        })
        if (isContinue) {
            return false;
        } else {
            itemName = itemName.substring(0, itemName.length - 1);
            return "该单据反审核后,商品【"+itemName+"】将出现负库存，不可反审核，请确认";
        }
    }else{
        return false;
    }
}

//物流信息
function goLogistical(){
    var selected  = $("#tScIcXsstockbillList").datagrid("getChecked");
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
    var url = 'tScSlStockbillController.do?goLogisticalUpdate&fid=' + id+"&tranType="+tranType+"&tableName=t_sc_ic_xsstockbill&load=detail";
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