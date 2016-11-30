<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="nodePanelList" title="面板列表"
					actionUrl="roleController.do?nodePanelGrid&nodeId=${node_id}" idField="id"
					queryMode="group">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="node_id" field="node_id" hidden="true"></t:dgCol>
			<t:dgCol title="面板名称" field="panelName" width="50"></t:dgCol>
			<t:dgCol title="类型"  field="type"  dictionary="NODE_VTYPE" queryMode="single" ></t:dgCol>
			<t:dgCol title="面板值" field="value" width="100"></t:dgCol>
			<t:dgDelOpt url="roleController.do?delNodePanel&id={id}" title="common.delete"></t:dgDelOpt>
			<t:dgToolBar title="面板新增" langArg="common.type" icon="icon-add" url="roleController.do?addorupdateNodePanel&node_id=${node_id}" funname="add"></t:dgToolBar>
			<t:dgToolBar title="面板编辑" langArg="common.type" icon="icon-edit" url="roleController.do?addorupdateNodePanel&node_id=${node_id}" funname="update"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script>
	function addType(title,addurl,gname,width,height) {
		add(title,addurl,gname,width,height);
	}
</script>

