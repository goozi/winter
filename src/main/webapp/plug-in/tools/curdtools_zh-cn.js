var tab;
function getPinYin(str) {
  var pinyin;
  var urlStr = encodeURI("pinyinControl.do?doName&name=" + str);

  $.ajax({
    url: urlStr,
    success: function (data) {
      pinyin = data.obj;
    },
    async: false
  })
  return pinyin;
}
function setTip() {
  $('table').find("[tip]").each(function () {
    var defaultvalue = $(this).attr("tip");
    var altercss = $(this).attr("altercss");
    //var _name = $(this).attr('name').replace('#index#', '0');
    //$('input[name="' + _name + '"]').val(defaultvalue);
    //$('input[name="' + _name + '"]').addClass(altercss);
    if (!$(this).val()) {
      $(this).val(defaultvalue);
      $(this).addClass(altercss);
    }
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
        }
    );
  });
  //非查看页，则对弹出输入框加上样式及事件
  if (location.href.indexOf("load=detail") < 0) {
    $('.popup-select').each(function (index, obj) {
      // debugger;
      var w = $(obj).width();
      $(obj).width(w - 20);
      $(obj).css('float', 'left');
      $(obj).css('margin-right', '20px');
      var popupDiv = $('<div></div>');
      popupDiv.addClass('popupSelectDiv');
      if ($(obj).parents('div[id$="scrolldiv"]').length > 0) {
        popupDiv.height(16).width(18);
        popupDiv.css('background', 'url("plug-in/easyui/themes/icons/popup.png") 1px 6px no-repeat');
      } else {
        popupDiv.height(18).width(18);
        popupDiv.css('background', 'url("plug-in/easyui/themes/icons/popup.png") 1px 7px no-repeat');
      }
      popupDiv.css('background-size', '16px 4px');
      popupDiv.css('float', 'left');
      popupDiv.css('position', 'relative');
      popupDiv.css('border', '1px solid #a5aeb6');
      popupDiv.css('left', '-1px');
      popupDiv.css('margin-left', '-20px');

      $(obj).after(popupDiv);
      // debugger;
    });
    var e = $.Event('keypress');
    e.keyCode = 13;
    $(document).on('dblclick','.popup-select',function () {
      debugger;
      if(!this.readOnly) {
        $(this).trigger(e);
      }
    });
    $(document).on('click','.popupSelectDiv',function () {
      var obj = this.previousSibling;
      if(!obj.readOnly){
        $(obj).trigger(e);
      }
    });
  }
}
/**
 * 如果页面是详细查看页面，无效化所有表单元素，只能进行查看
 */
$(function () {
  //Zerrion 16-02-01 一对一，一对多子页载入完成后把所有tip显示出来
  setTimeout(setTip, 1500);
  if (location.href.indexOf("load=detail") != -1) {
    $(":input").attr("disabled", "true");
    setTimeout('$("a").attr("onclick","")', 1000);
    //20151207 zerrion 查看页面时去除所有必填标志*号
    $(".Validform_checktip").html('');
    //$(":input").attr("style","border:0;border-bottom:1 solid black;background:white;");
    //hjhadd20160809如果当前账套未开账或已经结账，不允许单据管理操作
    billbeforeaccount();
  }
  //Zerrion 15-11-15--25 页面载入时把所有只读输入框背景设为灰色
  $(':input[readonly="readonly"]').css('background-color', '#e2e2e2');
  //单据复制时，重置或清空部分初始数据
  var url = location.href;
  var urliffcopy = url.indexOf("load=fcopy");
  if (urliffcopy >= 0) {
    billcopy();
  }
  //if ($('.formtable tr').length > 3) {
  if ($('#btnExpand').length>0 && $('.formtable tr').length > 3) {
    //页面载入时默认将大于3的主表行隐藏
    $(".formtable tr").each(function (i, data) {
      if (i > 2) {
        $(this).toggle();
      }
    });
    $('#btnExpand').toggle(
        function () {
          $('#btnExpand').removeClass('spanbtn-expand');
          $('#btnExpand').addClass('spanbtn-collapse');
          $(".formtable tr").each(function (i, data) {
            if (i > 2) {
              $(this).toggle();
            }
          });
        },
        function () {
          $('#btnExpand').removeClass('spanbtn-collapse');
          $('#btnExpand').addClass('spanbtn-expand');
          $(".formtable tr").each(function (i, data) {
            if (i > 2) {
              $(this).toggle();
            }
          });
        }).css('cursor','pointer');
  }else{
    $('#btnExpand').hide();
  }
  //调用控制左边导航快捷桌面的按钮是否可点击
  navigatebeforeaccount();
});

/**
 * 根据导航页面的beforeAccount隐藏域值来控制左边导航快捷桌面的按钮是否可点击
 *   值为true表示按表示如果当前账套已开账未结账才允许单据操作，
 *   值为init表示当前账套未开账才允许单据操作（用于初始化的几种单据）
 *   未设置值（默认为空字符串)即为不需要当前账套检查
 *   隐藏域属性 exception表示页面内例外不做控制的链接A的class(如初始化导航中结束初始化不做控制)
 * @author hjh 20160908
 */
function navigatebeforeaccount(){
  var objname = "beforeAccount";
  if ($("#" + objname).length>0){
    var beforeAccount = $("#" + objname).val();
    var exception = $("#" + objname).attr("exception");
    if (beforeAccount!=null && beforeAccount!=""){
      var url = "tScAccountConfigController.do?navigatebeforeaccount";
      var formData = new Object();
      formData['beforeAccount'] = beforeAccount;
      $.ajax({
        url: url,
        async: false,
        cache: false,
        type: 'POST',
        data: formData,
        error: function () {// 请求失败处理函数
        },
        success: function (data) {
          if (data.success == false) {//不允许操作
            var unbindstr = "";
            $(".navigate").find("a").each(function (i){
              //$(this).attr("disabled","disabled");
              if (exception!=null && exception!="" && $(this).attr('class')==exception){
                //例外不做控制
              }else {
                $(this).removeAttr("onclick");//如果有点击属性配置则为空
                $(this).attr("msg", data.msg);
                unbindstr += '$(".' + $(this).attr('class') + '").unbind();';
                $(this).attr("onclick", "tip('" + data.msg + "');");//如果有点击属性配置则为空
              }
            });
            if (unbindstr!="") {
              //解绑事件
              setTimeout(unbindstr, 500);
            }
          }
        }
      });
    }
  }
}

/**
 * 如果当前账套未开账或已经结账，不允许单据管理操作
 * @author hjh 20160908
 */
function billbeforeaccount() {
  if (location.href.indexOf("accountIsAllowBillManage=1") != -1) {
    $("[data-options*=icon-add]").linkbutton("disable"); //旧版录入
    $("[data-options*=icon-edit]").linkbutton("disable"); //旧版编辑
    $("[data-options*=new-icon-add]").linkbutton("disable"); //新版录入
    $("[data-options*=new-icon-edit]").linkbutton("disable"); //新版编辑
    $("[data-options*=new-icon-cancellation]").linkbutton("disable"); //作废
    $("[data-options*=new-icon-uncancellation]").linkbutton("disable"); //反作废
    $("[data-options*=new-icon-audit]").linkbutton("disable"); //审核
    $("[data-options*=new-icon-unaudit]").linkbutton("disable"); //反审核
    $("[data-options*=icon-put]").linkbutton("disable"); //旧版导入
    $("[data-options*=new-icon-put]").linkbutton("disable"); //新版导入
    $("[data-options*=new-icon-copy]").linkbutton("disable"); //复制
    $("[data-options*=new-icon-chkstockbill]").linkbutton("disable"); //自动盘点
    $("[data-options*=new-icon-close]").linkbutton("disable"); //关闭
    $("[data-options*=new-icon-unclose]").linkbutton("disable"); //反关闭
    $("[data-options*=new-icon-copy]").linkbutton("disable"); //复制
    //解绑事件
    setTimeout('$("#unAuditBtn").unbind();' +
        '$("#auditBtn").unbind();' +
        '$("#edit").unbind();' +
        '$("#closedBtn").unbind();' +
        '$("#cancelBtn").unbind();' +
        '$("#unCancelBtn").unbind();' +
        '$("#copyBtn").unbind();' +
        '$("#add").unbind();' +
        '$("#aduitBtn").unbind();' +
        '$("#unAuditBtn").unbind();', 500);

  }
}

