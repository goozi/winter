package com.qihang.winter.core.extend.datasource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.qihang.winter.core.util.*;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import com.qihang.winter.web.system.service.UserService;
import org.activiti.engine.impl.variable.DateType;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.util.Assert;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *类名：DynamicDataSource.java
 *功能：动态数据源类
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
  private Map<Object, Object> _targetDataSources;
  private Logger log = Logger.getLogger(this.getClass());
//  private boolean lenientFallback = true;//hjhadd20160905
//  private Map<Object, DataSource> resolvedDataSources;//hjhadd20160905
//  private DataSource resolvedDefaultDataSource;//hjhadd20160905

	/* 
	 * 该方法必须要重写  方法是为了根据数据库标示符取得当前的数据库
	 */
	@Override
	protected Object determineCurrentLookupKey() {
      //super.setLenientFallback(false);//hjh20160905 add,为了让父类方法的determineTargetDataSource中不返回默认数据源，而我们设定的数据源
      DataSourceType dataSourceType= DataSourceContextHolder.getDataSourceType();
//      if(dataSourceType == null){
//        HttpSession httpSession = null;
//        try{
//          httpSession = ContextHolderUtils.getSession();
//        }catch (Exception e){
//
//        }
//        Object sessiondataSourceType = httpSession==null?null:httpSession.getAttribute("dataSourceType");
//        if (sessiondataSourceType!=null){//如果本地线程中当前数据源存储为空，则默认从http会话中取。如果http会话中为空才使用默认数据源
//          dataSourceType = DataSourceType.valueOf(sessiondataSourceType.toString());
//        }else {
//          dataSourceType = DataSourceType.dataSource_jeecg;
//        }
//      }
      //应该还是DataSourceContextHolder.getDataSourceType()乱了，决定，优先从http会话中取DataSourceType()，如果没有再用线程或默认值。同时要求手动切换数据源时要应记录http会话里的值，通过改会话值来切换，使用后，再用原值切换回来
        HttpSession httpSession = null;
        try{
          httpSession = ContextHolderUtils.getSession();
        }catch (Exception e){

        }
        Object sessiondataSourceType = httpSession==null?null:httpSession.getAttribute("dataSourceType");
        if (sessiondataSourceType!=null){//如果本地线程中当前数据源存储为空，则默认从http会话中取。如果http会话中为空才使用默认数据源
          dataSourceType = DataSourceType.valueOf(sessiondataSourceType.toString());
        }else {
          dataSourceType = DataSourceType.dataSource_jeecg;
        }
      if (!DBUtil.isOpenMultDataSource()){//如果未开启多数据源切换，则返回的数据源永远是主库数据源
        dataSourceType = DataSourceType.dataSource_jeecg;
      }
      if(this._targetDataSources.containsKey(dataSourceType)) {
        return dataSourceType;
      }else{
        this.createDataSource(dataSourceType);
        return dataSourceType;
      }
	}
  @Override//hjhadd20160905
  protected DataSource determineTargetDataSource() {
//    Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
//    Object lookupKey = this.determineCurrentLookupKey();
//    DataSource dataSource = (DataSource)this.resolvedDataSources.get(lookupKey);
//    if(dataSource == null && (this.lenientFallback || lookupKey == null)) {
//      dataSource = this.resolvedDefaultDataSource;
//    }
//
//    if(dataSource == null) {
//      throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
//    } else {
//      return dataSource;
//    }
    Object lookupKey = this.determineCurrentLookupKey();
    DataSource dataSource;
    if(this._targetDataSources.containsKey(lookupKey)) {
      dataSource = (DataSource) this._targetDataSources.get(lookupKey);
//      if (!this.resolvedDataSources.containsKey(lookupKey)){//
//        this.resolvedDataSources.put(lookupKey,dataSource);
//      }
    }else{
      dataSource = super.determineTargetDataSource();
    }
    return dataSource;
  }

	
	public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
		super.setDataSourceLookup(dataSourceLookup);
	}

	
	public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
	}

	
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
    this._targetDataSources = targetDataSources;
	  super.setTargetDataSources(targetDataSources);
	}

	/**
   * 创建数据源
   * 从系统数据源表中根据数据源名取得驱动名,URL,用户名,密码等配置
	 */
	private BasicDataSource createDataSource(DataSourceType dataSourceType){
    Connection conn = null;
    HashMap<String, Object> map = null;
    try {
      //方案一：会死循环
//      conn = this.getConnection();

      //方案二：同样会死循环
//      JdbcTemplate jdbcTemplate = ApplicationContextUtil.getContext().getBean(JdbcTemplate.class);
//      DynamicDataSourceEntity dynamicDataSourceEntity = jdbcTemplate.queryForObject("SELECT * FROM t_s_data_source WHERE db_key = '" + dataSourceType.toString() + "'", DynamicDataSourceEntity.class);
      map = new HashMap<String, Object>();
//      if (dynamicDataSourceEntity!=null){
//        map.put("db_key", dynamicDataSourceEntity.getDbKey());
//        map.put("driver_class", dynamicDataSourceEntity.getDriverClass());
//        map.put("url", dynamicDataSourceEntity.getUrl());
//        map.put("db_user", dynamicDataSourceEntity.getDbUser());
//        map.put("db_password", dynamicDataSourceEntity.getDbPassword());
//      }
      //方案三：从数据库连接配置文件中读取主库配置，通过jdbc来连接，查询要切换数据源的参数
      PropertiesUtil propertiesUtil = new PropertiesUtil("dbconfig.properties");
      Class.forName(propertiesUtil.readProperty("diver.name"));
      conn = DriverManager.getConnection(propertiesUtil.readProperty("jdbc.url.jeecg"),propertiesUtil.readProperty("jdbc.username.jeecg"),propertiesUtil.readProperty("jdbc.password.jeecg"));
      PreparedStatement ps = conn.prepareStatement("SELECT * FROM t_s_data_source WHERE db_key = ?");
      ps.setString(1, dataSourceType.toString());
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        map.put("db_key", rs.getString("db_key"));
        map.put("driver_class", rs.getString("driver_class"));
        map.put("url", rs.getString("url"));
        map.put("db_user", rs.getString("db_user"));
        map.put("db_password", rs.getString("db_password"));
      }
      rs.close();
      ps.close();
    }  catch (Exception e) {
      log.error(e);
      e.printStackTrace();
    } finally {
      try {
        if (conn!=null) {
          conn.close();
        }
      } catch (SQLException e) {
        log.error(e);
      }
    }
    if (null != map) {
      String driverClass = map.get("driver_class").toString();
      String url = map.get("url").toString();
      String dbUser = map.get("db_user").toString();
      String dbPassword = map.get("db_password").toString();
      BasicDataSource dataSource = new BasicDataSource();
      dataSource.setDriverClassName(driverClass);
      dataSource.setUrl(url);
      dataSource.setUsername(dbUser);
      dataSource.setPassword(dbPassword);
      dataSource.setTestWhileIdle(true);
      this._targetDataSources.put(dataSourceType,dataSource);
      //super.resolveSpecifiedDataSource(dataSource);
      super.afterPropertiesSet();
      this.setTargetDataSources(this._targetDataSources);
      return dataSource;
    }
    return null;

  }


}
