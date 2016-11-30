<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>基础资料分类表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScItemTypeController.do?doUpdate"
			   tiptype="1" windowType="dialog" callback="@Override noteSubmitCallback">
					<input id="id" name="id" type="hidden" value="${tScItemTypePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScItemTypePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScItemTypePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScItemTypePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScItemTypePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScItemTypePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScItemTypePage.updateDate }">
					<input id="level" name="level" type="hidden" value="${tScItemTypePage.level }">
					<input id="count   " name="count   " type="hidden" value="${tScItemTypePage.count    }">
					<input id="deleted" name="deleted" type="hidden" value="${tScItemTypePage.deleted }">
					<input id="version" name="version" type="hidden" value="${tScItemTypePage.version }">
	  <input id="itemClassId" name="itemClassId" type="hidden" value="${tScItemTypePage.itemClassId}"
			  >
	  <input id="parentId" name="parentId" type="hidden" value="${tScItemTypePage.parentId}"
			  >


	  <table style="width: 400px;" cellpadding="0" cellspacing="1" class="formtable">

					<tr>
						<td align="right">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="number" name="number" type="text" style="width: 150px" class="inputxt"  
									               datatype="*"
										       value='${tScItemTypePage.number}' >
							<span class="Validform_checktip">*
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
									               datatype="*1-60"
										       value='${tScItemTypePage.name}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/baseinfo/tScItemType.js"></script>		