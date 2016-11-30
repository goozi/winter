<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>多级审核</title>
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
	var leave = $("#leaveNum").val();
//	if(!leave){
//		document.getElementsByName("leaveNum")[0].options[1].selected="selected";
//	}
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" windowType="dialog"  callback="refreshTable" beforeSubmit="setCheckInfo" tiptype="1" action="tSAuditController.do?doUpdate">
					<input id="id" name="id" type="hidden" value="${tSAuditPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSAuditPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSAuditPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSAuditPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSAuditPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSAuditPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSAuditPage.updateDate }">
					<input id="funId" name="funId" type="hidden" value="${tSAuditPage.funId }">
					<input id="isMoney" name="isMoney" type="hidden" value="${tSAuditPage.isMoney }">
					<input id="minMoney" name="minMoney" type="hidden" value="${tSAuditPage.minMoney }">
					<input id="isMoneyLeave" name="isMoneyLeave" type="hidden" value="${tSAuditPage.isMoneyLeave }">
	                <input id="billId" name="billId" type="hidden" value="${tSAuditPage.billId }">
	                <input id="leaveNum" type="hidden" value="${tSAuditPage.leaveNum }">
	                <input id="sonId" name="sonId" type="hidden" value="${tSAuditPage.sonId }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">进行多级审核控制:</label>
			</td>
			<td class="value">
				<input type="checkbox" id="isAudit" name="isAudit" value="${tSAuditPage.isAudit}">
					<%--<t:dictSelect field="isAudit" type="checkbox"--%>
						<%--typeGroupCode="" defaultVal="${tSAuditPage.isAudit}" hasLabel="false"  title="进行多级审核控制"></t:dictSelect>     --%>
				<span class="Validform_checktip">
				</span>
				<label class="Validform_label" style="display: none;">进行多级审核控制</label>
			</td>
			</tr>
		    <tr>
			<td align="right">
				<label class="Validform_label">级次:</label>
			</td>
			<td class="value">
					<t:dictSelect field="leaveNum" type="list" showDefaultOption="false"
						typeGroupCode="SC_A_L" defaultVal="${tSAuditPage.leaveNum}" extendJson="{onchange:changeLine()}" hasLabel="false"  title="级次"></t:dictSelect>
				<span class="Validform_checktip">
				</span>
				<label class="Validform_label" style="display: none;">级次</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">审核时必须逐级审核:</label>
			</td>
			<td class="value">
					<%--<t:dictSelect field="isSort" type="checkbox"--%>
						<%--typeGroupCode="" defaultVal="${tSAuditPage.isSort}" hasLabel="false"  title="审核时必须逐级审核"></t:dictSelect>     --%>
						<input type="checkbox" id="isSort" name="isSort" value="${tSAuditPage.isSort}">
				<span class="Validform_checktip">
				</span>
				<label class="Validform_label" style="display: none;">审核时必须逐级审核</label>
			</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">修改无须反审核到最低一级审核:</label>
				</td>
				<td class="value">
						<%--<t:dictSelect field="isSort" type="checkbox"--%>
						<%--typeGroupCode="" defaultVal="${tSAuditPage.isSort}" hasLabel="false"  title="审核时必须逐级审核"></t:dictSelect>     --%>
					<input type="checkbox" id="isEdit" name="isEdit" value="${tSAuditPage.isEdit}">
				<span class="Validform_checktip">
				</span>
					<label class="Validform_label" style="display: none;">修改无须反审核到最低一级审核</label>
				</td>
			</tr>
		    <tr>
			<td align="right">
				<label class="Validform_label">使用工作流消息:</label>
			</td>
			<td class="value">
					<%--<t:dictSelect field="isSendMessage" type="checkbox"--%>
						<%--typeGroupCode="" defaultVal="${tSAuditPage.isSendMessage}" hasLabel="false"  title="使用工作流消息"></t:dictSelect>     --%>
						<input type="checkbox" id="isSendMessage" name="isSendMessage" value="${tSAuditPage.isSendMessage}">
				<span class="Validform_checktip">
				</span>
				<label class="Validform_label" style="display: none;">使用工作流消息</label>
			</td>
		</tr>
		<%--<tr>--%>
			<%--<td align="right">--%>
				<%--<label class="Validform_label">启用审核拒绝功能:</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
					<%--&lt;%&ndash;<t:dictSelect field="isUnaudit" type="checkbox"&ndash;%&gt;--%>
						<%--&lt;%&ndash;typeGroupCode="" defaultVal="${tSAuditPage.isUnaudit}" hasLabel="false"  title="启用审核拒绝功能"></t:dictSelect>     &ndash;%&gt;--%>
						<%--<input type="checkbox" id="isUnaudit" name="isUnaudit" value="${tSAuditPage.isUnaudit}">--%>
				<%--<span class="Validform_checktip">--%>
				<%--</span>--%>
				<%--<label class="Validform_label" style="display: none;">启用审核拒绝功能</label>--%>
			<%--</td>--%>
		<%--</tr>--%>
			</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tSAuditController.do?tSAuditLeaveList&id=${tSAuditPage.id}" icon="icon-search" title="多级审核分级信息" id="tSAuditLeave"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
		<tbody id="add_tSAuditLeave_table_template">
			<tr>
			 <%--<td align="center"><div style="width: 25px;" name="xh"></div></td>--%>
			 <%--<td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>--%>
				  <td align="left" bgcolor="white">
					  	<input name="tSAuditLeaveList[#index#].leaveNum" maxlength="50" 
					  		type="text" class="inputxt" readonly="readonly" style="width:50px;text-align: center"
					               
					               >
					  <label class="Validform_label" style="display: none;">级次</label>
				  </td>
				  <td align="left" bgcolor="white">
					  <input id="tree[#index#]"  name="tSAuditLeaveList[#index#].auditorId" />
							<%--<t:dictSelect field="tSAuditLeaveList[#index#].auditorId" type="list"--%>
										<%--dictTable="T_S_Base_User" dictField="id" dictText="realName" defaultVal="" hasLabel="false"  title="审核人"></t:dictSelect>--%>
					  <label class="Validform_label" style="display: none;">审核人</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/qihang/buss/sysaudit/tSAudit.js"></script>	