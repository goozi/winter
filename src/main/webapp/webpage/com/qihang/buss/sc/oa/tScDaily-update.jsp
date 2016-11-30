<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>工作日志</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	<script type="text/javascript">
		//编写自定义JS代码
		$(function() {
			$("#dailyShare").combotree({
				onChange: function(n, o) {
					if($("#dailyShare").combotree("getValues") != "") {
						$("#dailyShare option").eq(1).attr("selected", true);
					} else {
						$("#dailyShare option").eq(1).attr("selected", false);
					}
				}
			});
			if(!${shareId eq null}){
				$("#dailyShare").combotree("setValues", ${shareId eq null ? "[]" : shareId});
			}

			if(${plan}){
				$("#workPlan").combobox("disable");
				$("#dailyShare").combotree("disable");
			}
		});
		//附件上传成功后方法
		//  function uploadSuccess(d,file,response){
		//	  if($("#streamName").val() == null) {
		//		  $("#streamName").val(d.attributes.url+",");
		//		  $("#fileName").val(file.name+",");
		//	  }else{
		//		  $("#streamName").val($("#streamName").val()+d.attributes.url+",");
		//		  $("#fileName").val($("#fileName").val()+file.name+",");
		//	  }
		//  }
		var fileIndex = 1;
		var fileNameIndex = 1;
		function uploadSuccess(d,file,response){
			$('#viewmsg').remove();
			var fileName = file.name;
			if($("#streamName").val() == null) {
				$("#streamName").val(d.attributes.url+",");
				$("#fileName").val(file.name+",");
			}else{
				$("#streamName").val($("#streamName").val()+d.attributes.url+",");
				var oldFileName = $("#fileName").val();
				if(oldFileName.indexOf(fileName) > -1){
					var files = fileName.split(".");
					fileName = files[0]+"_"+fileNameIndex+"."+files[1];
					fileNameIndex++;
				}
				$("#fileName").val($("#fileName").val()+fileName+",");
			}
			var table = document.getElementById("dg");
			var tr = document.createElement("tr");
			var fileId = "file"+fileIndex;
			fileIndex++;
			tr.setAttribute("id",fileId);
			var headTd = document.createElement("td");
			//var fileNode = document.createTextNode("附件");
			//headTd.appendChild(fileNode);
			tr.appendChild(headTd);
			var td = document.createElement("td");
			var a = document.createElement("a");
			var node = document.createTextNode("查看附件："+fileName);
			a.setAttribute("href",d.attributes.url);
			a.setAttribute("id", d.attributes.url);
			a.setAttribute("target","_blank");
			a.appendChild(node);
			var da = document.createElement("a");
			var dnode = document.createTextNode("   删除");
			da.setAttribute("id",fileName);
			da.setAttribute("onclick","delFile('"+d.attributes.url+"','"+fileName+"','"+fileId+"')");
			da.appendChild(dnode);
			td.appendChild(a);
			td.appendChild(da);
			tr.appendChild(td);
			table.appendChild(tr);
		}
		function delFile(url,fName,fileId){
			var streamName = $("#streamName").val();
			streamName = streamName.replace(url+",","");
			$("#streamName").val(streamName);

			var fileName = $("#fileName").val();
			fileName = fileName.replace(fName+",","");
			$("#fileName").val(fileName);

			$("#"+fileId).remove();
		}
		//删除附件
		function removeFile(id){
			$("#"+id).remove();
			var delFileId = $("#delFileId").val();
			if(delFileId == ""){
				$("#delFileId").val(id+",");
			}else{
				$("#delFileId").val(delFileId+id+",");
			}
		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScDailyController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${tScDailyPage.id }">
	<input id="createName" name="createName" type="hidden" value="${tScDailyPage.createName }">
	<input id="createBy" name="createBy" type="hidden" value="${tScDailyPage.createBy }">
	<input id="createDate" name="createDate" type="hidden" value="${tScDailyPage.createDate }">
	<input id="updateName" name="updateName" type="hidden" value="${tScDailyPage.updateName }">
	<input id="updateBy" name="updateBy" type="hidden" value="${tScDailyPage.updateBy }">
	<input id="updateDate" name="updateDate" type="hidden" value="${tScDailyPage.updateDate }">
	<input id="delFileId" name="delFileId" type="hidden" >
	<table id="dg" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="80px">
				<label class="Validform_label">
					日志标题:
				</label>
			</td>
			<td class="value">
				<input id="dailyTitle" name="dailyTitle" type="text" style="width: 150px" class="inputxt"
							 datatype="*1-100" maxlength="100"
					   value='${tScDailyPage.dailyTitle}'>
							<span class="Validform_checktip">
                                  *
							</span>
				<label class="Validform_label" style="display: none;">日志标题</label>
			</td>
		</tr>
		<tr>
			<td align="right" width="80px">
				<label class="Validform_label">
					日志类型:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="dailyType" type="list"
							  typeGroupCode="dailyType" defaultVal="${tScDailyPage.dailyType}" hasLabel="false"  title="日志类型"></t:dictSelect>
							<span class="Validform_checktip">
                                  *
							</span>
				<label class="Validform_label" style="display: none;">日志类型</label>
			</td>
		</tr>
		<tr>
			<td align="right" width="80px">
				<label class="Validform_label">
					日志时间:
				</label>
			</td>
			<td class="value">
				<input id="dailyTime" name="dailyTime" type="text" style="width: 150px"
					   class="Wdate" onClick="WdatePicker()"
					   datatype="*"
					   value='<fmt:formatDate value='${tScDailyPage.dailyTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
                                  *
							</span>
				<label class="Validform_label" style="display: none;">日志时间</label>
			</td>
		</tr>
		<tr>
			<td align="right" width="80px">
				<label class="Validform_label">
					工作计划:
				</label>
			</td>
			<td class="value">
					<%--<t:dictSelect field="workPlan" type="list"--%>
					<%--dictTable="t_oa_plan" dictField="id" dictText="plan_name" defaultVal="${tOaDailyPage.workPlan}" hasLabel="false"  title="工作计划"></t:dictSelect>     --%>
					<%--<span class="Validform_checktip">--%>
					<%--</span>--%>
					<%--<label class="Validform_label" style="display: none;">工作计划</label>--%>
				<input class="easyui-combobox" value="${tScDailyPage.workPlan}" data-options="
							  url:'tScDailyController.do?loadPlanInfo',
							  multiple:false,
							  textField:'name',
							  valueField:'id',
							  editable:false,
							  panelHeight:'auto'"
					   id="workPlan" name="workPlan" style="width: 155px" <c:if test="${isBinding}"> datatype="select1"</c:if>>
				</input><a href="tScPlanController.do?goUpdate&plan=true&load=detail&id=${tScDailyPage.workPlan}" target="_blank">查看</a>
				<span class="Validform_checktip"><c:if test="${isBinding}">*</c:if></span>
				<label class="Validform_label" style="display: none;">工作计划</label>
			</td>
		</tr>
		<tr>
			<td align="right" width="80px">
				<label class="Validform_label">
					日志内容:
				</label>
			</td>
			<td class="value">
				<t:ckeditor name="dailyInfo" isfinder="false" type="width:document.body.availWidth,height:200,toolbar:[
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    ['Link','Unlink','Anchor'],
    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
    ['Styles','Format','Font','FontSize'],
    ['TextColor','BGColor'],
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value="${tScDailyPage.dailyInfo}"></t:ckeditor>
	<span class="Validform_checktip" >
							</span>
				<label class="Validform_label" style="display: none;">日志内容</label>
			</td>
		</tr>
		<tr>
			<td align="right" width="80px">
				<label class="Validform_label">
					共享范围:
				</label>
			</td>
			<td class="value" >
				<input class="easyui-combotree" data-options="
                          url:'tScDailyController.do?loadShareInfo',
                          multiple:true,
                          editable:false,
                          width:450,
                          panelHeight:'auto'"
					   id="dailyShare" name="dailyShare" >
				</input><c:if test="${!plan}"><a class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="$('#daily_share').combotree('reset')">清空</a></c:if>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">共享范围</label>
			</td>
		</tr>
		<c:forEach var="child" items="${tScDailyPage.childernFile}">
			<tr id="${child.id}">
				<td class="value" width="80px">
					<a href="${child.streamName}" target="_blank">查看附件:${child.realName}</a><c:if test="${!plan}"><a href="javascript:void(0)" onclick="removeFile('${child.id}')">&nbsp;&nbsp;删除</a></c:if>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${!plan}">
			<tr>
				<td align="right" width="80px"><label class="Validform_label">上传文件:</label></td>
				<td class="value"><t:upload name="childernFile" dialog="false" queueID="childernUpFile" onUploadSuccess="uploadSuccess" view="true" multi="true" auto="true" uploader="systemController.do?saveFiles" extend="*.*" id="childernFile"
											formData="streamName"></t:upload>
					<input id="streamName" name="streamName" type="hidden"/>
					<input id="fileName" name="fileName" type="hidden"/>
				</td>
			</tr>
			<%--<tr>--%>
				<%--<td id="childernUpFile" class="value"></td>--%>
			<%--</tr>--%>
		</c:if>
	</table>
</t:formvalid>
</body>
<script src = "webpage/com/qihang/buss/sc/oa/tScDaily.js"></script>