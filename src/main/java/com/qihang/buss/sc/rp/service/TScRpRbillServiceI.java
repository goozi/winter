
package com.qihang.buss.sc.rp.service;
import com.qihang.buss.sc.rp.entity.TScRpRbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpRbillentry1Entity;
import com.qihang.buss.sc.rp.entity.TScRpRbillentry2Entity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScRpRbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScRpRbillEntity tScRpRbill,
	        List<TScRpRbillentry1Entity> tScRpRbillentry1List,List<TScRpRbillentry2Entity> tScRpRbillentry2List) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScRpRbillEntity tScRpRbill,
	        List<TScRpRbillentry1Entity> tScRpRbillentry1List,List<TScRpRbillentry2Entity> tScRpRbillentry2List);
	public void delMain (TScRpRbillEntity tScRpRbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpRbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpRbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpRbillEntity t);
}
