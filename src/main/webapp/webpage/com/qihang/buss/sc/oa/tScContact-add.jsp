<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>通讯录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" windowType="dialog"
             action="tScContactController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScContactPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScContactPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScContactPage.createBy }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScContactPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScContactPage.updateBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScContactPage.createDate }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScContactPage.updateDate }">
      <input id="type" name="type" type="hidden"
             value="${type}">
      <input id="userId" name="userId" type="hidden"
             value="${tScContactPage.userId }">
  <table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
    <td align="right">
      <label class="Validform_label">
      姓名:
      </label>
    </td>
    <td class="value">
          <input id="name" name="name" type="text" style="width: 150px" class="inputxt"
                 datatype="*"
              >
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">姓名</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      性别:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="sex" type="radio"
                        typeGroupCode="oa_sex"
                        defaultVal="${tScContactPage.sex}" hasLabel="false"
                        title="性别"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">性别</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      生日:
      </label>
    </td>
    <td class="value">
          <input id="birthday" name="birthday" type="text" style="width: 150px"
                 class="Wdate" onClick="WdatePicker()"
                 datatype="date" ignore="ignore"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">生日</label>
    </td>
              <td align="right">
                  <label class="Validform_label">
                      QQ:
                  </label>
              </td>
              <td class="value">
                  <input id="qq" name="qq" type="text" style="width: 150px" class="inputxt"
                         datatype="qq" ignore="ignore"
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">QQ</label>
              </td>

          </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  联系电话:
              </label>
          </td>
          <td class="value">
              <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                     datatype="m" ignore="ignore"
                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
          <td align="right">
              <label class="Validform_label">
                  职位:
              </label>
          </td>
          <td class="value">
              <input id="position" name="position" type="text" style="width: 150px" class="inputxt"

                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">职位</label>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  家庭住址:
              </label>
          </td>
          <td class="value" colspan="3">
              <input id="address" name="address" type="text" style="width: 514px" class="inputxt"
                     datatype="*1-60" ignore="ignore"
                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">家庭住址</label>
          </td>
      </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      单位:
      </label>
    </td>
    <td class="value">
          <input id="unit" name="unit" type="text" style="width: 150px" class="inputxt"

              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单位</label>
    </td>
              <td align="right">
                  <label class="Validform_label">
                      单位电话:
                  </label>
              </td>
              <td class="value">
                  <input id="unitPhone" name="unitPhone" type="text" style="width: 150px" class="inputxt"
                         datatype="po" ignore="ignore"
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">单位电话</label>
              </td>
          </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  单位地址:
              </label>
          </td>
          <td class="value" colspan="3">
              <input id="unitAddress" name="unitAddress" type="text" style="width: 514px" class="inputxt"
                     datatype="*1-60" ignore="ignore"
                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">单位地址</label>
          </td>
      </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      单位邮编:
      </label>
    </td>
    <td class="value">
          <input id="unitCode" name="unitCode" type="text" style="width: 150px" class="inputxt"

              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单位邮编</label>
    </td>
              <td align="right">
                  <label class="Validform_label">
                      单位传真:
                  </label>
              </td>
              <td class="value">
                  <input id="unitFax" name="unitFax" type="text" style="width: 150px" class="inputxt"
                         datatype="f" ignore="ignore"
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">单位传真</label>
              </td>
          </tr>

          <tr>
    <td align="right">
      <label class="Validform_label">
      备注:
      </label>
    </td>
    <td class="value" colspan="3">
          <input id="note" name="note" type="text" style="width: 514px" class="inputxt"
                 datatype="*1-80" ignore="ignore"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">备注</label>
    </td>

          </tr>
  </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/oa/tScContact.js"></script>