<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职员</title>
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
		 @media (min-width: 768px) and (max-width: 1366px){
			 #address{
				 width:81%;
			 }
			 #idAddress{
				 width: 81%;
			 }
			 #note{
				 width: 88%;
			 }
		 }

		 @media (min-width: 600px) and (max-width: 1280px) {
			 #address{
				 width:82.5%;
			 }
			 #idAddress{
				 width: 82.5%;
			 }
			 #note{
				 width: 89%;
			 }

		 }
		 @media (min-width: 768px) and(max-width: 1024px) {
			 #address{
				 width:89%;
			 }
			 #idAddress{
				 width: 89%;
			 }
			 #note{
				 width: 93%;
			 }
		 }
		 @media (min-width: 600px) and(max-width: 800px) {
			 #address{
				 width:94%;
			 }
			 #idAddress{
				 width: 94%;
			 }
			 #note{
				 width: 96%;
			 }
		 }
     </style>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScEmpController.do?doUpdate" tiptype="1" beforeSubmit="checkName()">
					<input id="id" name="id" type="hidden" value="${tScEmpPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScEmpPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScEmpPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScEmpPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScEmpPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScEmpPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScEmpPage.updateDate }">
					<input id="parentID" name="parentID" type="hidden" value="${tScEmpPage.parentID }">
					<input id="disabled" name="disabled" type="hidden" value="${tScEmpPage.disabled }">
					<input id="deleted" name="deleted" type="hidden" value="${tScEmpPage.deleted }">
					<input id="version" name="version" type="hidden" value="${tScEmpPage.version }">
					<input id="level" name="level" type="hidden" value="${tScEmpPage.level }">
					<input id="count" name="count" type="hidden" value="${tScEmpPage.count }">
					<input id="userId" name="userId" type="hidden" value="${tScEmpPage.userId }">
		      <input id="tranType" name="tranType" type="hidden" value="${tranType}">
		      <input id="old_name" name="old_name" type="hidden" value='${tScEmpPage.name}'>
		      <input id="load" value="${load}" type="hidden">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">创建用户:</label>
				</td>
				<td class="value" colspan="5">
					<input   type="checkbox"  name="userId"  checked="checked" disabled="disabled"/>
				</td>
			</tr>
					<tr>

						<td align="right" style="width: 9%">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value" style="width: 24%">
						     	 <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
													datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点"
										       value='${tScEmpPage.number}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">编号</label>
						</td>
						<td align="right" style="width:9%;">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value" style="width: 24%">
							<input id="name" name="name" type="text" style="width: 150px" class="inputxt"
								   datatype="*" onchange="doName()"
								   value='${tScEmpPage.name}'>
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
						<td class="value" style="width: 24%">
							<input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"
								   datatype="*" readonly="readonly"
								   value='${tScEmpPage.shortName}'>
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
								   validType="t_sc_emp,shortNumber,id"
								   value='${tScEmpPage.shortNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">助记码</label>
						</td>

						<td align="right">
							<label class="Validform_label">
								性别:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="gender" type="list" showDefaultOption="false"
										  typeGroupCode="SC_GENDER" defaultVal="${tScEmpPage.gender}" hasLabel="false"  title="性别"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								部门:
							</label>
						</td>
						<td class="value">
							<input id="deptIdName" name="deptIdName" type="text" style="width: 150px" class="inputxt popup-select"
								   value="${deptIdName}"
									>
								<%--<t:dictSelect field="deptIdName" type="text" defaultVal="${tScEmpPage.deptID}" hasLabel="false" dictTable="t_sc_department" dictField="id" dictText="name"/>--%>
							<input id="deptID" name="deptID" type="hidden" style="width: 150px" class="inputxt"

								   value='${tScEmpPage.deptID}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">部门</label>
						</td>
					</tr>

					<tr>
						<td align="right">
							<label class="Validform_label">
								职务:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="duty" type="list"
										  typeGroupCode="SC_DUTY_TYPE" defaultVal="${tScEmpPage.duty}" hasLabel="false"  title="职务"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">职务</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								文化程度:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="degree" type="list"
										  typeGroupCode="SC_CULTURE_TYPE" defaultVal="${tScEmpPage.degree}" hasLabel="false"  title="文化程度"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">文化程度</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								手机号码:
							</label>
						</td>
						<td class="value">
							<input id="mobilePhone" name="mobilePhone" type="text" style="width: 150px" class="inputxt"
								datatype="m" ignore=ignore
								   value='${tScEmpPage.mobilePhone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
					</tr>
					<tr>

						<td align="right">
							<label class="Validform_label">
								联系电话:
							</label>
						</td>
						<td class="value">
							<input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"

								   value='${tScEmpPage.phone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">联系电话</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								联系地址:
							</label>
						</td>
						<td class="value" colspan="3">
							<input id="address" name="address" type="text" style="width: 79.5%" class="inputxt"
								datatype="*1-50" ignore=ignore
								   value='${tScEmpPage.address}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">联系地址</label>
						</td>


					</tr>

			<tr>

				<td align="right">
					<label class="Validform_label">
						身份证号码:
					</label>
				</td>
				<td class="value">
					<input id="identify" name="identify" type="text" style="width: 150px" class="inputxt"
						datatype="idcard"ignore=ignore
						   value='${tScEmpPage.identify}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">身份证号码</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						身份证地址:
					</label>
				</td>
				<td class="value" colspan="3">
					<input id="idAddress" name="idAddress" type="text" style="width: 79.5%" class="inputxt"
						datatype="*1-50" ignore=ignore
						   value='${tScEmpPage.idAddress}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">身份证地址</label>
				</td>
					</tr>
