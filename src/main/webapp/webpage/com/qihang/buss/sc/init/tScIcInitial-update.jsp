<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>存货初始化单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <%--<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>--%>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript" src="plug-in/scm/js/computingTools.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
	$(".tabs-wrap").css('display', 'none');
	//库存初始化不用作废、反作废功能
	//$("[data-options*=new-icon-cancellation]").linkbutton("disable"); //作废
	//$("[data-options*=new-icon-uncancellation]").linkbutton("disable"); //反作废
	  $("#cancelBtn").css("display","none");
	  $("#unCancelBtn").css("display","none");
	  //解绑事件
	  setTimeout(
			  '$("#cancelBtn").unbind();' +
			  '$("#unCancelBtn").unbind();'
			  , 500);
  });
  //单据复制扩展数据重置函数
 function doClearExt(){
//	 $("#explanation").val("");
//	 $("input[name$='.qty']").val("");
	 //在此，请特别注意combobox和日期控件的数据重置方式不用采用.val(值)的方式。
 }
 </script>

   <style >
     /**20160629  页脚样式 **/
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
       text-decoration: underline;
     }
     </style>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" title="${title}" usePlugin="password" layout="table" tiptype="1"
			   action="tScIcInitialController.do?doUpdate" tranType="${tranType}" tableName="t_sc_ic_initial"
			   beforeSubmit="checkPriceIsZero" saveTemp="true">
	  <input id="load" value="${param.load}" type="hidden">
					<input id="id" name="id" type="hidden" value="${tScIcInitialPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScIcInitialPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScIcInitialPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScIcInitialPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScIcInitialPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScIcInitialPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScIcInitialPage.updateDate }">
				    <input id="tranType" name="tranType" type="hidden" value="${tScIcInitialPage.tranType}">
				    <input id="cancellation" name="cancellation" type="hidden" value="${tScIcInitialPage.cancellation}"/>
	  				<input id="checkState" name="checkState" type="hidden" value="${tScIcInitialPage.checkState}"/>
				    <input id="stockId" name="stockId" type="hidden" value="${tScIcInitialPage.stockId}"/>
				    <input id="sonId" name="sonId" value="${tScIcInitialPage.sonId}" type="hidden"/>
				    <input id="billerId" name="billerId" value="${tScIcInitialPage.billerId}" type="hidden"/>
				    <input id="billNo" name="billNo" value="${tScIcInitialPage.billNo}" type="hidden"/>
				    <input name="date" type="hidden" value="${tScIcInitialPage.date}"/>
	  				<input name="clearExt" id="clearExt" type="hidden" value="doClearExt"/>
	  				<input name="billDateType" id="billDateType" type="hidden" value="init"/>
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="10%">
				<label class="Validform_label">仓库:</label>
			</td>
			<td class="value" width="22%">
				<input id="stockName" name="stockName" value="${stockName}" type="text" style="width: 150px" class="inputxt popup-select">
				<span class="Validform_checktip"></span><span style="color: red">*</span>
				<label class="Validform_label" style="display: none;">仓库</label>
			</td>
			<td align="right" width="10%">
				<label class="Validform_label">分支机构:</label>
			</td>
			<td class="value" colspan="3">
				<input id="sonName" name="sonName" type="text" value="${sonName}"
					   readonly="readonly" style="width: 315px" class="inputxt">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">分支机构</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">摘要:</label>
			</td>
			<td class="value" colspan="5">
				<input id="explanation" name="explanation" value="${tScIcInitialPage.explanation}" type="text" style="width: 725px" class="inputxt">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">摘要</label>
			</td>
		</tr>
			</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tScIcInitialController.do?tScIcInitialentryList&id=${tScIcInitialPage.id}" icon="icon-search" title="附录明细" id="tScIcInitialentry"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
		<tbody id="add_tScIcInitialentry_table_template">
			<tr>
			 <td align="center" bgcolor="white">
				 <div style="width: 30px;" name="xh"></div>
			 </td>
			 <td align="center" bgcolor="white">
         <div style="width: 80px;">
           <a name="addTScIcInitialentryBtn[#index#]" id="addTScIcInitialentryBtn[#index#]" href="#"  onclick="clickAddTScIcInitialentryBtn('#index#');"></a>
           <a name="delTScIcInitialentryBtn[#index#]" id="delTScIcInitialentryBtn[#index#]" href="#"  onclick="clickDelTScIcInitialentryBtn('#index#');"></a>
         </div>
       </td>
				<input name="tScIcInitialentryList[#index#].coefficient" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].basicQty" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].secUnitId" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].basicUnitId" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].secCoefficient" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].secQty" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].indexnumber" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].stockId" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].itemId" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].kfDateType" type="hidden"/>
				<input name="tScIcInitialentryList[#index#].batchManager" type="hidden" value=""/>
				<input name="tScIcInitialentryList[#index#].isKFPeriod" type="hidden" value=""/>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].itemNo" maxlength="32"
						   type="text" class="inputxt popup-select" style="width:105px;"
						   datatype="*" onkeypress="keyDownInfo('#index#','item',event)"
						   onblur="orderListStockBlur('#index#','itemId','itemNo');"
					>
					<label class="Validform_label" style="display: none;">商品编号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].itemName" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:180px;background-color:rgb(226,226,226)" readonly="readonly"
						   datatype="*"
					>
					<label class="Validform_label" style="display: none;">商品名称</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].model" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:85px;background-color:rgb(226,226,226)" readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">商品规格</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].barCode" readonly="readonly" maxlength="32"
						   type="text" class="inputxt" style="width:65px;background-color:rgb(226,226,226)" readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">条形码</label>
				</td>
				<td align="left" bgcolor="white">
					<input id="unitId[#index#]" name="tScIcInitialentryList[#index#].unitId" maxlength="32"
						   type="text" style="width:50px;"
					>
					<label class="Validform_label" style="display: none;">单位</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].qty" maxlength="20"
						   type="text" class="inputxt" style="width:70px;"
						   datatype="num" value="0"
					>
					<label class="Validform_label" style="display: none;">箱数</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].smallQty" maxlength="20"
						   type="text" class="inputxt" style="width:120px;" datatype="*" value="0"/>
					<label class="Validform_label" style="display: none;">散数</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].coefficient" maxlength="20"
						   type="text" class="inputxt" style="width:120px;background-color:rgb(226,226,226)" readonly="readonly" datatype="*"/>
					<label class="Validform_label" style="display: none;">换算率</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].basicQty" maxlength="20"
						   type="text" class="inputxt" style="width:120px;background-color:rgb(226,226,226)" readonly="readonly"  datatype="vfloat" value="${poVal.basicQtystr }" ignore="ignore" errormsg="箱数或散数不能全为零"/>
					<label class="Validform_label" style="display: none;">数量</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].costPrice" maxlength="20"
						   type="text" class="inputxt" style="width:70px;"
						   datatype="float"
					>
					<label class="Validform_label" style="display: none;">成本单价</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].costAmount" maxlength="20"
						   type="text" class="inputxt" style="width:70px;background-color:rgb(226,226,226)" readonly="readonly"
						   datatype="num" ignore="ignore"
					>
					<label class="Validform_label" style="display: none;">成本金额</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].batchNo" maxlength="50"
						   type="text" class="inputxt" style="width:80px;"
						   datatype="*"
					>
					<label class="Validform_label" style="display: none;">批号</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].kfDate" maxlength="20"
						   type="text" class="Wdate" onClick="WdatePicker()" style="width:80px;"
						   onchange="setPeriodDate('#index#')"
					>
					<label class="Validform_label" style="display: none;">生产日期</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].kfPeriod" maxlength="10"
						   type="text" class="inputxt" style="width:50px;"
						   onchange="setPeriodDate('#index#')"
					>
					<label class="Validform_label" style="display: none;">保质期</label>
				</td>
				<td align="left" bgcolor="white">
					<%--<t:dictSelect field="tScIcInitialentryList[#index#].kfDateType" width="70" type="list"--%>
								  <%--showDefaultOption="true"--%>
								  <%--extendJson="{onChange:setPeriodDate(0)}"--%>
								  <%--typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false" title="保质期类型"></t:dictSelect>--%>
					<t:dictSelect field="tScIcInitialentryList[#index#].kfDateType_" width="70px" type="list"
								  showDefaultOption="true" extendJson="{disabled:disabled}"
								  typeGroupCode="SC_DURA_DATE_TYPE" defaultVal="" hasLabel="false" title="保质期类型"></t:dictSelect>
					<label class="Validform_label" style="display: none;">保质期类型</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].periodDate" maxlength="20"
						   type="text" class="Wdate" style="width:80px;background-color:rgb(226,226,226)" readonly="readonly"
						   readonly="readonly"
					>
					<label class="Validform_label" style="display: none;">有效期至</label>
				</td>
				<td align="left" bgcolor="white">
					<input name="tScIcInitialentryList[#index#].note" maxlength="255"
						   type="text" class="inputxt" style="width:180px;"/>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		 </tbody>
		</table>
<!--20160629  页脚显示 -->

  <!-- 页脚显示 -->
  <div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label class="Validform_label footlabel">制单人: </label>
		  <span class="inputxt footspan"> ${billerName}</span>
	  </div>
	  <div style="width: 33%;display: inline;float: left" align="left">
		  <label class="Validform_label footlabel">审核人: </label>
		  <span class="inputxt footspan">${checkUserName} </span>
	  </div>
	  <div style="width: 33%;display: inline;float: right" align="left">
		  <label class="Validform_label footlabel">审核日期: </label>
		  <span class="inputxt footspan">${tScIcInitialPage.checkDate} </span>
	  </div>
  </div>

 </body>
 <script src = "webpage/com/qihang/buss/sc/init/tScIcInitial.js"></script>	