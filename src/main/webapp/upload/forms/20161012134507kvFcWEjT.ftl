<html
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml">





<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script><script type="text/javascript" src="plug-in/tools/dataformat.js"></script><link id="easyuiTheme" rel="stylesheet" href="plug-in/easyui/themes/default/easyui.css" type="text/css"></link><link rel="stylesheet" href="plug-in/easyui/themes/icon.css" type="text/css"></link><link rel="stylesheet" type="text/css" href="plug-in/accordion/css/accordion.css"></link><script type="text/javascript" src="plug-in/easyui/jquery.easyui.min.1.3.2.js"></script><script type="text/javascript" src="plug-in/easyui/locale/zh-cn.js"></script><script type="text/javascript" src="plug-in/tools/syUtil.js"></script><script type="text/javascript" src="plug-in/My97DatePicker/WdatePicker.js"></script><script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script><script type="text/javascript">$(function(){$("#formobj").Validform({tiptype:1,btnSubmit:"#btn_sub",btnReset:"#btn_reset",ajaxPost:true,usePlugin:{passwordstrength:{minLen:6,maxLen:18,trigger:function(obj,error){if(error){obj.parent().next().find(".Validform_checktip").show();obj.find(".passwordStrength").hide();}else{$(".passwordStrength").show();obj.parent().next().find(".Validform_checktip").hide();}}}},callback:function(data){if(data.success==true){if(!neibuClickFlag){var win = frameElement.api.opener;frameElement.api.close();win.tip(data.msg);win.reloadTable();}else {alert(data.msg)}}else{if(data.responseText==''||data.responseText==undefined)$("#formobj").html(data.msg);else $("#formobj").html(data.responseText); return false;}if(!neibuClickFlag){var win = frameElement.api.opener;win.reloadTable();}}});});</script><script type="text/javascript" src="plug-in/tools/curdtools_zh-cn.js"></script><script type="text/javascript" src="plug-in/tools/easyuiextend.js"></script><link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/><link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/><script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script><script type="text/javascript" src="plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js"></script><style>body{font-size:12px;}table{border: 1px solid #000000;padding:0; margin:0 auto;border-collapse: collapse;width:100%;align:right;}td {border: 1px solid #000000;background: #fff;font-size:12px;padding: 3px 3px 3px 8px;color: #000000;word-break: keep-all;}</style>
<body  >

<div  >

<p  align=center ><b >�����û���</b></p>

<p ></p>

<form action="cgFormBuildController.do?saveOrUpdate" id="formobj" name="formobj" method="post">
<input type="hidden" name="tableName" value="${tableName?if_exists?html}"/>
<input type="hidden" name="id" value="${id?if_exists?html}"/>
<input type="hidden" id="btn_sub" class="btn_sub"/>
#{jform_hidden_field}<table  border=1 cellspacing=0 cellpadding=0
 >
 <tr >
  <td width=104  >
  <p  align=right >�û�����</p>
  </td>
  <td width=173  >
  <p >#{name}</p>
  </td>
  <td width=92  >
  <p  align=right >���䣺</p>
  </td>
  <td width=185  >
  <p >#{age}</p>
  </td>
 </tr>
 <tr >
  <td width=104  >
  <p  align=right >�Ա�</p>
  </td>
  <td width=173  >
  <p >#{sex}</p>
  </td>
  <td width=92  >
  <p ></p>
  </td>
  <td width=185  >
  <p ></p>
  </td>
 </tr>
</table>
</form>

<p ></p>

<p  align=right ></p>

</div>

</body>

</html>
