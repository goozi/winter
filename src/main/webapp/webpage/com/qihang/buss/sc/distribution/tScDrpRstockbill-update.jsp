<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>退货申请</title>
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
<body style="overflow: hidden;">
<input type="hidden" id="checkDate" value="${checkDate}"/>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" title="退货申请" action="tScDrpRstockbillController.do?doUpdate" beforeSubmit="checkBillDate" saveTemp="true">
	<input id="id" name="id" type="hidden" value="${tScDrpRstockbillPage.id }"/>
	<input id="createName" name="createName" type="hidden" value="${tScDrpRstockbillPage.createName }"/>
	<input id="createBy" name="createBy" type="hidden" value="${tScDrpRstockbillPage.createBy }"/>
	<input id="createDate" name="createDate" type="hidden" value="${tScDrpRstockbillPage.createDate }"/>
	<input id="updateName" name="updateName" type="hidden" value="${tScDrpRstockbillPage.updateName }"/>
	<input id="updateBy" name="updateBy" type="hidden" value="${tScDrpRstockbillPage.updateBy }"/>
	<input id="updateDate" name="updateDate" type="hidden" value="${tScDrpRstockbillPage.updateDate }"/>
	<input id="tranType" name="tranType" type="hidden" value="1507"/>
	<input id="amount" name="amount" type="hidden" value="${tScDrpRstockbillPage.amount }"/>
	<input id="interIdSrc" name="interIdSrc" type="hidden" value="${tScDrpRstockbillPage.interIdSrc }"/>
	<%--<input id="billNoSrc" name="billNoSrc" type="hidden" value="${tScDrpRstockbillPage.billNoSrc }"/>--%>
	<input id="cancellation" name="cancellation" type="hidden" value="${tScDrpRstockbillPage.cancellation }"/>
	<input id="version" name="version" type="hidden" value="${tScDrpRstockbillPage.version }"/>
	<input id="disabled" name="disabled" type="hidden" value="${tScDrpRstockbillPage.disabled }"/>
	<input id="deleted" name="deleted" type="hidden" value="${tScDrpRstockbillPage.deleted }"/>
	<input id="itemId" name="itemId" type="hidden" value="${tScDrpRstockbillPage.itemId }"/>
	<input id="empId" name="empId" type="hidden" value="${tScDrpRstockbillPage.empId }"/>
	<input id="sonId" name="sonId" type="hidden" value="${tScDrpRstockbillPage.sonId }"/>
	<input id="deptId" name="deptId" type="hidden" value="${tScDrpRstockbillPage.deptId }"/>
	<input id="rStockId" name="rStockId" type="hidden" value="${tScDrpRstockbillPage.rStockId }"/>
	<input id="checkerId" name="checkerId" type="hidden" value="${tScDrpRstockbillPage.checkerId }"/>
	<input id="billerId" name="billerId" type="hidden" value="${tScDrpRstockbillPage.billerId }"/>
	<input id="date" name="date" type="hidden" value="${tScDrpRstockbillPage.date}"/>
	<input id="billNo" name="billNo" type="hidden" value="${tScDrpRstockbillPage.billNo}"/>
	<input id="customerId" name="customerId" type="hidden" value="${sessionScope.user.id}"/>
	<input id="json" name="json" type="hidden"/>
	<input id="delearType" value="${delearType}" type="hidden"/>
	<table cellpadding="0" cellspacing="1" class="formtable" style="position: absolute;z-index: 50;width: 100% ">
		<tr>
			<td align="right">
				<label class="Validform_label">上游经销商:</label>
			</td>
			<td class="value">
				<input id="itemName" name="itemName" type="text" value="${tScDrpRstockbillPage.itemName}" style="width: 150px" class="inputxt <c:if test="${(!delearType eq 'ADealer') || (!delearType eq '')}">popup-select</c:if>" datatype="*" <c:if test="${delearType eq 'CDealer'}">readonly</c:if><c:if test="${delearType eq ''}">readonly</c:if>/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">上游经销商或总部</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系人:</label>
			</td>
			<td class="value">
				<input id="contact" name="contact" type="text" style="width: 150px" value="${tScDrpRstockbillPage.contact}" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人</label>
			</td>
			<td align="right">
				<label class="Validform_label">手机号码:</label>
			</td>
			<td class="value">
				<input id="mobilePhone" name="mobilePhone" type="text"  value="${tScDrpRstockbillPage.mobilePhone}" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">手机号码</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">电话:</label>
			</td>
			<td class="value">
				<input id="phone" name="phone" type="text" value="${tScDrpRstockbillPage.phone}" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">传真:</label>
			</td>
			<td class="value">
				<input id="fax" name="fax" type="text" value="${tScDrpRstockbillPage.fax}" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">传真</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系地址 :</label>
			</td>
			<td class="value">
				<input id="address" name="address" type="text" value="${tScDrpRstockbillPage.address}" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系地址 </label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">经办人:</label>
			</td>
			<td class="value">
				<input id="empName" name="empName" type="text" value="${tScDrpRstockbillPage.empName}" style="width: 150px" class="inputxt popup-select" datatype="*" />
				<span class="Validform_checktip"> * </span>
				<label class="Validform_label" style="display: none;">经办人</label>
			</td>
			<td align="right">
				<label class="Validform_label">部门 :</label>
			</td>
			<td class="value">
				<input id="deptName" name="deptName" type="text" value="${tScDrpRstockbillPage.deptName}" style="width: 150px" class="inputxt popup-select" datatype="*" />
				<span class="Validform_checktip"> *</span>
				<label class="Validform_label" style="display: none;">部门 </label>
			</td>
			<td align="right">
				<label class="Validform_label">仓库 :</label>
			</td>
			<td class="value">
				<input id="rStockName" name="rStockName" value="${tScDrpRstockbillPage.rStockName}" type="text" style="width: 150px" class="inputxt popup-select" />
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">仓库</label>
				<span class="spanbtn-expand" id="btnExpand"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">物流费用:</label>
			</td>
			<td class="value">
				<input id="freight" name="freight" type="text" value="${tScDrpRstockbillPage.freight}" style="width: 150px" class="inputxt" readonly>
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">物流费用</label>
			</td>
			<td align="right">
				<label class="Validform_label">应收金额:</label>
			</td>
			<td class="value">
				<input id="allAmount" name="allAmount" type="text" value="${tScDrpRstockbillPage.allAmount}" style="width: 150px" class="inputxt"  readonly/>
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">应收金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">重量:</label>
			</td>
			<td class="value">
				<input id="weight" name="weight" type="text" style="width: 150px" value="${tScDrpRstockbillPage.weight}"  class="inputxt"  readonly/>
				<span class="Validform_checktip">  </span>
				<label class="Validform_label" style="display: none;">重量</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">源单类型:</label>
			</td>
			<td class="value">
				<input id="classIdSrc" name="classIdSrc" type="text" value="${tScDrpRstockbillPage.classIdSrc}" style="width: 150px" class="inputxt" readonly/>
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">源单类型</label>
			</td>
			<td align="right">
				<label class="Validform_label">源单编号:</label>
			</td>
			<td class="value">
				<input id="billNoSrc" name="billNoSrc" type="text" value="${tScDrpRstockbillPage.billNoSrc}" style="width: 150px" class="inputxt" readonly/>
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">源单编号</label>
			</td>
			<td align="right">
				<label class="Validform_label">分支机构:</label>
			</td>
			<td class="value">
				<input id="sonName" name="sonName" value="${tScDrpRstockbillPage.sonName}" readonly type="text" style="width: 150px" class="inputxt" datatype="*" >
				<span class="Validform_checktip"> *</span>
				<label class="Validform_label" style="display: none;">分支机构</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">摘要:</label>
			</td>
			<td class="value" colspan="5">
				<input id="explanation" name="explanation" type="text" value="${tScDrpRstockbillPage.explanation}" style="width: 60%" class="inputxt" />
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">摘要</label>
			</td>
		</tr>
	</table>
	<div style="width: auto;height: 200px;margin-top: 100px;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
		<div style="width:800px;height:1px;"></div>
		<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
			<t:tab href="tScDrpRstockbillController.do?tScDrpRstockbillentryList&id=${tScDrpRstockbillPage.id}"
				   icon="icon-search" title="退货申请" id="tScDrpRstockbillentry"></t:tab>
		</t:tabs>
	</div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
	<tbody id="add_tScDrpRstockbillentry_table_template">
	<tr>
		<td align="center" bgcolor="#F6FCFF">
			<input name="tScDrpRstockbillentryList[#index#].id" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].createName" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].indexNumber" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].createBy" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].createDate" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].updateName" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].updateBy" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].updateDate" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].interId" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].stockId" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].itemId" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].classIDSrc" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].idSrc" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].interIdSrc" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].interIdOrder" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].idOrder" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].kfDateType" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].secQty" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].basicUnitId" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].secUnitId" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].secCoefficient" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].price" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].amount" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].discountPrice" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].discountAmount" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].basicQty" type="hidden"/>
			<input name="tScDrpRstockbillentryList[#index#].coefficient" type="hidden"/>
			<input id="tScDrpRstockbillentryList[#index#].itemWeight" name="tScDrpRstockbillentryList[#index#].itemWeight" type="hidden"/>
			<div style="width: 25px;background-color: white;" name="xh">1</div>
		</td>
		<td align="center" bgcolor="white">
			<div style="width: 80px;background-color: white;">
				<a name="addTScDrpRstockbillentryBtn[#index#]" id="addTScDrpRstockbillentryBtn[#index#]" href="#"  onclick="clickAddTScDrpRstockbillentryBtn('#index#');"></a>
				<a name="delTScDrpRstockbillentryBtn[#index#]" id="delTScDrpRstockbillentryBtn[#index#]" href="#"  onclick="clickDelTScDrpRstockbillentryBtn('#index#');"></a>
			</div>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].number" maxlength="32" type="text" class="inputxt popup-select"  style="width:120px;" onkeypress="keyDownInfo('#index#','item',event)" datatype="*" />
			<label class="Validform_label" style="display: none;">商品编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].name" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" datatype="*" readonly/>
			<label class="Validform_label" style="display: none;">名称</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].model" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">规格</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].barCode" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">条形码</label>
		</td>
		<td align="left" bgcolor="white">
			<input id="tScDrpRstockbillentryList[#index#].stockName" name="tScDrpRstockbillentryList[#index#].stockName" maxlength="32" type="text" class="inputxt popup-select" onkeypress="keyDownInfo('#index#','stock',event)" style="width:120px;" datatype="*"/>
			<label class="Validform_label" style="display: none;">仓库</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].batchNo" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">批号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].unitId" id="tScDrpRstockbillentryList[#index#].unitId" style="width:120px;"/>
			<label class="Validform_label" style="display: none;">单位 </label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].qty" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" onblur="changePrice('#index#')"/>
			<label class="Validform_label" style="display: none;">数量</label>
		</td>
		<%-- <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].basicUnitId" id="tScDrpRstockbillentryList[#index#].basicUnitId" style="width:120px;background-color:rgb(226,226,226);" readonly/>
              <label class="Validform_label" style="display: none;">基本单位 </label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].coefficient" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">换算率</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].basicQty" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);"  datatype="num" ignore="ignore" readonly/>
              <label class="Validform_label" style="display: none;">基本数量 </label>
          </td>--%>
		<%-- <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].secUnitId" id="tScDrpRstockbillentryList[#index#].secUnitId" style="width:120px;background-color:rgb(226,226,226);" readonly/>
              <label class="Validform_label" style="display: none;">辅助单位</label>
          </td>--%>
		<%--<td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].secCoefficient" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">辅助换算率</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].secQty" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly  datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">辅助数量</label>
          </td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" />
			<label class="Validform_label" style="display: none;">单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore" />
			<label class="Validform_label" style="display: none;">金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].discountRate" maxlength="32" type="text" class="inputxt" value="100" style="width:120px;"  datatype="num" ignore="ignore"/>
			<label class="Validform_label" style="display: none;">折扣率(%)</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].taxPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly  datatype="num" ignore="ignore"/>
			<label class="Validform_label" style="display: none;">折后单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
			<label class="Validform_label" style="display: none;">折后金额</label>
		</td>
	<%--	<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].weight" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">重量</label>
		</td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].taxRate" maxlength="32" type="text" class="inputxt"  style="width:120px;" value="0" datatype="num" ignore="ignore"/>
			<label class="Validform_label" style="display: none;">税率(%)</label>
		</td>
		<%-- <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].price" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">不含税单价</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].amount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">不含税金额</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].discountPrice" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">不含税折后单价</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpRstockbillentryList[#index#].discountAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;"  datatype="num" ignore="ignore"/>
              <label class="Validform_label" style="display: none;">不含税折后金额</label>
          </td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly datatype="num" ignore="ignore"/>
			<label class="Validform_label" style="display: none;">税额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].kfDate" maxlength="32" type="text" class="Wdate"  onClick="WdatePicker()" style="width:80px;" onClick="WdatePicker()"  style="width:80px;" onchange="setPeriodDate('#index#','tScDrpRstockbillentryList')"/>
			<label class="Validform_label" style="display: none;">生产日期</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].kfPeriod" maxlength="32" type="text" class="inputxt"  style="width:120px;"/>
			<label class="Validform_label" style="display: none;">保质期</label>
		</td>
		<td align="left" bgcolor="white">
			<%--<input name="tScDrpRstockbillentryList[#index#].kfDateType" maxlength="32" type="text" class="inputxt"  style="width:120px;" />--%>
			<t:dictSelect field="tScDrpRstockbillentryList[#index#].kfDateType_" width="100px" type="list"
						  showDefaultOption="true" extendJson="{disabled:disabled}"
						  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false"
						  title="保质期类型"></t:dictSelect>
			<label class="Validform_label" style="display: none;">保质期类型</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].periodDate" maxlength="32" type="text" class="Wdate"  onClick="WdatePicker()" style="width:80px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">有效期至</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].billNoSrc" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">源单编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].billNoOrder" maxlength="32" type="text" class="inputxt"  style="width:120px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">订单编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpRstockbillentryList[#index#].note" maxlength="32" type="text" class="inputxt"  style="width:120px;" />
			<label class="Validform_label" style="display: none;">备注</label>
		</td>
	</tr>
	</tbody>
</table>
<!-- 页脚显示 -->
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 0px;left:60px;width:100%">
	<div style="width: 33%;float: left">
		<label class="Validform_label footlabel">制单人: </label>
		<span class="inputxt footspan">${sessionScope.user.realName}</span>
	</div>
	<div style="width: 33%;float: left">
		<label class="Validform_label footlabel">审核人 : </label>
		<span class="inputxt footspan">${auditor}</span>
	</div>
	<div style="width: 34%;float: left">
		<label class="Validform_label footlabel">审核日期: </label>
		<span class="inputxt footspan">${auditDate}</span>
	</div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpRstockbill.js"></script>