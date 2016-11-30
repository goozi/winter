/**
 * Created by Administrator on 2016-06-23.
 */

$(function () {

    $("#tree").tree({
        onClick: function (node) {
            nodeTree = node;
            refreshRightData(node);
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

        onContextMenu: showRightMenu

    });

});


var nodeTree;
function addTree(type, title, gname) {
    if (!nodeTree) {
        $.messager.show({
            title: '提示消息',
            msg: '请在左边选定一个上级分类',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    //得到上级节点，带到url，在从后台取值，赋给分类编号为生成按规则的3位分类编号

    var addTitle = window.top.$('.tabs-selected:first').text();
    var node = $("#tree").tree("getSelected");
    var info = node.attributes.split("#");
    var parentNo = info[1];
     //parentNo =  parentNo.split(".").join("");
    add("分类添加", "tScItemTypeController.do?goAdd&itemClassId=" + type + "&parentId=" + nodeTree.id + "&title=" + addTitle + "&parentNo=" + parentNo, gname, 400, 200, "dialog");
}

function updateTree(title, gname) {
    if (!nodeTree) {
        $.messager.show({
            title: '提示消息',
            msg: '请在左边选择要编辑的分类',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    if (nodeTree.attributes == "00000") {
        $.messager.show({
            title: '提示消息',
            msg: '根节点不可编辑',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    var title = window.top.$('.tabs-selected:first').text();
//  createwindow("站点编辑", "tApSontypeController.do?goUpdate&id="+treeId, null, null);
    add("分类编辑", "tScItemTypeController.do?goUpdate&id=" + nodeTree.id + "&title=" + title, gname, 400, 200, "dialog");
}


//删除树形节点
function Delete() {
    var nodes = $('#tree').tree('getSelected');
    if (nodes == null) {
        $.messager.show({
            title: '提示消息',
            msg: '请选择要删除的节点',
            timeout: 2000,
            showType: 'slide'
        });
        return;
    }
    if (nodes.attributes == "00000") {
        $.messager.show({
            title: '提示消息',
            msg: '根节点不可删除',
            timeout: 2000,
            showType: 'slide'
        });
        return false;
    }
    $.messager.confirm('确认', '确认要删除吗?', function (a) {
        if (a) {
            url = "tScItemTypeController.do?doDel&id=" + nodes.id;
            $.ajax({
                type: 'post',
                contentType: 'application/json',
                url: url,
                dataType: 'json',
                success: function (result) {
                    $.messager.show({
                        title: '提示消息',
                        msg: result.msg,
                        timeout: 2000,
                        showType: 'slide'
                    });
                    $("#tree").tree("reload");
                    nodeTree = null;
                }
            })
        }
    })
}

function showRightMenu(e, node) {
    nodeTree = node;
    e.preventDefault();
    //查找节点
    $('#tree').tree('select', node.target);
    //显示快捷菜单
    $('#mm1').menu({
        onShow: function () {
            nodeTree = node;
        }
    });
    $('#mm1').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
}

function refreshTreess() {
    $("#tree").tree("reload");
}