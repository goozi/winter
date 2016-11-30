<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>v_sc_promotion</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScPromotionViewController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScPromotionViewPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								entryid:
							</label>
						</td>
						<td class="value">
						     	 <input id="entryid" name="entryid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.entryid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">entryid</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								单据编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="billno" name="billno" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.billno}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								单据日期:
							</label>
						</td>
						<td class="value">
									  <input id="date" name="date" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScPromotionViewPage.date}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单据日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								经办人:
							</label>
						</td>
						<td class="value">
						     	 <input id="empid" name="empid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.empid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">经办人</label>
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
									               
										       value='${tScPromotionViewPage.deptid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">部门</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								起始日期:
							</label>
						</td>
						<td class="value">
									  <input id="begdate" name="begdate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScPromotionViewPage.begdate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">起始日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结束日期:
							</label>
						</td>
						<td class="value">
									  <input id="enddate" name="enddate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tScPromotionViewPage.enddate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">结束日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								摘要:
							</label>
						</td>
						<td class="value">
						     	 <input id="explanation" name="explanation" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.explanation}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">摘要</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								制单人:
							</label>
						</td>
						<td class="value">
						     	 <input id="billerid" name="billerid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.billerid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">制单人</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								审核人:
							</label>
						</td>
						<td class="value">
						     	 <input id="checkerid" name="checkerid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.checkerid}'>
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
									                
						      						 value='<fmt:formatDate value='${tScPromotionViewPage.checkdate}' type="date" pattern="yyyy-MM-dd"/>'>    
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
									               
										       value='${tScPromotionViewPage.checkstate}'>
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
									               
										       value='${tScPromotionViewPage.cancellation}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">作废标记</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								分支机构:
							</label>
						</td>
						<td class="value">
						     	 <input id="sonid" name="sonid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.sonid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">分支机构</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsnumber" name="goodsnumber" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsnumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsname" name="goodsname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								规格:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsmodel" name="goodsmodel" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsmodel}'>
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
						     	 <input id="goodsbarcode" name="goodsbarcode" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsbarcode}'>
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
						     	 <input id="goodsunitid" name="goodsunitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsunitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsqty" name="goodsqty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsqty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsnote" name="goodsnote" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsnote}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								商品:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsitemid" name="goodsitemid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsitemid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">商品</label>
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
									               
										       value='${tScPromotionViewPage.indexnumber}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">分录号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="givenumber" name="givenumber" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.givenumber}'>
							<span class="Validform_checktip">
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
						     	 <input id="givename" name="givename" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.givename}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								规格:
							</label>
						</td>
						<td class="value">
						     	 <input id="givemodel" name="givemodel" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.givemodel}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">规格</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								条形码:
							</label>
						</td>
						<td class="value">
						     	 <input id="givebarcode" name="givebarcode" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.givebarcode}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">条形码</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="giveunitid" name="giveunitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.giveunitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="giveqty" name="giveqty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.giveqty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
						     	 <input id="givenote" name="givenote" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.givenote}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品:
							</label>
						</td>
						<td class="value">
						     	 <input id="giveitemid" name="giveitemid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.giveitemid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">商品</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodunitid" name="goodunitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodunitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="goodsunitname" name="goodsunitname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.goodsunitname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="empname" name="empname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.empname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="deptname" name="deptname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.deptname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								billername:
							</label>
						</td>
						<td class="value">
						     	 <input id="billername" name="billername" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.billername}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">billername</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								sonname:
							</label>
						</td>
						<td class="value">
						     	 <input id="sonname" name="sonname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.sonname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">sonname</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="givunitid" name="givunitid" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.givunitid}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="giveunitname" name="giveunitname" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScPromotionViewPage.giveunitname}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
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
  <script src = "webpage/com/qihang/buss/sc/distribution/tScPromotionView.js"></script>		