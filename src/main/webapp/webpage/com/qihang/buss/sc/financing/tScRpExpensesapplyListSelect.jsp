<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">

        <div id="mm1" class="easyui-menu" style="width:120px;">
            <div onclick="selectBill()" data-options="iconCls:'icon-add'"><font color="black">选择本单</font></div>
            <div onclick="unselect()" data-options="iconCls:'icon-edit'"><font color="black">取消选择</font></div>
        </div>

        <t:datagrid name="tScRpExpensesapplySelectList" checkbox="true" fitColumns="false" title="费用申报单"
                    actionUrl="tScRpExpensesapplyController.do?datagridSelect&itemName=${param.itemName}&ids=${param.ids}&sonId=${sonId}"
                    idField="id" fit="true" queryMode="group"  tableName="tableName" checkOnSelect="true" onLoadSuccess="loadsuccess">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="明细id"  field="entryId" hidden="true"   queryMode="single"  width="50"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="版本号"  field="version"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据类型"  field="tranType"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="源单名称"  field="billName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

            <t:dgCol title="单据编号"  field="billNo"   query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="核算类别"  field="itemClassId"    queryMode="single"  width="120" replace="职员_1,部门_2"></t:dgCol>
            <t:dgCol title="核算项目"  field="itemName"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="经办人"  field="empName"    queryMode="single" query="true"  width="120"></t:dgCol>
            <t:dgCol title="部门"  field="deptName"    queryMode="single" query="true" width="120"></t:dgCol>
            <t:dgCol title="总金额"  field="allamount"    queryMode="single"  width="120"></t:dgCol>

            <t:dgCol title="分录号"  field="findex"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="收支项目"  field="feeName"    queryMode="single"  width="120" query="true" ></t:dgCol>
            <t:dgCol title="金额"  field="amount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="已报销金额"  field="haveAmount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="未报销金额"  field="notAmount"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="备注"  field="note"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="收支ID"  field="expId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>

            <t:dgCol title="摘要"  field="explanation"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="审核人"  field="checkUserName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="制单人"  field="billUserName"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="审核日期"  field="checkDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="审核状态"  field="checkState" replace="未审核_0,审核中_1,已审核_2"    queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="作废标记"  field="cancellation" replace="未作废_0,已作废_1"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="分支机构"  field="departName"    queryMode="single"  width="120"></t:dgCol>
        </t:datagrid>
    </div>

</div>
<script src = "webpage/com/qihang/buss/sc/financing/tScRpExpensesapplyList.js"></script>
<script type="text/javascript">
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
                msg: '请选择销售出库',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }
    function loadsuccess(data){
        if(data.rows.length > 0){
            mergeCellsByField("tScRpExpensesapplySelectList", "billNo,date,billName,itemClassId,itemName,empName,deptName,allamount,explanation," +
                    "checkUserName,billUserName,checkDate,checkState,cancellation,departName","id");
        }
        for (var i = 0; i < data.rows.length; i++) {
            var row = data.rows[i];
            var id = row.entryId;
            for (var j = 0; j < popJon.length; j++) {
                var inf = popJon[j];
                if (id == inf.entryId) {
                    $("#tScRpExpensesapplySelectList").datagrid("selectRow", i);
                    //popJon.remove(j);
                    break;
                }
            }
        }
    }
    $(document).ready(function(){
        //添加datagrid 事件 onDblClickRow
        //给时间控件加上样式

        $("#tScRpExpensesapplySelectListtb").find("input[name='date_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScRpExpensesapplySelectListtb").find("input[name='date_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

        $("#tScRpExpensesapplySelectList").datagrid({
            onSelect: function (rowIndex, rowData) {
                if (popJon.length) {
                    var isPush = true;
                    for (var i = 0; i < popJon.length; i++) {
                        var val = popJon[i];
                        if (val.entryId == rowData.entryId) {
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
                    if (value.entryId == rowData.entryId) {
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
                        if (row.entryId != popJon[j].entryId) {
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
                        if (row.entryId == popJon[j].entryId) {
                            popJon.remove(j);
                            break;
                        }
                    }
                }
            },
            onRowContextMenu: function (e, rowIndex, rowData) {
                return showRightMenu(e, rowData);
            }

        });
       });


    //合并行操作
    function mergeCellsByField(tableID, colList,id) {
        var ColArray = colList.split(",");
        var tTable = $("#" + tableID);
        var TableRowCnts = tTable.datagrid("getRows").length;
        var tmpA;
        var PerTxt = "";
        var CurTxt = "";
        var PerId = "";
        var CurId = "";
        for (var j = ColArray.length - 1; j >= 0; j--) {
            PerTxt = "";
            PerId = "";
            tmpA = 1;
            for (var i = 0; i <= TableRowCnts; i++) {
                if (i == TableRowCnts) {
                    CurTxt = "";
                    CurId = "";
                }
                else {
                    CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
                    CurId = tTable.datagrid("getRows")[i][id];

                }
                if ((PerTxt == CurTxt)&&(PerId==CurId)) {
                    tmpA += 1;
                }
                else {
                    var index =  i - tmpA;
                    tTable.datagrid("mergeCells", {
                        index: index,
                        field: ColArray[j],　　//合并字段
                        rowspan: tmpA,
                        colspan: null
                    });
                    tmpA = 1;
                }
                PerTxt = CurTxt;
                PerId = CurId;
            }
        }
    }
    //右键菜单配置
    var selectRow;
    function showRightMenu(e, rowData) {
        selectRow = rowData;
        e.preventDefault();
        //显示快捷菜单
        $('#mm1').menu({
            onShow: function () {
                selectRow = rowData;
            }
        });
        $('#mm1').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    }

    //单据全选
    function selectBill() {
        var id = selectRow.id;
        var entryUrl = "tScRpExpensesapplyController.do?loadEntryViewList&id=" + id;
        $.ajax({
            url: entryUrl,
            dataType: 'json',
            cache: false,
            async: false,
            success: function (data) {
                var rows = data;
                if (popJon.length > 0) {
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        for (var j = 0; j < popJon.length; j++) {
                            var value = popJon[j];
                            if (value.entryId == value.entryId) {
                                popJon.remove(j);
                                break;
                            }
                        }
                    }
                    for (var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        popJon.push(row);
                    }
                } else {
                    popJon = rows;
                }
                var rows = $("#tScRpExpensesapplySelectList").datagrid("getRows");
                for (var index = 0; index < rows.length; index++) {
                    var rowInfo = rows[index];
                    for (var k = 0; k < popJon.length; k++) {
                        if (rowInfo.entryId == popJon[k].entryId) {
                            $("#tScRpExpensesapplySelectList").datagrid("selectRow", index);
                            break;
                        }
                    }
                }
            }
        });
    }
    //取消选择
    function unselect() {
        var id = selectRow.id;
        var rows = $("#tScRpExpensesapplySelectList").datagrid("getRows");
        for (var index = 0; index < rows.length; index++) {
            var rowInfo = rows[index];
            for (var k = 0; k < popJon.length; k++) {
                if (rowInfo.id == id && rowInfo.id == popJon[k].id) {
                    $("#tScRpExpensesapplySelectList").datagrid("unselectRow", index);
                    break;
                }
            }
        }
    }
</script>