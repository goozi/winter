<%@ page import="com.qihang.buss.sc.util.AccountUtil" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.qihang.winter.web.system.pojo.base.TSUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../context/mytags.jsp" %>
<%
  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
  String accountDate = "";
  if(AccountUtil.getAccountStartDate()!=null){
    accountDate = sdf.format(AccountUtil.getAccountStartDate());
  }
%>
<!DOCTYPE html>
<html>
<head>
  <title>优膳名品</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
  <meta name="apple-mobile-web-app-capable" content="yes"/>

  <%--<link href="resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>--%>
  <link href="resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
  <link href="data/styles.css" type="text/css" rel="stylesheet"/>
  <link href="files/home/styles.css" type="text/css" rel="stylesheet"/>
  <link href="plug-in/easyui/themes/gray/easyui.css" type="text/css" rel="stylesheet">
  <link href="plug-in/easyui/themes/icon.css" type="text/css" rel="stylesheet"/>
  <%--  <link href="plug-in/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>--%>
  <link href="plug-in/ace/css/bootstrap.css" type="text/css" rel="stylesheet"/>
  <script src="resources/scripts/jquery-1.7.1.min.js"></script>
  <script src="plug-in/jquery-plugs/jquery.ba-resize.js"></script>
  <script src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>
  <script src="plug-in/easyui/locale/zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/bootstrap/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
  <script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
  <%--<script src="resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>--%>
  <script src="resources/scripts/axure/axQuery.js"></script>
  <script src="resources/scripts/axure/globals.js"></script>
  <script src="resources/scripts/axutils.js"></script>
  <script src="resources/scripts/axure/annotation.js"></script>
  <script src="resources/scripts/axure/axQuery.std.js"></script>
  <script src="resources/scripts/axure/doc.js"></script>
  <script src="data/document.js"></script>
  <script src="resources/scripts/messagecenter.js"></script>
  <script src="resources/scripts/axure/events.js"></script>
  <script src="resources/scripts/axure/action.js"></script>
  <script src="resources/scripts/axure/expr.js"></script>
  <script src="resources/scripts/axure/geometry.js"></script>
  <script src="resources/scripts/axure/flyout.js"></script>
  <script src="resources/scripts/axure/ie.js"></script>
  <script src="resources/scripts/axure/model.js"></script>
  <script src="resources/scripts/axure/repeater.js"></script>
  <script src="resources/scripts/axure/sto.js"></script>
  <script src="resources/scripts/axure/utils.temp.js"></script>
  <script src="resources/scripts/axure/variables.js"></script>
  <script src="resources/scripts/axure/drag.js"></script>
  <script src="resources/scripts/axure/move.js"></script>
  <script src="resources/scripts/axure/visibility.js"></script>
  <script src="resources/scripts/axure/style.js"></script>
  <script src="resources/scripts/axure/adaptive.js"></script>
  <script src="resources/scripts/axure/tree.js"></script>
  <script src="resources/scripts/axure/init.temp.js"></script>
  <script src="files/home/data.js"></script>
  <script src="resources/scripts/axure/legacy.js"></script>
  <script src="resources/scripts/axure/viewer.js"></script>
  <style>
    /**选中面板样式**/
    .on {
      /*background-color: #2BA8D1;*/
      color:red;
      font-weight: bold;
    }
  </style>
  <script type="text/javascript">
    var bool = true;
    $(document).ready(function(){
      //debugger;
//      $("#base").find(".ax_动态面板").each(function(){
//        $(this).unbind('mouseout');
//        $(this).removeAttr("onmouseout");
//        $(this).unbind('mouseover');
//        $(this).removeAttr("onmouseover");
//        $(this).unbind("");
//      });
//      $("#base").find(".panel_state").each(function(){
//        $(this).unbind('mouseout');
//        $(this).removeAttr("onmouseout");
//        $(this).unbind('mouseover');
//        $(this).removeAttr("onmouseover");
//      });
    });
    $axure.utils.getTransparentGifPath = function () {
      return 'resources/images/transparent.gif';
    };
    $axure.utils.getOtherPath = function () {
      return 'resources/Other.html';
    };
    $axure.utils.getReloadPath = function () {
      return 'resources/reload.html';
    };

    var chooseModuleId="";//当前选中的左边菜单id
    function loadModule(title, url, target) {
//      var p = $(target).parentsUntil('.ax_动态面板','.panel_state');
//      p.toggle();
//      p.unbind('mouseout');
//      p.prev().toggle();
      //var data_label = $(target).parent().parent().parent().parent().attr("data-label");
      //获得当前触点击操作对应的左边菜单id
      var data_id = $(target).parent().parent().attr("id") + "";
      if(data_id) {
        data_id = data_id.replace("_state0_content", "");
        data_id = data_id.replace("_state1_content", "");
        data_id = data_id.replace("_state0", "");
        data_id = data_id.replace("_state1", "");
        var data_label = $("#" + data_id).attr("data-label");
        chooseModuleId = data_id;
      }
      //遍历所有左边菜单为未选中
      $("#base").find(".ax_动态面板").each(function(){
        $(this).find(".text").removeClass("on");
        $(this).find(".oninsert").remove();//移除先前选中的插入
        $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("a").remove();
        //$(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find("[src$='u437.png']").remove();
        $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("[src$='transparent.gif']").css("display","");
        //把备份的state0_bak恢复到state0
        var state0_bak = $(this).find("#"+$(this).attr("id") +"_state0_bak");
        if (state0_bak.length>0){

        }
        //进货管理样式处理
        if(data_id == "u473"){

          document.getElementById("u473_state1").style.visibility = 'hidden';
          document.getElementById("u473_state1").style.display = 'none';
          document.getElementById("u473_state0").style.visibility = 'visible';
          document.getElementById("u473_state0").style.display = 'block';
          var img = $("#u473").find("#u476").clone();
          if(bool) {
            bool =false;

            $("#u473").find("#u476").remove();
            $("#u473").find("#u473_state1").find("#u473_state1_content").append(img);
            $("#u473").find(".ax_文本").find(".text").addClass("on");
          }else{
            $("#u473").find("#u476").remove();
            $("#u473").find("#u473_state0").find("#u473_state0_content").append(img);
            $("#u473").find(".ax_文本").find(".text").addClass("on");
          }

        }else{
          bool = true;
          document.getElementById("u473_state1").style.visibility = 'visible';
          document.getElementById("u473_state1").style.display = 'block';
          document.getElementById("u473_state0").style.visibility = 'hidden';
          document.getElementById("u473_state0").style.display = 'none';
          var img = $("#u473").find("#u476").clone();
          $("#u473").find("#u476").remove();
          $("#u473").find("#u473_state0").find("#u473_state0_content").append(img);
          $("#u473").find(".ax_文本").find(".text").removeClass("on");
        }
        //把备份的state0_bak移除
        if($(this).attr("id")==data_id && data_id != "u473"){

          debugger;
          $(this).find(".text").addClass("on");
          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("[src$='transparent.gif']").css("display","none");
//          //隐藏第一个ax_形状（含transparent.gif）
//          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("img").parent().css("display","none");
//          //在第ax_image前加入 带遮罩的菜单图标
//          var state1_ax_a_html = $(this).find("#"+$(this).attr("id") +"_state1").find(".panel_state_content").find(".ax_形状").find("a").parent().html();
//          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").after(state1_ax_a_html);
//          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("a").parent().addClass("oninsert");
//          var ax_image_width = 130;//-7;
//          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("a").find("img").css("width",ax_image_width);
          //在第ax_image前加入 左边黄条图u437.png
          //var div_u437_html = "<div id=\"" + $(this).attr("id") + "_divu437\" class=\"ax_形状 oninsert\"><img id=\"" +$(this).attr("id")+"_img_u437\" class=\"img oninsert\" src=\"images/home/u437.png\">";
          //div_u437_html += "<div id=\"" + $(this).attr("id") + "_text_u437\" class=\"text\"><p><span></span></p></div></div>";
          var div_u437_html = $(this).find("#"+$(this).attr("id") +"_state1").find(".panel_state_content").find(".ax_形状").find("[src$='u437.png']").parent().html();
          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_image").before(div_u437_html);
          //$(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("[src$='u437.png']").parent().addClass("oninsert");
          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find("[src$='u437.png']").addClass("oninsert");
          //在第ax_image前加入 带遮罩的菜单图标
          var state1_ax_a_html = $(this).find("#"+$(this).attr("id") +"_state1").find(".panel_state_content").find(".ax_形状").find("a").parent().html();
          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("[src$='transparent.gif']").after(state1_ax_a_html);
          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("a").addClass("oninsert");
          var ax_image_width = 130;//-7;
          $(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_形状").find("a").find("img").css("width",ax_image_width);
          //产生一个state0备份，id为state0_bak
          //将state1的内容替换到
          //$(this).find("#"+$(this).attr("id") +"_state0").find(".panel_state_content").find(".ax_image").before("<img id=\"" +$(this).attr("id")+"_img_u437\" class=\"img oninsert\" src=\"images/home/u437.png\" style=\"height:75px;\">");
          //$(this).find(".ax_形状").append("<img id=\"" +$(this).attr("id")+"_img_state1\" class=\"img oninsert\" src=\"images/home/u437.png\">");//元素内部插入
          //$(this).find(".ax_形状").after("<div id=\"" +$(this).attr("id")+"_after\" class=\"ax_形状 oninsert\"><img id=\"" +$(this).attr("id")+"_img_after\" class=\"img oninsert\" src=\"images/home/u437.png\">");//元素后面插入
        }


      });

      //$("#" + data_id + "_state1").removeAttr("onmouseout");
      //在右边打开左边对应菜单tab
      addTab(title, url);
    }

  </script>
</head>
<body>
<div id="base" class="">

  <!-- Unnamed (形状) -->
  <div id="u3" class="ax_形状">
    <div id="maintabs" border="false" style="width: 100%;">
      <div id="tabs-1" title="首页" style="width: 100%">
        <iframe style="width:99%;height:680px;margin:0;padding:0" scrolling="no" frameborder="0"
                id="center"
                src="loginController.do?home"></iframe>
      </div>
      <%--<div id="tabs-2" title="导航" style="width: 100%">--%>
        <%--<iframe style="width:99%;height:680px;margin:0;padding:0" scrolling="no" frameborder="0"--%>
                <%--id="center2"--%>
                <%--src="loginController.do?navigation"></iframe>--%>
      <%--</div>--%>
    </div>
  </div>

  <!-- Unnamed (流程形状) -->
  <div id="u5" class="ax_流程形状">
    <img id="u5_img" class="img " src="images/home/head_bg.png"/>
    <!-- Unnamed () -->
    <div id="u6" class="text">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (形状) -->
  <div id="u7" class="ax_文本">
    <img id="u7_img" class="img " src="resources/images/transparent.gif"/>
    <!-- Unnamed () -->
    <div id="u8" class="text">
      <p><span>优膳名品电子商务平台</span></p>
    </div>
  </div>

  <!-- 新手导航 (动态面板) -->
  <%--<div id="u9" class="ax_动态面板" data-label="新手导航">
    <div id="u9_state0" class="panel_state" data-label="State1">
      <div id="u9_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u10" class="ax_文本">
          <img id="u10_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u11" class="text">
            <p><span>&nbsp; &nbsp;&nbsp; 期初建账</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 期初数据完成&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 进货业务&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 销售业务</span></p><p><span>&nbsp;</span></p><p><span>&nbsp;</span></p><p><span>&nbsp;</span></p><p><span>&nbsp; </span></p><p><span>&nbsp;</span></p><p><span>&nbsp;</span></p><p><span>&nbsp;</span></p><p><span>&nbsp;</span></p><p><span>&nbsp; &nbsp; &nbsp; </span></p><p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span></p><p><span>&nbsp;</span></p><p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span><span>月结存</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 财务管理</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>&nbsp; </span><span>&nbsp; &nbsp;&nbsp; 库存管理</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u12" class="ax_形状">
          <img id="u12_img" class="img " src="images/home/u12.png"/>
          <!-- Unnamed () -->
          <div id="u13" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u14" class="ax_形状">
          <img id="u14_img" class="img " src="images/home/u14.png"/>
          <!-- Unnamed () -->
          <div id="u15" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u16" class="ax_形状">
          <img id="u16_img" class="img " src="images/home/u16.png"/>
          <!-- Unnamed () -->
          <div id="u17" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u18" class="ax_形状">
          <img id="u18_img" class="img " src="images/home/u14.png"/>
          <!-- Unnamed () -->
          <div id="u19" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u20" class="ax_形状">
          <img id="u20_img" class="img " src="images/home/u20.png"/>
          <!-- Unnamed () -->
          <div id="u21" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u22" class="ax_形状">
          <img id="u22_img" class="img " src="images/home/u12.png"/>
          <!-- Unnamed () -->
          <div id="u23" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u24" class="ax_形状">
          <img id="u24_img" class="img " src="images/home/u16.png"/>
          <!-- Unnamed () -->
          <div id="u25" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u26" class="ax_image">
          <img id="u26_img" class="img " src="images/home/u26.png"/>
          <!-- Unnamed () -->
          <div id="u27" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u28" class="ax_image">
          <img id="u28_img" class="img " src="images/home/u28.png"/>
          <!-- Unnamed () -->
          <div id="u29" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u30" class="ax_image">
          <img id="u30_img" class="img " src="images/home/u30.png"/>
          <!-- Unnamed () -->
          <div id="u31" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u32" class="ax_image">
          <img id="u32_img" class="img " src="images/home/u32.png"/>
          <!-- Unnamed () -->
          <div id="u33" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u34" class="ax_image">
          <img id="u34_img" class="img " src="images/home/u34.png"/>
          <!-- Unnamed () -->
          <div id="u35" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u36" class="ax_image">
          <img id="u36_img" class="img " src="images/home/u36.png"/>
          <!-- Unnamed () -->
          <div id="u37" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u38" class="ax_image">
          <img id="u38_img" class="img " src="images/home/u38.png"/>
          <!-- Unnamed () -->
          <div id="u39" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u40" class="ax_image">
          <img id="u40_img" class="img " src="images/home/u40.png"/>
          <!-- Unnamed () -->
          <div id="u41" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u42" class="ax_image">
          <img id="u42_img" class="img " src="images/home/u40.png"/>
          <!-- Unnamed () -->
          <div id="u43" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u44" class="ax_image">
          <img id="u44_img" class="img " src="images/home/u40.png"/>
          <!-- Unnamed () -->
          <div id="u45" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u46" class="ax_image">
          <img id="u46_img" class="img " src="images/home/u40.png"/>
          <!-- Unnamed () -->
          <div id="u47" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u48" class="ax_image">
          <img id="u48_img" class="img " src="images/home/u40.png"/>
          <!-- Unnamed () -->
          <div id="u49" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u50" class="ax_image">
          <img id="u50_img" class="img " src="images/home/u50.png"/>
          <!-- Unnamed () -->
          <div id="u51" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u52" class="ax_形状">
          <img id="u52_img" class="img " src="images/home/u52.png"/>
          <!-- Unnamed () -->
          <div id="u53" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u54" class="ax_文本">
          <img id="u54_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u55" class="text">
            <p><span>导航</span><span>图</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>--%>

  <!-- 商城管理 (动态面板) -->
  <div id="u56" class="ax_动态面板" data-label="商城管理">
    <div id="u56_state0" class="panel_state" data-label="状态1">
      <div id="u56_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u57" class="ax_文本">
          <img id="u57_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u58" class="text">
            <p><span>商品上架管理&nbsp; </span><span>&nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp; &nbsp; 商品促销管理&nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; 电子会员管理&nbsp; &nbsp; &nbsp; &nbsp; </span><span>&nbsp; &nbsp;&nbsp; </span><span>商城订单查看</span>
            </p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp; </span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p>
              <span>订单物流跟踪&nbsp; </span><span>&nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp; </span><span>&nbsp;&nbsp; </span><span>&nbsp; </span><span>&nbsp; &nbsp;&nbsp; </span><span>结算查看&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp;&nbsp; 新品推介&nbsp; &nbsp; &nbsp; </span><span>&nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp; &nbsp;&nbsp; 宣传推介</span>
            </p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp;</span></p>
            <p><span>&nbsp; &nbsp; &nbsp; 其它</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u59" class="ax_形状">
          <img id="u59_img" class="img " src="images/home/u20.png"/>
          <!-- Unnamed () -->
          <div id="u60" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u61" class="ax_形状">
          <img id="u61_img" class="img " src="images/home/u12.png"/>
          <!-- Unnamed () -->
          <div id="u62" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u63" class="ax_形状">
          <img id="u63_img" class="img " src="images/home/u16.png"/>
          <!-- Unnamed () -->
          <div id="u64" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u65" class="ax_形状">
          <img id="u65_img" class="img " src="images/home/u14.png"/>
          <!-- Unnamed () -->
          <div id="u66" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u67" class="ax_形状">
          <img id="u67_img" class="img " src="images/home/u16.png"/>
          <!-- Unnamed () -->
          <div id="u68" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u69" class="ax_形状">
          <img id="u69_img" class="img " src="images/home/u14.png"/>
          <!-- Unnamed () -->
          <div id="u70" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u71" class="ax_形状">
          <img id="u71_img" class="img " src="images/home/u20.png"/>
          <!-- Unnamed () -->
          <div id="u72" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u73" class="ax_形状">
          <img id="u73_img" class="img " src="images/home/u12.png"/>
          <!-- Unnamed () -->
          <div id="u74" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u75" class="ax_形状">
          <img id="u75_img" class="img " src="images/home/u16.png"/>
          <!-- Unnamed () -->
          <div id="u76" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u77" class="ax_image">
          <img id="u77_img" class="img " src="images/home/u26.png"/>
          <!-- Unnamed () -->
          <div id="u78" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u79" class="ax_image">
          <img id="u79_img" class="img " src="images/home/u28.png"/>
          <!-- Unnamed () -->
          <div id="u80" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u81" class="ax_image">
          <img id="u81_img" class="img " src="images/home/u30.png"/>
          <!-- Unnamed () -->
          <div id="u82" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u83" class="ax_image">
          <img id="u83_img" class="img " src="images/home/u32.png"/>
          <!-- Unnamed () -->
          <div id="u84" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u85" class="ax_image">
          <img id="u85_img" class="img " src="images/home/u34.png"/>
          <!-- Unnamed () -->
          <div id="u86" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u87" class="ax_image">
          <img id="u87_img" class="img " src="images/home/u36.png"/>
          <!-- Unnamed () -->
          <div id="u88" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u89" class="ax_image">
          <img id="u89_img" class="img " src="images/home/u38.png"/>
          <!-- Unnamed () -->
          <div id="u90" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u91" class="ax_image">
          <img id="u91_img" class="img " src="images/home/u91.png"/>
          <!-- Unnamed () -->
          <div id="u92" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u93" class="ax_image">
          <img id="u93_img" class="img " src="images/home/u93.png"/>
          <!-- Unnamed () -->
          <div id="u94" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u95" class="ax_形状">
          <img id="u95_img" class="img " src="images/home/u52.png"/>
          <!-- Unnamed () -->
          <div id="u96" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u97" class="ax_文本">
          <img id="u97_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u98" class="text">
            <p><span>导航</span><span>图</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u99" class="ax_形状">
          <img id="u99_img" class="img " src="images/home/u99.png"/>
          <!-- Unnamed () -->
          <div id="u100" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u101" class="ax_文本">
          <img id="u101_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u102" class="text">
            <p><span>新手导航</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 商品上架管理 (动态面板) -->
  <div id="u103" class="ax_动态面板" data-label="商品上架管理">
    <div id="u103_state0" class="panel_state" data-label="状态1">
      <div id="u103_state0_content" class="panel_state_content">

        <!-- Unnamed (Image) -->
        <div id="u104" class="ax_image">
          <img id="u104_img" class="img " src="images/home/u91.png"/>
          <!-- Unnamed () -->
          <div id="u105" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- 树结构 (动态面板) -->
        <div id="u106" class="ax_动态面板" data-label="树结构">
          <div id="u106_state0" class="panel_state" data-label="State1">
            <div id="u106_state0_content" class="panel_state_content">

              <!-- Unnamed (形状) -->
              <div id="u107" class="ax_形状">
                <img id="u107_img" class="img " src="images/home/u107.png"/>
                <!-- Unnamed () -->
                <div id="u108" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (文本框(单行)) -->
              <div id="u109" class="ax_文本框_单行_">
                <input id="u109_input" type="text" value=""/>
              </div>

              <!-- Unnamed (树) -->
              <div id="u110" class="ax_树节点 treeroot">
                <div id="u110_children" class="u110_children">

                  <!-- Unnamed (树节点) -->
                  <div id="u111" class="ax_树节点 treenode">

                    <!-- Unnamed (Image) -->
                    <div id="u112" class="ax_image">
                      <img id="u112_img" class="img " src="images/home/u112_selected.png"/>
                      <!-- Unnamed () -->
                      <div id="u113" class="text">
                        <p><span></span></p>
                      </div>
                    </div>
                    <!-- Unnamed (形状) -->
                    <div id="u114" class="" selectiongroup="u110_tree_group">
                      <img id="u114_img" class="img " src="resources/images/transparent.gif"/>
                      <!-- Unnamed () -->
                      <div id="u115" class="text">
                        <p><span>&nbsp; </span><span>项目</span><span> 1</span></p>
                      </div>
                    </div>
                    <div id="u111_children" class="u111_children">

                      <!-- Unnamed (树节点) -->
                      <div id="u116" class="ax_树节点 treenode">

                        <!-- Unnamed (Image) -->
                        <div id="u117" class="ax_image">
                          <img id="u117_img" class="img " src="images/home/u112_selected.png"/>
                          <!-- Unnamed () -->
                          <div id="u118" class="text">
                            <p><span></span></p>
                          </div>
                        </div>
                        <!-- Unnamed (形状) -->
                        <div id="u119" class="" selectiongroup="u110_tree_group">
                          <img id="u119_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u120" class="text">
                            <p><span>项目</span><span> 1.1</span></p>
                          </div>
                        </div>
                        <div id="u116_children" class="u116_children">

                          <!-- Unnamed (树节点) -->
                          <div id="u121" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u122" class="" selectiongroup="u110_tree_group">
                              <img id="u122_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u123" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u124" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u125" class="" selectiongroup="u110_tree_group">
                              <img id="u125_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u126" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u127" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u128" class="" selectiongroup="u110_tree_group">
                              <img id="u128_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u129" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u130" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u131" class="" selectiongroup="u110_tree_group">
                              <img id="u131_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u132" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u133" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u134" class="" selectiongroup="u110_tree_group">
                              <img id="u134_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u135" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u136" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u137" class="" selectiongroup="u110_tree_group">
                              <img id="u137_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u138" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>

                      <!-- Unnamed (树节点) -->
                      <div id="u139" class="ax_树节点 treenode">

                        <!-- Unnamed (Image) -->
                        <div id="u140" class="ax_image">
                          <img id="u140_img" class="img " src="images/home/u112_selected.png"/>
                          <!-- Unnamed () -->
                          <div id="u141" class="text">
                            <p><span></span></p>
                          </div>
                        </div>
                        <!-- Unnamed (形状) -->
                        <div id="u142" class="" selectiongroup="u110_tree_group">
                          <img id="u142_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u143" class="text">
                            <p><span>输入文字...</span></p>
                          </div>
                        </div>
                        <div id="u139_children" class="u139_children">

                          <!-- Unnamed (树节点) -->
                          <div id="u144" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u145" class="" selectiongroup="u110_tree_group">
                              <img id="u145_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u146" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u147" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u148" class="" selectiongroup="u110_tree_group">
                              <img id="u148_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u149" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u150" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u151" class="" selectiongroup="u110_tree_group">
                              <img id="u151_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u152" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u153" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u154" class="" selectiongroup="u110_tree_group">
                              <img id="u154_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u155" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u156" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u157" class="" selectiongroup="u110_tree_group">
                              <img id="u157_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u158" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u159" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u160" class="" selectiongroup="u110_tree_group">
                              <img id="u160_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u161" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u162" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u163" class="" selectiongroup="u110_tree_group">
                              <img id="u163_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u164" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u165" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u166" class="" selectiongroup="u110_tree_group">
                              <img id="u166_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u167" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u168" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u169" class="" selectiongroup="u110_tree_group">
                              <img id="u169_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u170" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>

                      <!-- Unnamed (树节点) -->
                      <div id="u171" class="ax_树节点 treenode">

                        <!-- Unnamed (Image) -->
                        <div id="u172" class="ax_image">
                          <img id="u172_img" class="img " src="images/home/u112_selected.png"/>
                          <!-- Unnamed () -->
                          <div id="u173" class="text">
                            <p><span></span></p>
                          </div>
                        </div>
                        <!-- Unnamed (形状) -->
                        <div id="u174" class="" selectiongroup="u110_tree_group">
                          <img id="u174_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u175" class="text">
                            <p><span>输入文字...</span></p>
                          </div>
                        </div>
                        <div id="u171_children" class="u171_children">

                          <!-- Unnamed (树节点) -->
                          <div id="u176" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u177" class="" selectiongroup="u110_tree_group">
                              <img id="u177_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u178" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u179" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u180" class="" selectiongroup="u110_tree_group">
                              <img id="u180_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u181" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u182" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u183" class="" selectiongroup="u110_tree_group">
                              <img id="u183_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u184" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u185" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u186" class="" selectiongroup="u110_tree_group">
                              <img id="u186_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u187" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u188" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u189" class="" selectiongroup="u110_tree_group">
                              <img id="u189_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u190" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u191" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u192" class="" selectiongroup="u110_tree_group">
                              <img id="u192_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u193" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u194" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u195" class="" selectiongroup="u110_tree_group">
                              <img id="u195_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u196" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u197" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u198" class="" selectiongroup="u110_tree_group">
                              <img id="u198_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u199" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>

                          <!-- Unnamed (树节点) -->
                          <div id="u200" class="ax_树节点 treenode">
                            <!-- Unnamed (形状) -->
                            <div id="u201" class="" selectiongroup="u110_tree_group">
                              <img id="u201_img" class="img " src="resources/images/transparent.gif"/>
                              <!-- Unnamed () -->
                              <div id="u202" class="text">
                                <p><span>输入文字...</span></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>

                      <!-- Unnamed (树节点) -->
                      <div id="u203" class="ax_树节点 treenode">
                        <!-- Unnamed (形状) -->
                        <div id="u204" class="" selectiongroup="u110_tree_group">
                          <img id="u204_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u205" class="text">
                            <p><span>输入文字...</span></p>
                          </div>
                        </div>
                      </div>

                      <!-- Unnamed (树节点) -->
                      <div id="u206" class="ax_树节点 treenode">
                        <!-- Unnamed (形状) -->
                        <div id="u207" class="" selectiongroup="u110_tree_group">
                          <img id="u207_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u208" class="text">
                            <p><span>输入文字...</span></p>
                          </div>
                        </div>
                      </div>

                      <!-- Unnamed (树节点) -->
                      <div id="u209" class="ax_树节点 treenode">
                        <!-- Unnamed (形状) -->
                        <div id="u210" class="" selectiongroup="u110_tree_group">
                          <img id="u210_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u211" class="text">
                            <p><span>项目</span><span>&nbsp;</span><span>1.2</span></p>
                          </div>
                        </div>
                      </div>

                      <!-- Unnamed (树节点) -->
                      <div id="u212" class="ax_树节点 treenode">
                        <!-- Unnamed (形状) -->
                        <div id="u213" class="" selectiongroup="u110_tree_group">
                          <img id="u213_img" class="img " src="resources/images/transparent.gif"/>
                          <!-- Unnamed () -->
                          <div id="u214" class="text">
                            <p><span>项目</span><span> 1.3</span></p>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u215" class="ax_形状">
          <img id="u215_img" class="img " src="images/home/u215.png"/>
          <!-- Unnamed () -->
          <div id="u216" class="text">
            <p><span>上 架</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u217" class="ax_形状">
          <img id="u217_img" class="img " src="images/home/u217.png"/>
          <!-- Unnamed () -->
          <div id="u218" class="text">
            <p><span>新 增</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u219" class="ax_形状">
          <img id="u219_img" class="img " src="images/home/u219.png"/>
          <!-- Unnamed () -->
          <div id="u220" class="text">
            <p><span>删 除</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u221" class="ax_形状">
          <img id="u221_img" class="img " src="images/home/u215_mouseOver.png"/>
          <!-- Unnamed () -->
          <div id="u222" class="text">
            <p><span>导 出</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u223" class="ax_文本">
          <img id="u223_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u224" class="text">
            <p><span>上架</span><span>状态</span></p>
          </div>
        </div>

        <!-- Unnamed (下拉列表框) -->
        <div id="u225" class="ax_下拉列表框">
          <select id="u225_input">
            <option selected value="已上架">已上架</option>
            <option value="未上架">未上架</option>
          </select>
        </div>

        <!-- Unnamed (文本框(单行)) -->
        <div id="u226" class="ax_文本框_单行_">
          <input id="u226_input" type="text" value=""/>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u227" class="ax_形状">
          <img id="u227_img" class="img " src="images/home/u227.png"/>
          <!-- Unnamed () -->
          <div id="u228" class="text">
            <p><span>查 询</span></p>
          </div>
        </div>

        <!-- 列表 (动态面板) -->
        <div id="u229" class="ax_动态面板" data-label="列表">
          <div id="u229_state0" class="panel_state" data-label="State1">
            <div id="u229_state0_content" class="panel_state_content">

              <!-- Unnamed (形状) -->
              <div id="u230" class="ax_形状">
                <img id="u230_img" class="img " src="images/home/u230.png"/>
                <!-- Unnamed () -->
                <div id="u231" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>序号</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 商品编号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 商品名称&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 规格&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 数量&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 单价&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp;&nbsp; </span><span>预售单价&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>&nbsp; </span><span>上架状态&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span><span>&nbsp; &nbsp; &nbsp;&nbsp; </span><span>操作&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u232" class="ax_形状">
                <img id="u232_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u233" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>1</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 大闸蟹1号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; djx&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 123&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 456&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 999&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 1020&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u234" class="ax_形状">
                <img id="u234_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u235" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>2</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 大闸蟹1号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; djx&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 123&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 456&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 999&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 1020&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u236" class="ax_形状">
                <img id="u236_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u237" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>3</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 大闸蟹1号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; djx&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 123&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 456&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 999&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 1020&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u238" class="ax_形状">
                <img id="u238_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u239" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>4</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 大闸蟹1号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; djx&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 123&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 456&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 999&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 1020&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u240" class="ax_形状">
                <img id="u240_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u241" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>5</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 大闸蟹1号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; djx&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 123&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 456&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 999&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 1020&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u242" class="ax_形状">
                <img id="u242_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u243" class="text">
                  <p><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </span><span>7</span><span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 大闸蟹1号&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; djx&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 123&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 456&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 999&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; 1020&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; </span>
                  </p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u244" class="ax_形状">
                <img id="u244_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u245" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u246" class="ax_形状">
                <img id="u246_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u247" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u248" class="ax_形状">
                <img id="u248_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u249" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u250" class="ax_形状">
                <img id="u250_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u251" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u252" class="ax_形状">
                <img id="u252_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u253" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u254" class="ax_形状">
                <img id="u254_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u255" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u256" class="ax_形状">
                <img id="u256_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u257" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u258" class="ax_形状">
                <img id="u258_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u259" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u260" class="ax_形状">
                <img id="u260_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u261" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u262" class="ax_形状">
                <img id="u262_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u263" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u264" class="ax_形状">
                <img id="u264_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u265" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u266" class="ax_形状">
                <img id="u266_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u267" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u268" class="ax_垂直线">
                <img id="u268_start" class="img " src="resources/images/transparent.gif" alt="u268_start"/>
                <img id="u268_end" class="img " src="resources/images/transparent.gif" alt="u268_end"/>
                <img id="u268_line" class="img " src="images/home/u268_line.png" alt="u268_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u269" class="ax_垂直线">
                <img id="u269_start" class="img " src="resources/images/transparent.gif" alt="u269_start"/>
                <img id="u269_end" class="img " src="resources/images/transparent.gif" alt="u269_end"/>
                <img id="u269_line" class="img " src="images/home/u268_line.png" alt="u269_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u270" class="ax_垂直线">
                <img id="u270_start" class="img " src="resources/images/transparent.gif" alt="u270_start"/>
                <img id="u270_end" class="img " src="resources/images/transparent.gif" alt="u270_end"/>
                <img id="u270_line" class="img " src="images/home/u268_line.png" alt="u270_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u271" class="ax_垂直线">
                <img id="u271_start" class="img " src="resources/images/transparent.gif" alt="u271_start"/>
                <img id="u271_end" class="img " src="resources/images/transparent.gif" alt="u271_end"/>
                <img id="u271_line" class="img " src="images/home/u268_line.png" alt="u271_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u272" class="ax_垂直线">
                <img id="u272_start" class="img " src="resources/images/transparent.gif" alt="u272_start"/>
                <img id="u272_end" class="img " src="resources/images/transparent.gif" alt="u272_end"/>
                <img id="u272_line" class="img " src="images/home/u268_line.png" alt="u272_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u273" class="ax_垂直线">
                <img id="u273_start" class="img " src="resources/images/transparent.gif" alt="u273_start"/>
                <img id="u273_end" class="img " src="resources/images/transparent.gif" alt="u273_end"/>
                <img id="u273_line" class="img " src="images/home/u268_line.png" alt="u273_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u274" class="ax_垂直线">
                <img id="u274_start" class="img " src="resources/images/transparent.gif" alt="u274_start"/>
                <img id="u274_end" class="img " src="resources/images/transparent.gif" alt="u274_end"/>
                <img id="u274_line" class="img " src="images/home/u268_line.png" alt="u274_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u275" class="ax_垂直线">
                <img id="u275_start" class="img " src="resources/images/transparent.gif" alt="u275_start"/>
                <img id="u275_end" class="img " src="resources/images/transparent.gif" alt="u275_end"/>
                <img id="u275_line" class="img " src="images/home/u268_line.png" alt="u275_line"/>
              </div>

              <!-- Unnamed (垂直线) -->
              <div id="u276" class="ax_垂直线">
                <img id="u276_start" class="img " src="resources/images/transparent.gif" alt="u276_start"/>
                <img id="u276_end" class="img " src="resources/images/transparent.gif" alt="u276_end"/>
                <img id="u276_line" class="img " src="images/home/u268_line.png" alt="u276_line"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u277" class="ax_复选框">
                <label for="u277_input">
                  <!-- Unnamed () -->
                  <div id="u278" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u277_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u279" class="ax_复选框">
                <label for="u279_input">
                  <!-- Unnamed () -->
                  <div id="u280" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u279_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u281" class="ax_复选框">
                <label for="u281_input">
                  <!-- Unnamed () -->
                  <div id="u282" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u281_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u283" class="ax_复选框">
                <label for="u283_input">
                  <!-- Unnamed () -->
                  <div id="u284" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u283_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u285" class="ax_复选框">
                <label for="u285_input">
                  <!-- Unnamed () -->
                  <div id="u286" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u285_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u287" class="ax_复选框">
                <label for="u287_input">
                  <!-- Unnamed () -->
                  <div id="u288" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u287_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u289" class="ax_复选框">
                <label for="u289_input">
                  <!-- Unnamed () -->
                  <div id="u290" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u289_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u291" class="ax_复选框">
                <label for="u291_input">
                  <!-- Unnamed () -->
                  <div id="u292" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u291_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u293" class="ax_复选框">
                <label for="u293_input">
                  <!-- Unnamed () -->
                  <div id="u294" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u293_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u295" class="ax_复选框">
                <label for="u295_input">
                  <!-- Unnamed () -->
                  <div id="u296" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u295_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u297" class="ax_复选框">
                <label for="u297_input">
                  <!-- Unnamed () -->
                  <div id="u298" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u297_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u299" class="ax_复选框">
                <label for="u299_input">
                  <!-- Unnamed () -->
                  <div id="u300" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u299_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u301" class="ax_复选框">
                <label for="u301_input">
                  <!-- Unnamed () -->
                  <div id="u302" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u301_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u303" class="ax_复选框">
                <label for="u303_input">
                  <!-- Unnamed () -->
                  <div id="u304" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u303_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u305" class="ax_复选框">
                <label for="u305_input">
                  <!-- Unnamed () -->
                  <div id="u306" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u305_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u307" class="ax_复选框">
                <label for="u307_input">
                  <!-- Unnamed () -->
                  <div id="u308" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u307_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u309" class="ax_形状">
                <img id="u309_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u310" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u311" class="ax_形状">
                <img id="u311_img" class="img " src="images/home/u232.png"/>
                <!-- Unnamed () -->
                <div id="u312" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u313" class="ax_复选框">
                <label for="u313_input">
                  <!-- Unnamed () -->
                  <div id="u314" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u313_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u315" class="ax_复选框">
                <label for="u315_input">
                  <!-- Unnamed () -->
                  <div id="u316" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u315_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u317" class="ax_复选框">
                <label for="u317_input">
                  <!-- Unnamed () -->
                  <div id="u318" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u317_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u319" class="ax_复选框">
                <label for="u319_input">
                  <!-- Unnamed () -->
                  <div id="u320" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u319_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (复选框) -->
              <div id="u321" class="ax_复选框">
                <label for="u321_input">
                  <!-- Unnamed () -->
                  <div id="u322" class="text">
                    <p><span></span></p>
                  </div>
                </label>
                <input id="u321_input" type="checkbox" value="checkbox"/>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u323" class="ax_image">
                <img id="u323_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u324" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u325" class="ax_image">
                <img id="u325_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u326" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u327" class="ax_image">
                <img id="u327_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u328" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u329" class="ax_image">
                <img id="u329_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u330" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u331" class="ax_image">
                <img id="u331_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u332" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u333" class="ax_image">
                <img id="u333_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u334" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u335" class="ax_image">
                <img id="u335_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u336" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u337" class="ax_image">
                <img id="u337_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u338" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u339" class="ax_image">
                <img id="u339_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u340" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u341" class="ax_image">
                <img id="u341_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u342" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u343" class="ax_image">
                <img id="u343_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u344" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u345" class="ax_image">
                <img id="u345_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u346" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u347" class="ax_image">
                <img id="u347_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u348" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u349" class="ax_image">
                <img id="u349_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u350" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u351" class="ax_image">
                <img id="u351_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u352" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u353" class="ax_image">
                <img id="u353_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u354" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u355" class="ax_image">
                <img id="u355_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u356" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u357" class="ax_image">
                <img id="u357_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u358" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u359" class="ax_image">
                <img id="u359_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u360" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (Image) -->
              <div id="u361" class="ax_image">
                <img id="u361_img" class="img " src="images/home/u323.png"/>
                <!-- Unnamed () -->
                <div id="u362" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u363" class="ax_形状">
                <img id="u363_img" class="img " src="images/home/u363.png"/>
                <!-- Unnamed () -->
                <div id="u364" class="text">
                  <p><span>未上架</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u365" class="ax_形状">
                <img id="u365_img" class="img " src="images/home/u363.png"/>
                <!-- Unnamed () -->
                <div id="u366" class="text">
                  <p><span>未上架</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u367" class="ax_形状">
                <img id="u367_img" class="img " src="images/home/u367.png"/>
                <!-- Unnamed () -->
                <div id="u368" class="text">
                  <p><span>已</span><span>上架</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u369" class="ax_文本">
                <img id="u369_img" class="img " src="resources/images/transparent.gif"/>
                <!-- Unnamed () -->
                <div id="u370" class="text">
                  <p><span>第 1/17 页 (470 条记录)</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u371" class="ax_形状">
                <img id="u371_img" class="img " src="images/home/u371.png"/>
                <!-- Unnamed () -->
                <div id="u372" class="text">
                  <p><span>1</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u373" class="ax_形状">
                <img id="u373_img" class="img " src="images/home/u373.png"/>
                <!-- Unnamed () -->
                <div id="u374" class="text">
                  <p><span>2</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u375" class="ax_形状">
                <img id="u375_img" class="img " src="images/home/u373.png"/>
                <!-- Unnamed () -->
                <div id="u376" class="text">
                  <p><span>3</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u377" class="ax_形状">
                <img id="u377_img" class="img " src="images/home/u373.png"/>
                <!-- Unnamed () -->
                <div id="u378" class="text">
                  <p><span>4</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u379" class="ax_形状">
                <img id="u379_img" class="img " src="images/home/u373.png"/>
                <!-- Unnamed () -->
                <div id="u380" class="text">
                  <p><span>5</span></p>
                </div>
              </div>

              <!-- Unnamed (文本框(单行)) -->
              <div id="u381" class="ax_文本框_单行_">
                <input id="u381_input" type="text" value=""/>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u382" class="ax_形状">
                <img id="u382_img" class="img " src="images/home/u382.png"/>
                <!-- Unnamed () -->
                <div id="u383" class="text">
                  <p><span>跳转</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u384" class="ax_形状">
                <img id="u384_img" class="img " src="images/home/u373.png"/>
                <!-- Unnamed () -->
                <div id="u385" class="text">
                  <p><span>6</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u386" class="ax_形状">
                <img id="u386_img" class="img " src="images/home/u386.png"/>
                <!-- Unnamed () -->
                <div id="u387" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u388" class="ax_形状">
                <img id="u388_img" class="img " src="images/home/u388.png"/>
                <!-- Unnamed () -->
                <div id="u389" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u390" class="ax_形状">
                <img id="u390_img" class="img " src="images/home/u390.png"/>
                <!-- Unnamed () -->
                <div id="u391" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u392" class="ax_形状">
                <img id="u392_img" class="img " src="images/home/u392.png"/>
                <!-- Unnamed () -->
                <div id="u393" class="text">
                  <p><span></span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u394" class="ax_形状">
                <img id="u394_img" class="img " src="images/home/u367.png"/>
                <!-- Unnamed () -->
                <div id="u395" class="text">
                  <p><span>已</span><span>上架</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u396" class="ax_形状">
                <img id="u396_img" class="img " src="images/home/u367.png"/>
                <!-- Unnamed () -->
                <div id="u397" class="text">
                  <p><span>已</span><span>上架</span></p>
                </div>
              </div>

              <!-- Unnamed (形状) -->
              <div id="u398" class="ax_形状">
                <img id="u398_img" class="img " src="images/home/u363.png"/>
                <!-- Unnamed () -->
                <div id="u399" class="text">
                  <p><span>未上架</span></p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u400" class="ax_文本">
          <img id="u400_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u401" class="text">
            <p><span>勾选多个分类，过滤商品信息</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u402" class="ax_形状">
          <img id="u402_img" class="img " src="images/home/u402.png"/>
          <!-- Unnamed () -->
          <div id="u403" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u404" class="ax_文本">
          <img id="u404_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u405" class="text">
            <p><span>商品上架管理</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u406" class="ax_文本">
          <img id="u406_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u407" class="text">
            <p><span>新手导航</span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u408" class="ax_形状">
          <img id="u408_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u409" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u410" class="ax_文本">
          <img id="u410_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u411" class="text">
            <p><span>导航</span><span>图</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Unnamed (形状) -->
  <div id="u412" class="ax_文本">
    <img id="u412_img" class="img " src="resources/images/transparent.gif"/>
    <!-- Unnamed () -->
    <div id="u413" class="text">
      <p><span>LOGO</span></p>
    </div>
  </div>

  <div style="float:right;width:650px;height: 50px" id="topIcon">
    <!-- Unnamed (Image) -->
    <div id="u414" class="ax_image" title="退出系统">
      <img id="u414_img" class="img " src="images/home/u414.png"
           onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>');"/>
      <!-- Unnamed () -->
      <div id="u415" class="text">
        <p><span></span></p>
      </div>
    </div>

    <!-- Unnamed (Image) -->
    <div id="u416" class="ax_image" title="消息推送">
      <img id="u416_img" class="img " src="images/home/u416.png"/>
      <!-- Unnamed () -->
      <div id="u417" class="text">
        <p><span></span></p>
      </div>
    </div>
    <!-- Unnamed (Image) -->
    <div id="u420" class="ax_image" title="预警提醒">
      <img id="u420_img" class="img " src="images/home/u420.png"/>
      <!-- Unnamed () -->
      <div id="u421" class="text">
        <p><span></span></p>
      </div>
    </div>
    <div id="u983">
      <% if(!session.getAttribute("dataSourceType").toString().equals("dataSource_jeecg")){%>
      <span class="badge badge-important" style="font-size: 14px;font-weight: normal;">
        当前账套：<%=session.getAttribute("accountBookName")%> -- 当前期：<%=AccountUtil.isAccountOpen()?accountDate:"未开账"%>
      </span>
      <%}%>
    </div>
    <!-- Unnamed (Image) -->
    <%--<div id="u418" class="ax_image">--%>
    <%--<img id="u418_img" class="img " src="images/home/u418.png"/>--%>
    <%--<!-- Unnamed () -->--%>
    <%--<div id="u419" class="text">--%>
    <%--<p><span></span></p>--%>
    <%--</div>--%>
    <%--</div>--%>


    <!-- Unnamed (形状) -->
    <%--<div id="u422" class="ax_文本">--%>
    <%--<img id="u422_img" class="img " src="resources/images/transparent.gif"/>--%>
    <%--<!-- Unnamed () -->--%>
    <%--<div id="u423" class="text">--%>
    <%--<p>--%>
    <%--&lt;%&ndash;<span style="margin-right: 32px">咨询 </span>&ndash;%&gt;--%>
    <%--<span style="margin-right: 40px">提醒</span>--%>
    <%--<span--%>
    <%--style="margin-right: 18px"> 消息中心 </span>--%>
    <%--<span> 退出系统</span>--%>
    <%--</p>--%>
    <%--</div>--%>
    <%--</div>--%>
  </div>

  <!-- Unnamed (Image) -->
  <div id="u424" class="ax_image">
    <%--<img id="u424_img" class="img " src="images/home/u424.png"/>--%>
    <!-- Unnamed () -->
    <div id="u425" class="text">
      <p><span></span></p>
    </div>
  </div>

  <!-- Unnamed (Image) -->
  <div id="u426" class="ax_image">
    <img id="u426_img" class="img " src="images/home/u426.png"/>
    <!-- Unnamed () -->
    <div id="u427" class="text"  title="<%=((TSUser)session.getAttribute("user")).getRealName()%>">
      <p><span class="badge badge-warning"  style="width:66px;overflow: hidden;text-overflow: ellipsis;"><%=((TSUser)session.getAttribute("user")).getRealName()%></span></p>
    </div>
    <div id="u982" title="<%=request.getAttribute("roleName")%>">
      <span class="badge badge-success"
            style="width:108px;overflow: hidden;text-overflow: ellipsis;font-size: 12px;font-weight: normal;"><%=request.getAttribute("roleName")%></span>
    </div>
    <div id="u984" title="<%=request.getSession().getAttribute("departName")%>">
      <span class="badge badge-important"
            style="width:108px;overflow: hidden;text-overflow: ellipsis;font-size: 12px;font-weight: normal;"><%=request.getSession().getAttribute("departName")%></span>
    </div>
  </div>

  <!-- 零售管理 (动态面板) -->
  <div id="u428" class="ax_动态面板" data-label="经销商管理">
    <div id="u428_state0" class="panel_state" data-label="状态1" style="width: 140px; height: 75px;">
      <div id="u428_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u429" class="ax_形状">
          <img id="u429_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u430" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u431" class="ax_image">
          <img id="u431_img" class="img " src="images/home/u28.png"/>
          <!-- Unnamed () -->
          <div id="u432" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u433" class="ax_文本">
          <img id="u433_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u434" class="text">
            <p><span>经销商管理</span></p>
          </div>
        </div>
      </div>
    </div>
    <div id="u428_state1" class="panel_state" data-label="状态2">
      <div id="u428_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u435" class="ax_形状" style="cursor: pointer">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=5',this)">--%>
            <img id="u435_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=5',this)"/>
          <%--</a>--%>

          <!-- Unnamed () -->
          <div id="u436" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u437" class="ax_形状">
          <img id="u437_img" class="img " src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u438" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u439" class="ax_image" style="cursor: pointer;">
          <img id="u439_img" class="img " src="images/home/u28.png"
               onclick="loadModule('导航','loginController.do?navigate&type=5',this)"/>
          <!-- Unnamed () -->
          <div id="u440" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u441" class="ax_文本">
          <img id="u441_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u442" class="text">
            <p>经销商管理</p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 基础信息管理 (动态面板) -->
  <div id="u443" class="ax_动态面板" data-label="基础信息管理">
    <div id="u443_state0" class="panel_state" data-label="状态1" style="width: 140px; height: 75px;">
      <div id="u443_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u444" class="ax_形状">
          <img id="u444_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u445" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (Image) -->
        <div id="u448" class="ax_image">
          <img id="u448_img" class="img " src="images/home/u448.png"/>
          <!-- Unnamed () -->
          <div id="u449" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (形状) -->
        <div id="u446" class="ax_文本">
          <img id="u446_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u447" class="text">
            <p><span>基础信息管理</span></p>
          </div>
        </div>


      </div>
    </div>
    <div id="u443_state1" class="panel_state" data-label="状态2">
      <div id="u443_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u450" class="ax_形状"  style="cursor: pointer;">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=2',this)">--%>
            <img id="u450_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=2',this)"/>
          <%--</a>--%>

          <!-- Unnamed () -->
          <div id="u451" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u452" class="ax_形状">
          <img id="u452_img" class="img " src="images/home/u437.png">

          <!-- Unnamed () -->
          <div id="u453" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u454" class="ax_image"  style="cursor: pointer;">

          <img id="u454_img" class="img " src="images/home/u448.png"
               onclick="loadModule('导航','loginController.do?navigate&type=2',this)"/>
          <!-- Unnamed () -->
          <div id="u455" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u456" class="ax_文本">
          <img id="u456_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u457" class="text">
            <p><span>基础信息管理</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 资金管理 (动态面板) -->
  <div id="u458" class="ax_动态面板" data-label="资金管理">
    <div id="u458_state0" class="panel_state" data-label="状态1" style="width: 140px; height: 75px;">
      <div id="u458_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u459" class="ax_形状">
          <img id="u459_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u460" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u461" class="ax_image">
          <img id="u461_img" class="img " src="images/home/u36.png"/>
          <!-- Unnamed () -->
          <div id="u462" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u463" class="ax_文本">
          <img id="u463_img" class="img " src="resources/images/transparent.gif">

          <!-- Unnamed () -->
          <div id="u464" class="text">
            <p><span>资金管理</span></p>
          </div>
        </div>
      </div>
    </div>
    <div id="u458_state1" class="panel_state" data-label="状态2">
      <div id="u458_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u465" class="ax_形状"  style="cursor: pointer;">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=6',this)">--%>
            <img id="u465_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=6',this)"/>
          <%--</a>--%>

          <!-- Unnamed () -->
          <div id="u466" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u467" class="ax_形状">
          <img id="u467_img" class="img " src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u468" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u469" class="ax_image"  style="cursor: pointer;">
          <img id="u469_img" class="img " src="images/home/u36.png"
               onclick="loadModule('导航','loginController.do?navigate&type=6',this)"/>
          <!-- Unnamed () -->
          <div id="u470" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u471" class="ax_文本">
          <img id="u471_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u472" class="text">
            <p><span>资金管理</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 进货管理 (动态面板) -->
  <div id="u473" class="ax_动态面板" data-label="进货管理" style="cursor: pointer;">
    <div id="u473_state0" class="panel_state"  data-label="状态1">
      <div id="u473_state0_content" class="panel_state_content">

        <div id="u474" class="ax_形状">
          <img id="u474_img" class="img " src="images/home/u435.png" style="cursor: pointer;"/>
          <div id="u475" class="text">
            <p><span></span></p>
          </div>
        </div>
        <div id="u476" class="ax_形状" >
          <img id="u476_img" class="img" onclick="loadModule('导航','loginController.do?navigate&type=3',this)" src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u477" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (Image) -->
        <div id="u478" class="ax_image">
          <img id="u478_img" class="img " src="images/home/u572.png"
               onclick="loadModule('导航','loginController.do?navigate&type=3',this)"/>
          <!-- Unnamed () -->
          <div id="u479" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (形状) -->
        <div id="u480" class="ax_文本">
          <img id="u480_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u481" class="text">
            <p>进货管理</p>
          </div>
        </div>
      </div>
    </div>
    <div id="u473_state1" class="panel_state" data-label="状态">
      <div id="u473_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u482" class="ax_形状">
          <img id="u482_img" class="img"
               onclick="loadModule('导航','loginController.do?navigate&type=3',this)" src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u483" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u484" class="ax_image">
          <img id="u484_img" class="img"  onclick="loadModule('导航','loginController.do?navigate&type=3',this)" src="images/home/u572.png"/>
          <!-- Unnamed () -->
          <div id="u485" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (形状) -->

        <!-- Unnamed (形状) -->
        <div id="u486" class="ax_文本">
          <img id="u486_img" class="img" onclick="loadModule('导航','loginController.do?navigate&type=3',this)" src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u487" class="text">
            <p>进货管理</p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 系统管理 (动态面板) -->
  <div id="u488" class="ax_动态面板" data-label="初始化">
    <div id="u488_state0" class="panel_state" data-label="状态1" style="width: 140px; height: 75px;">
      <div id="u488_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u489" class="ax_形状">

          <img id="u489_img" class="img " src="resources/images/transparent.gif">
          <!-- Unnamed () -->
          <div id="u490" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (Image) -->
        <div id="u493" class="ax_image">
          <img id="u493_img" class="img " src="images/home/u493.png"/>
          <!-- Unnamed () -->
          <div id="u494" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u491" class="ax_文本">
          <img id="u491_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u492" class="text">
            <p>初始化</p>
          </div>
        </div>


      </div>
    </div>
    <div id="u488_state1" class="panel_state" data-label="状态2" style="display: none; visibility: hidden;">
      <div id="u488_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u495" class="ax_形状"  style="cursor: pointer;">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=1',this)">--%>
            <img id="u495_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=1',this)"/>
          <%--</a>--%>

          <!-- Unnamed () -->
          <div id="u496" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (形状) -->
        <div id="u499" class="ax_形状">
          <img id="u499_img" class="img " src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u500" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (Image) -->
        <div id="u497" class="ax_image"  style="cursor: pointer;">
          <img id="u497_img" class="img " src="images/home/u493.png"
               onclick="loadModule('导航','loginController.do?navigate&type=1',this)"/>
          <!-- Unnamed () -->
          <div id="u498" class="text">
            <p><span></span></p>
          </div>
        </div>
        <!-- Unnamed (形状) -->
        <div id="u501" class="ax_文本">
          <img id="u501_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u502" class="text">
            <p>初始化</p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 销售管理 (动态面板) -->
  <div id="u579" class="ax_动态面板" data-label="销货管理">
    <div id="u579_state0" class="panel_state" data-label="状态1">
      <div id="u579_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u580" class="ax_形状">
          <img id="u580_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u581" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u582" class="ax_image">
          <img id="u582_img" class="img " src="images/home/u573.png"/>
          <!-- Unnamed () -->
          <div id="u583" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u584" class="ax_文本">
          <img id="u584_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u585" class="text">
            <p><span>销货管理</span></p>
          </div>
        </div>
      </div>
    </div>
    <div id="u579_state1" class="panel_state" data-label="状态2">
      <div id="u579_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u586" class="ax_形状"  style="cursor: pointer;">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=8',this)">--%>
            <img id="u586_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=8',this)"/>
          <%--</a>--%>


          <!-- Unnamed () -->
          <div id="u587" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u588" class="ax_形状">
          <img id="u588_img" class="img " src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u589" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u590" class="ax_image"  style="cursor: pointer;">
          <img id="u590_img" class="img " src="images/home/u573.png"
               onclick="loadModule('导航','loginController.do?navigate&type=8',this)"/>
          <!-- Unnamed () -->
          <div id="u591" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u592" class="ax_文本">
          <img id="u592_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u593" class="text">
            <p><span>销货管理</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 仓存管理 (动态面板) -->
  <div id="u503" class="ax_动态面板" data-label="仓存管理">
    <div id="u503_state0" class="panel_state" data-label="状态1">
      <div id="u503_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u504" class="ax_形状">
          <img id="u504_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u505" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u506" class="ax_image">
          <img id="u506_img" class="img " src="images/home/u506.png"/>
          <!-- Unnamed () -->
          <div id="u507" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u508" class="ax_文本">
          <img id="u508_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u509" class="text">
            <p><span>仓存</span><span>管理</span></p>
          </div>
        </div>
      </div>
    </div>
    <div id="u503_state1" class="panel_state" data-label="状态2">
      <div id="u503_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u510" class="ax_形状"  style="cursor: pointer;">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=4',this)">--%>
            <img id="u510_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=4',this)"/>
          <%--</a>--%>


          <!-- Unnamed () -->
          <div id="u511" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u512" class="ax_形状">
          <img id="u512_img" class="img " src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u513" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u514" class="ax_image"  style="cursor: pointer;">
          <img id="u514_img" class="img " src="images/home/u506.png"
               onclick="loadModule('导航','loginController.do?navigate&type=4',this)"/>
          <!-- Unnamed () -->
          <div id="u515" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u516" class="ax_文本">
          <img id="u516_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u517" class="text">
            <p><span>仓存</span><span>管理</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Unnamed (形状) -->
  <%--<nav class="navbar navbar-default" role="navigation" id="navigatebar">--%>
  <%--<button type="button" class="navbar-toggle" data-toggle="collapse"--%>
  <%--data-target="#example-navbar-collapse">--%>
  <%--<span class="sr-only">切换导航</span>--%>
  <%--<span class="icon-bar"></span>--%>
  <%--<span class="icon-bar"></span>--%>
  <%--<span class="icon-bar"></span>--%>
  <%--</button>--%>
  <%--<div class="ax_文本" id="menuNavigate">菜单</div>--%>
  <%--</nav>--%>
  <div id="u518" class="ax_形状">
    <img id="u518_img" class="img " src="images/home/menu_bg.png"/>
    <!-- Unnamed () -->
    <%-- <div class="navbar navbar-inverse" role="navigation" style="font-size: 14px;" style="">--%>


    <div class="collapse navbar-collapse" id="example-navbar-collapse">
      <t:menu style="scm_bootstrap" menuFun="${menuMap}"/>
    </div>
  </div>


  <!-- oa (动态面板) -->
  <div id="u556" class="ax_动态面板" data-label="oa">
    <div id="u556_state0" class="panel_state" data-label="状态1" style="width: 140px; height: 75px;">
      <div id="u556_state0_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u557" class="ax_形状">
          <img id="u557_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u558" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u559" class="ax_image">
          <img id="u559_img" class="img " src="images/home/u559.png"/>
          <!-- Unnamed () -->
          <div id="u560" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u561" class="ax_文本">
          <img id="u561_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u562" class="text">
            <p><span>OA</span><span>管理</span></p>
          </div>
        </div>
      </div>
    </div>
    <div id="u556_state1" class="panel_state" data-label="状态2" style="display: none; visibility: hidden;">
      <div id="u556_state1_content" class="panel_state_content">

        <!-- Unnamed (形状) -->
        <div id="u563" class="ax_形状"  style="cursor: pointer;">
          <%--<a href="javascript:loadModule('导航','loginController.do?navigate&type=7',this)">--%>
            <img id="u563_img" class="img " src="images/home/u435.png"
                 onclick="loadModule('导航','loginController.do?navigate&type=7',this)"/>
          <%--</a>--%>

          <!-- Unnamed () -->
          <div id="u564" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u565" class="ax_形状">
          <img id="u565_img" class="img " src="images/home/u437.png"/>
          <!-- Unnamed () -->
          <div id="u566" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (Image) -->
        <div id="u567" class="ax_image"  style="cursor: pointer;">
          <img id="u567_img" class="img " src="images/home/u559.png"
               onclick="loadModule('导航','loginController.do?navigate&type=7',this)"/>
          <!-- Unnamed () -->
          <div id="u568" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (形状) -->
        <div id="u569" class="ax_文本">
          <img id="u569_img" class="img " src="resources/images/transparent.gif"/>
          <!-- Unnamed () -->
          <div id="u570" class="text">
            <p><span>OA</span><span>管理</span></p>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u571" class="ax_横线">
    <img id="u571_start" class="img " src="resources/images/transparent.gif" alt="u571_start"/>
    <img id="u571_end" class="img " src="resources/images/transparent.gif" alt="u571_end"/>
    <img id="u571_line" class="img " src="images/home/u571_line.png" alt="u571_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u572" class="ax_横线">
    <img id="u572_start" class="img " src="resources/images/transparent.gif" alt="u572_start"/>
    <img id="u572_end" class="img " src="resources/images/transparent.gif" alt="u572_end"/>
    <img id="u572_line" class="img " src="images/home/u571_line.png" alt="u572_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u573" class="ax_横线">
    <img id="u573_start" class="img " src="resources/images/transparent.gif" alt="u573_start"/>
    <img id="u573_end" class="img " src="resources/images/transparent.gif" alt="u573_end"/>
    <img id="u573_line" class="img " src="images/home/u571_line.png" alt="u573_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u574" class="ax_横线">
    <img id="u574_start" class="img " src="resources/images/transparent.gif" alt="u574_start"/>
    <img id="u574_end" class="img " src="resources/images/transparent.gif" alt="u574_end"/>
    <img id="u574_line" class="img " src="images/home/u571_line.png" alt="u574_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u575" class="ax_横线">
    <img id="u575_start" class="img " src="resources/images/transparent.gif" alt="u575_start"/>
    <img id="u575_end" class="img " src="resources/images/transparent.gif" alt="u575_end"/>
    <img id="u575_line" class="img " src="images/home/u571_line.png" alt="u575_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u576" class="ax_横线">
    <img id="u576_start" class="img " src="resources/images/transparent.gif" alt="u576_start"/>
    <img id="u576_end" class="img " src="resources/images/transparent.gif" alt="u576_end"/>
    <img id="u576_line" class="img " src="images/home/u571_line.png" alt="u576_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u577" class="ax_横线">
    <img id="u577_start" class="img " src="resources/images/transparent.gif" alt="u577_start"/>
    <img id="u577_end" class="img " src="resources/images/transparent.gif" alt="u577_end"/>
    <img id="u577_line" class="img " src="images/home/u571_line.png" alt="u577_line"/>
  </div>

  <!-- Unnamed (横线) -->
  <div id="u578" class="ax_横线">
    <img id="u578_start" class="img " src="resources/images/transparent.gif" alt="u578_start"/>
    <img id="u578_end" class="img " src="resources/images/transparent.gif" alt="u578_end"/>
    <img id="u578_line" class="img " src="images/home/u571_line.png" alt="u578_line"/>
  </div>
  <!-- Unnamed (横线) -->
  <div id="u594" class="ax_横线">
    <img id="u594_start" class="img " src="resources/images/transparent.gif" alt="u594_start"/>
    <img id="u594_end" class="img " src="resources/images/transparent.gif" alt="u594_end"/>
    <img id="u594_line" class="img " src="images/home/u571_line.png" alt="u594_line"/>
  </div>
</div>
<div id="mm" class="easyui-menu" style="width: 150px;">
  <div id="mm-tabupdate"><t:mutiLang langKey="common.refresh"/></div>
  <div id="mm-tabclose"><t:mutiLang langKey="common.close"/></div>
  <div id="mm-tabcloseall"><t:mutiLang langKey="common.close.all"/></div>
  <div id="mm-tabcloseother"><t:mutiLang langKey="common.close.all.but.this"/></div>
  <div class="menu-sep"></div>
  <div id="mm-tabcloseright"><t:mutiLang langKey="common.close.all.right"/></div>
  <div id="mm-tabcloseleft"><t:mutiLang langKey="common.close.all.left"/></div>
</div>
</body>
<%--<t:base type="easyui,tools"/>--%>
<script>
  jQuery(function ($) {
    $("#maintabs").tabs({
      fit: true
    });
    tabClose();
    tabCloseEven();
//    debugger;
    var contentHeight = ($(window).height() - 92) < 750 ? 750 : ($(window).height() - 92);
    var contentWidth = $(window).width();
    if (contentWidth > 763 && contentWidth <= 1398) {
      contentHeight = contentHeight - 56;
    } else if (contentWidth <= 763) {
      contentHeight = contentHeight - 112;
    }
    $('#u424').height(contentHeight);
    $('#u3').height(contentHeight);
    $('#u3').width($(window).width() - 130);
    $('iframe').height(contentHeight - 44);
    $('#maintabs').tabs({
      width: 'auto',
      height: $("#maintabs").parent().height() + 2
    });

    $(window).resize(function () {
//      debugger;
      contentHeight = ($(window).height() - 92) < 750 ? 750 : ($(window).height() - 92);
      contentWidth = $(window).width();
      if (contentWidth > 763 && contentWidth <= 1398) {
        contentHeight = contentHeight - 56;
      } else if (contentWidth <= 763) {
        contentHeight = contentHeight - 112;
      }
      $('#u424').height(contentHeight);
      $('#u3').height(contentHeight);
      $('#u3').width($(window).width() - 130);
      $('iframe').height(contentHeight - 44);
      var title = $('.tabs-selected').text();
      if (title == '首页') {
        $('iframe').attr('src', $('iframe').attr('src'));
      }
      $('#maintabs').tabs({
        width: 'auto',
        height: $("#maintabs").parent().height() + 2
      });
    });

//    $('#u518').resize(function(){
//      var h518 = $('#u518').height();
//      if(h518 == 94){
//        $('#u424').height($('#u424').height()-56);
//        $('#u3').height($('#u3').height()-56);
//      }else if(h518 == 150){
//        $('#u424').height($('#u424').height()-112);
//        $('#u3').height($('#u3').height()-112);
//      }
//    })
    $("#u579_state1").css("display: none; visibility: hidden;");
    $("#u579").mouseover(function () {
      document.getElementById("u579_state1").style.visibility = 'visible';
    });
    $("#u579").mouseout(function () {
      document.getElementById("u579_state1").style.visibility = 'hidden';
    });
//
 //   $("#u473_state1").css("display: none; visibility: hidden;");

    $("#u476").hide();
    document.getElementById("u473_state1").style.visibility = 'visible';
    document.getElementById("u473_state1").style.display = 'block';
    document.getElementById("u473_state0").style.visibility = 'hidden';
    document.getElementById("u473_state0").style.display = 'none';
    $("#u473").mouseover(function () {
      document.getElementById("u473_state1").style.visibility = 'hidden';
      document.getElementById("u473_state1").style.display = 'none';
      document.getElementById("u473_state0").style.visibility = 'visible';
      document.getElementById("u473_state0").style.display = 'block';
      var img = $("#u473").find("#u476").clone();
      $("#u473").find("#u476").remove();
      $("#u473").find("#u473_state0").find("#u473_state0_content").append(img);
      if(bool)
        $("#u476").show();
      });
    $("#u473").mouseout(function () {
      document.getElementById("u473_state1").style.visibility = 'visible';
      document.getElementById("u473_state1").style.display = 'block';
      document.getElementById("u473_state0").style.visibility = 'hidden';
      document.getElementById("u473_state0").style.display = 'none';
      var img = $("#u473").find("#u476").clone();
      $("#u473").find("#u476").remove();
      $("#u473").find("#u473_state1").find("#u473_state1_content").append(img);
      if(bool)
        $("#u476").hide();
    });
  });
  function addTab(subtitle, url, icon) {

    var progress = $("div.messager-progress");
    if (progress.length) {
      return;
    }
    rowid = "";
    $.messager.progress({
      text: loading,
      interval: 200
    });
    if (!$('#maintabs').tabs('exists', subtitle)) {
      //判断是否进行href方式打开tab，默认为iframe方式
      if (url.indexOf('isHref') != -1) {
        $('#maintabs').tabs('add', {
          title: subtitle,
          href: url,
          closable: true,
          icon: icon
        });
      } else {

        $('#maintabs').tabs('add', {
          title: subtitle,
          content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:' + ($('#maintabs').height() - 35) + 'px"></iframe>',
          closable: true,
          icon: icon
        });

      }

    } else {
      $('#maintabs').tabs('select', subtitle);
      var currTab = $('#maintabs').tabs('getSelected');
//      var url = $(currTab.panel('options').content).attr('src');
      $('#maintabs').tabs('update', {
        tab: currTab,
        options: {
          content: createFrame(url)
        }
      });
      $.messager.progress('close');
    }

    // $('#maintabs').tabs('select',subtitle);
    tabClose();
  }
  function tabClose() {
    /* 双击关闭TAB选项卡 */
    $(".tabs-inner").dblclick(function () {
      var subtitle = $(this).children(".tabs-closable").text();
      $('#tabs').tabs('close', subtitle);
    })
    /* 为选项卡绑定右键 */
    $(".tabs-inner").bind('contextmenu', function (e) {
      $('#mm').menu('show', {
        left: e.pageX,
        top: e.pageY
      });

      var subtitle = $(this).children(".tabs-closable").text();

      $('#mm').data("currtab", subtitle);
      // $('#maintabs').tabs('select',subtitle);
      return false;
    });
  }
  // 绑定右键菜单事件
  function tabCloseEven() {
    // 刷新
    $('#mm-tabupdate').click(function () {
      var currTab = $('#maintabs').tabs('getSelected');
      var url = $(currTab.panel('options').content).attr('src');
      $('#maintabs').tabs('update', {
        tab: currTab,
        options: {
          content: createFrame(url)
        }
      })
    })
    // 关闭当前
    $('#mm-tabclose').click(function () {
      var currtab_title = $('#mm').data("currtab");
      $('#maintabs').tabs('close', currtab_title);
    })
    // 全部关闭
    $('#mm-tabcloseall').click(function () {
      $('.tabs-inner span').each(function (i, n) {
        var t = $(n).text();
        if (t != '首页') {
          $('#maintabs').tabs('close', t);
        }
      });
    });
    // 关闭除当前之外的TAB
    $('#mm-tabcloseother').click(function () {
      $('#mm-tabcloseright').click();
      $('#mm-tabcloseleft').click();
    });
    // 关闭当前右侧的TAB
    $('#mm-tabcloseright').click(function () {
      var nextall = $('.tabs-selected').nextAll();
      if (nextall.length == 0) {
        // msgShow('系统提示','后边没有啦~~','error');
        alert('后边没有啦~~');
        return false;
      }
      nextall.each(function (i, n) {
        var t = $('a:eq(0) span', $(n)).text();
        $('#maintabs').tabs('close', t);
      });
      return false;
    });
    // 关闭当前左侧的TAB
    $('#mm-tabcloseleft').click(function () {
      var prevall = $('.tabs-selected').prevAll();
      if (prevall.length == 0) {
        alert('到头了，前边没有啦~~');
        return false;
      }
      prevall.each(function (i, n) {
        var t = $('a:eq(0) span', $(n)).text();
        if (t != '首页') {
          $('#maintabs').tabs('close', t);
        }
      });
      return false;
    });

    // 退出
    $("#mm-exit").click(function () {
      $('#mm').menu('hide');
    });
  }
  function createFrame(url) {
    //var h = $(document.body).height()-210;
    var s = '<iframe name="tabiframe" id="tabiframe"  scrolling="no" frameborder="0"  src="' + url + '" style="width:100%;height:99%;overflow-y:hidden;"></iframe>';
    return s;
  }
  $.parser.onComplete = function () {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
    if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
    }
    window.setTimeout(function () {
      $.messager.progress('close');
    }, 200);
  };
</script>
</html>