/**
 * 单据复制事件清除：生成新单据编号，单据日期为当天，制单人为当前登录人姓名，制单人ID为当前登录人ID，清除审核人、审核日期为空，审核状态为未审核，作废状态为未作废,
 *       创建人、创建人ID、创建时间、更新人、更新人ID、更新时间
 *       清空主表相关数据：表头主表ID,除初始化以外（经办、部门），来源单据相关（classIDSrc、idSrc、billNoSrc）
 *       清空子表相关数据：（附表明细）的主表ID、明细表ID，来源单据相关（idSrc、entryIdSrc、idOrder、entryIdOrder、classIdSrc、billNoOrder），属性字段名前加小数点
 * 注意：编辑页面必须要有tranType的表单域
 * @author hjh 20160908
 */
function billcopy() {
  var tranType = $("#tranType").val();//编辑页面必须要有tranType的表单域，且值为复制源表单的tranType
  if ($("#tranType").length==0){
    tranType = $("#trantype").val();
  }
  var billDateType = "";//新单据的单据日期的类型，默认是当天，init表示初始化时是用扎帐日期减1天
  var number = "";//源基础资料的编号
  var billNo = "";//源单据的单据编号
  if ($("#billDateType").length > 0) {
    billDateType = $("#billDateType").val();
  }
  if ($("#number").length > 0) {
    number = $("#number").val();
  }
  if ($("#billNo").length > 0) {
    billNo = $("#billNo").val();
  }
  var url = "tScBillController.do?goBillCopy&tranType=" + tranType + "&billDateType=" + billDateType;
  url += "&billNo=" + billNo + "&number=" + number;
  //传入源单的单据编号 或 源基础资料的编号
  $.ajax({
    url: url,
    type: 'post',
    cache: false,
    success: function (data) {
      //var d = $.parseJSON(data);
      var d = data;
      if (d.success) {
        var msg = d.msg;
        if (d.success) {//单据复制成功取到要清除或重置的值
          //通过js遍历obj对象的所有属性，并给其赋值，即后台把要清空或重置的数据按规则处理好，前台用规则来处理就好了(分为主表和子表，其中子表带小数点开头)。
          var formobj = $("#formobj");
          //$(formobj).form("load", d.obj);
          var jobj = d.obj;
          for (var fieldname in jobj) {
            var fieldvalue = jobj[fieldname];
            if (fieldname == "date") {
              $("[name='date']").val(fieldvalue);
            } else if (fieldname == "billNo") {
              $("[name='billNo']").val(fieldvalue);
            }
            if (fieldname.indexOf(":") >= 0) {//footdiv+冒号+要清除内容标题 表示页脚设置的重置字段
              //debugger;
              var fieldnameArray = fieldname.split(":");
              if (fieldnameArray.length>1){
                $("#" + fieldnameArray[0]).find(".footlabel").each(function(i){
                  if ($(this).text().indexOf(fieldnameArray[1])>=0){
                    $(this).parent().find("span").text(fieldvalue);
                  }
                });
              }else{
                $(".footlabel").each(function(i){
                  if ($(this).text().indexOf(fieldnameArray[1])>=0){
                    $(this).parent().find("span").text(fieldvalue);
                  }
                });
              }
            }else if (fieldname.indexOf(".") >= 0) {//点号开头表示子表的重置字段
              //清空子表相关数据：（附表明细）的主表ID、明细表ID，来源单据相关（idSrc、entryIdSrc、idOrder、entryIdOrder、classIdSrc、billNoOrder）
              //$("input[name$="+fieldname+"]").val(fieldvalue);
              var iobj = $("input[name$='" + fieldname + "']");
              if ($(iobj).length > 0) {
                $(iobj).val(fieldvalue);
              }
            } else {
              //重置为新数据：新单据编号、单据日期、制单人姓名、制单人ID、分支机构ID、分支机构名称、未审核状态、未作废状态，创建人、创建人ID、创建时间、更新人、更新人ID、更新时间
              //清空主表相关数据：表头主表ID，来源单据相关（classIDSrc、idSrc、billNoSrc）
              //$("#"+fieldname).val(fieldvalue);
              var iobj = $("#" + fieldname);
              if ($(iobj).length > 0) {
                $(iobj).val(fieldvalue);
              }
            }
          }
          //清除单据页面自定义的扩展清除属性
          var clearExt = "";//单据复制清除扩展属性串：多个用逗号间隔，其中主表为属性名，子表为小数点+属性名。
          if ($("#clearExt").length > 0) {
            clearExt = $("#clearExt").val();
            //  var clearExtArray = clearExt.split(",");
            //  for (var i=0;i<clearExtArray.length;i++){
            //    var fieldname = clearExtArray[i];
            //    var fieldvalue = "";
            //    if (fieldname!=""){
            //      if (fieldname.indexOf(".")>=0){
            //        //清空子表相关数据
            //        //$("input[name$="+fieldname+"]").val(fieldvalue);
            //        var iobj = $("input[name$='" + fieldname + "']");
            //        if ($(iobj).length > 0) {
            //          $(iobj).val(fieldvalue);
            //        }
            //      } else {
            //        //清空主表相关数据
            //        //$("#"+fieldname).val(fieldvalue);
            //        var iobj = $("#" + fieldname);
            //        if ($(iobj).length > 0) {
            //          $(iobj).val(fieldvalue);
            //        }
            //      }
            //    }
            //  }
            //更正声明：单据复制已经默认部分字段的设置，原约定在编辑页面增加id为clearExt的隐藏域为要扩展清除属性，现更正为当前单据需要调用扩展的重置函数。如应收初始化单据需要在单据复制后实现“应收日期要重置为当天的日期”，则在此表单域的值为其自定义的函数名，并在编辑页面内编写实现其功能的自定义函数。
            if (clearExt != "") {
              eval(clearExt + "()");//调用单据复制的扩展数据重置函数
            }
          }
          //根据单据日期更新单据日期选择框值
          if ($("[name='date']").length > 0) {
            var billDate = $("[name='date']").val();
            billDate = billDate.replace(/-/g, "/");
            var billDateArray = billDate.split(" ");
            if (billDateArray.length > 1) {//去掉日期后面的时间
              billDate = billDateArray[0];
            }
            var newdate = new Date(billDate);
            var year = newdate.getFullYear();
            var month = (newdate.getMonth() + 1) < 10 ? "0" + (newdate.getMonth() + 1) : (newdate.getMonth() + 1);
            var date = newdate.getDate() < 10 ? "0" + newdate.getDate() : newdate.getDate();
            $("#date").val(year + "-" + month + "-" + date);
          }
          //重置form url为新增doAdd
          var url = $(formobj).attr("action");
          url = url.replace("?doUpdate", "?doAdd");
          $(formobj).attr("action", url);
        }
        //tip(msg);//20190831婷婷:去掉单据复制提示
        //reloadTable();
        //$("#" + gname).datagrid('unselectAll');
        ids = '';
      }
    }
  });
}

/**
 * 单据保存草稿
 * @author hjh 20160908
 */
function doTemp(formid, tranType) {
  var formData = $("#" + formid).serializeArray();//自动将form表单封装成json
  var url = "tScBillTempController.do?doAddJson";
  var d = {};
  var d_form = {};
  var d_combobox = {};
  var d_combobox_itemid = {};
  var d_wdate = {};
  var d_ckeditor = {};
  var d_tt_tab = {};
  $.each(formData, function () {
    d_form[this.name] = this.value;
  });
  //手动下拉框、日期框、复文本框要单独取值，并放到json串里
  $("input.easyui-combobox,input.combobox-f").each(function (i) {
    var fieldname = this.name ? this.name : this.id;//$(this).attr("comboname")?$(this).attr("comboname"):this.id;
    //d_combobox[this.name ? this.name : this.id] = $(this).combobox('getValues');
    d_combobox[fieldname] = $(this).combobox('getValues');
    var itemIdobj = $(this).attr("comboname");//fieldname;//this.name ? this.name : this.id;
    itemIdobj = itemIdobj.replace(".unitiD",".unitId").replace(".unitid",".unitId").replace(".unitID",".unitId");//兼容单位id(支持unitId,unitID,unitid,unitiD)
    itemIdobj = itemIdobj.replace(".unitId", ".itemId");
    var itemId = "";
    //debugger;
    //兼容商品ID（支持itemId,itemID,itemid,itemiD)
    if ($("input[name='" + itemIdobj + "']").length>0){
      itemId = $("input[name='" + itemIdobj + "']").val();
    }else{
      itemIdobj = itemIdobj.replace(".itemId", ".itemID");
      if ($("input[name='" + itemIdobj + "']").length>0){
        itemId = $("input[name='" + itemIdobj + "']").val();
      }else{
        itemIdobj = itemIdobj.replace(".itemID", ".itemiD");
        if ($("input[name='" + itemIdobj + "']").length>0){
          itemId = $("input[name='" + itemIdobj + "']").val();
        }else{
          itemIdobj = itemIdobj.replace(".itemiD", ".itemid");
          itemId = $("input[name='" + itemIdobj + "']").val();
        }
      }
    }

    //d_combobox_itemid[this.name ? this.name : this.id] = itemId;
    d_combobox_itemid[fieldname] = itemId;
  });
  $("input.Wdate").each(function (i) {
    d_wdate[this.name ? this.name : this.id] = this.value;//日期以id为key
  });
  $("textarea").each(function (i) {
    if ($("#cke_" + this.id).length > 0) {
      d_ckeditor[this.name] = CHEDITOR.instance[this.name].getData();
    }
  });
  //手动获得有多少个附表，每个附表有多少行，目前只考虑 tab模式的附表
  //$("[id=tt]").find("tbody[id$='_table']").each(function(i){
  $("tbody[id$='_table']").each(function (i) {
    d_tt_tab[this.id] = $(this).find("tr").length;
  });
  //存放到大json对象d中
  d["d_form"] = d_form;
  d["d_combobox"] = d_combobox;
  d["d_combobox_itemid"] = d_combobox_itemid;
  d["d_wdate"] = d_wdate;
  d["d_ckeditor"] = d_ckeditor;
  d["d_tt_tab"] = d_tt_tab;
  $.ajax({
    type: "post",
    contentType: "application/json",
    url: url,
    data: "str=" + JSON.stringify(d),//{json: JSON.stringify(formData)},
    //data:"str="+json.stringify(formdata),
    dataType: 'json',
    success: function (result) {//回调函数
      alertTip("单据保存草稿成功");
      var preTitle = window.top.$('.tabs-selected:first').text();
      setTimeout("closetab('"+preTitle+"');",1000);
    }
  });
}
/**
 * 选择恢复单据草稿
 * @param formid
 * @param tranType
 * @author hjh 2016-9-8
 */
