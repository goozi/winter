//审核前
function checkAudit(){
    //选择记录
    var selected = $("#tScPrcplyList").datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
    var state = selected.checkState;
    var billDate = selected.date;
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
}

/*//反审核前校验
 function checkUnAudit() {
 var selected = $("#tScPrcplyList").datagrid("getSelected");
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

//编辑页面跳转
function goUpdate() {
    var selected = $("#tScPrcplyList").datagrid("getSelected");
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
    var url = "tScPrcplyController.do?goUpdate";
    update("编辑", url, "tScPrcplyList", null, null, "tab");
}

/**
 * 批量删除控制
 */
function deleteALLSelect(){
    var rows = $("#tScPrcplyList").datagrid('getSelections');
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
                url: 'tScPrcplyController.do?doBatchDel',
                async: false,
                cache: false,
                data: {'ids':ids},
                dataType: 'json',
                success: function (data) {
                    if (data.success == true) {
                        $("#tScPrcplyList").datagrid("reload");
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
