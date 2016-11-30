<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSTreeinfoList" checkbox="false" checkOnSelect="true" fitColumns="false" title="流程树表" actionUrl="tSTreeinfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="create_name"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="create_by"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="create_date" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="update_name"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="update_by"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="update_date" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="流程id"  field="act_id"   hidden="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="树名称"  field="tree_name"    queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="流程实例"  field="actname"    queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="节点名称"  field="node_name"    queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="类型"  field="type"  dictionary="NODE_VTYPE" queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="内容"  field="value"    queryMode="single" width="100"></t:dgCol>
   <t:dgCol title="备注"  field="note"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="节点id"  field="eid" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="250"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSTreeinfoController.do?doDel&id={eid}" />
   <t:dgFunOpt title="只读设置" funname="setReadOnly(eid,node_name)"/>
   <t:dgFunOpt title="面板设置"  funname="setPanel(eid,node_name)"></t:dgFunOpt>
   <t:dgFunOpt title="节点设置"  funname="setTaskNode(act_id,actname,eid)"></t:dgFunOpt>
   <t:dgToolBar title="新增" icon="icon-add" url="tSTreeinfoController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSTreeinfoController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSTreeinfoController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="权限管理"  icon="icon-edit" funname="roleManager"></t:dgToolBar>
  <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
<div region="east" style="width: 600px;" split="true">
 <div tools="#tt" class="easyui-panel" title='设置' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
 <script src = "webpage/system/tree/tSTreeinfoList.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tSTreeinfoListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tSTreeinfoListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tSTreeinfoController.do?upload', "tSTreeinfoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tSTreeinfoController.do?exportXls","tSTreeinfoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tSTreeinfoController.do?exportXlsByT","tSTreeinfoList");
}
  //只读设置：设置只读角色
  function setReadOnly(id,node_name){
   $("#function-panel").panel(
           {
            title :node_name+ ':' + '只读设置',
            href:"roleController.do?treeNode&node_id=" + id
           }
   );
   $('#function-panel').panel("refresh" );
  }
 //面板设置：设置该节点对应面板
 function setPanel(id,node_name) {
     $("#function-panel").panel(
             {
                 title: node_name + ':' + '面板设置',
                 href: "roleController.do?nodePanel&node_id=" + id
             }
     );
     $('#function-panel').panel("refresh");
 }
 //节点设置：设置资源树对应的流程节点
 function setTaskNode(id,taskName,node_id){
     $("#function-panel").panel(
             {
                 title: taskName + ':' + '节点设置',
                 href: "tSTreeinfoController.do?nodeTask&act_id=" + id+"&node_id="+node_id
             }
     );
     $('#function-panel').panel("refresh");
 }
 </script>