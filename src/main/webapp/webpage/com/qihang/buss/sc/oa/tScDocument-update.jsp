<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>文档结构</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScDocumentController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScDocumentPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScDocumentPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScDocumentPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScDocumentPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScDocumentPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScDocumentPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScDocumentPage.updateDate }">
					<input id="pid" name="pid" type="hidden" value="${tScDocumentPage.pid }">
					<input id="type" name="type" type="hidden" value="${tScDocumentPage.type }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDocumentPage.name}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/oa/tScDocument.js"></script>