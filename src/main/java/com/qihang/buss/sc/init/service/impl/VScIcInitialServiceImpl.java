package com.qihang.buss.sc.init.service.impl;
import com.qihang.buss.sc.init.service.VScIcInitialServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.init.entity.VScIcInitialEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("vIcInitialService")
@Transactional
public class VScIcInitialServiceImpl extends CommonServiceImpl implements VScIcInitialServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((VScIcInitialEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((VScIcInitialEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((VScIcInitialEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(VScIcInitialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(VScIcInitialEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(VScIcInitialEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,VScIcInitialEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTrantype()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillno()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockid()));
 		sql  = sql.replace("#{stockname}",String.valueOf(t.getStockname()));
 		sql  = sql.replace("#{indexnumber}",String.valueOf(t.getIndexnumber()));
 		sql  = sql.replace("#{entryitemno}",String.valueOf(t.getEntryitemno()));
 		sql  = sql.replace("#{entryitemname}",String.valueOf(t.getEntryitemname()));
 		sql  = sql.replace("#{model}",String.valueOf(t.getModel()));
 		sql  = sql.replace("#{barcode}",String.valueOf(t.getBarcode()));
 		sql  = sql.replace("#{batchno}",String.valueOf(t.getBatchno()));
 		sql  = sql.replace("#{unitid}",String.valueOf(t.getUnitid()));
 		sql  = sql.replace("#{unitname}",String.valueOf(t.getUnitname()));
 		sql  = sql.replace("#{qty}",String.valueOf(t.getQty()));
 		sql  = sql.replace("#{basicunitid}",String.valueOf(t.getBasicunitid()));
 		sql  = sql.replace("#{basicunitname}",String.valueOf(t.getBasicunitname()));
 		sql  = sql.replace("#{coefficient}",String.valueOf(t.getCoefficient()));
 		sql  = sql.replace("#{basicqty}",String.valueOf(t.getBasicqty()));
 		sql  = sql.replace("#{secunitid}",String.valueOf(t.getSecunitid()));
 		sql  = sql.replace("#{secunitname}",String.valueOf(t.getSecunitname()));
 		sql  = sql.replace("#{seccoefficient}",String.valueOf(t.getSeccoefficient()));
 		sql  = sql.replace("#{secqty}",String.valueOf(t.getSecqty()));
 		sql  = sql.replace("#{costprice}",String.valueOf(t.getCostprice()));
 		sql  = sql.replace("#{costamount}",String.valueOf(t.getCostamount()));
 		sql  = sql.replace("#{kfdate}",String.valueOf(t.getKfdate()));
 		sql  = sql.replace("#{kfperiod}",String.valueOf(t.getKfperiod()));
 		sql  = sql.replace("#{kfdatetypeinfo}",String.valueOf(t.getKfdatetypeinfo()));
 		sql  = sql.replace("#{perioddate}",String.valueOf(t.getPerioddate()));
 		sql  = sql.replace("#{entryexplanation}",String.valueOf(t.getEntryexplanation()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerid()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerid()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckdate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckstate()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonid()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{entryid}",String.valueOf(t.getEntryid()));
 		sql  = sql.replace("#{entryitemid}",String.valueOf(t.getEntryitemid()));
 		sql  = sql.replace("#{entrystockid}",String.valueOf(t.getEntrystockid()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{entrystockname}",String.valueOf(t.getEntrystockname()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}