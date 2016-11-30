<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>仓库</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
    function doName() {
        var getName=$("#name").val()
        var pinyin = getPinYin(getName);
        $("#shortName").val(pinyin);
    }
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tBiStockController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tBiStockPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tBiStockPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tBiStockPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tBiStockPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tBiStockPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tBiStockPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tBiStockPage.updateDate }">
      <input id="deleted" name="deleted" type="hidden"
             value="${tBiStockPage.deleted }">
      <input id="level" name="level" type="hidden"
             value="${tBiStockPage.level }">
    <input id="parentID" name="parentID" type="hidden"
            value="${parentID}">
    <input id="empID" name="empID" type="hidden"
            value="${tBiStockPage.empID}">
    <input id="tranType" name="tranType" type="hidden"
           value="${tranType}">
  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
          <td align="right" style="width: 9%">
              <label class="Validform_label">
                  编号:
              </label>
          </td>
          <td class="value" style="width: 24%">
              <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
                     datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点" value="${number}">
      <span class="Validform_checktip">
            *
      </span>
              <label class="Validform_label" style="display: none;">编号</label>
          </td>
          <td align="right"style="width: 9%">
              <label class="Validform_label">
                  简称:
              </label>
          </td>
          <td class="value" style="width: 24%">
              <input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"
                     datatype="*" readonly
              >
              <span class="Validform_checktip">
            *
      </span>
              <label class="Validform_label" style="display: none;">简称</label>
          </td>
      </tr>
      <tr>
    <td align="right" style="width: 9%">
      <label class="Validform_label">
      名称:
      </label>
    </td>
    <td class="value" style="width: 24%">
          <input id="name" name="name" type="text" style="width: 150px" class="inputxt"
                 datatype="*1-50" onchange="doName()"
>
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">名称</label>
    </td>
          <td align="right" style="width: 9%">
              <label class="Validform_label">
                  助记码:
              </label>
          </td>
          <td class="value" style="width: 24%">
              <input id="shortNumber" name="shortNumber" type="text" style="width: 150px" class="inputxt"
                     validType="t_sc_stock,shortNumber,id"
              >
              <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">助记码</label>
          </td>

      </tr>



      <tr>
    <td align="right" style="width: 9%">
      <label class="Validform_label">
      仓管:
      </label>
    </td>
    <td class="value" style="width: 24%">
          <input id="empName" name="empName" type="text" style="width: 150px" class="inputxt popup-select"

              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">仓管</label>
    </td>
          <td align="right" style="width: 9%">
              <label class="Validform_label">
                  仓库类型:
              </label>
          </td>
          <td class="value" style="width: 24%">
              <t:dictSelect field="typeID" type="list" width="150px" extendJson="{datatype:*}"
                            typeGroupCode="SC_REPERTORY_TYPE"
                            defaultVal="1" hasLabel="false"
                            showDefaultOption="false"
                            title="仓库类型"></t:dictSelect>
              <span class="Validform_checktip">
            *
      </span>
              <label class="Validform_label" style="display: none;">仓库类型</label>
          </td>
      </tr>
      <tr>
          <td align="right" style="width: 9%">
              <label class="Validform_label">
                  电话:
              </label>
          </td>
          <td class="value" style="width: 24%">
              <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                     datatype="po" ignore="ignore"
              >
              <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">电话</label>
          </td>
          <td align="right" style="width: 9%">
              <label class="Validform_label">
                  机构:
              </label>
          </td>
          <td class="value" style="width: 24%">
              <input id="sonID" value="${sonId}" class="easyui-combotree" data-options="url:'departController.do?loadDepartTree'" onkeypress="EnterPress(event)" onkeydown="EnterPress()"  type="text" name="sonID"   style="width: 196px;height: 20px;" />
                  <%--  <input type="text" style="width: 150px" class="inputxt" readonly id="sonName" name="sonName"
                       value="${sessionScope.user.currentDepart.departname}">

                <input id="sonID" name="sonID" type="hidden" value="${sessionScope.user.currentDepart.id}">
                --%>
                  <%--<t:dictSelect field="sonID" type="list"
                                typeGroupCode=""
                                defaultVal="${tBiStockPage.sonID}" hasLabel="false"
                                title="机构ID"></t:dictSelect>--%>
              <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">机构</label>
          </td>
      </tr>
      <tr>
          <td align="right" style="width: 9%">
              <label class="Validform_label">
                  仓库地址:
              </label>
          </td>
          <td class="value" style="width: 24%" colspan="3">
              <input id="address" name="address" type="text" style="width: 800px" class="inputxt"
                        maxlength="100"
                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">仓库地址</label>
          </td>
      </tr>


      <tr>
          <td align="right"  style="width: 9%">
              <label class="Validform_label">
                  备注:
              </label>
          </td>
          <td class="value"  style="width: 24%" colspan="3">
          <textarea style="width: 800px;" class="inputxt" rows="4" id="note" maxlength="100"
                    name="note"></textarea>
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">备注</label>
          </td>
      </tr>

  </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/baseinfo/tBiStock.js"></script>