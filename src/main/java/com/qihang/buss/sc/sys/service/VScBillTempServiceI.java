package com.qihang.buss.sc.sys.service;
import com.qihang.buss.sc.sys.entity.VScBillTempEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface VScBillTempServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(VScBillTempEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(VScBillTempEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(VScBillTempEntity t);
}
