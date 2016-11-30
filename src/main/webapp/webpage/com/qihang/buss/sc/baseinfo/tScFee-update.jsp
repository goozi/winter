<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>收支项目</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScFeeController.do?doUpdate"
							 beforeSubmit="checkName" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScFeePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScFeePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScFeePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScFeePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScFeePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScFeePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScFeePage.updateDate }">
					<input id="parentID" name="parentID" type="hidden" value="${tScFeePage.parentID }">
					<input id="disabled" name="disabled" type="hidden" value="${tScFeePage.disabled }">
					<input id="deleted" name="deleted" type="hidden" value="${tScFeePage.deleted }">
					<input id="version" name="version" type="hidden" value="${tScFeePage.version }">
					<input id="level" name="level" type="hidden" value="${tScFeePage.level }">
					<input id="count" name="count" type="hidden" value="${tScFeePage.count }">
					<input id="tranType" name="tranType" type="hidden" value="${tranType}">
					<input id="old_name" name="old_name" type="hidden" value='${tScFeePage.name}'>
		      <input id="load" value="${load}" type="hidden">

	  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">

					<tr>
						<td align="right" style="width: 10%">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
													datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点"
										       value='${tScFeePage.number}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">编号</label>
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						名称:
					</label>
				</td>
				<td class="value">
					<input id="name" name="name" type="text" style="width: 150px" class="inputxt"
						   datatype="*1-80" onchange="doName()"
						   value='${tScFeePage.name}'>
							<span class="Validform_checktip">
                                  *
							</span>
					<label class="Validform_label" style="display: none;">名称</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						简称:
					</label>
				</td>
				<td class="value">
					<input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"
						   datatype="*" readonly="readonly"
						   value='${tScFeePage.shortName}'>
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
										validType="t_sc_fee,shortNumber,id"
										       value='${tScFeePage.shortNumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">助记码</label>
						</td>
			</tr>

					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="note" style="width:13%;" class="inputxt" rows="2" name="note">${tScFeePage.note}</textarea>
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
  <script src = "webpage/com/qihang/buss/sc/baseinfo/tScFee.js"></script>		