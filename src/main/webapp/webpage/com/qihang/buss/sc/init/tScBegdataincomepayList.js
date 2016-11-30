//编辑页面跳转
function goUpdate(){

    var selected = $("#tScBegdataList").datagrid("getSelected");
    if(null != selected) {
        var checkState = selected.checkState;
        if (checkState == 1){
            tip("该单据["+selected.billNo+"]正在审核中，不可编辑");
            return;
        }else if(checkState == 2){
            tip("该单据["+selected.billNo+"]已审核，不可编辑");
            return;
        }
    }
    var url = "tScBegdataController.do?goUpdate";
    update("编辑", url, "tScBegdataList", null, null, "tab");
}



//审核前校验，判断是否已经开帐，如果是则审核不能操作
function checkAudit(){
    var isAccountOpen = $("#isAccountOpen").val();
    if (isAccountOpen=="true"){
        return "该单据已开账，不可审核，请确认";
    }
    else{
        return false;
    }

}
//反审核前，判断是否已经开帐，如果是则反审核不能操作
function checkUnAudit(){
    var isAccountOpen = $("#isAccountOpen").val();
    if (isAccountOpen=="true"){
        return "该单据已开账，不可反审核，请确认";
    }
    else{
        return false;
    }

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
    //
    //

    //todo:	单据审核后，不执行核算项目的应收账款增加或减少，而是等到系统结束初始化时才执行核算项目的应收账款增加或减少，同时反应在“应收账款汇总表”等相关报表中。
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
