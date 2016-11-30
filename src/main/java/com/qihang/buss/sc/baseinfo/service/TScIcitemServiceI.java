
package com.qihang.buss.sc.baseinfo.service;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPhotoEntity;

import java.util.List;

import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;
import java.util.Map;

public interface TScIcitemServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScIcitemEntity tScIcitem,
	        List<TScItemPriceEntity> tScItemPriceList,List<TScItemPhotoEntity> tScItemPhotoList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScIcitemEntity tScIcitem,
	        List<TScItemPriceEntity> tScItemPriceList,List<TScItemPhotoEntity> tScItemPhotoList);
	public void delMain (TScIcitemEntity tScIcitem);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcitemEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcitemEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcitemEntity t);

	/**
	 * 根据id获取辅助信息
	 * @param id
	 * @return
	 */
	public AjaxJson getItemInfoById(String id,String tranType,String stockId,String batchNo);

	/**
	 * 获取商品单位下拉数据
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> loadItemUnit(String id);

	/**
	 * 单位变换时获取相应的换算率和条码
	 * @param id
	 * @param unitId
	 * @return
	 */
	public AjaxJson getChangeInfo(String id, String unitId,Integer rowIndex);

	/**
	 *
	 *修改引用次数
	 */
	public boolean updateIcitemCount(TScCountEntity countEntity);

	AjaxJson getIcItemPriceInfoByIcitemId(String id);

	AjaxJson getIcItemForQuoteItems(String id);
}
