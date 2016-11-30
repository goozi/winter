<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScMeasureunitList" checkbox="false" checkOnSelect="true" fitColumns="false" title="单位" colConfig="false"
                    actionUrl="tScMeasureunitController.do?datagrid&disabled=0" idField="id" fit="true" isSon="false"
                    queryMode="group" onLoadSuccess="loadsuccess">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name" query="true"    queryMode="single"   width="120"></t:dgCol>
            <%--<t:dgCol title="禁用" field="disabled"  queryMode="single" replace="否_0,是_1"   width="120"  ></t:dgCol>--%>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<%--<script src="webpage/com/qihang/buss/sc/baseinfo/tScMeasureunitList.js"></script>--%>
<script type="text/javascript">
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}');
            $('.icon-query').click();
            flag = false;
        }
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tScMeasureunitList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScMeasureunitController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null);
//
//            }
//
//        });
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

    var popJon;
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tScMeasureunitList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择单位',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
</script>