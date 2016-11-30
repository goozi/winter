package com.qihang.buss.sc.oa.service;

import com.qihang.buss.sc.oa.entity.TScNoticeEntity;
import com.qihang.buss.sc.oa.entity.TScNoticeFileEntity;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.TSDepart;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface TScNoticeServiceI extends CommonService {
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScNoticeEntity tScNotice,
						List<TScNoticeFileEntity> tScNoticeFileList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScNoticeEntity tScNotice,
						   List<TScNoticeFileEntity> tScNoticeFileList);
	public void delMain(TScNoticeEntity tScNotice);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScNoticeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScNoticeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScNoticeEntity t);

	/**
	 * 公告信息发布
	 * @param id
	 * @return
     */
	public Map<String, Object> release(String id);

	/**
	 * 获取通知人员树形结构
	 * @param type
	 * @param noticeId
     * @return
     */
	public com.alibaba.fastjson.JSONArray getUserTreeInfo(String type, String noticeId, String style);

	/**
	 * 获取公告类型
	 * @param childIds
	 * @return
	 */
	public List<Map<String,Object>> loadTypeInfo(Set<String> childIds,TSDepart depart);
}
