
package com.qihang.buss.sc.distribution.service.impl;
import com.qihang.buss.sc.distribution.service.TScDrpOrderServiceI;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.distribution.entity.TScDrpOrderEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpOrderentryEntity;

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


@Service("tScDrpOrderService")
@Transactional
public class TScDrpOrderServiceImpl extends CommonServiceImpl implements TScDrpOrderServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScDrpOrderEntity)entity);
 	}
	
	public void addMain(TScDrpOrderEntity tScDrpOrder,
	        List<TScDrpOrderentryEntity> tScDrpOrderentryList){
			//保存主信息
			this.save(tScDrpOrder);
		
			/**保存-订单管理*/
			for(TScDrpOrderentryEntity tScDrpOrderentry:tScDrpOrderentryList){
				//外键设置
				tScDrpOrderentry.setInterID(tScDrpOrder.getId());
				this.save(tScDrpOrderentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScDrpOrder);
	}

	
	public void updateMain(TScDrpOrderEntity tScDrpOrder,
	        List<TScDrpOrderentryEntity> tScDrpOrderentryList) {
		//保存主表信息
		this.saveOrUpdate(tScDrpOrder);
		//===================================================================================
		//获取参数
		Object id0 = tScDrpOrder.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-订单管理
	    String hql0 = "from TScDrpOrderentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScDrpOrderentryEntity> tScDrpOrderentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-订单管理
		for(TScDrpOrderentryEntity oldE:tScDrpOrderentryOldList){
			boolean isUpdate = false;
				for(TScDrpOrderentryEntity sendE:tScDrpOrderentryList){
					//需要更新的明细数据-订单管理
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-订单管理
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-订单管理
			for(TScDrpOrderentryEntity tScDrpOrderentry:tScDrpOrderentryList){
				if(oConvertUtils.isEmpty(tScDrpOrderentry.getId())){
					//外键设置
					tScDrpOrderentry.setInterID(tScDrpOrder.getId());
					this.save(tScDrpOrderentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScDrpOrder);
	}

	
	public void delMain(TScDrpOrderEntity tScDrpOrder) {
		//删除主表信息
		this.delete(tScDrpOrder);
		//===================================================================================
		//获取参数
		Object id0 = tScDrpOrder.getId();
		//===================================================================================
		//删除-订单管理
	    String hql0 = "from TScDrpOrderentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScDrpOrderentryEntity> tScDrpOrderentryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScDrpOrderentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpOrderEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScDrpOrderEntity t){
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
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemID()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockID()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{rebateamount}",String.valueOf(t.getRebateAmount()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
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
		String updateSql = "update t_sc_drp_order set cancellation = 0 where id in ("+idStr+")";
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
		String updateSql = "update t_sc_drp_order set cancellation = 1 where id in ("+idStr+")";
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
		String updateSql = "update t_sc_drp_order set closed = 1 where id in ("+idStr+")";
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
		String updateSql = "update t_sc_drp_order set closed = 0 where id in ("+idStr+")";
		try {
			updateBySqlString(updateSql);
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("反关闭失败："+e.getMessage());
		}
		return j;
	}
}