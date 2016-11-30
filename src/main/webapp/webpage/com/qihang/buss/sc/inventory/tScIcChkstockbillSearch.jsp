<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>盘点单条件筛选</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#tt').tabs({
                onSelect: function (title) {
                    $('#tt .panel-body').css('width', 'auto');
                }
            });
            $(".tabs-wrap").css('width', '100%');
            $("#tree1").tree({
                url:'tBiStockController.do?getStockTree&isClose=1',
                checkbox:true
            })
            $("#tree2").tree({
                url:'tScIcitemController.do?getIcItemTree&isClose=1',
                checkbox:true
            })

            $("#deptName").keypress(function (e) {
                if (e.keyCode == 13) {
                    selectDeptDialog();
                }
            });
            $("#empName").keypress(function(e){
                if (e.keyCode == 13) {
                    selectEmpDialog();
                }
            })

            $("#deptName").blur(function(){
                checkInput($("#deptId"), $("#deptName"));
            })
            $("#empName").blur(function(){
                checkInput($("#empId"), $("#empName"));
            })
        });

        function changeRadio(){
            var v = $("input[name='calType']:checked").val();
            if(v == 1){
                $("#date").removeAttr("readonly");
                $("#date").css("background-color","white");
                $("#date").attr("onClick","WdatePicker()");
            } else {
                $("#date").attr("readonly","readonly");
                $("#date").css("background-color","rgb(226,226,226)");
                $("#date").removeAttr("onClick");
            }
        }

        function checkMaxNum(){
            var maxNum = $("#maxNum").val();
            if(parseFloat(maxNum) > 500){
                tip("最大记录量不得超过500");
                $("#maxNum").val(500);
            }else{
                $("#maxNum").val(Math.ceil(parseFloat(maxNum)));
            }
        }

        function getSelectionData(){
            var info = {};
            var calType = $("input[name='calType']:checked").val();//盘点类型
            info.calType = calType;
            if(calType == 1){
                //截止日期
                var date = $("#date").val();
                info.date = date;
            }else{
                info.date = "";
            }
            //仓库
            var stockInfo = $("#tree1").tree("getChecked");
            var StockIdInfo = "";
            for(var i = 0 ; i < stockInfo.length ; i++){
                var stock = stockInfo[i];
                var stockId = stock.id;
                var node = $("#tree1").tree("find",stockId);
                var isLeaf = $("#tree1").tree("isLeaf",node.target);
                if(isLeaf){
                    StockIdInfo += stockId+",";
                }
            }
            if(!StockIdInfo){
                tip("请选择仓库信息");
                return;
            }
            info.stockInfo = StockIdInfo;
            //商品
            var itemInfo = $("#tree2").tree("getChecked");
            var itemIdInfo = "";
            for(var i = 0 ; i < itemInfo.length ; i++){
                var item = itemInfo[i];
                var itemId = item.id;
                var node = $("#tree2").tree("find",itemId);
                var isLeaf = $("#tree2").tree("isLeaf",node.target);
                if(isLeaf){
                    itemIdInfo += itemId+",";
                }
            }
            info.itemInfo = itemIdInfo;
            //最大数据量
            var maxNum = $("#maxNum").val();
            info.maxNum = maxNum;
            //s是否零库存
            var isZero = $("input[name='isZero']:checked").val();
            if(isZero){
                info.isZero = isZero;
            }else{
                info.isZero = 0;
            }
            //经办人
            var empId = $("#empId").val();
            if(!empId){
                tip("请选择经办人");
                return;
            }
            info.empId = empId;
            //部门
            var deptId = $("#deptId").val();
            var deptName = $("#deptName").val();
            if(!deptId || !deptName){
                tip("请选择部门");
                return;
            }
            info.deptId = deptId;

            return info;
        }

        //选择职员
        function selectEmpDialog(){
            var empName = $("#empName").val();
            var url = "tScEmpController.do?goselectdeptIdNameDialog&name="+empName;
            var width = 885;
            var height = 500;
            var title = "经办人";
            $.dialog({
                content: 'url:' + url,
                title: title,
                lock: true,
                zIndex: 800,
                height: height,
                cache: false,
                width: width,
                opacity: 0.3,
                button: [{
                    name: '确定',
                    callback: function () {
                        iframe = this.iframe.contentWindow;
                        var item = iframe.getSelectionData();
                        $("#empName").val(item.name);
                        $("#empId").val(item.id);
                        setValOldIdAnOldName($("#empName"), item.id, item.name);

                        $("#deptName").val(item.deptIdName);
                        $("#deptId").val(item.deptID);
                        setValOldIdAnOldName($("#deptName"), item.deptID, item.deptIdName);
                    },
                    focus: true
                }, {
                    name: '取消',
                    callback: function () {
                    }
                }]
            }).zindex();
        }

        //选择部门
        function selectDeptDialog(){
            var deptName = $("#deptName").val();
            var url = 'tScDepartmentController.do?goSelectDialog&name=' + deptName;
            var width = 800;
            var height = 500;
            var title = "部门";
            $.dialog({
                content: 'url:' + url,
                title: title,
                lock: true,
                zIndex: 800,
                height: height,
                cache: false,
                width: width,
                opacity: 0.3,
                button: [{
                    name: '确定',
                    callback: function () {
                        iframe = this.iframe.contentWindow;
                        var item = iframe.getSelectionData();
                        $("#deptName").val(item.name);
                        $("#deptId").val(item.id);
                        setValOldIdAnOldName($("#deptName"), item.id, item.name);
                    },
                    focus: true
                }, {
                    name: '取消',
                    callback: function () {
                    }
                }]
            }).zindex();
        }
        /**
         * 存放旧值
         * 弹出框回传设置值
         */
        function setValOldIdAnOldName(inputid, oldId, oldName) {
            inputid.attr("oldid", oldId);
            inputid.attr("oldname", oldName);
        }

        //弹出界面数据赋值
        function checkInput(checkId, checkNameId) {
            if (checkNameId.val() != undefined && checkNameId.val() != "") {
                if (checkNameId.attr("oldid") != undefined && checkNameId.attr("oldid") != "") {
                    checkNameId.val(checkNameId.attr("oldname"));
                    checkId.val(checkNameId.attr("oldid"));
                } else {
                    checkNameId.val("");
                    checkId.val("");
                }
            } else {
                checkNameId.attr("oldid", "");
                checkNameId.attr("oldname", "");
                checkId.val("");
            }
        }
    </script>

    <style>
        /** 页脚样式  **/
        body {
            position: absolute;
            width: 100%;
            height: 100%;
        }

        .footlabel {
            float: left;
            margin-left: 35px;
        }

        .footspan {
            float: left;
            margin-left: 5px;
            margin-right: 5px;
            font-weight: bold;
            color: grey;
            margin-bottom: 5px;
            text-decoration: underline;
        }
    </style>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" windowType="dialog">

    <table cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td class="value" style="line-height: 24px">
                <div style="text-align: left">
                    <input name="calType" type="radio" style="width: 20px" value="0" onclick="changeRadio()"; checked="checked">即时库存
                </div>
            </td>
        </tr>
        <tr>
            <td class="value" style="line-height: 24px">
                <div style="text-align: left">
                    <input name="calType" type="radio" value="1" onclick="changeRadio()" style="width: 20px">截止日期
                    <input id="date" name="date" type="text" value="${date}" readonly="readonly" style="width: 150px" class="Wdate" >
                </div>
            </td>
        </tr>
    </table>
    <div style="width: auto;height: 200px;">
            <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
        <div style="width:400px;height:1px;"></div>
        <div id="tt" class="easyui-tabs" style="width:400px;height:250px;">
            <div title="仓库条件" data-options="iconCls:'icon-search'" style="padding:20px">
                <ul id="tree1"></ul>
            </div>
            <div title="商品条件" data-options="iconCls:'icon-search'" style="padding:20px">
                <ul id="tree2"></ul>
            </div>
            <div title="其他条件" data-options="iconCls:'icon-search'" style="padding:20px">
                <input type="hidden" id="empId">
                <input type="hidden" id="deptId">
                <table cellpadding="0" cellspacing="0" class="formtable">
                    <tr>
                        <td class="value" align="right">
                            <label class="Validform_label">最大记录数:</label>
                        </td>
                        <td class="value" style="line-height: 24px">
                            <div style="text-align: left">
                                <input id="maxNum" name="maxNum" type="text" style="width: 95px;border: 1px solid rgb(226,226,226)" value="300" onChange="checkMaxNum()">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="value" style="line-height: 24px">
                            <div style="text-align: center">
                                <input id="isZero" name="isZero" type="checkbox" value="1" checked="checked" style="width: 20px">盘点账面数量为零的数据
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="value" align="right">
                            <label class="Validform_label">经办人:</label>
                        </td>
                        <td class="value" style="line-height: 24px">
                            <div style="text-align: left">
                                <input id="empName" name="empName" class="inputxt popup-select" type="text" style="width: 95px;border: 1px solid rgb(226,226,226)">
                                <span class="Validform_checktip">*</span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="value" align="right">
                            <label class="Validform_label">部门:</label>
                        </td>
                        <td  class="value" style="line-height: 24px">
                            <div style="text-align: left">
                                <input id="deptName" name="deptName" class="inputxt popup-select" type="text" style="width: 95px;border: 1px solid rgb(226,226,226)">
                                <span class="Validform_checktip">*</span>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <%--<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">--%>
            <%--<t:tab href="tScIcChkstockbillController.do?tScIcChkstockbillentryList&id=${tScIcChkstockbillPage.id}"--%>
                   <%--icon="icon-search" title="盘点单明细" id="tScIcChkstockbillentry"></t:tab>--%>
        <%--</t:tabs>--%>
    </div>
</t:formvalid>
</body>