package com.qihang.winter.web.system.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import com.mysql.jdbc.Connection;
import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.extend.datasource.DataSourceContextHolder;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.core.util.*;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import com.qihang.winter.web.system.service.DynamicDataSourceServiceI;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dynamicDataSourceService")
@Transactional
public class DynamicDataSourceServiceImpl extends CommonServiceImpl implements DynamicDataSourceServiceI {
	
	/**初始化数据库信息，TOMCAT启动时直接加入到内存中**/
	public List<DynamicDataSourceEntity> initDynamicDataSource() {
		DynamicDataSourceEntity.DynamicDataSourceMap.clear();
		List<DynamicDataSourceEntity> dynamicSourceEntityList = this.commonDao.loadAll(DynamicDataSourceEntity.class);
		if (DBUtil.isOpenMultDataSource()) {//如果 开启多数据源切换 才将数据源配置列表加载到动态数据源map列表中
			for (DynamicDataSourceEntity dynamicSourceEntity : dynamicSourceEntityList) {
				DynamicDataSourceEntity.DynamicDataSourceMap.put(dynamicSourceEntity.getDbKey(), dynamicSourceEntity);
			}
		}
		//todo:hjh测试本地先写主数据库对象,此代码不提交svn
		DynamicDataSourceEntity maindynamicSourceEntity = new DynamicDataSourceEntity();
		//数据库配置文件中读取默认主库的数据库连接参数
		PropertiesUtil propertiesUtil = new PropertiesUtil("dbconfig.properties");
		maindynamicSourceEntity.setDbKey("dataSource_jeecg");
		maindynamicSourceEntity.setUrl(propertiesUtil.readProperty("jdbc.url.jeecg"));
		maindynamicSourceEntity.setDriverClass(propertiesUtil.readProperty("diver.name"));
		maindynamicSourceEntity.setDbUser(propertiesUtil.readProperty("jdbc.username.jeecg"));
		maindynamicSourceEntity.setDbPassword(propertiesUtil.readProperty("jdbc.password.jeecg"));
		maindynamicSourceEntity.setDbType(propertiesUtil.readProperty("jdbc.dbType"));
		DynamicDataSourceEntity.DynamicDataSourceMap.put(maindynamicSourceEntity.getDbKey(), maindynamicSourceEntity);
		return dynamicSourceEntityList;
	}
	
	public static DynamicDataSourceEntity getDbSourceEntityByKey(String dbKey) {
		DynamicDataSourceEntity dynamicDataSourceEntity = DynamicDataSourceEntity.DynamicDataSourceMap.get(dbKey);
		
		return dynamicDataSourceEntity;
	}
	
	public void refleshCache() {
		initDynamicDataSource();
	}

	/**
	 * 获得当前连接的db_key及参数,从配置参数文件中读取数据库bin文件路径和初始化数据sql文件，根据新库名创建新的数据库,增加数据源配置记录，并初始化数据
	 *   目前仅支持对同一数据库服务器建新用户
	 * @param tSDataSource 新数据源
	 * @author hjh 2016-8-30
	 */
	//public void createDatabaseAndInitialize(String databaseName, String description){
	public void createDatabaseAndInitialize(DynamicDataSourceEntity tSDataSource){
		TScAccountConfigEntity tScAccountConfigEntity = commonDao.getEntity(TScAccountConfigEntity.class, tSDataSource.getAccountId());
		boolean isOk = DBUtil.createDatabaseAndInitialize(tScAccountConfigEntity, tSDataSource);
		if (isOk) {
			//5.保存新的数据源配置
			String databaseName = tSDataSource.getDbKey();
			tSDataSource.setDbUser(databaseName);
			tSDataSource.setDbPassword(databaseName);
			commonDao.save(tSDataSource);
		}else{
			throw new BusinessException("创建新库失败");
		}
	}



	/**
	 * 用于测试选择账套登录后，看一下当前的数据源连接是否有真的切换过来？
	 */
//	public void testCurConnection(){
//		Session session = commonDao.getSession();
//		try {
//			Connection conn = SessionFactoryUtils.getDataSource(session.getSessionFactory()).getConnection();
//			int i =0;
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}
}