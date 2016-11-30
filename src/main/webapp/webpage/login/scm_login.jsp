﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.qihang.winter.core.enums.SysThemesEnum,com.qihang.winter.core.util.BrowserUtils" %>
<%@ page import="com.qihang.winter.core.util.SysThemesUtil" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
  String lang = BrowserUtils.getBrowserLanguage(request);
  String langurl = "plug-in/mutiLang/" + lang + ".js";
  SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
  String lhgdialogTheme = SysThemesUtil.getLhgdialogTheme(sysTheme);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>优膳名品</title>
  <link href="plug-in/login/css/scm_login.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all"/>
  <link href="plug-in/login/css/scm_demo.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all"/>
  <link rel="stylesheet" href="plug-in/lhgDialog/skins/default.css" type="text/css"/>
  <%--<link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"/>--%>
  <style>
    .ui_lt,.ui_t,.ui_rt{
      border-bottom-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
      border-top-width: 0px;
    }
  </style>
  <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
  <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
  <script type="text/javascript" src="plug-in/jquery/jquery.SuperSlide.js"></script>
  <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
  <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
  <%--<script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script>--%>

  <script>
    $(function () {
      readCookies();
      $(".i-text").focus(function () {
        $(this).addClass('h-light');
      });

      $(".i-text").focusout(function () {
        $(this).removeClass('h-light');
      });

      $("#userName").focus(function () {
        var username = $(this).val();
        if (username == '输入账号') {
          $(this).val('');
        }
      });

      $("#userName").focusout(function () {
        var username = $(this).val();
        if (username == '') {
          $(this).val('输入账号');
        }
      });


      $("#password").focus(function () {
        var username = $(this).val();
        if (username == '输入密码') {
          $(this).val('');
        }
      });


      $("#randCode").focus(function () {
        var username = $(this).val();
        if (username == '输入验证码') {
          $(this).val('');
        }
      });

      $("#randCode").focusout(function () {
        var username = $(this).val();
        if (username == '') {
          $(this).val('输入验证码');
        }
      });

      //$("#dbKey").dblclick(function (e) {
      $("#dbDescription").dblclick(function (e) {
        //if (e.keyCode == 13) {
          selectAccountDialog();
        //}
      });

      $(".registerform").Validform({
        tiptype: function (msg, o, cssctl) {
          var objtip = $(".error-box");
          cssctl(objtip, o.type);
          objtip.text(msg);
        },
        ajaxPost: false,
        beforeSubmit: function(){
          writeCookies();
          var checkurl = $('form').attr('check');//验证路径
          var formData = new Object();
          var flag = false;
          $(":input").each(function () {
            formData[this.name] = $("#" + this.name).val();
          });
          formData['orgId'] = "";
          formData['langCode'] = $("#langCode").val();
          $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            url: checkurl,// 请求的action路径
            data: formData,
            error: function () {// 请求失败处理函数
            },
            success: function (data) {
              var d = data;
              if (d.success) {
                  flag = true;
              } else {
                if (d.msg == "a") {
                  $.dialog.confirm("数据库无数据,是否初始化数据?", function () {
                    window.location = "init.jsp";
                  }, function () {
                    //
                  });
                } else {
                  $.dialog.alert(d.msg);
                }
              }
            }
          });
          return flag;
        }//,
        /*callback: function(data){
          if(data.success){
            location.href = 'loginController.do?goHomepage&amp;homepage='+data.obj;
          }else{
            location.href = 'webpage/login/login.jsp';
          }
        }*/
      });
      $('#randCodeImage').click(function () {
        reloadRandCodeImage();
      });
      /**
       * 刷新验证码
       */
      function reloadRandCodeImage() {
        var date = new Date();
        var img = document.getElementById("randCodeImage");
        img.src = 'randCodeImage?a=' + date.getTime();
      }
      //设置cookie
      function readCookies() {
        if ($.cookie('rememberMe') == 'true') {
          $('#rememberMe').prop('checked', true);
          $('#userName').val($.cookie('userName'));
          //$('#fsid').val($.cookie('fsid'));
          $('#dbKey').val($.cookie('dbKey'));
          $('#dbDescription').val($.cookie('dbDescription'));
        }
      }

      function writeCookies() {
        if ($('#rememberMe').prop('checked')) {
          var dbKey = $("#dbKey").val();
          var dbDescription = $("#dbDescription").val();
          var userName = $("#userName").val();
          //var password = $("#fsid").val();
          $.cookie("rememberMe", "true", {expires: 7}); // 存储一个带7天期限的 cookie
          $.cookie("userName", userName, {expires: 7}); // 存储一个带7天期限的 cookie
          //$.cookie("fsid", password, {expires: 7}); // 存储一个带7天期限的 cookie
          $.cookie("dbKey", dbKey, {expires: 7}); // 存储一个带7天期限的 cookie
          $.cookie("dbDescription", dbDescription, {expires: 7}); // 存储一个带7天期限的 cookie
        }
        else {
          $.cookie("rememberMe", "false", {expires: -1});
          $.cookie("userName", '', {expires: -1});
          $.cookie("dbKey", '', {expires: -1});
          $.cookie("dbDescription", '', {expires: -1});
          //$.cookie("fsid", '', {expires: -1});
        }
      }
      $.get('loginController.do?getRandCode',function (randCode) {
        $('#randCode').val(randCode);
      },'text');
    });

    //选择账套
    function selectAccountDialog(index) {
      var itemName = $("#dbKey").val();
      var url = 'tScAccountConfigController.do?goSelectDialog&dbKey=' + itemName;
      var width = 640;
      var height = 480;
      var title = "账套";
      /*$('#dd').dialog({
        title: title,
        width: width,
        height: height,
        closed: false,
        cache: false,
        href: url,
        modal: true,
      });*/
      $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        zIndex: 9999999,
        height: height,
        cache: false,
        width: width,
        opacity: 0.3,
        min: false,
        max: false,
        button: [{
          name: '确定',
          callback: function () {
            iframe = this.iframe.contentWindow;
            var item = iframe.getSelectionData();
            if (item!=null){
              $("#dbKey").val(item.dbKey);
              $("#dbDescription").val(item.dbDescription);
            }else{//清空账套
              $("#dbKey").val("");
              $("#dbDescription").val("");
            }
          },
          focus: true
        }, {
          name: '取消',
          callback: function () {
          }
        }]
      }).zindex();

    }
  </script>
