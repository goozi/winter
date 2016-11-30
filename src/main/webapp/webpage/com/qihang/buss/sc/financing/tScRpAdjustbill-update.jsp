<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>资金调账</title>
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
            $(".tabs-wrap").css('display', 'none');
        });
    </script>

    <style>
        /** 页脚样式  **/
        body {
            position: absolute;
            width: 100%;
            height: 100%;
        }

        .footlabel {
            float: left;
            margin-left: 35px;
        }

        .footspan {
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
<t:formvalid formid="formobj" dialog="true" title="资金调账" usePlugin="password" layout="table" tranType="${tScRpAdjustbillPage.tranType }" tiptype="1"
             action="tScRpBankbillFinController.do?doUpdate"  beforeSubmit="checkPartten()" >
    <input id="load" name="load" value="${param.load}" type="hidden">
    <input id="id" name="id" type="hidden" value="${tScRpAdjustbillPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tScRpAdjustbillPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tScRpAdjustbillPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tScRpAdjustbillPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tScRpAdjustbillPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tScRpAdjustbillPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tScRpAdjustbillPage.updateDate }">
    <input id="version" name="version" type="hidden" value="${tScRpAdjustbillPage.version }">
    <input id="tranType" name="tranType" type="hidden" value="${tScRpAdjustbillPage.tranType }">
    <input id="billerId" name="billerId" type="hidden" value="${tScRpAdjustbillPage.billerId}">
    <input id="checkerId" name="checkerId" type="hidden" value="${tScRpAdjustbillPage.checkerId}">
    <input id="checkState" name="checkState" type="hidden" value="${tScRpAdjustbillPage.checkState}">
    <input id="cancellation" name="cancellation" type="hidden" value="${tScRpAdjustbillPage.cancellation}">
    <input id="sonId" name="sonId" type="hidden" value="${tScRpAdjustbillPage.sonId}">
    <input id="itemId" name="itemId" type="hidden" value="${tScRpAdjustbillPage.itemId}">
    <input id="checkAmount" name="checkAmount" type="hidden" value="${tScRpAdjustbillPage.checkAmount}">
    <input id="classIdSrc" name="classIdSrc" type="hidden" value="${tScRpAdjustbillPage.classIdSrc}">
    <input id="idSrc" name="idSrc" type="hidden" value="${tScRpAdjustbillPage.idSrc}">
    <input id="billNoSrc" name="billNoSrc" type="hidden" value="${tScRpAdjustbillPage.billNoSrc}">
    <input id="checkDate" name="checkDate" type="hidden" style="width: 150px"
           class="inputxt" value='<fmt:formatDate value='${tScRpAdjustbillPage.checkDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>'>
    <input name="billNo" type="hidden" value="${tScRpAdjustbillPage.billNo}">
    <input name="date" type="hidden" value="${tScRpAdjustbillPage.date}">
    <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    经办人:
                </label>
            </td>
            <td class="value">
                <input id="empId" name="empId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tScRpAdjustbillPage.empId}">
                <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*" value="${tScRpAdjustbillPage.empName}">
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
                <input id="deptId" name="deptId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tScRpAdjustbillPage.deptId}">
                <input id="deptName" name="deptName" type="text" style="width: 150px" class="inputxt popup-select" datatype="*" value="${tScRpAdjustbillPage.deptName}">
      <span class="Validform_checktip">*
      </span>
                <label class="Validform_label" style="display: none;">部门</label>
            <td align="right">
                <label class="Validform_label">
                    结算账户:
                </label>
            </td>
            <td class="value">
                <select name="accountId">
                    <c:forEach items="${listSet}" var="set">
                        <option value="${set.id}" <c:if test="${set.id == tScRpAdjustbillPage.accountId}">selected="selected"</c:if>  >${set.name}</option>
                    </c:forEach>
                </select>
                <span class="Validform_checktip">*</span>
                <label class="Validform_label" style="display: none;"> 结算账户</label>
            </td>
        </tr>
        <tr>

            <td align="right">
                <label class="Validform_label">
                    分支机构:
                </label>
            </td>
            <td class="value">
                <input id="sonName" name="sonName" value="${tScRpAdjustbillPage.sonName}" type="text" style="width: 150px" class="inputxt"

                       readonly="readonly">

      <span class="Validform_checktip">*
      </span>
                <label class="Validform_label" style="display: none;">分支机构</label>
            </td>
            <td align="right">
                <label class="Validform_label">总金额:</label>
            </td>
            <td class="value" colspan="3">
                <input id="allAmount" name="allAmount" type="text" value="${tScRpAdjustbillPage.allAmount}" style="width: 150px" class="inputxt" readonly="readonly" datatype="d">
                 <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">摘要:</label>
            </td>
            <td class="value" colspan="5">
                <input name="explanation" style="width: 600px;"  maxlength="255" value="${tScRpAdjustbillPage.explanation}"/>
                 <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">摘要</label>
            </td>
        </tr>
    </table>
    <div style="width: auto;height: 200px;">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:800px;height:1px;"></div>
        <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
            <t:tab href="tScRpBankbillFinController.do?tScRpAdjustbillentryFinList&id=${tScRpAdjustbillPage.id}"
                   icon="icon-search" title="应收调账从表" id="tScRpAdjustbillentry"></t:tab>
        </t:tabs>
    </div>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
    <tbody id="add_tScRpAdjustbillentry_table_template">
    <tr>
        <input name="tScRpAdjustbillentryList[#index#].id" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].createName" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].createBy" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].createDate" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].updateName" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].updateBy" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].updateDate" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].fid" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].findex" type="hidden"/>
        <input name="tScRpAdjustbillentryList[#index#].expId" type="hidden" datatype="*"/>
        <td align="center" bgcolor="white">
            <div style="width: 25px;" name="xh"></div>
        </td>
        <td align="center" bgcolor="white">
            <div style="width: 80px;">
                <a name="addTScRpAdjustbillentryBtn[#index#]" id="addTScRpAdjustbillentryBtn[#index#]" href="#"
                   onclick="clickAddTScRpAdjustbillentryBtn('#index#');"></a>
                <a name="delTScRpAdjustbillentryBtn[#index#]" id="delTScRpAdjustbillentryBtn[#index#]" href="#"
                   onclick="clickDelTScRpAdjustbillentryBtn('#index#');"></a>
            </div>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpAdjustbillentryList[#index#].expName" maxlength="32"
                   onkeypress="keyDownInfo('#index#',event)" onblur="orderListStockBlur('#index#','expId','expName');"
                    datatype="*"
                   type="text" class="inputxt popup-select" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">收支项目</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpAdjustbillentryList[#index#].amount" maxlength="10"
                    datatype="float" errormsg="请填写正确的金额"   onchange="setAllAmount(this)"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">金额</label>
        </td>
        <td align="left" bgcolor="white">
            <input name="tScRpAdjustbillentryList[#index#].note" maxlength="255"
                   type="text" class="inputxt" style="width:120px;"/>
            <label class="Validform_label" style="display: none;">备注</label>
        </td>
    </tr>
    </tbody>
</table>
<!-- 页脚显示 -->
<div style="width:100%;position: absolute;bottom: 10px;left:10px;" id="footdiv">
    <div style="width: 33%;display:inline;float: left" align="left">
        <label class="Validform_label footlabel">制单人:${tScRpAdjustbillPage.billerName} </label>
        <span class="inputxt footspan"></span>
    </div>
    <div style="width: 33%;display: inline;float: left" align="left">

        <label class="Validform_label footlabel">审核人:${checkUserName} </label>
        <span class="inputxt footspan"> </span>
    </div>
    <div style="width: 33%;display: inline;float: right" align="left">
        <label class="Validform_label footlabel">审核日期:<fmt:formatDate value='${tScRpAdjustbillPage.checkDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/> </label>
        <span class="inputxt footspan"> </span>
    </div>


</div>
</body>
<script src="webpage/com/qihang/buss/sc/financing/tScRpAdjustbill.js"></script>