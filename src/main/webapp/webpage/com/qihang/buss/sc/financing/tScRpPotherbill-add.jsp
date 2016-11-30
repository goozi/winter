<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>费用开支单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    $(document).ready(function () {
      $('#tt').tabs({
        onSelect: function (title) {
          $('#tt .panel-body').css('width', 'auto');
        }
      });
      $(".tabs-wrap").css('width', '100%');
    });
  </script>

  <style >
    /** 页脚样式  **/
    body{
      position: absolute;
      width: 100%;
      height: 100%;
    }
    .footlabel{
      float: left;
      margin-left: 35px;
    }

    .footspan{
      float: left;
      margin-left: 5px;
      margin-right: 5px;
      font-weight: bold;
      color: grey;
      margin-bottom: 5px;
      text-decoration: underline;
    }
    </style>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" title="费用开支单" dialog="true" usePlugin="password" layout="table" tiptype="1"
             action="tScRpPotherbillController.do?doAdd" beforeSubmit="checkPartten()" saveTemp="true">
      <input id="id" name="id" type="hidden" value="${tScRpPotherbillPage.id }">
      <input id="createName" name="createName" type="hidden" value="${tScRpPotherbillPage.createName }">
      <input id="createBy" name="createBy" type="hidden" value="${tScRpPotherbillPage.createBy }">
      <input id="createDate" name="createDate" type="hidden" value="${tScRpPotherbillPage.createDate }">
      <input id="updateName" name="updateName" type="hidden" value="${tScRpPotherbillPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tScRpPotherbillPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tScRpPotherbillPage.updateDate }">
      <input id="tranType" name="tranType" type="hidden" value="${tranType}">
      <input id="checkState" name="checkState" type="hidden" value="0">
      <input id="cancellation" name="cancellation" type="hidden" value="0">
      <input id="billerId" name="billerId" type="hidden" value="${billerId}">
      <input id="sonId" name="sonId" type="hidden" value="${sonId}">
      <input id="billNo" name="billNo" value="${billNo}" type="hidden"/>
      <input id="date" name="date" value="${date}" type="hidden"/>

  <table cellpadding="0" cellspacing="1" class="formtable"  style="position: absolute;z-index: 50;width: 100% ">
      <tr>
          <td align="right">
              <label class="Validform_label">核算类别:</label>
          </td>
          <td class="value">
              <select name="itemClassId" id="itemClassId">
                  <option value="1" selected="selected">职员</option>
                  <option value="2">部门</option>
              </select>
      <span class="Validform_checktip">
                </span>
              <label class="Validform_label" style="display: none;">核算类别</label>
          </td>
          <td align="right">
              <label class="Validform_label">核算项目:</label>
          </td>
          <td class="value">
              <input id="itemId" name="itemId" type="hidden" style="width: 150px" class="inputxt" datatype="*">
              <input id="itemName" name="itemName"  type="text" style="width: 150px;" class="inputxt popup-select" onkeypress="pressItemName();" datatype="*">
      <span class="Validform_checktip">*
                </span>
              <label class="Validform_label" style="display: none;">核算项目</label>
          </td>
      </tr>

      <tr>
          <td align="right">
              <label class="Validform_label">经办人:</label>
          </td>
          <td class="value">
              <input id="empId" name="empId" type="hidden" style="width: 150px" class="inputxt"
                     datatype="*">
              <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select"
                     datatype="*"  >
      <span class="Validform_checktip">*
                </span>
              <label class="Validform_label" style="display: none;">经办人</label>
          </td>
          <td align="right">
              <label class="Validform_label">部门:</label>
          </td>
          <td class="value">
              <input id="deptId" name="deptId" type="hidden" style="width: 150px" class="inputxt"
                     datatype="*"  >
              <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select"
                     datatype="*">
      <span class="Validform_checktip">*
                </span>
              <label class="Validform_label" style="display: none;">部门</label>
          </td>
      </tr>
      <tr>

          <td align="right">
              <label class="Validform_label">结算账户:</label>
          </td>
          <td class="value">
              <select name="accountId" datatype="*">
                  <option value="">----请选择---</option>
                  <c:forEach items="${listSet}" var="set">
                      <option value="${set.id}">${set.name}</option>
                  </c:forEach>
              </select>
      <span class="Validform_checktip">*
                </span>
              <label class="Validform_label" style="display: none;">结算账户</label>
          </td>

          <td align="right">
              <label class="Validform_label">总金额:</label>
          </td>
          <td class="value">
              <input id="allAmount" name="allAmount" type="text" style="width: 150px" class="inputxt" readonly="readonly"
                     value="0.00" datatype="d">
      <span class="Validform_checktip">*
                </span>
              <label class="Validform_label" style="display: none;">总金额</label>
              <span class="spanbtn-expand" id="btnExpand"></span>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">源单类型:</label>
          </td>
          <td class="value">
              <input id="classIdSrc" name="classIdSrc" type="hidden" style="width: 150px" class="inputxt"  readonly="readonly"
                     value="" >
              <input id="classIdSrcName" name="classIdSrcName" type="text" style="width: 150px" class="inputxt"  readonly="readonly"
                     value=""  message="aaa">
      <span class="Validform_checktip">
                </span>
              <label class="Validform_label" style="display: none;"></label>
          </td>
          <td align="right">
              <label class="Validform_label">分支机构:</label>
          </td>
          <td class="value">
              <input id="sonName" name="sonName" type="text" style="width: 150px" class="inputxt" value="${sonName}"
                     readonly="readonly" >
      <span class="Validform_checktip">*
                </span>
              <label class="Validform_label" style="display: none;">分支机构</label>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">摘要:</label>
          </td>
          <td class="value" colspan="3">
              <input name="explanation" style="width:600px;"  datatype="*0-255" >

      <span class="Validform_checktip">
                </span>
              <label class="Validform_label" style="display: none;">摘要</label>
          </td>

      </tr>
  </table>
  <div style="width: auto;height: 200px;margin-top: 100px;"">
    <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
    <div style="width:800px;height:1px;"></div>
    <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
      <t:tab href="tScRpPotherbillController.do?tScRpPotherbillentryList&id=${tScRpPotherbillPage.id}"
             icon="icon-search" title="费用开支单明细" id="tScRpPotherbillentry"></t:tab>
    </t:tabs>
  </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
  <tbody id="add_tScRpPotherbillentry_table_template">
  <tr>
      <input name="tScRpPotherbillentryList[#index#].id" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].createName" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].createBy" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].createDate" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].updateName" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].updateBy" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].updateDate" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].fid" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].findex" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].expId" type="hidden" datatype="*"/>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addTScRpPotherbillentryBtn[#index#]" id="addTScRpPotherbillentryBtn[#index#]" href="#" onclick="clickAddTScRpPotherbillentryBtn('#index#');"></a>
        <a name="delTScRpPotherbillentryBtn[#index#]" id="delTScRpPotherbillentryBtn[#index#]" href="#"  onclick="clickDelTScRpPotherbillentryBtn('#index#',this);"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpPotherbillentryList[#index#].expName" maxlength="32"
                          datatype="*"
                         onkeypress="keyDownInfo('#index#',event)"
                         onblur="orderListStockBlur('#index#','expId','expName');"
                         type="text" class="inputxt popup-select" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">收支项目</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpPotherbillentryList[#index#].billNoSrc" maxlength="32" readonly="readonly" value=""
                         type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);"/>
              <label class="Validform_label" style="display: none;">源单编号</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpPotherbillentryList[#index#].haveAmountSrc" maxlength="32" readonly="readonly" value="0"
                         type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);" datatype="float"/>
              <label class="Validform_label" style="display: none;">源单已报销金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpPotherbillentryList[#index#].notAmountSrc" maxlength="32" readonly="readonly" value="0"
                         type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);" datatype="float"/>
              <label class="Validform_label" style="display: none;">源单未报销金额</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpPotherbillentryList[#index#].amount" maxlength="32"
                         datatype="d" onchange="setAllAmount(this);"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">本次支出</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tScRpPotherbillentryList[#index#].note" maxlength="255"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">备注</label>
            </td>
      <input name="tScRpPotherbillentryList[#index#].classIdSrc" type="hidden" />
      <input name="tScRpPotherbillentryList[#index#].interIdSrc" type="hidden"/>
      <input name="tScRpPotherbillentryList[#index#].fidSrc"  type="hidden"/>

  </tr>
  </tbody>
</table>
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
<script src="webpage/com/qihang/buss/sc/financing/tScRpPotherbill.js"></script>