<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="waitingClaimTask" fitColumns="false" title="运行中流程列表" actionUrl="activitiController.do?waitingClaimTaskDataGrid"
			pagination="true" checkbox="true"
			idField="taskId" fit="true" queryMode="group">
	<t:dgCol title="编号" field="taskId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="任务名称" field="name" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="流程定义" field="processDefinitionId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="操作" field="opt" queryMode="group" width="200"></t:dgCol>
	<t:dgOpenOpt url="activitiController.do?claimTask&taskId={taskId}"
				 title="签收"/>
</t:datagrid>