<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	/*$('a[name^="addTScPromotiongoodsentryBtn"]').linkbutton({
		iconCls: 'icon-add',
		plain: true
	});*/
	/*$('a[name^="delTScPromotiongoodsentryBtn"]').linkbutton({
		iconCls: 'icon-remove',
		plain: true
	});*/
  function clickAddTScPromotiongoodsentryBtn(rowIndex) {
    var tr = $("#add_tScPromotiongoodsentry_table_template tr").clone();
    $("#add_tScPromotiongoodsentry_table tr").eq(rowIndex).after(tr);
	  resetPromotionGoodsEntryListTrNum('add_tScPromotiongoodsentry_table',rowIndex,'tScPromotiongoodsentryList');
  }

  function clickDelTScPromotiongoodsentryBtn(rowIndex) {
	  $("#add_tScPromotiongoodsentry_table").find(">tr").eq(rowIndex).remove();
	  var length = $("#add_tScPromotiongoodsentry_table").find(">tr").length;
	  if(length == 0){
		  //clickAddEntryBtn(0)
		 // $("#itemName").removeAttr("readonly");
		  var tr =  $("#add_tScPromotiongoodsentry_table_template tr").clone();
		  $("#add_tScPromotiongoodsentry_table").append(tr);
		  resetPromotionGoodsEntryListTrNum('add_tScPromotiongoodsentry_table',-1,"tScPromotiongoodsentryList");
	  }else {
		  resetPromotionGoodsEntryListTrNum('add_tScPromotiongoodsentry_table',rowIndex,"tScPromotiongoodsentryList");
	  }
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScPromotiongoodsentry_table").createhftable({
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

		$("#add_tScPromotiongoodsentry_table tr").each(function(i,data){
			$('#tScPromotiongoodsentryList\\['+i+'\\]\\.unitID').combobox({
				width:"auto",
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
		});

		if(${fn:length(tScPromotiongoodsentryList) > 0}){
			var  length = $("#add_tScPromotiongoodsentry_table").find("tr").length;
			for(var j=0 ; j<length ; j++){
				setValOldIdAnOldName($('input[name="tScPromotiongoodsentryList['+j+'].goodsNumber"]'),$('input[name="tScPromotiongoodsentryList['+j+'].itemID"]').val(), $('input[name="tScPromotiongoodsentryList['+j+'].goodsNumber"]').val());
				$('#tScPromotiongoodsentryList\\[' + j + '\\]\\.unitID').combobox({
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
										var rowIndex = resultData.rowIndex;
										$('input[name="tScPromotiongoodsentryList[' + rowIndex+ '].goodsBarCode"]').val(barCode);//条形码
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
<table border="0" cellpadding="2" cellspacing="1"  id="tScPromotiongoodsentry_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<%--<td align="center" bgcolor="#476f9a">操作</td>--%>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="center" bgcolor="#476f9a">单位<span style="color: red">*</span> </td>
		<td align="center" bgcolor="#476f9a">数量<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScPromotiongoodsentry_table">	
	<c:if test="${fn:length(tScPromotiongoodsentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScPromotiongoodsentryList[0].id" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].createName" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].createBy" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].createDate" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].updateName" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].updateBy" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].updateDate" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].interID" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].indexNumber" value="1" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].itemID" type="hidden"/>
					<input name="tScPromotiongoodsentryList[0].version" type="hidden"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<%--<td align="center" >
          			<div style="width: 80px;background-color: rgb(226, 226, 226);">
            			<a name="addTScPromotiongoodsentryBtn[0]" id="addTScPromotiongoodsentryBtn[0]" href="#" onclick="clickAddTScPromotiongoodsentryBtn(0);" ></a>
						<a name="delTScPromotiongoodsentryBtn[0]" id="delTScPromotiongoodsentryBtn[0]" href="#" onclick="clickDelTScPromotiongoodsentryBtn(0);"></a>
          			</div>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[0].goodsNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" datatype="*"
						   onkeypress="tScPromotionGoodsEntryListItemIdListKeyPress(0,event)"
						   onblur="tScPromotionGoodsEntryListItemIdListBlur(0)"/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[0].goodsName" datatype="*" maxlength="32" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[0].goodsModel" maxlength="32" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[0].goodsBarCode" maxlength="32" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				  <td align="left" bgcolor="white">
						<input id="tScPromotiongoodsentryList[0].unitID" name="tScPromotiongoodsentryList[0].unitID" style="width:80px;" />
					  	<label class="Validform_label" style="display: none;">单位</label>
					</td>
				  <td align="left" bgcolor="white">

					  <input name="tScPromotiongoodsentryList[0].qty" maxlength="32" type="text" class="inputxt"
							 style="width:120px;"  datatype="d" ignore="ignore" onchange="checkQty(this)"/>
					  <label class="Validform_label" style="display: none;">数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  <input name="tScPromotiongoodsentryList[0].note" maxlength="255" type="text" class="inputxt"  style="width:120px;"/>
					  <label class="Validform_label" style="display: none;">备注</label>
				  </td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScPromotiongoodsentryList)  > 0 }">
		<c:forEach items="${tScPromotiongoodsentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScPromotiongoodsentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].interID" type="hidden" value="${poVal.interID }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].indexNumber" type="hidden" value="${poVal.indexNumber }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].itemID" type="hidden" value="${poVal.itemID }"/>
					<input name="tScPromotiongoodsentryList[${stuts.index }].version" type="hidden" value="${poVal.version }"/>
					<div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
				</td>
				<%--<td align="center" bgcolor="white">
				  <div style="width: 80px;background-color: white;">
					<a name="addTScPromotiongoodsentryBtn[${stuts.index}]" id="addTScPromotiongoodsentryBtn[${stuts.index}]" href="#" onclick="clickAddTScPromotiongoodsentryBtn(${stuts.index});"></a>
					<a name="delTScPromotiongoodsentryBtn[${stuts.index}]" id="delTScPromotiongoodsentryBtn[${stuts.index}]" href="#" onclick="clickDelTScPromotiongoodsentryBtn(${stuts.index});"></a>
				  </div>
        		</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[${stuts.index }].goodsNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" datatype="*" value="${poVal.goodsNumber }"
						   onkeypress="tScPromotionGoodsEntryListItemIdListKeyPress(0,event)"
						   onblur="tScPromotionGoodsEntryListItemIdListBlur(0)"/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[${stuts.index }].goodsName" datatype="*" maxlength="32" type="text" class="inputxt" style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.goodsName }"/>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[${stuts.index }].goodsModel" maxlength="32" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.goodsModel }"/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongoodsentryList[${stuts.index }].goodsBarCode" maxlength="32" type="text" class="inputxt" style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.goodsBarCode }"/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				   <td align="left" bgcolor="white">
					   <input id="tScPromotiongoodsentryList[${stuts.index}].unitID" name="tScPromotiongoodsentryList[${stuts.index}].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.unitID}" style="width:80px;"/>
					  <label class="Validform_label" style="display: none;">单位</label>
				   </td>
				   <td align="left" bgcolor="white">

					  	<input name="tScPromotiongoodsentryList[${stuts.index }].qty" da maxlength="32" type="text" class="inputxt" onchange="checkQty(this)" style="width:120px;" datatype="d" ignore="ignore"  value="${poVal.qty }"/>
					    <label class="Validform_label" style="display: none;">数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					   <input name="tScPromotiongoodsentryList[${stuts.index }].note" maxlength="255" type="text" class="inputxt"  style="width:120px;" value="${poVal.note }"/>
					   <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
