<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="modelList" fitColumns="false" title="流程模型管理" actionUrl="modelController.do?datagrid"
            pagination="true" checkbox="true"
            idField="id" fit="true" queryMode="group">
    <t:dgCol title="ID" field="id" queryMode="group" width="120"></t:dgCol>
    <t:dgCol title="KEY" field="key" queryMode="single" width="120" query="true"></t:dgCol>
    <t:dgCol title="名称" field="name" queryMode="single" width="120" query="true"></t:dgCol>
    <t:dgCol title="版本" field="version" queryMode="group" width="120"></t:dgCol>
    <t:dgCol title="创建时间" field="createTime" queryMode="single" width="120" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
    <t:dgCol title="最后更新时间" field="lastUpdateTime" queryMode="single" width="120" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
    <t:dgCol title="元数据" field="metaInfo" queryMode="single" width="120"></t:dgCol>
    <t:dgToolBar title="新建" icon="icon-add" url="modelController.do?toCreate" funname="add" windowType="tab"/>
    <t:dgToolBar title="编辑" icon="icon-edit" url="modelController.do?toUpdate" funname="update" windowType="tab"/>
    <t:dgToolBar title="批量删除" icon="icon-remove" url="modelController.do?doBatchDel"
                 funname="deleteALLSelect"></t:dgToolBar>
    <t:dgCol title="操作" field="opt" queryMode="group" width="200"></t:dgCol>
    <t:dgFunOpt title="导出BPMN" funname="exportBpmn(id)" />
    <t:dgFunOpt title="导出JSON" funname="exportJson(id)" />
    <%--<t:dgFunOpt title="导出SVG" funname="exportSvg(id)" />--%>
    <t:dgDelOpt title="删除" url="modelController.do?delete&modelId={id}"/>
</t:datagrid>
<script>
    function exportBpmn(id){
        location.href = 'modelController.do?export&modelId='+id+'&type=bpmn';
    }
    function exportJson(id){
        location.href = 'modelController.do?export&modelId='+id+'&type=json';
    }
//    function exportSvg(id){
//        location.href = 'modelController.do?export&modelId='+id+'&type=svg';
//    }
</script>