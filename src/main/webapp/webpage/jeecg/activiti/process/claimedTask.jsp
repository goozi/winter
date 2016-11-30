<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="claimedTask" fitColumns="false" title="待办任务列表" actionUrl="activitiController.do?claimedTaskDataGrid"
			pagination="true" checkbox="true"
			idField="taskId" fit="true" queryMode="group">
	<t:dgCol title="编号" field="taskId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="任务名称" field="name" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="流程定义" field="processDefinitionId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="流程实例" field="processInstanceId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="操作" field="opt" queryMode="group" width="200"></t:dgCol>
	<t:dgOpenOpt url="leaveController.do?taskCompletePageSelect&jspPage={description}&processInstanceId={processInstanceId}&taskId={taskId}"
				 title="办理" openModel="OpenTab"/>
</t:datagrid>