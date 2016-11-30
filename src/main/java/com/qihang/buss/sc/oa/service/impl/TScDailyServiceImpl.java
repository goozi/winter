package com.qihang.buss.sc.oa.service.impl;


import com.qihang.buss.sc.oa.entity.TScDailyEntity;
import com.qihang.buss.sc.oa.entity.TScPlanEntity;
import com.qihang.buss.sc.oa.service.TScDailyServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Service("tScDailyService")
@Transactional
public class TScDailyServiceImpl extends CommonServiceImpl implements TScDailyServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScDailyEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScDailyEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScDailyEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScDailyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScDailyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScDailyEntity t){
	 	return true;
 	}

	@Override
	public List<Map<String, Object>> loadPlanInfo(String userName,String userId) {
		StringBuffer hql = new StringBuffer();
		String q1 = userId+",%";
		String q2 = "%,"+userId+",%";
		String q3 = "%,"+userId;
		hql.append("from TScPlanEntity where planProgress < 100 and (");
		hql.append("(planMaster like '"+q1+"' or planMaster like '"+q2+"' or planMaster like '"+q3+"') or");
		hql.append("(planGroup like '"+q1+"' or planGroup like '"+q2+"' or planGroup like '"+q3+"') or");
		hql.append("(createBy = '"+userName+"')) order by createDate desc");
		List<TScPlanEntity> planInfo = super.findHql(hql.toString(),null);
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		if(planInfo.size() > 0) {
			for (int i = 0; i < 10; i++) {
				int size = planInfo.size();
				if(i < size) {
					TScPlanEntity entity = planInfo.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", entity.getId());
					map.put("name", entity.getPlanName());
					result.add(map);
				}
			}
		}
		return result;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScDailyEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{daily_title}",String.valueOf(t.getDailyTitle()));
 		sql  = sql.replace("#{daily_type}",String.valueOf(t.getDailyType()));
 		sql  = sql.replace("#{daily_time}",String.valueOf(t.getDailyTime()));
 		sql  = sql.replace("#{work_plan}",String.valueOf(t.getWorkPlan()));
 		sql  = sql.replace("#{daily_info}",String.valueOf(t.getDailyInfo()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}