<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>销售报价单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
   <script type="text/javascript">
		 var centerBodyHigh;
		 var southBodyHight;
		 $(document).ready(function () {
			 $('#tt').tabs({
				 onSelect: function (title) {
					 $('#tt .panel-body').css('width', 'auto');
				 }
			 });
			 $(".tabs-wrap").css('width', '100%');

			 // 客户信息获取
			 $.ajax({
				 type: "get",
				 url: "tScQuoteController.do?tScQuotecustomerList&id=${tScQuotePage.id}&load=${load}",
				 success:function(data){
					 $("#centerBody").html(data);
					 centerBodyHigh = $("#centerBody").height();
				 }
			 });

			 // 商品信息获取
			 $.ajax({
				 type: "get",
				 url: "tScQuoteController.do?tScQuoteitemsList&id=${tScQuotePage.id}&load=${load}",
				 success:function(data){
					 $("#southBody").html(data);
					 southBodyHigh= $("#southBody").height();
				 }
			 });

			 $(".layout-button-up").click(toggleCenter);
			 $(".layout-button-down").click(toggleSouth);
			 $("#custHeader").dblclick(toggleCenter);
			 $("#goodsHeader").dblclick(toggleSouth);
			 setValOldIdAnOldName($('#empName'), $('#empID').val(), $('#empName').val());
			 setValOldIdAnOldName($('#deptName'), $('#deptID').val(), $('#deptName').val());

		 });

		 function toggleCenter(){
			 if($("#centerBody").is(":hidden")){
				 $("#centerBody").slideDown();
				 $("#southBody").height(southBodyHigh );
			 } else {
				 $("#centerBody").slideUp();
				 $("#southBody").height(centerBodyHigh +southBodyHigh );
			 }
		 }

		 function toggleSouth(){
			 if($("#southBody").is(":hidden")){
				 $("#southBody").slideDown();
				 $("#centerBody").height(southBodyHigh );
			 } else {
				 $("#southBody").slideUp();
				 $("#centerBody").height(centerBodyHigh +southBodyHigh  );
			 }
		 }
	 </script>

	 <style>
		 html,body,form{height: 100%}
		 .layout-button-up {
			 width: 16px !important;
			 height: 16px !important;
		 }

		 .layout-button-down {
			 width: 16px !important;
			 height: 16px !important;
		 }

		 #tScQuoteitems_tablescrolldiv{
			 overflow: hidden !important;
		 }
		 .footlabel{
			 float: left;
			 margin-left: 35px;
		 }
		 .footspan{
			 float: left;
			 margin-left: 5px;
			 margin-right: 5px;
			 font-weight: bold;
			 color: grey;
			 margin-bottom: 5px;
			 text-decoration: underline;
		 }
	 </style>
 </head>
 <body style="overflow-x: hidden;">
 <body style="overflow: hidden;">
 <input type="hidden" id="load" value="${load}">
 <input id="clearExt" type="hidden" value="doTempRecoveryExt">
 <t:formvalid formid="formobj" dialog="true" title="销售报价单" usePlugin="password" layout="table" tiptype="1" tranType="101" tableName="t_sc_quote" saveTemp="true"
			  action="tScQuoteController.do?doUpdate" isCancel="false">
	 <div  class="panel-body layout-body" style="height: 80px">
		 <input id="id" name="id" type="hidden"
				value="${tScQuotePage.id }">
		 <input id="createName" name="createName" type="hidden"
				value="${tScQuotePage.createName }">
		 <input id="createBy" name="createBy" type="hidden"
				value="${tScQuotePage.createBy }">
		 <input id="createDate" name="createDate" type="hidden"
				value="${tScQuotePage.createDate }">
		 <input id="updateName" name="updateName" type="hidden"
				value="${tScQuotePage.updateName }">
		 <input id="updateBy" name="updateBy" type="hidden"
				value="${tScQuotePage.updateBy }">
		 <input id="updateDate" name="updateDate" type="hidden"
				value="${tScQuotePage.updateDate }">
		 <input id="tranType" name="tranType" type="hidden"
				value="${tScQuotePage.tranType }">
		 <input id="checkDate" name="checkDate" type="hidden"
				value="${tScQuotePage.checkDate }">
		 <input id="checkState" name="checkState" type="hidden"
				value="${tScQuotePage.checkState }">
		 <input id="cancellation" name="cancellation" type="hidden"
				value="${tScQuotePage.cancellation }">
		 <input id="deleted" name="deleted" type="hidden"
				value="${tScQuotePage.deleted }">
		 <input id="version" name="version" type="hidden"
				value="${tScQuotePage.version }">
		 <input id="billerID" name="billerID" type="hidden" value="${tScQuotePage.billerID}"/>
		 <input id="sonID" name="sonID" type="hidden" value="${tScQuotePage.sonID}"/>
		 <input id="checkerID" name="checkerID" type="hidden" value="${tScQuotePage.checkerID}"/>
		 <input id="empID" name="empID" type="hidden" value="${tScQuotePage.empID}"/>
		 <input id="deptID" name="deptID"  type="hidden" value="${tScQuotePage.deptID}"/>
		 <input name="billNo"  type="hidden" value="${tScQuotePage.billNo}"/>
		 <input name="date"  type="hidden" value="${tScQuotePage.date}"/>
		 <table cellpadding="0" cellspacing="1" class="formtable" style="width: 100%">
			 <tr>
				 <%--<td align="right" width="10%">--%>
					 <%--<label class="Validform_label">单据编号:</label>--%>
				 <%--</td>--%>
				 <%--<td class="value"  width="23%">--%>
					 <%--<input id="billNo" name="billNo" type="text" style="width: 150px" class="inputxt" datatype="*" value="${tScQuotePage.billNo}" readonly="true">--%>
					 <%--<span class="Validform_checktip">*</span>--%>
					 <%--<label class="Validform_label" style="display: none;">单据编号</label>--%>
				 <%--</td>--%>
				 <%--<td align="right" width="10%">--%>
					 <%--<label class="Validform_label">单据日期:</label>--%>
				 <%--</td>--%>
				 <%--<td class="value" width="23%">--%>
					 <%--<input id="date" name="date" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*"  value='<fmt:formatDate value='${tScQuotePage.date}' type="date" pattern="yyyy-MM-dd"/>'>--%>
					 <%--<span class="Validform_checktip">*</span>--%>
					 <%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
				 <%--</td>--%>
				 <td align="right" width="10%">
					 <label class="Validform_label">经办人:</label>
				 </td>
				 <td class="value" width="24%">
					 <input id="empName" name="empName" value="${tScQuotePage.empName}" type="text" style="width: 150px" class="inputxt popup-select" datatype="*" >
					 <span class="Validform_checktip">*</span>
					 <label class="Validform_label" style="display: none;">经办人</label>
				 </td>
				 <td align="right">
					 <label class="Validform_label">部门:</label>
				 </td>
				 <td class="value">
					 <input id="deptName" name="deptName"  value="${tScQuotePage.deptName}"  type="text" style="width: 150px" class="inputxt popup-select" datatype="*" >
					 <span class="Validform_checktip">*</span>
					 <label class="Validform_label" style="display: none;">部门</label>
				 </td>
				 <td align="right">
					 <label class="Validform_label">生效日期:</label>
				 </td>
				 <td class="value">
					 <input id="inureDate" name="inureDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*"  value='<fmt:formatDate value='${tScQuotePage.inureDate}' type="date" pattern="yyyy-MM-dd"/>'/>
					 <span class="Validform_checktip">*</span>
					 <label class="Validform_label" style="display: none;">生效日期</label>
				 </td>
			 </tr>
			 <tr>
				 <td align="right">
					 <label class="Validform_label">分支机构:</label>
				 </td>
				 <td class="value">
					 <input id="sonName" name="sonName" type="text" style="width: 150px" class="inputxt" readonly="readonly" value="${tScQuotePage.sonName}"/>
					 <span class="Validform_checktip"></span>
					 <label class="Validform_label" style="display: none;">分支机构</label>
				 </td>
				 <td align="right">
					 <label class="Validform_label">摘要:</label>
				 </td>
				 <td class="value" colspan="3">
						 <%--<input id="tScQuoteitemsList[0].unitID" name="tScQuoteitemsList[0].unitID" class="easyui-combobox"  data-options="valueField: 'id',textField: 'text',panelHeight: 'auto',editable: false" style="width:80px;"/>--%>
					 <input id="explanation" name="explanation"  value="${tScQuotePage.explanation}"   style="width: 72.5%" class="inputxt"  datatype="*1-126" ignore="ignore" />
					 <span class="Validform_checktip"></span>
					 <label class="Validform_label" style="display: none;">摘要</label>
				 </td>
			 </tr>
		 </table>
	 </div>
	 <div class="panel-header" id="custHeader" >
		 <div class="panel-title">客戶</div>
		 <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up" ></a></div>
	 </div>
	 <div  class="panel-body layout-body" id="centerBody" style="height: 31.5%">

	 </div>
	 <div class="panel-header" id="goodsHeader" >
		 <div class="panel-title">商品</div>
		 <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-down" ></a></div>
	 </div>
	 <div  class="panel-body layout-body" id="southBody" style="height: 31%">

	 </div>
 </t:formvalid>
 <!-- 添加 附表明细 模版 -->
 <table style="display:none">
	 <tbody id="add_tScQuotecustomer_table_template">
	 <tr>
		 <td align="center" bgcolor="#F6FCFF">
			 <div style="width: 25px;background-color: white;" name="xh"></div>
			 <input name="tScQuotecustomerList[#index#].indexNumber" type="hidden"/>
			 <input name="tScQuotecustomerList[#index#].itemID" type="hidden"/>
		 </td>
		 <td align="center" bgcolor="white">
			 <div style="width: 80px;background-color: white;">
				 <a name="addTScQuotecustomerBtn[#index#]" id="addTScQuotecustomerBtn[#index#]" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScQuotecustomerBtn('#index#');"></a>
				 <a name="delTScQuotecustomerBtn[#index#]" id="delTScQuotecustomerBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScQuotecustomerBtn('#index#');"></a>
			 </div>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].itemName" maxlength="32"
					type="text" class="inputxt popup-select" style="width:120px;"
					datatype="*" onkeypress="tScQuotecustomerItemIdListKeyPress('#index#',event);"
					onblur="tScQuotecustomerItemIdListBlur('#index#');"
					 >
			 <label class="Validform_label" style="display: none;">客户</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].contact" maxlength="25"
					type="text" class="inputxt" style="width:120px;"

					 >
			 <label class="Validform_label" style="display: none;">联系人</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].mobilePhone" maxlength="15"
					type="text" class="inputxt" style="width:120px;"  datatype="m" ignore="ignore"

					 >
			 <label class="Validform_label" style="display: none;">手机号码</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].phone" maxlength="40"  datatype="po" ignore="ignore"
					type="text" class="inputxt" style="width:120px;"

					 >
			 <label class="Validform_label" style="display: none;">电话</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].fax" maxlength="40" datatype="f" ignore="ignore"
					type="text" class="inputxt" style="width:120px;"

					 >
			 <label class="Validform_label" style="display: none;">传真</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].address" maxlength="125"
					type="text" class="inputxt" style="width:120px;"

					 >
			 <label class="Validform_label" style="display: none;">联系地址</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuotecustomerList[#index#].note" maxlength="125"
					type="text" class="inputxt" style="width:120px;"

					 >
			 <label class="Validform_label" style="display: none;">备注</label>
		 </td>
	 </tr>
	 </tbody>
	 <tbody id="add_tScQuoteitems_table_template">
	 <tr>
		 <td align="center" bgcolor="#F6FCFF">
			 <input name="tScQuoteitemsList[#index#].indexNumber" type="hidden"/>
			 <input name="tScQuoteitemsList[#index#].itemID" type="hidden"/>
			 <div style="width: 25px;background-color: white;" name="xh"></div>
		 </td>
		 <td align="center"  bgcolor="white">
			 <div style="width: 80px;background-color: white;">
				 <a id="addTScQuoteitemsBtn[#index#]" name="addTScQuoteitemsBtn[#index#]"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScQuoteitemsBtn('#index#');"></a>
				 <a name="delTScQuoteitemsBtn[#index#]" id="delTScQuoteitemsBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScQuoteitemsBtn('#index#');"></a>

			 </div>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].itemNumber" maxlength="32" type="text" class="inputxt popup-select" style="width:120px;" datatype="*"
					onkeypress="tScQuoteitemsListItemIdListKeyPress('#index#',event);"
					onblur="tScQuoteitemsListItemIdListBlur('#index#');">
			 <label class="Validform_label" style="display: none;">商品</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].itemName"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].itemModel"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].itemBarcode"  type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"/>
		 </td>
		 <td align="left" bgcolor="white">
			 <input  id="tScQuoteitemsList[#index#].unitID"  name="tScQuoteitemsList[#index#].unitID" class="inputxt"   style="width:80px;">
			 <label class="Validform_label" style="display: none;">单位</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].begQty" maxlength="32"
					type="text" class="inputxt" style="width:120px;" value="0"
					datatype="num"
					 >
			 <label class="Validform_label" style="display: none;">数量段(从)</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].endQty" maxlength="32"
					type="text" class="inputxt" style="width:120px;"
					datatype="num" value="0"
					 >
			 <label class="Validform_label" style="display: none;">数量段(至)</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].price" maxlength="32"
					type="text" class="inputxt" style="width:120px;" onchange="countGrossAmount('#index#')"
					datatype="num"
					 >
			 <label class="Validform_label" style="display: none;">单价</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].costPrice" maxlength="32"
					type="text" class="inputxt" style="width:120px;"
					readonly="true"
					 >
			 <label class="Validform_label" style="display: none;">成本单价</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].grossAmount" maxlength="32"
					type="text" class="inputxt" style="width:120px;"
					readonly="true"
					 >
			 <label class="Validform_label" style="display: none;">毛利</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].grossper" maxlength="32"
					type="text" class="inputxt" style="width:120px;"
					readonly="true"
					 >
			 <label class="Validform_label" style="display: none;">毛利率</label>
		 </td>
		 <td align="left" bgcolor="white">
			 <input name="tScQuoteitemsList[#index#].note" maxlength="255"
					type="text" class="inputxt" style="width:120px;"

					 >
			 <label class="Validform_label" style="display: none;">备注</label>
		 </td>
	 </tr>
	 </tbody>
 </table>
 <div style="position: absolute;bottom: 10px;left:60px;width:100%">
	 <div style="width: 33%;float: left">
		 <label  class="Validform_label footlabel">制单人: </label>
		 <span  class="inputxt footspan">${tScQuotePage.billerName}</span>
	 </div>
	 <div style="width: 33%;float: left">
		 <label  class="Validform_label footlabel">审核人: </label>
		 <span  class="inputxt footspan">${auditor}</span>
	 </div>
	 <div style="width: 34%;float: left"></div>
	 <label  class="Validform_label footlabel">审核时间: </label>
	 <span  class="inputxt footspan">${auditDate}</span>
 </div>
 </body>
 <script src="webpage/com/qihang/buss/sc/sales/tScQuote.js"></script>