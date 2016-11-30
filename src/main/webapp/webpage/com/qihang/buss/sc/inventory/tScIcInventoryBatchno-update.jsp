<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>即时库存批号表</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScIcInventoryBatchnoController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScIcInventoryBatchnoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScIcInventoryBatchnoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScIcInventoryBatchnoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScIcInventoryBatchnoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScIcInventoryBatchnoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScIcInventoryBatchnoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScIcInventoryBatchnoPage.updateDate }">
					<input id="count" name="count" type="hidden" value="${tScIcInventoryBatchnoPage.count }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								商品id:
							</label>
						</td>
						<td class="value">
						     	 <input id="itemId" name="itemId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.itemId}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">商品id</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								仓库id:
							</label>
						</td>
						<td class="value">
						     	 <input id="stockId" name="stockId" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.stockId}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">仓库id</label>
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
									               
										       value='${tScIcInventoryBatchnoPage.batchNo}'>
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
						     	 <input id="kfDate" name="kfDate" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.kfDate}'>
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
						     	 <input id="kfPeriod" name="kfPeriod" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.kfPeriod}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">保质期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								保质期类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="kfDateType" name="kfDateType" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.kfDateType}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">保质期类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								有效期至:
							</label>
						</td>
						<td class="value">
						     	 <input id="periodDate" name="periodDate" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.periodDate}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">有效期至</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								箱数:
							</label>
						</td>
						<td class="value">
						     	 <input id="qty" name="qty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.qty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">箱数</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								散数:
							</label>
						</td>
						<td class="value">
						     	 <input id="smallQty" name="smallQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.smallQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">散数</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								基本数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="basicQty" name="basicQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.basicQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">基本数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secQty" name="secQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.secQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">辅助数量</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								存货单价:
							</label>
						</td>
						<td class="value">
						     	 <input id="costPrice" name="costPrice" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.costPrice}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">存货单价</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								存货金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="costAmount" name="costAmount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryBatchnoPage.costAmount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">存货金额</label>
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
  <script src = "webpage/com/qihang/buss/sc/inventory/tScIcInventoryBatchno.js"></script>		