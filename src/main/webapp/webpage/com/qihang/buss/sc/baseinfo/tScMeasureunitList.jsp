<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScMeasureunitList" checkbox="false" checkOnSelect="true" fitColumns="false" title="单位" colConfig="false"
                    actionUrl="tScMeasureunitController.do?datagrid" idField="id" fit="true" isSon="false" tableName="t_sc_measureUnit"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name" query="true"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用" field="disabled"  queryMode="single" replace="否_0,是_1"   width="120"  ></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="count#eq#0" url="tScMeasureunitController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScMeasureunitController.do?goAdd"
                         funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScMeasureunitController.do?goUpdate"
                         funname="update" onclick="goUpdate()"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScMeasureunitController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScMeasureunitController.do?goUpdate"
                         funname="detail"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScMeasureunitController.do?goUpdate"
                         funname="fcopy" windowType="tab"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('单位', '#tScMeasureunitList')"></t:dgToolBar>
            <t:dgToolBar title="禁用" icon="icon-lock" funname="doDisabled"   ></t:dgToolBar>
            <t:dgToolBar title="启用" icon="icon-active" funname="doEnable"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScMeasureunitList.js"></script>
<script type="text/javascript">
    function geshi(){
        alert(4);
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScMeasureunitList").datagrid({

            onDblClickRow: function (rowIndex, rowData) {
                url = 'tScMeasureunitController.do?goUpdate' + '&load=detail&id=' + rowData.id;
                createdetailwindow('查看', url, null, null,'tab');

            }

        });
        //给时间控件加上样式
            $("#tScMeasureunitListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScMeasureunitListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScMeasureunitController.do?upload', "tScMeasureunitList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScMeasureunitController.do?exportXls", "tScMeasureunitList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScMeasureunitController.do?exportXlsByT", "tScMeasureunitList");
    }
    function doDisabled(){
        var rowsData = $('#tScMeasureunitList').datagrid('getSelections');
        if (!rowsData || rowsData.length==0) {
            tip('请选择禁用数据');
            return;
        }
        var id =rowsData[0].id;
        $.ajax({
            type: 'POST',
            url: 'tScMeasureunitController.do?doDisabled',
            async: false,
            data: {'id':id},
            dataType: 'json',
            success:function(data){
                if(data.success == true){
                    tip('禁用成功');
                    $('#tScMeasureunitList').datagrid('reload');
                }else{
                    tip('禁用失败');
                }
            }
        });

    }
    function doEnable(){
        var rowsData = $('#tScMeasureunitList').datagrid('getSelections');
        if (!rowsData || rowsData.length==0) {
            tip('请选择启用数据');
            return;
        }
        var id =rowsData[0].id;
        $.ajax({
            type: 'POST',
            url: 'tScMeasureunitController.do?doEnable',
            async: false,
            data: {'id':id},
            dataType: 'json',
            success:function(data){
                if(data.success == true){
                    tip(data.msg);
                    $('#tScMeasureunitList').datagrid('reload');
                }else{
                  tip('启用失败');
                }
            }
        });

    }
</script>