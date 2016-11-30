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
  <div data-options="region:'west',title:'文件分类',split:true" style="width:200px;">
    <ul id="tt" class="easyui-tree"></ul>
    <%--// 右键菜单定义如下：--%>
    <div id="mm" class="easyui-menu" style="width:120px;">
      <div onclick="appendTT()" data-options="iconCls:'icon-add'" >追加</div>
      <div onclick="editTT()" data-options="iconCls:'icon-edit'">变更</div>
      <div onclick="removeTT()" data-options="iconCls:'icon-remove'">移除</div>
    </div>
    <div id="mm1" class="easyui-menu" style="width:120px;">
      <div onclick="appendTT()" data-options="iconCls:'icon-add'">追加</div>
    </div>

  </div>
  <div region="center" style="padding:1px;">
  <t:datagrid name="tScFileList" checkbox="true" fitColumns="false" title="文件柜" sortName="createDate" sortOrder="desc"
              actionUrl="tScFileController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="用户ID"  field="userId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="name"   query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="路径"  field="url"   hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="type"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类别"  field="fileType" dictionary="T_SC_DOCUMENT,id,name"   queryMode="single"  width="120"></t:dgCol>

   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="状态"  field="status"  dictionary="oa_fileSta"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
    <c:if test="${role == true}">
      <t:dgFunOpt title="审核" funname="audit(id)" exp="status#eq#0"></t:dgFunOpt>
      <t:dgDefOpt url="commonController.do?viewFile&fileid={url}" title="下载"></t:dgDefOpt>
      <t:dgOpenOpt width="800" height="700" url="commonController.do?openViewFile&fileid={url}" title="预览" ></t:dgOpenOpt>
    </c:if>
    <c:if test="${role == false}">
      <t:dgDefOpt url="commonController.do?viewFile&fileid={url}" title="下载" exp="status#eq#1"></t:dgDefOpt>
      <t:dgOpenOpt width="800" height="700" url="commonController.do?openViewFile&fileid={url}" title="预览" exp="status#eq#1"></t:dgOpenOpt>
    </c:if>

   <t:dgDelOpt title="删除" url="tScFileController.do?doDel&id={id}" />
   <t:dgToolBar title="上传" icon="icon-add" url="tScFileController.do?goAdd" onclick="commonUpload(fileCallback)" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tScFileController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
    <c:if test="${role == true}">
    <t:dgToolBar title="批量审核"  icon="icon-edit" url="tScFileController.do?doBatchAudit" funname="doBatchAudit"></t:dgToolBar>
    </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/qihang/buss/sc/oa/tScFileList.js"></script>

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