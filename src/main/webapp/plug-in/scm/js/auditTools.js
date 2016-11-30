/**
 * Created by LenovoM4550 on 2016-06-24.
 */
function checkIsAudit(tranType,load){
    if(tranType && load=="detail") {
        var billId = $("input[name='id']").val();
        var tableName = $("#tableName").val();
        var url = "tSAuditController.do?checkIsAudit&billId="+ billId +"&tranType="+tranType+"&load="+load;
        $.ajax({
            url: url,
            type: 'post',
            cache: false,
            success: function (d) {
                //var d = $.parseJSON(data);
                if (d.success) {
                    var attribute = d.attributes;
                    var cancellation = $("#cancellation").val();
                    var closed =  $("#closed").val();
                    var isStock = $("#isStock").val();
                    if("0" == cancellation || !cancellation){
                        cancellation = 0;
                    }
                    if("0" == closed || !closed){
                        closed = 0;
                    }
                    if("0" == isStock || !isStock){
                        isStock = 0;
                    }
                    if(!cancellation && !closed && !isStock) {
                        if(attribute) {
                            if(attribute.isAudit) {
                                if (attribute.isSub && attribute.isSub == "1") {
                                    $("#auditBtn").css("display", "inline");
                                    //document.getElementById('auditBtn').disabled = false;
                                    setTimeout("document.getElementById('auditBtn').disabled = false;", 800);
                                }
                                if (attribute.isBack && attribute.isBack == "1") {
                                    $("#unAuditBtn").css("display", "inline");
                                    //document.getElementById("unAuditBtn").disabled = false;
                                    setTimeout("document.getElementById('unAuditBtn').disabled = false;", 800);
                                }
                            } else {
                                $("#auditBtn").css("display","none");
                                $("#unAuditBtn").css("display","none");
                            }
                        }else{
                            var state = $("#checkState").val();
                            if(state == 0){
                                $("#auditBtn").css("display", "inline");
                                //document.getElementById('auditBtn').disabled = false;
                                setTimeout("document.getElementById('auditBtn').disabled = false;", 800);
                            }else if(state == 2){
                                $("#unAuditBtn").css("display", "inline");
                                //document.getElementById("unAuditBtn").disabled = false;
                                setTimeout("document.getElementById('unAuditBtn').disabled = false;", 800);
                            }
                        }
                    }
                }
            }
        });
        $("#auditBtn").bind("click",function(){
            var msg = checkAudit();
            if(msg){
                tip(msg,"审核");
                return ;
            }
            $.messager.confirm('确认对话框', '是否审核当前数据？', function(r) {
                if (r) {
                    $.ajax({
                        url: "tSAuditController.do?checkIsMore&tranType=" + tranType,
                        type: 'post',
                        cache: false,
                        success: function (d) {
                            if (d.success) {
                                var billNo = $("input[name='billNo']").val();
                                var billDate = $("input[name='date']").val();
                                billDate = billDate.substring(0, 10);
                                var addurl = "tSAuditController.do?goAudit&billId=" + billId + "&tranType=" + tranType + "&isSub=1&billNo=" + billNo + "&billDate=" + billDate + "&entityName=" + tableName;
                                addurl = encodeURI(projectName + addurl);
                                var width = width ? width : 700;
                                var height = height ? height : 400;
                                if (width == "100%" || height == "100%") {
                                    width = window.top.document.body.offsetWidth;
                                    height = window.top.document.body.offsetHeight - 100;
                                }
                                //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
                                if (windowapi == null) {
                                    $.dialog({
                                        content: 'url:' + addurl,
                                        lock: false,
                                        zIndex: 500,
                                        width: width,
                                        height: height,
                                        title: "单据审核",
                                        opacity: 0.3,
                                        cache: false,
                                        ok: function () {
                                            iframe = this.iframe.contentWindow;
                                            saveObj();
                                            setTimeout("afterAudit('"+billId+"');location.reload();", 2000);
                                            return false;
                                        },
                                        cancelVal: '关闭',
                                        cancel: true /*为true等价于function(){}*/
                                    }).zindex();
                                } else {
                                    W.$.dialog({
                                        content: 'url:' + addurl,
                                        lock: false,
                                        width: width,
                                        zIndex: 500,
                                        height: height,
                                        parent: windowapi,
                                        title: "单据审核",
                                        opacity: 0.3,
                                        cache: false,
                                        ok: function () {
                                            iframe = this.iframe.contentWindow;
                                            saveObj();
                                            return false;
                                        },
                                        cancelVal: '关闭',
                                        cancel: true /*为true等价于function(){}*/
                                    }).zindex();
                                }
                            } else {
                                //$.messager.confirm('确认对话框', '是否审核当前数据？', function (r) {
                                //    if (r) {
                                        $.ajax({
                                            url: "tSAuditController.do?auditBill&tranType=" + tranType + "&billId=" + billId + "&entityName=" + tableName,
                                            type: 'post',
                                            cache: false,
                                            success: function (d) {
                                                tip(d.msg);
                                                setTimeout("afterAudit('"+billId+"');location.reload();", 2000);
                                            }
                                        });
                                //    }
                                //});
                            }
                        }
                    });
                }
            });
        })
        $("#unAuditBtn").bind("click",function(){
            var msg = checkUnAudit();
            if(msg){
                tip(msg,"反审核");
                return ;
            }
            $.messager.confirm('确认对话框', '是否审核当前数据？', function(r) {
                if (r) {
                    $.ajax({
                        url: "tSAuditController.do?checkIsMore&tranType=" + tranType,
                        type: 'post',
                        cache: false,
                        success: function (d) {
                            if (d.success) {
                                var billNo = $("input[name='billNo']").val();
                                var billDate = $("input[name='date']").val();
                                var addurl = "tSAuditController.do?goAudit&billId=" + billId + "&tranType=" + tranType + "&isSub=0&billNo=" + billNo + "&billDate=" + billDate + "&entityName=" + tableName;
                                addurl = encodeURI(projectName + addurl);
                                var width = width ? width : 700;
                                var height = height ? height : 400;
                                if (width == "100%" || height == "100%") {
                                    width = window.top.document.body.offsetWidth;
                                    height = window.top.document.body.offsetHeight - 100;
                                }
                                //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
                                if (windowapi == null) {
                                    $.dialog({
                                        content: 'url:' + addurl,
                                        lock: false,
                                        zIndex: 500,
                                        width: width,
                                        height: height,
                                        title: "单据反审核",
                                        opacity: 0.3,
                                        cache: false,
                                        ok: function () {
                                            iframe = this.iframe.contentWindow;
                                            saveObj();
                                            var gridName = $("#gridName").val();
                                            $("#" + gridName).datagrid("reload");
                                            setTimeout("afterUnAudit('"+billId+"');location.reload();", 2000);
                                            return false;
                                        },
                                        cancelVal: '关闭',
                                        cancel: true /*为true等价于function(){}*/
                                    }).zindex();
                                } else {
                                    W.$.dialog({
                                        content: 'url:' + addurl,
                                        lock: false,
                                        width: width,
                                        zIndex: 500,
                                        height: height,
                                        parent: windowapi,
                                        title: "单据反审核",
                                        opacity: 0.3,
                                        cache: false,
                                        ok: function () {
                                            iframe = this.iframe.contentWindow;
                                            saveObj();
                                            afterUnAudit(billId);
                                            return false;
                                        },
                                        cancelVal: '关闭',
                                        cancel: true /*为true等价于function(){}*/
                                    }).zindex();
                                }
                            } else {
                                //$.messager.confirm('确认对话框', '是否反审核当前数据？', function (r) {
                                //    if (r) {
                                        $.ajax({
                                            url: "tSAuditController.do?unAuditBill&tranType=" + tranType + "&billId=" + billId + "&entityName=" + tableName,
                                            type: 'post',
                                            cache: false,
                                            success: function (d) {
                                                tip(d.msg);
                                                setTimeout("afterUnAudit('"+billId+"');location.reload();", 2000);
                                            }
                                        });
                                //    }
                                //});
                            }
                        }
                    });
                }
            });
        })
    }
}

