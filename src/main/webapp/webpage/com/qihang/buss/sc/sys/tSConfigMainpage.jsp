<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>系统设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    $(document).ready(function () {

      $("#mytabs").height($("body").height());
      $('#tt').tabs({
        onSelect: function (title) {
          $('#tt .panel-body').css('width', 'auto');
        }
      });
      $(".tabs-wrap").css('width', '100%');



    });


  </script>
  <style type="text/css" >
    <%-- 给input的背景文字提示增加样式 兼容多种浏览器 --%>
    ::-webkit-input-placeholder{/*WeiKit browsers*/
      color:#999;
    <%-- background-color:#FFF3F3 --%>
    }
    :-moz-placeholder{/*Mozilla Firefox 4 to 18*/
      color:#999;

    }
    ::-moz-placeholder{/*Mozilla Firefox 19+*/
      color:#999;

    }
    :-ms-input-placeholder{/*internet explorer 10+*/
      color:#999;

    }
    html,body{
      height: 99%;
    }
  </style>
</head>
<body style="overflow-x: hidden;">

  <div style="width: auto;" id="mytabs">
      <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
    <div style="width:800px;height:1px;" ></div>
        <t:tabs id="tt" iframe="true" tabPosition="top" fit="true" >


          <t:tab href="tScAccountConfigController.do?goUpdate"
                 icon="icon-default" title="账套设置" id="tScAccountConfig" ></t:tab>
          <t:tab href="tSConfigController.do?goUpdate"
                 icon="icon-default" title="业务参数设置" id="tSConfig"></t:tab>
          <t:tab href="tSConfigController.do?goSupplyUpdate"
                 icon="icon-default" title="供应链设置" id="tScSupplychainConfig"></t:tab>

        </t:tabs>
  </div>



</body>
