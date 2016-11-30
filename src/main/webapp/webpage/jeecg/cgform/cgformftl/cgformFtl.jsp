<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>上传Word布局模板</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="div" beforeSubmit="upload" windowType="dialog">
  <input id="id" name="id" type="hidden" value="${cgformFtlPage.id }">
  <input id="cgformId" name="cgformId" type="hidden" value="${cgformFtlPage.cgformId }">
  <input id="editorType" name="editorType" type="hidden" value="01">
  <fieldset class="step">
    <div class="form">
      <label class="Validform_label">表单模板名称:</label>
      <input class="inputxt" id="cgformName" name="cgformName" value="${cgformFtlPage.cgformName}" datatype="*">
      <span class="Validform_checktip"></span></div>
    <div class="form">
      <t:upload name="fiels" buttonText="选择Word模板文件" uploader="cgformFtlController.do?saveWordFiles&editorType=03" extend="*.doc"
                id="file_upload" formData="id,cgformId,cgformName,ftlVersion,ftlStatus"></t:upload>
    </div>
    <div class="form" id="filediv" style="height: 50px"></div>

  </fieldset>
</t:formvalid>
<script type="text/javascript">
  $(function () {
    $("#formobj").Validform({
      tiptype: 1,
      btnSubmit: "#btn_sub",
      btnReset: "#btn_reset",
      ajaxPost: true,
      usePlugin: {
        passwordstrength: {
          minLen: 6, maxLen: 18, trigger: function (obj, error) {
            if (error) {
              obj.parent().next().find(".Validform_checktip").show();
              obj.find(".passwordStrength").hide();
            } else {
              $(".passwordStrength").show();
              obj.parent().next().find(".Validform_checktip").hide();
            }
          }
        }
      },
      callback: function (data) {
        if (data.success == true) {
          uploadFile(data);
        } else {
          if (data.responseText == '' || data.responseText == undefined) {
            $.messager.alert('错误', data.msg);
            $.Hidemsg();
          } else {
            try {
              var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'), data.responseText.indexOf('错误信息'));
              $.messager.alert('错误', emsg);
              $.Hidemsg();
            } catch (ex) {
              $.messager.alert('错误', data.responseText + '');
            }
          }
          return false;
        }
        if (!neibuClickFlag) {
          var win = frameElement.api.opener;
          win.reloadTable();
        }
      }
    });
  });
</script>
</body>
<div align="center" id="sub_tr" style="display: none;">
  <input class="ui_state_highlight" onclick="neibuClick()"
         type="button" value="提交"/>
</div>
<script type="text/javascript">
  $(function () {
    if (location.href.indexOf("mode=read") != -1) {
      $('#formobj').find(':input').attr('disabled', 'disabled');
    }
    if (location.href.indexOf("mode=onbutton") != -1) {
      $("#sub_tr").show();
    }
  });
  var neibuClickFlag = false;
  function neibuClick() {
    neibuClickFlag = true;
    $('#btn_sub').trigger('click');
  }
</script>
<script type="text/javascript">
${js_plug_in }
</script>