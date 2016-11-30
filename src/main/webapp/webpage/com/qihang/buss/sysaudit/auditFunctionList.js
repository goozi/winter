$(function(){
    $("#tree").tree({
        onClick: function(node){
            var id = node.id;  // 在用户点击的时候提示
            $("#tScOrganizationList").datagrid("load",{id:id})
        }
    })
})