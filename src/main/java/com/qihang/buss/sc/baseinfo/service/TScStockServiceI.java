package com.qihang.buss.sc.baseinfo.service;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScStockServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScStockEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScStockEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScStockEntity t);

	/**
	 *
	 *修改引用次数
	 */
	public boolean updateStockCount(TScCountEntity countEntity);
}
