<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScPromotiongiftentryBtn"]').linkbutton({
		iconCls: 'icon-add',
		plain: true
	});
	$('a[name^="delTScPromotiongiftentryBtn"]').linkbutton({
		iconCls: 'icon-remove',
		plain: true
	});
  function clickAddTScPromotiongiftentryBtn(rowIndex) {
    var tr = $("#add_tScPromotiongiftentry_table_template tr").clone();
    $("#add_tScPromotiongiftentry_table tr").eq(rowIndex).after(tr);
	  resetPromotionGiftsEntryListTrNum('add_tScPromotiongiftentry_table',rowIndex,'tScPromotiongiftsentryList',"add");
  }

  function clickdelTScPromotiongiftentryBtn(rowIndex) {
	  $("#add_tScPromotiongiftentry_table").find(">tr").eq(rowIndex).remove();
	  var length = $("#add_tScPromotiongiftentry_table").find(">tr").length;
	  if(length == 0){
		  var tr =  $("#add_tScPromotiongiftentry_table_template tr").clone();
		  $("#add_tScPromotiongiftentry_table").append(tr);
		  resetPromotionGiftsEntryListTrNum('add_tScPromotiongiftentry_table',-1,"tScPromotiongiftsentryList","del");
		  $("#tScPromotiongiftsentryList\\[" + 0 + "\\]\\.unitID").combobox({
			  width:"auto",
			  valueField: "id",
			  textField: "text",
			  panelHeight: "auto",
			  editable: false
		  })
	  }else {
		  resetPromotionGiftsEntryListTrNum('add_tScPromotiongiftentry_table',rowIndex,"tScPromotiongiftsentryList","del");
	  }
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScPromotiongiftentry_table").createhftable({
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

		$("#add_tScPromotiongiftentry_table tr").each(function(i,data){
			$('input[name="tScPromotiongiftsentryList[' + i+ '].unitID"]').combobox({
				width:'84',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				width:"auto",
				editable:false
			});
		});

		if(${fn:length(tScPromotiongiftsentryList) > 0}){
			var  length = $("#add_tScPromotiongiftentry_table").find("tr").length;
			for(var j=0 ; j<length ; j++){
				//setValOldIdAnOldName($('input[name="tScPromotiongiftsentryList['+j+'].giftNumber"]'),$('input[name="tScPromotiongiftsentryList['+j+'].itemID"]').val(), $('input[name="tScPromotiongiftsentryList['+j+'].giftNumber"]').val());
				//$('#tScPromotiongiftsentryList\\[' + j + '\\]\\.unitID')
				$('input[name="tScPromotiongiftsentryList[' + j + '].unitID"]').combobox({
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
										$('input[name="tScPromotiongiftsentryList[' + rowIndex+ '].giftBarCode"]').val(barCode);//条形码
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
<table border="0" cellpadding="2" cellspacing="1" id="tScPromotiongiftentry_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="center" bgcolor="#476f9a">单位<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">赠品数量<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScPromotiongiftentry_table">	
	<c:if test="${fn:length(tScPromotiongiftsentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScPromotiongiftsentryList[0].id" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].createName" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].createBy" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].createDate" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].updateName" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].updateBy" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].updateDate" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].interID" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].indexNumber" value="1" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].version" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].itemID" type="hidden"/>
					<input name="tScPromotiongiftsentryList[0].goodsItemID" type="hidden"/>
					<div style="width: 25px;" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
				  <div style="width: 80px;background-color: white;">
					<a name="addTScPromotiongiftentryBtn[0]" id="addTScPromotiongiftentryBtn[0]" href="#" onclick="clickAddTScPromotiongiftentryBtn(0);"></a>
					<a name="delTScPromotiongiftentryBtn[0]" id="delTScPromotiongiftentryBtn[0]" href="#" onclick="clickdelTScPromotiongiftentryBtn(0);"></a>
				  </div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[0].giftNumber" maxlength="36" datatype="*" type="text" class="inputxt popup-select"  style="width:120px;"
						   onkeypress="tScPromotionGiftsEntryListItemIdListKeyPress(0,event)"
						   onblur="tScPromotionGiftsEntryListItemIdListBlur(0)"/>
					<label class="Validform_label" style="display: none;">赠品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[0].giftName" datatype="*" maxlength="36" type="text" class="inputxt" style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"  />
					<label class="Validform_label" style="display: none;">赠品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[0].giftModel" maxlength="36" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" />
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[0].giftBarCode" maxlength="36" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" />
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[0].unitID" id="tScPromotiongiftsentryList[0].unitID"  style="width:80px;" />
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">

					<input name="tScPromotiongiftsentryList[0].qty" datatype="d" ignore="ignore" onchange="checkQty(this)" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="n1-25"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[0].note" maxlength="255" type="text" class="inputxt"  style="width:120px;"/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScPromotiongiftsentryList)  > 0 }">
		<c:forEach items="${tScPromotiongiftsentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].interID" type="hidden" value="${poVal.interID }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].indexNumber" type="hidden" value="${poVal.indexNumber }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].version" type="hidden" value="${poVal.version }"/>
					<input name="tScPromotiongiftsentryList[${stuts.index }].itemID" type="hidden" value="${poVal.itemID }"/>
					<div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;">
						<a name="addTScPromotiongiftentryBtn[${stuts.index}]" id="addTScPromotiongiftentryBtn[${stuts.index}]" href="#" onclick="clickAddTScPromotiongiftentryBtn(${stuts.index});"></a>
						<a name="delTScPromotiongiftentryBtn[${stuts.index}]" id="delTScPromotiongiftentryBtn[${stuts.index}]" href="#" onclick="clickdelTScPromotiongiftentryBtn(${stuts.index});"></a>
				  	</div>
        		</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].giftNumber" maxlength="36" datatype="*" type="text" class="inputxt popup-select"  style="width:120px;" value="${poVal.giftNumber }"
						   onkeypress="tScPromotionGiftsEntryListItemIdListKeyPress(${stuts.index },event)"
						   onblur="tScPromotionGiftsEntryListItemIdListBlur(${stuts.index })"/>
					<label class="Validform_label" style="display: none;">赠品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].giftName" maxlength="36" datatype="*" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.giftName }"/>
					<label class="Validform_label" style="display: none;">赠品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].giftModel" maxlength="36" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.giftModel }"/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].giftBarCode" maxlength="36" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.giftBarCode }"/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].unitID"  id="tScPromotiongiftsentryList[${stuts.index }].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.unitID}" style="width:80px;"/>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].qty" datatype="d"  onchange="checkQty(this)" ignore="ignore" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="n1-25" value="${poVal.qty }"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPromotiongiftsentryList[${stuts.index }].note" maxlength="255" type="text" class="inputxt"  style="width:120px;" value="${poVal.note }"/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
