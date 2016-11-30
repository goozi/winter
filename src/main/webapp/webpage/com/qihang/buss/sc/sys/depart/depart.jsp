<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>分支机构信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {
//                alert(node.text);
                console.log(node);
                changeBillNo(node.id);
            }
        });
        //名称
        $("#departname").blur(function () {
            var name = $("#departname").val();
            if(name!=null&&name!="") {
                $.ajax({
                    type: 'POST',
                    url: 'tScIcitemController.do?getShortName',
                    async: false,
                    data: {'name':name},
                    dataType: 'json',
                    success:function(data){
                        if(data.success == true){
                            var pingyinname = data.obj;
                            $("#shortName").val(pingyinname);
                        }
                    }
                });
            }else{
                $("#shortName").val("");
            }
        });
        <%--if(!$('#cc').val()) { // 第一级，只显示公司选择项--%>
            <%--var orgTypeSelect = $("#orgType");--%>
            <%--var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';--%>
            <%--orgTypeSelect.empty();--%>
            <%--orgTypeSelect.append(companyOrgType);--%>
        <%--}--%>
//        else { // 非第一级，不显示公司选择项
//            $("#orgType option:first").remove();
//        }
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});
    var isSearch = false;
    function changeBillNo(parentId){
       // var parentId = $("#cc").combotree("getValue");
        if(parentId && isSearch) {
            $.ajax({
                type: 'POST',
                url: 'departController.do?getBillNo',
                async: false,
                data: {'parentId': parentId},
                dataType: 'json',
                success: function (data) {
                    if (data.success == true) {
                        var billNo = data.obj;
                        $("#orgCode").val(billNo);
                    }
                }
            });
        } else {
            isSearch = true;
        }
    }
    <%--function changeOrgType() { // 处理组织类型，不显示公司选择项--%>
        <%--var orgTypeSelect = $("#orgType");--%>
        <%--var optionNum = orgTypeSelect.get(0).options.length;--%>
        <%--if(optionNum == 1) {--%>
<%--//            $("#orgType option:first").remove();--%>
            <%--var bumen = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>>门店</option>';--%>
            <%--var gangwei = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>>部门</option>';--%>
            <%--orgTypeSelect.append(bumen).append(gangwei);--%>
        <%--}--%>
    <%--}--%>
