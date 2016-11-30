<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tScEmpList" checkbox="false" fitColumns="false" title="职员" checkOnSelect="true"
                    actionUrl="tScEmpController.do?datagrid" idField="id" fit="true" colConfig="false"
                    queryMode="group" onLoadSuccess="loadsuccess">
            <t:dgCol title="主键" field="id"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人名称" field="createName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称" field="createBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="创建日期" field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人名称" field="updateName"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称" field="updateBy"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="更新日期" field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="名称" field="name"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="编号" field="number"   query="true" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="分类ID" field="parentID"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="助记码" field="shortNumber" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="简称" field="shortName"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="部门" field="deptID"    queryMode="single"  dictionary="T_SC_Department,id,Name "  width="120"></t:dgCol>
            <t:dgCol title="职员类别" field="empGroup"    queryMode="single"  dictionary="SC_EMPLOYEE_TYPE" width="120"></t:dgCol>
            <t:dgCol title="性别" field="gender"    queryMode="single"  dictionary="SC_GENDER" width="120"></t:dgCol>
            <t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="电子邮件" field="email"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="QQ号" field="ciqNumber"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="联系地址" field="address"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="联系电话" field="phone"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="手机号码" field="mobilePhone"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="身份证号码" field="identify"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="身份证地址" field="idAddress"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="职务" field="duty"    queryMode="single"  dictionary="SC_DUTY_TYPE" width="120"></t:dgCol>
            <t:dgCol title="文化程度" field="degree"    queryMode="single"  dictionary="SC_CULTURE_TYPE" width="120"></t:dgCol>
            <t:dgCol title="毕业院校" field="school"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="学习专业" field="expertise"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="入职日期" field="hireDate" formatter="yyyy-MM-dd"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="离职日期" field="leaveDate" formatter="yyyy-MM-dd"   queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="开户银行" field="bank"    queryMode="single"  dictionary="SC_BANK" width="120"></t:dgCol>
            <t:dgCol title="银行账号" field="bankNumber"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="启用信控" field="isCreditMgr"    queryMode="single"  dictionary="sf_01" width="120"></t:dgCol>
            <t:dgCol title="信用额度" field="creditAmount"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="禁用标记" field="disabled"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="逻辑删除" field="deleted"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="版本号" field="version"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="级次" field="level"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="引用次数" field="count"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="备注" field="note"    queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="用户id" field="userId"  hidden="true"  queryMode="single"   width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScEmpList.js"></script>
<script type="text/javascript">
    var flag = true;
    function loadsuccess(){
        if(flag) {
            $("input[name='name']").val('${name}');
            $('.icon-query').click();
            flag = false;
        }
    }
    $(document).ready(function () {
        //  //添加datagrid 事件 onDblClickRow
//        $("#tScEmpList").datagrid({
//
//            onDblClickRow: function (rowIndex, rowData) {
//                url = 'tScEmpController.do?goUpdate' + '&load=detail&id=' + rowData.id;
//                createdetailwindow('查看', url, 1050, 650);
//
//            }
//        });
        //给时间控件加上样式
            $("#tScEmpListtb").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScEmpListtb").find("input[name='updateDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScEmpListtb").find("input[name='birthday']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScEmpListtb").find("input[name='hireDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
            $("#tScEmpListtb").find("input[name='leaveDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd'});
            });
    });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tScEmpController.do?upload', "tScEmpList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tScEmpController.do?exportXls", "tScEmpList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tScEmpController.do?exportXlsByT", "tScEmpList");
    }
    var popJon;
    var orgId;
    var isPop = false;
    function getSelectionData() {
        var rows = $('#tScEmpList').datagrid('getSelections');
        if (rows.length > 0) {
            isPop =true ;
            return rows[0];

        } else {
            parent.$.messager.show({
                title: '提示消息',
                msg: '请选择信息',
                timeout: 2000,
                showType: 'slide'
            });
            return '';
        }
    }

</script>