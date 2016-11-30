package com.qihang.buss.sc.distribution.service;
import com.qihang.buss.sc.distribution.entity.TScAptitudeEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScAptitudeServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScAptitudeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScAptitudeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScAptitudeEntity t);

	/**
	 * 审核（反审核）后执行
	 * @param id
	 * @param audit
	 * @return
	 */
	public AjaxJson afterAudit(String id, String audit);
}
