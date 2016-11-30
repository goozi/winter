package com.qihang.winter.tag.core.easyui;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qihang.winter.core.common.hibernate.qbc.PageList;
import com.qihang.winter.core.common.model.json.ComboBox;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.util.ApplicationContextUtil;
import com.qihang.winter.core.util.ReflectHelper;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;
import com.qihang.winter.tag.vo.datatable.DataTableReturn;
import com.qihang.winter.tag.vo.easyui.Autocomplete;
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSRole;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：标签工具类
 *
 * @author liuht 修改不能输入双引号问题解决
 * @version 1.1
 * @author: 张代浩
 * @date： 日期：2012-12-28 时间：上午09:58:00
 */
public class TagUtil {
  /**
   * <b>Summary: </b> getFiled(获得实体Bean中所有属性)
   *
   * @param objClass
   * @return
   * @throws ClassNotFoundException
   */
  public static Field[] getFiled(Class<?> objClass) throws ClassNotFoundException {
    Field[] field = null;
    if (objClass != null) {
      Class<?> class1 = Class.forName(objClass.getName());
      field = class1.getDeclaredFields();// 这里便是获得实体Bean中所有属性的方法
      return field;
    } else {
      return field;
    }
  }

  /**
   * 获取对象内对应字段的值
   *
   * @param FiledName
   * @param o
   */
  public static Object fieldNametoValues(String FiledName, Object o) {
    Object value = "";
    String fieldName = "";
    String childFieldName = null;
    ReflectHelper reflectHelper = new ReflectHelper(o);
    if (FiledName.indexOf("_") == -1) {
      if (FiledName.indexOf(".") == -1) {
        fieldName = FiledName;
      } else {
        fieldName = FiledName.substring(0, FiledName.indexOf("."));//外键字段引用名
        childFieldName = FiledName.substring(FiledName.indexOf(".") + 1);//外键字段名
      }
    } else {
      fieldName = FiledName.substring(0, FiledName.indexOf("_"));//外键字段引用名
      childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);//外键字段名
    }
    //// TODO: 2016-09-21 此处反射极影响效率，后期重构优化
    value = reflectHelper.getMethodValue(fieldName) == null ? "" : reflectHelper.getMethodValue(fieldName);
    if (value != "" && value != null && (FiledName.indexOf("_") != -1 || FiledName.indexOf(".") != -1)) {
      if (value instanceof List) {
        Object tempValue = "";
        for (Object listValue : (List) value) {
          tempValue = tempValue.toString() + fieldNametoValues(childFieldName, listValue) + ",";
        }
        value = tempValue;
      } else {
        value = fieldNametoValues(childFieldName, value);
      }
    }
    if (value != "" && value != null) {
      value = converunicode(value.toString());
    }
    return value;
  }

  static Object converunicode(String jsonValue) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < jsonValue.length(); i++) {
      char c = jsonValue.charAt(i);
      switch (c) {
        case '\"':
          sb.append("\\\"");
          break;
        case '\'':
          sb.append("\\\'");
          break;
        case '\\':
          sb.append("\\\\");
          break;
        case '/':
          sb.append("\\/");
          break;
        case '\b':
          sb.append("\\b");
          break;
        case '\f':
          sb.append("\\f");
          break;
        case '\n':
          sb.append("\\n");
          break;
        case '\r':
          sb.append("\\r");
          break;
        case '\t':
          sb.append("\\t");
          break;
        default:
          sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 对象转数组
   *
   * @param fields
   * @param o
   * @return
   * @throws Exception
   */
  protected static Object[] field2Values(String[] fields, Object o) throws Exception {
    Object[] values = new Object[fields.length];
    for (int i = 0; i < fields.length; i++) {
      String fieldName = fields[i].toString();
      values[i] = fieldNametoValues(fieldName, o);
    }
    return values;
  }

  /**
   * 循环LIST对象拼接EASYUI格式的JSON数据
   *
   * @param fields
   * @param total
   * @param list
   */
  private static String listtojson(String[] fields, int total, List<?> list, String[] footers, String[] mergeFields) throws Exception {
    Object[] values = new Object[fields.length];
    StringBuffer jsonTemp = new StringBuffer();
    jsonTemp.append("{\"total\":" + total + ",\"rows\":[");
    int i;
    String fieldName;
    for (int j = 0; j < list.size(); ++j) {
      jsonTemp.append("{\"state\":\"closed\",");
      for (i = 0; i < fields.length; ++i) {
        fieldName = fields[i].toString();
        if (list.get(j) instanceof Map)
          values[i] = ((Map<?, ?>) list.get(j)).get(fieldName);
        else {
          values[i] = fieldNametoValues(fieldName, list.get(j));
        }
        jsonTemp.append("\"" + fieldName + "\"" + ":\"" + values[i] + "\"");
        if (i != fields.length - 1) {
          jsonTemp.append(",");
        }
      }
      if (j != list.size() - 1)
        jsonTemp.append("},");
      else {
        jsonTemp.append("}");
      }
    }
    jsonTemp.append("]");
    if (footers != null) {
      jsonTemp.append(",");
      jsonTemp.append("\"footer\":[");
      jsonTemp.append("{");
      jsonTemp.append("\"billNo\":\"合计\",");
      for (String footer : footers) {
        String footerFiled = footer.split(":")[0];
        Object value = null;
        if (footer.split(":").length == 2)
          value = footer.split(":")[1];
        else {
          value = getTotalValue(footerFiled, mergeFields, list);
        }
        jsonTemp.append("\"" + footerFiled + "\":\"" + value + "\",");
      }
      if (jsonTemp.lastIndexOf(",") == jsonTemp.length() - 1) {
        jsonTemp = jsonTemp.deleteCharAt(jsonTemp.length() - 1);
      }
      jsonTemp.append("}");
      jsonTemp.append("]");
    }
    jsonTemp.append("}");
    return jsonTemp.toString();
  }

  /**
   * 计算指定列的合计
   *
   * @param fieldName 字段名
   * @param list      列表数据
   * @return
   */
  private static Object getTotalValue(String fieldName, String[] mergeFields, List list) {
    Double sum = 0D;
    if (mergeFields != null && ArrayUtils.contains(mergeFields, fieldName)) {
      Map<String, Object> tempMap = new HashMap<String, Object>();
      for (Object o : list) {
        String id = fieldNametoValues("id", o).toString();
        if (!tempMap.containsKey(id)) {
          tempMap.put(id, o);
        }
      }
      try {
        for (String key : tempMap.keySet()) {
          Double v = 0d;
          String vstr = String.valueOf(fieldNametoValues(fieldName, tempMap.get(key)));
          if (!StringUtil.isEmpty(vstr)) {
            v = Double.valueOf(vstr);
          }
          sum += v;
        }
      } catch (Exception e) {
        return "";
      }
    } else {
      try {
        for (Object o : list) {
          Double v = 0d;
          String vstr = String.valueOf(fieldNametoValues(fieldName, o));
          if (!StringUtil.isEmpty(vstr)) {
            v = Double.valueOf(vstr);
          }
          sum += v;
        }
      } catch (Exception e) {
        return "";
      }
    }
    //数字位数控制
    sum = sumFormat(sum,fieldName);
    return sum;
  }

  /**
   * 数字格式化
   * @param number
   * @param fieldName
   * @return
   */
  public static Double sumFormat(Double number,String fieldName){
    Integer pointNum = 0;
    String code = "";
    if(fieldName.toLowerCase().indexOf("qty") > -1){
      code = "CFG_NUMBER";
    } else if(fieldName.toLowerCase().indexOf("amount") > -1){
      code = "CFG_MONEY";
    } else {
      code = "CFG_OTHER";
    }
    SystemService systemService = systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
    List<TSConfig> tsConfigList = systemService.findByProperty(TSConfig.class,"code",code);
    if(tsConfigList.size()>0){
      pointNum = Integer.parseInt(tsConfigList.get(0).getContents());
    }
    number = BigDecimal.valueOf(number).setScale(pointNum,BigDecimal.ROUND_HALF_EVEN).doubleValue();
    return number;
  }
  /**
   * 循环LIST对象拼接自动完成控件数据
   *
   * @param autocomplete
   * @param list
   * @throws Exception
   */
  public static String getAutoList(Autocomplete autocomplete, List list) throws Exception {
    String field = autocomplete.getLabelField() + "," + autocomplete.getValueField();
    String[] fields = field.split(",");
    Object[] values = new Object[fields.length];
    StringBuffer jsonTemp = new StringBuffer();
    jsonTemp.append("{\"totalResultsCount\":\"1\",\"geonames\":[");
    if (list.size() > 0) {
      for (int j = 0; j < list.size(); j++) {
        jsonTemp.append("{'nodate':'yes',");
        for (int i = 0; i < fields.length; i++) {
          String fieldName = fields[i].toString();
          values[i] = fieldNametoValues(fieldName, list.get(j));
          jsonTemp.append("\"").append(fieldName).append("\"").append(":\"").append(values[i]).append("\"");
          if (i != fields.length - 1) {
            jsonTemp.append(",");
          }
        }
        if (j != list.size() - 1) {
          jsonTemp.append("},");
        } else {
          jsonTemp.append("}");
        }
      }
    } else {
      jsonTemp.append("{'nodate':'数据不存在'}");
    }
    jsonTemp.append("]}");
    return JSONObject.toJSONString(jsonTemp).toString();
  }

  /**
   * 循环LIST对象拼接DATATABLE格式的JSON数据
   *
   * @param field
   * @param total
   * @param list
   */
  private static String datatable(String field, int total, List list) throws Exception {
    String[] fields = field.split(",");
    Object[] values = new Object[fields.length];
    StringBuffer jsonTemp = new StringBuffer();
    jsonTemp.append("{\"iTotalDisplayRecords\":" + total + ",\"iTotalRecords\":" + total + ",\"aaData\":[");
    for (int j = 0; j < list.size(); j++) {
      jsonTemp.append("{");
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].toString();
        values[i] = fieldNametoValues(fieldName, list.get(j));
        jsonTemp.append("\"" + fieldName + "\"" + ":\"" + values[i] + "\"");
        if (i != fields.length - 1) {
          jsonTemp.append(",");
        }
      }
      if (j != list.size() - 1) {
        jsonTemp.append("},");
      } else {
        jsonTemp.append("}");
      }
    }
    jsonTemp.append("]}");
    return jsonTemp.toString();
  }

  /**
   * 返回列表JSONObject对象
   *
   * @param dg
   * @return
   */
  private static JSONObject getJson(DataGrid dg) {
    JSONObject jObject = null;
    try {
      if (!StringUtil.isEmpty(dg.getFooter())) {
        String[] mergeFieldArry = null;
        if(StringUtil.isNotEmpty(dg.getMergeField())){
           mergeFieldArry = dg.getMergeField().split(",");
        }
        jObject = JSONObject.parseObject(listtojson(dg.getField().split(","), dg.getTotal(), dg.getResults(), dg.getFooter().split(","), mergeFieldArry));
      } else {
        jObject = JSONObject.parseObject(listtojson(dg.getField().split(","), dg.getTotal(), dg.getResults(), null, null));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jObject;

  }

  /**
   * 返回列表JSONObject对象
   *
   * @param dataTable
   * @param field
   * @return
   */
  private static JSONObject getJson(DataTableReturn dataTable, String field) {
    JSONObject jObject = null;
    try {
      jObject = JSONObject.parseObject(datatable(field, dataTable.getiTotalDisplayRecords(), dataTable.getAaData()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return jObject;

  }


  /**
   * 获取指定字段类型 <b>Summary: </b> getColumnType(请用一句话描述这个方法的作用)
   *
   * @param fileName
   * @param fields
   * @return
   */
  public static String getColumnType(String fileName, Field[] fields) {
    String type = "";
    if (fields.length > 0) {
      for (int i = 0; i < fields.length; i++) {
        String name = fields[i].getName(); // 获取属性的名字
        String filedType = fields[i].getGenericType().toString(); // 获取属性的类型
        if (fileName.equals(name)) {
          if (filedType.equals("class java.lang.Integer")) {
            filedType = "int";
            type = filedType;
          } else if (filedType.equals("class java.lang.Short")) {
            filedType = "short";
            type = filedType;
          } else if (filedType.equals("class java.lang.Double")) {
            filedType = "double";
            type = filedType;
          } else if (filedType.equals("class java.util.Date")) {
            filedType = "date";
            type = filedType;
          } else if (filedType.equals("class java.lang.String")) {
            filedType = "string";
            type = filedType;
          } else if (filedType.equals("class java.sql.Timestamp")) {
            filedType = "Timestamp";
            type = filedType;
          } else if (filedType.equals("class java.lang.Character")) {
            filedType = "character";
            type = filedType;
          } else if (filedType.equals("class java.lang.Boolean")) {
            filedType = "boolean";
            type = filedType;
          } else if (filedType.equals("class java.lang.Long")) {
            filedType = "long";
            type = filedType;
          }

        }
      }
    }
    return type;
  }

  /**
   * <b>Summary: </b> getSortColumnIndex(获取指定字段索引)
   *
   * @param fileName
   * @param fieldString
   * @return
   */
  protected static String getSortColumnIndex(String fileName, String[] fieldString) {
    String index = "";
    if (fieldString.length > 0) {
      for (int i = 0; i < fieldString.length; i++) {
        if (fileName.equals(fieldString[i])) {
          int j = i + 1;
          index = oConvertUtils.getString(j);
        }
      }
    }
    return index;

  }

  // JSON返回页面MAP方式
  public static void ListtoView(HttpServletResponse response, PageList pageList) {
    response.setContentType("application/json");
    response.setHeader("Cache-Control", "no-store");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("total", pageList.getCount());
    map.put("rows", pageList.getResultList());
    ObjectMapper mapper = new ObjectMapper();
//		JSONObject jsonObject = JSONObject.fromObject(map);
    try {
      mapper.writeValue(response.getWriter(), map);
    } catch (JsonGenerationException e) {
      e.printStackTrace();
    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 控件类型：easyui
   * 返回datagrid JSON数据
   *
   * @param response
   * @param dg
   */
  public static void datagrid(HttpServletResponse response, DataGrid dg) {
    response.setContentType("application/json");
    response.setHeader("Cache-Control", "no-store");
    JSONObject object = TagUtil.getJson(dg);
    try {
      PrintWriter pw = response.getWriter();
      pw.write(object.toString());
      pw.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 控件类型：datatable
   * 返回datatable JSON数据
   *
   * @param response
   * @param dataTableReturn
   */
  public static void datatable(HttpServletResponse response, DataTableReturn dataTableReturn, String field) {
    response.setContentType("application/json");
    response.setHeader("Cache-Control", "no-store");
    JSONObject object = TagUtil.getJson(dataTableReturn, field);
    try {
      response.getWriter().write(object.toString());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 手工拼接JSON
   */
  public static String getComboBoxJson(List<TSRole> list, List<TSRole> roles) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[");
    for (TSRole node : list) {
      if (roles.size() > 0) {
        buffer.append("{\"id\":" + node.getId() + ",\"text\":\"" + node.getRoleName() + "\"");
        for (TSRole node1 : roles) {
          if (node.getId() == node1.getId()) {
            buffer.append(",\"selected\":true");
          }
        }
        buffer.append("},");
      } else {
        buffer.append("{\"id\":" + node.getId() + ",\"text\":\"" + node.getRoleName() + "\"},");
      }

    }
    buffer.append("]");

    // 将,\n]替换成\n]

    String tmp = buffer.toString();
    tmp = tmp.replaceAll(",]", "]");
    return tmp;

  }

  /**
   * 根据模型生成JSON
   *
   * @param all      全部对象
   * @param in       已拥有的对象
   * @param comboBox 模型
   * @return
   */
  public static List<ComboBox> getComboBox(List all, List in, ComboBox comboBox) {
    List<ComboBox> comboxBoxs = new ArrayList<ComboBox>();
    String[] fields = new String[]{comboBox.getId(), comboBox.getText()};
    Object[] values = new Object[fields.length];
    for (Object node : all) {
      ComboBox box = new ComboBox();
      ReflectHelper reflectHelper = new ReflectHelper(node);
      for (int i = 0; i < fields.length; i++) {
        String fieldName = fields[i].toString();
        values[i] = reflectHelper.getMethodValue(fieldName);
      }
      box.setId(values[0].toString());
      box.setText(values[1].toString());
      if (in != null) {
        for (Object node1 : in) {
          ReflectHelper reflectHelper2 = new ReflectHelper(node);
          if (node1 != null) {
            String fieldName = fields[0].toString();
            String test = reflectHelper2.getMethodValue(fieldName).toString();
            if (values[0].toString().equals(test)) {
              box.setSelected(true);
            }
          }
        }
      }
      comboxBoxs.add(box);
    }
    return comboxBoxs;

  }

  /**
   * 获取自定义函数名
   *
   * @param functionname
   * @return
   */
  public static String getFunction(String functionname) {
    int index = functionname.indexOf("(");
    if (index == -1) {
      return functionname;
    } else {
      return functionname.substring(0, functionname.indexOf("("));
    }
  }

  /**
   * 获取自定义函数的参数
   *
   * @param functionname
   * @return
   */
  public static String getFunParams(String functionname) {
    int index = functionname.indexOf("(");
    String param = "";
    if (index != -1) {
      String testparam = functionname.substring(
              functionname.indexOf("(") + 1, functionname.length() - 1);
      if (StringUtil.isNotEmpty(testparam)) {
        String[] params = testparam.split(",");
        for (String string : params) {
          param += (string.indexOf("{") != -1) ? ("'\"+"
                  + string.substring(1, string.length() - 1) + "+\"',")
                  : ("'\"+rec." + (string.equals("date") ? string + ".substring(0,10)" : string) + "+\"',");
        }
      }
    }
    param += "'\"+index+\"'";// 传出行索引号参数
    return param;
  }
}
