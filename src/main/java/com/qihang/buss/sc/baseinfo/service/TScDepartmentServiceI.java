package com.qihang.buss.sc.baseinfo.service;
import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScDepartmentServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDepartmentEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDepartmentEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDepartmentEntity t);

	/**
	 *
	 *修改引用次数
	 */
	public boolean updateDepartmentCount(TScCountEntity countEntity);

	/**
	 * 保存分支机构数据
	 * @param tScDepartment
	 * @param sonId
	 */
	public void saveSysDepartInfo(TScDepartmentEntity tScDepartment, String sonId);
}
