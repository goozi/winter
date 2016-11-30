<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addTScRpPbillentry2Btn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="delTScRpPbillentry2Btn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScRpPbillentry2Btn(rowIndex) {
        var tr = $("#add_tScRpPbillentry2_table_template tr").clone();
        $("#add_tScRpPbillentry2_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScRpPbillentry2_table');
    }

    function clickDelTScRpPbillentry2Btn(rowIndex) {
        var idSrc = $("input[name='tScRpPbillentry2List[" + rowIndex + "].idSrc']").val();
        var tranType = $("input[name='tScRpPbillentry2List[" + rowIndex + "].classIdSrc']").val();
        if (ids || ids2) {
            if ("102" == tranType) {
                ids = ids.replace(idSrc + ",", "");
            } else {
                ids2 = ids2.replace(idSrc + ",", "");
            }
        }
        $("#add_tScRpPbillentry2_table").find(">tr").eq(rowIndex).remove();
        var len = $("#add_tScRpPbillentry2_table").find(">tr").length;
        if (len == 0) {
            var tr = $("#add_tScRpPbillentry2_table_template tr").clone();
            $("#add_tScRpPbillentry2_table").append(tr);
            resetTrNum('add_tScRpPbillentry2_table');
            var billType = $("select[name='billType_']").val();
            if ("1" == billType) {
                $("input[name='tScRpPbillentry2List[0].amount']").attr("readonly", "readonly");
                $("input[name='tScRpPbillentry2List[0].amount']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpPbillentry2List[0].note']").attr("readonly", "readonly");
                $("input[name='tScRpPbillentry2List[0].note']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpPbillentry2List[0].amount']").removeAttr("datatype");
                $("input[name='tScRpPbillentry2List[0].billnoSrc']").removeAttr("datatype")
            }
            $("#itemName").removeAttr("readonly");
            $("#itemName").css("background-color", "white");
            $("select[name='billType_']").removeAttr("disabled");
        } else {
            resetTrNum('add_tScRpPbillentry2_table');
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
        $("#tScRpPbillentry2_table").createhftable({
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
        if (${fn:length(tScRpPbillentry2List)  <= 0 }) {
            var billType = $("select[name='billType_']").val();
            if ("1" == billType) {
                $("input[name='tScRpPbillentry2List[0].amount']").attr("readonly", "readonly");
                $("input[name='tScRpPbillentry2List[0].amount']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpPbillentry2List[0].note']").attr("readonly", "readonly");
                $("input[name='tScRpPbillentry2List[0].note']").css("background-color", "rgb(226,226,226)");
                $("input[name='tScRpPbillentry2List[0].amount']").removeAttr("datatype");
            } else {
                $("input[name='tScRpPbillentry2List[0].amount']").removeAttr("readonly");
                $("input[name='tScRpPbillentry2List[0].amount']").css("background-color", "white");
                $("input[name='tScRpPbillentry2List[0].note']").removeAttr("readonly");
                $("input[name='tScRpPbillentry2List[0].note']").css("background-color", "white");
                $("input[name='tScRpPbillentry2List[0].amount']").attr("datatype", "float");
                $("input[name='tScRpPbillentry2List[0].billnoSrc']").attr("datatype", "*");
            }
        } else {
            $("#itemName").attr("readonly", "readonly");
            $("#itemName").css("background-color", "rgb(226,226,226)");
            $("select[name='billType_']").attr("disabled", "disabled");
            var $tbody = $("#tScRpPbillentry2_table");
            var length = $tbody[0].rows.length;
            rowIndex = length - 1;
            var allAmount = 0;
            var allUnCheckAmount = 0;
            var CFG_MONEY = $("#CFG_MONEY").val();
            for (var j = 0; j < length; j++) {
                var amount = $("input[name='tScRpPbillentry2List[" + j + "].amount']").val();
                var unCheckAmount = $("input[name='tScRpPbillentry2List[" + j + "].billUnCheckAmount']").val();//未付数量
                var checkAmount = $("input[name='tScRpPbillentry2List[" + j + "].billCheckAmount']").val();//已付金额
                var billAmount = $("input[name='tScRpPbillentry2List[" + j + "].billAmount']").val();//源单金额
                var tranType = $("input[name='tScRpPbillentry2List[" + j + "].classIdSrc']").val();
                var id = $("input[name='tScRpPbillentry2List[" + j + "].idSrc']").val();
                if ("53" != tranType) {
                    //amount = -1 * parseFloat(amount);
                    allUnCheckAmount += parseFloat(unCheckAmount);
                }
                allAmount += parseFloat(amount);
                if ("51" == tranType) {
                    ids += id + ",";
                } else {
                    ids2 += id + ",";
                }
                amount = parseFloat(amount).toFixed(CFG_MONEY);
                unCheckAmount = parseFloat(unCheckAmount).toFixed(CFG_MONEY);
                checkAmount = parseFloat(checkAmount).toFixed(CFG_MONEY);
                billAmount = parseFloat(billAmount).toFixed(CFG_MONEY);
                $("input[name='tScRpPbillentry2List[" + j + "].amount']").val(amount);
                $("input[name='tScRpPbillentry2List[" + j + "].billUnCheckAmount']").val(unCheckAmount);
                $("input[name='tScRpPbillentry2List[" + j + "].billCheckAmount']").val(checkAmount);
                $("input[name='tScRpPbillentry2List[" + j + "].billAmount']").val(billAmount);
            }
            $("#entry2Amount").val(allAmount);
            $("#allUnCheckAmount").val(allUnCheckAmount);
        }
    });
</script>
<input id="allUnCheckAmount" type="hidden">
<table border="0" cellpadding="2" cellspacing="1" id="tScRpPbillentry2_table" totalRow="true"
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
            源单已付金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            源单未付金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            本次付款
        </td>
        <td align="center" bgcolor="#476f9a">
            备注
        </td>
    </tr>
    <tbody id="add_tScRpPbillentry2_table">
    <c:if test="${fn:length(tScRpPbillentry2List)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 25px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                        <%--<a name="addTScRpPbillentry2Btn[0]" id="addTScRpPbillentry2Btn[0]" href="#"--%>
                        <%--onclick="clickAddTScRpPbillentry2Btn(0);"></a>--%>
                    <a name="delTScRpPbillentry2Btn[0]" id="delTScRpPbillentry2Btn[0]" href="#"
                       onclick="clickDelTScRpPbillentry2Btn(0);"></a>
                </div>
            </td>
            <input name="tScRpPbillentry2List[0].id" type="hidden"/>
            <input name="tScRpPbillentry2List[0].createName" type="hidden"/>
            <input name="tScRpPbillentry2List[0].createBy" type="hidden"/>
            <input name="tScRpPbillentry2List[0].createDate" type="hidden"/>
            <input name="tScRpPbillentry2List[0].updateName" type="hidden"/>
            <input name="tScRpPbillentry2List[0].updateBy" type="hidden"/>
            <input name="tScRpPbillentry2List[0].updateDate" type="hidden"/>
            <input name="tScRpPbillentry2List[0].fid" type="hidden"/>
            <input name="tScRpPbillentry2List[0].findex" type="hidden"/>
            <input name="tScRpPbillentry2List[0].idSrc" type="hidden"/>
            <input name="tScRpPbillentry2List[0].classIdSrc" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].classIdName" maxlength="11"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单类型</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].billnoSrc" maxlength="50"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].dateSrc" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单日期</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].billAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].billCheckAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单已付金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].billUnCheckAmount" maxlength="20"
                       type="text" class="inputxt" readonly="readonly"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">源单未付金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].amount" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" onchange="checkAmount(0)"
                       style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">本次付款</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpPbillentry2List[0].note" maxlength="255" readonly="readonly"
                       type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScRpPbillentry2List)  > 0 }">
        <c:forEach items="${tScRpPbillentry2List}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 80px;background-color: white;">
                            <%--<a name="addTScRpPbillentry2Btn[${stuts.index}]" id="addTScRpPbillentry2Btn[${stuts.index}]" href="#"--%>
                            <%--onclick="clickAddTScRpPbillentry2Btn(${stuts.index});"></a>--%>

                        <a name="delTScRpPbillentry2Btn[${stuts.index}]" id="delTScRpPbillentry2Btn[${stuts.index}]"
                           href="#"
                           onclick="clickDelTScRpPbillentry2Btn(${stuts.index});"></a>
                    </div>
                </td>
                </c:if>
                <input name="tScRpPbillentry2List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].findex" type="hidden" value="${poVal.findex }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].idSrc" type="hidden" value="${poVal.idSrc }"/>
                <input name="tScRpPbillentry2List[${stuts.index }].classIdSrc" type="hidden"
                       value="${poVal.classIdSrc }"/>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].classIdName" maxlength="11"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.classIdName }">
                    <label class="Validform_label" style="display: none;">源单类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].billnoSrc" maxlength="50"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billnoSrc }">
                    <label class="Validform_label" style="display: none;">源单编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].dateSrc" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="<fmt:formatDate value='${poVal.dateSrc}' type="date" pattern="yyyy-MM-dd"/>">
                    <label class="Validform_label" style="display: none;">源单日期</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].billAmount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billAmount }">
                    <label class="Validform_label" style="display: none;">源单金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].billCheckAmount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billCheckAmount }">
                    <label class="Validform_label" style="display: none;">源单已付金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].billUnCheckAmount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                           value="${poVal.billUnCheckAmount }">
                    <label class="Validform_label" style="display: none;">源单未付金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].amount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;" onchange="checkAmount(${stuts.index })"
                           datatype="float" value="${poVal.amount }">
                    <label class="Validform_label" style="display: none;">本次付款</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpPbillentry2List[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:120px;"
                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
