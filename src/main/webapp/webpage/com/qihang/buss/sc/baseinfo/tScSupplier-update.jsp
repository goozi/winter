<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>供应商</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  function doName() {
	  var getName=$("#name").val()
	  var pinyin = getPinYin(getName);

	  $("#shortName").val(pinyin);
  }

  </script>
	 <style>
		 @media (min-width: 768px) and (max-width: 1366px){
			 #address{
				 width:81%;
			 }

			 #note{
				 width: 81%;
			 }
		 }

		 @media (min-width: 600px) and (max-width: 1280px) {
			 #address{
				 width:82.5%;
			 }
			 #note{
				 width: 82.5%;
			 }

		 }
		 @media (min-width: 768px) and(max-width: 1024px) {
			 #address{
				 width:88.5%;
			 }

			 #note{
				 width: 88.5%;
			 }
		 }
		 @media (min-width: 600px) and(max-width: 800px) {
			 #address{
				 width:93.5%;
			 }

			 #note{
				 width: 93.5%;
			 }
		 }
	 </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" beforeSubmit="checkName()" action="tScSupplierController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScSupplierPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScSupplierPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScSupplierPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScSupplierPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScSupplierPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScSupplierPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScSupplierPage.updateDate }">
					<input id="parentID" name="parentID" type="hidden" value="${tScSupplierPage.parentID }">
					<input id="level" name="level" type="hidden" value="${tScSupplierPage.level }">
					<input id="disabled" name="disabled" type="hidden" value="${tScSupplierPage.disabled }">
					<input id="deleted" name="deleted" type="hidden" value="${tScSupplierPage.deleted }">
					<input id="version" name="version" type="hidden" value="${tScSupplierPage.version }">
					<input id="count" name="count" type="hidden" value="${tScSupplierPage.count }">
					<input id="tranType" name="tranType" type="hidden" value="${tranType}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" style="width: 9%">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value"style="width: 24%" >
							<input id="number" name="number" type="text" style="width: 150px" class="inputxt"
										 datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点"
								     value='${tScSupplierPage.number}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">编号</label>
						</td>
						<td align="right" style="width: 9%">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value"style="width: 24%">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
									               datatype="*1-50" onchange="doName()"
										       value='${tScSupplierPage.name}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right" style="width: 9%">
							<label class="Validform_label">
								简称:
							</label>
						</td>
						<td class="value"style="width: 24%">
							<input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"
								   datatype="*"  readonly="readonly"
								   value='${tScSupplierPage.shortName}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">简称</label>
						</td>
					</tr>
					<tr>

						<td align="right">
							<label class="Validform_label">
								助记码:
							</label>
						</td>
						<td class="value">
						     	 <input id="shortNumber" name="shortNumber" type="text" style="width: 150px" class="inputxt"
										validType="t_sc_supplier,shortNumber,id"
										       value='${tScSupplierPage.shortNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">助记码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								法人代表:
							</label>
						</td>
						<td class="value">
							<input id="corperate" name="corperate" type="text" style="width: 150px" class="inputxt"

								   value='${tScSupplierPage.corperate}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">法人代表</label>
						</td>

						<td align="right">
							<label class="Validform_label">
								联系人:
							</label>
						</td>
						<td class="value">
							<input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"

								   value='${tScSupplierPage.contact}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">联系人</label>
						</td>
					</tr>

					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号码:
							</label>
						</td>
						<td class="value">
						     	 <input id="mobilePhone" name="mobilePhone" type="text" style="width: 150px" class="inputxt"
									 datatype="m" ignore=ignore
										       value='${tScSupplierPage.mobilePhone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
									 datatype="po" ignore=ignore
										       value='${tScSupplierPage.phone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								传真:
							</label>
						</td>
						<td class="value" >
							<input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
								datatype="f" ignore=ignore
								   value='${tScSupplierPage.fax}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">传真</label>
						</td>
					</tr>
			<tr>

				<td align="right">
					<label class="Validform_label">
						供应商分类:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="typeID" type="list"
								  typeGroupCode="SC_SUPPLIER_TYPE" defaultVal="${tScSupplierPage.typeID}" hasLabel="false"  title="供应商分类"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">供应商分类</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						联系地址:
					</label>
				</td>
				<td class="value" colspan="3">
					<input id="address" name="address" type="text" style="width: 80%" class="inputxt"
						datatype="*1-100" ignore=ignore
						   value='${tScSupplierPage.address}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">联系地址</label>
				</td>
			</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								营业执照号:
							</label>
						</td>
						<td class="value">
							<input id="licence" name="licence" type="text" style="width: 150px" class="inputxt"

								   value='${tScSupplierPage.licence}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">营业执照号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								城市:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="city" type="list"
										  typeGroupCode="SC_CITY" defaultVal="${tScSupplierPage.city}" hasLabel="false"  title="城市"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">城市</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								区域:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="regionID" type="list"
										  typeGroupCode="SC_REGION" defaultVal="${tScSupplierPage.regionID}" hasLabel="false"  title="区域"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">区域</label>
						</td>


					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						邮政编码:
					</label>
				</td>
				<td class="value">
					<input id="postalCode" name="postalCode" type="text" style="width: 150px" class="inputxt"
						datatype="p" ignore=ignore
						   value='${tScSupplierPage.postalCode}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">邮政编码</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						电子邮件:
					</label>
				</td>
				<td class="value">
					<input id="email" name="email" type="text" style="width: 150px" class="inputxt"
						datatype="e" ignore=ignore
						   value='${tScSupplierPage.email}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">电子邮件</label>
				</td>

				<td align="right">
					<label class="Validform_label">
						公司主页:
					</label>
				</td>
				<td class="value">
					<input id="homePage" name="homePage" type="text" style="width: 150px" class="inputxt"

						   value='${tScSupplierPage.homePage}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">公司主页</label>
				</td>
			</tr>
					<tr>

						<td align="right">
							<label class="Validform_label">
								开户银行:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="bank" type="list"
										  typeGroupCode="SC_BANK" defaultVal="${tScSupplierPage.bank}" hasLabel="false"  title="开户银行"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">开户银行</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								银行账号:
							</label>
						</td>
						<td class="value">
							<input id="bankNumber" name="bankNumber" type="text" style="width: 150px" class="inputxt"

								   value='${tScSupplierPage.bankNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">银行账号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								QQ号:
							</label>
						</td>
						<td class="value">
							<input id="cIQNumber" name="cIQNumber" type="text" style="width: 150px" class="inputxt"
								datatype="qq" ignore=ignore
								   value="${tScSupplierPage.cIQNumber}"/>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">QQ号</label>
						</td>
					</tr>
					<tr>

						<td align="right">
							<label class="Validform_label">
								行业:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="trade" type="list"
										typeGroupCode="SC_TRADE" defaultVal="${tScSupplierPage.trade}" hasLabel="false"  title="行业"></t:dictSelect>     
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">行业</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								结算单位:
							</label>
						</td>
						<td class="value">
							<input id="settleCompanyName" name="settleCompanyName" type="text" style="width: 150px" class="inputxt popup-select"
								   value="${tScSupplierPage.settleCompanyName}"
									>
                  <span>

                      </span>
							<input id="settleCompany" name="settleCompany" type="hidden" style="width: 150px" class="inputxt"

								   value="${tScSupplierPage.settleCompany}">
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">结算单位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								储备天数:
							</label>
						</td>
						<td class="value">
							<input id="stockDay" name="stockDay" type="text" style="width: 150px" class="inputxt"

								   value='${tScSupplierPage.stockDay}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">储备天数</label>
						</td>
					</tr>
					<tr>

						<td align="right">
							<label class="Validform_label">
								在途天数:
							</label>
						</td>
						<td class="value">
							<input id="byWayDay" name="byWayDay" type="text" style="width: 150px" class="inputxt"
								   datatype="n1-5" ignore="ignore"

								   value='${tScSupplierPage.byWayDay}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">在途天数</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value"colspan="3">
						  	 	<textarea id="note" style="width:80%;" class="inputxt" rows="2"
										  name="note"
									datatype="*1-80" ignore=ignore
										>${tScSupplierPage.note}</textarea>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>

			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/baseinfo/tScSupplier.js"></script>		