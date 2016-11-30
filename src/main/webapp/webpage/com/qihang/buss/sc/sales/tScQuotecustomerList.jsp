<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//	$('#addTScQuotecustomerBtn\\[0\\]').linkbutton({
//	    iconCls: 'icon-add',
//		plain:'true'
//	});
//	$('#delTScQuotecustomerBtn').linkbutton({
//	    iconCls: 'icon-remove'
//	});
/*	$('#addTScQuotecustomerBtn').bind('click', function(){
 		 var tr =  $("#add_tScQuotecustomer_table_template tr").clone();
	 	 $("#add_tScQuotecustomer_table").append(tr);
	 	 resetTrNum('add_tScQuotecustomer_table');
	 	 return false;
    });  */
    function clickAddTScQuotecustomerBtn(rowIndex){
		var tr =  $("#add_tScQuotecustomer_table_template tr").clone();
		$("#add_tScQuotecustomer_table tr").eq(rowIndex).after(tr);
		resetTrNum('add_tScQuotecustomer_table');
	}
/*
	$('#delTScQuotecustomerBtn').bind('click', function(){   
      	$("#add_tScQuotecustomer_table").find("input:checked").parent().parent().remove();   
        resetTrNum('add_tScQuotecustomer_table'); 
        return false;
    }); */
  function clickDelTScQuotecustomerBtn(rowIndex){
	  //$("#add_tScQuotecustomer_table tr").eq(rowIndex).remove();
	  //resetTrNum('add_tScQuotecustomer_table');
	  var itemId = $('input[name="tScQuotecustomerList[' + rowIndex + '].itemID"]').val();
	  itemIds = itemIds.replace(itemId+rowIndex+",","");
	  var len = $("#add_tScQuotecustomer_table").find(">tr").length;
	  $("#add_tScQuotecustomer_table").find(">tr").eq(rowIndex).remove();
	  if (rowIndex == 0 && len == 1) {//如果只有一行且删除这一行则删除后补一空行
		  var tr = $("#add_tScQuotecustomer_table_template tr").clone();
		  $("#add_tScQuotecustomer_table").append(tr);
	  }
	  resetTrNum('add_tScQuotecustomer_table');
  }
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScQuotecustomer_table").createhftable({
				height: (h-90)+'px',
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
		$("#add_tScQuotecustomer_table tr").each(function(i,data){
			$('#addTScQuotecustomerBtn\\['+i+'\\]').linkbutton({
				iconCls: 'icon-add',
				plain:'true'
			});
			$('#delTScQuotecustomerBtn\\['+i+'\\]').linkbutton({
				iconCls: 'icon-remove',
				plain:'true'
			});
		});
		if(${fn:length(tScQuotecustomerList) > 0}){
			var  length = $("#add_tScQuotecustomer_table").find("tr").length;
			for(var j = 0 ;j < length;j++ ){
				setValOldIdAnOldName($('input[name="tScQuotecustomerList[' + j + '].itemName"]'), $('input[name="tScQuotecustomerList[' + j + '].itemID"]').val(), $('input[name="tScQuotecustomerList[' + j + '].itemName"]').val());
			}

		}
    });
