
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sales.entity.TScOrderentryEntity;

import com.qihang.winter.core.constant.Globals;
import org.apache.batik.dom.svg12.Global;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;

import java.io.Serializable;


@Service("tScOrderService")
@Transactional
public class TScOrderServiceImpl extends CommonServiceImpl implements TScOrderServiceI {

	@Autowired
	private TScOrganizationServiceI tScOrganizationService;
	@Autowired
	private TScEmpServiceI tScEmpService;
	@Autowired
	private TScDepartmentServiceI tScDepartmentService;
	@Autowired
	private TScStockServiceI tScStockService;
	@Autowired
	private TScIcitemServiceI tScIcitemService;
	@Autowired
	private SessionFactory sessionFactory;


 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScOrderEntity)entity);
 	}
	
	public void addMain(TScOrderEntity tScOrder, List<TScOrderentryEntity> tScOrderentryList){
		   //计数客户
		  if (StringUtils.isNotEmpty(tScOrder.getItemID())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScOrder.getItemID());
				tScOrganizationService.updateOrganizationCount(countEntity);
			}
			//计数经办人
			if (StringUtils.isNotEmpty(tScOrder.getEmpID())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScOrder.getEmpID());
				tScEmpService.updateEmpCount(countEntity);
			}
		    //计数部门
			if (StringUtils.isNotEmpty(tScOrder.getDeptID())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScOrder.getDeptID());
				tScDepartmentService.updateDepartmentCount(countEntity);
			}
		    //计数仓库
			if (StringUtils.isNotEmpty(tScOrder.getStockID())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScOrder.getStockID());
				tScStockService.updateStockCount(countEntity);
			}
			//保存主信息
			this.save(tScOrder);
		
			/**保存-销售订单从表*/
			for(TScOrderentryEntity tScOrderentry:tScOrderentryList){
				//外键设置
				tScOrderentry.setOrderId(tScOrder.getId());
				tScOrderentry.setStockQty(BigDecimal.ZERO);
				//子表计数商品
				if (StringUtils.isNotEmpty(tScOrderentry.getItemID())) {
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScOrderentry.getItemID());
					tScIcitemService.updateIcitemCount(countEntity);
				}
				//计数仓库
				if(StringUtils.isNotEmpty(tScOrderentry.getStockID())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScOrderentry.getStockID());
					tScStockService.updateStockCount(countEntity);
				}
				this.save(tScOrderentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScOrder);
	}

	
	public void updateMain(TScOrderEntity tScOrder, List<TScOrderentryEntity> tScOrderentryList) {
		TScOrderEntity oldOrderEntity  =  this.get(TScOrderEntity.class,tScOrder.getId());
		sessionFactory.getCurrentSession().evict(oldOrderEntity);
		//主表计数
        if(oldOrderEntity != null){
             //计数客户计数
			if(StringUtils.isNotEmpty(tScOrder.getItemID())){
				if(!(oldOrderEntity.getItemID().equals(tScOrder.getItemID()))){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldOrderEntity.getItemID());
					countEntity.setNewId(tScOrder.getItemID());
					tScOrganizationService.updateOrganizationCount(countEntity);
				}
				//计数经办人
				if (!(oldOrderEntity.getEmpID().equals(tScOrder.getEmpID()))) {
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldOrderEntity.getEmpID());
					countEntity.setNewId(tScOrder.getEmpID());
					tScEmpService.updateEmpCount(countEntity);
				}
				//计数部门
				if (!oldOrderEntity.getDeptID().equals(tScOrder.getDeptID())) {
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldOrderEntity.getDeptID());
					countEntity.setNewId(tScOrder.getDeptID());
					tScDepartmentService.updateDepartmentCount(countEntity);
				}
				//计数仓库
				if(StringUtils.isEmpty(oldOrderEntity.getStockID()) && StringUtils.isNotEmpty(tScOrder.getStockID())) {
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScOrder.getStockID());
					tScStockService.updateStockCount(countEntity);
				}
				if(StringUtils.isNotEmpty(oldOrderEntity.getStockID())){
				   if(StringUtils.isNotEmpty(tScOrder.getStockID())){
					   if(!(oldOrderEntity.getStockID().equals(tScOrder.getStockID()))){
						   TScCountEntity countEntity = new TScCountEntity();
						   countEntity.setType(Globals.COUNT_UPDATE_TYPE);
						   countEntity.setOldId(oldOrderEntity.getStockID());
						   countEntity.setNewId(tScOrder.getStockID());
						   tScStockService.updateStockCount(countEntity);
					   }
				   }else{
					   TScCountEntity countEntity = new TScCountEntity();
					   countEntity.setType(Globals.COUNT_DEL_TYPE);
					   countEntity.setOldId(oldOrderEntity.getStockID());
					   tScStockService.updateStockCount(countEntity);
				   }
				}
			}
		}
		//保存主表信息
		this.saveOrUpdate(tScOrder);
		//===================================================================================
		//获取参数
		Object id0 = tScOrder.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-销售订单从表
	    String hql0 = "from TScOrderentryEntity where 1 = 1 AND oRDERID = ? ";
	    List<TScOrderentryEntity> tScOrderentryOldList = this.findHql(hql0, id0);
		//2.筛选更新明细数据-销售订单从表
		for(TScOrderentryEntity oldE:tScOrderentryOldList){
			boolean isUpdate = false;
				for(TScOrderentryEntity sendE:tScOrderentryList){
					//需要更新的明细数据-销售订单从表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							//商品
							if(!oldE.getItemID().equals(sendE.getItemID())){
								TScCountEntity countEntity = new TScCountEntity();
								countEntity.setType(Globals.COUNT_UPDATE_TYPE);
								countEntity.setOldId(oldE.getItemID());
								countEntity.setNewId(sendE.getItemID());
								tScIcitemService.updateIcitemCount(countEntity);
							}
							//仓库
							if(StringUtils.isEmpty(oldE.getStockID()) && StringUtils.isNotEmpty(sendE.getStockID())){
								TScCountEntity countEntity = new TScCountEntity();
								countEntity.setType(Globals.COUNT_ADD_TYPE);
								countEntity.setOldId(sendE.getStockID());
								tScStockService.updateStockCount(countEntity);
							}
							if(StringUtils.isNotEmpty(oldE.getStockID())){
								if(StringUtils.isNotEmpty(sendE.getStockID())){
									if(!(oldE.getStockID().equals(sendE.getStockID()))){
										TScCountEntity countEntity = new TScCountEntity();
										countEntity.setType(Globals.COUNT_UPDATE_TYPE);
										countEntity.setOldId(oldE.getStockID());
										countEntity.setNewId(sendE.getStockID());
										tScStockService.updateStockCount(countEntity);
									}
								}else{
									TScCountEntity countEntity = new TScCountEntity();
									countEntity.setType(Globals.COUNT_DEL_TYPE);
									countEntity.setOldId(oldE.getStockID());
									tScStockService.updateStockCount(countEntity);
								}
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-销售订单从表
					//商品
					if(StringUtils.isNotEmpty(oldE.getItemID())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_DEL_TYPE);
						countEntity.setOldId(oldE.getItemID());//原先的单位
						tScIcitemService.updateIcitemCount(countEntity);
					}
					//仓库
					if(StringUtils.isNotEmpty(oldE.getStockID())){
						TScCountEntity countStockEntity = new TScCountEntity();
						countStockEntity.setType(Globals.COUNT_DEL_TYPE);
						countStockEntity.setOldId(oldE.getStockID());
						tScStockService.updateStockCount(countStockEntity);
					}
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-销售订单从表
			for(TScOrderentryEntity tScOrderentry:tScOrderentryList){
				if(oConvertUtils.isEmpty(tScOrderentry.getId())){
					//商品
					if(StringUtils.isNotEmpty(tScOrderentry.getItemID())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_ADD_TYPE);
						countEntity.setOldId(tScOrderentry.getItemID());//原先的单位
						tScIcitemService.updateIcitemCount(countEntity);
					}
					//仓库
					if(StringUtils.isNotEmpty(tScOrderentry.getStockID())){
						TScCountEntity countStockEntity = new TScCountEntity();
						countStockEntity.setType(Globals.COUNT_ADD_TYPE);
						countStockEntity.setOldId(tScOrderentry.getStockID());
						tScStockService.updateStockCount(countStockEntity);
					}
					//外键设置
					tScOrderentry.setOrderId(tScOrder.getId());
					this.save(tScOrderentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScOrder);
	}

	
	public void delMain(TScOrderEntity tScOrder) {
		//计数客户
		if (StringUtils.isNotEmpty(tScOrder.getItemID())) {
			TScCountEntity countEntity = new TScCountEntity();
			countEntity.setType(Globals.COUNT_DEL_TYPE);
			countEntity.setOldId(tScOrder.getItemID());
			tScOrganizationService.updateOrganizationCount(countEntity);
		}
		//计数经办人
		if (StringUtils.isNotEmpty(tScOrder.getEmpID())) {
			TScCountEntity countEntity = new TScCountEntity();
			countEntity.setType(Globals.COUNT_DEL_TYPE);
			countEntity.setOldId(tScOrder.getEmpID());
			tScEmpService.updateEmpCount(countEntity);
		}
		//计数部门
		if (StringUtils.isNotEmpty(tScOrder.getDeptID())) {
			TScCountEntity countEntity = new TScCountEntity();
			countEntity.setType(Globals.COUNT_DEL_TYPE);
			countEntity.setOldId(tScOrder.getDeptID());
			tScDepartmentService.updateDepartmentCount(countEntity);
		}
		//计数仓库
		if (StringUtils.isNotEmpty(tScOrder.getStockID())) {
			TScCountEntity countEntity = new TScCountEntity();
			countEntity.setType(Globals.COUNT_DEL_TYPE);
			countEntity.setOldId(tScOrder.getStockID());
			tScStockService.updateStockCount(countEntity);
		}
		//删除主表信息
		this.delete(tScOrder);
		//===================================================================================
		//获取参数
		Object id0 = tScOrder.getId();
		//===================================================================================
		//删除-销售订单从表
	    String hql0 = "from TScOrderentryEntity where 1 = 1 AND oRDERID = ? ";
	    List<TScOrderentryEntity> tScOrderentryOldList = this.findHql(hql0,id0);
		for(TScOrderentryEntity entity:tScOrderentryOldList){
			//商品
			if(StringUtils.isNotEmpty(entity.getItemID())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(entity.getItemID());//原先的单位
				tScIcitemService.updateIcitemCount(countEntity);
			}
			//仓库
			if(StringUtils.isNotEmpty(entity.getStockID())){
				TScCountEntity countStockEntity = new TScCountEntity();
				countStockEntity.setType(Globals.COUNT_DEL_TYPE);
				countStockEntity.setOldId(entity.getStockID());
				tScStockService.updateStockCount(countStockEntity);
			}
		}
		this.deleteAllEntitie(tScOrderentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScOrderEntity t){
	 	return true;
 	}

	/**
	 * 关闭和反关闭 type关闭为1,反关闭为2
	 * @param id
	 * @param type
	 */
	@Override
	public void closeOrderBill(String id,String type) {
		TScOrderEntity tScOrder = this.get(TScOrderEntity.class,id);
		if("1".equals(type)) {
			tScOrder.setClosed(Globals.CLOSE_YES);
		}else if("2".equals(type)){
			tScOrder.setClosed(Globals.CLOSE_NO);
		}
		this.saveOrUpdate(tScOrder);
	}

	/**
	 * 作废和反作废 type作废为1，反作废为2
	 * @param id
	 * @param type
	 */
	@Override
	public void updateCancelBill(String id, String type) {
		TScOrderEntity tScOrder = this.get(TScOrderEntity.class,id);
		if("1".equals(type)) {
			tScOrder.setCancellation(Globals.CLOSE_YES);
		}else if("2".equals(type)){
			tScOrder.setCancellation(Globals.CLOSE_NO);
		}
		this.saveOrUpdate(tScOrder);
	}

	/**
	 * 校验订单是否自动关闭
	 * @param mainIdList
	 */
	@Override
	public void checkAutoFlag(Set<String> mainIdList) {
		for(String mainId : mainIdList){
			List<Map<String,Object>> entryList = this.findForJdbc("select * from t_sc_order_entry where ORDERID = ?", mainId);
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
				String updateSql = "update t_sc_order set closed=1,autoFlag=1 where id = '"+mainId+"'";
				this.updateBySqlString(updateSql);
			}else{
				String updateSql = "update t_sc_order set closed=0,autoFlag=0 where id = '"+mainId+"'";
				TScOrderEntity order = this.getEntity(TScOrderEntity.class,mainId);
				if("1".equals(order.getClosed()) && "1".equals(order.getAutoFlag())){
					this.updateBySqlString(updateSql);
				}
			}
//			if(isUse){
//				String updateSql = "update t_sc_order set isUse=1 where id = '"+mainId+"'";
//				this.updateBySqlString(updateSql);
//			}else{
//				String updateSql = "update t_sc_order set isUse=0 where id = '"+mainId+"'";
//				this.updateBySqlString(updateSql);
//			}
		}
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScOrderEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemID()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockID()));
 		sql  = sql.replace("#{fetchstyle}",String.valueOf(t.getFetchStyle()));
 		sql  = sql.replace("#{preamount}",String.valueOf(t.getPreAmount()));
 		sql  = sql.replace("#{mall}",String.valueOf(t.getMall()));
 		sql  = sql.replace("#{mallorderid}",String.valueOf(t.getMallOrderID()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateAmount()));
 		sql  = sql.replace("#{freight}",String.valueOf(t.getFreight()));
 		sql  = sql.replace("#{weight}",String.valueOf(t.getWeight()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIDSrc()));
 		sql  = sql.replace("#{interid_src}",String.valueOf(t.getInterIDSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{closed}",String.valueOf(t.getClosed()));
 		sql  = sql.replace("#{autoflag}",String.valueOf(t.getAutoFlag()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}