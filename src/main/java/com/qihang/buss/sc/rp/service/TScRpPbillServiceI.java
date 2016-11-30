
package com.qihang.buss.sc.rp.service;
import com.qihang.buss.sc.rp.entity.TScRpPbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpPbillentry1Entity;
import com.qihang.buss.sc.rp.entity.TScRpPbillentry2Entity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScRpPbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScRpPbillEntity tScRpPbill,
	        List<TScRpPbillentry1Entity> tScRpPbillentry1List,List<TScRpPbillentry2Entity> tScRpPbillentry2List) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScRpPbillEntity tScRpPbill,
	        List<TScRpPbillentry1Entity> tScRpPbillentry1List,List<TScRpPbillentry2Entity> tScRpPbillentry2List);
	public void delMain (TScRpPbillEntity tScRpPbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpPbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpPbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpPbillEntity t);
}
