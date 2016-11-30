<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>v_sc_drp_rstockbill</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScDrpRstockbillViewController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScDrpRstockbillViewPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								单据类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="trantype" name="trantype" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.trantype}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据类型</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								单据日期:
							</label>
						</td>
						<td class="value">
									  <input id="date" name="date" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScDrpRstockbillViewPage.date}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								单据编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billno" name="billno" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.billno}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								上游经销商或总部:
							</label>
						</td>
						<td class="value">
						     	 <input id="itemid" name="itemid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.itemid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">上游经销商或总部</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="itemname" name="itemname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.itemname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								联系人:
							</label>
						</td>
						<td class="value">
						     	 <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.contact}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">联系人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								手机号码:
							</label>
						</td>
						<td class="value">
						     	 <input id="mobilephone" name="mobilephone" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.mobilephone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">手机号码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								电话:
							</label>
						</td>
						<td class="value">
						     	 <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.phone}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								传真:
							</label>
						</td>
						<td class="value">
						     	 <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.fax}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">传真</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								联系地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="address" name="address" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.address}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">联系地址</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经办人:
							</label>
						</td>
						<td class="value">
						     	 <input id="empid" name="empid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.empid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">经办人</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="empname" name="empname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.empname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								部门:
							</label>
						</td>
						<td class="value">
						     	 <input id="deptid" name="deptid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.deptid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">部门</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="deptname" name="deptname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.deptname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								仓库:
							</label>
						</td>
						<td class="value">
						     	 <input id="rstockid" name="rstockid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.rstockid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">仓库</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="rstockname" name="rstockname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.rstockname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								物流费用:
							</label>
						</td>
						<td class="value">
						     	 <input id="freight" name="freight" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.freight}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">物流费用</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								单据金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="amount" name="amount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.amount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								应收金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="allamount" name="allamount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.allamount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">应收金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								重量:
							</label>
						</td>
						<td class="value">
						     	 <input id="weight" name="weight" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.weight}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">重量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								源单类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="rClassidSrc" name="rClassidSrc" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.rClassidSrc}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">源单类型</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								源单主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="rInteridSrc" name="rInteridSrc" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.rInteridSrc}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">源单主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								源单编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="rBillnoSrc" name="rBillnoSrc" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.rBillnoSrc}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">源单编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								审核人:
							</label>
						</td>
						<td class="value">
						     	 <input id="checkerid" name="checkerid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.checkerid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">审核人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								审核日期:
							</label>
						</td>
						<td class="value">
									  <input id="checkdate" name="checkdate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScDrpRstockbillViewPage.checkdate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">审核日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								审核状态:
							</label>
						</td>
						<td class="value">
						     	 <input id="checkstate" name="checkstate" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.checkstate}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">审核状态</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								作废标记:
							</label>
						</td>
						<td class="value">
						     	 <input id="cancellation" name="cancellation" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.cancellation}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">作废标记</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								摘要:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="explanation" style="width:600px;" class="inputxt" rows="6" name="explanation">${tScDrpRstockbillViewPage.explanation}</textarea>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">摘要</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								分支机构:
							</label>
						</td>
						<td class="value">
						     	 <input id="sonid" name="sonid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.sonid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">分支机构</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								sonname:
							</label>
						</td>
						<td class="value">
						     	 <input id="sonname" name="sonname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.sonname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">sonname</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								分录号:
							</label>
						</td>
						<td class="value">
						     	 <input id="indexnumber" name="indexnumber" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.indexnumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">分录号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								仓库:
							</label>
						</td>
						<td class="value">
						     	 <input id="stockid" name="stockid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.stockid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">仓库</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="stockname" name="stockname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.stockname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								商品:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsitemid" name="goodsitemid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.goodsitemid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">商品</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsname" name="goodsname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.goodsname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="number" name="number" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.number}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								规格:
							</label>
						</td>
						<td class="value">
						     	 <input id="model" name="model" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.model}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">规格</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								条形码:
							</label>
						</td>
						<td class="value">
						     	 <input id="barcode" name="barcode" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.barcode}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">条形码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="unitid" name="unitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.unitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="unitname" name="unitname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.unitname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								辅助单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="secunitid" name="secunitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.secunitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">辅助单位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="secunitname" name="secunitname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.secunitname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								基本单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="basicunitid" name="basicunitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.basicunitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">基本单位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="basicunitname" name="basicunitname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.basicunitname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								批号:
							</label>
						</td>
						<td class="value">
						     	 <input id="batchno" name="batchno" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.batchno}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">批号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								生产日期:
							</label>
						</td>
						<td class="value">
									  <input id="kfdate" name="kfdate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScDrpRstockbillViewPage.kfdate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">生产日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								保质期:
							</label>
						</td>
						<td class="value">
						     	 <input id="kfperiod" name="kfperiod" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.kfperiod}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">保质期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								有效期至:
							</label>
						</td>
						<td class="value">
									  <input id="perioddate" name="perioddate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScDrpRstockbillViewPage.perioddate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">有效期至</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								保质期类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="kfdatetype" name="kfdatetype" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.kfdatetype}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">保质期类型</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="qty" name="qty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.qty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								辅助换算率:
							</label>
						</td>
						<td class="value">
						     	 <input id="seccoefficient" name="seccoefficient" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.seccoefficient}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">辅助换算率</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secqty" name="secqty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.secqty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">辅助数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								换算率:
							</label>
						</td>
						<td class="value">
						     	 <input id="coefficient" name="coefficient" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.coefficient}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">换算率</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								基本数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="basicqty" name="basicqty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.basicqty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">基本数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								不含税单价:
							</label>
						</td>
						<td class="value">
						     	 <input id="price" name="price" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.price}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">不含税单价</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								不含税金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="rbamount" name="rbamount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.rbamount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">不含税金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								折扣率(%):
							</label>
						</td>
						<td class="value">
						     	 <input id="discountrate" name="discountrate" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.discountrate}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">折扣率(%)</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								不含税折后单价:
							</label>
						</td>
						<td class="value">
						     	 <input id="discountprice" name="discountprice" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.discountprice}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">不含税折后单价</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								不含税折后金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="discountamount" name="discountamount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.discountamount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">不含税折后金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								税率(%):
							</label>
						</td>
						<td class="value">
						     	 <input id="taxrate" name="taxrate" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.taxrate}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">税率(%)</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								单价:
							</label>
						</td>
						<td class="value">
						     	 <input id="taxpriceex" name="taxpriceex" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.taxpriceex}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单价</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="taxamountex" name="taxamountex" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.taxamountex}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								折后单价:
							</label>
						</td>
						<td class="value">
						     	 <input id="taxprice" name="taxprice" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.taxprice}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">折后单价</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								折后金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="intaxamount" name="intaxamount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.intaxamount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">折后金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								税额:
							</label>
						</td>
						<td class="value">
						     	 <input id="taxamount" name="taxamount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.taxamount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">税额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								源单主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="interidSrc" name="interidSrc" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.interidSrc}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">源单主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								源单编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billnoSrc" name="billnoSrc" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.billnoSrc}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">源单编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								源单分录主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="idSrc" name="idSrc" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.idSrc}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">源单分录主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								订单主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="interidOrder" name="interidOrder" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.interidOrder}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">订单主键</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								订单编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billnoOrder" name="billnoOrder" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.billnoOrder}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">订单编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								订单分录主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="idOrder" name="idOrder" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScDrpRstockbillViewPage.idOrder}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">订单分录主键</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="note" style="width:600px;" class="inputxt" rows="6" name="note">${tScDrpRstockbillViewPage.note}</textarea>
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
  <script src = "webpage/com/qihang/buss/sc/distribution/tScDrpRstockbillView.js"></script>		