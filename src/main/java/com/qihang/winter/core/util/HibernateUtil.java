package com.qihang.winter.core.util;

/**
 * Created by LenovoM4550 on 2016-05-13.
 */


import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 功能描述：根据实体类得到对应的表名、主键名、字段名工具类
 * </p>
 * 注：po类名须与对应映射文件名一致，即Student.java与Student.hbm.xml
 *
 * @Date：2016-05-13
 */
public class HibernateUtil {

  /**
   * 获取字段
   *
   * @param classtype
   */
  @SuppressWarnings(value = {"rawtypes"})
  public static String getColumnName(Class classtype, String propertyName) throws NoSuchMethodException {
    String methodName = "get"+StringUtil.firstUpperCase(propertyName);
    Method method = classtype.getDeclaredMethod(methodName);
    Annotation[] annotations = method.getAnnotations();
    for (int j = 0; j < annotations.length; j++) {
      if (annotations[j] instanceof Column) {
        Column column = (Column) annotations[j];
        return column.name();
      }
    }
    return null;
  }

  /**
   * 获取表名
   *
   * @param classtype
   * @return
   */
  @SuppressWarnings(value = {"rawtypes"})
  public static String getTableName(Class classtype) {
    Annotation[] anno = classtype.getAnnotations();
    String tableName = "";
    for (int i = 0; i < anno.length; i++) {
      if (anno[i] instanceof Table) {
        Table table = (Table) anno[i];
        System.out.println(table.name());
        tableName = table.name();
      }
    }
    return tableName;
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
//      System.out.println(getTableName(IntelligenceNetVPNAndSCP.class));

  }
}

