package com.qihang.buss.sc.baseinfo.service;
import com.qihang.buss.sc.baseinfo.entity.TScLogisticalEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScLogisticalServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScLogisticalEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScLogisticalEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScLogisticalEntity t);

	/**
	 *
	 *修改引用次数
	 */
	public boolean updateLogisticalCount(TScCountEntity countEntity);
	/**
	 *
	 * 唯一性校验
	 *
	 */
	public AjaxJson checkName(String name, String load, String old_name);
}
