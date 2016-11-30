<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>存货日结表</title>
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
             action="tScIcSpeedbalController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScIcSpeedbalPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScIcSpeedbalPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScIcSpeedbalPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScIcSpeedbalPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScIcSpeedbalPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScIcSpeedbalPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScIcSpeedbalPage.updateDate }">
      <input id="billId" name="billId" type="hidden"
             value="${tScIcSpeedbalPage.billId }">
      <input id="billEntryId" name="billEntryId" type="hidden"
             value="${tScIcSpeedbalPage.billEntryId }">
  <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
    <td align="right">
      <label class="Validform_label">
      单据日期:
      </label>
    </td>
    <td class="value">
          <input id="date" name="date" type="text" style="width: 150px"
                 class="Wdate" onClick="WdatePicker()"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据日期</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      单据类型:
      </label>
    </td>
    <td class="value">
          <input id="tranType" name="tranType" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据类型</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      单据创建日期:
      </label>
    </td>
    <td class="value">
          <input id="billCreateTime" name="billCreateTime" type="text" style="width: 150px"
                 class="Wdate" onClick="WdatePicker()"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">单据创建日期</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      商品id:
      </label>
    </td>
    <td class="value">
          <input id="itemId" name="itemId" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">商品id</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      仓库id:
      </label>
    </td>
    <td class="value">
          <input id="stockId" name="stockId" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">仓库id</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      批号:
      </label>
    </td>
    <td class="value">
          <input id="batchNo" name="batchNo" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">批号</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      数量:
      </label>
    </td>
    <td class="value">
          <input id="qty" name="qty" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">数量</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      辅助数量:
      </label>
    </td>
    <td class="value">
          <input id="secQty" name="secQty" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">辅助数量</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      成本单价:
      </label>
    </td>
    <td class="value">
          <input id="price" name="price" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">成本单价</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      成本金额:
      </label>
    </td>
    <td class="value">
          <input id="amount" name="amount" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">成本金额</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      结余单价:
      </label>
    </td>
    <td class="value">
          <input id="ePrice" name="ePrice" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">结余单价</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      结余金额:
      </label>
    </td>
    <td class="value">
          <input id="eAmount" name="eAmount" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">结余金额</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      差异金额:
      </label>
    </td>
    <td class="value">
          <input id="diffAmount" name="diffAmount" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">差异金额</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      源单类型:
      </label>
    </td>
    <td class="value">
          <input id="blidSrc" name="blidSrc" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">源单类型</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      出入库标记:
      </label>
    </td>
    <td class="value">
          <input id="flag" name="flag" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">出入库标记</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      计算状态:
      </label>
    </td>
    <td class="value">
          <input id="status" name="status" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">计算状态</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      负结余处理状态:
      </label>
    </td>
    <td class="value">
          <input id="negFlag" name="negFlag" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">负结余处理状态</label>
    </td>
            <td align="right">
              <label class="Validform_label">
              </label>
            </td>
            <td class="value">
            </td>
          </tr>
  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScIcSpeedbal.js"></script>