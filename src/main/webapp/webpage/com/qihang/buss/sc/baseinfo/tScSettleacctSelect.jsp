<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScSettleacctList" checkbox="${isMore}" fitColumns="false" title="结算账户" colConfig="false"
                    actionUrl="tScSettleacctController.do?datagrid&disabled=0&ids=${ids}" idField="id" fit="true" isSon="false"
                    queryMode="group" onLoadSuccess="loadsuccess">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled"  replace="启用_0,禁用_1"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<%--<script src="webpage/com/qihang/buss/sc/baseinfo/tScSettleacctList.js"></script>--%>
<script type="text/javascript">
    Array.prototype.remove=function(dx)
    {
        if(isNaN(dx)||dx>this.length){return false;}
        for(var i=0,n=0;i<this.length;i++)
        {
            if(this[i]!=this[dx])
            {
                this[n++]=this[i]
            }
        }
        this.length-=1
    }
    var flag = true;
    var popJon = new Array();
    var orgId;
    var isPop = false;
    //var flag = true;
    <%--function loadsuccess(){--%>
        <%--if(flag) {--%>
            <%--$("input[name='name']").val('${name}');--%>
            <%--$('.icon-query').click();--%>
            <%--flag = false;--%>
        <%--}--%>
    <%--}--%>
    //var popJon;
    //var orgId;
    //var isPop = false;
    function getSelectionData() {
        var rows = $('#tScSettleacctList').datagrid('getSelected');
        if (null != rows) {
            isPop =true ;
            return rows;

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择结算账户',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }

    function getMoreSelectionData(){
        var rows = popJon;
        if (rows.length > 0) {
            isPop =true ;
            return rows;

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择结算账户',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
    $(document).ready(function () {
        $("#tScSettleacctList").datagrid({
            onSelect:function(rowIndex,rowData){
                if(popJon.length) {
                    var isPush = true;
                    for (var i = 0; i < popJon.length; i++) {
                        var val = popJon[i];
                        if(val.id == rowData.id) {
                            isPush = false;
                        }
                    }
                    if(isPush) {
                        popJon.push(rowData);
                    }
                }else{
                    popJon.push(rowData);
                }
            },
            onUnselect:function(rowIndex,rowData){
                for(var i=0 ; i<popJon.length ; i++){
                    var value = popJon[i];
                    if(value.id == rowData.id){
                        popJon.remove(i);
                        break;
                    }
                }
            },
            onSelectAll:function(rows){
                for(var i=0 ; i<rows.length ; i++){
                    var row = rows[i];
                    var checkNum = 0;
                    for(var j = 0 ; j<popJon.length ; j++){
                        if(row.id != popJon[j].id){
                            checkNum++;
                        }
                    }
                    if(checkNum == popJon.length){
                        popJon.push(row);
                    }
                }
            },
            onUnselectAll:function(rows){
                for(var i=0 ; i<rows.length ; i++){
                    var row = rows[i];
                    for(var j = 0 ; j<popJon.length ; j++){
                        if(row.id == popJon[j].id){
                            popJon.remove(j);
                            break;
                        }
                    }
                }
            },
            onLoadSuccess:function(data){
                if(flag) {
                    $("input[name='name']").val('${name}');
                    $('.icon-query').click();
                    flag = false;
                }
                for(var i = 0; i<data.rows.length ;i++){
                    var row = data.rows[i];
                    var id = row.id;
                    for(var j=0 ; j<popJon.length ; j++){
                        var inf = popJon[j];
                        if(id==inf.id){
                            $("#tScSettleacctList").datagrid("selectRow",i);
                            //popJon.remove(j);
                            break;
                        }
                    }
                }
            }

        });
        //给时间控件加上样式
            $("#tScSettleacctListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScSettleacctListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScSettleacctController.do?upload', "tScSettleacctList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScSettleacctController.do?exportXls", "tScSettleacctList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScSettleacctController.do?exportXlsByT", "tScSettleacctList");
    }
</script>