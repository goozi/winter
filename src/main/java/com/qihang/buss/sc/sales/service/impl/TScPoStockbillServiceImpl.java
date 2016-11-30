
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.entity.TScPoOrderentryEntity;
import com.qihang.buss.sc.sales.entity.TScPoStockBillViewEntity;
import com.qihang.buss.sc.sales.service.TScPoStockbillServiceI;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScAuditBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sales.entity.TScPoStockbillEntity;
import com.qihang.buss.sc.sales.entity.TScPoStockbillentryEntity;

import com.qihang.winter.web.system.pojo.base.TSDepart;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("tScPoStockbillService")
@Transactional
public class TScPoStockbillServiceImpl extends CommonServiceImpl implements TScPoStockbillServiceI {

	@Autowired
	private SessionFactory sessionFactory;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScPoStockbillEntity) entity);
 	}
	
	public void addMain(TScPoStockbillEntity tScPoStockbill,
	        List<TScPoStockbillentryEntity> tScPoStockbillentryList){
			//保存主信息
			String idSrc = tScPoStockbill.getIdSrc();
			String classIdSrc = tScPoStockbill.getClassIDSrc();
		    tScPoStockbill.setCheckState(0);
			//List<TScPoStockbillEntity> entityList = this.findHql("from TScPoStockbillEntity where idSrc = ? and checkState = 2", new Object[]{idSrc});
			this.save(tScPoStockbill);

		    //源单类型为采购订单
		    if("51".equals(classIdSrc)){
				Double allAmount = tScPoStockbill.getAllAmount();
			}
			/**保存-采购出入库单明细表*/
			for(TScPoStockbillentryEntity tScPoStockbillentry:tScPoStockbillentryList){
				//外键设置
				tScPoStockbillentry.setFid(tScPoStockbill.getId());
				tScPoStockbillentry.setCommitQty(0.0);
				Double disPrice = tScPoStockbillentry.getDiscountPrice();
				if(null != disPrice) {
					Double costPrice = disPrice;
					Double qty = tScPoStockbillentry.getQty();
					Double costAmount = tScPoStockbillentry.getDiscountAmount();
					DecimalFormat def = new DecimalFormat("#.00");
					costPrice = Double.parseDouble(def.format(costPrice));
					costAmount = Double.parseDouble(def.format(costAmount));
					tScPoStockbillentry.setCostPrice(costPrice);
					tScPoStockbillentry.setCostAmount(costAmount);
				}
				this.save(tScPoStockbillentry);
				//订单数量回写
				if("52".equals(tScPoStockbill.getTranType())) {
					String classId = tScPoStockbillentry.getClassIDSrc();
					String entryId = tScPoStockbillentry.getEntryIdOrder();
					if (StringUtils.isNotEmpty(entryId) && "51".equals(classId)) {
						String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+" + tScPoStockbillentry.getQty() + " where id = '" + entryId + "'";
						updateBySqlString(updateSql);
					}
				}else if("53".equals(tScPoStockbill.getTranType())){
					String entryId = tScPoStockbillentry.getEntryIdOrder();
					String entryIdSrc = tScPoStockbillentry.getEntryIdSrc();
//					//退货减少订单收货数量
//					if (StringUtils.isNotEmpty(entryId)) {
//						String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-" + tScPoStockbillentry.getQty() + " where id = '" + entryId + "'";
//						updateBySqlString(updateSql);
//					}
					//退货记录入库单明细退货数量
					if(StringUtils.isNotEmpty(entryIdSrc)){
						String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty+" + tScPoStockbillentry.getQty() + " where id = '" + entryIdSrc + "'";
						updateBySqlString(updateSql);
					}
				}
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScPoStockbill);
	}

	
	public void updateMain(TScPoStockbillEntity tScPoStockbill,
	        List<TScPoStockbillentryEntity> tScPoStockbillentryList) {
		//保存主表信息
		this.saveOrUpdate(tScPoStockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScPoStockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-采购出入库单明细表
	    String hql0 = "from TScPoStockbillentryEntity where 1 = 1 AND fid = ? ";
	    List<TScPoStockbillentryEntity> tScPoStockbillentryOldList = this.findHql(hql0, id0);
		//2.筛选更新明细数据-采购出入库单明细表
		for(TScPoStockbillentryEntity oldE:tScPoStockbillentryOldList){
			boolean isUpdate = false;
				for(TScPoStockbillentryEntity sendE:tScPoStockbillentryList){
					//需要更新的明细数据-采购出入库单明细表
					if(oldE.getId().equals(sendE.getId())){
						//修改订单已执行量
						if(StringUtils.isNotEmpty(oldE.getEntryIdSrc()) && oldE.getEntryIdSrc().equals(sendE.getEntryIdSrc())){
							Double oldQty = oldE.getQty();
							Double newQty = sendE.getQty();
							Double changeValue = oldQty-newQty;
							if("52".equals(tScPoStockbill.getTranType())) {
								if("51".equals(oldE.getClassIDSrc())) {
									String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-(" + changeValue + ") where id = '" + oldE.getEntryIdOrder() + "'";
									this.updateBySqlString(updateSql);
								}
							}else if("53".equals(tScPoStockbill.getTranType())){
								//String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+("+changeValue+") where id = '"+oldE.getEntryIdOrder()+"'";
								//this.updateBySqlString(updateSql);

								String entryId = oldE.getEntryIdSrc();
								if(StringUtils.isNotEmpty(entryId)){
									String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty-("+changeValue+") where id = '"+entryId+"'";
									this.updateBySqlString(updateSql);
								}
							}
						}
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							//设置成本单价、成本金额
							Double disPrice = oldE.getDiscountPrice();
							Double costPrice = disPrice;
							Double qty = oldE.getQty();
							Double costAmount = oldE.getDiscountAmount();
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

					//修改订单已执行量
					if(StringUtils.isNotEmpty(oldE.getEntryIdOrder())){
						if("52".equals(tScPoStockbill.getTranType())) {
							if("51".equals(oldE.getClassIDSrc())) {
								String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-(" + oldE.getQty() + ") where id = '" + oldE.getEntryIdOrder() + "'";
								this.updateBySqlString(updateSql);
							}
						}else if("53".equals(tScPoStockbill.getTranType())){
//							String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+("+oldE.getBasicQty()+") where id = '"+oldE.getEntryIdOrder()+"'";
//							this.updateBySqlString(updateSql);

							String entryId = oldE.getEntryIdSrc();
							if(StringUtils.isNotEmpty(entryId)){
								String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty -("+oldE.getQty()+") where id = '"+entryId+"'";
								this.updateBySqlString(updateSql);
							}
						}
					}
					//如果数据库存在的明细，前台没有传递过来则是删除-采购出入库单明细表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-采购出入库单明细表
			for(TScPoStockbillentryEntity tScPoStockbillentry:tScPoStockbillentryList){
				if(oConvertUtils.isEmpty(tScPoStockbillentry.getId())){
					//修改订单已执行量
					if(StringUtils.isNotEmpty(tScPoStockbillentry.getEntryIdOrder())){
						if("52".equals(tScPoStockbill.getTranType())) {
							if("51".equals(tScPoStockbillentry.getClassIDSrc())) {
								String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+(" + tScPoStockbillentry.getQty() + ") where id = '" + tScPoStockbillentry.getEntryIdOrder() + "'";
								this.updateBySqlString(updateSql);
							}
						}else if("53".equals(tScPoStockbill.getTranType())){
							//String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-("+tScPoStockbillentry.getQty()+") where id = '"+tScPoStockbillentry.getEntryIdOrder()+"'";
							//this.updateBySqlString(updateSql);

							if(StringUtils.isNotEmpty(tScPoStockbillentry.getEntryIdSrc())){
								String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty+("+tScPoStockbillentry.getQty()+") where id = '"+tScPoStockbillentry.getEntryIdSrc()+"'";
								this.updateBySqlString(updateSql);
							}
						}
					}
					//外键设置
					tScPoStockbillentry.setFid(tScPoStockbill.getId());
					//设置成本单价、成本金额
					Double disPrice = tScPoStockbillentry.getDiscountPrice();
					Double costPrice = disPrice;
					Double qty = tScPoStockbillentry.getQty();
					Double costAmount = tScPoStockbillentry.getDiscountAmount();
					DecimalFormat def = new DecimalFormat("#.00");
					costPrice = Double.parseDouble(def.format(costPrice));
					costAmount = Double.parseDouble(def.format(costAmount));
					tScPoStockbillentry.setCostPrice(costPrice);
					tScPoStockbillentry.setCostAmount(costAmount);
					this.save(tScPoStockbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScPoStockbill);
	}

	
	public void delMain(TScPoStockbillEntity tScPoStockbill) {
		//删除主表信息
		this.delete(tScPoStockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScPoStockbill.getId();
		//===================================================================================
		//删除-采购出入库单明细表
	    String hql0 = "from TScPoStockbillentryEntity where 1 = 1 AND fid = ? ";
	    List<TScPoStockbillentryEntity> tScPoStockbillentryOldList = this.findHql(hql0,id0);
		//修改订单已执行量
		for(TScPoStockbillentryEntity entity : tScPoStockbillentryOldList){
			if(StringUtils.isNotEmpty(entity.getEntryIdSrc())) {
				if ("52".equals(tScPoStockbill.getTranType())) {
					if("51".equals(entity.getClassIDSrc())) {
						String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-(" + entity.getQty() + ") where id = '" + entity.getEntryIdOrder() + "'";
						this.updateBySqlString(updateSql);
					}
				}else if("53".equals(tScPoStockbill.getTranType())){
				//	String entryId = entity.getEntryIdOrder();
					String entryIdSrc = entity.getEntryIdSrc();
//					if(StringUtils.isNotEmpty(entryId)) {
//						String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+(" + entity.getQty() + ") where id = '" + entryId + "'";
//						this.updateBySqlString(updateSql);
//					}
					if(StringUtils.isNotEmpty(entryIdSrc)){
						String updateSql = "update t_sc_po_stockbillentry set commitQty = commitQty-(" + entity.getQty() + ") where id = '" + entryIdSrc + "'";
						this.updateBySqlString(updateSql);
					}
				}
			}
			//String updateInventory = "update t_sc_ic_inventory set count = count-1 where itemId = '"+entity.getItemId()+"'";
			//this.updateBySqlString(updateInventory);
//			TScIcInventoryEntity inventoryEntity = this.findUniqueByProperty(TScIcInventoryEntity.class,"itemId",entity.getItemId());
//			if(null != inventoryEntity){
//				inventoryEntity.setCount(inventoryEntity.getCount()-1);
//				this.saveOrUpdate(inventoryEntity);
//			}
//			if(StringUtils.isNotEmpty(entity.getBatchNo())){
//				List<TScIcInventoryBatchnoEntity> batchNoList = this.findHql("from TScIcInventoryBatchnoEntity where itemId=? and batchNo = ?",new Object[]{entity.getItemId(),entity.getBatchNo()});
//				if(batchNoList.size() > 0){
//					TScIcInventoryBatchnoEntity batchnoEntity = batchNoList.get(0);
//					batchnoEntity.setCount(batchnoEntity.getCount()-1);
//					this.saveOrUpdate(batchnoEntity);
//				}
//				//String updateBatchNOInventory = "update t_sc_ic_inventory_batchno set count = count-1 where itemId = '"+entity.getItemId()+"' and batchNo = '"+entity.getBatchNo()+"'";
//				//this.updateBySqlString(updateBatchNOInventory);
//			}
		}
		this.deleteAllEntitie(tScPoStockbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPoStockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPoStockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPoStockbillEntity t){
	 	return true;
 	}

	@Override
	public AjaxJson cancelBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		for(String id : idList){
			idStr += "'"+id+"',";
			//修改订单已执行量
			TScPoStockbillEntity stockBill = this.getEntity(TScPoStockbillEntity.class,id);
			List<TScPoStockbillentryEntity> tScPoStockbillentryOldList = this.findHql("from TScPoStockbillentryEntity where fid = ? ",new Object[]{id});
			for(TScPoStockbillentryEntity entity : tScPoStockbillentryOldList){
				if(StringUtils.isNotEmpty(entity.getEntryIdOrder())) {
					//回写采购订单已执行数量
					if ("51".equals(entity.getClassIDSrc()) && "52".equals(stockBill.getTranType())) {
						String updateSql = "update t_sc_po_orderentry set stockQty = stockQty-(" + entity.getQty() + ") where id = '" + entity.getEntryIdOrder() + "'";
						this.updateBySqlString(updateSql);
					}else if("52".equals(entity.getClassIDSrc()) && "53".equals(stockBill.getTranType())){
						String updateSql = "update T_SC_Po_StockBillEntry set commitQty = commitQty+(" + entity.getQty() + ") where id = '" + entity.getEntryIdOrder() + "'";
						this.updateBySqlString(updateSql);
					}
				}
			}
		}
		if(idStr.length() > 0){
			idStr = idStr.substring(0,idStr.length()-1);
		}
		String updateSql = "update t_sc_po_stockbill set cancellation = 1 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("作废失败："+e.getMessage());
		}
		return j;
	}

	@Override
	public AjaxJson enableBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		Boolean isAllowEnable = true;
		for(String id : idList){
			idStr += "'"+id+"',";
			//校验引用的采购订单执行数量是否超出订单数量
			List<TScPoStockbillentryEntity> tScPoStockbillentryOldList = this.findHql("from TScPoStockbillentryEntity where fid = ? ",new Object[]{id});
			for(TScPoStockbillentryEntity entity : tScPoStockbillentryOldList){
				if(StringUtils.isNotEmpty(entity.getEntryIdOrder())) {
					if ("51".equals(entity.getClassIDSrc())) {
						TScPoOrderentryEntity orderentryEntity = this.getEntity(TScPoOrderentryEntity.class,entity.getEntryIdOrder());
						Double stockQty = orderentryEntity.getStockQty();
						Double billQty = orderentryEntity.getBasicQty();
						Double enableQty = entity.getBasicQty();
						//订单已执行数量溢出则不可反作废
						if(enableQty+stockQty <= billQty) {
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
				TScPoStockbillEntity stockBill = this.getEntity(TScPoOrderentryEntity.class,id);
				List<TScPoStockbillentryEntity> tScPoStockbillentryOldList = this.findHql("from TScPoStockbillentryEntity where fid = ? ", new Object[]{id});
				for(TScPoStockbillentryEntity entity : tScPoStockbillentryOldList){
					if(StringUtils.isNotEmpty(entity.getEntryIdOrder())) {
						//回写采购订单已执行数量
						if ("51".equals(entity.getClassIDSrc()) && "52".equals(stockBill.getTranType())) {
							String updateSql = "update t_sc_po_orderentry set stockQty = stockQty+(" + entity.getQty() + ") where id = '" + entity.getEntryIdOrder() + "'";
							this.updateBySqlString(updateSql);
						}else if("52".equals(entity.getClassIDSrc()) && "53".equals(stockBill.getTranType())){
							String updateSql = "update T_SC_Po_StockBillEntry set commitQty = commitQty-(" + entity.getQty() + ") where id = '" + entity.getEntryIdOrder() + "'";
							this.updateBySqlString(updateSql);
						}
					}
				}
			}
			if (idStr.length() > 0) {
				idStr = idStr.substring(0, idStr.length() - 1);
			}
			String updateSql = "update t_sc_po_stockbill set cancellation = 0 where id in (" + idStr + ")";
			try {
				updateBySqlString(updateSql);
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("作废失败：" + e.getMessage());
			}
		}
		return j;
	}

	/**
	 * 选单获取明细数据
	 * @param ids
	 * @param tranType
	 * @return
	 */
	@Override
	public List<TScPoStockbillentryEntity> loadEntryList(String ids, String tranType) {
		List<TScPoStockbillentryEntity> entryList = new ArrayList<TScPoStockbillentryEntity>();
		String[] idList = ids.split(",");
		for(String id : idList) {
			TScPoStockbillEntity main = this.getEntity(TScPoStockbillEntity.class, id);
			List<TScPoStockbillentryEntity> entities = this.findHql("from TScPoStockbillentryEntity where fid = ?",new Object[]{id});
			for (TScPoStockbillentryEntity entry : entities) {
				sessionFactory.getCurrentSession().evict(entry);
				TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class, entry.getItemId());
				if(null != icitemEntity) {
					entry.setItemNo(icitemEntity.getNumber());
					entry.setItemName(icitemEntity.getName());
					entry.setModel(icitemEntity.getModel());
					entry.setKfDateType(icitemEntity.getKfDateType());
					entry.setKfPeriod(icitemEntity.getKfPeriod());
					entry.setIsKFPeriod(icitemEntity.getIskfPeriod());
					entry.setBatchManager(icitemEntity.getBatchManager());
					entry.setFreeGifts_(entry.getFreeGifts());
					if (icitemEntity.getIcitemToPrices().size() > 0) {
						List<TScItemPriceEntity> unitList = icitemEntity.getIcitemToPrices();
						for (TScItemPriceEntity unit : unitList) {
							if (unit.getId().equals(entry.getUnitId())) {
								entry.setBarCode(unit.getBarCode());
							}
							if ("0001".equals(unit.getUnitType())) {
								entry.setBasicCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
							}
						}
					}
				}
				entry.setClassIDSrc(main.getTranType());
				entry.setIdSrc(main.getId());
				entry.setBillNoSrc(main.getBillNo());
				Double billQty = entry.getBasicQty();
				entry.setBillQty(billQty);
				Double allowQty = entry.getBasicQty()-(entry.getCommitQty()==null ? 0 : entry.getCommitQty());
				if(allowQty > 0 ) {
					entry.setQty(allowQty);
					entryList.add(entry);
				}
			}
		}
//		for(TScPoOrderentryEntity entity : entryList){
//
//		}
		return entryList;
	}

	@Override
	public AjaxJson afterAudit(String id, Integer audit,String tranType,String sonId) {
		TScPoStockbillEntity entity = this.getEntity(TScPoStockbillEntity.class,id);
		DecimalFormat def = new DecimalFormat("#.00");
		AjaxJson j = new AjaxJson();
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where tranType = ? and billId=? order by orderNum desc", new Object[]{entity.getTranType(), id});
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?", new Object[]{sonId, tranType, id});
		try {
			if (auditBillInfoEntityList.size() > 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				if (((auditBillInfoEntity.getOldState() == 1 && auditBillInfoEntity.getNewState() == 2) || (auditBillInfoEntity.getOldState() == 2 && auditBillInfoEntity.getNewState() == 1)) && relationEntityList.get(0).getStatus() >= 0) {
					List<TScPoStockbillentryEntity> entryList = findHql("from TScPoStockbillentryEntity where fid = ?", new Object[]{id});
					for (TScPoStockbillentryEntity entry : entryList) {
						String itemId = entry.getItemId();
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
							if ("52".equals(tranType) && 1 == audit) {
								BigDecimal coefficient = BigDecimal.ONE;
								if (null != unit.getCoefficient()) {
									coefficient = unit.getCoefficient();
								}
								BigDecimal coe = BigDecimal.valueOf(entry.getCoefficient());
								BigDecimal basicPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(coe, 2, BigDecimal.ROUND_HALF_EVEN);//基本单价
								BigDecimal cgLastPrice = basicPrice.multiply(coefficient);
								unit.setCgLatestPrice(cgLastPrice);
								super.saveOrUpdate(unit);
							} else if ("52".equals(tranType) && 0 == audit) {
								List<TScPoStockBillViewEntity> lastStockInfo = super.findHql("from TScPoStockBillViewEntity where entryItemId = ? and unitId = ? and tranType=52 and checkState=2 and sonId = ? and id <> ? order by date desc", new Object[]{entry.getItemId(), unit.getId(), entity.getSonId(), entity.getId()});
								if (lastStockInfo.size() > 0) {
									TScPoStockBillViewEntity lastStock = lastStockInfo.get(0);
									BigDecimal coefficient = BigDecimal.ONE;
									if (null != unit.getCoefficient()) {
										coefficient = unit.getCoefficient();
									}
									BigDecimal coe = BigDecimal.valueOf(entry.getCoefficient());
									BigDecimal basicPrice = BigDecimal.valueOf(entry.getDiscountPrice()).divide(coe, 2, BigDecimal.ROUND_HALF_EVEN);//基本单价
									BigDecimal cgLastPrice = basicPrice.multiply(coefficient);
									unit.setCgLatestPrice(cgLastPrice);
								} else {
									unit.setCgLatestPrice(BigDecimal.ZERO);
								}
								super.saveOrUpdate(unit);
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
							queryInfo = new Object[]{itemId, entry.getStockId(), entry.getBatchNo()};
						}
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql(hql, queryInfo);
						if (null != inventoryEntity) {
							//若为审核操作
							if (1 == audit) {
								//进货单
								if ("52".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockId())) {
										inventoryEntity.setStockId(entry.getStockId());
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
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryBatchnoEntity.setQty(BigDecimal.valueOf(inventoryBatchnoEntity.getQty()).add(BigDecimal.valueOf(xQty)).doubleValue());
//											inventoryBatchnoEntity.setSmallQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSmallQty()).add(BigDecimal.valueOf(smallQty)).doubleValue());
										}
										//inventoryBatchnoEntity.setCostPrice((inventoryBatchnoEntity.getCostPrice() + costPrice)/2);
										inventoryBatchnoEntity.setCostAmount(BigDecimal.valueOf(inventoryBatchnoEntity.getCostAmount()).add(costAmount).doubleValue());
										//计算平均单价
										amount = inventoryBatchnoEntity.getCostAmount() == 0.0 ? costAmount.doubleValue() : inventoryBatchnoEntity.getCostAmount();
										basicQ = inventoryBatchnoEntity.getBasicQty();
										if (!basicQ.equals(0.0)) {
											Double avgPrice = Double.parseDouble(def.format(amount / basicQ));
											inventoryBatchnoEntity.setCostPrice(avgPrice);
										} else {
											inventoryBatchnoEntity.setCostPrice(0.0);
										}
										inventoryBatchnoEntity.setCount(inventoryBatchnoEntity.getCount() + 1);
										this.saveOrUpdate(inventoryBatchnoEntity);
										ePrice = inventoryBatchnoEntity.getCostPrice();
										eAmount = inventoryBatchnoEntity.getCostAmount();
									} else {
										TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
										inventoryBatchnoEntity.setIsCheck(0);
										inventoryBatchnoEntity.setCostAmount(costAmount.doubleValue());
										inventoryBatchnoEntity.setCostPrice(costPrice.doubleValue());
										if (BigDecimal.ZERO != ckCoefficient) {
//									Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
									inventoryBatchnoEntity.setBasicQty(entry.getBasicQty());
//									qty = qty / Double.parseDouble(ckCoefficient.toString());
//									Double xQty = Math.floor(qty);
//									Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//									inventoryBatchnoEntity.setSmallQty(smallQty);
//									inventoryBatchnoEntity.setQty(xQty);
											BigDecimal qty = BigDecimal.valueOf(entry.getBasicQty());
											inventoryEntity.setBasicQty(entry.getBasicQty());
//											qty = qty.divide(ckCoefficient, 2, BigDecimal.ROUND_HALF_EVEN);
//											Double xQty = Math.floor(qty.doubleValue());
//											Double smallQty = (qty.subtract(BigDecimal.valueOf(xQty))).multiply(ckCoefficient).doubleValue();
//											inventoryEntity.setQty(xQty);
//											inventoryEntity.setSmallQty(smallQty);
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
									speedbalEntity.setTranType(tranType);//单据类型
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
								//退货单
								else if ("53".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockId())) {
										inventoryEntity.setStockId(entry.getStockId());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
									//计算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
//								Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
//								inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - qty);
//								qty = qty / Double.parseDouble(ckCoefficient.toString());
//								Double xQty = Math.floor(qty);
//								Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//								inventoryEntity.setQty(inventoryEntity.getQty() - xQty);
//								inventoryEntity.setSmallQty(inventoryEntity.getSmallQty() - smallQty);
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
									Double ePrice = inventoryEntity.getCostPrice();
									Double eAmount = inventoryEntity.getCostAmount();
									if (inventoryBatchnoEntityList.size() > 0) {
										TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
										inventoryBatchnoEntity.setIsCheck(0);
										//inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - basicQty);
										inventoryBatchnoEntity.setSecQty(BigDecimal.valueOf(inventoryBatchnoEntity.getSecQty()).subtract(secQty).doubleValue());
										if (BigDecimal.ZERO != ckCoefficient) {
//									Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
//									inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - qty);
//									qty = qty / Double.parseDouble(ckCoefficient.toString());
//									Double xQty = Math.floor(qty);
//									Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//									inventoryBatchnoEntity.setQty(inventoryBatchnoEntity.getQty() - xQty);
//									inventoryBatchnoEntity.setSmallQty(inventoryBatchnoEntity.getSmallQty() - smallQty);
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
										this.saveOrUpdate(inventoryBatchnoEntity);
										ePrice = inventoryBatchnoEntity.getCostPrice();
										eAmount = inventoryBatchnoEntity.getCostAmount();
									}
									//存货日结记录
									TScIcSpeedbalEntity speedbalEntity = new TScIcSpeedbalEntity();
									speedbalEntity.setTranType(tranType);//单据类型
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
							//反审核
							else {
								//进货单
								if ("52".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockId())) {
										inventoryEntity.setStockId(entry.getStockId());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).subtract(secQty).doubleValue());
									//计算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
//								Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
//								inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - qty);
//								qty = qty / Double.parseDouble(ckCoefficient.toString());
//								Double xQty = Math.floor(qty);
//								Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//								inventoryEntity.setQty(inventoryEntity.getQty() - xQty);
//								inventoryEntity.setSmallQty(inventoryEntity.getSmallQty() - smallQty);
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
//									Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
//									inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() - qty);
//									qty = qty / Double.parseDouble(ckCoefficient.toString());
//									Double xQty = Math.floor(qty);
//									Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//									inventoryBatchnoEntity.setQty(inventoryBatchnoEntity.getQty() - xQty);
//									inventoryBatchnoEntity.setSmallQty(inventoryBatchnoEntity.getSmallQty() - smallQty);
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
											//inventoryBatchnoEntity.setCostAmount(Double.parseDouble(def.format(inventoryBatchnoEntity.getCostAmount()-amount)));
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
								}
								//退货单
								else if ("53".equals(tranType)) {
									if (StringUtils.isNotEmpty(entry.getStockId())) {
										inventoryEntity.setStockId(entry.getStockId());
									}
									//inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + basicQty);
									inventoryEntity.setSecQty(BigDecimal.valueOf(inventoryEntity.getSecQty()).add(secQty).doubleValue());
									//计算箱数
									if (BigDecimal.ZERO != ckCoefficient) {
//								Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
//								inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + qty);
//								qty = qty / Double.parseDouble(ckCoefficient.toString());
//								Double xQty = Math.floor(qty);
//								Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//								inventoryEntity.setQty(inventoryEntity.getQty() + xQty);
//								inventoryEntity.setSmallQty(inventoryEntity.getSmallQty() + smallQty);
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
//									Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
//									inventoryBatchnoEntity.setBasicQty(inventoryBatchnoEntity.getBasicQty() + qty);
//									qty = qty / Double.parseDouble(ckCoefficient.toString());
//									Double xQty = Math.floor(qty);
//									Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//									inventoryBatchnoEntity.setQty(inventoryBatchnoEntity.getQty() + xQty);
//									inventoryBatchnoEntity.setSmallQty(inventoryBatchnoEntity.getSmallQty() + smallQty);
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
							if ("52".equals(tranType) && 1 == audit) {
								TScIcInventoryEntity newInventory = new TScIcInventoryEntity();
								newInventory.setItemId(itemId);
								if (StringUtils.isNotEmpty(entry.getStockId())) {
									newInventory.setStockId(entry.getStockId());
								}
								//newInventory.setBasicQty(entry.getBasicQty());
								newInventory.setSecQty(entry.getSecQty());
								if (BigDecimal.ZERO != ckCoefficient) {
									Double qty = entry.getBasicQty() / Double.parseDouble(basicCoefficient.toString());
									newInventory.setBasicQty(qty);
//									qty = qty / Double.parseDouble(ckCoefficient.toString());
//									Double xQty = Math.floor(qty);
//									Double smallQty = (qty - xQty) * Double.parseDouble(ckCoefficient.toString());
//									newInventory.setQty(xQty);
//									newInventory.setSmallQty(smallQty);
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
								speedbalEntity.setTranType(tranType);//单据类型
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
			}
		} catch (Exception e){
			//单据状态回写
			TScPoStockbillEntity bill= this.getEntity(TScPoStockbillEntity.class, id);
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
 	public String replaceVal(String sql,TScPoStockbillEntity t){
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
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockId()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateAmount()));
 		sql  = sql.replace("#{curpayamount}",String.valueOf(t.getCurPayAmount()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountId()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIDSrc()));
 		sql  = sql.replace("#{id_src}",String.valueOf(t.getIdSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}