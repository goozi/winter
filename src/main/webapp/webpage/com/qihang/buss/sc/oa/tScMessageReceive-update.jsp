<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>收信箱</title>
  <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScMessageReceiveController.do?doUpdate" callback="@Override noteSubmitCallback" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScMessageReceivePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScMessageReceivePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScMessageReceivePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScMessageReceivePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScMessageReceivePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScMessageReceivePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScMessageReceivePage.updateDate }">
					<input id="readStatus" name="readStatus" type="hidden" value="${tScMessageReceivePage.readStatus }">
					<input id="readDate" name="readDate" type="hidden" value="${tScMessageReceivePage.readDate }">
					<input id="userId" name="userId" type="hidden" value="${tScMessageReceivePage.userId }">
					<input id="messageId" name="messageId" type="hidden" value="${tScMessageReceivePage.messageId }">
		<table cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="title" name="title" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScMessageReceivePage.title}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
			</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发送人:
							</label>
						</td>
						<td class="value">
						     	 <input id="sender" name="sender" type="text" style="width: 150px" class="inputxt"  
									               
										       value='${tScMessageReceivePage.sender}'>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">发送人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								消息内容:
							</label>
						</td>
						<td class="value">
						     	 <%--<input id="content" name="content" type="text" style="width: 150px" class="inputxt"
										       value='${tScMessageReceivePage.content}'>--%>
									 <t:ckeditor name="content" isfinder="false" type="width:580,height:90,toolbar:[
						    ]" value="${tScMessageReceivePage.content}"></t:ckeditor>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">消息内容</label>
						</td>
					</tr>
			</table>
	  <div style="width: auto;height: 200px;">
			  <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
		  <div style="width:500px;height:1px;"></div>
		  <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
			  <t:tab href="tScMessageController.do?tScMessageFileList&id=${tScMessageReceivePage.messageId}" icon="icon-search" title="消息管理附件表" id="tScMessageFile"></t:tab>
		  </t:tabs>
	  </div>
		</t:formvalid>
  <!-- 添加 附表明细 模版 -->
  <table style="display:none">
	  <tbody id="add_tScMessageFile_table_template">
	  <tr>
		  <td align="center"><div style="width: 25px;" name="xh"></div></td>
		  <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
		  <td align="left">
			  <input type="hidden" id="tScMessageFileList[#index#].messageFile" name="tScMessageFileList[#index#].messageFile" />
			  <a  target="_blank" id="tScMessageFileList[#index#].messageFile_href">未上传</a>
			  <br>
			  <input class="ui-button" type="button" value="上传附件"
					 onclick="commonUpload(commonUploadDefaultCallBack,'tScMessageFileList\\[#index#\\]\\.messageFile')"/>
			  <label class="Validform_label" style="display: none;">附件</label>
		  </td>
	  </tr>
	  </tbody>
  </table>
 </body>
  <script src = "webpage/com/qihang/buss/sc/oa/tScMessageReceive.js"></script>