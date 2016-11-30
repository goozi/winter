function AutoChk(){
    $.ajax({
        async: false,
        url: "tScIcChkstockbillController.do?checkIsAudit",
        type: "POST",
        dataType: "json",
        success: function (data) {
            if(data.success){
                goAutoChk();
            }else{
                goAuditInfo();
                tip("存在未审核单据");
            }
        }
    })
}


function goAutoChk(){
    var url = "tScIcChkstockbillController.do?goSearchDialog";
    var width = 400;
    var height = 350;
    var title = "盘点";
    $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        zIndex: 600,
        height: height,
        cache: false,
        width: width,
        opacity: 0.3,
        button: [{
            name: '确定',
            callback: function () {
                iframe = this.iframe.contentWindow;
                var info = iframe.getSelectionData();
                $.ajax({
                    async: false,
                    data:{
                        calType : info.calType,
                        date : info.date,
                        stockInfo : info.stockInfo,
                        itemInfo : info.itemInfo,
                        maxNum : info.maxNum,
                        isZero : info.isZero,
                        empId : info.empId,
                        deptId : info.deptId
                    },
                    url: "tScIcChkstockbillController.do?autoChk",
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        if(data.success){
                            tip(data.msg);
                            $("#tScIcChkstockbillList").datagrid("reload");
                        }
                    }
                })
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}

function goAuditInfo(){
    var url = "tScIcChkstockbillController.do?goAuditInfoDialog";
    var width = 400;
    var height = 350;
    var title = "未审核单据";
    $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        zIndex: 600,
        height: height,
        cache: false,
        width: width,
        opacity: 0.3,
        button: [{
            name: '确定',
            callback: function () {
                iframe = this.iframe.contentWindow;
                var infoList = iframe.getSelectionData();
                debugger;
                for(var i = 0 ; i < infoList.length ; i++) {
                    var info = infoList[i];
                    var tranType = info.tranType;
                    var preTitle = "";
                    if (tranType == "52" || tranType == "53") {
                        if (tranType == "52") {
                            preTitle = "采购入库单";
                        } else if (tranType == "53") {
                            preTitle = "采购退货单"
                        }
                        url = 'tScPoStockbillController.do?goUpdate' + '&load=detail&tranType=' + tranType + '&id=' + info.id;
                    } else if (tranType == "61") {
                        preTitle = "采购换货单"
                        url = 'tScIcJhstockbillController.do?goUpdate' + '&load=detail&id=' + info.id;
                    } else if (tranType == "103" || tranType == "104") {
                        if (tranType == "103") {
                            preTitle = "销售出库单";
                        } else if (tranType == "104") {
                            preTitle = "销售退货单";
                        }
                        url = 'tScSlStockbillController.do?goUpdate' + '&load=detail&id=' + info.id;
                    } else {
                        preTitle = "销售换货单"
                        url = 'tScIcXsstockbillController.do?goUpdate' + '&load=detail&id=' + info.id;
                    }
                    createdetailwindow('查看', url, null, null, "tab", preTitle);
                }
            },
            focus: true
        }, {
            name: '取消',
            callback: function () {
            }
        }]
    }).zindex();
}

function goAdd(){
    $.ajax({
        async: false,
        url: "tScIcChkstockbillController.do?checkIsAudit",
        type: "POST",
        dataType: "json",
        success: function (data) {
            if(data.success){
                add("盘点单新增", "tScIcChkstockbillController.do?goAdd&tranType=160", "tScIcChkstockbillList", 100, 100, "tab");
            }else{
                goAuditInfo();
                tip("存在未审核单据");
            }
        }
    })
}

function afterAudit(billId){
    var tranType = $("#tranType").val();
    var select = $("#tScIcChkstockbillList").datagrid("getSelected");
    var id = billId;
    var url = "tScIcChkstockbillController.do?afterAudit&audit=1&id=" + id+"&tranType="+tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}

function afterUnAudit(billId){
    var tranType = $("#tranType").val();
    var url = "tScIcChkstockbillController.do?afterAudit&audit=0&id=" + billId+"&tranType="+tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
            //
        }
    });
}

function showColor(v,r,i){
    if(v < 0){
        return "color:red";
    }
}