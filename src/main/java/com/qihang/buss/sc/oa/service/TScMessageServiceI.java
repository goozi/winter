
package com.qihang.buss.sc.oa.service;


import com.qihang.buss.sc.oa.entity.TScMessageEntity;
import com.qihang.buss.sc.oa.entity.TScMessageFileEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.util.List;

public interface TScMessageServiceI extends CommonService {
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScMessageEntity tScMessage,
						List<TScMessageFileEntity> tScMessageFileList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScMessageEntity tScMessage,
						   List<TScMessageFileEntity> tScMessageFileList);
	public void delMain(TScMessageEntity tScMessage);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScMessageEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScMessageEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScMessageEntity t);

}
