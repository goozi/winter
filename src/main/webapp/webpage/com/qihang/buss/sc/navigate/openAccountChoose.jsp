<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>系统开账</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
	  function checkSaveValue(){
		  var openType = $("input[name='openType']:checked").val();
		  if(!openType) {
			  return false;
		  } else {
			  return true;
		  }
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" refresh="false" beforeSubmit="checkSaveValue" windowType="dialog"
			   action="tScAccountConfigController.do?openAccount&id=${accountId}&type=${hasOpen}"
			   tiptype="1" callback="afterOpenAccount">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td colspan="2">
							<div style="text-align: left">
							<span>
								<span style="color:red">*</span>系统开账，就是结束初始化的意思。是日常业务操作的前提条件。<br/><br/>
								    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开账前必须对初始化数据进行一系列的检查：<br/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、必须检查系统设置的设置是否符合本公司的需求，系统设置的某些信息在系统开账后将不能进行修改，如：是 否允许负库存出库等在开账后将不能修改。<br/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、是否有未审核的初始化单据，必须将全部的初始化单据审核成功后才能进行系统开账。<br/><br/><br/>
							</span>
							<span>
								<span style="color:red">*</span>系统开账后，才能对业务单据进行操作。<br/><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开账后初始化单据将不能被编辑，系统只提供浏览，如果想编辑初始化单据则必须对系统进行反开账。反开账时如果系统已经产生了业务单据，则系统控制不允许反开账。<br/><br/>
							</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="value" style="text-align:left;">
							开账:
							<input name="openType" type="radio" <c:if test="${hasOpen eq 1}"> checked="checked"</c:if> <c:if test="${hasOpen ne 1}"> disabled="disabled"</c:if>  value="0">
							<span class="Validform_checktip">
							</span>
						</td>
						<td class="value" style="text-align:left;">
							反开账:<input name="openType" type="radio" <c:if test="${hasOpen eq 2}"> checked="checked"</c:if> <c:if test="${hasOpen ne 2}"> disabled="disabled"</c:if> value='1'>
							<span class="Validform_checktip">
							</span>
						</td>
					</tr>
					<%--<tr>--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--承担方:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
						     	 <%--&lt;%&ndash;<input id="bears" name="bears" type="text" style="width: 150px" class="inputxt" datatype="*"&ndash;%&gt;--%>
									               <%--&lt;%&ndash;&ndash;%&gt;--%>
										       <%--&lt;%&ndash;value='${tScSlLogisticalPage.bears}'>&ndash;%&gt;--%>
									 <%--<t:dictSelect field="bears" width="150" type="list"--%>
												   <%--showDefaultOption="true" extendJson="{datatype:'select1',onChange:'openBuyType()'}"--%>
												   <%--typeGroupCode="SC_BEAR" defaultVal="${tScSlLogisticalPage.bears}" hasLabel="false" title="承担方"></t:dictSelect>--%>
							<%--<span class="Validform_checktip">*--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">承担方</label>--%>
						<%--</td>--%>
					<%--<tr>--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--买家承担方式:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
							<%--<t:dictSelect field="buyType" width="150" type="list"--%>
										  <%--showDefaultOption="true" extendJson="{disabled:'disabled',onChange:'showInfo()'}"--%>
										  <%--typeGroupCode="SC_BUYER_TYPE" defaultVal="${tScSlLogisticalPage.buyType}" hasLabel="false" title="买家承担方式"></t:dictSelect>--%>
						     	 <%--&lt;%&ndash;<input id="buyType" name="buyType" readonly="readonly" type="text" style="width: 150px" class="inputxt"&ndash;%&gt;--%>
									               <%--&lt;%&ndash;&ndash;%&gt;--%>
										       <%--&lt;%&ndash;value='${tScSlLogisticalPage.buyType}'>&ndash;%&gt;--%>
							<%--<span class="Validform_checktip">--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">买家承担方式</label>--%>
						<%--</td>--%>
					<%--</tr>--%>
					<%--<tr class="hideField" style="display: none">--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--物流费用:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
						     	 <%--<input id="freight" name="freight" type="text" style="width: 150px" class="inputxt"--%>
									               <%----%>
										       <%--value='${tScSlLogisticalPage.freight}'>--%>
							<%--<span class="Validform_checktip">*--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">物流费用</label>--%>
						<%--</td>--%>
					<%--<tr class="hideField" style="display: none;">--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--结算账户:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
						     	 <%--<input id="accountName" name="accountName" type="text" style="width: 150px" class="inputxt popup-select" onblur="checkcurPayAmount()"--%>
									               <%----%>
										       <%--value='${tScSlLogisticalPage.accountName}'>--%>
							<%--<span class="Validform_checktip">--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">结算账户</label>--%>
						<%--</td>--%>
					<%--</tr>--%>
					<%--<tr class="hideField" style="display: none;">--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--本次付款:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
						     	 <%--<input id="curPayAmount" name="curPayAmount" type="text" style="width: 150px" class="inputxt"  --%>
									               <%----%>
										       <%--value='${tScSlLogisticalPage.curPayAmount}'>--%>
							<%--<span class="Validform_checktip">--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">本次付款</label>--%>
						<%--</td>--%>
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
  <%--<script src = "webpage/com/qihang/buss/sc/sales/tScSlLogistical.js"></script>--%>