</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="systemController.do?saveDepart" tiptype="1">
	<input id="id" name="id" type="hidden" value="${depart.id }">
    <input id="deleted" name="deleted" type="hidden" value="${depart.deleted}">
    <%--<fieldset class="step">
        <table>
            <tr>
                <td>
                    <div class="form">
                        <label class="Validform_label"> 分支机构名称: </label>
                        <input name="departname" class="inputxt" value="${depart.departname }"  datatype="s1-20">
                        <span class="Validform_checktip"><t:mutiLang langKey="departmentname.rang1to20"/></span>
                    </div>
                </td>
                <td>
                    <div class="form">
                        <label class="Validform_label"> 机构描述: </label>
                        <input name="description" class="inputxt" value="${depart.description }">
                    </div>
                </td>
                <td>
                    <div class="form">
                        <label class="Validform_label"> <t:mutiLang langKey="parent.depart"/>: </label>
                        <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}">
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="form">
                        <input type="hidden" name="orgCode" value="${depart.orgCode }">
                        <label class="Validform_label"> <t:mutiLang langKey="common.org.type"/>: </label>
                        <select name="orgType" id="orgType">
                            <option value="1" <c:if test="${depart.orgType=='1'}">selected="selected"</c:if>>经销商</option>
                            <option value="2" <c:if test="${depart.orgType=='2'}">selected="selected"</c:if>>门店</option>
                            <option value="3" <c:if test="${depart.orgType=='3'}">selected="selected"</c:if>>部门</option>
                        </select>
                    </div>
                </td>
            </tr>
        </table>
	</fieldset>--%>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    上级分支机构:
                </label>
            </td>
            <td class="value">
                <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}">
							</span>
                <label class="Validform_label" style="display: none;">上级分支机构</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    名称:
                </label>
            </td>
            <td class="value">
                <input id="departname" name="departname" type="text" style="width: 150px" class="inputxt"
                       datatype="*"
                       value='${depart.departname}'>
							<span class="Validform_checktip">
                                  *
							</span>
                <label class="Validform_label" style="display: none;">名称</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    编号:
                </label>
            </td>
            <td class="value">
                <input id="orgCode" name="orgCode" type="text" style="width: 150px" class="inputxt"
                       datatype="*"
                       value='${depart.orgCode}'>
							<span class="Validform_checktip">
                                  *
							</span>
                <label class="Validform_label" style="display: none;">编号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    助记码:
                </label>
            </td>
            <td class="value">
                <input id="shortNumber" name="shortNumber" type="text" style="width: 150px" class="inputxt"

                       value='${depart.shortNumber}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">助记码</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    简称:
                </label>
            </td>
            <td class="value">
                <input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"

                       value='${depart.shortName}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">简称</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    联系人:
                </label>
            </td>
            <td class="value">
                <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"

                       value='${depart.contact}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">联系人</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    手机号码:
                </label>
            </td>
            <td class="value">
                <input id="mobilePhone" name="mobilePhone" type="text" style="width: 150px" class="inputxt"
                       datatype="m" ignore="ignore"
                       value='${depart.mobilePhone}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">手机号码</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    电话号码:
                </label>
            </td>
            <td class="value">
                <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                       datatype="po" ignore="ignore"
                       value='${depart.phone}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">电话号码</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    传真:
                </label>
            </td>
            <td class="value">
                <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
                       datatype="f" ignore="ignore"
                       value='${depart.fax}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">传真</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    联系地址:
                </label>
            </td>
            <td class="value" colspan="3">
                <input id="address" name="address" type="text" style="width: 63%" class="inputxt"

                       value='${depart.address}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">联系地址</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    QQ号:
                </label>
            </td>
            <td class="value">
                <input id="ciqNumber" name="ciqNumber" type="text" style="width: 150px" class="inputxt"
                       value='${depart.ciqNumber}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">QQ号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    区域:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="regionId" type="list"
                              typeGroupCode="SC_REGION" defaultVal="${depart.regionId}" hasLabel="false"  title="区域"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">区域</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    城市:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="city" type="list"
                              typeGroupCode="SC_CITY" defaultVal="${depart.city}" hasLabel="false"  title="城市"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">城市</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    邮政编码:
                </label>
            </td>
            <td class="value">
                <input id="postalCode" name="postalCode" type="text" style="width: 150px" class="inputxt"

                       value='${depart.postalCode}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">邮政编码</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    电子邮件:
                </label>
            </td>
            <td class="value">
                <input id="email" name="email" type="text" style="width: 150px" class="inputxt"
                        datatype="e" ignore="ignore"
                       value='${depart.email}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">电子邮件</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    公司主页:
                </label>
            </td>
            <td class="value" colspan="3">
                <input id="homePage" name="homePage" type="text" style="width: 50%" class="inputxt"
                        datatype="url" ignore="ignore"
                       value='${depart.homePage}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">公司主页</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    开户银行:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="bank" type="list"
                              typeGroupCode="SC_BANK" defaultVal="${depart.bank}" hasLabel="false"  title="开户银行"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">开户银行</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    银行账号:
                </label>
            </td>
            <td class="value">
                <input id="bankNumber" name="bankNumber" type="text" style="width: 150px" class="inputxt"

                       value='${depart.bankNumber}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">银行账号</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    营业执照号:
                </label>
            </td>
            <td class="value">
                <input id="licence" name="licence" type="text" style="width: 150px" class="inputxt"

                       value='${depart.licence}'>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">营业执照号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    行业:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="trade" type="list"
                              typeGroupCode="SC_TRADE" defaultVal="${depart.trade}" hasLabel="false"  title="行业"></t:dictSelect>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">行业</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <t:mutiLang langKey="common.org.type"/>:
                </label>
            </td>
            <td class="value">
                <select name="orgType" id="orgType">
                    <option value="1" <c:if test="${depart.orgType=='1'}">selected="selected"</c:if>>经销商</option>
                    <option value="2" <c:if test="${depart.orgType=='2'}">selected="selected"</c:if>>门店</option>
                    <option value="3" <c:if test="${depart.orgType=='3'}">selected="selected"</c:if>>部门</option>
                </select>
                <label class="Validform_label" style="display: none;">编号</label>
            </td>
            <td></td>
            <td class="value"></td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    备注:
                </label>
            </td>
            <td class="value" colspan="5">
                <textarea id="note" style="width:50%;" class="inputxt" rows="6" name="note">${depart.note}</textarea>
							<span class="Validform_checktip">
							</span>
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
</html>
