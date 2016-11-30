/**
 * 启用
 * @param obj
 * @param id
 */
function doEnabled(id) {
    var str = "启用";
    $.dialog.confirm('你确定'+str+'吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tScAptitudeController.do?doDisable',
                type: 'post',
                dataType: 'json',
                data: {
                    id: id,
                    disabled:0
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tScAptitudeList').datagrid("reload");
                        $.messager.show({
                            title: '提示消息',
                            msg: rs.msg,
                            timeout: 2000,
                            showType: 'slide'
                        });
                    }
                }
            });
        }
    })
}

/**
 * 禁用
 * @param obj
 * @param id
 */
function doDisabled(id) {
    var str = "禁用";
    $.dialog.confirm('你确定'+str+'吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tScAptitudeController.do?doDisable',
                type: 'post',
                dataType: 'json',
                data: {
                    id: id,
                    disabled:1
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tScAptitudeList').datagrid("reload");
                        $.messager.show({
                            title: '提示消息',
                            msg: rs.msg,
                            timeout: 2000,
                            showType: 'slide'
                        });
                    }
                }
            });
        }
    })
}

//审核前
function checkAudit(){
    //选择记录
    var selected = $("#tScAptitudeList").datagrid("getSelected");
    if(null == selected){
        tip("请选择一条数据");
        return;
    }
    var cancel = selected.cancellation;
    var state = selected.state;
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

//审核后执行
function afterAudit(billId){
    var id = billId;
    var url = "tScAptitudeController.do?afterAudit";
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        data:{"audit":1,"id":id},
        success: function (d) {
            var attr = d.attributes;
            tip("账号生成成功！请牢记!<br/>"+"用户名:&nbsp;&nbsp;<span style='color: red;font-weight: bold;'>"+attr.userName+"</span><br/>"+"密码:&nbsp;&nbsp;<span style='color: red;font-weight: bold;'>"+attr.password+"</span>");
        }
    });
}
//反审核后执行
function afterUnAudit(billId){
    var url = "tScAptitudeController.do?afterAudit&audit=0&id=" + billId;
    $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (d) {
        }
    });
}

//编辑页面跳转
function goUpdate() {
    var selected = $("#tScAptitudeList").datagrid("getSelected");
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
    var url = "tScAptitudeController.do?goUpdate";
    update("编辑", url, "tScAptitudeList", null, null, "tab");
}

/**
 * 批量删除控制
 */
function deleteALLSelect(){
    var rows = $("#tScAptitudeList").datagrid('getSelections');
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
                url: 'tScAptitudeController.do?doBatchDel',
                async: false,
                cache: false,
                data: {'ids':ids},
                dataType: 'json',
                success: function (data) {
                    if (data.success == true) {
                        $("#tScAptitudeList").datagrid("reload");
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

//删除
function doDel(id,billNo,date){
    date = new Date(date).getTime();
    var checkDate = $("#checkDate").val();
    checkDate = new Date(checkDate).getTime();
    if(date < checkDate){
        tip("单据["+billNo+"]不在本期间内，不可删除");
        return;
    }
    var url="tScAptitudeController.do?doDel&id="+id;
    $.dialog.confirm("确定删除该数据？", function () {
            $.ajax({
                url: url,
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if (data.success) {
                        tip("删除成功");
                        $("#tScAptitudeList").datagrid("reload");
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

/***
 * 作废
 */
/*
function cancelBill(){
    var rows = $("#tScAptitudeList").datagrid('getSelections');
    if(rows.length > 0){
        var cancellation = rows[0].cancellation;
        var status = rows[0].status;
        if(status == 1){
            tip("单据["+rows[0].billNo+"]已在审核中，不可作废");
            return;
        }else if(status == 2){
            tip("单据["+rows[0].billNo+"]已审核，不可作废");
            return;
        }
        if(cancellation == 1){
            tip("单据["+rows[0].billNo+"]已作废");
            return;
        }
        $.ajax({
            type: 'POST',
            url: 'tScOrderController.do?cancelBill',
            async: false,
            cache: false,
            data: {'id': rows[0].id,'type':'1'},
            dataType: 'json',
            success: function (data) {
                if (data.success == true) {
                    $("#tScAptitudeList").datagrid("reload");
                }
                tip(data.msg);
            }
        });

    }else{
        tip("请选择需要作废的数据");
    }
}*/
