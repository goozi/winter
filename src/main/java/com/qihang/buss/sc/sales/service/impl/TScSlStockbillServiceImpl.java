
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScSlStockbillServiceI;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScAuditBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("tScSlStockbillService")
@Transactional
public class TScSlStockbillServiceImpl extends CommonServiceImpl implements TScSlStockbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScSlStockbillEntity) entity);
 	}
	
	public void addMain(TScSlStockbillEntity tScSlStockbill,
	        List<TScSlStockbillentryEntity> tScSlStockbillentryList){
			//保存主信息
		    tScSlStockbill.setCheckState(0);
			this.save(tScSlStockbill);
		
			/**保存-销售出库单明细*/
			for(TScSlStockbillentryEntity tScSlStockbillentry:tScSlStockbillentryList){
				//外键设置
				tScSlStockbillentry.setWeight(tScSlStockbillentry.getWeight_());
				tScSlStockbillentry.setFid(tScSlStockbill.getId());
				tScSlStockbillentry.setCommitqty(0.0);
				Double disPrice = tScSlStockbillentry.getDiscountPrice();
				if(null != disPrice && disPrice > 0) {
					Double basicCoe = 1.0;
					Double costPrice = disPrice / basicCoe;
					Double qty = tScSlStockbillentry.getQty();
					Double costAmount = costPrice * qty;
					DecimalFormat def = new DecimalFormat("#.00");
					costPrice = Double.parseDouble(def.format(costPrice));
					costAmount = Double.parseDouble(def.format(costAmount));
					tScSlStockbillentry.setCostPrice(costPrice);
					tScSlStockbillentry.setCostAmount(costAmount);
				}else{
					tScSlStockbillentry.setCostPrice(0.0);
					tScSlStockbillentry.setCostAmount(0.0);
				}
				this.save(tScSlStockbillentry);
				//订单数量回写
				if("103".equals(tScSlStockbill.getTrantype())) {
					String classId = tScSlStockbillentry.getClassidSrc();
					String entryId = tScSlStockbillentry.getEntryidOrder();
					if (StringUtils.isNotEmpty(entryId) && "102".equals(classId)) {
						String updateSql = "update t_sc_order_entry set stockQty = stockQty+" + tScSlStockbillentry.getQty() + " where id = '" + entryId + "'";
						updateBySqlString(updateSql);
					}
				}else if("104".equals(tScSlStockbill.getTrantype())){
					//String entryId = tScSlStockbillentry.getEntryidOrder();
					String entryIdSrc = tScSlStockbillentry.getEntryidSrc();
//					//退货减少订单收货数量
//					if (StringUtils.isNotEmpty(entryId)) {
//						String updateSql = "update t_sc_order_entry set stockQty = stockQty-" + tScSlStockbillentry.getQty() + " where id = '" + entryId + "'";
//						updateBySqlString(updateSql);
//					}
					//退货记录入库单明细退货数量
					if(StringUtils.isNotEmpty(entryIdSrc)){
						String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+" + tScSlStockbillentry.getQty() + " where id = '" + entryIdSrc + "'";
						updateBySqlString(updateSql);
					}
				}
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScSlStockbill);
	}

	
	public void updateMain(TScSlStockbillEntity tScSlStockbill,
	        List<TScSlStockbillentryEntity> tScSlStockbillentryList) {
		//保存主表信息
		this.saveOrUpdate(tScSlStockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScSlStockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-销售出库单明细
	    String hql0 = "from TScSlStockbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScSlStockbillentryEntity> tScSlStockbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-销售出库单明细
		for(TScSlStockbillentryEntity oldE:tScSlStockbillentryOldList){
			boolean isUpdate = false;
				for(TScSlStockbillentryEntity sendE:tScSlStockbillentryList){
					//需要更新的明细数据-销售出库单明细
					if(oldE.getId().equals(sendE.getId())){
						//修改订单已执行量
						if(StringUtils.isNotEmpty(oldE.getEntryidOrder()) && oldE.getEntryidOrder().equals(sendE.getEntryidOrder())){
							Double oldQty = oldE.getQty();
							Double newQty = sendE.getQty();
							Double changeValue = oldQty-newQty;
							if("103".equals(tScSlStockbill.getTrantype())) {
								if("102".equals(oldE.getClassidSrc())) {
									String updateSql = "update t_sc_order_entry set stockQty = stockQty-(" + changeValue + ") where id = '" + oldE.getEntryidOrder() + "'";
									this.updateBySqlString(updateSql);
								}
							}else if("104".equals(tScSlStockbill.getTrantype())){
								//String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+("+changeValue+") where id = '"+oldE.getEntryIdOrder()+"'";
								//this.updateBySqlString(updateSql);

								String entryId = oldE.getEntryidSrc();
								if(StringUtils.isNotEmpty(entryId)){
									String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+("+changeValue+") where id = '"+entryId+"'";
									this.updateBySqlString(updateSql);
								}
							}
						}
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							oldE.setWeight(sendE.getWeight_());
							//设置成本单价、成本金额
							Double disPrice = oldE.getDiscountPrice();
							if(null != disPrice) {
								Double basicCoe = 1.0;
								Double costPrice = disPrice / basicCoe;
								Double qty = oldE.getQty();
								Double costAmount = costPrice * qty;
								DecimalFormat def = new DecimalFormat("#.00");
								costPrice = Double.parseDouble(def.format(costPrice));
								costAmount = Double.parseDouble(def.format(costAmount));
								oldE.setCostPrice(costPrice);
								oldE.setCostAmount(costAmount);
							} else {
								oldE.setCostPrice(0.0);
								oldE.setCostAmount(0.0);
							}
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
					//修改订单已执行量
					if(StringUtils.isNotEmpty(oldE.getEntryidOrder())){
						if("103".equals(tScSlStockbill.getTrantype())) {
							if("102".equals(oldE.getClassidSrc())) {
								String updateSql = "update t_sc_order_entry set stockQty = stockQty-(" + oldE.getQty() + ") where id = '" + oldE.getEntryidOrder() + "'";
								this.updateBySqlString(updateSql);
							}
						}else if("104".equals(tScSlStockbill.getTrantype())){
							//String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+("+oldE.getQty()+") where id = '"+oldE.getEntryidOrder()+"'";
							//this.updateBySqlString(updateSql);

							String entryId = oldE.getEntryidSrc();
							if(StringUtils.isNotEmpty(entryId)){
								String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+("+oldE.getQty()+") where id = '"+entryId+"'";
								this.updateBySqlString(updateSql);
							}
						}
					}
		    		//如果数据库存在的明细，前台没有传递过来则是删除-销售出库单明细
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-销售出库单明细
			for(TScSlStockbillentryEntity tScSlStockbillentry:tScSlStockbillentryList){
				if(oConvertUtils.isEmpty(tScSlStockbillentry.getId())){
					//修改订单已执行量
					if(StringUtils.isNotEmpty(tScSlStockbillentry.getEntryidOrder())){
						if("103".equals(tScSlStockbill.getTrantype())) {
							if("102".equals(tScSlStockbillentry.getClassidSrc())) {
								String updateSql = "update t_sc_order_entry set stockQty = stockQty+(" + tScSlStockbillentry.getQty() + ") where id = '" + tScSlStockbillentry.getEntryidOrder() + "'";
								this.updateBySqlString(updateSql);
							}
						}else if("104".equals(tScSlStockbill.getTrantype())){

							if(StringUtils.isNotEmpty(tScSlStockbillentry.getEntryidSrc())){
								String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-("+tScSlStockbillentry.getQty()+") where id = '"+tScSlStockbillentry.getEntryidSrc()+"'";
								this.updateBySqlString(updateSql);
							}
						}
					}
					//外键设置
					tScSlStockbillentry.setFid(tScSlStockbill.getId());
					tScSlStockbillentry.setWeight(tScSlStockbillentry.getWeight_());
					//设置成本单价、成本金额
					Double disPrice = tScSlStockbillentry.getDiscountPrice();
					Double basicCoe = 1.0;
					if(null != disPrice) {
						Double costPrice = disPrice / basicCoe;
						Double qty = tScSlStockbillentry.getQty();
						Double costAmount = costPrice * qty;
						DecimalFormat def = new DecimalFormat("#.00");
						costPrice = Double.parseDouble(def.format(costPrice));
						costAmount = Double.parseDouble(def.format(costAmount));
						tScSlStockbillentry.setCostPrice(costPrice);
						tScSlStockbillentry.setCostAmount(costAmount);
					} else {
						tScSlStockbillentry.setCostPrice(0.0);
						tScSlStockbillentry.setCostAmount(0.0);
					}
					this.save(tScSlStockbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScSlStockbill);
	}

	
	public void delMain(TScSlStockbillEntity tScSlStockbill) {
		//删除主表信息
		this.delete(tScSlStockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScSlStockbill.getId();
		//===================================================================================
		//删除-销售出库单明细
	    String hql0 = "from TScSlStockbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScSlStockbillentryEntity> tScSlStockbillentryOldList = this.findHql(hql0,id0);
		//修改订单已执行量
		for(TScSlStockbillentryEntity entity : tScSlStockbillentryOldList) {
			if (StringUtils.isNotEmpty(entity.getEntryidSrc())) {
				if ("103".equals(tScSlStockbill.getTrantype())) {
					if("102".equals(entity.getClassidSrc())) {
						String updateSql = "update t_sc_order_entry set stockQty = stockQty-(" + entity.getQty() + ") where id = '" + entity.getEntryidOrder() + "'";
						this.updateBySqlString(updateSql);
					}
				} else if ("104".equals(tScSlStockbill.getTrantype())) {
					String entryId = entity.getEntryidOrder();
					String entryIdSrc = entity.getEntryidSrc();
//					if(StringUtils.isNotEmpty(entryId)) {
//						String updateSql = "update t_sc_order_entry set stockQty = stockQty+(" + entity.getQty() + ") where id = '" + entryId + "'";
//						this.updateBySqlString(updateSql);
//					}
					if (StringUtils.isNotEmpty(entryIdSrc)) {
						String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-(" + entity.getQty() + ") where id = '" + entryIdSrc + "'";
						this.updateBySqlString(updateSql);
					}
				}
			}
		}
		this.deleteAllEntitie(tScSlStockbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScSlStockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScSlStockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScSlStockbillEntity t){
	 	return true;
 	}

	/**
	 * 作废，同时回写源单数据
	 * @param ids
	 * @return
	 */
	@Override
	public AjaxJson cancelBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		for(String id : idList){
			idStr += "'"+id+"',";
			//修改订单已执行量
			TScSlStockbillEntity stockBill = this.getEntity(TScSlStockbillEntity.class,id);
			List<TScSlStockbillentryEntity> tScPoStockbillentryOldList = this.findHql("from TScSlStockbillentryEntity where fid = ? ",new Object[]{id});
			for(TScSlStockbillentryEntity entity : tScPoStockbillentryOldList){
				if(StringUtils.isNotEmpty(entity.getEntryidOrder())) {
					//回写采购订单已执行数量
					if ("102".equals(entity.getClassidSrc()) && "103".equals(stockBill.getTrantype())) {
						String updateSql = "update t_sc_order_entry set stockQty = stockQty-(" + entity.getQty() + ") where id = '" + entity.getEntryidOrder() + "'";
						this.updateBySqlString(updateSql);
					}else if("103".equals(entity.getClassidSrc()) && "104".equals(stockBill.getTrantype())){
						String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+(" + entity.getQty() + ") where id = '" + entity.getEntryidOrder() + "'";
						this.updateBySqlString(updateSql);
					}
				}
			}
		}
		if(idStr.length() > 0){
			idStr = idStr.substring(0,idStr.length()-1);
		}
		String updateSql = "update t_sc_sl_stockbill set cancellation = 1 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("作废失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 反作废，同时回写源单数据
	 * @param ids
	 * @return
	 */
	@Override
	public AjaxJson enableBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		Boolean isAllowEnable = true;
		for(String id : idList){
			idStr += "'"+id+"',";
			//校验引用的采购订单执行数量是否超出订单数量
			List<TScSlStockbillentryEntity> tScPoStockbillentryOldList = this.findHql("from TScSlStockbillentryEntity where fid = ? ",new Object[]{id});
			for(TScSlStockbillentryEntity entity : tScPoStockbillentryOldList){
				if(StringUtils.isNotEmpty(entity.getEntryidOrder())) {
					if ("102".equals(entity.getClassidSrc())) {
						TScOrderentryEntity orderentryEntity = this.getEntity(TScOrderentryEntity.class,entity.getEntryidOrder());
						BigDecimal stockQty = orderentryEntity.getStockQty();
						BigDecimal billQty = orderentryEntity.getBasicQty();
						Double enableQty = entity.getBasicQty();
						//订单已执行数量溢出则不可反作废
						if(enableQty+Double.parseDouble(stockQty.toString()) <= Double.parseDouble(billQty.toString())) {
							continue;
						}else{
							isAllowEnable = false;
							j.setSuccess(false);
							j.setMsg("该单据不可反作废");
							break;
						}
					}
				}
			}
		}
		if(isAllowEnable) {
			for(String id : idList){
				//修改订单已执行量
				TScSlStockbillEntity stockBill = this.getEntity(TScSlStockbillEntity.class,id);
				List<TScSlStockbillentryEntity> tScPoStockbillentryOldList = this.findHql("from TScSlStockbillentryEntity where fid = ? ", new Object[]{id});
				for(TScSlStockbillentryEntity entity : tScPoStockbillentryOldList){
					if(StringUtils.isNotEmpty(entity.getEntryidOrder())) {
						//回写采购订单已执行数量
						if ("102".equals(entity.getClassidSrc()) && "103".equals(stockBill.getTrantype())) {
							String updateSql = "update t_sc_order_entry set stockQty = stockQty+(" + entity.getQty() + ") where id = '" + entity.getEntryidOrder() + "'";
							this.updateBySqlString(updateSql);
						}else if("103".equals(entity.getClassidSrc()) && "104".equals(stockBill.getTrantype())){
							String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-(" + entity.getQty() + ") where id = '" + entity.getEntryidOrder() + "'";
							this.updateBySqlString(updateSql);
						}
					}
				}
			}
			if (idStr.length() > 0) {
				idStr = idStr.substring(0, idStr.length() - 1);
			}
			String updateSql = "update t_sc_sl_stockbill set cancellation = 0 where id in (" + idStr + ")";
			try {
				updateBySqlString(updateSql);
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("作废失败：" + e.getMessage());
			}
		}
		return j;
	}

	@Override
	public AjaxJson afterAudit(String id, Integer audit, String tranType,String sonId) {
		AjaxJson j = new AjaxJson();
		TScSlStockbillEntity entity = this.getEntity(TScSlStockbillEntity.class,id);
		DecimalFormat def = new DecimalFormat("#.00");
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where tranType = ? and billId=? order by orderNum desc", new Object[]{entity.getTrantype(), id});
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?", new Object[]{sonId, tranType, id});
		try {
			if (auditBillInfoEntityList.size() > 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				if (((auditBillInfoEntity.getOldState() == 1 && auditBillInfoEntity.getNewState() == 2) || (auditBillInfoEntity.getOldState() == 2 && auditBillInfoEntity.getNewState() == 1)) && relationEntityList.get(0).getStatus() >= 0) {
					List<TScSlStockbillentryEntity> entryList = findHql("from TScSlStockbillentryEntity where fid = ?", new Object[]{id});
					for (TScSlStockbillentryEntity entry : entryList) {
						String itemId = entry.getItemid();
						List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ? order by defaultCK desc", new Object[]{itemId});
						BigDecimal ckCoefficient = BigDecimal.ZERO;//仓库换算率
						BigDecimal basicCoefficient = BigDecimal.ZERO;//基本换算率
						for (TScItemPriceEntity unit : unitList) {
							if (1 == (unit.getDefaultCK() == null ? 0 : unit.getDefaultCK())) {
								ckCoefficient = unit.getCoefficient();
							}
							if ("0001".equals(unit.getUnitType())) {
								basicCoefficient = unit.getCoefficient();
							}

							//回写最近采购单价
							if ("103".equals(tranType) && 1 == audit) {
								BigDecimal coefficient = BigDecimal.ONE;
								if (null != unit.getCoefficient()) {
									coefficient = unit.getCoefficient();
								}
								BigDecimal xsLastPrice = BigDecimal.ZERO;
								if (null != entry.getDiscountPrice()) {
									BigDecimal coe = BigDecimal.valueOf(entry.getCoefficient());
									BigDecimal basicPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(coe, 2, BigDecimal.ROUND_HALF_EVEN);//基本单价
									xsLastPrice = basicPrice.multiply(coefficient);
								}
								unit.setXsLatestPrice(xsLastPrice);
								super.saveOrUpdate(unit);
							} else if ("103".equals(tranType) && 0 == audit) {
								List<TScSlStockbillViewEntity> lastStockInfo = super.findHql("from TScSlStockbillViewEntity where entryitemid = ? and unitid = ? and tranType=103 and checkState=2 and sonid = ? and id <> ? order by date desc,createDate desc", new Object[]{entry.getItemid(), unit.getId(), entity.getSonid(), entity.getId()});
								if (lastStockInfo.size() > 0) {
									TScSlStockbillViewEntity lastStock = lastStockInfo.get(0);
									BigDecimal coefficient = BigDecimal.ONE;
									if (null != unit.getCoefficient()) {
										coefficient = unit.getCoefficient();
									}
									BigDecimal xsLastPrice = BigDecimal.ZERO;
									if (null != entry.getDiscountPrice()) {
										BigDecimal coe = BigDecimal.valueOf(entry.getCoefficient());
										BigDecimal basicPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(coe, 2, BigDecimal.ROUND_HALF_EVEN);//基本单价
										xsLastPrice = basicPrice.multiply(coefficient);
									}
									unit.setXsLatestPrice(xsLastPrice);
								} else {
									unit.setXsLatestPrice(BigDecimal.ZERO);
								}
								super.saveOrUpdate(unit);
							}
						}
						Double basicQty = entry.getBasicQty();//基本数量
						BigDecimal secQty = entry.getSecQty() == null ? BigDecimal.ZERO : BigDecimal.valueOf(entry.getSecQty());//辅助数量
						BigDecimal costPrice = BigDecimal.ZERO;
						if (null != entry.getDiscountPrice()) {
							costPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);//成本单价
						}
						BigDecimal costAmount = BigDecimal.ZERO;
						if (null != entry.getDiscountAmount()) {
							costAmount = BigDecimal.valueOf(entry.getDiscountAmount());
						}

						TScIcInventoryEntity inventoryEntity = null;
						List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?", new Object[]{itemId, entry.getStockid()});
						if (inventoryEntities.size() > 0) {
							inventoryEntity = inventoryEntities.get(0);
						}
						String hql = "from TScIcInventoryBatchnoEntity where itemId = ? and stockId = ?";
						Object[] queryInfo = new Object[]{itemId, entry.getStockid()};
						if (StringUtil.isNotEmpty(entry.getBatchno())) {
							hql += " and batchNo = ?";
							queryInfo = new Object[]{itemId, entry.getStockid(), entry.getBatchno()};
						}
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql(hql, queryInfo);
						if (null != inventoryEntity) {
							//若为审核操作
							if (1 == audit) {
								//退货单
								if ("104".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockid())) {
										inventoryEntity.setStockId(entry.getStockid());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
									//计算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).add(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryEntity.setCostPrice((inventoryEntity.getCostPrice() + costPrice)/2);//取平均单价
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
											BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
											inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).add(qty).doubleValue());
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//											inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
										}
										//inventoryBatchnoEntity.setCostPrice((inventoryBatchnoEntity.getCostPrice() + costPrice)/2);
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
										this.saveOrUpdate(inventoryBatchnoEntity);
									} else {
										TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
										inventoryBatchnoEntity.setIsCheck(0);
										inventoryBatchnoEntity.setCostAmount(costAmount.doubleValue());
										inventoryBatchnoEntity.setCostPrice(costPrice.doubleValue());
										if (BigDecimal.ZERO != ckCoefficient) {
											BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
											inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryBatchnoEntity.setQty(xQty);
//											inventoryBatchnoEntity.setSmallQty(smallQty);
										}

										//inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
										inventoryBatchnoEntity.setSecQty(entry.getSecQty());
										if (StringUtils.isNotEmpty(entry.getStockid())) {
											inventoryBatchnoEntity.setStockId(entry.getStockid());
										}
										inventoryBatchnoEntity.setItemId(entry.getItemid());
										inventoryBatchnoEntity.setBatchNo(entry.getBatchno());
										if (null != entry.getKfdate()) {
											inventoryBatchnoEntity.setKfDate(entry.getKfdate());
										}
										if (null != entry.getKfperiod()) {
											inventoryBatchnoEntity.setKfPeriod(entry.getKfperiod());
										}
										if (StringUtils.isNotEmpty(entry.getKfdatetype())) {
											inventoryBatchnoEntity.setKfDateType(entry.getKfdatetype());
										}
										if (null != entry.getPerioddate()) {
											inventoryBatchnoEntity.setPeriodDate(entry.getPerioddate());
										}
										inventoryBatchnoEntity.setCount(1);
										this.save(inventoryBatchnoEntity);
									}
									//存货日结记录
									TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
									speedbalEntity.setTranType(tranType);//单据类型
									speedbalEntity.setDate(entity.getDate());//单据日期
									speedbalEntity.setBillId(id);//单据主键
									speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
									speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
									speedbalEntity.setItemId(entry.getItemid());//商品id
									speedbalEntity.setStockId(entry.getStockid());//仓库id
									speedbalEntity.setBatchNo(entry.getBatchno());//批号
									speedbalEntity.setQty(entry.getBasicQty());//数量
									speedbalEntity.setSecQty(entry.getSecQty());//辅助数量
									BigDecimal price = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
									speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
									speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
									speedbalEntity.setEPrice(0.0);//结存单价
									speedbalEntity.setEAmount(0.0);//结存金额
									//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
									speedbalEntity.setDiffAmount(0.0);//差异金额
									speedbalEntity.setBlidSrc(entity.getClassidSrc());//源单类型
									speedbalEntity.setFlag(1);//出入库标记
									speedbalEntity.setStatus(1);//计算状态
									speedbalEntity.setNegFlag(0);//负结余处理状态
									this.save(speedbalEntity);
								}
								//出库单
								else if ("103".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockid())) {
										inventoryEntity.setStockId(entry.getStockid());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
									//计算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).subtract(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryEntity.setCostPrice((inventoryEntity.getCostPrice() - costPrice)/2);
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
									if (inventoryBatchnoEntityList.size() > 0) {
										TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
										inventoryBatchnoEntity.setIsCheck(0);
										//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - basicQty);
										inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).subtract(secQty).doubleValue());
										if (BigDecimal.ZERO != ckCoefficient) {
											BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
											inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).subtract(qty).doubleValue());
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//											inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
										}
										//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() - costPrice);
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
										if (StringUtils.isNotEmpty(entry.getStockid())) {
											inventoryBatchnoEntity.setStockId(entry.getStockid());
										}
										inventoryBatchnoEntity.setItemId(entry.getItemid());
										inventoryBatchnoEntity.setBatchNo(entry.getBatchno());
										if (null != entry.getKfdate()) {
											inventoryBatchnoEntity.setKfDate(entry.getKfdate());
										}
										if (null != entry.getKfperiod()) {
											inventoryBatchnoEntity.setKfPeriod(entry.getKfperiod());
										}
										if (StringUtils.isNotEmpty(entry.getKfdatetype())) {
											inventoryBatchnoEntity.setKfDateType(entry.getKfdatetype());
										}
										if (null != entry.getPerioddate()) {
											inventoryBatchnoEntity.setPeriodDate(entry.getPerioddate());
										}
										this.saveOrUpdate(inventoryBatchnoEntity);
									}
									//存货日结记录
									TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
									speedbalEntity.setTranType(tranType);//单据类型
									speedbalEntity.setDate(entity.getDate());//单据日期
									speedbalEntity.setBillId(id);//单据主键
									speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
									speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
									speedbalEntity.setItemId(entry.getItemid());//商品id
									speedbalEntity.setStockId(entry.getStockid());//仓库id
									speedbalEntity.setBatchNo(entry.getBatchno());//批号
									speedbalEntity.setQty(entry.getBasicQty());//数量
									speedbalEntity.setSecQty(secQty.doubleValue());//辅助数量
									BigDecimal price = BigDecimal.valueOf((entry.getDiscountPrice() == null ? 0.0 : entry.getDiscountPrice())).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
									speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
									speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
									speedbalEntity.setEPrice(0.0);//结存单价
									speedbalEntity.setEAmount(0.0);//结存金额
									//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
									speedbalEntity.setDiffAmount(0.0);//差异金额
									speedbalEntity.setBlidSrc(entity.getClassidSrc());//源单类型
									speedbalEntity.setFlag(-1);//出入库标记
									speedbalEntity.setStatus(1);//计算状态
									speedbalEntity.setNegFlag(0);//负结余处理状态
									this.save(speedbalEntity);
								}
							}
							//反审核
							else {
								//退货单
								if ("104".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockid())) {
										inventoryEntity.setStockId(entry.getStockid());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
									//计算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).subtract(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryEntity.setCostPrice(inventoryEntity.getCostPrice() - costPrice);
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
										//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - basicQty);
										inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).subtract(secQty).doubleValue());
										if (BigDecimal.ZERO != ckCoefficient) {
											BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
											inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).subtract(qty).doubleValue());
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//											inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
										}
										//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() - costPrice);
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
										if (null != entry.getKfdate()) {
											inventoryBatchnoEntity.setKfDate(entry.getKfdate());
										}
										if (null != entry.getKfperiod()) {
											inventoryBatchnoEntity.setKfPeriod(entry.getKfperiod());
										}
										if (StringUtils.isNotEmpty(entry.getKfdatetype())) {
											inventoryBatchnoEntity.setKfDateType(entry.getKfdatetype());
										}
										if (null != entry.getPerioddate()) {
											inventoryBatchnoEntity.setPeriodDate(entry.getPerioddate());
										}
										this.saveOrUpdate(inventoryBatchnoEntity);
									}
								}
								//出库单
								else if ("103".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockid())) {
										inventoryEntity.setStockId(entry.getStockid());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
									//就算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).add(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
									}
									//inventoryEntity.setCostPrice(inventoryEntity.getCostPrice() + costPrice);
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
									if (inventoryBatchnoEntityList.size() > 0) {
										TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
										inventoryBatchnoEntity.setIsCheck(0);
										//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() + basicQty);
										inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).add(secQty).doubleValue());
										if (BigDecimal.ZERO != ckCoefficient) {
											BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
											inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).add(qty).doubleValue());
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//											inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
										}
										//inventoryBatchnoEntity.setCostPrice(inventoryBatchnoEntity.getCostPrice() + costPrice);
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
										if (StringUtils.isNotEmpty(entry.getStockid())) {
											inventoryBatchnoEntity.setStockId(entry.getStockid());
										}
										inventoryBatchnoEntity.setItemId(entry.getItemid());
										inventoryBatchnoEntity.setBatchNo(entry.getBatchno());
										if (null != entry.getKfdate()) {
											inventoryBatchnoEntity.setKfDate(entry.getKfdate());
										}
										if (null != entry.getKfperiod()) {
											inventoryBatchnoEntity.setKfPeriod(entry.getKfperiod());
										}
										if (StringUtils.isNotEmpty(entry.getKfdatetype())) {
											inventoryBatchnoEntity.setKfDateType(entry.getKfdatetype());
										}
										if (null != entry.getPerioddate()) {
											inventoryBatchnoEntity.setPeriodDate(entry.getPerioddate());
										}
										this.saveOrUpdate(inventoryBatchnoEntity);
									}
								}
								//反审核，删除存货日记记录
								List<TScIcSpeedbalEntity> speedbalEntityList = this.findHql("from TScIcSpeedbalEntity where tranType = ? and billId = ? and billEntryId = ? ", new Object[]{tranType, id, entry.getId()});
								if (speedbalEntityList.size() > 0) {
									for (TScIcSpeedbalEntity speedbalEntity : speedbalEntityList) {
										super.delete(speedbalEntity);
									}
								}
							}
						} else {
							if ("104".equals(tranType) && 1 == audit) {
								TScIcInventoryEntity newInventory = new TScIcInventoryEntity();
								newInventory.setItemId(itemId);
								if (StringUtils.isNotEmpty(entry.getStockid())) {
									newInventory.setStockId(entry.getStockid());
								}
								//newInventory.setBasicQty(entry.getBasicQty());
								newInventory.setSecQty(entry.getSecQty());
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
									newInventory.setBasicQty(entry.getBasicQty());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									newInventory.setQty(xQty);
//									newInventory.setSmallQty(smallQty);
								}
								newInventory.setCostPrice(costPrice.doubleValue());
								newInventory.setCostAmount(costAmount.doubleValue());
								newInventory.setCount(1);
								this.save(newInventory);
								//if (StringUtils.isNotEmpty(entry.getBatchno())) {
								TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
								inventoryBatchnoEntity.setIsCheck(0);
								inventoryBatchnoEntity.setCostAmount(newInventory.getCostAmount());
								inventoryBatchnoEntity.setCostPrice(newInventory.getCostPrice());
								//inventoryBatchnoEntity.setSmallQty(newInventory.getSmallQty());
								//inventoryBatchnoEntity.setQty(newInventory.getQty());
								inventoryBatchnoEntity.setBasicQty(newInventory.getBasicQty());
								inventoryBatchnoEntity.setSecQty(newInventory.getSecQty());
								if (StringUtils.isNotEmpty(newInventory.getStockId())) {
									inventoryBatchnoEntity.setStockId(newInventory.getStockId());
								}
								inventoryBatchnoEntity.setItemId(newInventory.getItemId());
								inventoryBatchnoEntity.setBatchNo(entry.getBatchno());
								if (null != entry.getKfdate()) {
									inventoryBatchnoEntity.setKfDate(entry.getKfdate());
								}
								if (null != entry.getKfperiod()) {
									inventoryBatchnoEntity.setKfPeriod(entry.getKfperiod());
								}
								if (StringUtils.isNotEmpty(entry.getKfdatetype())) {
									inventoryBatchnoEntity.setKfDateType(entry.getKfdatetype());
								}
								if (null != entry.getPerioddate()) {
									inventoryBatchnoEntity.setPeriodDate(entry.getPerioddate());
								}
								inventoryBatchnoEntity.setCount(1);
								this.save(inventoryBatchnoEntity);
								//}
								//存货日结记录
								TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
								speedbalEntity.setTranType(tranType);//单据类型
								speedbalEntity.setDate(entity.getDate());//单据日期
								speedbalEntity.setBillId(id);//单据主键
								speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
								speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
								speedbalEntity.setItemId(entry.getItemid());//商品id
								speedbalEntity.setStockId(entry.getStockid());//仓库id
								speedbalEntity.setBatchNo(entry.getBatchno());//批号
								speedbalEntity.setQty(entry.getBasicQty());//数量
								speedbalEntity.setSecQty(secQty.doubleValue());//辅助数量
								BigDecimal price = BigDecimal.valueOf((entry.getDiscountPrice() == null ? 0.0 : entry.getDiscountPrice())).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
								speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
								speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
								speedbalEntity.setEPrice(0.0);//结存单价
								speedbalEntity.setEAmount(0.0);//结存金额
								//BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
								speedbalEntity.setDiffAmount(0.0);//差异金额
								speedbalEntity.setBlidSrc(entity.getClassidSrc());//源单类型
								speedbalEntity.setFlag(1);//出入库标记
								speedbalEntity.setStatus(1);//计算状态
								speedbalEntity.setNegFlag(0);//负结余处理状态
								this.save(speedbalEntity);
							}
						}
					}
				}
			}
		} catch (Exception e){
			//单据状态回写
			TScSlStockbillEntity bill= this.getEntity(TScSlStockbillEntity.class, id);
			if(bill.getCheckState() == 2) {
				//审核异常
				TSAuditRelationEntity delRelationEntity = relationEntityList.get(0);
				super.delete(delRelationEntity);
				if(relationEntityList.size() > 1) {
					//前一次审核内容
					TSAuditRelationEntity lastAuditInfo = relationEntityList.get(1);
					bill.setCheckState(bill.getCheckState() - 1);
					bill.setCheckerid(lastAuditInfo.getAuditorId());
					bill.setCheckdate(lastAuditInfo.getAuditDate());
				} else {
					bill.setCheckState(0);
					bill.setCheckerid(null);
					bill.setCheckdate(null);
				}
				//更新待审核数据预警提醒
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, tranType, id});
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
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, tranType, id});
				if(tScBillAuditStatusEntityList.size() > 0){
					for(TScBillAuditStatusEntity billAuditStatusEntity : tScBillAuditStatusEntityList){
						billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()+1);
						super.saveOrUpdate(billAuditStatusEntity);
					}
				}
				bill.setCheckState(bill.getCheckState()+1);
				bill.setCheckerid(endRelationEntity.getAuditorId());
				bill.setCheckdate(endRelationEntity.getAuditDate());

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

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScSlStockbillEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTrantype()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemid()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpid()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptid()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockid()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllamount()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateamount()));
 		sql  = sql.replace("#{curpayamount}",String.valueOf(t.getCurpayamount()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountid()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilephone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckamount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassidSrc()));
 		sql  = sql.replace("#{id_src}",String.valueOf(t.getIdSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillnoSrc()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerid()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerid()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckdate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonid()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{weight}",String.valueOf(t.getWeight()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}