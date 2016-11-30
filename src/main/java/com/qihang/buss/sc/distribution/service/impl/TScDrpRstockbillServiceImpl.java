
package com.qihang.buss.sc.distribution.service.impl;
import com.qihang.buss.sc.distribution.entity.TScDrpRstockbillEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpRstockbillentryEntity;
import com.qihang.buss.sc.distribution.service.TScDrpRstockbillServiceI;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service("tScDrpRstockbillService")
@Transactional
public class TScDrpRstockbillServiceImpl extends CommonServiceImpl implements TScDrpRstockbillServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScDrpRstockbillEntity)entity);
 	}
	
	public void addMain(TScDrpRstockbillEntity tScDrpRstockbill,
	        List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryList){
			//保存主信息
			this.save(tScDrpRstockbill);
		
			/**保存-退货申请*/
			for(TScDrpRstockbillentryEntity tScDrpRstockbillentry:tScDrpRstockbillentryList){
				//外键设置
				tScDrpRstockbillentry.setInterId(tScDrpRstockbill.getId());
				this.save(tScDrpRstockbillentry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScDrpRstockbill);
	}

	
	public void updateMain(TScDrpRstockbillEntity tScDrpRstockbill,
	        List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryList) {
		//保存主表信息
		this.saveOrUpdate(tScDrpRstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScDrpRstockbill.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-退货申请
	    String hql0 = "from TScDrpRstockbillentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-退货申请
		for(TScDrpRstockbillentryEntity oldE:tScDrpRstockbillentryOldList){
			boolean isUpdate = false;
				for(TScDrpRstockbillentryEntity sendE:tScDrpRstockbillentryList){
					//需要更新的明细数据-退货申请
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-退货申请
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-退货申请
			for(TScDrpRstockbillentryEntity tScDrpRstockbillentry:tScDrpRstockbillentryList){
				if(oConvertUtils.isEmpty(tScDrpRstockbillentry.getId())){
					//外键设置
					tScDrpRstockbillentry.setInterId(tScDrpRstockbill.getId());
					this.save(tScDrpRstockbillentry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScDrpRstockbill);
	}

	
	public void delMain(TScDrpRstockbillEntity tScDrpRstockbill) {
		//删除主表信息
		this.delete(tScDrpRstockbill);
		//===================================================================================
		//获取参数
		Object id0 = tScDrpRstockbill.getId();
		//===================================================================================
		//删除-退货申请
	    String hql0 = "from TScDrpRstockbillentryEntity where 1 = 1 AND iNTERID = ? ";
	    List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScDrpRstockbillentryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDrpRstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDrpRstockbillEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDrpRstockbillEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScDrpRstockbillEntity t){
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
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getrStockId()));
 		sql  = sql.replace("#{freight}",String.valueOf(t.getFreight()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{weight}",String.valueOf(t.getWeight()));
 		sql  = sql.replace("#{classid_src}",String.valueOf(t.getClassIdSrc()));
 		sql  = sql.replace("#{interid_src}",String.valueOf(t.getInterIdSrc()));
 		sql  = sql.replace("#{billno_src}",String.valueOf(t.getBillNoSrc()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
 		sql  = sql.replace("#{billerid }",String.valueOf(t.getBillerId ()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate }",String.valueOf(t.getCheckState ()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}