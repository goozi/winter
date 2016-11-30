<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>工作计划</title>
  <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码

  function setOrgIds(curform) {
      var masterIds = $("#masterSelect").combotree("getValues");
      $("#planMaster").val(masterIds);

      var groupIds = $("#groupSelect").combotree("getValues");
      $("#planGroup").val(groupIds);

      var leaderIds = $("#leaderSelect").combotree("getValues");
      $("#planLeadder").val(leaderIds);

      var startDate = $("#planStartdate").val();
      var endDate = $("#planEnddate").val();
      if(startDate && endDate) {
          var dates = new Date(startDate).getTime();
          var datee = new Date(endDate).getTime();
          if (dates > datee) {
              $.messager.alert(
                      "提示",
                      "开始日期不可大于结束日期"
              )
              return false;
          }
      }
	  var content = CKEDITOR.instances.planInfo_text;
	  if(content.document.getBody().getText().trim()==""){
		  $.messager.alert("提示","请填写计划内容");
		  return false;
	  }
  }
  $(function() {
	  $("#masterSelect").combotree({
		  onChange: function(n, o) {
			  if($("#masterSelect").combotree("getValues") != "") {
				  $("#masterSelect option").eq(1).attr("selected", true);
			  } else {
				  $("#masterSelect option").eq(1).attr("selected", false);
			  }
		  }
	  });
	  $("#masterSelect").combotree("setValues", ${masterIdList});

	  $("#groupSelect").combotree({
		  onChange: function(n, o) {
			  if($("#groupSelect").combotree("getValues") != "") {
				  $("#groupSelect option").eq(1).attr("selected", true);
			  } else {
				  $("#groupSelect option").eq(1).attr("selected", false);
			  }
		  }
	  });
	  $("#groupSelect").combotree("setValues", ${groupIdList});

	  $("#leaderSelect").combotree({
		  onChange: function(n, o) {
			  if($("#leaderSelect").combotree("getValues") != "") {
				  $("#leaderSelect option").eq(1).attr("selected", true);
			  } else {
				  $("#leaderSelect option").eq(1).attr("selected", false);
			  }
		  }
	  });
	  $("#leaderSelect").combotree("setValues", ${leaderIdList});

	  if(!${editAble}){
		  $("select[name='planType']").attr({'disabled':true});
		  $("#planName").attr({'disabled':true});
		  $("#masterSelect").combotree('disable');
		  $("#groupSelect").combotree('disable');
		  $("#leaderSelect").combotree('disable');
		  $('#planStartdate').attr({'disabled':true});
		  $('#planEnddate').attr({'disabled':true});
		  $("#planInfo_text").attr({'disabled':'disabled'});
	  }
  });
  //附件上传成功后方法
