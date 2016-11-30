$(function(){
    $.ajaxSetup({//防止从缓存中提取数据
        cache: false
    });

});
function  refreshRightData(node){
    var name=$("input[name='name']").val();
    var number=$("input[nam='number']").val();
    var shortNumber=$("input[name='shortNumber']").val();
    var shortName=$("input[nmae='shortName']").val();

    $("#tScEmpList").datagrid("load",{name:name,number:number,
        shortName:shortName,shortNumber:shortNumber,parentID: node.id})
}
function goAdd(){

    if(!nodeTree){
        $.messager.show({
            title: '提示消息',
            msg: '请选定一个职员分类,再进行录入',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    //得到上级节点，带到url，在从后台取值，赋给单据编号为生成按规则的4位单据编号
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];
    add('录入','tScEmpController.do?goAdd&parentID='+nodeTree.id+'&parentNo='+parentNo,'tScEmpList',null,null,'tab')

}
function goUpdate(){
    var rowsData = $('#tScEmpList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再编辑');
        return;
    }

    if($("#tScEmpList").datagrid("getSelected").disabled=='1'){

        $.messager.show({
            title: '提示消息',
            msg: '已被禁用，不能修改',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    update('编辑','tScEmpController.do?goUpdate','tScEmpList',null,null,'tab');

}