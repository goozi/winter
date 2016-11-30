<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addTScRpAdjustbillentryBtn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="delTScRpAdjustbillentryBtn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScRpAdjustbillentryBtn(rowIndex) {
        var tr = $("#add_tScRpAdjustbillentry_table_template tr").clone();
        $("#add_tScRpAdjustbillentry_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScRpAdjustbillentry_table');
    }

    function clickDelTScRpAdjustbillentryBtn(rowIndex) {
        var len = $("#add_tScRpAdjustbillentry_table").find(">tr").length;
        $("#add_tScRpAdjustbillentry_table").find(">tr").eq(rowIndex).remove();
        if (rowIndex == 0 && len == 1) {//如果只有一行且删除这一行则删除后补一空行
            var tr = $("#add_tScRpAdjustbillentry_table_template tr").clone();
            $("#add_tScRpAdjustbillentry_table").append(tr);
        }
        resetTrNum('add_tScRpAdjustbillentry_table');
    }

    function keyDownInfo(index, evt) {
        var evt = (evt) ? evt : ((window.event) ? window.event : "");
        var code = evt.keyCode ? evt.keyCode : evt.which;
        if (code == 13) {
            selectFeeDialog(index);
        }
    }
    $(document).ready(function () {
        $(".datagrid-toolbar").parent().css("width", "auto");
        if (location.href.indexOf("load=detail") != -1) {
            $(":input").attr("disabled", "true");
            $(".datagrid-toolbar").hide();
        }
        //将表格的表头固定
        $("#tScRpAdjustbillentry_table").createhftable({
            height: (h - 40) + 'px',
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
        if (${fn:length(tScRpAdjustbillentryList)  > 0 }) {
            var $tbody = $("#tScRpAdjustbillentry_table");
            var length = $tbody[0].rows.length;
            rowIndex = length - 1;
            for (var j = 0; j < length; j++) {
                var expId = $("input[name='tScRpAdjustbillentryList[" + j + "].expId']").val();
                var expName = $("input[name='tScRpAdjustbillentryList[" + j + "].expName']").val();
                setValOldIdAnOldName($("input[name='tScRpAdjustbillentryList[" + j + "].expName']"), expId, expName);
            }
        }
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScRpAdjustbillentry_table" totalRow="true"
       style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
        <td align="center" bgcolor="#476f9a">序号</td>
        <c:if test="${load ne 'detail'}">
            <td align="center" bgcolor="#476f9a">操作</td>
        </c:if>
        <td align="center" bgcolor="#476f9a">
            收支项目<span style="color: red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            金额<span style="color: red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            备注
        </td>
    </tr>
    <tbody id="add_tScRpAdjustbillentry_table">
    <c:if test="${fn:length(tScRpAdjustbillentryList)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 25px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                    <a name="addTScRpAdjustbillentryBtn[0]" id="addTScRpAdjustbillentryBtn[0]" href="#"
                       onclick="clickAddTScRpAdjustbillentryBtn(0);"></a>
                    <a name="delTScRpAdjustbillentryBtn[0]" id="delTScRpAdjustbillentryBtn[0]" href="#"
                       onclick="clickDelTScRpAdjustbillentryBtn(0);"></a>
                </div>
            </td>
            <input name="tScRpAdjustbillentryList[0].id" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].createName" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].createBy" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].createDate" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].updateName" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].updateBy" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].updateDate" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].fid" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].findex" value="1" type="hidden"/>
            <input name="tScRpAdjustbillentryList[0].expId" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScRpAdjustbillentryList[0].expName" maxlength="32"
                       onkeypress="keyDownInfo(0,event)"
                       onblur="orderListStockBlur('0','expId','expName');"
                       type="text" class="inputxt popup-select" style="width:120px;" datatype="*">
                <label class="Validform_label" style="display: none;">收支项目</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpAdjustbillentryList[0].amount" maxlength="20"
                       onchange="setAllAmount()"
                       type="text" class="inputxt" style="width:120px;" datatype="fInt">
                <label class="Validform_label" style="display: none;">金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpAdjustbillentryList[0].note" maxlength="255"
                       type="text" class="inputxt" style="width:120px;">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScRpAdjustbillentryList)  > 0 }">
        <c:forEach items="${tScRpAdjustbillentryList}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                    <td align="center" bgcolor="white">
                        <div style="width: 80px;background-color: white;">
                            <a name="addTScRpAdjustbillentryBtn[${stuts.index}]"
                               id="addTScRpAdjustbillentryBtn[${stuts.index}]" href="#"
                               onclick="clickAddTScRpAdjustbillentryBtn(${stuts.index});"></a>

                            <a name="delTScRpAdjustbillentryBtn[${stuts.index}]"
                               id="delTScRpAdjustbillentryBtn[${stuts.index}]" href="#"
                               onclick="clickDelTScRpAdjustbillentryBtn(${stuts.index});"></a>
                        </div>
                    </td>
                </c:if>
                <input name="tScRpAdjustbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].createBy" type="hidden"
                       value="${poVal.createBy }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].updateBy" type="hidden"
                       value="${poVal.updateBy }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].findex" type="hidden" value="${poVal.findex }"/>
                <input name="tScRpAdjustbillentryList[${stuts.index }].expId" type="hidden" value="${poVal.expId }"/>
                <td align="left" bgcolor="white">
                    <input name="tScRpAdjustbillentryList[${stuts.index }].expName" maxlength="32"
                           onkeypress="keyDownInfo(${stuts.index },event)"
                           onblur="orderListStockBlur('${stuts.index }','expId','expName');"
                           type="text" class="inputxt popup-select" style="width:120px;" datatype="*"
                           value="${poVal.expName }">
                    <label class="Validform_label" style="display: none;">收支项目</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpAdjustbillentryList[${stuts.index }].amount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;" datatype="fInt"
                           onchange="setAllAmount()"
                           value="${poVal.amount }">
                    <label class="Validform_label" style="display: none;">金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpAdjustbillentryList[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:120px;"
                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
