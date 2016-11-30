/**
 * 编辑
 * @param checkState
 */
function updateEdit(){
    var rowsData = $('#tScRpBankbillList').datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length > 1) {
        tip('请选择一条记录再编辑');
        return;
    }
    if(rowsData[0].checkState != 0){
        tip('该条数据已被审核或审核中,不允许被修改');
        return ;
    }
    var url = 'tScRpBankbillController.do?goUpdate';
    url += '&id=' + rowsData[0].id;
    add( '编辑', url,'tScRpBankbillList',null,null,'tab');
}
/**
 * 批量删除
 */
function deleteALLSelectBank(){
    var rowsData = $('#tScRpBankbillList').datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
        tip('请选择删除项目');
        return;
    }
    var bool = false;
    for(var i =0 ;i<rowsData.length;i++){
        if(rowsData[0].checkState != 0){
            bool = true;
        }
    }
    if(bool){
        tip('批量删除不能删除已审核或审核中');
        return ;
    }
    deleteALLSelect('批量删除', 'tScRpBankbillController.do?doBatchDel','tScRpBankbillList',null,null,'tab')
}