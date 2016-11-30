
package com.qihang.buss.sc.distribution.service.impl;
import com.qihang.buss.sc.distribution.entity.TScDrpStockbillEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpStockbillentryEntity;
import com.qihang.buss.sc.distribution.service.TScDrpStockbillServiceI;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.entity.TScIcXsstockbillEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScAuditBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Service("tScDrpStockbillService")
@Transactional
public class TScDrpStockbillServiceImpl extends CommonServiceImpl implements TScDrpStockbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScDrpStockbillEntity)entity);
 	}
	
	public void addMain(TScDrpStockbillEntity tScDrpStockbill,
	        List<TScDrpStockbillentryEntity> tScDrpStockbillentryList){
			//保存主信息
			this.save(tScDrpStockbill);
		
			/**保存-商品信息*/
			for(TScDrpStockbillentryEntity tScDrpStockbillentry:tScDrpStockbillentryList){
				//外键设置
				tScDrpStockbillentry.setFid(tScDrpStockbill.getId());
				System.out.println("indexNumber:"+tScDrpStockbillentry.getIndexNumber());
				this.save(tScDrpStockbillentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScDrpStockbill);
	}

	
	public void updateMain(TScDrpStockbillEntity tScDrpStockbill,
	        List<TScDrpStockbillentryEntity> tScDrpStockbillentryList) {
		//保存主表信息
		this.saveOrUpdate(tScDrpStockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScDrpStockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-商品信息
	    String hql0 = "from TScDrpStockbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScDrpStockbillentryEntity> tScDrpStockbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-商品信息
		for(TScDrpStockbillentryEntity oldE:tScDrpStockbillentryOldList){
			boolean isUpdate = false;
				for(TScDrpStockbillentryEntity sendE:tScDrpStockbillentryList){
					//需要更新的明细数据-商品信息
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-商品信息
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-商品信息
			for(TScDrpStockbillentryEntity tScDrpStockbillentry:tScDrpStockbillentryList){
				if(oConvertUtils.isEmpty(tScDrpStockbillentry.getId())){
					//外键设置
					tScDrpStockbillentry.setFid(tScDrpStockbill.getId());
					this.save(tScDrpStockbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScDrpStockbill);
	}

	
	public void delMain(TScDrpStockbillEntity tScDrpStockbill) {
		//删除主表信息
		this.delete(tScDrpStockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScDrpStockbill.getId();
		//===================================================================================
		//删除-商品信息
	    String hql0 = "from TScDrpStockbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScDrpStockbillentryEntity> tScDrpStockbillentryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScDrpStockbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpStockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpStockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpStockbillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScDrpStockbillEntity t){
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
 		sql  = sql.replace("#{dealerid}",String.valueOf(t.getDealerID()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getrStockId()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateAmount()));
 		sql  = sql.replace("#{freight}",String.valueOf(t.getFreight()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountID()));
 		sql  = sql.replace("#{curpayamount}",String.valueOf(t.getCurPayAmount()));
 		sql  = sql.replace("#{weight}",String.valueOf(t.getWeight()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIDSrc()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{affirmstatus}",String.valueOf(t.getAffirmStatus()));
 		sql  = sql.replace("#{affirmid}",String.valueOf(t.getAffirmID()));
 		sql  = sql.replace("#{affirmdate}",String.valueOf(t.getAffirmDate()));
 		sql  = sql.replace("#{amountloss}",String.valueOf(t.getAmountLoss()));
 		sql  = sql.replace("#{interid_src}",String.valueOf(t.getInterIDSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	//反作废
	@Override
	public AjaxJson enableBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		for(String id : idList){
			idStr += "'"+id+"',";
		}
		if(idStr.length() > 0){
			idStr = idStr.substring(0,idStr.length()-1);
		}
		String updateSql = "update T_SC_DRP_StockBill set cancellation = 0 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("反作废失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 作废功能实现
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
		}
		if(idStr.length() > 0){
			idStr = idStr.substring(0,idStr.length()-1);
		}
		String updateSql = "update T_SC_DRP_StockBill set cancellation = 1 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("作废失败："+e.getMessage());
		}
		return j;
	}
	/**
	 * 关闭功能实现方法
	 * @param ids
	 * @return
	 */
	@Override
	public AjaxJson closeBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		for(String id : idList){
			idStr += "'"+id+"',";
		}
		if(idStr.length() > 0){
			idStr = idStr.substring(0,idStr.length()-1);
		}
		String updateSql = "update T_SC_DRP_StockBill set closed = 1 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("关闭失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 反关闭功能实现方法
	 * @param ids
	 * @return
	 */
	@Override
	public AjaxJson openBill(String ids) {
		AjaxJson j = new AjaxJson();
		String[] idList = ids.split(",");
		String idStr="";
		for(String id : idList){
			idStr += "'"+id+"',";
		}
		if(idStr.length() > 0){
			idStr = idStr.substring(0,idStr.length()-1);
		}
		String updateSql = "update T_SC_DRP_StockBill set closed = 0 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("反关闭失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 审核（反审核）后执行
	 * @param id
	 * @param audit
	 * @return
	 */
	@Override
	public AjaxJson afterAudit(String id, Integer audit) {
		AjaxJson j = new AjaxJson();
		TScDrpStockbillEntity entity = this.getEntity(TScDrpStockbillEntity.class, id);
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where tranType = ? and billId=? order by orderNum desc", new Object[]{entity.getTranType(), id});
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?", new Object[]{entity.getSonID(), entity.getTranType(), id});
		try {
			if(auditBillInfoEntityList.size() > 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				//判断单据是从已审核状态至审核中（未审核）或从未审核（审核中）至已审核状态时进行库存变更；
				//oldState 审核前状态值 newState 审核后状态值
				if (((auditBillInfoEntity.getOldState() == 1 && auditBillInfoEntity.getNewState() == 2) || (auditBillInfoEntity.getOldState() == 2 && auditBillInfoEntity.getNewState() == 1)) && relationEntityList.get(0).getStatus() >= 0) {
					List<TScDrpStockbillentryEntity> entryList = findHql("from TScDrpStockbillentryEntity where fid = ?", new Object[]{id});
					for (TScDrpStockbillentryEntity entry : entryList) {
						BigDecimal basicCoefficient = BigDecimal.ZERO;
						BigDecimal ckCoefficient = BigDecimal.ZERO;
						Double secQty = entry.getSecQty();//辅助数量
						Double basicQty = entry.getBasicQty();//基本数量
						Double costPrice = (entry.getTaxPriceEx() * Double.parseDouble(basicCoefficient.toString()));//基础单价
						Double costAmount = (costPrice * entry.getQty());
						TScIcInventoryEntity inventoryEntity = null;
						List<TScIcInventoryEntity> inventoryEntities = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?", new Object[]{entry.getItemID(), entry.getStockID()});
						if (inventoryEntities.size() > 0) {
							inventoryEntity = inventoryEntities.get(0);
						}
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = this.findHql("from TScIcInventoryBatchnoEntity where itemId = ? and batchNo = ?", new Object[]{entry.getItemID(), entry.getBatchNo()});
						if (null != inventoryEntity) {
							//若为审核操作
							if (1 == audit) {
								if (StringUtils.isNotEmpty(entry.getStockID())) {
									inventoryEntity.setStockId(entry.getStockID());
								}
								inventoryEntity.setSecQty(inventoryEntity.getSecQty() - secQty);
								inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() - basicQty);
								inventoryEntity.setCount(inventoryEntity.getCount() + 1);
								this.saveOrUpdate(inventoryEntity);
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setSecQty(inventoryBatchnoEntity.getSecQty() - secQty);
									this.saveOrUpdate(inventoryBatchnoEntity);
								} else if (StringUtils.isNotEmpty(entry.getBatchNo())) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
									inventoryBatchnoEntity.setCostAmount(costAmount);
									inventoryBatchnoEntity.setCostPrice(costPrice);
									inventoryBatchnoEntity.setSecQty(entry.getSecQty());
									if (StringUtils.isNotEmpty(entry.getStockID())) {
										inventoryBatchnoEntity.setStockId(entry.getStockID());
									}
									inventoryBatchnoEntity.setItemId(entry.getItemID());
									inventoryBatchnoEntity.setBatchNo(entry.getBatchNo());
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									inventoryBatchnoEntity.setCount(1);
									this.save(inventoryBatchnoEntity);
								}
							} else {//反审核
								if (StringUtils.isNotEmpty(entry.getStockID())) {
									inventoryEntity.setStockId(entry.getStockID());
								}
								inventoryEntity.setSecQty(inventoryEntity.getSecQty() + secQty);
								inventoryEntity.setBasicQty(inventoryEntity.getBasicQty() + basicQty);
								this.saveOrUpdate(inventoryEntity);
								if (inventoryBatchnoEntityList.size() > 0) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = inventoryBatchnoEntityList.get(0);
									inventoryBatchnoEntity.setSecQty(inventoryBatchnoEntity.getSecQty() - secQty);
									this.saveOrUpdate(inventoryBatchnoEntity);
								} else if (StringUtils.isNotEmpty(entry.getBatchNo())) {
									TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
									inventoryBatchnoEntity.setCostAmount(costAmount);
									inventoryBatchnoEntity.setCostPrice(costPrice);
									inventoryBatchnoEntity.setSecQty(entry.getSecQty());
									if (StringUtils.isNotEmpty(entry.getStockID())) {
										inventoryBatchnoEntity.setStockId(entry.getStockID());
									}
									inventoryBatchnoEntity.setItemId(entry.getItemID());
									inventoryBatchnoEntity.setBatchNo(entry.getBatchNo());
									if (null != entry.getPeriodDate()) {
										inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
									}
									inventoryBatchnoEntity.setCount(1);
									this.save(inventoryBatchnoEntity);
								}
							}
						} else {
							TScIcInventoryEntity newInventory = new TScIcInventoryEntity();
							newInventory.setItemId(entry.getItemID());
							if (StringUtils.isNotEmpty(entry.getStockID())) {
								newInventory.setStockId(entry.getStockID());
							}
							newInventory.setBasicQty(basicQty);
							newInventory.setSecQty(secQty);
							newInventory.setCostPrice(costPrice);
							newInventory.setCostAmount(costAmount);
							newInventory.setCount(1);
							this.save(newInventory);
							if (StringUtils.isNotEmpty(entry.getBatchNo())) {
								TScIcInventoryBatchnoEntity inventoryBatchnoEntity = new TScIcInventoryBatchnoEntity();
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
								if (null != entry.getPeriodDate()) {
									inventoryBatchnoEntity.setPeriodDate(entry.getPeriodDate());
								}
								inventoryBatchnoEntity.setCount(1);
								this.save(inventoryBatchnoEntity);
							}
						}

					}
				}
			}
		} catch (Exception e){
			//单据状态回写
			TScDrpStockbillEntity bill= this.getEntity(TScDrpStockbillEntity.class, id);
			if(bill.getCheckState() == 2) {
				//审核异常
				TSAuditRelationEntity delRelationEntity = relationEntityList.get(0);
				super.delete(delRelationEntity);
				if(relationEntityList.size() > 1) {
					//前一次审核内容
					TSAuditRelationEntity lastAuditInfo = relationEntityList.get(1);
					bill.setCheckState(bill.getCheckState() - 1);
					bill.setCheckerID(lastAuditInfo.getAuditorId());
					bill.setCheckDate(lastAuditInfo.getAuditDate());
				} else {
					bill.setCheckState(0);
					bill.setCheckerID(null);
					bill.setCheckDate(null);
				}
				//更新待审核数据预警提醒
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{entity.getSonID(),entity.getTranType(), id});
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
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{entity.getSonID(), entity.getTranType(), id});
				if(tScBillAuditStatusEntityList.size() > 0){
					for(TScBillAuditStatusEntity billAuditStatusEntity : tScBillAuditStatusEntityList){
						billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()+1);
						super.saveOrUpdate(billAuditStatusEntity);
					}
				}
				bill.setCheckState(bill.getCheckState()+1);
				bill.setCheckerID(endRelationEntity.getAuditorId());
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
}