
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScIcXsstockbillServiceI;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScAuditBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;

import java.util.UUID;


@Service("tScIcXsstockbillService")
@Transactional
public class TScIcXsstockbillServiceImpl extends CommonServiceImpl implements TScIcXsstockbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		//this.doDelSql((TScIcXsstockbillEntity)entity);
 	}
	
	public void addMain(TScIcXsstockbillEntity tScIcXsstockbill,
	        List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List,List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List){
			//保存主信息
			this.save(tScIcXsstockbill);
		
			/**保存-销售换货单退货表*/
			for(TScIcXsstockbillentry1Entity tScIcXsstockbillentry1:tScIcXsstockbillentry1List){
				//外键设置
				tScIcXsstockbillentry1.setFid(tScIcXsstockbill.getId());
				//成本单价，金额计算
				tScIcXsstockbillentry1.setCostPrice(tScIcXsstockbillentry1.getDiscountPrice());
				tScIcXsstockbillentry1.setCostAmount(tScIcXsstockbillentry1.getDiscountAmount());
				String entryId = tScIcXsstockbillentry1.getEntryIdSrc();
				if (StringUtils.isNotEmpty(entryId)) {
					String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+" + tScIcXsstockbillentry1.getQty() + " where id = '" + entryId + "'";
					updateBySqlString(updateSql);
				}
				this.save(tScIcXsstockbillentry1);
			}
			/**保存-销售换货单换货表*/
			for(TScIcXsstockbillentry2Entity tScIcXsstockbillentry2:tScIcXsstockbillentry2List){
				//外键设置
				tScIcXsstockbillentry2.setFid(tScIcXsstockbill.getId());
				tScIcXsstockbillentry2.setCostPrice(tScIcXsstockbillentry2.getDiscountPrice());
				tScIcXsstockbillentry2.setCostAmount(tScIcXsstockbillentry2.getDiscountAmount());
				this.save(tScIcXsstockbillentry2);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScIcXsstockbill);
	}

	
	public void updateMain(TScIcXsstockbillEntity tScIcXsstockbill,
	        List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List,List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List) {
		//保存主表信息
		this.saveOrUpdate(tScIcXsstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScIcXsstockbill.getId();
		Object id1 = tScIcXsstockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-销售换货单退货表
	    String hql0 = "from TScIcXsstockbillentry1Entity where 1 = 1 AND fID = ? ";
	    List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1OldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-销售换货单退货表
		for(TScIcXsstockbillentry1Entity oldE:tScIcXsstockbillentry1OldList){
			boolean isUpdate = false;
				for(TScIcXsstockbillentry1Entity sendE:tScIcXsstockbillentry1List){
					//需要更新的明细数据-销售换货单退货表
					if(oldE.getId().equals(sendE.getId())){
						if(StringUtils.isNotEmpty(oldE.getEntryIdSrc()) && oldE.getEntryIdSrc().equals(sendE.getEntryIdSrc())){
							BigDecimal oldQty = oldE.getQty();
							BigDecimal newQty = sendE.getQty();
							BigDecimal changeValue = oldQty.subtract(newQty);
							String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-("+changeValue+") where id = '"+oldE.getEntryIdSrc()+"'";
							this.updateBySqlString(updateSql);
						}
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							oldE.setCostPrice(oldE.getDiscountPrice());
							oldE.setCostAmount(oldE.getDiscountAmount());
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

					//如果数据库存在的明细，前台没有传递过来则是删除-退货信息表
					if(StringUtils.isNotEmpty(oldE.getEntryIdSrc())){
						String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-("+oldE.getQty()+") where id = '"+oldE.getEntryIdSrc()+"'";
						this.updateBySqlString(updateSql);
					}
		    		//如果数据库存在的明细，前台没有传递过来则是删除-销售换货单退货表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-销售换货单退货表
			for(TScIcXsstockbillentry1Entity tScIcXsstockbillentry1:tScIcXsstockbillentry1List){
				if(oConvertUtils.isEmpty(tScIcXsstockbillentry1.getId())){
					//外键设置
					tScIcXsstockbillentry1.setFid(tScIcXsstockbill.getId());
					tScIcXsstockbillentry1.setCostPrice(tScIcXsstockbillentry1.getDiscountPrice());
					tScIcXsstockbillentry1.setCostAmount(tScIcXsstockbillentry1.getDiscountAmount());
					this.save(tScIcXsstockbillentry1);
					//更新执行数量
					if(StringUtils.isNotEmpty(tScIcXsstockbillentry1.getEntryIdSrc())){
						String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+("+tScIcXsstockbillentry1.getQty()+") where id = '"+tScIcXsstockbillentry1.getEntryIdSrc()+"'";
						this.updateBySqlString(updateSql);
					}
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-销售换货单换货表
	    String hql1 = "from TScIcXsstockbillentry2Entity where 1 = 1 AND fID = ? ";
	    List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2OldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-销售换货单换货表
		for(TScIcXsstockbillentry2Entity oldE:tScIcXsstockbillentry2OldList){
			boolean isUpdate = false;
				for(TScIcXsstockbillentry2Entity sendE:tScIcXsstockbillentry2List){
					//需要更新的明细数据-销售换货单换货表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							oldE.setCostPrice(oldE.getDiscountPrice());
							oldE.setCostAmount(oldE.getDiscountAmount());
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-销售换货单换货表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-销售换货单换货表
			for(TScIcXsstockbillentry2Entity tScIcXsstockbillentry2:tScIcXsstockbillentry2List){
				if(oConvertUtils.isEmpty(tScIcXsstockbillentry2.getId())){
					//外键设置
					tScIcXsstockbillentry2.setFid(tScIcXsstockbill.getId());
					tScIcXsstockbillentry2.setCostPrice(tScIcXsstockbillentry2.getDiscountPrice());
					tScIcXsstockbillentry2.setCostAmount(tScIcXsstockbillentry2.getDiscountAmount());
					this.save(tScIcXsstockbillentry2);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScIcXsstockbill);
	}

	
	public void delMain(TScIcXsstockbillEntity tScIcXsstockbill) {
		//删除主表信息
		this.delete(tScIcXsstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScIcXsstockbill.getId();
		Object id1 = tScIcXsstockbill.getId();
		//===================================================================================
		//删除-销售换货单退货表
	    String hql0 = "from TScIcXsstockbillentry1Entity where 1 = 1 AND fID = ? ";
	    List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1OldList = this.findHql(hql0,id0);
		//修改订单已执行量
		for(TScIcXsstockbillentry1Entity entity : tScIcXsstockbillentry1OldList){
			if(StringUtils.isNotEmpty(entity.getEntryIdSrc())) {
				String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-(" + entity.getQty() + ") where id = '" + entity.getEntryIdSrc() + "'";
				this.updateBySqlString(updateSql);
			}
		}
		this.deleteAllEntitie(tScIcXsstockbillentry1OldList);
		//===================================================================================
		//删除-销售换货单换货表
	    String hql1 = "from TScIcXsstockbillentry2Entity where 1 = 1 AND fID = ? ";
	    List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2OldList = this.findHql(hql1,id1);
		this.deleteAllEntitie(tScIcXsstockbillentry2OldList);
		//删除物流信息
		TScSlLogisticalEntity logisticalEntity = this.findUniqueByProperty(TScSlLogisticalEntity.class,"fid",tScIcXsstockbill.getId());
		if(null != logisticalEntity) {
			this.delete(logisticalEntity);
		}
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcXsstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcXsstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcXsstockbillEntity t){
	 	return true;
 	}

	@Override
	public AjaxJson afterAudit(String id, Integer audit,String sonId) {
		AjaxJson j = new AjaxJson();
		TScIcXsstockbillEntity entity = this.getEntity(TScIcXsstockbillEntity.class, id);
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where tranType = ? and billId=? order by orderNum desc", new Object[]{entity.getTranType(), id});
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?", new Object[]{sonId, Globals.SC_IC_XSSTOCKBILL_TRANTYPE.toString(), id});
		try {
			if(auditBillInfoEntityList.size() > 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				if (((auditBillInfoEntity.getOldState() == 1 && auditBillInfoEntity.getNewState() == 2) || (auditBillInfoEntity.getOldState() == 2 && auditBillInfoEntity.getNewState() == 1)) && relationEntityList.get(0).getStatus() >= 0) {
					//退货明细
					List<TScIcXsstockbillentry1Entity> entryList = findHql("from TScIcXsstockbillentry1Entity where fid = ?", new Object[]{id});
					DecimalFormat def = new DecimalFormat("#.00");
					for (TScIcXsstockbillentry1Entity entry : entryList) {
						String itemId = entry.getItemId();
						List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ?", new Object[]{itemId});
						BigDecimal ckCoefficient = BigDecimal.ZERO;
						BigDecimal basicCoefficient = BigDecimal.ZERO;
						for (TScItemPriceEntity unit : unitList) {
							if (1 == (unit.getDefaultCK() == null ? 0 : unit.getDefaultCK())) {
								ckCoefficient = unit.getCoefficient();
							}
							if ("0001".equals(unit.getUnitType())) {
								basicCoefficient = unit.getCoefficient();
							}
						}
						BigDecimal basicQty = entry.getBasicQty();//基本数量
						BigDecimal secQty = entry.getSecQty();//辅助数量
						BigDecimal costPrice = entry.getDiscountPrice().divide(entry.getCoefficient(), 2, BigDecimal.ROUND_HALF_EVEN);//成本单价
						BigDecimal costAmount = entry.getDiscountAmount();

						TScIcInventoryEntity inventoryEntity = null;
						List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?", new Object[]{itemId, entry.getStockId()});
						if (inventoryEntities.size() > 0) {
							inventoryEntity = inventoryEntities.get(0);
						}
						String hql = "from TScIcInventoryBatchnoEntity where itemId = ? and stockId = ?";
						Object[] queryInfo = new Object[]{itemId, entry.getStockId()};
						if (StringUtil.isNotEmpty(entry.getBatchNo())) {
							hql += " and batchNo = ?";
							queryInfo = new Object[]{itemId, entry.getStockId(),entry.getBatchNo()};
						}
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql(hql, queryInfo);
						if (null != inventoryEntity) {
							//若为审核操作
							if (1 == audit) {
								if (StringUtils.isNotEmpty(entry.getStockId())) {
									inventoryEntity.setStockId(entry.getStockId());
								}
								//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = entry.getBasicQty();
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).add(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
								}
								inventoryEntity.setCostAmount(BigDecimal.valueOf(inventoryEntity.getCostAmount()).add(costAmount).doubleValue());
								//计算平均单价
								Double amount = inventoryEntity.getCostAmount();
								Double basicQ = inventoryEntity.getBasicQty();
								if (!basicQ.equals(0.0)) {
									Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
									inventoryEntity.setCostPrice(avgPrice);
								} else {
									inventoryEntity.setCostPrice(0.0);
								}

								inventoryEntity.setCount(inventoryEntity.getCount() + 1);
								this.saveOrUpdate(inventoryEntity);
								//若存在本商品批号数据 增加库存 否则新增批号库存数据
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setIsCheck(0);
									//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() + basicQty);
									inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).add(secQty).doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = entry.getBasicQty();
										inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).add(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() - costPrice);
									inventoryBatchnoEntity.setCostAmount(BigDecimal.valueOf(inventoryBatchnoEntity.getCostAmount()).add(costAmount).doubleValue());
									//计算平均单价
									amount = inventoryBatchnoEntity.getCostAmount();
									basicQ = inventoryBatchnoEntity.getBasicQty();
									if (!basicQ.equals(0.0)) {
										Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
										inventoryBatchnoEntity.setCostPrice(avgPrice);
									} else {
										inventoryBatchnoEntity.setCostPrice(0.0);
									}
									inventoryBatchnoEntity.setCount(inventoryBatchnoEntity.getCount() + 1);
									if (null != entry.getKfDate()) {
										inventoryBatchnoEntity.setKfDate(entry.getKfDate());
									}
									if (null != entry.getKfPeriod()) {
										inventoryBatchnoEntity.setKfPeriod(entry.getKfPeriod());
									}
									if (StringUtils.isNotEmpty(entry.getKfDateType())) {
										inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
									}
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									this.saveOrUpdate(inventoryBatchnoEntity);
								} else {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
									inventoryBatchnoEntity.setIsCheck(0);
									inventoryBatchnoEntity.setCostAmount(costAmount.doubleValue());
									inventoryBatchnoEntity.setCostPrice(costPrice.doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = entry.getBasicQty();
										inventoryBatchnoEntity.setBasicQty(qty.doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setSmallQty(smallQty);
//										inventoryBatchnoEntity.setQty(xQty);
									}

									//inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
									inventoryBatchnoEntity.setSecQty(Double.parseDouble(entry.getSecQty().toString()));
									if (StringUtils.isNotEmpty(entry.getStockId())) {
										inventoryBatchnoEntity.setStockId(entry.getStockId());
									}
									inventoryBatchnoEntity.setItemId(entry.getItemId());
									inventoryBatchnoEntity.setBatchNo(entry.getBatchNo());
									if (null != entry.getKfDate()) {
										inventoryBatchnoEntity.setKfDate(entry.getKfDate());
									}
									if (null != entry.getKfPeriod()) {
										inventoryBatchnoEntity.setKfPeriod(entry.getKfPeriod());
									}
									if (StringUtils.isNotEmpty(entry.getKfDateType())) {
										inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
									}
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									inventoryBatchnoEntity.setCount(1);
									this.save(inventoryBatchnoEntity);
								}
								//存货日结记录
								TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
								speedbalEntity.setTranType("110");//单据类型
								speedbalEntity.setDate(entity.getDate());//单据日期
								speedbalEntity.setBillId(id);//单据主键
								speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
								speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
								speedbalEntity.setItemId(entry.getItemId());//商品id
								speedbalEntity.setStockId(entry.getStockId());//仓库id
								speedbalEntity.setBatchNo(entry.getBatchNo());//批号
								speedbalEntity.setQty(entry.getBasicQty().doubleValue());//数量
								speedbalEntity.setSecQty(entry.getSecQty().doubleValue());//辅助数量
								BigDecimal price = entry.getDiscountPrice().divide(entry.getCoefficient(), 2, BigDecimal.ROUND_HALF_EVEN);
								speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
								speedbalEntity.setAmount(entry.getDiscountAmount().doubleValue());//成本金额
								speedbalEntity.setEPrice(0.0);//结存单价
								speedbalEntity.setEAmount(0.0);//结存金额
								//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
								speedbalEntity.setDiffAmount(0.0);//差异金额
								speedbalEntity.setBlidSrc(entity.getClassIDSrc());//源单类型
								speedbalEntity.setFlag(1);//出入库标记
								speedbalEntity.setStatus(1);//计算状态
								speedbalEntity.setNegFlag(0);//负结余处理状态
								this.save(speedbalEntity);
							}
							//反审核
							else {
								if (StringUtils.isNotEmpty(entry.getStockId())) {
									inventoryEntity.setStockId(entry.getStockId());
								}
								//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + basicQty);
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = entry.getBasicQty();
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).subtract(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
								}
								//inventoryEntity.setCostPrice(inventoryEntity.getCostPrice() + costPrice);
								inventoryEntity.setCostAmount(BigDecimal.valueOf(inventoryEntity.getCostAmount()).subtract(costAmount).doubleValue());
								//计算平均单价
								Double amount = inventoryEntity.getCostAmount();
								Double basicQ = inventoryEntity.getBasicQty();
								if (!basicQ.equals(0.0)) {
									Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
									inventoryEntity.setCostPrice(avgPrice);
								} else {
									inventoryEntity.setCostPrice(0.0);
								}

								inventoryEntity.setCount(inventoryEntity.getCount() - 1);
								this.saveOrUpdate(inventoryEntity);
								//若存在本商品批号数据 增加库存 否则新增批号库存数据
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setIsCheck(0);
									//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() + basicQty);
									inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).subtract(secQty).doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = entry.getBasicQty();
										inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).subtract(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() + costPrice);
									inventoryBatchnoEntity.setCostAmount(BigDecimal.valueOf(inventoryBatchnoEntity.getCostAmount()).subtract(costAmount).doubleValue());
									//计算平均单价
									amount = inventoryBatchnoEntity.getCostAmount();
									basicQ = inventoryBatchnoEntity.getBasicQty();
									if (!basicQ.equals(0.0)) {
										Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
										inventoryBatchnoEntity.setCostPrice(avgPrice);
									} else {
										inventoryBatchnoEntity.setCostPrice(0.0);
									}

									inventoryBatchnoEntity.setCount(inventoryBatchnoEntity.getCount() - 1);

									if (null != entry.getKfDate()) {
										inventoryBatchnoEntity.setKfDate(entry.getKfDate());
									}
									if (null != entry.getKfPeriod()) {
										inventoryBatchnoEntity.setKfPeriod(entry.getKfPeriod());
									}
									if (StringUtils.isNotEmpty(entry.getKfDateType())) {
										inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
									}
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									this.saveOrUpdate(inventoryBatchnoEntity);
								}
								//反审核，删除存货日记记录
								List<TScIcSpeedbalEntity> speedbalEntityList = this.findHql("from TScIcSpeedbalEntity where tranType = ? and billId = ? and billEntryId = ? ", new Object[]{"110", id, entry.getId()});
								if (speedbalEntityList.size() > 0) {
									for (TScIcSpeedbalEntity speedbalEntity : speedbalEntityList) {
										super.delete(speedbalEntity);
									}
								}
							}
						} else {
							TScIcInventoryEntity newInventory = new TScIcInventoryEntity();
							newInventory.setItemId(itemId);
							if (StringUtils.isNotEmpty(entry.getStockId())) {
								newInventory.setStockId(entry.getStockId());
							}
							//newInventory.setBasicQty(entry.getBasicQty());
							newInventory.setSecQty(secQty.doubleValue());
							if (BigDecimal.ZERO != ckCoefficient) {
								BigDecimal qty = entry.getBasicQty();
								newInventory.setBasicQty(qty.doubleValue());
//								qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//								Double xQty = Math.floor(qty.doubleValue());
//								Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//								newInventory.setQty(xQty);
//								newInventory.setSmallQty(smallQty);
							}
							newInventory.setCostPrice(costPrice.doubleValue());
							newInventory.setCostAmount(costAmount.doubleValue());
							newInventory.setCount(1);
							this.save(newInventory);
							//if (StringUtils.isNotEmpty(entry.getBatchNo())) {
							TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
							inventoryBatchnoEntity.setIsCheck(0);
							inventoryBatchnoEntity.setCostAmount(newInventory.getCostAmount());
							inventoryBatchnoEntity.setCostPrice(newInventory.getCostPrice());
							inventoryBatchnoEntity.setSmallQty(newInventory.getSmallQty());
							inventoryBatchnoEntity.setQty(newInventory.getQty());
							inventoryBatchnoEntity.setBasicQty(newInventory.getBasicQty());
							inventoryBatchnoEntity.setSecQty(newInventory.getSecQty());
							if (StringUtils.isNotEmpty(newInventory.getStockId())) {
								inventoryBatchnoEntity.setStockId(newInventory.getStockId());
							}
							inventoryBatchnoEntity.setItemId(newInventory.getItemId());
							inventoryBatchnoEntity.setBatchNo(entry.getBatchNo());
							if (null != entry.getKfDate()) {
								inventoryBatchnoEntity.setKfDate(entry.getKfDate());
							}
							if (null != entry.getKfPeriod()) {
								inventoryBatchnoEntity.setKfPeriod(entry.getKfPeriod());
							}
							if (StringUtils.isNotEmpty(entry.getKfDateType())) {
								inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
							}
							if (null != entry.getPeriodDate()) {
								inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
							}
							inventoryBatchnoEntity.setCount(1);
							this.save(inventoryBatchnoEntity);
							//}
							//存货日结记录
							TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
							speedbalEntity.setTranType("110");//单据类型
							speedbalEntity.setDate(entity.getDate());//单据日期
							speedbalEntity.setBillId(id);//单据主键
							speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
							speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
							speedbalEntity.setItemId(entry.getItemId());//商品id
							speedbalEntity.setStockId(entry.getStockId());//仓库id
							speedbalEntity.setBatchNo(entry.getBatchNo());//批号
							speedbalEntity.setQty(entry.getBasicQty().doubleValue());//数量
							speedbalEntity.setSecQty(entry.getSecQty().doubleValue());//辅助数量
							BigDecimal price = entry.getDiscountPrice().divide(entry.getCoefficient(), 2, BigDecimal.ROUND_HALF_EVEN);
							speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
							speedbalEntity.setAmount(entry.getDiscountAmount().doubleValue());//成本金额
							speedbalEntity.setEPrice(0.0);//结存单价
							speedbalEntity.setEAmount(0.0);//结存金额
							//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
							speedbalEntity.setDiffAmount(0.0);//差异金额
							speedbalEntity.setBlidSrc(entity.getClassIDSrc());//源单类型
							speedbalEntity.setFlag(1);//出入库标记
							speedbalEntity.setStatus(1);//计算状态
							speedbalEntity.setNegFlag(0);//负结余处理状态
							this.save(speedbalEntity);
						}
					}
					//换货明细
					List<TScIcXsstockbillentry2Entity> entryList2 = findHql("from TScIcXsstockbillentry2Entity where fid = ?", new Object[]{id});
					for (TScIcXsstockbillentry2Entity entry : entryList2) {
						String itemId = entry.getItemId();
						List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ?", new Object[]{itemId});
						BigDecimal ckCoefficient = BigDecimal.ZERO;
						BigDecimal basicCoefficient = BigDecimal.ZERO;
						for (TScItemPriceEntity unit : unitList) {
							if (1 == (unit.getDefaultCK() == null ? 0 : unit.getDefaultCK())) {
								ckCoefficient = unit.getCoefficient();
							}
							if ("0001".equals(unit.getUnitType())) {
								basicCoefficient = unit.getCoefficient();
							}
						}

						BigDecimal basicQty = entry.getBasicQty();//基本数量
						BigDecimal secQty = entry.getSecQty();//辅助数量
						BigDecimal costPrice = entry.getDiscountPrice().divide(entry.getCoefficient(), 2, BigDecimal.ROUND_HALF_EVEN);//成本单价
						BigDecimal costAmount = entry.getDiscountAmount();

						TScIcInventoryEntity inventoryEntity = null;
						List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?", new Object[]{itemId, entry.getStockId()});
						if (inventoryEntities.size() > 0) {
							inventoryEntity = inventoryEntities.get(0);
						}
						String hql = "from TScIcInventoryBatchnoEntity where itemId = ? and stockId = ?";
						Object[] queryInfo = new Object[]{itemId, entry.getStockId()};
						if (StringUtil.isNotEmpty(entry.getBatchNo())) {
							hql += " and batchNo = ?";
							queryInfo = new Object[]{itemId, entry.getStockId(),entry.getBatchNo()};
						}
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql(hql, queryInfo);
						if (null != inventoryEntity) {
							//若为审核操作
							if (1 == audit) {
								if (StringUtils.isNotEmpty(entry.getStockId())) {
									inventoryEntity.setStockId(entry.getStockId());
								}
								//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + basicQty);
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());

								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = entry.getBasicQty();
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).subtract(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
								}
								//inventoryEntity.setCostPrice(inventoryEntity.getCostPrice() + costPrice);
								inventoryEntity.setCostAmount(BigDecimal.valueOf(inventoryEntity.getCostAmount()).subtract(costAmount).doubleValue());
								//计算平均单价
								Double amount = inventoryEntity.getCostAmount();
								Double basicQ = inventoryEntity.getBasicQty();
								if (!basicQ.equals(0.0)) {
									Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
									inventoryEntity.setCostPrice(avgPrice);
								} else {
									inventoryEntity.setCostPrice(0.0);
								}
								inventoryEntity.setCount(inventoryEntity.getCount() + 1);
								this.saveOrUpdate(inventoryEntity);
								//若存在本商品批号数据 增加库存 否则新增批号库存数据
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setIsCheck(0);
									//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() + basicQty);
									inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).subtract(secQty).doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = entry.getBasicQty();
										inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).subtract(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() + costPrice);
									inventoryBatchnoEntity.setCostAmount(BigDecimal.valueOf(inventoryBatchnoEntity.getCostAmount()).subtract(costAmount).doubleValue());
									//计算平均单价
									amount = inventoryBatchnoEntity.getCostAmount();
									basicQ = inventoryBatchnoEntity.getBasicQty();
									if (!basicQ.equals(0.0)) {
										Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
										inventoryBatchnoEntity.setCostPrice(avgPrice);
									} else {
										inventoryBatchnoEntity.setCostPrice(0.0);
									}

									inventoryBatchnoEntity.setCount(inventoryBatchnoEntity.getCount() + 1);
									if (null != entry.getKfDate()) {
										inventoryBatchnoEntity.setKfDate(entry.getKfDate());
									}
									if (null != entry.getKfPeriod()) {
										inventoryBatchnoEntity.setKfPeriod(Integer.parseInt(entry.getKfPeriod().toString()));
									}
									if (StringUtils.isNotEmpty(entry.getKfDateType())) {
										inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
									}
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									this.saveOrUpdate(inventoryBatchnoEntity);
								} else {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
									inventoryBatchnoEntity.setIsCheck(0);
									inventoryBatchnoEntity.setCostAmount(costAmount.doubleValue());
									inventoryBatchnoEntity.setCostPrice(costPrice.doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = entry.getBasicQty();
										inventoryBatchnoEntity.setBasicQty(qty.doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setSmallQty(smallQty);
//										inventoryBatchnoEntity.setQty(xQty);
									}

									//inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
									inventoryBatchnoEntity.setSecQty(secQty.doubleValue());
									if (StringUtils.isNotEmpty(entry.getStockId())) {
										inventoryBatchnoEntity.setStockId(entry.getStockId());
									}
									inventoryBatchnoEntity.setItemId(entry.getItemId());
									inventoryBatchnoEntity.setBatchNo(entry.getBatchNo());
									if (null != entry.getKfDate()) {
										inventoryBatchnoEntity.setKfDate(entry.getKfDate());
									}
									if (null != entry.getKfPeriod()) {
										inventoryBatchnoEntity.setKfPeriod(Integer.parseInt(entry.getKfPeriod().toString()));
									}
									if (StringUtils.isNotEmpty(entry.getKfDateType())) {
										inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
									}
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									inventoryBatchnoEntity.setCount(1);
									this.save(inventoryBatchnoEntity);
								}
								//存货日结记录
								TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
								speedbalEntity.setTranType("110");//单据类型
								speedbalEntity.setDate(entity.getDate());//单据日期
								speedbalEntity.setBillId(id);//单据主键
								speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
								speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
								speedbalEntity.setItemId(entry.getItemId());//商品id
								speedbalEntity.setStockId(entry.getStockId());//仓库id
								speedbalEntity.setBatchNo(entry.getBatchNo());//批号
								speedbalEntity.setQty(entry.getBasicQty().doubleValue());//数量
								speedbalEntity.setSecQty(entry.getSecQty().doubleValue());//辅助数量
								BigDecimal price = entry.getDiscountPrice().divide(entry.getCoefficient(), 2, BigDecimal.ROUND_HALF_EVEN);
								speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
								speedbalEntity.setAmount(entry.getDiscountAmount().doubleValue());//成本金额
								speedbalEntity.setEPrice(0.0);//结存单价
								speedbalEntity.setEAmount(0.0);//结存金额
								//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
								speedbalEntity.setDiffAmount(0.0);//差异金额
								speedbalEntity.setBlidSrc(entity.getClassIDSrc());//源单类型
								speedbalEntity.setFlag(-1);//出入库标记
								speedbalEntity.setStatus(1);//计算状态
								speedbalEntity.setNegFlag(0);//负结余处理状态
								this.save(speedbalEntity);
							}
							//反审核
							else {
								if (StringUtils.isNotEmpty(entry.getStockId())) {
									inventoryEntity.setStockId(entry.getStockId());
								}
								//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = entry.getBasicQty();
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).add(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
								}
								//inventoryEntity.setCostPrice(inventoryEntity.getCostPrice() - costPrice);
								inventoryEntity.setCostAmount(BigDecimal.valueOf(inventoryEntity.getCostAmount()).add(costAmount).doubleValue());
								//计算平均单价
								Double amount = inventoryEntity.getCostAmount();
								Double basicQ = inventoryEntity.getBasicQty();
								if (!basicQ.equals(0.0)) {
									Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
									inventoryEntity.setCostPrice(avgPrice);
								} else {
									inventoryEntity.setCostPrice(0.0);
								}

								inventoryEntity.setCount(inventoryEntity.getCount() - 1);
								this.saveOrUpdate(inventoryEntity);
								//若存在本商品批号数据 增加库存 否则新增批号库存数据
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setIsCheck(0);
									//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - basicQty);
									inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).add(secQty).doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = entry.getBasicQty();
										inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).add(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() - costPrice);
									inventoryBatchnoEntity.setCostAmount(BigDecimal.valueOf(inventoryBatchnoEntity.getCostAmount()).add(costAmount).doubleValue());
									//计算平均单价
									amount = inventoryBatchnoEntity.getCostAmount();
									basicQ = inventoryBatchnoEntity.getBasicQty();
									if (!basicQ.equals(0.0)) {
										Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
										inventoryBatchnoEntity.setCostPrice(avgPrice);
									} else {
										inventoryBatchnoEntity.setCostPrice(0.0);
									}

									inventoryBatchnoEntity.setCount(inventoryBatchnoEntity.getCount() - 1);
									if (null != entry.getKfDate()) {
										inventoryBatchnoEntity.setKfDate(entry.getKfDate());
									}
									if (null != entry.getKfPeriod()) {
										inventoryBatchnoEntity.setKfPeriod(Integer.parseInt(entry.getKfPeriod().toString()));
									}
									if (StringUtils.isNotEmpty(entry.getKfDateType())) {
										inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
									}
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									this.saveOrUpdate(inventoryBatchnoEntity);
								}
								//反审核，删除存货日记记录
								List<TScIcSpeedbalEntity> speedbalEntityList = this.findHql("from TScIcSpeedbalEntity where tranType = ? and billId = ? and billEntryId = ? ", new Object[]{"110", id, entry.getId()});
								if (speedbalEntityList.size() > 0) {
									for (TScIcSpeedbalEntity speedbalEntity : speedbalEntityList) {
										super.delete(speedbalEntity);
									}
								}
							}
						} else {
							TScIcInventoryEntity newInventory = new TScIcInventoryEntity();
							newInventory.setItemId(itemId);
							if (StringUtils.isNotEmpty(entry.getStockId())) {
								newInventory.setStockId(entry.getStockId());
							}
							//newInventory.setBasicQty(entry.getBasicQty());
							newInventory.setSecQty(secQty.doubleValue());
							if (BigDecimal.ZERO != ckCoefficient) {
								BigDecimal qty = entry.getBasicQty();
								newInventory.setBasicQty(qty.doubleValue());
//								qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//								Double xQty = Math.floor(qty.doubleValue());
//								Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//								newInventory.setQty(xQty);
//								newInventory.setSmallQty(smallQty);
							}
							newInventory.setCostPrice(costPrice.doubleValue());
							newInventory.setCostAmount(costAmount.doubleValue());
							newInventory.setCount(1);
							this.save(newInventory);
							//if (StringUtils.isNotEmpty(entry.getBatchNo())) {
							TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
							inventoryBatchnoEntity.setIsCheck(0);
							inventoryBatchnoEntity.setCostAmount(newInventory.getCostAmount());
							inventoryBatchnoEntity.setCostPrice(newInventory.getCostPrice());
							inventoryBatchnoEntity.setSmallQty(newInventory.getSmallQty());
							inventoryBatchnoEntity.setQty(newInventory.getQty());
							inventoryBatchnoEntity.setBasicQty(newInventory.getBasicQty());
							inventoryBatchnoEntity.setSecQty(newInventory.getSecQty());
							if (StringUtils.isNotEmpty(newInventory.getStockId())) {
								inventoryBatchnoEntity.setStockId(newInventory.getStockId());
							}
							inventoryBatchnoEntity.setItemId(newInventory.getItemId());
							inventoryBatchnoEntity.setBatchNo(entry.getBatchNo());
							if (null != entry.getKfDate()) {
								inventoryBatchnoEntity.setKfDate(entry.getKfDate());
							}
							if (null != entry.getKfPeriod()) {
								inventoryBatchnoEntity.setKfPeriod(Integer.parseInt(entry.getKfPeriod().toString()));
							}
							if (StringUtils.isNotEmpty(entry.getKfDateType())) {
								inventoryBatchnoEntity.setKfDateType(entry.getKfDateType());
							}
							if (null != entry.getPeriodDate()) {
								inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
							}
							inventoryBatchnoEntity.setCount(1);
							this.save(inventoryBatchnoEntity);
							//}
							//存货日结记录
							TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
							speedbalEntity.setTranType("110");//单据类型
							speedbalEntity.setDate(entity.getDate());//单据日期
							speedbalEntity.setBillId(id);//单据主键
							speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
							speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
							speedbalEntity.setItemId(entry.getItemId());//商品id
							speedbalEntity.setStockId(entry.getStockId());//仓库id
							speedbalEntity.setBatchNo(entry.getBatchNo());//批号
							speedbalEntity.setQty(entry.getBasicQty().doubleValue());//数量
							speedbalEntity.setSecQty(entry.getSecQty().doubleValue());//辅助数量
							BigDecimal price = entry.getDiscountPrice().divide(entry.getCoefficient(), 2, BigDecimal.ROUND_HALF_EVEN);
							speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
							speedbalEntity.setAmount(entry.getDiscountAmount().doubleValue());//成本金额
							speedbalEntity.setEPrice(0.0);//结存单价
							speedbalEntity.setEAmount(0.0);//结存金额
							//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
							speedbalEntity.setDiffAmount(0.0);//差异金额
							speedbalEntity.setBlidSrc(entity.getClassIDSrc());//源单类型
							speedbalEntity.setFlag(-1);//出入库标记
							speedbalEntity.setStatus(1);//计算状态
							speedbalEntity.setNegFlag(0);//负结余处理状态
							this.save(speedbalEntity);
						}
					}
				}
			}
		} catch (Exception e){
			//单据状态回写
			TScIcXsstockbillEntity bill= this.getEntity(TScIcXsstockbillEntity.class, id);
			if(bill.getCheckState() == 2) {
				//审核异常
				TSAuditRelationEntity delRelationEntity = relationEntityList.get(0);
				super.delete(delRelationEntity);
				if(relationEntityList.size() > 1) {
					//前一次审核内容
					TSAuditRelationEntity lastAuditInfo = relationEntityList.get(1);
					bill.setCheckState(bill.getCheckState() - 1);
					bill.setCheckerId(lastAuditInfo.getAuditorId());
					bill.setCheckDate(lastAuditInfo.getAuditDate());
				} else {
					bill.setCheckState(0);
					bill.setCheckerId(null);
					bill.setCheckDate(null);
				}
				//更新待审核数据预警提醒
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, Globals.SC_IC_XSSTOCKBILL_TRANTYPE.toString(), id});
				if(tScBillAuditStatusEntityList.size() > 0){
					for(TScBillAuditStatusEntity billAuditStatusEntity : tScBillAuditStatusEntityList){
						billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()-1);
						super.saveOrUpdate(billAuditStatusEntity);
					}
				}
			} else {
				//反审核异常
				TSAuditRelationEntity endRelationEntity = relationEntityList.get(0);
				endRelationEntity.setIsFinish(1);//审核完毕
				endRelationEntity.setDeleted(0);//删除标记
				endRelationEntity.setIsBack(0);//反审核标记
				//更新待审核数据预警提醒
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, Globals.SC_IC_XSSTOCKBILL_TRANTYPE.toString(), id});
				if(tScBillAuditStatusEntityList.size() > 0){
					for(TScBillAuditStatusEntity billAuditStatusEntity : tScBillAuditStatusEntityList){
						billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()+1);
						super.saveOrUpdate(billAuditStatusEntity);
					}
				}
				bill.setCheckState(bill.getCheckState()+1);
				bill.setCheckerId(endRelationEntity.getAuditorId());
				bill.setCheckDate(endRelationEntity.getAuditDate());

			}
			super.saveOrUpdate(bill);
			String text = "审核";
			if(audit != 1){
				text = "反审核";
			}
			j.setSuccess(false);
			j.setMsg("单据"+text+"异常："+e.getMessage());
		}
		return j;
	}

	@Override
	public AjaxJson enableBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr = "";
		for(String id : idList){
			idStr += "'"+id+"',";
		}
		if(StringUtils.isNotEmpty(idStr)) {
			idStr = idStr.substring(0,idStr.length()-1);
			String updateSql = "update t_sc_ic_xsstockbill set cancellation = 0 where id in ("+idStr+")";
			try {
				updateBySqlString(updateSql);
			}catch (Exception e){
				j.setSuccess(false);
				j.setMsg("作废失败："+e.getMessage());
			}

		}
		return j;
	}

	@Override
	public AjaxJson cancelBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr = "";
		for(String id : idList){
			idStr += "'"+id+"',";
		}
		if(StringUtils.isNotEmpty(idStr)) {
			idStr = idStr.substring(0,idStr.length()-1);
			String updateSql = "update t_sc_ic_xsstockbill set cancellation = 1 where id in ("+idStr+")";
			try {
				updateBySqlString(updateSql);
			}catch (Exception e){
				j.setSuccess(false);
				j.setMsg("反作废失败："+e.getMessage());
			}

		}
		return j;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScIcXsstockbillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemId()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateAmount()));
 		sql  = sql.replace("#{curpayamount}",String.valueOf(t.getCurPayAmount()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountID()));
 		sql  = sql.replace("#{freight}",String.valueOf(t.getFreight()));
 		sql  = sql.replace("#{weight}",String.valueOf(t.getWeight()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIDSrc()));
 		sql  = sql.replace("#{id_src}",String.valueOf(t.getIdSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{diffamount}",String.valueOf(t.getDiffAmount()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}