package com.qihang.buss.sc.distribution.service;
import com.qihang.buss.sc.distribution.entity.TScDrpLogisticalEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScDrpLogisticalServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpLogisticalEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpLogisticalEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpLogisticalEntity t);
}
