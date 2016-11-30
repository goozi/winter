function roleManager(){
    var select = $("#tSTreeinfoList").datagrid("getSelected");
    if(!select){
        tip("请选择一条数据进行权限管理");
        return;
    }
    var url = 'roleController.do?treeNode2&node_id='+select.eid;
    createdetailwindow("权限管理",url,300,550);
}