<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//	$('#addTScQuoteitemsBtn').linkbutton({
//	    iconCls: 'icon-add'
//	});
//	$('#delTScQuoteitemsBtn').linkbutton({
//	    iconCls: 'icon-remove'
//	});
//	$('#addTScQuoteitemsBtn').bind('click', function(){
// 		 var tr =  $("#add_tScQuoteitems_table_template tr").clone();
//	 	 $("#add_tScQuoteitems_table").append(tr);
//	 	 resetTrNum('add_tScQuoteitems_table');
//	 	 return false;
//    });
//	$('#delTScQuoteitemsBtn').bind('click', function(){
//      	$("#add_tScQuoteitems_table").find("input:checked").parent().parent().remove();
//        resetTrNum('add_tScQuoteitems_table');
//        return false;
//    });
	function clickAddTScQuoteitemsBtn(rowIndex){
		var tr =  $("#add_tScQuoteitems_table_template tr").clone();
		$("#add_tScQuoteitems_table tr").eq(rowIndex).after(tr);
		resetQuoteItemTrNum('add_tScQuoteitems_table',rowIndex);
	}
    function  clickDelTScQuoteitemsBtn(rowIndex){
		var len = $("#add_tScQuoteitems_table").find(">tr").length;
		$("#add_tScQuoteitems_table").find(">tr").eq(rowIndex).remove();
		if (rowIndex == 0 && len == 1) {//如果只有一行且删除这一行则删除后补一空行
			var tr = $("#add_tScQuoteitems_table_template tr").clone();
			$("#add_tScQuoteitems_table").append(tr);
			rowIndex--;
		}
		resetQuoteItemTrNum('add_tScQuoteitems_table',rowIndex);
		if (rowIndex == 0 && len == 1) {
			$('#tScQuoteitemsList\\[' + 0 + '\\]\\.unitID').combobox({
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				width:84
			});
		}
//		$("#add_tScQuoteitems_table tr").eq(rowIndex).remove();
//		resetQuoteItemTrNum('add_tScQuoteitems_table');
	}

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScQuoteitems_table").createhftable({
	    	height: (h-90)+'px',
			width:'100%',
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
		$("#add_tScQuoteitems_table tr").each(function(i,data){
			$('#addTScQuoteitemsBtn\\['+i+'\\]').linkbutton({
				iconCls: 'icon-add',
				plain:'true'
			});
			$('#delTScQuoteitemsBtn\\['+i+'\\]').linkbutton({
				iconCls: 'icon-remove',
				plain:'true'
			});
			$('#tScQuoteitemsList\\['+i+'\\]\\.unitID').combobox({
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				width:84
			});
		});
       if(${fn:length(tScQuoteitemsList) > 0}){
		   var  length = $("#add_tScQuoteitems_table").find("tr").length;
		   for(var j=0 ; j<length ; j++){
			   setValOldIdAnOldName($('input[name="tScQuoteitemsList['+j+'].itemNumber"]'),$('input[name="tScQuoteitemsList['+j+'].itemID"]').val(), $('input[name="tScQuoteitemsList['+j+'].itemNumber"]').val());
			   $('#tScQuoteitemsList\\[' + j + '\\]\\.unitID').combobox({
				   onChange: function (newValue, oldValue) {
					   if (oldValue != newValue) {
						   var index = $(this)[0].id.replace(/[^0-9]/ig,"");
						   $.ajax({
							   type: 'POST',
							   url: 'tScIcitemController.do?getChangeInfo',
							   async: false,
							   cache: false,
							   data: {'unitId':newValue,'rowIndex':index},
							   dataType: 'json',
							   success: function (data) {
								   if (data.success == true) {
									   var resultData = data.attributes;
									   barCode = resultData.barCode;
									   var cgLatestPrice = resultData.cgLatestPrice;
									   var rowIndex = resultData.rowIndex;
									   $('input[name="tScQuoteitemsList[' + rowIndex+ '].itemBarcode"]').val(barCode);//条形码
									   $('input[name="tScQuoteitemsList[' + rowIndex + '].costPrice"]').val(cgLatestPrice);
									   $('input[name="tScQuoteitemsList[' + rowIndex+ '].price"]').trigger('change');
								   }
							   }
						   });
					   }
				   }
			   });

		   }
	   }
    });
