<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.qihang.winter.core.enums.SysThemesEnum,com.qihang.winter.core.util.BrowserUtils" %>
<%@ page import="com.qihang.winter.core.util.SysThemesUtil" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<%
  String lang = BrowserUtils.getBrowserLanguage(request);
  String langurl = "plug-in/mutiLang/" + lang + ".js";
  SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
  String lhgdialogTheme = SysThemesUtil.getLhgdialogTheme(sysTheme);
%>

<html>
<head>
  <title>政务OA系统</title>
  <link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
  <script src=<%=langurl%> type="text/javascript"></script>
  <!--[if lt IE 9]>
  <script src="plug-in/login/js/html5.js"></script>
  <![endif]-->
  <!--[if lt IE 7]>
  <script src="plug-in/login/js/iepng.js" type="text/javascript"></script>
  <script type="text/javascript">
    EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。

  </script>
  <![endif]-->
  <link href="plug-in/login/css/oaLogin.css" rel="stylesheet" type="text/css"/>
  <link href="plug-in/login/css/buttons.css" rel="stylesheet" type="text/css"/>
  <link href="plug-in/login/css/icon.css" rel="stylesheet" type="text/css"/>
  <%--<link rel="stylesheet" type="text/css" href="plug-in/login/css/tipsy.css" media="all"/>--%>
  <style type="text/css">
    html {
      background-image: none;
    }

    label.iPhoneCheckLabelOn span {
      padding-left: 0px
    }

    #versionBar {
      background-color: #212121;
      position: fixed;
      width: 100%;
      height: 35px;
      bottom: 0;
      left: 0;
      text-align: center;
      line-height: 35px;
      z-index: 11;
      -webkit-box-shadow: black 0px 10px 10px -10px inset;
      -moz-box-shadow: black 0px 10px 10px -10px inset;
      box-shadow: black 0px 10px 10px -10px inset;
    }

    .copyright {
      text-align: center;
      font-size: 10px;
      color: #CCC;
    }

    .copyright a {
      color: #A31F1A;
      text-decoration: none
    }

    .on_off_checkbox {
      width: 0px;
    }

    #login .logo {
      width: 300px;
      height: 51px;
    }

    .user{
      width:90%;
      height:30px;
      float:left;
      margin-left:5%;
      color:#5a9009;
      font-size:15px;
      line-height: 30px;
      margin-top:15px;
      text-align: left;

      text-shadow: 0px 0px 2px #efefef;
      font-weight: bold;
    }

    .title2{
      width:330px;
      *height:50px;
      height:auto;
      float:left;
      margin-top:5%;
      margin-left:36%;
      position: relative;
    }


  </style>
</head>
<body>
<div >
  <img class="title2" src="plug-in/login/images/oalogo_fordm.png" alt="">
  <!--<span class="title2 titleFont">餐饮服务安全在线监管平台</span>-->
</div>
<div id="alertMessage"></div>
<div id="successLogin"></div>
<div class="text_success"><img src="plug-in/login/images/loader_green.gif" alt="Please wait"/> <span>
		<t:mutiLang langKey="common.login.success.wait"/></span></div>
<div id="login">
  <div class="top" ></div>
  <!--<div class="ribbon" style="background-image: url(plug-in/login/images/typelogin.png);"></div>-->
  <div class="inner">

    <div class="formLogin">
      <form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser"
            method="post">
        <div class="logo">
          <div class="user" style="margin-left:50px;">用户登录<span class="message" style="color:red;margin-left:50px;"></span></div>
        </div>
        <input name="userKey" type="hidden" id="userKey"
               value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900"/>

        <div class="tip">
          <input class="userName" name="userName" type="text" id="userName" title="" iscookie="true" style="width: 266px"
                 value="" nullmsg=""/>
        </div>
        <div class="tip" style="text-align: center;height: 35px;">
          <input class="password" name="password" type="password" id="password" title="" value="" style="width: 266px"
                 nullmsg=""/>
        </div>
        <div style="margin-top: 5px;height: 40px;">
          <table style="margin-left:50px"><tr><td >
            <input class="randCode" name="randCode" type="text" id="randCode" title="" value="" nullmsg="" style="width:160px;height: 30px;font-size: 15px;margin-left:5px"/></td>
            <td style="padding-bottom: 10px"><img id="randCodeImage" src="randCodeImage" /></td></tr></table>
        </div>
        <div style="text-align: center;height: 40px;">
          <table style="margin-left:60px;margin-bottom: 10px"><tr><td ><input type="checkbox" id="rememberMe" name="remember" checked="true" value="0"/></td>
            <td style="padding-bottom: 10px"><span >
            <t:mutiLang langKey="common.remember.user"/></span></td></tr></table>

        </div>

        <%--update-end--Author:Zerrion  Date:20140226 for：添加验证码--%>

        <div style="text-align: center;margin-top: 15px;">



          <a class="btn-login" href="#" id="but_login" ></a>
          <a class="btn-reset" href="#" id="forgetpass"></a>


          <%--
          <div style="float: left; margin-left: 30px;"><a href="init.jsp"><span class="f_help"><t:mutiLang langKey="common.init.data"/></span></a></div>
          --%>
          <br>
          <select name="langCode" id="langCode" style="display: none"> <option value="">---请选择---
          </option> <option value="en">English </option>
            <option value="zh-cn" selected="selected">中文 </option></select>

        </div>
        <div class="clear"></div>
    </div>
    </form>
  </div>
</div>
<div class="shadow"></div>
</div>
<!--Login div-->
<div class="clear"></div>
<div id="versionBar">
  <div class="copyright">&copy; <t:mutiLang langKey="common.copyright"/> <span class="tip"> <t:mutiLang langKey="common.browser.recommend"/></span></div>
</div>
<!-- Link JScript-->
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery-jrumble.js"></script>
<%--<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>--%>
<script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
<script type="text/javascript" src="plug-in/login/js/newlogin.js"></script>
<!--    <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script> -->
<%=lhgdialogTheme %>
<script>
  $(function(){
    $('#userName').focus();
  });
</script>
</body>
</html>