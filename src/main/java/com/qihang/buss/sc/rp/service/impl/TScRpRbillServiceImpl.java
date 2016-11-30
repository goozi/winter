
package com.qihang.buss.sc.rp.service.impl;
import com.qihang.buss.sc.rp.service.TScRpRbillServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.rp.entity.TScRpRbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpRbillentry1Entity;
import com.qihang.buss.sc.rp.entity.TScRpRbillentry2Entity;

import com.qihang.winter.core.constant.Globals;
import org.apache.batik.dom.svg12.Global;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;
import java.util.ArrayList;
import java.util.UUID;
import java.io.Serializable;


@Service("tScRpRbillService")
@Transactional
public class TScRpRbillServiceImpl extends CommonServiceImpl implements TScRpRbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScRpRbillEntity) entity);
 	}
	
	public void addMain(TScRpRbillEntity tScRpRbill,
	        List<TScRpRbillentry1Entity> tScRpRbillentry1List,List<TScRpRbillentry2Entity> tScRpRbillentry2List){
			//保存主信息
			this.save(tScRpRbill);
		
			/**保存-结算表*/
			for(TScRpRbillentry1Entity tScRpRbillentry1:tScRpRbillentry1List){
				//外键设置
				tScRpRbillentry1.setFid(tScRpRbill.getId());
				this.save(tScRpRbillentry1);
			}
			/**保存-应收表*/
			for(TScRpRbillentry2Entity tScRpRbillentry2:tScRpRbillentry2List){
				//外键设置
				if(null != tScRpRbillentry2.getFindex()) {
					tScRpRbillentry2.setFid(tScRpRbill.getId());
					this.save(tScRpRbillentry2);
					String idSrc = tScRpRbillentry2.getIdSrc();
					Integer tranType = Integer.parseInt(tScRpRbillentry2.getClassIdSRC());
					String tableName = "";
					BigDecimal amount = tScRpRbillentry2.getAmount();
					if(Globals.SC_ORDER_TRANTYPE.equals(tranType)){
						//销售订单
						tableName = "t_sc_order";
					}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
						//销售出库
						tableName = "t_sc_sl_stockbill";
					}else if (Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
						//销售退货
						tableName = "t_sc_sl_stockbill";
						if(amount.compareTo(BigDecimal.ZERO) < 0){
							amount = BigDecimal.ZERO.subtract(amount);
						}
					}else if(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.equals(tranType)){
						//销售换货
						tableName = "t_sc_ic_xsstockbill";

					}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
						//应收初始化
						tableName = "t_sc_begdata";
					}else if(Globals.SC_RP_RBILL.equals(tranType)){
						//应收调账
						tableName = "T_SC_RP_AdjustBill";
					}


					String updateSql = "update "+tableName+" set checkAmount = ifnull(checkAmount,0) + "+amount+" where id = '"+idSrc+"'";
					this.updateBySqlString(updateSql);
				}
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScRpRbill);
	}

	
	public void updateMain(TScRpRbillEntity tScRpRbill,
	        List<TScRpRbillentry1Entity> tScRpRbillentry1List,List<TScRpRbillentry2Entity> tScRpRbillentry2List) {
		//保存主表信息
		this.saveOrUpdate(tScRpRbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpRbill.getId();
		Object id1 = tScRpRbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-结算表
	    String hql0 = "from TScRpRbillentry1Entity where 1 = 1 AND fID = ? ";
	    List<TScRpRbillentry1Entity> tScRpRbillentry1OldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-结算表
		for(TScRpRbillentry1Entity oldE:tScRpRbillentry1OldList){
			boolean isUpdate = false;
				for(TScRpRbillentry1Entity sendE:tScRpRbillentry1List){
					//需要更新的明细数据-结算表
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-结算表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-结算表
			for(TScRpRbillentry1Entity tScRpRbillentry1:tScRpRbillentry1List){
				if(oConvertUtils.isEmpty(tScRpRbillentry1.getId())){
					//外键设置
					tScRpRbillentry1.setFid(tScRpRbill.getId());
					this.save(tScRpRbillentry1);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-应收表
	    String hql1 = "from TScRpRbillentry2Entity where 1 = 1 AND fID = ? ";
	    List<TScRpRbillentry2Entity> tScRpRbillentry2OldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-应收表
		for(TScRpRbillentry2Entity oldE:tScRpRbillentry2OldList){
			boolean isUpdate = false;
			for(TScRpRbillentry2Entity sendE:tScRpRbillentry2List){
				//需要更新的明细数据-应收表
				if(oldE.getId().equals(sendE.getId())){
					try {
						String idSrc = oldE.getIdSrc();
						Integer tranType = Integer.parseInt(oldE.getClassIdSRC());
						String tableName = "";
						BigDecimal oldAmount = oldE.getAmount();
						BigDecimal newAmount = sendE.getAmount();
						BigDecimal changeAmount = oldAmount.subtract(newAmount);
						if(Globals.SC_ORDER_TRANTYPE.equals(tranType)){
							//销售订单
							tableName = "t_sc_order";
						}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
							//销售出库
							tableName = "t_sc_sl_stockbill";
						}else if( Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
							//销售退货
							tableName = "t_sc_sl_stockbill";
							if(changeAmount.compareTo(BigDecimal.ZERO) > 0){
								changeAmount = BigDecimal.ZERO.subtract(changeAmount);
							}
						}else if(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.equals(tranType)){
							//销售换货
							tableName = "t_sc_ic_xsstockbill";

						}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
							//应收初始化
							tableName = "t_sc_begdata";
						}else if(Globals.SC_RP_RBILL.equals(tranType)){
							//应收调账
							tableName = "T_SC_RP_AdjustBill";
						}

						String updateSql = "update "+tableName+" set checkAmount = checkAmount - ("+changeAmount+") where id = '"+idSrc+"'";
						this.updateBySqlString(updateSql);
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
				//如果数据库存在的明细，前台没有传递过来则是删除-应收表
				String idSrc = oldE.getIdSrc();
				Integer tranType = Integer.parseInt(oldE.getClassIdSRC());
				String tableName = "";
				BigDecimal oldAmount = oldE.getAmount();
				if(Globals.SC_ORDER_TRANTYPE.equals(tranType)){
					//销售订单
					tableName = "t_sc_order";
				}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
					//销售出库
					tableName = "t_sc_sl_stockbill";
				}else if( Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
					//销售退货
					tableName = "t_sc_sl_stockbill";
					if(oldAmount.compareTo(BigDecimal.ZERO) < 0){
						oldAmount = BigDecimal.ZERO.subtract(oldAmount);
					}
				}else if(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.equals(tranType)){
					//销售换货
					tableName = "t_sc_ic_xsstockbill";

				}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
					//应收初始化
					tableName = "t_sc_begdata";
				}else if(Globals.SC_RP_RBILL.equals(tranType)){
					//应收调账
					tableName = "T_SC_RP_AdjustBill";
				}
				String updateSql = "update "+tableName+" set checkAmount = checkAmount - "+oldAmount+" where id = '"+idSrc+"'";
				this.updateBySqlString(updateSql);
				super.delete(oldE);
			}
	    		
		}
		//3.持久化新增的数据-应收表
		for(TScRpRbillentry2Entity tScRpRbillentry2:tScRpRbillentry2List){
			if(oConvertUtils.isEmpty(tScRpRbillentry2.getId())){
				//外键设置
				if(null != tScRpRbillentry2.getFindex()) {
					tScRpRbillentry2.setFid(tScRpRbill.getId());
					this.save(tScRpRbillentry2);
					String idSrc = tScRpRbillentry2.getIdSrc();
					Integer tranType = Integer.parseInt(tScRpRbillentry2.getClassIdSRC());
					String tableName = "";
					BigDecimal amount = tScRpRbillentry2.getAmount();
					if(Globals.SC_ORDER_TRANTYPE.equals(tranType)){
						//销售订单
						tableName = "t_sc_order";
					}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
						//销售出库
						tableName = "t_sc_sl_stockbill";
					}else if (Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
						//销售退货
						tableName = "t_sc_sl_stockbill";
						if(amount.compareTo(BigDecimal.ZERO) < 0){
							amount = BigDecimal.ZERO.subtract(amount);
						}
					}else if(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.equals(tranType)){
						//销售换货
						tableName = "t_sc_ic_xsstockbill";

					}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
						//应收初始化
						tableName = "t_sc_begdata";
					}else if(Globals.SC_RP_RBILL.equals(tranType)){
						//应收调账
						tableName = "T_SC_RP_AdjustBill";
					}
					String updateSql = "update "+tableName+" set checkAmount = ifnull(checkAmount,0) + "+amount+" where id = '"+idSrc+"'";
					this.updateBySqlString(updateSql);
				}
			}
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScRpRbill);
	}

	
	public void delMain(TScRpRbillEntity tScRpRbill) {
		//删除主表信息
		this.delete(tScRpRbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpRbill.getId();
		Object id1 = tScRpRbill.getId();
		//===================================================================================
		//删除-结算表
	    String hql0 = "from TScRpRbillentry1Entity where 1 = 1 AND fID = ? ";
	    List<TScRpRbillentry1Entity> tScRpRbillentry1OldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScRpRbillentry1OldList);
		//===================================================================================
		//删除-应收表
	    String hql1 = "from TScRpRbillentry2Entity where 1 = 1 AND fID = ? ";
	    List<TScRpRbillentry2Entity> tScRpRbillentry2OldList = this.findHql(hql1,id1);
		for(TScRpRbillentry2Entity entry : tScRpRbillentry2OldList){
			String idSrc = entry.getIdSrc();
			Integer tranType = Integer.parseInt(entry.getClassIdSRC());
			String tableName = "";
			BigDecimal amount = entry.getAmount();
			if(Globals.SC_ORDER_TRANTYPE.equals(tranType)){
				//销售订单
				tableName = "t_sc_order";
			}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
				//销售出库
				tableName = "t_sc_sl_stockbill";
			}else if (Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
				//销售退货
				tableName = "t_sc_sl_stockbill";
				if(amount.compareTo(BigDecimal.ZERO) < 0){
					amount = BigDecimal.ZERO.subtract(amount);
				}
			}else if(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.equals(tranType)){
				//销售换货
				tableName = "t_sc_ic_xsstockbill";

			}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
				//应收初始化
				tableName = "t_sc_begdata";
			}else if(Globals.SC_RP_RBILL.equals(tranType)){
				//应收调账
				tableName = "T_SC_RP_AdjustBill";
			}
			String updateSql = "update "+tableName+" set checkAmount = checkAmount - ("+amount+") where id = '"+idSrc+"'";
			this.updateBySqlString(updateSql);
		}
		this.deleteAllEntitie(tScRpRbillentry2OldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpRbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpRbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpRbillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScRpRbillEntity t){
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
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{billtype}",String.valueOf(t.getBillType()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
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