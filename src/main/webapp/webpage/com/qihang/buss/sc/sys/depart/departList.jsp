<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
        <t:datagrid name="departList" title="common.department.list"
                    actionUrl="departController.do?departgrid" checkbox="true"
                    treegrid="true" idField="departid" pagination="false">
            <t:dgCol title="common.id" field="id" treefield="id" hidden="true"></t:dgCol>
            <t:dgCol title="分支机构名称" field="departname" treefield="text"></t:dgCol>
            <t:dgCol title="机构描述" hidden="true" field="description" treefield="src"></t:dgCol>
            <t:dgCol title="common.org.code" field="orgCode" treefield="fieldMap.orgCode"></t:dgCol>
            <t:dgCol title="common.org.type" field="orgType" dictionary="sc_orgtype" treefield="fieldMap.orgType"></t:dgCol>
            <t:dgCol title="common.operation" field="opt"></t:dgCol>
            <t:dgDelOpt url="departController.do?del&id={id}" title="common.delete"></t:dgDelOpt>
            <%--<t:dgFunOpt funname="queryUsersByDepart(id)" title="view.member"></t:dgFunOpt>--%>
            <%--<t:dgFunOpt funname="setRoleByDepart(id,text)" title="role.set"></t:dgFunOpt>--%>
        </t:datagrid>
        <div id="departListtb" style="padding: 3px; height: 25px">
            <div style="float: left;">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="addOrg()">新增分支机构</a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑分支机构','departController.do?update_sc','departList',null,null,'tab')">编辑分支机构</a>
            </div>
        </div>
    </div>
</div>
<div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
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
<!--
    $(function() {
        var li_east = 0;
    });
    function addOrg() {
        var id = "";
        var orgCode = "";
        var rowsData = $('#departList').datagrid('getSelections');
        if (rowsData.length == 1) {
            id = rowsData[0].id;
            orgCode = rowsData[0]['fieldMap.orgCode'];
        }
        var url = "departController.do?add_sc&id=" + id + "&orgCode="+orgCode;
        add('新增分支机构', url, "departList",null,null,"tab");
    }

    function queryUsersByDepart(departid){
        var title = '<t:mutiLang langKey="member.list"/>';
        if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
            $('#main_depart_list').layout('expand','east');
        }
        <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
        $('#userListpanel').panel("refresh", "departController.do?userList&departid=" + departid);
    }
    /**
     * 为 组织机构 设置 角色
     * @param departid 组织机构主键
     * @param departname 组织机构名称
     */
    function setRoleByDepart(departid, departname){
        var currentTitle = $('#main_depart_list').layout('panel', 'east').panel('options').title;
        if(li_east == 0 || currentTitle.indexOf("<t:mutiLang langKey="current.org"/>") < 0){
            $('#main_depart_list').layout('expand','east');
        }
        var title = departname + ':<t:mutiLang langKey="current.org"/>';
        $('#main_depart_list').layout('panel','east').panel('setTitle', title);
        $('#main_depart_list').layout('panel','east').panel('resize', {width: 200});
        var url = {
            <%--title :"test",--%>
            href:"roleController.do?roleTree&orgId=" + departid
        }
        $('#userListpanel').panel(url);
        $('#userListpanel').panel("refresh");
    }
//-->
</script>