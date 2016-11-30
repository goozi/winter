<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>账套设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    $(document).ready(function () {
      $('#tt').tabs({
        onSelect: function (title) {
          $('#tt .panel-body').css('width', 'auto');
        }
      });
      $(".tabs-wrap").css('width', '100%');
        //给时间控件加上样式
        $("#accountStartDate").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM'});
            updateDescription();//更新默认的账套名称
        });
        //设置数据库类型和驱动类为只读，不能用disabled（后台会读不到数据）
        //$("[name='tSDataSourceList[0].dbType']").attr("disabled","disabled");
        //$("[name='tSDataSourceList[0].driverClass']").attr("disabled","disabled");
        $("[name='tSDataSourceList[0].dbType']").css("display","none");
        $("[name='tSDataSourceList[0].driverClass']").css("display","none");
        //非oracle时实例名与实体库相同，只读
        var dbType = $("[name='tSDataSourceList[0].dbType']").val();
        if (dbType!="oracle"){
            $("[name='tSDataSourceList[0].dbName']").attr("readonly","readonly");
            $("[name='tSDataSourceList[0].dbName']").val("");
        }
//       //初始年份下拉列表
//        var accountStartYear = $("#accountStartYear");
//        $(accountStartYear).combobox({
//            valueField:'year',
//            textField:'year',
//            panelHeight:'auto'
//        });
//        var data = [];//创建年度数组
//        var startYear;//起始年份
//        var thisYear=new Date().getUTCFullYear();//今年
//        var endYear=parseInt(thisYear) + 1;//结束年份
//        //数组添加值（2012-2016）//根据情况自己修改
//        for(startYear=endYear-4;startYear<endYear;startYear++ ){
//            data.push({"year":startYear});
//        }
//        $(accountStartYear).combobox("loadData",data);//下拉框加载数据
//        $(accountStartYear).combobox("setValue",thisYear);//设置默认值为今年
//        //初始化月份下拉列表
//        var accountStartMonth = $("#accountStartMonth");
//        $(accountStartMonth).combobox({
//            valueField:'month',
//            textField:'month',
//            panelHeight:'auto'
//        });
//        var dataMonth = [];//创建月份数组
//        var startMonth = 1;//起始月份
//        var thisMonth=new Date().getMonth();//今月
//        var endMonth=12+1;//结束月份
//        //数组添加值（1-12）//根据情况自己修改
//        for(startMonth;startMonth<endMonth;startMonth++ ){
//            dataMonth.push({"month":startMonth});
//        }
//        $(accountStartMonth).combobox("loadData",dataMonth);//下拉框加载数据
//        $(accountStartMonth).combobox("setValue",thisMonth);//设置默认值为今月

    });
    //dbKey变化时，计算url
    function doDbkeyChange(){
        var dbKey = $("#dbKey").val();
        //var olddbKey = $("#olddbKey").val();
        //var url = $("#url").val();
        //url = url.replace(olddbKey,dbKey);
        //$("#url").val(url);
        var dbType = $("[name='tSDataSourceList[0].dbType']").val();
        if (dbType!="oracle"){
            $("[name='tSDataSourceList[0].dbName']").val(dbKey);
        }
        //$("#olddbKey").val(dbKey);
    }
      //公司名称或启用年月发生值发生变化时，自动生成默认的账套名称和实体库
      function  updateDescriptionAndDbkey(){
          //调用ajax提取公司名称每个汉字的首字母做为实体库
          var name = $("#companyName").val();
          if(name!=null&&name!="") {
              $.ajax({
                  type: 'POST',
                  url: 'tScIcitemController.do?getShortNameDelSpecialChar',//'tScIcitemController.do?getShortName',
                  async: false,
                  data: {'name':name},
                  dataType: 'json',
                  success:function(data){
                      if(data.success == true){
                          var pingyinname = data.obj;
                          $("#dbKey").val(pingyinname);
                      }
                  }
              });
          }else{
              $("#dbKey").val("");
          }
          //更新实例名
          doDbkeyChange();
          //生成默认的账套名称
          updateDescription();
      }
      //生成默认的账套名称
      function updateDescription(){
          var companyName = $("#companyName").val();
          var accountStartDate = $("#accountStartDate").val();
          $("[name='tSDataSourceList[0].description']").val(companyName+accountStartDate.replace("-",""));
      }
  </script>

  <style >
    /** 页脚样式  **/
    body{
      position: absolute;
      width: 100%;
      height: 100%;
    }
    .footlabel{
      float: left;
      margin-left: 35px;
    }

    .footspan{
      float: left;
      margin-left: 5px;
      margin-right: 5px;
      font-weight: bold;
      color: grey;
      margin-bottom: 5px;
      text-decoration: underline;
    }
    </style>
