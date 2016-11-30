<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">

  <div region="center" style="padding:1px;">
    <t:datagrid name="tScEmpList" checkbox="false" fitColumns="false" title="职员" isSon="false" colConfig="false"
                actionUrl="tScEmpController.do?datagrid&deleted=0&disabled=0" idField="id" fit="true" checkOnSelect="true" onLoadSuccess="loadsuccess"
                queryMode="group" onDblClick="dbButtonOk" >
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
      <t:dgCol title="助记码" field="shortNumber"   query="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="简称" field="shortName"   query="true" queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="部门id" field="deptID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="部门" field="deptIdName"  hidden="true"   queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="职员类别" field="empGroup" hidden="true"   queryMode="single"  dictionary="SC_EMPLOYEE_TYPE" width="120"></t:dgCol>
      <t:dgCol title="性别" field="gender" hidden="true"   queryMode="single"  dictionary="SC_GENDER" width="120"></t:dgCol>
      <t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="电子邮件" field="email"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="QQ号" field="ciqNumber"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="联系地址" field="address"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="联系电话" field="phone"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="手机号码" field="mobilePhone"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="身份证号码" field="identify" hidden="true"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="身份证地址" field="idAddress"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="职务" field="duty" hidden="true"   queryMode="single"  dictionary="SC_DUTY_TYPE" width="120"></t:dgCol>
      <t:dgCol title="文化程度" field="degree" hidden="true"   queryMode="single"  dictionary="SC_CULTURE_TYPE" width="120"></t:dgCol>
      <t:dgCol title="毕业院校" field="school"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="学习专业" field="expertise"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="入职日期" field="hireDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="离职日期" field="leaveDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="开户银行" field="bank" hidden="true"   queryMode="single"  dictionary="SC_BANK" width="120"></t:dgCol>
      <t:dgCol title="银行账号" field="bankNumber" hidden="true"   queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="启用信控" field="isCreditMgr" hidden="true"   queryMode="single"  dictionary="sf_01" width="120"></t:dgCol>
      <t:dgCol title="信用额度" field="creditAmount"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="禁用标记" field="disabled"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
      <t:dgCol title="备注" field="note"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
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




    //给时间控件加上样式
    $("#tScOrganizationListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
    });
    $("#tScOrganizationListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
      WdatePicker({dateFmt: 'yyyy-MM-dd'});
    });
  });

  var popJon;
  var orgId;
  var isPop = false;
  function getSelectionData() {
    var rows = $('#tScEmpList').datagrid('getSelections');
    if (rows.length > 0) {
      isPop =true ;
      return rows[0];

    } else {
      parent.$.messager.show({
        title: '提示消息',
        msg: '请选择经办人',
        timeout: 2000,
        showType: 'slide'
      });
      return '';
    }
  }
</script>