<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <table id="tScAccountConfigList" class="easyui-datagrid" data-options="url:'tScAccountConfigController.do?loadSelectDatagrid&field=id,dbDescription,dbKey,accountStartDate,companyName,contact,openState,closeState',fit:true,pagination:false,method:'get',rownumbers:true,singleSelect:true" onDblClick="dbClickView">
            <thead>
            <tr nowrap="nowrap">
                <th data-options="field:'id',align:'center',hidden:true">编号</th>
                <th data-options="field:'dbDescription',align:'center',width:100">账套名称</th>
                <th data-options="field:'dbKey',align:'center',width:100">实体库</th>
                <th data-options="field:'accountStartDate',align:'center',width:100,formatter:showDate">账套启用年月</th>
                <th data-options="field:'companyName',align:'center',width:100">公司名称</th>
                <th data-options="field:'contact',align:'center',width:100">联系人</th>
                <th data-options="field:'openState',align:'center',width:100,formatter:showOpenState">开账状态</th>
                <%--<th data-options="field:'closeState',align:'center',width:100,formatter:showCloseState">结账状态</th>--%>
            </tr>
            </thead>
        </table>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tBiStockList.js"></script>
<script type="text/javascript">
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}');
            $('.icon-query').click();
            flag = false;
        }
    }
    function dbClickView(rowIndex, rowData) {
        $(".ui_state_highlight").trigger("click");//双击行模拟点击确认
    }
    $(document).ready(function () {
        //给时间控件加上样式
        $("#tScAccountConfigListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#tScAccountConfigListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

    });
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tScAccountConfigList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            /*parent.$.messager.show({
                title: '提示消息',
                msg: '请选择信息',
                timeout: 2000,
                showType: 'slide'
            });*/
            return;
        }
    }
    //格式化显示账套年月
    function showDate(v, r, i) {
        if (v == undefined || v=="") {
            return "";
        }
        var timeArr = v.split("T");
        if (timeArr.length==1){
            timeArr = v.split(" ");
        }
        var d = timeArr[0].split("-");
        var t = timeArr[1].split(":");
        var unixTimestamp = new Date(d[0], (d[1] - 1), d[2], t[0], t[1], t[2]);
        if (unixTimestamp.toLocaleString() == 'Invalid Date')
            return '';
        var year = unixTimestamp.getFullYear();
        var month = unixTimestamp.getMonth() + 1;
        if (month < 10) {
            month = '0' + month;
        }
        var day = unixTimestamp.getDate();
        if (day < 10) {
            day = '0' + day;
        }
        var hour = (unixTimestamp.getHours() < 10) ? '0' + unixTimestamp.getHours()
                : unixTimestamp.getHours();
        var minute = (unixTimestamp.getMinutes() < 10) ? '0' + unixTimestamp
                .getMinutes() : unixTimestamp.getMinutes();
        var second = (unixTimestamp.getSeconds() < 10) ? '0' + unixTimestamp
                .getSeconds() : unixTimestamp.getSeconds();
        if (v == undefined)
            return "";
        return year + '-' + month;// + '-' + day;
    }
    //显示开启状态
    function showOpenState(v, r, i) {
        if (v == undefined || v == "") {
            return "";
        }
        return v=="1"?"是":"否";
    }
    //显示结账状态
    function showCloseState(v, r, i) {
        if (v == undefined || v == "") {
            return "";
        }
        return v=="1"?"已结账":"未结账";
    }
</script>