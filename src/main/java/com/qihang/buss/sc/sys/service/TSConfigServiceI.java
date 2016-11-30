package com.qihang.buss.sc.sys.service;
import com.qihang.buss.sc.sys.entity.TSConfigEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TSConfigServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);

 	public <T> void saveOrUpdate(T entity);

	public <T> void saveOrUpdateSupply(T entity);


 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TSConfigEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSConfigEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSConfigEntity t);
}
