<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>应收应付结账表</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScBalController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScBalPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScBalPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScBalPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScBalPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScBalPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScBalPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScBalPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								年份:
							</label>
						</td>
						<td class="value">
						     	 <input id="year" name="year" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.year}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">年份</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								月份:
							</label>
						</td>
						<td class="value">
						     	 <input id="period" name="period" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.period}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">月份</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="itemID" name="itemID" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.itemID}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">商品主键</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								仓库主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="stockID" name="stockID" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.stockID}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">仓库主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								批号:
							</label>
						</td>
						<td class="value">
						     	 <input id="batchNo" name="batchNo" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.batchNo}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">批号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								期初数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="begQty" name="begQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.begQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">期初数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								期初辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secBegQty" name="secBegQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.secBegQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">期初辅助数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								本期收入数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="receive" name="receive" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.receive}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本期收入数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本期发出数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="send" name="send" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.send}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本期发出数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								本年累计收入数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="ytdReceive" name="ytdReceive" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.ytdReceive}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本年累计收入数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本年累计发出数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="ytdSend" name="ytdSend" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.ytdSend}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本年累计发出数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								本年累计收入辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secYtdReceive" name="secYtdReceive" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.secYtdReceive}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本年累计收入辅助数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本年累计发出辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secYtdSend" name="secYtdSend" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.secYtdSend}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本年累计发出辅助数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								本期结存数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="endQty" name="endQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.endQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本期结存数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本期结存辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secEndQty" name="secEndQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.secEndQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本期结存辅助数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								期初金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="begBal" name="begBal" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.begBal}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">期初金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本期收入金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="debit" name="debit" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.debit}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本期收入金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								本期发出金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="credit" name="credit" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.credit}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本期发出金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								本年累计收入金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="ytdDebit" name="ytdDebit" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.ytdDebit}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本年累计收入金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								本年累计发出金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="ytdCredit" name="ytdCredit" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.ytdCredit}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">本年累计发出金额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								期末结存金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="endBal" name="endBal" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.endBal}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">期末结存金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								机构ID:
							</label>
						</td>
						<td class="value">
						     	 <input id="sonID" name="sonID" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScBalPage.sonID}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">机构ID</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">

  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/sys/tScBal.js"></script>		