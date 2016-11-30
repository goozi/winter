
package com.qihang.buss.sc.rp.service.impl;
import com.qihang.buss.sc.rp.entity.*;
import com.qihang.buss.sc.rp.service.TScRpPbillServiceI;
import com.qihang.buss.sc.rp.service.TScRpPbillServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

import com.qihang.winter.core.constant.Globals;
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


@Service("tScRpPbillService")
@Transactional
public class TScRpPbillServiceImpl extends CommonServiceImpl implements TScRpPbillServiceI {

	public <T> void delete(T entity) {
		super.delete(entity);
		//执行删除操作配置的sql增强
		this.doDelSql((TScRpPbillEntity) entity);
	}

	public void addMain(TScRpPbillEntity tScRpPbill,
						List<TScRpPbillentry1Entity> tScRpPbillentry1List,List<TScRpPbillentry2Entity> tScRpPbillentry2List){
		//保存主信息
		this.save(tScRpPbill);

		/**保存-结算表*/
		for(TScRpPbillentry1Entity tScRpPbillentry1:tScRpPbillentry1List){
			//外键设置
			tScRpPbillentry1.setFid(tScRpPbill.getId());
			this.save(tScRpPbillentry1);
		}
		/**保存-应收表*/
		for(TScRpPbillentry2Entity tScRpPbillentry2:tScRpPbillentry2List){
			//外键设置
			if(null != tScRpPbillentry2.getFindex()) {
				tScRpPbillentry2.setFid(tScRpPbill.getId());
				this.save(tScRpPbillentry2);
				String idSrc = tScRpPbillentry2.getIdSrc();
				Integer tranType = Integer.parseInt(tScRpPbillentry2.getClassIdSrc());
				String tableName = "";
				BigDecimal amount = tScRpPbillentry2.getAmount();
				if(Globals.SC_PO_ORDER_TRANTYPE.equals(tranType)){
					//采购订单
					tableName = "t_sc_po_order";
				}else if(Globals.SC_PO_STOCKBILL_TRANTYPE.equals(tranType)){
					//采购入库
					tableName = "t_sc_po_stockbill";
				}else if (Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
					//采购退货
					tableName = "t_sc_po_stockbill";
					if(amount.compareTo(BigDecimal.ZERO) < 0){
						amount = BigDecimal.ZERO.subtract(amount);
					}
				}else if(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.equals(tranType)){
					//采购换货
					tableName = "t_sc_ic_jhstockbill";

				}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
					//应付初始化
					tableName = "t_sc_begdata";
				}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
					//销售出库物流信息
					tableName = "t_sc_sl_logistical";
				}else if(Globals.SC_RP_PBILL.equals(tranType)){
					//付款调账
					tableName = "T_SC_RP_AdjustBill";
				}


				String updateSql = "update "+tableName+" set checkAmount = ifnull(checkAmount,0) + "+amount+" where id = '"+idSrc+"'";
				this.updateBySqlString(updateSql);
			}
		}
		//执行新增操作配置的sql增强
		this.doAddSql(tScRpPbill);
	}


	public void updateMain(TScRpPbillEntity tScRpPbill,
						   List<TScRpPbillentry1Entity> tScRpPbillentry1List,List<TScRpPbillentry2Entity> tScRpPbillentry2List) {
		//保存主表信息
		this.saveOrUpdate(tScRpPbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpPbill.getId();
		Object id1 = tScRpPbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-结算表
		String hql0 = "from TScRpPbillentry1Entity where 1 = 1 AND fID = ? ";
		List<TScRpPbillentry1Entity> tScRpPbillentry1OldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-结算表
		for(TScRpPbillentry1Entity oldE:tScRpPbillentry1OldList){
			boolean isUpdate = false;
			for(TScRpPbillentry1Entity sendE:tScRpPbillentry1List){
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
		for(TScRpPbillentry1Entity tScRpPbillentry1:tScRpPbillentry1List){
			if(oConvertUtils.isEmpty(tScRpPbillentry1.getId())){
				//外键设置
				tScRpPbillentry1.setFid(tScRpPbill.getId());
				this.save(tScRpPbillentry1);
			}
		}
		//===================================================================================
		//1.查询出数据库的明细数据-应收表
		String hql1 = "from TScRpPbillentry2Entity where 1 = 1 AND fID = ? ";
		List<TScRpPbillentry2Entity> tScRpPbillentry2OldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-应收表
		for(TScRpPbillentry2Entity oldE:tScRpPbillentry2OldList){
			boolean isUpdate = false;
			for(TScRpPbillentry2Entity sendE:tScRpPbillentry2List){
				//需要更新的明细数据-应收表
				if(oldE.getId().equals(sendE.getId())){
					try {
						String idSrc = oldE.getIdSrc();
						Integer tranType = Integer.parseInt(oldE.getClassIdSrc());
						String tableName = "";
						BigDecimal oldAmount = oldE.getAmount();
						BigDecimal newAmount = sendE.getAmount();
						BigDecimal changeAmount = oldAmount.subtract(newAmount);
						if(Globals.SC_PO_ORDER_TRANTYPE.equals(tranType)){
							//采购订单
							tableName = "t_sc_po_order";
						}else if(Globals.SC_PO_STOCKBILL_TRANTYPE.equals(tranType)){
							//采购入库
							tableName = "t_sc_po_stockbill";
						}else if( Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
							//采购退货
							tableName = "t_sc_po_stockbill";
							if(changeAmount.compareTo(BigDecimal.ZERO) > 0){
								changeAmount = BigDecimal.ZERO.subtract(changeAmount);
							}
						}else if(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.equals(tranType)){
							//采购换货
							tableName = "t_sc_ic_jhstockbill";

						}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
							//应付初始化
							tableName = "t_sc_begdata";
						}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
							//销售出库物流信息
							tableName = "t_sc_sl_logistical";
						}else if(Globals.SC_RP_PBILL.equals(tranType)){
							//付款调账
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
				Integer tranType = Integer.parseInt(oldE.getClassIdSrc());
				String tableName = "";
				BigDecimal oldAmount = oldE.getAmount();
				if(Globals.SC_PO_ORDER_TRANTYPE.equals(tranType)){
					//采购订单
					tableName = "t_sc_po_order";
				}else if(Globals.SC_PO_STOCKBILL_TRANTYPE.equals(tranType)){
					//采购入库
					tableName = "t_sc_po_stockbill";
				}else if( Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
					//采购退货
					tableName = "t_sc_po_stockbill";
					if(oldAmount.compareTo(BigDecimal.ZERO) < 0){
						oldAmount = BigDecimal.ZERO.subtract(oldAmount);
					}
				}else if(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.equals(tranType)){
					//采购换货
					tableName = "t_sc_ic_jhstockbill";

				}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
					//应付初始化
					tableName = "t_sc_begdata";
				}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
					//销售出库物流信息
					tableName = "t_sc_sl_logistical";
				}else if(Globals.SC_RP_PBILL.equals(tranType)){
					//付款调账
					tableName = "T_SC_RP_AdjustBill";
				}
				String updateSql = "update "+tableName+" set checkAmount = checkAmount - "+oldAmount+" where id = '"+idSrc+"'";
				this.updateBySqlString(updateSql);
				super.delete(oldE);
			}

		}
		//3.持久化新增的数据-应收表
		for(TScRpPbillentry2Entity tScRpPbillentry2:tScRpPbillentry2List){
			if(oConvertUtils.isEmpty(tScRpPbillentry2.getId())){
				//外键设置
				if(null != tScRpPbillentry2.getFindex()) {
					tScRpPbillentry2.setFid(tScRpPbill.getId());
					this.save(tScRpPbillentry2);
					String idSrc = tScRpPbillentry2.getIdSrc();
					Integer tranType = Integer.parseInt(tScRpPbillentry2.getClassIdSrc());
					String tableName = "";
					BigDecimal amount = tScRpPbillentry2.getAmount();
					if(Globals.SC_PO_ORDER_TRANTYPE.equals(tranType)){
						//采购订单
						tableName = "t_sc_po_order";
					}else if(Globals.SC_PO_STOCKBILL_TRANTYPE.equals(tranType)){
						//采购入库
						tableName = "t_sc_po_stockbill";
					}else if (Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
						//采购退货
						tableName = "t_sc_po_stockbill";
						if(amount.compareTo(BigDecimal.ZERO) < 0){
							amount = BigDecimal.ZERO.subtract(amount);
						}
					}else if(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.equals(tranType)){
						//采购换货
						tableName = "t_sc_ic_jhstockbill";

					}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
						//应付初始化
						tableName = "t_sc_begdata";
					}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
						//销售出库物流信息
						tableName = "t_sc_sl_logistical";
					}else if(Globals.SC_RP_PBILL.equals(tranType)){
						//付款调账
						tableName = "T_SC_RP_AdjustBill";
					}
					String updateSql = "update "+tableName+" set checkAmount = ifnull(checkAmount,0) + "+amount+" where id = '"+idSrc+"'";
					this.updateBySqlString(updateSql);
				}
			}
		}
		//执行更新操作配置的sql增强
		this.doUpdateSql(tScRpPbill);
	}


	public void delMain(TScRpPbillEntity tScRpPbill) {
		//删除主表信息
		this.delete(tScRpPbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpPbill.getId();
		Object id1 = tScRpPbill.getId();
		//===================================================================================
		//删除-结算表
		String hql0 = "from TScRpPbillentry1Entity where 1 = 1 AND fID = ? ";
		List<TScRpPbillentry1Entity> tScRpPbillentry1OldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScRpPbillentry1OldList);
		//===================================================================================
		//删除-应收表
		String hql1 = "from TScRpPbillentry2Entity where 1 = 1 AND fID = ? ";
		List<TScRpPbillentry2Entity> tScRpPbillentry2OldList = this.findHql(hql1,id1);
		for(TScRpPbillentry2Entity entry : tScRpPbillentry2OldList){
			String idSrc = entry.getIdSrc();
			Integer tranType = Integer.parseInt(entry.getClassIdSrc());
			String tableName = "";
			BigDecimal amount = entry.getAmount();
			if(Globals.SC_PO_ORDER_TRANTYPE.equals(tranType)){
				//采购订单
				tableName = "t_sc_po_order";
			}else if(Globals.SC_PO_STOCKBILL_TRANTYPE.equals(tranType)){
				//采购入库
				tableName = "t_sc_po_stockbill";
			}else if (Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
				//采购退货
				tableName = "t_sc_po_stockbill";
				if(amount.compareTo(BigDecimal.ZERO) < 0){
					amount = BigDecimal.ZERO.subtract(amount);
				}
			}else if(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.equals(tranType)){
				//采购换货
				tableName = "t_sc_ic_jhstockbill";

			}else if(Globals.SC_IC_BEGDATA_TRANTYPE.equals(tranType)){
				//应付初始化
				tableName = "t_sc_begdata";
			}else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
				//销售出库物流信息
				tableName = "t_sc_sl_logistical";
			}else if(Globals.SC_RP_PBILL.equals(tranType)){
				//付款调账
				tableName = "T_SC_RP_AdjustBill";
			}
			String updateSql = "update "+tableName+" set checkAmount = checkAmount - ("+amount+") where id = '"+idSrc+"'";
			this.updateBySqlString(updateSql);
		}
		this.deleteAllEntitie(tScRpPbillentry2OldList);
	}


	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
	public boolean doAddSql(TScRpPbillEntity t){
		return true;
	}
	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
	public boolean doUpdateSql(TScRpPbillEntity t){
		return true;
	}
	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
	public boolean doDelSql(TScRpPbillEntity t){
		return true;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
	public String replaceVal(String sql,TScRpPbillEntity t){
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