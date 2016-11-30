<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
//	$('#addTPoOrderentryBtn').linkbutton({
//	    iconCls: 'icon-add'
//	});
//	$('#delTPoOrderentryBtn').linkbutton({
//	    iconCls: 'icon-remove'
//	});
//	$('#addTPoOrderentryBtn').bind('click', function(){
// 		 var tr =  $("#add_tPoOrderentry_table_template tr").clone();
//	 	 $("#add_tPoOrderentry_table").append(tr);
//	 	 resetTrNum('add_tPoOrderentry_table');
//	 	 return false;
//    });
//	$('#delTPoOrderentryBtn').bind('click', function(){
//      	$("#add_tPoOrderentry_table").find("input:checked").parent().parent().remove();
//        resetTrNum('add_tPoOrderentry_table');
//        return false;
//    });
	function clickAddTPoOrderentryBtn(index){
		var tr =  $("#add_tPoOrderentry_table_template tr").clone();
		//$("#add_tPoOrderentry_table").append(tr);
		$("#add_tPoOrderentry_table tr").eq(index).after(tr);
		rowIndex++;
		resetTrNum('add_tPoOrderentry_table',index,1);
	}
	function clickDelTScOrderentryBtn(index){
		var stockQty = $("input[name='tPoOrderentryList["+index+"].stockQty']").val();
		if(stockQty > 0){
			tip("该商品已被执行，不可删除");
			return;
		}
		$("#add_tPoOrderentry_table").find(">tr").eq(index).remove();
		var length = $("#add_tPoOrderentry_table").find(">tr").length;
		if(length == 0){
			var tr =  $("#add_tPoOrderentry_table_template tr").clone();
			$("#add_tPoOrderentry_table").append(tr);
			resetTrNum('add_tPoOrderentry_table',-1);
			$("#unitId\\[" + 0 + "\\]").combobox({
				width:84,
				valueField: "id",
				textField: "text",
				panelHeight: "auto",
				editable: false
			})
		}else {
			resetTrNum('add_tPoOrderentry_table');
		}
	}

	function keyDownInfo(index,name,evt){
		debugger;
		var evt = (evt)?evt:((window.event)?window.event:"");
		var code =evt.keyCode?evt.keyCode:evt.which;
		if(code == 13){
			if(name=="stock") {
				selectStockDialog(index);
			}else if(name == "item"){
				selectIcitemDialog(index);
			}else if(name == "unit"){
				selectUnitDialog(index);
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
	    $("#tPoOrderentry_table").createhftable({
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
		if(${fn:length(tPoOrderentryList)  <= 0 }){

			var billDate = $("#date").val();
			var taxRate = $("#defaultTaxRate").val();
			$("input[name='tPoOrderentryList[0].taxRate']").val(taxRate);
			$("input[name='tPoOrderentryList[0].jhDate']").val(billDate);
			$("input[name='tPoOrderentryList[0].stockName']").keypress(function (e) {
				if (e.keyCode == 13) {
					selectStockDialog(0);
				}
			});
//			$("input[name='tPoOrderentryList[0].unitName']").keypress(function(e){
//				if(e.keyCode == 13){
//					selectUnitDialog(0);
//				}
//			})
			setFunctionInfo(0);
		}else{
			var checkState = $("#checkState").val();
			var $tbody = $("#tPoOrderentry_table");
			var length = $tbody[0].rows.length;
			rowIndex = length-1;
			//变更功能配置
			if(checkState == 2 && "fcopy" != '${load}'){
				for(var i=0 ; i<length ; i++){
					setFunctionInfo(i);
					$("input[name='tPoOrderentryList["+i+"].stockName']").attr("disabled","disabled");
					setValOldIdAnOldName($("input[name='tPoOrderentryList["+i+"].stockName']"),$("input[name='tPoOrderentryList["+i+"].stockId']").val(),$("input[name='tPoOrderentryList["+i+"].stockName']").val());
					$("input[name='tPoOrderentryList["+i+"].itemNo']").attr("disabled","disabled");
					setValOldIdAnOldName($("input[name='tPoOrderentryList["+i+"].itemNo']"),$("input[name='tPoOrderentryList["+i+"].itemId']").val(),$("input[name='tPoOrderentryList["+i+"].itemNo']").val());

					$("input[name='tPoOrderentryList["+i+"].unitId']").attr("readonly","readonly");
					$("#unitId\\["+i+"\\]").combobox({disabled:true});
					$("input[name='tPoOrderentryList["+i+"].discountRate']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].taxRate']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].taxPriceEx']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].taxAmountEx']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].taxPrice']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].inTaxAmount']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].jhDate']").attr("readonly","readonly");
					$("input[name='tPoOrderentryList["+i+"].jhDate']").attr("onclick","");
					$("input[name='tPoOrderentryList["+i+"].note']").attr("readonly","readonly");
					$("select[name='tPoOrderentryList["+i+"].freeGifts']").attr("disabled","disabled");
					$("input[name='tPoOrderentryList["+i+"].qty']").attr("oldvalue",$("input[name='tPoOrderentryList["+i+"].stockQty']").val());
				}
			}else{
				for(var j=0 ; j<length ; j++){
					//编辑行金额函数配置
					if("detail" != "${load}") {
						setFunctionInfo(j);
					}
					var unitId = $("#unitId\\["+j+"\\]").val();
					var itemId = $("input[name='tPoOrderentryList["+j+"].itemId']").val();
					setValOldIdAnOldName($("input[name='tPoOrderentryList["+j+"].stockName']"),$("input[name='tPoOrderentryList["+j+"].stockId']").val(),$("input[name='tPoOrderentryList["+j+"].stockName']").val());
					setValOldIdAnOldName($("input[name='tPoOrderentryList["+j+"].itemNo']"),itemId,$("input[name='tPoOrderentryList["+j+"].itemNo']").val());
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
										$("input[name='tPoOrderentryList["+rowIndex+"].barCode']").val(barCode);
										$("input[name='tPoOrderentryList["+rowIndex+"].coefficient']").val(cofficient);
										$("input[name='tPoOrderentryList["+rowIndex+"].coefficient']").trigger("propertychange");
										$("input[name='tPoOrderentryList["+rowIndex+"].secCoefficient']").trigger("propertychange");
										$("input[name='tPoOrderentryList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);
									}
								});
							}
						}
					})
					var freeGifts = $("select[name='tPoOrderentryList["+j+"].freeGifts']").val();
					if(freeGifts == 1){
						setPrice(j);
					}
				}
			}
		}
    });