function doTempChooseDialog(formid, tranType) {
  //var itemName = $("#stockName").val();
  //debugger;
  var tranType = $("#tranType").val();
  if ($("#tranType").length==0){
    tranType = $("#trantype").val();
  }
  var url = 'vScBillTempController.do?goSelectDialog&tranType=' + tranType;
  var width = 1000;
  var height = 500;
  var title = "单据草稿";
  $.dialog({
    content: 'url:' + url,
    title: title,
    lock: true,
    zIndex: 600,
    height: height,
    cache: false,
    width: width,
    opacity: 0.3,
    button: [{
      name: '确定',
      callback: function () {
        iframe = this.iframe.contentWindow;
        var item = iframe.getSelectionData();
        var content = eval("(" + item.content + ")");
        //判断每个附表的行数是否达到保存草稿里的行号要求，不够的先加行号，再来给表单域赋值
        var content_tt_tab = content["d_tt_tab"];
        for (fieldname in content_tt_tab) {
          var fieldvalue = content_tt_tab[fieldname];
          var nextTrLength = $("#" + fieldname).find("tr").length;
          var itemLength = parseInt(fieldvalue);
          var newLength = itemLength - nextTrLength;// - 1;
          //先加不够的空行
          for (var j = 0; j < newLength; j++) {
            var newfunname = fieldname.replace("add_", "").replace("_table", "");
            newfunname = "clickAdd" + newfunname.substring(0, 1).toUpperCase() + newfunname.substring(1) + "Btn(" + j + ")";//Add后跟的附表ID首字母大写
            var f0 = eval("(" + newfunname + ")");//clickAddTScIcInitialentryBtn(j);
          }

          //将表格的表头固定
          //$(fieldname.replace("add_", "")).createhftable({
          //  height: (h-40)+'px',
          //  width:'auto',
          //  fixFooter:true
          //});
        }
        //将草稿数据通过form的load方法快速赋值
        var content_form = content["d_form"];
        $("#" + formid).form("load", content_form);
        //将隐藏域的单据编号赋值到显示的单据编号中
        $("#billNoStr").val($("#billNo").val());
        //手动从json中获取下拉框、日期框、复文本框取值并赋值
        var content_combobox = content["d_combobox"];
        var d_combobox_itemid = content["d_combobox_itemid"];
        //debugger;
        for (fieldname in content_combobox) {
          var fieldvalue = content_combobox[fieldname];
          //fieldname = fieldname.replace("[","\\[").replace("]","\\]");
          fieldname = "'" + fieldname + "'";
          if ($("input[id=" + fieldname + "]").length > 0) {
            var comobj = fieldname.replace("'", "").replace("'", "").replace("[", "\\[").replace("]", "\\]");//$("input[id=" + fieldname + "]").attr("id");
            var itemId = d_combobox_itemid[fieldname.replace("'", "").replace("'", "")];
            //$("#" + comobj).combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
            $("input[id=" + fieldname + "]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
            $("input[id=" + fieldname + "]").combobox("setValue", fieldvalue);
          } else {
            //debugger;
            var comobj = $("input[name=" + fieldname + "]").attr("id");
            var itemId = d_combobox_itemid[fieldname];
            $("#" + comobj).combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId);
            //$("#unitId\\[" + rowIndex + "\\]").combobox("reload", "tScIcitemController.do?loadItemUnit&id=" + itemId + "&timestamp=" + new Date().getTime());
            $("input[name=" + fieldname + "]").combobox("setValue", fieldvalue);
          }
        }
        //debugger;
        var strobjnames = '#qty, #basicQty, #secQty, #coefficient, #secCoefficient, #price, #amount, #discountRate, #discountPrice, #discountAmount, #taxRate, #taxPriceEx, #taxAmountEx, #taxPrice, #inTaxAmount, #taxAmount, #itemWeight';
        if ($(strobjnames).length>0) {
          //如果存在数量、基本数量、单价、金额等表单域时，在模拟输入框值改变事件前初始主表计算js金额相关工具类
          initComputingTools();
        }
        //重新对每个子表进行计算js金额初始化动作
        var content_tt_tab = content["d_tt_tab"];
        for (fieldname in content_tt_tab) {
          var tableobj = $("#" + fieldname);//内容表格
          var nextTrLength = $(tableobj).find("tr").length;
          var configName =fieldname.replace("add_", "").replace("_table", "") + "List"; //如 ，tPoOrderentryList
          var isExists = false;
          var arrobjnames = strobjnames.split(",");
          for(var i=0;i<arrobjnames.length;i++){
            if (arrobjnames[i]!=""){
              if ($("[name='" + configName+"[0]." + arrobjnames[i].replace("#","") + "']").length>0){
                isExists = true;
              }
            }
          }
          if (isExists){
            for (var i=0;i<nextTrLength;i++){
              //子表函数计算
              try{//如买一赠一有数量，但没有计算需求，就会报错，这里抛弃异常
                setFunctionInfo(i, configName);
              }catch(e){

              }

            }
          }
        }
        //判断每个附表的的标题列是否有total属性且值为true，如果有的话，对其列下所有单元格模拟change事件
        var content_tt_tab = content["d_tt_tab"];
        for (fieldname in content_tt_tab) {
          var tableobj = $("#" + fieldname);//内容表格
          var tablehead = $("#" + fieldname.replace("add_", "") + "scrolldivHead");//标题表格
          var nextTrLength = $(tableobj).find("tr").length;
          $(tablehead).find("td").each(function (i){
            if ($(this).attr("total")!=null && $(this).attr("total").toLowerCase()=="true") {
              //debugger;
              //对其列下所有单元格模拟input事件
              for (var j = 0; j < nextTrLength; j++) {
                var inputobj = $(tableobj).find("tr").eq(j).children("td:eq(" + i + ")").find("input");
                if (inputobj.length>0){
                  $(inputobj).trigger('input');//数量乘以单价算金额等
                  $(inputobj).trigger('change');//数量乘以单价算金额等
                  //$(inputobj).trigger('propertychange input change');//合计行
                }
              }
            }
          });
        }
        var content_wdate = content["d_wdate"];
        for (fieldname in content_wdate) {
          var fieldvalue = content_wdate[fieldname];
          var dateValue = "";
          fieldname = "'" + fieldname + "'";
          if (fieldvalue != "") {
            var dateValue = new Date(fieldvalue);
            var year = dateValue.getFullYear();
            var month = (dateValue.getMonth() + 1) < 10 ? "0" + (dateValue.getMonth() + 1) : (dateValue.getMonth() + 1);
            var date = dateValue.getDate() < 10 ? "0" + dateValue.getDate() : dateValue.getDate();
            dateValue = year + "-" + month + "-" + date;
          }
          if ($("input[name=" + fieldname + "]").length > 0) {
            $("input[name=" + fieldname + "]").val(dateValue);
          }
          if ($("input[id=" + fieldname + "]").length > 0) {
            $("input[id=" + fieldname + "]").val(dateValue);
          }
        }
        var content_ckeditor = content["d_ckeditor"];
        for (fieldname in content_ckeditor) {
          var fieldvalue = content_ckeditor[fieldname];
          CHEDITOR.instance[this.name].setData(fieldvalue);
        }
        //发送ajax到controller,删除被选中的单据草稿
        var url = "tScBillTempController.do?doBatchDel";
        $.ajax({
          url: url,
          type: 'post',
          data: {
            ids: item.id//单据草稿ID
          },
          cache: false,
          success: function (data) {
            //var d = $.parseJSON(data);
            var d = data;
            if (d.success) {
              var msg = d.msg;
              //tip(msg);
              //reloadTable();
              //$("#" + gname).datagrid('unselectAll');
              var tempRecoveryExt = "";//恢复草稿后留一个自定义函数来供各单据恢复后自定义脚本
              if ($("#tempRecoveryExt").length > 0) {
                tempRecoveryExt = $("#tempRecoveryExt").val();
                if (tempRecoveryExt != "") {
                  eval(tempRecoveryExt + "()");//调用单据恢复后的扩展函数
                }
              }
              tip("单据恢复草稿成功");
              ids = '';
            }
          }
        });
        //此方法用于 恢复草稿时调用自己再模块里写的方法，用于恢复明细一些空间被禁用时恢复不了
        //注意值提供方法名，具体业务根据情况来定
        if(typeof removeEventDraw!='undefined' && removeEventDraw instanceof Function){
          removeEventDraw();
        }

      },
      focus: true
    }, {
      name: '取消',
      callback: function () {
      }
    }]
  }).zindex();
}

