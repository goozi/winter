/**
 * Created by LenovoM4550 on 2016-08-03.
 */
$(function () {
  var id = $("#id").val();
  var billNo = $("input[name='billNo']").val();
  var date = $("input[name='date']").val();
  var title = $("#billTitle").val();
  $("#billNoStr").val(billNo);
  if (date) {
    date = dateFormat(date);
  }
  $("#date").val(date);
  $("#date").attr("readonly", "readonly");

  var div = document.getElementById("picInfo");
  var checkState = $("#checkState").val();
  var cancell = $("#cancellation").val();
  var closed = $("#closed").val();
  var load = $("#load").val();
  if (checkState && id && load == "detail") {
    var audtImgUrl = "plug-in/scm/pic/audit_" + checkState + ".png";
    var auditImg = document.createElement("img");
    auditImg.setAttribute("src", audtImgUrl);
    auditImg.setAttribute("style", "margin-right:10px");
    auditImg.setAttribute("height", 30);
    auditImg.setAttribute("width", 71);
    div.appendChild(auditImg);
  }
  if (checkState == 0) {
    $("#unAuditBtn").linkbutton("disable");
    $("#closedBtn").linkbutton("disable");
    $("#unClosedBtn").linkbutton("disable");
    setTimeout('$("#unAuditBtn").unbind();' +
        '$("#closedBtn").unbind();' +
        '$("#unClosedBtn").unbind();', 500);
  } else if (checkState == 2) {
    $("#auditBtn").linkbutton("disable");
    $("#edit").linkbutton("disable");
    $("#cancelBtn").linkbutton("disable");
    $("#unCancelBtn").linkbutton("disable");
    setTimeout('$("#auditBtn").unbind();' +
        '$("#edit").unbind();' +
        '$("#cancelBtn").unbind();' +
        '$("#unCancelBtn").unbind();', 500);
  } else if (checkState == 1) {
    $("#edit").linkbutton("disable");
    //$("#unAuditBtn").linkbutton("disable");
    $("#cancelBtn").linkbutton("disable");
    $("#unCancelBtn").linkbutton("disable");
    $("#closedBtn").linkbutton("disable");
    $("#unClosedBtn").linkbutton("disable");
    setTimeout("$('#edit').unbind();" +
        "$('#cancelBtn').unbind();" +
        "$('#unCancelBtn').unbind();" +
        "$('#unCancelBtn').unbind();" +
        "$('#closedBtn').unbind();" +
        "$('#unClosedBtn').unbind();", 500);
  }
  if (parseInt(cancell) > 0 && load == "detail") {
    $("#cancelBtn").linkbutton("disable");
    $("#edit").linkbutton("disable");
    $("#auditBtn").linkbutton("disable");
    $("#unAuditBtn").linkbutton("disable");
    setTimeout('$("#unAuditBtn").unbind();' +
        '$("#auditBtn").unbind();' +
        '$("#edit").unbind();' +
        '$("#cancelBtn").unbind();', 500);
    var cancellImgUrl = "plug-in/scm/pic/cancellation.png";
    var cancellImg = document.createElement("img");
    cancellImg.setAttribute("src", cancellImgUrl);
    cancellImg.setAttribute("style", "margin-right:10px");
    cancellImg.setAttribute("width", 71);
    cancellImg.setAttribute("height", 30);
    div.appendChild(cancellImg);
  } else {
    $("#unCancelBtn").linkbutton("disable");
    setTimeout('$("#unCancelBtn").unbind();', 500);
  }
  if (closed) {
    if (parseInt(closed) > 0 && load == "detail") {

      $("#edit").linkbutton("disable");
      $("#auditBtn").linkbutton("disable");
      $("#unAuditBtn").linkbutton("disable");
      $("#closedBtn").linkbutton("disable");
      setTimeout('$("#unAuditBtn").unbind();' +
          '$("#auditBtn").unbind();' +
          '$("#edit").unbind();' +
          '$("#closedBtn").unbind();' +
          '$("#cancelBtn").unbind();' +
          '$("#unCancelBtn").unbind();', 500);
      var closedImgUrl = "plug-in/scm/pic/closed.png";
      var closedImg = document.createElement("img");
      closedImg.setAttribute("src", closedImgUrl);
      closedImg.setAttribute("style", "margin-right:10px");
      closedImg.setAttribute("width", 71);
      closedImg.setAttribute("height", 30);
      div.appendChild(closedImg);
    } else {
      $("#unClosedBtn").linkbutton("disable");
      setTimeout('$("#unClosedBtn").unbind();');
    }
    $("#closedBtn").bind("click", function () {
      var tableName = $("#tableName").val();
      var url = "scBaseController.do?opeBill&tableName=" + tableName + "&id=" + id + "&value=1&field=closed";
      $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (data) {
          if (data.success) {
            tip("关闭成功");
            setTimeout("location.reload();", 2000);
          } else {
            tip(data.msg);
          }
        }
      });
    })
    $("#unClosedBtn").bind("click", function () {
      var tableName = $("#tableName").val();
      var autoflag = $("#autoFlag").val();
      if (autoflag != "0") {
        tip("该单据已自动关闭，不可反关闭");
        return;
      }
      var url = "scBaseController.do?opeBill&tableName=" + tableName + "&id=" + id + "&value=0&field=closed";
      $.ajax({
        url: url,
        type: 'post',
        cache: false,
        success: function (data) {
          if (data.success) {
            tip("反关闭成功");
            setTimeout("location.reload();", 2000);
          } else {
            tip(data.msg);
          }
        }
      });
    })
  } else {
    $("#closedBtn").attr("style", "display:none");
    $("#unClosedBtn").attr("style", "display:none");
  }

  $("#add").bind("click", function () {
    var url = window.location.href;
    var str = url.split("?");
    var tranType = $("#tranType").val();
    var newUrl = str[0] + "?goAdd";
    if (tranType) {
      newUrl += "&tranType=" + tranType;
    }
    var number = $("#number").val();
    if (number) {
      var cutIndex = number.lastIndexOf(".");
      number = number.substring(0, cutIndex);
      var parentID = $("#parentID").val();
      newUrl += "&parentID=" + parentID + "&parentNo=" + number;
    }

    var tabTitle = window.top.$('.tabs-selected:first').find("span")[0].innerHTML;
    var tab = window.top.$('#maintabs').tabs("getTab", tabTitle);
    tabTitle = tabTitle.replace("查看", "新增");
    preTitle = tabTitle.substring(0, tabTitle.length - 3);
    //window.top.$('.tabs-selected:first').find("span")[0].innerText = tabTitle;
    //window.top.$('.tabs-selected:first').text(tabTitle);
    newUrl += "&preTitle=" + preTitle;
    //location.replace(newUrl);
    window.top.$('#maintabs').tabs("update", {
      tab: tab,
      options: {
        title: tabTitle,
        content: '<iframe id="tabiframe" onLoad="iFrameHeight()" src="' + newUrl + '" frameborder="0" style="border:0;width:100%;height:' + (window.top.$('#maintabs').height() - 35) + 'px"></iframe>',
        //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
        closable: true
      }
    })
  })
  $("#edit").bind("click", function () {
    var url = window.location.href;
    url = url.replace("load=detail", "");
    var tabTitle = window.top.$('.tabs-selected:first').find("span")[0].innerHTML;
    var tab = window.top.$('#maintabs').tabs("getTab", tabTitle);
    tabTitle = tabTitle.replace("查看", "编辑");
    preTitle = tabTitle.substring(0, tabTitle.length - 3);
    url += "&load=edit&preTitle=" + preTitle;
    window.top.$('#maintabs').tabs("update", {
      tab: tab,
      options: {
        title: tabTitle,
        content: '<iframe id="tabiframe" onLoad="iFrameHeight()" src="' + url + '" frameborder="0" style="border:0;width:100%;height:' + (window.top.$('#maintabs').height() - 35) + 'px"></iframe>',
        //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
        closable: true
      }
    })
  })

  $("#cancelBtn").bind("click", function () {
    var tableName = $("#tableName").val();
    var tranType = $("#tranType").val();
    var url = "scBaseController.do?opeBill&tableName=" + tableName + "&id=" + id + "&value=1&field=cancellation&tranType=" + tranType;
    $.ajax({
      url: url,
      type: 'post',
      cache: false,
      success: function (data) {
        if (data.success) {
          tip("作废成功");
          setTimeout("location.reload();", 2000);
        } else {
          tip(data.msg);
        }
      }
    });
  })
  $("#unCancelBtn").bind("click", function () {
    var tableName = $("#tableName").val();
    var tranType = $("#tranType").val();
    var url = "scBaseController.do?opeBill&tableName=" + tableName + "&id=" + id + "&value=0&field=cancellation&tranType=" + tranType;
    $.ajax({
      url: url,
      type: 'post',
      cache: false,
      success: function (data) {
        if (data.success) {
          tip("反作废成功");
          setTimeout("location.reload();", 2000);
        } else {
          tip(data.msg);
        }
      }
    });
  })

  //复制按钮
  $("#copyBtn").bind("click", function () {
    debugger;
    var url = window.location.href;
    url = url.replace("load=detail", "load=fcopy");
    var tabTitle = window.top.$('.tabs-selected:first').find("span")[0].innerHTML;
    //var tab = window.top.$('#maintabs').tabs("getTab",tabTitle);
    tabTitle = tabTitle.replace("查看", "复制");
    var preTitle = tabTitle.substring(0, tabTitle.length - 3);
    url += "&preTitle=" + preTitle;
    /*window.top.$('#maintabs').tabs("update",{
     tab : tab,
     options : {
     title:tabTitle,
     content: '<iframe id="tabiframe" onLoad="iFrameHeight()" src="' + url + '" frameborder="0" style="border:0;width:100%;height:' + (window.top.$('#maintabs').height() - 35) + 'px"></iframe>',
     //content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
     closable: true
     }
     })*/
    var pathName = window.document.location.pathname;
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1) + '/';
    url = url.substring(url.substr(1).indexOf(projectName) + projectName.length + 1);
    var id = $("#id").val();
    var width = "0";
    var height = "0";
    var windowType = "tab";
    var title = "复制";
    createdetailwindow(title, url, width, height, windowType, preTitle);
  })
})