<tr>
	<td align="right">
		<label class="Validform_label">
			QQ号:
		</label>
	</td>
	<td class="value">
		<input id="ciqNumber" name="ciqNumber" type="text" style="width: 150px" class="inputxt"
			datatype="qq" ignore=ignore
			   value='${tScEmpPage.ciqNumber}'>
							<span class="Validform_checktip">
							</span>
		<label class="Validform_label" style="display: none;">QQ号</label>
	</td>
	<td align="right">
		<label class="Validform_label">
			电子邮件:
		</label>
	</td>
	<td class="value">
		<input id="email" name="email" type="text" style="width: 150px" class="inputxt"
			datatype="e" ignore=ignore
			   value='${tScEmpPage.email}'>
							<span class="Validform_checktip">
							</span>
		<label class="Validform_label" style="display: none;">电子邮件</label>
	</td>
	<td align="right">
		<label class="Validform_label">
			生日:
		</label>
	</td>
	<td class="value">
		<input id="birthday" name="birthday" type="text" style="width: 150px"
			   class="Wdate" onClick="WdatePicker()"
			datatype="date" ignore=ignore
			   value='<fmt:formatDate value='${tScEmpPage.birthday}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
							</span>
		<label class="Validform_label" style="display: none;">生日</label>
	</td>
</tr>

					<tr>

						<td align="right">
							<label class="Validform_label">
								职员类别:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="empGroup" type="list"
										  typeGroupCode="SC_EMPLOYEE_TYPE" defaultVal="${tScEmpPage.empGroup}" hasLabel="false"  title="职员类别"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">职员类别</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								毕业院校:
							</label>
						</td>
						<td class="value">
							<input id="school" name="school" type="text" style="width: 150px" class="inputxt"

								   value='${tScEmpPage.school}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">毕业院校</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								学习专业:
							</label>
						</td>
						<td class="value">
							<input id="expertise" name="expertise" type="text" style="width: 150px" class="inputxt"

								   value='${tScEmpPage.expertise}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">学习专业</label>
						</td>
					</tr>
					<tr>


						<td align="right">
							<label class="Validform_label">
								入职日期:
							</label>
						</td>
						<td class="value">
							<input id="hireDate" name="hireDate" type="text" style="width: 150px"
								   class="Wdate" onClick="WdatePicker()"
								datatype="date" ignore=ignore
								   value='<fmt:formatDate value='${tScEmpPage.hireDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">入职日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								离职日期:
							</label>
						</td>
						<td class="value">
							<input id="leaveDate" name="leaveDate" type="text" style="width: 150px"
								   class="Wdate" onClick="WdatePicker()"
								datatype="date" ignore=ignore
								   value='<fmt:formatDate value='${tScEmpPage.leaveDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">离职日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								开户银行:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="bank" type="list"
										  typeGroupCode="SC_BANK" defaultVal="${tScEmpPage.bank}" hasLabel="false"  title="开户银行"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">开户银行</label>
						</td>
					</tr>

					<tr>

						<td align="right">
							<label class="Validform_label">
								银行账号:
							</label>
						</td>
						<td class="value">
						     	 <input id="bankNumber" name="bankNumber" type="text" style="width: 150px" class="inputxt"
													datatype="*1-40" ignore="ignore"
										       value='${tScEmpPage.bankNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">银行账号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								启用信控:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="isCreditMgr" type="list" extendJson="{'datatype':'*','onChange':'iscreditmgrf(value)'}"
										  showDefaultOption="false"
										  typeGroupCode="sf_01" defaultVal="${tScEmpPage.isCreditMgr}" hasLabel="false"  title="启用信控"></t:dictSelect>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">启用信控</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								信asd用额度:
							</label>
						</td>
						<td class="value">
							<input id="creditAmount" name="creditAmount" type="text" style="width: 150px" class="inputxt"
										 datatype="fInt" min="1"  onchange="checkcreditamount(this)"
							value='${tScEmpPage.dcreditAmount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">信用额度</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value" colspan="5">
						  	 	<textarea id="note" style="width:87.5%;"
									datatype="*1-80" ignore=ignore
										  class="inputxt" rows="2" name="note">${tScEmpPage.note}</textarea>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>

			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">

  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/baseinfo/tScEmp.js"></script>		