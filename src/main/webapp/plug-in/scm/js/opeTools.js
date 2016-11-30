/**
 * Created by LenovoM4550 on 2016-08-24.
 * 单据操作公用方法
 */

//关闭操作
function closeBillInfo(){
    var gridName = $("#gridName").val();
    var tableName = $("#entityName").val();
    var selected = $("#"+gridName).datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据进行关闭");
        return;
    }
    var id = selected.id;
    var cancellation = selected.cancellation;
    var state = selected.checkState;
    var closed = selected.closed;
    if(closed == 1){
        tip("单据["+selected.billNo+"]已关闭，不可重复关闭");
        return;
    }
    if(state < 2){
        tip("单据["+selected.billNo+"]未审核完毕，不可关闭");
        return;
    }
    if(cancellation == 1){
        tip("单据["+selected.billNo+"]已作废，不可关闭");
        return;
    }
    var url = "scBaseController.do?opeBill&tableName="+tableName+"&id="+id+"&value=1&field=closed";
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (data) {
            if(data.success){
                tip("关闭成功");
                $("#"+gridName).datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反关闭操作
function unCloseBillInfo(){
    var gridName = $("#gridName").val();
    var tableName = $("#entityName").val();
    var selected = $("#"+gridName).datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据进行反关闭");
        return;
    }
    var id = selected.id;
    var autoFlag = selected.autoFlag;
    var cancellation = selected.cancellation;
    var closed = selected.closed;
    if(autoFlag == 1){
        tip("单据["+selected.billNo+"]已自动关闭，不可反关闭");
        return;
    }
    if(cancellation == 1){
        tip("单据["+selected.billNo+"]已作废，不可反关闭");
        return;
    }
    if(closed == 0){
        tip("单据["+selected.billNo+"]未关闭，不可反关闭");
        return;
    }
    var url = "scBaseController.do?opeBill&tableName="+tableName+"&id="+id+"&value=0&field=closed";
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反关闭成功");
                $("#"+gridName).datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//作废操作
function cancellBillInfo(){
    var gridName = $("#gridName").val();
    var tableName = $("#entityName").val();
    var selected = $("#"+gridName).datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据进行作废");
        return;
    }
    var id = selected.id;
    var autoFlag =selected.autoFlag;
    var cancellation = selected.cancellation;
    var closed = selected.closed;
    var status = selected.checkState;
    var tranType = selected.tranType;
    if(status == 1){
        tip("单据["+selected.billNo+"]已在审核中，不可作废");
        return;
    }else if(status == 2){
        tip("单据["+selected.billNo+"]已审核，不可作废");
        return;
    }
    if(closed == 1 ||autoFlag == 1){
        tip("单据["+selected.billNo+"]已关闭，不可作废");
        return;
    }
    if(cancellation == 1){
        tip("单据["+selected.billNo+"]已作废");
        return;
    }
    var url = "scBaseController.do?opeBill&tableName="+tableName+"&id="+id+"&value=1&field=cancellation&tranType="+tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (data) {
            if(data.success){
                tip("作废成功");
                $("#"+gridName).datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//反作废操作
function unCancellBillInfo(){
    var gridName = $("#gridName").val();
    var tableName = $("#entityName").val();
    var selected = $("#"+gridName).datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据进行反作废");
        return;
    }
    var id = selected.id;
    var cancellation = selected.cancellation;
    var tranType = selected.tranType;
    if(cancellation == 0){
        tip("单据["+selected.billNo+"]未作废，不可反作废");
        return;
    }
    var url = "scBaseController.do?opeBill&tableName="+tableName+"&id="+id+"&value=0&field=cancellation&tranType="+tranType;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (data) {
            if(data.success){
                tip("反作废成功");
                $("#"+gridName).datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

$(function(){
    $(".layout-button-up").click(toggleCenter);
    $(".layout-button-down").click(toggleCenter);

})
function toggleCenter(){
    var centerBodyHigh = $("#centerBody").height();
    if($("#centerBody").is(":hidden")){
        $("#centerBody").slideDown();
        $(".datagrid-view1 .datagrid-body").height($(".datagrid-view1 .datagrid-body").height()-centerBodyHigh-20);
        $(".datagrid-view2 .datagrid-body").height($(".datagrid-view2 .datagrid-body").height()-centerBodyHigh-20);
        $(".datagrid-view").height($(".datagrid-view").height()-centerBodyHigh-20);
    } else {
        $("#centerBody").slideUp();
        $(".datagrid-view1 .datagrid-body").height($(".datagrid-view1 .datagrid-body").height()+centerBodyHigh+20);
        $(".datagrid-view2 .datagrid-body").height($(".datagrid-view2 .datagrid-body").height()+centerBodyHigh+20);
        $(".datagrid-view").height($(".datagrid-view").height()+centerBodyHigh+20);
    }
}
