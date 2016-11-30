package com.qihang.buss.sc.baseinfo.service;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface TScItemTypeServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScItemTypeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScItemTypeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScItemTypeEntity t);

	/**
	 * 获取parentId 下的子树
	 * itemClassId : 基础资料类型
	 * parentId: 父节点
	 * 01	客户,02	供应商,03	物流公司,04	商品,05	职员,06	部门,07	仓库,
	 * 08	单位,09	收支项目,10	结算账户,11	辅助属性,12	辅助资料,13	分支机构
	 */
	public List<TScItemTypeEntity> getItemTypeTree(String itemClassId, String parentId,String sonId);

	/**
	 * 查出直接子节点
	 * @param parentId
	 * @return
	 */
	public List<TScItemTypeEntity> selectChildren(String parentId);

	/**
	 * 获取该树的所有节点的id
	 * @param itemClassId
	 * @param parentId
	 * @return
	 */
	public List<String> getParentIdFromTree(String itemClassId, String parentId,String sonId);

	/**
	 * 获取改节点下 所有分类名称
	 * @param itemClassId
	 * @param parentId
     * @return
     */
	public List<String> getParentNameFromTree(String itemClassId, String parentId,String sonId);

	/**
	 * 获取节点id的级次
	 * 如果返回-1 说明节点不存在
	 * @param id
	 * @return
	 */
	public  int getLevel(String id);

}
