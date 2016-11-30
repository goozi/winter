<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>工作计划</title>
  <t:base type="jquery,easyui,tools,DatePicker,ckeditor"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
    function setOrgIds(curform) {
        var masterIds = $("#masterSelect").combotree("getValues");
        $("#planMaster").val(masterIds);

        var groupIds = $("#groupSelect").combotree("getValues");
        $("#planGroup").val(groupIds);

        var leaderIds = $("#leaderSelect").combotree("getValues");
        $("#planLeadder").val(leaderIds);

        var startDate = $("#planStartdate").val();
        var endDate = $("#planEnddate").val();
        if(startDate && endDate) {
            var dates = new Date(startDate).getTime();
            var datee = new Date(endDate).getTime();
            if (dates > datee) {
                $.messager.alert(
                        "提示",
                        "开始日期不可大于结束日期"
                )
                return false;
            }
        }
        var content = CKEDITOR.instances.planInfo_text;
        if(content.document.getBody().getText().trim()==""){
            $.messager.alert("提示","请填写计划内容");
            return false;
        }

    }
    function checkInfo(node, checked){

        //var text = $("#leaderSelect").combotree("getText");
        var tree = $(this).tree;
        var isLeaf = tree('isLeaf',node.target);
//        var text = $("#leaderSelect").combotree("getText");
//        var value = $("#leaderSelect").combotree("getValues");
        if(!isLeaf){
            return false;
        }
        return true;
//            var texts = text.split(",");
//            var newText = "";
//           // var ids = value.split(",");
//            var newValue = [];
//            var n = 1;
//            for(var i=1 ; i<texts.length ; i++){
//                newText +=texts[i]+",";
//                if(texts[i]==node.text){
//                    n++;
//                }
//                newValue[i-n] = value[i];
//            }
//            $("#leaderSelect").combotree("setText",newText);
//            $("#leaderSelect").combotree("setValues",newValue);
//        }
    }
