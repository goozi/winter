
package com.qihang.buss.sc.inventory.service;
import com.qihang.buss.sc.inventory.entity.TScIcChkstockbillEntity;
import com.qihang.buss.sc.inventory.entity.TScIcChkstockbillentryEntity;

import java.text.ParseException;
import java.util.List;

import com.qihang.buss.sc.inventory.page.TScAutoChkPage;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScIcChkstockbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScIcChkstockbillEntity tScIcChkstockbill,
	        List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScIcChkstockbillEntity tScIcChkstockbill,
	        List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryList);
	public void delMain (TScIcChkstockbillEntity tScIcChkstockbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcChkstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcChkstockbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcChkstockbillEntity t);

	/**
	 * 自动盘点功能
	 * @param info
	 * @return
	 */
	public AjaxJson autoChk(TScAutoChkPage info,String billerId,String sonId) throws ParseException;

	public AjaxJson afterAudit(String id, Integer audit, String tranType,String sonId);
}
