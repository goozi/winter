<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
  var CFG_PRICE = $("#CFG_UNITP_RICE").val();//单价精度
  $('#addTScItemPriceBtn').linkbutton({
    iconCls: 'icon-add'
  });
  $('#delTScItemPriceBtn').linkbutton({
    iconCls: 'icon-remove'
  });
  //	$('#addTScItemPriceBtn').bind('click', function(){
  // 		 var tr =  $("#add_tScItemPrice_table_template tr").clone();
  //	 	 $("#add_tScItemPrice_table").append(tr);
  //	 	 resetTrNum('add_tScItemPrice_table');
  //	 	 return false;
  //    });
  function clickAddTScItemPriceBtn(rowIndex) {
    if(Object.prototype.toString.call(rowIndex)  === "[object String]"){
      //若传进来的是字符串转为数值型
      rowIndex = parseInt(rowIndex);
    }
    var tr = $("#add_tScItemPrice_table_template tr").clone();
//        $("#add_tScItemPrice_table").append(tr);
    $("#add_tScItemPrice_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScItemPrice_table');
    rowIndex = rowIndex+1;
    $("input[name='tScItemPriceList[" + rowIndex + "].lsRetailPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].lsMemberPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice1']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice2']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice3']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].cgLimitPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].cgLatestPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice1']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice2']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice3']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].xsLimitPrice']").val(0.0.toFixed(CFG_PRICE));
    $("input[name='tScItemPriceList[" + rowIndex + "].xsLatestPrice']").val(0.0.toFixed(CFG_PRICE));
  }

  function clickDelTScItemPriceBtn(rowIndex) {
    $("#add_tScItemPrice_table").find(">tr").eq(rowIndex).remove();
    resetTrNum('add_tScItemPrice_table');
  }
  //	$('#delTScItemPriceBtn').bind('click', function(){
  //      	$("#add_tScItemPrice_table").find("input:checked").parent().parent().remove();
  //        resetTrNum('add_tScItemPrice_table');
  //        return false;
  //    });
  function disableBtn() {
    $('a[name^="addTScItemPriceBtn"]').each(function () {
      $(this).linkbutton('disable');
    });
    $('a[name^="delTScItemPriceBtn"]').each(function () {
      $(this).linkbutton('disable');
    });

  }
  $(document).ready(function () {
    $(".datagrid-toolbar").parent().css("width", "auto");
    if (location.href.indexOf("load=detail") != -1) {
      $(":input").attr("disabled", "true");
      $(".datagrid-toolbar").hide();
      setTimeout(disableBtn, 200);
    }
    //将表格的表头固定
    $("#tScItemPrice_table").createhftable({
      height: (h-50)+'px',
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
    //初始化
    if($("input[name='tScItemPriceList[0].lsRetailPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].lsRetailPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].lsMemberPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].lsMemberPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].cgPrice']").val() == ""){
    $("input[name='tScItemPriceList[0].cgPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].cgPrice1']").val() == "") {
      $("input[name='tScItemPriceList[0].cgPrice1']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].cgPrice2']").val() == "") {
      $("input[name='tScItemPriceList[0].cgPrice2']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].cgPrice3']").val() == "") {
      $("input[name='tScItemPriceList[0].cgPrice3']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].cgLimitPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].cgLimitPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].cgLatestPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].cgLatestPrice']").val(0.0.toFixed(CFG_PRICE))
    }
    if($("input[name='tScItemPriceList[0].xsPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].xsPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].xsPrice1']").val() == "") {
      $("input[name='tScItemPriceList[0].xsPrice1']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].xsPrice2']").val() == "") {
      $("input[name='tScItemPriceList[0].xsPrice2']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].xsPrice3']").val() == "") {
      $("input[name='tScItemPriceList[0].xsPrice3']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].xsLimitPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].xsLimitPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    if($("input[name='tScItemPriceList[0].xsLatestPrice']").val() == "") {
      $("input[name='tScItemPriceList[0].xsLatestPrice']").val(0.0.toFixed(CFG_PRICE));
    }
    var count = $('#count').val();
    var load = $('#load').val();//区分编辑和复制
    if(count > 0 && load == 'edit' && ${fn:length(tScItemPriceList)  > 0 }){
      for(var rowIndex=0; rowIndex<${tScItemPriceList.size()}; rowIndex++){
        $("select[name='tScItemPriceList["+ rowIndex +"].unitID']").css("background","#ddd");
        $("select[name='tScItemPriceList["+ rowIndex +"].unitID']").attr("disabled","true");
        if(rowIndex > 0) {
          $("select[name='tScItemPriceList[" + rowIndex + "].unitType']").css("background", "#ddd");
          $("select[name='tScItemPriceList[" + rowIndex + "].unitType']").attr("disabled", "true");
        }
      }
    }
    if(${fn:length(tScItemPriceList)  > 0 }){
      for(var rowIndex=0; rowIndex<${tScItemPriceList.size()}; rowIndex++){
        //取值后控制精度
        var lsRetailPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].lsRetailPrice']").val()).toFixed(CFG_PRICE);
        var lsMemberPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].lsMemberPrice']").val()).toFixed(CFG_PRICE);
        var cgPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].cgPrice']").val()).toFixed(CFG_PRICE);
        var cgPrice1 = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].cgPrice1']").val()).toFixed(CFG_PRICE);
        var cgPrice2 = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].cgPrice2']").val()).toFixed(CFG_PRICE);
        var cgPrice3 = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].cgPrice3']").val()).toFixed(CFG_PRICE);
        var cgLimitPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].cgLimitPrice']").val()).toFixed(CFG_PRICE);
        var cgLatestPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].cgLatestPrice']").val()).toFixed(CFG_PRICE);
        var xsPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].xsPrice']").val()).toFixed(CFG_PRICE);
        var xsPrice1 = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].xsPrice1']").val()).toFixed(CFG_PRICE);
        var xsPrice2 = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].xsPrice2']").val()).toFixed(CFG_PRICE);
        var xsPrice3 = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].xsPrice3']").val()).toFixed(CFG_PRICE);
        var xsLimitPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].xsLimitPrice']").val()).toFixed(CFG_PRICE);
        var xsLatestPrice = (Number)($("input[name='tScItemPriceList[" + rowIndex + "].xsLatestPrice']").val()).toFixed(CFG_PRICE);
        $("input[name='tScItemPriceList[" + rowIndex + "].lsRetailPrice']").val(lsRetailPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].lsMemberPrice']").val(lsMemberPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice']").val(cgPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice1']").val(cgPrice1);
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice2']").val(cgPrice2);
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice3']").val(cgPrice3);
        $("input[name='tScItemPriceList[" + rowIndex + "].cgLimitPrice']").val(cgLimitPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].cgLatestPrice']").val(cgLatestPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice']").val(xsPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice1']").val(xsPrice1);
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice2']").val(xsPrice2);
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice3']").val(xsPrice3);
        $("input[name='tScItemPriceList[" + rowIndex + "].xsLimitPrice']").val(xsLimitPrice);
        $("input[name='tScItemPriceList[" + rowIndex + "].xsLatestPrice']").val(xsLatestPrice);
        //取值后的项加入onblur方法校验精度
        $("input[name='tScItemPriceList[" + rowIndex + "].lsRetailPrice']").attr("onblur","checkPrecision('0','lsRetailPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].lsMemberPrice']").attr("onblur","checkPrecision('0','lsMemberPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice']").attr("onblur","checkPrecision('0','cgPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice1']").attr("onblur","checkPrecision('0','cgPrice1')");
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice2']").attr("onblur","checkPrecision('0','cgPrice2')");
        $("input[name='tScItemPriceList[" + rowIndex + "].cgPrice3']").attr("onblur","checkPrecision('0','cgPrice3')");
        $("input[name='tScItemPriceList[" + rowIndex + "].cgLimitPrice']").attr("onblur","checkPrecision('0','cgLimitPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].cgLatestPrice']").attr("onblur","checkPrecision('0','cgLatestPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice']").attr("onblur","checkPrecision('0','xsPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice1']").attr("onblur","checkPrecision('0','xsPrice1')");
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice2']").attr("onblur","checkPrecision('0','xsPrice2')");
        $("input[name='tScItemPriceList[" + rowIndex + "].xsPrice3']").attr("onblur","checkPrecision('0','xsPrice3')");
        $("input[name='tScItemPriceList[" + rowIndex + "].xsLimitPrice']").attr("onblur","checkPrecision('0','xsLimitPrice')");
        $("input[name='tScItemPriceList[" + rowIndex + "].xsLatestPrice']").attr("onblur","checkPrecision('0','xsLatestPrice')");
      }
    }
  });