</head>
<body>
<div class="header">
  <div class="headerlogo"><img alt="logo" src="plug-in/login/images/logo.png"></div>
  <div class="headerNav">
    <a target="_blank" href="http://www.qihangsoft.com">启航官网</a>
    <a target="_blank" href="http://www.qihangsoft.com"> 启航商城</a>
    <a target="_blank" href="http://www.qihangsoft.com">开发团队</a>
    <a target="_blank" href="http://www.qihangsoft.com">意见反馈</a>
    <a target="_blank" href="http://www.qihangsoft.com">帮助</a>
  </div>
</div>

<div class="banner">

  <div class="login-aside">
    <div id="o-box-up"></div>
    <div id="o-box-down" style="table-layout:fixed;">
      <div class="error-box"></div>

      <form class="registerform" action="loginController.do?login"  check="loginController.do?checkuser" method="post">
        <input type="hidden" id="langCode" value="zh-cn"/>
        <div class="fm-item">
          <label for="dbDescription" class="form-label">账套：</label>
          <input type="hidden" name="dbKey" id="dbKey">
          <input type="text" value="" maxlength="100" id="dbDescription" name="dbDescription" class="i-text"
                 errormsg="按回车符选择账套" readonly="readonly" >
          <div class="ui-form-explain"></div>
        </div>
        <div class="fm-item">
          <label for="userName" class="form-label">用户名：</label>
          <input type="text" value="scadmin" maxlength="100" id="userName" name="userName" class="i-text"
                 datatype="s" >
          <div class="ui-form-explain"></div>
        </div>

        <div class="fm-item">
          <label for="password" class="form-label">密码：</label>
          <input type="password" value="123456" maxlength="100" id="password" name="password" class="i-text" datatype="*6-16" nullmsg="请设置密码！"
                 errormsg="密码范围在6~16位之间！">
          <div class="ui-form-explain"></div>
        </div>
        <div class="fm-item  pos-r">
          <label for="rememberMe" class="form-label" style="left:165px;position:absolute;">记住用户名</label>
          <input type="checkbox" id="rememberMe" name="remember" checked="ture" class="on_off_checkbox"
                 value="0"/>
        </div>
        <div class="fm-item pos-r">
          <label for="randCode" class="form-label">验证码</label>
          <input type="text" value="" maxlength="100" id="randCode" name="randCode" class="i-text yzm" nullmsg="请输入验证码！">
          <div class="ui-form-explain"><img src="randCodeImage" class="yzm-img" id="randCodeImage"/></div>
        </div>

        <div class="fm-item">
          <label for="btn-sub" class="form-label"></label>
          <input type="submit" value="" tabindex="4" id="btn-sub" name="btn_sub" class="btn-login">
          <div class="ui-form-explain"></div>
        </div>
        <div id="dd"></div><%--账套选择窗口--%>
      </form>

    </div>

  </div>

  <div class="bd">
    <ul>
      <li style="background:url(plug-in/login/images/theme-pic1.jpg) #CCE1F3 center 0 no-repeat;"></li>
      <li style="background:url(plug-in/login/images/theme-pic2.jpg) #BCE0FF center 0 no-repeat;"></li>
    </ul>
  </div>

  <div class="hd">
    <ul></ul>
  </div>
</div>
<script type="text/javascript">jQuery(".banner").slide({
  titCell: ".hd ul",
  mainCell: ".bd ul",
  effect: "fold",
  autoPlay: true,
  autoPage: true,
  trigger: "click"
});</script>


<div class="banner-shadow"></div>

<div class="footer">
  <p>版权所有：厦门典通科技有限公司</p>
  <p>建议使用的浏览器:IE9+/FireFox等浏览
  </p>
</div>
</body>
</html>
