//发布
function release() {
    var rows = $('#tScNoticeList').datagrid('getSelections');
    if (rows.length == 0) {
        $.messager.show({
            title: '提示消息',
            msg: '请选择要发布的数据',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    } else if (rows.length > 1) {
        $.messager.show({
            title: '提示消息',
            msg: '请选择要一条公告消息',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }
    var issuance = rows[0].issuance;
    if (issuance == 1) {
        $.messager.show({
            title: '提示消息',
            msg: '公告消息已发布',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }
    var id = rows[0].id;
    $.messager.confirm('确认', '确认要发布吗?', function (a) {
        if (a) {
            url = "tScNoticeController.do?release&id=" + id;
            $.ajax({
                type: 'post',
                contentType: 'application/json',
                url: url,
                dataType: 'json',
                success: function (result) {
                    $.messager.show({
                        title: '提示消息',
                        msg: result.msg,
                        timeout: 2000,
                        showType: 'slide'
                    });
                    $("#tScNoticeList").datagrid("reload");
                }
            })
        }
    })
}

