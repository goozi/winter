//开账
function openAccount(){
    var selected = $("#tScAccountConfigList").datagrid("getSelections");
    if(selected.length == 0){
        tip("请选择一条数据进行开账");
        return;
    }else if(selected.length > 1){
        tip("请选择单条数据进行开账");
        return;
    }
    var select = selected[0];
    var openState = select.openState;
    if(parseFloat(openState) > 0){
        tip("该账套已开账，不可重复开账");
        return;
    }
    var id = select.id;//账套id
    $.dialog.confirm('确定进行开账吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tScAccountConfigController.do?openAccount',
                type: 'post',
                dataType: 'json',
                data: {
                    id: id,
                    type:1
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tScAccountConfigList').datagrid("reload");
                        tip(rs.msg);
                    }else{
                        alertTip(rs.msg);
                    }
                }
            });
        }
    })
}
//反开账
function unOpenAccount(){
    var selected = $("#tScAccountConfigList").datagrid("getSelections");
    if(selected.length == 0){
        tip("请选择一条数据进行反开账");
        return;
    }else if(selected.length > 1){
        tip("请选择单条数据进行反开账");
        return;
    }
    var select = selected[0];
    var openState = select.openState;
    if(parseFloat(openState) == 0){
        tip("该账套未开账，不可进行反开账");
        return;
    }
    var id = select.id;//账套id
    $.dialog.confirm('确定进行反开账吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tScAccountConfigController.do?unOpenAccount',
                type: 'post',
                dataType: 'json',
                data: {
                    id: id
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tScAccountConfigList').datagrid("reload");
                        tip(rs.msg);
                    }else{
                        alertTip(rs.msg);
                    }
                }
            });
        }
    })
}