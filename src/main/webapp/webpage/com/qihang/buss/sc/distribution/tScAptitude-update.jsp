<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>资格审查</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
	<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	<script type="text/javascript">
		//编写自定义JS代码
	</script>
	<style>
		/**20160629添加页脚样式 **/
		html, body, form {
			height: 100%
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
	</style>
</head>
<body>
<input type="hidden" id="checkDate" value="${checkDate}">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" title="资格审查" layout="table" action="tScAptitudeController.do?doUpdate" tiptype="1" tranType="77" tableName="t_sc_aptitude" beforeSubmit="checkBillDate" isCancel="false">
	<input id="id" name="id" type="hidden" value="${tScAptitudePage.id }"/>
	<input id="createName" name="createName" type="hidden" value="${tScAptitudePage.createName }"/>
	<input id="createBy" name="createBy" type="hidden" value="${tScAptitudePage.createBy }"/>
	<input id="createDate" name="createDate" type="hidden" value="${tScAptitudePage.createDate }"/>
	<input id="updateName" name="updateName" type="hidden" value="${tScAptitudePage.updateName }"/>
	<input id="updateBy" name="updateBy" type="hidden" value="${tScAptitudePage.updateBy }"/>
	<input id="updateDate" name="updateDate" type="hidden"value="${tScAptitudePage.updateDate }"/>
	<input id="deleted" name="deleted" type="hidden" value="${tScAptitudePage.deleted }"/>
	<input id="disabled" name="disabled" type="hidden" value="${tScAptitudePage.disabled }"/>
	<input id="version" name="version" type="hidden" value="${tScAptitudePage.version }"/>
	<input id="tranType" name="tranType" type="hidden" value="77"/>
	<input id="itemID" name="itemID" type="hidden" value="${tScAptitudePage.itemID }"/>
	<input id="checkerID" name="checkerID" type="hidden" value="${tScAptitudePage.checkerID }"/>
	<input id="billerID" name="billerID" type="hidden" value="${tScAptitudePage.billerID}"/>
	<input id="sonID" name="sonID" type="hidden" value="${tScAptitudePage.sonID}"/>
	<input name="billNo" value="${tScAptitudePage.billNo}" type="hidden"/>
	<input name="date" value="${tScAptitudePage.date}" type="hidden"/>
	<input name="delearType" value="${delearType}" type="hidden"/>
	<table  cellpadding="0" cellspacing="1" class="formtable" style="position: absolute;z-index: 50;width: 100% "/>
	<tr>
		<td align="right" style="width: 9%">
			<label class="Validform_label">经销商:</label>
		</td>
		<td class="value" style="width: 24%">
			<input id="itemIDName" name="distributor" value="${distributor}" type="text" style="width: 150px" class="inputxt popup-select" <c:if test="${delearType eq 'CDealer'}">readonly</c:if><c:if test="${delearType eq ''}">readonly</c:if>/>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">经销商</label>
		</td>
		<td align="right">
			<label class="Validform_label">法人代表:</label>
		</td>
		<td class="value">
			<input id="corperate" name="corperate" type="text" value="${tScAptitudePage.corperate}" style="width: 150px" class="inputxt" maxlength="40"/>
			<span class="Validform_checktip"> </span>
			<label class="Validform_label" style="display: none;">法人代表</label>
		</td>
		<td align="right">
			<label class="Validform_label">联系人:</label>
		</td>
		<td class="value">
			<input id="contact" name="contact" type="text"  value="${tScAptitudePage.contact}" style="width: 150px" class="inputxt" maxlength="20" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">联系人</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">手机号码: </label>
		</td>
		<td class="value">
			<input id="mobilePhone" name="mobilePhone" type="text" value="${tScAptitudePage.mobilePhone}" style="width: 150px" class="inputxt" datatype="m" ignore="ignore"/>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">手机号码</label>
		</td>
		<td align="right">
			<label class="Validform_label">电话:</label>
		</td>
		<td class="value">
			<input id="phone" name="phone" type="text" value="${tScAptitudePage.phone}" style="width: 150px" class="inputxt" datatype="po" ignore="ignore" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">电话</label>
		</td>
		<td align="right">
			<label class="Validform_label">传真:</label>
		</td>
		<td class="value">
			<input id="fax" name="fax" type="text" value="${tScAptitudePage.fax}" style="width: 150px" class="inputxt" datatype="f" ignore="ignore" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">传真</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label"> 注册资金:</label>
		</td>
		<td class="value">
			<input id="regCapital" name="regCapital" value="${tScAptitudePage.regCapital}" type="text" style="width: 150px" class="inputxt" datatype="d" onchange="checkRegCapital(this)"  ignore="ignore" maxlength="13" errorMsg="请填写正确的注册资金" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">注册资金</label>
		</td>
		<td align="right">
			<label class="Validform_label">联系地址:</label>
		</td>
		<td class="value" colspan="3">
			<input id="address" name="address" type="text" value="${tScAptitudePage.address}" style="width: 575px" class="inputxt" maxlength="100"/>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">联系地址</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">企业性质:</label>
		</td>
		<td class="value">
			<input id="economyKind" name="economyKind" value="${tScAptitudePage.economyKind}" type="text" style="width: 150px" class="inputxt" maxlength="40" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">企业性质</label>
		</td>
		<td align="right">
			<label class="Validform_label"> 渠道: </label>
		</td>
		<td class="value">
			<input id="trench" name="trench" type="text" value="${tScAptitudePage.trench}" style="width: 150px" class="inputxt" maxlength="100" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">渠道</label>
		</td>
		<td align="right">
			<label class="Validform_label">代理产品: </label>
		</td>
		<td class="value">
			<input id="itemName" name="itemName" type="text" value="${tScAptitudePage.itemName}" style="width: 150px" class="inputxt" maxlength="100"/>
			<span class="Validform_checktip"> </span>
			<label class="Validform_label" style="display: none;">代理产品</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">预计销量（年）: </label>
		</td>
		<td class="value">
			<input id="planQty" name="planQty" type="text" value="${tScAptitudePage.planQty}" style="width: 150px" class="inputxt" datatype="float" ignore="ignore"/>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">预计销量（年）</label>
		</td>
		<td align="right">
			<label class="Validform_label">销售渠道|A类:</label>
		</td>
		<td class="value">
			<input id="trenchA" name="trenchA" type="text" value="${tScAptitudePage.trenchA}" style="width: 150px" class="inputxt" datatype="number" ignore="ignore"/>
			<span class="Validform_checktip"> </span>
			<label class="Validform_label" style="display: none;">销售渠道|A类</label>
		</td>
		<td align="right">
			<label class="Validform_label">销售渠道|B类:</label>
		</td>
		<td class="value">
			<input id="trenchB" name="trenchB" type="text" value="${tScAptitudePage.trenchB}" style="width: 150px" class="inputxt" datatype="number" ignore="ignore" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">销售渠道|B类</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">销售渠道|C类:</label>
		</td>
		<td class="value">
			<input id="trenchC" name="trenchC" type="text" value="${tScAptitudePage.trenchC}" style="width: 150px" class="inputxt" datatype="number" ignore="ignore"/>
			<span class="Validform_checktip"> </span>
			<label class="Validform_label" style="display: none;">销售渠道|C类</label>
		</td>
		<td align="right">
			<label class="Validform_label">销售渠道|其他: </label>
		</td>
		<td class="value">
			<input id="trenchO" name="trenchO" type="text" value="${tScAptitudePage.trenchO}" style="width: 150px" class="inputxt" datatype="number" ignore="ignore" />
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">销售渠道|其他</label>
		</td>
		<td align="right">
			<label class="Validform_label">是否合格经销商:</label>
		</td>
		<td class="value">
			<t:dictSelect field="eligibility" showDefaultOption="false" type="list" typeGroupCode="sf_01" extendJson="{'datatype':'*'}" defaultVal="${tScAptitudePage.eligibility}" hasLabel="false" title="是否合格经销商"></t:dictSelect>
			<span class="Validform_checktip">* </span>
			<label class="Validform_label" style="display: none;">是否合格经销商</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">分支机构:</label>
		</td>
		<td class="value">
			<input id="sonName" name="sonName" type="text" style="width: 150px" class="inputxt"  readonly="readonly" value='${sonName}'/>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">分支机构</label>
		</td>
		<td align="right">
			<label class="Validform_label">摘要:</label>
		</td>
		<td class="value" colspan="3">
			<input id="explanation" name="explanation"  value='${tScAptitudePage.explanation}' type="text" style="width:72.5%" class="inputxt" datatype="*1-126"  ignore="ignore"/>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">摘要</label>
		</td>
	</tr>
	</table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;width:80%">
	<div style="width: 33%;display:inline;float: left" align="left">
		<label class="Validform_label footlabel">制单人: </label>
		<span class="inputxt footspan"> ${sessionScope.user.realName}</span>
	</div>
	<div style="width: 33%;display: inline;float: left" align="left">
		<label class="Validform_label footlabel">审核人: </label>
		<span class="inputxt footspan">${auditor}</span>
	</div>
	<div style="width: 33%;display: inline;float: right" align="left">
		<label class="Validform_label footlabel">审核日期: </label>
		<span class="inputxt footspan">${auditDate} </span>
	</div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/distribution/tScAptitude.js"></script>