</head>
<body style="overflow-x: hidden;">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1"
             action="tScAccountConfigController.do?doAddInit">
      <input id="id" name="id" type="hidden"
             value="${tScAccountConfigPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScAccountConfigPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScAccountConfigPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScAccountConfigPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScAccountConfigPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScAccountConfigPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScAccountConfigPage.updateDate }">
      <input id="olddbKey" name="olddbKey" type="hidden"
           value="${tSDataSourcePage.dbKey }">
  <table cellpadding="0" cellspacing="1" class="formtable" style="margin: 0px auto;width:600px;">
      <tr>
          <td colspan="4" align="center" height="30px"><label class="Validform_label">企业信息</label></td>
      </tr>
      <tr>
    <td align="right" width="10%">
      <label class="Validform_label">公司名称:</label>
    </td>
    <td class="value" width="23%">
        <input id="companyName" name="companyName" type="text" style="width: 150px" class="inputxt" datatype="*" onchange="updateDescriptionAndDbkey()"><span style="color: red">*</span>
        <span class="Validform_checktip"></span>
      <label class="Validform_label" style="display: none;">公司名称</label>
    </td>
          <td align="right" width="10%">
              <label class="Validform_label">联系人:</label>
          </td>
          <td class="value" width="23%">
              <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">联系人</label>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">税号:</label>
          </td>
          <td class="value">
              <input id="taxNumber" name="taxNumber" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">税号</label>
          </td>
          <td align="right">
              <label class="Validform_label">银行账号:</label>
          </td>
          <td class="value">
              <input id="bankAccount" name="bankAccount" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">银行账号</label>
          </td>
      </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">公司地址:</label>
    </td>
    <td class="value" colspan="3">
          <input id="companyAddress" name="companyAddress" type="text" style="width: 91.5%" class="inputxt">
          <span class="Validform_checktip"></span>
      <label class="Validform_label" style="display: none;">公司地址</label>
    </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">电话:</label>
          </td>
          <td class="value">
              <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">电话</label>
          </td>
          <td align="right">
              <label class="Validform_label">传真:</label>
          </td>
          <td class="value">
              <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">传真</label>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">公司网址:</label>
          </td>
          <td class="value">
              <input id="companyUrl" name="companyUrl" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">公司网址</label>
          </td>
          <td align="right">
              <label class="Validform_label">E_Mail:</label>
          </td>
          <td class="value">
              <input id="email" name="email" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">E_Mail</label>
          </td>
          </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">注册号:</label>
          </td>
          <td class="value">
              <input id="registrationNumber" name="registrationNumber" type="text" style="width: 150px" class="inputxt">
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">注册号</label>
          </td>
          <td></td>
          <td></td>
      </tr>
      <tr>
          <td colspan="4" align="center" height="30px"><label class="Validform_label">建账信息</label></td>
      </tr>
      <tr>
    <td align="right">
      <label class="Validform_label">实体库:</label>
    </td>
    <td class="value">
          <input id="dbKey" name="dbKey" type="text" style="width: 150px" class="inputxt" datatype="s2-20,w1" onchange="doDbkeyChange()" validType="t_sc_account_config,dbKey,id,dataSource_jeecg"><span style="color: red">*</span>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">实体库</label>
    </td>
          <td align="right" width="15%">
              <label class="Validform_label">
                  账套名称:
              </label>
          </td>
          <td class="value" colspan="1">
              <input name="tSDataSourceList[0].description" maxlength="50" width="340px"  type="text" class="inputxt"  datatype="*" validType="t_s_data_source,description,id,dataSource_jeecg"><span style="color: red">*</span>
              <label class="Validform_label" style="display: none;">账套名称</label>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label"><!--账套-->启用年月:</label>
          </td>
          <td class="value">
              <input id="accountStartDate" name="accountStartDate" maxlength="20" type="text" style="width:80px;" value="<fmt:formatDate value='${tScAccountConfigPage.accountStartDate}' type="date" pattern="yyyy-MM"/>" datatype="*"><span style="color: red">*</span>
              <label class="Validform_label" style="display: none;">账套启用年月</label>
              <%--<input class="easyui-combobox ui-text" id="accountStartYear" name="accountStartYear" style="width:150px" >--%>
                <%--<span class="Validform_checktip">--%>
                <%--</span>--%>
              <%--<label class="Validform_label" style="display: none;">账套启用年份</label>--%>
              <%--<input id="accountStartMonth" name="accountStartMonth" type="text" style="width: 150px" class="inputxt">--%>
                <%--<span class="Validform_checktip">--%>
                <%--</span>--%>
              <%--<label class="Validform_label" style="display: none;">账套启用月份</label>--%>
          </td>
          <td></td>
          <td></td>
          </tr>
      <tr style="display: none">
    <td align="right">
      <label class="Validform_label">负库存选项:</label>
    </td>
    <td class="value" colspan="3">
          <input id="minusInventoryAccount" name="minusInventoryAccount" type="checkbox" value="1"><font color="red">允许允许负库存结账</font>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">允许负库存结账</label>
    <%--</td>--%>
    <%--<td align="right">--%>
      <%--<label class="Validform_label">允许负库存出库:</label>--%>
    <%--</td>--%>
    <%--<td class="value">--%>
          <input id="minusInventorySl" name="minusInventorySl" type="checkbox" value="1"><font color="red">允许负库存出库</font>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">允许负库存出库</label>
    </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  数据库地址:
              </label>
          </td>
          <td class="value">
              <input name="tSDataSourceList[0].dbIp" maxlength="40" type="text" class="inputxt" value="${tSDataSourcePage.dbIp}" datatype="*"  ><span style="color: red">*(Ip)</span>
              <label class="Validform_label" style="display: none;">数据库地址</label>
          </td>
          <td align="right">
              <label class="Validform_label">
                  数据库端口:
              </label>
          </td>
          <td class="value">
              <input name="tSDataSourceList[0].dbPort" maxlength="50" type="text" style="width: 40px" class="inputxt" value="${tSDataSourcePage.dbPort}" datatype="*" ><%--${tSDataSourcePage.dbPassword}--%>
              <label class="Validform_label" style="display: none;">数据库端口</label>
              实例名：<input name="tSDataSourceList[0].dbName" maxlength="50" type="text" style="width: 52px" class="inputxt" value="${tSDataSourcePage.dbName}" datatype="*" ><span style="color: red">*</span>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  数据库用户:
              </label>
          </td>
          <td class="value">
              <input name="tSDataSourceList[0].dbUser" maxlength="50" type="text" class="inputxt" value="${tSDataSourcePage.dbUser}" datatype="*"  ><span style="color: red">*<!--(请使用有创建新数据库、用户、并可以授权的用户，如mysql建议使用root用户,sql server建议使用sa用户,oracle建议使用system用户)--></span>
              <label class="Validform_label" style="display: none;">数据库用户</label>
          </td>
          <td align="right">
              <label class="Validform_label">
                  数据库密码:
              </label>
          </td>
          <td class="value">
              <input name="tSDataSourceList[0].dbPassword" maxlength="50" type="password" class="inputxt" value="" datatype="*" ><span style="color: red">*</span>
              <label class="Validform_label" style="display: none;">数据库密码</label>
          </td>
      </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  驱动类:
              </label>
          </td>
          <td class="value">
              <t:dictSelect field="tSDataSourceList[0].driverClass" width="70" type="list"
                            showDefaultOption="true"
                            typeGroupCode="driverClass" defaultVal="${tSDataSourcePage.driverClass}" hasLabel="false" title="驱动类"></t:dictSelect>${tSDataSourcePage.driverClass}<span style="color: red">*</span>
              <label class="Validform_label" style="display: none;">驱动类</label>
          </td>
          <td align="right">
              <label class="Validform_label">
                  数据库类型:
              </label>
          </td>
          <td class="value">
              <t:dictSelect field="tSDataSourceList[0].dbType" width="70" type="list"
                            showDefaultOption="true"
                            typeGroupCode="dbtype" defaultVal="${tSDataSourcePage.dbType}" hasLabel="false" title="数据库类型"></t:dictSelect>${tSDataSourcePage.dbType}<span style="color: red">*</span>
              <label class="Validform_label" style="display: none;">数据库类型</label>
          </td>
      </tr>
  </table>
  <%--<div style="width: auto;height: 200px;">--%>
    <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
    <%--<div style="width:800px;height:1px;"></div>--%>
    <%--<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">--%>
      <%--<t:tab href="tScAccountConfigController.do?tSDataSourceList&id=${tScAccountConfigPage.id}"--%>
             <%--icon="icon-search" title="数据源" id="tSDataSource"></t:tab>--%>
    <%--</t:tabs>--%>
  <%--</div>--%>
