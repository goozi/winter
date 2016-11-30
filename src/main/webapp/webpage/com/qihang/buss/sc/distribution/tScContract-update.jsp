<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>合同管理</title>
  <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScContractController.do?doUpdate" tiptype="1" isCancel="false">
					<input id="id" name="id" type="hidden" value="${tScContractPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScContractPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScContractPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScContractPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScContractPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScContractPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScContractPage.updateDate }">
					<input id="itemID" name="itemID" type="hidden" value="${tScContractPage.tScOrganizationEntity.id }">
	  				<input id="version" name="version" type="hidden" value="${tScContractPage.version }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<%--<td align="right" style="width: 6%">
							<label class="Validform_label">
								单据类型:
							</label>
						</td>
						<td class="value" style="width: 24%">
									<t:dictSelect field="trantype" type="list" extendJson="{'datatype':'*'}"
										typeGroupCode="SC_TRAN_TYPE" defaultVal="${tScContractPage.trantype}" hasLabel="false"  title="单据类型"></t:dictSelect>     
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">单据类型</label>
						</td>--%>

						<td align="right">
							<label class="Validform_label">
								合同日期:
							</label>
						</td>
						<td class="value">
									  <input id="date" name="date" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
											 datatype="*"
						      						 value='<fmt:formatDate value='${tScContractPage.date}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">合同日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								合同号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billNo" name="billNo" type="text" style="width: 150px" class="inputxt"
										datatype="*"
										       value='${tScContractPage.billNo}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">合同号</label>
						</td>
						<td align="right"></td>
						<td class="value"></td>
			</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经销商:
							</label>
						</td>
						<td class="value">
						     	 <input id="itemIDName" name="itemIDName" type="text" style="width: 150px" class="inputxt popup-select"
										datatype="*"
										       value='${itemIDName}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">经销商</label>
						</td>

						<td align="right">
							<label class="Validform_label">
								合同名:
							</label>
						</td>
						<td class="value">
						     	 <input id="contractName" name="contractName" type="text" style="width: 150px" class="inputxt"
										datatype="*"
										       value='${tScContractPage.contractName}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">合同名</label>
						</td>

				<td align="right">
					<label class="Validform_label">
						分支机构:
					</label>
				</td>
				<td class="value">
					<input  type="text" style="width: 150px" class="inputxt" readonly="readonly" value='${sonName}'>
					<input  type="hidden" style="width: 150px" id="sonID" name="sonID"  value='${tScContractPage.sonID}'>
							<span class="Validform_checktip">
							</span>
					<label class="Validform_label" style="display: none;">分支机构</label>
				</td>
			</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								合同内容:
							</label>
						</td>
						<td class="value" colspan="5">
									<t:ckeditor  name="content" isfinder="false" type="width:document.body.clientWidth-70,toolbar:[['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
        ['Link','Unlink','Anchor'],
        ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
        ['Styles','Format','Font','FontSize'],
        ['TextColor','BGColor'],
        ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value="${tScContractPage.content}"></t:ckeditor>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">合同内容</label>
						</td>
					</tr>

			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">
	  <div style="width: 33%;display:inline;">
		  <label class="Validform_label footlabel">制单人: </label>
		  <span class="inputxt footspan"> ${sessionScope.user.realName}</span>
	  </div>
  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/distribution/tScContract.js"></script>		