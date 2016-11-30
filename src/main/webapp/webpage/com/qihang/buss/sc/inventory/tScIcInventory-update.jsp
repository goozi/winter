<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>即时库存基本表</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScIcInventoryController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScIcInventoryPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScIcInventoryPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScIcInventoryPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScIcInventoryPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScIcInventoryPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScIcInventoryPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScIcInventoryPage.updateDate }">
					<input id="itemId" name="itemId" type="hidden" value="${tScIcInventoryPage.itemId }">
					<input id="stockId" name="stockId" type="hidden" value="${tScIcInventoryPage.stockId }">
					<input id="count" name="count" type="hidden" value="${tScIcInventoryPage.count }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								箱数:
							</label>
						</td>
						<td class="value">
						     	 <input id="qty" name="qty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryPage.qty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">箱数</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								散数:
							</label>
						</td>
						<td class="value">
						     	 <input id="smallQty" name="smallQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryPage.smallQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">散数</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								基本数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="basicQty" name="basicQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryPage.basicQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">基本数量</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								辅助数量:
							</label>
						</td>
						<td class="value">
						     	 <input id="secQty" name="secQty" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryPage.secQty}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">辅助数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								存货单价:
							</label>
						</td>
						<td class="value">
						     	 <input id="costPrice" name="costPrice" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryPage.costPrice}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">存货单价</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								存货金额:
							</label>
						</td>
						<td class="value">
						     	 <input id="costAmount" name="costAmount" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScIcInventoryPage.costAmount}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">存货金额</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">

  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/inventory/tScIcInventory.js"></script>		