package com.qihang.buss.sc.sysaudit.service;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TSBillInfoServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TSBillInfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSBillInfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSBillInfoEntity t);

	/**
	 * 更新默认单价
	 * @param billInfoEntity
	 */
	public void updatePriceInfo(TSBillInfoEntity billInfoEntity);


}
