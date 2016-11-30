<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="mm1" class="easyui-menu" style="width:120px;">
  <div onclick="addTree('04')" data-options="iconCls:'icon-add'"><font color="black">添加</font></div>
  <div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>
  <div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>
</div>
<div class="easyui-layout" fit="true">
  <div data-options="region:'west',title:'商品分类',split:true" style="width:200px;">
    <ul id="tree" class="easyui-tree" data-options="url:'tScItemTypeController.do?getItemTypeTree&itemClassId=04'"></ul>
  </div>
  <div region="center" style="padding:1px;">
    <t:datagrid name="tScIcitemList" checkbox="false" checkOnSelect="true" fitColumns="false" title="商品主表" tableName="t_sc_icitem" isSon="false" colConfig="false"
                actionUrl="tScIcitemController.do?datagrid" idField="entryId" fit="true" queryMode="group" onDblClick="dbClickView">
      <t:dgCol title="从表主键" field="entryId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="主键" field="id" hidden="true" queryMode="single" width="120" ></t:dgCol>
      <t:dgCol title="编号" field="number" queryMode="single" width="120" mergeCells="true" query="true"></t:dgCol>
      <t:dgCol title="名称" field="name" query="true" queryMode="single" width="180" mergeCells="true"></t:dgCol>
      <t:dgCol title="简称" field="shortName" queryMode="single" width="120" mergeCells="true" query="true"></t:dgCol>
      <t:dgCol title="规格" field="model" queryMode="single" width="120" mergeCells="true" query="true"></t:dgCol>
      <t:dgCol title="重量" field="weight" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <%--<t:dgCol title="保质期" field="kfPeriodShow" width="120" mergeCells="true"></t:dgCol>--%>
      <t:dgCol title="保质期" field="kfPeriodCon" colspan="true" queryMode="single" width="120" mergeCells="true" ></t:dgCol>
      <%--<t:dgCol title="保质期类型" field="kfDateType" dictionary="SC_DURA_DATE_TYPE" queryMode="single" width="120" mergeCells="true" ></t:dgCol>--%>
      <t:dgCol title="默认仓库" field="stockID" dictionary="T_SC_Stock,id,name" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="主供应商" field="supplierID" dictionary="T_SC_SUPPLIER,id,name" queryMode="single" query="true"
               width="160" mergeCells="true"></t:dgCol>
      <t:dgCol title="产家" field="factory" queryMode="single" width="160" mergeCells="true" query="true"></t:dgCol>
      <t:dgCol title="产地" field="producingPlace" queryMode="single" width="160" mergeCells="true"></t:dgCol>
      <t:dgCol title="指导价" field="costPrice" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="进项税" field="taxRateIn" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="销项税" field="taxRateOut" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="存货类型" field="itemType" dictionary="SC_STOCK_TYPE" queryMode="single" width="120"  mergeCells="true"></t:dgCol>
      <t:dgCol title="计价方法" field="track" queryMode="single" dictionary="SC_PRICE_METHOD"  mergeCells="true" width="120"></t:dgCol>
      <t:dgCol title="按保质期管理" field="iskfPeriod" dictionary="sf_yn" queryMode="single" width="120" mergeCells="true" query="true"></t:dgCol>
      <t:dgCol title="启用批次管理" field="batchManager" dictionary="sf_yn" queryMode="single" width="120" mergeCells="true" query="true"></t:dgCol>
      <t:dgCol title="可组装/拆卸" field="isAssembly" dictionary="sf_yn" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="助记码" field="shortNumber" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="批准文号" field="cultureNo" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="品牌" field="brandID" dictionary="SC_BRAND_TYPE" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="备注" field="note" queryMode="single" width="120" mergeCells="true"></t:dgCol>
      <t:dgCol title="条形码" field="barCode" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="单位" field="unitID" dictionary="T_SC_MeasureUnit,id,name" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="零售价" field="lsRetailPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="会员价" field="lsMemberPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="默认采购单位" field="defaultCG" replace="是_1" queryMode="single" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="默认销售单位" field="defaultXS" replace="是_1" queryMode="single" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="默认仓存单位" field="defaultCK" replace="是_1" queryMode="single" hidden="true" width="120"></t:dgCol>
      <%--<t:dgCol title="默认生产单位" field="defaultSC" hidden="true" replace="是_1" queryMode="single"  width="120"></t:dgCol>--%>
      <t:dgCol title="采购单价" field="cgPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="一级采购价" field="cgPrice1" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="二级采购价" field="cgPrice2" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="三级采购价" field="cgPrice3" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="采购限价" field="cgLimitPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="最近采购价" field="cgLatestPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="销售单价" field="xsPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="一级销售价" field="xsPrice1" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="二级销售价" field="xsPrice2" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="三级销售价" field="xsPrice3" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="销售限价" field="xsLimitPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="最近销售价" field="xsLatestPrice" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="禁用标记"  field="disabled"  replace="启用_0,禁用_1"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100" mergeCells="true"></t:dgCol>
      <t:dgCol title="count" field="count"  width="120" hidden="true"></t:dgCol>
      <t:dgDelOpt title="删除" exp="count#eq#0" url="tScIcitemController.do?doDel&id={id}"/>
      <t:dgToolBar title="新增" icon="new-icon-add" url="tScIcitemController.do?goAdd" funname="goAdd"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScIcitemController.do?goUpdate" funname="update"></t:dgToolBar>
      <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tScIcitemController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="new-icon-view" url="tScIcitemController.do?goUpdate" funname="detail"></t:dgToolBar>
      <t:dgToolBar title="复制" icon="new-icon-copy" url="tScIcitemController.do?goUpdate"
                   funname="fcopy" windowType="tab"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
      <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <t:dgToolBar title="打印" icon="new-icon-print"
                   funname="CreateFormPage('商品主表', '#tScIcitemList')"></t:dgToolBar>
      <t:dgToolBar title="禁用" icon="icon-lock" funname="Disabled "   ></t:dgToolBar>
      <t:dgToolBar title="启用" icon="icon-active" funname="Enable "></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScIcitemList.js"></script>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScItemTypeTree.js"></script>
