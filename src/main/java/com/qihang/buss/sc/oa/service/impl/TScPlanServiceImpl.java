package com.qihang.buss.sc.oa.service.impl;


import com.qihang.buss.sc.oa.entity.TScPlanEntity;
import com.qihang.buss.sc.oa.service.TScPlanServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.web.system.pojo.base.TSType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Service("tScPlanService")
@Transactional
public class TScPlanServiceImpl extends CommonServiceImpl implements TScPlanServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScPlanEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScPlanEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScPlanEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScPlanEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScPlanEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScPlanEntity t){
	 	return true;
 	}

	@Override
	public List<Map<String, Object>> loadComboInfo(String dicName) {
		StringBuffer hql = new StringBuffer();
		hql.append("from TSType where TSTypegroup.typegroupcode = ?");
		List<TSType> types = super.findHql(hql.toString(),dicName);
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		for(TSType type : types){
			Map<String,Object> value = new HashMap<String, Object>();
			value.put("id",type.getTypecode());
			value.put("text",type.getTypename());
			result.add(value);
		}
		return result;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScPlanEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{plan_type}",String.valueOf(t.getPlanType()));
 		sql  = sql.replace("#{plan_name}",String.valueOf(t.getPlanName()));
 		sql  = sql.replace("#{plan_master}",String.valueOf(t.getPlanMaster()));
 		sql  = sql.replace("#{plan_group}",String.valueOf(t.getPlanGroup()));
 		sql  = sql.replace("#{plan_leadder}",String.valueOf(t.getPlanLeadder()));
 		sql  = sql.replace("#{plan_startdate}",String.valueOf(t.getPlanStartdate()));
 		sql  = sql.replace("#{plan_enddate}",String.valueOf(t.getPlanEnddate()));
 		sql  = sql.replace("#{planer}",String.valueOf(t.getPlaner()));
 		sql  = sql.replace("#{plan_info}",String.valueOf(t.getPlanInfo()));
 		sql  = sql.replace("#{plan_progress}",String.valueOf(t.getPlanProgress()));
 		sql  = sql.replace("#{plan_state}",String.valueOf(t.getPlanState()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}