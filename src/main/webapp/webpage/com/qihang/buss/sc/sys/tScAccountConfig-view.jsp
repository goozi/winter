<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>账套设置</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function () {
			$(".Validform_checktip").each(function(){
				$(this).hide();
			});

			$("[data-options*=icon-edit]").linkbutton("disable"); //旧版编辑
			//设置数据库类型和驱动类为只读，不能用disabled（后台会读不到数据）
			//$("[name='tSDataSourceList[0].dbType']").attr("disabled","disabled");
			//$("[name='tSDataSourceList[0].driverClass']").attr("disabled","disabled");
			$("[name='tSDataSourceList[0].dbType']").css("display","none");
			$("[name='tSDataSourceList[0].driverClass']").css("display","none");
		});
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScAccountConfigController.do?doUpdate" tiptype="1">
	<input id="id" name="id" type="hidden" value="${tScAccountConfigPage.id }">
	<input id="createName" name="createName" type="hidden" value="${tScAccountConfigPage.createName }">
	<input id="createBy" name="createBy" type="hidden" value="${tScAccountConfigPage.createBy }">
	<input id="createDate" name="createDate" type="hidden" value="${tScAccountConfigPage.createDate }">
	<input id="updateName" name="updateName" type="hidden" value="${tScAccountConfigPage.updateName }">
	<input id="updateBy" name="updateBy" type="hidden" value="${tScAccountConfigPage.updateBy }">
	<input id="updateDate" name="updateDate" type="hidden" value="${tScAccountConfigPage.updateDate }">
	<input id="olddbKey" name="olddbKey" type="hidden" value="${tSDataSourcePage.dbKey }">
	<table cellpadding="0" cellspacing="1" class="formtable" style="margin: 0px auto;width:600px;">
		<tr>
			<th colspan="4" align="center" height="30px"><label class="Validform_label">企业信息</label></th>
		</tr>
		<tr>
			<td align="right" width="10%">
				<label class="Validform_label">
					公司名称:
				</label>
			</td>
			<td class="value" width="23%">
				<input id="companyName" name="companyName" type="text" style="width: 150px" class="inputxt" value='${tScAccountConfigPage.companyName}'><span style="color: red">*</span>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">公司名称</label>
			</td>
			<td align="right" width="10%">
				<label class="Validform_label">联系人:</label>
			</td>
			<td class="value" width="23%">
				<input id="contact" name="contact" type="text" style="width: 150px" class="inputxt" value='${tScAccountConfigPage.contact}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">联系人</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					税号:
				</label>
			</td>
			<td class="value">
				<input id="taxNumber" name="taxNumber" type="text" style="width: 150px" class="inputxt"

					   value='${tScAccountConfigPage.taxNumber}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">税号</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					银行账号:
				</label>
			</td>
			<td class="value">
				<input id="bankAccount" name="bankAccount" type="text" style="width: 150px" class="inputxt"

					   value='${tScAccountConfigPage.bankAccount}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">银行账号</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					公司地址:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="companyAddress" name="companyAddress" type="text" style="width: 91.5%" class="inputxt" value='${tScAccountConfigPage.companyAddress}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">公司地址</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					电话:
				</label>
			</td>
			<td class="value">
				<input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
					   datatype="*,po"
					   value='${tScAccountConfigPage.phone}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">电话</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					传真:
				</label>
			</td>
			<td class="value">
				<input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
					   datatype="*,f"
					   value='${tScAccountConfigPage.fax}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">传真</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					公司网址:
				</label>
			</td>
			<td class="value">
				<input id="companyUrl" name="companyUrl" type="text" style="width: 150px" class="inputxt"
					   datatype="*,url"
					   value='${tScAccountConfigPage.companyUrl}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">公司网址</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					E-Mail:
				</label>
			</td>
			<td class="value">
				<input id="email" name="email" type="text" style="width: 150px" class="inputxt"
					   datatype="*,e"
					   value='${tScAccountConfigPage.email}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">E_Mail</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					注册号:
				</label>
			</td>
			<td class="value">
				<input id="registrationNumber" name="registrationNumber" type="text" style="width: 150px" class="inputxt"

					   value='${tScAccountConfigPage.registrationNumber}'>
							<span class="Validform_checktip">
							</span>
				<label class="Validform_label" style="display: none;">注册号</label>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td colspan="4" align="center" height="30px"><label class="Validform_label">建账信息</label></td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">实体库:</label>
			</td>
			<td class="value">
				<input id="dbKey" name="dbKey" type="text" style="width: 150px" class="inputxt" datatype="s2-10,w1" onchange="doDbkeyChange()" validType="t_sc_account_config,dbKey,id,dataSource_jeecg" value='${tScAccountConfigPage.dbKey}'><span style="color: red">*</span>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">实体库</label>
			</td>
			<td align="right" width="15%">
				<label class="Validform_label">
					账套名称:
				</label>
			</td>
			<td class="value" colspan="1">
				<input name="tSDataSourceList[0].description" maxlength="50" width="340px"  type="text" class="inputxt"  datatype="*" value='${tSDataSourcePage.description}'><span style="color: red">*</span>
				<label class="Validform_label" style="display: none;">账套名称</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">启用年月:</label>
			</td>
			<td class="value">
				<input id="accountStartDate" name="accountStartDate" maxlength="20" type="text" style="width:80px;" value="<fmt:formatDate value='${tScAccountConfigPage.accountStartDate}' type="date" pattern="yyyy-MM"/>" datatype="*"><span style="color: red">*</span>
				<label class="Validform_label" style="display: none;">账套启用年月</label>
					<%--<input class="easyui-combobox ui-text" id="accountStartYear" name="accountStartYear" style="width:150px" >--%>
					<%--<span class="Validform_checktip">--%>
					<%--</span>--%>
					<%--<label class="Validform_label" style="display: none;">账套启用年份</label>--%>
					<%--<input id="accountStartMonth" name="accountStartMonth" type="text" style="width: 150px" class="inputxt">--%>
					<%--<span class="Validform_checktip">--%>
					<%--</span>--%>
					<%--<label class="Validform_label" style="display: none;">账套启用月份</label>--%>
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">负库存选项:</label>
			</td>
			<td class="value" colspan="3">
				<input id="minusInventoryAccount" name="minusInventoryAccount" type="checkbox" value="1" <c:if test="${tScAccountConfigPage.minusInventoryAccount==1}">checked="checked"</c:if>><font color="red">允许允许负库存结账</font>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">允许负库存结账</label><br>
			<%--</td>--%>
			<%--<td align="right">--%>
				<%--<label class="Validform_label">允许负库存出库:</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
				<input id="minusInventorySl" name="minusInventorySl" type="checkbox" value="1" <c:if test="${tScAccountConfigPage.minusInventorySl==1}">checked="checked"</c:if>><font color="red">允许负库存出库</font>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">允许负库存出库</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					数据库地址:
				</label>
			</td>
			<td class="value">
				<input name="tSDataSourceList[0].dbIp" maxlength="40" type="text" class="inputxt" value="${tSDataSourcePage.dbIp}" datatype="*"  ><span style="color: red">*(Ip)</span>
				<label class="Validform_label" style="display: none;">数据库地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					数据库端口:
				</label>
			</td>
			<td class="value">
				<input name="tSDataSourceList[0].dbPort" maxlength="50" type="text" style="width: 40px" class="inputxt" value="${tSDataSourcePage.dbPort}" datatype="*" ><%--${tSDataSourcePage.dbPassword}--%>
				<label class="Validform_label" style="display: none;">数据库端口</label>
				实例名：<input name="tSDataSourceList[0].dbName" maxlength="50" type="text" style="width: 52px" class="inputxt" value="${tSDataSourcePage.dbName}" datatype="*" ><span style="color: red">*</span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					数据库连接:
				</label>
			</td>
			<td class="value" colspan="3">
				<input id="url" name="tSDataSourceList[0].url" maxlength="100" style="width: 91.5%"  type="text" class="inputxt" value="${tSDataSourcePage.url}" datatype="*" readonly="readonly"><span style="color: red">*</span>
				<label class="Validform_label" style="display: none;">数据库连接</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					数据库用户:
				</label>
			</td>
			<td class="value">
				<input name="tSDataSourceList[0].dbUser" maxlength="50" type="text" class="inputxt" value="${tSDataSourcePage.dbUser}" datatype="*"  ><span style="color: red">*<!--(请使用有创建新数据库、用户、并可以授权的用户，如mysql建议使用root用户,sql server建议使用sa用户,oracle建议使用system用户)--></span>
				<label class="Validform_label" style="display: none;">数据库用户</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					数据库密码:
				</label>
			</td>
			<td class="value">
				<input name="tSDataSourceList[0].dbPassword" maxlength="50" type="password" class="inputxt" value="******" datatype="*" ><span style="color: red">*</span><%--${tSDataSourcePage.dbPassword}--%>
				<label class="Validform_label" style="display: none;">数据库密码</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					驱动类:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="tSDataSourceList[0].driverClass" width="70" type="list"
							  showDefaultOption="true"
							  typeGroupCode="driverClass" defaultVal="${tSDataSourcePage.driverClass}" hasLabel="false" title="驱动类"></t:dictSelect>${tSDataSourcePage.driverClass}<span style="color: red">*</span>
				<label class="Validform_label" style="display: none;">驱动类</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					数据库类型:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="tSDataSourceList[0].dbType" width="70" type="list"
							  showDefaultOption="true"
							  typeGroupCode="dbtype" defaultVal="${tSDataSourcePage.dbType}" hasLabel="false" title="数据库类型"></t:dictSelect>${tSDataSourcePage.dbType}<span style="color: red">*</span>
				<label class="Validform_label" style="display: none;">数据库类型</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script src = "webpage/com/qihang/buss/sc/sys/tScAccountConfig.js"></script>