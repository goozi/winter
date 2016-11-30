
package com.qihang.buss.sc.baseinfo.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPhotoEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScMeasureunitToIcItemEntity;
import com.qihang.buss.sc.baseinfo.service.TScIcitemServiceI;
import com.qihang.buss.sc.baseinfo.service.TScMeasureunitServiceI;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("tScIcitemService")
@Transactional
public class TScIcitemServiceImpl extends CommonServiceImpl implements TScIcitemServiceI {

	@Autowired
	private TScMeasureunitServiceI tScMeasureunitService;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScIcitemEntity) entity);
 	}
	
	public void addMain(TScIcitemEntity tScIcitem,
	        List<TScItemPriceEntity> tScItemPriceList,List<TScItemPhotoEntity> tScItemPhotoList){
			//保存主信息
			this.save(tScIcitem);
		
			/**保存-单位价格表*/
			for(TScItemPriceEntity tScItemPrice:tScItemPriceList){
				//外键设置
				if(StringUtils.isNotEmpty(tScItemPrice.getUnitID())) {
					TScMeasureunitToIcItemEntity entity = this.get(TScMeasureunitToIcItemEntity.class, tScItemPrice.getUnitID());
					entity.setId(tScItemPrice.getUnitID());
					tScItemPrice.setMeasureunitToIcItemEntity(entity);
				}
				tScItemPrice.setPriceToIcItem(tScIcitem);
//				tScItemPrice.setItemID(tScIcitem.getId());
//                if(tScItemPrice.getDefaultCG() == null){
//                    tScItemPrice.setDefaultCG(0);
//                }
//                if(tScItemPrice.getDefaultXS() == null){
//                    tScItemPrice.setDefaultXS(0);
//                }
//                if(tScItemPrice.getDefaultCK() ==null){
//                    tScItemPrice.setDefaultCK(0);
//                }
//                if(tScItemPrice.getDefaultSC() == null){
//                    tScItemPrice.setDefaultSC(0);
//                }
				this.save(tScItemPrice);
			}
			/**保存-商品图片表*/
			for(TScItemPhotoEntity tScItemPhoto:tScItemPhotoList){
				//外键设置
				tScItemPhoto.setItemID(tScIcitem.getId());
				this.save(tScItemPhoto);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScIcitem);
	}

	
	public void updateMain(TScIcitemEntity tScIcitem,
	        List<TScItemPriceEntity> tScItemPriceList,List<TScItemPhotoEntity> tScItemPhotoList) {
		//保存主表信息
		this.saveOrUpdate(tScIcitem);
		//===================================================================================
		//获取参数
		Object id0 = tScIcitem.getId();
		Object id1 = tScIcitem.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-单位价格表
	    String hql0 = "from TScItemPriceEntity where 1 = 1 AND priceToIcItem.id  = ? ";
	    List<TScItemPriceEntity> tScItemPriceOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-单位价格表
		for(TScItemPriceEntity oldE:tScItemPriceOldList){
			boolean isUpdate = false;
				for(TScItemPriceEntity sendE:tScItemPriceList){
					 if(StringUtils.isNotEmpty(sendE.getUnitID())){
						 TScMeasureunitToIcItemEntity entity = this.get(TScMeasureunitToIcItemEntity.class, sendE.getUnitID());//持久化
						 entity.setId(sendE.getUnitID());
						 sendE.setMeasureunitToIcItemEntity(entity);
					 }
					//需要更新的明细数据-单位价格表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							//计数单位
							if(!oldE.getMeasureunitToIcItemEntity().getId().equals(sendE.getUnitID())) {
								TScCountEntity countEntity = new TScCountEntity();
								countEntity.setType(Globals.COUNT_UPDATE_TYPE);
								countEntity.setOldId(oldE.getMeasureunitToIcItemEntity().getId());//原先的单位
								countEntity.setNewId(sendE.getUnitID());//更改后的单位
								tScMeasureunitService.updateMeasureunitCount(countEntity);
							}
//                            if(sendE.getDefaultCG() == null){
//                                sendE.setDefaultCG(0);
//                            }
//                            if(sendE.getDefaultXS() == null){
//                                sendE.setDefaultXS(0);
//                            }
//                            if(sendE.getDefaultCK() ==null){
//                                sendE.setDefaultCK(0);
//                            }
//                            if(sendE.getDefaultSC() == null){
//                                sendE.setDefaultSC(0);
//                            }
//							MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
                            MyBeanUtils.copyBean2Bean(oldE,sendE);
							oldE.setPriceToIcItem(tScIcitem);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
                    //计数单位
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_DEL_TYPE);
					countEntity.setOldId(oldE.getMeasureunitToIcItemEntity().getId());//原先的单位
					 tScMeasureunitService.updateMeasureunitCount(countEntity);
					//如果数据库存在的明细，前台没有传递过来则是删除-单位价格表
					super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-单位价格表
			for(TScItemPriceEntity tScItemPrice:tScItemPriceList){
				if(oConvertUtils.isEmpty(tScItemPrice.getId())){
					//计数单位
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScItemPrice.getUnitID());//原先的单位
					tScMeasureunitService.updateMeasureunitCount(countEntity);

					if(StringUtils.isNotEmpty(tScItemPrice.getUnitID())){
						TScMeasureunitToIcItemEntity entity = this.get(TScMeasureunitToIcItemEntity.class, tScItemPrice.getUnitID());
						entity.setId(tScItemPrice.getUnitID());
						tScItemPrice.setMeasureunitToIcItemEntity(entity);
					}
					//外键设置
//					tScItemPrice.setItemID(tScIcitem.getId());
					tScItemPrice.setPriceToIcItem(tScIcitem);
					this.save(tScItemPrice);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-商品图片表
	    String hql1 = "from TScItemPhotoEntity where 1 = 1 AND iTEMID = ? ";
	    List<TScItemPhotoEntity> tScItemPhotoOldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-商品图片表
		for(TScItemPhotoEntity oldE:tScItemPhotoOldList){
			boolean isUpdate = false;
				for(TScItemPhotoEntity sendE:tScItemPhotoList){
					//需要更新的明细数据-商品图片表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-商品图片表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-商品图片表
			for(TScItemPhotoEntity tScItemPhoto:tScItemPhotoList){
				if(oConvertUtils.isEmpty(tScItemPhoto.getId())){
					//外键设置
					tScItemPhoto.setItemID(tScIcitem.getId());
					this.save(tScItemPhoto);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScIcitem);
	}

	
	public void delMain(TScIcitemEntity tScIcitem) {
		//删除主表信息
		this.delete(tScIcitem);
		//===================================================================================
		//获取参数
		Object id0 = tScIcitem.getId();
		Object id1 = tScIcitem.getId();
		//===================================================================================
		//删除-单位价格表
	    String hql0 = "from TScItemPriceEntity where 1 = 1 AND iTEMID = ? ";
	    List<TScItemPriceEntity> tScItemPriceOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScItemPriceOldList);
		//===================================================================================
		//删除-商品图片表
	    String hql1 = "from TScItemPhotoEntity where 1 = 1 AND iTEMID = ? ";
	    List<TScItemPhotoEntity> tScItemPhotoOldList = this.findHql(hql1,id1);
		this.deleteAllEntitie(tScItemPhotoOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcitemEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcitemEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcitemEntity t){
	 	return true;
 	}

	/**
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AjaxJson getItemInfoById(String id,String tranType,String stockId,String batchNo) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> attributes = new HashMap<String, Object>();
		TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class,id);
		//配置默认仓库
		if(null != icitemEntity.getStockForIcitemEntity()) {
			attributes.put("stockId", icitemEntity.getStockForIcitemEntity().getId());
			attributes.put("stockName",icitemEntity.getStockForIcitemEntity().getName());
		} else {
			attributes.put("stockId", "");
			attributes.put("stockName","");
		}
		List<TScItemPriceEntity> priceEntityList = this.findHql("from TScItemPriceEntity where itemID = ?",new Object[]{id});
		for(TScItemPriceEntity price : priceEntityList){
			if("51".equals(tranType)) {
				if (null != price.getDefaultCG() && 1 == price.getDefaultCG()) {
					attributes.put("unitId", price.getId());
					attributes.put("unitName", price.getMeasureunitToIcItemEntity()==null?"":price.getMeasureunitToIcItemEntity().getName());
					attributes.put("cofficient", price.getCoefficient());
					attributes.put("barCode", price.getBarCode());
					//采购限价
					attributes.put("cgLimitPrice",price.getCgLimitPrice());
					//销货限价
					attributes.put("xsLimitPrice",price.getXsLimitPrice());
				}
			}else if("52".equals(tranType) || "53".equals(tranType)){
				if (null != price.getDefaultCK() && 1 == price.getDefaultCK()) {
					attributes.put("unitId", price.getId());
					attributes.put("unitName", price.getMeasureunitToIcItemEntity()==null?"":price.getMeasureunitToIcItemEntity().getName());
					attributes.put("cofficient", price.getCoefficient());
					attributes.put("barCode", price.getBarCode());
					//限购单价
					attributes.put("cgLimitPrice",price.getCgLimitPrice());
					//销货限价
					attributes.put("xsLimitPrice",price.getXsLimitPrice());
				}
			}else if("1504".equals(tranType) || "353".equals(tranType)){
				if(null != price.getDefaultCK()&& 1 == price.getDefaultCK()){
					attributes.put("unitName", price.getMeasureunitToIcItemEntity()==null?"":price.getMeasureunitToIcItemEntity().getName());//单位名称
					attributes.put("barCode", price.getBarCode());//条形码
					attributes.put("unitID",price.getId());//单位
					attributes.put("basicUnitID", price.getId());//基本单位
					attributes.put("coefficient",price.getCoefficient());//换算率
				}
			}else if("103".equals(tranType) || "1506".equals(tranType)){
				if(null != price.getDefaultXS() && 1 == price.getDefaultXS()){
					attributes.put("unitId", price.getId());
					if(null != price.getMeasureunitToIcItemEntity()) {
						attributes.put("unitName", price.getMeasureunitToIcItemEntity()==null?"":price.getMeasureunitToIcItemEntity().getName());
					}
					attributes.put("cofficient", price.getCoefficient());
					attributes.put("barCode", price.getBarCode());
					//限购单价
					attributes.put("cgLimitPrice",price.getCgLimitPrice());
					//销货限价
					attributes.put("xsLimitPrice",price.getXsLimitPrice());
				}
			}
			//辅助单位
			if("0003".equals(price.getUnitType())){
				attributes.put("secUnitId",price.getId());
				attributes.put("secCoefficient",price.getCoefficient());
			}
			//基本单位
			else if("0001".equals(price.getUnitType())){
				attributes.put("basicUnitId",price.getId());
				attributes.put("basicCoefficient",price.getCoefficient());
			}

		}
		attributes.put("id",id);
		if(StringUtils.isNotEmpty(stockId)) {
			//获取库存基本信息；
			List<TScIcInventoryEntity> inventoryEntity = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?", new Object[]{id, stockId});
			//若存在库存数据，则判断是否存在批号数据，若不存在，则取库存数量为商品限量，若存在，则取该批号的库存数量作为商品限量
			if (inventoryEntity.size() > 0) {
				if(StringUtils.isEmpty(batchNo)) {
					attributes.put("invQty",inventoryEntity.get(0).getBasicQty());
				}else{
					List<TScIcInventoryBatchnoEntity> batchnoEntityList = this.findHql("from TScIcInventoryBatchnoEntity where itemId = ? and stockId=? and batchNo = ?", new Object[]{id, stockId, batchNo});
					if(batchnoEntityList.size() > 0){
						attributes.put("invQty", batchnoEntityList.get(0).getBasicQty());
					}else{
						attributes.put("invQty", inventoryEntity.get(0).getBasicQty());
					}
				}
			} else {
				attributes.put("invQty", 0);
			}
		}else{
			attributes.put("invQty", 0);
		}
		j.setAttributes(attributes);
		return j;
	}

	@Override
	public List<Map<String, Object>> loadItemUnit(String id) {
		List<TScItemPriceEntity> priceEntityList = this.findHql("from TScItemPriceEntity where itemID = ? and unitType <> '0003'",new Object[]{id});
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		for(TScItemPriceEntity entity : priceEntityList){
			Map<String,Object> param = new HashMap<String, Object>();
			Boolean isAdd = true;
			param.put("id", entity.getId());
			param.put("text", entity.getMeasureunitToIcItemEntity()==null?"":entity.getMeasureunitToIcItemEntity().getName());
			result.add(param);
		}
		return result;
	}

	/**
	 * 单位变换时获取相应的换算率和条码
	 * @param id
	 * @param unitId
	 * @return
	 */
	@Override
	public AjaxJson getChangeInfo(String id, String unitId,Integer rowIndex) {
		AjaxJson j = new AjaxJson();
		TScItemPriceEntity entity = this.getEntity(TScItemPriceEntity.class,unitId);
		Map<String,Object> attributes = new HashMap<String, Object>();
		if(null != entity){
			attributes.put("coefficient",entity.getCoefficient());
			attributes.put("barCode",entity.getBarCode());
			attributes.put("cgLimitPrice", entity.getCgLimitPrice());
			if(entity.getXsLimitPrice()==null){
				attributes.put("xsLimitPrice", 0);
			}else{
				attributes.put("xsLimitPrice",entity.getXsLimitPrice());
			}
			if(entity.getCgLatestPrice() == null){
				attributes.put("cgLatestPrice",0);
			}else{
				attributes.put("cgLatestPrice",entity.getCgLatestPrice());
			}
		}
		attributes.put("rowIndex",rowIndex);
		j.setAttributes(attributes);
		return j;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScIcitemEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
 		sql  = sql.replace("#{parentid}",String.valueOf(t.getParentID()));
 		sql  = sql.replace("#{shortname}",String.valueOf(t.getShortName()));
 		sql  = sql.replace("#{shortnumber}",String.valueOf(t.getShortNumber()));
 		sql  = sql.replace("#{model}",String.valueOf(t.getModel()));
 		sql  = sql.replace("#{itemtype}",String.valueOf(t.getItemType()));
 		sql  = sql.replace("#{track}",String.valueOf(t.getTrack()));
 		sql  = sql.replace("#{costprice}",String.valueOf(t.getCostPrice()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockID()));
 		sql  = sql.replace("#{supplierid}",String.valueOf(t.getSupplierID()));
 		sql  = sql.replace("#{iskfperiod}",String.valueOf(t.getIskfPeriod()));
 		sql  = sql.replace("#{kfperiod}",String.valueOf(t.getKfPeriod()));
 		sql  = sql.replace("#{kfdatetype}",String.valueOf(t.getKfDateType()));
 		sql  = sql.replace("#{batchmanager}",String.valueOf(t.getBatchManager()));
 		sql  = sql.replace("#{isassembly}",String.valueOf(t.getIsAssembly()));
 		sql  = sql.replace("#{factory}",String.valueOf(t.getFactory()));
 		sql  = sql.replace("#{producingplace}",String.valueOf(t.getProducingPlace()));
 		sql  = sql.replace("#{cultureno}",String.valueOf(t.getCultureNo()));
 		sql  = sql.replace("#{brandid}",String.valueOf(t.getBrandID()));
 		sql  = sql.replace("#{weight}",String.valueOf(t.getWeight()));
 		sql  = sql.replace("#{taxratein}",String.valueOf(t.getTaxRateIn()));
 		sql  = sql.replace("#{taxrateout}",String.valueOf(t.getTaxRateOut()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 操作单据的时候修改它的引用次数根据操作的方式
	 * @param countEntity
	 * @return
	 */
	@Override
	public boolean updateIcitemCount(TScCountEntity countEntity) {
		//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是供应商的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScIcitemEntity icitemEntity = this.get(TScIcitemEntity.class,countEntity.getOldId());
				if(icitemEntity == null){
					return false;
				}
				if(icitemEntity.getCount() == null|| icitemEntity.getCount() == 0){
					icitemEntity.setCount(1);
				}else{
					icitemEntity.setCount(icitemEntity.getCount() + 1);
				}
				super.saveOrUpdate(icitemEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScIcitemEntity icitemOldEntity = this.get(TScIcitemEntity.class,countEntity.getOldId());
				TScIcitemEntity  icitemNewEntity = this.get(TScIcitemEntity.class,countEntity.getNewId());
				if(icitemOldEntity == null ||icitemNewEntity == null){
					return false;
				}
				if(icitemOldEntity.getCount() == null || icitemOldEntity.getCount() == 0){
					icitemOldEntity.setCount(0);
				}else {
					icitemOldEntity.setCount(icitemOldEntity.getCount() - 1);
				}
				if(icitemNewEntity.getCount() == null || icitemNewEntity.getCount() == 0){
					icitemNewEntity.setCount(1);
				}else {
					icitemNewEntity.setCount(icitemNewEntity.getCount() + 1);
				}
				super.saveOrUpdate(icitemOldEntity);
				super.saveOrUpdate(icitemNewEntity);

			}else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScIcitemEntity icitemOldEntity = this.get(TScIcitemEntity.class,countEntity.getOldId());
				if(icitemOldEntity == null){
					return false;
				}
				if(icitemOldEntity.getCount() == null || icitemOldEntity.getCount() == 0){
					icitemOldEntity.setCount(0);
				}else{
					icitemOldEntity.setCount(icitemOldEntity.getCount() - 1);
				}
				super.saveOrUpdate(icitemOldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public AjaxJson getIcItemPriceInfoByIcitemId(String id) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> attributes = new HashMap<String, Object>();
		TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class, id);
		if(null != icitemEntity.getStockForIcitemEntity()){
			attributes.put("stockId",icitemEntity.getStockForIcitemEntity().getId());
			attributes.put("stockName",icitemEntity.getStockForIcitemEntity().getName());
		} else {
			attributes.put("stockId","");
			attributes.put("stockName","");
		}
		List<TScItemPriceEntity> priceEntityList = this.findHql("from TScItemPriceEntity where itemID = ?",new Object[]{id});
		for(TScItemPriceEntity price : priceEntityList){
			if(null != price.getDefaultXS() && 1 == price.getDefaultXS()){
				attributes.put("unitId", price.getId());//商品从表主键
				attributes.put("barCode",price.getBarCode());//商品从表的条形码
//				attributes.put("unieName",price.getMeasureunitToIcItemEntity().getName());
				attributes.put("cofficient",price.getCoefficient());//单位换算率
				if(price.getXsLimitPrice() == null){
					attributes.put("xsLimitPrice",0);
				}else{
					attributes.put("xsLimitPrice",price.getXsLimitPrice());
				}
			}
			if("0003".equals(price.getUnitType())){
				attributes.put("secUnitId",price.getId());
				attributes.put("secCoefficient",price.getCoefficient());
			}else if("0001".equals(price.getUnitType())){
				attributes.put("basicUnitId",price.getId());
			}

		}
//		attributes.put("id",id);
		j.setAttributes(attributes);
		return j;
	}

	@Override
	public AjaxJson getIcItemForQuoteItems(String id) {
		AjaxJson j = new AjaxJson();
		Map<String,Object> attributes = new HashMap<String, Object>();
		List<TScItemPriceEntity> priceEntityList = this.findHql("from TScItemPriceEntity where itemID = ?",new Object[]{id});
		for(TScItemPriceEntity price : priceEntityList){
			if(null != price.getDefaultXS() && 1 == price.getDefaultXS()){
				attributes.put("unitId", price.getId());//商品从表主键相当于单位id
				attributes.put("barCode",price.getBarCode());//商品从表的条形码
				attributes.put("coefficient",price.getCoefficient());//单位换算率
				attributes.put("cgLatestPrice",price.getCgLatestPrice());//最近采购价
			}
		}
		j.setAttributes(attributes);
		return j;
	}
}