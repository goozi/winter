<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="add${entityName}Btn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="del${entityName}Btn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAdd${entityName}Btn(rowIndex) {
    var tr = $("#add_${entityName?uncap_first}_table_template tr").clone();
    $("#add_${entityName?uncap_first}_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_${entityName?uncap_first}_table');
  }

  function clickDel${entityName}Btn(rowIndex) {
    var len = $("#add_${entityName?uncap_first}_table").find(">tr").length;
    $("#add_${entityName?uncap_first}_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_${entityName?uncap_first}_table_template tr").clone();
      $("#add_${entityName?uncap_first}_table").append(tr);
    }
    resetTrNum('add_${entityName?uncap_first}_table');
  }

	<#--$('#add${entityName}Btn').bind('click', function(){   -->
 		 <#--var tr =  $("#add_${entityName?uncap_first}_table_template tr").clone();-->
	 	 <#--$("#add_${entityName?uncap_first}_table").append(tr);-->
	 	 <#--resetTrNum('add_${entityName?uncap_first}_table');-->
	 	 <#--return false;-->
    <#--});  -->
	<#--$('#del${entityName}Btn').bind('click', function(){   -->
      	<#--$("#add_${entityName?uncap_first}_table").find("input:checked").parent().parent().remove();   -->
        <#--resetTrNum('add_${entityName?uncap_first}_table'); -->
        <#--return false;-->
    <#--}); -->
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#${entityName?uncap_first}_table").createhftable({
	    	height: (h-50)+'px',
			  width:'auto',
			  fixFooter:false
			});
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
<#--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">-->
	<#--<a id="add${entityName}Btn" href="#">添加</a> <a id="del${entityName}Btn" href="#">删除</a> -->
