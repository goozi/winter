var treeId;
var selId;
$(function(){
    $.ajaxSetup({//防止从缓存中提取数据
        cache: false
    });
    $("#tree").tree({
        onClick:function(node){

            var number = $("input[name='number']").val();
            var name = $("input[name='name']").val();
            var licence = $("input[name='licence']").val();
            var settlecompany = $("input[name='settlecompany']").val();
            var shortname = $("input[name='shortname']").val();
            var shortnumber = $("input[name='shortnumber']").val();
            var sonIdSup = node.id;
            treeId = node.id;
            $("#tScOrganizationList").datagrid("load",{name:name,number:number,licence:licence,
                settlecompany:settlecompany,shortname:shortname,shortnumber:shortnumber,parentid:treeId})
        },
        onContextMenu:function(e,node){
            e.preventDefault();
            //查找节点
            $('#tree').tree('select', node.target);
            //显示快捷菜单
            $('#mm1').menu({
                onShow:function(){
                    selId = node.id;
                    treeId = node.id;
                }
            });
            $('#mm1').menu('show',{
                left: e.pageX,
                top: e.pageY
            });
        },
        onLoadSuccess: function (node, data) {
            if (data.length > 0) {
                var n = $("#tree").tree('find', data[0].id);
                if (null != n && undefined != n) {
                    $("#tree").tree('select', n.target);
                    n.target.click();
                }
            }
        },
    });
    $("#tScOrganizationList").datagrid({
        fit:true,fitColumns:false
    });
    var dg = $("#tScOrganizationList");
    var col = dg.datagrid('getColumnOption', 'name');
    col.width = 300;
});


function addTree(title, gname, type) {
    type=$("#itemClassId").val();
    if (!treeId) {
        $.messager.show({
            title: '提示消息',
            msg: '请在左边选定一个上级分类',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    //得到上级节点，带到url，在从后台取值，赋给分类编号为生成按规则的3位分类编号

    var addTitle=window.top.$('.tabs-selected:first').text();
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];

    add("分类添加",  "tScItemTypeController.do?goAdd&itemClassId="+type+"&parentId=" + treeId+"&title="+addTitle+"&parentNo="+parentNo, gname, 400, 200, "dialog");
}

function updateTree(title, gname){
    if (!treeId) {
        $.messager.show({
            title: '提示消息',
            msg: '请在左边选择要编辑的分类',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    if(treeId == "10000"){
        $.messager.show({
            title: '提示消息',
            msg: '根节点不可编辑',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    var title=window.top.$('.tabs-selected:first').text();
//  createwindow("站点编辑", "tApSontypeController.do?goUpdate&id="+treeId, null, null);
    add("分类编辑", "tScItemTypeController.do?goUpdate&id="+treeId+"&title="+title,gname,400,200,"dialog") ;
}


//删除树形节点
function Delete(){
    var nodes = $('#tree').tree('getSelected');
    if(nodes == null){
        $.messager.show({
            title:'提示消息',
            msg:'请选择要删除的节点',
            timeout:2000,
            showType:'slide'
        });
        return;
    }
    if(treeId == "10000"){
        $.messager.show({
            title: '提示消息',
            msg: '根节点不可删除',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    $.messager.confirm('确认','确认要删除吗?',function(a){
        if(a){
            url="tScItemTypeController.do?doDel&id="+nodes.id;
            $.ajax({
                type:'post',
                contentType:'application/json',
                url:url,
                dataType:'json',
                success:function(result){
                    $.messager.show({
                        title:'提示消息',
                        msg:result.msg,
                        timeout:2000,
                        showType:'slide'
                    });
                    $("#tree").tree("reload");
                    treeId = null;
                }
            })
        }
    })
}


function goAdd(){
    if(!treeId){
        $.messager.show({
            title: '提示消息',
            msg: '请选定一个客户分类,再进行录入',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    //得到上级节点，带到url，在从后台取值，赋给单据编号为生成按规则的4位单据编号
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];
    add('录入','tScOrganizationController.do?goAdd&parentid='+treeId+'&parentNo='+parentNo,'tScOrganizationList',null,null,'tab')

}

function goUpdate(){
    debugger;
    var rowsData = $('#tScOrganizationList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再编辑');
        return;
    }
    if($("#tScOrganizationList").datagrid("getSelected").disable=='1'){

        $.messager.show({
            title: '提示消息',
            msg: '已被禁用，不能修改',
            timeout: 2000,
            showType: 'slide'
        });
        return ;
    }
    update('编辑','tScOrganizationController.do?goUpdate','tScOrganizationList',null,null,'tab');

}

function refreshTreess(){
    $("#tree").tree("reload");
}

/**
 * 启用、禁用
 * @param obj
 * @param id
 */
function changeSta(obj) {
    var rows = $("#tScOrganizationList").datagrid('getSelections');
    if(rows.length != 1){
        $.messager.show({
            title: '提示消息',
            msg: '请选择一条数据,再进行操作',
            timeout: 2000,
            showType: 'slide'
        });
    }
    var str = "启用";
    if(obj == 1){
        str = "禁用";
    }
    $.dialog.confirm('你确定'+str+'吗?', function (r) {
        if (r) {
            $.ajax({
                url: 'tScOrganizationController.do?doDisable',
                type: 'post',
                dataType: 'json',
                data: {
                    id: rows[0].id,
                    disable:obj
                },
                success: function (rs) {
                    if (rs.success) {
                        $('#tScOrganizationList').datagrid("reload");
                        $.messager.show({
                            title: '提示消息',
                            msg: rs.msg,
                            timeout: 2000,
                            showType: 'slide'
                        });
                        //tip(rs.msg);
                    }
                }
            });
        }
    })
}