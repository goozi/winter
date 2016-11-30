<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>农业投入品在线监管平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="stylesheet" href="plug-in/accordion/css/icons.css"/>
    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="plug-in/ace/css/bootstrap.css"/>
    <link rel="stylesheet" href="plug-in/ace/css/font-awesome.css"/>
    <link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css">
    <link rel="stylesheet" href="plug-in/ace/css/qh-newstyle.css">
    <!-- text fonts -->
    <link rel="stylesheet" href="plug-in/ace/css/ace-fonts.css"/>

    <link rel="stylesheet" href="plug-in/ace/css/jquery-ui.css"/>
    <!-- ace styles -->
    <link rel="stylesheet" href="plug-in/ace/css/ace.css" class="ace-main-stylesheet" id="main-ace-style"/>

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-part2.css" class="ace-main-stylesheet"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="plug-in/ace/css/ace-ie.css"/>
    <![endif]-->
    <!-- ace settings handler -->
    <script src="plug-in/ace/js/ace-extra.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="plug-in/ace/js/html5shiv.js"></script>
    <script src="plug-in/ace/js/respond.js"></script>
    <![endif]-->
    <style>
        html, body {
            margin: 0px;
            height: 100%;
        }

        .submenu a:visited {
            background-color: #fff !important;
        }

        #mm .menu-text {
            top: 1px;
            left: 36px;
        }

    </style>
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>
</head>
<body class="no-skin">
<!-- #section:basics/navbar.layout -->
<div id="navbar" class="navbar navbar-default">
    <div class="navbar-container" id="navbar-container" nowrap="nowrap">
        <!-- #section:basics/sidebar.mobile.toggle -->
        <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
            <span class="sr-only">Toggle sidebar</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <!-- /section:basics/sidebar.mobile.toggle -->
        <div class="navbar-header pull-left">
            <!-- #section:basics/navbar.layout.brand -->
            <div class="qh-logo"></div>
            <div class="qh-divider"></div>
            <div class="qh-title"></div>
            <!-- /section:basics/navbar.layout.brand -->
            <!-- #section:basics/navbar.toggle -->
            <!-- /section:basics/navbar.toggle -->
        </div>
        <%--</div>--%>
        <%--<div class="qh-nav-right " style="float: right">--%>
        <!-- #section:basics/navbar.dropdown -->
        <div class="navbar-buttons navbar-header pull-right" role="navigation"
             style="height: 45px;margin: 18px 15px 18px 0;">
            <ul class="nav ace-nav">
                <%--<li class="grey">--%>
                <!-- #section:basics/navbar.user_menu -->
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="plug-in/ace/avatars/user.jpg" alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎,${userName }</small>
									<span style="color: #CC33FF">
                    <span style="color: #CC33FF"><t:mutiLang langKey="common.role"/>:</span>
                    <span style="color: #D99B28">${roleName }</span>
								</span>
              </span>
                        <i class="ace-icon fa fa-caret-down"></i>
                    </a>

                    <ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="javascript:add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                                <i class="ace-icon fa fa-cog"></i>
                                <t:mutiLang langKey="common.change.password"/>
                            </a>
                        </li>

                        <li>
                            <a href="javascript:openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')">
                                <i class="ace-icon fa fa-user"></i>
                                <t:mutiLang langKey="common.profile"/>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,200)">
                                <i class="ace-icon fa fa-user"></i>
                                <t:mutiLang langKey="common.my.style"/>
                            </a>
                        </li>

                        <li>
                            <a href="javascript:clearLocalstorage()">
                                <i class="ace-icon fa fa-warning"></i>
                                <t:mutiLang langKey="common.clear.localstorage"/>
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="javascript:logout()">
                                <i class="ace-icon fa fa-power-off"></i>
                                <t:mutiLang langKey="common.logout"/>
                            </a>
                        </li>
                    </ul>
                </li>

                <!-- /section:basics/navbar.user_menu -->
            </ul>
        </div>
        <!-- /section:basics/navbar.dropdown -->
        <%--</div>--%>
    </div><!-- /.navbar-container -->
</div>