/**
 * 增删改工具栏
 */
/*window.onerror = function() {
 return true;
 };*/
var iframe;// iframe操作对象
var win;//窗口对象
var gridname = "";//操作datagrid对象名称
var windowapi = frameElement == null ? null : frameElement.api, W = windowapi == null ? null : windowapi.opener;//内容页中调用窗口实例对象接口
var pathName = window.document.location.pathname;
var projectName = '..' + pathName.substring(0, pathName.substr(1).indexOf('/') + 1) + '/';
function upload(curform) {
  upload();
}
/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl  目标页面地址
 * @param windowType 窗口类型：dialog对话框，tab标签页方式打开
 */
function add(title, addurl, gname, width, height, windowType) {
  //Zerrion 2016-08-20
  //解决一个奇葩问题，当点击新增按钮打开新增页面时立即敲回车会连续打开新增页面
  //解决办法为手工把焦点移开
  // var evt = getEvent();
  // var target = evt.target;
  // while (target.tagName != null && target.tagName.toLowerCase() != "a" && target.tagName.toLowerCase() != "div") {
  //   target = target.parentNode;
  // }
  // target.blur();
  if (addurl.indexOf('cgFormBuildController.do?ftlForm') == 0) {
    var rowsData = $('#' + gname).datagrid('getSelections');
    if (rowsData.length > 0) {
      if (rowsData.length > 1) {
        tip('请只选择一条记录');
        return;
      }
      addurl += '&id=' + rowsData[0].id;
    }
  }
  gridname = gname;
  if (windowType == 'tab') {
    addOneTabForBiz(title, addurl);
  } else {
    createwindow(title, addurl, width, height);
  }
}
/**
 * 树列表添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function addTreeNode(title, addurl, gname) {
  if (rowid != '') {
    addurl += '&id=' + rowid;
  }
  gridname = gname;
  createwindow(title, addurl);
}
/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param url 目标页面地址
 * @param id 主键字段
 */
function update(title, url, id, width, height, windowType) {
  //Zerrion 2016-08-20
  //解决一个奇葩问题，当点击编辑按钮打开编辑页面时立即敲回车会连续打开编辑页面
  //解决办法为手工把焦点移开
  // debugger;
  // var evt = getEvent();
  // var target = evt.target;
  // while (target.tagName != null && target.tagName.toLowerCase() != "a" && target.tagName.toLowerCase() != "div") {
  //   target = target.parentNode;
  // }
  // target.blur();
  gridname = id;
  var rowsData = $('#' + id).datagrid('getSelections');
  if (!rowsData || rowsData.length == 0) {
    tip('请选择编辑项目');
    return;
  }
  if (rowsData.length > 1) {
    tip('请选择一条记录再编辑');
    return;
  }
  var sonId = $("#sonCompanyId").val();
  var selectSonId = "";
  if(rowsData[0].sonId){
    selectSonId = rowsData[0].sonId;
  }else if(rowsData[0].sonID){
    selectSonId = rowsData[0].sonID;
  }else if(rowsData[0].sonid){
    selectSonId = rowsData[0].sonid;
  }else if(rowsData[0].soniD){
    selectSonId = rowsData[0].soniD;
  }
  if(selectSonId && sonId != selectSonId){
    tip("不可编辑非本机构数据");
    return;
  }
  url += '&load=edit';//告诉编辑表单页面此次是编辑操作
  url += '&id=' + rowsData[0].id;
  if (windowType == 'tab') {
    var tranType = $("#tranType").val();
    //if(tranType) {
    //  var checkurl = "tSAuditController.do?checkIsEdit&billId="+rowsData[0].id+"&tranType="+tranType;
    //  $.ajax({
    //    url: checkurl,
    //    type: 'post',
    //    cache: false,
    //    success: function (d) {
    //      //var d = $.parseJSON(data);
    //      if (d.success) {
    //        addOneTabForBiz(title, url);
    //      }else{
    //        tip(d.msg);
    //      }
    //    }
    //  });
    //}else{
    addOneTabForBiz(title, url);
    //}
  } else {
    createwindow(title, url, width, height);
  }
}


/**
 * 查看详细事件打开窗口
 * @param title 查看框标题
 * @param url//目标页面地址
 * @param id//主键字段
 */
function detail(title, url, id, width, height, windowType) {
  //debugger;
  //Zerrion 2016-08-20
  //解决一个奇葩问题，当点击查看按钮打开查看页面时立即敲回车会连续打开查看页面
  //解决办法为手工把焦点移开
  // var evt = getEvent();
  // var target = evt.target;
  // while (target.tagName != null && target.tagName.toLowerCase() != "a" && target.tagName.toLowerCase() != "div") {
  //   target = target.parentNode;
  // }
  // target.blur();
  var rowsData = $('#' + id).datagrid('getSelections');
//	if (rowData.id == '') {
//		tip('请选择查看项目');
//		return;
//	}

  if (!rowsData || rowsData.length == 0) {
    tip('请选择查看项目');
    return;
  }
  if (rowsData.length > 1) {
    tip('请选择一条记录再查看');
    return;
  }
  url += '&load=detail&operation=goupdate&id=' + rowsData[0].id;
  createdetailwindow(title, url, width, height, windowType);
  //addOneTabForBiz(title, url);
}

/**
 * 单据复制事件打开窗口
 * @param title 查看框标题
 * @param url//目标页面地址
 * @param id//主键字段
 */
function fcopy(title, url, id, width, height, windowType) {
  //Zerrion 2016-08-20
  //解决一个奇葩问题，当点击查看按钮打开查看页面时立即敲回车会连续打开查看页面
  //解决办法为手工把焦点移开
  // var evt = getEvent();
  // var target = evt.target;
  // while (target.tagName != null && target.tagName.toLowerCase() != "a" && target.tagName.toLowerCase() != "div") {
  //   target = target.parentNode;
  // }
  // target.blur();
  var rowsData = $('#' + id).datagrid('getSelections');
//	if (rowData.id == '') {
//		tip('请选择查看项目');
//		return;
//	}

  if (!rowsData || rowsData.length == 0) {
    tip('请选择查看项目');
    return;
  }
  if (rowsData.length > 1) {
    tip('请选择一条记录再查看');
    return;
  }
  url += '&load=fcopy&id=' + rowsData[0].id;
  createdetailwindow(title, url, width, height, windowType);
  //addOneTabForBiz(title, url);
}


/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelect(title, url, gname) {
  gridname = gname;
  var ids = [];
  var rows = $("#" + gname).datagrid('getSelections');
  if (rows.length > 0) {
    $.dialog.confirm('你确定永久删除该数据吗?', function (r) {
      if (r) {
        for (var i = 0; i < rows.length; i++) {
          ids.push(rows[i].id);
        }
        $.ajax({
          url: url,
          type: 'post',
          data: {
            ids: ids.join(',')
          },
          cache: false,
          success: function (data) {
            //var d = $.parseJSON(data);
            var d = data;
            if (d.success) {
              var msg = d.msg;
              tip(msg);
              reloadTable();
              $("#" + gname).datagrid('unselectAll');
              ids = '';
            }
          }
        });
      }
    });
  } else {
    tip("请选择需要删除的数据");
  }
}

