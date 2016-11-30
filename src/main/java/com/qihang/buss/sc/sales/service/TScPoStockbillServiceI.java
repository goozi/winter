
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScPoStockbillEntity;
import com.qihang.buss.sc.sales.entity.TScPoStockbillentryEntity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScPoStockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScPoStockbillEntity tScPoStockbill,
	        List<TScPoStockbillentryEntity> tScPoStockbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScPoStockbillEntity tScPoStockbill,
	        List<TScPoStockbillentryEntity> tScPoStockbillentryList);
	public void delMain (TScPoStockbillEntity tScPoStockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPoStockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPoStockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPoStockbillEntity t);

	/**
	 * 作废操作
	 * @param ids
	 * @return
	 */
	public AjaxJson cancelBill(String ids);

	/**
	 * 反作废
	 * @param ids
	 * @return
	 */
	public AjaxJson enableBill(String ids);

	public List<TScPoStockbillentryEntity> loadEntryList(String id, String tranType);

	/**
	 * 审核后执行方法
	 * @param id
	 * @param audit
	 * @return
	 */
	public AjaxJson afterAudit(String id, Integer audit,String tranType,String sonId);
}
