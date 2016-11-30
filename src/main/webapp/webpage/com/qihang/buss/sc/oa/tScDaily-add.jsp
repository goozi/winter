<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>工作日志</title>
  <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <%--<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>--%>
  <%--<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>--%>
  <script type="text/javascript">
    //编写自定义JS代码
    $(function(){
        <%--$("#dailyShare").combotree("setValues",${shareUser});--%>
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var day = date.getDate();
        var newDate = year+"-"+(month < 10 ? "0"+month : month)+"-"+(day < 10 ? "0"+day : day)
        $('#dailyTime').val(newDate);
    })
    var fileIndex = 1;
    var fileNameIndex = 1;
    function uploadSuccess(d,file,response){
        var fileName = file.name;
        $('#viewmsg').remove();
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
//    function removeFile(id,fileName){
//        var streamName = $("#streamName").val();
//        var fileName = $("#fileName").val();
//        var s = streamName.split(",");
//        var f = streamName.split(",");
//        for(var i=0 ; i < s.length ; i++){
//            var item = s[i];
//            if(id==item){
//                s.splice(i,1);
//                f.splice(i,1);
//                if(s.length > 0) {
//                    for (var j = 0; j < s.length; j++) {
//                        streamName += s[j] + ",";
//                        fileName += f[j] + ",";
//                    }
//                }else{
//                    streamName = "";
//                    fileName = "";
//                }
//                $("#streamName").val(streamName);
//                $("#fileName").val(fileName);
//                break;
//            }
//        }
//    }
//    function setShareInfo(){
//        var masterIds = $("#shareSelect").combotree("getValues");
//        $("#dailyShare").val(masterIds);
//    }
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tScDailyController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScDailyPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScDailyPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScDailyPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScDailyPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScDailyPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScDailyPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScDailyPage.updateDate }">
  <table id="dg" style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right" width="80px">
      <label class="Validform_label">
      日志标题:
      </label>
    </td>
    <td class="value">
          <input id="dailyTitle" name="dailyTitle" type="text" style="width: 150px" class="inputxt"
                 datatype="*1-100" maxlength="100"
>
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
                        typeGroupCode="dailyType"
                        defaultVal="工作日志" hasLabel="false"
                        title="日志类型"></t:dictSelect>
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
>
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
          <input class="easyui-combobox" data-options="
          url:'tScDailyController.do?loadPlanInfo',
          multiple:false,
          textField:'name',
          valueField:'id',
          editable:false,
          panelHeight:'auto'"
                  id="workPlan" style="width: 155px" name="workPlan"<c:if test="${isBinding}"> datatype="select1"</c:if>>
          </input>
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
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value=""></t:ckeditor>
      <span class="Validform_checktip">
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
          <td class="value">
              <input class="easyui-combotree" data-options="
                  url:'tScDailyController.do?loadShareInfo',
                  multiple:true,
                  editable:false,
                  width:450,
                  panelHeight:'auto'"
                     id="dailyShare" name="dailyShare" >
              </input><a class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="$('#dailyShare').combotree('reset')">清空</a>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">共享范围</label>
          </td>
      </tr>
      <tr>
          <td align="right" width="80px"><label class="Validform_label">上传文件:</label></td>
          <td class="value"><t:upload name="childernFile" dialog="false" queueID="childernUpFile" onUploadSuccess="uploadSuccess" view="true" multi="true" auto="true" uploader="systemController.do?saveFiles" extend="*.*" id="childernFile"
                                      formData="streamName"></t:upload>
              <input id="streamName" name="streamName" type="hidden"/>
              <input id="fileName" name="fileName" type="hidden"/>
          </td>
      </tr>
      <%--<tr>--%>
          <%--<td id="childernUpFile" class="value" hidden="true"></td>--%>
      <%--</tr>--%>
  </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/oa/tScDaily.js"></script>