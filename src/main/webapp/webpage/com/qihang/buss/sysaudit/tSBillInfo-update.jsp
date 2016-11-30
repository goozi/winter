<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>单据类型设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" beforeSubmit="setCheckInfo" action="tSBillInfoController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tSBillInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSBillInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSBillInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSBillInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSBillInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSBillInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSBillInfoPage.updateDate }">
					<input id="pid" name="pid" type="hidden" value="${tSBillInfoPage.pid }">
					<input id="billNo" name="billNo" type="hidden" value="${tSBillInfoPage.billNo }">
	                <input id="backZero" value="${tSBillInfoPage.backZero}" type="hidden">
	                <input name="isEdit" type="hidden" value="${tSBillInfoPage.isEdit}">
	  				<input name="isOffOn" type="hidden" value="${tSBillInfoPage.isOffOn}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								单据名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="billName" name="billName" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSBillInfoPage.billName}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								单据类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="billId" name="billId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSBillInfoPage.billId}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								前缀:
							</label>
						</td>
						<td class="value">
						     	 <input id="prefix" name="prefix" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tSBillInfoPage.prefix}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">前缀</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								日期格式:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="dateFormatter" type="list"
										typeGroupCode="SC_DF" defaultVal="${tSBillInfoPage.dateFormatter}" hasLabel="false"  title="日期格式"></t:dictSelect>     
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">日期格式</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								流水号长度:
							</label>
						</td>
						<td class="value">
						     	 <input id="billNoLen" name="billNoLen" type="text" datatype="number" style="width: 150px" class="inputxt"
									               
										       value='${tSBillInfoPage.billNoLen}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">流水号长度</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								允许手工编辑单据号:
							</label>
						</td>
						<td class="value">
									<%--<t:dictSelect field="isEdit" type="checkbox"--%>
										<%--typeGroupCode="" defaultVal="${tSBillInfoPage.isEdit}" hasLabel="false"  title="允许手工编辑单据号"></t:dictSelect>     --%>
							<input id="isEdit"  onchange="onChange('isEdit');" type="checkbox" value="1">
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">允许手工编辑单据号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								允许断号:
							</label>
						</td>
						<td class="value">
									<%--<t:dictSelect field="isOffOn" type="checkbox"--%>
										<%--typeGroupCode="" defaultVal="${tSBillInfoPage.isOffOn}"  hasLabel="false"  title="允许断号"></t:dictSelect>--%>
										<input id="isOffOn"  onchange="onChange('isOffOn');" type="checkbox" value="1">
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">允许断号</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								流水号归零:
							</label>
						</td>
						<td class="value">
							<input  name="backZero" type="radio" value="0">永不归零</br>
							<input  name="backZero" type="radio" value="1">跨日归零</br>
							<input  name="backZero" type="radio" value="2">跨月归零</br>
							<input  name="backZero" type="radio" value="3">跨年归零
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">流水号归零</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sysaudit/tSBillInfo.js"></script>		