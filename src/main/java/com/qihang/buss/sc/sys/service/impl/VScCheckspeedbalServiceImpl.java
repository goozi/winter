package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.sys.service.VScCheckspeedbalServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sys.entity.VScCheckspeedbalEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("vScCheckspeedbalService")
@Transactional
public class VScCheckspeedbalServiceImpl extends CommonServiceImpl implements VScCheckspeedbalServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((VScCheckspeedbalEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((VScCheckspeedbalEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((VScCheckspeedbalEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(VScCheckspeedbalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(VScCheckspeedbalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(VScCheckspeedbalEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,VScCheckspeedbalEntity t){
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockid()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemid()));
 		sql  = sql.replace("#{batchno}",String.valueOf(t.getBatchno()));
 		sql  = sql.replace("#{qty}",String.valueOf(t.getQty()));
 		sql  = sql.replace("#{secqty}",String.valueOf(t.getSecqty()));
 		sql  = sql.replace("#{sonname}",String.valueOf(t.getSonname()));
 		sql  = sql.replace("#{departmentname}",String.valueOf(t.getDepartmentname()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{itemname}",String.valueOf(t.getItemname()));
 		sql  = sql.replace("#{itemnumber}",String.valueOf(t.getItemnumber()));
 		sql  = sql.replace("#{unitname}",String.valueOf(t.getUnitname()));
 		sql  = sql.replace("#{coefficient}",String.valueOf(t.getCoefficient()));
 		sql  = sql.replace("#{bigqty}",String.valueOf(t.getBigqty()));
 		sql  = sql.replace("#{smallqty}",String.valueOf(t.getSmallqty()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}