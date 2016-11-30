
package com.qihang.buss.sc.init.service;
import com.qihang.buss.sc.init.entity.TScIcInitialEntity;
import com.qihang.buss.sc.init.entity.TScIcInitialentryEntity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScIcInitialServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScIcInitialEntity tScIcInitial,
	        List<TScIcInitialentryEntity> tScIcInitialentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScIcInitialEntity tScIcInitial,
	        List<TScIcInitialentryEntity> tScIcInitialentryList);
	public void delMain (TScIcInitialEntity tScIcInitial);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcInitialEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcInitialEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcInitialEntity t);

	/**
	 * 作废功能
	 * @param ids
	 * @return
	 */
	public AjaxJson cancelBill(String ids);

	/**
	 * 反作废
	 * @param ids
	 */
	public AjaxJson enableBill(String ids);

	/**
	 * 系统开帐时，将存货已审核数据插入或(按itemId、stockId来判断)更新到即时库存表及即时库存批次表
	 * @return
     */
	public boolean doAddInventory();
}