<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <!-- #section:basics/sidebar -->
    <div id="sidebar" class="sidebar responsive" style="min-height: 630px;margin-right: -15px;">
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'fixed')
            } catch (e) {
            }
        </script>
        <ul class="nav nav-list">
            <li class="">
                <a href="javascript:loadModule('首页','loginController.do?home')">
                    <i class="menu-icon qh-icon-home"></i>
                    <span class="menu-text"> 首页 </span>
                </a>
                <b class="arrow"></b>
            </li>
            <li class="qh-menu-divider"></li>
            <t:menu style="ace" menuFun="${menuMap}"></t:menu>
        </ul><!-- /.nav-list -->

        <!-- #section:basics/sidebar.layout.minimize -->
        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
               data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>

        <!-- /section:basics/sidebar.layout.minimize -->
        <script type="text/javascript">
            try {
                ace.settings.check('sidebar', 'collapsed')
            } catch (e) {
            }
        </script>
    </div>
    <div class="main-content">
        <!-- /section:basics/sidebar -->
        <!-- #section:basics/content.breadcrumbs -->
        <div class="breadcrumbs" id="breadcrumbs" style="display:none">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
            </ul><!-- /.breadcrumb -->

            <!-- #section:basics/content.searchbox -->
            <div class="nav-search" id="nav-search">
                <form class="form-search">
							<span class="input-icon">
								<input type="text" placeholder="Search ..." class="nav-search-input"
                                       id="nav-search-input"
                                       autocomplete="off"/>
								<i class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
                </form>
            </div><!-- /.nav-search -->

            <!-- /section:basics/content.searchbox -->
        </div>

        <!-- /section:basics/content.breadcrumbs -->
        <div class="page-content" style="padding:0px">
            <!-- /section:settings.box -->
            <div id="page-content-area" style="width: 100%;min-height: 630px;">
                <div id="maintabs" border="false" style="width: 100%;">
                    <div id="tabs-1" title="首页" style="width: 100%">
                        <iframe style="width:99%;height:680px;margin:0px;padding:0px" scrolling="no" frameborder="0"
                                id="center"
                                src="loginController.do?home"></iframe>
                    </div>
                </div>
            </div><!-- /.page-content-area -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->


    <div class="footer">
        <div class="footer-inner">
            <div class="footer-content">
						<span class="bigger-120">
							典通&nbsp; &copy; 2005-2015
						</span>
            </div>
        </div>
    </div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
    </a>
</div><!-- /.main-container -->
<!-- basic scripts -->

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='plug-in/ace/js/jquery.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->
<div id="changestylePanel" style="display:none">
    <form id="formobj" action="userController.do?savestyle" name="formobj" method="post">
        <table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td>风格</td>
            </tr>
            <tr>
                <td class="value"><input type="radio" value="default" name="indexStyle"/> <span>经典风格</span></td>
            </tr>
            <tr>
                <td class="value"><input type="radio" value="bootstrap" name="indexStyle"/> <span>BootStrap风格</span>
                </td>
            </tr>
            <!-- update-start--Author:gaofeng  Date:2014-01-10 for:新增首页风格  -->
            <tr>
                <td class="value"><input type="radio" value="shortcut" name="indexStyle"/> <span>ShortCut风格</span></td>
            </tr>
            <!-- update-start--Author:gaofeng  Date:2014-01-24 for:新增首页风格  -->
            <tr>
                <td class="value"><input type="radio" value="sliding" name="indexStyle"/><span>Sliding云桌面</span></td>
            </tr>
            <!-- update-end--Author:longjb  Date:2013-03-15 for:新增首页风格  -->
            <tr>
                <td class="value"><input type="radio" value="ace" name="indexStyle"/><span>ACE平面风格</span></td>
            </tr>
        </table>
    </form>
