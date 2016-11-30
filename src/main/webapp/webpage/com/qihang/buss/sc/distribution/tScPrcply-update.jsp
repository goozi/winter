<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>调价政策单编辑</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');

	  // 客户信息获取
	  $.ajax({
		  type: "get",
		  url: "tScPrcplyController.do?tScPrcplyentry1List&id=${tScPrcplyPage.id}",
		  success:function(data){
			  $("#centerBody").html(data);
			  centerBodyHigh = $("#centerBody").height();
		  }
	  });

	  // 商品信息获取
	  $.ajax({
		  type: "get",
		  url: "tScPrcplyController.do?tScPrcplyentry2List&id=${tScPrcplyPage.id}",
		  success:function(data){
			  $("#southBody").html(data);
			  southBodyHigh= $("#southBody").height();
		  }
	  });

	  $(".layout-button-up").click(toggleCenter);
	  $(".layout-button-down").click(toggleSouth);
	  $("#custHeader").dblclick(toggleCenter);
	  $("#goodsHeader").dblclick(toggleSouth);
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
 <style >
	 body {
		 position: absolute;
		 width: 100%;
		 height: 100%;
	 }
	 .footlabel {
		 float: left;
		 margin-left: 15px;
	 }
	 .footspan {
		 float: left;
		 margin-left: 5px;
		 margin-right: 5px;
		 font-weight: bold;
		 color: grey;
		 margin-bottom: 5px;
	 }
	 #centerBody{
		 overflow: hidden !important;
	 }
	 #southBody{
		 overflow: hidden !important;
	 }
	 .fullForm {
		 width: 100%;;
	 }
	 .spanBtn {
		 background-color: #CCE1F3;
		 display: -moz-inline-box;
		 display: inline-block;
		 width: 20px;
		 height: 20px;
		 text-align: center;
	 }
 </style>
 </head>
 <body style="overflow: hidden;">
 <input type="hidden" id="checkDate" value="${checkDate}">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" title="调价政策单" layout="table" tiptype="1"
			   action="tScPrcplyController.do?doUpdate" tranType="353" tableName="t_sc_prcply"
			   beforeSubmit="checkBillDate" isCancel="false">
	  <div class="panel-body layout-body" style="height: 64px">
		  <input id="load" name="load" value="${param.load}" type="hidden">
		  <input id="id" name="id" type="hidden" value="${tScPrcplyPage.id }"/>
		  <input id="createName" name="createName" type="hidden" value="${tScPrcplyPage.createName }"/>
		  <input id="createBy" name="createBy" type="hidden" value="${tScPrcplyPage.createBy }"/>
		  <input id="createDate" name="createDate" type="hidden" value="${tScPrcplyPage.createDate }"/>
		  <input id="updateName" name="updateName" type="hidden" value="${tScPrcplyPage.updateName }"/>
		  <input id="updateBy" name="updateBy" type="hidden" value="${tScPrcplyPage.updateBy }"/>
		  <input id="updateDate" name="updateDate" type="hidden" value="${tScPrcplyPage.updateDate }"/>
		  <input id="billerID" name="billerID" type="hidden" value="${sessionScope.user.id }"/>
		  <input id="checkerID" name="checkerID" type="hidden" value="${tScPrcplyPage.checkerID }"/>
		  <input id="checkDate1" name="checkDate" type="hidden" value="${tScPrcplyPage.checkDate }"/>
		  <input id="checkState" name="checkState" type="hidden" value="${tScPrcplyPage.checkState }"/>
		  <input id="cancellation" name="cancellation" type="hidden" value="${tScPrcplyPage.cancellation }"/>
		  <input id="disabled" name="disabled" type="hidden" value="${tScPrcplyPage.disabled }"/>
		  <input id="deleted" name="deleted" type="hidden" value="${tScPrcplyPage.deleted }"/>
		  <input id="version" name="version" type="hidden" value="${tScPrcplyPage.version }"/>
		  <input id="tranType" name="tranType" type="hidden" value="${tScPrcplyPage.tranType}"/>
		  <input id="empID" name="empID" type="hidden" value="${tScPrcplyPage.empID}"/>
		  <input id="deptID" name="deptID" type="hidden" value="${tScPrcplyPage.deptID}"/>
		  <input id="sonID" name="sonID" type="hidden" value="${tScPrcplyPage.sonID}"/>
		  <input id="customerId" name="customerId" type="hidden" value="${sessionScope.user.id}"/>
		  <input id="billNo" name="billNo" value="${tScPrcplyPage.billNo}" type="hidden"/>
		  <input id="date" name="date" value="${tScPrcplyPage.date}" type="hidden"/>
		  <table cellpadding="0" cellspacing="1" class="formtable" style="width: 100%">
			  <tr>
					  <%--<td align="right" style="width:6%">
                        <label class="Validform_label">单据编号:</label>
                    </td>
                    <td class="value" style="width:24%">
                        <input id="billNo" name="billNo" type="text" style="width: 150px" class="inputxt" datatype="*" value="${billNo}" readonly>
                        <span class="Validform_checktip">*</span>
                        <label class="Validform_label" style="display: none;">单据编号</label>
                    </td>
                    <td align="right" style="width:6%">
                        <label class="Validform_label">单据日期:</label>
                    </td>
                    <td class="value" style="width:24%">
                        <input id="date" name="date" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*"/>
                        <span class="Validform_checktip">* </span>
                        <label class="Validform_label" style="display: none;">单据日期</label>
                    </td>--%>
				  <td align="right" style="width:6%">
					  <label class="Validform_label">经办人:</label>
				  </td>
				  <td class="value" style="width:24%">
					  <input id="empName" name="empName" type="text" value="${tScPrcplyPage.empName}" style="width: 150px" class="inputxt popup-select" datatype="*" />
					  <span class="Validform_checktip">*</span>
					  <label class="Validform_label" style="display: none;">经办人</label>
				  </td>
				  <td align="right">
					  <label class="Validform_label">部门:</label>
				  </td>
				  <td class="value">
					  <input id="deptName" name="deptName" type="text" value="${tScPrcplyPage.deptName}" style="width: 150px" class="inputxt popup-select" datatype="*" />
					  <span class="Validform_checktip">*</span>
					  <label class="Validform_label" style="display: none;">部门</label>
				  </td>
				  <td align="right">
					  <label class="Validform_label">分支机构:</label>
				  </td>
				  <td class="value">
					  <input id="sonName" name="sonName" type="text" style="width: 150px" class="inputxt"  readonly="readonly" value='${tScPrcplyPage.sonName}'/>
					  <span class="Validform_checktip"></span>
					  <label class="Validform_label" style="display: none;">分支机构</label>
				  </td>
			  </tr>
			  <tr>
				  <td align="right">
					  <label class="Validform_label">摘要:</label>
				  </td>
				  <td class="value" colspan="5">
					  <input id="explanation" value="${tScPrcplyPage.explanation}" class="inputxt"  name="explanation" style="width: 72.5%" datatype="*1-126" ignore="ignore"/>
					  <span class="Validform_checktip"></span>
					  <label class="Validform_label" style="display: none;">摘要</label>
				  </td>
			  </tr>
		  </table>
	  </div>
	  <%--<div style="width: auto;height: 200px;">
            &lt;%&ndash; 增加一个div，用于调节页面大小，否则默认太小 &ndash;%&gt;
        <div style="width:800px;height:1px;"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScPrcplyController.do?tScPrcplyentry1List&id=${tScPrcplyPage.id}"
                   icon="icon-search" title="客户表" id="tScPrcplyentry1"></t:tab>
            <t:tab href="tScPrcplyController.do?tScPrcplyentry2List&id=${tScPrcplyPage.id}"
                   icon="icon-search" title="商品表" id="tScPrcplyentry2"></t:tab>
        </t:tabs>
    </div>--%>
	  <div class="panel-header" id="custHeader" >
		  <div class="panel-title">客户</div>
		  <div class="panel-tool"><a href="javascript:void(0)" class="layout-button-up"></a></div>
	  </div>
	  <div class="panel-body layout-body" id="centerBody" style="height: 26%"></div>

	  <div class="panel-header" id="goodsHeader">
		  <div class="panel-title">商品</div>
		  <div class="panel-tool"><a href="javascript:void(0)" class="layou-button-down"></a></div>
	  </div>
	  <div class="panel-body layout-body" id="southBody" style="height: 26%"></div>
  </t:formvalid>
  <!-- 添加 附表明细 模版 -->
  <table style="display:none">
	  <tbody id="add_tScPrcplyentry2_table_template">
	  <tr>
		  <td align="center" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].id" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].createName" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].createBy" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].createDate" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].updateName" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].updateBy" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].updateDate" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].indexNumber" type="hidden"/>
			  <input name="tScPrcplyentry2List[#index#].interID" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].disabled" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].deleted" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].version" type="hidden" />
			  <input name="tScPrcplyentry2List[#index#].itemID" type="hidden" />
			  <div style="width: 25px;" name="xh"></div>
		  </td>
		  <td align="center" bgcolor="white">
			  <div style="width: 80px;">
				  <a name="addTScPrcplyentry2Btn[#index#]" id="addTScPrcplyentry2Btn[#index#]" href="#" onclick="clickAddEntryBtns('#index#');"></a>
				  <a name="delTScPrcplyentry2Btn[#index#]" id="delTScPrcplyentry2Btn[#index#]" href="#" onclick="clickDelEntryBtns('#index#');"></a>
			  </div>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].itemNo" maxlength="32" type="text" class="inputxt popup-select"  style="width:105px;"
					 onkeypress="keyDownInfo('#index#','item',event)"
					 onblur="orderListStockBlur('${stuts.index}','itemId','itemNo');"/>
			  <label class="Validform_label" style="display: none;">商品编号</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].itemName" maxlength="50" type="text" class="inputxt"  style="width:180px;background-color:rgb(226,226,226)" datatype="*" />
			  <label class="Validform_label" style="display: none;">商品名称</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].model" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:85px;background-color:rgb(226,226,226)" />
			  <label class="Validform_label" style="display: none;">规格</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].barCode" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:65px;background-color:rgb(226,226,226)" />
			  <label class="Validform_label" style="display: none;">条形码</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].unitID" id="tScPrcplyentry2List[#index#].unitID"  style="width:80px;"/>
			  <label class="Validform_label" style="display: none;">单位</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].begQty" maxlength="32" type="text" onblur="checkNum('#index#','qty','begQty');" class="inputxt"  style="width:65px;" value="0" datatype="d" ignore="ignore" />
			  <label class="Validform_label" style="display: none;">数量段（从）</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].endQty" maxlength="32" type="text" onblur="checkNum('#index#','qty','endQty');" class="inputxt"  style="width:65px;" value="0" datatype="d" ignore="ignore" />
			  <label class="Validform_label" style="display: none;">数量段（至）</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].price" maxlength="32" type="text" onblur="checkNum('#index#','price','price');" class="inputxt"  style="width:70px;" datatype="*" />
			  <label class="Validform_label" style="display: none;">原价</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].newPrice" maxlength="32" type="text" class="inputxt" onblur="checkNum('#index#','price','newPrice');"  style="width:71px;" datatype="*" onchange="changePrice('#index#')" />
			  <label class="Validform_label" style="display: none;">新价</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].differPrice" readonly="readonly"  maxlength="32" type="text" class="inputxt"  style="width:72px;background-color:rgb(226,226,226);" />
			  <label class="Validform_label" style="display: none;">差价</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].disCountRate" readonly="readonly"  maxlength="32" type="text" class="inputxt"  style="width:72px;background-color:rgb(226,226,226);"/>
			  <label class="Validform_label" style="display: none;">折扣率</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input id="#index#begDate" name="tScPrcplyentry2List[#index#].begDate" maxlength="20" type="text" class="Wdate"
					 onClick="WdatePicker({maxDate:'#F{$dp.$D(\'#index#endDate\')}'})"
					 style="width:80px;" datatype="*"  />
			  <label class="Validform_label" style="display: none;">起始日期</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input id="#index#endDate" name="tScPrcplyentry2List[#index#].endDate" maxlength="20" type="text" class="Wdate"
					 onClick="WdatePicker({minDate:'#F{$dp.$D(\'#index#begDate\')}'})"
					 style="width:80px;" datatype="*"  />
			  <label class="Validform_label" style="display: none;">结束日期</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].costPrice" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:70px;background-color:rgb(226,226,226);" />
			  <label class="Validform_label" style="display: none;">成本单价</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].grossper" maxlength="32" type="text" class="inputxt" readonly="readonly"  style="width:70px;background-color:rgb(226,226,226);" />
			  <label class="Validform_label" style="display: none;">原毛利率</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].newGrossper" maxlength="32" type="text" class="inputxt" readonly="readonly"   style="width:70px;background-color:rgb(226,226,226);" />
			  <label class="Validform_label" style="display: none;">新毛利率</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry2List[#index#].note" maxlength="255" type="text" class="inputxt"  style="width:180px;"/>
			  <label class="Validform_label" style="display: none;">备注</label>
		  </td>
	  </tr>
	  </tbody>
	  <tbody id="add_tScPrcplyentry1_table_template">
	  <tr>
		  <td align="center" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].id" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].createName" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].createBy" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].createDate" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].updateName" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].updateBy" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].updateDate" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].indexNumber" type="hidden"/>
			  <input name="tScPrcplyentry1List[#index#].interID" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].disabled" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].deleted" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].version" type="hidden" />
			  <input name="tScPrcplyentry1List[#index#].itemID" type="hidden" />
			  <div style="width: 25px;" name="xh"></div>
		  </td>
		  <td align="center" bgcolor="white">
			  <div style="width: 80px;">
				  <a name="addTScPrcplyentry1Btn[#index#]" id="addTScPrcplyentry1Btn[#index#]" href="#" onclick="clickAddEntryBtn('#index#' ,'tScPrcplyentry1');"></a>
				  <a name="delTScPrcplyentry1Btn[#index#]" id="delTScPrcplyentry1Btn[#index#]" href="#" onclick="clickDelEntryBtn('#index#');"></a>
			  </div>
		  </td>
		  <td align="left" bgcolor="white">
			  <input  name="tScPrcplyentry1List[#index#].itemName" maxlength="50" type="text" class="inputxt popup-select"  style="width:180px;" datatype="*" onkeypress="selectItemNameDialog('#index#' ,event)"  />
			  <label class="Validform_label" style="display: none;">客户</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].contact" maxlength="50" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" />
			  <label class="Validform_label" style="display: none;">联系人</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].mobilePhone" maxlength="15" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true"  datatype="m" ignore="ignore" />
			  <label class="Validform_label" style="display: none;">手机号码</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].phone" maxlength="40" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="po" ignore="ignore" />
			  <label class="Validform_label" style="display: none;">电话</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].fax" maxlength="40" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" datatype="f" ignore="ignore"/>
			  <label class="Validform_label" style="display: none;">传真</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].address" maxlength="255" type="text" class="inputxt"  style="width:120px; background-color: rgb(226, 226, 226);" readonly="true" value="${poVal.address }"/>
			  <label class="Validform_label" style="display: none;">地址</label>
		  </td>
		  <td align="left" bgcolor="white">
			  <input name="tScPrcplyentry1List[#index#].note" maxlength="255" type="text" class="inputxt"  style="width:100px;" value="${poVal.note }"/>
			  <label class="Validform_label" style="display: none;">备注</label>
		  </td>
	  </tr>
	  </tbody>
  </table>
  <!-- 页脚显示 -->
  <div style="position: absolute;bottom: -85px;left:10px;width:100%" >
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label  class="Validform_label footlabel">制单人: </label>
		  <span  class="inputxt footspan"> ${sessionScope.user.realName}</span>
	  </div>
	  <div style="width: 33%;display: inline;float: left" align="left">
		  <label  class="Validform_label footlabel">审核人: </label>
		  <span  class="inputxt footspan">${auditor}</span>
	  </div>
	  <div style="width: 33%;display: inline;float: right" align="left">
		  <label  class="Validform_label footlabel">审核日期: </label>
		  <span  class="inputxt footspan">${auditDate}</span>
	  </div>
  </div>
 </body>
 <script src="webpage/com/qihang/buss/sc/distribution/tScPrcply.js"></script>