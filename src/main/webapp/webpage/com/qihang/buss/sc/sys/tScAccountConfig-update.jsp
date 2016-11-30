<%@ page language="java" import="com.qihang.buss.sc.util.AccountUtil" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
     <title>账套设置</title>
     <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
     <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
     <script type="text/javascript">
     //编写自定义JS代码
	 		$(document).ready(function () {
	 				 $(".Validform_checktip").each(function(){
	 					 $(this).hide();
	 				 });
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
		<table style="width: 41%;height:420px;" align="center" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
			<th colspan="4" height="25">企业信息 </th>
			</tr>
					<tr>
						<td align="right" >
							<label class="Validform_label">
								公司名称:
							</label>
						</td>
						<td class="value" >
						     	 <input id="companyName" name="companyName" type="text" style="width: 400px" class="inputxt"
									               
										       value='${tScAccountConfigPage.companyName}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">公司名称</label>
						</td>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								税号:
							</label>
						</td>
						<td class="value">
						     	 <input id="taxNumber" name="taxNumber" type="text" style="width: 400px" class="inputxt"
									               
										       value='${tScAccountConfigPage.taxNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">税号</label>
						</td>
					</tr>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								银行账号:
							</label>
						</td>
						<td class="value">
						     	 <input id="bankAccount" name="bankAccount" type="text" style="width: 400px" class="inputxt"
									               
										       value='${tScAccountConfigPage.bankAccount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">银行账号</label>
						</td>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								公司地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="companyAddress" name="companyAddress" type="text" style="width: 400px" class="inputxt"
									               
										       value='${tScAccountConfigPage.companyAddress}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">公司地址</label>
						</td>
					</tr>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="phone" name="phone" type="text" style="width: 400px" class="inputxt"
										datatype="*,po"
										       value='${tScAccountConfigPage.phone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								传真:
							</label>
						</td>
						<td class="value">
						     	 <input id="fax" name="fax" type="text" style="width: 400px" class="inputxt"
										datatype="*,f"
										       value='${tScAccountConfigPage.fax}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">传真</label>
						</td>
					</tr>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								E-Mail:
							</label>
						</td>
						<td class="value">
						     	 <input id="email" name="email" type="text" style="width: 400px" class="inputxt"
									             datatype="*,e"
										       value='${tScAccountConfigPage.email}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">E_Mail</label>
						</td>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								公司网址:
							</label>
						</td>
						<td class="value">
						     	 <input id="companyUrl" name="companyUrl" type="text" style="width: 400px" class="inputxt"
										datatype="*,url"
										       value='${tScAccountConfigPage.companyUrl}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">公司网址</label>
						</td>
					</tr>
					<tr>
						<td align="right"style="width: 100%">
							<label class="Validform_label">
								注册号:
							</label>
						</td>
						<td class="value">
						     	 <input id="registrationNumber" name="registrationNumber" type="text" style="width: 400px" class="inputxt"
									               
										       value='${tScAccountConfigPage.registrationNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">注册号</label>
						</td>

					</tr>
			<tr>
				<td align="right"style="width: 100%">
					<label class="Validform_label">
						启用期间:
					</label>
				</td>
				<td class="value">
					<fmt:formatDate value="${tScAccountConfigPage.accountStartDate}" pattern="yyyy年MM期"></fmt:formatDate>

				</td>

			</tr>
			<tr>
				<td align="right"style="width: 100%">
					<label class="Validform_label">
						当前期:
					</label>
				</td>
				<td class="value">
					<%--是否开账开账就显示时期 没开账，显示未开账--%>
					<c:choose>
						<c:when test="${isAccountOpen}">
							<fmt:formatDate value="${tScAccountConfigPage.stageStartDate}" pattern="yyyy年MM期"></fmt:formatDate>
						</c:when>
						<c:otherwise>
							未开账
						</c:otherwise>

					</c:choose>



				</td>

			</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/sys/tScAccountConfig.js"></script>		