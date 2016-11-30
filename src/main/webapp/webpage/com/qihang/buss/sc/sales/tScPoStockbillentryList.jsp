<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">

	function clickAddTScPoStockbillentryBtn(index){
		var tr =  $("#add_tScPoStockbillentry_table_template tr").clone();
		$("#add_tScPoStockbillentry_table tr").eq(index).after(tr);
		rowIndex ++;
		resetTrNum('add_tScPoStockbillentry_table',index,1);
	}
	function clickDelTScOrderentryBtn(index){
//		var stockQty = $("input[name='tScPoStockbillentryList["+index+"].stockQty']").val();
//		if(stockQty > 0){
//			tip("该商品已被执行，不可删除");
//			return;
//		}
		$("#add_tScPoStockbillentry_table").find(">tr").eq(index).remove();
		var length = $("#add_tScPoStockbillentry_table").find(">tr").length;
		if(length == 0){
			//clickAddTScPoStockbillentryBtn(0)
			$("#itemName").removeAttr("disabled");
			var tr =  $("#add_tScPoStockbillentry_table_template tr").clone();
			$("#add_tScPoStockbillentry_table").append(tr);
			resetTrNum('add_tScPoStockbillentry_table',-1);
			$("#unitId\\[" + 0 + "\\]").combobox({
				width:54,
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			})
		}else {
			resetTrNum('add_tScPoStockbillentry_table',index);
		}
		rowIndex--;
	}

	function keyDownInfo(index,name,evt){
		var evt = (evt)?evt:((window.event)?window.event:"");
		var code =evt.keyCode?evt.keyCode:evt.which;
		if(code == 13){
			if(name=="stock") {
				selectStockDialog(index);
			}else if(name == "item"){
				selectIcitemDialog(index);
			}else if(name == "batchNo"){
				selectInventoryInfo(index);
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
	    $("#tScPoStockbillentry_table").createhftable({
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
		var stockId = $("#stockId").val();
		if(stockId){
			var stockName = $("#stockName").val();
			$("input[name='tScPoStockbillentryList[#index#].stockId']").val(stockId);
			$("input[name='tScPoStockbillentryList[#index#].stockName']").val(stockName);
		}
		if(${fn:length(tScPoStockbillentryList)  <= 0 }){

			checkAllowAddLine(0);//判断是否允许增行操作
			var billDate = $("#date").val();
			var taxRate = $("#defaultTaxRate").val();
			$("input[name='tScPoStockbillentryList[0].taxRate']").val(taxRate);
			$("input[name='tScPoStockbillentryList[0].kfDate']").val(billDate);
			$("input[name='tScPoStockbillentryList[0].stockName']").keypress(function (e) {
				if (e.keyCode == 13) {
					selectStockDialog(0);
				}
			});
			setFunctionInfo(0);
//			$("input[name='tScPoStockbillentryList[0].unitName']").keypress(function(e){
//				if(e.keyCode == 13){
//					selectUnitDialog(0);
//				}
//			})
		}else{
			var checkState = $("#checkState").val();
			var $tbody = $("#tScPoStockbillentry_table");
			var length = $tbody[0].rows.length;
			rowIndex = length-1;
			//变更功能配置
//			if(checkState == 2){
//				for(var i=0 ; i<length ; i++){
//					setFunctionInfo(i);
//					$("input[name='tScPoStockbillentryList["+i+"].stockName']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].stockName']").attr("oldid",$("input[name='tScPoStockbillentryList["+i+"].stockId']").val());
//					$("input[name='tScPoStockbillentryList["+i+"].stockName']").attr("oldName",$("input[name='tScPoStockbillentryList["+i+"].stockName']").val());
//					$("input[name='tScPoStockbillentryList["+i+"].itemNo']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].itemNo']").attr("oldid",$("input[name='tScPoStockbillentryList["+i+"].itemId']").val());
//					$("input[name='tScPoStockbillentryList["+i+"].itemNo']").attr("oldName",$("input[name='tScPoStockbillentryList["+i+"].itemNo']").val());
//					$("input[name='tScPoStockbillentryList["+i+"].unitId']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].discountRate']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].taxRate']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].taxPriceEx']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].taxAmountEx']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].taxPrice']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].inTaxAmount']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].jhDate']").attr("readonly","readonly");
//					$("input[name='tScPoStockbillentryList["+i+"].jhDate']").attr("onclick","");
//					$("input[name='tScPoStockbillentryList["+i+"].note']").attr("readonly","readonly");
//					$("select[name='tScPoStockbillentryList["+i+"].freeGifts']").attr("disabled","disabled");
//					$("input[name='tScPoStockbillentryList["+i+"].qty']").attr("oldvalue",$("input[name='tScPoStockbillentryList["+i+"].qty']").val());
//				}
//			}else{
				for(var j=0 ; j<length ; j++){
					//编辑行金额函数配置
					if("detail" != "${load}") {
						setFunctionInfo(j);
					}
					checkAllowAddLine(j);//判断是否允许增行操作
					//var unitId = $("#unitId\\["+j+"\\]").val();
					var itemId = $("input[name='tScPoStockbillentryList["+j+"].itemId']").val();
					var itemNo = $("input[name='tScPoStockbillentryList["+j+"].itemNo']").val();
					setValOldIdAnOldName($("input[name='tScPoStockbillentryList["+j+"].itemNo']"),itemId,itemNo);
					var stockId = $("input[name='tScPoStockbillentryList["+j+"].stockId']").val();
					if(stockId){
						var stockName = $("input[name='tScPoStockbillentryList["+j+"].stockName']").val();
						setValOldIdAnOldName($("input[name='tScPoStockbillentryList["+j+"].stockName']"),stockId,stockName);
					}
					var entryOrderId = $("input[name='tScPoStockbillentryList["+j+"].entryIdOrder']").val();
					if(entryOrderId){
						if("fcopy" != "${load}") {
							$("select[name='tScPoStockbillentryList[" + j + "].freeGifts_']").attr("disabled", "disabled");
							$("select[name='tScPoStockbillentryList[" + j + "].freeGifts_']").css("background-color", "rgb(226,226,226)");
						}
					}
					var batchManager = $("input[name='tScPoStockbillentryList["+j+"].batchManager']").val();
					if("Y"==batchManager){
						var tranType = $("#tranType").val();
						if("53" == tranType){
							$("input[name='tScPoStockbillentryList[" + j + "].batchNo']").attr("readonly","readonly");
							$("input[name='tScPoStockbillentryList[" + j + "].batchNo']").css("background-color","rgb(226,226,226)");
						} else {
							$("input[name='tScPoStockbillentryList[" + j + "].batchNo']").attr("onkeypress","keyDownInfo('"+j+"','batchNo')");
						}
					}else{
						$("input[name='tScPoStockbillentryList[" + j + "].batchNo']").attr("readonly","readonly");
						$("input[name='tScPoStockbillentryList[" + j + "].batchNo']").css("background-color","rgb(226,226,226)");
						$("input[name='tScPoStockbillentryList[" + j + "].batchNo']").removeAttr("datatype");
					}
					var isKfPeriod = $("input[name='tScPoStockbillentryList["+j+"].isKFPeriod']").val();
					if("Y" != isKfPeriod) {
						//生产日期
						$("input[name='tScPoStockbillentryList[" + j + "].kfDate']").attr("readonly","readonly");
						$("input[name='tScPoStockbillentryList[" + j + "].kfDate']").css("background-color","rgb(226,226,226)");
						$("input[name='tScPoStockbillentryList[" + j + "].kfDate']").removeAttr("onclick");
						$("input[name='tScPoStockbillentryList[" + j + "].kfDate']").val("");
						//保质期
						$("input[name='tScPoStockbillentryList[" + j + "].kfPeriod']").attr("readonly","readonly");
						$("input[name='tScPoStockbillentryList[" + j + "].kfPeriod']").css("background-color","rgb(226,226,226)");
						//保质期类型
						$("select[name='tScPoStockbillentryList[" + j + "].kfDateType']").attr("disabled", "disabled");
						$("select[name='tScPoStockbillentryList[" + j + "].kfDateType']").css("background-color", "rgb(226,226,226)");
					}
					$("#unitId\\["+j+"\\]").combobox({
						onChange:function(newV,oldV){
							if(oldV != newV) {
								var index = $(this)[0].id.replace(/[^0-9]/ig,"");
								var changeUrl = "tScIcitemController.do?getChangeInfo&id=" + itemId + "&unitId=" + newV+"&rowIndex="+index;
								$.ajax({
									url: changeUrl,
									dataType: 'json',
									cache: false,
									success: function (data) {
										var attributes = data.attributes;
										var cofficient = attributes.coefficient;
										var barCode = attributes.barCode;
										var rowIndex = attributes.rowIndex;
										var cgLimitPrice = attributes.cgLimitPrice;
										$("input[name='tScPoStockbillentryList["+rowIndex+"].barCode']").val(barCode);
										$("input[name='tScPoStockbillentryList["+rowIndex+"].coefficient']").val(cofficient);
										$("input[name='tScPoStockbillentryList["+rowIndex+"].coefficient']").trigger("propertychange");
										$("input[name='tScPoStockbillentryList["+rowIndex+"].secCoefficient']").trigger("propertychange");
										$("input[name='tScPoStockbillentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);
									}
								});
							}
						}
					})
					var idSrc = $("input[name='tScPoStockbillentryList["+j+"].idSrc']").val();
					if(idSrc){
						if("fcopy" != "${load}") {
							$("input[name='tScPoStockbillentryList[" + j + "].itemNo']").attr("disabled", "disabled");
							//$("input[name='tScPoStockbillentryList["+j+"].stockName']").attr("disabled","disabled");
							$("#unitId\\[" + j + "\\]").combobox({
								disabled: true
							});
							$("#itemName").attr("disabled", "disabled");
						}
					}
					var freeGifts = $("input[name='tScPoStockbillentryList["+j+"].freeGifts']").val();
					if(freeGifts == 1){
						setPrice(j);
					}
				}
		//	}
		}
    });
</script>
<%--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">--%>
	<%--<a id="addTScPoStockbillentryBtn" href="#">添加</a> <a id="delTScPoStockbillentryBtn" href="#">删除</a> --%>
<%--</div>--%>
<table border="0" cellpadding="2" cellspacing="1" id="tScPoStockbillentry_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
<c:if test="${load ne 'detail'}">
		<td align="center" bgcolor="#476f9a">操作</td>
	</c:if>
				  <td align="center" bgcolor="#476f9a">
						商品编号<span style="color: red">*</span>
				  </td>
		          <td align="center" bgcolor="#476f9a">
				 	    商品名称<span style="color: red">*</span>
				  </td>
				<td align="center" bgcolor="#476f9a">
					规格
				</td>
				<td align="center" bgcolor="#476f9a">
					条形码
				</td>
				  <td align="center" bgcolor="#476f9a">
						仓库<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
						批号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						单位<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						数量<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
						单价
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						折扣率（%）
				  </td>
				  <td align="center" bgcolor="#476f9a">
						折后单价
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						折后金额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						税率（%）
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						税额
				  </td>
				  <td align="center" bgcolor="#476f9a">
						生产日期
				  </td>
				  <td align="center" bgcolor="#476f9a">
						保质期
				  </td>
				  <td align="center" bgcolor="#476f9a">
						保质期类型
				  </td>
				  <td align="center" bgcolor="#476f9a">
						有效期至
				  </td>
				  <%--<td align="center" bgcolor="#476f9a">--%>
						<%--成本单价--%>
				  <%--</td>--%>
				  <%--<td align="center" bgcolor="#476f9a">--%>
						<%--成本金额--%>
				  <%--</td>--%>
				  <td align="center" bgcolor="#476f9a">
						赠品标记
				  </td>
		          <c:if test="${tranType eq '52'}">
				  <td align="center" bgcolor="#476f9a">
						退货数量
				  </td>
				  </c:if>
				  <td align="center" bgcolor="#476f9a">
						源单类型
				  </td>
				  <td align="center" bgcolor="#476f9a">
						源单编号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						订单编号
				  </td>
				  <td align="center" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tScPoStockbillentry_table">	
	<c:if test="${fn:length(tScPoStockbillentryList)  <= 0 }">
			<tr id="tr0">
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center" bgcolor="white"><div style="width: 80px;"><a name="addTScOrderentryBtn[0]" id="addTScOrderentryBtn[0]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScPoStockbillentryBtn(0);"></a><a name="delTScOrderentryBtn[0]" id="delTScOrderentryBtn[0]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn(0);"></a></div></td>

					<input name="tScPoStockbillentryList[0].basicUnitId" type="hidden"/>
					<input name="tScPoStockbillentryList[0].basicCoefficient" type="hidden"/>
					<input name="tScPoStockbillentryList[0].coefficient" type="hidden"/>
					<input name="tScPoStockbillentryList[0].basicQty" type="hidden"/>
					<input name="tScPoStockbillentryList[0].secUnitId" type="hidden"/>
					<input name="tScPoStockbillentryList[0].secCoefficient" type="hidden"/>
					<input name="tScPoStockbillentryList[0].secQty" type="hidden"/>
					<input name="tScPoStockbillentryList[0].price" type="hidden"/>
					<input name="tScPoStockbillentryList[0].amount" type="hidden"/>
					<input name="tScPoStockbillentryList[0].discountPrice" type="hidden"/>
					<input name="tScPoStockbillentryList[0].discountAmount" type="hidden"/>
					<input name="tScPoStockbillentryList[0].idSrc" type="hidden"/>
					<input name="tScPoStockbillentryList[0].entryIdSrc" type="hidden"/>
					<input name="tScPoStockbillentryList[0].idOrder" type="hidden"/>
					<input name="tScPoStockbillentryList[0].entryIdOrder" type="hidden"/>
					<input name="tScPoStockbillentryList[0].findex" value="1" type="hidden"/>
					<input name="tScPoStockbillentryList[0].stockId" type="hidden"/>
					<input name="tScPoStockbillentryList[0].itemId" type="hidden"/>
					<input name="tScPoStockbillentryList[0].costPrice" type="hidden"/>
					<input name="tScPoStockbillentryList[0].costAmount" type="hidden"/>
					<input name="tScPoStockbillentryList[0].cgLimitPrice" type="hidden"/>
					<input name="tScPoStockbillentryList[0].billQty" type="hidden"/>
					<input name="tScPoStockbillentryList[0].stockQty" type="hidden"/>
					<input name="tScPoStockbillentryList[0].freeGifts" value="0" type="hidden"/>
					<input name="tScPoStockbillentryList[0].commitQty" value="0.0" type="hidden"/>
					<input name="tScPoStockbillentryList[0].classIDSrc" type="hidden"/>
					<input name="tScPoStockbillentryList[0].kfDateType" type="hidden"/>
					<input name="tScPoStockbillentryList[0].isKFPeriod" type="hidden"/>
					<input name="tScPoStockbillentryList[0].batchManager" type="hidden"/>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].itemNo" maxlength="32"
					  		type="text" class="inputxt popup-select"  style="width:105px;"
					               datatype="*" onkeypress="keyDownInfo(0,'item',event)" onblur="orderListStockBlur('0','itemId','itemNo');"
>
					  <label class="Validform_label" style="display: none;">商品编号</label>
					</td>
				<td align="left" bgcolor="white">
					<input name="tScPoStockbillentryList[0].itemName" readonly="readonly" maxlength="32"
						   type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226)"
						   datatype="*"
							>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPoStockbillentryList[0].model" readonly="readonly" maxlength="32"
						   type="text" class="inputxt"  style="width:85px;background-color:rgb(226,226,226)"
							>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPoStockbillentryList[0].barCode" readonly="readonly" maxlength="32"
						   type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226)"
							>
					<label class="Validform_label" style="display: none;">条码</label>
				</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].stockName" maxlength="32"
					  		type="text" class="inputxt popup-select"  style="width:65px;" datatype="*" onblur="orderListStockBlur('0','stockId','stockName');"
