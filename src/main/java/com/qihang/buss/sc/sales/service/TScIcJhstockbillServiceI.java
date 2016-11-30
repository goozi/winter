
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillEntity;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillentry1Entity;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillentry2Entity;

import java.util.List;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScIcJhstockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScIcJhstockbillEntity tScIcJhstockbill,
	        List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List,List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScIcJhstockbillEntity tScIcJhstockbill,
	        List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List,List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List);
	public void delMain (TScIcJhstockbillEntity tScIcJhstockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcJhstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcJhstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcJhstockbillEntity t);

	/**
	 * 作废功能
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

	/**
	 * 审核（反审核）后执行犯法
	 * @param id
	 * @param audit
	 * @return
	 */
	public AjaxJson afterAudit(String id, Integer audit,String sonId);
}
