
package com.qihang.buss.sc.financing.service;
import com.qihang.buss.sc.financing.entity.TScRpExpensesapplyEntity;
import com.qihang.buss.sc.financing.entity.TScRpExpensesapplyentryEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScRpExpensesapplyServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScRpExpensesapplyEntity tScRpExpensesapply,
						List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScRpExpensesapplyEntity tScRpExpensesapply,
						   List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryList);
	public void delMain(TScRpExpensesapplyEntity tScRpExpensesapply);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpExpensesapplyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpExpensesapplyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpExpensesapplyEntity t);
}
