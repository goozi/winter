<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScIcInitialentryBtn"]').linkbutton({
		iconCls: 'icon-add',
		plain: true
	});
	$('a[name^="delTScIcInitialentryBtn"]').linkbutton({
		iconCls: 'icon-remove',
		plain: true
	});
	function clickAddTScIcInitialentryBtn(rowIndex) {
		var tr = $("#add_tScIcInitialentry_table_template tr").clone();
		$("#add_tScIcInitialentry_table tr").eq(rowIndex).after(tr);
		resetTrNum('add_tScIcInitialentry_table',rowIndex);
	}

	function clickDelTScIcInitialentryBtn(rowIndex) {
		var len = $("#add_tScIcInitialentry_table").find(">tr").length;
		$("#add_tScIcInitialentry_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
			var tr = $("#add_tScIcInitialentry_table_template tr").clone();
			$("#add_tScIcInitialentry_table").append(tr);
		}
		resetTrNum('add_tScIcInitialentry_table',rowIndex);
	}

	$(document).ready(function(){
		$(".datagrid-toolbar").parent().css("width","auto");
		if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
		$("#tScIcInitialentry_table").createhftable({
			height: (h-50)+'px',
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
			$("input[name='tScIcInitialentryList[#index#].stockId']").val(stockId);
			$("input[name='tScIcInitialentryList[#index#].stockName']").val(stockName);
		}
		if(${fn:length(tScIcInitialentryList)  <= 0 }){

			checkAllowAddLine(0);//判断是否允许增行操作
			var billDate = $("#date").val();
			//var taxRate = $("#defaultTaxRate").val();
			//$("input[name='tScIcInitialentryList[0].taxRate']").val(taxRate);
			$("input[name='tScIcInitialentryList[0].kfDate']").val(billDate);
			/*$("input[name='tScIcInitialentryList[0].stockName']").keypress(function (e) {
			 if (e.keyCode == 13) {
			 selectStockDialog(0);
			 }
			 });*/
			setFunctionInfo(0);
		}else{
			var checkState = $("#checkState").val();
			var $tbody = $("#tScIcInitialentry_table");
			var length = $tbody[0].rows.length;
			rowIndex = length-1;
			for(var j=0 ; j<length ; j++){
				//编辑行金额函数配置
				setFunctionInfo(j);
				checkAllowAddLine(j);//判断是否允许增行操作
				//var unitId = $("#unitId\\["+j+"\\]").val();
				var itemId = $("input[name='tScIcInitialentryList["+j+"].itemId']").val();
				var itemNo = $("input[name='tScIcInitialentryList["+j+"].itemNo']").val();
				setValOldIdAnOldName($("input[name='tScIcInitialentryList["+j+"].itemNo']"),itemId,itemNo);
				/*var stockId = $("input[name='tScIcInitialentryList["+j+"].stockId']").val();
				 if(stockId){
				 var stockName = $("input[name='tScIcInitialentryList["+j+"].stockName']").val();
				 setValOldIdAnOldName($("input[name='tScIcInitialentryList["+j+"].stockName']"),stockId,stockName);
				 }
				 var entryOrderId = $("input[name='tScIcInitialentryList["+j+"].entryIdOrder']").val();
				 if(entryOrderId){
				 $("select[name='tScIcInitialentryList["+j+"].freeGifts_']").attr("disabled","disabled");
				 $("select[name='tScIcInitialentryList["+j+"].freeGifts_']").css("background-color","rgb(226,226,226)");
				 }*/
				var batchManager = $("input[name='tScIcInitialentryList["+j+"].batchManager']").val();
				if("N"==batchManager){
					$("input[name='tScIcInitialentryList[" + j + "].batchNo']").attr("readonly","readonly");
					$("input[name='tScIcInitialentryList[" + j + "].batchNo']").css("background-color","rgb(226,226,226)");
					$("input[name='tScIcInitialentryList[" + j + "].batchNo']").removeAttr("datatype");
				}else{
					$("input[name='tScIcInitialentryList[" + j + "].batchNo']").attr("onkeypress","keyDownInfo('"+j+"','batchNo')");
				}
				var isKfPeriod = $("input[name='tScIcInitialentryList["+j+"].isKFPeriod']").val();
				//if("N"==isKfPeriod) {
				if("Y" != isKfPeriod) {
					//生产日期
					$("input[name='tScIcInitialentryList[" + j + "].kfDate']").attr("readonly","readonly");
					$("input[name='tScIcInitialentryList[" + j + "].kfDate']").css("background-color","rgb(226,226,226)");
					$("input[name='tScIcInitialentryList[" + j + "].kfDate']").attr("onClick","");
					$("input[name='tScIcInitialentryList[" + j + "].kfDate']").val("");
					//保质期
					$("input[name='tScIcInitialentryList[" + j + "].kfPeriod']").attr("readonly","readonly");
					$("input[name='tScIcInitialentryList[" + j + "].kfPeriod']").css("background-color","rgb(226,226,226)");
					//保质期类型
					$("select[name='tScIcInitialentryList[" + j + "].kfDateType']").attr("disabled", "disabled");
					$("select[name='tScIcInitialentryList[" + j + "].kfDateType']").css("background-color", "rgb(226,226,226)");
					$("select[name='tScIcInitialentryList[" + j + "].kfDateType_']").attr("disabled", "disabled");
					$("select[name='tScIcInitialentryList[" + j + "].kfDateType_']").css("background-color", "rgb(226,226,226)");
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
									$("input[name='tScIcInitialentryList["+rowIndex+"].barCode']").val(barCode);
									$("input[name='tScIcInitialentryList["+rowIndex+"].coefficient']").val(cofficient);
									$("input[name='tScIcInitialentryList["+rowIndex+"].coefficient']").trigger("propertychange");
									$("input[name='tScIcInitialentryList["+rowIndex+"].secCoefficient']").trigger("propertychange");
								}
							});
						}
					}
				})
				var idSrc = $("input[name='tScIcInitialentryList["+j+"].idSrc']").val();
				if(idSrc){
					$("input[name='tScIcInitialentryList["+j+"].itemNo']").attr("readonly","readonly");
					$("#unitId\\["+j+"\\]").combobox({
						disabled : true
					});
					$("#itemName").attr("readonly","readonly");
				}
			}
		}


		//debugger;
		var tableId = 'add_tScIcInitialentry_table';
		$tbody = $("#"+tableId+"");
		$tbody.find('>tr').each(function(i){
			$(':input', this).each(function(){
				var $this = $(this), name = $this.attr('name');
				if(name!=null){//name不为空
					if(name.indexOf(".qty") > -1){
						$this.attr("onchange","onPriceChange("+i +  ")");
					}else if(name.indexOf(".smallQty") > -1){
						$this.attr("onchange","onPriceChange("+i +  ")");
					}else if(name.indexOf(".costPrice") > -1){
						$this.attr("onchange","onPriceChange("+i +  ")");
						//}else if(name.indexOf("kfDateType") > -1){
						//	$this.attr("onchange","setPeriodDate("+i+")");
					}
				}
			});
		});
	});
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
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScIcInitialentry_table" style="background-color: #cbccd2" totalRow="true">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" width="30px" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
				  <td align="center" bgcolor="#476f9a">
						商品编号<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
					  	商品名称<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
					  	商品规格
				  </td>
				  <td align="center" bgcolor="#476f9a">
					  	条形码
				  </td>
			  	  <td align="center" bgcolor="#476f9a">
					  	单位<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						箱数<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						散数<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
						换算率<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						数量<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a">
						成本单价<span style="color: red">*</span>
				  </td>
				  <td align="center" bgcolor="#476f9a" total="true">
						成本金额<span style="color: red">*</span>
				  </td>
				<td align="center" bgcolor="#476f9a">
					批号
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
				  <td align="center" bgcolor="#476f9a">
						备注
				  </td>
	</tr>
	<tbody id="add_tScIcInitialentry_table">	
	<c:if test="${fn:length(tScIcInitialentryList)  <= 0 }">
			<tr>
				<td align="center" bgcolor="#F6FCFF"><div style="width: 30px;background-color: white;" name="xh">1</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;">
            <a name="addTScIcInitialentryBtn[0]" id="addTScIcInitialentryBtn[0]" href="#"
               onclick="clickAddTScIcInitialentryBtn(0);"></a>
			  <a name="delTScIcInitialentryBtn[0]" id="delTScIcInitialentryBtn[0]" href="#"
				 onclick="clickDelTScIcInitialentryBtn(0);"></a>
          </div>
				</td>
					<input name="tScIcInitialentryList[0].id" type="hidden"/>
					<input name="tScIcInitialentryList[0].createName"  type="hidden"/>
					<input name="tScIcInitialentryList[0].createBy" type="hidden"/>
					<input name="tScIcInitialentryList[0].createDate" type="hidden"/>
					<input name="tScIcInitialentryList[0].updateName" type="hidden"/>
					<input name="tScIcInitialentryList[0].updateBy" type="hidden"/>
					<input name="tScIcInitialentryList[0].updateDate" type="hidden"/>
					<input name="tScIcInitialentryList[0].fid" type="hidden"/>
					<input name="tScIcInitialentryList[0].indexnumber" type="hidden" value="1"/>
					<input name="tScIcInitialentryList[0].secUnitId" type="hidden"/>
					<input name="tScIcInitialentryList[0].basicUnitId" type="hidden"/>
					<input name="tScIcInitialentryList[0].secCoefficient" type="hidden"/>
					<input name="tScIcInitialentryList[0].secQty" type="hidden"/>
					<input name="tScIcInitialentryList[0].stockId" type="hidden"/>
					<input name="tScIcInitialentryList[0].itemId" type="hidden"/>
					<input name="tScIcInitialentryList[0].kfDateType" type="hidden"/>
					<input name="tScIcInitialentryList[0].batchManager" type="hidden" value=""/>
					<input name="tScIcInitialentryList[0].isKFPeriod" type="hidden" value=""/>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].itemNo" maxlength="32"
						   type="text" class="inputxt popup-select" style="width:105px;"
						   datatype="*" onkeypress="keyDownInfo('0','item',event)"
						   onblur="orderListStockBlur('0','itemId','itemNo');"
					>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].itemName" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226)"
						   datatype="*" readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].model" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)" readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">商品规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].barCode" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:65px;background-color:rgb(226,226,226)" readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="unitId[0]" name="tScIcInitialentryList[0].unitId" maxlength="32"
						   type="text" class="easyui-combobox"
						   data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false" style="width:50px;"
					>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].qty" maxlength="20"
						   type="text" class="inputxt" style="width:70px;"
						   datatype="num" value="0"
					>
					<label class="Validform_label" style="display: none;">箱数</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].smallQty" maxlength="20"
						   type="text" class="inputxt" style="width:120px;" datatype="*" value="0"/>
					<label class="Validform_label" style="display: none;">散数</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].coefficient" maxlength="20"
						   type="text" class="inputxt" style="width:120px;background-color:rgb(226,226,226)" readonly="readonly" datatype="*"/>
					<label class="Validform_label" style="display: none;">换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].basicQty" maxlength="20"
						   type="text" class="inputxt" style="width:120px;background-color:rgb(226,226,226)" datatype="vfloat" value="${poVal.basicQtystr }" ignore="ignore" errormsg="箱数或散数不能全为零" readonly="readonly"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].costPrice" maxlength="20"
						   type="text" class="inputxt" style="width:70px;"
						   datatype="float"
					>
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].costAmount" maxlength="20"
						   type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)"
						   datatype="num" ignore="ignore" readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">成本金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].batchNo" maxlength="50"
						   type="text" class="inputxt" style="width:80px;"
						   datatype="*"
					>
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].kfDate" maxlength="20"
						   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
						   onchange="setPeriodDate('0')"
					>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].kfPeriod" maxlength="10"
						   type="text" class="inputxt" style="width:50px;"
						   onchange="setPeriodDate('0')"
					>
					<label class="Validform_label" style="display: none;">保质期</label>
				</td>
				<td align="left" bgcolor="white">
					<%--<t:dictSelect field="tScIcInitialentryList[0].kfDateType" width="70" type="list"--%>
								  <%--showDefaultOption="true"--%>
								  <%--extendJson="{onChange:setPeriodDate(0)}"--%>
								  <%--typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false" title="保质期类型"></t:dictSelect>--%>
						<t:dictSelect field="tScIcInitialentryList[0].kfDateType_" width="70px" type="list" showDefaultOption="true" extendJson="{onChange:setPeriodDate(0),disabled:disabled}"
									  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"  title="保质期类型"></t:dictSelect>
					<label class="Validform_label" style="display: none;">保质期类型</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].periodDate" maxlength="20"
						   type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)"
						   readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">有效期至</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[0].note" maxlength="255"
						   type="text" class="inputxt" style="width:180px;"/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
	</c:if>
	<c:if test="${fn:length(tScIcInitialentryList)  > 0 }">
		<c:forEach items="${tScIcInitialentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="white"><div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div></td>
				<td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;" checkState="${checkState}" cancellation="${cancellation}">

            <a name="addTScIcInitialentryBtn[${stuts.index}]" id="addTScIcInitialentryBtn[${stuts.index}]" href="#"
               onclick="clickAddTScIcInitialentryBtn(${stuts.index});"></a>
			<%--<c:if test="${stuts.index>0}">--%>
              <a name="delTScIcInitialentryBtn[${stuts.index}]" id="delTScIcInitialentryBtn[${stuts.index}]" href="#"
                 onclick="clickDelTScIcInitialentryBtn(${stuts.index});"></a>
			<%--</c:if>--%>
		  </div>
        </td>
					<input name="tScIcInitialentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
					<input name="tScIcInitialentryList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
					<input name="tScIcInitialentryList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
					<input name="tScIcInitialentryList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
					<input name="tScIcInitialentryList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
					<input name="tScIcInitialentryList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
					<input name="tScIcInitialentryList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
					<input name="tScIcInitialentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
					<input name="tScIcInitialentryList[${stuts.index }].indexnumber" type="hidden" value="${poVal.indexnumber }"/>
					<input name="tScIcInitialentryList[${stuts.index }].secUnitId" type="hidden" value="${poVal.secUnitId }"/>
					<input name="tScIcInitialentryList[${stuts.index }].basicUnitId" type="hidden" value="${poVal.basicUnitId }"/>
					<input name="tScIcInitialentryList[${stuts.index }].secCoefficient" type="hidden" value="${poVal.secCoefficientstr }"/>
					<input name="tScIcInitialentryList[${stuts.index }].secQty" type="hidden" value="${poVal.secQtystr }"/>
					<input name="tScIcInitialentryList[${stuts.index }].stockId" type="hidden" value="${poVal.stockId }"/>
					<input name="tScIcInitialentryList[${stuts.index }].itemId" type="hidden" value="${poVal.itemId }"/>
					<input name="tScIcInitialentryList[${stuts.index }].batchManager" type="hidden" value="${poVal.batchManager }"/>
					<input name="tScIcInitialentryList[${stuts.index }].isKFPeriod" type="hidden" value="${poVal.isKFPeriod }"/>
				    <input name="tScIcInitialentryList[${stuts.index }].kfDateType" type="hidden" value="${poVal.kfDateType }"/>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].itemNo" maxlength="32"
						   type="text" class="inputxt popup-select" style="width:105px;"
						   datatype="*" onkeypress="keyDownInfo('${stuts.index }','item',event)"
						   onblur="orderListStockBlur('${stuts.index }','itemId','itemNo');"
						   value="${poVal.itemNo }"
					>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].itemName" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226)" readonly="readonly"
						   datatype="*"
						   value="${poVal.itemName }"
					>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].model" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)" readonly="readonly"
						   value="${poVal.model }"
					>
					<label class="Validform_label" style="display: none;">商品规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].barCode" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:65px;background-color:rgb(226,226,226)" readonly="readonly"
						   value="${poVal.barCode }"
					>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="unitId[${stuts.index }]" name="tScIcInitialentryList[${stuts.index }].unitId" maxlength="32"
						   type="text" class="easyui-combobox"
						   data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"  style="width:50px;"
						   value="${poVal.unitId }"
					>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].qty" maxlength="20"
						   type="text" class="inputxt" style="width:70px;"
						   datatype="num" value="${poVal.qtystr }"
					>
					<label class="Validform_label" style="display: none;">箱数</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].smallQty" maxlength="20"
						   type="text" class="inputxt" style="width:120px;" datatype="num" value="${poVal.smallQtystr }"/>
					<label class="Validform_label" style="display: none;">散数</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].coefficient" maxlength="20"
						   type="text" class="inputxt" style="width:120px;background-color:rgb(226,226,226)" readonly="readonly" datatype="*" value="${poVal.coefficientstr }"/>
					<label class="Validform_label" style="display: none;">换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].basicQty" maxlength="20"
						   type="text" class="inputxt" style="width:120px;background-color:rgb(226,226,226)" readonly="readonly" datatype="vfloat" value="${poVal.basicQtystr }" ignore="ignore" errormsg="箱数或散数不能全为零"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].costPrice" maxlength="20"
						   type="text" class="inputxt" style="width:70px;"
						   datatype="float" value="${poVal.costPricestr }"
					>
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].costAmount" maxlength="20"
						   type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
						   datatype="num" ignore="ignore" value="${poVal.costAmountstr }"
					>
					<label class="Validform_label" style="display: none;">成本金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].batchNo" maxlength="50"
						   type="text" class="inputxt" style="width:80px;"
						   datatype="*" value="${poVal.batchNo }">
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].kfDate" maxlength="20"
						   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
						   onchange="setPeriodDate('${stuts.index }')" value="<fmt:formatDate value='${poVal.kfDate}' type="date" pattern="yyyy-MM-dd"/>"
					>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].kfPeriod" maxlength="10"
						   type="text" class="inputxt" style="width:50px;"
						   onchange="setPeriodDate('${stuts.index }')" value="${poVal.kfPeriod }"
					>
					<label class="Validform_label" style="display: none;">保质期</label>
				</td>
				<td align="left" bgcolor="white">
					<%--<t:dictSelect field="tScIcInitialentryList[${stuts.index }].kfDateType" width="70" type="list"--%>
								  <%--showDefaultOption="true"--%>
								  <%--extendJson="{onChange:setPeriodDate(${stuts.index })}"--%>
								  <%--typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType }" hasLabel="false" title="保质期类型"></t:dictSelect>--%>
						<t:dictSelect field="tScIcInitialentryList[${stuts.index }].kfDateType_" width="70px" type="list" showDefaultOption="true" extendJson="{onChange:setPeriodDate(${stuts.index }),disabled:disabled}"
									  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType }" hasLabel="false"  title="保质期类型"></t:dictSelect>
					<label class="Validform_label" style="display: none;">保质期类型</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].periodDate" maxlength="20"
						   type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)" readonly="readonly"
						   readonly="readonly" value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>"
					>
					<label class="Validform_label" style="display: none;">有效期至</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[${stuts.index }].note" maxlength="255"
						   type="text" class="inputxt" style="width:180px;" value="${poVal.note }"/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
   			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
