<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTScNoticeFileBtn').linkbutton({
	    iconCls: 'icon-add'
	});
	$('#delTScNoticeFileBtn').linkbutton({
	    iconCls: 'icon-remove'
	});
	/*$('#addTScNoticeFileBtn').bind('click', function(){
 		 var tr =  $("#add_tScNoticeFile_table_template tr").clone();
	 	 $("#add_tScNoticeFile_table").append(tr);
	 	 resetTrNum('add_tScNoticeFile_table');
	 	 return false;
    });
	$('#delTScNoticeFileBtn').bind('click', function(){
      	$("#add_tScNoticeFile_table").find("input:checked").parent().parent().remove();
        resetTrNum('add_tScNoticeFile_table');
        return false;
    }); */
	function clickAddTScNoticeFileBtn(rowIndex){
		var tr =  $("#add_tScNoticeFile_table_template tr").clone();
		$("#add_tScNoticeFile_table tr").eq(rowIndex).after(tr);
		resetTrNum('add_tScNoticeFile_table');
	}
	function clickDelTScNoticeFileBtn(rowIndex) {
		$("#add_tScNoticeFile_table").find(">tr").eq(rowIndex).remove();
		resetTrNum('add_tScNoticeFile_table');
	}


    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScNoticeFile_table").createhftable({
	    	height: (h-50)+'px',
			width:'auto',
			fixFooter:false
			});
    });
</script>
<%--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">--%>
	<%--<a id="addTScNoticeFileBtn" href="#">添加</a> <a id="delTScNoticeFileBtn" href="#">删除</a>--%>
<%--</div>--%>
<table border="0" cellpadding="2" cellspacing="0" id="tScNoticeFile_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						附件名称
				  </td>
	</tr>
	<tbody id="add_tScNoticeFile_table">
	<c:if test="${fn:length(tScNoticeFileList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><div style="width: 80px;"><a name="addTScNoticeFileBtn[0]" id="addTScNoticeFileBtn[0]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScNoticeFileBtn(0);"></a></div></td>
			<%--<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>--%>
					<input name="tScNoticeFileList[0].id" type="hidden"/>
					<input name="tScNoticeFileList[0].createName" type="hidden"/>
					<input name="tScNoticeFileList[0].createBy" type="hidden"/>
					<input name="tScNoticeFileList[0].createDate" type="hidden"/>
					<input name="tScNoticeFileList[0].updateName" type="hidden"/>
					<input name="tScNoticeFileList[0].updateBy" type="hidden"/>
					<input name="tScNoticeFileList[0].updateDate" type="hidden"/>
					<input name="tScNoticeFileList[0].noteId" type="hidden"/>
				  <td align="left">
							<input type="hidden" id="tScNoticeFileList[0].fileName" name="tScNoticeFileList[0].fileName" />
										<a  target="_blank" id="tScNoticeFileList[0].fileName_href">未上传</a>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'tScNoticeFileList\\[0\\]\\.fileName')"/>
					  <label class="Validform_label" style="display: none;">附件名称</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScNoticeFileList)  > 0 }">
		<c:forEach items="${tScNoticeFileList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<%--<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>--%>
				<td align="center">
					<div style="width: 80px;">
						<c:if test="${stuts.index == 0}">
							<a name="addTScNoticeFileBtn[${stuts.index }]" id="addTScNoticeFileBtn[${stuts.index }]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScNoticeFileBtn(${stuts.index});"></a>
						</c:if>
						<c:if test="${stuts.index >0}">
							<a name="addTScNoticeFileBtn[${stuts.index }]" id="addTScNoticeFileBtn[${stuts.index }]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScNoticeFileBtn(${stuts.index});"></a>
							<a name="delTScNoticeFileBtn[${stuts.index }]" id="delTScNoticeFileBtn[${stuts.index }]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScNoticeFileBtn(${stuts.index});"></a>
						</c:if>
					</div>
				</td>

				<input name="tScNoticeFileList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScNoticeFileList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScNoticeFileList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScNoticeFileList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScNoticeFileList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScNoticeFileList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScNoticeFileList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScNoticeFileList[${stuts.index }].noteId" type="hidden" value="${poVal.noteId }"/>
				   <td align="left">
					        <input type="hidden" id="tScNoticeFileList[${stuts.index }].fileName" name="tScNoticeFileList[${stuts.index }].fileName"  value="${poVal.fileName }"/>
										<c:if test="${empty poVal.fileName}">
											<a  target="_blank" id="tScNoticeFileList[${stuts.index }].fileName_href">未上传</a>
										</c:if>
										<c:if test="${!empty poVal.fileName}">
											<a  href="${poVal.fileName}"  target="_blank" id="tScNoticeFileList[${stuts.index }].fileName_href">下载</a>
										</c:if>
										<br>
									   <input class="ui-button" type="button" value="上传附件"
													onclick="commonUpload(commonUploadDefaultCallBack,'tScNoticeFileList\\[${stuts.index }\\]\\.fileName')"/>
					  <label class="Validform_label" style="display: none;">附件名称</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
