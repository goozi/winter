package com.qihang.winter.codegenerate.database;

import com.qihang.winter.codegenerate.pojo.TableConvert;
import com.qihang.winter.codegenerate.util.CodeResourceUtil;
import com.qihang.winter.codegenerate.util.CodeStringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class JeecgReadTable {
  private static final Log log = LogFactory.getLog(JeecgReadTable.class);

  private static final long serialVersionUID = -5324160085184088010L;
  private Connection conn;
  private Statement stmt;
  private String sql;
  private ResultSet rs;

  public static void main(String[] args)
          throws SQLException {
    try {
      List<com.qihang.winter.codegenerate.pojo.Columnt> cls = new JeecgReadTable().readTableColumn("person");
      for (com.qihang.winter.codegenerate.pojo.Columnt c : cls) {
        System.out.println(c.getFieldName());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(ArrayUtils.toString(new JeecgReadTable().readAllTableNames()));
  }

  public List<String> readAllTableNames() throws SQLException {
    ArrayList tableNames = new ArrayList(0);

    try {
      Class.forName(CodeResourceUtil.DIVER_NAME);
      this.conn = DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD);
      this.stmt = this.conn.createStatement(1005, 1007);
      if (CodeResourceUtil.DATABASE_TYPE.equals("mysql")) {
        this.sql = MessageFormat.format("select distinct table_name from information_schema.columns where table_schema = {0}", new Object[]{TableConvert.getV(CodeResourceUtil.DATABASE_NAME)});
      }

      if (CodeResourceUtil.DATABASE_TYPE.equals("oracle")) {
        this.sql = " select distinct colstable.table_name as  table_name from user_tab_cols colstable";
      }

      if (CodeResourceUtil.DATABASE_TYPE.equals("postgresql")) {
        this.sql = "SELECT distinct c.relname AS  table_name FROM pg_class c";
      }

      if (CodeResourceUtil.DATABASE_TYPE.equals("sqlserver")) {
        this.sql = "select * from sys.objects c where c.type in ('U','V') ";
      }

      this.rs = this.stmt.executeQuery(this.sql);

      while (this.rs.next()) {
        String e = this.rs.getString(1);
        tableNames.add(e);
      }
    } catch (Exception var11) {
      var11.printStackTrace();
    } finally {
      try {
        if (this.stmt != null) {
          this.stmt.close();
          this.stmt = null;
          System.gc();
        }

        if (this.conn != null) {
          this.conn.close();
          this.conn = null;
          System.gc();
        }
      } catch (SQLException var10) {
        throw var10;
      }

    }

    return tableNames;
  }

  /**
   * 读取表中字段信息
   * @param tableName
   * @return
   * @throws Exception
   * @modify Zerrion 2015-11-11 对sql server加入视图支持
   */
  public List<com.qihang.winter.codegenerate.pojo.Columnt> readTableColumn(String tableName)
          throws Exception {
    List<com.qihang.winter.codegenerate.pojo.Columnt> columntList = new ArrayList();
    try {
      Class.forName(com.qihang.winter.codegenerate.util.CodeResourceUtil.DIVER_NAME);
      this.conn = DriverManager.getConnection(com.qihang.winter.codegenerate.util.CodeResourceUtil.URL, com.qihang.winter.codegenerate.util.CodeResourceUtil.USERNAME, com.qihang.winter.codegenerate.util.CodeResourceUtil.PASSWORD);
      this.stmt = this.conn.createStatement(1005,
              1007);


      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("mysql")) {
        this.sql = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1}", new Object[]{TableConvert.getV(tableName.toUpperCase()), TableConvert.getV(com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_NAME)});
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("oracle")) {
        this.sql = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", new Object[]{TableConvert.getV(tableName.toUpperCase())});
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("postgresql")) {
        this.sql = MessageFormat.format("SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ", new Object[]{TableConvert.getV(tableName.toLowerCase())});
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("sqlserver")) {
        this.sql = MessageFormat.format("select cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type='''U''' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}", new Object[]{TableConvert.getV(tableName.toLowerCase())});
      }


      this.rs = this.stmt.executeQuery(this.sql);
      this.rs.last();
      int fieldNum = this.rs.getRow();
      int n = fieldNum;

      if (n > 0) {
        com.qihang.winter.codegenerate.pojo.Columnt columnt = new com.qihang.winter.codegenerate.pojo.Columnt();

        if (com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_FILED_CONVERT) {
          columnt.setFieldName(formatField(this.rs.getString(1).toLowerCase()));
        } else {
          columnt.setFieldName(this.rs.getString(1).toLowerCase());
        }


        columnt.setFieldDbName(this.rs.getString(1).toUpperCase());
        columnt.setFieldType(formatField(this.rs.getString(2).toLowerCase()));

        columnt.setPrecision(this.rs.getString(4));
        columnt.setScale(this.rs.getString(5));
        columnt.setCharmaxLength(this.rs.getString(6));
        columnt.setNullable(TableConvert.getNullAble(this.rs.getString(7)));

        formatFieldClassType(columnt);
        columnt.setFiledComment(StringUtils.isBlank(this.rs.getString(3)) ? columnt.getFieldName() : this.rs.getString(3));


        String[] ui_filter_fields = new String[0];
        if (com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_UI_FILTER_FIELDS != null) {
          ui_filter_fields = com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_UI_FILTER_FIELDS.toLowerCase().split(",");
        }

        if ((!com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_TABLE_ID.equals(columnt.getFieldName())) &&
                (!CodeStringUtils.isIn(columnt.getFieldDbName().toLowerCase(), ui_filter_fields))) {

          columntList.add(columnt);
        }
        while (this.rs.previous()) {
          com.qihang.winter.codegenerate.pojo.Columnt po = new com.qihang.winter.codegenerate.pojo.Columnt();

          if (com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_FILED_CONVERT) {
            po.setFieldName(formatField(this.rs.getString(1).toLowerCase()));
          } else {
            po.setFieldName(this.rs.getString(1).toLowerCase());
          }


          po.setFieldDbName(this.rs.getString(1).toUpperCase());

          if ((!com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_TABLE_ID.equals(po.getFieldName())) &&
                  (!CodeStringUtils.isIn(po.getFieldDbName().toLowerCase(), ui_filter_fields))) {

            po.setFieldType(formatField(this.rs.getString(2).toLowerCase()));


            po.setPrecision(this.rs.getString(4));
            po.setScale(this.rs.getString(5));
            po.setCharmaxLength(this.rs.getString(6));
            po.setNullable(TableConvert.getNullAble(this.rs.getString(7)));

            formatFieldClassType(po);
            po.setFiledComment(StringUtils.isBlank(this.rs.getString(3)) ? po.getFieldName() : this.rs.getString(3));
            columntList.add(po);
          }
        }
      } else {
        throw new Exception("该表不存在或者表中没有字段");
      }
    } catch (ClassNotFoundException e) {
      e = e;
      throw e;
    } catch (SQLException e) {
      e = e;
      throw e;
    } finally {
      try {
        if (this.stmt != null) {
          this.stmt.close();
          this.stmt = null;
          System.gc();
        }
        if (this.conn != null) {
          this.conn.close();
          this.conn = null;
          System.gc();
        }
      } catch (SQLException e) {
        throw e;
      }
    }
    try {
      if (this.stmt != null) {
        this.stmt.close();
        this.stmt = null;
        System.gc();
      }
      if (this.conn != null) {
        this.conn.close();
        this.conn = null;
        System.gc();
      }
    } catch (SQLException e) {
      throw e;
    }


    List<com.qihang.winter.codegenerate.pojo.Columnt> rsList = new ArrayList();
    for (int i = columntList.size() - 1; i >= 0; i--) {
      com.qihang.winter.codegenerate.pojo.Columnt ch = (com.qihang.winter.codegenerate.pojo.Columnt) columntList.get(i);
      rsList.add(ch);
    }
    return rsList;
  }


  public List<com.qihang.winter.codegenerate.pojo.Columnt> readOriginalTableColumn(String tableName)
          throws Exception {
    List<com.qihang.winter.codegenerate.pojo.Columnt> columntList = new ArrayList();
    try {
      Class.forName(com.qihang.winter.codegenerate.util.CodeResourceUtil.DIVER_NAME);
      this.conn = DriverManager.getConnection(com.qihang.winter.codegenerate.util.CodeResourceUtil.URL, com.qihang.winter.codegenerate.util.CodeResourceUtil.USERNAME, com.qihang.winter.codegenerate.util.CodeResourceUtil.PASSWORD);
      this.stmt = this.conn.createStatement(1005,
              1007);


      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("mysql")) {
        this.sql = MessageFormat.format("select column_name,data_type,column_comment,numeric_precision,numeric_scale,character_maximum_length,is_nullable nullable from information_schema.columns where table_name = {0} and table_schema = {1}", new Object[]{TableConvert.getV(tableName.toUpperCase()), TableConvert.getV(com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_NAME)});
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("oracle")) {
        this.sql = MessageFormat.format(" select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment, colstable.Data_Precision column_precision, colstable.Data_Scale column_scale,colstable.Char_Length,colstable.nullable from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = {0}", new Object[]{TableConvert.getV(tableName.toUpperCase())});
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("postgresql")) {
        this.sql = MessageFormat.format("SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ", new Object[]{TableConvert.getV(tableName.toLowerCase())});
      }
      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("sqlserver")) {
        this.sql = MessageFormat.format("select cast(a.name as varchar(50)) column_name,  cast(b.name as" +
                " varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  " +
                "cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision, " +
                " cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  " +
                "(case when a.is_nullable=1 then '''y''' else '''n''' end) nullable   from sys.columns a " +
                "left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on" +
                " a.object_id=c.object_id and (c.type='''U''' or c.type='''V''') left join sys.extended_properties e on " +
                "e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}",
                new Object[]{TableConvert.getV(tableName.toLowerCase())});
      }

      this.rs = this.stmt.executeQuery(this.sql);
      this.rs.last();
      int fieldNum = this.rs.getRow();
      int n = fieldNum;

      if (n > 0) {
        com.qihang.winter.codegenerate.pojo.Columnt columnt = new com.qihang.winter.codegenerate.pojo.Columnt();

        if (com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_FILED_CONVERT) {
          columnt.setFieldName(formatField(this.rs.getString(1).toLowerCase()));
        } else {
          columnt.setFieldName(this.rs.getString(1).toLowerCase());
        }


        columnt.setFieldDbName(this.rs.getString(1).toUpperCase());

        columnt.setPrecision(TableConvert.getNullString(this.rs.getString(4)));
        columnt.setScale(TableConvert.getNullString(this.rs.getString(5)));
        columnt.setCharmaxLength(TableConvert.getNullString(this.rs.getString(6)));
        columnt.setNullable(TableConvert.getNullAble(this.rs.getString(7)));

        columnt.setFieldType(formatDataType(this.rs.getString(2).toLowerCase(), columnt.getPrecision(), columnt.getScale()));

        formatFieldClassType(columnt);
        columnt.setFiledComment(StringUtils.isBlank(this.rs.getString(3)) ? columnt.getFieldName() : this.rs.getString(3));


        columntList.add(columnt);
        while (this.rs.previous()) {
          com.qihang.winter.codegenerate.pojo.Columnt po = new com.qihang.winter.codegenerate.pojo.Columnt();

          if (com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_FILED_CONVERT) {
            po.setFieldName(formatField(this.rs.getString(1).toLowerCase()));
          } else {
            po.setFieldName(this.rs.getString(1).toLowerCase());
          }


          po.setFieldDbName(this.rs.getString(1).toUpperCase());

          po.setPrecision(TableConvert.getNullString(this.rs.getString(4)));
          po.setScale(TableConvert.getNullString(this.rs.getString(5)));
          po.setCharmaxLength(TableConvert.getNullString(this.rs.getString(6)));
          po.setNullable(TableConvert.getNullAble(this.rs.getString(7)));

          po.setFieldType(formatDataType(this.rs.getString(2).toLowerCase(), po.getPrecision(), po.getScale()));

          formatFieldClassType(po);
          po.setFiledComment(StringUtils.isBlank(this.rs.getString(3)) ? po.getFieldName() : this.rs.getString(3));
          columntList.add(po);
        }
      } else {
        throw new Exception("该表不存在或者表中没有字段");
      }
    } catch (ClassNotFoundException e) {
      e = e;
      throw e;
    } catch (SQLException e) {
      e = e;
      throw e;
    } finally {
      try {
        if (this.stmt != null) {
          this.stmt.close();
          this.stmt = null;
          System.gc();
        }
        if (this.conn != null) {
          this.conn.close();
          this.conn = null;
          System.gc();
        }
      } catch (SQLException e) {
        throw e;
      }
    }
    try {
      if (this.stmt != null) {
        this.stmt.close();
        this.stmt = null;
        System.gc();
      }
      if (this.conn != null) {
        this.conn.close();
        this.conn = null;
        System.gc();
      }
    } catch (SQLException e) {
      throw e;
    }


    List<com.qihang.winter.codegenerate.pojo.Columnt> rsList = new ArrayList();
    for (int i = columntList.size() - 1; i >= 0; i--) {
      com.qihang.winter.codegenerate.pojo.Columnt ch = (com.qihang.winter.codegenerate.pojo.Columnt) columntList.get(i);
      rsList.add(ch);
    }
    return rsList;
  }


  public static String formatField(String field) {
    String[] strs = field.split("_");
    field = "";
    int m = 0;
    for (int length = strs.length; m < length; m++) {
      if (m > 0) {
        String tempStr = strs[m].toLowerCase();
        tempStr = tempStr.substring(0, 1).toUpperCase() +
                tempStr.substring(1, tempStr.length());
        field = field + tempStr;
      } else {

        // 修改代码，直接使用字段名第一个字母改小写
        String tempStr = strs[m];
        tempStr = tempStr.substring(0, 1).toLowerCase() +
                tempStr.substring(1, tempStr.length());

        field = field + tempStr;

        //        field = field + strs[m].toLowerCase();

      }
    }
    return field;
  }


  public static String formatFieldCapital(String field) {
    String[] strs = field.split("_");
    field = "";
    int m = 0;
    for (int length = strs.length; m < length; m++) {
      if (m > 0) {
        String tempStr = strs[m].toLowerCase();
        tempStr = tempStr.substring(0, 1).toUpperCase() +
                tempStr.substring(1, tempStr.length());
        field = field + tempStr;
      } else {
        field = field + strs[m].toLowerCase();
      }
    }
    field = field.substring(0, 1).toUpperCase() + field.substring(1);
    return field;
  }


  public boolean checkTableExist(String tableName) {
    try {
      System.out.println("数据库驱动: " + com.qihang.winter.codegenerate.util.CodeResourceUtil.DIVER_NAME);
      Class.forName(com.qihang.winter.codegenerate.util.CodeResourceUtil.DIVER_NAME);
      this.conn = DriverManager.getConnection(com.qihang.winter.codegenerate.util.CodeResourceUtil.URL, com.qihang.winter.codegenerate.util.CodeResourceUtil.USERNAME, com.qihang.winter.codegenerate.util.CodeResourceUtil.PASSWORD);
      this.stmt = this.conn.createStatement(1005,
              1007);


      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("mysql")) {
        this.sql =

                ("select column_name,data_type,column_comment,0,0 from information_schema.columns where table_name = '" + tableName.toUpperCase() + "'" + " and table_schema = '" + com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_NAME + "'");
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("oracle")) {
        this.sql =


                ("select colstable.column_name column_name, colstable.data_type data_type, commentstable.comments column_comment from user_tab_cols colstable  inner join user_col_comments commentstable  on colstable.column_name = commentstable.column_name  where colstable.table_name = commentstable.table_name  and colstable.table_name = '" + tableName.toUpperCase() + "'");
      }

      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("postgresql")) {
        this.sql = MessageFormat.format("SELECT a.attname AS  field,t.typname AS type,col_description(a.attrelid,a.attnum) as comment,null as column_precision,null as column_scale,null as Char_Length,a.attnotnull  FROM pg_class c,pg_attribute  a,pg_type t  WHERE c.relname = {0} and a.attnum > 0  and a.attrelid = c.oid and a.atttypid = t.oid  ORDER BY a.attnum ", new Object[]{TableConvert.getV(tableName.toLowerCase())});
      }
      if (com.qihang.winter.codegenerate.util.CodeResourceUtil.DATABASE_TYPE.equals("sqlserver")) {
        this.sql = MessageFormat.format("select cast(a.name as varchar(50)) column_name,  cast(b.name as varchar(50)) data_type,  cast(e.value as varchar(200)) comment,  cast(ColumnProperty(a.object_id,a.Name,'''Precision''') as int) num_precision,  cast(ColumnProperty(a.object_id,a.Name,'''Scale''') as int) num_scale,  a.max_length,  (case when a.is_nullable=1 then '''y''' else '''n''' end) nullable   from sys.columns a left join sys.types b on a.user_type_id=b.user_type_id left join sys.objects c on a.object_id=c.object_id and c.type='''U''' left join sys.extended_properties e on e.major_id=c.object_id and e.minor_id=a.column_id and e.class=1 where c.name={0}", new Object[]{TableConvert.getV(tableName.toLowerCase())});
      }


      this.rs = this.stmt.executeQuery(this.sql);
      this.rs.last();
      int fieldNum = this.rs.getRow();
      if (fieldNum > 0) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }


  private void formatFieldClassType(com.qihang.winter.codegenerate.pojo.Columnt columnt) {
    String fieldType = columnt.getFieldType();
    String scale = columnt.getScale();

    columnt.setClassType("inputxt");

    if ("N".equals(columnt.getNullable())) {
      columnt.setOptionType("*");
    }
    if (("datetime".equals(fieldType)) || (fieldType.contains("time"))) {
      columnt.setClassType("easyui-datetimebox");
    } else if ("date".equals(fieldType)) {
      columnt.setClassType("easyui-datebox");
    } else if (fieldType.contains("int")) {
      columnt.setOptionType("n");
    } else if ("number".equals(fieldType)) {
      if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
        columnt.setOptionType("d");
      }
    } else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
      columnt.setOptionType("d");
    } else if ("numeric".equals(fieldType)) {
      columnt.setOptionType("d");
    }
  }


  private String formatDataType(String dataType, String precision, String scale) {
    if (dataType.contains("char")) {
      dataType = "java.lang.String";
    } else if (dataType.contains("int")) {
      dataType = "java.lang.Integer";
    } else if (dataType.contains("float")) {
      dataType = "java.lang.Float";
    } else if (dataType.contains("double")) {
      dataType = "java.lang.Double";
    } else if (dataType.contains("number")) {
      if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
        dataType = "java.math.BigDecimal";
      } else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 10)) {
        dataType = "java.lang.Long";
      } else {
        dataType = "java.lang.Integer";
      }
    } else if (dataType.contains("decimal")) {
      dataType = "BigDecimal";
    } else if (dataType.contains("date")) {
      dataType = "java.util.Date";
    } else if (dataType.contains("time")) {
      dataType = "java.util.Date";
    } else if (dataType.contains("blob")) {
      dataType = "byte[]";
    } else if (dataType.contains("clob")) {
      dataType = "java.sql.Clob";
    } else if (dataType.contains("numeric")) {
      dataType = "BigDecimal";
    } else {
      dataType = "java.lang.Object";
    }
    return dataType;
  }
}
