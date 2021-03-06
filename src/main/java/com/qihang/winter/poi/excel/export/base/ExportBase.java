/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qihang.winter.poi.excel.export.base;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.core.util.ApplicationContextUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.TSType;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出基础处理,不设计POI,只设计对象,保证复用性
 *
 * @author Zerrion
 * @date 2014年8月9日 下午11:01:32
 */
public class ExportBase {

  protected com.qihang.winter.poi.handler.inter.IExcelDataHandler dataHanlder;

  protected List<String> needHanlderList;

  /**
   * 创建导出实体对象
   *
   * @param field
   * @param targetId
   * @param pojoClass
   * @param getMethods
   * @return
   * @throws Exception
   */
  private com.qihang.winter.poi.excel.entity.params.ExcelExportEntity createExcelExportEntity(Field field, String targetId,
                                                                                              Class<?> pojoClass, List<Method> getMethods)
          throws Exception {
    com.qihang.winter.poi.excel.annotation.Excel excel = field.getAnnotation(com.qihang.winter.poi.excel.annotation.Excel.class);
    com.qihang.winter.poi.excel.entity.params.ExcelExportEntity excelEntity = new com.qihang.winter.poi.excel.entity.params.ExcelExportEntity();
    excelEntity.setType(excel.type());
    getExcelField(targetId, field, excelEntity, excel, pojoClass);
    if (getMethods != null) {
      List<Method> newMethods = new ArrayList<Method>();
      newMethods.addAll(getMethods);
      newMethods.add(excelEntity.getMethod());
      excelEntity.setMethods(newMethods);
    }
    return excelEntity;
  }

  private Object formatValue(Object value, com.qihang.winter.poi.excel.entity.params.ExcelExportEntity entity) throws Exception {
    Date temp = null;
    if (value instanceof String) {
      SimpleDateFormat format = new SimpleDateFormat(entity.getDatabaseFormat());
      temp = format.parse(value.toString());
    } else if (value instanceof Date) {
      temp = (Date) value;
    }
    if (temp != null) {
      SimpleDateFormat format = new SimpleDateFormat(entity.getFormat());
      value = format.format(temp);
    }
    return value;
  }

