<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScDrpStockbillentryBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});
	$('a[name^="delTScDrpStockbillentryBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScDrpStockbillentryBtn(rowIndex) {
    var tr = $("#add_tScDrpStockbillentry_table_template tr").clone();
    $("#add_tScDrpStockbillentry_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScDrpStockbillentry_table',rowIndex,'tScDrpStockbillentryList');
  }

  function clickDelTScDrpStockbillentryBtn(rowIndex) {
    var len = $("#add_tScDrpStockbillentry_table").find(">tr").length;
    $("#add_tScDrpStockbillentry_table").find(">tr").eq(rowIndex).remove();
	  if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
		  var tr = $("#add_tScDrpStockbillentry_table_template tr").clone();
		  $("#add_tScDrpStockbillentry_table").append(tr);
			resetTrNum('add_tScDrpStockbillentry_table',-1,'tScDrpStockbillentryList');
			$("#tScDrpStockbillentryList\\["+0+"\\].unitID").combobox({
				width:54,
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			});
    }
	  resetTrNum('add_tScDrpStockbillentry_table',rowIndex,'tScDrpStockbillentryList');
  }

	function keyDownInfo(index, name, evt) {
		var evt = (evt) ? evt : ((window.event) ? window.event : "");
		var code = evt.keyCode ? evt.keyCode : evt.which;
		if (code == 13) {
			if (name == "stock") {
				selectStockNameDialog(index);
			} else if (name == "item") {
				selectIcitemDialog(index);
			} else if (name == "unit") {
				selectUnitDialog(index);
			}else if (name == "batchNo") {
				selectInventoryInfo(index,'tScDrpRstockbillentryList');
			}
		}
	}
	function disableBtn(){
		$('a[name^="addTScOrderentryBtn"]').each(function(){
			$(this).linkbutton('disable');
		});
		$('a[name^="delTScOrderentryBtn"]').each(function(){
			$(this).linkbutton('disable');
		});

	}
    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
			setTimeout(disableBtn, 200);
		}
		//将表格的表头固定
	    $("#tScDrpStockbillentry_table").createhftable({
	    	height: (h-40)+'px',
			width:'auto',
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
		/*var stockid = $("#stockID").val();
		if (stockid) {
			var stockName = $("#stockName").val();
			$("input[name='tScDrpStockbillentryList[0].stockID']").val(stockid);
			$("input[name='tScDrpStockbillentryList[0].stockName']").val(stockName);
		}*/
		if(${fn:length(tScDrpStockbillentryList) <= 0}){
			var billDate = $("#date").val();
			$("input[name='tScDrpStockbillentryList[0].date']").val(billDate);
			$("select[name='tScDrpStockbillentryList[0].kfDateType']").attr("onChange","setPeriodDate(0,'tScDrpStockbillentryList')");
			//设置默认税率
			var defaultTaxRate = $("#CFG_TAX_RATE").val();
			$("input[name='tScDrpStockbillentryList[0].taxRate']").val(defaultTaxRate);
			$("input[name='tScDrpStockbillentryList[#index#].taxRate']").val(defaultTaxRate);
			setFunctionInfo(0,"tScDrpStockbillentryList");
		}else{
			//设置默认税率
			var defaultTaxRate = $("#CFG_TAX_RATE").val();
			$("input[name='tScDrpStockbillentryList[#index#].taxRate']").val(defaultTaxRate);
			var length = $("#add_tScDrpStockbillentry_table").find("tr").length;
			for(var i = 0;i<length;i++){
				setFunctionInfo(i,"tScDrpStockbillentryList");
				var billDate = $("#date").val();
				$("input[name='tScDrpStockbillentryList["+i+"].date']").val(billDate);
				$("select[name='tScDrpStockbillentryList["+i+"].kfDateType']").attr("onChange","setPeriodDate("+i+",'tScDrpStockbillentryList')");
			}
		}

		$("#add_tScDrpStockbillentry_table tr").each(function(i,data){
			$('#tScDrpStockbillentryList\\[' + i + '\\]\\.unitID').combobox({
				width:'64',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
		});
    });
</script>
<style>
	.backgroundInput{
		background-color: rgb(226, 226, 226);
	}
</style>
<table border="0" cellpadding="2" cellspacing="1" totalRow="true" id="tScDrpStockbillentry_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<c:if test="${load ne 'detail'}">
			<td align="center" bgcolor="#476f9a">操作</td>
		</c:if>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="center" bgcolor="#476f9a">仓库<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">批号 </td>
		<td align="center" bgcolor="#476f9a">单位</td>
		<td align="center" bgcolor="#476f9a" total="true">数量<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">单价</td>
		<td align="center" bgcolor="#476f9a" total="true">金额</td>
		<td align="center" bgcolor="#476f9a">折扣率(%)</td>
		<td align="center" bgcolor="#476f9a">折后单价</td>
		<td align="center" bgcolor="#476f9a" total="true">折后金额</td>
		<td align="center" bgcolor="#476f9a" total="true">重量</td>
		<td align="center" bgcolor="#476f9a">税率(%)</td>
		<td align="center" bgcolor="#476f9a" total="true">税额</td>
		<td align="center" bgcolor="#476f9a">生产日期</td>
		<td align="center" bgcolor="#476f9a">保质期</td>
		<td align="center" bgcolor="#476f9a">保质期类型</td>
		<td align="center" bgcolor="#476f9a">有效期至</td>
		<td align="center" bgcolor="#476f9a">成本单价</td>
		<td align="center" bgcolor="#476f9a" total="true">成本金额</td>
		<td align="center" bgcolor="#476f9a">促销类型</td>
		<td align="center" bgcolor="#476f9a">赠品标记</td>
		<td align="center" bgcolor="#476f9a" total="true">退货数量</td>
		<td align="center" bgcolor="#476f9a" total="true">确认收货数量</td>
		<td align="center" bgcolor="#476f9a">源单编号</td>
		<td align="center" bgcolor="#476f9a">源单类型</td>
		<td align="center" bgcolor="#476f9a">订单编号</td>
		<td align="center" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScDrpStockbillentry_table">
	<c:if test="${fn:length(tScDrpStockbillentryList)  <= 0 }">
		<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScDrpStockbillentryList[0].id" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].createName" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].createBy" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].createDate" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].updateName" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].updateBy" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].updateDate" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].fid" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].indexNumber" id="tScDrpStockbillentryList[0].indexNumber" value="1" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].kfDateType"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].itemID"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].stockID"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].salesID"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].isFreeGift" value="2" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].billQty" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].fidSrc" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].interIDSrc" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].fidOrder" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].interIDOrder" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].classIDSrc" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].freeGifts" type="hidden" value="0"/>
					<input name="tScDrpStockbillentryList[0].basicUnitId"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].secUnitId"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].secCoefficient"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].secQty"  type="hidden"/>
					<input name="tScDrpStockbillentryList[0].price" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].amount" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].discountPrice" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].discountAmount" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].basicQty" type="hidden"/>
					<input name="tScDrpStockbillentryList[0].coefficient" type="hidden"/>
					<input id="tScDrpStockbillentryList[0].weight" name="tScDrpStockbillentryList[0].weight" type="hidden"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
				  <div style="width: 80px;background-color: white;">
					<a name="addTScDrpStockbillentryBtn[0]" id="addTScDrpStockbillentryBtn[0]" href="#" onclick="clickAddTScDrpStockbillentryBtn(0);"></a>
					<a name="delTScDrpStockbillentryBtn[0]" id="delTScDrpStockbillentryBtn[0]" href="#"  onclick="clickDelTScDrpStockbillentryBtn(0);"></a>
				  </div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].number" maxlength="32" onkeypress="keyDownInfo(0,'item',event)" onblur="orderListStockBlur(0,'itemID','number');" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*"/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].name" maxlength="32" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226);" readonly datatype="*"/>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].model" maxlength="32" type="text" class="inputxt"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].barCode" maxlength="32" type="text" class="inputxt" style="width:65px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].stockName" id="tScDrpStockbillentryList[0].stockName" datatype="*" maxlength="32" onkeypress="keyDownInfo(0,'stock',event)" onblur="orderListStockBlur(0,'stockID','stockName');" type="text" class="inputxt popup-select"  style="width:85px;" />
					<label class="Validform_label" style="display: none;">仓库</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].batchNo" maxlength="32" type="text" class="inputxt"  style="width:80px;" />
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				  <td align="left" bgcolor="white">
					  <input id="tScDrpStockbillentryList[0].unitID" name="tScDrpStockbillentryList[0].unitID"   style="width:60px;"/>
					  <label class="Validform_label" style="display: none;">单位</label>
				  </td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].qty" maxlength="32" type="text" class="inputxt"  datatype="num" style="width:70px;"  onblur="changePrice(0)"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" />
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:70px;">
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].discountRate" maxlength="32" type="text" class="inputxt"  style="width:65px;" value="100" />
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].taxPrice" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px;"/>
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].itemWeight" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">重量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].taxRate" maxlength="32" type="text" class="inputxt"  style="width:70px;" value="0" />
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].date" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:80px;" onchange="setPeriodDate(0,'tScDrpStockbillentryList')"/>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].kfperiod" maxlength="32" type="text" class="inputxt"  style="width:80px;" <%--onchange="setPeriodDate(0,'tScDrpStockbillentryList')"--%>/>
					<label class="Validform_label" style="display: none;">保质期</label>
				</td>
				<td align="left" bgcolor="white">
					<t:dictSelect field="tScDrpStockbillentryList[0].kfDateType_" type="list" width="70" typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="0" hasLabel="false"  title="保质期类型"></t:dictSelect>
					<label class="Validform_label" style="display: none;">保质期类型</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].periodDate" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">有效期至</label>
				</td>

				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].costPrice" maxlength="32" type="text" class="inputxt"   style="width:65px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].costAmount" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">成本金额</label>
				</td>

				  <td align="left" bgcolor="white">
					  <input name="tScDrpStockbillentryList[0].salesName" maxlength="10" type="text" class="inputxt" readonly="readonly" style="width:65px;background-color:rgb(226,226,226)">
					  <label class="Validform_label" style="display: none;">促销类型</label>
				  </td>
				  <td align="left" bgcolor="white">
					  <t:dictSelect field="tScDrpStockbillentryList[0].freeGifts_" width="70px" type="list"
									extendJson="{datatype:select1,onChange:setPrice(this)}" showDefaultOption="false"
									typeGroupCode="sf_01" defaultVal="0" hasLabel="false" title="赠品标记"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">赠品标记</label>
				  </td>
				  <td align="left" bgcolor="white">
					  <input name="tScDrpStockbillentryList[0].commitQty" maxlength="32" type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226);" readonly/>
					  <label class="Validform_label" style="display: none;">退货数量</label>
				  </td>
				  <td align="left" bgcolor="white">
					  <input name="tScDrpStockbillentryList[0].stockQty" maxlength="32" type="text" class="inputxt"  style="width:80px;background-color:rgb(226,226,226);" readonly/>
					  <label class="Validform_label" style="display: none;">确认收货数量</label>
				  </td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[0].billNoSrc"  maxlength="50" type="text" class="inputxt"  style="width:100px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">源单编号</label>
				</td>
				  <td align="left" bgcolor="white">
					  <input name="tScDrpStockbillentryList[0].classIDName"  maxlength="32" type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226);" readonly/>
					  <label class="Validform_label" style="display: none;">源单类型</label>
				  </td>

				  <td align="left" bgcolor="white">
					  <input name="tScDrpStockbillentryList[0].billNoOrder" maxlength="50" type="text" class="inputxt"  style="width:100px;background-color:rgb(226,226,226);" readonly/>
					  <label class="Validform_label" style="display: none;">订单编号</label>
				  </td>
				  <td align="left" bgcolor="white">
					  <input name="tScDrpStockbillentryList[0].note" maxlength="255" type="text" class="inputxt"  style="width:180px;" />
					  <label class="Validform_label" style="display: none;">备注</label>
				  </td>
   			</tr>
	</c:if>
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
					<input name="tScDrpStockbillentryList[${stuts.index}].billQty" type="hidden" value="${poVal.billQty}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].fidSrc" type="hidden" value="${poVal.fidSrc}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].interIDSrc" type="hidden" value="${poVal.interIDSrc}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].fidOrder" type="hidden" value="${poVal.fidOrder}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].interIDOrder" type="hidden" value="${poVal.interIDOrder}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].classIDSrc" type="hidden" value="${poVal.classIDSrc}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].freeGifts" type="hidden" value="0"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].basicUnitId"  type="hidden" value="${poVal.basicUnitID}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].secUnitId"  type="hidden" value="${poVal.secUnitID}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].secCoefficient"  type="hidden" value="${poVal.secCoefficient}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].secQty"  type="hidden" value="${poVal.secQty}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].price" type="hidden" value="${poVal.price}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].amount" type="hidden" value="${poVal.amount}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].discountPrice" type="hidden" value="${poVal.discountPrice}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].discountAmount" type="hidden" value="${poVal.discountAmount}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].basicQty" type="hidden" value="${poVal.basicQty}"/>
					<input name="tScDrpStockbillentryList[${stuts.index}].coefficient" type="hidden" value="${poVal.coefficient}"/>
					<input id="tScDrpStockbillentryList[${stuts.index}].weight" name="tScDrpStockbillentryList[${stuts.index}].weight" type="hidden" value="${poVal.weight}"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;background-color: white;">
						<a name="addTScDrpStockbillentryBtn[${stuts.index}]" id="addTScDrpStockbillentryBtn[${stuts.index}]" href="#" onclick="clickAddTScDrpStockbillentryBtn(${stuts.index});" ></a>
						<a name="delTScDrpStockbillentryBtn[${stuts.index}]" id="delTScDrpStockbillentryBtn[${stuts.index}]" href="#"  onclick="clickDelTScDrpStockbillentryBtn(${stuts.index});"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].number" value="${poVal.number}" maxlength="32" onkeypress="keyDownInfo(${stuts.index},'item',event)" onblur="orderListStockBlur(${stuts.index},'itemID','number');" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*"/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].name" value="${poVal.name}" maxlength="32" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226);" readonly datatype="*"/>
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
					<input name="tScDrpStockbillentryList[${stuts.index}].stockName" value="${poVal.stockName}" id="tScDrpStockbillentryList[${stuts.index}].stockName" maxlength="32" onkeypress="keyDownInfo(${stuts.index},'stock',event)" onblur="orderListStockBlur(0,'stockID','stockName');" type="text" class="inputxt popup-select"  style="width:85px;" datatype="*"/>
					<label class="Validform_label" style="display: none;">仓库</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].batchNo"  value="${poVal.batchNo}" maxlength="32" type="text" class="inputxt"  style="width:80px;" />
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="tScDrpStockbillentryList[${stuts.index}].unitID" name="tScDrpStockbillentryList[${stuts.index}].unitID" maxlength="32"
						   type="text"
						   class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'"
						   style="width:60px;"
						   value="${poVal.unitID }">
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].qty" value="${poVal.qty}" maxlength="32" type="text" class="inputxt"  datatype="num" style="width:70px;" onblur="changePrice(${stuts.index})"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxPriceEx" value="${poVal.taxPriceEx}" maxlength="32" type="text" class="inputxt"  style="width:70px;" />
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxAmountEx" value="${poVal.taxAmountEx}" maxlength="32" type="text" class="inputxt"  style="width:70px;" />
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].discountRate" value="${poVal.discountRate}" maxlength="32" type="text" class="inputxt"  style="width:65px;" value="100" />
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxPrice" value="${poVal.taxPrice}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].inTaxAmount" value="${poVal.inTaxAmount}" maxlength="32" type="text" class="inputxt"  style="width:70px;" onchange="setAllAmount()"/>
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].itemWeight" value="${poVal.itemWeight}" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly />
					<label class="Validform_label" style="display: none;">重量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxRate" value="${poVal.taxRate}" maxlength="32" type="text" class="inputxt"  style="width:70px;" value="0" />
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].taxAmount" value="${poVal.taxAmount}" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].date" value="<fmt:formatDate value='${poVal.date}' type="date" pattern="yyyy-MM-dd"/>" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:80px;" onchange="setPeriodDate(0,'tScDrpStockbillentryList')"/>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].kfperiod" value="${poVal.kfperiod}" maxlength="32" type="text" class="inputxt"  style="width:80px;" <%--onchange="setPeriodDate(0,'tScDrpStockbillentryList')"--%>/>
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
								  extendJson="{datatype:select1,onChange:setPrice(this)}" showDefaultOption="false"
								  typeGroupCode="sf_01" defaultVal="${poVal.freeGifts}" hasLabel="false" title="赠品标记"></t:dictSelect>
					<label class="Validform_label" style="display: none;">赠品标记</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].commitQty" value="${poVal.commitQty}" maxlength="32" type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">退货数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpStockbillentryList[${stuts.index}].stockQty" value="${poVal.stockQty}" maxlength="32" type="text" class="inputxt"  style="width:80px;background-color:rgb(226,226,226);" readonly/>
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
					<input name="tScDrpStockbillentryList[${stuts.index}].note" value="${poVal.note}" maxlength="255" type="text" class="inputxt"  style="width:180px;" />
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
