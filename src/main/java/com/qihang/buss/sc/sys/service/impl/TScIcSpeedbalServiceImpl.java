package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.sys.service.TScIcSpeedbalServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tScIcSpeedbalService")
@Transactional
public class TScIcSpeedbalServiceImpl extends CommonServiceImpl implements TScIcSpeedbalServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScIcSpeedbalEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScIcSpeedbalEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScIcSpeedbalEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcSpeedbalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcSpeedbalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcSpeedbalEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScIcSpeedbalEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{billid}",String.valueOf(t.getBillId()));
 		sql  = sql.replace("#{billentryid}",String.valueOf(t.getBillEntryId()));
 		sql  = sql.replace("#{billcreatetime}",String.valueOf(t.getBillCreateTime()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemId()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockId()));
 		sql  = sql.replace("#{batchno}",String.valueOf(t.getBatchNo()));
 		sql  = sql.replace("#{qty}",String.valueOf(t.getQty()));
 		sql  = sql.replace("#{secqty}",String.valueOf(t.getSecQty()));
 		sql  = sql.replace("#{price}",String.valueOf(t.getPrice()));
 		sql  = sql.replace("#{amount}",String.valueOf(t.getAmount()));
 		sql  = sql.replace("#{eprice}",String.valueOf(t.getEPrice()));
 		sql  = sql.replace("#{eamount}",String.valueOf(t.getEAmount()));
 		sql  = sql.replace("#{diffamount}",String.valueOf(t.getDiffAmount()));
 		sql  = sql.replace("#{blid_src}",String.valueOf(t.getBlidSrc()));
 		sql  = sql.replace("#{flag}",String.valueOf(t.getFlag()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{negflag}",String.valueOf(t.getNegFlag()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}