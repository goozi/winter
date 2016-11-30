<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>资金其他收入</title>
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
  });
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tScRpRotherbillController.do?doUpdate"
			   title="其他收入单" tranType="${tScRpRotherbillPage.tranType}">
					<input id="id" name="id" type="hidden" value="${tScRpRotherbillPage.id }">
	  				<input id="load" value="${param.load}" type="hidden">
					<input id="createName" name="createName" type="hidden" value="${tScRpRotherbillPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScRpRotherbillPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScRpRotherbillPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScRpRotherbillPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScRpRotherbillPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScRpRotherbillPage.updateDate }">
					<input id="version" name="version" type="hidden" value="${tScRpRotherbillPage.version }">
					<input id="tranType" name="tranType" type="hidden" value="${tScRpRotherbillPage.tranType }" datatype="*">
	                <input id="billerId" name="billerId" type="hidden" value="${tScRpRotherbillPage.billerId}" datatype="*">
					  <input id="checkerId" name="checkerId" type="hidden" value="${tScRpRotherbillPage.checkerId}">
					  <input id="checkState" name="checkState" type="hidden" value="${tScRpRotherbillPage.checkState}">
					  <input id="cancellation" name="cancellation" type="hidden" value="${tScRpRotherbillPage.cancellation}">
					  <input id="sonId" name="sonId" type="hidden" value="${tScRpRotherbillPage.sonId}" datatype="*">
					  <input id="checkDate" name="checkDate" type="hidden" style="width: 150px"
			 class="inputxt" value='<fmt:formatDate value='${tScRpRotherbillPage.checkDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'>
					  <input name="billNo" type="hidden" value="${tScRpRotherbillPage.billNo}">
					  <input name="date" type="hidden" value="${tScRpRotherbillPage.date}">
	  <table cellpadding="0" cellspacing="1" class="formtable">
		  <tr>
			  <td align="right">
				  <label class="Validform_label">经办人:</label>
			  </td>
			  <td class="value">
				  <input id="empId" name="empId" type="hidden" style="width: 150px" class="inputxt" value="${tScRpRotherbillPage.empId}" datatype="*">
				  <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" value="${tScRpRotherbillPage.empName}" datatype="*">
      <span class="Validform_checktip">*
                </span>
				  <label class="Validform_label" style="display: none;">经办人</label>
			  </td>
			  <td align="right">
				  <label class="Validform_label">部门:</label>
			  </td>
			  <td class="value">
				  <input id="deptId" name="deptId" type="hidden" style="width: 150px" class="inputxt" value="${tScRpRotherbillPage.deptId}"
				 datatype="*" >
				  <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select"  datatype="*"
				 value="${tScRpRotherbillPage.deptName}" >
      <span class="Validform_checktip">*
                </span>
				  <label class="Validform_label" style="display: none;">部门</label>
			  </td>
		  </tr>
		  <tr>

			  <td align="right">
				  <label class="Validform_label">结算账户:</label>
			  </td>
			  <td class="value">
				  <select name="accountId" datatype="*">
					  <c:forEach items="${listSet}" var="set">
						  <option value="${set.id}"  <c:if test="${tScRpRotherbillPage.accountId==set.id}">selected="selected"</c:if>  >${set.name}</option>
					  </c:forEach>
				  </select>
      <span class="Validform_checktip">*
                </span>
				  <label class="Validform_label" style="display: none;">结算账户</label>
			  </td>

			  <td align="right">
				  <label class="Validform_label">总金额:</label>
			  </td>
			  <td class="value">
				  <input id="allAmount" name="allAmount" type="text" style="width: 150px" class="inputxt" readonly="readonly"
						 value="${tScRpRotherbillPage.allAmount}" datatype="float" >
      <span class="Validform_checktip">*
                </span>
				  <label class="Validform_label" style="display: none;">总金额</label>
			  </td>
		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">分支机构:</label>
			  </td>
			  <td class="value" colspan="3">
				  <input id="sonName" name="sonName" type="text" style="width: 150px" class="inputxt" value="${tScRpRotherbillPage.sonName}"
						 readonly="readonly"
				  >
      <span class="Validform_checktip">*
                </span>
				  <label class="Validform_label" style="display: none;">分支机构</label>
			  </td>
		  </tr>
		  <tr>
			  <td align="right">
				  <label class="Validform_label">摘要:</label>
			  </td>
			  <td class="value" colspan="3">
				  <input name="explanation" style="width: 600px"  datatype="*0-255" maxlength="255" value="${tScRpRotherbillPage.explanation}"/>
      <span class="Validform_checktip">
                </span>
				  <label class="Validform_label" style="display: none;">摘要</label>
			  </td>
		  </tr>
	  </table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tScRpRotherbillController.do?tScRpRotherbillentryList&id=${tScRpRotherbillPage.id}"
						icon="icon-search" title="资金其他收入明细" id="tScRpRotherbillentry"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
		<tbody id="add_tScRpRotherbillentry_table_template">
			<tr>
				<input name="tScRpRotherbillentryList[#index#].id" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].createName" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].createBy" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].createDate" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].updateName" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].updateBy" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].updateDate" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].fid" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].findex" type="hidden"/>
				<input name="tScRpRotherbillentryList[#index#].expId" type="hidden" datatype="*"/>
			 <td align="center" bgcolor="white">
				 <div style="width: 30px;" name="xh"></div>
			 </td>
			 <td align="center" bgcolor="white">
         <div style="width: 80px;">
           <a name="addTScRpRotherbillentryBtn[#index#]" id="addTScRpRotherbillentryBtn[#index#]" href="#"  onclick="clickAddTScRpRotherbillentryBtn('#index#');"></a>
           <a name="delTScRpRotherbillentryBtn[#index#]" id="delTScRpRotherbillentryBtn[#index#]" href="#"  onclick="clickDelTScRpRotherbillentryBtn('#index#');"></a>
         </div>
       </td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[#index#].expName" maxlength="32"
							   onkeypress="keyDownInfo('#index#',event)" onblur="orderListStockBlur('#index#','expId','expName');"
							    datatype="*"   onchange="clearExp(this)"
					  		type="text" class="inputxt popup-select"  style="width:120px;" />
					  <label class="Validform_label" style="display: none;">收支项目</label>
				  </td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[#index#].amount" maxlength="10" onchange="setAllAmount(this)"
					  		type="text" class="inputxt"  style="width:120px;" datatype="float" errormsg="请填写正确的金额" />
					  <label class="Validform_label" style="display: none;">金额</label>
				  </td>
				  <td align="left" bgcolor="white">
					  	<input name="tScRpRotherbillentryList[#index#].note" maxlength="255"
					  		type="text" class="inputxt"  style="width:120px;" />
					  <label class="Validform_label" style="display: none;">备注</label>
				  </td>
			</tr>
		 </tbody>
		</table>
<!--20160629  页脚显示 -->
  <!-- 页脚显示 -->
  <div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label class="Validform_label footlabel">制单人:${tScRpRotherbillPage.billerName} </label>
		  <span class="inputxt footspan"></span>
	  </div>
	  <div style="width: 33%;display: inline;float: left" align="left">
		  <label class="Validform_label footlabel">审核人:${tScRpRotherbillPage.checkUserName} </label>
		  <span class="inputxt footspan"> </span>
	  </div>
	  <div style="width: 33%;display: inline;float: right" align="left">
		  <label class="Validform_label footlabel">审核日期:<fmt:formatDate value='${tScRpRotherbillPage.checkDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/> </label>
		  <span class="inputxt footspan"> </span>
	  </div>
  </div>

 </body>
 <script src = "webpage/com/qihang/buss/sc/financing/tScRpRotherbill.js"></script>