/**
 * 编辑
 * @param checkState
 */
function updateEdit(){
    var rowsData = $('#tScRpAdjustbillList').datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length > 1) {
        tip('请选择一条记录再编辑');
        return;
    }
    if(rowsData[0].checkstate != 0){
        tip('该条数据已被审核或审核中,不允许被修改');
        return ;
    }
    var url = 'tScRpBankbillFinController.do?goUpdate';
    url += '&id=' + rowsData[0].id;
    add( '编辑', url,'tScRpAdjustbillList',null,null,'tab');
}