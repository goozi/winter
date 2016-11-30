<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addTScRpRbillentry2Btn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="delTScRpRbillentry2Btn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScRpRbillentry2Btn(rowIndex) {
        var tr = $("#add_tScRpRbillentry2_table_template tr").clone();
        $("#add_tScRpRbillentry2_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScRpRbillentry2_table');
    }

    function clickDelTScRpRbillentry2Btn(rowIndex) {
        debugger;
        var idSrc = $("input[name='tScRpRbillentry2List[" + rowIndex + "].idSrc']").val();
        var tranType = $("input[name='tScRpRbillentry2List[" + rowIndex + "].classIdSRC']").val();
        if (ids || ids2) {
            if ("102" == tranType) {
                ids = ids.replace(idSrc + ",", "");
            } else {
                ids2 = ids2.replace(idSrc + ",", "");
            }
        }
        $("#add_tScRpRbillentry2_table").find(">tr").eq(rowIndex).remove();
        var len = $("#add_tScRpRbillentry2_table").find(">tr").length;
        if (len == 0) {
            var tr = $("#add_tScRpRbillentry2_table_template tr").clone();
            $("#add_tScRpRbillentry2_table").append(tr);
            resetTrNum('add_tScRpRbillentry2_table');
            var billType = $("select[name='billType_']").val();
            if ("1" == billType) {
                $("input[name='tScRpRbillentry2List[0].amount']").attr("readonly", "readonly");
                $("input[name='tScRpRbillentry2List[0].amount']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpRbillentry2List[0].note']").attr("readonly", "readonly");
                $("input[name='tScRpRbillentry2List[0].note']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpRbillentry2List[0].amount']").removeAttr("datatype");
                $("input[name='tScRpRbillentry2List[0].billNoSrc']").removeAttr("datatype")
            }
            $("#itemName").removeAttr("disabled");
            $("#itemName").css("background-color", "white");
            $("select[name='billType_']").removeAttr("disabled");
        } else {
            resetTrNum('add_tScRpRbillentry2_table');
        }
        setAllAmount2();
    }

    $(document).ready(function () {
        $(".datagrid-toolbar").parent().css("width", "auto");
        if (location.href.indexOf("load=detail") != -1) {
            $(":input").attr("disabled", "true");
            $(".datagrid-toolbar").hide();
        }
        //将表格的表头固定
        $("#tScRpRbillentry2_table").createhftable({
            height: ((h / 2) - 86.5) + 'px',
            width: 'auto',
            fixFooter: true
        });
        //解决一对多页面中列表页输入框tip属性失效问题
        $('table').find("[tip]").each(function () {
            var defaultvalue = $(this).attr("tip");
            var altercss = $(this).attr("altercss");
            $(this).focus(function () {
                if ($(this).val() == defaultvalue) {
                    $(this).val('');
                    if (altercss) {
                        $(this).removeClass(altercss);
                    }
                }
            }).blur(function () {
                if ($.trim($(this).val()) === '') {
                    $(this).val(defaultvalue);
                    if (altercss) {
                        $(this).addClass(altercss);
                    }
                }
            });
        });
        if (${fn:length(tScRpRbillentry2List)  <= 0 }) {
            var billType = $("select[name='billType_']").val();
            if ("1" == billType) {
                $("input[name='tScRpRbillentry2List[0].amount']").attr("readonly", "readonly");
                $("input[name='tScRpRbillentry2List[0].amount']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpRbillentry2List[0].note']").attr("readonly", "readonly");
                $("input[name='tScRpRbillentry2List[0].note']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpRbillentry2List[0].amount']").removeAttr("datatype");
            } else {
                $("input[name='tScRpRbillentry2List[0].amount']").removeAttr("readonly");
                $("input[name='tScRpRbillentry2List[0].amount']").css("background-color", "white");
                $("input[name='tScRpRbillentry2List[0].note']").removeAttr("readonly");
                $("input[name='tScRpRbillentry2List[0].note']").css("background-color", "white");
                $("input[name='tScRpRbillentry2List[0].amount']").attr("datatype", "float");
                $("input[name='tScRpRbillentry2List[0].billNoSrc']").attr("datatype", "*");
            }
        } else {
            $("#itemName").attr("disabled", "disabled");
            $("#itemName").css("background-color", "rgb(226,226,226)");
            $("select[name='billType_']").attr("disabled", "disabled");
            var $tbody = $("#tScRpRbillentry2_table");
            var length = $tbody[0].rows.length;
            rowIndex = length - 1;
            var allAmount = 0;
            var allUnCheckAmount = 0;
            debugger;
            for (var j = 0; j < length; j++) {
                var amount = $("input[name='tScRpRbillentry2List[" + j + "].amount']").val();
                var unCheckAmount = $("input[name='tScRpRbillentry2List[" + j + "].billUnCheckAmount']").val();//未执行数量
                var tranType = $("input[name='tScRpRbillentry2List[" + j + "].classIdSRC']").val();
                var id = $("input[name='tScRpRbillentry2List[" + j + "].idSrc']").val();
                if ("104" != tranType) {
                    //amount = -1 * parseFloat(amount);
                    allUnCheckAmount += parseFloat(unCheckAmount);
                }
                allAmount += parseFloat(amount);
                if ("102" == tranType) {
                    ids += id + ",";
                } else {
                    ids2 += id + ",";
                }
            }
            $("#entry2Amount").val(allAmount);
            $("#allUnCheckAmount").val(allUnCheckAmount);
        }
    });
