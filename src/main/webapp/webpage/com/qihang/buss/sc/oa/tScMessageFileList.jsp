<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTScMessageFileBtn').linkbutton({
	    iconCls: 'icon-add'  
	});  
	$('#delTScMessageFileBtn').linkbutton({
	    iconCls: 'icon-remove'  
	}); 
	$('#addTScMessageFileBtn').bind('click', function(){
 		 var tr =  $("#add_tScMessageFile_table_template tr").clone();
	 	 $("#add_tScMessageFile_table").append(tr);
	 	 resetTrNum('add_tScMessageFile_table');
	 	 return false;
    });  
	$('#delTScMessageFileBtn').bind('click', function(){
      	$("#add_tScMessageFile_table").find("input:checked").parent().parent().remove();
        resetTrNum('add_tScMessageFile_table');
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScMessageFile_table").createhftable({
	    	height: (h-50)+'px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTScMessageFileBtn" href="#">添加</a> <a id="delTScMessageFileBtn" href="#">删除</a>
</div>
<table border="0" cellpadding="2" cellspacing="0" id="tScMessageFile_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						附件
				  </td>
	</tr>
	<tbody id="add_tScMessageFile_table">
	<c:if test="${fn:length(tScMessageFileList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tScMessageFileList[0].id" type="hidden"/>
					<input name="tScMessageFileList[0].createName" type="hidden"/>
					<input name="tScMessageFileList[0].createBy" type="hidden"/>
					<input name="tScMessageFileList[0].createDate" type="hidden"/>
					<input name="tScMessageFileList[0].updateName" type="hidden"/>
					<input name="tScMessageFileList[0].updateBy" type="hidden"/>
					<input name="tScMessageFileList[0].updateDate" type="hidden"/>
					<input name="tScMessageFileList[0].messageId" type="hidden"/>
				  <td align="left">
							<input type="hidden" id="tScMessageFileList[0].messageFile" name="tScMessageFileList[0].messageFile" />
										<a  target="_blank" id="tScMessageFileList[0].messageFile_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'tScMessageFileList\\[0\\]\\.messageFile')"/>
					  <label class="Validform_label" style="display: none;">附件</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScMessageFileList)  > 0 }">
		<c:forEach items="${tScMessageFileList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tScMessageFileList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScMessageFileList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScMessageFileList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScMessageFileList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScMessageFileList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScMessageFileList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScMessageFileList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScMessageFileList[${stuts.index }].messageId" type="hidden" value="${poVal.messageId }"/>
				   <td align="left">
					        <input type="hidden" id="tScMessageFileList[${stuts.index }].messageFile" name="tScMessageFileList[${stuts.index }].messageFile"  value="${poVal.messageFile }"/>
										<c:if test="${empty poVal.messageFile}">
											<a  target="_blank" id="tScMessageFileList[${stuts.index }].messageFile_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.messageFile}">
											<a  href="${poVal.messageFile}"  target="_blank" id="tScMessageFileList[${stuts.index }].messageFile_href">下载</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'tScMessageFileList\\[${stuts.index }\\]\\.messageFile')"/>
					  <label class="Validform_label" style="display: none;">附件</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
