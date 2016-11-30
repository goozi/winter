<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScDrpOrderentryBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScDrpOrderentryBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScDrpOrderentryBtn(rowIndex) {
    var tr = $("#add_tScDrpOrderentry_table_template tr").clone();
    $("#add_tScDrpOrderentry_table tr").eq(rowIndex).after(tr);
	  resetTrNum('add_tScDrpOrderentry_table',rowIndex,"tScDrpOrderentryList");
  }

  function clickDelTScDrpOrderentryBtn(rowIndex) {
    var len = $("#add_tScDrpOrderentry_table").find(">tr").length;
    $("#add_tScDrpOrderentry_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScDrpOrderentry_table_template tr").clone();
      $("#add_tScDrpOrderentry_table").append(tr);
    }
    resetTrNum('add_tScDrpOrderentry_table',rowIndex,"tScDrpOrderentryList");
  }

	function keyDownInfo(index,name,evt){
		var evt = (evt)?evt:((window.event)?window.event:"");
		var code =evt.keyCode?evt.keyCode:evt.which;
		if(code == 13){
			if(name == "item"){
				selectIcitemDialog(index);
			}else if(name == "unit"){
				selectUnitDialog(index);
			}else if(name == "custom"){
				selectCustomDialog(index);
			}else if(name == "stock"){
				selectStockDialog(index);
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
	    $("#tScDrpOrderentry_table").createhftable({
	    	height: (h-65)+'px',
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
		var stockid = $("#stockID").val();
		if (stockid) {
			var stockName = $("#stockName").val();
			$("input[name='tScDrpOrderentryList[#index#].stockID']").val(stockid);
			$("input[name='tScDrpOrderentryList[#index#].stockName']").val(stockName);
		}
		if(${fn:length(tScDrpOrderentryList)  <= 0 }){
			//设置默认税率
			var defaultTaxRate = $("#CFG_TAX_RATE").val();
			$("input[name='tScDrpOrderentryList[0].taxRate']").val(defaultTaxRate);
			$("input[name='tScDrpOrderentryList[#index#].taxRate']").val(defaultTaxRate);
			var billDate = $("#date").val();
			$("input[name='tScDrpOrderentryList[0].jhDate']").val(billDate);
			setFunctionInfo(0,"tScDrpOrderentryList");
		}else{
			//设置默认税率
			var defaultTaxRate = $("#CFG_TAX_RATE").val();
			$("input[name='tScOrderentryList[#index#].taxRate']").val(defaultTaxRate);
			var  length = $("#add_tScDrpOrderentry_table").find("tr").length;
			var pageType = '${pageType}';
			var index = 0;
			var rowLength = 0;
			if(pageType == 1){
				for(var t = 0 ; t < length ; t++) {
					setFunctionInfo(t,"tScDrpOrderentryList");
					setValOldIdAnOldName($('input[name="tScDrpOrderentryList['+t+'].goodsNo"]'),$('input[name="tScDrpOrderentryList['+t+'].itemID"]').val(), $('input[name="tScDrpOrderentryList['+t+'].goodsNo"]').val());
					setValOldIdAnOldName($('input[name="tScDrpOrderentryList['+t+'].stockName"]'),$('input[name="tScDrpOrderentryList['+t+'].stockID"]').val(), $('input[name="tScDrpOrderentryList['+t+'].stockName"]').val());
					$("input[name='tScDrpOrderentryList["+t+"].goodsNo']").attr("disabled","disabled");
					$("input[name='tScDrpOrderentryList["+t+"].goodsNo']").css("background-color","rgb(226,226,226)");
					$("input[name='tScDrpOrderentryList["+t+"].stockName']").attr("disabled","disabled");
					$("input[name='tScDrpOrderentryList["+t+"].stockName']").css("background-color","rgb(226,226,226)");
					$("#tScDrpOrderentryList\\[" + t + "\\]\\.unitID").combobox({disabled:true});
					$("input[name='tScDrpOrderentryList["+t+"].qty']").attr("oldvalue",$("input[name='tScDrpOrderentryList["+t+"].qty']").val());
					$("input[name='tScDrpOrderentryList["+t+"].taxPriceEx']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
					$("input[name='tScDrpOrderentryList["+t+"].taxAmountEx']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
					$("input[name='tScDrpOrderentryList["+t+"].discountRate']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].discountRate']").css("background-color","rgb(226,226,226)");
					$("input[name='tScDrpOrderentryList["+t+"].inTaxAmount']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
					$("input[name='tScDrpOrderentryList["+t+"].taxRate']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].taxRate']").css("background-color","rgb(226,226,226)");
					$("input[name='tScDrpOrderentryList["+t+"].jhDate']").removeAttr('onclick');
					$("input[name='tScDrpOrderentryList["+t+"].jhDate']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].note']").attr("readonly","readonly");
					$("input[name='tScDrpOrderentryList["+t+"].note']").css("background-color","rgb(226,226,226)");
				}
			}else{
				for(var j=0 ; j<length ; j++){
					//编辑行金额函数配置
					//if("detail" != "${load}") {
						setFunctionInfo(j,"tScDrpOrderentryList");
					//}
					setValOldIdAnOldName($('input[name="tScDrpOrderentryList['+j+'].goodsNo"]'),$('input[name="tScDrpOrderentryList['+j+'].itemID"]').val(), $('input[name="tScDrpOrderentryList['+j+'].goodsNo"]').val());
					setValOldIdAnOldName($('input[name="tScDrpOrderentryList['+j+'].stockName"]'),$('input[name="tScDrpOrderentryList['+j+'].stockID"]').val(), $('input[name="tScDrpOrderentryList['+j+'].stockName"]').val());
					$('#tScDrpOrderentryList\\['+j+'\\]\\.unitID').combobox({
						onChange:function(newValue,oldValue){
							if (oldValue != newValue) {
								var index = $(this)[0].id.replace(/[^0-9]/ig,"");
								$.ajax({
									type: 'POST',
									url: 'tScIcitemController.do?getChangeInfo',
									async: false,
									cache:false,
									data: {'unitId':newValue,'rowIndex':index},
									dataType: 'json',
									success:function(data){
										if(data.success == true){
											var resultData = data.attributes;
											coefficient = resultData.coefficient;
											barCode = resultData.barCode;
											xsLimitPrice = resultData.xsLimitPrice
											var rowIndex = resultData.rowIndex;
											$('input[name="tScDrpOrderentryList['+rowIndex+'].coefficient"]').val(coefficient);//换算率
											$('input[name="tScDrpOrderentryList['+rowIndex+'].coefficient"]').trigger("change");
											$('input[name="tScDrpOrderentryList['+rowIndex+'].barCode"]').val(barCode);//条形码
										}
									}
								});
							}
						}
					})
				}
			}
		}
		$("#add_tScDrpOrderentry_table tr").each(function(i,data){
			$('#tScDrpOrderentryList\\[' + i + '\\]\\.unitID').combobox({
				width:"64",
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
		});
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" totalRow="true" id="tScDrpOrderentry_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="center" bgcolor="#476f9a">仓库</td>
		<td align="center" bgcolor="#476f9a">单位<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a" total="true">数量<span style="color: red">*</span></td>
		<%--<td align="center" bgcolor="#476f9a">基本单位<span style="color: red">*</span></td>--%>
		<%--<td align="center" bgcolor="#476f9a">换算率<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">基本数量<span style="color: red">*</span> </td>--%>
		<%--<td align="center" bgcolor="#476f9a">辅助单位</td>--%>
		<%--<td align="center" bgcolor="#476f9a">辅助换算率</td>--%>
		<%--<td align="center" bgcolor="#476f9a">辅助数量</td>--%>
		<td align="center" bgcolor="#476f9a">单价</td>
		<td align="center" bgcolor="#476f9a" total="true">金额</td>
		<td align="center" bgcolor="#476f9a">折扣率(%)</td>
		<td align="center" bgcolor="#476f9a">折后单价</td>
		<td align="center" bgcolor="#476f9a" total="true">折后金额</td>
		<td align="center" bgcolor="#476f9a">税率(%)</td>
		<%--<td align="center" bgcolor="#476f9a">不含税单价</td>
		<td align="center" bgcolor="#476f9a">不含税金额</td>
		<td align="center" bgcolor="#476f9a">不含税折后单价</td>
		<td align="center" bgcolor="#476f9a">不含税折后金额</td>--%>
		<td align="center" bgcolor="#476f9a" total="true">税额</td>
		<td align="center" bgcolor="#476f9a">收货确认数量</td>
		<td align="center" bgcolor="#476f9a">收货日期</td>
		<td align="center" bgcolor="#476f9a">发货数量</td>
		<td align="center" bgcolor="#476f9a">发货日期</td>
		<%--<td align="center" bgcolor="#476f9a">收货确认时间</td>--%>
		<td align="center" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScDrpOrderentry_table">	
	<c:if test="${fn:length(tScDrpOrderentryList)  <= 0 }">
		<tr>
			<td align="center" bgcolor="#F6FCFF">
				<input name="tScDrpOrderentryList[0].id" type="hidden"/>
				<input name="tScDrpOrderentryList[0].createName" type="hidden"/>
				<input name="tScDrpOrderentryList[0].createBy" type="hidden"/>
				<input name="tScDrpOrderentryList[0].createDate" type="hidden"/>
				<input name="tScDrpOrderentryList[0].updateName" type="hidden"/>
				<input name="tScDrpOrderentryList[0].updateBy" type="hidden"/>
				<input name="tScDrpOrderentryList[0].updateDate" type="hidden"/>
				<input name="tScDrpOrderentryList[0].interID" type="hidden"/>
				<input name="tScDrpOrderentryList[0].indexNumber" type="hidden" value="1"/>
				<input name="tScDrpOrderentryList[0].affirmDate" type="hidden"/>
				<input name="tScDrpOrderentryList[0].itemID" type="hidden"/>
				<input name="tScDrpOrderentryList[0].stockID" type="hidden"/>
				<input name="tScDrpOrderentryList[0].secQty" type="hidden"/>
				<input name="tScDrpOrderentryList[0].basicUnitID" type="hidden"/>
				<input name="tScDrpOrderentryList[0].secUnitID" type="hidden"/>
				<input name="tScDrpOrderentryList[0].secCoefficient" type="hidden"/>
				<input name="tScDrpOrderentryList[0].price" type="hidden"/>
				<input name="tScDrpOrderentryList[0].amount" type="hidden"/>
				<input name="tScDrpOrderentryList[0].discountPrice" type="hidden"/>
				<input name="tScDrpOrderentryList[0].discountAmount" type="hidden"/>
				<input id="tScDrpOrderentryList[0].itemWeight" name="tScDrpOrderentryList[0].itemWeight" type="hidden"/>
				<input name="tScDrpOrderentryList[0].basicQty" type="hidden"/>
				<input name="tScDrpOrderentryList[0].coefficient" type="hidden"/>

				<div style="width: 25px;background-color: white;" name="xh">1</div>
			</td>
			<td align="center" bgcolor="white">
				<div style="width: 80px;background-color: white;">
					<a name="addTScDrpOrderentryBtn[0]" id="addTScDrpOrderentryBtn[0]" href="#" onclick="clickAddTScDrpOrderentryBtn(0);"></a>
					<a name="delTScDrpOrderentryBtn[0]" id="delTScDrpOrderentryBtn[0]" href="#" onclick="clickDelTScDrpOrderentryBtn(0);"></a>
				</div>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].goodsNo" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo(0,'item',event)" datatype="*"/>
				<label class="Validform_label" style="display: none;">商品编号</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].goodsName" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"  datatype="*"/>
				<label class="Validform_label" style="display: none;">商品名称</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].model" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"/>
				<label class="Validform_label" style="display: none;">规格</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].barCode" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"/>
				<label class="Validform_label" style="display: none;">条形码</label>
			</td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].stockName" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo(0,'stock',event)"/>
				  <label class="Validform_label" style="display: none;">仓库</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input id="tScDrpOrderentryList[0].unitID" name="tScDrpOrderentryList[0].unitID" style="width:60px;"/>
				  <label class="Validform_label" style="display: none;">单位</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].qty" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="vInt" onblur="changePrice(0)"/>
				  <label class="Validform_label" style="display: none;">数量</label>
			  </td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[0].basicUnitID" id="tScDrpOrderentryList[0].basicUnitID" style="width:60px;" />
					<label class="Validform_label" style="display: none;">基本单位</label>
				</td>--%>
			<%--<td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].coefficient" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)"/>
				  <label class="Validform_label" style="display: none;">换算率</label>
			  </td>
				<td align="left" bgcolor="white">
                    <input name="tScDrpOrderentryList[0].basicQty" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" datatype="*"/>
                    <label class="Validform_label" style="display: none;">基本数量</label>
                </td>--%>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].secUnitID" id="tScDrpOrderentryList[0].secUnitID"   style="width:60px;" />
				<label class="Validform_label" style="display: none;">辅助单位</label>
			</td>--%>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].secCoefficient" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
				<label class="Validform_label" style="display: none;">辅助换算率</label>
			</td>--%>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].secQty" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
				<label class="Validform_label" style="display: none;">辅助数量</label>
			</td>--%>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">单价</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">金额</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].discountRate" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="100"/>
				  <label class="Validform_label" style="display: none;">折扣率(%)</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].taxPrice" maxlength="32" readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)"/>
				  <label class="Validform_label" style="display: none;">折后单价</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">折后金额</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].taxRate" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="0" />
				  <label class="Validform_label" style="display: none;">税率(%)</label>
			  </td>
			  <%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[0].price" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">不含税单价</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">不含税金额</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">不含税折后单价</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">不含税折后金额</label>
			  </td>--%>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly/>
				  <label class="Validform_label" style="display: none;">税额</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].affirmQty" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
				  <label class="Validform_label" style="display: none;">收货确认数量</label>
				</td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].jhDate" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"/>
				  <label class="Validform_label" style="display: none;">收货日期</label>
			  </td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].outStockQty" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly/>
				  <label class="Validform_label" style="display: none;">发货数量</label>
				</td>
			  <td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].outDate" maxlength="32"  readonly type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;background-color:rgb(226,226,226)"/>
				  <label class="Validform_label" style="display: none;">发货日期</label>
			  </td>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpOrderentryList[0].affirmDate" maxlength="32"  readonly type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;background-color:rgb(226,226,226)" />
				<label class="Validform_label" style="display: none;">收货确认时间</label>
			</td>--%>
			<td align="left" bgcolor="white">
				  <input name="tScDrpOrderentryList[0].note" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				  <label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
	</c:if>
	<c:if test="${fn:length(tScDrpOrderentryList)  > 0 }">
		<c:forEach items="${tScDrpOrderentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScDrpOrderentryList[${stuts.index}].id" type="hidden" value="${poVal.id}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].createName" type="hidden" value="${poVal.createName}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].createBy" type="hidden" value="${poVal.createBy}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].createDate" type="hidden" value="${poVal.createDate}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].updateName" type="hidden" value="${poVal.updateName}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].updateBy" type="hidden" value="${poVal.updateBy}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].updateDate" type="hidden" value="${poVal.updateDate}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].interID" type="hidden" value="${poVal.interID}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].indexNumber" type="hidden" value="${poVal.indexNumber}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].affirmDate" type="hidden" value="${poVal.affirmDate}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].itemID" type="hidden" value="${poVal.itemID}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].stockID" type="hidden" value="${poVal.stockID}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].secQty" type="hidden" value="${poVal.secQty}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].basicUnitID" type="hidden" value="${poVal.basicUnitID}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].secUnitID" type="hidden" value="${poVal.secUnitID}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].secCoefficient" type="hidden" value="${poVal.secCoefficient}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].price" type="hidden" value="${poVal.price}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].amount" type="hidden" value="${poVal.amount}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].discountPrice" type="hidden" value="${poVal.discountPrice}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].discountAmount" type="hidden" value="${poVal.discountAmount}"/>
					<input id="tScDrpOrderentryList[${stuts.index}].itemWeight" name="tScDrpOrderentryList[${stuts.index}].itemWeight" type="hidden" value="${poVal.itemWeight}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].basicQty" type="hidden" value="${poVal.basicQty}"/>
					<input name="tScDrpOrderentryList[${stuts.index}].coefficient" type="hidden" value="${poVal.coefficient}"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;background-color: white;">
						<a name="addTScDrpOrderentryBtn[${stuts.index}]" id="addTScDrpOrderentryBtn[${stuts.index}]" href="#" onclick="clickAddTScDrpOrderentryBtn(${stuts.index});" ></a>
						<a name="delTScDrpOrderentryBtn[${stuts.index}]" id="delTScDrpOrderentryBtn[${stuts.index}]" href="#" onclick="clickDelTScDrpOrderentryBtn(${stuts.index});"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].goodsNo" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo(${stuts.index},'item',event)" datatype="*" value="${poVal.goodsNo}"/>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].goodsName" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"  datatype="*" value="${poVal.goodsName}"/>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].model" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)" value="${poVal.model}"/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].barCode" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"  value="${poVal.barCode}"/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].stockName"  value="${poVal.stockName}" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo(${stuts.index},'stock',event)"/>
					<label class="Validform_label" style="display: none;">仓库</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="tScDrpOrderentryList[${stuts.index}].unitID" name="tScDrpOrderentryList[${stuts.index}].unitID" maxlength="32"
						   type="text"
						   class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'"
						   style="width:60px;"
						   value="${poVal.unitID }">
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].qty" value="${poVal.qty}" maxlength="32" type="text" class="inputxt"
						   style="width:120px;" datatype="vInt" onblur="changePrice(${stuts.index})"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].basicUnitID" id="tScDrpOrderentryList[${stuts.index}].basicUnitID" maxlength="32"
						   type="text"
						   class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'"
						   style="width:60px;"
						   value="${poVal.basicUnitID }">
					<label class="Validform_label" style="display: none;">基本单位</label>
				</td>--%>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].coefficient" value="${poVal.coefficient}" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)"/>
					<label class="Validform_label" style="display: none;">换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].basicQty"  value="${poVal.basicQty}" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)"/>
					<label class="Validform_label" style="display: none;">基本数量</label>
				</td>--%>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].secUnitID" id="tScDrpOrderentryList[${stuts.index}].secUnitID" maxlength="32"
						   type="text"
						   class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'"
						   style="width:60px;"
						   value="${poVal.secUnitID }">
					<label class="Validform_label" style="display: none;">辅助单位</label>
				</td>--%>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].secCoefficient"  value="${poVal.secCoefficient}" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
					<label class="Validform_label" style="display: none;">辅助换算率</label>
				</td>--%>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].secQty" maxlength="32" value="${poVal.secQty}" readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
					<label class="Validform_label" style="display: none;">辅助数量</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].taxPriceEx" maxlength="32" value="${poVal.taxPriceEx}" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].taxAmountEx" maxlength="32" value="${poVal.taxAmountEx}" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].discountRate" maxlength="32"  value="${poVal.discountRate}" type="text" class="inputxt"  style="width:120px;" value="100"/>
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].taxPrice" maxlength="32" value="${poVal.taxPrice}" readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)"/>
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].inTaxAmount" value="${poVal.inTaxAmount}"  maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].taxRate" value="${poVal.taxRate}" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="0" />
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].price" value="${poVal.price}" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">不含税单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].amount" value="${poVal.amount}" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">不含税金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].discountPrice" value="${poVal.discountPrice}"  maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">不含税折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].discountAmount" value="${poVal.discountAmount}" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">不含税折后金额</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].taxAmount"  value="${poVal.taxAmount}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly/>
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].affirmQty" value="${poVal.affirmQty}"  maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
					<label class="Validform_label" style="display: none;">收货确认数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].jhDate" value="<fmt:formatDate value='${poVal.jhDate}' type="date" pattern="yyyy-MM-dd"/>" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"/>
					<label class="Validform_label" style="display: none;">收货日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].outStockQty" value="${poVal.outStockQty}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly/>
					<label class="Validform_label" style="display: none;">发货数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].outDate" value="${poVal.outDate}" maxlength="32"  readonly type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;background-color:rgb(226,226,226)"/>
					<label class="Validform_label" style="display: none;">发货日期</label>
				</td>
					<%--<td align="left" bgcolor="white">
                        <input name="tScDrpOrderentryList[${stuts.index}].affirmDate" maxlength="32"  readonly type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;background-color:rgb(226,226,226)" />
                        <label class="Validform_label" style="display: none;">收货确认时间</label>
                    </td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpOrderentryList[${stuts.index}].note" value="${poVal.note}" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