/**
 * 查看时的弹出窗口
 *
 * @param title
 * @param addurl
 * @param saveurl
 */
function createdetailwindow(title, addurl, width, height, windowtype, preTitle) {
  //查看页面url多传递（从左datagrid隐藏域）是否账套未开账或已结账，来控制查看页面的按钮是否显示
  addurl += '&accountIsAllowBillManage=' + $("#accountIsAllowBillManage").val();
  //debugger;
  var fcopyobj = $("[onclick^='fcopy(']");
  if (fcopyobj!=null && fcopyobj.length>0){//判断列表页面是否有复制按钮
    addurl += '&iscopy=true';
  }
  title = title.replace("复制","新增");
  if (windowtype == 'tab') {
    addurl = encodeURI(projectName + addurl);
    addOneTabForBiz(title, addurl, null, preTitle);
  } else {
    addurl = encodeURI(projectName + addurl);
    width = width ? width : 700;
    height = height ? height : 400;
    if (width == "100%" || height == "100%") {
      width = window.top.document.body.offsetWidth;
      height = window.top.document.body.offsetHeight - 100;
    }
    if (typeof(windowapi) == 'undefined') {
      $.dialog({
        content: 'url:' + addurl,
        lock: true,
        width: width,
        height: height,
        title: title,
        opacity: 0.3,
        cache: false,
        //cancelVal: '关闭',
        //cancel: true /*为true等价于function(){}*/
      }).zindex();
    } else {
      W.$.dialog({
        content: 'url:' + addurl,
        lock: true,
        width: width,
        height: height,
        parent: windowapi,
        title: title,
        opacity: 0.3,
        cache: false,
        //   cancelVal: '关闭',
        //   cancel: true /*为true等价于function(){}*/
      }).zindex();
    }
  }

}
/**
 * 全屏编辑
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function editfs(title, url) {
  var name = gridname;
  if (rowid == '') {
    tip('请选择编辑项目');
    return;
  }
  url += '&id=' + rowid;
  openwindow(title, url, name, 800, 500);
}
// 删除调用函数
function delObj(url, name, id) {
  if (window.beforeDelFunc) {
    var flag = beforeDelFunc.apply(this, [id]);
    if (!flag) {
      return;
    }
  }
  url = encodeURI(url);
  gridname = name;
  createdialog('删除确认 ', '确定删除该记录吗 ?', url, name);
}
// 删除调用函数
function confuploadify(url, id) {
  $.dialog.confirm('确定删除吗', function () {
    deluploadify(url, id);
  }, function () {
  }).zindex();
}
/**
 * 执行删除附件
 *
 * @param url
 * @param index
 */
function deluploadify(url, id) {
  $.ajax({
    async: false,
    cache: false,
    type: 'POST',
    url: url,// 请求的action路径
    error: function () {// 请求失败处理函数
    },
    success: function (data) {
      //var d = $.parseJSON(data);
      var d = data;
      if (d.success) {
        $("#" + id).remove();// 移除SPAN
        m.remove(id);// 移除MAP对象内字符串
      }

    }
  });
}
// 普通询问操作调用函数
function confirm(url, content, name) {
  createdialog('提示信息 ', content, url, name);
}
/**
 * 提示信息
 */
function tip_old(msg) {
  $.dialog.setting.zIndex = 1980;
  $.dialog.tips(msg, 1);
}
/**
 * 提示信息
 */
function tip(msg) {
  try {
    $.dialog.setting.zIndex = 1980;
    $.messager.show({
      title: '提示信息',
      msg: msg,
      timeout: 1000 * 6
    });
  } catch (e) {
    alertTipTop(msg, '10%');
  }
}

function alertTipTop(msg, top, title) {
  $.dialog.setting.zIndex = 1980;
  title = title ? title : "提示信息";
  $.dialog({
    title: title,
    icon: 'tips.gif',
    top: top,
    content: msg
  }).zindex();
}

/**
 * 提示信息像alert一样
 */
function alertTip(msg, title) {
  $.dialog.setting.zIndex = 1980;
  title = title ? title : "提示信息";
  $.dialog({
    title: title,
    icon: 'tips.gif',
    content: msg
  }).zindex();
}
/**
 * 创建添加或编辑窗口
 *
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl, width, height) {
  //20151207 Zerrion 因测试服务器出现
  addurl = encodeURI(projectName + addurl);
  width = width ? width : 700;
  height = height ? height : 400;
  if (width == "100%" || height == "100%") {
    width = window.top.document.body.offsetWidth;
    height = window.top.document.body.offsetHeight - 100;
  }
  //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数
  if (windowapi == null) {
    $.dialog({
      content: 'url:' + addurl,
      lock: false,
      zIndex: 500,
      width: width,
      height: height,
      title: title,
      opacity: 0.3,
      cache: false,
      ok: function () {
        iframe = this.iframe.contentWindow;
        saveObj();
        return false;
      },
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  } else {
    W.$.dialog({
      content: 'url:' + addurl,
      lock: false,
      width: width,
      zIndex: 500,
      height: height,
      parent: windowapi,
      title: title,
      opacity: 0.3,
      cache: false,
      ok: function () {
        iframe = this.iframe.contentWindow;
        saveObj();
        return false;
      },
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  }
  //--author：Zerrion---------date：20140427---------for：弹出bug修改,设置了zindex()函数

}
/**
 * 创建上传页面窗口
 *
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url, name, width, height) {
  gridname = name;
  $.dialog({
    content: 'url:' + projectName + url,
    cache: false,
    button: [
      {
        name: "开始上传",
        callback: function () {
          iframe = this.iframe.contentWindow;
          iframe.upload();
          return false;
        },
        focus: true
      },
      {
        name: "取消上传",
        callback: function () {
          iframe = this.iframe.contentWindow;
          iframe.cancel();
        }
      }
    ]
  }).zindex();
}
/**
 * 创建查询页面窗口
 *
 * @param title
 * @param addurl
 * @param saveurl
 */
function opensearchdwin(title, url, width, height) {
  $.dialog({
    content: 'url:' + url,
    title: title,
    lock: true,
    height: height,
    cache: false,
    width: width,
    opacity: 0.3,
    button: [{
      name: '查询',
      callback: function () {
        iframe = this.iframe.contentWindow;
        iframe.searchs();
      },
      focus: true
    }, {
      name: '取消',
      callback: function () {

      }
    }]
  }).zindex();
}
/**
 * 创建不带按钮的窗口
 *
 * @param title
 * @param addurl
 * @param saveurl
 */
function openwindow(title, url, name, width, height) {
  gridname = name;
  if (typeof (width) == 'undefined' && typeof (height) != 'undefined') {
    if (typeof(windowapi) == 'undefined') {
      $.dialog({
        content: 'url:' + url,
        title: title,
        cache: false,
        lock: true,
        width: 'auto',
        height: height
      }).zindex();
    } else {
      $.dialog({
        content: 'url:' + url,
        title: title,
        cache: false,
        parent: windowapi,
        lock: true,
        width: 'auto',
        height: height
      }).zindex();
    }
  }
  if (typeof (height) == 'undefined' && typeof (width) != 'undefined') {
    if (typeof(windowapi) == 'undefined') {
      $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        width: width,
        cache: false,
        height: 'auto'
      }).zindex();
    } else {
      $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        parent: windowapi,
        width: width,
        cache: false,
        height: 'auto'
      }).zindex();
    }
  }
  if (typeof (width) == 'undefined' && typeof (height) == 'undefined') {
    if (typeof(windowapi) == 'undefined') {
      $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        width: 'auto',
        cache: false,
        height: 'auto'
      }).zindex();
    } else {
      $.dialog({
        content: 'url:' + url,
        title: title,
        lock: true,
        parent: windowapi,
        width: 'auto',
        cache: false,
        height: 'auto'
      }).zindex();
    }
  }

  if (typeof (width) != 'undefined' && typeof (height) != 'undefined') {
    if (typeof(windowapi) == 'undefined') {
      $.dialog({
        width: width,
        height: height,
        content: 'url:' + url,
        title: title,
        cache: false,
        lock: true
      }).zindex();
    } else {
      $.dialog({
        width: width,
        height: height,
        content: 'url:' + url,
        parent: windowapi,
        title: title,
        cache: false,
        lock: true
      }).zindex();
    }
  }
}

/**
 * 创建询问窗口
 *
 * @param title
 * @param content
 * @param url
 */
