<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('#addTSAuditLeaveBtn').linkbutton({   
	    iconCls: 'icon-add'  
	});  
	$('#delTSAuditLeaveBtn').linkbutton({   
	    iconCls: 'icon-remove'  
	}); 
	$('#addTSAuditLeaveBtn').bind('click', function(){   
 		 var tr =  $("#add_tSAuditLeave_table_template tr").clone();
		$("#add_tSAuditLeave_table").append(tr);
	 	 resetTrNum('add_tSAuditLeave_table');
	 	 return false;
    });  
	$('#delTSAuditLeaveBtn').bind('click', function(){   
      	$("#add_tSAuditLeave_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tSAuditLeave_table'); 
        return false;
    }); 
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		$(".datagrid-toolbar").css("display","none");
		//将表格的表头固定
	    $("#tSAuditLeave_table").createhftable({
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
		var isAudit = $("#isAudit").val();
		if(!isAudit || isAudit == "0"){
			var $tbody = $("#add_tSAuditLeave_table");
			var length = $tbody[0].rows.length;
			for(var i = 0 ; i < length ; i++){
				$("#tree\\["+i+"\\]").combotree({disabled:true});
			}
		}
    });
</script>
<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">
	<%--<a id="addTSAuditLeaveBtn" href="#">添加</a> <a id="delTSAuditLeaveBtn" href="#">删除</a> --%>
</div>
<input type="hidden" id="rowSize" value="${fn:length(tSAuditLeaveList)}">
<table border="0" cellpadding="2" cellspacing="1" id="tSAuditLeave_table" style="background-color: #cbccd2" >
	<tr bgcolor="#E6E6E6" style="height:24px;color: white">
		<%--<td align="center" bgcolor="#EEEEEE">序号</td>--%>
		<%--<td align="center" bgcolor="#EEEEEE">操作</td>--%>
				  <td align="center" bgcolor="#476f9a">
						级次
				  </td>
				  <td align="center"  bgcolor="#476f9a">
						审核人<span style="color:red">*</span>
				  </td>
	</tr>
	<tbody id="add_tSAuditLeave_table">	
	<c:if test="${fn:length(tSAuditLeaveList)  <= 0 }">
			<tr>
				<%--<td align="center"><div style="width: 25px;" name="xh">1</div></td>--%>
				<%--<td align="center"><input style="width:20px;"  type="checkbox" name="ck"/></td>--%>
					<input name="tSAuditLeaveList[0].id" type="hidden"/>
					<input name="tSAuditLeaveList[0].createName" type="hidden"/>
					<input name="tSAuditLeaveList[0].createBy" type="hidden"/>
					<input name="tSAuditLeaveList[0].createDate" type="hidden"/>
					<input name="tSAuditLeaveList[0].updateName" type="hidden"/>
					<input name="tSAuditLeaveList[0].updateBy" type="hidden"/>
					<input name="tSAuditLeaveList[0].updateDate" type="hidden"/>
					<input name="tSAuditLeaveList[0].pid" type="hidden"/>
					<input name="tSAuditLeaveList[0].maxLeaveMoney" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="tSAuditLeaveList[0].leaveNum" maxlength="50" 
					  		type="text" class="inputxt" value="1" readonly="readonly" style="width:50px;text-align: center"
					               
					               >
					  <label class="Validform_label" style="display: none;">级次</label>
					</td>
				  <td align="left" style="width: 500px" bgcolor="white">
					  <input id="tree[0]" name="tSAuditLeaveList[0].auditorId" class="easyui-combotree"  data-options="url:'tSAuditController.do?loadUserInfo',width:500,panelHeight:200,multiple:true"/>
							<%--<t:dictSelect field="auditorId" type="list"--%>
										<%--dictTable="T_S_Base_User"  dictField="id" dictText="realName" defaultVal="${tSAuditLeavePage.auditorId}" hasLabel="false"  title="审核人"></t:dictSelect>--%>
					  <label class="Validform_label" style="display: none;">审核人</label>
					</td>
   			</tr>

	</c:if>
	<c:if test="${fn:length(tSAuditLeaveList)  > 0 }">
		<c:forEach items="${tSAuditLeaveList}" var="poVal" varStatus="stuts">
			<tr>
				<%--<td align="center"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>--%>
				<%--<td align="center"><input style="width:20px;"  type="checkbox" name="ck" /></td>--%>
					<input name="tSAuditLeaveList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tSAuditLeaveList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tSAuditLeaveList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tSAuditLeaveList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tSAuditLeaveList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tSAuditLeaveList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tSAuditLeaveList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tSAuditLeaveList[${stuts.index }].pid" type="hidden" value="${poVal.pid }"/>
					<input name="tSAuditLeaveList[${stuts.index }].maxLeaveMoney" type="hidden" value="${poVal.maxLeaveMoney }"/>
				    <input id="auditorId${stuts.index }" type="hidden" value="${poVal.auditorId}"/>
				   <td align="left" bgcolor="white">
					  	<input name="tSAuditLeaveList[${stuts.index }].leaveNum" maxlength="50" 
					  		type="text" class="inputxt"  readonly="readonly" style="width:50px;text-align: center;"
					               
					                value="${poVal.leaveNum }">
					  <label class="Validform_label" style="display: none;">级次</label>
				   </td>
				   <td align="left" style="width: 500px" bgcolor="white">
							<%--<t:dictSelect field="tSAuditLeaveList[${stuts.index }].auditorId" type="list"--%>
										<%--dictTable="T_S_Base_User" dictField="id" dictText="realName" defaultVal="${poVal.auditorId }" hasLabel="false"  title="审核人"></t:dictSelect>     --%>
								<input id="tree[${stuts.index }]" style="width: 500px" name="tSAuditLeaveList[${stuts.index }].auditorId" class="easyui-combotree"  data-options="valueField:'id',textField:'text',onLoadSuccess:setValue,url:'tSAuditController.do?loadUserInfo',width:500,panelHeight:200,multiple:true"/>
								<%--<script type="text/javascript">--%>
									<%--debugger;--%>
									<%--var value = "${poVal.auditorId}";--%>
									<%--var values = value.split(",");--%>
									<%--var newValue = [];--%>
									<%--for(var i=0 ; i < values.length; i++){--%>
										<%--var v = values[i];--%>
										<%--newValue.push(v);--%>
									<%--}--%>
									<%--//$("#auditorId${stuts.index }").combotree("setValues",newValue);--%>
								<%--</script>--%>

					  <label class="Validform_label" style="display: none;">审核人</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>
	</tbody>
</table>
