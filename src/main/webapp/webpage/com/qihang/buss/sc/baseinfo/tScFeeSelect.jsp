<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="mm1" class="easyui-menu" style="width:120px;">
    <div onclick="addTree('09')" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>
    <div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>
    <div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScFeeList" checkbox="true" fitColumns="false" title="收支项目" isSon="false"
                    actionUrl="tScFeeController.do?datagrid&disabled=0&deleted=0" idField="id" fit="true" colConfig="false"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name" query="true"  queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="简称" field="shortName" query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber" query="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"    queryMode="single"   width="200"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScFeeList.js"></script>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScItemTypeTree.js"></script>
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
    function getSelectionData() {
        var rows = $('#tScFeeList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择商品',
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
                msg: '请选择收支项目',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        $("#tScFeeList").datagrid({
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
                            $("#tScIcitemList").datagrid("selectRow",i);
                            //popJon.remove(j);
                            break;
                        }
                    }
                }
            }

        });
        //给时间控件加上样式
            $("#tScFeeListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScFeeListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });
</script>