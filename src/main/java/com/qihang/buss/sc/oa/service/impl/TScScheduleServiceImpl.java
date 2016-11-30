package com.qihang.buss.sc.oa.service.impl;


import com.qihang.buss.sc.oa.entity.TScScheduleEntity;
import com.qihang.buss.sc.oa.service.TScScheduleServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.UUID;

@Service("tScScheduleService")
@Transactional
public class TScScheduleServiceImpl extends CommonServiceImpl implements TScScheduleServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScScheduleEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScScheduleEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScScheduleEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @return
	 */
 	public boolean doAddSql(TScScheduleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @return
	 */
 	public boolean doUpdateSql(TScScheduleEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @return
	 */
 	public boolean doDelSql(TScScheduleEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScScheduleEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{calendar_title}",String.valueOf(t.getCalendarTitle()));
 		sql  = sql.replace("#{calendar_start_time}",String.valueOf(t.getCalendarStartTime()));
 		sql  = sql.replace("#{calendar_end_time}",String.valueOf(t.getCalendarEndTime()));
 		sql  = sql.replace("#{is_all_day_event}",String.valueOf(t.getIsAllDayEvent()));
 		sql  = sql.replace("#{time_zone}",String.valueOf(t.getTimezone()));
// 		sql  = sql.replace("#{plan_startdate}",String.valueOf(t.getPlanStartdate()));
// 		sql  = sql.replace("#{plan_enddate}",String.valueOf(t.getPlanEnddate()));
// 		sql  = sql.replace("#{planer}",String.valueOf(t.getPlaner()));
// 		sql  = sql.replace("#{plan_info}",String.valueOf(t.getPlanInfo()));
// 		sql  = sql.replace("#{plan_progress}",String.valueOf(t.getPlanProgress()));
// 		sql  = sql.replace("#{plan_state}",String.valueOf(t.getPlanState()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}