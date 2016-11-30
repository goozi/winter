<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户信息</title>
    <t:base type="jquery,easyui,tools"></t:base>
    <%--update-start--Author:zhangguoming  Date:20140825 for：添加组织机构combobox多选的处理方法--%>
    <script>
        function setOrgIds() {
//            var orgIds = $("#orgSelect").combobox("getValues");
                var orgIds = $("#orgSelect").combotree("getValues");
                $("#orgIds").val(orgIds);
        }
        $(function () {
            <%--if(${currLoginUser.userName == "programmer"}){--%>
            $("#orgSelect").combotree({
                onChange: function (n, o) {
                    if ($("#orgSelect").combotree("getValues") != "") {
                        $("#orgSelect option").eq(1).attr("selected", true);
                    } else {
                        $("#orgSelect option").eq(1).attr("selected", false);
                    }
                }
            });
            <%--$("#orgSelect").combobox("setValues", ${orgIdList});--%>
            var id = $("#id").val();
            if(id){
                $("#orgSelect").combotree("setValues", ${orgIdList});
            }
            <%--$('#orgSelect').combotree("setValue", '${currDepartId}');--%>

            $('#sonSelect').combobox({
                onChange: function (n, o) {
                    $('#sonId').val(n);
                }
            })
            $('#sonSelect').combobox("setValue", '${user.sonCompanyId eq null ? "" : user.sonCompanyId}');

            var isMyself = $("select[name='isMyself']").val();
            if(isMyself == "1"){
                $("select[name='readType']").val(0);
                $("select[name='readType']").attr("disabled","disabled");
            } else {
                $("select[name='readType']").removeAttr("disabled");
            }

            $("select[name='isMyself']").attr("onChange","checkIsMyself();")
        });

        function checkIsMyself(){
            var isMyself = $("select[name='isMyself']").val();
            if(isMyself == "1"){
                $("select[name='readType']").val(0);
                $("select[name='readType']").attr("disabled","disabled");
            } else {
                $("select[name='readType']").removeAttr("disabled");
            }
        }
    </script>
    <%--update-end--Author:zhangguoming  Date:20140825 for：添加组织机构combobox多选的处理方法--%>
</head>
<body style="overflow-y: hidden" scroll="no">
<%--update-start--Author:zhangguoming  Date:20140825 for：格式化页面代码 并 添加组织机构combobox多选框--%>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userController.do?saveUser"
             beforeSubmit="setOrgIds" windowType="tab">
    <input id="id" name="id" type="hidden" value="${user.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right" width="15%" nowrap>
                <label class="Validform_label"> <t:mutiLang langKey="common.username"/>: </label>
            </td>
            <td class="value" width="85%">
                <c:if test="${user.id!=null }"> ${user.userName } </c:if>
                <c:if test="${user.id==null }">
                    <input id="userName" class="inputxt" name="userName" validType="t_s_base_user,userName,id"
                           value="${user.userName }" datatype="s2-160"/>
                    <span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to10"/></span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td align="right" width="10%" nowrap><label class="Validform_label"> <t:mutiLang
                    langKey="common.real.name"/>: </label></td>
            <td class="value" width="10%">
                <input id="realName" class="inputxt" name="realName" value="${user.realName }" datatype="s2-160">
                <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
            </td>
        </tr>
        <c:if test="${user.id==null }">
            <tr>
                <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.password"/>: </label></td>
                <td class="value">
                    <input type="password" class="inputxt" value="" name="password" plugin="passwordStrength"
                           datatype="*6-18"
                           />
                    <span class="passwordStrength" style="display: none;">
                        <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
                </td>
            </tr>
            <tr>
                <td align="right"><label class="Validform_label"> <t:mutiLang
                        langKey="common.repeat.password"/>: </label></td>
                <td class="value">
                    <input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password"
                           datatype="*6-18" errormsg="两次输入的密码不一致！">
                    <span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
                </td>
            </tr>
        </c:if>
            <%--<c:if test='${currLoginUser.userName == "programmer"}'>--%>
        <tr <c:if test='${currLoginUser.userName != "programmer"} and ${currLoginUser.userName != "scadmin"}'> style="display: none" </c:if>>
            <td align="right"><label class="Validform_label"> 部门: </label></td>
            <td class="value">
                    <%--update-start--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree--%>
                    <%--<select class="easyui-combobox" data-options="multiple:true, editable: false" id="orgSelect" datatype="*">--%>
                <select class="easyui-combotree"
                        data-options="url:'departController.do?getOrgTree', width:155, multiple:false, cascadeCheck:false, readonly:true"
                        id="orgSelect" name="orgSelect" datatype="select1">
                        <%--update-end--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree--%>
                    <c:forEach items="${departList}" var="depart">
                        <option value="${depart.id }">${depart.departname}</option>
                    </c:forEach>
                </select>
                <input id="orgIds" name="orgIds" type="hidden">
                <span class="Validform_checktip"><t:mutiLang langKey="please.select.department"/></span>
            </td>
        </tr>
            <%--</c:if>--%>
        <%-- 以下注释代码属于监管单位代码，已经没必要--%>
<%--        <c:if test="${comSize > 0 ? true : false}">
            <tr>
                <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.soncompany"/>: </label>
                </td>
                <td class="value">
                        &lt;%&ndash;update-start--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree&ndash;%&gt;
                        &lt;%&ndash;<select class="easyui-combobox" data-options="multiple:true, editable: false" id="orgSelect" datatype="*">&ndash;%&gt;
                    <select class="easyui-combobox"
                            data-options=" multiple:false, width:155,cascadeCheck:false,editable: false"
                            id="sonSelect" name="sonSelect" datatype="select1">
                            &lt;%&ndash;update-end--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree&ndash;%&gt;
                        <c:forEach items="${comList}" var="com">
                            <option value="${com.id }">${com.name}</option>
                        </c:forEach>
                    </select>

                    <input id="sonId" name="sonId" type="hidden" value="${user.sonId}">
                    <span class="Validform_checktip"><t:mutiLang langKey="please.select.soncompany"/></span>
                </td>
            </tr>
        </c:if>--%>
        <tr ${(currLoginUser.userName != "programmer" and currLoginUser.userName != "scadmin") ? "style=\"display:none;\"":""}>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.role"/>: </label></td>
            <td class="value" nowrap>
                <input name="roleid" type="hidden" value="${id}" id="roleid">
                <input name="roleName" class="inputxt" value="${roleName }" id="roleName" readonly="readonly"
                       datatype="*"/>
                <t:choose hiddenName="roleid" hiddenid="id" url="userController.do?roles" name="roleList"
                          icon="icon-search" title="common.role.list" textname="roleName" isclear="true"
                          isInit="true"></t:choose>
                <span class="Validform_checktip"><t:mutiLang langKey="role.muti.select"/></span>
            </td>
        </tr>
        <tr>
            <td align="right" nowrap><label class="Validform_label"> <t:mutiLang langKey="common.phone"/>: </label></td>
            <td class="value">
                <input class="inputxt" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确"
                       ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.tel"/>: </label></td>
            <td class="value">
                <input class="inputxt" name="officePhone" value="${user.officePhone}" datatype="po" errormsg="办公室电话不正确"
                       ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
            <td class="value">
                <input class="inputxt" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!"
                       ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr ${currLoginUser.userName != "programmer" ? "style=\"display:none;\"":""}>
            <td align="right"><label class="Validform_label"> 项目代码: </label></td>
            <td class="value">
                <t:dictSelect field="projectCode" defaultVal="${user.projectCode}" typeGroupCode="PROJECT_CODE" hasLabel="false"/>
                <span class="Validform_checktip">当前开发项目代码，如不清楚请咨询管理员</span>
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> 是否只看自己创建数据: </label></td>
            <td class="value">
                <t:dictSelect field="isMyself" defaultVal="${user.isMyself}" showDefaultOption="false" typeGroupCode="sf_01" hasLabel="false"/>
                <span class="Validform_checktip">控制登录用户是否只查看自己创建的数据，如不清楚请咨询管理员</span>
            </td>
        </tr>
        <tr>
            <td align="right"><label class="Validform_label"> 是否查看下级分支机构数据: </label></td>
            <td class="value">
                <t:dictSelect field="readType" defaultVal="${user.readType}" showDefaultOption="false" typeGroupCode="sf_01" hasLabel="false"/>
                <span class="Validform_checktip">控制登录用户能否查看下级分支机构的数据，如不清楚请咨询管理员</span>
            </td>
        </tr>
    </table>
</t:formvalid>
<%--update-end--Author:zhangguoming  Date:20140825 for：格式化页面代码 并 添加组织机构combobox多选框--%>
</body>
</html>