</script>
<table border="0" cellpadding="2" cellspacing="1" id="tScItemPrice_table" style="background-color: #cbccd2" totalRow="true">
  <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
    <td align="center" bgcolor="#476f9a">序号</td>
    <td align="center" bgcolor="#476f9a">操作</td>
    <td align="center" bgcolor="#476f9a">
      单位类型
    </td>
    <td align="center" bgcolor="#476f9a">
      单位
    </td>
    <td align="center" bgcolor="#476f9a">
      单位换算率
    </td>
    <td align="center" bgcolor="#476f9a">
      条形码
    </td>
    <td align="center" bgcolor="#476f9a">
      默认采购单位
    </td>
    <td align="center" bgcolor="#476f9a">
      默认销售单位
    </td>
    <td align="center" bgcolor="#476f9a">
      默认仓存单位
    </td>
    <td align="center" bgcolor="#476f9a" total="true">
      零售价
    </td>
    <td align="center" bgcolor="#476f9a">
      会员价
    </td>
    <td align="center" bgcolor="#476f9a">
      采购单价
    </td>
    <td align="center" bgcolor="#476f9a">
      一级采购价
    </td>
    <td align="center" bgcolor="#476f9a">
      二级采购价
    </td>
    <td align="center" bgcolor="#476f9a">
      三级采购价
    </td>
    <td align="center" bgcolor="#476f9a">
      采购限价
    </td>
    <td align="center" bgcolor="#476f9a">
      最近采购价
    </td>
    <td align="center" bgcolor="#476f9a">
      销售单价
    </td>
    <td align="center" bgcolor="#476f9a">
      一级销售价
    </td>
    <td align="center" bgcolor="#476f9a">
      二级销售价
    </td>
    <td align="center" bgcolor="#476f9a">
      三级销售价
    </td>
    <td align="center" bgcolor="#476f9a">
      销售限价
    </td>
    <td align="center" bgcolor="#476f9a">
      最近销售价
    </td>

  </tr>
  <tbody id="add_tScItemPrice_table">
  <c:if test="${fn:length(tScItemPriceList)  <= 0 }">
    <tr>
      <td align="center" bgcolor="#F6FCFF">
        <div style="width: 25px;background-color: white;" name="xh">1</div>
      </td>
      <td align="center" bgcolor="white">
        <div style="width: 80px;background-color: white;">
          <a name="addTScItemPriceBtn[0]" id="addTScItemPriceBtn[0]" href="#" class="easyui-linkbutton"
             data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScItemPriceBtn(0);"></a>
        </div>
      </td>
      <input name="tScItemPriceList[0].id" type="hidden"/>
      <input name="tScItemPriceList[0].createName" type="hidden"/>
      <input name="tScItemPriceList[0].createBy" type="hidden"/>
      <input name="tScItemPriceList[0].createDate" type="hidden"/>
      <input name="tScItemPriceList[0].updateName" type="hidden"/>
      <input name="tScItemPriceList[0].updateBy" type="hidden"/>
      <input name="tScItemPriceList[0].updateDate" type="hidden"/>
      <input name="tScItemPriceList[0].itemID" type="hidden"/>
      <input name="tScItemPriceList[0].defaultSC" type="hidden"/>
      <input name="tScItemPriceList[0].unitType" type="hidden" value="0001"/>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].unitTypeName" maxlength="32"
               type="text" class="inputxt" style="width:120px; background-color: rgb(226, 226, 226);" value="基本单位"
               readonly="true"/>
        <label class="Validform_label" style="display: none;">单位类型</label>
      </td>
      <td align="left" bgcolor="white">
        <t:dictSelect field="tScItemPriceList[0].unitID"
                      dictTable="t_sc_measureunit" dictField="id" dictText="name"
                      type="list"
                      hasLabel="false"
                      title="单位" extendJson="{'datatype':'*'}" width="120px"></t:dictSelect>
        <label class="Validform_label" style="display: none;">单位</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].coefficient" maxlength="32"
               type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);" value="1"
               readonly="true">
        <label class="Validform_label" style="display: none;">单位换算率</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].barCode" maxlength="50"
               type="text" class="inputxt" style="width:120px;"

        >
        <label class="Validform_label" style="display: none;">条形码</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].defaultCG"
               type="checkbox" style="width:120px;"
               checked="true" value="1"
        >
        <label class="Validform_label" style="display: none;">默认采购单位</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].defaultXS"
               type="checkbox" style="width:120px;" checked="true" value="1"
        >
        <label class="Validform_label" style="display: none;">默认销售单位</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].defaultCK"
               type="checkbox" style="width:120px;" checked="true" value="1"
        >
        <label class="Validform_label" style="display: none;">默认仓存单位</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].lsRetailPrice" maxlength="32" onblur="checkPrecision('0','lsRetailPrice')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">零售价 </label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].lsMemberPrice" maxlength="32" onblur="checkPrecision('0','lsMemberPrice')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">会员价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].cgPrice" maxlength="32" onblur="checkPrecision('0','cgPrice')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">采购单价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].cgPrice1" maxlength="32"  onblur="checkPrecision('0','cgPrice1')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >

        <label class="Validform_label" style="display: none;">一级采购价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].cgPrice2" maxlength="32" onblur="checkPrecision('0','cgPrice2')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">二级采购价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].cgPrice3" maxlength="32" onblur="checkPrecision('0','cgPrice3')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">三级采购价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].cgLimitPrice" maxlength="32" onblur="checkPrecision('0','cgLimitPrice')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">采购限价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].cgLatestPrice" maxlength="32" onblur="checkPrecision('0','cgLatestPrice')"
               type="text" class="inputxt" style="width:120px;background-color:rgb(226, 226, 226)" datatype="num" ignore="ignore"
               readonly="readonly"
        >
        <label class="Validform_label" style="display: none;">最近采购价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].xsPrice" maxlength="32" onblur="checkPrecision('0','xsPrice')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">销售单价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].xsPrice1" maxlength="32" onblur="checkPrecision('0','xsPrice1')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">一级销售价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].xsPrice2" maxlength="32" onblur="checkPrecision('0','xsPrice2')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">二级销售价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].xsPrice3" maxlength="32" onblur="checkPrecision('0','xsPrice3')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">三级销售价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].xsLimitPrice" maxlength="32" onblur="checkPrecision('0','xsLimitPrice')"
               type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
               onchange="setAllAmount(this);"
        >
        <label class="Validform_label" style="display: none;">销售限价</label>
      </td>
      <td align="left" bgcolor="white">
        <input name="tScItemPriceList[0].xsLatestPrice" maxlength="32" onblur="checkPrecision('0','xsLatestPrice')"
               type="text" class="inputxt" style="width:120px;background-color:rgb(226, 226, 226)" datatype="num" ignore="ignore"
            readonly="readonly"
        >
        <label class="Validform_label" style="display: none;">最近销售价 </label>
      </td>
    </tr>
  </c:if>
  <c:if test="${fn:length(tScItemPriceList)  > 0 }">
    <c:forEach items="${tScItemPriceList}" var="poVal" varStatus="stuts">
      <tr>
        <input name="tScItemPriceList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
        <input name="tScItemPriceList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
        <input name="tScItemPriceList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
        <input name="tScItemPriceList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
        <input name="tScItemPriceList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
        <input name="tScItemPriceList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
        <input name="tScItemPriceList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
        <input name="tScItemPriceList[${stuts.index }].itemID" type="hidden" value="${poVal.priceToIcItem.id}"/>
        <input name="tScItemPriceList[${stuts.index }].defaultSC" type="hidden" value="${poVal.defaultSC }"/>
        <input name="tScItemPriceList[${stuts.index }].version" type="hidden" value="${poVal.version}"/>
        <td align="center" bgcolor="white">
          <div style="width: 30px;background-color: white;" name="xh">${stuts.index+1 }</div>
        </td>
        <td align="center" bgcolor="white">
          <div style="width: 80px;background-color: white;"><a name="addTScItemPriceBtn[${stuts.index}]"
                                                               id="addTScItemPriceBtn[${stuts.index}]"
                                                               href="#" class="easyui-linkbutton"
                                                               data-options="iconCls:'icon-add'" plain="true"
                                                               onclick="clickAddTScItemPriceBtn(${stuts.index});"></a>
            <c:if test="${count eq '0'}">
              <a name="delTScItemPriceBtn[${stuts.index}]" id="delTScItemPriceBtn[${stuts.index}]" href="#"
                 class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                 onclick="clickDelTScItemPriceBtn(${stuts.index});"></a>
            </c:if>
          </div>
        </td>
        <c:if test="${stuts.index ==0}">
          <td align="left" bgcolor="white">
            <input name="tScItemPriceList[${stuts.index}].unitType" value="${poVal.unitType }" type="hidden"/>
            <input name="tScItemPriceList[${stuts.index}].unitTypeName" maxlength="32"
                   type="text" class="inputxt" style="width:120px; background-color: rgb(226, 226, 226);" value="基本单位"
                   readonly="true"/>
            <label class="Validform_label" style="display: none;">单位类型</label>
          </td>
        </c:if>
        <c:if test="${stuts.index >0}">
          <td align="left" bgcolor="white">
            <t:dictSelect field="tScItemPriceList[${stuts.index}].unitType"
                          type="list"
                          typeGroupCode="SC_UNIT_TYPE" defaultVal="${poVal.unitType}" hasLabel="false"
                          title="单位类型" extendJson="{'datatype':'*'}" width="120px"></t:dictSelect>
            <label class="Validform_label" style="display: none;">单位类型</label>
          </td>
        </c:if>
        <td align="left" bgcolor="white">
          <t:dictSelect field="tScItemPriceList[${stuts.index}].unitID"
                        dictTable="t_sc_measureunit" dictField="id" dictText="name"
                        type="list" defaultVal="${poVal.measureunitToIcItemEntity.id}"
                        hasLabel="false"
                        title="单位" extendJson="{'datatype':'*'}" width="120px"></t:dictSelect>
          <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left" bgcolor="white">
          <c:if test="${stuts.index == 0}">
            <input name="tScItemPriceList[${stuts.index}].coefficient" maxlength="32"
                   type="text" class="inputxt" style="width:120px;background-color: rgb(226, 226, 226);"
                   value="${poVal.coefficient}" readonly="true">
          </c:if>
          <c:if test="${stuts.index > 0}">
            <input name="tScItemPriceList[${stuts.index}].coefficient" maxlength="32"
                   type="text" class="inputxt" style="width:120px;" value="${poVal.coefficient}"
                   datatype="num"
            >
          </c:if>
          <label class="Validform_label" style="display: none;">单位换算率</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].barCode" maxlength="50"
                 type="text" class="inputxt" style="width:120px;" value="${poVal.barCode}" datatype="*1-25"
                 ignore="ignore"/>
          <label class="Validform_label" style="display: none;">条形码</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].defaultCG" class="inputxt"
                 type="checkbox" style="width:120px;" value="1"
                 <c:if test="${poVal.defaultCG == 1}">checked</c:if>/>
          <label class="Validform_label" style="display: none;">默认采购单位</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].defaultXS" maxlength="32" class="inputxt"
                 type="checkbox" style="width:120px;" value="1"
                 <c:if test="${poVal.defaultXS == 1}">checked</c:if>
          >
          <label class="Validform_label" style="display: none;">默认销售单位</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].defaultCK" style="width:120px;" class="inputxt"
                 type="checkbox" value="1"
                 <c:if test="${poVal.defaultCK == 1}">checked</c:if>
          >
          <label class="Validform_label" style="display: none;">默认仓存单位</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].lsRetailPrice" maxlength="32" value="${poVal.lsRetailPrice}"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore" onchange="setAllAmount(this);"
          />
          <label class="Validform_label" style="display: none;">零售价 </label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].lsMemberPrice" maxlength="32"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                 value="${poVal.lsMemberPrice}" onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">会员价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].cgPrice" maxlength="32" value="${poVal.cgPrice}"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore" onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">采购单价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].cgPrice1" maxlength="32" datatype="num" ignore="ignore"
                 value="${poVal.cgPrice1}"
                 type="text" class="inputxt" style="width:120px;" onchange="setAllAmount(this);"

          >
          <label class="Validform_label" style="display: none;">一级采购价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].cgPrice2" maxlength="32" datatype="num" ignore="ignore"
                 value="${poVal.cgPrice2}"
                 type="text" class="inputxt" style="width:120px;" onchange="setAllAmount(this);"

          >
          <label class="Validform_label" style="display: none;">二级采购价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].cgPrice3" value="${poVal.cgPrice3}" maxlength="32"
                 datatype="num" ignore="ignore"
                 type="text" class="inputxt" style="width:120px;" onchange="setAllAmount(this);"

          >
          <label class="Validform_label" style="display: none;">三级采购价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].cgLimitPrice" value="${poVal.cgLimitPrice}" maxlength="32"
                 datatype="num" ignore="ignore"
                 type="text" class="inputxt" style="width:120px;" onchange="setAllAmount(this);"

          >
          <label class="Validform_label" style="display: none;">采购限价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].cgLatestPrice" value="${poVal.cgLatestPrice}" maxlength="32"
                 datatype="num" ignore="ignore"
                 type="text" class="inputxt" style="width:120px;background-color:rgb(226, 226, 226)" readonly="readonly"

          >
          <label class="Validform_label" style="display: none;">最近采购价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].xsPrice" value="${poVal.xsPrice}" maxlength="32" datatype="num"
                 ignore="ignore"
                 type="text" class="inputxt" style="width:120px;"
                 onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">销售单价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].xsPrice1" value="${poVal.xsPrice1}" maxlength="32"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                 onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">一级销售价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].xsPrice2" value="${poVal.xsPrice2}" maxlength="32"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                 onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">二级销售价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].xsPrice3" value="${poVal.xsPrice3}" maxlength="32"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                 onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">三级销售价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].xsLimitPrice" value="${poVal.xsLimitPrice}" maxlength="32"
                 type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                 onchange="setAllAmount(this);"
          >
          <label class="Validform_label" style="display: none;">销售限价</label>
        </td>
        <td align="left" bgcolor="white">
          <input name="tScItemPriceList[${stuts.index}].xsLatestPrice" value="${poVal.xsLatestPrice}" maxlength="32"
                 type="text" class="inputxt" style="width:120px;background-color:rgb(226, 226, 226)" datatype="num" ignore="ignore"
              readonly="readonly"
          >
          <label class="Validform_label" style="display: none;">最近销售价 </label>
        </td>


      </tr>
    </c:forEach>
  </c:if>
  </tbody>
