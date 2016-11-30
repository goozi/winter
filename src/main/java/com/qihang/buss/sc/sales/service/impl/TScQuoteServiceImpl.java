
package com.qihang.buss.sc.sales.service.impl;
import com.qihang.buss.sc.sales.service.TScQuoteServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sales.entity.TScQuoteEntity;
import com.qihang.buss.sc.sales.entity.TScQuotecustomerEntity;
import com.qihang.buss.sc.sales.entity.TScQuoteitemsEntity;

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


@Service("tScQuoteService")
@Transactional
public class TScQuoteServiceImpl extends CommonServiceImpl implements TScQuoteServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScQuoteEntity)entity);
 	}
	
	public void addMain(TScQuoteEntity tScQuote,
	        List<TScQuotecustomerEntity> tScQuotecustomerList,List<TScQuoteitemsEntity> tScQuoteitemsList){
			//保存主信息
			this.save(tScQuote);
		
			/**保存-报价单客户*/
			for(TScQuotecustomerEntity tScQuotecustomer:tScQuotecustomerList){
				//外键设置
				tScQuotecustomer.setFid(tScQuote.getId());
				this.save(tScQuotecustomer);
			}
			/**保存-报价单商品*/
			for(TScQuoteitemsEntity tScQuoteitems:tScQuoteitemsList){
				//外键设置
				tScQuoteitems.setFid(tScQuote.getId());
				this.save(tScQuoteitems);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScQuote);
	}

	
	public void updateMain(TScQuoteEntity tScQuote,
	        List<TScQuotecustomerEntity> tScQuotecustomerList,List<TScQuoteitemsEntity> tScQuoteitemsList) {
		//保存主表信息
		this.saveOrUpdate(tScQuote);
		//===================================================================================
		//获取参数
		Object id0 = tScQuote.getId();
		Object id1 = tScQuote.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-报价单客户
	    String hql0 = "from TScQuotecustomerEntity where 1 = 1 AND fID = ? ";
	    List<TScQuotecustomerEntity> tScQuotecustomerOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-报价单客户
		for(TScQuotecustomerEntity oldE:tScQuotecustomerOldList){
			boolean isUpdate = false;
				for(TScQuotecustomerEntity sendE:tScQuotecustomerList){
					//需要更新的明细数据-报价单客户
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-报价单客户
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-报价单客户
			for(TScQuotecustomerEntity tScQuotecustomer:tScQuotecustomerList){
				if(oConvertUtils.isEmpty(tScQuotecustomer.getId())){
					//外键设置
					tScQuotecustomer.setFid(tScQuote.getId());
					this.save(tScQuotecustomer);
				}
			}
		//===================================================================================
		//1.查询出数据库的明细数据-报价单商品
	    String hql1 = "from TScQuoteitemsEntity where 1 = 1 AND fID = ? ";
	    List<TScQuoteitemsEntity> tScQuoteitemsOldList = this.findHql(hql1,id1);
		//2.筛选更新明细数据-报价单商品
		for(TScQuoteitemsEntity oldE:tScQuoteitemsOldList){
			boolean isUpdate = false;
				for(TScQuoteitemsEntity sendE:tScQuoteitemsList){
					//需要更新的明细数据-报价单商品
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-报价单商品
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-报价单商品
			for(TScQuoteitemsEntity tScQuoteitems:tScQuoteitemsList){
				if(oConvertUtils.isEmpty(tScQuoteitems.getId())){
					//外键设置
					tScQuoteitems.setFid(tScQuote.getId());
					this.save(tScQuoteitems);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScQuote);
	}

	
	public void delMain(TScQuoteEntity tScQuote) {
		//删除主表信息
		this.delete(tScQuote);
		//===================================================================================
		//获取参数
		Object id0 = tScQuote.getId();
		Object id1 = tScQuote.getId();
		//===================================================================================
		//删除-报价单客户
	    String hql0 = "from TScQuotecustomerEntity where 1 = 1 AND fID = ? ";
	    List<TScQuotecustomerEntity> tScQuotecustomerOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScQuotecustomerOldList);
		//===================================================================================
		//删除-报价单商品
	    String hql1 = "from TScQuoteitemsEntity where 1 = 1 AND fID = ? ";
	    List<TScQuoteitemsEntity> tScQuoteitemsOldList = this.findHql(hql1,id1);
		this.deleteAllEntitie(tScQuoteitemsOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScQuoteEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScQuoteEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScQuoteEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScQuoteEntity t){
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
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{inuredate}",String.valueOf(t.getInureDate()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}