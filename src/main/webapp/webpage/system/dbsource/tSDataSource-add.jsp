<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>数据源</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
      //dbKey变化时，计算url
      function doDbkeyChange(){
          var dbKey = $("#dbKey").val();
          var olddbKey = $("#olddbKey").val();
          var url = $("#url").val();
          url = url.replace(olddbKey,dbKey);
          $("#url").val(url);
          $("#olddbKey").val(dbKey);
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
             action="dynamicDataSourceController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tSDataSourcePage.id }">
    <input id="olddbKey" name="olddbKey" type="hidden"
           value="${tSDataSourcePage.dbKey }">
  <table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
    <td align="right">
      <label class="Validform_label">
      dbKey:
      </label>
    </td>
    <td class="value">
          <input id="dbKey" name="dbKey" type="text" style="width: 150px" class="inputxt"
                 datatype="*" value="" onchange="doDbkeyChange()"
              >
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">dbKey</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      数据源描述:
      </label>
    </td>
    <td class="value">
          <input id="description" name="description" type="text" style="width: 150px" class="inputxt"
                 datatype="*"
              >
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">数据源描述</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      驱动类:
      </label>
    </td>
    <td class="value">
        <t:dictSelect field="driverClass" width="70" type="list"
                      showDefaultOption="true"
                      typeGroupCode="driverClass" defaultVal="${tSDataSourcePage.driverClass}" hasLabel="false" title="驱动类"></t:dictSelect>
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">驱动类</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      url:
      </label>
    </td>
    <td class="value">
          <input id="url" name="url" type="text" style="width: 500px" class="inputxt"
                 datatype="*" readonly="readonly" value="${tSDataSourcePage.url}"
              >
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">url</label>
    </td>
        </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      数据库用户:
      </label>
    </td>
    <td class="value">
          <input id="dbUser" name="dbUser" type="text" style="width: 150px" class="inputxt"
                 datatype="*" value="${tSDataSourcePage.dbUser}"
              >
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">数据库密码</label>
    </td>
        </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  数据库密码:
              </label>
          </td>
          <td class="value">
              <input id="dbPassword" name="dbPassword" type="password" style="width: 150px" class="inputxt"
                     datatype="*" value="${tSDataSourcePage.dbPassword}"
              >
      <span class="Validform_checktip">
            *
      </span>
              <label class="Validform_label" style="display: none;">数据库密码</label>
          </td>
      </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">
      数据库类型:
      </label>
    </td>
    <td class="value">
        <t:dictSelect field="dbType" width="70" type="list"
                      showDefaultOption="true"
                      typeGroupCode="dbtype" defaultVal="${tSDataSourcePage.dbType}" hasLabel="false" title="数据库类型"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">数据库类型</label>
    </td>
        </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<%--<script src="webpage/com/qihang/buss/sc/sys/tSDataSource.js"></script>--%>
<script src="webpage/system/dbsource/tSDataSource.js"></script>