</script>
<style>
	#tScQuoteitems_tablescrolldiv{
		overflow: visible !important;
	}
</style>
<table border="0" cellpadding="2" cellspacing="1" id="tScQuoteitems_table" totalRow="false"  style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<c:if test="${load ne 'detail'}">
		<td align="center" bgcolor="#476f9a">操作</td>
		</c:if>
		<td align="left" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">商品名称</td>
		<td align="left" bgcolor="#476f9a">规格</td>
		<td align="left" bgcolor="#476f9a">条形码</td>
				  <td align="left" bgcolor="#476f9a">
						单位<span style="color: red">*</span>
				  </td>
				  <td align="left" bgcolor="#476f9a">
						数量段(从)<span style="color: red">*</span>
				  </td>
				  <td align="left" bgcolor="#476f9a">
						数量段(至)<span style="color: red">*</span>
				  </td>
				  <td align="left" bgcolor="#476f9a">
						单价<span style="color: red">*</span>
				  </td>
				  <td align="left" bgcolor="#476f9a">
						成本单价
				  </td>
				  <td align="left" bgcolor="#476f9a">
						毛利
				  </td>
				  <td align="left" bgcolor="#476f9a">
						毛利率
				  </td>
				  <td align="left" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tScQuoteitems_table">	
	<c:if test="${fn:length(tScQuoteitemsList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScQuoteitemsList[0].id" type="hidden"/>
					<input name="tScQuoteitemsList[0].createName" type="hidden"/>
					<input name="tScQuoteitemsList[0].createBy" type="hidden"/>
					<input name="tScQuoteitemsList[0].createDate" type="hidden"/>
					<input name="tScQuoteitemsList[0].updateName" type="hidden"/>
					<input name="tScQuoteitemsList[0].updateBy" type="hidden"/>
					<input name="tScQuoteitemsList[0].updateDate" type="hidden"/>
					<input name="tScQuoteitemsList[0].fid" type="hidden"/>
					<input name="tScQuoteitemsList[0].indexNumber" type="hidden" value="1"/>
					<input name="tScQuoteitemsList[0].version" type="hidden"/>
					<input name="tScQuoteitemsList[0].itemID" type="hidden"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<td align="center"  bgcolor="white">
					<div style="width: 80px;background-color: white;">
						<a id="addTScQuoteitemsBtn[0]" name="addTScQuoteitemsBtn[0]" href="#"  onclick="clickAddTScQuoteitemsBtn(0);"></a>
						<a name="delTScQuoteitemsBtn[0]" id="delTScQuoteitemsBtn[0]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScQuoteitemsBtn(0);"></a>
					</div>
				</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].itemNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" datatype="*"
							   onkeypress="tScQuoteitemsListItemIdListKeyPress(0,event);"
							   onblur="tScQuoteitemsListItemIdListBlur(0);">
					  <label class="Validform_label" style="display: none;">商品</label>
					</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[0].itemName"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[0].itemModel"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[0].itemBarcode"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<%--<td align="left">--%>
					<%--<input name="tScQuoteitemsList[0].coefficient"  style="width:120px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
					<%--<label class="Validform_label" style="display: none;">换算率</label>--%>
				<%--</td>--%>
				  <td align="left" bgcolor="white">
							<%--<t:dictSelect field="unitID" type="list"--%>
										<%--typeGroupCode="SC_CITY" defaultVal="${tScQuoteitemsPage.unitID}" hasLabel="false"  title="单位" extendJson="{datatype:*}"></t:dictSelect>--%>
					  <%--<label class="Validform_label" style="display: none;">单位</label>--%>
					  <input id="tScQuoteitemsList[0].unitID" name="tScQuoteitemsList[0].unitID" style="width:80px;"/>
					  <label class="Validform_label" style="display: none;">单位</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].begQty" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" value="0">
					  <label class="Validform_label" style="display: none;">数量段(从)</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].endQty" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" value="0">
					  <label class="Validform_label" style="display: none;">数量段(至)</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].price" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" onchange="countGrossAmount(0)">
					    <label class="Validform_label" style="display: none;">单价</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].costPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="true">
					    <label class="Validform_label" style="display: none;">成本单价</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].grossAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="true">
					    <label class="Validform_label" style="display: none;">毛利</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScQuoteitemsList[0].grossper" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="true">
					    <label class="Validform_label" style="display: none;">毛利率</label>
					</td>
				  <td align="left" bgcolor="white">
						<input name="tScQuoteitemsList[0].note" maxlength="125" type="text" class="inputxt"  style="width:120px;">
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScQuoteitemsList)  > 0 }">
		<c:forEach items="${tScQuoteitemsList}" var="poVal" varStatus="stuts">
			<tr >
				<td align="center"  bgcolor="#F6FCFF">
					<input name="tScQuoteitemsList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScQuoteitemsList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScQuoteitemsList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScQuoteitemsList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScQuoteitemsList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScQuoteitemsList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScQuoteitemsList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScQuoteitemsList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
					<input name="tScQuoteitemsList[${stuts.index }].indexNumber" type="hidden" value="${poVal.indexNumber }"/>
					<input name="tScQuoteitemsList[${stuts.index }].version" type="hidden" value="${poVal.version }"/>
					<input name="tScQuoteitemsList[${stuts.index }].itemID" type="hidden" value="${poVal.itemID}"/>
					<div style="width: 25px;background-color: white;" name="xh">${stuts.index+1 }</div>
				</td>
				<c:if test="${load ne 'detail'}">
				<td align="center"  bgcolor="white">
					<div style="width: 80px;background-color: white;">
						<a id="addTScQuoteitemsBtn[${stuts.index}]" name="addTScQuoteitemsBtn[${stuts.index}]" href="#"  onclick="clickAddTScQuoteitemsBtn(${stuts.index});"></a>
							<a name="delTScQuoteitemsBtn[${stuts.index}]" id="delTScQuoteitemsBtn[${stuts.index}]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScQuoteitemsBtn(${stuts.index});"></a>
					</div>
				</td>
				</c:if>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].itemNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" datatype="*"
						   onkeypress="tScQuoteitemsListItemIdListKeyPress(${stuts.index},event);" value="${poVal.itemNumber}"
						   onblur="tScQuoteitemsListItemIdListBlur(${stuts.index});">
					<label class="Validform_label" style="display: none;">商品</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].itemName"  value="${poVal.itemName}"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].itemModel"   value="${poVal.itemModel}" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].itemBarcode"  value="${poVal.itemBarcode}"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input id="tScQuoteitemsList[${stuts.index}].unitID" name="tScQuoteitemsList[${stuts.index}].unitID" class="easyui-combobox"  data-options="valueField: 'id',width:84,textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.unitID}" style="width:80px;"/>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].begQty" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" value="${poVal.begQty}">
					<label class="Validform_label" style="display: none;">数量段(从)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].endQty" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" value="${poVal.endQty}">
					<label class="Validform_label" style="display: none;">数量段(至)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].price" maxlength="32" type="text" class="inputxt" value="${poVal.price}"  style="width:120px;" datatype="num" onchange="countGrossAmount(${stuts.index})">
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].costPrice" maxlength="32" type="text" value="${poVal.costPrice}" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].grossAmount" maxlength="32" value="${poVal.grossAmount}" type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">毛利</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].grossper" maxlength="32"   value="${poVal.grossper}" type="text" class="inputxt"  style="width:120px;background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">毛利率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScQuoteitemsList[${stuts.index}].note"  value="${poVal.note}" maxlength="125" type="text" class="inputxt"  style="width:120px;">
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
