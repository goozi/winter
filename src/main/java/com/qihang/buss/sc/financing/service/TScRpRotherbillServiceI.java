
package com.qihang.buss.sc.financing.service;
import java.util.List;

import com.qihang.buss.sc.financing.entity.TScRpRotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillentryEntity;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

public interface TScRpRotherbillServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TScRpRotherbillEntity tScRpRotherbill,
						List<TScRpRotherbillentryEntity> tScRpRotherbillentryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TScRpRotherbillEntity tScRpRotherbill,
						   List<TScRpRotherbillentryEntity> tScRpRotherbillentryList);
	public void delMain(TScRpRotherbillEntity tScRpRotherbill);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScRpRotherbillEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScRpRotherbillEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScRpRotherbillEntity t);

	/**
	 * 保存资金其他收入
	 * @param tScRpRotherbill
	 * @param tScRpRotherbillentryList
     */
	public void addRotherbill(TScRpRotherbillEntity tScRpRotherbill,
							  List<TScRpRotherbillentryEntity> tScRpRotherbillentryList);
}
