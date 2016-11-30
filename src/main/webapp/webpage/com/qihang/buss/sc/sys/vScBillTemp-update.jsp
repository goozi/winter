<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>单据草稿</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <%--<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>--%>
	 <!--jsoneditor插件只给开发人员自己使用，所以这里注释掉-->
  <link rel="stylesheet" type="text/css" href="plug-in/jquery-plugs/jsoneditor/jsoneditor.css"/>
  <script type="text/javascript" src="plug-in/jquery-plugs/jsoneditor/jquery.jsoneditor.js"></script>
  <script type="text/javascript">
	  $(document).ready(function(){
		  var content = $("#content").val();
		  var json = eval("(" + content + ")");
		  $('#editor').jsonEditor(json, { change: function() {
			  $('#json').html(JSON.stringify(json));
			  //$('#json').html(json);
		  } })
		  //隐藏新增、编辑、打印按钮
		  $("#add").css("display","none");
		  $("#edit").css("display","none");
	  });
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="vScBillTempController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${vScBillTempPage.id }">
					<input id="createName" name="createName" type="hidden" value="${vScBillTempPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${vScBillTempPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${vScBillTempPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${vScBillTempPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${vScBillTempPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${vScBillTempPage.updateDate }">
					<input id="trantype" name="trantype" type="hidden" value="${vScBillTempPage.tranType }">
					<input id="stockid" name="stockid" type="hidden" value="${vScBillTempPage.stockid }">
					<input id="deptid" name="deptid" type="hidden" value="${vScBillTempPage.deptid }">
					<input id="itemid" name="itemid" type="hidden" value="${vScBillTempPage.itemid }">
					<input id="empid" name="empid" type="hidden" value="${vScBillTempPage.empid }">
		<table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								单据编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billno" name="billno" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${vScBillTempPage.billno}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								单据日期:
							</label>
						</td>
						<td class="value">
									  <input id="date" name="date" type="text" style="width: 150px"
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${vScBillTempPage.date}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								仓库:
							</label>
						</td>
						<td class="value">
						     	 <input id="stockname" name="stockname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${vScBillTempPage.stockname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">仓库</label>
						</td>
			        </tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								部门:
							</label>
						</td>
						<td class="value">
						     	 <input id="departname" name="departname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${vScBillTempPage.departname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">部门</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								供货商/客户:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${vScBillTempPage.name}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">供货商/客户</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								经办人:
							</label>
						</td>
						<td class="value">
						     	 <input id="empname" name="empname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${vScBillTempPage.empname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">经办人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								数据JSON串:
							</label>
						</td>
						<td class="value" colspan="5">
							<div id="editor" class="json-editor"></div>
							<pre id="json"></pre>
						  	<div style="display: none"><textarea id="content" style="width:600px;" rows="30" class="inputxt" rows="6" name="content">${vScBillTempPage.content}</textarea></div>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">数据JSON串</label>
						</td>
				<td align="right">
					<label class="Validform_label">
					</label>
				</td>
				<td class="value">
				</td>
					</tr>
			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">

  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/sys/vScBillTemp.js"></script>		