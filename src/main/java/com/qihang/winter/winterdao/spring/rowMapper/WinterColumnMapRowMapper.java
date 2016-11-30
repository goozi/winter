package com.qihang.winter.winterdao.spring.rowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import com.qihang.winter.winterdao.spring.map.WinterDaoLinkedMap;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 使用小写的key 作为map的关键字
 * 
 * @author Zerrion
 * @date 2013-9-27
 * @version 1.0
 */
public class WinterColumnMapRowMapper implements RowMapper<Map<String, Object>> {

	public WinterColumnMapRowMapper() {
	}

	protected Map<String, Object> createColumnMap(int columnCount) {
		return new WinterDaoLinkedMap(columnCount);
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
