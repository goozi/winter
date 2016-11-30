<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title><t:mutiLang langKey="common.add.param"/></title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveTypeGroup" windowType="dialog">
	<input name="id" type="hidden" value="${typegroup.id }">
	<fieldset class="step">
	<div class="form">
	<label class="Validform_label"> <t:mutiLang langKey="common.code"/>: </label> 
	<input name="typegroupcode" class="inputxt" validType="t_s_typegroup,typegroupcode,id" value="${typegroup.typegroupcode }" datatype="s2-30"> <span class="Validform_checktip">编码范围2~30</span></div>
	<div class="form">
		<label class="Validform_label"> <t:mutiLang langKey="common.name"/>: </label>
		<input name="typegroupname" class="inputxt" value="${typegroup.typegroupname }" datatype="s2-20"> <span class="Validform_checktip">名称范围2~20</span></div>


		<c:if test='${sessionScope.user.userName=="programmer"}'>
		<div class="form">
			<label class="Validform_label"> 项目代码: </label>
			<t:dictSelect field="projectCode" defaultVal="${typegroup.projectCode}" typeGroupCode="PROJECT_CODE" hasLabel="false"/>
			<span class="Validform_checktip">当前开发项目代码，如不清楚请咨询管理员</span>
		</div>
		</c:if>

		<c:if test='${sessionScope.user.userName!="programmer"}'>
			<input name="projectCode" class="inputxt" value="${typegroup.projectCode}" type="hidden">
		</c:if>
		<div class="form"><label class="Validform_label"> 用户自定义: </label>
			<t:dictSelect field="userCustom" defaultVal="${typegroup.userCustom}" typeGroupCode="sf_01" hasLabel="false" showDefaultOption="false"/>
			<span class="Validform_checktip">用户自定义字典则用户可修改，否则只有超级管理员才能修改</span></div>
	</fieldset>
</t:formvalid>
</body>
</html>
