<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="common.role.info"/></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="roleController.do?saveRole" windowType="dialog">
	<c:if test="${role != null}">
	<input name="id" type="hidden" value="${role.id}">
	</c:if>
	<fieldset class="step">
	<div class="form"><label class="Validform_label"><t:mutiLang langKey="common.role.name"/>:</label> <input name="roleName" class="inputxt" <c:if test="${role != null}"> value="${role.roleName }" </c:if> datatype="s2-8"> <span class="Validform_checktip"><t:mutiLang langKey="rolescope.rang2to8.notnull"/></span>
	</div>
	<div class="form">
        <label class="Validform_label"> <t:mutiLang langKey="common.role.code"/>: </label>
        <input name="roleCode" id="roleCode" <c:if test="${role != null}"> ajaxurl="roleController.do?checkRole&code=${role.roleCode }" </c:if> class="inputxt"
		<c:if test="${role != null}"> value="${role.roleCode }" </c:if> datatype="s2-15"> <span class="Validform_checktip"><t:mutiLang langKey="rolecode.rang2to15.notnull"/></span></div>
        <div class="form"><label class="Validform_label"> 项目代码: </label>
			<t:dictSelect field="projectCode" defaultVal="${role.projectCode}" typeGroupCode="PROJECT_CODE" hasLabel="false"/>
			<span class="Validform_checktip">当前开发项目代码，如不清楚请咨询管理员</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