function goAudit(name){
    var entityName = $("#entityName").val();
    var gridName = $("#gridName").val();
    var selected = $("#"+name).datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
    var billId = selected.id;
    var msg = checkAudit(billId);
    if(msg){
        tip(msg,"审核");
        return ;
    }
    var closed = selected.closed;
    var cancel = selected.cancellation;
    var state = selected.checkState;
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
    if(closed == 1 ){
        tip("单据["+selected.billNo+"]已关闭，不可审核");
        return;
    }
    if(cancel == 1){
        tip("单据["+selected.billNo+"]已作废，不可审核");
        return;
    }
    var tranType = $("#tranType").val();
    $.messager.confirm('确认对话框', '是否审核所选数据？', function(r) {
        if (r) {
            //判断是否多级审核
            $.ajax({
                url: "tSAuditController.do?checkIsMore&tranType="+tranType,
                type: 'post',
                cache: false,
                success: function (d) {
                    if (d.success) {
                        var billNo = selected.billNo;
                        var billDate = selected.date;
                        var addurl = "tSAuditController.do?goAudit&billId="+billId+"&tranType="+tranType+"&isSub=1&billNo="+billNo+"&billDate="+billDate+"&entityName="+entityName;
                        addurl = encodeURI(projectName + addurl);
                        var width = width ? width : 700;
                        var height = height ? height : 400;
                        if (width == "100%" || height == "100%") {
                            width = window.top.document.body.offsetWidth;
                            height = window.top.document.body.offsetHeight - 100;
                        }
                        //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
                        if (windowapi == null) {
                            $.dialog({
                                content: 'url:' + addurl,
                                lock: false,
                                zIndex: 500,
                                width: width,
                                height: height,
                                title: "单据审核",
                                opacity: 0.3,
                                cache: false,
                                ok: function () {
                                    iframe = this.iframe.contentWindow;
                                    saveObj();
                                    var gridName = $("#gridName").val();
                                    $("#"+gridName).datagrid("reload");
                                    //afterAudit(billId);
                                    setTimeout("afterAudit('"+billId+"');",1000);
                                    return false;
                                },
                                cancelVal: '关闭',
                                cancel: true /*为true等价于function(){}*/
                            }).zindex();
                        } else {
                            W.$.dialog({
                                content: 'url:' + addurl,
                                lock: false,
                                width: width,
                                zIndex: 500,
                                height: height,
                                parent: windowapi,
                                title: "单据审核",
                                opacity: 0.3,
                                cache: false,
                                ok: function () {
                                    iframe = this.iframe.contentWindow;
                                    saveObj();
                                    var gridName = $("#gridName").val();
                                    $("#"+gridName).datagrid("reload");
                                    //afterAudit(billId);
                                    setTimeout("afterAudit('"+billId+"');",1000);
                                    return false;
                                },
                                cancelVal: '关闭',
                                cancel: true /*为true等价于function(){}*/
                            }).zindex();
                        }
                    }else{
                        $.ajax({
                            url: "tSAuditController.do?auditBill&tranType=" + tranType+"&billId="+billId+"&entityName="+entityName,
                            type: 'post',
                            cache: false,
                            success: function (d) {
                                tip(d.msg);
                                $("#"+gridName).datagrid("reload");
                                afterAudit(billId);
                            }
                        });
                    }
                }
            });
        }
    });
}

