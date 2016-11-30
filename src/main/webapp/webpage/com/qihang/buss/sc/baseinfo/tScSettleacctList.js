$(function(){
    $.ajaxSetup({//防止从缓存中提取数据
        cache: false
    });
});

/**
 * 启用、禁用
 * @param obj
 * @param id
 */
function changeSta(obj) {
    var rows = $("#tScSettleacctList").datagrid('getSelections');
    if(rows.length != 1){
        tip('请选择一条数据,再进行操作');
        return;
    }
    var str = "启用";
    if(obj == 1){
        str = "禁用";
    }
    $.dialog.confirm('你确定'+str+'吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tScSettleacctController.do?doDisable&disabled='+obj,
                type: 'post',
                dataType: 'json',
                data: {
                    id: rows[0].id
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tScSettleacctList').datagrid("reload");
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

function uploadEdit(){
    var rowsData = $('#tScSettleacctList').datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
        tip('请选择编辑的项');
        return;
    }
    if (rowsData.length > 1) {
        tip('请选择一条记录再编辑');
        return;
    }
    if(rowsData[0].disabled == 1){
        tip('该条数据已被禁用不允许修改');
        return ;
    }
    update('编辑','tScSettleacctController.do?goUpdate','tScSettleacctList',null,null,'tab')
}