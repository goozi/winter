package com.qihang.buss.sc.oa.service;

import com.qihang.buss.sc.oa.entity.TScPlanEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TScPlanServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScPlanEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScPlanEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScPlanEntity t);

	/**
	 * 获取字典数据
	 * @param dicName
	 * @return
	 */
	List<Map<String,Object>> loadComboInfo(String dicName);
}
