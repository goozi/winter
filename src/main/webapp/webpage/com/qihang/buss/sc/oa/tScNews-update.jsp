<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>新闻管理</title>
  <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScNewsController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScNewsPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScNewsPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScNewsPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScNewsPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScNewsPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScNewsPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScNewsPage.updateDate }">
					<input id="issuance" name="issuance" type="hidden" value="${tScNewsPage.issuance }">
					<input id="issuanceDate" name="issuanceDate" type="hidden" value="${tScNewsPage.issuanceDate }">
	  				<input id="userIds"  type="hidden" value="${tScNewsPage.userId }">
	 				 <input id="detail"  type="hidden" value="${load}">
		<table cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="80">
							<label class="Validform_label">
								标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="title" name="title" type="text" style="width: 580px" class="inputxt"
									               datatype="*1-100" maxlength="100"
										       value='${tScNewsPage.title}'>
							<span class="Validform_checktip">*
							</span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								类型:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="type" type="list"
												  typeGroupCode="newsType" extendJson="{'datatype':'*'}"
										 defaultVal="${tScNewsPage.type}" hasLabel="false"  title="类型"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">类型</label>
						</td>
					</tr>
					<%--<tr>--%>
						<%--<td align="right">--%>
							<%--<label class="Validform_label">--%>
								<%--查看人员:--%>
							<%--</label>--%>
						<%--</td>--%>
						<%--<td class="value">--%>
							<%--<input class="easyui-combotree" id="userId" name="userId" style="width:155px;"--%>
									<%--></input>--%>
							<%--<span class="Validform_checktip">--%>
							<%--</span>--%>
							<%--<label class="Validform_label" style="display: none;">查看人员</label>--%>
						<%--</td>--%>
					<%--<tr>--%>
						<td align="right">
							<label class="Validform_label">
								新闻内容:
							</label>
						</td>
						<td class="value">
						     	 <%--<input id="content" name="content" type="text" style="width: 150px" class="inputxt"
										       value='${tScNewsPage.content}'>--%>
									 <t:ckeditor name="content" isfinder="false" type="width:document.body.availWidth,height:document.body.availHeight,toolbar:[
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    ['Link','Unlink','Anchor'],
    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
    ['Styles','Format','Font','FontSize'],
    ['TextColor','BGColor'],
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value='${tScNewsPage.content}'></t:ckeditor>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								允许评论:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="comment" type="radio"
										typeGroupCode="sf_01" defaultVal="${tScNewsPage.comment}" hasLabel="false"  title="允许评论"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
							<label class="Validform_label" style="display: none;">允许评论</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/qihang/buss/sc/oa/tScNews.js"></script>