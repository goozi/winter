<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="roleController.do?saveNodePanel">
    <input name="id" type="hidden" value="${panel.id }">
    <input name="nodeId" type="hidden" value="${panel.nodeId}">
    <fieldset class="step">
        <%--// add-start--Author:zhangguoming  Date:20140928 for：添加显示字段--%>
        <div class="form">
            <label class="Validform_label"> 面板名称: </label>
            <input name ="panelName" class="inputxt" value="${panel.panelName }">
        </div>
        <%--// add-end--Author:zhangguoming  Date:20140928 for：添加显示字段--%>
        <div class="form">
            <label class="Validform_label"> 面板类型: </label>
            <t:dictSelect field="type" type="list"
                          typeGroupCode="NODE_VTYPE" defaultVal="${panel.type}" hasLabel="false"  title="类型"></t:dictSelect>
            <span class="Validform_checktip">*</span>
        </div>
        <div class="form">
            <label class="Validform_label"> 面板内容: </label>
            <input name="value" class="inputxt" datatype="*1-255" value="${panel.value}">
            <span class="Validform_checktip">*</span>
        </div>
            <div class="form">
                <label class="Validform_label"> 备注: </label>
                <textarea name="note" class="inputxt" >${panel.note}</textarea>
                <span class="Validform_checktip"></span>
            </div>
    </fieldset>
</t:formvalid>
</body>
</html>
