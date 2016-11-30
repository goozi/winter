<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScOrderList" checkbox="true" fitColumns="false" title="应付单"
                    actionUrl="tScPoStockbillController.do?datagridMain2&itemId=${itemId}&ids=${ids}&ids2=${ids2}&sonId=${sonId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="tranType" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据类型" field="typeName" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单据日期" field="date" query="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="单据编号" field="billNo" query="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="供应商Id" field="itemId" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="供应商" field="itemName" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="应付金额" field="allAmount" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="已付金额" field="checkAmount"  queryMode="single" width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/sales/tScOrderList.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //添加datagrid 事件 onDblClickRow
        $("#tScOrderList").datagrid({

//         onDblClickRow:function(rowIndex,rowData){
//             url ='tScOrderController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
//             createdetailwindow('查看', url, null, null,"tab");
//         },
            onSelect: function (rowIndex, rowData) {
                if (popJon.length) {
                    var isPush = true;
                    for (var i = 0; i < popJon.length; i++) {
                        var val = popJon[i];
                        if (val.id == rowData.id) {
                            isPush = false;
                        }
                    }
                    if (isPush) {
                        popJon.push(rowData);
                    }
                } else {
                    popJon.push(rowData);
                }
            },
            onUnselect: function (rowIndex, rowData) {
                for (var i = 0; i < popJon.length; i++) {
                    var value = popJon[i];
                    if (value.id == rowData.id) {
                        popJon.remove(i);
                        break;
                    }
                }
            },
            onSelectAll: function (rows) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    var checkNum = 0;
                    for (var j = 0; j < popJon.length; j++) {
                        if (row.id != popJon[j].id) {
                            checkNum++;
                        }
                    }
                    if (checkNum == popJon.length) {
                        popJon.push(row);
                    }
                }
            },
            onUnselectAll: function (rows) {
                for (var i = 0; i < rows.length; i++) {
                    var row = rows[i];
                    for (var j = 0; j < popJon.length; j++) {
                        if (row.id == popJon[j].id) {
                            popJon.remove(j);
                            break;
                        }
                    }
                }
            },
            onLoadSuccess: function (data) {
                for(var i = 0; i<data.rows.length ;i++){
                    var row = data.rows[i];
                    var id = row.id;
                    for(var j=0 ; j<popJon.length ; j++){
                        var inf = popJon[j];
                        if(id==inf.id){
                            $("#tScOrderList").datagrid("selectRow",i);
                            //popJon.remove(j);
                            break;
                        }
                    }
                }
            }
        });
        //给时间控件加上样式
        $("#tScOrderListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScOrderListtb").find("input[name='checkDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
    });

    Array.prototype.remove = function (dx) {
        if (isNaN(dx) || dx > this.length) {
            return false;
        }
        for (var i = 0, n = 0; i < this.length; i++) {
            if (this[i] != this[dx]) {
                this[n++] = this[i]
            }
        }
        this.length -= 1
    }
    var popJon = new Array();
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = popJon;
        if (rows.length > 0) {
            isPop = true;
            return rows;

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择销售订单',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }

</script>