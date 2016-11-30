<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style>
  #mm .menu-icon,#mm1 .menu-icon{
    top: 0px;
    color: black !important;
  }
  #mm .menu-text,#mm1 .menu-text{
    top: 0px;
    color: black !important;
  }
</style>
<div class="easyui-layout" fit="true">
  <div data-options="region:'west',title:'通讯录',split:true" style="width:200px;">
    <ul id="tt" class="easyui-tree"></ul>
  </div>
  <div region="center" style="padding:1px;">
      <iframe id="message" frameborder="no" style="width: 100%;height: 100%" src="tScContactController.do?tScContact&type=1"></iframe>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScContactList.js"></script>

 <script type="text/javascript">
 $(document).ready(function(){
     //  //添加datagrid 事件 onDblClickRow
/*         $("#tScFileList").datagrid({

             onDblClickRow:function(rowIndex,rowData){
                 url ='tScFileController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
                 createdetailwindow('查看', url, null, null);

             }

         });*/
 		//给时间控件加上样式
 			$("#tScFileListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tScFileListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tScFileController.do?upload', "tScFileList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tScFileController.do?exportXls","tScFileList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tScFileController.do?exportXlsByT","tScFileList");
}


 </script>