package com.qihang.winter.winterdao.spring.rowMapper;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

/**
 * 使用默认的key作为关键字
 *
 * @author Zerrion
 * @version 1.0
 * @date 2013-9-27
 */
public class WinterColumnOriginalMapRowMapper implements
        RowMapper<Map<String, Object>> {

  public WinterColumnOriginalMapRowMapper() {
  }

  @SuppressWarnings("unchecked")
  protected Map<String, Object> createColumnMap(int columnCount) {
    return new LinkedMap(columnCount);
  }

  protected String getColumnKey(String columnName) {
    return columnName;
  }

  protected Object getColumnValue(ResultSet rs, int index)
          throws SQLException {
    return JdbcUtils.getResultSetValue(rs, index);
  }

  public Map<String, Object> mapRow(ResultSet resultset, int rowNum)
          throws SQLException {
    ResultSetMetaData rsmd = resultset.getMetaData();
    int columnCount = rsmd.getColumnCount();
    Map<String, Object> mapOfColValues = createColumnMap(columnCount);
    for (int i = 1; i <= columnCount; i++) {
      String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
      Object obj = getColumnValue(resultset, i);
      mapOfColValues.put(key, obj);
    }

    return mapOfColValues;
  }

}
