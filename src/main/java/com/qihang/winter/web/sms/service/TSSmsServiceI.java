package com.qihang.winter.web.sms.service;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.sms.entity.TSSmsEntity;

import java.io.Serializable;

public interface TSSmsServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSSmsEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSSmsEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSSmsEntity t);
 	
 	public void send();
}
