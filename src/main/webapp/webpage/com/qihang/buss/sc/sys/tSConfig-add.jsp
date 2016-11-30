<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <title>t_s_config</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
    //编写自定义JS代码
  </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tSConfigController.do?doAdd" tiptype="1">
      <input id="id" name="id" type="hidden"
             value="${tSConfigPage.id }">
    <div>
  <table style="width: 600px;" cellpadding="0" cellspacing="1"  class="formtable"  >


          <th colspan="4" height="25">基本设置 </th>

      <tr >
          <td align="right" >
              <label class="Validform_label" >
                  分页记录:
              </label>
          </td>
          <td class="value" >
              <input id="pageRecord" name="pageRecord" type="text" style="width: 150px" class="inputxt"
                     datatype="*"
                      ></input>
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">content</label>
          </td>

          <td align="right">
              <label class="Validform_label">
                  行高:
              </label>
          </td>
          <td class="value">
              <input  id="rowHeight" name="rowHeight"type="text" style="width: 150px" class="inputxt"
                      datatype="*"
                      ></input>
      <span class="Validform_checktip">
      </span>
              <label class="Validform_label" style="display: none;">content</label>
          </td>
      </tr>

          <tr>
              <td align="right">
                  <label class="Validform_label">
                      默认税率:
                  </label>
              </td>
              <td class="value" colspan="3">
                  <input  id="defaultRate" name="defaultRate" type="text" style="width: 150px" class="inputxt"
                          datatype="*"
                         ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

          </tr>

          <th colspan="4" height="25">精度设置</th>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      数量:
                  </label>
              </td>
              <td class="value">
                  <input id="number" name="number" type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      单价:
                  </label>
              </td>
              <td class="value">
                  <input id="unitPrice"  name="unitPrice" type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                        ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>
          </tr>

          <tr>
              <td align="right">
                  <label class="Validform_label">
                      金额:
                  </label>
              </td>
              <td class="value" >
                  <input id="money " name="money " type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      税率:
                  </label>
              </td>
              <td class="value">
                  <input id="rates" name="rates" type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>
          </tr>

          <tr>
              <td align="right">
                  <label class="Validform_label">
                      折扣率:
                  </label>
              </td>
              <td class="value" >
                  <input id="discountRate" name="discountRate" type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      其他:
                  </label>
              </td>
              <td class="value">
                  <input  id="other" name="other" type="text" style="width: 150px" class="inputxt"
                          datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>
          </tr>
          <th colspan="4" height="25">信控设置</th>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      控制方式:
                  </label>
              </td>
              <td class="value" colspan="3">
                  <t:dictSelect field="controlMethod"
                                type="list"
                                typeGroupCode="SC_CONTROLMETHOD_TYPE"  defaultVal="${configEntityPage.controlMethod}" hasLabel="false"
                                title="控制方式"></t:dictSelect>

                  <%--<input  id="controlMethod" name="controlMethod" type="text" style="width: 150px" class="inputxt"--%>
                          <%--datatype="*"--%>
                          <%--></input>--%>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

          </tr>

          <tr>
              <td align="right">
                  <label class="Validform_label">
                      控制时点:
                  </label>
              </td>
              <td class="value" colspan="3">
                  <t:dictSelect field="controlTimePoint"
                                type="list"
                                typeGroupCode="SC_CONTROLTIMEPOINT_TYPE" defaultVal="${configEntityPage.controlTimePoint}" hasLabel="false"
                                title="控制时点"></t:dictSelect>

                  <%--<input    id="controlTimePoint" name="controlTimePoint" type="text" style="width: 150px" class="inputxt"--%>
                            <%--datatype="*"--%>
                          <%--></input>--%>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

          </tr>

          <th colspan="4" height="25">预警设置</th>
          <tr>
              <td align="right">
                  <label class="Validform_label">
                      应收预警天数:
                  </label>
              </td>
              <td class="value">
                  <input id="recearwarDays" name="recearwarDays"type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      采购订单预警天数:
                  </label>
              </td>
              <td class="value">
                  <input id="pruordearwarDays" name="pruordearwarDays" type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>
          </tr>

          <tr>
              <td align="right">
                  <label class="Validform_label">
                      应付预警天数:
                  </label>
              </td>
              <td class="value" >
                  <input id="payearwarDays" name="payearwarDays"type="text" style="width: 150px" class="inputxt"
                         datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      销售订单预警天数:
                  </label>
              </td>
              <td class="value">
                  <input  id="salordearwarDays"  name="salordearwarDays" type="text" style="width: 150px" class="inputxt"
                          datatype="*"
                         ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>
          </tr>

          <tr>
              <td align="right">
                  <label class="Validform_label">
                      保质期预警天数:
                  </label>
              </td>
              <td class="value" >
                  <input  id="shelflifeearwarDays" name="shelflifeearwarDays" type="text" style="width: 150px" class="inputxt"
                          datatype="*"
                          ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>

              <td align="right">
                  <label class="Validform_label">
                      系统日志保留天数:
                  </label>
              </td>
              <td class="value">
                  <input  id="syslogholdDays"  name="syslogholdDays" type="text" style="width: 150px" class="inputxt"
                          datatype="*"
                         ></input>
      <span class="Validform_checktip">
      </span>
                  <label class="Validform_label" style="display: none;">content</label>
              </td>
          </tr>


  </table>
    </div>
</t:formvalid>
</body>
<script src="webpage/com/qihang/buss/sc/sys/tSConfig.js"></script>