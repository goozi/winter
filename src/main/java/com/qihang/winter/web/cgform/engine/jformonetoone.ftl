<script>
    $(document).ready(function(){
        //解决一对多页面中列表页输入框tip属性失效问题
        $('table').find("[tip]").each(function () {
            var defaultvalue = $(this).attr("tip");
            var altercss = $(this).attr("altercss");
            $(this).focus(function () {
                if ($(this).val() == defaultvalue) {
                    $(this).val('');
                    if (altercss) {
                        $(this).removeClass(altercss);
                    }
                }
            }).blur(function () {
                if ($.trim($(this).val()) === '') {
                    $(this).val(defaultvalue);
                    if (altercss) {
                        $(this).addClass(altercss);
                    }
                }
            });
        });
    });
</script>
<div title="${field['${sub}'].head.content?if_exists?html}" style="margin:0px;padding:0px;overflow:hidden;">
  <div>
    <table cellpadding="0" cellspacing="1" class="formtable" id="${sub}_table">
    <#if data['${sub}']?exists&&(data['${sub}']?size>0) >
        <#if (data['${sub}']?size>1) >
          <div><font color="red">该附表下存在多条数据</font></div>
        <#else>
            <#list data['${sub}'] as subTableData >
              <input type="hidden" name="${sub}[${subTableData_index}].id" id="${sub}[${subTableData_index}].id"
                     value="${subTableData['id']?if_exists?html}"/>
                <#list field['${sub}'].hiddenFieldList as subTableField >
                  <input type="hidden" name="${sub}[${subTableData_index}].${subTableField.field_name}"
                         id="${sub}[${subTableData_index}].${subTableField.field_name}"
                         value="${subTableData['${subTableField.field_name}']?if_exists?html}}"/>
                </#list>
                <#list field['${sub}'].fieldNoAreaList as subTableField >
                    <#if subTableField_index%2==0>
                    <tr>
                    </#if>
                  <td align="right">
                    <label class="Validform_label">
                    ${subTableField.content?if_exists?html}
                    </label>
                  </td>
                  <td class="value">
                      <#if subTableField.show_type=='text'>
                        <input id="${sub}[${subTableData_index}].${subTableField.field_name}"
                               name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
                               style="width: 150px" class="inputxt"
                               value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                               nullmsg="请输入${subTableField.content}！"

                            <#if subTableField.field_valid_type?if_exists?html != ''>
                               datatype="${subTableField.field_valid_type?if_exists?html}"
                            <#else>
                                <#if subTableField.type == 'int'>
                               datatype="n"
                                <#elseif subTableField.type=='double'>
                               datatype="/^(-?\d+)(\.\d+)?$/"
                                <#else>
                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                                </#if></#if>>

                      <#elseif subTableField.show_type=='password'>
                        <input id="${sub}[${subTableData_index}].${subTableField.field_name}"
                               name="${sub}[${subTableData_index}].${subTableField.field_name}" type="password"
                               style="width: 150px" class="inputxt"
                               value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                               nullmsg="请输入${subTableField.content}！"

                            <#if subTableField.field_valid_type?if_exists?html != ''>
                               datatype="${subTableField.field_valid_type?if_exists?html}"
                            <#else>
                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                            </#if>>

                      <#elseif subTableField.show_type=='radio'>
                          <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
                              <#list dataList as dictdata>
                                <input value="${dictdata.typecode?if_exists?html}"
                                       name="${sub}[${subTableData_index}].${subTableField.field_name}" type="radio"
                                       <#if dictdata_index==0&&subTableField.is_null != 'Y'>datatype="*"</#if>
                                    <#if dictdata.typecode?if_exists?html=="${subTableData['${subTableField.field_name}']?if_exists?html}">
                                       checked="true" </#if>>
                              ${dictdata.typename?if_exists?html}
                              </#list>
                          </@DictData>

                      <#elseif subTableField.show_type=='checkbox'>
                          <#assign checkboxstr>${subTableData['${subTableField.field_name}']?if_exists?html}</#assign>
                          <#assign checkboxlist=checkboxstr?split(",")>
                          <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
                              <#list dataList as dictdata>
                                <input value="${dictdata.typecode?if_exists?html}"
                                       name="${sub}[${subTableData_index}].${subTableField.field_name}" type="checkbox"
                                       <#if dictdata_index==0&&subTableField.is_null != 'Y'>datatype="*"</#if>
                                    <#list checkboxlist as x >
                                        <#if dictdata.typecode?if_exists?html=="${x?if_exists?html}">
                                       checked="true" </#if></#list>>
                              ${dictdata.typename?if_exists?html}
                              </#list>
                          </@DictData>

                      <#elseif subTableField.show_type=='list'>
                          <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
                            <select id="${sub}[${subTableData_index}].${subTableField.field_name}"
                                    name="${sub}[${subTableData_index}].${subTableField.field_name}"
                                    <#if subTableField.is_null != 'Y'>datatype="*"</#if>>
                                <#list dataList as dictdata>
                                  <option value="${dictdata.typecode?if_exists?html}"
                                      <#if dictdata.typecode?if_exists?html=="${subTableData['${subTableField.field_name}']?if_exists?html}">
                                          selected="selected" </#if>>
                                  ${dictdata.typename?if_exists?html}
                                  </option>
                                </#list>
                            </select>
                          </@DictData>

                      <#elseif subTableField.show_type=='date'>
                        <input id="${sub}[${subTableData_index}].${subTableField.field_name}"
                               name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
                               style="width: 150px"
                               value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                               class="Wdate" onClick="WdatePicker()"
                               nullmsg="请输入${subTableField.content}！"

                            <#if subTableField.field_valid_type?if_exists?html != ''>
                               datatype="${subTableField.field_valid_type?if_exists?html}"
                            <#else>
                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                            </#if>>

                      <#elseif subTableField.show_type=='datetime'>
                        <input id="${sub}[${subTableData_index}].${subTableField.field_name}"
                               name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
                               style="width: 150px"
                               value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                               class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               nullmsg="请输入${subTableField.content}！"

                            <#if subTableField.field_valid_type?if_exists?html != ''>
                               datatype="${subTableField.field_valid_type?if_exists?html}"
                            <#else>
                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                            </#if>>

                      <#elseif subTableField.show_type=='popup'>
                        <input id="${sub}[${subTableData_index}].${subTableField.field_name}"
                               name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
                               style="width: 150px" class="searchbox-inputtext15"
                               onClick="inputClick(this,'${subTableField.dict_text?if_exists?html}','${subTableField.dict_table?if_exists?html}');"
                               value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                               nullmsg="请输入${subTableField.content}！"

                            <#if subTableField.field_valid_type?if_exists?html != ''>
                               datatype="${subTableField.field_valid_type?if_exists?html}"
                            <#else>
                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                            </#if>>

                      <#elseif subTableField.show_type=='file'>
                        <#--<input id="${sub}[${subTableData_index}].${subTableField.field_name}"-->
                               <#--name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"-->
                               <#--style="width: 150px" class="inputxt"-->
                               <#--value="${subTableData['${subTableField.field_name}']?if_exists?html}"-->
                               <#--nullmsg="请输入${subTableField.content}！"-->

                            <#--<#if subTableField.field_valid_type?if_exists?html != ''>-->
                               <#--datatype="${subTableField.field_valid_type?if_exists?html}"-->
                            <#--<#else>-->
                               <#--<#if subTableField.is_null != 'Y'>datatype="*"</#if>-->
                            <#--</#if>>-->
                          <table>
                              <#list filesList as fileB>
                                  <#if fileB['field'] == subTableField.field_name>
                                      <tr style="height:34px;">
                                          <td>${fileB['title']}</td>
                                          <td>
                                              <a href="commonController.do?viewFile&fileid=${fileB['fileKey']}&subclassname=com.qihang.winter.web.cgform.entity.upload.CgUploadEntity"
                                                 title="下载">下载</a></td>
                                          <td><a href="javascript:void(0);"
                                                 onclick="openwindow('预览','commonController.do?openViewFile&fileid=${fileB['fileKey']}&subclassname=org.jeecgframeworcom.qihang.winter.web.cgformUploadEntity','fList',700,500)">预览</a>
                                          </td>
                                          <td><a href="javascript:void(0)" class="jeecgDetail"
                                                 onclick="del('cgUploadController.do?delFile&id=${fileB['fileKey']}',this)">删除</a></td>
                                      </tr>
                                  </#if>
                              </#list>
                          </table>
                          <#if !(subTableField.operationCodesReadOnly ??)>
                              <div class="form jeecgDetail">
                                  <script type="text/javascript">
                                      var serverMsg = "";
                                      var m = new Map();
                                      $(function () {
                                          $('#${subTableField.field_name}').uploadify(
                                                  {
                                                      buttonText: '添加文件',
                                                      auto: false,
                                                      progressData: 'speed',
                                                      multi: true,
                                                      height: 25,
                                                      overrideEvents: ['onDialogClose'],
                                                      fileTypeDesc: '文件格式:',
                                                      queueID: 'filediv_${subTableField.field_name}',
                                                      fileTypeExts: '*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png',
                                                      fileSizeLimit: '15MB', swf: 'plug-in/uploadify/uploadify.swf',
                                                      uploader: 'cgUploadController.do?saveFiles&jsessionid=' + $("#sessionUID").val() + '',
                                                      onUploadStart: function (file) {
                                                          var cgFormId = $("input[name='id']").val();
                                                          $('#${subTableField.field_name}').uploadify("settings", "formData", {
                                                              'cgFormId': cgFormId,
                                                              'cgFormName': '${tableName?if_exists?html}',
                                                              'cgFormField': '${subTableField.field_name}'
                                                          });
                                                      },
                                                      onQueueComplete: function (queueData) {
                                                          var win = frameElement.api.opener;
                                                          win.reloadTable();
                                                          win.tip(serverMsg);
                                                          frameElement.api.close();
                                                      },
                                                      onUploadSuccess: function (file, data, response) {
                                                          var d = $.parseJSON(data);
                                                          if (d.success) {
                                                              var win = frameElement.api.opener;
                                                              serverMsg = d.msg;
                                                          }
                                                      }, onFallback: function () {
                                                      tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                                                  }, onSelectError: function (file, errorCode, errorMsg) {
                                                      switch (errorCode) {
                                                          case -100:
                                                              tip("上传的文件数量已经超出系统限制的" + $('#${subTableField.field_name}').uploadify('settings', 'queueSizeLimit') + "个文件！");
                                                              break;
                                                          case -110:
                                                              tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#${subTableField.field_name}').uploadify('settings', 'fileSizeLimit') + "大小！");
                                                              break;
                                                          case -120:
                                                              tip("文件 [" + file.name + "] 大小异常！");
                                                              break;
                                                          case -130:
                                                              tip("文件 [" + file.name + "] 类型不正确！");
                                                              break;
                                                      }
                                                  },
                                                      onUploadProgress: function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                                                      }
                                                  });
                                      });

                                  </script>
                                  <span id="file_uploadspan"><input type="file" name="${subTableField.field_name}" id="${subTableField.field_name}"/></span>
                              </div>
                              <div class="form" id="filediv_${subTableField.field_name}"></div>
                          </#if>

                      <#else>
                        <input id="${sub}[${subTableData_index}].${subTableField.field_name}"
                               name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
                               style="width: 150px" class="inputxt"
                               value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                               nullmsg="请输入${subTableField.content}！"

                            <#if subTableField.field_valid_type?if_exists?html != ''>
                               datatype="${subTableField.field_valid_type?if_exists?html}"
                            <#else>
                                <#if subTableField.type == 'int'>
                               datatype="n"
                                <#elseif subTableField.type=='double'>
                               datatype="/^(-?\d+)(\.\d+)?$/"
                                <#else>
                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                                </#if></#if>>
                      </#if>
                    <label class="Validform_label"
                           style="display: none;">${subTableField.content?if_exists?html}</label>
                  </td>
                    <#if (subTableField_index%2==0)&&(!subTableField_has_next)>
                      <td align="right">
                        <label class="Validform_label">
                        </label>
                      </td>
                      <td class="value">
                      </td>
                    </#if>
                    <#if (subTableField_index%2!=0)||(!subTableField_has_next)>
                    </tr>
                    </#if>
                </#list>
                <#list field['${sub}'].fieldAreaList as subTableField>
                  <tr>
                    <td align="right">
                      <label class="Validform_label">
                      ${subTableField.content?if_exists?html}
                      </label>
                    </td>
                    <td class="value" colspan="3">
													<textarea
                                                            id="${sub}[${subTableData_index}].${subTableField.field_name}"
                                                            name="${sub}[${subTableData_index}].${subTableField.field_name}"
                                                            style="width: 600px" class="inputxt" rows="6"
                                                        <#if subTableField.field_valid_type?if_exists?html != ''>
                                                            datatype="${subTableField.field_valid_type?if_exists?html}"
                                                        <#else>
                                                            <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                                                        </#if>>${subTableData['${subTableField.field_name}']?if_exists?html}</textarea>
													<span class="Validform_checktip">
                                                        <#if subTableField.field_valid_type?if_exists?starts_with('*') || (subTableField.field_valid_type?if_exists?html == '' && subTableField.is_null != 'Y')>
                                                          *
                                                        </#if>
													</span>
                      <label class="Validform_label"
                             style="display: none;">${subTableData['${subTableField.content}']?if_exists?html}</label>
                    </td>
                  </tr>
                </#list>
            </#list>
        </#if>
    <#else>
      <input type="hidden" name="${sub}[0].id" id="${sub}[0].id"/>
        <#list field['${sub}'].hiddenFieldList as subTableField >
          <input type="hidden" name="${sub}[0].${subTableField.field_name}" id="${sub}[0].${subTableField.field_name}"/>
        </#list>
        <#list field['${sub}'].fieldNoAreaList as subTableField >
            <#if subTableField_index%2==0>
            <tr>
            </#if>
          <td align="right">
            <label class="Validform_label">
            ${subTableField.content?if_exists?html}
            </label>
          </td>
          <td class="value">
              <#if subTableField.show_type=='text'>
                <input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                       type="text"
                       style="width: 150px" class="inputxt"
                       nullmsg="请输入${subTableField.content}！"

                    <#if subTableField.field_valid_type?if_exists?html != ''>
                       datatype="${subTableField.field_valid_type?if_exists?html}"
                    <#else>
                        <#if subTableField.type == 'int'>
                       datatype="n"
                        <#elseif subTableField.type=='double'>
                       datatype="/^(-?\d+)(\.\d+)?$/"
                        <#else>
                       <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                        </#if></#if>>

              <#elseif subTableField.show_type=='password'>
                <input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                       type="password"
                       style="width: 150px" class="inputxt"
                       nullmsg="请输入${subTableField.content}！"

                    <#if subTableField.field_valid_type?if_exists?html != ''>
                       datatype="${subTableField.field_valid_type?if_exists?html}"
                    <#else>
                       <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                    </#if>>

              <#elseif subTableField.show_type=='radio'>
                  <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
                      <#list dataList as dictdata>
                        <input value="${dictdata.typecode?if_exists?html}" name="${sub}[0].${subTableField.field_name}"
                               type="radio" <#if subTableField.is_null != 'Y'>datatype="*"</#if>>
                      ${dictdata.typename?if_exists?html}
                      </#list>
                  </@DictData>

              <#elseif subTableField.show_type=='checkbox'>
                  <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
                      <#list dataList as dictdata>
                        <input value="${dictdata.typecode?if_exists?html}" name="${sub}[0].${subTableField.field_name}"
                               type="checkbox" <#if subTableField.is_null != 'Y'>datatype="*"</#if>>
                      ${dictdata.typename?if_exists?html}
                      </#list>
                  </@DictData>

              <#elseif subTableField.show_type=='list'>
                  <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
                    <select id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                            <#if subTableField.is_null != 'Y'>datatype="*"</#if>>
                        <#list dataList as dictdata>
                          <option value="${dictdata.typecode?if_exists?html}">
                          ${dictdata.typename?if_exists?html}
                          </option>
                        </#list>
                    </select>
                  </@DictData>

              <#elseif subTableField.show_type=='date'>
                <input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                       type="text"
                       style="width: 150px"
                       class="Wdate" onClick="WdatePicker()"
                       nullmsg="请输入${subTableField.content}！"

                    <#if subTableField.field_valid_type?if_exists?html != ''>
                       datatype="${subTableField.field_valid_type?if_exists?html}"
                    <#else>
                       <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                    </#if>>

              <#elseif subTableField.show_type=='datetime'>
                <input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                       type="text"
                       style="width: 150px"
                       class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       nullmsg="请输入${subTableField.content}！"

                    <#if subTableField.field_valid_type?if_exists?html != ''>
                       datatype="${subTableField.field_valid_type?if_exists?html}"
                    <#else>
                       <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                    </#if>>

              <#elseif subTableField.show_type=='popup'>
                <input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                       type="text"
                       style="width: 150px" class="searchbox-inputtext15"
                       onClick="inputClick(this,'${subTableField.dict_text?if_exists?html}','${subTableField.dict_table?if_exists?html}');"
                       nullmsg="请输入${subTableField.content}！"

                    <#if subTableField.field_valid_type?if_exists?html != ''>
                       datatype="${subTableField.field_valid_type?if_exists?html}"
                    <#else>
                       <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                    </#if>>

              <#elseif subTableField.show_type=='file'>
                <#--<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"-->
                       <#--type="text"-->
                       <#--style="width: 150px" class="inputxt"-->
                       <#--nullmsg="请输入${subTableField.content}！"-->

                    <#--<#if subTableField.field_valid_type?if_exists?html != ''>-->
                       <#--datatype="${subTableField.field_valid_type?if_exists?html}"-->
                    <#--<#else>-->
                       <#--<#if subTableField.is_null != 'Y'>datatype="*"</#if>-->
                    <#--</#if>>-->
                  <table>
                      <#list filesList as fileB>
                          <#if fileB['field'] == subTableField.field_name>
                              <tr style="height:34px;">
                                  <td>${fileB['title']}</td>
                                  <td>
                                      <a href="commonController.do?viewFile&fileid=${fileB['fileKey']}&subclassname=com.qihang.winter.web.cgform.entity.upload.CgUploadEntity"
                                         title="下载">下载</a></td>
                                  <td><a href="javascript:void(0);"
                                         onclick="openwindow('预览','commonController.do?openViewFile&fileid=${fileB['fileKey']}&subclassname=org.jeecgframeworcom.qihang.winter.web.cgformUploadEntity','fList',700,500)">预览</a>
                                  </td>
                                  <td><a href="javascript:void(0)" class="jeecgDetail"
                                         onclick="del('cgUploadController.do?delFile&id=${fileB['fileKey']}',this)">删除</a></td>
                              </tr>
                          </#if>
                      </#list>
                  </table>
                  <#if !(subTableField.operationCodesReadOnly ??)>
                      <div class="form jeecgDetail">
                          <script type="text/javascript">
                              var serverMsg = "";
                              var m = new Map();
                              $(function () {
                                  $('#${subTableField.field_name}').uploadify(
                                          {
                                              buttonText: '添加文件',
                                              auto: false,
                                              progressData: 'speed',
                                              multi: true,
                                              height: 25,
                                              overrideEvents: ['onDialogClose'],
                                              fileTypeDesc: '文件格式:',
                                              queueID: 'filediv_${subTableField.field_name}',
                                              fileTypeExts: '*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png',
                                              fileSizeLimit: '15MB', swf: 'plug-in/uploadify/uploadify.swf',
                                              uploader: 'cgUploadController.do?saveFiles&jsessionid=' + $("#sessionUID").val() + '',
                                              onUploadStart: function (file) {
                                                  var cgFormId = $("input[name='id']").val();
                                                  $('#${subTableField.field_name}').uploadify("settings", "formData", {
                                                      'cgFormId': cgFormId,
                                                      'cgFormName': '${tableName?if_exists?html}',
                                                      'cgFormField': '${subTableField.field_name}'
                                                  });
                                              },
                                              onQueueComplete: function (queueData) {
                                                  var win = frameElement.api.opener;
                                                  win.reloadTable();
                                                  win.tip(serverMsg);
                                                  frameElement.api.close();
                                              },
                                              onUploadSuccess: function (file, data, response) {
                                                  var d = $.parseJSON(data);
                                                  if (d.success) {
                                                      var win = frameElement.api.opener;
                                                      serverMsg = d.msg;
                                                  }
                                              }, onFallback: function () {
                                              tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                                          }, onSelectError: function (file, errorCode, errorMsg) {
                                              switch (errorCode) {
                                                  case -100:
                                                      tip("上传的文件数量已经超出系统限制的" + $('#${subTableField.field_name}').uploadify('settings', 'queueSizeLimit') + "个文件！");
                                                      break;
                                                  case -110:
                                                      tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#${subTableField.field_name}').uploadify('settings', 'fileSizeLimit') + "大小！");
                                                      break;
                                                  case -120:
                                                      tip("文件 [" + file.name + "] 大小异常！");
                                                      break;
                                                  case -130:
                                                      tip("文件 [" + file.name + "] 类型不正确！");
                                                      break;
                                              }
                                          },
                                              onUploadProgress: function (file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                                              }
                                          });
                              });

                          </script>
                          <span id="file_uploadspan"><input type="file" name="${subTableField.field_name}" id="${subTableField.field_name}"/></span>
                      </div>
                      <div class="form" id="filediv_${subTableField.field_name}"></div>
                  </#if>
              <#else>
                <input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"
                       type="text"
                       style="width: 150px" class="inputxt"
                       nullmsg="请输入${subTableField.content}！"

                    <#if subTableField.field_valid_type?if_exists?html != ''>
                       datatype="${subTableField.field_valid_type?if_exists?html}"
                    <#else>
                        <#if subTableField.type == 'int'>
                       datatype="n"
                        <#elseif subTableField.type=='double'>
                       datatype="/^(-?\d+)(\.\d+)?$/"
                        <#else>
                       <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                        </#if></#if>>
              </#if>
            <label class="Validform_label" style="display: none;">${subTableField.content?if_exists?html}</label>
          </td>
            <#if (subTableField_index%2==0)&&(!subTableField_has_next)>
              <td align="right">
                <label class="Validform_label">
                </label>
              </td>
              <td class="value">
              </td>
            </#if>
            <#if (subTableField_index%2!=0)||(!subTableField_has_next)>
            </tr>
            </#if>
        </#list>
        <#list field['${sub}'].fieldAreaList as subTableField>
          <tr>
            <td align="right">
              <label class="Validform_label">
              ${subTableField.content?if_exists?html}
              </label>
            </td>
            <td class="value" colspan="3">
              <textarea id="${sub}[0].${subTableField.field_name}"
                        name="${sub}[0].${subTableField.field_name}"
                        style="width: 600px" class="inputxt" rows="6"
                  <#if subTableField.field_valid_type?if_exists?html != ''>
                        datatype="${subTableField.field_valid_type?if_exists?html}"
                  <#else>
                        <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                  </#if>></textarea>
              <span class="Validform_checktip">
                  <#if subTableField.fieldValidType?if_exists?starts_with('*') || (subTableField.fieldValidType?if_exists?html == '' && subTableField.isNull != 'Y')>
                    *
                  </#if>
              </span>
              <label class="Validform_label" style="display: none;">${sub}
                [0].${subTableField.content?if_exists?html}</label>
            </td>
          </tr>
        </#list>
    </#if>
    </table>
  </div>
</div>
					