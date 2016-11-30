
package com.qihang.buss.sc.financing.service.impl;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillentryEntity;
import com.qihang.buss.sc.financing.service.TScRpExpensesapplyServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.financing.entity.TScRpExpensesapplyEntity;
import com.qihang.buss.sc.financing.entity.TScRpExpensesapplyentryEntity;

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


@Service("tScRpExpensesapplyService")
@Transactional
public class TScRpExpensesapplyServiceImpl extends CommonServiceImpl implements TScRpExpensesapplyServiceI {
	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private SessionFactory sessionFactory;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScRpExpensesapplyEntity)entity);
 	}
	
	public void addMain(TScRpExpensesapplyEntity tScRpExpensesapply,
	        List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryList){
			if(tScRpExpensesapply.getItemClassId() == 1){//职员
				//修改  经办人  引用的次数
				countCommonService.addUpdateCount("T_SC_EMP",tScRpExpensesapply.getItemId());
			}
			if(tScRpExpensesapply.getItemClassId() == 2){//部门
				//修改  经办人  引用的次数
				countCommonService.addUpdateCount("T_SC_Department",tScRpExpensesapply.getDeptId());
			}
			//修改  经办人  引用的次数
			countCommonService.addUpdateCount("T_SC_EMP",tScRpExpensesapply.getEmpId());
			//修改  部门 引用的次数
			countCommonService.addUpdateCount("T_SC_Department",tScRpExpensesapply.getDeptId());
			//保存主信息
			this.save(tScRpExpensesapply);
			/**保存-费用申报单明细*/
			for(TScRpExpensesapplyentryEntity tScRpExpensesapplyentry:tScRpExpensesapplyentryList){
				//修改收支账户有引用的次数
				countCommonService.addUpdateCount("T_SC_Fee",tScRpExpensesapplyentry.getExpId());
				//外键设置
				tScRpExpensesapplyentry.setFid(tScRpExpensesapply.getId());
				this.save(tScRpExpensesapplyentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScRpExpensesapply);
	}

	
	public void updateMain(TScRpExpensesapplyEntity tScRpExpensesapply,
	        List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryList) {
		//保存主表信息
		TScRpExpensesapplyEntity te = this.get(TScRpExpensesapplyEntity.class,tScRpExpensesapply.getId());
		sessionFactory.getCurrentSession().evict(te);
		if(tScRpExpensesapply.getItemClassId() == 1){//修改职员引用次数
			if(!te.getItemId().equals(tScRpExpensesapply.getItemId())) {
				countCommonService.editUpdateCount("T_SC_EMP", te.getItemId(), tScRpExpensesapply.getItemId());
			}
		}
		if(tScRpExpensesapply.getItemClassId() == 2){//修改部门引用次数
			if(!te.getItemId().equals(tScRpExpensesapply.getItemId())) {
				countCommonService.editUpdateCount("T_SC_Department", te.getItemId(), tScRpExpensesapply.getItemId());
			}
		}
		//修改  经办人  引用的次数
		if (!te.getEmpId().equals(tScRpExpensesapply.getEmpId())){
			countCommonService.editUpdateCount("T_SC_EMP",te.getEmpId(),tScRpExpensesapply.getEmpId());
		}
		if (!te.getDeptId().equals(tScRpExpensesapply.getDeptId())){
			//修改  部门 引用的次数
			countCommonService.editUpdateCount("T_SC_Department",te.getDeptId(),tScRpExpensesapply.getDeptId());
		}
		//保存主表信息
		this.saveOrUpdate(tScRpExpensesapply);
		//===================================================================================
		//获取参数
		Object id0 = tScRpExpensesapply.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-费用申报单明细
	    String hql0 = "from TScRpExpensesapplyentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-费用申报单明细
		for(TScRpExpensesapplyentryEntity oldE:tScRpExpensesapplyentryOldList){
			boolean isUpdate = false;
				for(TScRpExpensesapplyentryEntity sendE:tScRpExpensesapplyentryList){
					//需要更新的明细数据-费用申报单明细
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-费用申报单明细
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-费用申报单明细
			for(TScRpExpensesapplyentryEntity tScRpExpensesapplyentry:tScRpExpensesapplyentryList){
				if(oConvertUtils.isEmpty(tScRpExpensesapplyentry.getId())){
					//增加 修改引用的次数
					countCommonService.addUpdateCount("T_SC_Fee",tScRpExpensesapplyentry.getExpId());
					//外键设置
					tScRpExpensesapplyentry.setFid(tScRpExpensesapply.getId());
					this.save(tScRpExpensesapplyentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScRpExpensesapply);
	}

	
	public void delMain(TScRpExpensesapplyEntity tScRpExpensesapply) {

		if(tScRpExpensesapply.getItemClassId() == 1){//职员
			//修改  经办人  引用的次数
			countCommonService.deleteUpdateCount("T_SC_EMP",tScRpExpensesapply.getItemId());
		}
		if(tScRpExpensesapply.getItemClassId() == 2){//部门
			//修改  经办人  引用的次数
			countCommonService.deleteUpdateCount("T_SC_Department",tScRpExpensesapply.getDeptId());
		}
		//修改  经办人  引用的次数
		countCommonService.deleteUpdateCount("T_SC_EMP",tScRpExpensesapply.getEmpId());
		//修改  部门 引用的次数
		countCommonService.deleteUpdateCount("T_SC_Department",tScRpExpensesapply.getDeptId());
		//删除主表信息
		this.delete(tScRpExpensesapply);
		//===================================================================================
		//获取参数
		Object id0 = tScRpExpensesapply.getId();
		//===================================================================================
		//删除-费用申报单明细
	    String hql0 = "from TScRpExpensesapplyentryEntity where 1 = 1 AND fID = ? ";
	    List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryOldList = this.findHql(hql0,id0);
		//修改收支账户有引用的次数
		for (TScRpExpensesapplyentryEntity e : tScRpExpensesapplyentryOldList){
			countCommonService.deleteUpdateCount("T_SC_Fee",e.getExpId());
		}
		this.deleteAllEntitie(tScRpExpensesapplyentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpExpensesapplyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpExpensesapplyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpExpensesapplyEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScRpExpensesapplyEntity t){
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
}