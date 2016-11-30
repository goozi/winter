<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="cgFormHeadConfigList" checkbox="true" fitColumns="false" title="商品批号表" isSon="false" checkOnSelect="false"
                    actionUrl="tScIcInventoryController.do?datagridBatchNo&itemId=${itemId}&stockId=${stockId}&isCheck=${isCheck}&isZero=${isZero}" idField="id" fit="true"
                    queryMode="group">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="选择数量" field="chooseQty" editor="{type:'numberbox',options:{min:0,max:999,precision:0,onChange:checkChooseQty}}"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate"  formatter="yyyy-MM-dd" hidden="true"  queryMode="group"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品id" field="itemId" hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="仓库id" field="stockId" hidden="true"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="商品编号" field="itemNo"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="商品名称" field="itemName" query="true" queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="规格" field="model" query="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="条形码" field="barCode" query="true" queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="仓库" field="stockName" query="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="批号" field="batchNo"  query="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="箱数" field="qty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="散数" field="smallQty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="基本数量" field="basicQty"    queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="生产日期" field="kfDate" formatter="yyyy-MM-dd"   queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="保质期日期" field="kfPeriod"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="保质期" field="kfDateType" hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="保质期类型" field="kfDateType1"   queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="有效期至" field="periodDate" formatter="yyyy-MM-dd"   queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="辅助数量" field="secQty"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="存货单价" field="costPrice"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="存货金额" field="costAmount"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="单据数量" field="count"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="进项税"  field="taxRateIn"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="销项税"  field="taxRateOut" hidden="true"   queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="是否保质期标志" field="iSKFPeriod"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <t:dgCol title="是否批号管理标志" field="batchManager"  hidden="true"  queryMode="single"   width="100"></t:dgCol>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
            <%--<t:dgDelOpt title="删除" url="tScIcInventoryBatchnoController.do?doDel&id={id}"/>--%>
            <%--<t:dgToolBar title="录入" icon="icon-add" url="tScIcInventoryBatchnoController.do?goAdd"--%>
                         <%--funname="add"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScIcInventoryBatchnoController.do?goUpdate"--%>
                         <%--funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScIcInventoryBatchnoController.do?doBatchDel"--%>
                         <%--funname="deleteALLSelect"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="tScIcInventoryBatchnoController.do?goUpdate"--%>
                         <%--funname="detail"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="打印" icon="icon-print"--%>
                         <%--funname="CreateFormPage('商品批号明细', '#tScIcInventoryBatchnoList')"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<%--<script src="webpage/com/qihang/buss/sc/inventory/tScIcInventoryBatchnoList.js"></script>--%>
<script type="text/javascript">
    var rowIndex;
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
        var flag = true;
        $("#cgFormHeadConfigList").datagrid({

//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScIcInventoryBatchnoController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null);
//
//            }
            onDblClickCell:function(index,rowData){
                rowIndex = index;
            },
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
                    $("input[name='number']").val('${number}');
                    $('.icon-query').click();
                    flag = false;
                }
                for(var i = 0; i<data.rows.length ;i++){
                    var row = data.rows[i];
                    var id = row.id;
                    for(var j=0 ; j<popJon.length ; j++){
                        var inf = popJon[j];
                        if(id==inf.id){
                            $("#cgFormHeadConfigList").datagrid("selectRow",i);
                            //popJon.remove(j);
                            break;
                        }
                    }
                }
            }

        });
        //给时间控件加上样式
            $("#tScIcInventoryBatchnoListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcInventoryBatchnoListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScIcInventoryBatchnoListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

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
    <%--function loadsuccess(){--%>
    <%--if(flag) {--%>
    <%--$("input[name='number']").val('${number}');--%>
    <%--$('.icon-query').click();--%>
    <%--flag = false;--%>
    <%--}--%>
    <%--}--%>
    var popJon = new Array();
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#cgFormHeadConfigList').datagrid('getSelections');
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
            for(var i = 0 ; i < rows.length ; i++){
                var row = rows[i];
                var chooseQty = row.chooseQty;
                if(!chooseQty){
                    chooseQty = 0;
                    rows[i].chooseQty = 0;
                }
            }
            isPop =true ;
            return rows;

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

    function checkChooseQty(newData,oldData){
        if(newData) {
            $("#cgFormHeadConfigList").datagrid("selectRow", rowIndex);
        } else {
            $("#cgFormHeadConfigList").datagrid("unselectRow", rowIndex);
        }
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScIcInventoryBatchnoController.do?upload', "tScIcInventoryBatchnoList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScIcInventoryBatchnoController.do?exportXls", "tScIcInventoryBatchnoList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScIcInventoryBatchnoController.do?exportXlsByT", "tScIcInventoryBatchnoList");
    }
</script>