</div>
<div id="changepassword" style="display:none">

    <input id="id" type="hidden" value="${user.id }">
    <table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
        <tbody>
        <tr>
            <td align="right" width="20%"><span class="filedzt">原密码:</span></td>
            <td class="value"><input id="password" type="password" value="" name="password" class="inputxt" datatype="*"
                                     errormsg="请输入原密码"/> <span class="Validform_checktip"> 请输入原密码 </span></td>
        </tr>
        <tr>
            <td align="right"><span class="filedzt">新密码:</span></td>
            <td class="value"><input type="password" value="" name="newpassword" class="inputxt"
                                     plugin="passwordStrength"
                                     datatype="*6-18" errormsg="密码至少6个字符,最多18个字符！"/> <span
                    class="Validform_checktip"> 密码至少6个字符,最多18个字符！ </span> <span class="passwordStrength"
                                                                                style="display: none;"> <b>密码强度：</b> <span>弱</span><span>中</span><span
                    class="last">强</span> </span></td>
        </tr>
        <tr>
            <td align="right"><span class="filedzt">重复密码:</span></td>
            <td class="value"><input id="newpassword" type="password" recheck="newpassword" datatype="*6-18"
                                     errormsg="两次输入的密码不一致！"> <span class="Validform_checktip"></span></td>
        </tr>
        </tbody>
    </table>
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
<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='plug-in/ace/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
    if ('ontouchstart' in document.documentElement) document.write("<script src='plug-in/ace/js/jquery.mobile.custom.js'>" + "<" + "/script>");

    function loadModule(title, url, target) {
        addTab(title, url);
//    $("#mainTitle").text(title);
//    $("#center").attr("src", url);
    }


    function logout() {
        bootbox.confirm("确认退出吗？", function (result) {
            if (result)
                location.href = "loginController.do?logout";
        });
    }
    function opendialog(title, url, target) {
        //$("#dialog").attr("src",url);
        bootbox.dialog({
            message: $("#changestylePanel").html(),
            title: title,
            buttons: {
                OK: {
                    label: "OK",
                    callback: function () {
                        var indexStyle = $('input[name="indexStyle"]:checked').val();
                        if (indexStyle == undefined || indexStyle == "") {
                            indexStyle = "ace";
                        }
                        var cssTheme = $('input[name="cssTheme"]:checked').val();
                        if (cssTheme == undefined) {
                            cssTheme = "";
                        }
                        var form = $("#formobj");//取iframe里的form
                        $.ajax({
                            url: form.attr('action'),
                            type: form.attr('method'),
                            data: "indexStyle=" + indexStyle,//+"&cssTheme="+cssTheme,
                            success: function (data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    bootbox.alert(msg);
                                } else {
                                    bootbox.alert(d.msg);
                                }
                            },
                            error: function (e) {
                                bootbox.alert("出错了哦");
                            }
                        });
                    }
                }, Cancel: {
                    label: "CLOSE",
                    callback: function () {
                        //alert('close');//$("#dialog").dialog("close");
                    }
                }
            }
        });

    }
    function changepass(title, url, target) {
        //$("#dialog").attr("src",url);
        bootbox.dialog({
            message: '<form id="formobj2"  action="userController.do?savenewpwd" name="formobj2" method="post">'
            + $("#changepassword").html() + '</form>',
            title: title,
            buttons: {
                OK: {
                    label: "OK",
                    callback: function () {
                        //alert('not implement');
                        $.ajax({
                            url: "userController.do?savenewpwd",
                            type: "post",
                            data: $('#formobj2').serialize(),// 要提交的表单 ,
                            success: function (data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    var msg = d.msg;
                                    bootbox.alert(msg);
                                } else {
                                    bootbox.alert(d.msg);
                                }
                            },
                            error: function (e) {
                                bootbox.alert("出错了哦");
                            }
                        });
                    }
                }, Cancel: {
                    label: "CLOSE",
                    callback: function () {
                        alert('close');//$("#dialog").dialog("close");
                    }
                }
            }
        });

    }
    function profile(title, url, target) {
        //$("#dialog").attr("src",url);
        bootbox.dialog({
            message: '<iframe width="100%" height="300px" src="' + url + '" style="border:1px #fff solid; background:#CCC;"></iframe>',
            title: title,
            buttons: {
                OK: {
                    label: "OK"
                }, Cancel: {
                    label: "CLOSE"
                }
            }
        });

    }
    function clearLocalstorage() {
        var storage = $.localStorage;
        if (!storage)
            storage = $.cookieStorage;
        storage.removeAll();
        //bootbox.alert( "浏览器缓存清除成功!");
        alertTipTop("浏览器缓存清除成功!", "10%");
    }
</script>
<script src="plug-in/ace/js/bootstrap.js"></script>
<script src="plug-in/ace/js/bootbox.js"></script>

