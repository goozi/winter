package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.sys.service.VScCheckstageServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sys.entity.VScCheckstageEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("vScCheckstageService")
@Transactional
public class VScCheckstageServiceImpl extends CommonServiceImpl implements VScCheckstageServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((VScCheckstageEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((VScCheckstageEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((VScCheckstageEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(VScCheckstageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(VScCheckstageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(VScCheckstageEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,VScCheckstageEntity t){
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckstate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTrantype()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillno()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonid()));
 		sql  = sql.replace("#{departmentname}",String.valueOf(t.getDepartmentname()));
 		sql  = sql.replace("#{bill_name}",String.valueOf(t.getBillName()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}