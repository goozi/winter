<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTSTreeinfoEntryBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTSTreeinfoEntryBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTSTreeinfoEntryBtn').bind('click', function(){   
 		 var tr =  $("#add_tSTreeinfoEntry_table_template tr").clone();
	 	 $("#add_tSTreeinfoEntry_table").append(tr);
	 	 resetTrNum('add_tSTreeinfoEntry_table');
	 	 return false;
    });  
	$('#delTSTreeinfoEntryBtn').bind('click', function(){   
      	$("#add_tSTreeinfoEntry_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tSTreeinfoEntry_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tSTreeinfoEntry_table").createhftable({
	    	height: (h-50)+'px',
			width:'auto',
			fixFooter:false
			});
    });

</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<a id="addTSTreeinfoEntryBtn" href="#">添加</a> <a id="delTSTreeinfoEntryBtn" href="#">删除</a> 
</div>
<table border="0" cellpadding="2" cellspacing="0" id="tSTreeinfoEntry_table">
	<tr bgcolor="#E6E6E6">
		<td align="center" bgcolor="#EEEEEE">序号</td>
		<td align="center" bgcolor="#EEEEEE">操作</td>
				  <td align="left" bgcolor="#EEEEEE">
						节点名称
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						类型
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						内容
				  </td>
				  <td align="left" bgcolor="#EEEEEE">
						备注
				  </td>
	</tr>
	<tbody id="add_tSTreeinfoEntry_table">	
	<c:if test="${fn:length(tSTreeinfoEntryList)  <= 0 }">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>
					<input name="tSTreeinfoEntryList[0].id" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].createName" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].createBy" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].createDate" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].updateName" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].updateBy" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].updateDate" type="hidden"/>
					<input name="tSTreeinfoEntryList[0].parentId" type="hidden"/>
				  <td align="left">
					  	<input name="tSTreeinfoEntryList[0].nodeName" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					               datatype="*1-30"
>
					  <label class="Validform_label" style="display: none;">节点名称</label>
					</td>
				  <td align="left">
							<t:dictSelect field="tSTreeinfoEntryList[0].type" type="list"
										typeGroupCode="NODE_VTYPE" hasLabel="false"  title="类型"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">类型</label>
					</td>
				  <td align="left">
					  	<input name="tSTreeinfoEntryList[0].value" maxlength="255"
					  		type="text" class="inputxt"  style="width:120px;"

					               >
					  <label class="Validform_label" style="display: none;">内容</label>
					</td>
				  <td align="left">
					  	<input name="tSTreeinfoEntryList[0].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tSTreeinfoEntryList)  > 0 }">
		<c:forEach items="${tSTreeinfoEntryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>
					<input name="tSTreeinfoEntryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].updateDate" type="hidden"/>
					<input name="tSTreeinfoEntryList[${stuts.index }].parentId" type="hidden" value="${poVal.parentId }"/>
				   <td align="left">
					  	<input name="tSTreeinfoEntryList[${stuts.index }].nodeName" maxlength="100" 
					  		type="text" class="inputxt"  style="width:120px;"
					               datatype="*1-30"
 value="${poVal.nodeName }">
					  <label class="Validform_label" style="display: none;">节点名称</label>
				   </td>
				   <td align="left">
							<t:dictSelect field="tSTreeinfoEntryList[${stuts.index }].type" type="list"
										typeGroupCode="NODE_VTYPE" defaultVal="${poVal.type}"  hasLabel="false"  title="类型"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">类型</label>
				   </td>
				   <td align="left">
					  	<input name="tSTreeinfoEntryList[${stuts.index }].value" maxlength="255"
					  		type="text" class="inputxt"  style="width:120px;"

					                value="${poVal.value }">
					  <label class="Validform_label" style="display: none;">内容</label>
				   </td>
				   <td align="left">
					  	<input name="tSTreeinfoEntryList[${stuts.index }].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					                value="${poVal.note }">
					  <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
