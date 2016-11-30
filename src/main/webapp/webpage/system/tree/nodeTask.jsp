<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$(function() {
        // 获取并构建角色Tree：展示角色树并选中当前组织机构所拥有的角色；
		$('#nodeTask').tree({
			checkbox : true,
			url : 'tSTreeinfoController.do?loadNodeTask&node_id=${node_id}&act_id=${act_id}',
			onLoadSuccess : function(node) {
				expandAll();
			}
		});
		$("#functionListPanel").panel(
				{
					title :'流程节点列表',
					tools:[{iconCls:'icon-save',handler:function(){mysubmit();}}]
				}
		);
	});
	function mysubmit() { // 提交
		var node_id = $("#node_id").val();
		var act_id = "${act_id}"
		var ids = GetNode();
		ids = ids.substring(0,ids.length-1);
		var param = {};
		param.id=1;
		doSubmit("tSTreeinfoController.do?saveNodeTask&taskIds=" + ids + "&node_id=" + node_id+"&act_id="+act_id,null,param);
	}
    /**
     * 获取 选中的节点 并返回
     * @returns {string} 节点id，多个id已逗号分割
     * @constructor
     */
    function GetNode() {
		var node = $('#nodeTask').tree('getChecked');
		var cnodes = '';
		var pnodes = '';
		var pnode = null; //保存上一步所选父节点
		for ( var i = 0; i < node.length; i++) {
			if ($('#nodeTask').tree('isLeaf', node[i].target)) {
				cnodes += node[i].id + ',';
				pnode = $('#nodeTask').tree('getParent', node[i].target); //获取当前节点的父节点
				while (pnode!=null) {//添加全部父节点
					pnodes += pnode.id + ',';
					pnode = $('#nodeTask').tree('getParent', pnode.target);
				}
			}
		}
		cnodes = cnodes.substring(0, cnodes.length - 1);
		pnodes = pnodes.substring(0, pnodes.length - 1);
		return cnodes + "," + pnodes;
	}
	/**
	 * 展开所有节点
     */
	function expandAll() {
		var node = $('#nodeTask').tree('getSelected');
		if (node) {
			$('#nodeTask').tree('expandAll', node.target);
		} else {
			$('#nodeTask').tree('expandAll');
		}
	}
    /**
     * 选择所有节点
     */
	function selecrAll() {
		var node = $('#nodeTask').tree('getRoots');
		for ( var i = 0; i < node.length; i++) {
            $('#nodeTask').tree("check",node[i].target);
	    }
	}
    /**
     * 重置树
     */
	function reset() {
		$('#nodeTask').tree('reload');
	}

	$('#selecrAllBtn').linkbutton({
	});
	$('#resetBtn').linkbutton({
	});
</script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel">
            <input type="hidden" name="node_id" value="${node_id}" id="node_id">
            <a id="selecrAllBtn" onclick="selecrAll();"><t:mutiLang langKey="select.all"/></a>
            <a id="resetBtn" onclick="reset();"><t:mutiLang langKey="common.reset"/></a>
            <ul id="nodeTask"></ul>
        </div>
    </div>
</div>
