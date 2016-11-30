package com.qihang.buss.sc.sysaudit.service.impl;
import com.qihang.buss.sc.sysaudit.service.TSBillInfoServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tSBillInfoService")
@Transactional
public class TSBillInfoServiceImpl extends CommonServiceImpl implements TSBillInfoServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSBillInfoEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TSBillInfoEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TSBillInfoEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TSBillInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSBillInfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSBillInfoEntity t){
	 	return true;
 	}

	@Override
	public void updatePriceInfo(TSBillInfoEntity billInfoEntity) {
		String id = billInfoEntity.getId();//单据id
		List<TSBillInfoEntity> billInfo = this.findHql("from TSBillInfoEntity where id = ?", new Object[]{id});
		String infoId = "";
		infoId = getChildInfo(billInfo,infoId);
		if(StringUtils.isNotEmpty(infoId)){
			infoId = infoId.substring(0,infoId.length()-1);
			String updateSQL = "update T_S_Bill_Info set price_Field = '" + billInfoEntity.getPriceField() + "' where id in (" + infoId + ")";
			updateBySqlString(updateSQL);
		}
	}

	//迭代数据
	public String getChildInfo(List<TSBillInfoEntity> billInfo ,String infoId){
		for(TSBillInfoEntity billInfoEntity : billInfo){
			if(billInfoEntity.getTSBillInfoEntitys() == null || billInfoEntity.getTSBillInfoEntitys().size() == 0){
				infoId += "'"+billInfoEntity.getId()+"',";
			}else{
				infoId = getChildInfo(billInfoEntity.getTSBillInfoEntitys(),infoId);
			}
		}
		return infoId;
	}
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TSBillInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{bill_name}",String.valueOf(t.getBillName()));
 		sql  = sql.replace("#{bill_id}",String.valueOf(t.getBillId()));
 		sql  = sql.replace("#{pid}",String.valueOf(t.getTSBillInfoEntity().getId()));
 		sql  = sql.replace("#{prefix}",String.valueOf(t.getPrefix()));
 		sql  = sql.replace("#{date_formatter}",String.valueOf(t.getDateFormatter()));
 		sql  = sql.replace("#{bill_no_len}",String.valueOf(t.getBillNoLen()));
 		sql  = sql.replace("#{is_edit}",String.valueOf(t.getIsEdit()));
 		sql  = sql.replace("#{is_off_on}",String.valueOf(t.getIsOffOn()));
 		sql  = sql.replace("#{back_zero}",String.valueOf(t.getBackZero()));
 		sql  = sql.replace("#{bill_no}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}