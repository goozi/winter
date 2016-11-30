function goUpdate(){
    var rowsData = $('#tScMeasureunitList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再编辑');
        return;
    }

    if($("#tScMeasureunitList").datagrid("getSelected").disabled=='1'){

        $.messager.show({
            title: '提示消息',
            msg: '已被禁用，不能修改',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    update('编辑','tScMeasureunitController.do?goUpdate','tScMeasureunitList',null,null,'tab');

}