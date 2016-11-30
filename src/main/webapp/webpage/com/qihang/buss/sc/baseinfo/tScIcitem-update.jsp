<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>商品主表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <%--<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>--%>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
      setValOldIdAnOldName($("#supplierName"), '${tScIcitemPage.supplierID}', '${tScIcitemPage.supplierName}');
      setValOldIdAnOldName($("#stockName"), '${tScIcitemPage.stockID}', '${tScIcitemPage.stockName}');
      var iSKFPeriodVal = $('select[name="iskfPeriod"]').val();
      if(iSKFPeriodVal != "" && iSKFPeriodVal == 'Y'){
          //保质期

          $("#kfPeriod").removeAttr("readonly");
          $("#kfPeriod").css("background-color","#ffffff");
          $("#kfPeriod").attr('datatype','n');
          $("#kfPeriod").next().text("*");
          //保质期类型
          $('select[name="kfDateType"]').removeAttr("disabled");
          $('select[name="kfDateType"]').removeAttr("style");
          $('select[name="kfDateType"]').attr("datatype","*");
          $('select[name="kfDateType"]').next().text("*");

      }else{
          //保质期
          $("#kfPeriod").removeAttr('datatype');
          $("#kfPeriod").next().text("");
          $("#kfPeriod").css("background-color","rgb(226, 226, 226)");
          $("#kfPeriod").val("")
          $("#kfPeriod").attr("readonly","true");
          //保质期类型
          $('select[name="kfDateType"]').val("");
          $('select[name="kfDateType"]').removeAttr("datatype");
          $('select[name="kfDateType"]').next().text("");
          $('select[name="kfDateType"]').css("background-color","rgb(226, 226, 226)");
          $('select[name="kfDateType"]').attr("disabled","true");
      }
  });
 </script>
     <style >
         body{
             position: absolute;
             width: 100%;
             height: 100%;
         }
         .footlabel{
             float: left;
             margin-left: 15px;
         }

         .footspan{
             float: left;
             margin-left: 5px;
             margin-right: 5px;
             font-weight: bold;
             color: grey;
             margin-bottom: 5px;
         }
     </style>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tScIcitemController.do?doUpdate" beforeSubmit="checkMsg">
					<input id="id" name="id" type="hidden" value="${tScIcitemPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tScIcitemPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tScIcitemPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tScIcitemPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tScIcitemPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tScIcitemPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tScIcitemPage.updateDate }">
					<input id="parentID" name="parentID" type="hidden" value="${tScIcitemPage.parentID }">
					<input id="level" name="level" type="hidden" value="${tScIcitemPage.level }">
					<input id="disabled" name="disabled" type="hidden" value="${tScIcitemPage.disabled }">
          <input id="supplierID" name="supplierID" type="hidden"  value="${tScIcitemPage.supplierID}">
          <input id="stockID" name="stockID" type="hidden" value="${tScIcitemPage.stockID}"/>
          <input id="version" name="version" type="hidden" value="${tScIcitemPage.version}">
          <input id="count" name="count" type="hidden" value="${tScIcitemPage.count}">
          <input id="tranType" name="tranType" type="hidden" value="${tranType}">
          <input id="load" value="${load}" type="hidden">
	<table cellpadding="0" cellspacing="1" class="formtable"  style="position: absolute;z-index: 50;width: 100%">
        <tr>
            <td align="right" width="10%">
                <label class="Validform_label">编号:</label>
            </td>
            <td class="value" width="23%">
                <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
                       datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点" value="${tScIcitemPage.number}">
          <span class="Validform_checktip">
              *
          </span>
                <label class="Validform_label" style="display: none;">编号</label>
            </td>
            <td align="right" width="10%">
                <label class="Validform_label">名称 :</label>
            </td>
            <td class="value" width="23%">
                <input id="name" name="name" type="text" style="width: 150px" class="inputxt"
                       datatype="*1-50" value="${tScIcitemPage.name}">
          <span class="Validform_checktip">
              *
          </span>
                <label class="Validform_label" style="display: none;">名称 </label>
            </td>
            <%--<td  rowspan="3" colspan="2" align="center"><img src="${tScIcitemPage.imageSrc}" height="90" />--%>
            <%--</td>--%>
            <td align="right" width="10%">
                <label class="Validform_label">简称:</label>
            </td>
            <td class="value" width="23%">
                <input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"  readonly="true" value="${tScIcitemPage.shortName}"
                >
                <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">简称</label>
            </td>
        </tr>
       <tr>

            <td align="right" width="10%">
                <label class="Validform_label">规格:</label>
            </td>
            <td class="value" width="24%">
                <input id="model" name="model" type="text" style="width: 150px" class="inputxt" datatype="*1-126" ignore="ignore" value="${tScIcitemPage.model}"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">规格</label>
            </td>

           <td align="right">
               <label class="Validform_label">指导价:</label>
           </td>
           <td class="value">
               <input id="costPrice" name="costPrice" type="text" style="width: 150px;" class="inputxt" datatype="num" value="${tScIcitemPage.costPrice}"
               >
               <span class="Validform_checktip">*
                </span>
               <label class="Validform_label" style="display: none;">指导价</label>
           </td>
           <td align="right">
               <label class="Validform_label">存货类型:</label>
           </td>
           <td class="value">
               <t:dictSelect field="itemType" type="list"
                             typeGroupCode="SC_STOCK_TYPE" hasLabel="false" showDefaultOption="false"
                             title="存货类型" extendJson="{datatype:*}" defaultVal="${tScIcitemPage.itemType}" ></t:dictSelect>
               <span class="Validform_checktip">*
                </span>
               <label class="Validform_label" style="display: none;">存货类型</label>
                   <%--<span id="btnshow" style="border: 1px solid #003bb3">&nbsp;&nbsp;+&nbsp;&nbsp;</span>--%>

           </td>

       </tr>

        <tr>
            <td align="right">
                <label class="Validform_label">计价方法:</label>
            </td>
            <td class="value">
                <t:dictSelect field="track" type="list" showDefaultOption="false"
                              typeGroupCode="SC_PRICE_METHOD" hasLabel="false"
                              title="计价方法" extendJson="{datatype:*}" defaultVal="${tScIcitemPage.track}"></t:dictSelect>
               <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">计价方法</label>
            </td>
            <td align="right">
                <label class="Validform_label">按保质期管理:</label>
            </td>
            <td class="value">
                <t:dictSelect field="iskfPeriod" type="list"
                              typeGroupCode="sf_yn" hasLabel="false"
                              title="按保质期管理" showDefaultOption="false" defaultVal="${tScIcitemPage.iskfPeriod}"></t:dictSelect>
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">按保质期管理</label>
            </td>
            <td align="right">
                <label class="Validform_label">保质期:</label>
            </td>
            <td class="value">
                <input id="kfPeriod" name="kfPeriod" type="text" style="width: 150px" class="inputxt" readonly="true" value="${tScIcitemPage.kfPeriod}"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">保质期</label>
                <span class="spanbtn-expand" id="btnExpand"></span>

            </td>

        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">保质期类型:</label>
            </td>
            <td class="value">
                <t:dictSelect field="kfDateType" type="list"
                              typeGroupCode="SC_DURA_DATE_TYPE" hasLabel="false"
                              title="保质期类型" extendJson="{'disabled':'true','style':'background-color:rgb(226, 226, 226)'}" defaultVal="${tScIcitemPage.kfDateType}"></t:dictSelect>
                <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">保质期类型</label>
            </td>
            <td align="right">
                <label class="Validform_label">启用批次管理:</label>
            </td>
            <td class="value">
                <t:dictSelect field="batchManager" type="list"
                              typeGroupCode="sf_yn" hasLabel="false" defaultVal="${tScIcitemPage.batchManager}" showDefaultOption="false"
                              title="启用批次管理" extendJson="{'datatype':'*'}"></t:dictSelect>
      <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">启用批次管理</label>
            </td>
            <td align="right">
                <label class="Validform_label">重量:</label>
            </td>
            <td class="value">
                <input id="weight" name="weight" type="text" style="width: 150px" class="inputxt" datatype="num" ignore="ignore" value="${tScIcitemPage.weight}"
                        >
                <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">重量</label>
            </td>
            </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">默认仓库:</label>
            </td>
            <td class="value">
                <input id="stockName" name="stockName" type="text" style="width: 150px" class="inputxt popup-select" value="${tScIcitemPage.stockName}"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">默认仓库</label>
            </td>
            <td align="right">
                <label class="Validform_label">主供应商:</label>
            </td>
            <td class="value">
                <input id="supplierName" name="supplierName" type="text" style="width: 150px" class="inputxt popup-select" value="${tScIcitemPage.supplierName}"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">主供应商</label>
            </td>
            <td align="right">
                <label class="Validform_label">产家:</label>
            </td>
            <td class="value">
                <input id="factory" name="factory" type="text" style="width: 150px" class="inputxt" datatype="*1-126" ignore="ignore" value="${tScIcitemPage.factory}"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">产家</label>
            </td>
        </tr>
       <tr>
            <td align="right">
                <label class="Validform_label">产地:</label>
            </td>
            <td class="value">
                <input id="producingPlace" name="producingPlace" type="text" value="${tScIcitemPage.producingPlace}" style="width: 150px" class="inputxt" datatype="*1-126" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">产地</label>
            </td>
           <td align="right">
           <label class="Validform_label">进项税:</label>
           </td>
           <td class="value">
               <input id="taxRateIn" name="taxRateIn" type="text" value="${tScIcitemPage.taxRateIn}" style="width: 150px" class="inputxt" datatype="num" ignore="ignore" value="${tScIcitemPage.taxRateIn }"
                       >
      <span class="Validform_checktip">
                </span>
               <label class="Validform_label" style="display: none;">进项税</label>
           </td>
           <td align="right">
               <label class="Validform_label">销项税:</label>
           </td>
           <td class="value">
               <input id="taxRateOut" name="taxRateOut" value="${tScIcitemPage.taxRateOut}" type="text" style="width: 150px" class="inputxt"  datatype="num" ignore="ignore" value="${tScIcitemPage.taxRateOut}"
                       >
      <span class="Validform_checktip">
                </span>
               <label class="Validform_label" style="display: none;">销项税</label>
           </td>
            </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">可组装/拆卸:</label>
            </td>
            <td class="value">
                <t:dictSelect field="isAssembly" type="list"
                              typeGroupCode="sf_yn" hasLabel="false" defaultVal="${tScIcitemPage.isAssembly}"  showDefaultOption="false"
                              title="可组装/拆卸" extendJson="{'datatype':'*'}" ></t:dictSelect>
      <span class="Validform_checktip">*
                </span>
                <label class="Validform_label" style="display: none;">可组装/拆卸</label>
            </td>
            <td align="right">
                <label class="Validform_label">助记码:</label>
            </td>
            <td class="value">
                <input id="shortNumber" name="shortNumber" value="${tScIcitemPage.shortNumber}" type="text" style="width: 150px" class="inputxt" datatype="*1-40" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">助记码</label>
            </td>
            <td align="right">
                <label class="Validform_label">批准文号:</label>
            </td>
            <td class="value">
                <input id="cultureNo" name="cultureNo" value="${tScIcitemPage.cultureNo}" type="text" style="width: 150px" class="inputxt" datatype="*1-126" ignore="ignore"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">批准文号</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">品牌:</label>
            </td>
            <td class="value">
                    <t:dictSelect field="brandID" type="list"
                                  typeGroupCode="SC_BRAND_TYPE" hasLabel="false"
                                  title="品牌" defaultVal="${tScIcitemPage.brandID}"></t:dictSelect>
              <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">品牌</label>
            </td>
            <td align="right">
                <label class="Validform_label">备注:</label>
            </td>
            <td class="value"colspan="3">
                <input id="note" name="note" type="text"  class="inputxt" style="width: 80%" value="${tScIcitemPage.note}"
                        >
      <span class="Validform_checktip">
                </span>
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </table>
			<div style="width: auto;height: 200px;margin-top: 120px;">
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tScIcitemController.do?tScItemPriceList&id=${tScIcitemPage.id}&count=${tScIcitemPage.count}" icon="icon-search" title="单位价格表" id="tScItemPrice"></t:tab>
				 <t:tab href="tScIcitemController.do?tScItemPhotoList&id=${tScIcitemPage.id}" icon="icon-search" title="商品图片表" id="tScItemPhoto"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
		<table style="display:none">
            <tbody id="add_tScItemPrice_table_template">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;" name="xh"></div>
                </td>
                <td align="center" bgcolor="white">
                  <div style="width: 80px;margin-top: -5px">
                    <a name="addLineTScItemPriceBtn[#index#]" id="addLineTScItemPriceBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScItemPriceBtn('#index#');"></a>
                    <a name="delLineTScItemPriceBtn[#index#]" id="delLineTScItemPriceBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScItemPriceBtn('#index#');"></a>
                  </div>
                </td>
                <td align="left" bgcolor="white">
                    <t:dictSelect field="tScItemPriceList[#index#].unitType"
                                  type="list"
                                  typeGroupCode="SC_UNIT_TYPE" hasLabel="false"
                                  title="单位类型" extendJson="{'datatype':'*'}" width="120px"></t:dictSelect>
                    <label class="Validform_label" style="display: none;">单位类型</label>
                </td>
                <td align="left" bgcolor="white">
                    <t:dictSelect field="tScItemPriceList[#index#].unitID"
                                  dictTable="t_sc_measureunit" dictField="id" dictText="name"
                                  type="list"
                                  hasLabel="false"
                                  title="单位" extendJson="{'datatype':'*'}" width="120px"></t:dictSelect>
                    <label class="Validform_label" style="display: none;">单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].coefficient" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" value="1"
                           datatype="/^([1-9][0-9]*(\.[0-9]{1,2})?|0\.0[1-9]|0\.[1-9]([0-9])?)$/" errormsg="请填写正数（最多两位小数）"
                            >
                    <label class="Validform_label" style="display: none;">单位换算率</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].barCode" maxlength="50"
                           type="text" class="inputxt" style="width:120px;" datatype="*1-25" ignore="ignore"

                            >
                    <label class="Validform_label" style="display: none;">条形码</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].defaultCG" class="inputxt"
                           type="checkbox"  style="width:120px;" value="1"

                            >
                    <label class="Validform_label" style="display: none;">默认采购单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].defaultXS" maxlength="32" class="inputxt"
                           type="checkbox"  style="width:120px;" value="1"
                            >
                    <label class="Validform_label" style="display: none;">默认销售单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].defaultCK"  style="width:120px;" class="inputxt"
                           type="checkbox" value="1"
                            >
                    <label class="Validform_label" style="display: none;">默认仓存单位</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].lsRetailPrice" maxlength="32"
                           type="text" class="inputxt" style="width:120px;"  datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','lsRetailPrice')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">零售价 </label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].lsMemberPrice" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','lsMemberPrice')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">会员价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].cgPrice" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','cgPrice')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">采购单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].cgPrice1" maxlength="32" datatype="num" ignore="ignore"
                           type="text" class="inputxt" style="width:120px;"
                           onblur="checkPrecision('#index#','cgPrice1')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">一级采购价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].cgPrice2" maxlength="32" datatype="num" ignore="ignore"
                           type="text" class="inputxt" style="width:120px;"
                           onblur="checkPrecision('#index#','cgPrice2')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">二级采购价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].cgPrice3" maxlength="32" datatype="num" ignore="ignore"
                           type="text" class="inputxt" style="width:120px;"
                           onblur="checkPrecision('#index#','cgPrice3')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">三级采购价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].cgLimitPrice" maxlength="32" datatype="num" ignore="ignore"
                           type="text" class="inputxt" style="width:120px;"
                           onblur="checkPrecision('#index#','cgLimitPrice')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">采购限价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].cgLatestPrice" maxlength="32" datatype="num" ignore="ignore"
                           type="text" class="inputxt" style="width:120px;background-color:rgb(226, 226, 226)"
                           onblur="checkPrecision('#index#','cgLatestPrice')" readonly="readonly"
                            >
                    <label class="Validform_label" style="display: none;">最近采购价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].xsPrice" maxlength="32"  datatype="num" ignore="ignore"
                           type="text" class="inputxt" style="width:120px;"
                           onblur="checkPrecision('#index#','xsPrice')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">销售单价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].xsPrice1" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','xsPrice1')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">一级销售价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].xsPrice2" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','xsPrice2')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">二级销售价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].xsPrice3" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','xsPrice3')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">三级销售价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].xsLimitPrice" maxlength="32"
                           type="text" class="inputxt" style="width:120px;" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','xsLimitPrice')"
                           onchange="setAllAmount(this);"
                            >
                    <label class="Validform_label" style="display: none;">销售限价</label>
                </td>
                <td align="left" bgcolor="white">
                    <input name="tScItemPriceList[#index#].xsLatestPrice" maxlength="32"
                           type="text" class="inputxt" style="width:120px;background-color:rgb(226, 226, 226)" datatype="num" ignore="ignore"
                           onblur="checkPrecision('#index#','xsLatestPrice')" readonly="readonly"
                            >
                    <label class="Validform_label" style="display: none;">最近销售价 </label>
                </td>
            </tr>
            </tbody>
            <tbody id="add_tScItemPhoto_table_template">
            <tr>
                <td align="center" bgcolor="white">
                    <div style="width: 25px;" name="xh"></div>
                </td>
                <td align="center"  bgcolor="white">
                  <div style="width: 80px;background-color: white;">
                    <a name="addLineTScItemPhotoBtn[#index#]" id="addLineTScItemPhotoBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true" onclick="clickAddTScItemPhotoBtn('#index#');"></a>
                    <a name="delLineTScItemPhotoBtn[#index#]" id="delLineTScItemPhotoBtn[#index#]" href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-remove'"  plain="true" onclick="clickDelTScItemPhotoBtn('#index#');"></a></div>
                </td>
                <td align="left" bgcolor="white">
                    <input type="hidden" id="tScItemPhotoList[#index#].filePath"
                           name="tScItemPhotoList[#index#].filePath" style="width: 150px"/>
                    <a target="_blank" id="tScItemPhotoList[#index#].filePath_href">未上传</a>
                    <br>
                    <input class="ui-button" type="button" value="上传附件"
                           onclick="commonUpload(commonUploadDefaultCallBack,'tScItemPhotoList\\[#index#\\]\\.filePath')"/>
                    <label class="Validform_label" style="display: none;">文件存放地址</label>
                </td>
            </tr>
            </tbody>
		</table>
  <div style="position: absolute;bottom: 10px;left:10px;" id="footdiv">

  </div>

 </body>
 <script src = "webpage/com/qihang/buss/sc/baseinfo/tScIcitem.js"></script>	