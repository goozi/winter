
package com.qihang.buss.sc.inventory.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.inventory.page.TScAutoChkPage;
import com.qihang.buss.sc.inventory.service.TScIcChkstockbillServiceI;
import com.qihang.buss.sc.sales.entity.TScIcJhstockbillEntity;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalGroupEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScAuditBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.inventory.entity.TScIcChkstockbillEntity;
import com.qihang.buss.sc.inventory.entity.TScIcChkstockbillentryEntity;

import com.qihang.winter.core.constant.Globals;
import com.sun.star.util.NumberFormat;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;

import java.io.Serializable;


@Service("tScIcChkstockbillService")
@Transactional
public class TScIcChkstockbillServiceImpl extends CommonServiceImpl implements TScIcChkstockbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScIcChkstockbillEntity) entity);
 	}
	
	public void addMain(TScIcChkstockbillEntity tScIcChkstockbill,
	        List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryList){
			//保存主信息
			this.save(tScIcChkstockbill);
		
			/**保存-盘点单明细*/
			for(TScIcChkstockbillentryEntity tScIcChkstockbillentry:tScIcChkstockbillentryList){
				//外键设置
				tScIcChkstockbillentry.setFid(tScIcChkstockbill.getId());
				this.save(tScIcChkstockbillentry);
				String updateSql = "update t_sc_ic_inventory_batchno set isCheck = 1 where id = '"+tScIcChkstockbillentry.getIdSrc()+"'";
				this.updateBySqlString(updateSql);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScIcChkstockbill);
	}

	
	public void updateMain(TScIcChkstockbillEntity tScIcChkstockbill,
	        List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryList) {
		//保存主表信息
		this.saveOrUpdate(tScIcChkstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScIcChkstockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-盘点单明细
	    String hql0 = "from TScIcChkstockbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-盘点单明细
		for(TScIcChkstockbillentryEntity oldE:tScIcChkstockbillentryOldList){
			boolean isUpdate = false;
				for(TScIcChkstockbillentryEntity sendE:tScIcChkstockbillentryList){
					//需要更新的明细数据-盘点单明细
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-盘点单明细
		    		super.delete(oldE);
					String updateSql = "update t_sc_ic_inventory_batchno set isCheck = 0 where id = '"+oldE.getIdSrc()+"'";
					this.updateBySqlString(updateSql);
	    		}
	    		
			}
			//3.持久化新增的数据-盘点单明细
			for(TScIcChkstockbillentryEntity tScIcChkstockbillentry:tScIcChkstockbillentryList){
				if(oConvertUtils.isEmpty(tScIcChkstockbillentry.getId())){
					//外键设置
					tScIcChkstockbillentry.setFid(tScIcChkstockbill.getId());
					this.save(tScIcChkstockbillentry);
					String updateSql = "update t_sc_ic_inventory_batchno set isCheck = 1 where id = '"+tScIcChkstockbillentry.getIdSrc()+"'";
					this.updateBySqlString(updateSql);

				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScIcChkstockbill);
	}

	
	public void delMain(TScIcChkstockbillEntity tScIcChkstockbill) {
		//删除主表信息
		this.delete(tScIcChkstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScIcChkstockbill.getId();
		//===================================================================================
		//删除-盘点单明细
	    String hql0 = "from TScIcChkstockbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScIcChkstockbillentryOldList);
		String queryIds = "";
		for(TScIcChkstockbillentryEntity entity : tScIcChkstockbillentryOldList){
			queryIds += "'" + entity.getIdSrc() + "',";
		}
		if(StringUtils.isNotEmpty(queryIds)){
			queryIds = queryIds.substring(0,queryIds.length()-1);
			String updateSql = "update t_sc_ic_inventory_batchno set isCheck = 0 where id in ("+queryIds+")";
			this.updateBySqlString(updateSql);
		}
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcChkstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcChkstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcChkstockbillEntity t){
	 	return true;
 	}

	@Override
	public AjaxJson autoChk(TScAutoChkPage info,String billerId,String sonId) throws ParseException {
		AjaxJson j = new AjaxJson();
		String msg = "";
		String calType = info.getCalType();//盘点分类 0：即时库存 1：截止日期
		if("0".equals(calType)){
			String stockIdList = info.getStockInfo();//仓库信息
			String itemIdList = info.getItemInfo();//商品信息
			String itemIdStr = "";
			if(StringUtils.isNotEmpty(itemIdList)) {
				for (String itemId : itemIdList.split(",")) {
					itemIdStr += "'" + itemId + "',";
				}
			}
			if(StringUtils.isNotEmpty(itemIdStr)) {
				itemIdStr = itemIdStr.substring(0, itemIdStr.length() - 1);
			}
			for(String stockId : stockIdList.split(",")){
				String hql = "from TScIcInventoryBatchnoEntity where stockId = ? ";
				if(StringUtils.isNotEmpty(itemIdStr)){
					hql += " and itemId in ("+itemIdStr+")";
				}
				if(0 == info.getIsZero()){
					hql += " and basicQty > 0";
				}
				List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql(hql,new Object[]{stockId});
				if(inventoryBatchnoEntityList.size() > 0){
					msg = "自动盘点成功";
					Integer maxNum = info.getMaxNum();
					Integer size = inventoryBatchnoEntityList.size();
					if(size > maxNum){
						while (size >0 ) {
							TScIcChkstockbillEntity mainInfo = new TScIcChkstockbillEntity();
							String billNo = BillNoGenerate.getBillNo("160");
							mainInfo.setBillNo(billNo);
							mainInfo.setDate(new Date());
							mainInfo.setStockId(stockId);
							mainInfo.setCancellation(0);
							mainInfo.setCheckState("0");
							mainInfo.setChkType(calType);
							mainInfo.setPdDate(new Date());
							mainInfo.setTranType("160");
							mainInfo.setSonId(sonId);
							mainInfo.setBillerId(billerId);
							mainInfo.setEmpId(info.getEmpId());
							mainInfo.setDeptId(info.getDeptId());
							mainInfo.setIsAuto(1);
							this.save(mainInfo);
							//待审核数据提醒操作
							List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1",new Object[]{mainInfo.getTranType(),sonId});
							if(checkIsMore.size() > 0){
								TScBillAuditStatusEntity tScBillAuditStatusEntity = new TScBillAuditStatusEntity();
								tScBillAuditStatusEntity.setSonId(sonId);
								tScBillAuditStatusEntity.setBillId(mainInfo.getId());
								tScBillAuditStatusEntity.setTranType(mainInfo.getTranType());
								tScBillAuditStatusEntity.setStatus(1);
								super.save(tScBillAuditStatusEntity);
							}
							if(maxNum >= size){
								maxNum = size;
							}
							for (Integer findex = 0; findex < maxNum; findex++) {
								TScIcInventoryBatchnoEntity inventroy = inventoryBatchnoEntityList.get(findex);
								TScIcChkstockbillentryEntity entry = new TScIcChkstockbillentryEntity();
								entry.setFid(mainInfo.getId());
								entry.setItemId(inventroy.getItemId());
								entry.setStockId(inventroy.getStockId());
								entry.setBatchNo(inventroy.getBatchNo());
								TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class, inventroy.getItemId());
								BigDecimal ckCoefficient = BigDecimal.ONE;
								if (null != icitemEntity) {
									List<TScItemPriceEntity> unitList = icitemEntity.getIcitemToPrices();
									if (unitList.size() > 0) {
										for (TScItemPriceEntity unit : unitList) {
											if ("0003".equals(unit.getUnitType())) {
												entry.setSecUnitId(unit.getId());
											}
											if(null != unit.getDefaultCK() && 1 == unit.getDefaultCK()){
												entry.setUnitId(unit.getId());
												entry.setCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
												ckCoefficient = unit.getCoefficient();
											}
										}
									}
								}
								//entry.setQty(inventroy.getQty());
								//entry.setSmallQty(inventroy.getSmallQty());
								entry.setBasicQty(inventroy.getBasicQty());
								Double basicQty = inventroy.getBasicQty();//基础数量
								if(basicQty != 0){
									BigDecimal bQty = BigDecimal.valueOf(basicQty);
									bQty = bQty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
									Double xQty = 0.0;//Math.floor(bQty.doubleValue());
									if(bQty.compareTo(BigDecimal.ZERO) < 0){
										Double bQtyV = bQty.doubleValue();
										bQtyV = -1 * bQtyV;
										xQty = -1 * Math.floor(bQtyV);
									} else {
										xQty = Math.floor(bQty.doubleValue());
									}
									Double smallQty = BigDecimal.valueOf(basicQty).subtract(BigDecimal.valueOf(xQty).multiply(ckCoefficient)).doubleValue();
									entry.setQty(xQty);
//									if(xQty < 0){
//										smallQty = -1 * smallQty;
//									}
									entry.setSmallQty(smallQty);
								} else {
									entry.setQty(0.0);
									entry.setSmallQty(0.0);
								}
								entry.setChkQty(entry.getQty());
								entry.setChkSmallQty(entry.getSmallQty());
								entry.setChkBasicQty(inventroy.getBasicQty());
								entry.setDiffQty(0.0);
								entry.setCostPrice(inventroy.getCostPrice());
								entry.setAmount(inventroy.getCostAmount());
								entry.setChkAmount(inventroy.getCostAmount());
								entry.setDiffAmount(0.0);
								entry.setSecQty(inventroy.getSecQty());
								entry.setFindex(findex);
								entry.setIdSrc(inventroy.getId());
								this.save(entry);
							}
							size = size - maxNum;
						}

					}else{
						TScIcChkstockbillEntity mainInfo = new TScIcChkstockbillEntity();
						String billNo = BillNoGenerate.getBillNo("160");
						mainInfo.setBillNo(billNo);
						mainInfo.setDate(new Date());
						mainInfo.setStockId(stockId);
						mainInfo.setCancellation(0);
						mainInfo.setCheckState("0");
						mainInfo.setChkType(calType);
						mainInfo.setPdDate(new Date());
						mainInfo.setTranType("160");
						mainInfo.setSonId(sonId);
						mainInfo.setBillerId(billerId);
						mainInfo.setEmpId(info.getEmpId());
						mainInfo.setDeptId(info.getDeptId());
						mainInfo.setIsAuto(1);
						this.save(mainInfo);
						//待审核数据提醒操作
						List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1",new Object[]{mainInfo.getTranType(),sonId});
						if(checkIsMore.size() > 0){
							TScBillAuditStatusEntity tScBillAuditStatusEntity = new TScBillAuditStatusEntity();
							tScBillAuditStatusEntity.setSonId(sonId);
							tScBillAuditStatusEntity.setBillId(mainInfo.getId());
							tScBillAuditStatusEntity.setTranType(mainInfo.getTranType());
							tScBillAuditStatusEntity.setStatus(1);
							super.save(tScBillAuditStatusEntity);
						}
						Integer findex = 1;
						for(TScIcInventoryBatchnoEntity inventroy : inventoryBatchnoEntityList){
							TScIcChkstockbillentryEntity entry = new TScIcChkstockbillentryEntity();
							entry.setFid(mainInfo.getId());
							entry.setItemId(inventroy.getItemId());
							entry.setStockId(inventroy.getStockId());
							entry.setBatchNo(inventroy.getBatchNo());
							TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class,inventroy.getItemId());
							BigDecimal ckCoefficient = BigDecimal.ONE;
							if(null != icitemEntity){
								List<TScItemPriceEntity> unitList = icitemEntity.getIcitemToPrices();
								if(unitList.size() > 0){
									for(TScItemPriceEntity unit : unitList){
										if(null != unit.getDefaultCK() && 1 == unit.getDefaultCK()){
											entry.setUnitId(unit.getId());
											entry.setCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
											ckCoefficient = unit.getCoefficient();
										}else if("0003".equals(unit.getUnitType())){
											entry.setSecUnitId(unit.getId());
										}
									}
								}
							}
							//entry.setQty(inventroy.getQty());
							//entry.setSmallQty(inventroy.getSmallQty());
							entry.setBasicQty(inventroy.getBasicQty());
							Double basicQty = inventroy.getBasicQty();//基础数量
							if(basicQty != 0){
								BigDecimal bQty = BigDecimal.valueOf(basicQty);
								bQty = bQty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
								Double xQty = 0.0;//Math.floor(bQty.doubleValue());
								if(bQty.compareTo(BigDecimal.ZERO) < 0){
									Double bQtyV = bQty.doubleValue();
									bQtyV = -1 * bQtyV;
									xQty = -1 * Math.floor(bQtyV);
								} else {
									xQty = Math.floor(bQty.doubleValue());
								}
								Double smallQty = BigDecimal.valueOf(basicQty).subtract(BigDecimal.valueOf(xQty).multiply(ckCoefficient)).doubleValue();
								entry.setQty(xQty);
//								if(xQty < 0){
//									smallQty = -1 * smallQty;
//								}
								entry.setSmallQty(smallQty);
							} else {
								entry.setQty(0.0);
								entry.setSmallQty(0.0);
							}
							entry.setChkQty(entry.getQty());
							entry.setChkSmallQty(entry.getSmallQty());
							entry.setChkBasicQty(inventroy.getBasicQty());
							entry.setDiffQty(0.0);
							entry.setCostPrice(inventroy.getCostPrice());
							entry.setAmount(inventroy.getCostAmount());
							entry.setChkAmount(inventroy.getCostAmount());
							entry.setDiffAmount(0.0);
							entry.setSecQty(inventroy.getSecQty());
							entry.setFindex(findex);
							entry.setIdSrc(inventroy.getId());
							findex++;
							this.save(entry);
						}
					}
				}
				String queryIds = "";
				for(TScIcInventoryBatchnoEntity entity : inventoryBatchnoEntityList){
					queryIds += "'" + entity.getId() + "',";
				}
				if(StringUtils.isNotEmpty(queryIds)){
					queryIds = queryIds.substring(0,queryIds.length()-1);
					String updateSql = "update t_sc_ic_inventory_batchno set isCheck = 1 where id in ("+queryIds+")";
					this.updateBySqlString(updateSql);
				}
			}
			j.setSuccess(true);
		}else{
			String stockIdList = info.getStockInfo();//仓库信息
			String itemIdList = info.getItemInfo();//商品信息
			String itemIdStr = "";
			if(StringUtils.isNotEmpty(itemIdList)) {
				for (String itemId : itemIdList.split(",")) {
					itemIdStr += "'" + itemId + "',";
				}
			}
			if(StringUtils.isNotEmpty(itemIdStr)) {
				itemIdStr = itemIdStr.substring(0, itemIdStr.length() - 1);
			}
			for(String stockId : stockIdList.split(",")){
				String date = info.getDate()+" 23:59:59";
				Date beginDate = AccountUtil.getAccountStartDate();
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd");
				String beginDateStr = sdf.format(beginDate) + " 00:00:00";
				String hql = "from TScIcSpeedbalGroupEntity where stockId = ? and invBatchId <> null and date <= '"+date+"' and date >='"+beginDateStr+"'";
				if(StringUtils.isNotEmpty(itemIdStr)){
					hql += " and itemId in ("+itemIdStr+")";
				}
				if(0 == info.getIsZero()){
					hql += " and qty > 0";
				}
				List<TScIcSpeedbalGroupEntity> speedbalEntityList = this.findHql(hql,new Object[]{stockId});
				if(speedbalEntityList.size() > 0) {
					msg = "自动盘点成功";
					Integer maxNum = info.getMaxNum();
					Integer size = speedbalEntityList.size();
					if(size > maxNum){
						while (size >0 ) {
							TScIcChkstockbillEntity mainInfo = new TScIcChkstockbillEntity();
							String billNo = BillNoGenerate.getBillNo("160");
							mainInfo.setBillNo(billNo);
							mainInfo.setDate(new Date());
							mainInfo.setStockId(stockId);
							mainInfo.setCancellation(0);
							mainInfo.setCheckState("0");
							mainInfo.setChkType(calType);
							mainInfo.setPdDate(sdf.parse(info.getDate()));
							mainInfo.setTranType("160");
							mainInfo.setSonId(sonId);
							mainInfo.setBillerId(billerId);
							mainInfo.setEmpId(info.getEmpId());
							mainInfo.setDeptId(info.getDeptId());
							mainInfo.setIsAuto(1);
							this.save(mainInfo);
							//待审核数据提醒操作
							List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1",new Object[]{mainInfo.getTranType(),sonId});
							if(checkIsMore.size() > 0){
								TScBillAuditStatusEntity tScBillAuditStatusEntity = new TScBillAuditStatusEntity();
								tScBillAuditStatusEntity.setSonId(sonId);
								tScBillAuditStatusEntity.setBillId(mainInfo.getId());
								tScBillAuditStatusEntity.setTranType(mainInfo.getTranType());
								tScBillAuditStatusEntity.setStatus(1);
								super.save(tScBillAuditStatusEntity);
							}
							if(maxNum >= size){
								maxNum = size;
							}
							for (Integer findex = 0; findex < maxNum; findex++) {
								TScIcSpeedbalGroupEntity speed = speedbalEntityList.get(findex);
								//TODO 期初数量 测试 500；
								BigDecimal oldQty = AccountUtil.getInitialNumber(speed.getItemId(),speed.getBatchNo(),stockId);
								BigDecimal oldAmount = AccountUtil.getInitialAmount(speed.getItemId(), speed.getBatchNo(), stockId);
								TScIcChkstockbillentryEntity entry = new TScIcChkstockbillentryEntity();
								entry.setFid(mainInfo.getId());
								entry.setItemId(speed.getItemId());
								entry.setStockId(speed.getStockId());
								entry.setBatchNo(speed.getBatchNo());
								TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class, speed.getItemId());
								BigDecimal ckCoefficient = BigDecimal.ONE;
								if (null != icitemEntity) {
									List<TScItemPriceEntity> unitList = icitemEntity.getIcitemToPrices();
									if (unitList.size() > 0) {
										for (TScItemPriceEntity unit : unitList) {
											if ("0003".equals(unit.getUnitType())) {
												entry.setSecUnitId(unit.getId());
											}
											if(null != unit.getDefaultCK() && 1 == unit.getDefaultCK()){
												entry.setUnitId(unit.getId());
												entry.setCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
												ckCoefficient = unit.getCoefficient();
											}
										}
									}
								}
								BigDecimal qty = BigDecimal.valueOf(speed.getQty());
								qty = qty.add(oldQty);
								oldQty = qty;
								qty = qty.divide(ckCoefficient,2,BigDecimal.ROUND_HALF_EVEN);
								Double xQty = Math.floor(qty.doubleValue());
								Double smallQty = qty.subtract(BigDecimal.valueOf(xQty)).multiply(ckCoefficient).doubleValue();
								entry.setQty(xQty);
								entry.setSmallQty(smallQty);
								entry.setBasicQty(oldQty.doubleValue());
								entry.setChkQty(xQty);
								entry.setChkSmallQty(smallQty);
								entry.setChkBasicQty(oldQty.doubleValue());
								entry.setDiffQty(0.0);
								String amountStr = speed.getAmount();
								amountStr = amountStr.replace(",", "");
								BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
								amount = amount.add(oldAmount);
								BigDecimal price = amount.divide(oldQty,2,BigDecimal.ROUND_HALF_EVEN);

								entry.setCostPrice(price.doubleValue());
								entry.setAmount(amount.doubleValue());
								entry.setChkAmount(amount.doubleValue());
								entry.setDiffAmount(0.0);
								entry.setSecQty(speed.getSecQty());
								entry.setFindex(findex);
								entry.setIdSrc(speed.getInvBatchId());
								this.save(entry);
							}
							size = size - maxNum;
						}

					}else{
						TScIcChkstockbillEntity mainInfo = new TScIcChkstockbillEntity();
						String billNo = BillNoGenerate.getBillNo("160");
						mainInfo.setBillNo(billNo);
						mainInfo.setDate(new Date());
						mainInfo.setStockId(stockId);
						mainInfo.setCancellation(0);
						mainInfo.setCheckState("0");
						mainInfo.setChkType(calType);
						mainInfo.setPdDate(new Date());
						mainInfo.setTranType("160");
						mainInfo.setSonId(sonId);
						mainInfo.setBillerId(billerId);
						mainInfo.setEmpId(info.getEmpId());
						mainInfo.setDeptId(info.getDeptId());
						mainInfo.setIsAuto(1);
						this.save(mainInfo);
						//待审核数据提醒操作
						List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1",new Object[]{mainInfo.getTranType(),sonId});
						if(checkIsMore.size() > 0){
							TScBillAuditStatusEntity tScBillAuditStatusEntity = new TScBillAuditStatusEntity();
							tScBillAuditStatusEntity.setSonId(sonId);
							tScBillAuditStatusEntity.setBillId(mainInfo.getId());
							tScBillAuditStatusEntity.setTranType(mainInfo.getTranType());
							tScBillAuditStatusEntity.setStatus(1);
							super.save(tScBillAuditStatusEntity);
						}
						Integer findex = 1;
						BigDecimal ckCoefficient = BigDecimal.ONE;
						for(TScIcSpeedbalGroupEntity speed : speedbalEntityList){
							//TODO 期初数量 测试 5000；
							BigDecimal oldQty = AccountUtil.getInitialNumber(speed.getItemId(),speed.getBatchNo(),stockId);
							BigDecimal oldAmount = AccountUtil.getInitialAmount(speed.getItemId(),speed.getBatchNo(),stockId);
							TScIcChkstockbillentryEntity entry = new TScIcChkstockbillentryEntity();
							entry.setFid(mainInfo.getId());
							entry.setItemId(speed.getItemId());
							entry.setStockId(speed.getStockId());
							entry.setBatchNo(speed.getBatchNo());
							TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class,speed.getItemId());
							if(null != icitemEntity){
								List<TScItemPriceEntity> unitList = icitemEntity.getIcitemToPrices();
								if(unitList.size() > 0){
									for(TScItemPriceEntity unit : unitList){
										if("0003".equals(unit.getUnitType())){
											entry.setSecUnitId(unit.getId());
										}
										if(null != unit.getDefaultCK() && 1 == unit.getDefaultCK()){
											entry.setUnitId(unit.getId());
											entry.setCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
											ckCoefficient = unit.getCoefficient();
										}
									}
								}
							}
							BigDecimal qty = BigDecimal.valueOf(speed.getQty());
							qty = qty.add(oldQty);
							oldQty = qty;
							qty = qty.divide(ckCoefficient,2,BigDecimal.ROUND_HALF_EVEN);
							Double xQty = Math.floor(qty.doubleValue());
							Double smallQty = qty.subtract(BigDecimal.valueOf(xQty)).multiply(ckCoefficient).doubleValue();
							entry.setQty(xQty);
							entry.setSmallQty(smallQty);
							entry.setBasicQty(oldQty.doubleValue());
							entry.setChkQty(xQty);
							entry.setChkSmallQty(smallQty);
							entry.setChkBasicQty(oldQty.doubleValue());
							entry.setDiffQty(0.0);
							String amountStr = speed.getAmount();
							amountStr = amountStr.replace(",", "");
							BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(amountStr));
							amount = amount.add(oldAmount);
							BigDecimal price = amount.divide(oldQty,2,BigDecimal.ROUND_HALF_EVEN);

							entry.setCostPrice(price.doubleValue());
							entry.setAmount(amount.doubleValue());
							entry.setChkAmount(amount.doubleValue());
							entry.setDiffAmount(0.0);
							entry.setSecQty(speed.getSecQty());
							entry.setFindex(findex);
							entry.setIdSrc(speed.getInvBatchId());
							findex++;
							this.save(entry);
						}
					}
				}
				String queryIds = "";
				for(TScIcSpeedbalGroupEntity entity : speedbalEntityList){
					queryIds += "'" + entity.getInvBatchId() + "',";
				}
				if(StringUtils.isNotEmpty(queryIds)){
					queryIds = queryIds.substring(0,queryIds.length()-1);
					String updateSql = "update t_sc_ic_inventory_batchno set isCheck = 1 where id in ("+queryIds+")";
					this.updateBySqlString(updateSql);
				}
			}
		}
		j.setMsg(msg);
		return j;
	}

	@Override
	public AjaxJson afterAudit(String id, Integer audit, String tranType,String sonId) {
		AjaxJson j = new AjaxJson();
		TScIcChkstockbillEntity entity = this.getEntity(TScIcChkstockbillEntity.class, id);
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where tranType = ? and billId=? order by orderNum desc", new Object[]{Globals.SC_IC_CHK_STOCKBILL.toString(), id});
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?", new Object[]{sonId, Globals.SC_IC_CHK_STOCKBILL.toString(), id});
		try {
			if(auditBillInfoEntityList.size() > 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				if(((auditBillInfoEntity.getOldState() == 1 && auditBillInfoEntity.getNewState() == 2) || (auditBillInfoEntity.getOldState() == 2 && auditBillInfoEntity.getNewState() == 1)) && relationEntityList.get(0).getStatus() >= 0) {
					if (null != entity) {
						String chkType = entity.getChkType();
						List<TScIcChkstockbillentryEntity> entryList = this.findHql("from TScIcChkstockbillentryEntity where fid = ?", new Object[]{id});
						//if("0".equals(chkType)){
						if (1 == audit) {
							Set<String> itemIdSet = new HashSet<String>();//盘点商品集合
							String itemIdStr = "";
							String stockId = entity.getStockId();
							Map<String, Object> qtyMap = new HashMap<String, Object>();//盘点数量集合
							Map<String, Object> amountMap = new HashMap<String, Object>();//盘点金额集合
							for (TScIcChkstockbillentryEntity entry : entryList) {
								TScIcInventoryBatchnoEntity inventoryBatchnoEntity = this.getEntity(TScIcInventoryBatchnoEntity.class, entry.getIdSrc());
								inventoryBatchnoEntity.setQty(entry.getChkQty());//盘点箱数
								inventoryBatchnoEntity.setSmallQty(entry.getChkSmallQty());//盘点散数
								inventoryBatchnoEntity.setBasicQty(entry.getChkBasicQty());//盘点数量
								inventoryBatchnoEntity.setCostAmount(entry.getChkAmount());//盘点金额
								inventoryBatchnoEntity.setCostPrice(entry.getCostPrice());//盘点单价
								this.saveOrUpdate(inventoryBatchnoEntity);
								itemIdSet.add(entry.getItemId());
								String key = entry.getItemId();
								if (null != qtyMap.get(key)) {
									BigDecimal oldQty = (BigDecimal) qtyMap.get(key);
									BigDecimal newQty = BigDecimal.valueOf(entry.getChkBasicQty());
									oldQty = oldQty.add(newQty);
									qtyMap.put(key, oldQty);

								} else {
									qtyMap.put(key, BigDecimal.valueOf(entry.getChkBasicQty()));
								}
								if (null != amountMap.get(key)) {
									BigDecimal oldQty = (BigDecimal) amountMap.get(key);
									BigDecimal newQty = BigDecimal.valueOf(entry.getChkAmount());
									oldQty = oldQty.add(newQty);
									amountMap.put(key, oldQty);

								} else {
									amountMap.put(key, BigDecimal.valueOf(entry.getChkAmount()));
								}
							}
							for (String itemId : itemIdSet) {
								itemIdStr += "'" + itemId + "',";
							}
							itemIdStr = itemIdStr.substring(0, itemIdStr.length() - 1);
							List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId in (" + itemIdStr + ") and stockId = ?", new Object[]{stockId});
							for (TScIcInventoryEntity inventoryEntity : inventoryEntities) {
								String key = inventoryEntity.getItemId();
								BigDecimal chkBasicQty = (BigDecimal) qtyMap.get(key);//数量
								BigDecimal chkAmount = (BigDecimal) amountMap.get(key);//金额
								BigDecimal costPrice = BigDecimal.ZERO;
								if (chkBasicQty.compareTo(BigDecimal.ZERO) > 0) {
									costPrice = chkAmount.divide(chkBasicQty, 2, BigDecimal.ROUND_HALF_EVEN);//单价
								}
								BigDecimal coefficient = BigDecimal.ONE;//换算率
								List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ?", new Object[]{key});
								for (TScItemPriceEntity unit : unitList) {
									if (null != unit.getDefaultCK() && 1 == unit.getDefaultCK()) {
										coefficient = unit.getCoefficient();
									}
								}
//								BigDecimal qty = chkBasicQty.divide(coefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//								Double xQty = Math.floor(qty.doubleValue());
//								Double smallQty = Double.parseDouble(qty.subtract(BigDecimal.valueOf(xQty)).multiply(coefficient).toString());

								//数据保留两位小数
								DecimalFormat df = new DecimalFormat("#.00");
								String basicQ = df.format(Double.parseDouble(chkBasicQty.toString()));
								String costA = df.format(Double.parseDouble(chkAmount.toString()));
								//String Q = df.format(Double.parseDouble(qty.toString()));
								String costP = df.format(Double.parseDouble(costPrice.toString()));

								inventoryEntity.setBasicQty(Double.parseDouble(basicQ));//基本数量
								inventoryEntity.setCostAmount(Double.parseDouble(costA));//金额
								//inventoryEntity.setQty(Double.parseDouble(Q));//箱数
								//inventoryEntity.setSmallQty(Double.parseDouble(df.format(smallQty)));//散数
								inventoryEntity.setCostPrice(Double.parseDouble(costP));//成本单价
								this.saveOrUpdate(inventoryEntity);
							}
						} else {
							//TODO 反审核回复盘点前数据
							Set<String> itemIdSet = new HashSet<String>();//盘点商品集合
							String itemIdStr = "";
							String stockId = entity.getStockId();
							Map<String, Object> qtyMap = new HashMap<String, Object>();//盘点数量集合
							Map<String, Object> amountMap = new HashMap<String, Object>();//盘点金额集合
							for (TScIcChkstockbillentryEntity entry : entryList) {
								TScIcInventoryBatchnoEntity inventoryBatchnoEntity = this.getEntity(TScIcInventoryBatchnoEntity.class, entry.getIdSrc());
								inventoryBatchnoEntity.setQty(entry.getQty());//盘点箱数
								inventoryBatchnoEntity.setSmallQty(entry.getSmallQty());//盘点散数
								inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());//盘点数量
								inventoryBatchnoEntity.setCostAmount(entry.getAmount());//盘点金额
								BigDecimal amount = BigDecimal.valueOf(entry.getAmount());
								BigDecimal basicQty = BigDecimal.valueOf(entry.getBasicQty());
								BigDecimal price = BigDecimal.ZERO;
								if (basicQty.compareTo(BigDecimal.ZERO) > 0) {
									price = amount.divide(basicQty, 2, BigDecimal.ROUND_HALF_EVEN);
								}
								inventoryBatchnoEntity.setCostPrice(price.doubleValue());//盘点单价
								this.saveOrUpdate(inventoryBatchnoEntity);
								itemIdSet.add(entry.getItemId());
								String key = entry.getItemId();
								if (null != qtyMap.get(key)) {
									BigDecimal oldQty = (BigDecimal) qtyMap.get(key);
									BigDecimal newQty = BigDecimal.valueOf(entry.getBasicQty());
									oldQty = oldQty.add(newQty);
									qtyMap.put(key, oldQty);

								} else {
									qtyMap.put(key, BigDecimal.valueOf(entry.getBasicQty()));
								}
								if (null != amountMap.get(key)) {
									BigDecimal oldQty = (BigDecimal) amountMap.get(key);
									BigDecimal newQty = BigDecimal.valueOf(entry.getAmount());
									oldQty = oldQty.add(newQty);
									amountMap.put(key, oldQty);

								} else {
									amountMap.put(key, BigDecimal.valueOf(entry.getAmount()));
								}
							}
							for (String itemId : itemIdSet) {
								itemIdStr += "'" + itemId + "',";
							}
							itemIdStr = itemIdStr.substring(0, itemIdStr.length() - 1);
							List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId in (" + itemIdStr + ") and stockId = ?", new Object[]{stockId});
							for (TScIcInventoryEntity inventoryEntity : inventoryEntities) {
								String key = inventoryEntity.getItemId();
								BigDecimal basicQty = (BigDecimal) qtyMap.get(key);//数量
								BigDecimal amount = (BigDecimal) amountMap.get(key);//金额
								BigDecimal costPrice = BigDecimal.ZERO;
								if (basicQty.compareTo(BigDecimal.ZERO) > 0) {
									costPrice = amount.divide(basicQty, 2, BigDecimal.ROUND_HALF_EVEN);//单价
								}
								BigDecimal coefficient = BigDecimal.ONE;//换算率
								List<TScItemPriceEntity> unitList = this.findHql("from TScItemPriceEntity where priceToIcItem.id = ?", new Object[]{key});
								for (TScItemPriceEntity unit : unitList) {
									if (null != unit.getDefaultCK() && 1 == unit.getDefaultCK()) {
										coefficient = unit.getCoefficient();
									}
								}
								//BigDecimal qty = basicQty.divide(coefficient, 2, BigDecimal.ROUND_HALF_EVEN);
								//Double xQty = Math.floor(Double.parseDouble(qty.toString()));
								//Double smallQty = Double.parseDouble(qty.subtract(BigDecimal.valueOf(xQty)).multiply(coefficient).toString());

								//数据保留两位小数
								DecimalFormat df = new DecimalFormat("#.00");
								String basicQ = df.format(Double.parseDouble(basicQty.toString()));
								String costA = df.format(Double.parseDouble(amount.toString()));
								//String Q = df.format(Double.parseDouble(qty.toString()));
								String costP = df.format(Double.parseDouble(costPrice.toString()));

								inventoryEntity.setBasicQty(Double.parseDouble(basicQ));//基本数量
								inventoryEntity.setCostAmount(Double.parseDouble(costA));//金额
								//inventoryEntity.setQty(Double.parseDouble(Q));//箱数
								//inventoryEntity.setSmallQty(Double.parseDouble(df.format(smallQty)));//散数
								inventoryEntity.setCostPrice(Double.parseDouble(costP));//成本单价
								this.saveOrUpdate(inventoryEntity);
							}
						}
						//}
					}
				}
			}
		} catch (Exception e){
			//单据状态回写
			TScIcChkstockbillEntity bill= this.getEntity(TScIcChkstockbillEntity.class, id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(bill.getCheckState().equals(2)) {
				//审核异常
				TSAuditRelationEntity delRelationEntity = relationEntityList.get(0);
				super.delete(delRelationEntity);
				if(relationEntityList.size() > 1) {
					//前一次审核内容
					TSAuditRelationEntity lastAuditInfo = relationEntityList.get(1);
					bill.setCheckState((Integer.parseInt(bill.getCheckState()) - 1)+"");
					bill.setCheckerId(lastAuditInfo.getAuditorId());
					bill.setCheckDate(sdf.format(lastAuditInfo.getAuditDate()));
				} else {
					bill.setCheckState("0");
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
				bill.setCheckDate(sdf.format(endRelationEntity.getAuditDate()));

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
 	public String replaceVal(String sql,TScIcChkstockbillEntity t){
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
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockId()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{pddate}",String.valueOf(t.getPdDate()));
 		sql  = sql.replace("#{chktype}",String.valueOf(t.getChkType()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}