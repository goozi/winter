<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>物流信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $.fn.serializeObject = function(){
	  var obj = {};
	  var count = 0;
	  $.each(this.serializeArray(),function(i,o){
		  var n = o.name,v= o.value;
		  count++;
		  obj[n] = obj[n] ===undefined ? v
		  : $.isArray(obj[n]) ? obj[n].concat(v)
		  : [obj[n],v];
	  });
	  obj.nameCounts = count + "";//表单name个数
	  return JSON.stringify(obj);
  }
	  function getJsonInfo(){
		  var formStr = $("#formobj").serializeObject();
		  return formStr;
	  }
  </script>
<!--20160629页脚样式 -->
     <style >
         body{
             position: absolute;
             width: 99%;
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
 <input type="hidden" id="load" value="${load}">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" beforeSubmit="checkSaveValue" windowType="dialog" action="tScSlStockbillController.do?updateLogistical&tableName=${tableName}" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScSlLogisticalPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScSlLogisticalPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScSlLogisticalPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScSlLogisticalPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScSlLogisticalPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScSlLogisticalPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScSlLogisticalPage.updateDate }">
					<input id="fid" name="fid" type="hidden" value="${tScSlLogisticalPage.fid }">
					<input id="tranType" name="tranType" type="hidden" value="${tScSlLogisticalPage.tranType }">
	  				<input id="logisticalId" name="logisticalId" type="hidden" value="${tScSlLogisticalPage.logisticalId }">
	  				<input id="accountId" name="accountId" type="hidden" value="${tScSlLogisticalPage.accountId }">
	  				<input id="checkAmount" name="checkAmount" type="hidden" value="${tScSlLogisticalPage.checkAmount }">
	  				<input id="version" name="version" type="hidden" value="${tScSlLogisticalPage.version }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								物流公司:
							</label>
						</td>
						<td class="value">
						     	 <input id="logisticalName" name="logisticalName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*"
									               
										       value='${tScSlLogisticalPage.logisticalName}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">物流公司</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								物流单号:
							</label>
						</td>
						<td class="value">
						     	 <input id="logisticalNo" name="logisticalNo" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScSlLogisticalPage.logisticalNo}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">物流单号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								承担方:
							</label>
						</td>
						<td class="value">
						     	 <%--<input id="bears" name="bears" type="text" style="width: 150px" class="inputxt" datatype="*"--%>
									               <%----%>
										       <%--value='${tScSlLogisticalPage.bears}'>--%>
									 <t:dictSelect field="bears" width="150" type="list"
												   showDefaultOption="true" extendJson="{datatype:'select1',onChange:'openBuyType()'}"
												   typeGroupCode="SC_BEAR" defaultVal="${tScSlLogisticalPage.bears}" hasLabel="false" title="承担方"></t:dictSelect>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">承担方</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								买家承担方式:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="buyType" width="150" type="list"
										  showDefaultOption="true" extendJson="{disabled:'disabled',onChange:'showInfo()'}"
										  typeGroupCode="SC_BUYER_TYPE" defaultVal="${tScSlLogisticalPage.buyType}" hasLabel="false" title="买家承担方式"></t:dictSelect>
						     	 <%--<input id="buyType" name="buyType" readonly="readonly" type="text" style="width: 150px" class="inputxt"--%>
									               <%----%>
										       <%--value='${tScSlLogisticalPage.buyType}'>--%>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">买家承担方式</label>
						</td>
					</tr>
					<tr class="hideField" style="display: none">
						<td align="right">
							<label class="Validform_label">
								物流费用:
							</label>
						</td>
						<td class="value">
						     	 <input id="freight" name="freight" type="text" style="width: 150px" class="inputxt"
									               
										       value='${tScSlLogisticalPage.freight}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">物流费用</label>
						</td>
					<tr class="hideField" style="display: none;">
						<td align="right">
							<label class="Validform_label">
								结算账户:
							</label>
						</td>
						<td class="value">
						     	 <input id="accountName" name="accountName" type="text" style="width: 150px" class="inputxt" onblur="checkcurPayAmount()"
									               
										       value='${tScSlLogisticalPage.accountName}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">结算账户</label>
						</td>
					</tr>
					<tr class="hideField" style="display: none;">
						<td align="right">
							<label class="Validform_label">
								本次付款:
							</label>
						</td>
						<td class="value">
						     	 <input id="curPayAmount" name="curPayAmount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScSlLogisticalPage.curPayAmount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本次付款</label>
						</td>
					<%--<tr>--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--执行金额:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
						     	 <%--<input id="checkAmount" name="checkAmount" type="text" style="width: 150px" class="inputxt"  --%>
									               <%----%>
										       <%--value='${tScSlLogisticalPage.checkAmount}'>--%>
							<%--<span class="Validform_checktip">--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">执行金额</label>--%>
						<%--</td>--%>
					<%--</tr>--%>
			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">

  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/sales/tScSlLogistical.js"></script>		