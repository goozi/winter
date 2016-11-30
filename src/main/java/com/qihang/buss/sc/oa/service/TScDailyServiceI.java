package com.qihang.buss.sc.oa.service;

import com.qihang.buss.sc.oa.entity.TScDailyEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TScDailyServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScDailyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScDailyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScDailyEntity t);

	/**
	 * 获取与当前登录用户相关的工作计划数据
	 * @param userName
	 * @return
	 */
	List<Map<String,Object>> loadPlanInfo(String userName, String userId);
}
