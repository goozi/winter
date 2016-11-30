<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>订货管理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
	<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	<script type="text/javascript" src="plug-in/scm/js/computingTools.js"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			$('#tt').tabs({
				onSelect: function (title) {
					$('#tt .panel-body').css('width', 'auto');
				}
			});
			$(".tabs-wrap").css('width', '100%');
			$(".tabs-wrap").css('display', 'none');
		});
	</script>

	<style >
		/** 页脚样式  **/
		body{
			position: absolute;
			width: 100%;
			height: 100%;
		}
		.footlabel{
			float: left;
			margin-left: 35px;
		}

		.footspan{
			float: left;
			margin-left: 5px;
			margin-right: 5px;
			font-weight: bold;
			color: grey;
			margin-bottom: 5px;
			text-decoration: underline;
		}
		.spanBtn{
			background-color:#CCE1F3;
			display:-moz-inline-box;
			display:inline-block;
			width:20px;
			height: 20px;
			text-align: center;
		}
	</style>
</head>
<body style="overflow: hidden;">
<input type="hidden" id="checkDate" value="${checkDate}">
<input type="hidden" id="load" value="${load}">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" title="订货管理" tranType="1505" tableName="t_sc_drp_order" tiptype="1" action="tScDrpOrderController.do?doUpdate" beforeSubmit="checkBillDate" saveTemp="true" styleClass="fullForm">
	<input id="id" name="id" type="hidden" value="${tScDrpOrderPage.id }"/>
	<input id="createName" name="createName" type="hidden" value="${tScDrpOrderPage.createName }"/>
	<input id="createBy" name="createBy" type="hidden" value="${tScDrpOrderPage.createBy }"/>
	<input id="createDate" name="createDate" type="hidden" value="${tScDrpOrderPage.createDate }"/>
	<input id="updateName" name="updateName" type="hidden" value="${tScDrpOrderPage.updateName }"/>
	<input id="updateBy" name="updateBy" type="hidden" value="${tScDrpOrderPage.updateBy }"/>
	<input id="updateDate" name="updateDate" type="hidden" value="${tScDrpOrderPage.updateDate }"/>
	<input id="tranType" name="tranType" type="hidden" value="1505"/>
	<input id="amount" name="amount" type="hidden" value="${tScDrpOrderPage.amount }"/>
	<input id="checkAmount" name="checkAmount" type="hidden" value="${tScDrpOrderPage.checkAmount }"/>
	<input id="checkerID" name="checkerID" type="hidden" value="${tScDrpOrderPage.checkerID }"/>
	<input id="billerID" name="billerID" type="hidden" value="${tScDrpOrderPage.billerID }"/>
	<input id="checkDate" name="checkDate" type="hidden" value="${tScDrpOrderPage.checkDate }"/>
	<input id="checkState" name="checkState" type="hidden" value="${tScDrpOrderPage.checkState }"/>
	<input id="closed" name="closed" type="hidden" value="${tScDrpOrderPage.closed }"/>
	<input id="autoFlag" name="autoFlag" type="hidden" value="${tScDrpOrderPage.autoFlag }"/>
	<input id="cancellation" name="cancellation" type="hidden" value="${tScDrpOrderPage.cancellation }"/>
	<input id="empID" name="empID" type="hidden" value="${tScDrpOrderPage.empID }"/>
	<input id="deptID" name="deptID" type="hidden" value="${tScDrpOrderPage.deptID }"/>
	<input id="stockID" name="stockID" type="hidden" value="${tScDrpOrderPage.stockID }"/>
	<input id="sonId" name="sonID" type="hidden" value="${tScDrpOrderPage.sonID}"/>
	<input id="itemID" name="itemID" type="hidden" value="${tScDrpOrderPage.itemID}"/>
	<input id="version" name="version" type="hidden" value="${tScDrpOrderPage.version }"/>
	<input name="billNo" id="billNo" value="${tScDrpOrderPage.billNo}" type="hidden"/>
	<input name="date" value="${tScDrpOrderPage.date}" type="hidden"/>
	<input id="delearType" value="${delearType}" type="hidden"/>
	<input id="version" name="version" value="${tScDrpOrderPage.version}" type="hidden"/>
	<table cellpadding="0" cellspacing="1" class="formtable" style="position: absolute;z-index: 50;width: 100% ">
		<tr>
			<td align="right" style="width: 6%">
				<label class="Validform_label">上游经销商:</label>
			</td>
			<td class="value" style="width: 24%">
				<input id="itemName" name="itemName" value="${tScDrpOrderPage.itemName}" type="text" style="width: 150px" class="inputxt <c:if test="${(!delearType eq 'ADealer') || (!delearType eq '')}">popup-select</c:if>" datatype="*" <c:if test="${(delearType eq 'ADealer') || (delearType eq '')}">readonly</c:if>/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">上游经销商或总部</label>
			</td>
			<td align="right">
				<label class="Validform_label">经办人:</label>
			</td>
			<td class="value">
				<input id="empName" name="empName" value="${tScDrpOrderPage.empName}" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">经办人</label>
			</td>
			<td align="right">
				<label class="Validform_label">部门:</label>
			</td>
			<td class="value">
				<input id="deptName" name="deptName" value="${tScDrpOrderPage.deptName}" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">部门</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">仓库:</label>
			</td>
			<td class="value">
				<input id="stockName" name="stockName" value="${tScDrpOrderPage.stockName}" type="text" style="width: 150px" class="inputxt popup-select" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">仓库</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系人:</label>
			</td>
			<td class="value">
				<input id="contact" name="contact" value="${tScDrpOrderPage.contact}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人</label>
			</td>
			<td align="right">
				<label class="Validform_label">手机号码:</label>
			</td>
			<td class="value">
				<input id="mobilePhone" name="mobilePhone" value="${tScDrpOrderPage.mobilePhone}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">手机号码</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">电话:</label>
			</td>
			<td class="value">
				<input id="phone" name="phone" value="${tScDrpOrderPage.phone}" ype="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">传真:</label>
			</td>
			<td class="value">
				<input id="fax" name="fax" value="${tScDrpOrderPage.fax}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">传真</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系地址:</label>
			</td>
			<td class="value">
				<input id="address" name="address" value="${tScDrpOrderPage.address}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系地址</label>
				<span class="spanbtn-expand" id="btnExpand"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">优惠金额:</label>
			</td>
			<td class="value">
				<input id="rebateAmount" name="rebateAmount" value="${tScDrpOrderPage.rebateAmount}" type="text" style="width: 150px" class="inputxt"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">应付金额:</label>
			</td>
			<td class="value">
				<input id="allAmount" name="allAmount" value="${tScDrpOrderPage.allAmount}" type="text" style="width:150px;background-color:rgb(226,226,226);" class="inputxt" readonly />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">应付金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">分支机构:</label>
			</td>
			<td class="value">
				<input type="text" style="width: 150px" class="inputxt" value="${tScDrpOrderPage.sonName}" readonly datatype="*"/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">分支机构</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">摘要:</label>
			</td>
			<td class="value" colspan="5">
				<input id="explanation" name="explanation" value="${tScDrpOrderPage.explanation}" type="text" style="width: 1000px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">摘要</label>
			</td>
		</tr>
	</table>
	<div style="width: auto;height: 200px;margin-top: 100px;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
		<div style="width:800px;height:1px;"></div>
		<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
			<t:tab href="tScDrpOrderController.do?tScDrpOrderentryList&id=${tScDrpOrderPage.id}"
				   icon="icon-search" title="订单管理" id="tScDrpOrderentry"></t:tab>
		</t:tabs>
	</div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
	<tbody id="add_tScDrpOrderentry_table_template">
	<tr>
		<td align="center" bgcolor="#F6FCFF">
			<input name="tScDrpOrderentryList[#index#].id" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].createName" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].createBy" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].createDate" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].updateName" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].updateBy" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].updateDate" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].interID" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].indexNumber" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].affirmDate" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].itemID" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].stockID" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].secQty" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].basicUnitID" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].secUnitID" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].secCoefficient" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].price" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].amount" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].discountPrice" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].discountAmount" type="hidden"/>
			<input id="tScDrpOrderentryList[#index#].itemWeight" name="tScDrpOrderentryList[0].itemWeight" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].basicQty" type="hidden"/>
			<input name="tScDrpOrderentryList[#index#].coefficient" type="hidden"/>
			<div style="width: 25px;background-color: white;" name="xh"></div>
		</td>
		<td align="center" bgcolor="white">
			<div style="width: 80px;background-color: white;">
				<a name="addTScDrpOrderentryBtn[#index#]" id="addTScDrpOrderentryBtn[#index#]" href="#" onclick="clickAddTScDrpOrderentryBtn('#index#');"></a>
				<a name="delTScDrpOrderentryBtn[#index#]" id="delTScDrpOrderentryBtn[#index#]" href="#" onclick="clickDelTScDrpOrderentryBtn('#index#');"></a>
			</div>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].goodsNo" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo('#index#','item',event)" datatype="*"/>
			<label class="Validform_label" style="display: none;">商品编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].goodsName" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"  datatype="*"/>
			<label class="Validform_label" style="display: none;">商品名称</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].model" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"/>
			<label class="Validform_label" style="display: none;">规格</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].barCode" maxlength="32" readonly type="text" class="inputxt" style="width: 120px;background-color:rgb(226,226,226)"/>
			<label class="Validform_label" style="display: none;">条形码</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].stockName" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo('#index#','stock',event)"/>
			<label class="Validform_label" style="display: none;">仓库</label>
		</td>
		<td align="left" bgcolor="white">
			<input id="tScDrpOrderentryList[#index#].unitID" name="tScDrpOrderentryList[#index#].unitID" style="width:60px;"/>
			<label class="Validform_label" style="display: none;">单位</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].qty" maxlength="32" type="text" class="inputxt"  style="width:120px;" datatype="vInt" onblur="changePrice('#index#')" />
			<label class="Validform_label" style="display: none;">数量</label>
		</td>
		<%--<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].basicUnitID" id="tScDrpOrderentryList[#index#].basicUnitID" style="width:60px;" />
			<label class="Validform_label" style="display: none;">基本单位</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].coefficient" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" datatype="*"/>
			<label class="Validform_label" style="display: none;">换算率</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].basicQty" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" datatype="*"/>
			<label class="Validform_label" style="display: none;">基本数量</label>
		</td>--%>
		<%--<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].secUnitID" id="tScDrpOrderentryList[#index#].secUnitID"   style="width:60px;" />
			<label class="Validform_label" style="display: none;">辅助单位</label>
		</td>--%>
		<%--<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].secCoefficient" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
			<label class="Validform_label" style="display: none;">辅助换算率</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].secQty" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
			<label class="Validform_label" style="display: none;">辅助数量</label>
		</td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:120px;" onblur="setAmountBytaxPriceEx('#index#','tScDrpOrderentryList',3)" />
			<label class="Validform_label" style="display: none;">单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:120px;" onblur="setAmountBytaxAmountEx('#index#','tScDrpOrderentryList',3)"/>
			<label class="Validform_label" style="display: none;">金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].discountRate" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="100"/>
			<label class="Validform_label" style="display: none;">折扣率(%)</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].taxPrice" maxlength="32" readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)"/>
			<label class="Validform_label" style="display: none;">折后单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">折后金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].taxRate" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="0" />
			<label class="Validform_label" style="display: none;">税率(%)</label>
		</td>
		<%--<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].price" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">不含税单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">不含税金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">不含税折后单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">不含税折后金额</label>
		</td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly/>
			<label class="Validform_label" style="display: none;">税额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].affirmQty" maxlength="32"  readonly type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" />
			<label class="Validform_label" style="display: none;">收货确认数量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].jhDate" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"/>
			<label class="Validform_label" style="display: none;">收货日期</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].outStockQty" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226)" readonly/>
			<label class="Validform_label" style="display: none;">发货数量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].outDate" maxlength="32"  readonly type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;background-color:rgb(226,226,226)"/>
			<label class="Validform_label" style="display: none;">发货日期</label>
		</td>
		<%--<td align="left" bgcolor="white">
              <input name="tScDrpOrderentryList[#index#].affirmDate" maxlength="32"  readonly type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;background-color:rgb(226,226,226)" />
              <label class="Validform_label" style="display: none;">收货确认时间</label>
          </td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpOrderentryList[#index#].note" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">备注</label>
		</td>
	</tr>
	</tbody>
</table>
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 10px;left:60px;width:100%" >
	<div style="width: 33%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">制单人: </label>
		<span  class="inputxt footspan">${sessionScope.user.realName}</span>
	</div>
	<div style="width: 33%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">审核人: </label>
		<span  class="inputxt footspan">${auditor}</span>
	</div>
	<div style="width: 34%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">审核日期: </label>
		<span  class="inputxt footspan">${auditDate}</span>
	</div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpOrder.js"></script>