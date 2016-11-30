package com.qihang.buss.sc.oa.service;

import com.qihang.buss.sc.oa.entity.TScDocumentEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TScDocumentServiceI extends CommonService {
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScDocumentEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScDocumentEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScDocumentEntity t);

	/**
	 * 获取数集合
	 * @return
	 */
	TScDocumentEntity treeList();

	/**
	 * 删除验证
	 * @param tScDocument
	 * @return
	 */
	Boolean delVaild(TScDocumentEntity tScDocument);


	/**
	 * 获取APP文档目录
	 * @return
	 */
	List<TScDocumentEntity> getAppDoc(String userId);

	/**
	 * 获取APP文档目录测试
	 * @param id
	 * @param userId
	 * @return
	 */
	Map<String,Object> getAppDocT(String id, String userId, String queryName);

	List<TScDocumentEntity> getTreeList(String id);
}
