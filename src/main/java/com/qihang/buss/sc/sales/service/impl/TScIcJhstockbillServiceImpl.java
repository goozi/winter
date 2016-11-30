
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.service.TScIcJhstockbillServiceI;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScAuditBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillEntity;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillentry1Entity;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillentry2Entity;

import com.qihang.winter.core.constant.Globals;
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


@Service("tScIcJhstockbillService")
@Transactional
public class TScIcJhstockbillServiceImpl extends CommonServiceImpl implements TScIcJhstockbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScIcJhstockbillEntity) entity);
 	}
	
	public void addMain(TScIcJhstockbillEntity tScIcJhstockbill,
	        List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List,List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List){
			//保存主信息
			this.save(tScIcJhstockbill);
		
			/**保存-退货信息表*/
			for(TScIcJhstockbillentry1Entity tScIcJhstockbillentry1:tScIcJhstockbillentry1List){
				//外键设置
				tScIcJhstockbillentry1.setFid(tScIcJhstockbill.getId());
				//设置成本单价，成本金额
				Double qty = tScIcJhstockbillentry1.getQty();
				Double disPrice = tScIcJhstockbillentry1.getDiscountPrice();
				Double costPrice = disPrice;
				Double costAmount = costPrice * qty;
				DecimalFormat def = new DecimalFormat("#.00");
				costPrice = Double.parseDouble(def.format(costPrice));
				costAmount = Double.parseDouble(def.format(costAmount));
				tScIcJhstockbillentry1.setCostPrice(costPrice);
				tScIcJhstockbillentry1.setCostAmount(costAmount);
				this.save(tScIcJhstockbillentry1);
				//退货数量回写
				String entryId = tScIcJhstockbillentry1.getEntryIdSrc();
				if (StringUtils.isNotEmpty(entryId)) {
					String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty+" + tScIcJhstockbillentry1.getQty() + " where id = '" + entryId + "'";
					updateBySqlString(updateSql);
				}
				//TODO 后期或许要回抛至订单
//				String entryIdOrder = tScIcJhstockbillentry1.getEntryIdOrder();
//				if(StringUtils.isNotEmpty(entryIdOrder)){
//					String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-" + tScIcJhstockbillentry1.getQty() + " where id = '" + entryIdOrder + "'";
//					updateBySqlString(updateSql);
//				}
			}
			/**保存-换货信息表*/
			for(TScIcJhstockbillentry2Entity tScIcJhstockbillentry2:tScIcJhstockbillentry2List){
				//外键设置
				tScIcJhstockbillentry2.setFid(tScIcJhstockbill.getId());
				//设置成本单价，成本金额
				Double qty = tScIcJhstockbillentry2.getQty();
				Double disPrice = tScIcJhstockbillentry2.getDiscountPrice();
				Double costPrice = disPrice;
				Double costAmount = costPrice * qty;
				DecimalFormat def = new DecimalFormat("#.00");
				costPrice = Double.parseDouble(def.format(costPrice));
				costAmount = Double.parseDouble(def.format(costAmount));
				tScIcJhstockbillentry2.setCostPrice(costPrice);
				tScIcJhstockbillentry2.setCostAmount(costAmount);
				this.save(tScIcJhstockbillentry2);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScIcJhstockbill);
	}

	
	public void updateMain(TScIcJhstockbillEntity tScIcJhstockbill,
	        List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List,List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List) {
		//保存主表信息
		this.saveOrUpdate(tScIcJhstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScIcJhstockbill.getId();
		Object id1 = tScIcJhstockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-退货信息表
	    String hql0 = "from TScIcJhstockbillentry1Entity where 1 = 1 AND fID = ? ";
	    List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1OldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-退货信息表
		for(TScIcJhstockbillentry1Entity oldE:tScIcJhstockbillentry1OldList){
			boolean isUpdate = false;
				for(TScIcJhstockbillentry1Entity sendE:tScIcJhstockbillentry1List){
					//需要更新的明细数据-退货信息表
					if(oldE.getId().equals(sendE.getId())){
						if(StringUtils.isNotEmpty(oldE.getEntryIdSrc()) && oldE.getEntryIdSrc().equals(sendE.getEntryIdSrc())){
							Double oldQty = oldE.getQty();
							Double newQty = sendE.getQty();
							Double changeValue = oldQty-newQty;
							String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty-("+changeValue+") where id = '"+oldE.getEntryIdSrc()+"'";
							this.updateBySqlString(updateSql);
						}
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							//设置成本单价，成本金额
							Double qty = oldE.getQty();
							Double disPrice = oldE.getDiscountPrice();
							Double costPrice = disPrice;
							Double costAmount = costPrice * qty;
							DecimalFormat def = new DecimalFormat("#.00");
							costPrice = Double.parseDouble(def.format(costPrice));
							costAmount = Double.parseDouble(def.format(costAmount));
							oldE.setCostPrice(costPrice);
							oldE.setCostAmount(costAmount);

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
						String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty-("+oldE.getQty()+") where id = '"+oldE.getEntryIdSrc()+"'";
						this.updateBySqlString(updateSql);
					}
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-退货信息表
			for(TScIcJhstockbillentry1Entity tScIcJhstockbillentry1:tScIcJhstockbillentry1List){
				if(oConvertUtils.isEmpty(tScIcJhstockbillentry1.getId())){
					//外键设置
					tScIcJhstockbillentry1.setFid(tScIcJhstockbill.getId());
					//设置成本单级，成本金额
					Double qty = tScIcJhstockbillentry1.getQty();
					Double disPrice = tScIcJhstockbillentry1.getDiscountPrice();
					Double costPrice = disPrice;
					Double costAmount = costPrice * qty;
					DecimalFormat def = new DecimalFormat("#.00");
					costPrice = Double.parseDouble(def.format(costPrice));
					costAmount = Double.parseDouble(def.format(costAmount));
					tScIcJhstockbillentry1.setCostPrice(costPrice);
					tScIcJhstockbillentry1.setCostAmount(costAmount);

					this.save(tScIcJhstockbillentry1);
					if(StringUtils.isNotEmpty(tScIcJhstockbillentry1.getEntryIdSrc())){
						String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty+("+tScIcJhstockbillentry1.getQty()+") where id = '"+tScIcJhstockbillentry1.getEntryIdSrc()+"'";
						this.updateBySqlString(updateSql);
					}
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-换货信息表
	    String hql1 = "from TScIcJhstockbillentry2Entity where 1 = 1 AND fID = ? ";
	    List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2OldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-换货信息表
		for(TScIcJhstockbillentry2Entity oldE:tScIcJhstockbillentry2OldList){
			boolean isUpdate = false;
				for(TScIcJhstockbillentry2Entity sendE:tScIcJhstockbillentry2List){
					//需要更新的明细数据-换货信息表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							Double qty = oldE.getQty();
							Double disPrice = oldE.getDiscountPrice();
							Double costPrice = disPrice;
							Double costAmount = costPrice * qty;
							DecimalFormat def = new DecimalFormat("#.00");
							costPrice = Double.parseDouble(def.format(costPrice));
							costAmount = Double.parseDouble(def.format(costAmount));
							oldE.setCostPrice(costPrice);
							oldE.setCostAmount(costAmount);
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-换货信息表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-换货信息表
			for(TScIcJhstockbillentry2Entity tScIcJhstockbillentry2:tScIcJhstockbillentry2List){
				if(oConvertUtils.isEmpty(tScIcJhstockbillentry2.getId())){
					//外键设置
					tScIcJhstockbillentry2.setFid(tScIcJhstockbill.getId());
					//设置成本单级，成本金额
					Double qty = tScIcJhstockbillentry2.getQty();
					Double disPrice = tScIcJhstockbillentry2.getDiscountPrice();
					Double costPrice = disPrice;
					Double costAmount = costPrice * qty;
					DecimalFormat def = new DecimalFormat("#.00");
					costPrice = Double.parseDouble(def.format(costPrice));
					costAmount = Double.parseDouble(def.format(costAmount));
					tScIcJhstockbillentry2.setCostPrice(costPrice);
					tScIcJhstockbillentry2.setCostAmount(costAmount);

					this.save(tScIcJhstockbillentry2);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScIcJhstockbill);
	}

	
	public void delMain(TScIcJhstockbillEntity tScIcJhstockbill) {
		//删除主表信息
		this.delete(tScIcJhstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScIcJhstockbill.getId();
		Object id1 = tScIcJhstockbill.getId();
		//===================================================================================
		//删除-退货信息表
	    String hql0 = "from TScIcJhstockbillentry1Entity where 1 = 1 AND fID = ? ";
	    List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1OldList = this.findHql(hql0,id0);
		//修改订单已执行量
		for(TScIcJhstockbillentry1Entity entity : tScIcJhstockbillentry1OldList){
			if(StringUtils.isNotEmpty(entity.getEntryIdSrc())) {
				String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty-(" + entity.getQty() + ") where id = '" + entity.getEntryIdOrder() + "'";
				this.updateBySqlString(updateSql);
			}
//			String updateInventory = "update t_sc_ic_inventory set count = count-1 where itemId = '"+entity.getItemId()+"'";
//			this.updateBySqlString(updateInventory);
//			if(StringUtils.isNotEmpty(entity.getBatchNo())){
//				String updateBatchNOInventory = "update t_sc_ic_inventory_batchno set count = count-1 where itemId = '"+entity.getItemId()+"' and batchNo = '"+entity.getBatchNo()+"'";
//				this.updateBySqlString(updateBatchNOInventory);
//			}
		}
		this.deleteAllEntitie(tScIcJhstockbillentry1OldList);
		//===================================================================================
		//删除-换货信息表
	    String hql1 = "from TScIcJhstockbillentry2Entity where 1 = 1 AND fID = ? ";
	    List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2OldList = this.findHql(hql1,id1);
//		for(TScIcJhstockbillentry2Entity entity : tScIcJhstockbillentry2OldList){
//			String updateInventory = "update t_sc_ic_inventory set count = count-1 where itemId = '"+entity.getItemId()+"'";
//			this.updateBySqlString(updateInventory);
//			if(StringUtils.isNotEmpty(entity.getBatchNo())){
//				String updateBatchNOInventory = "update t_sc_ic_inventory_batchno set count = count-1 where itemId = '"+entity.getItemId()+"' and batchNo = '"+entity.getBatchNo()+"'";
//				this.updateBySqlString(updateBatchNOInventory);
//			}
//		}
		this.deleteAllEntitie(tScIcJhstockbillentry2OldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcJhstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcJhstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcJhstockbillEntity t){
	 	return true;
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
			String updateSql = "update t_sc_ic_jhstockbill set cancellation = 1 where id in ("+idStr+")";
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
	public AjaxJson enableBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr = "";
		for(String id : idList){
			idStr += "'"+id+"',";
		}
		if(StringUtils.isNotEmpty(idStr)) {
			idStr = idStr.substring(0,idStr.length()-1);
			String updateSql = "update t_sc_ic_jhstockbill set cancellation = 0 where id in ("+idStr+")";
			try {
				updateBySqlString(updateSql);
			}catch (Exception e){
				j.setSuccess(false);
				j.setMsg("反作废失败："+e.getMessage());
			}

		}
		return j;
	}

	@Override
	public AjaxJson afterAudit(String id, Integer audit,String sonId) {
		AjaxJson j = new AjaxJson();
		TScIcJhstockbillEntity entity = this.getEntity(TScIcJhstockbillEntity.class,id);
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where tranType = ? and billId=? order by orderNum desc", new Object[]{entity.getTranType(), id});
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?", new Object[]{sonId, Globals.SC_IC_JHSTOCKBILL_TRANTYPE.toString(), id});
		try {
			if (auditBillInfoEntityList.size() > 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				if (((auditBillInfoEntity.getOldState() == 1 && auditBillInfoEntity.getNewState() == 2) || (auditBillInfoEntity.getOldState() == 2 && auditBillInfoEntity.getNewState() == 1)) && relationEntityList.get(0).getStatus() >= 0) {
					//退货明细
					List<TScIcJhstockbillentry1Entity> entryList = findHql("from TScIcJhstockbillentry1Entity where fid = ?", new Object[]{id});
					DecimalFormat def = new DecimalFormat("#.00");
					for (TScIcJhstockbillentry1Entity entry : entryList) {
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
						Double basicQty = entry.getBasicQty();//基本数量
						BigDecimal secQty = BigDecimal.valueOf(entry.getSecQty());//辅助数量
						BigDecimal costPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);//成本单价
						BigDecimal costAmount = BigDecimal.valueOf(entry.getDiscountAmount());

						TScIcInventoryEntity inventoryEntity = null;
						List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?", new Object[]{itemId, entry.getStockId()});
						if (inventoryEntities.size() > 0) {
							inventoryEntity = inventoryEntities.get(0);
						}
						String hql = "from TScIcInventoryBatchnoEntity where itemId = ? and stockId = ?";
						Object[] queryInfo = new Object[]{itemId, entry.getStockId()};
						if (StringUtil.isNotEmpty(entry.getBatchNo())) {
							hql += " and batchNo = ?";
							queryInfo = new Object[]{itemId,entry.getStockId(),entry.getBatchNo()};
						}
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql(hql, queryInfo);
						if (null != inventoryEntity) {
							//若为审核操作
							if (1 == audit) {
								if (StringUtils.isNotEmpty(entry.getStockId())) {
									inventoryEntity.setStockId(entry.getStockId());
								}
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).subtract(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
								}
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
								Double ePrice = inventoryEntity.getCostPrice();
								Double eAmount = inventoryEntity.getCostAmount();
								//若存在本商品批号数据 增加库存 否则新增批号库存数据
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setIsCheck(0);
									//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - basicQty);
									inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).subtract(secQty).doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).subtract(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
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
									ePrice = inventoryBatchnoEntity.getCostPrice();
									eAmount = inventoryBatchnoEntity.getCostAmount();
								} else {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
									inventoryBatchnoEntity.setIsCheck(0);
									inventoryBatchnoEntity.setCostAmount(costAmount.doubleValue());
									inventoryBatchnoEntity.setCostPrice(costPrice.doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(xQty);
//										inventoryBatchnoEntity.setSmallQty(smallQty);
									}

									inventoryBatchnoEntity.setSecQty(entry.getSecQty());
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
								speedbalEntity.setTranType("61");//单据类型
								speedbalEntity.setDate(entity.getDate());//单据日期
								speedbalEntity.setBillId(id);//单据主键
								speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
								speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
								speedbalEntity.setItemId(entry.getItemId());//商品id
								speedbalEntity.setStockId(entry.getStockId());//仓库id
								speedbalEntity.setBatchNo(entry.getBatchNo());//批号
								speedbalEntity.setQty(entry.getBasicQty());//数量
								speedbalEntity.setSecQty(entry.getSecQty());//辅助数量
								BigDecimal price = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
								speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
								speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
								speedbalEntity.setEPrice(0.0);//结存单价
								speedbalEntity.setEAmount(0.0);//结存金额
								BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
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
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).add(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
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

								//inventoryEntity.setCount(inventoryEntity.getCount() + 1);
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
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
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
								List<TScIcSpeedbalEntity> speedbalEntityList = this.findHql("from TScIcSpeedbalEntity where tranType = ? and billId = ? and billEntryId = ? ", new Object[]{"61", id, entry.getId()});
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
								Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
								newInventory.setBasicQty(qty);
//								qty = qty / Double.parseDouble(ckCoefficient.toString());
//								Double xQty = Math.floor(qty);
//								Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//								newInventory.setQty(xQty);
//								newInventory.setSmallQty(smallQty);
							}
							newInventory.setCostPrice(costPrice.doubleValue());
							newInventory.setCostAmount(costAmount.doubleValue());
							newInventory.setCount(1);
							this.save(newInventory);
							Double ePrice = newInventory.getCostPrice();
							Double eAmount = newInventory.getCostAmount();
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
							ePrice = inventoryBatchnoEntity.getCostPrice();
							eAmount = inventoryBatchnoEntity.getCostAmount();
							//}
							//存货日结记录
							TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
							speedbalEntity.setTranType("61");//单据类型
							speedbalEntity.setDate(entity.getDate());//单据日期
							speedbalEntity.setBillId(id);//单据主键
							speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
							speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
							speedbalEntity.setItemId(entry.getItemId());//商品id
							speedbalEntity.setStockId(entry.getStockId());//仓库id
							speedbalEntity.setBatchNo(entry.getBatchNo());//批号
							speedbalEntity.setQty(entry.getBasicQty());//数量
							speedbalEntity.setSecQty(entry.getSecQty());//辅助数量
							BigDecimal price = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
							speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
							speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
							speedbalEntity.setEPrice(0.0);//结存单价
							speedbalEntity.setEAmount(0.0);//结存金额
							BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
							speedbalEntity.setDiffAmount(0.0);//差异金额
							speedbalEntity.setBlidSrc(entity.getClassIDSrc());//源单类型
							speedbalEntity.setFlag(-1);//出入库标记
							speedbalEntity.setStatus(1);//计算状态
							speedbalEntity.setNegFlag(0);//负结余处理状态
							this.save(speedbalEntity);
						}
					}
					//换货明细
					List<TScIcJhstockbillentry2Entity> entryList2 = findHql("from TScIcJhstockbillentry2Entity where fid = ?", new Object[]{id});
					for (TScIcJhstockbillentry2Entity entry : entryList2) {
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

						Double basicQty = entry.getBasicQty();//基本数量
						BigDecimal secQty = BigDecimal.valueOf(entry.getSecQty());//辅助数量
						BigDecimal costPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);//成本单价
						BigDecimal costAmount = BigDecimal.valueOf(entry.getDiscountAmount());

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
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).add(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
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
								inventoryEntity.setCount(inventoryEntity.getCount() + 1);
								this.saveOrUpdate(inventoryEntity);
								Double ePrice = inventoryEntity.getCostPrice();
								Double eAmount = inventoryEntity.getCostAmount();
								//若存在本商品批号数据 增加库存 否则新增批号库存数据
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setIsCheck(0);
									//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() + basicQty);
									inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).add(secQty).doubleValue());

									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryBatchnoEntity.setBasicQty(BigDecimal.valueOf(inventoryBatchnoEntity.getBasicQty()).add(qty).doubleValue());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
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
									ePrice = inventoryBatchnoEntity.getCostPrice();
									eAmount = inventoryBatchnoEntity.getCostAmount();
								} else {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
									inventoryBatchnoEntity.setIsCheck(0);
									inventoryBatchnoEntity.setCostAmount(costAmount.doubleValue());
									inventoryBatchnoEntity.setCostPrice(costPrice.doubleValue());
									if (BigDecimal.ZERO != ckCoefficient) {
										BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
										inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(xQty);
//										inventoryBatchnoEntity.setSmallQty(smallQty);
									}

									//inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
									inventoryBatchnoEntity.setSecQty(entry.getSecQty());
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
								speedbalEntity.setTranType("61");//单据类型
								speedbalEntity.setDate(entity.getDate());//单据日期
								speedbalEntity.setBillId(id);//单据主键
								speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
								speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
								speedbalEntity.setItemId(entry.getItemId());//商品id
								speedbalEntity.setStockId(entry.getStockId());//仓库id
								speedbalEntity.setBatchNo(entry.getBatchNo());//批号
								speedbalEntity.setQty(entry.getBasicQty());//数量
								speedbalEntity.setSecQty(entry.getSecQty());//辅助数量
								BigDecimal price = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
								speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
								speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
								speedbalEntity.setEPrice(0.0);//结存单价
								speedbalEntity.setEAmount(0.0);//结存金额
								BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
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
								//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
								inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
								//计算箱数
								if (BigDecimal.ZERO != ckCoefficient) {
									BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
									inventoryEntity.setBasicQty(BigDecimal.valueOf(inventoryEntity.getBasicQty()).subtract(qty).doubleValue());
//									qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//									Double xQty = Math.floor(qty.doubleValue());
//									Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//									inventoryEntity.setQty(BigDecimal.valueOf(inventoryEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//									inventoryEntity.setSmallQty(BigDecimal.valueOf(inventoryEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
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
//										qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//										Double xQty = Math.floor(qty.doubleValue());
//										Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//										inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).subtract(BigDecimal.valueOf(xQty)).doubleValue());
//										inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).subtract(BigDecimal.valueOf(smallQty)).doubleValue());
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
								List<TScIcSpeedbalEntity> speedbalEntityList = this.findHql("from TScIcSpeedbalEntity where tranType = ? and billId = ? and billEntryId = ? ", new Object[]{"61", id, entry.getId()});
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
								Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
								newInventory.setBasicQty(qty);
//								qty = qty / Double.parseDouble(ckCoefficient.toString());
//								Double xQty = Math.floor(qty);
//								Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//								newInventory.setQty(xQty);
//								newInventory.setSmallQty(smallQty);
							}
							newInventory.setCostPrice(costPrice.doubleValue());
							newInventory.setCostAmount(costAmount.doubleValue());
							newInventory.setCount(1);
							this.save(newInventory);
							Double ePrice = newInventory.getCostPrice();
							Double eAmount = newInventory.getCostAmount();
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
							TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
							speedbalEntity.setTranType("61");//单据类型
							speedbalEntity.setDate(entity.getDate());//单据日期
							speedbalEntity.setBillId(id);//单据主键
							speedbalEntity.setBillEntryId(entry.getId());//单据分录主键
							speedbalEntity.setBillCreateTime(entity.getCreateDate());//单据创建日期
							speedbalEntity.setItemId(entry.getItemId());//商品id
							speedbalEntity.setStockId(entry.getStockId());//仓库id
							speedbalEntity.setBatchNo(entry.getBatchNo());//批号
							speedbalEntity.setQty(entry.getBasicQty());//数量
							speedbalEntity.setSecQty(entry.getSecQty());//辅助数量
							BigDecimal price = BigDecimal.valueOf(entry.getDiscountPrice()).divide(BigDecimal.valueOf(entry.getCoefficient()), 2, BigDecimal.ROUND_HALF_EVEN);
							speedbalEntity.setPrice(Double.parseDouble(price.toString()));//成本单价
							speedbalEntity.setAmount(entry.getDiscountAmount());//成本金额
							speedbalEntity.setEPrice(0.0);//结存单价
							speedbalEntity.setEAmount(0.0);//结存金额
							BigDecimal diffQty = BigDecimal.valueOf(eAmount).subtract(BigDecimal.valueOf(entry.getCostAmount()));
							speedbalEntity.setDiffAmount(0.0);//差异金额
							speedbalEntity.setBlidSrc(entity.getClassIDSrc());//源单类型
							speedbalEntity.setFlag(1);//出入库标记
							speedbalEntity.setStatus(1);//计算状态
							speedbalEntity.setNegFlag(0);//负结余处理状态
							this.save(speedbalEntity);
						}
					}
				}
			}
		} catch (Exception e){
			//单据状态回写
			TScIcJhstockbillEntity bill= this.getEntity(TScIcJhstockbillEntity.class, id);
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
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, Globals.SC_IC_JHSTOCKBILL_TRANTYPE.toString(), id});
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
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, Globals.SC_IC_JHSTOCKBILL_TRANTYPE.toString(), id});
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

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScIcJhstockbillEntity t){
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
 		sql  = sql.replace("#{diffamount}",String.valueOf(t.getDiffAmount()));
 		sql  = sql.replace("#{curpayamount}",String.valueOf(t.getCurPayAmount()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountId()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIDSrc()));
 		sql  = sql.replace("#{id_src}",String.valueOf(t.getIdSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}