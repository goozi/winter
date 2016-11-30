package com.qihang.buss.sc.sys.service;
import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface TScAccountConfigServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
	/**
	 * 添加一对多
	 *
	 */
	public void addMain(TScAccountConfigEntity tScAccountConfig,
						List<DynamicDataSourceEntity> tSDataSourceList) ;
	/**
	 * 修改一对多
	 *
	 */
	public void updateMain(TScAccountConfigEntity tScAccountConfig,
						   List<DynamicDataSourceEntity> tSDataSourceList);
	public void delMain (TScAccountConfigEntity tScAccountConfig);
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScAccountConfigEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScAccountConfigEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScAccountConfigEntity t);

	/**
	 * 开账操作
	 * @return
	 */
	public AjaxJson openAccount(String accountId,Date startDate,String sonId,String userId,String userName);

	/**
	 * 反开账操作
	 * @param id
	 * @return
	 */
	public AjaxJson unOpenAccount(String id);

	/**
	 * 按账套dbKey查询账套配置信息，从数据查询后会缓存到EhCache的accountConfigCache中，即下次查询都从缓存中取（不访问数据库），除非清除手动清除（即账套开启、关闭状态、系统设置时要手动清除缓存）
	 * @param dbKey
	 * @return
	 * @author hjh 2016-09-07
	 */
	public TScAccountConfigEntity findByDbKey(String dbKey);

	/**
	 * 按账套dbKey清除EhCache的accountConfigCache中值，即下次查询时要访问数据库
	 *   即账套开启、关闭状态、系统设置时要手动调用此方法来清除缓存
	 * @param dbKey
	 * @author hjh 2016-09-07
	 */
	public void clearAccountConfigCacheByDbKey(String dbKey);

	/**
	 * 按账套Id查询账套配置信息，从数据查询后会缓存到EhCache的accountConfigCache中，即下次查询都从缓存中取（不访问数据库），除非清除手动清除（即账套开启、关闭状态、系统设置时要手动清除缓存）
	 * @param accountId
	 * @return
	 * @author hjh 2016-09-07
	 */
	public TScAccountConfigEntity findByAccountId(String accountId);

	/**
	 * 按账套Id清除EhCache的accountConfigCache中值，即下次查询时要访问数据库
	 *   即账套开启、关闭状态、系统设置时要手动调用此方法来清除缓存
	 * @param accountId
	 * @author hjh 2016-09-07
	 */
	public void clearAccountConfigCacheByAccountId(String accountId);
}
