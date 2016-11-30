<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>银行存取款</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>

    <style >
        /**20160629添加页脚样式 **/
        body{
            position: absolute;
            width: 100%;
            height: 100%;
        }
        .footlabel{
            float: left;
            margin-left: 15px;
        }

        .footspan{
            float: left;
            margin-left: 5px;
            margin-right: 5px;
            font-weight: bold;
            color: grey;
            margin-bottom: 5px;
        }
    </style>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" title="银行存取款" beforeSubmit="checkAmount();"
             action="tScRpBankbillController.do?doAdd" tiptype="1" saveTemp="true" tranType="${tranType }" >
      <input id="id" name="id" type="hidden" value="${tScRpBankbillPage.id }">
      <input id="createName" name="createName" type="hidden" value="${tScRpBankbillPage.createName }">
      <input id="createBy" name="createBy" type="hidden" value="${tScRpBankbillPage.createBy }">
      <input id="createDate" name="createDate" type="hidden" value="${tScRpBankbillPage.createDate }">
      <input id="updateName" name="updateName" type="hidden" value="${tScRpBankbillPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tScRpBankbillPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tScRpBankbillPage.updateDate }">
      <input id="version" name="version" type="hidden" value="${tScRpBankbillPage.version }">
      <input id="tranType" name="tranType" type="hidden" value="${tranType }">
      <input id="billNo" name="billNo" type="hidden" value="${billNo }">
      <input id="date" name="date" type="hidden" value="${date}">
  <table  cellpadding="0" cellspacing="1" class="formtable">
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      经办人:
                  </label>
              </td>
              <td class="value">
                  <input id="empId" name="empId" type="hidden" style="width: 150px" class="inputxt" datatype="*">
                  <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*" >
      <span class="Validform_checktip">*
      </span>
                  <label class="Validform_label" style="display: none;">经办人</label>
              </td>
    <td align="right">
      <label class="Validform_label">
      部门:
      </label>
    </td>
    <td class="value">
          <input id="deptId" name="deptId" type="hidden" style="width: 150px" class="inputxt" datatype="*">
          <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*">
      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">部门</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      转出机构:
      </label>
    </td>
    <td class="value">
        <input id="scsonId" value="${sonId}" class="easyui-combotree" data-options="url:'departController.do?loadDepartTree'" onkeypress="EnterPress(event)" onkeydown="EnterPress()"  type="text" name="scsonId"   style="width: 196px;height: 20px;" />

      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">转出机构</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      转出账户:
      </label>
    </td>
    <td class="value">
        <select name="paccountId" datatype="*">
            <option value="">----请选择---</option>
            <c:forEach items="${listSet}" var="set">
                <option value="${set.id}">${set.name}</option>
            </c:forEach>
        </select>
      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">转出账户</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      转入机构:
      </label>
    </td>
    <td class="value">
        <input id="dcsonId" value="${sonId}" class="easyui-combotree" data-options="url:'departController.do?loadDepartTree'" onkeypress="EnterPress(event)" onkeydown="EnterPress()"  type="text" name="dcsonId"   style="width: 196px;height: 20px;" />

      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">转入机构</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      转入账户:
      </label>
    </td>
    <td class="value">
        <select name="raccountId" datatype="*">
            <option value="">----请选择---</option>
            <c:forEach items="${listSet}" var="set">
                <option value="${set.id}">${set.name}</option>
            </c:forEach>
        </select>
      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">转入账户</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      金额:
      </label>
    </td>
    <td class="value">
          <input id="allAmount" name="allAmount" type="text" datatype="d"  style="width: 150px" class="inputxt" errormsg="请填写正确的金额"

              >
      <span class="Validform_checktip">*
      </span>
      <label class="Validform_label" style="display: none;">金额</label>
    </td>
              <td align="right">
                  <label class="Validform_label">
                      分支机构:
                  </label>
              </td>
              <td class="value">
                  <input id="sonId" name="sonId" value="${sonId}" type="hidden" style="width: 150px" class="inputxt" readonly="readonly">
                  <input id="sonName" name="sonName" value="${sonName}" type="text" style="width: 150px" class="inputxt"
                         readonly="readonly">
      <span class="Validform_checktip">*
      </span>
                  <label class="Validform_label" style="display: none;">分支机构</label>
              </td>
    </td>
          </tr>

      <tr>
          <td align="right">
              <label class="Validform_label">
                  摘要:
              </label>
          </td>
          <td class="value" colspan="4">
             <input name="explanation" name="name"   datatype="*0-255" style="width: 600px;" />
              <span class="Validform_checktip">
              </span>
              <label class="Validform_label" style="display: none;">摘要</label>
          </td>
      </tr>
  </table>
</t:formvalid>
<!-- 页脚显示 -->
<div style="width:100%;position: absolute;bottom: 10px;" id="footdiv">
    <div style="width: 33%;display:inline;float: left" align="left">
        <label class="Validform_label footlabel">制单人:${billerName} </label>
        <span class="inputxt footspan"></span>
    </div>
    <div style="width: 33%;display: inline;float: left" align="left">
        <label class="Validform_label footlabel">审核人: </label>
        <span class="inputxt footspan"> </span>
    </div>
    <div style="width: 33%;display: inline;float: right" align="left">
        <label class="Validform_label footlabel">审核日期: </label>
        <span class="inputxt footspan"> </span>
    </div>
</div>
</body>
<script src="webpage/com/qihang/buss/sc/financing/tScRpBankbill.js"></script>
<style type="text/css">
    .spanBtn {
        background-color: #CCE1F3;
        display: -moz-inline-box;
        display: inline-block;
        width: 20px;
        height: 20px;
        text-align: center;
    }
</style>