function createdialog(title, content, url, name) {
  $.dialog.confirm(content, function () {
    doSubmit(url, name);
    rowid = '';
  }, function () {
  }).zindex();
}
/**
 * 执行保存
 *
 * @param url
 * @param gridname
 */
function saveObj() {
  //todo:保存前，如果有单据日期对象，则要验证当前单据日期的值是否超过当期月份即表单域对象的stageDate属性；如果验证失败则弹出提示并不进行表单提交
  if ($("#date").length>0){
    var stageDate = $("#date").attr("stageDate");
    var date = $("#date").val();
    var tranType = $("#tranType").val();
    if (tranType=="1032" || tranType=="1031" || tranType=="1030" || tranType=="1020"){//初始化单据必须早于开账月份
      if (date>stageDate){
        tip("单据日期必须早于开账年月[" + stageDate + "]");
        return false;
      }
    }else{//普通单据必须晚于当前期间月份
      if (date<stageDate){
        tip("单据日期必须晚于当前期间年月[" + stageDate + "]");
        return false;
      }
    }
  }
  $('#btn_sub', iframe.document).click();
}

/**
 * 执行AJAX提交FORM
 *
 * @param url
 * @param gridname
 */
function ajaxSubForm(url) {
  $('#myform', iframe.document).form('submit', {
    url: url,
    onSubmit: function () {
      iframe.editor.sync();
    },
    success: function (r) {
      tip('操作成功');
      reloadTable();
    }
  });
}
/**
 * 执行查询
 *
 * @param url
 * @param gridname
 */
function search() {

  $('#btn_sub', iframe.document).click();
  iframe.search();
}

/**
 * 执行操作
 *
 * @param url
 * @param index
 */
