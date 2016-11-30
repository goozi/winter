<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>部门</title>
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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tScDepartmentController.do?doAdd" tiptype="1" beforeSubmit="checkValue()">
       <%--<input id="">--%>
      <input id="id" name="id" type="hidden"
             value="${tScDepartmentPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScDepartmentPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScDepartmentPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScDepartmentPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScDepartmentPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScDepartmentPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScDepartmentPage.updateDate }">
      <input id="parentID" name="parentID" type="hidden"
             value="${parentID}">
      <input id="disabled" name="disabled" type="hidden"
             value="${tScDepartmentPage.disabled }">
      <input id="level" name="level" type="hidden"
             value="${tScDepartmentPage.level }">
      <input id="count" name="count" type="hidden"
             value="${tScDepartmentPage.count }">
    <input id="manager" name="manager" type="hidden"
           value="${tScDepartmentPage.count }">
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
                      datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点" value="${number}"
                      >
               <span class="Validform_checktip">
               *
               </span>
              <label class="Validform_label" style="display: none;">编号</label>
          </td>
        <td align="right" style="width: 9%">
          <label class="Validform_label">
            助记码:
          </label>
        </td>
        <td class="value" style="width: 24%">
          <input id="shortNumber" name="shortNumber" type="text" style="width: 150px" class="inputxt"
                 validType="t_sc_department,shortNumber,id">
      <span class="Validform_checktip">
      </span>
          <label class="Validform_label" style="display: none;">助记码</label>
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
                 datatype="*1-50" onchange="doName()">
            <span class="Validform_checktip">
            *
             </span>
          <label class="Validform_label" style="display: none;">名称</label>
        </td>
        <td align="right" style="width: 9%">
              <label class="Validform_label">
                  简称:
              </label>
          </td>
        <td class="value" style="width: 24%">
              <input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"
                     datatype="*" readonly="readonly"
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
      电话:
      </label>
    </td>
        <td class="value" style="width: 24%">
          <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">电话</label>
    </td>
        <td align="right" style="width: 9%">
      <label class="Validform_label">
      传真:
      </label>
    </td>
        <td class="value" style="width: 24%">
          <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
                 datatype="f" ignore="ignore"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">传真</label>
    </td>
      </tr>
      <tr>
        <td align="right" style="width: 9%">
      <label class="Validform_label">
      启用信控:
      </label>
    </td>
        <td class="value" style="width: 24%">
          <t:dictSelect field="isCreditMgr" type="list"
                        typeGroupCode="sf_01" width="150px;"
                        defaultVal="0"
                        hasLabel="false"
                        showDefaultOption="false"
                        title="启用信控"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">启用信控</label>
    </td>
        <td align="right" style="width: 9%">
      <label class="Validform_label">
      信用额度:
      </label>
    </td>
        <td class="value" style="width: 24%">
          <input id="creditAmount" name="creditAmount" type="text" style="width: 150px" class="inputxt"
                 datatype="number" ignore="ignore"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">信用额度</label>
    </td>
      </tr>
      <tr>
        <td align="right" style="width: 9%">
          <label class="Validform_label">
            部门主管:
          </label>
        </td>
        <td class="value" style="width: 24%">
          <input id="managerName" name="managerName" type="text" style="width:150px"
                 class="inputxt popup-select" datatype="*" ignore="ignore">
      <span class="Validform_checktip">
      </span>
          <label class="Validform_label" style="display: none;">部门主管</label>
        </td>
        <td align="right" style="width: 9%">
      <label class="Validform_label">
      </label>
    </td>
        <td class="value" style="width: 24%">
    </td>
          </tr>
    <tr>
      <td align="right" style="width: 9%">
      <label class="Validform_label">
        备注:
      </label>
    </td>
    <td class="value" style="width: 24%" colspan="5">
      <textarea style="width: 780px;" class="inputxt" rows="6" id="note" name="note"
                datatype="*1-255" ignore="ignore"></textarea>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">备注</label>
    </td>
    </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScDepartment.js"></script>