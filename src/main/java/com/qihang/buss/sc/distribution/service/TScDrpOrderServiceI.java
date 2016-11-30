
package com.qihang.buss.sc.distribution.service;
import com.qihang.buss.sc.distribution.entity.TScDrpOrderEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpOrderentryEntity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScDrpOrderServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScDrpOrderEntity tScDrpOrder,
	        List<TScDrpOrderentryEntity> tScDrpOrderentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScDrpOrderEntity tScDrpOrder,
	        List<TScDrpOrderentryEntity> tScDrpOrderentryList);
	public void delMain (TScDrpOrderEntity tScDrpOrder);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpOrderEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpOrderEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpOrderEntity t);
	/**
	 * 关闭事件
	 * @param ids
	 * @return
	 */
	public AjaxJson closeBill(String ids);

	/**
	 * 反关闭功能
	 * @param ids
	 * @return
	 */
	public AjaxJson openBill(String ids);

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
}
