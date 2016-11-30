
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.sales.service.TScPoOrderServiceI;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sales.entity.TScPoOrderEntity;
import com.qihang.buss.sc.sales.entity.TScPoOrderentryEntity;

import com.qihang.winter.core.util.ContextHolderUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;

import javax.servlet.http.HttpSession;


@Service("tPoOrderService")
@Transactional
public class TScPoOrderServiceImpl extends CommonServiceImpl implements TScPoOrderServiceI {

	@Autowired
	private SessionFactory sessionFactory;


	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScPoOrderEntity) entity);
 	}
	
	public void addMain(TScPoOrderEntity tPoOrder,
	        List<TScPoOrderentryEntity> tPoOrderentryList){
			//保存主信息
			this.save(tPoOrder);
			/**保存-采购订单从表*/
			for(TScPoOrderentryEntity tPoOrderentry:tPoOrderentryList){
				//外键设置
				tPoOrderentry.setFid(tPoOrder.getId());
				tPoOrderentry.setStockQty(0.0);
				this.save(tPoOrderentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tPoOrder);
	}

	
	public void updateMain(TScPoOrderEntity tPoOrder,
	        List<TScPoOrderentryEntity> tPoOrderentryList) {
		//保存主表信息
		//this.saveOrUpdate(tPoOrder);
		//===================================================================================
		//获取参数
		Object id0 = tPoOrder.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-采购订单从表
	    String hql0 = "from TScPoOrderentryEntity where 1 = 1 AND fid = ? ";
	    List<TScPoOrderentryEntity> tPoOrderentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-采购订单从表
		Double allQty = 0.0;
		Double allStockQty = 0.0;
		for(TScPoOrderentryEntity oldE:tPoOrderentryOldList){
			boolean isUpdate = false;
				for(TScPoOrderentryEntity sendE:tPoOrderentryList){
					//需要更新的明细数据-采购订单从表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							allQty += oldE.getQty();
							allStockQty += oldE.getStockQty();
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-采购订单从表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-采购订单从表
			for(TScPoOrderentryEntity tPoOrderentry:tPoOrderentryList){
				if(oConvertUtils.isEmpty(tPoOrderentry.getId())){
					//外键设置
					tPoOrderentry.setFid(tPoOrder.getId());
					allQty += tPoOrderentry.getQty();
					this.save(tPoOrderentry);
				}
			}
		if(allQty.equals(allStockQty)){
			tPoOrder.setAutoFlag(1);
			tPoOrder.setClosed(1);
		}else{
			tPoOrder.setClosed(0);
			tPoOrder.setAutoFlag(0);
		}
		saveOrUpdate(tPoOrder);
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tPoOrder);
	}

	
	public void delMain(TScPoOrderEntity tPoOrder) {
		//删除主表信息
		this.delete(tPoOrder);
		//===================================================================================
		//获取参数
		Object id0 = tPoOrder.getId();
		//===================================================================================
		//删除-采购订单从表
	    String hql0 = "from TScPoOrderentryEntity where 1 = 1 AND fid = ? ";
	    List<TScPoOrderentryEntity> tPoOrderentryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tPoOrderentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPoOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPoOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPoOrderEntity t){
	 	return true;
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
		String updateSql = "update t_sc_po_order set closed = 1 where id in ("+idStr+")";
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
		String updateSql = "update t_sc_po_order set closed = 0 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("反关闭失败："+e.getMessage());
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
		String updateSql = "update t_sc_po_order set cancellation = 1 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("作废失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 选单明细数据
	 * @param ids
	 * @return
	 */
	@Override
	public List<TScPoOrderentryEntity> loadEntryList(String ids,String tranType) {
		List<TScPoOrderentryEntity> entryList = new ArrayList<TScPoOrderentryEntity>();
		String[] idList = ids.split(",");
		for(String id : idList) {
			TScPoOrderEntity main = this.getEntity(TScPoOrderEntity.class, id);
			List<TScPoOrderentryEntity> entities = this.findHql("from TScPoOrderentryEntity where fid = ?",new Object[]{id});
			for (TScPoOrderentryEntity entry : entities) {
				sessionFactory.getCurrentSession().evict(entry);
				TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class, entry.getItemId());
				if(null != icitemEntity) {
					entry.setItemNo(icitemEntity.getNumber());
					entry.setItemName(icitemEntity.getName());
					entry.setModel(icitemEntity.getModel());
					entry.setKfDateType(icitemEntity.getKfDateType());
					entry.setKfPeriod(icitemEntity.getKfPeriod());
					entry.setIsKfPeriod(icitemEntity.getIskfPeriod());
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
				Double billQty = entry.getQty();
				entry.setBillQty(billQty);
				Double allowQty = entry.getQty()-entry.getStockQty();
				if(allowQty > 0 && "52".equals(tranType)) {
					entry.setQty(allowQty);
					entryList.add(entry);
				}else if("53".equals(tranType) && entry.getStockQty() > 0){
					entry.setQty(entry.getStockQty());
					entryList.add(entry);
				}
			}
		}
//		for(TScPoOrderentryEntity entity : entryList){
//
//		}
		return entryList;
	}

	//校验是否自动关闭
	@Override
	public void checkAutoFlag(Set<String> mainIdList) {
		sessionFactory.getCurrentSession().flush();
		for(String mainId : mainIdList){
			List<Map<String,Object>> entryList = this.findForJdbc("select * from T_SC_PO_OrderEntry where fid = ?", mainId);
			Boolean isAutoFlag = true;
			Boolean isUse = false;
			for(Map<String,Object> entry : entryList){
				if(null != entry.get("stockQty")) {
					if ((Double) entry.get("stockQty") >= (Double) entry.get("qty")) {
						continue;
					} else {
						isAutoFlag = false;
					}
					if ((Double) entry.get("stockQty")  > 0) {
						isUse = true;
					}
				}
			}
			if(isAutoFlag){
				String updateSql = "update t_sc_po_order set closed=1,autoFlag=1 where id = '"+mainId+"'";
				this.updateBySqlString(updateSql);
			}else{
				String updateSql = "update t_sc_po_order set closed=0,autoFlag=0 where id = '"+mainId+"'";
				TScPoOrderEntity order = this.getEntity(TScPoOrderEntity.class,mainId);
				if(1 == order.getClosed() && 1 == order.getAutoFlag()) {
					this.updateBySqlString(updateSql);
				}
			}
			if(isUse){
				String updateSql = "update t_sc_po_order set isUse=1 where id = '"+mainId+"'";
				this.updateBySqlString(updateSql);
			}else{
				String updateSql = "update t_sc_po_order set isUse=0 where id = '"+mainId+"'";
				this.updateBySqlString(updateSql);
			}
		}
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
		String updateSql = "update t_sc_po_order set cancellation = 0 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("反作废失败："+e.getMessage());
		}
		return j;
	}

	@Override
	public AjaxJson afterAudit(String id, Integer audit) {
		return new AjaxJson();
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScPoOrderEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemId()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockId()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateAmount()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIdSrc()));
 		sql  = sql.replace("#{id_src}",String.valueOf(t.getIdSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{closed}",String.valueOf(t.getClosed()));
 		sql  = sql.replace("#{autoflag}",String.valueOf(t.getAutoFlag()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}