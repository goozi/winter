//确认
function goConfirm() {
    var selected = $("#tScDrpRecepitConfimStockbillList").datagrid("getSelected");
    if (selected == null) {
        tip("请选择一条记录进行确认！");
        return;
    }
    if (selected.length > 0) {
        tip("请选择一条记录进行确认！");
        return;
    }
    var id = selected.id;
    var url = "tScDrpReceiptConfirmlController.do?goConfirm&id=" + id;
    url = encodeURI(projectName + url);
    $.dialog({
        content: 'url:' + url,
        lock: false,
        width: 800,
        zIndex: 500,
        height: 210,
        parent: windowapi,
        title: "确认发货",
        opacity: 0.3,
        cache: false,
        ok: function () {
            iframe = this.iframe.contentWindow;
            var stockQtyEle = $(iframe.document.body.getElementsByClassName("stockQty"));
            var qtyEle = $(iframe.document.body.getElementsByClassName("qty"));

            stockQty = parseFloat(stockQtyEle.val());
            qty = parseFloat(qtyEle.val());

            if(!stockQty|| stockQty <=0){
                tip("请输入确认收货数量");
                stockQtyEle.focus();
                return false;
            }
            if(stockQty>qty){
                tip("确认收货数量不能大于"+qty+"!");
                stockQtyEle.focus();
                return false;
            }
            saveObj();
            return false;
        },
        cancelVal: '关闭',
        cancel: true
    }).zindex();
}

//反确认
function doUnConfirm(){
    $.messager.confirm('确认对话框', '确定要修改所有数据的确认状态？', function(flag) {
        if(flag){
            $.ajax({
                type: 'POST',
                url: 'tScDrpReceiptConfirmlController.do?doUnConfirm',
                async: false,
                cache: false,
                dataType: 'json',
                success: function (data) {
                    tip("反确认成功！");
                    $("#tScDrpRecepitConfimStockbillList").datagrid('reload');
                }
            });
        }
    });
}