  /**
   * 获取需要导出的全部字段
   *
   * @param exclusions
   * @param targetId
   *            目标ID
   * @param fields
   * @throws Exception
   */
  public void getAllExcelField(String[] exclusions, String targetId, Field[] fields,
                               List<com.qihang.winter.poi.excel.entity.params.ExcelExportEntity> excelParams, Class<?> pojoClass,
                               List<Method> getMethods) throws Exception {
    List<String> exclusionsList = exclusions != null ? Arrays.asList(exclusions) : null;
    com.qihang.winter.poi.excel.entity.params.ExcelExportEntity excelEntity;
    // 遍历整个filed
    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      // 先判断是不是collection,在判断是不是java自带对象,之后就是我们自己的对象了
      if (com.qihang.winter.poi.util.PoiPublicUtil.isNotUserExcelUserThis(exclusionsList, field, targetId)) {
        continue;
      }
      // 首先判断Excel 可能一下特殊数据用户回自定义处理
      if (field.getAnnotation(com.qihang.winter.poi.excel.annotation.Excel.class) != null) {
        excelParams.add(createExcelExportEntity(field, targetId, pojoClass, getMethods));
      } else if (com.qihang.winter.poi.util.PoiPublicUtil.isCollection(field.getType())) {
        com.qihang.winter.poi.excel.annotation.ExcelCollection excel = field.getAnnotation(com.qihang.winter.poi.excel.annotation.ExcelCollection.class);
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        Class<?> clz = (Class<?>) pt.getActualTypeArguments()[0];
        List<com.qihang.winter.poi.excel.entity.params.ExcelExportEntity> list = new ArrayList<com.qihang.winter.poi.excel.entity.params.ExcelExportEntity>();
        getAllExcelField(exclusions, StringUtils.isNotEmpty(excel.id()) ? excel.id()
                : targetId, com.qihang.winter.poi.util.PoiPublicUtil.getClassFields(clz), list, clz, null);
        excelEntity = new com.qihang.winter.poi.excel.entity.params.ExcelExportEntity();
        excelEntity.setName(getExcelName(excel.name(), targetId));
        excelEntity.setOrderNum(getCellOrder(excel.orderNum(), targetId));
        excelEntity.setMethod(com.qihang.winter.poi.util.PoiPublicUtil.getMethod(field.getName(), pojoClass));
        excelEntity.setList(list);
        excelParams.add(excelEntity);
      } else {
        List<Method> newMethods = new ArrayList<Method>();
        if (getMethods != null) {
          newMethods.addAll(getMethods);
        }
        newMethods.add(com.qihang.winter.poi.util.PoiPublicUtil.getMethod(field.getName(), pojoClass));
        com.qihang.winter.poi.excel.annotation.ExcelEntity excel = field.getAnnotation(com.qihang.winter.poi.excel.annotation.ExcelEntity.class);
        getAllExcelField(exclusions, StringUtils.isNotEmpty(excel.id()) ? excel.id()
                        : targetId, com.qihang.winter.poi.util.PoiPublicUtil.getClassFields(field.getType()), excelParams,
                field.getType(), newMethods);
      }
    }
  }

  /**
   * 获取这个字段的顺序
   *
   * @param orderNum
   * @param targetId
   * @return
   */
  public int getCellOrder(String orderNum, String targetId) {
    if (isInteger(orderNum) || targetId == null) {
      return Integer.valueOf(orderNum);
    }
    String[] arr = orderNum.split(",");
    String[] temp;
    for (String str : arr) {
      temp = str.split("_");
      if (targetId.equals(temp[1])) {
        return Integer.valueOf(temp[0]);
      }
    }
    return 0;
  }

  /**
   * 获取填如这个cell的值,提供一些附加功能
   *
   * @param entity
   * @param obj
   * @return
   * @throws Exception
   */
  public Object getCellValue(com.qihang.winter.poi.excel.entity.params.ExcelExportEntity entity, Object obj) throws Exception {
    Object value;
    if (obj instanceof Map) {
      value = ((Map<?, ?>) obj).get(entity.getKey());
    } else {
      value = entity.getMethods() != null ? getFieldBySomeMethod(entity.getMethods(), obj)
              : entity.getMethod().invoke(obj, new Object[]{});
    }
    if (StringUtils.isNotEmpty(entity.getFormat())) {
      value = formatValue(value, entity);
    }
    if (entity.getReplace() != null && entity.getReplace().length > 0) {
      value = replaceValue(entity.getReplace(), String.valueOf(value));
    }
    if (needHanlderList != null && needHanlderList.contains(entity.getName())) {
      value = dataHanlder.exportHandler(obj, entity.getName(), value);
    }
    if (StringUtils.isNotEmpty(entity.getDicCode())) {
      if(StringUtil.isNotEmpty(value)) {
        CommonService commonService = (CommonService) ApplicationContextUtil.getContext().getBean("commonService");
        String[] typeCodeArray = value.toString().split(",");
        StringBuffer sb = new StringBuffer();
        for (String typeCode : typeCodeArray) {
          sb.append("'" + typeCode + "',");
        }
        List<TSType> tsTypeList = commonService.findByQueryString("from TSType where TSTypegroup.typegroupcode='" + entity.getDicCode() + "' and typecode in (" + sb.substring(0, sb.length() - 1) + ")");
        StringBuffer valueSb = new StringBuffer();
        for (TSType tsType : tsTypeList) {
          valueSb.append(tsType.getTypename() + ",");
        }
        if (valueSb.length() > 0) {
          value = valueSb.substring(0, valueSb.length() - 1);
        }
      }else{
        value = "";
      }
    }
    if (StringUtils.isNotEmpty(entity.getSuffix()) && value != null) {
      value = value + entity.getSuffix();
    }
    return value == null ? "" : value.toString();
  }

  /**
   * 获取集合的值
   * @param entity
   * @param obj
   * @return
   * @throws Exception
   */
  public Collection<?> getListCellValue(com.qihang.winter.poi.excel.entity.params.ExcelExportEntity entity, Object obj) throws Exception {
    Object value;
    if (obj instanceof Map) {
      value = ((Map<?, ?>) obj).get(entity.getKey());
    } else {
      value = (Collection<?>) entity.getMethod().invoke(obj, new Object[]{});
    }
    return (Collection<?>) value;
  }

  /**
   * 注解到导出对象的转换
   *
   * @param targetId
   * @param field
   * @param excelEntity
   * @param excel
   * @param pojoClass
   * @throws Exception
   */
  private void getExcelField(String targetId, Field field, com.qihang.winter.poi.excel.entity.params.ExcelExportEntity excelEntity,
                             com.qihang.winter.poi.excel.annotation.Excel excel, Class<?> pojoClass) throws Exception {
    excelEntity.setName(getExcelName(excel.name(), targetId));
    excelEntity.setWidth(excel.width());
    excelEntity.setHeight(excel.height());
    excelEntity.setNeedMerge(excel.needMerge());
    excelEntity.setMergeVertical(excel.mergeVertical());
    excelEntity.setMergeRely(excel.mergeRely());
    excelEntity.setReplace(excel.replace());
    excelEntity.setDicCode(excel.dicCode());
    excelEntity.setOrderNum(getCellOrder(excel.orderNum(), targetId));
    excelEntity.setWrap(excel.isWrap());
    excelEntity.setExportImageType(excel.imageType());
    excelEntity.setSuffix(excel.suffix());
    excelEntity.setDatabaseFormat(excel.databaseFormat());
    excelEntity.setFormat(StringUtils.isNotEmpty(excel.exportFormat()) ? excel.exportFormat()
            : excel.format());
    excelEntity.setStatistics(excel.isStatistics());
    String fieldname = field.getName();
    excelEntity.setMethod(com.qihang.winter.poi.util.PoiPublicUtil.getMethod(fieldname, pojoClass));
  }

  /**
   * 判断在这个单元格显示的名称
   *
   * @param exportName
   * @param targetId
   * @return
   */
  public String getExcelName(String exportName, String targetId) {
    if (exportName.indexOf(",") < 0) {
      return exportName;
    }
    String[] arr = exportName.split(",");
    for (String str : arr) {
      if (str.indexOf(targetId) != -1) {
        return str.split("_")[0];
      }
    }
    return null;
  }

  /**
   * 多个反射获取值
   *
   * @param list
   * @param t
   * @return
   * @throws Exception
   */
  public Object getFieldBySomeMethod(List<Method> list, Object t) throws Exception {
    for (Method m : list) {
      if (t == null) {
        t = "";
        break;
      }
      t = m.invoke(t, new Object[]{});
    }
    return t;
  }

  /**
   * 根据注解获取行高
   *
   * @param excelParams
   * @return
   */
  public short getRowHeight(List<com.qihang.winter.poi.excel.entity.params.ExcelExportEntity> excelParams) {
    double maxHeight = 0;
    for (int i = 0; i < excelParams.size(); i++) {
      maxHeight = maxHeight > excelParams.get(i).getHeight() ? maxHeight : excelParams.get(i)
              .getHeight();
      if (excelParams.get(i).getList() != null) {
        for (int j = 0; j < excelParams.get(i).getList().size(); j++) {
          maxHeight = maxHeight > excelParams.get(i).getList().get(j).getHeight() ? maxHeight
                  : excelParams.get(i).getList().get(j).getHeight();
        }
      }
    }
    return (short) (maxHeight * 50);
  }

  /**
   * 判断字符串是否是整数
   */
  public boolean isInteger(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private Object replaceValue(String[] replace, String value) {
    if(StringUtil.isNotEmpty(value)) {
      String[] temp;
      for (String str : replace) {
        temp = str.split("_");
        if (value.equals(temp[1])) {
          value = temp[0];
          break;
        }
      }
    }
    return value;
  }

  /**
   * 对字段根据用户设置排序
   */
  public void sortAllParams(List<com.qihang.winter.poi.excel.entity.params.ExcelExportEntity> excelParams) {
    Collections.sort(excelParams);
    for (com.qihang.winter.poi.excel.entity.params.ExcelExportEntity entity : excelParams) {
      if (entity.getList() != null) {
        Collections.sort(entity.getList());
      }
    }
  }

}
