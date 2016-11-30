$(function(){
   $(".datagrid-toolbar").find("a[icon=new-icon-unAudit]").attr("onclick","unCheck()");
});
//反审核执行之前时控制点
function unCheck(){
    var selected = $("#tScRpExpensesapplyList").datagrid("getSelected");
    $.ajax({
        url: 'tScRpExpensesapplyController.do?isReference&id='+selected.id,
        dataType: 'json',
        cache: false,
        async: true,
        success: function(data) {
           if(!data.success)
             tip(data.msg);
            else
               goUnAudit('tScRpExpensesapplyList');
        }
    })

}
/**
 * 编辑
 * @param checkState
 */
function updateEdit(){
    var rowsData = $('#tScRpExpensesapplyList').datagrid('getSelections');
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
    var url = 'tScRpExpensesapplyController.do?goUpdate';
    url += '&id=' + rowsData[0].id;
    add( '编辑', url,'tScRpExpensesapplyList',null,null,'tab');
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