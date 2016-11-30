
package com.qihang.buss.sc.distribution.service;
import com.qihang.buss.sc.distribution.entity.TScDrpStockbillEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpStockbillentryEntity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;
import java.util.Set;

public interface TScDrpStockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScDrpStockbillEntity tScDrpStockbill,
	        List<TScDrpStockbillentryEntity> tScDrpStockbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScDrpStockbillEntity tScDrpStockbill,
	        List<TScDrpStockbillentryEntity> tScDrpStockbillentryList);
	public void delMain (TScDrpStockbillEntity tScDrpStockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpStockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpStockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpStockbillEntity t);

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

	/**
	 * 审核（反审核）后执行
	 * @param id
	 * @param audit
	 * @return
	 */
	public AjaxJson afterAudit(String id, Integer audit);

}
