<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>发货管理</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<%--<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>--%>
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
<%--<input id="configName" value="tScDrpStockbillentryList" type="hidden">--%>
<input type="hidden" id="checkDate" value="${checkDate}">
<input id="load" value="${load}" type="hidden">
<t:formvalid formid="formobj" dialog="true" tranType="1506" title="发货单" usePlugin="password" layout="table" tiptype="1" action="tScDrpStockbillController.do?doUpdate" beforeSubmit="checkBillDate" tableName="t_sc_drp_stockbill">
	<input id="id" name="id" type="hidden" value="${tScDrpStockbillPage.id }">
	<input id="createName" name="createName" type="hidden" value="${tScDrpStockbillPage.createName }">
	<input id="createBy" name="createBy" type="hidden" value="${tScDrpStockbillPage.createBy }">
	<input id="createDate" name="createDate" type="hidden" value="${tScDrpStockbillPage.createDate }">
	<input id="updateName" name="updateName" type="hidden" value="${tScDrpStockbillPage.updateName }">
	<input id="updateBy" name="updateBy" type="hidden" value="${tScDrpStockbillPage.updateBy }">
	<input id="updateDate" name="updateDate" type="hidden" value="${tScDrpStockbillPage.updateDate }">
	<input id="tranType" name="tranType" type="hidden" value="1506">
	<input id="amount" name="amount" type="hidden" value="${tScDrpStockbillPage.amount }">
	<input id="checkAmount" name="checkAmount" type="hidden" value="${tScDrpStockbillPage.checkAmount }">
	<input id="affirmStatus" name="affirmStatus" type="hidden" value="${tScDrpStockbillPage.affirmStatus }">
	<input id="affirmID" name="affirmID" type="hidden" value="${tScDrpStockbillPage.affirmID }">
	<input id="interIDSrc" name="interIDSrc" type="hidden" value="${tScDrpStockbillPage.interIDSrc }">
	<input id="checkerID" name="checkerID" type="hidden" value="${tScDrpStockbillPage.checkerID }">
	<input id="billerID" name="billerID" type="hidden" value="${tScDrpStockbillPage.billerID }">
	<input id="accountID" name="accountID" type="hidden" value="${tScDrpStockbillPage.accountID }">
	<input id="checkDate" name="checkDate" type="hidden" value="${tScDrpStockbillPage.checkDate }">
	<input id="checkState" name="checkState" type="hidden" value="${tScDrpStockbillPage.checkState }">
	<input id="cancellation" name="cancellation" type="hidden" value="${tScDrpStockbillPage.cancellation }">
	<input id="dealerID" name="dealerID" type="hidden" value="${tScDrpStockbillPage.dealerID }">
	<input id="empID" name="empID" type="hidden" value="${tScDrpStockbillPage.empID }">
	<input id="deptID" name="deptID" type="hidden" value="${tScDrpStockbillPage.deptID }">
	<input id="rStockId" name="rStockId" type="hidden" value="${tScDrpStockbillPage.rStockId }">
	<input id="sonID" name="sonID" type="hidden" value="${tScDrpStockbillPage.sonID}">
	<input id="billNo" name="billNo" type="hidden" value="${tScDrpStockbillPage.billNo}">
	<input id="date" name="date" type="hidden" value="${tScDrpStockbillPage.date}">
	<input id="version" name="version" type="hidden" value="${tScDrpStockbillPage.version }"/>
	<input id="json" name="json" type="hidden"/>
	<input id="iscreditmgr" type="hidden"/>
	<input id="countAmount" type="hidden"/>
	<input id="creditamount" type="hidden"/>
	<input id="delearType" value="${delearType}" type="hidden"/>
	<table cellpadding="0" cellspacing="1" class="formtable" style="position: absolute;z-index: 50;width: 100% ">
		<tr>
			<td align="right" style="width: 6%">
				<label class="Validform_label">下游经销商:</label>
			</td>
			<td class="value">
				<input id="dealerName" name="dealerName" type="text" value="${tScDrpStockbillPage.dealerName}" style="width: 150px" class="inputxt <c:if test="${(!delearType eq 'CDealer') || (!delearType eq '')}">popup-select</c:if>" datatype="*" <c:if test="${delearType eq 'CDealer'}">readonly</c:if><c:if test="${delearType eq ''}">readonly</c:if>>
				<span class="Validform_checktip"> * </span>
				<label class="Validform_label" style="display: none;">下游经销商</label>
			</td>
			<td align="right">
				<label class="Validform_label">经办人:</label>
			</td>
			<td class="value">
				<input id="empName" name="empName" type="text" value="${tScDrpStockbillPage.empName}" style="width: 150px" class="inputxt popup-select" datatype="*"/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">经办人</label>
			</td>
			<td align="right">
				<label class="Validform_label">部门:</label>
			</td>
			<td class="value">
				<input id="deptName" name="deptName" type="text" value="${tScDrpStockbillPage.deptName}" style="width: 150px" class="inputxt popup-select" datatype="*"/>
				<span class="Validform_checktip">*</span>
				<label class="Validform_label" style="display: none;">部门</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">仓库:</label>
			</td>
			<td class="value">
				<input id="rStockName" name="rStockName" value="${tScDrpStockbillPage.rStockName}" type="text" style="width: 150px" class="inputxt popup-select" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">仓库</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系人:</label>
			</td>
			<td class="value">
				<input id="contact" name="contact" value="${tScDrpStockbillPage.contact}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">联系人</label>
			</td>
			<td align="right">
				<label class="Validform_label">手机号码:</label>
			</td>
			<td class="value">
				<input id="mobilePhone" name="mobilePhone" value="${tScDrpStockbillPage.mobilePhone}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">手机号码</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">电话:</label>
			</td>
			<td class="value">
				<input id="phone" name="phone" value="${tScDrpStockbillPage.phone}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">传真:</label>
			</td>
			<td class="value">
				<input id="fax" name="fax" value="${tScDrpStockbillPage.fax}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">传真</label>
			</td>
			<td align="right">
				<label class="Validform_label">联系地址:</label>
			</td>
			<td class="value">
				<input id="address" name="address" value="${tScDrpStockbillPage.address}" type="text" style="width: 150px" class="inputxt" />
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
				<input id="rebateAmount" name="rebateAmount" value="${tScDrpStockbillPage.rebateAmount}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">优惠金额</label>
			</td>
			<td align="right">
				<label class="Validform_label">物流费用:</label>
			</td>
			<td class="value">
				<input id="freight" name="freight"  value="${tScDrpStockbillPage.freight}" type="text" style="width: 150px" class="inputxt" readonly />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">物流费用</label>
			</td>
			<td align="right">
				<label class="Validform_label">结算账户:</label>
			</td>
			<td class="value">
				<input id="accountName" name="accountName"  value="${tScDrpStockbillPage.accountName}" type="text" style="width: 150px" class="inputxt popup-select"  />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">结算账号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">本次收款:</label>
			</td>
			<td class="value">
				<input id="curPayAmount" name="curPayAmount" value="${tScDrpStockbillPage.curPayAmount}" type="text" style="width: 150px" class="inputxt" />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">本次收款</label>
			</td>
			<td align="right">
				<label class="Validform_label">重量:</label>
			</td>
			<td class="value">
				<input id="weight" name="weight" type="text" value="${tScDrpStockbillPage.weight}"  style="width: 150px" class="inputxt" readonly />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">重量</label>
			</td>
			<td align="right">
				<label class="Validform_label">应收金额:</label>
			</td>
			<td class="value">
				<input id="allAmount" name="allAmount" value="${tScDrpStockbillPage.allAmount}" type="text" style="width: 150px" class="inputxt" readonly />
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">应收金额</label>
			</td>
		</tr>
		<tr>
				<%--<td align="right">
                      <label class="Validform_label">丢失金额:</label>
                  </td>
                  <td class="value">
                      <input id="amountLoss" name="amountLoss" type="text" style="width: 150px" class="inputxt"  readonly />
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">丢失金额</label>
                  </td>--%>
			<td align="right">
				<label class="Validform_label">源单类型:</label>
			</td>
			<td class="value">
				<%--<t:dictSelect field="classIDSrc" id="classIDSrc" type="list" extendJson="{'readonly':'readonly'}" typeGroupCode="sf_01" hasLabel="false" title="源单类型" defaultVal="${tScDrpStockbillPage.classIDSrc}"></t:dictSelect>--%>
				<input id="classIDSrc" name="classIDSrc" type="text" style="width: 150px" class="inputxt"  readonly>
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">源单类型</label>
			</td>
			<td align="right">
				<label class="Validform_label">分支机构:</label>
			</td>
			<td class="value">
				<input id="sonName" name="sonName" value="${tScDrpStockbillPage.sonName}" type="text" style="width: 150px" class="inputxt" value="${sessionScope.user.currentDepart.departname}" readonly>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">分支机构</label>
			</td>
			<td align="right"></td>
			<td class="value"></td>

				<%-- <td align="right">
                      <label class="Validform_label">源单编号:</label>
                  </td>
                  <td class="value">
                      <input id="billNoSrc" name="billNoSrc" type="text" style="width: 150px" class="inputxt"  readonly />
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">源单编号</label>
                  </td>--%>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">摘要:</label>
			</td>
			<td class="value" colspan="5">
				<input id="explanation" name="explanation" value="${tScDrpStockbillPage.explanation}" type="text" style="width: 600px" class="inputxt" />
				<span class="Validform_checktip"> </span>
				<label class="Validform_label" style="display: none;">摘要</label>
			</td>
		</tr>
	</table>
	<div style="width: auto;height: 200px;margin-top: 100px;">
			<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
		<div style="width:800px;height:1px;"></div>
		<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
			<t:tab href="tScDrpStockbillController.do?tScDrpStockbillentryList&id=${tScDrpStockbillPage.id}"
				   icon="icon-search" title="商品信息" id="tScDrpStockbillentry"></t:tab>
		</t:tabs>
			<%--<div class="panel-header" id="goodsHeader">--%>
			<%--<div class="panel-title">商品</div>--%>
			<%--<div class="panel-tool"><a href="javascript:void(0)" class="layou-button-down"></a></div>--%>
			<%--</div>--%>
			<%--<div class="panel-body layout-body" id="southBody" ></div>--%>
	</div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
	<tbody id="add_tScDrpStockbillentry_table_template">
	<tr>
		<td align="center" bgcolor="#F6FCFF">
			<input name="tScDrpStockbillentryList[#index#].id" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].createName" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].createBy" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].createDate" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].updateName" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].updateBy" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].updateDate" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].fid" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].indexNumber" id="tScDrpStockbillentryList[#index#].indexNumber" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].kfDateType"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].itemID"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].stockID"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].salesID"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].isFreeGift" value="2" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].billQty" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].fidSrc" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].interIDSrc" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].fidOrder" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].interIDOrder" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].classIDSrc" type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].freeGifts" type="hidden" value="0"/>
			<input name="tScDrpStockbillentryList[#index#].basicUnitID"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].secUnitID"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].secCoefficient"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].secQty"  type="hidden"/>
			<input name="tScDrpStockbillentryList[#index#].price" type="hidden" />
			<input name="tScDrpStockbillentryList[#index#].amount" type="hidden" />
			<input name="tScDrpStockbillentryList[#index#].discountPrice" type="hidden" />
			<input name="tScDrpStockbillentryList[#index#].discountAmount" type="hidden" />
			<input name="tScDrpStockbillentryList[#index#].basicQty" type="hidden" />
			<input name="tScDrpStockbillentryList[#index#].coefficient" type="hidden" />
			<input id="tScDrpStockbillentryList[#index#].weight" name="tScDrpStockbillentryList[#index#].weight" type="hidden"/>
			<div style="width: 25px;background-color: white;" name="xh">1</div>
		</td>
		<td align="center" bgcolor="white">
			<div style="width: 80px;background-color: white;">
				<a name="addTScDrpStockbillentryBtn[#index#]" id="addTScDrpStockbillentryBtn[#index#]" href="#" onclick="clickAddTScDrpStockbillentryBtn('#index#');"></a>
				<a name="delTScDrpStockbillentryBtn[#index#]" id="delTScDrpStockbillentryBtn[#index#]" href="#"  onclick="clickDelTScDrpStockbillentryBtn('#index#');"></a>
			</div>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].number" maxlength="32" onkeypress="keyDownInfo('#index#','item',event)" onblur="orderListStockBlur('#index#','itemID','number');" type="text" class="inputxt popup-select"  style="width:105px;" datatype="*"/>
			<label class="Validform_label" style="display: none;">商品编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].name" maxlength="32" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226);" readonly datatype="*"/>
			<label class="Validform_label" style="display: none;">商品名称</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].model" maxlength="32" type="text" class="inputxt"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">规格</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].barCode" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly />
			<label class="Validform_label" style="display: none;">条形码</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].stockName" datatype="*" id="tScDrpStockbillentryList[#index#].stockName" maxlength="32" onkeypress="keyDownInfo('#index#','stock',event)" onblur="orderListStockBlur('#index#','stockID','stockName');" type="text" class="inputxt popup-select"  style="width:85px;" datatype="*"/>
			<label class="Validform_label" style="display: none;">仓库</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].batchNo" maxlength="32" type="text" class="inputxt"  style="width:80px;" />
			<label class="Validform_label" style="display: none;">批号</label>
		</td>
		<td align="left" bgcolor="white">
			<input id="tScDrpStockbillentryList[#index#].unitID" name="tScDrpStockbillentryList[#index#].unitID"   style="width:50px;"/>
			<label class="Validform_label" style="display: none;">单位</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].qty" maxlength="32" type="text" class="inputxt"  datatype="num" style="width:70px;"  onblur="changePrice('#index#')"/>
			<label class="Validform_label" style="display: none;">数量</label>
		</td>
		<%--<td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].basicUnitID" id="tScDrpStockbillentryList[#index#].basicUnitID" maxlength="32" type="text" class="inputxt"  style="width:70px;" />
              <label class="Validform_label" style="display: none;">基本单位</label>
          </td>--%>
		<%--<td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].coefficient" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
              <label class="Validform_label" style="display: none;">换算率(%)</label>
          </td>--%>
		<%-- <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].basicQty" maxlength="32" type="text" class="inputxt" datatype="num" style="width:70px;background-color:rgb(226,226,226);" readonly/>
              <label class="Validform_label" style="display: none;">基本数量</label>
          </td>--%>
		<%-- <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].secUnitID"  id="tScDrpStockbillentryList[#index#].secUnitID"  style="width:70px;" />
              <label class="Validform_label" style="display: none;">辅助单位</label>
          </td>--%>
		<%-- <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].secCoefficient" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
              <label class="Validform_label" style="display: none;">辅助换算率</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].secQty" maxlength="32" type="text" class="inputxt"   style="width:70px;background-color:rgb(226,226,226);" readonly/>
              <label class="Validform_label" style="display: none;">辅助数量</label>
          </td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].taxPriceEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" />
			<label class="Validform_label" style="display: none;">单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].taxAmountEx" maxlength="32" type="text" class="inputxt"  style="width:70px;" />
			<label class="Validform_label" style="display: none;">金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].discountRate" maxlength="32" type="text" class="inputxt"  style="width:65px;" value="100" />
			<label class="Validform_label" style="display: none;">折扣率(%)</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].taxPrice" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly />
			<label class="Validform_label" style="display: none;">折后单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].inTaxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px;" onchange="setAllAmount()"/>
			<label class="Validform_label" style="display: none;">折后金额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].itemWeight" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly />
			<label class="Validform_label" style="display: none;">重量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].taxRate" maxlength="32" type="text" class="inputxt"  style="width:70px;" value="0" />
			<label class="Validform_label" style="display: none;">税率(%)</label>
		</td>
		<%--<td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].price" maxlength="32" type="text" class="inputxt"   style="width:70px;" />
              <label class="Validform_label" style="display: none;">不含税单价</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].amount" maxlength="32" type="text" class="inputxt"   style="width:70px;" />
              <label class="Validform_label" style="display: none;">不含税金额</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].taxPrice" maxlength="32" type="text" class="inputxt"   style="width:80px;" />
              <label class="Validform_label" style="display: none;">不含税折后单价</label>
          </td>
          <td align="left" bgcolor="white">
              <input name="tScDrpStockbillentryList[#index#].inTaxAmount" maxlength="32" type="text" class="inputxt"    style="width:80px;" />
              <label class="Validform_label" style="display: none;">不含税折后金额</label>
          </td>--%>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].taxAmount" maxlength="32" type="text" class="inputxt"  style="width:70px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">税额</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].date" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:80px;" onchange="setPeriodDate('#index#','tScDrpStockbillentryList')"/>
			<label class="Validform_label" style="display: none;">生产日期</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].kfperiod" maxlength="32" type="text" class="inputxt"  style="width:80px;" <%--onchange="setPeriodDate(0,'tScDrpStockbillentryList')"--%>/>
			<label class="Validform_label" style="display: none;">保质期</label>
		</td>
		<td align="left" bgcolor="white">
			<t:dictSelect field="tScDrpStockbillentryList[#index#].kfDateType_" type="list" width="70" typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="0" hasLabel="false"  title="保质期类型"></t:dictSelect>
			<label class="Validform_label" style="display: none;">保质期类型</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].periodDate" maxlength="32" type="text" class="Wdate" onClick="WdatePicker()"  style="width:85px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">有效期至</label>
		</td>

		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].costPrice" maxlength="32" type="text" class="inputxt"   style="width:65px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">成本单价</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].costAmount" maxlength="32" type="text" class="inputxt"  style="width:65px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">成本金额</label>
		</td>

		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].salesName" maxlength="10" type="text" class="inputxt" readonly="readonly" style="width:65px;background-color:rgb(226,226,226)">
			<label class="Validform_label" style="display: none;">促销类型</label>
		</td>
		<td align="left" bgcolor="white">
			<t:dictSelect field="tScDrpStockbillentryList[#index#].freeGifts_" width="70px" type="list"
						  extendJson="{datatype:select1,onChange:setPrice(this)}" showDefaultOption="false"
						  typeGroupCode="sf_01" defaultVal="0" hasLabel="false" title="赠品标记"></t:dictSelect>
			<label class="Validform_label" style="display: none;">赠品标记</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].commitQty" maxlength="32" type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">退货数量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].stockQty" maxlength="32" type="text" class="inputxt"  style="width:80px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">确认收货数量</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].billNoSrc"  maxlength="50" type="text" class="inputxt"  style="width:100px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">源单编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].classIDName"  maxlength="32" type="text" class="inputxt"  style="width:90px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">源单类型</label>
		</td>

		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].billNoOrder" maxlength="50" type="text" class="inputxt"  style="width:100px;background-color:rgb(226,226,226);" readonly/>
			<label class="Validform_label" style="display: none;">订单编号</label>
		</td>
		<td align="left" bgcolor="white">
			<input name="tScDrpStockbillentryList[#index#].note" maxlength="255" type="text" class="inputxt"  style="width:180px;" />
			<label class="Validform_label" style="display: none;">备注</label>
		</td>
	</tr>
	</tbody>
</table>
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 0px;left:10px;width: 100%;">
	<div style="width: 16%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">制单人: </label>
		<span  class="inputxt footspan">${sessionScope.user.realName}</span>
	</div>
	<div style="width: 16%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">审核人: </label>
		<span  class="inputxt footspan">${auditor}</span>
	</div>
	<div style="width: 16%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">审核日期: </label>
		<span  class="inputxt footspan">${auditDate}</span>
	</div>
	<div style="width: 16%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">确认人: </label>
		<span  class="inputxt footspan">${affirmtor}</span>
	</div>
	<div style="width: 16%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">确认时间: </label>
		<span  class="inputxt footspan">${affirmDate}</span>
	</div>
	<div style="width: 16%;display:inline;float: left" align="left">
		<label  class="Validform_label footlabel">丢失金额: </label>
		<span  class="inputxt footspan">${amountLoss}</span>
	</div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScDrpStockbill.js"></script>