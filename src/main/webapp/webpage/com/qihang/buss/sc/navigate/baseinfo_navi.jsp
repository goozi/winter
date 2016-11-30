<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    * {
        font-size: 12px;
        font-family: Tahoma, Verdana, 新宋体
    }
</style>
<style>
    * {
        font-size: 12px;
        font-family: Tahoma, Verdana, 新宋体
    }

    .navigate {
        float: left;
        margin-left: 40px;
    }

    .row {
        margin-top: 2%;
        margin-left: 20%;
        text-align: center;
        float: left;
        width: 80%
    }

    .arrow {
        margin-top: 6%;
        margin-right: 10px;
    }

    .navigate a {
        vertical-align: middle;
        display: block;
        line-height: 82px;
        padding-right: 10px;
        padding-bottom: 10px;
        width: 82px;
        height: 82px;

    }

    a.one {
        background: url('plug-in/buss/scm/home/u14.png') no-repeat;
    }

    a.one:hover {
        background: url('plug-in/buss/scm/home/u14_mouseOver.png');
    }

    a.two {
        background: url('plug-in/buss/scm/home/u20.png') no-repeat;
    }

    a.two:hover {
        background: url('plug-in/buss/scm/home/u20_mouseOver.png');
    }

    a.three {
        background: url('plug-in/buss/scm/home/u12.png') no-repeat;
    }

    a.three:hover {
        background: url('plug-in/buss/scm/home/u12_mouseOver.png');
    }

    a.four {
        background: url('plug-in/buss/scm/home/u16.png') no-repeat;
    }

    a.four:hover {
        background: url('plug-in/buss/scm/home/u16_mouseOver.png');
    }

    a.five {
        background: url('plug-in/buss/scm/home/u12.png') no-repeat;
    }

    a.five:hover {
        background: url('plug-in/buss/scm/home/u12_mouseOver.png');
    }

    a.six {
        background: url('plug-in/buss/scm/home/u16.png') no-repeat;
    }

    a.six:hover {
        background: url('plug-in/buss/scm/home/u16_mouseOver.png');
    }

    a.seven {
        background: url('plug-in/buss/scm/home/u14.png') no-repeat;
    }

    a.seven:hover {
        background: url('plug-in/buss/scm/home/u14_mouseOver.png');
    }

    a.eight {
        background: url('plug-in/buss/scm/home/u16.png') no-repeat;
    }

    a.eight:hover {
        background: url('plug-in/buss/scm/home/u16_mouseOver.png');
    }

    a.nine {
        background: url('plug-in/buss/scm/home/u20.png') no-repeat;
    }

    a.nine:hover {
        background: url('plug-in/buss/scm/home/u20_mouseOver.png');
    }

    a.ten {
        background: url('plug-in/buss/scm/home/u20.png') no-repeat;
    }

    a.ten:hover {
        background: url('plug-in/buss/scm/home/u20_mouseOver.png');
    }

    a.eleven {
        background: url('plug-in/buss/scm/home/u12.png') no-repeat;
    }

    a.eleven:hover {
        background: url('plug-in/buss/scm/home/u12_mouseOver.png');
    }

    a.twelve {
        background: url('plug-in/buss/scm/home/u20.png') no-repeat;
    }

    a.twelve:hover {
        background: url('plug-in/buss/scm/home/u20_mouseOver.png');
    }

    a.thirteen {
        background: url('plug-in/buss/scm/home/u16.png') no-repeat;
    }

    a.thirteen:hover {
        background: url('plug-in/buss/scm/home/u16_mouseOver.png');
    }

    .navigate img {
        margin-top: 19%;

    }

    .navigate span {
        margin-top: 5px;
        display: block;
        text-align: center;
        font-family: 'Arial Normal', 'Arial';
        font-weight: 400;
        font-style: normal;
        font-size: 13px;
        color: #333333;
        line-height: normal;
        padding-right: 10px;
    }
</style>


