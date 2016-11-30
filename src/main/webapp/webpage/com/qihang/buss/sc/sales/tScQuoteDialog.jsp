<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div id="mm1" class="easyui-menu" style="width:120px;">
    <div onclick="selectBill()" data-options="iconCls:'icon-add'"><font color="black" >选择本单</font></div>
    <div onclick="unselect()" data-options="iconCls:'icon-edit'"><font color="black">取消选择</font></div>
  </div>
  <div region="center" style="padding:1px;">
    <t:datagrid name="tScQuoteList" checkbox="true"  fitColumns="false" title="销售报价单" actionUrl="tScQuoteController.do?datagridView${not empty checkState?'&checkState='.concat(checkState):''}${not empty customerId?'&customerId='.concat(customerId):''}&sonId=${sonId}" idField="entryId" fit="true" queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="分录主键"  field="entryId"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单据类型"  field="tranType" hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单据编号"  field="billNo"  queryMode="single" query="true"  width="120"></t:dgCol>
      <t:dgCol title="单据日期"  field="date" formatter="yyyy-MM-dd"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="经办人id"  field="empid" hidden="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="经办人"  field="empName" query="true"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="部门id"  field="deptid" hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="部门"  field="deptName"  queryMode="single"  width="120"></t:dgCol>

      <t:dgCol title="生效时间"  field="inureDate" formatter="yyyy-MM-dd"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="客户"  field="customerId" dictionary="T_SC_Organization,id,name"    queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="分录号"  field="indexNumber"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品id"   field="itemID"  hidden="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品编号"   field="icitemNumber"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品重量"   field="icitemWeight"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品名称"   field="icitemName"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品规格"   field="icitemModel"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="条形码"   field="icitemBarCode"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品单位换算率"   field="icitemCoefficient" query="hidden"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="销售限价"   field="icitemxsLimitPrice" query="hidden"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="商品关联的从表的单位id"   field="unitID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单位id"   field="icitemUnitId" hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单位"   field="icitemUnitName"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="单价"   field="price"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="成本单价"   field="costPrice"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="毛利"   field="grossAmount"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="毛利率"   field="grossper"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="数量段(从)"   field="begQty"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="数量段(至)"   field="endQty"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="审核状态"   field="checkState" hidden="true"  queryMode="single"  width="120"></t:dgCol>
    </t:datagrid>
  </div>
</div>
<script src = "webpage/com/qihang/buss/sc/sales/tScQuoteList.js"></script>
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
  var popJon = new Array();
  function getMoreSelectionData() {
    var rows = popJon;
    if (rows.length > 0) {
      return rows;

    } else {
      parent.$.messager.show({
        title: '提示消息',
        msg: '请选择数据',
        timeout: 2000,
        showType: 'slide'
      });
      return '';
    }
  }


  $(document).ready(function() {
    $("#tScQuoteList").datagrid({
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
      onLoadSuccess: function (data) {
        if(data.rows.length > 0){
          mergeCellsByField("tScQuoteList", "billNo,date,customerId,empid,empName,deptid,deptName,inureDate,auditorName,auditDate,checkState,sonName,opt","id");
        }
        for (var i = 0; i < data.rows.length; i++) {
          var row = data.rows[i];
          var entryId = row.entryId;
          for (var j = 0; j < popJon.length; j++) {
            var inf = popJon[j];
            if (entryId == inf.entryId) {
              $("#tScQuoteList").datagrid("selectRow", i);
              //popJon.remove(j);
              break;
            }
          }
        }
      }
      ,
      onRowContextMenu: function (e, rowIndex, rowData) {
        return showRightMenu(e, rowData);
      }

    });
  });
  //右键菜单配置
  var selectRow;
  function showRightMenu(e,rowData){
    selectRow = rowData;
    e.preventDefault();
    //显示快捷菜单
    $('#mm1').menu({
      onShow:function(){
        selectRow = rowData;
      }
    });
    $('#mm1').menu('show',{
      left: e.pageX,
      top: e.pageY
    });
  }
  //单据全选
  function selectBill(){
    var id = selectRow.id;
    var entryUrl = "tScQuoteController.do?getDatagridByNumberView&id="+id+"&checkState=2"+"&customerId="+'${customerId}';
    $.ajax({
      url: entryUrl,
      dataType: 'json',
      cache: false,
      async: false,
      success: function (data) {
        var rows = data;
        if(popJon.length > 0) {
          for (var i = 0; i < rows.length; i++) {
            var row = rows[i];
            for (var j = 0; j < popJon.length; j++) {
              var v = popJon[j];
              if (v.id == row.id) {
                continue;
              } else {
                popJon.push(row);
              }
            }
          }
        }else{
          popJon = rows;
        }
        var rows = $("#tScQuoteList").datagrid("getRows");
        for(var index = 0 ; index < rows.length ; index++){
          var rowInfo = rows[index];
          for(var k = 0 ; k<popJon.length ; k++){
            if(rowInfo.id == popJon[k].id){
              $("#tScQuoteList").datagrid("selectRow",index);
              break;
            }
          }
        }
      }
    });
  }

  //取消选择
  function unselect(){
     //var icitemNumber = selectRow.icitemNumber;
     var rows = $("#tScQuoteList").datagrid("getRows");
     for(var index = 0 ; index < rows.length ; index++){
        var rowInfo = rows[index];
        for(var k = 0 ; k<popJon.length ; k++){
          if(rowInfo.entryId == popJon[k].entryId){
            $("#tScQuoteList").datagrid("unselectRow",index);
            break;
          }
        }
      }
  }
/*   var popJon = new Array();
  function getMoreSelectionData() {
    debugger;
//    var rows = popJon;
    if(popJon.length>0){
      return  popJon;
    }else {
      var rows = $('#tScQuoteList').datagrid('getSelections');
      if (rows.length > 0) {
        return rows;

      } else {
        parent.$.messager.show({
          title: '提示消息',
          msg: '请选择数据',
          timeout: 2000,
          showType: 'slide'
        });
        return '';
      }
    }
  }
  $(document).ready(function(){
    $("#tScQuoteList").datagrid({
      onDblClickRow:function(rowIndex,rowData){
        var icitemNumber = rowData.icitemNumber;
        var customerId = '${customerId}';
       $.ajax({
              type: 'POST',
              url: 'tScQuoteController.do?getDatagridByNumberView',
              async: false,
              cache: false,
              data: {'icitemNumber': icitemNumber,'customerId':customerId,'checkState':1},
              dataType: 'json',
              success: function (data) {
                popJon = data;
                debugger;
                parent.$(".ui_state_highlight").click();
              }
            });
      }
    });
  });*/
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
</script>