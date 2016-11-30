
package com.qihang.buss.sc.financing.service.impl;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillentryEntity;
import com.qihang.buss.sc.financing.service.TScRpRotherbillServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.web.system.service.SystemService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import java.util.UUID;


@Service("tScRpRotherbillService")
@Transactional
public class TScRpRotherbillServiceImpl extends CommonServiceImpl implements TScRpRotherbillServiceI {

	@Autowired
	private SystemService systemService;
	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private SessionFactory sessionFactory;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScRpRotherbillEntity)entity);
 	}
	
	public void addMain(TScRpRotherbillEntity tScRpRotherbill,
	        List<TScRpRotherbillentryEntity> tScRpRotherbillentryList){
			//修改  经办人  引用的次数
			countCommonService.addUpdateCount("T_SC_EMP",tScRpRotherbill.getEmpId());
			//修改  部门 引用的次数
			countCommonService.addUpdateCount("T_SC_Department",tScRpRotherbill.getDeptId());
			//修改 结算账户 引用的次数
			countCommonService.addUpdateCount("T_SC_SettleAcct",tScRpRotherbill.getAccountId());
			//保存主信息
			this.save(tScRpRotherbill);
		
			/**保存-资金其他收入单*/
			for(TScRpRotherbillentryEntity tScRpRotherbillentry:tScRpRotherbillentryList){
				//修改收支账户有引用的次数
				countCommonService.addUpdateCount("T_SC_Fee",tScRpRotherbillentry.getExpId());
				//外键设置
				tScRpRotherbillentry.setFid(tScRpRotherbill.getId());
				this.save(tScRpRotherbillentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScRpRotherbill);
	}

	
	public void updateMain(TScRpRotherbillEntity tScRpRotherbill,
	        List<TScRpRotherbillentryEntity> tScRpRotherbillentryList) {
		//保存主表信息
		TScRpRotherbillEntity te = this.get(TScRpRotherbillEntity.class,tScRpRotherbill.getId());
		sessionFactory.getCurrentSession().evict(te);
		//修改  经办人  引用的次数
		if (!te.getEmpId().equals(tScRpRotherbill.getEmpId())){
			countCommonService.editUpdateCount("T_SC_EMP",te.getEmpId(),tScRpRotherbill.getEmpId());
		}
		if (!te.getDeptId().equals(tScRpRotherbill.getDeptId())){
			//修改  部门 引用的次数
			countCommonService.editUpdateCount("T_SC_Department",te.getDeptId(),tScRpRotherbill.getDeptId());
		}
		if (te.getAccountId().equals(tScRpRotherbill.getAccountId())){
			//修改 结算账户 引用的次数
			countCommonService.editUpdateCount("T_SC_SettleAcct",te.getAccountId(),tScRpRotherbill.getAccountId());
		}
		//保存主表信息
		this.saveOrUpdate(tScRpRotherbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpRotherbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-资金其他收入单
	    String hql0 = "from TScRpRotherbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpRotherbillentryEntity> tScRpRotherbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-资金其他收入单
		for(TScRpRotherbillentryEntity oldE:tScRpRotherbillentryOldList){
			boolean isUpdate = false;
				for(TScRpRotherbillentryEntity sendE:tScRpRotherbillentryList){
					//需要更新的明细数据-资金其他收入单
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-资金其他收入单
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-资金其他收入单
			for(TScRpRotherbillentryEntity tScRpRotherbillentry:tScRpRotherbillentryList){
				if(oConvertUtils.isEmpty(tScRpRotherbillentry.getId())){
					//增加 修改引用的次数
					countCommonService.addUpdateCount("T_SC_Fee",tScRpRotherbillentry.getExpId());
					//外键设置
					tScRpRotherbillentry.setFid(tScRpRotherbill.getId());
					this.save(tScRpRotherbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScRpRotherbill);
	}

	
	public void delMain(TScRpRotherbillEntity tScRpRotherbill) {
		//修改  经办人  引用的次数
		countCommonService.deleteUpdateCount("T_SC_EMP",tScRpRotherbill.getEmpId());
		//修改  部门 引用的次数
		countCommonService.deleteUpdateCount("T_SC_Department",tScRpRotherbill.getDeptId());
		//修改 转出账户 引用的次数
		countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpRotherbill.getAccountId());
		//删除主表信息
		this.delete(tScRpRotherbill);
		//===================================================================================
		//获取参数
		Object id0 = tScRpRotherbill.getId();
		//===================================================================================
		//删除-资金其他收入单
	    String hql0 = "from TScRpRotherbillentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpRotherbillentryEntity> tScRpRotherbillentryOldList = this.findHql(hql0,id0);
		//修改收支账户有引用的次数
		for (TScRpRotherbillentryEntity e : tScRpRotherbillentryOldList){
			countCommonService.deleteUpdateCount("T_SC_Fee",e.getExpId());
		}
		this.deleteAllEntitie(tScRpRotherbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpRotherbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpRotherbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpRotherbillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScRpRotherbillEntity t){
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
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountId()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
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
	@Override
	public void addRotherbill(TScRpRotherbillEntity tScRpRotherbill, List<TScRpRotherbillentryEntity> tScRpRotherbillentryList){
		//保存主表信息
		this.save(tScRpRotherbill);
		for(TScRpRotherbillentryEntity t : tScRpRotherbillentryList){
			t.setFid(tScRpRotherbill.getId());
			this.save(t);
		}
	}
}