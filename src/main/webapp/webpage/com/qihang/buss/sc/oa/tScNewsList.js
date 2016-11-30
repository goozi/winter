//发布
function release() {
    var rows = $('#tScNewsList').datagrid('getSelections');
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
            msg: '请选择要一条记录',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }
    var issuance = rows[0].issuance;
    if (issuance == 1) {
        $.messager.show({
            title: '提示消息',
            msg: '新闻已发布',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }
    var id = rows[0].id;
    $.messager.confirm('确认', '确认要发布吗?', function (a) {
        if (a) {
            url = "tScNewsController.do?release&id=" + id;
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
                    $("#tScNewsList").datagrid("reload");
                }
            })
        }
    })
}

//已发布消息不可编辑更新
function updateIssuance(title, addurl, gname, width, height){
    var rows = $('#tScNewsList').datagrid('getSelections');
    if (rows.length == 0) {
        $.messager.show({
            title: '提示消息',
            msg: '请选择编辑项目',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }else if(rows.length > 1){
        $.messager.show({
            title: '提示消息',
            msg: '请选择要一条记录',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }
    var issuance = rows[0].issuance;
    if(issuance == 1){
        /*var url ='tScNewsController.do?goUpdate'+ '&load=detail&id=' + rows[0].id;
         createdetailwindow('查看', url, 700, 450);*/
        $.messager.show({
            title: '提示消息',
            msg: '新闻已发布，不能编辑',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }else{
        update("编辑", addurl, gname, width, height,"tab");
    }
}
