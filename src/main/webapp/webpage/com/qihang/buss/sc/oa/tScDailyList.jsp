<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScDailyList" checkbox="false" checkOnSelect="true" sortName="dailyTime" sortOrder="desc" fitColumns="false" title="工作日志" actionUrl="tScDailyController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="日志标题"  field="dailyTitle"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="日志类型"  field="dailyType"  queryMode="single" dictionary="dailyType" width="120"></t:dgCol>
   <t:dgCol title="日志时间"  field="dailyTime" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="工作计划"  field="workPlan"    queryMode="single" dictionary="t_sc_plan,id,plan_name"  width="200"></t:dgCol>
   <t:dgCol title="日志内容"  field="dailyInfo"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="计划是否完成"  field="hasFinish"   hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgCol title="日志分类"  field="type"    queryMode="single" query="true" hidden="true" dictionary="dailyClass"  width="120"></t:dgCol>

      <t:dgDelOpt title="删除" exp="createBy#eq#${sessionScope.user.userName}" url="tScDailyController.do?doDel&id={id}&hasFinish={hasFinish}" />
   <t:dgToolBar title="新增" icon="new-icon-add" height="510" width="620" url="tScDailyController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="new-icon-edit" height="510" width="620" url="tScDailyController.do?goUpdate&plan=false" onclick="checkValue();" funname="update"></t:dgToolBar>
   <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tOaDailyController.do?doBatchDel" onclick="checkDelValue();" funname="deleteALLSelect"></t:dgToolBar>--%>
   <t:dgToolBar title="查看" icon="new-icon-view" height="510" width="620" url="tScDailyController.do?goUpdate&plan=true" funname="detail"></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScDailyList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     //  //添加datagrid 事件 onDblClickRow
         $("#tScDailyList").datagrid({

             onDblClickRow:function(rowIndex,rowData){
                 url ='tScDailyController.do?goUpdate'+ '&load=detail&plan=true&id=' + rowData.id;
                 createdetailwindow('查看', url, null, null,'tab');

             }

         });
         /**
          * 扩展两个方法
          */
         $.extend($.fn.datagrid.methods, {
             /**
              * 开打提示功能
              * @param {} jq
              * @param {} params 提示消息框的样式
              * @return {}
              */
             doCellTip : function(jq, params) {
                 function showTip(data, td, e) {
                     if ($(td).text() == "")
                         return;
                     data.tooltip.text($(td).text()).css({
                         top : (e.pageY + 10) + 'px',
                         left : (e.pageX + 20) + 'px',
                         'z-index' : $.fn.window.defaults.zIndex,
                         display : 'block'
                     });
                 };
                 return jq.each(function() {
                     var grid = $(this);
                     var options = $(this).data('datagrid');
                     if (!options.tooltip) {
                         var panel = grid.datagrid('getPanel').panel('panel');
                         var defaultCls = {
                             'border' : '1px solid #333',
                             'padding' : '1px',
                             'color' : '#333',
                             'background' : '#f7f5d1',
                             'position' : 'absolute',
                             'max-width' : '200px',
                             'border-radius' : '4px',
                             '-moz-border-radius' : '4px',
                             '-webkit-border-radius' : '4px',
                             'display' : 'none'
                         }
                         var tooltip = $("<div id='celltip'></div>").appendTo('body');
                         tooltip.css($.extend({}, defaultCls, params.cls));
                         options.tooltip = tooltip;
                         panel.find('.datagrid-body').each(function() {
                             var delegateEle = $(this).find('> div.datagrid-body-inner').length
                                     ? $(this).find('> div.datagrid-body-inner')[0]
                                     : this;
                             $(delegateEle).undelegate('td', 'mouseover').undelegate(
                                     'td', 'mouseout').undelegate('td', 'mousemove')
                                     .delegate('td', {
                                         'mouseover' : function(e) {
                                             if (params.delay) {
                                                 if (options.tipDelayTime)
                                                     clearTimeout(options.tipDelayTime);
                                                 var that = this;
                                                 options.tipDelayTime = setTimeout(
                                                         function() {
                                                             showTip(options, that, e);
                                                         }, params.delay);
                                             } else {
                                                 showTip(options, this, e);
                                             }

                                         },
                                         'mouseout' : function(e) {
                                             if (options.tipDelayTime)
                                                 clearTimeout(options.tipDelayTime);
                                             options.tooltip.css({
                                                 'display' : 'none'
                                             });
                                         },
                                         'mousemove' : function(e) {
                                             var that = this;
                                             if (options.tipDelayTime) {
                                                 clearTimeout(options.tipDelayTime);
                                                 options.tipDelayTime = setTimeout(
                                                         function() {
                                                             showTip(options, that, e);
                                                         }, params.delay);
                                             } else {
                                                 showTip(options, that, e);
                                             }
                                         }
                                     });
                         });

                     }

                 });
             },
             /**
              * 关闭消息提示功能
              * @param {} jq
              * @return {}
              */
             cancelCellTip : function(jq) {
                 return jq.each(function() {
                     var data = $(this).data('datagrid');
                     if (data.tooltip) {
                         data.tooltip.remove();
                         data.tooltip = null;
                         var panel = $(this).datagrid('getPanel').panel('panel');
                         panel.find('.datagrid-body').undelegate('td',
                                 'mouseover').undelegate('td', 'mouseout')
                                 .undelegate('td', 'mousemove')
                     }
                     if (data.tipDelayTime) {
                         clearTimeout(data.tipDelayTime);
                         data.tipDelayTime = null;
                     }
                 });
             }
         });
         $("#tScDailyList").datagrid({
             onLoadSuccess:function(data){
                 $('#tScDailyList').datagrid('doCellTip',{cls:{'background-color':'white'},delay:1000})
             }
         })
 		//给时间控件加上样式
 			$("#tScDailyListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScDailyListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScDailyListtb").find("input[name='dailyTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScDailyListtb").find("input[name='dailyTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

 function checkValue(){
     var select = $('#tScDailyList').datagrid('getSelected');
     var hasFinish = select.hasFinish;
     if(hasFinish == "true"){
         $.messager.show({
             title: '提示消息',
             msg: '该日志所属工作计划已完成，不可修改,请确认',
             timeout: 2000,
             showType: 'slide'
         });
         return;
     }
     update('编辑', 'tScDailyController.do?goUpdate&plan=false', 'tScDailyList', 620, 510,'tab')
 }

 function checkDelValue(){
     var selects = $('#tScDailyList').datagrid('getSelections');
     var errInfo = "";
     for(var i=0 ; i<selects.length ; i++ ){
         var item = selects[i];
         if(item.hasFinish == "true"){
             errInfo+=item.dailyTitle+";";
         }
     }
     if(errInfo.length > 0){
         $.messager.show({
             title: '提示消息',
             msg: '日志标题为['+errInfo+']所属工作计划已完成，不可删除，请确认',
             timeout: 2000,
             showType: 'slide'
         });
         return;
     }
     deleteALLSelect("批量删除", 'tScDailyController.do?doBatchDel', 'tScDailyList');
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScDailyController.do?upload', "tScDailyList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScDailyController.do?exportXls","tScDailyList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScDailyController.do?exportXlsByT","tScDailyList");
}
 </script>