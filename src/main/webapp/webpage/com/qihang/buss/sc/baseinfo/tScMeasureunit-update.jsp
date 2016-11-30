<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>单位</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tScMeasureunitController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScMeasureunitPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScMeasureunitPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScMeasureunitPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScMeasureunitPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScMeasureunitPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScMeasureunitPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScMeasureunitPage.updateDate }">
					<input id="disabled" name="disabled" type="hidden" value="${tScMeasureunitPage.disabled }">
					<input id="version" name="version" type="hidden" value="${tScMeasureunitPage.version }">
		<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt"  
									               datatype="*1-40"
										       value='${tScMeasureunitPage.name}'>
							<span class="Validform_checktip">
                                  *
							</span>
							<label class="Validform_label" style="display: none;">名称</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
<!--页脚字段显示 -->
  <div style="position: absolute;bottom: 10px;left:60px;">

  </div>
 </body>
  <script src = "webpage/com/qihang/buss/sc/baseinfo/tScMeasureunit.js"></script>		