</t:formvalid>
<!-- 添加 附表明细 模版 -->
<table style="display:none">
  <tbody id="add_tSDataSource_table_template">
  <tr>
    <td align="center" bgcolor="white">
      <div style="width: 30px;" name="xh"></div>
    </td>
    <td align="center" bgcolor="white">
      <div style="width: 80px;">
        <a name="addTSDataSourceBtn[#index#]" id="addTSDataSourceBtn[#index#]" href="#" onclick="clickAddTSDataSourceBtn('#index#');"></a>
        <a name="delTSDataSourceBtn[#index#]" id="delTSDataSourceBtn[#index#]" href="#"  onclick="clickDelTSDataSourceBtn('#index#');"></a>
      </div>
    </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].dbKey" maxlength="50"
                         type="text" class="inputxt" style="width:120px;" datatype="*"/>
              <label class="Validform_label" style="display: none;">账套名称</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].description" maxlength="50"
                         type="text" class="inputxt" style="width:120px;" datatype="*"/>
              <label class="Validform_label" style="display: none;">数据源描述</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].driverClass" maxlength="50"
                         type="text" class="inputxt" style="width:120px;" datatype="*"/>
              <label class="Validform_label" style="display: none;">驱动类</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].url" maxlength="100"
                         type="text" class="inputxt" style="width:120px;" datatype="*"/>
              <label class="Validform_label" style="display: none;">url</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].dbUser" maxlength="50"
                         type="text" class="inputxt" style="width:120px;" datatype="*"/>
              <label class="Validform_label" style="display: none;">数据库用户</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].dbPassword" maxlength="50"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">数据库密码</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].dbType" maxlength="50"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">数据库类型</label>
            </td>
            <td align="left" bgcolor="white">
                  <input name="tSDataSourceList[#index#].accountId" maxlength="36"
                         type="text" class="inputxt" style="width:120px;"/>
              <label class="Validform_label" style="display: none;">账套Id</label>
            </td>
  </tr>
  </tbody>
</table>
<!-- 页脚显示 -->
<div style="position: absolute;bottom: 10px;left:10px;" id="footdiv">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tScAccountConfigInit.js"></script>