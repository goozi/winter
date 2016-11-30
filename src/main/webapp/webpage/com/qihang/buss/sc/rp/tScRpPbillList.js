function amountFormatter(v){
    var CFG_MONEY = $("#CFG_MONEY").val();
    v = parseFloat(v).toFixed(CFG_MONEY);
    return v;
}

function afterAudit(){
    $("#tScRpPbillList").datagrid("reload");
}

function afterUnAudit(){
    $("#tScRpPbillList").datagrid("reload");
}