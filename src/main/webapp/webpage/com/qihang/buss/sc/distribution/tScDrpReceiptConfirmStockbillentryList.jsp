<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScDrpStockbillentry_table").createhftable({
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
		$("#add_tScDrpStockbillentry_table tr").each(function(i,data){
			$('input[name="tScDrpStockbillentryList[' + i+ '].unitID"]').combobox({
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
			$('input[name="tScDrpStockbillentryList[' + i+ '].basicUnitID"]').combobox({
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				disabled:true
			});
			$('input[name="tScDrpStockbillentryList[' + i+ '].secUnitID"]').combobox({
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				disabled:true
			});
		});
		var stockQty = $("#stockQty").val();
		$("#stockQty").on("change",function(){
			var stockQty = $("#stockQty").val();
			$(this).val();
			$("#nStockQty").val();
			$("#nStockQty").val(stockQty);
		});
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScDrpStockbillentry_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<%--<td align="center" bgcolor="#476f9a">操作</td>--%>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="center" bgcolor="#476f9a">仓库<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">批号 </td>
		<td align="center" bgcolor="#476f9a">单位<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">数量<span style="color: red">*</span></td>
		<%--<td align="center" bgcolor="#476f9a">基本单位<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">换算率<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">基本数量<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">辅助单位</td>
		<td align="center" bgcolor="#476f9a">辅助换算率</td>
		<td align="center" bgcolor="#476f9a">辅助数量</td>--%>
		<td align="center" bgcolor="#476f9a">单价</td>
		<td align="center" bgcolor="#476f9a">金额</td>
		<td align="center" bgcolor="#476f9a">折扣率(%)</td>
		<td align="center" bgcolor="#476f9a">折后单价</td>
		<td align="center" bgcolor="#476f9a">折后金额</td>
		<td align="center" bgcolor="#476f9a">重量</td>
		<td align="center" bgcolor="#476f9a">税率(%)</td>
		<%--<td align="center" bgcolor="#476f9a">不含税单价</td>
		<td align="center" bgcolor="#476f9a">不含税金额</td>
		<td align="center" bgcolor="#476f9a">不含税折后单价</td>
		<td align="center" bgcolor="#476f9a">不含税折后金额</td>--%>
		<td align="center" bgcolor="#476f9a">税额</td>
		<td align="center" bgcolor="#476f9a">生产日期</td>
		<td align="center" bgcolor="#476f9a">保质期</td>
		<td align="center" bgcolor="#476f9a">保质期类型</td>
		<td align="center" bgcolor="#476f9a">有效期至</td>
		<td align="center" bgcolor="#476f9a">成本单价</td>
		<td align="center" bgcolor="#476f9a">成本金额</td>
		<td align="center" bgcolor="#476f9a">促销类型</td>
		<td align="center" bgcolor="#476f9a">赠品标记</td>
		<td align="center" bgcolor="#476f9a">退货数量</td>
		<td align="center" bgcolor="#476f9a">确认收货数量</td>
		<td align="center" bgcolor="#476f9a">源单编号</td>
		<td align="center" bgcolor="#476f9a">源单类型</td>
		<td align="center" bgcolor="#476f9a">订单编号</td>
		<td align="center" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScDrpStockbillentry_table">
	<c:if test="${fn:length(tScDrpStockbillentryList)  > 0 }">
		<c:forEach items="${tScDrpStockbillentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScDrpStockbillentryList[${stuts.index}].id" type="hidden" value="${poVal.id}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].createName" type="hidden" value="${poVal.createName}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].createBy" type="hidden" value="${poVal.createBy}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].createDate" type="hidden"  value="${poVal.createDate}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].updateName" type="hidden" value="${poVal.updateName}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].updateBy" type="hidden" value="${poVal.updateBy}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].updateDate" type="hidden" value="${poVal.updateDate}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].fid" type="hidden"  value="${poVal.fid}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].indexNumber" id="tScDrpStockbillentryList[${stuts.index}].indexNumber" type="hidden" value="${poVal.indexNumber}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].kfDateType"  type="hidden" value="${poVal.kfDateType}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].itemID"  type="hidden"  value="${poVal.itemID}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].stockID"  type="hidden" value="${poVal.stockID}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].salesID"  type="hidden" value="${poVal.salesID}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].isFreeGift" value="2" type="hidden" value="${poVal.isFreeGift}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].weight"  type="hidden" value="${poVal.weight}" onchange="setAllWeight(0)"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].billQty" type="hidden" value="${poVal.billQty}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].fidSrc" type="hidden" value="${poVal.fidSrc}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].interIDSrc" type="hidden" value="${poVal.interIDSrc}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].fidOrder" type="hidden" value="${poVal.fidOrder}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].interIDOrder" type="hidden" value="${poVal.interIDOrder}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].classIDSrc" type="hidden" value="${poVal.classIDSrc}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].freeGifts" type="hidden" value="0"/>

					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<%--<td align="center">
					<div style="width: 80px;background-color:rgb(226,226,226);">
						<a name="addTScDrpStockbillentryBtn[${stuts.index}]" id="addTScDrpStockbillentryBtn[${stuts.index}]" href="#"></a>
					</div>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].number" value="${poVal.number}" maxlength="32" type="text" class="inputxt"  style="width:105px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].name" value="${poVal.name}" maxlength="32" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].model" value="${poVal.model}" maxlength="32" type="text" class="inputxt"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].barCode" value="${poVal.barCode}" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].stockName" value="${poVal.stockName}" id="tScDrpStockbillentryList[${stuts.index}].stockName" maxlength="32"  type="text" class="inputxt"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">仓库</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].batchNo"  value="${poVal.batchNo}" maxlength="32" type="text" class="inputxt"  style="width:80px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index }].unitID" id="tScDrpStockbillentryList[${stuts.index }].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.unitID}" style="width:80px;"/>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].qty" value="${poVal.qty}" maxlength="32" type="text" class="inputxt qty"  datatype="num" style="width:70px;"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index }].basicUnitID" id="tScDrpStockbillentryList[${stuts.index }].basicUnitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.basicUnitID}" style="width:80px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">基本单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].coefficient" value="${poVal.coefficient}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">换算率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].basicQty" value="${poVal.basicQty}"  maxlength="32" type="text" class="inputxt" datatype="num" style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">基本数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index }].secUnitID" id="tScDrpStockbillentryList[${stuts.index }].secUnitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.secUnitID}" style="width:80px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">辅助单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].secCoefficient" value="${poVal.secCoefficient}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">辅助换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].secQty" value="${poVal.secQty}" maxlength="32" type="text" class="inputxt"   style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">辅助数量</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxPriceEx" value="${poVal.taxPriceEx}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxAmountEx" value="${poVal.taxAmountEx}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].discountRate" value="${poVal.discountRate}" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" value="100" readonly/>
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxPrice" value="${poVal.taxPrice}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].inTaxAmount" value="${poVal.inTaxAmount}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].weight" value="${poVal.weight}" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">重量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxRate" value="${poVal.taxRate}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" value="0" readonly/>
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].price" value="${poVal.price}" maxlength="32" type="text" class="inputxt"   style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">不含税单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].amount" value="${poVal.amount}" maxlength="32" type="text" class="inputxt"   style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">不含税金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxPrice" value="${poVal.taxPrice}" maxlength="32" type="text" class="inputxt"   style="width:80px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">不含税折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].inTaxAmount" value="${poVal.inTaxAmount}" maxlength="32" type="text" class="inputxt"    style="width:80px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">不含税折后金额</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxAmount" value="${poVal.taxAmount}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].date" value="<fmt:formatDate value='${poVal.date}' type="date" pattern="yyyy-MM-dd"/>" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:80px;background-color:rgb(226,226,226);" onchange="setPeriodDate(0,'tScDrpStockbillentryList')" readonly/>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].kfperiod" value="${poVal.kfperiod}" maxlength="32" type="text" class="inputxt"  style="width:80px;background-color:rgb(226,226,226);" <%--onchange="setPeriodDate(0,'tScDrpStockbillentryList')"--%> readonly/>
					<label class="Validform_label" style="display: none;">保质期</label>
				</td>
				<td align="left" bgcolor="white">
					<t:dictSelect field="tScDrpStockbillentryList[${stuts.index}].kfDateType_" type="list" width="70" typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType}" hasLabel="false"  title="保质期类型"></t:dictSelect>
					<label class="Validform_label" style="display: none;">保质期类型</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].periodDate" value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">有效期至</label>
				</td>

				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].costPrice"  value="${poVal.costPrice}" maxlength="32" type="text" class="inputxt"   style="width:65px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].costAmount"  value="${poVal.costAmount}" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">成本金额</label>
				</td>

				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].salesName" value="${poVal.salesName}" maxlength="10" type="text" class="inputxt" readonly="readonly" style="width:65px;background-color:rgb(226,226,226)">
					<label class="Validform_label" style="display: none;">促销类型</label>
				</td>
				<td align="left" bgcolor="white">
					<t:dictSelect field="tScDrpStockbillentryList[${stuts.index}].freeGifts_" width="70px" type="list"
								  extendJson="{datatype:select1,onChange:setPrice(${stuts.index})}" showDefaultOption="false"
								  typeGroupCode="sf_01" defaultVal="${poVal.freeGifts}" hasLabel="false" title="赠品标记"></t:dictSelect>
					<label class="Validform_label" style="display: none;">赠品标记</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].commitQty" value="${poVal.commitQty}" maxlength="32" type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">退货数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].stockQty" value="${poVal.stockQty}"  maxlength="32" type="text" class="inputxt stockQty"  />
					<label class="Validform_label" style="display: none;">确认收货数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].billNoSrc" value="${poVal.billNoSrc}"  maxlength="50" type="text" class="inputxt"  style="width:100px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">源单编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].classIDName"  value="${poVal.billNoSrc}" maxlength="32" type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">源单类型</label>
				</td>

				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].billNoOrder" value="${poVal.billNoOrder}" maxlength="50" type="text" class="inputxt"  style="width:100px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">订单编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].note" value="${poVal.note}" maxlength="255" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>