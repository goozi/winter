
package com.qihang.buss.sc.rp.service.impl;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.rp.service.TScRpAdjustbillServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillentryEntity;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;

import java.util.UUID;


@Service("tScRpAdjustbillService")
@Transactional
public class TScRpAdjustbillServiceImpl extends CommonServiceImpl implements TScRpAdjustbillServiceI {

	@Autowired
	private CountCommonServiceI countCommonService;

	@Autowired
	private SessionFactory sessionFactory;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScRpAdjustbillEntity)entity);
 	}
	
	public void addMain(TScRpAdjustbillEntity tScRpAdjustbill,
	        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList){
			//修改  经办人  引用的次数
			countCommonService.addUpdateCount("T_SC_EMP",tScRpAdjustbill.getEmpId());
			//修改  部门 引用的次数
			countCommonService.addUpdateCount("T_SC_Department",tScRpAdjustbill.getDeptId());
			//修改 结算账户 引用的次数
			countCommonService.addUpdateCount("T_SC_SettleAcct",tScRpAdjustbill.getAccountId());
			//保存主信息
			this.save(tScRpAdjustbill);
			/**保存-应收调账从表*/
			for(TScRpAdjustbillentryEntity tScRpAdjustbillentry:tScRpAdjustbillentryList){
				//修改收支账户有引用的次数
				countCommonService.addUpdateCount("T_SC_Fee",tScRpAdjustbillentry.getExpId());
				//外键设置
				tScRpAdjustbillentry.setFid(tScRpAdjustbill.getId());
				this.save(tScRpAdjustbillentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScRpAdjustbill);
	}

	
	public void updateMain(TScRpAdjustbillEntity tScRpAdjustbill,
	        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList) {
		//保存主表信息
		TScRpAdjustbillEntity te = this.get(TScRpAdjustbillEntity.class,tScRpAdjustbill.getId());
		sessionFactory.getCurrentSession().evict(te);
		//如果不相等 就要 修改2着的引用次数
		if(!te.getEmpId().equals(tScRpAdjustbill.getEmpId())){
			//修改  经办人  引用的次数
			countCommonService.editUpdateCount("T_SC_EMP",te.getEmpId(),tScRpAdjustbill.getEmpId());
		}
		if (!te.getDeptId().equals(tScRpAdjustbill.getDeptId())){
			//修改  部门 引用的次数
			countCommonService.editUpdateCount("T_SC_Department",te.getDeptId(),tScRpAdjustbill.getDeptId());
		}
		if(null != te.getAccountId() && !te.getAccountId().equals(tScRpAdjustbill.getAccountId())){
			//修改 结算账户 引用的次数
			countCommonService.editUpdateCount("T_SC_SettleAcct",te.getAccountId(),tScRpAdjustbill.getAccountId());
		}
		this.saveOrUpdate(tScRpAdjustbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpAdjustbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-应收调账从表
	    String hql0 = "from TScRpAdjustbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-应收调账从表
		for(TScRpAdjustbillentryEntity oldE:tScRpAdjustbillentryOldList){
			boolean isUpdate = false;
				for(TScRpAdjustbillentryEntity sendE:tScRpAdjustbillentryList){
					//需要更新的明细数据-应收调账从表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							//编辑 时修改收支账户有引用的次数
							if(!oldE.getExpId().equals(sendE.getExpId())){
								countCommonService.editUpdateCount("T_SC_Fee",oldE.getId(),sendE.getId());
							}
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-应收调账从表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-应收调账从表
			for(TScRpAdjustbillentryEntity tScRpAdjustbillentry:tScRpAdjustbillentryList){
				if(oConvertUtils.isEmpty(tScRpAdjustbillentry.getId())){
					//增加 修改引用的次数
					countCommonService.addUpdateCount("T_SC_Fee",tScRpAdjustbillentry.getExpId());
					//外键设置
					tScRpAdjustbillentry.setFid(tScRpAdjustbill.getId());
					this.save(tScRpAdjustbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScRpAdjustbill);
	}

	
	public void delMain(TScRpAdjustbillEntity tScRpAdjustbill) {
		//修改  经办人  引用的次数
		countCommonService.deleteUpdateCount("T_SC_EMP",tScRpAdjustbill.getEmpId());
		//修改  部门 引用的次数
		countCommonService.deleteUpdateCount("T_SC_Department",tScRpAdjustbill.getDeptId());
		//修改 结算账户 引用的次数
		countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpAdjustbill.getAccountId());
		//删除主表信息
		this.delete(tScRpAdjustbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpAdjustbill.getId();
		//===================================================================================
		//删除-应收调账从表
	    String hql0 = "from TScRpAdjustbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryOldList = this.findHql(hql0,id0);
		//修改收支账户有引用的次数
		for (TScRpAdjustbillentryEntity e : tScRpAdjustbillentryOldList){
				countCommonService.deleteUpdateCount("T_SC_Fee",e.getExpId());
		}
		this.deleteAllEntitie(tScRpAdjustbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpAdjustbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpAdjustbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpAdjustbillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScRpAdjustbillEntity t){
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
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{classidsrc}",String.valueOf(t.getClassIdSrc()));
 		sql  = sql.replace("#{idsrc}",String.valueOf(t.getIdSrc()));
 		sql  = sql.replace("#{billnosrc}",String.valueOf(t.getBillNoSrc()));
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