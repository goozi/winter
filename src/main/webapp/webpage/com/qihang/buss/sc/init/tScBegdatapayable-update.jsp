<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>应付初始化</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  function doClearExt(){
	  debugger;
	  var date = $("#currDate").val();
	  $("#rpDate").val(date);
//	 $("input[name$='.qty']").val("");
	  //在此，请特别注意combobox和日期控件的数据重置方式不用采用.val(值)的方式。
  }
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
  <t:formvalid formid="formobj" dialog="true" title="${title}" usePlugin="password" layout="table"
			   saveTemp="true" action="tScBegdataController.do?doUpdate" tiptype="1" tableName="T_SC_BEGDATA" tranType="${tScBegdataPage.tranType }">
	  <input id="load" value="${param.load}" type="hidden">
	  <input id="id" name="id" type="hidden" value="${tScBegdataPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScBegdataPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScBegdataPage.createBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tScBegdataPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScBegdataPage.updateBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScBegdataPage.createDate }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScBegdataPage.updateDate }">
					<input id="tranType" name="tranType" type="hidden" value="${tScBegdataPage.tranType }">
					<input id="checkAmount" name="checkAmount" type="hidden" value="${tScBegdataPage.checkAmount }">
					<input id="unCheckAmount" name="unCheckAmount" type="hidden" value="${tScBegdataPage.unCheckAmount }">
	  <input id="checkState" name="checkState" type="hidden" value="${tScBegdataPage.checkState }">
	  <input id="version" name="version" type="hidden" value="${tScBegdataPage.version }">
	  <input name="clearExt" id="clearExt" type="hidden" value="doClearExt"/>
	  <input id="currDate" type="hidden" value="${rpDate}"/>
	  <input id="billNo" name="billNo" value="${tScBegdataPage.billNo }" type="hidden"/>
	  <input  name="date" value="${tScBegdataPage.date }" type="hidden"/>
	  <input id="billerID" name="billerID" value="${sessionScope.user.id}" type="hidden"/>
	  <input id="billDateType" name="billDateType" value="init" type="hidden">

		<table  cellpadding="0" cellspacing="1" class="formtable">
					<%--<tr>--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--单据编号:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
						     	 <%--<input id="billNo" name="billNo" type="text" style="width: 150px" class="inputxt"  --%>
									               <%--datatype="*"--%>
										       <%--value='${tScBegdataPage.billNo}'>--%>
							<%--<span class="Validform_checktip">--%>
                                  <%--*--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">单据编号</label>--%>
						<%--</td>--%>
					<%--<tr>--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--单据日期:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
									  <%--<input id="date" name="date" type="text" style="width: 150px" --%>
						      						<%--class="Wdate" onClick="WdatePicker()"--%>
									               <%--datatype="*" --%>
						      						 <%--value='<fmt:formatDate value='${tScBegdataPage.date}' type="date" pattern="yyyy-MM-dd"/>'>    --%>
							<%--<span class="Validform_checktip">--%>
                                  <%--*--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">单据日期</label>--%>
						<%--</td>--%>
					<%--</tr>--%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								供应商:
							</label>
						</td>
						<td class="value">
							<input id="itemID" name="itemID" value="${tScBegdataPage.itemID}" type="hidden"/>
						     	 <input id="itemName" name="itemName" type="text" style="width: 150px" class="inputxt popup-select"
									               datatype="*"
										       value='${tScBegdataPage.itemName}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">供应商</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								应付日期:
							</label>
						</td>
						<td class="value">
									  <input id="rpDate" name="rpDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									               datatype="*" 
						      						 value='<fmt:formatDate value='${tScBegdataPage.rpDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">应收日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经办人:
							</label>
						</td>
						<td class="value">
							<input id="empID" name="empID"  value='${tScBegdataPage.empID}' type="hidden"/>
						     	 <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select"
									               datatype="*"
										       value='${tScBegdataPage.empName}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">经办人</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								部门:
							</label>
						</td>
						<td class="value">
							<input id="deptID" name="deptID" value='${tScBegdataPage.deptID}' type="hidden"/>
						     	 <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select"
									               datatype="*"
										       value='${tScBegdataPage.deptName}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">部门</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								总金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="allAmount" name="allAmount" type="text" style="width: 150px" class="inputxt"  
									               datatype="num" onblur="allAmountPrecision()"
										       value='${tScBegdataPage.allAmount}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">总金额</label>
						</td>
						<tr>
							<td align="right">
								<label class="Validform_label">
									分支机构:
								</label>
							</td>
							<td class="value">
								<input type="text" style="width: 150px" class="inputxt" readonly
									   value="${sonName}">

								<input id="sonID" name="sonID" type="hidden" style="width: 150px" class="inputxt"
									   datatype="*"
									   value='${tScBegdataPage.sonID}'>
							<span class="Validform_checktip">
                                  *
							</span>
								<label class="Validform_label" style="display: none;">分支机构</label>
							</td>

						</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								摘要:
							</label>
						</td>
						<td class="value">
						     	 <input id="explanation" name="explanation" type="text" style="width: 500px" class="inputxt"
										datatype="*1-100" ignore="ignore"
										       value='${tScBegdataPage.explanation}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">摘要</label>
						</td>
					</tr>

			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="width:99%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label class="Validform_label footlabel">制单人: </label>
		  <span class="inputxt footspan"> ${tScBegdataPage.billerName}</span>
	  </div>
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label class="Validform_label footlabel">审核人: </label>
		  <span class="inputxt footspan">${tScBegdataPage.checkUserName} </span>
	  </div>
	  <div style="width: 33%;display:inline;float: left" align="left">
		  <label class="Validform_label footlabel">审核日期: </label>
		  <span class="inputxt footspan">${tScBegdataPage.checkDate} </span>
	  </div>

  </div>


  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/init/tScBegdatapayable.js"></script>