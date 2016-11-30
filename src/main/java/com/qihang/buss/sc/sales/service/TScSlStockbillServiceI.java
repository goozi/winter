
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScSlStockbillEntity;
import com.qihang.buss.sc.sales.entity.TScSlStockbillentryEntity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScSlStockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScSlStockbillEntity tScSlStockbill,
	        List<TScSlStockbillentryEntity> tScSlStockbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScSlStockbillEntity tScSlStockbill,
	        List<TScSlStockbillentryEntity> tScSlStockbillentryList);
	public void delMain (TScSlStockbillEntity tScSlStockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScSlStockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScSlStockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScSlStockbillEntity t);

	/**
	 * 作废功能
	 * @param ids
	 * @return
	 */
	public AjaxJson cancelBill(String ids);

	/**
	 * 反作废功能
	 * @param ids
	 * @return
	 */
	public AjaxJson enableBill(String ids);

	/**
	 * 审核（反审核）后执行方法
	 * @param id
	 * @param audit
	 * @param tranType
	 * @return
	 */
	public AjaxJson afterAudit(String id, Integer audit, String tranType,String sonId);

}
