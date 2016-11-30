package com.qihang.buss.sc.baseinfo.service;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScOrganizationServiceI extends CommonService{
//
// 	public <T> void delete(T entity);
//
// 	public <T> Serializable save(T entity);
//
// 	public <T> void saveOrUpdate(T entity);
	/**
	 * 添加
	 * @param tScOrganization
     */
	public void addMain(TScOrganizationEntity tScOrganization);

	/**
	 * 更新数据
	 * @param tScOrganization
     */
	public void updateMain(TScOrganizationEntity tScOrganization);
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScOrganizationEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScOrganizationEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScOrganizationEntity t);

	/**
	 *
	 *修改引用次数
	 */
	public boolean updateOrganizationCount(TScCountEntity countEntity);
	/**
	 *
	 * 唯一性校验
	 *
	 */
	public AjaxJson checkName(String name, String load, String old_name);
}
