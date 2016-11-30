/* 
 * hftable 0.1
 * Copyright (c) 2014 赵俊夫 http://zjfhw.iteye.com
 * Date: 2014-02-23
 主要功能：将普通的Table的表头/表尾固定
 特点：
 1.该版本约定将表格第一行作为表头,所以不需要特别设置<thead>之类的标签标识表头,普通结构的table即可。
 2.同上,如果设置了表尾固定,则约定表格的最后一行为表尾。
 入参：
 options{
 fixHeader:true/fales	//是否固定表头,默认true
 fixFooter:true/false	//是否固定表尾,默认false
 width					//Table的宽度
 height					//Table的高度
 }
 2016-09-08 Zerrion 加入一对多表单中自动合计行功能
 */
(function ($) {
  $.fn.createhftable = function (options) {
    var defaultOptions = {
      fixHeader: true,
      fixFooter: false,
      width: '100%',
      height: '400px'
    };
    if (!options) {
      options = {};
    }
    $.extend(defaultOptions, options);
    // debugger;
    var $tb = this;
    var tableId = $tb.attr("id");
    if($tb.attr('totalRow') == 'true') {
      // debugger;
      var $trTotal = $tb.find("tbody:eq(1) tr:last").clone();
      $trTotal.find('td>input').each(function (index, obj) {
        $(obj).parent().width($(obj).width()).css('white-space','nowrap').css('overflow','hidden').css('word-break','keep-all');
      });
      $trTotal.children('input').remove();
      $trTotal.find('td').each(function (index, element) {
        if (index == 0) {
          $(element).html('合计');
        } else {
          var td = $tb.find('tr:first td:eq(' + index + ')');
          var tdTotal = 0;
          if (td.attr('total') == 'true') {
            $tb.find('tr:not(tr:first)').each(function (trIndex, trElement) {
              var input = $(trElement).find('td:eq(' + index + ') input');
              var oldValue = parseFloat(input.val()) || 0;
              input.attr('oldV', oldValue);
              var CFG_NUMBER = $("#CFG_NUMBER").val();
              var CFG_MONEY = $("#CFG_MONEY").val();
              var CFG_OTHER = $("#CFG_OTHER").val();
              var len;
              var name = input[0].name;
              if(name.toLowerCase().indexOf("qty") > -1){
                len = CFG_NUMBER;
              } else if (name.toLowerCase().indexOf("amount") > -1){
                len = CFG_MONEY;
              } else {
                len = CFG_OTHER;
              }
              tdTotal = addNum(tdTotal,oldValue,len);
            });

            $(element).html(tdTotal);
          } else {
            $(element).html('');
          }
        }
      });
      $(document).on('propertychange input change', 'input[oldv]', function () {
        //debugger;
        var CFG_NUMBER = $("#CFG_NUMBER").val();
        var CFG_MONEY = $("#CFG_MONEY").val();
        var CFG_OTHER = $("#CFG_OTHER").val();
        var len;
        var name = $(this)[0].name;
        if(name.toLowerCase().indexOf("qty") > -1){
          len = CFG_NUMBER;
        } else if (name.toLowerCase().indexOf("amount") > -1){
          len = CFG_MONEY;
        } else {
          len = CFG_OTHER;
        }
        var newValue = parseFloat($(this).val()) || 0;
        var oldValue = parseFloat($(this).attr('oldV')) || 0;
        var colIndex = $(this).parent().parent().find('td').index($(this).parent()[0]);
        // debugger;
        var tableIndex = $('table[id $= "_table"]').index($(this).parent().parent().parent().parent()[0]);
        if (oldValue != newValue) {
          var totalTd = $('table[id $= "scrolldivFoot"]:eq('+tableIndex+') tr:first td:eq(' + colIndex + ')');
          var differ = newValue - oldValue;
          var totalTdOldValue = parseFloat(totalTd.text()) || 0;
          totalTd.text(addNum(totalTdOldValue,differ,len));
          $(this).attr('oldV', newValue);
        }
      });
      $tb.find('tbody:eq(1)').append($trTotal);
    }
    //1.将原始table分离
    var $trHead = $tb.find("tr:first").clone();
    var $trFoot = $tb.find("tr:last").clone();
    var $tableHead = $tb.clone();
    var $tableFoot = $tb.clone();
    $tb.find("tr:first").remove();
    if (defaultOptions.fixFooter) {
      $tb.find("tr:last").remove();
    }
    //2.创建一个Div包裹Table,设置成可滚动

    var divId = tableId + "scrolldiv";
    var headId = tableId + "scrolldivHead";
    var footId = tableId + "scrolldivFoot";
    $tableHead.attr("id", headId);
    $tableFoot.attr("id", footId);
    var divStyle = "overflow: auto;width:" + defaultOptions.width + ";height:" + defaultOptions.height;
    var divtp = "<div id='" + divId + "' style=\"" + divStyle + "\">" + getOuterHtml($tb) + "</div>";
    $tb.before(divtp);
    $tb.remove();
    //3.将Table的表头/表尾复制成单独的Table放在hftable的上下方
    //3.1 添加表头
    $tableHead.html('');
    $tableHead.append(getOuterHtml($trHead));
    $("#" + divId).before(getOuterHtml($tableHead));
    fix(headId, tableId);
    //3.2 添加表尾
    if (defaultOptions.fixFooter) {
      $tableFoot.html('');
      $tableFoot.append(getOuterHtml($trFoot));
      $("#" + divId).after(getOuterHtml($tableFoot));
      fix(footId, tableId);
    }
    //4.绑定滚动事件
    bindScrollEvent(headId, footId, divId, defaultOptions.fixFooter);

    if($tb.attr('totalRow') == 'true') {
      //5.新增行时更新合计并为新增行中需合计单元格绑定事件
      $('#' + tableId).on('DOMNodeInserted', function (e) {
        var ele = e.target;
        $(ele).find('td').each(function (index, element) {
         // debugger;
          //if ($('table[id $="scrolldivHead"] tr td:eq(' + index + ')').attr('total') == 'true') {
          if ($('table[id="'+tableId.replace("add_","") + 'scrolldivHead"] tr td:eq(' + index + ')').attr('total') == 'true') {
            $(element).children('input').on('propertychange input change', function () {
              var CFG_NUMBER = $("#CFG_NUMBER").val();
              var CFG_MONEY = $("#CFG_MONEY").val();
              var CFG_OTHER = $("#CFG_OTHER").val();
              var len;
              var name = $(this)[0].name;
              if(name.toLowerCase().indexOf("qty") > -1){
                len = CFG_NUMBER;
              } else if (name.toLowerCase().indexOf("amount") > -1){
                len = CFG_MONEY;
              } else {
                len = CFG_OTHER;
              }
              //debugger;
              var newValue = parseFloat($(this).val()) || 0;
              var oldValue = parseFloat($(this).attr('oldV')) || 0;
              // var colIndex = $(this).parent().parent().find('td').index($(this).parent()[0]);
              //var tableIndex = $('table[id $= "_table"]').index($(this).parent().parent().parent().parent()[0]);
              var tableIndex = $('table[id="'+tableId.replace("add_","") + '"]').index($(this).parent().parent().parent().parent()[0]);
              if (oldValue != newValue) {
                //var totalTd = $('table[id $= "scrolldivFoot"]:eq('+tableIndex+') tr:first td:eq(' + index + ')');
                var totalTd = $('table[id="'+tableId.replace("add_","") + 'scrolldivFoot"]:eq('+tableIndex+') tr:first td:eq(' + index + ')');
                var differ = newValue - oldValue;
                var totalTdOldValue = parseFloat(totalTd.text()) || 0;
                totalTd.text(addNum(totalTdOldValue,differ,len));
                $(this).attr('oldV', newValue);
              }
            });
          }
        })
      });
      //6.删除行时更新合计
      $('#' + tableId).on('DOMNodeRemoved', function (e) {
        var ele = e.target;
        $(ele).find('td').each(function (index, element) {
          //var tableIndex = $('table[id $= "_table"]').index($(element).parent().parent().parent()[0]);
          var tableIndex = $('table[id="'+tableId.replace("add_","") + '"]').index($(element).parent().parent().parent()[0]);
          //var totalTd = $('table[id $= "scrolldivFoot"]:eq('+tableIndex+') tr:first td:eq(' + index + ')');
          var totalTd = $('table[id="'+tableId.replace("add_","") + 'scrolldivFoot"]:eq('+tableIndex+') tr:first td:eq(' + index + ')');
          //if ($('table[id $="scrolldivHead"]:eq('+tableIndex+') tr td:eq(' + index + ')').attr('total') == 'true') {
          if ($('table[id="'+tableId.replace("add_","") + 'scrolldivHead"]:eq('+tableIndex+') tr td:eq(' + index + ')').attr('total') == 'true') {
            var totalTdOldValue = parseFloat(totalTd.text()) || 0;
            var CFG_NUMBER = $("#CFG_NUMBER").val();
            var CFG_MONEY = $("#CFG_MONEY").val();
            var CFG_OTHER = $("#CFG_OTHER").val();
            var len;
            var name = $(element).children('input')[0].name;
            if(name.toLowerCase().indexOf("qty") > -1){
              len = CFG_NUMBER;
            } else if (name.toLowerCase().indexOf("amount") > -1){
              len = CFG_MONEY;
            } else {
              len = CFG_OTHER;
            }
            var newValue = addNum(totalTdOldValue,(-1*(parseFloat($(element).children('input').val()) || 0)),len);
            totalTd.text(newValue);
          }
        });
      });
      $('table[id $= "scrolldivFoot"]').css('table-layout','fixed');
    }
  };
  function addNum(num1,num2,len){
    if(!num1){
      num1 = 0;
    }
    if(!num2){
      num2 = 0;
    }
    return (parseFloat(num1)+parseFloat(num2)).toFixed(len);
  }
  function getOuterHtml(obj) {
    var box = $('<div></div>');
    box.append(obj.clone());
    return box.html();
  }

  function fix(headid, contentid) {
    for (var i = 0; i <= $('#' + contentid + ' tr:last').find('td:last').index(); i++) {
      if ($('#' + contentid + ' tr:last').find('td').eq(i).width() == 0) {
        $('#' + headid + ' td').eq(i).css('width', "auto");
      } else {
        $('#' + headid + ' td').eq(i).css('width',
            $('#' + contentid + ' tr:last').find('td').eq(i).width());
      }

    }
    $('#' + headid).css('width', $('#' + contentid + ' tr:last').width());
  }

  function bindScrollEvent(headId, footId, divId, fixFooter) {
    $('#' + divId).scroll(function () {
      $('#' + headId).css('margin-left', -($('#' + divId).scrollLeft()));
      if (fixFooter) {
        $('#' + footId).css('margin-left', -($('#' + divId).scrollLeft()));
      }
    });
  }
})(jQuery);  