/**
 * 反审核
 * @param billId  单据id
 * @param billNo  单据编号
 * @param billDate  单据日期
 * @param cancel  作废标记
 * @param isStock 被引用标记
 * @param close   关闭标记
 */
function goUnAudit(name){
    var entityName = $("#entityName").val();
    var selected = $("#"+name).datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
    var billId = selected.id;
    var msg = checkUnAudit(billId);
    if(msg){
        tip(msg,"反审核");
        return ;
    }
    var close = selected.closed;
    var cancel = selected.cancellation;
    var state = selected.checkState;
    var date = new Date(selected.date).getTime();
    var checkDate = $("#checkDate").val();
    var isStock = selected.isStock;
    var billNo = selected.billNo;
    var billDate = selected.date;
    if(checkDate){
        checkDate = new Date(checkDate).getTime();
        if(date < checkDate){
            tip("单据["+billNo+"]不在本期间内，不可反审核");
            return;
        }
    }
    if(cancel && parseInt(cancel) == 1){
        tip("单据["+billNo+"]已作废，不可反审核");
        return;
    }
    if(close && parseInt(close) == 1){
        tip("单据["+billNo+"]已关闭，不可反审核");
        return;
    }
    if(isStock && parseInt(isStock) == 1){
        tip("单据["+billNo+"]已被目标单据引用，不可反审核");
        return;
    }
    if(0 == state){
        tip("单据["+billNo+"]未审核，不可反审核");
        return;
    }
    var tranType = $("#tranType").val();
    $.messager.confirm('确认对话框', '是否反审核所选数据？', function(r) {
        if (r) {
            $.ajax({
                url: "tSAuditController.do?checkIsMore&tranType=" + tranType,
                type: 'post',
                cache: false,
                success: function (d) {
                    if (d.success) {
                        var addurl = "tSAuditController.do?goAudit&billId=" + billId + "&tranType=" + tranType + "&isSub=0&billNo=" + billNo + "&billDate=" + billDate+"&entityName="+entityName;
                        addurl = encodeURI(projectName + addurl);
                        var width = width ? width : 700;
                        var height = height ? height : 400;
                        if (width == "100%" || height == "100%") {
                            width = window.top.document.body.offsetWidth;
                            height = window.top.document.body.offsetHeight - 100;
                        }
                        //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
                        if (windowapi == null) {
                            $.dialog({
                                content: 'url:' + addurl,
                                lock: false,
                                zIndex: 500,
                                width: width,
                                height: height,
                                title: "单据反审核",
                                opacity: 0.3,
                                cache: false,
                                ok: function () {
                                    iframe = this.iframe.contentWindow;
                                    saveObj();
                                    var gridName = $("#gridName").val();
                                    $("#"+gridName).datagrid("reload");
                                    setTimeout("afterUnAudit('"+billId+"');",1000);
                                    return false;
                                },
                                cancelVal: '关闭',
                                cancel: true /*为true等价于function(){}*/
                            }).zindex();
                        } else {
                            W.$.dialog({
                                content: 'url:' + addurl,
                                lock: false,
                                width: width,
                                zIndex: 500,
                                height: height,
                                parent: windowapi,
                                title: "单据反审核",
                                opacity: 0.3,
                                cache: false,
                                ok: function () {
                                    iframe = this.iframe.contentWindow;
                                    saveObj();
                                    var gridName = $("#gridName").val();
                                    $("#"+gridName).datagrid("reload");
                                    setTimeout("afterUnAudit('"+billId+"');",1000);
                                    return false;
                                },
                                cancelVal: '关闭',
                                cancel: true /*为true等价于function(){}*/
                            }).zindex();
                        }
                    }else{
                        $.ajax({
                            url: "tSAuditController.do?unAuditBill&tranType=" + tranType+"&billId="+billId+"&entityName="+entityName,
                            type: 'post',
                            cache: false,
                            success: function (d) {
                                tip(d.msg);
                                var gridName = $("#gridName").val();
                                $("#"+gridName).datagrid("reload");
                                afterUnAudit(billId);
                            }
                        });
                    }
                }
            });
        }
    });
}

//审核前方法控制
function checkAudit(billId){
    return false;
}
//反审核前方法控制
function checkUnAudit(billId){
    return false;
}

function afterAudit(billId){
}

function afterUnAudit(billId){

}