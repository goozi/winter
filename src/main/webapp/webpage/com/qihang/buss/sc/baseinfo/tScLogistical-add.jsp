<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>物流公司</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
    function doName() {
        var getName=$("#name").val()
        var pinyin = getPinYin(getName);

        $("#shortName").val(pinyin);
    }
  </script>

    <style >
        /**20160629添加页脚样式 **/
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

        @media (min-width: 768px) and (max-width: 1366px){
            #address{
                width:81%;
            }

            #note{
                width: 88%;
            }
        }

        @media (min-width: 600px) and (max-width: 1280px) {
            #address{
                width:82.5%;
            }
            #note{
                width: 89%;
            }

        }
        @media (min-width: 768px) and(max-width: 1024px) {
            #address{
                width:89%;
            }

            #note{
                width: 93%;
            }
        }
        @media (min-width: 600px) and(max-width: 800px) {
            #address{
                width:94.5%;
            }

            #note{
                width: 96.5%;
            }
        }

    </style>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" beforeSubmit="checkName()"
             action="tScLogisticalController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tScLogisticalPage.id }">
      <input id="createName" name="createName" type="hidden"
             value="${tScLogisticalPage.createName }">
      <input id="createBy" name="createBy" type="hidden"
             value="${tScLogisticalPage.createBy }">
      <input id="updateName" name="updateName" type="hidden"
             value="${tScLogisticalPage.updateName }">
      <input id="updateBy" name="updateBy" type="hidden"
             value="${tScLogisticalPage.updateBy }">
      <input id="createDate" name="createDate" type="hidden"
             value="${tScLogisticalPage.createDate }">
      <input id="updateDate" name="updateDate" type="hidden"
             value="${tScLogisticalPage.updateDate }">
      <input id="parentID" name="parentID" type="hidden"
             value="${parentID }">
      <input id="level" name="level" type="hidden"
             value="${tScLogisticalPage.level }">
      <input id="disabled" name="disabled" type="hidden"
             value="${tScLogisticalPage.disabled }">
      <input id="deleted" name="deleted" type="hidden"
             value="${tScLogisticalPage.deleted }">
      <input id="version" name="version" type="hidden"
             value="${tScLogisticalPage.version }">
      <input id="count" name="count" type="hidden"
             value="0">
      <input id="tranType" name="tranType" type="hidden"
              value="${tranType}">
      <input id="old_name" name="old_name" type="hidden" value=''>
      <input id="load" value="${load}" type="hidden">
  <table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
              <td align="right" style="width: 9%">
                  <label class="Validform_label">
                      编号:
                  </label>
              </td>
              <td class="value" style="width: 24%">
                  <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
                         value="${number}"  datatype="/^[0-9\.]{1,80}$/g" errormsg="只能输入数字和小数点"
                          >
      <span class="Validform_checktip">
            *
      </span>
                  <label class="Validform_label" style="display: none;">编号</label>
              </td>
    <td align="right" style="width: 9%">
      <label class="Validform_label">
      名称:
      </label>
    </td>
    <td class="value" style="width: 24%">
          <input id="name" name="name" type="text" style="width: 150px" class="inputxt"
                 datatype="*1-50" onchange="doName()"
              >
      <span class="Validform_checktip">
            *
      </span>
      <label class="Validform_label" style="display: none;">名称</label>
    </td>
              <td align="right" style="width: 9%">
                  <label class="Validform_label">
                      简称:
                  </label>
              </td>
              <td class="value" style="width: 24%">
                  <input id="shortName" name="shortName" type="text" style="width: 150px" class="inputxt"
                         datatype="*" readonly="readonly"
                          >
      <span class="Validform_checktip">
            *
      </span>
                  <label class="Validform_label" style="display: none;">简称</label>
              </td>
          </tr>
          <tr>

    <td align="right">
      <label class="Validform_label">
      助记码:
      </label>
    </td>
    <td class="value">
          <input id="shortNumber" name="shortNumber" type="text" style="width: 150px" class="inputxt"
                 validType="t_sc_logistical,shortNumber,id"
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">助记码</label>
    </td>
              <td align="right">
                  <label class="Validform_label">
                      联系人:
                  </label>
              </td>
              <td class="value">
                  <input id="contact" name="contact" type="text" style="width: 150px" class="inputxt"

                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">联系人</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      手机号码:
                  </label>
              </td>
              <td class="value">
                  <input id="mobilePhone" name="mobilePhone" type="text" style="width: 150px" class="inputxt"
                      datatype="m" ignore=ignore
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">手机号码</label>
              </td>
          </tr>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      电话:
                  </label>
              </td>
              <td class="value">
                  <input id="phone" name="phone" type="text" style="width: 150px" class="inputxt"
                      datatype="po" ignore=ignore
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">电话</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      传真:
                  </label>
              </td>
              <td class="value">
                  <input id="fax" name="fax" type="text" style="width: 150px" class="inputxt"
                      datatype="f" ignore=ignore
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">传真</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      区域:
                  </label>
              </td>
              <td class="value" >
                  <t:dictSelect field="regionID" type="list"
                                typeGroupCode="SC_REGION"
                                defaultVal="${tScLogisticalPage.regionID}" hasLabel="false"
                                title="区域"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">区域</label>
              </td>
          </tr>
      <tr>
          <td align="right">
              <label class="Validform_label">
                  邮政编码:
              </label>
          </td>
          <td class="value">
              <input id="postalCode" name="postalCode" type="text" style="width: 150px" class="inputxt"
                  datatype="p" ignore=ignore
                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">邮政编码</label>
          </td>
          <td align="right">
              <label class="Validform_label">
                  联系地址:
              </label>
          </td>
          <td class="value" colspan="3">
              <input id="address" name="address" type="text" style="width:80%;" class="inputxt"
                  datatype="*1-80" ignore=ignore
                      >
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">联系地址</label>
          </td>



          </tr>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      电子邮件:
                  </label>
              </td>
              <td class="value">
                  <input id="email" name="email" type="text" style="width: 150px" class="inputxt"
                      datatype="e" ignore=ignore
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">电子邮件</label>
              </td>
    <td align="right">
      <label class="Validform_label">
      开户银行:
      </label>
    </td>
    <td class="value">
          <t:dictSelect field="bank" type="list"
                        typeGroupCode="SC_BANK"
                        defaultVal="${tScLogisticalPage.bank}" hasLabel="false"
                        title="开户银行"></t:dictSelect>
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">开户银行</label>
    </td>
    <td align="right">
      <label class="Validform_label">
      银行账号:
      </label>
    </td>
    <td class="value">
          <input id="bankNumber" name="bankNumber" type="text" style="width: 150px" class="inputxt"
                 
              >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">银行账号</label>
    </td>

          </tr>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      换货天数:
                  </label>
              </td>
              <td class="value">
                  <input id="jhDay" name="jhDay" type="text" style="width: 150px" class="inputxt" datatype="n"
                         ignore="ignore"
                          >
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">换货天数</label>
              </td>
    <td align="right">
      <label class="Validform_label">
      换货比例:
      </label>
    </td>
    <td class="value"colspan="3">
          <input id="jhRate" name="jhRate" type="text" style="width: 150px" class="inputxt" datatype="num"
                 ignore="ignore"
                  >
      <span class="Validform_checktip">
      </span>
      <label class="Validform_label" style="display: none;">换货比例</label>
    </td>

          </tr>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      备注:
                  </label>
              </td>
              <td class="value" colspan="5">
          <textarea style="width:87.5%;" class="inputxt" rows="2" id="note"
                    name="note"
              datatype="*1-80" ignore=ignore
                  ></textarea>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">备注</label>
              </td>
          </tr>


  </table>
</t:formvalid>
<!--页脚字段显示 -->
<div style="position: absolute;bottom: 10px;left:60px;">

</div>
</body>
<script src="webpage/com/qihang/buss/sc/baseinfo/tScLogistical.js"></script>