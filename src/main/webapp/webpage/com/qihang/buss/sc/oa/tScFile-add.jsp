<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
  <t:base type="jquery,easyui,tools"></t:base>
  <script type="text/javascript">

    function uploadSuccess(d,file,response){
      $("#fileUrl").val(d.attributes.url);
      $("#fileName").val(d.attributes.name);
      $("#fileExtend").val(d.attributes.extend);
      $("#fileKey").val(d.attributes.fileKey);
      var url = $("#fileUrl").val();
      var html="";
      if(url.indexOf(".gif")!=-1 ||
              url.indexOf(".jpg")!=-1        ||
              url.indexOf(".png")!=-1 ||
              url.indexOf(".bmp")!=-1){
        html += "<img src='"+url+"' width =400 height=300 />";
      }else{
        html += "<a href='"+url+"' target=_blank >下载:"+d.attributes.name+"</a>";
      }
      $("#fileShow").html(html);
    }
    function uploadCallback(callback,inputId){
      var key = $("#fileKey").val();
      var name= $("#fileName").val();
      var extend= $("#fileExtend").val();
      var note= $("#note").val();
      callback(key,name,extend,note,inputId);

    }
  </script>
</head>
<body style="overflow-y: hidden" scroll="no">

<table cellpadding="0" cellspacing="1" class="formtable">
  <input id="documentTitle" type="hidden" name="documentTitle" value="blank"/>
  <input id="fileUrl" type="hidden"/>
  <input id="fileName" type="hidden"/>
  <input id="fileExtend" type="hidden"/>
  <input id="fileKey" type="hidden"/>
  <tbody>
  <tr>
    <td align="right">
      <label class="Validform_label"></label>
    </td>
    <td class="value">
      <t:upload name="instruction" size="1GB" dialog="false" multi="false" extend="*.*" queueID="instructionfile" view="false" auto="true" uploader="systemController.do?saveFiles" onUploadSuccess="uploadSuccess"  id="instruction" formData="documentTitle"></t:upload>
    </td>
  </tr>
  <tr>
    <td colspan="2" id="instructionfile" class="value">
    </td>
  </tr>
  <tr>
    <td align="right">
      备注
    </td>
    <td class="value">
      <textarea cols="50" rows="3" id="note"></textarea>
    </td>
  </tr>
  </tbody>
</table>
<div id="fileShow" >
</div>
</body>
</html>
