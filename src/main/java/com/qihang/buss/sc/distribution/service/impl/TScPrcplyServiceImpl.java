
package com.qihang.buss.sc.distribution.service.impl;
import com.qihang.buss.sc.distribution.entity.TScPrcplyEntity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyViewEntityEntity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyentry1Entity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyentry2Entity;
import com.qihang.buss.sc.distribution.service.TScPrcplyServiceI;
import com.qihang.buss.sc.sales.entity.TScOrderentryEntity;
import com.qihang.buss.sc.sales.entity.TScSlStockbillEntity;
import com.qihang.buss.sc.sales.entity.TScSlStockbillentryEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service("tScPrcplyService")
@Transactional
public class TScPrcplyServiceImpl extends CommonServiceImpl implements TScPrcplyServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScPrcplyEntity) entity);
 	}
	
	public void addMain(TScPrcplyEntity tScPrcply,
	        List<TScPrcplyentry2Entity> tScPrcplyentry2List,List<TScPrcplyentry1Entity> tScPrcplyentry1List){
			//保存主信息
			this.save(tScPrcply);
		
			/**保存-商品从表*/
			for(TScPrcplyentry2Entity tScPrcplyentry2:tScPrcplyentry2List){
				//外键设置
				tScPrcplyentry2.setInterID(tScPrcply.getId());
				this.save(tScPrcplyentry2);
			}
			/**保存-客户从表*/
			for(TScPrcplyentry1Entity tScPrcplyentry1:tScPrcplyentry1List){
				//外键设置
				tScPrcplyentry1.setInterID(tScPrcply.getId());
				this.save(tScPrcplyentry1);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScPrcply);
	}

	
	public void updateMain(TScPrcplyEntity tScPrcply,
	        List<TScPrcplyentry2Entity> tScPrcplyentry2List,List<TScPrcplyentry1Entity> tScPrcplyentry1List) {
		//保存主表信息
		this.saveOrUpdate(tScPrcply);
		//===================================================================================
		//获取参数
		Object id0 = tScPrcply.getId();
		Object id1 = tScPrcply.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-商品从表
	    String hql0 = "from TScPrcplyentry2Entity where 1 = 1 AND iNTERID = ? ";
	    List<TScPrcplyentry2Entity> tScPrcplyentry2OldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-商品从表
		for(TScPrcplyentry2Entity oldE:tScPrcplyentry2OldList){
			boolean isUpdate = false;
				for(TScPrcplyentry2Entity sendE:tScPrcplyentry2List){
					//需要更新的明细数据-商品从表
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-商品从表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-商品从表
			for(TScPrcplyentry2Entity tScPrcplyentry2:tScPrcplyentry2List){
				if(oConvertUtils.isEmpty(tScPrcplyentry2.getId())){
					//外键设置
					tScPrcplyentry2.setInterID(tScPrcply.getId());
					this.save(tScPrcplyentry2);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-客户从表
	    String hql1 = "from TScPrcplyentry1Entity where 1 = 1 AND iNTERID = ? ";
	    List<TScPrcplyentry1Entity> tScPrcplyentry1OldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-客户从表
		for(TScPrcplyentry1Entity oldE:tScPrcplyentry1OldList){
			boolean isUpdate = false;
				for(TScPrcplyentry1Entity sendE:tScPrcplyentry1List){
					//需要更新的明细数据-客户从表
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-客户从表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-客户从表
			for(TScPrcplyentry1Entity tScPrcplyentry1:tScPrcplyentry1List){
				if(oConvertUtils.isEmpty(tScPrcplyentry1.getId())){
					//外键设置
					tScPrcplyentry1.setInterID(tScPrcply.getId());
					this.save(tScPrcplyentry1);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScPrcply);
	}

	
	public void delMain(TScPrcplyEntity tScPrcply) {
		//删除主表信息
		this.delete(tScPrcply);
		//===================================================================================
		//获取参数
		Object id0 = tScPrcply.getId();
		Object id1 = tScPrcply.getId();
		//===================================================================================
		//删除-商品从表
	    String hql0 = "from TScPrcplyentry2Entity where 1 = 1 AND iNTERID = ? ";
	    List<TScPrcplyentry2Entity> tScPrcplyentry2OldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScPrcplyentry2OldList);
		//===================================================================================
		//删除-客户从表
	    String hql1 = "from TScPrcplyentry1Entity where 1 = 1 AND iNTERID = ? ";
	    List<TScPrcplyentry1Entity> tScPrcplyentry1OldList = this.findHql(hql1,id1);
		this.deleteAllEntitie(tScPrcplyentry1OldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScPrcplyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScPrcplyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScPrcplyEntity t){
	 	return true;
 	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScPrcplyEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public List<TScPrcplyViewEntityEntity> getLatePrice(String goodsId, String custId,String unitId,double qty) {
		//根据商品ID、客户ID、单位ID查询出已审核的调价单
		String getLatePriceHql = " from TScPrcplyViewEntityEntity where itemID = ? and custId = ? and UnitID = ? and checkstate = 2 ";
		List<TScPrcplyViewEntityEntity> lateList = this.findHql(getLatePriceHql,new Object[]{goodsId,custId,unitId});
		//调价单不为空
		if(!lateList.isEmpty()){
			//获取当前时间毫秒数
			long currentDate = new Date().getTime();
			int size = lateList.size()==1?1:lateList.size()-1;
			/**
			 * 过滤集合
			 */
			outer:
			for(int i = 0;i<size;i++){
				//获取当前记录的数量区间
				double oDegQty = lateList.get(i).getBegQty().doubleValue();
				double oEndQty = lateList.get(i).getEndQty().doubleValue();

				//判断数量段  数量段(至)为0 把数量设为无穷大
				if(oEndQty == 0 ){
					oEndQty = Double.MAX_VALUE;
				}

				//获取当前记录的起始日期的毫秒数
				long bDate = lateList.get(i).getBegDate().getTime();
				long eDate = lateList.get(i).getEndDate().getTime();

				//如果当前日期在当前记录的有效期内并且数量也处在当前记录的范围内就继续过滤集合 否则移除
				if ((currentDate >= bDate && currentDate <= eDate) && (qty >= oDegQty && qty <= oEndQty)) {
					for (int k = (lateList.size()-1); k >= i; k--) {
						//获取当前记录的起始数量
						double oldDegQty = lateList.get(k).getBegQty().doubleValue();
						double oldEndQty = lateList.get(k).getEndQty().doubleValue();

						//判断数量段  数量段(至)为0 把数量设为无穷大
						if(oldEndQty == 0 ){
							oldEndQty = Double.MAX_VALUE;
						}

						//获取当前记录的起始日期的毫秒数
						long begDate = lateList.get(k).getBegDate().getTime();
						long endDate = lateList.get(k).getEndDate().getTime();

						//如果当前日期处在有效期内并且数量也在有效范围内 继续过滤
						if ((currentDate >= begDate && currentDate <= endDate) && (qty >= oldDegQty && qty <= oldEndQty)) {
							//如果是同一个商品
							if (lateList.get(i).getItemId().equals(lateList.get(k).getItemId())) {
								//根据单据日期比较
								if ((lateList.get(i).getDate().getTime() >= lateList.get(k).getDate().getTime())) {
									if(lateList.size() > 1){//如果集合中的记录大于1 才移除
										lateList.remove(k);//移除掉单据日期最旧的记录
									}
									if(lateList.size() == 1){//如果集合中只有一条记录 直接退出循环
										break outer;
									}
								}
							}
						}else{
							lateList.remove(k);//移除掉集合中不符合条件的记录
							if(lateList.size() == 1){//如果集合中只有一条记录 直接退出循环
								break outer;
							}
						}
					}
				}else {//否则移除不在有效期和数量不达标的记录
					lateList.remove(i);
					if(lateList.size() == 0){//如果集合中的数据全部被移除 退出循环
						break;
					}
					--i; //重置循环变量 重新进行循环
				}
			}
			return lateList;
		}
		return Collections.emptyList();
	}

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
						String updateSql = "update t_sc_order_entry set stockQty = stockQty-(" + entity.getBasicQty() + ") where id = '" + entity.getEntryidOrder() + "'";
						this.updateBySqlString(updateSql);
					}else if("103".equals(entity.getClassidSrc()) && "104".equals(stockBill.getTrantype())){
						String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty+(" + entity.getBasicQty() + ") where id = '" + entity.getEntryidOrder() + "'";
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
							String updateSql = "update t_sc_order_entry set stockQty = stockQty+(" + entity.getBasicQty() + ") where id = '" + entity.getEntryidOrder() + "'";
							this.updateBySqlString(updateSql);
						}else if("103".equals(entity.getClassidSrc()) && "104".equals(stockBill.getTrantype())){
							String updateSql = "update t_sc_sl_stockbillentry set commitQty = commitQty-(" + entity.getBasicQty() + ") where id = '" + entity.getEntryidOrder() + "'";
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
}