<script src="plug-in/ace/js/jquery-ui.js"></script>
<script src="plug-in/ace/js/jquery.ui.touch-punch.js"></script>
<!-- ace scripts -->
<script src="plug-in/ace/js/ace/elements.scroller.js"></script>
<script src="plug-in/ace/js/ace/elements.colorpicker.js"></script>
<script src="plug-in/ace/js/ace/elements.fileinput.js"></script>
<script src="plug-in/ace/js/ace/elements.typeahead.js"></script>
<script src="plug-in/ace/js/ace/elements.wysiwyg.js"></script>
<script src="plug-in/ace/js/ace/elements.spinner.js"></script>
<script src="plug-in/ace/js/ace/elements.treeview.js"></script>
<script src="plug-in/ace/js/ace/elements.wizard.js"></script>
<script src="plug-in/ace/js/ace/elements.aside.js"></script>
<script src="plug-in/ace/js/ace/ace.js"></script>
<script src="plug-in/ace/js/ace/ace.ajax-content.js"></script>
<script src="plug-in/ace/js/ace/ace.touch-drag.js"></script>
<script src="plug-in/ace/js/ace/ace.sidebar.js"></script>
<script src="plug-in/ace/js/ace/ace.sidebar-scroll-1.js"></script>
<script src="plug-in/ace/js/ace/ace.submenu-hover.js"></script>
<script src="plug-in/ace/js/ace/ace.widget-box.js"></script>
<script src="plug-in/ace/js/ace/ace.settings.js"></script>
<script src="plug-in/ace/js/ace/ace.settings-rtl.js"></script>
<script src="plug-in/ace/js/ace/ace.settings-skin.js"></script>
<script src="plug-in/ace/js/ace/ace.widget-on-reload.js"></script>
<script src="plug-in/ace/js/ace/ace.searchbox-autocomplete.js"></script>


<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript" src="plug-in/accordion/js/leftmenu.js"></script>
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>
<%--<script src="plug-in/tools/jquery.ba-resize.js"></script>--%>
<script>
    //  $('.maintabs').width($(document.body).width()-190);
    jQuery(function ($) {
        $("#maintabs").tabs({
            fit: true
        });
        $('#sidebar').height($(window).height() - 130);
        $('#page-content-area').height($(window).height() - 130);
        $('iframe').height($(window).height() - 170 < 600 ? 600 : $(window).height() - 170);
        $('#maintabs').tabs({
            width: 'auto',
            height: $("#maintabs").parent().height()
        });

        $(window).resize(function () {
            $('#sidebar').height($(window).height() - 130);
            $('#page-content-area').height($(window).height() - 130);
            $('iframe').height($(window).height() - 170 < 600 ? 600 : $(window).height() - 170);
            var title = $('.tabs-selected').text();
            if (title == '首页') {
                $('iframe').attr('src', $('iframe').attr('src'));
            }

            $('#maintabs').tabs({
                width: 'auto',
                height: $("#maintabs").parent().height()
            });
        });
//        $("#maintabs").tabs({
//            fit: true
//        });
//        $(window).resize(function(){
//            var h = $('#page-content-area').width();
//            $('#maintabs').width(h);
//            $('#tabiframe').attr('width',h);
//            var currTab =  $('#maintabs').tabs('getSelected'); //获得当前tab
//            var url = $(currTab.panel('options').content).attr('src');
//            var title = $('.tabs-selected').text();
//            if (title == '首页') {
//                url = 'loginController.do?home';
//            }
//            $('#maintabs').tabs('update', {
//                tab : currTab,
//                options : {
//                    content : createFrame(url)
//                }
//            });
//        })
        // 刷新
        $('#mm-tabupdate').click(function () {
            var currTab = $('#maintabs').tabs('getSelected');
            var url = $(currTab.panel('options').content).attr('src');
            var title = $('.tabs-selected').text();
            if (title == '首页') {
                url = 'loginController.do?home';
            }
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
        $("#center").css("height","99%");

        /**
         * 标签页选择时自动刷新列表
         */
        function onTabsSel() {
            $('#maintabs').tabs({
                onSelect: function (title, index) {
                    var iframeObj = $("iframe")[index];
                    $(iframeObj).contents().find(".easyui-datagrid").each(function () {
                        iframeObj.contentWindow.publicDatagrid('#' + $(this).attr('id'), 'reload');
                    });
                }
            });
        }
    });
</script>
</body>
</html>