<%--<script type="text/javascript">--%>
<%--$(document).ready(function(){--%>
<%--$(".one").bind("click", function(){--%>
<%--var title = "客户";--%>
<%--var url = "tScOrganizationController.do?tScOrganization";--%>
<%--window.parent.addTab(title, url);--%>
<%--});--%>
<%--});--%>
<%--</script>--%>
<div style="float: left;overflow: auto">
    <div class="row">

        <%--<div class="navigate" ><a class="one"><img src="plug-in/buss/scm/baseinfo_navi/u86.png"></a><span >客  户</span></div>--%>
        <div class="navigate"><a class="one" onclick="window.parent.addTab('客户','tScOrganizationController.do?tScOrganization')"><img src="plug-in/buss/scm/baseinfo_navi/u86.png"></a><span>客  户</span></div>


        <%--<div class="navigate"><a class="two"><img src="plug-in/buss/scm/baseinfo_navi/u88.png"></a><span>供应商</span></div>--%>
        <div class="navigate"><a class="two" onclick="window.parent.addTab('供应商','tScSupplierController.do?tScSupplier')"><img src="plug-in/buss/scm/baseinfo_navi/u88.png"></a><span>供应商</span></div>


        <%--<div class="navigate"><a class="three"><img src="plug-in/buss/scm/baseinfo_navi/u90.png" style="padding-top: 5px"></a><span>物流公司</span></div>--%>
        <div class="navigate"><a class="three" onclick="window.parent.addTab('物流公司','tScLogisticalController.do?tScLogistical')"><img src="plug-in/buss/scm/baseinfo_navi/u90.png" style="padding-top: 5px"></a><span>物流公司</span></div>


        <%--<div class="navigate"><a class="four"><img src="plug-in/buss/scm/baseinfo_navi/u92.png"></a><span>商   品</span></div>--%>
        <div class="navigate"><a class="four" onclick="window.parent.addTab('商   品','tScIcitemController.do?tScIcitem')"><img src="plug-in/buss/scm/baseinfo_navi/u92.png"></a><span>商   品</span></div>

    </div>

    <div class="row">
        <%--<div class="navigate"><a class="five"><img src="plug-in/buss/scm/baseinfo_navi/u94.png"></a><span>职   员</span></div>--%>
        <div class="navigate"><a class="five" onclick="window.parent.addTab('职   员','tScEmpController.do?tScEmp')"><img src="plug-in/buss/scm/baseinfo_navi/u94.png"></a><span>职   员</span></div>

        <%--<div class="navigate"><a class="six"> <img src="plug-in/buss/scm/baseinfo_navi/u96.png"></a><span>部   门</span></div>--%>
        <div class="navigate"><a class="six" onclick="window.parent.addTab('部   门','tScDepartmentController.do?tScDepartment')"> <img src="plug-in/buss/scm/baseinfo_navi/u96.png"></a><span>部   门</span></div>

        <%--<div class="navigate"><a class="seven"><img src="plug-in/buss/scm/baseinfo_navi/u98.png"></a><span>仓   库</span></div>--%>
        <div class="navigate"><a class="seven" onclick="window.parent.addTab('仓   库','tBiStockController.do?tBiStock')"><img src="plug-in/buss/scm/baseinfo_navi/u98.png"></a><span>仓   库</span></div>

        <%--<div class="navigate"><a class="eight"><img src="plug-in/buss/scm/baseinfo_navi/u100.png"></a><span>单   位</span></div>--%>
        <div class="navigate"><a class="eight" onclick="window.parent.addTab('单   位','tScMeasureunitController.do?tScMeasureunit')"><img src="plug-in/buss/scm/baseinfo_navi/u100.png"></a><span>单   位</span></div>

    </div>
    <div class="row">
        <%--<div class="navigate"><a class="nine"><img src="plug-in/buss/scm/baseinfo_navi/u102.png"></a><span>收支项目</span></div>--%>
        <div class="navigate"><a class="nine" onclick="window.parent.addTab('收支项目','tScFeeController.do?tScFee')"><img src="plug-in/buss/scm/baseinfo_navi/u102.png"></a><span>收支项目</span></div>

        <%--<div class="navigate"><a class="ten"> <img src="plug-in/buss/scm/baseinfo_navi/u104.png"></a><span>结算账目</span></div>--%>
        <div class="navigate"><a class="ten" onclick="window.parent.addTab('结算账户','tScSettleacctController.do?tScSettleacct')"> <img src="plug-in/buss/scm/baseinfo_navi/u104.png"></a><span>结算账户</span></div>

        <div class="navigate"><a class="thirteen" onclick="window.parent.addTab('分支机构','departController.do?depart_sc')"><img src="plug-in/buss/scm/baseinfo_navi/u112.png"></a><span>分支机构</span></div>

        <%--<div class="navigate"><a class="eleven"><img src="plug-in/buss/scm/baseinfo_navi/u106.png"></a><span>辅助属性</span></div>--%>

        <%--<div class="navigate"><a class="twelve"><img src="plug-in/buss/scm/baseinfo_navi/u108.png"></a><span>辅助资料</span></div>--%>

    </div>
    <div class="row">
        <%--<div class="navigate"><a class="thirteen"><img src="plug-in/buss/scm/baseinfo_navi/u112.png"></a><span>分支机构</span></div>--%>
        <%--<div class="navigate"><a class="thirteen" onclick="window.parent.addTab('分支机构','departController.do?depart_sc')"><img src="plug-in/buss/scm/baseinfo_navi/u112.png"></a><span>分支机构</span></div>--%>

    </div>

</div>
