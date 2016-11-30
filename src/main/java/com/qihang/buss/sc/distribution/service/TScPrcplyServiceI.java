
package com.qihang.buss.sc.distribution.service;

import com.qihang.buss.sc.distribution.entity.TScPrcplyEntity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyViewEntityEntity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyentry1Entity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyentry2Entity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

import java.util.List;

public interface TScPrcplyServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScPrcplyEntity tScPrcply,
	        List<TScPrcplyentry2Entity> tScPrcplyentry2List,List<TScPrcplyentry1Entity> tScPrcplyentry1List) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScPrcplyEntity tScPrcply,
	        List<TScPrcplyentry2Entity> tScPrcplyentry2List,List<TScPrcplyentry1Entity> tScPrcplyentry1List);
	public void delMain (TScPrcplyEntity tScPrcply);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPrcplyEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPrcplyEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPrcplyEntity t);

	/**
	 * 根据商品ID和客户ID获得最新的调价
	 * @param goodsId 商品ID
	 * @param custId 客户ID
	 * @return
	 */
	public List<TScPrcplyViewEntityEntity> getLatePrice(String goodsId,String custId,String unitId,double qty);

	/**
	 * 作废功能
	 * @param ids
	 * @return
	 */
	public AjaxJson cancelBill(String ids);

	/**
	 * 反作废功能
	 * @param ids
	 * @return
	 */
	public AjaxJson enableBill(String ids);
}
