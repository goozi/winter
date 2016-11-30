//树切换
function changeTreeInfo(name){
    if("stock" == name){
        $("#tree").tree({
            url:"tBiStockController.do?getStockTree",
            onClick:function(node){
                $("#tScIcInventoryList").datagrid("load",{stockId:node.id})
            }
        });
    }
    else if("icitem" == name){
        $("#tree").tree({
            url:"tScIcitemController.do?getIcItemTree",
            onClick:function(node){
                $("#tScIcInventoryList").datagrid("load",{itemId:node.id})
            }
        });
    }
}
//初始化函数
$(function(){
    $("#tree").tree({
        onClick:function(node){
            $("#tScIcInventoryList").datagrid("load",{stockId:node.id})
        }
    })
    $("select[name='isZero']").val(0);
    var options = $("select[name='isZero']")[0].options
    for(var i=0 ; i<options.length ; i++){
        var value = options[i].value;
        if(value == ""){
            options.remove(i);
            break;
        }
    }
})


//清理
function deleteInfo(){
    var url = "tScIcInventoryController.do?deleteInfo";
    $.ajax({
        url: url,
        dataType: 'json',
        cache: false,
        success: function (data) {
            if(data.success){
                tip(data.msg);
                $("#tScIcInventoryList").datagrid("reload");
            }else{
                tip(data.msg);
            }
        }
    });
}

//批号
function showBatchNoInfo(itemId,stockId,itemName,model){
    if(!itemId) {
        var select = $("#tScIcInventoryList").datagrid("getSelected");
        if(null != select) {
            itemId = select.itemId;
            stockId = select.stockId;
            itemName = select.itemName;
            model = select.model;
        }else{
            tip("请选择一条数据");
            return;
        }
    }
    var url = "tScIcInventoryController.do?batchNoInfo&itemId="+itemId+"&stockId="+stockId;
    createdetailwindow("商品【"+itemName+"（"+model+"）】批号明细",url,1500,500,"tab");
}
