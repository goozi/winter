<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<%@include file="/context/mytags.jsp"%>--%>
<%--<t:base type="jquery,easyui,tools,DatePicker"></t:base>--%>
<%--&lt;%&ndash;   update-start--Author:duanql  Date:20130619 for：操作按钮窗口显示控制&ndash;%&gt;--%>
<%--<div id="system_function_functionList" class="easyui-layout" fit="true">&lt;%&ndash;   update-end--Author:duanql  Date:20130619 for：操作按钮窗口显示控制&ndash;%&gt;--%>
<%--<div region="center" style="padding: 1px;">--%>
    <%--<t:datagrid name="functionList" title="menu.manage"--%>
                <%--actionUrl="auditController.do?functionGrid" idField="id" width="200" treegrid="true" pagination="false">--%>
        <%--<t:dgCol title="common.id" field="id" treefield="id" hidden="true"></t:dgCol>--%>
        <%--<t:dgCol title="menu.name" field="functionName" treefield="text"></t:dgCol>--%>
        <%--<t:dgCol title="common.icon" field="TSIcon_iconPath" treefield="code" image="true"></t:dgCol>--%>
        <%--<t:dgCol title="funcType" field="functionType" treefield="functionType" replace="funcType.page_0,funcType.from_1"></t:dgCol>--%>
        <%--<t:dgCol title="menu.url" field="functionUrl" treefield="src"></t:dgCol>--%>
        <%--<t:dgCol title="menu.order" field="functionOrder" treefield="order"></t:dgCol>--%>
		<%--<t:dgCol title="开合状态" field="state" treefield="state" hidden="true" width="100"></t:dgCol>--%>
        <%--<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>--%>
        <%--<t:dgFunOpt funname="delFun(id)" exp="state#eq#open" title="清除"></t:dgFunOpt>--%>
		<%--<t:dgFunOpt funname="edit(id)" exp="state#eq#open" title="管理"></t:dgFunOpt>--%>
        <%--&lt;%&ndash;   update-start--Author:anchao  Date:20130415 for：按钮权限控制&ndash;%&gt;--%>
        <%--&lt;%&ndash;<t:dgFunOpt funname="operationDetail(id)" title="button.setting"></t:dgFunOpt>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<t:dgFunOpt funname="operationData(id)" title="数据规则"></t:dgFunOpt>&ndash;%&gt;--%>
        <%--&lt;%&ndash;   update-end--Author:anchao  Date:20130415 for：按钮权限控制&ndash;%&gt;--%>
        <%--&lt;%&ndash;<t:dgToolBar title="common.add.param" langArg="common.menu" icon="icon-add" url="functionController.do?addorupdate" funname="addFun" windowType="dialog"></t:dgToolBar>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<t:dgToolBar title="common.edit.param" langArg="common.menu" icon="icon-edit" url="functionController.do?addorupdate" funname="update" windowType="dialog"></t:dgToolBar>&ndash;%&gt;--%>
    <%--</t:datagrid>--%>
<%--</div>--%>
<%--</div>--%>
<%--&lt;%&ndash;   update-start--Author:duanql  Date:20130619 for：操作按钮窗口显示控制&ndash;%&gt;--%>
<%--<div data-options="region:'east',--%>
	<%--title:'<t:mutiLang langKey="operate.button.list"/>',--%>
	<%--collapsed:true,--%>
	<%--split:true,--%>
	<%--border:false,--%>
	<%--onExpand : function(){--%>
		<%--li_east = 1;--%>
	<%--},--%>
	<%--onCollapse : function() {--%>
	    <%--li_east = 0;--%>
	<%--}"--%>
	<%--style="width: 400px; overflow: hidden;">--%>
<%--<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="operationDetailpanel"></div>--%>
<%--</div>--%>
<%--</div>--%>

<%--<script type="text/javascript">--%>
<%--&lt;%&ndash;   update-start--Author:anchao  Date:20130415 for：按钮权限控制&ndash;%&gt;--%>
<%--$(function() {--%>
	<%--var li_east = 0;--%>
<%--});--%>
<%--//数据规则权数--%>
<%--function  operationData(fucntionId){--%>
	<%--if(li_east == 0){--%>
	   <%--$('#system_function_functionList').layout('expand','east'); --%>
	<%--}--%>
	<%--$('#operationDetailpanel').panel("refresh", "functionController.do?dataRule&functionId=" +fucntionId);--%>
<%--}--%>
<%--function operationDetail(functionId)--%>
<%--{--%>
	<%--if(li_east == 0){--%>
	   <%--$('#system_function_functionList').layout('expand','east'); --%>
	<%--}--%>
	<%--$('#operationDetailpanel').panel("refresh", "functionController.do?operation&functionId=" +functionId);--%>
<%--}--%>
<%--&lt;%&ndash;   update-end--Author:anchao  Date:20130415 for：按钮权限控制&ndash;%&gt;--%>
<%--&lt;%&ndash;   update-start--Author:Zerrion  Date:20130622 for：菜单录入代入父菜单&ndash;%&gt;--%>
<%--function addFun(title,url, id) {--%>
	<%--var rowData = $('#'+id).datagrid('getSelected');--%>
	<%--if (rowData) {--%>
		<%--url += '&TSFunction.id='+rowData.id;--%>
	<%--}--%>
	<%--add(title,url,'functionList');--%>
<%--}--%>