</table>
<script>

  function initUnit(){
    $("#add_tScItemPrice_table").on('change', 'select[name$=".unitID"]', function () {
       var danUnid = $(this).val();
       var num =0;
        $("#add_tScItemPrice_table").find('select[name$=".unitID"]').each(function(){
              if($(this).val() == danUnid){
                num = num + 1;
              }
        });
      if(num >1){
        tip("单位不能选一样的");
        return false;
      }
    });
  }
  initUnit();

  /* $('input[name$="defaultCG"]').click(function(){alert(4343)});*/
  /* $("#add_tScItemPrice_table").on('click','input[name$="defaultCG"]', function() {
   debugger;
   alert(32);
   });*/
  /*  var t=  $("#add_tScItemPrice_table");
   $("#add_tScItemPrice_table").find('input[name$="defaultCG"]').click(function(){
   alert(323);
   })*/

  /**
   *默认仓存单位只能选择一个
   *
   * */
//  $("#add_tScItemPrice_table").on('click', 'input[name$="defaultCK"]', function () {
//    debugger;
//    var unitTypeVal = $(this).parent().parent().find('select[name$="unitType"]').val();
//    var addtemp = 0;
//    $("#add_tScItemPrice_table > tr").each(function (i, data) {
//      var val = $(data).find('input[name$="defaultCK"]').eq(0).attr("checked");
//      if (val == "checked") {
//        addtemp = addtemp + 1;
//      }
//    });
//    if (addtemp == 0) {
//      tip("请选择默认仓存单位");
//    } else if (addtemp > 1) {
//      $(this).removeAttr('checked');
//      tip("默认仓存单位只能选择一个");
//    } else if (unitTypeVal != "" && unitTypeVal == "0003") {
//      $(this).removeAttr('checked');
//      tip("辅助单位的单位类型不能做为默认仓存单位");
//    }
//  });
  $("#add_tScItemPrice_table").on('click', 'input[name$="defaultCK"]', function () {
    debugger;
    var unitTypeVal = $(this).parent().parent().find('select[name$="unitType"]').val();
    var addtemp = 0;
    $("#add_tScItemPrice_table > tr").each(function (i, data) {
      var val = $(data).find('input[name$="defaultCK"]').eq(0).attr("checked");
      if (val == "checked") {
        $(data).find('input[name$="defaultCK"]').removeAttr("checked");
        addtemp = addtemp + 1;
      }
    });
    $(this).attr('checked','checked');
    if (addtemp == 0) {
      tip("请选择默认仓存单位");
    } else if (addtemp > 1) {
//      $(this).removeAttr('checked');
//      tip("默认仓存单位只能选择一个");
    } else if (unitTypeVal != "" && unitTypeVal == "0003") {
      $(this).removeAttr('checked');
      tip("辅助单位的单位类型不能做为默认仓存单位");
    }
  });


  /**
   *默认销售单位只能选择一个
   *
   * */
  $("#add_tScItemPrice_table").on('click', 'input[name$="defaultXS"]', function () {
    var unitTypeVal = $(this).parent().parent().find('select[name$="unitType"]').val();
    var addtemp = 0;
    $("#add_tScItemPrice_table > tr").each(function (i, data) {
      var val = $(data).find('input[name$="defaultXS"]').eq(0).attr("checked");
      if (val == "checked") {
        $(data).find('input[name$="defaultXS"]').removeAttr("checked");
        addtemp = addtemp + 1;
      }
    });
    $(this).attr('checked','checked');
    if (addtemp == 0) {
      tip("请选择默认销售单位");
    } else if (addtemp > 1) {
//      $(this).removeAttr('checked');
//      tip("默认销售单位只能选择一个");
    } else if (unitTypeVal != "" && unitTypeVal == "0003") {
      $(this).removeAttr('checked');
      tip("辅助单位的单位类型不能做为默认销售单位");
    }
  });

  /**
   *默认采购单位只能选择一个
   *
   * */
  $("#add_tScItemPrice_table").on('click', 'input[name$="defaultCG"]', function () {
    var unitTypeVal = $(this).parent().parent().find('select[name$="unitType"]').val();
    var addtemp = 0;
    $("#add_tScItemPrice_table > tr").each(function (i, data) {
      var val = $(data).find('input[name$="defaultCG"]').eq(0).attr("checked");
      if (val == "checked") {
        $(data).find('input[name$="defaultCG"]').removeAttr("checked");
        addtemp = addtemp + 1;
      }
    });
    $(this).attr('checked','checked');
    if (addtemp == 0) {
      tip("请选择默认采购单位");
    } else if (addtemp > 1) {
//      $(this).removeAttr('checked');
//      tip("默认采购单位只能选择一个");
    } else if (unitTypeVal != "" && unitTypeVal == "0003") {
      $(this).removeAttr('checked');
      tip("辅助单位的单位类型不能做为默认采购单位");
    }
  });
  /***
   * 对单位类型的判断
   * 基本单位每个商品有且只能有一个基本单位
   * 常用单位每个商品可以有N个常用位，也可以一个也没有
   * 辅助单位每个商品最多只能一个辅助单位，也可以没有
   */
  $("#add_tScItemPrice_table").on('change', 'select[name$="unitType"]', function () {
    var basicUnitTypeCount = 0;
    var assistUnitTypeCount = 0;//辅助单位
    $("#add_tScItemPrice_table > tr:gt(0)").each(function (i, data) {
      var val = $(data).find('select[name$="unitType"]').eq(0).val();
      if (val != "" && val == "0001") {
        basicUnitTypeCount += 1;
      }
      if (val != "" && val == "0003") {
        assistUnitTypeCount += 1;
      }
    });
    if (basicUnitTypeCount >= 1) {
      tip("一个商品只能有一个基本单位,请重新输入！");
    }
    if (assistUnitTypeCount > 1) {
      tip("一个商品只能有一个辅助单位,请重新输入！");
    }
  });
</script>