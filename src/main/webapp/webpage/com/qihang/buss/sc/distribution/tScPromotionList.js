//删除
function doDel(){
    var selected = $("#tScPromotionViewList").datagrid("getSelected");
    var id = selected.id;
    if (null != selected) {
        var billDate = selected.date;
        if(billDate.indexOf(" ")){
            billDate = billDate.substring(0,billDate.indexOf(" "));
        }
        var date = new Date(billDate).getTime();
        var checkDate = $("#checkDate").val();
        checkDate = new Date(checkDate).getTime();
        if(date < checkDate){
            tip("单据["+billNo+"]不在本期间内，不可删除");
            return;
        }
        var checkState = selected.checkState;
        var isEdit = $("#isEdit").val();
        if (checkState == 1 && !isEdit) {
            tip("该单据正在审核中，不可删除");
            return;
        }
        if (checkState == 2 && !isEdit) {
            tip("该单据已审核，不可删除");
            return;
        }
        var url="tScPromotionController.do?doDel&id="+id
        $.dialog.confirm("确定删除该单据？",
            function () {
                $.ajax({
                    url: url,
                    dataType: 'json',
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            tip("删除成功");
                            $("#tScPromotionViewList").datagrid("reload");
                        } else {
                            tip(data.msg);
                        }
                    }
                });
            },function(){
                //
            }
        );
    }
}
//编辑页面跳转
function goUpdate() {
    var selected = $("#tScPromotionViewList").datagrid("getSelected");
    if (null != selected) {
        var isEdit = $("#isEdit").val();
        var checkState = selected.checkState;

        if (checkState == 1 && !isEdit) {
            tip("该单据正在审核中，不可编辑");
            return;
        }
        if (checkState == 2 && !isEdit) {
            tip("该单据已审核，不可编辑");
            return;
        }
    }
    var url = "tScPromotionController.do?goUpdate";
    update("编辑", url, "tScPromotionViewList", null, null, "tab");
}

//审核前校验
function checkAudit(){
    //选择记录
    var selected = $("#tScPromotionViewList").datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
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
    if(cancel == 1){
        tip("单据["+selected.billNo+"]已作废，不可审核");
        return;
    }
}

//反审核前校验
 /*function checkUnAudit() {
     var selected = $("#tScPromotionViewList").datagrid("getSelected");
     if (null == selected) {
         tip("请选择一条数据");
         return;
     }
     var cancel = selected.cancellation;
     var state = selected.checkState;
     var date = new Date(selected.date).getTime();
     var checkDate = $("#checkDate").val();
     if(checkDate){
         checkDate = new Date(checkDate).getTime();
         if(date < checkDate){
             tip("单据["+selected.billNo+"]不在本期间内，不可反审核1");
             return;
         }
     }
     if(state == 0){
         tip("单据["+selected.billNo+"]未审核，不可反审核1");
         return;
     }
     if(cancel == 1){
         tip("单据["+selected.billNo+"]已作废，不可反审核1");
         return;
     }
 }*/

/**
 * 批量删除控制
 */
function deleteALLSelect(){
    var rows = $("#tScPromotionViewList").datagrid('getSelections');
    var ids="";
    if(rows.length > 0){
        for(var i in rows){
            var status = parseInt(rows[i].checkState);
            if(status == 1){
                tip("单据["+rows[0].billNo+"]已在审核中，不删除");
                return;
            }else if(status == 2){
                tip("单据["+rows[0].billNo+"]已审核，不可删除");
                return;
            }
            ids += rows[i].id+",";
        }
        if(ids.endsWith(",")){
            ids = ids.substring(ids.lastIndexOf(","),0);
        }
        $.dialog.confirm("确定删除所选记录？", function () {
            $.ajax({
                type: 'POST',
                url: 'tScPromotionController.do?doBatchDel',
                async: false,
                cache: false,
                data: {'ids':ids},
                dataType: 'json',
                success: function (data) {
                    if (data.success == true) {
                        $("#tScPromotionViewList").datagrid("reload");
                    }
                    tip(data.msg);
                }
            });
        });
    }else{
        tip("请选择需要删除的数据");
        return;
    }
}