<%--&lt;%&ndash;   update-end--Author:Zerrion  Date:20130622 for：菜单录入代入父菜单&ndash;%&gt;--%>
<%--</script>--%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%--<div id="mm1" class="easyui-menu" style="width:120px;">--%>
	<%--<div onclick="addTree()" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>--%>
	<%--<div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>--%>
	<%--<div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>--%>
<%--</div>--%>
<div class="easyui-layout" fit="true">
	<div data-options="region:'west',title:'单据类型',split:true" style="width:200px;">
		<ul id="tree" class="easyui-tree" data-options="url:'auditController.do?getMenuTree'"></ul>
	</div>

	<div region="center" style="padding:1px;">
		<t:datagrid name="tScOrganizationList" checkbox="false" checkOnSelect="true" pagination="false" fitColumns="false" title="级次明细"
					actionUrl="auditController.do?datagrid" idField="id" fit="true"
					queryMode="group">
			<t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="单据名称" field="billName"   query="false" queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="单据类型id" field="menuId" hidden="true"  query="false" queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="一级" field="user1"   query="false" queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="二级" field="user2"   query="false" queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="三级" field="user3"   query="false" queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="四级" field="user4"    queryMode="single"  width="120"></t:dgCol>
			<t:dgCol title="五级" field="user5"    queryMode="single"   width="120"></t:dgCol>
			<t:dgCol title="六级" field="user6"    queryMode="single"   width="120"></t:dgCol>
			<t:dgToolBar title="管理" icon="new-icon-add"
						 onclick="edit()" funname="add"></t:dgToolBar>
			<t:dgToolBar title="清除" icon="icon-remove"
						 funname="delFun()"></t:dgToolBar>
			<%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
		</t:datagrid>
	</div>
</div>
<script src="webpage/com/qihang/buss/sysaudit/auditFunctionList.js"></script>
<script type="text/javascript">
	$(document).ready(function () {
		//  //添加datagrid 事件 onDblClickRow
		$("#tScOrganizationList").datagrid({

			onDblClickRow: function (rowIndex, rowData) {
				url = 'tSAuditController.do?goUpdate' + '&load=detail&infoId=' + rowData.id;
				createdetailwindow('查看', url, 800, 500);

			}

		});
//		//给时间控件加上样式
//		$("#tScOrganizationListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
//			WdatePicker({dateFmt: 'yyyy-MM-dd'});
//		});
//		$("#tScOrganizationListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
//			WdatePicker({dateFmt: 'yyyy-MM-dd'});
//		});
	});

//	//导入
//	function ImportXls() {
//		openuploadwin('Excel导入', 'tScOrganizationController.do?upload', "tScOrganizationList");
//	}

	//导出
	function ExportXls() {
		JeecgExcelExport("auditController.do?exportXls", "tScOrganizationList");
	}

//	//模板下载
//	function ExportXlsByT() {
//		JeecgExcelExport("tScOrganizationController.do?exportXlsByT", "tScOrganizationList");
//	}

	//清除
	function delFun(id){
		var select = $("#tree").tree("getSelected");
		if(null == select) {
			return;
		}
		var children = $("#tree").tree("getChildren",select.target);
		if(children.length > 0){
			return;
		}
		$.messager.confirm('确认对话框', '确认要清楚当前单据所有级次审核人数据吗？', function(r){
			if (r){
				var infoId = select.id;
				var url = "auditController.do?del&id="+infoId;
				$.ajax({
					async: false,
					cache: false,
					type: 'POST',
					url: url,// 请求的action路径
					error: function () {// 请求失败处理函数
					},
					success: function (d) {
						//var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							$("#tScOrganizationList").datagrid("reload");
							//setTimeout("location.reload();", 2000)
						} else {
							var msg = d.msg;
							tip(msg);
						}
					}
				});
			}
		});
	}
	//刷新列表
	function refreshTable(){
		$("#tScOrganizationList").datagrid("reload");
	}

	//管理
	function edit(id){
		var select = $("#tree").tree("getSelected");
		if(null == select) {
			return;
		}
		var children = $("#tree").tree("getChildren",select.target);
		if(children.length > 0){
			return;
		}
		var infoId = select.id;
		var url = "tSAuditController.do?goUpdate&infoId="+infoId;
		createwindow("编辑", url, 800, 550);
	}
</script>