>
					  <label class="Validform_label" style="display: none;">仓库</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].batchNo" maxlength="50" 
					  		type="text" class="inputxt"  style="width:80px;" datatype="*"
					               
					               >
					  <label class="Validform_label" style="display: none;">批号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input id="unitId[0]" name="tScPoStockbillentryList[0].unitId" maxlength="32"
					  		type="text" class="easyui-combobox"
							   data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false" style="width:50px;"
>
					  <label class="Validform_label" style="display: none;">单位</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].qty" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;"
					               datatype="vInt"
>
					  <label class="Validform_label" style="display: none;">数量</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].taxPriceEx" maxlength="20"
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
					               
					               >
					  <label class="Validform_label" style="display: none;">单价</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].taxAmountEx" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
					               
					               >
					  <label class="Validform_label" style="display: none;">金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].discountRate" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
							       value="100"
					               >
					  <label class="Validform_label" style="display: none;">折扣率（%）</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].taxPrice" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					               >
					  <label class="Validform_label" style="display: none;">折后单价</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].inTaxAmount" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
							   onchange="setAllAmount()"
					               >
					  <label class="Validform_label" style="display: none;">折后金额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].taxRate" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
					               
					               >
					  <label class="Validform_label" style="display: none;">税率（%）</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].taxAmount" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)"
							   readonly="readonly"
					               >
					  <label class="Validform_label" style="display: none;">税额</label>
					</td>
				  <td align="left" bgcolor="white">
							<input name="tScPoStockbillentryList[0].kfDate" maxlength="20" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:80px;"
					                onchange="setPeriodDate(0)"
					               >  
					  <label class="Validform_label" style="display: none;">生产日期</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].kfPeriod" maxlength="10" 
					  		type="text" class="inputxt"  style="width:50px;background-color: rgb(226,226,226)" readonly="readonly"
							   onchange="setPeriodDate(0)"
					               >
					  <label class="Validform_label" style="display: none;">保质期</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<%--<input name="tScPoStockbillentryList[0].kfDateType" maxlength="10" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
							   <%--dictionary="SC_DURA_DATE_TYPE"--%>
					               <%-->--%>

					  <t:dictSelect field="tScPoStockbillentryList[0].kfDateType_" width="70px" type="list" showDefaultOption="true" extendJson="{onChange:setPeriodDate(0),disabled:disabled}"
									typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"  title="保质期类型"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">保质期类型</label>
					</td>
				  <td align="left" bgcolor="white">
							<input name="tScPoStockbillentryList[0].periodDate" maxlength="20" 
					  		type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)"
					                readonly="readonly"
					               >
					  <label class="Validform_label" style="display: none;">有效期至</label>
					</td>
				  <%--<td align="left">--%>
					  	<%--<input name="tScPoStockbillentryList[0].costPrice" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly="readonly"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">成本单价</label>--%>
					<%--</td>--%>
				  <%--<td align="left">--%>
					  	<%--<input name="tScPoStockbillentryList[0].costAmount" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly="readonly"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">成本金额</label>--%>
					<%--</td>--%>
				  <td align="left" bgcolor="white">
					  <span id="id_select[0]">
							<t:dictSelect field="tScPoStockbillentryList[0].freeGifts_" width="70px" type="list" showDefaultOption="false"
										  extendJson="{onChange:setPrice(${0})}"
										typeGroupCode="sf_01" defaultVal="0" hasLabel="false"  title="赠品标记"></t:dictSelect>
						  </span>
					  <label class="Validform_label" style="display: none;">赠品标记</label>
					</td>
		<c:if test="${tranType eq '52'}">
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].commitQty" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					               >
					  <label class="Validform_label" style="display: none;">退货数量</label>
					</td>
			</c:if>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].classIDName" maxlength="10"
					  		type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					               >
					  <label class="Validform_label" style="display: none;">源单类型</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].billNoSrc" maxlength="50" 
					  		type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					               >
					  <label class="Validform_label" style="display: none;">源单编号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].billNoOrder" maxlength="50" 
					  		type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					               >
					  <label class="Validform_label" style="display: none;">订单编号</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[0].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:180px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScPoStockbillentryList)  > 0 }">
		<c:forEach items="${tScPoStockbillentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<c:if test="${load ne 'detail'}">
				<td align="center" bgcolor="white"><div style="width: 80px;margin-top: -5px"><a name="addTScOrderentryBtn[${stuts.index }]" id="addTScOrderentryBtn[${stuts.index }]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScPoStockbillentryBtn(${stuts.index });"></a><a name="delTScOrderentryBtn[${stuts.index }]" id="delTScOrderentryBtn[${stuts.index }]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn(${stuts.index });"></a></div></td>
				</c:if>
				<input name="tScPoStockbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].basicUnitId" type="hidden" value="${poVal.basicUnitId }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].coefficient" type="hidden" value="${poVal.coefficient }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].basicQty" type="hidden" value="${poVal.basicQty }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].secUnitId" type="hidden" value="${poVal.secUnitId }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].secCoefficient" type="hidden" value="${poVal.secCoefficient }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].secQty" type="hidden" value="${poVal.secQty }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].price" type="hidden" value="${poVal.price }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].amount" type="hidden" value="${poVal.amount }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].discountPrice" type="hidden" value="${poVal.discountPrice }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].discountAmount" type="hidden" value="${poVal.discountAmount }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].idSrc" type="hidden" <c:if test="${load ne 'fcopy'}"> value="${poVal.idSrc }" </c:if> />
					<input name="tScPoStockbillentryList[${stuts.index }].entryIdSrc" type="hidden" <c:if test="${load ne 'fcopy'}"> value="${poVal.entryIdSrc }" </c:if> />
					<input name="tScPoStockbillentryList[${stuts.index }].idOrder" type="hidden" <c:if test="${load ne 'fcopy'}"> value="${poVal.idOrder }" </c:if> />
					<input name="tScPoStockbillentryList[${stuts.index }].entryIdOrder" type="hidden" <c:if test="${load ne 'fcopy'}"> value="${poVal.entryIdOrder }" </c:if>/>
					<input name="tScPoStockbillentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].itemId" type="hidden" value="${poVal.itemId }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].stockId" type="hidden" value="${poVal.stockId }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].costPrice" type="hidden" value="${poVal.costPrice }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].costAmount" type="hidden" value="${poVal.costAmount }"/>
				    <input name="tScPoStockbillentryList[${stuts.index }].isKFPeriod" type="hidden" value="${poVal.isKFPeriod }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].freeGifts" type="hidden" value="${poVal.freeGifts }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].batchManager" type="hidden" value="${poVal.batchManager }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].commitQty" type="hidden" value="${poVal.commitQty }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].cgLimitPrice" type="hidden" value="${poVal.cgLimitPrice }"/>
					<input name="tScPoStockbillentryList[${stuts.index }].classIDSrc" type="hidden" value="${poVal.classIDSrc}"/>
					<input name="tScPoStockbillentryList[${stuts.index }].kfDateType" type="hidden" value="${poVal.kfDateType}"/>
					<input name="tScPoStockbillentryList[${stuts.index }].billQty" type="hidden" value="${poVal.billQty}"/>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].itemNo" maxlength="32"
					  		type="text" class="inputxt popup-select"  style="width:105px;"
					               datatype="*" onkeypress="keyDownInfo('${stuts.index}','item',event)" onblur="orderListStockBlur('${stuts.index}','itemId','itemNo');"
 value="${poVal.itemNo }">
					  <label class="Validform_label" style="display: none;">商品编号</label>
				   </td>
					<td align="left" bgcolor="white">
						<input name="tScPoStockbillentryList[${stuts.index }].itemName" maxlength="50"
							   type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226)" readonly="readonly"
							   value="${poVal.itemName }">
						<label class="Validform_label" style="display: none;">商品名称</label>
					</td>
				<td align="left" bgcolor="white">
					<input name="tScPoStockbillentryList[${stuts.index }].model" maxlength="50"
						   type="text" class="inputxt"  style="width:85px;background-color:rgb(226,226,226)" readonly="readonly"
						   value="${poVal.model }">
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScPoStockbillentryList[${stuts.index }].barCode" maxlength="50"
						   type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226)" readonly="readonly"
						   value="${poVal.barCode }">
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].stockName" maxlength="32"
					  		type="text" class="inputxt popup-select"  style="width:65px;" datatype="*"
							   onkeypress="keyDownInfo('${stuts.index}','stock',event)" onblur="orderListStockBlur('${stuts.index}','stockId','stockName');"
 					value="${poVal.stockName }">
					  <label class="Validform_label" style="display: none;">仓库</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].batchNo" maxlength="50" 
					  		type="text" class="inputxt"  style="width:80px;" datatype="*"
					               
					                value="${poVal.batchNo }">
					  <label class="Validform_label" style="display: none;">批号</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input id="unitId[${stuts.index }]" name="tScPoStockbillentryList[${stuts.index }].unitId" maxlength="32"
					  		type="text"
							   class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"

							   style="width:50px;"
 						value="${poVal.unitId }">
					  <label class="Validform_label" style="display: none;">单位</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].qty" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;"
							   datatype="vInt"
 						value="${poVal.qty }">
					  <label class="Validform_label" style="display: none;">数量</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].taxPriceEx" maxlength="20"
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
					               
					                value="${poVal.taxPriceEx  }">
					  <label class="Validform_label" style="display: none;">单价</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].taxAmountEx" maxlength="20"
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
					               
					                value="${poVal.taxAmountEx }">
					  <label class="Validform_label" style="display: none;">金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].discountRate" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
					               
					                value="${poVal.discountRate }">
					  <label class="Validform_label" style="display: none;">折扣率（%）</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].taxPrice" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					                value="${poVal.taxPrice }">
					  <label class="Validform_label" style="display: none;">折后单价</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].inTaxAmount" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;" datatype="num" ignore="ignore"
							   onchange="setAllAmount()"
					                value="${poVal.inTaxAmount }">
					  <label class="Validform_label" style="display: none;">折后金额</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].taxRate" maxlength="20" 
					  		type="text" class="inputxt"
							   datatype="num" ignore="ignore" style="width:70px"
					                value="${poVal.taxRate }">
					  <label class="Validform_label" style="display: none;">税率（%）</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].taxAmount" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					                value="${poVal.taxAmount }">
					  <label class="Validform_label" style="display: none;">税额</label>
				   </td>
				   <td align="left" bgcolor="white">
							<input name="tScPoStockbillentryList[${stuts.index }].kfDate" maxlength="20" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:80px;"
								   onchange="setPeriodDate('${stuts.index }')"
					                value="<fmt:formatDate value='${poVal.kfDate}' type="date" pattern="yyyy-MM-dd"/>">  
					  <label class="Validform_label" style="display: none;">生产日期</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].kfPeriod" maxlength="10" 
					  		type="text" class="inputxt"  style="width:50px;background-color: rgb(226,226,226)" readonly="readonly"
							   onchange="setPeriodDate('${stuts.index }')"
					                value="${poVal.kfPeriod }">
					  <label class="Validform_label" style="display: none;">保质期</label>
				   </td>
				   <td align="left" bgcolor="white">
					   <t:dictSelect field="tScPoStockbillentryList[${stuts.index }].kfDateType_" width="70px" type="list" showDefaultOption="true" extendJson="{disabled:disabled}"
									 typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType }" hasLabel="false"  title="保质期类型"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">保质期类型</label>
				   </td>
				   <td align="left" bgcolor="white">
							<input name="tScPoStockbillentryList[${stuts.index }].periodDate" maxlength="20" 
					  		type="text" class="Wdate"  style="width:80px;background-color:rgb(226,226,226)" readonly="readonly"
					                
					                value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>">  
					  <label class="Validform_label" style="display: none;">有效期至</label>
				   </td>
				   <%--<td align="left" bgcolor="white">--%>
					  	<%--<input name="tScPoStockbillentryList[${stuts.index }].costPrice" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.costPrice }">--%>
					  <%--<label class="Validform_label" style="display: none;">成本单价</label>--%>
				   <%--</td>--%>
				   <%--<td align="left" bgcolor="white">--%>
					  	<%--<input name="tScPoStockbillentryList[${stuts.index }].costAmount" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.costAmount }">--%>
					  <%--<label class="Validform_label" style="display: none;">成本金额</label>--%>
				   <%--</td>--%>
				   <td align="left" bgcolor="white">
							<t:dictSelect field="tScPoStockbillentryList[${stuts.index }].freeGifts_" width="70px" type="list"
										  extendJson="{onChange:setPrice(${stuts.index})}" showDefaultOption="false"
										typeGroupCode="sf_01" defaultVal="${poVal.freeGifts }" hasLabel="false"  title="赠品标记"></t:dictSelect>
					  <label class="Validform_label" style="display: none;">赠品标记</label>
				   </td>
				<c:if test="${tranType eq '52'}">
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].commitQty" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"

						<c:if test="${load ne 'fcopy'}"> value="${poVal.commitQty }" </c:if> >
					  <label class="Validform_label" style="display: none;">退货数量</label>
				   </td>
				</c:if>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].classIDName" maxlength="10"
					  		type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					                <c:if test="${load ne 'fcopy'}">value="${poVal.classIDName }"></c:if>
					  <label class="Validform_label" style="display: none;">源单类型</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].billNoSrc" maxlength="50" 
					  		type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"

							   <c:if test="${load ne 'fcopy'}"> value="${poVal.billNoSrc }" </c:if> >
					  <label class="Validform_label" style="display: none;">源单编号</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].billNoOrder" maxlength="50" 
					  		type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226)" readonly="readonly"

							<c:if test="${load ne 'fcopy'}">   value="${poVal.billNoOrder }"</c:if> >
					  <label class="Validform_label" style="display: none;">订单编号</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tScPoStockbillentryList[${stuts.index }].note" maxlength="255" 
					  		type="text" class="inputxt"  style="width:180px;"
					               
					                value="${poVal.note }">
					  <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
