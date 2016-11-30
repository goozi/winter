$(function(){
    $.ajaxSetup({//防止从缓存中提取数据
        cache: false
    });
});
function  refreshRightData(node){
    var name=$("input[name='name']").val();

    $("#tScIcitemList").datagrid("load",{name:name,parentID: node.id})
}

//function refreshRightData( node){
//    $("#tScIcitemList").datagrid("load",{parentID: node.id})
//}
function goAdd(title, addurl, gname, width, height, windowType){
    if(!nodeTree){
        $.messager.show({
            title: '提示消息',
            msg: '请选定一个商品分类,再进行录入',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    //得到上级节点，带到url，在从后台取值，赋给单据编号为生成按规则的4位单据编号
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];
    addurl = addurl + "&parentID="+nodeTree.id+'&parentNo='+parentNo;
    add(title, addurl, gname, width, height, windowType);
}