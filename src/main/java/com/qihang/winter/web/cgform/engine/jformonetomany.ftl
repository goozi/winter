
					    <div title="${field['${sub}'].head.content?if_exists?html}" style="margin:0px;padding:0px;overflow:hidden;">
							<script type="text/javascript">

								$('#addBtn_${sub}').linkbutton({   
								    iconCls: 'icon-add'  
								});  
								$('#delBtn_${sub}').linkbutton({   
								    iconCls: 'icon-remove'  
								}); 
								$('#addBtn_${sub}').bind('click', function(){
							 		 var tr =  $("#add_${sub}_table tr").clone();
                                     tr = tr[0];
								 	 $("#add_${sub}_table").append(tr);
                                    resetTrNum2('add_${sub}_table');
								 	 return false;
							    });  
								$('#delBtn_${sub}').bind('click', function(){   
							       $("#add_${sub}_table").find("input:checked").parent().parent().remove();
                                    resetTrNum2('add_${sub}_table');
							        return false;
							    });
							    $(document).ready(function(){
									/**20160628 判断页脚存在缩小子表大小 **/
									if($("#footdiv").length > 0 ){
                                            $(".subTable").height(300);
									} else {
                                        $(".subTable").height(350);
									}
							    	$(".datagrid-toolbar").parent().css("width","auto");
							    	if(location.href.indexOf("load=detail")!=-1){
							    		$(".datagrid-toolbar").hide();
										$(":input").each(function(){
											var $thisThing = $(this);
											$thisThing.attr("title",$thisThing.val());
										});
							    	}
							    	resetTrNum('add_${sub}_table');
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
                                //通用弹出式文件上传
                                function commonUpload(callback,inputId){
                                    $.dialog({
                                        content: "url:systemController.do?commonUpload",
                                        lock : true,
                                        title:"文件上传",
                                        zIndex:2100,
                                        width:700,
                                        height: 200,
                                        parent:windowapi,
                                        cache:false,
                                        ok: function(){
                                            var iframe = this.iframe.contentWindow;
                                            iframe.uploadCallback(callback,inputId);
                                            return true;
                                        },
                                        cancelVal: '关闭',
                                        cancel: function(){
                                        }
                                    });
                                }
                                //通用弹出式文件上传-回调
                                function commonUploadDefaultCallBack(url,name,inputId){

                                    $(document.getElementById(inputId+"_href")).attr('href',url).html('下载');
                                    $(document.getElementById(inputId)).val(url);
                                }
                                //重置下标
                                function resetTrNum2(tableId) {
                                    $tbody = $("#"+tableId+"");
                                    $tbody.find('>tr').each(function(i){
                                        $(':input,select,a', this).each(function(){
                                            var $this = $(this), name = $this.attr('name'), val = $this.val();
                                            if(name!=null){
                                                if (name.indexOf("#index#") >= 0){
                                                    $this.attr("name",name.replace('#index#',i));
                                                }else{
                                                    var s = name.indexOf("[");
                                                    var e = name.indexOf("]");
                                                    var new_name = name.substring(s+1,e);
                                                    $this.attr("name",name.replace(new_name,i));
                                                }
                                            }
                                            var id = $this.attr('id');
                                            if(id){
                                                console.log(i);
                                                var s1 = id.indexOf("[");
                                                var e1 = id.indexOf("]");
                                                var new_id = id.substring(s1+1,e1);
                                                $this.attr("id",id.replace(new_id,i));
                                                if(id.indexOf("_btn") > -1){
                                                   id = $this.attr("id");
                                                   $this.attr("onclick","commonUpload(commonUploadDefaultCallBack,'"+id.replace('_btn','')+"')");
                                                }
                                            }
                                        });
                                        $(this).find('div[name=\'xh\']').html(i+1);
                                    });
                                }
							</script>
							<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
								<a id="addBtn_${sub}" href="#">添加</a> <a id="delBtn_${sub}" href="#">删除</a> 
							</div>
							<div style="width: auto;height: 350px;overflow-y:auto;overflow-x:scroll;" class="subTable">
							<table border="0" cellpadding="2" cellspacing="0" id="${sub}_table">
							<tr bgcolor="#E6E6E6" ">
								<td align="center" bgcolor="#EEEEEE">序号</td>
								<td align="center" bgcolor="#EEEEEE">操作</td>
							<#list field['${sub}'].fieldList as subTableField >
								<td align="left" bgcolor="#EEEEEE">${subTableField.content?if_exists?html}</td>
							</#list>
							</tr>
							<tbody id="add_${sub}_table">
								<#if data['${sub}']?exists&&(data['${sub}']?size>0) >
								<#list data['${sub}'] as subTableData >
									<tr>
									<td align="center"><div style="width: 25px;" name="xh"></div></td>
									<td align="center">
										<input style="width:20px;" type="checkbox" name="ck"/>
										<input type="hidden" name="${sub}[${subTableData_index}].id" id="${sub}[${subTableData_index}].id" value="${subTableData['id']?if_exists?html}"/>
										<#list field['${sub}'].hiddenFieldList as subTableField >
										<input type="hidden" name="${sub}[${subTableData_index}].${subTableField.field_name}" id="${sub}[${subTableData_index}].${subTableField.field_name}" value="${subTableData['${subTableField.field_name}']?if_exists?html}"/>
										</#list> 
									</td>
									<#list field['${sub}'].fieldList as subTableField >
									<td align="left">
									<#if subTableField.show_type=='text'>
										<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt" value="${subTableData['${subTableField.field_name}']?if_exists?html}"
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
										<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}"  type="password"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt" value="${subTableData['${subTableField.field_name}']?if_exists?html}"
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
				               					<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif subTableField.show_type=='radio'>
								        <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
											<#list dataList as dictdata> 
											<input value="${dictdata.typecode?if_exists?html}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="radio"
											<#if dictdata_index==0&&subTableField.is_null != 'Y'>datatype="*"</#if>
											<#if dictdata.typecode?if_exists?html=="${subTableData['${subTableField.field_name}']?if_exists?html}"> checked="true" </#if>>
												${dictdata.typename?if_exists?html}
											</#list> 
										</@DictData>
								               
									<#elseif subTableField.show_type=='checkbox'>
										<#assign checkboxstr>${subTableData['${subTableField.field_name}']?if_exists?html}</#assign>
										<#assign checkboxlist=checkboxstr?split(",")>
										<@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
											<#list dataList as dictdata> 
											<input value="${dictdata.typecode?if_exists?html}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="checkbox"
											<#if dictdata_index==0&&subTableField.is_null != 'Y'>datatype="*"</#if>
											<#list checkboxlist as x >
											<#if dictdata.typecode?if_exists?html=="${x?if_exists?html}"> checked="true" </#if></#list>>
												${dictdata.typename?if_exists?html}
											</#list> 
										</@DictData>
								               
									<#elseif subTableField.show_type=='list'>
										<@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
											<select id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" <#if subTableField.is_null != 'Y'>datatype="*"</#if>>
												<#list dataList as dictdata> 
												<option value="${dictdata.typecode?if_exists?html}" 
												<#if dictdata.typecode?if_exists?html=="${subTableData['${subTableField.field_name}']?if_exists?html}"> selected="selected" </#if>>
													${dictdata.typename?if_exists?html}
												</option> 
												</#list> 
											</select>
										</@DictData>
										
									<#elseif subTableField.show_type=='date'>
										<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px"  value="${subTableData['${subTableField.field_name}']?if_exists?html}"
										       class="Wdate" onClick="WdatePicker()" 
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
				               					<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif subTableField.show_type=='datetime'>
										<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px"  value="${subTableData['${subTableField.field_name}']?if_exists?html}"
										       class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
				               					<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
								               
									<#elseif subTableField.show_type=='popup'>
										<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}"  type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="searchbox-inputtext15" 
										       onClick="inputClick(this,'${subTableField.dict_text?if_exists?html}','${subTableField.dict_table?if_exists?html}');" 
										       value="${subTableData['${subTableField.field_name}']?if_exists?html}"
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
				               					<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif subTableField.show_type=='file'>
                                        <input type="hidden" id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" />
                                        <a  target="_blank" id="${sub}[0].${subTableField.field_name}_href">未上传</a>
                                        <br>
                                        <input class="ui-button" type="button" value="上传附件" id="${sub}[0].${subTableField.field_name}_btn"
                                               onclick="commonUpload(commonUploadDefaultCallBack,'${sub}[0].${subTableField.field_name}')"/>
                                    <#--<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
                                           style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt" value="${subTableData['${subTableField.field_name}']?if_exists?html}"
                                           nullmsg="请输入${subTableField.content}！"

                                           <#if subTableField.field_valid_type?if_exists?html != ''>
                                           datatype="${subTableField.field_valid_type?if_exists?html}"
                                           <#else>
                                              <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                                           </#if>>
                                           -->
                                        <#--<table>-->
											<#--<#list filesList as fileB>-->
												<#--<#if fileB['field'] == subTableField.field_name>-->
                                                    <#--<tr style="height:34px;">-->
                                                        <#--<td>${fileB['title']}</td>-->
                                                        <#--<td><a href="commonController.do?viewFile&fileid=${fileB['fileKey']}&subclassname=com.qihang.winter.web.cgform.entity.upload.CgUploadEntity" title="下载">下载</a></td>-->
                                                        <#--<td><a href="javascript:void(0);" onclick="openwindow('预览','commonController.do?openViewFile&fileid=${fileB['fileKey']}&subclassname=org.jeecgframeworcom.qihang.winter.web.cgformUploadEntity','fList',700,500)">预览</a></td>-->
                                                        <#--<td><a href="javascript:void(0)" class="jeecgDetail" onclick="del('cgUploadController.do?delFile&id=${fileB['fileKey']}',this)">删除</a></td>-->
                                                    <#--</tr>-->
												<#--</#if>-->
											<#--</#list>-->
                                        <#--</table>-->
										<#--<#if !(subTableField.operationCodesReadOnly ??)>-->
                                            <#--<div class="form jeecgDetail">-->
                                                <#--<script type="text/javascript">-->
                                                    <#--var serverMsg="";-->
                                                    <#--var m = new Map();-->
                                                    <#--$(function(){$('#${subTableField.field_name}').uploadify(-->
                                                            <#--{buttonText:'添加文件',-->
                                                                <#--auto:false,-->
                                                                <#--progressData:'speed',-->
                                                                <#--multi:true,-->
                                                                <#--height:25,-->
                                                                <#--overrideEvents:['onDialogClose'],-->
                                                                <#--fileTypeDesc:'文件格式:',-->
                                                                <#--queueID:'filediv_${subTableField.field_name}',-->
                                                                <#--fileTypeExts:'*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png',-->
                                                                <#--fileSizeLimit:'15MB',swf:'plug-in/uploadify/uploadify.swf',-->
                                                                <#--uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',-->
                                                                <#--onUploadStart : function(file) {-->
                                                                    <#--var cgFormId=$("input[name='id']").val();-->
                                                                    <#--$('#${subTableField.field_name}').uploadify("settings", "formData", {'cgFormId':cgFormId,'cgFormName':'${tableName?if_exists?html}','cgFormField':'${subTableField.field_name}'});} ,-->
                                                                <#--onQueueComplete : function(queueData) {-->
                                                                    <#--var win = frameElement.api.opener;-->
                                                                    <#--win.reloadTable();-->
                                                                    <#--win.tip(serverMsg);-->
                                                                    <#--frameElement.api.close();},-->
                                                                <#--onUploadSuccess : function(file, data, response) {var d=$.parseJSON(data);if(d.success){var win = frameElement.api.opener;serverMsg = d.msg;}},onFallback : function(){tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")},onSelectError : function(file, errorCode, errorMsg){switch(errorCode) {case -100:tip("上传的文件数量已经超出系统限制的"+$('#${subTableField.field_name}').uploadify('settings','queueSizeLimit')+"个文件！");break;case -110:tip("文件 ["+file.name+"] 大小超出系统限制的"+$('#${subTableField.field_name}').uploadify('settings','fileSizeLimit')+"大小！");break;case -120:tip("文件 ["+file.name+"] 大小异常！");break;case -130:tip("文件 ["+file.name+"] 类型不正确！");break;}},-->
                                                                <#--onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { }});});-->

                                                <#--</script><span id="file_uploadspan"><input type="file" name="${subTableField.field_name}" id="${subTableField.field_name}" /></span>-->
                                            <#--</div>-->
                                            <#--<div class="form" id="filediv_${subTableField.field_name}"> </div>-->
										<#--</#if>-->
									<#else>
										<input id="${sub}[${subTableData_index}].${subTableField.field_name}" name="${sub}[${subTableData_index}].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt" value="${subTableData['${subTableField.field_name}']?if_exists?html}"
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
									</#list>
									</tr>
								</#list>
								<#else>
									<tr>
									<td align="center"><div style="width: 25px;" name="xh"></div></td>
									<td align="center">
									<input style="width:20px;" type="checkbox" name="ck"/>
									<input type="hidden" name="${sub}[0].id" id="${sub}[0].id" />
									<#list field['${sub}'].hiddenFieldList as subTableField >
									<input type="hidden" name="${sub}[0].${subTableField.field_name}" id="${sub}[0].${subTableField.field_name}"/>
									</#list> 
									</td>
									<#list field['${sub}'].fieldList as subTableField >
									<td align="left">
									<#if subTableField.show_type=='text'>
										<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt"
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
										<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"  type="password"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt" 
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif subTableField.show_type=='radio'>
								        <@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
											<#list dataList as dictdata> 
											<input value="${dictdata.typecode?if_exists?html}" name="${sub}[0].${subTableField.field_name}" type="radio" <#if subTableField.is_null != 'Y'>datatype="*"</#if> >
												${dictdata.typename?if_exists?html}
											</#list> 
										</@DictData>
								               
									<#elseif subTableField.show_type=='checkbox'>
										<@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
											<#list dataList as dictdata> 
											<input value="${dictdata.typecode?if_exists?html}" name="${sub}[0].${subTableField.field_name}" type="checkbox" <#if subTableField.is_null != 'Y'>datatype="*"</#if>>
												${dictdata.typename?if_exists?html}
											</#list> 
										</@DictData>
								               
									<#elseif subTableField.show_type=='list'>
										<@DictData name="${subTableField.dict_field?if_exists?html}" text="${subTableField.dict_text?if_exists?html}" tablename="${subTableField.dict_table?if_exists?html}" var="dataList">
											<select id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" <#if subTableField.is_null != 'Y'>datatype="*"</#if> >
												<#list dataList as dictdata> 
												<option value="${dictdata.typecode?if_exists?html}" >
													${dictdata.typename?if_exists?html}
												</option> 
												</#list> 
											</select>
										</@DictData>
										
									<#elseif subTableField.show_type=='date'>
										<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px"  
										       class="Wdate" onClick="WdatePicker()" 
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif subTableField.show_type=='datetime'>
										<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px"  
										       class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
								               
									<#elseif subTableField.show_type=='popup'>
										<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}"  type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="searchbox-inputtext15" 
										       onClick="inputClick(this,'${subTableField.dict_text?if_exists?html}','${subTableField.dict_table?if_exists?html}');" 
								               nullmsg="请输入${subTableField.content}！"
								               
								               <#if subTableField.field_valid_type?if_exists?html != ''>
								               datatype="${subTableField.field_valid_type?if_exists?html}"
								               <#else>
					               				<#if subTableField.is_null != 'Y'>datatype="*"</#if>
								               </#if>>
									
									<#elseif subTableField.show_type=='file'>
                                        <input type="hidden" id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" />
                                        <a  target="_blank" id="${sub}[0].${subTableField.field_name}_href">未上传</a>
                                        <br>
                                        <input class="ui-button" type="button" value="上传附件" id="${sub}[0].${subTableField.field_name}_btn"
                                               onclick="commonUpload(commonUploadDefaultCallBack,'${sub}[0].${subTableField.field_name}')"/>
                                    <#--<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" type="text"
                                           style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt"
                                           nullmsg="请输入${subTableField.content}！"

                                           <#if subTableField.field_valid_type?if_exists?html != ''>
                                           datatype="${subTableField.field_valid_type?if_exists?html}"
                                           <#else>
                                               <#if subTableField.is_null != 'Y'>datatype="*"</#if>
                                           </#if>>-->
                                        <#--<table>-->
                                            <#--<#list filesList as fileB>-->
                                                <#--<#if fileB['field'] == subTableField.field_name>-->
                                                    <#--<tr style="height:34px;">-->
                                                        <#--<td>${fileB['title']}</td>-->
                                                        <#--<td><a href="commonController.do?viewFile&fileid=${fileB['fileKey']}&subclassname=com.qihang.winter.web.cgform.entity.upload.CgUploadEntity" title="下载">下载</a></td>-->
                                                        <#--<td><a href="javascript:void(0);" onclick="openwindow('预览','commonController.do?openViewFile&fileid=${fileB['fileKey']}&subclassname=org.jeecgframeworcom.qihang.winter.web.cgformUploadEntity','fList',700,500)">预览</a></td>-->
                                                        <#--<td><a href="javascript:void(0)" class="jeecgDetail" onclick="del('cgUploadController.do?delFile&id=${fileB['fileKey']}',this)">删除</a></td>-->
                                                    <#--</tr>-->
                                                <#--</#if>-->
                                            <#--</#list>-->
                                        <#--</table>-->
                                        <#--<#if !(subTableField.operationCodesReadOnly ??)>-->
                                            <#--<div class="form jeecgDetail">-->
                                                <#--<script type="text/javascript">-->
                                                    <#--var serverMsg="";-->
                                                    <#--var m = new Map();-->
                                                    <#--$(function(){$('#${subTableField.field_name}').uploadify(-->
                                                            <#--{buttonText:'添加文件',-->
                                                                <#--auto:false,-->
                                                                <#--progressData:'speed',-->
                                                                <#--multi:true,-->
                                                                <#--height:25,-->
                                                                <#--overrideEvents:['onDialogClose'],-->
                                                                <#--fileTypeDesc:'文件格式:',-->
                                                                <#--queueID:'filediv_${subTableField.field_name}',-->
                                                                <#--fileTypeExts:'*.rar;*.zip;*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.gif;*.png',-->
                                                                <#--fileSizeLimit:'15MB',swf:'plug-in/uploadify/uploadify.swf',-->
                                                                <#--uploader:'cgUploadController.do?saveFiles&jsessionid='+$("#sessionUID").val()+'',-->
                                                                <#--onUploadStart : function(file) {-->
                                                                    <#--var cgFormId=$("input[name='id']").val();-->
                                                                    <#--$('#${subTableField.field_name}').uploadify("settings", "formData", {'cgFormId':cgFormId,'cgFormName':'${tableName?if_exists?html}','cgFormField':'${subTableField.field_name}'});} ,-->
                                                                <#--onQueueComplete : function(queueData) {-->
                                                                    <#--var win = frameElement.api.opener;-->
                                                                    <#--win.reloadTable();-->
                                                                    <#--win.tip(serverMsg);-->
                                                                    <#--frameElement.api.close();},-->
                                                                <#--onUploadSuccess : function(file, data, response) {var d=$.parseJSON(data);if(d.success){var win = frameElement.api.opener;serverMsg = d.msg;}},onFallback : function(){tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")},onSelectError : function(file, errorCode, errorMsg){switch(errorCode) {case -100:tip("上传的文件数量已经超出系统限制的"+$('#${subTableField.field_name}').uploadify('settings','queueSizeLimit')+"个文件！");break;case -110:tip("文件 ["+file.name+"] 大小超出系统限制的"+$('#${subTableField.field_name}').uploadify('settings','fileSizeLimit')+"大小！");break;case -120:tip("文件 ["+file.name+"] 大小异常！");break;case -130:tip("文件 ["+file.name+"] 类型不正确！");break;}},-->
                                                                <#--onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { }});});-->

                                                <#--</script><span id="file_uploadspan"><input type="file" name="${subTableField.field_name}" id="${subTableField.field_name}" /></span>-->
                                            <#--</div>-->
                                            <#--<div class="form" id="filediv_${subTableField.field_name}"> </div>-->
                                        <#--</#if>-->
								               
									<#else>
										<input id="${sub}[0].${subTableField.field_name}" name="${sub}[0].${subTableField.field_name}" type="text"
										       style="width: ${(subTableField.field_length==0)?string(150, subTableField.field_length)}px" class="inputxt"
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
									</#list>
									</tr>
								</#if>
							</tbody>
							</table>
							</div>
						</div>
					