$(function(){
  var oldNode;
  var isAdd=false;
  $('#tt').tree({
    url:'tScDocumentController.do?treeList',
    onClick: function(node){
      $("#tScFileList").datagrid('load', {
        'fileType': node.id,
        type:node.attributes.type
      })
    },
    onContextMenu: function(e, node){
      e.preventDefault();
      // 查找节点
      $('#tt').tree('select', node.target);
      var pid = node.attributes.pid;
      if(pid != "0"){
        if(pid == "0000"){
          // 显示快捷菜单
          $('#mm1').menu('show', {
            left: e.pageX,
            top: e.pageY
          });
        }else{
          // 显示快捷菜单
          $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
          });
        }
      }

    },
    onBeforeEdit:function(node){
      oldNode = node.text;
    },
    onAfterEdit:function(node){
      //var parentNode = $('#tt').tree('getParent', node.target);
      var id = null;
      var msg ='新增目录';
      if(node.attributes.isedit){
          id = node.id;
          msg='更新目录';
      }
      $.dialog.confirm('您确认想要'+msg+'？',function(r){
          $.ajax({
            url:'tScDocumentController.do?doAdd',
            type:'post',
            dataType:'json',
            data:{
              'id':id,
              'name':node.text,
              'pid':node.attributes.pid,
              'type':node.attributes.type,
            },
            success:function(rs){
              if(rs.success){
                $('#tt').tree('update', {
                  target: node.target,
                  id: rs.obj
                });
                $.messager.show({
                  title:'提示',
                  msg:msg+'成功！',
                  timeout:2000,
                  showType:'slide'
                });
              }else{
                $('#tt').tree('cancelEdit', node.target);
              }
            }
          });
      },function(){
        if(!node.attributes.isedit) {
          $('#tt').tree('cancelEdit', node.target);
        }else{
          $('#tt').tree('update', {
            target: node.target,
            text: oldNode
          });
        }
      });
    },
    onCancelEdit:function(node){
      var select = $('#tt').tree("getSelected");
      $('#tt').tree("remove",select.target);
    },
    onLoadSuccess:function(node,data){
    if(!isAdd) {
      isAdd = true;
      //if(data[0].children.length>0){
      var id = data[0].children[0].id;
      var nodes = $('#tt').tree('find', id);

      $('#tt').tree('select', nodes.target);
      $("#tScFileList").datagrid('load', {
        'fileType': nodes.id,
        type:nodes.attributes.type
      })
      //}
    }
    }
  });
});

function appendTT(){
  // 追加若干个节点并选中他们
  var selected = $('#tt').tree('getSelected');
  var timestamp = new Date().getTime();
  $('#tt').tree('append', {
    parent: selected.target,
    data: [{
      id: timestamp,
      text: '新建目录',
      attributes:{
        isedit:false,
        pid:selected.id,
        type:selected.attributes.type
      }
    }]
  });
  var node = $('#tt').tree('find', timestamp);
  $('#tt').tree('select', node.target);
  var sel = $('#tt').tree('getSelected');
  $('#tt').tree('beginEdit', sel.target);
}


function editTT(){
  // 追加若干个节点并选中他们
  var selected = $('#tt').tree('getSelected');
  selected.attributes.isedit = true;
  $('#tt').tree('beginEdit', selected.target);
}

function removeTT(){
  var select = $('#tt').tree("getSelected");
  var bl = true;
  $.ajax({
    url:'tScDocumentController.do?delVaild',
    type:'post',
    dataType:'json',
    async:false,
    data:{
      'id':select.id
    },
    success:function(rs){
      if(rs.success){
        bl = false;
          tip(rs.msg);

      }
    }
  });
  if(bl){
    $.dialog.confirm('您确认想要删除目录？',function(r){
      $.ajax({
        url:'tScDocumentController.do?doDel',
        type:'post',
        dataType:'json',
        data:{
          'id':select.id
        },
        success:function(rs){
          //$('#tt').tree('reload');
          $('#tt').tree("remove",select.target);
          $.messager.show({
            title:'提示',
            msg:'删除目录成功！',
            timeout:2000,
            showType:'slide'
          });
          $("#tScFileList").datagrid('load');

        }
      });
    });
  }
}

//通用弹出式文件上传
function commonUpload(callback){
  var selected = $('#tt').tree('getSelected');
  if(selected == null){
    $.messager.show({
      title:'提示',
      msg:'请先选择文件上传目录！',
      timeout:2000,
      showType:'slide'
    });
    return;
  }
  var pid = selected.attributes.pid;
  if(pid == "0" || pid == "0000"){
    $.messager.show({
      title:'提示',
      msg:'该目录不允许上传文件！',
      timeout:2000,
      showType:'slide'
    });
    return;
  }
  $.dialog({
    content: "url:tScFileController.do?goAdd",
    lock : true,
    title:"文件上传",
    zIndex:2100,
    width:500,
    height: 200,
    parent:windowapi,
    cache:false,
    ok: function(){
      var iframe = this.iframe.contentWindow;
      iframe.uploadCallback(callback);
      return true;
    },
    cancelVal: '关闭',
    cancel: function(){
    }
  });
}

function fileCallback(url,name,extend,note){
  var selected = $('#tt').tree('getSelected');
 $.ajax({
    url:'tScFileController.do?doAdd',
    type:'post',
    dataType:'json',
    data:{
      'url':url,
      'name':name+"."+extend,
      'type':selected.attributes.type,
      'fileType':selected.id,
      'note':note
    },
    success:function(rs){
      if(rs.success){
        var sel = $('#tt').tree('getSelected');
        $("#tScFileList").datagrid('load', {
          'fileType': sel.id
        })
        $.messager.show({
          title:'提示',
          msg:'上传成功！',
          timeout:2000,
          showType:'slide'
        });
      }else{
        $.messager.show({
          title:'提示',
          msg: rs.msg,
          timeout:2000,
          showType:'slide'
        });
      }
    }
  });

}

/**
 * 批量审核請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function doBatchAudit() {
  var url = "tScFileController.do?doBatchAudit";
  var ids = [];
  var rows = $('#tScFileList').datagrid('getSelections');
  if (rows.length > 0) {
    $.dialog.confirm('你确定批量审核吗?', function (r) {
        for (var i = 0; i < rows.length; i++) {
          if(rows[i].status != 0){
            tip("请选择待审核的数据");
            ids = '';
            return;
          }
          ids.push(rows[i].id);
        }
        $.ajax({
          url: url,
          type: 'post',
          dataType:'json',
          data: {
            ids: ids.join(',')
          },
          cache: false,
          success: function (rs) {
            if (rs.success) {
              var msg = rs.msg;
              tip(msg);
              $('#tScFileList').datagrid('reload');
              ids = '';
            }
          }
        });
    });
  } else {
    tip("请选择需要审核的数据");
  }
}

//审核
function audit(id,rowIndex) {
  $.dialog.confirm('你确定审核吗?', function (r) {
      $.ajax({
        url: 'tScFileController.do?doBatchAudit',
        type: 'post',
        dataType: 'json',
        data: {
          ids: id
        },
        success: function (rs) {
          if (rs.success) {
            $('#tScFileList').datagrid("reload");
            tip(rs.msg);
          }
        }
      });
  })
}