function doSubmit(url, name, data) {
  gridname = name;
  //--author：Zerrion ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
  var paramsData = data;
  if (!paramsData) {
    paramsData = new Object();
    if (url.indexOf("&") != -1) {
      var str = url.substr(url.indexOf("&") + 1);
      url = url.substr(0, url.indexOf("&"));
      var strs = str.split("&");
      for (var i = 0; i < strs.length; i++) {
        paramsData[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
      }
    }
  }
  //--author：Zerrion ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
  $.ajax({
    async: false,
    cache: false,
    type: 'POST',
    data: paramsData,
    url: url,// 请求的action路径
    error: function () {// 请求失败处理函数
    },
    success: function (data) {
      //var d = $.parseJSON(data);
      var d = data;
      if (d.success) {
        var msg = d.msg;
        tip(msg);
        reloadTable();
      }
    }
  });


}
/**
 * 退出确认框
 *
 * @param url
 * @param content
 * @param index
 */
function exit(url, content) {
  $.dialog.confirm(content, function () {
    window.location = url;
  }, function () {
  }).zindex();
}
/**
 * 模板页面ajax提交
 *
 * @param url
 * @param gridname
 */
function ajaxdoSub(url, formname) {
  $('#' + formname).form('submit', {
    url: url,
    onSubmit: function () {
      editor.sync();
    },
    success: function (r) {
      tip('操作成功');
    }
  });
}
/**
 * ajax提交FORM
 *
 * @param url
 * @param gridname
 */
function ajaxdoForm(url, formname) {
  $('#' + formname).form('submit', {
    url: url,
    onSubmit: function () {
    },
    success: function (r) {
      tip('操作成功');
    }
  });
}

function opensubwin(title, url, saveurl, okbutton, closebutton) {
  $.dialog({
    content: 'url:' + url,
    title: title,
    lock: true,
    opacity: 0.3,
    button: [{
      name: okbutton,
      callback: function () {
        iframe = this.iframe.contentWindow;
        win = frameElement.api.opener;// 来源页面
        $('#btn_sub', iframe.document).click();
        return false;
      }
    }, {
      name: closebutton,
      callback: function () {
      }
    }]

  }).zindex();
}

function openauditwin(title, url, saveurl, okbutton, backbutton, closebutton) {
  $.dialog({
    content: 'url:' + url,
    title: title,
    lock: true,
    opacity: 0.3,
    button: [{
      name: okbutton,
      callback: function () {
        iframe = this.iframe.contentWindow;
        win = $.dialog.open.origin;// 来源页面
        $('#btn_sub', iframe.document).click();
        return false;
      }
    }, {
      name: backbutton,
      callback: function () {
        iframe = this.iframe.contentWindow;
        win = frameElement.api.opener;// 来源页面
        $('#formobj', iframe.document).form('submit', {
          url: saveurl + "&code=exit",
          onSubmit: function () {
            $('#code').val('exit');
          },
          success: function (r) {
            $.dialog.tips('操作成功', 2);
            win.location.reload();
          }
        });

      }
    }, {
      name: closebutton,
      callback: function () {
      }
    }]

  }).zindex();
}
/*获取Cookie值*/
function getCookie(c_name) {
  if (document.cookie.length > 0) {
    c_start = document.cookie.indexOf(c_name + "=")
    if (c_start != -1) {
      c_start = c_start + c_name.length + 1
      c_end = document.cookie.indexOf(";", c_start)
      if (c_end == -1)
        c_end = document.cookie.length
      return unescape(document.cookie.substring(c_start, c_end))
    }
  }
  return ""
}
// 添加标签
function addOneTab(subtitle, url, icon) {
  var indexStyle = getCookie("JEECGINDEXSTYLE");
  if (indexStyle == 'sliding' || indexStyle == 'bootstrap') {
    //shortcut和bootstrap风格的tab跳转改为直接跳转
    window.location.href = url;
  } else {
    //if (icon == '') {
    //    icon = 'icon folder';
    //}
    window.top.$.messager.progress({
      text: '页面加载中....',
      interval: 300
    });
    window.top.$('#maintabs').tabs({
      onClose: function (subtitle, index) {
        window.top.$.messager.progress('close');
      }
    });
    if (window.top.$('#maintabs').tabs('exists', subtitle)) {
      window.top.$('#maintabs').tabs('select', subtitle);
      if (url.indexOf('isHref') != -1) {
        window.top.$('#maintabs').tabs('update', {
          tab: window.top.$('#maintabs').tabs('getSelected'),
          options: {
            title: subtitle,
            href: url,
            //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
            closable: true
          }
        });
      } else {
        window.top.$('#maintabs').tabs('update', {
          tab: window.top.$('#maintabs').tabs('getSelected'),
          options: {
            title: subtitle,
            content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.9%;"></iframe>',
            //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
            closable: true
          }
        });
      }
    } else {
      if (url.indexOf('isHref') != -1) {
        window.top.$('#maintabs').tabs('add', {
          title: subtitle,
          href: url,
          closable: true
        });
      } else {
        window.top.$('#maintabs').tabs('add', {
          title: subtitle,
          content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.9%;"></iframe>',
          closable: true
        });
      }
    }
  }
}

// 添加标签
function addOneTabForBiz(subtitle, url, icon, preTitle) {
  if (!preTitle) {
    preTitle = window.top.$('.tabs-selected:first').text();
  }
  subtitle = preTitle + '-' + subtitle;
  url = projectName + url + '&preTitle=' + preTitle;
  var indexStyle = getCookie("JEECGINDEXSTYLE");
  if (indexStyle == 'sliding' || indexStyle == 'bootstrap') {
    //shortcut和bootstrap风格的tab跳转改为直接跳转
    window.location.href = url;
  } else {
    //if (icon == '') {
    //    icon = 'icon folder';
    //}
    window.top.$.messager.progress({
      text: '页面加载中....',
      interval: 300
    });
    window.top.$('#maintabs').tabs({
      onClose: function (subtitle, index) {
        $.messager.progress('close');
      }
    });
    if (window.top.$('#maintabs').tabs('exists', subtitle)) {
      window.top.$('#maintabs').tabs('select', subtitle);
      if (url.indexOf('isHref') != -1) {
        window.top.$('#maintabs').tabs('update', {
          tab: window.top.$('#maintabs').tabs('getSelected'),
          options: {
            title: subtitle,
            href: url,
            //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
            closable: true
          }
        });
      } else {
        window.top.$('#maintabs').tabs('update', {
          tab: window.top.$('#maintabs').tabs('getSelected'),
          options: {
            title: subtitle,
            content: '<iframe id="tabiframe" onLoad="iFrameHeight()" src="' + url + '" frameborder="0" style="border:0;width:100%;height:' + (window.top.$('#maintabs').height() - 35) + 'px"></iframe>',
            //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
            closable: true
          }
        });

      }
    } else {
      if (url.indexOf('isHref') != -1) {
        window.top.$('#maintabs').tabs('add', {
          title: subtitle,
          href: url,
          closable: true
        });
      } else {
        window.top.$('#maintabs').tabs('add', {
          title: subtitle,
          content: '<iframe id="tabiframe" onLoad="iFrameHeight()" src="' + url + '" frameborder="0" style="border:0;width:100%;height:' + (window.top.$('#maintabs').height() - 35) + 'px"></iframe>',
          closable: true
        });
        //$('iframe','form').append('<div style="float:right;"><input type="button" value="确定" id="btn_sub">&nbsp;<input type="button" value="清除" id="btn_reset"></div>');
      }
      window.top.$('#maintabs').tabs('select', subtitle);
    }
  }
  tabClose();
  tabCloseEven();
}
// 关闭自身TAB刷新父TABgrid
function closetab(title) {
  //暂时先不刷新
  //window.top.document.getElementById('tabiframe').contentWindow.reloadTable();
  //window.top.document.getElementById('maintabs').contentWindow.reloadTable();
  window.top.$('#maintabs').tabs('close', title);
  //tip("添加成功");
}

//popup
//object: this  name:需要选择的列表的字段  code:动态报表的code
function inputClick(obj, name, code) {
  $.dialog.setting.zIndex = 2002;
  if (name == "" || code == "") {
    alert("popup参数配置不全");
    return;
  }
  if (typeof(windowapi) == 'undefined') {
    $.dialog({
      content: "url:cgReportController.do?popup&id=" + code,
      lock: true,
      title: "选择",
      width: 800,
      height: 400,
      cache: false,
      ok: function () {
        iframe = this.iframe.contentWindow;
        var selected = iframe.getSelectRows();
        if (selected == '' || selected == null) {
          alert("请选择");
          return false;
        } else {
          var str = "";
          $.each(selected, function (i, n) {
            if (i == 0)
              str += n[name];
            else
              str += "," + n[name];
          });
          $(obj).val("");
          //$('#myText').searchbox('setValue', str);
          $(obj).val(str);
          return true;
        }

      },
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  } else {
    $.dialog({
      content: "url:cgReportController.do?popup&id=" + code,
      lock: true,
      title: "选择",
      width: 800,
      height: 400,
      parent: windowapi,
      cache: false,
      ok: function () {
        iframe = this.iframe.contentWindow;
        var selected = iframe.getSelectRows();
        if (selected == '' || selected == null) {
          alert("请选择");
          return false;
        } else {
          var str = "";
          $.each(selected, function (i, n) {
            if (i == 0)
              str += n[name];
            else
              str += "," + n[name];
          });
          $(obj).val("");
          //$('#myText').searchbox('setValue', str);
          $(obj).val(str);
          return true;
        }

      },
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  }
}
/*
 自定义url的弹出
 obj:要填充的控件,可以为多个，以逗号分隔
 name:列表中对应的字段,可以为多个，以逗号分隔（与obj要对应）
 url：弹出页面的Url
 */
function popClick(obj, name, url) {
  $.dialog.setting.zIndex = 2001;
  var names = name.split(",");
  var objs = obj.split(",");
  if (typeof(windowapi) == 'undefined') {
    $.dialog({
      content: "url:" + url,
      lock: true,
      title: "选择",
      width: 700,
      height: 400,
      cache: false,
      ok: function () {
        iframe = this.iframe.contentWindow;
        var selected = iframe.getSelectRows();
        if (selected == '' || selected == null) {
          alert("请选择");
          return false;
        } else {
          for (var i1 = 0; i1 < names.length; i1++) {
            var str = "";
            $.each(selected, function (i, n) {
              if (i == 0)
                str += n[names[i1]];
              else {
                str += ",";
                str += n[names[i1]];
              }
            });
            if ($("#" + objs[i1]).length >= 1) {
              $("#" + objs[i1]).val("");
              $("#" + objs[i1]).val(str);
            } else {
              $("input[name='" + objs[i1] + "']").val("");
              $("input[name='" + objs[i1] + "']").val(str);
            }
          }
          return true;
        }

      },
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  } else {
    $.dialog({
      content: "url:" + url,
      lock: true,
      title: "选择",
      width: 700,
      height: 400,
      parent: windowapi,
      cache: false,
      ok: function () {
        iframe = this.iframe.contentWindow;
        var selected = iframe.getSelectRows();
        if (selected == '' || selected == null) {
          alert("请选择");
          return false;
        } else {
          for (var i1 = 0; i1 < names.length; i1++) {
            var str = "";
            $.each(selected, function (i, n) {
              if (i == 0)
                str += n[names[i1]];
              else {
                str += ",";
                str += n[names[i1]];
              }
            });
            if ($("#" + objs[i1]).length >= 1) {
              $("#" + objs[i1]).val("");
              $("#" + objs[i1]).val(str);
            } else {
              $("[name='" + objs[i1] + "']").val("");
              $("[name='" + objs[i1] + "']").val(str);
            }
          }
          return true;
        }

      },
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  }
}
/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function JeecgExcelExport(url, datagridId) {
  var queryParams = $('#' + datagridId).datagrid('options').queryParams;
  $('#' + datagridId + 'tb').find('*').each(function () {
    if($(this).attr('id') == 'sonId'){
       var treeInfo = $("#sonId").combotree('tree');
       var checkInfo = treeInfo.tree('getChecked');
       var sonIds = '';
       for(var i = 0 ; i < checkInfo.length ; i++){
           var node = checkInfo[i];
           var nodeId = node.id;
           sonIds += nodeId+',';
       }
       queryParams[$(this).attr('id')] = sonIds;
    } else if( $(this).attr('name') != 'sonId'){
       queryParams[$(this).attr('name')]=$(this).val();
    }
  });
  var params = '&';
  $.each(queryParams, function (key, val) {
    params += '&' + key + '=' + val;
  });
  var fields = '&field=';
  $.each($('#' + datagridId).datagrid('options').columns[0], function (i, val) {
    if (val.field != 'opt') {
      fields += val.field + ',';
    }
  });
  window.location.href = url + encodeURI(fields + params);
}
/**
 * 自动完成的解析函数
 * @param data
 * @returns {Array}
 */
function jeecgAutoParse(data) {
  var parsed = [];
  $.each(data.rows, function (index, row) {
    parsed.push({data: row, result: row, value: row.id});
  });
  return parsed;
}


//var title = window.top.$('.tabs-selected:first').text();
/*if (data.success == true) {
 $.dialog.confirm('保存成功，是否关闭页面？', function () {
 debugger;
 window.top.$('#maintabs').tabs('close', title);
 });
 } else {
 if (data.responseText == '' || data.responseText == undefined) {
 $.messager.alert('错误', data.msg);
 $.Hidemsg();
 } else {
 try {
 var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),
 data.responseText.indexOf('错误信息'));
 $.messager.alert('错误', emsg);
 $.Hidemsg();
 }
 catch (ex) {
 $.messager.alert('错误', data.responseText
 );
 $.Hidemsg();
 }
 }
 //return false;
 }*/
//var tab = window.top.$('#maintabs').tabs('getTab', title);
function iFrameHeight() {
  var ifm = document.getElementById("tabiframe");
  var subWeb = document.frames ? document.frames["tabiframe"].document : ifm.contentDocument;
  if (ifm != null && subWeb != null) {
    ifm.height = subWeb.body.scrollHeight;
    ifm.width = subWeb.body.scrollWidth;
  }
}

// strPrintName 打印任务名
// printDatagrid 要打印的datagrid
function CreateFormPage(strPrintName, datagrid) {
  var printDatagrid = $(datagrid);
  //var tableString = '<table cellspacing="0" class="pb" width="100%">';
  //var tableString = "<div align='center' style='font-size:20px'><strong>"+strPrintName+"</strong></div></br>";
  var tableString = "";
  var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
  var columns = printDatagrid.datagrid("options").columns;    // 得到columns对象
  var nameList = '';
  //计算表格宽度
  var tableWidth = 0;
  if (typeof columns != 'undefined' && columns != '') {
    $(columns).each(function (index) {
      if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
        for (var i = 0; i < frozenColumns[index].length; ++i) {
          if (!frozenColumns[index][i].hidden && frozenColumns[index][i].field != 'ck'
              && frozenColumns[index][i].field != '_expander') {
            tableWidth += frozenColumns[index][i].width;
          }
        }
      }
      for (var i = 0; i < columns[index].length; ++i) {
        if (!columns[index][i].hidden && columns[index][i].field != 'ck' && columns[index][i].field != 'opt') {
          tableWidth += columns[index][i].width;
        }
      }

    });
  }
  //如果计算出来的表格宽度大于0，将表格宽度设置为tableWidth,否则设置为100%
  if (tableWidth > 0) {
    tableString += '<table cellspacing="0" class="pb" width="' + tableWidth + 'px">';
  } else {
    tableString += '<table cellspacing="0" class="pb" width="100%">';
  }
  // 载入title
  if (typeof columns != 'undefined' && columns != '') {
    $(columns).each(function (index) {
      tableString += '\n<tr>';
      if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
        for (var i = 0; i < frozenColumns[index].length; ++i) {
          if (!frozenColumns[index][i].hidden && frozenColumns[index][i].field != 'ck') {
            tableString += '\n<th width="' + frozenColumns[index][i].width + '"';
            if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
              tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
            }
            if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
              tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
            }
            if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
              nameList += ',{"f":"' + frozenColumns[index][i].field + '", "a":"' + frozenColumns[index][i].align + '"}';
            }
            tableString += '>' + frozenColumns[0][i].title + '</th>';
          }
        }
      }
      for (var i = 0; i < columns[index].length; ++i) {
        if (!columns[index][i].hidden && columns[index][i].field != 'ck' && columns[index][i].field != 'opt') {
          tableString += '\n<th width="' + columns[index][i].width + '"';
          if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
            tableString += ' rowspan="' + columns[index][i].rowspan + '"';
          }
          if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
            tableString += ' colspan="' + columns[index][i].colspan + '"';
          }
          if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
            nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '","i":"' + i + '"}';
          }
          tableString += '>' + columns[index][i].title + '</th>';
        }
      }
      tableString += '\n</tr>';
    });
  }
  // 载入内容
  var rows = printDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
  if (rows != null) {
    var nl = eval('([' + nameList.substring(1) + '])');
    for (var i = 0; i < rows.length; ++i) {
      tableString += '\n<tr>';
      $(nl).each(function (j) {
        var e = nl[j].f.lastIndexOf('_0');

        tableString += '\n<td';
        if (nl[j].a != 'undefined' && nl[j].a != '') {
          tableString += ' style="text-align:' + nl[j].a + ';"';
        }
        tableString += '>';
        if (rows[i][nl[j].f] == null || rows[i][nl[j].f] == '') {
          tableString += '&nbsp;&nbsp;';
        } else {
          if (e + 2 == nl[j].f.length) {
            tableString += rows[i][nl[j].f.substring(0, e)];
          }
          else
          //如果datagrid中某列为字典或者有应用了formatter，动态调用formatter转换数据
          if (columns[0][nl[j].i].formatter == null || columns[0][nl[j].i].formatter == undefined) {
            tableString += rows[i][nl[j].f];
          } else {
            tableString += columns[0][nl[j].i].formatter.apply(this, [rows[i][nl[j].f]]);
          }
        }
        tableString += '</td>';
      });
      tableString += '\n</tr>';
    }
    tableString += '\n</table>';

    //window.showModalDialog(projectName+"/print.jsp", tableString,
    //  "location:No;status:No;help:No;dialogWidth:1000px;dialogHeight:600px;scroll:auto;");

    $.dialog({
      content: 'url:' + projectName + '/print.jsp',
      lock: false,
      zIndex: 500,
      width: 800,
      height: 600,
      title: '数据打印',
      opacity: 0.3,
      cache: false,
      init: function () {
        $(this.iframe.contentDocument.body).append(tableString);
      },
      ok: function () {
        $("html", this.iframe.contentDocument).jqprint();
      },
      okVal: '打印',
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  }
}


/**
 * 标签页选择时自动刷新列表
 */
function onTabsSel() {
  $('#maintabs').tabs({
    onSelect: function (title, index) {
      var iframeObj = $("iframe")[index];
      $(iframeObj).contents().find(".easyui-datagrid").each(function () {
        iframeObj.contentWindow.publicDatagrid('#' + $(this).attr('id'), 'reload');
      });
    }
  });
}


//关闭标签页
function closeTab(title) {
  if (title != '首页') {
    $('#maintabs').tabs('close', title);
  }
}

//页签跳转
window.top.reloadHome = function reloadHome(type, title) {
  if (parent.$('#maintabs').tabs('exists', title)) {
    var tab = parent.$('#maintabs').tabs('getSelected');
    var index = parent.$('#maintabs').tabs('getTabIndex', tab);
    parent.$('#maintabs').tabs('close', index);
    parent.$('#maintabs').tabs('select', title);
    window.top.refreshDatagrid(type);
  } else {
    var tab = parent.$('#maintabs').tabs('getSelected');
    var index = parent.$('#maintabs').tabs('getTabIndex', tab);
    parent.$('#maintabs').tabs('close', index);
  }
};

/**
 * 获取当前事件event,同时兼容ie和ff的写法
 * @returns {*}
 */
function getEvent() {
  if ($.browser.msie)  return window.event;
  func = getEvent.caller;
  while (func != null) {
    var arg0 = func.arguments[0];
    if (arg0) {
      if ((arg0.constructor == Event || arg0.constructor == MouseEvent) || (typeof(arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
        return arg0;
      }
    }
    func = func.caller;
  }
  return null;
}

//列表颜色区分不同状态数据
//绿色：已审核；红色：关闭；灰色；作废
function dgRowStyler(index,row){
  var returnInfo = "";
  if(row && (row.checkState || row.checkstate) && (row.checkState == 2 || row.checkstate == 2)){
    returnInfo = 'color:green;'
  }
  if(row && row.closed && row.closed == 1){
    returnInfo = 'color:red;'
  }
  if(row && row.cancellation && row.cancellation == 1){
    returnInfo = 'color:rgb(150,150,150);'
  }
  returnInfo += 'vertical-align: top;';
  return returnInfo;
}
function tabClose() {
  /* 双击关闭TAB选项卡 */
  window.top.$(".tabs-inner").dblclick(function () {
    var subtitle = $(this).children(".tabs-closable").text();
    $('#tabs').tabs('close', subtitle);
  })
  /* 为选项卡绑定右键 */
  window.top.$(".tabs-inner").bind('contextmenu', function (e) {
    window.top.$('#mm').menu('show', {
      left: e.pageX,
      top: e.pageY
    });

    var subtitle = $(this).children(".tabs-closable").text();

    window.top.$('#mm').data("currtab", subtitle);
    // $('#maintabs').tabs('select',subtitle);
    return false;
  });
}
// 绑定右键菜单事件
function tabCloseEven() {
  // 刷新
  window.top.$('#mm-tabupdate').click(function () {
    var currTab = window.top.$('#maintabs').tabs('getSelected');
    var url = $(currTab.panel('options').content).attr('src');
    window.top.$('#maintabs').tabs('update', {
      tab: currTab,
      options: {
        content: createFrame(url)
      }
    })
  })
  // 关闭当前
  window.top.$('#mm-tabclose').click(function () {
    var currtab_title = $('#mm').data("currtab");
    window.top.$('#maintabs').tabs('close', currtab_title);
  })
  // 全部关闭
  window.top.$('#mm-tabcloseall').click(function () {
    window.top.$('.tabs-inner span').each(function (i, n) {
      var t = $(n).text();
      if (t != '首页') {
        window.top.$('#maintabs').tabs('close', t);
      }
    });
  });
  // 关闭除当前之外的TAB
  window.top.$('#mm-tabcloseother').click(function () {
    window.top.$('#mm-tabcloseright').click();
    window.top.$('#mm-tabcloseleft').click();
  });
  // 关闭当前右侧的TAB
  window.top.$('#mm-tabcloseright').click(function () {
    var nextall = window.top.$('.tabs-selected').nextAll();
    if (nextall.length == 0) {
      // msgShow('系统提示','后边没有啦~~','error');
      alert('后边没有啦~~');
      return false;
    }
    nextall.each(function (i, n) {
      var t = $('a:eq(0) span', $(n)).text();
      window.top.$('#maintabs').tabs('close', t);
    });
    return false;
  });
  // 关闭当前左侧的TAB
  window.top.$('#mm-tabcloseleft').click(function () {
    var prevall = window.top.$('.tabs-selected').prevAll();
    if (prevall.length == 0) {
      alert('到头了，前边没有啦~~');
      return false;
    }
    prevall.each(function (i, n) {
      var t = $('a:eq(0) span', $(n)).text();
      if (t != '首页') {
        window.top.$('#maintabs').tabs('close', t);
      }
    });
    return false;
  });

  // 退出
  window.top.$("#mm-exit").click(function () {
    window.top.$('#mm').menu('hide');
  });
}

/**
 * 判断传入的tr（jquery对象）中包含的所有非隐藏输入框是否有值
 * 只要有一个有值则返回False
 * @param tr
 * @returns {boolean}
 */
function checkTextboxIsEmpty(tr){
  var flag = true;
  tr.find('input:not([type="hidden"])').each(function () {
    if($(this).val()!=null && $(this).val()!=''){
      flag = false;
    }
  });
  return flag;
}