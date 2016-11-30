var treeId;
$(function(){
    $.ajaxSetup({//防止从缓存中提取数据
        cache: false
    });
});

function refreshRightData(node){
    treeId = node.id;
    var number = $("input[name='number']").val();
    var name = $("input[name='name']").val();
    var shortNumber = $("input[name='shortNumber']").val();
    $("#tBiStockList").datagrid("load",{
        name:name,
        number:number,
        shortNumber:shortNumber,
        parentID:treeId
    })

}

function goAdd(){
    if(!treeId){
        $.messager.show({
            title: '提示消息',
            msg: '请选定一个客户分类,再进行录入',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    //得到上级节点，带到url，在从后台取值，赋给单据编号为生成按规则的4位单据编号
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];
    add('录入','tBiStockController.do?goAdd&parentID='+treeId+'&parentNo='+parentNo,'tBiStockList',null,null,'tab')
}

/**
 * 启用、禁用
 * @param obj
 * @param id
 */
function changeSta(obj) {
    var rows = $("#tBiStockList").datagrid('getSelections');
    if(rows.length != 1){
        $.messager.show({
            title: '提示消息',
            msg: '请选择一条数据,再进行操作',
            timeout: 2000,
            showType: 'slide'
        });
    }
    var str = "启用";
    if(obj == 1){
        str = "禁用";
    }
    $.dialog.confirm('你确定'+str+'吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tBiStockController.do?doDisable',
                type: 'post',
                dataType: 'json',
                data: {
                    id: rows[0].id,
                    disabled:obj
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tBiStockList').datagrid("reload");
                        $.messager.show({
                            title: '提示消息',
                            msg: rs.msg,
                            timeout: 2000,
                            showType: 'slide'
                        });
                        //tip(rs.msg);
                    }
                }
            });
        }
    })
}

function uploadEdit(){
    var rowsData = $('#tBiStockList').datagrid('getSelections');
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
    update('编辑','tBiStockController.do?goUpdate','tBiStockList',null,null,'tab')
}