//  function uploadSuccess(d,file,response){
//      if($("#streamName").val() == null) {
//          $("#streamName").val(d.attributes.url+",");
//          $("#fileName").val(file.name+",");
//      }else{
//          $("#streamName").val($("#streamName").val()+d.attributes.url+",");
//          $("#fileName").val($("#fileName").val()+file.name+",");
//      }
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
  //日期大小校验
  function checkDate(){
      var startDate = $("#planStartdate").val();
      var endDate = $("#planEnddate").val();
      if(startDate && endDate) {
          var dates = new Date(startDate).getTime();
          var datee = new Date(endDate).getTime();
          if (dates > datee) {
              $.messager.alert(
                      "提示",
                      "开始日期不可小于结束日期"
              )
          }
      }
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScPlanController.do?doUpdate" beforeSubmit="setOrgIds" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScPlanPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScPlanPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScPlanPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScPlanPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScPlanPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScPlanPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScPlanPage.updateDate }">
	  				<input id="streamName" name="streamName" type="hidden">
                    <input id="fileName" name="fileName" type="hidden">
	  				<input id="delFileId" name="delFileId" type="hidden" >
	  <table id="dg" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								计划类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="planType" type="list"
										typeGroupCode="planType" defaultVal="${tScPlanPage.planType}" hasLabel="false"  title="计划类型"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">计划类型</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								计划名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="planName" name="planName" type="text" style="width: 150px" class="inputxt"
										datatype="*1-100" value='${tScPlanPage.planName}'>
							<span class="Validform_checktip">*</span>
							<label class="Validform_label" style="display: none;">计划名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								负责人:
							</label>
						</td>
						<td class="value">
							<select class="easyui-combotree" data-options="width:155,url:'tScPlanController.do?userSel',onlyLeafCheck:false, multiple:true, cascadeCheck:true,disabled:${load eq 'detail'}"
									id="masterSelect" name="masterSelect" datatype="select1">
								<c:forEach items="${masterList}" var="master">
									<option value="${master.id }">${master.userName}</option>
								</c:forEach>
							</select>
							<input id="planMaster" name="planMaster" type="hidden">
							<span class="Validform_checktip">*</span>
						</td>
						<td align="right">
							<label class="Validform_label">
								参与人:
							</label>
						</td>
						<td class="value">
							<select class="easyui-combotree" data-options="width:155,url:'tScPlanController.do?userSel',onlyLeafCheck:false, multiple:true, cascadeCheck:true,disabled:${load eq 'detail'?true:false}"
									id="groupSelect" name="groupSelect" datatype="select1">
								<c:forEach items="${groupList}" var="group">
									<option value="${group.id }">${group.userName}</option>
								</c:forEach>
							</select>
							<input id="planGroup" name="planGroup" type="hidden">
							<span class="Validform_checktip">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								领导批注:
							</label>
						</td>
						<td class="value">
							<select class="easyui-combotree" data-options="width:155,url:'tScPlanController.do?userSel',onlyLeafCheck:false, multiple:true, cascadeCheck:true,disabled:${load eq 'detail'?true:false}"
									id="leaderSelect" name="leaderSelect" datatype="select1">
								<c:forEach items="${leaderList}" var="leader">
									<option value="${leader.id }">${leader.userName}</option>
								</c:forEach>
							</select>
							<input id="planLeadder" name="planLeadder" type="hidden">
							<span class="Validform_checktip">*</span>
						</td>
						<td align="right">
							<label class="Validform_label">
								计划人:
							</label>
						</td>
						<td class="value">
							<input id="planer" name="planer" readonly="readonly" type="text" style="width: 150px" class="inputxt"

								   value='${tScPlanPage.planer}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">计划人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								开始时间:
							</label>
						</td>
						<td class="value">
							<input id="planStartdate" name="planStartdate"  type="text" style="width: 150px"
								   class="Wdate" onClick="WdatePicker()" datatype="*" onchange="checkDate()"

								   value='<fmt:formatDate value='${tScPlanPage.planStartdate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">开始时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								结束时间:
							</label>
						</td>
						<td class="value">
									  <input id="planEnddate" name="planEnddate" type="text" style="width: 150px"
						      						class="Wdate" onClick="WdatePicker()" datatype="*" onchange="checkDate()"
									                
						      						 value='<fmt:formatDate value='${tScPlanPage.planEnddate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">结束时间</label>
						</td>
					</tr>
					<tr>
							<td class="value" colspan="4">
									<t:ckeditor name="planInfo" isfinder="false" type="width:document.body.availWidth,toolbar:[
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    ['Link','Unlink','Anchor'],
    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
    ['Styles','Format','Font','FontSize'],
    ['TextColor','BGColor'],
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value="${tScPlanPage.planInfo}"></t:ckeditor>
								<span class="Validform_checktip">*</span>
								<label class="Validform_label" style="display: none;">计划内容</label>
							</td>
					</tr>
					<c:forEach var="child" items="${tScPlanPage.childernFile}">
						<tr id="${child.id}">
							<td class="value" colspan="4">
								<a href="${child.streamName}" target="_blank">查看附件:${child.realName}</a><c:if test="${!plan}"><a href="javascript:void(0)" onclick="removeFile('${child.id}')">&nbsp;&nbsp;删除</a></c:if>
							</td>
						</tr>
					</c:forEach>
                    <c:if test="${!plan}">
					<tr>
						<td align="right"><label class="Validform_label">上传文件:</label></td>
						<td class="value"><t:upload name="childernFile" dialog="false" queueID="childernUpFile" onUploadSuccess="uploadSuccess" view="true" multi="true" auto="true" uploader="systemController.do?saveFiles" extend="*.*" id="childernFile"
													formData="streamName"></t:upload>
						</td>
						<td colspan="2" id="childernUpFile" class="value"></td>
					</tr>
                    </c:if>
            <c:if test="${plan}">
                <tr>
                    <td align="right" style="height: 80px">
                        <label class="Validform_label">
                            计划进度:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                            <%--<input id="planProgress" name="planProgress" type="text" style="width: 150px" class="inputxt"--%>

                            <%-->--%>
                        <input id="planProgress" name="planProgress" class="easyui-slider" value="${tScPlanPage.planProgress eq null ? 0 : tScPlanPage.planProgress}"  style="width:300px"
                               data-options="showTip:true,rule:[0,'|',25,'|',50,'|',75,'|',100],disabled:${load eq 'detail'?true:false}" />
                      <span class="Validform_checktip">
                      </span>
                        <label class="Validform_label" style="display: none;">计划进度</label>
                    </td>
                </tr>
            </c:if>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/oa/tScPlan.js"></script>