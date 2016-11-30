/**
 * 编辑
 * @param checkState
 */
function updateEdit(){
    var rowsData = $('#tScRpRotherbillList').datagrid('getSelections');
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
    var url = 'tScRpRotherbillController.do?goUpdate';
    url += '&id=' + rowsData[0].id;
    add( '编辑', url,'tScRpRotherbillList',null,null,'tab');
}
//合并行操作
function mergeCellsByField(tableID, colList,id) {
    var ColArray = colList.split(",");
    var tTable = $("#" + tableID);
    var TableRowCnts = tTable.datagrid("getRows").length;
    var tmpA;
    var PerTxt = "";
    var CurTxt = "";
    var PerId = "";
    var CurId = "";
    for (var j = ColArray.length - 1; j >= 0; j--) {
        PerTxt = "";
        PerId = "";
        tmpA = 1;
        for (var i = 0; i <= TableRowCnts; i++) {
            if (i == TableRowCnts) {
                CurTxt = "";
                CurId = "";
            }
            else {
                CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                CurId = tTable.datagrid("getRows")[i][id];

            }
            if ((PerTxt == CurTxt)&&(PerId==CurId)) {
                tmpA += 1;
            }
            else {
                var index =  i - tmpA;
                tTable.datagrid("mergeCells", {
                    index: index,
                    field: ColArray[j],　　//合并字段
                    rowspan: tmpA,
                    colspan: null
                });
                tmpA = 1;
            }
            PerTxt = CurTxt;
            PerId = CurId;
        }
    }
}