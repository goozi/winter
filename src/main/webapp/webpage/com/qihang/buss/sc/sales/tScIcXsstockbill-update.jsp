<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>销售换货单</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	<script type="text/javascript" src="plug-in/scm/js/computingTools.js"></script>
	<script type="text/javascript">
		var centerBodyHigh;
		var southBodyHight;
		$(document).ready(function () {
			$('#tt').tabs({
				onSelect: function (title) {
					$('#tt .panel-body').css('width', 'auto');
				}
			});
			$(".tabs-wrap").css('width', '100%');
			// 退货单
			$.ajax({
				type: "get",
				url: "tScIcXsstockbillController.do?tScIcXsstockbillentry1List&id=${tScIcXsstockbillPage.id}&load=${load}",
				success:function(data){
					$("#centerBody").html("<div id='centerInfo'>"+data+"</div>");
					centerBodyHigh = $("#centerBody").height();
				}
			});

			// 换货单
			$.ajax({
				type: "get",
				url: "tScIcXsstockbillController.do?tScIcXsstockbillentry2List&id=${tScIcXsstockbillPage.id}&load=${load}",
				success:function(data){
					$("#southBody").html(data);
					southBodyHigh= $("#southBody").height();
				}
			});

			$(".layout-button-up").click(toggleCenter);
			$(".layout-button-down").click(toggleSouth);
			$("#custHeader").dblclick(toggleCenter);
			$("#goodsHeader").dblclick(toggleSouth);

			function toggleCenter(){
				if($("#centerBody").is(":hidden")){
					$("#centerBody").slideDown();
					$("#southBody").height(southBodyHigh );
					$("#tScIcXsstockbillentry2_tablescrolldiv").height(southBodyHigh-50);
				} else {
					$("#centerBody").slideUp();
					$("#southBody").height(centerBodyHigh +southBodyHigh );
					$("#tScIcXsstockbillentry2_tablescrolldiv").height(centerBodyHigh +southBodyHigh-50);
				}
			}

			function toggleSouth(){
				if($("#southBody").is(":hidden")){
					$("#southBody").slideDown();
					$("#centerBody").height(southBodyHigh );
					$("#tScIcXsstockbillentry1_tablescrolldiv").height(southBodyHigh-50);
				} else {
					$("#southBody").slideUp();
					$("#centerBody").height(centerBodyHigh +southBodyHigh  );
					$("#tScIcXsstockbillentry1_tablescrolldiv").height(centerBodyHigh +southBodyHigh-50);
				}
			}
		});
	</script>

	<style>
		/** 页脚样式  **/
		body {
			position: absolute;
			width: 100%;
			height: 100%;
		}

		.footlabel {
			float: left;
			margin-left: 35px;
		}

		.footspan {
			float: left;
			margin-left: 5px;
			margin-right: 5px;
			font-weight: bold;
			color: grey;
			margin-bottom: 5px;
			text-decoration: underline;
		}
		#centerBody{
			overflow: hidden !important;
		}
		#southBody{
			overflow: hidden !important;
		}
		.fullForm {
			width: 100%;;
		}

		.spanBtn {
			background-color: #CCE1F3;
			display: -moz-inline-box;
			display: inline-block;
			width: 20px;
			height: 20px;
			text-align: center;
		}
	</style>
