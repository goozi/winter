<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>文件柜</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScFileController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScFilePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScFilePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScFilePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScFilePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScFilePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScFilePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScFilePage.updateDate }">
					<input id="userId" name="userId" type="hidden" value="${tScFilePage.userId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScFilePage.name}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								路径:
							</label>
						</td>
						<td class="value">
						     	 <input id="url" name="url" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScFilePage.url}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">路径</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="type" name="type" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScFilePage.type}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								类别:
							</label>
						</td>
						<td class="value">
						     	 <input id="fileType" name="fileType" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScFilePage.fileType}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">类别</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="status" name="status" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScFilePage.status}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">状态</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						     	 <input id="note" name="note" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScFilePage.note}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/oa/tScFile.js"></script>