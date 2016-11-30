<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$('a[name^="addTScDrpRstockbillentryBtn"]').linkbutton({
    iconCls: 'icon-add',
    plain: true
	});  
	$('a[name^="delTScDrpRstockbillentryBtn"]').linkbutton({
    iconCls: 'icon-remove',
    plain: true
  });
  function clickAddTScDrpRstockbillentryBtn(rowIndex) {
    var tr = $("#add_tScDrpRstockbillentry_table_template tr").clone();
    $("#add_tScDrpRstockbillentry_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScDrpRstockbillentry_table',rowIndex,'tScDrpRstockbillentryList');
  }

  function clickDelTScDrpRstockbillentryBtn(rowIndex) {
    var len = $("#add_tScDrpRstockbillentry_table").find(">tr").length;
    $("#add_tScDrpRstockbillentry_table").find(">tr").eq(rowIndex).remove();
		if(rowIndex==0 && len == 1){//如果只有一行且删除这一行则删除后补一空行
      var tr = $("#add_tScDrpRstockbillentry_table_template tr").clone();
      $("#add_tScDrpRstockbillentry_table").append(tr);
    }
	  resetTrNum('add_tScDrpRstockbillentry_table',rowIndex,'tScDrpRstockbillentryList');
  }

    $(document).ready(function(){
    	$(".datagrid-toolbar").parent().css("width","auto");
    	if(location.href.indexOf("load=detail")!=-1){
			$(":input").attr("disabled","true");
			$(".datagrid-toolbar").hide();
		}
		//将表格的表头固定
	    $("#tScDrpRstockbillentry_table").createhftable({
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
		$("#add_tScDrpRstockbillentry_table tr").each(function(i,data){
			$('input[name="tScDrpRstockbillentryList[' + i+ '].unitId"]').combobox({
				width:'64',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false
			});
			/*$('input[name="tScDrpRstockbillentryList[' + i+ '].basicUnitId"]').combobox({
				width:'64',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				disabled:true
			});
			$('input[name="tScDrpRstockbillentryList[' + i+ '].secUnitId"]').combobox({
				width:'64',
				valueField:'id',
				textField:'text',
				panelHeight:'auto',
				editable:false,
				disabled:true
			});*/
		});
		if(${fn:length(tScDrpRstockbillentryList) <= 0}){
			setFunctionInfo(0,"tScDrpRstockbillentryList");
			var billDate = $("#date").val();
			$("input[name='tScDrpRstockbillentryList[0].kfDate']").val(billDate);
			$("select[name='tScDrpRstockbillentryList[0].kfDateType']").attr("onChange","setPeriodDate(0,'tScDrpRstockbillentryList')");
		}else{
			var $tbody = $("#tScDrpRstockbillentry_table");
			var length = $tbody[0].rows.length;
			rowIndex = length - 1;
			for(var j=0; j > length; j++){
				setFunctionInfo(j,"tScDrpRstockbillentryList");
			}
		}
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
			}else if (name == "batchNo") {
				selectInventoryInfo(index,'tScDrpRstockbillentryList');
			}
		}
	}
</script>
<table border="0" cellpadding="2" cellspacing="1" totalRow="true" id="tScDrpRstockbillentry_table" style="background-color: #cbccd2">
	<tr bgcolor="#E6E6E6" style="color: white; height:24px;">
		<td align="center" bgcolor="#476f9a">序号</td>
		<td align="center" bgcolor="#476f9a">操作</td>
		<td align="center" bgcolor="#476f9a">商品编号<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">商品名称<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">规格</td>
		<td align="center" bgcolor="#476f9a">条形码</td>
		<td align="center" bgcolor="#476f9a">仓库<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">批号</td>
		<td align="center" bgcolor="#476f9a">单位<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a" total="true">数量<span style="color: red">*</span></td>
		<%--<td align="center" bgcolor="#476f9a">基本单位<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">换算率<span style="color: red">*</span></td>
		<td align="center" bgcolor="#476f9a">基本数量<span style="color: red">*</span></td>--%>
		<%--<td align="center" bgcolor="#476f9a">辅助单位</td>--%>
		<%--<td align="center" bgcolor="#476f9a">辅助换算率</td>
		<td align="center" bgcolor="#476f9a">辅助数量</td>--%>
		<td align="center" bgcolor="#476f9a">单价</td>
		<td align="center" bgcolor="#476f9a" total="true">金额</td>
		<td align="center" bgcolor="#476f9a">折扣率(%)</td>
		<td align="center" bgcolor="#476f9a">折后单价</td>
		<td align="center" bgcolor="#476f9a" total="true">折后金额</td>
		<%--<td align="center" bgcolor="#476f9a" total="true">重量</td>--%>
		<td align="center" bgcolor="#476f9a">税率(%)</td>
		<%--<td align="center" bgcolor="#476f9a">不含税单价</td>
		<td align="center" bgcolor="#476f9a" total="true">不含税金额</td>
		<td align="center" bgcolor="#476f9a" >不含税折后单价</td>
		<td align="center" bgcolor="#476f9a" total="true">不含税折后金额</td>--%>
		<td align="center" bgcolor="#476f9a" total="true">税额</td>
		<td align="center" bgcolor="#476f9a">生产日期</td>
		<td align="center" bgcolor="#476f9a">保质期</td>
		<td align="center" bgcolor="#476f9a">保质期类型</td>
		<td align="center" bgcolor="#476f9a">有效期至</td>
		<td align="center" bgcolor="#476f9a">源单编号</td>
		<td align="center" bgcolor="#476f9a">订单编号</td>
		<td align="center" bgcolor="#476f9a">备注</td>
	</tr>
	<tbody id="add_tScDrpRstockbillentry_table">	
	<c:if test="${fn:length(tScDrpRstockbillentryList)  <= 0 }">
		<tr>
			<td align="center" bgcolor="#F6FCFF">
				<input name="tScDrpRstockbillentryList[0].id" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].createName" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].indexNumber" value="1" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].createBy" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].createDate" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].updateName" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].updateBy" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].updateDate" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].interId" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].stockId" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].itemId" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].classIDSrc" type="hidden"/>
				<%--<input name="tScDrpRstockbillentryList[0].weight" type="hidden"/>--%>
				<input name="tScDrpRstockbillentryList[0].idSrc" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].interIdSrc" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].interIdOrder" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].idOrder" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].kfDateType" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].secQty" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].basicUnitId" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].secUnitId" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].secCoefficient" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].price" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].amount" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].discountPrice" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].discountAmount" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].basicQty" type="hidden"/>
				<input name="tScDrpRstockbillentryList[0].coefficient" type="hidden"/>
				<input id="tScDrpRstockbillentryList[0].itemWeight" name="tScDrpRstockbillentryList[0].itemWeight" type="hidden"/>
				<div style="width: 25px;background-color: white;" name="xh">1</div>
			</td>
			<td align="center" bgcolor="white">
			  <div style="width: 80px;background-color: white;">
				<a name="addTScDrpRstockbillentryBtn[0]" id="addTScDrpRstockbillentryBtn[0]" href="#"  onclick="clickAddTScDrpRstockbillentryBtn(0);"></a>
				<a name="delTScDrpRstockbillentryBtn[0]" id="delTScDrpRstockbillentryBtn[0]" href="#"  onclick="clickDelTScDrpRstockbillentryBtn(0);"></a>
			  </div>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].number" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo(0,'item',event)" datatype="*" />
				<label class="Validform_label" style="display: none;">商品编号</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].name" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" datatype="*" readonly/>
				<label class="Validform_label" style="display: none;">名称</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].model" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">规格</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].barCode" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">条形码</label>
			</td>
			<td align="left" bgcolor="white">
				<input id="tScDrpRstockbillentryList[0].stockName" name="tScDrpRstockbillentryList[0].stockName" maxlength="32" type="text" class="inputxt popup-select" onkeypress="keyDownInfo(0,'stock',event)" style="width:120px;" datatype="*"/>
				<label class="Validform_label" style="display: none;">仓库</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].batchNo" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">批号</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].unitId" id="tScDrpRstockbillentryList[0].unitId" style="width:60px;"/>
				<label class="Validform_label" style="display: none;">单位 </label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].qty" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" onblur="changePrice(0)"/>
				<label class="Validform_label" style="display: none;">数量</label>
			</td>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0]." id="tScDrpRstockbillentryList[0].basicUnitId" style="width:60px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">基本单位 </label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].coefficient" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">换算率</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].basicQty" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);"  datatype="num" ignore="ignore" readonly/>
				<label class="Validform_label" style="display: none;">基本数量 </label>
			</td>--%>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].secUnitId" id="tScDrpRstockbillentryList[0].secUnitId" style="width:60px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">辅助单位</label>
			</td>--%>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].secCoefficient" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">辅助换算率</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].secQty" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">辅助数量</label>
			</td>--%>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" />
				<label class="Validform_label" style="display: none;">单价</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" />
				<label class="Validform_label" style="display: none;">金额</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].discountRate" maxlength="32" type="text" class="inputxt" value="100" style="width:120px;"  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">折扣率(%)</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].taxPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">折后单价</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" onchange="setAllAmount()"/>
				<label class="Validform_label" style="display: none;">折后金额</label>
			</td>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].weight" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">重量</label>
			</td>--%>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].taxRate" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="0" datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">税率(%)</label>
			</td>
			<%--<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].price" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">不含税单价</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">不含税金额</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">不含税折后单价</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">不含税折后金额</label>
			</td>--%>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
				<label class="Validform_label" style="display: none;">税额</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].kfDate" maxlength="32" type="text" class="Wdate"  onClick="WdatePicker()" style="width:80px;" onClick="WdatePicker()"  style="width:80px;" onchange="setPeriodDate(0,'tScDrpRstockbillentryList')"/>
				<label class="Validform_label" style="display: none;">生产日期</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].kfPeriod" maxlength="32" type="text" class="inputxt"  style="width:120px;"/>
				<label class="Validform_label" style="display: none;">保质期</label>
			</td>
			<td align="left" bgcolor="white">
				<%--<input name="tScDrpRstockbillentryList[0].kfDateType" maxlength="32" type="text" class="inputxt"  style="width:120px;" />--%>
					<t:dictSelect field="tScDrpRstockbillentryList[0].kfDateType_" width="100px" type="list"
								  showDefaultOption="true" extendJson="{disabled:disabled}"
								  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
								  title="保质期类型"></t:dictSelect>
				<label class="Validform_label" style="display: none;">保质期类型</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].periodDate" maxlength="32" type="text" class="Wdate"  onClick="WdatePicker()" style="width:80px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">有效期至</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].billNoSrc" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
				<label class="Validform_label" style="display: none;">源单编号</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].billNoOrder" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			  	<label class="Validform_label" style="display: none;">订单编号</label>
			</td>
			<td align="left" bgcolor="white">
				<input name="tScDrpRstockbillentryList[0].note" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
	</c:if>
	<c:if test="${fn:length(tScDrpRstockbillentryList)  > 0 }">
		<c:forEach items="${tScDrpRstockbillentryList}" var="poVal" varStatus="stuts">
			<tr>
				<td align="center" bgcolor="#F6FCFF">
					<input name="tScDrpRstockbillentryList[${stuts.index}].id" type="hidden" value="${poVal.id}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].createName" value="${poVal.createName}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].indexNumber" value="${poVal.indexNumber}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].createBy" value="${poVal.createBy}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].createDate" value="${poVal.createDate}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].updateName" value="${poVal.updateName}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].updateBy" value="${poVal.updateBy}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].updateDate" value="${poVal.updateDate}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].interId" value="${poVal.interId}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].stockId" value="${poVal.stockId}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].itemId" value="${poVal.itemId}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].classIDSrc"  value="${poVal.classIDSrc}" type="hidden"/>
					<%--<input name="tScDrpRstockbillentryList[${stuts.index}].weight"  value="${poVal.weight}" type="hidden"/>--%>
						<%--<input name="tScDrpRstockbillentryList[${stuts.index}].unitId" type="hidden"/>--%>
						<%--<input name="tScDrpRstockbillentryList[${stuts.index}].basicUnitId" type="hidden"/>--%>
						<%--<input name="tScDrpRstockbillentryList[${stuts.index}].secUnitId" type="hidden"/>--%>
					<input name="tScDrpRstockbillentryList[${stuts.index}].idSrc" value="${poVal.idSrc}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].interIdSrc" value="${poVal.interIdSrc}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].interIdOrder" value="${poVal.interIdOrder}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].idOrder" value="${poVal.idOrder}" type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].kfDateType" value="${poVal.kfDateType}"  type="hidden"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].secQty" type="hidden" value="${poVal.secQty}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].basicUnitId" type="hidden" value="${poVal.basicUnitId}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].secUnitId" type="hidden" value="${poVal.secUnitId}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].secCoefficient" type="hidden" value="${poVal.secCoefficient}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].price" type="hidden" value="${poVal.price}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].amount" type="hidden" value="${poVal.amount}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].discountPrice" type="hidden" value="${poVal.discountPrice}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].discountAmount" type="hidden" value="${poVal.discountAmount}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].basicQty" type="hidden" value="${poVal.basicQty}"/>
					<input name="tScDrpRstockbillentryList[${stuts.index}].coefficient" type="hidden" value="${poVal.coefficient}"/>
					<input id="tScDrpRstockbillentryList[${stuts.index}].itemWeight" name="tScDrpRstockbillentryList[${stuts.index}].itemWeight" type="hidden"/>
					<div style="width: 25px;background-color: white;" name="xh">1</div>
				</td>
				<td align="center" bgcolor="white">
					<div style="width: 80px;background-color: white;">
						<a name="addTScDrpRstockbillentryBtn[${stuts.index}]" id="addTScDrpRstockbillentryBtn[${stuts.index}]" href="#"  onclick="clickAddTScDrpRstockbillentryBtn(${stuts.index});"></a>
					</div>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].number" value="${poVal.number}" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo(${stuts.index},'item',event)" datatype="*" />
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].name"  value="${poVal.name}"  maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);"/>
					<label class="Validform_label" style="display: none;">名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].model"  value="${poVal.model}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].barCode" value="${poVal.barCode}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="tScDrpRstockbillentryList[${stuts.index}].stockName" value="${poVal.stockName}" name="tScDrpRstockbillentryList[${stuts.index}].stockName" maxlength="32" type="text" class="inputxt popup-select" onkeypress="keyDownInfo(${stuts.index},'stock',event)" style="width:120px;" datatype="*"/>
					<label class="Validform_label" style="display: none;">仓库</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].batchNo" value="${poVal.batchNo}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index }].unitId" id="tScDrpRstockbillentryList[${stuts.index }].unitId" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'" value="${poVal.unitId}" style="width:60px;"/>
					<label class="Validform_label" style="display: none;">单位 </label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].qty" value="${poVal.qty}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" onblur="changePrice(${stuts.index })" />
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index }].basicUnitId" id="tScDrpRstockbillentryList[${stuts.index }].basicUnitId" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'" value="${poVal.basicUnitId}" style="width:60px;"/>
					<label class="Validform_label" style="display: none;">基本单位 </label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].coefficient" value="${poVal.coefficient}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].basicQty" value="${poVal.basicQty}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);"  readonly/>
					<label class="Validform_label" style="display: none;">基本数量 </label>
				</td>--%>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index }].secUnitId" value="${poVal.secUnitId}" id="tScDrpRstockbillentryList[${stuts.index }].secUnitId" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'" value="${poVal.secUnitId}" style="width:60px;"/>
					<label class="Validform_label" style="display: none;">辅助单位</label>
				</td>--%>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].secCoefficient" value="${poVal.secCoefficient}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">辅助换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].secQty" value="${poVal.secQty}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">辅助数量</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].taxPriceEx"  value="${poVal.taxPriceEx}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" />
					<label class="Validform_label" style="display: none;">单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].taxAmountEx" value="${poVal.taxAmountEx}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" />
					<label class="Validform_label" style="display: none;">金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].discountRate" value="${poVal.discountRate}" maxlength="32" type="text" class="inputxt" value="100" style="width:120px;"  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">折扣率(%)</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].taxPrice" value="${poVal.taxPrice}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].inTaxAmount"  value="${poVal.inTaxAmount}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" onchange="setAllAmount()"/>
					<label class="Validform_label" style="display: none;">折后金额</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].weight" value="${poVal.weight}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">重量</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].taxRate" value="${poVal.taxRate}" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="0" datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">税率(%)</label>
				</td>
				<%--<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].price" value="${poVal.price}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">不含税单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].amount" value="${poVal.amount}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">不含税金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].discountPrice" value="${poVal.discountPrice}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">不含税折后单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].discountAmount" value="${poVal.discountAmount}" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">不含税折后金额</label>
				</td>--%>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].taxAmount" value="${poVal.taxAmount}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
					<label class="Validform_label" style="display: none;">税额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].kfDate" value="<fmt:formatDate value='${poVal.kfDate}' type="date" pattern="yyyy-MM-dd"/>" maxlength="32" type="text" class="Wdate"  onClick="WdatePicker()" style="width:80px;" onClick="WdatePicker()"  style="width:80px;" onchange="setPeriodDate(0,'tScDrpRstockbillentryList')"/>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].kfPeriod" value="${poVal.kfPeriod}" maxlength="32" type="text" class="inputxt"  style="width:120px;"/>
					<label class="Validform_label" style="display: none;">保质期</label>
				</td>
				<td align="left" bgcolor="white">
						<%--<input name="tScDrpRstockbillentryList[${stuts.index}].kfDateType" maxlength="32" type="text" class="inputxt"  style="width:120px;" />--%>
					<t:dictSelect field="tScDrpRstockbillentryList[${stuts.index}].kfDateType_" width="100px" type="list"
								  showDefaultOption="true" extendJson="{disabled:disabled}"
								  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="${poVal.kfDateType}" hasLabel="false"
								  title="保质期类型"></t:dictSelect>
					<label class="Validform_label" style="display: none;">保质期类型</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].periodDate" value="<fmt:formatDate value='${poVal.periodDate}' type="date" pattern="yyyy-MM-dd"/>" maxlength="32" type="text" class="Wdate"  onClick="WdatePicker()" style="width:80px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">有效期至</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].billNoSrc" value="${poVal.billNoSrc}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">源单编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].billNoOrder" value="${poVal.billNoOrder}" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
					<label class="Validform_label" style="display: none;">订单编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScDrpRstockbillentryList[${stuts.index}].note" value="${poVal.note}" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</c:forEach>
	</c:if>	
	</tbody>
</table>
