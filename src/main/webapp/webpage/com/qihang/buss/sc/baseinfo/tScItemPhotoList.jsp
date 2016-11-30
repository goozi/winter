<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<script type="text/javascript">
  $('#addTScItemPhotoBtn').linkbutton({
    iconCls: 'icon-add'
  });
  $('#delTScItemPhotoBtn').linkbutton({
    iconCls: 'icon-remove'
  });
  //	$('#addTScItemPhotoBtn').bind('click', function(){
  // 		 var tr =  $("#add_tScItemPhoto_table_template tr").clone();
  //	 	 $("#add_tScItemPhoto_table").append(tr);
  //	 	 resetTrNum('add_tScItemPhoto_table');
  //	 	 return false;
  //    });
  function clickAddTScItemPhotoBtn(rowIndex) {
    var tr = $("#add_tScItemPhoto_table_template tr").clone();
//        $("#add_tScItemPhoto_table").append(tr);
    $("#add_tScItemPhoto_table tr").eq(rowIndex).after(tr);
    resetTrNum('add_tScItemPhoto_table');
  }
  function clickDelTScItemPhotoBtn(rowIndex) {
    $("#add_tScItemPhoto_table").find(">tr").eq(rowIndex).remove();
    resetTrNum('add_tScItemPhoto_table');
  }
  //	$('#delTScItemPhotoBtn').bind('click', function(){
  //      	$("#add_tScItemPhoto_table").find("input:checked").parent().parent().remove();
  //        resetTrNum('add_tScItemPhoto_table');
  //        return false;
  //    });
  function disableBtn() {
    $('a[name^="addTScItemPhotoBtn"]').each(function () {
      $(this).linkbutton('disable');
    });
    $('a[name^="delTScItemPhotoBtn"]').each(function () {
      $(this).linkbutton('disable');
    });

  }
  $(document).ready(function () {
    $(".datagrid-toolbar").parent().css("width", "auto");
    if (location.href.indexOf("load=detail") != -1) {
      $(":input").attr("disabled", "true");
      $(".datagrid-toolbar").hide();
      setTimeout(disableBtn, 200);
//            $('a[name^="addLine"],a[name^="delLine"]').each(function(){
//                $(this).linkbutton('disable');
//            });
    }
    //将表格的表头固定
    $("#tScItemPhoto_table").createhftable({
      height: (h-50)+'px',
      width: 'auto',
      fixFooter: false
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
<table border="0" cellpadding="2" cellspacing="1" id="tScItemPhoto_table" style="background-color: #cbccd2">
  <tr bgcolor="#E6E6E6" style="color: white; height:24px;">
    <td align="center" bgcolor="#476f9a">序号</td>
    <td align="center" bgcolor="#476f9a">操作</td>
    <td align="left" bgcolor="#476f9a">
      文件
    </td>
  </tr>
  <tbody id="add_tScItemPhoto_table">
  <c:if test="${fn:length(tScItemPhotoList)  <= 0 }">
    <tr>
      <td align="center" bgcolor="#F6FCFF">
        <div style="width: 25px;background-color: white;" name="xh">1</div>
      </td>
      <td align="center" bgcolor="white">
        <div style="width: 80px;background-color: white;">
          <a name="addTScItemPhotoBtn[0]" id="addTScItemPhotoBtn[0]" href="#"
                                     class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                                     onclick="clickAddTScItemPhotoBtn(0);"></a>
        </div>
      </td>
      <input name="tScItemPhotoList[0].id" type="hidden"/>
      <input name="tScItemPhotoList[0].createName" type="hidden"/>
      <input name="tScItemPhotoList[0].createBy" type="hidden"/>
      <input name="tScItemPhotoList[0].createDate" type="hidden"/>
      <input name="tScItemPhotoList[0].updateName" type="hidden"/>
      <input name="tScItemPhotoList[0].updateBy" type="hidden"/>
      <input name="tScItemPhotoList[0].updateDate" type="hidden"/>
      <input name="tScItemPhotoList[0].itemID" type="hidden"/>
      <input name="tScItemPhotoList[0].fileName" type="hidden"/>
      <td align="left" bgcolor="white">
        <input type="hidden" id="tScItemPhotoList[0].filePath" name="tScItemPhotoList[0].filePath" style="width:150px"/>
        <a target="_blank" id="tScItemPhotoList[0].filePath_href">未上传</a>
        <br>
        <input class="ui-button" type="button" value="上传附件"
               onclick="commonUpload(commonUploadDefaultCallBack,'tScItemPhotoList\\[0\\]\\.filePath')"/>
        <label class="Validform_label" style="display: none;">文件存放地址</label>
      </td>
    </tr>
  </c:if>
  <c:if test="${fn:length(tScItemPhotoList)  > 0 }">
    <c:forEach items="${tScItemPhotoList}" var="poVal" varStatus="stuts">
      <tr>
        <td align="center" bgcolor="white">
          <div style="width: 25px;" name="xh">${stuts.index+1 }</div>
        </td>
        <td align="center" bgcolor="white">
          <div style="width: 80px;">
            <c:if test="${stuts.index == 0}">
              <a name="addTScItemPhotoBtn[${stuts.index }]" id="addTScItemPhotoBtn[${stuts.index }]" href="#"
                 class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                 onclick="clickAddTScItemPhotoBtn(${stuts.index});"></a>
            </c:if>
            <c:if test="${stuts.index >0}">
              <a name="addTScItemPhotoBtn[${stuts.index }]" id="addTScItemPhotoBtn[${stuts.index }]" href="#"
                 class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain="true"
                 onclick="clickAddTScItemPhotoBtn(${stuts.index});"></a>
              <a name="delTScItemPhotoBtn[${stuts.index }]" id="delTScItemPhotoBtn[${stuts.index }]" href="#"
                 class="easyui-linkbutton" data-options="iconCls:'icon-remove'" plain="true"
                 onclick="clickDelTScItemPhotoBtn(${stuts.index});"></a>
            </c:if>
          </div>
        </td>
        <input name="tScItemPhotoList[${stuts.index }].id" type="hidden" value="${poVal.id }"/>
        <input name="tScItemPhotoList[${stuts.index }].createName" type="hidden" value="${poVal.createName }"/>
        <input name="tScItemPhotoList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }"/>
        <input name="tScItemPhotoList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }"/>
        <input name="tScItemPhotoList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }"/>
        <input name="tScItemPhotoList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }"/>
        <input name="tScItemPhotoList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }"/>
        <input name="tScItemPhotoList[${stuts.index }].itemID" type="hidden" value="${poVal.itemID }"/>
        <input name="tScItemPhotoList[${stuts.index }].fileName" type="hidden" value="${poVal.fileName }"/>
        <input name="tScItemPhotoList[${stuts.index }].version" type="hidden" value="${poVal.version}"/>
        <td align="left" bgcolor="white">
          <input type="hidden" id="tScItemPhotoList[${stuts.index }].filePath"
                 name="tScItemPhotoList[${stuts.index }].filePath" value="${poVal.filePath }"/>
          <c:if test="${empty poVal.filePath}">
            <a target="_blank" id="tScItemPhotoList[${stuts.index }].filePath_href" >未上传</a>
          </c:if>
          <c:if test="${!empty poVal.filePath}">
            <a href="${poVal.filePath}" target="_blank" id="tScItemPhotoList[${stuts.index }].filePath_href">下载</a>
          </c:if>
          <br>
          <input class="ui-button" type="button" value="上传附件"
                 onclick="commonUpload(commonUploadDefaultCallBack,'tScItemPhotoList\\[${stuts.index }\\]\\.filePath')"/>
          <label class="Validform_label" style="display: none;">文件存放地址</label>
        </td>
      </tr>
    </c:forEach>
  </c:if>
  </tbody>
</table>
