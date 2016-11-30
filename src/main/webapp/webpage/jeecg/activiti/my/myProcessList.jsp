<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="processList" fitColumns="false" title="发起新流程" actionUrl="activitiController.do?datagrid"
			pagination="true" checkbox="true"
			idField="id" fit="true" queryMode="group">
	<t:dgCol title="备注" field="description" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="编号" field="id" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="流程名称" field="name" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="KEY" field="key" queryMode="single" width="120" query="true"></t:dgCol>
	<t:dgCol title="版本" field="version" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="是否挂起" field="suspensionState" queryMode="single" width="120" replace="激活_1,挂起_2"
			 query="true"></t:dgCol>
	<t:dgToolBar title="启动" icon="icon-add" url="activitiController.do?startPageSelect" funname="startProcess" />
	<t:dgCol title="操作" field="opt" queryMode="group" width="200"></t:dgCol>
	<t:dgDelOpt title="删除" url="activitiController.do?del&deploymentId={deploymentId}"/>
	<t:dgOpenOpt url="activitiController.do?resourceRead&resourceType=image&isIframe&processDefinitionId={id}"
				 title="查看流程图"/>
</t:datagrid>
<script>
	function startProcess(title,url,gridname,width, height, windowType){
		var rowsData = $('#' + gridname).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('请选择要启动的流程');
			return;
		}
		if (rowsData.length > 1) {
			tip('请只选择一条流程');
			return;
		}
		url += '&startPage='+rowsData[0].description;
		add(title,url,gridname,width, height, windowType);
	}
</script>