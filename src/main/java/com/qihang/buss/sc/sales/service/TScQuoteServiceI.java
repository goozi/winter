
package com.qihang.buss.sc.sales.service;
import com.qihang.buss.sc.sales.entity.TScQuoteEntity;
import com.qihang.buss.sc.sales.entity.TScQuotecustomerEntity;
import com.qihang.buss.sc.sales.entity.TScQuoteitemsEntity;

import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScQuoteServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScQuoteEntity tScQuote,
	        List<TScQuotecustomerEntity> tScQuotecustomerList,List<TScQuoteitemsEntity> tScQuoteitemsList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScQuoteEntity tScQuote,
	        List<TScQuotecustomerEntity> tScQuotecustomerList,List<TScQuoteitemsEntity> tScQuoteitemsList);
	public void delMain (TScQuoteEntity tScQuote);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScQuoteEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScQuoteEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScQuoteEntity t);
}
