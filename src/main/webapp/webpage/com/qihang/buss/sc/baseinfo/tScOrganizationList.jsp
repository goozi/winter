<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="mm1" class="easyui-menu" style="width:120px;">
    <div onclick="addTree()" data-options="iconCls:'icon-add'"><font color="black" >添加</font></div>
    <div onclick="updateTree()" data-options="iconCls:'icon-edit'"><font color="black">编辑</font></div>
    <div onclick="Delete()" data-options="iconCls:'icon-remove'"><font color="black">移除</font></div>
</div>
<div class="easyui-layout" fit="true">
    <div data-options="region:'west',title:'客户分类',split:true" style="width:200px;">
        <ul id="tree" class="easyui-tree" data-options="url:'tScItemTypeController.do?getItemTypeTree&itemClassId=01'"></ul>
    </div>
    <input id="itemClassId" name="itemClassId" value="01" type="hidden"/>

    <div region="center" style="padding:1px;">
        <t:datagrid name="tScOrganizationList"  fitColumns="false" title="客户" tableName="t_sc_organization" colConfig="false"
                    actionUrl="tScOrganizationController.do?datagrid" idField="id" fit="true" isSon="false"
                    queryMode="group" onDblClick="dbClick">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="160"></t:dgCol>
            <t:dgCol title="简称" field="shortname"   query="true" queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="助记码" field="shortnumber"   query="true" queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="法人代表" field="corperate"    queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="联系人" field="contact"    queryMode="single"   width="70"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilephone"    queryMode="single"   width="70"></t:dgCol>
            <t:dgCol title="电话" field="phone"    queryMode="single"   width="70"></t:dgCol>
            <t:dgCol title="传真" field="fax"    queryMode="single"   width="70"></t:dgCol>
            <t:dgCol title="是否是经销商" field="isDealer" queryMode="single" replace="否_0,是_1" width="70"></t:dgCol>
            <t:dgCol title="联系地址" field="address"    queryMode="single"   width="180"></t:dgCol>
            <t:dgCol title="区域" field="regionid"    queryMode="single"  dictionary="SC_REGION" width="65"></t:dgCol>
            <t:dgCol title="上级经销商" field="parentDealerName"   queryMode="single"   width="165"></t:dgCol>
            <t:dgCol title="客户分类" field="typeid"    queryMode="single"  dictionary="SC_TYPE_ID" width="70"></t:dgCol>
            <t:dgCol title="默认业务员" field="defaultoperator"  dictionary="t_sc_emp,id,name"  queryMode="single"   width="70"></t:dgCol>
            <t:dgCol title="营业执照号" field="licence"   query="true" queryMode="single"   width="80"></t:dgCol>
            <t:dgCol title="城市" field="city"    queryMode="single"  dictionary="SC_TYPE" width="65"></t:dgCol>
            <t:dgCol title="邮政编码" field="postalcode"    queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="电子邮件" field="email"    queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="公司主页" field="homepage"    queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="结算单位" field="settlementName"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="开户银行" field="bank"    queryMode="single"  dictionary="SC_BANK" width="70"></t:dgCol>
            <t:dgCol title="银行账号" field="banknumber"    queryMode="single"   width="70"></t:dgCol>
            <t:dgCol title="qq号" field="ciqnumber"    queryMode="single"   width="50"></t:dgCol>
            <t:dgCol title="行业" field="trade"    queryMode="single"  dictionary="SC_TRADE" width="65"></t:dgCol>
            <t:dgCol title="送货方式" field="delivertype"    queryMode="single"  dictionary="SC_DELIVER_TYPE" width="65"></t:dgCol>
            <t:dgCol title="启用信控" field="iscreditmgr"    queryMode="single"  dictionary="sf_01" width="65"></t:dgCol>
            <t:dgCol title="信用额度" field="creditamount"    queryMode="single"   width="65"></t:dgCol>
            <t:dgCol title="分类id" field="parentid"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note" hidden="true"   queryMode="single"   width="200"></t:dgCol>
            <t:dgCol title="禁用标记" field="disable" replace="启用_0,禁用_1"   queryMode="single" width="120"></t:dgCol>
            <%--<t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>--%>
            <%--<t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>--%>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" exp="count#eq#0" url="tScOrganizationController.do?doDel&id={id}"/>
            <t:dgToolBar title="新增" icon="new-icon-add" url="tScOrganizationController.do?goAdd"
                        onclick="goAdd()" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="new-icon-edit" url="tScOrganizationController.do?goUpdate"
                         onclick="goUpdate()" funname="update"></t:dgToolBar>
            <%--<t:dgToolBar title="批量删除" icon="icon-remove" url="tScOrganizationController.do?doBatchDel"
                         funname="deleteALLSelect"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="new-icon-view" url="tScOrganizationController.do?goUpdate"
                         funname="detail" width="950"></t:dgToolBar>
            <t:dgToolBar title="复制" icon="new-icon-copy" url="tScOrganizationController.do?goUpdate"
                         funname="fcopy" windowType="tab"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="new-icon-excel-in" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="new-icon-excel-out" funname="ExportXls"></t:dgToolBar>
            <%--<t:dgToolBar title="模板下载" icon="icon-download" funname="ExportXlsByT"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" icon="new-icon-print"
                         funname="CreateFormPage('客户', '#tScOrganizationList')"></t:dgToolBar>
            <%--<t:dgToolBar title="分类添加" icon="icon-add" windowType="dialog" url="tScItemTypeController.do?goAdd&itemClassId=01" funname="addTree"></t:dgToolBar>
            <t:dgToolBar title="分类编辑" icon="icon-edit" windowType="dialog" url="tScItemTypeController.do?goUpdate&itemClassId=01" funname="updateTree" ></t:dgToolBar>
            <t:dgToolBar title="分类删除" icon="icon-remove"  funname="Delete"></t:dgToolBar>--%>
            <t:dgToolBar title="禁用" icon="icon-lock" onclick="changeSta(1)"></t:dgToolBar>
            <t:dgToolBar title="启用" icon="icon-active" onclick="changeSta(0)"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScOrganizationList.js"></script>
<script type="text/javascript">
    function dbClick(rowIndex, rowData) {
        url = 'tScOrganizationController.do?goUpdate' + '&load=detail&id=' + rowData.id;

        createdetailwindow('查看', url, null, null,"tab");
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tScOrganizationList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScOrganizationController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, null, null,'tab');
//
//            }
//
//        });
        //给时间控件加上样式
            $("#tScOrganizationListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScOrganizationListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScOrganizationController.do?upload', "tScOrganizationList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScOrganizationController.do?exportXls", "tScOrganizationList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScOrganizationController.do?exportXlsByT", "tScOrganizationList");
    }
</script>