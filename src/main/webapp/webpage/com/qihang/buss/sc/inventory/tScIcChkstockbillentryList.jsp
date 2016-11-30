<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
    $('a[name^="addTScIcChkstockbillentryBtn"]').linkbutton({
        iconCls: 'icon-add',
        plain: true
    });
    $('a[name^="delTScIcChkstockbillentryBtn"]').linkbutton({
        iconCls: 'icon-remove',
        plain: true
    });
    function clickAddTScIcChkstockbillentryBtn(rowIndex) {
        var tr = $("#add_tScIcChkstockbillentry_table_template tr").clone();
        $("#add_tScIcChkstockbillentry_table tr").eq(rowIndex).after(tr);
        resetTrNum('add_tScIcChkstockbillentry_table', rowIndex);
    }

    function clickDelTScIcChkstockbillentryBtn(rowIndex) {
        var len = $("#add_tScIcChkstockbillentry_table").find(">tr").length;
        $("#add_tScIcChkstockbillentry_table").find(">tr").eq(rowIndex).remove();
        if (rowIndex == 0 && len == 1) {//如果只有一行且删除这一行则删除后补一空行
            var tr = $("#add_tScIcChkstockbillentry_table_template tr").clone();
            $("#add_tScIcChkstockbillentry_table").append(tr);
            $("#stockName").removeAttr("disabled");
        }
        resetTrNum('add_tScIcChkstockbillentry_table', rowIndex);
        if (rowIndex == 0 && len == 1) {
            $("#unitId\\[" + 0 + "\\]").combobox({
                width: 54,
                valueField: "id",
                textField: "text",
                panelHeight: "auto",
                editable: false
            })
        }
    }

    $(document).ready(function () {
        $(".datagrid-toolbar").parent().css("width", "auto");
        if (location.href.indexOf("load=detail") != -1) {
            $(":input").attr("disabled", "true");
            $(".datagrid-toolbar").hide();
        }
        //将表格的表体固定
        $("#tScIcChkstockbillentry_table").createhftable({
            height: (h - 50) + 'px',
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

        if (${fn:length(tScIcChkstockbillentryList)  > 0 }) {
            var isAuto = $("#isAuto").val();
            var $tbody = $("#tScIcChkstockbillentry_table");
            var length = $tbody[0].rows.length;
            $("select[name='chkType_']").attr("disabled", "disabled");
            if (isAuto) {
                for (var j = 0; j < length; j++) {
                    $("#addTScIcChkstockbillentryBtn\\[" + j + "\\]").removeAttr("onclick");
                    $("#delTScIcChkstockbillentryBtn\\[" + j + "\\]").removeAttr("onclick");
                    $("input[name='tScIcChkstockbillentryList[" + j + "].itemNo']").attr("disabled", "disabled");
                    $("input[name='tScIcChkstockbillentryList[" + j + "].itemNo']").css("background-color", "rgb(226,226,226)");
                    setValOldIdAnOldName($("input[name='tScIcChkstockbillentryList[" + j + "].itemNo']"),
                            $("input[name='tScIcChkstockbillentryList[" + j + "].itemId']").val(),
                            $("input[name='tScIcChkstockbillentryList[" + j + "].itemNo']").val());
                }
            } else {
                for (var j = 0; j < length; j++) {
                    setValOldIdAnOldName($("input[name='tScIcChkstockbillentryList[" + j + "].itemNo']"),
                            $("input[name='tScIcChkstockbillentryList[" + j + "].itemId']").val(),
                            $("input[name='tScIcChkstockbillentryList[" + j + "].itemNo']").val());
                }
            }
        }

    });

    function keyDownInfo(index, name, evt) {
        var evt = (evt) ? evt : ((window.event) ? window.event : "");
        var code = evt.keyCode ? evt.keyCode : evt.which;
        if (code == 13) {
            if (name == "item") {
                selectIcitemDialog(index);
            }
        }
    }
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScIcChkstockbillentry_table" totalRow="true"
       style="background-color: #cbccd2">
    <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
        <td align="center" bgcolor="#476f9a">序号</td>
        <td align="center" bgcolor="#476f9a">操作</td>
        <td align="center" bgcolor="#476f9a">
            商品编号<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a">
            商品名称
        </td>
        <td align="center" bgcolor="#476f9a">
            规格
        </td>
        <td align="center" bgcolor="#476f9a">
            条形码
        </td>
        <%--<td align="center" bgcolor="#476f9a">--%>
        <%--仓库--%>
        <%--</td>--%>
        <td align="center" bgcolor="#476f9a">
            批号
        </td>
        <td align="center" bgcolor="#476f9a">
            单位
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            账面箱数
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            账面散数
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            账面数量
        </td>
        <td align="center" bgcolor="#476f9a">
            换算率
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            箱数<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            散数<span style="color:red">*</span>
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            盘点数量
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            差异数量
        </td>
        <td align="center" bgcolor="#476f9a">
            成本单价
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            账面金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            盘点金额
        </td>
        <td align="center" bgcolor="#476f9a" total="true">
            差异金额
        </td>
        <td align="center" bgcolor="#476f9a">
            备注
        </td>
    </tr>
    <tbody id="add_tScIcChkstockbillentry_table">
    <c:if test="${fn:length(tScIcChkstockbillentryList)  <= 0 }">
        <tr>
            <td align="center" bgcolor="#F6FCFF">
                <div style="width: 30px;background-color: white;" name="xh">1</div>
            </td>
            <td align="center" bgcolor="white">
                <div style="width: 80px;background-color: white;">
                    <a name="addTScIcChkstockbillentryBtn[0]" id="addTScIcChkstockbillentryBtn[0]" href="#"
                       onclick="clickAddTScIcChkstockbillentryBtn(0);"></a>
                    <a name="delTScIcChkstockbillentryBtn[0]" id="delTScIcChkstockbillentryBtn[0]" href="#"
                       onclick="clickDelTScIcChkstockbillentryBtn(0);"></a>
                </div>
            </td>
            <input name="tScIcChkstockbillentryList[0].id" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].createName" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].createBy" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].createDate" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].updateName" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].updateBy" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].updateDate" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].secUnitId" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].basicUnitId" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].secQty" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].fid" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].findex" value="1" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].itemId" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].stockId" type="hidden"/>
            <input name="tScIcChkstockbillentryList[0].idSrc" type="hidden"/>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].itemNo" maxlength="32"
                       onkeypress="keyDownInfo(0,'item',event)"
                       onblur="orderListStockBlur('0','itemid','itemNo');" datatype="*"
                       type="text" class="inputxt popup-select" style="width:105px;">
                <label class="Validform_label" style="display: none;">商品编号</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].itemName" maxlength="32"
                       type="text" class="inputxt" style="width:180px;background-color: rgb(226,226,226)"
                       readonly="readonly">
                <label class="Validform_label" style="display: none;">商品名称</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].model" maxlength="32"
                       type="text" class="inputxt" style="width:85px;background-color: rgb(226,226,226)"
                       readonly="readonly">
                <label class="Validform_label" style="display: none;">规格</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].barCode" maxlength="32"
                       type="text" class="inputxt" style="width:65px;background-color: rgb(226,226,226)"
                       readonly="readonly">
                <label class="Validform_label" style="display: none;">条形码</label>
            </td>
                <%--<td align="left" bgcolor="white">--%>
                <%--<input name="tScIcChkstockbillentryList[0].stockName" maxlength="32"--%>
                <%--type="text" class="inputxt"  style="width:65px;" >--%>
                <%--<label class="Validform_label" style="display: none;">仓库</label>--%>
                <%--</td>--%>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].batchNo" maxlength="100" readonly="readonly"
                       type="text" class="inputxt" style="width:80px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">批号</label>
            </td>
            <td align="left" bgcolor="white">
                <input id="unitId[0]" name="tScIcChkstockbillentryList[0].unitId" maxlength="32" style="width: 50px;"
                       type="text"
                       class="easyui-combobox"
                       data-options="valueField: 'id',textField: 'text',disabled:true,width:54,panelHeight: 'auto',editable: false"
                        >
                <label class="Validform_label" style="display: none;">单位</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].qty" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">账面箱数</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].smallQty" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">账面散数</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].basicQty" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">账面数量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].coefficient" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:65px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">换算率</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].chkQty" maxlength="20"
                       type="text" class="inputxt" style="width:65px;" onchange="changeQty(0)" datatype="vFloat">
                <label class="Validform_label" style="display: none;">箱数</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].chkSmallQty" maxlength="20"
                       type="text" class="inputxt" style="width:65px;" onchange="changeQty(0)" datatype="vFloat">
                <label class="Validform_label" style="display: none;">散数</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].chkBasicQty" maxlength="20"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226);"
                       readonly="readonly" >
                <label class="Validform_label" style="display: none;">盘点数量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].diffQty" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">差异数量</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].costPrice" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">成本单价</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].amount" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">账面金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].chkAmount" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">盘点金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].diffAmount" maxlength="20" readonly="readonly"
                       type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)">
                <label class="Validform_label" style="display: none;">差异金额</label>
            </td>
            <td align="left" bgcolor="white">
                <input name="tScIcChkstockbillentryList[0].note" maxlength="80"
                       type="text" class="inputxt" style="width:180px;">
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </c:if>
    <c:if test="${fn:length(tScIcChkstockbillentryList)  > 0 }">
        <c:forEach items="${tScIcChkstockbillentryList}" var="poVal" varStatus="stuts">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
                </td>
                <td align="center" bgcolor="white">
                    <div style="width: 80px;background-color: white;">
                        <a name="addTScIcChkstockbillentryBtn[${stuts.index}]"
                           id="addTScIcChkstockbillentryBtn[${stuts.index}]" href="#"
                           onclick="clickAddTScIcChkstockbillentryBtn(${stuts.index});"></a>

                        <a name="delTScIcChkstockbillentryBtn[${stuts.index}]"
                           id="delTScIcChkstockbillentryBtn[${stuts.index}]" href="#"
                           onclick="clickDelTScIcChkstockbillentryBtn(${stuts.index});"></a>
                    </div>
                </td>
                <input name="tScIcChkstockbillentryList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].createName" type="hidden"
                       value="${poVal.createName }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].createBy" type="hidden"
                       value="${poVal.createBy }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].createDate" type="hidden"
                       value="${poVal.createDate }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].updateName" type="hidden"
                       value="${poVal.updateName }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].updateBy" type="hidden"
                       value="${poVal.updateBy }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].updateDate" type="hidden"
                       value="${poVal.updateDate }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].secUnitId" type="hidden"
                       value="${poVal.secUnitId }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].basicUnitId" type="hidden"
                       value="${poVal.basicUnitId }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].secQty" type="hidden"
                       value="${poVal.secQty }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].fid" type="hidden" value="${poVal.fid }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].findex" type="hidden"
                       value="${poVal.findex }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].itemId" type="hidden"
                       value="${poVal.itemId }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].stockId" type="hidden"
                       value="${poVal.stockId }"/>
                <input name="tScIcChkstockbillentryList[${stuts.index }].idSrc" type="hidden" value="${poVal.idSrc }"/>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].itemNo"
                           type="text" class="inputxt popup-select" style="width:105px;"
                           onkeypress="keyDownInfo('${stuts.index}','item',event)"
                           onblur="orderListStockBlur('${stuts.index}','itemId','itemNo');"
                           value="${poVal.itemNo }">
                    <label class="Validform_label" style="display: none;background-color: rgb(226,226,226)">商品编号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].itemName" readonly="readonly"
                           type="text" class="inputxt" style="width:180px;background-color: rgb(226,226,226)"
                           value="${poVal.itemName }">
                    <label class="Validform_label" style="display: none;">商品名称</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].model" readonly="readonly"
                           type="text" class="inputxt" style="width:85px;background-color: rgb(226,226,226)"
                           value="${poVal.model }">
                    <label class="Validform_label" style="display: none;">规格</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].barCode" readonly="readonly"
                           type="text" class="inputxt" style="width:65px;background-color: rgb(226,226,226)"
                           value="${poVal.barCode }">
                    <label class="Validform_label" style="display: none;">条形码</label>
                </td>
                    <%--<td align="left" bgcolor="white">--%>
                    <%--<input name="tScIcChkstockbillentryList[${stuts.index }].stockName" maxlength="32"--%>
                    <%--type="text" class="inputxt"  style="width:65px;background-color: rgb(226,226,226)" readonly="readonly"--%>
                    <%--value="${poVal.stockName }">--%>
                    <%--<label class="Validform_label" style="display: none;">仓库</label>--%>
                    <%--</td>--%>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].batchNo" maxlength="100"
                           type="text" class="inputxt" style="width:80px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.batchNo }">
                    <label class="Validform_label" style="display: none;">批号</label>
                </td>
                <td align="left" bgcolor="white">
                    <input id="unitId[${stuts.index }]" name="tScIcChkstockbillentryList[${stuts.index }].unitId"
                           maxlength="32"
                           type="text" style="width:50px;"
                           class="easyui-combobox"
                           data-options="valueField: 'id',textField: 'text',width:54,panelHeight: 'auto',disabled:true,editable: false,url:'tScIcitemController.do?loadItemUnit&id=${poVal.itemId}'"
                           value="${poVal.unitId }">
                    <label class="Validform_label" style="display: none;">单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].qty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.qty }">
                    <label class="Validform_label" style="display: none;">账面箱数</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].smallQty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.smallQty }">
                    <label class="Validform_label" style="display: none;">账面散数</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].basicQty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.basicQty }">
                    <label class="Validform_label" style="display: none;">账面数量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].coefficient" maxlength="20"
                           type="text" class="inputxt" style="width:65px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.coefficient }">
                    <label class="Validform_label" style="display: none;">换算率</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].chkQty" maxlength="20"
                           type="text" class="inputxt" style="width:65px;" onchange="changeQty(${stuts.index})"
                           datatype="vFloat" value="${poVal.chkQty }">
                    <label class="Validform_label" style="display: none;">箱数</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].chkSmallQty" maxlength="20"
                           type="text" class="inputxt" style="width:65px;" onchange="changeQty(${stuts.index})"
                           datatype="vFloat" value="${poVal.chkSmallQty }">
                    <label class="Validform_label" style="display: none;">散数</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].chkBasicQty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226);"
                           readonly="readonly"
                            value="${poVal.chkBasicQty }">
                    <label class="Validform_label" style="display: none;">盘点数量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].diffQty" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.diffQty }">
                    <label class="Validform_label" style="display: none;">差异数量</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].costPrice" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.costPrice }">
                    <label class="Validform_label" style="display: none;">成本单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].amount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.amount }">
                    <label class="Validform_label" style="display: none;">账面金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].chkAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.chkAmount }">
                    <label class="Validform_label" style="display: none;">盘点金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].diffAmount" maxlength="20"
                           type="text" class="inputxt" style="width:70px;background-color: rgb(226,226,226)"
                           readonly="readonly"
                           value="${poVal.diffAmount }">
                    <label class="Validform_label" style="display: none;">差异金额</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScIcChkstockbillentryList[${stuts.index }].note" maxlength="80"
                           type="text" class="inputxt" style="width:180px;" value="${poVal.note }">
                    <label class="Validform_label" style="display: none;">备注</label>
                </td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>
