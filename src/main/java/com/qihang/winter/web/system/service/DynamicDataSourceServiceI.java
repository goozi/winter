package com.qihang.winter.web.system.service;

import java.util.List;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;

public interface DynamicDataSourceServiceI extends CommonService {
	
	public List<DynamicDataSourceEntity> initDynamicDataSource();
	
	public void refleshCache();

	/**
	 * 获得当前连接的db_key及参数,从配置参数文件中读取数据库bin文件路径和初始化数据sql文件，根据新库名创建新的数据库,增加数据源配置记录，并初始化数据
	 *   目前仅支持对同一数据库服务器建新用户
	 * @param tSDataSource 新数据源
	 * @author hjh 2016-8-30
	 */
	//public void createDatabaseAndInitialize(String databaseName, String description);
	public void createDatabaseAndInitialize(DynamicDataSourceEntity tSDataSource);

	/**
	 * 用于测试选择账套登录后，看一下当前的数据源连接是否有真的切换过来？
	 */
//	public void testCurConnection();
}