</script>
<table border="0" cellpadding="2" cellspacing="1"  id="tScQuotecustomer_table" totalRow="false"  style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<c:if test="${load ne 'detail'}">
		<td align="center" bgcolor="#476f9a">操作</td>
		</c:if>
				  <td align="left" bgcolor="#476f9a">
						客户<span style="color: red">*</span>
				  </td>
				  <td align="left" bgcolor="#476f9a">
						联系人
				  </td>
				  <td align="left" bgcolor="#476f9a">
						手机号码
				  </td>
				  <td align="left" bgcolor="#476f9a">
						电话
				  </td>
				  <td align="left" bgcolor="#476f9a">
						传真
				  </td>
				  <td align="left" bgcolor="#476f9a">
						联系地址
				  </td>
				  <td align="left" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tScQuotecustomer_table">	
	<c:if test="${fn:length(tScQuotecustomerList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScQuotecustomerList[0].id" type="hidden"/>
					<input name="tScQuotecustomerList[0].createName" type="hidden"/>
					<input name="tScQuotecustomerList[0].createBy" type="hidden"/>
					<input name="tScQuotecustomerList[0].createDate" type="hidden"/>
					<input name="tScQuotecustomerList[0].updateName" type="hidden"/>
					<input name="tScQuotecustomerList[0].updateBy" type="hidden"/>
					<input name="tScQuotecustomerList[0].updateDate" type="hidden"/>
					<input name="tScQuotecustomerList[0].fid" type="hidden"/>
					<input name="tScQuotecustomerList[0].indexNumber" type="hidden" value="1"/>
					<input name="tScQuotecustomerList[0].version" type="hidden"/>
					<input name="tScQuotecustomerList[0].itemID" type="hidden"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				  <td align="center" bgcolor="white">
					  <div style="width: 80px;background-color: white;">
						  <a id="addTScQuotecustomerBtn[0]" name="addTScQuotecustomerBtn[0]" href="#"  onclick="clickAddTScQuotecustomerBtn(0);"></a>
						  <a name="delTScQuotecustomerBtn[0]" id="delTScQuotecustomerBtn[0]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScQuotecustomerBtn(0);"></a>
				      </div>
				  </td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuotecustomerList[0].itemName" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;"
							   datatype="*"   onkeypress="tScQuotecustomerItemIdListKeyPress(0,event);"
							   onblur="tScQuotecustomerItemIdListBlur(0);">
					  <label class="Validform_label" style="display: none;">客户</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuotecustomerList[0].contact" maxlength="25"
					  		type="text" class="inputxt"  style="width:120px;"

					               >
					  <label class="Validform_label" style="display: none;">联系人</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuotecustomerList[0].mobilePhone" maxlength="15" datatype="m" ignore="ignore"
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">手机号码</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuotecustomerList[0].phone" maxlength="40"  datatype="po" ignore="ignore"
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">电话</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuotecustomerList[0].fax" maxlength="40" 
					  		type="text" class="inputxt"  style="width:120px;"  datatype="f" ignore="ignore"
					               
					               >
					  <label class="Validform_label" style="display: none;">传真</label>
					</td>
				  <td align="left" bgcolor="white">
					       	<input name="tScQuotecustomerList[0].address" maxlength="125"
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                >
					  <label class="Validform_label" style="display: none;">联系地址</label>
					</td>
				  <td align="left" bgcolor="white">
					       	<input name="tScQuotecustomerList[0].note" maxlength="125"
						  		type="text" class="inputxt"  style="width:120px;"
					               
					                >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScQuotecustomerList)  > 0 }">
		<c:forEach items="${tScQuotecustomerList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScQuotecustomerList[${stuts.index}].id" type="hidden" value="${poVal.id}"/>
					<input name="tScQuotecustomerList[${stuts.index}].createName" type="hidden"  value="${poVal.createName}"/>
					<input name="tScQuotecustomerList[${stuts.index}].createBy" type="hidden" value="${poVal.createBy}"/>
					<input name="tScQuotecustomerList[${stuts.index}].createDate" type="hidden" value="${poVal.createDate}"/>
					<input name="tScQuotecustomerList[${stuts.index}].updateName" type="hidden" value="${poVal.updateName}"/>
					<input name="tScQuotecustomerList[${stuts.index}].updateBy" type="hidden" value="${poVal.updateBy}"/>
					<input name="tScQuotecustomerList[${stuts.index}].updateDate" type="hidden" value="${poVal.updateDate}"/>
					<input name="tScQuotecustomerList[${stuts.index}].fid" type="hidden"  value="${poVal.fid}"/>
					<input name="tScQuotecustomerList[${stuts.index}].indexNumber" type="hidden" value="${stuts.index+1}"/>
					<input name="tScQuotecustomerList[${stuts.index}].version" type="hidden" value="${poVal.version}"/>
					<input name="tScQuotecustomerList[${stuts.index}].itemID" type="hidden" value="${poVal.itemID}"/>
					<div style="width: 25px;background-color: white;" name="xh">${stuts.index+1}</div>
				</td>
				<c:if test="${load ne 'detail'}">
				<td align="center" bgcolor="white">
					<div style="width: 80px;background-color: white;">
						<a id="addTScQuotecustomerBtn[${stuts.index}]" name="addTScQuotecustomerBtn[${stuts.index}]" href="#"  onclick="clickAddTScQuotecustomerBtn(${stuts.index});"></a>
						    <a name="delTScQuotecustomerBtn[${stuts.index}]" id="delTScQuotecustomerBtn[${stuts.index}]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScQuotecustomerBtn(${stuts.index});"></a>
					</div>
				</td>
				</c:if>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].itemName" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;"
						   datatype="*"   onkeypress="tScQuotecustomerItemIdListKeyPress(${stuts.index},event);"
						   onblur="tScQuotecustomerItemIdListBlur(${stuts.index});" value="${poVal.itemName}">
					<label class="Validform_label" style="display: none;">客户</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].contact" maxlength="25"
						   type="text" class="inputxt"  style="width:120px;" value="${poVal.contact}"

							>
					<label class="Validform_label" style="display: none;">联系人</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].mobilePhone" maxlength="15" datatype="m" ignore="ignore" value="${poVal.mobilePhone}"
						   type="text" class="inputxt"  style="width:120px;"

							>
					<label class="Validform_label" style="display: none;">手机号码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].phone" maxlength="40"  datatype="po" ignore="ignore" value="${poVal.phone}"
						   type="text" class="inputxt"  style="width:120px;"

							>
					<label class="Validform_label" style="display: none;">电话</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].fax" maxlength="40" value="${poVal.fax}"
						   type="text" class="inputxt"  style="width:120px;"  datatype="f" ignore="ignore"

							>
					<label class="Validform_label" style="display: none;">传真</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].address" maxlength="125" value="${poVal.address}"
						   type="text" class="inputxt"  style="width:120px;"

							>
					<label class="Validform_label" style="display: none;">联系地址</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuotecustomerList[${stuts.index}].note" maxlength="125"  value="${poVal.note}"
						   type="text" class="inputxt"  style="width:120px;"
							>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
