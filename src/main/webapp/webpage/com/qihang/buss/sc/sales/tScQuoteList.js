//编辑页面跳转
function goUpdate(){
    var selected = $("#tScQuoteList").datagrid("getSelected");
    if(null != selected) {
        var isEdit = $("#isEdit").val();
        var checkState = selected.checkState;
    //    var date = new Date(selected.date).getTime();
    //<%--var checkDate = new Date('${checkDate}').getTime();--%>
    //    <%--if(date<checkDate){--%>
    //    <%--tip("该单据未在本期间内，不可编辑");--%>
    //    <%--return;--%>
    //    <%--}--%>

        if (checkState == 1 && !isEdit) {
            tip("该单据正在审核中，不可编辑");
            return;
        }
        if (checkState == 2 && !isEdit) {
            tip("该单据已审核，不可编辑");
            return;
        }
//         var closed = selected.closed;
//         if(closed == 1){
//             tip("该单据已关闭，不可编辑");
//             return;
//         }
//        var cancellation = selected.cancellation;
//        if(cancellation == 1){
//            tip("该单据已作废，不可编辑");
//            return;
//        }
    }
    var url = "tScQuoteController.do?goUpdate";
    update("编辑", url, "tScQuoteList", null, null, "tab");
}
//变更
function goChange(){
    var selected = $("#tScQuoteList").datagrid("getSelected");
    if(null != selected) {
        var isEdit = $("#isEdit").val();
        var checkState = selected.checkState;
        //    var date = new Date(selected.date).getTime();
        //<%--var checkDate = new Date('${checkDate}').getTime();--%>
        //    <%--if(date<checkDate){--%>
        //    <%--tip("该单据未在本期间内，不可编辑");--%>
        //    <%--return;--%>
        //    <%--}--%>

        if (checkState == 1 && !isEdit) {
            tip("该单据正在审核中，不可变更");
            return;
        }
        if (checkState == 0 && !isEdit) {
            tip("该单据未审核，不可变更");
            return;
        }
//         var closed = selected.closed;
//         if(closed == 1){
//             tip("该单据已关闭，不可编辑");
//             return;
//         }
//        var cancellation = selected.cancellation;
//        if(cancellation == 1){
//            tip("该单据已作废，不可编辑");
//            return;
//        }
    }
    var url = "tScQuoteController.do?goUpdate";
    update("变更", url, "tScQuoteList", null, null, "tab");
}

function checkAudit(){
    var selected = $("#tScQuoteList").datagrid("getSelected");
    if(null != selected) {
        if(2 == selected.checkState){
            return "该单据已审核，不可重复审核，请确认";
        }
    }else{
        return "请选择一条数据"
    }
}