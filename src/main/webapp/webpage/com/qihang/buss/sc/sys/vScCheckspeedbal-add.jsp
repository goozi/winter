<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>结账前检查当期月结库存统计列表</title>
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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="vScCheckspeedbalController.do?doAdd" tiptype="1">
      <input id="stockid" name="stockid" type="hidden"
             value="${vScCheckspeedbalPage.stockid }">
      <input id="itemid" name="itemid" type="hidden"
             value="${vScCheckspeedbalPage.itemid }">
      <input id="id" name="id" type="hidden"
             value="${vScCheckspeedbalPage.id }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
    <td align="right">
      <label class="Validform_label">
      日结年月:
      </label>
    </td>
    <td class="value">
          <input id="date" name="date" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">日结年月</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      批号:
      </label>
    </td>
    <td class="value">
          <input id="batchno" name="batchno" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">批号</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      基本数量:
      </label>
    </td>
    <td class="value">
          <input id="qty" name="qty" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">基本数量</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      辅助数量:
      </label>
    </td>
    <td class="value">
          <input id="secqty" name="secqty" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">辅助数量</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      仓库:
      </label>
    </td>
    <td class="value">
          <input id="sonname" name="sonname" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">仓库</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      分支机构:
      </label>
    </td>
    <td class="value">
          <input id="departmentname" name="departmentname" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">分支机构</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      商品名称:
      </label>
    </td>
    <td class="value">
          <input id="itemname" name="itemname" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">商品名称</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      商品编号:
      </label>
    </td>
    <td class="value">
          <input id="itemnumber" name="itemnumber" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">商品编号</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      单位:
      </label>
    </td>
    <td class="value">
          <input id="unitname" name="unitname" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单位</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      单位换算率:
      </label>
    </td>
    <td class="value">
          <input id="coefficient" name="coefficient" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单位换算率</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      箱数:
      </label>
    </td>
    <td class="value">
          <input id="bigqty" name="bigqty" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">箱数</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      散数:
      </label>
    </td>
    <td class="value">
          <input id="smallqty" name="smallqty" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">散数</label>
    </td>
          </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/vScCheckspeedbal.js"></script>