</head>
<body style="overflow-x: hidden;">
<input id="load" value="${load}" type="hidden">
<input id="isCheckNegative" type="hidden" value="${isCheckNegative}">
<input id="clearExt" type="hidden" value="doClearExt">
<t:formvalid formid="formobj" dialog="true" title="销售换货单" tranType="110" tableName="t_sc_ic_xsstockbill" usePlugin="password" layout="table" tiptype="1"
			 action="tScIcXsstockbillController.do?doUpdate" beforeSubmit="checkPriceIsZero" saveTemp="true">
	<div  class="panel-body layout-body" style="height: 20%">
		<input id="id" name="id" type="hidden"
			   value="${tScIcXsstockbillPage.id }">
		<input id="createName" name="createName" type="hidden"
			   value="${tScIcXsstockbillPage.createName }">
		<input id="createBy" name="createBy" type="hidden"
			   value="${tScIcXsstockbillPage.createBy }">
		<input id="createDate" name="createDate" type="hidden"
			   value="${tScIcXsstockbillPage.createDate }">
		<input id="updateName" name="updateName" type="hidden"
			   value="${tScIcXsstockbillPage.updateName }">
		<input id="updateBy" name="updateBy" type="hidden"
			   value="${tScIcXsstockbillPage.updateBy }">
		<input id="updateDate" name="updateDate" type="hidden"
			   value="${tScIcXsstockbillPage.updateDate }">
		<input id="tranType" name="tranType" type="hidden"
			   value="110">
		<input id="billNo" name="billNo" type="hidden"
			   value="${tScIcXsstockbillPage.billNo }">
		<input name="date" type="hidden"
			   value="${tScIcXsstockbillPage.date }">
		<input id="idSrc" name="idSrc" type="hidden"
			   value="${tScIcXsstockbillPage.idSrc }">
		<input id="billNoSrc" name="billNoSrc" type="hidden"
			   value="${tScIcXsstockbillPage.billNoSrc }">
		<input id="checkState" name="checkState" type="hidden"
			   value="${tScIcXsstockbillPage.checkState }">
		<input id="cancellation" name="cancellation" type="hidden"
			   value="${tScIcXsstockbillPage.cancellation }">
		<input id="version" name="version" type="hidden"
			   value="${tScIcXsstockbillPage.version }">
		<input id="checkAmount" name="checkAmount" type="hidden"
			   value="${tScIcXsstockbillPage.checkAmount }">
		<input id="diffAmount" name="diffAmount" type="hidden"
			   value="${tScIcXsstockbillPage.diffAmount }">
		<input id="billerId" name="billerId" type="hidden"
			   value="${tScIcXsstockbillPage.billerId }">
		<input id="sonId" name="sonId" type="hidden"
			   value="${tScIcXsstockbillPage.sonId }">
		<input id="itemId" name="itemId" type="hidden" value="${tScIcXsstockbillPage.itemId }">
		<input id="empId" name="empId" type="hidden" value="${tScIcXsstockbillPage.empId }">
		<input id="deptId" name="deptId" type="hidden" value="${tScIcXsstockbillPage.deptId }">
		<input id="accountID" name="accountID" type="hidden" value="${tScIcXsstockbillPage.accountID }">
		<input id="classIDSrc" name="classIDSrc" type="hidden" value="${tScIcXsstockbillPage.accountID }">
		<input id="json" name="json" type="hidden"/>
		<table cellpadding="0" cellspacing="1" class="formtable" style="z-index: 50;position: absolute">
			<tr>
				<td align="right">
					<label class="Validform_label">客户:</label>
				</td>
				<td class="value">
					<input id="itemName" name="itemName"  type="text" style="width: 150px" class="inputxt popup-select" value="${tScIcXsstockbillPage.itemName }"
						   datatype="*">
      <span class="Validform_checktip">
          *
      </span>
					<label class="Validform_label" style="display: none;">客户</label>
				</td>
				<td align="right">
					<label class="Validform_label">经办人:</label>
				</td>
				<td class="value">
					<input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" value="${tScIcXsstockbillPage.empName }"
						   datatype="*">
      <span class="Validform_checktip">
          *
      </span>
					<label class="Validform_label" style="display: none;">经办人</label>
				</td>
				<td align="right">
					<label class="Validform_label">部门:</label>
				</td>
				<td class="value">
					<input id="deptName" name="deptName"  type="text" style="width: 150px" class="inputxt popup-select" value="${tScIcXsstockbillPage.deptName }"
						   datatype="*">
      <span class="Validform_checktip">
          *
      </span>
					<label class="Validform_label" style="display: none;">部门</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">优惠金额:</label>
				</td>
				<td class="value">
					<input id="rebateAmount" name="rebateAmount" type="text" style="width: 150px" class="inputxt"
						   onblur="changeAllAmount()"
						   value="${tScIcXsstockbillPage.rebateAmount }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">优惠金额</label>
				</td>
				<td align="right">
					<label class="Validform_label">本次收款:</label>
				</td>
				<td class="value">
					<input id="curPayAmount" name="curPayAmount" type="text" style="width: 150px" class="inputxt"
						   value="${tScIcXsstockbillPage.curPayAmount }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">本次收款</label>
				</td>
				<td align="right">
					<label class="Validform_label">结算账户:</label>
				</td>
				<td class="value">
					<input id="accountName" name="accountName" type="text" style="width: 150px" class="inputxt popup-select" onblur="checkcurPayAmount()"
						   value="${tScIcXsstockbillPage.accountName }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">结算账户</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">物流费用:</label>
				</td>
				<td class="value">
					<input id="freight" name="freight" type="text" readonly="readonly" style="width: 150px" class="inputxt"
						   value="${tScIcXsstockbillPage.freight }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">物流费用</label>
				</td>
				<%--<td align="right">--%>
					<%--<label class="Validform_label">重量:</label>--%>
				<%--</td>--%>
				<%--<td class="value">--%>
					<%--<input id="weight" name="weight" type="text" readonly="readonly" style="width: 150px" class="inputxt"--%>
						   <%--value="${tScIcXsstockbillPage.weight }"--%>
							<%-->--%>
      <%--<span class="Validform_checktip">--%>
                <%--</span>--%>
					<%--<label class="Validform_label" style="display: none;">重量</label>--%>
				<%--</td>--%>
				<td align="right">
					<label class="Validform_label">应收金额:</label>
				</td>
				<td class="value">
					<input id="allAmount" name="allAmount" readonly="readonly" type="text" style="width: 150px" class="inputxt"
						   value="${tScIcXsstockbillPage.allAmount }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">应收金额</label>
				</td>
				<td align="right">
					<label class="Validform_label">源单类型:</label>
				</td>
				<td class="value">
					<input id="classIDName" readonly="readonly" name="classIDName" type="text" style="width: 150px" class="inputxt"
						   value="${tScIcXsstockbillPage.classIDName }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">源单类型</label>
					<span class="spanbtn-expand" id="btnExpand"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">分支机构:</label>
				</td>
				<td class="value">
					<input id="sonName" name="sonName" readonly="readonly" value="${tScIcXsstockbillPage.sonName }" type="text" style="width: 150px" class="inputxt"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">分支机构</label>
				</td>
				<td align="right">
					<label class="Validform_label">摘要:</label>
				</td>
				<td class="value" colspan="3">
					<input id="explanation" name="explanation" type="text" style="width: 400px" class="inputxt"
						   value="${tScIcXsstockbillPage.explanation }"
							>
      <span class="Validform_checktip">
                </span>
					<label class="Validform_label" style="display: none;">摘要</label>
				</td>
			</tr>
		</table>
	</div>
	<div class="panel-header" id="custHeader"  style="margin-top: 100px;">
		<div class="panel-title">退货单</div>
		<div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up" ></a></div>
	</div>
	<div  class="panel-body layout-body" id="centerBody" style="height: 30%">

	</div>
	<div class="panel-header" id="goodsHeader" >
		<div class="panel-title">换货单</div>
		<div class="panel-tool"><a href="javascript:void(0)" class="layout-button-down" ></a></div>
	</div>
	<div  class="panel-body layout-body" id="southBody" style="height: 30%">

	</div>
	<%--<div style="width: auto;height: 200px;">--%>
	<%--&lt;%&ndash; 增加一个div，用于调节页面大小，否则默认太小 &ndash;%&gt;--%>
	<%--<div style="width:800px;height:1px;"></div>--%>
	<%--<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">--%>
	<%--<t:tab href="tScIcXsstockbillController.do?tScIcXsstockbillentry1List&id=${tScIcXsstockbillPage.id}"--%>
	<%--icon="icon-search" title="销售换货单退货表" id="tScIcXsstockbillentry1"></t:tab>--%>
	<%--<t:tab href="tScIcXsstockbillController.do?tScIcXsstockbillentry2List&id=${tScIcXsstockbillPage.id}"--%>
	<%--icon="icon-search" title="销售换货单换货表" id="tScIcXsstockbillentry2"></t:tab>--%>
	<%--</t:tabs>--%>
	<%--</div>--%>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
	<tbody id="add_tScIcXsstockbillentry1_table_template">
	<tr>
		<td align="center" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].id" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].createName" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].createBy" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].createDate" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].updateName" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].updateBy" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].updateDate" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].fid" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].findex" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].basicUnitId" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].coefficient" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].basicQty" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].secUnitId" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].secCoefficient" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].secQty" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].price" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].amount" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].discountPrice" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].discountAmount" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].idSrc" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].entryIdSrc" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].idOrder" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].entryIdOrder" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].costPrice" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].costAmount" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].itemId" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].stockId" type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].basicCoefficient"  type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].batchManager"  type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].isKFPeriod"  type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].xsLimitPrice"  type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].kfDateType"  type="hidden"/>
			<input name="tScIcXsstockbillentry1List[#index#].classIDSrc"  type="hidden"/>
			<div style="width: 25px;" name="xh"></div>
		</td>
		<td align="center" bgcolor="white">
			<div style="width: 80px;">
				<a name="addTScIcXsstockbillentry1Btn[#index#]" id="addTScIcXsstockbillentry1Btn[#index#]" href="#"
				   onclick="clickAddTScIcXsstockbillentry1Btn('#index#');"></a>
				<a name="delTScIcXsstockbillentry1Btn[#index#]" id="delTScIcXsstockbillentry1Btn[#index#]" href="#"
				   onclick="clickDelTScIcXsstockbillentry1Btn('#index#');"></a>
			</div>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].itemNo" maxlength="32"
				   onkeypress="keyDownInfoI('#index#','item',event)" datatype="*"
				   type="text" class="inputxt popup-select" style="width:105px;"/>
			<label class="Validform_label" style="display: none;">商品编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].itemName" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:180px;"/>
			<label class="Validform_label" style="display: none;">商品名称</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].model" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:85px;"/>
			<label class="Validform_label" style="display: none;">规格</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].barCode" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:65px;"/>
			<label class="Validform_label" style="display: none;">条形码</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].stockName" maxlength="32"
				   onkeypress="keyDownInfoI('#index#','stock',event)" datatype="*"
				   type="text" class="inputxt popup-select" style="width:65px;"/>
			<label class="Validform_label" style="display: none;">仓库</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].batchNo" maxlength="100"
				   type="text" class="inputxt" style="width:80px;" datatype="*"/>
			<label class="Validform_label" style="display: none;">批号</label>
		</td>
		<td align="left" bgcolor="white">
			<input id="unitId1[#index#]" name="tScIcXsstockbillentry1List[#index#].unitId" maxlength="32"
				   type="text" class="inputxt" style="width:50px;"/>
			<label class="Validform_label" style="display: none;">单位</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].qty" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">数量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].taxPriceEx" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].taxAmountEx" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].discountRate" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" value="100" datatype="num"/>
			<label class="Validform_label" style="display: none;">折扣率</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].taxPrice" maxlength="20"
				   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
			<label class="Validform_label" style="display: none;">折后单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].inTaxAmount" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">折后金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].taxRate" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">税率</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].taxAmount" maxlength="20"
				   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
			<label class="Validform_label" style="display: none;">税额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].kfDate" maxlength="20"
				   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"/>
			<label class="Validform_label" style="display: none;">生产日期</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].kfPeriod" maxlength="10" readonly="readonly"
				   type="text" class="inputxt" datatype="num" ignore="ignore" style="width:50px;"/>
			<label class="Validform_label" style="display: none;">保质期</label>
		</td>
		<td align="left" bgcolor="white">
			<t:dictSelect width="70" field="tScIcXsstockbillentry1List[#index#].kfDateType_"
						  type="list" extendJson="{disabled:disabled}"
						  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
						  title="保质期类型"></t:dictSelect>
			<label class="Validform_label" style="display: none;">保质期类型</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].periodDate" maxlength="20"
				   type="text" class="Wdate" readonly="readonly" style="width:80px;"/>
			<label class="Validform_label" style="display: none;">有效期至</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].classIDName" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:90px;"/>
			<label class="Validform_label" style="display: none;">源单类型</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].billNoSrc" maxlength="50"
				   type="text" class="inputxt" readonly="readonly" style="width:90px;"/>
			<label class="Validform_label"  style="display: none;">源单编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].billNoOrder" maxlength="50"
				   type="text" class="inputxt" readonly="readonly" style="width:90px;"/>
			<label class="Validform_label" style="display: none;">订单编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry1List[#index#].note" maxlength="255"
				   type="text" class="inputxt" style="width:180px;"/>
			<label class="Validform_label" style="display: none;">备注</label>
		</td>
	</tr>
	</tbody>
	<tbody id="add_tScIcXsstockbillentry2_table_template">
	<tr>
		<td align="center" bgcolor="white">
			<div style="width: 25px;" name="xh"></div>
		</td>
		<td align="center" bgcolor="white">
			<div style="width: 80px;">
				<a name="addTScIcXsstockbillentry2Btn[#index#]" id="addTScIcXsstockbillentry2Btn[#index#]" href="#"
				   onclick="clickAddTScIcXsstockbillentry2Btn('#index#');"></a>
				<a name="delTScIcXsstockbillentry2Btn[#index#]" id="delTScIcXsstockbillentry2Btn[#index#]" href="#"
				   onclick="clickDelTScIcXsstockbillentry2Btn('#index#');"></a>
			</div>
		</td>
		<input name="tScIcXsstockbillentry2List[#index#].id" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].createName" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].createBy" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].createDate" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].updateName" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].updateBy" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].updateDate" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].fid" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].findex" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].secUnitId" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].basicUnitId" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].coefficient" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].basicQty" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].secCoefficient" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].secQty" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].price" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].amount" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].discountPrice" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].discountAmount" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].costPrice" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].costAmount" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].stockId" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].itemId" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].basicCoefficient" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].batchManager" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].isKFPeriod" type="hidden"/>
		<input name="tScIcXsstockbillentry2List[#index#].xsLimitPrice"  type="hidden"/>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].itemNo" maxlength="32"
				   onkeypress="keyDownInfo('#index#','item',event)" datatype="*"
				   type="text" class="inputxt popup-select" style="width:105px;"/>
			<label class="Validform_label" style="display: none;">商品编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].itemName" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:180px;"/>
			<label class="Validform_label" style="display: none;">商品名称</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].model" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:85px;"/>
			<label class="Validform_label" style="display: none;">规格</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].barCode" maxlength="32"
				   type="text" class="inputxt" readonly="readonly" style="width:65px;"/>
			<label class="Validform_label" style="display: none;">条形码</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].stockName" maxlength="32"
				   onkeypress="keyDownInfo('#index#','stock',event)" datatype="*"
				   type="text" class="inputxt popup-select" style="width:65px;"/>
			<label class="Validform_label" style="display: none;">仓库</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].batchNo" maxlength="100"
				   type="text" class="inputxt" style="width:80px;"/>
			<label class="Validform_label" style="display: none;">批号</label>
		</td>
		<td align="left" bgcolor="white">
			<input id="unitId2[#index#]" name="tScIcXsstockbillentry2List[#index#].unitId" maxlength="32"
				   type="text" class="inputxt" style="width:50px;"/>
			<label class="Validform_label" style="display: none;">单位</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].qty" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">数量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].taxPriceEx" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].taxAmountEx" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].discountRate" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" value="100" datatype="num"/>
			<label class="Validform_label" style="display: none;">折扣率（%）</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].taxPrice" maxlength="20"
				   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
			<label class="Validform_label" style="display: none;">折后单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].inTaxAmount" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">折后金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].taxRate" maxlength="20"
				   type="text" class="inputxt" style="width:70px;" datatype="num"/>
			<label class="Validform_label" style="display: none;">税率（%）</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].taxAmount" maxlength="20"
				   type="text" class="inputxt" readonly="readonly" style="width:70px;"/>
			<label class="Validform_label" style="display: none;">税额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].kfDate" maxlength="20"
				   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"/>
			<label class="Validform_label" style="display: none;">生产日期</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].kfPeriod" maxlength="20" readonly="readonly"
				   type="text" class="inputxt"  style="width:50px;" datatype="num" ignore="ignore"/>
			<label class="Validform_label" style="display: none;">保质期</label>
		</td>
		<td align="left" bgcolor="white">
			<t:dictSelect width="70" field="tScIcXsstockbillentry2List[#index#].kfDateType_"
						  type="list" extendJson="{disabled:disabled}"
						  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
						  title="保质期类型"></t:dictSelect>
			<label class="Validform_label" style="display: none;">保质期类型</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].periodDate" maxlength="20"
				   type="text" class="Wdate" readonly="readonly" style="width:80px;"/>
			<label class="Validform_label" style="display: none;">有效期至</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScIcXsstockbillentry2List[#index#].note" maxlength="255"
				   type="text" class="inputxt" style="width:180px;"/>
			<label class="Validform_label" style="display: none;">备注</label>
		</td>
	</tr>
	</tbody>
</table>
<!-- 页脚显示 -->
<div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
	<div style="width: 33%;display:inline;float: left" align="left">
		<label class="Validform_label footlabel">制单人: </label>
		<span class="inputxt footspan"> ${sessionScope.user.realName}</span>
	</div>
	<div style="width: 33%;display: inline;float: left" align="left">
		<label class="Validform_label footlabel">审核人: </label>
		<span class="inputxt footspan"> </span>
	</div>
	<div style="width: 33%;display: inline;float: right" align="left">
		<label class="Validform_label footlabel">审核日期: </label>
		<span class="inputxt footspan"> </span>
	</div>


</div>
</body>
<script src="webpage/com/qihang/buss/sc/sales/tScIcXsstockbill.js"></script>