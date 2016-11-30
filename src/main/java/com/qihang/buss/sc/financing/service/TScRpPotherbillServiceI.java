
package com.qihang.buss.sc.financing.service;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillentryEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScRpPotherbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScRpPotherbillEntity tScRpPotherbill,
						List<TScRpPotherbillentryEntity> tScRpPotherbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScRpPotherbillEntity tScRpPotherbill,
						   List<TScRpPotherbillentryEntity> tScRpPotherbillentryList);
	public void delMain(TScRpPotherbillEntity tScRpPotherbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpPotherbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpPotherbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpPotherbillEntity t);
}
