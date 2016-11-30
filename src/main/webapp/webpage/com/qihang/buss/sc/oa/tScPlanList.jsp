<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScPlanList" sortName="createDate" checkOnSelect="true" sortOrder="desc" extendParams="autoRowHeight:false," checkbox="false" fitColumns="false" title="工作计划" actionUrl="tScPlanController.do?datagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="计划类型"  field="planType"  dictionary="planType"  query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="计划名称"  field="planName"   query="true" queryMode="single"  width="200"></t:dgCol>
            <t:dgCol title="负责人"  field="planMaster"    queryMode="single" dictionary="t_s_base_user,id,realname"  width="120"></t:dgCol>
            <t:dgCol title="参与人"  field="planGroup"    queryMode="single" dictionary="t_s_base_user,id,realname"  width="120"></t:dgCol>
            <t:dgCol title="领导批注"  field="planLeadder"    queryMode="single" dictionary="t_s_base_user,id ,realname"  width="120"></t:dgCol>
            <t:dgCol title="开始时间"  field="planStartdate" formatter="yyyy-MM-dd" query="true" queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="结束时间"  field="planEnddate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="计划人"  field="planer"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="计划内容"  field="planInfo"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="计划进度"  field="planProgress" extendParams="formatter:function(v,r,i){return changePercent(v,r,i);}," queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="计划状态"  field="planState"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgCol title="查询范围"  field="type"    queryMode="single" query="true" hidden="true" dictionary="planMember"  width="120"></t:dgCol>
            <t:dgDelOpt title="删除" exp="createBy#eq#${sessionScope.user.userName}&&planProgress#eq#0.0" url="tScPlanController.do?doDel&id={id}" />
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScPlanController.do?goAdd" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit"  url="tScPlanController.do?goUpdate&plan=false" onclick="checkValue();" funname="update"></t:dgToolBar>
            <t:dgToolBar title="计划跟踪" icon="new-icon-edit" url="tScPlanController.do?goUpdate&plan=true" funname="update"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScPlanController.do?doBatchDel" onclick="checkDelValue();" funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScPlanController.do?goUpdate&plan=true" funname="detail"></t:dgToolBar>
            <%--
               <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            --%>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
            <%--
               <t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>
            --%>
        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/qihang/buss/sc/oa/tScPlanList.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        //  //添加datagrid 事件 onDblClickRow
        $("#tScPlanList").datagrid({

            onDblClickRow:function(rowIndex,rowData){
                url ='tScPlanController.do?goUpdate'+ '&load=detail&plan=true&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,"tab");

            }
        });
        //给时间控件加上样式
        $("#tScPlanListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScPlanListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScPlanListtb").find("input[name='planStartdate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScPlanListtb").find("input[name='planStartdate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#tScPlanListtb").find("input[name='planEnddate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScPlanController.do?upload', "tScPlanList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScPlanController.do?exportXls","tScPlanList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScPlanController.do?exportXlsByT","tScPlanList");
    }

    function checkValue(){
        var select = $('#tScPlanList').datagrid('getSelected');
        var process = select.planProgress;
        if(process > 0){
            $.messager.show({
                title: '提示消息',
                msg: '该计划正在进行中，不可修改',
                timeout: 2000,
                showType: 'slide'
            });
            return;
        }
        var creator = select.createBy;
        if(creator != null && creator != '${sessionScope.user.userName}'){
            $.messager.alert("提示","您不是创建人，无法修改该数据！");
            return;
        }
        update('编辑', 'tScPlanController.do?goUpdate&plan=false', 'tScPlanList', null, null,'tab');
    }

    function checkDelValue(){
        var selects = $('#tScPlanList').datagrid('getSelections');
        var errInfo = "";
        for(var i=0 ; i<selects.length ; i++ ){
            var item = selects[i];
            if(item.planProgress > 0){
                errInfo+=item.planName+";";
            }
        }
        if(errInfo.length > 0){
            $.messager.show({
                title: '提示消息',
                msg: '计划名为['+errInfo+']的工作计划正在进行中或已完成，不可删除，请确认',
                timeout: 2000,
                showType: 'slide'
            });
            return;
        }
        deleteALLSelect("批量删除", 'tScPlanController.do?doBatchDel', 'tScPlanList');
    }

    function changePercent(v,r,i){
        v = v+"%";
        return v;
    }
</script>