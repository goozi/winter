
package com.qihang.buss.sc.init.service.impl;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.init.service.TScIcInitialServiceI;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.init.entity.TScIcInitialEntity;
import com.qihang.buss.sc.init.entity.TScIcInitialentryEntity;

import com.qihang.winter.core.constant.Globals;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("tScIcInitialService")
@Transactional
public class TScIcInitialServiceImpl extends CommonServiceImpl implements TScIcInitialServiceI {
	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private SessionFactory sessionFactory;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScIcInitialEntity)entity);
 	}
	
	public void addMain(TScIcInitialEntity tScIcInitial,
	        List<TScIcInitialentryEntity> tScIcInitialentryList){
			//保存主信息
			this.save(tScIcInitial);
		
			/**保存-附录明细*/
			for(TScIcInitialentryEntity tScIcInitialentry:tScIcInitialentryList){
				//外键设置
				tScIcInitialentry.setFid(tScIcInitial.getId());
				this.save(tScIcInitialentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScIcInitial);
	}

	
	public void updateMain(TScIcInitialEntity tScIcInitial, List<TScIcInitialentryEntity> tScIcInitialentryList) {
		TScIcInitialEntity te = this.get(TScIcInitialEntity.class, tScIcInitial.getId());
		sessionFactory.getCurrentSession().evict(te);
		//修改主表仓库引用的次数
		if(!te.getStockId().equals(tScIcInitial.getStockId())){
			countCommonService.editUpdateCount("T_SC_Stock",te.getStockId(),tScIcInitial.getStockId());
		}
		//保存主表信息
		this.saveOrUpdate(tScIcInitial);
		//===================================================================================
		//获取参数
		Object id0 = tScIcInitial.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-附录明细
	    String hql0 = "from TScIcInitialentryEntity where 1 = 1 AND fID = ? ";
	    List<TScIcInitialentryEntity> tScIcInitialentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-附录明细
		for(TScIcInitialentryEntity oldE:tScIcInitialentryOldList){
			boolean isUpdate = false;
				for(TScIcInitialentryEntity sendE:tScIcInitialentryList){
					//需要更新的明细数据-附录明细
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							//修改商品引用的次数
							if(!oldE.getItemId().equals(sendE.getItemId())){
								countCommonService.editUpdateCount("T_SC_ICItem",oldE.getItemId(),sendE.getItemId());
							}
							//从表修改仓库引用的次数
							if(!oldE.getStockId().equals(sendE.getStockId())){
								countCommonService.editUpdateCount("T_SC_Stock",oldE.getStockId(),sendE.getStockId());
							}
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
					//修改商品引用的次数
					countCommonService.deleteUpdateCount("T_SC_ICItem",oldE.getItemId());
					//从表修改仓库引用的次数
					countCommonService.deleteUpdateCount("T_SC_Stock",oldE.getStockId());
		    		//如果数据库存在的明细，前台没有传递过来则是删除-附录明细
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-附录明细
			for(TScIcInitialentryEntity tScIcInitialentry:tScIcInitialentryList){
				if(oConvertUtils.isEmpty(tScIcInitialentry.getId())){
					//外键设置
					tScIcInitialentry.setFid(tScIcInitial.getId());
					this.save(tScIcInitialentry);
				}
				//修改商品引用的次数
				countCommonService.addUpdateCount("T_SC_ICItem",tScIcInitialentry.getItemId());
				//从表修改仓库引用的次数
				countCommonService.addUpdateCount("T_SC_Stock",tScIcInitialentry.getStockId());
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScIcInitial);
	}

	
	public void delMain(TScIcInitialEntity tScIcInitial) {
		//从表修改仓库引用的次数
		countCommonService.deleteUpdateCount("T_SC_Stock",tScIcInitial.getStockId());
		//删除主表信息
		this.delete(tScIcInitial);
		//===================================================================================
		//获取参数
		Object id0 = tScIcInitial.getId();
		//===================================================================================
		//删除-附录明细
	    String hql0 = "from TScIcInitialentryEntity where 1 = 1 AND fID = ? ";
	    List<TScIcInitialentryEntity> tScIcInitialentryOldList = this.findHql(hql0,id0);
		for(TScIcInitialentryEntity entry : tScIcInitialentryOldList){
			//修改商品引用的次数
			countCommonService.deleteUpdateCount("T_SC_ICItem",entry.getItemId());
			//从表修改仓库引用的次数
			countCommonService.deleteUpdateCount("T_SC_Stock",entry.getStockId());
		}
		this.deleteAllEntitie(tScIcInitialentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcInitialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcInitialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcInitialEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScIcInitialEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{billdate}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockId()));
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
		String updateSql = "update t_sc_ic_initial set cancellation = 1 where id in ("+idStr+") and cancellation=0 and checkstate=0";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("作废失败："+e.getMessage());
		}
		return j;
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
		String updateSql = "update t_sc_ic_initial set cancellation = 0 where id in ("+idStr+") and cancellation=1";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("反作废失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 系统开帐时，将存货已审核数据插入或(按itemId、stockId来判断)更新到即时库存表及即时库存批次表
	 * @return
	 */
	@Override
	public boolean doAddInventory(){
		boolean isOk = false;

		String hql0 = "from TScIcInitialentryEntity a where 1 = 1 AND exists(select 1 from TScIcInitialEntity b where a.fid=b.id and b.checkstate="+Globals.AUDIT_FIN+" and b.cancellation="+Globals.CLOSE_NO+") ";
		List<TScIcInitialentryEntity> tScIcInitialentryList = this.findByQueryString(hql0);
		String hql3 = "from TScIcInitialEntity a where a.checkstate="+Globals.AUDIT_FIN+" and a.cancellation="+Globals.CLOSE_NO+" ";
		List<TScIcInitialEntity> tScIcInitialList = this.findByQueryString(hql3);
		for(TScIcInitialentryEntity entry : tScIcInitialentryList) {
			//累加到即时库存表
			String hql1 = "from TScIcInventoryEntity a where a.itemId=? and a.stockId=?";
			List<TScIcInventoryEntity> inventoryList =  this.findHql(hql1,new Object[]{entry.getItemId(),entry.getStockId()});
			//振铃：存货初始化开帐时，即时库存表和即时库存批次表如果是更新的，要累加count字段值，如果是新增，则count值为1
			//小叶：存货只有进货，没有退货。
			if (inventoryList.size()>0){//存在则更新第1个
				TScIcInventoryEntity inventory = inventoryList.get(0);
				inventory.setQty(inventory.getQty()+Double.parseDouble(entry.getQtystr()));
				inventory.setSmallQty(inventory.getSmallQty()+Double.parseDouble(entry.getSmallQtystr()));
				inventory.setBasicQty(inventory.getBasicQty()+Double.parseDouble(entry.getBasicQtystr()));
				inventory.setSecQty(inventory.getSecQty()+Double.parseDouble(entry.getSecQtystr()));
				inventory.setCostAmount(inventory.getCostAmount()+Double.parseDouble(entry.getCostAmountstr()));
				inventory.setCostPrice(inventory.getBasicQty()==0?0:inventory.getCostAmount()/inventory.getBasicQty());//新单价=新成本金额/新基本数量
				inventory.setCount(inventory.getCount()+1);
				this.saveOrUpdate(inventory);
			}else{//不存在则新增
				TScIcInventoryEntity inventory = new TScIcInventoryEntity();
				inventory.setItemId(entry.getItemId());
				inventory.setStockId(entry.getStockId());
				inventory.setQty(Double.parseDouble(entry.getQtystr()));
				inventory.setSmallQty(Double.parseDouble(entry.getSmallQtystr()));
				inventory.setBasicQty(Double.parseDouble(entry.getBasicQtystr()));
				inventory.setSecQty(Double.parseDouble(entry.getSecQtystr()));
				inventory.setCostAmount(Double.parseDouble(entry.getCostAmountstr()));
				inventory.setCostPrice(inventory.getBasicQty()==0?0:inventory.getCostAmount()/inventory.getBasicQty());//新单价=新成本金额/新基本数量
				inventory.setCount(1);
				this.saveOrUpdate(inventory);
			}
			//累加到即时库存批次表
			String hql2 = "from TScIcInventoryBatchnoEntity a where a.itemId=? and a.stockId=? and a.batchNo=?";
			List<TScIcInventoryBatchnoEntity> inventoryBatchnoList =  this.findHql(hql2,new Object[]{entry.getItemId(),entry.getStockId(),entry.getBatchNo()});
			if (inventoryBatchnoList.size()>0){//存在则更新第1个
				TScIcInventoryBatchnoEntity inventoryBatchno = inventoryBatchnoList.get(0);
				inventoryBatchno.setQty(inventoryBatchno.getQty()+Double.parseDouble(entry.getQtystr()));
				inventoryBatchno.setSmallQty(inventoryBatchno.getSmallQty()+Double.parseDouble(entry.getSmallQtystr()));
				inventoryBatchno.setBasicQty(inventoryBatchno.getBasicQty()+Double.parseDouble(entry.getBasicQtystr()));
				inventoryBatchno.setSecQty(inventoryBatchno.getSecQty()+Double.parseDouble(entry.getSecQtystr()));
				inventoryBatchno.setCostAmount(inventoryBatchno.getCostAmount()+Double.parseDouble(entry.getCostAmountstr()));
				inventoryBatchno.setCostPrice(inventoryBatchno.getBasicQty()==0?0:inventoryBatchno.getCostAmount()/inventoryBatchno.getBasicQty());//新单价=新成本金额/新基本数量
				inventoryBatchno.setCount(inventoryBatchno.getCount()+1);
				inventoryBatchno.setIsCheck(0);
				this.saveOrUpdate(inventoryBatchno);
			}else{//不存在则新增
				TScIcInventoryBatchnoEntity inventoryBatchno = new TScIcInventoryBatchnoEntity();
				inventoryBatchno.setItemId(entry.getItemId());
				inventoryBatchno.setStockId(entry.getStockId());
				inventoryBatchno.setBatchNo(entry.getBatchNo());
				inventoryBatchno.setQty(Double.parseDouble(entry.getQtystr()));
				inventoryBatchno.setSmallQty(Double.parseDouble(entry.getSmallQtystr()));
				inventoryBatchno.setBasicQty(Double.parseDouble(entry.getBasicQtystr()));
				inventoryBatchno.setSecQty(Double.parseDouble(entry.getSecQtystr()));
				inventoryBatchno.setCostAmount(Double.parseDouble(entry.getCostAmountstr()));
				inventoryBatchno.setCostPrice(inventoryBatchno.getBasicQty()==0?0:inventoryBatchno.getCostAmount()/inventoryBatchno.getBasicQty());//新单价=新成本金额/新基本数量
				inventoryBatchno.setCount(1);
				inventoryBatchno.setIsCheck(0);
				this.saveOrUpdate(inventoryBatchno);
			}
			//todo:加数据到存货日结表
			for(TScIcInitialEntity entryMain : tScIcInitialList) {
				if (entryMain.getId().equals(entry.getFid())) {
					TScIcSpeedbalEntity sppedbal = new TScIcSpeedbalEntity();
					sppedbal.setDate(entryMain.getDate());
					sppedbal.setTranType(entryMain.getTranType());
					sppedbal.setBillId(entryMain.getId());
					sppedbal.setBillEntryId(entry.getId());
					sppedbal.setBillCreateTime(entryMain.getCreateDate());
					sppedbal.setItemId(entry.getItemId());
					sppedbal.setStockId(entry.getStockId());
					sppedbal.setBatchNo(entry.getBatchNo());
					sppedbal.setQty(Double.parseDouble(entry.getBasicQtystr()));
					sppedbal.setSecQty(Double.parseDouble(entry.getSecQtystr()));
					sppedbal.setPrice(Double.parseDouble(entry.getCostPricestr()));
					sppedbal.setAmount(Double.parseDouble(entry.getCostAmountstr()));
					sppedbal.setEPrice(0.0);//结存单价
					sppedbal.setEAmount(0.0);//结存金额
					sppedbal.setDiffAmount(0.0);//差异金额
					//sppedbal.setBlidSrc(entry.getClassIDSrc());//源单类型
					sppedbal.setFlag(1);//出入库标记
					sppedbal.setStatus(1);//计算状态
					sppedbal.setNegFlag(0);//负结余处理状态
					this.save(sppedbal);
				}
			}

		}
		return isOk;
	}
}