</script>
<%--<div style="padding: 3px; height: 25px;width:auto;" class="datagrid-toolbar">--%>
	<%--<a id="addTPoOrderentryBtn" href="#">添加</a> <a id="delTPoOrderentryBtn" href="#">删除</a> --%>
<%--</div>--%>
<table border="0" cellpadding="2" cellspacing="1" id="tPoOrderentry_table" totalRow="true" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white;">
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
			条码
		</td>
		<td align="center" bgcolor="#476f9a">
			仓库
		</td>
				  <td align="center" bgcolor="#476f9a">
						单位<span style="color: red">*</span>
				  </td>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--辅助单位--%>
				  <%--</td>--%>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--基本单位--%>
				  <%--</td>--%>
				  <td align="center" bgcolor="#476f9a" total="true">
						数量<span style="color: red">*</span>
				  </td>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--辅助换算率--%>
				  <%--</td>--%>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--辅助数量--%>
				  <%--</td>--%>
				  <%--<td align="center" bgcolor="#476f9a">--%>
						<%--换算率--%>
				  <%--</td>--%>
				  <%--<td align="center" bgcolor="#476f9a">--%>
						<%--基本数量--%>
				  <%--</td>--%>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--不含税单价--%>
				  <%--</td>--%>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--不含税金额--%>
				  <%--</td>--%>
				<td align="center" bgcolor="#476f9a">
					单价
				</td>
				<td align="center" bgcolor="#476f9a" total="true">
					金额
				</td>
				  <td align="center" bgcolor="#476f9a">
						折扣率（%）
				  </td>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--不含税折后单价--%>
				  <%--</td>--%>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--不含税折后金额--%>
				  <%--</td>--%>

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
						收货日期
				  </td>
				  <td align="center" bgcolor="#476f9a">
						赠品标记<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
						收货数量
				  </td>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--源单类型--%>
				  <%--</td>--%>
				  <%--<td align="left" bgcolor="#476f9a">--%>
						<%--源单编号--%>
				  <%--</td>--%>
				  <td align="center" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tPoOrderentry_table">	
	<c:if test="${fn:length(tPoOrderentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 25px;" name="xh">1</div></td>
				<td align="center" bgcolor="white"><div style="width: 80px;">
					<a name="addTScOrderentryBtn[0]" id="addTScOrderentryBtn[0]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTPoOrderentryBtn(0);"></a>
					<a name="delTScOrderentryBtn[0]" id="delTScOrderentryBtn[0]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn(0);"></a>
				</div></td>
					<input name="tPoOrderentryList[0].id" type="hidden"/>
					<input name="tPoOrderentryList[0].createName" type="hidden"/>
					<input name="tPoOrderentryList[0].createBy" type="hidden"/>
					<input name="tPoOrderentryList[0].createDate" type="hidden"/>
					<input name="tPoOrderentryList[0].updateName" type="hidden"/>
					<input name="tPoOrderentryList[0].updateBy" type="hidden"/>
					<input name="tPoOrderentryList[0].updateDate" type="hidden"/>
					<input name="tPoOrderentryList[0].fid" type="hidden"/>
					<input name="tPoOrderentryList[0].fidSrc" type="hidden"/>
					<input name="tPoOrderentryList[0].idSrc" type="hidden"/>
				    <input name="tPoOrderentryList[0].findex" value="1" type="hidden">
					<input name="tPoOrderentryList[0].secUnitId" type="hidden">
					<input name="tPoOrderentryList[0].basicUnitId" type="hidden" >
					<input id="secCoefficient0" name="tPoOrderentryList[0].secCoefficient" type="hidden">
					<input id="secQty0" name="tPoOrderentryList[0].secQty" type="hidden">
					<input name="tPoOrderentryList[0].stockId" type="hidden">
					<input name="tPoOrderentryList[0].itemId" type="hidden">
				<input name="tPoOrderentryList[0].coefficient" type="hidden">
				<input name="tPoOrderentryList[0].basicQty" type="hidden">
					<%--<input name="tPoOrderentryList[0].unitId" type="hidden">--%>
				    <input id="amount0" name="tPoOrderentryList[0].amount" type="hidden">
					<input id="discountPrice0" name="tPoOrderentryList[0].discountPrice" type="hidden">
					<input id="discountAmount0" name="tPoOrderentryList[0].discountAmount" type="hidden" >
					<input id="price0" name="tPoOrderentryList[0].price" type="hidden">
					<input name="tPoOrderentryList[0].cgLimitPrice" type="hidden"/>
					<input name="tPoOrderentryList[0].version" type="hidden">
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].itemNo" maxlength="32"
						   type="text" class="inputxt popup-select"  style="width:105px;" datatype="*"
								onkeypress="keyDownInfo(0,'item',event)" onblur="orderListStockBlur('0','itemId','itemNo');"
							>
					<label class="Validform_label" style="display: none;">商品编码</label>
				</td>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].itemName" maxlength="32"
					  		type="text" class="inputxt" readonly="readonly"  style="width:180px;background-color:rgb(226,226,226)"
					               
					               >
					  <label class="Validform_label" style="display: none;">商品名称</label>
					</td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].model" maxlength="32"
						   type="text" class="inputxt" readonly="readonly" style="width:85px;background-color:rgb(226,226,226)"

							>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].barCode" maxlength="32"
						   type="text" class="inputxt" readonly="readonly" style="width:65px;background-color:rgb(226,226,226)"

							>
					<label class="Validform_label" style="display: none;">条码</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].stockName" maxlength="32"
						   type="text" class="inputxt popup-select"  style="width:65px;" onblur="orderListStockBlur('0','stockId','stockName');"

							>
					<label class="Validform_label" style="display: none;">仓库</label>
				</td>
				  <td align="left" bgcolor="white">
					  	<input id="unitId[0]" name="tPoOrderentryList[0].unitId" maxlength="32"
					  		type="text" class="easyui-combobox" data-options="valueField: 'id',width:84,textField: 'text',panelHeight: 'auto',editable: false" style="width:80px;"
					               >
					  <label class="Validform_label" style="display: none;">单位</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].qty" maxlength="20" datatype="vInt"
					  		type="text" class="inputxt" id="qty0"
							   <%--onchange="setAllQty(0)" --%>
							   style="width:65px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">数量</label>
					</td>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].coefficient" maxlength="20" --%>
					  		<%--type="text" class="inputxt" readonly="readonly" id="coefficient0"--%>
							   <%--&lt;%&ndash;onchange="setBasicQty(0)"  &ndash;%&gt;--%>
							   <%--style="width:65px;background-color:rgb(226,226,226)"--%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
					<%--</td>--%>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].basicQty" maxlength="20" --%>
					  		<%--type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color:rgb(226,226,226)"--%>
					               <%--id="basicQty0"--%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">基本数量</label>--%>
					<%--</td>--%>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].price" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">不含税单价</label>--%>
					<%--</td>--%>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].amount" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">不含税金额</label>--%>
					<%--</td>--%>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].taxPriceEx" maxlength="20"
						   type="text" class="inputxt" id="taxPriceEx0" datatype="num" ignore="ignore"
						   <%--onchange="onPriceChange(0)" --%>
						   style="width:70px;"
							>
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].taxAmountEx" maxlength="20" datatype="num" ignore="ignore"
						   type="text" class="inputxt"  style="width:70px;" id="taxAmountEx0"
						  <%-- onchange="onAmountChange(0)"--%>

							>
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].discountRate" maxlength="20" 
					  		type="text" class="inputxt" value="100" id="discountRate0" datatype="num" ignore="ignore"
							   <%--onchange="ondisRateChange(0)" --%>
							   style="width:80px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">折扣率（%）</label>
					</td>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].discountPrice" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">不含税折后单价</label>--%>
					<%--</td>--%>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].discountAmount" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">不含税折后金额</label>--%>
					<%--</td>--%>

				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].taxPrice" maxlength="20"
					  		type="text" class="inputxt" readonly="readonly"  style="width:70px;background-color:rgb(226,226,226)"
					               id="taxPrice0"
					               >
					  <label class="Validform_label" style="display: none;">折后单价</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].inTaxAmount" maxlength="20"  datatype="num" ignore="ignore"
					  		type="text" class="inputxt"  style="width:70px;" id="inTaxAmount0"
							   <%--onchange="onTaxAmountChange(0)"--%>
					               onchange="setAllAmount()"
					               >
					  <label class="Validform_label" style="display: none;">折后金额</label>
					</td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[0].taxRate" maxlength="20"
						   type="text" class="inputxt" value="0"  id="taxRate0" datatype="num" ignore="ignore"
						   <%--onchange="onRateChange(0)"  --%>
						   style="width:70px;"

							>
					<label class="Validform_label" style="display: none;">税率（%）</label>
				</td>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].taxAmount" maxlength="20" 
					  		type="text" class="inputxt"  readonly="readonly" style="width:70px;background-color:rgb(226,226,226)"
					               id="taxAmount0"
					               >
					  <label class="Validform_label" style="display: none;">税额</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<%--<input name="tPoOrderentryList[0].jhDate" maxlength="20" --%>
					  		<%--type="text" class="easyui-datebox"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
							<input  name="tPoOrderentryList[0].jhDate" type="text" style="width: 100px"
									class="Wdate" onClick="WdatePicker()" >
					  <label class="Validform_label" style="display: none;">收货日期</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<%--<input name="tPoOrderentryList[0].freeGifts" maxlength="1" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
							<t:dictSelect field="tPoOrderentryList[0].freeGifts" type="list" showDefaultOption="false"
										  typeGroupCode="sf_01"
										  defaultVal="0"
										  width="70px"
										  hasLabel="false"
										  extendJson="{datatype:select1,onChange:setPrice(${0})}"
										  title="赠品标记" ></t:dictSelect>
					  <label class="Validform_label" style="display: none;">赠品标记</label>
					</td>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].stockQty" maxlength="20" 
					  		type="text" class="inputxt" readonly="readonly"  style="width:70px;background-color:rgb(226,226,226)"
					               value="0"
					               >
					  <label class="Validform_label" style="display: none;">收货数量</label>
					</td>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].classIDSrc" maxlength="10" --%>
					  		<%--type="text" class="inputxt" readonly="readonly"  style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">源单类型</label>--%>
					<%--</td>--%>
				  <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[0].billNoSrc" maxlength="50" --%>
					  		<%--type="text" class="inputxt"  readonly="readonly" style="width:120px;"--%>
					               <%----%>
					               <%-->--%>
					  <%--<label class="Validform_label" style="display: none;">源单编号</label>--%>
					<%--</td>--%>
				  <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[0].note" maxlength="32" 
					  		type="text" class="inputxt"  style="width:180px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">备注</label>
					</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tPoOrderentryList)  > 0 }">
		<c:forEach items="${tPoOrderentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 25px;" name="xh">${stuts.index+1 }</div></td>
				<c:if test="${load ne 'detail'}">
				<td align="center" bgcolor="white"><div style="width: 80px;margin-top: -5px"><a name="addTScOrderentryBtn[${stuts.index }]" id="addTScOrderentryBtn[${stuts.index }]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTPoOrderentryBtn(${stuts.index });"></a>
					<a name="delTScOrderentryBtn[${stuts.index }]" id="delTScOrderentryBtn[${stuts.index }]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScOrderentryBtn(${stuts.index });"></a></div></td>
				</c:if>
				<input name="tPoOrderentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tPoOrderentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tPoOrderentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tPoOrderentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tPoOrderentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tPoOrderentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tPoOrderentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tPoOrderentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
					<input name="tPoOrderentryList[${stuts.index }].fidSrc" type="hidden" value="${poVal.fidSrc }"/>
					<input name="tPoOrderentryList[${stuts.index }].idSrc" type="hidden" value="${poVal.idSrc }"/>
					<input name="tPoOrderentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex }">
					<input name="tPoOrderentryList[${stuts.index }].secUnitId" type="hidden" value="${poVal.secUnitId }">
					<input name="tPoOrderentryList[${stuts.index }].basicUnitId" type="hidden" value="${poVal.basicUnitId }">
					<input name="tPoOrderentryList[${stuts.index }].secCoefficient" type="hidden" value="${poVal.secCoefficient }">
					<input name="tPoOrderentryList[${stuts.index }].secQty" type="hidden" value="${poVal.secQty }">
					<input name="tPoOrderentryList[${stuts.index }].stockId" type="hidden" value="${poVal.stockId}">
					<input name="tPoOrderentryList[${stuts.index }].itemId" type="hidden" value="${poVal.itemId}">
				<input name="tPoOrderentryList[${stuts.index }].coefficient" type="hidden" value="${poVal.coefficient}">
				<input name="tPoOrderentryList[${stuts.index }].basicQty" type="hidden" value="${poVal.basicQty}">
					<%--<input name="tPoOrderentryList[${stuts.index }].unitId" type="hidden" value="${poVal.unitId}">--%>
					<input name="tPoOrderentryList[${stuts.index }].amount" type="hidden" value="${poVal.amount}">
					<input name="tPoOrderentryList[${stuts.index }].discountPrice" type="hidden" value="${poVal.discountPrice}">
					<input name="tPoOrderentryList[${stuts.index }].discountAmount" type="hidden" value="${poVal.discountAmount}">
					<input name="tPoOrderentryList[${stuts.index }].price" type="hidden" value="${poVal.price}">
					<input name="tPoOrderentryList[${stuts.index }].cgLimitPrice" value="${poVal.cgLimitPrice}" type="hidden"/>
					<input name="tPoOrderentryList[${stuts.index }].version" type="hidden" value="${poVal.version}">
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[${stuts.index }].itemNo" maxlength="32"
						   type="text" class="inputxt popup-select"  style="width:105px;"
						   onkeypress="keyDownInfo(${stuts.index },'item',event)"
						   onblur="orderListStockBlur('${stuts.index}','itemId','itemNo');"
						   value="${poVal.itemNo }">
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].itemName" maxlength="32"
					  		type="text" class="inputxt" readonly="readonly"  style="width:180px;background-color:rgb(226,226,226)"
					               
					                value="${poVal.itemName }">
					  <label class="Validform_label" style="display: none;">商品名称</label>
				   </td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[${stuts.index }].model" maxlength="32"
						   type="text" class="inputxt" readonly="readonly"  style="width:85px;background-color:rgb(226,226,226)"

						   value="${poVal.model }">
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
				<input name="tPoOrderentryList[${stuts.index }].barCode" maxlength="32"
					   type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color:rgb(226,226,226)"

					   value="${poVal.barCode }">
				<label class="Validform_label" style="display: none;">条码</label>
			</td>
					<td align="left" bgcolor="white">
						<input name="tPoOrderentryList[${stuts.index }].stockName" maxlength="32"
							   type="text" class="inputxt popup-select"  style="width:65px;"
							   onblur="orderListStockBlur('${stuts.index}','stockId','stockName');"
							   onkeypress="keyDownInfo(${stuts.index },'stock',event)"
							   value="${poVal.stockName }" >
						<label class="Validform_label" style="display: none;">仓库</label>
					</td>
				   <td align="left" bgcolor="white">
					  	<input id="unitId[${stuts.index }]" name="tPoOrderentryList[${stuts.index }].unitId" maxlength="32"
					  		type="text"
							   class="easyui-combobox" data-options="valueField: 'id',textField: 'text',width:84,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"
							   style="width:80px;"
					                value="${poVal.unitId }">
					  <label class="Validform_label" style="display: none;">单位</label>
				   </td>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].basicUnitId" maxlength="32" --%>
					  		<%--type="text" class="inputxt"  hidden="hidden" style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.basicUnitId }">--%>
					  <%--<label class="Validform_label" style="display: none;">基本单位</label>--%>
				   <%--</td>--%>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].qty" maxlength="20" 
					  		type="text" class="inputxt"  style="width:65px;" datatype="vInt"
					               <c:if test="${checkState eq 2}">onchange="editQtyChange(${stuts.index })"</c:if>
								   <%--<c:if test="${checkState ne 2}">onchange="setAllQty(${stuts.index })"</c:if>--%>
					                value="${poVal.qty }">
					  <label class="Validform_label" style="display: none;">数量</label>
				   </td>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].secCoefficient" maxlength="20" --%>
					  		<%--type="text" class="inputxt" hidden="hidden" style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.secCoefficient }">--%>
					  <%--<label class="Validform_label" style="display: none;">辅助换算率</label>--%>
				   <%--</td>--%>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].secQty" maxlength="20" --%>
					  		<%--type="text" class="inputxt" hidden="hidden" style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.secQty }">--%>
					  <%--<label class="Validform_label" style="display: none;">辅助数量</label>--%>
				   <%--</td>--%>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].coefficient" maxlength="20" --%>
					  		<%--type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color:rgb(226,226,226)"--%>
					               <%----%>
					                <%--value="${poVal.coefficient }">--%>
					  <%--<label class="Validform_label" style="display: none;">换算率</label>--%>
				   <%--</td>--%>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].basicQty" maxlength="20" --%>
					  		<%--type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color:rgb(226,226,226)"--%>
							   <%--&lt;%&ndash;onchange="setBasicQty(${stuts.index })"&ndash;%&gt;--%>
					               <%----%>
					                <%--value="${poVal.basicQty }">--%>
					  <%--<label class="Validform_label" style="display: none;">基本数量</label>--%>
				   <%--</td>--%>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[${stuts.index }].taxPriceEx" maxlength="20"
						   type="text" class="inputxt"  style="width:70px;" datatype="num" ignoer="ignoer"
						   <%--onchange="onPriceChange(${stuts.index })"--%>

						   value="${poVal.taxPriceEx }">
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[${stuts.index }].taxAmountEx" maxlength="20"
						   type="text" class="inputxt"  style="width:70px;" datatype="num" ignoer="ignoer"
						   <%--onchange="onAmountChange(${stuts.index })"--%>

						   value="${poVal.taxAmountEx }">
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].price" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.price }">--%>
					  <%--<label class="Validform_label" style="display: none;">单价</label>--%>
				   <%--</td>--%>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].amount" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.amount }">--%>
					  <%--<label class="Validform_label" style="display: none;">不含税金额</label>--%>
				   <%--</td>--%>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].discountRate" maxlength="20" 
					  		type="text" class="inputxt"  style="width:80px;" datatype="num" ignoer="ignoer"
							   <%--onchange="ondisRateChange(${stuts.index })"--%>
					               
					                value="${poVal.discountRate }">
					  <label class="Validform_label" style="display: none;">折扣率（%）</label>
				   </td>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].discountPrice" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.discountPrice }">--%>
					  <%--<label class="Validform_label" style="display: none;">不含税折后单价</label>--%>
				   <%--</td>--%>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].discountAmount" maxlength="20" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.discountAmount }">--%>
					  <%--<label class="Validform_label" style="display: none;">不含税折后金额</label>--%>
				   <%--</td>--%>

				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].taxPrice" maxlength="20"
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)"

					                value="${poVal.taxPrice }">
					  <label class="Validform_label" style="display: none;">折后单价</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].inTaxAmount" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;"onchange="setAllAmount()"
							   <%--onchange="onTaxAmountChange(${stuts.index })"--%>
							   datatype="num" ignoer="ignoer"
					                value="${poVal.inTaxAmount }">
					  <label class="Validform_label" style="display: none;">折后金额</label>
				   </td>
				<td align="left" bgcolor="white">
					<input name="tPoOrderentryList[${stuts.index }].taxRate" maxlength="20"
						   type="text" class="inputxt"  style="width:70px;"
						   <%--onchange="onRateChange(${stuts.index })"--%>
						   datatype="num" ignoer="ignoer"
						   value="${poVal.taxRate }">
					<label class="Validform_label" style="display: none;">税率（%）</label>
				</td>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].taxAmount" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					                value="${poVal.taxAmount }">
					  <label class="Validform_label" style="display: none;">税额</label>
				   </td>
				   <td align="left" bgcolor="white">
					   <input  name="tPoOrderentryList[${stuts.index }].jhDate" type="text" style="width: 100px"
							   class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${poVal.jhDate }' type="date" pattern="yyyy-MM-dd"/>' >
					  <label class="Validform_label" style="display: none;">收货日期</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<%--<input name="tPoOrderentryList[${stuts.index }].freeGifts" maxlength="1" --%>
					  		<%--type="text" class="inputxt"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.freeGifts }">--%>
							<t:dictSelect field="tPoOrderentryList[${stuts.index }].freeGifts" type="list" showDefaultOption="false"
										  typeGroupCode="sf_01"
										  width="70px"
										  defaultVal="${poVal.freeGifts }"
										  hasLabel="false"
										  extendJson="{datatype:select1,onChange:setPrice(${stuts.index })}"
										  title="赠品标记" ></t:dictSelect>
					  <label class="Validform_label" style="display: none;">赠品标记</label>
				   </td>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].stockQty" maxlength="20" 
					  		type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
					               
					                value="${poVal.stockQty }">
					  <label class="Validform_label" style="display: none;">收货数量</label>
				   </td>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].classIDSrc" maxlength="10" --%>
					  		<%--type="text" class="inputxt" readonly="readonly"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.classIDSrc }">--%>
					  <%--<label class="Validform_label" style="display: none;">源单类型</label>--%>
				   <%--</td>--%>
				   <%--<td align="left">--%>
					  	<%--<input name="tPoOrderentryList[${stuts.index }].billNoSrc" maxlength="50" --%>
					  		<%--type="text" class="inputxt" readonly="readonly"  style="width:120px;"--%>
					               <%----%>
					                <%--value="${poVal.billNoSrc }">--%>
					  <%--<label class="Validform_label" style="display: none;">源单编号</label>--%>
				   <%--</td>--%>
				   <td align="left" bgcolor="white">
					  	<input name="tPoOrderentryList[${stuts.index }].note" maxlength="32" 
					  		type="text" class="inputxt"  style="width:180px;"
					               
					                value="${poVal.note }">
					  <label class="Validform_label" style="display: none;">备注</label>
				   </td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
