package com.qihang.buss.sc.oa.service;

import com.qihang.buss.sc.oa.entity.TScNewsEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;
import java.util.Map;

public interface TScNewsServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScNewsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScNewsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScNewsEntity t);

	/**
	 * 新闻发布
	 * @param id
	 * @return
     */
	public Map<String, Object> release(String id);
}