<script type="text/javascript">
  function dbClickView(rowIndex, rowData) {
    url ='tScIcitemController.do?goUpdate'+ '&load=detail&id=' + rowData.id;
    createdetailwindow('查看', url, null, null,"tab");
  }
  $(document).ready(function () {
    //添加datagrid 事件 onDblClickRow
//    $("#tScIcitemList").datagrid({
//
//      onDblClickRow: function (rowIndex, rowData) {
//        url = 'tScIcitemController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//        addOneTabForBiz('查看', url, null);
//
//      },
//      onLoadSuccess: function (data) {
//        if (data.rows.length > 0) {
//          mergeCellsByField("tScIcitemList", "number,name,shortName,model,weight,kfPeriod,kfDateType,stockID,supplierID,factory,producingPlace,costPrice,taxRateIn,taxRateOut,iskfPeriod,batchManager,isAssembly,shortNumber,cultureNo,brandID,note,opt", "id");
//        }
//      }
//
//    });
    //给时间控件加上样式
    $("#tScIcitemListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
    });
    $("#tScIcitemListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
    });
  });

  function mergeCellsByField(tableID, colList, id) {
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
        if ((PerTxt == CurTxt) && (PerId == CurId)) {
          tmpA += 1;
        }
        else {
          var index = i - tmpA;
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
  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tScIcitemController.do?upload', "tScIcitemList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tScIcitemController.do?exportXls", "tScIcitemList");
  }

  //模板下载
  function ExportXlsByT() {
    JeecgExcelExport("tScIcitemController.do?exportXlsByT", "tScIcitemList");
  }

  //禁用
  //getSelected获取你所选行，返回你选中的行的数据，
  //取它的id，用ajax请求把id传到后台，然后差出这条数据，设置它禁用，更新保存那条数据
  function Disabled(){

    var rowsData = $('#tScIcitemList').datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
      tip('请选择禁用项目');
      return;
    }
    if (rowsData.length>1) {
      tip('请选择一条记录再禁用');
      return;
    }
    if (rowsData[0].disabled == "1") {
      tip('这条记录已禁用，请选择启用项目');
      return;
    }
    var  option=$("#tScIcitemList").datagrid('getSelected');
    $.dialog.confirm('你确定要禁用吗?',function(r) {
      if (r) {

        $.ajax({
          url: "tScIcitemController.do?doDisable&id=" + option.id,
          type: "post",
          success: function (data) {

            var result = data;
            if (result.success == true) {
              $("#tScIcitemList").datagrid('reload');
              $.messager.show({
                title: '提示消息',
                msg: '禁用成功！',
                timeout: 2000,
                showType: 'slide'
              });

            } else {
              $.messager.show({
                title: '提示消息',
                msg: '禁用失败！',
                timeout: 2000,
                showType: 'slide'
              });
            }
          }
        });
      }
      return option;
    });
  }
  //启用
  function Enable() {

    var rowsData = $('#tScIcitemList').datagrid('getSelections');
    if (!rowsData || rowsData.length == 0) {
      tip('请选择启用项目');
      return;
    }
    if (rowsData.length > 1) {
      tip('请选择一条记录再启用');
      return;
    }
    if (rowsData[0].disabled == "0") {
      tip('这条记录已启用，请选择禁用项目');
      return;
    }


    var option = $("#tScIcitemList").datagrid('getSelected');
    $.dialog.confirm('你确定要启用吗?',function(r){
      if(r){

        $.ajax({
          url: "tScIcitemController.do?doEnable&id=" + option.id,
          type: "post",
          success: function (data) {

            var result = data;
            if (result.success == true) {
              $("#tScIcitemList").datagrid('reload');

              $.messager.show({
                title: '提示消息',
                msg: '启用成功！',
                timeout: 2000,
                showType: 'slide'
              });

            } else {
              $.messager.show({
                title: '提示消息',
                msg: '启用失败！',
                timeout: 2000,
                showType: 'slide'
              });
            }
          }
        });
      }
      return option;
    });
  }
</script>