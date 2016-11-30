<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addTScRpRbillentry1Btn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="delTScRpRbillentry1Btn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScRpRbillentry1Btn(rowIndex) {
        var tr = $("#add_tScRpRbillentry1_table_template tr").clone();
        $("#add_tScRpRbillentry1_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScRpRbillentry1_table');
    }

    function clickDelTScRpRbillentry1Btn(rowIndex) {
        var accountId = $("input[name='tScRpRbillentry1List[" + rowIndex + "].accountId']").val();
        if (ids3) {
            ids3 = ids3.replace(accountId + ",", "");
        }
        var len = $("#add_tScRpRbillentry1_table").find(">tr").length;
        $("#add_tScRpRbillentry1_table").find(">tr").eq(rowIndex).remove();
        if (rowIndex == 0 && len == 1) {//如果只有一行且删除这一行则删除后补一空行
            var tr = $("#add_tScRpRbillentry1_table_template tr").clone();
            $("#add_tScRpRbillentry1_table").append(tr);
        }
        resetTrNum('add_tScRpRbillentry1_table');
        setAllAmount()
    }

    function keyDownInfo(index, evt) {
        var evt = (evt) ? evt : ((window.event) ? window.event : "");
        var code = evt.keyCode ? evt.keyCode : evt.which;
        if (code == 13) {
            selectAccountDialog(index);
        }
    }

    $(document).ready(function () {
        $(".datagrid-toolbar").parent().css("width", "auto");
        if (location.href.indexOf("load=detail") != -1) {
            $(":input").attr("disabled", "true");
            $(".datagrid-toolbar").hide();
        }
        //将表格的表头固定
        $("#tScRpRbillentry1_table").createhftable({
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
    });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScRpRbillentry1_table" totalRow="true"
       style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
        <td align="center" bgcolor="#476f9a">序号</td>
        <c:if test="${load ne 'detail'}">
            <td align="center" bgcolor="#476f9a">操作</td>
        </c:if>
        <td align="center" bgcolor="#476f9a">
            结算账户<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            金额<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            备注
        </td>
    </tr>
    <tbody id="add_tScRpRbillentry1_table">
    <c:if test="${fn:length(tScRpRbillentry1List)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 25px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                    <a name="addTScRpRbillentry1Btn[0]" id="addTScRpRbillentry1Btn[0]" href="#"
                       onclick="clickAddTScRpRbillentry1Btn(0);"></a>
                    <a name="delTScRpRbillentry1Btn[0]" id="delTScRpRbillentry1Btn[0]" href="#"
                       onclick="clickDelTScRpRbillentry1Btn(0);"></a>
                </div>
            </td>
            <input name="tScRpRbillentry1List[0].id" type="hidden"/>
            <input name="tScRpRbillentry1List[0].createName" type="hidden"/>
            <input name="tScRpRbillentry1List[0].createBy" type="hidden"/>
            <input name="tScRpRbillentry1List[0].createDate" type="hidden"/>
            <input name="tScRpRbillentry1List[0].updateName" type="hidden"/>
            <input name="tScRpRbillentry1List[0].updateBy" type="hidden"/>
            <input name="tScRpRbillentry1List[0].updateDate" type="hidden"/>
            <input name="tScRpRbillentry1List[0].fid" type="hidden"/>
            <input name="tScRpRbillentry1List[0].findex" value="1" type="hidden"/>
            <input name="tScRpRbillentry1List[0].accountId" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry1List[0].accountName" maxlength="100" datatype="*"
                       onkeypress="keyDownInfo(0,event)"
                       onblur="orderListStockBlur('0','accountId','accountName');"
                       type="text" class="inputxt popup-select" style="width:120px;">
                <label class="Validform_label" style="display: none;">结算账户</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry1List[0].amount" maxlength="20" onchange="setAllAmount()"
                       type="text" class="inputxt" style="width:120px;" datatype="vInt">
                <label class="Validform_label" style="display: none;">金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScRpRbillentry1List[0].note" maxlength="255"
                       type="text" class="inputxt" style="width:120px;">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScRpRbillentry1List)  > 0 }">
        <c:forEach items="${tScRpRbillentry1List}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <c:if test="${load ne 'detail'}">
                <td align="center" bgcolor="white">
                    <div style="width: 80px;background-color: white;">
                        <a name="addTScRpRbillentry1Btn[${stuts.index}]" id="addTScRpRbillentry1Btn[${stuts.index}]"
                           href="#"
                           onclick="clickAddTScRpRbillentry1Btn(${stuts.index});"></a>

                        <a name="delTScRpRbillentry1Btn[${stuts.index}]" id="delTScRpRbillentry1Btn[${stuts.index}]"
                           href="#"
                           onclick="clickDelTScRpRbillentry1Btn(${stuts.index});"></a>
                    </div>
                </td>
                </c:if>
                <input name="tScRpRbillentry1List[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].findex" type="hidden" value="${poVal.findex }"/>
                <input name="tScRpRbillentry1List[${stuts.index }].accountId" type="hidden"
                       value="${poVal.accountId }"/>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry1List[${stuts.index }].accountName" maxlength="100"
                           type="text" class="inputxt popup-select" style="width:120px;" datatype="*"
                           onkeypress="keyDownInfo(${stuts.index },event)"
                           onblur="orderListStockBlur('${stuts.index }','accountId','accountName');"
                           value="${poVal.accountName }">
                    <label class="Validform_label" style="display: none;">结算账户</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry1List[${stuts.index }].amount" maxlength="20"
                           type="text" class="inputxt" style="width:120px;" onchange="setAllAmount()"
                           datatype="vInt" value="${poVal.amount }">
                    <label class="Validform_label" style="display: none;">金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScRpRbillentry1List[${stuts.index }].note" maxlength="255"
                           type="text" class="inputxt" style="width:120px;"
                           value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