//    function uploadSuccess(d,file,response){
//        if($("#streamName").val() == null) {
//            $("#streamName").val(d.attributes.url+",");
//            $("#fileName").val(file.name+",");
//        }else{
//            $("#streamName").val($("#streamName").val()+d.attributes.url+",");
//            $("#fileName").val($("#fileName").val()+file.name+",");
//        }
//    }
    var fileIndex = 1;
    var fileNameIndex = 1;
    function uploadSuccess(d,file,response){
        var fileName = file.name;
        $('#viewmsg').remove();
        if($("#streamName").val() == null) {
            $("#streamName").val(d.attributes.url+",");
            $("#fileName").val(file.name+",");
        }else{
            $("#streamName").val($("#streamName").val()+d.attributes.url+",");
            var oldFileName = $("#fileName").val();
            if(oldFileName.indexOf(fileName) > -1){
                var files = fileName.split(".");
                fileName = files[0]+"_"+fileNameIndex+"."+files[1];
                fileNameIndex++;
            }
            $("#fileName").val($("#fileName").val()+fileName+",");
        }
        var table = document.getElementById("dg");
        var tr = document.createElement("tr");
        var fileId = "file"+fileIndex;
        fileIndex++;
        tr.setAttribute("id",fileId);
        var headTd = document.createElement("td");
        //var fileNode = document.createTextNode("附件");
        //headTd.appendChild(fileNode);
        tr.appendChild(headTd);
        var td = document.createElement("td");
        td.setAttribute("colspan",3);
        var a = document.createElement("a");
        var node = document.createTextNode("查看附件："+fileName);
        a.setAttribute("href",d.attributes.url);
        a.setAttribute("id", d.attributes.url);
        a.setAttribute("target","_blank");
        a.appendChild(node);
        var da = document.createElement("a");
        var dnode = document.createTextNode("   删除");
        da.setAttribute("id",fileName);
        da.setAttribute("onclick","delFile('"+d.attributes.url+"','"+fileName+"','"+fileId+"')");
        da.appendChild(dnode);
        td.appendChild(a);
        td.appendChild(da);
        tr.appendChild(td);
        table.appendChild(tr);
    }
    function delFile(url,fName,fileId){
        var streamName = $("#streamName").val();
        streamName = streamName.replace(url+",","");
        $("#streamName").val(streamName);

        var fileName = $("#fileName").val();
        fileName = fileName.replace(fName+",","");
        $("#fileName").val(fileName);

        $("#"+fileId).remove();
    }
    function checkDate(){
        var startDate = $("#planStartdate").val();
        var endDate = $("#planEnddate").val();
        if(startDate && endDate) {
            var dates = new Date(startDate).getTime();
            var datee = new Date(endDate).getTime();
            if (dates > datee) {
                $.messager.alert(
                    "提示",
                    "开始日期不可小于结束日期"
                )
            }
        }
    }
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tScPlanController.do?doAdd" beforeSubmit="setOrgIds" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScPlanPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScPlanPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScPlanPage.createBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScPlanPage.createDate }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScPlanPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScPlanPage.updateBy }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScPlanPage.updateDate }">
  <table id="dg" style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
    <td align="right">
      <label class="Validform_label">
      计划类型:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="planType" type="list"
                        typeGroupCode="planType"
                        defaultVal="周报" hasLabel="false"
                        title="计划类型" extendJson="{datatype:'select1'}"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">计划类型</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      计划名称:
      </label>
    </td>
    <td class="value">
          <input id="planName" name="planName" type="text" style="width: 150px" class="inputxt"
                 datatype="*1-100"
              >
      <span class="Validform_checktip">*</span>
      <label class="Validform_label" style="display: none;">计划名称</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      负责人:
      </label>
    </td>
    <td class="value">
          <select class="easyui-combotree" data-options="width:155,url:'tScPlanController.do?userSel', multiple:true,onlyLeafCheck:false, cascadeCheck:true"
                  id="masterSelect" name="masterSelect" datatype="select1">
              <c:forEach items="${masterList}" var="master">
                  <option value="${master.id }">${master.realName}</option>
              </c:forEach>
          </select>
          <input id="planMaster" name="planMaster" type="hidden">
          <span class="Validform_checktip">*</span>
          <label class="Validform_label" style="display: none;">负责人</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      参与人:
      </label>
    </td>
    <td class="value">
        <select class="easyui-combotree" data-options="width:155,url:'tScPlanController.do?userSel', multiple:true,onlyLeafCheck:false,cascadeCheck:true"
                id="groupSelect" name="groupSelect" datatype="select1">
            <c:forEach items="${groupList}" var="depart">
                <option value="${group.id }">${group.realName}</option>
            </c:forEach>
        </select>
        <input id="planGroup" name="planGroup" type="hidden">
        <span class="Validform_checktip">*</span>
        <label class="Validform_label" style="display: none;">参与人</label>
    </td>
          </tr>
          <tr>
    <td align="right">
      <label class="Validform_label">
      领导批注:
      </label>
    </td>
    <td class="value">
          <select class="easyui-combotree" data-options="width:155,url:'tScPlanController.do?userSel', multiple:true,onlyLeafCheck:false, cascadeCheck:true"
                  id="leaderSelect" name="leaderSelect" datatype="select1">
              <c:forEach items="${leaderList}" var="leader">
                  <option value="${leader.id }">${leader.realName}</option>
              </c:forEach>
          </select>
          <input id="planLeadder" name="planLeadder" type="hidden">
          <span class="Validform_checktip">*</span>
          <label class="Validform_label" style="display: none;">领导批注</label>
    </td>
    <td align="right">
          <label class="Validform_label">
              计划人:
          </label>
        </td>
        <td class="value">
          <input id="planer" name="planer" type="text" readonly="readonly" value="${sessionScope.user.realName}" style="width: 150px" class="inputxt"

                  >
      <span class="Validform_checktip">
      </span>
          <label class="Validform_label" style="display: none;">计划人</label>
      </td>
          </tr>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      开始时间:
                  </label>
              </td>
              <td class="value">
                  <input id="planStartdate" name="planStartdate" type="text" datatype="*" style="width: 150px"
                         class="Wdate" onClick="WdatePicker()" onchange="checkDate()"

                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">开始时间</label>
              </td>
    <td align="right">
      <label class="Validform_label">
      结束时间:
      </label>
    </td>
    <td class="value">
          <input id="planEnddate" name="planEnddate" type="text" style="width: 150px"
                 class="Wdate" onClick="WdatePicker()" datatype="*"  onchange="checkDate()"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">结束时间</label>
    </td>

          </tr>
          <tr>
    <td class="value" colspan="4">
          <t:ckeditor  name="planInfo" isfinder="false" type="width:document.body.clientWidth-20,toolbar:[
    ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
    ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
    ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
    ['Link','Unlink','Anchor'],
    ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
    ['Styles','Format','Font','FontSize'],
    ['TextColor','BGColor'],
    ['Maximize', 'ShowBlocks','-','Source','-','Undo','Redo']]" value=""></t:ckeditor>
          <span class="Validform_checktip">*</span>
          <label class="Validform_label" style="display: none;">计划内容</label>
    </td>
          </tr>
      <tr>
          <td align="right"><label class="Validform_label">上传文件:</label></td>
          <td class="value"><t:upload name="childernFile" dialog="false" queueID="childernUpFile" onUploadSuccess="uploadSuccess" view="true" multi="true" auto="true" uploader="systemController.do?saveFiles" extend="*.*" id="childernFile"
                                      formData="streamName"></t:upload>
              <input id="streamName" name="streamName" type="hidden"/>
              <input id="fileName" name="fileName" type="hidden"/>
          </td>
          <td colspan="2" id="childernUpFile" class="value"></td>
      </tr>
      <%--<c:if test="${id eq null ? false : true}">--%>
      <%--</c:if>--%>
  </table>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/oa/tScPlan.js"></script>