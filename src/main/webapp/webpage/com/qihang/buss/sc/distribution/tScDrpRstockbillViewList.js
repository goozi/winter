//审核前
function checkAudit(){
    //选择记录
    var selected = $("#tScDrpRstockbillViewList").datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
    var cancel = selected.cancellation;
    var state = selected.checkState;
    //var closed = selected.closed;
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