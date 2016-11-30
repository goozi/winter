
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScIcXsstockbillEntity;
import com.qihang.buss.sc.sales.entity.TScIcXsstockbillentry1Entity;
import com.qihang.buss.sc.sales.entity.TScIcXsstockbillentry2Entity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

public interface TScIcXsstockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScIcXsstockbillEntity tScIcXsstockbill,
	        List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List,List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScIcXsstockbillEntity tScIcXsstockbill,
	        List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List,List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List);
	public void delMain (TScIcXsstockbillEntity tScIcXsstockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcXsstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcXsstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcXsstockbillEntity t);

	/**
	 * 审核后执行方法
	 * @param id
	 * @param audit
	 * @return
	 */
	AjaxJson afterAudit(String id, Integer audit,String sonId);

	/**
	 * 反作废功能
	 * @param ids
	 * @return
	 */
	AjaxJson enableBill(String ids);

	/**
	 * 作废功能
	 * @param ids
	 * @return
	 */
	AjaxJson cancelBill(String ids);
}
