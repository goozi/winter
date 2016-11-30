
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sales.entity.TScOrderentryEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;
import java.util.Set;

public interface TScOrderServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScOrderEntity tScOrder,
	        List<TScOrderentryEntity> tScOrderentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScOrderEntity tScOrder,
	        List<TScOrderentryEntity> tScOrderentryList);
	public void delMain (TScOrderEntity tScOrder);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScOrderEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScOrderEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScOrderEntity t);

	/**
	 *按钮关闭的操作
	 * @param tScOrder
	 */
	void closeOrderBill(String id,String type);

	void updateCancelBill(String id, String type);

	/**
	 * 校验是否自动关闭
	 * @param mainId
	 */
	public void checkAutoFlag(Set<String> mainId);
}
