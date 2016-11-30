package com.qihang.buss.sc.init.service.impl;

import com.qihang.buss.sc.init.entity.TScBegdataEntity;
import com.qihang.buss.sc.init.service.TScBegdataServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Service("tScBegdataService")
@Transactional
public class TScBegdataServiceImpl extends CommonServiceImpl implements TScBegdataServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScBegdataEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScBegdataEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScBegdataEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScBegdataEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScBegdataEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScBegdataEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScBegdataEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemID()));
 		sql  = sql.replace("#{rpdate}",String.valueOf(t.getRpDate()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptID()));
 		sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
 		sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
 		sql  = sql.replace("#{uncheckamount}",String.valueOf(t.getUnCheckAmount()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

/**
 * 单据审核后，不执行核算项目的应收账款增加或减少，
 * 而是等到系统结束初始化时才执行核算项目的应收账款增加或减少，
 * 同时反应在“日结表，还有结账表”等相关报表中。
 */
	@Override
	public boolean doAddReceivable(){
		boolean isOk = false;
		String hql="from TScBegdataEntity a where a.id and a.checkstate="+ Globals.AUDIT_FIN+" ";
		List<TScBegdataEntity> tScBegdataEntityList=this.findByQueryString(hql);
		for(TScBegdataEntity entity : tScBegdataEntityList){
			//累计加到
			//todo:因应收日结表和结账表这块还没有开发，这里等应收开发完毕之后，待开发， 参考TScIcInitialServiceImpl类
		}
		return isOk;
	}
}