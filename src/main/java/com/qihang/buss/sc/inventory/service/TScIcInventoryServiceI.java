package com.qihang.buss.sc.inventory.service;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScIcInventoryServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcInventoryEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcInventoryEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcInventoryEntity t);

	/**
	 * 清理功能：清理没有任何业务数据但是有库存记录的数据
	 * @return
	 */
	public AjaxJson deleteInfo();

	/**
	 * 校验商品数量是否超出库存数量
	 * @param checkValue
	 * @return
	 */
	public AjaxJson checkIsOverInv(String checkValue);
}
