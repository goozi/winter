
package com.qihang.buss.sc.financing.service.impl;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.financing.entity.TScRpExpensesapplyentryEntity;
import com.qihang.buss.sc.financing.service.TScRpPotherbillServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillentryEntity;

import com.qihang.winter.web.system.service.SystemService;
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


@Service("tScRpPotherbillService")
@Transactional
public class TScRpPotherbillServiceImpl extends CommonServiceImpl implements TScRpPotherbillServiceI {

	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private SystemService systemService;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScRpPotherbillEntity)entity);
 	}
	
	public void addMain(TScRpPotherbillEntity tScRpPotherbill,
	        List<TScRpPotherbillentryEntity> tScRpPotherbillentryList){
			if(tScRpPotherbill.getItemClassId() == 1){//职员
				//修改  经办人  引用的次数
				countCommonService.addUpdateCount("T_SC_EMP",tScRpPotherbill.getItemId());
			}
			if(tScRpPotherbill.getItemClassId() == 2){//部门
				//修改  经办人  引用的次数
				countCommonService.addUpdateCount("T_SC_Department",tScRpPotherbill.getDeptId());
			}
			//修改  经办人  引用的次数
			countCommonService.addUpdateCount("T_SC_EMP",tScRpPotherbill.getEmpId());
			//修改  部门 引用的次数
			countCommonService.addUpdateCount("T_SC_Department",tScRpPotherbill.getDeptId());
			//修改 结算账户引用的次数
			countCommonService.addUpdateCount("T_SC_SettleAcct",tScRpPotherbill.getAccountId());
			//保存主信息
			this.save(tScRpPotherbill);
		
			/**保存-费用开支单明细*/
			for(TScRpPotherbillentryEntity tScRpPotherbillentry:tScRpPotherbillentryList){
				//修改收支账户有引用的次数
				countCommonService.addUpdateCount("T_SC_Fee",tScRpPotherbillentry.getExpId());
				//外键设置
				tScRpPotherbillentry.setFid(tScRpPotherbill.getId());
				this.save(tScRpPotherbillentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScRpPotherbill);
	}

	
	public void updateMain(TScRpPotherbillEntity tScRpPotherbill,
	        List<TScRpPotherbillentryEntity> tScRpPotherbillentryList) {
		//保存主表信息
		TScRpPotherbillEntity te = this.get(TScRpPotherbillEntity.class,tScRpPotherbill.getId());
		sessionFactory.getCurrentSession().evict(te);
		if(tScRpPotherbill.getItemClassId() == 1){//修改职员引用次数
			if(!te.getItemId().equals(tScRpPotherbill.getItemId())) {
				countCommonService.editUpdateCount("T_SC_EMP", te.getItemId(), tScRpPotherbill.getItemId());
			}
		}
		if(tScRpPotherbill.getItemClassId() == 2){//修改部门引用次数
			if(!te.getItemId().equals(tScRpPotherbill.getItemId())) {
				countCommonService.editUpdateCount("T_SC_Department", te.getItemId(), tScRpPotherbill.getItemId());
			}
		}
		//修改  经办人  引用的次数
		if (!te.getEmpId().equals(tScRpPotherbill.getEmpId())){
			countCommonService.editUpdateCount("T_SC_EMP",te.getEmpId(),tScRpPotherbill.getEmpId());
		}
		if (!te.getDeptId().equals(tScRpPotherbill.getDeptId())){
			//修改  部门 引用的次数
			countCommonService.editUpdateCount("T_SC_Department",te.getDeptId(),tScRpPotherbill.getDeptId());
		}
		if(!te.getAccountId().equals(tScRpPotherbill.getAccountId())){
			//修改 结算账户引用的次数
			countCommonService.addUpdateCount("T_SC_SettleAcct",tScRpPotherbill.getAccountId());
		}
		//保存主表信息
		this.saveOrUpdate(tScRpPotherbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpPotherbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-费用开支单明细
	    String hql0 = "from TScRpPotherbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpPotherbillentryEntity> tScRpPotherbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-费用开支单明细
		for(TScRpPotherbillentryEntity oldE:tScRpPotherbillentryOldList){
			boolean isUpdate = false;
				for(TScRpPotherbillentryEntity sendE:tScRpPotherbillentryList){
					//需要更新的明细数据-费用开支单明细
					if(oldE.getId().equals(sendE.getId())){
		    			try {

							//编辑 时修改收支账户有引用的次数
							if(!oldE.getExpId().equals(sendE.getExpId())){
								countCommonService.editUpdateCount("T_SC_Fee",oldE.getId(),sendE.getId());
							}
							//从新计算
							TScRpExpensesapplyentryEntity applyEnter = systemService.get(TScRpExpensesapplyentryEntity.class,oldE.getFidSrc() );
							if(applyEnter != null){
								applyEnter.setHaveAmount(applyEnter.getHaveAmount().subtract(oldE.getAmount()).add(sendE.getAmount()));
								applyEnter.setNotAmount(applyEnter.getNotAmount().add(oldE.getAmount()).subtract(sendE.getAmount()));
								systemService.updateEntitie(applyEnter);
							}
//							StringBuilder str = new StringBuilder();
//							str.append(" update t_sc_rp_expensesapplyEntry set ");
//							str.append(" haveAmount = (haveAmount-"+oldE.getAmount() + "+"+sendE.getAmount()+"), ");
//							str.append(" notAmount=(notAmount+"+oldE.getAmount()+"-"+sendE.getAmount()+") ");
//							str.append(" where id=? ");
//							systemService.executeSql(str.toString(),oldE.getFidSrc() );

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
					//删除时修改引用的次数
					countCommonService.deleteUpdateCount("T_SC_Fee",oldE.getId());
		    		//如果数据库存在的明细，前台没有传递过来则是删除-费用开支单明细
					//删除时从新计算
					TScRpExpensesapplyentryEntity applyEnter = systemService.get(TScRpExpensesapplyentryEntity.class,oldE.getFidSrc()  );
					if(applyEnter != null){
						applyEnter.setHaveAmount(applyEnter.getHaveAmount().subtract(oldE.getAmount()));
						applyEnter.setNotAmount(applyEnter.getNotAmount().add(oldE.getAmount()));
						systemService.updateEntitie(applyEnter);
					}
//					StringBuilder str = new StringBuilder();
//					str.append(" update t_sc_rp_expensesapplyEntry set ");
//					str.append(" haveAmount = haveAmount-"+oldE.getAmount() + " ,");
//					str.append(" notAmount=notAmount+"+oldE.getAmount()+" ");
//					str.append(" where id=? ");
//					systemService.executeSql(str.toString(),oldE.getFidSrc() );
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-费用开支单明细
			for(TScRpPotherbillentryEntity tScRpPotherbillentry:tScRpPotherbillentryList){
				if(oConvertUtils.isEmpty(tScRpPotherbillentry.getId())){
					//删除时从新计算
					if(!StringUtil.isEmpty(tScRpPotherbillentry.getFidSrc())) {
						TScRpExpensesapplyentryEntity applyEnter = systemService.get(TScRpExpensesapplyentryEntity.class,tScRpPotherbillentry.getFidSrc());
						if(applyEnter != null){
							applyEnter.setHaveAmount(applyEnter.getHaveAmount().add(tScRpPotherbillentry.getAmount()));
							applyEnter.setNotAmount(applyEnter.getNotAmount().subtract(tScRpPotherbillentry.getAmount()));
							systemService.updateEntitie(applyEnter);
						}
//						StringBuilder str = new StringBuilder();
//						str.append(" update t_sc_rp_expensesapplyEntry set ");
//						str.append(" haveAmount = haveAmount+" + tScRpPotherbillentry.getAmount() + " ,");
//						str.append(" notAmount=notAmount-" + tScRpPotherbillentry.getAmount() + " ");
//						str.append(" where id=? ");
//						systemService.executeSql(str.toString(), tScRpPotherbillentry.getFidSrc());
					}
					//增加 修改引用的次数
					countCommonService.addUpdateCount("T_SC_Fee",tScRpPotherbillentry.getExpId());
					//外键设置
					tScRpPotherbillentry.setFid(tScRpPotherbill.getId());
					this.save(tScRpPotherbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScRpPotherbill);
	}

	
	public void delMain(TScRpPotherbillEntity tScRpPotherbill) {
		if(tScRpPotherbill.getItemClassId() == 1){//职员
			//修改  经办人  引用的次数
			countCommonService.deleteUpdateCount("T_SC_EMP",tScRpPotherbill.getItemId());
		}
		if(tScRpPotherbill.getItemClassId() == 2){//部门
			//修改  经办人  引用的次数
			countCommonService.deleteUpdateCount("T_SC_Department",tScRpPotherbill.getDeptId());
		}
		//修改  经办人  引用的次数
		countCommonService.deleteUpdateCount("T_SC_EMP",tScRpPotherbill.getEmpId());
		//修改  部门 引用的次数
		countCommonService.deleteUpdateCount("T_SC_Department",tScRpPotherbill.getDeptId());
		//修改 结算账户引用的次数
		countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpPotherbill.getAccountId());
		//删除主表信息
		this.delete(tScRpPotherbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpPotherbill.getId();
		//===================================================================================
		//删除-费用开支单明细
	    String hql0 = "from TScRpPotherbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpPotherbillentryEntity> tScRpPotherbillentryOldList = this.findHql(hql0,id0);
		//修改收支账户有引用的次数
		for (TScRpPotherbillentryEntity e : tScRpPotherbillentryOldList){
			countCommonService.deleteUpdateCount("T_SC_Fee",e.getExpId());
		}
		this.deleteAllEntitie(tScRpPotherbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpPotherbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpPotherbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpPotherbillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScRpPotherbillEntity t){
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
 		sql  = sql.replace("#{itemclassid}",String.valueOf(t.getItemClassId()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemId()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountId()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{classidsrc}",String.valueOf(t.getClassIdSrc()));
 		sql  = sql.replace("#{interidsrc}",String.valueOf(t.getInterIdSrc()));
 		sql  = sql.replace("#{billnosrc}",String.valueOf(t.getBillNoSrc()));
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
}