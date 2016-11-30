
package com.qihang.buss.sc.distribution.service;

import com.qihang.buss.sc.distribution.entity.TScPromotionEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotionViewEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotiongiftsentryEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotiongoodsentryEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.util.List;

public interface TScPromotionServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScPromotionEntity tScPromotion,
	        List<TScPromotiongiftsentryEntity> tScPromotiongiveentryList,List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScPromotionEntity tScPromotion,
	        List<TScPromotiongiftsentryEntity> tScPromotiongiveentryList,List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryList);
	public void delMain (TScPromotionEntity tScPromotion);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPromotionEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPromotionEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPromotionEntity t);

	/**
	 * 根据商品ID、商品数量获得对应赠品记录集合
	 * @param goodsId 商品ID
	 * @param goodsQty 商品数量
	 * @return  商品对应赠品记录集合
	 */
	public List<TScPromotionViewEntity> getGiftsGoodsInfo(String goodsId,double goodsQty);

}
