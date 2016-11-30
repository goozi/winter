<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScPrcplyentry1Btn"]').linkbutton({
		iconCls: 'icon-add',
		plain: true
	});
	$('a[name^="delTScPrcplyentry1Btn"]').linkbutton({
		iconCls: 'icon-remove',
		plain: true
	});
	function clickAddEntryBtn(index,entryName){
		var tr =  $("#add_tScPrcplyentry1_table_template tr").clone();
		$("#add_tScPrcplyentry1_table tr").eq(index).after(tr);
		resetTrNum('add_tScPrcplyentry1_table',index,entryName+"List");
	}
	function clickDelEntryBtn(index){
		$("#add_tScPrcplyentry1_table").find(">tr").eq(index).remove();
		var length = $("#add_tScPrcplyentry1_table").find(">tr").length;
		if(length == 0){
			//clickAddEntryBtn(0)
			var tr =  $("#add_tScPrcplyentry1_table_template tr").clone();
			$("#add_tScPrcplyentry1_table").append(tr);
			resetTrNum('add_tScPrcplyentry1_table',-1,"tScPrcplyentry1List");
		}else {
			resetTrNum('add_tScPrcplyentry1_table',index,"tScPrcplyentry1List");
		}
	}
function keyDownInfo(index,name,evt){
	var evt = (evt)?evt:((window.event)?window.event:"");
	var code =evt.keyCode?evt.keyCode:evt.which;
	if(code == 13){
		if(name == "item"){
			selectIcitemDialog(index);
		}else if(name == "unit"){
			selectUnitDialog(index);
		}else {
			selectCustomDialog(index);
		}
	}
}
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScPrcplyentry1_table").createhftable({
	    	height:(h-40)+'px',
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
<table border="0" cellpadding="2" cellspacing="1" id="tScPrcplyentry1_table"  style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
		<td align="left" bgcolor="#476f9a">客户<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">联系人</td>
		<td align="left" bgcolor="#476f9a">手机号码</td>
		<td align="left" bgcolor="#476f9a">电话</td>
		<td align="left" bgcolor="#476f9a">传真</td>
		<td align="left" bgcolor="#476f9a">地址</td>
		<td align="left" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScPrcplyentry1_table">	
	<c:if test="${fn:length(tScPrcplyentry1List)  <= 0 }">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScPrcplyentry1List[0].id" type="hidden"/>
					<input name="tScPrcplyentry1List[0].createName" type="hidden"/>
					<input name="tScPrcplyentry1List[0].createBy" type="hidden"/>
					<input name="tScPrcplyentry1List[0].createDate" type="hidden"/>
					<input name="tScPrcplyentry1List[0].updateName" type="hidden"/>
					<input name="tScPrcplyentry1List[0].updateBy" type="hidden"/>
					<input name="tScPrcplyentry1List[0].updateDate" type="hidden"/>
					<input name="tScPrcplyentry1List[0].indexNumber" type="hidden" value="1"/>
					<input name="tScPrcplyentry1List[0].interID" type="hidden"/>
					<input name="tScPrcplyentry1List[0].disabled" type="hidden"/>
					<input name="tScPrcplyentry1List[0].deleted" type="hidden"/>
					<input name="tScPrcplyentry1List[0].version" type="hidden"/>
					<input name="tScPrcplyentry1List[0].itemID" type="hidden" id="itemID"/>
					<div style="width: 25px;background-color: white" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;">
						<a name="addTScPrcplyentry1Btn[0]" id="addTScPrcplyentry1Btn[0]" href="#" onclick="clickAddEntryBtn(0,'tScPrcplyentry1');"></a>
						<a name="delTScPrcplyentry1Btn[0]" id="delTScPrcplyentry1Btn[0]" href="#" onclick="clickDelEntryBtn(0);"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].itemName" type="text" class="inputxt popup-select"  style="width:180px;" datatype="*" onkeypress="selectItemNameDialog(0,event)"/>
				    <label class="Validform_label" style="display: none;">客户</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].contact" maxlength="50" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				    <label class="Validform_label" style="display: none;">联系人</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].mobilePhone" maxlength="15" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="m" ignore="ignore" />
					<label class="Validform_label" style="display: none;">手机号码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].phone" maxlength="40" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="po" ignore="ignore" />
					<label class="Validform_label" style="display: none;">电话</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].fax" maxlength="40" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="f" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">传真</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].address" maxlength="255" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
					<label class="Validform_label" style="display: none;">地址</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[0].note" maxlength="255" type="text" class="inputxt"  style="width:200px;" />
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScPrcplyentry1List)  > 0 }">
		<c:forEach items="${tScPrcplyentry1List}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].indexNumber" type="hidden" value="${poVal.indexNumber }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].interID" type="hidden" value="${poVal.interID }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].disabled" type="hidden" value="${poVal.disabled }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].deleted" type="hidden" value="${poVal.deleted }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].version" type="hidden" value="${poVal.version }"/>
					<input name="tScPrcplyentry1List[${stuts.index }].itemID" type="hidden" value="${poVal.itemID }"/>
					<div style="width: 25px;" name="xh">${stuts.index+1 }</div
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;">
						<a name="addTScPrcplyentry1Btn[${stuts.index }]" id="addTScPrcplyentry1Btn[${stuts.index }]" href="#" onclick="clickAddEntryBtn(${stuts.index } ,'tScPrcplyentry1');"></a>
						<a name="delTScPrcplyentry1Btn[${stuts.index }]" id="delTScPrcplyentry1Btn[${stuts.index }]" href="#" onclick="clickDelEntryBtn(${stuts.index });"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].itemName" maxlength="50" type="text" class="inputxt popup-select"  style="width:180px;" datatype="*" onkeypress="selectItemNameDialog(${stuts.index} ,event)" value="${poVal.itemName }" />
					<label class="Validform_label" style="display: none;">客户</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].contact" maxlength="50" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.contact }"/>
					<label class="Validform_label" style="display: none;">联系人</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].mobilePhone" maxlength="15" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"  datatype="m" ignore="ignore" value="${poVal.mobilePhone }"/>
					<label class="Validform_label" style="display: none;">手机号码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].phone" maxlength="40" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="po" ignore="ignore" value="${poVal.phone }" />
					<label class="Validform_label" style="display: none;">电话</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].fax" maxlength="40" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="f" ignore="ignore" value="${poVal.fax }"/>
					<label class="Validform_label" style="display: none;">传真</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].address" maxlength="255" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.address }"/>
					<label class="Validform_label" style="display: none;">地址</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry1List[${stuts.index }].note" maxlength="255" type="text" class="inputxt"  style="width:100px;" value="${poVal.note }"/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
