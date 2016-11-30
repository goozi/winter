<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>客户</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        function doName() {
            var getName = $("#name").val()
            var pinyin = getPinYin(getName);
            $("#shortname").val(pinyin);
        }
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" beforeSubmit="checkName()"
             action="tScOrganizationController.do?doAdd" tiptype="1">
    <input id="id" name="id" type="hidden"
           value="${tScOrganizationPage.id }">
    <input id="createName" name="createName" type="hidden"
           value="${tScOrganizationPage.createName }">
    <input id="createBy" name="createBy" type="hidden"
           value="${tScOrganizationPage.createBy }">
    <input id="createDate" name="createDate" type="hidden"
           value="${tScOrganizationPage.createDate }">
    <input id="updateName" name="updateName" type="hidden"
           value="${tScOrganizationPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden"
           value="${tScOrganizationPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden"
           value="${tScOrganizationPage.updateDate }">
    <input id="parentid" name="parentid" type="hidden"
           value="${parentid }">
    <input id="level" name="level" type="hidden"
           value="${tScOrganizationPage.level }">
    <input id="count" name="count" type="hidden"
           value="0">
    <input id="deleted" name="deleted" type="hidden"
           value="0">
    <input id="version" name="version" type="hidden"
           value="${tScOrganizationPage.version }">
    <input id="dealerId" name="dealerId" type="hidden" >
    <input id="tranType" name="tranType" type="hidden"
           value="${tranType}">
    <input id="old_name" name="old_name" type="hidden" value=''>
    <input id="load" value="${load}" type="hidden">
    <table cellpadding="0" cellspacing="1" width="100%" class="formtable">
        <tr>
            <td align="right"  style="width: 9%">
                <label class="Validform_label">
                    编号:
                </label>
            </td>
            <td class="value" style="width: 24%">
                <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
                       value="${number}" datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点"
                        >
      <span class="Validform_checktip">*
      </span>
                <label class="Validform_label" style="display: none;">编号</label>
            </td>
            <td align="right" style="width: 9%">
                <label class="Validform_label">
                    名称:
                </label>
            </td>
            <td class="value" style="width: 24%">
                <input id="name" name="name" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-50" onchange="doName()"
                        >
      <span class="Validform_checktip">*
      </span>
                <label class="Validform_label" style="display: none;">名称</label>
            </td>
            <td align="right" style="width: 9%">
                <label class="Validform_label">
                    简称:
                </label>
            </td>
            <td class="value" style="width: 24%">
                <input id="shortname" name="shortname" type="text" style="width: 150px" class="inputxt"
                       readonly="readonly"
                        >
      <span class="Validform_checktip">
      </span>
                <label class="Validform_label" style="display: none;">简称</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    助记码:
                </label>
            </td>
            <td class="value">
                <input id="shortnumber" name="shortnumber" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-30" ignore="ignore" validType="t_sc_organization,shortnumber,id"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">助记码</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    法人代表:
                </label>
            </td>
            <td class="value">
                <input id="corperate" name="corperate" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-30" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">法人代表</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    联系人:
                </label>
            </td>
            <td class="value">
                <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-20" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">联系人</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    手机号码:
                </label>
            </td>
            <td class="value">
                <input id="mobilephone" name="mobilephone" type="text" style="width: 150px" class="inputxt"
                       datatype="m" ignore="ignore"
                        >
           <span class="Validform_checktip">
           </span>
                <label class="Validform_label" style="display: none;">手机号码</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    电话:
                </label>
            </td>
            <td class="value">
                <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                       datatype="po" ignore="ignore"
                        >
           <span class="Validform_checktip">
           </span>
                <label class="Validform_label" style="display: none;">电话</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    传真:
                </label>
            </td>
            <td class="value">
                <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
                       datatype="f" ignore="ignore"
                        >
           <span class="Validform_checktip">
           </span>
                <label class="Validform_label" style="display: none;">传真</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    联系地址:
                </label>
            </td>
            <td class="value" colspan="3">
                <input id="address" name="address" type="text" style="width: 575px" class="inputxt"
                       datatype="*1-100" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">联系地址</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    区域:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="regionid" type="list"
                              typeGroupCode="SC_REGION"
                              defaultVal="${tScOrganizationPage.regionid}" hasLabel="false"
                              title="区域"></t:dictSelect>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">区域</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">是否经销商</label>
            </td>
            <td class="value">
                <t:dictSelect field="isDealer" type="list" id="isDealer"
                              typeGroupCode="sf_01" extendJson="{'datatype':'*','onChange':'isdealer(value)'}"
                              defaultVal="0" hasLabel="false" showDefaultOption="false"
                              title="是否经销商"></t:dictSelect>
            <span class="Validform_checktip">*
          </span>
                <label class="Validform_label" style="display: none;">是否经销商</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    上级经销商:
                </label>
            </td>
            <td class="value">
                <input id="dealerName" name="dealerName" type="text" style="width: 150px" class="inputxt popup-select"
                       readonly="readonly" datatype="*" disabled="disabled"
                        >
                <input id="dealer" name="dealer" type="hidden"

                        >

          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">上级经销商</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    客户分类:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="typeid" type="list" extendJson="{'disabled':'disabled','datatype':'*','onChange':'clientSort(value)'}"
                              typeGroupCode="SC_TYPE_ID"
                              defaultVal="${tScOrganizationPage.typeid}" hasLabel="false"
                              title="客户分类"></t:dictSelect>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">客户分类</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    默认业务员:
                </label>
            </td>
            <td class="value">
                <input id="defaultoperatorName" name="defaultoperatorName" type="text" style="width: 150px"
                       class="inputxt popup-select">
                <input id="defaultoperator" name="defaultoperator" type="hidden">
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">默认业务员</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    营业执照号:
                </label>
            </td>
            <td class="value">
                <input id="licence" name="licence" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-80" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">营业执照号</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    城市:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="city" type="list"
                              typeGroupCode="SC_CITY"
                              defaultVal="${tScOrganizationPage.city}" hasLabel="false"
                              title="城市"></t:dictSelect>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">城市</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    邮政编码:
                </label>
            </td>
            <td class="value">
                <input id="postalcode" name="postalcode" type="text" style="width: 150px" class="inputxt"
                       datatype="n1-20" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">邮政编码</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    电子邮件:
                </label>
            </td>
            <td class="value">
                <input id="email" name="email" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-40" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">电子邮件</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    公司主页:
                </label>
            </td>
            <td class="value">
                <input id="homepage" name="homepage" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-80" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">公司主页</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    开户银行:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="bank" type="list"
                              typeGroupCode="SC_BANK"
                              defaultVal="${tScOrganizationPage.bank}" hasLabel="false"
                              title="开户银行"></t:dictSelect>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">开户银行</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    银行账号:
                </label>
            </td>
            <td class="value">
                <input id="banknumber" name="banknumber" type="text" style="width: 150px" class="inputxt"
                       datatype="n1-40" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">银行账号</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    qq号:
                </label>
            </td>
            <td class="value">
                <input id="ciqnumber" name="ciqnumber" type="text" style="width: 150px" class="inputxt"
                       datatype="n1-20" ignore="ignore"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">qq号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    行业:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="trade" type="list"
                              typeGroupCode="SC_TRADE"
                              defaultVal="${tScOrganizationPage.trade}" hasLabel="false"
                              title="行业"></t:dictSelect>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">行业</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    送货方式:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="delivertype" type="list"
                              typeGroupCode="SC_DELIVER_TYPE"
                              defaultVal="${tScOrganizationPage.delivertype}" hasLabel="false"
                              title="送货方式"></t:dictSelect>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">送货方式</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    结算单位:
                </label>
            </td>
            <td class="value">
                <input id="settlecompanyName" name="settlecompanyName" type="text" style="width: 150px" class="inputxt popup-select"
                         >
                <input id="settlecompany" name="settlecompany" type="hidden"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">结算单位</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    启用信控:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="iscreditmgr" type="list" showDefaultOption="false"
                              typeGroupCode="sf_01" extendJson="{'datatype':'*','onChange':'iscreditmgrf(value)'}"
                              defaultVal="0" hasLabel="false"
                              title="启用信控"></t:dictSelect>
          <span class="Validform_checktip">*
          </span>
                <label class="Validform_label" style="display: none;">启用信控</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    信用额度:
                </label>
            </td>
            <td class="value">
                <input id="creditamount" name="creditamount" type="text" style="width: 150px" class="inputxt"
                       datatype="vInt" ignore="ignore" onblur="checkcreditamount()"
                        >
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">信用额度</label>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    备注:
                </label>
            </td>
            <td class="value" colspan="5">
              <textarea style="width:1000px;" class="inputxt" rows="6" id="note"
                        name="note" datatype="*1-100" ignore="ignore"></textarea>
          <span class="Validform_checktip">
          </span>
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScOrganization.js"></script>