//作废功能
function cancelBill(){
    var selected = $("#vScIcInitialList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.checkstate;
        //var closed = row.closed;
        if(state == 1){
            tip("单据["+row.billno+"]已在审核中，不可作废");
            return;
        }else if(state == 2){
            tip("单据["+row.billno+"]已审核，不可作废");
            return;
        }
        //if(closed == 1){
        //    tip("单据["+row.billNo+"]已关闭，不可作废");
        //    return;
        //}
        if(cancellation == 1){
            tip("单据["+row.billno+"]已作废，不可再作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tScIcInitialController.do?cancelBill";

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#vScIcInitialList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反作废功能
function enableBill(){
    var selected = $("#vScIcInitialList").datagrid("getSelections");
    var selLen = selected.length;
    if(selLen == 0){
        tip("请选择一条数据");
        return;
    }
    var ids = "";
    for(var i=0 ; i<selLen ; i++){
        var row = selected[i];
        var cancellation = row.cancellation;
        var state = row.checkstate;
        //var closed = row.closed;

        if(cancellation == 0){
            tip("单据["+row.billno+"]未作废，不可反作废");
            return;
        }
        ids += row.id + ",";
    }
    var url = "tScIcInitialController.do?enableBill"

    $.ajax({
        url: url,
        dataType: 'json',
        data:{ids:ids},
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#vScIcInitialList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反审核前
function checkUnAudit(billId){
    var isCheckNegative = $("#isCheckNegative").val();
    if(isCheckNegative) {
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
            url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=t_sc_po_orderentry&parentId=fid",
            type: "POST",
            dataType: "json",
            success: function (data) {
                if(!data.success){
                    isContinue = false;
                    //itemName = data.msg;
                }
            }
        })
        if (isContinue) {
            return false;
        } else {
            //itemName = itemName.substring(0, itemName.length - 1);
            return "该单据反审核后将出现负库存，不可反审核，请确认";
        }
    }else{
        return false;
    }
}

//审核前校验
function checkAudit(){
    //存货初始化不验证审核后出现负库存
    /*var isCheckNegative = $("#isCheckNegative").val();
     if(isCheckNegative) {
     var tranType = $("#tranType").val();
     if("53" == tranType){
     var isContinue = true;
     var itemName = "";
     var billId = $("#id").val();
     $.ajax({
     async: false,
     url: "tSAuditController.do?checkIsNegative&id=" + billId+"&tranType="+tranType+"&tableName=T_SC_IC_InitiallEntry&parentId=fid",
     type: "POST",
     dataType: "json",
     success: function (data) {
     if(!data.success){
     isContinue = false;
     //itemName = data.msg;
     }
     }
     })
     if (isContinue) {
     return false;
     } else {
     //itemName = itemName.substring(0, itemName.length - 1);
     return "该单据审核后将出现负库存，不可审核，请确认";
     }
     }else{
     return false;
     }
     }else{
     return false;
     }*/
    //需要判断作废单单据或已审核单据不允许审核；还需要判断当前主体单位是否已经系统开帐，如果开帐了就不允许进行审核，页面上已经公共功能进行按钮控制。
    return false;
}

//审核后执行
function afterAudit(){
    //var tranType = $("#tranType").val();
    //var id = $("#id").val();
    //var url = "tScIcInitialController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
    //$.ajax({
    //	url: url,
    //	type: 'post',
    //	cache: false,
    //	success: function (d) {
    //		//
    //	}
    //});
    return false;
}

//反审核后执行
function afterUnAudit(billId){
    //var tranType = $("#tranType").val();
    //var url = "tScIcInitialController.do?afterAudit&audit=0&id=" + billId+"&tranType="+tranType;
    //$.ajax({
    //	url: url,
    //	type: 'post',
    //	cache: false,
    //	success: function (d) {
    //		//
    //	}
    //});
    return false;
}

//编辑页面跳转
function goUpdate(){
    var selected = $("#vScIcInitialList").datagrid("getSelected");
    if(null != selected) {
        //var isEdit = $("#isEdit").val();
        var checkState = selected.checkstate;
        if (checkState == 1){// && !isEdit) {
            tip("该单据["+selected.billno+"]正在审核中，不可编辑");
            return;
        }else if(checkState == 2){// && !isEdit){
            tip("该单据["+selected.billno+"]已审核，不可编辑");
            return;
        }

        var cancellation = selected.cancellation;
        if(cancellation == 1){
            tip("该单据["+selected.billno+"]已作废，不可编辑");
            return;
        }
    }
    var url = "tScIcInitialController.do?goUpdate";
    update("编辑", url, "vScIcInitialList", null, null, "tab");
}