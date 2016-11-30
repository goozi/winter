<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="runningList" fitColumns="false" title="运行中流程列表" actionUrl="activitiController.do?runningProcessDataGrid"
			pagination="true" checkbox="true"
			idField="id" fit="true" queryMode="group">
	<t:dgCol title="编号" field="id" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="流程定义" field="processDefinitionId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="流程实例" field="processInstanceId" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="activityId" field="activityId" queryMode="single" width="120" query="true"></t:dgCol>
	<t:dgCol title="操作" field="opt" queryMode="group" width="200"></t:dgCol>
	<t:dgOpenOpt url="activitiController.do?viewProcessInstanceHistory&processInstanceId={processInstanceId}&isIframe"
				 title="流程历史"/>
</t:datagrid>