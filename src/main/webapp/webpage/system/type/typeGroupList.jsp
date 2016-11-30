<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%--
<script type="text/javascript">
function typeGridTree_AddType() {
	var treeCtrlId = "typeGridTree";
	var node = $("#"+treeCtrlId).treegrid('getSelected');
	if (node == null) {
		tip("请选择一个字典组");
		return;
	}
	if (node.state == "closed" || node.children) {
	} else {//叶结点
		node = $("#"+treeCtrlId).treegrid('getParent', node.id); //获取当前节点的父节点
	}
	var groupid = node.id.substring(1);
	add("参数值录入("+node.text+")", "systemController.do?addorupdateType&typegroupid="+groupid, treeCtrlId);
}
function typeGridTree_UpdateType() {
	var treeCtrlId = "typeGridTree";
	var node = $("#"+treeCtrlId).treegrid('getSelected');
	if (node == null) {
		tip("请选择一个编辑对象。");
		return;
	}
	var nodeid = node.id.substring(1);
	if (node.state == "closed" || node.children) {
		createwindow("字典编辑", "systemController.do?aouTypeGroup&id="+nodeid);
	} else {//叶结点
		var pnode = $("#"+treeCtrlId).treegrid('getParent', node.id); //获取当前节点的父节点
		var groupid = pnode.id.substring(1);
		createwindow("参数值编辑", "systemController.do?addorupdateType&typegroupid="+groupid+"&id="+nodeid);
	}
}
</script>

&lt;%&ndash; add-start--Author:Zerrion  Date:20140807 for：添加字典查询条件 &ndash;%&gt;
<script>
    $(function() {
        var datagrid = $("#typeGridTreetb");
        datagrid.prepend($("#tempSearchColums").html());

        var toolbarDiv = $("#typeGridTreetb div.datagrid-toolbar");
        toolbarDiv.append($("#tempToolbar").html());
    });
    function processExpandTree() {
    }
    function typeGridTreesearch2() {
        processExpandTree();
        typeGridTreesearch();
    }
    function EnterPress2(e) {
        var e = e || window.event;
        if (e.keyCode == 13) {
            processExpandTree();
            typeGridTreesearch();
        }
    }
    function searchReset2(name) {
        $("#" + name + "tb").find(":input").val("");
        processExpandTree();
        typeGridTreesearch();
    }
</script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="<t:mutiLang langKey="dict.name"/>"><t:mutiLang langKey="dict.name"/>：</span>
            <input onkeypress="EnterPress2(event)" onkeydown="EnterPress2()" type="text" name="typegroupname" style="width: 100px">
        </span>
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="<t:mutiLang langKey="dict.code"/>"><t:mutiLang langKey="dict.code"/>：</span>
            <input onkeypress="EnterPress2(event)" onkeydown="EnterPress2()" type="text" name="typegroupcode" style="width: 100px">
        </span>
    </div>
</div>
<div id="tempToolbar" style="display: none">
    <span style="float:right">
        <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="typeGridTreesearch2()"><t:mutiLang langKey="common.query"/></a>
        <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-reload" onclick="searchReset2('typeGridTree')"><t:mutiLang langKey="common.reset"/></a>
    </span>
</div>
&lt;%&ndash; add-end--Author:Zerrion  Date:20140807 for：添加字典查询条件 &ndash;%&gt;

<t:datagrid name="typeGridTree" title="common.data.dictionary" actionUrl="systemController.do?typeGridTree"
            idField="id" treegrid="true" pagination="false">
	<t:dgCol title="common.code" field="id" treefield="id" hidden="true"></t:dgCol>
	<t:dgCol title="dict.name" field="typename" width="100" treefield="text"></t:dgCol>
	<t:dgCol title="dict.code" field="code" width="100" treefield="code"></t:dgCol>
	<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt url="systemController.do?delTypeGridTree&id={id}" title="common.delete"></t:dgDelOpt>
	<t:dgToolBar title="common.add.param" langArg="lang.dictionary.type" icon="icon-add" url="systemController.do?aouTypeGroup" funname="add"></t:dgToolBar>
	<t:dgToolBar title="common.add.param" langArg="lang.dictionary.value" icon="icon-add" funname="typeGridTree_AddType"></t:dgToolBar>
	<t:dgToolBar title="common.edit" icon="icon-edit" funname="typeGridTree_UpdateType"></t:dgToolBar>
