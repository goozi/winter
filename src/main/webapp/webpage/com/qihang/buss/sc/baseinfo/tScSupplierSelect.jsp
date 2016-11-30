<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">


  <div region="center" style="padding:1px;">
    <t:datagrid name="tScSupplierList" checkbox="false" fitColumns="false" title="供应商" colConfig="false"
                actionUrl="tScSupplierController.do?datagrid&disabled=0" idField="id" checkOnSelect="true" fit="true" onLoadSuccess="loadsuccess" isSon="false"
                queryMode="group" onDblClick="dbButtonOk">
      <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="简称" field="shortName"   query="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="助记码" field="shortNumber"   query="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="区域" field="regionID"  hidden="true"  queryMode="single"  dictionary="SC_REGION" width="120"></t:dgCol>
      <t:dgCol title="联系人" field="contact"    queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="手机号码" field="mobilePhone"    queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="电话" field="phone"    queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="传真" field="fax" hidden="true"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="联系地址" field="address"    queryMode="single"   width="200"></t:dgCol>
      <t:dgCol title="QQ号" field="cIQNumber"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="城市" field="city"  hidden="true"  queryMode="single"  dictionary="SC_CITY" width="120"></t:dgCol>
      <t:dgCol title="邮政编码" field="postalCode"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="电子邮件" field="email"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="公司主页" field="homePage"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="开户银行" field="bank"  hidden="true"   queryMode="single"  dictionary="SC_BANK" width="120"></t:dgCol>
      <t:dgCol title="银行账号" field="bankNumber"  hidden="true"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="营业执照号" field="licence"   hidden="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="行业" field="trade" hidden="true"   queryMode="single"  dictionary="SC_TRADE" width="120"></t:dgCol>
      <t:dgCol title="法人代表" field="corperate"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="供应商分类" field="typeID"  hidden="true"  queryMode="single"  dictionary="SC_SUPPLIER_TYPE" width="120"></t:dgCol>
      <t:dgCol title="结算单位" field="settleCompany"   hidden="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="储备天数" field="stockDay"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="在途天数" field="byWayDay"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="禁用标记" field="disabled"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="备注" field="note"  hidden="true"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="数据类型" field="billType" hidden="true" query="${isBillType}" replace="供应商_0,物流公司_1"   queryMode="single"   width="120"></t:dgCol>
      <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
      <%--<t:dgDelOpt title="删除" url="tScSupplierController.do?doDel&id={id}"/>--%>
      <%--<t:dgToolBar title="录入" icon="icon-add" url="tScSupplierController.do?goAdd"--%>
                   <%--funname="add" onclick="goAdd()"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tScSupplierController.do?goUpdate"--%>
                   <%--funname="update"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScSupplierController.do?doBatchDel"--%>
                   <%--funname="deleteALLSelect"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="查看" icon="icon-search" url="tScSupplierController.do?goUpdate"--%>
                   <%--funname="detail"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="打印" icon="icon-print"--%>
                   <%--funname="CreateFormPage('供应商', '#tScSupplierList')"></t:dgToolBar>--%>
    </t:datagrid>
  </div>
</div>

<script type="text/javascript">
  function dbButtonOk(){
    $(".ui_buttons .ui_state_highlight",parent.document).trigger('click');
  }
  <%--window.onload=function(){--%>
  <%--$("input[name='name']").val(${name});--%>
  <%--$(".icon-query").click();--%>
  <%--}--%>
  var flag = true;
  function loadsuccess(){
    if(flag) {
      $("input[name='name']").val('${name}_');
      $('.icon-query').click();
      $("input[name='name']").val('');
      flag = false;
    }
  }
  $(document).ready(function () {
//        $("#tCrProductList").datagrid({
//            singleSelect:true,
//            method:'get'
//        });
    var selection = $("select[name='billType']")[0].options.remove(0);


    //给时间控件加上样式
    $("#tScSupplierListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
    });
    $("#tScSupplierListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
    });
  });

  var popJon;
  var orgId;
  var isPop = false;
  function getSelectionData() {
    var rows = $('#tScSupplierList').datagrid('getSelections');
    if (rows.length > 0) {
      isPop =true ;
      return rows[0];

    } else {
      parent.$.messager.show({
        title: '提示消息',
        msg: '请选择结算单位',
        timeout: 2000,
        showType: 'slide'
      });
      return '';
    }
  }
</script>