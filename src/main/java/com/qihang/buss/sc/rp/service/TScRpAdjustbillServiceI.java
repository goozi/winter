
package com.qihang.buss.sc.rp.service;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillentryEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScRpAdjustbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScRpAdjustbillEntity tScRpAdjustbill,
	        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScRpAdjustbillEntity tScRpAdjustbill,
	        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList);
	public void delMain (TScRpAdjustbillEntity tScRpAdjustbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpAdjustbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpAdjustbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpAdjustbillEntity t);
}