</script>
<input id="allUnCheckAmount" type="hidden">
<table border="0" cellpadding="2" cellspacing="1" id="tScRpRbillentry2_table" totalRow="true"
       style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
        <td align="center" bgcolor="#476f9a">序号</td>
        <c:if test="${load ne 'detail'}">
            <td align="center" bgcolor="#476f9a">操作</td>
        </c:if>
        <td align="center" bgcolor="#476f9a">
            源单类型
        </td>
        <td align="center" bgcolor="#476f9a">
            源单编号
        </td>
        <td align="center" bgcolor="#476f9a">
            源单日期
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            源单金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            源单已执行金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            源单未执行金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            本次收款
        </td>
        <td align="center" bgcolor="#476f9a">
            备注
        </td>
    </tr>
    <tbody id="add_tScRpRbillentry2_table">
    <c:if test="${fn:length(tScRpRbillentry2List)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 25px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                        <%--<a name="addTScRpRbillentry2Btn[0]" id="addTScRpRbillentry2Btn[0]" href="#"--%>
                        <%--onclick="clickAddTScRpRbillentry2Btn(0);"></a>--%>
                    <a name="delTScRpRbillentry2Btn[0]" id="delTScRpRbillentry2Btn[0]" href="#"
                       onclick="clickDelTScRpRbillentry2Btn(0);"></a>
                </div>
            </td>
            <input name="tScRpRbillentry2List[0].id" type="hidden"/>
            <input name="tScRpRbillentry2List[0].createName" type="hidden"/>
            <input name="tScRpRbillentry2List[0].createBy" type="hidden"/>
            <input name="tScRpRbillentry2List[0].createDate" type="hidden"/>
            <input name="tScRpRbillentry2List[0].updateName" type="hidden"/>
            <input name="tScRpRbillentry2List[0].updateBy" type="hidden"/>
            <input name="tScRpRbillentry2List[0].updateDate" type="hidden"/>
            <input name="tScRpRbillentry2List[0].fid" type="hidden"/>
            <input name="tScRpRbillentry2List[0].findex" type="hidden"/>
            <input name="tScRpRbillentry2List[0].idSrc" type="hidden"/>
            <input name="tScRpRbillentry2List[0].classIdSRC" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].classIdName" maxlength="11"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].billNoSrc" maxlength="50"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].dateSrc" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单日期</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].billAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].billCheckAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单已执行金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].billUnCheckAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单未执行金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].amount" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" onchange="checkAmount(0)"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">本次收款</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry2List[0].note" maxlength="255" readonly="readonly"
                       type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScRpRbillentry2List)  > 0 }">
        <c:forEach items="${tScRpRbillentry2List}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 80px;background-color: white;">
                            <%--<a name="addTScRpRbillentry2Btn[${stuts.index}]" id="addTScRpRbillentry2Btn[${stuts.index}]" href="#"--%>
                            <%--onclick="clickAddTScRpRbillentry2Btn(${stuts.index});"></a>--%>

                        <a name="delTScRpRbillentry2Btn[${stuts.index}]" id="delTScRpRbillentry2Btn[${stuts.index}]"
                           href="#"
                           onclick="clickDelTScRpRbillentry2Btn(${stuts.index});"></a>
                    </div>
                </td>
                </c:if>
                <input name="tScRpRbillentry2List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].findex" type="hidden" value="${poVal.findex }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].idSrc" type="hidden" value="${poVal.idSrc }"/>
                <input name="tScRpRbillentry2List[${stuts.index }].classIdSRC" type="hidden"
                       value="${poVal.classIdSRC }"/>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].classIdName" maxlength="11"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.classIdName }">
                    <label class="Validform_label" style="display: none;">源单类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].billNoSrc" maxlength="50"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billNoSrc }">
                    <label class="Validform_label" style="display: none;">源单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].dateSrc" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="<fmt:formatDate value='${poVal.dateSrc}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">源单日期</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].billAmount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billAmount }">
                    <label class="Validform_label" style="display: none;">源单金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].billCheckAmount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billCheckAmount }">
                    <label class="Validform_label" style="display: none;">源单已执行金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].billUnCheckAmount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billUnCheckAmount }">
                    <label class="Validform_label" style="display: none;">源单未执行金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].amount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;" onchange="checkAmount(${stuts.index })"
                           datatype="float" value="${poVal.amount }">
                    <label class="Validform_label" style="display: none;">本次收款</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry2List[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:120px;"
                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
