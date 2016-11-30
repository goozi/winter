<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>工作日程</title>
     <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
     <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
     <script type="text/javascript" src="webpage/com/qihang/buss/sc/oa/static/js/locales/zh-cn.js"></script>
     <script type="text/javascript" src="webpage/com/qihang/buss/sc/oa/static/js/sea.js"></script>
     <script type="text/javascript" src="webpage/com/qihang/buss/sc/oa/static/js/seaconfig.js">  </script>
  <script type="text/javascript">
  //编写自定义JS代码
	  //取消
      function closePanel(){
          parent.document.getElementById("closePanel").click();
      }
	  //修改保存
	  function doUpdate(){
          $("#formobj").submit();
          parent.document.location.reload();
          parent.document.getElementById("closePanel").click();
	  }

  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tScScheduleController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tScSchedulePage.id }">
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		<input id="createName" name="createName" type="hidden" value="${tScSchedulePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScSchedulePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScSchedulePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScSchedulePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScSchedulePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScSchedulePage.updateDate }">
	  				<input id="IsAllDayEvent" name="IsAllDayEvent" type="hidden" value="${tScSchedulePage.isAllDayEvent }">
	  				<input id="timezone" name="timezone" type="hidden" value="${tScSchedulePage.timezone }">

	  <table style="width: 600px; height: 220px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								时间:
							</label>
						</td>
						<td class="value">
							<input id="CalendarStartTime" name="CalendarStartTime" type="text" style="width: 150px"
								   class="easyui-datetimebox" data-options="editable:false"
								   datatype="*"
								   value='${tScSchedulePage.calendarStartTime}'>
							<span>
                                  至
							</span>
							<input id="CalendarEndTime" name="CalendarEndTime" type="text" style="width: 150px"
								   class="easyui-datetimebox" data-options="editable:false"
								   datatype="*"
								   value='${tScSchedulePage.calendarEndTime}'/>
							<label class="Validform_label" style="display: none;">时间</label>
						</td>
                    </tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容:
							</label>
						</td>
						<td class="value">
							<textarea id="CalendarTitle" name="CalendarTitle" style="width:99.5%;height: 99.5%" class="inputxt" rows="6" name="dailyInfo">${tScSchedulePage.calendarTitle}</textarea>
							<label class="Validform_label" style="display: none;">内容</label>
						</td>
					</tr>
			</table>
            <%--<div align="center" style="padding-top: 5px;">--%>
                <%--<a class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="doUpdate();">保存</a>--%>
                <%--<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="closePanel();">取消</a>--%>
            <%--</div>--%>
		</t:formvalid>
 </body>
<%--
  <script src = "webpage/com/qihang/buss/oa/personal/tOaDaily.js"></script>--%>
