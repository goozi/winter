$(function(){
    $.ajaxSetup({//防止从缓存中提取数据
        cache: false
    });

});
function refreshRightData( node){
    var number = $("input[name='number']").val();
    var name = $("input[name='name']").val();
    var shortName = $("input[name='shortName']").val();
    var shortNumber = $("input[name='shortNumber']").val();
    var licence = $("input[name='licence']").val();
    var settleCompany = $("input[name='settleCompany']").val();

    $("#tScSupplierList").datagrid("load",{name:name,number:number,licence:licence,
        settleCompany:settleCompany,shortName:shortName,shortNumber:shortNumber,parentID: node.id})
}

function goAdd(){

    if(!nodeTree){
        $.messager.show({
            title: '提示消息',
            msg: '请选定一个供应商分类,再进行录入',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    //得到上级节点，带到url，在从后台取值，赋给单据编号为生成按规则的4位单据编号
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];
    add('录入','tScSupplierController.do?goAdd&parentID='+nodeTree.id+'&parentNo='+parentNo,'tScSupplierList',null,null,'tab')

}
//编辑判断 disabled是否等于1
function goUpdate(){
    var rowsData = $('#tScSupplierList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再编辑');
        return;
    }

    if($("#tScSupplierList").datagrid("getSelected").disabled=='1'){

        $.messager.show({
            title: '提示消息',
            msg: '已被禁用，不能修改',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    update('编辑','tScSupplierController.do?goUpdate','tScSupplierList',null,null,'tab');

}