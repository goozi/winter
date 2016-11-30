package com.qihang.buss.sc.baseinfo.service.impl;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.winter.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.io.Serializable;

@Service("tScItemTypeService")
@Transactional
public class TScItemTypeServiceImpl extends CommonServiceImpl implements TScItemTypeServiceI {




	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScItemTypeEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScItemTypeEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScItemTypeEntity) entity);
 	}

	/**
	 * 获取节点id的级次
	 * 如果返回-1 说明节点不存在
	 * @param id
	 * @return
	 */
 	public  int getLevel(String id){

		TScItemTypeEntity itemType = get(TScItemTypeEntity.class, id);
		if(itemType != null){
			return itemType.getLevel();
		} else {
			return -1;
		}

	}
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScItemTypeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScItemTypeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScItemTypeEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScItemTypeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{item_class_id}",String.valueOf(t.getItemClassId()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{parent_id}",String.valueOf(t.getParentId()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{count   }",String.valueOf(t.getCount   ()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 获取Id 为根节点的树
	 * itemClassId : 基础资料类型
	 * id: 目标节点
	 * 01	客户,02	供应商,03	物流公司,04	商品,05	职员,06	部门,07	仓库,
	 * 08	单位,09	收支项目,10	结算账户,11	辅助属性,12	辅助资料,13	分支机构
	 */
	public List<TScItemTypeEntity> getItemTypeTree(String itemClassId, String id,String sonId){
		TScItemTypeEntity item  = null;
		TScItemTypeEntity  root = null;
		String hql = "";
		Object[] queryInfo = new Object[]{itemClassId};
		if(StringUtil.isNotEmpty(sonId)){
			queryInfo = new Object[]{itemClassId,itemClassId,sonId};
			hql = "from TScItemTypeEntity where (itemClassId = ? and sonId is null) or(itemClassId = ? and sonId = ?)";
		} else {
			hql = "from TScItemTypeEntity where itemClassId = ?";
		}
		List<TScItemTypeEntity> items = super.findHql(hql,queryInfo);
		String parentIdTemp = null;
		Map<String,List<TScItemTypeEntity>> map =  new HashMap<String,List<TScItemTypeEntity> >();
		for(int i=0; i<items.size(); i++){
			TScItemTypeEntity temp = items.get(i);
			temp.setText(temp.getName()+"【"+temp.getNumber()+"】");
			temp.setAttributes(temp.getParentId()+"#"+temp.getNumber());
			parentIdTemp = temp.getParentId();
			if(map.get(parentIdTemp) == null){
				List<TScItemTypeEntity> list = new ArrayList<TScItemTypeEntity>();
				list.add(temp);
				map.put(parentIdTemp, list);
			} else {
				List<TScItemTypeEntity> list = map.get(parentIdTemp);
				list.add(temp);
			}
			if(temp.getId().equals(id)) {
				root = temp;
			}
		}
		Set<String> keys = map.keySet();
		for(String key : keys){
			for(int i=0; i<items.size(); i++){
				TScItemTypeEntity temp = items.get(i);
				if(temp.getId().equals(key)){
					temp.setChildren(map.get(key));
				}
			}
		}
		List<TScItemTypeEntity> tree = new ArrayList<TScItemTypeEntity>();
		if(root != null){
			tree.add(root);
		}

		return tree;
	}


	/**
	 * 获取 parentId 的直接子节点
	 * @param parentId
	 * @return
	 */
	public List<TScItemTypeEntity> selectChildren(String parentId){
		List<TScItemTypeEntity> list = findByProperty(TScItemTypeEntity.class, "parentId",parentId);
		return list;
	}

	/**
	 * 获取parentId 节点下的所有子节点的id，包含parentId
	 * @param itemClassId
	 * @param parentId
	 * @return
	 */
	public List<String> getParentIdFromTree(String itemClassId, String parentId,String sonId){
		List<String> list = new ArrayList<String>();
		List<TScItemTypeEntity> trees = getItemTypeTree(itemClassId, parentId,sonId);
		if(trees.size() > 0){
			TScItemTypeEntity root = trees.get(0);
			list.add(root.getId());
			getParentIdFromTree(root,list);
		}
		return list;
	}
	/**
	 * 获取所以该节点下分类名称
	 * @param itemClassId
	 * @param parentId
     * @return
     */
	public List<String> getParentNameFromTree(String itemClassId, String parentId,String sonId){
		List<String> list = new ArrayList<String>();
		List<TScItemTypeEntity> trees = getItemTypeTree(itemClassId, parentId,sonId);
		if(trees.size() > 0){
			TScItemTypeEntity root = trees.get(0);
			list.add(root.getName());
			getParentNameFromTree(root,list);
		}
		return list;
	}

	/**
	 * 获取
	 * @param itemTypeEntity
	 * @param list
	 * @return
	 */
	public List<String> getParentIdFromTree(TScItemTypeEntity itemTypeEntity,List list){
		List<TScItemTypeEntity> children = itemTypeEntity.getChildren();
		if(children != null && children.size() > 0){
			for(TScItemTypeEntity itemType: children){
				list.add(itemType.getId());
				getParentIdFromTree(itemType, list);
			}

		}
		return list;
	}
	/**
	 * 获取
	 * @param itemTypeEntity
	 * @param list
	 * @return
	 */
	public List<String> getParentNameFromTree(TScItemTypeEntity itemTypeEntity,List list){
		List<TScItemTypeEntity> children = itemTypeEntity.getChildren();
		if(children != null && children.size() > 0){
			for(TScItemTypeEntity itemType: children){
				list.add(itemType.getName());
				getParentNameFromTree(itemType, list);
			}

		}
		return list;
	}
}