<#--</div>-->
<table border="0" cellpadding="2" cellspacing="1" id="${entityName?uncap_first}_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
		 <#list pageColumns as po>
				 <#assign check = 0 >
				  <#list foreignKeys as key>
				  <#if fieldMeta[po.fieldName]==key?uncap_first>
				  <#assign check = 1 >
				  <#break>
				  </#if>
				  </#list>
				  <#if check==0>
				  <td align="center" bgcolor="#476f9a">
						${po.content}<#if po.isNull != 'Y'><span style="color: red;">*</span></#if>
				  </td>
				  </#if>
	      </#list>
	</tr>
	<tbody id="add_${entityName?uncap_first}_table">	
	<c:if test="${"$"}{fn:length(${entityName?uncap_first}List)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="add${entityName}Btn[0]" id="add${entityName}Btn[0]" href="#"
               onclick="clickAdd${entityName}Btn(0);"></a>
          </div>
				</td>
				<#list columns as po>
				<#if po.isShow=="N">
					<input name="${entityName?uncap_first}List[0].${po.fieldName}" type="hidden"/>
				</#if>
				</#list>
				<#list pageColumns as po>
				  <#assign check = 0 >
				  <#list foreignKeys as key>
				  <#if fieldMeta[po.fieldName]==key?uncap_first>
				  <#assign check = 1 >
				  <#break>
				  </#if>
				  </#list>
				  <#if check==0>
				  <td align="left" bgcolor="white">
					<#if po.showType == "text">
					  	<input name="${entityName?uncap_first}List[0].${po.fieldName}" maxlength="${po.length?c}" 
					  		type="text" class="inputxt"  style="width:120px;" <#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}"<#else><#if po.type == 'int'> datatype="n"<#elseif po.type=='double'> datatype="/^(-?\d+)(\.\d+)?$/" <#else><#if po.isNull != 'Y'>datatype="*"</#if></#if></#if>>
						<#elseif po.showType=='password'>
							<input name="${entityName?uncap_first}List[0].${po.fieldName}" maxlength="${po.length?c}" 
					  		type="password" class="inputxt"  style="width:120px;"
					  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}"<#else><#if po.type == 'int'> datatype="n"<#elseif po.type=='double'> datatype="/^(-?\d+)(\.\d+)?$/"<#else><#if po.isNull != 'Y'>datatype="*"</#if></#if></#if>>
						<#elseif po.showType=='radio' || po.showType=='select' || po.showType=='checkbox' || po.showType=='list'>
							<t:dictSelect field="${po.fieldName}" type="${po.showType?if_exists?html}"
										<#if po.dictTable?if_exists?html != ''>dictTable="po.dictTable" dictField="${po.dictField?if_exists?html}" dictText="${po.dictText?if_exists?html}"<#else>typeGroupCode="${po.dictField}"</#if> defaultVal="${'$'}{${entityName?uncap_first}Page.${po.fieldName}}" hasLabel="false"  title="${po.content}"></t:dictSelect>     
						<#elseif po.showType=='date'>
							<input name="${entityName?uncap_first}List[0].${po.fieldName}" maxlength="${po.length?c}" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}" <#else><#if po.isNull != 'Y'>datatype="*"</#if></#if>>
					      <#elseif po.showType=='datetime'>
					      	<input name="${entityName?uncap_first}List[0].${po.fieldName}" maxlength="${po.length?c}" 
						  		type="text"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:120px;"
						  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}" <#else><#if po.isNull != 'Y'>datatype="*"</#if></#if>>
					       <#elseif po.showType=='file'>
							<input type="hidden" id="${entityName?uncap_first}List[0].${po.fieldName}" name="${entityName?uncap_first}List[0].${po.fieldName}" />
										<a  target="_blank" id="${entityName?uncap_first}List[0].${po.fieldName}_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'${entityName?uncap_first}List\\[0\\]\\.${po.fieldName}')"/> 
					       <#else>
					       	<input name="${entityName?uncap_first}List[0].${po.fieldName}" maxlength="${po.length?c}" 
						  		type="text" class="inputxt"  style="width:120px;"
						  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}"<#else><#if po.type == 'int'> datatype="n" <#elseif po.type=='double'> datatype="/^(-?\d+)(\.\d+)?$/"<#else><#if po.isNull != 'Y'>datatype="*"</#if></#if></#if> >
					  </#if>
					  <label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
					</td>
				  </#if>
	            </#list>
   			</tr>
	</c:if>
	<c:if test="${"$"}{fn:length(${entityName?uncap_first}List)  > 0 }">
		<c:forEach items="${"$"}{${entityName?uncap_first}List}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${'$'}{stuts.index+1 }</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="add${entityName}Btn[${"$"}{stuts.index}]" id="add${entityName}Btn[${"$"}{stuts.index}]" href="#"
               onclick="clickAdd${entityName}Btn(${"$"}{stuts.index});"></a>

            <#--<c:if test="${"$"}{stuts.index > 0}">-->
              <a name="del${entityName}Btn[${"$"}{stuts.index}]" id="del${entityName}Btn[${"$"}{stuts.index}]" href="#"
                 onclick="clickDel${entityName}Btn(${"$"}{stuts.index});"></a>
            <#--</c:if>-->
          </div>
        </td>
				<#list columns as po>
				<#if po.isShow=="N">
					<input name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" type="hidden" value="${'$'}{poVal.${po.fieldName} }"/>
				</#if>
				</#list>
				<#list pageColumns as po>
				  <#assign check = 0 >
				  <#list foreignKeys as key>
				  <#if fieldMeta[po.fieldName]==key?uncap_first>
				  <#assign check = 1 >
				  <#break>
				  </#if>
				  </#list>
				  <#if check==0>
				   <td align="left" bgcolor="white">
				   <#if po.showType == "text">
					  	<input name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" maxlength="${po.length?c}" 
					  		type="text" class="inputxt"  style="width:120px;"
					  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}"<#else><#if po.type == 'int'> datatype="n"<#elseif po.type=='double'> datatype="/^(-?\d+)(\.\d+)?$/" <#else><#if po.isNull != 'Y'> datatype="*"</#if></#if></#if> value="${'$'}{poVal.${po.fieldName} }">
						<#elseif po.showType=='password'>
							<input name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" maxlength="${po.length?c}" 
					  		type="password" class="inputxt"  style="width:120px;"
					  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}"<#else><#if po.type == 'int'> datatype="n"<#elseif po.type=='double'> datatype="/^(-?\d+)(\.\d+)?$/" <#else><#if po.isNull != 'Y'> datatype="*"</#if></#if></#if> value="${'$'}{poVal.${po.fieldName} }">
						<#elseif po.showType=='radio' || po.showType=='select' || po.showType=='checkbox' || po.showType=='list'>
							<t:dictSelect field="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" type="${po.showType?if_exists?html}"
										<#if po.dictTable?if_exists?html != ''>dictTable="${po.dictTable?if_exists?html}" dictField="${po.dictField?if_exists?html}" dictText="${po.dictText?if_exists?html}"<#else>typeGroupCode="${po.dictField}"</#if> defaultVal="${'$'}{poVal.${po.fieldName} }" hasLabel="false"  title="${po.content}"></t:dictSelect>     
						<#elseif po.showType=='date'>
							<input name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" maxlength="${po.length?c}" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					  		<#if po.fieldValidType?if_exists?html != ''>
					               datatype="${po.fieldValidType?if_exists?html}"
					               <#else>
					               <#if po.isNull != 'Y'>datatype="*"</#if> 
					               </#if> value="<fmt:formatDate value='${'$'}{poVal.${po.fieldName}}' type="date" pattern="yyyy-MM-dd"/>">  
					      <#elseif po.showType=='datetime'>
					      	<input name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" maxlength="${po.length?c}" 
						  		type="text"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width:120px;"
						  		<#if po.fieldValidType?if_exists?html != ''>
					               datatype="${po.fieldValidType?if_exists?html}"
					               <#else>
					               <#if po.isNull != 'Y'>datatype="*"</#if> 
					               </#if> value="<fmt:formatDate value='${'$'}{poVal.${po.fieldName}}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">  
					      <#elseif po.showType=='file'>
					        <input type="hidden" id="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}"  value="${'$'}{poVal.${po.fieldName} }"/>
										<c:if test="${'$'}{empty poVal.${po.fieldName}}">
											<a  target="_blank" id="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}_href">未上传</a>
										</c:if>
										<c:if test="${'$'}{!empty poVal.${po.fieldName}}">
											<a  href="${'$'}{poVal.${po.fieldName}}"  target="_blank" id="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}_href">下载</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'${entityName?uncap_first}List\\[${'$'}{stuts.index }\\]\\.${po.fieldName}')"/> 
					       <#else>
					       	<input name="${entityName?uncap_first}List[${'$'}{stuts.index }].${po.fieldName}" maxlength="${po.length?c}" 
						  		type="text" class="inputxt"  style="width:120px;"
						  		<#if po.fieldValidType?if_exists?html != ''> datatype="${po.fieldValidType?if_exists?html}"<#else><#if po.type == 'int'> datatype="n"<#elseif po.type=='double'> datatype="/^(-?\d+)(\.\d+)?$/" <#else><#if po.isNull != 'Y'> datatype="*"</#if></#if></#if> value="${'$'}{poVal.${po.fieldName} }">
					  </#if>
					  <label class="Validform_label" style="display: none;">${po.content?if_exists?html}</label>
				   </td>
				  </#if>
   			 	</#list>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
