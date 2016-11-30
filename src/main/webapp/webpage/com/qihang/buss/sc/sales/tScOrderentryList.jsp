<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
/*	$('#addTScOrderentryBtn').linkbutton({
	    iconCls: 'icon-add',
	});
	$('#delTScOrderentryBtn').linkbutton({
	    iconCls: 'icon-remove'
	});*/
/*	$('#addTScOrderentryBtn').bind('click', function(){
 		 var tr =  $("#add_tScOrderentry_table_template tr").clone();
	 	 $("#add_tScOrderentry_table").append(tr);
	 	 resetTrNum('add_tScOrderentry_table');
	 	 return false;
    });  */
    function clickAddTScOrderentryBtn(rowIndex){
        var tr =  $("#add_tScOrderentry_table_template tr").clone();
//		var count = 0;
//		$("#add_tScOrderentry_table").find(">tr").find('input[name$="itemID"]').each(function () {
//			if($(this).val()==""){
//               count++;
//			}
//		});
//		if(count >=1){
//			tip('请填写从表数据');
//			return;
//		}
		$("#add_tScOrderentry_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScOrderentry_table',rowIndex,1);
    }
    function clickDelTScOrderentryBtn(rowIndex){
		var type = '${pageType}';
		if(type == 1){
			var stockQty  = 0;
			if(	$("input[name='tScOrderentryList["+rowIndex+"].stockQty']").val()!=""){
				 stockQty = $("input[name='tScOrderentryList["+rowIndex+"].stockQty']").val();
			}
			if(stockQty==0){
				$("#add_tScOrderentry_table").find(">tr").eq(rowIndex).remove();
				$("#add_tScOrderentry_table").find(">tr").eq(0).find('input[name$="itemWeight"]').trigger("change");
				$("#add_tScOrderentry_table").find(">tr").eq(0).find('input[name$="taxAmountEx"]').trigger("change");
				//		$('input[name= "tScOrderentryList['+rowIndex+'].weight"]').trigger("change");
				resetTrNum('add_tScOrderentry_table');
				var tablelength = $("#add_tScOrderentry_table").find(">tr").length;
				if (tablelength == 0) {
					$("#itemName").removeAttr("disabled");
					var tr = $("#add_tScOrderentry_table_template tr").clone();
					$("#add_tScOrderentry_table").append(tr);
					resetTrNum('add_tScOrderentry_table', -1);
					$("#tScOrderentryList\\[" + 0 + "\\]\\.unitID").combobox({
						width:54,
						valueField: "id",
						textField: "text",
						panelHeight: "auto",
						editable: false
					})
				}
			}else{
				tip("变更时执行数量不为0不可以删除");
			}

		}else {
				$("#add_tScOrderentry_table").find(">tr").eq(rowIndex).remove();
				$("#add_tScOrderentry_table").find(">tr").eq(0).find('input[name$="itemWeight"]').trigger("change");
				$("#add_tScOrderentry_table").find(">tr").eq(0).find('input[name$="taxAmountEx"]').trigger("change");
	   //		$('input[name= "tScOrderentryList['+rowIndex+'].weight"]').trigger("change");
				resetTrNum('add_tScOrderentry_table');
				var tablelength = $("#add_tScOrderentry_table").find(">tr").length;
				if (tablelength == 0) {
					$("#itemName").removeAttr("disabled");
					var tr = $("#add_tScOrderentry_table_template tr").clone();
					$("#add_tScOrderentry_table").append(tr);
					resetTrNum('add_tScOrderentry_table', -1);
					$("#tScOrderentryList\\[" + 0 + "\\]\\.unitID").combobox({
						width:54,
						valueField: "id",
						textField: "text",
						panelHeight: "auto",
						editable: false
					})
				}
		}
        /*   var sumweight = 0;
         $("#add_tScOrderentry_table input[name$='weight']").each(function (i, data) {
            sumweight +=parseFloat($(data).val());
            $("#weight").val(sumweight);
        });*/
    }
/*	$('#delTScOrderentryBtn').bind('click', function(){
      	$("#add_tScOrderentry_table").find("input:checked").parent().parent().remove();
        resetTrNum('add_tScOrderentry_table');
		var sumweight = 0;
		$("#add_tScOrderentry_table input[name$='weight']").each(function (i, data) {
			sumweight +=parseFloat($(data).val());
			$("#weight").val(sumweight);
		});
        return false;
    });*/
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
	    $("#tScOrderentry_table").createhftable({
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

		var stockid = $("#stockID").val();
		if (stockid) {
			var stockName = $("#stockName").val();
			$("input[name='tScOrderentryList[#index#].stockid']").val(stockid);
			$("input[name='tScOrderentryList[#index#].stockName']").val(stockName);
		}
		/**
		 * 针对新增和编辑的情况
		 */
		if(${fn:length(tScOrderentryList)  <= 0 }){
			//设置默认税率
			var defaultTaxRate = $("#CFG_TAX_RATE").val();
			$("input[name='tScOrderentryList[0].taxRate']").val(defaultTaxRate);
			$("input[name='tScOrderentryList[#index#].taxRate']").val(defaultTaxRate);
			$("input[name='tScOrderentryList[0].fetchDate']").val(setmorendate());
			setFunctionInfo(0);
		}
		if(${fn:length(tScOrderentryList)  > 0 }){
			//设置默认税率
			var defaultTaxRate = $("#CFG_TAX_RATE").val();
			$("input[name='tScOrderentryList[#index#].taxRate']").val(defaultTaxRate);
			var  length = $("#add_tScOrderentry_table").find("tr").length;
			var pageType = '${pageType}';
			var index = 0;
			var rowLength = 0;
			if(pageType == 1){
				for(var t = 0 ; t < length ; t++) {
					setFunctionInfo(t);
					setValOldIdAnOldName($('input[name="tScOrderentryList['+t+'].itemNumber"]'),$('input[name="tScOrderentryList['+t+'].itemID"]').val(), $('input[name="tScOrderentryList['+t+'].itemNumber"]').val());
					setValOldIdAnOldName($('input[name="tScOrderentryList['+t+'].stockName"]'),$('input[name="tScOrderentryList['+t+'].stockID"]').val(), $('input[name="tScOrderentryList['+t+'].stockName"]').val());
					$("input[name='tScOrderentryList["+t+"].itemNumber']").attr("disabled","disabled");
					$("input[name='tScOrderentryList["+t+"].itemNumber']").css("background-color","rgb(226,226,226)");
					$("input[name='tScOrderentryList["+t+"].stockName']").attr("disabled","disabled");
					$("input[name='tScOrderentryList["+t+"].stockName']").css("background-color","rgb(226,226,226)");
					$("#tScOrderentryList\\[" + t + "\\]\\.unitID").combobox({disabled:true});
					$("input[name='tScOrderentryList["+t+"].qty']").attr("oldvalue",$("input[name='tScOrderentryList["+t+"].qty']").val());
					$("input[name='tScOrderentryList["+t+"].taxPriceEx']").attr("readonly","readonly");
					$("input[name='tScOrderentryList["+t+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
					$("input[name='tScOrderentryList["+t+"].taxAmountEx']").attr("readonly","readonly");
					$("input[name='tScOrderentryList["+t+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
					$("input[name='tScOrderentryList["+t+"].discountRate']").attr("readonly","readonly");
					$("input[name='tScOrderentryList["+t+"].discountRate']").css("background-color","rgb(226,226,226)");
					$("input[name='tScOrderentryList["+t+"].inTaxAmount']").attr("readonly","readonly");
					$("input[name='tScOrderentryList["+t+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
					$("input[name='tScOrderentryList["+t+"].taxRate']").attr("readonly","readonly");
					$("input[name='tScOrderentryList["+t+"].taxRate']").css("background-color","rgb(226,226,226)");
					$("input[name='tScOrderentryList["+t+"].fetchDate']").removeAttr('onclick');
					$("input[name='tScOrderentryList["+t+"].fetchDate']").attr("readonly","readonly");
					$('select[name="tScOrderentryList[' + t + '].freeGifts_"]').attr("disabled","disabled");
					$("input[name='tScOrderentryList["+t+"].note']").attr("readonly","readonly");
					$("input[name='tScOrderentryList["+t+"].note']").css("background-color","rgb(226,226,226)");
					var freeGifts = $("input[name='tScOrderentryList["+t+"].freeGifts']").val();
					var isFreeGift = $("input[name='tScOrderentryList["+t+"].isFreeGift']").val();
					if(freeGifts == "1" && isFreeGift == "1"){
						$("input[name='tScOrderentryList["+t+"].qty']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+t+"].qty']").css("background-color","rgb(226,226,226)");
						rowInfo[index]=rowLength+1;
					}else{
						index = t;
						rowLength = 0;
					}
				}
			}else{
				for(var j=0 ; j<length ; j++){
					//编辑行金额函数配置
					if("detail" != "${load}") {
						setFunctionInfo(j);
					}
					setValOldIdAnOldName($('input[name="tScOrderentryList['+j+'].itemNumber"]'),$('input[name="tScOrderentryList['+j+'].itemID"]').val(), $('input[name="tScOrderentryList['+j+'].itemNumber"]').val());
					setValOldIdAnOldName($('input[name="tScOrderentryList['+j+'].stockName"]'),$('input[name="tScOrderentryList['+j+'].stockID"]').val(), $('input[name="tScOrderentryList['+j+'].stockName"]').val());
					$('#tScOrderentryList\\['+j+'\\]\\.unitID').combobox({
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
											$('input[name="tScOrderentryList['+rowIndex+'].coefficient"]').val(coefficient);//换算率
											$('input[name="tScOrderentryList['+rowIndex+'].coefficient"]').trigger("change");
											$('input[name="tScOrderentryList['+rowIndex+'].itemBarcode"]').val(barCode);//条形码
											$('input[name="tScOrderentryList['+rowIndex+'].xsLimitPrice"]').val(xsLimitPrice);//销售限价

										}
									}
								});
							}
						}
					})
					var freeGifts = $("input[name='tScOrderentryList["+j+"].freeGifts']").val();
					var isFreeGift = $("input[name='tScOrderentryList["+j+"].isFreeGift']").val();
					if(freeGifts == "1" && isFreeGift == "1"){
						$("input[name='tScOrderentryList["+j+"].qty']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].qty']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].itemNumber']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].itemNumber']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].stockName']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].stockName']").css("background-color","rgb(226,226,226)");
						$("#tScOrderentryList\\[" + j + "\\]\\.unitID").combobox({disabled:true});
						$("input[name='tScOrderentryList["+j+"].qty']").attr("oldvalue",$("input[name='tScOrderentryList["+t+"].qty']").val());
						$("input[name='tScOrderentryList["+j+"].taxPriceEx']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].taxPriceEx']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].taxAmountEx']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].taxAmountEx']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].discountRate']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].discountRate']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].inTaxAmount']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].inTaxAmount']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].taxRate']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].taxRate']").css("background-color","rgb(226,226,226)");
						$("input[name='tScOrderentryList["+j+"].fetchDate']").removeAttr('onclick');
						$("input[name='tScOrderentryList["+j+"].fetchDate']").attr("readonly","readonly");
						$('select[name="tScOrderentryList[' + j + '].freeGifts_"]').attr("disabled","disabled");
						$("input[name='tScOrderentryList["+j+"].note']").attr("readonly","readonly");
						$("input[name='tScOrderentryList["+j+"].note']").css("background-color","rgb(226,226,226)");
						rowInfo[index]=rowLength+1;
					}else{
						index = t;
						rowLength = 0;
					}
					var idSrc = $("input[name='tScOrderentryList["+j+"].idSrc']").val();
					if(idSrc){
						$("input[name='tScOrderentryList["+j+"].itemNumber']").attr("disabled","disabled");
						$("input[name='tScOrderentryList["+j+"].itemNumber']").css("background-color","rgb(226,226,226)");
						$("#tScOrderentryList\\[" + j + "\\]\\.unitID").combobox({
							disabled:true
						})
						$("#itemName").attr("disabled","disabled");
						//$("#itemName").css("background-color","rgb(226,226,226)");
					}
				}
			}
		}
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScOrderentry_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<c:if test="${load ne 'detail'}">
	  	<td align="center" bgcolor="#476f9a">操作</td>
		</c:if>
		<td align="left" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a">商品名称</td>
		<td align="left" bgcolor="#476f9a">规格</td>
		<td align="left" bgcolor="#476f9a">条形码</td>
		<td align="left" bgcolor="#476f9a">仓库</td>
		<td align="left" bgcolor="#476f9a">单位<span style="color: red">*</span></td>
		<td align="left" bgcolor="#476f9a" total="true">数量<span style="color: red">*</span></td>
		<%--<td align="left" bgcolor="#476f9a">换算率</td>--%>
		<%--<td align="left" bgcolor="#476f9a">基本数量</td>--%>
		<td align="left" bgcolor="#476f9a">单价</td>
		<td align="left" bgcolor="#476f9a" total="true">金额</td>
		<td align="left" bgcolor="#476f9a">折扣率（%）</td>
		<td align="left" bgcolor="#476f9a">折后单价</td>
		<td align="left" bgcolor="#476f9a" total="true">折后金额</td>
		<td align="left" bgcolor="#476f9a">商城单价</td>
		<td align="left" bgcolor="#476f9a">商城金额</td>
		<td align="left" bgcolor="#476f9a" total="true">重量</td>
		<td align="left" bgcolor="#476f9a">税率（%）</td>
		<%--<td align="left" bgcolor="#476f9a">不含税单价</td>--%>
		<%--<td align="left" bgcolor="#476f9a">不含税金额</td>--%>
		<%--<td align="left" bgcolor="#476f9a">不含税折后单价</td>--%>
		<%--<td align="left" bgcolor="#476f9a">不含税折后金额</td>--%>
		<td align="left" bgcolor="#476f9a" total="true">税额</td>
		<td align="left" bgcolor="#476f9a">交货日期</td>
		<td align="left" bgcolor="#476f9a">促销类型</td>
		<td align="left" bgcolor="#476f9a">赠品标记</td>
		<td align="left" bgcolor="#476f9a">发货数量</td>
		<td align="left" bgcolor="#476f9a">源单编号</td>
		<td align="left" bgcolor="#476f9a">源单类型</td>
		<td align="left" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScOrderentry_table">
	<c:if test="${fn:length(tScOrderentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScOrderentryList[0].itemID"  type="hidden"/>
					<input name="tScOrderentryList[0].stockID"  type="hidden"/>
					<input name="tScOrderentryList[0].indexNumber" type="hidden"  value="1"/>
					<input name="tScOrderentryList[0].basicUnitID" type="hidden"/>
					<input name="tScOrderentryList[0].secUnitID" type="hidden"/>
					<input name="tScOrderentryList[0].secCoefficient" type="hidden"/>
					<input name="tScOrderentryList[0].secQty" type="hidden"/>
					<input name="tScOrderentryList[0].price" type="hidden"/>
					<input name="tScOrderentryList[0].amount" type="hidden"/>
					<input name="tScOrderentryList[0].discountPrice" type="hidden"/>
					<input name="tScOrderentryList[0].discountAmount" type="hidden"/>
					<input id="tScOrderentryList[0].itemWeight" name="tScOrderentryList[0].itemWeight" type="hidden"/>
					<input name="tScOrderentryList[0].xsLimitPrice" type="hidden"/>
					<input name="tScOrderentryList[0].salesID" type="hidden"/>
					<input name="tScOrderentryList[0].freeGifts" value="0" type="hidden"/>
					<input name="tScOrderentryList[0].isFreeGift" value="0" type="hidden"/>
					<input name="tScOrderentryList[0].idSrc" type="hidden"/>
					<input name="tScOrderentryList[0].classIDSrc"  type="hidden"/>
					<input name="tScOrderentryList[0].coefficient"  type="hidden"/>
					<input name="tScOrderentryList[0].basicQty"  type="hidden"/>
					<div style="width: 25px;" name="xh">1</div>
				</td>
				<td align="center"  bgcolor="white">
					<div style="width: 80px;">
						<a name="addTScOrderentryBtn[0]" id="addTScOrderentryBtn[0]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScOrderentryBtn(0);"></a>
						<a name="delTScOrderentryBtn[0]" id="delTScOrderentryBtn[0]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn(0);"></a>
					</div></td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].itemNumber" maxlength="32" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*" onkeypress="orderListIcitemKeyPress(0,event);" onblur="orderListIcItemBlur(0);">
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].itemName"  type="text" class="inputxt"  style="width:180px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].itemModel"  type="text" class="inputxt"  style="width:85px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].itemBarcode"  type="text" class="inputxt"  style="width:65px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
						<input name="tScOrderentryList[0].stockName"  type="text" class="inputxt popup-select"  style="width:65px;" onkeypress="orderListStockKeyPress(0,event);" onblur="orderListStockBlur(0);"/>
				</td>
				<td align="left" bgcolor="white">
					<%--<select id="tScOrderentryList[0].unitID" name="tScOrderentryList[0].unitID" style="width:80px;" datatype="*"></select>--%>
                     <input id="tScOrderentryList[0].unitID" name="tScOrderentryList[0].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false" style="width:50px;"/>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].qty" maxlength="32" type="text" class="inputxt" onblur="changePrice(0)"  style="width:70px;" datatype="vInt">
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<%--<td align="left" bgcolor="white">--%>
					<%--<input name="tScOrderentryList[0].coefficient"  style="width:70px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
					<%--<label class="Validform_label" style="display: none;">换算率</label>--%>
				<%--</td>--%>
				<%--<td align="left" bgcolor="white">--%>
					<%--<input name="tScOrderentryList[0].basicQty"  style="width:70px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
					<%--<label class="Validform_label" style="display: none;">基本数量</label>--%>
				<%--</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="float" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="float" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].discountRate" maxlength="32" type="text" class="inputxt"  style="width:70px;"  datatype="float" ignore="ignore" value="100">
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].taxPrice" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true"/>
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="float" ignore="ignore">
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].mallPrice" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">商城单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].mallAmount" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true" >
					<label class="Validform_label" style="display: none;">商城金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].weight" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">重量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].taxRate" maxlength="32" type="text" class="inputxt"  style="width:70px;"  datatype="float" ignore="ignore" value="0">
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
				<%--<td align="left" a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].price" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" ignore="ignore" readonly="true">--%>
					<%--<label class="Validform_label" style="display: none;">不含税单价</label>--%>
				<%--</td>--%>
				<%--<td align="left" a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"  readonly="true">--%>
					<%--<label class="Validform_label" style="display: none;">不含税金额</label>--%>
				<%--</td>--%>
				<%--<td align="left"  a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;">--%>
					<%--<label class="Validform_label" style="display: none;">不含税折后单价</label>--%>
				<%--</td>--%>
				<%--<td align="left"  a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;">--%>
					<%--<label class="Validform_label" style="display: none;">不含税折后金额</label>--%>
				<%--</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].fetchDate" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;">
					<label class="Validform_label" style="display: none;">交货日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].salesName" maxlength="32" type="text" class="inputxt"  style="width:80px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">促销类型</label>
				</td>
				<td align="left" bgcolor="white">
					<t:dictSelect field="tScOrderentryList[0].freeGifts_" extendJson="{onChange:setPrice(${0})}" type="list" typeGroupCode="sf_01" width="80px" defaultVal="0" hasLabel="false" showDefaultOption="false" title="赠品标记"></t:dictSelect>
						<label class="Validform_label" style="display: none;">赠品标记</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].stockQty" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);"  readonly="true">
					<label class="Validform_label" style="display: none;">发货数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].billNoSrc" maxlength="50" type="text" class="inputxt"  style="width:90px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">源单编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[0].classIDName" maxlength="32" type="text" class="inputxt"  style="width:90px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">源单类型</label>
				</td>
				<td align="left" bgcolor="white"><input name="tScOrderentryList[0].note" maxlength="255" type="text" class="inputxt"  style="width:180px;" datatype="*1-125" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">备注 </label>
				</td>
			</tr>
	</c:if>
	<c:if test="${fn:length(tScOrderentryList)  > 0 }">
		<c:forEach items="${tScOrderentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScOrderentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScOrderentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScOrderentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScOrderentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScOrderentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScOrderentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScOrderentryList[${stuts.index }].version" type="hidden"    value="${poVal.version }"/>
					<input name="tScOrderentryList[${stuts.index }].orderId"  type="hidden"  value="${poVal.orderId }"/>
					<input name="tScOrderentryList[${stuts.index }].itemID"  type="hidden" value="${poVal.itemID}"/>
					<input name="tScOrderentryList[${stuts.index }].stockID"  type="hidden"  value="${poVal.stockID}"/>
					<input name="tScOrderentryList[${stuts.index }].indexNumber" type="hidden"  value="${poVal.indexNumber}"/>
					<input name="tScOrderentryList[${stuts.index }].basicUnitID" type="hidden" value="${poVal.basicUnitID}"/>
					<input name="tScOrderentryList[${stuts.index }].secUnitID" type="hidden"  value="${poVal.secUnitID}"/>
					<input name="tScOrderentryList[${stuts.index}].secCoefficient" type="hidden"  value="${poVal.secCoefficient}"/>
					<input name="tScOrderentryList[${stuts.index}].secQty" type="hidden" value="${poVal.secQty}"/>
					<input name="tScOrderentryList[${stuts.index}].price" type="hidden" value="${poVal.price}"/>
					<input name="tScOrderentryList[${stuts.index}].amount" type="hidden"  value="${poVal.amount}"/>
					<input name="tScOrderentryList[${stuts.index}].discountPrice" type="hidden" value="${poVal.discountPrice}"/>
					<input name="tScOrderentryList[${stuts.index}].discountAmount" type="hidden" value="${poVal.discountAmount}"/>
					<input id="tScOrderentryList[${stuts.index}].itemWeight" name="tScOrderentryList[${stuts.index}].itemWeight" type="hidden" value="${poVal.itemWeight}"/>
					<input name="tScOrderentryList[${stuts.index}].xsLimitPrice" type="hidden"  value="${poVal.xsLimitPrice}"/>
					<input name="tScOrderentryList[${stuts.index}].salesID" type="hidden"  value="${poVal.salesID}"/>
					<input name="tScOrderentryList[${stuts.index}].freeGifts" type="hidden"  value="${poVal.freeGifts}"/>
					<input name="tScOrderentryList[${stuts.index}].isFreeGift" value="${poVal.isFreeGift}" type="hidden"/>
					<input name="tScOrderentryList[${stuts.index}].idSrc" <c:if test="${load ne 'fcopy'}"> value="${poVal.idSrc}" </c:if> type="hidden"/>
					<input name="tScOrderentryList[${stuts.index}].classIDSrc" <c:if test="${load ne 'fcopy'}"> value="${poVal.classIDSrc}" </c:if> type="hidden"/>
					<input name="tScOrderentryList[${stuts.index}].coefficient" value="${poVal.coefficient}" type="hidden"/>
					<input name="tScOrderentryList[${stuts.index}].basicQty" value="${poVal.basicQty}" type="hidden"/>
					<div style="width: 25px;" name="xh">${stuts.index+1 }</div>
				</td>
				<c:if test="${load ne 'detail'}">
				<td align="center" bgcolor="white">
					<div style="width: 80px;">
					<a name="addTScOrderentryBtn[${stuts.index}]" id="addTScOrderentryBtn[${stuts.index}]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScOrderentryBtn(${stuts.index});"></a>
						<a name="delTScOrderentryBtn[${stuts.index}]" id="delTScOrderentryBtn[${stuts.index}]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn(${stuts.index});"></a>
				</div></td>
				</c:if>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].itemNumber"  value="${poVal.itemNumber}" maxlength="32" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*" onkeypress="orderListIcitemKeyPress(${stuts.index},event);" onblur="orderListIcItemBlur(${stuts.index});">
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].itemName" value="${poVal.itemName}" type="text" class="inputxt"  style="width:180px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].itemModel" value="${poVal.itemModel}" type="text" class="inputxt"  style="width:85px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].itemBarcode" value="${poVal.itemBarcode}"  type="text" class="inputxt"  style="width:65px; background-color: rgb(226, 226, 226);" readonly="true"/>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].stockName"  value="${poVal.stockName}" type="text" class="inputxt popup-select"  style="width:65px;" onkeypress="orderListStockKeyPress(${stuts.index},event);" onblur="orderListStockBlur(${stuts.index});"/>
				</td>
				<td align="left" bgcolor="white">
						<%--<select id="tScOrderentryList[0].unitID" name="tScOrderentryList[0].unitID" style="width:80px;" datatype="*"></select>--%>
					<input id="tScOrderentryList[${stuts.index}].unitID" name="tScOrderentryList[${stuts.index}].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',width:54,editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemID}'" value="${poVal.unitID}" style="width:50px;"/>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].qty" maxlength="32" type="text" class="inputxt" onblur="changePrice(${stuts.index})"  style="width:70px;" datatype="vInt"  value="${poVal.qty}" <c:if test="${pageType==1}">onchange="editQtyChange(${stuts.index })"</c:if>>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<%--<td align="left" bgcolor="white">--%>
					<%--<input name="tScOrderentryList[${stuts.index}].coefficient"  value="${poVal.coefficient}"  style="width:70px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
					<%--<label class="Validform_label" style="display: none;">换算率</label>--%>
				<%--</td>--%>
				<%--<td align="left" bgcolor="white">--%>
					<%--<input name="tScOrderentryList[${stuts.index}].basicQty"    value="${poVal.basicQty}" style="width:70px; background-color: rgb(226, 226, 226);" class="inputxt"  type="text" readonly="true" />--%>
					<%--<label class="Validform_label" style="display: none;">基本数量</label>--%>
				<%--</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].taxPriceEx" <c:if test="${load eq 'fcopy' and poVal.freeGifts eq 1}">readonly="readonly" style="width: 70px;background-color: rgb(226,226,226)"</c:if>  value="${poVal.taxPriceEx}" maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="float" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].taxAmountEx"  value="${poVal.taxAmountEx}" <c:if test="${load eq 'fcopy' and poVal.freeGifts eq 1}">readonly="readonly" style="width: 70px;background-color: rgb(226,226,226)"</c:if> maxlength="32" type="text" class="inputxt"  style="width:70px;" datatype="float" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].discountRate"  value="${poVal.discountRate}" <c:if test="${load eq 'fcopy' and poVal.freeGifts eq 1}">readonly="readonly" style="width: 70px;background-color: rgb(226,226,226)"</c:if>  maxlength="32" type="text" class="inputxt"  style="width:70px;"  datatype="float" ignore="ignore">
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].taxPrice"   value="${poVal.taxPrice}" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true"/>
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].inTaxAmount"  value="${poVal.inTaxAmount}" maxlength="32" <c:if test="${load eq 'fcopy' and poVal.freeGifts eq 1}">readonly="readonly" style="width: 70px;background-color: rgb(226,226,226)"</c:if>  type="text" class="inputxt"  style="width:70px;" datatype="float" ignore="ignore">
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].mallPrice"  value="${poVal.mallPrice}" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">商城单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].mallAmount"  value="${poVal.mallAmount}" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true" >
					<label class="Validform_label" style="display: none;">商城金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].weight" value="${poVal.weight}"  maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">重量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].taxRate" value="${poVal.taxRate}" maxlength="32" type="text" class="inputxt" <c:if test="${load eq 'fcopy' and poVal.freeGifts eq 1}">readonly="readonly" style="width: 70px;background-color: rgb(226,226,226)"</c:if>  style="width:70px;"  datatype="float" ignore="ignore">
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
					<%--<td align="left" a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].price" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="num" ignore="ignore" readonly="true">--%>
					<%--<label class="Validform_label" style="display: none;">不含税单价</label>--%>
					<%--</td>--%>
					<%--<td align="left" a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"  readonly="true">--%>
					<%--<label class="Validform_label" style="display: none;">不含税金额</label>--%>
					<%--</td>--%>
					<%--<td align="left"  a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;">--%>
					<%--<label class="Validform_label" style="display: none;">不含税折后单价</label>--%>
					<%--</td>--%>
					<%--<td align="left"  a="bumingbai">--%>
					<%--<input name="tScOrderentryList[0].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;">--%>
					<%--<label class="Validform_label" style="display: none;">不含税折后金额</label>--%>
					<%--</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].taxAmount" value="${poVal.taxAmount}" maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].fetchDate"   value="<fmt:formatDate value='${poVal.fetchDate}' type="date" pattern="yyyy-MM-dd"/>"  maxlength="32" type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;">
					<label class="Validform_label" style="display: none;">交货日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].salesName"  value="${poVal.salesName}" maxlength="32" type="text" class="inputxt"  style="width:80px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">促销类型</label>
				</td>
				<td align="left" bgcolor="white">
					<t:dictSelect field="tScOrderentryList[${stuts.index}].freeGifts_" type="list" extendJson="{onChange:setPrice(${stuts.index })}" typeGroupCode="sf_01" width="80px" defaultVal="${poVal.freeGifts}" hasLabel="false" showDefaultOption="false" title="赠品标记"></t:dictSelect>
					<label class="Validform_label" style="display: none;">赠品标记</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].stockQty"  <c:if test="${load ne 'fcopy'}"> value="${poVal.stockQty}"</c:if> maxlength="32" type="text" class="inputxt"  style="width:70px; background-color: rgb(226, 226, 226);"  readonly="true">
					<label class="Validform_label" style="display: none;">发货数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].billNoSrc" <c:if test="${load ne 'fcopy'}"> value="${poVal.billNoSrc}"</c:if> maxlength="50" type="text" class="inputxt"  style="width:90px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">源单编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScOrderentryList[${stuts.index}].classIDName" <c:if test="${load ne 'fcopy'}"> value="${poVal.classIDName}" </c:if> maxlength="32" type="text" class="inputxt"  style="width:90px; background-color: rgb(226, 226, 226);" readonly="true">
					<label class="Validform_label" style="display: none;">源单类型</label>
				</td>
				<td align="left" bgcolor="white"><input name="tScOrderentryList[${stuts.index}].note" value="${poVal.note}" maxlength="255" type="text" class="inputxt"  style="width:180;" datatype="*1-125" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">备注 </label>
				</td>

   			</tr>
		</c:forEach>
	</c:if>
	</tbody>
</table>
<script>
	/*$("#add_tScOrderentry_table").on('input propertychange',' input[name$="weight"]', function() {
		 var sum = 0;
		 $("#add_tScOrderentry_table input[name$='weight']").each(function (i, data) {
			 var t =0;
			 if($(data).val()==""||$(data).val()==undefined){
				 t=0;
			 }else{
				 t = $(data).val();
			 }
		 sum +=parseFloat(t);
		 $("#weight").val(sum);
		});
	});*/
</script>
