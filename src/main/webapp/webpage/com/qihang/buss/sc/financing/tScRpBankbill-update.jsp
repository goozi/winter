<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>银行存取款</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
<!--20160629页脚样式 -->
     <style >
         body{
             position: absolute;
             width: 100%;
             height: 100%;
         }
         .footlabel{
             float: left;
             margin-left: 15px;
         }

         .footspan{
             float: left;
             margin-left: 5px;
             margin-right: 5px;
             font-weight: bold;
             color: grey;
             margin-bottom: 5px;
         }
     </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" title="银行存取款" beforeSubmit="checkAmount();"
			   action="tScRpBankbillController.do?doUpdate" tiptype="1" tranType="${tScRpBankbillPage.tranType}"
			   tableName="t_sc_rp_bankbill"  isCancel="false" saveTemp="true">
	 				 <input id="load" value="${param.load}" type="hidden">
	 				 <input id="id" name="id" type="hidden" value="${tScRpBankbillPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScRpBankbillPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScRpBankbillPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScRpBankbillPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScRpBankbillPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScRpBankbillPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScRpBankbillPage.updateDate }">
					<input id="version" name="version" type="hidden" value="${tScRpBankbillPage.version }">
					<input id="tranType" name="tranType" type="hidden" value="${tScRpBankbillPage.tranType }">
	 				 <input id="billNo" name="billNo" type="hidden" value="${tScRpBankbillPage.billNo }">
	  				<input id="date" name="date" type="hidden" value="${tScRpBankbillPage.date}">
	 				 <input id="checkState" name="checkState" type="hidden" value="${tScRpBankbillPage.checkState}">
	  				<input id="checkDate" name="checkDate" type="hidden" value="<fmt:formatDate value='${tScRpBankbillPage.checkDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
		<table style="/* width: 600px; */" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								经办人:
							</label>
						</td>
						<td class="value">
							<input id="empId" name="empId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tScRpBankbillPage.tScEmpEntity.id}">
							<input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*" value="${tScRpBankbillPage.tScEmpEntity.name}">
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">经办人</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								部门:
							</label>
						</td>
						<td class="value">
							<input id="deptId" name="deptId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tScRpBankbillPage.tScDepartmentEntity.id}">
							<input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*" value="${tScRpBankbillPage.tScDepartmentEntity.name}">
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">部门</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								转出机构:
							</label>
						</td>
						<td class="value">
							<input id="scsonId" value="${tScRpBankbillPage.dcsoncompanyEntity.id}" class="easyui-combotree" data-options="url:'departController.do?loadDepartTree'" onkeypress="EnterPress(event)" onkeydown="EnterPress()"  type="text" name="scsonId"   style="width: 196px;height: 20px;" />

							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">转出机构</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								转出账户:
							</label>
						</td>
						<td class="value">
							<select name="paccountId">
								<c:forEach items="${listSet}" var="set">
									<option value="${set.id}" <c:if test="${tScRpBankbillPage.pasettleacctEntity.id == set.id}">selected="selected" </c:if> >${set.name}</option>
								</c:forEach>
							</select>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">转出账户</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								转入机构:
							</label>
						</td>
						<td class="value">
							<input id="dcsonId" value="${tScRpBankbillPage.scsoncompanyEntity.id}" class="easyui-combotree" data-options="url:'departController.do?loadDepartTree'" onkeypress="EnterPress(event)" onkeydown="EnterPress()"  type="text" name="dcsonId"   style="width: 196px;height: 20px;" />
							</select>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">转入机构</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								转入账户:
							</label>
						</td>
						<td class="value">
							<select name="raccountId">
								<c:forEach items="${listSet}" var="set">
									<option value="${set.id}" <c:if test="${tScRpBankbillPage.rasettleacctEntity.id == set.id}">selected="selected" </c:if> >${set.name}</option>
								</c:forEach>
							</select>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">转入账户</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								金额:
							</label>
						</td>
						<td class="value">
							<input id="allAmount" name="allAmount" type="text" datatype="d"  style="width: 150px" class="inputxt" errormsg="请填写正确的金额"

								value="${tScRpBankbillPage.allAmount}" >
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								分支机构:
							</label>
						</td>
						<td class="value" colspan="3">
							<input id="sonName" name="sonName" value="${tScRpBankbillPage.tsDepart.departname}" type="text" style="width: 150px" class="inputxt"
								   readonly="readonly">
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">分支机构</label>
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						摘要:
					</label>
				</td>
				<td class="value" colspan="3">
					<input name="explanation" name="name" style="width: 600px;"  datatype="*0-255" value="${tScRpBankbillPage.explanation }"/>

							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">摘要</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
  <!-- 页脚显示 -->
  <div style="width:100%;position: absolute;bottom: 10px;" id="footdiv">
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label class="Validform_label footlabel">制单人:${tScRpBankbillPage.billerUser.realName} </label>
		  <span class="inputxt footspan"></span>
	  </div>
	  <div style="width: 33%;display: inline;float: left" align="left">
		  <label class="Validform_label footlabel">审核人:${auditor} </label>
		  <span class="inputxt footspan"> </span>
	  </div>
	  <div style="width: 33%;display: inline;float: right" align="left">
		  <label class="Validform_label footlabel">审核日期:${auditDate} </label>
		  <span class="inputxt footspan"> </span>
	  </div>
  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/financing/tScRpBankbill.js"></script>
 <style type="text/css">
	 .spanBtn {
		 background-color: #CCE1F3;
		 display: -moz-inline-box;
		 display: inline-block;
		 width: 20px;
		 height: 20px;
		 text-align: center;
	 }
 </style>