</t:datagrid>
<input type="hidden" id="typeGroupId" name="typeGroupId" value="">
--%>

<%--// add-start--Author:Zerrion  Date:20140928 for：数据字典修改--%>
<div id="main_typegroup_list" class="easyui-layout" fit="true">
    <input type="hidden" id="userName" value="${sessionScope.user.userName}">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="typeGridTree" title="common.data.dictionary" actionUrl="systemController.do?typeGroupGrid"
                    idField="id" treegrid="false" pagination="false" onLoadSuccess="loadSuccess" queryMode="group">
            <t:dgCol title="common.code" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="dict.name" field="typegroupname" width="100" query="true"></t:dgCol>
            <t:dgCol title="dict.code" field="typegroupcode" width="100" treefield="code"></t:dgCol>
            <t:dgCol title="项目代码" field="projectCode" width="100" treefield="code" dictionary="PROJECT_CODE"></t:dgCol>
            <t:dgCol title="是否用户自定义" field="userCustom" width="100" treefield="code" dictionary="sf_01"></t:dgCol>
            <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
            <c:if test='${sessionScope.user.userName=="programmer"}'>
            <t:dgDelOpt url="systemController.do?delTypeGroup&id={id}" title="common.delete" ></t:dgDelOpt>
            </c:if>
            <t:dgFunOpt funname="queryTypeValue(id,typegroupname)" title="common.type.view"></t:dgFunOpt>
            <c:if test='${sessionScope.user.userName=="programmer"}'>
            <t:dgToolBar title="common.add.param" langArg="lang.dictionary.type" icon="icon-add" url="systemController.do?aouTypeGroup" funname="add" windowType="dialog"></t:dgToolBar>
            </c:if>
            <%--<t:dgToolBar title="common.add.param" langArg="lang.dictionary.value" icon="icon-add" funname="typeGridTree_AddType"></t:dgToolBar>--%>
            <t:dgToolBar title="common.edit" icon="icon-edit" url="systemController.do?aouTypeGroup" funname="updateTypeGroup" windowType="dialog"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<div data-options="region:'east',
	title:'mytitle',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
</div>

<script type="text/javascript">
    $(function() {
        var li_east = 0;
    });

    function queryTypeValue(typegroupid, typegroupname){
        var title = '<t:mutiLang langKey="lang.dictionary.type"/>: ' + typegroupname;
        if(li_east == 0){
            $('#main_typegroup_list').layout('expand','east');
        }
        $('#main_typegroup_list').layout('panel','east').panel('setTitle', title);
        $('#userListpanel').panel("refresh", "systemController.do?goTypeGrid&typegroupid=" + typegroupid);
    }
    function loadSuccess() {
        $('#main_typegroup_list').layout('panel','east').panel('setTitle', "");
        $('#main_typegroup_list').layout('collapse','east');
        $('#userListpanel').empty();
    }
    function updateTypeGroup(title,url,id,width,height,windowType){
//        debugger;
        var rowsData = $('#' + id).datagrid('getSelections');
        if (!rowsData || rowsData.length == 0) {
            tip('请选择编辑项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再编辑');
            return;
        }
        var userCustom = rowsData[0].userCustom;
        var userName = $("#userName").val();
        if(userCustom==0 && userName!='programmer'){
            $.messager.alert('提醒', '非用户自定义字典，不可编辑！');
        }else{
            update(title,url,id,width,height,windowType);
        }
    }
</script>
<%--// add-end--Author:Zerrion  Date:20140928 for：数据字典修改--%>
