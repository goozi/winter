<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScPrcplyentry2Btn"]').linkbutton({
		iconCls: 'icon-add',
		plain: true
	});
	$('a[name^="delTScPrcplyentry2Btn"]').linkbutton({
		iconCls: 'icon-remove',
		plain: true
	});
	function clickAddEntryBtns(index){
		var tr =  $("#add_tScPrcplyentry2_table_template tr").clone();
		$("#add_tScPrcplyentry2_table tr").eq(index).after(tr);
		rowIndex ++;
		resetTrNum('add_tScPrcplyentry2_table',index,'tScPrcplyentry2List');
	}

	function clickDelEntryBtns(index){
//		$("#add_tScPrcplyentry2_table").find(">tr").eq(index).remove();
//		var length = $("#add_tScPrcplyentry2_table").find(">tr").length;
//		if(length == 0){
//			var tr =  $("#add_tScPrcplyentry2_table_template tr").clone();
//			$("#add_tScPrcplyentry2_table").append(tr);
//			resetTrNum('add_tScPrcplyentry2_table',-1,"tScPrcplyentry2List");
//		}else {
//			resetTrNum('add_tScPrcplyentry2_table',index,"tScPrcplyentry2List");
//		}

		var len = $("#add_tScPrcplyentry2_table").find("tr").length;
		$("#add_tScPrcplyentry2_table").find("tr").eq(index).remove();
		if(index==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
			var tr = $("#add_tScPrcplyentry2_table_template tr").clone();
			$("#add_tScPrcplyentry2_table").append(tr);
			resetTrNum('add_tScPrcplyentry2_table',-1,"tScPrcplyentry2List");
		}else{
			resetTrNum('add_tScPrcplyentry2_table',index,"tScPrcplyentry2List");
		}

	}

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScPrcplyentry2_table").createhftable({
	    	height: (h-40)+'px',
			width:'auto',
			overflow:'hidden',
			fixFooter:true
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

		$("#add_tScPrcplyentry2_table tr").each(function(i,data){
			$('input[name="tScPrcplyentry2List[' + i+ '].unitID"]').combobox({
				width:'84',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
		});
		if("${fn:length(tScPrcplyentry2List) }" == 0 ) {
			var currentDate = $("#date").val();
			$('input[name="tScPrcplyentry2List[0].begDate"]').val(currentDate);
			$('input[name="tScPrcplyentry2List[0].endDate"]').val(currentDate);
		}
		if(${fn:length(tScPrcplyentry2List) > 0}){
			var  length = $("#add_tScPrcplyentry2_table").find("tr").length;

			for(var j=0 ; j<length ; j++){
				$("input[name='tScPrcplyentry2List["+j+"].begQty']").trigger("blur");
				$("input[name='tScPrcplyentry2List["+j+"].endQty']").trigger("blur");
				$("input[name='tScPrcplyentry2List["+j+"].price']").trigger("blur");
				$("input[name='tScPrcplyentry2List["+j+"].newPrice']").trigger("blur");
				$("input[name='tScPrcplyentry2List["+j+"].costPrice']").trigger("blur");
				setValOldIdAnOldName($('input[name="tScPrcplyentry2List['+j+'].itemNo"]'),$('input[name="tScPrcplyentry2List['+j+'].itemID"]').val(), $('input[name="tScPrcplyentry2List['+j+'].itemNo"]').val());
				$('input[name="tScPrcplyentry2List[' + j + '].unitID"]').combobox({
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
										$('input[name="tScPrcplyentry2List[' + rowIndex+ '].barCode"]').val(barCode);//条形码
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
<table border="0" cellpadding="2" cellspacing="1" id="tScPrcplyentry2_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="left" bgcolor="#476f9a">单位<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">数量段（从）<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">数量段（至）<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a" total="true">原价<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a" total="true">新价<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a" total="true">差价 </td>
		<td align="left" bgcolor="#476f9a">折扣率 </td>
		<td align="left" bgcolor="#476f9a">起始日期<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">结束日期<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a" total="true">成本单价</td>
		<td align="left" bgcolor="#476f9a" total="true">原毛利率 </td>
		<td align="left" bgcolor="#476f9a" total="true">新毛利率</td>
		<td align="left" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScPrcplyentry2_table">
	<input id="customerId" type="hidden" value="${sessionScope.user.id}"/>
	<c:if test="${fn:length(tScPrcplyentry2List)  <= 0 }">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScPrcplyentry2List[0].id" type="hidden"/>
					<input name="tScPrcplyentry2List[0].createName" type="hidden"/>
					<input name="tScPrcplyentry2List[0].createBy" type="hidden"/>
					<input name="tScPrcplyentry2List[0].createDate" type="hidden"/>
					<input name="tScPrcplyentry2List[0].updateName" type="hidden"/>
					<input name="tScPrcplyentry2List[0].updateBy" type="hidden"/>
					<input name="tScPrcplyentry2List[0].updateDate" type="hidden"/>
					<input name="tScPrcplyentry2List[0].indexNumber" type="hidden" value="1"/>
					<input name="tScPrcplyentry2List[0].interID" type="hidden"/>
					<input name="tScPrcplyentry2List[0].disabled" type="hidden"/>
					<input name="tScPrcplyentry2List[0].deleted" type="hidden"/>
					<input name="tScPrcplyentry2List[0].version" type="hidden"/>
					<input name="tScPrcplyentry2List[0].itemID"  type="hidden"/>
					<div style="width: 25px;" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;">
						<a name="addTScPrcplyentry2Btn[0]" id="addTScPrcplyentry2Btn[0]" href="#" onclick="clickAddEntryBtns(0);"></a>
						<a name="delTScPrcplyentry2Btn[0]" id="delTScPrcplyentry2Btn[0]" href="#" onclick="clickDelEntryBtns(0);"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].itemNo" maxlength="32" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*" onkeypress="keyDownInfo(0,'item',event)" onblur="orderListStockBlur('0','itemId','itemNo');" />
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].itemName" maxlength="50" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226)" datatype="*" readonly />
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].model" maxlength="32" type="text" class="inputxt" readonly="readonly" style="width:85px;background-color:rgb(226,226,226)" />
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].barCode" maxlength="32" type="text" class="inputxt" readonly="readonly" style="width:65px;background-color:rgb(226,226,226)"/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="tScPrcplyentry2List[0].unitID"  name="tScPrcplyentry2List[0].unitID" style="width:80px;" />
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">

					<input name="tScPrcplyentry2List[0].begQty" maxlength="32" type="text" class="inputxt" onblur="checkNum(0,'qty','begQty');"  style="width:65px;" datatype="d"  value="0" />
					<label class="Validform_label" style="display: none;">数量段（从）</label>
				</td>
				<td align="left" bgcolor="white">

					<input name="tScPrcplyentry2List[0].endQty" maxlength="32" type="text" class="inputxt"  onblur="checkNum(0,'qty','endQty');" style="width:65px;" datatype="d"  value="0"  />
					<label class="Validform_label" style="display: none;">数量段（至）</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].price" maxlength="32" type="text" class="inputxt" value="0" onblur="checkNum(0,'price','price');" style="width:70px;" datatype="numrange" onchange="changePrice(0)"/>
					<label class="Validform_label" style="display: none;">原价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].newPrice" maxlength="32" type="text" class="inputxt" onblur="checkNum(0,'price','newPrice');"  value="0" style="width:71px;" datatype="numrange" onchange="changePrice(0)"/>
					<label class="Validform_label" style="display: none;">新价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].differPrice" maxlength="32" readonly="readonly"  type="text" class="inputxt"  style="width:72px;background-color:rgb(226,226,226);" />
					<label class="Validform_label" style="display: none;">差价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].disCountRate" maxlength="32" readonly="readonly" type="text" class="inputxt"  style="width:73px;background-color:rgb(226,226,226);" />
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">

					<input id="0begDate" name="tScPrcplyentry2List[0].begDate" maxlength="32" type="text" class="Wdate"
						   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'0endDate\')}'})"
						   style="width:80px;" datatype="*" />
					<label class="Validform_label" style="display: none;">起始日期</label>
				</td>
				<td align="left" bgcolor="white">

					<input id="0endDate" name="tScPrcplyentry2List[0].endDate" maxlength="32" type="text" class="Wdate"
						   onClick="WdatePicker({minDate:'#F{$dp.$D(\'0begDate\')}'})"
						   style="width:80px;" datatype="*" />
					<label class="Validform_label" style="display: none;">结束日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].costPrice" maxlength="32" readonly type="text" readonly="readonly"  class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" />
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].grossper" maxlength="32" type="text" readonly="readonly"  class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly="readonly" />
					<label class="Validform_label" style="display: none;">原毛利率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[0].newGrossper" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:70px;background-color:rgb(226,226,226);" readonly="readonly"/>
					<label class="Validform_label" style="display: none;">新毛利率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					 <input name="tScPrcplyentry2List[0].note" maxlength="255" type="text" class="inputxt"  style="width:180px;" />
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScPrcplyentry2List)  > 0 }">
		<c:forEach items="${tScPrcplyentry2List}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].indexNumber" type="hidden" value="${poVal.indexNumber }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].interID" type="hidden" value="${poVal.interID }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].disabled" type="hidden" value="${poVal.disabled }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].deleted" type="hidden" value="${poVal.deleted }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].version" type="hidden" value="${poVal.version }"/>
					<input name="tScPrcplyentry2List[${stuts.index }].itemID" type="hidden" value="${poVal.itemID }"/>

					<div style="width: 25px;" name="xh">${stuts.index+1 }</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;">
						<a name="addTScPrcplyentry2Btn[${stuts.index }]" id="addTScPrcplyentry2Btn[${stuts.index }]" href="#" onclick="clickAddEntryBtns(${stuts.index });"></a>
						<a name="delTScPrcplyentry2Btn[${stuts.index }]" id="delTScPrcplyentry2Btn[${stuts.index }]" href="#" onclick="clickDelEntryBtns(${stuts.index });"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].itemNo" maxlength="32" type="text" class="inputxt popup-select"  style="width:105px;"  value="${poVal.itemNo }"
						   onkeypress="keyDownInfo(${stuts.index },'item',event)"
						   onblur="orderListStockBlur('${stuts.index}','itemID','itemNo');"/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].itemName" maxlength="50" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226)" datatype="*" value="${poVal.itemName }"/>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].model" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:85px;background-color:rgb(226,226,226)" value="${poVal.model }"/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].barCode" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color:rgb(226,226,226)" value="${poVal.barCode }"/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].unitID" id="tScPrcplyentry2List[${stuts.index }].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.unitID}" style="width:80px;"/>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">

					<input name="tScPrcplyentry2List[${stuts.index }].begQty" maxlength="32" type="text" onblur="checkNum(${stuts.index },'qty','begQty');" class="inputxt"  style="width:65px;" datatype="d" value="${poVal.begQty }"/>
					<label class="Validform_label" style="display: none;">数量段（从）</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].endQty" maxlength="32" type="text" onblur="checkNum(${stuts.index },'qty','endQty');" class="inputxt"  style="width:65px;" datatype="d" value="${poVal.endQty }"/>
					<label class="Validform_label" style="display: none;">数量段（至）</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].price" maxlength="32" type="text" onblur="checkNum(${stuts.index },'price','price');" class="inputxt"  style="width:70px;" datatype="numrange" value="${poVal.price }"/>
					<label class="Validform_label" style="display: none;">原价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].newPrice" maxlength="32" type="text" onblur="checkNum(${stuts.index },'price','newPrice');" class="inputxt"  style="width:71px;" datatype="numrange" onchange="changePrice(${stuts.index })" value="${poVal.newPrice }"/>
					<label class="Validform_label" style="display: none;">新价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].differPrice" maxlength="32" type="text" class="inputxt" readonly="readonly"   style="width:72px;background-color:rgb(226,226,226);" value="${poVal.differPrice }">
					<label class="Validform_label" style="display: none;">差价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].disCountRate" maxlength="32"  readonly="readonly"  class="inputxt" style="width:72px;background-color:rgb(226,226,226);" value="${poVal.disCountRate }"/>

					<label class="Validform_label" style="display: none;">折扣率</label>
				</td>
				<td align="left" bgcolor="white">

					<input id="${stuts.index }begDate" name="tScPrcplyentry2List[${stuts.index }].begDate"  maxlength="32" type="text" class="Wdate"
						   onClick="WdatePicker({maxDate:'#F{$dp.$D(\'${stuts.index }endDate\')}'})"
						   style="width:80px;" datatype="*" value="<fmt:formatDate value='${poVal.begDate}' type="date" pattern="yyyy-MM-dd"/>" />
					<label class="Validform_label" style="display: none;">起始日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="${stuts.index }endDate" name="tScPrcplyentry2List[${stuts.index }].endDate" maxlength="32" type="text" class="Wdate"
						   onClick="WdatePicker({minDate:'#F{$dp.$D(\'${stuts.index }begDate\')}'})"
						   style="width:80px;" datatype="*" value="<fmt:formatDate value='${poVal.endDate}' type="date" pattern="yyyy-MM-dd"/>" />
					<label class="Validform_label" style="display: none;">结束日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].costPrice" maxlength="32" readonly="readonly" onblur="checkNum(${stuts.index },'price','costPrice');"  type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" value="${poVal.costPrice }" />
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].grossper" maxlength="32" readonly="readonly"  type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" value="${poVal.grossper }"/>
					<label class="Validform_label" style="display: none;">原毛利率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].newGrossper"  readonly="readonly"  type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" value="${poVal.newGrossper }">

					<label class="Validform_label" style="display: none;">新毛利率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPrcplyentry2List[${stuts.index }].note" maxlength="255" type="text" class="inputxt"  style="width:180px;" value="${poVal.note }">
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
		</c:forEach>
	</c:if>
	</tbody>
</table>
