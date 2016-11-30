<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>流程树表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tSTreeinfoController.do?doAdd">
					<input id="id" name="id" type="hidden" value="${tSTreeinfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSTreeinfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSTreeinfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSTreeinfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSTreeinfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSTreeinfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSTreeinfoPage.updateDate }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">树名称:</label>
			</td>
			<td class="value">
		     	 <input id="treeName" name="treeName" type="text" style="width: 150px" class="inputxt"datatype="*1-30" >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">树名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">流程实例:</label>
			</td>
			<td class="value">
					<%--<t:dictSelect field="actId" type="list"--%>
						<%--dictTable="ACT_RE_PROCDEF" dictField="ID_" dictText="NAME_" dictCondition=" where "  hasLabel="false"  title="流程实例"></t:dictSelect>--%>
						<input class="easyui-combobox" id="actId" name="actId" data-options="valueField: 'id',
						textField: 'value',
						panelHeight:200,
						editable:false,
						url:'cgFormHeadController.do?loadFlowInfo&isAll=1'" >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">流程实例</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">备注:</label>
			</td>
			<td class="value" colspan="3">
				 <textarea id="note" style="width:600px;" class="inputxt" rows="6" name="note"></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
	</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tSTreeinfoController.do?tSTreeinfoEntryList&id=${tSTreeinfoPage.id}" icon="icon-search" title="流程树配置" id="tSTreeinfoEntry"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
	<table style="display:none">
	<tbody id="add_tSTreeinfoEntry_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
					  	<input name="tSTreeinfoEntryList[#index#].nodeName" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					               datatype="*1-30"
>
					  <label class="Validform_label" style="display: none;">节点名称</label>
				  </td>
				  <td align="left">
							<t:dictSelect field="tSTreeinfoEntryList[#index#].type" type="list"
										typeGroupCode="NODE_VTYPE" defaultVal="" hasLabel="false"  title="类型"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">类型</label>
				  </td>
				  <td align="left">
					  	<input name="tSTreeinfoEntryList[#index#].value" maxlength="255"
					  		type="text" class="inputxt"  style="width:120px;"

					               >
					  <label class="Validform_label" style="display: none;">内容</label>
				  </td>
				  <td align="left">
					  	<input name="tSTreeinfoEntryList[#index#].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">备注</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/system/tree/tSTreeinfo.js"></script>