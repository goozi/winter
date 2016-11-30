
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScPoOrderEntity;
import com.qihang.buss.sc.sales.entity.TScPoOrderentryEntity;

import java.util.List;
import java.util.Set;

import com.qihang.buss.sc.sales.entity.TScPoStockbillentryEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

public interface TScPoOrderServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScPoOrderEntity tPoOrder,
	        List<TScPoOrderentryEntity> tPoOrderentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScPoOrderEntity tPoOrder,
	        List<TScPoOrderentryEntity> tPoOrderentryList);
	public void delMain (TScPoOrderEntity tPoOrder);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPoOrderEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPoOrderEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPoOrderEntity t);

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
	 * 选单明细数据
	 * @param id
	 * @return
	 */
	public List<TScPoOrderentryEntity> loadEntryList(String id,String tranType);

	/**
	 * 校验是否自动关闭
	 * @param mainIdList
	 */
	public void checkAutoFlag(Set<String> mainIdList);

	/**
	 * 反作废
	 * @param ids
	 */
	public AjaxJson enableBill(String ids);

	/**
	 * 审核后执行方法
	 * @param id
	 * @param audit
	 * @return
	 */
	public AjaxJson afterAudit(String id, Integer audit);
}
