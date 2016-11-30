
package com.qihang.buss.sc.distribution.service;
import com.qihang.buss.sc.distribution.entity.TScDrpRstockbillEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpRstockbillentryEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScDrpRstockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScDrpRstockbillEntity tScDrpRstockbill,
	        List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScDrpRstockbillEntity tScDrpRstockbill,
	        List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryList);
	public void delMain (TScDrpRstockbillEntity tScDrpRstockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpRstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpRstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpRstockbillEntity t);
}
