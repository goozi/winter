package com.qihang.buss.sc.oa.service.impl;


import com.qihang.buss.sc.oa.entity.TScNewsEntity;
import com.qihang.buss.sc.oa.entity.TScNewsRelationEntity;
import com.qihang.buss.sc.oa.service.TScNewsServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("tScNewsService")
@Transactional
public class TScNewsServiceImpl extends CommonServiceImpl implements TScNewsServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScNewsEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScNewsEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScNewsEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScNewsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScNewsEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScNewsEntity t){
	 	return true;
 	}

	@Override
	public Map<String, Object> release(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtil.isEmpty(id)) {
			result.put("success", false);
			result.put("msg", "发布失败");
		} else {
			TScNewsEntity entity = this.get(TScNewsEntity.class, id);
			if(null != entity && entity.getUserId() != null) {
				String[] uIdArray = entity.getUserId().split(",");
				if(null != uIdArray && uIdArray.length > 0){
					for (int i=0;i<uIdArray.length;i++){
						TScNewsRelationEntity newsRelationEntity = new TScNewsRelationEntity();
						newsRelationEntity.setNewsId(entity.getId());
						newsRelationEntity.setUserId(uIdArray[i]);
						newsRelationEntity.setStatus(0);
						super.saveOrUpdate(newsRelationEntity);
					}
				}
			}
//			int updatei = super.updateBySqlString("UPDATE T_SC_NEWS SET ISSUANCE = 1," +
//					"ISSUANCE_DATE=convert(varchar(10),getdate(),120) WHERE ID='" + id + "'");

			int updatei = super.updateBySqlString("UPDATE T_SC_NEWS SET ISSUANCE = 1," +
					"ISSUANCE_DATE=DATE_FORMAT(NOW(),'%Y-%m-%d') WHERE ID='" + id + "'");


			if (updatei > 0) {
				result.put("success", true);
				result.put("msg", "发布成功");
			}
		}
		return result;
	}

	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScNewsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{type}",String.valueOf(t.getType()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{issuance}",String.valueOf(t.getIssuance()));
 		sql  = sql.replace("#{issuance_date}",String.valueOf(t.getIssuanceDate()));
 		sql  = sql.replace("#{comment}",String.valueOf(t.getComment()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}