//赋值日期
function setDate() {
  var date = $("#date").val();
  $("input[name='date']").val(date);
}

//赋值单据编号
function setBillNo() {
  var billNo = $("#billNoStr").val();
  $("input[name='billNo']").val(billNo);
}

//日期格式化
function dateFormat(date) {
  date = date.substring(0, 10);
  return date;
}

//图片等比缩小
function AutoResizeImage(maxWidth, maxHeight, objImg) {
  var img = new Image();
  img.src = objImg.src;
  var hRatio;
  var wRatio;
  var Ratio = 1;
  var w = img.width;
  var h = img.height;
  wRatio = maxWidth / w;
  hRatio = maxHeight / h;
  if (maxWidth == 0 && maxHeight == 0) {
    Ratio = 1;
  } else if (maxWidth == 0) {//
    if (hRatio < 1) Ratio = hRatio;
  } else if (maxHeight == 0) {
    if (wRatio < 1) Ratio = wRatio;
  } else if (wRatio < 1 || hRatio < 1) {
    Ratio = (wRatio <= hRatio ? wRatio : hRatio);
  }
  if (Ratio < 1) {
    w = w * Ratio;
    h = h * Ratio;
  }
  objImg.height = h;
  objImg.width = w;
}

/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param url 目标页面地址
 * @param id 主键字段
 */
function printForm(tableName, title) {
  debugger;
  if (tableName == null || tableName == '') {
    alertTip('请先设置表单的tableName属性', '提醒');
  } else {
    var id = $('#id').val();
    // createwindow("[" + title + "]打印", "printController.do?printPreview&tableName=" + tableName + "&id=" + id, 800, 600);
    var url = "printController.do?printPreview&tableName=" + tableName + "&id=" + id;
    $.dialog({
      content: 'url:' + url,
      lock: true,
      zIndex: 500,
      width: 800,
      height: 600,
      title: content,
      opacity: 0.3,
      cache: false,
      ok: function () {
        $("html", this.iframe.contentDocument).jqprint();
      },
      okVal: '打印',
      cancelVal: '关闭',
      cancel: true /*为true等价于function(){}*/
    }).zindex();
  }
}
